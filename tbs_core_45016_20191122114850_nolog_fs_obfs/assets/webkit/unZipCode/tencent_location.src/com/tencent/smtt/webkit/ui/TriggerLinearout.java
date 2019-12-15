package com.tencent.smtt.webkit.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TriggerLinearout
  extends LinearLayout
{
  OnSizeChangeCallback jdField_a_of_type_ComTencentSmttWebkitUiTriggerLinearout$OnSizeChangeCallback;
  boolean jdField_a_of_type_Boolean = true;
  
  public TriggerLinearout(Context paramContext)
  {
    super(paramContext);
  }
  
  public TriggerLinearout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  @TargetApi(11)
  public TriggerLinearout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public TriggerLinearout(Context paramContext, OnSizeChangeCallback paramOnSizeChangeCallback)
  {
    super(paramContext);
    this.jdField_a_of_type_ComTencentSmttWebkitUiTriggerLinearout$OnSizeChangeCallback = paramOnSizeChangeCallback;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((!this.jdField_a_of_type_Boolean) && (this.jdField_a_of_type_ComTencentSmttWebkitUiTriggerLinearout$OnSizeChangeCallback != null) && (5 < Math.abs(paramInt4 - paramInt2)) && (5 < Math.abs(paramInt3 - paramInt1))) {
      this.jdField_a_of_type_ComTencentSmttWebkitUiTriggerLinearout$OnSizeChangeCallback.onSizeChangeCallback();
    }
    this.jdField_a_of_type_Boolean ^= true;
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static abstract interface OnSizeChangeCallback
  {
    public abstract void onSizeChangeCallback();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\TriggerLinearout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */