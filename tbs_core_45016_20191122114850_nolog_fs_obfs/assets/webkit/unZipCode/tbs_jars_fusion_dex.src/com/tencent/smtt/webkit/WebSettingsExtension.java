package com.tencent.smtt.webkit;

import android.content.Context;
import com.tencent.common.http.Apn;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebSettingsExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.internal.interfaces.IX5QQBrowserSettings;
import com.tencent.smtt.export.internal.interfaces.IX5QQBrowserSettings.ImageQuality;
import com.tencent.smtt.net.AwNetworkUtils;
import com.tencent.smtt.util.MttTraceEvent;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.util.b;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.CookieManager;
import java.util.List;
import java.util.Map;
import org.chromium.android_webview.AwSettings;
import org.chromium.base.CommandLine;
import org.chromium.base.annotations.UsedByReflection;
import org.chromium.tencent.TencentAwSettings;

public class WebSettingsExtension
  implements IX5WebSettingsExtension, IX5QQBrowserSettings
{
  public static final int DEFAULT_CACHE_CAPACITY = 15;
  public static final int DEFAULT_IMAGE_QUALITY_NONWIFI = 2;
  public static final int DEFAULT_IMAGE_QUALITY_WIFI = 3;
  private static final String DOUBLE_TAP_TOAST_COUNT = "double_tap_toast_count";
  public static final int IDENTITY_DEFAULT = 1;
  public static final int IDENTITY_HTC_TMS = 3;
  public static final int IDENTITY_HUAWEI_TMS = 2;
  private static final int MAX_CACHE_CAPACITY = 15;
  private static final int MAX_HTTPDNS_DOMAIN_COUNT = 20;
  private static final String PREF_FILE = "WebViewSettingsExtension";
  private static final int STATE_HOST_CHOSE_DIRECT = 1;
  private static final int STATE_HOST_CHOSE_QPROXY = 2;
  private static Map<String, String> mAdditionalHttpHeaders;
  private static int mCustomImageQuality = -1;
  private static boolean mDoNotTrackEnabled = false;
  private static int mDoubleTapToastCount = 3;
  private static byte mHostUseQProxyState = 0;
  private static b mHttpDnsDomainMatcher;
  private static String mIgnoreHostHsts;
  public static boolean mIsKidMode = false;
  private static boolean mQProxyEnabled = false;
  private static boolean mRemoveAds = e.a().i();
  private static b mSepcialArugmentAddedDomainMatcher;
  private static String mSepecialArgumentForImageRequest;
  private static boolean mShouldRequestFavicon = false;
  private static boolean mShouldTrackVisitedLinks = true;
  private static int mUIIdentity = 0;
  private static boolean mWapSitePreferred = false;
  private static String sMiniQBInfo;
  private boolean mAllowJavaScriptCloseWindow = false;
  private boolean mAllowSwitchToMiniQB = false;
  private boolean mAutoRecoredAndRestoreScaleEnabled = false;
  private TencentAwSettings mAwSettings;
  private boolean mDisplayCutoutEnable;
  private boolean mDynamicCanvasGpu = true;
  private boolean mEnableUnderLIne = false;
  private boolean mEvaluateJavaScriptInSubFrameEnabled = false;
  private int mEvilType = -1;
  private boolean mExitFlushBeaconConditionalEnabled = false;
  private boolean mFitScreenFlag = false;
  private boolean mFloatVideoEnabled = false;
  private boolean mForceVideoSameLayer = false;
  private boolean mImgAsDldFile = true;
  private boolean mIsLongPressMenuSelectCopyEnabled = true;
  private boolean mLongClickPopupMenuExtensionEnabled = false;
  private boolean mOnlyDomTreeBuild = false;
  private int mPageCacheCapacity = 15;
  private boolean mPageSolarEnableFlag = true;
  private boolean mPreFectchEnableWhenHasMedia = false;
  private boolean mReadModeFlag = false;
  private int mRedirectCountLimitFromCloud = -2;
  private boolean mRememberScaleValue = false;
  private boolean mRemoveAds_ForSyncControl = e.a().j();
  private int mSecurityLevel = -1;
  private boolean mShrinksStandaloneImagesToFit = false;
  private boolean mSkipPageFinishEventForAborted = false;
  private boolean mSkipPageFinishEventForOverrideUrlLoading = false;
  private boolean mSmartFullScreenEnabled = false;
  private boolean mStandardFullScreen = false;
  private boolean mSupportLiteWnd = true;
  private boolean mSyncPending = false;
  private boolean mUseDialogSubMenuAuto = true;
  private boolean mUseTbsDefaultSettings = false;
  private a mVideoScreenMode = a.a;
  private String mWebViewIdentity = null;
  private boolean mWebViewInBackground = false;
  private boolean mWorkersEnabled = false;
  private boolean mWrapLineLayoutFLag = false;
  private int mZxWebViewType = 0;
  private boolean misMiniQB = false;
  private String sXCXReferer = null;
  
  static
  {
    mQProxyEnabled = e.a().k();
    mShouldRequestFavicon = true;
    mIgnoreHostHsts = "";
    mAdditionalHttpHeaders = null;
    mUIIdentity = 1;
    mIsKidMode = false;
    mHostUseQProxyState = 0;
    mHttpDnsDomainMatcher = null;
    mSepcialArugmentAddedDomainMatcher = null;
    mSepecialArgumentForImageRequest = null;
    sMiniQBInfo = null;
  }
  
  public WebSettingsExtension(Context paramContext, AwSettings paramAwSettings, IX5WebViewBase paramIX5WebViewBase)
  {
    this.mAwSettings = ((TencentAwSettings)paramAwSettings);
    this.mAwSettings.setQBorTMS(e.a().m());
    if (ContextHolder.getInstance().isTbsDevelopMode()) {
      this.mPageCacheCapacity = 0;
    }
    this.mDynamicCanvasGpu = SmttServiceProxy.getInstance().canUseDynamicCanvasGpu();
    this.mAwSettings.setAutoDetectToOpenFitScreenEnabled(e.a().m());
    e.a().a(this, paramIX5WebViewBase);
    int i = SmttServiceProxy.getInstance().getDisplayCutoutEnableSwitch();
    if (1 == i) {
      this.mDisplayCutoutEnable = false;
    } else if (2 == i) {
      this.mDisplayCutoutEnable = true;
    } else {
      this.mDisplayCutoutEnable = e.a().u();
    }
    this.mRedirectCountLimitFromCloud = SmttServiceProxy.getInstance().getRedirectCountLimit();
    i = this.mRedirectCountLimitFromCloud;
    if (i >= 0) {
      this.mAwSettings.setRedirectCountLimit(i);
    }
  }
  
  public static Map<String, String> getAdditionalHttpHeaders()
  {
    return mAdditionalHttpHeaders;
  }
  
  public static boolean getDoNotTrackEnabled()
  {
    return mDoNotTrackEnabled;
  }
  
  public static int getImageQuality()
  {
    int i = mCustomImageQuality;
    if (i != -1) {
      return i;
    }
    if (Apn.sApnTypeS == 4) {
      return 3;
    }
    return 2;
  }
  
  public static byte getIsHostUseQProxy()
  {
    return mHostUseQProxyState;
  }
  
  public static boolean getIsKidMode()
  {
    return mIsKidMode;
  }
  
  public static String getMiniQBInfo()
  {
    return sMiniQBInfo;
  }
  
  public static boolean getQProxyEnabled()
  {
    return mQProxyEnabled;
  }
  
  public static boolean getRemoveAds()
  {
    return mRemoveAds;
  }
  
  public static boolean getShouldRequestFavicon()
  {
    return mShouldRequestFavicon;
  }
  
  public static int getUIIdentity()
  {
    return mUIIdentity;
  }
  
  public static boolean isHTCTMS()
  {
    return mUIIdentity == 3;
  }
  
  public static boolean isHuaWeiTMS()
  {
    return mUIIdentity == 2;
  }
  
  public static boolean isQB()
  {
    return mUIIdentity == 1;
  }
  
  public static boolean isWapSitePreferredS()
  {
    return mWapSitePreferred;
  }
  
  private native void nativeSyncExension(int paramInt);
  
  private void postSync()
  {
    try
    {
      this.mAwSettings.updateWebkitPreferences();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static String shouldAddSpecialArgumentForThisDomain(String paramString)
  {
    boolean bool = CommandLine.getInstance().hasSwitch("enable-sharpP-dec");
    String str = null;
    if (!bool) {
      return null;
    }
    if (mSepecialArgumentForImageRequest != null)
    {
      b localb = mSepcialArugmentAddedDomainMatcher;
      if (localb == null) {
        return null;
      }
      if (localb.a(paramString)) {
        str = mSepecialArgumentForImageRequest;
      }
      return str;
    }
    return null;
  }
  
  public static boolean shouldHSTSIgnore(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    if (paramString.isEmpty()) {
      return false;
    }
    return paramString.equals(mIgnoreHostHsts);
  }
  
  public static boolean shouldHttpDnsResolveForThisDomain(String paramString)
  {
    b localb = mHttpDnsDomainMatcher;
    if (localb == null) {
      return false;
    }
    return localb.a(paramString);
  }
  
  public boolean autoRecoredAndRestoreScaleEnabled()
  {
    return this.mAutoRecoredAndRestoreScaleEnabled;
  }
  
  public void customDiskCachePathEnabled(boolean paramBoolean, String paramString)
  {
    this.mAwSettings.setCustomDiskCacheEnabled(paramBoolean);
  }
  
  public void enableHSTSIgnore(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty()))
    {
      mIgnoreHostHsts = paramString;
      return;
    }
    mIgnoreHostHsts = "";
  }
  
  public boolean enableSmoothTransition()
  {
    return true;
  }
  
  public boolean evaluateJavaScriptInSubFrameEnabled()
  {
    return this.mEvaluateJavaScriptInSubFrameEnabled;
  }
  
  public boolean exitFlushBeaconConditionalEnabled()
  {
    return this.mExitFlushBeaconConditionalEnabled;
  }
  
  public boolean forceUserScalable()
  {
    return false;
  }
  
  public boolean getARModeEnable()
  {
    return this.mAwSettings.getARModeEnable();
  }
  
  @UsedByReflection("MiniQB")
  public boolean getDayOrNight()
  {
    try
    {
      boolean bool = this.mAwSettings.getDayOrNight();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getDisplayCutoutEnable()
  {
    return this.mDisplayCutoutEnable;
  }
  
  public boolean getFakeLoginEnabled()
  {
    return this.mAwSettings.getFakeLoginEnabledLocked();
  }
  
  public boolean getFeedsReadingEnabled()
  {
    return this.mAwSettings.getFeedsReadingEnabled();
  }
  
  public boolean getForcePinchScaleEnabled()
  {
    return this.mAwSettings.getForcePinchScaleEnabled();
  }
  
  public boolean getImageScanEnable()
  {
    return this.mAwSettings.getImageScanEnabled();
  }
  
  public boolean getIsLongPressMenuSelectCopyEnabled()
  {
    return this.mIsLongPressMenuSelectCopyEnabled;
  }
  
  public boolean getJavaScriptOpenWindowsBlockedNotifyEnabled()
  {
    return this.mAwSettings.getJavaScriptOpenWindowsBlockedNotifyEnabled();
  }
  
  public boolean getLongClickPopupMenuExtensionEnabled()
  {
    return this.mLongClickPopupMenuExtensionEnabled;
  }
  
  public boolean getPageSolarEnableFlag()
  {
    try
    {
      boolean bool = this.mPageSolarEnableFlag;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getPrereadingEnabled()
  {
    try
    {
      boolean bool = this.mAwSettings.getPrereadingEnabled();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getSecurityInfoEvilType()
  {
    return this.mEvilType;
  }
  
  public int getSecurityInfoSecurityLevel()
  {
    return this.mSecurityLevel;
  }
  
  public boolean getShouldBubbleColorModeChanged()
  {
    return this.mAwSettings.getShouldBubbleColorModeChanged();
  }
  
  public boolean getShouldTrackVisitedLinks()
  {
    try
    {
      boolean bool = mShouldTrackVisitedLinks;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getShowTtsBar()
  {
    return this.mAwSettings.getShowTtsBarEnable();
  }
  
  public int getTbsARShareType()
  {
    return this.mAwSettings.getTbsARShareType();
  }
  
  public boolean getUseTbsDefaultSettings()
  {
    return this.mUseTbsDefaultSettings;
  }
  
  public boolean getUseWebViewBackgroundForOverscrollBackground()
  {
    return false;
  }
  
  public boolean getVideoPlaybackRequiresUserGesture()
  {
    return this.mAwSettings.getVideoPlaybackRequiresUserGesture();
  }
  
  public a getVideoScreenMode()
  {
    return this.mVideoScreenMode;
  }
  
  public boolean getWebTranslation()
  {
    return this.mAwSettings.getWebTranslation();
  }
  
  public String getWebViewIdentity()
  {
    return this.mWebViewIdentity;
  }
  
  public int getZxWebViewType()
  {
    return this.mZxWebViewType;
  }
  
  public boolean isAllowSwitchToMiniQB()
  {
    return this.mAllowSwitchToMiniQB;
  }
  
  public boolean isFitScreen()
  {
    try
    {
      boolean bool = this.mFitScreenFlag;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean isFloatVideoEnabled()
  {
    return this.mFloatVideoEnabled;
  }
  
  public boolean isForceVideoSameLayer()
  {
    return this.mForceVideoSameLayer;
  }
  
  public boolean isMiniQB()
  {
    return this.misMiniQB;
  }
  
  public boolean isNarrowColumnLayout()
  {
    return true;
  }
  
  public boolean isPageCacheDisabled()
  {
    try
    {
      int i = this.mPageCacheCapacity;
      boolean bool;
      if (i == 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean isReadModeWebView()
  {
    return this.mReadModeFlag;
  }
  
  public boolean isStandardFullScreen()
  {
    return this.mStandardFullScreen;
  }
  
  public boolean isSupportLiteWnd()
  {
    return this.mSupportLiteWnd;
  }
  
  public boolean isWapSitePreferred()
  {
    return mWapSitePreferred;
  }
  
  public boolean isWebViewInBackground()
  {
    return this.mWebViewInBackground;
  }
  
  public boolean isWrapLineLayoutEnabled()
  {
    try
    {
      boolean bool = this.mWrapLineLayoutFLag;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  void onDestroyed() {}
  
  public void setARModeEnable(boolean paramBoolean)
  {
    this.mAwSettings.setARModeEnable(paramBoolean);
  }
  
  public void setAcceptCookie(boolean paramBoolean)
  {
    CookieManager.getInstance().setAcceptCookie(paramBoolean);
  }
  
  public void setAdditionalHttpHeaders(Map<String, String> paramMap)
  {
    mAdditionalHttpHeaders = paramMap;
  }
  
  public void setAllowScriptsToCloseWindows(boolean paramBoolean)
  {
    try
    {
      if (this.mAllowJavaScriptCloseWindow != paramBoolean)
      {
        this.mAllowJavaScriptCloseWindow = paramBoolean;
        postSync();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setAllowSwitchToMiniQB(boolean paramBoolean)
  {
    this.mAllowSwitchToMiniQB = paramBoolean;
  }
  
  public void setAudioAutoPlayNotify(boolean paramBoolean)
  {
    this.mAwSettings.setAudioAutoPlayNotify(paramBoolean);
  }
  
  public void setAutoDetectToOpenFitScreenEnabled(boolean paramBoolean)
  {
    this.mAwSettings.setAutoDetectToOpenFitScreenEnabled(paramBoolean);
  }
  
  public void setAutoRecoredAndRestoreScaleEnabled(boolean paramBoolean)
  {
    this.mAutoRecoredAndRestoreScaleEnabled = paramBoolean;
  }
  
  public void setAutoRemoveAdsEnabled(boolean paramBoolean)
  {
    if (this.mRemoveAds_ForSyncControl != paramBoolean)
    {
      this.mRemoveAds_ForSyncControl = paramBoolean;
      mRemoveAds = paramBoolean;
      this.mAwSettings.setRemoveAds(paramBoolean);
      postSync();
    }
  }
  
  public void setBFBeforeUnloadEnabled(boolean paramBoolean)
  {
    this.mAwSettings.setBFBeforeUnloadEnabled(paramBoolean);
  }
  
  public void setContentCacheCacheableQuirkEnabled(boolean paramBoolean)
  {
    this.mAwSettings.setContentCacheCacheableQuirkEnabled(paramBoolean);
  }
  
  public void setContentCacheEnable(boolean paramBoolean)
  {
    this.mAwSettings.setContentCacheEnable(paramBoolean);
  }
  
  public void setDataFilterForRequestInfo(int paramInt)
  {
    this.mAwSettings.setDataFilterForRequestInfo(paramInt);
  }
  
  public void setDayOrNight(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setDayOrNight(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setDiskCahceSize(int paramInt)
  {
    if (paramInt > 20971520)
    {
      if (paramInt > 83886080) {
        return;
      }
      if (SmttServiceProxy.getInstance().getDiskCacheSizeSwitch())
      {
        CommandLine localCommandLine = CommandLine.getInstance();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramInt);
        localStringBuilder.append("");
        localCommandLine.appendSwitchWithValue("disk-cache-size", localStringBuilder.toString());
      }
      return;
    }
  }
  
  public void setDisplayCutoutEnable(boolean paramBoolean)
  {
    this.mDisplayCutoutEnable = paramBoolean;
  }
  
  public void setDoNotTrackEnabled(boolean paramBoolean)
  {
    mDoNotTrackEnabled = paramBoolean;
  }
  
  public void setDomainsAndArgumentForImageRequest(List<String> paramList, String paramString)
  {
    if ((paramList != null) && (paramList.size() != 0))
    {
      if (paramString == null) {
        return;
      }
      if (SmttServiceProxy.getInstance().getAddArgumentForImageRequestEnable())
      {
        if (mSepcialArugmentAddedDomainMatcher == null) {
          mSepcialArugmentAddedDomainMatcher = new b();
        }
        mSepcialArugmentAddedDomainMatcher.a();
        mSepcialArugmentAddedDomainMatcher.a(paramList);
        mSepecialArgumentForImageRequest = paramString;
        return;
      }
      return;
    }
  }
  
  public void setDynamicPageSaveEnabled(boolean paramBoolean) {}
  
  public void setEnableUnderLine(boolean paramBoolean)
  {
    try
    {
      if (this.mEnableUnderLIne != paramBoolean)
      {
        this.mEnableUnderLIne = paramBoolean;
        postSync();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setEvaluateJavaScriptInSubFrameEnabled(boolean paramBoolean)
  {
    this.mEvaluateJavaScriptInSubFrameEnabled = paramBoolean;
  }
  
  public void setExitFlushBeaconConditionalEnable(boolean paramBoolean)
  {
    this.mExitFlushBeaconConditionalEnabled = paramBoolean;
  }
  
  public void setFakeLoginEnabled(boolean paramBoolean)
  {
    this.mAwSettings.setFakeLoginEnabled(paramBoolean);
  }
  
  public void setFeedsReadingEnabled(boolean paramBoolean)
  {
    this.mAwSettings.setFeedsReadingEnabled(paramBoolean);
  }
  
  public void setFirstScreenDetect(boolean paramBoolean)
  {
    this.mAwSettings.setFirstScreenDetect(paramBoolean);
  }
  
  public void setFirstScreenSoftwareTextureDraw(boolean paramBoolean)
  {
    this.mAwSettings.setFirstScreenDrawFullScreen(paramBoolean);
  }
  
  public void setFitScreen(boolean paramBoolean)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("FitScreenEnabled=");
      localStringBuilder.append(paramBoolean);
      X5Log.i("LAYOUT", localStringBuilder.toString());
      if (this.mFitScreenFlag != paramBoolean)
      {
        this.mFitScreenFlag = paramBoolean;
        this.mAwSettings.setFitScreenEnabled(paramBoolean);
        postSync();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setFloatVideoEnabled(boolean paramBoolean)
  {
    this.mFloatVideoEnabled = paramBoolean;
  }
  
  public void setForbitInputPassword(boolean paramBoolean)
  {
    this.mAwSettings.setForbitInputPassword(paramBoolean);
  }
  
  public void setForcePinchScale(boolean paramBoolean, String paramString)
  {
    this.mAwSettings.setForcePinchScale(paramBoolean, paramString);
  }
  
  public void setForcePinchScaleEnabled(boolean paramBoolean)
  {
    this.mAwSettings.setForcePinchScaleEnabled(paramBoolean);
  }
  
  public void setForceVideoSameLayer(boolean paramBoolean)
  {
    this.mForceVideoSameLayer = paramBoolean;
  }
  
  public void setFrameLimitCount(int paramInt)
  {
    this.mAwSettings.setFrameLimitCount(paramInt);
  }
  
  public void setFramePerformanceRecordEnable(boolean paramBoolean)
  {
    if (SmttServiceProxy.getInstance().getFramePerformanceRecordEnable()) {
      this.mAwSettings.setFramePerformanceRecordEnable(paramBoolean);
    }
  }
  
  public void setGifControl(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setGifControl(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setHistoryScreenshotEnable(boolean paramBoolean)
  {
    this.mAwSettings.setHistoryScreenshotEnable(paramBoolean);
  }
  
  public boolean setHttpDnsDomains(List<String> paramList)
  {
    if (paramList.size() > 20) {
      return false;
    }
    if (mHttpDnsDomainMatcher == null) {
      mHttpDnsDomainMatcher = new b();
    }
    mHttpDnsDomainMatcher.a();
    mHttpDnsDomainMatcher.a(paramList);
    return true;
  }
  
  public void setImageQuality(IX5QQBrowserSettings.ImageQuality paramImageQuality)
  {
    int i;
    switch (1.a[paramImageQuality.ordinal()])
    {
    default: 
      i = -1;
      break;
    case 2: 
      if (Apn.isWifiMode()) {
        i = 6;
      } else {
        i = 4;
      }
      break;
    case 1: 
      i = 2;
    }
    mCustomImageQuality = i;
  }
  
  public void setImageScanEnable(boolean paramBoolean)
  {
    this.mAwSettings.setImageScanEnabled(paramBoolean);
  }
  
  public void setImgAsDownloadFile(boolean paramBoolean)
  {
    this.mImgAsDldFile = paramBoolean;
    postSync();
  }
  
  public void setImgBrowser(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setImgBrowser(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setIsKidMode(boolean paramBoolean)
  {
    mIsKidMode = paramBoolean;
  }
  
  public void setIsLongPressMenuSelectCopyEnabled(boolean paramBoolean)
  {
    this.mIsLongPressMenuSelectCopyEnabled = paramBoolean;
  }
  
  public void setIsMiniQB(boolean paramBoolean)
  {
    this.misMiniQB = paramBoolean;
    this.mAwSettings.setMiniQBEnabled(paramBoolean);
    if (paramBoolean) {
      e.a().b(this);
    }
  }
  
  public void setIsViewSourceMode(boolean paramBoolean) {}
  
  public void setJSPerformanceRecordEnable(boolean paramBoolean)
  {
    this.mAwSettings.setJSPerformanceRecordEnable(paramBoolean);
  }
  
  public void setJavaScriptOpenWindowsBlockedNotifyEnabled(boolean paramBoolean)
  {
    this.mAwSettings.setJavaScriptOpenWindowsBlockedNotifyEnabled(paramBoolean);
  }
  
  public void setLongClickPopupMenuExtensionEnabled(boolean paramBoolean)
  {
    this.mLongClickPopupMenuExtensionEnabled = paramBoolean;
  }
  
  public void setMSEEnable(boolean paramBoolean)
  {
    this.mAwSettings.setMSEEnabled(paramBoolean);
  }
  
  public void setMediaPlaybackRequiresUserGesture(boolean paramBoolean)
  {
    this.mAwSettings.setMediaPlaybackRequiresUserGesture(paramBoolean);
  }
  
  @UsedByReflection("MiniQB")
  public void setMiniQBInfo(String paramString)
  {
    sMiniQBInfo = paramString;
  }
  
  public void setMixedContentMode(int paramInt)
  {
    this.mAwSettings.setMixedContentMode(paramInt);
  }
  
  public void setOnContextMenuEnable(boolean paramBoolean)
  {
    this.mAwSettings.setOnContextMenuEnable(paramBoolean);
  }
  
  public void setOnlyDomTreeBuild(boolean paramBoolean)
  {
    try
    {
      if (this.mOnlyDomTreeBuild != paramBoolean)
      {
        this.mOnlyDomTreeBuild = paramBoolean;
        postSync();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setPageCacheCapacity(int paramInt)
  {
    int i = paramInt;
    if (paramInt < 0) {
      i = 0;
    }
    paramInt = i;
    if (i > 15) {
      paramInt = 15;
    }
    try
    {
      if (this.mPageCacheCapacity != paramInt)
      {
        this.mPageCacheCapacity = paramInt;
        postSync();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setPageSolarEnableFlag(boolean paramBoolean)
  {
    try
    {
      this.mPageSolarEnableFlag = paramBoolean;
      postSync();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setPicModel(int paramInt)
  {
    try
    {
      this.mAwSettings.setPicModel(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setPreFectch(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setPrereadingEnabled(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setPreFectchEnableWhenHasMedia(boolean paramBoolean)
  {
    try
    {
      if (this.mPreFectchEnableWhenHasMedia != paramBoolean)
      {
        this.mPreFectchEnableWhenHasMedia = paramBoolean;
        postSync();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setQProxyEnabled(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("enable=");
    localStringBuilder.append(paramBoolean);
    MttTraceEvent.begin(4, "WebSettingsExtension.setQProxyEnabled", localStringBuilder.toString());
    mQProxyEnabled = paramBoolean;
    AwNetworkUtils.nativeSetQProxyUserChanged(paramBoolean);
    MttTraceEvent.end(4, "WebSettingsExtension.setQProxyEnabled");
  }
  
  public void setReadModeWebView(boolean paramBoolean)
  {
    this.mReadModeFlag = paramBoolean;
  }
  
  public void setRecordRequestEnabled(boolean paramBoolean) {}
  
  public void setRedirectCountLimit(int paramInt)
  {
    if (this.mRedirectCountLimitFromCloud <= -2) {
      this.mAwSettings.setRedirectCountLimit(paramInt);
    }
  }
  
  public void setRememberScaleValue(boolean paramBoolean)
  {
    try
    {
      if (this.mRememberScaleValue != paramBoolean)
      {
        this.mRememberScaleValue = paramBoolean;
        postSync();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setSecurityInfo(int paramInt1, int paramInt2)
  {
    this.mSecurityLevel = paramInt1;
    this.mEvilType = paramInt2;
  }
  
  public void setSelectionColorEnabled(boolean paramBoolean) {}
  
  public void setShouldBubbleColorModeChanged(boolean paramBoolean)
  {
    this.mAwSettings.setShouldBubbleColorModeChanged(paramBoolean);
  }
  
  public void setShouldRequestFavicon(boolean paramBoolean)
  {
    mShouldRequestFavicon = paramBoolean;
  }
  
  /* Error */
  public void setShouldTrackVisitedLinks(boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 2
    //   4: monitorenter
    //   5: iload_1
    //   6: putstatic 401	com/tencent/smtt/webkit/WebSettingsExtension:mShouldTrackVisitedLinks	Z
    //   9: ldc 2
    //   11: monitorexit
    //   12: aload_0
    //   13: getfield 240	com/tencent/smtt/webkit/WebSettingsExtension:mAwSettings	Lorg/chromium/tencent/TencentAwSettings;
    //   16: iload_1
    //   17: invokevirtual 677	org/chromium/tencent/TencentAwSettings:setShouldTrackVisitedLinks	(Z)V
    //   20: aload_0
    //   21: invokespecial 451	com/tencent/smtt/webkit/WebSettingsExtension:postSync	()V
    //   24: aload_0
    //   25: monitorexit
    //   26: return
    //   27: astore_2
    //   28: ldc 2
    //   30: monitorexit
    //   31: aload_2
    //   32: athrow
    //   33: astore_2
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_2
    //   37: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	38	0	this	WebSettingsExtension
    //   0	38	1	paramBoolean	boolean
    //   27	5	2	localObject1	Object
    //   33	4	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   5	12	27	finally
    //   28	31	27	finally
    //   2	5	33	finally
    //   12	24	33	finally
    //   31	33	33	finally
  }
  
  public void setShowTtsBarEnable(boolean paramBoolean)
  {
    this.mAwSettings.setShowTtsBarEnable(paramBoolean);
  }
  
  public void setShrinksStandaloneImagesToFit(boolean paramBoolean)
  {
    if (this.mShrinksStandaloneImagesToFit != paramBoolean)
    {
      this.mShrinksStandaloneImagesToFit = paramBoolean;
      postSync();
    }
  }
  
  public void setSkipPageFinishEventForAborted(boolean paramBoolean)
  {
    this.mSkipPageFinishEventForAborted = paramBoolean;
  }
  
  public void setSkipPageFinishEventForOverrideUrlLoading(boolean paramBoolean)
  {
    this.mSkipPageFinishEventForOverrideUrlLoading = paramBoolean;
  }
  
  public void setSmartFullScreenEnabled(boolean paramBoolean)
  {
    this.mSmartFullScreenEnabled = paramBoolean;
  }
  
  public void setSplicedNativeAdHeight(int paramInt)
  {
    this.mAwSettings.setSplicedNativeAdHeight(paramInt);
  }
  
  public void setStandardFullScreen(boolean paramBoolean)
  {
    this.mStandardFullScreen = paramBoolean;
  }
  
  public void setSupportLiteWnd(boolean paramBoolean)
  {
    this.mSupportLiteWnd = paramBoolean;
  }
  
  public void setTbsARShareType(int paramInt)
  {
    this.mAwSettings.setTbsARShareType(paramInt);
  }
  
  public void setTextDecorationUnlineEnabled(boolean paramBoolean) {}
  
  public void setUIIdentity(int paramInt)
  {
    mUIIdentity = paramInt;
  }
  
  public void setUseDialogSubMenuAuto(boolean paramBoolean)
  {
    this.mUseDialogSubMenuAuto = paramBoolean;
  }
  
  @UsedByReflection("MiniQB")
  public void setUseQProxy(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      mHostUseQProxyState = 1;
      return;
    }
    mHostUseQProxyState = 2;
  }
  
  public void setUseSurfaceView(boolean paramBoolean)
  {
    this.mAwSettings.setUseSurfaceView(paramBoolean);
  }
  
  public void setUseTbsDefaultSettings(boolean paramBoolean)
  {
    this.mUseTbsDefaultSettings = paramBoolean;
  }
  
  public void setUseWebViewBackgroundForOverscrollBackground(boolean paramBoolean) {}
  
  public void setVideoPlaybackRequiresUserGesture(boolean paramBoolean)
  {
    this.mAwSettings.setVideoPlaybackRequiresUserGesture(paramBoolean);
  }
  
  public void setVideoScreenMode(a parama)
  {
    this.mVideoScreenMode = parama;
  }
  
  public void setVrImageEnable(boolean paramBoolean) {}
  
  public void setWapSitePreferred(boolean paramBoolean)
  {
    mWapSitePreferred = paramBoolean;
  }
  
  public void setWebTranslationEnabled(boolean paramBoolean)
  {
    this.mAwSettings.setWebTranslationEnabled(paramBoolean);
  }
  
  public void setWebViewIdentity(String paramString)
  {
    this.mWebViewIdentity = paramString;
    this.mAwSettings.setWebViewIdentity(paramString);
  }
  
  public void setWebViewInBackground(boolean paramBoolean)
  {
    this.mWebViewInBackground = paramBoolean;
  }
  
  public void setWorkersEnabled(boolean paramBoolean)
  {
    try
    {
      if (this.mWorkersEnabled != paramBoolean)
      {
        this.mWorkersEnabled = paramBoolean;
        postSync();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setWrapLineLayoutEnabled(boolean paramBoolean)
  {
    try
    {
      if (this.mWrapLineLayoutFLag != paramBoolean)
      {
        this.mWrapLineLayoutFLag = paramBoolean;
        this.mAwSettings.setWrapLineLayoutEnabled(paramBoolean);
        postSync();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setXCXReferer(String paramString)
  {
    this.mAwSettings.setXCXReferer(paramString);
  }
  
  public void setZxWebViewType(int paramInt)
  {
    this.mZxWebViewType = paramInt;
  }
  
  public boolean skipPageFinishEventForAborted()
  {
    return this.mSkipPageFinishEventForAborted;
  }
  
  public boolean skipPageFinishEventForOverrideUrlLoading()
  {
    return this.mSkipPageFinishEventForOverrideUrlLoading;
  }
  
  public boolean smartFullScreenEnabled()
  {
    return this.mSmartFullScreenEnabled;
  }
  
  public boolean useDialogSubMenuAuto()
  {
    return this.mUseDialogSubMenuAuto;
  }
  
  public static enum a
  {
    static
    {
      jdField_a_of_type_ComTencentSmttWebkitWebSettingsExtension$a = new a("VideoScreenModeDefault", 0);
      b = new a("VideoScreenModeFullScreen", 1);
      jdField_a_of_type_ArrayOfComTencentSmttWebkitWebSettingsExtension$a = new a[] { jdField_a_of_type_ComTencentSmttWebkitWebSettingsExtension$a, b };
    }
    
    private a() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\WebSettingsExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */