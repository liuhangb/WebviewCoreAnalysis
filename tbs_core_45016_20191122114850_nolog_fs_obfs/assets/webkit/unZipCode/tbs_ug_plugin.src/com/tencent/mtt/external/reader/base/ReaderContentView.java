package com.tencent.mtt.external.reader.base;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class ReaderContentView
  extends ReaderBaseView
{
  FrameLayout.LayoutParams jdField_a_of_type_AndroidWidgetFrameLayout$LayoutParams = null;
  FrameLayout jdField_a_of_type_AndroidWidgetFrameLayout = null;
  
  public ReaderContentView(Context paramContext)
  {
    this.jdField_a_of_type_AndroidWidgetFrameLayout = new FrameLayout(paramContext);
  }
  
  public int create()
  {
    this.jdField_a_of_type_AndroidWidgetFrameLayout$LayoutParams = ((FrameLayout.LayoutParams)this.jdField_a_of_type_AndroidWidgetFrameLayout.getLayoutParams());
    return 0;
  }
  
  public void destroy()
  {
    FrameLayout localFrameLayout = this.jdField_a_of_type_AndroidWidgetFrameLayout;
    if (localFrameLayout != null) {
      localFrameLayout.removeAllViews();
    }
    this.jdField_a_of_type_AndroidWidgetFrameLayout = null;
  }
  
  public int getContentButtomMargin()
  {
    FrameLayout.LayoutParams localLayoutParams = this.jdField_a_of_type_AndroidWidgetFrameLayout$LayoutParams;
    if (localLayoutParams != null) {
      return localLayoutParams.bottomMargin;
    }
    return 0;
  }
  
  public int getContentHeight()
  {
    FrameLayout.LayoutParams localLayoutParams = this.jdField_a_of_type_AndroidWidgetFrameLayout$LayoutParams;
    if (localLayoutParams != null) {
      return localLayoutParams.height;
    }
    return 0;
  }
  
  public int getContentLeftMargin()
  {
    FrameLayout.LayoutParams localLayoutParams = this.jdField_a_of_type_AndroidWidgetFrameLayout$LayoutParams;
    if (localLayoutParams != null) {
      return localLayoutParams.leftMargin;
    }
    return 0;
  }
  
  public int getContentRightMargin()
  {
    FrameLayout.LayoutParams localLayoutParams = this.jdField_a_of_type_AndroidWidgetFrameLayout$LayoutParams;
    if (localLayoutParams != null) {
      return localLayoutParams.rightMargin;
    }
    return 0;
  }
  
  public int getContentTopMargin()
  {
    FrameLayout.LayoutParams localLayoutParams = this.jdField_a_of_type_AndroidWidgetFrameLayout$LayoutParams;
    if (localLayoutParams != null) {
      return localLayoutParams.topMargin;
    }
    return 0;
  }
  
  public int getContentWidth()
  {
    FrameLayout.LayoutParams localLayoutParams = this.jdField_a_of_type_AndroidWidgetFrameLayout$LayoutParams;
    if (localLayoutParams != null) {
      return localLayoutParams.width;
    }
    return 0;
  }
  
  public FrameLayout getFrameLayout()
  {
    return this.jdField_a_of_type_AndroidWidgetFrameLayout;
  }
  
  public void setContentHeight(int paramInt) {}
  
  public void setContentWidth(int paramInt) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderContentView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */