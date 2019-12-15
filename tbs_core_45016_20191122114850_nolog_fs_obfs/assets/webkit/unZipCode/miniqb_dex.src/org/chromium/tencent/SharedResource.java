package org.chromium.tencent;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.chromium.base.annotations.UsedByReflection;

public class SharedResource
{
  public static String ADINFOMANAGER_INIT_BEGIN;
  public static String ADINFOMANAGER_INIT_END;
  public static final String BROWSER_CONTENT_START_BEGIN = "browser_content_start_begin";
  public static final String BROWSER_CONTENT_START_END = "browser_content_start_end";
  public static final String CHROMIUM_INIT_FOR_REAL_BEGIN = "chromium_init_for_real_begin";
  public static final String CHROMIUM_INIT_FOR_REAL_END = "chromium_init_for_real_end";
  public static String ENSUREPROVIDERCREATED_BEGIN;
  public static String ENSUREPROVIDERCREATED_END;
  public static final String INIT_APPLICATION_CONTEXT_BEGIN = "init_application_context_begin";
  public static final String INIT_APPLICATION_CONTEXT_END = "init_application_context_end";
  public static final String INIT_DEFAULT_COMMANDLINE_BEGIN = "init_default_commandline_begin";
  public static final String INIT_DEFAULT_COMMANDLINE_END = "init_default_commandline_end";
  private static final String LOGTAG = "SharedResource";
  public static final String NEW_AW_CONTENTS_BEGIN = "new_aw_contents_begin";
  public static final String NEW_AW_CONTENTS_END = "new_aw_contents_end";
  public static final String NEW_WEBVIEWCHROMIUMFACTORYPROVIDER_BEGIN = "new_webviewchromiumfactoryprovider_begin";
  public static final String NEW_WEBVIEWCHROMIUMFACTORYPROVIDER_END = "new_webviewchromiumfactoryprovider_end";
  public static final boolean PERFORMANCE_LOG_VERBOSE = false;
  public static String PROVIDER_INIT_BEGIN;
  public static String PROVIDER_INIT_END;
  public static final String STARTYOURENGINES_BEGIN = "startyourengines_begin";
  public static final String STARTYOURENGINES_END = "startyourengines_end";
  public static final String TYPE_ARRAY = "array";
  public static final String TYPE_ATTR = "attr";
  public static final String TYPE_COLOR = "color";
  public static final String TYPE_DIMEN = "dimen";
  public static final String TYPE_DRAWABLE = "drawable";
  public static final String TYPE_ID = "id";
  public static final String TYPE_LAYOUT = "layout";
  public static final String TYPE_PLURALS = "plurals";
  public static final String TYPE_STRING = "string";
  public static final String TYPE_STYLE = "style";
  public static final String WEBVIEWCHROMIUM_EXTENSION_BEGIN = "webviewchromium_extension_begin";
  public static final String WEBVIEWCHROMIUM_EXTENSION_END = "webviewchromium_extension_end";
  public static String WEBVIEW_INIT_BEGIN;
  public static String WEBVIEW_INIT_END;
  public static String X5WEBVIEW_CLINIT_END;
  public static String X5_WEBVIEW_INIT_BEGIN;
  public static String X5_WEBVIEW_INIT_END;
  public static final String accept = "x5_accept";
  public static final String audio_capture_permission_prompt = "x5_audio_capture_permission_prompt";
  public static final String btnSetDateTime = "x5_btnSetDateTime";
  public static final String cancel = "x5_cancel";
  public static final String create_video_player_failed = "x5_create_video_player_failed";
  public static final String datePicker = "x5_datePicker";
  public static final String double_tap_toast = "x5_double_tap_toast";
  public static final String error_page_x5_error_already_uploaded = "x5_error_already_uploaded";
  public static final String error_page_x5_error_apn_name = "x5_error_apn_name";
  public static final String error_page_x5_error_network_type = "x5_error_network_type";
  public static final String error_page_x5_error_proxy_disabled = "x5_error_proxy_disabled";
  public static final String error_page_x5_error_proxy_enabled = "x5_error_proxy_enabled";
  public static final String error_page_x5_error_upload_failed = "x5_error_upload_failed";
  public static final String error_page_x5_error_upload_success = "x5_error_upload_success";
  public static final String geo_provider_not_found = "x5_geo_provider_not_found";
  public static final String httpError = "x5_httpError";
  public static final String httpErrorAuth = "x5_httpErrorAuth";
  public static final String httpErrorBadUrl = "x5_httpErrorBadUrl";
  public static final String httpErrorConnect = "x5_httpErrorConnect";
  public static final String httpErrorFailedSslHandshake = "x5_httpErrorFailedSslHandshake";
  public static final String httpErrorFile = "x5_httpErrorFile";
  public static final String httpErrorFileNotFound = "x5_httpErrorFileNotFound";
  public static final String httpErrorIO = "x5_httpErrorIO";
  public static final String httpErrorLookup = "x5_httpErrorLookup";
  public static final String httpErrorOk = "x5_httpErrorOk";
  public static final String httpErrorProxyAuth = "x5_httpErrorProxyAuth";
  public static final String httpErrorRedirectLoop = "x5_httpErrorRedirectLoop";
  public static final String httpErrorTimeout = "x5_httpErrorTimeout";
  public static final String httpErrorTooManyRequests = "x5_httpErrorTooManyRequests";
  public static final String httpErrorUnsupportedAuthScheme = "x5_httpErrorUnsupportedAuthScheme";
  public static final String httpErrorUnsupportedScheme = "x5_httpErrorUnsupportedScheme";
  public static final String ic_media_play = "x5_ic_media_play";
  public static final String js_dialog_before_unload = "x5_js_dialog_before_unload";
  public static final String js_dialog_title = "x5_js_dialog_title";
  public static final String js_dialog_title_default = "x5_js_dialog_title_default";
  public static final String js_new_dialog_alert_button_text = "x5_js_new_dialog_alert_button_text";
  public static final String js_new_dialog_main_title = "x5_js_new_dialog_main_title";
  public static final String js_new_dialog_second_title_suffix = "x5_js_new_dialog_second_title_suffix";
  public static final String last_month = "x5_last_month";
  public static final String last_num_days = "x5_last_num_days";
  public static final String listbox_theme = "x5_MttFuncWindowTheme";
  protected static Context mContext;
  public static final String mControl_Edittext_Holder_Height = "x5_control_edittext_holder_height";
  public static final String mControl_Edittext_Holder_Width = "x5_control_edittext_holder_width";
  public static final String mControl_Edittext_popup_Vertically_Offset = "x5_control_edittext_popup_vertically_offset";
  protected static final LongSparseArray<WeakReference<Drawable.ConstantState>> mDrawableCache;
  protected static Map<String, Integer> mIdentifiers;
  private static String mInitResInfo = "";
  protected static String mPackageName;
  private static Map<String, String> mPerformanceRecord = new HashMap();
  protected static String mResPath;
  protected static Resources mResource;
  public static final String media_access_permission_prompt = "x5_media_access_permission_prompt";
  public static final String message = "x5_message";
  public static final String missingPluginText = "x5_missing_plugin_text";
  public static final String multipleFileUploadText = "x5_multiple_file_upload_text";
  public static final String network_error = "x5_net_error";
  public static final String next_page_desc = "x5_next_page_desc";
  public static final String notifi_icon = "x5_notifi_icon";
  public static final String notifi_text = "x5_notifi_text";
  public static final String notifi_title = "x5_notifi_title";
  public static final String notification_permission_prompt = "x5_notification_permission_prompt";
  public static final String ok = "x5_ok";
  public static final String older = "x5_older";
  public static final String open_permission_deny = "x5_open_permission_deny";
  public static final String play_video_float_mode = "x5_play_video_float_mode";
  public static final String play_video_lite_mode = "x5_play_video_lite_mode";
  public static final String play_video_with_sys_app = "x5_play_video_with_sys_app";
  public static final String prohibit = "x5_prohibit";
  public static final String refuse = "x5_refuse";
  private static TbsResourcesFetcher resFetcher = null;
  public static final String reset = "x5_reset";
  public static final String save_password_label = "x5_save_password_label";
  public static final String save_password_message = "x5_save_password_message";
  public static final String save_password_notnow = "x5_save_password_notnow";
  public static final String save_password_remember = "x5_save_password_remember";
  public static final String save_replace_password_message = "x5_save_replace_password_message";
  public static final String selectMenuListText = "x5_select_menu_list_text";
  public static final String special_hosts = "x5_special_hosts";
  public static final String submit = "x5_submit";
  public static final String text_select_handle_left = "x5_text_select_holder_left";
  public static final String text_select_handle_middle = "x5_text_select_holder_middle";
  public static final String text_select_handle_night_left = "x5_text_select_holder_night_left";
  public static final String text_select_handle_night_middle = "x5_text_select_holder_night_middle";
  public static final String text_select_handle_night_right = "x5_text_select_holder_night_right";
  public static final String text_select_handle_right = "x5_text_select_holder_right";
  public static final String timePicker = "x5_timePicker";
  public static final String unknown = "x5_unknown";
  public static final String upload_file = "x5_upload_file";
  public static final String upload_file_too_large = "x5_upload_file_too_large";
  public static final String upload_nofile = "x5_upload_nofile";
  public static final String upload_nofiles = "x5_upload_nofiles";
  public static final String value = "x5_value";
  public static final String video_capture_permission_prompt = "x5_video_capture_permission_prompt";
  public static final String web_user_agent = "x5_web_user_agent";
  public static final String x5_DropdownPopupWindow = "x5_DropdownPopupWindow";
  public static final String x5_DropdownPopupWindow_night = "x5_DropdownPopupWindow_night";
  public static final String x5_activity_camera = "x5_activity_camera";
  public static final String x5_ar_forbiden_retry = "x5_ar_forbiden_retry";
  public static final String x5_ar_set_camera = "x5_ar_set_camera";
  public static final String x5_ar_set_system = "x5_ar_set_system";
  public static final String x5_audio_data_upload_failed = "x5_audio_data_upload_failed";
  public static final String x5_audio_data_upload_success = "x5_audio_data_upload_success";
  public static final String x5_autofill_popup_content_description = "x5_autofill_popup_content_description";
  public static final String x5_camera_preview = "x5_camera_preview";
  public static final String x5_close_ad_no_interest = "x5_close_ad_no_interest";
  public static final String x5_close_ad_x = "x5_close_ad_x";
  public static final String x5_dialogTheme_fullscreen = "x5_dialogTheme_fullscreen";
  public static final String x5_dialog_black_text_color = "x5_dialog_black_text_color";
  public static final String x5_dialog_black_text_night_color = "x5_dialog_black_text_night_color";
  public static final String x5_dialog_blue_text_color = "x5_dialog_blue_text_color";
  public static final String x5_dialog_blue_text_night_color = "x5_dialog_blue_text_night_color";
  public static final String x5_dialog_bottom_button_height = "x5_dialog_bottom_button_height";
  public static final String x5_dialog_gray_text_color = "x5_dialog_gray_text_color";
  public static final String x5_dialog_gray_text_night_color = "x5_dialog_gray_text_night_color";
  public static final String x5_dialog_line_color = "x5_dialog_line_color";
  public static final String x5_dialog_line_night_color = "x5_dialog_line_night_color";
  public static final String x5_dialog_prompt_edittext_height = "x5_dialog_prompt_edittext_height";
  public static final String x5_dialog_prompt_edittext_magin_left = "x5_dialog_prompt_edittext_magin_left";
  public static final String x5_dialog_prompt_edittext_magin_top = "x5_dialog_prompt_edittext_magin_top";
  public static final String x5_dialog_red_text_color = "x5_dialog_red_text_color";
  public static final String x5_dialog_red_text_night_color = "x5_dialog_red_text_night_color";
  public static final String x5_dialog_ssl_cert_magin_middle = "x5_dialog_ssl_cert_magin_middle";
  public static final String x5_dialog_ssl_magin_middle = "x5_dialog_ssl_magin_middle";
  public static final String x5_dialog_subtitle_magin_bottom = "x5_dialog_subtitle_magin_bottom";
  public static final String x5_dialog_subtitle_magin_top = "x5_dialog_subtitle_magin_top";
  public static final String x5_dialog_title_linespace = "x5_dialog_title_linespace";
  public static final String x5_dialog_title_magin_top = "x5_dialog_title_magin_top";
  public static final String x5_dialog_width = "x5_dialog_width";
  public static final String x5_dropdown_item = "x5_dropdown_item";
  public static final String x5_dropdown_item_divider_height = "x5_dropdown_item_divider_height";
  public static final String x5_dropdown_item_height = "x5_dropdown_item_height";
  public static final String x5_dropdown_item_label_font_size = "x5_dropdown_item_label_font_size";
  public static final String x5_dropdown_item_label_margin = "x5_dropdown_item_label_margin";
  public static final String x5_dropdown_item_larger_sublabel_font_size = "x5_dropdown_item_larger_sublabel_font_size";
  public static final String x5_dropdown_label = "x5_dropdown_label";
  public static final String x5_dropdown_label_wrapper = "x5_dropdown_label_wrapper";
  public static final String x5_dropdown_sublabel = "x5_dropdown_sublabel";
  public static final String x5_fast_scroller = "x5_fast_scroller";
  public static final String x5_fast_scroller_night = "x5_fast_scroller_night";
  public static final String x5_file_delete_file_error = "x5_file_delete_file_error";
  public static final String x5_force_pinch_scale = "x5_force_pinch_scale";
  public static final String x5_id_area_sv = "x5_id_area_sv";
  public static final String x5_id_camera_back = "x5_id_camera_back";
  public static final String x5_id_capture_btn = "x5_id_capture_btn";
  public static final String x5_id_preview_camera_iv = "x5_id_preview_camera_iv";
  public static final String x5_id_recapture_btn = "x5_id_recapture_btn";
  public static final String x5_id_switch_camera_btn = "x5_id_switch_camera_btn";
  public static final String x5_id_upload_btn = "x5_id_upload_btn";
  public static final String x5_image_query = "x5_image_query";
  public static final String x5_listbox_checked_day = "x5_listbox_checked_day";
  public static final String x5_listbox_group_margin_top = "x5_listbox_group_margin_top";
  public static final String x5_listbox_item_margin_group_top = "x5_listbox_item_margin_group_top";
  public static final String x5_listbox_item_margin_item_top = "x5_listbox_item_margin_item_top";
  public static final String x5_listbox_single_grid = "x5_listbox_single_grid";
  public static final String x5_listbox_unchecked_day = "x5_listbox_unchecked_day";
  public static final String x5_log_no_file_exist = "x5_log_no_file_exist";
  public static final String x5_log_upload_failed = "x5_log_upload_failed";
  public static final String x5_log_upload_success = "x5_log_upload_success";
  public static final String x5_media_player_error_button = "x5_media_player_error_button";
  public static final String x5_media_player_error_text_invalid_progressive_playback = "x5_media_player_error_text_invalid_progressive_playback";
  public static final String x5_media_player_error_text_unknown = "x5_media_player_error_text_unknown";
  public static final String x5_media_player_error_title = "x5_media_player_error_title";
  public static final String x5_menu_button_height = "x5_menu_button_height";
  public static final String x5_menu_button_margin_border = "x5_menu_button_margin_border";
  public static final String x5_menu_button_margin_left_right = "x5_menu_button_margin_left_right";
  public static final String x5_menu_button_width = "x5_menu_button_width";
  public static final String x5_menu_copy_button_width = "x5_menu_copy_button_width";
  public static final String x5_menu_text_color = "x5_menu_text_color";
  public static final String x5_menu_text_color_night = "x5_menu_text_color_night";
  public static final String x5_menu_toast_text_color = "x5_menu_toast_text_color";
  public static final String x5_menu_toast_text_night_color = "x5_menu_toast_text_night_color";
  public static final String x5_mttlog_check_url = "x5_mttlog_check_url";
  public static final String x5_mttlog_finish = "x5_mttlog_finish";
  public static final String x5_mttlog_shutdown = "x5_mttlog_shutdown";
  public static final String x5_mttlog_shutdown_by_home = "x5_mttlog_shutdown_by_home";
  public static final String x5_mttlog_start_record = "x5_mttlog_start_record";
  public static final String x5_mttlog_start_record_render = "x5_mttlog_start_record_render";
  public static final String x5_mttlog_stop_record = "x5_mttlog_stop_record";
  public static final String x5_mttlog_stop_record_render = "x5_mttlog_stop_record_render";
  public static final String x5_mttlog_upload_done = "x5_mttlog_upload_done";
  public static final String x5_mttlog_url_outofdate = "x5_mttlog_url_outofdate";
  public static final String x5_mttlog_will = "x5_mttlog_will";
  public static final String x5_qq_snapshot_check = "x5_qq_snapshot_check";
  public static final String x5_qq_snapshot_content_preview = "x5_qq_snapshot_content_preview";
  public static final String x5_qq_snapshot_deleted = "x5_qq_snapshot_deleted";
  public static final String x5_qq_snapshot_saved = "x5_qq_snapshot_saved";
  public static final String x5_qq_snapshot_stop = "x5_qq_snapshot_stop";
  public static final String x5_qq_snapshot_to_quick = "x5_qq_snapshot_to_quick";
  public static final String x5_search_video_close_button = "x5_search_video_close_button";
  public static final String x5_search_video_play_button = "x5_search_video_play_button";
  public static final String x5_select_list_multi_disable = "x5_select_list_multi_disable";
  public static final String x5_select_list_multi_normal = "x5_select_list_multi_normal";
  public static final String x5_select_list_multi_pressed = "x5_select_list_multi_pressed";
  public static final String x5_select_list_multi_selected = "x5_select_list_multi_selected";
  public static final String x5_select_list_scrollbar = "x5_select_list_scrollbar";
  public static final String x5_select_list_single_disable = "x5_select_list_single_disable";
  public static final String x5_select_list_single_normal = "x5_select_list_single_normal";
  public static final String x5_select_list_single_pressed = "x5_select_list_single_pressed";
  public static final String x5_select_list_single_selected = "x5_select_list_single_selected";
  public static final String x5_ssl_cert_info_title = "x5_ssl_cert_info_title";
  public static final String x5_ssl_check_cert_info = "x5_ssl_check_cert_info";
  public static final String x5_ssl_continue_access = "x5_ssl_continue_access";
  public static final String x5_ssl_correct_date = "x5_ssl_correct_date";
  public static final String x5_ssl_error_info_expired = "x5_ssl_error_info_expired";
  public static final String x5_ssl_error_info_mismatch = "x5_ssl_error_info_mismatch";
  public static final String x5_ssl_error_info_not_yet_valid = "x5_ssl_error_info_not_yet_valid";
  public static final String x5_ssl_error_info_phone_date_error = "x5_ssl_error_info_phone_date_error";
  public static final String x5_ssl_error_info_unknown_error = "x5_ssl_error_info_unknown_error";
  public static final String x5_ssl_error_info_untrusted = "x5_ssl_error_info_untrusted";
  public static final String x5_ssl_error_info_untrusted_chain_error = "x5_ssl_error_info_untrusted_chain_error";
  public static final String x5_ssl_finger_print = "x5_ssl_finger_print";
  public static final String x5_ssl_finger_print_256 = "x5_ssl_finger_print_256";
  public static final String x5_ssl_issued_by = "x5_ssl_issued_by";
  public static final String x5_ssl_issued_to = "x5_ssl_issued_to";
  public static final String x5_ssl_url = "x5_ssl_url";
  public static final String x5_ssl_validity_period = "x5_ssl_validity_period";
  public static final String x5_ssl_validity_period_to = "x5_ssl_validity_period_to";
  public static final String x5_start_record_audio_data = "x5_start_record_audio_data";
  public static final String x5_tbs_dialog_background = "x5_tbs_dialog_backgroud";
  public static final String x5_tbs_dialog_edittext_background = "x5_tbs_dialog_edittext_background";
  public static final String x5_tbs_dialog_press_background = "x5_tbs_dialog_press_background";
  public static final String x5_tbs_dialog_press_night_background = "x5_tbs_dialog_press_night_background";
  public static final String x5_tbs_menu_background_day = "x5_tbs_menu_background_day";
  public static final String x5_tbs_menu_background_night = "x5_tbs_menu_background_night";
  public static final String x5_tbs_menu_bg_arrow_down_day = "x5_tbs_menu_bg_arrow_down_day";
  public static final String x5_tbs_menu_bg_arrow_down_night = "x5_tbs_menu_bg_arrow_down_night";
  public static final String x5_tbs_menu_bg_arrow_up_day = "x5_tbs_menu_bg_arrow_up_day";
  public static final String x5_tbs_menu_bg_arrow_up_night = "x5_tbs_menu_bg_arrow_up_night";
  public static final String x5_tbs_menu_press_background = "x5_tbs_menu_press_background";
  public static final String x5_tbs_menu_press_night_background = "x5_tbs_menu_press_night_background";
  public static final String x5_tbs_menu_toast_background = "x5_tbs_menu_toast_background";
  public static final String x5_tencentspread = "x5_tencentspread";
  public static final String x5_theme_page_inputbox_bkg_normal = "x5_theme_page_inputbox_bkg_normal";
  public static final String x5_timinglog_delete_file_error = "x5_timinglog_delete_file_error";
  public static final String x5_timinglog_no_log = "x5_timinglog_no_log";
  public static final String x5_timinglog_shutdown_switch = "x5_timinglog_shutdown_switch";
  public static final String x5_timinglog_start_upload = "x5_timinglog_start_upload";
  public static final String x5_timinglog_upload_failed = "x5_timinglog_upload_failed";
  public static final String x5_timinglog_upload_success = "x5_timinglog_upload_success";
  public static final String x5_toast_margin_left_right = "x5_toast_margin_left_right";
  public static final String x5_toast_margin_top_bottom = "x5_toast_margin_top_bottom";
  public static final String x5_trace_file_not_exist = "x5_trace_file_not_exist";
  public static final String x5_trace_saved = "x5_trace_saved";
  public static final String x5_trafficTipsAPKintercept = "x5_trafficTipsAPKintercept";
  public static final String x5_trafficTipsbyteThreshold = "x5_trafficTipsbyteThreshold";
  public static final String x5_video_back = "x5_video_back";
  public static final String x5_video_freewifi_height = "x5_video_freewifi_height";
  public static final String x5_video_freewifi_paddingleft = "x5_video_freewifi_paddingleft";
  public static final String x5_video_freewifi_paddingright = "x5_video_freewifi_paddingright";
  public static final String x5_video_freewifi_width = "x5_video_freewifi_width";
  
  static
  {
    X5WEBVIEW_CLINIT_END = "x5webview_clinit_end";
    WEBVIEW_INIT_BEGIN = "webview_init_begin";
    WEBVIEW_INIT_END = "webview_init_end";
    X5_WEBVIEW_INIT_BEGIN = "x5_webview_init_begin";
    X5_WEBVIEW_INIT_END = "x5_webview_init_end";
    ENSUREPROVIDERCREATED_BEGIN = "ensureprovidercreated_begin";
    ENSUREPROVIDERCREATED_END = "ensureprovidercreated_end";
    PROVIDER_INIT_BEGIN = "provider_init_begin";
    PROVIDER_INIT_END = "provider_init_end";
    ADINFOMANAGER_INIT_BEGIN = "adinfomanager_init_begin";
    ADINFOMANAGER_INIT_END = "adinfomanager_init_end";
    mIdentifiers = new HashMap();
    mDrawableCache = new LongSparseArray();
    mResPath = "";
  }
  
  public static Bitmap decodeResource(int paramInt, BitmapFactory.Options paramOptions)
  {
    try
    {
      paramOptions = BitmapFactory.decodeResource(mResource, paramInt, paramOptions);
      return paramOptions;
    }
    catch (Throwable paramOptions)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("decodeResource exception: ");
      ((StringBuilder)localObject).append(paramOptions);
      Log.e("SharedResource", ((StringBuilder)localObject).toString());
      localObject = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(paramOptions);
      ((SmttServiceClientProxy)localObject).reportTbsError(3004, localStringBuilder.toString());
    }
    return new BitmapDrawable().getBitmap();
  }
  
  private static Drawable drawableFromBitmap(Bitmap paramBitmap, byte[] paramArrayOfByte, Rect paramRect, String paramString)
  {
    if (paramArrayOfByte != null) {
      return new NinePatchDrawable(mResource, paramBitmap, paramArrayOfByte, paramRect, paramString);
    }
    return new BitmapDrawable(mResource, paramBitmap);
  }
  
  public static ArrayList<String> getAssetPath(AssetManager paramAssetManager)
  {
    localArrayList = new ArrayList();
    try
    {
      if (Build.VERSION.SDK_INT >= 28) {
        return localArrayList;
      }
      Object localObject = paramAssetManager.getClass().getDeclaredMethod("getStringBlockCount", new Class[0]);
      ((Method)localObject).setAccessible(true);
      int k = ((Integer)((Method)localObject).invoke(paramAssetManager, new Object[0])).intValue();
      int i = 0;
      while (i < k)
      {
        localObject = paramAssetManager.getClass().getMethod("getCookieName", new Class[] { Integer.TYPE });
        int j = i + 1;
        localObject = (String)((Method)localObject).invoke(paramAssetManager, new Object[] { Integer.valueOf(j) });
        i = j;
        if (!TextUtils.isEmpty((CharSequence)localObject))
        {
          localArrayList.add(localObject);
          i = j;
        }
      }
      return localArrayList;
    }
    catch (Throwable paramAssetManager)
    {
      paramAssetManager.printStackTrace();
    }
  }
  
  private static Drawable getCachedDrawable(LongSparseArray<WeakReference<Drawable.ConstantState>> paramLongSparseArray, long paramLong)
  {
    try
    {
      Object localObject1 = (WeakReference)paramLongSparseArray.get(paramLong);
      if (localObject1 != null)
      {
        localObject1 = (Drawable.ConstantState)((WeakReference)localObject1).get();
        if (localObject1 != null)
        {
          localObject1 = ((Drawable.ConstantState)localObject1).newDrawable(mResource);
          return (Drawable)localObject1;
        }
        paramLongSparseArray.delete(paramLong);
      }
      return null;
    }
    finally {}
  }
  
  public static String getCurrentAssetPathStr(AssetManager paramAssetManager)
  {
    if (paramAssetManager == null) {
      return "";
    }
    Object localObject = getAssetPath(paramAssetManager);
    paramAssetManager = new StringBuffer();
    paramAssetManager.append("TBSResources [");
    if (localObject != null)
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        paramAssetManager.append((String)((Iterator)localObject).next());
        paramAssetManager.append(",");
      }
    }
    paramAssetManager.append("]");
    return paramAssetManager.toString();
  }
  
  private static String getCurrentEnv()
  {
    StringBuilder localStringBuilder = new StringBuilder("");
    localStringBuilder.append(mContext);
    localStringBuilder.append(mPackageName);
    localStringBuilder.append(mResource);
    Resources localResources = mResource;
    if (localResources != null)
    {
      localStringBuilder.append(localResources.getAssets());
      localStringBuilder.append(getCurrentAssetPathStr(mResource.getAssets()));
    }
    localStringBuilder.append(";");
    return localStringBuilder.toString();
  }
  
  public static int getDimensionPixelSize(int paramInt)
  {
    try
    {
      paramInt = mResource.getDimensionPixelOffset(paramInt);
      return paramInt;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getDimensionPixelSize exception: ");
      ((StringBuilder)localObject).append(localThrowable);
      Log.e("SharedResource", ((StringBuilder)localObject).toString());
      localObject = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(localThrowable);
      ((SmttServiceClientProxy)localObject).reportTbsError(3004, localStringBuilder.toString());
    }
    return 0;
  }
  
  public static Drawable getDrawable(String paramString)
  {
    for (;;)
    {
      try
      {
        int i = loadIdentifierResource(paramString, "drawable");
        localObject1 = mDrawableCache;
        long l = i;
        localObject1 = getCachedDrawable((LongSparseArray)localObject1, l);
        if (localObject1 != null) {
          return (Drawable)localObject1;
        }
        localObject2 = new Rect();
        Object localObject3 = new BitmapFactory.Options();
        Bitmap localBitmap = decodeResource(loadIdentifierResource(paramString, "drawable"), (BitmapFactory.Options)localObject3);
        if (localBitmap != null)
        {
          localObject3 = localBitmap.getNinePatchChunk();
          if (localObject3 != null)
          {
            localObject1 = localObject3;
            if (NinePatch.isNinePatchChunk((byte[])localObject3)) {
              localObject1 = drawableFromBitmap(localBitmap, (byte[])localObject1, (Rect)localObject2, paramString);
            }
          }
        }
        else
        {
          if (localObject1 != null) {
            mDrawableCache.put(l, new WeakReference(((Drawable)localObject1).getConstantState()));
          }
          return (Drawable)localObject1;
        }
      }
      catch (Throwable paramString)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("getDrawable exception: ");
        ((StringBuilder)localObject1).append(paramString);
        Log.e("SharedResource", ((StringBuilder)localObject1).toString());
        localObject1 = SmttServiceClientProxy.getInstance();
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Resource not found: ");
        ((StringBuilder)localObject2).append(paramString);
        ((SmttServiceClientProxy)localObject1).reportTbsError(3004, ((StringBuilder)localObject2).toString());
        return new BitmapDrawable();
      }
      Object localObject1 = null;
      Object localObject2 = localObject1;
    }
  }
  
  public static LayoutInflater getLayoutInflater()
  {
    return LayoutInflater.from(mContext);
  }
  
  public static String getPackageName()
  {
    return mPackageName;
  }
  
  @UsedByReflection("TbsInitPerformanceUtils.java")
  public static Map<String, String> getPerformanceData()
  {
    return mPerformanceRecord;
  }
  
  public static String getQuantityString(int paramInt1, int paramInt2)
  {
    try
    {
      String str = mResource.getQuantityString(paramInt1, paramInt2);
      return str;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getQuantityString exception: ");
      ((StringBuilder)localObject).append(localThrowable);
      Log.e("SharedResource", ((StringBuilder)localObject).toString());
      localObject = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(localThrowable);
      ((SmttServiceClientProxy)localObject).reportTbsError(3004, localStringBuilder.toString());
    }
    return "";
  }
  
  public static Context getResourceContext()
  {
    return mContext;
  }
  
  public static Resources getResources()
  {
    return mResource;
  }
  
  public static InputStream getServiceWorkerDefaultJs()
  {
    try
    {
      InputStream localInputStream = mResource.getAssets().open("sw.js");
      return localInputStream;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      SmttServiceClientProxy localSmttServiceClientProxy = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(localIOException);
      localSmttServiceClientProxy.reportTbsError(3004, localStringBuilder.toString());
    }
    return null;
  }
  
  public static InputStream getServiceWorkerToolsStream()
  {
    try
    {
      InputStream localInputStream = mResource.getAssets().open("swtools/swtools.html");
      return localInputStream;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      SmttServiceClientProxy localSmttServiceClientProxy = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(localIOException);
      localSmttServiceClientProxy.reportTbsError(3004, localStringBuilder.toString());
    }
    return null;
  }
  
  public static String getString(int paramInt)
  {
    try
    {
      String str = mResource.getString(paramInt);
      return str;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getString1 exception: ");
      ((StringBuilder)localObject).append(localThrowable);
      Log.e("SharedResource", ((StringBuilder)localObject).toString());
      localObject = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(localThrowable);
      ((SmttServiceClientProxy)localObject).reportTbsError(3004, localStringBuilder.toString());
    }
    return "";
  }
  
  public static String getString(int paramInt, Object... paramVarArgs)
  {
    return String.format(getString(paramInt), paramVarArgs);
  }
  
  public static String getString(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = mResource.getString(loadIdentifierResource(paramString1, paramString2));
      return paramString1;
    }
    catch (Throwable paramString1)
    {
      paramString2 = new StringBuilder();
      paramString2.append("getString2 exception: ");
      paramString2.append(paramString1);
      Log.e("SharedResource", paramString2.toString());
      paramString2 = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(paramString1);
      paramString2.reportTbsError(3004, localStringBuilder.toString());
    }
    return "";
  }
  
  public static String[] getStringArray(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = mResource.getStringArray(loadIdentifierResource(paramString1, paramString2));
      return paramString1;
    }
    catch (Throwable paramString1)
    {
      paramString2 = new StringBuilder();
      paramString2.append("getStringArray exception: ");
      paramString2.append(paramString1);
      Log.e("SharedResource", paramString2.toString());
      paramString2 = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(paramString1);
      paramString2.reportTbsError(3004, localStringBuilder.toString());
    }
    return new String[0];
  }
  
  public static InputStream getTBSDebugStream()
  {
    try
    {
      InputStream localInputStream = mResource.getAssets().open("tbsdebug/debug.html");
      return localInputStream;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      SmttServiceClientProxy localSmttServiceClientProxy = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(localIOException);
      localSmttServiceClientProxy.reportTbsError(3004, localStringBuilder.toString());
    }
    return null;
  }
  
  public static InputStream getTbsAdReinstallTipsStream()
  {
    try
    {
      InputStream localInputStream = mResource.getAssets().open("tbsad/tbs_reinstall_tips.js");
      return localInputStream;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      SmttServiceClientProxy localSmttServiceClientProxy = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(localIOException);
      localSmttServiceClientProxy.reportTbsError(3004, localStringBuilder.toString());
    }
    return null;
  }
  
  public static InputStream getTbsAdStream()
  {
    try
    {
      InputStream localInputStream = mResource.getAssets().open("tbsad/tbs_autoad.js");
      return localInputStream;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      SmttServiceClientProxy localSmttServiceClientProxy = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(localIOException);
      localSmttServiceClientProxy.reportTbsError(3004, localStringBuilder.toString());
    }
    return null;
  }
  
  public static CharSequence getText(int paramInt)
  {
    try
    {
      CharSequence localCharSequence = mResource.getText(paramInt);
      return localCharSequence;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getText1 exception: ");
      ((StringBuilder)localObject).append(localThrowable);
      Log.e("SharedResource", ((StringBuilder)localObject).toString());
      localObject = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(localThrowable);
      ((SmttServiceClientProxy)localObject).reportTbsError(3004, localStringBuilder.toString());
    }
    return "";
  }
  
  public static CharSequence getText(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = mResource.getText(loadIdentifierResource(paramString1, paramString2));
      return paramString1;
    }
    catch (Throwable paramString1)
    {
      paramString2 = new StringBuilder();
      paramString2.append("getText2 exception: ");
      paramString2.append(paramString1);
      Log.e("SharedResource", paramString2.toString());
      paramString2 = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(paramString1);
      paramString2.reportTbsError(3004, localStringBuilder.toString());
    }
    return "";
  }
  
  public static InputStream getUserGuidePageStream()
  {
    try
    {
      InputStream localInputStream = mResource.getAssets().open("tbsdebug/userguide.html");
      return localInputStream;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      SmttServiceClientProxy localSmttServiceClientProxy = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(localIOException);
      localSmttServiceClientProxy.reportTbsError(3004, localStringBuilder.toString());
    }
    return null;
  }
  
  public static void getValue(int paramInt, TypedValue paramTypedValue, boolean paramBoolean)
  {
    try
    {
      mResource.getValue(paramInt, paramTypedValue, paramBoolean);
      return;
    }
    catch (Throwable paramTypedValue)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getValue exception: ");
      ((StringBuilder)localObject).append(paramTypedValue);
      Log.e("SharedResource", ((StringBuilder)localObject).toString());
      localObject = SmttServiceClientProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(paramTypedValue);
      ((SmttServiceClientProxy)localObject).reportTbsError(3004, localStringBuilder.toString());
    }
  }
  
  public static void init(Context paramContext)
  {
    mContext = paramContext;
    mPackageName = paramContext.getPackageName();
    mResource = paramContext.getResources();
    paramContext = new StringBuilder();
    paramContext.append(mInitResInfo);
    paramContext.append(getCurrentEnv());
    mInitResInfo = paramContext.toString();
  }
  
  public static int loadIdentifierResource(String paramString1, String paramString2)
  {
    return loadIdentifierResource(paramString1, paramString2, true);
  }
  
  private static int loadIdentifierResource(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (paramString1.startsWith("x5_"))
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(paramString2);
      ((StringBuilder)localObject1).append("/");
      ((StringBuilder)localObject1).append(paramString1);
      localObject1 = ((StringBuilder)localObject1).toString();
      if (mIdentifiers.containsKey(localObject1)) {
        return ((Integer)mIdentifiers.get(localObject1)).intValue();
      }
      int j = mResource.getIdentifier(paramString1, paramString2, mPackageName);
      if (!paramBoolean) {
        return j;
      }
      int i = j;
      if (j == 0)
      {
        i = j;
        Object localObject2;
        if (needTbsResourceAdaptation(mContext))
        {
          localObject2 = resFetcher;
          i = j;
          if ((localObject2 instanceof TbsResourcesFetcher))
          {
            localObject2 = ((TbsResourcesFetcher)localObject2).newTbsResourcesContext(mContext);
            i = j;
            if (localObject2 != null)
            {
              init((Context)localObject2);
              i = loadIdentifierResource(paramString1, paramString2, false);
            }
          }
        }
        if (i == 0)
        {
          localObject1 = getCurrentEnv();
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("init_info: ");
          ((StringBuilder)localObject2).append(mInitResInfo);
          throw new RuntimeException(String.format("x5_res %s(type=%s) not found; %s; #%s", new Object[] { paramString1, paramString2, localObject1, ((StringBuilder)localObject2).toString() }));
        }
      }
      mIdentifiers.put(localObject1, Integer.valueOf(i));
      return i;
    }
    paramString2 = new StringBuilder();
    paramString2.append("resource name doesn't start with x5_ prefix. resource name: ");
    paramString2.append(paramString1);
    throw new RuntimeException(paramString2.toString());
  }
  
  public static boolean needTbsResourceAdaptation(Context paramContext)
  {
    if (((paramContext instanceof Context)) && (paramContext.getApplicationContext() != null))
    {
      paramContext = paramContext.getApplicationContext().getApplicationInfo().packageName;
      if (("com.tencent.mobileqq".equalsIgnoreCase(paramContext)) || ("com.tencent.mm".equalsIgnoreCase(paramContext)) || ("com.tencent.mtt".equalsIgnoreCase(paramContext)) || ("com.tencent.tbs".equalsIgnoreCase(paramContext))) {
        return false;
      }
    }
    return true;
  }
  
  private static void resourceRefetchTest()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          Thread.sleep(30000L);
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
        SharedResource.mPackageName = null;
      }
    }).start();
  }
  
  public static void setResourceFetcher(TbsResourcesFetcher paramTbsResourcesFetcher)
  {
    resFetcher = paramTbsResourcesFetcher;
  }
  
  public static abstract interface TbsResourcesFetcher
  {
    public abstract Context newTbsResourcesContext(Context paramContext);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\SharedResource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */