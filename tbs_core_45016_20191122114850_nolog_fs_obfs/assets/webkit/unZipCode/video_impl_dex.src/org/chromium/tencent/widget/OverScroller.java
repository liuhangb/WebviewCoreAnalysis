package org.chromium.tencent.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import java.lang.reflect.Method;
import org.chromium.base.CommandLine;

public class OverScroller
{
  private static final boolean DEBUG = true;
  private static final int DEFAULT_DURATION = 250;
  private static final int FLING_MODE = 1;
  private static final String LOG_TAG = "OverScroller";
  private static final int SCROLL_MODE = 0;
  private static Method methodviscousFluid;
  private final boolean mFlywheel;
  private Interpolator mInterpolator;
  private int mMode;
  RenderTestingHelper mRenderTestingHelper;
  private final SplineOverScroller mScrollerX;
  private final SplineOverScroller mScrollerY;
  
  public OverScroller(Context paramContext, Interpolator paramInterpolator)
  {
    this(paramContext, paramInterpolator, true);
  }
  
  public OverScroller(Context paramContext, Interpolator paramInterpolator, float paramFloat1, float paramFloat2)
  {
    this(paramContext, paramInterpolator, true);
  }
  
  public OverScroller(Context paramContext, Interpolator paramInterpolator, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    this(paramContext, paramInterpolator, paramBoolean);
  }
  
  public OverScroller(Context paramContext, Interpolator paramInterpolator, boolean paramBoolean)
  {
    this.mInterpolator = paramInterpolator;
    this.mFlywheel = paramBoolean;
    this.mScrollerX = new SplineOverScroller(paramContext);
    this.mScrollerY = new SplineOverScroller(paramContext);
    this.mRenderTestingHelper = new RenderTestingHelper(null);
  }
  
  public void abortAnimation()
  {
    this.mScrollerX.finish();
    this.mScrollerY.finish();
  }
  
  public boolean computeScrollOffset()
  {
    if (isFinished()) {
      return false;
    }
    Interpolator localInterpolator;
    switch (this.mMode)
    {
    default: 
      return true;
    case 1: 
      if ((!this.mScrollerX.mFinished) && (!this.mScrollerX.update()) && (!this.mScrollerX.continueWhenFinished())) {
        this.mScrollerX.finish();
      }
      if ((!this.mScrollerY.mFinished) && (!this.mScrollerY.update()) && (!this.mScrollerY.continueWhenFinished())) {
        this.mScrollerY.finish();
      }
      if ((isFinished()) && (CommandLine.getInstance().hasSwitch("enable-render-testing-log")) && (this.mRenderTestingHelper.mIsFlingStarted))
      {
        RenderTestingHelper.access$802(this.mRenderTestingHelper, false);
        return true;
      }
      break;
    case 0: 
      long l = AnimationUtils.currentAnimationTimeMillis() - this.mScrollerX.mStartTime;
      int i = this.mScrollerX.mDuration;
      if (l < i)
      {
        f2 = (float)l / i;
        localInterpolator = this.mInterpolator;
        if (localInterpolator != null) {}
      }
      break;
    }
    try
    {
      if (methodviscousFluid == null) {
        methodviscousFluid = Scroller.class.getMethod("viscousFluid", new Class[] { Float.TYPE });
      }
      f1 = f2;
      if (methodviscousFluid == null) {
        break label270;
      }
      f1 = ((Float)methodviscousFluid.invoke(Scroller.class, new Object[] { Float.valueOf(f2) })).floatValue();
    }
    catch (Exception localException)
    {
      for (;;)
      {
        float f1 = f2;
      }
    }
    f1 = localInterpolator.getInterpolation(f2);
    label270:
    this.mScrollerX.updateScroll(f1);
    this.mScrollerY.updateScroll(f1);
    return true;
    abortAnimation();
    return true;
  }
  
  @Deprecated
  public void extendDuration(int paramInt)
  {
    this.mScrollerX.extendDuration(paramInt);
    this.mScrollerY.extendDuration(paramInt);
  }
  
  public void fling(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    fling(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, 0, 0);
  }
  
  public void fling(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
  {
    if ((this.mFlywheel) && (!isFinished()))
    {
      float f1 = this.mScrollerX.mCurrVelocity;
      float f2 = this.mScrollerY.mCurrVelocity;
      float f3 = paramInt3;
      if (Math.signum(f3) == Math.signum(f1))
      {
        float f4 = paramInt4;
        if (Math.signum(f4) == Math.signum(f2))
        {
          paramInt3 = (int)(f3 + f1);
          paramInt4 = (int)(f4 + f2);
        }
      }
      else {}
    }
    this.mMode = 1;
    this.mScrollerX.fling(paramInt1, paramInt3, paramInt5, paramInt6, paramInt9);
    this.mScrollerY.fling(paramInt2, paramInt4, paramInt7, paramInt8, paramInt10);
    if ((CommandLine.getInstance().hasSwitch("enable-render-testing-log")) && (!this.mRenderTestingHelper.mIsFlingStarted)) {
      RenderTestingHelper.access$802(this.mRenderTestingHelper, true);
    }
  }
  
  public final void forceFinished(boolean paramBoolean)
  {
    SplineOverScroller.access$102(this.mScrollerX, SplineOverScroller.access$102(this.mScrollerY, paramBoolean));
  }
  
  public float getCurrVelocity()
  {
    return (float)Math.sqrt(this.mScrollerX.mCurrVelocity * this.mScrollerX.mCurrVelocity + this.mScrollerY.mCurrVelocity * this.mScrollerY.mCurrVelocity);
  }
  
  public final int getCurrX()
  {
    return this.mScrollerX.mCurrentPosition;
  }
  
  public final int getCurrY()
  {
    return this.mScrollerY.mCurrentPosition;
  }
  
  @Deprecated
  public final int getDuration()
  {
    return Math.max(this.mScrollerX.mDuration, this.mScrollerY.mDuration);
  }
  
  public final int getFinalX()
  {
    return this.mScrollerX.mFinal;
  }
  
  public final int getFinalY()
  {
    return this.mScrollerY.mFinal;
  }
  
  public final int getStartX()
  {
    return this.mScrollerX.mStart;
  }
  
  public final int getStartY()
  {
    return this.mScrollerY.mStart;
  }
  
  public final boolean isFinished()
  {
    return (this.mScrollerX.mFinished) && (this.mScrollerY.mFinished);
  }
  
  public boolean isOverScrolled()
  {
    return ((!this.mScrollerX.mFinished) && (this.mScrollerX.mState != 0)) || ((!this.mScrollerY.mFinished) && (this.mScrollerY.mState != 0));
  }
  
  public boolean isScrollingInDirection(float paramFloat1, float paramFloat2)
  {
    int i = this.mScrollerX.mFinal;
    int j = this.mScrollerX.mStart;
    int k = this.mScrollerY.mFinal;
    int m = this.mScrollerY.mStart;
    return (!isFinished()) && (Math.signum(paramFloat1) == Math.signum(i - j)) && (Math.signum(paramFloat2) == Math.signum(k - m));
  }
  
  public void notifyHorizontalEdgeReached(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mScrollerX.notifyEdgeReached(paramInt1, paramInt2, paramInt3);
  }
  
  public void notifyVerticalEdgeReached(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mScrollerY.notifyEdgeReached(paramInt1, paramInt2, paramInt3);
  }
  
  @Deprecated
  public void setFinalX(int paramInt)
  {
    this.mScrollerX.setFinalPosition(paramInt);
  }
  
  @Deprecated
  public void setFinalY(int paramInt)
  {
    this.mScrollerY.setFinalPosition(paramInt);
  }
  
  public final void setFriction(float paramFloat)
  {
    this.mScrollerX.setFriction(paramFloat);
    this.mScrollerY.setFriction(paramFloat);
  }
  
  void setInterpolator(Interpolator paramInterpolator)
  {
    this.mInterpolator = paramInterpolator;
  }
  
  public boolean springBack(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    boolean bool1 = true;
    this.mMode = 1;
    boolean bool2 = this.mScrollerX.springback(paramInt1, paramInt3, paramInt4);
    boolean bool3 = this.mScrollerY.springback(paramInt2, paramInt5, paramInt6);
    if (!bool2)
    {
      if (bool3) {
        return true;
      }
      bool1 = false;
    }
    return bool1;
  }
  
  public void startScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    startScroll(paramInt1, paramInt2, paramInt3, paramInt4, 250);
  }
  
  public void startScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.mMode = 0;
    this.mScrollerX.startScroll(paramInt1, paramInt3, paramInt5);
    this.mScrollerY.startScroll(paramInt2, paramInt4, paramInt5);
  }
  
  public int timePassed()
  {
    return (int)(AnimationUtils.currentAnimationTimeMillis() - Math.min(this.mScrollerX.mStartTime, this.mScrollerY.mStartTime));
  }
  
  private class RenderTestingHelper
  {
    private static final String RENDER_TESTING_LOGTAG = "render_testing_log";
    private static final String RENDER_TESTING_LOG_SWITCH = "enable-render-testing-log";
    private boolean mIsFlingStarted = false;
    
    private RenderTestingHelper() {}
  }
  
  static class SplineOverScroller
  {
    private static final int BALLISTIC = 2;
    private static float DECELERATION_RATE = (float)(Math.log(0.78D) / Math.log(0.9D));
    private static final float END_TENSION = 1.0F;
    private static final int EXPONENTIAL = 1;
    private static final float GRAVITY = 2000.0F;
    private static final float INFLEXION = 0.35F;
    private static final int NB_SAMPLES = 100;
    private static final float P1 = 0.175F;
    private static final float P2 = 0.35000002F;
    private static final int SPLINE = 0;
    private static float[] SPLINE_POSITION = null;
    private static float[] SPLINE_TIME = null;
    private static final float START_TENSION = 0.5F;
    private float mCurrVelocity;
    private int mCurrentPosition;
    private float mDeceleration;
    private int mDuration;
    private int mFinal;
    private boolean mFinished = true;
    private float mFlingFriction = ViewConfiguration.getScrollFriction();
    private int mOver;
    private float mPhysicalCoeff;
    private int mSplineDistance;
    private int mSplineDuration;
    private int mStart;
    private long mStartTime;
    private int mState = 0;
    private int mVelocity;
    
    SplineOverScroller(Context paramContext)
    {
      this.mPhysicalCoeff = (paramContext.getResources().getDisplayMetrics().density * 160.0F * 386.0878F * 0.84F);
    }
    
    private void adjustDuration(int paramInt1, int paramInt2, int paramInt3)
    {
      float f3 = Math.abs((paramInt3 - paramInt1) / (paramInt2 - paramInt1));
      paramInt1 = (int)(f3 * 100.0F);
      initSamplePositionIfNeed();
      if (paramInt1 < 100)
      {
        float f4 = paramInt1 / 100.0F;
        paramInt2 = paramInt1 + 1;
        float f5 = paramInt2 / 100.0F;
        float[] arrayOfFloat = SPLINE_TIME;
        float f1 = arrayOfFloat[paramInt1];
        float f2 = arrayOfFloat[paramInt2];
        f3 = (f3 - f4) / (f5 - f4);
        this.mDuration = ((int)(this.mDuration * (f1 + f3 * (f2 - f1))));
      }
    }
    
    private void fitOnBounceCurve(int paramInt1, int paramInt2, int paramInt3)
    {
      float f2 = -paramInt3;
      float f1 = this.mDeceleration;
      f2 /= f1;
      double d1 = paramInt3 * paramInt3 / 2.0F / Math.abs(f1) + Math.abs(paramInt2 - paramInt1);
      Double.isNaN(d1);
      double d2 = Math.abs(this.mDeceleration);
      Double.isNaN(d2);
      f1 = (float)Math.sqrt(d1 * 2.0D / d2);
      this.mStartTime -= (int)((f1 - f2) * 1000.0F);
      this.mStart = paramInt2;
      this.mVelocity = ((int)(-this.mDeceleration * f1));
    }
    
    private static float getDeceleration(int paramInt)
    {
      if (paramInt > 0) {
        return -2000.0F;
      }
      return 2000.0F;
    }
    
    private double getSplineDeceleration(int paramInt)
    {
      return Math.log(Math.abs(paramInt) * 0.35F / (this.mFlingFriction * this.mPhysicalCoeff));
    }
    
    private double getSplineFlingDistance(int paramInt)
    {
      double d2 = getSplineDeceleration(paramInt);
      float f = DECELERATION_RATE;
      double d3 = f;
      Double.isNaN(d3);
      double d1 = this.mFlingFriction * this.mPhysicalCoeff;
      double d4 = f;
      Double.isNaN(d4);
      d2 = Math.exp(d4 / (d3 - 1.0D) * d2);
      Double.isNaN(d1);
      return d1 * d2;
    }
    
    private int getSplineFlingDuration(int paramInt)
    {
      double d1 = getSplineDeceleration(paramInt);
      double d2 = DECELERATION_RATE;
      Double.isNaN(d2);
      return (int)(Math.exp(d1 / (d2 - 1.0D)) * 1000.0D);
    }
    
    private static void initSamplePositionIfNeed()
    {
      if (SPLINE_POSITION == null)
      {
        SPLINE_POSITION = new float[101];
        SPLINE_TIME = new float[101];
        int i = 0;
        float f1 = 0.0F;
        float f2 = 0.0F;
        if (i < 100)
        {
          float f5 = i / 100.0F;
          float f3 = 1.0F;
          for (;;)
          {
            float f4 = (f3 - f1) / 2.0F + f1;
            float f6 = 1.0F - f4;
            float f7 = f4 * 3.0F * f6;
            float f8 = f4 * f4 * f4;
            float f9 = (f6 * 0.175F + f4 * 0.35000002F) * f7 + f8;
            if (Math.abs(f9 - f5) < 1.0E-5D)
            {
              SPLINE_POSITION[i] = (f7 * (f6 * 0.5F + f4) + f8);
              f3 = 1.0F;
              for (;;)
              {
                f4 = (f3 - f2) / 2.0F + f2;
                f6 = 1.0F - f4;
                f7 = f4 * 3.0F * f6;
                f8 = f4 * f4 * f4;
                f9 = (f6 * 0.5F + f4) * f7 + f8;
                if (Math.abs(f9 - f5) < 1.0E-5D)
                {
                  SPLINE_TIME[i] = (f7 * (f6 * 0.175F + f4 * 0.35000002F) + f8);
                  i += 1;
                  break;
                }
                if (f9 > f5) {
                  f3 = f4;
                } else {
                  f2 = f4;
                }
              }
            }
            if (f9 > f5) {
              f3 = f4;
            } else {
              f1 = f4;
            }
          }
        }
        float[] arrayOfFloat = SPLINE_POSITION;
        SPLINE_TIME[100] = 1.0F;
        arrayOfFloat[100] = 1.0F;
      }
    }
    
    private void onEdgeReached()
    {
      int i = this.mVelocity;
      float f2 = i * i / (Math.abs(this.mDeceleration) * 2.0F);
      float f3 = Math.signum(this.mVelocity);
      i = this.mOver;
      float f1 = f2;
      if (f2 > i)
      {
        f1 = -f3;
        int j = this.mVelocity;
        this.mDeceleration = (f1 * j * j / (i * 2.0F));
        f1 = i;
      }
      this.mOver = ((int)f1);
      this.mState = 2;
      i = this.mStart;
      if (this.mVelocity <= 0) {
        f1 = -f1;
      }
      this.mFinal = (i + (int)f1);
      this.mDuration = (-(int)(this.mVelocity * 1000.0F / this.mDeceleration));
    }
    
    private void startAfterEdge(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      int k = 1;
      if ((paramInt1 > paramInt2) && (paramInt1 < paramInt3))
      {
        Log.e("OverScroller", "startAfterEdge called from a valid position");
        this.mFinished = true;
        return;
      }
      int i;
      if (paramInt1 > paramInt3) {
        i = 1;
      } else {
        i = 0;
      }
      int j;
      if (i != 0) {
        j = paramInt3;
      } else {
        j = paramInt2;
      }
      int m = paramInt1 - j;
      if (m * paramInt4 < 0) {
        k = 0;
      }
      if (k != 0)
      {
        startBounceAfterEdge(paramInt1, j, paramInt4);
        return;
      }
      if (getSplineFlingDistance(paramInt4) > Math.abs(m))
      {
        if (i == 0) {
          paramInt2 = paramInt1;
        }
        if (i != 0) {
          paramInt3 = paramInt1;
        }
        fling(paramInt1, paramInt4, paramInt2, paramInt3, this.mOver);
        return;
      }
      startSpringback(paramInt1, j, paramInt4);
    }
    
    private void startBounceAfterEdge(int paramInt1, int paramInt2, int paramInt3)
    {
      int i;
      if (paramInt3 == 0) {
        i = paramInt1 - paramInt2;
      } else {
        i = paramInt3;
      }
      this.mDeceleration = getDeceleration(i);
      fitOnBounceCurve(paramInt1, paramInt2, paramInt3);
      onEdgeReached();
    }
    
    private void startSpringback(int paramInt1, int paramInt2, int paramInt3)
    {
      this.mFinished = false;
      this.mState = 1;
      this.mStart = paramInt1;
      this.mFinal = paramInt2;
      paramInt1 -= paramInt2;
      this.mDeceleration = getDeceleration(paramInt1);
      this.mVelocity = (-paramInt1);
      this.mOver = Math.abs(paramInt1);
      double d1 = paramInt1;
      Double.isNaN(d1);
      double d2 = this.mDeceleration;
      Double.isNaN(d2);
      this.mDuration = ((int)(Math.sqrt(d1 * -2.0D / d2) * 1000.0D));
    }
    
    boolean continueWhenFinished()
    {
      switch (this.mState)
      {
      default: 
        break;
      case 2: 
        this.mStartTime += this.mDuration;
        startSpringback(this.mFinal, this.mStart, 0);
        break;
      case 1: 
        return false;
      case 0: 
        if (this.mDuration < this.mSplineDuration)
        {
          this.mStart = this.mFinal;
          this.mVelocity = ((int)this.mCurrVelocity);
          this.mDeceleration = (getDeceleration(this.mVelocity) * 2.0F);
          this.mStartTime += this.mDuration;
          onEdgeReached();
        }
        else
        {
          return false;
        }
        break;
      }
      update();
      return true;
    }
    
    void extendDuration(int paramInt)
    {
      this.mDuration = ((int)(AnimationUtils.currentAnimationTimeMillis() - this.mStartTime) + paramInt);
      this.mFinished = false;
    }
    
    void finish()
    {
      this.mCurrentPosition = this.mFinal;
      this.mFinished = true;
    }
    
    void fling(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      this.mOver = paramInt5;
      this.mFinished = false;
      this.mVelocity = paramInt2;
      float f = paramInt2;
      this.mCurrVelocity = f;
      this.mSplineDuration = 0;
      this.mDuration = 0;
      this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
      this.mStart = paramInt1;
      this.mCurrentPosition = paramInt1;
      if ((paramInt1 <= paramInt4) && (paramInt1 >= paramInt3))
      {
        this.mState = 0;
        double d1 = 0.0D;
        if (paramInt2 != 0)
        {
          paramInt5 = getSplineFlingDuration(paramInt2);
          this.mSplineDuration = paramInt5;
          this.mDuration = paramInt5;
          d1 = getSplineFlingDistance(paramInt2);
        }
        double d2 = Math.signum(f);
        Double.isNaN(d2);
        this.mSplineDistance = ((int)(d1 * d2));
        this.mFinal = (paramInt1 + this.mSplineDistance);
        paramInt1 = this.mFinal;
        if (paramInt1 < paramInt3)
        {
          adjustDuration(this.mStart, paramInt1, paramInt3);
          this.mFinal = paramInt3;
        }
        paramInt1 = this.mFinal;
        if (paramInt1 > paramInt4)
        {
          adjustDuration(this.mStart, paramInt1, paramInt4);
          this.mFinal = paramInt4;
        }
        return;
      }
      startAfterEdge(paramInt1, paramInt3, paramInt4, paramInt2);
    }
    
    void notifyEdgeReached(int paramInt1, int paramInt2, int paramInt3)
    {
      if (this.mState == 0)
      {
        this.mOver = paramInt3;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        startAfterEdge(paramInt1, paramInt2, paramInt2, (int)this.mCurrVelocity);
      }
    }
    
    void setFinalPosition(int paramInt)
    {
      this.mFinal = paramInt;
      this.mFinished = false;
    }
    
    void setFriction(float paramFloat)
    {
      this.mFlingFriction = paramFloat;
    }
    
    boolean springback(int paramInt1, int paramInt2, int paramInt3)
    {
      this.mFinished = true;
      this.mFinal = paramInt1;
      this.mStart = paramInt1;
      this.mVelocity = 0;
      this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
      this.mDuration = 0;
      if (paramInt1 < paramInt2) {
        startSpringback(paramInt1, paramInt2, 0);
      } else if (paramInt1 > paramInt3) {
        startSpringback(paramInt1, paramInt3, 0);
      } else {
        this.mCurrentPosition = this.mStart;
      }
      return this.mFinished ^ true;
    }
    
    void startScroll(int paramInt1, int paramInt2, int paramInt3)
    {
      this.mFinished = false;
      this.mStart = paramInt1;
      this.mFinal = (paramInt1 + paramInt2);
      this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
      this.mDuration = paramInt3;
      this.mDeceleration = 0.0F;
      this.mVelocity = 0;
    }
    
    boolean update()
    {
      long l1 = AnimationUtils.currentAnimationTimeMillis() - this.mStartTime;
      initSamplePositionIfNeed();
      int i = this.mDuration;
      long l2 = i;
      boolean bool = false;
      if (l1 > l2) {
        return false;
      }
      double d = 0.0D;
      int j = this.mState;
      float f2 = 1.0F;
      float f1;
      float f3;
      switch (j)
      {
      default: 
        break;
      case 2: 
        f1 = (float)l1 / 1000.0F;
        i = this.mVelocity;
        f2 = i;
        f3 = this.mDeceleration;
        this.mCurrVelocity = (f2 + f3 * f1);
        d = i * f1 + f3 * f1 * f1 / 2.0F;
        break;
      case 1: 
        f2 = (float)l1 / i;
        f1 = Math.signum(this.mVelocity);
        f2 = (float)Math.pow(6.0D, f2 * -5.0F);
        i = this.mOver;
        d = i * f1 * (1.0F - f2);
        this.mCurrVelocity = (f1 * i * 5.0F * 1.79175F * f2);
        break;
      case 0: 
        if (l1 <= 0L)
        {
          if (i > 0) {
            bool = true;
          }
          return bool;
        }
        f3 = (float)l1 / this.mSplineDuration;
        i = (int)(f3 * 100.0F);
        if (i < 100)
        {
          f2 = i / 100.0F;
          j = i + 1;
          f1 = j / 100.0F;
          float[] arrayOfFloat = SPLINE_POSITION;
          float f4 = arrayOfFloat[i];
          f1 = (arrayOfFloat[j] - f4) / (f1 - f2);
          f2 = f4 + (f3 - f2) * f1;
        }
        else
        {
          f1 = 0.0F;
        }
        i = this.mSplineDistance;
        d = f2 * i;
        this.mCurrVelocity = (f1 * i / this.mSplineDuration * 1000.0F);
      }
      this.mCurrentPosition = (this.mStart + (int)Math.round(d));
      return true;
    }
    
    void updateScroll(float paramFloat)
    {
      int i = this.mStart;
      this.mCurrentPosition = (i + Math.round(paramFloat * (this.mFinal - i)));
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\widget\OverScroller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */