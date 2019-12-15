package android.webview.chromium.tencent;

import android.content.Context;
import android.os.Looper;
import android.view.ViewGroup;
import android.webview.chromium.WebViewChromiumAwInit;
import android.webview.chromium.WebViewChromiumFactoryProvider;
import android.webview.chromium.WebViewDelegateFactory.WebViewDelegate;
import com.tencent.common.utils.FileUtils;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.RenderMonitor;
import com.tencent.smtt.webkit.RenderSmttService;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.j;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.WebView.PrivateAccess;
import com.tencent.tbs.core.webkit.WebViewProvider;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewFactory;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewFactoryProvider;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView.PrivateAccess;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import org.chromium.android_webview.ResourcesContextWrapperFactory;
import org.chromium.android_webview.command_line.CommandLineUtil;
import org.chromium.base.CommandLine;
import org.chromium.base.ContextUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.components.autofill.AutofillProvider;
import org.chromium.tencent.SharedResource;
import org.chromium.tencent.TencentAwBrowserContext;
import org.chromium.tencent.TencentAwBrowserProcess;
import org.chromium.tencent.content.browser.TencentTracingControllerAndroid;

public class TencentWebViewChromiumFactoryProvider
  extends WebViewChromiumFactoryProvider
  implements TencentWebViewFactoryProvider
{
  private ArrayList<String> mStrArray;
  private TencentTracingControllerAndroid mTracingControllerAndroid;
  
  public TencentWebViewChromiumFactoryProvider()
  {
    super(TencentWebViewDelegateFactory.createApi21CompatibilityDelegate());
  }
  
  private void appendSwitchFromServer()
  {
    if (this.mStrArray == null) {
      this.mStrArray = new ArrayList();
    }
    int j = this.mStrArray.size();
    int i = 0;
    while (i < j)
    {
      String str1 = (String)this.mStrArray.get(i);
      String str2 = SmttServiceProxy.getInstance().getGpuBugWorkAroundSwitch(str1.toUpperCase());
      if (str2 != null) {
        CommandLine.getInstance().appendSwitchWithValue(str1, str2);
      }
      i += 1;
    }
  }
  
  private void initGpuBugWorkAroundList()
  {
    if (this.mStrArray == null) {
      this.mStrArray = new ArrayList();
    }
    this.mStrArray.add("bug_workaround_force_bitmap_rendering");
    this.mStrArray.add("bug_workaround_gpu_bug_manager");
    this.mStrArray.add("bug_workaround_ext_gpu_json");
    this.mStrArray.add("bug_workaround_ext_soft_json");
  }
  
  public AutofillProvider createAutofillProvider(Context paramContext, ViewGroup paramViewGroup)
  {
    return null;
  }
  
  protected WebViewChromiumAwInit createAwInit()
  {
    return new TencentWebViewChromiumAwInit(this);
  }
  
  public WebViewProvider createWebView(WebView paramWebView, WebView.PrivateAccess paramPrivateAccess)
  {
    paramWebView = (TencentWebViewProxy.InnerWebView)paramWebView;
    paramWebView.getClass();
    return new TencentWebViewChromium(this, paramWebView, new TencentWebViewProxy.InnerWebView.PrivateAccess(paramWebView), this.mShouldDisableThreadChecking);
  }
  
  public TencentWebViewContentsClientAdapter createWebViewContentsClientAdapter(TencentWebViewProxy paramTencentWebViewProxy, Context paramContext)
  {
    return new TencentWebViewContentsClientAdapter(paramTencentWebViewProxy, paramContext, this.mWebViewDelegate);
  }
  
  public j getMediaAccessPermissions()
  {
    if ((this.mAwInit instanceof TencentWebViewChromiumAwInit)) {
      return ((TencentWebViewChromiumAwInit)this.mAwInit).getMediaAccessPermissions();
    }
    return null;
  }
  
  public TencentAwBrowserContext getTencentBrowserContext()
  {
    if ((this.mAwInit instanceof TencentWebViewChromiumAwInit)) {
      return ((TencentWebViewChromiumAwInit)this.mAwInit).getTencentBrowserContext();
    }
    return null;
  }
  
  protected void initialize(WebViewDelegateFactory.WebViewDelegate paramWebViewDelegate)
  {
    this.mAwInit = createAwInit();
    this.mWebViewDelegate = paramWebViewDelegate;
    SharedResource.getPerformanceData().put("init_application_context_begin", String.valueOf(System.currentTimeMillis()));
    ContextUtils.initApplicationContext(ResourcesContextWrapperFactory.get(ContextHolder.getInstance().getApplicationContext()));
    SharedResource.getPerformanceData().put("init_application_context_end", String.valueOf(System.currentTimeMillis()));
    SharedResource.getPerformanceData().put("init_default_commandline_begin", String.valueOf(System.currentTimeMillis()));
    CommandLineUtil.initCommandLine();
    CommandLine.getInstance().appendSwitch("enable-smoothness-mode");
    if (!SmttServiceProxy.getInstance().getIsX5ContentCacheDisabled()) {
      CommandLine.getInstance().appendSwitch("enable-faster-back-forward-navigation");
    }
    if (SmttServiceProxy.getInstance().getIsX5ContentCacheLogEnabled()) {
      CommandLine.getInstance().appendSwitch("enable-faster-back-forward-navigation-log");
    }
    if (!SmttServiceProxy.getInstance().getIsX5CustomFontDisabled()) {
      CommandLine.getInstance().appendSwitch("enable-custom-font");
    }
    paramWebViewDelegate = ContextUtils.getApplicationContext();
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(FileUtils.getSDcardDir(paramWebViewDelegate));
      ((StringBuilder)localObject).append("/tencent/tbs");
      localObject = new File(((StringBuilder)localObject).toString());
      if (!((File)localObject).exists()) {
        ((File)localObject).mkdirs();
      }
      localObject = FileUtils.createDir((File)localObject, paramWebViewDelegate.getPackageName());
      if (localObject != null) {
        CommandLine.getInstance().appendSwitchWithValue("deadcode_dump_dir", ((File)localObject).getPath());
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    if (SmttServiceProxy.getInstance().getCrashReportEnabled())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(FileUtils.getSDcardDir(paramWebViewDelegate));
      localStringBuilder.append("/tencent/tbs/");
      localStringBuilder.append(paramWebViewDelegate.getPackageName());
      localStringBuilder.append("/.crash_time");
      paramWebViewDelegate = localStringBuilder.toString();
      CommandLine.getInstance().appendSwitchWithValue("enable-crash-report", paramWebViewDelegate);
    }
    CommandLine.getInstance().appendSwitch("use-tencent-tile-priorties");
    CommandLine.getInstance().appendSwitch("enable-gpu-async-worker-context");
    CommandLine.getInstance().appendSwitchWithValue("origin-to-force-quic-on", "*");
    initGpuBugWorkAroundList();
    appendSwitchFromServer();
    RenderSmttService.a();
    RenderMonitor.a();
    e.a().a(CommandLine.getInstance());
    SharedResource.getPerformanceData().put("init_default_commandline_end", String.valueOf(System.currentTimeMillis()));
    ThreadUtils.setWillOverrideUiThread();
    TencentAwBrowserProcess.loadLibrary(this.mWebViewDelegate.getDataDirectorySuffix());
    TencentWebViewFactory.getLoadedPackageInfo();
    this.mWebViewPrefs = ContextUtils.getApplicationContext().getSharedPreferences("WebViewChromiumPrefs", 0);
    this.mShouldDisableThreadChecking = shouldDisableThreadChecking(ContextUtils.getApplicationContext());
    setSingleton(this);
  }
  
  public void setTbsMiniProgramDebugEnable(boolean paramBoolean)
  {
    if (Looper.myLooper() == ThreadUtils.getUiThreadLooper())
    {
      if (this.mTracingControllerAndroid == null)
      {
        if (!paramBoolean) {
          return;
        }
        this.mTracingControllerAndroid = new TencentTracingControllerAndroid(ContextUtils.getApplicationContext());
      }
      this.mTracingControllerAndroid.setTbsMiniProgramDebugEnable(paramBoolean);
      return;
    }
    throw new RuntimeException("Toggling of Mini Program Debugging must be done on the UI thread");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentWebViewChromiumFactoryProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */