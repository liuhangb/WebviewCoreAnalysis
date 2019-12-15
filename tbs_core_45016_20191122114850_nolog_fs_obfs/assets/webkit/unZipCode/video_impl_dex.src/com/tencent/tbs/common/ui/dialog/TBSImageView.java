package com.tencent.tbs.common.ui.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class TBSImageView
  extends ImageView
{
  private OnMeasureListener onMeasureListener;
  
  public TBSImageView(Context paramContext)
  {
    super(paramContext);
  }
  
  public TBSImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public TBSImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    OnMeasureListener localOnMeasureListener = this.onMeasureListener;
    if (localOnMeasureListener != null) {
      localOnMeasureListener.onMeasureSize(getMeasuredWidth(), getMeasuredHeight());
    }
  }
  
  public void setOnMeasureListener(OnMeasureListener paramOnMeasureListener)
  {
    this.onMeasureListener = paramOnMeasureListener;
  }
  
  public static abstract interface OnMeasureListener
  {
    public abstract void onMeasureSize(int paramInt1, int paramInt2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\TBSImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */