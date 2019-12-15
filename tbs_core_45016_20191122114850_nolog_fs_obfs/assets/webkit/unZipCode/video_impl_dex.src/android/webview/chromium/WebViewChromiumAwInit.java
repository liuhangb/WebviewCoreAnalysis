package android.webview.chromium;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Looper;
import android.os.Process;
import com.tencent.tbs.core.webkit.CookieManager;
import com.tencent.tbs.core.webkit.GeolocationPermissions;
import com.tencent.tbs.core.webkit.ServiceWorkerController;
import com.tencent.tbs.core.webkit.TokenBindingService;
import com.tencent.tbs.core.webkit.WebIconDatabase;
import com.tencent.tbs.core.webkit.WebStorage;
import com.tencent.tbs.core.webkit.WebViewDatabase;
import org.chromium.android_webview.AwBrowserContext;
import org.chromium.android_webview.AwContents;
import org.chromium.android_webview.AwContentsStatics;
import org.chromium.android_webview.AwCookieManager;
import org.chromium.android_webview.AwNetworkChangeNotifierRegistrationPolicy;
import org.chromium.android_webview.AwResource;
import org.chromium.android_webview.AwTracingController;
import org.chromium.android_webview.HttpAuthDatabase;
import org.chromium.base.BuildConfig;
import org.chromium.base.ContextUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.base.TraceEvent;
import org.chromium.net.NetworkChangeNotifier;

public class WebViewChromiumAwInit
{
  protected static final int DIR_RESOURCE_PAKS_ANDROID = 3003;
  private static final String HTTP_AUTH_DATABASE_FILE = "http_auth.db";
  private static final String TAG = "WebViewChromiumAwInit";
  protected AwTracingController mAwTracingController;
  private AwBrowserContext mBrowserContext;
  protected CookieManagerAdapter mCookieManager;
  protected final WebViewChromiumFactoryProvider mFactory;
  protected GeolocationPermissionsAdapter mGeolocationPermissions;
  protected final Object mLock = new Object();
  private Object mServiceWorkerController;
  protected SharedStatics mSharedStatics;
  protected boolean mStarted;
  private Object mTokenBindingManager;
  protected WebIconDatabaseAdapter mWebIconDatabase;
  protected WebStorageAdapter mWebStorage;
  protected WebViewDatabaseAdapter mWebViewDatabase;
  
  protected WebViewChromiumAwInit(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider)
  {
    this.mFactory = paramWebViewChromiumFactoryProvider;
  }
  
  protected void doNetworkInitializations(Context paramContext)
  {
    if (paramContext.checkPermission("android.permission.ACCESS_NETWORK_STATE", Process.myPid(), Process.myUid()) == 0)
    {
      NetworkChangeNotifier.init();
      NetworkChangeNotifier.setAutoDetectConnectivityState(new AwNetworkChangeNotifierRegistrationPolicy());
    }
    boolean bool;
    if (paramContext.getApplicationInfo().targetSdkVersion >= 26) {
      bool = true;
    } else {
      bool = false;
    }
    AwContentsStatics.setCheckClearTextPermitted(bool);
  }
  
  protected void ensureChromiumStartedLocked(boolean paramBoolean)
  {
    if (this.mStarted) {
      return;
    }
    Looper localLooper;
    if (!paramBoolean) {
      localLooper = Looper.myLooper();
    } else {
      localLooper = Looper.getMainLooper();
    }
    ThreadUtils.setUiThread(localLooper);
    if (ThreadUtils.runningOnUiThread())
    {
      startChromiumLocked();
      return;
    }
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        synchronized (WebViewChromiumAwInit.this.mLock)
        {
          WebViewChromiumAwInit.this.startChromiumLocked();
          return;
        }
      }
    });
    while (!this.mStarted) {
      try
      {
        this.mLock.wait();
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;) {}
      }
    }
  }
  
  AwTracingController getAwTracingController()
  {
    synchronized (this.mLock)
    {
      if (this.mAwTracingController == null) {
        ensureChromiumStartedLocked(true);
      }
      return this.mAwTracingController;
    }
  }
  
  protected AwBrowserContext getBrowserContextOnUiThread()
  {
    if ((BuildConfig.DCHECK_IS_ON) && (!ThreadUtils.runningOnUiThread()))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getBrowserContextOnUiThread called on ");
      localStringBuilder.append(Thread.currentThread());
      throw new RuntimeException(localStringBuilder.toString());
    }
    if (this.mBrowserContext == null) {
      this.mBrowserContext = new AwBrowserContext(this.mFactory.getWebViewPrefs(), ContextUtils.getApplicationContext());
    }
    return this.mBrowserContext;
  }
  
  public CookieManager getCookieManager()
  {
    synchronized (this.mLock)
    {
      if (this.mCookieManager == null) {
        this.mCookieManager = new CookieManagerAdapter(new AwCookieManager());
      }
      return this.mCookieManager;
    }
  }
  
  public GeolocationPermissions getGeolocationPermissions()
  {
    synchronized (this.mLock)
    {
      if (this.mGeolocationPermissions == null) {
        ensureChromiumStartedLocked(true);
      }
      return this.mGeolocationPermissions;
    }
  }
  
  public Object getLock()
  {
    return this.mLock;
  }
  
  public ServiceWorkerController getServiceWorkerController()
  {
    synchronized (this.mLock)
    {
      if (this.mServiceWorkerController == null) {
        ensureChromiumStartedLocked(true);
      }
      return (ServiceWorkerController)this.mServiceWorkerController;
    }
  }
  
  public SharedStatics getStatics()
  {
    synchronized (this.mLock)
    {
      if (this.mSharedStatics == null) {
        ensureChromiumStartedLocked(true);
      }
      return this.mSharedStatics;
    }
  }
  
  public TokenBindingService getTokenBindingService()
  {
    synchronized (this.mLock)
    {
      if (this.mTokenBindingManager == null) {
        this.mTokenBindingManager = new TokenBindingManagerAdapter(this.mFactory);
      }
      return (TokenBindingService)this.mTokenBindingManager;
    }
  }
  
  public WebIconDatabase getWebIconDatabase()
  {
    synchronized (this.mLock)
    {
      if (this.mWebIconDatabase == null)
      {
        ensureChromiumStartedLocked(true);
        this.mWebIconDatabase = new WebIconDatabaseAdapter();
      }
      return this.mWebIconDatabase;
    }
  }
  
  public WebStorage getWebStorage()
  {
    synchronized (this.mLock)
    {
      if (this.mWebStorage == null) {
        ensureChromiumStartedLocked(true);
      }
      return this.mWebStorage;
    }
  }
  
  public WebViewDatabase getWebViewDatabase(Context paramContext)
  {
    synchronized (this.mLock)
    {
      if (this.mWebViewDatabase == null)
      {
        ensureChromiumStartedLocked(true);
        this.mWebViewDatabase = new WebViewDatabaseAdapter(this.mFactory, HttpAuthDatabase.newInstance(paramContext, "http_auth.db"));
      }
      return this.mWebViewDatabase;
    }
  }
  
  boolean hasStarted()
  {
    return this.mStarted;
  }
  
  protected void initPlatSupportLibrary()
  {
    DrawGLFunctor.setChromiumAwDrawGLFunction(AwContents.getAwDrawGLFunction());
    AwContents.setAwDrawSWFunctionTable(GraphicsUtils.getDrawSWFunctionTable());
    AwContents.setAwDrawGLFunctionTable(GraphicsUtils.getDrawGLFunctionTable());
  }
  
  protected void setUpResources(PackageInfo paramPackageInfo, Context paramContext)
  {
    AwResource.setResources(paramContext.getResources());
  }
  
  /* Error */
  protected void startChromiumLocked()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 55	android/webview/chromium/WebViewChromiumAwInit:mLock	Ljava/lang/Object;
    //   4: invokevirtual 309	java/lang/Object:notifyAll	()V
    //   7: aload_0
    //   8: getfield 109	android/webview/chromium/WebViewChromiumAwInit:mStarted	Z
    //   11: ifeq +4 -> 15
    //   14: return
    //   15: invokestatic 315	com/tencent/tbs/core/webkit/WebViewFactory:getLoadedPackageInfo	()Landroid/content/pm/PackageInfo;
    //   18: astore_3
    //   19: aload_3
    //   20: getfield 320	android/content/pm/PackageInfo:packageName	Ljava/lang/String;
    //   23: astore_1
    //   24: invokestatic 197	org/chromium/base/ContextUtils:getApplicationContext	()Landroid/content/Context;
    //   27: astore_2
    //   28: new 165	java/lang/Thread
    //   31: dup
    //   32: new 6	android/webview/chromium/WebViewChromiumAwInit$1
    //   35: dup
    //   36: aload_0
    //   37: aload_3
    //   38: aload_2
    //   39: invokespecial 323	android/webview/chromium/WebViewChromiumAwInit$1:<init>	(Landroid/webview/chromium/WebViewChromiumAwInit;Landroid/content/pm/PackageInfo;Landroid/content/Context;)V
    //   42: invokespecial 325	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   45: astore_3
    //   46: aload_3
    //   47: invokevirtual 328	java/lang/Thread:start	()V
    //   50: iconst_3
    //   51: invokestatic 334	org/chromium/base/library_loader/LibraryLoader:get	(I)Lorg/chromium/base/library_loader/LibraryLoader;
    //   54: invokevirtual 337	org/chromium/base/library_loader/LibraryLoader:ensureInitialized	()V
    //   57: iconst_3
    //   58: ldc_w 339
    //   61: invokestatic 345	org/chromium/base/PathService:override	(ILjava/lang/String;)V
    //   64: sipush 3003
    //   67: ldc_w 347
    //   70: invokestatic 345	org/chromium/base/PathService:override	(ILjava/lang/String;)V
    //   73: aload_0
    //   74: invokevirtual 349	android/webview/chromium/WebViewChromiumAwInit:initPlatSupportLibrary	()V
    //   77: aload_0
    //   78: aload_2
    //   79: invokevirtual 351	android/webview/chromium/WebViewChromiumAwInit:doNetworkInitializations	(Landroid/content/Context;)V
    //   82: aload_3
    //   83: invokevirtual 354	java/lang/Thread:join	()V
    //   86: aload_1
    //   87: invokestatic 359	org/chromium/android_webview/AwBrowserProcess:setWebViewPackageName	(Ljava/lang/String;)V
    //   90: aload_1
    //   91: iconst_1
    //   92: invokestatic 363	org/chromium/android_webview/AwBrowserProcess:configureChildProcessLauncher	(Ljava/lang/String;Z)V
    //   95: invokestatic 364	org/chromium/android_webview/AwBrowserProcess:start	()V
    //   98: aload_1
    //   99: iconst_1
    //   100: invokestatic 367	org/chromium/android_webview/AwBrowserProcess:handleMinidumpsAndSetMetricsConsent	(Ljava/lang/String;Z)V
    //   103: aload_0
    //   104: new 369	android/webview/chromium/SharedStatics
    //   107: dup
    //   108: invokespecial 370	android/webview/chromium/SharedStatics:<init>	()V
    //   111: putfield 228	android/webview/chromium/WebViewChromiumAwInit:mSharedStatics	Landroid/webview/chromium/SharedStatics;
    //   114: invokestatic 375	org/chromium/android_webview/command_line/CommandLineUtil:isBuildDebuggable	()Z
    //   117: ifeq +11 -> 128
    //   120: aload_0
    //   121: getfield 228	android/webview/chromium/WebViewChromiumAwInit:mSharedStatics	Landroid/webview/chromium/SharedStatics;
    //   124: iconst_1
    //   125: invokevirtual 378	android/webview/chromium/SharedStatics:setWebContentsDebuggingEnabledUnconditionally	(Z)V
    //   128: aload_0
    //   129: getfield 57	android/webview/chromium/WebViewChromiumAwInit:mFactory	Landroid/webview/chromium/WebViewChromiumFactoryProvider;
    //   132: invokevirtual 382	android/webview/chromium/WebViewChromiumFactoryProvider:getWebViewDelegate	()Landroid/webview/chromium/WebViewDelegateFactory$WebViewDelegate;
    //   135: invokeinterface 387 1 0
    //   140: invokestatic 392	org/chromium/base/TraceEvent:setATraceEnabled	(Z)V
    //   143: aload_0
    //   144: getfield 57	android/webview/chromium/WebViewChromiumAwInit:mFactory	Landroid/webview/chromium/WebViewChromiumFactoryProvider;
    //   147: invokevirtual 382	android/webview/chromium/WebViewChromiumFactoryProvider:getWebViewDelegate	()Landroid/webview/chromium/WebViewDelegateFactory$WebViewDelegate;
    //   150: new 8	android/webview/chromium/WebViewChromiumAwInit$2
    //   153: dup
    //   154: aload_0
    //   155: invokespecial 393	android/webview/chromium/WebViewChromiumAwInit$2:<init>	(Landroid/webview/chromium/WebViewChromiumAwInit;)V
    //   158: invokeinterface 397 2 0
    //   163: aload_0
    //   164: iconst_1
    //   165: putfield 109	android/webview/chromium/WebViewChromiumAwInit:mStarted	Z
    //   168: aload_0
    //   169: invokevirtual 399	android/webview/chromium/WebViewChromiumAwInit:getBrowserContextOnUiThread	()Lorg/chromium/android_webview/AwBrowserContext;
    //   172: astore_1
    //   173: aload_0
    //   174: new 401	android/webview/chromium/GeolocationPermissionsAdapter
    //   177: dup
    //   178: aload_0
    //   179: getfield 57	android/webview/chromium/WebViewChromiumAwInit:mFactory	Landroid/webview/chromium/WebViewChromiumFactoryProvider;
    //   182: aload_1
    //   183: invokevirtual 404	org/chromium/android_webview/AwBrowserContext:getGeolocationPermissions	()Lorg/chromium/android_webview/AwGeolocationPermissions;
    //   186: invokespecial 407	android/webview/chromium/GeolocationPermissionsAdapter:<init>	(Landroid/webview/chromium/WebViewChromiumFactoryProvider;Lorg/chromium/android_webview/AwGeolocationPermissions;)V
    //   189: putfield 216	android/webview/chromium/WebViewChromiumAwInit:mGeolocationPermissions	Landroid/webview/chromium/GeolocationPermissionsAdapter;
    //   192: aload_0
    //   193: new 409	android/webview/chromium/WebStorageAdapter
    //   196: dup
    //   197: aload_0
    //   198: getfield 57	android/webview/chromium/WebViewChromiumAwInit:mFactory	Landroid/webview/chromium/WebViewChromiumFactoryProvider;
    //   201: invokestatic 415	org/chromium/android_webview/AwQuotaManagerBridge:getInstance	()Lorg/chromium/android_webview/AwQuotaManagerBridge;
    //   204: invokespecial 418	android/webview/chromium/WebStorageAdapter:<init>	(Landroid/webview/chromium/WebViewChromiumFactoryProvider;Lorg/chromium/android_webview/AwQuotaManagerBridge;)V
    //   207: putfield 249	android/webview/chromium/WebViewChromiumAwInit:mWebStorage	Landroid/webview/chromium/WebStorageAdapter;
    //   210: aload_0
    //   211: aload_1
    //   212: invokevirtual 421	org/chromium/android_webview/AwBrowserContext:getTracingController	()Lorg/chromium/android_webview/AwTracingController;
    //   215: putfield 145	android/webview/chromium/WebViewChromiumAwInit:mAwTracingController	Lorg/chromium/android_webview/AwTracingController;
    //   218: getstatic 426	android/os/Build$VERSION:SDK_INT	I
    //   221: bipush 23
    //   223: if_icmple +18 -> 241
    //   226: aload_0
    //   227: new 428	android/webview/chromium/ServiceWorkerControllerAdapter
    //   230: dup
    //   231: aload_1
    //   232: invokevirtual 431	org/chromium/android_webview/AwBrowserContext:getServiceWorkerController	()Lorg/chromium/android_webview/AwServiceWorkerController;
    //   235: invokespecial 434	android/webview/chromium/ServiceWorkerControllerAdapter:<init>	(Lorg/chromium/android_webview/AwServiceWorkerController;)V
    //   238: putfield 222	android/webview/chromium/WebViewChromiumAwInit:mServiceWorkerController	Ljava/lang/Object;
    //   241: aload_0
    //   242: getfield 57	android/webview/chromium/WebViewChromiumAwInit:mFactory	Landroid/webview/chromium/WebViewChromiumFactoryProvider;
    //   245: invokevirtual 438	android/webview/chromium/WebViewChromiumFactoryProvider:getRunQueue	()Lorg/chromium/android_webview/WebViewChromiumRunQueue;
    //   248: invokevirtual 443	org/chromium/android_webview/WebViewChromiumRunQueue:drainQueue	()V
    //   251: invokestatic 448	org/chromium/base/CommandLine:getInstance	()Lorg/chromium/base/CommandLine;
    //   254: ldc_w 450
    //   257: invokevirtual 454	org/chromium/base/CommandLine:hasSwitch	(Ljava/lang/String;)Z
    //   260: pop
    //   261: return
    //   262: astore_1
    //   263: new 174	java/lang/RuntimeException
    //   266: dup
    //   267: aload_1
    //   268: invokespecial 457	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   271: athrow
    //   272: astore_1
    //   273: new 174	java/lang/RuntimeException
    //   276: dup
    //   277: ldc_w 459
    //   280: aload_1
    //   281: invokespecial 462	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   284: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	285	0	this	WebViewChromiumAwInit
    //   23	209	1	localObject1	Object
    //   262	6	1	localInterruptedException	InterruptedException
    //   272	9	1	localProcessInitException	org.chromium.base.library_loader.ProcessInitException
    //   27	52	2	localContext	Context
    //   18	65	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   82	86	262	java/lang/InterruptedException
    //   50	57	272	org/chromium/base/library_loader/ProcessInitException
  }
  
  void startYourEngines(boolean paramBoolean)
  {
    synchronized (this.mLock)
    {
      ensureChromiumStartedLocked(paramBoolean);
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebViewChromiumAwInit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */