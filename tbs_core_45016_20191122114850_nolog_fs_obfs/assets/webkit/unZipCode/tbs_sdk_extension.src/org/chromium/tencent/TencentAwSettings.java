package org.chromium.tencent;

import android.content.Context;
import android.text.TextUtils;
import org.chromium.android_webview.AwSettings;
import org.chromium.android_webview.AwSettings.EventHandler;
import org.chromium.android_webview.AwSettings.LazyDefaultUserAgent;
import org.chromium.base.annotations.CalledByNative;

public class TencentAwSettings
  extends AwSettings
{
  public static final boolean OverScrollDefaultState = true;
  private static boolean mShouldTrackVisitedLinks = true;
  private int PicModel_INVALID = 0;
  private int PicModel_NORMAL = 1;
  private int PicModel_NetNoPic = 3;
  private int PicModel_NoPic = 2;
  private boolean autoDetectToOpenFitScreenEnabled = false;
  private boolean mARModeEnable = true;
  private int mARShareType = 1;
  private boolean mAudioAutoPlayNotify = false;
  private boolean mBFBeforeUnloadEnabled = true;
  private boolean mContentCacheCacheableQuirkEnabled = false;
  private boolean mContentCacheEnable = false;
  private boolean mCustomDiskCacheEnabled = false;
  private int mDataFilterForRequestInfo = 0;
  private boolean mDayOrNight = true;
  private DayOrNightModeChangeListener mDayOrNightModeChangeListener = null;
  private int mEyeShieldBaseColor = 0;
  private boolean mEyeShieldModeEnabled = false;
  private boolean mFakeLoginEnabled = false;
  private boolean mFeedsReadingEnabled = false;
  private boolean mFirstScreenDetect = true;
  private boolean mFirstScreenDrawFullScreen = true;
  private boolean mFirstScreenDrawNotFullScreen = false;
  private boolean mFitScreenEnabled = false;
  private boolean mForbitInputPassword = false;
  private boolean mForcePinchScale = false;
  private boolean mForcePinchScaleEnabled = false;
  private String mForcePinchScaleHost = "";
  private int mFrameLimitCount = 0;
  private boolean mFramePerformanceRecordEnable = false;
  private boolean mGifControl = false;
  private boolean mHideMediaDownloadUi = true;
  private boolean mHistoryScreenshotEnable = false;
  private boolean mImageScanEnable = true;
  private boolean mImgBrowser = false;
  private boolean mJSPerformanceRecordEnable = false;
  private boolean mJavaScriptOpenWindowsBlockedNotifyEnabled = false;
  private boolean mLayoutAlgorithmHadSet = false;
  private boolean mMiniQBEnabled = false;
  private boolean mOnContextMenuEnable = true;
  private int mPicModel = this.PicModel_NORMAL;
  private PicModelChangeListener mPicModelChangeListener = null;
  private boolean mPrereadingEnabled = false;
  private boolean mQBorTMS = false;
  private int mRedirectCountLimit = -1;
  private boolean mRemoveAds = false;
  private Boolean mShouldBubbleColorModeChanged = Boolean.valueOf(false);
  private boolean mShowTtsBarEnable = false;
  private int mSplicedNativeAdHeight = 0;
  private boolean mUseSurfaceView = false;
  private boolean mVideoPlaybackRequiresUserGesture = true;
  private boolean mWebTranslationEnabled = false;
  private String mWebViewIdentity = null;
  private boolean mWebViewOverScrollEnabled = true;
  private boolean mWrapLineLayoutEnabled = false;
  private String mXCXReferer = null;
  private boolean mforceEnableZoom = false;
  
  public TencentAwSettings(Context arg1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
  {
    super(???, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5);
    synchronized (this.mAwSettingsLock)
    {
      this.mMixedContentMode = 0;
      this.mAcceptThirdPartyCookies = true;
      if (getTextAutosizingEnabledLocked()) {
        this.mTextSizePercent = 100;
      }
      return;
    }
  }
  
  @CalledByNative
  private boolean forceEnableZoomLocked()
  {
    return this.mforceEnableZoom;
  }
  
  private boolean getAutoDetectToOpenFitScreenEnabled()
  {
    return this.autoDetectToOpenFitScreenEnabled;
  }
  
  @CalledByNative
  private boolean getBFBeforeUnloadEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mBFBeforeUnloadEnabled;
      return bool;
    }
  }
  
  @CalledByNative
  private boolean getDayOrNightLocked()
  {
    return this.mDayOrNight;
  }
  
  @CalledByNative
  private int getEyeShieldBaseColor()
  {
    return this.mEyeShieldBaseColor;
  }
  
  @CalledByNative
  private boolean getEyeShieldModeEnabled()
  {
    return this.mEyeShieldModeEnabled;
  }
  
  private boolean getFirstScreenDetect()
  {
    return this.mFirstScreenDetect;
  }
  
  private boolean getFirstScreenDrawFullScreen()
  {
    return this.mFirstScreenDrawFullScreen;
  }
  
  private boolean getFirstScreenDrawNotFullScreen()
  {
    return this.mFirstScreenDrawNotFullScreen;
  }
  
  @CalledByNative
  private boolean getFitScreenEnabled()
  {
    return this.mFitScreenEnabled;
  }
  
  @CalledByNative
  private boolean getForbitInputPasswordLocked()
  {
    return this.mForbitInputPassword;
  }
  
  @CalledByNative
  private int getFrameLimitCount()
  {
    return this.mFrameLimitCount;
  }
  
  private boolean getGifControlLocked()
  {
    return this.mGifControl;
  }
  
  @CalledByNative
  private boolean getHideMediaDownloadUi()
  {
    return this.mHideMediaDownloadUi;
  }
  
  @CalledByNative
  private boolean getImgBrowserLocked()
  {
    return this.mImgBrowser;
  }
  
  @CalledByNative
  private boolean getIncognito()
  {
    return mShouldTrackVisitedLinks ^ true;
  }
  
  @CalledByNative
  private boolean getMiniQBEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mMiniQBEnabled;
      return bool;
    }
  }
  
  @CalledByNative
  private int getPicModelLocked()
  {
    updatePicModel();
    return this.mPicModel;
  }
  
  @CalledByNative
  private int getRedirectCountLimit()
  {
    return this.mRedirectCountLimit;
  }
  
  @CalledByNative
  private boolean getRemoveAds()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mRemoveAds;
      return bool;
    }
  }
  
  @CalledByNative
  private int getSplicedNativeAdHeightLocked()
  {
    return this.mSplicedNativeAdHeight;
  }
  
  @CalledByNative
  private boolean getUseSurfaceViewLocked()
  {
    return this.mUseSurfaceView;
  }
  
  @CalledByNative
  private boolean getVideoPlaybackRequiresUserGestureLocked()
  {
    return this.mVideoPlaybackRequiresUserGesture;
  }
  
  @CalledByNative
  private boolean getWrapLineLayoutEnabled()
  {
    return this.mWrapLineLayoutEnabled;
  }
  
  private String getXCXRefererLocked()
  {
    return this.mXCXReferer;
  }
  
  @CalledByNative
  private boolean isWifiLoginWebView()
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mWebViewIdentity != null)
      {
        boolean bool = this.mWebViewIdentity.equals("wifilogin");
        return bool;
      }
      return false;
    }
  }
  
  private void updatePicModel()
  {
    if (this.mQBorTMS)
    {
      if ((getLoadsImagesAutomatically() == true) && (getImagesEnabled() == true)) {
        synchronized (this.mAwSettingsLock)
        {
          this.mPicModel = this.PicModel_NORMAL;
          if (this.mPicModelChangeListener != null) {
            this.mPicModelChangeListener.onPicModelChangeListener(this.mPicModel);
          }
          return;
        }
      }
      if ((!getLoadsImagesAutomatically()) && (!getImagesEnabled())) {
        synchronized (this.mAwSettingsLock)
        {
          this.mPicModel = this.PicModel_NoPic;
          if (this.mPicModelChangeListener != null) {
            this.mPicModelChangeListener.onPicModelChangeListener(this.mPicModel);
          }
          return;
        }
      }
      if ((getLoadsImagesAutomatically() == true) && (!getImagesEnabled())) {
        synchronized (this.mAwSettingsLock)
        {
          this.mPicModel = this.PicModel_NetNoPic;
          if (this.mPicModelChangeListener != null) {
            this.mPicModelChangeListener.onPicModelChangeListener(this.mPicModel);
          }
          return;
        }
      }
    }
  }
  
  public boolean getARModeEnable()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mARModeEnable;
      return bool;
    }
  }
  
  public boolean getAudioAutoPlayNotify()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getAudioAutoPlayNotifyLocked();
      return bool;
    }
  }
  
  @CalledByNative
  public boolean getAudioAutoPlayNotifyLocked()
  {
    return this.mAudioAutoPlayNotify;
  }
  
  @CalledByNative
  public boolean getContentCacheCacheableQuirkEnabledLocked()
  {
    return this.mContentCacheCacheableQuirkEnabled;
  }
  
  @CalledByNative
  public boolean getContentCacheEnableLocked()
  {
    return this.mContentCacheEnable;
  }
  
  public boolean getCustomDiskCacheEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mCustomDiskCacheEnabled;
      return bool;
    }
  }
  
  public int getDataFilterForRequestInfo()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = this.mDataFilterForRequestInfo;
      return i;
    }
  }
  
  public boolean getDayOrNight()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mDayOrNight;
      return bool;
    }
  }
  
  public boolean getDefaultOverScrollState()
  {
    return true;
  }
  
  @CalledByNative
  public boolean getFakeLoginEnabledLocked()
  {
    return this.mFakeLoginEnabled;
  }
  
  @CalledByNative
  public boolean getFeedsReadingEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mFeedsReadingEnabled;
      return bool;
    }
  }
  
  @CalledByNative
  public boolean getForcePinchScale()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mForcePinchScale;
      return bool;
    }
  }
  
  public boolean getForcePinchScaleEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mForcePinchScaleEnabled;
      return bool;
    }
  }
  
  @CalledByNative
  public String getForcePinchScaleHost()
  {
    synchronized (this.mAwSettingsLock)
    {
      String str = this.mForcePinchScaleHost;
      return str;
    }
  }
  
  @CalledByNative
  public boolean getFramePerformanceRecordEnable()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mFramePerformanceRecordEnable;
      return bool;
    }
  }
  
  public boolean getGifControl()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mGifControl;
      return bool;
    }
  }
  
  @CalledByNative
  public boolean getHistoryScreenshotEnableLocked()
  {
    return this.mHistoryScreenshotEnable;
  }
  
  public boolean getImageScanEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mImageScanEnable;
      return bool;
    }
  }
  
  public boolean getImgBrowser()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mImgBrowser;
      return bool;
    }
  }
  
  @CalledByNative
  public boolean getJSPerformanceRecordEnable()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mJSPerformanceRecordEnable;
      return bool;
    }
  }
  
  public boolean getJavaScriptOpenWindowsBlockedNotifyEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mJavaScriptOpenWindowsBlockedNotifyEnabled;
      return bool;
    }
  }
  
  @CalledByNative
  public boolean getOnContextMenuEnable()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mOnContextMenuEnable;
      return bool;
    }
  }
  
  public boolean getOverScrollState()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mWebViewOverScrollEnabled;
      return bool;
    }
  }
  
  public int getPicModel()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = this.mPicModel;
      return i;
    }
  }
  
  public boolean getPrereadingEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mPrereadingEnabled;
      return bool;
    }
  }
  
  @CalledByNative
  public boolean getPrereadingEnabledLocked()
  {
    return this.mPrereadingEnabled;
  }
  
  public boolean getShouldBubbleColorModeChanged()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mShouldBubbleColorModeChanged.booleanValue();
      return bool;
    }
  }
  
  @CalledByNative
  public boolean getShowTtsBarEnable()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mShowTtsBarEnable;
      return bool;
    }
  }
  
  public int getSplicedNativeAdHeight()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = this.mSplicedNativeAdHeight;
      return i;
    }
  }
  
  public int getTbsARShareType()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = this.mARShareType;
      return i;
    }
  }
  
  protected boolean getTextAutosizingEnabledLocked()
  {
    if ((!this.mLayoutAlgorithmHadSet) && (100 == this.mTextSizePercent)) {
      return true;
    }
    return (this.mLayoutAlgorithm != 0) && (this.mLayoutAlgorithm != 1) && (this.mLayoutAlgorithm != 2);
  }
  
  public boolean getUseSurfaceView()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getUseSurfaceViewLocked();
      return bool;
    }
  }
  
  public boolean getVideoOverlayForEmbeddedVideoEnabled()
  {
    return false;
  }
  
  public boolean getVideoPlaybackRequiresUserGesture()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mVideoPlaybackRequiresUserGesture;
      return bool;
    }
  }
  
  public boolean getWebTranslation()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mWebTranslationEnabled;
      return bool;
    }
  }
  
  @CalledByNative
  public boolean getWebTranslationLocked()
  {
    return getWebTranslation();
  }
  
  public void setARModeEnable(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mARModeEnable != paramBoolean)
      {
        this.mARModeEnable = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setAudioAutoPlayNotify(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mAudioAutoPlayNotify != paramBoolean)
      {
        this.mAudioAutoPlayNotify = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setAutoDetectToOpenFitScreenEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.autoDetectToOpenFitScreenEnabled = paramBoolean;
      return;
    }
  }
  
  public void setBFBeforeUnloadEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mBFBeforeUnloadEnabled != paramBoolean)
      {
        this.mBFBeforeUnloadEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setContentCacheCacheableQuirkEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mContentCacheCacheableQuirkEnabled != paramBoolean)
      {
        this.mContentCacheCacheableQuirkEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setContentCacheEnable(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mContentCacheEnable != paramBoolean)
      {
        this.mContentCacheEnable = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setCustomDiskCacheEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mCustomDiskCacheEnabled != paramBoolean)
      {
        this.mCustomDiskCacheEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setDataFilterForRequestInfo(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mDataFilterForRequestInfo != paramInt) {
        this.mDataFilterForRequestInfo = paramInt;
      }
      return;
    }
  }
  
  public void setDayOrNight(boolean paramBoolean)
  {
    for (;;)
    {
      synchronized (this.mAwSettingsLock)
      {
        if (this.mDayOrNight != paramBoolean)
        {
          this.mDayOrNight = paramBoolean;
          if (this.mNativeAwSettings != 0L)
          {
            long l = this.mNativeAwSettings;
            if (paramBoolean) {
              break label98;
            }
            paramBoolean = true;
            nativeUpdateBackgroundAtNightLocked(l, paramBoolean);
          }
          if (this.mDayOrNightModeChangeListener != null) {
            this.mDayOrNightModeChangeListener.onDayOrNightModeChangeListener(this.mDayOrNight);
          }
        }
        this.mEventHandler.updateWebkitPreferencesLocked();
        this.mShouldBubbleColorModeChanged = Boolean.valueOf(true);
        return;
      }
      label98:
      paramBoolean = false;
    }
  }
  
  public void setDayOrNightModeChangeListener(DayOrNightModeChangeListener paramDayOrNightModeChangeListener)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mDayOrNightModeChangeListener = paramDayOrNightModeChangeListener;
      return;
    }
  }
  
  public void setEyeShieldMode(boolean paramBoolean, int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mEyeShieldModeEnabled = paramBoolean;
      this.mEyeShieldBaseColor = paramInt;
      this.mEventHandler.updateWebkitPreferencesLocked();
      return;
    }
  }
  
  public void setFakeLoginEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mFakeLoginEnabled != paramBoolean)
      {
        this.mFakeLoginEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setFeedsReadingEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mFeedsReadingEnabled != paramBoolean)
      {
        this.mFeedsReadingEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setFirstScreenDetect(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mFirstScreenDetect != paramBoolean)
      {
        this.mFirstScreenDetect = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setFirstScreenDrawFullScreen(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mFirstScreenDrawFullScreen != paramBoolean)
      {
        this.mFirstScreenDrawFullScreen = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setFirstScreenDrawNotFullScreen(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mFirstScreenDrawNotFullScreen != paramBoolean)
      {
        this.mFirstScreenDrawNotFullScreen = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setFitScreenEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mFitScreenEnabled = paramBoolean;
      return;
    }
  }
  
  public void setForbitInputPassword(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mForbitInputPassword != paramBoolean)
      {
        this.mForbitInputPassword = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setForceEnableZoom(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mforceEnableZoom != paramBoolean)
      {
        this.mforceEnableZoom = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setForcePinchScale(boolean paramBoolean, String paramString)
  {
    synchronized (this.mAwSettingsLock)
    {
      if ((this.mForcePinchScale != paramBoolean) || ((!TextUtils.isEmpty(paramString)) && (!paramString.equalsIgnoreCase(this.mForcePinchScaleHost))))
      {
        this.mForcePinchScale = paramBoolean;
        this.mForcePinchScaleHost = paramString;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setForcePinchScaleEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mForcePinchScaleEnabled != paramBoolean) {
        this.mForcePinchScaleEnabled = paramBoolean;
      }
      return;
    }
  }
  
  public void setFrameLimitCount(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mFrameLimitCount != paramInt)
      {
        this.mFrameLimitCount = paramInt;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setFramePerformanceRecordEnable(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mFramePerformanceRecordEnable != paramBoolean)
      {
        this.mFramePerformanceRecordEnable = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setGifControl(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mGifControl != paramBoolean)
      {
        this.mGifControl = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setHistoryScreenshotEnable(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mHistoryScreenshotEnable != paramBoolean)
      {
        this.mHistoryScreenshotEnable = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setImageScanEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mImageScanEnable != paramBoolean)
      {
        this.mImageScanEnable = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setImgBrowser(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mImgBrowser != paramBoolean)
      {
        this.mImgBrowser = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setJSPerformanceRecordEnable(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mFramePerformanceRecordEnable != paramBoolean)
      {
        this.mJSPerformanceRecordEnable = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setJavaScriptOpenWindowsBlockedNotifyEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mJavaScriptOpenWindowsBlockedNotifyEnabled != paramBoolean)
      {
        this.mJavaScriptOpenWindowsBlockedNotifyEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setLayoutAlgorithm(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mLayoutAlgorithm != paramInt)
      {
        this.mLayoutAlgorithm = paramInt;
        this.mLayoutAlgorithmHadSet = true;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setLoadsImagesAutomatically(boolean paramBoolean)
  {
    super.setLoadsImagesAutomatically(paramBoolean);
    if (this.mMiniQBEnabled)
    {
      if (paramBoolean)
      {
        setPicModel(this.PicModel_NORMAL);
        return;
      }
      setPicModel(this.PicModel_NoPic);
    }
  }
  
  public void setMSEEnabled(boolean paramBoolean) {}
  
  public void setMiniQBEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mMiniQBEnabled != paramBoolean) {
        this.mMiniQBEnabled = paramBoolean;
      }
      return;
    }
  }
  
  public void setOnContextMenuEnable(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mOnContextMenuEnable != paramBoolean)
      {
        this.mOnContextMenuEnable = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setOverScrollState(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (paramBoolean != this.mWebViewOverScrollEnabled) {
        this.mWebViewOverScrollEnabled = paramBoolean;
      }
      return;
    }
  }
  
  public void setPicModel(int paramInt)
  {
    if (paramInt >= this.PicModel_NORMAL)
    {
      if (this.PicModel_NetNoPic < paramInt) {
        return;
      }
      synchronized (this.mAwSettingsLock)
      {
        if (this.mPicModel != paramInt)
        {
          this.mPicModel = paramInt;
          this.mEventHandler.updateWebkitPreferencesLocked();
          if (this.mPicModelChangeListener != null) {
            this.mPicModelChangeListener.onPicModelChangeListener(this.mPicModel);
          }
        }
        return;
      }
    }
  }
  
  public void setPicModelChangeListener(PicModelChangeListener paramPicModelChangeListener)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mPicModelChangeListener = paramPicModelChangeListener;
      return;
    }
  }
  
  public void setPrereadingEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mPrereadingEnabled != paramBoolean)
      {
        this.mPrereadingEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setQBorTMS(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mQBorTMS = paramBoolean;
      return;
    }
  }
  
  public void setRedirectCountLimit(int paramInt)
  {
    this.mRedirectCountLimit = paramInt;
  }
  
  public void setRemoveAds(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mRemoveAds != paramBoolean)
      {
        this.mRemoveAds = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setShouldBubbleColorModeChanged(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mShouldBubbleColorModeChanged.booleanValue() != paramBoolean) {
        this.mShouldBubbleColorModeChanged = Boolean.valueOf(paramBoolean);
      }
      return;
    }
  }
  
  public void setShouldTrackVisitedLinks(boolean paramBoolean)
  {
    mShouldTrackVisitedLinks = paramBoolean;
  }
  
  public void setShowTtsBarEnable(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mShowTtsBarEnable != paramBoolean)
      {
        this.mShowTtsBarEnable = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setSplicedNativeAdHeight(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mSplicedNativeAdHeight != paramInt)
      {
        this.mSplicedNativeAdHeight = paramInt;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setTbsARShareType(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mARShareType != paramInt)
      {
        this.mARShareType = paramInt;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setUseSurfaceView(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mUseSurfaceView != paramBoolean)
      {
        this.mUseSurfaceView = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setUserAgentString(String paramString, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      setUserAgentString(paramString);
      return;
    }
    synchronized (this.mAwSettingsLock)
    {
      String str = this.mUserAgent;
      if ((paramString != null) && (paramString.length() != 0)) {
        this.mUserAgent = paramString;
      } else {
        this.mUserAgent = AwSettings.LazyDefaultUserAgent.sInstance;
      }
      return;
    }
  }
  
  public void setVideoOverlayForEmbeddedVideoEnabled(boolean paramBoolean) {}
  
  public void setVideoPlaybackRequiresUserGesture(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mVideoPlaybackRequiresUserGesture != paramBoolean)
      {
        this.mVideoPlaybackRequiresUserGesture = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setWebTranslationEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (paramBoolean != this.mWebTranslationEnabled)
      {
        this.mWebTranslationEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setWebViewIdentity(String paramString)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mWebViewIdentity = paramString;
      return;
    }
  }
  
  public void setWrapLineLayoutEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mWrapLineLayoutEnabled = paramBoolean;
      return;
    }
  }
  
  public void setXCXReferer(String paramString)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (((this.mXCXReferer != null) && (!this.mXCXReferer.equals(paramString))) || ((this.mXCXReferer == null) && (paramString != null)))
      {
        this.mXCXReferer = paramString;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void updateWebkitPreferences()
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mEventHandler.updateWebkitPreferencesLocked();
      return;
    }
  }
  
  public static abstract interface DayOrNightModeChangeListener
  {
    public abstract void onDayOrNightModeChangeListener(boolean paramBoolean);
  }
  
  public static abstract interface PicModelChangeListener
  {
    public abstract void onPicModelChangeListener(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\TencentAwSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */