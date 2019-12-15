package com.tencent.tbs.tbsshell.partner.reader;

import android.graphics.Paint;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewParent;

public final class ViewCompat
{
  private static final int FAKE_FRAME_TIME = 10;
  private static final ViewCompatImpl IMPL = new BaseViewCompatImpl();
  public static final int LAYER_TYPE_HARDWARE = 2;
  public static final int LAYER_TYPE_NONE = 0;
  public static final int LAYER_TYPE_SOFTWARE = 1;
  public static final int LAYOUT_DIRECTION_INHERIT = 2;
  public static final int LAYOUT_DIRECTION_LOCALE = 3;
  public static final int LAYOUT_DIRECTION_LTR = 0;
  public static final int LAYOUT_DIRECTION_RTL = 1;
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 17)
    {
      IMPL = new JBMR1ViewCompatIMPL();
      return;
    }
    if (i >= 16)
    {
      IMPL = new JBViewCompatImpl();
      return;
    }
    if (i >= 14)
    {
      IMPL = new ICSViewCompatImpl();
      return;
    }
    if (i >= 11)
    {
      IMPL = new HCViewCompatImpl();
      return;
    }
    if (i >= 9)
    {
      IMPL = new GBViewCompatImpl();
      return;
    }
  }
  
  public static void postInvalidateOnAnimation(View paramView)
  {
    IMPL.postInvalidateOnAnimation(paramView);
  }
  
  public static void postInvalidateOnAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    IMPL.postInvalidateOnAnimation(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static void postOnAnimation(View paramView, Runnable paramRunnable)
  {
    IMPL.postOnAnimation(paramView, paramRunnable);
  }
  
  public static void postOnAnimationDelayed(View paramView, Runnable paramRunnable, int paramInt)
  {
    IMPL.postOnAnimationDelayed(paramView, paramRunnable, paramInt);
  }
  
  static class BaseViewCompatImpl
    implements ViewCompat.ViewCompatImpl
  {
    public boolean canScrollHorizontally(View paramView, int paramInt)
    {
      return false;
    }
    
    public boolean canScrollVertically(View paramView, int paramInt)
    {
      return false;
    }
    
    protected int getFrameTime()
    {
      return 10;
    }
    
    public int getLayerType(View paramView)
    {
      return 0;
    }
    
    public int getLayoutDirection(ViewParent paramViewParent)
    {
      return 0;
    }
    
    public int getMinimumHeight(View paramView)
    {
      return 0;
    }
    
    public int getMinimumWidth(View paramView)
    {
      return 0;
    }
    
    public boolean isHardwareAccelerated(View paramView)
    {
      return false;
    }
    
    public void postInvalidateOnAnimation(View paramView)
    {
      paramView.postInvalidateDelayed(getFrameTime());
    }
    
    public void postInvalidateOnAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramView.postInvalidateDelayed(getFrameTime(), paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void postOnAnimation(View paramView, Runnable paramRunnable)
    {
      paramView.postDelayed(paramRunnable, getFrameTime());
    }
    
    public void postOnAnimationDelayed(View paramView, Runnable paramRunnable, long paramLong)
    {
      paramView.postDelayed(paramRunnable, getFrameTime() + paramLong);
    }
    
    public void setLayerPaint(View paramView, Paint paramPaint) {}
    
    public void setLayerType(View paramView, int paramInt, Paint paramPaint) {}
    
    public void setLayoutDirection(View paramView, int paramInt) {}
  }
  
  static class GBViewCompatImpl
    extends ViewCompat.BaseViewCompatImpl
  {}
  
  static class HCViewCompatImpl
    extends ViewCompat.GBViewCompatImpl
  {
    public int getLayerType(View paramView)
    {
      return paramView.getLayerType();
    }
    
    public boolean isHardwareAccelerated(View paramView)
    {
      return paramView.isHardwareAccelerated();
    }
    
    public void setLayerType(View paramView, int paramInt, Paint paramPaint)
    {
      paramView.setLayerType(paramInt, paramPaint);
    }
  }
  
  static class ICSViewCompatImpl
    extends ViewCompat.GBViewCompatImpl
  {
    public boolean canScrollHorizontally(View paramView, int paramInt)
    {
      return paramView.canScrollHorizontally(paramInt);
    }
    
    public boolean canScrollVertically(View paramView, int paramInt)
    {
      return paramView.canScrollVertically(paramInt);
    }
  }
  
  static class JBMR1ViewCompatIMPL
    extends ViewCompat.JBViewCompatImpl
  {
    public int getLayoutDirection(ViewParent paramViewParent)
    {
      return 0;
    }
    
    public void setLayerPaint(View paramView, Paint paramPaint) {}
    
    public void setLayoutDirection(View paramView, int paramInt)
    {
      paramView.setLayoutDirection(paramInt);
    }
  }
  
  static class JBViewCompatImpl
    extends ViewCompat.ICSViewCompatImpl
  {
    public int getMinimumHeight(View paramView)
    {
      return paramView.getMinimumHeight();
    }
    
    public int getMinimumWidth(View paramView)
    {
      return paramView.getMinimumWidth();
    }
    
    public void postInvalidateOnAnimation(View paramView)
    {
      paramView.postInvalidateOnAnimation();
    }
    
    public void postInvalidateOnAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramView.postInvalidateOnAnimation(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void postOnAnimation(View paramView, Runnable paramRunnable)
    {
      paramView.postOnAnimation(paramRunnable);
    }
    
    public void postOnAnimationDelayed(View paramView, Runnable paramRunnable, long paramLong)
    {
      paramView.postOnAnimationDelayed(paramRunnable, paramLong);
    }
  }
  
  static abstract interface ViewCompatImpl
  {
    public abstract boolean canScrollHorizontally(View paramView, int paramInt);
    
    public abstract boolean canScrollVertically(View paramView, int paramInt);
    
    public abstract int getLayerType(View paramView);
    
    public abstract int getLayoutDirection(ViewParent paramViewParent);
    
    public abstract int getMinimumHeight(View paramView);
    
    public abstract int getMinimumWidth(View paramView);
    
    public abstract boolean isHardwareAccelerated(View paramView);
    
    public abstract void postInvalidateOnAnimation(View paramView);
    
    public abstract void postInvalidateOnAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
    
    public abstract void postOnAnimation(View paramView, Runnable paramRunnable);
    
    public abstract void postOnAnimationDelayed(View paramView, Runnable paramRunnable, long paramLong);
    
    public abstract void setLayerPaint(View paramView, Paint paramPaint);
    
    public abstract void setLayerType(View paramView, int paramInt, Paint paramPaint);
    
    public abstract void setLayoutDirection(View paramView, int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\reader\ViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */