package org.chromium.android_webview;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.Settings.System;
import android.support.annotation.IntDef;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.chromium.base.BuildInfo;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.WebContents;

@JNINamespace("android_webview")
public class AwSettings
{
  public static final int LAYOUT_ALGORITHM_NARROW_COLUMNS = 2;
  public static final int LAYOUT_ALGORITHM_NORMAL = 0;
  public static final int LAYOUT_ALGORITHM_SINGLE_COLUMN = 1;
  public static final int LAYOUT_ALGORITHM_TEXT_AUTOSIZING = 3;
  private static final String LOGTAG = "AwSettings";
  private static final int MAXIMUM_FONT_SIZE = 72;
  private static final int MINIMUM_FONT_SIZE = 1;
  public static final int PLUGIN_STATE_OFF = 2;
  public static final int PLUGIN_STATE_ON = 0;
  public static final int PLUGIN_STATE_ON_DEMAND = 1;
  public static final String TAG = "AwSettings";
  private static final boolean TRACE = false;
  private static boolean sAppCachePathIsSet;
  private static final Object sGlobalContentSettingsLock = new Object();
  protected boolean mAcceptThirdPartyCookies;
  private boolean mAllowContentUrlAccess;
  private final boolean mAllowEmptyDocumentPersistence;
  private boolean mAllowFileAccessFromFileURLs;
  private boolean mAllowFileUrlAccess;
  private final boolean mAllowGeolocationOnInsecureOrigins;
  private boolean mAllowUniversalAccessFromFileURLs;
  private boolean mAppCacheEnabled;
  private boolean mAutoCompleteEnabled;
  protected final Object mAwSettingsLock = new Object();
  private boolean mBlockNetworkLoads;
  private boolean mBuiltInZoomControls;
  private boolean mCSSHexAlphaColorEnabled;
  private int mCacheMode;
  private String mCursiveFontFamily = "cursive";
  private double mDIPScale = 1.0D;
  private boolean mDatabaseEnabled;
  private int mDefaultFixedFontSize = 13;
  private int mDefaultFontSize = 16;
  private String mDefaultTextEncoding = "UTF-8";
  private String mDefaultVideoPosterURL;
  private int mDisabledMenuItems;
  private boolean mDisplayZoomControls;
  private final boolean mDoNotUpdateSelectionOnMutatingSelectionRange;
  private boolean mDomStorageEnabled;
  private boolean mEnableSupportedHardwareAcceleratedFeatures;
  protected final EventHandler mEventHandler;
  private String mFantasyFontFamily = "fantasy";
  private String mFixedFontFamily = "monospace";
  private boolean mForceZeroLayoutHeight;
  private boolean mFullscreenSupported;
  private boolean mGeolocationEnabled;
  private final boolean mHasInternetPermission;
  private boolean mImagesEnabled = true;
  private float mInitialPageScalePercent;
  private boolean mJavaScriptCanOpenWindowsAutomatically;
  private boolean mJavaScriptEnabled;
  protected int mLayoutAlgorithm = 2;
  private boolean mLoadWithOverviewMode;
  private boolean mLoadsImagesAutomatically = true;
  private boolean mMediaPlaybackRequiresUserGesture = true;
  private int mMinimumFontSize = 8;
  private int mMinimumLogicalFontSize = 8;
  protected int mMixedContentMode;
  public long mNativeAwSettings;
  private boolean mOffscreenPreRaster;
  private final boolean mPasswordEchoEnabled;
  private int mPluginState = 2;
  private Boolean mSafeBrowsingEnabled;
  private String mSansSerifFontFamily = "sans-serif";
  private boolean mScrollTopLeftInteropEnabled;
  private String mSerifFontFamily = "serif";
  private boolean mShouldFocusFirstNode;
  private boolean mSpatialNavigationEnabled;
  private String mStandardFontFamily = "sans-serif";
  private final boolean mSupportLegacyQuirks;
  private boolean mSupportMultipleWindows;
  private boolean mSupportZoom;
  protected int mTextSizePercent = 100;
  private boolean mUseWideViewport;
  protected String mUserAgent;
  private boolean mZeroLayoutHeightDisablesViewportQuirk;
  private ZoomSupportChangeListener mZoomChangeListener;
  
  public AwSettings(Context paramContext, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
  {
    boolean bool2 = false;
    this.mMixedContentMode = 0;
    this.mCSSHexAlphaColorEnabled = false;
    this.mScrollTopLeftInteropEnabled = false;
    this.mDisabledMenuItems = 0;
    this.mAllowContentUrlAccess = true;
    this.mAllowFileUrlAccess = true;
    this.mCacheMode = -1;
    this.mShouldFocusFirstNode = true;
    this.mGeolocationEnabled = true;
    boolean bool1;
    if (Build.VERSION.SDK_INT < 26) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.mAutoCompleteEnabled = bool1;
    this.mSupportZoom = true;
    this.mDisplayZoomControls = true;
    if (paramContext.checkPermission("android.permission.INTERNET", Process.myPid(), Process.myUid()) == 0) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    for (;;)
    {
      synchronized (this.mAwSettingsLock)
      {
        this.mHasInternetPermission = bool1;
        if (!bool1)
        {
          bool1 = true;
          this.mBlockNetworkLoads = bool1;
          this.mEventHandler = new EventHandler();
          if (paramBoolean1)
          {
            this.mAllowUniversalAccessFromFileURLs = true;
            this.mAllowFileAccessFromFileURLs = true;
          }
          this.mUserAgent = LazyDefaultUserAgent.sInstance;
          if (paramContext.getPackageManager().hasSystemFeature("android.hardware.touchscreen")) {
            break label402;
          }
          paramBoolean1 = true;
          this.mSpatialNavigationEnabled = paramBoolean1;
          paramBoolean1 = bool2;
          if (Settings.System.getInt(paramContext.getContentResolver(), "show_password", 1) == 1) {
            paramBoolean1 = true;
          }
          this.mPasswordEchoEnabled = paramBoolean1;
          if (!getTextAutosizingEnabledLocked()) {
            this.mTextSizePercent = ((int)(this.mTextSizePercent * paramContext.getResources().getConfiguration().fontScale));
          }
          this.mSupportLegacyQuirks = paramBoolean2;
          this.mAllowEmptyDocumentPersistence = paramBoolean3;
          this.mAllowGeolocationOnInsecureOrigins = paramBoolean4;
          this.mDoNotUpdateSelectionOnMutatingSelectionRange = paramBoolean5;
          return;
        }
      }
      bool1 = false;
      continue;
      label402:
      paramBoolean1 = false;
    }
  }
  
  private int clipFontSize(int paramInt)
  {
    if (paramInt < 1) {
      return 1;
    }
    if (paramInt > 72) {
      return 72;
    }
    return paramInt;
  }
  
  @CalledByNative
  private boolean getAllowEmptyDocumentPersistenceLocked()
  {
    return this.mAllowEmptyDocumentPersistence;
  }
  
  @CalledByNative
  private boolean getAllowFileAccessFromFileURLsLocked()
  {
    return this.mAllowFileAccessFromFileURLs;
  }
  
  @CalledByNative
  private boolean getAllowGeolocationOnInsecureOrigins()
  {
    return this.mAllowGeolocationOnInsecureOrigins;
  }
  
  @CalledByNative
  private boolean getAllowRunningInsecureContentLocked()
  {
    return this.mMixedContentMode == 0;
  }
  
  @CalledByNative
  private static boolean getAllowSniffingFileUrls()
  {
    return BuildInfo.targetsAtLeastP() ^ true;
  }
  
  @CalledByNative
  private boolean getAllowUniversalAccessFromFileURLsLocked()
  {
    return this.mAllowUniversalAccessFromFileURLs;
  }
  
  @CalledByNative
  private boolean getAppCacheEnabledLocked()
  {
    if (!this.mAppCacheEnabled) {
      return false;
    }
    synchronized (sGlobalContentSettingsLock)
    {
      boolean bool = sAppCachePathIsSet;
      return bool;
    }
  }
  
  @CalledByNative
  private boolean getCSSHexAlphaColorEnabledLocked()
  {
    return this.mCSSHexAlphaColorEnabled;
  }
  
  @CalledByNative
  private String getCursiveFontFamilyLocked()
  {
    return this.mCursiveFontFamily;
  }
  
  @CalledByNative
  private double getDIPScaleLocked()
  {
    return this.mDIPScale;
  }
  
  @CalledByNative
  private boolean getDatabaseEnabledLocked()
  {
    return this.mDatabaseEnabled;
  }
  
  @CalledByNative
  private int getDefaultFixedFontSizeLocked()
  {
    return this.mDefaultFixedFontSize;
  }
  
  @CalledByNative
  private int getDefaultFontSizeLocked()
  {
    return this.mDefaultFontSize;
  }
  
  @CalledByNative
  private String getDefaultTextEncodingLocked()
  {
    return this.mDefaultTextEncoding;
  }
  
  public static String getDefaultUserAgent()
  {
    return LazyDefaultUserAgent.sInstance;
  }
  
  @CalledByNative
  private String getDefaultVideoPosterURLLocked()
  {
    return this.mDefaultVideoPosterURL;
  }
  
  @CalledByNative
  private boolean getDoNotUpdateSelectionOnMutatingSelectionRange()
  {
    return this.mDoNotUpdateSelectionOnMutatingSelectionRange;
  }
  
  @CalledByNative
  private boolean getDomStorageEnabledLocked()
  {
    return this.mDomStorageEnabled;
  }
  
  @CalledByNative
  private boolean getEnableSupportedHardwareAcceleratedFeaturesLocked()
  {
    return this.mEnableSupportedHardwareAcceleratedFeatures;
  }
  
  @CalledByNative
  private String getFantasyFontFamilyLocked()
  {
    return this.mFantasyFontFamily;
  }
  
  @CalledByNative
  private String getFixedFontFamilyLocked()
  {
    return this.mFixedFontFamily;
  }
  
  @CalledByNative
  private boolean getForceZeroLayoutHeightLocked()
  {
    return this.mForceZeroLayoutHeight;
  }
  
  @CalledByNative
  private boolean getFullscreenSupportedLocked()
  {
    return this.mFullscreenSupported;
  }
  
  @CalledByNative
  private boolean getImagesEnabledLocked()
  {
    return this.mImagesEnabled;
  }
  
  @CalledByNative
  private float getInitialPageScalePercentLocked()
  {
    return this.mInitialPageScalePercent;
  }
  
  @CalledByNative
  private boolean getJavaScriptCanOpenWindowsAutomaticallyLocked()
  {
    return this.mJavaScriptCanOpenWindowsAutomatically;
  }
  
  @CalledByNative
  private boolean getJavaScriptEnabledLocked()
  {
    return this.mJavaScriptEnabled;
  }
  
  @CalledByNative
  private boolean getLoadWithOverviewModeLocked()
  {
    return this.mLoadWithOverviewMode;
  }
  
  @CalledByNative
  private boolean getLoadsImagesAutomaticallyLocked()
  {
    return this.mLoadsImagesAutomatically;
  }
  
  @CalledByNative
  private boolean getMediaPlaybackRequiresUserGestureLocked()
  {
    return this.mMediaPlaybackRequiresUserGesture;
  }
  
  @CalledByNative
  private int getMinimumFontSizeLocked()
  {
    return this.mMinimumFontSize;
  }
  
  @CalledByNative
  private int getMinimumLogicalFontSizeLocked()
  {
    return this.mMinimumLogicalFontSize;
  }
  
  @CalledByNative
  private boolean getOffscreenPreRasterLocked()
  {
    return this.mOffscreenPreRaster;
  }
  
  @CalledByNative
  private boolean getPasswordEchoEnabledLocked()
  {
    return this.mPasswordEchoEnabled;
  }
  
  @CalledByNative
  private boolean getPluginsDisabledLocked()
  {
    return this.mPluginState == 2;
  }
  
  @CalledByNative
  private boolean getRecordFullDocument()
  {
    return AwContentsStatics.a();
  }
  
  @CalledByNative
  private String getSansSerifFontFamilyLocked()
  {
    return this.mSansSerifFontFamily;
  }
  
  @CalledByNative
  private boolean getSaveFormDataLocked()
  {
    return this.mAutoCompleteEnabled;
  }
  
  @CalledByNative
  private boolean getScrollTopLeftInteropEnabledLocked()
  {
    return this.mScrollTopLeftInteropEnabled;
  }
  
  @CalledByNative
  private String getSerifFontFamilyLocked()
  {
    return this.mSerifFontFamily;
  }
  
  @CalledByNative
  private boolean getSpatialNavigationLocked()
  {
    return this.mSpatialNavigationEnabled;
  }
  
  @CalledByNative
  private String getStandardFontFamilyLocked()
  {
    return this.mStandardFontFamily;
  }
  
  @CalledByNative
  private boolean getSupportLegacyQuirksLocked()
  {
    return this.mSupportLegacyQuirks;
  }
  
  @CalledByNative
  private boolean getSupportMultipleWindowsLocked()
  {
    return this.mSupportMultipleWindows;
  }
  
  @CalledByNative
  private int getTextSizePercentLocked()
  {
    return this.mTextSizePercent;
  }
  
  @CalledByNative
  private boolean getUseStricMixedContentCheckingLocked()
  {
    return this.mMixedContentMode == 1;
  }
  
  @CalledByNative
  private boolean getUseWideViewportLocked()
  {
    return this.mUseWideViewport;
  }
  
  @CalledByNative
  private String getUserAgentLocked()
  {
    return this.mUserAgent;
  }
  
  @CalledByNative
  private boolean getZeroLayoutHeightDisablesViewportQuirkLocked()
  {
    return this.mZeroLayoutHeightDisablesViewportQuirk;
  }
  
  @CalledByNative
  private void nativeAwSettingsGone(long paramLong)
  {
    this.mNativeAwSettings = 0L;
  }
  
  private native void nativeDestroy(long paramLong);
  
  private static native String nativeGetDefaultUserAgent();
  
  private native long nativeInit(WebContents paramWebContents);
  
  private native void nativePopulateWebPreferencesLocked(long paramLong1, long paramLong2);
  
  private native void nativeResetScrollAndScaleState(long paramLong);
  
  private native void nativeUpdateEverythingLocked(long paramLong);
  
  private native void nativeUpdateFormDataPreferencesLocked(long paramLong);
  
  private native void nativeUpdateInitialPageScaleLocked(long paramLong);
  
  private native void nativeUpdateOffscreenPreRasterLocked(long paramLong);
  
  private native void nativeUpdateRendererPreferencesLocked(long paramLong);
  
  private native void nativeUpdateUserAgentLocked(long paramLong);
  
  private native void nativeUpdateWebkitPreferencesLocked(long paramLong);
  
  private void onGestureZoomSupportChanged(final boolean paramBoolean1, final boolean paramBoolean2)
  {
    this.mEventHandler.a(new Runnable()
    {
      public void run()
      {
        synchronized (AwSettings.this.mAwSettingsLock)
        {
          if (AwSettings.this.mZoomChangeListener != null) {
            AwSettings.this.mZoomChangeListener.onGestureZoomSupportChanged(paramBoolean1, paramBoolean2);
          }
          return;
        }
      }
    });
  }
  
  @CalledByNative
  private void populateWebPreferences(long paramLong)
  {
    synchronized (this.mAwSettingsLock)
    {
      nativePopulateWebPreferencesLocked(this.mNativeAwSettings, paramLong);
      return;
      Object localObject2;
      throw ((Throwable)localObject2);
    }
  }
  
  @CalledByNative
  private boolean supportsDoubleTapZoomLocked()
  {
    return (this.mSupportZoom) && (this.mBuiltInZoomControls) && (this.mUseWideViewport);
  }
  
  private boolean supportsMultiTouchZoomLocked()
  {
    return (this.mSupportZoom) && (this.mBuiltInZoomControls);
  }
  
  @CalledByNative
  private void updateEverything()
  {
    synchronized (this.mAwSettingsLock)
    {
      updateEverythingLocked();
      return;
    }
  }
  
  private void updateEverythingLocked()
  {
    nativeUpdateEverythingLocked(this.mNativeAwSettings);
    onGestureZoomSupportChanged(supportsDoubleTapZoomLocked(), supportsMultiTouchZoomLocked());
  }
  
  public boolean getAcceptThirdPartyCookies()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mAcceptThirdPartyCookies;
      return bool;
    }
  }
  
  public boolean getAllowContentAccess()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mAllowContentUrlAccess;
      return bool;
    }
  }
  
  public boolean getAllowFileAccess()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mAllowFileUrlAccess;
      return bool;
    }
  }
  
  public boolean getAllowFileAccessFromFileURLs()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getAllowFileAccessFromFileURLsLocked();
      return bool;
    }
  }
  
  public boolean getAllowUniversalAccessFromFileURLs()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getAllowUniversalAccessFromFileURLsLocked();
      return bool;
    }
  }
  
  public boolean getBlockNetworkLoads()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mBlockNetworkLoads;
      return bool;
    }
  }
  
  public boolean getBuiltInZoomControls()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mBuiltInZoomControls;
      return bool;
    }
  }
  
  public int getCacheMode()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = this.mCacheMode;
      return i;
    }
  }
  
  public String getCursiveFontFamily()
  {
    synchronized (this.mAwSettingsLock)
    {
      String str = getCursiveFontFamilyLocked();
      return str;
    }
  }
  
  public boolean getDatabaseEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mDatabaseEnabled;
      return bool;
    }
  }
  
  public int getDefaultFixedFontSize()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = getDefaultFixedFontSizeLocked();
      return i;
    }
  }
  
  public int getDefaultFontSize()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = getDefaultFontSizeLocked();
      return i;
    }
  }
  
  public String getDefaultTextEncodingName()
  {
    synchronized (this.mAwSettingsLock)
    {
      String str = getDefaultTextEncodingLocked();
      return str;
    }
  }
  
  public String getDefaultVideoPosterURL()
  {
    synchronized (this.mAwSettingsLock)
    {
      String str = getDefaultVideoPosterURLLocked();
      return str;
    }
  }
  
  public int getDisabledActionModeMenuItems()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = this.mDisabledMenuItems;
      return i;
    }
  }
  
  public boolean getDisplayZoomControls()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mDisplayZoomControls;
      return bool;
    }
  }
  
  public boolean getDomStorageEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mDomStorageEnabled;
      return bool;
    }
  }
  
  public String getFantasyFontFamily()
  {
    synchronized (this.mAwSettingsLock)
    {
      String str = getFantasyFontFamilyLocked();
      return str;
    }
  }
  
  public String getFixedFontFamily()
  {
    synchronized (this.mAwSettingsLock)
    {
      String str = getFixedFontFamilyLocked();
      return str;
    }
  }
  
  public boolean getForceZeroLayoutHeight()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getForceZeroLayoutHeightLocked();
      return bool;
    }
  }
  
  boolean getGeolocationEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mGeolocationEnabled;
      return bool;
    }
  }
  
  public boolean getImagesEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mImagesEnabled;
      return bool;
    }
  }
  
  public boolean getJavaScriptCanOpenWindowsAutomatically()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getJavaScriptCanOpenWindowsAutomaticallyLocked();
      return bool;
    }
  }
  
  public boolean getJavaScriptEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mJavaScriptEnabled;
      return bool;
    }
  }
  
  public int getLayoutAlgorithm()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = this.mLayoutAlgorithm;
      return i;
    }
  }
  
  public boolean getLoadWithOverviewMode()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getLoadWithOverviewModeLocked();
      return bool;
    }
  }
  
  public boolean getLoadsImagesAutomatically()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getLoadsImagesAutomaticallyLocked();
      return bool;
    }
  }
  
  public boolean getMediaPlaybackRequiresUserGesture()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getMediaPlaybackRequiresUserGestureLocked();
      return bool;
    }
  }
  
  public int getMinimumFontSize()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = getMinimumFontSizeLocked();
      return i;
    }
  }
  
  public int getMinimumLogicalFontSize()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = getMinimumLogicalFontSizeLocked();
      return i;
    }
  }
  
  public int getMixedContentMode()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = this.mMixedContentMode;
      return i;
    }
  }
  
  public boolean getOffscreenPreRaster()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getOffscreenPreRasterLocked();
      return bool;
    }
  }
  
  public int getPluginState()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = this.mPluginState;
      return i;
    }
  }
  
  public boolean getPluginsEnabled()
  {
    for (;;)
    {
      synchronized (this.mAwSettingsLock)
      {
        if (this.mPluginState == 0)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public boolean getSafeBrowsingEnabled()
  {
    synchronized (this.mAwSettingsLock)
    {
      Boolean localBoolean = AwSafeBrowsingConfigHelper.getSafeBrowsingUserOptIn();
      if ((localBoolean != null) && (!localBoolean.booleanValue())) {
        return false;
      }
      if (this.mSafeBrowsingEnabled == null)
      {
        bool = AwContentsStatics.getSafeBrowsingEnabledByManifest();
        return bool;
      }
      boolean bool = this.mSafeBrowsingEnabled.booleanValue();
      return bool;
    }
  }
  
  public String getSansSerifFontFamily()
  {
    synchronized (this.mAwSettingsLock)
    {
      String str = getSansSerifFontFamilyLocked();
      return str;
    }
  }
  
  public boolean getSaveFormData()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getSaveFormDataLocked();
      return bool;
    }
  }
  
  public String getSerifFontFamily()
  {
    synchronized (this.mAwSettingsLock)
    {
      String str = getSerifFontFamilyLocked();
      return str;
    }
  }
  
  public String getStandardFontFamily()
  {
    synchronized (this.mAwSettingsLock)
    {
      String str = getStandardFontFamilyLocked();
      return str;
    }
  }
  
  @CalledByNative
  protected boolean getTextAutosizingEnabledLocked()
  {
    return this.mLayoutAlgorithm == 3;
  }
  
  public int getTextZoom()
  {
    synchronized (this.mAwSettingsLock)
    {
      int i = getTextSizePercentLocked();
      return i;
    }
  }
  
  public boolean getUseWideViewPort()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getUseWideViewportLocked();
      return bool;
    }
  }
  
  public String getUserAgentString()
  {
    synchronized (this.mAwSettingsLock)
    {
      String str = getUserAgentLocked();
      return str;
    }
  }
  
  public boolean getZeroLayoutHeightDisablesViewportQuirk()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = getZeroLayoutHeightDisablesViewportQuirkLocked();
      return bool;
    }
  }
  
  protected native void nativeUpdateBackgroundAtNightLocked(long paramLong, boolean paramBoolean);
  
  public void setAcceptThirdPartyCookies(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mAcceptThirdPartyCookies = paramBoolean;
      return;
    }
  }
  
  public void setAllowContentAccess(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mAllowContentUrlAccess = paramBoolean;
      return;
    }
  }
  
  public void setAllowFileAccess(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mAllowFileUrlAccess = paramBoolean;
      return;
    }
  }
  
  public void setAllowFileAccessFromFileURLs(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mAllowFileAccessFromFileURLs != paramBoolean)
      {
        this.mAllowFileAccessFromFileURLs = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setAllowUniversalAccessFromFileURLs(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mAllowUniversalAccessFromFileURLs != paramBoolean)
      {
        this.mAllowUniversalAccessFromFileURLs = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setAppCacheEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mAppCacheEnabled != paramBoolean)
      {
        this.mAppCacheEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setAppCachePath(String arg1)
  {
    for (;;)
    {
      synchronized (sGlobalContentSettingsLock)
      {
        boolean bool = sAppCachePathIsSet;
        i = 1;
        if ((!bool) && (??? != null) && (!???.isEmpty()))
        {
          sAppCachePathIsSet = true;
          if (i != 0) {
            synchronized (this.mAwSettingsLock)
            {
              this.mEventHandler.updateWebkitPreferencesLocked();
              return;
            }
          }
          return;
        }
      }
      int i = 0;
    }
  }
  
  public void setBlockNetworkLoads(boolean paramBoolean)
  {
    Object localObject1 = this.mAwSettingsLock;
    if (!paramBoolean) {}
    try
    {
      if (!this.mHasInternetPermission) {
        throw new SecurityException("Permission denied - application missing INTERNET permission");
      }
      this.mBlockNetworkLoads = paramBoolean;
      return;
    }
    finally {}
  }
  
  public void setBuiltInZoomControls(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mBuiltInZoomControls != paramBoolean)
      {
        this.mBuiltInZoomControls = paramBoolean;
        onGestureZoomSupportChanged(supportsDoubleTapZoomLocked(), supportsMultiTouchZoomLocked());
      }
      return;
    }
  }
  
  public void setCSSHexAlphaColorEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mCSSHexAlphaColorEnabled != paramBoolean)
      {
        this.mCSSHexAlphaColorEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setCacheMode(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mCacheMode = paramInt;
      return;
    }
  }
  
  public void setCursiveFontFamily(String paramString)
  {
    Object localObject = this.mAwSettingsLock;
    if (paramString != null) {}
    try
    {
      if (!this.mCursiveFontFamily.equals(paramString))
      {
        this.mCursiveFontFamily = paramString;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
    finally {}
  }
  
  void setDIPScale(double paramDouble)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mDIPScale = paramDouble;
      return;
    }
  }
  
  public void setDatabaseEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mDatabaseEnabled != paramBoolean)
      {
        this.mDatabaseEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setDefaultFixedFontSize(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      paramInt = clipFontSize(paramInt);
      if (this.mDefaultFixedFontSize != paramInt)
      {
        this.mDefaultFixedFontSize = paramInt;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setDefaultFontSize(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      paramInt = clipFontSize(paramInt);
      if (this.mDefaultFontSize != paramInt)
      {
        this.mDefaultFontSize = paramInt;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setDefaultTextEncodingName(String paramString)
  {
    Object localObject = this.mAwSettingsLock;
    if (paramString != null) {}
    try
    {
      if (!this.mDefaultTextEncoding.equals(paramString))
      {
        this.mDefaultTextEncoding = paramString;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
    finally {}
  }
  
  public void setDefaultVideoPosterURL(String paramString)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (((this.mDefaultVideoPosterURL != null) && (!this.mDefaultVideoPosterURL.equals(paramString))) || ((this.mDefaultVideoPosterURL == null) && (paramString != null)))
      {
        this.mDefaultVideoPosterURL = paramString;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setDisabledActionModeMenuItems(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mDisabledMenuItems = paramInt;
      return;
    }
  }
  
  public void setDisplayZoomControls(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mDisplayZoomControls = paramBoolean;
      return;
    }
  }
  
  public void setDomStorageEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mDomStorageEnabled != paramBoolean)
      {
        this.mDomStorageEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setEnableSupportedHardwareAcceleratedFeatures(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mEnableSupportedHardwareAcceleratedFeatures != paramBoolean)
      {
        this.mEnableSupportedHardwareAcceleratedFeatures = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setFantasyFontFamily(String paramString)
  {
    Object localObject = this.mAwSettingsLock;
    if (paramString != null) {}
    try
    {
      if (!this.mFantasyFontFamily.equals(paramString))
      {
        this.mFantasyFontFamily = paramString;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
    finally {}
  }
  
  public void setFixedFontFamily(String paramString)
  {
    Object localObject = this.mAwSettingsLock;
    if (paramString != null) {}
    try
    {
      if (!this.mFixedFontFamily.equals(paramString))
      {
        this.mFixedFontFamily = paramString;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
    finally {}
  }
  
  public void setForceZeroLayoutHeight(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mForceZeroLayoutHeight != paramBoolean)
      {
        this.mForceZeroLayoutHeight = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setFullscreenSupported(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mFullscreenSupported != paramBoolean)
      {
        this.mFullscreenSupported = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setGeolocationEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mGeolocationEnabled = paramBoolean;
      return;
    }
  }
  
  public void setImagesEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mImagesEnabled != paramBoolean)
      {
        this.mImagesEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setInitialPageScale(float paramFloat)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mInitialPageScalePercent != paramFloat)
      {
        this.mInitialPageScalePercent = paramFloat;
        this.mEventHandler.runOnUiThreadBlockingAndLocked(new Runnable()
        {
          public void run()
          {
            if (AwSettings.this.mNativeAwSettings != 0L)
            {
              AwSettings localAwSettings = AwSettings.this;
              localAwSettings.nativeUpdateInitialPageScaleLocked(localAwSettings.mNativeAwSettings);
            }
          }
        });
      }
      return;
    }
  }
  
  public void setJavaScriptCanOpenWindowsAutomatically(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mJavaScriptCanOpenWindowsAutomatically != paramBoolean)
      {
        this.mJavaScriptCanOpenWindowsAutomatically = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setJavaScriptEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mJavaScriptEnabled != paramBoolean)
      {
        this.mJavaScriptEnabled = paramBoolean;
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
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setLoadWithOverviewMode(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mLoadWithOverviewMode != paramBoolean)
      {
        this.mLoadWithOverviewMode = paramBoolean;
        this.mEventHandler.runOnUiThreadBlockingAndLocked(new Runnable()
        {
          public void run()
          {
            if (AwSettings.this.mNativeAwSettings != 0L)
            {
              AwSettings.this.updateWebkitPreferencesOnUiThreadLocked();
              AwSettings localAwSettings = AwSettings.this;
              localAwSettings.nativeResetScrollAndScaleState(localAwSettings.mNativeAwSettings);
            }
          }
        });
      }
      return;
    }
  }
  
  public void setLoadsImagesAutomatically(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mLoadsImagesAutomatically != paramBoolean)
      {
        this.mLoadsImagesAutomatically = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setMediaPlaybackRequiresUserGesture(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mMediaPlaybackRequiresUserGesture != paramBoolean)
      {
        this.mMediaPlaybackRequiresUserGesture = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setMinimumFontSize(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      paramInt = clipFontSize(paramInt);
      if (this.mMinimumFontSize != paramInt)
      {
        this.mMinimumFontSize = paramInt;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setMinimumLogicalFontSize(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      paramInt = clipFontSize(paramInt);
      if (this.mMinimumLogicalFontSize != paramInt)
      {
        this.mMinimumLogicalFontSize = paramInt;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setMixedContentMode(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mMixedContentMode != paramInt)
      {
        this.mMixedContentMode = paramInt;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setOffscreenPreRaster(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (paramBoolean != this.mOffscreenPreRaster)
      {
        this.mOffscreenPreRaster = paramBoolean;
        this.mEventHandler.runOnUiThreadBlockingAndLocked(new Runnable()
        {
          public void run()
          {
            if (AwSettings.this.mNativeAwSettings != 0L)
            {
              AwSettings localAwSettings = AwSettings.this;
              localAwSettings.nativeUpdateOffscreenPreRasterLocked(localAwSettings.mNativeAwSettings);
            }
          }
        });
      }
      return;
    }
  }
  
  public void setPluginState(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mPluginState != paramInt)
      {
        this.mPluginState = paramInt;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setPluginsEnabled(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 0;
    } else {
      i = 2;
    }
    setPluginState(i);
  }
  
  public void setSafeBrowsingEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mSafeBrowsingEnabled = Boolean.valueOf(paramBoolean);
      return;
    }
  }
  
  public void setSansSerifFontFamily(String paramString)
  {
    Object localObject = this.mAwSettingsLock;
    if (paramString != null) {}
    try
    {
      if (!this.mSansSerifFontFamily.equals(paramString))
      {
        this.mSansSerifFontFamily = paramString;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
    finally {}
  }
  
  public void setSaveFormData(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mAutoCompleteEnabled != paramBoolean)
      {
        this.mAutoCompleteEnabled = paramBoolean;
        this.mEventHandler.runOnUiThreadBlockingAndLocked(new Runnable()
        {
          public void run()
          {
            if (AwSettings.this.mNativeAwSettings != 0L)
            {
              AwSettings localAwSettings = AwSettings.this;
              localAwSettings.nativeUpdateFormDataPreferencesLocked(localAwSettings.mNativeAwSettings);
            }
          }
        });
      }
      return;
    }
  }
  
  public void setScrollTopLeftInteropEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mScrollTopLeftInteropEnabled != paramBoolean)
      {
        this.mScrollTopLeftInteropEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setSerifFontFamily(String paramString)
  {
    Object localObject = this.mAwSettingsLock;
    if (paramString != null) {}
    try
    {
      if (!this.mSerifFontFamily.equals(paramString))
      {
        this.mSerifFontFamily = paramString;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
    finally {}
  }
  
  public void setShouldFocusFirstNode(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mShouldFocusFirstNode = paramBoolean;
      return;
    }
  }
  
  public void setSpatialNavigationEnabled(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mSpatialNavigationEnabled != paramBoolean)
      {
        this.mSpatialNavigationEnabled = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setStandardFontFamily(String paramString)
  {
    Object localObject = this.mAwSettingsLock;
    if (paramString != null) {}
    try
    {
      if (!this.mStandardFontFamily.equals(paramString))
      {
        this.mStandardFontFamily = paramString;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
    finally {}
  }
  
  public void setSupportMultipleWindows(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mSupportMultipleWindows != paramBoolean)
      {
        this.mSupportMultipleWindows = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setSupportZoom(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mSupportZoom != paramBoolean)
      {
        this.mSupportZoom = paramBoolean;
        onGestureZoomSupportChanged(supportsDoubleTapZoomLocked(), supportsMultiTouchZoomLocked());
      }
      return;
    }
  }
  
  public void setTextZoom(int paramInt)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mTextSizePercent != paramInt)
      {
        this.mTextSizePercent = paramInt;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setUseWideViewPort(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mUseWideViewport != paramBoolean)
      {
        this.mUseWideViewport = paramBoolean;
        onGestureZoomSupportChanged(supportsDoubleTapZoomLocked(), supportsMultiTouchZoomLocked());
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  public void setUserAgent(int paramInt)
  {
    if (paramInt == 0) {
      setUserAgentString(null);
    }
  }
  
  public void setUserAgentString(String paramString)
  {
    synchronized (this.mAwSettingsLock)
    {
      String str = this.mUserAgent;
      if ((paramString != null) && (paramString.length() != 0)) {
        this.mUserAgent = paramString;
      } else {
        this.mUserAgent = LazyDefaultUserAgent.sInstance;
      }
      if (!str.equals(this.mUserAgent)) {
        this.mEventHandler.runOnUiThreadBlockingAndLocked(new Runnable()
        {
          public void run()
          {
            if (AwSettings.this.mNativeAwSettings != 0L)
            {
              AwSettings localAwSettings = AwSettings.this;
              localAwSettings.nativeUpdateUserAgentLocked(localAwSettings.mNativeAwSettings);
            }
          }
        });
      }
      return;
    }
  }
  
  void setWebContents(WebContents paramWebContents)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mNativeAwSettings != 0L) {
        nativeDestroy(this.mNativeAwSettings);
      }
      if (paramWebContents != null)
      {
        this.mEventHandler.a();
        this.mNativeAwSettings = nativeInit(paramWebContents);
        updateEverythingLocked();
      }
      return;
    }
  }
  
  public void setZeroLayoutHeightDisablesViewportQuirk(boolean paramBoolean)
  {
    synchronized (this.mAwSettingsLock)
    {
      if (this.mZeroLayoutHeightDisablesViewportQuirk != paramBoolean)
      {
        this.mZeroLayoutHeightDisablesViewportQuirk = paramBoolean;
        this.mEventHandler.updateWebkitPreferencesLocked();
      }
      return;
    }
  }
  
  void setZoomListener(ZoomSupportChangeListener paramZoomSupportChangeListener)
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mZoomChangeListener = paramZoomSupportChangeListener;
      return;
    }
  }
  
  boolean shouldDisplayZoomControls()
  {
    for (;;)
    {
      synchronized (this.mAwSettingsLock)
      {
        if ((supportsMultiTouchZoomLocked()) && (this.mDisplayZoomControls))
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public boolean shouldFocusFirstNode()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mShouldFocusFirstNode;
      return bool;
    }
  }
  
  public boolean supportMultipleWindows()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mSupportMultipleWindows;
      return bool;
    }
  }
  
  public boolean supportZoom()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = this.mSupportZoom;
      return bool;
    }
  }
  
  boolean supportsMultiTouchZoom()
  {
    synchronized (this.mAwSettingsLock)
    {
      boolean bool = supportsMultiTouchZoomLocked();
      return bool;
    }
  }
  
  @VisibleForTesting
  public void updateAcceptLanguages()
  {
    synchronized (this.mAwSettingsLock)
    {
      this.mEventHandler.runOnUiThreadBlockingAndLocked(new Runnable()
      {
        public void run()
        {
          if (AwSettings.this.mNativeAwSettings != 0L)
          {
            AwSettings localAwSettings = AwSettings.this;
            localAwSettings.nativeUpdateRendererPreferencesLocked(localAwSettings.mNativeAwSettings);
          }
        }
      });
      return;
    }
  }
  
  public void updateWebkitPreferencesOnUiThreadLocked()
  {
    ThreadUtils.assertOnUiThread();
    long l = this.mNativeAwSettings;
    if (l != 0L) {
      nativeUpdateWebkitPreferencesLocked(l);
    }
  }
  
  public class EventHandler
  {
    private Handler jdField_a_of_type_AndroidOsHandler;
    private boolean b;
    
    EventHandler() {}
    
    void a()
    {
      if (this.jdField_a_of_type_AndroidOsHandler != null) {
        return;
      }
      this.jdField_a_of_type_AndroidOsHandler = new Handler(ThreadUtils.getUiThreadLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (paramAnonymousMessage.what != 0) {
            return;
          }
          synchronized (AwSettings.this.mAwSettingsLock)
          {
            if (AwSettings.this.mNativeAwSettings != 0L) {
              ((Runnable)paramAnonymousMessage.obj).run();
            }
            AwSettings.EventHandler.a(AwSettings.EventHandler.this, false);
            AwSettings.this.mAwSettingsLock.notifyAll();
            return;
          }
        }
      };
    }
    
    void a(Runnable paramRunnable)
    {
      Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
      if (localHandler != null) {
        localHandler.post(paramRunnable);
      }
    }
    
    public void runOnUiThreadBlockingAndLocked(Runnable paramRunnable)
    {
      if ((!jdField_a_of_type_Boolean) && (!Thread.holdsLock(AwSettings.this.mAwSettingsLock))) {
        throw new AssertionError();
      }
      if (this.jdField_a_of_type_AndroidOsHandler == null) {
        return;
      }
      if (ThreadUtils.runningOnUiThread())
      {
        paramRunnable.run();
        return;
      }
      if ((!jdField_a_of_type_Boolean) && (this.b)) {
        throw new AssertionError();
      }
      this.b = true;
      this.jdField_a_of_type_AndroidOsHandler.sendMessage(Message.obtain(null, 0, paramRunnable));
      try
      {
        while (this.b) {
          AwSettings.this.mAwSettingsLock.wait();
        }
        return;
      }
      catch (InterruptedException paramRunnable)
      {
        Log.e("AwSettings", "Interrupted waiting a Runnable to complete", paramRunnable);
        this.b = false;
      }
    }
    
    public void updateWebkitPreferencesLocked()
    {
      runOnUiThreadBlockingAndLocked(new Runnable()
      {
        public void run()
        {
          AwSettings.this.updateWebkitPreferencesOnUiThreadLocked();
        }
      });
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  @IntDef({0L, 1L, 2L, 3L})
  public static @interface LayoutAlgorithm {}
  
  protected static class LazyDefaultUserAgent
  {
    public static final String sInstance = ;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  @IntDef({0L, 1L, 2L})
  public static @interface PluginState {}
  
  static abstract interface ZoomSupportChangeListener
  {
    public abstract void onGestureZoomSupportChanged(boolean paramBoolean1, boolean paramBoolean2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */