package com.tencent.mtt.external.reader.utils;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class AnimatorProxy
  extends Animation
{
  public static final boolean NEEDS_PROXY;
  private static final WeakHashMap<View, AnimatorProxy> jdField_a_of_type_JavaUtilWeakHashMap = new WeakHashMap();
  private float jdField_a_of_type_Float = 1.0F;
  private final Camera jdField_a_of_type_AndroidGraphicsCamera = new Camera();
  private final Matrix jdField_a_of_type_AndroidGraphicsMatrix = new Matrix();
  private final RectF jdField_a_of_type_AndroidGraphicsRectF = new RectF();
  private final WeakReference<View> jdField_a_of_type_JavaLangRefWeakReference;
  private boolean jdField_a_of_type_Boolean;
  private float jdField_b_of_type_Float;
  private final RectF jdField_b_of_type_AndroidGraphicsRectF = new RectF();
  private float c;
  private float d;
  private float e;
  private float f;
  private float g = 1.0F;
  private float h = 1.0F;
  private float i;
  private float j;
  
  static
  {
    boolean bool;
    if (Integer.valueOf(Build.VERSION.SDK).intValue() < 11) {
      bool = true;
    } else {
      bool = false;
    }
    NEEDS_PROXY = bool;
  }
  
  private AnimatorProxy(View paramView)
  {
    setDuration(0L);
    setFillAfter(true);
    if (paramView != null) {
      paramView.setAnimation(this);
    }
    this.jdField_a_of_type_JavaLangRefWeakReference = new WeakReference(paramView);
  }
  
  private void a()
  {
    View localView = (View)this.jdField_a_of_type_JavaLangRefWeakReference.get();
    if (localView != null) {
      a(this.jdField_a_of_type_AndroidGraphicsRectF, localView);
    }
  }
  
  private void a(Matrix paramMatrix, View paramView)
  {
    float f3 = paramView.getWidth();
    float f4 = paramView.getHeight();
    boolean bool = this.jdField_a_of_type_Boolean;
    float f1;
    if (bool) {
      f1 = this.jdField_b_of_type_Float;
    } else {
      f1 = f3 / 2.0F;
    }
    float f2;
    if (bool) {
      f2 = this.c;
    } else {
      f2 = f4 / 2.0F;
    }
    float f5 = this.d;
    float f6 = this.e;
    float f7 = this.f;
    if ((f5 != 0.0F) || (f6 != 0.0F) || (f7 != 0.0F))
    {
      paramView = this.jdField_a_of_type_AndroidGraphicsCamera;
      paramView.save();
      paramView.rotateX(f5);
      paramView.rotateY(f6);
      paramView.rotateZ(-f7);
      paramView.getMatrix(paramMatrix);
      paramView.restore();
      paramMatrix.preTranslate(-f1, -f2);
      paramMatrix.postTranslate(f1, f2);
    }
    f5 = this.g;
    f6 = this.h;
    if ((f5 != 1.0F) || (f6 != 1.0F))
    {
      paramMatrix.postScale(f5, f6);
      paramMatrix.postTranslate(-(f1 / f3) * (f5 * f3 - f3), -(f2 / f4) * (f6 * f4 - f4));
    }
    paramMatrix.postTranslate(this.i, this.j);
  }
  
  private void a(RectF paramRectF, View paramView)
  {
    paramRectF.set(0.0F, 0.0F, paramView.getWidth(), paramView.getHeight());
    Matrix localMatrix = this.jdField_a_of_type_AndroidGraphicsMatrix;
    localMatrix.reset();
    a(localMatrix, paramView);
    this.jdField_a_of_type_AndroidGraphicsMatrix.mapRect(paramRectF);
    paramRectF.offset(paramView.getLeft(), paramView.getTop());
    float f1;
    if (paramRectF.right < paramRectF.left)
    {
      f1 = paramRectF.right;
      paramRectF.right = paramRectF.left;
      paramRectF.left = f1;
    }
    if (paramRectF.bottom < paramRectF.top)
    {
      f1 = paramRectF.top;
      paramRectF.top = paramRectF.bottom;
      paramRectF.bottom = f1;
    }
  }
  
  private void b()
  {
    View localView = (View)this.jdField_a_of_type_JavaLangRefWeakReference.get();
    if (localView != null)
    {
      if (localView.getParent() == null) {
        return;
      }
      RectF localRectF = this.jdField_b_of_type_AndroidGraphicsRectF;
      a(localRectF, localView);
      localRectF.union(this.jdField_a_of_type_AndroidGraphicsRectF);
      ((View)localView.getParent()).invalidate((int)Math.floor(localRectF.left), (int)Math.floor(localRectF.top), (int)Math.ceil(localRectF.right), (int)Math.ceil(localRectF.bottom));
      return;
    }
  }
  
  public static AnimatorProxy wrap(View paramView)
  {
    AnimatorProxy localAnimatorProxy2 = (AnimatorProxy)jdField_a_of_type_JavaUtilWeakHashMap.get(paramView);
    AnimatorProxy localAnimatorProxy1;
    if ((localAnimatorProxy2 != null) && (paramView != null))
    {
      localAnimatorProxy1 = localAnimatorProxy2;
      if (localAnimatorProxy2 == paramView.getAnimation()) {}
    }
    else
    {
      localAnimatorProxy1 = new AnimatorProxy(paramView);
      jdField_a_of_type_JavaUtilWeakHashMap.put(paramView, localAnimatorProxy1);
    }
    return localAnimatorProxy1;
  }
  
  protected void applyTransformation(float paramFloat, Transformation paramTransformation)
  {
    View localView = (View)this.jdField_a_of_type_JavaLangRefWeakReference.get();
    if (localView != null)
    {
      paramTransformation.setAlpha(this.jdField_a_of_type_Float);
      a(paramTransformation.getMatrix(), localView);
    }
  }
  
  public float getAlpha()
  {
    return this.jdField_a_of_type_Float;
  }
  
  public float getPivotX()
  {
    return this.jdField_b_of_type_Float;
  }
  
  public float getPivotY()
  {
    return this.c;
  }
  
  public float getRotation()
  {
    return this.f;
  }
  
  public float getRotationX()
  {
    return this.d;
  }
  
  public float getRotationY()
  {
    return this.e;
  }
  
  public float getScaleX()
  {
    return this.g;
  }
  
  public float getScaleY()
  {
    return this.h;
  }
  
  public int getScrollX()
  {
    View localView = (View)this.jdField_a_of_type_JavaLangRefWeakReference.get();
    if (localView == null) {
      return 0;
    }
    return localView.getScrollX();
  }
  
  public int getScrollY()
  {
    View localView = (View)this.jdField_a_of_type_JavaLangRefWeakReference.get();
    if (localView == null) {
      return 0;
    }
    return localView.getScrollY();
  }
  
  public float getTranslationX()
  {
    return this.i;
  }
  
  public float getTranslationY()
  {
    return this.j;
  }
  
  public float getX()
  {
    View localView = (View)this.jdField_a_of_type_JavaLangRefWeakReference.get();
    if (localView == null) {
      return 0.0F;
    }
    return localView.getLeft() + this.i;
  }
  
  public float getY()
  {
    View localView = (View)this.jdField_a_of_type_JavaLangRefWeakReference.get();
    if (localView == null) {
      return 0.0F;
    }
    return localView.getTop() + this.j;
  }
  
  public void setAlpha(float paramFloat)
  {
    if (this.jdField_a_of_type_Float != paramFloat)
    {
      this.jdField_a_of_type_Float = paramFloat;
      View localView = (View)this.jdField_a_of_type_JavaLangRefWeakReference.get();
      if (localView != null) {
        localView.invalidate();
      }
    }
  }
  
  public void setPivotX(float paramFloat)
  {
    if ((!this.jdField_a_of_type_Boolean) || (this.jdField_b_of_type_Float != paramFloat))
    {
      a();
      this.jdField_a_of_type_Boolean = true;
      this.jdField_b_of_type_Float = paramFloat;
      b();
    }
  }
  
  public void setPivotY(float paramFloat)
  {
    if ((!this.jdField_a_of_type_Boolean) || (this.c != paramFloat))
    {
      a();
      this.jdField_a_of_type_Boolean = true;
      this.c = paramFloat;
      b();
    }
  }
  
  public void setRotation(float paramFloat)
  {
    if (this.f != paramFloat)
    {
      a();
      this.f = paramFloat;
      b();
    }
  }
  
  public void setRotationX(float paramFloat)
  {
    if (this.d != paramFloat)
    {
      a();
      this.d = paramFloat;
      b();
    }
  }
  
  public void setRotationY(float paramFloat)
  {
    if (this.e != paramFloat)
    {
      a();
      this.e = paramFloat;
      b();
    }
  }
  
  public void setScaleX(float paramFloat)
  {
    if (this.g != paramFloat)
    {
      a();
      this.g = paramFloat;
      b();
    }
  }
  
  public void setScaleY(float paramFloat)
  {
    if (this.h != paramFloat)
    {
      a();
      this.h = paramFloat;
      b();
    }
  }
  
  public void setScrollX(int paramInt)
  {
    View localView = (View)this.jdField_a_of_type_JavaLangRefWeakReference.get();
    if (localView != null) {
      localView.scrollTo(paramInt, localView.getScrollY());
    }
  }
  
  public void setScrollY(int paramInt)
  {
    View localView = (View)this.jdField_a_of_type_JavaLangRefWeakReference.get();
    if (localView != null) {
      localView.scrollTo(localView.getScrollX(), paramInt);
    }
  }
  
  public void setTranslationX(float paramFloat)
  {
    if (this.i != paramFloat)
    {
      a();
      this.i = paramFloat;
      b();
    }
  }
  
  public void setTranslationY(float paramFloat)
  {
    if (this.j != paramFloat)
    {
      a();
      this.j = paramFloat;
      b();
    }
  }
  
  public void setX(float paramFloat)
  {
    View localView = (View)this.jdField_a_of_type_JavaLangRefWeakReference.get();
    if (localView != null) {
      setTranslationX(paramFloat - localView.getLeft());
    }
  }
  
  public void setY(float paramFloat)
  {
    View localView = (View)this.jdField_a_of_type_JavaLangRefWeakReference.get();
    if (localView != null) {
      setTranslationY(paramFloat - localView.getTop());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\utils\AnimatorProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */