package com.tencent.tbs.tbsshell.partner.reader;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public class QBScroller
{
  public static float ALPHA = 0.0F;
  private static float DECELERATION_RATE = (float)(Math.log(0.75D) / Math.log(0.9D));
  private static final int DEFAULT_DURATION = 250;
  private static float END_TENSION = 0.0F;
  private static final int FLING_MODE = 1;
  public static final int ILLEGAL_DURING = Integer.MAX_VALUE;
  private static final int NB_SAMPLES = 100;
  private static final int SCROLL_MODE = 0;
  private static final float[] SPLINE;
  private static float START_TENSION = 0.0F;
  public static boolean mUseIphoneAlgorithm = true;
  private static float sViscousFluidNormalize = 1.0F / viscousFluid(1.0F);
  private static float sViscousFluidScale;
  private float mCurrVelocity;
  private int mCurrX;
  private int mCurrY;
  private float mDeceleration;
  private float mDeltaX;
  private float mDeltaY;
  private float mDistance;
  private int mDuration;
  private float mDurationReciprocal;
  private int mFinalX;
  private int mFinalY;
  private boolean mFinished = true;
  private boolean mFlywheel;
  public Interpolator mInterpolator;
  private boolean mIsUpOrLeft = false;
  private long mLastComputeTime = -1L;
  private int mMaxX;
  private int mMaxY;
  private int mMinX;
  private int mMinY;
  private int mMode;
  private final float mPpi;
  private QBScrollInterpolator mScrollInterpolator = new QBScrollInterpolator();
  private long mStartTime;
  private int mStartX;
  private int mStartY;
  private float mVelocity;
  
  static
  {
    ALPHA = 200.0F;
    START_TENSION = 0.175F;
    END_TENSION = 0.35F;
    SPLINE = new float[101];
    float f2 = 0.0F;
    int i = 0;
    if (i <= 100)
    {
      float f4 = i / 100.0F;
      float f1 = 1.0F;
      for (;;)
      {
        float f3 = (f1 - f2) / 2.0F + f2;
        float f7 = 1.0F - f3;
        float f5 = 3.0F * f3 * f7;
        float f8 = START_TENSION;
        float f9 = END_TENSION;
        float f6 = f3 * f3 * f3;
        f7 = (f7 * f8 + f9 * f3) * f5 + f6;
        if (Math.abs(f7 - f4) < 1.0E-5D)
        {
          SPLINE[i] = (f5 + f6);
          i += 1;
          break;
        }
        if (f7 > f4) {
          f1 = f3;
        } else {
          f2 = f3;
        }
      }
    }
    SPLINE[100] = 1.0F;
    sViscousFluidScale = 8.0F;
    sViscousFluidNormalize = 1.0F;
  }
  
  public QBScroller(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public QBScroller(Context paramContext, Interpolator paramInterpolator)
  {
    this(paramContext, paramInterpolator, true);
  }
  
  public QBScroller(Context paramContext, Interpolator paramInterpolator, boolean paramBoolean)
  {
    this.mInterpolator = paramInterpolator;
    this.mPpi = (paramContext.getResources().getDisplayMetrics().density * 160.0F);
    this.mDeceleration = computeDeceleration(ViewConfiguration.getScrollFriction());
    this.mFlywheel = paramBoolean;
  }
  
  private float computeDeceleration(float paramFloat)
  {
    return this.mPpi * 386.0878F * paramFloat;
  }
  
  public static float viscousFluid(float paramFloat)
  {
    paramFloat *= sViscousFluidScale;
    if (paramFloat < 1.0F) {
      paramFloat -= 1.0F - (float)Math.exp(-paramFloat);
    } else {
      paramFloat = (1.0F - (float)Math.exp(1.0F - paramFloat)) * 0.63212055F + 0.36787945F;
    }
    return paramFloat * sViscousFluidNormalize;
  }
  
  public void abortAnimation()
  {
    this.mCurrX = this.mFinalX;
    this.mCurrY = this.mFinalY;
    this.mFinished = true;
  }
  
  public boolean computeScrollOffset()
  {
    if (this.mFinished) {
      return false;
    }
    int j = (int)(AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
    int i = this.mDuration;
    int k;
    if (i == Integer.MAX_VALUE)
    {
      if (this.mMode != 0) {
        return true;
      }
      if (Math.abs(System.currentTimeMillis() - this.mLastComputeTime) <= 2L) {
        return true;
      }
      this.mLastComputeTime = System.currentTimeMillis();
      int m;
      if (this.mIsUpOrLeft)
      {
        k = this.mFinalX;
        i = k - this.mStartX;
        j = this.mFinalY - this.mStartY;
        m = this.mCurrX;
        this.mCurrX = (m - this.mScrollInterpolator.getStep(m, i, k));
        k = this.mCurrY;
        this.mCurrY = (k - this.mScrollInterpolator.getStep(k, j, this.mFinalY));
        if (((i == 0) && (j == 0)) || ((j != 0) && (this.mCurrY <= this.mFinalY)) || ((i != 0) && (this.mCurrX <= this.mFinalX)))
        {
          this.mCurrX = this.mFinalX;
          this.mCurrY = this.mFinalY;
          this.mFinished = true;
          if (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime > 700L)
          {
            mUseIphoneAlgorithm = false;
            return true;
          }
        }
      }
      else
      {
        k = this.mFinalX;
        i = k - this.mStartX;
        j = this.mFinalY - this.mStartY;
        m = this.mCurrX;
        this.mCurrX = (m + this.mScrollInterpolator.getStep(m, i, k));
        k = this.mCurrY;
        this.mCurrY = (k + this.mScrollInterpolator.getStep(k, j, this.mFinalY));
        if (((i == 0) && (j == 0)) || ((j != 0) && (this.mCurrY >= this.mFinalY)) || ((i != 0) && (this.mCurrX >= this.mFinalX)))
        {
          this.mCurrX = this.mFinalX;
          this.mCurrY = this.mFinalY;
          this.mFinished = true;
          if (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime > 700L)
          {
            mUseIphoneAlgorithm = false;
            return true;
          }
        }
      }
    }
    else if (j < i)
    {
      Object localObject;
      float f1;
      switch (this.mMode)
      {
      default: 
        return true;
      case 1: 
        float f4 = j / i;
        j = (int)(f4 * 100.0F);
        float f5 = j / 100.0F;
        k = j + 1;
        float f2 = k / 100.0F;
        localObject = SPLINE;
        f1 = localObject[j];
        float f3 = localObject[k];
        f2 -= f5;
        f4 = (f4 - f5) / f2;
        f3 -= f1;
        f1 += f4 * f3;
        this.mCurrVelocity = (f3 / f2 * this.mDistance / i * 1000.0F);
        i = this.mStartX;
        this.mCurrX = (i + Math.round((this.mFinalX - i) * f1));
        this.mCurrX = Math.min(this.mCurrX, this.mMaxX);
        this.mCurrX = Math.max(this.mCurrX, this.mMinX);
        i = this.mStartY;
        this.mCurrY = (i + Math.round(f1 * (this.mFinalY - i)));
        this.mCurrY = Math.min(this.mCurrY, this.mMaxY);
        this.mCurrY = Math.max(this.mCurrY, this.mMinY);
        if ((this.mCurrX != this.mFinalX) || (this.mCurrY != this.mFinalY)) {
          break;
        }
        this.mFinished = true;
        return true;
      case 0: 
        f1 = j * this.mDurationReciprocal;
        localObject = this.mInterpolator;
        if (localObject == null) {
          f1 = viscousFluid(f1);
        } else {
          f1 = ((Interpolator)localObject).getInterpolation(f1);
        }
        this.mCurrX = (this.mStartX + Math.round(this.mDeltaX * f1));
        this.mCurrY = (this.mStartY + Math.round(f1 * this.mDeltaY));
        return true;
      }
    }
    else
    {
      this.mCurrX = this.mFinalX;
      this.mCurrY = this.mFinalY;
      this.mFinished = true;
    }
    return true;
  }
  
  public void extendDuration(int paramInt)
  {
    this.mDuration = (timePassed() + paramInt);
    this.mDurationReciprocal = (1.0F / this.mDuration);
    this.mFinished = false;
  }
  
  public void fling(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    float f1;
    int i;
    if ((this.mFlywheel) && (!this.mFinished))
    {
      f1 = getCurrVelocity();
      f4 = this.mFinalX - this.mStartX;
      f2 = this.mFinalY - this.mStartY;
      f3 = (float)Math.sqrt(f4 * f4 + f2 * f2);
      f4 /= f3;
      f3 = f2 / f3;
      f2 = f4 * f1;
      f1 = f3 * f1;
      f3 = paramInt3;
      if (Math.signum(f3) == Math.signum(f2))
      {
        i = paramInt4;
        f4 = i;
        paramInt4 = paramInt3;
        paramInt3 = i;
        if (Math.signum(f4) == Math.signum(f1))
        {
          paramInt4 = (int)(f3 + f2);
          paramInt3 = (int)(f4 + f1);
        }
      }
      else
      {
        i = paramInt4;
        paramInt4 = paramInt3;
        paramInt3 = i;
      }
    }
    else
    {
      i = paramInt3;
      paramInt3 = paramInt4;
      paramInt4 = i;
    }
    this.mMode = 1;
    this.mFinished = false;
    float f3 = (float)Math.sqrt(paramInt4 * paramInt4 + paramInt3 * paramInt3);
    this.mVelocity = f3;
    double d1 = Math.log(START_TENSION * f3 / ALPHA);
    double d2 = DECELERATION_RATE;
    Double.isNaN(d2);
    this.mDuration = ((int)(Math.exp(d1 / (d2 - 1.0D)) * 1000.0D));
    this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
    this.mStartX = paramInt1;
    this.mStartY = paramInt2;
    float f2 = 1.0F;
    if (f3 == 0.0F) {
      f1 = 1.0F;
    } else {
      f1 = paramInt4 / f3;
    }
    if (f3 != 0.0F) {
      f2 = paramInt3 / f3;
    }
    d2 = ALPHA;
    float f4 = DECELERATION_RATE;
    double d3 = f4;
    double d4 = f4;
    Double.isNaN(d4);
    Double.isNaN(d3);
    d1 = Math.exp(d3 / (d4 - 1.0D) * d1);
    Double.isNaN(d2);
    f4 = (int)(d2 * d1);
    this.mDistance = ((int)(Math.signum(f3) * f4));
    this.mMinX = paramInt5;
    this.mMaxX = paramInt6;
    this.mMinY = paramInt7;
    this.mMaxY = paramInt8;
    this.mFinalX = (paramInt1 + Math.round(f1 * f4));
    this.mFinalX = Math.min(this.mFinalX, this.mMaxX);
    this.mFinalX = Math.max(this.mFinalX, this.mMinX);
    this.mFinalY = (Math.round(f4 * f2) + paramInt2);
    this.mFinalY = Math.min(this.mFinalY, this.mMaxY);
    this.mFinalY = Math.max(this.mFinalY, this.mMinY);
  }
  
  public final void forceFinished(boolean paramBoolean)
  {
    this.mFinished = paramBoolean;
  }
  
  public float getCurrVelocity()
  {
    if (this.mMode == 1) {
      return this.mCurrVelocity;
    }
    return this.mVelocity - this.mDeceleration * timePassed() / 2000.0F;
  }
  
  public final int getCurrX()
  {
    return this.mCurrX;
  }
  
  public final int getCurrY()
  {
    return this.mCurrY;
  }
  
  public final int getDuration()
  {
    return this.mDuration;
  }
  
  public final int getFinalX()
  {
    return this.mFinalX;
  }
  
  public final int getFinalY()
  {
    return this.mFinalY;
  }
  
  public final int getStartX()
  {
    return this.mStartX;
  }
  
  public final int getStartY()
  {
    return this.mStartY;
  }
  
  public final boolean isFinished()
  {
    return this.mFinished;
  }
  
  public boolean isFling()
  {
    return this.mMode == 1;
  }
  
  public boolean isScrollingInDirection(float paramFloat1, float paramFloat2)
  {
    return (!this.mFinished) && (Math.signum(paramFloat1) == Math.signum(this.mFinalX - this.mStartX)) && (Math.signum(paramFloat2) == Math.signum(this.mFinalY - this.mStartY));
  }
  
  public void setFinalX(int paramInt)
  {
    this.mFinalX = paramInt;
    this.mDeltaX = (this.mFinalX - this.mStartX);
    this.mFinished = false;
  }
  
  public void setFinalY(int paramInt)
  {
    this.mFinalY = paramInt;
    this.mDeltaY = (this.mFinalY - this.mStartY);
    this.mFinished = false;
  }
  
  public final void setFriction(float paramFloat)
  {
    this.mDeceleration = computeDeceleration(paramFloat);
  }
  
  public void startScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    startScroll(paramInt1, paramInt2, paramInt3, paramInt4, 250);
  }
  
  public void startScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.mMode = 0;
    this.mFinished = false;
    this.mDuration = paramInt5;
    this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
    this.mStartX = paramInt1;
    this.mStartY = paramInt2;
    this.mFinalX = (paramInt1 + paramInt3);
    this.mFinalY = (paramInt2 + paramInt4);
    this.mDeltaX = paramInt3;
    this.mDeltaY = paramInt4;
    paramInt1 = this.mStartX;
    this.mCurrX = paramInt1;
    paramInt2 = this.mStartY;
    this.mCurrY = paramInt2;
    paramInt3 = this.mFinalX;
    boolean bool2 = true;
    boolean bool1 = true;
    if (paramInt1 == paramInt3)
    {
      if (this.mFinalY >= paramInt2) {
        bool1 = false;
      }
      this.mIsUpOrLeft = bool1;
      this.mScrollInterpolator.initVelocity(this.mFinalY - this.mStartY);
    }
    else if (paramInt2 == this.mFinalY)
    {
      if (paramInt3 < paramInt1) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      this.mIsUpOrLeft = bool1;
      this.mScrollInterpolator.initVelocity(this.mFinalX - this.mStartX);
    }
    this.mDurationReciprocal = (1.0F / this.mDuration);
  }
  
  public int timePassed()
  {
    return (int)(AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\reader\QBScroller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */