package com.tencent.tbs.core.webkit.tencent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.MutableContextWrapper;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.common.http.Apn;
import com.tencent.mtt.video.export.H5VideoTime;
import com.tencent.mtt.video.export.VideoProxyDefault;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebChromeClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.ISelectionInterface;
import com.tencent.smtt.export.external.interfaces.IX5WebBackForwardList;
import com.tencent.smtt.export.external.interfaces.IX5WebBackForwardListClient;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.IX5WebHistoryItem;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.FindListener;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.PictureListener;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.WebViewTransport;
import com.tencent.smtt.export.external.interfaces.IX5WebViewClient;
import com.tencent.smtt.export.internal.interfaces.DownloadListenerExtension;
import com.tencent.smtt.export.internal.interfaces.IX5QQBrowserClient;
import com.tencent.smtt.export.internal.interfaces.IX5QQBrowserSettings;
import com.tencent.smtt.memory.MemoryChecker;
import com.tencent.smtt.net.AwNetworkUtils;
import com.tencent.smtt.net.NetworkSmttService;
import com.tencent.smtt.net.PageLoadDetector;
import com.tencent.smtt.net.PersistentSessionManager;
import com.tencent.smtt.net.QProxyStatusReporter;
import com.tencent.smtt.net.SWTools;
import com.tencent.smtt.util.MttTraceEvent;
import com.tencent.smtt.util.WebRtcUtils;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.util.g;
import com.tencent.smtt.util.j;
import com.tencent.smtt.util.m;
import com.tencent.smtt.util.o;
import com.tencent.smtt.webkit.AdInfoManager;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.DebugX5SettingsProxy;
import com.tencent.smtt.webkit.ReadModeManager;
import com.tencent.smtt.webkit.ReadModeManager.e;
import com.tencent.smtt.webkit.RenderMonitor;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.WebViewProviderExtension;
import com.tencent.smtt.webkit.a.b;
import com.tencent.smtt.webkit.i;
import com.tencent.smtt.webkit.nativewidget.NativeWidgetManager;
import com.tencent.smtt.webkit.s;
import com.tencent.smtt.webkit.s.a;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.TbsCoreEngine;
import com.tencent.tbs.core.WebViewObserver;
import com.tencent.tbs.core.partner.ad.AdWebView;
import com.tencent.tbs.core.partner.ad.AdWebViewController;
import com.tencent.tbs.core.partner.ad.AdWebviewStatusChangeProxy;
import com.tencent.tbs.core.partner.ad.TbsAdDebugTools;
import com.tencent.tbs.core.partner.filechooser.FileChooserFirstScreenView;
import com.tencent.tbs.core.partner.suspensionview.SUtiles;
import com.tencent.tbs.core.webkit.SDKMttTraceEvent;
import com.tencent.tbs.core.webkit.WebSettings;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.WebView.FindListener;
import com.tencent.tbs.core.webkit.WebView.HitTestResult;
import com.tencent.tbs.core.webkit.WebView.PictureListener;
import com.tencent.tbs.core.webkit.WebView.PrivateAccess;
import com.tencent.tbs.core.webkit.WebView.WebViewTransport;
import com.tencent.tbs.core.webkit.WebViewClient;
import com.tencent.tbs.core.webkit.WebViewProvider.ViewDelegate;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.HttpHost;
import org.chromium.android_webview.AwContentsClient.AwWebResourceError;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.android_webview.AwWebResourceResponse;
import org.chromium.base.annotations.UsedByReflection;
import org.chromium.tencent.SharedResource;
import org.chromium.tencent.TencentContentViewCore;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public abstract class TencentWebViewProxy
  implements IX5WebViewBase
{
  public static final String SWTOOLSURL = "http://swtools.qq.com";
  public static final String TBSDEBUGURL = "http://debugx5.qq.com";
  static final String TBS_DEBUG_INSPECTOR_ACTION = "android.intent.action.TBS_DEBUG";
  static final String TBS_DEBUG_INSPECTOR_SCHEME = "tbs_inspector";
  private static final int WEBAR_TIPS_GAP = 20;
  private static volatile Boolean isInvokingX5InspectorSettings = Boolean.valueOf(false);
  private Map<String, String> downloadInterceptInfo;
  private boolean isDownloadInterceptJsApiInjected = false;
  private TextView mARTypeNameView = null;
  protected AdWebViewController mAdWebViewController = null;
  private boolean mAutoPlayNextVideoFlag;
  private String mAutoPlayNextVideoUrl;
  private int mCharCountInBody = 0;
  protected Context mContext;
  private int mCountImage = 0;
  private int mCountVideo = 0;
  private final long mCreationTimeMillis = System.currentTimeMillis();
  private DebugX5SettingsProxy mDebugX5SettingsProxy = null;
  private String mFileInfoCookie = null;
  private String mFileInfoMimeType = null;
  private String mFileInfoName = null;
  private String mFileInfoSize = null;
  private String mFileInfoUrl = null;
  private boolean mForVideoSniff;
  private TextView mFpsView = null;
  private i mFullScreenSurfaceView = null;
  private com.tencent.smtt.webkit.h5video.c mH5MediaSourceProxy;
  private com.tencent.smtt.webkit.h5video.d mH5VideoProxy;
  private boolean mHasAlreadyAttached = false;
  private TextView mInspectHintView = null;
  private BroadcastReceiver mInspectorServiceListener = null;
  private boolean mIsActive;
  private boolean mIsAdWebViewControllerInited = false;
  private boolean mIsBarControllerInited = false;
  private boolean mIsDebugInterfaceAdded = false;
  private boolean mIsSWToolsInterfaceAdded = false;
  private boolean mIsTbsAdDebugInterfaceAdded = false;
  private NativeWidgetManager mNativeWidgetManager = null;
  private boolean mNeeedReportWIFIGuideFromVideo;
  public WeakReference<TencentWebViewProxy> mNewOpenedWebView = null;
  private ArrayList<WebViewObserver> mObserverList;
  protected TencentWebViewProvider mProvider;
  private boolean mReadModeLastPreCheckResult = false;
  private String mReadModeLastPreCheckUrl = null;
  private ReadModeManager mReadModeManager = null;
  private SWTools mSWTools = null;
  private int mScrollHeigth = 0;
  private int mSharedVideoTime;
  private final boolean mShowInspectHint = false;
  private int mSniffVideoID;
  private int mSniffVideoJumpCount;
  private String mSniffVideoPoster;
  private String mSniffVideoRefer;
  private String mSniffVideoTitle;
  private boolean mSoftKeyBoardShowing;
  private TbsAdDebugTools mTbsAdDebugTools = null;
  private boolean mTempPrereadingStatus = false;
  private String mTitle = null;
  private VelocityTracker mVelocityTracker = null;
  private s mViewManager;
  private InnerWebView mWebView;
  private int mXOverScrollMode;
  private int mYOverScrollMode;
  private g mttHelper = null;
  boolean needUploadPlatformType = true;
  private PageLoadDetector pageLoadDetector = null;
  private QProxyStatusReporter qProxyStatus = null;
  
  public TencentWebViewProxy(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public TencentWebViewProxy(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public TencentWebViewProxy(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, false);
  }
  
  protected TencentWebViewProxy(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2, Map<String, Object> paramMap, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mObserverList = new ArrayList();
    this.mWebView = new InnerWebView(paramContext, paramAttributeSet, paramInt1, paramInt2);
    this.mProvider = ((TencentWebViewProvider)this.mWebView.getWebViewProvider());
    SharedResource.getPerformanceData().put(SharedResource.PROVIDER_INIT_END, String.valueOf(System.currentTimeMillis()));
    this.mViewManager = new s(this);
    Apn.getApnType();
    this.pageLoadDetector = PageLoadDetector.a();
    getRealWebView().addJavascriptInterface(this.pageLoadDetector, "PageLoadDetector");
    this.mNativeWidgetManager = new NativeWidgetManager(this);
    getRealWebView().addJavascriptInterface(this.mNativeWidgetManager, "nativewidget");
    SharedResource.getPerformanceData().put(SharedResource.ADINFOMANAGER_INIT_BEGIN, String.valueOf(System.currentTimeMillis()));
    AdInfoManager.getInstance().setContext(getContext());
    SharedResource.getPerformanceData().put(SharedResource.ADINFOMANAGER_INIT_END, String.valueOf(System.currentTimeMillis()));
    PersistentSessionManager.getInstance().setContext(getContext());
    paramContext = TbsCoreEngine.createWebViewObserver(getRealWebView());
    if (paramContext != null) {
      addObserver(paramContext);
    }
    checkAdInit();
    notifyWebViewCreated(this);
    SharedResource.getPerformanceData().put(SharedResource.WEBVIEW_INIT_END, String.valueOf(System.currentTimeMillis()));
  }
  
  protected TencentWebViewProxy(Context paramContext, AttributeSet paramAttributeSet, int paramInt, Map<String, Object> paramMap, boolean paramBoolean)
  {
    SharedResource.getPerformanceData().put(SharedResource.X5WEBVIEW_CLINIT_END, String.valueOf(System.currentTimeMillis()));
    SharedResource.getPerformanceData().put(SharedResource.WEBVIEW_INIT_BEGIN, String.valueOf(System.currentTimeMillis()));
    SDKMttTraceEvent.begin("TencentWebViewProxy.<init>");
    this.mContext = paramContext;
    this.mObserverList = new ArrayList();
    SDKMttTraceEvent.begin("new InnerWebView");
    this.mWebView = new InnerWebView(paramContext, paramAttributeSet, paramInt);
    SDKMttTraceEvent.end("new InnerWebView");
    this.mProvider = ((TencentWebViewProvider)this.mWebView.getWebViewProvider());
    if (X5Log.isEnableLog()) {
      X5Log.d("WebView", "WebView init");
    }
    ContextHolder.getInstance().associateContext(this.mCreationTimeMillis, getContext());
    this.mViewManager = new s(this);
    Apn.getApnType();
    this.pageLoadDetector = PageLoadDetector.a();
    getRealWebView().addJavascriptInterface(this.pageLoadDetector, "PageLoadDetector");
    this.mNativeWidgetManager = new NativeWidgetManager(this);
    getRealWebView().addJavascriptInterface(this.mNativeWidgetManager, "nativewidget");
    SharedResource.getPerformanceData().put(SharedResource.ADINFOMANAGER_INIT_BEGIN, String.valueOf(System.currentTimeMillis()));
    AdInfoManager.getInstance().setContext(getContext());
    SharedResource.getPerformanceData().put(SharedResource.ADINFOMANAGER_INIT_END, String.valueOf(System.currentTimeMillis()));
    PersistentSessionManager.getInstance().setContext(getContext());
    paramContext = TbsCoreEngine.createWebViewObserver(getRealWebView());
    if (paramContext != null) {
      addObserver(paramContext);
    }
    checkAdInit();
    notifyWebViewCreated(this);
    SDKMttTraceEvent.end("TencentWebViewProxy.<init>");
    SharedResource.getPerformanceData().put(SharedResource.WEBVIEW_INIT_END, String.valueOf(System.currentTimeMillis()));
  }
  
  public TencentWebViewProxy(Context paramContext, AttributeSet paramAttributeSet, int paramInt, boolean paramBoolean)
  {
    this(paramContext, paramAttributeSet, paramInt, null, paramBoolean);
  }
  
  private void checkAdInit()
  {
    if ((!this.mIsAdWebViewControllerInited) && (!SmttServiceProxy.getInstance().isInMiniProgram(this)))
    {
      this.mAdWebViewController = new AdWebViewController(this);
      new AdWebviewStatusChangeProxy(this);
      this.mIsAdWebViewControllerInited = true;
    }
  }
  
  private void checkBarInit()
  {
    if (!ContextHolder.getInstance().isThirdPartyApp(getContext())) {
      return;
    }
    if (!this.mIsBarControllerInited)
    {
      new b(this);
      this.mIsBarControllerInited = true;
    }
  }
  
  protected static void checkThread()
  {
    if (Looper.myLooper() != Looper.getMainLooper())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Warning: A WebView method was called on thread '");
      localStringBuilder.append(Thread.currentThread().getName());
      localStringBuilder.append("'. All WebView methods must be called on the UI thread. Future versions of WebView may not support use on other threads.");
      new Throwable(localStringBuilder.toString());
      reportUnavailableThreadMessage(Log.getStackTraceString(new Throwable()));
    }
  }
  
  private String getNextUrl(boolean paramBoolean)
  {
    IX5WebBackForwardList localIX5WebBackForwardList = copyBackForwardList();
    int j = localIX5WebBackForwardList.getCurrentIndex();
    if (paramBoolean) {
      i = 1;
    } else {
      i = -1;
    }
    int i = j + i;
    if (localIX5WebBackForwardList.getItemAtIndex(i) != null) {
      return localIX5WebBackForwardList.getItemAtIndex(i).getUrl();
    }
    return null;
  }
  
  private int getPlatFormType()
  {
    Object localObject = this.mWebView;
    for (;;)
    {
      if (localObject == null) {
        break label53;
      }
      try
      {
        if (localObject.getClass().getCanonicalName().contains("org.apache.cordova")) {
          return 1;
        }
        if (!(((View)localObject).getParent() instanceof View)) {
          return 0;
        }
        localObject = (View)((View)localObject).getParent();
      }
      catch (Exception localException)
      {
        label53:
        for (;;) {}
      }
    }
    return -2;
    return 0;
  }
  
  private boolean handleUrlLoading(String paramString, Map<String, String> paramMap)
  {
    if (paramString == null) {
      return false;
    }
    o.a();
    if (com.tencent.smtt.webkit.c.a(paramString)) {
      return true;
    }
    if (j.a(getContext(), paramString)) {
      return true;
    }
    try
    {
      String str;
      if (paramString.length() < 500) {
        str = paramString;
      } else {
        str = paramString.substring(0, 490);
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Options=loadUrl; url=");
      localStringBuilder.append(str);
      localStringBuilder.append("; headers=");
      localStringBuilder.append(paramMap);
      X5Log.i("WebView", localStringBuilder.toString());
    }
    catch (Throwable paramMap)
    {
      for (;;) {}
    }
    paramMap = NetworkSmttService.a;
    if (!paramMap.isEmpty()) {
      getRealWebView().getSettings().setUserAgentString(paramMap);
    }
    AwNetworkUtils.setPreConnect(paramString, 1);
    NetworkSmttService.a(paramString);
    if (paramString.toLowerCase().startsWith("http://debugx5.qq.com"))
    {
      if (paramString.contains("openid"))
      {
        this.mttHelper = new g(this, paramString);
        this.mttHelper.a();
        return true;
      }
      addInjectedJavascriptInterface(paramString);
      return true;
    }
    if ((!paramString.startsWith("javascript:")) && (this.mIsDebugInterfaceAdded))
    {
      getRealWebView().removeJavascriptInterface("ProxyStatus");
      this.mIsDebugInterfaceAdded = false;
    }
    if (paramString.startsWith("http://swtools.qq.com"))
    {
      addSWInjectedJavascriptInterface();
      return true;
    }
    if (this.mIsSWToolsInterfaceAdded)
    {
      getRealWebView().removeJavascriptInterface("SWTools");
      this.mIsSWToolsInterfaceAdded = false;
    }
    if (paramString.contains("debugtbsad.html"))
    {
      SmttServiceProxy.getInstance().getTbsAdDebugWhiteList();
      addTBSADInjectedJavascriptInterface();
    }
    else if (this.mIsTbsAdDebugInterfaceAdded)
    {
      getRealWebView().removeJavascriptInterface("TbsAdDebugTools");
      this.mIsTbsAdDebugInterfaceAdded = false;
    }
    if (paramString.contains("mtt_in_readmode=1"))
    {
      navigateToReadModePage(ReadModeManager.b(paramString, "mtt_in_readmode=1"), ReadModeManager.e.a);
      return true;
    }
    return false;
  }
  
  private boolean isReadModeCanGoForward()
  {
    if (this.mReadModeManager != null)
    {
      if (getUrl() == null) {
        return false;
      }
      if (!getUrl().contains("mtt_in_readmode=1")) {
        return false;
      }
      return this.mReadModeManager.b();
    }
    return false;
  }
  
  public static boolean isTimerPaused()
  {
    return TencentContentViewCore.isWebKitSharedTimersSuspended();
  }
  
  @UsedByReflection("WebCoreReflectionPartnerImpl.java")
  public static void prefetchResource(String paramString, Map<String, String> paramMap)
  {
    checkThread();
    AwNetworkUtils.prefetchResource(paramString, paramMap);
  }
  
  private void readModeGoForward()
  {
    ReadModeManager localReadModeManager = this.mReadModeManager;
    if (localReadModeManager != null) {
      localReadModeManager.a();
    }
  }
  
  private static void reportUnavailableThreadMessage(String paramString)
  {
    if (!SmttServiceProxy.getInstance().isReportCallWebviewApiOnWrongThreadEnabled()) {
      return;
    }
    int i = paramString.indexOf("at ", paramString.indexOf("com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy"));
    paramString = paramString.substring(i + 3, Math.min(paramString.length() - i - 1, 900) + i);
    SmttServiceProxy.getInstance().reportCallWebviewApiOnWrongThread(paramString);
  }
  
  private void setVideoActive(boolean paramBoolean)
  {
    com.tencent.smtt.webkit.h5video.d locald = this.mH5VideoProxy;
    if ((locald != null) && (locald.isAllowRenderingWithinPage()) && (!this.mProvider.isFullScreen()))
    {
      if (paramBoolean)
      {
        locald.a();
        return;
      }
      locald.b();
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static void setWebContentsDebuggingEnabled(boolean paramBoolean)
  {
    WebView.setWebContentsDebuggingEnabled(paramBoolean);
  }
  
  private void uploadPlatformInfoToBeaconIfNeed()
  {
    String str = this.mContext.getApplicationInfo().packageName;
    if ((str != null) && (!str.equals("com.tencent.mm")) && (!str.equals("com.tencent.mobileqq")))
    {
      if (str.equals("com.qzone")) {
        return;
      }
      File localFile = this.mContext.getDir("tbs", 0);
      if (localFile != null)
      {
        if (!localFile.exists()) {
          return;
        }
        localFile = new File(localFile, "platformFile");
        if (localFile.exists()) {
          return;
        }
        try
        {
          localFile.createNewFile();
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
        int i = getPlatFormType();
        if (i <= 0) {
          return;
        }
        HashMap localHashMap = new HashMap();
        localHashMap.put("platform", String.valueOf(i));
        localHashMap.put("packagename", str);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(i);
        localStringBuilder.append("|");
        localStringBuilder.append(str);
        localStringBuilder.toString();
        SmttServiceProxy.getInstance().upLoadToBeacon("x5userplatformtype", localHashMap);
        return;
      }
      return;
    }
  }
  
  public void active()
  {
    com.tencent.smtt.webkit.h5video.d locald = this.mH5VideoProxy;
    if (locald != null) {
      locald.a(true);
    }
    this.mIsActive = true;
    this.mProvider.getExtension().active();
    this.mProvider.getExtension().startMainThreadBlockedDetect();
  }
  
  public void addInjectedJavascriptInterface(String paramString)
  {
    if (!this.mIsDebugInterfaceAdded)
    {
      if (this.qProxyStatus == null) {
        this.qProxyStatus = new QProxyStatusReporter(this);
      }
      if (this.mDebugX5SettingsProxy == null) {
        this.mDebugX5SettingsProxy = new DebugX5SettingsProxy(this);
      }
      getRealWebView().addJavascriptInterface(this.qProxyStatus, "ProxyStatus");
      getRealWebView().addJavascriptInterface(this.mDebugX5SettingsProxy, "DebugStatus");
      this.mIsDebugInterfaceAdded = true;
    }
    Object localObject = SmttResource.getTBSDebugStream();
    if (localObject != null)
    {
      localObject = com.tencent.smtt.util.d.a((InputStream)localObject, "UTF-8");
      getRealWebView().loadDataWithBaseURL(paramString, (String)localObject, "text/html", "UTF-8", "http://debugx5.qq.com");
    }
  }
  
  public void addJavascriptInterface(Object paramObject, String paramString)
  {
    this.mWebView.addJavascriptInterface(paramObject, paramString);
  }
  
  public void addObserver(WebViewObserver paramWebViewObserver)
  {
    if (paramWebViewObserver != null) {
      this.mObserverList.add(paramWebViewObserver);
    }
  }
  
  public void addSWInjectedJavascriptInterface()
  {
    if (!this.mIsSWToolsInterfaceAdded)
    {
      if (this.mSWTools == null) {
        this.mSWTools = new SWTools(this.mContext);
      }
      getRealWebView().addJavascriptInterface(this.mSWTools, "SWTools");
      this.mIsSWToolsInterfaceAdded = true;
      Object localObject = SmttResource.getServiceWorkerToolsStream();
      if (localObject != null)
      {
        localObject = com.tencent.smtt.util.d.a((InputStream)localObject, "UTF-8");
        getRealWebView().loadDataWithBaseURL("http://swtools.qq.com", (String)localObject, "text/html", "UTF-8", "http://swtools.qq.com");
      }
    }
  }
  
  public s.a addSurfaceOnUIThread(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (paramView == null)
    {
      Log.e("WebView", "Attempted to add an empty plugin view to the view hierarchy");
      return null;
    }
    if ((paramView instanceof SurfaceView)) {
      ((SurfaceView)paramView).setZOrderOnTop(true);
    }
    s.a locala = this.mViewManager.a();
    locala.a = paramView;
    locala.e = paramInt5;
    if (locala != null) {
      locala.b(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    return locala;
  }
  
  public void addSurfaceView(int paramInt)
  {
    i locali = this.mFullScreenSurfaceView;
    if (locali != null) {
      locali.a(paramInt);
    }
  }
  
  public void addTBSADInjectedJavascriptInterface()
  {
    if (!this.mIsTbsAdDebugInterfaceAdded)
    {
      if (this.mTbsAdDebugTools == null) {
        this.mTbsAdDebugTools = new TbsAdDebugTools(this);
      }
      getRealWebView().addJavascriptInterface(this.mTbsAdDebugTools, "TbsAdDebugTools");
      this.mIsTbsAdDebugInterfaceAdded = true;
    }
  }
  
  public void canEnterReadMode(android.webkit.ValueCallback<Boolean> paramValueCallback)
  {
    canEnterReadMode(ReadModeManager.e.a, paramValueCallback);
  }
  
  public void canEnterReadMode(ReadModeManager.e parame, android.webkit.ValueCallback<Boolean> paramValueCallback)
  {
    if ((getUrl() != null) && (getUrl().contains("mtt_in_readmode=1")))
    {
      paramValueCallback.onReceiveValue(Boolean.valueOf(false));
      return;
    }
    if (this.mReadModeManager == null) {
      this.mReadModeManager = new ReadModeManager(this);
    }
    this.mReadModeManager.a(parame, this, paramValueCallback);
  }
  
  public boolean canGoBack()
  {
    WeakReference localWeakReference = this.mNewOpenedWebView;
    if ((localWeakReference != null) && (localWeakReference.get() != null)) {
      return true;
    }
    return this.mWebView.canGoBack();
  }
  
  public boolean canGoBackOrForward(int paramInt)
  {
    return this.mWebView.canGoBackOrForward(paramInt);
  }
  
  public boolean canGoForward()
  {
    return this.mWebView.canGoForward();
  }
  
  public boolean canZoomIn()
  {
    return this.mWebView.canZoomIn();
  }
  
  public boolean canZoomOut()
  {
    return this.mWebView.canZoomOut();
  }
  
  public void cancelFling(long paramLong)
  {
    checkThread();
    this.mProvider.getExtension().cancelFling(paramLong);
  }
  
  public void cancelLongPress()
  {
    this.mWebView.cancelLongPress();
  }
  
  public boolean capturePageToFile(Bitmap.Config paramConfig, String paramString, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    return false;
  }
  
  public Picture capturePicture()
  {
    return this.mWebView.capturePicture();
  }
  
  public void clearCache(boolean paramBoolean)
  {
    this.mWebView.clearCache(paramBoolean);
  }
  
  public void clearExplorerInfo()
  {
    this.mCharCountInBody = 0;
    this.mCountVideo = 0;
    this.mCountImage = 0;
    this.mTitle = null;
  }
  
  public void clearFormData()
  {
    this.mWebView.clearFormData();
  }
  
  public void clearHistory()
  {
    this.mWebView.clearHistory();
  }
  
  public void clearMatches()
  {
    this.mWebView.clearMatches();
  }
  
  public void clearSslPreferences()
  {
    this.mWebView.clearSslPreferences();
  }
  
  public void clearTextEntry()
  {
    this.mProvider.getExtension().clearTextEntry();
  }
  
  public void clearView()
  {
    this.mWebView.clearView();
  }
  
  public int contentToViewDimension(int paramInt)
  {
    return this.mProvider.getExtension().contentToViewDimension(paramInt);
  }
  
  public int contentToViewX(int paramInt)
  {
    return this.mProvider.getExtension().contentToViewX(paramInt);
  }
  
  public int contentToViewY(int paramInt)
  {
    return this.mProvider.getExtension().contentToViewY(paramInt);
  }
  
  @UsedByReflection("UgJsApiExecutor.java")
  public IX5WebBackForwardList copyBackForwardList()
  {
    checkThread();
    return this.mProvider.copyBackForwardListTencent();
  }
  
  public void copySelection()
  {
    checkThread();
    this.mProvider.getExtension().copySelection();
  }
  
  public String createExplorerUrl(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("http://zixun.html5.qq.com/taglist/exploreList?url=");
      localStringBuilder.append(URLEncoder.encode(paramString, "UTF-8"));
      localStringBuilder.append("&sTitle=");
      localStringBuilder.append(URLEncoder.encode(this.mTitle, "UTF-8"));
      localStringBuilder.append("&iTextLength=");
      localStringBuilder.append(this.mCharCountInBody);
      localStringBuilder.append("&iVideoNum=");
      localStringBuilder.append(this.mCountVideo);
      localStringBuilder.append("&iImageNum=");
      localStringBuilder.append(this.mCountImage);
      paramString = localStringBuilder.toString();
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public Object createPrintDocumentAdapter(String paramString)
  {
    return this.mWebView.createPrintDocumentAdapter(paramString);
  }
  
  public void cutSelection()
  {
    checkThread();
    this.mProvider.getExtension().cutSelection();
  }
  
  public void deactive()
  {
    this.mProvider.getExtension().stopMainThreadBlockedDetect();
    this.mProvider.getExtension().deactive();
    this.mIsActive = false;
    com.tencent.smtt.webkit.h5video.d locald = this.mH5VideoProxy;
    if (locald != null) {
      locald.a(false);
    }
  }
  
  public void debugDumpTrees() {}
  
  public void destroy()
  {
    exitReadMode();
    this.mWebView.destroy();
    AdWebViewController localAdWebViewController = this.mAdWebViewController;
    if (localAdWebViewController != null) {
      localAdWebViewController.destroy();
    }
  }
  
  public void destroySurface(s.a parama)
  {
    if (parama != null) {
      parama.a();
    }
  }
  
  public void discardCurrentHiddenPage()
  {
    this.mProvider.getExtension().discardCurrentHiddenPage();
  }
  
  public abstract boolean dispatchTouchEvent(MotionEvent paramMotionEvent);
  
  public void doFingerSearchIfNeed()
  {
    this.mProvider.getExtension().doFingerSearchIfNeed();
  }
  
  public void documentHasImages(Message paramMessage)
  {
    this.mWebView.documentHasImages(paramMessage);
  }
  
  public abstract void draw(Canvas paramCanvas);
  
  public boolean drawPreReadBaseLayer(Canvas paramCanvas, boolean paramBoolean)
  {
    if ((getUrl() != null) && (matchReadModeCurrentHost(getUrl()))) {
      return false;
    }
    return this.mProvider.getExtension().drawPreReadBaseLayer(paramCanvas, paramBoolean);
  }
  
  public void dumpDisplayTree() {}
  
  public void enterReadMode(android.webkit.ValueCallback<Boolean> paramValueCallback, Runnable paramRunnable)
  {
    if (this.mReadModeManager == null) {
      this.mReadModeManager = new ReadModeManager(this);
    }
    this.mReadModeManager.a(this, this, paramValueCallback, paramRunnable);
    if (getSettingsExtension() != null)
    {
      this.mTempPrereadingStatus = getSettingsExtension().getPrereadingEnabled();
      getSettingsExtension().setPreFectch(false);
    }
  }
  
  public void enterSelectionMode(boolean paramBoolean)
  {
    checkThread();
    this.mProvider.getExtension().enterSelectionMode(paramBoolean);
  }
  
  public void enterSelectionModeWaitFS(boolean paramBoolean)
  {
    checkThread();
    this.mProvider.getExtension().enterSelectionModeWaitFS(paramBoolean);
  }
  
  @UsedByReflection("X5JsHelper.java")
  public void evaluateJavaScriptInFrame(String paramString1, String paramString2)
  {
    checkThread();
    this.mProvider.getExtension().evaluateJavaScriptInFrame(paramString1, paramString2);
  }
  
  public void evaluateJavaScriptInSubFrame(String paramString)
  {
    checkThread();
    this.mProvider.getExtension().evaluateJavaScriptInSubFrame(paramString);
  }
  
  @UsedByReflection("TbsServiceProxy.java")
  public void evaluateJavascript(String paramString, final android.webkit.ValueCallback<String> paramValueCallback)
  {
    checkThread();
    TencentWebViewProvider localTencentWebViewProvider = this.mProvider;
    if (paramValueCallback == null) {
      paramValueCallback = null;
    } else {
      paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
      {
        public void onReceiveValue(String paramAnonymousString)
        {
          paramValueCallback.onReceiveValue(paramAnonymousString);
        }
      };
    }
    localTencentWebViewProvider.evaluateJavaScript(paramString, paramValueCallback);
  }
  
  public void exitPluginFullScreen()
  {
    this.mProvider.getExtension().exitPluginFullScreen();
  }
  
  public void exitReadMode()
  {
    ReadModeManager localReadModeManager = this.mReadModeManager;
    if (localReadModeManager != null) {
      localReadModeManager.b();
    }
    if (getSettingsExtension() != null)
    {
      getSettingsExtension().setPreFectch(this.mTempPrereadingStatus);
      this.mTempPrereadingStatus = false;
    }
  }
  
  public int findAll(String paramString)
  {
    return this.mWebView.findAll(paramString);
  }
  
  public void findAllAsync(String paramString)
  {
    this.mWebView.findAllAsync(paramString);
  }
  
  public void findNext(boolean paramBoolean)
  {
    this.mWebView.findNext(paramBoolean);
  }
  
  public void flingScroll(int paramInt1, int paramInt2)
  {
    this.mWebView.flingScroll(paramInt1, paramInt2);
  }
  
  public void forceSyncOffsetToCore() {}
  
  public void freeMemory()
  {
    this.mWebView.freeMemory();
  }
  
  public void fullScreenPluginHidden()
  {
    this.mProvider.getExtension().fullScreenPluginHidden();
  }
  
  public VideoProxyDefault getActiveVideoProxy()
  {
    return getH5VideoProxy();
  }
  
  public HttpHost getActualQProxy()
  {
    return null;
  }
  
  public int getAddressbarDisplayMode()
  {
    return this.mProvider.getExtension().getAddressbarDisplayMode();
  }
  
  public ArrayList<H5VideoTime> getAllVideoTime()
  {
    return com.tencent.smtt.webkit.h5video.d.a(this);
  }
  
  @UsedByReflection("X5JsHelper.java")
  public boolean getAutoPlayFlag()
  {
    return this.mAutoPlayNextVideoFlag;
  }
  
  public String getAutoPlayNextVideoUrl()
  {
    return this.mAutoPlayNextVideoUrl;
  }
  
  public SslCertificate getCertificate()
  {
    return this.mWebView.getCertificate();
  }
  
  public int getCharCountInBody()
  {
    return this.mCharCountInBody;
  }
  
  public int getContentHeight()
  {
    return this.mWebView.getContentHeight();
  }
  
  public int getContentWidth()
  {
    return this.mWebView.getContentWidth();
  }
  
  @UsedByReflection("UgJsApiExecutor.java")
  public Context getContext()
  {
    return this.mContext;
  }
  
  @UsedByReflection("MiniQB")
  public int getCurrentHistoryItemIndex()
  {
    return this.mProvider.getExtension().getCurrentHistoryItemIndex();
  }
  
  public String getDownloadFileInfo()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("name", this.mFileInfoName);
      localJSONObject.put("url", this.mFileInfoUrl);
      localJSONObject.put("size", this.mFileInfoSize);
      localJSONObject.put("cookie", this.mFileInfoCookie);
      localJSONObject.put("mimetype", this.mFileInfoMimeType);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return localJSONObject.toString();
  }
  
  @UsedByReflection("X5JsHelper.java")
  public Map<String, String> getDownloadInterceptInfo()
  {
    return this.downloadInterceptInfo;
  }
  
  public boolean getDrawWithBuffer()
  {
    return false;
  }
  
  public Bitmap getFavicon()
  {
    return this.mWebView.getFavicon();
  }
  
  public boolean getGlobalVisibleRect(Rect paramRect)
  {
    return this.mWebView.getGlobalVisibleRect(paramRect);
  }
  
  public com.tencent.smtt.webkit.h5video.c getH5MediaSourceProxy()
  {
    return this.mH5MediaSourceProxy;
  }
  
  public com.tencent.smtt.webkit.h5video.d getH5VideoProxy()
  {
    return this.mH5VideoProxy;
  }
  
  public int getHeight()
  {
    return this.mWebView.getHeight();
  }
  
  @UsedByReflection("X5JsHelper.java")
  public IX5WebHistoryItem getHistoryItem(int paramInt)
  {
    checkThread();
    return this.mProvider.getExtension().getHistoryItem(paramInt);
  }
  
  public IX5WebViewBase.HitTestResult getHitTestResult()
  {
    checkThread();
    return this.mProvider.getHitTestResultTencent();
  }
  
  public String[] getHttpAuthUsernamePassword(String paramString1, String paramString2)
  {
    return this.mWebView.getHttpAuthUsernamePassword(paramString1, paramString2);
  }
  
  public boolean getIsCreatedFullScreenSurfaceView()
  {
    i locali = this.mFullScreenSurfaceView;
    if (locali != null) {
      return locali.a();
    }
    return false;
  }
  
  public NativeWidgetManager getNativeWidgetManager()
  {
    return this.mNativeWidgetManager;
  }
  
  public String getOriginalUrl()
  {
    return this.mWebView.getOriginalUrl();
  }
  
  public ViewParent getParent()
  {
    return this.mWebView.getParent();
  }
  
  public String[] getPasswordFromDatabase(String paramString)
  {
    return this.mProvider.getExtension().getPasswordFromDatabase(paramString);
  }
  
  public int getProgress()
  {
    return this.mWebView.getProgress();
  }
  
  public IX5QQBrowserClient getQQBrowserClient()
  {
    return this.mProvider.getExtension().getQQBrowserClient();
  }
  
  public IX5QQBrowserSettings getQQBrowserSettings()
  {
    return getSettingsExtension();
  }
  
  public Context getRealContext()
  {
    Context localContext1 = getContext();
    try
    {
      if ((!(localContext1 instanceof Activity)) && ((localContext1 instanceof MutableContextWrapper)))
      {
        Context localContext2 = ((MutableContextWrapper)localContext1).getBaseContext();
        return localContext2;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return localContext1;
  }
  
  public InnerWebView getRealWebView()
  {
    return this.mWebView;
  }
  
  @UsedByReflection("X5JsHelper.java")
  public float getScale()
  {
    return this.mWebView.getScale();
  }
  
  public int getScrollX()
  {
    return this.mWebView.getScrollX();
  }
  
  public int getScrollY()
  {
    return this.mWebView.getScrollY();
  }
  
  public String getSelection()
  {
    checkThread();
    return this.mProvider.getExtension().getSelection();
  }
  
  @UsedByReflection("X5JsHelper.java")
  public IX5WebSettings getSettings()
  {
    checkThread();
    return this.mProvider.getSettingsTencent();
  }
  
  public WebSettingsExtension getSettingsExtension()
  {
    checkThread();
    return this.mProvider.getExtension().getSettingsExtension();
  }
  
  public int getSharedVideoTime()
  {
    return this.mSharedVideoTime;
  }
  
  public int getSniffVideoID()
  {
    return this.mSniffVideoID;
  }
  
  public String getSniffVideoRefer()
  {
    return this.mSniffVideoRefer;
  }
  
  public boolean getSolarMode()
  {
    checkThread();
    return false;
  }
  
  @UsedByReflection("X5JsHelper.java")
  public String getTitle()
  {
    return this.mWebView.getTitle();
  }
  
  public abstract int getTitleHeight();
  
  @UsedByReflection("X5JsHelper.java")
  public String getUrl()
  {
    return this.mWebView.getUrl();
  }
  
  public s getViewManager()
  {
    return this.mViewManager;
  }
  
  public int getVisibility()
  {
    return this.mWebView.getVisibility();
  }
  
  public int getVisibleTitleHeight()
  {
    return this.mWebView.getVisibleTitleHeight();
  }
  
  public IX5WebChromeClient getWebChromeClient()
  {
    TencentWebChromeClient localTencentWebChromeClient = (TencentWebChromeClient)this.mProvider.getWebChromeClient();
    if (localTencentWebChromeClient == null) {
      return null;
    }
    return localTencentWebChromeClient.getX5WebChromeClient();
  }
  
  public IX5WebChromeClientExtension getWebChromeClientExtension()
  {
    return this.mProvider.getExtension().getWebChromeClientExtension();
  }
  
  public IX5WebViewClient getWebViewClient()
  {
    return ((TencentWebViewClient)this.mProvider.getWebViewClient()).getX5WebViewClient();
  }
  
  @UsedByReflection("X5JsHelper.java")
  public IX5WebViewClientExtension getWebViewClientExtension()
  {
    return this.mProvider.getExtension().getWebViewClientExtension();
  }
  
  public TencentWebViewProvider getWebViewProvider()
  {
    return this.mProvider;
  }
  
  public int getWidth()
  {
    return this.mWebView.getWidth();
  }
  
  public void getWindowVisibleDisplayFrame(Rect paramRect)
  {
    this.mWebView.getWindowVisibleDisplayFrame(paramRect);
  }
  
  public int getYVelocity()
  {
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    if (localVelocityTracker == null) {
      return -1;
    }
    return (int)localVelocityTracker.getYVelocity();
  }
  
  public View getZoomControls()
  {
    return this.mWebView.getZoomControls();
  }
  
  public void goBack()
  {
    WeakReference localWeakReference = this.mNewOpenedWebView;
    if ((localWeakReference != null) && (localWeakReference.get() != null))
    {
      if (((TencentWebViewProxy)this.mNewOpenedWebView.get()).canGoBack())
      {
        ((TencentWebViewProxy)this.mNewOpenedWebView.get()).goBack();
        return;
      }
      ((TencentWebViewProxy)this.mNewOpenedWebView.get()).destroy();
      this.mNewOpenedWebView = null;
      return;
    }
    this.mWebView.goBack();
  }
  
  public void goBackOrForward(int paramInt)
  {
    this.mWebView.goBackOrForward(paramInt);
  }
  
  public void goForward()
  {
    this.mWebView.goForward();
  }
  
  public void initX5SelectionIfNeeded() {}
  
  public void invalidateContent() {}
  
  public void invokeZoomPicker()
  {
    this.mWebView.invokeZoomPicker();
  }
  
  public boolean isActive()
  {
    return this.mIsActive;
  }
  
  public boolean isDownloadInterceptJsApiInjected()
  {
    return this.isDownloadInterceptJsApiInjected;
  }
  
  public boolean isForVideoSniff()
  {
    return this.mForVideoSniff;
  }
  
  public boolean isHorizontalScrollBarEnabled()
  {
    return this.mWebView.isHorizontalScrollBarEnabled();
  }
  
  public boolean isMobileSite()
  {
    return false;
  }
  
  public boolean isNeeedReportWIFIGuideFromVideo()
  {
    return this.mNeeedReportWIFIGuideFromVideo;
  }
  
  public boolean isPluginFullScreen()
  {
    return this.mProvider.getExtension().isPluginFullScreen();
  }
  
  public boolean isPreReadCanGoForward()
  {
    if ((!canGoBackOrForward(1)) && (isReadModeCanGoForward())) {
      return true;
    }
    return this.mProvider.getExtension().isPreReadCanGoForward();
  }
  
  public boolean isPrivateBrowsingEnabled()
  {
    return this.mWebView.isPrivateBrowsingEnabled();
  }
  
  public boolean isShown()
  {
    return this.mWebView.isShown();
  }
  
  public boolean isSoftKeyBoardShowing()
  {
    return this.mSoftKeyBoardShowing;
  }
  
  public boolean isSurfaceviewMode()
  {
    return false;
  }
  
  public boolean isVerticalScrollBarEnabled()
  {
    return this.mWebView.isVerticalScrollBarEnabled();
  }
  
  public boolean isX5CanvasHardwareAccelerated(Canvas paramCanvas)
  {
    if (Build.VERSION.SDK_INT >= 11) {
      return paramCanvas.isHardwareAccelerated();
    }
    return false;
  }
  
  public boolean isX5HardwareAccelerated()
  {
    if (Build.VERSION.SDK_INT >= 11) {
      return this.mWebView.isHardwareAccelerated();
    }
    return false;
  }
  
  public boolean isX5HardwareAcceleratedForVideo()
  {
    return (isX5HardwareAccelerated()) && (this.mWebView.getLayerType() != 1);
  }
  
  public Bitmap lastHitTestBmp()
  {
    return this.mProvider.lastHitTestBmp();
  }
  
  public void leaveSelectionMode()
  {
    checkThread();
    this.mProvider.getExtension().leaveSelectionMode();
  }
  
  public void loadData(String paramString1, String paramString2, String paramString3)
  {
    this.mWebView.loadData(paramString1, paramString2, paramString3);
  }
  
  public void loadDataWithBaseURL(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if (m.a().a())
    {
      this.mWebView.loadUrl("http://debugtbs.qq.com?from=core");
      return;
    }
    this.mWebView.loadDataWithBaseURL(paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  @UsedByReflection("jsApiDomainWhiteListManager.java")
  public void loadUrl(String paramString)
  {
    this.mWebView.loadUrl(paramString);
  }
  
  @UsedByReflection("jsApiDomainWhiteListManager.java")
  public void loadUrl(String paramString, Map<String, String> paramMap)
  {
    if (m.a().a())
    {
      this.mWebView.loadUrl("http://debugtbs.qq.com?from=core");
      return;
    }
    this.mWebView.loadUrl(paramString, paramMap);
  }
  
  public boolean matchReadModeCurrentHost(String paramString)
  {
    ReadModeManager localReadModeManager = this.mReadModeManager;
    if (localReadModeManager == null) {
      return false;
    }
    return localReadModeManager.a(paramString);
  }
  
  public void navigateToReadModePage(String paramString, ReadModeManager.e parame)
  {
    if (this.mReadModeManager == null) {
      this.mReadModeManager = new ReadModeManager(this);
    }
    this.mReadModeManager.a(paramString, parame);
  }
  
  public void notifyAttachedToWindow()
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onAttachedToWindow();
    }
  }
  
  public void notifyContentPageSwapIn(String paramString)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onContentPageSwapIn(paramString);
    }
  }
  
  public void notifyContentsSizeChanged(int paramInt1, int paramInt2)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onContentsSizeChanged(paramInt1, paramInt2);
    }
  }
  
  public void notifyDetachedFromWindow()
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onDetachedFromWindow();
    }
  }
  
  public void notifyFirstScreenTime(long paramLong1, long paramLong2)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onFirstScreenTime(paramLong1, paramLong2);
    }
  }
  
  public void notifyMemoryPressure(int paramInt)
  {
    this.mProvider.getExtension().notifyMemoryPressure(paramInt);
  }
  
  public void notifyOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
    }
  }
  
  public void notifyPageFinished(final String paramString)
  {
    Object localObject = this.mObserverList.iterator();
    while (((Iterator)localObject).hasNext()) {
      ((WebViewObserver)((Iterator)localObject).next()).onPageFinished(paramString);
    }
    if (this.needUploadPlatformType)
    {
      if (SmttServiceProxy.getInstance().isPlatformTypeReportEnabled()) {
        uploadPlatformInfoToBeaconIfNeed();
      }
      this.needUploadPlatformType = false;
    }
    if ((paramString != null) && (paramString.contains("mtt_in_readmode=1")))
    {
      paramString = this.mReadModeManager;
      if (paramString != null) {
        paramString.a(this);
      }
    }
    paramString = getWebViewClientExtension();
    if ((paramString != null) && (ReadModeManager.a()) && (getUrl() != null) && (getUrl().startsWith("http")) && (!getUrl().contains("mtt_in_readmode=1")))
    {
      localObject = this.mReadModeLastPreCheckUrl;
      if ((localObject != null) && (((String)localObject).equals(getUrl())))
      {
        if (this.mReadModeLastPreCheckResult == true) {
          paramString.onSupportReadMode();
        }
      }
      else
      {
        this.mReadModeLastPreCheckUrl = getUrl();
        this.mReadModeLastPreCheckResult = false;
        paramString = new android.webkit.ValueCallback()
        {
          public void onReceiveValue(Boolean paramAnonymousBoolean)
          {
            if ((paramAnonymousBoolean != null) && (paramAnonymousBoolean.booleanValue() == true) && (TencentWebViewProxy.this.mReadModeLastPreCheckUrl != null) && (TencentWebViewProxy.this.mReadModeLastPreCheckUrl.equals(TencentWebViewProxy.this.getUrl())))
            {
              TencentWebViewProxy.access$402(TencentWebViewProxy.this, true);
              paramString.onSupportReadMode();
            }
          }
        };
        canEnterReadMode(ReadModeManager.e.b, paramString);
      }
    }
  }
  
  public void notifyPageStarted(String paramString)
  {
    try
    {
      checkBarInit();
      if (this.mInspectorServiceListener == null)
      {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.intent.action.TBS_DEBUG");
        localIntentFilter.addDataScheme("tbs_inspector");
        this.mInspectorServiceListener = new InspectorServiceListener(null);
        X5ApiCompatibilityUtils.x5RegisterReceiver(this.mContext.getApplicationContext(), this.mInspectorServiceListener, localIntentFilter);
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onPageStarted(paramString);
    }
  }
  
  public void notifyQProxyResponseReceived(String paramString)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onQProxyResponseReceived(paramString);
    }
  }
  
  public void notifyReceivedError(int paramInt, String paramString1, String paramString2)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onReceivedError(paramInt, paramString1, paramString2);
    }
  }
  
  public void notifyReceivedError2(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwContentsClient.AwWebResourceError paramAwWebResourceError)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onReceivedError2(paramAwWebResourceRequest, paramAwWebResourceError);
    }
  }
  
  public void notifyReceivedHttpError(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onReceivedHttpError(paramAwWebResourceRequest, paramAwWebResourceResponse);
    }
  }
  
  public void notifyScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public void notifyUpdateScrollState(AwScrollOffsetManager paramAwScrollOffsetManager, int paramInt1, int paramInt2)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onUpdateScrollState(paramAwScrollOffsetManager, paramInt1, paramInt2);
    }
  }
  
  public void notifyWebViewCreated(TencentWebViewProxy paramTencentWebViewProxy)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onWebViewCreated(paramTencentWebViewProxy);
    }
  }
  
  public void notifyWebViewDestoryed()
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onWebViewDestroyed(this.mContext);
    }
  }
  
  public void notifyWebViewPaused()
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onWebViewPaused(this.mContext);
    }
  }
  
  public void notifyWebViewVisibilityChanged(View paramView, int paramInt)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onWebViewVisibilityChanged(this.mContext, paramView, paramInt);
    }
  }
  
  public void notifyWindowVisibilityChanged(int paramInt)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onWindowVisibilityChanged(paramInt);
    }
  }
  
  public void onAppExit()
  {
    checkThread();
    this.mProvider.getExtension().onAppExit();
    com.tencent.smtt.webkit.h5video.d.a(this);
    SmttServiceProxy.getInstance().onWebViewAppExit();
  }
  
  public void onBrowserControlsChanged(int paramInt)
  {
    AdWebViewController localAdWebViewController = this.mAdWebViewController;
    if (localAdWebViewController != null) {
      localAdWebViewController.onBrowserControlsChanged(paramInt);
    }
  }
  
  public void onDayOrNightChanged(boolean paramBoolean)
  {
    if (!(this instanceof AdWebView))
    {
      AdWebViewController localAdWebViewController = this.mAdWebViewController;
      if (localAdWebViewController == null) {
        return;
      }
      localAdWebViewController.onDayOrNightChanged(paramBoolean);
      return;
    }
  }
  
  public void onDetectSpecialSite(String paramString, int paramInt)
  {
    Iterator localIterator = this.mObserverList.iterator();
    while (localIterator.hasNext()) {
      ((WebViewObserver)localIterator.next()).onDetectSpecialSite(paramString, paramInt);
    }
  }
  
  public boolean onDownloadStartCustom(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte, TencentWebViewProxy paramTencentWebViewProxy, com.tencent.tbs.core.webkit.DownloadListener paramDownloadListener, String paramString9)
  {
    Iterator localIterator = this.mObserverList.iterator();
    boolean bool = false;
    while (localIterator.hasNext()) {
      bool |= ((WebViewObserver)localIterator.next()).onDownloadStartCustom(paramString1, paramString2, paramString3, paramString4, paramLong, paramString5, paramString6, paramString7, paramString8, paramArrayOfByte, paramTencentWebViewProxy, paramDownloadListener, paramString9);
    }
    return bool;
  }
  
  public void onFingerSearchResult(String paramString, int paramInt1, int paramInt2)
  {
    this.mProvider.getExtension().onFingerSearchResult(paramString, paramInt1, paramInt2);
  }
  
  public void onFlingScrollBegin(int paramInt1, int paramInt2, int paramInt3) {}
  
  public abstract void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
  
  public void onPageTransFormationSettingChanged(boolean paramBoolean) {}
  
  public void onPause()
  {
    this.mProvider.getExtension().stopMainThreadBlockedDetect();
    this.mWebView.onPause();
    MemoryChecker.stopTimerCheck();
  }
  
  public void onPauseActiveDomObject() {}
  
  public void onPicModleChanged(int paramInt)
  {
    if (!(this instanceof AdWebView))
    {
      AdWebViewController localAdWebViewController = this.mAdWebViewController;
      if (localAdWebViewController == null) {
        return;
      }
      localAdWebViewController.onPicModleChanged(paramInt);
      return;
    }
  }
  
  public void onProgressChanged(int paramInt)
  {
    if (!(this instanceof AdWebView))
    {
      AdWebViewController localAdWebViewController = this.mAdWebViewController;
      if (localAdWebViewController == null) {
        return;
      }
      localAdWebViewController.onProgressChanged(paramInt);
      return;
    }
  }
  
  public void onReadModePreReadFinished()
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = getWebViewClientExtension();
    if (localIX5WebViewClientExtension != null) {
      localIX5WebViewClientExtension.onPreReadFinished();
    }
  }
  
  public void onResume()
  {
    this.mWebView.onResume();
    MemoryChecker.resumeTimerCheck();
  }
  
  public void onResumeActiveDomObject() {}
  
  public boolean overlayHorizontalScrollbar()
  {
    return this.mWebView.overlayHorizontalScrollbar();
  }
  
  public boolean overlayVerticalScrollbar()
  {
    return this.mWebView.overlayVerticalScrollbar();
  }
  
  public boolean pageDown(boolean paramBoolean)
  {
    return this.mWebView.pageDown(paramBoolean);
  }
  
  public boolean pageUp(boolean paramBoolean)
  {
    return this.mWebView.pageUp(paramBoolean);
  }
  
  public void pasteFromClipboard(CharSequence paramCharSequence)
  {
    checkThread();
    this.mProvider.getExtension().pasteFromClipboard(paramCharSequence);
  }
  
  public void pauseAudio()
  {
    this.mProvider.getExtension().pauseAudio();
  }
  
  public void pauseTimerForPendingLitePlayExit() {}
  
  public void pauseTimers()
  {
    this.mWebView.pauseTimers();
  }
  
  public void playAudio()
  {
    this.mProvider.getExtension().playAudio();
  }
  
  public void postUrl(String paramString, byte[] paramArrayOfByte)
  {
    this.mWebView.postUrl(paramString, paramArrayOfByte);
  }
  
  public void preConnectQProxy() {}
  
  public void pruneMemoryCache()
  {
    this.mProvider.getExtension().pruneMemoryCache();
  }
  
  public void refreshPlugins(boolean paramBoolean)
  {
    this.mWebView.refreshPlugins(paramBoolean);
  }
  
  public void reload()
  {
    this.mWebView.reload();
  }
  
  @UsedByReflection("X5JsHelper.java")
  public void reloadCustomMetaData()
  {
    this.mProvider.getExtension().reloadCustomMetaData();
  }
  
  public void removeHistoryItem(int paramInt)
  {
    this.mProvider.getExtension().removeHistoryItem(paramInt);
  }
  
  public void removeJavascriptInterface(String paramString)
  {
    this.mWebView.removeJavascriptInterface(paramString);
  }
  
  public void removeObserver(WebViewObserver paramWebViewObserver)
  {
    this.mObserverList.remove(paramWebViewObserver);
  }
  
  public void removeShownTips()
  {
    if (this.mFpsView != null)
    {
      getRealWebView().removeView(this.mFpsView);
      this.mFpsView = null;
    }
    if (this.mARTypeNameView != null)
    {
      getRealWebView().removeView(this.mARTypeNameView);
      this.mARTypeNameView = null;
    }
  }
  
  @UsedByReflection("sdk.WebView")
  public void removeViewForce(s.a parama)
  {
    if (parama != null) {
      parama.b();
    }
  }
  
  public void replaceAllInputText(String paramString)
  {
    this.mProvider.getExtension().replaceAllInputText(paramString);
  }
  
  public void replaceTextfieldText(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4, boolean paramBoolean) {}
  
  public void replyListBox(int paramInt)
  {
    this.mProvider.getExtension().replyListBox(paramInt);
  }
  
  public void replyMultiListBox(int paramInt, boolean[] paramArrayOfBoolean)
  {
    this.mProvider.getExtension().replyMultiListBox(paramInt, paramArrayOfBoolean);
  }
  
  public boolean requestFocusForInputNode(int paramInt)
  {
    return this.mProvider.getExtension().requestFocusForInputNode(paramInt);
  }
  
  public void requestFocusNodeHref(Message paramMessage)
  {
    this.mWebView.requestFocusNodeHref(paramMessage);
  }
  
  public void requestImageRef(Message paramMessage)
  {
    this.mWebView.requestImageRef(paramMessage);
  }
  
  public boolean restorePicture(Bundle paramBundle, File paramFile)
  {
    return this.mWebView.restorePicture(paramBundle, paramFile);
  }
  
  public IX5WebBackForwardList restoreState(Bundle paramBundle)
  {
    checkThread();
    return this.mProvider.restoreStateTencent(paramBundle);
  }
  
  public void resumeTimers()
  {
    this.mWebView.resumeTimers();
  }
  
  public void retrieveFingerSearchContext(int paramInt)
  {
    this.mProvider.getExtension().retrieveFingerSearchContext(paramInt);
  }
  
  public void savePassword(String paramString1, String paramString2, String paramString3)
  {
    this.mWebView.savePassword(paramString1, paramString2, paramString3);
  }
  
  public boolean savePicture(Bundle paramBundle, File paramFile)
  {
    return this.mWebView.savePicture(paramBundle, paramFile);
  }
  
  public IX5WebBackForwardList saveState(Bundle paramBundle)
  {
    checkThread();
    return this.mProvider.saveStateTencent(paramBundle);
  }
  
  @UsedByReflection("X5JsHelper.java")
  public void saveWebArchive(String paramString)
  {
    this.mWebView.saveWebArchive(paramString);
  }
  
  @UsedByReflection("X5JsHelper.java")
  public void saveWebArchive(String paramString, boolean paramBoolean, final android.webkit.ValueCallback<String> paramValueCallback)
  {
    checkThread();
    TencentWebViewProvider localTencentWebViewProvider = this.mProvider;
    if (paramValueCallback == null) {
      paramValueCallback = null;
    } else {
      paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
      {
        public void onReceiveValue(String paramAnonymousString)
        {
          paramValueCallback.onReceiveValue(paramAnonymousString);
        }
      };
    }
    localTencentWebViewProvider.saveWebArchive(paramString, paramBoolean, paramValueCallback);
  }
  
  public void scrollBy(int paramInt1, int paramInt2)
  {
    this.mWebView.scrollBy(paramInt1, paramInt2);
  }
  
  public void scrollTo(int paramInt1, int paramInt2)
  {
    this.mWebView.scrollTo(paramInt1, paramInt2);
  }
  
  public int selectionType()
  {
    checkThread();
    return this.mProvider.getExtension().selectionType();
  }
  
  public void sendNeverRememberMsg(String paramString1, String paramString2, String paramString3, Message paramMessage) {}
  
  public void sendPluginDrawMsg(SurfaceView paramSurfaceView) {}
  
  public void sendRememberMsg(String paramString1, String paramString2, String paramString3, Message paramMessage) {}
  
  public void sendRememberMsg(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.mProvider.getExtension().sendRememberMsg(paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  public void sendResumeMsg(String paramString1, String paramString2, String paramString3, Message paramMessage) {}
  
  public void setAddressbarDisplayMode(int paramInt, boolean paramBoolean)
  {
    this.mProvider.getExtension().setAddressbarDisplayMode(paramInt, paramBoolean);
  }
  
  public void setAddressbarDisplayMode(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mProvider.getExtension().setAddressbarDisplayMode(paramInt, paramBoolean1, paramBoolean2);
  }
  
  @UsedByReflection("X5JsHelper.java")
  public void setAutoPlayNextVideo(String paramString, boolean paramBoolean)
  {
    this.mAutoPlayNextVideoUrl = paramString;
    this.mAutoPlayNextVideoFlag = paramBoolean;
  }
  
  public void setBackFromSystem()
  {
    checkThread();
    this.mProvider.getExtension().setBackFromSystem();
  }
  
  public void setBackgroundColor(int paramInt)
  {
    this.mWebView.setBackgroundColor(paramInt);
  }
  
  public void setCertificate(SslCertificate paramSslCertificate)
  {
    this.mWebView.setCertificate(paramSslCertificate);
  }
  
  public void setContext(Context paramContext)
  {
    this.mContext = paramContext;
    try
    {
      Field localField = View.class.getDeclaredField("mContext");
      localField.setAccessible(true);
      localField.set(this.mWebView, paramContext);
      return;
    }
    catch (IllegalAccessException paramContext)
    {
      paramContext.printStackTrace();
      return;
    }
    catch (NoSuchFieldException paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public void setDisableDrawingWhileLosingFocus(boolean paramBoolean) {}
  
  public void setDownloadFileInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.mFileInfoName = paramString1;
    this.mFileInfoUrl = paramString2;
    this.mFileInfoSize = paramString3;
    this.mFileInfoCookie = paramString4;
    this.mFileInfoMimeType = paramString5;
  }
  
  public void setDownloadInterceptJsApiInjected()
  {
    this.isDownloadInterceptJsApiInjected = true;
  }
  
  public void setDownloadListener(com.tencent.smtt.export.external.interfaces.DownloadListener paramDownloadListener)
  {
    checkThread();
    TencentWebViewProvider localTencentWebViewProvider = this.mProvider;
    if (paramDownloadListener == null) {
      paramDownloadListener = null;
    } else {
      paramDownloadListener = new TencentDownloadListener(paramDownloadListener);
    }
    localTencentWebViewProvider.setDownloadListener(paramDownloadListener);
  }
  
  public void setDownloadListenerExtension(DownloadListenerExtension paramDownloadListenerExtension)
  {
    checkThread();
    this.mProvider.getExtension().setDownLoadListenerExtension(paramDownloadListenerExtension);
  }
  
  public void setDrawWithBuffer(boolean paramBoolean) {}
  
  public void setEnableAutoPageDiscarding(boolean paramBoolean)
  {
    this.mProvider.getExtension().setEnableAutoPageDiscarding(paramBoolean);
  }
  
  public void setEnableAutoPageRestoration(boolean paramBoolean)
  {
    this.mProvider.getExtension().setEnableAutoPageRestoration(paramBoolean);
  }
  
  public void setExplorerInfo(int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    this.mCharCountInBody = paramInt1;
    this.mCountVideo = paramInt2;
    this.mCountImage = paramInt3;
    this.mTitle = paramString;
  }
  
  public void setEyeShieldMode(boolean paramBoolean, int paramInt)
  {
    this.mProvider.getExtension().setEyeShieldMode(paramBoolean, paramInt);
  }
  
  public void setFindListener(final IX5WebViewBase.FindListener paramFindListener)
  {
    checkThread();
    TencentWebViewProvider localTencentWebViewProvider = this.mProvider;
    if (paramFindListener == null) {
      paramFindListener = null;
    } else {
      paramFindListener = new WebView.FindListener()
      {
        public void onFindResultReceived(int paramAnonymousInt1, int paramAnonymousInt2, boolean paramAnonymousBoolean)
        {
          paramFindListener.onFindResultReceived(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousBoolean);
        }
      };
    }
    localTencentWebViewProvider.setFindListener(paramFindListener);
  }
  
  public void setH5MediaSourceProxy(com.tencent.smtt.webkit.h5video.c paramc)
  {
    this.mH5MediaSourceProxy = paramc;
  }
  
  public void setH5VideoProxy(com.tencent.smtt.webkit.h5video.d paramd)
  {
    this.mH5VideoProxy = paramd;
  }
  
  public void setHorizontalScrollbarOverlay(boolean paramBoolean)
  {
    this.mWebView.setHorizontalScrollbarOverlay(paramBoolean);
  }
  
  public void setHttpAuthUsernamePassword(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.mWebView.setHttpAuthUsernamePassword(paramString1, paramString2, paramString3, paramString4);
  }
  
  public void setInitialScale(int paramInt)
  {
    this.mWebView.setInitialScale(paramInt);
  }
  
  public boolean setIsCreatedFullScreenSurfaceView()
  {
    this.mFullScreenSurfaceView = new i(this, true);
    return this.mFullScreenSurfaceView.a();
  }
  
  public void setIsForVideoSniff(boolean paramBoolean)
  {
    this.mForVideoSniff = paramBoolean;
  }
  
  public void setMapTrackballToArrowKeys(boolean paramBoolean)
  {
    this.mWebView.setMapTrackballToArrowKeys(paramBoolean);
  }
  
  public void setNeeedReportWIFIGuideFromVideo(boolean paramBoolean)
  {
    this.mNeeedReportWIFIGuideFromVideo = paramBoolean;
  }
  
  public void setNetworkAvailable(boolean paramBoolean)
  {
    this.mWebView.setNetworkAvailable(paramBoolean);
  }
  
  public void setNewOpenedWebView(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.mNewOpenedWebView = new WeakReference(paramTencentWebViewProxy);
    if (paramTencentWebViewProxy != null) {
      paramTencentWebViewProxy.getRealWebView().getSettings().setUserAgentString(getRealWebView().getSettings().getUserAgentString());
    }
  }
  
  public void setOverScrollParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3)
  {
    this.mXOverScrollMode = paramInt1;
    this.mYOverScrollMode = paramInt4;
    this.mProvider.getExtension().setOverScrollParams(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramDrawable1, paramDrawable2, paramDrawable3);
  }
  
  @Deprecated
  public void setPictureListener(final IX5WebViewBase.PictureListener paramPictureListener)
  {
    checkThread();
    TencentWebViewProvider localTencentWebViewProvider = this.mProvider;
    if (paramPictureListener == null) {
      paramPictureListener = null;
    } else {
      paramPictureListener = new WebView.PictureListener()
      {
        @Deprecated
        public void onNewPicture(WebView paramAnonymousWebView, Picture paramAnonymousPicture)
        {
          paramPictureListener.onNewPicture(TencentWebViewProxy.this, paramAnonymousPicture, false);
        }
      };
    }
    localTencentWebViewProvider.setPictureListener(paramPictureListener);
  }
  
  public void setQQBrowserClient(IX5QQBrowserClient paramIX5QQBrowserClient)
  {
    checkThread();
    this.mProvider.getExtension().setQQBrowserClient(paramIX5QQBrowserClient);
  }
  
  public void setRealWebView(WebView paramWebView)
  {
    this.mWebView = ((InnerWebView)paramWebView);
  }
  
  public void setSelectListener(ISelectionInterface paramISelectionInterface)
  {
    checkThread();
    this.mProvider.getExtension().setSelectListener(paramISelectionInterface);
  }
  
  public void setSharedVideoTime(int paramInt)
  {
    this.mSharedVideoTime = paramInt;
  }
  
  @UsedByReflection("X5JsHelper.java")
  public void setSniffVideoInfo(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    this.mSniffVideoRefer = paramString1;
    this.mSniffVideoID = paramInt;
    this.mSniffVideoPoster = paramString2;
    this.mSniffVideoTitle = paramString3;
    this.mSniffVideoJumpCount = 0;
  }
  
  public void setSoftKeyBoardIsShowing(boolean paramBoolean)
  {
    this.mSoftKeyBoardShowing = paramBoolean;
  }
  
  public void setVerticalScrollbarOverlay(boolean paramBoolean)
  {
    this.mWebView.setVerticalScrollbarOverlay(paramBoolean);
  }
  
  public void setWebBackForwardListClient(IX5WebBackForwardListClient paramIX5WebBackForwardListClient)
  {
    this.mProvider.getExtension().setWebBackForwardListClient(paramIX5WebBackForwardListClient);
  }
  
  public void setWebChromeClient(IX5WebChromeClient paramIX5WebChromeClient)
  {
    checkThread();
    TencentWebViewProvider localTencentWebViewProvider = this.mProvider;
    if (paramIX5WebChromeClient == null) {
      paramIX5WebChromeClient = null;
    } else {
      paramIX5WebChromeClient = new TencentWebChromeClient(this, paramIX5WebChromeClient);
    }
    localTencentWebViewProvider.setWebChromeClient(paramIX5WebChromeClient);
  }
  
  public void setWebChromeClientExtension(IX5WebChromeClientExtension paramIX5WebChromeClientExtension)
  {
    checkThread();
    this.mProvider.getExtension().setWebChromeClientExtension(paramIX5WebChromeClientExtension);
  }
  
  public void setWebViewClient(IX5WebViewClient paramIX5WebViewClient)
  {
    checkThread();
    TencentWebViewProvider localTencentWebViewProvider = this.mProvider;
    if (paramIX5WebViewClient == null) {
      paramIX5WebViewClient = null;
    } else {
      paramIX5WebViewClient = new TencentWebViewClient(this, paramIX5WebViewClient);
    }
    localTencentWebViewProvider.setWebViewClient(paramIX5WebViewClient);
  }
  
  public void setWebViewClient(WebViewClient paramWebViewClient)
  {
    checkThread();
    this.mProvider.setWebViewClient(paramWebViewClient);
  }
  
  public void setWebViewClientExtension(IX5WebViewClientExtension paramIX5WebViewClientExtension)
  {
    checkThread();
    this.mProvider.getExtension().setWebViewClientExtension(paramIX5WebViewClientExtension);
  }
  
  public boolean shouldFitScreenLayout()
  {
    return false;
  }
  
  public boolean shouldOverrideUrlLoadingForReadMode(String paramString)
  {
    if (this.mReadModeManager != null)
    {
      if (paramString == null) {
        return false;
      }
      if (paramString.contains("mtt_in_readmode=1"))
      {
        navigateToReadModePage(ReadModeManager.b(paramString, "mtt_in_readmode=1"), ReadModeManager.e.a);
        return true;
      }
      if (matchReadModeCurrentHost(paramString))
      {
        if (getUrl().contains("mtt_in_readmode=1")) {
          return false;
        }
        navigateToReadModePage(paramString, ReadModeManager.e.d);
        return true;
      }
      return false;
    }
    return false;
  }
  
  public boolean showFindDialog(String paramString, boolean paramBoolean)
  {
    return this.mWebView.showFindDialog(paramString, paramBoolean);
  }
  
  public void showImage(int paramInt1, int paramInt2)
  {
    this.mProvider.getExtension().showImage(paramInt1, paramInt2);
  }
  
  public void showInspectorHint()
  {
    if (this.mInspectHintView != null) {
      return;
    }
    WindowManager localWindowManager = (WindowManager)this.mContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    int i = (int)localDisplayMetrics.density;
    this.mInspectHintView = new TextView(this.mContext);
    this.mInspectHintView.setTextColor(-65536);
    this.mInspectHintView.setTextSize(2, 20.0F);
    this.mInspectHintView.setGravity(3);
    this.mInspectHintView.setPadding(i * 20, i * 40, 0, 0);
    this.mInspectHintView.setText("开发调试版本");
    getRealWebView().addView(this.mInspectHintView, new AbsoluteLayout.LayoutParams(-1, -1, 0, 44));
  }
  
  public void showRecognitionFps(int paramInt)
  {
    if (this.mFpsView == null)
    {
      this.mFpsView = new TextView(getContext());
      localObject = new RelativeLayout.LayoutParams(-1, -2);
      this.mFpsView.setLayoutParams((ViewGroup.LayoutParams)localObject);
      this.mFpsView.setTextColor(-65536);
      this.mFpsView.setTextSize(2, 20.0F);
      this.mFpsView.setGravity(5);
      int i = SUtiles.dip2px(getContext().getApplicationContext(), 20.0F);
      localObject = this.mARTypeNameView;
      if (localObject != null) {
        i = ((TextView)localObject).getHeight() + SUtiles.dip2px(getContext().getApplicationContext(), 20.0F);
      }
      this.mFpsView.setPadding(0, i, SUtiles.dip2px(getContext().getApplicationContext(), 20.0F), 0);
      getRealWebView().addView(this.mFpsView);
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("识别帧率：");
    ((StringBuilder)localObject).append(String.valueOf(paramInt));
    localObject = ((StringBuilder)localObject).toString();
    this.mFpsView.setText((CharSequence)localObject);
  }
  
  public void showWebAREngineTypeTips(String paramString)
  {
    if (this.mARTypeNameView == null)
    {
      this.mARTypeNameView = new TextView(getContext());
      localObject = new RelativeLayout.LayoutParams(-1, -2);
      this.mARTypeNameView.setLayoutParams((ViewGroup.LayoutParams)localObject);
      this.mARTypeNameView.setTextColor(-65536);
      this.mARTypeNameView.setTextSize(2, 20.0F);
      this.mARTypeNameView.setPadding(0, SUtiles.dip2px(getContext().getApplicationContext(), 20.0F), SUtiles.dip2px(getContext().getApplicationContext(), 20.0F), 0);
      this.mARTypeNameView.setGravity(5);
      getRealWebView().addView(this.mARTypeNameView);
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("识别引擎：");
    ((StringBuilder)localObject).append(paramString);
    paramString = ((StringBuilder)localObject).toString();
    this.mARTypeNameView.setText(paramString);
  }
  
  public Drawable snapshot(int paramInt, boolean paramBoolean)
  {
    return this.mProvider.getExtension().snapshot(paramInt, paramBoolean);
  }
  
  public void snapshotVisible(Canvas paramCanvas, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.mProvider.getExtension().snapshotVisible(paramCanvas, paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public void snapshotVisibleWithBitmap(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, float paramFloat1, float paramFloat2)
  {
    this.mProvider.getExtension().snapshotVisibleWithBitmap(paramBitmap, paramBoolean1, paramBoolean2, paramBoolean3, paramFloat1, paramFloat2);
    if (!(this instanceof AdWebView))
    {
      AdWebViewController localAdWebViewController = this.mAdWebViewController;
      if (localAdWebViewController != null) {
        localAdWebViewController.snapshotVisibleWithBitmap(paramBitmap, paramBoolean1, paramBoolean2, paramBoolean3, paramFloat1, paramFloat2);
      }
    }
  }
  
  public void snapshotVisibleWithBitmap(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, float paramFloat1, float paramFloat2, Runnable paramRunnable)
  {
    this.mProvider.getExtension().snapshotVisibleWithBitmap(paramBitmap, paramBoolean1, paramBoolean2, paramBoolean3, paramFloat1, paramFloat2, paramRunnable);
  }
  
  public void snapshotVisibleWithBitmapThreaded(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, float paramFloat1, float paramFloat2, int paramInt)
  {
    this.mProvider.getExtension().snapshotVisibleWithBitmapThreaded(paramBitmap, paramBoolean1, paramBoolean2, paramBoolean3, paramFloat1, paramFloat2, paramInt);
  }
  
  public void snapshotWholePage(Canvas paramCanvas, boolean paramBoolean)
  {
    this.mProvider.getExtension().snapshotWholePage(paramCanvas, paramBoolean);
  }
  
  public void snapshotWholePage(Canvas paramCanvas, boolean paramBoolean, Runnable paramRunnable)
  {
    this.mProvider.getExtension().snapshotWholePage(paramCanvas, paramBoolean, paramRunnable);
  }
  
  public void stopLoading()
  {
    this.mWebView.stopLoading();
  }
  
  public void storeDownloadInterceptInfo(Map<String, String> paramMap)
  {
    this.downloadInterceptInfo = paramMap;
  }
  
  public boolean superOnTouchEvent(MotionEvent paramMotionEvent)
  {
    return this.mWebView.superOnTouchEvent(paramMotionEvent);
  }
  
  public boolean superOverScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    return this.mWebView.superOverScrollBy(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
  }
  
  public boolean super_awakenScrollBars()
  {
    return this.mWebView.super_awakenScrollBars();
  }
  
  protected boolean super_awakenScrollBars(int paramInt)
  {
    return this.mWebView.super_awakenScrollBars(paramInt);
  }
  
  public boolean super_awakenScrollBars(int paramInt, boolean paramBoolean)
  {
    return this.mWebView.super_awakenScrollBars(paramInt, paramBoolean);
  }
  
  @UsedByReflection("sdk.WebView")
  public void super_computeScroll()
  {
    this.mWebView.super_computeScroll();
  }
  
  @UsedByReflection("sdk.WebView")
  public boolean super_dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    return this.mWebView.super_dispatchTouchEvent(paramMotionEvent);
  }
  
  public void super_draw(Canvas paramCanvas)
  {
    this.mWebView.super_draw(paramCanvas);
  }
  
  public boolean super_drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    return this.mWebView.super_drawChild(paramCanvas, paramView, paramLong);
  }
  
  public void super_invalidate()
  {
    this.mWebView.invalidate();
  }
  
  @UsedByReflection("sdk.WebView")
  public boolean super_onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    return this.mWebView.super_onInterceptTouchEvent(paramMotionEvent);
  }
  
  protected void super_onMeasure(int paramInt1, int paramInt2)
  {
    this.mWebView.super_onMeasure(paramInt1, paramInt2);
  }
  
  @UsedByReflection("sdk.WebView")
  public void super_onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mWebView.super_onOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
  }
  
  @UsedByReflection("sdk.WebView")
  public void super_onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mWebView.super_onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void super_setHorizontalScrollBarEnabled(boolean paramBoolean)
  {
    this.mWebView.super_setHorizontalScrollBarEnabled(paramBoolean);
  }
  
  public void super_setOnLongClickListener(View.OnLongClickListener paramOnLongClickListener)
  {
    this.mWebView.super_setOnLongClickListener(paramOnLongClickListener);
  }
  
  public void super_setVerticalScrollBarEnabled(boolean paramBoolean)
  {
    this.mWebView.super_setVerticalScrollBarEnabled(paramBoolean);
  }
  
  public void suspendPageScroll(boolean paramBoolean)
  {
    this.mProvider.getExtension().suspendPageScroll(paramBoolean);
  }
  
  public void trimMemory(int paramInt)
  {
    checkThread();
    this.mProvider.getExtension().trimMemory(paramInt);
  }
  
  public void updateContext(Context paramContext) {}
  
  public void updateSelectionPosition() {}
  
  public boolean videoModeDisabled()
  {
    return this.mProvider.getExtension().hasWebAR();
  }
  
  public boolean zoomIn()
  {
    return this.mWebView.zoomIn();
  }
  
  public boolean zoomOut()
  {
    return this.mWebView.zoomOut();
  }
  
  public static class HitTestResult
    extends IX5WebViewBase.HitTestResult
  {
    private WebView.HitTestResult mHitTestResult = new WebView.HitTestResult();
    
    public HitTestResult(WebView.HitTestResult paramHitTestResult)
    {
      this.mHitTestResult = paramHitTestResult;
    }
    
    public String getExtra()
    {
      return this.mHitTestResult.getExtra();
    }
    
    public int getType()
    {
      return this.mHitTestResult.getType();
    }
    
    public void setExtra(String paramString)
    {
      this.mHitTestResult.setExtra(paramString);
    }
    
    public void setType(int paramInt)
    {
      this.mHitTestResult.setType(paramInt);
    }
  }
  
  @UsedByReflection("sdk.WebView")
  public class InnerWebView
    extends WebView
  {
    public static final String SCHEME_FAKE_TEL = "faketel:";
    private FileChooserFirstScreenView mFileChooserFirstScreenView;
    private String mImagesPath = null;
    private boolean mMonitorCamera = false;
    private long mTimeCallCamera = -1L;
    com.tencent.tbs.core.webkit.ValueCallback<Uri[]> mValueCallback = null;
    
    public InnerWebView(Context paramContext)
    {
      super();
    }
    
    public InnerWebView(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public InnerWebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      super(paramAttributeSet, paramInt);
    }
    
    public InnerWebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
    {
      super(paramAttributeSet, paramInt1, paramInt2);
    }
    
    protected InnerWebView(AttributeSet paramAttributeSet, int paramInt1, int paramInt2, Map<String, Object> paramMap, boolean paramBoolean)
    {
      super(paramInt1, paramInt2, paramMap, paramBoolean, bool);
    }
    
    protected InnerWebView(AttributeSet paramAttributeSet, int paramInt, Map<String, Object> paramMap, boolean paramBoolean)
    {
      super(paramInt, paramMap, paramBoolean, bool);
    }
    
    public InnerWebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt, boolean paramBoolean)
    {
      super(paramAttributeSet, paramInt, paramBoolean);
    }
    
    @UsedByReflection("sdk.WebView")
    public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
    {
      if (Build.VERSION.SDK_INT < 11)
      {
        if ((paramLayoutParams instanceof AbsoluteLayout.LayoutParams))
        {
          AbsoluteLayout.LayoutParams localLayoutParams = (AbsoluteLayout.LayoutParams)paramLayoutParams;
          paramView.setTag(Integer.valueOf(localLayoutParams.y));
          localLayoutParams.y += getVisibleTitleHeight();
        }
      }
      else {
        paramView.setTranslationY(getVisibleTitleHeight());
      }
      super.addView(paramView, paramInt, paramLayoutParams);
    }
    
    protected boolean awakenScrollBars()
    {
      return ((X5WebViewAdapter)getOuterProxy()).awakenScrollBars();
    }
    
    protected boolean awakenScrollBars(int paramInt)
    {
      return ((X5WebViewAdapter)getOuterProxy()).awakenScrollBars(paramInt);
    }
    
    protected boolean awakenScrollBars(int paramInt, boolean paramBoolean)
    {
      return ((X5WebViewAdapter)getOuterProxy()).awakenScrollBars(paramInt, paramBoolean);
    }
    
    public boolean canGoBack()
    {
      if (TencentWebViewProxy.this.isPluginFullScreen()) {
        return true;
      }
      return super.canGoBack();
    }
    
    public boolean canGoForward()
    {
      return (super.canGoForward()) || (TencentWebViewProxy.this.isReadModeCanGoForward()) || (TencentWebViewProxy.this.mProvider.getExtension().isPreReadCanGoForward());
    }
    
    @Deprecated
    public Picture capturePicture()
    {
      checkThread();
      return TencentWebViewProxy.this.mProvider.getExtension().capturePicture();
    }
    
    protected void checkThread()
    {
      try
      {
        super.checkThread();
        if (Looper.myLooper() != Looper.getMainLooper()) {
          TencentWebViewProxy.reportUnavailableThreadMessage(Log.getStackTraceString(new Throwable()));
        }
        return;
      }
      catch (Throwable localThrowable) {}
    }
    
    @UsedByReflection("sdk.WebView")
    public int computeHorizontalScrollExtent()
    {
      return super.computeHorizontalScrollExtent();
    }
    
    @UsedByReflection("sdk.WebView")
    public int computeHorizontalScrollOffset()
    {
      return super.computeHorizontalScrollOffset();
    }
    
    @UsedByReflection("sdk.WebView")
    public int computeHorizontalScrollRange()
    {
      return super.computeHorizontalScrollRange();
    }
    
    public void computeScroll()
    {
      ((X5WebViewAdapter)getOuterProxy()).computeScroll();
    }
    
    public int computeVerticalScrollExtent()
    {
      return super.computeVerticalScrollExtent();
    }
    
    public int computeVerticalScrollOffset()
    {
      return super.computeVerticalScrollOffset();
    }
    
    @UsedByReflection("sdk.WebView")
    public int computeVerticalScrollRange()
    {
      return super.computeVerticalScrollRange();
    }
    
    public void destroy()
    {
      X5Log.v("WebView", "WebView destroy() invoke");
      com.tencent.smtt.util.f.a();
      try
      {
        if (TencentWebViewProxy.this.mInspectorServiceListener != null)
        {
          X5ApiCompatibilityUtils.x5UnRegisterReceiver(this.mContext.getApplicationContext(), TencentWebViewProxy.this.mInspectorServiceListener);
          TencentWebViewProxy.access$802(TencentWebViewProxy.this, null);
        }
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
      super.destroy();
      com.tencent.smtt.webkit.h5video.d.b(TencentWebViewProxy.this);
      TencentWebViewProxy.this.notifyWebViewDestoryed();
      if (TencentWebViewProxy.this.mFullScreenSurfaceView != null)
      {
        TencentWebViewProxy.this.mFullScreenSurfaceView.a();
        TencentWebViewProxy.access$1402(TencentWebViewProxy.this, null);
      }
    }
    
    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      if ((paramKeyEvent.getKeyCode() == 4) && (paramKeyEvent.getAction() == 0))
      {
        FileChooserFirstScreenView localFileChooserFirstScreenView = this.mFileChooserFirstScreenView;
        if (localFileChooserFirstScreenView != null)
        {
          localFileChooserFirstScreenView.onBack();
          return true;
        }
      }
      return super.dispatchKeyEvent(paramKeyEvent);
    }
    
    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
    {
      return ((X5WebViewAdapter)getOuterProxy()).dispatchTouchEvent(paramMotionEvent);
    }
    
    public void draw(Canvas paramCanvas)
    {
      ((X5WebViewAdapter)getOuterProxy()).draw(paramCanvas);
    }
    
    protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
    {
      return ((X5WebViewAdapter)getOuterProxy()).drawChild(paramCanvas, paramView, paramLong);
    }
    
    @UsedByReflection("TbsServiceProxy.java")
    public void evaluateJavascript(String paramString, android.webkit.ValueCallback<String> paramValueCallback)
    {
      getOuterProxy().evaluateJavascript(paramString, paramValueCallback);
    }
    
    public TencentWebViewProxy getOuterProxy()
    {
      return TencentWebViewProxy.this;
    }
    
    public void goBack()
    {
      Object localObject = TencentWebViewProxy.this.getNextUrl(false);
      if (localObject != null)
      {
        localObject = ((String)localObject).toLowerCase();
        if ((localObject != null) && (((String)localObject).startsWith("http://debugx5.qq.com")))
        {
          TencentWebViewProxy.this.handleUrlLoading((String)localObject, null);
          TencentWebViewProxy.this.clearTextEntry();
          return;
        }
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("WebView goBack <");
      ((StringBuilder)localObject).append(TencentWebViewProxy.this.getRealWebView().getUrl());
      ((StringBuilder)localObject).append(">");
      X5Log.d("WebView", ((StringBuilder)localObject).toString());
      super.goBack();
      TencentWebViewProxy.this.clearTextEntry();
    }
    
    public void goBackOrForward(int paramInt)
    {
      if ((paramInt == 1) && (!super.canGoBackOrForward(paramInt)) && (TencentWebViewProxy.this.isReadModeCanGoForward()))
      {
        TencentWebViewProxy.this.readModeGoForward();
        return;
      }
      if ((paramInt == 1) && (TencentWebViewProxy.this.mProvider.getExtension().isPreReadCanGoForward()))
      {
        TencentWebViewProxy.this.mProvider.getExtension().gotoPreRead();
        return;
      }
      TencentWebViewProxy.this.mProvider.getExtension().goBackOrForward(paramInt);
      super.goBackOrForward(paramInt);
    }
    
    public void goForward()
    {
      Object localObject = TencentWebViewProxy.this.getNextUrl(true);
      if (localObject != null)
      {
        localObject = ((String)localObject).toLowerCase();
        if ((localObject != null) && (((String)localObject).startsWith("http://debugx5.qq.com")))
        {
          TencentWebViewProxy.this.handleUrlLoading((String)localObject, null);
          TencentWebViewProxy.this.clearTextEntry();
          return;
        }
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("WebView goForward <");
      ((StringBuilder)localObject).append(TencentWebViewProxy.this.getRealWebView().getUrl());
      ((StringBuilder)localObject).append(">");
      X5Log.d("WebView", ((StringBuilder)localObject).toString());
      if ((!super.canGoForward()) && (TencentWebViewProxy.this.isReadModeCanGoForward()))
      {
        TencentWebViewProxy.this.readModeGoForward();
        return;
      }
      if (TencentWebViewProxy.this.mProvider.getExtension().isPreReadCanGoForward())
      {
        TencentWebViewProxy.this.mProvider.getExtension().gotoPreRead();
        return;
      }
      super.goForward();
      TencentWebViewProxy.this.clearTextEntry();
    }
    
    public void invalidate()
    {
      super.invalidate();
      try
      {
        IX5WebViewClientExtension localIX5WebViewClientExtension = TencentWebViewProxy.this.getWebViewClientExtension();
        Method localMethod = localIX5WebViewClientExtension.getClass().getDeclaredMethod("invalidate", new Class[0]);
        localMethod.setAccessible(true);
        localMethod.invoke(localIX5WebViewClientExtension, null);
        return;
      }
      catch (Throwable localThrowable) {}
    }
    
    @UsedByReflection("jsApiDomainWhiteListManager.java")
    public void loadUrl(String paramString)
    {
      if (TencentWebViewProxy.this.handleUrlLoading(paramString, null)) {
        return;
      }
      super.loadUrl(paramString);
    }
    
    @UsedByReflection("jsApiDomainWhiteListManager.java")
    public void loadUrl(String paramString, Map<String, String> paramMap)
    {
      if (TencentWebViewProxy.this.handleUrlLoading(paramString, paramMap)) {
        return;
      }
      super.loadUrl(paramString, paramMap);
    }
    
    protected void onAttachedToWindow()
    {
      if (TencentWebViewProxy.this.mNativeWidgetManager != null) {
        TencentWebViewProxy.this.mNativeWidgetManager.onAttachedToWindow();
      }
      if ((TencentWebViewProxy.this.getH5VideoProxy() != null) && (TencentWebViewProxy.this.getH5VideoProxy().b())) {
        return;
      }
      TencentWebViewProxy.access$702(TencentWebViewProxy.this, true);
      super.onAttachedToWindow();
      TencentWebViewProxy.this.mProvider.getViewDelegate().onAttachedToWindow();
      TencentWebViewProxy.this.notifyAttachedToWindow();
    }
    
    protected void onDetachedFromWindow()
    {
      if (TencentWebViewProxy.this.mNativeWidgetManager != null) {
        TencentWebViewProxy.this.mNativeWidgetManager.onDetachedFromWindow();
      }
      if ((TencentWebViewProxy.this.getH5VideoProxy() != null) && (TencentWebViewProxy.this.getH5VideoProxy().b())) {
        return;
      }
      TencentWebViewProxy.this.mProvider.getViewDelegate().onDetachedFromWindow();
      super.onDetachedFromWindow();
      SmttServiceProxy.getInstance().onWebViewDetachedFromWindow();
      TencentWebViewProxy.this.notifyDetachedFromWindow();
      try
      {
        if (TencentWebViewProxy.this.mInspectorServiceListener != null)
        {
          X5ApiCompatibilityUtils.x5UnRegisterReceiver(this.mContext.getApplicationContext(), TencentWebViewProxy.this.mInspectorServiceListener);
          TencentWebViewProxy.access$802(TencentWebViewProxy.this, null);
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
    
    protected void onDetachedFromWindowInternal() {}
    
    protected void onDraw(Canvas paramCanvas)
    {
      MttTraceEvent.begin(64, "WebView.onDraw");
      TencentWebViewProxy.this.mProvider.getViewDelegate().onDraw(paramCanvas);
      if (RenderMonitor.b()) {
        RenderMonitor.a(paramCanvas, "X5");
      }
      MttTraceEvent.end(64, "WebView.onDraw");
    }
    
    public void onDrawHorizontalScrollBar(Canvas paramCanvas, Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramDrawable.setBounds(paramInt1, paramInt2 + getVisibleTitleHeight(), paramInt3, paramInt4);
      paramDrawable.draw(paramCanvas);
    }
    
    public void onDrawVerticalScrollBar(Canvas paramCanvas, Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if (getScrollY() < 0) {
        paramDrawable.setBounds(paramInt1, paramInt2 + getVisibleTitleHeight(), paramInt3, paramInt4 + getVisibleTitleHeight());
      } else {
        paramDrawable.setBounds(paramInt1, paramInt2 + getVisibleTitleHeight(), paramInt3, paramInt4);
      }
      paramDrawable.draw(paramCanvas);
    }
    
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
    {
      super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
      paramAccessibilityNodeInfo.setClassName(WebView.class.getName());
      TencentWebViewProxy.this.mProvider.getViewDelegate().onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    }
    
    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
      return ((X5WebViewAdapter)getOuterProxy()).onInterceptTouchEvent(paramMotionEvent);
    }
    
    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
    {
      if ((TencentWebViewProxy.this.mAdWebViewController != null) && (TencentWebViewProxy.this.mAdWebViewController.canShowScreenAdWhenBackEvent()) && (TencentWebViewProxy.this.mAdWebViewController.showScreenAd(getUrl(), true))) {
        return true;
      }
      if ((paramInt == 4) && (TencentWebViewProxy.this.mAdWebViewController != null)) {
        TencentWebViewProxy.this.mAdWebViewController.onBackEvent();
      }
      WebRtcUtils.a(this, paramInt);
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      ((X5WebViewAdapter)getOuterProxy()).onMeasure(paramInt1, paramInt2);
    }
    
    protected void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
    {
      ((X5WebViewAdapter)getOuterProxy()).onOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
    }
    
    public void onPause()
    {
      super.onPause();
      SmttServiceProxy.getInstance().onWebViewPause();
      TencentWebViewProxy.this.setVideoActive(false);
      TencentWebViewProxy.this.notifyWebViewPaused();
    }
    
    public void onResume()
    {
      super.onResume();
      TencentWebViewProxy.this.setVideoActive(true);
    }
    
    protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      ((X5WebViewAdapter)getOuterProxy()).onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      if (TencentWebViewProxy.this.mVelocityTracker == null) {
        TencentWebViewProxy.access$1802(TencentWebViewProxy.this, VelocityTracker.obtain());
      }
      if (TencentWebViewProxy.this.mVelocityTracker != null)
      {
        TencentWebViewProxy.this.mVelocityTracker.addMovement(paramMotionEvent);
        TencentWebViewProxy.this.mVelocityTracker.computeCurrentVelocity(1000);
      }
      TencentWebViewProxy.this.mProvider.getExtension().onTouchEvent(paramMotionEvent);
      return ((X5WebViewAdapter)getOuterProxy()).onTouchEvent(paramMotionEvent);
    }
    
    protected void onVisibilityChanged(View paramView, int paramInt)
    {
      if (X5Log.isEnableLog())
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("WebView onVisibilityChanged visibility ");
        if (paramInt == 0) {
          localObject = " (visible) ";
        } else {
          localObject = " (invisible) ";
        }
        localStringBuilder.append((String)localObject);
        localStringBuilder.append(" this = ");
        localStringBuilder.append(hashCode());
        localStringBuilder.append(" changedView is ");
        localStringBuilder.append(paramView.hashCode());
        localStringBuilder.append(" visiblity is ");
        localStringBuilder.append(paramInt);
        localStringBuilder.append("  url=");
        localStringBuilder.append(getUrl());
        X5Log.d("WebView", localStringBuilder.toString());
      }
      if (paramInt == 4) {
        X5Log.i("WebView", "Options=PhoneHomeTouch;");
      }
      if ((TencentWebViewProxy.this.getH5VideoProxy() != null) && (TencentWebViewProxy.this.getH5VideoProxy().b())) {
        return;
      }
      super.onVisibilityChanged(paramView, paramInt);
      if ((TencentWebViewProxy.this.mProvider != null) && (TencentWebViewProxy.this.mProvider.getViewDelegate() != null)) {
        TencentWebViewProxy.this.mProvider.getViewDelegate().onVisibilityChanged(paramView, paramInt);
      }
      if ((TencentWebViewProxy.this.mttHelper != null) && (paramInt != 0))
      {
        TencentWebViewProxy.this.mttHelper.b();
        TencentWebViewProxy.this.mttHelper.a();
      }
      if ((RenderMonitor.a != null) && (paramInt != 0)) {
        RenderMonitor.a.a();
      }
      Object localObject = TencentWebViewProxy.this;
      boolean bool;
      if (paramInt == 0) {
        bool = true;
      } else {
        bool = false;
      }
      ((TencentWebViewProxy)localObject).setVideoActive(bool);
      TencentWebViewProxy.this.notifyWebViewVisibilityChanged(paramView, paramInt);
    }
    
    public void onWindowFocusChanged(boolean paramBoolean)
    {
      super.onWindowFocusChanged(paramBoolean);
      try
      {
        SmttServiceProxy.getInstance().onWindowFocusChanged(this.mContext, paramBoolean);
        if ((this.mMonitorCamera) && (paramBoolean))
        {
          Object localObject1 = new File(this.mImagesPath);
          if (Long.valueOf(((File)localObject1).lastModified()).longValue() > this.mTimeCallCamera)
          {
            Object localObject2 = new HashMap();
            ((Map)localObject2).put("coreversion", ContextHolder.getInstance().getTbsCoreVersion());
            ((Map)localObject2).put("packageName", this.mContext.getApplicationInfo().packageName);
            SmttServiceProxy.getInstance().upLoadToBeacon("CH0022", (Map)localObject2);
            localObject2 = Uri.fromFile((File)localObject1);
            this.mValueCallback.onReceiveValue(new Uri[] { localObject2 });
            ContentValues localContentValues = new ContentValues();
            localContentValues.put("_data", ((File)localObject1).toString());
            localContentValues.put("description", "save image ---");
            localContentValues.put("mime_type", "image/jpeg");
            getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, localContentValues);
            localObject1 = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", (Uri)localObject2);
            getContext().sendBroadcast((Intent)localObject1);
          }
          else
          {
            localObject1 = new HashMap();
            ((Map)localObject1).put("coreversion", ContextHolder.getInstance().getTbsCoreVersion());
            SmttServiceProxy.getInstance().upLoadToBeacon("CH0023", (Map)localObject1);
            this.mValueCallback.onReceiveValue(null);
          }
          this.mMonitorCamera = false;
        }
        return;
      }
      catch (Throwable localThrowable) {}
    }
    
    protected void onWindowVisibilityChanged(int paramInt)
    {
      if ((TencentWebViewProxy.this.getH5VideoProxy() != null) && (TencentWebViewProxy.this.getH5VideoProxy().b())) {
        return;
      }
      super.onWindowVisibilityChanged(paramInt);
      TencentWebViewProxy.this.mProvider.getViewDelegate().onWindowVisibilityChanged(paramInt);
      TencentWebViewProxy localTencentWebViewProxy = TencentWebViewProxy.this;
      boolean bool;
      if (paramInt == 0) {
        bool = true;
      } else {
        bool = false;
      }
      localTencentWebViewProxy.setVideoActive(bool);
      TencentWebViewProxy.this.notifyWindowVisibilityChanged(paramInt);
      if (paramInt == 0) {
        TencentWebViewProxy.this.mProvider.getExtension().startMainThreadBlockedDetect();
      }
    }
    
    protected boolean overScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
    {
      return ((X5WebViewAdapter)getOuterProxy()).overScrollBy(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
    }
    
    public boolean performLongClick()
    {
      if ((TencentWebViewProxy.this.getH5VideoProxy() != null) && (TencentWebViewProxy.this.getH5VideoProxy().a())) {
        return true;
      }
      return TencentWebViewProxy.this.mProvider.getViewDelegate().performLongClick();
    }
    
    public void reload()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("WebView reload invoked url ");
      localStringBuilder.append(TencentWebViewProxy.this.getRealWebView().getUrl());
      X5Log.d("WebView", localStringBuilder.toString());
      super.reload();
      TencentWebViewProxy.this.mProvider.getExtension().reload();
    }
    
    public void setFileChooserFirstScreenView(FileChooserFirstScreenView paramFileChooserFirstScreenView)
    {
      this.mFileChooserFirstScreenView = paramFileChooserFirstScreenView;
    }
    
    public void setHorizontalScrollBarEnabled(boolean paramBoolean)
    {
      ((X5WebViewAdapter)getOuterProxy()).setHorizontalScrollBarEnabled(paramBoolean);
    }
    
    public void setOnLongClickListener(View.OnLongClickListener paramOnLongClickListener)
    {
      ((X5WebViewAdapter)getOuterProxy()).setOnLongClickListener(paramOnLongClickListener);
    }
    
    public void setScrollBarDefaultDelayBeforeFade(int paramInt)
    {
      ((X5WebViewAdapter)getOuterProxy()).setScrollBarDefaultDelayBeforeFade(paramInt);
    }
    
    public void setScrollBarFadeDuration(int paramInt)
    {
      ((X5WebViewAdapter)getOuterProxy()).setScrollBarFadeDuration(paramInt);
    }
    
    public void setScrollBarSize(int paramInt)
    {
      ((X5WebViewAdapter)getOuterProxy()).setScrollBarSize(paramInt);
    }
    
    public void setScrollbarFadingEnabled(boolean paramBoolean)
    {
      ((X5WebViewAdapter)getOuterProxy()).setScrollBarFadingEnabled(paramBoolean);
    }
    
    public void setVerticalScrollBarEnabled(boolean paramBoolean)
    {
      ((X5WebViewAdapter)getOuterProxy()).setVerticalScrollBarEnabled(paramBoolean);
    }
    
    public void stopLoading()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("WebView stopLoading invoked url ");
      localStringBuilder.append(TencentWebViewProxy.this.getRealWebView().getUrl());
      X5Log.d("WebView", localStringBuilder.toString());
      super.stopLoading();
    }
    
    public boolean superOnTouchEvent(MotionEvent paramMotionEvent)
    {
      return super.onTouchEvent(paramMotionEvent);
    }
    
    public boolean superOverScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
    {
      int i = TencentWebViewProxy.this.mWebView.computeHorizontalScrollRange();
      int j = computeHorizontalScrollExtent();
      boolean bool2 = false;
      if (i > j) {
        i = 1;
      } else {
        i = 0;
      }
      if (computeVerticalScrollRange() > computeVerticalScrollExtent()) {
        j = 1;
      } else {
        j = 0;
      }
      if ((TencentWebViewProxy.this.mXOverScrollMode != 0) && ((TencentWebViewProxy.this.mXOverScrollMode != 1) || (i == 0))) {
        i = 0;
      } else {
        i = 1;
      }
      if ((TencentWebViewProxy.this.mYOverScrollMode != 0) && ((TencentWebViewProxy.this.mYOverScrollMode != 1) || (j == 0))) {
        j = 0;
      } else {
        j = 1;
      }
      paramInt1 = paramInt3 + paramInt1;
      if (i == 0) {
        paramInt7 = 0;
      }
      paramInt2 = paramInt4 + paramInt2;
      if (j == 0) {
        paramInt8 = 0;
      }
      paramInt4 = -paramInt7;
      paramInt7 += paramInt5;
      paramInt3 = -paramInt8;
      paramInt5 = paramInt6 + paramInt8;
      if (paramInt1 > paramInt7)
      {
        paramInt1 = paramInt7;
        paramBoolean = true;
      }
      else if (paramInt1 < paramInt4)
      {
        paramBoolean = true;
        paramInt1 = paramInt4;
      }
      else
      {
        paramBoolean = false;
      }
      boolean bool1;
      if (paramInt2 > paramInt5)
      {
        paramInt2 = paramInt5;
        bool1 = true;
      }
      else if (paramInt2 < paramInt3)
      {
        paramInt2 = paramInt3;
        bool1 = true;
      }
      else
      {
        bool1 = false;
      }
      onOverScrolled(paramInt1, paramInt2, paramBoolean, bool1);
      if (!paramBoolean)
      {
        paramBoolean = bool2;
        if (!bool1) {}
      }
      else
      {
        paramBoolean = true;
      }
      return paramBoolean;
    }
    
    protected boolean super_awakenScrollBars()
    {
      return super.awakenScrollBars();
    }
    
    protected boolean super_awakenScrollBars(int paramInt)
    {
      return super.awakenScrollBars(paramInt);
    }
    
    protected boolean super_awakenScrollBars(int paramInt, boolean paramBoolean)
    {
      return super.awakenScrollBars(paramInt, paramBoolean);
    }
    
    @UsedByReflection("sdk.WebView")
    public void super_computeScroll()
    {
      super.computeScroll();
    }
    
    @UsedByReflection("sdk.WebView")
    public boolean super_dispatchTouchEvent(MotionEvent paramMotionEvent)
    {
      return super.dispatchTouchEvent(paramMotionEvent);
    }
    
    public void super_draw(Canvas paramCanvas)
    {
      super.draw(paramCanvas);
      TencentWebViewProxy.this.mProvider.draw(paramCanvas);
    }
    
    public boolean super_drawChild(Canvas paramCanvas, View paramView, long paramLong)
    {
      return super.drawChild(paramCanvas, paramView, paramLong);
    }
    
    @UsedByReflection("sdk.WebView")
    public boolean super_onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
      return super.onInterceptTouchEvent(paramMotionEvent);
    }
    
    @SuppressLint({"WrongCall"})
    protected void super_onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
    }
    
    @UsedByReflection("sdk.WebView")
    public void super_onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
    {
      TencentWebViewProxy.this.mProvider.getViewDelegate().onOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
      TencentWebViewProxy.this.mViewManager.a(paramInt2);
      TencentWebViewProxy.this.notifyOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
    }
    
    @UsedByReflection("sdk.WebView")
    public void super_onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
      TencentWebViewProxy.this.mProvider.getViewDelegate().onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
      TencentWebViewProxy.access$2002(TencentWebViewProxy.this, paramInt2);
      if (TencentWebViewProxy.this.mH5VideoProxy != null) {
        TencentWebViewProxy.this.mH5VideoProxy.a(paramInt1, paramInt2, paramInt3, paramInt4);
      }
      if (TencentWebViewProxy.this.mH5MediaSourceProxy != null) {
        TencentWebViewProxy.this.mH5MediaSourceProxy.a(paramInt1, paramInt2, paramInt3, paramInt4);
      }
      TencentWebViewProxy.this.notifyScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    @UsedByReflection("sdk.WebView")
    public boolean super_onTouchEvent(MotionEvent paramMotionEvent)
    {
      return ((X5WebViewAdapter)getOuterProxy()).super_onTouchEvent(paramMotionEvent);
    }
    
    @UsedByReflection("sdk.WebView")
    public boolean super_overScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
    {
      return ((X5WebViewAdapter)getOuterProxy()).super_overScrollBy(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
    }
    
    public void super_setHorizontalScrollBarEnabled(boolean paramBoolean)
    {
      super.setHorizontalScrollBarEnabled(paramBoolean);
    }
    
    public void super_setOnLongClickListener(View.OnLongClickListener paramOnLongClickListener)
    {
      super.setOnLongClickListener(paramOnLongClickListener);
    }
    
    public void super_setVerticalScrollBarEnabled(boolean paramBoolean)
    {
      super.setVerticalScrollBarEnabled(paramBoolean);
    }
    
    public void uploadImageFromCamera(String paramString, com.tencent.tbs.core.webkit.ValueCallback<Uri[]> paramValueCallback)
    {
      this.mMonitorCamera = true;
      this.mTimeCallCamera = System.currentTimeMillis();
      this.mImagesPath = paramString;
      this.mValueCallback = paramValueCallback;
    }
    
    public class PrivateAccess
      extends WebView.PrivateAccess
    {
      public PrivateAccess()
      {
        super();
      }
      
      public void overScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
      {
        super.overScrollBy(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
      }
      
      public boolean super_performLongClick()
      {
        if ((TencentWebViewProxy.this.getH5VideoProxy() != null) && (TencentWebViewProxy.this.getH5VideoProxy().a())) {
          return true;
        }
        return super.super_performLongClick();
      }
      
      public void super_scrollTo(int paramInt1, int paramInt2)
      {
        if ((TencentWebViewProxy.this.getH5VideoProxy() != null) && (TencentWebViewProxy.this.getH5VideoProxy().a(paramInt1, paramInt2))) {
          return;
        }
        super.super_scrollTo(paramInt1, paramInt2);
      }
    }
  }
  
  private class InspectorServiceListener
    extends BroadcastReceiver
  {
    private InspectorServiceListener() {}
    
    public void onReceive(Context paramContext, final Intent paramIntent)
    {
      String str2 = paramIntent.getAction();
      String str1 = paramIntent.getData().getSchemeSpecificPart();
      paramIntent = paramIntent.getData().getScheme();
      if ((com.tencent.smtt.webkit.ui.f.c(paramContext)) && ("android.intent.action.TBS_DEBUG".equals(str2)) && (paramIntent.equals("tbs_inspector")))
      {
        if ("test".equals(str1))
        {
          Log.e("inspector", "test x5_core success!");
          return;
        }
        if ((TencentWebViewProxy.isInvokingX5InspectorSettings.booleanValue()) && (com.tencent.smtt.webkit.ui.f.a())) {
          return;
        }
        paramContext = TencentWebViewProxy.this;
        paramIntent = paramContext.getContext();
        final boolean bool;
        try
        {
          bool = Boolean.parseBoolean(str1);
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
          bool = true;
        }
        if (com.tencent.smtt.webkit.ui.f.b(paramIntent))
        {
          paramContext.getRealWebView().post(new Runnable()
          {
            public void run()
            {
              try
              {
                new com.tencent.smtt.webkit.ui.f(paramIntent, bool).show();
                return;
              }
              catch (Exception localException)
              {
                localException.printStackTrace();
              }
            }
          });
        }
        else
        {
          paramIntent = new StringBuilder();
          paramIntent.append("javascript:window.open('http://debugx5.qq.com/?inspector=");
          paramIntent.append(bool);
          paramIntent.append("');");
          paramContext.loadUrl(paramIntent.toString(), null);
        }
        TencentWebViewProxy.access$102(Boolean.valueOf(true));
      }
    }
  }
  
  @Deprecated
  public static abstract interface PictureListener
    extends IX5WebViewBase.PictureListener, WebView.PictureListener
  {
    @Deprecated
    public abstract void onNewPicture(WebView paramWebView, Picture paramPicture);
  }
  
  public class WebViewTransport
    extends IX5WebViewBase.WebViewTransport
  {
    private WebView.WebViewTransport mWebViewTransport;
    
    public WebViewTransport()
    {
      this$1 = TencentWebViewProxy.this.mWebView;
      TencentWebViewProxy.this.getClass();
      this.mWebViewTransport = new WebView.WebViewTransport(TencentWebViewProxy.this);
    }
    
    public IX5WebViewBase getWebView()
    {
      try
      {
        this.mWebViewTransport.getWebView();
        IX5WebViewBase localIX5WebViewBase = super.getWebView();
        return localIX5WebViewBase;
      }
      finally
      {
        localObject = finally;
        throw ((Throwable)localObject);
      }
    }
    
    public void setWebView(IX5WebViewBase paramIX5WebViewBase)
    {
      try
      {
        this.mWebViewTransport.setWebView((WebView)paramIX5WebViewBase);
        super.setWebView(paramIX5WebViewBase);
        return;
      }
      finally
      {
        paramIX5WebViewBase = finally;
        throw paramIX5WebViewBase;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentWebViewProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */