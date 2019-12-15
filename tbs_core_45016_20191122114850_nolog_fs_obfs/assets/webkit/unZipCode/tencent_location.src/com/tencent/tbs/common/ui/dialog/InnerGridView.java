package com.tencent.tbs.common.ui.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.GridView;

public class InnerGridView
  extends GridView
{
  public InnerGridView(Context paramContext)
  {
    super(paramContext);
  }
  
  public InnerGridView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public InnerGridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    getParent().requestDisallowInterceptTouchEvent(true);
    return super.onInterceptTouchEvent(paramMotionEvent);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\InnerGridView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */