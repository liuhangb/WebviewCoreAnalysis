package com.tencent.mtt.external.reader.utils;

import android.graphics.Paint;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewParent;

public final class ViewCompat
{
  public static final int LAYER_TYPE_HARDWARE = 2;
  public static final int LAYER_TYPE_NONE = 0;
  public static final int LAYER_TYPE_SOFTWARE = 1;
  public static final int LAYOUT_DIRECTION_INHERIT = 2;
  public static final int LAYOUT_DIRECTION_LOCALE = 3;
  public static final int LAYOUT_DIRECTION_LTR = 0;
  public static final int LAYOUT_DIRECTION_RTL = 1;
  private static final h a = new a();
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 17)
    {
      a = new f();
      return;
    }
    if (i >= 16)
    {
      a = new g();
      return;
    }
    if (i >= 14)
    {
      a = new e();
      return;
    }
    if (i >= 11)
    {
      a = new c();
      return;
    }
    if (i >= 9)
    {
      a = new b();
      return;
    }
  }
  
  public static boolean canScrollHorizontally(View paramView, int paramInt)
  {
    return a.a(paramView, paramInt);
  }
  
  public static boolean canScrollVertically(View paramView, int paramInt)
  {
    return a.b(paramView, paramInt);
  }
  
  public static float getAlpha(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getAlpha();
    }
    return d.a(paramView);
  }
  
  public static int getLayerType(View paramView)
  {
    return a.a(paramView);
  }
  
  public static int getLayoutDirection(ViewParent paramViewParent)
  {
    return a.a(paramViewParent);
  }
  
  public static int getMinimumHeight(View paramView)
  {
    return a.b(paramView);
  }
  
  public static int getMinimumWidth(View paramView)
  {
    return a.c(paramView);
  }
  
  public static float getPivotX(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getPivotX();
    }
    return d.b(paramView);
  }
  
  public static float getPivotY(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getPivotY();
    }
    return d.c(paramView);
  }
  
  public static float getRotation(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getRotation();
    }
    return d.d(paramView);
  }
  
  public static float getRotationX(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getRotationX();
    }
    return d.e(paramView);
  }
  
  public static float getRotationY(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getRotationY();
    }
    return d.f(paramView);
  }
  
  public static float getScaleX(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getScaleX();
    }
    return d.g(paramView);
  }
  
  public static float getScaleY(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getScaleY();
    }
    return d.h(paramView);
  }
  
  public static float getScrollX(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getScrollX();
    }
    return d.i(paramView);
  }
  
  public static float getScrollY(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getScrollY();
    }
    return d.j(paramView);
  }
  
  public static float getTranslationX(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getTranslationX();
    }
    return d.k(paramView);
  }
  
  public static float getTranslationY(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getTranslationY();
    }
    return d.l(paramView);
  }
  
  public static float getX(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getX();
    }
    return d.m(paramView);
  }
  
  public static float getY(View paramView)
  {
    if (AnimatorProxy.NEEDS_PROXY) {
      return AnimatorProxy.wrap(paramView).getY();
    }
    return d.n(paramView);
  }
  
  public static boolean isHardwareAccelerated(View paramView)
  {
    return a.a(paramView);
  }
  
  public static void postInvalidateOnAnimation(View paramView)
  {
    a.a(paramView);
  }
  
  public static void postInvalidateOnAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    a.a(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static void postOnAnimation(View paramView, Runnable paramRunnable)
  {
    a.a(paramView, paramRunnable);
  }
  
  public static void postOnAnimationDelayed(View paramView, Runnable paramRunnable, int paramInt)
  {
    a.a(paramView, paramRunnable, paramInt);
  }
  
  public static void postOnAnimationDelayed(View paramView, Runnable paramRunnable, long paramLong)
  {
    a.a(paramView, paramRunnable, paramLong);
  }
  
  public static void setAlpha(View paramView, float paramFloat)
  {
    if (paramView == null) {
      return;
    }
    float f;
    if (paramFloat < 0.0F)
    {
      f = 0.0F;
    }
    else
    {
      f = paramFloat;
      if (paramFloat > 1.0F) {
        f = 1.0F;
      }
    }
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setAlpha(f);
      return;
    }
    d.a(paramView, f);
  }
  
  public static void setDefaultLayotuDirection(View paramView)
  {
    setLayoutDirection(paramView, 0);
  }
  
  public static void setLayerPaint(View paramView, Paint paramPaint)
  {
    a.a(paramView, paramPaint);
  }
  
  public static void setLayerType(View paramView, int paramInt, Paint paramPaint)
  {
    a.a(paramView, paramInt, paramPaint);
  }
  
  public static void setLayoutDirection(View paramView, int paramInt)
  {
    a.a(paramView, paramInt);
  }
  
  public static void setPivotX(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setPivotX(paramFloat);
      return;
    }
    d.b(paramView, paramFloat);
  }
  
  public static void setPivotY(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setPivotY(paramFloat);
      return;
    }
    d.c(paramView, paramFloat);
  }
  
  public static void setRotation(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setRotation(paramFloat);
      return;
    }
    d.d(paramView, paramFloat);
  }
  
  public static void setRotationX(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setRotationX(paramFloat);
      return;
    }
    d.e(paramView, paramFloat);
  }
  
  public static void setRotationY(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setRotationY(paramFloat);
      return;
    }
    d.f(paramView, paramFloat);
  }
  
  public static void setScaleX(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setScaleX(paramFloat);
      return;
    }
    d.g(paramView, paramFloat);
  }
  
  public static void setScaleY(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setScaleY(paramFloat);
      return;
    }
    d.h(paramView, paramFloat);
  }
  
  public static void setScrollX(View paramView, int paramInt)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setScrollX(paramInt);
      return;
    }
    d.a(paramView, paramInt);
  }
  
  public static void setScrollY(View paramView, int paramInt)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setScrollY(paramInt);
      return;
    }
    d.b(paramView, paramInt);
  }
  
  public static void setTranslationX(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setTranslationX(paramFloat);
      return;
    }
    d.i(paramView, paramFloat);
  }
  
  public static void setTranslationY(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setTranslationY(paramFloat);
      return;
    }
    d.j(paramView, paramFloat);
  }
  
  public static void setX(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setX(paramFloat);
      return;
    }
    d.k(paramView, paramFloat);
  }
  
  public static void setY(View paramView, float paramFloat)
  {
    if (AnimatorProxy.NEEDS_PROXY)
    {
      AnimatorProxy.wrap(paramView).setY(paramFloat);
      return;
    }
    d.l(paramView, paramFloat);
  }
  
  static class a
    implements ViewCompat.h
  {
    protected int a()
    {
      return 10;
    }
    
    public int a(View paramView)
    {
      return 0;
    }
    
    public int a(ViewParent paramViewParent)
    {
      return 0;
    }
    
    public void a(View paramView)
    {
      paramView.postInvalidateDelayed(a());
    }
    
    public void a(View paramView, int paramInt) {}
    
    public void a(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramView.postInvalidateDelayed(a(), paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void a(View paramView, int paramInt, Paint paramPaint) {}
    
    public void a(View paramView, Paint paramPaint) {}
    
    public void a(View paramView, Runnable paramRunnable)
    {
      paramView.postDelayed(paramRunnable, a());
    }
    
    public void a(View paramView, Runnable paramRunnable, long paramLong)
    {
      paramView.postDelayed(paramRunnable, a() + paramLong);
    }
    
    public boolean a(View paramView)
    {
      return false;
    }
    
    public boolean a(View paramView, int paramInt)
    {
      return false;
    }
    
    public int b(View paramView)
    {
      return 0;
    }
    
    public boolean b(View paramView, int paramInt)
    {
      return false;
    }
    
    public int c(View paramView)
    {
      return 0;
    }
  }
  
  static class b
    extends ViewCompat.a
  {}
  
  static class c
    extends ViewCompat.b
  {
    public int a(View paramView)
    {
      return paramView.getLayerType();
    }
    
    public void a(View paramView, int paramInt, Paint paramPaint)
    {
      paramView.setLayerType(paramInt, paramPaint);
    }
    
    public boolean a(View paramView)
    {
      return paramView.isHardwareAccelerated();
    }
  }
  
  private static final class d
  {
    static float a(View paramView)
    {
      return paramView.getAlpha();
    }
    
    static void a(View paramView, float paramFloat)
    {
      paramView.setAlpha(paramFloat);
    }
    
    static void a(View paramView, int paramInt)
    {
      paramView.setScrollX(paramInt);
    }
    
    static float b(View paramView)
    {
      return paramView.getPivotX();
    }
    
    static void b(View paramView, float paramFloat)
    {
      paramView.setPivotX(paramFloat);
    }
    
    static void b(View paramView, int paramInt)
    {
      paramView.setScrollY(paramInt);
    }
    
    static float c(View paramView)
    {
      return paramView.getPivotY();
    }
    
    static void c(View paramView, float paramFloat)
    {
      paramView.setPivotY(paramFloat);
    }
    
    static float d(View paramView)
    {
      return paramView.getRotation();
    }
    
    static void d(View paramView, float paramFloat)
    {
      paramView.setRotation(paramFloat);
    }
    
    static float e(View paramView)
    {
      return paramView.getRotationX();
    }
    
    static void e(View paramView, float paramFloat)
    {
      paramView.setRotationX(paramFloat);
    }
    
    static float f(View paramView)
    {
      return paramView.getRotationY();
    }
    
    static void f(View paramView, float paramFloat)
    {
      paramView.setRotationY(paramFloat);
    }
    
    static float g(View paramView)
    {
      return paramView.getScaleX();
    }
    
    static void g(View paramView, float paramFloat)
    {
      paramView.setScaleX(paramFloat);
    }
    
    static float h(View paramView)
    {
      return paramView.getScaleY();
    }
    
    static void h(View paramView, float paramFloat)
    {
      paramView.setScaleY(paramFloat);
    }
    
    static float i(View paramView)
    {
      return paramView.getScrollX();
    }
    
    static void i(View paramView, float paramFloat)
    {
      paramView.setTranslationX(paramFloat);
    }
    
    static float j(View paramView)
    {
      return paramView.getScrollY();
    }
    
    static void j(View paramView, float paramFloat)
    {
      paramView.setTranslationY(paramFloat);
    }
    
    static float k(View paramView)
    {
      return paramView.getTranslationX();
    }
    
    static void k(View paramView, float paramFloat)
    {
      paramView.setX(paramFloat);
    }
    
    static float l(View paramView)
    {
      return paramView.getTranslationY();
    }
    
    static void l(View paramView, float paramFloat)
    {
      paramView.setY(paramFloat);
    }
    
    static float m(View paramView)
    {
      return paramView.getX();
    }
    
    static float n(View paramView)
    {
      return paramView.getY();
    }
  }
  
  static class e
    extends ViewCompat.b
  {
    public boolean a(View paramView, int paramInt)
    {
      return paramView.canScrollHorizontally(paramInt);
    }
    
    public boolean b(View paramView, int paramInt)
    {
      return paramView.canScrollVertically(paramInt);
    }
  }
  
  static class f
    extends ViewCompat.g
  {
    public int a(ViewParent paramViewParent)
    {
      return 0;
    }
    
    public void a(View paramView, int paramInt)
    {
      paramView.setLayoutDirection(paramInt);
    }
    
    public void a(View paramView, Paint paramPaint) {}
  }
  
  static class g
    extends ViewCompat.e
  {
    public void a(View paramView)
    {
      paramView.postInvalidateOnAnimation();
    }
    
    public void a(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramView.postInvalidateOnAnimation(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void a(View paramView, Runnable paramRunnable)
    {
      paramView.postOnAnimation(paramRunnable);
    }
    
    public void a(View paramView, Runnable paramRunnable, long paramLong)
    {
      paramView.postOnAnimationDelayed(paramRunnable, paramLong);
    }
    
    public int b(View paramView)
    {
      return paramView.getMinimumHeight();
    }
    
    public int c(View paramView)
    {
      return paramView.getMinimumWidth();
    }
  }
  
  static abstract interface h
  {
    public abstract int a(View paramView);
    
    public abstract int a(ViewParent paramViewParent);
    
    public abstract void a(View paramView);
    
    public abstract void a(View paramView, int paramInt);
    
    public abstract void a(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
    
    public abstract void a(View paramView, int paramInt, Paint paramPaint);
    
    public abstract void a(View paramView, Paint paramPaint);
    
    public abstract void a(View paramView, Runnable paramRunnable);
    
    public abstract void a(View paramView, Runnable paramRunnable, long paramLong);
    
    public abstract boolean a(View paramView);
    
    public abstract boolean a(View paramView, int paramInt);
    
    public abstract int b(View paramView);
    
    public abstract boolean b(View paramView, int paramInt);
    
    public abstract int c(View paramView);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\utils\ViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */