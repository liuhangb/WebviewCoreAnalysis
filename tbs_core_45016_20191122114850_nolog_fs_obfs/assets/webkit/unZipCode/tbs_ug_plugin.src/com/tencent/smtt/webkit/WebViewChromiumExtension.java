package com.tencent.smtt.webkit;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputConnection;
import android.webview.chromium.WebViewChromium;
import android.webview.chromium.WebViewChromiumFactoryProvider;
import android.webview.chromium.tencent.TencentCallbackConverter;
import android.webview.chromium.tencent.TencentGeolocationPermissionsAdapter;
import android.webview.chromium.tencent.TencentWebHistoryItemChromium;
import android.webview.chromium.tencent.TencentWebViewChromium;
import android.webview.chromium.tencent.TencentWebViewContentsClientAdapter;
import android.webview.chromium.tencent.TencentWebViewDatabaseAdapter;
import android.widget.Toast;
import com.tencent.common.http.Apn;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.TbsMode;
import com.tencent.common.utils.UrlUtils;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebChromeClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.ISelectionInterface;
import com.tencent.smtt.export.external.interfaces.IX5WebBackForwardListClient;
import com.tencent.smtt.export.external.interfaces.IX5WebHistoryItem;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.ImageInfo;
import com.tencent.smtt.export.external.interfaces.MediaAccessPermissionsCallback;
import com.tencent.smtt.export.internal.interfaces.DownloadListenerExtension;
import com.tencent.smtt.export.internal.interfaces.IX5QQBrowserClient;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.smtt.export.internal.interfaces.IX5WebView.TRANSLATE_STATE;
import com.tencent.smtt.export.internal.interfaces.SecurityLevelBase;
import com.tencent.smtt.jsApi.export.OpenJsApiBridge;
import com.tencent.smtt.livelog.LiveLog;
import com.tencent.smtt.memory.MemoryChecker;
import com.tencent.smtt.net.AwNetworkUtils;
import com.tencent.smtt.net.PageLoadDetector;
import com.tencent.smtt.net.X5BadJsReporter.a;
import com.tencent.smtt.net.f;
import com.tencent.smtt.net.g;
import com.tencent.smtt.os.SMTTAdaptation;
import com.tencent.smtt.util.CrashExtraMessage;
import com.tencent.smtt.util.MttLog;
import com.tencent.smtt.util.WebRtcUtils;
import com.tencent.smtt.util.X5LogUploadManager.OnUploadListener;
import com.tencent.smtt.util.k;
import com.tencent.smtt.webkit.h5video.d;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.smtt.webkit.ui.e.a;
import com.tencent.smtt.webkit.ui.h;
import com.tencent.smtt.webkit.webar.WebARCameraUtils;
import com.tencent.tbs.core.partner.impl.wakelock.X5CoreWakeLockManager;
import com.tencent.tbs.core.partner.menu.X5Selection;
import com.tencent.tbs.core.webkit.GeolocationPermissions;
import com.tencent.tbs.core.webkit.JsPromptResult;
import com.tencent.tbs.core.webkit.JsResult.ResultReceiver;
import com.tencent.tbs.core.webkit.URLUtil;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentURLUtil;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import org.chromium.android_webview.AwContentsStatics;
import org.chromium.android_webview.CleanupReference;
import org.chromium.android_webview.JsPromptResultReceiver;
import org.chromium.android_webview.JsResultReceiver;
import org.chromium.android_webview.WebViewChromiumRunQueue;
import org.chromium.android_webview.permission.AwPermissionRequest;
import org.chromium.base.Callback;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.content.browser.input.ImeAdapterImpl;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.content_public.browser.GestureListenerManager;
import org.chromium.content_public.browser.NavigationHistory;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.browser.WebContentsAccessibility;
import org.chromium.content_public.browser.WebContentsObserver;
import org.chromium.media.VideoCaptureFactory;
import org.chromium.net.GURLUtils;
import org.chromium.tencent.AwContentsClient.AwResourceMetrics;
import org.chromium.tencent.AwContentsClient.AwStgwTime;
import org.chromium.tencent.AwContentsClient.AwWebRequestInfo;
import org.chromium.tencent.AwContentsClientExtension;
import org.chromium.tencent.AwMediaAccessPermissions;
import org.chromium.tencent.ContentViewClientExtension;
import org.chromium.tencent.SelectionControllerClient;
import org.chromium.tencent.SharedResource;
import org.chromium.tencent.TencentAwBrowserProcess;
import org.chromium.tencent.TencentAwContent;
import org.chromium.tencent.TencentAwContent.TencentHitTestData;
import org.chromium.tencent.TencentAwSettings;
import org.chromium.tencent.TencentContentViewCore;
import org.chromium.tencent.X5NativeBitmap;
import org.chromium.tencent.X5NativeBitmap.Factory;
import org.chromium.tencent.android_webview.permission.TencentAwCameraPermissionRequest;
import org.chromium.tencent.android_webview.permission.TencentAwPermissionRequest;
import org.chromium.tencent.android_webview.permission.TencentAwPermissionRequest.AwPermissionRequestClient;
import org.chromium.tencent.content.browser.TencentSelectionPopupControllerImpl;
import org.chromium.tencent.content.browser.input.TencentImeAdapter;
import org.chromium.tencent.media.MediaSourceBridge;
import org.chromium.tencent.media.TencentMediaPlayerBridge;
import org.chromium.tencent.video.IMediaPlayer;
import org.chromium.tencent.video.SystemMediaPlayer;
import org.chromium.ui.base.EventForwarder;
import org.chromium.ui.base.WindowAndroid;
import org.json.JSONException;
import org.json.JSONObject;

@JNINamespace("tencent")
public class WebViewChromiumExtension
  implements WebViewProviderExtension, AwContentsClientExtension, ContentViewClientExtension, SelectionControllerClient, TencentAwPermissionRequest.AwPermissionRequestClient
{
  private static final int AWHANDLE_MSG_CANCEL_FORCE_PINCH_SCALE = 7;
  private static final int DEAD_CODE_DETECTION_DELAY = 5000;
  private static final int DEAD_CODE_DETECTION_WAIT = 8000;
  private static final int DEAD_CODE_FACT = 2;
  private static final int DEAD_CODE_FACT_SECOND = 9;
  private static final int DEAD_CODE_PREDICTION = 1;
  private static final boolean ENABLE_DEAD_CODE_DETECTION = true;
  private static final boolean ENABLE_DEAD_CODE_UPLOAD_BEACON = true;
  private static final boolean ENABLE_DEAD_CODE_UPLOAD_DUMP = true;
  private static final int FINGER_SEARCH_TIMEOUT = 1200;
  private static final int FORCE_PINCH_SCALE_DELAY = 3000;
  private static String[] GEOLOCATION_AUTHORIZED_STATUS_WHITE_LIST = { "res.imtt.qq.com", "apis.map.qq.com" };
  private static String GEOLOCATION_STATUS_ALLOW = "3";
  private static String GEOLOCATION_STATUS_DENY = "4";
  private static String GEOLOCATION_STATUS_UNAUTHORIZED = "1";
  private static String GEOLOCATION_STATUS_UNKNOWN = "0";
  private static String GEOLOCATION_STATUS_UNPERSISTENT = "2";
  private static boolean IS_STARTUP_INIT_REPORT_CONFIG_MAP = true;
  private static boolean IS_STARTUP_UPLOAD = true;
  private static boolean IS_STARTUP_UPLOAD_CRASH = true;
  private static boolean IS_STARTUP_UPLOAD_FIXED_AD = true;
  private static final int JAVA_SCRIPT_OPEN_WINDOW_DELAY = 250;
  private static final int JAVA_SCRIPT_OPEN_WINDOW_NUM_LIMIT = 10;
  private static boolean MAIN_THREAD_ALREADY_BLOCKED = false;
  private static final int MAX_PRELOAD_COPY_TIME = 12000;
  private static final int MAX_PRELOAD_WORD_LEN = 10;
  private static final int MSG_DO_PRELOAD = 10;
  private static final int MSG_ON_DOWNLOAD_START_CUSTOM = 3;
  private static final int MSG_ON_ERROR_RESOURCE_INFO = 5;
  private static final int MSG_ON_FINGER_SEARCH_TIMEOUT = 14;
  private static final int MSG_ON_RECEIVED_STGW_TIME = 13;
  private static final int MSG_ON_REPORT_INSPECTED_URL = 8;
  private static final int MSG_ON_REPORT_METICS = 4;
  private static final int MSG_ON_REPORT_NIGHTMODE = 11;
  private static final int MSG_ON_RESOURCE_ALL_INFO = 6;
  private static int MaxBKLiveLogCount = 0;
  private static final int SCREENSHOT_ID_CURRENT = -10002;
  private static final int SCREENSHOT_ID_PREREADING = -10001;
  private static final String TAG = "WebViewChromiumExtension";
  private static boolean mHasReportedX5CoreDataInfo = false;
  private static HashMap<String, Object> mMediaAccessPermissions;
  private static boolean mPermissionDenyToastShow = false;
  private static HashSet<String> preLoadHistory;
  private static boolean sCompatibleOnMetricsSavedCountReceived = true;
  private boolean ENABLE_DEAD_CODE_DETECTION_IN_APP = e.a();
  private boolean hasShown = false;
  private boolean isEnterSelectionMode = false;
  private boolean isExistFingerSearchClient = false;
  private boolean isNewUrl = false;
  private TencentAwContent mAwContents;
  private Bitmap mBitmap = null;
  private volatile int mBlankScreenStatus = -1;
  private b mBrowserControlsOffsetManager;
  private Runnable mCallback = null;
  private TencentWebViewContentsClientAdapter mContentsClientAdapter;
  float mCurPosY;
  private int mCurrentReportPageIndex = WebRtcUtils.a();
  private ArrayList<Integer> mDisableOverScrollPageId = new ArrayList();
  private com.tencent.smtt.webkit.b.a mDisplayCutoutController = null;
  private DownloadListenerExtension mDownloadListenerExtension;
  private boolean mEnableAutoPageDiscarding = e.c();
  private boolean mEnableAutoPageRestoration = e.d();
  private int mEventType = -1;
  private WebViewChromiumFactoryProvider mFactory;
  private e mFingerSearchClient;
  private long mFirstScreen = 0L;
  private boolean mForceNoNeedsScroll = false;
  private Toast mForcePinchScaleToast = null;
  private ArrayList<Integer> mForceScaleDisabledPageIdList = new ArrayList();
  l mFullScreenHolder;
  private Object mGetWebContentLock = new Object();
  private Handler mHandler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      Object localObject;
      switch (paramAnonymousMessage.what)
      {
      case 4: 
      case 5: 
      case 12: 
      default: 
        
      case 14: 
        WebViewChromiumExtension.this.notifyWXEnterSelectionMode(true);
        return;
      case 13: 
        if (WebViewChromiumExtension.this.mWebViewClientExtension != null)
        {
          paramAnonymousMessage = (AwContentsClient.AwStgwTime)paramAnonymousMessage.obj;
          localObject = new Bundle();
          ((Bundle)localObject).putString("url", paramAnonymousMessage.url);
          ((Bundle)localObject).putString("requestTime", paramAnonymousMessage.requestTime);
          ((Bundle)localObject).putString("upstreamResponseTime", paramAnonymousMessage.upstreamResponseTime);
          WebViewChromiumExtension.this.mWebViewClientExtension.onMiscCallBack("onReportStgwTime", (Bundle)localObject);
          return;
        }
        break;
      case 11: 
        paramAnonymousMessage = (WebViewChromiumExtension.h)paramAnonymousMessage.obj;
        localObject = new HashMap();
        ((HashMap)localObject).put("app", paramAnonymousMessage.jdField_a_of_type_JavaLangString);
        ((HashMap)localObject).put("mode", paramAnonymousMessage.b);
        SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_NIGHTMODE", (Map)localObject);
        return;
      case 10: 
        if (paramAnonymousMessage.obj != null)
        {
          localObject = new ArrayList();
          ((List)localObject).add(paramAnonymousMessage.obj.toString());
          WebViewChromiumExtension.this.preLoad((List)localObject);
          return;
        }
        break;
      case 9: 
        WebViewChromiumExtension.this.saveDeadDump();
        return;
      case 8: 
        paramAnonymousMessage = (WebViewChromiumExtension.f)paramAnonymousMessage.obj;
        localObject = new HashMap();
        ((HashMap)localObject).put("url", paramAnonymousMessage.jdField_a_of_type_JavaLangString);
        ((HashMap)localObject).put("loadType", Integer.toString(paramAnonymousMessage.jdField_a_of_type_Int));
        SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_DEVTOOLS_URL", (Map)localObject);
        return;
      case 7: 
        paramAnonymousMessage = (String)paramAnonymousMessage.obj;
        WebViewChromiumExtension.this.mSettingsExtension.setForcePinchScale(false, paramAnonymousMessage);
        return;
      case 6: 
        paramAnonymousMessage = (AwContentsClient.AwWebRequestInfo)paramAnonymousMessage.obj;
        WebViewChromiumExtension.this.mContentsClientAdapter.onReportResourceAllInfo(paramAnonymousMessage);
        return;
      case 3: 
        paramAnonymousMessage = (WebViewChromiumExtension.d)paramAnonymousMessage.obj;
        if (!paramAnonymousMessage.jdField_a_of_type_Boolean)
        {
          WebViewChromiumExtension.this.mContentsClientAdapter.onDownloadStartCustom(paramAnonymousMessage.jdField_a_of_type_JavaLangString, paramAnonymousMessage.b, paramAnonymousMessage.c, paramAnonymousMessage.d, paramAnonymousMessage.jdField_a_of_type_Long, paramAnonymousMessage.e, paramAnonymousMessage.f, paramAnonymousMessage.g, paramAnonymousMessage.h, paramAnonymousMessage.jdField_a_of_type_ArrayOfByte, paramAnonymousMessage.i);
          paramAnonymousMessage.jdField_a_of_type_Boolean = true;
          return;
        }
        break;
      case 2: 
        WebViewChromiumExtension.this.uploadBlockInfo();
        return;
      case 1: 
        WebViewChromiumExtension.this.detectDeadCode();
      }
    }
  };
  private boolean mHasCallbackTask = false;
  private boolean mHasEnteredSelection = false;
  private boolean mHasWaitingEnterSelectionMode = false;
  private ArrayList<IX5WebViewBase.ImageInfo> mImageList = new ArrayList();
  private long mImeShowLastTime = 0L;
  private boolean mIsFingerSeaching = false;
  private boolean mIsImageListUpdated = false;
  private boolean mIsPrereadingValid = false;
  private volatile int mLastBlankScreenStatus = 4;
  private int mLastNavigationIndex = -1;
  private int mLastNavigationPageId = -1;
  private String mLastNavigationTitle = "";
  private String mLastNavigationUrl = "";
  private int mLastsearchID = 0;
  private long mNativeWebViewChromiumExtension = 0L;
  private String mNavigateUrl;
  private long mNextEditableNode;
  private long mPageStart = 0L;
  private TencentWebViewDatabaseAdapter mPasswordDatabase;
  private SurfaceView mPluginSurfaceView = null;
  private ArrayList<String> mPopwindowBlockedList = new ArrayList();
  float mPosY;
  private long mPreEditableNode;
  private String mPreRenderUrl = null;
  private IX5QQBrowserClient mQQBrowserClient;
  private i mReportLongClickClient;
  int mScreenHeight;
  private WeakReference<Bitmap> mScreenshot = null;
  private List<j> mSearchResultDataList = null;
  private SecurityLevelBase mSecurityLevel = null;
  private boolean mSelectable;
  private com.tencent.smtt.webkit.input.c mSelectionController;
  private ISelectionInterface mSelectionListener;
  private WebSettingsExtension mSettingsExtension;
  private SurfaceHolder.Callback mSurfaceCallback = null;
  int mTouchCount;
  private r mTranslateHelper = null;
  private android.webkit.ValueCallback<Integer> mUpdataImageCallback;
  private t mWXTranslateHelper = null;
  private Object mWakeLockUser = new Object();
  private IX5WebBackForwardListClient mWebBackForwardListClient;
  private IX5WebChromeClientExtension mWebChromeClientExtension;
  private String mWebContent = null;
  private k mWebContentsObserverExtension;
  private TencentWebViewProxy mWebView;
  private TencentWebViewChromium mWebViewChromium;
  private IX5WebViewClientExtension mWebViewClientExtension;
  private u mWebViewReportClient;
  private String mX5ForcePinchScale = null;
  
  public WebViewChromiumExtension(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider, TencentWebViewProxy paramTencentWebViewProxy, WebViewChromium paramWebViewChromium, TencentWebViewContentsClientAdapter paramTencentWebViewContentsClientAdapter, TencentAwContent paramTencentAwContent)
  {
    this.mWebView = paramTencentWebViewProxy;
    this.mWebViewChromium = ((TencentWebViewChromium)paramWebViewChromium);
    this.mFactory = paramWebViewChromiumFactoryProvider;
    this.mContentsClientAdapter = paramTencentWebViewContentsClientAdapter;
    this.mAwContents = paramTencentAwContent;
    this.mAwContents.setAwContentsClientExtension(this);
    this.mSelectionController = new com.tencent.smtt.webkit.input.c(this.mWebView.getRealWebView(), this);
    this.mBrowserControlsOffsetManager = new b(paramTencentAwContent, paramTencentWebViewProxy, this, this.mSelectionController);
    if (this.mAwContents.getContentViewCore() != null)
    {
      this.mAwContents.getTencentContentViewCore().setContentViewClientExtension(this);
      this.mAwContents.getTencentContentViewCore().getTencentSelectionPopupController().setSelectionControllerClient(this);
      GestureListenerManager.fromWebContents(this.mAwContents.getContentViewCore().getWebContents()).addListener(this.mSelectionController);
    }
    this.mSettingsExtension = new WebSettingsExtension(this.mWebView.getContext(), this.mAwContents.getSettings(), this.mWebView);
    this.mNativeWebViewChromiumExtension = nativeInit(this.mAwContents.getWebContents());
    if ((this.ENABLE_DEAD_CODE_DETECTION_IN_APP) && (IS_STARTUP_UPLOAD))
    {
      IS_STARTUP_UPLOAD = false;
      SmttServiceProxy.getInstance().uploadDeadCodeToBeaconIfNeeded(true, true);
    }
    if ((IS_STARTUP_UPLOAD_CRASH) && (SmttServiceProxy.getInstance().getCrashReportEnabled()))
    {
      IS_STARTUP_UPLOAD_CRASH = false;
      SmttServiceProxy.getInstance().uploadCrashMsgInfoToBeacon();
    }
    if (IS_STARTUP_UPLOAD_FIXED_AD)
    {
      IS_STARTUP_UPLOAD_FIXED_AD = false;
      RenderSmttService.b();
    }
    if (IS_STARTUP_INIT_REPORT_CONFIG_MAP)
    {
      IS_STARTUP_INIT_REPORT_CONFIG_MAP = false;
      RenderSmttService.a();
    }
    if (mMediaAccessPermissions == null) {
      mMediaAccessPermissions = new HashMap();
    }
    this.mWebContentsObserverExtension = new k(this.mAwContents.getWebContents());
    this.mWebViewReportClient = new u();
    this.mWXTranslateHelper = new t(this, this.mWebView);
    this.mDisplayCutoutController = new com.tencent.smtt.webkit.b.a(this.mAwContents, this.mWebView, this.mSettingsExtension);
    this.mTranslateHelper = new r(this);
    if (!mHasReportedX5CoreDataInfo)
    {
      if (e.a().y()) {
        SmttServiceProxy.getInstance().onReportX5CoreDataDirInfo(TencentAwBrowserProcess.isUsingX5DataDir());
      }
      mHasReportedX5CoreDataInfo = true;
    }
  }
  
  private void DoHandleAndUpload(j paramj, double paramDouble)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  @CalledByNative
  private void OnContentCachePageSwapIn(String paramString)
  {
    this.mContentsClientAdapter.OnContentCachePageSwapIn(paramString);
    this.mWebView.notifyContentPageSwapIn(paramString);
  }
  
  @CalledByNative
  private void OnFingerSearchRequest(final String paramString1, final int paramInt1, final String paramString2, final int paramInt2, final int[] paramArrayOfInt)
  {
    if (this.mWebView == null) {
      return;
    }
    if (this.mFingerSearchClient == null) {
      this.mFingerSearchClient = new e(SmttServiceProxy.getInstance().getFingerSearchInstance((IX5WebView)this.mWebView));
    }
    if (this.mFingerSearchClient != null)
    {
      if (e.a().x()) {
        BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
        {
          public void doRun()
          {
            WebViewChromiumExtension.this.mFingerSearchClient.a(paramString1, paramInt1, paramString2, paramInt2, paramArrayOfInt, "", "");
          }
        });
      } else {
        this.mFingerSearchClient.a(paramString1, paramInt1, paramString2, paramInt2, paramArrayOfInt, "", "");
      }
      this.isExistFingerSearchClient = true;
    }
    if (e.a().a(this.mWebView)) {
      onFingerSearchTimeOut();
    }
  }
  
  @CalledByNative
  private void OnReceivedExplorerStatistics(int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    this.mWebView.setExplorerInfo(paramInt1, paramInt2, paramInt3, paramString);
  }
  
  public static void RefreshPlugins(String paramString)
  {
    nativeRefreshPlugins(paramString);
  }
  
  @CalledByNative
  private static void ReportExtraMessageLogToRQD(String paramString1, String paramString2)
  {
    CrashExtraMessage.ReportExtraMessageLogToRQD(paramString1, paramString2);
  }
  
  @CalledByNative
  private static void ReportExtraMessageUserDataToRQD(String paramString1, String paramString2)
  {
    CrashExtraMessage.ReportExtraMessageUserDataToRQD(paramString1, paramString2);
  }
  
  @CalledByNative
  private void ReportHitRateInfo(Object paramObject)
  {
    if (paramObject != null) {
      paramObject = (HashMap)paramObject;
    } else {
      paramObject = null;
    }
    if ((paramObject != null) && (((HashMap)paramObject).size() > 2)) {
      SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_QB_HIT_RATE_INFO", (Map)paramObject);
    }
  }
  
  private static String buildDataUri(String paramString1, String paramString2, boolean paramBoolean, String paramString3)
  {
    StringBuilder localStringBuilder = new StringBuilder("data:");
    localStringBuilder.append(paramString2);
    if ((paramString3 != null) && (!paramString3.isEmpty()))
    {
      paramString2 = new StringBuilder();
      paramString2.append(";charset=");
      paramString2.append(paramString3);
      localStringBuilder.append(paramString2.toString());
    }
    if (paramBoolean) {
      localStringBuilder.append(";base64");
    }
    localStringBuilder.append(",");
    localStringBuilder.append(paramString1);
    return localStringBuilder.toString();
  }
  
  @CalledByNative
  private void closeDisambiguationPopup()
  {
    if (this.mAwContents.getContentViewCore() != null) {
      this.mAwContents.getTencentContentViewCore().closeDisambiguationPopup();
    }
  }
  
  @CalledByNative
  private static Object createHashMap()
  {
    return new HashMap();
  }
  
  @CalledByNative
  private Object createSurface(View paramView)
  {
    if (this.mWebView == null)
    {
      Log.e("WebViewChromiumExtension", "createSurface, mWebView is null!");
      return null;
    }
    if (paramView == null)
    {
      Log.e("WebViewChromiumExtension", "createSurface, pluginView is null!");
      return null;
    }
    if (!(paramView instanceof SurfaceView))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("createSurface, pluginView is not instanceof SurfaceView, pluginView:");
      ((StringBuilder)localObject).append(paramView);
      Log.e("WebViewChromiumExtension", ((StringBuilder)localObject).toString());
      return null;
    }
    ((SurfaceView)paramView).setZOrderOnTop(true);
    Object localObject = this.mWebView.getViewManager().a();
    ((s.a)localObject).a = paramView;
    return localObject;
  }
  
  private void detectDeadCode()
  {
    if (this.ENABLE_DEAD_CODE_DETECTION_IN_APP)
    {
      if (MAIN_THREAD_ALREADY_BLOCKED) {
        return;
      }
      if (this.mNativeWebViewChromiumExtension == 0L) {
        return;
      }
      this.mHandler.sendEmptyMessageDelayed(2, 8000L);
      this.mHandler.sendEmptyMessageDelayed(1, 8000L);
      nativeDetectDeadCode(this.mNativeWebViewChromiumExtension);
      return;
    }
  }
  
  private int detectFastOpenWebView(String paramString)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        try
        {
          WebViewChromiumExtension.this.getWebContent();
          return;
        }
        catch (Exception localException)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("detectFastOpenWebView Exception:");
          localStringBuilder.append(localException.getMessage());
          localStringBuilder.append(",");
          localStringBuilder.append(localException.getCause());
          Log.e("odin_trans", localStringBuilder.toString());
        }
      }
    });
    synchronized (this.mGetWebContentLock)
    {
      boolean bool = TextUtils.isEmpty(this.mWebContent);
      if (bool) {
        try
        {
          this.mGetWebContentLock.wait(5000L);
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
      }
      if (TextUtils.isEmpty(this.mWebContent))
      {
        Log.e("odin_trans", "detectFastOpenWebView - get content empty...");
        return 2;
      }
      return this.mWXTranslateHelper.a(this.mWebContent, paramString);
    }
  }
  
  private int detectNormalWebView(String paramString)
  {
    long l3 = System.currentTimeMillis();
    synchronized (this.mGetWebContentLock)
    {
      long l1 = System.currentTimeMillis();
      for (;;)
      {
        boolean bool = TextUtils.isEmpty(this.mWebContent);
        if ((!bool) || (l1 - l3 >= 10000L)) {
          break;
        }
        try
        {
          this.mGetWebContentLock.wait(5000L);
          long l2 = System.currentTimeMillis();
          l1 = l2;
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
      }
      if (TextUtils.isEmpty(this.mWebContent))
      {
        Log.e("odin_trans", "detectNormalWebView - get web content empty...");
        return 2;
      }
      return this.mWXTranslateHelper.a(this.mWebContent, paramString);
    }
  }
  
  private void dumpDeadCode()
  {
    if (this.ENABLE_DEAD_CODE_DETECTION_IN_APP)
    {
      long l = this.mNativeWebViewChromiumExtension;
      if (l == 0L) {
        return;
      }
      nativeDumpDeadCode(l);
      return;
    }
  }
  
  private Activity getActivity()
  {
    if ((this.mWebView.getContext() instanceof Activity)) {
      return (Activity)this.mWebView.getContext();
    }
    for (Object localObject = this.mWebView.getRealWebView();; localObject = (View)((View)localObject).getParent())
    {
      if ((((View)localObject).getContext() instanceof Activity)) {
        return (Activity)((View)localObject).getContext();
      }
      if ((((View)localObject).getParent() == null) || (!(((View)localObject).getParent() instanceof View))) {
        break;
      }
    }
    return null;
  }
  
  private int getBlankScreenStatus()
  {
    int i;
    if ((!this.mAwContents.isShowOnScreen()) && (!this.hasShown)) {
      i = 0;
    } else {
      i = 1;
    }
    if (i == 0) {
      return 0;
    }
    this.hasShown = false;
    String str = getWebViewIdentity();
    if ((str != null) && (str.equalsIgnoreCase("video_product_operation"))) {
      return 0;
    }
    if (this.mAwContents.getMostRecentProgress() < 100) {
      return 0;
    }
    if ((this.mBlankScreenStatus != 0) && (this.mLastBlankScreenStatus == 0)) {
      this.mBlankScreenStatus += 100;
    }
    return this.mBlankScreenStatus;
  }
  
  public static String getCrashExtraMessage()
  {
    return nativeGetCrashExtraMessage();
  }
  
  @CalledByNative
  private int[] getDisplayCutRect(int paramInt)
  {
    Rect localRect = this.mDisplayCutoutController.a(paramInt);
    return new int[] { localRect.left, localRect.top, localRect.right, localRect.bottom };
  }
  
  private int getTouchEventCount()
  {
    TencentAwContent localTencentAwContent = this.mAwContents;
    if (localTencentAwContent != null) {
      return localTencentAwContent.getTouchEventCount();
    }
    return 0;
  }
  
  @CalledByNative
  private boolean handlePluginTag(String paramString1, String paramString2, boolean paramBoolean, String paramString3)
  {
    Apn.isWifiMode();
    if (!isPluginTypeSupported(paramString1, paramString2)) {
      return false;
    }
    if ((("application/x-shockwave-flash".equalsIgnoreCase(paramString2)) || (paramString1.contains(".swf"))) && (paramBoolean))
    {
      paramString2 = this.mWebViewClientExtension;
      if (paramString2 != null)
      {
        paramString2.handlePluginTag(paramString1, "application/x-shockwave-flash", paramBoolean, paramString3);
        return true;
      }
    }
    return false;
  }
  
  @CalledByNative
  private void hideFullScreenPlugin()
  {
    IX5WebChromeClientExtension localIX5WebChromeClientExtension = this.mWebChromeClientExtension;
    if (localIX5WebChromeClientExtension != null) {
      localIX5WebChromeClientExtension.exitFullScreenFlash();
    }
    if (isPluginFullScreen())
    {
      this.mFullScreenHolder.b();
      this.mFullScreenHolder = null;
      this.mPluginSurfaceView.getHolder().removeCallback(this.mSurfaceCallback);
      this.mPluginSurfaceView = null;
      this.mSurfaceCallback = null;
      this.mWebView.getRealWebView().invalidate();
    }
  }
  
  @CalledByNative
  private void initVrFullScreen() {}
  
  private boolean isApk(String paramString1, String paramString2)
  {
    boolean bool1;
    if (paramString1 == null) {
      bool1 = false;
    } else {
      bool1 = paramString1.equalsIgnoreCase("application/vnd.android.package-archive");
    }
    boolean bool2 = bool1;
    if (!bool1)
    {
      bool2 = bool1;
      if (!TextUtils.isEmpty(paramString2))
      {
        paramString1 = "";
        if (paramString2.length() >= 4) {
          paramString1 = paramString2.substring(paramString2.length() - 4);
        }
        bool2 = bool1;
        if (paramString1.equalsIgnoreCase(".apk")) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  private boolean isPluginTypeSupported(String paramString1, String paramString2)
  {
    return SmttServiceProxy.getInstance().isPluginSupported(this.mAwContents.getUrl(), paramString1, paramString2);
  }
  
  private boolean isPrerenderEnabled()
  {
    int i = SmttServiceProxy.getInstance().getPrerenderSwitch();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("PreLoad Switch : ");
    localStringBuilder.append(i);
    MttLog.d("preload_switch", localStringBuilder.toString());
    if (i == -1) {
      return false;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Network Switch : NetworkAvailable=");
    localStringBuilder.append(Apn.isNetworkAvailable());
    localStringBuilder.append("  Apn.getApnType()=");
    localStringBuilder.append(Apn.getApnType());
    MttLog.d("preload_switch", localStringBuilder.toString());
    if (!Apn.isNetworkAvailable()) {
      return false;
    }
    return (i != 0) || (Apn.getApnType() == 4);
  }
  
  private boolean isRequestPermissionCached(TencentAwPermissionRequest paramTencentAwPermissionRequest, long paramLong, String paramString)
  {
    AwMediaAccessPermissions localAwMediaAccessPermissions = this.mAwContents.getMediaAccessPermissions();
    long l2 = 2L;
    long l1;
    if (((paramLong & 0x2) != 0L) && (localAwMediaAccessPermissions.hasOrigin(paramString, 1)))
    {
      if (localAwMediaAccessPermissions.isOriginAllowed(paramString, 1)) {
        l1 = 2L;
      } else {
        l1 = 0L;
      }
    }
    else
    {
      l2 = 0L;
      l1 = l2;
    }
    long l3 = l2;
    long l4 = l1;
    if ((paramLong & 0x4) != 0L)
    {
      l3 = l2;
      l4 = l1;
      if (localAwMediaAccessPermissions.hasOrigin(paramString, 2))
      {
        l2 |= 0x4;
        l3 = l2;
        l4 = l1;
        if (localAwMediaAccessPermissions.isOriginAllowed(paramString, 2))
        {
          l4 = l1 | 0x4;
          l3 = l2;
        }
      }
    }
    if ((paramLong & l3) == paramLong)
    {
      paramString = new StringBuilder();
      paramString.append("isRequestPermissionCached retained originAllowed: ");
      paramString.append(l4);
      LiveLog.d("WebViewChromiumExtension", paramString.toString());
      if (l4 != 0L)
      {
        paramTencentAwPermissionRequest.grant(l4);
        return true;
      }
      if (!mPermissionDenyToastShow)
      {
        mPermissionDenyToastShow = true;
        Toast.makeText(this.mWebView.getContext(), SmttResource.getString("x5_ar_forbiden_retry", "string"), 1).show();
      }
      paramTencentAwPermissionRequest.deny();
      return true;
    }
    return false;
  }
  
  private boolean isRequestPermissionInWhiteList(TencentAwPermissionRequest paramTencentAwPermissionRequest, long paramLong, String paramString)
  {
    if (SmttServiceProxy.getInstance().isTbsARGrantPermisionRequest(paramString))
    {
      paramTencentAwPermissionRequest.grant(paramLong);
      paramTencentAwPermissionRequest = new StringBuilder();
      paramTencentAwPermissionRequest.append("onPermissionRequest SmttServiceProxy grant resources: ");
      paramTencentAwPermissionRequest.append(paramLong);
      LiveLog.d("WebViewChromiumExtension", paramTencentAwPermissionRequest.toString());
      return true;
    }
    if (SmttServiceProxy.getInstance().isTbsARDenyPermisionRequest(paramString))
    {
      LiveLog.d("WebViewChromiumExtension", "onPermissionRequest SmttServiceProxy deny !!");
      paramTencentAwPermissionRequest.deny();
      return true;
    }
    return false;
  }
  
  private boolean isSelectAll()
  {
    Object localObject = this.mAwContents;
    if ((localObject != null) && (((TencentAwContent)localObject).getTencentContentViewCore() != null) && (this.mAwContents.getTencentContentViewCore().getTencentImeAdapter() != null)) {
      localObject = this.mAwContents.getTencentContentViewCore().getTencentImeAdapter();
    } else {
      localObject = null;
    }
    if ((localObject != null) && (!TextUtils.isEmpty(((TencentImeAdapter)localObject).getLastText())))
    {
      int i = ((TencentImeAdapter)localObject).getSelectionStart();
      int j = ((TencentImeAdapter)localObject).getSelectionEnd();
      int k = ((TencentImeAdapter)localObject).getLastTextLength();
      if ((i == 0) && (j == k)) {
        return true;
      }
    }
    return false;
  }
  
  @CalledByNative
  private void keepScreenOn(boolean paramBoolean)
  {
    this.mWebView.getRealWebView().setKeepScreenOn(paramBoolean);
  }
  
  private native boolean nativeCanTakeScreenshotWithPageID(long paramLong, int paramInt);
  
  private native void nativeDetectDeadCode(long paramLong);
  
  private native void nativeDumpDeadCode(long paramLong);
  
  private native void nativeEvaluateJavaScriptInFrame(long paramLong, String paramString1, String paramString2, boolean paramBoolean);
  
  private native void nativeEvaluateJavaScriptInSubFrame(long paramLong, String paramString, boolean paramBoolean);
  
  private native void nativeFocusAndPopupIM(long paramLong, String paramString);
  
  private native void nativeFullScreenPluginHidden(long paramLong);
  
  private native void nativeGetBitmapByIndex(long paramLong, int paramInt);
  
  private static native String nativeGetCrashExtraMessage();
  
  private native int nativeGetCurrentNavigationEntryIndex(long paramLong);
  
  private native String nativeGetDocumentOuterHTML(long paramLong);
  
  private native int nativeGetGoBackOrForwardToDesiredSteps(long paramLong, int paramInt);
  
  private native void nativeGetInputBoxNum(long paramLong, Callback<Bundle> paramCallback);
  
  private native int nativeGetRoutingID(long paramLong);
  
  private native String nativeGetUrlByPageID(long paramLong, int paramInt);
  
  private native void nativeGoToPrereading(long paramLong, String paramString);
  
  private native long nativeInit(WebContents paramWebContents);
  
  private native void nativeMarkScrollOffsetForMoveRange(long paramLong, boolean paramBoolean);
  
  private native void nativeMoveCaret(long paramLong, float paramFloat1, float paramFloat2);
  
  private native void nativeOnAppExit(long paramLong);
  
  private native void nativeOnAudioStateChanged(long paramLong, boolean paramBoolean);
  
  private native void nativeOnJavaWebViewChromiumExtensionDestroyed(long paramLong);
  
  private native void nativeOnQBVideoSearchResult(long paramLong, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11);
  
  private native void nativeOnRetrieveFingerSearchContext(long paramLong, int paramInt);
  
  private native void nativeOnSizeChanged(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  private native void nativePluginSurfaceCreated(long paramLong, boolean paramBoolean);
  
  private native void nativePreLoad(long paramLong, String[] paramArrayOfString);
  
  private static native void nativeRefreshPlugins(String paramString);
  
  private native void nativeReloadCustomMetaData(long paramLong);
  
  private native void nativeRemoveNavigationEntryAtIndex(long paramLong, int paramInt);
  
  private native void nativeRequestExplorerStatistics(long paramLong);
  
  private native void nativeRequestFocusForInputNode(long paramLong1, long paramLong2);
  
  private native void nativeSavePasswordResponse(long paramLong, String paramString);
  
  private native void nativeSelectBetweenCoordinates(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
  
  private native void nativeSelectWordAroundPosition(long paramLong, float paramFloat1, float paramFloat2);
  
  private native void nativeServiceWorkerBackgroundRegister(long paramLong, String paramString1, String paramString2);
  
  private native void nativeServiceWorkerBackgroundUpdate(long paramLong, String paramString);
  
  private native void nativeSetContentCacheCurrentFrameWhenJsLocation(long paramLong, boolean paramBoolean);
  
  private native void nativeSetForbitInputPassword(long paramLong);
  
  private native void nativeSetRecognisedText(long paramLong, String[] paramArrayOfString);
  
  private native void nativeShowImage(long paramLong, float paramFloat1, float paramFloat2);
  
  private native void nativeStopPreLoad(long paramLong, String paramString);
  
  private native void nativeTakeCurrentScreenshot(long paramLong, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2);
  
  private native void nativeTakeCurrentScreenshotThreaded(long paramLong, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, int paramInt3, boolean paramBoolean);
  
  private native boolean nativeTakeScreenshotWithPageID(long paramLong, int paramInt, Bitmap paramBitmap);
  
  private native void nativeToCapturePageInfo(long paramLong, String paramString);
  
  private native void nativeUpdateImageList(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);
  
  private native void nativeUploadBlockInfo(long paramLong);
  
  private native void nativeWasResized(long paramLong);
  
  private native void nativedoFingerSearchIfNeed(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);
  
  private native void nativeonFingerSearchResult(long paramLong, String paramString, int paramInt1, int paramInt2);
  
  @CalledByNative
  private void notifyCurrentNavigationEntryChanged(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, Bitmap paramBitmap)
  {
    changeOverScrollState(paramInt2);
    resetPrereadingState();
    if (this.mWebBackForwardListClient != null)
    {
      if (this.mLastNavigationIndex != paramInt1) {
        this.mForceNoNeedsScroll = true;
      }
      this.mWebBackForwardListClient.onIndexChanged(new TencentWebHistoryItemChromium(paramInt1, paramInt2, paramString1, paramString3, paramString4, paramBitmap), paramInt1);
      if (this.mLastNavigationIndex != paramInt1) {
        this.mForceNoNeedsScroll = false;
      }
    }
    this.mContentsClientAdapter.onReceivedTitle(paramString4);
    this.mPopwindowBlockedList.clear();
    setCurrentNavigationEntry(paramInt1, paramInt2, paramString1, paramString4);
    paramString2 = this.mAwContents;
    if ((paramString2 != null) && (paramString2.isPageVisible()))
    {
      WebRtcUtils.a(this.mCurrentReportPageIndex, paramString1);
      WebARCameraUtils.a(this.mCurrentReportPageIndex, paramString1);
      WebARCameraUtils.a(this.mWebView, this.mWebChromeClientExtension);
    }
  }
  
  private void notifyJavaScriptOpenWindowsBlocked(final String paramString1, String paramString2, boolean paramBoolean)
  {
    if (this.mPopwindowBlockedList.size() >= 10) {
      return;
    }
    this.mPopwindowBlockedList.add(paramString2);
    if (this.mWebViewClientExtension != null)
    {
      paramString2 = new android.webkit.ValueCallback()
      {
        public void a(Boolean paramAnonymousBoolean)
        {
          if (paramAnonymousBoolean.booleanValue()) {
            TencentWebViewDatabaseAdapter.getInstance(WebViewChromiumExtension.this.mWebView.getContext()).setJavaScriptOpenWindowAutomaticallyOperation(paramString1, 1);
          } else {
            TencentWebViewDatabaseAdapter.getInstance(WebViewChromiumExtension.this.mWebView.getContext()).setJavaScriptOpenWindowAutomaticallyOperation(paramString1, 0);
          }
          WebViewChromiumExtension.this.openJavaScriptBlockedWindows();
        }
      };
      try
      {
        Method localMethod = this.mWebViewClientExtension.getClass().getMethod("notifyJavaScriptOpenWindowsBlocked", new Class[] { String.class, String[].class, android.webkit.ValueCallback.class, Boolean.TYPE });
        localMethod.setAccessible(true);
        localMethod.invoke(this.mWebViewClientExtension, new Object[] { paramString1, this.mPopwindowBlockedList.toArray(new String[this.mPopwindowBlockedList.size()]), paramString2, Boolean.valueOf(paramBoolean) });
        return;
      }
      catch (Throwable paramString1)
      {
        paramString1.printStackTrace();
      }
    }
  }
  
  @CalledByNative
  private void notifyNavigationEntryAdded(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, Bitmap paramBitmap)
  {
    paramString2 = this.mAwContents;
    if ((paramString2 != null) && (paramString2.getTencentSettings() != null)) {
      changeOverScrollState(paramInt2, this.mAwContents.getTencentSettings().getDefaultOverScrollState());
    }
    paramString2 = this.mAwContents;
    if ((paramString2 != null) && (paramString2.isPageVisible()))
    {
      WebRtcUtils.a(this.mCurrentReportPageIndex, paramString1);
      WebARCameraUtils.a(this.mCurrentReportPageIndex, paramString1);
      WebARCameraUtils.a(this.mWebView, this.mWebChromeClientExtension);
    }
    resetPrereadingState();
    setCurrentNavigationEntry(paramInt1, paramInt2, paramString1, paramString4);
    paramString2 = this.mWebBackForwardListClient;
    if (paramString2 == null) {
      return;
    }
    this.mForceNoNeedsScroll = true;
    paramString2.onNewHistoryItem(new TencentWebHistoryItemChromium(paramInt1, paramInt2, paramString1, paramString3, paramString4, paramBitmap));
    this.mForceNoNeedsScroll = false;
  }
  
  @CalledByNative
  private void notifyNavigationEntryRemoved(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, Bitmap paramBitmap)
  {
    if ((paramInt1 == this.mLastNavigationIndex) && (paramInt2 == this.mLastNavigationPageId) && (paramString1 == this.mLastNavigationUrl)) {
      setCurrentNavigationEntry(-1, -1, "", "");
    }
    paramString2 = this.mWebBackForwardListClient;
    if (paramString2 == null) {
      return;
    }
    paramString2.onRemoveHistoryItem(new TencentWebHistoryItemChromium(paramInt1, paramInt2, paramString1, paramString3, paramString4, paramBitmap));
  }
  
  private void notifyQProxyResponseReceived(final String paramString)
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        WebViewChromiumExtension.this.mWebView.notifyQProxyResponseReceived(paramString);
      }
    });
  }
  
  @CalledByNative
  private void onColorModeChanged(long paramLong)
  {
    if (this.mWebChromeClientExtension == null) {
      return;
    }
    try
    {
      if (this.mSettingsExtension.getShouldBubbleColorModeChanged())
      {
        this.mWebChromeClientExtension.onColorModeChanged(paramLong);
        this.mSettingsExtension.setShouldBubbleColorModeChanged(false);
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  @CalledByNative
  private void onDeadCodeDetectionDone()
  {
    if ((this.ENABLE_DEAD_CODE_DETECTION_IN_APP) && (MAIN_THREAD_ALREADY_BLOCKED))
    {
      SmttServiceProxy.getInstance().clearDeadCode();
      MAIN_THREAD_ALREADY_BLOCKED = false;
      startMainThreadBlockedDetect();
    }
    this.mHandler.removeMessages(2);
    this.mHandler.removeMessages(9);
  }
  
  @CalledByNative
  private static void onEvaluateTencentResult(int paramInt, Callback<Bundle> paramCallback)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("input-box-num", paramInt);
    paramCallback.onResult(localBundle);
  }
  
  private void onFakeLoginRecognised(Bundle paramBundle)
  {
    try
    {
      if (this.mWebViewClientExtension == null) {
        return;
      }
      this.mWebViewClientExtension.onFakeLoginRecognised(paramBundle);
      return;
    }
    catch (NoSuchMethodError paramBundle)
    {
      paramBundle.printStackTrace();
    }
  }
  
  private void onFingerSearchTimeOut()
  {
    if (!this.mHandler.hasMessages(14)) {
      this.mHandler.sendEmptyMessageDelayed(14, 1200L);
    }
  }
  
  @CalledByNative
  private void onHideAddressBar() {}
  
  @CalledByNative
  private void onMissingPluginClicked(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension == null) {
      return;
    }
    localIX5WebViewClientExtension.onMissingPluginClicked(paramString1, paramString2, paramString3, paramInt);
  }
  
  private void onPermissionRequestDeny(long paramLong, String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onPermissionRequestDeny resources: ");
    ((StringBuilder)localObject).append(paramLong);
    LiveLog.d("WebViewChromiumExtension", ((StringBuilder)localObject).toString());
    int j = 0;
    int i;
    if ((0x2 & paramLong) != 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if ((paramLong & 0x4) != 0L) {
      j = 1;
    }
    localObject = this.mAwContents.getMediaAccessPermissions();
    if (i != 0) {
      ((AwMediaAccessPermissions)localObject).deny(paramString, 1);
    }
    if (j != 0) {
      ((AwMediaAccessPermissions)localObject).deny(paramString, 2);
    }
  }
  
  private void onPermissionRequestGrant(long paramLong, String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onPermissionRequestGrant resources: ");
    ((StringBuilder)localObject).append(paramLong);
    LiveLog.d("WebViewChromiumExtension", ((StringBuilder)localObject).toString());
    int j = 0;
    int i;
    if ((0x2 & paramLong) != 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if ((paramLong & 0x4) != 0L) {
      j = 1;
    }
    localObject = this.mAwContents.getMediaAccessPermissions();
    if (i != 0) {
      ((AwMediaAccessPermissions)localObject).allow(paramString, 1);
    }
    if (j != 0) {
      ((AwMediaAccessPermissions)localObject).allow(paramString, 2);
    }
  }
  
  @CalledByNative
  private void onPrintPage()
  {
    IX5WebChromeClientExtension localIX5WebChromeClientExtension = this.mWebChromeClientExtension;
    if (localIX5WebChromeClientExtension == null) {
      return;
    }
    localIX5WebChromeClientExtension.onPrintPage();
  }
  
  @CalledByNative
  private void onReceiveNewImageInfo(String paramString, long paramLong, boolean paramBoolean)
  {
    try
    {
      IX5WebViewBase.ImageInfo localImageInfo = new IX5WebViewBase.ImageInfo();
      localImageInfo.mPicUrl = paramString;
      localImageInfo.mRawDataSize = paramLong;
      try
      {
        localImageInfo.mIsGif = paramBoolean;
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
      }
      this.mImageList.add(localImageInfo);
    }
    catch (NoClassDefFoundError paramString)
    {
      paramString.printStackTrace();
    }
    if ((this.mUpdataImageCallback != null) && (!this.mHasCallbackTask))
    {
      this.mHasCallbackTask = true;
      ThreadUtils.postOnUiThreadDelayed(new Runnable()
      {
        public void run()
        {
          if (WebViewChromiumExtension.this.mUpdataImageCallback != null)
          {
            WebViewChromiumExtension.this.mUpdataImageCallback.onReceiveValue(Integer.valueOf(0));
            WebViewChromiumExtension.access$1002(WebViewChromiumExtension.this, null);
          }
        }
      }, 10L);
    }
    this.mIsImageListUpdated = true;
  }
  
  @CalledByNative
  private void onReceivedMetas(final Object paramObject)
  {
    if (paramObject != null) {
      paramObject = (HashMap)paramObject;
    } else {
      paramObject = null;
    }
    Object localObject = this.mWebChromeClientExtension;
    if (localObject != null) {
      ((IX5WebChromeClientExtension)localObject).onAllMetaDataFinished((IX5WebViewExtension)this.mWebView, (HashMap)paramObject);
    }
    localObject = (String)((HashMap)paramObject).get("x5-screen-on");
    if ((this.mWebView.getContext() instanceof Activity)) {
      if ((localObject != null) && (canScreenOn()) && ("true".equalsIgnoreCase((String)localObject))) {
        X5CoreWakeLockManager.getInstance().acquireWakeLock(this.mWakeLockUser, (Activity)this.mWebView.getContext());
      } else {
        X5CoreWakeLockManager.getInstance().releaseWakeLock(this.mWakeLockUser, (Activity)this.mWebView.getContext());
      }
    }
    if (paramObject != null) {
      paramObject = (String)((HashMap)paramObject).get("x5-force-scale");
    } else {
      paramObject = "";
    }
    this.mWebView.getRealWebView().post(new Runnable()
    {
      public void run()
      {
        if ((!TextUtils.isEmpty(paramObject)) && (paramObject.equalsIgnoreCase("false")))
        {
          WebViewChromiumExtension.this.disabledForceScaleState(true);
          return;
        }
        WebViewChromiumExtension.this.disabledForceScaleState(false);
      }
    });
  }
  
  @CalledByNative
  private void onUpdatePreNextInputNodeResult(long paramLong1, long paramLong2)
  {
    if ((paramLong1 == this.mPreEditableNode) && (paramLong2 == this.mNextEditableNode)) {
      return;
    }
    this.mPreEditableNode = paramLong1;
    this.mNextEditableNode = paramLong2;
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension == null) {
      return;
    }
    paramLong1 = this.mPreEditableNode;
    boolean bool2 = true;
    boolean bool1;
    if (paramLong1 != 0L) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if (this.mNextEditableNode == 0L) {
      bool2 = false;
    }
    localIX5WebViewClientExtension.onSetButtonStatus(bool1, bool2);
  }
  
  @CalledByNative
  private void onUpdateSearchResultDataList(boolean paramBoolean)
  {
    List localList = this.mSearchResultDataList;
    if (localList == null)
    {
      this.mSearchResultDataList = new ArrayList();
      return;
    }
    localList.clear();
  }
  
  private void onUploadLiveLogFinished(boolean paramBoolean)
  {
    if (this.mWebViewClientExtension != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putBoolean("result", paramBoolean);
      this.mWebViewClientExtension.onMiscCallBack("onUploadLiveLogFinished", localBundle);
    }
  }
  
  @CalledByNative
  private void onUploadSearchResultData(int paramInt1, String paramString1, int paramInt2, int paramInt3, int paramInt4, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt5, int paramInt6)
  {
    if (this.mSearchResultDataList == null) {
      return;
    }
    Object localObject = new j(paramInt1, paramString1, paramInt2, paramInt3, paramInt4, paramString2, paramString3, paramString4, paramString5, paramInt5, paramInt6);
    this.mSearchResultDataList.add(localObject);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Receive Data:  id=");
    ((StringBuilder)localObject).append(paramInt1);
    ((StringBuilder)localObject).append(" keyword=");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(" order=");
    ((StringBuilder)localObject).append(paramInt2);
    ((StringBuilder)localObject).append(" type=");
    ((StringBuilder)localObject).append(paramInt3);
    ((StringBuilder)localObject).append(" height=");
    ((StringBuilder)localObject).append(paramInt4);
    ((StringBuilder)localObject).append(" tpl=");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(" title=");
    ((StringBuilder)localObject).append(paramString3);
    ((StringBuilder)localObject).append(" url=");
    ((StringBuilder)localObject).append(paramString4);
    ((StringBuilder)localObject).append(" host=");
    ((StringBuilder)localObject).append(paramString5);
    ((StringBuilder)localObject).append(" titleRed=");
    ((StringBuilder)localObject).append(paramInt5);
    ((StringBuilder)localObject).append(" imgCount=");
    ((StringBuilder)localObject).append(paramInt6);
    MttLog.d("search_result", ((StringBuilder)localObject).toString());
  }
  
  @CalledByNative
  private void onVideoElementCreated() {}
  
  private void openJavaScriptBlockedWindows()
  {
    if (Looper.myLooper() != Looper.getMainLooper())
    {
      this.mHandler.post(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.openJavaScriptBlockedWindows();
        }
      });
      return;
    }
    int i = 0;
    while (i < this.mPopwindowBlockedList.size())
    {
      final String str = (String)this.mPopwindowBlockedList.get(i);
      this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          Object localObject = new StringBuilder();
          ((StringBuilder)localObject).append("javascript:window.open(\"");
          ((StringBuilder)localObject).append(str);
          ((StringBuilder)localObject).append("\")");
          localObject = ((StringBuilder)localObject).toString();
          WebViewChromiumExtension.this.mWebView.loadUrl((String)localObject);
        }
      }, i * 250);
      i += 1;
    }
    this.mPopwindowBlockedList.clear();
  }
  
  private void preLoad(List<String> paramList)
  {
    if (paramList != null)
    {
      if (paramList.size() == 0) {
        return;
      }
      if (!isPrerenderEnabled()) {
        return;
      }
      if (this.mNativeWebViewChromiumExtension == 0L) {
        return;
      }
      if (preLoadHistory == null) {
        preLoadHistory = new HashSet();
      }
      String[] arrayOfString = new String[paramList.size()];
      int i = 0;
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        String str = (String)paramList.next();
        if ((str != null) && (!TextUtils.isEmpty(str)))
        {
          arrayOfString[i] = str;
          preLoadHistory.add(str);
          i += 1;
        }
      }
      nativePreLoad(this.mNativeWebViewChromiumExtension, arrayOfString);
      return;
    }
  }
  
  private void reportPreloadStatus(int paramInt)
  {
    HashMap localHashMap = new HashMap();
    Object localObject = new int[4];
    localObject[(paramInt + 1)] = 1;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(localObject[0]);
    localHashMap.put("preloadBegin", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(localObject[1]);
    localHashMap.put("preloadEnd", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(localObject[2]);
    localHashMap.put("preloadMatched", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(localObject[3]);
    localHashMap.put("preloadFailed", localStringBuilder.toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("WebViewChromiumExtension::reportPreloadStatus  report=");
    ((StringBuilder)localObject).append(localHashMap.toString());
    MttLog.d("preload", ((StringBuilder)localObject).toString());
    SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_PRERENDER", localHashMap);
  }
  
  private void resetBlankScreenStatus()
  {
    this.mBlankScreenStatus = -1;
    this.mLastBlankScreenStatus = 4;
  }
  
  private void resetTouchEventCount()
  {
    TencentAwContent localTencentAwContent = this.mAwContents;
    if (localTencentAwContent != null) {
      localTencentAwContent.resetTouchEventCount();
    }
  }
  
  @CalledByNative
  private void retrieveFingerSearchContext(String paramString1, String paramString2, int paramInt)
  {
    ISelectionInterface localISelectionInterface = this.mSelectionListener;
    if (localISelectionInterface == null) {
      return;
    }
    try
    {
      localISelectionInterface.onRetrieveFingerSearchContextResponse(paramString1, paramString2, paramInt);
      return;
    }
    catch (Throwable paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  private void runVoidTaskOnUiThreadBlockingIgnoreException(Runnable paramRunnable)
  {
    try
    {
      paramRunnable = new FutureTask(paramRunnable, null);
      this.mFactory.getRunQueue().runBlockingFuture(paramRunnable);
      return;
    }
    catch (Exception paramRunnable)
    {
      paramRunnable.printStackTrace();
    }
  }
  
  private void saveDeadDump()
  {
    if ((this.ENABLE_DEAD_CODE_DETECTION_IN_APP) && (MAIN_THREAD_ALREADY_BLOCKED)) {
      SmttServiceProxy.getInstance().saveDeadDump();
    }
  }
  
  private void setCurrentNavigationEntry(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    this.mLastNavigationIndex = paramInt1;
    this.mLastNavigationPageId = paramInt2;
    this.mLastNavigationUrl = paramString1;
    this.mLastNavigationTitle = paramString2;
  }
  
  @CalledByNative
  private void setNetWorkLoadPolicyToCacheFirst(String paramString)
  {
    this.mContentsClientAdapter.setNetWorkLoadPolicyToCacheFirst(paramString);
  }
  
  @CalledByNative
  private void showFullScreenPlugin(Object paramObject, int paramInt1, int paramInt2)
  {
    IX5WebChromeClientExtension localIX5WebChromeClientExtension = this.mWebChromeClientExtension;
    if (localIX5WebChromeClientExtension != null) {
      localIX5WebChromeClientExtension.requestFullScreenFlash();
    }
    if (isPluginFullScreen()) {
      hideFullScreenPlugin();
    }
    paramObject = ((s.a)paramObject).a;
    if ((paramObject instanceof SurfaceView))
    {
      this.mPluginSurfaceView = ((SurfaceView)paramObject);
      this.mSurfaceCallback = new SurfaceHolder.Callback()
      {
        public void surfaceChanged(SurfaceHolder paramAnonymousSurfaceHolder, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public void surfaceCreated(SurfaceHolder paramAnonymousSurfaceHolder)
        {
          paramAnonymousSurfaceHolder = WebViewChromiumExtension.this;
          paramAnonymousSurfaceHolder.nativePluginSurfaceCreated(paramAnonymousSurfaceHolder.mNativeWebViewChromiumExtension, true);
        }
        
        public void surfaceDestroyed(SurfaceHolder paramAnonymousSurfaceHolder)
        {
          paramAnonymousSurfaceHolder = WebViewChromiumExtension.this;
          paramAnonymousSurfaceHolder.nativePluginSurfaceCreated(paramAnonymousSurfaceHolder.mNativeWebViewChromiumExtension, false);
        }
      };
      this.mPluginSurfaceView.getHolder().addCallback(this.mSurfaceCallback);
    }
    this.mFullScreenHolder = new l(this.mWebView, paramInt1);
    this.mFullScreenHolder.a((View)paramObject);
    this.mFullScreenHolder.a();
    this.mWebView.getRealWebView().invalidate();
  }
  
  @CalledByNative
  private void showLongClickPopupMenu(boolean paramBoolean)
  {
    int i = 0;
    this.isExistFingerSearchClient = false;
    this.mIsFingerSeaching = false;
    if (this.mWebView != null)
    {
      reportDoLongClick();
      if ((!Apn.isWifiMode()) && (!Apn.is3GMode()) && (!Apn.is4GMode()))
      {
        if (e.a().a(this.mWebView)) {
          notifyWXEnterSelectionMode(false);
        }
      }
      else {
        doFingerSearchIfNeed();
      }
    }
    if ((this.mWebView.getRealWebView().getParent() != null) && (this.mWebView.getRealWebView().getVisibility() == 0))
    {
      Object localObject1 = this.mWebViewClientExtension;
      if (localObject1 != null)
      {
        try
        {
          localObject1 = localObject1.getClass().getMethod("onShowLongClickPopupMenu", new Class[0]);
          ((Method)localObject1).setAccessible(true);
          boolean bool = ((Boolean)((Method)localObject1).invoke(this.mWebViewClientExtension, new Object[0])).booleanValue();
          i = bool;
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
        if (i == 0)
        {
          Object localObject2 = this.mWebView;
          if ((localObject2 instanceof X5WebViewAdapter))
          {
            localObject2 = (X5WebViewAdapter)localObject2;
            this.mSelectable = paramBoolean;
            if ((!e.a().a(this.mWebView)) || (((X5WebViewAdapter)localObject2).imageQueryFromX5Core())) {
              ((X5WebViewAdapter)localObject2).onShowLongClickPopupMenu(paramBoolean);
            }
          }
        }
      }
    }
  }
  
  @CalledByNative
  private void upLoadToBeacon(String paramString, Object paramObject)
  {
    if ((paramObject != null) && ((paramObject instanceof HashMap)))
    {
      paramObject = (HashMap)paramObject;
      SmttServiceProxy.getInstance().upLoadToBeacon(paramString, (Map)paramObject);
    }
  }
  
  @CalledByNative
  private static void updateHashMap(Object paramObject, String paramString1, String paramString2)
  {
    if (paramObject != null)
    {
      paramObject = (HashMap)paramObject;
      if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2))) {
        ((HashMap)paramObject).put(paramString1, paramString2);
      }
    }
  }
  
  @CalledByNative
  private void updateImeAdapter(int paramInt, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension == null) {
      return;
    }
    localIX5WebViewClientExtension.onInputBoxTextChanged((IX5WebViewExtension)this.mWebView, paramString);
    paramString = this.mWebViewClientExtension;
    long l = this.mPreEditableNode;
    paramBoolean2 = true;
    if (l != 0L) {
      paramBoolean1 = true;
    } else {
      paramBoolean1 = false;
    }
    if (this.mNextEditableNode == 0L) {
      paramBoolean2 = false;
    }
    paramString.onSetButtonStatus(paramBoolean1, paramBoolean2);
  }
  
  private void uploadBlockInfo()
  {
    if (this.ENABLE_DEAD_CODE_DETECTION_IN_APP)
    {
      if (MAIN_THREAD_ALREADY_BLOCKED) {
        return;
      }
      if (this.mNativeWebViewChromiumExtension == 0L) {
        return;
      }
      MAIN_THREAD_ALREADY_BLOCKED = true;
      stopMainThreadBlockedDetect();
      nativeUploadBlockInfo(this.mNativeWebViewChromiumExtension);
      this.mHandler.sendEmptyMessageDelayed(9, 8000L);
      return;
    }
  }
  
  @CalledByNative
  private boolean useAudioFramePlayer(String paramString1, String paramString2, String paramString3)
  {
    return SmttServiceProxy.getInstance().shouldUseTbsMediaPlayer(paramString1);
  }
  
  @CalledByNative
  public void BlockAPopupWindow(String paramString, boolean paramBoolean)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension == null) {
      return;
    }
    localIX5WebViewClientExtension.onReportAdFilterInfo(0, 1, paramString, paramBoolean);
  }
  
  public void OnQBVideoSearchResult(Map<String, String> paramMap)
  {
    if (this.mNativeWebViewChromiumExtension == 0L) {
      return;
    }
    String str1 = (String)paramMap.get("mainUrl");
    String str2 = (String)paramMap.get("imageUrl");
    String str3 = (String)paramMap.get("iconUrl");
    String str4 = (String)paramMap.get("title");
    String str5 = (String)paramMap.get("vid");
    String str6 = (String)paramMap.get("duration");
    String str7 = (String)paramMap.get("cue");
    String str8 = (String)paramMap.get("shorterCue");
    String str9 = (String)paramMap.get("displayNormal");
    String str10 = (String)paramMap.get("jumpUrl");
    paramMap = (String)paramMap.get("md5");
    nativeOnQBVideoSearchResult(this.mNativeWebViewChromiumExtension, str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, paramMap);
  }
  
  @CalledByNative
  public void OnReportAdHiddenNum(int paramInt)
  {
    SmttServiceProxy.getInstance().reportAdHiddenNum(this.mWebView.getRealWebView().getUrl(), paramInt);
    com.tencent.smtt.net.b.a().a(this, this.mWebView.getRealWebView().getUrl(), paramInt);
  }
  
  @CalledByNative
  public void OnReportInspectedURL(String paramString, int paramInt)
  {
    paramString = new f(paramString, paramInt);
    Handler localHandler = this.mHandler;
    localHandler.sendMessage(localHandler.obtainMessage(8, paramString));
  }
  
  @CalledByNative
  public void RecordDirectAdBlockInfo(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
  {
    com.tencent.smtt.net.b.a().a(this, this.mWebView.getRealWebView().getUrl(), paramString2);
  }
  
  @CalledByNative
  public void SendSearchClickInfoToUI(int paramInt)
  {
    MttLog.d("WebViewChromiumExtension", "BDSearchPredict-SendSearchClickInfoToUI");
    int m = paramInt & 0x7;
    int i = paramInt >> 3;
    if (i == this.mLastsearchID) {
      return;
    }
    Object localObject = this.mSearchResultDataList;
    if ((localObject != null) && (((List)localObject).size() >= 4))
    {
      localObject = this.mSearchResultDataList;
      int k = 0;
      if (((j)((List)localObject).get(0)).jdField_a_of_type_Int != i) {
        return;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("BDSearchPredict-SendSearchClickInfoToUI top: ");
      ((StringBuilder)localObject).append(paramInt);
      ((StringBuilder)localObject).append(" id: ");
      ((StringBuilder)localObject).append(i);
      ((StringBuilder)localObject).append("  click_order: ");
      ((StringBuilder)localObject).append(m);
      MttLog.d("WebViewChromiumExtension", ((StringBuilder)localObject).toString());
      double d1 = 0.0D;
      this.mLastsearchID = i;
      paramInt = 0;
      int j;
      for (i = 0;; i = j)
      {
        j = k;
        if (paramInt >= this.mSearchResultDataList.size()) {
          break;
        }
        localObject = (j)this.mSearchResultDataList.get(paramInt);
        j = i;
        if (((j)localObject).c == 0)
        {
          i += 1;
          if (((j)localObject).b != 1)
          {
            j = i;
            if (i <= 2) {}
          }
          else
          {
            MttLog.d("WebViewChromiumExtension", "BDSearchPredict-SendSearchClickInfoToUI media too much");
            return;
          }
        }
        if (!((j)localObject).e.isEmpty()) {
          ((j)localObject).e = TencentURLUtil.getHost(((j)localObject).e);
        }
        if (((j)localObject).b == m)
        {
          ((j)localObject).g = 1;
          n.a().a(m, ((j)localObject).e);
        }
        if (n.a().a(((j)localObject).e)) {
          ((j)localObject).h = 1;
        }
        ((j)localObject).i = n.a().a(((j)localObject).b);
        double d2 = ((j)this.mSearchResultDataList.get(paramInt)).jdField_d_of_type_Int;
        Double.isNaN(d2);
        d1 += d2;
        paramInt += 1;
      }
      while (j < this.mSearchResultDataList.size())
      {
        DoHandleAndUpload((j)this.mSearchResultDataList.get(j), d1);
        j += 1;
      }
      return;
    }
  }
  
  public void active()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.active();
        }
      });
      return;
    }
    this.mWebViewChromium.onResume();
  }
  
  @CalledByNative
  public boolean allowJavaScriptOpenWindowAutomatically(final String paramString1, final String paramString2, String paramString3)
  {
    paramString2 = this.mAwContents.getWebContents().getVisibleUrl();
    if (paramString2 == null) {
      paramString2 = "";
    }
    paramString2 = UrlUtils.getHost(paramString2);
    final int i = TencentWebViewDatabaseAdapter.getInstance(this.mWebView.getContext()).getJavaScriptOpenWindowAutomaticallyOperation(paramString2);
    boolean bool1;
    if (i == 1) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    boolean bool2 = bool1;
    if (!bool1)
    {
      paramString3 = this.mWebViewClientExtension;
      bool2 = bool1;
      if (paramString3 != null)
      {
        try
        {
          paramString3 = paramString3.getClass().getMethod("allowJavaScriptOpenWindowAutomatically", new Class[] { String.class, String.class });
          paramString3.setAccessible(true);
          bool2 = ((Boolean)paramString3.invoke(this.mWebViewClientExtension, new Object[] { paramString2, paramString1 })).booleanValue();
          bool1 = bool2;
        }
        catch (Throwable paramString3)
        {
          paramString3.printStackTrace();
        }
        bool2 = bool1;
        if (!bool1)
        {
          this.mHandler.post(new Runnable()
          {
            public void run()
            {
              if ((WebViewChromiumExtension.this.mWebView.getSettingsExtension() != null) && (WebViewChromiumExtension.this.mWebView.getSettingsExtension().getJavaScriptOpenWindowsBlockedNotifyEnabled()))
              {
                WebViewChromiumExtension localWebViewChromiumExtension = WebViewChromiumExtension.this;
                String str1 = paramString2;
                String str2 = paramString1;
                boolean bool;
                if (i == 0) {
                  bool = true;
                } else {
                  bool = false;
                }
                localWebViewChromiumExtension.notifyJavaScriptOpenWindowsBlocked(str1, str2, bool);
              }
            }
          });
          bool2 = bool1;
        }
      }
    }
    return bool2;
  }
  
  public boolean canDrawBaseLayer(int paramInt)
  {
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return false;
    }
    return nativeCanTakeScreenshotWithPageID(l, paramInt);
  }
  
  boolean canScreenOn()
  {
    return ((this.mWebView.getContext() instanceof Activity)) && (!((Activity)this.mWebView.getContext()).isFinishing());
  }
  
  boolean canShowAlertDialog()
  {
    Activity localActivity = WindowAndroid.activityFromContext(this.mWebView.getContext());
    return (localActivity != null) && (!localActivity.isFinishing());
  }
  
  public void cancelFingerSearch(boolean paramBoolean)
  {
    e locale = this.mFingerSearchClient;
    if (locale != null) {
      locale.a(paramBoolean);
    }
    resetFingerSearchClient();
  }
  
  public void cancelFling(final long paramLong)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.cancelFling(paramLong);
        }
      });
      return;
    }
    if (this.mAwContents.getContentViewCore() != null) {
      this.mAwContents.getContentViewCore().getWebContents().getEventForwarder().onCancelFling(paramLong);
    }
  }
  
  public Picture capturePicture()
  {
    Picture localPicture = new Picture();
    float f = this.mWebView.getContext().getApplicationContext().getResources().getDisplayMetrics().density;
    try
    {
      Bitmap localBitmap = Bitmap.createBitmap((int)(this.mWebViewChromium.getContentWidth() * f), (int)(this.mWebViewChromium.getContentHeight() * f), Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      localCanvas.scale(f, f);
      snapshotWholePage(localCanvas, false);
      localPicture.beginRecording((int)(this.mWebViewChromium.getContentWidth() * f), (int)(this.mWebViewChromium.getContentHeight() * f)).drawBitmap(localBitmap, 0.0F, 0.0F, new Paint());
      localPicture.endRecording();
      new CleanupReference(localPicture, new c(localBitmap, null));
      return localPicture;
    }
    catch (Throwable localThrowable) {}
    return localPicture;
  }
  
  @CalledByNative
  public void capturedPageInfo(String arg1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if (???.equalsIgnoreCase("about:blank")) {
      return;
    }
    r localr = this.mTranslateHelper;
    if (localr == null) {
      this.mTranslateHelper = new r(this);
    } else {
      localr.a();
    }
    if ((TextUtils.isEmpty(paramString2)) && (TextUtils.isEmpty(paramString3)) && (TextUtils.isEmpty(paramString4)) && (TextUtils.isEmpty(paramString5)))
    {
      this.mTranslateHelper.a(false);
      return;
    }
    this.mTranslateHelper.b(???);
    this.mTranslateHelper.a(true);
    if (this.mTranslateHelper.a(???)) {
      return;
    }
    if ((??? != null) && (???.equalsIgnoreCase("InitiativeRequest")))
    {
      this.mTranslateHelper.a().e();
      this.mTranslateHelper.b(this.mWebView.getUrl());
      if (this.mTranslateHelper.a(paramString2, paramString3, paramString4, paramString5))
      {
        this.mTranslateHelper.a("en");
        doTranslateAction(0);
        this.mTranslateHelper.a().k();
        return;
      }
      showTranslateBubble(-2, "", "", -101);
      this.mTranslateHelper.a().d();
      return;
    }
    this.mWebContent = paramString5;
    if (this.mTranslateHelper.a(paramString2, paramString3, paramString4, paramString5))
    {
      this.mTranslateHelper.a("en");
      showTranslateBubble(1, "en", "zh", 0);
      this.mTranslateHelper.a().k();
      this.mWXTranslateHelper.a();
    }
    else
    {
      this.mWXTranslateHelper.b(this.mWebContent);
    }
    try
    {
      synchronized (this.mGetWebContentLock)
      {
        this.mGetWebContentLock.notifyAll();
      }
    }
    catch (Exception paramString2)
    {
      paramString2.printStackTrace();
      return;
    }
  }
  
  public void changeOverScrollState(int paramInt)
  {
    Integer localInteger = new Integer(paramInt);
    TencentAwContent localTencentAwContent = this.mAwContents;
    if (localTencentAwContent != null)
    {
      if (localTencentAwContent.getTencentSettings() == null) {
        return;
      }
      boolean bool = this.mDisableOverScrollPageId.contains(localInteger);
      this.mAwContents.getTencentSettings().setOverScrollState(bool ^ true);
      return;
    }
  }
  
  public void changeOverScrollState(int paramInt, boolean paramBoolean)
  {
    Integer localInteger = new Integer(paramInt);
    TencentAwContent localTencentAwContent = this.mAwContents;
    if (localTencentAwContent != null)
    {
      if (localTencentAwContent.getTencentSettings() == null) {
        return;
      }
      this.mAwContents.getTencentSettings().setOverScrollState(paramBoolean);
      if (paramBoolean)
      {
        if (this.mDisableOverScrollPageId.contains(localInteger)) {
          this.mDisableOverScrollPageId.remove(localInteger);
        }
      }
      else if (!this.mDisableOverScrollPageId.contains(localInteger)) {
        this.mDisableOverScrollPageId.add(localInteger);
      }
      return;
    }
  }
  
  boolean checkNeedsPost()
  {
    boolean bool;
    if ((this.mFactory.hasStarted()) && (ThreadUtils.runningOnUiThread())) {
      bool = false;
    } else {
      bool = true;
    }
    if (!bool)
    {
      if (this.mAwContents != null) {
        return bool;
      }
      throw new IllegalStateException("AwContents must be created if we are not posting!");
    }
    return bool;
  }
  
  public void checkSecurityLevel(final String paramString1, final String paramString2, final int paramInt)
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (WebViewChromiumExtension.this.getQQBrowserClient() != null)
        {
          WebViewChromiumExtension.this.getQQBrowserClient().checkSecurityLevel(paramString1, paramString2, paramInt);
          return;
        }
        JSONObject localJSONObject = new JSONObject();
        try
        {
          localJSONObject.put("url", paramString1);
          localJSONObject.put("referer", paramString2);
          localJSONObject.put("resourceType", paramInt);
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
        }
        WebViewChromiumExtension.this.mContentsClientAdapter.notifySecurityLevel(localJSONObject);
      }
    });
  }
  
  public void chooseFavorites()
  {
    e locale = this.mFingerSearchClient;
    if (locale != null) {
      locale.i();
    }
  }
  
  public void chooseTranslation()
  {
    e locale = this.mFingerSearchClient;
    if (locale != null) {
      locale.h();
    }
  }
  
  public void clearFingerSearchResponse()
  {
    e locale = this.mFingerSearchClient;
    if (locale != null) {
      locale.a();
    }
  }
  
  public void clearServiceWorkerCache() {}
  
  public void clearTextEntry()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.clearTextEntry();
        }
      });
      return;
    }
  }
  
  public void cloneRenderCoordinates(RenderCoordinates paramRenderCoordinates)
  {
    this.mSelectionController.a(paramRenderCoordinates);
  }
  
  public int contentToViewDimension(int paramInt)
  {
    return Math.round(paramInt * this.mAwContents.getScale());
  }
  
  public int contentToViewX(int paramInt)
  {
    return contentToViewDimension(paramInt);
  }
  
  public int contentToViewY(int paramInt)
  {
    return contentToViewDimension(paramInt);
  }
  
  public boolean controlsResizeView()
  {
    b localb = this.mBrowserControlsOffsetManager;
    if (localb != null) {
      return localb.a();
    }
    return false;
  }
  
  public void copySelection()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.copySelection();
        }
      });
      return;
    }
    if (this.mAwContents.getContentViewCore() != null) {
      this.mAwContents.getContentViewCore().getWebContents().copy();
    }
  }
  
  public IMediaPlayer createMediaPlayer(boolean paramBoolean, TencentMediaPlayerBridge paramTencentMediaPlayerBridge)
  {
    if (paramBoolean) {
      return new d(this.mWebView, paramTencentMediaPlayerBridge);
    }
    return new SystemMediaPlayer();
  }
  
  public IMediaPlayer createMediaSourcePlayer(MediaSourceBridge paramMediaSourceBridge)
  {
    return new com.tencent.smtt.webkit.h5video.c(this.mWebView, paramMediaSourceBridge);
  }
  
  public void cutSelection()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.cutSelection();
        }
      });
      return;
    }
    if (this.mAwContents.getContentViewCore() != null) {
      this.mAwContents.getContentViewCore().getWebContents().cut();
    }
  }
  
  public void deactive()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.deactive();
        }
      });
      return;
    }
    this.mWebViewChromium.onPause();
  }
  
  public void destroy()
  {
    long l = this.mNativeWebViewChromiumExtension;
    if (l != 0L) {
      nativeOnJavaWebViewChromiumExtensionDestroyed(l);
    }
  }
  
  public int detectTranslateWebSiteIsNeeded(String paramString, boolean paramBoolean)
  {
    if (paramBoolean) {
      return detectFastOpenWebView(paramString);
    }
    int i = detectNormalWebView(paramString);
    if (i == 2) {
      return detectFastOpenWebView(paramString);
    }
    return i;
  }
  
  @CalledByNative
  public void didCommitProvisionalLoadForFrame(long paramLong, boolean paramBoolean, String paramString, int paramInt)
  {
    if (!paramBoolean) {
      return;
    }
    resetPrereadingState();
    paramString = this.mWebViewClientExtension;
    if (paramString != null) {
      paramString.onTransitionToCommitted();
    }
  }
  
  @CalledByNative
  public void didFailLoad(boolean paramBoolean1, boolean paramBoolean2, int paramInt, String paramString1, String paramString2, boolean paramBoolean3)
  {
    if (paramBoolean2)
    {
      if (paramInt != -3) {
        PageLoadDetector.a().a(this);
      }
      g.a().a(this.mContentsClientAdapter, this.mAwContents.getSettings());
      f.a().a(this.mContentsClientAdapter.hashCode());
      com.tencent.smtt.memory.e.a(this.mContentsClientAdapter.hashCode(), paramString2, 2);
      com.tencent.smtt.net.b.a().a(this);
      com.tencent.smtt.net.b.a().a();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("WebViewChromiumExtension::didFailLoad onPageFinished failingUrl=<");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("> errorCode is <");
    localStringBuilder.append(paramInt);
    localStringBuilder.append("> errorDesc is <");
    localStringBuilder.append(paramString1);
    localStringBuilder.append(">.");
    MttLog.d("WebViewChromiumExtension", localStringBuilder.toString());
  }
  
  @CalledByNative
  public void didFinishLoad(long paramLong, String paramString, boolean paramBoolean)
  {
    Object localObject = AwContentsStatics.getUnreachableWebDataUrl();
    boolean bool;
    if ((localObject != null) && (((String)localObject).equals(paramString))) {
      bool = true;
    } else {
      bool = false;
    }
    PageLoadDetector.a().a(this);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("WebViewChromiumExtension::didFinishLoad url is ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("thread=");
    ((StringBuilder)localObject).append(Thread.currentThread().getName());
    ((StringBuilder)localObject).append("[isErrorUrl=");
    ((StringBuilder)localObject).append(bool);
    ((StringBuilder)localObject).append("] [isMainFrame=");
    ((StringBuilder)localObject).append(paramBoolean);
    ((StringBuilder)localObject).append("]");
    MttLog.d("WebViewChromiumExtension", ((StringBuilder)localObject).toString());
    if ((paramBoolean) && (!bool))
    {
      g.a().a(this.mContentsClientAdapter, this.mAwContents.getSettings());
      com.tencent.smtt.net.e.a().a(this.mContentsClientAdapter);
      f.a().a(paramString, this.mContentsClientAdapter.hashCode());
      com.tencent.smtt.memory.e.a(this.mContentsClientAdapter.hashCode(), paramString, 1);
      com.tencent.smtt.net.b.a().a(this);
      com.tencent.smtt.net.b.a().a();
    }
    if (this.mAwContents.getWebContents() != null) {
      this.mAwContents.getWebContentsAccessibility().disableAccessibilityIfNecessary();
    }
  }
  
  @CalledByNative
  public void didFirstVisuallyNonEmptyPaint()
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension == null) {
      return;
    }
    try
    {
      Method localMethod = localIX5WebViewClientExtension.getClass().getMethod("didFirstVisuallyNonEmptyPaint", new Class[0]);
      localMethod.setAccessible(true);
      localMethod.invoke(localIX5WebViewClientExtension, new Object[0]);
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  @CalledByNative
  public void didNavigateMainFrame(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    if (paramBoolean2)
    {
      paramString2 = new StringBuilder();
      paramString2.append("AwContentClient::didNavigateMainFrame onPageFinished url=");
      paramString2.append(paramString1);
      MttLog.d("AwContentClient", paramString2.toString());
    }
  }
  
  public void disabledForceScaleState(boolean paramBoolean)
  {
    int i = this.mLastNavigationPageId;
    if (i == -1) {
      return;
    }
    Integer localInteger = new Integer(i);
    if (paramBoolean)
    {
      if (!this.mForceScaleDisabledPageIdList.contains(localInteger)) {
        this.mForceScaleDisabledPageIdList.add(localInteger);
      }
    }
    else if (this.mForceScaleDisabledPageIdList.contains(localInteger)) {
      this.mForceScaleDisabledPageIdList.remove(localInteger);
    }
  }
  
  public void discardCurrentHiddenPage() {}
  
  public void doFingerSearchIfNeed()
  {
    clearFingerSearchResponse();
    TencentAwContent.TencentHitTestData localTencentHitTestData = this.mAwContents.getLastTencentHitTestResult();
    double d1 = this.mAwContents.dipScale();
    if (localTencentHitTestData != null)
    {
      if (localTencentHitTestData.hitTestPoint == null) {
        return;
      }
      double d2 = localTencentHitTestData.hitTestPoint.x;
      Double.isNaN(d2);
      int i = (int)Math.round(d2 / d1);
      d2 = localTencentHitTestData.hitTestPoint.y;
      Double.isNaN(d2);
      int j = (int)Math.round(d2 / d1);
      boolean bool = e.a().v();
      nativedoFingerSearchIfNeed(this.mNativeWebViewChromiumExtension, i, j, bool);
      this.mIsFingerSeaching = true;
      reportDoFingerSearch();
      return;
    }
  }
  
  public void doTranslateAction(int paramInt)
  {
    Object localObject = this.mTranslateHelper;
    if ((localObject != null) && (((r)localObject).b() != null))
    {
      localObject = this.mWebView;
      if ((localObject != null) && (((TencentWebViewProxy)localObject).getUrl() != null) && (this.mWebView.getUrl().contains("mtt_in_readmode=1")))
      {
        showTranslateBubble(-2, "", "", -104);
        return;
      }
      if (((TextUtils.isEmpty(this.mTranslateHelper.b())) && (!this.mTranslateHelper.a())) || (!this.mTranslateHelper.b().equalsIgnoreCase(this.mWebView.getUrl())))
      {
        if (this.mTranslateHelper.a()) {
          this.mTranslateHelper.a().f();
        }
        nativeToCapturePageInfo(this.mNativeWebViewChromiumExtension, this.mWebView.getUrl());
        return;
      }
      if (paramInt == -1)
      {
        this.mTranslateHelper.b();
        return;
      }
      if (paramInt == 0)
      {
        this.mTranslateHelper.c(this.mWebView);
        return;
      }
      if (paramInt == 1) {
        this.mTranslateHelper.a(this.mWebView);
      }
      return;
    }
    showTranslateBubble(-2, "", "", -100);
  }
  
  @CalledByNative
  public void documentAvailableInMainFrame()
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension == null) {
      return;
    }
    try
    {
      Method localMethod = localIX5WebViewClientExtension.getClass().getMethod("documentAvailableInMainFrame", new Class[0]);
      localMethod.setAccessible(true);
      localMethod.invoke(localIX5WebViewClientExtension, new Object[0]);
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  public boolean drawBaseLayer(final Canvas paramCanvas, final boolean paramBoolean, final int paramInt)
  {
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean a()
        {
          return Boolean.valueOf(WebViewChromiumExtension.this.drawBaseLayer(paramCanvas, paramBoolean, paramInt));
        }
      })).booleanValue();
    }
    long l = this.mNativeWebViewChromiumExtension;
    int i = 0;
    if (l == 0L) {
      return false;
    }
    Bitmap localBitmap = SMTTAdaptation.getBitmap(paramCanvas);
    if (localBitmap == null) {
      i = 1;
    }
    if (i != 0) {
      localBitmap = X5NativeBitmap.nativeBitmapFactory().createBitmap(this.mWebView.getRealWebView().getWidth(), this.mWebView.getRealWebView().getHeight(), Bitmap.Config.RGB_565);
    }
    paramBoolean = nativeTakeScreenshotWithPageID(this.mNativeWebViewChromiumExtension, paramInt, localBitmap);
    if ((paramBoolean) && (i != 0))
    {
      paramInt = paramCanvas.save();
      paramCanvas.drawBitmap(localBitmap, 0.0F, 0.0F, null);
      paramCanvas.restoreToCount(paramInt);
    }
    return paramBoolean;
  }
  
  public boolean drawPreReadBaseLayer(final Canvas paramCanvas, final boolean paramBoolean)
  {
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean a()
        {
          return Boolean.valueOf(WebViewChromiumExtension.this.drawPreReadBaseLayer(paramCanvas, paramBoolean));
        }
      })).booleanValue();
    }
    long l = this.mNativeWebViewChromiumExtension;
    int i = 0;
    if (l != 0L)
    {
      if (!this.mIsPrereadingValid) {
        return false;
      }
      Bitmap localBitmap = SMTTAdaptation.getBitmap(paramCanvas);
      if (localBitmap == null) {
        i = 1;
      }
      if (i != 0) {
        localBitmap = X5NativeBitmap.nativeBitmapFactory().createBitmap(this.mWebView.getRealWebView().getWidth(), this.mWebView.getRealWebView().getHeight(), Bitmap.Config.RGB_565);
      }
      paramBoolean = nativeTakeScreenshotWithPageID(this.mNativeWebViewChromiumExtension, 55535, localBitmap);
      if ((paramBoolean) && (i != 0))
      {
        i = paramCanvas.save();
        paramCanvas.drawBitmap(localBitmap, 0.0F, 0.0F, null);
        paramCanvas.restoreToCount(i);
      }
      return paramBoolean;
    }
    return false;
  }
  
  public void enterFingerSearchMode()
  {
    e locale = this.mFingerSearchClient;
    if ((locale != null) && (this.isExistFingerSearchClient))
    {
      locale.b();
      this.isEnterSelectionMode = true;
    }
  }
  
  public void enterFullScreen(final View paramView)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        WebViewChromiumExtension.this.mDisplayCutoutController.a(paramView);
      }
    });
  }
  
  public void enterOtherMode()
  {
    e locale = this.mFingerSearchClient;
    if (locale != null) {
      locale.c();
    }
  }
  
  public void enterSelectionMode(final boolean paramBoolean)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.enterSelectionMode(paramBoolean);
        }
      });
      return;
    }
    if ((paramBoolean) && (this.mAwContents.getContentViewCore() != null))
    {
      this.mAwContents.getContentViewCore().getWebContents().selectAll();
    }
    else
    {
      IX5WebViewBase.HitTestResult localHitTestResult = this.mWebViewChromium.getHitTestResultTencent();
      if (localHitTestResult != null)
      {
        enterFingerSearchMode();
        selectWordAroundPosition(localHitTestResult.getHitTestPoint());
      }
    }
    this.mHasEnteredSelection = true;
  }
  
  public void enterSelectionModeWaitFS(boolean paramBoolean)
  {
    if ((!paramBoolean) && (this.mIsFingerSeaching))
    {
      this.mHasWaitingEnterSelectionMode = true;
      return;
    }
    enterSelectionMode(paramBoolean);
  }
  
  public void evaluateJavaScriptInFrame(final String paramString1, final String paramString2)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.evaluateJavaScriptInFrame(paramString1, paramString2);
        }
      });
      return;
    }
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeEvaluateJavaScriptInFrame(l, paramString1, paramString2, false);
  }
  
  public void evaluateJavaScriptInSubFrame(final String paramString)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.evaluateJavaScriptInSubFrame(paramString);
        }
      });
      return;
    }
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeEvaluateJavaScriptInSubFrame(l, paramString, false);
  }
  
  public void executeCopyItem()
  {
    e locale = this.mFingerSearchClient;
    if (locale != null) {
      locale.e();
    }
  }
  
  public void executeDiectConsumptionItems()
  {
    e locale = this.mFingerSearchClient;
    if (locale != null) {
      locale.d();
    }
  }
  
  public void executeMoreItem()
  {
    e locale = this.mFingerSearchClient;
    if (locale != null) {
      locale.g();
    }
  }
  
  public void executeSearchItem()
  {
    e locale = this.mFingerSearchClient;
    if (locale != null) {
      locale.f();
    }
  }
  
  public void exitFullScreen()
  {
    this.mDisplayCutoutController.a();
  }
  
  public void exitPluginFullScreen()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.exitPluginFullScreen();
        }
      });
      return;
    }
    if (isPluginFullScreen()) {
      hideFullScreenPlugin();
    }
  }
  
  public void exitX5LongClickPopMenu()
  {
    if (!this.isEnterSelectionMode)
    {
      e locale = this.mFingerSearchClient;
      if (locale != null) {
        locale.j();
      }
    }
    this.isEnterSelectionMode = false;
  }
  
  public void focusAndPopupIM(final String paramString)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.focusAndPopupIM(paramString);
        }
      });
      return;
    }
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeFocusAndPopupIM(l, paramString);
  }
  
  public void focusTtsNode(int paramInt, boolean paramBoolean)
  {
    this.mAwContents.focusTtsNode(paramInt, paramBoolean);
  }
  
  public boolean forceScaleDisabled()
  {
    int i = this.mLastNavigationPageId;
    if (i != -1)
    {
      Integer localInteger = new Integer(i);
      return this.mForceScaleDisabledPageIdList.contains(localInteger);
    }
    return false;
  }
  
  public void fullScreenPluginHidden()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.fullScreenPluginHidden();
        }
      });
      return;
    }
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeFullScreenPluginHidden(l);
  }
  
  public List<String> getAccessibilityBlackList()
  {
    return SmttServiceProxy.getInstance().getAccessibilityBlackList();
  }
  
  public int getAddressbarDisplayMode()
  {
    return this.mBrowserControlsOffsetManager.b();
  }
  
  public ArrayList<IX5WebViewBase.ImageInfo> getAllImageInfo()
  {
    return this.mImageList;
  }
  
  public Bitmap getBitmapByIndex(int paramInt)
  {
    if (paramInt < this.mImageList.size())
    {
      if (!this.mIsImageListUpdated) {
        return null;
      }
      this.mBitmap = null;
      nativeGetBitmapByIndex(this.mNativeWebViewChromiumExtension, paramInt);
      return this.mBitmap;
    }
    return null;
  }
  
  public int getBrowserControlsHeight()
  {
    if (this.mBrowserControlsOffsetManager != null)
    {
      float f = this.mWebView.getContext().getResources().getDisplayMetrics().density;
      return (int)(this.mBrowserControlsOffsetManager.a() / f);
    }
    return 0;
  }
  
  public float getCurrScrollVelocity()
  {
    return this.mAwContents.getCurrScrollVelocity();
  }
  
  public int getCurrentEntryIndex()
  {
    TencentAwContent localTencentAwContent = this.mAwContents;
    if (localTencentAwContent != null) {
      return localTencentAwContent.getCurrentEntryIndex();
    }
    return -1;
  }
  
  public int getCurrentHistoryItemIndex()
  {
    if (checkNeedsPost()) {
      ((Integer)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Integer a()
        {
          return Integer.valueOf(WebViewChromiumExtension.this.getCurrentHistoryItemIndex());
        }
      })).intValue();
    }
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return 0;
    }
    return nativeGetCurrentNavigationEntryIndex(l);
  }
  
  public float getCurrentPageMaxScrollRatio()
  {
    TencentAwContent localTencentAwContent = this.mAwContents;
    if (localTencentAwContent != null) {
      return localTencentAwContent.getCurrentPageMaxVerticalScrollRatio();
    }
    return 0.0F;
  }
  
  public float getCurrentPageNumber()
  {
    TencentAwContent localTencentAwContent = this.mAwContents;
    if (localTencentAwContent != null) {
      return localTencentAwContent.getCurrentPageNumber();
    }
    return 0.0F;
  }
  
  public com.tencent.smtt.webkit.b.a getDisplayCutoutController()
  {
    return this.mDisplayCutoutController;
  }
  
  public String getDocumentOuterHTML()
  {
    if (checkNeedsPost()) {
      (String)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public String a()
        {
          return WebViewChromiumExtension.this.getDocumentOuterHTML();
        }
      });
    }
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return null;
    }
    return nativeGetDocumentOuterHTML(l);
  }
  
  public void getFakeLoginStatus(Bundle paramBundle, android.webkit.ValueCallback<Bundle> paramValueCallback)
  {
    if ((this.mSettingsExtension.getFakeLoginEnabled()) && (paramBundle.getString("input-box-num") != null)) {
      nativeGetInputBoxNum(this.mNativeWebViewChromiumExtension, TencentCallbackConverter.fromValueCallback(paramValueCallback));
    }
  }
  
  public int getGoBackOrForwardToDesiredSteps(int paramInt)
  {
    long l = this.mNativeWebViewChromiumExtension;
    if (l != 0L) {
      return nativeGetGoBackOrForwardToDesiredSteps(l, paramInt);
    }
    return paramInt;
  }
  
  public IX5WebHistoryItem getHistoryItem(final int paramInt)
  {
    if (checkNeedsPost()) {
      (IX5WebHistoryItem)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public IX5WebHistoryItem a()
        {
          return WebViewChromiumExtension.this.getHistoryItem(paramInt);
        }
      });
    }
    Object localObject = this.mAwContents.getNavigationHistory();
    if (localObject == null) {
      return null;
    }
    paramInt = ((NavigationHistory)localObject).getCurrentEntryIndex() + paramInt;
    if ((paramInt >= 0) && (paramInt < ((NavigationHistory)localObject).getEntryCount()))
    {
      localObject = new TencentWebHistoryItemChromium(((NavigationHistory)localObject).getEntryAtIndex(paramInt));
      ((TencentWebHistoryItemChromium)localObject).setWebViewChromiumExtension(this);
      return (IX5WebHistoryItem)localObject;
    }
    return null;
  }
  
  public boolean getJavaScriptEnabled()
  {
    return this.mWebView.getSettings().getJavaScriptEnabled();
  }
  
  public String getNavigateUrl()
  {
    return this.mNavigateUrl;
  }
  
  public OpenJsApiBridge getOpenJsApiBridge()
  {
    return null;
  }
  
  public boolean getOverScrollState(int paramInt)
  {
    Integer localInteger = new Integer(paramInt);
    return this.mDisableOverScrollPageId.contains(localInteger) ^ true;
  }
  
  public TencentWebViewDatabaseAdapter getPasswordDatabase(Context paramContext)
  {
    if (this.mPasswordDatabase == null) {
      this.mPasswordDatabase = TencentWebViewDatabaseAdapter.getInstance(paramContext);
    }
    return this.mPasswordDatabase;
  }
  
  @CalledByNative
  public String[] getPasswordFromDatabase(String paramString)
  {
    if (this.mAwContents.getContentViewCore() != null) {
      return getPasswordDatabase(this.mAwContents.getContentViewCore().getContext()).getUsernamePassword(paramString);
    }
    return null;
  }
  
  public IX5QQBrowserClient getQQBrowserClient()
  {
    return this.mQQBrowserClient;
  }
  
  public long getRemoteServerTimeStamp()
  {
    return SmttServiceProxy.getInstance().getRemoteServerTimeStamp();
  }
  
  public int getRoutingID()
  {
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return 0;
    }
    return nativeGetRoutingID(l);
  }
  
  public String getSelection()
  {
    if (checkNeedsPost()) {
      (String)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public String a()
        {
          return WebViewChromiumExtension.this.getSelection();
        }
      });
    }
    if (this.mAwContents.getTencentContentViewCore() != null)
    {
      TencentSelectionPopupControllerImpl localTencentSelectionPopupControllerImpl = this.mAwContents.getTencentContentViewCore().getTencentSelectionPopupController();
      if (localTencentSelectionPopupControllerImpl != null) {
        return localTencentSelectionPopupControllerImpl.getSelectedText();
      }
    }
    return null;
  }
  
  public WebSettingsExtension getSettingsExtension()
  {
    return this.mSettingsExtension;
  }
  
  public long getSntpTime()
  {
    return SmttServiceProxy.getInstance().getSntpTime();
  }
  
  public float getTitleHeight()
  {
    Object localObject = this.mAwContents;
    if (localObject != null)
    {
      if (((TencentAwContent)localObject).getContentViewCore() == null) {
        return 0.0F;
      }
      localObject = this.mContentsClientAdapter.getQQBrowserClient();
      if (localObject != null) {
        return this.mAwContents.getContentViewCore().getRenderCoordinates().fromPixToDip(((IX5QQBrowserClient)localObject).getTitleHeight());
      }
      return 0.0F;
    }
    return 0.0F;
  }
  
  public IX5WebView.TRANSLATE_STATE getTranslateState(String paramString)
  {
    if (this.mTranslateHelper == null) {
      return IX5WebView.TRANSLATE_STATE.TRANLATEHELPER_NOT_INIT;
    }
    if (TextUtils.isEmpty(paramString))
    {
      if (this.mHasEnteredSelection)
      {
        this.mHasEnteredSelection = false;
        return IX5WebView.TRANSLATE_STATE.ENTER_SELECTION;
      }
      return IX5WebView.TRANSLATE_STATE.DRAGING_SELECTION;
    }
    if (!paramString.equalsIgnoreCase(this.mTranslateHelper.b())) {
      return IX5WebView.TRANSLATE_STATE.NOT_CURRENT_PAGE_ERR;
    }
    int i = this.mTranslateHelper.a();
    if (i == 1) {
      return IX5WebView.TRANSLATE_STATE.TRANSLATED_PAGE;
    }
    if ((this.mTranslateHelper.a().equals("en")) && ((i == 0) || (i == 2))) {
      return IX5WebView.TRANSLATE_STATE.ENGLISH_PAGE;
    }
    return IX5WebView.TRANSLATE_STATE.NON_ENGLIS_PAGE;
  }
  
  public void getTtsTextAsync(int paramInt)
  {
    if (paramInt == 0)
    {
      SmttServiceProxy localSmttServiceProxy = SmttServiceProxy.getInstance();
      HashMap localHashMap = new HashMap();
      localHashMap.put("guid", localSmttServiceProxy.getGUID());
      if (this.mAwContents.getUrl() != null) {
        localHashMap.put("url", this.mAwContents.getUrl());
      }
      localSmttServiceProxy.upLoadToBeacon("MTT_CORE_TTS", localHashMap);
    }
    this.mAwContents.getTtsTextAsync(paramInt);
  }
  
  @CalledByNative
  public String getUserAction()
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension == null) {
      return null;
    }
    return (String)localIX5WebViewClientExtension.onMiscCallBack("getUserAction", null);
  }
  
  public List<String> getUserSelectedHiddenDomains()
  {
    return AdInfoManager.getInstance().getUserSelectAdInfoDomain();
  }
  
  public IX5WebChromeClientExtension getWebChromeClientExtension()
  {
    return this.mWebChromeClientExtension;
  }
  
  public void getWebContent()
  {
    this.mWebView.evaluateJavascript(this.mWXTranslateHelper.a(), null);
    this.mWebView.evaluateJavascript("getWebpageText()", new android.webkit.ValueCallback()
    {
      public void a(String arg1)
      {
        WebViewChromiumExtension.access$2202(WebViewChromiumExtension.this, ???);
        try
        {
          synchronized (WebViewChromiumExtension.this.mGetWebContentLock)
          {
            WebViewChromiumExtension.this.mGetWebContentLock.notifyAll();
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
      }
    });
  }
  
  public IX5WebViewClientExtension getWebViewClientExtension()
  {
    return this.mWebViewClientExtension;
  }
  
  public String getWebViewIdentity()
  {
    return getSettingsExtension().getWebViewIdentity();
  }
  
  public void goBackOrForward(final int paramInt)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.goBackOrForward(paramInt);
        }
      });
      return;
    }
  }
  
  public void gotoPreRead()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.gotoPreRead();
        }
      });
      return;
    }
    long l = this.mNativeWebViewChromiumExtension;
    if ((l != 0L) && (this.mIsPrereadingValid))
    {
      String str = this.mPreRenderUrl;
      if (str == null) {
        return;
      }
      nativeGoToPrereading(l, str);
      return;
    }
  }
  
  public void handleAudioAutoPlayPrompt(String paramString, JsResultReceiver paramJsResultReceiver)
  {
    if (this.mWebViewClientExtension != null)
    {
      final JsPromptResult localJsPromptResult = new a(paramJsResultReceiver).a();
      if (!this.mWebViewClientExtension.notifyAutoAudioPlay(paramString, new com.tencent.smtt.export.external.interfaces.JsResult() {})) {
        paramJsResultReceiver.cancel();
      }
    }
    else
    {
      paramJsResultReceiver.cancel();
    }
  }
  
  public void handleCameraOpenAuthorized(String paramString, JsPromptResultReceiver paramJsPromptResultReceiver)
  {
    int j = VideoCaptureFactory.getNumberOfCameras();
    int i = 0;
    while (i < j)
    {
      if (VideoCaptureFactory.isLegacyOrDeprecatedDevice(i))
      {
        i = 1;
        break label34;
      }
      i += 1;
    }
    i = 0;
    label34:
    if (i != 0)
    {
      onPermissionRequestInternal(new TencentAwCameraPermissionRequest(0L, Uri.parse(GURLUtils.getOrigin(paramString)), 2L, paramJsPromptResultReceiver), false);
      return;
    }
    paramJsPromptResultReceiver.confirm(Boolean.toString(true));
  }
  
  public void handleGeolocationAuthorizedStatus(String paramString, JsPromptResultReceiver paramJsPromptResultReceiver)
  {
    Object localObject = GEOLOCATION_STATUS_UNKNOWN;
    localObject = UrlUtils.getHost(paramString);
    boolean bool = TextUtils.isEmpty((CharSequence)localObject);
    int k = 0;
    int j = k;
    if (!bool)
    {
      int i = 0;
      for (;;)
      {
        String[] arrayOfString = GEOLOCATION_AUTHORIZED_STATUS_WHITE_LIST;
        j = k;
        if (i >= arrayOfString.length) {
          break;
        }
        if (((String)localObject).equalsIgnoreCase(arrayOfString[i]))
        {
          j = 1;
          break;
        }
        i += 1;
      }
    }
    if (j == 0)
    {
      paramString = GEOLOCATION_STATUS_UNAUTHORIZED;
    }
    else
    {
      localObject = (TencentGeolocationPermissionsAdapter)GeolocationPermissions.getInstance();
      if (((TencentGeolocationPermissionsAdapter)localObject).hasOrigin(paramString))
      {
        if (((TencentGeolocationPermissionsAdapter)localObject).isOriginAllowed(paramString)) {
          paramString = GEOLOCATION_STATUS_ALLOW;
        } else {
          paramString = GEOLOCATION_STATUS_DENY;
        }
      }
      else {
        paramString = GEOLOCATION_STATUS_UNPERSISTENT;
      }
    }
    paramJsPromptResultReceiver.confirm(paramString);
  }
  
  public void handleVibrationAuthorized(String paramString, JsResultReceiver paramJsResultReceiver)
  {
    if (this.mWebViewClientExtension != null)
    {
      final JsPromptResult localJsPromptResult = new b(paramJsResultReceiver).a();
      if (!this.mWebViewClientExtension.requestVibration(paramString, new com.tencent.smtt.export.external.interfaces.JsResult() {})) {
        paramJsResultReceiver.confirm();
      }
    }
    else
    {
      paramJsResultReceiver.confirm();
    }
  }
  
  @CalledByNative
  public void hasDiscardCurrentPage(boolean paramBoolean)
  {
    Object localObject = this.mWebViewClientExtension;
    if (localObject == null) {
      return;
    }
    try
    {
      localObject = localObject.getClass().getMethod("hasDiscardCurrentPage", new Class[] { Boolean.TYPE });
      ((Method)localObject).setAccessible(true);
      ((Method)localObject).invoke(this.mWebViewClientExtension, new Object[] { Boolean.valueOf(paramBoolean) });
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  @CalledByNative
  public void hasRestoreCurrentPage(boolean paramBoolean)
  {
    Object localObject = this.mWebViewClientExtension;
    if (localObject == null) {
      return;
    }
    try
    {
      localObject = localObject.getClass().getMethod("hasRestoreCurrentPage", new Class[] { Boolean.TYPE });
      ((Method)localObject).setAccessible(true);
      ((Method)localObject).invoke(this.mWebViewClientExtension, new Object[] { Boolean.valueOf(paramBoolean) });
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  public boolean hasWebAR()
  {
    return WebARCameraUtils.a(this.mCurrentReportPageIndex);
  }
  
  public void hideInsertionMode()
  {
    this.mSelectionController.b();
  }
  
  public void hideSelectionMode()
  {
    this.mSelectionController.a();
    ISelectionInterface localISelectionInterface = this.mSelectionListener;
    if (localISelectionInterface != null) {
      try
      {
        localISelectionInterface.hideSelectionView();
        return;
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
  }
  
  public void hideSelectionView()
  {
    ISelectionInterface localISelectionInterface = this.mSelectionListener;
    if (localISelectionInterface != null) {
      try
      {
        localISelectionInterface.hideSelectionView();
        return;
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
  }
  
  public void hideUserSelectedElement(final String paramString)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.hideUserSelectedElement(paramString);
        }
      });
      return;
    }
    this.mAwContents.hideSelectedAdElement(paramString);
  }
  
  public void injectJavascript(String paramString)
  {
    this.mAwContents.injectJavascript(paramString);
  }
  
  @CalledByNative
  public boolean isDomDistillWhiteList(String paramString)
  {
    return SmttServiceProxy.getInstance().isDomDistillWhiteList(paramString);
  }
  
  public boolean isEnableTbsAR()
  {
    if (TbsMode.TBSISQB()) {
      return false;
    }
    if (!this.mAwContents.getTencentSettings().getARModeEnable()) {
      return false;
    }
    if (!SmttServiceProxy.getInstance().isEnableTbsARPage(this.mWebView.getRealWebView().getUrl())) {
      return false;
    }
    return SmttServiceProxy.getInstance().isTbsAREnable(this.mWebView.getContext());
  }
  
  public boolean isFocusedNodeEditable()
  {
    if (this.mAwContents.getTencentContentViewCore() != null) {
      return this.mAwContents.getTencentContentViewCore().isSelectionEditable();
    }
    return false;
  }
  
  public boolean isHandleViewDragging()
  {
    return this.mSelectionController.b();
  }
  
  public boolean isLbsDontAlertWhiteList(String paramString)
  {
    return SmttServiceProxy.getInstance().isLbsDontAlertWhiteList(paramString);
  }
  
  public boolean isNightMode()
  {
    return this.mSettingsExtension.getDayOrNight() ^ true;
  }
  
  public boolean isPluginFullScreen()
  {
    return this.mFullScreenHolder != null;
  }
  
  public boolean isPreReadCanGoForward()
  {
    return this.mIsPrereadingValid;
  }
  
  public boolean isScrollInProgress()
  {
    if (this.mAwContents.getContentViewCore() != null) {
      return this.mAwContents.getContentViewCore().isScrollInProgress();
    }
    return false;
  }
  
  public boolean isSelectionViewVisible()
  {
    try
    {
      if (this.mSelectionListener != null)
      {
        boolean bool = this.mSelectionListener.isSelectionViewVisible();
        return bool;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebViewActive()
  {
    TencentWebViewProxy localTencentWebViewProxy = this.mWebView;
    if (localTencentWebViewProxy == null) {
      return false;
    }
    return localTencentWebViewProxy.isActive();
  }
  
  public void leaveSelectionMode()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.leaveSelectionMode();
        }
      });
      return;
    }
    reportUserFinallySelectText(getSelection());
    resetFingerSearchClient();
    if (this.mAwContents.getContentViewCore() != null) {
      ThreadUtils.postOnUiThread(new Runnable()
      {
        public void run()
        {
          if (WebViewChromiumExtension.this.mAwContents.getContentViewCore() != null) {
            WebViewChromiumExtension.this.mAwContents.getContentViewCore().getWebContents().collapseSelection();
          }
          WebViewChromiumExtension.this.hideSelectionMode();
        }
      });
    }
    this.mHasWaitingEnterSelectionMode = false;
  }
  
  public void markScrollOffsetForMoveRange(boolean paramBoolean)
  {
    nativeMarkScrollOffsetForMoveRange(this.mNativeWebViewChromiumExtension, paramBoolean);
  }
  
  public int maxHorizontalScroll()
  {
    return this.mAwContents.computeHorizontalScrollRange();
  }
  
  public int maxVerticalScroll()
  {
    return this.mAwContents.computeVerticalScrollRange();
  }
  
  public void moveCaret(float paramFloat1, float paramFloat2)
  {
    nativeMoveCaret(this.mNativeWebViewChromiumExtension, paramFloat1, paramFloat2);
  }
  
  @CalledByNative
  public boolean navigateBackForwardForNativePage(int paramInt)
  {
    Object localObject = this.mWebChromeClientExtension;
    if (localObject != null) {
      try
      {
        localObject = localObject.getClass().getMethod("onGoToEntryOffset", new Class[] { Integer.TYPE });
        ((Method)localObject).setAccessible(true);
        boolean bool = ((Boolean)((Method)localObject).invoke(this.mWebChromeClientExtension, new Object[] { Integer.valueOf(paramInt) })).booleanValue();
        return bool;
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
    return true;
  }
  
  public boolean needReSizeForDisplayCut(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    com.tencent.smtt.webkit.b.a locala = this.mDisplayCutoutController;
    if (locala != null) {
      return locala.a(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    return false;
  }
  
  public boolean needRecordHostScale(String paramString)
  {
    return TencentWebViewDatabaseAdapter.getInstance(this.mWebView.getContext()).getForcePinchScale(paramString);
  }
  
  public boolean needSniff()
  {
    return false;
  }
  
  public void notifyADWebviewVisiableHeight(int paramInt) {}
  
  @CalledByNative
  public void notifyDocumentTiming(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5)
  {
    g.a().a(this.mContentsClientAdapter, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, this.mAwContents.getSettings());
  }
  
  public void notifyFirstScreenParameter(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
  {
    if ((this.mContentsClientAdapter != null) && ((paramLong2 | paramLong3 | paramLong4) != 0L)) {
      g.a().a(this.mContentsClientAdapter, paramLong1, paramLong2, paramLong3, paramLong4);
    }
  }
  
  public void notifyFirstScreenTime(long paramLong1, long paramLong2)
  {
    Object localObject = this.mAwContents;
    if ((localObject != null) && (((TencentAwContent)localObject).getSettings() != null) && (this.mContentsClientAdapter != null))
    {
      g.a().a(this.mContentsClientAdapter, paramLong2, paramLong1, this.mAwContents.getSettings());
      com.tencent.smtt.net.e.a().b(this.mContentsClientAdapter);
    }
    if (this.mFirstScreen < 1L)
    {
      this.mFirstScreen = paramLong2;
      long l1 = this.mFirstScreen;
      long l2 = this.mPageStart;
      localObject = this.mContentsClientAdapter;
      if (localObject != null) {
        ((TencentWebViewContentsClientAdapter)localObject).notifyFirstScreenFinished(l1 - l2, paramLong1);
      }
      this.mWebView.notifyFirstScreenTime(paramLong1, paramLong2);
    }
  }
  
  public void notifyMemoryPressure(int paramInt)
  {
    MemoryChecker.trim(paramInt);
  }
  
  public void notifyMiniQbStatus(String paramString1, String paramString2) {}
  
  @CalledByNative
  public void notifyPageImageInfo(Object paramObject)
  {
    if ((paramObject != null) && ((paramObject instanceof HashMap)))
    {
      paramObject = (HashMap)paramObject;
      k.a().a(this);
      k.a().b((Map)paramObject);
    }
  }
  
  public void notifyResponseStartTime(long paramLong)
  {
    g.a().a(this.mContentsClientAdapter, paramLong);
  }
  
  public void notifyScreenStatus(int paramInt)
  {
    if (TextUtils.isEmpty(this.mNavigateUrl)) {
      return;
    }
    if ((this.mBlankScreenStatus != 0) && (paramInt == 0)) {
      g.a().a(this.mContentsClientAdapter, SystemClock.uptimeMillis(), System.currentTimeMillis());
    }
    this.mBlankScreenStatus = paramInt;
    if (paramInt == 0) {
      this.mLastBlankScreenStatus = paramInt;
    }
  }
  
  public void notifyUrlSecurityLevelResp(String paramString1, String paramString2)
  {
    notifyQProxyResponseReceived(paramString1);
    this.mSecurityLevel = new SecurityLevelBase();
    SecurityLevelBase localSecurityLevelBase = this.mSecurityLevel;
    int i = 0;
    localSecurityLevelBase.level = 0;
    localSecurityLevelBase.url = paramString2;
    if (!TextUtils.isEmpty(paramString1))
    {
      paramString1 = paramString1.split(";");
      while (i < paramString1.length)
      {
        paramString2 = paramString1[i];
        if (paramString2.startsWith("STLevel="))
        {
          paramString2 = paramString2.substring(8);
          if (!TextUtils.isEmpty(paramString2)) {
            try
            {
              this.mSecurityLevel.level = Integer.parseInt(paramString2);
            }
            catch (Exception paramString2)
            {
              paramString2.printStackTrace();
            }
          }
        }
        else if (paramString2.startsWith("STType="))
        {
          paramString2 = paramString2.substring(7);
          if (!TextUtils.isEmpty(paramString2)) {
            try
            {
              paramString2 = com.tencent.common.utils.Base64.decode(paramString2);
              this.mSecurityLevel.type = new String(paramString2, "UTF-8");
            }
            catch (Exception paramString2)
            {
              paramString2.printStackTrace();
            }
          }
        }
        else if (paramString2.startsWith("STSubLevel="))
        {
          paramString2 = paramString2.substring(11);
          if (!TextUtils.isEmpty(paramString2)) {
            try
            {
              this.mSecurityLevel.securitySubLevel = Integer.parseInt(paramString2);
            }
            catch (Exception paramString2)
            {
              paramString2.printStackTrace();
            }
          }
        }
        else if (paramString2.startsWith("STFlag="))
        {
          paramString2 = paramString2.substring(7);
          if (!TextUtils.isEmpty(paramString2)) {
            try
            {
              this.mSecurityLevel.flag = Integer.parseInt(paramString2);
            }
            catch (Exception paramString2)
            {
              paramString2.printStackTrace();
            }
          }
        }
        else if (paramString2.startsWith("STInfoUrl="))
        {
          paramString2 = paramString2.substring(10);
          if (TextUtils.isEmpty(paramString2)) {
            break label345;
          }
        }
        try
        {
          this.mSecurityLevel.infoUrl = paramString2;
        }
        catch (Exception paramString2)
        {
          label345:
          for (;;) {}
        }
        if (paramString2.startsWith("STEvilClass="))
        {
          paramString2 = paramString2.substring(12);
          if (!TextUtils.isEmpty(paramString2)) {
            this.mSecurityLevel.evilclass = Integer.parseInt(paramString2);
          }
        }
        i += 1;
      }
      setSecurityLevel(this.mSecurityLevel);
      return;
    }
    checkSecurityLevel(paramString2, null, 1);
  }
  
  @CalledByNative
  public void notifyWXEnterSelectionMode(boolean paramBoolean)
  {
    if ((!paramBoolean) && (this.mHandler.hasMessages(14))) {
      this.mHandler.removeMessages(14);
    }
    this.mIsFingerSeaching = false;
    if (this.mHasWaitingEnterSelectionMode)
    {
      this.mHasWaitingEnterSelectionMode = false;
      enterSelectionMode(false);
      return;
    }
    if (e.a().a(this.mWebView))
    {
      Object localObject = this.mWebView;
      if ((localObject instanceof X5WebViewAdapter))
      {
        localObject = (X5WebViewAdapter)localObject;
        if (!((X5WebViewAdapter)localObject).imageQueryFromX5Core()) {
          ((X5WebViewAdapter)localObject).onShowLongClickPopupMenu(this.mSelectable);
        }
      }
    }
  }
  
  public void onAppExit()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.onAppExit();
        }
      });
      return;
    }
    if ((this.ENABLE_DEAD_CODE_DETECTION_IN_APP) && (MAIN_THREAD_ALREADY_BLOCKED)) {
      SmttServiceProxy.getInstance().setDeadCodeExit();
    }
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeOnAppExit(l);
  }
  
  @CalledByNative
  public void onBackForwardFinished()
  {
    IX5WebChromeClientExtension localIX5WebChromeClientExtension = this.mWebChromeClientExtension;
    if (localIX5WebChromeClientExtension == null) {
      return;
    }
    localIX5WebChromeClientExtension.onBackforwardFinished(0);
  }
  
  public void onDestroy()
  {
    com.tencent.smtt.net.e.a().a(this.mContentsClientAdapter, getBlankScreenStatus(), 6);
    f.a().a(this.mContentsClientAdapter.hashCode(), 11, getCurrentPageMaxScrollRatio(), getCurrentPageNumber(), getTouchEventCount());
    if (this.mAwContents.getContentViewCore() != null) {
      com.tencent.smtt.memory.e.a(this.mContentsClientAdapter.hashCode(), this.mAwContents.getTencentContentViewCore().getUrl(), 3);
    }
  }
  
  public void onDownloadStart(String paramString1, final String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte)
  {
    paramString2 = new d(paramString1, paramString2, paramString3, paramString4, paramLong, paramString5, paramString6, paramString7, paramString8, paramArrayOfByte);
    if ((isApk(paramString4, paramString3)) && (SmttServiceProxy.getInstance().isDetectDownloadPkgName()))
    {
      SmttServiceProxy.getInstance().addDetectorTask(paramString1, new android.webkit.ValueCallback()
      {
        public void a(String paramAnonymousString)
        {
          paramString2.i = paramAnonymousString;
          WebViewChromiumExtension.this.mHandler.sendMessage(WebViewChromiumExtension.this.mHandler.obtainMessage(3, paramString2));
        }
      });
      paramString1 = this.mHandler;
      paramString1.sendMessageDelayed(paramString1.obtainMessage(3, paramString2), 1000L);
      return;
    }
    paramString1 = this.mHandler;
    paramString1.sendMessage(paramString1.obtainMessage(3, paramString2));
  }
  
  public void onEndUpdatingPosition(Rect paramRect1, Rect paramRect2)
  {
    ISelectionInterface localISelectionInterface = this.mSelectionListener;
    if (localISelectionInterface != null)
    {
      try
      {
        localISelectionInterface.updateHelperWidget(paramRect1, paramRect2);
      }
      catch (Throwable paramRect1)
      {
        paramRect1.printStackTrace();
      }
      paramRect1 = this.mWebView;
      if ((paramRect1 != null) && ((paramRect1 instanceof X5WebViewAdapter))) {
        ((X5WebViewAdapter)paramRect1).setEventType(this.mEventType);
      }
      try
      {
        this.mSelectionListener.onSelectionDone(null, isSelectAll());
        return;
      }
      catch (Throwable paramRect1)
      {
        paramRect1.printStackTrace();
      }
    }
  }
  
  public void onFingerSearchResult(final String paramString, final int paramInt1, final int paramInt2)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.onFingerSearchResult(paramString, paramInt1, paramInt2);
        }
      });
      return;
    }
    if (this.mNativeWebViewChromiumExtension == 0L) {
      return;
    }
    e locale = this.mFingerSearchClient;
    if ((locale != null) && (paramInt1 != -1)) {
      locale.a(paramString);
    }
    nativeonFingerSearchResult(this.mNativeWebViewChromiumExtension, paramString, paramInt1, paramInt2);
    notifyWXEnterSelectionMode(false);
  }
  
  public void onFlingScrollBegin(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mWebView.onFlingScrollBegin(paramInt1, paramInt2, paramInt3);
  }
  
  @CalledByNative
  public String onGetExtraHeadersForPreloading(String paramString)
  {
    Object localObject = this.mWebViewClientExtension;
    if (localObject == null) {
      return "";
    }
    try
    {
      localObject = ((IX5WebViewClientExtension)localObject).onGetExtraHeadersForPreloading(paramString);
      if (localObject == null) {
        return "";
      }
      paramString = new StringBuilder();
      localObject = ((HashMap)localObject).entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        if (paramString.length() > 0) {
          paramString.append("\r\n");
        }
        paramString.append(((String)localEntry.getKey()).toLowerCase(Locale.US));
        paramString.append(":");
        paramString.append((String)localEntry.getValue());
      }
      paramString.append("\r\n");
      paramString = paramString.toString();
      return paramString;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return "";
  }
  
  @CalledByNative
  public void onGetTtsText(String paramString, int paramInt)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension == null) {
      return;
    }
    localIX5WebViewClientExtension.onGetTtsText(paramString, paramInt);
  }
  
  public void onHide()
  {
    WebRtcUtils.a(this.mCurrentReportPageIndex);
    WebARCameraUtils.b(this.mCurrentReportPageIndex);
    if (this.mAwContents.getContentViewCore() != null) {
      com.tencent.smtt.memory.e.a(this.mContentsClientAdapter.hashCode(), this.mAwContents.getTencentContentViewCore().getUrl(), 5);
    }
  }
  
  @CalledByNative
  public void onHideAdSuccess()
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension != null) {
      localIX5WebViewClientExtension.onHideAdSuccess();
    }
  }
  
  public void onMatchedPreLoadingView(String paramString) {}
  
  @CalledByNative
  void onNativeWebViewChromiumExtensionDestroyed(long paramLong)
  {
    this.mNativeWebViewChromiumExtension = 0L;
    stopMainThreadBlockedDetect();
  }
  
  @CalledByNative
  public void onNotifyAudioStatus(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mWebViewClientExtension != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putBoolean("isaudio", paramBoolean1);
      localBundle.putBoolean("status", paramBoolean2);
      this.mWebViewClientExtension.onMiscCallBack("onNotifyAudioStatus", localBundle);
    }
  }
  
  public void onNotifyResourceRequestURL(final String paramString)
  {
    if (this.mWebViewClientExtension == null) {
      return;
    }
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        Bundle localBundle = new Bundle();
        localBundle.putString("url", paramString);
        if (WebViewChromiumExtension.this.mWebViewClientExtension != null) {
          WebViewChromiumExtension.this.mWebViewClientExtension.onMiscCallBack("onNotifyResourceRequestURL", localBundle);
        }
      }
    });
  }
  
  public void onNotifyTouchEvent(int paramInt)
  {
    if (this.mWebViewClientExtension != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putInt("action", paramInt);
      this.mWebViewClientExtension.onMiscCallBack("onNotifyTouchEvent", localBundle);
    }
  }
  
  public void onPageFinished(String paramString)
  {
    this.mTouchCount = 0;
  }
  
  public void onPageStarted(String paramString)
  {
    this.mWebContent = null;
    this.mWXTranslateHelper.a(null);
  }
  
  public void onPageStarted(String paramString, boolean paramBoolean)
  {
    this.mFirstScreen = 0L;
    this.mPageStart = SystemClock.uptimeMillis();
    this.mNavigateUrl = paramString;
    this.isNewUrl = true;
    com.tencent.smtt.net.e.a().a(this.mContentsClientAdapter, getBlankScreenStatus(), 1);
    resetBlankScreenStatus();
    com.tencent.smtt.net.e.a().a(this.mContentsClientAdapter, paramString, paramBoolean, getSettingsExtension().getZxWebViewType());
    g.a().a(this.mContentsClientAdapter, paramString);
    TencentAwContent localTencentAwContent = this.mAwContents;
    if (localTencentAwContent != null) {
      this.hasShown = localTencentAwContent.isShowOnScreen();
    }
    f.a().a(this.mContentsClientAdapter.hashCode(), 1, getCurrentPageMaxScrollRatio(), getCurrentPageNumber(), getTouchEventCount());
    f.a().a(this.mContentsClientAdapter.hashCode(), paramString, 1);
    resetTouchEventCount();
    com.tencent.smtt.memory.e.a(this.mContentsClientAdapter.hashCode(), paramString, 0);
    this.mPopwindowBlockedList.clear();
    com.tencent.smtt.net.b.a().a(this, paramString);
  }
  
  public void onPause()
  {
    com.tencent.smtt.net.e.a().a(this.mContentsClientAdapter, getBlankScreenStatus(), 11);
    f.a().a(this.mContentsClientAdapter.hashCode(), 11, getCurrentPageMaxScrollRatio(), getCurrentPageNumber(), getTouchEventCount());
    resetTouchEventCount();
  }
  
  public void onPermissionRequest(AwPermissionRequest paramAwPermissionRequest)
  {
    onPermissionRequestInternal(paramAwPermissionRequest, true);
  }
  
  public void onPermissionRequestInternal(AwPermissionRequest paramAwPermissionRequest, boolean paramBoolean)
  {
    if (!(paramAwPermissionRequest instanceof TencentAwPermissionRequest))
    {
      Log.e("WebViewChromiumExtension", "permissionRequest not instanceof TencentAwPermissionRequest");
      return;
    }
    final TencentAwPermissionRequest localTencentAwPermissionRequest = (TencentAwPermissionRequest)paramAwPermissionRequest;
    localTencentAwPermissionRequest.setPermissionRequestClient(this);
    String str = localTencentAwPermissionRequest.getOrigin().toString();
    final long l = localTencentAwPermissionRequest.getResources();
    if (isRequestPermissionCached(localTencentAwPermissionRequest, l, str)) {
      return;
    }
    if (isRequestPermissionInWhiteList(localTencentAwPermissionRequest, l, str)) {
      return;
    }
    paramAwPermissionRequest = this.mWebChromeClientExtension;
    boolean bool = true;
    Object localObject;
    if (paramAwPermissionRequest != null) {
      try
      {
        paramAwPermissionRequest = new a(str, this.mAwContents, localTencentAwPermissionRequest);
        localObject = Long.TYPE;
        localObject = this.mWebChromeClientExtension.getClass().getMethod("onPermissionRequest", new Class[] { String.class, localObject, MediaAccessPermissionsCallback.class });
        ((Method)localObject).setAccessible(true);
        paramBoolean = ((Boolean)((Method)localObject).invoke(this.mWebChromeClientExtension, new Object[] { str, Long.valueOf(l), paramAwPermissionRequest })).booleanValue();
      }
      catch (Throwable paramAwPermissionRequest)
      {
        paramAwPermissionRequest.printStackTrace();
      }
    } else {
      paramBoolean = false;
    }
    if (paramBoolean)
    {
      paramAwPermissionRequest = new StringBuilder();
      paramAwPermissionRequest.append("onPermissionRequest WebChromeClientExtension invoke ret: ");
      paramAwPermissionRequest.append(paramBoolean);
      LiveLog.d("WebViewChromiumExtension", paramAwPermissionRequest.toString());
      return;
    }
    int i;
    if ((0x2 & l) != 0L) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if ((0x4 & l) != 0L) {
      j = 1;
    } else {
      j = 0;
    }
    for (;;)
    {
      try
      {
        localObject = SmttResource.getString("x5_media_access_permission_prompt", "string");
        if ((i != 0) && (j == 0))
        {
          paramAwPermissionRequest = SmttResource.getString("x5_video_capture_permission_prompt", "string");
        }
        else
        {
          paramAwPermissionRequest = (AwPermissionRequest)localObject;
          if (j != 0)
          {
            paramAwPermissionRequest = (AwPermissionRequest)localObject;
            if (i == 0) {
              paramAwPermissionRequest = SmttResource.getString("x5_audio_capture_permission_prompt", "string");
            }
          }
        }
        if (!this.mWebView.getSettingsExtension().getDayOrNight())
        {
          paramBoolean = bool;
          localObject = new e.a(this.mWebView.getContext(), 2, paramBoolean);
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(str);
          localStringBuilder.append(paramAwPermissionRequest);
          ((e.a)localObject).a(localStringBuilder.toString()).b(SmttResource.getString("x5_accept", "string"), new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
              WebViewChromiumExtension.this.onPermissionRequestGrant(l, localTencentAwPermissionRequest);
              this.jdField_a_of_type_OrgChromiumTencentAndroid_webviewPermissionTencentAwPermissionRequest.grant(l);
            }
          }).a(SmttResource.getString("x5_prohibit", "string"), new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
              WebViewChromiumExtension.this.onPermissionRequestDeny(l, localTencentAwPermissionRequest);
              this.jdField_a_of_type_OrgChromiumTencentAndroid_webviewPermissionTencentAwPermissionRequest.deny();
            }
          }).a(new DialogInterface.OnCancelListener()
          {
            public void onCancel(DialogInterface paramAnonymousDialogInterface)
            {
              WebViewChromiumExtension.this.onPermissionRequestDeny(l, localTencentAwPermissionRequest);
              this.jdField_a_of_type_OrgChromiumTencentAndroid_webviewPermissionTencentAwPermissionRequest.deny();
            }
          }).a();
          return;
        }
      }
      catch (Exception paramAwPermissionRequest)
      {
        paramAwPermissionRequest.printStackTrace();
        return;
      }
      paramBoolean = false;
    }
  }
  
  public void onPermissionRequestOnUI(TencentAwPermissionRequest paramTencentAwPermissionRequest, long paramLong)
  {
    for (;;)
    {
      try
      {
        Object localObject = this.mWebView.getContext().getPackageManager();
        if (((PackageManager)localObject).checkPermission("android.permission.CAMERA", this.mWebView.getContext().getPackageName()) == 0)
        {
          i = 1;
          if (i == 0)
          {
            localObject = this.mWebView.getContext().getApplicationInfo().loadLabel((PackageManager)localObject).toString();
            Context localContext = this.mWebView.getContext();
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(SmttResource.getString("x5_ar_set_system", "string"));
            localStringBuilder.append((String)localObject);
            localStringBuilder.append(SmttResource.getString("x5_ar_set_camera", "string"));
            Toast.makeText(localContext, localStringBuilder.toString(), 1).show();
            paramTencentAwPermissionRequest.deny();
            LiveLog.d("WebViewChromiumExtension", "onPermissionRequest app checkPermission deny !!");
            return;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        paramTencentAwPermissionRequest.grantOnNative(paramLong);
        return;
      }
      int i = 0;
    }
  }
  
  public void onPinchBeginEvent()
  {
    if (!this.mSettingsExtension.getForcePinchScaleEnabled()) {
      return;
    }
    if (this.mHandler.hasMessages(7))
    {
      this.mHandler.removeMessages(7);
      TencentWebViewDatabaseAdapter.getInstance(this.mWebView.getContext()).setForcePinchScale(UrlUtils.getHost(this.mWebView.getRealWebView().getUrl()));
    }
    Toast localToast = this.mForcePinchScaleToast;
    if (localToast != null) {
      localToast.cancel();
    }
  }
  
  public void onPinchEndEvent(float paramFloat1, float paramFloat2)
  {
    if (!this.mSettingsExtension.getForcePinchScaleEnabled()) {
      return;
    }
    if (forceScaleDisabled()) {
      return;
    }
    if (paramFloat1 >= paramFloat2)
    {
      String str = UrlUtils.getHost(this.mWebView.getRealWebView().getUrl());
      if (TencentWebViewDatabaseAdapter.getInstance(this.mWebView.getContext()).getForcePinchScale(str)) {
        return;
      }
      if (this.mForcePinchScaleToast == null)
      {
        if (this.mX5ForcePinchScale == null) {
          this.mX5ForcePinchScale = SharedResource.getString("x5_force_pinch_scale", "string");
        }
        this.mForcePinchScaleToast = h.a(this.mWebView.getContext(), this.mX5ForcePinchScale, 1, false);
      }
      this.mForcePinchScaleToast.show();
      this.mHandler.removeMessages(7);
      Message localMessage = this.mHandler.obtainMessage(7, str);
      this.mHandler.sendMessageDelayed(localMessage, 3000L);
      this.mSettingsExtension.setForcePinchScale(true, str);
    }
  }
  
  @CalledByNative
  public void onPreLoadCallback(int paramInt, String paramString)
  {
    if (this.mWebViewClientExtension == null) {
      return;
    }
    if (paramString != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
      reportPreloadStatus(paramInt);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onPreLoadCallback type=");
      localStringBuilder.append(paramInt);
      localStringBuilder.append(" url=");
      localStringBuilder.append(paramString);
      MttLog.d("preload_switch", localStringBuilder.toString());
      if (paramInt == -1) {
        return;
      }
      try
      {
        this.mWebViewClientExtension.onPreloadCallback(paramInt, paramString);
        return;
      }
      catch (NoSuchMethodError paramString)
      {
        paramString.printStackTrace();
        return;
      }
    }
  }
  
  public void onPrefetchResourceHit(boolean paramBoolean) {}
  
  @CalledByNative
  public void onPrereadingFinished(String paramString)
  {
    this.mIsPrereadingValid = true;
    this.mPreRenderUrl = paramString;
    paramString = this.mWebViewClientExtension;
    if (paramString == null) {
      return;
    }
    paramString.onPreReadFinished();
  }
  
  @CalledByNative
  public void onReceiveBitmap(Bitmap paramBitmap)
  {
    this.mBitmap = paramBitmap;
  }
  
  public void onReceivedPopupContents()
  {
    if (this.mNativeWebViewChromiumExtension != 0L) {
      destroy();
    }
    this.mNativeWebViewChromiumExtension = nativeInit(this.mAwContents.getWebContents());
    if (this.mAwContents.getContentViewCore() != null)
    {
      this.mAwContents.getTencentContentViewCore().setContentViewClientExtension(this);
      this.mAwContents.getTencentContentViewCore().getTencentSelectionPopupController().setSelectionControllerClient(this);
    }
    k localk = this.mWebContentsObserverExtension;
    if (localk != null) {
      localk.destroy();
    }
    this.mWebContentsObserverExtension = new k(this.mAwContents.getWebContents());
  }
  
  @CalledByNative
  public void onReceivedPushStateUrl(final String paramString1, final String paramString2)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        if (WebViewChromiumExtension.this.getQQBrowserClient() != null) {
          WebViewChromiumExtension.this.getQQBrowserClient().onReportMainresourceInDirectMode(paramString1);
        }
        HashMap localHashMap = new HashMap();
        localHashMap.put("url", paramString1);
        localHashMap.put("refer", paramString2);
        localHashMap.put("proxy", "0");
        localHashMap.put("result", "1");
        localHashMap.put("duration", "0");
        SmttServiceProxy.getInstance().uploadTbsDirectInfoToBeacon(localHashMap);
      }
    });
  }
  
  public void onReceivedSslError(Callback<Boolean> paramCallback, SslError paramSslError, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mAwContents.onReceivedSslError(paramCallback, paramSslError, paramString1, paramString2, paramString3, paramBoolean1, paramBoolean2);
  }
  
  @CalledByNative
  void onReportFramePerformanceInfo(int paramInt1, long paramLong1, long paramLong2, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2)
  {
    com.tencent.smtt.b.a.a();
    if (com.tencent.smtt.b.a.jdField_a_of_type_Boolean)
    {
      com.tencent.smtt.b.a.a();
      com.tencent.smtt.b.a.jdField_a_of_type_Boolean = false;
      com.tencent.smtt.b.a.a();
      com.tencent.smtt.b.a.c = paramLong1;
      com.tencent.smtt.b.a.a();
      com.tencent.smtt.b.a.jdField_d_of_type_Long = paramLong2;
      com.tencent.smtt.b.a.a();
      com.tencent.smtt.b.a.jdField_d_of_type_Int = paramInt2;
      com.tencent.smtt.b.a.a();
      com.tencent.smtt.b.a.e = paramInt3;
      com.tencent.smtt.b.a.a();
      com.tencent.smtt.b.a.jdField_a_of_type_Float = paramFloat1;
      com.tencent.smtt.b.a.a();
      com.tencent.smtt.b.a.b = paramFloat2;
    }
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("frame_start_time", paramLong1);
      localJSONObject.put("frame_end_time", paramLong2);
      localJSONObject.put("layer_num", paramInt2);
      localJSONObject.put("total_layer_mem", paramInt3);
      localJSONObject.put("min_layer_size", paramFloat1);
      localJSONObject.put("max_layer_size", paramFloat2);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    this.mAwContents.onReportFramePerformanceInfo(localJSONObject);
  }
  
  @CalledByNative
  void onReportJSPerformanceInfo(String paramString, int paramInt, double paramDouble1, double paramDouble2)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("info", paramString);
      localJSONObject.put("start time", paramDouble1);
    }
    catch (JSONException paramString)
    {
      paramString.printStackTrace();
    }
    this.mAwContents.onReportJSPerformanceInfo(localJSONObject);
  }
  
  public void onReportMainresourceInDirectMode(String paramString)
  {
    if (getQQBrowserClient() != null) {
      getQQBrowserClient().onReportMainresourceInDirectMode(paramString);
    }
  }
  
  public void onReportNightMode()
  {
    try
    {
      h localh = new h(this.mWebView.getContext().getPackageName(), "nightmode");
      this.mHandler.sendMessage(this.mHandler.obtainMessage(11, localh));
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void onReportPrivacyHeaders(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.mWebViewReportClient.a(paramString);
    }
  }
  
  public void onReportResourceInfo(AwContentsClient.AwResourceMetrics paramAwResourceMetrics)
  {
    if (this.mWebViewClientExtension != null)
    {
      if (this.mContentsClientAdapter == null) {
        return;
      }
      this.mWebViewReportClient.a(paramAwResourceMetrics);
      this.mWebViewReportClient.a(this.mWebViewClientExtension);
      this.mWebViewReportClient.b(this.mContentsClientAdapter.hashCode());
      this.mWebViewReportClient.a(this.mContentsClientAdapter.hashCode());
      return;
    }
  }
  
  public void onReportStgwTime(AwContentsClient.AwStgwTime paramAwStgwTime)
  {
    if (e.a().g())
    {
      Handler localHandler = this.mHandler;
      localHandler.sendMessage(localHandler.obtainMessage(13, paramAwStgwTime));
    }
  }
  
  public void onResize()
  {
    long l = this.mNativeWebViewChromiumExtension;
    if (l != 0L) {
      nativeWasResized(l);
    }
  }
  
  public void onResolveProxyHeadersCmd(String paramString)
  {
    if (paramString.startsWith("cmd=dump_upload"))
    {
      com.tencent.smtt.c.a.a().a(paramString);
      return;
    }
    com.tencent.smtt.livelog.a.a().b(paramString);
  }
  
  @CalledByNative
  public void onScreenshotReady(int paramInt)
  {
    if (this.mNativeWebViewChromiumExtension == 0L) {
      return;
    }
    if (paramInt == 55534)
    {
      Object localObject = this.mScreenshot;
      if ((localObject != null) && (((WeakReference)localObject).get() != null))
      {
        if (((Bitmap)this.mScreenshot.get()).isRecycled()) {
          return;
        }
        nativeTakeScreenshotWithPageID(this.mNativeWebViewChromiumExtension, 55534, (Bitmap)this.mScreenshot.get());
        localObject = this.mCallback;
        if (localObject != null) {
          ((Runnable)localObject).run();
        }
        this.mCallback = null;
        this.mScreenshot = null;
        return;
      }
    }
  }
  
  public void onScrollChanged()
  {
    this.mAwContents.adjustAutofillPositionOnScrollChanged();
  }
  
  public void onScrollEnded() {}
  
  public void onSelectionEnd()
  {
    ISelectionInterface localISelectionInterface = this.mSelectionListener;
    if ((localISelectionInterface instanceof X5Selection)) {
      try
      {
        ((X5Selection)localISelectionInterface).onSelectionEnd();
        return;
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
  }
  
  public void onShow()
  {
    TencentWebViewProxy localTencentWebViewProxy = this.mWebView;
    if (localTencentWebViewProxy != null)
    {
      WebRtcUtils.a(this.mCurrentReportPageIndex, localTencentWebViewProxy.getUrl());
      WebARCameraUtils.a(this.mCurrentReportPageIndex, this.mWebView.getUrl());
      WebARCameraUtils.a(this.mWebView, this.mWebChromeClientExtension);
      WebARCameraUtils.a(this.mCurrentReportPageIndex);
    }
    if (this.mAwContents.getContentViewCore() != null) {
      com.tencent.smtt.memory.e.a(this.mContentsClientAdapter.hashCode(), this.mAwContents.getTencentContentViewCore().getUrl(), 4);
    }
  }
  
  public void onShowImageBrowser(boolean paramBoolean)
  {
    if (this.mWebView == null) {
      return;
    }
    if ((!SmttServiceProxy.getInstance().isClickImageScanEnabled(this.mWebView.getContext(), this.mWebView)) && (!e.a().n())) {
      return;
    }
    if ((this.mWebView.getParent() != null) && (this.mWebView.getVisibility() == 0))
    {
      Object localObject = this.mWebView.getHitTestResult();
      if (localObject != null)
      {
        if (((IX5WebViewBase.HitTestResult)localObject).getType() != 5) {
          return;
        }
        localObject = this.mWebView;
        if ((localObject instanceof X5WebViewAdapter)) {
          ((X5WebViewAdapter)localObject).onShowImageBrowser(paramBoolean);
        }
      }
      else {}
    }
  }
  
  public void onShowSelectPopup(Rect paramRect, String[] paramArrayOfString, int[] paramArrayOfInt1, boolean paramBoolean, int[] paramArrayOfInt2)
  {
    if (this.mWebViewClientExtension != null)
    {
      paramRect = new boolean[paramArrayOfString.length];
      int j = 0;
      int i = 0;
      while (i < paramRect.length)
      {
        paramRect[i] = 0;
        i += 1;
      }
      i = 0;
      while (i < paramArrayOfInt1.length)
      {
        if (paramArrayOfInt1[i] == 0) {
          paramRect[i] = 1;
        }
        if (paramArrayOfInt1[i] == 2) {
          paramArrayOfInt1[i] = 1;
        } else {
          paramArrayOfInt1[i] = 0;
        }
        i += 1;
      }
      if (canShowAlertDialog())
      {
        new com.tencent.smtt.webkit.ui.b(paramArrayOfString, paramArrayOfInt1, paramArrayOfInt2, paramRect, paramBoolean, this.mWebView).a();
        return;
      }
      if (paramBoolean)
      {
        this.mWebViewClientExtension.onShowMutilListBox(paramArrayOfString, paramArrayOfInt1, null, paramArrayOfInt2);
        return;
      }
      i = j;
      if (paramArrayOfInt2.length > 0)
      {
        i = j;
        if (paramArrayOfInt2[0] > -1) {
          i = paramArrayOfInt2[0];
        }
      }
      this.mWebViewClientExtension.onShowListBox(paramArrayOfString, paramArrayOfInt1, null, i);
    }
  }
  
  @CalledByNative
  public void onShowTtsBar()
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension == null) {
      return;
    }
    localIX5WebViewClientExtension.onShowTtsBar();
  }
  
  public void onSizeChanged(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4)
  {
    TencentWebViewProxy localTencentWebViewProxy = this.mWebView;
    if (localTencentWebViewProxy == null) {
      return;
    }
    localTencentWebViewProxy.getRealWebView().getWidth();
    this.mWebView.getRealWebView().getHeight();
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        if (WebViewChromiumExtension.this.mNativeWebViewChromiumExtension != 0L)
        {
          WebViewChromiumExtension localWebViewChromiumExtension = WebViewChromiumExtension.this;
          localWebViewChromiumExtension.nativeOnSizeChanged(localWebViewChromiumExtension.mNativeWebViewChromiumExtension, paramInt1, paramInt2, paramInt3, paramInt4);
        }
        WebARCameraUtils.a(paramInt1, paramInt2);
      }
    });
  }
  
  public void onSoftKeyBoardIsShowing(boolean paramBoolean)
  {
    this.mWebView.setSoftKeyBoardIsShowing(paramBoolean);
  }
  
  public void onSoftKeyBoardShow()
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension != null) {
      localIX5WebViewClientExtension.onSoftKeyBoardShow();
    }
  }
  
  public void onStartUpdatingPosition()
  {
    ISelectionInterface localISelectionInterface = this.mSelectionListener;
    if (localISelectionInterface != null) {
      try
      {
        localISelectionInterface.hideSelectionView();
        if ((this.mSelectionListener instanceof X5Selection))
        {
          ((X5Selection)this.mSelectionListener).onHandleViewDraggingBegin();
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
  }
  
  public void onTopControlsChanged(float paramFloat)
  {
    this.mBrowserControlsOffsetManager.a(paramFloat);
    TencentWebViewProxy localTencentWebViewProxy = this.mWebView;
    if (localTencentWebViewProxy != null) {
      localTencentWebViewProxy.onBrowserControlsChanged((int)paramFloat);
    }
  }
  
  public void onTopControlsChanged(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.mBrowserControlsOffsetManager.a(paramFloat1, paramFloat2, paramFloat3);
    TencentWebViewProxy localTencentWebViewProxy = this.mWebView;
    if (localTencentWebViewProxy != null) {
      localTencentWebViewProxy.onBrowserControlsChanged((int)(paramFloat1 * paramFloat2 * paramFloat3));
    }
  }
  
  public void onTouchEvent(MotionEvent paramMotionEvent)
  {
    String str = URLUtil.getHost(this.mWebView.getRealWebView().getUrl());
    if ((this.mSettingsExtension.getFakeLoginEnabled()) && (!TextUtils.isEmpty(str)) && (!str.endsWith("qq.com")))
    {
      if (this.mScreenHeight == 0) {
        this.mScreenHeight = ((WindowManager)this.mWebView.getContext().getSystemService("window")).getDefaultDisplay().getHeight();
      }
      switch (paramMotionEvent.getAction())
      {
      default: 
        
      case 2: 
        this.mCurPosY = paramMotionEvent.getY();
        return;
      case 1: 
      case 3: 
        if (Math.abs(this.mCurPosY - this.mPosY) < 10.0F)
        {
          if (paramMotionEvent.getY() > this.mScreenHeight / 2)
          {
            this.mTouchCount += 1;
            if (this.mTouchCount >= 6)
            {
              paramMotionEvent = new Bundle();
              paramMotionEvent.putBoolean("virtual-keyboard", true);
              onFakeLoginRecognised(paramMotionEvent);
              this.mTouchCount = 0;
            }
          }
          else
          {
            this.mTouchCount = 0;
          }
        }
        else
        {
          this.mTouchCount = 0;
          return;
        }
        break;
      case 0: 
        float f = paramMotionEvent.getY();
        this.mPosY = f;
        this.mCurPosY = f;
      }
    }
  }
  
  @CalledByNative
  void onUpdateHTMLElementAssoicateNativePanel(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.mAwContents.onUpdateHTMLElementAssoicateNativePanel(paramInt1, paramString, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  public void onVisibilityChanged(View paramView, int paramInt)
  {
    if (paramInt != 0)
    {
      if (this.isNewUrl)
      {
        paramView = this.mWebView;
        if ((paramView != null) && (this.mContentsClientAdapter != null))
        {
          this.isNewUrl = false;
          paramView = paramView.getRealWebView().getUrl();
          if ((!TextUtils.isEmpty(paramView)) && (!paramView.equals(this.mNavigateUrl)))
          {
            com.tencent.smtt.net.e.a().a(this.mContentsClientAdapter.hashCode(), this.mNavigateUrl, paramView);
            f.a().a(this.mContentsClientAdapter.hashCode(), this.mNavigateUrl, paramView);
          }
        }
      }
      com.tencent.smtt.net.e.a().a(this.mContentsClientAdapter, getBlankScreenStatus(), 4);
      f.a().a(this.mContentsClientAdapter.hashCode(), 4, getCurrentPageMaxScrollRatio(), getCurrentPageNumber(), getTouchEventCount());
    }
  }
  
  public void onWindowVisibilityChanged(int paramInt)
  {
    if (paramInt != 0)
    {
      if (this.isNewUrl)
      {
        Object localObject = this.mWebView;
        if ((localObject != null) && (this.mContentsClientAdapter != null))
        {
          this.isNewUrl = false;
          localObject = ((TencentWebViewProxy)localObject).getRealWebView().getUrl();
          if ((!TextUtils.isEmpty((CharSequence)localObject)) && (!((String)localObject).equals(this.mNavigateUrl)))
          {
            com.tencent.smtt.net.e.a().a(this.mContentsClientAdapter.hashCode(), this.mNavigateUrl, (String)localObject);
            f.a().a(this.mContentsClientAdapter.hashCode(), this.mNavigateUrl, (String)localObject);
          }
        }
      }
      com.tencent.smtt.net.e.a().a(this.mContentsClientAdapter, getBlankScreenStatus(), 10);
      f.a().a(this.mContentsClientAdapter.hashCode(), 4, getCurrentPageMaxScrollRatio(), getCurrentPageNumber(), getTouchEventCount());
    }
  }
  
  public void pasteFromClipboard(final CharSequence paramCharSequence)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.pasteFromClipboard(paramCharSequence);
        }
      });
      return;
    }
    if (this.mAwContents.getContentViewCore() != null)
    {
      InputConnection localInputConnection = ImeAdapterImpl.fromWebContents(this.mAwContents.getContentViewCore().getWebContents()).getInputConnectionForTest();
      if (localInputConnection != null) {
        localInputConnection.commitText(paramCharSequence, 1);
      }
    }
  }
  
  public void pauseAudio()
  {
    long l = this.mNativeWebViewChromiumExtension;
    if (l != 0L) {
      nativeOnAudioStateChanged(l, true);
    }
  }
  
  public boolean performContextMenuActionPaste()
  {
    Object localObject = this.mWebViewClientExtension;
    if (localObject != null)
    {
      localObject = ((IX5WebViewClientExtension)localObject).onMiscCallBack("contextMenuActionPaste", null);
      if ((localObject != null) && ((localObject instanceof Boolean))) {
        return ((Boolean)localObject).booleanValue();
      }
    }
    return false;
  }
  
  public void playAudio()
  {
    long l = this.mNativeWebViewChromiumExtension;
    if (l != 0L) {
      nativeOnAudioStateChanged(l, false);
    }
  }
  
  public void preConnect(String paramString, int paramInt) {}
  
  public void preLoad(String paramString, int paramInt1, int paramInt2, Map<String, String> paramMap)
  {
    if (paramString != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
      if (!isPrerenderEnabled()) {
        return;
      }
      Object localObject2 = paramString;
      Object localObject1;
      Object localObject3;
      if (paramMap != null)
      {
        int j = -1;
        long l1 = -1L;
        int i;
        int k;
        try
        {
          i = Integer.parseInt((String)paramMap.get("TYPE"));
          try
          {
            long l2 = Long.parseLong((String)paramMap.get("COPY_TIME"));
            l1 = l2;
            k = Integer.parseInt((String)paramMap.get("WORD_LENGTH"));
            j = k;
            l1 = l2;
          }
          catch (NumberFormatException localNumberFormatException1) {}
          localObject2 = new StringBuilder();
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          i = 0;
        }
        ((StringBuilder)localObject2).append("Exception1 ");
        ((StringBuilder)localObject2).append(localNumberFormatException2);
        MttLog.d("preload_switch", ((StringBuilder)localObject2).toString());
        localObject1 = paramString;
        try
        {
          k = Integer.parseInt((String)paramMap.get("TYPE"));
          i = k;
          localObject1 = paramString;
          localObject2 = (String)paramMap.get("mimeType");
          i = k;
          localObject1 = paramString;
          String str = (String)paramMap.get("encoding");
          i = k;
          localObject1 = paramString;
          Object localObject4 = (String)paramMap.get("baseUrl");
          i = k;
          localObject1 = paramString;
          Object localObject5 = (String)paramMap.get("historyUrl");
          if (k == 3)
          {
            i = k;
            localObject1 = paramString;
            localObject4 = new StringBuilder();
            i = k;
            localObject1 = paramString;
            ((StringBuilder)localObject4).append("about:blank,");
            i = k;
            localObject1 = paramString;
            ((StringBuilder)localObject4).append(paramString);
            i = k;
            localObject1 = paramString;
            paramString = ((StringBuilder)localObject4).toString();
            i = k;
            localObject1 = paramString;
            paramString = buildDataUri(paramString, (String)localObject2, "base64".equals(str), null);
            i = k;
            localObject1 = paramString;
          }
          else
          {
            i = k;
            localObject1 = paramString;
            if (k == 4)
            {
              i = k;
              localObject1 = paramString;
              if (localObject4 != null)
              {
                i = k;
                localObject1 = paramString;
                localObject5 = new StringBuilder();
                i = k;
                localObject1 = paramString;
                ((StringBuilder)localObject5).append((String)localObject4);
                i = k;
                localObject1 = paramString;
                ((StringBuilder)localObject5).append(",");
                i = k;
                localObject1 = paramString;
                ((StringBuilder)localObject5).append(paramString);
                i = k;
                localObject1 = paramString;
                paramString = ((StringBuilder)localObject5).toString();
                if (localObject4 != null)
                {
                  i = k;
                  localObject1 = paramString;
                  if (((String)localObject4).startsWith("data:"))
                  {
                    i = k;
                    localObject1 = paramString;
                    paramString = buildDataUri(paramString, (String)localObject2, "base64".equals(str), str);
                    i = k;
                    localObject1 = paramString;
                    break label607;
                  }
                }
                i = k;
                localObject1 = paramString;
                try
                {
                  localObject2 = buildDataUri(android.util.Base64.encodeToString(paramString.getBytes("utf-8"), 0), (String)localObject2, true, "utf-8");
                  i = k;
                  localObject1 = localObject2;
                }
                catch (UnsupportedEncodingException localUnsupportedEncodingException)
                {
                  i = k;
                  localObject1 = paramString;
                  localUnsupportedEncodingException.printStackTrace();
                  return;
                }
              }
            }
          }
          label607:
          localObject1 = new StringBuilder();
        }
        catch (NumberFormatException paramString)
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("Exception2 ");
          ((StringBuilder)localObject3).append(paramString);
          MttLog.d("preload_switch", ((StringBuilder)localObject3).toString());
          if ((!paramMap.isEmpty()) && (paramMap.containsKey("TYPE")) && (paramMap.containsKey("COPY_TIME")) && (paramMap.containsKey("WORD_LENGTH")))
          {
            paramString = new StringBuilder();
            paramString.append("  Type=");
            paramString.append(i);
            paramString.append("   wordLength=");
            paramString.append(j);
            paramString.append("   Time=");
            paramString.append(l1);
            paramString.append("  Gap=");
            paramString.append(System.currentTimeMillis() - l1);
            MttLog.d("preload_switch", paramString.toString());
            if ((i == 2) && (j > 10)) {
              return;
            }
            if (System.currentTimeMillis() - l1 > 12000L) {
              return;
            }
          }
          localObject3 = localObject1;
          if (i == 5)
          {
            paramString = new StringBuilder();
            paramString.append("[#PMP#]");
            paramString.append((String)localObject1);
            localObject3 = paramString.toString();
          }
        }
      }
      ((StringBuilder)localObject1).append("Url=");
      ((StringBuilder)localObject1).append((String)localObject3);
      ((StringBuilder)localObject1).append("  HEADER=");
      if (paramMap != null) {
        paramString = paramMap.toString();
      } else {
        paramString = "null";
      }
      ((StringBuilder)localObject1).append(paramString);
      MttLog.d("preload_switch", ((StringBuilder)localObject1).toString());
      this.mWebView.getView().layout(0, 0, paramInt1, paramInt2);
      paramString = new ArrayList();
      paramString.add(localObject3);
      preLoad(paramString);
      return;
    }
  }
  
  public void pruneMemoryCache()
  {
    MemoryChecker.trim(10);
  }
  
  public void recordCertErrorInfo(String paramString1, String paramString2, String paramString3, int paramInt1, String paramString4, int paramInt2)
  {
    if (this.mContentsClientAdapter == null) {
      return;
    }
    u.d locald = new u.d();
    locald.jdField_a_of_type_JavaLangString = paramString1;
    locald.b = paramString2;
    locald.c = paramString3;
    locald.jdField_a_of_type_Int = paramInt1;
    locald.jdField_d_of_type_JavaLangString = paramString4;
    locald.jdField_a_of_type_Long = paramInt2;
    locald.g = this.mContentsClientAdapter.hashCode();
    locald.e = 1;
    com.tencent.smtt.net.e.a().a(locald);
  }
  
  @CalledByNative
  public void recordCorsErrorInfo(String paramString1, String paramString2, int paramInt1, boolean paramBoolean1, boolean paramBoolean2, int paramInt2, String paramString3, long paramLong)
  {
    if (this.mContentsClientAdapter == null) {
      return;
    }
    u.d locald = new u.d();
    locald.jdField_a_of_type_JavaLangString = paramString1;
    locald.b = UrlUtils.getHost(paramString1);
    locald.c = paramString2;
    locald.jdField_a_of_type_Int = paramInt1;
    locald.jdField_d_of_type_Int = paramInt2;
    locald.jdField_d_of_type_JavaLangString = paramString3;
    locald.jdField_a_of_type_Long = paramLong;
    locald.e = 2;
    locald.g = this.mContentsClientAdapter.hashCode();
    com.tencent.smtt.net.e.a().a(locald);
  }
  
  public void registerServiceWorkerBackground(String paramString1, String paramString2)
  {
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeServiceWorkerBackgroundRegister(l, paramString1, paramString2);
  }
  
  public void reload()
  {
    Object localObject = this.mWebView;
    if (localObject != null)
    {
      localObject = ((TencentWebViewProxy)localObject).getHistoryItem(0);
      if (localObject != null)
      {
        TencentAwContent localTencentAwContent = this.mAwContents;
        if ((localTencentAwContent != null) && (localTencentAwContent.getTencentSettings() != null)) {
          changeOverScrollState(((IX5WebHistoryItem)localObject).getId(), this.mAwContents.getTencentSettings().getDefaultOverScrollState());
        }
      }
    }
  }
  
  public void reloadCustomMetaData()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.reloadCustomMetaData();
        }
      });
      return;
    }
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeReloadCustomMetaData(l);
  }
  
  public void removeHistoryItem(final int paramInt)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.removeHistoryItem(paramInt);
        }
      });
      return;
    }
    if (this.mNativeWebViewChromiumExtension == 0L) {
      return;
    }
    IX5WebHistoryItem localIX5WebHistoryItem = this.mWebView.getHistoryItem(paramInt);
    if (localIX5WebHistoryItem != null) {
      removeOverScrollPageId(localIX5WebHistoryItem.getId());
    }
    nativeRemoveNavigationEntryAtIndex(this.mNativeWebViewChromiumExtension, paramInt);
  }
  
  public void removeOverScrollPageId(int paramInt)
  {
    Integer localInteger = new Integer(paramInt);
    if (this.mDisableOverScrollPageId.contains(localInteger)) {
      this.mDisableOverScrollPageId.remove(localInteger);
    }
  }
  
  public void removeUserSelectedAdInfoByDomain(String paramString)
  {
    AdInfoManager.getInstance().removeUserSelectAdInfoByDomain(paramString);
  }
  
  public void replaceAllInputText(final String paramString)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.replaceAllInputText(paramString);
        }
      });
      return;
    }
    if (this.mAwContents.getContentViewCore() != null)
    {
      ImeAdapterImpl localImeAdapterImpl = ImeAdapterImpl.fromWebContents(this.mAwContents.getContentViewCore().getWebContents());
      if (localImeAdapterImpl != null)
      {
        localImeAdapterImpl.deleteSurroundingText(Integer.MAX_VALUE, Integer.MAX_VALUE);
        localImeAdapterImpl.sendCompositionToNative(paramString, 1, true, 0);
      }
    }
  }
  
  public void replyListBox(final int paramInt)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.replyListBox(paramInt);
        }
      });
      return;
    }
    if (this.mAwContents.getContentViewCore() != null) {
      this.mAwContents.getContentViewCore().selectPopupMenuItems(new int[] { paramInt });
    }
  }
  
  public void replyMultiListBox(final int paramInt, final boolean[] paramArrayOfBoolean)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.replyMultiListBox(paramInt, paramArrayOfBoolean);
        }
      });
      return;
    }
    int[] arrayOfInt = new int[paramArrayOfBoolean.length];
    paramInt = 0;
    int j;
    for (int i = 0; paramInt < paramArrayOfBoolean.length; i = j)
    {
      j = i;
      if (paramArrayOfBoolean[paramInt] != 0)
      {
        arrayOfInt[i] = paramInt;
        j = i + 1;
      }
      paramInt += 1;
    }
    if (this.mAwContents.getContentViewCore() != null) {
      this.mAwContents.getContentViewCore().selectPopupMenuItems(Arrays.copyOf(arrayOfInt, i));
    }
  }
  
  public void reportBadJsMsg(X5BadJsReporter.a parama)
  {
    if (this.mContentsClientAdapter != null)
    {
      if (parama == null) {
        return;
      }
      u.a locala = new u.a();
      locala.jdField_a_of_type_JavaLangString = parama.jdField_a_of_type_JavaLangString;
      locala.b = parama.b;
      locala.c = parama.c;
      locala.jdField_a_of_type_Int = this.mContentsClientAdapter.hashCode();
      com.tencent.smtt.net.e.a().a(locala);
      return;
    }
  }
  
  public void reportDoFingerSearch()
  {
    i locali = this.mReportLongClickClient;
    if (locali != null) {
      locali.b();
    }
  }
  
  public void reportDoLongClick()
  {
    if (this.mWebView == null) {
      return;
    }
    if (this.mReportLongClickClient == null) {
      this.mReportLongClickClient = new i(SmttServiceProxy.getInstance().getFingerSearchInstance((IX5WebView)this.mWebView));
    }
    i locali = this.mReportLongClickClient;
    if (locali != null) {
      locali.a();
    }
  }
  
  @CalledByNative
  public void reportHtmlInfo(int paramInt, String paramString)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension == null) {
      return;
    }
    if (paramInt != 1) {
      return;
    }
    localIX5WebViewClientExtension.onReportHtmlInfo(1, paramString);
  }
  
  @CalledByNative
  public void reportMetricsSavedCountReceived(String paramString1, boolean paramBoolean, int paramInt1, String paramString2, int paramInt2)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension == null) {
      return;
    }
    if (sCompatibleOnMetricsSavedCountReceived)
    {
      long l = paramInt1;
      try
      {
        localIX5WebViewClientExtension.onMetricsSavedCountReceived(paramString1, paramBoolean, l, paramString2, paramInt2);
        return;
      }
      catch (NoSuchMethodError paramString1)
      {
        if ((paramString1.getMessage() != null) && (paramString1.getMessage().contains("onMetricsSavedCountReceived")))
        {
          sCompatibleOnMetricsSavedCountReceived = false;
          return;
        }
        throw paramString1;
      }
    }
  }
  
  public void reportResourceAllInfo(AwContentsClient.AwWebRequestInfo paramAwWebRequestInfo)
  {
    Handler localHandler = this.mHandler;
    localHandler.sendMessage(localHandler.obtainMessage(6, paramAwWebRequestInfo));
  }
  
  public void reportResponseFailedWithErrCode(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("mainurl", paramString1);
    localHashMap.put("host", paramString2);
    localHashMap.put("proxyip", paramString3);
    paramString1 = new StringBuilder();
    paramString1.append(paramInt);
    paramString1.append("");
    localHashMap.put("statuscode", paramString1.toString());
    SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_QB_RESPONSE_FAILED_WITH_ERR_CODE", localHashMap);
  }
  
  public void reportShowLongClickPopupMenu()
  {
    i locali = this.mReportLongClickClient;
    if (locali != null) {
      locali.c();
    }
  }
  
  public void reportTrafficAnomalyInfo(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, int paramInt3, String paramString4, int paramInt4)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("anomalytype", Integer.toString(paramInt1));
    localHashMap.put("selfurl", paramString1);
    localHashMap.put("mainurl", paramString2);
    localHashMap.put("refurl", paramString3);
    localHashMap.put("contentlength", Integer.toString(paramInt2));
    localHashMap.put("resourcetype", Integer.toString(paramInt3));
    localHashMap.put("mimetype", paramString4);
    localHashMap.put("repeatnum", Integer.toString(paramInt4));
    SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_QB_TRAFFIC_ANOMALY_INFO", localHashMap);
  }
  
  public void reportUserAdjustSelectText()
  {
    if (this.mWebViewClientExtension == null) {
      return;
    }
    String str = getSelection();
    if (!TextUtils.isEmpty(str)) {
      try
      {
        this.mWebViewClientExtension.reportFingerSearchAdjustInfo(str);
        return;
      }
      catch (NoSuchMethodError localNoSuchMethodError)
      {
        localNoSuchMethodError.printStackTrace();
      }
    }
  }
  
  public void reportUserFinallySelectText(String paramString)
  {
    e locale = this.mFingerSearchClient;
    if (locale != null) {
      locale.b(paramString);
    }
  }
  
  public void requestExplorerStatistics()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.requestExplorerStatistics();
        }
      });
      return;
    }
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeRequestExplorerStatistics(l);
  }
  
  public boolean requestFocusForInputNode(final int paramInt)
  {
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean a()
        {
          return Boolean.valueOf(WebViewChromiumExtension.this.requestFocusForInputNode(paramInt));
        }
      })).booleanValue();
    }
    long l1 = this.mNativeWebViewChromiumExtension;
    if (l1 == 0L) {
      return false;
    }
    if (paramInt == 130)
    {
      long l2 = this.mNextEditableNode;
      if (l2 != 0L)
      {
        nativeRequestFocusForInputNode(l1, l2);
        return true;
      }
    }
    if (paramInt == 33)
    {
      l1 = this.mPreEditableNode;
      if (l1 != 0L)
      {
        nativeRequestFocusForInputNode(this.mNativeWebViewChromiumExtension, l1);
        return true;
      }
    }
    return false;
  }
  
  public void resetFingerSearchClient()
  {
    e locale = this.mFingerSearchClient;
    if (locale != null) {
      locale.a();
    }
  }
  
  public void resetPrereadingState()
  {
    this.mIsPrereadingValid = false;
  }
  
  public void restoreAddressBarDisplayMode()
  {
    this.mBrowserControlsOffsetManager.a();
  }
  
  public void retrieveFingerSearchContext(final int paramInt)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.retrieveFingerSearchContext(paramInt);
        }
      });
      return;
    }
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeOnRetrieveFingerSearchContext(l, paramInt);
  }
  
  @CalledByNative
  public void savePassword(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append("://");
    localStringBuilder.append(paramString2);
    paramString1 = localStringBuilder.toString();
    paramString2 = getPasswordFromDatabase(paramString1);
    this.mContentsClientAdapter.savePassword(new com.tencent.tbs.core.webkit.ValueCallback()
    {
      public void a(String paramAnonymousString)
      {
        WebViewChromiumExtension.this.savePasswordResponse(paramAnonymousString);
      }
    }, paramString1, paramString3, paramString4, paramString5, paramString6, paramString2);
  }
  
  public void savePasswordResponse(String paramString)
  {
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeSavePasswordResponse(l, paramString);
  }
  
  public void scrollBy(int paramInt1, int paramInt2)
  {
    this.mAwContents.scrollBy(paramInt1, paramInt2);
  }
  
  public void selectBetweenCoordinates(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    nativeSelectBetweenCoordinates(this.mNativeWebViewChromiumExtension, paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.mWebView.getRealWebView().getScrollX(), this.mWebView.getRealWebView().getScrollY());
  }
  
  public void selectWordAroundPosition(Point paramPoint)
  {
    float f;
    if (this.mAwContents.getContentViewCore() != null) {
      f = this.mAwContents.getContentViewCore().getRenderCoordinates().getContentOffsetYPix();
    } else {
      f = 0.0F;
    }
    nativeSelectWordAroundPosition(this.mNativeWebViewChromiumExtension, paramPoint.x, paramPoint.y - f);
  }
  
  public int selectionType()
  {
    if (checkNeedsPost()) {
      ((Integer)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Integer a()
        {
          return Integer.valueOf(WebViewChromiumExtension.this.selectionType());
        }
      })).intValue();
    }
    if (this.mAwContents.getContentViewCore() != null)
    {
      TencentContentViewCore localTencentContentViewCore = this.mAwContents.getTencentContentViewCore();
      if (localTencentContentViewCore != null)
      {
        if (localTencentContentViewCore.hasSelection())
        {
          if (localTencentContentViewCore.isSelectionEditable()) {
            return 2;
          }
          return 3;
        }
        if (localTencentContentViewCore.isSelectionEditable()) {
          return 1;
        }
      }
    }
    return 0;
  }
  
  @CalledByNative
  public void sendInputValue(String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("normal-input-value-change", paramString);
    onFakeLoginRecognised(localBundle);
  }
  
  @CalledByNative
  public void sendPasswordShown()
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("password-input-show", true);
    onFakeLoginRecognised(localBundle);
  }
  
  public void sendRememberMsg(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if (this.mAwContents.getContentViewCore() != null) {
      getPasswordDatabase(this.mAwContents.getContentViewCore().getContext()).insertOrUpdateUsernamePassword(paramString1, paramString2, paramString3, paramString4, paramString5);
    }
  }
  
  @CalledByNative
  public void sendSendRecognisedText(String[] paramArrayOfString)
  {
    Bundle localBundle = new Bundle();
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramArrayOfString.length)
    {
      localArrayList.add(paramArrayOfString[i]);
      i += 1;
    }
    localBundle.putStringArrayList("recognised-text", localArrayList);
    onFakeLoginRecognised(localBundle);
  }
  
  @CalledByNative
  public void sendStartInputPassword()
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("password-start-input", true);
    onFakeLoginRecognised(localBundle);
  }
  
  @CalledByNative
  public void sentBlockInfoToUI(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (this.mWebViewClientExtension == null) {
      return;
    }
    if ((paramInt1 != 0) || (paramInt2 != 0)) {
      this.mWebViewClientExtension.onReportAdFilterInfo(paramInt1, paramInt2, paramString, paramBoolean);
    }
  }
  
  public void setAddressbarDisplayMode(int paramInt, boolean paramBoolean)
  {
    this.mBrowserControlsOffsetManager.a(paramInt, paramBoolean, true);
  }
  
  public void setAddressbarDisplayMode(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mForceNoNeedsScroll) {
      paramBoolean2 = false;
    }
    this.mBrowserControlsOffsetManager.a(paramInt, paramBoolean1, paramBoolean2);
  }
  
  public void setAudioAutoPlayNotify(boolean paramBoolean)
  {
    this.mSettingsExtension.setAudioAutoPlayNotify(paramBoolean);
  }
  
  public void setBackFromSystem() {}
  
  public void setContentCacheCurrentFrameWhenJsLocation(boolean paramBoolean)
  {
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeSetContentCacheCurrentFrameWhenJsLocation(l, paramBoolean);
  }
  
  public void setDownLoadListenerExtension(DownloadListenerExtension paramDownloadListenerExtension)
  {
    this.mDownloadListenerExtension = paramDownloadListenerExtension;
    this.mContentsClientAdapter.setDownLoadListenerExtension(paramDownloadListenerExtension);
  }
  
  public void setEnableAutoPageDiscarding(boolean paramBoolean)
  {
    this.mEnableAutoPageDiscarding = paramBoolean;
  }
  
  public void setEnableAutoPageRestoration(boolean paramBoolean)
  {
    this.mEnableAutoPageRestoration = paramBoolean;
  }
  
  public void setEntryDataForSearchTeam(String paramString)
  {
    SmttServiceProxy.getInstance().setEntryDataForSearchTeam(paramString);
  }
  
  public void setEventType(int paramInt)
  {
    this.mEventType = paramInt;
  }
  
  public void setEyeShieldMode(boolean paramBoolean, int paramInt)
  {
    this.mAwContents.getTencentSettings().setEyeShieldMode(paramBoolean, paramInt);
  }
  
  public void setFakeLoginParams(Bundle paramBundle)
  {
    if (this.mSettingsExtension.getFakeLoginEnabled())
    {
      Object localObject = paramBundle.getString("forbid-input");
      if ((localObject != null) && (!TextUtils.isEmpty((CharSequence)localObject))) {
        if (((String)localObject).equals("*")) {
          this.mSettingsExtension.setForbitInputPassword(true);
        } else {
          nativeSetForbitInputPassword(this.mNativeWebViewChromiumExtension);
        }
      }
      paramBundle = (ArrayList)paramBundle.get("recognised-text");
      int j = paramBundle.size();
      if ((paramBundle != null) && (j > 0))
      {
        localObject = new String[j];
        int i = 0;
        while (i < j)
        {
          localObject[i] = ((String)paramBundle.get(i));
          i += 1;
        }
        nativeSetRecognisedText(this.mNativeWebViewChromiumExtension, (String[])localObject);
      }
    }
  }
  
  public String setFingerSearchRequestInfo(String paramString1, int paramInt1, String paramString2, int paramInt2, int[] paramArrayOfInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append("#");
    localStringBuilder.append(String.valueOf(paramInt1));
    localStringBuilder.append("#");
    localStringBuilder.append(String.valueOf(paramArrayOfInt[0]));
    localStringBuilder.append("#");
    localStringBuilder.append(String.valueOf(paramArrayOfInt[1]));
    localStringBuilder.append("#");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("#");
    localStringBuilder.append(String.valueOf(paramInt2));
    localStringBuilder.append("#");
    localStringBuilder.append(String.valueOf(paramArrayOfInt[2]));
    localStringBuilder.append("#");
    localStringBuilder.append(String.valueOf(paramArrayOfInt[3]));
    return localStringBuilder.toString();
  }
  
  public void setForceEnableZoom(boolean paramBoolean)
  {
    this.mAwContents.getTencentSettings().setForceEnableZoom(paramBoolean);
  }
  
  public void setHandleViewBitmap(Bitmap paramBitmap1, Bitmap paramBitmap2, int paramInt1, int paramInt2) {}
  
  public void setHandleViewLineColor(int paramInt1, int paramInt2) {}
  
  public void setHandleViewLineIsShowing(boolean paramBoolean, int paramInt) {}
  
  public void setHandleViewSelectionColor(int paramInt1, int paramInt2) {}
  
  public void setOverScrollParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3)
  {
    TencentAwContent localTencentAwContent = this.mAwContents;
    if (localTencentAwContent != null) {
      localTencentAwContent.setOverScrollParams(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramDrawable1, paramDrawable2, paramDrawable3);
    }
  }
  
  public void setQQBrowserClient(IX5QQBrowserClient paramIX5QQBrowserClient)
  {
    this.mQQBrowserClient = paramIX5QQBrowserClient;
    this.mBrowserControlsOffsetManager.a(paramIX5QQBrowserClient);
  }
  
  public void setRenderMode(int paramInt) {}
  
  public void setScreenScrollableForHandleView(boolean paramBoolean)
  {
    this.mSelectionController.a(paramBoolean);
  }
  
  public void setSecurityLevel(final SecurityLevelBase paramSecurityLevelBase)
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (WebViewChromiumExtension.this.getQQBrowserClient() != null)
        {
          WebViewChromiumExtension.this.getQQBrowserClient().setSecurityLevel(paramSecurityLevelBase);
          return;
        }
        JSONObject localJSONObject = new JSONObject();
        try
        {
          localJSONObject.put("url", paramSecurityLevelBase.url);
          localJSONObject.put("STLevel", paramSecurityLevelBase.level);
          localJSONObject.put("STType", paramSecurityLevelBase.type);
          localJSONObject.put("STSubLevel", paramSecurityLevelBase.securitySubLevel);
          localJSONObject.put("STFlag", paramSecurityLevelBase.flag);
          localJSONObject.put("STInfoUrl", paramSecurityLevelBase.infoUrl);
          localJSONObject.put("STEvilclass", paramSecurityLevelBase.evilclass);
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
        }
        WebViewChromiumExtension.this.mContentsClientAdapter.notifySecurityLevel(localJSONObject);
      }
    });
  }
  
  public void setSelectListener(ISelectionInterface paramISelectionInterface)
  {
    this.mSelectionListener = paramISelectionInterface;
  }
  
  public void setSiteSecurityInfo(String paramString1, String paramString2)
  {
    this.mAwContents.setSiteSecurityInfo(paramString1, paramString2);
  }
  
  public void setSkvDataForSearchTeam(String paramString)
  {
    SmttServiceProxy.getInstance().setSkvDataForSearchTeam(paramString);
  }
  
  public boolean setTranslateInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.mWXTranslateHelper.a(paramString1, paramString2, paramString3, paramString4, paramString5);
    return true;
  }
  
  public void setWebBackForwardListClient(IX5WebBackForwardListClient paramIX5WebBackForwardListClient)
  {
    this.mWebBackForwardListClient = paramIX5WebBackForwardListClient;
  }
  
  public void setWebChromeClientExtension(IX5WebChromeClientExtension paramIX5WebChromeClientExtension)
  {
    this.mWebChromeClientExtension = paramIX5WebChromeClientExtension;
    WebARCameraUtils.a(this.mWebView, this.mWebChromeClientExtension);
  }
  
  public void setWebViewClientExtension(IX5WebViewClientExtension paramIX5WebViewClientExtension)
  {
    this.mWebViewClientExtension = paramIX5WebViewClientExtension;
  }
  
  public boolean shouldBlockTheRequestCustom(String paramString1, String paramString2, int paramInt)
  {
    boolean bool1 = TextUtils.isEmpty(paramString1);
    boolean bool2 = false;
    if (!bool1)
    {
      paramString1 = UrlUtils.getHost(paramString1);
      if ((!TextUtils.isEmpty(paramString1)) && (("127.0.0.1".equals(paramString1)) || ("localhost".equalsIgnoreCase(paramString1)) || ("[::1]".equals(paramString1)) || ("0:0:0:0:0:0:0:1".equals(paramString1))))
      {
        if (!TextUtils.isEmpty(paramString2)) {
          bool1 = SmttServiceProxy.getInstance().isAllowLocalAddrAccess(paramString2);
        } else {
          bool1 = false;
        }
        if (!bool1) {
          bool2 = true;
        }
        return bool2;
      }
    }
    return false;
  }
  
  @CalledByNative
  public boolean shouldDiscardCurrentPage()
  {
    if (this.mEnableAutoPageDiscarding) {
      return true;
    }
    Object localObject = this.mWebViewClientExtension;
    if (localObject == null) {
      return false;
    }
    try
    {
      localObject = localObject.getClass().getMethod("shouldDiscardCurrentPage", new Class[0]);
      ((Method)localObject).setAccessible(true);
      boolean bool = ((Boolean)((Method)localObject).invoke(this.mWebViewClientExtension, new Object[0])).booleanValue();
      return bool;
    }
    catch (Throwable localThrowable) {}
    return false;
  }
  
  public boolean shouldIgnoreNavigation(Context paramContext, String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt, String paramString2)
  {
    return this.mContentsClientAdapter.shouldOverrideUrlLoadingCustom(paramString1, paramBoolean1, paramBoolean2, paramBoolean3, paramInt, paramString2);
  }
  
  public boolean shouldPopupHideAdDialog()
  {
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean a()
        {
          return Boolean.valueOf(WebViewChromiumExtension.this.shouldPopupHideAdDialog());
        }
      })).booleanValue();
    }
    TencentAwContent.TencentHitTestData localTencentHitTestData = this.mAwContents.getLastTencentHitTestResult();
    if (localTencentHitTestData == null) {
      return false;
    }
    return localTencentHitTestData.canBeHidden;
  }
  
  public boolean shouldReportPerformanceToServer(String paramString)
  {
    return g.a().a(paramString);
  }
  
  public int shouldReportSubResourcePerformance()
  {
    if (g.a()) {
      return 64;
    }
    return 0;
  }
  
  @CalledByNative
  public boolean shouldRestoreCurrentPage()
  {
    if (this.mEnableAutoPageRestoration) {
      return true;
    }
    Object localObject = this.mWebViewClientExtension;
    if (localObject == null) {
      return false;
    }
    try
    {
      localObject = localObject.getClass().getMethod("shouldRestoreCurrentPage", new Class[0]);
      ((Method)localObject).setAccessible(true);
      boolean bool = ((Boolean)((Method)localObject).invoke(this.mWebViewClientExtension, new Object[0])).booleanValue();
      return bool;
    }
    catch (Throwable localThrowable) {}
    return false;
  }
  
  public void showImage(final int paramInt1, final int paramInt2)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.showImage(paramInt1, paramInt2);
        }
      });
      return;
    }
    if (this.mNativeWebViewChromiumExtension == 0L) {
      return;
    }
    if (this.mAwContents.getContentViewCore() != null) {
      nativeShowImage(this.mNativeWebViewChromiumExtension, paramInt1, paramInt2 - this.mAwContents.getContentViewCore().getRenderCoordinates().getContentOffsetYPix());
    }
  }
  
  public void showInputMethodExtBar(boolean paramBoolean, int paramInt)
  {
    if (this.mAwContents.getTencentContentViewCore() != null) {
      this.mAwContents.getTencentContentViewCore().showInputMethodExtBar(paramBoolean, paramInt);
    }
  }
  
  public void showInsertionMode(Rect paramRect, boolean paramBoolean)
  {
    try
    {
      this.mSelectionController.a(paramRect, paramBoolean);
      return;
    }
    catch (Exception paramRect)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("showInsertionMode exception: ");
      ((StringBuilder)localObject).append(paramRect);
      Log.e("WebViewChromiumExtension", ((StringBuilder)localObject).toString());
      localObject = SmttServiceProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(paramRect);
      ((SmttServiceProxy)localObject).reportTbsError(3004, localStringBuilder.toString());
    }
  }
  
  public void showSelectionMode(Rect paramRect1, Rect paramRect2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.mSelectionController.a(paramRect1, paramRect2, paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public void showTranslateBubble(int paramInt1, String paramString1, String paramString2, int paramInt2)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = this.mWebViewClientExtension;
    if (localIX5WebViewClientExtension == null) {
      return;
    }
    localIX5WebViewClientExtension.showTranslateBubble(paramInt1, paramString1, paramString2, paramInt2);
    if ((paramInt1 == -2) && (paramInt2 == -101)) {
      this.mTranslateHelper.a().j();
    }
  }
  
  public Drawable snapshot(int paramInt, boolean paramBoolean)
  {
    return null;
  }
  
  public void snapshotVisible(final Canvas paramCanvas, final boolean paramBoolean1, final boolean paramBoolean2, final boolean paramBoolean3)
  {
    if (checkNeedsPost())
    {
      runVoidTaskOnUiThreadBlockingIgnoreException(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.snapshotVisible(paramCanvas, paramBoolean1, paramBoolean2, paramBoolean3);
        }
      });
      return;
    }
    int i = paramCanvas.save();
    int j = this.mWebView.getRealWebView().getScrollX();
    int k = this.mWebView.getRealWebView().getScrollY();
    if (paramBoolean2) {
      paramCanvas.translate(-j, -k);
    } else {
      paramCanvas.translate(-j, -k - this.mWebViewChromium.getVisibleTitleHeight());
    }
    Bitmap localBitmap = SMTTAdaptation.getBitmap(paramCanvas);
    if (localBitmap != null) {
      snapshotVisibleWithBitmap(localBitmap, paramBoolean1, paramBoolean2, paramBoolean3, 1.0F, 1.0F);
    } else {
      this.mAwContents.onDraw(paramCanvas);
    }
    paramCanvas.restoreToCount(i);
  }
  
  public void snapshotVisibleWithBitmap(final Bitmap paramBitmap, final boolean paramBoolean1, final boolean paramBoolean2, final boolean paramBoolean3, final float paramFloat1, final float paramFloat2)
  {
    if (checkNeedsPost())
    {
      runVoidTaskOnUiThreadBlockingIgnoreException(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.snapshotVisibleWithBitmap(paramBitmap, paramBoolean1, paramBoolean2, paramBoolean3, paramFloat1, paramFloat2);
        }
      });
      return;
    }
    takeCurrentScreenshot(paramBitmap, paramFloat1, paramFloat2, null, false);
  }
  
  public void snapshotVisibleWithBitmap(final Bitmap paramBitmap, final boolean paramBoolean1, final boolean paramBoolean2, final boolean paramBoolean3, final float paramFloat1, final float paramFloat2, final Runnable paramRunnable)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.snapshotVisibleWithBitmap(paramBitmap, paramBoolean1, paramBoolean2, paramBoolean3, paramFloat1, paramFloat2, paramRunnable);
        }
      });
      return;
    }
    takeCurrentScreenshot(paramBitmap, paramFloat1, paramFloat2, paramRunnable, false);
  }
  
  public void snapshotVisibleWithBitmapThreaded(final Bitmap paramBitmap, final boolean paramBoolean1, final boolean paramBoolean2, final boolean paramBoolean3, final float paramFloat1, final float paramFloat2, final int paramInt)
  {
    if (checkNeedsPost())
    {
      runVoidTaskOnUiThreadBlockingIgnoreException(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.snapshotVisibleWithBitmapThreaded(paramBitmap, paramBoolean1, paramBoolean2, paramBoolean3, paramFloat1, paramFloat2, paramInt);
        }
      });
      return;
    }
    takeCurrentScreenshotThreaded(paramBitmap, paramFloat1, paramFloat2, paramInt, false);
  }
  
  public void snapshotWholePage(final Canvas paramCanvas, final boolean paramBoolean)
  {
    if (checkNeedsPost())
    {
      this.mFactory.runVoidTaskOnUiThreadBlocking(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.snapshotWholePage(paramCanvas, paramBoolean);
        }
      });
      return;
    }
    int i = paramCanvas.save();
    paramCanvas.scale(1.0F / this.mWebViewChromium.getScale(), 1.0F / this.mWebViewChromium.getScale());
    if (paramBoolean) {
      paramCanvas.translate(0.0F, this.mWebView.getTitleHeight() - this.mWebViewChromium.getVisibleTitleHeight());
    }
    float[] arrayOfFloat = new float[9];
    Matrix localMatrix = new Matrix();
    paramCanvas.getMatrix(localMatrix);
    localMatrix.getValues(arrayOfFloat);
    float f = arrayOfFloat[0];
    takeCurrentScreenshot(SMTTAdaptation.getBitmap(paramCanvas), f, f, null, true);
    paramCanvas.restoreToCount(i);
  }
  
  public void snapshotWholePage(final Canvas paramCanvas, final boolean paramBoolean, final Runnable paramRunnable)
  {
    if (checkNeedsPost())
    {
      this.mFactory.runVoidTaskOnUiThreadBlocking(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.snapshotWholePage(paramCanvas, paramBoolean, paramRunnable);
        }
      });
      return;
    }
    int i = paramCanvas.save();
    paramCanvas.scale(1.0F / this.mWebViewChromium.getScale(), 1.0F / this.mWebViewChromium.getScale());
    if (paramBoolean) {
      paramCanvas.translate(0.0F, this.mWebView.getTitleHeight() - this.mWebViewChromium.getVisibleTitleHeight());
    }
    float[] arrayOfFloat = new float[9];
    Matrix localMatrix = new Matrix();
    paramCanvas.getMatrix(localMatrix);
    localMatrix.getValues(arrayOfFloat);
    float f = arrayOfFloat[0];
    takeCurrentScreenshot(SMTTAdaptation.getBitmap(paramCanvas), f, f, paramRunnable, true);
    paramCanvas.restoreToCount(i);
  }
  
  public void startMainThreadBlockedDetect()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.startMainThreadBlockedDetect();
        }
      });
      return;
    }
    if (this.ENABLE_DEAD_CODE_DETECTION_IN_APP)
    {
      if (MAIN_THREAD_ALREADY_BLOCKED) {
        return;
      }
      if (!this.mHandler.hasMessages(1)) {
        this.mHandler.sendEmptyMessageDelayed(1, 5000L);
      }
      return;
    }
  }
  
  public void stopMainThreadBlockedDetect()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromiumExtension.this.stopMainThreadBlockedDetect();
        }
      });
      return;
    }
    this.mHandler.removeMessages(2);
    this.mHandler.removeMessages(1);
  }
  
  public void stopPreLoad(String paramString)
  {
    if (paramString != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
      Object localObject = preLoadHistory;
      if (localObject != null)
      {
        if (!((HashSet)localObject).contains(paramString)) {
          return;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("WebViewChromiumExtension::stopPreload url=");
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append("  preLoadHistory=");
        ((StringBuilder)localObject).append(preLoadHistory.toString());
        MttLog.d("preload_stop", ((StringBuilder)localObject).toString());
        long l = this.mNativeWebViewChromiumExtension;
        if (l == 0L) {
          return;
        }
        nativeStopPreLoad(l, paramString);
        return;
      }
      return;
    }
  }
  
  public void suspendPageScroll(boolean paramBoolean)
  {
    this.mAwContents.suspendPageScroll(paramBoolean);
  }
  
  public void takeCurrentScreenshot(Bitmap paramBitmap, float paramFloat1, float paramFloat2, Runnable paramRunnable, boolean paramBoolean)
  {
    if (this.mNativeWebViewChromiumExtension != 0L)
    {
      if (paramBitmap == null) {
        return;
      }
      this.mScreenshot = new WeakReference(paramBitmap);
      this.mCallback = paramRunnable;
      long l = this.mNativeWebViewChromiumExtension;
      int i = paramBitmap.getWidth();
      int j = paramBitmap.getHeight();
      boolean bool;
      if (paramRunnable == null) {
        bool = true;
      } else {
        bool = false;
      }
      nativeTakeCurrentScreenshot(l, i, j, paramFloat1, paramFloat2, bool, paramBoolean);
      return;
    }
  }
  
  public void takeCurrentScreenshotThreaded(Bitmap paramBitmap, float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean)
  {
    if (this.mNativeWebViewChromiumExtension != 0L)
    {
      if (paramBitmap == null) {
        return;
      }
      this.mScreenshot = new WeakReference(paramBitmap);
      nativeTakeCurrentScreenshotThreaded(this.mNativeWebViewChromiumExtension, paramBitmap.getWidth(), paramBitmap.getHeight(), paramFloat1, paramFloat2, paramInt, paramBoolean);
      return;
    }
  }
  
  public void translateWord(String paramString)
  {
    r localr = this.mTranslateHelper;
    if (localr == null) {
      return;
    }
    localr.a(this.mWebView, paramString);
  }
  
  public void trimMemory(int paramInt) {}
  
  public void unselect()
  {
    cancelFingerSearch(false);
  }
  
  public void updateForceScaleStatus(float paramFloat1, float paramFloat2)
  {
    if (!this.mSettingsExtension.getForcePinchScaleEnabled()) {
      return;
    }
    if (this.mHandler.hasMessages(7)) {
      return;
    }
    String str = UrlUtils.getHost(this.mWebView.getRealWebView().getUrl());
    boolean bool = TencentWebViewDatabaseAdapter.getInstance(this.mWebView.getContext()).getForcePinchScale(str);
    if ((paramFloat1 >= paramFloat2) && (bool))
    {
      this.mSettingsExtension.setForcePinchScale(true, str);
      return;
    }
    if (!bool) {
      this.mSettingsExtension.setForcePinchScale(false, str);
    }
  }
  
  @CalledByNative
  public void updateFrameInfo(int paramInt1, int paramInt2)
  {
    if (this.mWebChromeClientExtension != null)
    {
      if (paramInt1 == 0) {
        return;
      }
      if ((paramInt1 & 0x4) != 0) {
        paramInt1 = 1;
      } else {
        paramInt1 = 0;
      }
      if (paramInt1 != 0)
      {
        Object localObject = this.mNavigateUrl;
        if ((localObject != null) && (((String)localObject).equals(nativeGetUrlByPageID(this.mNativeWebViewChromiumExtension, paramInt2))))
        {
          this.mContentsClientAdapter.onProgressChanged(100);
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Options=PROGRESS; SCREENFUL=PROGRESS_100; URL=");
          ((StringBuilder)localObject).append(this.mNavigateUrl);
          LiveLog.d("LOADER", ((StringBuilder)localObject).toString());
        }
      }
      return;
    }
  }
  
  public void updateHitTestEditable(String paramString)
  {
    this.mAwContents.updateHitTestEditable(paramString);
  }
  
  public void updateImageList(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.mIsImageListUpdated = false;
    this.mImageList.clear();
    this.mUpdataImageCallback = null;
    this.mHasCallbackTask = false;
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeUpdateImageList(l, paramInt1, paramInt2, paramBoolean);
  }
  
  public void updateImageList2(int paramInt1, int paramInt2, boolean paramBoolean, android.webkit.ValueCallback<Integer> paramValueCallback)
  {
    updateImageList(paramInt1, paramInt2, paramBoolean);
    this.mUpdataImageCallback = paramValueCallback;
  }
  
  public void updateServiceWorkerBackground(String paramString)
  {
    long l = this.mNativeWebViewChromiumExtension;
    if (l == 0L) {
      return;
    }
    nativeServiceWorkerBackgroundUpdate(l, paramString);
  }
  
  public boolean uploadPageErrorMetaInfo(String paramString1, int paramInt, String paramString2, String paramString3, boolean paramBoolean, String paramString4, String paramString5)
  {
    return false;
  }
  
  public void uploadX5CoreLiveLog(String paramString1, String paramString2)
  {
    com.tencent.smtt.livelog.a.a().a(paramString1, paramString2, new g());
  }
  
  class a
    implements JsResult.ResultReceiver
  {
    private final JsPromptResult jdField_a_of_type_ComTencentTbsCoreWebkitJsPromptResult = new JsPromptResult(this);
    private JsPromptResultReceiver jdField_a_of_type_OrgChromiumAndroid_webviewJsPromptResultReceiver;
    private JsResultReceiver jdField_a_of_type_OrgChromiumAndroid_webviewJsResultReceiver;
    
    public a(JsResultReceiver paramJsResultReceiver)
    {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewJsResultReceiver = paramJsResultReceiver;
    }
    
    public JsPromptResult a()
    {
      return this.jdField_a_of_type_ComTencentTbsCoreWebkitJsPromptResult;
    }
    
    public void onJsResultComplete(com.tencent.tbs.core.webkit.JsResult paramJsResult)
    {
      if (this.jdField_a_of_type_OrgChromiumAndroid_webviewJsPromptResultReceiver != null)
      {
        if (this.jdField_a_of_type_ComTencentTbsCoreWebkitJsPromptResult.getResult())
        {
          this.jdField_a_of_type_OrgChromiumAndroid_webviewJsPromptResultReceiver.confirm(this.jdField_a_of_type_ComTencentTbsCoreWebkitJsPromptResult.getStringResult());
          return;
        }
        this.jdField_a_of_type_OrgChromiumAndroid_webviewJsPromptResultReceiver.cancel();
        return;
      }
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitJsPromptResult.getResult())
      {
        this.jdField_a_of_type_OrgChromiumAndroid_webviewJsResultReceiver.confirm();
        return;
      }
      this.jdField_a_of_type_OrgChromiumAndroid_webviewJsResultReceiver.cancel();
    }
  }
  
  class b
    implements JsResult.ResultReceiver
  {
    private final JsPromptResult jdField_a_of_type_ComTencentTbsCoreWebkitJsPromptResult = new JsPromptResult(this);
    private JsPromptResultReceiver jdField_a_of_type_OrgChromiumAndroid_webviewJsPromptResultReceiver;
    private JsResultReceiver jdField_a_of_type_OrgChromiumAndroid_webviewJsResultReceiver;
    
    public b(JsResultReceiver paramJsResultReceiver)
    {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewJsResultReceiver = paramJsResultReceiver;
    }
    
    public JsPromptResult a()
    {
      return this.jdField_a_of_type_ComTencentTbsCoreWebkitJsPromptResult;
    }
    
    public void onJsResultComplete(com.tencent.tbs.core.webkit.JsResult paramJsResult)
    {
      if (this.jdField_a_of_type_OrgChromiumAndroid_webviewJsPromptResultReceiver != null)
      {
        if (this.jdField_a_of_type_ComTencentTbsCoreWebkitJsPromptResult.getResult())
        {
          this.jdField_a_of_type_OrgChromiumAndroid_webviewJsPromptResultReceiver.confirm(this.jdField_a_of_type_ComTencentTbsCoreWebkitJsPromptResult.getStringResult());
          return;
        }
        this.jdField_a_of_type_OrgChromiumAndroid_webviewJsPromptResultReceiver.cancel();
        return;
      }
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitJsPromptResult.getResult())
      {
        this.jdField_a_of_type_OrgChromiumAndroid_webviewJsResultReceiver.confirm();
        return;
      }
      this.jdField_a_of_type_OrgChromiumAndroid_webviewJsResultReceiver.cancel();
    }
  }
  
  private static class c
    implements Runnable
  {
    private Bitmap a;
    
    private c(Bitmap paramBitmap)
    {
      this.a = paramBitmap;
    }
    
    public void run()
    {
      Bitmap localBitmap = this.a;
      if (localBitmap != null)
      {
        localBitmap.recycle();
        this.a = null;
      }
    }
  }
  
  private static class d
  {
    final long jdField_a_of_type_Long;
    final String jdField_a_of_type_JavaLangString;
    boolean jdField_a_of_type_Boolean = false;
    final byte[] jdField_a_of_type_ArrayOfByte;
    final String b;
    final String c;
    final String d;
    final String e;
    final String f;
    final String g;
    final String h;
    String i;
    
    d(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte)
    {
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.b = paramString2;
      this.c = paramString3;
      this.d = paramString4;
      this.jdField_a_of_type_Long = paramLong;
      this.e = paramString5;
      this.f = paramString6;
      this.g = paramString7;
      this.h = paramString8;
      this.jdField_a_of_type_ArrayOfByte = paramArrayOfByte;
    }
  }
  
  private class e
  {
    private Object jdField_a_of_type_JavaLangObject;
    private String jdField_a_of_type_JavaLangString;
    private boolean jdField_a_of_type_Boolean;
    
    public e(Object paramObject)
    {
      this.jdField_a_of_type_JavaLangObject = paramObject;
      this.jdField_a_of_type_Boolean = false;
    }
    
    public void a()
    {
      a("");
    }
    
    public void a(String paramString)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    public void a(String paramString1, int paramInt1, String paramString2, int paramInt2, int[] paramArrayOfInt, String paramString3, String paramString4)
    {
      if (this.jdField_a_of_type_JavaLangObject == null) {
        return;
      }
      this.jdField_a_of_type_Boolean = false;
      SmttServiceProxy.getInstance().fingerSearchRequest(this.jdField_a_of_type_JavaLangObject, paramString1, paramInt1, paramString2, paramInt2, paramArrayOfInt, "", "");
    }
    
    public void a(boolean paramBoolean)
    {
      if ((!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) && (this.jdField_a_of_type_Boolean))
      {
        this.jdField_a_of_type_Boolean = false;
        SmttServiceProxy.getInstance().cancelFingerSearch(this.jdField_a_of_type_JavaLangObject, WebViewChromiumExtension.this.getSelection(), paramBoolean);
      }
    }
    
    public void b()
    {
      if (!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString))
      {
        this.jdField_a_of_type_Boolean = true;
        SmttServiceProxy.getInstance().enterFingerSearchMode(this.jdField_a_of_type_JavaLangObject);
      }
    }
    
    public void b(String paramString)
    {
      if ((!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) && (!TextUtils.isEmpty(paramString)))
      {
        if (this.jdField_a_of_type_JavaLangString.equals(paramString))
        {
          d();
          return;
        }
        SmttServiceProxy.getInstance().reportUserFinallySelectText(this.jdField_a_of_type_JavaLangObject, paramString);
      }
    }
    
    public void c()
    {
      if (!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString))
      {
        this.jdField_a_of_type_Boolean = false;
        SmttServiceProxy.getInstance().enterOtherMode(this.jdField_a_of_type_JavaLangObject);
      }
    }
    
    public void d()
    {
      if ((!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) && (this.jdField_a_of_type_Boolean))
      {
        this.jdField_a_of_type_Boolean = false;
        SmttServiceProxy.getInstance().executeDiectConsumptionItems(this.jdField_a_of_type_JavaLangObject, this.jdField_a_of_type_JavaLangString);
      }
    }
    
    public void e()
    {
      if ((!TextUtils.isEmpty(WebViewChromiumExtension.this.getSelection())) && (this.jdField_a_of_type_Boolean)) {
        SmttServiceProxy.getInstance().executeCopyItem(this.jdField_a_of_type_JavaLangObject, WebViewChromiumExtension.this.getSelection());
      }
    }
    
    public void f()
    {
      if ((!TextUtils.isEmpty(WebViewChromiumExtension.this.getSelection())) && (this.jdField_a_of_type_Boolean)) {
        SmttServiceProxy.getInstance().executeSearchItem(this.jdField_a_of_type_JavaLangObject, WebViewChromiumExtension.this.getSelection());
      }
    }
    
    public void g()
    {
      if ((!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) && (this.jdField_a_of_type_Boolean))
      {
        this.jdField_a_of_type_Boolean = false;
        SmttServiceProxy.getInstance().executeMoreItem(this.jdField_a_of_type_JavaLangObject, this.jdField_a_of_type_JavaLangString);
      }
    }
    
    public void h()
    {
      if ((!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) && (this.jdField_a_of_type_Boolean))
      {
        this.jdField_a_of_type_Boolean = false;
        SmttServiceProxy.getInstance().chooseTranslation(this.jdField_a_of_type_JavaLangObject, this.jdField_a_of_type_JavaLangString);
      }
    }
    
    public void i()
    {
      if ((!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) && (this.jdField_a_of_type_Boolean))
      {
        this.jdField_a_of_type_Boolean = false;
        SmttServiceProxy.getInstance().chooseFavorites(this.jdField_a_of_type_JavaLangObject, this.jdField_a_of_type_JavaLangString);
      }
    }
    
    public void j()
    {
      if (!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString))
      {
        this.jdField_a_of_type_Boolean = false;
        SmttServiceProxy.getInstance().exitX5LongClickPopMenu(this.jdField_a_of_type_JavaLangObject);
      }
    }
  }
  
  private static class f
  {
    final int jdField_a_of_type_Int;
    final String jdField_a_of_type_JavaLangString;
    
    f(String paramString, int paramInt)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      this.jdField_a_of_type_Int = paramInt;
    }
  }
  
  class g
    implements X5LogUploadManager.OnUploadListener
  {
    g() {}
    
    public void onUploadFinished(boolean paramBoolean)
    {
      WebViewChromiumExtension.this.onUploadLiveLogFinished(paramBoolean);
    }
  }
  
  private static class h
  {
    final String a;
    final String b;
    
    h(String paramString1, String paramString2)
    {
      this.a = paramString1;
      this.b = paramString2;
    }
  }
  
  private class i
  {
    private Object jdField_a_of_type_JavaLangObject;
    
    public i(Object paramObject)
    {
      this.jdField_a_of_type_JavaLangObject = paramObject;
    }
    
    public void a()
    {
      if (this.jdField_a_of_type_JavaLangObject == null) {
        return;
      }
      SmttServiceProxy.getInstance().reportDoLongClick(this.jdField_a_of_type_JavaLangObject);
    }
    
    public void b()
    {
      if (this.jdField_a_of_type_JavaLangObject == null) {
        return;
      }
      SmttServiceProxy.getInstance().reportDoFingerSearch(this.jdField_a_of_type_JavaLangObject);
    }
    
    public void c()
    {
      if (this.jdField_a_of_type_JavaLangObject == null) {
        return;
      }
      SmttServiceProxy.getInstance().reportShowLongClickPopupMenu(this.jdField_a_of_type_JavaLangObject);
    }
  }
  
  private static class j
  {
    final int jdField_a_of_type_Int;
    final String jdField_a_of_type_JavaLangString;
    final int jdField_b_of_type_Int;
    final String jdField_b_of_type_JavaLangString;
    final int jdField_c_of_type_Int;
    final String jdField_c_of_type_JavaLangString;
    final int jdField_d_of_type_Int;
    final String jdField_d_of_type_JavaLangString;
    final int jdField_e_of_type_Int;
    String jdField_e_of_type_JavaLangString;
    final int f;
    int g;
    int h;
    int i;
    
    j(int paramInt1, String paramString1, int paramInt2, int paramInt3, int paramInt4, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt5, int paramInt6)
    {
      this.jdField_a_of_type_Int = paramInt1;
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.jdField_b_of_type_Int = paramInt2;
      this.jdField_c_of_type_Int = paramInt3;
      this.jdField_d_of_type_Int = paramInt4;
      this.jdField_b_of_type_JavaLangString = paramString2;
      this.jdField_c_of_type_JavaLangString = paramString3;
      this.jdField_d_of_type_JavaLangString = paramString4;
      this.jdField_e_of_type_JavaLangString = paramString5;
      this.jdField_e_of_type_Int = paramInt5;
      this.f = paramInt6;
      this.g = 0;
      this.h = 0;
      this.i = 0;
    }
  }
  
  private class k
    extends WebContentsObserver
  {
    k(WebContents paramWebContents)
    {
      super();
    }
    
    public void didFinishNavigation(String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, @Nullable Integer paramInteger, int paramInt1, String paramString2, int paramInt2)
    {
      if ((paramBoolean3) && (paramBoolean1) && (!paramBoolean4) && (!paramBoolean2)) {
        WebViewChromiumExtension.this.mContentsClientAdapter.restoreScale(paramString1);
      }
      if ((paramString1 != null) && (!paramString1.startsWith("about:blank")) && (WebViewChromiumExtension.this.mAwContents.getWebContents() != null)) {
        WebViewChromiumExtension.this.mAwContents.getWebContentsAccessibility().startAccessibilityIfNecessary();
      }
    }
    
    public void didStartNavigation(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      if ((paramBoolean1) && ((URLUtil.isDataUrl(paramString)) || (URLUtil.isNetworkUrl(paramString))) && (WebViewChromiumExtension.this.mDisplayCutoutController != null)) {
        WebViewChromiumExtension.this.mDisplayCutoutController.a(paramString);
      }
    }
    
    public void viewportFitChanged(int paramInt)
    {
      if (WebViewChromiumExtension.this.mDisplayCutoutController != null) {
        WebViewChromiumExtension.this.mDisplayCutoutController.a(paramInt);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\WebViewChromiumExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */