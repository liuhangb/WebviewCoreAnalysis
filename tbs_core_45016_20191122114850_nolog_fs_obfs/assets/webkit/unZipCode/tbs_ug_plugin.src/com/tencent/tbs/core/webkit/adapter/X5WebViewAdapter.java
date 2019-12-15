package com.tencent.tbs.core.webkit.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Interpolator;
import android.graphics.Interpolator.Result;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.webkit.ValueCallback;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.smtt.d.b.a;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.IX5ScrollListener;
import com.tencent.smtt.export.external.interfaces.IX5WebHistoryItem;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult.EditableData;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult.ImageData;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.ImageInfo;
import com.tencent.smtt.export.internal.interfaces.IAdSettings;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.smtt.jsApi.export.OpenJsApiBridge;
import com.tencent.smtt.net.AwNetworkUtils;
import com.tencent.smtt.net.SWOfflineFramework;
import com.tencent.smtt.util.MttLog;
import com.tencent.smtt.util.f;
import com.tencent.smtt.util.m;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.WebSettingsExtension.a;
import com.tencent.smtt.webkit.WebViewList;
import com.tencent.smtt.webkit.WebViewProviderExtension;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.h5video.d;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.partner.ad.AdWebViewController;
import com.tencent.tbs.core.partner.menu.X5ActionMode;
import com.tencent.tbs.core.partner.menu.X5LongClickPopMenu;
import com.tencent.tbs.core.partner.menu.X5Menu;
import com.tencent.tbs.core.partner.menu.X5Selection;
import com.tencent.tbs.core.partner.menu.X5Selection.ISelectionHost;
import com.tencent.tbs.core.webkit.SDKMttTraceEvent;
import com.tencent.tbs.core.webkit.tencent.TencentCacheManager;
import com.tencent.tbs.core.webkit.tencent.TencentCacheManager.CacheResult;
import com.tencent.tbs.core.webkit.tencent.TencentURLUtil;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProvider;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.io.BufferedWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.chromium.base.annotations.UsedByReflection;
import org.chromium.tencent.SharedResource;
import org.chromium.tencent.net.TencentProxyChangeListener;
import org.json.JSONException;
import org.json.JSONObject;

public class X5WebViewAdapter
  extends TencentWebViewProxy
  implements IX5WebView, X5Selection.ISelectionHost
{
  static final String KEY_TBSCORE_VERSION_STRING = "tbscore_version_string";
  static final String KEY_TBS_LOCAL_V = "tbs_local_version";
  private static final String TBS_CORE_VER = "tbs_core_version";
  static final String TBS_EXTENSION_CFG_FILE = "tbs_extension.conf";
  private static final String TBS_FILE_CONF = "tbs.conf";
  static final String TBS_FOLDER_NAME = "tbs";
  static final String TBS_PRIVATE_FOLDER_NAME = "core_private";
  static final String TBS_SHARE_FOLDER_NAME = "core_share";
  static final String TBS_SHARE_NAME = "share";
  private static final int WALLED_GARDEN_SOCKET_TIMEOUT_MS = 4000;
  private static boolean isWeappWebviewInited;
  private static int mCoreVersion;
  private static int mCountOnLongClick = 0;
  private static Drawable mSdkScrollBarDrawable;
  private static Drawable mSdkVerticalTrackDrawable;
  private static Drawable mSdkVerticalTrackNightDrawable;
  private static String mTbsVersion;
  private static String mUrl;
  private static boolean mUseTbsTrackDrawable;
  private static float sDensity;
  private static Handler sHandler = new Handler(Looper.getMainLooper());
  private final int MSG_LOADING_HIDE = 1019;
  private final int MSG_LOADING_SHOW = 1017;
  private final int MSG_LOADING_UPDATE = 1018;
  final Handler handler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 1019: 
        X5WebViewAdapter.access$102(X5WebViewAdapter.this, 1019);
        X5WebViewAdapter.this.hideLoading();
        return;
      case 1018: 
        X5WebViewAdapter.access$102(X5WebViewAdapter.this, 1018);
        X5WebViewAdapter.this.updateLoading();
        return;
      }
      X5WebViewAdapter.access$102(X5WebViewAdapter.this, 1017);
      X5WebViewAdapter.this.showLoading();
    }
  };
  public boolean isOunterClientForUIEventSetted = false;
  private Bitmap mBmpFromCore = null;
  private int mClickImageCount = 0;
  private int mEventType = -1;
  private boolean mForceLongClickQuickCopy = false;
  private boolean mFromLongClick = false;
  private boolean mHasForceDebugtbs = false;
  private String mImageBase64 = null;
  private boolean mImageBrowserInShowing = false;
  private String mImageQueryClass = null;
  private boolean mIsMiniQB;
  private int mLastGetImageQueryClass = 0;
  private Bitmap mLastHitTestBmp = null;
  private int mLoadingStatus = 1019;
  private LongClickImageFirstView mLoadingView = null;
  private boolean mLongClickPopupMenuInited = false;
  private int mLongPressTextExtensionMenu = 0;
  private int mMainX5WebviewMaxOverScrollY = 0;
  private int mMiniProgramFlag = -1;
  private String mMiniProgramPkgName = null;
  private int mMiniQBSourcePosID = 0;
  private com.tencent.smtt.d.b mScrollCache;
  private IX5ScrollListener mScrollListener;
  private X5Selection mSelectionListener;
  private long mTimePointLongClickBegin = 0L;
  private View mTitleView;
  private X5ActionMode mX5ActionMode = null;
  private boolean mbQuickSelectCopy = false;
  
  static
  {
    mCoreVersion = 0;
    mTbsVersion = "";
    mSdkScrollBarDrawable = null;
    mSdkVerticalTrackDrawable = null;
    mSdkVerticalTrackNightDrawable = null;
    mUseTbsTrackDrawable = true;
    isWeappWebviewInited = false;
    mUrl = "";
    sDensity = -1.0F;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public X5WebViewAdapter(Context paramContext, boolean paramBoolean)
  {
    super(paramContext, null, 0, null, paramBoolean);
    SDKMttTraceEvent.begin("X5WebViewAdapter");
    SharedResource.getPerformanceData().put(SharedResource.X5_WEBVIEW_INIT_BEGIN, String.valueOf(System.currentTimeMillis()));
    this.mScrollCache = new com.tencent.smtt.d.b(ViewConfiguration.get(paramContext), getView());
    this.mScrollCache.jdField_a_of_type_ComTencentSmttDA = new com.tencent.smtt.d.a();
    if (ContextHolder.getInstance().isTbsDevelopMode())
    {
      localObject2 = mSdkScrollBarDrawable;
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = com.tencent.smtt.listbox.a.a("theme_scrollbar_horizontal_fg_normal.9.png");
      }
      mSdkScrollBarDrawable = (Drawable)localObject1;
      localObject1 = mSdkScrollBarDrawable;
      if (localObject1 != null)
      {
        setHorizontalScrollBarDrawable(new com.tencent.smtt.browser.x5.b(false, (Drawable)localObject1, 0, SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_control_scrollbar_width", "dimen")), 100));
        setVerticalScrollBarDrawable(new com.tencent.smtt.browser.x5.b(true, mSdkScrollBarDrawable, 0, SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_control_scrollbar_width", "dimen")), 100));
      }
      localObject2 = mSdkVerticalTrackDrawable;
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = SmttResource.getDrawable("x5_fast_scroller");
      }
      mSdkVerticalTrackDrawable = (Drawable)localObject1;
      if (mSdkVerticalTrackDrawable != null) {
        this.mScrollCache.jdField_a_of_type_ComTencentSmttDA.b(mSdkVerticalTrackDrawable);
      }
    }
    setScrollBarFadingEnabled(true);
    setHorizontalScrollBarEnabled(true);
    setVerticalScrollBarEnabled(true);
    if ((ContextHolder.getInstance().isTbsDevelopMode()) && (getSettingsExtension().getUseTbsDefaultSettings()))
    {
      setScrollBarDefaultDelayBeforeFade(1000);
      if ((!getSettingsExtension().getDayOrNight()) && (getSettingsExtension().getPageSolarEnableFlag() == true)) {
        getView().setBackgroundColor(-16777216);
      } else {
        getView().setBackgroundColor(-1);
      }
      getSettings().setSupportMultipleWindows(false);
      getSettings().setDatabaseEnabled(false);
      getSettings().setDomStorageEnabled(false);
      getSettings().setLoadWithOverviewMode(false);
      getSettings().setJavaScriptEnabled(false);
      getSettings().setUseWideViewPort(false);
      getSettings().setBuiltInZoomControls(false);
      getSettings().setAppCacheEnabled(false);
      if (paramContext.getPackageName().equals("com.tencent.mm"))
      {
        getSettings().setAllowFileAccessFromFileURLs(true);
        getSettings().setAllowUniversalAccessFromFileURLs(true);
      }
      else
      {
        getSettings().setAllowFileAccessFromFileURLs(false);
        getSettings().setAllowUniversalAccessFromFileURLs(false);
      }
      getSettingsExtension().setImgAsDownloadFile(false);
      getSettings().setDisplayZoomControls(false);
    }
    Object localObject1 = "UTF-8";
    Object localObject2 = Locale.getDefault();
    if ((!((Locale)localObject2).equals(Locale.CHINESE)) && (!((Locale)localObject2).equals(Locale.CHINA)) && (!((Locale)localObject2).equals(Locale.SIMPLIFIED_CHINESE)))
    {
      if ((!((Locale)localObject2).equals(Locale.TAIWAN)) && (!((Locale)localObject2).equals(Locale.TRADITIONAL_CHINESE)))
      {
        if ((((Locale)localObject2).equals(Locale.JAPAN)) || (((Locale)localObject2).equals(Locale.JAPANESE))) {
          localObject1 = "ISO-2022-JP";
        }
      }
      else {
        localObject1 = "BIG5";
      }
    }
    else {
      localObject1 = "GB2312";
    }
    getSettings().setDefaultTextEncodingName((String)localObject1);
    WebViewList.addWebView(this);
    if (paramContext.getPackageName().startsWith("com.tencent.mobileqq")) {
      TencentProxyChangeListener.getInstance().setProxy(SmttServiceProxy.getInstance().getHttpProxyAddressStringAsSystemProxy());
    }
    SDKMttTraceEvent.end("X5WebViewAdapter");
    SharedResource.getPerformanceData().put(SharedResource.X5_WEBVIEW_INIT_END, String.valueOf(System.currentTimeMillis()));
    sHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        SWOfflineFramework.getInstance().start(X5WebViewAdapter.this.mContext);
      }
    }, 2000L);
  }
  
  private void adjustTitleViewPosition()
  {
    View localView = this.mTitleView;
    if (localView != null)
    {
      localView.offsetLeftAndRight(getRealWebView().getScrollX() - this.mTitleView.getLeft());
      int i = getRealWebView().getScrollY();
      if (i < 0) {
        i -= this.mTitleView.getTop();
      } else {
        i = -this.mTitleView.getTop();
      }
      this.mTitleView.offsetTopAndBottom(i);
    }
  }
  
  private int canGetImageQueryClass(String paramString)
  {
    Object localObject3 = null;
    this.mImageQueryClass = null;
    Object localObject1;
    try
    {
      paramString = new JSONObject(paramString).getString("imageRet");
      if (paramString != null)
      {
        paramString = new JSONObject(paramString);
        localObject1 = paramString.getString("sClass");
        try
        {
          paramString = paramString.getString("iClass");
        }
        catch (JSONException localJSONException1)
        {
          paramString = (String)localObject1;
          break label71;
        }
      }
      else
      {
        localObject1 = null;
        paramString = (String)localObject1;
      }
    }
    catch (JSONException localJSONException2)
    {
      paramString = null;
      label71:
      localJSONException2.printStackTrace();
      Object localObject2 = null;
      localObject1 = paramString;
      paramString = (String)localObject2;
    }
    if ((paramString != null) && (paramString.contains("10003")))
    {
      localObject1 = getWebViewClientExtension();
      paramString = (String)localObject3;
      if (localObject1 != null) {
        paramString = ((IX5WebViewClientExtension)localObject1).onMiscCallBack("handleImage", new Bundle());
      }
      if (paramString == null)
      {
        this.mImageQueryClass = "二维码";
        this.mLastGetImageQueryClass = 0;
        return this.mLastGetImageQueryClass;
      }
      this.mLastGetImageQueryClass = 1;
      return this.mLastGetImageQueryClass;
    }
    if (localObject1 != null)
    {
      this.mImageQueryClass = ((String)localObject1);
      this.mLastGetImageQueryClass = 0;
      return this.mLastGetImageQueryClass;
    }
    this.mLastGetImageQueryClass = -1;
    return this.mLastGetImageQueryClass;
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
  
  public static Point getLocationOnScreen(View paramView, Point paramPoint)
  {
    if (paramPoint == null) {
      return null;
    }
    if (paramView == null) {
      return paramPoint;
    }
    int[] arrayOfInt = new int[2];
    paramView.getLocationOnScreen(arrayOfInt);
    return new Point(arrayOfInt[0] + paramPoint.x, arrayOfInt[1] + paramPoint.y);
  }
  
  private static byte[] getMD5(byte[] paramArrayOfByte)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      paramArrayOfByte = localMessageDigest.digest();
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      for (;;) {}
    }
    return null;
  }
  
  private static String getSig(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    paramString4 = Base64.encodeToString(getMD5(paramString4.getBytes()), 2);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append("\n");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("\n");
    localStringBuilder.append(paramString3);
    localStringBuilder.append("\n");
    localStringBuilder.append(paramString4);
    paramString1 = localStringBuilder.toString();
    try
    {
      paramString1 = hmacSHA1Encrypt(paramString1.getBytes("UTF-8"), paramString5.getBytes("UTF-8"));
    }
    catch (UnsupportedEncodingException paramString1)
    {
      paramString1.printStackTrace();
    }
    catch (NoSuchAlgorithmException paramString1)
    {
      paramString1.printStackTrace();
    }
    catch (InvalidKeyException paramString1)
    {
      paramString1.printStackTrace();
    }
    paramString1 = null;
    return Base64.encodeToString(paramString1, 2);
  }
  
  private String getUrl(IX5WebViewBase.HitTestResult paramHitTestResult)
  {
    int i = paramHitTestResult.getType();
    if (i != 5)
    {
      switch (i)
      {
      default: 
        return null;
      }
      return paramHitTestResult.getExtra();
    }
    paramHitTestResult = paramHitTestResult.getData();
    if ((paramHitTestResult instanceof IX5WebViewBase.HitTestResult.ImageData)) {
      return ((IX5WebViewBase.HitTestResult.ImageData)paramHitTestResult).mPicUrl;
    }
    return null;
  }
  
  private void hideLoading()
  {
    LongClickImageFirstView localLongClickImageFirstView = this.mLoadingView;
    if (localLongClickImageFirstView != null)
    {
      localLongClickImageFirstView.setVisibility(8);
      this.mLoadingView = null;
    }
  }
  
  private static byte[] hmacSHA1Encrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws InvalidKeyException, NoSuchAlgorithmException
  {
    paramArrayOfByte2 = new SecretKeySpec(paramArrayOfByte2, "HmacSHA1");
    Mac localMac = Mac.getInstance("HmacSHA1");
    localMac.init(paramArrayOfByte2);
    return localMac.doFinal(paramArrayOfByte1);
  }
  
  /* Error */
  private void imageQuery(Bundle paramBundle)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: ldc_w 747
    //   5: invokevirtual 748	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   8: putfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   11: aload_0
    //   12: getfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   15: invokevirtual 751	java/lang/String:length	()I
    //   18: ldc_w 752
    //   21: if_icmple +14 -> 35
    //   24: aload_0
    //   25: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   28: sipush 1017
    //   31: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   34: pop
    //   35: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   38: invokevirtual 759	com/tencent/smtt/webkit/service/SmttServiceProxy:getGUID	()Ljava/lang/String;
    //   41: astore_1
    //   42: aload_1
    //   43: ifnull +13 -> 56
    //   46: aload_1
    //   47: astore 7
    //   49: aload_1
    //   50: invokevirtual 762	java/lang/String:isEmpty	()Z
    //   53: ifeq +8 -> 61
    //   56: ldc_w 764
    //   59: astore 7
    //   61: ldc_w 766
    //   64: aload 7
    //   66: ldc_w 768
    //   69: aload_0
    //   70: getfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   73: ldc_w 770
    //   76: invokestatic 772	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:getSig	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   79: astore_3
    //   80: new 774	java/net/URL
    //   83: dup
    //   84: ldc_w 776
    //   87: invokespecial 777	java/net/URL:<init>	(Ljava/lang/String;)V
    //   90: astore 9
    //   92: aload 9
    //   94: invokevirtual 781	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   97: checkcast 783	java/net/HttpURLConnection
    //   100: astore_1
    //   101: aload_1
    //   102: astore 4
    //   104: aload_1
    //   105: astore 5
    //   107: aload_1
    //   108: astore 6
    //   110: aload_1
    //   111: sipush 4000
    //   114: invokevirtual 786	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   117: aload_1
    //   118: astore 4
    //   120: aload_1
    //   121: astore 5
    //   123: aload_1
    //   124: astore 6
    //   126: aload_1
    //   127: sipush 4000
    //   130: invokevirtual 789	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   133: aload_1
    //   134: astore 4
    //   136: aload_1
    //   137: astore 5
    //   139: aload_1
    //   140: astore 6
    //   142: aload_1
    //   143: ldc_w 791
    //   146: invokevirtual 794	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   149: aload_1
    //   150: astore 4
    //   152: aload_1
    //   153: astore 5
    //   155: aload_1
    //   156: astore 6
    //   158: aload_1
    //   159: ldc_w 796
    //   162: ldc_w 798
    //   165: invokevirtual 802	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   168: aload_1
    //   169: astore 4
    //   171: aload_1
    //   172: astore 5
    //   174: aload_1
    //   175: astore 6
    //   177: aload_1
    //   178: iconst_1
    //   179: invokevirtual 805	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   182: aload_1
    //   183: astore 4
    //   185: aload_1
    //   186: astore 5
    //   188: aload_1
    //   189: astore 6
    //   191: aload_1
    //   192: iconst_1
    //   193: invokevirtual 808	java/net/HttpURLConnection:setDoInput	(Z)V
    //   196: aload_1
    //   197: astore 4
    //   199: aload_1
    //   200: astore 5
    //   202: aload_1
    //   203: astore 6
    //   205: aload_1
    //   206: iconst_0
    //   207: invokevirtual 811	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   210: aload_1
    //   211: astore 4
    //   213: aload_1
    //   214: astore 5
    //   216: aload_1
    //   217: astore 6
    //   219: aload_1
    //   220: invokevirtual 814	java/net/HttpURLConnection:connect	()V
    //   223: aload_1
    //   224: astore 4
    //   226: aload_1
    //   227: astore 5
    //   229: aload_1
    //   230: astore 6
    //   232: aload_3
    //   233: ldc_w 414
    //   236: invokestatic 820	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   239: astore 8
    //   241: aload 8
    //   243: astore_3
    //   244: goto +19 -> 263
    //   247: astore 8
    //   249: aload_1
    //   250: astore 4
    //   252: aload_1
    //   253: astore 5
    //   255: aload_1
    //   256: astore 6
    //   258: aload 8
    //   260: invokevirtual 695	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   263: aload_1
    //   264: astore 4
    //   266: aload_1
    //   267: astore 5
    //   269: aload_1
    //   270: astore 6
    //   272: aload_0
    //   273: aload_0
    //   274: getfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   277: ldc_w 414
    //   280: invokestatic 820	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   283: putfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   286: goto +19 -> 305
    //   289: astore 8
    //   291: aload_1
    //   292: astore 4
    //   294: aload_1
    //   295: astore 5
    //   297: aload_1
    //   298: astore 6
    //   300: aload 8
    //   302: invokevirtual 695	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   305: aload_1
    //   306: astore 4
    //   308: aload_1
    //   309: astore 5
    //   311: aload_1
    //   312: astore 6
    //   314: new 677	java/lang/StringBuilder
    //   317: dup
    //   318: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   321: astore 8
    //   323: aload_1
    //   324: astore 4
    //   326: aload_1
    //   327: astore 5
    //   329: aload_1
    //   330: astore 6
    //   332: aload 8
    //   334: ldc_w 822
    //   337: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   340: pop
    //   341: aload_1
    //   342: astore 4
    //   344: aload_1
    //   345: astore 5
    //   347: aload_1
    //   348: astore 6
    //   350: aload 8
    //   352: ldc_w 766
    //   355: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   358: pop
    //   359: aload_1
    //   360: astore 4
    //   362: aload_1
    //   363: astore 5
    //   365: aload_1
    //   366: astore 6
    //   368: aload 8
    //   370: ldc_w 824
    //   373: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   376: pop
    //   377: aload_1
    //   378: astore 4
    //   380: aload_1
    //   381: astore 5
    //   383: aload_1
    //   384: astore 6
    //   386: aload 8
    //   388: aload 7
    //   390: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   393: pop
    //   394: aload_1
    //   395: astore 4
    //   397: aload_1
    //   398: astore 5
    //   400: aload_1
    //   401: astore 6
    //   403: aload 8
    //   405: ldc_w 826
    //   408: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   411: pop
    //   412: aload_1
    //   413: astore 4
    //   415: aload_1
    //   416: astore 5
    //   418: aload_1
    //   419: astore 6
    //   421: aload 8
    //   423: ldc_w 768
    //   426: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   429: pop
    //   430: aload_1
    //   431: astore 4
    //   433: aload_1
    //   434: astore 5
    //   436: aload_1
    //   437: astore 6
    //   439: aload 8
    //   441: ldc_w 828
    //   444: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   447: pop
    //   448: aload_1
    //   449: astore 4
    //   451: aload_1
    //   452: astore 5
    //   454: aload_1
    //   455: astore 6
    //   457: aload 8
    //   459: aload_3
    //   460: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   463: pop
    //   464: aload_1
    //   465: astore 4
    //   467: aload_1
    //   468: astore 5
    //   470: aload_1
    //   471: astore 6
    //   473: aload 8
    //   475: ldc_w 830
    //   478: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   481: pop
    //   482: aload_1
    //   483: astore 4
    //   485: aload_1
    //   486: astore 5
    //   488: aload_1
    //   489: astore 6
    //   491: aload 8
    //   493: aload_0
    //   494: getfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   497: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   500: pop
    //   501: aload_1
    //   502: astore 4
    //   504: aload_1
    //   505: astore 5
    //   507: aload_1
    //   508: astore 6
    //   510: aload 8
    //   512: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   515: astore_3
    //   516: aload_1
    //   517: astore 4
    //   519: aload_1
    //   520: astore 5
    //   522: aload_1
    //   523: astore 6
    //   525: new 832	java/io/PrintWriter
    //   528: dup
    //   529: aload_1
    //   530: invokevirtual 836	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   533: invokespecial 839	java/io/PrintWriter:<init>	(Ljava/io/OutputStream;)V
    //   536: astore 8
    //   538: aload_1
    //   539: astore 4
    //   541: aload_1
    //   542: astore 5
    //   544: aload_1
    //   545: astore 6
    //   547: aload 8
    //   549: aload_3
    //   550: invokevirtual 842	java/io/PrintWriter:print	(Ljava/lang/String;)V
    //   553: aload_1
    //   554: astore 4
    //   556: aload_1
    //   557: astore 5
    //   559: aload_1
    //   560: astore 6
    //   562: aload 8
    //   564: invokevirtual 845	java/io/PrintWriter:flush	()V
    //   567: aload_1
    //   568: astore 4
    //   570: aload_1
    //   571: astore 5
    //   573: aload_1
    //   574: invokevirtual 849	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   577: astore_3
    //   578: goto +17 -> 595
    //   581: aload_1
    //   582: astore 4
    //   584: aload_1
    //   585: astore 5
    //   587: aload_1
    //   588: astore 6
    //   590: aload_1
    //   591: invokevirtual 852	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   594: astore_3
    //   595: aload_1
    //   596: astore 4
    //   598: aload_1
    //   599: astore 5
    //   601: aload_1
    //   602: astore 6
    //   604: aload_1
    //   605: invokevirtual 855	java/net/HttpURLConnection:getResponseCode	()I
    //   608: istore_2
    //   609: aload_1
    //   610: astore 4
    //   612: aload_1
    //   613: astore 5
    //   615: aload_1
    //   616: astore 6
    //   618: new 857	java/io/BufferedReader
    //   621: dup
    //   622: new 859	java/io/InputStreamReader
    //   625: dup
    //   626: aload_3
    //   627: invokespecial 862	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   630: invokespecial 865	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   633: astore_3
    //   634: aload_1
    //   635: astore 4
    //   637: aload_1
    //   638: astore 5
    //   640: aload_1
    //   641: astore 6
    //   643: new 867	java/lang/StringBuffer
    //   646: dup
    //   647: invokespecial 868	java/lang/StringBuffer:<init>	()V
    //   650: astore 8
    //   652: aload_1
    //   653: astore 4
    //   655: aload_1
    //   656: astore 5
    //   658: aload_1
    //   659: astore 6
    //   661: aload_3
    //   662: invokevirtual 871	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   665: astore 10
    //   667: aload 10
    //   669: ifnull +41 -> 710
    //   672: aload_1
    //   673: astore 4
    //   675: aload_1
    //   676: astore 5
    //   678: aload_1
    //   679: astore 6
    //   681: aload 8
    //   683: aload 10
    //   685: invokevirtual 874	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   688: pop
    //   689: aload_1
    //   690: astore 4
    //   692: aload_1
    //   693: astore 5
    //   695: aload_1
    //   696: astore 6
    //   698: aload 8
    //   700: ldc_w 684
    //   703: invokevirtual 874	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   706: pop
    //   707: goto -55 -> 652
    //   710: aload_1
    //   711: astore 4
    //   713: aload_1
    //   714: astore 5
    //   716: aload_1
    //   717: astore 6
    //   719: aload 8
    //   721: invokevirtual 875	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   724: astore 8
    //   726: aload 8
    //   728: ifnull +2406 -> 3134
    //   731: aload_1
    //   732: astore 4
    //   734: aload_1
    //   735: astore 5
    //   737: aload_1
    //   738: astore 6
    //   740: new 558	org/json/JSONObject
    //   743: dup
    //   744: aload 8
    //   746: invokespecial 560	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   749: ldc_w 877
    //   752: invokevirtual 566	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   755: astore_3
    //   756: aload_3
    //   757: ifnull +2377 -> 3134
    //   760: aload_1
    //   761: astore 4
    //   763: aload_1
    //   764: astore 5
    //   766: aload_1
    //   767: astore 6
    //   769: new 558	org/json/JSONObject
    //   772: dup
    //   773: aload_3
    //   774: ldc_w 879
    //   777: ldc -128
    //   779: invokevirtual 883	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   782: ldc_w 885
    //   785: ldc -128
    //   787: invokevirtual 883	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   790: invokespecial 560	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   793: ldc_w 887
    //   796: invokevirtual 566	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   799: astore_3
    //   800: goto +22 -> 822
    //   803: astore_3
    //   804: aload_1
    //   805: astore 4
    //   807: aload_1
    //   808: astore 5
    //   810: aload_1
    //   811: astore 6
    //   813: aload_3
    //   814: invokevirtual 573	org/json/JSONException:printStackTrace	()V
    //   817: aconst_null
    //   818: astore_3
    //   819: goto +3 -> 822
    //   822: iload_2
    //   823: sipush 200
    //   826: if_icmpne +106 -> 932
    //   829: aload_3
    //   830: ifnull +102 -> 932
    //   833: aload_1
    //   834: astore 4
    //   836: aload_1
    //   837: astore 5
    //   839: aload_1
    //   840: astore 6
    //   842: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   845: ldc_w 889
    //   848: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   851: aload_1
    //   852: astore 4
    //   854: aload_1
    //   855: astore 5
    //   857: aload_1
    //   858: astore 6
    //   860: aload_0
    //   861: aload_3
    //   862: invokevirtual 895	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:loadUrl	(Ljava/lang/String;)V
    //   865: aload_1
    //   866: astore 4
    //   868: aload_1
    //   869: astore 5
    //   871: aload_1
    //   872: astore 6
    //   874: aload_0
    //   875: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   878: sipush 1017
    //   881: if_icmpeq +25 -> 906
    //   884: aload_1
    //   885: astore 8
    //   887: aload_1
    //   888: astore 4
    //   890: aload_1
    //   891: astore 5
    //   893: aload_1
    //   894: astore 6
    //   896: aload_0
    //   897: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   900: sipush 1018
    //   903: if_icmpne +1834 -> 2737
    //   906: aload_1
    //   907: astore 4
    //   909: aload_1
    //   910: astore 5
    //   912: aload_1
    //   913: astore 6
    //   915: aload_0
    //   916: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   919: sipush 1019
    //   922: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   925: pop
    //   926: aload_1
    //   927: astore 8
    //   929: goto +1808 -> 2737
    //   932: iload_2
    //   933: sipush 200
    //   936: if_icmpne +1430 -> 2366
    //   939: aload 8
    //   941: ifnull +1425 -> 2366
    //   944: aload_1
    //   945: astore 4
    //   947: aload_1
    //   948: astore 5
    //   950: aload_1
    //   951: astore 6
    //   953: aload 8
    //   955: ldc_w 897
    //   958: invokevirtual 579	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   961: ifeq +1405 -> 2366
    //   964: aload_1
    //   965: astore 4
    //   967: aload_1
    //   968: astore 5
    //   970: aload_1
    //   971: astore 6
    //   973: aload_0
    //   974: invokevirtual 901	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:lastHitTestBmp	()Landroid/graphics/Bitmap;
    //   977: astore_3
    //   978: aload_1
    //   979: astore 8
    //   981: aload_3
    //   982: ifnull +1755 -> 2737
    //   985: aload_1
    //   986: astore 4
    //   988: aload_1
    //   989: astore 5
    //   991: aload_1
    //   992: astore 6
    //   994: aload_0
    //   995: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   998: sipush 1017
    //   1001: if_icmpne +37 -> 1038
    //   1004: aload_1
    //   1005: astore 4
    //   1007: aload_1
    //   1008: astore 5
    //   1010: aload_1
    //   1011: astore 6
    //   1013: aload_0
    //   1014: aload_3
    //   1015: putfield 188	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mBmpFromCore	Landroid/graphics/Bitmap;
    //   1018: aload_1
    //   1019: astore 4
    //   1021: aload_1
    //   1022: astore 5
    //   1024: aload_1
    //   1025: astore 6
    //   1027: aload_0
    //   1028: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   1031: sipush 1018
    //   1034: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   1037: pop
    //   1038: aload_1
    //   1039: astore 4
    //   1041: aload_1
    //   1042: astore 5
    //   1044: aload_1
    //   1045: astore 6
    //   1047: new 903	java/io/ByteArrayOutputStream
    //   1050: dup
    //   1051: invokespecial 904	java/io/ByteArrayOutputStream:<init>	()V
    //   1054: astore 8
    //   1056: aload_1
    //   1057: astore 4
    //   1059: aload_1
    //   1060: astore 5
    //   1062: aload_1
    //   1063: astore 6
    //   1065: aload_3
    //   1066: getstatic 910	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   1069: bipush 100
    //   1071: aload 8
    //   1073: invokevirtual 916	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   1076: pop
    //   1077: aload_1
    //   1078: astore 4
    //   1080: aload_1
    //   1081: astore 5
    //   1083: aload_1
    //   1084: astore 6
    //   1086: aload_0
    //   1087: aload 8
    //   1089: invokevirtual 919	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   1092: bipush 10
    //   1094: invokestatic 675	android/util/Base64:encodeToString	([BI)Ljava/lang/String;
    //   1097: putfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   1100: aload_1
    //   1101: astore 4
    //   1103: aload_1
    //   1104: astore 5
    //   1106: aload_1
    //   1107: astore 6
    //   1109: ldc_w 766
    //   1112: aload 7
    //   1114: ldc_w 768
    //   1117: aload_0
    //   1118: getfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   1121: ldc_w 770
    //   1124: invokestatic 772	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:getSig	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1127: astore_3
    //   1128: aload_1
    //   1129: astore 4
    //   1131: aload_1
    //   1132: astore 5
    //   1134: aload_1
    //   1135: astore 6
    //   1137: aload_3
    //   1138: ldc_w 414
    //   1141: invokestatic 820	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1144: astore 8
    //   1146: aload 8
    //   1148: astore_3
    //   1149: goto +19 -> 1168
    //   1152: astore 8
    //   1154: aload_1
    //   1155: astore 4
    //   1157: aload_1
    //   1158: astore 5
    //   1160: aload_1
    //   1161: astore 6
    //   1163: aload 8
    //   1165: invokevirtual 695	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   1168: aload_1
    //   1169: astore 4
    //   1171: aload_1
    //   1172: astore 5
    //   1174: aload_1
    //   1175: astore 6
    //   1177: aload_0
    //   1178: aload_0
    //   1179: getfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   1182: ldc_w 414
    //   1185: invokestatic 820	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1188: putfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   1191: goto +19 -> 1210
    //   1194: astore 8
    //   1196: aload_1
    //   1197: astore 4
    //   1199: aload_1
    //   1200: astore 5
    //   1202: aload_1
    //   1203: astore 6
    //   1205: aload 8
    //   1207: invokevirtual 695	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   1210: aload_1
    //   1211: astore 4
    //   1213: aload_1
    //   1214: astore 5
    //   1216: aload_1
    //   1217: astore 6
    //   1219: new 677	java/lang/StringBuilder
    //   1222: dup
    //   1223: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   1226: astore 8
    //   1228: aload_1
    //   1229: astore 4
    //   1231: aload_1
    //   1232: astore 5
    //   1234: aload_1
    //   1235: astore 6
    //   1237: aload 8
    //   1239: ldc_w 822
    //   1242: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1245: pop
    //   1246: aload_1
    //   1247: astore 4
    //   1249: aload_1
    //   1250: astore 5
    //   1252: aload_1
    //   1253: astore 6
    //   1255: aload 8
    //   1257: ldc_w 766
    //   1260: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1263: pop
    //   1264: aload_1
    //   1265: astore 4
    //   1267: aload_1
    //   1268: astore 5
    //   1270: aload_1
    //   1271: astore 6
    //   1273: aload 8
    //   1275: ldc_w 824
    //   1278: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1281: pop
    //   1282: aload_1
    //   1283: astore 4
    //   1285: aload_1
    //   1286: astore 5
    //   1288: aload_1
    //   1289: astore 6
    //   1291: aload 8
    //   1293: aload 7
    //   1295: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1298: pop
    //   1299: aload_1
    //   1300: astore 4
    //   1302: aload_1
    //   1303: astore 5
    //   1305: aload_1
    //   1306: astore 6
    //   1308: aload 8
    //   1310: ldc_w 826
    //   1313: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1316: pop
    //   1317: aload_1
    //   1318: astore 4
    //   1320: aload_1
    //   1321: astore 5
    //   1323: aload_1
    //   1324: astore 6
    //   1326: aload 8
    //   1328: ldc_w 768
    //   1331: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1334: pop
    //   1335: aload_1
    //   1336: astore 4
    //   1338: aload_1
    //   1339: astore 5
    //   1341: aload_1
    //   1342: astore 6
    //   1344: aload 8
    //   1346: ldc_w 828
    //   1349: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1352: pop
    //   1353: aload_1
    //   1354: astore 4
    //   1356: aload_1
    //   1357: astore 5
    //   1359: aload_1
    //   1360: astore 6
    //   1362: aload 8
    //   1364: aload_3
    //   1365: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1368: pop
    //   1369: aload_1
    //   1370: astore 4
    //   1372: aload_1
    //   1373: astore 5
    //   1375: aload_1
    //   1376: astore 6
    //   1378: aload 8
    //   1380: ldc_w 830
    //   1383: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1386: pop
    //   1387: aload_1
    //   1388: astore 4
    //   1390: aload_1
    //   1391: astore 5
    //   1393: aload_1
    //   1394: astore 6
    //   1396: aload 8
    //   1398: aload_0
    //   1399: getfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   1402: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1405: pop
    //   1406: aload_1
    //   1407: astore 4
    //   1409: aload_1
    //   1410: astore 5
    //   1412: aload_1
    //   1413: astore 6
    //   1415: aload 8
    //   1417: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1420: astore 7
    //   1422: aload_1
    //   1423: ifnull +21 -> 1444
    //   1426: aload_1
    //   1427: astore 4
    //   1429: aload_1
    //   1430: astore 5
    //   1432: aload_1
    //   1433: astore 6
    //   1435: aload_1
    //   1436: invokevirtual 922	java/net/HttpURLConnection:disconnect	()V
    //   1439: aconst_null
    //   1440: astore_1
    //   1441: goto +3 -> 1444
    //   1444: aload_1
    //   1445: astore 4
    //   1447: aload 9
    //   1449: invokevirtual 781	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   1452: checkcast 783	java/net/HttpURLConnection
    //   1455: astore_3
    //   1456: aload_3
    //   1457: astore 4
    //   1459: aload_3
    //   1460: astore 5
    //   1462: aload_3
    //   1463: astore 6
    //   1465: aload_3
    //   1466: sipush 4000
    //   1469: invokevirtual 786	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   1472: aload_3
    //   1473: astore 4
    //   1475: aload_3
    //   1476: astore 5
    //   1478: aload_3
    //   1479: astore 6
    //   1481: aload_3
    //   1482: sipush 4000
    //   1485: invokevirtual 789	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   1488: aload_3
    //   1489: astore 4
    //   1491: aload_3
    //   1492: astore 5
    //   1494: aload_3
    //   1495: astore 6
    //   1497: aload_3
    //   1498: ldc_w 791
    //   1501: invokevirtual 794	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   1504: aload_3
    //   1505: astore 4
    //   1507: aload_3
    //   1508: astore 5
    //   1510: aload_3
    //   1511: astore 6
    //   1513: aload_3
    //   1514: ldc_w 796
    //   1517: ldc_w 798
    //   1520: invokevirtual 802	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   1523: aload_3
    //   1524: astore 4
    //   1526: aload_3
    //   1527: astore 5
    //   1529: aload_3
    //   1530: astore 6
    //   1532: aload_3
    //   1533: iconst_1
    //   1534: invokevirtual 805	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   1537: aload_3
    //   1538: astore 4
    //   1540: aload_3
    //   1541: astore 5
    //   1543: aload_3
    //   1544: astore 6
    //   1546: aload_3
    //   1547: iconst_1
    //   1548: invokevirtual 808	java/net/HttpURLConnection:setDoInput	(Z)V
    //   1551: aload_3
    //   1552: astore 4
    //   1554: aload_3
    //   1555: astore 5
    //   1557: aload_3
    //   1558: astore 6
    //   1560: aload_3
    //   1561: iconst_0
    //   1562: invokevirtual 811	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   1565: aload_3
    //   1566: astore 4
    //   1568: aload_3
    //   1569: astore 5
    //   1571: aload_3
    //   1572: astore 6
    //   1574: aload_3
    //   1575: invokevirtual 814	java/net/HttpURLConnection:connect	()V
    //   1578: aload_3
    //   1579: astore 4
    //   1581: aload_3
    //   1582: astore 5
    //   1584: aload_3
    //   1585: astore 6
    //   1587: new 832	java/io/PrintWriter
    //   1590: dup
    //   1591: aload_3
    //   1592: invokevirtual 836	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   1595: invokespecial 839	java/io/PrintWriter:<init>	(Ljava/io/OutputStream;)V
    //   1598: astore_1
    //   1599: aload_3
    //   1600: astore 4
    //   1602: aload_3
    //   1603: astore 5
    //   1605: aload_3
    //   1606: astore 6
    //   1608: aload_1
    //   1609: aload 7
    //   1611: invokevirtual 842	java/io/PrintWriter:print	(Ljava/lang/String;)V
    //   1614: aload_3
    //   1615: astore 4
    //   1617: aload_3
    //   1618: astore 5
    //   1620: aload_3
    //   1621: astore 6
    //   1623: aload_1
    //   1624: invokevirtual 845	java/io/PrintWriter:flush	()V
    //   1627: aload_3
    //   1628: astore 4
    //   1630: aload_3
    //   1631: astore 5
    //   1633: aload_3
    //   1634: invokevirtual 849	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   1637: astore_1
    //   1638: goto +17 -> 1655
    //   1641: aload_3
    //   1642: astore 4
    //   1644: aload_3
    //   1645: astore 5
    //   1647: aload_3
    //   1648: astore 6
    //   1650: aload_3
    //   1651: invokevirtual 852	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   1654: astore_1
    //   1655: aload_3
    //   1656: astore 4
    //   1658: aload_3
    //   1659: astore 5
    //   1661: aload_3
    //   1662: astore 6
    //   1664: aload_3
    //   1665: invokevirtual 855	java/net/HttpURLConnection:getResponseCode	()I
    //   1668: istore_2
    //   1669: aload_3
    //   1670: astore 4
    //   1672: aload_3
    //   1673: astore 5
    //   1675: aload_3
    //   1676: astore 6
    //   1678: new 857	java/io/BufferedReader
    //   1681: dup
    //   1682: new 859	java/io/InputStreamReader
    //   1685: dup
    //   1686: aload_1
    //   1687: invokespecial 862	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   1690: invokespecial 865	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   1693: astore_1
    //   1694: aload_3
    //   1695: astore 4
    //   1697: aload_3
    //   1698: astore 5
    //   1700: aload_3
    //   1701: astore 6
    //   1703: new 867	java/lang/StringBuffer
    //   1706: dup
    //   1707: invokespecial 868	java/lang/StringBuffer:<init>	()V
    //   1710: astore 7
    //   1712: aload_3
    //   1713: astore 4
    //   1715: aload_3
    //   1716: astore 5
    //   1718: aload_3
    //   1719: astore 6
    //   1721: aload_1
    //   1722: invokevirtual 871	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   1725: astore 8
    //   1727: aload 8
    //   1729: ifnull +41 -> 1770
    //   1732: aload_3
    //   1733: astore 4
    //   1735: aload_3
    //   1736: astore 5
    //   1738: aload_3
    //   1739: astore 6
    //   1741: aload 7
    //   1743: aload 8
    //   1745: invokevirtual 874	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1748: pop
    //   1749: aload_3
    //   1750: astore 4
    //   1752: aload_3
    //   1753: astore 5
    //   1755: aload_3
    //   1756: astore 6
    //   1758: aload 7
    //   1760: ldc_w 684
    //   1763: invokevirtual 874	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1766: pop
    //   1767: goto -55 -> 1712
    //   1770: aload_3
    //   1771: astore 4
    //   1773: aload_3
    //   1774: astore 5
    //   1776: aload_3
    //   1777: astore 6
    //   1779: aload 7
    //   1781: invokevirtual 875	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1784: astore 7
    //   1786: aload 7
    //   1788: ifnull +1351 -> 3139
    //   1791: aload_3
    //   1792: astore 4
    //   1794: aload_3
    //   1795: astore 5
    //   1797: aload_3
    //   1798: astore 6
    //   1800: new 558	org/json/JSONObject
    //   1803: dup
    //   1804: aload 7
    //   1806: invokespecial 560	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   1809: ldc_w 877
    //   1812: invokevirtual 566	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   1815: astore_1
    //   1816: aload_1
    //   1817: ifnull +1322 -> 3139
    //   1820: aload_3
    //   1821: astore 4
    //   1823: aload_3
    //   1824: astore 5
    //   1826: aload_3
    //   1827: astore 6
    //   1829: new 558	org/json/JSONObject
    //   1832: dup
    //   1833: aload_1
    //   1834: ldc_w 879
    //   1837: ldc -128
    //   1839: invokevirtual 883	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   1842: ldc_w 885
    //   1845: ldc -128
    //   1847: invokevirtual 883	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   1850: invokespecial 560	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   1853: ldc_w 887
    //   1856: invokevirtual 566	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   1859: astore_1
    //   1860: goto +22 -> 1882
    //   1863: astore_1
    //   1864: aload_3
    //   1865: astore 4
    //   1867: aload_3
    //   1868: astore 5
    //   1870: aload_3
    //   1871: astore 6
    //   1873: aload_1
    //   1874: invokevirtual 573	org/json/JSONException:printStackTrace	()V
    //   1877: aconst_null
    //   1878: astore_1
    //   1879: goto +3 -> 1882
    //   1882: iload_2
    //   1883: sipush 200
    //   1886: if_icmpne +106 -> 1992
    //   1889: aload_1
    //   1890: ifnull +102 -> 1992
    //   1893: aload_3
    //   1894: astore 4
    //   1896: aload_3
    //   1897: astore 5
    //   1899: aload_3
    //   1900: astore 6
    //   1902: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   1905: ldc_w 889
    //   1908: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   1911: aload_3
    //   1912: astore 4
    //   1914: aload_3
    //   1915: astore 5
    //   1917: aload_3
    //   1918: astore 6
    //   1920: aload_0
    //   1921: aload_1
    //   1922: invokevirtual 895	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:loadUrl	(Ljava/lang/String;)V
    //   1925: aload_3
    //   1926: astore 4
    //   1928: aload_3
    //   1929: astore 5
    //   1931: aload_3
    //   1932: astore 6
    //   1934: aload_0
    //   1935: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   1938: sipush 1017
    //   1941: if_icmpeq +25 -> 1966
    //   1944: aload_3
    //   1945: astore 8
    //   1947: aload_3
    //   1948: astore 4
    //   1950: aload_3
    //   1951: astore 5
    //   1953: aload_3
    //   1954: astore 6
    //   1956: aload_0
    //   1957: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   1960: sipush 1018
    //   1963: if_icmpne +774 -> 2737
    //   1966: aload_3
    //   1967: astore 4
    //   1969: aload_3
    //   1970: astore 5
    //   1972: aload_3
    //   1973: astore 6
    //   1975: aload_0
    //   1976: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   1979: sipush 1019
    //   1982: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   1985: pop
    //   1986: aload_3
    //   1987: astore 8
    //   1989: goto +748 -> 2737
    //   1992: iload_2
    //   1993: sipush 200
    //   1996: if_icmpne +70 -> 2066
    //   1999: aload 7
    //   2001: ifnull +65 -> 2066
    //   2004: aload_3
    //   2005: astore 4
    //   2007: aload_3
    //   2008: astore 5
    //   2010: aload_3
    //   2011: astore 6
    //   2013: aload_0
    //   2014: aload 7
    //   2016: invokespecial 924	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:canGetImageQueryClass	(Ljava/lang/String;)I
    //   2019: ifne +47 -> 2066
    //   2022: aload_3
    //   2023: astore 4
    //   2025: aload_3
    //   2026: astore 5
    //   2028: aload_3
    //   2029: astore 6
    //   2031: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2034: aload_0
    //   2035: getfield 190	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageQueryClass	Ljava/lang/String;
    //   2038: iconst_0
    //   2039: invokevirtual 928	com/tencent/smtt/webkit/service/SmttServiceProxy:showToast	(Ljava/lang/String;I)V
    //   2042: aload_3
    //   2043: astore 4
    //   2045: aload_3
    //   2046: astore 5
    //   2048: aload_3
    //   2049: astore 6
    //   2051: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2054: ldc_w 889
    //   2057: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   2060: aload_3
    //   2061: astore 8
    //   2063: goto +674 -> 2737
    //   2066: iload_2
    //   2067: sipush 200
    //   2070: if_icmpne +49 -> 2119
    //   2073: aload 7
    //   2075: ifnull +44 -> 2119
    //   2078: aload_3
    //   2079: astore 4
    //   2081: aload_3
    //   2082: astore 5
    //   2084: aload_3
    //   2085: astore 6
    //   2087: iconst_1
    //   2088: aload_0
    //   2089: getfield 192	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLastGetImageQueryClass	I
    //   2092: if_icmpne +27 -> 2119
    //   2095: aload_3
    //   2096: astore 4
    //   2098: aload_3
    //   2099: astore 5
    //   2101: aload_3
    //   2102: astore 6
    //   2104: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2107: ldc_w 889
    //   2110: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   2113: aload_3
    //   2114: astore 8
    //   2116: goto +621 -> 2737
    //   2119: aload_3
    //   2120: astore 4
    //   2122: aload_3
    //   2123: astore 5
    //   2125: aload_3
    //   2126: astore 6
    //   2128: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2131: ldc_w 930
    //   2134: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   2137: aload_3
    //   2138: astore 4
    //   2140: aload_3
    //   2141: astore 5
    //   2143: aload_3
    //   2144: astore 6
    //   2146: ldc_w 932
    //   2149: ldc_w 934
    //   2152: invokestatic 936	com/tencent/smtt/webkit/SmttResource:getString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   2155: astore_1
    //   2156: aload_3
    //   2157: astore 4
    //   2159: aload_3
    //   2160: astore 5
    //   2162: aload_3
    //   2163: astore 6
    //   2165: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2168: aload_1
    //   2169: iconst_0
    //   2170: invokevirtual 928	com/tencent/smtt/webkit/service/SmttServiceProxy:showToast	(Ljava/lang/String;I)V
    //   2173: aload_3
    //   2174: astore 4
    //   2176: aload_3
    //   2177: astore 5
    //   2179: aload_3
    //   2180: astore 6
    //   2182: new 938	java/util/HashMap
    //   2185: dup
    //   2186: invokespecial 939	java/util/HashMap:<init>	()V
    //   2189: astore_1
    //   2190: aload_3
    //   2191: astore 4
    //   2193: aload_3
    //   2194: astore 5
    //   2196: aload_3
    //   2197: astore 6
    //   2199: new 677	java/lang/StringBuilder
    //   2202: dup
    //   2203: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   2206: astore 8
    //   2208: aload_3
    //   2209: astore 4
    //   2211: aload_3
    //   2212: astore 5
    //   2214: aload_3
    //   2215: astore 6
    //   2217: aload 8
    //   2219: ldc -128
    //   2221: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2224: pop
    //   2225: aload_3
    //   2226: astore 4
    //   2228: aload_3
    //   2229: astore 5
    //   2231: aload_3
    //   2232: astore 6
    //   2234: aload 8
    //   2236: iload_2
    //   2237: invokevirtual 942	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   2240: pop
    //   2241: aload_3
    //   2242: astore 4
    //   2244: aload_3
    //   2245: astore 5
    //   2247: aload_3
    //   2248: astore 6
    //   2250: aload_1
    //   2251: ldc_w 944
    //   2254: aload 8
    //   2256: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2259: invokeinterface 247 3 0
    //   2264: pop
    //   2265: aload_3
    //   2266: astore 4
    //   2268: aload_3
    //   2269: astore 5
    //   2271: aload_3
    //   2272: astore 6
    //   2274: new 677	java/lang/StringBuilder
    //   2277: dup
    //   2278: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   2281: astore 8
    //   2283: aload_3
    //   2284: astore 4
    //   2286: aload_3
    //   2287: astore 5
    //   2289: aload_3
    //   2290: astore 6
    //   2292: aload 8
    //   2294: ldc -128
    //   2296: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2299: pop
    //   2300: aload_3
    //   2301: astore 4
    //   2303: aload_3
    //   2304: astore 5
    //   2306: aload_3
    //   2307: astore 6
    //   2309: aload 8
    //   2311: aload 7
    //   2313: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2316: pop
    //   2317: aload_3
    //   2318: astore 4
    //   2320: aload_3
    //   2321: astore 5
    //   2323: aload_3
    //   2324: astore 6
    //   2326: aload_1
    //   2327: ldc_w 946
    //   2330: aload 8
    //   2332: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2335: invokeinterface 247 3 0
    //   2340: pop
    //   2341: aload_3
    //   2342: astore 4
    //   2344: aload_3
    //   2345: astore 5
    //   2347: aload_3
    //   2348: astore 6
    //   2350: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2353: ldc_w 948
    //   2356: aload_1
    //   2357: invokevirtual 952	com/tencent/smtt/webkit/service/SmttServiceProxy:upLoadToBeacon	(Ljava/lang/String;Ljava/util/Map;)V
    //   2360: aload_3
    //   2361: astore 8
    //   2363: goto +374 -> 2737
    //   2366: iload_2
    //   2367: sipush 200
    //   2370: if_icmpne +70 -> 2440
    //   2373: aload 8
    //   2375: ifnull +65 -> 2440
    //   2378: aload_1
    //   2379: astore 4
    //   2381: aload_1
    //   2382: astore 5
    //   2384: aload_1
    //   2385: astore 6
    //   2387: aload_0
    //   2388: aload 8
    //   2390: invokespecial 924	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:canGetImageQueryClass	(Ljava/lang/String;)I
    //   2393: ifne +47 -> 2440
    //   2396: aload_1
    //   2397: astore 4
    //   2399: aload_1
    //   2400: astore 5
    //   2402: aload_1
    //   2403: astore 6
    //   2405: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2408: aload_0
    //   2409: getfield 190	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageQueryClass	Ljava/lang/String;
    //   2412: iconst_0
    //   2413: invokevirtual 928	com/tencent/smtt/webkit/service/SmttServiceProxy:showToast	(Ljava/lang/String;I)V
    //   2416: aload_1
    //   2417: astore 4
    //   2419: aload_1
    //   2420: astore 5
    //   2422: aload_1
    //   2423: astore 6
    //   2425: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2428: ldc_w 889
    //   2431: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   2434: aload_1
    //   2435: astore 8
    //   2437: goto +300 -> 2737
    //   2440: iload_2
    //   2441: sipush 200
    //   2444: if_icmpne +49 -> 2493
    //   2447: aload 8
    //   2449: ifnull +44 -> 2493
    //   2452: aload_1
    //   2453: astore 4
    //   2455: aload_1
    //   2456: astore 5
    //   2458: aload_1
    //   2459: astore 6
    //   2461: iconst_1
    //   2462: aload_0
    //   2463: getfield 192	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLastGetImageQueryClass	I
    //   2466: if_icmpne +27 -> 2493
    //   2469: aload_1
    //   2470: astore 4
    //   2472: aload_1
    //   2473: astore 5
    //   2475: aload_1
    //   2476: astore 6
    //   2478: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2481: ldc_w 889
    //   2484: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   2487: aload_1
    //   2488: astore 8
    //   2490: goto +247 -> 2737
    //   2493: aload_1
    //   2494: astore 4
    //   2496: aload_1
    //   2497: astore 5
    //   2499: aload_1
    //   2500: astore 6
    //   2502: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2505: ldc_w 930
    //   2508: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   2511: aload_1
    //   2512: astore 4
    //   2514: aload_1
    //   2515: astore 5
    //   2517: aload_1
    //   2518: astore 6
    //   2520: ldc_w 932
    //   2523: ldc_w 934
    //   2526: invokestatic 936	com/tencent/smtt/webkit/SmttResource:getString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   2529: astore_3
    //   2530: aload_1
    //   2531: astore 4
    //   2533: aload_1
    //   2534: astore 5
    //   2536: aload_1
    //   2537: astore 6
    //   2539: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2542: aload_3
    //   2543: iconst_0
    //   2544: invokevirtual 928	com/tencent/smtt/webkit/service/SmttServiceProxy:showToast	(Ljava/lang/String;I)V
    //   2547: aload_1
    //   2548: astore 4
    //   2550: aload_1
    //   2551: astore 5
    //   2553: aload_1
    //   2554: astore 6
    //   2556: new 938	java/util/HashMap
    //   2559: dup
    //   2560: invokespecial 939	java/util/HashMap:<init>	()V
    //   2563: astore_3
    //   2564: aload_1
    //   2565: astore 4
    //   2567: aload_1
    //   2568: astore 5
    //   2570: aload_1
    //   2571: astore 6
    //   2573: new 677	java/lang/StringBuilder
    //   2576: dup
    //   2577: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   2580: astore 7
    //   2582: aload_1
    //   2583: astore 4
    //   2585: aload_1
    //   2586: astore 5
    //   2588: aload_1
    //   2589: astore 6
    //   2591: aload 7
    //   2593: ldc -128
    //   2595: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2598: pop
    //   2599: aload_1
    //   2600: astore 4
    //   2602: aload_1
    //   2603: astore 5
    //   2605: aload_1
    //   2606: astore 6
    //   2608: aload 7
    //   2610: iload_2
    //   2611: invokevirtual 942	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   2614: pop
    //   2615: aload_1
    //   2616: astore 4
    //   2618: aload_1
    //   2619: astore 5
    //   2621: aload_1
    //   2622: astore 6
    //   2624: aload_3
    //   2625: ldc_w 944
    //   2628: aload 7
    //   2630: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2633: invokeinterface 247 3 0
    //   2638: pop
    //   2639: aload_1
    //   2640: astore 4
    //   2642: aload_1
    //   2643: astore 5
    //   2645: aload_1
    //   2646: astore 6
    //   2648: new 677	java/lang/StringBuilder
    //   2651: dup
    //   2652: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   2655: astore 7
    //   2657: aload_1
    //   2658: astore 4
    //   2660: aload_1
    //   2661: astore 5
    //   2663: aload_1
    //   2664: astore 6
    //   2666: aload 7
    //   2668: ldc -128
    //   2670: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2673: pop
    //   2674: aload_1
    //   2675: astore 4
    //   2677: aload_1
    //   2678: astore 5
    //   2680: aload_1
    //   2681: astore 6
    //   2683: aload 7
    //   2685: aload 8
    //   2687: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2690: pop
    //   2691: aload_1
    //   2692: astore 4
    //   2694: aload_1
    //   2695: astore 5
    //   2697: aload_1
    //   2698: astore 6
    //   2700: aload_3
    //   2701: ldc_w 946
    //   2704: aload 7
    //   2706: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2709: invokeinterface 247 3 0
    //   2714: pop
    //   2715: aload_1
    //   2716: astore 4
    //   2718: aload_1
    //   2719: astore 5
    //   2721: aload_1
    //   2722: astore 6
    //   2724: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2727: ldc_w 948
    //   2730: aload_3
    //   2731: invokevirtual 952	com/tencent/smtt/webkit/service/SmttServiceProxy:upLoadToBeacon	(Ljava/lang/String;Ljava/util/Map;)V
    //   2734: aload_1
    //   2735: astore 8
    //   2737: aload 8
    //   2739: ifnull +8 -> 2747
    //   2742: aload 8
    //   2744: invokevirtual 922	java/net/HttpURLConnection:disconnect	()V
    //   2747: aload_0
    //   2748: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   2751: istore_2
    //   2752: iload_2
    //   2753: sipush 1017
    //   2756: if_icmpeq +315 -> 3071
    //   2759: iload_2
    //   2760: sipush 1018
    //   2763: if_icmpne +319 -> 3082
    //   2766: goto +305 -> 3071
    //   2769: astore_1
    //   2770: goto +314 -> 3084
    //   2773: astore_3
    //   2774: aload 5
    //   2776: astore_1
    //   2777: goto +20 -> 2797
    //   2780: astore_3
    //   2781: aload 6
    //   2783: astore_1
    //   2784: goto +153 -> 2937
    //   2787: astore_1
    //   2788: aconst_null
    //   2789: astore 4
    //   2791: goto +293 -> 3084
    //   2794: astore_3
    //   2795: aconst_null
    //   2796: astore_1
    //   2797: aload_1
    //   2798: astore 4
    //   2800: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2803: ldc_w 954
    //   2806: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   2809: aload_1
    //   2810: astore 4
    //   2812: aload_0
    //   2813: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   2816: sipush 1019
    //   2819: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   2822: pop
    //   2823: aload_1
    //   2824: astore 4
    //   2826: new 938	java/util/HashMap
    //   2829: dup
    //   2830: invokespecial 939	java/util/HashMap:<init>	()V
    //   2833: astore 5
    //   2835: aload_1
    //   2836: astore 4
    //   2838: new 677	java/lang/StringBuilder
    //   2841: dup
    //   2842: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   2845: astore 6
    //   2847: aload_1
    //   2848: astore 4
    //   2850: aload 6
    //   2852: ldc -128
    //   2854: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2857: pop
    //   2858: aload_1
    //   2859: astore 4
    //   2861: aload 6
    //   2863: aload_3
    //   2864: invokevirtual 957	java/lang/OutOfMemoryError:getMessage	()Ljava/lang/String;
    //   2867: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2870: pop
    //   2871: aload_1
    //   2872: astore 4
    //   2874: aload 5
    //   2876: ldc_w 959
    //   2879: aload 6
    //   2881: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2884: invokeinterface 247 3 0
    //   2889: pop
    //   2890: aload_1
    //   2891: astore 4
    //   2893: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2896: ldc_w 961
    //   2899: aload 5
    //   2901: invokevirtual 952	com/tencent/smtt/webkit/service/SmttServiceProxy:upLoadToBeacon	(Ljava/lang/String;Ljava/util/Map;)V
    //   2904: aload_1
    //   2905: ifnull +7 -> 2912
    //   2908: aload_1
    //   2909: invokevirtual 922	java/net/HttpURLConnection:disconnect	()V
    //   2912: aload_0
    //   2913: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   2916: istore_2
    //   2917: iload_2
    //   2918: sipush 1017
    //   2921: if_icmpeq +150 -> 3071
    //   2924: iload_2
    //   2925: sipush 1018
    //   2928: if_icmpne +154 -> 3082
    //   2931: goto +140 -> 3071
    //   2934: astore_3
    //   2935: aconst_null
    //   2936: astore_1
    //   2937: aload_1
    //   2938: astore 4
    //   2940: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   2943: ldc_w 954
    //   2946: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   2949: aload_1
    //   2950: astore 4
    //   2952: aload_0
    //   2953: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   2956: sipush 1019
    //   2959: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   2962: pop
    //   2963: aload_1
    //   2964: astore 4
    //   2966: new 938	java/util/HashMap
    //   2969: dup
    //   2970: invokespecial 939	java/util/HashMap:<init>	()V
    //   2973: astore 5
    //   2975: aload_1
    //   2976: astore 4
    //   2978: new 677	java/lang/StringBuilder
    //   2981: dup
    //   2982: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   2985: astore 6
    //   2987: aload_1
    //   2988: astore 4
    //   2990: aload 6
    //   2992: ldc -128
    //   2994: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2997: pop
    //   2998: aload_1
    //   2999: astore 4
    //   3001: aload 6
    //   3003: aload_3
    //   3004: invokevirtual 962	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   3007: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3010: pop
    //   3011: aload_1
    //   3012: astore 4
    //   3014: aload 5
    //   3016: ldc_w 959
    //   3019: aload 6
    //   3021: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   3024: invokeinterface 247 3 0
    //   3029: pop
    //   3030: aload_1
    //   3031: astore 4
    //   3033: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   3036: ldc_w 961
    //   3039: aload 5
    //   3041: invokevirtual 952	com/tencent/smtt/webkit/service/SmttServiceProxy:upLoadToBeacon	(Ljava/lang/String;Ljava/util/Map;)V
    //   3044: aload_1
    //   3045: ifnull +7 -> 3052
    //   3048: aload_1
    //   3049: invokevirtual 922	java/net/HttpURLConnection:disconnect	()V
    //   3052: aload_0
    //   3053: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   3056: istore_2
    //   3057: iload_2
    //   3058: sipush 1017
    //   3061: if_icmpeq +10 -> 3071
    //   3064: iload_2
    //   3065: sipush 1018
    //   3068: if_icmpne +14 -> 3082
    //   3071: aload_0
    //   3072: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   3075: sipush 1019
    //   3078: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   3081: pop
    //   3082: return
    //   3083: astore_1
    //   3084: aload 4
    //   3086: ifnull +8 -> 3094
    //   3089: aload 4
    //   3091: invokevirtual 922	java/net/HttpURLConnection:disconnect	()V
    //   3094: aload_0
    //   3095: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   3098: istore_2
    //   3099: iload_2
    //   3100: sipush 1017
    //   3103: if_icmpeq +10 -> 3113
    //   3106: iload_2
    //   3107: sipush 1018
    //   3110: if_icmpne +14 -> 3124
    //   3113: aload_0
    //   3114: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   3117: sipush 1019
    //   3120: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   3123: pop
    //   3124: aload_1
    //   3125: athrow
    //   3126: astore_3
    //   3127: goto -2546 -> 581
    //   3130: astore_1
    //   3131: goto -1490 -> 1641
    //   3134: aconst_null
    //   3135: astore_3
    //   3136: goto -2314 -> 822
    //   3139: aconst_null
    //   3140: astore_1
    //   3141: goto -1259 -> 1882
    //   3144: astore_3
    //   3145: goto -368 -> 2777
    //   3148: astore_3
    //   3149: goto -365 -> 2784
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	3152	0	this	X5WebViewAdapter
    //   0	3152	1	paramBundle	Bundle
    //   608	2503	2	i	int
    //   79	721	3	localObject1	Object
    //   803	11	3	localJSONException	JSONException
    //   818	1913	3	localObject2	Object
    //   2773	1	3	localOutOfMemoryError1	OutOfMemoryError
    //   2780	1	3	localException1	Exception
    //   2794	70	3	localOutOfMemoryError2	OutOfMemoryError
    //   2934	70	3	localException2	Exception
    //   3126	1	3	localException3	Exception
    //   3135	1	3	localObject3	Object
    //   3144	1	3	localOutOfMemoryError3	OutOfMemoryError
    //   3148	1	3	localException4	Exception
    //   102	2988	4	localObject4	Object
    //   105	2935	5	localObject5	Object
    //   108	2912	6	localObject6	Object
    //   47	2658	7	localObject7	Object
    //   239	3	8	str1	String
    //   247	12	8	localUnsupportedEncodingException1	UnsupportedEncodingException
    //   289	12	8	localUnsupportedEncodingException2	UnsupportedEncodingException
    //   321	826	8	localObject8	Object
    //   1152	12	8	localUnsupportedEncodingException3	UnsupportedEncodingException
    //   1194	12	8	localUnsupportedEncodingException4	UnsupportedEncodingException
    //   1226	1517	8	localObject9	Object
    //   90	1358	9	localURL	java.net.URL
    //   665	19	10	str2	String
    // Exception table:
    //   from	to	target	type
    //   232	241	247	java/io/UnsupportedEncodingException
    //   272	286	289	java/io/UnsupportedEncodingException
    //   740	756	803	org/json/JSONException
    //   769	800	803	org/json/JSONException
    //   1137	1146	1152	java/io/UnsupportedEncodingException
    //   1177	1191	1194	java/io/UnsupportedEncodingException
    //   1800	1816	1863	org/json/JSONException
    //   1829	1860	1863	org/json/JSONException
    //   110	117	2769	finally
    //   126	133	2769	finally
    //   142	149	2769	finally
    //   158	168	2769	finally
    //   177	182	2769	finally
    //   191	196	2769	finally
    //   205	210	2769	finally
    //   219	223	2769	finally
    //   232	241	2769	finally
    //   258	263	2769	finally
    //   272	286	2769	finally
    //   300	305	2769	finally
    //   314	323	2769	finally
    //   332	341	2769	finally
    //   350	359	2769	finally
    //   368	377	2769	finally
    //   386	394	2769	finally
    //   403	412	2769	finally
    //   421	430	2769	finally
    //   439	448	2769	finally
    //   457	464	2769	finally
    //   473	482	2769	finally
    //   491	501	2769	finally
    //   510	516	2769	finally
    //   525	538	2769	finally
    //   547	553	2769	finally
    //   562	567	2769	finally
    //   573	578	2769	finally
    //   590	595	2769	finally
    //   604	609	2769	finally
    //   618	634	2769	finally
    //   643	652	2769	finally
    //   661	667	2769	finally
    //   681	689	2769	finally
    //   698	707	2769	finally
    //   719	726	2769	finally
    //   740	756	2769	finally
    //   769	800	2769	finally
    //   813	817	2769	finally
    //   842	851	2769	finally
    //   860	865	2769	finally
    //   874	884	2769	finally
    //   896	906	2769	finally
    //   915	926	2769	finally
    //   953	964	2769	finally
    //   973	978	2769	finally
    //   994	1004	2769	finally
    //   1013	1018	2769	finally
    //   1027	1038	2769	finally
    //   1047	1056	2769	finally
    //   1065	1077	2769	finally
    //   1086	1100	2769	finally
    //   1109	1128	2769	finally
    //   1137	1146	2769	finally
    //   1163	1168	2769	finally
    //   1177	1191	2769	finally
    //   1205	1210	2769	finally
    //   1219	1228	2769	finally
    //   1237	1246	2769	finally
    //   1255	1264	2769	finally
    //   1273	1282	2769	finally
    //   1291	1299	2769	finally
    //   1308	1317	2769	finally
    //   1326	1335	2769	finally
    //   1344	1353	2769	finally
    //   1362	1369	2769	finally
    //   1378	1387	2769	finally
    //   1396	1406	2769	finally
    //   1415	1422	2769	finally
    //   1435	1439	2769	finally
    //   1465	1472	2769	finally
    //   1481	1488	2769	finally
    //   1497	1504	2769	finally
    //   1513	1523	2769	finally
    //   1532	1537	2769	finally
    //   1546	1551	2769	finally
    //   1560	1565	2769	finally
    //   1574	1578	2769	finally
    //   1587	1599	2769	finally
    //   1608	1614	2769	finally
    //   1623	1627	2769	finally
    //   1633	1638	2769	finally
    //   1650	1655	2769	finally
    //   1664	1669	2769	finally
    //   1678	1694	2769	finally
    //   1703	1712	2769	finally
    //   1721	1727	2769	finally
    //   1741	1749	2769	finally
    //   1758	1767	2769	finally
    //   1779	1786	2769	finally
    //   1800	1816	2769	finally
    //   1829	1860	2769	finally
    //   1873	1877	2769	finally
    //   1902	1911	2769	finally
    //   1920	1925	2769	finally
    //   1934	1944	2769	finally
    //   1956	1966	2769	finally
    //   1975	1986	2769	finally
    //   2013	2022	2769	finally
    //   2031	2042	2769	finally
    //   2051	2060	2769	finally
    //   2087	2095	2769	finally
    //   2104	2113	2769	finally
    //   2128	2137	2769	finally
    //   2146	2156	2769	finally
    //   2165	2173	2769	finally
    //   2182	2190	2769	finally
    //   2199	2208	2769	finally
    //   2217	2225	2769	finally
    //   2234	2241	2769	finally
    //   2250	2265	2769	finally
    //   2274	2283	2769	finally
    //   2292	2300	2769	finally
    //   2309	2317	2769	finally
    //   2326	2341	2769	finally
    //   2350	2360	2769	finally
    //   2387	2396	2769	finally
    //   2405	2416	2769	finally
    //   2425	2434	2769	finally
    //   2461	2469	2769	finally
    //   2478	2487	2769	finally
    //   2502	2511	2769	finally
    //   2520	2530	2769	finally
    //   2539	2547	2769	finally
    //   2556	2564	2769	finally
    //   2573	2582	2769	finally
    //   2591	2599	2769	finally
    //   2608	2615	2769	finally
    //   2624	2639	2769	finally
    //   2648	2657	2769	finally
    //   2666	2674	2769	finally
    //   2683	2691	2769	finally
    //   2700	2715	2769	finally
    //   2724	2734	2769	finally
    //   110	117	2773	java/lang/OutOfMemoryError
    //   126	133	2773	java/lang/OutOfMemoryError
    //   142	149	2773	java/lang/OutOfMemoryError
    //   158	168	2773	java/lang/OutOfMemoryError
    //   177	182	2773	java/lang/OutOfMemoryError
    //   191	196	2773	java/lang/OutOfMemoryError
    //   205	210	2773	java/lang/OutOfMemoryError
    //   219	223	2773	java/lang/OutOfMemoryError
    //   232	241	2773	java/lang/OutOfMemoryError
    //   258	263	2773	java/lang/OutOfMemoryError
    //   272	286	2773	java/lang/OutOfMemoryError
    //   300	305	2773	java/lang/OutOfMemoryError
    //   314	323	2773	java/lang/OutOfMemoryError
    //   332	341	2773	java/lang/OutOfMemoryError
    //   350	359	2773	java/lang/OutOfMemoryError
    //   368	377	2773	java/lang/OutOfMemoryError
    //   386	394	2773	java/lang/OutOfMemoryError
    //   403	412	2773	java/lang/OutOfMemoryError
    //   421	430	2773	java/lang/OutOfMemoryError
    //   439	448	2773	java/lang/OutOfMemoryError
    //   457	464	2773	java/lang/OutOfMemoryError
    //   473	482	2773	java/lang/OutOfMemoryError
    //   491	501	2773	java/lang/OutOfMemoryError
    //   510	516	2773	java/lang/OutOfMemoryError
    //   525	538	2773	java/lang/OutOfMemoryError
    //   547	553	2773	java/lang/OutOfMemoryError
    //   562	567	2773	java/lang/OutOfMemoryError
    //   573	578	2773	java/lang/OutOfMemoryError
    //   590	595	2773	java/lang/OutOfMemoryError
    //   604	609	2773	java/lang/OutOfMemoryError
    //   618	634	2773	java/lang/OutOfMemoryError
    //   643	652	2773	java/lang/OutOfMemoryError
    //   661	667	2773	java/lang/OutOfMemoryError
    //   681	689	2773	java/lang/OutOfMemoryError
    //   698	707	2773	java/lang/OutOfMemoryError
    //   719	726	2773	java/lang/OutOfMemoryError
    //   740	756	2773	java/lang/OutOfMemoryError
    //   769	800	2773	java/lang/OutOfMemoryError
    //   813	817	2773	java/lang/OutOfMemoryError
    //   842	851	2773	java/lang/OutOfMemoryError
    //   860	865	2773	java/lang/OutOfMemoryError
    //   874	884	2773	java/lang/OutOfMemoryError
    //   896	906	2773	java/lang/OutOfMemoryError
    //   915	926	2773	java/lang/OutOfMemoryError
    //   953	964	2773	java/lang/OutOfMemoryError
    //   973	978	2773	java/lang/OutOfMemoryError
    //   994	1004	2773	java/lang/OutOfMemoryError
    //   1013	1018	2773	java/lang/OutOfMemoryError
    //   1027	1038	2773	java/lang/OutOfMemoryError
    //   1047	1056	2773	java/lang/OutOfMemoryError
    //   1065	1077	2773	java/lang/OutOfMemoryError
    //   1086	1100	2773	java/lang/OutOfMemoryError
    //   1109	1128	2773	java/lang/OutOfMemoryError
    //   1137	1146	2773	java/lang/OutOfMemoryError
    //   1163	1168	2773	java/lang/OutOfMemoryError
    //   1177	1191	2773	java/lang/OutOfMemoryError
    //   1205	1210	2773	java/lang/OutOfMemoryError
    //   1219	1228	2773	java/lang/OutOfMemoryError
    //   1237	1246	2773	java/lang/OutOfMemoryError
    //   1255	1264	2773	java/lang/OutOfMemoryError
    //   1273	1282	2773	java/lang/OutOfMemoryError
    //   1291	1299	2773	java/lang/OutOfMemoryError
    //   1308	1317	2773	java/lang/OutOfMemoryError
    //   1326	1335	2773	java/lang/OutOfMemoryError
    //   1344	1353	2773	java/lang/OutOfMemoryError
    //   1362	1369	2773	java/lang/OutOfMemoryError
    //   1378	1387	2773	java/lang/OutOfMemoryError
    //   1396	1406	2773	java/lang/OutOfMemoryError
    //   1415	1422	2773	java/lang/OutOfMemoryError
    //   1435	1439	2773	java/lang/OutOfMemoryError
    //   1465	1472	2773	java/lang/OutOfMemoryError
    //   1481	1488	2773	java/lang/OutOfMemoryError
    //   1497	1504	2773	java/lang/OutOfMemoryError
    //   1513	1523	2773	java/lang/OutOfMemoryError
    //   1532	1537	2773	java/lang/OutOfMemoryError
    //   1546	1551	2773	java/lang/OutOfMemoryError
    //   1560	1565	2773	java/lang/OutOfMemoryError
    //   1574	1578	2773	java/lang/OutOfMemoryError
    //   1587	1599	2773	java/lang/OutOfMemoryError
    //   1608	1614	2773	java/lang/OutOfMemoryError
    //   1623	1627	2773	java/lang/OutOfMemoryError
    //   1633	1638	2773	java/lang/OutOfMemoryError
    //   1650	1655	2773	java/lang/OutOfMemoryError
    //   1664	1669	2773	java/lang/OutOfMemoryError
    //   1678	1694	2773	java/lang/OutOfMemoryError
    //   1703	1712	2773	java/lang/OutOfMemoryError
    //   1721	1727	2773	java/lang/OutOfMemoryError
    //   1741	1749	2773	java/lang/OutOfMemoryError
    //   1758	1767	2773	java/lang/OutOfMemoryError
    //   1779	1786	2773	java/lang/OutOfMemoryError
    //   1800	1816	2773	java/lang/OutOfMemoryError
    //   1829	1860	2773	java/lang/OutOfMemoryError
    //   1873	1877	2773	java/lang/OutOfMemoryError
    //   1902	1911	2773	java/lang/OutOfMemoryError
    //   1920	1925	2773	java/lang/OutOfMemoryError
    //   1934	1944	2773	java/lang/OutOfMemoryError
    //   1956	1966	2773	java/lang/OutOfMemoryError
    //   1975	1986	2773	java/lang/OutOfMemoryError
    //   2013	2022	2773	java/lang/OutOfMemoryError
    //   2031	2042	2773	java/lang/OutOfMemoryError
    //   2051	2060	2773	java/lang/OutOfMemoryError
    //   2087	2095	2773	java/lang/OutOfMemoryError
    //   2104	2113	2773	java/lang/OutOfMemoryError
    //   2128	2137	2773	java/lang/OutOfMemoryError
    //   2146	2156	2773	java/lang/OutOfMemoryError
    //   2165	2173	2773	java/lang/OutOfMemoryError
    //   2182	2190	2773	java/lang/OutOfMemoryError
    //   2199	2208	2773	java/lang/OutOfMemoryError
    //   2217	2225	2773	java/lang/OutOfMemoryError
    //   2234	2241	2773	java/lang/OutOfMemoryError
    //   2250	2265	2773	java/lang/OutOfMemoryError
    //   2274	2283	2773	java/lang/OutOfMemoryError
    //   2292	2300	2773	java/lang/OutOfMemoryError
    //   2309	2317	2773	java/lang/OutOfMemoryError
    //   2326	2341	2773	java/lang/OutOfMemoryError
    //   2350	2360	2773	java/lang/OutOfMemoryError
    //   2387	2396	2773	java/lang/OutOfMemoryError
    //   2405	2416	2773	java/lang/OutOfMemoryError
    //   2425	2434	2773	java/lang/OutOfMemoryError
    //   2461	2469	2773	java/lang/OutOfMemoryError
    //   2478	2487	2773	java/lang/OutOfMemoryError
    //   2502	2511	2773	java/lang/OutOfMemoryError
    //   2520	2530	2773	java/lang/OutOfMemoryError
    //   2539	2547	2773	java/lang/OutOfMemoryError
    //   2556	2564	2773	java/lang/OutOfMemoryError
    //   2573	2582	2773	java/lang/OutOfMemoryError
    //   2591	2599	2773	java/lang/OutOfMemoryError
    //   2608	2615	2773	java/lang/OutOfMemoryError
    //   2624	2639	2773	java/lang/OutOfMemoryError
    //   2648	2657	2773	java/lang/OutOfMemoryError
    //   2666	2674	2773	java/lang/OutOfMemoryError
    //   2683	2691	2773	java/lang/OutOfMemoryError
    //   2700	2715	2773	java/lang/OutOfMemoryError
    //   2724	2734	2773	java/lang/OutOfMemoryError
    //   110	117	2780	java/lang/Exception
    //   126	133	2780	java/lang/Exception
    //   142	149	2780	java/lang/Exception
    //   158	168	2780	java/lang/Exception
    //   177	182	2780	java/lang/Exception
    //   191	196	2780	java/lang/Exception
    //   205	210	2780	java/lang/Exception
    //   219	223	2780	java/lang/Exception
    //   232	241	2780	java/lang/Exception
    //   258	263	2780	java/lang/Exception
    //   272	286	2780	java/lang/Exception
    //   300	305	2780	java/lang/Exception
    //   314	323	2780	java/lang/Exception
    //   332	341	2780	java/lang/Exception
    //   350	359	2780	java/lang/Exception
    //   368	377	2780	java/lang/Exception
    //   386	394	2780	java/lang/Exception
    //   403	412	2780	java/lang/Exception
    //   421	430	2780	java/lang/Exception
    //   439	448	2780	java/lang/Exception
    //   457	464	2780	java/lang/Exception
    //   473	482	2780	java/lang/Exception
    //   491	501	2780	java/lang/Exception
    //   510	516	2780	java/lang/Exception
    //   525	538	2780	java/lang/Exception
    //   547	553	2780	java/lang/Exception
    //   562	567	2780	java/lang/Exception
    //   590	595	2780	java/lang/Exception
    //   604	609	2780	java/lang/Exception
    //   618	634	2780	java/lang/Exception
    //   643	652	2780	java/lang/Exception
    //   661	667	2780	java/lang/Exception
    //   681	689	2780	java/lang/Exception
    //   698	707	2780	java/lang/Exception
    //   719	726	2780	java/lang/Exception
    //   740	756	2780	java/lang/Exception
    //   769	800	2780	java/lang/Exception
    //   813	817	2780	java/lang/Exception
    //   842	851	2780	java/lang/Exception
    //   860	865	2780	java/lang/Exception
    //   874	884	2780	java/lang/Exception
    //   896	906	2780	java/lang/Exception
    //   915	926	2780	java/lang/Exception
    //   953	964	2780	java/lang/Exception
    //   973	978	2780	java/lang/Exception
    //   994	1004	2780	java/lang/Exception
    //   1013	1018	2780	java/lang/Exception
    //   1027	1038	2780	java/lang/Exception
    //   1047	1056	2780	java/lang/Exception
    //   1065	1077	2780	java/lang/Exception
    //   1086	1100	2780	java/lang/Exception
    //   1109	1128	2780	java/lang/Exception
    //   1137	1146	2780	java/lang/Exception
    //   1163	1168	2780	java/lang/Exception
    //   1177	1191	2780	java/lang/Exception
    //   1205	1210	2780	java/lang/Exception
    //   1219	1228	2780	java/lang/Exception
    //   1237	1246	2780	java/lang/Exception
    //   1255	1264	2780	java/lang/Exception
    //   1273	1282	2780	java/lang/Exception
    //   1291	1299	2780	java/lang/Exception
    //   1308	1317	2780	java/lang/Exception
    //   1326	1335	2780	java/lang/Exception
    //   1344	1353	2780	java/lang/Exception
    //   1362	1369	2780	java/lang/Exception
    //   1378	1387	2780	java/lang/Exception
    //   1396	1406	2780	java/lang/Exception
    //   1415	1422	2780	java/lang/Exception
    //   1435	1439	2780	java/lang/Exception
    //   1465	1472	2780	java/lang/Exception
    //   1481	1488	2780	java/lang/Exception
    //   1497	1504	2780	java/lang/Exception
    //   1513	1523	2780	java/lang/Exception
    //   1532	1537	2780	java/lang/Exception
    //   1546	1551	2780	java/lang/Exception
    //   1560	1565	2780	java/lang/Exception
    //   1574	1578	2780	java/lang/Exception
    //   1587	1599	2780	java/lang/Exception
    //   1608	1614	2780	java/lang/Exception
    //   1623	1627	2780	java/lang/Exception
    //   1650	1655	2780	java/lang/Exception
    //   1664	1669	2780	java/lang/Exception
    //   1678	1694	2780	java/lang/Exception
    //   1703	1712	2780	java/lang/Exception
    //   1721	1727	2780	java/lang/Exception
    //   1741	1749	2780	java/lang/Exception
    //   1758	1767	2780	java/lang/Exception
    //   1779	1786	2780	java/lang/Exception
    //   1800	1816	2780	java/lang/Exception
    //   1829	1860	2780	java/lang/Exception
    //   1873	1877	2780	java/lang/Exception
    //   1902	1911	2780	java/lang/Exception
    //   1920	1925	2780	java/lang/Exception
    //   1934	1944	2780	java/lang/Exception
    //   1956	1966	2780	java/lang/Exception
    //   1975	1986	2780	java/lang/Exception
    //   2013	2022	2780	java/lang/Exception
    //   2031	2042	2780	java/lang/Exception
    //   2051	2060	2780	java/lang/Exception
    //   2087	2095	2780	java/lang/Exception
    //   2104	2113	2780	java/lang/Exception
    //   2128	2137	2780	java/lang/Exception
    //   2146	2156	2780	java/lang/Exception
    //   2165	2173	2780	java/lang/Exception
    //   2182	2190	2780	java/lang/Exception
    //   2199	2208	2780	java/lang/Exception
    //   2217	2225	2780	java/lang/Exception
    //   2234	2241	2780	java/lang/Exception
    //   2250	2265	2780	java/lang/Exception
    //   2274	2283	2780	java/lang/Exception
    //   2292	2300	2780	java/lang/Exception
    //   2309	2317	2780	java/lang/Exception
    //   2326	2341	2780	java/lang/Exception
    //   2350	2360	2780	java/lang/Exception
    //   2387	2396	2780	java/lang/Exception
    //   2405	2416	2780	java/lang/Exception
    //   2425	2434	2780	java/lang/Exception
    //   2461	2469	2780	java/lang/Exception
    //   2478	2487	2780	java/lang/Exception
    //   2502	2511	2780	java/lang/Exception
    //   2520	2530	2780	java/lang/Exception
    //   2539	2547	2780	java/lang/Exception
    //   2556	2564	2780	java/lang/Exception
    //   2573	2582	2780	java/lang/Exception
    //   2591	2599	2780	java/lang/Exception
    //   2608	2615	2780	java/lang/Exception
    //   2624	2639	2780	java/lang/Exception
    //   2648	2657	2780	java/lang/Exception
    //   2666	2674	2780	java/lang/Exception
    //   2683	2691	2780	java/lang/Exception
    //   2700	2715	2780	java/lang/Exception
    //   2724	2734	2780	java/lang/Exception
    //   80	101	2787	finally
    //   80	101	2794	java/lang/OutOfMemoryError
    //   80	101	2934	java/lang/Exception
    //   1447	1456	3083	finally
    //   2800	2809	3083	finally
    //   2812	2823	3083	finally
    //   2826	2835	3083	finally
    //   2838	2847	3083	finally
    //   2850	2858	3083	finally
    //   2861	2871	3083	finally
    //   2874	2890	3083	finally
    //   2893	2904	3083	finally
    //   2940	2949	3083	finally
    //   2952	2963	3083	finally
    //   2966	2975	3083	finally
    //   2978	2987	3083	finally
    //   2990	2998	3083	finally
    //   3001	3011	3083	finally
    //   3014	3030	3083	finally
    //   3033	3044	3083	finally
    //   573	578	3126	java/lang/Exception
    //   1633	1638	3130	java/lang/Exception
    //   1447	1456	3144	java/lang/OutOfMemoryError
    //   1447	1456	3148	java/lang/Exception
  }
  
  private boolean isAppHomeCateGory()
  {
    try
    {
      new HashSet();
      Object localObject1 = this.mContext.getPackageManager();
      Object localObject2 = new Intent("android.intent.action.MAIN");
      ((Intent)localObject2).addCategory("android.intent.category.HOME");
      localObject2 = ((PackageManager)localObject1).queryIntentActivities((Intent)localObject2, 65536);
      localObject1 = this.mContext.getApplicationContext().getPackageName();
      localObject2 = ((List)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        boolean bool = ((String)localObject1).equalsIgnoreCase(((ResolveInfo)((Iterator)localObject2).next()).activityInfo.packageName);
        if (bool) {
          return true;
        }
      }
      return false;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  private void scheduleFadeOutScrollBar(int paramInt)
  {
    int i = paramInt;
    if (this.mScrollCache.a() == b.a.a) {
      i = Math.max(750, paramInt);
    }
    long l = AnimationUtils.currentAnimationTimeMillis() + i;
    com.tencent.smtt.d.b localb = this.mScrollCache;
    localb.jdField_a_of_type_Long = l;
    localb.a(b.a.b);
    sHandler.removeCallbacks(this.mScrollCache);
    sHandler.postAtTime(this.mScrollCache, l);
  }
  
  /* Error */
  public static void setTbsCoreVersion(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +442 -> 443
    //   4: aload_0
    //   5: ldc -128
    //   7: invokevirtual 400	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   10: ifeq +4 -> 14
    //   13: return
    //   14: invokestatic 278	com/tencent/smtt/webkit/ContextHolder:getInstance	()Lcom/tencent/smtt/webkit/ContextHolder;
    //   17: invokevirtual 1061	com/tencent/smtt/webkit/ContextHolder:getApplicationContext	()Landroid/content/Context;
    //   20: ldc 41
    //   22: iconst_0
    //   23: invokevirtual 1065	android/content/Context:getDir	(Ljava/lang/String;I)Ljava/io/File;
    //   26: astore 7
    //   28: new 1067	java/io/File
    //   31: dup
    //   32: aload 7
    //   34: ldc 47
    //   36: invokespecial 1070	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   39: astore_2
    //   40: aload_2
    //   41: invokevirtual 1073	java/io/File:canWrite	()Z
    //   44: istore_1
    //   45: aconst_null
    //   46: astore 5
    //   48: aconst_null
    //   49: astore 6
    //   51: iload_1
    //   52: ifeq +187 -> 239
    //   55: new 1067	java/io/File
    //   58: dup
    //   59: aload_2
    //   60: ldc 38
    //   62: invokespecial 1070	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   65: astore 8
    //   67: new 1075	java/util/Properties
    //   70: dup
    //   71: invokespecial 1076	java/util/Properties:<init>	()V
    //   74: astore 4
    //   76: new 1078	java/io/FileInputStream
    //   79: dup
    //   80: aload 8
    //   82: invokespecial 1081	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   85: astore_3
    //   86: aload_3
    //   87: astore_2
    //   88: aload 4
    //   90: aload_3
    //   91: invokevirtual 1084	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   94: aload_3
    //   95: astore_2
    //   96: new 1086	java/io/FileOutputStream
    //   99: dup
    //   100: aload 8
    //   102: invokespecial 1087	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   105: astore 8
    //   107: aload_3
    //   108: astore_2
    //   109: aload 4
    //   111: ldc 32
    //   113: aload_0
    //   114: invokevirtual 1091	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   117: pop
    //   118: aload_3
    //   119: astore_2
    //   120: aload 4
    //   122: aload 8
    //   124: aconst_null
    //   125: invokevirtual 1095	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   128: aload_3
    //   129: invokevirtual 1098	java/io/FileInputStream:close	()V
    //   132: goto +107 -> 239
    //   135: astore_2
    //   136: aload_2
    //   137: invokevirtual 1099	java/io/IOException:printStackTrace	()V
    //   140: goto +99 -> 239
    //   143: astore 4
    //   145: goto +13 -> 158
    //   148: astore_0
    //   149: aconst_null
    //   150: astore_2
    //   151: goto +70 -> 221
    //   154: astore 4
    //   156: aconst_null
    //   157: astore_3
    //   158: aload_3
    //   159: astore_2
    //   160: aload 4
    //   162: invokevirtual 1100	java/lang/Throwable:printStackTrace	()V
    //   165: aload_3
    //   166: astore_2
    //   167: new 677	java/lang/StringBuilder
    //   170: dup
    //   171: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   174: astore 8
    //   176: aload_3
    //   177: astore_2
    //   178: aload 8
    //   180: ldc_w 1102
    //   183: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   186: pop
    //   187: aload_3
    //   188: astore_2
    //   189: aload 8
    //   191: aload 4
    //   193: invokevirtual 1105	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   196: pop
    //   197: aload_3
    //   198: astore_2
    //   199: ldc -42
    //   201: aload 8
    //   203: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   206: invokestatic 1109	com/tencent/smtt/webkit/q:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   209: aload_3
    //   210: ifnull +29 -> 239
    //   213: aload_3
    //   214: invokevirtual 1098	java/io/FileInputStream:close	()V
    //   217: goto +22 -> 239
    //   220: astore_0
    //   221: aload_2
    //   222: ifnull +15 -> 237
    //   225: aload_2
    //   226: invokevirtual 1098	java/io/FileInputStream:close	()V
    //   229: goto +8 -> 237
    //   232: astore_2
    //   233: aload_2
    //   234: invokevirtual 1099	java/io/IOException:printStackTrace	()V
    //   237: aload_0
    //   238: athrow
    //   239: new 1067	java/io/File
    //   242: dup
    //   243: aload 7
    //   245: ldc 44
    //   247: invokespecial 1070	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   250: astore_2
    //   251: aload_2
    //   252: invokevirtual 1073	java/io/File:canWrite	()Z
    //   255: ifeq +187 -> 442
    //   258: new 1067	java/io/File
    //   261: dup
    //   262: aload_2
    //   263: ldc 35
    //   265: invokespecial 1070	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   268: astore 7
    //   270: new 1075	java/util/Properties
    //   273: dup
    //   274: invokespecial 1076	java/util/Properties:<init>	()V
    //   277: astore 4
    //   279: aload 6
    //   281: astore_2
    //   282: new 1078	java/io/FileInputStream
    //   285: dup
    //   286: aload 7
    //   288: invokespecial 1081	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   291: astore_3
    //   292: aload 4
    //   294: aload_3
    //   295: invokevirtual 1084	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   298: new 1086	java/io/FileOutputStream
    //   301: dup
    //   302: aload 7
    //   304: invokespecial 1087	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   307: astore_2
    //   308: aload 4
    //   310: ldc 26
    //   312: aload_0
    //   313: invokevirtual 1091	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   316: pop
    //   317: aload 4
    //   319: ldc 29
    //   321: aload_0
    //   322: invokevirtual 1091	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   325: pop
    //   326: aload 4
    //   328: aload_2
    //   329: aconst_null
    //   330: invokevirtual 1095	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   333: aload_3
    //   334: invokevirtual 1098	java/io/FileInputStream:close	()V
    //   337: return
    //   338: astore_0
    //   339: aload_0
    //   340: invokevirtual 1099	java/io/IOException:printStackTrace	()V
    //   343: return
    //   344: astore_0
    //   345: aload_3
    //   346: astore_2
    //   347: goto +77 -> 424
    //   350: astore_2
    //   351: aload_3
    //   352: astore_0
    //   353: aload_2
    //   354: astore_3
    //   355: goto +11 -> 366
    //   358: astore_0
    //   359: goto +65 -> 424
    //   362: astore_3
    //   363: aload 5
    //   365: astore_0
    //   366: aload_0
    //   367: astore_2
    //   368: aload_3
    //   369: invokevirtual 1100	java/lang/Throwable:printStackTrace	()V
    //   372: aload_0
    //   373: astore_2
    //   374: new 677	java/lang/StringBuilder
    //   377: dup
    //   378: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   381: astore 4
    //   383: aload_0
    //   384: astore_2
    //   385: aload 4
    //   387: ldc_w 1111
    //   390: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   393: pop
    //   394: aload_0
    //   395: astore_2
    //   396: aload 4
    //   398: aload_3
    //   399: invokevirtual 1105	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   402: pop
    //   403: aload_0
    //   404: astore_2
    //   405: ldc -42
    //   407: aload 4
    //   409: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   412: invokestatic 1109	com/tencent/smtt/webkit/q:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   415: aload_0
    //   416: ifnull +26 -> 442
    //   419: aload_0
    //   420: invokevirtual 1098	java/io/FileInputStream:close	()V
    //   423: return
    //   424: aload_2
    //   425: ifnull +15 -> 440
    //   428: aload_2
    //   429: invokevirtual 1098	java/io/FileInputStream:close	()V
    //   432: goto +8 -> 440
    //   435: astore_2
    //   436: aload_2
    //   437: invokevirtual 1099	java/io/IOException:printStackTrace	()V
    //   440: aload_0
    //   441: athrow
    //   442: return
    //   443: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	444	0	paramString	String
    //   44	8	1	bool	boolean
    //   39	81	2	localObject1	Object
    //   135	2	2	localIOException1	java.io.IOException
    //   150	76	2	localObject2	Object
    //   232	2	2	localIOException2	java.io.IOException
    //   250	97	2	localObject3	Object
    //   350	4	2	localThrowable1	Throwable
    //   367	62	2	str	String
    //   435	2	2	localIOException3	java.io.IOException
    //   85	270	3	localObject4	Object
    //   362	37	3	localThrowable2	Throwable
    //   74	47	4	localProperties	java.util.Properties
    //   143	1	4	localThrowable3	Throwable
    //   154	38	4	localThrowable4	Throwable
    //   277	131	4	localObject5	Object
    //   46	318	5	localObject6	Object
    //   49	231	6	localObject7	Object
    //   26	277	7	localFile	java.io.File
    //   65	137	8	localObject8	Object
    // Exception table:
    //   from	to	target	type
    //   128	132	135	java/io/IOException
    //   213	217	135	java/io/IOException
    //   88	94	143	java/lang/Throwable
    //   96	107	143	java/lang/Throwable
    //   109	118	143	java/lang/Throwable
    //   120	128	143	java/lang/Throwable
    //   76	86	148	finally
    //   76	86	154	java/lang/Throwable
    //   88	94	220	finally
    //   96	107	220	finally
    //   109	118	220	finally
    //   120	128	220	finally
    //   160	165	220	finally
    //   167	176	220	finally
    //   178	187	220	finally
    //   189	197	220	finally
    //   199	209	220	finally
    //   225	229	232	java/io/IOException
    //   333	337	338	java/io/IOException
    //   419	423	338	java/io/IOException
    //   292	333	344	finally
    //   292	333	350	java/lang/Throwable
    //   282	292	358	finally
    //   368	372	358	finally
    //   374	383	358	finally
    //   385	394	358	finally
    //   396	403	358	finally
    //   405	415	358	finally
    //   282	292	362	java/lang/Throwable
    //   428	432	435	java/io/IOException
  }
  
  private void showLoading()
  {
    this.mLoadingView = new LongClickImageFirstView(this.mContext, this, this.mImageBase64);
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1);
    ((FrameLayout)getParent()).addView(this.mLoadingView, localLayoutParams);
  }
  
  private void updateLoading()
  {
    LongClickImageFirstView localLongClickImageFirstView = this.mLoadingView;
    if (localLongClickImageFirstView != null) {
      localLongClickImageFirstView.updateImage(this.mBmpFromCore);
    }
  }
  
  public void attachSelectionView(View paramView)
  {
    if (paramView != null) {
      getRealWebView().addView(paramView, new AbsoluteLayout.LayoutParams(-2, -2, 0, 0));
    }
  }
  
  public boolean awakenScrollBars()
  {
    com.tencent.smtt.d.b localb = this.mScrollCache;
    return (localb != null) && (awakenScrollBars(localb.jdField_b_of_type_Int, true));
  }
  
  public boolean awakenScrollBars(int paramInt)
  {
    return awakenScrollBars(paramInt, true);
  }
  
  public boolean awakenScrollBars(int paramInt, boolean paramBoolean)
  {
    com.tencent.smtt.d.b localb = this.mScrollCache;
    if (localb != null)
    {
      if (!localb.jdField_a_of_type_Boolean) {
        return false;
      }
      if (localb.jdField_a_of_type_ComTencentSmttDA == null) {
        localb.jdField_a_of_type_ComTencentSmttDA = new com.tencent.smtt.d.a();
      }
      if (localb.jdField_b_of_type_Boolean) {
        return true;
      }
      if ((!getRealWebView().isHorizontalScrollBarEnabled()) && (!getRealWebView().isVerticalScrollBarEnabled())) {
        return false;
      }
      if (paramBoolean) {
        getRealWebView().invalidate();
      }
      scheduleFadeOutScrollBar(paramInt);
      return true;
    }
    return false;
  }
  
  public void clearMemoryCache() {}
  
  public void clearServiceWorkerCache()
  {
    getWebViewProvider().getExtension().clearServiceWorkerCache();
  }
  
  public void clearTextEntry()
  {
    super.clearTextEntry();
  }
  
  public void clearTextFieldLongPressStatus() {}
  
  public void computeScroll()
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = getWebViewClientExtension();
    if ((this.isOunterClientForUIEventSetted) && (localIX5WebViewClientExtension != null))
    {
      localIX5WebViewClientExtension.computeScroll(getView());
      return;
    }
    super.super_computeScroll();
  }
  
  public void copyText()
  {
    copySelection();
  }
  
  public void cutText(CharSequence paramCharSequence)
  {
    cutSelection();
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = getWebViewClientExtension();
    if ((getRealWebView().getVisibleTitleHeight() > 0) && ((int)paramMotionEvent.getY() < getRealWebView().getVisibleTitleHeight()) && (!this.mScrollCache.jdField_b_of_type_Boolean) && (!isSelectionMode())) {
      return false;
    }
    if ((this.isOunterClientForUIEventSetted) && (localIX5WebViewClientExtension != null)) {
      return localIX5WebViewClientExtension.dispatchTouchEvent(paramMotionEvent, getView());
    }
    try
    {
      boolean bool = super.super_dispatchTouchEvent(paramMotionEvent);
      return bool;
    }
    catch (Exception paramMotionEvent)
    {
      paramMotionEvent.printStackTrace();
    }
    return false;
  }
  
  public void dispatchWebViewTouchEvent(MotionEvent paramMotionEvent)
  {
    dispatchTouchEvent(paramMotionEvent);
  }
  
  public void doTranslateAction(int paramInt)
  {
    getWebViewProvider().getExtension().doTranslateAction(paramInt);
  }
  
  public void documentAsText(Message paramMessage) {}
  
  public void documentDumpRenderTree(Message paramMessage) {}
  
  public void draw(Canvas paramCanvas)
  {
    super.super_draw(paramCanvas);
    onDrawScrollBarsX5(paramCanvas);
  }
  
  public boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    adjustTitleViewPosition();
    return super.super_drawChild(paramCanvas, paramView, paramLong);
  }
  
  public void dumpViewHierarchyWithProperties(BufferedWriter paramBufferedWriter, int paramInt) {}
  
  public void dumpViewportForLayoutTest(Message paramMessage) {}
  
  public void enterSelectionMode(boolean paramBoolean)
  {
    super.enterSelectionMode(paramBoolean);
  }
  
  public View findHierarchyView(String paramString, int paramInt)
  {
    return null;
  }
  
  public void focusAndPopupIM(String paramString)
  {
    getWebViewProvider().getExtension().focusAndPopupIM(paramString);
  }
  
  public void focusTtsNode(int paramInt)
  {
    getWebViewProvider().getExtension().focusTtsNode(paramInt, true);
  }
  
  public void focusTtsNode(int paramInt, boolean paramBoolean)
  {
    getWebViewProvider().getExtension().focusTtsNode(paramInt, paramBoolean);
  }
  
  public void fullScreenPluginHidden()
  {
    super.fullScreenPluginHidden();
  }
  
  public IAdSettings getAdSettings()
  {
    if (this.mAdWebViewController != null) {
      return this.mAdWebViewController.getAdSettings();
    }
    return null;
  }
  
  public ArrayList<IX5WebViewBase.ImageInfo> getAllImageInfo()
  {
    return getWebViewProvider().getExtension().getAllImageInfo();
  }
  
  public Bitmap getBitmapByIndex(int paramInt)
  {
    return getWebViewProvider().getExtension().getBitmapByIndex(paramInt);
  }
  
  public int getContentOffsetY()
  {
    return getRealWebView().getVisibleTitleHeight();
  }
  
  public String getDocumentOuterHTML()
  {
    return getWebViewProvider().getExtension().getDocumentOuterHTML();
  }
  
  public int getEventType()
  {
    return this.mEventType;
  }
  
  public void getFakeLoginStatus(Bundle paramBundle, ValueCallback<Bundle> paramValueCallback)
  {
    getWebViewProvider().getExtension().getFakeLoginStatus(paramBundle, paramValueCallback);
  }
  
  public String getFocusCandidateText()
  {
    if (getHitTestResult() != null)
    {
      Object localObject = getHitTestResult().getData();
      if ((localObject instanceof IX5WebViewBase.HitTestResult.EditableData)) {
        return ((IX5WebViewBase.HitTestResult.EditableData)localObject).mEditableText;
      }
    }
    return null;
  }
  
  public int getGoBackOrForwardToDesiredSteps(int paramInt)
  {
    return getWebViewProvider().getExtension().getGoBackOrForwardToDesiredSteps(paramInt);
  }
  
  public int getHostHeight()
  {
    return getRealWebView().getHeight();
  }
  
  public int[] getHostLocation()
  {
    int[] arrayOfInt = new int[2];
    getRealWebView().getLocationOnScreen(arrayOfInt);
    return arrayOfInt;
  }
  
  public int getHostWidth()
  {
    return getRealWebView().getWidth();
  }
  
  public int getLongPressTextExtensionMenu()
  {
    return this.mLongPressTextExtensionMenu;
  }
  
  public int getMainX5WebviewMaxOverScrollY()
  {
    return this.mMainX5WebviewMaxOverScrollY;
  }
  
  public boolean getMenuCleared()
  {
    if ((getX5ActionMode() != null) && (getX5ActionMode().getX5Menu() != null)) {
      return getX5ActionMode().getX5Menu().getMenuCleared();
    }
    return false;
  }
  
  public String getMiniProgramPkgName()
  {
    return this.mMiniProgramPkgName;
  }
  
  @UsedByReflection("MiniQB")
  public int getMiniQBSourcePosID()
  {
    return this.mMiniQBSourcePosID;
  }
  
  public OpenJsApiBridge getOpenJsApiBridge()
  {
    return getWebViewProvider().getOpenJsApiBridge();
  }
  
  public int getQQBrowserVersion()
  {
    int i = mCoreVersion;
    if (i > 0) {
      return i;
    }
    mTbsVersion = ContextHolder.getInstance().getTbsCoreVersion();
    try
    {
      if ((mTbsVersion != null) && (!mTbsVersion.equals(""))) {
        mCoreVersion = Integer.parseInt(mTbsVersion.substring(2, 4));
      }
      return mCoreVersion;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public boolean getQuickSelectCopy()
  {
    return this.mbQuickSelectCopy;
  }
  
  public int getRealScrollY()
  {
    return getRealWebView().getScrollY();
  }
  
  public Bundle getSdkQBStatisticsInfo()
  {
    Bundle localBundle = new Bundle();
    String str4 = SmttServiceProxy.getInstance().getGUID();
    String str3 = SmttServiceProxy.getInstance().getQUA2();
    String str2 = SmttServiceProxy.getInstance().getLC();
    String str1 = str4;
    if (str4 == null) {
      str1 = "";
    }
    localBundle.putString("guid", str1);
    str1 = str3;
    if (str3 == null) {
      str1 = "";
    }
    localBundle.putString("qua2", str1);
    str1 = str2;
    if (str2 == null) {
      str1 = "";
    }
    localBundle.putString("lc", str1);
    return localBundle;
  }
  
  public String getSelectionText()
  {
    return getSelection();
  }
  
  public Point getSinglePressPoint()
  {
    return null;
  }
  
  public int getTitleHeight()
  {
    View localView = this.mTitleView;
    if ((localView != null) && (localView.getVisibility() != 8)) {
      return localView.getHeight();
    }
    return 0;
  }
  
  public void getTtsTextAsync(int paramInt)
  {
    getWebViewProvider().getExtension().getTtsTextAsync(paramInt);
  }
  
  public List<String> getUserSelectedHiddenDomains()
  {
    return getWebViewProvider().getExtension().getUserSelectedHiddenDomains();
  }
  
  public View getView()
  {
    return getRealWebView();
  }
  
  public int getWebTextScrollDis()
  {
    return 0;
  }
  
  public X5ActionMode getX5ActionMode()
  {
    return this.mX5ActionMode;
  }
  
  public IX5WebViewExtension getX5WebViewExtension()
  {
    return this;
  }
  
  public boolean hasShownScreenAd()
  {
    if (this.mAdWebViewController != null) {
      return this.mAdWebViewController.hasShownScreenAd();
    }
    return false;
  }
  
  public void hideScreenAd()
  {
    SmttServiceProxy.getInstance().hideScreenAd(this);
  }
  
  public void hideScrollBar(com.tencent.smtt.d.b paramb)
  {
    paramb.a(b.a.a);
    paramb.jdField_a_of_type_ComTencentSmttDA.b(false);
    paramb.jdField_a_of_type_ComTencentSmttDA.a(false);
  }
  
  public void hideUserSelectedElement()
  {
    getWebViewProvider().getExtension().hideUserSelectedElement(mUrl);
  }
  
  /* Error */
  public void imageQueryFromX5Core(Bitmap paramBitmap)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +1219 -> 1220
    //   4: aconst_null
    //   5: astore 7
    //   7: aconst_null
    //   8: astore 5
    //   10: aconst_null
    //   11: astore 6
    //   13: aconst_null
    //   14: astore 4
    //   16: aload 7
    //   18: astore_3
    //   19: aload_0
    //   20: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   23: sipush 1017
    //   26: if_icmpne +25 -> 51
    //   29: aload 7
    //   31: astore_3
    //   32: aload_0
    //   33: aload_1
    //   34: putfield 188	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mBmpFromCore	Landroid/graphics/Bitmap;
    //   37: aload 7
    //   39: astore_3
    //   40: aload_0
    //   41: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   44: sipush 1018
    //   47: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   50: pop
    //   51: aload 7
    //   53: astore_3
    //   54: new 903	java/io/ByteArrayOutputStream
    //   57: dup
    //   58: invokespecial 904	java/io/ByteArrayOutputStream:<init>	()V
    //   61: astore 8
    //   63: aload 7
    //   65: astore_3
    //   66: aload_1
    //   67: getstatic 910	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   70: bipush 100
    //   72: aload 8
    //   74: invokevirtual 916	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   77: pop
    //   78: aload 7
    //   80: astore_3
    //   81: aload_0
    //   82: aload 8
    //   84: invokevirtual 919	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   87: bipush 10
    //   89: invokestatic 675	android/util/Base64:encodeToString	([BI)Ljava/lang/String;
    //   92: putfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   95: aload 7
    //   97: astore_3
    //   98: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   101: invokevirtual 759	com/tencent/smtt/webkit/service/SmttServiceProxy:getGUID	()Ljava/lang/String;
    //   104: astore 8
    //   106: aload 8
    //   108: ifnull +1117 -> 1225
    //   111: aload 8
    //   113: astore_1
    //   114: aload 7
    //   116: astore_3
    //   117: aload 8
    //   119: invokevirtual 762	java/lang/String:isEmpty	()Z
    //   122: ifeq +6 -> 128
    //   125: goto +1100 -> 1225
    //   128: aload 7
    //   130: astore_3
    //   131: ldc_w 766
    //   134: aload_1
    //   135: ldc_w 768
    //   138: aload_0
    //   139: getfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   142: ldc_w 770
    //   145: invokestatic 772	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:getSig	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   148: pop
    //   149: aload 7
    //   151: astore_3
    //   152: ldc_w 766
    //   155: aload_1
    //   156: ldc_w 768
    //   159: aload_0
    //   160: getfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   163: ldc_w 770
    //   166: invokestatic 772	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:getSig	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   169: ldc_w 414
    //   172: invokestatic 820	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   175: astore 8
    //   177: aload 7
    //   179: astore_3
    //   180: aload_0
    //   181: aload_0
    //   182: getfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   185: ldc_w 414
    //   188: invokestatic 820	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   191: putfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   194: aload 7
    //   196: astore_3
    //   197: new 677	java/lang/StringBuilder
    //   200: dup
    //   201: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   204: astore 9
    //   206: aload 7
    //   208: astore_3
    //   209: aload 9
    //   211: ldc_w 822
    //   214: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   217: pop
    //   218: aload 7
    //   220: astore_3
    //   221: aload 9
    //   223: ldc_w 766
    //   226: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   229: pop
    //   230: aload 7
    //   232: astore_3
    //   233: aload 9
    //   235: ldc_w 824
    //   238: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   241: pop
    //   242: aload 7
    //   244: astore_3
    //   245: aload 9
    //   247: aload_1
    //   248: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   251: pop
    //   252: aload 7
    //   254: astore_3
    //   255: aload 9
    //   257: ldc_w 826
    //   260: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   263: pop
    //   264: aload 7
    //   266: astore_3
    //   267: aload 9
    //   269: ldc_w 768
    //   272: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   275: pop
    //   276: aload 7
    //   278: astore_3
    //   279: aload 9
    //   281: ldc_w 828
    //   284: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: pop
    //   288: aload 7
    //   290: astore_3
    //   291: aload 9
    //   293: aload 8
    //   295: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   298: pop
    //   299: aload 7
    //   301: astore_3
    //   302: aload 9
    //   304: ldc_w 830
    //   307: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   310: pop
    //   311: aload 7
    //   313: astore_3
    //   314: aload 9
    //   316: aload_0
    //   317: getfield 186	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageBase64	Ljava/lang/String;
    //   320: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   323: pop
    //   324: aload 7
    //   326: astore_3
    //   327: aload 9
    //   329: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   332: astore 8
    //   334: aload 7
    //   336: astore_3
    //   337: new 774	java/net/URL
    //   340: dup
    //   341: ldc_w 776
    //   344: invokespecial 777	java/net/URL:<init>	(Ljava/lang/String;)V
    //   347: invokevirtual 781	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   350: checkcast 783	java/net/HttpURLConnection
    //   353: astore_1
    //   354: aload_1
    //   355: sipush 4000
    //   358: invokevirtual 786	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   361: aload_1
    //   362: sipush 4000
    //   365: invokevirtual 789	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   368: aload_1
    //   369: ldc_w 791
    //   372: invokevirtual 794	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   375: aload_1
    //   376: ldc_w 796
    //   379: ldc_w 798
    //   382: invokevirtual 802	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   385: aload_1
    //   386: iconst_1
    //   387: invokevirtual 805	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   390: aload_1
    //   391: iconst_1
    //   392: invokevirtual 808	java/net/HttpURLConnection:setDoInput	(Z)V
    //   395: aload_1
    //   396: iconst_0
    //   397: invokevirtual 811	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   400: aload_1
    //   401: invokevirtual 814	java/net/HttpURLConnection:connect	()V
    //   404: new 832	java/io/PrintWriter
    //   407: dup
    //   408: aload_1
    //   409: invokevirtual 836	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   412: invokespecial 839	java/io/PrintWriter:<init>	(Ljava/io/OutputStream;)V
    //   415: astore_3
    //   416: aload_3
    //   417: aload 8
    //   419: invokevirtual 842	java/io/PrintWriter:print	(Ljava/lang/String;)V
    //   422: aload_3
    //   423: invokevirtual 845	java/io/PrintWriter:flush	()V
    //   426: aload_1
    //   427: invokevirtual 849	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   430: astore_3
    //   431: goto +8 -> 439
    //   434: aload_1
    //   435: invokevirtual 852	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   438: astore_3
    //   439: aload_1
    //   440: invokevirtual 855	java/net/HttpURLConnection:getResponseCode	()I
    //   443: istore_2
    //   444: new 857	java/io/BufferedReader
    //   447: dup
    //   448: new 859	java/io/InputStreamReader
    //   451: dup
    //   452: aload_3
    //   453: invokespecial 862	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   456: invokespecial 865	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   459: astore 5
    //   461: new 867	java/lang/StringBuffer
    //   464: dup
    //   465: invokespecial 868	java/lang/StringBuffer:<init>	()V
    //   468: astore_3
    //   469: aload 5
    //   471: invokevirtual 871	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   474: astore 6
    //   476: aload 6
    //   478: ifnull +28 -> 506
    //   481: aload_3
    //   482: aload 6
    //   484: invokevirtual 874	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   487: pop
    //   488: aload_3
    //   489: ldc_w 684
    //   492: invokevirtual 874	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   495: pop
    //   496: goto -27 -> 469
    //   499: astore 5
    //   501: aload 5
    //   503: invokevirtual 1099	java/io/IOException:printStackTrace	()V
    //   506: aload_3
    //   507: invokevirtual 875	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   510: astore 5
    //   512: aload 4
    //   514: astore_3
    //   515: aload 5
    //   517: ifnull +71 -> 588
    //   520: new 558	org/json/JSONObject
    //   523: dup
    //   524: aload 5
    //   526: invokespecial 560	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   529: ldc_w 877
    //   532: invokevirtual 566	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   535: astore 6
    //   537: aload 4
    //   539: astore_3
    //   540: aload 6
    //   542: ifnull +46 -> 588
    //   545: new 558	org/json/JSONObject
    //   548: dup
    //   549: aload 6
    //   551: ldc_w 879
    //   554: ldc -128
    //   556: invokevirtual 883	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   559: ldc_w 885
    //   562: ldc -128
    //   564: invokevirtual 883	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   567: invokespecial 560	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   570: ldc_w 887
    //   573: invokevirtual 566	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   576: astore_3
    //   577: goto +11 -> 588
    //   580: astore_3
    //   581: aload_3
    //   582: invokevirtual 573	org/json/JSONException:printStackTrace	()V
    //   585: aload 4
    //   587: astore_3
    //   588: iload_2
    //   589: sipush 200
    //   592: if_icmpne +55 -> 647
    //   595: aload_3
    //   596: ifnull +51 -> 647
    //   599: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   602: ldc_w 889
    //   605: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   608: aload_0
    //   609: aload_3
    //   610: invokevirtual 895	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:loadUrl	(Ljava/lang/String;)V
    //   613: aload_0
    //   614: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   617: sipush 1017
    //   620: if_icmpeq +13 -> 633
    //   623: aload_0
    //   624: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   627: sipush 1018
    //   630: if_icmpne +217 -> 847
    //   633: aload_0
    //   634: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   637: sipush 1019
    //   640: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   643: pop
    //   644: goto +203 -> 847
    //   647: iload_2
    //   648: sipush 200
    //   651: if_icmpne +40 -> 691
    //   654: aload 5
    //   656: ifnull +35 -> 691
    //   659: aload_0
    //   660: aload 5
    //   662: invokespecial 924	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:canGetImageQueryClass	(Ljava/lang/String;)I
    //   665: ifne +26 -> 691
    //   668: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   671: aload_0
    //   672: getfield 190	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mImageQueryClass	Ljava/lang/String;
    //   675: iconst_0
    //   676: invokevirtual 928	com/tencent/smtt/webkit/service/SmttServiceProxy:showToast	(Ljava/lang/String;I)V
    //   679: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   682: ldc_w 889
    //   685: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   688: goto +159 -> 847
    //   691: iload_2
    //   692: sipush 200
    //   695: if_icmpne +28 -> 723
    //   698: aload 5
    //   700: ifnull +23 -> 723
    //   703: iconst_1
    //   704: aload_0
    //   705: getfield 192	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLastGetImageQueryClass	I
    //   708: if_icmpne +15 -> 723
    //   711: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   714: ldc_w 889
    //   717: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   720: goto +127 -> 847
    //   723: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   726: ldc_w 930
    //   729: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   732: ldc_w 932
    //   735: ldc_w 934
    //   738: invokestatic 936	com/tencent/smtt/webkit/SmttResource:getString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   741: astore_3
    //   742: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   745: aload_3
    //   746: iconst_0
    //   747: invokevirtual 928	com/tencent/smtt/webkit/service/SmttServiceProxy:showToast	(Ljava/lang/String;I)V
    //   750: new 938	java/util/HashMap
    //   753: dup
    //   754: invokespecial 939	java/util/HashMap:<init>	()V
    //   757: astore_3
    //   758: new 677	java/lang/StringBuilder
    //   761: dup
    //   762: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   765: astore 4
    //   767: aload 4
    //   769: ldc -128
    //   771: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   774: pop
    //   775: aload 4
    //   777: iload_2
    //   778: invokevirtual 942	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   781: pop
    //   782: aload_3
    //   783: ldc_w 944
    //   786: aload 4
    //   788: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   791: invokeinterface 247 3 0
    //   796: pop
    //   797: new 677	java/lang/StringBuilder
    //   800: dup
    //   801: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   804: astore 4
    //   806: aload 4
    //   808: ldc -128
    //   810: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   813: pop
    //   814: aload 4
    //   816: aload 5
    //   818: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   821: pop
    //   822: aload_3
    //   823: ldc_w 946
    //   826: aload 4
    //   828: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   831: invokeinterface 247 3 0
    //   836: pop
    //   837: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   840: ldc_w 948
    //   843: aload_3
    //   844: invokevirtual 952	com/tencent/smtt/webkit/service/SmttServiceProxy:upLoadToBeacon	(Ljava/lang/String;Ljava/util/Map;)V
    //   847: aload_1
    //   848: ifnull +7 -> 855
    //   851: aload_1
    //   852: invokevirtual 922	java/net/HttpURLConnection:disconnect	()V
    //   855: aload_0
    //   856: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   859: istore_2
    //   860: iload_2
    //   861: sipush 1017
    //   864: if_icmpeq +304 -> 1168
    //   867: iload_2
    //   868: sipush 1018
    //   871: if_icmpne +349 -> 1220
    //   874: goto +294 -> 1168
    //   877: astore_3
    //   878: goto +302 -> 1180
    //   881: astore 4
    //   883: goto +23 -> 906
    //   886: astore 4
    //   888: goto +153 -> 1041
    //   891: astore 4
    //   893: aload_3
    //   894: astore_1
    //   895: aload 4
    //   897: astore_3
    //   898: goto +282 -> 1180
    //   901: astore 4
    //   903: aload 5
    //   905: astore_1
    //   906: aload_1
    //   907: astore_3
    //   908: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   911: ldc_w 954
    //   914: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   917: aload_1
    //   918: astore_3
    //   919: aload_0
    //   920: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   923: sipush 1019
    //   926: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   929: pop
    //   930: aload_1
    //   931: astore_3
    //   932: new 938	java/util/HashMap
    //   935: dup
    //   936: invokespecial 939	java/util/HashMap:<init>	()V
    //   939: astore 5
    //   941: aload_1
    //   942: astore_3
    //   943: new 677	java/lang/StringBuilder
    //   946: dup
    //   947: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   950: astore 6
    //   952: aload_1
    //   953: astore_3
    //   954: aload 6
    //   956: ldc -128
    //   958: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   961: pop
    //   962: aload_1
    //   963: astore_3
    //   964: aload 6
    //   966: aload 4
    //   968: invokevirtual 957	java/lang/OutOfMemoryError:getMessage	()Ljava/lang/String;
    //   971: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   974: pop
    //   975: aload_1
    //   976: astore_3
    //   977: aload 5
    //   979: ldc_w 959
    //   982: aload 6
    //   984: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   987: invokeinterface 247 3 0
    //   992: pop
    //   993: aload_1
    //   994: astore_3
    //   995: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   998: ldc_w 961
    //   1001: aload 5
    //   1003: invokevirtual 952	com/tencent/smtt/webkit/service/SmttServiceProxy:upLoadToBeacon	(Ljava/lang/String;Ljava/util/Map;)V
    //   1006: aload_1
    //   1007: ifnull +7 -> 1014
    //   1010: aload_1
    //   1011: invokevirtual 922	java/net/HttpURLConnection:disconnect	()V
    //   1014: aload_0
    //   1015: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   1018: istore_2
    //   1019: iload_2
    //   1020: sipush 1017
    //   1023: if_icmpeq +145 -> 1168
    //   1026: iload_2
    //   1027: sipush 1018
    //   1030: if_icmpne +190 -> 1220
    //   1033: goto +135 -> 1168
    //   1036: astore 4
    //   1038: aload 6
    //   1040: astore_1
    //   1041: aload_1
    //   1042: astore_3
    //   1043: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   1046: ldc_w 954
    //   1049: invokevirtual 892	com/tencent/smtt/webkit/service/SmttServiceProxy:userBehaviorStatistics	(Ljava/lang/String;)V
    //   1052: aload_1
    //   1053: astore_3
    //   1054: aload_0
    //   1055: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   1058: sipush 1019
    //   1061: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   1064: pop
    //   1065: aload_1
    //   1066: astore_3
    //   1067: new 938	java/util/HashMap
    //   1070: dup
    //   1071: invokespecial 939	java/util/HashMap:<init>	()V
    //   1074: astore 5
    //   1076: aload_1
    //   1077: astore_3
    //   1078: new 677	java/lang/StringBuilder
    //   1081: dup
    //   1082: invokespecial 678	java/lang/StringBuilder:<init>	()V
    //   1085: astore 6
    //   1087: aload_1
    //   1088: astore_3
    //   1089: aload 6
    //   1091: ldc -128
    //   1093: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1096: pop
    //   1097: aload_1
    //   1098: astore_3
    //   1099: aload 6
    //   1101: aload 4
    //   1103: invokevirtual 962	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   1106: invokevirtual 682	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1109: pop
    //   1110: aload_1
    //   1111: astore_3
    //   1112: aload 5
    //   1114: ldc_w 959
    //   1117: aload 6
    //   1119: invokevirtual 687	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1122: invokeinterface 247 3 0
    //   1127: pop
    //   1128: aload_1
    //   1129: astore_3
    //   1130: invokestatic 474	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   1133: ldc_w 961
    //   1136: aload 5
    //   1138: invokevirtual 952	com/tencent/smtt/webkit/service/SmttServiceProxy:upLoadToBeacon	(Ljava/lang/String;Ljava/util/Map;)V
    //   1141: aload_1
    //   1142: ifnull +7 -> 1149
    //   1145: aload_1
    //   1146: invokevirtual 922	java/net/HttpURLConnection:disconnect	()V
    //   1149: aload_0
    //   1150: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   1153: istore_2
    //   1154: iload_2
    //   1155: sipush 1017
    //   1158: if_icmpeq +10 -> 1168
    //   1161: iload_2
    //   1162: sipush 1018
    //   1165: if_icmpne +55 -> 1220
    //   1168: aload_0
    //   1169: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   1172: sipush 1019
    //   1175: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   1178: pop
    //   1179: return
    //   1180: aload_1
    //   1181: ifnull +7 -> 1188
    //   1184: aload_1
    //   1185: invokevirtual 922	java/net/HttpURLConnection:disconnect	()V
    //   1188: aload_0
    //   1189: getfield 177	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:mLoadingStatus	I
    //   1192: istore_2
    //   1193: iload_2
    //   1194: sipush 1017
    //   1197: if_icmpeq +10 -> 1207
    //   1200: iload_2
    //   1201: sipush 1018
    //   1204: if_icmpne +14 -> 1218
    //   1207: aload_0
    //   1208: getfield 182	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:handler	Landroid/os/Handler;
    //   1211: sipush 1019
    //   1214: invokevirtual 756	android/os/Handler:sendEmptyMessage	(I)Z
    //   1217: pop
    //   1218: aload_3
    //   1219: athrow
    //   1220: return
    //   1221: astore_3
    //   1222: goto -788 -> 434
    //   1225: ldc_w 764
    //   1228: astore_1
    //   1229: goto -1101 -> 128
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1232	0	this	X5WebViewAdapter
    //   0	1232	1	paramBitmap	Bitmap
    //   443	762	2	i	int
    //   18	559	3	localObject1	Object
    //   580	2	3	localJSONException	JSONException
    //   587	257	3	localObject2	Object
    //   877	17	3	localObject3	Object
    //   897	322	3	localObject4	Object
    //   1221	1	3	localException1	Exception
    //   14	813	4	localStringBuilder1	StringBuilder
    //   881	1	4	localOutOfMemoryError1	OutOfMemoryError
    //   886	1	4	localException2	Exception
    //   891	5	4	localObject5	Object
    //   901	66	4	localOutOfMemoryError2	OutOfMemoryError
    //   1036	66	4	localException3	Exception
    //   8	462	5	localBufferedReader	java.io.BufferedReader
    //   499	3	5	localIOException	java.io.IOException
    //   510	627	5	localObject6	Object
    //   11	1107	6	localObject7	Object
    //   5	330	7	localObject8	Object
    //   61	357	8	localObject9	Object
    //   204	124	9	localStringBuilder2	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   469	476	499	java/io/IOException
    //   481	496	499	java/io/IOException
    //   520	537	580	org/json/JSONException
    //   545	577	580	org/json/JSONException
    //   354	426	877	finally
    //   426	431	877	finally
    //   434	439	877	finally
    //   439	469	877	finally
    //   469	476	877	finally
    //   481	496	877	finally
    //   501	506	877	finally
    //   506	512	877	finally
    //   520	537	877	finally
    //   545	577	877	finally
    //   581	585	877	finally
    //   599	633	877	finally
    //   633	644	877	finally
    //   659	688	877	finally
    //   703	720	877	finally
    //   723	847	877	finally
    //   354	426	881	java/lang/OutOfMemoryError
    //   426	431	881	java/lang/OutOfMemoryError
    //   434	439	881	java/lang/OutOfMemoryError
    //   439	469	881	java/lang/OutOfMemoryError
    //   469	476	881	java/lang/OutOfMemoryError
    //   481	496	881	java/lang/OutOfMemoryError
    //   501	506	881	java/lang/OutOfMemoryError
    //   506	512	881	java/lang/OutOfMemoryError
    //   520	537	881	java/lang/OutOfMemoryError
    //   545	577	881	java/lang/OutOfMemoryError
    //   581	585	881	java/lang/OutOfMemoryError
    //   599	633	881	java/lang/OutOfMemoryError
    //   633	644	881	java/lang/OutOfMemoryError
    //   659	688	881	java/lang/OutOfMemoryError
    //   703	720	881	java/lang/OutOfMemoryError
    //   723	847	881	java/lang/OutOfMemoryError
    //   354	426	886	java/lang/Exception
    //   434	439	886	java/lang/Exception
    //   439	469	886	java/lang/Exception
    //   469	476	886	java/lang/Exception
    //   481	496	886	java/lang/Exception
    //   501	506	886	java/lang/Exception
    //   506	512	886	java/lang/Exception
    //   520	537	886	java/lang/Exception
    //   545	577	886	java/lang/Exception
    //   581	585	886	java/lang/Exception
    //   599	633	886	java/lang/Exception
    //   633	644	886	java/lang/Exception
    //   659	688	886	java/lang/Exception
    //   703	720	886	java/lang/Exception
    //   723	847	886	java/lang/Exception
    //   19	29	891	finally
    //   32	37	891	finally
    //   40	51	891	finally
    //   54	63	891	finally
    //   66	78	891	finally
    //   81	95	891	finally
    //   98	106	891	finally
    //   117	125	891	finally
    //   131	149	891	finally
    //   152	177	891	finally
    //   180	194	891	finally
    //   197	206	891	finally
    //   209	218	891	finally
    //   221	230	891	finally
    //   233	242	891	finally
    //   245	252	891	finally
    //   255	264	891	finally
    //   267	276	891	finally
    //   279	288	891	finally
    //   291	299	891	finally
    //   302	311	891	finally
    //   314	324	891	finally
    //   327	334	891	finally
    //   337	354	891	finally
    //   908	917	891	finally
    //   919	930	891	finally
    //   932	941	891	finally
    //   943	952	891	finally
    //   954	962	891	finally
    //   964	975	891	finally
    //   977	993	891	finally
    //   995	1006	891	finally
    //   1043	1052	891	finally
    //   1054	1065	891	finally
    //   1067	1076	891	finally
    //   1078	1087	891	finally
    //   1089	1097	891	finally
    //   1099	1110	891	finally
    //   1112	1128	891	finally
    //   1130	1141	891	finally
    //   19	29	901	java/lang/OutOfMemoryError
    //   32	37	901	java/lang/OutOfMemoryError
    //   40	51	901	java/lang/OutOfMemoryError
    //   54	63	901	java/lang/OutOfMemoryError
    //   66	78	901	java/lang/OutOfMemoryError
    //   81	95	901	java/lang/OutOfMemoryError
    //   98	106	901	java/lang/OutOfMemoryError
    //   117	125	901	java/lang/OutOfMemoryError
    //   131	149	901	java/lang/OutOfMemoryError
    //   152	177	901	java/lang/OutOfMemoryError
    //   180	194	901	java/lang/OutOfMemoryError
    //   197	206	901	java/lang/OutOfMemoryError
    //   209	218	901	java/lang/OutOfMemoryError
    //   221	230	901	java/lang/OutOfMemoryError
    //   233	242	901	java/lang/OutOfMemoryError
    //   245	252	901	java/lang/OutOfMemoryError
    //   255	264	901	java/lang/OutOfMemoryError
    //   267	276	901	java/lang/OutOfMemoryError
    //   279	288	901	java/lang/OutOfMemoryError
    //   291	299	901	java/lang/OutOfMemoryError
    //   302	311	901	java/lang/OutOfMemoryError
    //   314	324	901	java/lang/OutOfMemoryError
    //   327	334	901	java/lang/OutOfMemoryError
    //   337	354	901	java/lang/OutOfMemoryError
    //   19	29	1036	java/lang/Exception
    //   32	37	1036	java/lang/Exception
    //   40	51	1036	java/lang/Exception
    //   54	63	1036	java/lang/Exception
    //   66	78	1036	java/lang/Exception
    //   81	95	1036	java/lang/Exception
    //   98	106	1036	java/lang/Exception
    //   117	125	1036	java/lang/Exception
    //   131	149	1036	java/lang/Exception
    //   152	177	1036	java/lang/Exception
    //   180	194	1036	java/lang/Exception
    //   197	206	1036	java/lang/Exception
    //   209	218	1036	java/lang/Exception
    //   221	230	1036	java/lang/Exception
    //   233	242	1036	java/lang/Exception
    //   245	252	1036	java/lang/Exception
    //   255	264	1036	java/lang/Exception
    //   267	276	1036	java/lang/Exception
    //   279	288	1036	java/lang/Exception
    //   291	299	1036	java/lang/Exception
    //   302	311	1036	java/lang/Exception
    //   314	324	1036	java/lang/Exception
    //   327	334	1036	java/lang/Exception
    //   337	354	1036	java/lang/Exception
    //   426	431	1221	java/lang/Exception
  }
  
  public boolean imageQueryFromX5Core()
  {
    IX5WebViewBase.HitTestResult localHitTestResult = getHitTestResult();
    if (localHitTestResult == null) {
      return false;
    }
    return (localHitTestResult.getType() != 5) && (localHitTestResult.getType() != 8) && (e.a().g()) && (SmttServiceProxy.getInstance().LongPressMenuImageQueryEnabled(this.mContext, this)) && (lastHitTestBmp() != null);
  }
  
  public boolean inFullScreenMode()
  {
    return false;
  }
  
  public void initX5SelectionIfNeeded()
  {
    if (this.mSelectionListener == null)
    {
      this.mSelectionListener = new X5Selection(this.mContext, this, getSettingsExtension().getDayOrNight() ^ true, this);
      this.mSelectionListener.setOwnerWebView(this);
      setSelectListener(this.mSelectionListener);
    }
  }
  
  public boolean inputNodeIsPasswordType()
  {
    Object localObject = getHitTestResult();
    if (localObject != null)
    {
      if (((IX5WebViewBase.HitTestResult)localObject).getType() != 9) {
        return false;
      }
      localObject = ((IX5WebViewBase.HitTestResult)localObject).getData();
      if ((localObject instanceof IX5WebViewBase.HitTestResult.EditableData)) {
        return ((IX5WebViewBase.HitTestResult.EditableData)localObject).mIsPassword;
      }
      return false;
    }
    return false;
  }
  
  public boolean inputNodeIsPhoneType()
  {
    return (getHitTestResult() != null) && (getHitTestResult().getType() == 2);
  }
  
  public Object invokeMiscMethod(String paramString, Bundle paramBundle)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    if (paramBundle == null)
    {
      if ("supportTranslateWebSite".equals(paramString))
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("CH0050");
        paramString = new Bundle();
        paramString.putBoolean("result", SmttServiceProxy.getInstance().isWXWholePageTranslateEnabled(this.mContext));
        return paramString;
      }
      return null;
    }
    boolean bool;
    int i;
    if ("setVideoParams".equals(paramString))
    {
      if (paramBundle.containsKey("supportLiteWnd"))
      {
        bool = paramBundle.getBoolean("supportLiteWnd");
        getWebViewProvider().getExtension().getSettingsExtension().setSupportLiteWnd(Boolean.valueOf(bool).booleanValue());
      }
      if (paramBundle.containsKey("DefaultVideoScreen"))
      {
        i = paramBundle.getInt("DefaultVideoScreen", 1);
        if ((i >= 1) && (i <= WebSettingsExtension.a.values().length)) {
          getWebViewProvider().getExtension().getSettingsExtension().setVideoScreenMode(WebSettingsExtension.a.values()[(i - 1)]);
        }
      }
      if (paramBundle.containsKey("forceSameLayer"))
      {
        bool = paramBundle.getBoolean("forceSameLayer");
        getWebViewProvider().getExtension().getSettingsExtension().setForceVideoSameLayer(Boolean.valueOf(bool).booleanValue());
        return null;
      }
    }
    else
    {
      if ("setWebViewCallbackClientFlag".equals(paramString))
      {
        this.isOunterClientForUIEventSetted = paramBundle.getBoolean("flag");
        return null;
      }
      if ("uploadX5CoreLiveLog".equals(paramString))
      {
        paramString = paramBundle.getString("date");
        paramBundle = paramBundle.getString("id");
        getWebViewProvider().getExtension().uploadX5CoreLiveLog(paramString, paramBundle);
        return null;
      }
      if ("HitQBAppDownloadCounts".equals(paramString))
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("ZZNS2");
        return null;
      }
      if ("setUIIdentity".equals(paramString))
      {
        i = paramBundle.getInt("UIIdentity", 1);
        getWebViewProvider().getExtension().getSettingsExtension().setUIIdentity(i);
        return null;
      }
      if ("showInputMethodExtBar".equals(paramString))
      {
        bool = paramBundle.getBoolean("show");
        i = paramBundle.getInt("height");
        getWebViewProvider().getExtension().showInputMethodExtBar(bool, i);
        return null;
      }
      if ("isHandleViewDragging".equals(paramString)) {
        return Boolean.valueOf(getWebViewProvider().getExtension().isHandleViewDragging());
      }
      if ("setScreenScrollableForHandleView".equals(paramString))
      {
        bool = paramBundle.getBoolean("enable");
        getWebViewProvider().getExtension().setScreenScrollableForHandleView(bool);
        return null;
      }
      if ("setFloatVideoEnabled".equals(paramString)) {
        try
        {
          bool = paramBundle.getBoolean("enable");
          getWebViewProvider().getExtension().getSettingsExtension().setFloatVideoEnabled(bool);
          return null;
        }
        catch (NoSuchMethodError paramString)
        {
          paramString.printStackTrace();
          return null;
        }
      }
      if ("setDataFilterForRequestInfo".equals(paramString))
      {
        i = paramBundle.getInt("filterType");
        getWebViewProvider().getExtension().getSettingsExtension().setDataFilterForRequestInfo(i);
        return null;
      }
      if ("preConnect".equals(paramString))
      {
        paramString = paramBundle.getString("url");
        i = paramBundle.getInt("num");
        getWebViewProvider().getExtension().preConnect(paramString, i);
        return null;
      }
      if ("evaluateJavaScriptInSubFrame".equals(paramString))
      {
        if (getSettingsExtension().evaluateJavaScriptInSubFrameEnabled())
        {
          super.evaluateJavaScriptInSubFrame(paramBundle.getString("script"));
          return null;
        }
      }
      else
      {
        if ("setIsKidMode".equals(paramString))
        {
          bool = paramBundle.getBoolean("enable");
          getWebViewProvider().getExtension().getSettingsExtension().setIsKidMode(bool);
          return null;
        }
        if ("setAllowSwitchToMiniQB".equals(paramString))
        {
          bool = paramBundle.getBoolean("enable");
          getWebViewProvider().getExtension().getSettingsExtension().setAllowSwitchToMiniQB(bool);
          return null;
        }
        if ("setVideoPlaybackRequiresUserGesture".equals(paramString))
        {
          bool = paramBundle.getBoolean("require");
          getWebViewProvider().getExtension().getSettingsExtension().setVideoPlaybackRequiresUserGesture(bool);
          return null;
        }
        if ("getCurrScrollVelocity".equals(paramString)) {
          return Float.valueOf(getWebViewProvider().getExtension().getCurrScrollVelocity());
        }
        if ("setHttpSystemProxy".equals(paramString))
        {
          paramString = paramBundle.getString("address");
          TencentProxyChangeListener.getInstance().setHttpSystemProxy(paramString);
          return null;
        }
        if ("setIsViewSourceMode".equals(paramString))
        {
          bool = paramBundle.getBoolean("mode");
          getWebViewProvider().getExtension().getSettingsExtension().setIsViewSourceMode(bool);
          return null;
        }
        if ("setJSPerformanceRecordEnable".equals(paramString))
        {
          bool = paramBundle.getBoolean("enable");
          getWebViewProvider().getExtension().getSettingsExtension().setJSPerformanceRecordEnable(bool);
          return null;
        }
        if ("setFramePerformanceRecordEnable".equals(paramString))
        {
          bool = paramBundle.getBoolean("enable");
          getWebViewProvider().getExtension().getSettingsExtension().setFramePerformanceRecordEnable(bool);
          return null;
        }
        if ("unuseFirstScreenCSS".equals(paramString)) {
          return "[]";
        }
        if ("customDiskCachePathEnabled".equals(paramString))
        {
          bool = paramBundle.getBoolean("enabled");
          paramString = paramBundle.getString("path");
          if (SmttServiceProxy.getInstance().getCustomDiskCacheEnabled())
          {
            getWebViewProvider().getExtension().getSettingsExtension().customDiskCachePathEnabled(bool, paramString);
            return null;
          }
        }
        else
        {
          if ("setDiskCacheSize".equals(paramString))
          {
            i = paramBundle.getInt("maxSize");
            getWebViewProvider().getExtension().getSettingsExtension().setDiskCahceSize(i);
            return null;
          }
          if ("setUseDialogSubMenuAuto".equals(paramString))
          {
            bool = paramBundle.getBoolean("enabled");
            getWebViewProvider().getExtension().getSettingsExtension().setUseDialogSubMenuAuto(bool);
            return null;
          }
          if ("setShouldRequestFavicon".equals(paramString))
          {
            bool = paramBundle.getBoolean("enabled");
            getWebViewProvider().getExtension().getSettingsExtension().setShouldRequestFavicon(bool);
            return null;
          }
          if ("setDomainsAndArgumentForImageRequest".equals(paramString))
          {
            paramString = paramBundle.getString("argument");
            paramBundle = paramBundle.getStringArrayList("domains");
            getWebViewProvider().getExtension().getSettingsExtension().setDomainsAndArgumentForImageRequest(paramBundle, paramString);
            return null;
          }
          if (!"useSurfaceView".equals(paramString))
          {
            if ("enableX5HighPerformanceRendering".equals(paramString)) {
              return null;
            }
            if ("setReferer".equals(paramString))
            {
              paramString = paramBundle.getString("url");
              getWebViewProvider().getExtension().getSettingsExtension().setXCXReferer(paramString);
              return null;
            }
            if ("setZiXunWebViewType".equals(paramString))
            {
              i = paramBundle.getInt("type");
              getWebViewProvider().getExtension().getSettingsExtension().setZxWebViewType(i);
              return null;
            }
            if ("setMiniProgramFlag".equals(paramString))
            {
              this.mMiniProgramFlag = paramBundle.getInt("flag");
              return null;
            }
            if ("getMiniProgramFlag".equals(paramString)) {
              return Integer.valueOf(this.mMiniProgramFlag);
            }
            if ("setForceLongClickQuickCopy".equals(paramString))
            {
              this.mForceLongClickQuickCopy = paramBundle.getBoolean("require");
              return null;
            }
            Object localObject;
            if ("showBrowserListDialog".equals(paramString))
            {
              localObject = paramBundle.getString("url");
              i = paramBundle.getInt("style", 0);
              if (!TextUtils.isEmpty((CharSequence)localObject))
              {
                paramString = new Intent();
                paramString.setAction("android.intent.action.VIEW");
                paramString.setData(Uri.parse((String)localObject));
                paramBundle = SmttResource.getBrowserListIcons(this.mContext, paramString, this);
                localObject = new Bundle();
                if (i == 1) {
                  ((Bundle)localObject).putString("PARAM_KEY_FORCE_DIALOG_STYLE", "hw");
                }
                f.a("", this.mContext, paramString, null, "", "", "", "", "", "", paramBundle, (Bundle)localObject);
                return new Object();
              }
            }
            else
            {
              if ("supportImageQuery".equals(paramString))
              {
                paramString = getHitTestResult();
                if (SmttServiceProxy.getInstance().LongPressMenuImageQueryEnabled(this.mContext, this))
                {
                  if ((paramString.getType() == 5) || (paramString.getType() == 8)) {
                    SmttServiceProxy.getInstance().userBehaviorStatistics("TBSLCIQ001");
                  }
                  return Boolean.valueOf(true);
                }
                SmttServiceProxy.getInstance().userBehaviorStatistics("TBSLCIQ002");
                return Boolean.valueOf(false);
              }
              if ("imageQuery".equals(paramString))
              {
                SmttServiceProxy.getInstance().userBehaviorStatistics("TBSLCIQ003");
                imageQuery(paramBundle);
                return Boolean.valueOf(true);
              }
              if ("detectTranslateWebSiteIsNeeded".equals(paramString))
              {
                SmttServiceProxy.getInstance().userBehaviorStatistics("CH0051");
                paramString = paramBundle.getString("destLanguage");
                bool = paramBundle.getBoolean("isFastOpen", true);
                i = getWebViewProvider().getExtension().detectTranslateWebSiteIsNeeded(paramString, bool);
                paramString = new Bundle();
                paramString.putInt("errorCode", i);
                return paramString;
              }
              if ("translateWebSite".equals(paramString))
              {
                SmttServiceProxy.getInstance().userBehaviorStatistics("CH0052");
                paramString = paramBundle.getString("destLanguage");
                localObject = paramBundle.getString("url");
                String str1 = paramBundle.getString("translateTips");
                String str2 = paramBundle.getString("translateFinish");
                paramBundle = paramBundle.getString("errorWording");
                getWebViewProvider().getExtension().setTranslateInfo((String)localObject, paramString, str1, str2, paramBundle);
                return null;
              }
              if ("fastGetCurrentEntryIndex".equals(paramString))
              {
                paramString = new Bundle();
                paramString.putInt("index", getWebViewProvider().getExtension().getCurrentEntryIndex());
                return paramString;
              }
              if ("canUseNewJsDialog".equals(paramString)) {
                return Boolean.valueOf(e.a().z());
              }
              if ("setRedirectCountLimit".equals(paramString))
              {
                i = paramBundle.getInt("limit");
                getSettingsExtension().setRedirectCountLimit(i);
                return Boolean.valueOf(true);
              }
              if (("injectJavascript".equals(paramString)) || ("InjectJavascript".equals(paramString)))
              {
                paramString = paramBundle.getString("script");
                getWebViewProvider().getExtension().injectJavascript(paramString);
                return Boolean.valueOf(true);
              }
            }
          }
        }
      }
    }
    return null;
  }
  
  public boolean isEditingMode()
  {
    return false;
  }
  
  public boolean isEnableSetFont()
  {
    return false;
  }
  
  public boolean isForceLongClickQuickCopy()
  {
    return this.mForceLongClickQuickCopy;
  }
  
  @UsedByReflection("TbsServiceImpl.java")
  public boolean isMiniQB()
  {
    return this.mIsMiniQB;
  }
  
  public boolean isOverScrollEnable()
  {
    IX5WebHistoryItem localIX5WebHistoryItem = getHistoryItem(0);
    if (localIX5WebHistoryItem == null) {
      return false;
    }
    return getWebViewProvider().getExtension().getOverScrollState(localIX5WebHistoryItem.getId());
  }
  
  public boolean isPrivateBrowsingEnable()
  {
    return false;
  }
  
  public boolean isSelectionMode()
  {
    return (selectionType() != 1) && (selectionType() != 0);
  }
  
  public boolean isX5CoreSandboxMode()
  {
    return false;
  }
  
  public Bitmap lastHitTestBmp()
  {
    return super.lastHitTestBmp();
  }
  
  public void leaveSelectionMode()
  {
    super.leaveSelectionMode();
  }
  
  public void loadDataWithBaseURLWithHeaders(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Map<String, String> paramMap)
  {
    getWebViewProvider().loadDataWithBaseURL(paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  public void loadUrl(String paramString)
  {
    Object localObject = paramString;
    if (m.a().a())
    {
      localObject = paramString;
      if (!this.mHasForceDebugtbs)
      {
        String str = m.a().a(getContext(), paramString);
        localObject = paramString;
        if (!TextUtils.isEmpty(str))
        {
          this.mHasForceDebugtbs = true;
          localObject = str;
        }
      }
    }
    super.loadUrl((String)localObject);
  }
  
  public void loadUrl(String paramString, Map<String, String> paramMap, boolean paramBoolean)
  {
    Object localObject = paramMap;
    if (paramBoolean)
    {
      localObject = paramMap;
      if (paramMap == null) {
        localObject = new HashMap();
      }
      ((Map)localObject).put("should_replace_current_entry", "");
    }
    loadUrl(paramString, (Map)localObject);
  }
  
  public void loaddataWithHeaders(String paramString1, String paramString2, String paramString3, Map<String, String> paramMap)
  {
    getWebViewProvider().loadData(paramString1, paramString2, paramString3);
  }
  
  public boolean needSniff()
  {
    return getWebViewProvider().getExtension().needSniff();
  }
  
  @UsedByReflection("MiniQB")
  public void notifyMiniQbStatus(String paramString1, String paramString2)
  {
    getWebViewProvider().getExtension().notifyMiniQbStatus(paramString1, paramString2);
  }
  
  protected final void onDrawScrollBarsX5(Canvas paramCanvas)
  {
    com.tencent.smtt.d.b localb = this.mScrollCache;
    if (localb != null)
    {
      Object localObject = localb.a();
      b.a locala1 = b.a.c;
      int k = 0;
      int j;
      if (localObject == locala1)
      {
        if (localb.jdField_a_of_type_ArrayOfFloat == null) {
          localb.jdField_a_of_type_ArrayOfFloat = new float[1];
        }
        localObject = localb.jdField_a_of_type_ArrayOfFloat;
        try
        {
          if (localb.jdField_a_of_type_AndroidGraphicsInterpolator.timeToValues((float[])localObject) == Interpolator.Result.FREEZE_END) {
            hideScrollBar(localb);
          } else {
            localb.jdField_a_of_type_ComTencentSmttDA.setAlpha(Math.round(localObject[0]));
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          hideScrollBar(localb);
        }
        j = 1;
      }
      else
      {
        if (localException == b.a.b) {
          localb.jdField_a_of_type_ComTencentSmttDA.setAlpha(255);
        } else {
          localb.jdField_a_of_type_ComTencentSmttDA.setAlpha(0);
        }
        j = 0;
      }
      boolean bool1 = getRealWebView().isHorizontalScrollBarEnabled();
      boolean bool2 = getRealWebView().isVerticalScrollBarEnabled();
      if (((bool2) || (bool1)) && (localb.jdField_a_of_type_ComTencentSmttDA.getAlpha() > 0))
      {
        int i3 = getRealWebView().getRight() - getRealWebView().getLeft();
        int i2 = getRealWebView().getBottom() - getRealWebView().getTop();
        com.tencent.smtt.d.a locala = localb.jdField_a_of_type_ComTencentSmttDA;
        int n = getRealWebView().getScrollX();
        int m = getRealWebView().getScrollY();
        int i;
        if (bool1)
        {
          int i1 = locala.b(false);
          i = i1;
          if (i1 <= 0) {
            i = localb.d;
          }
          locala.a(getRealWebView().computeHorizontalScrollRange(), getRealWebView().computeHorizontalScrollOffset(), getRealWebView().computeHorizontalScrollExtent(), false);
          if (bool2) {
            k = getRealWebView().getVerticalScrollbarWidth();
          }
          i1 = m + i2 - i;
          k = n + i3 - k;
          i += i1;
          getRealWebView().onDrawHorizontalScrollBar(paramCanvas, locala, n, i1, k, i);
          if (j != 0) {
            getRealWebView().invalidate(n, i1, k, i);
          }
        }
        k = m;
        if (bool2)
        {
          i = locala.a(true);
          m = i;
          if (i <= 0) {
            m = localb.d;
          }
          locala.a(getRealWebView().computeVerticalScrollRange(), getRealWebView().computeVerticalScrollOffset(), getRealWebView().computeVerticalScrollExtent(), true);
          if (Build.VERSION.SDK_INT >= 11) {
            i = getRealWebView().getVerticalScrollbarPosition();
          } else {
            i = 2;
          }
          if (i == 0) {
            i = 2;
          }
          if (i != 1) {
            n = n + i3 - m;
          }
          m += n;
          if (k < 0) {
            i = k + locala.b(true);
          } else {
            i = k + i2;
          }
          getRealWebView().onDrawVerticalScrollBar(paramCanvas, locala, n, k, m, i);
          if (j != 0) {
            getRealWebView().invalidate(n, k, m, i);
          }
        }
      }
    }
  }
  
  public void onFlingScrollBegin(int paramInt1, int paramInt2, int paramInt3)
  {
    super.onFlingScrollBegin(paramInt1, paramInt2, paramInt3);
    Object localObject = this.mScrollCache;
    if ((localObject != null) && (((com.tencent.smtt.d.b)localObject).jdField_a_of_type_ComTencentSmttDA != null))
    {
      this.mScrollCache.getClass();
      if ((paramInt1 >= 700) && (Math.abs(paramInt3) >= this.mScrollCache.f))
      {
        this.mScrollCache.jdField_a_of_type_ComTencentSmttDA.b(true);
        if (ContextHolder.getInstance().isTbsDevelopMode())
        {
          if (!mUseTbsTrackDrawable) {
            return;
          }
          Drawable localDrawable;
          if (!getSettingsExtension().getDayOrNight())
          {
            localDrawable = mSdkVerticalTrackNightDrawable;
            localObject = localDrawable;
            if (localDrawable == null) {
              localObject = SmttResource.getDrawable("x5_fast_scroller_night");
            }
            mSdkVerticalTrackNightDrawable = (Drawable)localObject;
            if (mSdkVerticalTrackNightDrawable != null) {
              this.mScrollCache.jdField_a_of_type_ComTencentSmttDA.b(mSdkVerticalTrackNightDrawable);
            }
          }
          else
          {
            localDrawable = mSdkVerticalTrackDrawable;
            localObject = localDrawable;
            if (localDrawable == null) {
              localObject = SmttResource.getDrawable("x5_fast_scroller");
            }
            mSdkVerticalTrackDrawable = (Drawable)localObject;
            if (mSdkVerticalTrackDrawable != null) {
              this.mScrollCache.jdField_a_of_type_ComTencentSmttDA.b(mSdkVerticalTrackDrawable);
            }
          }
        }
        else {}
      }
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = getWebViewClientExtension();
    if ((getRealWebView().getVisibleTitleHeight() > 0) && ((int)paramMotionEvent.getY() < getRealWebView().getVisibleTitleHeight()) && (!this.mScrollCache.jdField_b_of_type_Boolean) && (!isSelectionMode())) {
      return false;
    }
    if ((this.isOunterClientForUIEventSetted) && (localIX5WebViewClientExtension != null)) {
      return localIX5WebViewClientExtension.onInterceptTouchEvent(paramMotionEvent, getView());
    }
    return super.super_onInterceptTouchEvent(paramMotionEvent);
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    super.super_onMeasure(paramInt1, paramInt2);
  }
  
  public void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = getWebViewClientExtension();
    if ((this.isOunterClientForUIEventSetted) && (localIX5WebViewClientExtension != null))
    {
      localIX5WebViewClientExtension.onOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2, getView());
      return;
    }
    super.super_onOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
  }
  
  public void onPauseNativeVideo() {}
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = getWebViewClientExtension();
    if ((this.isOunterClientForUIEventSetted) && (localIX5WebViewClientExtension != null))
    {
      localIX5WebViewClientExtension.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4, getView());
      return;
    }
    super.super_onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void onShowImageBrowser(boolean paramBoolean)
  {
    if ((paramBoolean) && (!this.mImageBrowserInShowing))
    {
      if (SmttServiceProxy.getInstance().isInMiniProgram(this)) {
        return;
      }
      if (this.mIsMiniQB) {
        return;
      }
      Object localObject2 = getHitTestResult();
      if (localObject2 == null) {
        return;
      }
      final Object localObject3 = getUrl();
      final Object localObject1 = getUrl((IX5WebViewBase.HitTestResult)localObject2);
      this.mClickImageCount += 1;
      final int j;
      int i;
      int k;
      if ((!e.a().t()) && (!e.a().n()))
      {
        if ((e.a().g()) && (!getWebViewProvider().getExtension().getSettingsExtension().getImageScanEnable())) {
          j = 0;
        } else {
          j = 1;
        }
        Object localObject4 = SmttServiceProxy.getInstance().getMiniQbVersionName();
        i = j;
        int m;
        if (j != 0) {
          if (localObject4 != null)
          {
            localObject4 = ((String)localObject4).split("\\.");
            i = j;
            if (localObject4.length >= 2)
            {
              k = Integer.parseInt(localObject4[0]);
              m = Integer.parseInt(localObject4[1]);
              int n = Integer.parseInt(localObject4[2]);
              if ((k >= 2) && ((k != 2) || (m >= 4)))
              {
                i = j;
                if (k == 2)
                {
                  i = j;
                  if (m == 4)
                  {
                    i = j;
                    if (n >= 2003) {}
                  }
                }
              }
              else
              {
                i = 0;
              }
            }
          }
          else
          {
            i = 0;
          }
        }
        j = i;
        if (i != 0)
        {
          j = i;
          if (((IX5WebViewBase.HitTestResult)localObject2).getType() == 5) {
            try
            {
              localObject4 = ((IX5WebViewBase.HitTestResult)localObject2).getData();
              j = i;
              if ((localObject4 instanceof IX5WebViewBase.HitTestResult.ImageData))
              {
                k = ((IX5WebViewBase.HitTestResult.ImageData)localObject4).mImgWidth;
                j = ((IX5WebViewBase.HitTestResult.ImageData)localObject4).mImgHeight;
                m = getContentWidth();
                j = i;
                if (k * 2 < m) {
                  j = 0;
                }
              }
            }
            catch (NoSuchFieldError localNoSuchFieldError2)
            {
              localNoSuchFieldError2.printStackTrace();
              j = i;
            }
          }
        }
        i = j;
        if (j != 0)
        {
          i = j;
          if (SmttServiceProxy.getInstance().isImageBrowserDisableInPage((String)localObject3)) {
            i = 0;
          }
        }
        if ((i != 0) && (!this.mImageBrowserInShowing))
        {
          if (getContentWidth() > 0) {
            i = getContentWidth() * 4 / 10;
          } else {
            i = 200;
          }
          this.mImageBrowserInShowing = true;
          j = ((IX5WebViewBase.HitTestResult)localObject2).getType();
          getWebViewProvider().getExtension().updateImageList2(i, 20, true, new ValueCallback()
          {
            public void onReceiveValue(Integer paramAnonymousInteger)
            {
              paramAnonymousInteger = new LinkedHashMap();
              HashMap localHashMap = new HashMap();
              ArrayList localArrayList = X5WebViewAdapter.this.getAllImageInfo();
              int m = 0;
              for (int i = -1; m < localArrayList.size(); i = j)
              {
                for (;;)
                {
                  int j;
                  try
                  {
                    localObject = X5WebViewAdapter.this.getBitmapByIndex(m);
                    j = i;
                    if (((IX5WebViewBase.ImageInfo)localArrayList.get(m)).getPicUrl() != null)
                    {
                      j = i;
                      if (!((IX5WebViewBase.ImageInfo)localArrayList.get(m)).getPicUrl().isEmpty()) {
                        if (((IX5WebViewBase.ImageInfo)localArrayList.get(m)).getPicUrl().startsWith("data:image"))
                        {
                          j = i;
                        }
                        else
                        {
                          paramAnonymousInteger.put(((IX5WebViewBase.ImageInfo)localArrayList.get(m)).getPicUrl(), localObject);
                          k = i;
                        }
                      }
                    }
                  }
                  catch (OutOfMemoryError localOutOfMemoryError)
                  {
                    Object localObject;
                    int k;
                    localOutOfMemoryError.printStackTrace();
                    j = i;
                  }
                  try
                  {
                    localObject = ((IX5WebViewBase.ImageInfo)localArrayList.get(m)).getPicUrl();
                    j = i;
                    if (i == -1)
                    {
                      j = i;
                      if (localObject != null)
                      {
                        j = i;
                        k = i;
                        if (((String)localObject).equals(localObject1))
                        {
                          k = i;
                          j = localHashMap.size();
                        }
                      }
                    }
                    k = j;
                    localHashMap.put(localObject, Boolean.valueOf(((IX5WebViewBase.ImageInfo)localArrayList.get(m)).mIsGif));
                  }
                  catch (NoSuchFieldError localNoSuchFieldError) {}
                }
                localHashMap.put(((IX5WebViewBase.ImageInfo)localArrayList.get(m)).getPicUrl(), Boolean.valueOf(false));
                j = k;
                m += 1;
              }
              if (paramAnonymousInteger.size() > 0)
              {
                j = i;
                if (i == -1)
                {
                  if (5 == j)
                  {
                    X5WebViewAdapter.access$802(X5WebViewAdapter.this, false);
                    return;
                  }
                  j = 0;
                }
                SmttServiceProxy.getInstance().startMiniQBWithImages(X5WebViewAdapter.this.getContext(), paramAnonymousInteger, localHashMap, j, localObject3);
              }
              X5WebViewAdapter.access$802(X5WebViewAdapter.this, false);
            }
          });
        }
      }
      else
      {
        try
        {
          localObject3 = this.mProvider.getHitTestResultImgSize();
          int[] arrayOfInt = this.mProvider.getHitTestResultPosition();
          float f1 = this.mContext.getResources().getDisplayMetrics().density;
          f1 = getRealWebView().getScale();
          float f2 = localObject3[0];
          float f3 = localObject3[1];
          float f4 = arrayOfInt[0];
          float f5 = arrayOfInt[1];
          localObject3 = TencentCacheManager.getCacheResult((String)localObject1).getLocalPath();
          if (!((Boolean)getWebViewClientExtension().onMiscCallBack("supportImagePreview", new Bundle())).booleanValue()) {
            return;
          }
          i = ((IX5WebViewBase.HitTestResult)localObject2).getType();
          if (i == 5) {
            try
            {
              localObject2 = ((IX5WebViewBase.HitTestResult)localObject2).getData();
              if ((localObject2 instanceof IX5WebViewBase.HitTestResult.ImageData))
              {
                i = ((IX5WebViewBase.HitTestResult.ImageData)localObject2).mImgWidth;
                j = ((IX5WebViewBase.HitTestResult.ImageData)localObject2).mImgHeight;
                k = getContentWidth();
                if ((i * 2 < k) || (j < 100)) {
                  return;
                }
              }
              localObject2 = new Bundle();
              ((Bundle)localObject2).putString("url", (String)localObject1);
              ((Bundle)localObject2).putString("filePath", (String)localObject3);
              ((Bundle)localObject2).putFloat("left", f4 * f1);
              ((Bundle)localObject2).putFloat("top", f5 * f1);
              ((Bundle)localObject2).putFloat("width", f2 * f1);
              ((Bundle)localObject2).putFloat("height", f3 * f1);
              getWebViewClientExtension().onMiscCallBack("imagePreview", (Bundle)localObject2);
              localObject1 = new HashMap();
              ((HashMap)localObject1).put("TBSHost", "weixin");
              SmttServiceProxy.getInstance().upLoadToBeacon("WXIMGPRE", (Map)localObject1);
              return;
            }
            catch (NoSuchFieldError localNoSuchFieldError1)
            {
              localNoSuchFieldError1.printStackTrace();
              return;
            }
          }
          return;
        }
        catch (Exception localException)
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("wechat onShowImageBrowser throws exception:");
          ((StringBuilder)localObject2).append(localException.getCause());
          ((StringBuilder)localObject2).append(",");
          ((StringBuilder)localObject2).append(localException.getMessage());
          Log.e("ImageBrowser", ((StringBuilder)localObject2).toString());
        }
      }
    }
  }
  
  public void onShowLongClickPopupMenu(boolean paramBoolean)
  {
    for (;;)
    {
      boolean bool;
      try
      {
        int i;
        if (this.mForceLongClickQuickCopy)
        {
          i = getHitTestResult().getType();
          if ((i == 0) || (i == 4) || (i == 3))
          {
            setQuickSelectCopy(true);
            enterSelectionMode(false);
          }
        }
        else
        {
          Object localObject1;
          if ((!e.a().t()) && (!e.a().g()))
          {
            localObject1 = SmttServiceProxy.getInstance().getMiniQbVersionName();
            if (localObject1 != null)
            {
              localObject1 = ((String)localObject1).split("\\.");
              if (localObject1.length >= 2)
              {
                i = Integer.parseInt(localObject1[0]);
                int j = Integer.parseInt(localObject1[1]);
                if ((i <= 1) && (j <= 2) && (isAppHomeCateGory())) {
                  return;
                }
              }
            }
          }
          if (!ContextHolder.getInstance().isTbsDevelopMode()) {
            return;
          }
          if (((SmttServiceProxy.getInstance().AppLongPressMenuEnabled(this.mContext, this)) || ("com.luojilab.player".equalsIgnoreCase(this.mContext.getApplicationContext().getPackageName()))) && ((this.mContext instanceof Activity)))
          {
            if (this.mX5ActionMode == null) {
              this.mX5ActionMode = new X5ActionMode();
            }
            this.mX5ActionMode.setContext(this.mContext);
            ((Activity)this.mContext).onActionModeStarted(this.mX5ActionMode);
          }
          initX5SelectionIfNeeded();
          localObject2 = getHitTestResult();
          if (((IX5WebViewBase.HitTestResult)localObject2).getType() != 5)
          {
            if (((IX5WebViewBase.HitTestResult)localObject2).getType() == 8) {
              return;
            }
            if (!SmttServiceProxy.getInstance().isLongPressMenuEnabled(this.mContext, this)) {
              return;
            }
            if (((IX5WebViewBase.HitTestResult)localObject2).isFromSinglePress())
            {
              localObject1 = getSinglePressPoint();
              break label548;
            }
            localObject1 = ((IX5WebViewBase.HitTestResult)localObject2).getHitTestPoint();
            break label548;
            if ((((IX5WebViewBase.HitTestResult)localObject2).getType() != 9) && (!imageQueryFromX5Core()) && (SmttServiceProxy.getInstance().isLongPressQuickSecondMenu_QQ_ThirdApp(this.mContext, this)))
            {
              setQuickSelectCopy(true);
              SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA016");
              enterSelectionMode(false);
              return;
            }
            localObject3 = SmttResource.getThemeContextWrapper(this.mContext, "x5_MttFuncWindowTheme");
            Context localContext = this.mContext;
            if (getSettingsExtension().getDayOrNight()) {
              break label554;
            }
            bool = true;
            localObject2 = new X5LongClickPopMenu(localContext, this, (IX5WebViewBase.HitTestResult)localObject2, (Context)localObject3, bool, paramBoolean);
            ((X5LongClickPopMenu)localObject2).setLongClickPoint(getLocationOnScreen(getView(), (Point)localObject1));
            ((X5LongClickPopMenu)localObject2).setCanceledOnTouchOutside(true);
            ((X5LongClickPopMenu)localObject2).setCancelable(true);
            ((X5LongClickPopMenu)localObject2).show();
            getWebViewProvider().getExtension().reportShowLongClickPopupMenu();
            ((X5LongClickPopMenu)localObject2).setOnDismissListener(new DialogInterface.OnDismissListener()
            {
              public void onDismiss(DialogInterface paramAnonymousDialogInterface)
              {
                X5WebViewAdapter.this.getWebViewProvider().getExtension().exitX5LongClickPopMenu();
              }
            });
            SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA001");
            return;
          }
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("onShowLongClickPopupMenu exception: ");
        ((StringBuilder)localObject2).append(localThrowable);
        Log.e("X5WebViewAdapter", ((StringBuilder)localObject2).toString());
        localObject2 = SmttServiceProxy.getInstance();
        Object localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("Resource not found: ");
        ((StringBuilder)localObject3).append(localThrowable);
        ((SmttServiceProxy)localObject2).reportTbsError(3004, ((StringBuilder)localObject3).toString());
        return;
      }
      return;
      label548:
      if (localThrowable == null)
      {
        return;
        label554:
        bool = false;
      }
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = getWebViewClientExtension();
    if ((this.isOunterClientForUIEventSetted) && (localIX5WebViewClientExtension != null)) {
      return localIX5WebViewClientExtension.onTouchEvent(paramMotionEvent, getView());
    }
    return onTouchEventImpl(paramMotionEvent);
  }
  
  public boolean onTouchEventImpl(MotionEvent paramMotionEvent)
  {
    Object localObject = this.mScrollCache;
    if ((localObject != null) && (((com.tencent.smtt.d.b)localObject).jdField_a_of_type_ComTencentSmttDA != null))
    {
      localObject = this.mScrollCache.jdField_a_of_type_ComTencentSmttDA;
      int i;
      int j;
      switch (paramMotionEvent.getAction() & 0xFF)
      {
      default: 
        break;
      case 2: 
        if (this.mScrollCache.jdField_c_of_type_Boolean)
        {
          i = getRealWebView().computeVerticalScrollRange() + getRealWebView().getVisibleTitleHeight() - getRealWebView().computeVerticalScrollExtent();
          j = (int)paramMotionEvent.getX();
          int k = (int)paramMotionEvent.getY();
          if (this.mScrollCache.jdField_b_of_type_Boolean)
          {
            j = (int)(i * (k - getRealWebView().getVisibleTitleHeight()) / (getRealWebView().getHeight() - getRealWebView().getVisibleTitleHeight()));
            getRealWebView().scrollTo(getRealWebView().getScrollX(), Math.max(0, Math.min(j, i)));
            getRealWebView().invalidate();
            return true;
          }
          if (this.mScrollCache.jdField_a_of_type_AndroidGraphicsPoint == null)
          {
            this.mScrollCache.jdField_a_of_type_JavaUtilArrayList.add(MotionEvent.obtain(paramMotionEvent));
            this.mScrollCache.jdField_a_of_type_AndroidGraphicsPoint = new Point(j, k);
            return true;
          }
          if (Math.abs(k - this.mScrollCache.jdField_a_of_type_AndroidGraphicsPoint.y) >= this.mScrollCache.h)
          {
            paramMotionEvent = this.mScrollCache;
            paramMotionEvent.jdField_b_of_type_Boolean = true;
            paramMotionEvent.a();
            paramMotionEvent = this.mScrollCache;
            paramMotionEvent.jdField_a_of_type_AndroidGraphicsPoint = null;
            paramMotionEvent.a(b.a.b);
            ((com.tencent.smtt.d.a)localObject).b(true);
            sHandler.removeCallbacks(this.mScrollCache);
          }
          return true;
        }
        break;
      case 1: 
      case 3: 
        localObject = this.mScrollCache;
        ((com.tencent.smtt.d.b)localObject).jdField_c_of_type_Boolean = false;
        if (((com.tencent.smtt.d.b)localObject).jdField_b_of_type_Boolean)
        {
          paramMotionEvent = this.mScrollCache;
          paramMotionEvent.jdField_b_of_type_Boolean = false;
          paramMotionEvent.a();
          scheduleFadeOutScrollBar(this.mScrollCache.jdField_b_of_type_Int);
          return true;
        }
        if (this.mScrollCache.jdField_a_of_type_JavaUtilArrayList != null)
        {
          localObject = this.mScrollCache.jdField_a_of_type_JavaUtilArrayList.iterator();
          while (((Iterator)localObject).hasNext())
          {
            MotionEvent localMotionEvent = (MotionEvent)((Iterator)localObject).next();
            super.superOnTouchEvent(localMotionEvent);
            localMotionEvent.recycle();
          }
          this.mScrollCache.jdField_a_of_type_JavaUtilArrayList.clear();
          localObject = this.mScrollCache;
          ((com.tencent.smtt.d.b)localObject).jdField_a_of_type_JavaUtilArrayList = null;
          scheduleFadeOutScrollBar(((com.tencent.smtt.d.b)localObject).jdField_b_of_type_Int);
        }
        break;
      case 0: 
        if (this.mScrollCache.a() != b.a.a)
        {
          ((com.tencent.smtt.d.a)localObject).a(getRealWebView().computeVerticalScrollRange(), getRealWebView().computeVerticalScrollOffset(), getRealWebView().computeVerticalScrollExtent(), true);
          i = (int)paramMotionEvent.getY();
          j = (int)paramMotionEvent.getX();
          localObject = ((com.tencent.smtt.d.a)localObject).a(true);
          ((Rect)localObject).inset(-this.mScrollCache.e, -this.mScrollCache.e);
          boolean bool = ((Rect)localObject).contains(j + getRealWebView().getScrollX(), i + getRealWebView().getScrollY());
          if (bool) {
            cancelFling(0L);
          }
          localObject = this.mScrollCache;
          ((com.tencent.smtt.d.b)localObject).jdField_b_of_type_Boolean = false;
          ((com.tencent.smtt.d.b)localObject).jdField_c_of_type_Boolean = bool;
          ((com.tencent.smtt.d.b)localObject).jdField_a_of_type_AndroidGraphicsPoint = null;
          if (bool)
          {
            ((com.tencent.smtt.d.b)localObject).jdField_a_of_type_JavaUtilArrayList = new ArrayList();
            this.mScrollCache.jdField_a_of_type_JavaUtilArrayList.add(MotionEvent.obtain(paramMotionEvent));
            return true;
          }
          ((com.tencent.smtt.d.b)localObject).a();
        }
        break;
      }
      return super.superOnTouchEvent(paramMotionEvent);
    }
    return super.superOnTouchEvent(paramMotionEvent);
  }
  
  public boolean overScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    IX5WebViewClientExtension localIX5WebViewClientExtension = getWebViewClientExtension();
    if ((this.isOunterClientForUIEventSetted) && (localIX5WebViewClientExtension != null)) {
      return localIX5WebViewClientExtension.overScrollBy(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean, getView());
    }
    return overScrollByImpl(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
  }
  
  protected boolean overScrollByImpl(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    Object localObject = getH5VideoProxy();
    if (localObject != null) {
      ((d)localObject).a(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
    }
    localObject = this.mScrollListener;
    if ((localObject != null) && (!((IX5ScrollListener)localObject).onOverScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean))) {
      return false;
    }
    return super.superOverScrollBy(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
  }
  
  public boolean pageDown(boolean paramBoolean, int paramInt)
  {
    return super.pageDown(paramBoolean);
  }
  
  public boolean pageUp(boolean paramBoolean, int paramInt)
  {
    return super.pageUp(paramBoolean);
  }
  
  public void pasteText(CharSequence paramCharSequence)
  {
    pasteFromClipboard(paramCharSequence);
  }
  
  public void preConnectQProxy() {}
  
  public void preInitLongClickPopupMenu()
  {
    if (!this.mLongClickPopupMenuInited)
    {
      try
      {
        initX5SelectionIfNeeded();
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
      this.mLongClickPopupMenuInited = true;
    }
  }
  
  public void preLoad(String paramString, int paramInt1, int paramInt2, Map<String, String> paramMap)
  {
    getWebViewProvider().getExtension().preLoad(paramString, paramInt1, paramInt2, paramMap);
  }
  
  public int preLoadScreenAd(String paramString)
  {
    return SmttServiceProxy.getInstance().preLoadScreenAd(getContext(), paramString);
  }
  
  public void registerServiceWorkerBackground(String paramString1, String paramString2)
  {
    getWebViewProvider().getExtension().registerServiceWorkerBackground(paramString1, paramString2);
  }
  
  public void registerServiceWorkerOffline(String paramString, List<String> paramList, boolean paramBoolean) {}
  
  public void removeUserSelectedAdInfoByDomain(String paramString)
  {
    getWebViewProvider().getExtension().removeUserSelectedAdInfoByDomain(paramString);
  }
  
  public void replaceAllInputText(String paramString)
  {
    super.replaceAllInputText(paramString);
  }
  
  public boolean requestFocusForInputNode(int paramInt)
  {
    return super.requestFocusForInputNode(paramInt);
  }
  
  public void saveDynamicPageToDisk(String paramString, Message paramMessage)
  {
    paramMessage.arg1 = 0;
    paramMessage.sendToTarget();
  }
  
  public void savePageToDisk(String paramString, final Message paramMessage)
  {
    super.saveWebArchive(paramString, false, new ValueCallback()
    {
      public void onReceiveValue(String paramAnonymousString)
      {
        if (paramAnonymousString != null) {
          paramMessage.arg1 = 1;
        } else {
          paramMessage.arg1 = 0;
        }
        paramAnonymousString = X5WebViewAdapter.this.getUrl();
        SmttServiceProxy.getInstance().onReportSavePageToDiskInfo(paramAnonymousString, TencentURLUtil.getHost(paramAnonymousString), paramMessage.arg1);
        paramMessage.sendToTarget();
      }
    });
  }
  
  public void savePageToDisk(String paramString, Boolean paramBoolean, Message paramMessage)
  {
    paramMessage.arg1 = 0;
    paramMessage.sendToTarget();
  }
  
  public void savePageToDisk(String paramString, boolean paramBoolean, int paramInt, ValueCallback<String> paramValueCallback)
  {
    super.saveWebArchive(paramString, false, paramValueCallback);
  }
  
  public int seletionStatus()
  {
    return selectionType();
  }
  
  public void setAddressbarDisplayMode(int paramInt, boolean paramBoolean)
  {
    super.setAddressbarDisplayMode(paramInt, paramBoolean);
  }
  
  public void setAudioAutoPlayNotify(boolean paramBoolean)
  {
    getWebViewProvider().getExtension().setAudioAutoPlayNotify(paramBoolean);
  }
  
  public void setBackFromSystem()
  {
    super.setBackFromSystem();
  }
  
  public void setEmbTitleView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    View localView = this.mTitleView;
    if (localView == paramView) {
      return;
    }
    if (localView != null) {
      getRealWebView().removeView(this.mTitleView);
    }
    if (paramView != null) {
      getRealWebView().addView(paramView, paramLayoutParams);
    }
    this.mTitleView = paramView;
  }
  
  public void setEntryDataForSearchTeam(String paramString)
  {
    getWebViewProvider().getExtension().setEntryDataForSearchTeam(paramString);
  }
  
  public void setEventType(int paramInt)
  {
    this.mEventType = paramInt;
  }
  
  public void setEyeShieldMode(boolean paramBoolean, int paramInt)
  {
    getWebViewProvider().getExtension().setEyeShieldMode(paramBoolean, paramInt);
  }
  
  public void setFakeLoginParams(Bundle paramBundle)
  {
    getWebViewProvider().getExtension().setFakeLoginParams(paramBundle);
  }
  
  public void setForceEnableZoom(boolean paramBoolean)
  {
    getWebViewProvider().getExtension().setForceEnableZoom(paramBoolean);
  }
  
  public void setHandleViewBitmap(Bitmap paramBitmap1, Bitmap paramBitmap2, int paramInt1, int paramInt2)
  {
    getWebViewProvider().getExtension().setHandleViewBitmap(paramBitmap1, paramBitmap2, paramInt1, paramInt2);
  }
  
  public void setHandleViewLineColor(int paramInt1, int paramInt2)
  {
    getWebViewProvider().getExtension().setHandleViewLineColor(paramInt1, paramInt2);
  }
  
  public void setHandleViewLineIsShowing(boolean paramBoolean, int paramInt)
  {
    getWebViewProvider().getExtension().setHandleViewLineIsShowing(paramBoolean, paramInt);
  }
  
  public void setHandleViewSelectionColor(int paramInt1, int paramInt2)
  {
    getWebViewProvider().getExtension().setHandleViewSelectionColor(paramInt1, paramInt2);
  }
  
  public void setHasShownScreenAd(boolean paramBoolean)
  {
    if (this.mAdWebViewController != null) {
      this.mAdWebViewController.setHasShownScreenAd(paramBoolean);
    }
  }
  
  public void setHorizontalScrollBarDrawable(Drawable paramDrawable)
  {
    this.mScrollCache.jdField_a_of_type_ComTencentSmttDA.c(paramDrawable);
  }
  
  public void setHorizontalScrollBarEnabled(boolean paramBoolean)
  {
    int i;
    if (paramBoolean != getRealWebView().isHorizontalScrollBarEnabled()) {
      i = 1;
    } else {
      i = 0;
    }
    super.super_setHorizontalScrollBarEnabled(paramBoolean);
    if (i != 0) {
      getRealWebView().invalidate();
    }
  }
  
  public void setHorizontalTrackDrawable(Drawable paramDrawable)
  {
    this.mScrollCache.jdField_a_of_type_ComTencentSmttDA.d(paramDrawable);
  }
  
  @UsedByReflection("MiniQB")
  public void setIsMiniQB(boolean paramBoolean)
  {
    this.mIsMiniQB = paramBoolean;
    getWebViewProvider().getExtension().getSettingsExtension().setIsMiniQB(this.mIsMiniQB);
    getWebViewProvider().getExtension().getSettingsExtension().setContentCacheEnable(paramBoolean);
    if (paramBoolean) {
      getWebViewProvider().getExtension().getSettingsExtension().setARModeEnable(false);
    }
  }
  
  public void setLastHitTestBmp(Bitmap paramBitmap)
  {
    this.mLastHitTestBmp = paramBitmap;
  }
  
  public void setLongPressTextExtensionMenu(int paramInt)
  {
    this.mLongPressTextExtensionMenu = paramInt;
  }
  
  public void setMainX5WebviewMaxOverScrollY(int paramInt)
  {
    this.mMainX5WebviewMaxOverScrollY = paramInt;
    if (MttLog.isEnableLog())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("X5WebViewAdapter--setMainX5WebviewMaxOverScrollY()--mMainX5WebviewMaxOverScrollY =");
      localStringBuilder.append(this.mMainX5WebviewMaxOverScrollY);
      Log.e("NativeAdDebug", localStringBuilder.toString());
    }
  }
  
  public void setMiniProgramPkgName(String paramString)
  {
    this.mMiniProgramPkgName = paramString;
  }
  
  @UsedByReflection("MiniQB")
  public void setMiniQBSourcePosID(int paramInt)
  {
    this.mMiniQBSourcePosID = paramInt;
  }
  
  public void setOnLongClickListener(final View.OnLongClickListener paramOnLongClickListener)
  {
    super.super_setOnLongClickListener(new View.OnLongClickListener()
    {
      /* Error */
      public boolean onLongClick(View paramAnonymousView)
      {
        // Byte code:
        //   0: invokestatic 33	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:access$508	()I
        //   3: pop
        //   4: invokestatic 36	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:access$500	()I
        //   7: istore_2
        //   8: iload_2
        //   9: bipush 20
        //   11: if_icmple +10 -> 21
        //   14: iconst_0
        //   15: invokestatic 40	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:access$502	(I)I
        //   18: pop
        //   19: iconst_0
        //   20: ireturn
        //   21: aload_0
        //   22: getfield 19	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter$5:this$0	Lcom/tencent/tbs/core/webkit/adapter/X5WebViewAdapter;
        //   25: invokestatic 46	java/lang/System:currentTimeMillis	()J
        //   28: invokestatic 50	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:access$602	(Lcom/tencent/tbs/core/webkit/adapter/X5WebViewAdapter;J)J
        //   31: pop2
        //   32: aload_0
        //   33: getfield 19	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter$5:this$0	Lcom/tencent/tbs/core/webkit/adapter/X5WebViewAdapter;
        //   36: iconst_1
        //   37: invokestatic 54	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:access$702	(Lcom/tencent/tbs/core/webkit/adapter/X5WebViewAdapter;Z)Z
        //   40: pop
        //   41: aload_0
        //   42: getfield 21	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter$5:val$l	Landroid/view/View$OnLongClickListener;
        //   45: ifnull +21 -> 66
        //   48: aload_0
        //   49: getfield 21	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter$5:val$l	Landroid/view/View$OnLongClickListener;
        //   52: aload_1
        //   53: invokeinterface 56 2 0
        //   58: istore_3
        //   59: iconst_0
        //   60: invokestatic 40	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:access$502	(I)I
        //   63: pop
        //   64: iload_3
        //   65: ireturn
        //   66: iconst_0
        //   67: invokestatic 40	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:access$502	(I)I
        //   70: pop
        //   71: iconst_0
        //   72: ireturn
        //   73: astore_1
        //   74: iconst_0
        //   75: invokestatic 40	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:access$502	(I)I
        //   78: pop
        //   79: aload_1
        //   80: athrow
        //   81: iconst_0
        //   82: invokestatic 40	com/tencent/tbs/core/webkit/adapter/X5WebViewAdapter:access$502	(I)I
        //   85: pop
        //   86: iconst_0
        //   87: ireturn
        //   88: astore_1
        //   89: goto -8 -> 81
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	92	0	this	5
        //   0	92	1	paramAnonymousView	View
        //   7	5	2	i	int
        //   58	7	3	bool	boolean
        // Exception table:
        //   from	to	target	type
        //   0	8	73	finally
        //   21	59	73	finally
        //   0	8	88	java/lang/Exception
        //   21	59	88	java/lang/Exception
      }
    });
  }
  
  public void setOrientation(int paramInt) {}
  
  public void setOverScrollEnable(boolean paramBoolean)
  {
    IX5WebHistoryItem localIX5WebHistoryItem = getHistoryItem(0);
    if (localIX5WebHistoryItem == null) {
      return;
    }
    getWebViewProvider().getExtension().changeOverScrollState(localIX5WebHistoryItem.getId(), paramBoolean);
  }
  
  public void setQuickSelectCopy(boolean paramBoolean)
  {
    this.mbQuickSelectCopy = paramBoolean;
  }
  
  public void setRenderMode(int paramInt)
  {
    getWebViewProvider().getExtension().setRenderMode(paramInt);
  }
  
  public void setScreenState(int paramInt) {}
  
  public void setScrollBarDefaultDelayBeforeFade(int paramInt)
  {
    this.mScrollCache.jdField_b_of_type_Int = paramInt;
  }
  
  @SuppressLint({"Override"})
  public void setScrollBarFadeDuration(int paramInt)
  {
    this.mScrollCache.jdField_c_of_type_Int = paramInt;
  }
  
  public void setScrollBarFadingEnabled(boolean paramBoolean)
  {
    this.mScrollCache.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  @SuppressLint({"Override"})
  public void setScrollBarSize(int paramInt)
  {
    this.mScrollCache.d = paramInt;
  }
  
  public void setScrollListener(IX5ScrollListener paramIX5ScrollListener)
  {
    checkThread();
    this.mScrollListener = paramIX5ScrollListener;
  }
  
  public void setSelectionViewPosition(Point paramPoint)
  {
    if (this.mSelectionListener.isUseDialog())
    {
      localObject = (WindowManager.LayoutParams)this.mSelectionListener.getSelectLayoutParams();
      if (localObject == null) {
        return;
      }
      ((WindowManager.LayoutParams)localObject).gravity = 51;
      int[] arrayOfInt1 = new int[2];
      getRealWebView().getLocationOnScreen(arrayOfInt1);
      Rect localRect = new Rect();
      getRealWebView().getWindowVisibleDisplayFrame(localRect);
      int i = arrayOfInt1[1];
      int j = arrayOfInt1[1];
      getRealWebView().getHeight();
      j = localRect.bottom;
      j = this.mSelectionListener.getCopyViewHeight();
      int[] arrayOfInt2 = this.mSelectionListener.getSelectYZone();
      ((WindowManager.LayoutParams)localObject).x = (paramPoint.x + arrayOfInt1[0]);
      if (arrayOfInt2[0] + i - localRect.top > j)
      {
        ((WindowManager.LayoutParams)localObject).y = (i + arrayOfInt2[0] - localRect.top - j);
        this.mSelectionListener.setArrowVisable(false);
      }
      else
      {
        ((WindowManager.LayoutParams)localObject).y = (i + arrayOfInt2[1]);
        this.mSelectionListener.setArrowVisable(true);
      }
      this.mSelectionListener.updateDialogWindowsAttributes((WindowManager.LayoutParams)localObject);
      return;
    }
    Object localObject = (AbsoluteLayout.LayoutParams)this.mSelectionListener.getSelectLayoutParams();
    if (localObject == null) {
      return;
    }
    ((AbsoluteLayout.LayoutParams)localObject).x = (getRealWebView().getScrollX() + paramPoint.x);
    ((AbsoluteLayout.LayoutParams)localObject).y = (getRealWebView().getScrollY() + paramPoint.y);
  }
  
  public void setSiteSecurityInfo(String paramString1, String paramString2)
  {
    getWebViewProvider().getExtension().setSiteSecurityInfo(paramString1, paramString2);
  }
  
  public void setSkvDataForSearchTeam(String paramString)
  {
    getWebViewProvider().getExtension().setSkvDataForSearchTeam(paramString);
  }
  
  public void setTextFieldInLongPressStatus(boolean paramBoolean) {}
  
  public void setVerticalScrollBarDrawable(Drawable paramDrawable)
  {
    this.mScrollCache.jdField_a_of_type_ComTencentSmttDA.a(paramDrawable);
  }
  
  public void setVerticalScrollBarEnabled(boolean paramBoolean)
  {
    int i;
    if (paramBoolean != getRealWebView().isVerticalScrollBarEnabled()) {
      i = 1;
    } else {
      i = 0;
    }
    super.super_setVerticalScrollBarEnabled(paramBoolean);
    if (i != 0) {
      getRealWebView().invalidate();
    }
  }
  
  public void setVerticalTrackDrawable(Drawable paramDrawable)
  {
    mUseTbsTrackDrawable = false;
    this.mScrollCache.jdField_a_of_type_ComTencentSmttDA.b(paramDrawable);
  }
  
  public boolean shouldPopupHideAdDialog(String paramString)
  {
    if (!SmttServiceProxy.getInstance().getPopupHideAdDialogEnabled()) {
      return false;
    }
    if (!WebSettingsExtension.getRemoveAds()) {
      return false;
    }
    if (!SmttServiceProxy.getInstance().shouldPopupHideAdDialogByUrl(paramString)) {
      return false;
    }
    if (getWebViewProvider().getExtension().shouldPopupHideAdDialog())
    {
      mUrl = paramString;
      return true;
    }
    return false;
  }
  
  public int showScreenAd(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    return SmttServiceProxy.getInstance().showScreenAd(getContext(), this, paramString, paramBoolean1, false);
  }
  
  public void snapshotVisible(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, float paramFloat1, float paramFloat2, Runnable paramRunnable)
  {
    checkThread();
    super.snapshotVisibleWithBitmap(paramBitmap, paramBoolean1, paramBoolean3, paramBoolean4, paramFloat1, paramFloat2, paramRunnable);
  }
  
  public void snapshotVisible(Canvas paramCanvas, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    checkThread();
    super.snapshotVisible(paramCanvas, paramBoolean1, paramBoolean3, paramBoolean4);
    if ((paramBoolean3) && (this.mTitleView != null))
    {
      int i = getRealWebView().getScrollY();
      int j = this.mTitleView.getHeight();
      if ((i >= 0) && (i < j))
      {
        j = this.mTitleView.getLeft();
        int k = this.mTitleView.getTop();
        View localView = this.mTitleView;
        localView.offsetLeftAndRight(-localView.getLeft());
        localView = this.mTitleView;
        localView.offsetTopAndBottom(-localView.getTop() - i);
        i = this.mTitleView.getVisibility();
        this.mTitleView.setVisibility(0);
        this.mTitleView.draw(paramCanvas);
        this.mTitleView.setVisibility(i);
        paramCanvas = this.mTitleView;
        paramCanvas.offsetLeftAndRight(j - paramCanvas.getLeft());
        paramCanvas = this.mTitleView;
        paramCanvas.offsetTopAndBottom(k - paramCanvas.getTop());
      }
    }
  }
  
  @UsedByReflection("MiniQB")
  public void snapshotVisibleWithBitmap(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, float paramFloat1, float paramFloat2)
  {
    checkThread();
    super.snapshotVisibleWithBitmap(paramBitmap, paramBoolean1, paramBoolean3, paramBoolean4, paramFloat1, paramFloat2);
    if ((paramBoolean3) && (this.mTitleView != null))
    {
      Log.e("X5WebViewAdapter", "snapshotVisibleWithBitmap drawTitleBar");
      int i = getRealWebView().getScrollY();
      int j = this.mTitleView.getHeight();
      if ((i >= 0) && (i < j))
      {
        j = this.mTitleView.getLeft();
        int k = this.mTitleView.getTop();
        View localView = this.mTitleView;
        localView.offsetLeftAndRight(-localView.getLeft());
        localView = this.mTitleView;
        localView.offsetTopAndBottom(-localView.getTop() - i);
        i = this.mTitleView.getVisibility();
        this.mTitleView.setVisibility(0);
        paramBitmap = new Canvas(paramBitmap);
        paramBitmap.scale(paramFloat1, paramFloat2);
        this.mTitleView.draw(paramBitmap);
        this.mTitleView.setVisibility(i);
        paramBitmap = this.mTitleView;
        paramBitmap.offsetLeftAndRight(j - paramBitmap.getLeft());
        paramBitmap = this.mTitleView;
        paramBitmap.offsetTopAndBottom(k - paramBitmap.getTop());
      }
    }
  }
  
  @UsedByReflection("MiniQB")
  public void snapshotVisibleWithBitmap(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, float paramFloat1, float paramFloat2, Runnable paramRunnable)
  {
    super.snapshotVisibleWithBitmap(paramBitmap, paramBoolean1, paramBoolean3, paramBoolean4, paramFloat1, paramFloat2, paramRunnable);
  }
  
  public void snapshotVisibleWithBitmapThreaded(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, float paramFloat1, float paramFloat2, int paramInt)
  {
    super.snapshotVisibleWithBitmapThreaded(paramBitmap, paramBoolean1, paramBoolean3, paramBoolean4, paramFloat1, paramFloat2, paramInt);
  }
  
  public void snapshotWholePage(Canvas paramCanvas, boolean paramBoolean1, boolean paramBoolean2)
  {
    checkThread();
    super.snapshotWholePage(paramCanvas, paramBoolean2);
    if (paramBoolean2)
    {
      View localView = this.mTitleView;
      localView.offsetLeftAndRight(-localView.getLeft());
      localView = this.mTitleView;
      localView.offsetLeftAndRight(-localView.getTop());
      this.mTitleView.draw(paramCanvas);
    }
  }
  
  public void snapshotWholePage(Canvas paramCanvas, boolean paramBoolean1, boolean paramBoolean2, Runnable paramRunnable)
  {
    checkThread();
    super.snapshotWholePage(paramCanvas, paramBoolean2, paramRunnable);
    if (paramBoolean2)
    {
      paramRunnable = this.mTitleView;
      paramRunnable.offsetLeftAndRight(-paramRunnable.getLeft());
      paramRunnable = this.mTitleView;
      paramRunnable.offsetLeftAndRight(-paramRunnable.getTop());
      this.mTitleView.draw(paramCanvas);
    }
  }
  
  public void stopPreLoad(String paramString)
  {
    getWebViewProvider().getExtension().stopPreLoad(paramString);
  }
  
  public boolean super_onTouchEvent(MotionEvent paramMotionEvent)
  {
    return onTouchEventImpl(paramMotionEvent);
  }
  
  public boolean super_overScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    return overScrollByImpl(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void switchContext(Context paramContext)
  {
    setContext(paramContext);
  }
  
  public void unRegisterServiceWorker(String paramString, boolean paramBoolean) {}
  
  public void updateImageList(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    getWebViewProvider().getExtension().updateImageList(paramInt1, paramInt2, paramBoolean);
  }
  
  public void updateImageList2(int paramInt1, int paramInt2, boolean paramBoolean, ValueCallback<Integer> paramValueCallback)
  {
    getWebViewProvider().getExtension().updateImageList2(paramInt1, paramInt2, paramBoolean, paramValueCallback);
  }
  
  public void updateServiceWorkerBackground(String paramString)
  {
    getWebViewProvider().getExtension().updateServiceWorkerBackground(paramString);
  }
  
  public void uploadLongclick()
  {
    if ((this.mFromLongClick) && (this.mEventType == 0))
    {
      this.mFromLongClick = false;
      long l1 = System.currentTimeMillis();
      long l2 = this.mTimePointLongClickBegin;
      String str;
      if (l1 - l2 < 500L) {
        str = "TBS20181114CL500";
      } else if (l1 - l2 < 1000L) {
        str = "TBS20181114CL1000";
      } else if (l1 - l2 < 1500L) {
        str = "TBS20181114CL1500";
      } else if (l1 - l2 < 2000L) {
        str = "TBS20181114CL2000";
      } else if (l1 - l2 < 2500L) {
        str = "TBS20181114CL2500";
      } else if (l1 - l2 < 3000L) {
        str = "TBS20181114CL3000";
      } else {
        str = "TBS20181114CLMAX";
      }
      SmttServiceProxy.getInstance().userBehaviorStatistics(str);
      return;
    }
  }
  
  public boolean useDialogSubMenuAuto()
  {
    return getWebViewProvider().getExtension().getSettingsExtension().useDialogSubMenuAuto();
  }
  
  public void waitSWInstalled(String paramString, final Message paramMessage)
  {
    paramMessage = new ValueCallback()
    {
      public void onReceiveValue(Boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean.booleanValue()) {
          paramMessage.arg1 = 1;
        } else {
          paramMessage.arg1 = 0;
        }
        paramMessage.sendToTarget();
      }
    };
    SWOfflineFramework.getInstance().waitSWInstalled(paramString, paramMessage);
  }
  
  public Drawable wrapDrawableWithNativeBitmap(Drawable paramDrawable, int paramInt, Bitmap.Config paramConfig)
  {
    return paramDrawable;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\adapter\X5WebViewAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */