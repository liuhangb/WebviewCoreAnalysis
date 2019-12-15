package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.resources.TBSResources;

public class QBSelectView
  extends LinearLayout
{
  public static final int ID_COPY_BUTTON = 999;
  public static final int ID_EDIT_BUTTON = 997;
  public static final int ID_SEARCH_BUTTON = 998;
  int jdField_a_of_type_Int = TBSResources.getDimensionPixelSize("reader_select_view_padding");
  Context jdField_a_of_type_AndroidContentContext;
  TextView jdField_a_of_type_AndroidWidgetTextView;
  int jdField_b_of_type_Int = TBSResources.getDimensionPixelSize("setting_item_x_offset_28db");
  TextView jdField_b_of_type_AndroidWidgetTextView;
  public String mSelectText;
  
  public QBSelectView(Context paramContext)
  {
    super(paramContext);
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  void a(View paramView, Rect paramRect)
  {
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)getLayoutParams();
    localLayoutParams.leftMargin = (paramRect.left + (paramRect.right - paramRect.left) / 2 - localLayoutParams.width / 2);
    if (localLayoutParams.leftMargin < 0) {
      localLayoutParams.leftMargin = 0;
    }
    if (localLayoutParams.leftMargin > paramView.getWidth() - localLayoutParams.width) {
      localLayoutParams.leftMargin = (paramView.getWidth() - localLayoutParams.width);
    }
    if (paramRect.top - localLayoutParams.height - this.jdField_a_of_type_Int - this.jdField_b_of_type_Int > 0)
    {
      localLayoutParams.topMargin = (paramRect.top - localLayoutParams.height - this.jdField_a_of_type_Int);
      a(true);
    }
    else if (paramView.getHeight() - (paramRect.bottom + localLayoutParams.height + this.jdField_a_of_type_Int + this.jdField_b_of_type_Int) > 0)
    {
      localLayoutParams.topMargin = (paramRect.bottom + this.jdField_a_of_type_Int + this.jdField_b_of_type_Int);
      a(false);
    }
    else
    {
      localLayoutParams.topMargin = ((paramRect.bottom - paramRect.top) / 2 + paramRect.top - localLayoutParams.height / 2);
      a(true);
    }
    setLayoutParams(localLayoutParams);
  }
  
  void a(String paramString1, String paramString2, int paramInt)
  {
    setLayoutParams(new FrameLayout.LayoutParams(TBSResources.getDimensionPixelSize("reader_select_view_width"), TBSResources.getDimensionPixelSize("reader_select_view_height"), 48));
    setOrientation(0);
    this.jdField_a_of_type_AndroidWidgetTextView = new TextView(getContext());
    this.jdField_a_of_type_AndroidWidgetTextView.setId(999);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1, 1.0F);
    this.jdField_a_of_type_AndroidWidgetTextView.setLayoutParams(localLayoutParams);
    this.jdField_a_of_type_AndroidWidgetTextView.setGravity(17);
    this.jdField_a_of_type_AndroidWidgetTextView.setPadding(0, 0, 0, TBSResources.getDimensionPixelSize("reader_selectview_text_padding"));
    this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(0, TBSResources.getDimensionPixelSize("reader_fontsize_t3"));
    this.jdField_a_of_type_AndroidWidgetTextView.setTextColor(TBSResources.getColor("reader_select_text_color"));
    this.jdField_a_of_type_AndroidWidgetTextView.setText(paramString1);
    addView(this.jdField_a_of_type_AndroidWidgetTextView);
    this.jdField_a_of_type_AndroidWidgetTextView.setBackgroundDrawable(TBSResources.getDrawable("reader_select_bg_up_left"));
    this.jdField_b_of_type_AndroidWidgetTextView = new TextView(getContext());
    this.jdField_b_of_type_AndroidWidgetTextView.setId(paramInt);
    this.jdField_b_of_type_AndroidWidgetTextView.setTextSize(0, TBSResources.getDimensionPixelSize("reader_fontsize_t3"));
    this.jdField_b_of_type_AndroidWidgetTextView.setTextColor(TBSResources.getColor("reader_select_text_color"));
    paramString1 = new LinearLayout.LayoutParams(-1, -1, 1.0F);
    paramString1.gravity = 17;
    this.jdField_b_of_type_AndroidWidgetTextView.setLayoutParams(paramString1);
    this.jdField_b_of_type_AndroidWidgetTextView.setGravity(17);
    this.jdField_b_of_type_AndroidWidgetTextView.setPadding(0, 0, 0, TBSResources.getDimensionPixelSize("reader_selectview_text_padding"));
    this.jdField_b_of_type_AndroidWidgetTextView.setText(paramString2);
    this.jdField_a_of_type_AndroidWidgetTextView.setBackgroundDrawable(TBSResources.getDrawable("reader_select_bg_up_right"));
    addView(this.jdField_b_of_type_AndroidWidgetTextView);
    if (!Configuration.getInstance(this.jdField_a_of_type_AndroidContentContext).isFileReaderMenuEnabled(90)) {
      this.jdField_b_of_type_AndroidWidgetTextView.setVisibility(8);
    }
  }
  
  void a(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.jdField_a_of_type_AndroidWidgetTextView.setBackgroundDrawable(TBSResources.getDrawable("reader_select_bg_up_left"));
      this.jdField_b_of_type_AndroidWidgetTextView.setBackgroundDrawable(TBSResources.getDrawable("reader_select_bg_up_right"));
      this.jdField_a_of_type_AndroidWidgetTextView.setGravity(17);
      this.jdField_b_of_type_AndroidWidgetTextView.setGravity(17);
      this.jdField_a_of_type_AndroidWidgetTextView.setPadding(0, 0, 0, TBSResources.getDimensionPixelSize("reader_selectview_text_padding"));
      this.jdField_b_of_type_AndroidWidgetTextView.setPadding(0, 0, 0, TBSResources.getDimensionPixelSize("reader_selectview_text_padding"));
    }
    else
    {
      this.jdField_a_of_type_AndroidWidgetTextView.setBackgroundDrawable(TBSResources.getDrawable("reader_select_bg_down_left"));
      this.jdField_b_of_type_AndroidWidgetTextView.setBackgroundDrawable(TBSResources.getDrawable("reader_select_bg_down_right"));
      this.jdField_a_of_type_AndroidWidgetTextView.setGravity(17);
      this.jdField_b_of_type_AndroidWidgetTextView.setGravity(17);
      this.jdField_a_of_type_AndroidWidgetTextView.setPadding(0, TBSResources.getDimensionPixelSize("reader_selectview_text_padding"), 0, 0);
      this.jdField_b_of_type_AndroidWidgetTextView.setPadding(0, TBSResources.getDimensionPixelSize("reader_selectview_text_padding"), 0, 0);
    }
    invalidate();
  }
  
  public String getSelectText()
  {
    return this.mSelectText;
  }
  
  public void hideSelectView(FrameLayout paramFrameLayout)
  {
    paramFrameLayout.removeView(this);
  }
  
  public void setClickLintener(View.OnClickListener paramOnClickListener)
  {
    this.jdField_a_of_type_AndroidWidgetTextView.setOnClickListener(paramOnClickListener);
    this.jdField_b_of_type_AndroidWidgetTextView.setOnClickListener(paramOnClickListener);
  }
  
  public void showSelectView(FrameLayout paramFrameLayout, Rect paramRect, String paramString)
  {
    a(paramFrameLayout, paramRect);
    if (getParent() == null) {
      paramFrameLayout.addView(this);
    }
    bringToFront();
    this.mSelectText = paramString;
    setTag(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\QBSelectView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */