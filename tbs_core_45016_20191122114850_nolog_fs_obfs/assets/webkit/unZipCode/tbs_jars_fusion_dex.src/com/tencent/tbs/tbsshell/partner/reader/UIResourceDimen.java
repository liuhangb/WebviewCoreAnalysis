package com.tencent.tbs.tbsshell.partner.reader;

public class UIResourceDimen
{
  public static float mDensity;
  
  public static int dip2px(float paramFloat)
  {
    return (int)(paramFloat * mDensity + 0.5F);
  }
  
  public static class dimen
  {
    public static final int dr_tabhost_mask_margin_b;
    public static final int list_watermark_distance;
    public static final int tab_mask_width_left;
    public static final int tab_mask_width_right;
    public static final int text_paint_offset_y;
    public static final int uifw_addressbar_height;
    public static final int uifw_annulus_progress_width = UIResourceDimen.dip2px(1.0F);
    public static final int uifw_control_loading_default;
    public static final int uifw_control_loading_icon_height;
    public static final int uifw_control_loading_icon_width;
    public static final int uifw_control_scrollbar_height;
    public static final int uifw_control_scrollbar_left_offset;
    public static final int uifw_control_scrollbar_min_height;
    public static final int uifw_control_scrollbar_width;
    public static final int uifw_control_switch_btn_button_height;
    public static final int uifw_control_switch_btn_button_width;
    public static final int uifw_control_switch_btn_height;
    public static final int uifw_control_switch_btn_width;
    public static final int uifw_control_textsize_default;
    public static final int uifw_control_textspace_default;
    public static final int uifw_divider_marginleft;
    public static final int uifw_gallery_dot_padding;
    public static final int uifw_list_loading_height;
    public static final int uifw_list_speedup_threshold;
    public static final int uifw_listitem_contentbase_marginTopandBottom;
    public static final int uifw_listitem_contentbase_marginbetweenchilds;
    public static final int uifw_listitem_contentbase_marginleftandright;
    public static final int uifw_listitem_custom_view_height;
    public static final int uifw_listitem_custom_view_width;
    public static final int uifw_menu_updateicon_right_margin;
    public static final int uifw_menu_updateicon_top_margin;
    public static final int uifw_mtt_app_indiator_bottom_margin;
    public static final int uifw_mtt_app_list_adv_dot_space;
    public static final int uifw_mtt_app_list_adv_width_extenstin;
    public static final int uifw_radio_btn_height;
    public static final int uifw_radio_btn_maigin;
    public static final int uifw_radio_btn_text_left_maigin;
    public static final int uifw_radio_btn_two_line_height;
    public static final int uifw_reader_thdcall_loading_height;
    public static final int uifw_recycler_delete_velocity;
    public static final int uifw_recycler_item_checkbox_left_margin;
    public static final int uifw_recycler_item_checkbox_width;
    public static final int uifw_recycler_item_customerview_width;
    public static final int uifw_recycler_item_height = UIResourceDimen.dip2px(120.0F);
    public static final int uifw_recycler_list_delete_button_width;
    public static final int uifw_recycler_refresh_icon_distance;
    public static final int uifw_recycler_refresh_icon_distance_between_icon_text;
    public static final int uifw_recycler_refresh_icon_distance_offset;
    public static final int uifw_recycler_refresh_icon_offset;
    public static final int uifw_recycler_refresh_text_height;
    public static final int uifw_recycler_refresh_text_offset;
    public static final int uifw_recycler_refresh_text_width;
    public static final int uifw_recycler_springback_max_distance;
    public static final int uifw_scroll_min_step;
    public static final int uifw_style_btn_hollow_padding;
    public static final int uifw_tab_scroll_bar_height;
    public static final int uifw_tabhost_tab_height;
    public static final int uifw_text_paint_offset_y;
    public static final int uifw_textsize_10;
    public static final int uifw_textsize_11;
    public static final int uifw_textsize_12;
    public static final int uifw_textsize_14;
    public static final int uifw_textsize_16;
    public static final int uifw_toolbar_height;
    public static final int uifw_updateicon_margin;
    public static final int uifw_updateicon_size;
    public static final int uifw_updateicon_size_withtext;
    public static final int uifw_updateicon_text_margin;
    public static final int uifw_water_mark_offset;
    
    static
    {
      uifw_recycler_item_checkbox_width = UIResourceDimen.dip2px(48.0F);
      uifw_recycler_item_checkbox_left_margin = UIResourceDimen.dip2px(15.0F);
      uifw_recycler_item_customerview_width = UIResourceDimen.dip2px(38.0F);
      uifw_recycler_delete_velocity = UIResourceDimen.dip2px(500.0F);
      uifw_scroll_min_step = UIResourceDimen.dip2px(1.0F);
      uifw_recycler_refresh_text_offset = UIResourceDimen.dip2px(9.0F);
      uifw_recycler_refresh_icon_offset = UIResourceDimen.dip2px(48.0F);
      uifw_recycler_refresh_icon_distance = UIResourceDimen.dip2px(25.0F);
      uifw_recycler_refresh_icon_distance_between_icon_text = UIResourceDimen.dip2px(8.0F);
      uifw_recycler_refresh_text_height = UIResourceDimen.dip2px(36.0F);
      uifw_recycler_refresh_text_width = UIResourceDimen.dip2px(130.0F);
      uifw_recycler_refresh_icon_distance_offset = UIResourceDimen.dip2px(10.0F);
      uifw_recycler_list_delete_button_width = UIResourceDimen.dip2px(70.0F);
      uifw_mtt_app_indiator_bottom_margin = UIResourceDimen.dip2px(6.0F);
      uifw_mtt_app_list_adv_width_extenstin = UIResourceDimen.dip2px(42.0F);
      uifw_mtt_app_list_adv_dot_space = UIResourceDimen.dip2px(5.0F);
      uifw_gallery_dot_padding = UIResourceDimen.dip2px(5.0F);
      uifw_tab_scroll_bar_height = UIResourceDimen.dip2px(3.0F);
      uifw_text_paint_offset_y = UIResourceDimen.dip2px(1.0F);
      uifw_updateicon_text_margin = UIResourceDimen.dip2px(10.0F);
      uifw_updateicon_margin = UIResourceDimen.dip2px(4.0F);
      uifw_updateicon_size = UIResourceDimen.dip2px(3.0F);
      uifw_updateicon_size_withtext = UIResourceDimen.dip2px(7.0F);
      uifw_textsize_14 = UIResourceDimen.dip2px(14.0F);
      uifw_textsize_12 = UIResourceDimen.dip2px(12.0F);
      uifw_textsize_16 = UIResourceDimen.dip2px(16.0F);
      uifw_water_mark_offset = UIResourceDimen.dip2px(30.0F);
      uifw_listitem_contentbase_marginleftandright = UIResourceDimen.dip2px(16.0F);
      uifw_listitem_contentbase_marginTopandBottom = UIResourceDimen.dip2px(13.0F);
      uifw_listitem_contentbase_marginbetweenchilds = UIResourceDimen.dip2px(8.0F);
      uifw_listitem_custom_view_width = UIResourceDimen.dip2px(45.0F);
      uifw_listitem_custom_view_height = UIResourceDimen.dip2px(40.0F);
      uifw_addressbar_height = UIResourceDimen.dip2px(48.0F);
      uifw_toolbar_height = UIResourceDimen.dip2px(48.0F);
      uifw_control_scrollbar_min_height = UIResourceDimen.dip2px(15.0F);
      uifw_control_scrollbar_width = UIResourceDimen.dip2px(3.0F);
      uifw_control_scrollbar_height = UIResourceDimen.dip2px(3.0F);
      uifw_control_scrollbar_left_offset = UIResourceDimen.dip2px(3.0F);
      uifw_list_speedup_threshold = UIResourceDimen.dip2px(50.0F);
      uifw_control_textsize_default = UIResourceDimen.dip2px(14.0F);
      uifw_control_textspace_default = UIResourceDimen.dip2px(2.0F);
      uifw_control_loading_default = UIResourceDimen.dip2px(5.0F);
      uifw_control_loading_icon_width = UIResourceDimen.dip2px(24.0F);
      uifw_control_loading_icon_height = UIResourceDimen.dip2px(24.0F);
      uifw_reader_thdcall_loading_height = UIResourceDimen.dip2px(50.0F);
      uifw_list_loading_height = UIResourceDimen.dip2px(36.0F);
      uifw_recycler_springback_max_distance = UIResourceDimen.dip2px(40.0F);
      uifw_radio_btn_height = UIResourceDimen.dip2px(48.0F);
      uifw_radio_btn_two_line_height = UIResourceDimen.dip2px(64.0F);
      uifw_radio_btn_maigin = UIResourceDimen.dip2px(24.0F);
      uifw_radio_btn_text_left_maigin = UIResourceDimen.dip2px(16.0F);
      uifw_control_switch_btn_width = UIResourceDimen.dip2px(53.0F);
      uifw_control_switch_btn_height = UIResourceDimen.dip2px(27.0F);
      uifw_control_switch_btn_button_width = UIResourceDimen.dip2px(27.0F);
      uifw_control_switch_btn_button_height = UIResourceDimen.dip2px(27.0F);
      uifw_textsize_11 = UIResourceDimen.dip2px(11.0F);
      uifw_textsize_10 = UIResourceDimen.dip2px(10.0F);
      uifw_menu_updateicon_right_margin = UIResourceDimen.dip2px(0.0F);
      uifw_menu_updateicon_top_margin = UIResourceDimen.dip2px(0.0F);
      uifw_style_btn_hollow_padding = UIResourceDimen.dip2px(1.0F);
      uifw_tabhost_tab_height = UIResourceDimen.dip2px(48.0F);
      list_watermark_distance = UIResourceDimen.dip2px(20.0F);
      text_paint_offset_y = UIResourceDimen.dip2px(1.2F);
      tab_mask_width_right = UIResourceDimen.dip2px(16.0F);
      tab_mask_width_left = UIResourceDimen.dip2px(7.0F);
      dr_tabhost_mask_margin_b = UIResourceDimen.dip2px(3.0F);
      uifw_divider_marginleft = UIResourceDimen.dip2px(24.0F);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\reader\UIResourceDimen.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */