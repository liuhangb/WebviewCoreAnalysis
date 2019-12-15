package android.webview.chromium;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.StrictMode;
import android.os.UserManager;
import android.provider.Settings.Global;
import android.view.ViewGroup;
import android.webkit.WebViewDelegate;
import com.tencent.tbs.core.webkit.CookieManager;
import com.tencent.tbs.core.webkit.GeolocationPermissions;
import com.tencent.tbs.core.webkit.ServiceWorkerController;
import com.tencent.tbs.core.webkit.TokenBindingService;
import com.tencent.tbs.core.webkit.ValueCallback;
import com.tencent.tbs.core.webkit.WebIconDatabase;
import com.tencent.tbs.core.webkit.WebStorage;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.WebView.PrivateAccess;
import com.tencent.tbs.core.webkit.WebViewDatabase;
import com.tencent.tbs.core.webkit.WebViewFactory;
import com.tencent.tbs.core.webkit.WebViewFactoryProvider;
import com.tencent.tbs.core.webkit.WebViewFactoryProvider.Statics;
import com.tencent.tbs.core.webkit.WebViewProvider;
import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import org.chromium.android_webview.AwAutofillProvider;
import org.chromium.android_webview.AwBrowserContext;
import org.chromium.android_webview.AwBrowserProcess;
import org.chromium.android_webview.ResourcesContextWrapperFactory;
import org.chromium.android_webview.WebViewChromiumRunQueue;
import org.chromium.android_webview.WebViewChromiumRunQueue.ChromiumHasStartedCallable;
import org.chromium.android_webview.command_line.CommandLineUtil;
import org.chromium.base.CommandLine;
import org.chromium.base.ContextUtils;
import org.chromium.base.PackageUtils;
import org.chromium.base.PathUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.base.library_loader.NativeLibraries;
import org.chromium.components.autofill.AutofillProvider;

public class WebViewChromiumFactoryProvider
  implements WebViewFactoryProvider
{
  protected static final String CHROMIUM_PREFS_NAME = "WebViewChromiumPrefs";
  protected static final String TAG = "WebViewChromiumFactoryProvider";
  private static final String VERSION_CODE_PREF = "lastVersionCodeUsed";
  private static WebViewChromiumFactoryProvider sSingleton;
  private static final Object sSingletonLock = new Object();
  final Object mAdapterLock = new Object();
  protected WebViewChromiumAwInit mAwInit;
  protected final WebViewChromiumRunQueue mRunQueue = new WebViewChromiumRunQueue(new WebViewChromiumRunQueue.ChromiumHasStartedCallable()
  {
    public boolean hasStarted()
    {
      return WebViewChromiumFactoryProvider.this.mAwInit.hasStarted();
    }
  });
  protected boolean mShouldDisableThreadChecking;
  private WebViewFactoryProvider.Statics mStaticsAdapter;
  protected WebViewDelegateFactory.WebViewDelegate mWebViewDelegate;
  protected SharedPreferences mWebViewPrefs;
  
  public WebViewChromiumFactoryProvider()
  {
    initialize(WebViewDelegateFactory.createApi21CompatibilityDelegate());
  }
  
  public WebViewChromiumFactoryProvider(WebViewDelegate paramWebViewDelegate)
  {
    initialize(WebViewDelegateFactory.createProxyDelegate(paramWebViewDelegate));
  }
  
  protected WebViewChromiumFactoryProvider(WebViewDelegateFactory.WebViewDelegate paramWebViewDelegate)
  {
    initialize(paramWebViewDelegate);
  }
  
  static void checkStorageIsNotDeviceProtected(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 24)
    {
      if (!paramContext.isDeviceProtectedStorage()) {
        return;
      }
      throw new IllegalArgumentException("WebView cannot be used with device protected storage");
    }
  }
  
  public static WebViewChromiumFactoryProvider create(WebViewDelegateFactory.WebViewDelegate paramWebViewDelegate)
  {
    return new WebViewChromiumFactoryProvider(paramWebViewDelegate);
  }
  
  private static void deleteContents(File paramFile)
  {
    paramFile = paramFile.listFiles();
    if (paramFile != null)
    {
      int j = paramFile.length;
      int i = 0;
      while (i < j)
      {
        File localFile = paramFile[i];
        if (localFile.isDirectory()) {
          deleteContents(localFile);
        }
        localFile.delete();
        i += 1;
      }
    }
  }
  
  static WebViewChromiumFactoryProvider getSingleton()
  {
    synchronized (sSingletonLock)
    {
      if (sSingleton != null)
      {
        WebViewChromiumFactoryProvider localWebViewChromiumFactoryProvider = sSingleton;
        return localWebViewChromiumFactoryProvider;
      }
      throw new RuntimeException("WebViewChromiumFactoryProvider has not been set!");
    }
  }
  
  public static boolean preloadInZygote()
  {
    String[] arrayOfString = NativeLibraries.LIBRARIES;
    int j = arrayOfString.length;
    int i = 0;
    while (i < j)
    {
      System.loadLibrary(arrayOfString[i]);
      i += 1;
    }
    return true;
  }
  
  protected static void setSingleton(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider)
  {
    synchronized (sSingletonLock)
    {
      if (sSingleton == null)
      {
        sSingleton = paramWebViewChromiumFactoryProvider;
        return;
      }
      throw new RuntimeException("WebViewChromiumFactoryProvider should only be set once!");
    }
  }
  
  private static boolean versionCodeGE(int paramInt1, int paramInt2)
  {
    return paramInt1 / 100000 >= paramInt2 / 100000;
  }
  
  public void addTask(Runnable paramRunnable)
  {
    this.mRunQueue.addTask(paramRunnable);
  }
  
  public AutofillProvider createAutofillProvider(Context paramContext, ViewGroup paramViewGroup)
  {
    if (Build.VERSION.SDK_INT < 26) {
      return null;
    }
    return new AwAutofillProvider(paramContext, paramViewGroup);
  }
  
  protected WebViewChromiumAwInit createAwInit()
  {
    return new WebViewChromiumAwInit(this);
  }
  
  public WebViewProvider createWebView(WebView paramWebView, WebView.PrivateAccess paramPrivateAccess)
  {
    return new WebViewChromium(this, paramWebView, paramPrivateAccess, this.mShouldDisableThreadChecking);
  }
  
  WebViewContentsClientAdapter createWebViewContentsClientAdapter(WebView paramWebView, Context paramContext)
  {
    return new WebViewContentsClientAdapter(paramWebView, paramContext, this.mWebViewDelegate);
  }
  
  WebViewChromiumAwInit getAwInit()
  {
    return this.mAwInit;
  }
  
  public AwBrowserContext getBrowserContextOnUiThread()
  {
    return this.mAwInit.getBrowserContextOnUiThread();
  }
  
  public CookieManager getCookieManager()
  {
    return this.mAwInit.getCookieManager();
  }
  
  public GeolocationPermissions getGeolocationPermissions()
  {
    return this.mAwInit.getGeolocationPermissions();
  }
  
  public WebViewChromiumRunQueue getRunQueue()
  {
    return this.mRunQueue;
  }
  
  public ServiceWorkerController getServiceWorkerController()
  {
    return this.mAwInit.getServiceWorkerController();
  }
  
  public WebViewFactoryProvider.Statics getStatics()
  {
    synchronized (this.mAwInit.getLock())
    {
      final SharedStatics localSharedStatics = this.mAwInit.getStatics();
      if (this.mStaticsAdapter == null) {
        this.mStaticsAdapter = new WebViewFactoryProvider.Statics()
        {
          public void clearClientCertPreferences(Runnable paramAnonymousRunnable)
          {
            localSharedStatics.clearClientCertPreferences(paramAnonymousRunnable);
          }
          
          public void enableSlowWholeDocumentDraw()
          {
            localSharedStatics.enableSlowWholeDocumentDraw();
          }
          
          public String findAddress(String paramAnonymousString)
          {
            return localSharedStatics.findAddress(paramAnonymousString);
          }
          
          public void freeMemoryForTests()
          {
            localSharedStatics.freeMemoryForTests();
          }
          
          public String getDefaultUserAgent(Context paramAnonymousContext)
          {
            return localSharedStatics.getDefaultUserAgent(paramAnonymousContext);
          }
          
          public Uri getSafeBrowsingPrivacyPolicyUrl()
          {
            return localSharedStatics.getSafeBrowsingPrivacyPolicyUrl();
          }
          
          public void initSafeBrowsing(Context paramAnonymousContext, ValueCallback<Boolean> paramAnonymousValueCallback)
          {
            localSharedStatics.initSafeBrowsing(paramAnonymousContext, CallbackConverter.fromValueCallback(paramAnonymousValueCallback));
          }
          
          public Uri[] parseFileChooserResult(int paramAnonymousInt, Intent paramAnonymousIntent)
          {
            return localSharedStatics.parseFileChooserResult(paramAnonymousInt, paramAnonymousIntent);
          }
          
          public void setSafeBrowsingWhitelist(List<String> paramAnonymousList, ValueCallback<Boolean> paramAnonymousValueCallback)
          {
            localSharedStatics.setSafeBrowsingWhitelist(paramAnonymousList, CallbackConverter.fromValueCallback(paramAnonymousValueCallback));
          }
          
          public void setWebContentsDebuggingEnabled(boolean paramAnonymousBoolean)
          {
            localSharedStatics.setWebContentsDebuggingEnabled(paramAnonymousBoolean);
          }
        };
      }
      return this.mStaticsAdapter;
    }
  }
  
  public TokenBindingService getTokenBindingService()
  {
    return this.mAwInit.getTokenBindingService();
  }
  
  public WebIconDatabase getWebIconDatabase()
  {
    return this.mAwInit.getWebIconDatabase();
  }
  
  public WebStorage getWebStorage()
  {
    return this.mAwInit.getWebStorage();
  }
  
  public WebViewDatabase getWebViewDatabase(Context paramContext)
  {
    return this.mAwInit.getWebViewDatabase(paramContext);
  }
  
  public WebViewDelegateFactory.WebViewDelegate getWebViewDelegate()
  {
    return this.mWebViewDelegate;
  }
  
  public SharedPreferences getWebViewPrefs()
  {
    return this.mWebViewPrefs;
  }
  
  public boolean hasStarted()
  {
    return this.mAwInit.hasStarted();
  }
  
  @TargetApi(24)
  protected void initialize(WebViewDelegateFactory.WebViewDelegate paramWebViewDelegate)
  {
    this.mAwInit = createAwInit();
    this.mWebViewDelegate = paramWebViewDelegate;
    paramWebViewDelegate = this.mWebViewDelegate.getApplication().getApplicationContext();
    try
    {
      checkStorageIsNotDeviceProtected(this.mWebViewDelegate.getApplication());
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      if (!((UserManager)paramWebViewDelegate.getSystemService(UserManager.class)).isUserUnlocked()) {
        break label291;
      }
    }
    paramWebViewDelegate = paramWebViewDelegate.createCredentialProtectedStorageContext();
    ContextUtils.initApplicationContext(ResourcesContextWrapperFactory.get(paramWebViewDelegate));
    CommandLineUtil.initCommandLine();
    boolean bool;
    int i;
    if (Build.VERSION.SDK_INT >= 26)
    {
      bool = this.mWebViewDelegate.isMultiProcessEnabled();
    }
    else if (Build.VERSION.SDK_INT >= 24)
    {
      i = Settings.Global.getInt(ContextUtils.getApplicationContext().getContentResolver(), "webview_multiprocess", 0);
      bool = true;
      if (i != 1) {
        bool = false;
      }
    }
    else
    {
      bool = false;
    }
    if (bool) {
      CommandLine.getInstance().appendSwitch("webview-sandboxed-renderer");
    }
    ThreadUtils.setWillOverrideUiThread();
    AwBrowserProcess.loadLibrary(this.mWebViewDelegate.getDataDirectorySuffix());
    PackageInfo localPackageInfo = WebViewFactory.getLoadedPackageInfo();
    paramWebViewDelegate = StrictMode.allowThreadDiskWrites();
    try
    {
      System.loadLibrary("webviewchromium_plat_support");
      this.mWebViewPrefs = ContextUtils.getApplicationContext().getSharedPreferences("WebViewChromiumPrefs", 0);
      i = this.mWebViewPrefs.getInt("lastVersionCodeUsed", 0);
      int j = localPackageInfo.versionCode;
      if (!versionCodeGE(j, i)) {
        deleteContents(new File(PathUtils.getDataDirectory()));
      }
      if (i != j) {
        this.mWebViewPrefs.edit().putInt("lastVersionCodeUsed", j).apply();
      }
      StrictMode.setThreadPolicy(paramWebViewDelegate);
      this.mShouldDisableThreadChecking = shouldDisableThreadChecking(ContextUtils.getApplicationContext());
      return;
    }
    finally
    {
      StrictMode.setThreadPolicy(paramWebViewDelegate);
    }
    label291:
    throw ((Throwable)localObject);
  }
  
  public <T> T runOnUiThreadBlocking(Callable<T> paramCallable)
  {
    return (T)this.mRunQueue.runBlockingFuture(new FutureTask(paramCallable));
  }
  
  public void runVoidTaskOnUiThreadBlocking(Runnable paramRunnable)
  {
    this.mRunQueue.runVoidTaskOnUiThreadBlocking(paramRunnable);
  }
  
  protected boolean shouldDisableThreadChecking(Context paramContext)
  {
    String str = paramContext.getPackageName();
    int i = PackageUtils.getPackageVersion(paramContext, str);
    int j = paramContext.getApplicationInfo().targetSdkVersion;
    if (i == -1) {
      return false;
    }
    boolean bool;
    if ("com.lge.email".equals(str))
    {
      if (j > 24) {
        return false;
      }
      if (i > 67502100) {
        return false;
      }
      bool = true;
    }
    else
    {
      bool = false;
    }
    if (str.startsWith("com.yahoo.mobile.client.android.mail"))
    {
      if (j > 23) {
        return false;
      }
      if (i > 1315850) {
        return false;
      }
      bool = true;
    }
    if ("com.htc.android.mail".equals(str))
    {
      if (j > 23) {
        return false;
      }
      if (i >= 866001861) {
        return false;
      }
      bool = true;
    }
    return bool;
  }
  
  public void startYourEngines(boolean paramBoolean)
  {
    this.mAwInit.startYourEngines(paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebViewChromiumFactoryProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */