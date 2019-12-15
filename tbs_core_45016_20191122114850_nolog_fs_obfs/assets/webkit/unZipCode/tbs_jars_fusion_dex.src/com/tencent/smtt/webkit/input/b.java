package com.tencent.smtt.webkit.input;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout.LayoutParams;
import com.tencent.smtt.webkit.SmttResource;
import org.chromium.content.browser.PositionObserver;
import org.chromium.content.browser.PositionObserver.Listener;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.content.browser.RenderCoordinates.NormalizedPoint;

public class b
  extends View
{
  public static int a = -11756806;
  public static b a;
  public static int b = -13017978;
  public static b b;
  private static int jdField_c_of_type_Int = 10;
  public static b c;
  private static int jdField_d_of_type_Int = 2;
  private float jdField_a_of_type_Float;
  private long jdField_a_of_type_Long;
  private Paint jdField_a_of_type_AndroidGraphicsPaint;
  private final Rect jdField_a_of_type_AndroidGraphicsRect = new Rect();
  private Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  private final View jdField_a_of_type_AndroidViewView;
  private final CursorController jdField_a_of_type_ComTencentSmttWebkitInputCursorController;
  private b.a.a jdField_a_of_type_ComTencentSmttWebkitInputB$a$a = null;
  private String jdField_a_of_type_JavaLangString = "";
  private final PositionObserver.Listener jdField_a_of_type_OrgChromiumContentBrowserPositionObserver$Listener;
  private final PositionObserver jdField_a_of_type_OrgChromiumContentBrowserPositionObserver;
  private boolean jdField_a_of_type_Boolean;
  private float jdField_b_of_type_Float;
  private long jdField_b_of_type_Long;
  private boolean jdField_b_of_type_Boolean;
  private float jdField_c_of_type_Float;
  private boolean jdField_c_of_type_Boolean = true;
  private float jdField_d_of_type_Float;
  private boolean jdField_d_of_type_Boolean = false;
  private float jdField_e_of_type_Float;
  private int jdField_e_of_type_Int;
  private boolean jdField_e_of_type_Boolean = false;
  private float jdField_f_of_type_Float;
  private int jdField_f_of_type_Int;
  private float jdField_g_of_type_Float;
  private int jdField_g_of_type_Int;
  private float jdField_h_of_type_Float;
  private int jdField_h_of_type_Int;
  private int i;
  private int j;
  private final int k;
  private int l;
  private int m;
  
  static
  {
    jdField_a_of_type_ComTencentSmttWebkitInputB$b = new b(0);
    jdField_b_of_type_ComTencentSmttWebkitInputB$b = new b(1);
    jdField_c_of_type_ComTencentSmttWebkitInputB$b = new b(2);
  }
  
  b(CursorController paramCursorController, int paramInt, View paramView, PositionObserver paramPositionObserver)
  {
    super(paramView.getContext());
    this.jdField_e_of_type_Boolean = paramCursorController.isNightMode();
    this.m = paramInt;
    this.jdField_a_of_type_AndroidViewView = paramView;
    Context localContext = this.jdField_a_of_type_AndroidViewView.getContext();
    this.jdField_a_of_type_ComTencentSmttWebkitInputCursorController = paramCursorController;
    this.jdField_a_of_type_Float = paramView.getContext().getResources().getDisplayMetrics().density;
    a(paramInt);
    this.k = ((int)TypedValue.applyDimension(1, 5.0F, localContext.getResources().getDisplayMetrics()));
    this.jdField_h_of_type_Float = 1.0F;
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver$Listener = new PositionObserver.Listener()
    {
      public void onPositionChanged(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        b.a(b.this, paramAnonymousInt1, paramAnonymousInt2);
      }
    };
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver = paramPositionObserver;
    this.jdField_a_of_type_AndroidGraphicsPaint = new Paint();
    this.jdField_a_of_type_AndroidGraphicsPaint.setAntiAlias(true);
    a();
    this.jdField_a_of_type_AndroidGraphicsPaint.setStrokeWidth(this.jdField_a_of_type_Float * 5.0F / 3.0F);
    this.l = 0;
    ((ViewGroup)this.jdField_a_of_type_AndroidViewView).addView(this);
  }
  
  public static Drawable a(Bitmap paramBitmap)
  {
    Resources localResources = SmttResource.getResources();
    int n = localResources.getDisplayMetrics().densityDpi;
    if (paramBitmap != null)
    {
      paramBitmap.setDensity(n);
      return new BitmapDrawable(localResources, paramBitmap);
    }
    return null;
  }
  
  public static Drawable a(String paramString)
  {
    Resources localResources = SmttResource.getResources();
    int n = SmttResource.loadIdentifierResource(paramString, "drawable");
    paramString = new BitmapFactory.Options();
    paramString.inScaled = false;
    Drawable localDrawable = a(BitmapFactory.decodeResource(localResources, n, paramString));
    paramString = localDrawable;
    if (localDrawable == null) {
      paramString = localResources.getDrawable(n);
    }
    return paramString;
  }
  
  public static b a(int paramInt)
  {
    if (paramInt != 0)
    {
      if (paramInt != 2) {
        return jdField_b_of_type_ComTencentSmttWebkitInputB$b;
      }
      return jdField_c_of_type_ComTencentSmttWebkitInputB$b;
    }
    return jdField_a_of_type_ComTencentSmttWebkitInputB$b;
  }
  
  private void a(float paramFloat1, float paramFloat2)
  {
    float f1 = this.jdField_d_of_type_Float;
    float f2 = this.jdField_b_of_type_Float;
    float f3 = this.jdField_e_of_type_Float;
    float f4 = this.jdField_c_of_type_Float;
    float f5 = this.k;
    float f6 = this.jdField_f_of_type_Int;
    this.jdField_a_of_type_ComTencentSmttWebkitInputCursorController.updatePosition(this, Math.round(paramFloat1 - f1 + f2), Math.round(paramFloat2 - f3 + f4 - f5 - f6), Math.round(paramFloat1), Math.round(paramFloat2));
  }
  
  private void b(float paramFloat1, float paramFloat2)
  {
    float f1 = this.jdField_d_of_type_Float;
    float f2 = this.jdField_b_of_type_Float;
    float f3 = this.i;
    float f4 = this.jdField_e_of_type_Float;
    float f5 = this.jdField_c_of_type_Float;
    float f6 = this.k;
    float f7 = this.j;
    float f8 = this.jdField_f_of_type_Int;
    this.jdField_a_of_type_ComTencentSmttWebkitInputCursorController.updateHandleViewSliderPosition(paramFloat1 - f1 + f2 + f3, paramFloat2 - f4 + f5 - f6 + f7 - f8);
  }
  
  private void b(int paramInt1, int paramInt2)
  {
    this.jdField_d_of_type_Float += paramInt1 - this.i;
    this.jdField_e_of_type_Float += paramInt2 - this.j;
    this.i = paramInt1;
    this.j = paramInt2;
    e();
  }
  
  private boolean c()
  {
    return this.jdField_b_of_type_Boolean;
  }
  
  private void d()
  {
    if (this.jdField_a_of_type_ComTencentSmttWebkitInputB$a$a == null) {
      return;
    }
    int n = a(this.m).jdField_a_of_type_Int;
    float f1 = 0.0F;
    if (n != 0) {
      if (a(this.m).jdField_a_of_type_Int == 1) {
        f1 = this.jdField_b_of_type_Float;
      } else if (a(this.m).jdField_a_of_type_Int == 2) {
        f1 = this.jdField_b_of_type_Float * 2.0F;
      }
    }
    float f3 = this.jdField_a_of_type_ComTencentSmttWebkitInputB$a$a.d() + (this.jdField_a_of_type_AndroidViewView.getScrollX() - this.jdField_a_of_type_ComTencentSmttWebkitInputB$a$a.a()) - f1 - (int)(jdField_c_of_type_Int * this.jdField_a_of_type_Float);
    f1 = this.jdField_a_of_type_AndroidViewView.getScrollY();
    float f2 = this.jdField_a_of_type_ComTencentSmttWebkitInputB$a$a.e() - (this.jdField_a_of_type_ComTencentSmttWebkitInputB$a$a.b() - f1) - Math.round(this.jdField_c_of_type_Float) - this.l;
    f1 = f2;
    if (a(this.m).jdField_b_of_type_Int == 1) {
      f1 = f2 - this.jdField_f_of_type_Int;
    }
    if (Build.VERSION.SDK_INT < 11)
    {
      setLayoutParams(new AbsoluteLayout.LayoutParams(-2, -2, (int)f3, (int)f1));
    }
    else
    {
      setX(f3);
      setY(f1);
    }
    invalidate();
  }
  
  private void e()
  {
    if (getVisibility() != 0) {
      return;
    }
    d();
  }
  
  private void f()
  {
    if (this.jdField_h_of_type_Float == 1.0F) {
      return;
    }
    this.jdField_h_of_type_Float = Math.min(1.0F, (float)(AnimationUtils.currentAnimationTimeMillis() - this.jdField_b_of_type_Long) / 200.0F);
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setAlpha((int)(this.jdField_h_of_type_Float * 255.0F));
    invalidate();
  }
  
  public int a()
  {
    return this.jdField_e_of_type_Int;
  }
  
  Drawable a()
  {
    return this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  }
  
  void a()
  {
    if (this.jdField_e_of_type_Boolean)
    {
      this.jdField_a_of_type_AndroidGraphicsPaint.setColor(jdField_b_of_type_Int);
      return;
    }
    this.jdField_a_of_type_AndroidGraphicsPaint.setColor(jdField_a_of_type_Int);
  }
  
  void a(int paramInt)
  {
    Object localObject1 = jdField_a_of_type_ComTencentSmttWebkitInputB$b.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    Drawable localDrawable2 = null;
    if ((localObject1 != null) && (jdField_b_of_type_ComTencentSmttWebkitInputB$b.jdField_a_of_type_AndroidGraphicsDrawableDrawable != null) && (jdField_c_of_type_ComTencentSmttWebkitInputB$b.jdField_a_of_type_AndroidGraphicsDrawableDrawable != null))
    {
      localObject2 = null;
      localObject1 = localObject2;
      localObject3 = localObject1;
    }
    else
    {
      localObject2 = a("x5_text_select_holder_left");
      localObject1 = a("x5_text_select_holder_right");
      localObject3 = a("x5_text_select_holder_middle");
    }
    Drawable localDrawable3;
    Drawable localDrawable1;
    if ((jdField_a_of_type_ComTencentSmttWebkitInputB$b.jdField_b_of_type_AndroidGraphicsDrawableDrawable != null) && (jdField_b_of_type_ComTencentSmttWebkitInputB$b.jdField_b_of_type_AndroidGraphicsDrawableDrawable != null) && (jdField_c_of_type_ComTencentSmttWebkitInputB$b.jdField_b_of_type_AndroidGraphicsDrawableDrawable != null))
    {
      localDrawable3 = null;
      localDrawable1 = localDrawable3;
    }
    else
    {
      localDrawable2 = a("x5_text_select_holder_night_left");
      localDrawable3 = a("x5_text_select_holder_night_right");
      localDrawable1 = a("x5_text_select_holder_night_middle");
    }
    if (jdField_a_of_type_ComTencentSmttWebkitInputB$b.jdField_a_of_type_AndroidGraphicsDrawableDrawable == null) {
      jdField_a_of_type_ComTencentSmttWebkitInputB$b.jdField_a_of_type_AndroidGraphicsDrawableDrawable = ((Drawable)localObject2);
    }
    if (jdField_a_of_type_ComTencentSmttWebkitInputB$b.jdField_b_of_type_AndroidGraphicsDrawableDrawable == null) {
      jdField_a_of_type_ComTencentSmttWebkitInputB$b.jdField_b_of_type_AndroidGraphicsDrawableDrawable = localDrawable2;
    }
    if (jdField_b_of_type_ComTencentSmttWebkitInputB$b.jdField_a_of_type_AndroidGraphicsDrawableDrawable == null) {
      jdField_b_of_type_ComTencentSmttWebkitInputB$b.jdField_a_of_type_AndroidGraphicsDrawableDrawable = ((Drawable)localObject3);
    }
    if (jdField_b_of_type_ComTencentSmttWebkitInputB$b.jdField_b_of_type_AndroidGraphicsDrawableDrawable == null) {
      jdField_b_of_type_ComTencentSmttWebkitInputB$b.jdField_b_of_type_AndroidGraphicsDrawableDrawable = localDrawable1;
    }
    if (jdField_c_of_type_ComTencentSmttWebkitInputB$b.jdField_a_of_type_AndroidGraphicsDrawableDrawable == null) {
      jdField_c_of_type_ComTencentSmttWebkitInputB$b.jdField_a_of_type_AndroidGraphicsDrawableDrawable = ((Drawable)localObject1);
    }
    if (jdField_c_of_type_ComTencentSmttWebkitInputB$b.jdField_b_of_type_AndroidGraphicsDrawableDrawable == null) {
      jdField_c_of_type_ComTencentSmttWebkitInputB$b.jdField_b_of_type_AndroidGraphicsDrawableDrawable = localDrawable3;
    }
    localObject1 = jdField_a_of_type_ComTencentSmttWebkitInputB$b;
    ((b)localObject1).jdField_a_of_type_Int = 2;
    Object localObject2 = jdField_b_of_type_ComTencentSmttWebkitInputB$b;
    boolean bool = true;
    ((b)localObject2).jdField_a_of_type_Int = 1;
    Object localObject3 = jdField_c_of_type_ComTencentSmttWebkitInputB$b;
    ((b)localObject3).jdField_a_of_type_Int = 0;
    if (paramInt != 0)
    {
      if (paramInt != 2)
      {
        if (this.jdField_e_of_type_Boolean) {
          this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = ((b)localObject2).jdField_b_of_type_AndroidGraphicsDrawableDrawable;
        } else {
          this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = ((b)localObject2).jdField_a_of_type_AndroidGraphicsDrawableDrawable;
        }
      }
      else if (this.jdField_e_of_type_Boolean) {
        this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = ((b)localObject3).jdField_b_of_type_AndroidGraphicsDrawableDrawable;
      } else {
        this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = ((b)localObject3).jdField_a_of_type_AndroidGraphicsDrawableDrawable;
      }
    }
    else if (this.jdField_e_of_type_Boolean) {
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = ((b)localObject1).jdField_b_of_type_AndroidGraphicsDrawableDrawable;
    } else {
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = ((b)localObject1).jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    }
    if (paramInt != 1) {
      bool = false;
    }
    this.jdField_d_of_type_Boolean = bool;
    double d1 = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth();
    Double.isNaN(d1);
    d1 /= 3.0D;
    double d2 = this.jdField_a_of_type_Float;
    Double.isNaN(d2);
    this.jdField_e_of_type_Int = ((int)(d1 * d2));
    d1 = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight();
    Double.isNaN(d1);
    d1 /= 3.0D;
    d2 = this.jdField_a_of_type_Float;
    Double.isNaN(d2);
    this.jdField_f_of_type_Int = ((int)(d1 * d2));
    this.jdField_b_of_type_Float = (this.jdField_e_of_type_Int / 2.0F);
    this.jdField_c_of_type_Float = 0.0F;
    invalidate();
  }
  
  void a(int paramInt1, int paramInt2)
  {
    this.jdField_g_of_type_Int = paramInt1;
    this.jdField_h_of_type_Int = paramInt2;
    if (c())
    {
      if (a())
      {
        e();
        return;
      }
      b();
      return;
    }
    c();
  }
  
  void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    a(paramInt1 - Math.round(this.jdField_b_of_type_Float), paramInt2 - Math.round(this.jdField_c_of_type_Float));
    b(paramInt2 - paramInt4);
  }
  
  void a(b.a.a parama1, b.a.a parama2)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitInputB$a$a = parama1;
    a((int)parama1.getXPix(), (int)parama1.getYPix(), (int)parama2.getXPix(), (int)parama2.getYPix());
  }
  
  void a(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramString);
    this.jdField_a_of_type_JavaLangString = localStringBuilder.toString();
  }
  
  public void a(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  boolean a()
  {
    return getVisibility() == 0;
  }
  
  public int b()
  {
    return this.jdField_f_of_type_Int;
  }
  
  void b()
  {
    b(this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver.getPositionX(), this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver.getPositionY());
    if (!c())
    {
      c();
      return;
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver.addListener(this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver$Listener);
    setVisibility(0);
    setEnabled(true);
    d();
  }
  
  public void b(int paramInt)
  {
    if (this.l == paramInt) {
      return;
    }
    this.l = paramInt;
    requestLayout();
    e();
  }
  
  void b(boolean paramBoolean)
  {
    this.jdField_c_of_type_Boolean = paramBoolean;
  }
  
  boolean b()
  {
    return this.jdField_b_of_type_Boolean;
  }
  
  public int c()
  {
    return a(this.m).jdField_a_of_type_Int;
  }
  
  void c()
  {
    this.jdField_b_of_type_Boolean = false;
    setVisibility(8);
    setEnabled(false);
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver.removeListener(this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver$Listener);
  }
  
  public int d()
  {
    return this.jdField_a_of_type_AndroidGraphicsPaint.getColor();
  }
  
  public int e()
  {
    return (int)this.jdField_a_of_type_AndroidGraphicsPaint.getStrokeWidth();
  }
  
  public int f()
  {
    return this.l;
  }
  
  int g()
  {
    return this.jdField_g_of_type_Int + Math.round(this.jdField_b_of_type_Float);
  }
  
  int h()
  {
    return (int)(this.jdField_h_of_type_Int + this.jdField_c_of_type_Float - this.k);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (!this.jdField_c_of_type_Boolean) {
      return;
    }
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    if (this.l == 0) {
      return;
    }
    if (this.jdField_e_of_type_Boolean != this.jdField_a_of_type_ComTencentSmttWebkitInputCursorController.isNightMode())
    {
      this.jdField_e_of_type_Boolean = this.jdField_a_of_type_ComTencentSmttWebkitInputCursorController.isNightMode();
      a(this.m);
      a();
    }
    f();
    if ((this.l != 0) && (!(this.jdField_a_of_type_ComTencentSmttWebkitInputCursorController instanceof InsertionHandleController)) && (a(this.m).jdField_a_of_type_Boolean == true))
    {
      n = 0;
      i1 = (int)(jdField_c_of_type_Int * this.jdField_a_of_type_Float);
      if (a(this.m).jdField_b_of_type_Int == 1) {
        n = 0 + this.jdField_f_of_type_Int;
      }
      i2 = (int)(this.jdField_a_of_type_Float * 1.0F);
      if (a(this.m).jdField_a_of_type_Int == 0)
      {
        f1 = i1 + i2;
        paramCanvas.drawLine(f1, n, f1, n + this.l, this.jdField_a_of_type_AndroidGraphicsPaint);
      }
      else if (a(this.m).jdField_a_of_type_Int == 1)
      {
        f1 = i1;
        f2 = this.jdField_b_of_type_Float;
        paramCanvas.drawLine(f1 + f2, n, f1 + f2, n + this.l, this.jdField_a_of_type_AndroidGraphicsPaint);
      }
      else if (a(this.m).jdField_a_of_type_Int == 2)
      {
        f1 = i1;
        f2 = this.jdField_b_of_type_Float;
        float f3 = i2;
        paramCanvas.drawLine(f2 * 2.0F + f1 + f3, n, f1 + f2 * 2.0F + f3, n + this.l, this.jdField_a_of_type_AndroidGraphicsPaint);
      }
    }
    int n = this.l;
    float f1 = jdField_c_of_type_Int;
    float f2 = this.jdField_a_of_type_Float;
    int i1 = (int)(f1 * f2);
    int i2 = (int)(f2 * 1.0F);
    if (a(this.m).jdField_a_of_type_Int == 2)
    {
      i1 += i2 * 2;
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setBounds(i1, n, this.jdField_e_of_type_Int + i1, this.jdField_f_of_type_Int + n);
    }
    else if (a(this.m).jdField_a_of_type_Int == 1)
    {
      i1 += i2;
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setBounds(i1, n, this.jdField_e_of_type_Int + i1, this.jdField_f_of_type_Int + n);
    }
    else
    {
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setBounds(i1, n, this.jdField_e_of_type_Int + i1, this.jdField_f_of_type_Int + n);
    }
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.draw(paramCanvas);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    paramInt1 = jdField_c_of_type_Int;
    float f1 = paramInt1;
    float f2 = this.jdField_a_of_type_Float;
    setMeasuredDimension((int)(f1 * f2 + this.jdField_e_of_type_Int + paramInt1 * f2), (int)(this.jdField_f_of_type_Int + this.l + paramInt1 * f2));
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getActionMasked())
    {
    default: 
      return false;
    case 2: 
      float f1 = paramMotionEvent.getRawY();
      float f2 = paramMotionEvent.getRawX();
      if (Math.abs(f1 - this.jdField_g_of_type_Float) >= jdField_d_of_type_Int * this.jdField_a_of_type_Float)
      {
        if (Math.abs(f2 - this.jdField_f_of_type_Float) < jdField_d_of_type_Int * this.jdField_a_of_type_Float) {
          return true;
        }
        if (Build.VERSION.SDK_INT < 11) {
          b(paramMotionEvent.getX() + getLeft(), paramMotionEvent.getY() + getTop());
        } else {
          b(paramMotionEvent.getX() + getX(), paramMotionEvent.getY() + getY() - this.jdField_a_of_type_ComTencentSmttWebkitInputB$a$a.c());
        }
        a(paramMotionEvent.getRawX(), paramMotionEvent.getRawY());
        return true;
      }
      break;
    case 1: 
    case 3: 
      this.jdField_b_of_type_Boolean = false;
      this.jdField_a_of_type_ComTencentSmttWebkitInputCursorController.afterEndUpdatingPosition(this);
      return true;
    case 0: 
      getParent().requestDisallowInterceptTouchEvent(true);
      this.jdField_f_of_type_Float = paramMotionEvent.getRawX();
      this.jdField_g_of_type_Float = paramMotionEvent.getRawY();
      this.jdField_d_of_type_Float = (this.jdField_f_of_type_Float - this.jdField_g_of_type_Int);
      this.jdField_e_of_type_Float = (this.jdField_g_of_type_Float - this.jdField_h_of_type_Int);
      this.jdField_b_of_type_Boolean = true;
      this.jdField_a_of_type_ComTencentSmttWebkitInputCursorController.beforeStartUpdatingPosition(this);
      this.jdField_a_of_type_Long = SystemClock.uptimeMillis();
    }
    return true;
  }
  
  public static class a
    extends RenderCoordinates
  {
    public a a()
    {
      return new a(null);
    }
    
    public void a(RenderCoordinates paramRenderCoordinates)
    {
      this.mScrollXCss = paramRenderCoordinates.getScrollX();
      this.mScrollYCss = paramRenderCoordinates.getScrollY();
      this.mContentWidthCss = paramRenderCoordinates.getContentWidthCss();
      this.mContentHeightCss = paramRenderCoordinates.getContentHeightCss();
      this.mLastFrameViewportWidthCss = paramRenderCoordinates.getLastFrameViewportWidthCss();
      this.mLastFrameViewportHeightCss = paramRenderCoordinates.getLastFrameViewportHeightCss();
      this.mPageScaleFactor = paramRenderCoordinates.getPageScaleFactor();
      this.mMinPageScaleFactor = paramRenderCoordinates.getMinPageScaleFactor();
      this.mMaxPageScaleFactor = paramRenderCoordinates.getMaxPageScaleFactor();
      this.mDeviceScaleFactor = paramRenderCoordinates.getDeviceScaleFactor();
      this.mTopContentOffsetYPix = paramRenderCoordinates.getContentOffsetYPix();
    }
    
    public class a
      extends RenderCoordinates.NormalizedPoint
    {
      private a()
      {
        super();
      }
      
      float a()
      {
        return b.a.this.getScrollX() * b.a.this.getPageScaleFactor() * b.a.this.getDeviceScaleFactor();
      }
      
      float b()
      {
        return b.a.this.getScrollY() * b.a.this.getPageScaleFactor() * b.a.this.getDeviceScaleFactor();
      }
      
      public float c()
      {
        return b.a.this.getContentOffsetYPix();
      }
      
      public float d()
      {
        return getXAbsoluteCss() * b.a.this.getPageScaleFactor() * b.a.this.getDeviceScaleFactor();
      }
      
      public float e()
      {
        return getYAbsoluteCss() * b.a.this.getPageScaleFactor() * b.a.this.getDeviceScaleFactor() + b.a.this.getContentOffsetYPix();
      }
      
      public void setAbsoluteCss(float paramFloat1, float paramFloat2)
      {
        float f1 = paramFloat1;
        if (paramFloat1 < 0.0F) {
          f1 = 0.0F;
        }
        float f2 = b.a.this.getContentWidthCss();
        paramFloat1 = f1;
        if (f1 > f2) {
          paramFloat1 = f2;
        }
        super.setAbsoluteCss(paramFloat1, paramFloat2);
      }
    }
  }
  
  public static class b
  {
    public int a;
    public Drawable a;
    public boolean a;
    public int b;
    public Drawable b;
    
    public b(int paramInt)
    {
      if ((paramInt != 0) && (paramInt != 2))
      {
        if (paramInt == 1)
        {
          this.jdField_a_of_type_Int = 1;
          this.jdField_a_of_type_Boolean = false;
          this.b = 0;
        }
      }
      else
      {
        this.jdField_a_of_type_Int = 1;
        this.jdField_a_of_type_Boolean = true;
        this.b = 0;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\input\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */