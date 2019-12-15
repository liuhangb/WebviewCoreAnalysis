package org.chromium.content.browser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.metrics.RecordHistogram;

public class PopupZoomer
  extends View
{
  private static float jdField_a_of_type_Float;
  private static Rect jdField_a_of_type_AndroidGraphicsRect;
  private static Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  private long jdField_a_of_type_Long;
  private Bitmap jdField_a_of_type_AndroidGraphicsBitmap;
  private final PointF jdField_a_of_type_AndroidGraphicsPointF = new PointF();
  private RectF jdField_a_of_type_AndroidGraphicsRectF;
  private final GestureDetector jdField_a_of_type_AndroidViewGestureDetector;
  private final Interpolator jdField_a_of_type_AndroidViewAnimationInterpolator = new OvershootInterpolator();
  private final OnTapListener jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer$OnTapListener;
  private final OnVisibilityChangedListener jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer$OnVisibilityChangedListener;
  private boolean jdField_a_of_type_Boolean;
  private float jdField_b_of_type_Float;
  private long jdField_b_of_type_Long;
  private Rect jdField_b_of_type_AndroidGraphicsRect;
  private RectF jdField_b_of_type_AndroidGraphicsRectF;
  private final Interpolator jdField_b_of_type_AndroidViewAnimationInterpolator = new ReverseInterpolator(this.jdField_a_of_type_AndroidViewAnimationInterpolator);
  private boolean jdField_b_of_type_Boolean;
  private float jdField_c_of_type_Float;
  private RectF jdField_c_of_type_AndroidGraphicsRectF;
  private boolean jdField_c_of_type_Boolean;
  private float d = 1.0F;
  private float e;
  private float f;
  private float g;
  private float h;
  private float i;
  private float j;
  private float k;
  private float l;
  private float m;
  private float n;
  
  public PopupZoomer(Context paramContext, ViewGroup paramViewGroup, OnVisibilityChangedListener paramOnVisibilityChangedListener, OnTapListener paramOnTapListener)
  {
    super(paramContext);
    this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer$OnVisibilityChangedListener = paramOnVisibilityChangedListener;
    this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer$OnTapListener = paramOnTapListener;
    setVisibility(4);
    setFocusable(true);
    setFocusableInTouchMode(true);
    this.jdField_a_of_type_AndroidViewGestureDetector = new GestureDetector(paramContext, new GestureDetector.SimpleOnGestureListener()
    {
      private boolean a(MotionEvent paramAnonymousMotionEvent, boolean paramAnonymousBoolean)
      {
        if (PopupZoomer.a(PopupZoomer.this)) {
          return true;
        }
        float f1 = paramAnonymousMotionEvent.getX();
        float f2 = paramAnonymousMotionEvent.getY();
        if (PopupZoomer.a(PopupZoomer.this, f1, f2))
        {
          PopupZoomer.a(PopupZoomer.this);
          return true;
        }
        if (PopupZoomer.a(PopupZoomer.this) != null)
        {
          PointF localPointF = PopupZoomer.a(PopupZoomer.this, f1, f2);
          PopupZoomer.a(PopupZoomer.this).onResolveTapDisambiguation(paramAnonymousMotionEvent.getEventTime(), localPointF.x, localPointF.y, paramAnonymousBoolean);
          PopupZoomer.b(PopupZoomer.this);
        }
        return true;
      }
      
      public void onLongPress(MotionEvent paramAnonymousMotionEvent)
      {
        a(paramAnonymousMotionEvent, true);
      }
      
      public boolean onScroll(MotionEvent paramAnonymousMotionEvent1, MotionEvent paramAnonymousMotionEvent2, float paramAnonymousFloat1, float paramAnonymousFloat2)
      {
        if (PopupZoomer.a(PopupZoomer.this)) {
          return true;
        }
        if (PopupZoomer.a(PopupZoomer.this, paramAnonymousMotionEvent1.getX(), paramAnonymousMotionEvent1.getY()))
        {
          PopupZoomer.a(PopupZoomer.this);
          return true;
        }
        PopupZoomer.a(PopupZoomer.this, paramAnonymousFloat1, paramAnonymousFloat2);
        return true;
      }
      
      public boolean onSingleTapUp(MotionEvent paramAnonymousMotionEvent)
      {
        return a(paramAnonymousMotionEvent, false);
      }
    });
  }
  
  private static float a(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 < paramFloat2) {
      return paramFloat2;
    }
    paramFloat2 = paramFloat1;
    if (paramFloat1 > paramFloat3) {
      paramFloat2 = paramFloat3;
    }
    return paramFloat2;
  }
  
  private static float a(Context paramContext)
  {
    if (jdField_a_of_type_Float == 0.0F) {
      jdField_a_of_type_Float = 1.0F;
    }
    return jdField_a_of_type_Float;
  }
  
  private static int a(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 < paramInt2) {
      return paramInt2;
    }
    paramInt2 = paramInt1;
    if (paramInt1 > paramInt3) {
      paramInt2 = paramInt3;
    }
    return paramInt2;
  }
  
  private PointF a(float paramFloat1, float paramFloat2)
  {
    float f1 = this.jdField_b_of_type_Float;
    float f2 = this.jdField_c_of_type_Float;
    return new PointF(this.jdField_a_of_type_AndroidGraphicsPointF.x + (paramFloat1 - f1 - this.jdField_a_of_type_AndroidGraphicsPointF.x - this.i) / this.d, this.jdField_a_of_type_AndroidGraphicsPointF.y + (paramFloat2 - f2 - this.jdField_a_of_type_AndroidGraphicsPointF.y - this.j) / this.d);
  }
  
  private static Drawable a(Context paramContext)
  {
    if (jdField_a_of_type_AndroidGraphicsDrawableDrawable == null)
    {
      jdField_a_of_type_AndroidGraphicsDrawableDrawable = new ColorDrawable();
      jdField_a_of_type_AndroidGraphicsRect = new Rect();
      jdField_a_of_type_AndroidGraphicsDrawableDrawable.getPadding(jdField_a_of_type_AndroidGraphicsRect);
    }
    return jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  }
  
  private void a()
  {
    this.jdField_a_of_type_Boolean = false;
    this.jdField_b_of_type_Boolean = false;
    this.jdField_b_of_type_Long = 0L;
    OnVisibilityChangedListener localOnVisibilityChangedListener = this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer$OnVisibilityChangedListener;
    if (localOnVisibilityChangedListener != null) {
      localOnVisibilityChangedListener.onPopupZoomerHidden(this);
    }
    setVisibility(4);
    this.jdField_a_of_type_AndroidGraphicsBitmap.recycle();
    this.jdField_a_of_type_AndroidGraphicsBitmap = null;
  }
  
  private void a(float paramFloat1, float paramFloat2)
  {
    this.i = a(this.i - paramFloat1, this.k, this.l);
    this.j = a(this.j - paramFloat2, this.m, this.n);
    invalidate();
  }
  
  private void a(int paramInt)
  {
    RecordHistogram.recordEnumeratedHistogram("Touchscreen.TapDisambiguation", paramInt, 6);
  }
  
  private void a(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = true;
    this.jdField_b_of_type_Boolean = paramBoolean;
    this.jdField_b_of_type_Long = 0L;
    if (paramBoolean)
    {
      setVisibility(0);
      this.jdField_c_of_type_Boolean = true;
      OnVisibilityChangedListener localOnVisibilityChangedListener = this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer$OnVisibilityChangedListener;
      if (localOnVisibilityChangedListener != null) {
        localOnVisibilityChangedListener.onPopupZoomerShown(this);
      }
    }
    else
    {
      this.jdField_b_of_type_Long = (this.jdField_a_of_type_Long + 300L - SystemClock.uptimeMillis());
      if (this.jdField_b_of_type_Long < 0L) {
        this.jdField_b_of_type_Long = 0L;
      }
    }
    this.jdField_a_of_type_Long = SystemClock.uptimeMillis();
    invalidate();
  }
  
  private boolean a(float paramFloat1, float paramFloat2)
  {
    return this.jdField_b_of_type_AndroidGraphicsRectF.contains(paramFloat1, paramFloat2) ^ true;
  }
  
  private void b()
  {
    if (this.jdField_b_of_type_AndroidGraphicsRect != null)
    {
      if (this.jdField_a_of_type_AndroidGraphicsPointF == null) {
        return;
      }
      this.d = (this.jdField_a_of_type_AndroidGraphicsBitmap.getWidth() / this.jdField_b_of_type_AndroidGraphicsRect.width());
      float f1 = this.jdField_a_of_type_AndroidGraphicsPointF.x - this.d * (this.jdField_a_of_type_AndroidGraphicsPointF.x - this.jdField_b_of_type_AndroidGraphicsRect.left);
      float f2 = this.jdField_a_of_type_AndroidGraphicsPointF.y - this.d * (this.jdField_a_of_type_AndroidGraphicsPointF.y - this.jdField_b_of_type_AndroidGraphicsRect.top);
      this.jdField_b_of_type_AndroidGraphicsRectF = new RectF(f1, f2, this.jdField_a_of_type_AndroidGraphicsBitmap.getWidth() + f1, this.jdField_a_of_type_AndroidGraphicsBitmap.getHeight() + f2);
      int i1 = getWidth();
      int i2 = getHeight();
      f1 = i1 - 25;
      f2 = i2 - 25;
      this.jdField_a_of_type_AndroidGraphicsRectF = new RectF(25.0F, 25.0F, f1, f2);
      this.jdField_b_of_type_Float = 0.0F;
      this.jdField_c_of_type_Float = 0.0F;
      RectF localRectF;
      if (this.jdField_b_of_type_AndroidGraphicsRectF.left < 25.0F)
      {
        this.jdField_b_of_type_Float = (25.0F - this.jdField_b_of_type_AndroidGraphicsRectF.left);
        localRectF = this.jdField_b_of_type_AndroidGraphicsRectF;
        localRectF.left += this.jdField_b_of_type_Float;
        localRectF = this.jdField_b_of_type_AndroidGraphicsRectF;
        localRectF.right += this.jdField_b_of_type_Float;
      }
      else if (this.jdField_b_of_type_AndroidGraphicsRectF.right > f1)
      {
        this.jdField_b_of_type_Float = (f1 - this.jdField_b_of_type_AndroidGraphicsRectF.right);
        localRectF = this.jdField_b_of_type_AndroidGraphicsRectF;
        localRectF.right += this.jdField_b_of_type_Float;
        localRectF = this.jdField_b_of_type_AndroidGraphicsRectF;
        localRectF.left += this.jdField_b_of_type_Float;
      }
      if (this.jdField_b_of_type_AndroidGraphicsRectF.top < 25.0F)
      {
        this.jdField_c_of_type_Float = (25.0F - this.jdField_b_of_type_AndroidGraphicsRectF.top);
        localRectF = this.jdField_b_of_type_AndroidGraphicsRectF;
        localRectF.top += this.jdField_c_of_type_Float;
        localRectF = this.jdField_b_of_type_AndroidGraphicsRectF;
        localRectF.bottom += this.jdField_c_of_type_Float;
      }
      else if (this.jdField_b_of_type_AndroidGraphicsRectF.bottom > f2)
      {
        this.jdField_c_of_type_Float = (f2 - this.jdField_b_of_type_AndroidGraphicsRectF.bottom);
        localRectF = this.jdField_b_of_type_AndroidGraphicsRectF;
        localRectF.bottom += this.jdField_c_of_type_Float;
        localRectF = this.jdField_b_of_type_AndroidGraphicsRectF;
        localRectF.top += this.jdField_c_of_type_Float;
      }
      this.n = 0.0F;
      this.m = 0.0F;
      this.l = 0.0F;
      this.k = 0.0F;
      if (this.jdField_a_of_type_AndroidGraphicsRectF.right + this.jdField_b_of_type_Float < this.jdField_b_of_type_AndroidGraphicsRectF.right) {
        this.k = (this.jdField_a_of_type_AndroidGraphicsRectF.right - this.jdField_b_of_type_AndroidGraphicsRectF.right);
      }
      if (this.jdField_a_of_type_AndroidGraphicsRectF.left + this.jdField_b_of_type_Float > this.jdField_b_of_type_AndroidGraphicsRectF.left) {
        this.l = (this.jdField_a_of_type_AndroidGraphicsRectF.left - this.jdField_b_of_type_AndroidGraphicsRectF.left);
      }
      if (this.jdField_a_of_type_AndroidGraphicsRectF.top + this.jdField_c_of_type_Float > this.jdField_b_of_type_AndroidGraphicsRectF.top) {
        this.n = (this.jdField_a_of_type_AndroidGraphicsRectF.top - this.jdField_b_of_type_AndroidGraphicsRectF.top);
      }
      if (this.jdField_a_of_type_AndroidGraphicsRectF.bottom + this.jdField_c_of_type_Float < this.jdField_b_of_type_AndroidGraphicsRectF.bottom) {
        this.m = (this.jdField_a_of_type_AndroidGraphicsRectF.bottom - this.jdField_b_of_type_AndroidGraphicsRectF.bottom);
      }
      this.jdField_b_of_type_AndroidGraphicsRectF.intersect(this.jdField_a_of_type_AndroidGraphicsRectF);
      this.e = (this.jdField_a_of_type_AndroidGraphicsPointF.x - this.jdField_b_of_type_AndroidGraphicsRectF.left);
      this.g = (this.jdField_b_of_type_AndroidGraphicsRectF.right - this.jdField_a_of_type_AndroidGraphicsPointF.x);
      this.f = (this.jdField_a_of_type_AndroidGraphicsPointF.y - this.jdField_b_of_type_AndroidGraphicsRectF.top);
      this.h = (this.jdField_b_of_type_AndroidGraphicsRectF.bottom - this.jdField_a_of_type_AndroidGraphicsPointF.y);
      f1 = (this.jdField_a_of_type_AndroidGraphicsPointF.x - this.jdField_b_of_type_AndroidGraphicsRect.centerX()) / (this.jdField_b_of_type_AndroidGraphicsRect.width() / 2.0F);
      f2 = (this.jdField_a_of_type_AndroidGraphicsPointF.y - this.jdField_b_of_type_AndroidGraphicsRect.centerY()) / (this.jdField_b_of_type_AndroidGraphicsRect.height() / 2.0F);
      float f3 = this.l;
      float f4 = this.k;
      float f5 = this.n;
      float f6 = this.m;
      this.i = ((f3 - f4) * (f1 + 0.5F) * -1.0F);
      this.j = ((f5 - f6) * (f2 + 0.5F) * -1.0F);
      this.i = a(this.i, f4, f3);
      this.j = a(this.j, this.m, this.n);
      this.jdField_c_of_type_AndroidGraphicsRectF = new RectF();
      return;
    }
  }
  
  private void b(Rect paramRect)
  {
    this.jdField_b_of_type_AndroidGraphicsRect = paramRect;
  }
  
  private void c()
  {
    if (!this.jdField_b_of_type_Boolean) {
      return;
    }
    a(false);
  }
  
  private void d()
  {
    if (!this.jdField_b_of_type_Boolean) {
      return;
    }
    a(2);
    a(false);
  }
  
  @VisibleForTesting
  void a(Bitmap paramBitmap)
  {
    Object localObject = this.jdField_a_of_type_AndroidGraphicsBitmap;
    if (localObject != null)
    {
      ((Bitmap)localObject).recycle();
      this.jdField_a_of_type_AndroidGraphicsBitmap = null;
    }
    this.jdField_a_of_type_AndroidGraphicsBitmap = paramBitmap;
    paramBitmap = new Canvas(this.jdField_a_of_type_AndroidGraphicsBitmap);
    localObject = new Path();
    RectF localRectF = new RectF(0.0F, 0.0F, paramBitmap.getWidth(), paramBitmap.getHeight());
    float f1 = a(getContext());
    ((Path)localObject).addRoundRect(localRectF, f1, f1, Path.Direction.CCW);
    if (Build.VERSION.SDK_INT >= 26) {
      paramBitmap.clipOutPath((Path)localObject);
    } else {
      paramBitmap.clipPath((Path)localObject, Region.Op.DIFFERENCE);
    }
    localObject = new Paint();
    ((Paint)localObject).setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    ((Paint)localObject).setColor(0);
    paramBitmap.drawPaint((Paint)localObject);
  }
  
  @VisibleForTesting
  void a(Rect paramRect)
  {
    if (!this.jdField_b_of_type_Boolean)
    {
      if (this.jdField_a_of_type_AndroidGraphicsBitmap == null) {
        return;
      }
      b(paramRect);
      a(true);
      return;
    }
  }
  
  protected boolean a()
  {
    return false;
  }
  
  public void backButtonPressed()
  {
    if (!this.jdField_b_of_type_Boolean) {
      return;
    }
    a(1);
    a(false);
  }
  
  public void hide(boolean paramBoolean)
  {
    if (!this.jdField_b_of_type_Boolean) {
      return;
    }
    a(0);
    if (paramBoolean)
    {
      a(false);
      return;
    }
    a();
  }
  
  public boolean isShowing()
  {
    return (this.jdField_b_of_type_Boolean) || (this.jdField_a_of_type_Boolean);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (isShowing())
    {
      if (this.jdField_a_of_type_AndroidGraphicsBitmap == null) {
        return;
      }
      if ((!a()) && ((getWidth() == 0) || (getHeight() == 0))) {
        return;
      }
      if (this.jdField_c_of_type_Boolean)
      {
        this.jdField_c_of_type_Boolean = false;
        b();
      }
      paramCanvas.save();
      float f1 = a((float)(SystemClock.uptimeMillis() - this.jdField_a_of_type_Long + this.jdField_b_of_type_Long) / 300.0F, 0.0F, 1.0F);
      if (f1 >= 1.0F)
      {
        this.jdField_a_of_type_Boolean = false;
        if (!isShowing()) {
          a();
        }
      }
      else
      {
        invalidate();
      }
      if (this.jdField_b_of_type_Boolean) {
        f1 = this.jdField_a_of_type_AndroidViewAnimationInterpolator.getInterpolation(f1);
      } else {
        f1 = this.jdField_b_of_type_AndroidViewAnimationInterpolator.getInterpolation(f1);
      }
      paramCanvas.drawARGB((int)(80.0F * f1), 0, 0, 0);
      paramCanvas.save();
      float f3 = this.d;
      float f2 = (f3 - 1.0F) * f1 / f3 + 1.0F / f3;
      float f5 = -this.jdField_b_of_type_Float;
      float f4 = 1.0F - f1;
      f5 = f5 * f4 / f3;
      f3 = -this.jdField_c_of_type_Float * f4 / f3;
      this.jdField_c_of_type_AndroidGraphicsRectF.left = (this.jdField_a_of_type_AndroidGraphicsPointF.x - this.e * f2 + f5);
      this.jdField_c_of_type_AndroidGraphicsRectF.top = (this.jdField_a_of_type_AndroidGraphicsPointF.y - this.f * f2 + f3);
      this.jdField_c_of_type_AndroidGraphicsRectF.right = (this.jdField_a_of_type_AndroidGraphicsPointF.x + this.g * f2 + f5);
      this.jdField_c_of_type_AndroidGraphicsRectF.bottom = (this.jdField_a_of_type_AndroidGraphicsPointF.y + this.h * f2 + f3);
      paramCanvas.clipRect(this.jdField_c_of_type_AndroidGraphicsRectF);
      paramCanvas.scale(f2, f2, this.jdField_c_of_type_AndroidGraphicsRectF.left, this.jdField_c_of_type_AndroidGraphicsRectF.top);
      paramCanvas.translate(this.i, this.j);
      paramCanvas.drawBitmap(this.jdField_a_of_type_AndroidGraphicsBitmap, this.jdField_c_of_type_AndroidGraphicsRectF.left, this.jdField_c_of_type_AndroidGraphicsRectF.top, null);
      paramCanvas.restore();
      Drawable localDrawable = a(getContext());
      localDrawable.setBounds((int)this.jdField_c_of_type_AndroidGraphicsRectF.left - jdField_a_of_type_AndroidGraphicsRect.left, (int)this.jdField_c_of_type_AndroidGraphicsRectF.top - jdField_a_of_type_AndroidGraphicsRect.top, (int)this.jdField_c_of_type_AndroidGraphicsRectF.right + jdField_a_of_type_AndroidGraphicsRect.right, (int)this.jdField_c_of_type_AndroidGraphicsRectF.bottom + jdField_a_of_type_AndroidGraphicsRect.bottom);
      localDrawable.setAlpha(a((int)(f1 * 255.0F), 0, 255));
      localDrawable.draw(paramCanvas);
      paramCanvas.restore();
      return;
    }
  }
  
  @SuppressLint({"ClickableViewAccessibility"})
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.jdField_a_of_type_AndroidViewGestureDetector.onTouchEvent(paramMotionEvent);
    return true;
  }
  
  public void setLastTouch(float paramFloat1, float paramFloat2)
  {
    PointF localPointF = this.jdField_a_of_type_AndroidGraphicsPointF;
    localPointF.x = paramFloat1;
    localPointF.y = paramFloat2;
  }
  
  public static abstract interface OnTapListener
  {
    public abstract void onResolveTapDisambiguation(long paramLong, float paramFloat1, float paramFloat2, boolean paramBoolean);
  }
  
  public static abstract interface OnVisibilityChangedListener
  {
    public abstract void onPopupZoomerHidden(PopupZoomer paramPopupZoomer);
    
    public abstract void onPopupZoomerShown(PopupZoomer paramPopupZoomer);
  }
  
  private static class ReverseInterpolator
    implements Interpolator
  {
    private final Interpolator a;
    
    public ReverseInterpolator(Interpolator paramInterpolator)
    {
      this.a = paramInterpolator;
    }
    
    public float getInterpolation(float paramFloat)
    {
      paramFloat = 1.0F - paramFloat;
      Interpolator localInterpolator = this.a;
      if (localInterpolator == null) {
        return paramFloat;
      }
      return localInterpolator.getInterpolation(paramFloat);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\PopupZoomer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */