package android.webview.chromium.tencent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webview.chromium.WebViewContentsClientAdapter;
import android.webview.chromium.WebViewDelegateFactory.WebViewDelegate;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.MttLoader;
import com.tencent.common.utils.MttLoader.BrowserInfo;
import com.tencent.common.utils.UrlUtils;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebChromeClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebBackForwardList;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.WebViewTransport;
import com.tencent.smtt.export.internal.interfaces.DownloadListenerExtension;
import com.tencent.smtt.export.internal.interfaces.IX5QQBrowserClient;
import com.tencent.smtt.net.PersistentSessionManager;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.util.f;
import com.tencent.smtt.util.j;
import com.tencent.smtt.util.m;
import com.tencent.smtt.webkit.AdInfoManager;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.ScaleManager;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.WebViewProviderExtension;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.h;
import com.tencent.smtt.webkit.h5video.b;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.smtt.webkit.ui.e.a;
import com.tencent.tbs.core.partner.filechooser.FileChooserFirstScreenView;
import com.tencent.tbs.core.partner.filechooser.FirstScreenViewBackGround;
import com.tencent.tbs.core.webkit.DownloadListener;
import com.tencent.tbs.core.webkit.JsPromptResult;
import com.tencent.tbs.core.webkit.JsResult.ResultReceiver;
import com.tencent.tbs.core.webkit.SslErrorHandler;
import com.tencent.tbs.core.webkit.WebChromeClient;
import com.tencent.tbs.core.webkit.WebResourceError;
import com.tencent.tbs.core.webkit.WebSettings;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.WebView.WebViewTransport;
import com.tencent.tbs.core.webkit.WebViewClient;
import com.tencent.tbs.core.webkit.adapter.FileChooserParamsAdapter;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentDownloadListener;
import com.tencent.tbs.core.webkit.tencent.TencentJsDialogHelper;
import com.tencent.tbs.core.webkit.tencent.TencentNewJsDialogHelper;
import com.tencent.tbs.core.webkit.tencent.TencentURLUtil;
import com.tencent.tbs.core.webkit.tencent.TencentWebChromeClient;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewClient;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProvider;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.chromium.android_webview.AwContents;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwContentsClient.CustomViewCallback;
import org.chromium.android_webview.AwContentsClient.FileChooserParamsImpl;
import org.chromium.android_webview.AwContentsClientBridge.ClientCertificateRequestCallback;
import org.chromium.android_webview.AwGeolocationPermissions.Callback;
import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.android_webview.AwWebResourceResponse;
import org.chromium.base.Callback;
import org.chromium.base.TraceEvent;
import org.chromium.tencent.AwContentsClient.AwWebRequestInfo;
import org.chromium.tencent.ITencentAwContentsClient;
import org.chromium.tencent.TencentTraceEvent;
import org.json.JSONObject;

public class TencentWebViewContentsClientAdapter
  extends WebViewContentsClientAdapter
  implements ITencentAwContentsClient
{
  private static final int ACTIVE_QB_BACK_DELAY = 5000;
  private static final int ACTIVE_QB_HEADSUP_DELAY = 60000;
  private static final String SHARE_PREFERENCES_NAME = "qqth";
  private static final String SHARE_PREFERENCES_NAME_ACTIVEQB_BACK = "qqtaqb";
  public static final int WebNavigationTypeBackForward = 2;
  public static final int WebNavigationTypeFormResubmitted = 4;
  public static final int WebNavigationTypeFormSubmitted = 1;
  public static final int WebNavigationTypeLinkClicked = 0;
  public static final int WebNavigationTypeNONE = -1;
  public static final int WebNavigationTypeOther = 5;
  public static final int WebNavigationTypeReload = 3;
  private static final int kFileChooserModeOpenMultiple = 1;
  private static boolean mActiveQBBackDone = false;
  private static long mLastGetQBHeadsupTime = 0L;
  private static boolean mNewShouldInterceptRequestVersion = true;
  private static boolean mNewShouldInterceptRequestVersion_V2 = true;
  private static boolean mOnReceivedErrorMethodExisted = true;
  private static boolean mPrepareActiveQBHeadsupIfNeededDone = false;
  private static boolean mShouldCallonReceivedClientCertRequest = true;
  private static boolean mShouldCallonReceivedHttpError = true;
  private static boolean mShouldCallonReportMainResourceResponse = true;
  private static boolean mShouldCallonReportRequestAllInfo = true;
  private static boolean mShouldUseWebResourceResponseNewInterface = true;
  private static RandomAccessFile raf;
  private static float sDensity = -1.0F;
  private static boolean shouldShowSllErrorDialog = true;
  private static FileLock tbsFileLockFileLock;
  private final long DEFAULT_RETRY_INTERVAL_SEC = 86400L;
  private String KEY_HEADSUP_BTNTEXT = "btn_text";
  private String KEY_HEADSUP_BTNURL = "btn_url";
  private String KEY_HEADSUP_CONTENT = "content";
  private String KEY_HEADSUP_END = "end";
  private String KEY_HEADSUP_ICON = "icon";
  private String KEY_HEADSUP_ID = "id";
  private String KEY_HEADSUP_SHOWED = "headsup_showed";
  private String KEY_HEADSUP_START = "start";
  private String KEY_HEADSUP_STAT_URL_CLICK = "stat_url_click";
  private String KEY_HEADSUP_STAT_URL_SHOW = "stat_url_show";
  private String KEY_HEADSUP_TITLE = "title";
  private String KEY_HEADSUP_URL = "url";
  private String KEY_LAST_ACTIVE = "last_active";
  private String KEY_LAST_GET_TIME = "last_get_time";
  private String KEY_TIMES = "times";
  boolean ismHasEverOpendDebugtbs = false;
  private int mAbnormalRecoverPreState = 65535;
  private activeQBReceiver mActiveQBReceiver = null;
  boolean mCanOpenDebugtbs = false;
  private boolean mCheckQBActiveTimeCostAfterTBSDo = false;
  private boolean mCompatibleOnPageStartedOrFinishedMethod = false;
  private Runnable mDelayActiveQBBackRunnable = new Runnable()
  {
    public void run()
    {
      if (TencentWebViewContentsClientAdapter.this.mIsQBActived)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHQIAWC");
        return;
      }
      Object localObject = TencentWebViewContentsClientAdapter.this;
      if (((TencentWebViewContentsClientAdapter)localObject).isAudioActive(((TencentWebViewContentsClientAdapter)localObject).mWebView.getContext()))
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHAIAWC");
        return;
      }
      SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHPTAQBH");
      try
      {
        localObject = new Intent();
        ((Intent)localObject).setClassName("com.tencent.mtt", "com.tencent.mtt.ServiceDispatchActivity");
        ((Intent)localObject).putExtra("service", 2);
        ((Intent)localObject).putExtra("packageName", TencentWebViewContentsClientAdapter.this.mWebView.getContext().getPackageName());
        ((Intent)localObject).putExtra("versionName", "");
        Intent localIntent = new Intent();
        localIntent.setAction("com.tencent.mtt.ServiceDispatch.feedback");
        ((Intent)localObject).putExtra("feedback", localIntent);
        ((Intent)localObject).setFlags(268435456);
        ((Intent)localObject).putExtras(SmttServiceProxy.getInstance().getGuidBundle());
        TencentWebViewContentsClientAdapter.access$202(TencentWebViewContentsClientAdapter.this, true);
        TencentWebViewContentsClientAdapter.access$402(TencentWebViewContentsClientAdapter.this, System.currentTimeMillis());
        TencentWebViewContentsClientAdapter.this.mWebView.getContext().startActivity((Intent)localObject);
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHAQBH");
        TencentWebViewContentsClientAdapter.this.mHandlerActiveQBBack.postDelayed(TencentWebViewContentsClientAdapter.this.mDelayCheckQBActiveAfterBackHeartBeat, 10000L);
        TencentWebViewContentsClientAdapter.access$1508(TencentWebViewContentsClientAdapter.this);
        if (TencentWebViewContentsClientAdapter.this.mPreferenceActiveQBBack == null) {
          TencentWebViewContentsClientAdapter.access$1602(TencentWebViewContentsClientAdapter.this, ContextHolder.getInstance().getApplicationContext().getSharedPreferences("qqtaqb", 0));
        }
        if (TencentWebViewContentsClientAdapter.this.mPreferenceActiveQBBack == null) {
          break label315;
        }
        localObject = TencentWebViewContentsClientAdapter.this.mPreferenceActiveQBBack.edit();
        if (localObject == null) {
          break label315;
        }
        ((SharedPreferences.Editor)localObject).putLong(TencentWebViewContentsClientAdapter.this.KEY_TIMES, TencentWebViewContentsClientAdapter.this.mTimesActiveQBBackByTBSLocal);
        ((SharedPreferences.Editor)localObject).commit();
        return;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
      SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHEXP2");
      label315:
    }
  };
  private Runnable mDelayActiveQBHeadsupTask = new Runnable()
  {
    public void run()
    {
      if (TencentWebViewContentsClientAdapter.this.mIsQBActived)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSQIAWC");
        return;
      }
      TencentWebViewContentsClientAdapter localTencentWebViewContentsClientAdapter = TencentWebViewContentsClientAdapter.this;
      if (localTencentWebViewContentsClientAdapter.isAudioActive(localTencentWebViewContentsClientAdapter.mWebView.getContext()))
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAIAWC");
        return;
      }
      localTencentWebViewContentsClientAdapter = TencentWebViewContentsClientAdapter.this;
      localTencentWebViewContentsClientAdapter.activeQBHeadsup(localTencentWebViewContentsClientAdapter.mWebView.getContext());
    }
  };
  private Runnable mDelayCheckQBActiveAfterBackHeartBeat = new Runnable()
  {
    public void run()
    {
      if (TencentWebViewContentsClientAdapter.this.mIsQBActived)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHQIAATD");
        return;
      }
      SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHQINAATD");
    }
  };
  private Runnable mDelayCheckQBActivedTask = new Runnable()
  {
    public void run()
    {
      if (TencentWebViewContentsClientAdapter.this.mIsQBActived)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQHS");
        return;
      }
      SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQHF");
    }
  };
  private boolean mDoingShouldOverrideUrlLoading = false;
  private DownloadListenerExtension mDownloadListenerExtension;
  private FileChooserFirstScreenView mFileChooserFirstScreenView = null;
  private FirstScreenViewBackGround mFirstScreenViewBackGround = null;
  private boolean mFromX5FileChooserUI;
  private Handler mHandler;
  private Handler mHandlerActiveQBBack;
  boolean mHasEverOpendDebugtbs = false;
  private Map<String, String>[] mHeadsup = null;
  private boolean mIsQBActived = false;
  private SharedPreferences mPreferenceActiveQBBack = null;
  private SharedPreferences mPreferenceHeadsup = null;
  private Set<String> mRecoverUrls = new HashSet();
  private int mRequestedOrientation = -10;
  private ArrayList<String> mShouldOverrideUrlLoadingList = new ArrayList();
  private ArrayList<String> mShouldOverrideUrlLoadingListForLoadFaild = new ArrayList();
  private long mTimeActiveQBBack = 0L;
  private long mTimeQBActiveFeedback = 0L;
  private long mTimesActiveQBBackByTBSLocal = 0L;
  private boolean mUseFileChooserByTBS = false;
  private TencentWebViewProxy mWebViewProxy;
  
  static
  {
    sNullWebViewClient = new TencentWebViewClient();
    mPrepareActiveQBHeadsupIfNeededDone = false;
    mActiveQBBackDone = false;
    raf = null;
    tbsFileLockFileLock = null;
  }
  
  public TencentWebViewContentsClientAdapter(TencentWebViewProxy paramTencentWebViewProxy, Context paramContext, WebViewDelegateFactory.WebViewDelegate paramWebViewDelegate)
  {
    super(paramTencentWebViewProxy.getRealWebView(), paramContext, paramWebViewDelegate);
    this.mWebViewProxy = paramTencentWebViewProxy;
    this.mUiThreadHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what == 100)
        {
          paramAnonymousMessage = (TencentWebViewProxy)((IX5WebViewBase.WebViewTransport)paramAnonymousMessage.obj).getWebView();
          if (paramAnonymousMessage != TencentWebViewContentsClientAdapter.this.mWebViewProxy)
          {
            if ((paramAnonymousMessage != null) && (paramAnonymousMessage.copyBackForwardList().getSize() != 0)) {
              throw new IllegalArgumentException("New WebView for popup window must not have been previously navigated.");
            }
            TencentWebViewChromium.completeWindowCreation(TencentWebViewContentsClientAdapter.this.mWebViewProxy, paramAnonymousMessage);
            if ((paramAnonymousMessage != null) && (paramAnonymousMessage.getIsCreatedFullScreenSurfaceView()))
            {
              paramAnonymousMessage.addSurfaceView(-1);
              TencentWebViewContentsClientAdapter.this.mWebViewProxy.setNewOpenedWebView(paramAnonymousMessage);
            }
            return;
          }
          throw new IllegalArgumentException("Parent WebView cannot host it's own popup window. Please use WebSettings.setSupportMultipleWindows(false)");
        }
        throw new IllegalStateException();
      }
    };
  }
  
  private void activeQBHeadsup(Context paramContext)
  {
    if (paramContext == null) {
      return;
    }
    Object localObject2 = null;
    try
    {
      if (this.mActiveQBReceiver == null) {
        this.mActiveQBReceiver = new activeQBReceiver(null);
      }
      paramContext.getApplicationContext().registerReceiver(this.mActiveQBReceiver, new IntentFilter("com.tencent.mtt.ServiceDispatch.feedback"));
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    if (this.mPreferenceHeadsup == null) {
      this.mPreferenceHeadsup = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("qqth", 0);
    }
    Object localObject1 = this.mPreferenceHeadsup;
    if (localObject1 != null)
    {
      long l1 = Long.parseLong(((SharedPreferences)localObject1).getString(this.KEY_HEADSUP_START, "0"));
      long l2 = Long.parseLong(this.mPreferenceHeadsup.getString(this.KEY_HEADSUP_END, "0"));
      Long.parseLong(this.mPreferenceHeadsup.getString(this.KEY_HEADSUP_ID, "0"));
      if ((System.currentTimeMillis() > l1) && (System.currentTimeMillis() < l2))
      {
        l1 = this.mPreferenceHeadsup.getLong(this.KEY_LAST_ACTIVE, 0L);
        boolean bool = this.mPreferenceHeadsup.getBoolean(this.KEY_HEADSUP_SHOWED, false);
        if (bool) {
          SmttServiceProxy.getInstance().userBehaviorStatistics("TBSHHSFQ");
        }
        if ((System.currentTimeMillis() - l1 > 86400000L) && (!bool))
        {
          if (MttLoader.isBrowserInstalled(this.mWebView.getContext()))
          {
            if (MttLoader.getBrowserInfo(this.mWebView.getContext()).ver >= 9200000)
            {
              i = 1;
              break label294;
            }
            SmttServiceProxy.getInstance().userBehaviorStatistics("TBSQVLT92");
            i = 0;
            break label294;
          }
          SmttServiceProxy.getInstance().userBehaviorStatistics("TBSQNI");
        }
      }
      int i = 0;
      label294:
      if (i == 0) {
        return;
      }
    }
    localObject1 = "";
    String str1 = "";
    String str2 = "";
    String str3 = "";
    String str4 = "";
    String str5 = "";
    String str6 = "";
    Object localObject3 = this.mPreferenceHeadsup;
    if (localObject3 != null)
    {
      localObject2 = ((SharedPreferences)localObject3).getString(this.KEY_HEADSUP_ICON, null);
      localObject1 = this.mPreferenceHeadsup.getString(this.KEY_HEADSUP_TITLE, "");
      str1 = this.mPreferenceHeadsup.getString(this.KEY_HEADSUP_CONTENT, "");
      str2 = this.mPreferenceHeadsup.getString(this.KEY_HEADSUP_URL, "");
      str3 = this.mPreferenceHeadsup.getString(this.KEY_HEADSUP_BTNTEXT, "");
      str4 = this.mPreferenceHeadsup.getString(this.KEY_HEADSUP_BTNURL, "");
      str5 = this.mPreferenceHeadsup.getString(this.KEY_HEADSUP_STAT_URL_CLICK, "");
      str6 = this.mPreferenceHeadsup.getString(this.KEY_HEADSUP_STAT_URL_SHOW, "");
    }
    localObject3 = getHeadsupBitmap((String)localObject2);
    localObject2 = new Intent();
    ((Intent)localObject2).putExtra("service", 1);
    ((Intent)localObject2).putExtra("title", (String)localObject1);
    ((Intent)localObject2).putExtra("content", str1);
    ((Intent)localObject2).putExtra("url", str2);
    ((Intent)localObject2).putExtra("btn_text", str3);
    ((Intent)localObject2).putExtra("btn_url", str4);
    ((Intent)localObject2).putExtra("icon", (Parcelable)localObject3);
    ((Intent)localObject2).putExtra("stat_url_click", str5);
    ((Intent)localObject2).putExtra("stat_url_show", str6);
    localObject1 = new Intent();
    ((Intent)localObject1).setAction("com.tencent.mtt.ServiceDispatch.feedback");
    ((Intent)localObject2).putExtra("feedback", (Parcelable)localObject1);
    ((Intent)localObject2).setClassName("com.tencent.mtt", "com.tencent.mtt.ServiceDispatchActivity");
    ((Intent)localObject2).addFlags(268435456);
    paramContext.startActivity((Intent)localObject2);
    SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBH");
    paramContext = this.mPreferenceHeadsup;
    if (paramContext != null)
    {
      paramContext = paramContext.edit();
      if (paramContext != null)
      {
        paramContext.putLong(this.KEY_LAST_ACTIVE, System.currentTimeMillis());
        paramContext.putBoolean(this.KEY_HEADSUP_SHOWED, true);
        paramContext.commit();
      }
    }
    this.mIsQBActived = false;
    this.mHandler.postDelayed(this.mDelayCheckQBActivedTask, 5000L);
  }
  
  private void checkQBActive()
  {
    try
    {
      if (this.mActiveQBReceiver == null) {
        this.mActiveQBReceiver = new activeQBReceiver(null);
      }
      this.mWebView.getContext().getApplicationContext().registerReceiver(this.mActiveQBReceiver, new IntentFilter("com.tencent.mtt.ServiceDispatch.feedback"));
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    this.mCheckQBActiveTimeCostAfterTBSDo = false;
    Intent localIntent1 = new Intent();
    localIntent1.setAction("com.tencent.mtt.ServiceDispatch.broadcast");
    Intent localIntent2 = new Intent();
    localIntent2.setAction("com.tencent.mtt.ServiceDispatch.feedback");
    localIntent1.putExtra("feedback", localIntent2);
    this.mWebView.getContext().sendBroadcast(localIntent1);
  }
  
  private int dip2px(int paramInt)
  {
    if (sDensity < 0.0F)
    {
      WindowManager localWindowManager = (WindowManager)this.mContext.getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
      sDensity = localDisplayMetrics.density;
    }
    return (int)(paramInt * sDensity);
  }
  
  private Activity getActivity()
  {
    if ((this.mContext instanceof Activity)) {
      return (Activity)this.mContext;
    }
    for (Object localObject = ((TencentWebViewProxy.InnerWebView)this.mWebView).getOuterProxy().getRealWebView();; localObject = (View)((View)localObject).getParent())
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
  
  private Bitmap getHeadsupBitmap(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    for (;;)
    {
      try
      {
        localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
        localHttpURLConnection.setConnectTimeout(4000);
        localHttpURLConnection.setReadTimeout(4000);
      }
      catch (Exception paramString)
      {
        HttpURLConnection localHttpURLConnection;
        return null;
      }
      try
      {
        paramString = localHttpURLConnection.getInputStream();
      }
      catch (Exception paramString) {}
    }
    paramString = localHttpURLConnection.getErrorStream();
    paramString = BitmapFactory.decodeStream(paramString);
    return paramString;
  }
  
  private IX5QQBrowserClient getIX5QQBrowserClient()
  {
    TencentWebViewProxy localTencentWebViewProxy = this.mWebViewProxy;
    if ((localTencentWebViewProxy != null) && (localTencentWebViewProxy.getWebViewProvider() != null) && (this.mWebViewProxy.getWebViewProvider().getExtension() != null)) {
      return this.mWebViewProxy.getWebViewProvider().getExtension().getQQBrowserClient();
    }
    return null;
  }
  
  public static File getPermanentTbsFile(Context paramContext, String paramString)
  {
    paramContext = new File(paramContext.getFilesDir(), "tbs");
    if (!paramContext.exists()) {
      paramContext.mkdirs();
    }
    if (!paramContext.canWrite()) {
      return null;
    }
    paramContext = new File(paramContext, paramString);
    if (!paramContext.exists()) {}
    try
    {
      paramContext.createNewFile();
      return paramContext;
    }
    catch (IOException paramContext) {}
    return paramContext;
    return null;
  }
  
  private FileLock getTbsCoreRenameFileLock(Context paramContext)
  {
    paramContext = getPermanentTbsFile(paramContext, "tbslockyzj.txt");
    if (paramContext == null) {
      return null;
    }
    try
    {
      raf = new RandomAccessFile(paramContext.getAbsolutePath(), "rw");
      paramContext = raf.getChannel().tryLock(0L, Long.MAX_VALUE, false);
      return paramContext;
    }
    catch (Throwable paramContext) {}
    return null;
  }
  
  private void handleQProxyRespAndInvokeMethod(DownloadListenerExtension paramDownloadListenerExtension, String paramString)
  {
    Object localObject = new HashMap();
    paramString = paramString.split(";");
    int i = 0;
    while (i < paramString.length)
    {
      str2 = paramString[i];
      j = str2.indexOf("=");
      if (j != -1)
      {
        str1 = str2.substring(0, j);
        if (str1.length() != 0)
        {
          str2 = str2.substring(j + 1, str2.length());
          if (str2.length() != 0) {
            ((Map)localObject).put(str1, str2);
          }
        }
      }
      i += 1;
    }
    paramString = (String)((Map)localObject).get("app");
    if (paramString == null) {
      return;
    }
    String str1 = (String)((Map)localObject).get("vn");
    if ((str1 != null) && (str1.length() > 0)) {}
    try
    {
      i = Integer.parseInt(str1);
    }
    catch (Exception localException2)
    {
      String str3;
      String str4;
      String str5;
      String str6;
      String str7;
      for (;;) {}
    }
    i = 0;
    str1 = (String)((Map)localObject).get("yybApkUrl");
    String str2 = (String)((Map)localObject).get("yybApkSize");
    str3 = (String)((Map)localObject).get("yybApkName");
    str4 = (String)((Map)localObject).get("pkgName");
    str5 = (String)((Map)localObject).get("orgVersion");
    str6 = (String)((Map)localObject).get("newVersion");
    str7 = (String)((Map)localObject).get("apkType");
    if (!TextUtils.isEmpty(str7)) {}
    try
    {
      j = Integer.parseInt(str7);
    }
    catch (Exception localException3)
    {
      String str8;
      String str9;
      for (;;) {}
    }
    int j = 0;
    str7 = (String)((Map)localObject).get("marketPkgName");
    str8 = (String)((Map)localObject).get("backupApkUrl");
    str9 = (String)((Map)localObject).get("STLevel");
    if (!TextUtils.isEmpty(str9)) {}
    try
    {
      k = Integer.parseInt(str9);
    }
    catch (Exception localException4)
    {
      int k;
      for (;;) {}
    }
    k = 0;
    localObject = (String)((Map)localObject).get("apkSize");
    if (!TextUtils.isEmpty((CharSequence)localObject)) {}
    try
    {
      l = Long.parseLong((String)localObject);
    }
    catch (Exception localException1)
    {
      long l;
      for (;;) {}
    }
    l = 0L;
    try
    {
      paramDownloadListenerExtension.onSafeDownload(paramString, i, str1, str2, str3, str4, str5, str6, j, k, str7, str8, l);
      return;
    }
    catch (Exception paramDownloadListenerExtension)
    {
      paramDownloadListenerExtension.printStackTrace();
      return;
    }
  }
  
  private boolean isAudioActive(Context paramContext)
  {
    paramContext = (AudioManager)paramContext.getSystemService("audio");
    if (paramContext == null) {
      return false;
    }
    return paramContext.isMusicActive();
  }
  
  private boolean isQBEverActivedByTBSCheck()
  {
    Object localObject = this.mWebView.getContext().getApplicationContext();
    for (;;)
    {
      try
      {
        localObject = new File(((Context)localObject).getExternalFilesDir(null).getAbsolutePath().replace(((Context)localObject).getPackageName(), "com.tencent.mtt"));
        if ((((File)localObject).exists()) && (((File)localObject).isDirectory())) {
          i = ((File)localObject).listFiles().length;
        } else {
          i = 0;
        }
      }
      catch (Throwable localThrowable)
      {
        int i;
        continue;
      }
      try
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(Environment.getExternalStorageDirectory());
        ((StringBuilder)localObject).append(File.separator);
        ((StringBuilder)localObject).append("Android");
        ((StringBuilder)localObject).append(File.separator);
        ((StringBuilder)localObject).append("data");
        ((StringBuilder)localObject).append(File.separator);
        ((StringBuilder)localObject).append("com.tencent.mtt");
        ((StringBuilder)localObject).append(File.separator);
        ((StringBuilder)localObject).append("files");
        localObject = new File(((StringBuilder)localObject).toString());
        if ((((File)localObject).exists()) && (((File)localObject).isDirectory())) {
          i = ((File)localObject).listFiles().length;
        } else {
          i = 0;
        }
      }
      catch (Exception localException) {}
    }
    i = 0;
    return i > 0;
  }
  
  private boolean isQQDownloaderDomain(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    return (paramString.contains("app.qq.com")) || (paramString.contains("myapp.com")) || (paramString.contains("mobileapp.3g.qq.com")) || (paramString.contains("sparta.html5.qq.com")) || (paramString.contains("myapp.sparta.html5.com")) || (paramString.contains("cs0309.3g.qq.com"));
  }
  
  private boolean needRecordHostScale(String paramString)
  {
    return TencentWebViewDatabaseAdapter.getInstance(this.mContext).getForcePinchScale(paramString) ^ true;
  }
  
  private void recordUrlAndScaleInternal(String paramString, double paramDouble)
  {
    if (!needRecordHostScale(paramString)) {
      return;
    }
    if (ScaleManager.getInstance().isUrlExist(paramString)) {
      ScaleManager.getInstance().updateScale(paramString, paramDouble);
    } else {
      ScaleManager.getInstance().addScale(paramString, paramDouble);
    }
    if ((ScaleManager.isFirstSaved()) && (this.mWebViewProxy.getWebViewClientExtension() != null))
    {
      this.mWebViewProxy.getWebViewClientExtension().onPromptScaleSaved();
      ScaleManager.alreadySaved();
    }
  }
  
  private void requestHeadsupIfNeeded()
  {
    Object localObject;
    if (mLastGetQBHeadsupTime == 0L)
    {
      if (this.mPreferenceHeadsup == null) {
        this.mPreferenceHeadsup = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("qqth", 0);
      }
      localObject = this.mPreferenceHeadsup;
      if (localObject != null) {
        mLastGetQBHeadsupTime = Long.parseLong(((SharedPreferences)localObject).getString(this.KEY_LAST_GET_TIME, "0"));
      }
    }
    if (System.currentTimeMillis() - mLastGetQBHeadsupTime > 86400000L)
    {
      SmttServiceProxy.getInstance().requestHeadsUp(this.mWebView.getContext());
      mLastGetQBHeadsupTime = System.currentTimeMillis();
      if (this.mPreferenceHeadsup == null) {
        this.mPreferenceHeadsup = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("qqth", 0);
      }
      localObject = this.mPreferenceHeadsup;
      if (localObject != null)
      {
        localObject = ((SharedPreferences)localObject).edit();
        if (localObject != null)
        {
          ((SharedPreferences.Editor)localObject).putLong(this.KEY_LAST_GET_TIME, mLastGetQBHeadsupTime);
          ((SharedPreferences.Editor)localObject).commit();
        }
      }
    }
  }
  
  /* Error */
  private static boolean sendBrowsingIntent(Context paramContext, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    // Byte code:
    //   0: iload_2
    //   1: ifne +9 -> 10
    //   4: iload_3
    //   5: ifne +5 -> 10
    //   8: iconst_1
    //   9: ireturn
    //   10: aload_1
    //   11: iconst_1
    //   12: invokestatic 941	android/content/Intent:parseUri	(Ljava/lang/String;I)Landroid/content/Intent;
    //   15: astore_1
    //   16: aload_1
    //   17: ldc_w 943
    //   20: invokevirtual 946	android/content/Intent:addCategory	(Ljava/lang/String;)Landroid/content/Intent;
    //   23: pop
    //   24: aload_1
    //   25: aconst_null
    //   26: invokevirtual 950	android/content/Intent:setComponent	(Landroid/content/ComponentName;)Landroid/content/Intent;
    //   29: pop
    //   30: getstatic 955	android/os/Build$VERSION:SDK_INT	I
    //   33: bipush 15
    //   35: if_icmplt +8 -> 43
    //   38: aload_1
    //   39: aconst_null
    //   40: invokevirtual 958	android/content/Intent:setSelector	(Landroid/content/Intent;)V
    //   43: aload_1
    //   44: ldc_w 960
    //   47: aload_0
    //   48: invokevirtual 836	android/content/Context:getPackageName	()Ljava/lang/String;
    //   51: invokevirtual 534	android/content/Intent:putExtra	(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   54: pop
    //   55: aload_0
    //   56: aload_1
    //   57: invokevirtual 559	android/content/Context:startActivity	(Landroid/content/Intent;)V
    //   60: iconst_1
    //   61: ireturn
    //   62: astore_0
    //   63: iconst_0
    //   64: ireturn
    //   65: astore_0
    //   66: iconst_0
    //   67: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	68	0	paramContext	Context
    //   0	68	1	paramString	String
    //   0	68	2	paramBoolean1	boolean
    //   0	68	3	paramBoolean2	boolean
    // Exception table:
    //   from	to	target	type
    //   10	16	62	java/lang/Exception
    //   55	60	65	android/content/ActivityNotFoundException
  }
  
  private boolean shouldOverrideUrlLoadingCustomInternal(String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt, String paramString2)
  {
    boolean bool2 = ContextHolder.getInstance().isThirdPartyApp(this.mContext);
    boolean bool1 = true;
    if (bool2)
    {
      this.mCanOpenDebugtbs = m.a().a();
      if (this.mCanOpenDebugtbs)
      {
        if (!this.mHasEverOpendDebugtbs)
        {
          this.mHasEverOpendDebugtbs = true;
          paramString1 = new AwContentsClient.AwWebResourceRequest();
          paramString1.url = "http://debugtbs.qq.com?from=core";
          paramString1.isMainFrame = paramBoolean1;
          paramString1.hasUserGesture = paramBoolean2;
          paramString1.isRedirect = paramBoolean3;
          paramString1.method = "GET";
          return this.mWebViewClient.shouldOverrideUrlLoading(this.mWebView, new WebResourceRequestImpl(paramString1));
        }
        if (!paramString1.startsWith("http://debugx5.qq.com")) {
          return true;
        }
      }
    }
    if (paramString1.startsWith("http://debugx5.qq.com"))
    {
      i = 1;
      paramBoolean1 = bool1;
    }
    else if (j.a(this.mWebView.getContext(), paramString1))
    {
      i = 2;
      paramBoolean1 = bool1;
    }
    else
    {
      if ((SmttServiceProxy.getInstance().getContentCacheEnabled()) && ((!SmttServiceProxy.getInstance().isShouldContentCacheCurrentFrameWhenJsLocation(paramString1)) || (!((TencentWebViewClient)this.mWebViewClient).countPVContentCacheCallBack())))
      {
        this.mWebViewProxy.getWebViewProvider().getExtension().setContentCacheCurrentFrameWhenJsLocation(false);
        if (!((TencentWebViewClient)this.mWebViewClient).countPVContentCacheCallBack()) {
          this.mWebViewProxy.getSettingsExtension().setContentCacheEnable(false);
        }
      }
      TraceEvent.begin("WebViewContentsClientAdapter.shouldOverrideUrlLoading");
    }
    for (;;)
    {
      try
      {
        paramString2 = new URI(paramString1).getScheme();
        bool2 = SmttServiceProxy.getInstance().isSchemeInDeeplinkBlackList(paramString2);
        if (bool2)
        {
          i = 3;
          paramBoolean1 = bool1;
        }
      }
      catch (Exception paramString2)
      {
        continue;
      }
      try
      {
        new URI(paramString1).getScheme();
        if (SmttServiceProxy.getInstance().schemeInterceptActiveQBEnable(this.mWebView.getContext()))
        {
          paramString2 = new URI(paramString1).getScheme();
          if ((paramString2 != null) && (!paramString2.equals("http")) && (!paramString2.equals("https")) && (!paramString2.equals("jsbridge")) && (!paramString2.equals("data")) && (!paramString2.equals("file")) && (!paramString2.equals("mailto")) && (!paramString2.equals("sms")) && (!paramString2.equals("tel")) && (!paramString2.equals("weixin")) && (!paramString2.equals("qapp")) && (!paramString2.startsWith("mqq")) && (!paramString2.startsWith("mtt")) && (!paramString2.startsWith("qfav")) && (!paramString2.startsWith("java")) && (!paramString2.startsWith("mqzone")) && (!paramString2.startsWith("qgame")) && (!paramString2.startsWith("qq")) && (!paramString2.startsWith("tencent")) && (!paramString2.startsWith("tenvideo")) && (!paramString2.startsWith("weiyun")) && (SmttServiceProxy.getInstance().isSchemeIntercept(paramString2)) && (!SmttServiceProxy.getInstance().isQQDomain(paramString1)) && (!isQQDownloaderDomain(paramString1)))
          {
            SmttServiceProxy.getInstance().userBehaviorStatistics("TBSSAQ");
            if (f.a(this.mWebView.getContext(), paramString1, "BDTT6", "9100", new Point(0, 0), new Point(0, 0)))
            {
              SmttServiceProxy.getInstance().userBehaviorStatistics("TBSSAQT");
              return true;
            }
          }
        }
      }
      catch (Exception paramString2) {}
    }
    if (hasWebViewClient()) {
      try
      {
        this.mShouldOverrideUrlLoadingListForLoadFaild.add(paramString1);
        paramString2 = new AwContentsClient.AwWebResourceRequest();
        paramString2.url = paramString1;
        paramString2.isMainFrame = paramBoolean1;
        paramString2.hasUserGesture = paramBoolean2;
        paramString2.isRedirect = paramBoolean3;
        paramString2.method = "GET";
        paramBoolean1 = this.mWebViewClient.shouldOverrideUrlLoading(this.mWebView, new WebResourceRequestImpl(paramString2));
        if ((!paramBoolean1) && (!TextUtils.isEmpty(paramString1)))
        {
          bool2 = paramString1.startsWith("faketel:");
          if (bool2)
          {
            i = 6;
            TraceEvent.end("WebViewContentsClientAdapter.shouldOverrideUrlLoading");
            paramBoolean1 = bool1;
            break label814;
          }
        }
        if (paramBoolean1) {
          this.mShouldOverrideUrlLoadingList.add(paramString1);
        }
        TraceEvent.end("WebViewContentsClientAdapter.shouldOverrideUrlLoading");
        i = 7;
      }
      finally
      {
        TraceEvent.end("WebViewContentsClientAdapter.shouldOverrideUrlLoading");
      }
    }
    paramBoolean1 = sendBrowsingIntent(this.mContext, paramString1, paramBoolean2, paramBoolean3);
    int i = 8;
    label814:
    paramString2 = new StringBuilder();
    paramString2.append("shouldOverrideUrlLoadingCustom result= ");
    paramString2.append(paramBoolean1);
    paramString2.append("; step=");
    paramString2.append(i);
    paramString2.append(";  isRedirect=");
    paramString2.append(paramBoolean3);
    paramString2.append("; hasUserGesture=");
    paramString2.append(paramBoolean2);
    paramString2.append("; type=");
    paramString2.append(paramInt);
    paramString2.append("; hasWebViewClient=");
    paramString2.append(hasWebViewClient());
    paramString2.append("; url=");
    paramString2.append(paramString1);
    X5Log.i("X5-WebviewCallback", paramString2.toString());
    return paramBoolean1;
  }
  
  public void OnContentCachePageSwapIn(String paramString)
  {
    ((TencentWebViewClient)this.mWebViewClient).OnContentCachePageSwapIn(paramString);
  }
  
  public void activeQBBackHeartBeatIfNeeded()
  {
    try
    {
      if (mActiveQBBackDone)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHS0F");
        return;
      }
      mActiveQBBackDone = true;
      tbsFileLockFileLock = getTbsCoreRenameFileLock(this.mWebView.getContext());
      if (tbsFileLockFileLock == null)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHS1F");
        return;
      }
      boolean bool1 = SmttServiceProxy.getInstance().activeQBBackHeartBeatEnable(this.mWebView.getContext());
      boolean bool2 = SmttServiceProxy.getInstance().isQBPureIncrease(this.mWebView.getContext());
      int i = SmttServiceProxy.getInstance().activeQBBackHeartBeatFrequency(this.mWebView.getContext());
      if (!bool1)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHS2F");
        return;
      }
      if (!bool2)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHS3F");
        return;
      }
      if (i == 0)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHS4F");
        return;
      }
      if (i == -1)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHS5F");
        return;
      }
      if (isQBEverActivedByTBSCheck())
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHS6F");
        return;
      }
      if (this.mPreferenceActiveQBBack == null) {
        this.mPreferenceActiveQBBack = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("qqtaqb", 0);
      }
      if (this.mPreferenceActiveQBBack != null) {
        this.mTimesActiveQBBackByTBSLocal = this.mPreferenceActiveQBBack.getLong(this.KEY_TIMES, 0L);
      }
      if (this.mTimesActiveQBBackByTBSLocal >= i)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHS7F");
        return;
      }
      if (MttLoader.isBrowserInstalled(this.mWebView.getContext()))
      {
        if (MttLoader.getBrowserInfo(this.mWebView.getContext()).ver < 9610000)
        {
          SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHVLT961");
          return;
        }
        SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHCAQ");
        this.mIsQBActived = false;
        checkQBActive();
        this.mHandlerActiveQBBack = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime());
        this.mHandlerActiveQBBack.postDelayed(this.mDelayActiveQBBackRunnable, 5000L);
        return;
      }
      SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHQNI");
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHEXP1");
  }
  
  boolean canShowAlertDialog(Context paramContext)
  {
    return ((paramContext instanceof Activity)) && (!((Activity)paramContext).isFinishing());
  }
  
  public Bitmap getDefaultVideoPoster()
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.getDefaultVideoPoster");
      Bitmap localBitmap = null;
      if (this.mWebChromeClient != null) {
        localBitmap = this.mWebChromeClient.getDefaultVideoPoster();
      }
      return localBitmap;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.getDefaultVideoPoster");
    }
  }
  
  public IX5WebChromeClientExtension getIX5WebChromeClientExtension()
  {
    TencentWebViewProxy localTencentWebViewProxy = this.mWebViewProxy;
    if ((localTencentWebViewProxy != null) && (localTencentWebViewProxy.getWebViewProvider() != null) && (this.mWebViewProxy.getWebViewProvider().getExtension() != null)) {
      return this.mWebViewProxy.getWebViewProvider().getExtension().getWebChromeClientExtension();
    }
    return null;
  }
  
  public IX5QQBrowserClient getQQBrowserClient()
  {
    return getIX5QQBrowserClient();
  }
  
  public boolean isMiniQB()
  {
    return ((X5WebViewAdapter)this.mWebViewProxy).isMiniQB();
  }
  
  public void notifyFirstScreenFinished(long paramLong1, long paramLong2)
  {
    this.mWebViewProxy.getWebViewProvider().notifyFirstScreenFinished(paramLong1, paramLong2);
    Object localObject = this.mWebView.getUrl();
    String str = TencentURLUtil.getHost((String)localObject);
    if (X5Log.isEnableLog())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("evaluateJavaScript url=");
      localStringBuilder.append((String)localObject);
      localStringBuilder.append(" WebSettingsExtension.getRemoveAds()=");
      localStringBuilder.append(WebSettingsExtension.getRemoveAds());
      localStringBuilder.append(" getIsAdFilterEnhance(debug)=");
      localStringBuilder.append(SmttServiceProxy.getInstance().getIsAdFilterEnhance());
      localStringBuilder.append(" AdInfoManager.adFilterString.length=");
      localStringBuilder.append(AdInfoManager.adFilterString.length());
      localStringBuilder.append(" shouldNotExecuteSmartAdFilter(host)=");
      localStringBuilder.append(SmttServiceProxy.getInstance().shouldNotExecuteSmartAdFilter(str));
      localStringBuilder.append(" isDomainFeatureRulesEmpty(host)=");
      localStringBuilder.append(AdInfoManager.getInstance().isDomainFeatureRulesEmpty(str));
      localStringBuilder.append(" isQbMode=");
      localStringBuilder.append(e.a().n());
      X5Log.d("AdFilter", localStringBuilder.toString());
    }
    if ((WebSettingsExtension.getRemoveAds()) && (localObject != null) && (str != null) && (e.a().n()) && (((String)localObject).startsWith("http")) && (AdInfoManager.adFilterString != null) && (AdInfoManager.adFilterString.length() > 0) && ((SmttServiceProxy.getInstance().getIsAdFilterEnhance()) || ((!SmttServiceProxy.getInstance().shouldNotExecuteSmartAdFilter(str)) && (!AdInfoManager.getInstance().isDomainFeatureRulesEmpty(str)))))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("evaluateJavaScript url=");
      ((StringBuilder)localObject).append(this.mWebView.getUrl());
      ((StringBuilder)localObject).append(" AdInfoManager.adFilterString.length=");
      ((StringBuilder)localObject).append(AdInfoManager.adFilterString.length());
      X5Log.d("AdFitler", ((StringBuilder)localObject).toString());
      this.mWebViewProxy.getWebViewProvider().evaluateJavaScript(AdInfoManager.adFilterString.toString(), null);
    }
  }
  
  public void notifySecurityLevel(JSONObject paramJSONObject)
  {
    if (this.mWebViewProxy.getWebViewClientExtension() != null)
    {
      if (X5Log.isEnableLog())
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("notifySecurityLevel security=");
        localStringBuilder.append(paramJSONObject.toString());
        X5Log.d("NET", localStringBuilder.toString());
      }
      this.mWebViewProxy.getWebViewClientExtension().onMiscCallBack("onReportSecurityLevel", null, paramJSONObject, null, null, null);
    }
  }
  
  public void onClickFirstScreenViewBackGround()
  {
    this.mFirstScreenViewBackGround.setVisibility(8);
    this.mFirstScreenViewBackGround = null;
    this.mFileChooserFirstScreenView.setVisibility(8);
    this.mFileChooserFirstScreenView = null;
    ((TencentWebViewProxy.InnerWebView)this.mWebView).getOuterProxy().getRealWebView().setFileChooserFirstScreenView(null);
  }
  
  public void onClickToFileChooser()
  {
    this.mFileChooserFirstScreenView.setVisibility(8);
    this.mFileChooserFirstScreenView = null;
    ((TencentWebViewProxy.InnerWebView)this.mWebView).getOuterProxy().getRealWebView().setFileChooserFirstScreenView(null);
    this.mFirstScreenViewBackGround.setVisibility(8);
    this.mFirstScreenViewBackGround = null;
  }
  
  public void onCloseWindow()
  {
    super.onCloseWindow();
    if (X5Log.isEnableLog()) {
      X5Log.d("WebViewCallback", "onCloseWindow");
    }
    AdInfoManager.getInstance().syncAdInfoFromRamToFlash();
    PersistentSessionManager.getInstance().syncSessionInfoFromRamToFlash();
  }
  
  public void onContentsSizeChanged(int paramInt1, int paramInt2)
  {
    this.mWebViewProxy.notifyContentsSizeChanged(paramInt1, paramInt2);
  }
  
  public boolean onCreateWindow(boolean paramBoolean1, boolean paramBoolean2)
  {
    return onCreateWindow(paramBoolean1, paramBoolean2, false);
  }
  
  public boolean onCreateWindow(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onCreateWindow");
      boolean bool = false;
      if (paramBoolean3)
      {
        localObject1 = new X5WebViewAdapter(this.mWebView.getContext(), false);
        ((X5WebViewAdapter)localObject1).setWebViewClient(new com.tencent.smtt.webkit.g(this.mWebViewProxy, this.mWebViewProxy.getWebViewClient()));
        ((X5WebViewAdapter)localObject1).setWebViewClientExtension(new h());
        if (((X5WebViewAdapter)localObject1).setIsCreatedFullScreenSurfaceView())
        {
          localObject3 = this.mUiThreadHandler.obtainMessage(100, new IX5WebViewBase.WebViewTransport());
          ((IX5WebViewBase.WebViewTransport)((Message)localObject3).obj).setWebView((IX5WebViewBase)localObject1);
          ((Message)localObject3).sendToTarget();
          return true;
        }
      }
      Object localObject1 = this.mUiThreadHandler;
      Object localObject3 = this.mWebView;
      localObject3.getClass();
      localObject1 = ((Handler)localObject1).obtainMessage(100, new WebView.WebViewTransport((WebView)localObject3));
      paramBoolean3 = bool;
      if (this.mWebChromeClient != null) {
        paramBoolean3 = this.mWebChromeClient.onCreateWindow(this.mWebView, paramBoolean1, paramBoolean2, (Message)localObject1);
      }
      return paramBoolean3;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onCreateWindow");
    }
  }
  
  public void onDetectedBlankScreen(String paramString, int paramInt)
  {
    try
    {
      ((TencentWebViewClient)this.mWebViewClient).onDetectedBlankScreen(this.mWebView, paramString, paramInt);
      return;
    }
    catch (Exception paramString) {}
  }
  
  public void onDownloadStartCustom(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte, String paramString9)
  {
    Object localObject = this.mWebViewProxy;
    if (((TencentWebViewProxy)localObject).onDownloadStartCustom(paramString1, paramString2, paramString3, paramString4, paramLong, paramString5, paramString6, paramString7, paramString8, paramArrayOfByte, (TencentWebViewProxy)localObject, this.mDownloadListener, paramString9)) {
      return;
    }
    try
    {
      if (this.mDownloadListenerExtension != null)
      {
        paramString9 = this.mDownloadListenerExtension;
        try
        {
          paramString9.onDownloadListenerStart(paramString6, paramArrayOfByte, paramString7, paramString8);
        }
        catch (Exception paramString9) {}
      }
    }
    catch (Exception paramString9)
    {
      paramString9.printStackTrace();
    }
    String str = paramString8;
    localObject = paramString7;
    paramString9 = paramString6;
    if ((this.mDownloadListenerExtension != null) && (paramString5 != null) && (paramString5.length() > 0)) {
      handleQProxyRespAndInvokeMethod(this.mDownloadListenerExtension, paramString5);
    }
    TencentTraceEvent.begin();
    if (this.mDownloadListener != null)
    {
      paramString5 = new StringBuilder();
      paramString5.append("Download url = ");
      paramString5.append(paramString1);
      paramString5.append(" userAgent is ");
      paramString5.append(paramString2);
      paramString5.append(" contentDisposition is ");
      paramString5.append(paramString3);
      paramString5.append(" mimeType is ");
      paramString5.append(paramString4);
      paramString5.append(" contentLength is ");
      paramString5.append(paramLong);
      paramString5.append(" method is ");
      paramString5.append(paramString9);
      paramString5.append(" reffer is");
      paramString5.append((String)localObject);
      paramString5.append(" urlBeforeRedirect is ");
      paramString5.append(str);
      X5Log.d("DownLoad", paramString5.toString());
      try
      {
        if (e.a().l()) {
          ((TencentDownloadListener)this.mDownloadListener).onDownloadStart(paramString1, paramString6, paramArrayOfByte, paramString2, paramString3, paramString4, paramLong, paramString7, paramString8);
        } else {
          this.mDownloadListener.onDownloadStart(paramString1, paramString2, paramString3, paramString4, paramLong);
        }
      }
      catch (Throwable paramString5)
      {
        try
        {
          paramString6 = this.mDownloadListener.getClass().getDeclaredMethod("onDownloadStart", new Class[] { String.class, String.class, byte[].class, String.class, String.class, String.class, Long.TYPE, String.class, String.class });
          paramString6.setAccessible(true);
          paramString6.invoke(this.mDownloadListener, new Object[] { paramString1, paramString9, paramArrayOfByte, paramString2, paramString3, paramString4, Long.valueOf(paramLong), localObject, str });
        }
        catch (Throwable paramString1)
        {
          paramString1.printStackTrace();
        }
        paramString5.printStackTrace();
      }
    }
    TencentTraceEvent.end();
  }
  
  public void onGeolocationPermissionsShowPrompt(String paramString, AwGeolocationPermissions.Callback paramCallback)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onGeolocationPermissionsShowPrompt");
      if (this.mWebChromeClient == null)
      {
        paramCallback.invoke(paramString, false, false);
        return;
      }
      this.mWebChromeClient.onGeolocationPermissionsShowPrompt(paramString, new X5GeolocationPermissionsCallback(paramCallback));
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onGeolocationPermissionsShowPrompt");
    }
  }
  
  public void onHideCustomView()
  {
    super.onHideCustomView();
    this.mWebViewProxy.getWebViewProvider().getExtension().exitFullScreen();
  }
  
  public void onPageFinished(String paramString)
  {
    this.mWebViewProxy.getWebViewProvider().getExtension().onPageFinished(paramString);
    if (!this.ismHasEverOpendDebugtbs)
    {
      m.a().a(this.mWebViewProxy.getRealWebView());
      this.ismHasEverOpendDebugtbs = true;
    }
    SmttServiceProxy.getInstance().headsupActiveQB(this.mWebView.getContext());
    SmttServiceProxy.getInstance().ActiveQBHeartBeat(this.mWebView.getContext());
    SmttServiceProxy.getInstance().monitorAppRemoved(this.mWebView.getContext());
    if (((X5WebViewAdapter)this.mWebViewProxy).isForceLongClickQuickCopy()) {
      ((X5WebViewAdapter)this.mWebViewProxy).preInitLongClickPopupMenu();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("WebViewContentsClientAdapter.onPageFinished url = ");
    localStringBuilder.append(paramString);
    X5Log.i("NET", localStringBuilder.toString());
    super.onPageFinished(paramString);
  }
  
  public void onPageStarted(String paramString)
  {
    this.mShouldOverrideUrlLoadingList.clear();
    this.mShouldOverrideUrlLoadingListForLoadFaild.clear();
    if (!paramString.isEmpty()) {
      com.tencent.smtt.net.g.a().a(this, paramString);
    }
    this.mWebViewProxy.getWebViewProvider().getExtension().onPageStarted(paramString);
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("WebViewContentsClientAdapter.onPageStarted url = ");
    ((StringBuilder)localObject).append(paramString);
    X5Log.i("NET", ((StringBuilder)localObject).toString());
    localObject = SmttServiceProxy.getInstance().getTbsUserinfoLiveLog();
    if (!TextUtils.isEmpty((CharSequence)localObject)) {
      X5Log.i("TBSUSERINFO", (String)localObject);
    }
    if ((paramString != null) && (TencentURLUtil.getHost(paramString).contains("jd.com"))) {
      SmttServiceProxy.getInstance().userBehaviorStatistics("AYZ0001");
    }
    super.onPageStarted(paramString);
  }
  
  public void onReceivedClientCertRequest(AwContentsClientBridge.ClientCertificateRequestCallback paramClientCertificateRequestCallback, String[] paramArrayOfString, Principal[] paramArrayOfPrincipal, String paramString, int paramInt)
  {
    if (mShouldCallonReceivedClientCertRequest) {}
    try
    {
      super.onReceivedClientCertRequest(paramClientCertificateRequestCallback, paramArrayOfString, paramArrayOfPrincipal, paramString, paramInt);
      return;
    }
    catch (Throwable paramArrayOfString)
    {
      for (;;) {}
    }
    mShouldCallonReceivedClientCertRequest = false;
    paramClientCertificateRequestCallback.cancel();
    return;
    paramClientCertificateRequestCallback.cancel();
  }
  
  public void onReceivedDidFailLoad(String paramString, int paramInt)
  {
    if (this.mWebViewProxy.getWebViewClientExtension() == null) {
      return;
    }
    try
    {
      this.mWebViewProxy.getWebViewClientExtension().didFailLoad(paramString, paramInt);
      return;
    }
    catch (NoSuchMethodError paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void onReceivedError2(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, org.chromium.android_webview.AwContentsClient.AwWebResourceError paramAwWebResourceError)
  {
    try
    {
      try
      {
        TraceEvent.begin("WebViewContentsClientAdapter.onReceivedError");
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedError url=");
        localStringBuilder.append(paramAwWebResourceRequest.url);
        localStringBuilder.append(" error=");
        localStringBuilder.append(paramAwWebResourceError.errorCode);
        X5Log.d("NET", localStringBuilder.toString());
        if ((paramAwWebResourceError.description == null) || (paramAwWebResourceError.description.isEmpty())) {
          paramAwWebResourceError.description = this.mWebViewDelegate.getErrorString(this.mContext, paramAwWebResourceError.errorCode);
        }
        if (mOnReceivedErrorMethodExisted) {
          this.mWebViewClient.onReceivedError(this.mWebView, new WebResourceRequestImpl(paramAwWebResourceRequest), new WebResourceErrorImpl(paramAwWebResourceError));
        } else if (paramAwWebResourceRequest.isMainFrame) {
          this.mWebViewClient.onReceivedError(this.mWebView, paramAwWebResourceError.errorCode, paramAwWebResourceError.description, paramAwWebResourceRequest.url);
        }
        this.mWebViewProxy.notifyReceivedError2(paramAwWebResourceRequest, paramAwWebResourceError);
      }
      finally
      {
        break label219;
      }
    }
    catch (Throwable localThrowable)
    {
      label219:
      for (;;) {}
    }
    mOnReceivedErrorMethodExisted = false;
    if (paramAwWebResourceRequest.isMainFrame) {
      this.mWebViewClient.onReceivedError(this.mWebView, paramAwWebResourceError.errorCode, paramAwWebResourceError.description, paramAwWebResourceRequest.url);
    }
    TraceEvent.end("WebViewContentsClientAdapter.onReceivedError");
    return;
    TraceEvent.end("WebViewContentsClientAdapter.onReceivedError");
    throw paramAwWebResourceRequest;
  }
  
  public void onReceivedHttpError(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse)
  {
    TraceEvent.begin("WebViewContentsClientAdapter.onReceivedHttpError");
    if (mShouldCallonReceivedHttpError) {}
    try
    {
      try
      {
        this.mWebViewClient.onReceivedHttpError(this.mWebView, new WebResourceRequestImpl(paramAwWebResourceRequest), new com.tencent.tbs.core.webkit.WebResourceResponse(true, paramAwWebResourceResponse.getMimeType(), paramAwWebResourceResponse.getCharset(), paramAwWebResourceResponse.getStatusCode(), paramAwWebResourceResponse.getReasonPhrase(), paramAwWebResourceResponse.getResponseHeaders(), paramAwWebResourceResponse.getData()));
      }
      finally
      {
        break label83;
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    mShouldCallonReceivedHttpError = false;
    TraceEvent.end("WebViewContentsClientAdapter.onReceivedHttpError");
    break label91;
    label83:
    TraceEvent.end("WebViewContentsClientAdapter.onReceivedHttpError");
    throw paramAwWebResourceRequest;
    label91:
    this.mWebViewProxy.notifyReceivedHttpError(paramAwWebResourceRequest, paramAwWebResourceResponse);
  }
  
  public void onReceivedIcon(Bitmap paramBitmap)
  {
    super.onReceivedIcon(paramBitmap);
    if (TencentWebIconDatabaseAdapter.getInstance() != null) {
      TencentWebIconDatabaseAdapter.getInstance().updateReceivedIcon(this.mWebView.getUrl(), paramBitmap);
    }
  }
  
  public void onReceivedResourceError(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2, long paramLong)
  {
    if (this.mWebViewProxy.getWebViewClientExtension() == null) {
      return;
    }
    if (paramInt2 >= 400) {
      paramInt1 = 0;
    } else {
      paramInt2 = 0;
    }
    Bundle localBundle = new Bundle();
    localBundle.putString("url", paramString1);
    localBundle.putInt("error_id", paramInt1);
    localBundle.putInt("status_code", paramInt2);
    localBundle.putInt("resource_type", paramInt3);
    localBundle.putString("server_ip", paramString2);
    localBundle.putLong("content_length", paramLong);
    this.mWebViewProxy.getWebViewClientExtension().onMiscCallBack("onReceivedResourceError", localBundle);
  }
  
  public void onReceivedSslError(Callback<Boolean> paramCallback, SslError paramSslError)
  {
    onReceivedSslError(paramCallback, paramSslError, "", "", "", false, false);
  }
  
  public void onReceivedSslError(final Callback<Boolean> paramCallback, SslError paramSslError, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramCallback = new SslErrorHandler()
    {
      public void cancel()
      {
        paramCallback.onResult(Boolean.valueOf(false));
      }
      
      public void proceed()
      {
        paramCallback.onResult(Boolean.valueOf(true));
      }
    };
    TraceEvent.begin("WebViewContentsClientAdapter.onReceivedSslError");
    ((TencentWebViewClient)this.mWebViewClient).onReceivedSslError(this.mWebView, paramCallback, paramSslError, paramString1, paramString2, paramString3, paramBoolean1, paramBoolean2);
    TraceEvent.end("WebViewContentsClientAdapter.onReceivedSslError");
  }
  
  public void onReceivedTitle(String paramString)
  {
    if (this.mWebView.getUrl() != null) {
      super.onReceivedTitle(paramString);
    }
  }
  
  public void onReportFramePerformanceInfo(JSONObject paramJSONObject)
  {
    try
    {
      this.mWebViewProxy.getWebViewClientExtension().onMiscCallBack("onFramePerformanceInfo", null, paramJSONObject, null, null, null);
      return;
    }
    catch (Exception paramJSONObject) {}
  }
  
  public void onReportJSPerformanceInfo(JSONObject paramJSONObject)
  {
    this.mWebViewProxy.getWebViewClientExtension().onMiscCallBack("onJSPerformanceInfo", null, paramJSONObject, null, null, null);
  }
  
  public void onReportMainResourceResponse(String paramString, AwWebResourceResponse paramAwWebResourceResponse)
  {
    if ((paramAwWebResourceResponse != null) && (this.mWebViewProxy.getWebViewClientExtension() != null)) {
      if (!mShouldCallonReportMainResourceResponse) {
        return;
      }
    }
    try
    {
      paramString = new com.tencent.smtt.export.external.interfaces.WebResourceResponse(paramAwWebResourceResponse.getMimeType(), paramAwWebResourceResponse.getCharset(), paramAwWebResourceResponse.getStatusCode(), paramAwWebResourceResponse.getReasonPhrase(), paramAwWebResourceResponse.getResponseHeaders(), null);
      this.mWebViewProxy.getWebViewClientExtension().onMiscCallBack("onReportMainResourceResponse", null, paramString, null, null, null);
      return;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    mShouldCallonReportMainResourceResponse = false;
    return;
  }
  
  public void onReportResourceAllInfo(AwContentsClient.AwWebRequestInfo paramAwWebRequestInfo)
  {
    try
    {
      com.tencent.smtt.net.g.a().a(this, paramAwWebRequestInfo);
      return;
    }
    catch (Exception paramAwWebRequestInfo) {}
  }
  
  public void onReportResourceAllInfoToUI(JSONObject paramJSONObject, AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse, org.chromium.tencent.AwContentsClient.AwWebResourceError paramAwWebResourceError)
  {
    if (this.mWebViewProxy.getWebViewClientExtension() != null)
    {
      if (!mShouldCallonReportRequestAllInfo) {
        return;
      }
      if (paramAwWebResourceRequest == null) {
        break label120;
      }
    }
    label120:
    label125:
    do
    {
      for (;;)
      {
        try
        {
          paramAwWebResourceRequest = new WebResourceRequestImpl(paramAwWebResourceRequest);
          if (paramAwWebResourceResponse == null) {
            break label125;
          }
          paramAwWebResourceResponse = new com.tencent.smtt.export.external.interfaces.WebResourceResponse(paramAwWebResourceResponse.getMimeType(), paramAwWebResourceResponse.getCharset(), paramAwWebResourceResponse.getStatusCode(), paramAwWebResourceResponse.getReasonPhrase(), paramAwWebResourceResponse.getResponseHeaders(), null);
          if (paramAwWebResourceError == null) {
            break;
          }
          i = paramAwWebResourceError.errorCode;
        }
        catch (Throwable paramJSONObject)
        {
          continue;
        }
        this.mWebViewProxy.getWebViewClientExtension().onMiscCallBack("onReportResourceInfo", null, paramJSONObject, paramAwWebResourceRequest, paramAwWebResourceResponse, Integer.valueOf(i));
        return;
        mShouldCallonReportRequestAllInfo = false;
        return;
        return;
        paramAwWebResourceRequest = null;
        continue;
        paramAwWebResourceResponse = null;
      }
      int i = 0;
    } while (paramJSONObject != null);
  }
  
  public void onShowCustomView(View paramView, AwContentsClient.CustomViewCallback paramCustomViewCallback)
  {
    super.onShowCustomView(paramView, paramCustomViewCallback);
    this.mWebViewProxy.getWebViewProvider().getExtension().enterFullScreen(paramView);
  }
  
  public void onUpdateHTMLElementAssoicateNativePanel(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int i;
    if ((paramInt2 < this.mWebView.getContentWidth()) && (paramInt2 + paramInt4 > 0) && (paramInt3 < this.mWebView.getContentHeight()) && (paramInt3 + paramInt5 > 0)) {
      i = 1;
    } else {
      i = 0;
    }
    if ("freewififorvideo".equals(paramString))
    {
      b.a(paramInt1, paramString, paramInt2, paramInt3, paramInt4, paramInt5, this.mWebViewProxy);
      return;
    }
    if ("freewififorvideo_fromvideoelment".equals(paramString))
    {
      if ((!SmttServiceProxy.getInstance().isWebUrlInCloudList(this.mWebView.getUrl(), 171)) || (i == 0)) {
        b.a(paramInt1, paramString, paramInt2, paramInt3, paramInt4, paramInt5, this.mWebViewProxy);
      }
    }
    else if (TextUtils.isEmpty(paramString)) {
      b.a(paramInt1, this.mWebViewProxy);
    }
  }
  
  public void recordUrlAndScale(final String paramString, final double paramDouble)
  {
    paramString = TencentURLUtil.getHost(paramString);
    WebSettingsExtension localWebSettingsExtension = this.mWebViewProxy.getSettingsExtension();
    if ((localWebSettingsExtension != null) && (!localWebSettingsExtension.isFitScreen()) && (paramString != null))
    {
      if ("".equals(paramString.trim())) {
        return;
      }
      if (!localWebSettingsExtension.autoRecoredAndRestoreScaleEnabled()) {
        return;
      }
      BrowserExecutorSupplier.postForTimeoutTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          TencentWebViewContentsClientAdapter.this.recordUrlAndScaleInternal(paramString, paramDouble);
        }
      });
      return;
    }
  }
  
  public void reportFirstByteFScreenTime(long paramLong1, long paramLong2, long paramLong3)
  {
    Object localObject = this.mWebViewProxy;
    if (localObject != null)
    {
      if (((TencentWebViewProxy)localObject).getWebViewClientExtension() == null) {
        return;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("firstbytetime:");
      ((StringBuilder)localObject).append(paramLong1);
      ((StringBuilder)localObject).append(" firstscreentime:");
      ((StringBuilder)localObject).append(paramLong2);
      ((StringBuilder)localObject).append("thread=");
      ((StringBuilder)localObject).append(Thread.currentThread().getName());
      X5Log.d("WebViewContentsClientAdapter", ((StringBuilder)localObject).toString());
      localObject = new Bundle();
      ((Bundle)localObject).putString("firstbyte", String.valueOf(paramLong1));
      ((Bundle)localObject).putString("firstscreen", String.valueOf(paramLong2));
      ((Bundle)localObject).putString("firstword", String.valueOf(paramLong3));
      this.mWebViewProxy.getWebViewClientExtension().onMiscCallBack("netTimeConsumingReport", (Bundle)localObject);
      return;
    }
  }
  
  public void restoreScale(final String paramString)
  {
    final String str = TencentURLUtil.getHost(paramString);
    WebSettingsExtension localWebSettingsExtension = this.mWebViewProxy.getSettingsExtension();
    if ((localWebSettingsExtension != null) && (paramString != null) && (str != null) && (localWebSettingsExtension.autoRecoredAndRestoreScaleEnabled()))
    {
      if (localWebSettingsExtension.isFitScreen()) {
        return;
      }
      BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          float f = 0.0F;
          for (;;)
          {
            final int i;
            try
            {
              if (!TencentWebViewContentsClientAdapter.this.needRecordHostScale(str)) {
                return;
              }
              if (paramString.toLowerCase().startsWith("file://")) {
                break label132;
              }
              f = (float)ScaleManager.getInstance().findScale(str);
            }
            catch (Exception localException)
            {
              double d1;
              double d2;
              localException.printStackTrace();
            }
            if (i != 0)
            {
              d1 = ScaleManager.getInstance().findScale(str);
              f = TencentWebViewContentsClientAdapter.this.mContext.getResources().getDisplayMetrics().density;
              d2 = f;
              Double.isNaN(d2);
              i = (int)(d1 * 100.0D * d2);
              TencentWebViewContentsClientAdapter.this.mUiThreadHandler.post(new Runnable()
              {
                public void run()
                {
                  TencentWebViewContentsClientAdapter.this.mWebView.setInitialScale(i);
                }
              });
              return;
            }
            return;
            label132:
            if (f > 0.0D) {
              i = 1;
            } else {
              i = 0;
            }
          }
        }
      });
      return;
    }
  }
  
  public void savePassword(final com.tencent.tbs.core.webkit.ValueCallback<String> paramValueCallback, final String paramString1, final String paramString2, final String paramString3, final String paramString4, final String paramString5, String[] paramArrayOfString)
  {
    boolean bool3;
    final boolean bool1;
    label134:
    label149:
    boolean bool2;
    if ((this.mWebView != null) && (this.mWebView.getSettings().getSavePassword()))
    {
      bool3 = true;
      int i;
      if (paramArrayOfString != null)
      {
        if (!TextUtils.isEmpty(paramArrayOfString[0])) {
          if (!TextUtils.isEmpty(paramArrayOfString[1]))
          {
            if ((paramArrayOfString[0].equals(paramString2)) && (paramArrayOfString[1].equals(paramString3))) {
              break label134;
            }
            if ((!paramArrayOfString[2].equals(paramString4)) && (!paramArrayOfString[3].equals(paramString5)))
            {
              i = 1;
              bool1 = false;
              break label149;
            }
            i = 1;
            bool1 = true;
            break label149;
          }
          else
          {
            i = 1;
            bool1 = false;
            break label149;
          }
        }
        i = 0;
        bool1 = false;
      }
      else
      {
        i = 1;
        bool1 = false;
      }
      if (i != 0)
      {
        if (getIX5WebChromeClientExtension() != null) {
          try
          {
            bool2 = getIX5WebChromeClientExtension().onSavePassword(new android.webkit.ValueCallback()
            {
              public void onReceiveValue(String paramAnonymousString)
              {
                paramValueCallback.onReceiveValue(paramAnonymousString);
              }
            }, paramString1, paramString2, paramString3, paramString4, paramString5, bool1);
          }
          catch (NoSuchMethodError paramArrayOfString)
          {
            paramArrayOfString.printStackTrace();
          }
          catch (Exception paramArrayOfString)
          {
            paramArrayOfString.printStackTrace();
          }
        } else {
          bool2 = false;
        }
        if (bool2) {
          break label401;
        }
        if (!canShowAlertDialog(this.mWebView.getContext())) {}
      }
    }
    for (;;)
    {
      try
      {
        if (this.mWebViewProxy.getSettingsExtension().getDayOrNight()) {
          break label412;
        }
        bool2 = bool3;
        e.a locala = new e.a(this.mWebView.getContext(), 2, bool2);
        if (bool1) {
          paramArrayOfString = SmttResource.getString("x5_save_replace_password_message", "string");
        } else {
          paramArrayOfString = SmttResource.getString("x5_save_password_message", "string");
        }
        locala.a(paramArrayOfString).b(SmttResource.getString("x5_save_password_remember", "string"), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            paramValueCallback.onReceiveValue("true");
            TencentWebViewContentsClientAdapter.this.mWebViewProxy.sendRememberMsg(paramString1, paramString2, paramString3, paramString4, paramString5);
          }
        }).a(SmttResource.getString("x5_save_password_notnow", "string"), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            if (bool1)
            {
              paramValueCallback.onReceiveValue("false");
              return;
            }
            paramValueCallback.onReceiveValue("true");
            TencentWebViewContentsClientAdapter.this.mWebViewProxy.sendRememberMsg(paramString1, paramString2, null, paramString4, paramString5);
          }
        }).a(false).a();
        return;
      }
      catch (Exception paramValueCallback)
      {
        paramValueCallback.printStackTrace();
        return;
      }
      paramValueCallback.onReceiveValue("false");
      return;
      paramValueCallback.onReceiveValue("false");
      label401:
      return;
      paramValueCallback.onReceiveValue("false");
      return;
      label412:
      bool2 = false;
    }
  }
  
  public void setDownLoadListenerExtension(DownloadListenerExtension paramDownloadListenerExtension)
  {
    this.mDownloadListenerExtension = paramDownloadListenerExtension;
  }
  
  void setDownloadListener(DownloadListener paramDownloadListener)
  {
    this.mDownloadListener = ((TencentDownloadListener)paramDownloadListener);
  }
  
  public void setFromX5FileChooserUI(boolean paramBoolean)
  {
    this.mFromX5FileChooserUI = paramBoolean;
  }
  
  public void setNetWorkLoadPolicyToCacheFirst(String paramString)
  {
    ((TencentWebViewClient)this.mWebViewClient).setNetWorkLoadPolicyToCacheFirst(paramString);
  }
  
  void setWebChromeClient(WebChromeClient paramWebChromeClient)
  {
    this.mWebChromeClient = ((TencentWebChromeClient)paramWebChromeClient);
  }
  
  void setWebViewClient(WebViewClient paramWebViewClient)
  {
    if (paramWebViewClient != null)
    {
      this.mWebViewClient = ((TencentWebViewClient)paramWebViewClient);
      return;
    }
    this.mWebViewClient = sNullWebViewClient;
  }
  
  public AwWebResourceResponse shouldInterceptRequest(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest)
  {
    AwWebResourceResponse localAwWebResourceResponse = super.shouldInterceptRequest(paramAwWebResourceRequest);
    boolean bool;
    if (localAwWebResourceResponse != null) {
      bool = false;
    } else {
      bool = true;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("shouldInterceptRequest url=");
    localStringBuilder.append(paramAwWebResourceRequest.url);
    localStringBuilder.append(" [resourceType]=");
    localStringBuilder.append(paramAwWebResourceRequest.resourceType);
    localStringBuilder.append(" [ifResponseNull]=");
    localStringBuilder.append(bool);
    X5Log.d("X5-WebviewCallback", localStringBuilder.toString());
    return localAwWebResourceResponse;
  }
  
  public boolean shouldOverrideUrlLoading(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest)
  {
    return shouldOverrideUrlLoadingCustom(paramAwWebResourceRequest.url, paramAwWebResourceRequest.isMainFrame, paramAwWebResourceRequest.hasUserGesture, paramAwWebResourceRequest.isRedirect, -1, "");
  }
  
  public boolean shouldOverrideUrlLoadingCustom(String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt, String paramString2)
  {
    SmttServiceProxy.getInstance().checkNeedToUpload(paramString1);
    this.mDoingShouldOverrideUrlLoading = true;
    paramBoolean1 = shouldOverrideUrlLoadingCustomInternal(paramString1, paramBoolean1, paramBoolean2, paramBoolean3, paramInt, paramString2);
    this.mDoingShouldOverrideUrlLoading = false;
    return paramBoolean1;
  }
  
  protected boolean showDefaultJsDialog(final JsPromptResult paramJsPromptResult, int paramInt, String paramString1, String paramString2, String paramString3)
  {
    Activity localActivity = AwContents.activityFromContext(this.mContext);
    if (localActivity == null) {
      return false;
    }
    boolean bool = this.mWebViewProxy.getSettingsExtension().getDayOrNight() ^ true;
    paramJsPromptResult = new JsPromptResult(null)
    {
      public void cancel()
      {
        paramJsPromptResult.cancel();
      }
      
      public void confirm()
      {
        paramJsPromptResult.confirm();
      }
      
      public void confirm(String paramAnonymousString)
      {
        paramJsPromptResult.confirm(paramAnonymousString);
      }
    };
    if ((e.a().z()) && (paramInt != 4))
    {
      new TencentNewJsDialogHelper(paramJsPromptResult, paramInt, paramString1, paramString2, paramString3, bool).showDialog(localActivity);
      return true;
    }
    new TencentJsDialogHelper(paramJsPromptResult, paramInt, paramString1, paramString2, paramString3, bool).showDialog(localActivity);
    return true;
  }
  
  public void showFileChooser(final Callback<String[]> paramCallback, AwContentsClient.FileChooserParamsImpl paramFileChooserParamsImpl)
  {
    showFileChooserInternal(new com.tencent.tbs.core.webkit.ValueCallback()
    {
      public void onReceiveValue(String[] paramAnonymousArrayOfString)
      {
        paramCallback.onResult(paramAnonymousArrayOfString);
      }
    }, paramFileChooserParamsImpl);
  }
  
  /* Error */
  public void showFileChooserBySystem(AwContentsClient.FileChooserParamsImpl paramFileChooserParamsImpl, final com.tencent.tbs.core.webkit.ValueCallback<Uri[]> paramValueCallback, com.tencent.tbs.core.webkit.ValueCallback<String[]> paramValueCallback1)
  {
    // Byte code:
    //   0: new 2019	com/tencent/tbs/core/webkit/adapter/FileChooserParamsAdapter
    //   3: dup
    //   4: aload_1
    //   5: aload_0
    //   6: getfield 338	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter:mWebView	Lcom/tencent/tbs/core/webkit/WebView;
    //   9: invokevirtual 497	com/tencent/tbs/core/webkit/WebView:getContext	()Landroid/content/Context;
    //   12: invokespecial 2022	com/tencent/tbs/core/webkit/adapter/FileChooserParamsAdapter:<init>	(Lorg/chromium/android_webview/AwContentsClient$FileChooserParamsImpl;Landroid/content/Context;)V
    //   15: astore 7
    //   17: aload_0
    //   18: iconst_0
    //   19: putfield 322	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter:mUseFileChooserByTBS	Z
    //   22: aload_0
    //   23: getfield 1230	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter:mWebChromeClient	Lcom/tencent/tbs/core/webkit/WebChromeClient;
    //   26: aload_0
    //   27: getfield 338	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter:mWebView	Lcom/tencent/tbs/core/webkit/WebView;
    //   30: aload_2
    //   31: aload 7
    //   33: invokevirtual 2026	com/tencent/tbs/core/webkit/WebChromeClient:onShowFileChooser	(Lcom/tencent/tbs/core/webkit/WebView;Lcom/tencent/tbs/core/webkit/ValueCallback;Lcom/tencent/tbs/core/webkit/WebChromeClient$FileChooserParams;)Z
    //   36: istore 6
    //   38: iload 6
    //   40: ifeq +11 -> 51
    //   43: return
    //   44: astore 7
    //   46: aload 7
    //   48: invokevirtual 441	java/lang/Throwable:printStackTrace	()V
    //   51: aload_1
    //   52: invokevirtual 2031	org/chromium/android_webview/AwContentsClient$FileChooserParamsImpl:getMode	()I
    //   55: iconst_1
    //   56: iand
    //   57: iconst_1
    //   58: if_icmpne +9 -> 67
    //   61: iconst_1
    //   62: istore 4
    //   64: goto +6 -> 70
    //   67: iconst_0
    //   68: istore 4
    //   70: aload_0
    //   71: getfield 338	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter:mWebView	Lcom/tencent/tbs/core/webkit/WebView;
    //   74: invokevirtual 1253	com/tencent/tbs/core/webkit/WebView:getUrl	()Ljava/lang/String;
    //   77: astore 7
    //   79: iload 4
    //   81: istore 5
    //   83: aload 7
    //   85: ifnull +21 -> 106
    //   88: iload 4
    //   90: istore 5
    //   92: aload 7
    //   94: ldc_w 2033
    //   97: invokevirtual 1011	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   100: ifeq +6 -> 106
    //   103: iconst_1
    //   104: istore 5
    //   106: iload 5
    //   108: ifeq +74 -> 182
    //   111: aload_0
    //   112: invokevirtual 1884	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter:getIX5WebChromeClientExtension	()Lcom/tencent/smtt/export/external/extension/interfaces/IX5WebChromeClientExtension;
    //   115: ifnull +67 -> 182
    //   118: aload_0
    //   119: iconst_0
    //   120: putfield 322	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter:mUseFileChooserByTBS	Z
    //   123: aload_0
    //   124: invokevirtual 1884	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter:getIX5WebChromeClientExtension	()Lcom/tencent/smtt/export/external/extension/interfaces/IX5WebChromeClientExtension;
    //   127: astore 8
    //   129: new 10	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter$10
    //   132: dup
    //   133: aload_0
    //   134: aload_2
    //   135: invokespecial 2034	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter$10:<init>	(Landroid/webview/chromium/tencent/TencentWebViewContentsClientAdapter;Lcom/tencent/tbs/core/webkit/ValueCallback;)V
    //   138: astore 9
    //   140: aload_1
    //   141: invokevirtual 2037	org/chromium/android_webview/AwContentsClient$FileChooserParamsImpl:getAcceptTypesString	()Ljava/lang/String;
    //   144: astore 10
    //   146: aload_1
    //   147: invokevirtual 2040	org/chromium/android_webview/AwContentsClient$FileChooserParamsImpl:isCaptureEnabled	()Z
    //   150: ifeq +116 -> 266
    //   153: ldc_w 2042
    //   156: astore 7
    //   158: goto +3 -> 161
    //   161: aload 8
    //   163: aload 9
    //   165: aload 10
    //   167: aload 7
    //   169: invokeinterface 2046 4 0
    //   174: return
    //   175: astore 7
    //   177: aload 7
    //   179: invokevirtual 441	java/lang/Throwable:printStackTrace	()V
    //   182: aload_0
    //   183: getfield 1230	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter:mWebChromeClient	Lcom/tencent/tbs/core/webkit/WebChromeClient;
    //   186: ifnonnull +11 -> 197
    //   189: aload_3
    //   190: aconst_null
    //   191: invokeinterface 1945 2 0
    //   196: return
    //   197: aload_0
    //   198: iconst_0
    //   199: putfield 322	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter:mUseFileChooserByTBS	Z
    //   202: aload_0
    //   203: getfield 1230	android/webview/chromium/tencent/TencentWebViewContentsClientAdapter:mWebChromeClient	Lcom/tencent/tbs/core/webkit/WebChromeClient;
    //   206: checkcast 1958	com/tencent/tbs/core/webkit/tencent/TencentWebChromeClient
    //   209: astore_3
    //   210: aload_1
    //   211: invokevirtual 2037	org/chromium/android_webview/AwContentsClient$FileChooserParamsImpl:getAcceptTypesString	()Ljava/lang/String;
    //   214: astore 7
    //   216: aload_1
    //   217: invokevirtual 2040	org/chromium/android_webview/AwContentsClient$FileChooserParamsImpl:isCaptureEnabled	()Z
    //   220: ifeq +54 -> 274
    //   223: ldc_w 2042
    //   226: astore_1
    //   227: goto +3 -> 230
    //   230: aload_3
    //   231: aload_2
    //   232: aload 7
    //   234: aload_1
    //   235: iconst_0
    //   236: invokevirtual 2049	com/tencent/tbs/core/webkit/tencent/TencentWebChromeClient:openFileChooser	(Lcom/tencent/tbs/core/webkit/ValueCallback;Ljava/lang/String;Ljava/lang/String;Z)V
    //   239: goto +12 -> 251
    //   242: astore_1
    //   243: goto +15 -> 258
    //   246: astore_1
    //   247: aload_1
    //   248: invokevirtual 821	java/lang/Exception:printStackTrace	()V
    //   251: ldc_w 2051
    //   254: invokestatic 1135	org/chromium/base/TraceEvent:end	(Ljava/lang/String;)V
    //   257: return
    //   258: ldc_w 2051
    //   261: invokestatic 1135	org/chromium/base/TraceEvent:end	(Ljava/lang/String;)V
    //   264: aload_1
    //   265: athrow
    //   266: ldc_w 518
    //   269: astore 7
    //   271: goto -110 -> 161
    //   274: ldc_w 518
    //   277: astore_1
    //   278: goto -48 -> 230
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	281	0	this	TencentWebViewContentsClientAdapter
    //   0	281	1	paramFileChooserParamsImpl	AwContentsClient.FileChooserParamsImpl
    //   0	281	2	paramValueCallback	com.tencent.tbs.core.webkit.ValueCallback<Uri[]>
    //   0	281	3	paramValueCallback1	com.tencent.tbs.core.webkit.ValueCallback<String[]>
    //   62	27	4	i	int
    //   81	26	5	j	int
    //   36	3	6	bool	boolean
    //   15	17	7	localFileChooserParamsAdapter	FileChooserParamsAdapter
    //   44	3	7	localThrowable1	Throwable
    //   77	91	7	str1	String
    //   175	3	7	localThrowable2	Throwable
    //   214	56	7	str2	String
    //   127	35	8	localIX5WebChromeClientExtension	IX5WebChromeClientExtension
    //   138	26	9	local10	10
    //   144	22	10	str3	String
    // Exception table:
    //   from	to	target	type
    //   0	38	44	java/lang/Throwable
    //   118	153	175	java/lang/Throwable
    //   161	174	175	java/lang/Throwable
    //   197	223	242	finally
    //   230	239	242	finally
    //   247	251	242	finally
    //   197	223	246	java/lang/Exception
    //   230	239	246	java/lang/Exception
  }
  
  public void showFileChooserByTBS(AwContentsClient.FileChooserParamsImpl paramFileChooserParamsImpl, com.tencent.tbs.core.webkit.ValueCallback<Uri[]> paramValueCallback, com.tencent.tbs.core.webkit.ValueCallback<String[]> paramValueCallback1)
  {
    label101:
    label104:
    for (;;)
    {
      try
      {
        if ((paramFileChooserParamsImpl.getAcceptTypesString().contains("video")) || (paramFileChooserParamsImpl.getAcceptTypesString().contains("audio"))) {
          break label104;
        }
        if (paramFileChooserParamsImpl.isCaptureEnabled()) {
          break label101;
        }
        paramFileChooserParamsImpl = new FileChooserParamsAdapter(paramFileChooserParamsImpl, this.mWebView.getContext());
        this.mUseFileChooserByTBS = true;
        f.a(((TencentWebViewProxy.InnerWebView)this.mWebView).getOuterProxy(), paramValueCallback, paramFileChooserParamsImpl, null, null, null, null, null, null, null, null);
        return;
      }
      catch (Exception paramFileChooserParamsImpl)
      {
        paramFileChooserParamsImpl.printStackTrace();
        return;
      }
      setFromX5FileChooserUI(true);
      showFileChooserBySystem(paramFileChooserParamsImpl, paramValueCallback, paramValueCallback1);
      return;
    }
  }
  
  public void showFileChooserInternal(final com.tencent.tbs.core.webkit.ValueCallback<String[]> paramValueCallback, AwContentsClient.FileChooserParamsImpl paramFileChooserParamsImpl)
  {
    for (;;)
    {
      try
      {
        TraceEvent.begin("WebViewContentsClientAdapter.showFileChooser");
        Object localObject = this.mWebChromeClient;
        Intent localIntent = null;
        if (localObject == null)
        {
          paramValueCallback.onReceiveValue(null);
          return;
        }
        localObject = new com.tencent.tbs.core.webkit.ValueCallback()
        {
          private boolean mCompleted;
          
          public void onReceiveValue(Uri[] paramAnonymousArrayOfUri)
          {
            if (!this.mCompleted)
            {
              if (TencentWebViewContentsClientAdapter.this.mRequestedOrientation != -10)
              {
                if (TencentWebViewContentsClientAdapter.this.getActivity() != null) {
                  TencentWebViewContentsClientAdapter.this.getActivity().setRequestedOrientation(TencentWebViewContentsClientAdapter.this.mRequestedOrientation);
                }
                TencentWebViewContentsClientAdapter.access$1802(TencentWebViewContentsClientAdapter.this, -10);
              }
              this.mCompleted = true;
              String[] arrayOfString = null;
              Object localObject = arrayOfString;
              if (paramAnonymousArrayOfUri != null)
              {
                localObject = arrayOfString;
                if (paramAnonymousArrayOfUri.length > 0)
                {
                  localObject = arrayOfString;
                  if (paramAnonymousArrayOfUri[0] != null)
                  {
                    arrayOfString = new String[paramAnonymousArrayOfUri.length];
                    int i = 0;
                    while (i < paramAnonymousArrayOfUri.length)
                    {
                      localObject = "";
                      if (TextUtils.isEmpty(paramAnonymousArrayOfUri[i].getScheme())) {
                        localObject = "file://";
                      }
                      StringBuilder localStringBuilder = new StringBuilder();
                      localStringBuilder.append((String)localObject);
                      localStringBuilder.append(paramAnonymousArrayOfUri[i].toString());
                      arrayOfString[i] = localStringBuilder.toString();
                      if ("file".equalsIgnoreCase(Uri.parse(arrayOfString[i]).getScheme())) {
                        arrayOfString[i] = UrlUtils.decode(arrayOfString[i]);
                      }
                      i += 1;
                    }
                    if (TencentWebViewContentsClientAdapter.this.mUseFileChooserByTBS)
                    {
                      paramAnonymousArrayOfUri = new HashMap();
                      paramAnonymousArrayOfUri.put("coreversion", ContextHolder.getInstance().getTbsCoreVersion());
                      paramAnonymousArrayOfUri.put("packageName", TencentWebViewContentsClientAdapter.this.mContext.getApplicationInfo().packageName);
                      if (TencentWebViewContentsClientAdapter.this.mWebView.getUrl() != null) {
                        paramAnonymousArrayOfUri.put("domain", TencentURLUtil.getHost(TencentWebViewContentsClientAdapter.this.mWebView.getUrl()));
                      }
                      SmttServiceProxy.getInstance().upLoadToBeacon("CH0009001", paramAnonymousArrayOfUri);
                      localObject = arrayOfString;
                    }
                    else if (TencentWebViewContentsClientAdapter.this.mFromX5FileChooserUI)
                    {
                      paramAnonymousArrayOfUri = new HashMap();
                      paramAnonymousArrayOfUri.put("coreversion", ContextHolder.getInstance().getTbsCoreVersion());
                      SmttServiceProxy.getInstance().upLoadToBeacon("CH0020", paramAnonymousArrayOfUri);
                      localObject = arrayOfString;
                    }
                    else
                    {
                      paramAnonymousArrayOfUri = new HashMap();
                      paramAnonymousArrayOfUri.put("coreversion", ContextHolder.getInstance().getTbsCoreVersion());
                      SmttServiceProxy.getInstance().upLoadToBeacon("CH0021", paramAnonymousArrayOfUri);
                      localObject = arrayOfString;
                    }
                  }
                }
              }
              paramValueCallback.onReceiveValue(localObject);
              TencentWebViewContentsClientAdapter.access$2002(TencentWebViewContentsClientAdapter.this, false);
              return;
            }
            throw new IllegalStateException("showFileChooser result was already called");
          }
        };
        if (!TextUtils.isEmpty(this.mWebView.getUrl()))
        {
          bool1 = SmttServiceProxy.getInstance().isInTBSFileChooserBlackDomainList(this.mWebView.getUrl());
          if (this.mContext.checkPermission("android.permission.CAMERA", Process.myPid(), Process.myUid()) == 0) {
            break label702;
          }
          i = 0;
          boolean bool2 = SmttServiceProxy.getInstance().isFileChooserTBSEnabled(this.mWebView.getContext());
          boolean bool3 = SmttServiceProxy.getInstance().isInMiniProgram(((TencentWebViewProxy.InnerWebView)this.mWebView).getOuterProxy());
          if ((bool2) && (!bool1) && (i != 0) && (!bool3) && (!paramFileChooserParamsImpl.getAcceptTypesString().contains("video")) && (!paramFileChooserParamsImpl.getAcceptTypesString().contains("audio"))) {
            if (!paramFileChooserParamsImpl.isCaptureEnabled())
            {
              if ((this.mWebView.getParent() instanceof FrameLayout))
              {
                paramFileChooserParamsImpl.getAcceptTypes();
                this.mFileChooserFirstScreenView = new FileChooserFirstScreenView(this.mContext, this, paramFileChooserParamsImpl, (com.tencent.tbs.core.webkit.ValueCallback)localObject, paramValueCallback, true, getActivity());
                if (getActivity() != null)
                {
                  this.mRequestedOrientation = getActivity().getRequestedOrientation();
                  if (this.mWebView.getWidth() > this.mWebView.getHeight()) {
                    getActivity().setRequestedOrientation(0);
                  } else {
                    getActivity().setRequestedOrientation(1);
                  }
                }
                paramValueCallback = new FrameLayout.LayoutParams(-1, dip2px(192));
                paramValueCallback.bottomMargin = 0;
                paramValueCallback.gravity = 80;
                this.mFirstScreenViewBackGround = new FirstScreenViewBackGround(this.mContext, (com.tencent.tbs.core.webkit.ValueCallback)localObject, this);
                paramFileChooserParamsImpl = new FrameLayout.LayoutParams(-1, -1);
                paramFileChooserParamsImpl.topMargin = 0;
                paramFileChooserParamsImpl.gravity = 48;
                this.mFirstScreenViewBackGround.setBackgroundColor(-7829368);
                this.mFirstScreenViewBackGround.getBackground().setAlpha(170);
                ((FrameLayout)this.mWebView.getParent()).addView(this.mFirstScreenViewBackGround, paramFileChooserParamsImpl);
                ((FrameLayout)this.mWebView.getParent()).addView(this.mFileChooserFirstScreenView, paramValueCallback);
                ((TencentWebViewProxy.InnerWebView)this.mWebView).getOuterProxy().getRealWebView().setFileChooserFirstScreenView(this.mFileChooserFirstScreenView);
              }
            }
            else if ((paramFileChooserParamsImpl.getAcceptTypesString().contains("image")) && (paramFileChooserParamsImpl.isCaptureEnabled()))
            {
              paramFileChooserParamsImpl = this.mContext.getApplicationInfo().packageName;
              if ("com.tencent.mm".equals(paramFileChooserParamsImpl))
              {
                paramValueCallback = new StringBuilder();
                paramValueCallback.append(Environment.getExternalStorageDirectory().getAbsolutePath());
                paramValueCallback.append("/tencent/MicroMsg/WeiXin/");
                paramValueCallback = new File(paramValueCallback.toString());
              }
              else
              {
                paramValueCallback = localIntent;
                if ("com.tencent.mobileqq".equals(paramFileChooserParamsImpl))
                {
                  paramValueCallback = new StringBuilder();
                  paramValueCallback.append(Environment.getExternalStorageDirectory().getAbsolutePath());
                  paramValueCallback.append("/tencent/MobileQQ/photo/");
                  paramValueCallback = new File(paramValueCallback.toString());
                }
              }
              if ((paramValueCallback != null) && (!paramValueCallback.exists())) {
                paramValueCallback.mkdirs();
              }
              paramFileChooserParamsImpl = new StringBuilder();
              paramFileChooserParamsImpl.append("");
              paramFileChooserParamsImpl.append(System.currentTimeMillis());
              paramFileChooserParamsImpl.append(".jpg");
              paramValueCallback = new File(paramValueCallback, paramFileChooserParamsImpl.toString());
              paramFileChooserParamsImpl = paramValueCallback.getAbsolutePath();
              localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
              localIntent.addCategory("android.intent.category.DEFAULT");
              localIntent.putExtra("output", Uri.fromFile(paramValueCallback));
              this.mContext.startActivity(localIntent);
              uploadImageFromSystemCamera(paramFileChooserParamsImpl, (com.tencent.tbs.core.webkit.ValueCallback)localObject);
              return;
            }
          }
          setFromX5FileChooserUI(false);
          showFileChooserBySystem(paramFileChooserParamsImpl, (com.tencent.tbs.core.webkit.ValueCallback)localObject, paramValueCallback);
          return;
        }
      }
      catch (Exception paramValueCallback)
      {
        return;
      }
      boolean bool1 = false;
      continue;
      label702:
      int i = 1;
    }
  }
  
  public boolean skipPageFinishEventForErrAborted(String paramString)
  {
    TencentWebViewProxy localTencentWebViewProxy = this.mWebViewProxy;
    if ((localTencentWebViewProxy != null) && (localTencentWebViewProxy.getSettingsExtension() != null) && (this.mWebViewProxy.getSettingsExtension().skipPageFinishEventForAborted())) {
      return this.mShouldOverrideUrlLoadingListForLoadFaild.contains(paramString);
    }
    return false;
  }
  
  public boolean skipPageFinishEventForFailLoad(String paramString)
  {
    TencentWebViewProxy localTencentWebViewProxy = this.mWebViewProxy;
    boolean bool = false;
    if ((localTencentWebViewProxy != null) && (localTencentWebViewProxy.getSettingsExtension() != null) && (this.mWebViewProxy.getSettingsExtension().skipPageFinishEventForOverrideUrlLoading()))
    {
      if ((this.mDoingShouldOverrideUrlLoading) || (this.mShouldOverrideUrlLoadingList.contains(paramString))) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  public void updateScrollState(AwScrollOffsetManager paramAwScrollOffsetManager, int paramInt1, int paramInt2)
  {
    this.mWebViewProxy.notifyUpdateScrollState(paramAwScrollOffsetManager, paramInt1, paramInt2);
  }
  
  public void uploadImageFromSystemCamera(String paramString, com.tencent.tbs.core.webkit.ValueCallback<Uri[]> paramValueCallback)
  {
    ((TencentWebViewProxy.InnerWebView)this.mWebView).getOuterProxy().getRealWebView().uploadImageFromCamera(paramString, paramValueCallback);
  }
  
  @TargetApi(23)
  private static class WebResourceErrorImpl
    extends WebResourceError
  {
    private final org.chromium.android_webview.AwContentsClient.AwWebResourceError mError;
    
    public WebResourceErrorImpl(org.chromium.android_webview.AwContentsClient.AwWebResourceError paramAwWebResourceError)
    {
      this.mError = paramAwWebResourceError;
    }
    
    public CharSequence getDescription()
    {
      return this.mError.description;
    }
    
    public int getErrorCode()
    {
      return this.mError.errorCode;
    }
  }
  
  private static class WebResourceRequestImpl
    implements com.tencent.smtt.export.external.interfaces.WebResourceRequest, com.tencent.tbs.core.webkit.WebResourceRequest
  {
    private final AwContentsClient.AwWebResourceRequest mRequest;
    
    public WebResourceRequestImpl(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest)
    {
      this.mRequest = paramAwWebResourceRequest;
    }
    
    public String getMethod()
    {
      return this.mRequest.method;
    }
    
    public Map<String, String> getRequestHeaders()
    {
      return this.mRequest.requestHeaders;
    }
    
    public Uri getUrl()
    {
      return Uri.parse(this.mRequest.url);
    }
    
    public boolean hasGesture()
    {
      return this.mRequest.hasUserGesture;
    }
    
    public boolean isForMainFrame()
    {
      return this.mRequest.isMainFrame;
    }
    
    public boolean isRedirect()
    {
      return this.mRequest.isRedirect;
    }
  }
  
  private class activeQBReceiver
    extends BroadcastReceiver
  {
    private activeQBReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ((paramIntent != null) && (paramIntent.getAction() != null) && (paramIntent.getAction().equals("com.tencent.mtt.ServiceDispatch.feedback")))
      {
        TencentWebViewContentsClientAdapter.access$102(TencentWebViewContentsClientAdapter.this, true);
        if (TencentWebViewContentsClientAdapter.this.mCheckQBActiveTimeCostAfterTBSDo)
        {
          TencentWebViewContentsClientAdapter.access$302(TencentWebViewContentsClientAdapter.this, System.currentTimeMillis());
          long l = TencentWebViewContentsClientAdapter.this.mTimeQBActiveFeedback - TencentWebViewContentsClientAdapter.this.mTimeActiveQBBack;
          if (l < 0L) {
            SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHRERROR");
          } else if (l < 1000L) {
            SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHR1000");
          } else if (l < 3000L) {
            SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHR3000");
          } else if (l < 5000L) {
            SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHR5000");
          } else if (l < 9000L) {
            SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHR9000");
          } else if (l < 19000L) {
            SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHR19000");
          } else if (l < 29000L) {
            SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHR29000");
          } else {
            SmttServiceProxy.getInstance().userBehaviorStatistics("TBSAQBHRMAX");
          }
          TencentWebViewContentsClientAdapter.access$202(TencentWebViewContentsClientAdapter.this, false);
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentWebViewContentsClientAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */