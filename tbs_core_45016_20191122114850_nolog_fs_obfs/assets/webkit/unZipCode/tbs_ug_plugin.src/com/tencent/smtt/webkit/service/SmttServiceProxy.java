package com.tencent.smtt.webkit.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import com.tencent.common.http.Apn;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.Base64;
import com.tencent.common.utils.MttLoader;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.mtt.AppInfoHolder;
import com.tencent.mtt.AppInfoHolder.AppInfoID;
import com.tencent.mtt.video.export.FeatureSupport;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.IH5VideoPlayer;
import com.tencent.mtt.video.export.PlayerEnv;
import com.tencent.mtt.video.export.VideoProxyDefault;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.FileChooserParams;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.smtt.net.AwNetworkUtils;
import com.tencent.smtt.os.FileUtils;
import com.tencent.smtt.util.MttLog;
import com.tencent.smtt.util.MttTimingLog;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.util.X5LogManager;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.c;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.ui.h;
import com.tencent.tbs.common.ar.WEBAR.IWebARCloudRecognitionCallback;
import com.tencent.tbs.common.ar.WEBAR.IWebAREngineCallback;
import com.tencent.tbs.common.ar.WEBAR.IWebARModelCallback;
import com.tencent.tbs.core.partner.ad.AdInfoUnit;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.tbsshell.common.ISmttServiceCommon;
import com.tencent.tbs.tbsshell.common.ISmttServiceMtt;
import com.tencent.tbs.tbsshell.common.ISmttServicePartner;
import com.tencent.tbs.tbsshell.common.X5LoadPlugin.IX5LoadPluginCallback;
import com.tencent.tbs.tbsshell.common.errorpage.IWebErrorPagePluginLoadCallback;
import com.tencent.tbs.tbsshell.common.readmode.IWebpageReadModePluginLoadCallback;
import com.tencent.tbs.tbsshell.common.webrtc.IWebRtcPluginLoadCallback;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import org.chromium.base.annotations.UsedByReflection;
import org.chromium.tencent.TencentTraceEvent;
import org.json.JSONObject;

public class SmttServiceProxy
{
  private static final String CRASH_REPORT_COMMANDLINE_SWITCH = "enable-crash-report";
  private static final String CRASH_REPORT_ENABLE_KEY = "MTT_CORE_CRASH_REPORT_ENABLE";
  private static final String CRASH_UPLOAD_LIVE_LOG_ENABLE_KEY = "MTT_CORE_CRASH_UPLOAD_LIVE_LOG_ENABLE";
  private static final String DEADCODE_DETECTION_ENABLE_KEY = "MTT_CORE_DEADCODE_DETECTION_ENABLE";
  private static final String DEADCODE_UPLOAD_BEACON_ENABLE_KEY = "MTT_CORE_DEADCODE_UPLOAD_BEACON_ENABLE";
  private static final String DEADCODE_UPLOAD_DUMP_ENABLE_KEY = "MTT_CORE_DEADCODE_UPLOAD_DUMP_ENABLE";
  private static final String PREFERENCES_ITEM_ADFILTER_ADDRESS = "adfilter_test_address";
  private static final String PREFERENCES_ITEM_AREA_TYPE = "setting_area_type";
  private static final String PREFERENCES_ITEM_CONNECT_STATUS = "connect_status_new";
  private static final String PREFERENCES_ITEM_CUSTOM_HOSTS_LIST = "custom_hosts_lists";
  private static final String PREFERENCES_ITEM_DEADCODE_EXIT_TIME = "deadcode_exit_time";
  private static final String PREFERENCES_ITEM_DEADCODE_HEARTBEAT_REPORT_INFO = "deadcode_heartbeat_report_info";
  private static final String PREFERENCES_ITEM_DEADCODE_HEARTBEAT_STACK = "deadcode_heartbeat_stack";
  private static final String PREFERENCES_ITEM_DEADCODE_IS_CRASH = "deadcode_is_crash";
  private static final String PREFERENCES_ITEM_DEADCODE_LC_BUILDNO = "deadcode_lc_buildno";
  private static final String PREFERENCES_ITEM_DEADCODE_OTHER_REPORT_INFO = "deadcode_other_report_info";
  private static final String PREFERENCES_ITEM_DEADCODE_OTHER_STACK = "deadcode_other_stack";
  private static final String PREFERENCES_ITEM_DEADCODE_SHOULD_IGNORE = "deadcode_should_ignore";
  private static final String PREFERENCES_ITEM_DOWNLOAD_INTERCEPT_STATUS = "download_intercept_status";
  private static final String PREFERENCES_ITEM_DOWNLOAD_INTERCEPT_TRACE = "download_intercept_trace";
  private static final String PREFERENCES_ITEM_ENHANCE_ADFILTER_DEBUG_SWITCH = "ea_switch";
  private static final String PREFERENCES_ITEM_FIXEDAD_FILTERED_DISABLED = "fixed_ad_filtered_disabled";
  private static final String PREFERENCES_ITEM_FIXEDAD_FILTERED_NUM = "fixed_ad_filtered_num";
  private static final String PREFERENCES_ITEM_HTTPS_TUNNEL_ADDRESS = "bkht_address";
  private static final String PREFERENCES_ITEM_HTTP_PROXY_ADDRESS = "http_proxy_address";
  private static final String PREFERENCES_ITEM_INSPECTOR_ENABLED = "blink_inspector_enabled";
  private static final String PREFERENCES_ITEM_INSPECTOR_MINI_PROGRAM_ENABLED = "blink_inspector_mini_program_enabled";
  private static final String PREFERENCES_ITEM_KING_CARD_DEBUG_SWITCH = "kc_switch";
  private static final String PREFERENCES_ITEM_LOG_FIRST_TEXT_SCREEN = "log_first_text_and_screen";
  private static final String PREFERENCES_ITEM_NOTIFY_DOWNLOAD_QB_LIMIT = "notify_download_qb_limit";
  private static final String PREFERENCES_ITEM_PERFORMANCE_LOG_ENABLED = "blink_performance_log_enabled";
  private static final String PREFERENCES_ITEM_POST_ENCRIPTION = "post_encription";
  private static final String PREFERENCES_ITEM_QB_INSTALL_STATUS = "qb_install_status";
  private static final String PREFERENCES_ITEM_QPROXY_ADDRESS = "qproxy_address";
  private static final String PREFERENCES_ITEM_QPROXY_ADDRESS_LIST = "qproxy_address_lists_new";
  private static final String PREFERENCES_ITEM_RENDER_UPLOAD_GPU = "render_upload_gpu";
  private static final String PREFERENCES_ITEM_RENDER_UPLOAD_INFO = "render_upload_info";
  private static final String PREFERENCES_ITEM_RENDER_UPLOAD_QUA = "render_upload_qua";
  private static final String PREFERENCES_ITEM_RENDER_UPLOAD_TYPE = "render_upload_type";
  private static final String PREFERENCES_ITEM_RENDER_UPLOAD_URL = "render_upload_url";
  private static final String PREFERENCES_ITEM_SHARPP_PATH = "sharpp_path";
  private static final String PREFERENCES_ITEM_SUBRES_DIRECT = "subresource_direct_code";
  private static final String PREFERENCES_ITEM_TEST_AD_DURATION = "test_ad_duration";
  private static final String PREFERENCES_ITEM_TEST_AD_MAIN_URL = "test_ad_main_url";
  private static final String PREFERENCES_ITEM_TEST_AD_OPEN_TYPE = "test_ad_open_type";
  private static final String PREFERENCES_ITEM_TEST_AD_POS = "test_ad_pos";
  private static final String PREFERENCES_ITEM_TEST_AD_STATE_KEY = "test_ad_state_key";
  private static final String PREFERENCES_ITEM_TEST_AD_TYPE = "test_ad_type";
  private static final String PREFERENCES_ITEM_TEST_AD_URL = "test_ad_url";
  private static final String PREFERENCES_ITEM_TPG_PATH = "tpg_path";
  private static final String PREFERENCES_ITEM_VCONSOLE_ENABLED = "vconsole_enabled";
  private static final String PREFERENCES_ITEM_WEBAR_DEBUG_ENABLED = "x5_webar_debug_enabled";
  private static final String PREFERENCES_ITEM_WUP_PROXY_URL = "wup_proxy_url";
  private static final String PREFERENCES_ITEM_X5JSCORE_INSPECTOR_ENABLED = "blink_x5jscore_inspector_enabled";
  private static final String PREFERENCES_ITEM_X5PROXY_NAME = "enableX5Proxy";
  private static final String PREFERENCES_ITEM_X5_CONTENT_CACHE_DISABLED = "blink_x5content_cache_disabled";
  private static final String PREFERENCES_ITEM_X5_CONTENT_CACHE_LOG_ENABLED = "blink_x5content_cache_log_enabled";
  private static final String PREFERENCES_ITEM_X5_CUSTOM_FONT_DISABLED = "blink_x5_custom_font_disabled";
  private static final String PREFERENCES_LOG_BEACONUPLOAD = "log_beaconupload";
  private static final String PRERERENCES_ITEM_X5_PROXY_SPDY_SIGN = "x5_proxy_spdy_sign";
  private static final String RENDER_ERR_STATE_ENABLE_KEY = "MTT_CORE_RENDER_ERR_STATE_ENABLE";
  private static final String RENDER_PATH_ENABLE_KEY = "MTT_CORE_RENDER_PATH_ENABLE";
  private static final String SHARE_PREFERENCES_NAME = "x5_proxy_setting";
  private static final String TAG = "SmttServiceProxy";
  private static boolean defaultIsInspectorEnabled = false;
  private static boolean defaultIsQbNotInstalled = false;
  private static boolean defaultIsX5jscoreInspectorEnabled = false;
  private static String defaultWupProxyUrl = "";
  private static SmttServiceProxy mInstance;
  private static ISmttServiceCommon mSmttServiceCommon;
  private static ISmttServiceMtt mSmttServiceMtt;
  private static ISmttServicePartner mSmttServicePartner;
  final String POS_ID_SCHEME_ACTIVE_QB = "9100";
  private boolean isEnablePostEncriptionByForce = false;
  private String mBrowserBuilderId;
  private String mBrowserVersion;
  private int mConnectStatusCode = 0;
  private String mCustomHostsStringList = null;
  private String mDevName;
  private String mDevPlatform;
  private String mDevVersion;
  private String mHttpsTunnelAddress = null;
  private boolean mLogFirstTextAndScreen = false;
  private boolean mLogIsOpened = false;
  private boolean mPerformanceLogIsOpenedDefault = false;
  private SharedPreferences mPreference = null;
  private String mQProxyAddressStringFormatByForce = null;
  private String mQProxyAddressStringList = null;
  private String mQUA;
  private int mSubResDirectCode = 0;
  private boolean mVpnState = false;
  private boolean mX5ProxyEnabled = true;
  private boolean shouldDisplayHideFunction = false;
  
  @UsedByReflection("X5CoreInit.java")
  public static SmttServiceProxy getInstance()
  {
    if (mInstance == null) {
      mInstance = new SmttServiceProxy();
    }
    return mInstance;
  }
  
  private String getLCAndBuildNO()
  {
    Object localObject2 = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_LC);
    Object localObject3 = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_BUILD_NO);
    Object localObject1;
    if (localObject2 != null)
    {
      localObject1 = localObject2;
      if (!"".equalsIgnoreCase((String)localObject2)) {}
    }
    else
    {
      localObject1 = "0000";
    }
    if (localObject3 != null)
    {
      localObject2 = localObject3;
      if (!"".equalsIgnoreCase((String)localObject3)) {}
    }
    else
    {
      localObject2 = "0000";
    }
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append((String)localObject1);
    ((StringBuilder)localObject3).append('_');
    ((StringBuilder)localObject3).append((String)localObject2);
    return ((StringBuilder)localObject3).toString();
  }
  
  private void initSharedPreferenceIfNeeded()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
  }
  
  private boolean isMainThread()
  {
    return Looper.getMainLooper() == Looper.myLooper();
  }
  
  private void showToastInUIThread(String paramString, int paramInt)
  {
    if (ContextHolder.getInstance().getContext() != null)
    {
      h.a(ContextHolder.getInstance().getContext(), paramString, paramInt, false);
      return;
    }
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon != null) {
      localISmttServiceCommon.showToast(paramString, paramInt);
    }
  }
  
  private boolean uploadDeadCodeToBeacon(String paramString1, String paramString2, Boolean paramBoolean1, String paramString3, String paramString4, String paramString5, Boolean paramBoolean2)
  {
    if (!e.a()) {
      return false;
    }
    if (("".equals(paramString1)) && ("".equals(paramString2))) {
      return false;
    }
    HashMap localHashMap = new HashMap();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("uploadTime=");
    localStringBuilder.append(System.currentTimeMillis());
    localStringBuilder.append(",");
    localStringBuilder.append(paramString1);
    localHashMap.put("reportInfo", localStringBuilder.toString());
    localHashMap.put("stack", paramString2);
    if (paramBoolean1.booleanValue()) {
      localHashMap.put("isCrash", "1");
    } else {
      localHashMap.put("isCrash", "0");
    }
    localHashMap.put("lc", paramString3);
    localHashMap.put("buildNO", paramString4);
    localHashMap.put("qua2", paramString5);
    if (paramBoolean2.booleanValue()) {
      localHashMap.put("shouldIgnore", "1");
    } else {
      localHashMap.put("shouldIgnore", "0");
    }
    getInstance().upLoadToBeacon("MTT_CORE_DEAD_CODE", localHashMap);
    paramString1 = new StringBuilder();
    paramString1.append("SmttServiceProxy.uploadDeadCodeToBeacon MTT_CORE_DEAD_CODE data=");
    paramString1.append(localHashMap.toString());
    X5Log.i("RENDER", paramString1.toString());
    return true;
  }
  
  public void ActiveQBHeartBeat(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return;
      }
      mSmttServicePartner.ActiveQBHeartBeat(paramContext);
      return;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public boolean AppLongPressMenuEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.AppLongPressMenuEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public int DownloadInterceptFileType()
  {
    try
    {
      int i = mSmttServicePartner.DownloadInterceptFileType();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public boolean LongPressMenuImageQueryEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.LongPressMenuImageQueryEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean activeQBBackHeartBeatEnable(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.activeQBBackHeartBeatEnable(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public int activeQBBackHeartBeatFrequency(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return 1;
      }
      int i = mSmttServicePartner.activeQBBackHeartBeatFrequency(paramContext);
      return i;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return 0;
  }
  
  public void adResearch(boolean paramBoolean, String paramString1, String paramString2)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.adResearch(paramBoolean, paramString1, paramString2);
      return;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  public void addDetectorTask(String paramString, ValueCallback<String> paramValueCallback)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return;
      }
      mSmttServicePartner.addDetectorTask(paramString, paramValueCallback);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void addNovelSiteAdFilterBlackListDomaim(String paramString)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        mSmttServiceCommon.addNovelSiteAdFilterBlackListDomaim(paramString);
        return;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void addNovelSiteAdFilterWhiteListDomaim(String paramString)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        mSmttServiceCommon.addNovelSiteAdFilterWhiteListDomaim(paramString);
        return;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public boolean allowUseContentCacheBusiness(String paramString1, String paramString2)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.allowUseContentCacheBusiness(paramString1, paramString2);
      return bool;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    return false;
  }
  
  public boolean canClearDNSCacheGodCmd()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.canClearDNSCacheGodCmd();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean canInsertAdInSepcialSite()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.canInsertAdInSepcialSite();
  }
  
  public boolean canInsertTbsAdInPage(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.canInsertTbsAdInPage(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean canShowEncouorageWindow()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.canShowEncouorageWindow();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean canShowReinstallTip(String paramString)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.canShowReinstallTip(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean canShowScreenAd(Context paramContext, String paramString, IX5WebView paramIX5WebView)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.canShowScreenAd(paramContext, paramString, paramIX5WebView);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean canShowScreenAdWhenBackEvent(Context paramContext, String paramString, IX5WebView paramIX5WebView)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.canShowScreenAdWhenBackEvent(paramContext, paramString, paramIX5WebView);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean canUseDynamicCanvasGpu()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.canUseDynamicCanvasGpu();
  }
  
  public boolean canUseNewJsDialog()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return true;
      }
      boolean bool = mSmttServicePartner.canUseNewJsDialog();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public boolean canUseQProxyWhenHasSystemProxy()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.canUseQProxyWhenHasSystemProxy();
  }
  
  public void cancelFingerSearch(Object paramObject, String paramString, boolean paramBoolean)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.cancelFingerSearch(paramObject, paramString, paramBoolean);
  }
  
  public void checkNeedToUpload(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.checkNeedToUpload(paramString);
  }
  
  public boolean checkUseX5CookiePathPolicyEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return true;
      }
      boolean bool = mSmttServiceCommon.checkUseX5CookiePathPolicyEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public void chooseFavorites(Object paramObject, String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.chooseFavorites(paramObject, paramString);
  }
  
  public void chooseTranslation(Object paramObject, String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.chooseTranslation(paramObject, paramString);
  }
  
  public void cleanPlugins()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.cleanPlugins();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void clearDeadCode()
  {
    if (!e.a()) {
      return;
    }
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        SmttServiceProxy.this.initSharedPreferenceIfNeeded();
        SharedPreferences.Editor localEditor = SmttServiceProxy.this.mPreference.edit();
        localEditor.remove("deadcode_lc_buildno");
        localEditor.remove("deadcode_should_ignore");
        localEditor.remove("deadcode_heartbeat_report_info");
        localEditor.remove("deadcode_heartbeat_stack");
        localEditor.remove("deadcode_other_report_info");
        localEditor.remove("deadcode_other_stack");
        localEditor.remove("deadcode_exit_time");
        localEditor.remove("deadcode_is_crash");
        localEditor.commit();
      }
    });
  }
  
  public void clearLog()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return;
      }
      mSmttServicePartner.clearLog();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void clearMiniQBUpgradeFile()
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner == null) {
      return;
    }
    localISmttServicePartner.clearMiniQBUpgradeFile();
  }
  
  public void clearNovelSiteAdFilterWhiteAndBlackListDomaim()
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        mSmttServiceCommon.clearNovelSiteAdFilterWhiteAndBlackListDomaim();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void clearQbSilentDownloadFile()
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner == null) {
      return;
    }
    localISmttServicePartner.clearQbSilentDownloadFile();
  }
  
  public void clearTestAdInfo()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.remove("test_ad_type");
    localEditor.remove("test_ad_main_url");
    localEditor.remove("test_ad_url");
    localEditor.remove("test_ad_pos");
    localEditor.remove("test_ad_duration");
    localEditor.remove("test_ad_state_key");
    localEditor.remove("test_ad_open_type");
    localEditor.commit();
  }
  
  public void closeAndUploadLogRecord()
  {
    this.mLogIsOpened = false;
    X5LogManager.setLogWrite2FileFlag(false);
    X5LogManager.uploadX5LogFilesToServer();
  }
  
  public int convertDownloadInterceptthreeswitchLevel()
  {
    try
    {
      int i = mSmttServicePartner.convertDownloadInterceptthreeswitchLevel();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public int convertGetToPostLevel()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return 0;
    }
    return localISmttServiceCommon.convertGetToPostLevel();
  }
  
  public void copyWupSwitchesToClipBoard()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.copyWupSwitchesToClipBoard();
  }
  
  public IH5VideoPlayer createTbsVideoPlayer(Context paramContext, VideoProxyDefault paramVideoProxyDefault, H5VideoInfo paramH5VideoInfo, FeatureSupport paramFeatureSupport, PlayerEnv paramPlayerEnv)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      paramContext = mSmttServiceCommon.createTbsVideoPlayer(paramContext, paramVideoProxyDefault, paramH5VideoInfo, paramFeatureSupport, paramPlayerEnv);
      return paramContext;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public String detectLanguage(String paramString1, String paramString2)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return null;
      }
      paramString1 = mSmttServicePartner.detectLanguage(paramString1, paramString2);
      return paramString1;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    return null;
  }
  
  public void detectQProxyReachable()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.detectQProxyReachable();
  }
  
  public int directAdblockSwitcherLevel()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return 0;
      }
      int i = mSmttServiceCommon.directAdblockSwitcherLevel();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public int directAdblockSwitcherLevelForQB()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return 0;
      }
      int i = mSmttServiceCommon.directAdblockSwitcherLevelForQB();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public void dismiss()
  {
    try
    {
      ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void doRecogitionOnCloud(byte[] paramArrayOfByte)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        mSmttServiceCommon.doRecogitionOnCloud(paramArrayOfByte);
        return;
      }
    }
    catch (Exception paramArrayOfByte)
    {
      paramArrayOfByte.printStackTrace();
    }
  }
  
  public void enableMiniQBAllEntryKeys()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return;
      }
      mSmttServicePartner.enableMiniQBAllEntryKeys();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void enterFingerSearchMode(Object paramObject)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.enterFingerSearchMode(paramObject);
  }
  
  public void enterOtherMode(Object paramObject)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.enterOtherMode(paramObject);
  }
  
  public void executeCopyItem(Object paramObject, String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.executeCopyItem(paramObject, paramString);
  }
  
  public void executeDiectConsumptionItems(Object paramObject, String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.executeDiectConsumptionItems(paramObject, paramString);
  }
  
  public void executeMoreItem(Object paramObject, String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.executeMoreItem(paramObject, paramString);
  }
  
  public void executeSearchItem(Object paramObject, String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.executeSearchItem(paramObject, paramString);
  }
  
  public void exitFlushBeacon()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.exitFlushBeacon();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void exitX5LongClickPopMenu(Object paramObject)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.exitX5LongClickPopMenu(paramObject);
  }
  
  public int explorerCharacter(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return 0;
      }
      int i = mSmttServicePartner.explorerCharacter(paramContext);
      return i;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return 100;
  }
  
  public void fingerSearchRequest(Object paramObject, String paramString1, int paramInt1, String paramString2, int paramInt2, int[] paramArrayOfInt, String paramString3, String paramString4)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.fingerSearchRequest(paramObject, paramString1, paramInt1, paramString2, paramInt2, paramArrayOfInt, paramString3, paramString4);
  }
  
  public void generateTbsAdUserInfo(IX5WebView paramIX5WebView, Point paramPoint1, Point paramPoint2)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.generateTbsAdUserInfo(paramIX5WebView, paramPoint1, paramPoint2);
  }
  
  public List<String> getAccessibilityBlackList()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      List localList = mSmttServiceCommon.getAccessibilityBlackList();
      return localList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String getAdBlockSourceFilePath()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return "";
      }
      String str = mSmttServiceCommon.getAdBlockSourceFilePath();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public String getAdBlockSourceMD5Value()
  {
    ISmttServiceMtt localISmttServiceMtt = mSmttServiceMtt;
    if (localISmttServiceMtt == null) {
      return null;
    }
    return localISmttServiceMtt.getAdBlockSourceMD5Value();
  }
  
  public String getAdBlockUrl()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getString("adfilter_test_address", "");
  }
  
  public String getAdJsMD5Value()
  {
    ISmttServiceMtt localISmttServiceMtt = mSmttServiceMtt;
    if (localISmttServiceMtt == null) {
      return null;
    }
    return localISmttServiceMtt.getAdJsMD5Value();
  }
  
  public boolean getAddArgumentForImageRequestEnable()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.getAddArgumentForImageRequestEnable();
      return bool;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
      return false;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public int getAndroidAccelerated2dCanvas()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return 0;
      }
      int i = mSmttServiceCommon.getAndroidAccelerated2dCanvas();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public int getAndroidGpuRasterization()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return 0;
      }
      int i = mSmttServiceCommon.getAndroidGpuRasterization();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public int getAndroidUploadTextureMode()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return 0;
      }
      int i = mSmttServiceCommon.getAndroidUploadTextureMode();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public int getAndroidWebgl()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return 0;
      }
      int i = mSmttServiceCommon.getAndroidWebgl();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public int getAreaType()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getInt("setting_area_type", 0);
  }
  
  public boolean getAutoPageDiscardingDefaultEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getAutoPageDiscardingDefaultEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getAutoPageDiscardingEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getAutoPageDiscardingEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getAutoPageRestorationDefaultEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getAutoPageRestorationDefaultEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getAutoPageRestorationEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getAutoPageRestorationEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getBDSearchPredictEnable()
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return false;
      }
      boolean bool = mSmttServiceMtt.getBDSearchPredictEnable();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getBFOptEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return true;
      }
      boolean bool = mSmttServiceCommon.getBFOptEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public String getBrowserBuildId()
  {
    Object localObject = this.mBrowserBuilderId;
    if ((localObject == null) || (((String)localObject).length() == 0))
    {
      localObject = mSmttServiceCommon;
      if (localObject != null) {
        this.mBrowserBuilderId = ((ISmttServiceCommon)localObject).getBrowserBuildId();
      }
    }
    return this.mBrowserBuilderId;
  }
  
  public String getBrowserVersion()
  {
    Object localObject = this.mBrowserVersion;
    if ((localObject == null) || (((String)localObject).length() == 0))
    {
      localObject = mSmttServiceCommon;
      if (localObject != null) {
        this.mBrowserVersion = ((ISmttServiceCommon)localObject).getBrowserVersion();
      }
    }
    return this.mBrowserVersion;
  }
  
  public Object getCachedImage(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      paramString = mSmttServiceCommon.getCachedImage(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public boolean getCanAIAExtension()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return true;
      }
      boolean bool = mSmttServiceCommon.getCanAIAExtension();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public String getCloudStringSwitch(String paramString1, String paramString2)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return paramString2;
      }
      paramString1 = mSmttServiceCommon.getCloudStringSwitch(paramString1, paramString2);
      return paramString1;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    return paramString2;
  }
  
  public int getCloudSwitch(String paramString, int paramInt)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return paramInt;
      }
      int i = mSmttServiceCommon.getCloudSwitch(paramString, paramInt);
      return i;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return paramInt;
  }
  
  public int getConnectionTimeOutGPRS()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return 0;
      }
      int i = mSmttServicePartner.getConnectionTimeOutGPRS();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public int getConnectionTimeOutWIFI()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return 0;
      }
      int i = mSmttServicePartner.getConnectionTimeOutWIFI();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public boolean getContentCacheEnabled()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.getContentCacheEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public String getCrashExtraInfo()
  {
    if (e.a()) {
      BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          SmttServiceProxy.this.initSharedPreferenceIfNeeded();
          SharedPreferences.Editor localEditor = SmttServiceProxy.this.mPreference.edit();
          localEditor.putBoolean("deadcode_is_crash", true);
          localEditor.commit();
        }
      });
    }
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner != null) {
      return localISmttServicePartner.getCrashExtraInfo();
    }
    return "";
  }
  
  public boolean getCrashReportEnabled()
  {
    boolean bool = false;
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      int i = mSmttServiceCommon.getCloudSwitch("MTT_CORE_CRASH_REPORT_ENABLE", 0);
      if (i == 1) {
        bool = true;
      }
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getCrashUploadLiveLogEnabled()
  {
    boolean bool = false;
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      int i = mSmttServiceCommon.getCloudSwitch("MTT_CORE_CRASH_UPLOAD_LIVE_LOG_ENABLE", 0);
      if (i == 1) {
        bool = true;
      }
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getCustomDiskCacheEnabled()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.getCustomDiskCacheEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public int getCustomImageQuality()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return -1;
    }
    return localISmttServiceCommon.getCustomImageQuality();
  }
  
  public boolean getDeadCodeDetectionEnabled()
  {
    boolean bool = false;
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      if ((mSmttServiceCommon.getCloudSwitch("MTT_CORE_DEADCODE_DETECTION_ENABLE", 0) == 0) && (mSmttServiceCommon.getCloudSwitch("MTT_CORE_DEADCODE_DETECTION_ENABLE", 1) == 1)) {
        return true;
      }
      int i = mSmttServiceCommon.getCloudSwitch("MTT_CORE_DEADCODE_DETECTION_ENABLE", 0);
      if (i == 1) {
        bool = true;
      }
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getDeadCodeUploadBeaconEnabled()
  {
    if (e.a().n()) {
      return true;
    }
    boolean bool = false;
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      if ((!e.a().t()) && (!e.a().g()))
      {
        if ((mSmttServiceCommon.getCloudSwitch("MTT_CORE_DEADCODE_UPLOAD_BEACON_ENABLE", 0) == 0) && (mSmttServiceCommon.getCloudSwitch("MTT_CORE_DEADCODE_UPLOAD_BEACON_ENABLE", 1) == 1)) {
          return false;
        }
      }
      else if ((mSmttServiceCommon.getCloudSwitch("MTT_CORE_DEADCODE_UPLOAD_BEACON_ENABLE", 0) == 0) && (mSmttServiceCommon.getCloudSwitch("MTT_CORE_DEADCODE_UPLOAD_BEACON_ENABLE", 1) == 1)) {
        return true;
      }
      int i = mSmttServiceCommon.getCloudSwitch("MTT_CORE_DEADCODE_UPLOAD_BEACON_ENABLE", 0);
      if (i == 1) {
        bool = true;
      }
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getDeadCodeUploadDumpEnabled()
  {
    boolean bool = false;
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      if ((mSmttServiceCommon.getCloudSwitch("MTT_CORE_DEADCODE_UPLOAD_DUMP_ENABLE", 0) == 0) && (mSmttServiceCommon.getCloudSwitch("MTT_CORE_DEADCODE_UPLOAD_DUMP_ENABLE", 1) == 1)) {
        return false;
      }
      int i = mSmttServiceCommon.getCloudSwitch("MTT_CORE_DEADCODE_UPLOAD_DUMP_ENABLE", 0);
      if (i == 1) {
        bool = true;
      }
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public String getDeviceName()
  {
    Object localObject = this.mDevName;
    if ((localObject == null) || (((String)localObject).length() == 0))
    {
      localObject = mSmttServiceCommon;
      if (localObject != null) {
        this.mDevName = ((ISmttServiceCommon)localObject).getDeviceName();
      }
    }
    return this.mDevName;
  }
  
  public String getDevicePlatform()
  {
    Object localObject = this.mDevPlatform;
    if ((localObject == null) || (((String)localObject).length() == 0))
    {
      localObject = mSmttServiceCommon;
      if (localObject != null) {
        this.mDevPlatform = ((ISmttServiceCommon)localObject).getDevicePlatform();
      }
    }
    return this.mDevPlatform;
  }
  
  public String getDeviceVersion()
  {
    Object localObject = this.mDevVersion;
    if ((localObject == null) || (((String)localObject).length() == 0))
    {
      localObject = mSmttServiceCommon;
      if (localObject != null) {
        this.mDevVersion = ((ISmttServiceCommon)localObject).getDeviceVersion();
      }
    }
    return this.mDevVersion;
  }
  
  public boolean getDiskCacheSizeSwitch()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.getDiskCacheSizeSwitch();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public int getDisplayCutoutEnableSwitch()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return 0;
      }
      int i = mSmttServiceCommon.getDisplayCutoutEnableSwitch();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public int getDnsResolveTypeForThisDomain(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return 0;
      }
      int i = mSmttServiceCommon.getDnsResolveTypeForThisDomain(paramString);
      return i;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return 0;
  }
  
  public InputStream getDomDistillerJS()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      InputStream localInputStream = mSmttServiceCommon.getDomDistillerJS();
      return localInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public List<String> getDownloadInterceptApkBlackList()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return null;
      }
      List localList = mSmttServicePartner.getDownloadInterceptApkBlackList();
      return localList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public List<String> getDownloadInterceptApkWhiteList()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return null;
      }
      List localList = mSmttServicePartner.getDownloadInterceptApkWhiteList();
      return localList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public List<String> getDownloadInterceptFileTypeWhiteList()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return null;
      }
      List localList = mSmttServicePartner.getDownloadInterceptFileTypeWhiteList();
      return localList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String getDownloadInterceptKeyMessage()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return "mLocalSmttService == null";
      }
      String str = mSmttServicePartner.getDownloadInterceptKeyMessage();
      return str;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "SmttServiceProxyError";
  }
  
  public List<String> getDownloadInterceptNoApkBlackList()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return null;
      }
      List localList = mSmttServicePartner.getDownloadInterceptNoApkBlackList();
      return localList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public int getDownloadInterceptStatus()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getInt("download_intercept_status", 0);
  }
  
  public int getDownloadInterceptToQBPopMenuStyle()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return -1;
      }
      int i = mSmttServicePartner.getDownloadInterceptToQBPopMenuStyle();
      return i;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
      return -1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }
  
  public String getDownloadInterceptTrace()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getString("download_intercept_trace", "");
  }
  
  public List<String> getECommerceFetchjsList()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      List localList = mSmttServiceCommon.getECommerceFetchjsList();
      return localList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public boolean getEnablePostEncription()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.isEnablePostEncriptionByForce = this.mPreference.getBoolean("post_encription", false);
    return this.isEnablePostEncriptionByForce;
  }
  
  public boolean getEnableQua1()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return true;
      }
      boolean bool = mSmttServiceCommon.getEnableQua1();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public boolean getEnablevConsole()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("vconsole_enabled", false);
  }
  
  public String getExtendJsRule(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getExtendJsRule(paramString);
  }
  
  public Vector<String> getExternalSDcardPath()
  {
    Object localObject = mSmttServiceCommon;
    if (localObject == null) {
      return null;
    }
    localObject = ((ISmttServiceCommon)localObject).getExternalSDcardPathArray();
    Vector localVector = new Vector();
    int j = localObject.length;
    int i = 0;
    while (i < j)
    {
      localVector.add(localObject[i]);
      i += 1;
    }
    return localVector;
  }
  
  public boolean getFakeLoginEnabled()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.getFakeLoginEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public Object getFingerSearchInstance(IX5WebView paramIX5WebView)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return null;
    }
    return localISmttServiceCommon.getFingerSearchInstance(paramIX5WebView);
  }
  
  public boolean getFirstScreenDetectEnable()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getFirstScreenDetectEnable();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getFirstScreenDrawFullScreenEnable()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getFirstScreenDrawFullScreenEnable();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getFirstScreenDrawNotFullScreenEnable()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getFirstScreenDrawNotFullScreenEnable();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getFirstStartDownloadYYBTips()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.getFirstStartDownloadYYBTips();
  }
  
  public int getFrameLimitCount(int paramInt)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return paramInt;
      }
      int i = mSmttServiceCommon.getFrameLimitCount(paramInt);
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramInt;
  }
  
  public boolean getFramePerformanceRecordEnable()
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        boolean bool = mSmttServicePartner.getFramePerformanceRecordEnable();
        return bool;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public Object getFreeWifiGuideView(Context paramContext, IX5WebView paramIX5WebView)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        paramContext = mSmttServicePartner.getFreeWifiGuideView(paramContext, paramIX5WebView);
        return paramContext;
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public String getGUID()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getGUID();
  }
  
  public int getGameServiceEnv()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return 0;
      }
      int i = mSmttServicePartner.getGameServiceEnv();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public String getGpuBugWorkAroundSwitch(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      paramString = mSmttServiceCommon.getGpuBugWorkAroundSwitch(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public Bundle getGuidBundle()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return null;
    }
    return localISmttServiceCommon.getGuidBundle();
  }
  
  public ArrayList<String> getHeadsupByTBSServer()
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner != null) {
      return localISmttServicePartner.getHeadsupByTBSServer();
    }
    return null;
  }
  
  public boolean getHitRateEnabled()
  {
    ISmttServiceMtt localISmttServiceMtt = mSmttServiceMtt;
    if (localISmttServiceMtt == null) {
      return false;
    }
    return localISmttServiceMtt.getHitRateEnabled();
  }
  
  public String getHttpDNSEncryptKey()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getHttpDNSEncryptKey();
  }
  
  public int getHttpDNSRequestID(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return 0;
    }
    return localISmttServiceCommon.getHttpDNSRequestID(paramString);
  }
  
  public String getHttpDNSServerIP()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getHttpDNSServerIP();
  }
  
  public String getHttpProxyAddressStringAsSystemProxy()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getString("http_proxy_address", "");
  }
  
  public String getHttpTunnelAddressStringAtDebugPage()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mHttpsTunnelAddress = this.mPreference.getString("bkht_address", "");
    return this.mHttpsTunnelAddress;
  }
  
  public String getHttpsTunnelProxyAddress(String paramString1, boolean paramBoolean1, int paramInt, boolean paramBoolean2, String paramString2)
  {
    Object localObject = getHttpTunnelAddressStringAtDebugPage();
    if (!StringUtils.isEmpty((String)localObject))
    {
      paramString1 = new StringBuilder();
      paramString1.append("true|");
      paramString1.append((String)localObject);
      return paramString1.toString();
    }
    localObject = mSmttServiceCommon;
    if (localObject == null) {
      return "";
    }
    return ((ISmttServiceCommon)localObject).getHttpsTunnelProxyAddress(paramString1, paramBoolean1, paramInt, paramBoolean2, paramString2);
  }
  
  public String getHttpsTunnelToken()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getKingCardToken();
  }
  
  public String getIPListForWupAndQProxy()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getIPListForWupAndQProxy();
  }
  
  public boolean getInterceptDownloadInQB(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.getInterceptDownloadInQB(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean getInterceptDownloadInTencentFile(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.getInterceptDownloadInTencentFile(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public String getInterceptDownloadMessage()
  {
    try
    {
      String str = mSmttServicePartner.getInterceptDownloadMessage();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public boolean getIsAdFilterEnhance()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("ea_switch", false);
  }
  
  public boolean getIsInspectorEnabled()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("blink_inspector_enabled", defaultIsInspectorEnabled);
  }
  
  public boolean getIsInspectorMiniProgramEnabled()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("blink_inspector_mini_program_enabled", false);
  }
  
  public boolean getIsKingCardEnableAtDebugPage()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("kc_switch", false);
  }
  
  public boolean getIsUseThirdPartyAdRules()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return true;
    }
    return localISmttServiceCommon.getIsUseThirdPartyAdRules();
  }
  
  public boolean getIsWebARDebugEnabled()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("x5_webar_debug_enabled", false);
  }
  
  public boolean getIsX5ContentCacheDisabled()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("blink_x5content_cache_disabled", false);
  }
  
  public boolean getIsX5ContentCacheLogEnabled()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("blink_x5content_cache_log_enabled", false);
  }
  
  public boolean getIsX5CustomFontDisabled()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("blink_x5_custom_font_disabled", false);
  }
  
  public boolean getIsX5ProEnabled()
  {
    try
    {
      if (!e.a().n()) {
        return false;
      }
      if (!ThreadUtils.isMainProcess(ContextHolder.getInstance().getApplicationContext())) {
        return false;
      }
      if (mSmttServiceMtt == null) {
        return false;
      }
      boolean bool = mSmttServiceMtt.getIsX5ProEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getIsX5jscoreInspectorEnabled()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("blink_x5jscore_inspector_enabled", defaultIsX5jscoreInspectorEnabled);
  }
  
  public boolean getJniRegisterEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return true;
      }
      boolean bool = mSmttServiceCommon.getJniRegisterEnabled();
      return bool;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public String getJsTemplate()
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return null;
      }
      String str = mSmttServiceMtt.getJsTemplate();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String getLC()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getLC();
  }
  
  public Map<String, String> getLbsHeaders(String paramString)
  {
    HashMap localHashMap = new HashMap();
    Object localObject = mSmttServiceCommon;
    if (localObject != null) {
      paramString = ((ISmttServiceCommon)localObject).getLbsHeaders(paramString);
    } else {
      paramString = null;
    }
    if (paramString != null)
    {
      localObject = paramString.keySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        String str = (String)((Iterator)localObject).next();
        localHashMap.put(str, new String(Base64.encode((byte[])paramString.get(str))));
      }
    }
    return localHashMap;
  }
  
  public String getLiveLogGodCmd()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      String str = mSmttServiceCommon.getLiveLogGodCmd();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public ArrayList<String> getLocalConfigFilePath()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return null;
    }
    return localISmttServiceCommon.getLocalConfigFilePath();
  }
  
  public byte[] getLocalPluginInfo()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      byte[] arrayOfByte = mSmttServiceCommon.getLocalPluginInfo();
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String getLocalPluginPath()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return "";
      }
      String str = mSmttServiceCommon.getLocalPluginPath();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public boolean getLogBeaconUpload()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("log_beaconupload", false);
  }
  
  public String[] getLoginUserInfo()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return null;
    }
    return localISmttServiceCommon.getLoginUserInfo();
  }
  
  public int getLongPressToQBPopMenuStyle()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return -1;
      }
      int i = mSmttServicePartner.getLongPressToQBPopMenuStyle();
      return i;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
      return -1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }
  
  public boolean getMSEEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getMSEEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public String getMd5GUID()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getMd5GUID();
  }
  
  public String getMiniQBUA()
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner != null) {
      return localISmttServicePartner.getMiniQBUA();
    }
    return null;
  }
  
  public String getMiniQBVC()
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner != null) {
      return localISmttServicePartner.getMiniQBVC();
    }
    return null;
  }
  
  public String getMiniQbVersionName()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return null;
      }
      String str = mSmttServicePartner.getMiniQbVersionName();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String getModuleMD5Value()
  {
    ISmttServiceMtt localISmttServiceMtt = mSmttServiceMtt;
    if (localISmttServiceMtt == null) {
      return null;
    }
    return localISmttServiceMtt.getModuleMD5Value();
  }
  
  public int getNativeOptSwitches()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return 1;
      }
      int i = mSmttServiceCommon.getNativeOptSwitches();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 1;
  }
  
  public boolean getNeedWIFILogin()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.getNeedWIFILogin();
  }
  
  public int getNotifyDownloadQBCount()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getInt("notify_download_qb_limit", 0);
  }
  
  public int getNotifyDownloadQBTimesLimit()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return -1;
      }
      int i = mSmttServicePartner.getNotifyDownloadQBTimesLimit();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }
  
  public String getOAID()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return "";
      }
      String str = mSmttServiceCommon.getOAID();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public boolean getOpenWifiProxyEnable()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.getOpenWifiProxyEnable();
  }
  
  public String getPasswordKey()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getPasswordKey();
  }
  
  public void getPluginForce(Context paramContext)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.getPluginForce(paramContext);
      return;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public boolean getPopupHideAdDialogEnabled()
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return false;
      }
      boolean bool = mSmttServiceMtt.getPopupHideAdDialogEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public String getPrerenderPkgName()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return "";
      }
      String str = mSmttServiceCommon.getPrerenderPkgName();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public int getPrerenderSwitch()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return -1;
      }
      int i = mSmttServiceCommon.getPrerenderSwitch();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }
  
  public int getProxyAddressType()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return 1;
      }
      if (getQuicProxySwitch())
      {
        int i = mSmttServiceCommon.getProxyAddressType();
        return i;
      }
      return 1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 1;
  }
  
  public String getQAID()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return "";
      }
      String str = mSmttServiceCommon.getQAID();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public String getQAuth()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getQAuth();
  }
  
  public String getQIMEI()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return "";
      }
      String str = mSmttServiceCommon.getQIMEI();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public URL getQProxyAddress(int paramInt)
  {
    if (isEnableDirect()) {
      return null;
    }
    try
    {
      Object localObject = getQProxyAddressString();
      if (!StringUtils.isEmpty((String)localObject)) {
        return new URL((String)localObject);
      }
      if (mSmttServiceCommon != null)
      {
        localObject = new URL(mSmttServiceCommon.getQProxyAddressInStringFormat(paramInt, false));
        return (URL)localObject;
      }
      return null;
    }
    catch (MalformedURLException localMalformedURLException) {}
    return null;
  }
  
  public URL getQProxyAddressForHttp(int paramInt)
  {
    if (isEnableDirect()) {
      return null;
    }
    try
    {
      Object localObject = getQProxyAddressString();
      if (!StringUtils.isEmpty((String)localObject)) {
        return new URL((String)localObject);
      }
      if (mSmttServiceCommon != null)
      {
        localObject = new URL(mSmttServiceCommon.getQProxyAddressInStringFormat(paramInt, true));
        return (URL)localObject;
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
    }
    return null;
  }
  
  public String getQProxyAddressString()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mQProxyAddressStringFormatByForce = this.mPreference.getString("qproxy_address", "");
    return this.mQProxyAddressStringFormatByForce;
  }
  
  public int getQProxyType()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mConnectStatusCode = this.mPreference.getInt("connect_status_new", 0);
    return this.mConnectStatusCode;
  }
  
  public int getQProxyUsingFlag(String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, byte paramByte, String paramString2, boolean paramBoolean4)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return 0;
    }
    return localISmttServiceCommon.getQProxyUsingFlag(paramString1, paramBoolean1, paramBoolean2, paramBoolean3, paramByte, paramString2, paramBoolean4);
  }
  
  public int getQQInterruptApkType()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return -1;
      }
      int i = mSmttServicePartner.getQQInterruptApkType();
      return i;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
      return -1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }
  
  public int getQQInterruptNotApkType()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return -1;
      }
      int i = mSmttServicePartner.getQQInterruptNotApkType();
      return i;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
      return -1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }
  
  public String getQUA()
  {
    Object localObject = this.mQUA;
    if ((localObject == null) || (((String)localObject).length() == 0)) {
      localObject = mSmttServiceMtt;
    }
    return this.mQUA;
  }
  
  public String getQUA2()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getQUA2();
  }
  
  public boolean getQbInstallStatus()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("qb_install_status", defaultIsQbNotInstalled);
  }
  
  public boolean getQuicProxySwitch()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return true;
      }
      boolean bool = mSmttServiceCommon.getQuicProxySwitch();
      return bool;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public int getRedirectCountLimit()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return -2;
      }
      int i = mSmttServiceCommon.getRedirectCountLimit();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -2;
  }
  
  public long getRemoteServerTimeStamp()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return -1L;
      }
      long l = mSmttServiceCommon.getRemoteServerTimeStamp();
      return l;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1L;
  }
  
  public int getRestrictErrorPageReloadSecond()
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner == null) {
      return 2;
    }
    return localISmttServicePartner.getRestrictErrorPageReloadSecond();
  }
  
  public boolean getSPAAdPageReportSwitch()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getSPAAdPageReportSwitch();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getSPDYProxySign()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("x5_proxy_spdy_sign", false);
  }
  
  public String getSWApiReportWhiteList()
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return null;
      }
      String str = mSmttServiceMtt.getSWApiReportWhiteList();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public List<String> getSWPresetWhiteList()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      List localList = mSmttServiceCommon.getSWPresetWhiteList();
      return localList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public List<String> getSWUpdateWhiteList()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      List localList = mSmttServiceCommon.getSWUpdateWhiteList();
      return localList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public int getServerAllowQProxyStatus()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return -1;
      }
      int i = mSmttServiceCommon.getServerAllowQProxyStatus();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }
  
  public Vector<String> getSessionPersistentSupported0RTTDomains()
  {
    Object localObject = mSmttServiceMtt;
    if (localObject == null) {
      return null;
    }
    localObject = ((ISmttServiceMtt)localObject).getSessionPersistentSupported0RTTDomains();
    if (localObject == null) {
      return null;
    }
    Vector localVector = new Vector();
    int j = localObject.length;
    int i = 0;
    while (i < j)
    {
      localVector.add(localObject[i]);
      i += 1;
    }
    return localVector;
  }
  
  public Vector<String> getSessionPersistentSupportedDomains()
  {
    Object localObject = mSmttServiceCommon;
    if (localObject == null) {
      return null;
    }
    localObject = ((ISmttServiceCommon)localObject).getSessionPersistentSupportedDomains();
    if (localObject == null) {
      return null;
    }
    Vector localVector = new Vector();
    int j = localObject.length;
    int i = 0;
    while (i < j)
    {
      localVector.add(localObject[i]);
      i += 1;
    }
    return localVector;
  }
  
  public boolean getSharpDefaultEnable()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getSharpDefaultEnable();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getSharpEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getSharpEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public String getSharpPPath()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getString("sharpp_path", "");
  }
  
  public boolean getShouldDisplayHideFunction()
  {
    return this.shouldDisplayHideFunction;
  }
  
  public String getSmartAdLiveLog()
  {
    ISmttServiceMtt localISmttServiceMtt = mSmttServiceMtt;
    if (localISmttServiceMtt == null) {
      return "";
    }
    return localISmttServiceMtt.getSmartAdLiveLog();
  }
  
  public long getSntpTime()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return 0L;
      }
      long l = mSmttServiceCommon.getSntpTime();
      return l;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0L;
  }
  
  public String getSpecialSiteReportRatioInterval()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      String str = mSmttServiceCommon.getSpecialSiteReportRatioInterval();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String getSpyFinanceAdRatio()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      String str = mSmttServiceCommon.getSpyFinanceAdRatioInterval();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public int getSubResDirect()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mSubResDirectCode = this.mPreference.getInt("subresource_direct_code", 0);
    return this.mSubResDirectCode;
  }
  
  public boolean getSubResourcePerformanceEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getSubResourcePerformanceEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public String getSubsetAdRuleFilePath()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return "";
      }
      String str = mSmttServiceCommon.getSubsetAdRuleFilePath();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public String getSubsetAdRuleMD5Value()
  {
    ISmttServiceMtt localISmttServiceMtt = mSmttServiceMtt;
    if (localISmttServiceMtt == null) {
      return null;
    }
    return localISmttServiceMtt.getSubsetAdRuleMD5Value();
  }
  
  public String getSwitchUA(String paramString, int[] paramArrayOfInt)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getSwitchUA(paramString, paramArrayOfInt);
  }
  
  public int getSystemPopMenuStyle()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return -1;
      }
      int i = mSmttServicePartner.getSystemPopMenuStyle();
      return i;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
      return -1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }
  
  public String getTAID()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return "";
      }
      String str = mSmttServiceCommon.getTAID();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public String getTPGPath()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getString("tpg_path", "");
  }
  
  public String getTbsAdAppInstallCheckRatioInterval()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      String str = mSmttServiceCommon.getTbsAdAppInstallCheckRatioInterval();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String getTbsAdCompetitorReportRatio()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      String str = mSmttServiceCommon.getTbsAdCompetitorReportRatioInterval();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public ArrayList<String> getTbsAdCompetitors()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      ArrayList localArrayList = mSmttServiceCommon.getTbsAdCompetitors();
      return localArrayList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public void getTbsAdDebugWhiteList()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.getTbsAdDebugWhiteList();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public InputStream getTbsAdReinstallTipJS()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return null;
      }
      InputStream localInputStream = mSmttServicePartner.getTbsAdReinstallTipJS();
      return localInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String getTbsAdStatsRatio()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      String str = mSmttServiceCommon.getTbsAdStatsRatioInterval();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public ArrayList<String> getTbsAdUrl()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      ArrayList localArrayList = mSmttServiceCommon.getTbsAdUrl();
      return localArrayList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String getTbsAdUserInfo(IX5WebView paramIX5WebView, String paramString, Point paramPoint1, Point paramPoint2)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return null;
    }
    return localISmttServiceCommon.getTbsAdUserInfo(paramIX5WebView, paramString, paramPoint1, paramPoint2);
  }
  
  public InputStream getTbsAutoInsertAdJS()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      InputStream localInputStream = mSmttServiceCommon.getTbsAutoInsertAdJS();
      return localInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String getTbsCoreVersion()
  {
    return ContextHolder.getInstance().getTbsCoreVersion();
  }
  
  public boolean getTbsImpatientNetworkEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.getTbsImpatientNetworkEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public String getTbsSdkVersion()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(ContextHolder.getInstance().getSdkVersion());
    return localStringBuilder.toString();
  }
  
  public String getTbsUserinfoLiveLog()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getTbsUserinfoLiveLog();
  }
  
  public String getTbsWebviewSearchEngineUrl()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getTbsWebviewSearchEngineUrl();
  }
  
  public int getTestAdDuration()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getInt("test_ad_duration", 0);
  }
  
  public AdInfoUnit getTestAdInfo()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    int i = this.mPreference.getInt("test_ad_type", 0);
    int j = this.mPreference.getInt("test_ad_pos", 1);
    String str1 = this.mPreference.getString("test_ad_url", "");
    if ((i != 0) && (!str1.equals("")))
    {
      int k = this.mPreference.getInt("test_ad_duration", 0);
      String str2 = this.mPreference.getString("test_ad_main_url", "");
      return new AdInfoUnit(i, this.mPreference.getString("test_ad_state_key", ""), str1, "md5", k, str2, j, this.mPreference.getInt("test_ad_open_type", 2));
    }
    return null;
  }
  
  public String getTestAdMainUrl()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getString("test_ad_main_url", "");
  }
  
  public int getTestAdOpenType()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getInt("test_ad_open_type", 0);
  }
  
  public int getTestAdPos()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getInt("test_ad_pos", 0);
  }
  
  public String getTestAdStatKey()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getString("test_ad_state_key", "");
  }
  
  public int getTestAdType()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    int i = this.mPreference.getInt("test_ad_type", 0);
    if (i == 4) {
      return 2;
    }
    if (i == 1) {
      return 0;
    }
    return 3;
  }
  
  public String getTestAdUrl()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getString("test_ad_url", "");
  }
  
  public int getTpgEnabledType()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return 1;
      }
      int i = mSmttServiceCommon.getTpgEnabledType();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 1;
  }
  
  public Vector<String> getUserBehaviourDomains()
  {
    Object localObject = mSmttServiceCommon;
    if (localObject == null) {
      return null;
    }
    localObject = ((ISmttServiceCommon)localObject).getUserBehaviourDomains();
    if (localObject == null) {
      return null;
    }
    Vector localVector = new Vector();
    int j = localObject.length;
    int i = 0;
    while (i < j)
    {
      localVector.add(localObject[i]);
      i += 1;
    }
    return localVector;
  }
  
  public List<String> getVideoSniffList()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return null;
    }
    return localISmttServiceCommon.getVideoSniffList();
  }
  
  public boolean getWXPCDefaultEnable()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.getWXPCDefaultEnable();
      return bool;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
      return false;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean getWXPCEnabled()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.getWXPCEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public HashMap<String, String> getWeChatPayServiceProviderSpiedInfoMap()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return null;
      }
      HashMap localHashMap = mSmttServiceCommon.getWeChatPayServiceProviderSpiedInfoMap();
      return localHashMap;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String getWebARSlamHardwareConfig()
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        String str = mSmttServiceCommon.getWebARSlamHardwareConfig();
        return str;
      }
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public String getWhiteListInfo()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getWhiteListInfo();
  }
  
  public String getWupAddressByForce()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getWupAddressByForce();
  }
  
  public String getWupProxyUrl()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getString("wup_proxy_url", defaultWupProxyUrl);
  }
  
  public String getX5LongClickPopupMenuSubText()
  {
    try
    {
      String str = mSmttServicePartner.getX5LongClickPopupMenuSubText();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public String getXTbsKeyPrivateKey()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return "";
    }
    return localISmttServiceCommon.getXTbsKeyPrivateKey();
  }
  
  public String[] getforceGoQuicList()
  {
    return null;
  }
  
  public void giveToastTips(String paramString)
  {
    ISmttServiceMtt localISmttServiceMtt = mSmttServiceMtt;
    if (localISmttServiceMtt == null) {
      return;
    }
    localISmttServiceMtt.giveToastTips(paramString);
  }
  
  public String guessFileName(String paramString1, String paramString2, String paramString3)
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner == null) {
      return null;
    }
    return localISmttServicePartner.guessFileName(paramString1, paramString2, paramString3);
  }
  
  public void headsupActiveQB(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return;
      }
      mSmttServicePartner.headsupActiveQB(paramContext);
      return;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public void hideScreenAd(IX5WebView paramIX5WebView)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.hideScreenAd(paramIX5WebView);
      return;
    }
    catch (Exception paramIX5WebView)
    {
      paramIX5WebView.printStackTrace();
    }
  }
  
  public void interceptVideoPlay(Object paramObject, Context paramContext, Handler paramHandler)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return;
      }
      mSmttServicePartner.interceptVideoPlay(paramObject, paramContext, paramHandler);
      return;
    }
    catch (Exception paramObject)
    {
      ((Exception)paramObject).printStackTrace();
    }
  }
  
  public boolean isAllowDnsStoreEnable()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.isAllowDnsStoreEnable();
  }
  
  public boolean isAllowLocalAddrAccess(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return true;
      }
      boolean bool = mSmttServiceCommon.isAllowLocalAddrAccess(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return true;
  }
  
  public boolean isAllowQHead()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.isAllowQHead();
  }
  
  public boolean isAllowReportBadJs(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon != null) {
      return localISmttServiceCommon.isAllowReportBadJs(paramString);
    }
    return false;
  }
  
  public boolean isAppInSwitchToMiniQBWhiteList(String paramString)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isAppInSwitchToMiniQBWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isAutoSwitchARCoreBlackList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isAutoSwitchARCoreBlackList(paramString);
        return bool;
      }
    }
    catch (NoSuchMethodError paramString)
    {
      paramString.printStackTrace();
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isAutoSwitchARCoreDeviceWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isAutoSwitchARCoreDeviceWhiteList(paramString);
        return bool;
      }
    }
    catch (NoSuchMethodError paramString)
    {
      paramString.printStackTrace();
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isAutoSwitchARCoreEnabled()
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isAutoSwitchARCoreEnabled();
        return bool;
      }
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isAutoSwitchARCoreWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isAutoSwitchARCoreWhiteList(paramString);
        return bool;
      }
    }
    catch (NoSuchMethodError paramString)
    {
      paramString.printStackTrace();
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isAutoSwitchSlamVioBlackList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isAutoSwitchSlamVioBlackList(paramString);
        return bool;
      }
    }
    catch (NoSuchMethodError paramString)
    {
      paramString.printStackTrace();
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isAutoSwitchSlamVioEnabled()
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isAutoSwitchSlamVioEnabled();
        return bool;
      }
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isAutoSwitchSlamVioWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isAutoSwitchSlamVioWhiteList(paramString);
        return bool;
      }
    }
    catch (NoSuchMethodError paramString)
    {
      paramString.printStackTrace();
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isBottomBarEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isBottomBarEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isBubbleAdEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isBubbleAdEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isBubbleMiniQbAdEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isBubbleMiniQbAdEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isClickImageScanEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isClickImageScanEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isCodeCacheEnable()
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isCodeCacheEnable();
        return bool;
      }
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public boolean isCurrentPkgEnablePrerender(String paramString)
  {
    String str = getPrerenderPkgName();
    if (StringUtils.isEmpty(str)) {
      return false;
    }
    return str.trim().contains(paramString);
  }
  
  public boolean isDebugMiniQBEnv()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isDebugMiniQBEnv();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isDebugWupEnvironment()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.isDebugWupEnvironment();
  }
  
  public boolean isDefaultVideoFullScreenPlay(boolean paramBoolean)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return true;
      }
      paramBoolean = mSmttServiceCommon.isDefaultVideoFullScreenPlay(paramBoolean);
      return paramBoolean;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public boolean isDetectDownloadPkgName()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isDetectDownloadPkgName();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isDomDistillWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return false;
      }
      boolean bool = mSmttServiceMtt.isDomDistillWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isDownloadFileInterceptFileTypeWhiteList(String paramString)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isDownloadFileInterceptFileTypeWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isDownloadFileInterceptNotAPKDomainBlackList(String paramString)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isDownloadFileInterceptNotAPKDomainBlackList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isDownloadFileInterceptWhiteList(String paramString)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isDownloadFileInterceptWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isDownloadInterceptSwitchMatch(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isDownloadInterceptSwitchMatch(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isEnableAutoRemoveAds()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.isEnableAutoRemoveAds();
  }
  
  public boolean isEnableDirect()
  {
    return getQProxyType() == -1;
  }
  
  public boolean isEnableLiveLog()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return true;
    }
    return localISmttServiceCommon.isEnableLogs();
  }
  
  public boolean isEnablePreConn()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.isEnablePreConn();
  }
  
  public boolean isEnableRenderInfoUploadErrState()
  {
    boolean bool = false;
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      int i = mSmttServiceCommon.getCloudSwitch("MTT_CORE_RENDER_ERR_STATE_ENABLE", 0);
      if (i == 1) {
        bool = true;
      }
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isEnableTbsARPage(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isEnableTbsARPage(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isEnableVideoSameLayer(String paramString, boolean paramBoolean)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.isEnableVideoSameLayer(paramString, paramBoolean);
  }
  
  public boolean isEnableX5HTTP2Proxy()
  {
    return getQProxyType() == 4;
  }
  
  public boolean isEnableX5HTTP2ProxyByForce()
  {
    return getQProxyType() == 2;
  }
  
  public boolean isEnableX5Proxy()
  {
    if (this.mPreference == null)
    {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
      this.mX5ProxyEnabled = this.mPreference.getBoolean("enableX5Proxy", true);
    }
    return this.mX5ProxyEnabled;
  }
  
  public boolean isEnableX5QUICDirect()
  {
    return getQProxyType() == 5;
  }
  
  public boolean isEnableX5QUICProxy()
  {
    return getQProxyType() == 3;
  }
  
  public boolean isEnableX5QUICProxyByForce()
  {
    return getQProxyType() == 1;
  }
  
  public int isEnableX5TBSHeaderType(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return 0;
    }
    return localISmttServiceCommon.isEnableX5TBSHeaderType(paramString);
  }
  
  public boolean isEnabledRenderPathUpload()
  {
    boolean bool = false;
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      int i = mSmttServiceCommon.getCloudSwitch("MTT_CORE_RENDER_PATH_ENABLE", 0);
      if (i == 1) {
        bool = true;
      }
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isFileChooserTBSEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isFileChooserTBSEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isForceVideoPagePlay(String paramString, boolean paramBoolean)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      paramBoolean = mSmttServiceCommon.isForceVideoPagePlay(paramString, paramBoolean);
      return paramBoolean;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isGameEngineUseSandbox()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return true;
      }
      boolean bool = mSmttServicePartner.isGameEngineUseSandbox();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public boolean isGameFrameworkEnabled()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isGameFrameworkEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isGdtAdvertisementEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isGdtAdvertisementEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isHeadsUpRiskWebsite(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isHeadsUpRiskWebsite(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isHeadsUpTaobaoLinkEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isHeadsUpTaobaoLinkEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isHeadsUpTranscodingPageEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isHeadsUpTranscodingPageEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isHostInTbsAdAppInstallCheckWhitelist(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isHostInTbsAdAppInstallCheckWhitelist(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isHostInTbsAdWhitelist(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isHostInTbsAdWhitelist(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isHostInTbsJsAdReportWhitelist(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isHostInTbsJsAdReportWhitelist(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isHostNameInAdblockBlackList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return true;
      }
      boolean bool = mSmttServiceCommon.isHostNameInAdblockBlackList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return true;
  }
  
  public boolean isHostNameInAdblockWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isHostNameInAdblockWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isHostNameInSPAAdPageReportWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return true;
      }
      boolean bool = mSmttServiceCommon.isHostNameInSPAAdPageReportWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return true;
  }
  
  public boolean isHostNameInSwitchToMiniQBWhiteList(String paramString)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return true;
      }
      boolean bool = mSmttServicePartner.isHostNameInSwitchToMiniQBWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return true;
  }
  
  public boolean isHttps0RTTEnabled()
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return false;
      }
      boolean bool = mSmttServiceMtt.isHttps0RTTEnabled();
      return bool;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
      return false;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isImageBrowserDisableInPage(String paramString)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isImageBrowserDisableInPage(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isInDebugTbsAdWhiteList()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isInDebugTbsAdWhiteList();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isInMiniProgram(IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isInMiniProgram(paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramIX5WebViewBase)
    {
      paramIX5WebViewBase.printStackTrace();
    }
    return false;
  }
  
  public boolean isInNovelSiteAdFilterBlackList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isInNovelSiteAdFilterBlackList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isInNovelSiteAdFilterWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isInNovelSiteAdFilterWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isInQuicDirectBlacklist(String paramString)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isInQuicDirectBlacklist(paramString);
        return bool;
      }
    }
    catch (NoSuchMethodError paramString)
    {
      paramString.printStackTrace();
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return true;
  }
  
  public boolean isInRenderFixedADFilterDomainWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isInRenderFixedADFilterDomainWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isInRenderHijackDomainWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isInRenderHijackDomainWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isInRenderReportDomainWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isInRenderReportDomainWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isInSelectedAdFilterWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isInSelectedAdFilterWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isInTBSFileChooserBlackDomainList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isInTBSFileChooserBlackDomainList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isInWebpageReadModeBlackList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isInWebpageReadModeBlackList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isInWebpageReadModeWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isInWebpageReadModeWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isInterceptDownloadRequestEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isInterceptDownloadRequestEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isKingCardUser()
  {
    if (!WebSettingsExtension.getQProxyEnabled()) {
      return false;
    }
    if (getIsKingCardEnableAtDebugPage()) {
      return true;
    }
    if (Apn.isWifiMode()) {
      return false;
    }
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.isKingCardUser();
  }
  
  public boolean isLastWupReqFromTestEvn(String paramString)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isLastWupReqFromTestEvn(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isLbsDontAlertWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isLbsDontAlertWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isLogRecordOpen()
  {
    return this.mLogIsOpened;
  }
  
  public boolean isLongPressImageScanEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isLongPressImageScanEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isLongPressMenuEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return true;
      }
      boolean bool = mSmttServicePartner.isLongPressMenuEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isLongPressMenuExplorerEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isLongPressMenuExplorerEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isLongPressMenuOpenInBrowserEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isLongPressMenuOpenInBrowserEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isLongPressMenuRefreshEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isLongPressMenuRefreshEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isLongPressMenuScreenShotEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isLongPressMenuScreenShotEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isLongPressMenuSelectCopyEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isLongPressMenuSelectCopyEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isLongPressQuickSecondMenu_QQ_ThirdApp(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isLongPressQuickSecondMenu_QQ_ThirdApp(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isLongPressQuickSelectCopyEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isLongPressQuickSelectCopyEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isMSEBlackList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isMSEBlackList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isMediaCodecBlackList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isMediaCodecBlackList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isMeizuNightModeEnabled()
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner == null) {
      return false;
    }
    return localISmttServicePartner.isMeizuNightModeEnabled();
  }
  
  public boolean isMidPageQbNightModeOnLongPressEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isMidPageQbNightModeOnLongPressEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isMidPageQbOpenBrowserOnLongPressEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isMidPageQbOpenBrowserOnLongPressEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isMidPageQbSearchOnLongPressEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isMidPageQbSearchOnLongPressEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isMidPageQbTranslateOnLongPressEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isMidPageQbTranslateOnLongPressEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isMidPageQbWebviewBubbleEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isMidPageQbWebviewBubbleEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isMiniQBDisabled()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isMiniQBDisabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isNativeBufferEnable()
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isNativeBufferEnable();
        return bool;
      }
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public boolean isNeedQHead(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.isNeedQHead(paramString);
  }
  
  public boolean isOpenDebugTbsEnabled()
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner == null) {
      return false;
    }
    return localISmttServicePartner.isOpenDebugTbsEnabled();
  }
  
  public boolean isOpenUrlOnLongPressEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isOpenUrlOnLongPressEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isOutFrequencyControl(Context paramContext, String paramString, IX5WebView paramIX5WebView)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isOutFrequencyControl(paramContext, paramString, paramIX5WebView);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isPerformanceLogRecordOpen()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getBoolean("blink_performance_log_enabled", this.mPerformanceLogIsOpenedDefault);
  }
  
  public boolean isPlatformTypeReportEnabled()
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner == null) {
      return false;
    }
    return localISmttServicePartner.isPlatformTypeReportEnabled();
  }
  
  public boolean isPluginSupported(String paramString1, String paramString2, String paramString3)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.isPluginSupported(paramString1, paramString2, paramString3);
  }
  
  public boolean isQBDownloaded()
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        boolean bool = mSmttServicePartner.isQBDownloaded();
        return bool;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isQBPureIncrease(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isQBPureIncrease(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isQBiconInQQShineEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isQBiconInQQShineEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isQQDomain(String paramString)
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner != null) {
      return localISmttServicePartner.isQQDomain(paramString);
    }
    return false;
  }
  
  public boolean isQQErrorPageLittleGameEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isQQErrorPageLittleGameEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isRecordingTrace()
  {
    return TencentTraceEvent.isRecordingTrace();
  }
  
  public boolean isReportCallWebviewApiOnWrongThreadEnabled()
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return false;
      }
      boolean bool = mSmttServiceMtt.isReportCallWebviewApiOnWrongThreadEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isRestrictErrorPageReload(String paramString)
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner == null) {
      return false;
    }
    return localISmttServicePartner.isRestrictErrorPageReload(paramString);
  }
  
  public boolean isSafeDownloadInterceptEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isSafeDownloadInterceptEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isSchemeInDeeplinkBlackList(String paramString)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isSchemeInDeeplinkBlackList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isSchemeInDeeplinkWhiteList(String paramString)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isSchemeInDeeplinkWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isSchemeIntercept(String paramString)
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner != null) {
      return localISmttServicePartner.isSchemeIntercept(paramString);
    }
    return false;
  }
  
  public boolean isScreenAdDomainWhiteListMatched(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isScreenAdDomainWhiteListMatched(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isSearchOnLongPressEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isSearchOnLongPressEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isSessionPersistentEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isSessionPersistentEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isShouldContentCacheCurrentFrameWhenJsLocation(String paramString)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        boolean bool = mSmttServicePartner.isShouldContentCacheCurrentFrameWhenJsLocation(paramString);
        return bool;
      }
    }
    catch (NoSuchMethodError paramString)
    {
      paramString.printStackTrace();
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return true;
  }
  
  public boolean isShouldInterceptMttbrowser()
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        boolean bool = mSmttServicePartner.isShouldInterceptMttbrowser();
        return bool;
      }
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isShouldInterceptMttbrowserUseMiniQb()
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        boolean bool = mSmttServicePartner.isShouldInterceptMttbrowserUseMiniQb();
        return bool;
      }
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isSpliceAdEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isSpliceAdEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isSpliceMiniQbAdEnabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isSpliceMiniQbAdEnabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isStatReportPage(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.isStatReportPage(paramString);
  }
  
  public boolean isTbsARDenyPermisionRequest(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isTbsARDenyPermisionRequest(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isTbsAREnable(Context paramContext)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isTbsAREnable(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isTbsARGrantPermisionRequest(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isTbsARGrantPermisionRequest(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isTbsAdHttpProxySwitchOpened()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isTbsAdHttpProxySwitchOpened();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isTbsClipBoardEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isTbsClipBoardEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isTbsJsapiEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isTbsJsapiEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isTbsWebRTCAudioWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isTbsWebRTCAudioWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isTestMiniQBEnv()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isTestMiniQBEnv();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isTranslateOnLongPressEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isTranslateOnLongPressEnabled(paramContext, paramIX5WebViewBase);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isUseDefaultDNSTTL(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.isUseDefaultDNSTTL(paramString);
  }
  
  public boolean isVPNConnected()
  {
    return this.mVpnState;
  }
  
  public boolean isWXWholePageTranslateEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isWXWholePageTranslateEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isWasmDisabled()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isWasmDisabled();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public boolean isWeChatCrashMonitorEnable()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isWeChatCrashMonitorEnable();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebARCameraWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isWebARCameraWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebARMarkerDisabled()
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isWebARMarkerDisabled();
        return bool;
      }
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebARMarkerEnabledOnAllDevices()
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isWebARMarkerEnabledOnAllDevices();
        return bool;
      }
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebARMarkerWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isWebARMarkerWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebARSlamDisabled()
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isWebARSlamDisabled();
        return bool;
      }
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebARSlamEnabledOnAllDevices()
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isWebARSlamEnabledOnAllDevices();
        return bool;
      }
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebARSlamVIODisabled()
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isWebARSlamVIODisabled();
        return bool;
      }
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebARSlamWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isWebARSlamWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebARTFLiteWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isWebARTFLiteWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebRTCOptimizationWhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isWebRTCOptimizationWhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebRTCPluginAutoDownloadNotAllowed()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isWebRTCPluginAutoDownloadNotAllowed();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebRTCPluginLoadBlackList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isWebRTCPluginLoadBlackList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebRTCinCamera1WhiteList(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.isWebRTCinCamera1WhiteList(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isWebUrlInCloudList(String paramString, int paramInt)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.isWebUrlInCloudList(paramString, paramInt);
        return bool;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean isX5CameraEnabled(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isX5CameraEnabled(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean isX5CookieIsolationEnabled()
  {
    if (!e.a().g()) {
      return false;
    }
    try
    {
      if (mSmttServicePartner == null)
      {
        if ((Build.VERSION.SDK_INT >= 20) && (Build.VERSION.SDK_INT <= 23)) {
          return true;
        }
      }
      else
      {
        boolean bool = mSmttServicePartner.isX5CookieIsolationEnabled();
        return bool;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return (Build.VERSION.SDK_INT >= 20) && (Build.VERSION.SDK_INT <= 23);
    }
    return false;
  }
  
  public boolean isX5CookieIsolationEnabledForTBS()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.isX5CookieIsolationEnabledForTBS();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean isX5ProxyRequestEncrypted()
  {
    if (mSmttServiceCommon == null) {
      return false;
    }
    if (getEnablePostEncription()) {
      return true;
    }
    return mSmttServiceCommon.isX5ProxyRequestEncrypted();
  }
  
  public boolean isX5ProxySupportReadMode()
  {
    return false;
  }
  
  public boolean isX5ProxySupportWebP()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.isX5ProxySupportWebP();
  }
  
  public int kingCardUserProxyJudgeCondition()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return 0;
    }
    return localISmttServiceCommon.kingCardUserProxyJudgeCondition();
  }
  
  public String loadBKHT()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mHttpsTunnelAddress = this.mPreference.getString("bkht_address", "");
    return this.mHttpsTunnelAddress;
  }
  
  public String loadCustomHostsList()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mCustomHostsStringList = this.mPreference.getString("custom_hosts_lists", "");
    return this.mCustomHostsStringList;
  }
  
  public String loadQProxyAddressList()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mQProxyAddressStringList = this.mPreference.getString("qproxy_address_lists_new", "");
    return this.mQProxyAddressStringList;
  }
  
  public void loadWebAREngine(int paramInt, IWebAREngineCallback paramIWebAREngineCallback)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        mSmttServiceCommon.loadWebAREngine(paramInt, paramIWebAREngineCallback);
        return;
      }
    }
    catch (NoSuchMethodError paramIWebAREngineCallback)
    {
      paramIWebAREngineCallback.printStackTrace();
      return;
    }
    catch (Exception paramIWebAREngineCallback)
    {
      paramIWebAREngineCallback.printStackTrace();
    }
  }
  
  public void loadWebARModel(String paramString, IWebARModelCallback paramIWebARModelCallback)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        mSmttServiceCommon.loadWebARModel(paramString, paramIWebARModelCallback);
        return;
      }
    }
    catch (NoSuchMethodError paramString)
    {
      paramString.printStackTrace();
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void loadWebErrorPagePlugin(IWebErrorPagePluginLoadCallback paramIWebErrorPagePluginLoadCallback)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        mSmttServiceCommon.loadWebErrorPagePlugin(paramIWebErrorPagePluginLoadCallback);
        return;
      }
    }
    catch (NoSuchMethodError paramIWebErrorPagePluginLoadCallback)
    {
      paramIWebErrorPagePluginLoadCallback.printStackTrace();
      return;
    }
    catch (Exception paramIWebErrorPagePluginLoadCallback)
    {
      paramIWebErrorPagePluginLoadCallback.printStackTrace();
    }
  }
  
  public void loadWebRtcPlugin(IWebRtcPluginLoadCallback paramIWebRtcPluginLoadCallback)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        mSmttServiceCommon.loadWebRtcPlugin(paramIWebRtcPluginLoadCallback);
        return;
      }
    }
    catch (NoSuchMethodError paramIWebRtcPluginLoadCallback)
    {
      paramIWebRtcPluginLoadCallback.printStackTrace();
      return;
    }
    catch (Exception paramIWebRtcPluginLoadCallback)
    {
      paramIWebRtcPluginLoadCallback.printStackTrace();
    }
  }
  
  public void loadWebpageReadModePlugin(IWebpageReadModePluginLoadCallback paramIWebpageReadModePluginLoadCallback)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        mSmttServiceCommon.loadWebpageReadModePlugin(paramIWebpageReadModePluginLoadCallback);
        return;
      }
    }
    catch (NoSuchMethodError paramIWebpageReadModePluginLoadCallback)
    {
      paramIWebpageReadModePluginLoadCallback.printStackTrace();
      return;
    }
    catch (Exception paramIWebpageReadModePluginLoadCallback)
    {
      paramIWebpageReadModePluginLoadCallback.printStackTrace();
    }
  }
  
  public void loadX5Plugin(int paramInt, IX5LoadPluginCallback paramIX5LoadPluginCallback)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        mSmttServiceCommon.loadX5Plugin(paramInt, paramIX5LoadPluginCallback);
        return;
      }
    }
    catch (NoSuchMethodError paramIX5LoadPluginCallback)
    {
      paramIX5LoadPluginCallback.printStackTrace();
      return;
    }
    catch (Exception paramIX5LoadPluginCallback)
    {
      paramIX5LoadPluginCallback.printStackTrace();
    }
  }
  
  public String logDebugExec(String paramString)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        paramString = mSmttServicePartner.logDebugExec(paramString);
        return paramString;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public boolean logFirstTextAndFirstScreenEnable()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mLogFirstTextAndScreen = this.mPreference.getBoolean("log_first_text_and_screen", false);
    return this.mLogFirstTextAndScreen;
  }
  
  public void monitorAppRemoved(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return;
      }
      mSmttServicePartner.monitorAppRemoved(paramContext);
      return;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public boolean needIgnoreGdtAd()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return true;
      }
      boolean bool = mSmttServiceCommon.needIgnoreGdtAd();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public boolean needRequestTbsAd(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.needRequestTbsAd(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public Context newPluginContextWrapper(Context paramContext, String paramString)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return paramContext;
      }
      paramContext = mSmttServicePartner.newPluginContextWrapper(paramContext, paramString);
      return paramContext;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public int nightModeInLongPressthreeswitchLevel(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return 0;
      }
      int i = mSmttServicePartner.nightModeInLongPressthreeswitchLevel(paramContext);
      return i;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return 0;
  }
  
  public void notifyHttpsTunnelExpired()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.notifyHttpsTunnelExpired();
  }
  
  public boolean notifyHttpsTunnelProxyFail(int paramInt1, String paramString, int paramInt2)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.notifyHttpsTunnelProxyFail(paramInt1, paramString, paramInt2);
  }
  
  public void notifyProxyStatusReport(boolean paramBoolean, HashMap<String, String> paramHashMap)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.notifyProxyStatusReport(paramBoolean, paramHashMap);
  }
  
  public void notifyQProxyDetectResult(Boolean paramBoolean, String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.notifyQProxyDetectResult(paramBoolean, paramString);
  }
  
  public boolean notifyQProxyFailHandler(int paramInt1, String paramString, int paramInt2)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.notifyQProxyFailHandler(paramInt1, paramString, paramInt2);
  }
  
  public void notifyVPNChanged(boolean paramBoolean)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Options=VPN_CHANGED; open=");
      localStringBuilder.append(paramBoolean);
      X5Log.i("NET", localStringBuilder.toString());
      AwNetworkUtils.clearDNSCache();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void onBeaconReport(String paramString, Map<String, String> paramMap)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.onBeaconReport(paramString, paramMap);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void onGeolocationStartUpdating(ValueCallback<Location> paramValueCallback, ValueCallback<Bundle> paramValueCallback1, boolean paramBoolean)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.onGeolocationStartUpdating(paramValueCallback, paramValueCallback1, paramBoolean);
  }
  
  public void onGeolocationStopUpdating()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.onGeolocationStopUpdating();
  }
  
  public boolean onLongClickSearch(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.onLongClickSearch(paramContext, paramString1, paramString2, paramBundle);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public boolean onOpenInBrowserClick(Context paramContext, String paramString, Bundle paramBundle)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.onOpenInBrowserClick(paramContext, paramString, paramBundle);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public void onPageLoadFinished(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.onPageLoadFinished(paramString);
  }
  
  public void onPageLoadStart(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.onPageLoadStart(paramString);
  }
  
  public void onProxyDetectFinish(int paramInt1, int paramInt2)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.onProxyDetectFinish(paramInt1, paramInt2);
  }
  
  public void onReportApiExecResult(String paramString, int paramInt1, int paramInt2)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.onReportApiExecResult(paramString, paramInt1, paramInt2);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void onReportDisplayCutoutInfo(String paramString1, String paramString2, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.reportDisplayCutoutInfo(paramString1, paramString2, paramBoolean, paramInt1, paramInt2);
      return;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  public void onReportJSDoThrow(String paramString1, String paramString2)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.onReportJSDoThrow(paramString1, paramString2);
      return;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  public void onReportMetrics(String paramString1, String paramString2, long paramLong1, long paramLong2, int paramInt1, String paramString3, boolean paramBoolean1, int paramInt2, String paramString4, String paramString5, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3, boolean paramBoolean5, int paramInt4)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.onReportMetrics(paramString1, paramString2, paramLong1, paramLong2, paramInt1, paramString3, paramBoolean1, paramInt2, paramString4, paramString5, paramBoolean2, paramBoolean3, paramBoolean4, paramInt3, paramBoolean5, paramInt4);
    adResearch(paramBoolean2, paramString2, paramString1);
  }
  
  public void onReportPageTotalTimeV2(String paramString1, long paramLong1, long paramLong2, long paramLong3, long paramLong4, String paramString2, byte paramByte, String paramString3, boolean paramBoolean)
  {
    ISmttServiceMtt localISmttServiceMtt = mSmttServiceMtt;
    if (localISmttServiceMtt == null) {
      return;
    }
    localISmttServiceMtt.onReportPageTotalTimeV2(paramString1, paramLong1, paramLong2, paramLong3, paramLong4, paramString2, paramByte, paramString3, paramBoolean);
  }
  
  public void onReportPerformanceV4(HashMap<String, String> paramHashMap)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.onReportPerformanceV4(paramHashMap);
  }
  
  public void onReportRedirectIntercept(String paramString, int paramInt)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.reportRedirectIntercept(paramString, paramInt);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void onReportResouceLoadError(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, long paramLong)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.onReportResouceLoadError(paramString, paramBoolean1, paramBoolean2, paramBoolean3, paramLong);
  }
  
  public void onReportSWNetFlowInfo(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.onReportSWNetFlowInfo(paramString, paramInt1, paramInt2, paramInt3);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void onReportSWRegistInfo(String paramString)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.onReportSWRegistInfo(paramString);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void onReportSavePageToDiskInfo(String paramString1, String paramString2, int paramInt)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.reportSavePageToDiskInfo(paramString1, paramString2, paramInt);
      return;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  public void onReportTbsDAE(String paramString1, long paramLong, String paramString2, int paramInt1, String[] paramArrayOfString, int paramInt2)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("MOVE onReportTbsDAE url = ");
      localStringBuilder.append(paramString1);
      localStringBuilder.append(" time = ");
      localStringBuilder.append(paramLong);
      localStringBuilder.append(" apn = ");
      localStringBuilder.append(paramInt1);
      localStringBuilder.append(" urlsFiltered = ");
      localStringBuilder.append(Arrays.toString(paramArrayOfString));
      localStringBuilder.append(" hiddenNumber = ");
      localStringBuilder.append(paramInt2);
      MttLog.d("ADBlock", localStringBuilder.toString());
      mSmttServiceCommon.onReportTbsDAE(paramString1, paramLong, paramString2, paramInt1, paramArrayOfString, paramInt2);
      return;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  public void onReportX5CoreDataDirInfo(boolean paramBoolean)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.reportX5CoreDataDirInfo(paramBoolean);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void onWebViewAppExit()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.onWebViewAppExit();
  }
  
  public void onWebViewDestroy()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.onWebViewDestroy();
  }
  
  public void onWebViewDetachedFromWindow()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.onWebViewDetachedFromWindow();
  }
  
  public void onWebViewPause()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.onWebViewPause();
  }
  
  public void onWebviewStatusChange(IX5WebView paramIX5WebView, int paramInt, JSONObject paramJSONObject)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.onWebviewStatusChange(paramIX5WebView, paramInt, paramJSONObject);
      return;
    }
    catch (Exception paramIX5WebView)
    {
      paramIX5WebView.printStackTrace();
    }
  }
  
  public void onWindowFocusChanged(Context paramContext, boolean paramBoolean)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return;
      }
      mSmttServicePartner.onWindowFocusChanged(paramContext, paramBoolean);
      return;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public void openBrowserListWithGeneralDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        mSmttServicePartner.openBrowserListWithGeneralDownloader(paramString1, paramContext, paramIntent, paramFile, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramMap);
        return;
      }
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  public void openBrowserListWithQBDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    openBrowserListWithQBDownloader(paramString1, paramContext, paramIntent, paramFile, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, null);
  }
  
  public void openBrowserListWithQBDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        mSmttServicePartner.openBrowserListWithQBDownloader(paramString1, paramContext, paramIntent, paramFile, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramMap);
        return;
      }
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  public void openBrowserListWithQBDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap, Bundle paramBundle)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        mSmttServicePartner.openBrowserListWithQBDownloader(paramString1, paramContext, paramIntent, paramFile, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramMap, paramBundle);
        return;
      }
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  public View openLandPage(Context paramContext, String paramString, ViewGroup paramViewGroup)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return null;
      }
      paramContext = mSmttServicePartner.openLandPage(paramContext, paramString, paramViewGroup);
      return paramContext;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public void openLogRecord()
  {
    this.mLogIsOpened = true;
    X5LogManager.setLogWrite2FileFlag(true);
  }
  
  public void openLogRecord(String paramString)
  {
    this.mLogIsOpened = true;
    X5LogManager.setLogWrite2FileFlag(true, paramString);
  }
  
  public boolean openTBSFileChooser(IX5WebViewBase paramIX5WebViewBase, ValueCallback<Uri[]> paramValueCallback, IX5WebChromeClient.FileChooserParams paramFileChooserParams, File paramFile, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Map<String, Drawable> paramMap)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        boolean bool = mSmttServicePartner.openTBSFileChooser(paramIX5WebViewBase, paramValueCallback, paramFileChooserParams.getMode(), paramFile, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramMap);
        return bool;
      }
    }
    catch (Exception paramIX5WebViewBase)
    {
      paramIX5WebViewBase.printStackTrace();
    }
    return false;
  }
  
  public boolean openUrlAndDownloadInQQBrowserWithReport(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    if (!MttLoader.isBrowserInstalled(paramContext)) {
      return false;
    }
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner == null) {
      return false;
    }
    return localISmttServicePartner.openUrlAndDownloadInQQBrowserWithReport(paramContext, paramBoolean, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9);
  }
  
  public boolean openUrlInQQBrowser(Context paramContext, String paramString1, String paramString2)
  {
    if (!MttLoader.isBrowserInstalled(paramContext)) {
      return false;
    }
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.openUrlInQQBrowser(paramContext, paramString1, paramString2);
  }
  
  public boolean openUrlInQQBrowserWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    return openUrlInQQBrowserWithReportAndRecordAnchor(paramContext, paramString1, paramString2, paramString3, paramString4, new Point(0, 0), new Point(0, 0));
  }
  
  public boolean openUrlInQQBrowserWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if (!MttLoader.isBrowserInstalled(paramContext)) {
      return false;
    }
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner == null) {
      return false;
    }
    return localISmttServicePartner.openUrlInQQBrowserWithReport(paramContext, paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  public boolean openUrlInQQBrowserWithReportAndRecordAnchor(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, Point paramPoint1, Point paramPoint2)
  {
    if (!MttLoader.isBrowserInstalled(paramContext))
    {
      if ((paramString4 != null) && (paramString4.equals("9100")))
      {
        userBehaviorStatistics("TBSSAQNQ");
        return false;
      }
      return false;
    }
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner == null) {
      return false;
    }
    return localISmttServicePartner.openUrlInQQBrowserWithReportAndRecordAnchor(paramContext, paramString1, paramString2, paramString3, paramString4, paramPoint1, paramPoint2);
  }
  
  public void playVideo(H5VideoInfo paramH5VideoInfo)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.playVideo(paramH5VideoInfo);
  }
  
  public int preLoadScreenAd(Context paramContext, String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return -1;
      }
      int i = mSmttServiceCommon.preLoadScreenAd(paramContext, paramString);
      return i;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return -2;
  }
  
  public void pullWupDomainInfoByForce()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.pullWupDomainInfoByForce();
  }
  
  public void pullWupInfoByForce()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.pullWupInfoByForce();
  }
  
  public void pullWupIpListByForce()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.pullWupIpListByForce();
  }
  
  public void pullWupPreferenceByForce()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.pullWupPreferenceByForce();
  }
  
  public void recoderTrace(String paramString)
  {
    TencentTraceEvent.recoderTrace(paramString);
  }
  
  public void reportAccessibilityEnableCause(String paramString)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.reportAccessibilityEnableCause(paramString);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void reportAdHiddenNum(String paramString, int paramInt)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("MOVE onReportTbsDAE url = ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(" hiddenNumber = ");
      localStringBuilder.append(paramInt);
      MttLog.d("ADBlock", localStringBuilder.toString());
      mSmttServiceCommon.reportAdHiddenNum(paramString, paramInt);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void reportBigKingStatus(HashMap<String, String> paramHashMap)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.reportBigKingStatus(paramHashMap);
  }
  
  public void reportCallWebviewApiOnWrongThread(String paramString)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.reportCallWebviewApiOnWrongThread(paramString);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public boolean reportDnsResolveResults(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString1, String paramString2, String paramString3, int paramInt7, String paramString4, int paramInt8)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.reportDnsResolveResults(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramString1, paramString2, paramString3, paramInt7, paramString4, paramInt8);
  }
  
  public void reportDoFingerSearch(Object paramObject)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.reportDoFingerSearch(paramObject);
  }
  
  public void reportDoLongClick(Object paramObject)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.reportDoLongClick(paramObject);
  }
  
  public void reportErrorPageLearn(String paramString, int paramInt)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.reportErrorPageLearn(paramString, paramInt);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void reportJsFetchResults(HashMap paramHashMap)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.reportJsFetchResults(paramHashMap);
      return;
    }
    catch (Exception paramHashMap)
    {
      paramHashMap.printStackTrace();
    }
  }
  
  public void reportMSEUrl(String paramString)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.reportMSEUrl(paramString);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void reportPageImageInfo(Map<String, String> paramMap)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.reportPageImageInfo(paramMap);
      return;
    }
    catch (Exception paramMap)
    {
      paramMap.printStackTrace();
    }
  }
  
  public void reportShowLongClickPopupMenu(Object paramObject)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.reportShowLongClickPopupMenu(paramObject);
  }
  
  public void reportTbsError(int paramInt, String paramString)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        mSmttServicePartner.reportTbsError(paramInt, paramString);
        return;
      }
    }
    catch (NoSuchMethodError paramString)
    {
      paramString.printStackTrace();
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void reportUserFinallySelectText(Object paramObject, String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.reportUserFinallySelectText(paramObject, paramString);
  }
  
  public void reportVibrationInfo(String paramString, int paramInt)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.reportVibrationInfo(paramString, paramInt);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void requestCloudRecognition(String paramString, IWebARCloudRecognitionCallback paramIWebARCloudRecognitionCallback)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        mSmttServiceCommon.requestCloudRecognition(paramString, paramIWebARCloudRecognitionCallback);
        return;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public boolean requestHeadsUp(Context paramContext)
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner != null) {
      return localISmttServicePartner.requestHeadsUp(paramContext);
    }
    return false;
  }
  
  public void requestServiceByAreaType(int paramInt)
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner != null) {
      localISmttServicePartner.requestServiceByAreaType(paramInt);
    }
  }
  
  public void resetAdBlockSourceMD5()
  {
    ISmttServiceMtt localISmttServiceMtt = mSmttServiceMtt;
    if (localISmttServiceMtt == null) {
      return;
    }
    localISmttServiceMtt.resetAdBlockSourceMD5();
  }
  
  public void resetIPList()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.resetIPList();
  }
  
  public void resetJSAndModelMD5(boolean paramBoolean1, boolean paramBoolean2)
  {
    ISmttServiceMtt localISmttServiceMtt = mSmttServiceMtt;
    if (localISmttServiceMtt == null) {
      return;
    }
    localISmttServiceMtt.resetJSAndModelMD5(paramBoolean1, paramBoolean2);
  }
  
  public void resetSubsetAdRuleMD5()
  {
    ISmttServiceMtt localISmttServiceMtt = mSmttServiceMtt;
    if (localISmttServiceMtt == null) {
      return;
    }
    localISmttServiceMtt.resetSubsetAdRuleMD5();
  }
  
  public boolean satisfyRatioControl(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.satisfyRatioControl(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public void saveBKHT(String paramString)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mHttpsTunnelAddress = paramString;
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putString("bkht_address", paramString);
    localEditor.commit();
  }
  
  public void saveCustomHostsList(String paramString)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mCustomHostsStringList = paramString;
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putString("custom_hosts_lists", paramString);
    localEditor.commit();
  }
  
  public void saveDeadDump()
  {
    if (!e.a()) {
      return;
    }
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        String str = SmttServiceProxy.this.getLCAndBuildNO();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(str);
        localStringBuilder.append("_dump_2nd.dmp");
        c.b(localStringBuilder.toString());
      }
    });
  }
  
  public void saveQProxyAddressList(String paramString)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mQProxyAddressStringList = paramString;
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putString("qproxy_address_lists_new", paramString);
    localEditor.commit();
  }
  
  public void saveSharpPPath(final String paramString)
  {
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        if (SmttServiceProxy.this.mPreference == null) {
          SmttServiceProxy.access$002(SmttServiceProxy.this, ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0));
        }
        SharedPreferences.Editor localEditor = SmttServiceProxy.this.mPreference.edit();
        localEditor.putString("sharpp_path", paramString);
        localEditor.commit();
      }
    });
  }
  
  public void saveTPGPath(final String paramString)
  {
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        if (SmttServiceProxy.this.mPreference == null) {
          SmttServiceProxy.access$002(SmttServiceProxy.this, ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0));
        }
        SharedPreferences.Editor localEditor = SmttServiceProxy.this.mPreference.edit();
        localEditor.putString("tpg_path", paramString);
        localEditor.commit();
      }
    });
  }
  
  public int saveWebPageInLongPressthreeswitchLevel(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return 0;
      }
      int i = mSmttServicePartner.saveWebPageInLongPressthreeswitchLevel(paramContext);
      return i;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return 0;
  }
  
  public boolean schemeInterceptActiveQBEnable(Context paramContext)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.schemeInterceptActiveQBEnable(paramContext);
      return bool;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public void sendMainResourceSourceToBeacon(String paramString1, String paramString2)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.sendMainResourceSourceToBeacon(paramString1, paramString2);
      return;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  public void sendMiniQBWupRequestForSwitch(Context paramContext)
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner != null) {
      localISmttServicePartner.sendMiniQBWupRequestForSwitch(paramContext);
    }
  }
  
  public void sendUserBeaviourDataToBeacon(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString6, String paramString7, String paramString8)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.sendUserBeaviourDataToBeacon(paramString1, paramString2, paramString3, paramString4, paramString5, paramInt1, paramInt2, paramInt3, paramInt4, paramString6, paramString7, paramString8);
      return;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  public void setAdBlockUrl(String paramString)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putString("adfilter_test_address", paramString);
    localEditor.commit();
  }
  
  public void setAdFitlerEnhance(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("ea_switch", paramBoolean);
    localEditor.commit();
  }
  
  public void setAreaType(int paramInt)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putInt("setting_area_type", paramInt);
    localEditor.commit();
  }
  
  public void setDeadCode(final String paramString1, final String paramString2, final boolean paramBoolean, final String paramString3)
  {
    if (!e.a()) {
      return;
    }
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        String str = SmttServiceProxy.this.getLCAndBuildNO();
        SmttServiceProxy.this.initSharedPreferenceIfNeeded();
        Object localObject = SmttServiceProxy.this.mPreference.edit();
        ((SharedPreferences.Editor)localObject).putString("deadcode_lc_buildno", str);
        ((SharedPreferences.Editor)localObject).putBoolean("deadcode_should_ignore", paramBoolean);
        if ("BLOCK_TYPE_HEARTBEAT".equalsIgnoreCase(paramString1))
        {
          ((SharedPreferences.Editor)localObject).putString("deadcode_heartbeat_report_info", paramString2);
          ((SharedPreferences.Editor)localObject).putString("deadcode_heartbeat_stack", paramString3);
        }
        else
        {
          ((SharedPreferences.Editor)localObject).putString("deadcode_other_report_info", paramString2);
          ((SharedPreferences.Editor)localObject).putString("deadcode_other_stack", paramString3);
        }
        ((SharedPreferences.Editor)localObject).commit();
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString1);
        ((StringBuilder)localObject).append("_");
        ((StringBuilder)localObject).append(str);
        ((StringBuilder)localObject).append("_dump_1st.dmp");
        c.b(((StringBuilder)localObject).toString());
      }
    });
  }
  
  public void setDeadCodeExit()
  {
    if (!e.a()) {
      return;
    }
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        SmttServiceProxy.this.initSharedPreferenceIfNeeded();
        SharedPreferences.Editor localEditor = SmttServiceProxy.this.mPreference.edit();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("exitTime=");
        localStringBuilder.append(System.currentTimeMillis());
        localStringBuilder.append(",");
        localEditor.putString("deadcode_exit_time", localStringBuilder.toString());
        localEditor.commit();
      }
    });
  }
  
  public void setDownloadInterceptStatus(int paramInt)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putInt("download_intercept_status", paramInt);
    localEditor.commit();
  }
  
  public void setDownloadInterceptTrace(String paramString)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putString("download_intercept_trace", paramString);
    localEditor.commit();
  }
  
  public void setEnablePostEncription(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.isEnablePostEncriptionByForce = paramBoolean;
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("post_encription", paramBoolean);
    localEditor.commit();
  }
  
  public void setEnablevConsole(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("vconsole_enabled", paramBoolean);
    localEditor.commit();
  }
  
  public void setEntryDataForSearchTeam(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.setEntryDataForSearchTeam(paramString);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void setFixedADFilteredNum(final int paramInt)
  {
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        SmttServiceProxy.this.initSharedPreferenceIfNeeded();
        SharedPreferences.Editor localEditor = SmttServiceProxy.this.mPreference.edit();
        localEditor.putInt("fixed_ad_filtered_num", paramInt);
        localEditor.commit();
      }
    });
  }
  
  public void setGameSandbox(boolean paramBoolean)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return;
      }
      mSmttServicePartner.setGameSandbox(paramBoolean);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void setGameServiceEnv(int paramInt)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return;
      }
      mSmttServicePartner.setGameServiceEnv(paramInt);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void setHttpProxyAddressAsSystemProxy(String paramString)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putString("http_proxy_address", paramString);
    localEditor.commit();
  }
  
  public void setIsInspectorEnabled(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("blink_inspector_enabled", paramBoolean);
    localEditor.commit();
  }
  
  public void setIsInspectorMiniProgramEnabled(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("blink_inspector_mini_program_enabled", paramBoolean);
    localEditor.commit();
  }
  
  public void setIsWebARDebugEnabled(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("x5_webar_debug_enabled", paramBoolean);
    localEditor.commit();
  }
  
  public void setIsX5ContentCacheDisabled(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("blink_x5content_cache_disabled", paramBoolean);
    localEditor.commit();
  }
  
  public void setIsX5ContentCacheLogEnabled(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("blink_x5content_cache_log_enabled", paramBoolean);
    localEditor.commit();
  }
  
  public void setIsX5CustomFontDisabled(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("blink_x5_custom_font_disabled", paramBoolean);
    localEditor.commit();
  }
  
  public void setIsX5jscoreInspectorEnabled(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("blink_x5jscore_inspector_enabled", paramBoolean);
    localEditor.commit();
  }
  
  public void setKingCardEnableAtDebugPage(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("kc_switch", paramBoolean);
    localEditor.commit();
  }
  
  @UsedByReflection("X5CoreInit.java")
  public void setLocalSmttService(ISmttServiceCommon paramISmttServiceCommon, ISmttServicePartner paramISmttServicePartner, ISmttServiceMtt paramISmttServiceMtt)
  {
    mSmttServiceCommon = paramISmttServiceCommon;
    mSmttServicePartner = paramISmttServicePartner;
    mSmttServiceMtt = paramISmttServiceMtt;
  }
  
  public void setLogBeaconUpload(boolean paramBoolean)
  {
    Object localObject = mSmttServicePartner;
    if (localObject == null) {
      return;
    }
    ((ISmttServicePartner)localObject).setLogBeaconUpload(paramBoolean);
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mLogFirstTextAndScreen = paramBoolean;
    localObject = this.mPreference.edit();
    ((SharedPreferences.Editor)localObject).putBoolean("log_beaconupload", paramBoolean);
    ((SharedPreferences.Editor)localObject).commit();
  }
  
  public void setLogFirstTextAndFirstScreen(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mLogFirstTextAndScreen = paramBoolean;
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("log_first_text_and_screen", paramBoolean);
    localEditor.commit();
  }
  
  public void setNeedWIFILogin(boolean paramBoolean)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.setNeedWIFILogin(paramBoolean);
  }
  
  public void setNotifyDownloadQBCount(int paramInt)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putInt("notify_download_qb_limit", paramInt);
    localEditor.commit();
  }
  
  public void setPerformanceLogFlag(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("blink_performance_log_enabled", paramBoolean);
    localEditor.commit();
  }
  
  public void setQBPageVideoSearchWupCallback(ValueCallback<Map<String, String>> paramValueCallback)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return;
      }
      mSmttServiceMtt.setQBPageVideoSearchWupCallback(paramValueCallback);
      return;
    }
    catch (Exception paramValueCallback)
    {
      paramValueCallback.printStackTrace();
    }
  }
  
  public void setQProxyAddressIndex(int paramInt)
  {
    if (paramInt == -1)
    {
      this.mQProxyAddressStringFormatByForce = "";
    }
    else
    {
      localObject = loadQProxyAddressList().split("=")[paramInt].split("\\|");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("http://");
      localStringBuilder.append(localObject[0]);
      this.mQProxyAddressStringFormatByForce = localStringBuilder.toString();
    }
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    Object localObject = this.mPreference.edit();
    ((SharedPreferences.Editor)localObject).putString("qproxy_address", this.mQProxyAddressStringFormatByForce);
    ((SharedPreferences.Editor)localObject).commit();
  }
  
  public boolean setQProxyBlackDomain(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.setQProxyBlackDomain(paramString);
  }
  
  public boolean setQProxyBlackUrl(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.setQProxyBlackUrl(paramString);
  }
  
  @UsedByReflection("MiniQB")
  public void setQProxyType(int paramInt)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mConnectStatusCode = paramInt;
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putInt("connect_status_new", paramInt);
    localEditor.commit();
    AwNetworkUtils.setQProxyType(paramInt);
  }
  
  public boolean setQProxyWhiteUrl(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.setQProxyWhiteUrl(paramString);
  }
  
  public void setQbInstallStatus(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("qb_install_status", paramBoolean);
    localEditor.commit();
  }
  
  public void setSPDYProxySign(boolean paramBoolean)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putBoolean("x5_proxy_spdy_sign", paramBoolean);
    localEditor.commit();
  }
  
  public void setShouldDisplayHideFunction(boolean paramBoolean)
  {
    this.shouldDisplayHideFunction = paramBoolean;
  }
  
  public void setSkvDataForSearchTeam(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.setSkvDataForSearchTeam(paramString);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void setSubResDirect(int paramInt)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    this.mSubResDirectCode = paramInt;
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putInt("subresource_direct_code", paramInt);
    localEditor.commit();
  }
  
  public boolean setSwitchSoftwareToBeacon(final String paramString, final int paramInt)
  {
    if (!e.r()) {
      return false;
    }
    if ("".equals(paramString)) {
      return false;
    }
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("url", paramString);
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("");
        ((StringBuilder)localObject).append(paramInt);
        localHashMap.put("switch_bytes", ((StringBuilder)localObject).toString());
        String str2 = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_BUILD_NO);
        String str1 = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_LC);
        String str3 = SmttServiceProxy.getInstance().getQUA2();
        if (str1 != null)
        {
          localObject = str1;
          if (!"".equalsIgnoreCase(str1)) {}
        }
        else
        {
          localObject = "0000";
        }
        if (str2 != null)
        {
          str1 = str2;
          if (!"".equalsIgnoreCase(str2)) {}
        }
        else
        {
          str1 = "0000";
        }
        localHashMap.put("build_no", str1);
        localHashMap.put("lc", localObject);
        localHashMap.put("qua2", str3);
        SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_SWITCH_SOFTWARE", localHashMap);
      }
    });
    return true;
  }
  
  public void setTbsCoreVersion(String paramString)
  {
    X5WebViewAdapter.setTbsCoreVersion(paramString);
  }
  
  public void setTestAdInfo(int paramInt1, int paramInt2, String paramString1, int paramInt3, String paramString2, String paramString3, int paramInt4)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putInt("test_ad_type", 1 << paramInt1);
    localEditor.putInt("test_ad_pos", paramInt2);
    localEditor.putString("test_ad_url", paramString1);
    localEditor.putInt("test_ad_duration", paramInt3);
    localEditor.putString("test_ad_main_url", paramString2);
    localEditor.putString("test_ad_state_key", paramString3);
    localEditor.putInt("test_ad_open_type", paramInt4);
    localEditor.commit();
  }
  
  public void setTestMiniQBEnv(boolean paramBoolean)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        mSmttServicePartner.setTestMiniQBEnv(paramBoolean);
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void setVPNConnected(boolean paramBoolean)
  {
    this.mVpnState = paramBoolean;
  }
  
  public void setVisitorUrl(final String paramString1, final String paramString2, final String paramString3, final String paramString4, final String paramString5)
  {
    if (!getInstance().getCrashReportEnabled()) {
      return;
    }
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        SmttServiceProxy.this.initSharedPreferenceIfNeeded();
        SharedPreferences.Editor localEditor = SmttServiceProxy.this.mPreference.edit();
        localEditor.putString("render_upload_type", paramString1);
        localEditor.putString("render_upload_info", paramString2);
        localEditor.putString("render_upload_url", paramString3);
        localEditor.putString("render_upload_gpu", paramString4);
        localEditor.putString("render_upload_qua", paramString5);
        localEditor.commit();
      }
    });
  }
  
  public void setWupAddressByForce(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.setWupAddressByForce(paramString);
  }
  
  public void setWupProxyUrl(String paramString)
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putString("wup_proxy_url", paramString);
    localEditor.commit();
  }
  
  public boolean shouldConvertToHttpsForThisDomain(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.shouldConvertToHttpsForThisDomain(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean shouldEnableSessionPersistentForThisDomain(String paramString)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return false;
      }
      boolean bool = mSmttServiceMtt.shouldEnableSessionPersistentForThisDomain(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean shouldInterceptJsapiRequest()
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return false;
      }
      boolean bool = mSmttServiceCommon.shouldInterceptJsapiRequest();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean shouldLongClickExplorerByDomains(String paramString)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        boolean bool = mSmttServicePartner.shouldLongClickExplorerByDomains(paramString);
        return bool;
      }
    }
    catch (NoSuchMethodError paramString)
    {
      paramString.printStackTrace();
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean shouldNotExecuteSmartAdFilter(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return true;
    }
    return localISmttServiceCommon.shouldNotExecuteSmartAdFilter(paramString);
  }
  
  public boolean shouldPopupHideAdDialogByUrl(String paramString)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return false;
      }
      boolean bool = mSmttServiceMtt.shouldPopupHideAdDialogByUrl(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean shouldReportSearchVideoForDomain(String paramString)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return false;
      }
      boolean bool = mSmttServiceMtt.shouldReportSearchVideoForDomain(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean shouldSupportQuicDirect(String paramString)
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        boolean bool = mSmttServiceCommon.shouldSupportQuicDirect(paramString);
        return bool;
      }
    }
    catch (NoSuchMethodError paramString)
    {
      paramString.printStackTrace();
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return true;
  }
  
  public boolean shouldSupportTpgDec(String paramString)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return true;
      }
      boolean bool = mSmttServiceCommon.shouldSupportTpgDec(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return true;
  }
  
  public boolean shouldUseTbsAudioPlayerWithNoClick(String paramString)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return false;
      }
      boolean bool = mSmttServiceMtt.shouldUseTbsAudioPlayerWithNoClick(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public boolean shouldUseTbsMediaPlayer(String paramString)
  {
    try
    {
      if (mSmttServiceMtt == null) {
        return false;
      }
      boolean bool = mSmttServiceMtt.shouldUseTbsMediaPlayer(paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public void showNetExceptionTip(String paramString)
  {
    showToast(paramString, 1);
  }
  
  public int showScreenAd(Context paramContext, IX5WebView paramIX5WebView, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return -1;
      }
      int i = mSmttServiceCommon.showScreenAd(paramContext, paramIX5WebView, paramString, paramBoolean1, paramBoolean2);
      return i;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return -1;
  }
  
  public void showToast(final String paramString, final int paramInt)
  {
    if (isMainThread())
    {
      showToastInUIThread(paramString, paramInt);
      return;
    }
    new Handler(Looper.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what == 34918) {
          SmttServiceProxy.this.showToastInUIThread(paramString, paramInt);
        }
      }
    }.obtainMessage(34918).sendToTarget();
  }
  
  public void snapshotScreenAd(Bitmap paramBitmap, float paramFloat1, float paramFloat2, IX5WebView paramIX5WebView)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.snapshotScreenAd(paramBitmap, paramFloat1, paramFloat2, paramIX5WebView);
      return;
    }
    catch (Exception paramBitmap)
    {
      paramBitmap.printStackTrace();
    }
  }
  
  public int startDownloadAndInstallQbIfNeeded(Context paramContext, String paramString1, String paramString2, String paramString3, Bundle paramBundle)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        int i = mSmttServicePartner.startDownloadQb(paramContext, paramString1, paramString2, paramString3, paramBundle);
        return i;
      }
    }
    catch (NoSuchMethodError paramContext)
    {
      paramContext.printStackTrace();
      paramString1 = new StringBuilder();
      paramString1.append("startDownloadAndInstallQbIfNeeded NoSuchMethodError=");
      paramString1.append(paramContext.toString());
      Log.e("QBDownloadManager", paramString1.toString());
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
      paramString1 = new StringBuilder();
      paramString1.append("startDownloadAndInstallQbIfNeeded exception=");
      paramString1.append(paramContext.toString());
      Log.e("QBDownloadManager", paramString1.toString());
    }
    return -5;
  }
  
  public void startDownloadInMiniQB(Context paramContext, String paramString1, String paramString2, byte[] paramArrayOfByte, String paramString3, String paramString4, String paramString5, long paramLong, String paramString6, String paramString7)
  {
    ISmttServicePartner localISmttServicePartner = mSmttServicePartner;
    if (localISmttServicePartner == null) {
      return;
    }
    localISmttServicePartner.startDownloadInMiniQB(paramContext, paramString1, paramString2, paramArrayOfByte, paramString3, paramString4, paramString5, paramLong, paramString6, paramString7);
  }
  
  public int startMiniQB(Context paramContext, String paramString1, String paramString2)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        int i = mSmttServicePartner.startMiniQB(paramContext, paramString1, paramString2);
        return i;
      }
    }
    catch (NoSuchMethodError paramContext)
    {
      paramContext.printStackTrace();
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return -1;
  }
  
  public int startMiniQB(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        int i = mSmttServicePartner.startMiniQB(paramContext, paramString1, paramString2, paramMap);
        return i;
      }
    }
    catch (NoSuchMethodError paramContext)
    {
      paramContext.printStackTrace();
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return -1;
  }
  
  public int startMiniQB(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap, ValueCallback<String> paramValueCallback)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        int i = mSmttServicePartner.startMiniQB(paramContext, paramString1, paramString2, paramMap, paramValueCallback);
        return i;
      }
    }
    catch (NoSuchMethodError paramContext)
    {
      paramContext.printStackTrace();
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return -1;
  }
  
  public int startMiniQBWithImages(Context paramContext, Map<String, Bitmap> paramMap, Map<String, Boolean> paramMap1, int paramInt, String paramString)
  {
    try
    {
      if (mSmttServicePartner != null)
      {
        paramInt = mSmttServicePartner.startMiniQBWithImages(paramContext, paramMap, paramMap1, paramInt, paramString);
        return paramInt;
      }
    }
    catch (NoSuchMethodError paramContext)
    {
      paramContext.printStackTrace();
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return -1;
  }
  
  public int startQBWeb(Context paramContext, String paramString1, String paramString2, int paramInt)
  {
    return startQBWebAndRecordAnchor(paramContext, paramString1, paramString2, paramInt, new Point(0, 0), new Point(0, 0));
  }
  
  public int startQBWebAndRecordAnchor(Context paramContext, String paramString1, String paramString2, int paramInt, Point paramPoint1, Point paramPoint2)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return 0;
      }
      paramInt = mSmttServicePartner.startQBWeb(paramContext, paramString1, paramString2, paramInt, paramPoint1, paramPoint2);
      return paramInt;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return 0;
  }
  
  public int startQBWebWithNightFullscreenMode(Context paramContext, String paramString, int paramInt)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return 0;
      }
      paramInt = mSmttServicePartner.startQBWebWithNightFullscreenMode(paramContext, paramString, paramInt);
      return paramInt;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return 0;
  }
  
  public void stopCloudRecognition()
  {
    try
    {
      if (mSmttServiceCommon != null)
      {
        mSmttServiceCommon.stopCloudRecognition();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void stopTrace(String paramString)
  {
    TencentTraceEvent.stopTrace("/sdcard/tracing.data");
    Log.e("trace", MttLog.getLocalLogDir().getAbsolutePath());
    paramString = new StringBuilder();
    paramString.append(SmttResource.getString("x5_trace_saved", "string"));
    paramString.append("/sdcard/tracing.data");
    showToast(paramString.toString(), 0);
  }
  
  public boolean switchPluginDebugEnv()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      boolean bool = mSmttServicePartner.switchPluginDebugEnv();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public Bundle translate(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return null;
      }
      paramString1 = mSmttServicePartner.translate(paramString1, paramString2, paramString3, paramString4);
      return paramString1;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    return null;
  }
  
  public void upLoadToBeacon(String paramString, Map<String, String> paramMap)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.upLoadToBeacon(paramString, paramMap);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void uploadCrashMsgInfoToBeacon()
  {
    if (!getInstance().getCrashReportEnabled()) {
      return;
    }
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      /* Error */
      public void doRun()
      {
        // Byte code:
        //   0: invokestatic 27	org/chromium/base/CommandLine:getInstance	()Lorg/chromium/base/CommandLine;
        //   3: ldc 29
        //   5: invokevirtual 33	org/chromium/base/CommandLine:hasSwitch	(Ljava/lang/String;)Z
        //   8: ifeq +198 -> 206
        //   11: invokestatic 27	org/chromium/base/CommandLine:getInstance	()Lorg/chromium/base/CommandLine;
        //   14: ldc 29
        //   16: aconst_null
        //   17: invokevirtual 37	org/chromium/base/CommandLine:getSwitchValue	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   20: astore_1
        //   21: aload_1
        //   22: ifnonnull +4 -> 26
        //   25: return
        //   26: new 39	java/io/File
        //   29: dup
        //   30: aload_1
        //   31: invokespecial 42	java/io/File:<init>	(Ljava/lang/String;)V
        //   34: astore 4
        //   36: aload 4
        //   38: invokevirtual 46	java/io/File:exists	()Z
        //   41: ifne +4 -> 45
        //   44: return
        //   45: aload_0
        //   46: getfield 15	com/tencent/smtt/webkit/service/SmttServiceProxy$5:a	Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
        //   49: invokevirtual 49	com/tencent/smtt/webkit/service/SmttServiceProxy:uploadUrlInfoToBeacon	()Z
        //   52: pop
        //   53: invokestatic 52	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
        //   56: invokevirtual 55	com/tencent/smtt/webkit/service/SmttServiceProxy:getCrashUploadLiveLogEnabled	()Z
        //   59: ifne +10 -> 69
        //   62: aload 4
        //   64: invokevirtual 58	java/io/File:delete	()Z
        //   67: pop
        //   68: return
        //   69: new 60	java/io/BufferedReader
        //   72: dup
        //   73: new 62	java/io/FileReader
        //   76: dup
        //   77: aload 4
        //   79: invokespecial 65	java/io/FileReader:<init>	(Ljava/io/File;)V
        //   82: invokespecial 68	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
        //   85: astore_2
        //   86: aload_2
        //   87: astore_1
        //   88: aload_2
        //   89: invokevirtual 72	java/io/BufferedReader:readLine	()Ljava/lang/String;
        //   92: astore_3
        //   93: aload_3
        //   94: ifnull +15 -> 109
        //   97: aload_2
        //   98: astore_1
        //   99: invokestatic 77	com/tencent/smtt/livelog/a:a	()Lcom/tencent/smtt/livelog/a;
        //   102: aload_3
        //   103: invokevirtual 79	com/tencent/smtt/livelog/a:a	(Ljava/lang/String;)V
        //   106: goto +11 -> 117
        //   109: aload_2
        //   110: astore_1
        //   111: invokestatic 77	com/tencent/smtt/livelog/a:a	()Lcom/tencent/smtt/livelog/a;
        //   114: invokevirtual 82	com/tencent/smtt/livelog/a:b	()V
        //   117: aload_2
        //   118: invokevirtual 85	java/io/BufferedReader:close	()V
        //   121: aload 4
        //   123: invokevirtual 58	java/io/File:delete	()Z
        //   126: pop
        //   127: return
        //   128: astore_1
        //   129: aload 4
        //   131: invokevirtual 58	java/io/File:delete	()Z
        //   134: pop
        //   135: aload_1
        //   136: athrow
        //   137: astore_3
        //   138: goto +12 -> 150
        //   141: astore_2
        //   142: aconst_null
        //   143: astore_1
        //   144: goto +34 -> 178
        //   147: astore_3
        //   148: aconst_null
        //   149: astore_2
        //   150: aload_2
        //   151: astore_1
        //   152: aload_3
        //   153: invokevirtual 88	java/io/IOException:printStackTrace	()V
        //   156: aload_2
        //   157: ifnull +19 -> 176
        //   160: aload_2
        //   161: invokevirtual 85	java/io/BufferedReader:close	()V
        //   164: goto -43 -> 121
        //   167: astore_1
        //   168: aload 4
        //   170: invokevirtual 58	java/io/File:delete	()Z
        //   173: pop
        //   174: aload_1
        //   175: athrow
        //   176: return
        //   177: astore_2
        //   178: aload_1
        //   179: ifnull +25 -> 204
        //   182: aload_1
        //   183: invokevirtual 85	java/io/BufferedReader:close	()V
        //   186: aload 4
        //   188: invokevirtual 58	java/io/File:delete	()Z
        //   191: pop
        //   192: goto +12 -> 204
        //   195: astore_1
        //   196: aload 4
        //   198: invokevirtual 58	java/io/File:delete	()Z
        //   201: pop
        //   202: aload_1
        //   203: athrow
        //   204: aload_2
        //   205: athrow
        //   206: return
        //   207: astore_1
        //   208: goto -87 -> 121
        //   211: astore_1
        //   212: goto -26 -> 186
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	215	0	this	5
        //   20	91	1	localObject1	Object
        //   128	8	1	localObject2	Object
        //   143	9	1	localObject3	Object
        //   167	16	1	localObject4	Object
        //   195	8	1	localObject5	Object
        //   207	1	1	localIOException1	java.io.IOException
        //   211	1	1	localIOException2	java.io.IOException
        //   85	33	2	localBufferedReader	java.io.BufferedReader
        //   141	1	2	localObject6	Object
        //   149	12	2	localObject7	Object
        //   177	28	2	localObject8	Object
        //   92	11	3	str	String
        //   137	1	3	localIOException3	java.io.IOException
        //   147	6	3	localIOException4	java.io.IOException
        //   34	163	4	localFile	File
        // Exception table:
        //   from	to	target	type
        //   117	121	128	finally
        //   88	93	137	java/io/IOException
        //   99	106	137	java/io/IOException
        //   111	117	137	java/io/IOException
        //   69	86	141	finally
        //   69	86	147	java/io/IOException
        //   160	164	167	finally
        //   88	93	177	finally
        //   99	106	177	finally
        //   111	117	177	finally
        //   152	156	177	finally
        //   182	186	195	finally
        //   117	121	207	java/io/IOException
        //   160	164	207	java/io/IOException
        //   182	186	211	java/io/IOException
      }
    });
  }
  
  public void uploadDeadCodeToBeaconIfNeeded(boolean paramBoolean1, final boolean paramBoolean2)
  {
    if (!e.a()) {
      return;
    }
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        if (SmttServiceProxy.this.getDeadCodeUploadBeaconEnabled())
        {
          SmttServiceProxy.this.initSharedPreferenceIfNeeded();
          Object localObject1 = SmttServiceProxy.this.mPreference.getString("deadcode_lc_buildno", "0000_0000");
          Object localObject2 = SmttServiceProxy.this.getLCAndBuildNO();
          boolean bool2 = SmttServiceProxy.this.mPreference.getBoolean("deadcode_should_ignore", false);
          if (!((String)localObject1).equalsIgnoreCase((String)localObject2))
          {
            SmttServiceProxy.this.clearDeadCode();
            return;
          }
          localObject2 = SmttServiceProxy.this.mPreference.getString("deadcode_heartbeat_report_info", "");
          String str3 = SmttServiceProxy.this.mPreference.getString("deadcode_heartbeat_stack", "");
          Object localObject3 = SmttServiceProxy.this.mPreference.getString("deadcode_other_report_info", "");
          String str4 = SmttServiceProxy.this.mPreference.getString("deadcode_other_stack", "");
          String str1 = SmttServiceProxy.this.mPreference.getString("deadcode_exit_time", "");
          Boolean localBoolean = Boolean.valueOf(SmttServiceProxy.this.mPreference.getBoolean("deadcode_is_crash", false));
          localObject1 = localObject2;
          if (!"".equals(localObject2))
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append(str1);
            ((StringBuilder)localObject1).append((String)localObject2);
            localObject1 = ((StringBuilder)localObject1).toString();
          }
          localObject2 = localObject3;
          if (!"".equals(localObject3))
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append(str1);
            ((StringBuilder)localObject2).append((String)localObject3);
            localObject2 = ((StringBuilder)localObject2).toString();
          }
          SmttServiceProxy.this.clearDeadCode();
          String str2 = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_LC);
          str1 = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_BUILD_NO);
          String str5 = SmttServiceProxy.getInstance().getQUA2();
          if (str2 != null)
          {
            localObject3 = str2;
            if (!"".equalsIgnoreCase(str2)) {}
          }
          else
          {
            localObject3 = "0000";
          }
          if ((str1 != null) && (!"".equalsIgnoreCase(str1))) {
            break label351;
          }
          str1 = "0000";
          label351:
          boolean bool1 = SmttServiceProxy.this.uploadDeadCodeToBeacon((String)localObject1, str3, localBoolean, (String)localObject3, str1, str5, Boolean.valueOf(bool2));
          bool2 = SmttServiceProxy.this.uploadDeadCodeToBeacon((String)localObject2, str4, localBoolean, (String)localObject3, str1, str5, Boolean.valueOf(bool2));
          if ((bool1) || (bool2))
          {
            if (e.a().n())
            {
              if ((!paramBoolean2) && (!SmttServiceProxy.this.getDeadCodeUploadDumpEnabled()))
              {
                MttTimingLog.packDump(MttLog.getLocalDumpDirTbs());
                return;
              }
              MttTimingLog.packAndUploadDump(MttLog.getLocalDumpDirTbs());
              return;
            }
            if (SmttServiceProxy.this.getDeadCodeUploadDumpEnabled())
            {
              MttTimingLog.packAndUploadDump(MttLog.getLocalDumpDirTbs());
              return;
            }
            MttTimingLog.packDump(MttLog.getLocalDumpDirTbs());
          }
        }
      }
    });
  }
  
  public void uploadFixedADFilteredNumIfNeeded(final String paramString, final Map<String, String> paramMap)
  {
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        SmttServiceProxy.this.initSharedPreferenceIfNeeded();
        int i = SmttServiceProxy.this.mPreference.getInt("fixed_ad_filtered_num", 0);
        if (i <= 0) {
          return;
        }
        Object localObject = SmttServiceProxy.this.mPreference.edit();
        ((SharedPreferences.Editor)localObject).putInt("fixed_ad_filtered_num", 0);
        ((SharedPreferences.Editor)localObject).commit();
        localObject = new HashMap();
        ((HashMap)localObject).put("filtered_num", String.valueOf(i));
        Iterator localIterator = paramMap.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          ((HashMap)localObject).put(localEntry.getKey(), localEntry.getValue());
        }
        SmttServiceProxy.getInstance().upLoadToBeacon(paramString, (Map)localObject);
      }
    });
  }
  
  public boolean uploadPageErrorMetaInfo(String paramString1, int paramInt, String paramString2, String paramString3, boolean paramBoolean, String paramString4, String paramString5)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return false;
      }
      paramBoolean = mSmttServicePartner.uploadPageErrorMetaInfo(paramString1, paramInt, paramString2, paramString3, paramBoolean, paramString4, paramString5);
      return paramBoolean;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    return false;
  }
  
  public boolean uploadPageErrorMetaInfo(String paramString1, int paramInt1, String paramString2, String paramString3, boolean paramBoolean1, String paramString4, String paramString5, boolean paramBoolean2, boolean paramBoolean3, String paramString6, int paramInt2, String paramString7, boolean paramBoolean4, int paramInt3, int paramInt4, long paramLong, boolean paramBoolean5, int paramInt5)
  {
    try
    {
      if ((mSmttServiceMtt == null) && (mSmttServicePartner == null)) {
        return false;
      }
      if (mSmttServiceMtt != null) {
        return mSmttServiceMtt.uploadPageErrorMetaInfo(paramString1, paramInt1, paramString2, paramString3, paramBoolean1, paramString4, paramString5, paramBoolean2, paramBoolean3, paramString6, paramInt2, paramString7, paramBoolean4, paramInt3, paramInt4, paramLong, paramBoolean5, paramInt5);
      }
      if (mSmttServicePartner != null)
      {
        paramBoolean1 = mSmttServicePartner.uploadPageErrorMetaInfo(paramString1, paramInt1, paramString2, paramString3, paramBoolean1, paramString4, paramString5);
        return paramBoolean1;
      }
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    return false;
  }
  
  public void uploadTbsAdStatsRealTime(String paramString, Map<String, String> paramMap)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.uploadTbsAdStatsRealTime(paramString, paramMap);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void uploadTbsDirectInfoToBeacon(Map<String, String> paramMap)
  {
    try
    {
      if (mSmttServiceCommon == null) {
        return;
      }
      mSmttServiceCommon.uploadTbsDirectInfoToBeacon(paramMap);
      return;
    }
    catch (Exception paramMap)
    {
      paramMap.printStackTrace();
    }
  }
  
  public void uploadTrace()
  {
    File localFile = new File("//sdcard//tracing.data");
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(MttLog.getLocalLogDir().getAbsolutePath());
    ((StringBuilder)localObject).append("/tracing.data");
    localObject = new File(((StringBuilder)localObject).toString());
    if (localFile.exists())
    {
      FileUtils.a(localFile, (File)localObject);
      return;
    }
    showToast(SmttResource.getString("x5_trace_file_not_exist", "string"), 0);
  }
  
  public void uploadUgLog()
  {
    try
    {
      if (mSmttServicePartner == null) {
        return;
      }
      mSmttServicePartner.uploadUgLog();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public boolean uploadUrlInfoToBeacon()
  {
    if (!getInstance().getCrashReportEnabled()) {
      return false;
    }
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        SmttServiceProxy.this.initSharedPreferenceIfNeeded();
        Object localObject = SmttServiceProxy.this.mPreference.getString("render_upload_info", "");
        if ("".equals(localObject)) {
          return;
        }
        HashMap localHashMap = new HashMap();
        String str1 = SmttServiceProxy.this.mPreference.getString("render_upload_type", "");
        String str2 = SmttServiceProxy.this.mPreference.getString("render_upload_url", "");
        String str3 = SmttServiceProxy.this.mPreference.getString("render_upload_gpu", "");
        String str4 = SmttServiceProxy.this.mPreference.getString("render_upload_qua", "");
        localHashMap.put("url", str2);
        localHashMap.put("type", str1);
        localHashMap.put("msg", localObject);
        localHashMap.put("gpu", str3);
        localHashMap.put("qua2", str4);
        SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_RENDER_ERROR_INFO_EX", localHashMap);
        localObject = SmttServiceProxy.this.mPreference.edit();
        ((SharedPreferences.Editor)localObject).remove("render_upload_type");
        ((SharedPreferences.Editor)localObject).remove("render_upload_info");
        ((SharedPreferences.Editor)localObject).remove("render_upload_url");
        ((SharedPreferences.Editor)localObject).remove("render_upload_gpu");
        ((SharedPreferences.Editor)localObject).remove("render_upload_qua");
        ((SharedPreferences.Editor)localObject).commit();
      }
    });
    return true;
  }
  
  public int useDownloadInterceptPlugin(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong, String paramString6, String paramString7, String paramString8, String paramString9, byte[] paramArrayOfByte, IX5WebViewBase paramIX5WebViewBase, Object paramObject, String paramString10)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return -1;
      }
      int i = mSmttServicePartner.useDownloadInterceptPlugin(paramContext, paramString1, paramString2, paramString3, paramString4, paramString5, paramLong, paramString6, paramString7, paramString8, paramString9, paramArrayOfByte, paramIX5WebViewBase, paramObject, paramString10);
      return i;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return -1;
  }
  
  public boolean useNewAdBlockOrOld()
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return false;
    }
    return localISmttServiceCommon.canUseAdBlockWhenNotUsingQProxy();
  }
  
  public void useQBWebLoadUrl(String paramString)
  {
    try
    {
      if (mSmttServicePartner == null) {
        return;
      }
      mSmttServicePartner.useQBWebLoadUrl(paramString);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void userBehaviorStatistics(String paramString)
  {
    ISmttServiceCommon localISmttServiceCommon = mSmttServiceCommon;
    if (localISmttServiceCommon == null) {
      return;
    }
    localISmttServiceCommon.userBehaviorStatistics(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\service\SmttServiceProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */