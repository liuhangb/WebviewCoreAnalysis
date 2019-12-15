package com.tencent.tbs.common.config;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.MiniProgramUtil;

public class Configuration
{
  private static final String EATURE_NAME_CLICK_IMAGE_SCAN = "click_image_scan";
  private static final String EATURE_NAME_LONG_PRESS_MENU_IMAGE_SCAN = "long_press_menu_image_scan";
  private static final String EATURE_NAME_LONG_PRESS_MENU_SCREEN_SHOT = "long_press_screen_shot";
  private static final boolean[] ENABLE_ACTIVE_QB;
  private static final boolean[] ENABLE_ACTIVE_QBBACKHEARTBEAT = { 0, 0, 0, 0, 0, 0 };
  private static final boolean[] ENABLE_APP_LONG_PRESS_MENU;
  private static final boolean[] ENABLE_APP_LONG_PRESS_MENU_IMAGE_QUERY;
  private static final boolean[] ENABLE_BLOCK_LOCALHOST_REQUEST;
  private static final boolean[] ENABLE_BOTTOM_BAR;
  private static final boolean[] ENABLE_BUBBLE_AD;
  private static final boolean[] ENABLE_BUBBLE_MINIQB_AD;
  private static final boolean[] ENABLE_CLICK_IMAGE_SCAN;
  private static final boolean[] ENABLE_DETECT_DOWNLOAD_PKGNAME;
  private static final boolean[] ENABLE_DOWNLOAD_INTERCEPT_PLUGIN;
  private static final boolean[] ENABLE_FILE_CHOOSER_TBS;
  private static final boolean[] ENABLE_FILE_READER;
  private static final boolean[] ENABLE_FILE_READER_MENU;
  private static final boolean[] ENABLE_FULLSCREEN_PLAYER;
  private static final boolean[] ENABLE_GAME_EMBEDDED_FRAMEWORK;
  private static final boolean[] ENABLE_GAME_EMBEDDED_FRAMEWORK_LOAD_WXPAY_BACKGROUND;
  private static final boolean[] ENABLE_GAME_FRAMEWORK;
  private static final boolean[] ENABLE_GAME_PLAYER;
  private static final boolean[] ENABLE_GAME_SANDBOX;
  private static final boolean[] ENABLE_GDT_ADVERTISEMENT;
  private static final boolean[] ENABLE_HEADSUP_RISK_WEBSITE;
  private static final boolean[] ENABLE_HEADSUP_TAOBAO_LINK;
  private static final boolean[] ENABLE_HEADSUP_TRANSCODING_PAGE;
  private static final boolean[] ENABLE_ILIVE_SDK;
  private static final boolean[] ENABLE_INSTALL_APP_USE_YYB;
  private static final boolean[] ENABLE_INTERCEPT_DOWNLOAD_REQUEST;
  private static final boolean[] ENABLE_JSAPI_INSTALL_APP;
  private static final boolean[] ENABLE_JSAPI_START_DOWNLOAD;
  private static final boolean[] ENABLE_JS_DOWNLOAD_OPTIMIZATION;
  private static final boolean[] ENABLE_LONG_PRESS_MENU;
  private static final boolean[] ENABLE_LONG_PRESS_MENU_EXPLORER;
  private static final boolean[] ENABLE_LONG_PRESS_MENU_IMAGE_SCAN;
  private static final boolean[] ENABLE_LONG_PRESS_MENU_NIGHT_MODE;
  private static final boolean[] ENABLE_LONG_PRESS_MENU_OPEN_IN_BROWSER;
  private static final boolean[] ENABLE_LONG_PRESS_MENU_REFRESH;
  private static final boolean[] ENABLE_LONG_PRESS_MENU_SCREEN_SHOT;
  private static final boolean[] ENABLE_LONG_PRESS_MENU_SELECT_COPY;
  private static final boolean[] ENABLE_LONG_PRESS_QUICK_SECOND_MENU_QQ_THIRDAPP;
  private static final boolean[] ENABLE_LONG_PRESS_QUICK_SELECT_COPY;
  private static final boolean[] ENABLE_LONG_PRESS_TIMECOST_OPT;
  private static final boolean[] ENABLE_MEIZU_NIGHT_MODE;
  private static final boolean[] ENABLE_MID_PAGE_ADVERTISEMENT;
  private static final boolean[] ENABLE_MID_PAGE_HEADSUP;
  private static final boolean[] ENABLE_MID_PAGE_NIGHTFULLSCREENREAD_ONLONGPRESS;
  private static final boolean[] ENABLE_MID_PAGE_QB_OPENBROWSER_ONLONGPRESS;
  private static final boolean[] ENABLE_MID_PAGE_QB_SEARCH_ONLONGPRESS;
  private static final boolean[] ENABLE_MID_PAGE_QB_SILENT_DOWNLOAD;
  private static final boolean[] ENABLE_MID_PAGE_TRANSLATE_ONLONGPRESS;
  private static final boolean[] ENABLE_MID_PAGE_WEBVIEW_BOTTOMMONTAGE;
  private static final boolean[] ENABLE_MID_PAGE_WEBVIEW_BUBBLE;
  private static final boolean[] ENABLE_MINIQB_UPGRADE;
  private static final boolean[] ENABLE_MM_HELPER;
  private static final boolean[] ENABLE_NEW_LONG_PRESS_DOWNLOAD_INTERCEPT_OPEN_QB_METHOD;
  private static final boolean[] ENABLE_NEW_QB_DOWNLOAD_URL_STYLE;
  private static final boolean[] ENABLE_NEW_QB_DOWNLOAD_URL_STYLE_HTTPS;
  private static final boolean[] ENABLE_NIGHT_MODE;
  private static final boolean[] ENABLE_OPEN_DEBUGT_TBS;
  private static final boolean[] ENABLE_OPEN_URL_ON_LONG_PRESS;
  private static final boolean[] ENABLE_PLATFORM_REPORT;
  private static final boolean[] ENABLE_QBICON_QQ_SHINE;
  private static final boolean[] ENABLE_QB_PUREINCREASE = { 0, 0, 0, 0, 0, 0 };
  private static final boolean[] ENABLE_QQ_ERROR_PAGE_GAME;
  private static final boolean[] ENABLE_REINSTALL_TIPS_FOR_AD;
  private static final boolean[] ENABLE_REUSE_QBAPK;
  private static final boolean[] ENABLE_SAFE_DOWNLOAD_INTERCEPT;
  private static final boolean[] ENABLE_SCHEME_INTERCEPT_ACTIVE_QB;
  private static final boolean[] ENABLE_SCROLL_AD_WEBVIEW;
  private static final boolean[] ENABLE_SEARCH_ON_LONG_PRESS;
  private static final boolean[] ENABLE_SHOULD_INTERCEPT_MTTBROWSER;
  private static final boolean[] ENABLE_SHOULD_INTERCEPT_MTTBROWSER_USE_MINIQB;
  private static final boolean[] ENABLE_SPLICE_AD;
  private static final boolean[] ENABLE_SPLICE_MINIQB_AD;
  private static final boolean[] ENABLE_TBS_AD_LAND_PAGE_IN_DIALOG;
  private static final boolean[] ENABLE_TBS_AD_PLUGIN;
  private static final boolean[] ENABLE_TBS_AR;
  private static final boolean[] ENABLE_TBS_AUTO_DOWNLOAD_TBS_CORE;
  private static final boolean[] ENABLE_TBS_CLIPBOARD;
  private static final boolean[] ENABLE_TBS_HISTORY;
  private static final boolean[] ENABLE_TBS_IME_ASR;
  private static final boolean[] ENABLE_TBS_JSAPI;
  private static final boolean[] ENABLE_TBS_LOGMANAGER;
  private static final boolean[] ENABLE_TBS_RESOURCE_ADAPT;
  private static final boolean[] ENABLE_TRANSLATE_ON_LONG_PRESS;
  private static final boolean[] ENABLE_WEBCORE_LOAD_CHECK;
  private static final boolean[] ENABLE_WEB_ACCELERATOR_PREFETCH_RESOURCE;
  private static final boolean[] ENABLE_WECHAT_CRASH_MONITOR;
  private static final boolean[] ENABLE_WX_WHOLE_PAGE_TRANSLATE;
  private static final boolean[] ENABLE_X5_CAMERA;
  private static final String FEATURE_ENABLE_INSTALL_APP_USE_YYB = "enable_install_app_use_yyb";
  private static final String FEATURE_ENABLE_MEIZU_NIGHTMODE = "enable_meizu_nightmode";
  private static final String FEATURE_ENABLE_REINSTALL_TIPS_FOR_AD = "enable_reinstall_tips_for_ad";
  private static final String FEATURE_ENABLE_TBS_AD_LAND_PAGE_IN_DIALOG = "enable_tbs_ad_land_page_in_dialog";
  private static final String FEATURE_ENABLE_TBS_AUTO_DOWNLOAD_TBS_CORE = "enable_tbs_auto_download_tbs_core";
  private static final String FEATURE_ENABLE_WECHAT_CRASH_MONITOR = "enable_wechat_crash_monitor";
  private static final String FEATURE_JSAPI_ENABLE_INSTALL_APP = "jsapi_enable_install_app";
  private static final String FEATURE_JSAPI_ENABLE_START_DOWNLOAD = "jsapi_enable_start_download";
  private static final String FEATURE_JS_DOWNLOAD_OPTIMIZATION = "enable_js_download_optimization";
  private static final String FEATURE_MMHELPER = "mm_helper";
  private static final String FEATURE_NAME_ACTIVEQBBACK_FREQUENCY = "activeqbback_frequency";
  private static final String FEATURE_NAME_ACTIVE_QB = "active_qb";
  private static final String FEATURE_NAME_ACTIVE_QBBACKHEARTBEAT = "active_qbbackheartbeat";
  private static final String FEATURE_NAME_APP_LONG_PRESS_MENU = "app_long_press_menu";
  private static final String FEATURE_NAME_BLOCK_LOCALHOST_REQUEST = "block_localhost_request";
  private static final String FEATURE_NAME_BOTTOM_BAR = "bottom_bar";
  private static final String FEATURE_NAME_BUBBLE_AD = "bubble_ad";
  private static final String FEATURE_NAME_BUBBLE_MINIQB_AD = "bubble_miniqb_ad";
  private static final String FEATURE_NAME_DETECT_DOWNLOAD_PKGNAME = "detect_download_pkgname";
  private static final String FEATURE_NAME_DOWNLOAD_INTERCEPT_PLUGIN = "download_intercept_plugin";
  private static final String FEATURE_NAME_FILE_CHOOSER_TBS = "file_chooser_tbs";
  private static final String FEATURE_NAME_FILE_READER = "file_reader";
  private static final String FEATURE_NAME_FILE_READER_EXT = "file_reader_ext_";
  private static final String FEATURE_NAME_FILE_READER_MENU = "READER_MENU_";
  private static final String FEATURE_NAME_FULLSCREEN_PLAYER = "fullscreen_player";
  private static final String FEATURE_NAME_GAME_EMBEDDED_FRAMEWORK = "game_embedded_framework";
  private static final String FEATURE_NAME_GAME_EMBEDDED_FRAMEWORK_LOAD_WXPAY_BACKGROUND = "game_embedded_framework_load_wxpay_background";
  private static final String FEATURE_NAME_GAME_FRAMEWORK = "game_framework";
  private static final String FEATURE_NAME_GAME_PLAYER = "game_player";
  private static final String FEATURE_NAME_GAME_SANDBOX = "game_sandbox";
  private static final String FEATURE_NAME_GDT_ADVERTISEMENT = "gdt_advertisement";
  private static final String FEATURE_NAME_HEADSUP_RISK_WEBSITE = "headsup_risk_website";
  private static final String FEATURE_NAME_HEADSUP_TAOBAO_LINK = "headsup_taobao_link";
  private static final String FEATURE_NAME_HEADSUP_TRANSCODEING_PAGE = "headsup_transcoding_page";
  private static final String FEATURE_NAME_ILIVE_SDK = "ilive_sdk";
  private static final String FEATURE_NAME_INTERCEPT_DOWNLOAD_REQUEST = "intercept_download_request";
  private static final String FEATURE_NAME_JSAPI = "tbs_jsapi";
  private static final String FEATURE_NAME_LONG_PRESS_MENU = "long_press_menu";
  private static final String FEATURE_NAME_LONG_PRESS_MENU_EXPLORER = "long_press_menu_explorer";
  private static final String FEATURE_NAME_LONG_PRESS_MENU_IMAGE_QUERY = "long_press_menu_image_query";
  private static final String FEATURE_NAME_LONG_PRESS_MENU_NIGHT_MODE = "long_press_menu_night_mode";
  private static final String FEATURE_NAME_LONG_PRESS_MENU_OPEN_IN_BROWSER = "long_press_menu_open_in_browser";
  private static final String FEATURE_NAME_LONG_PRESS_MENU_REFRESH = "long_press_menu_refresh";
  private static final String FEATURE_NAME_LONG_PRESS_MENU_SELECT_COPY = "long_press_menu_select_copy";
  private static final String FEATURE_NAME_LONG_PRESS_QUICK_SECOND_MENU_QQ_THIRDAPP = "long_press_quick_second_menu_qq_thirdapp";
  private static final String FEATURE_NAME_LONG_PRESS_QUICK_SELECT_COPY = "long_press_quick_select_copy";
  private static final String FEATURE_NAME_LONG_PRESS_TIMECOST_OPT = "long_press_timecost_opt";
  private static final String FEATURE_NAME_MIDPAGE_HEADSUP = "midpage_headsup";
  private static final String FEATURE_NAME_MIDPAGE_NIGHTFULLSCREENREAD_ONLONGPRESS = "midpage_nightfullscreenread_onlongpress";
  private static final String FEATURE_NAME_MIDPAGE_TRANSLATE_ONLONGPRESS = "midpage_translate_onlongpress";
  private static final String FEATURE_NAME_MIDPAGE_WEBVIEW_BOTTOMMONTAGE = "midpage_webview_bottommontage";
  private static final String FEATURE_NAME_MIDPAGE_WEBVIEW_BUBBLE = "midpage_webview_bubble";
  private static final String FEATURE_NAME_MID_PAGE_ADVERTISEMENT = "mid_page_advertisement";
  private static final String FEATURE_NAME_MID_PAGE_QB_OPENBROWSER_ONLONGPRESS = "mid_page_qb_openbrowser_onlongpress";
  private static final String FEATURE_NAME_MID_PAGE_QB_SEARCH_ONLONGPRESS = "mid_page_qb_search_onlongpress";
  private static final String FEATURE_NAME_MID_PAGE_QB_SILENT_DOWNLOAD = "mid_page_qb_silent_download";
  private static final String FEATURE_NAME_MINIQB_UPGRADE = "miniqb_upgrade";
  private static final String FEATURE_NAME_NEW_LONG_PRESS_DOWNLOAD_INTERCEPT_OPEN_QB_METHOD = "new_long_press_download_intercept_open_qb_method";
  private static final String FEATURE_NAME_NEW_QB_DOWNLOAD_URL_STYLE = "new_qb_download_url_style";
  private static final String FEATURE_NAME_NEW_QB_DOWNLOAD_URL_STYLE_HTTPS = "new_qb_download_url_style_https";
  private static final String FEATURE_NAME_NIGHT_MODE = "invoke_qb_night_mode_and_fullscreen";
  private static final String FEATURE_NAME_OPEN_URL_ON_LONG_PRESS = "open_url_on_long_press";
  private static final String FEATURE_NAME_PLATFORM_REPORT = "platform_report";
  private static final String FEATURE_NAME_QBICON_QQ_SHINE = "qbicon_qq_shine";
  private static final String FEATURE_NAME_QB_PUREINCREASE = "qb_pureincrease";
  private static final String FEATURE_NAME_QQ_ERROR_PARE_GAME = "qq_error_page_little_game";
  private static final String FEATURE_NAME_REUSE_QBAPK = "reuse_qbapk";
  private static final String FEATURE_NAME_SAFE_DOWNLOAD_INTERCEPT = "safe_download_intercept";
  private static final String FEATURE_NAME_SCHEME_INTERCEPT_ACTIVE_QB = "scheme_intercept_active_qb";
  private static final String FEATURE_NAME_SEARCH_ON_LONG_PRESS = "search_on_long_press";
  private static final String FEATURE_NAME_SPLICE_AD = "splice_ad";
  private static final String FEATURE_NAME_SPLICE_MINIQB_AD = "splice_miniqb_ad";
  private static final String FEATURE_NAME_TBS_AR = "tbs_ar";
  private static final String FEATURE_NAME_TBS_HISTORY = "tbs_history";
  private static final String FEATURE_NAME_TBS_IME_ASR = "tbs_ime_asr";
  private static final String FEATURE_NAME_TRANSLATE_ON_LONG_PRESS = "translate_on_long_press";
  private static final String FEATURE_NAME_WEB_ACCELERATOR_PREFETCH_RESOURCE = "web_accelerator_prefetch_resource";
  private static final String FEATURE_NAME_WX_WHOLE_PAGE_TRANSLATE = "wx_whole_page_translate";
  private static final String FEATURE_NAME_X5_CAMERA = "x5_camera";
  private static final String FEATURE_SHOULD_INTERCEPT_MTTBROWSER = "should_intercept_mttbrowser";
  private static final String FEATURE_SHOULD_INTERCEPT_MTTBROWSER_USE_MINIQB = "should_intercept_mttbrowser_use_miniqb";
  private static final String FEATURE_TBS_AD_PLUGUN = "enable_tbs_ad_plugin";
  private static final String FEATURE_TBS_CLIPBOARD = "tbs_clipboard";
  private static final String FEATURE_TBS_QR_CODE_SHARE_ENABLE = "qr_code_share_enable";
  private static final String FEATURE_WEBCORE_MD5_CHECK = "enable_webcore_md5_check";
  private static final int HOST_APP_QB = 5;
  private static final int HOST_APP_QQ = 2;
  private static final int HOST_APP_QZONE = 3;
  private static final int HOST_APP_TBSDEMO = 4;
  private static final int HOST_APP_UNKOWN = 0;
  private static final int HOST_APP_WECHAT = 1;
  private static final String[] KNOWN_HOST_APPS = { "", "com.tencent.mm", "com.tencent.mobileqq", "com.qzone", "com.tencent.tbs", "com.tencent.mtt" };
  private static final String SCROLL_AD_WEBVIEW = "scroll_ad_webview";
  private static final boolean[] TBS_QR_CODE_SHARE_ENABLE;
  private static final String TBS_RESOURCE_ADAPT_CHECK = "enable_tbs_resource_adapt_check";
  private static String[] enableInMiniProgramList = { "long_press_menu", "long_press_menu_select_copy" };
  private static Configuration sConfig;
  private Context mApp;
  private String mAppName;
  private boolean mDebugMiniQBFlag;
  private int mHostApp;
  
  static
  {
    ENABLE_OPEN_URL_ON_LONG_PRESS = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_SEARCH_ON_LONG_PRESS = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_REUSE_QBAPK = new boolean[] { 1, 1, 1, 1, 0, 0 };
    ENABLE_GDT_ADVERTISEMENT = new boolean[] { 0, 0, 1, 0, 0, 0 };
    ENABLE_SPLICE_AD = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_SPLICE_MINIQB_AD = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_BUBBLE_AD = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_BUBBLE_MINIQB_AD = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_QBICON_QQ_SHINE = new boolean[] { 0, 0, 1, 0, 0, 0 };
    TBS_QR_CODE_SHARE_ENABLE = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_SCHEME_INTERCEPT_ACTIVE_QB = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_TBS_HISTORY = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_LONG_PRESS_MENU = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_LONG_PRESS_TIMECOST_OPT = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_LONG_PRESS_QUICK_SELECT_COPY = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_LONG_PRESS_MENU_SELECT_COPY = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_LONG_PRESS_MENU_REFRESH = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_APP_LONG_PRESS_MENU = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_APP_LONG_PRESS_MENU_IMAGE_QUERY = new boolean[] { 0, 0, 1, 0, 0, 0 };
    ENABLE_LONG_PRESS_MENU_EXPLORER = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_LONG_PRESS_MENU_OPEN_IN_BROWSER = new boolean[] { 0, 1, 1, 1, 1, 0 };
    ENABLE_BOTTOM_BAR = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_ACTIVE_QB = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_FILE_CHOOSER_TBS = new boolean[] { 0, 0, 1, 0, 0, 0 };
    ENABLE_LONG_PRESS_QUICK_SECOND_MENU_QQ_THIRDAPP = new boolean[] { 1, 1, 1, 1, 1, 1 };
    ENABLE_X5_CAMERA = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_TBS_IME_ASR = new boolean[] { 0, 0, 0, 0, 1, 0 };
    ENABLE_LONG_PRESS_MENU_NIGHT_MODE = new boolean[] { 1, 0, 0, 1, 1, 0 };
    ENABLE_LONG_PRESS_MENU_IMAGE_SCAN = new boolean[] { 0, 0, 0, 0, 1, 0 };
    ENABLE_INTERCEPT_DOWNLOAD_REQUEST = new boolean[] { 1, 0, 0, 1, 1, 0 };
    ENABLE_MID_PAGE_ADVERTISEMENT = new boolean[] { 1, 0, 1, 0, 1, 0 };
    ENABLE_BLOCK_LOCALHOST_REQUEST = new boolean[] { 0, 0, 0, 0, 1, 1 };
    ENABLE_FILE_READER = new boolean[] { 1, 1, 1, 0, 1, 0 };
    ENABLE_TRANSLATE_ON_LONG_PRESS = new boolean[] { 1, 0, 0, 1, 1, 1 };
    ENABLE_FILE_READER_MENU = new boolean[] { 1, 0, 1, 0, 1, 0 };
    ENABLE_HEADSUP_TAOBAO_LINK = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_HEADSUP_TRANSCODING_PAGE = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_HEADSUP_RISK_WEBSITE = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_TBS_JSAPI = new boolean[] { 1, 1, 1, 1, 1, 1 };
    ENABLE_MID_PAGE_QB_SILENT_DOWNLOAD = new boolean[] { 0, 0, 1, 0, 1, 0 };
    ENABLE_NIGHT_MODE = new boolean[] { 1, 0, 0, 1, 1, 0 };
    ENABLE_GAME_PLAYER = new boolean[] { 0, 0, 0, 1, 1, 0 };
    ENABLE_GAME_FRAMEWORK = new boolean[] { 1, 0, 0, 0, 1, 0 };
    ENABLE_GAME_SANDBOX = new boolean[] { 1, 1, 1, 1, 1, 1 };
    ENABLE_GAME_EMBEDDED_FRAMEWORK = new boolean[] { 0, 1, 0, 0, 1, 0 };
    ENABLE_GAME_EMBEDDED_FRAMEWORK_LOAD_WXPAY_BACKGROUND = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_SCROLL_AD_WEBVIEW = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_MID_PAGE_QB_SEARCH_ONLONGPRESS = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_MID_PAGE_QB_OPENBROWSER_ONLONGPRESS = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_MID_PAGE_TRANSLATE_ONLONGPRESS = new boolean[] { 1, 0, 1, 1, 1, 0 };
    ENABLE_MID_PAGE_NIGHTFULLSCREENREAD_ONLONGPRESS = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_MID_PAGE_WEBVIEW_BOTTOMMONTAGE = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_MID_PAGE_WEBVIEW_BUBBLE = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_MID_PAGE_HEADSUP = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_TBS_CLIPBOARD = new boolean[] { 0, 1, 1, 1, 1, 0 };
    ENABLE_WEB_ACCELERATOR_PREFETCH_RESOURCE = new boolean[] { 0, 0, 0, 0, 1, 0 };
    ENABLE_TBS_LOGMANAGER = new boolean[] { 1, 0, 0, 0, 1, 0 };
    ENABLE_SHOULD_INTERCEPT_MTTBROWSER = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_SHOULD_INTERCEPT_MTTBROWSER_USE_MINIQB = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_TBS_AR = new boolean[] { 0, 1, 1, 0, 1, 0 };
    ENABLE_ILIVE_SDK = new boolean[] { 0, 1, 1, 1, 1, 0 };
    ENABLE_FULLSCREEN_PLAYER = new boolean[] { 0, 1, 1, 0, 1, 0 };
    ENABLE_DETECT_DOWNLOAD_PKGNAME = new boolean[] { 0, 1, 1, 1, 1, 0 };
    ENABLE_MINIQB_UPGRADE = new boolean[] { 1, 1, 1, 1, 1, 0 };
    ENABLE_PLATFORM_REPORT = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_CLICK_IMAGE_SCAN = new boolean[] { 0, 0, 0, 0, 1, 1 };
    ENABLE_MM_HELPER = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_JSAPI_START_DOWNLOAD = new boolean[] { 1, 0, 0, 1, 1, 1 };
    ENABLE_JSAPI_INSTALL_APP = new boolean[] { 1, 0, 0, 1, 1, 1 };
    ENABLE_WEBCORE_LOAD_CHECK = new boolean[] { 0, 1, 0, 0, 0, 0 };
    ENABLE_TBS_RESOURCE_ADAPT = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_LONG_PRESS_MENU_SCREEN_SHOT = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_QQ_ERROR_PAGE_GAME = new boolean[] { 0, 0, 1, 0, 0, 0 };
    ENABLE_WX_WHOLE_PAGE_TRANSLATE = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_JS_DOWNLOAD_OPTIMIZATION = new boolean[] { 1, 1, 1, 1, 0, 0 };
    ENABLE_OPEN_DEBUGT_TBS = new boolean[] { 1, 0, 0, 0, 1, 0 };
    ENABLE_NEW_LONG_PRESS_DOWNLOAD_INTERCEPT_OPEN_QB_METHOD = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_NEW_QB_DOWNLOAD_URL_STYLE = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_NEW_QB_DOWNLOAD_URL_STYLE_HTTPS = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_SAFE_DOWNLOAD_INTERCEPT = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_TBS_AD_PLUGIN = new boolean[] { 1, 1, 1, 1, 1, 1 };
    ENABLE_MEIZU_NIGHT_MODE = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_DOWNLOAD_INTERCEPT_PLUGIN = new boolean[] { 0, 1, 1, 0, 0, 0 };
    ENABLE_REINSTALL_TIPS_FOR_AD = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_INSTALL_APP_USE_YYB = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_WECHAT_CRASH_MONITOR = new boolean[] { 0, 0, 0, 0, 0, 0 };
    ENABLE_TBS_AD_LAND_PAGE_IN_DIALOG = new boolean[] { 1, 1, 1, 1, 1, 1 };
    ENABLE_TBS_AUTO_DOWNLOAD_TBS_CORE = new boolean[] { 0, 1, 0, 0, 0, 0 };
    sConfig = null;
  }
  
  private Configuration(Context paramContext)
  {
    boolean bool = false;
    this.mDebugMiniQBFlag = false;
    this.mApp = paramContext.getApplicationContext();
    this.mAppName = this.mApp.getPackageName();
    this.mHostApp = 0;
    int i = 0;
    for (;;)
    {
      String[] arrayOfString = KNOWN_HOST_APPS;
      if (i >= arrayOfString.length) {
        break;
      }
      if (arrayOfString[i].equals(this.mAppName))
      {
        this.mHostApp = i;
        break;
      }
      i += 1;
    }
    try
    {
      if (paramContext.getSharedPreferences("debugtbsplugin_env_config", 0).getString("key_miniqb_debug_env", "0").equalsIgnoreCase("1")) {
        bool = true;
      }
      this.mDebugMiniQBFlag = bool;
      return;
    }
    catch (Exception paramContext) {}
  }
  
  private void checkHostApp()
  {
    int i = this.mHostApp;
    if ((i < 0) || (i >= KNOWN_HOST_APPS.length)) {
      this.mHostApp = 0;
    }
  }
  
  public static Configuration getInstance(Context paramContext)
  {
    try
    {
      if (sConfig == null) {
        sConfig = new Configuration(paramContext);
      }
      return sConfig;
    }
    finally {}
  }
  
  private int readFromPSM(String paramString)
  {
    int i = PublicSettingManager.getInstance().getTBSGeneralFeatureSwitch(paramString);
    paramString = new StringBuilder();
    paramString.append("readFromPSM value is ");
    paramString.append(i);
    paramString.toString();
    return i;
  }
  
  private boolean readFromPSM(IX5WebViewBase paramIX5WebViewBase, String paramString, boolean paramBoolean)
  {
    if (MiniProgramUtil.isInMiniProgram(paramIX5WebViewBase))
    {
      paramIX5WebViewBase = enableInMiniProgramList;
      int j = paramIX5WebViewBase.length;
      int i = 0;
      while (i < j)
      {
        if (paramIX5WebViewBase[i].equals(paramString)) {
          return true;
        }
        i += 1;
      }
      return false;
    }
    switch (PublicSettingManager.getInstance().getTBSGeneralFeatureSwitch(paramString))
    {
    default: 
      return paramBoolean;
    case 1: 
      return true;
    }
    return false;
  }
  
  @Deprecated
  private boolean readFromPSM(String paramString, boolean paramBoolean)
  {
    switch (PublicSettingManager.getInstance().getTBSGeneralFeatureSwitch(paramString))
    {
    default: 
      return paramBoolean;
    case 1: 
      return true;
    }
    return false;
  }
  
  public boolean AppLongPressMenuEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    return readFromPSM(paramIX5WebViewBase, "app_long_press_menu", ENABLE_APP_LONG_PRESS_MENU[this.mHostApp]);
  }
  
  public boolean LongPressMenuImageQueryEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    return readFromPSM(paramIX5WebViewBase, "long_press_menu_image_query", ENABLE_APP_LONG_PRESS_MENU_IMAGE_QUERY[this.mHostApp]);
  }
  
  public boolean activeQBBackHeartBeatEnable()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("active_qbbackheartbeat", ENABLE_ACTIVE_QBBACKHEARTBEAT[this.mHostApp]);
  }
  
  public int activeQBBackHeartBeatFrequency()
  {
    return readFromPSM("activeqbback_frequency");
  }
  
  public void enableMiniQBAllEntryKeys()
  {
    this.mDebugMiniQBFlag = true;
  }
  
  public boolean isBlockLocalHostRequestEnabled()
  {
    checkHostApp();
    return readFromPSM("block_localhost_request", ENABLE_BLOCK_LOCALHOST_REQUEST[this.mHostApp]);
  }
  
  public boolean isBottomBarEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("bottom_bar", ENABLE_BOTTOM_BAR[this.mHostApp]);
  }
  
  public boolean isBubbleAdEnabled()
  {
    checkHostApp();
    return readFromPSM("bubble_ad", ENABLE_BUBBLE_AD[this.mHostApp]);
  }
  
  public boolean isBubbleMiniQbAdEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("bubble_miniqb_ad", ENABLE_BUBBLE_MINIQB_AD[this.mHostApp]);
  }
  
  public boolean isClickImageScanEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM(paramIX5WebViewBase, "click_image_scan", ENABLE_CLICK_IMAGE_SCAN[this.mHostApp]);
  }
  
  public boolean isDebugMiniQB()
  {
    return this.mDebugMiniQBFlag;
  }
  
  public boolean isDownloadInterceptPluginEnabled()
  {
    checkHostApp();
    return readFromPSM("download_intercept_plugin", ENABLE_DOWNLOAD_INTERCEPT_PLUGIN[this.mHostApp]);
  }
  
  public boolean isDownloadInterceptSwitchMatch()
  {
    return PublicSettingManager.getInstance().getTBSGeneralFeatureSwitch("intercept_download_request") >= 0;
  }
  
  public boolean isEnableAutoDownloadTbsCore()
  {
    checkHostApp();
    return readFromPSM("enable_tbs_auto_download_tbs_core", ENABLE_TBS_AUTO_DOWNLOAD_TBS_CORE[this.mHostApp]);
  }
  
  public boolean isEnableInstallAppUseYYB()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("enable_install_app_use_yyb", ENABLE_INSTALL_APP_USE_YYB[this.mHostApp]);
  }
  
  public boolean isEnableMeizuNightMode()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("enable_meizu_nightmode", ENABLE_MEIZU_NIGHT_MODE[this.mHostApp]);
  }
  
  public boolean isEnableReinstallTipsforAd()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("enable_reinstall_tips_for_ad", ENABLE_REINSTALL_TIPS_FOR_AD[this.mHostApp]);
  }
  
  public boolean isFileChooserTBSEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("file_chooser_tbs", ENABLE_FILE_CHOOSER_TBS[this.mHostApp]);
  }
  
  public boolean isFileReaderEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("file_reader", ENABLE_FILE_READER[this.mHostApp]);
  }
  
  public boolean isFileReaderMenuEnabled(int paramInt)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("READER_MENU_");
    localStringBuilder.append(paramInt);
    return readFromPSM(localStringBuilder.toString(), ENABLE_FILE_READER_MENU[this.mHostApp]);
  }
  
  public boolean isFileReaderSupportExtEnabled(String paramString)
  {
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("file_reader_ext_");
    localStringBuilder.append(paramString);
    return readFromPSM(localStringBuilder.toString(), true);
  }
  
  public boolean isFullScreenPlayerEnable()
  {
    checkHostApp();
    return readFromPSM("fullscreen_player", ENABLE_FULLSCREEN_PLAYER[this.mHostApp]);
  }
  
  public boolean isGameEmbeddedFrameworkEnabled()
  {
    checkHostApp();
    return readFromPSM("game_embedded_framework", ENABLE_GAME_EMBEDDED_FRAMEWORK[this.mHostApp]);
  }
  
  public boolean isGameEmbeddedFrameworkLoadWxpayBackground()
  {
    checkHostApp();
    return readFromPSM("game_embedded_framework_load_wxpay_background", ENABLE_GAME_EMBEDDED_FRAMEWORK_LOAD_WXPAY_BACKGROUND[this.mHostApp]);
  }
  
  public boolean isGameFrameworkEnabled()
  {
    checkHostApp();
    return readFromPSM("game_framework", ENABLE_GAME_FRAMEWORK[this.mHostApp]);
  }
  
  public boolean isGamePlayerEnabled()
  {
    checkHostApp();
    return readFromPSM("game_player", ENABLE_GAME_PLAYER[this.mHostApp]);
  }
  
  public boolean isGameSandboxEnabled()
  {
    checkHostApp();
    return readFromPSM("game_sandbox", ENABLE_GAME_SANDBOX[this.mHostApp]);
  }
  
  public boolean isGdtAdvertisementEnabled()
  {
    checkHostApp();
    return readFromPSM("gdt_advertisement", ENABLE_GDT_ADVERTISEMENT[this.mHostApp]);
  }
  
  public boolean isHeadsUpRiskWebsite()
  {
    checkHostApp();
    return readFromPSM("headsup_risk_website", ENABLE_HEADSUP_RISK_WEBSITE[this.mHostApp]);
  }
  
  public boolean isHeadsUpTaobaoLinkEnabled()
  {
    checkHostApp();
    return readFromPSM("headsup_taobao_link", ENABLE_HEADSUP_TAOBAO_LINK[this.mHostApp]);
  }
  
  public boolean isHeadsUpTranscodingPageEnabled()
  {
    checkHostApp();
    return readFromPSM("headsup_transcoding_page", ENABLE_HEADSUP_TRANSCODING_PAGE[this.mHostApp]);
  }
  
  public boolean isIliveEnable()
  {
    checkHostApp();
    return readFromPSM("ilive_sdk", ENABLE_ILIVE_SDK[this.mHostApp]);
  }
  
  public boolean isInterceptDownloadRequestEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("intercept_download_request", ENABLE_INTERCEPT_DOWNLOAD_REQUEST[this.mHostApp]);
  }
  
  public boolean isJSDownloadOptimizationEnabled()
  {
    return readFromPSM("enable_js_download_optimization", ENABLE_JS_DOWNLOAD_OPTIMIZATION[this.mHostApp]);
  }
  
  public boolean isJsApiInstallAppEnable()
  {
    checkHostApp();
    return readFromPSM("jsapi_enable_install_app", ENABLE_JSAPI_INSTALL_APP[this.mHostApp]);
  }
  
  public boolean isJsApiStartDownloadEnable()
  {
    checkHostApp();
    return readFromPSM("jsapi_enable_start_download", ENABLE_JSAPI_START_DOWNLOAD[this.mHostApp]);
  }
  
  public boolean isLongClickOptMM(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM(paramIX5WebViewBase, "long_press_timecost_opt", ENABLE_LONG_PRESS_TIMECOST_OPT[this.mHostApp]);
  }
  
  public boolean isLongPressImageScanEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM(paramIX5WebViewBase, "long_press_menu_image_scan", ENABLE_LONG_PRESS_MENU_IMAGE_SCAN[this.mHostApp]);
  }
  
  public boolean isLongPressMenuEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM(paramIX5WebViewBase, "long_press_menu", ENABLE_LONG_PRESS_MENU[this.mHostApp]);
  }
  
  public boolean isLongPressMenuExplorerEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    return readFromPSM(paramIX5WebViewBase, "long_press_menu_explorer", ENABLE_LONG_PRESS_MENU_EXPLORER[this.mHostApp]);
  }
  
  public boolean isLongPressMenuNightModeEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM(paramIX5WebViewBase, "long_press_menu_night_mode", ENABLE_LONG_PRESS_MENU_NIGHT_MODE[this.mHostApp]);
  }
  
  public boolean isLongPressMenuOpenInBrowserEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM(paramIX5WebViewBase, "long_press_menu_open_in_browser", ENABLE_LONG_PRESS_MENU_OPEN_IN_BROWSER[this.mHostApp]);
  }
  
  public boolean isLongPressMenuRefreshEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    return readFromPSM(paramIX5WebViewBase, "long_press_menu_refresh", ENABLE_LONG_PRESS_MENU_REFRESH[this.mHostApp]);
  }
  
  public boolean isLongPressMenuScreenShotEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("long_press_screen_shot", ENABLE_LONG_PRESS_MENU_SCREEN_SHOT[this.mHostApp]);
  }
  
  public boolean isLongPressMenuSelectCopyEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM(paramIX5WebViewBase, "long_press_menu_select_copy", ENABLE_LONG_PRESS_MENU_SELECT_COPY[this.mHostApp]);
  }
  
  public boolean isLongPressQuickSecondMenu_QQ_ThirdApp(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM(paramIX5WebViewBase, "long_press_quick_second_menu_qq_thirdapp", ENABLE_LONG_PRESS_QUICK_SECOND_MENU_QQ_THIRDAPP[this.mHostApp]);
  }
  
  public boolean isLongPressQuickSelectCopyEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM(paramIX5WebViewBase, "long_press_quick_select_copy", ENABLE_LONG_PRESS_QUICK_SELECT_COPY[this.mHostApp]);
  }
  
  public boolean isMidPageAdvertisementEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("mid_page_advertisement", ENABLE_MID_PAGE_ADVERTISEMENT[this.mHostApp]);
  }
  
  public boolean isMidPageQbHeadsUpEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("midpage_headsup", ENABLE_MID_PAGE_HEADSUP[this.mHostApp]);
  }
  
  public boolean isMidPageQbNightFullScreenReadOnLongPressEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("midpage_nightfullscreenread_onlongpress", ENABLE_MID_PAGE_NIGHTFULLSCREENREAD_ONLONGPRESS[this.mHostApp]);
  }
  
  public boolean isMidPageQbOpenBrowserOnLongPressEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("mid_page_qb_openbrowser_onlongpress", ENABLE_MID_PAGE_QB_OPENBROWSER_ONLONGPRESS[this.mHostApp]);
  }
  
  public boolean isMidPageQbSearchOnLongPressEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("mid_page_qb_search_onlongpress", ENABLE_MID_PAGE_QB_SEARCH_ONLONGPRESS[this.mHostApp]);
  }
  
  public boolean isMidPageQbSilentDownloadEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("mid_page_qb_silent_download", ENABLE_MID_PAGE_QB_SILENT_DOWNLOAD[this.mHostApp]);
  }
  
  public boolean isMidPageQbTranslateOnLongPressEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("midpage_translate_onlongpress", ENABLE_MID_PAGE_TRANSLATE_ONLONGPRESS[this.mHostApp]);
  }
  
  public boolean isMidPageQbWebviewBottomMontageEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("midpage_webview_bottommontage", ENABLE_MID_PAGE_WEBVIEW_BOTTOMMONTAGE[this.mHostApp]);
  }
  
  public boolean isMidPageQbWebviewBubbleEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("midpage_webview_bubble", ENABLE_MID_PAGE_WEBVIEW_BUBBLE[this.mHostApp]);
  }
  
  public boolean isMiniqbUpdateEnable()
  {
    checkHostApp();
    return readFromPSM("miniqb_upgrade", ENABLE_MINIQB_UPGRADE[this.mHostApp]);
  }
  
  public boolean isMmHelperEnable()
  {
    checkHostApp();
    return readFromPSM("mm_helper", ENABLE_MM_HELPER[this.mHostApp]);
  }
  
  public boolean isNewLongPressDownloadInterceptOpenQbMethodEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("new_long_press_download_intercept_open_qb_method", ENABLE_NEW_LONG_PRESS_DOWNLOAD_INTERCEPT_OPEN_QB_METHOD[this.mHostApp]);
  }
  
  public boolean isNewQbDownloadUrlStyleEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("new_qb_download_url_style", ENABLE_NEW_QB_DOWNLOAD_URL_STYLE[this.mHostApp]);
  }
  
  public boolean isNewQbDownloadUrlStyleHttpsEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("new_qb_download_url_style_https", ENABLE_NEW_QB_DOWNLOAD_URL_STYLE_HTTPS[this.mHostApp]);
  }
  
  public boolean isNightModeEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("invoke_qb_night_mode_and_fullscreen", ENABLE_NIGHT_MODE[this.mHostApp]);
  }
  
  public boolean isOpenUrlOnLongPressEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM(paramIX5WebViewBase, "open_url_on_long_press", ENABLE_OPEN_URL_ON_LONG_PRESS[this.mHostApp]);
  }
  
  public boolean isPlatformTypeReportEnabled()
  {
    checkHostApp();
    return readFromPSM("platform_report", ENABLE_PLATFORM_REPORT[this.mHostApp]);
  }
  
  public boolean isQBPureIncrease()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("qb_pureincrease", ENABLE_QB_PUREINCREASE[this.mHostApp]);
  }
  
  public boolean isQBiconInQQShineEnabled()
  {
    checkHostApp();
    return readFromPSM("qbicon_qq_shine", ENABLE_QBICON_QQ_SHINE[this.mHostApp]);
  }
  
  public boolean isQQErrorPageLittleGameEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("qq_error_page_little_game", ENABLE_QQ_ERROR_PAGE_GAME[this.mHostApp]);
  }
  
  public boolean isSafeDownloadInterceptEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("safe_download_intercept", ENABLE_SAFE_DOWNLOAD_INTERCEPT[this.mHostApp]);
  }
  
  public boolean isScrollAdWebViewEnable()
  {
    checkHostApp();
    return readFromPSM("scroll_ad_webview", ENABLE_SCROLL_AD_WEBVIEW[this.mHostApp]);
  }
  
  public boolean isSearchOnLongPressEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM(paramIX5WebViewBase, "search_on_long_press", ENABLE_SEARCH_ON_LONG_PRESS[this.mHostApp]);
  }
  
  public boolean isShouldInterceptMttbrowser()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("should_intercept_mttbrowser", ENABLE_SHOULD_INTERCEPT_MTTBROWSER[this.mHostApp]);
  }
  
  public boolean isShouldInterceptMttbrowserUseMiniQb()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("should_intercept_mttbrowser_use_miniqb", ENABLE_SHOULD_INTERCEPT_MTTBROWSER_USE_MINIQB[this.mHostApp]);
  }
  
  public boolean isSpliceAdEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("splice_ad", ENABLE_SPLICE_AD[this.mHostApp]);
  }
  
  public boolean isSpliceMiniQbAdEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("splice_miniqb_ad", ENABLE_SPLICE_MINIQB_AD[this.mHostApp]);
  }
  
  public boolean isTBSIMEASREnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("tbs_ime_asr", ENABLE_TBS_IME_ASR[this.mHostApp]);
  }
  
  public boolean isTbsAREnable()
  {
    checkHostApp();
    return readFromPSM("tbs_ar", ENABLE_TBS_AR[this.mHostApp]);
  }
  
  public boolean isTbsAdLandPageInDialogEnable()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("enable_tbs_ad_land_page_in_dialog", ENABLE_TBS_AD_LAND_PAGE_IN_DIALOG[this.mHostApp]);
  }
  
  public boolean isTbsAdPluginEnable()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("enable_tbs_ad_plugin", ENABLE_TBS_AD_PLUGIN[this.mHostApp]);
  }
  
  public boolean isTbsClipBoardEnabled()
  {
    checkHostApp();
    return readFromPSM("tbs_clipboard", ENABLE_TBS_CLIPBOARD[this.mHostApp]);
  }
  
  public boolean isTbsHistoryEnabled()
  {
    checkHostApp();
    return readFromPSM("tbs_history", ENABLE_TBS_HISTORY[this.mHostApp]);
  }
  
  public boolean isTbsJsapiEnabled()
  {
    checkHostApp();
    return readFromPSM("tbs_jsapi", ENABLE_TBS_JSAPI[this.mHostApp]);
  }
  
  public boolean isTbsLogManagerEnable()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return ENABLE_TBS_LOGMANAGER[this.mHostApp];
  }
  
  public boolean isTbsQrCodeShareEnabled()
  {
    checkHostApp();
    return readFromPSM("qr_code_share_enable", TBS_QR_CODE_SHARE_ENABLE[this.mHostApp]);
  }
  
  public boolean isTbsResourceAdaptEnabled()
  {
    checkHostApp();
    return readFromPSM("enable_tbs_resource_adapt_check", ENABLE_TBS_RESOURCE_ADAPT[this.mHostApp]);
  }
  
  public boolean isTranslateOnLongPressEnabled(IX5WebViewBase paramIX5WebViewBase)
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM(paramIX5WebViewBase, "translate_on_long_press", ENABLE_TRANSLATE_ON_LONG_PRESS[this.mHostApp]);
  }
  
  public boolean isWXWholePageTranslateEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("wx_whole_page_translate", ENABLE_WX_WHOLE_PAGE_TRANSLATE[this.mHostApp]);
  }
  
  public boolean isWeChatCrashMonitorEnable()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("enable_wechat_crash_monitor", ENABLE_WECHAT_CRASH_MONITOR[this.mHostApp]);
  }
  
  public boolean isWebAcceleratorRefetchResourceEnable()
  {
    checkHostApp();
    return readFromPSM("web_accelerator_prefetch_resource", ENABLE_WEB_ACCELERATOR_PREFETCH_RESOURCE[this.mHostApp]);
  }
  
  public boolean isWebCoreCheckEnabled()
  {
    checkHostApp();
    return readFromPSM("enable_webcore_md5_check", ENABLE_WEBCORE_LOAD_CHECK[this.mHostApp]);
  }
  
  public boolean isX5CameraEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("x5_camera", ENABLE_X5_CAMERA[this.mHostApp]);
  }
  
  public boolean requestActiveQBEnable()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("active_qb", ENABLE_ACTIVE_QB[this.mHostApp]);
  }
  
  public boolean requestHeadsUp()
  {
    return true;
  }
  
  public boolean reuseQBApkEnabled()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("reuse_qbapk", ENABLE_REUSE_QBAPK[this.mHostApp]);
  }
  
  public boolean schemeInterceptActiveQBEnable()
  {
    checkHostApp();
    if (this.mDebugMiniQBFlag) {
      return true;
    }
    return readFromPSM("scheme_intercept_active_qb", ENABLE_SCHEME_INTERCEPT_ACTIVE_QB[this.mHostApp]);
  }
  
  public boolean shouldDetectDownloadPkgName()
  {
    checkHostApp();
    return readFromPSM("detect_download_pkgname", ENABLE_DETECT_DOWNLOAD_PKGNAME[this.mHostApp]);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\config\Configuration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */