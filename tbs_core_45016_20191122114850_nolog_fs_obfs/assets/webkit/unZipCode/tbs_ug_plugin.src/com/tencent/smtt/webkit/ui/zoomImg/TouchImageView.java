package com.tencent.smtt.webkit.ui.zoomImg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class TouchImageView
  extends ImageView
  implements CanScrollChecker.CanScrollInterface
{
  float jdField_a_of_type_Float;
  int jdField_a_of_type_Int = 0;
  Context jdField_a_of_type_AndroidContentContext;
  Bitmap jdField_a_of_type_AndroidGraphicsBitmap;
  Canvas jdField_a_of_type_AndroidGraphicsCanvas;
  final Matrix jdField_a_of_type_AndroidGraphicsMatrix = new Matrix();
  Paint jdField_a_of_type_AndroidGraphicsPaint;
  Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  TouchGestureCallback jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTouchImageView$TouchGestureCallback;
  final a jdField_a_of_type_ComTencentSmttWebkitUiZoomImgA = new a();
  c.a jdField_a_of_type_ComTencentSmttWebkitUiZoomImgC$a;
  c jdField_a_of_type_ComTencentSmttWebkitUiZoomImgC;
  Float jdField_a_of_type_JavaLangFloat;
  String jdField_a_of_type_JavaLangString = "TouchImageView";
  boolean jdField_a_of_type_Boolean;
  final float[] jdField_a_of_type_ArrayOfFloat = new float[9];
  float jdField_b_of_type_Float = 1.0F;
  int jdField_b_of_type_Int = 0;
  Float jdField_b_of_type_JavaLangFloat;
  boolean jdField_b_of_type_Boolean = false;
  float jdField_c_of_type_Float;
  private boolean jdField_c_of_type_Boolean = true;
  float d;
  
  public TouchImageView(Context paramContext)
  {
    super(paramContext);
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgC$a = new c.a()
    {
      public boolean onDoubleTap(MotionEvent paramAnonymousMotionEvent)
      {
        if (!TouchImageView.a(TouchImageView.this)) {
          return false;
        }
        TouchImageView.this.b();
        float f1 = TouchImageView.this.b();
        if (TouchImageView.this.jdField_a_of_type_Float <= f1) {
          f1 = TouchImageView.this.jdField_b_of_type_Float;
        }
        float f2 = paramAnonymousMotionEvent.getX() - (paramAnonymousMotionEvent.getX() - TouchImageView.this.c) * (f1 / TouchImageView.this.jdField_a_of_type_Float);
        float f3 = paramAnonymousMotionEvent.getY() - (paramAnonymousMotionEvent.getY() - TouchImageView.this.d) * (f1 / TouchImageView.this.jdField_a_of_type_Float);
        float f4 = TouchImageView.a(TouchImageView.this.getMeasuredWidth(), TouchImageView.this.jdField_a_of_type_Int * f1, f2, 0.0F);
        float f5 = TouchImageView.a(TouchImageView.this.getMeasuredHeight(), TouchImageView.this.jdField_b_of_type_Int * f1, f3, 0.0F);
        TouchImageView.this.clearAnimation();
        paramAnonymousMotionEvent = new TouchImageView.b(TouchImageView.this, f1, f2 + f4, f3 + f5);
        paramAnonymousMotionEvent.setDuration(300L);
        TouchImageView.this.startAnimation(paramAnonymousMotionEvent);
        return true;
      }
      
      public boolean onFling(MotionEvent paramAnonymousMotionEvent1, MotionEvent paramAnonymousMotionEvent2, float paramAnonymousFloat1, float paramAnonymousFloat2)
      {
        Log.e("burtonsun", "onFling:0");
        if (TouchImageView.this.jdField_a_of_type_Boolean) {
          return false;
        }
        Log.e("burtonsun", "onFling:");
        TouchImageView.this.b();
        float f2 = (TouchImageView.this.getMeasuredWidth() - TouchImageView.this.jdField_a_of_type_Int * TouchImageView.this.jdField_a_of_type_Float) / 2.0F;
        float f1;
        if (f2 > 0.0F) {
          f1 = f2;
        } else {
          f1 = TouchImageView.this.getMeasuredWidth() - TouchImageView.this.jdField_a_of_type_Int * TouchImageView.this.jdField_a_of_type_Float;
        }
        if (f2 <= 0.0F) {
          f2 = 0.0F;
        }
        float f3 = (TouchImageView.this.getMeasuredHeight() - TouchImageView.this.jdField_b_of_type_Int * TouchImageView.this.jdField_a_of_type_Float) / 2.0F;
        float f4;
        if (f3 > 0.0F) {
          f4 = f3;
        } else {
          f4 = TouchImageView.this.getMeasuredHeight() - TouchImageView.this.jdField_b_of_type_Int * TouchImageView.this.jdField_a_of_type_Float;
        }
        if (f3 <= 0.0F) {
          f3 = 0.0F;
        }
        TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgA.a(Math.round(TouchImageView.this.c), Math.round(TouchImageView.this.d), Math.round(paramAnonymousFloat1), Math.round(paramAnonymousFloat2), Math.round(f1), Math.round(f2), Math.round(f4), Math.round(f3));
        TouchImageView.this.clearAnimation();
        paramAnonymousMotionEvent1 = new TouchImageView.a(TouchImageView.this);
        paramAnonymousMotionEvent1.setDuration(TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgA.a());
        paramAnonymousMotionEvent1.setInterpolator(new LinearInterpolator());
        TouchImageView.this.startAnimation(paramAnonymousMotionEvent1);
        return true;
      }
      
      public void onLongPress(MotionEvent paramAnonymousMotionEvent)
      {
        Log.e("burtonsun", "-----touch image view get long touch event-----");
        TouchImageView.this.clearAnimation();
        TouchImageView.this.performLongClick();
      }
      
      public boolean onScale(ScaleGestureDetector paramAnonymousScaleGestureDetector)
      {
        if (!TouchImageView.a(TouchImageView.this)) {
          return false;
        }
        TouchImageView.this.b();
        float f4 = TouchImageView.this.jdField_a_of_type_Int * TouchImageView.this.jdField_a_of_type_Float;
        float f3 = TouchImageView.this.jdField_b_of_type_Int * TouchImageView.this.jdField_a_of_type_Float;
        float f1 = TouchImageView.c(TouchImageView.this.getMeasuredWidth(), f4, TouchImageView.this.c, paramAnonymousScaleGestureDetector.getFocusX());
        float f2 = TouchImageView.c(TouchImageView.this.getMeasuredHeight(), f3, TouchImageView.this.d, paramAnonymousScaleGestureDetector.getFocusY());
        if ((TouchImageView.this.jdField_a_of_type_JavaLangFloat != null) && (TouchImageView.this.jdField_b_of_type_JavaLangFloat != null))
        {
          f4 = TouchImageView.b(TouchImageView.this.getMeasuredWidth(), f4, TouchImageView.this.c, f1 - TouchImageView.this.jdField_a_of_type_JavaLangFloat.floatValue());
          f3 = TouchImageView.b(TouchImageView.this.getMeasuredHeight(), f3, TouchImageView.this.d, f2 - TouchImageView.this.jdField_b_of_type_JavaLangFloat.floatValue());
          if ((f4 != 0.0F) || (f3 != 0.0F)) {
            TouchImageView.this.jdField_a_of_type_AndroidGraphicsMatrix.postTranslate(f4, f3);
          }
        }
        f3 = TouchImageView.d(TouchImageView.this.b(), TouchImageView.this.jdField_b_of_type_Float, TouchImageView.this.jdField_a_of_type_Float, paramAnonymousScaleGestureDetector.getScaleFactor());
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("scale value:");
        localStringBuilder.append(f3);
        localStringBuilder.append(",detector float:");
        localStringBuilder.append(paramAnonymousScaleGestureDetector.getScaleFactor());
        Log.e("burtonsun", localStringBuilder.toString());
        TouchImageView.this.jdField_a_of_type_AndroidGraphicsMatrix.postScale(f3, f3, f1, f2);
        TouchImageView.this.jdField_a_of_type_JavaLangFloat = Float.valueOf(f1);
        TouchImageView.this.jdField_b_of_type_JavaLangFloat = Float.valueOf(f2);
        TouchImageView.this.clearAnimation();
        com.tencent.smtt.webkit.ui.a.a.a(TouchImageView.this);
        return true;
      }
      
      public boolean onScaleBegin(ScaleGestureDetector paramAnonymousScaleGestureDetector)
      {
        if (!TouchImageView.a(TouchImageView.this)) {
          return false;
        }
        paramAnonymousScaleGestureDetector = TouchImageView.this;
        paramAnonymousScaleGestureDetector.jdField_a_of_type_JavaLangFloat = null;
        paramAnonymousScaleGestureDetector.jdField_b_of_type_JavaLangFloat = null;
        Log.e("burtonsun", "----scale begin----");
        TouchImageView.this.clearAnimation();
        if (TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTouchImageView$TouchGestureCallback != null) {
          TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTouchImageView$TouchGestureCallback.onScaleBegin();
        }
        return true;
      }
      
      public void onScaleEnd(ScaleGestureDetector paramAnonymousScaleGestureDetector)
      {
        if (!TouchImageView.a(TouchImageView.this)) {
          return;
        }
        TouchImageView.this.b();
        float f3 = TouchImageView.this.jdField_a_of_type_Int;
        float f4 = TouchImageView.this.jdField_a_of_type_Float;
        float f1 = TouchImageView.this.jdField_b_of_type_Int;
        float f2 = TouchImageView.this.jdField_a_of_type_Float;
        f3 = TouchImageView.a(TouchImageView.this.getMeasuredWidth(), f3 * f4, TouchImageView.this.c, 0.0F);
        f1 = TouchImageView.a(TouchImageView.this.getMeasuredHeight(), f1 * f2, TouchImageView.this.d, 0.0F);
        if (TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTouchImageView$TouchGestureCallback != null) {
          TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTouchImageView$TouchGestureCallback.onScaleEnd();
        }
        if ((Math.abs(f3) < 1.0F) && (Math.abs(f1) < 1.0F)) {
          return;
        }
        f2 = TouchImageView.this.c + f3;
        f1 = TouchImageView.this.d + f1;
        paramAnonymousScaleGestureDetector = new StringBuilder();
        paramAnonymousScaleGestureDetector.append("scale end translateX:");
        paramAnonymousScaleGestureDetector.append(f2);
        paramAnonymousScaleGestureDetector.append(",translateY:");
        paramAnonymousScaleGestureDetector.append(f1);
        paramAnonymousScaleGestureDetector.append(",mScale=");
        paramAnonymousScaleGestureDetector.append(TouchImageView.this.jdField_a_of_type_Float);
        Log.e("burtonsun", paramAnonymousScaleGestureDetector.toString());
        TouchImageView.this.clearAnimation();
        paramAnonymousScaleGestureDetector = TouchImageView.this;
        paramAnonymousScaleGestureDetector = new TouchImageView.b(paramAnonymousScaleGestureDetector, paramAnonymousScaleGestureDetector.jdField_a_of_type_Float, f2, f1);
        paramAnonymousScaleGestureDetector.setDuration(200L);
        TouchImageView.this.startAnimation(paramAnonymousScaleGestureDetector);
        TouchImageView.this.jdField_a_of_type_Boolean = true;
      }
      
      public boolean onScroll(MotionEvent paramAnonymousMotionEvent1, MotionEvent paramAnonymousMotionEvent2, float paramAnonymousFloat1, float paramAnonymousFloat2)
      {
        Log.e("burtonsun", "onScroll:0");
        if (TouchImageView.this.jdField_a_of_type_Boolean) {
          return false;
        }
        TouchImageView.this.b();
        float f3 = TouchImageView.this.jdField_a_of_type_Int;
        float f4 = TouchImageView.this.jdField_a_of_type_Float;
        float f1 = TouchImageView.this.jdField_b_of_type_Int;
        float f2 = TouchImageView.this.jdField_a_of_type_Float;
        f3 = TouchImageView.a(TouchImageView.this.getMeasuredWidth(), f3 * f4, TouchImageView.this.c, -paramAnonymousFloat1);
        f1 = TouchImageView.a(TouchImageView.this.getMeasuredHeight(), f1 * f2, TouchImageView.this.d, -paramAnonymousFloat2);
        TouchImageView.this.jdField_a_of_type_AndroidGraphicsMatrix.postTranslate(f3, f1);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("scroll x:");
        localStringBuilder.append(f3);
        localStringBuilder.append(",y:");
        localStringBuilder.append(f1);
        localStringBuilder.append(",distance x:");
        localStringBuilder.append(paramAnonymousFloat1);
        localStringBuilder.append("distance y;");
        localStringBuilder.append(paramAnonymousFloat2);
        Log.e("burtonsun", localStringBuilder.toString());
        TouchImageView.this.clearAnimation();
        com.tencent.smtt.webkit.ui.a.a.a(TouchImageView.this);
        if (TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTouchImageView$TouchGestureCallback != null) {
          TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTouchImageView$TouchGestureCallback.onScroll(paramAnonymousMotionEvent1, paramAnonymousMotionEvent2, paramAnonymousFloat1, paramAnonymousFloat2);
        }
        return true;
      }
      
      public boolean onSingleTapConfirmed(MotionEvent paramAnonymousMotionEvent)
      {
        Log.e("burtonsun", "-----touch image view get touch event-----");
        if (TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTouchImageView$TouchGestureCallback != null) {
          TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTouchImageView$TouchGestureCallback.onSingleTapConfirmed(paramAnonymousMotionEvent);
        }
        TouchImageView.this.clearAnimation();
        return TouchImageView.this.performClick();
      }
    };
    super.setScaleType(ImageView.ScaleType.MATRIX);
  }
  
  static float a(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    paramFloat1 -= paramFloat2;
    paramFloat2 = paramFloat1 / 2.0F;
    if (paramFloat2 > 0.0F) {
      return paramFloat2 - paramFloat3;
    }
    paramFloat2 = paramFloat3 + paramFloat4;
    if (paramFloat2 > 0.0F) {
      return -paramFloat3;
    }
    if (paramFloat2 < paramFloat1) {
      return paramFloat1 - paramFloat3;
    }
    return paramFloat4;
  }
  
  static float b(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f;
    if (paramFloat1 > paramFloat2) {
      f = 0.0F;
    } else {
      f = paramFloat1 - paramFloat2;
    }
    if (paramFloat1 > paramFloat2) {
      paramFloat1 -= paramFloat2;
    } else {
      paramFloat1 = 0.0F;
    }
    if ((paramFloat3 < f) && (paramFloat4 > 0.0F))
    {
      if (paramFloat3 + paramFloat4 > paramFloat1) {
        return paramFloat1 - paramFloat3;
      }
      return paramFloat4;
    }
    if ((paramFloat3 > paramFloat1) && (paramFloat4 < 0.0F))
    {
      if (paramFloat3 + paramFloat4 < f) {
        return f - paramFloat3;
      }
      return paramFloat4;
    }
    if ((paramFloat3 > f) && (paramFloat3 < paramFloat1))
    {
      paramFloat2 = paramFloat3 + paramFloat4;
      if (paramFloat2 < f) {
        return f - paramFloat3;
      }
      if (paramFloat2 > paramFloat1) {
        return paramFloat1 - paramFloat3;
      }
      return paramFloat4;
    }
    return 0.0F;
  }
  
  static float c(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if ((paramFloat3 > 0.0F) && (paramFloat4 < paramFloat3)) {
      return paramFloat3;
    }
    if (paramFloat3 < paramFloat1 - paramFloat2)
    {
      paramFloat1 = paramFloat3 + paramFloat2;
      if (paramFloat4 > paramFloat1) {
        return paramFloat1;
      }
    }
    return paramFloat4;
  }
  
  private void c()
  {
    if ((this.jdField_a_of_type_AndroidGraphicsDrawableDrawable != null) && (getHeight() > 0))
    {
      if (getWidth() <= 0) {
        return;
      }
      if (a())
      {
        Bitmap localBitmap = this.jdField_a_of_type_AndroidGraphicsBitmap;
        if ((localBitmap != null) && (localBitmap.getHeight() == getHeight()) && (this.jdField_a_of_type_AndroidGraphicsBitmap.getWidth() == getWidth())) {}
      }
    }
    try
    {
      this.jdField_a_of_type_AndroidGraphicsBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
      this.jdField_a_of_type_AndroidGraphicsCanvas = new Canvas(this.jdField_a_of_type_AndroidGraphicsBitmap);
      if (this.jdField_a_of_type_AndroidGraphicsPaint != null) {
        break label126;
      }
      this.jdField_a_of_type_AndroidGraphicsPaint = new Paint();
      return;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      for (;;) {}
    }
    this.jdField_a_of_type_AndroidGraphicsBitmap = null;
    this.jdField_a_of_type_AndroidGraphicsCanvas = null;
    return;
    label126:
    return;
  }
  
  static float d(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f = paramFloat3 * paramFloat4;
    if (f < paramFloat1) {
      return paramFloat1 / paramFloat3;
    }
    if (f > paramFloat2) {
      return paramFloat2 / paramFloat3;
    }
    return paramFloat4;
  }
  
  float a()
  {
    float f1 = getMeasuredWidth() / this.jdField_a_of_type_Int;
    float f2 = this.jdField_b_of_type_Float;
    if (f1 > f2) {
      return f2;
    }
    return f1;
  }
  
  public float a(float paramFloat)
  {
    return (getMeasuredWidth() - this.jdField_a_of_type_Int * paramFloat) / 2.0F;
  }
  
  void a()
  {
    this.jdField_a_of_type_AndroidGraphicsMatrix.reset();
    float f = a();
    this.jdField_a_of_type_AndroidGraphicsMatrix.postScale(f, f);
    float[] arrayOfFloat = new float[9];
    this.jdField_a_of_type_AndroidGraphicsMatrix.getValues(arrayOfFloat);
    this.jdField_a_of_type_AndroidGraphicsMatrix.postTranslate(a(f), b(f));
    invalidate();
  }
  
  public boolean a()
  {
    Drawable localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null)
    {
      if ((localDrawable instanceof TileBitmapDrawable)) {
        return false;
      }
      return (localDrawable.getIntrinsicHeight() > 4096) || (this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth() > 4096);
    }
    return false;
  }
  
  float b()
  {
    float f1 = getMeasuredWidth() / this.jdField_a_of_type_Int;
    float f2 = getMeasuredHeight();
    int i = this.jdField_b_of_type_Int;
    f2 /= i;
    if (this.jdField_b_of_type_Boolean)
    {
      if (this.jdField_a_of_type_Int > i) {
        f1 = f2;
      }
    }
    else {
      f1 = Math.min(f1, f2);
    }
    float f3 = this.jdField_b_of_type_Float;
    f2 = f1;
    if (f1 > f3) {
      f2 = f3;
    }
    return f2;
  }
  
  public float b(float paramFloat)
  {
    float f = getMeasuredWidth() / this.jdField_a_of_type_Int;
    if (f > getMeasuredHeight() / this.jdField_b_of_type_Int)
    {
      paramFloat = this.jdField_b_of_type_Float;
      if (f <= paramFloat) {
        paramFloat = f;
      }
      if (this.jdField_b_of_type_Int * paramFloat >= getMeasuredHeight()) {
        return 0.0F;
      }
      return (getMeasuredHeight() - this.jdField_b_of_type_Int * paramFloat) / 2.0F;
    }
    return (getMeasuredHeight() - this.jdField_b_of_type_Int * paramFloat) / 2.0F;
  }
  
  void b()
  {
    this.jdField_a_of_type_AndroidGraphicsMatrix.getValues(this.jdField_a_of_type_ArrayOfFloat);
    float[] arrayOfFloat = this.jdField_a_of_type_ArrayOfFloat;
    this.jdField_a_of_type_Float = arrayOfFloat[0];
    this.jdField_c_of_type_Float = arrayOfFloat[2];
    this.d = arrayOfFloat[5];
  }
  
  public boolean canScrollHorizontally(int paramInt)
  {
    b();
    if (paramInt > 0) {
      return Math.round(this.jdField_c_of_type_Float) < 0;
    }
    if (paramInt < 0)
    {
      float f1 = this.jdField_a_of_type_Int;
      float f2 = this.jdField_a_of_type_Float;
      return Math.round(this.jdField_c_of_type_Float) > getMeasuredWidth() - Math.round(f1 * f2);
    }
    return false;
  }
  
  public void clearAnimation()
  {
    super.clearAnimation();
    this.jdField_a_of_type_Boolean = false;
  }
  
  public Matrix getImageMatrix()
  {
    return this.jdField_a_of_type_AndroidGraphicsMatrix;
  }
  
  public boolean horizontalCanScroll(int paramInt)
  {
    b();
    if (paramInt < 0) {
      return Math.round(this.jdField_c_of_type_Float) < 0;
    }
    if (paramInt > 0)
    {
      float f1 = this.jdField_a_of_type_Int;
      float f2 = this.jdField_a_of_type_Float;
      return Math.round(this.jdField_c_of_type_Float) > getMeasuredWidth() - Math.round(f1 * f2);
    }
    return false;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.setImageMatrix(this.jdField_a_of_type_AndroidGraphicsMatrix);
    try
    {
      if ((a()) && (this.jdField_a_of_type_AndroidGraphicsBitmap != null) && (this.jdField_a_of_type_AndroidGraphicsCanvas != null))
      {
        this.jdField_a_of_type_AndroidGraphicsBitmap.eraseColor(0);
        this.jdField_a_of_type_AndroidGraphicsCanvas.drawBitmap(((BitmapDrawable)this.jdField_a_of_type_AndroidGraphicsDrawableDrawable).getBitmap(), this.jdField_a_of_type_AndroidGraphicsMatrix, this.jdField_a_of_type_AndroidGraphicsPaint);
        paramCanvas.drawBitmap(this.jdField_a_of_type_AndroidGraphicsBitmap, 0.0F, 0.0F, this.jdField_a_of_type_AndroidGraphicsPaint);
        Log.e("burtonsun", "use soft draw");
        return;
      }
      super.onDraw(paramCanvas);
      return;
    }
    catch (Exception paramCanvas) {}
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = getMeasuredWidth();
    int j = getMeasuredHeight();
    super.onMeasure(paramInt1, paramInt2);
    if ((i != getMeasuredWidth()) || (j != getMeasuredHeight()))
    {
      clearAnimation();
      a();
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if ((paramInt1 != 0) && (paramInt2 != 0)) {
      c();
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    c localc = this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgC;
    if (localc != null)
    {
      localc.a(paramMotionEvent);
      return true;
    }
    return false;
  }
  
  public void setImageDrawable(Drawable paramDrawable)
  {
    super.setImageDrawable(paramDrawable);
    if (this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgC == null) {
      this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgC = new c(this.jdField_a_of_type_AndroidContentContext, this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgC$a);
    }
    if (this.jdField_a_of_type_AndroidGraphicsDrawableDrawable != paramDrawable)
    {
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = paramDrawable;
      if (paramDrawable != null)
      {
        int i = paramDrawable.getIntrinsicWidth();
        int j = paramDrawable.getIntrinsicHeight();
        if ((this.jdField_a_of_type_Int != i) || (this.jdField_b_of_type_Int != j))
        {
          this.jdField_a_of_type_Int = i;
          this.jdField_b_of_type_Int = j;
          a();
          c();
        }
      }
      else
      {
        this.jdField_a_of_type_Int = 0;
        this.jdField_b_of_type_Int = 0;
      }
    }
  }
  
  public void setImageMatrix(Matrix paramMatrix)
  {
    Matrix localMatrix = paramMatrix;
    if (paramMatrix == null) {
      localMatrix = new Matrix();
    }
    if (!this.jdField_a_of_type_AndroidGraphicsMatrix.equals(localMatrix))
    {
      this.jdField_a_of_type_AndroidGraphicsMatrix.set(localMatrix);
      invalidate();
    }
  }
  
  public void setScaleType(ImageView.ScaleType paramScaleType)
  {
    if (paramScaleType == ImageView.ScaleType.MATRIX)
    {
      super.setScaleType(paramScaleType);
      return;
    }
    throw new IllegalArgumentException("Unsupported scaleType. Only ScaleType.MATRIX is allowed.");
  }
  
  public boolean verticalCanScroll(int paramInt)
  {
    return false;
  }
  
  public static abstract interface TouchGestureCallback
  {
    public abstract void onScaleBegin();
    
    public abstract void onScaleEnd();
    
    public abstract void onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2);
    
    public abstract void onSingleTapConfirmed(MotionEvent paramMotionEvent);
  }
  
  class a
    extends Animation
  {
    a() {}
    
    protected void applyTransformation(float paramFloat, Transformation paramTransformation)
    {
      TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgA.a(paramFloat);
      TouchImageView.this.b();
      paramFloat = TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgA.b();
      float f1 = TouchImageView.this.c;
      float f2 = TouchImageView.this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgA.c();
      float f3 = TouchImageView.this.d;
      TouchImageView.this.jdField_a_of_type_AndroidGraphicsMatrix.postTranslate(paramFloat - f1, f2 - f3);
      com.tencent.smtt.webkit.ui.a.a.a(TouchImageView.this);
    }
  }
  
  class b
    extends Animation
  {
    float jdField_a_of_type_Float;
    float b;
    float c;
    float d;
    float e;
    float f;
    
    b(float paramFloat1, float paramFloat2, float paramFloat3)
    {
      TouchImageView.this.b();
      this.jdField_a_of_type_Float = TouchImageView.this.jdField_a_of_type_Float;
      this.b = TouchImageView.this.c;
      this.c = TouchImageView.this.d;
      this.d = paramFloat1;
      this.e = paramFloat2;
      this.f = paramFloat3;
    }
    
    protected void applyTransformation(float paramFloat, Transformation paramTransformation)
    {
      TouchImageView.this.b();
      if (paramFloat >= 1.0F)
      {
        TouchImageView.this.jdField_a_of_type_AndroidGraphicsMatrix.getValues(TouchImageView.this.jdField_a_of_type_ArrayOfFloat);
        TouchImageView.this.jdField_a_of_type_ArrayOfFloat[0] = this.d;
        TouchImageView.this.jdField_a_of_type_ArrayOfFloat[4] = this.d;
        TouchImageView.this.jdField_a_of_type_ArrayOfFloat[2] = this.e;
        TouchImageView.this.jdField_a_of_type_ArrayOfFloat[5] = this.f;
        TouchImageView.this.jdField_a_of_type_AndroidGraphicsMatrix.setValues(TouchImageView.this.jdField_a_of_type_ArrayOfFloat);
      }
      else
      {
        float f1 = this.jdField_a_of_type_Float;
        f1 = (f1 + (this.d - f1) * paramFloat) / TouchImageView.this.jdField_a_of_type_Float;
        TouchImageView.this.jdField_a_of_type_AndroidGraphicsMatrix.postScale(f1, f1);
        TouchImageView.this.jdField_a_of_type_AndroidGraphicsMatrix.getValues(TouchImageView.this.jdField_a_of_type_ArrayOfFloat);
        f1 = TouchImageView.this.jdField_a_of_type_ArrayOfFloat[2];
        float f2 = TouchImageView.this.jdField_a_of_type_ArrayOfFloat[5];
        float f3 = this.b;
        float f4 = this.e;
        float f5 = this.c;
        float f6 = this.f;
        TouchImageView.this.jdField_a_of_type_AndroidGraphicsMatrix.postTranslate(f3 + (f4 - f3) * paramFloat - f1, f5 + paramFloat * (f6 - f5) - f2);
      }
      com.tencent.smtt.webkit.ui.a.a.a(TouchImageView.this);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\zoomImg\TouchImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */