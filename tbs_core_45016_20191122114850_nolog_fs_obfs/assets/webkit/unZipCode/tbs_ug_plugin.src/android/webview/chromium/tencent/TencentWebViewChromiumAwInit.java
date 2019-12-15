package android.webview.chromium.tencent;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Canvas;
import android.webview.chromium.WebViewChromiumAwInit;
import android.webview.chromium.WebViewChromiumFactoryProvider;
import android.webview.chromium.WebViewDelegateFactory.WebViewDelegate.OnTraceEnabledChangeListener;
import com.tencent.smtt.os.SMTTAdaptation;
import com.tencent.smtt.webkit.j;
import com.tencent.smtt.webkit.k;
import com.tencent.tbs.core.webkit.CookieManager;
import com.tencent.tbs.core.webkit.WebIconDatabase;
import com.tencent.tbs.core.webkit.WebViewDatabase;
import org.chromium.android_webview.AwContents;
import org.chromium.android_webview.AwCookieManager;
import org.chromium.android_webview.HttpAuthDatabase;
import org.chromium.android_webview.gfx.AwDrawFnImpl;
import org.chromium.android_webview.gfx.AwDrawFnImpl.DrawFnAccess;
import org.chromium.base.BuildInfo;
import org.chromium.base.CommandLine;
import org.chromium.base.ContextUtils;
import org.chromium.base.TraceEvent;
import org.chromium.tencent.TencentAwBrowserContext;

public class TencentWebViewChromiumAwInit
  extends WebViewChromiumAwInit
{
  private static final String HTTP_AUTH_DATABASE_FILE_M66 = "http_auth_m66.db";
  private static final String TAG = "TencentWebViewChromiumAwInit";
  private k mMediaAccessPermissions;
  private TencentAwBrowserContext mTencentBrowserContext;
  
  TencentWebViewChromiumAwInit(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider)
  {
    super(paramWebViewChromiumFactoryProvider);
  }
  
  private TencentAwBrowserContext getTencentBrowserContextLocked()
  {
    if (this.mTencentBrowserContext == null) {
      this.mTencentBrowserContext = new TencentAwBrowserContext(this.mFactory.getWebViewPrefs(), ContextUtils.getApplicationContext());
    }
    return this.mTencentBrowserContext;
  }
  
  public CookieManager getCookieManager()
  {
    synchronized (this.mLock)
    {
      if (this.mCookieManager == null) {
        this.mCookieManager = new TencentCookieManagerAdapter(new AwCookieManager());
      }
      return this.mCookieManager;
    }
  }
  
  public j getMediaAccessPermissions()
  {
    synchronized (this.mLock)
    {
      if (this.mMediaAccessPermissions == null)
      {
        ensureChromiumStartedLocked(true);
        this.mMediaAccessPermissions = new k(getTencentBrowserContextLocked().getMediaAccessPermissions());
      }
      return this.mMediaAccessPermissions;
    }
  }
  
  public TencentAwBrowserContext getTencentBrowserContext()
  {
    synchronized (this.mLock)
    {
      TencentAwBrowserContext localTencentAwBrowserContext = getTencentBrowserContextLocked();
      return localTencentAwBrowserContext;
    }
  }
  
  public WebIconDatabase getWebIconDatabase()
  {
    synchronized (this.mLock)
    {
      if (this.mWebIconDatabase == null)
      {
        ensureChromiumStartedLocked(true);
        this.mWebIconDatabase = new TencentWebIconDatabaseAdapter();
      }
      return this.mWebIconDatabase;
    }
  }
  
  public WebViewDatabase getWebViewDatabase(Context paramContext)
  {
    synchronized (this.mLock)
    {
      if (this.mWebViewDatabase == null)
      {
        ensureChromiumStartedLocked(true);
        this.mWebViewDatabase = new TencentWebViewDatabaseAdapter(this.mFactory, HttpAuthDatabase.newInstance(paramContext, "http_auth_m66.db"));
      }
      return this.mWebViewDatabase;
    }
  }
  
  protected void initPlatSupportLibrary()
  {
    if ((BuildInfo.isAtLeastQ()) && (CommandLine.getInstance().hasSwitch("enable-draw-webview-functor-on-androidQ")))
    {
      AwDrawFnImpl.setDrawFnFunctionTable(SMTTAdaptation.GetFunctionTable());
      AwDrawFnImpl.setDrawFnAccess(new TencentDrawFnAccess(null));
    }
    TencentDrawGLFunctor.setChromiumAwDrawGLFunction(AwContents.getAwDrawGLFunction());
    AwContents.setAwDrawSWFunctionTable(TencentGraphicsUtils.getDrawSWFunctionTable());
    AwContents.setAwDrawGLFunctionTable(TencentGraphicsUtils.getDrawGLFunctionTable());
  }
  
  /* Error */
  protected void startChromiumLocked()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 69	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mLock	Ljava/lang/Object;
    //   4: invokevirtual 199	java/lang/Object:notifyAll	()V
    //   7: aload_0
    //   8: getfield 202	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mStarted	Z
    //   11: ifeq +4 -> 15
    //   14: return
    //   15: invokestatic 208	com/tencent/tbs/core/webkit/tencent/TencentWebViewFactory:getLoadedPackageInfo	()Landroid/content/pm/PackageInfo;
    //   18: astore_2
    //   19: aload_2
    //   20: ifnonnull +9 -> 29
    //   23: ldc -46
    //   25: astore_1
    //   26: goto +8 -> 34
    //   29: aload_2
    //   30: getfield 215	android/content/pm/PackageInfo:packageName	Ljava/lang/String;
    //   33: astore_1
    //   34: invokestatic 60	org/chromium/base/ContextUtils:getApplicationContext	()Landroid/content/Context;
    //   37: astore_3
    //   38: new 217	java/lang/Thread
    //   41: dup
    //   42: new 6	android/webview/chromium/tencent/TencentWebViewChromiumAwInit$1
    //   45: dup
    //   46: aload_0
    //   47: aload_2
    //   48: aload_3
    //   49: invokespecial 219	android/webview/chromium/tencent/TencentWebViewChromiumAwInit$1:<init>	(Landroid/webview/chromium/tencent/TencentWebViewChromiumAwInit;Landroid/content/pm/PackageInfo;Landroid/content/Context;)V
    //   52: invokespecial 222	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   55: astore_2
    //   56: aload_2
    //   57: invokevirtual 225	java/lang/Thread:start	()V
    //   60: iconst_3
    //   61: invokestatic 231	org/chromium/base/library_loader/LibraryLoader:get	(I)Lorg/chromium/base/library_loader/LibraryLoader;
    //   64: invokevirtual 234	org/chromium/base/library_loader/LibraryLoader:ensureInitialized	()V
    //   67: iconst_3
    //   68: ldc -20
    //   70: invokestatic 242	org/chromium/base/PathService:override	(ILjava/lang/String;)V
    //   73: sipush 3003
    //   76: ldc -12
    //   78: invokestatic 242	org/chromium/base/PathService:override	(ILjava/lang/String;)V
    //   81: invokestatic 250	com/tencent/smtt/webkit/o:a	()Lcom/tencent/smtt/webkit/o;
    //   84: invokestatic 256	org/chromium/device/geolocation/LocationProviderFactory:setLocationProviderImpl	(Lorg/chromium/device/geolocation/LocationProviderFactory$LocationProvider;)V
    //   87: aload_0
    //   88: invokevirtual 258	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:initPlatSupportLibrary	()V
    //   91: aload_0
    //   92: aload_3
    //   93: invokevirtual 262	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:doNetworkInitializations	(Landroid/content/Context;)V
    //   96: aload_2
    //   97: invokevirtual 265	java/lang/Thread:join	()V
    //   100: aload_1
    //   101: invokestatic 271	org/chromium/android_webview/AwBrowserProcess:setWebViewPackageName	(Ljava/lang/String;)V
    //   104: aload_1
    //   105: iconst_1
    //   106: invokestatic 275	org/chromium/android_webview/AwBrowserProcess:configureChildProcessLauncher	(Ljava/lang/String;Z)V
    //   109: invokestatic 276	org/chromium/android_webview/AwBrowserProcess:start	()V
    //   112: aload_1
    //   113: iconst_1
    //   114: invokestatic 279	org/chromium/android_webview/AwBrowserProcess:handleMinidumpsAndSetMetricsConsent	(Ljava/lang/String;Z)V
    //   117: aload_0
    //   118: new 281	android/webview/chromium/tencent/TencentSharedStatics
    //   121: dup
    //   122: invokespecial 282	android/webview/chromium/tencent/TencentSharedStatics:<init>	()V
    //   125: putfield 286	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mSharedStatics	Landroid/webview/chromium/SharedStatics;
    //   128: invokestatic 291	org/chromium/android_webview/command_line/CommandLineUtil:isBuildDebuggable	()Z
    //   131: ifeq +14 -> 145
    //   134: aload_0
    //   135: getfield 286	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mSharedStatics	Landroid/webview/chromium/SharedStatics;
    //   138: iconst_1
    //   139: invokevirtual 296	android/webview/chromium/SharedStatics:setWebContentsDebuggingEnabledUnconditionally	(Z)V
    //   142: goto +20 -> 162
    //   145: invokestatic 301	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   148: invokevirtual 304	com/tencent/smtt/webkit/service/SmttServiceProxy:getIsInspectorEnabled	()Z
    //   151: ifeq +11 -> 162
    //   154: aload_0
    //   155: getfield 286	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mSharedStatics	Landroid/webview/chromium/SharedStatics;
    //   158: iconst_1
    //   159: invokevirtual 296	android/webview/chromium/SharedStatics:setWebContentsDebuggingEnabledUnconditionally	(Z)V
    //   162: aload_0
    //   163: getfield 48	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mFactory	Landroid/webview/chromium/WebViewChromiumFactoryProvider;
    //   166: invokevirtual 308	android/webview/chromium/WebViewChromiumFactoryProvider:getWebViewDelegate	()Landroid/webview/chromium/WebViewDelegateFactory$WebViewDelegate;
    //   169: invokeinterface 313 1 0
    //   174: invokestatic 318	org/chromium/base/TraceEvent:setATraceEnabled	(Z)V
    //   177: invokestatic 301	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   180: invokevirtual 321	com/tencent/smtt/webkit/service/SmttServiceProxy:getIsInspectorMiniProgramEnabled	()Z
    //   183: ifeq +14 -> 197
    //   186: aload_0
    //   187: getfield 48	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mFactory	Landroid/webview/chromium/WebViewChromiumFactoryProvider;
    //   190: checkcast 323	android/webview/chromium/tencent/TencentWebViewChromiumFactoryProvider
    //   193: iconst_1
    //   194: invokevirtual 326	android/webview/chromium/tencent/TencentWebViewChromiumFactoryProvider:setTbsMiniProgramDebugEnable	(Z)V
    //   197: aload_0
    //   198: getfield 48	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mFactory	Landroid/webview/chromium/WebViewChromiumFactoryProvider;
    //   201: invokevirtual 308	android/webview/chromium/WebViewChromiumFactoryProvider:getWebViewDelegate	()Landroid/webview/chromium/WebViewDelegateFactory$WebViewDelegate;
    //   204: new 8	android/webview/chromium/tencent/TencentWebViewChromiumAwInit$2
    //   207: dup
    //   208: aload_0
    //   209: invokespecial 329	android/webview/chromium/tencent/TencentWebViewChromiumAwInit$2:<init>	(Landroid/webview/chromium/tencent/TencentWebViewChromiumAwInit;)V
    //   212: invokeinterface 333 2 0
    //   217: invokestatic 301	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   220: invokevirtual 337	com/tencent/smtt/webkit/service/SmttServiceProxy:getConnectionTimeOutWIFI	()I
    //   223: invokestatic 301	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   226: invokevirtual 340	com/tencent/smtt/webkit/service/SmttServiceProxy:getConnectionTimeOutGPRS	()I
    //   229: invokestatic 346	com/tencent/smtt/net/AwNetworkUtils:setConnectionTimeOut	(II)V
    //   232: aload_0
    //   233: iconst_1
    //   234: putfield 202	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mStarted	Z
    //   237: aload_0
    //   238: invokevirtual 350	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:getBrowserContextOnUiThread	()Lorg/chromium/android_webview/AwBrowserContext;
    //   241: astore_1
    //   242: aload_0
    //   243: new 352	android/webview/chromium/tencent/TencentGeolocationPermissionsAdapter
    //   246: dup
    //   247: aload_0
    //   248: getfield 48	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mFactory	Landroid/webview/chromium/WebViewChromiumFactoryProvider;
    //   251: aload_1
    //   252: invokevirtual 358	org/chromium/android_webview/AwBrowserContext:getGeolocationPermissions	()Lorg/chromium/android_webview/AwGeolocationPermissions;
    //   255: invokespecial 361	android/webview/chromium/tencent/TencentGeolocationPermissionsAdapter:<init>	(Landroid/webview/chromium/WebViewChromiumFactoryProvider;Lorg/chromium/android_webview/AwGeolocationPermissions;)V
    //   258: putfield 365	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mGeolocationPermissions	Landroid/webview/chromium/GeolocationPermissionsAdapter;
    //   261: aload_0
    //   262: new 367	android/webview/chromium/WebStorageAdapter
    //   265: dup
    //   266: aload_0
    //   267: getfield 48	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mFactory	Landroid/webview/chromium/WebViewChromiumFactoryProvider;
    //   270: invokestatic 372	org/chromium/android_webview/AwQuotaManagerBridge:getInstance	()Lorg/chromium/android_webview/AwQuotaManagerBridge;
    //   273: invokespecial 375	android/webview/chromium/WebStorageAdapter:<init>	(Landroid/webview/chromium/WebViewChromiumFactoryProvider;Lorg/chromium/android_webview/AwQuotaManagerBridge;)V
    //   276: putfield 379	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mWebStorage	Landroid/webview/chromium/WebStorageAdapter;
    //   279: aload_0
    //   280: aload_1
    //   281: invokevirtual 383	org/chromium/android_webview/AwBrowserContext:getTracingController	()Lorg/chromium/android_webview/AwTracingController;
    //   284: putfield 387	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mAwTracingController	Lorg/chromium/android_webview/AwTracingController;
    //   287: aload_0
    //   288: getfield 48	android/webview/chromium/tencent/TencentWebViewChromiumAwInit:mFactory	Landroid/webview/chromium/WebViewChromiumFactoryProvider;
    //   291: invokevirtual 391	android/webview/chromium/WebViewChromiumFactoryProvider:getRunQueue	()Lorg/chromium/android_webview/WebViewChromiumRunQueue;
    //   294: invokevirtual 396	org/chromium/android_webview/WebViewChromiumRunQueue:drainQueue	()V
    //   297: invokestatic 140	org/chromium/base/CommandLine:getInstance	()Lorg/chromium/base/CommandLine;
    //   300: ldc_w 398
    //   303: invokevirtual 146	org/chromium/base/CommandLine:hasSwitch	(Ljava/lang/String;)Z
    //   306: pop
    //   307: return
    //   308: astore_1
    //   309: new 400	java/lang/RuntimeException
    //   312: dup
    //   313: aload_1
    //   314: invokespecial 403	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   317: athrow
    //   318: astore_1
    //   319: new 400	java/lang/RuntimeException
    //   322: dup
    //   323: ldc_w 405
    //   326: aload_1
    //   327: invokespecial 408	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   330: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	331	0	this	TencentWebViewChromiumAwInit
    //   25	256	1	localObject1	Object
    //   308	6	1	localInterruptedException	InterruptedException
    //   318	9	1	localProcessInitException	org.chromium.base.library_loader.ProcessInitException
    //   18	79	2	localObject2	Object
    //   37	56	3	localContext	Context
    // Exception table:
    //   from	to	target	type
    //   96	100	308	java/lang/InterruptedException
    //   60	67	318	org/chromium/base/library_loader/ProcessInitException
  }
  
  private static class TencentDrawFnAccess
    implements AwDrawFnImpl.DrawFnAccess
  {
    public void drawWebViewFunctor(Canvas paramCanvas, int paramInt)
    {
      SMTTAdaptation.drawWebViewFunctor(paramCanvas, paramInt);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentWebViewChromiumAwInit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */