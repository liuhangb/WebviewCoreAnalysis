package com.tencent.smtt.webkit.nativewidget;

import android.view.MotionEvent;
import android.view.Surface;

public abstract interface INativeWidget
{
  public abstract void destroy();
  
  public abstract void handleTouchEvent(MotionEvent paramMotionEvent);
  
  public abstract int height();
  
  public abstract boolean isAvailable();
  
  public abstract void onAttachedToWindow();
  
  public abstract void onDetachedFromWindow();
  
  public abstract void setContentRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void setSurface(Surface paramSurface);
  
  public abstract int width();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\nativewidget\INativeWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */