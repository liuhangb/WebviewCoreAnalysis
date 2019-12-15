package com.tencent.tbs.common.settings;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.ICoreInfoFetcher;
import com.tencent.tbs.common.baseinfo.ICoreService;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.lbs.DonutAdapter;
import com.tencent.tbs.common.service.SntpClient;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.utils.QBUrlUtils;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublicSettingManager
{
  public static final int BG_TYPE_NORMAL_BMP = 2;
  public static final int BG_TYPE_PURE_COLOR = 1;
  public static final int CLEAR_ALL_CACHE = 5;
  public static final int CLEAR_COOKIE = 4;
  public static final int CLEAR_DNS_CACHE = 2;
  public static final int CLEAR_FILE_CACHE = 1;
  public static final int CLEAR_NONE = 0;
  public static final int CLEAR_WEB_STORAGE = 3;
  static final String DEFAULT_JUMPURL = "http://res.imtt.qq.com/smartbanner/wifi_saitest_v42/index.html#/list";
  public static final int FIRST_START_DYNAMIC_JAR_VERSION = 88;
  private static final String KEY_ACCUMULATION_VALUE_PV = "key_accumulation_value_pv";
  private static final String KEY_ACCUMULATION_VALUE_UB = "key_accumulation_value_ub";
  public static final String KEY_ADD_SPECIAL_ARGUMENT_ENABLED = "key_add_special_argument_enabled";
  private static final String KEY_AD_SPECIAL_PERMISSION = "key_tbs_ad_special_permission";
  private static final String KEY_ALLOW_DNS_STORE = "key_allow_dns_store";
  private static final String KEY_ANDROID_ACCELERATED_2D_CANVAS = "key_android_accelerated_2d_canvas";
  private static final String KEY_ANDROID_ENABLE_QUA1 = "key_android_enable_qua1";
  private static final String KEY_ANDROID_ENABLE_QUA2_V3 = "key_android_enable_qua2_v3";
  private static final String KEY_ANDROID_GPU_RASTERIZATION = "key_android_gpu_rasterization";
  private static final String KEY_ANDROID_TBSLOG_CACHE_STRATEGY_KEY = "key_tbslog_cache_strategy_prefs";
  private static final String KEY_ANDROID_TBSLOG_ENABLE_KEY = "key_tbslog_enable_prefs";
  private static final String KEY_ANDROID_TBSLOG_FLUSH_STRATEGY_KEY = "key_tbslog_flush_strategy_prefs";
  private static final String KEY_ANDROID_TBSLOG_REPORT_KEY = "key_tbslog_report_prefs";
  private static final String KEY_ANDROID_UPLOAD_TEXTURE_MODE = "key_android_upload_texture_mode";
  private static final String KEY_ANDROID_WEBGL = "key_android_webgl";
  private static final String KEY_AUTO_PAGE_DISCARDING_DEFAULT_ENABLED = "key_auto_page_discarding_defalut_enabled";
  private static final String KEY_AUTO_PAGE_DISCARDING_ENABLED = "key_auto_page_discarding_enabled";
  private static final String KEY_AUTO_PAGE_RESTORATION_DEFAULT_ENABLED = "key_auto_page_restoration_defalut_enabled";
  private static final String KEY_AUTO_PAGE_RESTORATION_ENABLED = "key_auto_page_restoration_enabled";
  private static final String KEY_BDHD_SUPPORT_TYPE = "key_bdhd_support_type";
  private static final String KEY_BDSEARCH_PREDICT_ENABLED = "key_bdsearch_predict_enabled";
  private static final String KEY_BEACON_UPLOAD_ENABLE = "key_beacon_upload_enable";
  public static final String KEY_BRAND_INFO_CACHE = "key_brand_info_cache";
  private static final String KEY_BUBBLE_AD = "key_bubble_ad";
  private static final String KEY_BUBBLE_MINIQB_AD = "key_bubble_miniqb_ad";
  private static final String KEY_BUGLY_ENABLE = "key_bugly_enable";
  private static final String KEY_CALL_WEBVIEW_API_ON_WRONG_THREAD = "key_call_webview_api_on_wrong_thread";
  private static final String KEY_CAN_AIA_EXTENSION = "key_can_aia_extension";
  private static final String KEY_CAN_CONVERT_DOWNLOADFILE_INTERCEPTTHEESWITCH = "key_download_intercept_switch";
  private static final String KEY_CAN_CONVERT_GET_TO_POST = "key_can_convert_get_to_post";
  private static final String KEY_CAN_DOWNLOADINTERCEPTFILETYPE = "key_download_intercept_filetype";
  private static final String KEY_CAN_INSERT_AD_IN_SPECIALSITE = "key_can_insert_ad_in_specialsite";
  private static final String KEY_CAN_IPV6_PROXY = "key_can_ipv6_proxy";
  private static final String KEY_CAN_USE_ADBLOCK_UNDER_DIRECT = "key_can_use_adblock_under_direct";
  private static final String KEY_CAN_USE_DYNAMIC_CANVAS_GPU = "key_can_use_dynamic_canvas_gpu";
  private static final String KEY_CAN_USE_QPROXY_UNDER_PROXY = "key_can_use_qproxy_under_proxy";
  private static final String KEY_CHECK_X5COOKIE_PATH_POLICY_ENABLE = "key_check_x5cookie_path_policy_enable";
  private static final String KEY_COMMON_CONFIG_LIST_CONTENT = "common_config_list_content";
  private static final String KEY_COMMON_CONFIG_LIST_MD5 = "common_config_list_md5";
  private static final String KEY_COMMON_CONFIG_LIST_TIME = "common_config_list_time";
  private static final String KEY_CONF_BROWSER_LIST_DIALOG_STYLE = "key_conf_browser_list_dialog_style";
  private static final String KEY_CONTENT_CACHE_ENABLED = "key_content_cache_enabled";
  private static final String KEY_CORE_FIRSTSCREEN_DETECT = "key_first_screen_x5core_detect";
  private static final String KEY_CORE_FIRSTSCREEN_DRAW_FULLSCREEN = "key_first_screen_draw_fullscreen";
  private static final String KEY_CORE_FIRSTSCREEN_DRAW_NOTFULLSCREEN = "key_first_screen_draw_notfullscreen";
  private static final String KEY_DIRECT_ADBLOCK_SWITCHER_LEVEL = "key_direct_adblock_switcher_level";
  private static final String KEY_DISK_CACHE_SIZE = "key_disk_cache_size";
  private static final String KEY_DOWLOAD_SIZE_THRESHOLD = "key_dowload_size_threshold";
  private static final String KEY_DOWNLOADINQB_ENABLE = "key_downloadinqb_enable";
  private static final String KEY_DOWNLOAD_INTERCEPT_TO_QB_POP_MENU_STYLE = "key_download_intercept_to_qb_pop_menu_style";
  private static final String KEY_DOWNLOAD_IN_TENCENTFILE_ENABLE = "key_download_in_tencentfile_enable";
  private static final String KEY_ENABLE_LOGS = "key_enable_logs";
  private static final String KEY_ENABLE_SUBRESOURCE_PERFORMANCE = "key_enable_subresource_performance";
  private static final String KEY_ENVIRONMENT_TYPE = "key_environment_type";
  private static final String KEY_EXPLORER_CHARACTER = "key_explorer_character";
  private static final String KEY_EXTEND_RULE_MD5 = "key_extend_rule_md5";
  private static final String KEY_EXTEND_RULE_UPDATED = "key_extend_rule_pdated";
  private static final String KEY_EXTEND_RULE_VERSION = "key_extend_rule_version";
  private static final String KEY_FACE_ENGINE_NAME = "key_face_engine_name";
  private static final String KEY_GDT_ADVERTISEMENT = "key_gdt_advertisement";
  private static final String KEY_GIN_JAVA_MAP_ERASE_DISABLE = "key_gin_java_map_erase_disable";
  private static final String KEY_GREAT_KING_OF_SIMCARD_SWICTH = "key_great_king_of_simcard";
  private static final String KEY_GREAT_KING_OF_SIMCARD_SWICTH_PROXY_STRAGETY = "key_kc_proxy_stragety";
  private static final String KEY_HAS_EVER_LOGIN = "key_has_ever_login";
  private static final String KEY_HAS_REPORT_LAUNCHER_PKG_NAME = "key_has_report_launcher_pkg_name";
  private static final String KEY_HAVE_PLUGIN_PUSH_REQUEST = "key_have_plugin_push_request";
  private static final String KEY_HITRATE_REPORT_ENABLED = "key_hitrate_report_enabled";
  private static final String KEY_HTTPS_0RTT = "key_https_0rtt";
  private static final String KEY_INTERCEPT_DIDFAIL_ONPAGEFINISHED = "key_intercept_didfail_onpagefinished";
  private static final String KEY_ISINWUP_UPLOAD_PV = "key_isinwup_upload_pv";
  private static final String KEY_IS_SCHEME_CALL_APP_BY_USER = "key_is_scheme_call_app_by_user";
  private static final String KEY_IS_USE_THIRDPARTY_ADRULES = "key_is_use_thirdparty_adrules";
  private static final String KEY_JNI_REGISTER_ENABLED = "key_jni_register_enabled";
  private static final String KEY_JSON_SIZE_FOR_PV = "key_json_size_for_pv";
  private static final String KEY_LAST_CHECK_BASIC_CONFIG_DATE = "key_last_check_basic_config_date";
  private static final String KEY_LAST_CHECK_WUP_CONFIG_DATE = "key_last_check_wup_config_date";
  private static final String KEY_LAST_MINIQB_VERSION_FOR_PREFERENCE = "key_last_miniqb_version_for_prefs";
  public static final String KEY_LAST_MODIFY_WUP_ENCRYPT_TIME = "key_last_modify_wup_encrypt_time";
  private static final String KEY_LAST_REQ_FROM_TEST_ENV = "key_last_req_from_test_evn_";
  private static final String KEY_LAST_RESOLVE_WUP_ADDRESS = "key_last_resovled_wup_address";
  private static final String KEY_LAST_SYSTEM_TIME = "key_last_system_time";
  private static final String KEY_LAST_WUP_DOMAIN_LIST_FROM_TEST_SERVER = "key_last_wup_domain_list_from_wup_server";
  private static final String KEY_LONG_CLICK_POPUP_NEMU_SUBTEXT = "key_long_click_popup_menu_subtext";
  private static final String KEY_LONG_PRESS_TO_QB_POP_MENU_STYLE = "key_long_press_to_qb_pop_menu_style";
  private static final String KEY_MARKER_ENGINE_NAME = "key_marker_engine_name";
  private static final String KEY_MINIQB_STAT_VERSION_NAME = "key_miniqb_stat_version_name";
  private static final String KEY_MINI_QB_PREFERENCE_UPDATE_TIME = "key_miniqb_preference_update_time";
  private static final String KEY_MSE_ENABLED = "key_mse_enabled";
  public static final String KEY_MTT_CORE_PAGE_DURATION = "key_mtt_core_page_duration";
  private static final String KEY_MYVIDEO_SHOW_GUESS_YOUR_FAV = "key_myvideo_show_guess_your_fav";
  private static final String KEY_NIGHT_MODE_IN_LONG_PRESS_THREE_SWITCH = "key_night_mode_in_long_press_switch";
  private static final String KEY_NOTIFY_DOWNLOAD_QB_TIMES_LIMIT = "key_notify_download_qb_times_limit";
  private static final String KEY_OPEN_WIFI_PROXY_WUP = "key_open_wifi_proxy_wup";
  public static final String KEY_PERFORMANCE_RECORD_ENABLED = "key_performance_record_enabled";
  private static final String KEY_PLUGINLIST_FIRST_START_QQBROWSER = "key_pluginlist_last_pull_pluignList";
  private static final String KEY_PLUGIN_LIST_MD5 = "key_plugin_list_md5";
  private static final String KEY_PLUGIN_LIST_SUCC = "key_plugin_list_succ2170";
  private static final String KEY_PREFERENCE_GPU_STATE = "key_prefernce_gpu_state";
  private static final String KEY_PREFERENCE_TYPE_ENABLE_RSA_AES_ENCRYPT = "key_preference_enable_rsa_aes_encrypt";
  private static final String KEY_PREFERENCE_UPDATE_TIME = "key_preference_update_time";
  private static final String KEY_PREFFIX_MINIQB_DOMAIN = "miniqb_pref_domain";
  private static final String KEY_PREFFIX_MINIQB_PREF = "miniqb_pref_pref";
  private static final String KEY_PRERENDER_PKG_NAMES = "key_prerender_package_names";
  private static final String KEY_PRERENDER_SWITCH = "key_prerender_swtich";
  private static final String KEY_QBICON_QQ_SHINE = "key_qbicon_qq_shine";
  private static final String KEY_QB_ICON_TYPE_IN_LONG_CLICK_POPUP = "key_qb_icon_type_in_long_click_popup";
  private static final String KEY_QQ_INTERRUPT_APK_TYPE = "key_qq_interrupt_apk_type";
  private static final String KEY_QQ_INTERRUPT_NOT_APK_TYPE = "key_qq_interrupt_not_apk_type";
  private static final String KEY_QUIC_PROXY_SWICTH = "key_quic_proxy_switch";
  private static final String KEY_QVOD_SUPPORT_TYPE = "key_qvod_support_type";
  private static final String KEY_REPORT_FAILED_TBS_GUID = "key_report_failed_tbs_guid";
  private static final String KEY_REPORT_LAUNCHER_PKG_NAME = "key_report_launcher_pkg_name";
  private static final String KEY_REQUEST_FAILED_TIME_PRFIX = "key_request_failed_time_prefix";
  private static final String KEY_RESET_DB_TABLE_PLUGIN = "key_reset_db_table_plugin";
  private static final String KEY_RESET_DB_TABLE_PLUGIN5_3 = "key_reset_db_table_plugin_5_3";
  private static final String KEY_SAVE_WEBPAGE_IN_LONG_PRESS_THREE_SWITCH = "key_save_webpage_in_long_press_switch";
  private static final String KEY_SDCARD_DISK_CACHE_ENABLED = "key_sdcard_disk_cache_enabled";
  public static final String KEY_SEARCH_ENGINE_SELECTED_SEARCH_URL = "key_search_engine_selected_search_url";
  private static final String KEY_SESSION_PERSISTENT = "key_session_persistent";
  private static final String KEY_SHARPP_DEFAULT = "key_sharpp_defalut_enabled";
  private static final String KEY_SHARPP_ENABLED = "key_sharpp_enabled";
  private static final String KEY_SHOULD_INTERCEPT_JSAPI_REQUEST = "key_should_intercept_jsapi_request";
  private static final String KEY_SHOULD_MERGER_QB_GUID = "key_should_merger_qb_guid";
  public static final String KEY_SNIFF_DISABLE_DOMAINS = "key_sniff_disable_domains";
  private static final String KEY_SNTP_TIME = "key_sntp_time";
  private static final String KEY_SPA_AD_PAGE_REPORT = "key_spa_ad_page_report";
  private static final String KEY_SPLICE_AD = "key_splice_ad";
  private static final String KEY_SPLICE_MINIQB_AD = "key_splice_miniqb_ad";
  private static final String KEY_SW_API_EXEC_RESULT_UPLOAD_LIST = "key_sw_api_exec_result_upload_list";
  private static final String KEY_SYSTEM_POP_MENU_STYLE = "key_stystem_pop_menu_style";
  private static final String KEY_TBSVIDEO_PREFERENCE_UPDATE_TIME = "key_tbsvideo_preference_update_time";
  private static final String KEY_TBS_AD_REINSTALL_TIPS_SHOW_TIME = "key_tbs_ad_reinstall_tips_show_time";
  private static final String KEY_TBS_ALLOW_LOADDATA_WITH_CSP = "key_tbs_allow_loadata_with_csp";
  private static final String KEY_TBS_CAN_QZONE_SWITCH_TO_MINIQB = "key_tbs_can_qzone_switch_to_miniqb";
  private static final String KEY_TBS_CAN_USE_X5_JSCORE = "key_tbs_can_use_x5_jscore";
  private static final String KEY_TBS_CAN_USE_X5_JSCORE_NEW_API = "key_tbs_can_use_x5_jscore_new_api";
  private static final String KEY_TBS_CLEAR_CACHE_COMMAND_VERSION = "key_tbs_clear_cache_command_version";
  private static final String KEY_TBS_FORMER_TBS_VERSION = "key_tbs_former_tbscore_version";
  private static final String KEY_TBS_GAME_FRAMEWORK_LOGIN_URL = "key_tbs_game_fw_login_url";
  private static final String KEY_TBS_GAME_FRAMEWORK_PAY_URL = "key_tbs_game_fw_pay_url";
  private static final String KEY_TBS_GAME_FRAMEWORK_SERVICE_ENV = "key_tbs_game_fw_service_env";
  private static final String KEY_TBS_GAME_FRAMEWORK_SHARE_URL = "key_tbs_game_fw_share_url";
  private static final String KEY_TBS_GAME_FRAMEWORK_USE_SANDBOX = "key_tbs_game_fw_sandbox";
  private static final String KEY_TBS_GENERAL_FEATURE_SWITCH_PREFIX = "key_tbs_general_feature_switch_";
  private static final String KEY_TBS_GENERAL_FEATURE_SWITCH_VERSION_PREFIX = "key_tbs_general_feature_switch_core_version_";
  private static final String KEY_TBS_GPRS_CONNECTION_TIMEOUT = "key_grps_to";
  private static final String KEY_TBS_HTTPDNS_ENCRYPTKEY = "key_tbs_httpdns_encryptkey";
  private static final String KEY_TBS_HTTPDNS_SERVER_IP = "key_tbs_httpdns_server_ip";
  private static final String KEY_TBS_IMPATIENT_NETWORK_SWITCH = "key_tbs_impatient_network_switch";
  private static final String KEY_TBS_INFO_UPLOAD_ARGUMENT_APN = "key_tbs_info_upload_argument_apn";
  private static final String KEY_TBS_INFO_UPLOAD_ARGUMENT_SWITCHER = "key_tbs_info_upload_argument_switcher";
  private static final String KEY_TBS_INFO_UPLOAD_NEEDPROXY = "key_tbs_info_upload_needproxy";
  private static final String KEY_TBS_PAGE_LOAD_INFO_UPLOAD_MAXCOUNT = "key_tbs_page_load_info_upload_maxcount";
  private static final String KEY_TBS_PICKED_DEFAULT_BROWSER = "key_tbs_picked_default_browser";
  private static final String KEY_TBS_PICKED_DEFAULT_FILE_OPENER = "key_tbs_picked_default_file_opener";
  private static final String KEY_TBS_RESTRICT_ERRORPAGE_RELOAD_SECOND = "key_tbs_restrict_errorpage_reload_second";
  private static final String KEY_TBS_WEBVIEW_SEARCH_ENGINE_URL = "key_tbs_webview_search_engine_url";
  private static final String KEY_TBS_WIFI_CONNECTION_TIMEOUT = "key_wifi_to";
  private static final String KEY_TEST_ENVIRONMENT_WUP_ADDRESS = "key_test_environment_wup_address";
  private static final String KEY_TPG_ENABLED = "key_tpg_enabled";
  private static final String KEY_UNSUCCESS_PLUGIN_PUSH_REQUEST_COUNT = "key_unsuccess_start_plugin_request_count";
  private static final String KEY_USER_ADHIDE_DIALOG_SWITCH = "key_user_adhide_dialog_switch";
  private static final String KEY_USE_SMART_ADFILTER = "key_use_smart_adfilter";
  private static final String KEY_VIDEO_IS_DEFAULT_FULLSCREEN = "key_video_is_default_fullscreen";
  private static final String KEY_VIDEO_MAXBUFFSZIE = "key_video_maxbuffsize";
  private static final String KEY_VIDEO_MINBUFFSZIE = "key_video_minbuffsize";
  private static final String KEY_VIDEO_PRELOAD = "key_video_preload";
  private static final String KEY_VIDEO_PRODUCT_JSAPI = "key_video_product_jsapi";
  private static final String KEY_VIDEO_PRODUCT_OPENQB = "key_video_product_openqb";
  private static final String KEY_VIDEO_SAME_LAYER = "key_video_same_layer";
  private static final String KEY_WASM_DISABLE = "key_wasm_disable";
  private static final String KEY_WEBAR_MARKER_DISABLED = "key_webar_marker_disabled";
  private static final String KEY_WEBAR_MARKER_ENABLED_ON_ALL_DEVICES = "key_webar_marker_enabled_on_all_devices";
  private static final String KEY_WEBAR_SLAM_DISABLED = "key_webar_slam_disabled";
  private static final String KEY_WEBAR_SLAM_ENABLED_ON_ALL_DEVICES = "key_webar_slam_enabled_on_all_devices";
  private static final String KEY_WEBAR_SLAM_HARDWARE_CONFIG = "key_webar_slam_hardware_config";
  private static final String KEY_WEBAR_SLAM_VIO_DISABLED = "key_webar_slam_vio_disabled";
  private static final String KEY_WEBAR_SWITCH_ARCORE_ENABLED = "key_webar_switch_arcore_enabled";
  private static final String KEY_WEBAR_SWITCH_SLAM_VIO_ENABLED = "key_webar_switch_slam_vio_enabled";
  private static final String KEY_WEBRTC_PLUGIN_AUTO_DOWNLOAD_NOT_ALLOWED = "key_webrtc_plugin_auto_download_not_allowed";
  public static final String KEY_WIFI_DOWNLOAD_OR_INST_DLG_TYPE = "key_wifi_download_or_inst_dlg_type";
  public static final String KEY_WIFI_ENABLE_CONFIG_UPLOAD = "key_wifi_enable_config_upload";
  public static final String KEY_WIFI_HAS_AUTHORIZE_DOWNLOAD = "key_wifi_has_authorize_download";
  public static final String KEY_WIFI_INFO_AVAILABLE_TIME = "key_wifi_info_available_time_fix";
  public static final String KEY_WIFI_LAST_CHECK_TIME = "key_wifi_last_check_time";
  public static final String KEY_WIFI_LAST_CONNECTED_FREE_WIFI = "key_wifi_last_connected_free_wifi";
  private static final String KEY_WUP_MINIQB_STAT_DATA_ID = "key_wup_miniqb_stat_data_id";
  private static final String KEY_WUP_PLI_DATA_ID = "key_wup_pli_data_id";
  public static final String KEY_WUP_RSA_AES_ENCRYPT_TYPE = "key_wup_rsa_aes_encrypt_type";
  public static final String KEY_WUP_SERVANT_AVAILABLE_TIME_PREFIX = "key_wp_svt_av_time_pref";
  private static final String KEY_WUP_SERVER_EVER_FAILED = "key_wup_server_ever_failed";
  private static final String KEY_WUP_STAT_DATA_ID = "key_wup_stat_data_id";
  private static final String KEY_WUP_TOKEN = "key_wup_token";
  private static final String KEY_WXPC_DEFAULT = "key_wxpic_default_enabled";
  private static final String KEY_WXPC_ENABLED = "key_wxpic_enabled";
  private static final String KEY_X5COOKIE_ISOLATION = "key_x5cookie_isolation";
  private static final String KEY_X5JSCORE_USE_DEPRECATED = "key_x5jscore_use_deprecated";
  private static final String KEY_X5_JSCORE_CAN_USE_BUFFER = "key_x5_jscore_can_use_buffer";
  private static final String KEY_X5_PRO_ENABLED = "key_x5_pro_enabled";
  private static final String PAT;
  private static final int PRELOAD_X5_CRASH_COUNTER_MAX_DEFAULT = 3;
  private static final int PRELOAD_X5_CRASH_DISABLE_INTERVAL_HOURS_DEFAULT = 48;
  private static final int RATIO_CEILING = 10000;
  private static final int RATIO_CEILING_LEN = String.valueOf(10000).length();
  private static final int RATIO_FLOOR = 0;
  private static final Pattern RATIO_INTERVAL_PATTERN = Pattern.compile(PAT);
  public static String SHARE_PREFERENCES_NAME = "";
  public static final String SKIN_INFO_TEST_URL = "http://skin.cs0309.icom.tencent.tbs.common.MTT.qq.com/skin/skinjson";
  public static final String SKIN_INFO_URL = "http://mdc.icom.tencent.tbs.common.MTT.qq.com/skin/skinjson";
  public static final long SKIN_UPDATE_TIME = 86400000L;
  static final long TIME_DAYS_TO_SECONDS = 86400L;
  public static final int WEBPAGE_FLAG_INVISABLE = 2;
  public static final int WEBPAGE_FLAG_NOUSE = 0;
  public static final int WEBPAGE_FLAG_VISABLE = 1;
  public static final int WEIYUAN_INDEPENDENT_PASSWORD_HAS_PASSWORD = 1;
  public static final int WEIYUAN_INDEPENDENT_PASSWORD_NO_PASSWORD = 2;
  public static final int WEIYUAN_INDEPENDENT_PASSWORD_NO_RESULT = 0;
  public static long mLastSysTemTime;
  public static long mSntpTime;
  private boolean isQua1Changed = true;
  private boolean isQua2_v3Changed = true;
  private boolean mBreakCommit = false;
  private Context mContext;
  private String mDirectUpLoadArgument = "";
  private SharedPreferences mPreference;
  private SharedPreferences.Editor mPreferenceEditor;
  private int qua1_enable = 0;
  private int qua2_v3_enable = 1;
  
  static
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("^\\s*\\[\\s*(\\d{1,");
    localStringBuilder.append(RATIO_CEILING_LEN);
    localStringBuilder.append("})\\s*,\\s*(\\d{1,");
    localStringBuilder.append(RATIO_CEILING_LEN);
    localStringBuilder.append("})\\s*\\]\\s*$");
    PAT = localStringBuilder.toString();
  }
  
  @TargetApi(9)
  public static final void editorApply(SharedPreferences.Editor paramEditor)
  {
    if (DeviceUtils.getSdkVersion() >= 9)
    {
      DonutAdapter.editorApply(paramEditor);
      return;
    }
    paramEditor.commit();
  }
  
  public static PublicSettingManager getInstance()
  {
    return InstanceHolder.INSTANCE;
  }
  
  private boolean getVideoIsDefaultFullscreenDefaultValue()
  {
    Context localContext = this.mContext;
    return (localContext != null) && ("com.tencent.mm".equalsIgnoreCase(localContext.getPackageName()));
  }
  
  public int DownloadInterceptFileType()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_download_intercept_filetype", 0);
  }
  
  public boolean canInsertAdInSepcialSite()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_can_insert_ad_in_specialsite", false);
  }
  
  public boolean canUseAdBlockUnderDirect()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_can_use_adblock_under_direct", false);
  }
  
  public boolean canUseDynamicCanvasGpu()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_can_use_dynamic_canvas_gpu", true);
  }
  
  public boolean canUseNewJsDialog()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getInt("CAN_USE_NEW_JS_DIALOG", 1) == 1;
  }
  
  public boolean canUseQProxyUnderProxy()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_can_use_qproxy_under_proxy", false);
  }
  
  public boolean checkUseX5CookiePathPolicyEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_check_x5cookie_path_policy_enable", true);
  }
  
  public void clear()
  {
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.clear();
      this.mPreferenceEditor.commit();
    }
    setBreakCommit(false);
  }
  
  public void commitAll()
  {
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null) {
      localEditor.commit();
    }
    setBreakCommit(false);
  }
  
  public int convertDownloadInterceptthreeswitchLevel()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_download_intercept_switch", 0);
  }
  
  public int convertGetToPostLevel()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_can_convert_get_to_post", 0);
  }
  
  public void crownTheGreatKingOfSimCardOrNot(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_great_king_of_simcard", paramBoolean).commit();
      return;
    }
  }
  
  public int explorerCharacter()
  {
    if (getPreference() == null) {
      return 100;
    }
    return getPreference().getInt("key_explorer_character", 100);
  }
  
  public String geFaceAREngineName()
  {
    if (getPreference() == null) {
      return "YT";
    }
    return getPreference().getString("key_face_engine_name", "YT");
  }
  
  public int getAccumulationValuePV()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_accumulation_value_pv", 0);
  }
  
  public int getAccumulationValueUB()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_accumulation_value_ub", 0);
  }
  
  public String getAdPluginPath()
  {
    if (getPreference() == null) {
      return null;
    }
    return getPreference().getString("TBS_AD_PLUGIN_PATH", null);
  }
  
  public boolean getAddArgumentForImageRequestEnable()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_add_special_argument_enabled", true);
  }
  
  public int getAndroidAccelerated2dCanvas()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_android_accelerated_2d_canvas", 0);
  }
  
  public int getAndroidEnableQua1()
  {
    if (getPreference() == null) {
      return 0;
    }
    if (this.isQua1Changed)
    {
      this.qua1_enable = getPreference().getInt("key_android_enable_qua1", 0);
      this.isQua1Changed = false;
    }
    return this.qua1_enable;
  }
  
  public int getAndroidEnableQua2_v3()
  {
    if (getPreference() == null) {
      return 1;
    }
    if (this.isQua2_v3Changed)
    {
      this.qua2_v3_enable = getPreference().getInt("key_android_enable_qua2_v3", 1);
      this.isQua2_v3Changed = false;
    }
    return this.qua2_v3_enable;
  }
  
  public int getAndroidGpuRasterization()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_android_gpu_rasterization", 0);
  }
  
  public int getAndroidUploadTextureMode()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_android_upload_texture_mode", 0);
  }
  
  public int getAndroidWebgl()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_android_webgl", 0);
  }
  
  public boolean getAutoPageDiscardingDefaultEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return this.mPreference.getBoolean("key_auto_page_discarding_defalut_enabled", true);
  }
  
  public boolean getAutoPageDiscardingEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_auto_page_discarding_enabled", false);
  }
  
  public boolean getAutoPageRestorationDefaultEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_auto_page_restoration_defalut_enabled", true);
  }
  
  public boolean getAutoPageRestorationEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_auto_page_restoration_enabled", false);
  }
  
  public boolean getAutoSwitchARCoreEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_webar_switch_arcore_enabled", false);
  }
  
  public boolean getAutoSwitchSlamVioEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_webar_switch_slam_vio_enabled", false);
  }
  
  public boolean getBDSearchPredictEnable()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_bdsearch_predict_enabled", false);
  }
  
  public boolean getBFOptEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getInt("BF_OPT_ENABLED", 1) == 1;
  }
  
  public int getBdhdSupportType()
  {
    if (getPreference() == null) {
      return 1;
    }
    return getPreference().getInt("key_bdhd_support_type", 1);
  }
  
  public boolean getBeaconUploadEnable()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_beacon_upload_enable", true);
  }
  
  public String getBrowserListDialogStyle()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_conf_browser_list_dialog_style", "");
  }
  
  public boolean getBuglyEnable()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_bugly_enable", false);
  }
  
  public boolean getCanAIAExtension()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_can_aia_extension", true);
  }
  
  public boolean getCanIpv6Proxy()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_can_ipv6_proxy", true);
  }
  
  public boolean getCanUseX5Jscore()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_tbs_can_use_x5_jscore", true);
  }
  
  public boolean getCanUseX5JscoreNewAPI()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_tbs_can_use_x5_jscore_new_api", false);
  }
  
  public String getCloudStringSwitch(String paramString1, String paramString2)
  {
    if (getPreference() == null) {
      return paramString2;
    }
    return getPreference().getString(paramString1, paramString2);
  }
  
  public int getCloudSwitch(String paramString, int paramInt)
  {
    if (getPreference() == null) {
      return 1;
    }
    return getPreference().getInt(paramString, paramInt);
  }
  
  public String getCommonConfigListContent(int paramInt)
  {
    if (getPreference() == null) {
      return "";
    }
    SharedPreferences localSharedPreferences = getPreference();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("common_config_list_content_");
    localStringBuilder.append(paramInt);
    return localSharedPreferences.getString(localStringBuilder.toString(), "");
  }
  
  public String getCommonConfigMd5(int paramInt)
  {
    if (getPreference() == null) {
      return "";
    }
    SharedPreferences localSharedPreferences = getPreference();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("common_config_list_md5_");
    localStringBuilder.append(paramInt);
    return localSharedPreferences.getString(localStringBuilder.toString(), "");
  }
  
  public int getCommonConfigTime(int paramInt)
  {
    if (getPreference() == null) {
      return 0;
    }
    SharedPreferences localSharedPreferences = getPreference();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("common_config_list_time_");
    localStringBuilder.append(paramInt);
    return localSharedPreferences.getInt(localStringBuilder.toString(), 0);
  }
  
  public int getConnectionTimeOutGPRS()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_grps_to", 0);
  }
  
  public int getConnectionTimeOutWIFI()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_wifi_to", 0);
  }
  
  public boolean getContentCacheEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_content_cache_enabled", false);
  }
  
  public String getCorePageDurationPrefs()
  {
    if (getPreference() == null) {
      return "2";
    }
    return getPreference().getString("key_mtt_core_page_duration", "2");
  }
  
  public int getCrashInspectionDisableIntervalHours()
  {
    if (getPreference() == null) {
      return 48;
    }
    return getPreference().getInt("CRASH_INSPECTION_DISABLE_INTERVAL_HOURS", 48);
  }
  
  public int getCrashInspectionDisableMaxTimes()
  {
    if (getPreference() == null) {
      return 3;
    }
    return getPreference().getInt("CRASH_INSPECTION_DISABLE_MAX_TIMES", 3);
  }
  
  public String getCustomedWupAddress()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_test_environment_wup_address", "");
  }
  
  public int getDirectAdblockSwitcherLevel()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_direct_adblock_switcher_level", 0);
  }
  
  public int getDirectAdblockSwitcherLevelForQB()
  {
    if (getPreference() == null) {
      return 3;
    }
    return getPreference().getInt("key_direct_adblock_switcher_level", 3);
  }
  
  public String getDirectUploadArgument()
  {
    return this.mDirectUpLoadArgument;
  }
  
  public boolean getDiskCacheSizeEnable()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_disk_cache_size", false);
  }
  
  public int getDisplayCutoutEnableSwitch()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("DISPLAY_CUTOUT_ENABLE_SWITCH", 0);
  }
  
  public int getDomainTime(byte paramByte)
  {
    if (getPreference() == null) {
      return 0;
    }
    SharedPreferences localSharedPreferences = getPreference();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("miniqb_pref_domain");
    localStringBuilder.append(paramByte);
    return localSharedPreferences.getInt(localStringBuilder.toString(), 0);
  }
  
  public float getDowloadSizeThreshold()
  {
    if (getPreference() == null) {
      return -1.0F;
    }
    return getPreference().getFloat("key_dowload_size_threshold", -1.0F);
  }
  
  public boolean getDownloadInQB()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_downloadinqb_enable", true);
  }
  
  public int getDownloadInTencentFile()
  {
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_download_in_tencentfile_enable", -1);
  }
  
  public String getDownloadInterceptKeyMessage()
  {
    if (getPreference() == null) {
      return "环境: 未知环境";
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("");
    ((StringBuilder)localObject1).append("环境:");
    localObject1 = ((StringBuilder)localObject1).toString();
    if (getPreference().getInt("key_environment_type", -1) == 1)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("测试环境");
      localObject1 = ((StringBuilder)localObject2).toString();
    }
    else if (getPreference().getInt("key_environment_type", -1) == 2)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("正式环境");
      localObject1 = ((StringBuilder)localObject2).toString();
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("未知环境");
      localObject1 = ((StringBuilder)localObject2).toString();
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("\n相关的Key:");
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("\n TBS_GENERAL_FEATURE_SWITCH_INTERCEPT_DOWNLOAD_REQUEST(A):");
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(getPreference().getInt("key_tbs_general_feature_switch_intercept_download_request", -1));
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("\n key_tbs_general_feature_switch_core_version_intercept_download_request:");
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(getPreference().getString("key_tbs_general_feature_switch_core_version_intercept_download_request", "-1"));
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("\n KEY_DOWNLOAD_INTERCEPT_SWITCH(E):");
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(getPreference().getInt("key_download_intercept_switch", -1));
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("\n KEY_NOTIFY_DOWNLOAD_QB_LIMIT(F):");
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(getPreference().getInt("key_notify_download_qb_times_limit", -1));
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("\n KEY_QQ_INTERRUPT_APK(H):");
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(getPreference().getInt("key_qq_interrupt_apk_type", -1));
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("\n KEY_QQ_INTERRUPT_NOT_APK(I):");
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(getPreference().getInt("key_qq_interrupt_not_apk_type", -1));
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("\n TBS_DOWNLOAD_IN_TENCENTFILE_ENABLE(J):");
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(getPreference().getInt("key_download_in_tencentfile_enable", -1));
    localObject1 = ((StringBuilder)localObject2).toString();
    int i = getPreference().getInt("key_download_intercept_to_qb_pop_menu_style", -1);
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("\n KEY_DOWNLOAD_INTERCEPT_TO_QB_POP_MENU_STYLE(G):");
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(i);
    localObject2 = ((StringBuilder)localObject2).toString();
    if (i == 1)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append(":系统样式(1)");
      return ((StringBuilder)localObject1).toString();
    }
    if (i == 2)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append(":定制样式(2)");
      return ((StringBuilder)localObject1).toString();
    }
    localObject1 = localObject2;
    if (i == 3)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append(":中间页(3)");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    return (String)localObject1;
  }
  
  public int getDownloadInterceptToQBPopMenuStyle()
  {
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_download_intercept_to_qb_pop_menu_style", -1);
  }
  
  public boolean getEnableLogs()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_enable_logs", true);
  }
  
  public String getExtendRuleMD5()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_extend_rule_md5", "");
  }
  
  public int getExtendRuleVersion()
  {
    if (getPreference() == null) {
      return 23;
    }
    return getPreference().getInt("key_extend_rule_version", 23);
  }
  
  public boolean getFakeLoginEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("TBS_FAKE_LOGIN_ENABLED", true);
  }
  
  public boolean getFirstScreenDetectEnable()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_first_screen_x5core_detect", true);
  }
  
  public boolean getFirstScreenDrawFullScreenEnable()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_first_screen_draw_fullscreen", true);
  }
  
  public boolean getFirstScreenDrawNotFullScreenEnable()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_first_screen_draw_notfullscreen", false);
  }
  
  public String getFormerTbsVersion()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_tbs_former_tbscore_version", "");
  }
  
  public int getFrameLimitCount(int paramInt)
  {
    if (getPreference() == null) {
      return paramInt;
    }
    return getPreference().getInt("FRAME_LIMIT_COUNT_FOR_OOM", paramInt);
  }
  
  public boolean getFramePerformanceRecordEnable()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_performance_record_enabled", true);
  }
  
  public boolean getGPUEnable()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_prefernce_gpu_state", true);
  }
  
  public int getGameServiceEnv()
  {
    if (getPreference() == null) {
      return 0;
    }
    int j = getPreference().getInt("key_tbs_game_fw_service_env", 0);
    int i;
    if (j <= 2)
    {
      i = j;
      if (j >= 0) {}
    }
    else
    {
      i = 0;
    }
    return i;
  }
  
  public boolean getGinJavaMapEraseDisabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_gin_java_map_erase_disable", true);
  }
  
  public String getGpuBugSwitch(String paramString1, String paramString2)
  {
    if (getPreference() == null) {
      return null;
    }
    return getPreference().getString(paramString1, paramString2);
  }
  
  public boolean getHasEverLogin()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_has_ever_login", false);
  }
  
  public boolean getHasReportLauncherPkgName()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_has_report_launcher_pkg_name", false);
  }
  
  public boolean getHideAdDialogEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_user_adhide_dialog_switch", true);
  }
  
  public boolean getHitRateEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_hitrate_report_enabled", false);
  }
  
  public String getHttpDNSEncryptKey()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_tbs_httpdns_encryptkey", "");
  }
  
  public String getHttpDNSServerIP()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_tbs_httpdns_server_ip", "");
  }
  
  public boolean getHttps0RTTEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_https_0rtt", false);
  }
  
  public boolean getIsCheckJsDomain()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_is_check_jsdomain", true);
  }
  
  public boolean getIsOpenDebugTbsEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("OPEN_DEBUGTBS_ENABLED", true);
  }
  
  public boolean getIsSchemeCallAppByUser()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_is_scheme_call_app_by_user", true);
  }
  
  public boolean getIsUploadPvinWup()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_isinwup_upload_pv", false);
  }
  
  public boolean getIsUseThirdPartyAdRules()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_is_use_thirdparty_adrules", false);
  }
  
  public boolean getIsX5ProEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_x5_pro_enabled", false);
  }
  
  public boolean getJniRegisterEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_jni_register_enabled", true);
  }
  
  public int getJsonSizeForPV()
  {
    if (getPreference() == null) {
      return 51200;
    }
    return getPreference().getInt("key_json_size_for_pv", 51200);
  }
  
  public int getKingCardUserProxyJudgeCondition()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_kc_proxy_stragety", 255);
  }
  
  public long getLastCheckBasicConfigDate(byte paramByte)
  {
    if (getPreference() == null) {
      return -1L;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("key_last_check_basic_config_date");
    ((StringBuilder)localObject).append(paramByte);
    localObject = ((StringBuilder)localObject).toString();
    return getPreference().getLong((String)localObject, -1L);
  }
  
  public long getLastCheckWupConfigDate()
  {
    if (getPreference() == null) {
      return -1L;
    }
    return getPreference().getLong("key_last_check_wup_config_date", -1L);
  }
  
  public String getLastConnectedFreeWifi()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_wifi_last_connected_free_wifi", "");
  }
  
  public String getLastMiniqbVersionForPrefs()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_last_miniqb_version_for_prefs", "");
  }
  
  public String getLastResolvedWupAddress()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_last_resovled_wup_address", "");
  }
  
  public String getLauncherPkgName()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_report_launcher_pkg_name", "");
  }
  
  public int getLongPressToQBPopMenuStyle()
  {
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_long_press_to_qb_pop_menu_style", -1);
  }
  
  public boolean getMSEEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_mse_enabled", true);
  }
  
  public String getMarkerAREngineName()
  {
    if (getPreference() == null) {
      return "Tar";
    }
    return getPreference().getString("key_marker_engine_name", "Tar");
  }
  
  public String getMiniQBPref(String paramString)
  {
    if (getPreference() == null) {
      return "";
    }
    SharedPreferences localSharedPreferences = getPreference();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("miniqb_pref_pref");
    localStringBuilder.append(paramString);
    return localSharedPreferences.getString(localStringBuilder.toString(), "");
  }
  
  public int getMiniQBPreferenceUpdateTime()
  {
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_miniqb_preference_update_time", -1);
  }
  
  public String getMiniQbStatVersion()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_miniqb_stat_version_name", "");
  }
  
  public boolean getNeedPreInitX5Core()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("NEED_PREINIT_X5_CORE", false);
  }
  
  public int getNotifyDownloadQBTimesLimit()
  {
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_notify_download_qb_times_limit", -1);
  }
  
  public long getPluginListLastTime()
  {
    if (getPreference() == null) {
      return 0L;
    }
    return getPreference().getLong("key_pluginlist_last_pull_pluignList", 0L);
  }
  
  public String getPluginListRspMD5()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_plugin_list_md5", "");
  }
  
  public SharedPreferences getPreference()
  {
    try
    {
      if (this.mPreference == null)
      {
        this.mContext = TbsBaseModuleShell.getCallerContext();
        if (this.mContext == null) {
          return null;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(ThreadUtils.getCurrentProcessNameIngoreColon(this.mContext));
        localStringBuilder.append("_tbs_public_settings");
        SHARE_PREFERENCES_NAME = localStringBuilder.toString();
        this.mPreference = new SafetySharedPreference(this.mContext.getSharedPreferences(SHARE_PREFERENCES_NAME, 0));
        this.mPreferenceEditor = this.mPreference.edit();
      }
      if (this.mPreferenceEditor == null) {
        this.mPreferenceEditor = this.mPreference.edit();
      }
      return this.mPreference;
    }
    finally {}
  }
  
  public int getPreferenceUpdateTime(byte paramByte)
  {
    if (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB) {
      return getMiniQBPreferenceUpdateTime();
    }
    if (paramByte == TbsBaseModuleShell.REQ_SRC_TBS_VIDEO) {
      return getTbsVideoPreferenceUpdateTime();
    }
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_preference_update_time", -1);
  }
  
  public String getPrerenderPkgName()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_prerender_package_names", "");
  }
  
  public int getPrerenderSwitch()
  {
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_prerender_swtich", -1);
  }
  
  public boolean getPushSvrNotifyUpdatePluginList()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_have_plugin_push_request", false);
  }
  
  public boolean getQBHavePullPluginListYet()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_plugin_list_succ2170", false);
  }
  
  public int getQQInterruptApkType()
  {
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_qq_interrupt_apk_type", -1);
  }
  
  public int getQQInterruptNotApkType()
  {
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_qq_interrupt_not_apk_type", -1);
  }
  
  public int getQbIconTypeInLongClick()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_qb_icon_type_in_long_click_popup", 0);
  }
  
  public boolean getQuicProxySwitch()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_quic_proxy_switch", true);
  }
  
  public int getQvodSupportType()
  {
    if (getPreference() == null) {
      return 1;
    }
    return getPreference().getInt("key_qvod_support_type", 1);
  }
  
  public int getRedirectCountLimit()
  {
    if (getPreference() == null) {
      return -2;
    }
    return getPreference().getInt("REDIRECT_COUNT_LIMIT", -2);
  }
  
  public boolean getReportCallWebviewApiOnWrongThreadEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_call_webview_api_on_wrong_thread", false);
  }
  
  public String getReportFailedGuid()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_report_failed_tbs_guid", "");
  }
  
  public long getRequestFailedTime(String paramString)
  {
    if (getPreference() == null) {
      return -1L;
    }
    SharedPreferences localSharedPreferences = getPreference();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("key_request_failed_time_prefix_");
    localStringBuilder.append(paramString);
    return localSharedPreferences.getLong(localStringBuilder.toString(), -1L);
  }
  
  public boolean getRestDbTablePlugin()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_reset_db_table_plugin", true);
  }
  
  public boolean getRestDbTablePluginFor5_3()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_reset_db_table_plugin_5_3", true);
  }
  
  public int getRestrictErrorPageReloadSecond()
  {
    if (getPreference() == null) {
      return 2;
    }
    return getPreference().getInt("key_tbs_restrict_errorpage_reload_second", 2);
  }
  
  public boolean getSPAAdPageReportEnable()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_spa_ad_page_report", false);
  }
  
  public String getSWApiExecResultUploadList()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_sw_api_exec_result_upload_list", "");
  }
  
  public boolean getSdcardDiskCacheEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_sdcard_disk_cache_enabled", false);
  }
  
  public boolean getSessionPersistentEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_session_persistent", false);
  }
  
  public boolean getSharpDefaultEnable()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_sharpp_defalut_enabled", true);
  }
  
  public boolean getSharpEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_sharpp_enabled", false);
  }
  
  public boolean getSharpPEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_sharpp_enabled", false);
  }
  
  public String getSniffDisableList()
  {
    if (getPreference() == null) {
      return null;
    }
    return getPreference().getString("key_sniff_disable_domains", null);
  }
  
  public long getSntpTime()
  {
    long l2 = System.currentTimeMillis() / 1000L;
    mLastSysTemTime = this.mPreference.getLong("key_sntp_time", 0L);
    Object localObject = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(1001);
    long l1;
    if ((localObject != null) && (((ArrayList)localObject).size() > 0)) {
      l1 = Long.valueOf((String)((ArrayList)localObject).get(0)).longValue();
    } else {
      l1 = 0L;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getSntpTime  mSntpTime ");
    ((StringBuilder)localObject).append(mSntpTime);
    ((StringBuilder)localObject).append("domainTime");
    ((StringBuilder)localObject).append(l1);
    ((StringBuilder)localObject).append("lastsystemTime");
    ((StringBuilder)localObject).append(mLastSysTemTime);
    LogUtils.d("PublicSettingManager", ((StringBuilder)localObject).toString());
    if (((mSntpTime == 0L) && (Math.abs(mLastSysTemTime - l2) > 3600L)) || ((Math.abs(mSntpTime - l1) > 86400L) && (Math.abs(mLastSysTemTime - mSntpTime) > 86400L))) {
      getSntpTimeFormClient();
    }
    return this.mPreference.getLong("key_sntp_time", 0L);
  }
  
  public void getSntpTimeFormClient()
  {
    new AsyncTask()
    {
      protected Long doInBackground(Void... paramAnonymousVarArgs)
      {
        long l = 0L;
        try
        {
          paramAnonymousVarArgs = new SntpClient();
          if (paramAnonymousVarArgs.requestTime("cn.pool.ntp.org", 10000)) {
            l = paramAnonymousVarArgs.getNtpTime();
          }
          return Long.valueOf(l / 1000L);
        }
        catch (Exception paramAnonymousVarArgs)
        {
          for (;;) {}
        }
        return Long.valueOf(0L);
      }
      
      protected void onPostExecute(Long paramAnonymousLong)
      {
        PublicSettingManager.mSntpTime = paramAnonymousLong.longValue();
        PublicSettingManager.this.setSntpTimeAndSysTemTime(PublicSettingManager.mSntpTime, PublicSettingManager.mLastSysTemTime);
      }
    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
  }
  
  public boolean getSubResourcePerformanceEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_enable_subresource_performance", true);
  }
  
  public int getSystemPopMenuStyle()
  {
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_stystem_pop_menu_style", -1);
  }
  
  public int getTBSGeneralFeatureSwitch(String paramString)
  {
    if (getPreference() == null) {
      return -1;
    }
    Object localObject1 = TbsBaseModuleShell.getCoreInfoFetcher().getCoreVersion();
    if (localObject1 != null)
    {
      Object localObject2 = getPreference();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("key_tbs_general_feature_switch_");
      localStringBuilder.append(paramString.toLowerCase());
      if (((SharedPreferences)localObject2).contains(localStringBuilder.toString()))
      {
        localObject2 = getPreference();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("key_tbs_general_feature_switch_core_version_");
        localStringBuilder.append(paramString.toLowerCase());
        if (((SharedPreferences)localObject2).contains(localStringBuilder.toString()))
        {
          localObject2 = getPreference();
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("key_tbs_general_feature_switch_core_version_");
          localStringBuilder.append(paramString.toLowerCase());
          if (((String)localObject1).equals(((SharedPreferences)localObject2).getString(localStringBuilder.toString(), "")))
          {
            localObject1 = getPreference();
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("key_tbs_general_feature_switch_");
            ((StringBuilder)localObject2).append(paramString.toLowerCase());
            return ((SharedPreferences)localObject1).getInt(((StringBuilder)localObject2).toString(), -1);
          }
        }
      }
    }
    return -1;
  }
  
  public String getTBSPickedDefaultBrowser()
  {
    if (getPreference() == null) {
      return null;
    }
    return getPreference().getString("key_tbs_picked_default_browser", null);
  }
  
  public String getTBSPickedDefaultFileOpener()
  {
    if (getPreference() == null) {
      return null;
    }
    return getPreference().getString("key_tbs_picked_default_file_opener", null);
  }
  
  public boolean getTbsAdHttpProxySwitch(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return paramBoolean;
    }
    return getPreference().getBoolean("TBS_AD_HTTP_PROXY_SWITCH", paramBoolean);
  }
  
  public long getTbsAdReinstallTipsShowTime()
  {
    if (getPreference() == null) {
      return 0L;
    }
    return getPreference().getLong("key_tbs_ad_reinstall_tips_show_time", 0L);
  }
  
  public boolean getTbsAllowLoaddataWithCSP()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_tbs_allow_loadata_with_csp", false);
  }
  
  public String getTbsGameFwLoginUrl()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_tbs_game_fw_login_url", "");
  }
  
  public String getTbsGameFwPayUrl()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_tbs_game_fw_pay_url", "");
  }
  
  public String getTbsGameFwShareUrl()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_tbs_game_fw_share_url", "");
  }
  
  public boolean getTbsImpatientNetworkSwitch()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_tbs_impatient_network_switch", false);
  }
  
  public int getTbsInfoUploadApn()
  {
    if (getPreference() == null) {
      return 4;
    }
    return getPreference().getInt("key_tbs_info_upload_argument_apn", 4);
  }
  
  public boolean getTbsInfoUploadNeedProxyData()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_tbs_info_upload_needproxy", false);
  }
  
  public boolean getTbsInfoUploadSwitcer()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_tbs_info_upload_argument_switcher", false);
  }
  
  public String getTbsLogCacheStrategyPrefs()
  {
    if (getPreference() == null) {
      return null;
    }
    return getPreference().getString("key_tbslog_cache_strategy_prefs", null);
  }
  
  public String getTbsLogEnablePrefs()
  {
    if (getPreference() == null) {
      return null;
    }
    return getPreference().getString("key_tbslog_enable_prefs", null);
  }
  
  public String getTbsLogFlushStrategyPrefs()
  {
    if (getPreference() == null) {
      return null;
    }
    return getPreference().getString("key_tbslog_flush_strategy_prefs", null);
  }
  
  public String getTbsLogReportPrefs()
  {
    if (getPreference() == null) {
      return null;
    }
    return getPreference().getString("key_tbslog_report_prefs", null);
  }
  
  public int getTbsPageInfoUploadMaxCount()
  {
    if (getPreference() == null) {
      return 200;
    }
    return getPreference().getInt("key_tbs_page_load_info_upload_maxcount", 200);
  }
  
  public int getTbsVideoPreferenceUpdateTime()
  {
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_tbsvideo_preference_update_time", -1);
  }
  
  public int getTbsVideoVersion()
  {
    if (getPreference() == null) {
      return 0;
    }
    try
    {
      int j = this.mContext.getSharedPreferences("qb_video_settings", 0).getInt("video_cur_version", 0);
      int i = j;
      if (j == 0)
      {
        Object localObject = Class.forName("com.tencent.mtt.video.export.VideoEngine");
        Field localField = ((Class)localObject).getField("DEFAULT_VERSION");
        localField.setAccessible(true);
        localObject = localField.get(localObject);
        i = j;
        if ((localObject instanceof Integer)) {
          i = ((Integer)localObject).intValue();
        }
      }
      return i;
    }
    catch (Throwable localThrowable) {}
    return 0;
  }
  
  public String getTbsWebviewSearchEngineUrl()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_tbs_webview_search_engine_url", "");
  }
  
  public boolean getTheGreatKingOfSimCardSwitch()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_great_king_of_simcard", true);
  }
  
  public int getTpgEnabledType()
  {
    if (getPreference() == null) {
      return 1;
    }
    return getPreference().getInt("key_tpg_enabled", 1);
  }
  
  public String getTuringDOAID()
  {
    if (getPreference() == null) {
      return null;
    }
    return getPreference().getString("TBS_AD_OAID", null);
  }
  
  public String getTuringDTAID()
  {
    if (getPreference() == null) {
      return null;
    }
    return getPreference().getString("TBS_AD_TAID", null);
  }
  
  public String getUgPluginUploadInterval()
  {
    return getCloudStringSwitch("TBS_UG_LOAD_REPORT_RATIO_CONTROL", "[1,0]");
  }
  
  public int getUnSuccessPluginPushRequestCount()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_unsuccess_start_plugin_request_count", 0);
  }
  
  public boolean getVideoIsDefaultFullscreen()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_video_is_default_fullscreen", false);
  }
  
  public int getVideoMaxBuffSize()
  {
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_video_maxbuffsize", -1);
  }
  
  public int getVideoMinBuffSize()
  {
    if (getPreference() == null) {
      return -1;
    }
    return getPreference().getInt("key_video_minbuffsize", -1);
  }
  
  public boolean getVideoProductJsApi()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_video_product_jsapi", true);
  }
  
  public boolean getVideoProductOpenQb()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_video_product_openqb", true);
  }
  
  public boolean getVideoSameLayer()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_video_same_layer", true);
  }
  
  public int getWUPPliDataId()
  {
    if (getPreference() == null) {
      return 0;
    }
    int i = getPreference().getInt("key_wup_pli_data_id", 0);
    getPreference().edit().putInt("key_wup_pli_data_id", (i + 1) % 9999);
    editorApply(getPreference().edit());
    return i;
  }
  
  public int getWUPStatDataId()
  {
    if (getPreference() == null) {
      return 0;
    }
    int i = getPreference().getInt("key_wup_stat_data_id", 0);
    getPreference().edit().putInt("key_wup_stat_data_id", (i + 1) % 9999);
    editorApply(getPreference().edit());
    return i;
  }
  
  public int getWUPStatDataId(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return 0;
    }
    String str;
    if (paramBoolean) {
      str = "key_wup_miniqb_stat_data_id";
    } else {
      str = "key_wup_stat_data_id";
    }
    int i = getPreference().getInt(str, 0);
    getPreference().edit().putInt(str, (i + 1) % 9999);
    editorApply(getPreference().edit());
    return i;
  }
  
  public boolean getWXPCDefaultEnable()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_wxpic_default_enabled", true);
  }
  
  public boolean getWXPCEnabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_wxpic_enabled", false);
  }
  
  public boolean getWasmDisabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_wasm_disable", false);
  }
  
  public boolean getWebARMarkerDisabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_webar_marker_disabled", false);
  }
  
  public boolean getWebARMarkerEnabledOnAllDevices()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_webar_marker_enabled_on_all_devices", false);
  }
  
  public boolean getWebARSlamDisabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_webar_slam_disabled", false);
  }
  
  public boolean getWebARSlamEnabledOnAllDevices()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_webar_slam_enabled_on_all_devices", false);
  }
  
  public String getWebARSlamHardwareConfig()
  {
    if (getPreference() == null) {
      return null;
    }
    return getPreference().getString("key_webar_slam_hardware_config", null);
  }
  
  public boolean getWebARSlamVIODisabled()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_webar_slam_vio_disabled", false);
  }
  
  public String getWifiBrandInfo()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_brand_info_cache", "");
  }
  
  public int getWifiDownloadOrInstDlgType()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_wifi_download_or_inst_dlg_type", 0);
  }
  
  public boolean getWifiEnableConfigUpload()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_wifi_enable_config_upload", true);
  }
  
  public boolean getWifiHasAuthorizeDownload()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_wifi_has_authorize_download", false);
  }
  
  public long getWifiInfoAvailableTime()
  {
    if (getPreference() == null) {
      return 180L;
    }
    return getPreference().getLong("key_wifi_info_available_time_fix", 180L);
  }
  
  public long getWifiLastCheckTime()
  {
    if (getPreference() == null) {
      return -1L;
    }
    return getPreference().getLong("key_wifi_last_check_time", -1L);
  }
  
  public int getWifiNOQBSwitch()
  {
    if (getPreference() == null) {
      return 1;
    }
    return getPreference().getInt("WIFI_NOQB_SHOW", 1);
  }
  
  public String getWifiNOQBSwitchJumpUrl()
  {
    if (getPreference() == null) {
      return "http://res.imtt.qq.com/smartbanner/wifi_saitest_v42/index.html#/list";
    }
    return getPreference().getString("WIFI_NOQB_SWITCH_JUMPURL", "http://res.imtt.qq.com/smartbanner/wifi_saitest_v42/index.html#/list");
  }
  
  public byte getWupEncryptType()
  {
    if (getPreference() == null) {
      return 2;
    }
    return (byte)getPreference().getInt("key_wup_rsa_aes_encrypt_type", 2);
  }
  
  public long getWupServantAvailableTime(String paramString)
  {
    if (getPreference() == null) {
      return -1L;
    }
    SharedPreferences localSharedPreferences = getPreference();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("key_wp_svt_av_time_pref");
    localStringBuilder.append(paramString);
    return localSharedPreferences.getLong(localStringBuilder.toString(), -1L);
  }
  
  public String getWupToken()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_wup_token", "");
  }
  
  public boolean getX5CookieIsolationEnabled()
  {
    if ((Build.VERSION.SDK_INT >= 20) && (Build.VERSION.SDK_INT <= 23))
    {
      if (getPreference() == null) {
        return true;
      }
      return getPreference().getBoolean("key_x5cookie_isolation", true);
    }
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_x5cookie_isolation", false);
  }
  
  public boolean getX5JsCoreBufferSwitch()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_x5_jscore_can_use_buffer", true);
  }
  
  public boolean getX5JsCoreUseDeprecated()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_x5jscore_use_deprecated", true);
  }
  
  public String getX5LongClickPopupMenuSubText()
  {
    if (getPreference() == null) {
      return "";
    }
    return getPreference().getString("key_long_click_popup_menu_subtext", "");
  }
  
  public void handleClearCacheCommand(String paramString)
  {
    ICoreService localICoreService;
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramString != null)
      {
        if (paramString.length() <= 0) {
          return;
        }
        localICoreService = TbsBaseModuleShell.getCoreService();
        if (localICoreService == null) {
          return;
        }
        paramString = paramString.split("\\|");
        if (paramString.length != 2) {
          return;
        }
      }
    }
    int j;
    try
    {
      int i = Integer.parseInt(paramString[0]);
      if (i > getPreference().getInt("key_tbs_clear_cache_command_version", 0))
      {
        j = Integer.parseInt(paramString[1]);
        this.mPreferenceEditor.putInt("key_tbs_clear_cache_command_version", i);
        if (this.mBreakCommit) {
          break label182;
        }
        this.mPreferenceEditor.commit();
        break label182;
        localICoreService.sweepAll(this.mContext);
        return;
        localICoreService.sweepCookieCache(this.mContext);
        return;
        localICoreService.sweepWebStorageCache(this.mContext);
        return;
        localICoreService.sweepDNSCache(this.mContext);
        return;
        localICoreService.sweepFileCache(this.mContext);
      }
      return;
    }
    catch (Exception paramString)
    {
      return;
    }
    return;
    return;
    label182:
    switch (j)
    {
    }
  }
  
  public boolean hasWupListFailedEver()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_wup_server_ever_failed", false);
  }
  
  public boolean isAllowDnsStoreEnable()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_allow_dns_store", true);
  }
  
  public boolean isBubbleAdEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_bubble_ad", true);
  }
  
  public boolean isBubbleMiniQbAdEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_bubble_miniqb_ad", true);
  }
  
  public boolean isCrashInspectionEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("CRASH_INSPECTION_ENABLED", true);
  }
  
  public boolean isEnableInterceptDidfailPageFinished()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_intercept_didfail_onpagefinished", true);
  }
  
  public boolean isEnableRsaAesEncrypt()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_preference_enable_rsa_aes_encrypt", false);
  }
  
  public boolean isExtendRuleUpdated()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_extend_rule_pdated", false);
  }
  
  public boolean isGameEngineUseSandbox()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_tbs_game_fw_sandbox", true);
  }
  
  public boolean isGdtAdvertisementEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_gdt_advertisement", false);
  }
  
  public boolean isLastDomainListFromTestServer()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_last_wup_domain_list_from_wup_server", false);
  }
  
  public boolean isLastWupEncryptModifyExpired()
  {
    if (getPreference() == null) {
      return true;
    }
    long l = getPreference().getLong("key_last_modify_wup_encrypt_time", 0L);
    return System.currentTimeMillis() - l > 86400000L;
  }
  
  public boolean isLastWupReqFromTestEvn(String paramString)
  {
    if (getPreference() == null) {
      return false;
    }
    SharedPreferences localSharedPreferences = getPreference();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("key_last_req_from_test_evn_");
    localStringBuilder.append(paramString);
    return localSharedPreferences.getBoolean(localStringBuilder.toString(), false);
  }
  
  public boolean isQBiconInQQShineEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_qbicon_qq_shine", false);
  }
  
  public boolean isQQDomain(String paramString)
  {
    return QBUrlUtils.isQQDomain(paramString, false);
  }
  
  public boolean isSpliceAdEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_splice_ad", true);
  }
  
  public boolean isSpliceMiniQbAdEnabled()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_splice_miniqb_ad", true);
  }
  
  public boolean isTbsAdEnableSpecialPermission()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_tbs_ad_special_permission", true);
  }
  
  public boolean isVideoPreloadOpen()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_video_preload", false);
  }
  
  public boolean isWebRTCPluginAutoDownloadNotAllowed()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_webrtc_plugin_auto_download_not_allowed", false);
  }
  
  public boolean isWifiShow_NOQB()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getInt("WIFI_NOQB_SHOW", 1) == 1;
  }
  
  public boolean isWifiShow_QB()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getInt("WIFI_QB_SHOW", 1) == 1;
  }
  
  public boolean isX5CookieIsolationEnabledForTBS()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_x5cookie_isolation", false);
  }
  
  public boolean needMergerQBGuid()
  {
    if (getPreference() == null) {
      return false;
    }
    return getPreference().getBoolean("key_should_merger_qb_guid", false);
  }
  
  public int nightModeInLongPressthreeswitchLevel()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_night_mode_in_long_press_switch", 0);
  }
  
  public void putCloudBoolSwitch(String paramString, boolean paramBoolean)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("key_");
      localStringBuilder.append(paramString.toLowerCase(Locale.getDefault()));
      localEditor.putBoolean(localStringBuilder.toString(), paramBoolean).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void putCloudStringSwitch(String paramString1, String paramString2)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putString(paramString1, paramString2).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void putCloudSwitch(String paramString1, String paramString2, int paramInt)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putInt(paramString1, StringUtils.parseInt(paramString2, paramInt)).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void putGpuBugSwitch(String paramString1, String paramString2)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putString(paramString1, paramString2).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void putLauncherPkgName(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_report_launcher_pkg_name", paramString).commit();
      return;
    }
  }
  
  public void putWebARSlamHardwareConfig(String paramString)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putString("key_webar_slam_hardware_config", paramString).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public boolean satisfyRatioControl(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if ("[1,0]".equals(paramString)) {
        return false;
      }
      paramString = RATIO_INTERVAL_PATTERN.matcher(paramString);
      if (!paramString.find()) {
        return false;
      }
      int i = Integer.valueOf(paramString.group(1)).intValue();
      int j = Integer.valueOf(paramString.group(2)).intValue();
      if ((i <= j) && (i >= 0) && (j <= 10000))
      {
        if ((i == j) && (i == 0)) {
          return false;
        }
        paramString = GUIDFactory.getInstance().getStrGuid();
        if (TextUtils.isEmpty(paramString)) {
          return false;
        }
        int k = new BigInteger(paramString.substring(8, 24), 16).mod(BigInteger.valueOf(10000L)).intValue();
        return (k >= i) && (k <= j);
      }
      return false;
    }
    return false;
  }
  
  public int saveWebPageInLongPressthreeswitchLevel()
  {
    if (getPreference() == null) {
      return 0;
    }
    return getPreference().getInt("key_save_webpage_in_long_press_switch", 0);
  }
  
  public void setAccumulationValuePV(int paramInt)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putInt("key_accumulation_value_pv", paramInt);
      this.mPreferenceEditor.commit();
      return;
    }
  }
  
  public void setAccumulationValueUB(int paramInt)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putInt("key_accumulation_value_ub", paramInt);
      this.mPreferenceEditor.commit();
      return;
    }
  }
  
  public void setAdPluginPath(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putString("TBS_AD_PLUGIN_PATH", paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setAddArgumentForImageRequestEnable(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_add_special_argument_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public boolean setAllowDnsStoreEnable(boolean paramBoolean)
  {
    if ((getPreference() != null) && (this.mPreferenceEditor != null)) {
      return getPreference().edit().putBoolean("key_allow_dns_store", paramBoolean).commit();
    }
    return false;
  }
  
  public void setAndroidAccelerated2dCanvas(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      try
      {
        localEditor.putInt("key_android_accelerated_2d_canvas", Integer.parseInt(paramString));
        if (!this.mBreakCommit)
        {
          this.mPreferenceEditor.commit();
          return;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
      return;
    }
  }
  
  public void setAndroidEnableQua1(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      this.isQua1Changed = true;
      try
      {
        localEditor.putInt("key_android_enable_qua1", Integer.parseInt(paramString));
        if (!this.mBreakCommit)
        {
          this.mPreferenceEditor.commit();
          return;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
      return;
    }
  }
  
  public void setAndroidEnableQua2_v3(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      this.isQua2_v3Changed = true;
      try
      {
        localEditor.putInt("key_android_enable_qua2_v3", Integer.parseInt(paramString));
        if (!this.mBreakCommit)
        {
          this.mPreferenceEditor.commit();
          return;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
      return;
    }
  }
  
  public void setAndroidGpuRasterization(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      try
      {
        localEditor.putInt("key_android_gpu_rasterization", Integer.parseInt(paramString));
        if (!this.mBreakCommit)
        {
          this.mPreferenceEditor.commit();
          return;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
      return;
    }
  }
  
  public void setAndroidUploadTextureMode(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      try
      {
        localEditor.putInt("key_android_upload_texture_mode", Integer.parseInt(paramString));
        if (!this.mBreakCommit)
        {
          this.mPreferenceEditor.commit();
          return;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
      return;
    }
  }
  
  public void setAndroidWebgl(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      try
      {
        localEditor.putInt("key_android_webgl", Integer.parseInt(paramString));
        if (!this.mBreakCommit)
        {
          this.mPreferenceEditor.commit();
          return;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
      return;
    }
  }
  
  public void setAutoPageDiscardingDefaultEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_auto_page_discarding_defalut_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setAutoPageDiscardingEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_auto_page_discarding_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setAutoPageRestorationDefaultEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_auto_page_restoration_defalut_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setAutoPageRestorationEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_auto_page_restoration_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setBdhdSupportType(int paramInt)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putInt("key_bdhd_support_type", paramInt);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public boolean setBeaconUploadEnable(boolean paramBoolean)
  {
    if ((getPreference() != null) && (this.mPreferenceEditor != null)) {
      return getPreference().edit().putBoolean("key_beacon_upload_enable", paramBoolean).commit();
    }
    return false;
  }
  
  public void setBreakCommit(boolean paramBoolean)
  {
    this.mBreakCommit = paramBoolean;
  }
  
  public void setBrowserListDialogStyle(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramString != null)
      {
        if (paramString.length() < 1) {
          return;
        }
        this.mPreferenceEditor.putString("key_conf_browser_list_dialog_style", paramString);
        if (!this.mBreakCommit) {
          this.mPreferenceEditor.commit();
        }
        return;
      }
      return;
    }
  }
  
  public void setBuglyEnable(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_bugly_enable", paramBoolean).commit();
      return;
    }
  }
  
  public void setCanAIAExtension(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_can_aia_extension", paramBoolean).commit();
      return;
    }
  }
  
  public void setCanInsertAdInSepcialSite(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_can_insert_ad_in_specialsite", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setCanIpv6Proxy(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_can_ipv6_proxy", paramBoolean).commit();
      return;
    }
  }
  
  public void setCanUseAdBlockUnderDirect(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_can_use_adblock_under_direct", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setCanUseDynamicCanvasGpu(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_can_use_dynamic_canvas_gpu", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setCanUseQProxyUnderProxy(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_can_use_qproxy_under_proxy", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setCanUseX5Jscore(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putBoolean("key_tbs_can_use_x5_jscore", paramBoolean).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void setCanUseX5JscoreNewAPI(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putBoolean("key_tbs_can_use_x5_jscore_new_api", paramBoolean).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void setCheckUseX5CookiePathPolicyEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_check_x5cookie_path_policy_enable", paramBoolean).commit();
      return;
    }
  }
  
  public void setCommonConfigListContent(int paramInt, String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("common_config_list_content_");
      localStringBuilder.append(paramInt);
      localEditor.putString(localStringBuilder.toString(), paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setCommonConfigMd5(int paramInt, String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("common_config_list_md5_");
      localStringBuilder.append(paramInt);
      localEditor.putString(localStringBuilder.toString(), paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setCommonConfigTime(int paramInt1, int paramInt2)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("common_config_list_time_");
      localStringBuilder.append(paramInt1);
      localEditor.putInt(localStringBuilder.toString(), paramInt2);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setConnectionTimeOut(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramString != null)
      {
        if (paramString.length() <= 0) {
          return;
        }
        paramString = paramString.split("\\|");
        if (paramString.length != 2) {
          return;
        }
      }
    }
    try
    {
      int i = Integer.parseInt(paramString[0]);
      int j = Integer.parseInt(paramString[1]);
      this.mPreferenceEditor.putInt("key_wifi_to", i);
      this.mPreferenceEditor.putInt("key_grps_to", j);
      if (this.mBreakCommit) {
        break label147;
      }
      this.mPreferenceEditor.commit();
      return;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    this.mPreferenceEditor.putInt("key_wifi_to", 0);
    this.mPreferenceEditor.putInt("key_grps_to", 0);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    label147:
    return;
    return;
  }
  
  public void setContentCacheEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_content_cache_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setConvertGetDownloadfileinterceptswitch(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramString != null) {
        if (paramString.length() < 1) {
          return;
        }
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_download_intercept_switch", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
    return;
  }
  
  public void setConvertGetToPostLevel(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramString != null) {
        if (paramString.length() < 1) {
          return;
        }
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_can_convert_get_to_post", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
    return;
  }
  
  public void setCorePageDurationPrefs(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putString("key_mtt_core_page_duration", paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setCrashInspectionDisableIntervalHours(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      int i = 48;
      try
      {
        int j = Integer.parseInt(paramString);
        i = j;
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
      }
      this.mPreferenceEditor.putInt("CRASH_INSPECTION_DISABLE_INTERVAL_HOURS", i);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setCrashInspectionDisableMaxTimes(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      int i = 3;
      try
      {
        int j = Integer.parseInt(paramString);
        i = j;
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
      }
      this.mPreferenceEditor.putInt("CRASH_INSPECTION_DISABLE_MAX_TIMES", i);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setCrashInspectionEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("CRASH_INSPECTION_ENABLED", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setCustomedWupAddress(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_test_environment_wup_address", paramString).commit();
      return;
    }
  }
  
  public void setDirectAdblockSwitcherLevel(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramString != null) {
        if (paramString.length() < 1) {
          return;
        }
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_direct_adblock_switcher_level", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
    return;
  }
  
  public void setDiskCacheSizeEnable(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putBoolean("key_disk_cache_size", paramBoolean).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void setDomainTime(byte paramByte, int paramInt)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("miniqb_pref_domain");
      localStringBuilder.append(paramByte);
      localEditor.putInt(localStringBuilder.toString(), paramInt);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setDowloadSizeThreshold(float paramFloat)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putFloat("key_dowload_size_threshold", paramFloat).commit();
      return;
    }
  }
  
  public void setDownloadInQB(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_downloadinqb_enable", paramBoolean).commit();
      return;
    }
  }
  
  public void setDownloadInTencentFile(int paramInt)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putInt("key_download_in_tencentfile_enable", paramInt).commit();
      return;
    }
  }
  
  public void setDownloadInterceptToQBPopMenuStyle(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_download_intercept_to_qb_pop_menu_style", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
  }
  
  public void setDownloadfileinterceptFileType(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramString != null) {
        if (paramString.length() < 1) {
          return;
        }
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_download_intercept_filetype", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
    return;
  }
  
  public void setEnableInterceptDidfailPageFinished(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_intercept_didfail_onpagefinished", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setEnableLogs(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_enable_logs", paramBoolean).commit();
      return;
    }
  }
  
  public void setEnableRsaAesEncrypt(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_preference_enable_rsa_aes_encrypt", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setEnvironmentType(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_environment_type", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
  }
  
  public void setExplorerCharacter(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramString != null) {
        if (paramString.length() < 1) {
          return;
        }
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_explorer_character", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
    return;
  }
  
  public void setExtendRuleMD5(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_extend_rule_md5", paramString).commit();
      return;
    }
  }
  
  public void setExtendRuleUpdated(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_extend_rule_pdated", paramBoolean).commit();
      return;
    }
  }
  
  public void setExtendRuleVersion(int paramInt)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putInt("key_extend_rule_version", paramInt);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setFaceAREngineName(String paramString)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putString("key_face_engine_name", paramString).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void setFakeLoginEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor != null)
      {
        localEditor.putBoolean("TBS_FAKE_LOGIN_ENABLED", paramBoolean);
        if (!this.mBreakCommit) {
          this.mPreferenceEditor.commit();
        }
      }
    }
  }
  
  public void setFormerTbsVersion(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      paramString = localEditor.putString("key_tbs_former_tbscore_version", paramString);
      if (!this.mBreakCommit) {
        editorApply(paramString);
      }
      return;
    }
  }
  
  public void setFramePerformanceRecordEnable(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_performance_record_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setGPUEnable(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_prefernce_gpu_state", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setGameSandbox(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return;
    }
    getPreference().edit().putBoolean("key_tbs_game_fw_sandbox", paramBoolean).commit();
  }
  
  public void setGameServiceEnv(int paramInt)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramInt <= 2)
      {
        if (paramInt < 0) {
          return;
        }
        getPreference().edit().putInt("key_tbs_game_fw_service_env", paramInt).commit();
        return;
      }
      return;
    }
  }
  
  public void setGinJavaMapEraseDisabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_gin_java_map_erase_disable", paramBoolean).commit();
      return;
    }
  }
  
  public void setHasEverLogin(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_has_ever_login", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setHasReportLauncherPkgName(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_has_report_launcher_pkg_name", paramBoolean).commit();
      return;
    }
  }
  
  public void setHideAdDialogEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_user_adhide_dialog_switch", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setHitRateEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_hitrate_report_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setHttpDNSEncryptKey(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_tbs_httpdns_encryptkey", paramString).commit();
      return;
    }
  }
  
  public void setHttpDNSServerIP(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_tbs_httpdns_server_ip", paramString).commit();
      return;
    }
  }
  
  public void setHttps0RTTEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_https_0rtt", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setIsCheckJsDomain(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_is_check_jsdomain", paramBoolean).commit();
      return;
    }
  }
  
  public void setIsOpenDebugTbsEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("OPEN_DEBUGTBS_ENABLED", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setIsShowGuessYourFav(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_myvideo_show_guess_your_fav", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setIsUploadPvinWup(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_isinwup_upload_pv", paramBoolean).commit();
      return;
    }
  }
  
  public void setIsUseThirdPartyAdRules(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_is_use_thirdparty_adrules", paramBoolean).commit();
      return;
    }
  }
  
  public void setIsX5ProEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      try
      {
        localEditor.putBoolean("key_x5_pro_enabled", paramBoolean);
        if (!this.mBreakCommit)
        {
          this.mPreferenceEditor.commit();
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return;
    }
  }
  
  public void setJniRegisterEnabled(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putBoolean("key_jni_register_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void setJsonSizeForPV(int paramInt)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramInt <= 1024) {
        return;
      }
      getPreference().edit().putInt("key_json_size_for_pv", paramInt).commit();
      return;
    }
  }
  
  public void setKingCardUserProxyJudgeCondition(String paramString)
  {
    if (getPreference() != null) {
      if (this.mPreferenceEditor == null) {
        return;
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    if (i < 0) {
      return;
    }
    getPreference().edit().putInt("key_kc_proxy_stragety", i).commit();
    return;
  }
  
  public void setLastCheckBasicConfigDate(long paramLong, byte paramByte)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("key_last_check_basic_config_date");
      ((StringBuilder)localObject).append(paramByte);
      localObject = ((StringBuilder)localObject).toString();
      getPreference().edit().putLong((String)localObject, paramLong).commit();
      return;
    }
  }
  
  public void setLastCheckWupConfigDate(long paramLong)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putLong("key_last_check_wup_config_date", paramLong).commit();
      return;
    }
  }
  
  public void setLastConnectedFreeWifi(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_wifi_last_connected_free_wifi", paramString).commit();
      return;
    }
  }
  
  public void setLastDomainListFromTestServer(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      editorApply(getPreference().edit().putBoolean("key_last_wup_domain_list_from_wup_server", paramBoolean));
      return;
    }
  }
  
  public void setLastMiniqbVersionForPrefs(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
      editorApply(getPreference().edit().putString("key_last_miniqb_version_for_prefs", paramString));
      return;
    }
  }
  
  public void setLastResolvedWupAddress(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("http://");
      localStringBuilder.append(paramString);
      localStringBuilder.append(":8080");
      getPreference().edit().putString("key_last_resovled_wup_address", localStringBuilder.toString()).commit();
      return;
    }
  }
  
  public void setLastWupReqFromTestEnv(boolean paramBoolean, String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      SharedPreferences.Editor localEditor = getPreference().edit();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("key_last_req_from_test_evn_");
      localStringBuilder.append(paramString);
      editorApply(localEditor.putBoolean(localStringBuilder.toString(), paramBoolean));
      return;
    }
  }
  
  public void setLongPressToQBPopMenuStyle(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_long_press_to_qb_pop_menu_style", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
  }
  
  public void setMSEEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_mse_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setMarkerAREngineName(String paramString)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putString("key_marker_engine_name", paramString).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void setMergeQBGuid(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_should_merger_qb_guid", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setMiniQBPref(String paramString1, String paramString2)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("miniqb_pref_pref");
      localStringBuilder.append(paramString1);
      localEditor.putString(localStringBuilder.toString(), paramString2);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setMiniQBPreferenceUpdateTime(int paramInt)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor = localEditor.putInt("key_miniqb_preference_update_time", paramInt);
      if (!this.mBreakCommit) {
        editorApply(localEditor);
      }
      return;
    }
  }
  
  public void setMiniQBStatVersion(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      paramString = localEditor.putString("key_miniqb_stat_version_name", paramString);
      if (!this.mBreakCommit) {
        editorApply(paramString);
      }
      return;
    }
  }
  
  public void setNeedPreInitX5Core(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("NEED_PREINIT_X5_CORE", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setNightModeInLongPressswitch(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramString != null) {
        if (paramString.length() < 1) {
          return;
        }
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_night_mode_in_long_press_switch", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
    return;
  }
  
  public void setNotifyDownloadQBTimesLimit(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_notify_download_qb_times_limit", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
  }
  
  public void setOpenWifiProxyEnableByWUP(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_open_wifi_proxy_wup", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setPluginListLastTime(long paramLong)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putLong("key_pluginlist_last_pull_pluignList", paramLong).commit();
      return;
    }
  }
  
  public void setPluginListRspMD5(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_plugin_list_md5", paramString).commit();
      return;
    }
  }
  
  public void setPreferenceUpdateTime(int paramInt)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor = localEditor.putInt("key_preference_update_time", paramInt);
      if (!this.mBreakCommit) {
        editorApply(localEditor);
      }
      return;
    }
  }
  
  public void setPrerenderPkgName(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      try
      {
        localEditor.putString("key_prerender_package_names", paramString);
        if (!this.mBreakCommit)
        {
          this.mPreferenceEditor.commit();
          return;
        }
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
      }
      return;
    }
  }
  
  public void setPrerenderSwitch(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      try
      {
        localEditor.putInt("key_prerender_swtich", Integer.parseInt(paramString));
        if (!this.mBreakCommit)
        {
          this.mPreferenceEditor.commit();
          return;
        }
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
      }
      return;
    }
  }
  
  public void setPushSvrNotifyUpdatePluginList(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_have_plugin_push_request", paramBoolean).commit();
      return;
    }
  }
  
  public void setQBHavePullPluginListYet(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_plugin_list_succ2170", paramBoolean).commit();
      return;
    }
  }
  
  public void setQQInterruptApkType(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_qq_interrupt_apk_type", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
  }
  
  public void setQQInterruptNotApkType(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_qq_interrupt_not_apk_type", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
  }
  
  public void setQbIconTypeInLongClick(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramString != null) {
        if (paramString.length() < 1) {
          return;
        }
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_qb_icon_type_in_long_click_popup", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
    return;
  }
  
  public void setQuicProxySwitch(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putBoolean("key_quic_proxy_switch", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void setQvodSupportType(int paramInt)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putInt("key_qvod_support_type", paramInt);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setReportCallWebviewApiOnWrongThreadEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_call_webview_api_on_wrong_thread", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setReportFailedGuid(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putString("key_report_failed_tbs_guid", paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setRequestFailedTime(String paramString, long paramLong)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      SharedPreferences.Editor localEditor = getPreference().edit();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("key_request_failed_time_prefix_");
      localStringBuilder.append(paramString);
      localEditor.putLong(localStringBuilder.toString(), paramLong).commit();
      return;
    }
  }
  
  public void setRestDbTablePlugin(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_reset_db_table_plugin", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setRestDbTablePluginFor5_3(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_reset_db_table_plugin_5_3", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setRestrictErrorPageReloadSecond(int paramInt)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putInt("key_tbs_restrict_errorpage_reload_second", paramInt).commit();
      return;
    }
  }
  
  public void setSPAAdPageReportEnable(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putBoolean("key_spa_ad_page_report", paramBoolean).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void setSWApiExecResultUploadList(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putString("key_sw_api_exec_result_upload_list", paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setSdcardDiskCacheEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_sdcard_disk_cache_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setSessionPersistentEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_session_persistent", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setSharpPEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_sharpp_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setSharppDefaultEnable(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_sharpp_defalut_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setShouldInterceptJsapiRequest(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_should_intercept_jsapi_request", paramBoolean).commit();
      return;
    }
  }
  
  public void setSniffDisableList(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_sniff_disable_domains", paramString).commit();
      return;
    }
  }
  
  public void setSntpTimeAndSysTemTime(long paramLong1, long paramLong2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setSntpTime:");
    localStringBuilder.append(paramLong1);
    localStringBuilder.append("systemtime:");
    localStringBuilder.append(paramLong2);
    LogUtils.d("SntpTime", localStringBuilder.toString());
    this.mPreference.edit().putLong("key_sntp_time", paramLong1).commit();
    this.mPreference.edit().putLong("key_last_system_time", paramLong1).commit();
  }
  
  public void setSubResourcePerformanceEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_enable_subresource_performance", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setSystemPopMenuStyle(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_stystem_pop_menu_style", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
  }
  
  public void setTBSGeneralFeatureSwitch(String paramString1, String paramString2)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      String str = TbsBaseModuleShell.getCoreInfoFetcher().getCoreVersion();
      Object localObject = getPreference().edit();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("key_tbs_general_feature_switch_");
      localStringBuilder.append(paramString1.toLowerCase());
      paramString2 = ((SharedPreferences.Editor)localObject).putInt(localStringBuilder.toString(), Integer.parseInt(paramString2));
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("key_tbs_general_feature_switch_core_version_");
      ((StringBuilder)localObject).append(paramString1.toLowerCase());
      paramString2.putString(((StringBuilder)localObject).toString(), str).commit();
      return;
    }
  }
  
  public void setTBSGeneralFeatureSwitch(String paramString, boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void setTBSPickedDefaultBrowser(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_tbs_picked_default_browser", paramString).commit();
      return;
    }
  }
  
  public void setTBSPickedDefaultFileOpener(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_tbs_picked_default_file_opener", paramString).commit();
      return;
    }
  }
  
  public void setTbsAdHttpProxySwitch(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putBoolean("TBS_AD_HTTP_PROXY_SWITCH", paramBoolean).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void setTbsAdReinstallTipsShowTime(long paramLong)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putLong("key_tbs_ad_reinstall_tips_show_time", paramLong);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setTbsAllowLoaddataWithCSP(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_tbs_allow_loadata_with_csp", paramBoolean).commit();
      return;
    }
  }
  
  public void setTbsGameFwLoginUrl(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_tbs_game_fw_login_url", paramString).commit();
      return;
    }
  }
  
  public void setTbsGameFwPayUrl(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_tbs_game_fw_pay_url", paramString).commit();
      return;
    }
  }
  
  public void setTbsGameFwShareUrl(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_tbs_game_fw_share_url", paramString).commit();
      return;
    }
  }
  
  public void setTbsImpatientNetworkSwitch(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return;
    }
    getPreference().edit().putBoolean("key_tbs_impatient_network_switch", paramBoolean).commit();
  }
  
  public void setTbsInfoUploadArguments(String paramString)
  {
    String[] arrayOfString;
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramString != null)
      {
        if (paramString.length() <= 0) {
          return;
        }
        arrayOfString = paramString.split("\\|");
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("setTbsInfoUploadArguments arguments");
        localStringBuilder.append(paramString);
        LogUtils.d("tbsInfo", localStringBuilder.toString());
        if ((!paramString.equalsIgnoreCase("0")) && (!arrayOfString[0].equalsIgnoreCase("0"))) {
          if ((arrayOfString.length != 5) && (arrayOfString.length != 6)) {
            return;
          }
        }
      }
    }
    try
    {
      if (!arrayOfString[0].equalsIgnoreCase("1")) {
        break label353;
      }
      paramString = this.mPreferenceEditor;
      bool = true;
      paramString.putBoolean("key_tbs_info_upload_argument_switcher", true);
      int i = Integer.parseInt(arrayOfString[4]);
      this.mPreferenceEditor.putInt("key_tbs_info_upload_argument_apn", i);
      if (arrayOfString.length != 6) {
        break label207;
      }
      i = Integer.parseInt(arrayOfString[5]);
      paramString = this.mPreferenceEditor;
      if (i != 1) {
        break label360;
      }
    }
    catch (Exception paramString)
    {
      for (;;)
      {
        label207:
        continue;
        boolean bool = false;
      }
    }
    paramString.putBoolean("key_tbs_info_upload_needproxy", bool);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    LogUtils.d("tbsInfo", "setTbsInfoUploadArguments successful");
    return;
    this.mPreferenceEditor.putBoolean("key_tbs_info_upload_argument_switcher", false);
    this.mPreferenceEditor.putInt("key_tbs_info_upload_argument_apn", 4);
    this.mPreferenceEditor.putBoolean("key_tbs_info_upload_needproxy", false);
    if (!this.mBreakCommit)
    {
      this.mPreferenceEditor.commit();
      return;
      this.mPreferenceEditor.putBoolean("key_tbs_info_upload_argument_switcher", false);
      this.mPreferenceEditor.putInt("key_tbs_info_upload_argument_apn", 4);
      this.mPreferenceEditor.putBoolean("key_tbs_info_upload_needproxy", false);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
    label353:
    return;
    return;
  }
  
  public void setTbsLogCacheStrategyPrefs(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putString("key_tbslog_cache_strategy_prefs", paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setTbsLogEnablePrefs(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putString("key_tbslog_enable_prefs", paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setTbsLogFlushStrategyPrefs(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putString("key_tbslog_flush_strategy_prefs", paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setTbsLogReportPrefs(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putString("key_tbslog_report_prefs", paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setTbsPageInfoUploadMaxCount(String paramString)
  {
    if (getPreference() == null) {
      return;
    }
    try
    {
      this.mPreferenceEditor.putInt("key_tbs_page_load_info_upload_maxcount", Integer.parseInt(paramString));
      if (!this.mBreakCommit)
      {
        this.mPreferenceEditor.commit();
        return;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void setTbsVideoPreferenceUpdateTime(int paramInt)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor = localEditor.putInt("key_tbsvideo_preference_update_time", paramInt);
      if (!this.mBreakCommit) {
        editorApply(localEditor);
      }
      return;
    }
  }
  
  public void setTbsWebviewSearchEngineUrl(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putString("key_tbs_webview_search_engine_url", paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setTpgEnabledType(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      int i = Integer.parseInt(paramString);
      this.mPreferenceEditor.putInt("key_tpg_enabled", i);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setTuringDOAID(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putString("TBS_AD_OAID", paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setTuringDTAID(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putString("TBS_AD_TAID", paramString);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setUnSuccessPluginPushRequestCount(int paramInt)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putInt("key_unsuccess_start_plugin_request_count", paramInt).commit();
      return;
    }
  }
  
  public void setUseSmartAdFilter(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_use_smart_adfilter", paramBoolean).commit();
      return;
    }
  }
  
  public void setVideoIsDefaultFullscreen(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_video_is_default_fullscreen", paramBoolean).commit();
      return;
    }
  }
  
  public void setVideoMaxBuffSize(int paramInt)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putInt("key_video_maxbuffsize", paramInt).commit();
      return;
    }
  }
  
  public void setVideoMinBuffSize(int paramInt)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putInt("key_video_minbuffsize", paramInt).commit();
      return;
    }
  }
  
  public void setVideoPreloadSwitch(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_video_preload", paramBoolean).commit();
      return;
    }
  }
  
  public void setVideoProductJsApi(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setVideoProductJsApi");
      localStringBuilder.append(paramBoolean);
      LogUtils.d("riceVideo", localStringBuilder.toString());
      getPreference().edit().putBoolean("key_video_product_jsapi", paramBoolean).commit();
      return;
    }
  }
  
  public void setVideoProductOpenQb(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setVideoProductOpenQb");
      localStringBuilder.append(paramBoolean);
      LogUtils.d("riceVideo", localStringBuilder.toString());
      getPreference().edit().putBoolean("key_video_product_openqb", paramBoolean).commit();
      return;
    }
  }
  
  public void setVideoSameLayer(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_video_same_layer", paramBoolean).commit();
      return;
    }
  }
  
  public void setWXPCDefaultEnable(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_wxpic_default_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setWXPCEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_wxpic_enabled", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setWasmDisabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_wasm_disable", paramBoolean).commit();
      return;
    }
  }
  
  public void setWifiBrandInfo(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("key_brand_info_cache", paramString).commit();
      return;
    }
  }
  
  public void setWifiDownloadOrInstDlgType(int paramInt)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putInt("key_wifi_download_or_inst_dlg_type", paramInt).commit();
      return;
    }
  }
  
  public void setWifiEnableConfigUpload(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_wifi_enable_config_upload", paramBoolean).commit();
      return;
    }
  }
  
  public void setWifiHasAuthorizeDownload(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_wifi_has_authorize_download", paramBoolean).commit();
      return;
    }
  }
  
  public void setWifiInfoAvailableTime(long paramLong)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putLong("key_wifi_info_available_time_fix", paramLong).commit();
      return;
    }
  }
  
  public void setWifiLastCheckTime(long paramLong)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putLong("key_wifi_last_check_time", paramLong).commit();
      return;
    }
  }
  
  public void setWifiNOQBSwitchJumpUrl(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putString("WIFI_NOQB_SWITCH_JUMPURL", paramString).commit();
      return;
    }
  }
  
  public void setWupEncryptType(byte paramByte)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      editorApply(getPreference().edit().putInt("key_wup_rsa_aes_encrypt_type", paramByte).putLong("key_last_modify_wup_encrypt_time", System.currentTimeMillis()));
      return;
    }
  }
  
  public void setWupListFailedEver(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      localEditor.putBoolean("key_wup_server_ever_failed", paramBoolean);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setWupServantAvailableTime(String paramString, long paramLong)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      SharedPreferences.Editor localEditor = getPreference().edit();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("key_wp_svt_av_time_pref");
      localStringBuilder.append(paramString);
      editorApply(localEditor.putLong(localStringBuilder.toString(), paramLong));
      return;
    }
  }
  
  public void setWupToken(String paramString)
  {
    if (getPreference() != null)
    {
      SharedPreferences.Editor localEditor = this.mPreferenceEditor;
      if (localEditor == null) {
        return;
      }
      paramString = localEditor.putString("key_wup_token", paramString);
      if (!this.mBreakCommit) {
        editorApply(paramString);
      }
      return;
    }
  }
  
  public void setX5CookieIsolationEnabled(boolean paramBoolean)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      getPreference().edit().putBoolean("key_x5cookie_isolation", paramBoolean).commit();
      return;
    }
  }
  
  public void setX5JsCoreBufferSwitch(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putBoolean("key_x5_jscore_can_use_buffer", paramBoolean).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void setX5JsCoreUseDeprecated(boolean paramBoolean)
  {
    if (getPreference() == null) {
      return;
    }
    SharedPreferences.Editor localEditor = this.mPreferenceEditor;
    if (localEditor != null)
    {
      localEditor.putBoolean("key_x5jscore_use_deprecated", paramBoolean).commit();
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
    }
  }
  
  public void setX5LongClickPopupMenuSubText(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      String str = paramString;
      if (paramString == null) {
        str = "";
      }
      this.mPreferenceEditor.putString("key_long_click_popup_menu_subtext", str);
      if (!this.mBreakCommit) {
        this.mPreferenceEditor.commit();
      }
      return;
    }
  }
  
  public void setsaveWebPageInLongPressswitch(String paramString)
  {
    if (getPreference() != null)
    {
      if (this.mPreferenceEditor == null) {
        return;
      }
      if (paramString != null) {
        if (paramString.length() < 1) {
          return;
        }
      }
    }
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    this.mPreferenceEditor.putInt("key_save_webpage_in_long_press_switch", i);
    if (!this.mBreakCommit) {
      this.mPreferenceEditor.commit();
    }
    return;
    return;
  }
  
  public boolean shouldInterceptJsapiRequest()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_should_intercept_jsapi_request", true);
  }
  
  public boolean shouldUseSmartAdFilter()
  {
    if (getPreference() == null) {
      return true;
    }
    return getPreference().getBoolean("key_use_smart_adfilter", false);
  }
  
  private static class InstanceHolder
  {
    private static final PublicSettingManager INSTANCE = new PublicSettingManager(null);
  }
  
  class SafetySharedPreference
    implements SharedPreferences
  {
    private SharedPreferences mPreference;
    
    SafetySharedPreference(SharedPreferences paramSharedPreferences)
    {
      this.mPreference = paramSharedPreferences;
    }
    
    public boolean contains(String paramString)
    {
      return this.mPreference.contains(paramString);
    }
    
    public SharedPreferences.Editor edit()
    {
      return this.mPreference.edit();
    }
    
    public Map<String, ?> getAll()
    {
      return this.mPreference.getAll();
    }
    
    public boolean getBoolean(String paramString, boolean paramBoolean)
    {
      try
      {
        paramBoolean = this.mPreference.getBoolean(paramString, paramBoolean);
        return paramBoolean;
      }
      catch (Exception paramString)
      {
        for (;;) {}
      }
      return false;
    }
    
    public float getFloat(String paramString, float paramFloat)
    {
      try
      {
        paramFloat = this.mPreference.getFloat(paramString, paramFloat);
        return paramFloat;
      }
      catch (Exception paramString)
      {
        for (;;) {}
      }
      return 0.0F;
    }
    
    public int getInt(String paramString, int paramInt)
    {
      try
      {
        paramInt = this.mPreference.getInt(paramString, paramInt);
        return paramInt;
      }
      catch (Exception paramString)
      {
        for (;;) {}
      }
      return 0;
    }
    
    public long getLong(String paramString, long paramLong)
    {
      try
      {
        paramLong = this.mPreference.getLong(paramString, paramLong);
        return paramLong;
      }
      catch (Exception paramString)
      {
        for (;;) {}
      }
      return 0L;
    }
    
    public String getString(String paramString1, String paramString2)
    {
      try
      {
        paramString1 = this.mPreference.getString(paramString1, paramString2);
        return paramString1;
      }
      catch (Exception paramString1)
      {
        paramString1.printStackTrace();
      }
      return "";
    }
    
    public Set<String> getStringSet(String paramString, Set<String> paramSet)
    {
      try
      {
        paramString = this.mPreference.getStringSet(paramString, paramSet);
        return paramString;
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
      return new HashSet();
    }
    
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
    {
      this.mPreference.registerOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
    }
    
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
    {
      this.mPreference.registerOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\settings\PublicSettingManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */