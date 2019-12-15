package com.tencent.smtt.webkit.input;

import android.view.ViewTreeObserver.OnTouchModeChangeListener;

abstract interface CursorController
  extends ViewTreeObserver.OnTouchModeChangeListener
{
  public abstract void afterEndUpdatingPosition(b paramb);
  
  public abstract void beforeStartUpdatingPosition(b paramb);
  
  public abstract void hide();
  
  public abstract boolean isNightMode();
  
  public abstract boolean isShowing();
  
  public abstract void onDetached();
  
  public abstract void show();
  
  public abstract void updateHandleViewSliderPosition(float paramFloat1, float paramFloat2);
  
  public abstract void updatePosition(b paramb, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\input\CursorController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */