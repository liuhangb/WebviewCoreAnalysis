package org.chromium.android_webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.BadTokenException;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.chromium.base.ObserverList;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.PositionObserver;
import org.chromium.content.browser.PositionObserver.Listener;
import org.chromium.content.browser.ViewPositionObserver;
import org.chromium.content.browser.selection.HandleViewResources;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.content_public.browser.GestureListenerManager;
import org.chromium.content_public.browser.GestureStateListener;
import org.chromium.content_public.browser.WebContents;
import org.chromium.ui.base.EventForwarder;
import org.chromium.ui.base.WindowAndroid;
import org.chromium.ui.display.DisplayAndroid;
import org.chromium.ui.display.DisplayAndroid.DisplayAndroidObserver;

@JNINamespace("android_webview")
public class PopupTouchHandleDrawable
  extends View
  implements DisplayAndroid.DisplayAndroidObserver
{
  private float jdField_a_of_type_Float;
  private int jdField_a_of_type_Int;
  private final long jdField_a_of_type_Long;
  private Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  private final PopupWindow jdField_a_of_type_AndroidWidgetPopupWindow;
  private Runnable jdField_a_of_type_JavaLangRunnable;
  private final ObserverList<PopupTouchHandleDrawable> jdField_a_of_type_OrgChromiumBaseObserverList;
  private final PositionObserver.Listener jdField_a_of_type_OrgChromiumContentBrowserPositionObserver$Listener;
  private PositionObserver jdField_a_of_type_OrgChromiumContentBrowserPositionObserver;
  private ContentViewCore jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore;
  private final GestureStateListener jdField_a_of_type_OrgChromiumContent_publicBrowserGestureStateListener;
  private WebContents jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents;
  private final int[] jdField_a_of_type_ArrayOfInt = new int[2];
  private float jdField_b_of_type_Float;
  private int jdField_b_of_type_Int;
  private long jdField_b_of_type_Long;
  private Runnable jdField_b_of_type_JavaLangRunnable;
  private boolean jdField_b_of_type_Boolean;
  private float jdField_c_of_type_Float;
  private int jdField_c_of_type_Int = 3;
  private long jdField_c_of_type_Long;
  private Runnable jdField_c_of_type_JavaLangRunnable;
  private boolean jdField_c_of_type_Boolean;
  private float jdField_d_of_type_Float;
  private boolean jdField_d_of_type_Boolean;
  private boolean e;
  private boolean f;
  private boolean g;
  private boolean h;
  private boolean i;
  private boolean j;
  private boolean k;
  private boolean l;
  private boolean m;
  
  private PopupTouchHandleDrawable(ObserverList<PopupTouchHandleDrawable> paramObserverList, ContentViewCore paramContentViewCore)
  {
    super(paramContentViewCore.getContainerView().getContext());
    this.jdField_a_of_type_OrgChromiumBaseObserverList = paramObserverList;
    this.jdField_a_of_type_OrgChromiumBaseObserverList.addObserver(this);
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore = paramContentViewCore;
    paramObserverList = this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getWindowAndroid();
    this.jdField_d_of_type_Float = paramObserverList.getDisplay().getDipScale();
    this.jdField_a_of_type_AndroidWidgetPopupWindow = new PopupWindow((Context)paramObserverList.getContext().get(), null, 16843464);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setSplitTouchEnabled(true);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setClippingEnabled(false);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setAnimationStyle(0);
    a(this.jdField_a_of_type_AndroidWidgetPopupWindow, 1002);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setWidth(-2);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setHeight(-2);
    this.jdField_c_of_type_Float = 0.0F;
    this.jdField_d_of_type_Boolean = false;
    setVisibility(4);
    this.f = this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getContainerView().hasWindowFocus();
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver = new ViewPositionObserver(this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getContainerView());
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver$Listener = new PositionObserver.Listener()
    {
      public void onPositionChanged(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        PopupTouchHandleDrawable.a(PopupTouchHandleDrawable.this, paramAnonymousInt1, paramAnonymousInt2);
      }
    };
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserGestureStateListener = new GestureStateListener()
    {
      public void onDestroyed()
      {
        PopupTouchHandleDrawable.b(PopupTouchHandleDrawable.this);
      }
      
      public void onFlingStartGesture(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
      {
        PopupTouchHandleDrawable.a(PopupTouchHandleDrawable.this, false);
      }
      
      public void onScrollEnded(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        PopupTouchHandleDrawable.a(PopupTouchHandleDrawable.this, false);
      }
      
      public void onScrollOffsetOrExtentChanged(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        PopupTouchHandleDrawable.a(PopupTouchHandleDrawable.this);
      }
      
      public void onScrollStarted(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        PopupTouchHandleDrawable.a(PopupTouchHandleDrawable.this, true);
      }
      
      public void onWindowFocusChanged(boolean paramAnonymousBoolean)
      {
        PopupTouchHandleDrawable.b(PopupTouchHandleDrawable.this, paramAnonymousBoolean);
      }
    };
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents = this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getWebContents();
    GestureListenerManager.fromWebContents(this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents).addListener(this.jdField_a_of_type_OrgChromiumContent_publicBrowserGestureStateListener);
    this.jdField_a_of_type_Long = nativeInit(HandleViewResources.getHandleHorizontalPaddingRatio());
  }
  
  private int a()
  {
    return this.jdField_a_of_type_Int + (int)(this.jdField_a_of_type_Float * this.jdField_d_of_type_Float);
  }
  
  private static Drawable a(Context paramContext, int paramInt)
  {
    switch (paramInt)
    {
    default: 
      if (jdField_a_of_type_Boolean) {
        return HandleViewResources.getCenterHandleDrawable(paramContext);
      }
      break;
    case 2: 
      return HandleViewResources.getRightHandleDrawable(paramContext);
    case 1: 
      return HandleViewResources.getCenterHandleDrawable(paramContext);
    case 0: 
      return HandleViewResources.getLeftHandleDrawable(paramContext);
    }
    throw new AssertionError();
  }
  
  @SuppressLint({"NewApi"})
  private void a()
  {
    this.m = false;
    if (this.jdField_a_of_type_AndroidGraphicsDrawableDrawable == null) {
      return;
    }
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = a(getContext(), this.jdField_c_of_type_Int);
    Drawable localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      localDrawable.setAlpha((int)(this.jdField_c_of_type_Float * 255.0F));
    }
    if ((Build.VERSION.SDK_INT >= 18) && (!isInLayout())) {
      requestLayout();
    }
  }
  
  private void a(int paramInt1, int paramInt2)
  {
    if ((this.jdField_a_of_type_Int == paramInt1) && (this.jdField_b_of_type_Int == paramInt2)) {
      return;
    }
    this.jdField_a_of_type_Int = paramInt1;
    this.jdField_b_of_type_Int = paramInt2;
    f();
  }
  
  private static void a(PopupWindow paramPopupWindow, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      paramPopupWindow.setWindowLayoutType(paramInt);
      return;
    }
    try
    {
      PopupWindow.class.getMethod("setWindowLayoutType", new Class[] { Integer.TYPE }).invoke(paramPopupWindow, new Object[] { Integer.valueOf(paramInt) });
      return;
    }
    catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException|RuntimeException paramPopupWindow) {}
  }
  
  private void a(boolean paramBoolean)
  {
    if (this.e == paramBoolean) {
      return;
    }
    this.e = paramBoolean;
    d();
  }
  
  private boolean a()
  {
    if ((this.h) && (this.jdField_d_of_type_Boolean) && (this.f) && (!this.e) && (!this.g))
    {
      float f1 = this.jdField_a_of_type_Float;
      float f2 = this.jdField_d_of_type_Float;
      if (a(f1 * f2, this.jdField_b_of_type_Float * f2)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean a(float paramFloat1, float paramFloat2)
  {
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = paramFloat1;
    arrayOfFloat[1] = paramFloat2;
    Object localObject = this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getContainerView();
    while (localObject != null)
    {
      if (localObject != this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getContainerView())
      {
        arrayOfFloat[0] -= ((View)localObject).getScrollX();
        arrayOfFloat[1] -= ((View)localObject).getScrollY();
      }
      paramFloat1 = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth();
      paramFloat2 = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight();
      if ((arrayOfFloat[0] + paramFloat1 >= 0.0F) && (arrayOfFloat[1] + paramFloat2 >= 0.0F) && (arrayOfFloat[0] <= ((View)localObject).getWidth()))
      {
        if (arrayOfFloat[1] > ((View)localObject).getHeight()) {
          return false;
        }
        if (!((View)localObject).getMatrix().isIdentity()) {
          ((View)localObject).getMatrix().mapPoints(arrayOfFloat);
        }
        arrayOfFloat[0] += ((View)localObject).getLeft();
        arrayOfFloat[1] += ((View)localObject).getTop();
        localObject = ((View)localObject).getParent();
        if ((localObject instanceof View)) {
          localObject = (View)localObject;
        } else {
          localObject = null;
        }
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  private int b()
  {
    return this.jdField_b_of_type_Int + (int)(this.jdField_b_of_type_Float * this.jdField_d_of_type_Float);
  }
  
  private void b()
  {
    this.jdField_a_of_type_AndroidWidgetPopupWindow.update(a(), b(), getRight() - getLeft(), getBottom() - getTop());
  }
  
  private void b(boolean paramBoolean)
  {
    if (this.f == paramBoolean) {
      return;
    }
    this.f = paramBoolean;
    d();
  }
  
  private void c()
  {
    int n;
    if (a()) {
      n = 0;
    } else {
      n = 4;
    }
    if ((n == 0) && (getVisibility() != 0) && (!this.k))
    {
      this.k = true;
      h();
      return;
    }
    this.k = false;
    setVisibility(4);
  }
  
  private void c(boolean paramBoolean)
  {
    if (this.g == paramBoolean) {
      return;
    }
    this.g = paramBoolean;
    if (this.g)
    {
      if (this.jdField_b_of_type_JavaLangRunnable == null) {
        this.jdField_b_of_type_JavaLangRunnable = new Runnable()
        {
          public void run()
          {
            PopupTouchHandleDrawable.c(PopupTouchHandleDrawable.this, false);
          }
        };
      }
      removeCallbacks(this.jdField_b_of_type_JavaLangRunnable);
      long l1 = SystemClock.uptimeMillis();
      l1 = Math.max(0L, this.jdField_c_of_type_Long - l1);
      postDelayed(this.jdField_b_of_type_JavaLangRunnable, l1);
    }
    else
    {
      Runnable localRunnable = this.jdField_b_of_type_JavaLangRunnable;
      if (localRunnable != null) {
        removeCallbacks(localRunnable);
      }
    }
    d();
  }
  
  public static PopupTouchHandleDrawable create(ObserverList<PopupTouchHandleDrawable> paramObserverList, ContentViewCore paramContentViewCore)
  {
    return new PopupTouchHandleDrawable(paramObserverList, paramContentViewCore);
  }
  
  private void d()
  {
    if (!this.jdField_a_of_type_AndroidWidgetPopupWindow.isShowing()) {
      return;
    }
    boolean bool = a();
    if (this.i == bool) {
      return;
    }
    this.i = bool;
    i();
    if (bool)
    {
      if (this.jdField_a_of_type_JavaLangRunnable == null) {
        this.jdField_a_of_type_JavaLangRunnable = new Runnable()
        {
          public void run()
          {
            PopupTouchHandleDrawable.c(PopupTouchHandleDrawable.this);
          }
        };
      }
      postOnAnimation(this.jdField_a_of_type_JavaLangRunnable);
      return;
    }
    c();
  }
  
  @CalledByNative
  private void destroy()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList.removeObserver(this);
    if (this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents == null) {
      return;
    }
    hide();
    GestureListenerManager localGestureListenerManager = GestureListenerManager.fromWebContents(this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents);
    if (localGestureListenerManager != null) {
      localGestureListenerManager.removeListener(this.jdField_a_of_type_OrgChromiumContent_publicBrowserGestureStateListener);
    }
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore = null;
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents = null;
  }
  
  private void e()
  {
    if (this.jdField_c_of_type_Float == 1.0F) {
      return;
    }
    this.jdField_c_of_type_Float = Math.min(1.0F, (float)(AnimationUtils.currentAnimationTimeMillis() - this.jdField_b_of_type_Long) / 200.0F);
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setAlpha((int)(this.jdField_c_of_type_Float * 255.0F));
    h();
  }
  
  private void f()
  {
    if (!this.jdField_a_of_type_AndroidWidgetPopupWindow.isShowing()) {
      return;
    }
    this.jdField_c_of_type_Long = (SystemClock.uptimeMillis() + 300L);
    c(true);
  }
  
  private void g()
  {
    if (!this.jdField_a_of_type_AndroidWidgetPopupWindow.isShowing()) {
      return;
    }
    c();
    b();
    invalidate();
  }
  
  @CalledByNative
  private float getOriginXDip()
  {
    return this.jdField_a_of_type_Float;
  }
  
  @CalledByNative
  private float getOriginYDip()
  {
    return this.jdField_b_of_type_Float;
  }
  
  @CalledByNative
  private float getVisibleHeightDip()
  {
    Drawable localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable == null) {
      return 0.0F;
    }
    return localDrawable.getIntrinsicHeight() / this.jdField_d_of_type_Float;
  }
  
  @CalledByNative
  private float getVisibleWidthDip()
  {
    Drawable localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable == null) {
      return 0.0F;
    }
    return localDrawable.getIntrinsicWidth() / this.jdField_d_of_type_Float;
  }
  
  private void h()
  {
    if (this.jdField_c_of_type_JavaLangRunnable == null) {
      this.jdField_c_of_type_JavaLangRunnable = new Runnable()
      {
        public void run()
        {
          PopupTouchHandleDrawable.a(PopupTouchHandleDrawable.this, false);
          PopupTouchHandleDrawable.d(PopupTouchHandleDrawable.this);
        }
      };
    }
    if (this.l) {
      return;
    }
    this.l = true;
    postOnAnimation(this.jdField_c_of_type_JavaLangRunnable);
  }
  
  @CalledByNative
  private void hide()
  {
    this.jdField_c_of_type_Long = 0L;
    c(false);
    this.jdField_c_of_type_Float = 1.0F;
    if (this.jdField_a_of_type_AndroidWidgetPopupWindow.isShowing()) {}
    try
    {
      this.jdField_a_of_type_AndroidWidgetPopupWindow.dismiss();
      this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver.clearListener();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;) {}
    }
  }
  
  private void i()
  {
    Runnable localRunnable = this.jdField_a_of_type_JavaLangRunnable;
    if (localRunnable == null) {
      return;
    }
    removeCallbacks(localRunnable);
  }
  
  private void j()
  {
    if (getVisibility() == 0) {
      return;
    }
    this.jdField_c_of_type_Float = 0.0F;
    this.jdField_b_of_type_Long = AnimationUtils.currentAnimationTimeMillis();
    g();
  }
  
  private native long nativeInit(float paramFloat);
  
  @CalledByNative
  private void setOrientation(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((!jdField_a_of_type_Boolean) && ((paramInt < 0) || (paramInt > 3))) {
      throw new AssertionError();
    }
    int n = this.jdField_c_of_type_Int;
    int i2 = 1;
    if (n != paramInt) {
      n = 1;
    } else {
      n = 0;
    }
    int i1 = i2;
    if (this.jdField_b_of_type_Boolean == paramBoolean2) {
      if (this.jdField_c_of_type_Boolean != paramBoolean1) {
        i1 = i2;
      } else {
        i1 = 0;
      }
    }
    this.jdField_c_of_type_Int = paramInt;
    this.jdField_b_of_type_Boolean = paramBoolean2;
    this.jdField_c_of_type_Boolean = paramBoolean1;
    if (n != 0) {
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = a(getContext(), this.jdField_c_of_type_Int);
    }
    Drawable localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      localDrawable.setAlpha((int)(this.jdField_c_of_type_Float * 255.0F));
    }
    if ((n != 0) || (i1 != 0)) {
      h();
    }
  }
  
  @CalledByNative
  private void setOrigin(float paramFloat1, float paramFloat2)
  {
    if ((this.jdField_a_of_type_Float == paramFloat1) && (this.jdField_b_of_type_Float == paramFloat2) && (!this.j)) {
      return;
    }
    this.jdField_a_of_type_Float = paramFloat1;
    this.jdField_b_of_type_Float = paramFloat2;
    if ((getVisibility() == 0) || (this.j))
    {
      if (this.j) {
        this.j = false;
      }
      h();
    }
  }
  
  @CalledByNative
  private void setVisible(boolean paramBoolean)
  {
    if (this.jdField_d_of_type_Boolean == paramBoolean) {
      return;
    }
    this.jdField_d_of_type_Boolean = paramBoolean;
    d();
  }
  
  @CalledByNative
  private void show()
  {
    if (this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore == null) {
      return;
    }
    if (this.jdField_a_of_type_AndroidWidgetPopupWindow.isShowing()) {
      return;
    }
    a(this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver.getPositionX(), this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver.getPositionY());
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver.addListener(this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver$Listener);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setContentView(this);
    try
    {
      this.jdField_a_of_type_AndroidWidgetPopupWindow.showAtLocation(this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getContainerView(), 0, a(), b());
      return;
    }
    catch (WindowManager.BadTokenException localBadTokenException)
    {
      for (;;) {}
    }
    hide();
  }
  
  public long getNativeDrawable()
  {
    return this.jdField_a_of_type_Long;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.h = true;
    d();
    WindowAndroid localWindowAndroid = this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getWindowAndroid();
    if (localWindowAndroid != null)
    {
      localWindowAndroid.getDisplay().addObserver(this);
      this.jdField_d_of_type_Float = localWindowAndroid.getDisplay().getDipScale();
      a();
    }
  }
  
  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if ((this.m) && (this.jdField_d_of_type_Float == getResources().getDisplayMetrics().density)) {
      a();
    }
  }
  
  public void onContainerViewChanged(ViewGroup paramViewGroup)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver.clearListener();
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver = new ViewPositionObserver(paramViewGroup);
    if (this.jdField_a_of_type_AndroidWidgetPopupWindow.isShowing()) {
      this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver.addListener(this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver$Listener);
    }
  }
  
  public void onDIPScaleChanged(float paramFloat)
  {
    if (this.jdField_d_of_type_Float != paramFloat)
    {
      this.jdField_d_of_type_Float = paramFloat;
      this.m = true;
    }
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    WindowAndroid localWindowAndroid = this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getWindowAndroid();
    if (localWindowAndroid != null) {
      localWindowAndroid.getDisplay().removeObserver(this);
    }
    this.h = false;
    d();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (this.jdField_a_of_type_AndroidGraphicsDrawableDrawable == null) {
      return;
    }
    int n;
    if ((!this.jdField_b_of_type_Boolean) && (!this.jdField_c_of_type_Boolean)) {
      n = 0;
    } else {
      n = 1;
    }
    if (n != 0)
    {
      paramCanvas.save();
      boolean bool = this.jdField_b_of_type_Boolean;
      float f2 = -1.0F;
      float f1;
      if (bool) {
        f1 = -1.0F;
      } else {
        f1 = 1.0F;
      }
      if (!this.jdField_c_of_type_Boolean) {
        f2 = 1.0F;
      }
      paramCanvas.scale(f1, f2, getWidth() / 2.0F, getHeight() / 2.0F);
    }
    e();
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.draw(paramCanvas);
    if (n != 0) {
      paramCanvas.restore();
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    Drawable localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable == null)
    {
      setMeasuredDimension(0, 0);
      return;
    }
    setMeasuredDimension(localDrawable.getIntrinsicWidth(), this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight());
  }
  
  public void onRotationChanged(int paramInt)
  {
    this.j = true;
  }
  
  @SuppressLint({"ClickableViewAccessibility"})
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    ContentViewCore localContentViewCore = this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore;
    if (localContentViewCore == null) {
      return false;
    }
    localContentViewCore.getContainerView().getLocationOnScreen(this.jdField_a_of_type_ArrayOfInt);
    float f1 = paramMotionEvent.getRawX();
    float f2 = paramMotionEvent.getX();
    float f3 = this.jdField_a_of_type_ArrayOfInt[0];
    float f4 = paramMotionEvent.getRawY();
    float f5 = paramMotionEvent.getY();
    float f6 = this.jdField_a_of_type_ArrayOfInt[1];
    paramMotionEvent = MotionEvent.obtainNoHistory(paramMotionEvent);
    paramMotionEvent.offsetLocation(f1 - f2 - f3, f4 - f5 - f6);
    boolean bool = this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getWebContents().getEventForwarder().b(paramMotionEvent);
    paramMotionEvent.recycle();
    return bool;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\PopupTouchHandleDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */