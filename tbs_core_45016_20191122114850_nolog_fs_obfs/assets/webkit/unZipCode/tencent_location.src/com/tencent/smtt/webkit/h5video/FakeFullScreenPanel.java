package com.tencent.smtt.webkit.h5video;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.lang.reflect.Field;

public class FakeFullScreenPanel
  extends FrameLayout
{
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private AnimatorSet jdField_a_of_type_AndroidAnimationAnimatorSet;
  private Bitmap jdField_a_of_type_AndroidGraphicsBitmap;
  private Rect jdField_a_of_type_AndroidGraphicsRect;
  private View jdField_a_of_type_AndroidViewView;
  private ViewGroup jdField_a_of_type_AndroidViewViewGroup = null;
  private WindowManager jdField_a_of_type_AndroidViewWindowManager;
  private FrameLayout jdField_a_of_type_AndroidWidgetFrameLayout;
  private ImageView jdField_a_of_type_AndroidWidgetImageView;
  private IFakeFullSceenPanelClient jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$IFakeFullSceenPanelClient;
  private a jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private boolean jdField_a_of_type_Boolean;
  private int jdField_b_of_type_Int;
  private ViewGroup jdField_b_of_type_AndroidViewViewGroup;
  private FrameLayout jdField_b_of_type_AndroidWidgetFrameLayout;
  private boolean jdField_b_of_type_Boolean;
  private int jdField_c_of_type_Int;
  private FrameLayout jdField_c_of_type_AndroidWidgetFrameLayout;
  private boolean jdField_c_of_type_Boolean;
  private int jdField_d_of_type_Int;
  private FrameLayout jdField_d_of_type_AndroidWidgetFrameLayout;
  private int e = -16777216;
  private int f;
  private int g;
  private int h;
  
  public FakeFullScreenPanel(Context paramContext, TencentWebViewProxy paramTencentWebViewProxy, View paramView, IFakeFullSceenPanelClient paramIFakeFullSceenPanelClient)
  {
    super(paramContext);
    this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$IFakeFullSceenPanelClient = paramIFakeFullSceenPanelClient;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    this.jdField_a_of_type_AndroidViewView = paramView;
    a(paramContext);
    setBackgroundColor(this.e);
  }
  
  private int a()
  {
    ViewGroup localViewGroup = this.jdField_a_of_type_AndroidViewViewGroup;
    if (localViewGroup != null) {
      return localViewGroup.getHeight() - b(getContext());
    }
    return 0;
  }
  
  private static int a(Context paramContext)
  {
    try
    {
      Class localClass = Class.forName("com.android.internal.R$dimen");
      Object localObject = localClass.newInstance();
      int i = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
      i = paramContext.getResources().getDimensionPixelSize(i);
      return i;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return -1;
  }
  
  @TargetApi(11)
  private int a(View paramView)
  {
    paramView.clearAnimation();
    ViewGroup localViewGroup = (ViewGroup)paramView.getParent();
    if (localViewGroup != null)
    {
      LayoutTransition localLayoutTransition = localViewGroup.getLayoutTransition();
      localViewGroup.setLayoutTransition(null);
      int i = 0;
      while (i < localViewGroup.getChildCount())
      {
        if (localViewGroup.getChildAt(i) == paramView)
        {
          localViewGroup.removeViewAt(i);
          break;
        }
        i += 1;
      }
      localViewGroup.setLayoutTransition(localLayoutTransition);
      return i;
    }
    return -1;
  }
  
  private void a(Context paramContext)
  {
    this.jdField_a_of_type_AndroidWidgetFrameLayout = new FrameLayout(paramContext);
    this.jdField_a_of_type_AndroidWidgetFrameLayout.setBackgroundColor(this.e);
    this.jdField_a_of_type_AndroidWidgetFrameLayout.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        return true;
      }
    });
    this.jdField_b_of_type_AndroidWidgetFrameLayout = new FrameLayout(paramContext);
    this.jdField_b_of_type_AndroidWidgetFrameLayout.setBackgroundColor(this.e);
    this.jdField_b_of_type_AndroidWidgetFrameLayout.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        return true;
      }
    });
    this.jdField_c_of_type_AndroidWidgetFrameLayout = new FrameLayout(paramContext);
    this.jdField_c_of_type_AndroidWidgetFrameLayout.setBackgroundColor(this.e);
    this.jdField_c_of_type_AndroidWidgetFrameLayout.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        return true;
      }
    });
    this.jdField_d_of_type_AndroidWidgetFrameLayout = new FrameLayout(paramContext);
    this.jdField_d_of_type_AndroidWidgetFrameLayout.setBackgroundColor(this.e);
    this.jdField_d_of_type_AndroidWidgetFrameLayout.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        return true;
      }
    });
    this.jdField_a_of_type_AndroidWidgetImageView = new ImageView(paramContext);
    this.jdField_a_of_type_AndroidWidgetImageView.setScaleType(ImageView.ScaleType.FIT_XY);
  }
  
  private static int b(Context paramContext)
  {
    paramContext = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramContext.getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics.heightPixels;
  }
  
  private boolean b()
  {
    return (this.jdField_c_of_type_Int < this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getContentWidth()) && (this.jdField_c_of_type_Int + this.jdField_a_of_type_Int > 0) && (this.jdField_d_of_type_Int < this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getContentHeight()) && (this.jdField_d_of_type_Int + this.jdField_b_of_type_Int > 0);
  }
  
  private static int c(Context paramContext)
  {
    paramContext = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramContext.getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics.widthPixels;
  }
  
  @TargetApi(11)
  private void c()
  {
    if (getParent() != null)
    {
      if (this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a == null) {
        return;
      }
      if ((this.jdField_a_of_type_Boolean) && (!b()))
      {
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 51));
        this.jdField_a_of_type_AndroidWidgetFrameLayout.setVisibility(8);
        this.jdField_b_of_type_AndroidWidgetFrameLayout.setVisibility(8);
        this.jdField_c_of_type_AndroidWidgetFrameLayout.setVisibility(8);
        this.jdField_d_of_type_AndroidWidgetFrameLayout.setVisibility(8);
        return;
      }
      int i;
      if (this.jdField_c_of_type_Boolean) {
        i = c(getContext());
      } else {
        i = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a.getWidth();
      }
      if (this.jdField_c_of_type_Boolean) {
        j = b(getContext());
      } else {
        j = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a.getHeight() - this.f;
      }
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().setLayoutParams(new FrameLayout.LayoutParams(-1, j, 17));
      int i3 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(this.jdField_a_of_type_Int);
      int i2 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(this.jdField_b_of_type_Int);
      int n = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewX(this.jdField_c_of_type_Int);
      int i1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewX(this.jdField_d_of_type_Int);
      if (i2 < j) {
        k = i1 - (j - i2) / 2;
      } else {
        k = i1;
      }
      if (i3 < i) {
        m = n - (i - i3) / 2;
      } else {
        m = n;
      }
      int m = Math.min(Math.max(0, m), Math.max(0, this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().computeHorizontalScrollRange() - i));
      int k = Math.min(Math.max(0, k), Math.max(0, this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().computeVerticalScrollRange() - j));
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().scrollTo(m, k);
      k = (i - i3) / 2;
      m = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getScrollX();
      int i4 = (j - i2) / 2;
      int i5 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getScrollY();
      if (i2 < j) {
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().setTranslationY(i4 - i1 + i5);
      } else {
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().setTranslationY(this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a.getTranslationY());
      }
      if (i3 < i) {
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().setTranslationX(k - n + m);
      } else {
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().setTranslationX(this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a.getTranslationX());
      }
      this.jdField_a_of_type_AndroidWidgetFrameLayout.setVisibility(0);
      this.jdField_b_of_type_AndroidWidgetFrameLayout.setVisibility(0);
      this.jdField_c_of_type_AndroidWidgetFrameLayout.setVisibility(0);
      this.jdField_d_of_type_AndroidWidgetFrameLayout.setVisibility(0);
      k = (b(getContext()) - i2) / 2 + 1;
      int j = k;
      if (k < 0) {
        j = 0;
      }
      m = (c(getContext()) - i3) / 2 + 1;
      k = m;
      if (m < 0) {
        k = 0;
      }
      this.jdField_a_of_type_AndroidWidgetFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(i, j, 51));
      this.jdField_b_of_type_AndroidWidgetFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(i, j, 83));
      this.jdField_c_of_type_AndroidWidgetFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(k, i2, 19));
      this.jdField_d_of_type_AndroidWidgetFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(k, i2, 21));
      return;
    }
  }
  
  private boolean c()
  {
    Object localObject = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$IFakeFullSceenPanelClient;
    if ((localObject != null) && (((IFakeFullSceenPanelClient)localObject).onShowPanelView(this))) {
      return true;
    }
    Activity localActivity = (Activity)getContext();
    if ((getContext() instanceof Activity)) {
      localObject = ((Activity)getContext()).getWindow();
    } else {
      localObject = null;
    }
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1);
    if ((localActivity.hasWindowFocus()) && (localObject != null))
    {
      this.jdField_a_of_type_AndroidViewViewGroup = ((ViewGroup)((Window)localObject).getDecorView());
      this.jdField_a_of_type_AndroidViewViewGroup.addView(this, localLayoutParams);
      return true;
    }
    localObject = (WindowManager)localActivity.getSystemService("window");
    this.jdField_a_of_type_AndroidViewWindowManager = ((WindowManager)localObject);
    if (localObject != null)
    {
      localObject = new WindowManager.LayoutParams();
      if (Build.VERSION.SDK_INT >= 11) {
        ((WindowManager.LayoutParams)localObject).flags = 8455424;
      }
      ((WindowManager.LayoutParams)localObject).softInputMode |= 0x20;
      ((WindowManager.LayoutParams)localObject).softInputMode |= 0x100;
      ((WindowManager.LayoutParams)localObject).width = -1;
      ((WindowManager.LayoutParams)localObject).height = -1;
      ((WindowManager.LayoutParams)localObject).gravity = 51;
      this.jdField_a_of_type_AndroidViewWindowManager.addView(this, (ViewGroup.LayoutParams)localObject);
      return true;
    }
    return false;
  }
  
  private void d()
  {
    Object localObject = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$IFakeFullSceenPanelClient;
    if ((localObject != null) && (((IFakeFullSceenPanelClient)localObject).onHidePanelView(this))) {
      return;
    }
    localObject = this.jdField_a_of_type_AndroidViewViewGroup;
    if (localObject != null) {
      ((ViewGroup)localObject).removeView(this);
    }
    localObject = this.jdField_a_of_type_AndroidViewWindowManager;
    if (localObject != null) {
      try
      {
        ((WindowManager)localObject).removeView(this);
      }
      catch (Exception localException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("requestRemoveFromWindow failed for: %s");
        localStringBuilder.append(Log.getStackTraceString(localException));
        Log.e("FakeFullScreenPanel", localStringBuilder.toString());
      }
    }
    if (this.jdField_a_of_type_AndroidViewView.getParent() == this) {
      removeView(this.jdField_a_of_type_AndroidViewView);
    }
  }
  
  @TargetApi(11)
  private void d(boolean paramBoolean)
  {
    Object localObject = this.jdField_a_of_type_AndroidAnimationAnimatorSet;
    if (localObject != null) {
      ((AnimatorSet)localObject).cancel();
    }
    f();
    if (!paramBoolean)
    {
      this.jdField_a_of_type_AndroidWidgetImageView.setVisibility(8);
      return;
    }
    localObject = ObjectAnimator.ofFloat(this.jdField_a_of_type_AndroidWidgetImageView, "alpha", new float[] { 1.0F, 0.0F });
    ((ObjectAnimator)localObject).setDuration(150L);
    ((ObjectAnimator)localObject).addListener(new Animator.AnimatorListener()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        FakeFullScreenPanel.a(FakeFullScreenPanel.this).setVisibility(8);
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        FakeFullScreenPanel.a(FakeFullScreenPanel.this).setVisibility(8);
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator) {}
      
      public void onAnimationStart(Animator paramAnonymousAnimator) {}
    });
    ((ObjectAnimator)localObject).start();
  }
  
  @TargetApi(11)
  private void e()
  {
    Object localObject1 = this.jdField_a_of_type_AndroidGraphicsBitmap;
    if (localObject1 == null)
    {
      this.jdField_a_of_type_AndroidWidgetImageView.setVisibility(8);
      return;
    }
    this.jdField_a_of_type_AndroidWidgetImageView.setImageBitmap((Bitmap)localObject1);
    this.jdField_a_of_type_AndroidWidgetImageView.setVisibility(0);
    setBackgroundColor(0);
    if (Build.VERSION.SDK_INT >= 11) {
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().setAlpha(0.01F);
    }
    int i = this.jdField_a_of_type_AndroidGraphicsBitmap.getHeight();
    int j = this.jdField_a_of_type_AndroidGraphicsBitmap.getWidth();
    Object localObject2 = this.jdField_a_of_type_AndroidGraphicsRect;
    localObject1 = localObject2;
    if (localObject2 == null)
    {
      k = (c(getContext()) - j) / 2;
      m = (b(getContext()) - i) / 2;
      localObject1 = new Rect(k, m, k + j, m + i);
    }
    int k = c(getContext());
    int m = b(getContext());
    localObject2 = new FrameLayout.LayoutParams(j, i);
    this.jdField_a_of_type_AndroidWidgetImageView.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    localObject2 = new Rect(0, 0, k, m);
    int n = ((Rect)localObject1).left;
    int i1 = ((Rect)localObject2).centerX();
    int i2 = ((Rect)localObject1).centerX();
    int i3 = ((Rect)localObject1).top;
    int i4 = ((Rect)localObject2).centerY();
    int i5 = ((Rect)localObject1).centerY();
    localObject2 = ObjectAnimator.ofFloat(this.jdField_a_of_type_AndroidWidgetImageView, "scaleX", new float[] { 1.0F, k / j });
    ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(this.jdField_a_of_type_AndroidWidgetImageView, "scaleY", new float[] { 1.0F, m / i });
    ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(this.jdField_a_of_type_AndroidWidgetImageView, "translationX", new float[] { ((Rect)localObject1).left, n + (i1 - i2) });
    localObject1 = ObjectAnimator.ofFloat(this.jdField_a_of_type_AndroidWidgetImageView, "translationY", new float[] { ((Rect)localObject1).top, i3 + (i4 - i5) });
    this.jdField_a_of_type_AndroidAnimationAnimatorSet = new AnimatorSet();
    this.jdField_a_of_type_AndroidAnimationAnimatorSet.play((Animator)localObject2).with(localObjectAnimator1).with(localObjectAnimator2).with((Animator)localObject1);
    this.jdField_a_of_type_AndroidAnimationAnimatorSet.setDuration(150L);
    this.jdField_a_of_type_AndroidAnimationAnimatorSet.addListener(new Animator.AnimatorListener()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        FakeFullScreenPanel.b(FakeFullScreenPanel.this);
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        FakeFullScreenPanel.b(FakeFullScreenPanel.this);
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator) {}
      
      public void onAnimationStart(Animator paramAnonymousAnimator) {}
    });
    this.jdField_a_of_type_AndroidAnimationAnimatorSet.start();
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
    {
      public void run()
      {
        if (FakeFullScreenPanel.a(FakeFullScreenPanel.this) != null) {
          FakeFullScreenPanel.a(FakeFullScreenPanel.this, true);
        }
      }
    }, 1000L);
  }
  
  @TargetApi(11)
  private void f()
  {
    setBackgroundColor(-16777216);
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().setAlpha(1.0F);
  }
  
  public void a()
  {
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
    {
      public void run()
      {
        FakeFullScreenPanel.a(FakeFullScreenPanel.this, true);
      }
    }, 500L);
  }
  
  public void a(float paramFloat1, float paramFloat2)
  {
    c();
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    c();
  }
  
  public void a(Bitmap paramBitmap)
  {
    this.jdField_a_of_type_AndroidGraphicsBitmap = paramBitmap;
  }
  
  public void a(boolean paramBoolean)
  {
    this.jdField_c_of_type_Boolean = paramBoolean;
  }
  
  @SuppressLint({"InlinedApi"})
  public boolean a()
  {
    if (getParent() != null) {
      return true;
    }
    if (!c()) {
      return false;
    }
    this.jdField_b_of_type_AndroidViewViewGroup = ((ViewGroup)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getParent());
    ViewGroup.LayoutParams localLayoutParams = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getLayoutParams();
    this.g = a(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView());
    Object localObject = new FrameLayout.LayoutParams(-1, this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getHeight(), 17);
    addView(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView(), (ViewGroup.LayoutParams)localObject);
    addView(this.jdField_a_of_type_AndroidWidgetFrameLayout);
    addView(this.jdField_b_of_type_AndroidWidgetFrameLayout);
    addView(this.jdField_c_of_type_AndroidWidgetFrameLayout);
    addView(this.jdField_d_of_type_AndroidWidgetFrameLayout);
    localObject = (ViewGroup)this.jdField_a_of_type_AndroidViewView.getParent();
    if (localObject != null) {
      ((ViewGroup)localObject).removeView(this.jdField_a_of_type_AndroidViewView);
    }
    addView(this.jdField_a_of_type_AndroidViewView, new FrameLayout.LayoutParams(-1, -1));
    addView(this.jdField_a_of_type_AndroidWidgetImageView);
    this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a = new a(getContext());
    this.jdField_b_of_type_AndroidViewViewGroup.addView(this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a, this.g, localLayoutParams);
    this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a.setTranslationX(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getTranslationX());
    this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a.setTranslationY(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getTranslationY());
    this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a.scrollTo(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getScrollX(), this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getScrollY());
    this.f = a(getContext());
    this.h = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getAddressbarDisplayMode();
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.setAddressbarDisplayMode(4, false);
    c();
    this.jdField_a_of_type_AndroidGraphicsRect = new Rect();
    if (!this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getGlobalVisibleRect(this.jdField_a_of_type_AndroidGraphicsRect)) {
      this.jdField_a_of_type_AndroidGraphicsRect = null;
    }
    e();
    return true;
  }
  
  @TargetApi(11)
  public void b()
  {
    a locala = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a;
    if (locala == null) {
      return;
    }
    this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a = null;
    d();
    a(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView());
    int k = this.g;
    int i = 0;
    int j;
    for (;;)
    {
      j = k;
      if (i >= this.jdField_b_of_type_AndroidViewViewGroup.getChildCount()) {
        break;
      }
      if (this.jdField_b_of_type_AndroidViewViewGroup.getChildAt(i) == locala)
      {
        this.jdField_b_of_type_AndroidViewViewGroup.removeViewAt(i);
        j = i;
        break;
      }
      i += 1;
    }
    this.jdField_b_of_type_AndroidViewViewGroup.addView(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView(), j, locala.getLayoutParams());
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().setTranslationY(locala.getTranslationY());
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().setTranslationX(locala.getTranslationX());
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().scrollTo(locala.getScrollX(), locala.getScrollY());
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.setAddressbarDisplayMode(this.h, false);
    this.jdField_a_of_type_AndroidViewViewGroup = null;
    this.jdField_a_of_type_AndroidViewView = null;
    this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel$a = null;
    this.jdField_a_of_type_AndroidViewWindowManager = null;
    this.jdField_b_of_type_AndroidViewViewGroup = null;
  }
  
  public void b(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int m = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getContentWidth();
    int k = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getContentHeight();
    int i = Math.min(Math.max(paramInt1, 0), m);
    int j = Math.min(Math.max(paramInt2, 0), k);
    paramInt1 = Math.min(Math.max(paramInt3 + paramInt1, 0), m);
    paramInt3 = Math.min(Math.max(paramInt4 + paramInt2, 0), k);
    paramInt2 = paramInt1 - i;
    paramInt3 -= j;
    paramInt4 = c(getContext());
    k = b(getContext());
    paramInt1 = 1;
    paramInt4 = paramInt4 * k * 1 / 4;
    k = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(this.jdField_a_of_type_Int);
    m = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(this.jdField_b_of_type_Int);
    int n = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(paramInt2);
    int i1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(paramInt3);
    if ((k * m >= paramInt4) || (n * i1 <= paramInt4)) {
      paramInt1 = 0;
    }
    if (paramInt1 == 0)
    {
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isSoftKeyBoardShowing())
      {
        this.jdField_a_of_type_Long = System.currentTimeMillis();
        return;
      }
      if ((this.jdField_a_of_type_Long != -1L) && (System.currentTimeMillis() - this.jdField_a_of_type_Long < 1000L)) {
        return;
      }
      this.jdField_a_of_type_Long = -1L;
    }
    if ((this.jdField_c_of_type_Int == i) && (this.jdField_d_of_type_Int == j) && (this.jdField_a_of_type_Int == paramInt2) && (this.jdField_b_of_type_Int == paramInt3)) {
      return;
    }
    this.jdField_c_of_type_Int = i;
    this.jdField_d_of_type_Int = j;
    this.jdField_a_of_type_Int = paramInt2;
    this.jdField_b_of_type_Int = paramInt3;
    c();
  }
  
  public void b(boolean paramBoolean)
  {
    if (this.jdField_b_of_type_Boolean == paramBoolean) {
      return;
    }
    this.jdField_b_of_type_Boolean = paramBoolean;
    c();
  }
  
  public void c(boolean paramBoolean)
  {
    if (this.jdField_a_of_type_Boolean == paramBoolean) {
      return;
    }
    this.jdField_a_of_type_Boolean = paramBoolean;
    c();
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    post(new Runnable()
    {
      public void run()
      {
        int i = FakeFullScreenPanel.a(FakeFullScreenPanel.this);
        FakeFullScreenPanel.this.setPadding(0, 0, 0, i);
        View localView = new View(FakeFullScreenPanel.this.getContext());
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, FakeFullScreenPanel.a(FakeFullScreenPanel.this), 83);
        localLayoutParams.setMargins(0, 0, 0, -i);
        localView.setBackgroundColor(-16777216);
        FakeFullScreenPanel.this.addView(localView, localLayoutParams);
        localView.setOnTouchListener(new View.OnTouchListener()
        {
          public boolean onTouch(View paramAnonymous2View, MotionEvent paramAnonymous2MotionEvent)
          {
            return true;
          }
        });
      }
    });
  }
  
  public static abstract interface IFakeFullSceenPanelClient
  {
    public abstract boolean onHidePanelView(View paramView);
    
    public abstract boolean onShowPanelView(View paramView);
    
    public abstract boolean shouldAttachToWebView();
  }
  
  private class a
    extends View
  {
    public a(Context paramContext)
    {
      super();
    }
    
    protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
      FakeFullScreenPanel.a(FakeFullScreenPanel.this).getRealWebView().getLayoutParams().height = (paramInt2 - FakeFullScreenPanel.b(FakeFullScreenPanel.this));
      FakeFullScreenPanel.a(FakeFullScreenPanel.this);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\h5video\FakeFullScreenPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */