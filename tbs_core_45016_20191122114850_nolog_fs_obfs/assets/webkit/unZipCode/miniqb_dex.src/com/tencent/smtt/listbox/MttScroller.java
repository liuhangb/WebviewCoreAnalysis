package com.tencent.smtt.listbox;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public class MttScroller
{
  public static boolean a = true;
  private static final float[] jdField_a_of_type_ArrayOfFloat;
  private static float jdField_e_of_type_Float = (float)(Math.log(0.75D) / Math.log(0.9D));
  private static float jdField_f_of_type_Float = 800.0F;
  private static float jdField_g_of_type_Float = 0.4F;
  private static float jdField_h_of_type_Float = 1.0F - jdField_g_of_type_Float;
  private static float jdField_k_of_type_Float;
  private static float jdField_l_of_type_Float = 1.0F / a(1.0F);
  private float jdField_a_of_type_Float;
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private Interpolator jdField_a_of_type_AndroidViewAnimationInterpolator;
  private float jdField_b_of_type_Float;
  private int jdField_b_of_type_Int;
  private long jdField_b_of_type_Long = -1L;
  private boolean jdField_b_of_type_Boolean = false;
  private float jdField_c_of_type_Float;
  private int jdField_c_of_type_Int;
  private boolean jdField_c_of_type_Boolean = true;
  private float jdField_d_of_type_Float;
  private int jdField_d_of_type_Int;
  private boolean jdField_d_of_type_Boolean;
  private int jdField_e_of_type_Int;
  private int jdField_f_of_type_Int;
  private int jdField_g_of_type_Int;
  private int jdField_h_of_type_Int;
  private float jdField_i_of_type_Float;
  private int jdField_i_of_type_Int;
  private final float jdField_j_of_type_Float;
  private int jdField_j_of_type_Int;
  private int jdField_k_of_type_Int;
  private int jdField_l_of_type_Int;
  
  static
  {
    jdField_a_of_type_ArrayOfFloat = new float[101];
    float f2 = 0.0F;
    int m = 0;
    if (m <= 100)
    {
      float f4 = m / 100.0F;
      float f1 = 1.0F;
      for (;;)
      {
        float f3 = (f1 - f2) / 2.0F + f2;
        float f7 = 1.0F - f3;
        float f5 = 3.0F * f3 * f7;
        float f8 = jdField_g_of_type_Float;
        float f9 = jdField_h_of_type_Float;
        float f6 = f3 * f3 * f3;
        f7 = (f7 * f8 + f9 * f3) * f5 + f6;
        if (Math.abs(f7 - f4) < 1.0E-5D)
        {
          jdField_a_of_type_ArrayOfFloat[m] = (f5 + f6);
          m += 1;
          break;
        }
        if (f7 > f4) {
          f1 = f3;
        } else {
          f2 = f3;
        }
      }
    }
    jdField_a_of_type_ArrayOfFloat[100] = 1.0F;
    jdField_k_of_type_Float = 8.0F;
    jdField_l_of_type_Float = 1.0F;
  }
  
  public MttScroller(Context paramContext, Interpolator paramInterpolator)
  {
    this(paramContext, paramInterpolator, false);
  }
  
  public MttScroller(Context paramContext, Interpolator paramInterpolator, boolean paramBoolean)
  {
    this.jdField_a_of_type_AndroidViewAnimationInterpolator = paramInterpolator;
    this.jdField_j_of_type_Float = (paramContext.getResources().getDisplayMetrics().density * 160.0F);
    this.jdField_i_of_type_Float = b(ViewConfiguration.getScrollFriction());
    this.jdField_d_of_type_Boolean = paramBoolean;
  }
  
  static float a(float paramFloat)
  {
    paramFloat *= jdField_k_of_type_Float;
    if (paramFloat < 1.0F) {
      paramFloat -= 1.0F - (float)Math.exp(-paramFloat);
    } else {
      paramFloat = (1.0F - (float)Math.exp(1.0F - paramFloat)) * 0.63212055F + 0.36787945F;
    }
    return paramFloat * jdField_l_of_type_Float;
  }
  
  private float b(float paramFloat)
  {
    return this.jdField_j_of_type_Float * 386.0878F * paramFloat;
  }
  
  public float a()
  {
    return this.jdField_d_of_type_Float - this.jdField_i_of_type_Float * a() / 2000.0F;
  }
  
  public int a()
  {
    return (int)(AnimationUtils.currentAnimationTimeMillis() - this.jdField_a_of_type_Long);
  }
  
  public void a()
  {
    this.jdField_j_of_type_Int = this.jdField_d_of_type_Int;
    this.jdField_k_of_type_Int = this.jdField_e_of_type_Int;
    this.jdField_c_of_type_Boolean = true;
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.jdField_a_of_type_Int = 0;
    this.jdField_c_of_type_Boolean = false;
    this.jdField_l_of_type_Int = paramInt5;
    this.jdField_a_of_type_Long = AnimationUtils.currentAnimationTimeMillis();
    this.jdField_b_of_type_Int = paramInt1;
    this.jdField_c_of_type_Int = paramInt2;
    this.jdField_d_of_type_Int = (paramInt1 + paramInt3);
    this.jdField_e_of_type_Int = (paramInt2 + paramInt4);
    this.jdField_b_of_type_Float = paramInt3;
    this.jdField_c_of_type_Float = paramInt4;
    paramInt1 = this.jdField_b_of_type_Int;
    this.jdField_j_of_type_Int = paramInt1;
    paramInt2 = this.jdField_c_of_type_Int;
    this.jdField_k_of_type_Int = paramInt2;
    paramInt3 = this.jdField_d_of_type_Int;
    boolean bool2 = true;
    boolean bool1 = true;
    if (paramInt1 == paramInt3)
    {
      if (this.jdField_e_of_type_Int >= paramInt2) {
        bool1 = false;
      }
      this.jdField_b_of_type_Boolean = bool1;
    }
    else if (paramInt2 == this.jdField_e_of_type_Int)
    {
      if (paramInt3 < paramInt1) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      this.jdField_b_of_type_Boolean = bool1;
    }
    this.jdField_a_of_type_Float = (1.0F / this.jdField_l_of_type_Int);
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    float f1;
    int m;
    if ((this.jdField_d_of_type_Boolean) && (!this.jdField_c_of_type_Boolean))
    {
      f1 = a();
      float f4 = this.jdField_d_of_type_Int - this.jdField_b_of_type_Int;
      f2 = this.jdField_e_of_type_Int - this.jdField_c_of_type_Int;
      f3 = FloatMath.sqrt(f4 * f4 + f2 * f2);
      f4 /= f3;
      f3 = f2 / f3;
      f2 = f4 * f1;
      f1 = f3 * f1;
      f3 = paramInt3;
      if (Math.signum(f3) == Math.signum(f2))
      {
        m = paramInt4;
        f4 = m;
        paramInt4 = paramInt3;
        paramInt3 = m;
        if (Math.signum(f4) == Math.signum(f1))
        {
          paramInt4 = (int)(f3 + f2);
          paramInt3 = (int)(f4 + f1);
        }
      }
      else
      {
        m = paramInt4;
        paramInt4 = paramInt3;
        paramInt3 = m;
      }
    }
    else
    {
      m = paramInt3;
      paramInt3 = paramInt4;
      paramInt4 = m;
    }
    this.jdField_a_of_type_Int = 1;
    this.jdField_c_of_type_Boolean = false;
    float f3 = FloatMath.sqrt(paramInt4 * paramInt4 + paramInt3 * paramInt3);
    this.jdField_d_of_type_Float = f3;
    double d1 = Math.log(jdField_g_of_type_Float * f3 / jdField_f_of_type_Float);
    double d2 = jdField_e_of_type_Float;
    Double.isNaN(d2);
    this.jdField_l_of_type_Int = ((int)(Math.exp(d1 / (d2 - 1.0D)) * 1000.0D));
    this.jdField_a_of_type_Long = AnimationUtils.currentAnimationTimeMillis();
    this.jdField_b_of_type_Int = paramInt1;
    this.jdField_c_of_type_Int = paramInt2;
    float f2 = 1.0F;
    if (f3 == 0.0F) {
      f1 = 1.0F;
    } else {
      f1 = paramInt4 / f3;
    }
    if (f3 != 0.0F) {
      f2 = paramInt3 / f3;
    }
    d2 = jdField_f_of_type_Float;
    f3 = jdField_e_of_type_Float;
    double d3 = f3;
    double d4 = f3;
    Double.isNaN(d4);
    Double.isNaN(d3);
    d1 = Math.exp(d3 / (d4 - 1.0D) * d1);
    Double.isNaN(d2);
    paramInt3 = (int)(d2 * d1);
    this.jdField_f_of_type_Int = paramInt5;
    this.jdField_g_of_type_Int = paramInt6;
    this.jdField_h_of_type_Int = paramInt7;
    this.jdField_i_of_type_Int = paramInt8;
    f3 = paramInt3;
    this.jdField_d_of_type_Int = (paramInt1 + Math.round(f1 * f3));
    this.jdField_d_of_type_Int = Math.min(this.jdField_d_of_type_Int, this.jdField_g_of_type_Int);
    this.jdField_d_of_type_Int = Math.max(this.jdField_d_of_type_Int, this.jdField_f_of_type_Int);
    this.jdField_e_of_type_Int = (Math.round(f3 * f2) + paramInt2);
    this.jdField_e_of_type_Int = Math.min(this.jdField_e_of_type_Int, this.jdField_i_of_type_Int);
    this.jdField_e_of_type_Int = Math.max(this.jdField_e_of_type_Int, this.jdField_h_of_type_Int);
  }
  
  public boolean a()
  {
    if (this.jdField_c_of_type_Boolean) {
      return false;
    }
    int m = (int)(AnimationUtils.currentAnimationTimeMillis() - this.jdField_a_of_type_Long);
    int n = this.jdField_l_of_type_Int;
    if (n == Integer.MAX_VALUE)
    {
      if (this.jdField_a_of_type_Int != 0) {
        return true;
      }
      if (Math.abs(System.currentTimeMillis() - this.jdField_b_of_type_Long) <= 2L) {
        return true;
      }
      this.jdField_b_of_type_Long = System.currentTimeMillis();
      if (this.jdField_b_of_type_Boolean)
      {
        m = this.jdField_d_of_type_Int - this.jdField_b_of_type_Int;
        n = this.jdField_e_of_type_Int - this.jdField_c_of_type_Int;
        if (((m == 0) && (n == 0)) || ((n != 0) && (this.jdField_k_of_type_Int <= this.jdField_e_of_type_Int)) || ((m != 0) && (this.jdField_j_of_type_Int <= this.jdField_d_of_type_Int)))
        {
          this.jdField_j_of_type_Int = this.jdField_d_of_type_Int;
          this.jdField_k_of_type_Int = this.jdField_e_of_type_Int;
          this.jdField_c_of_type_Boolean = true;
          if (AnimationUtils.currentAnimationTimeMillis() - this.jdField_a_of_type_Long > 700L)
          {
            jdField_a_of_type_Boolean = false;
            return true;
          }
        }
      }
      else
      {
        m = this.jdField_d_of_type_Int - this.jdField_b_of_type_Int;
        n = this.jdField_e_of_type_Int - this.jdField_c_of_type_Int;
        if (((m == 0) && (n == 0)) || ((n != 0) && (this.jdField_k_of_type_Int >= this.jdField_e_of_type_Int)) || ((m != 0) && (this.jdField_j_of_type_Int >= this.jdField_d_of_type_Int)))
        {
          this.jdField_j_of_type_Int = this.jdField_d_of_type_Int;
          this.jdField_k_of_type_Int = this.jdField_e_of_type_Int;
          this.jdField_c_of_type_Boolean = true;
          if (AnimationUtils.currentAnimationTimeMillis() - this.jdField_a_of_type_Long > 700L)
          {
            jdField_a_of_type_Boolean = false;
            return true;
          }
        }
      }
    }
    else if (m < n)
    {
      float f1;
      Object localObject;
      switch (this.jdField_a_of_type_Int)
      {
      default: 
        return true;
      case 1: 
        f1 = m / n;
        m = (int)(f1 * 100.0F);
        float f2 = m / 100.0F;
        n = m + 1;
        float f3 = n / 100.0F;
        localObject = jdField_a_of_type_ArrayOfFloat;
        float f4 = localObject[m];
        float f5 = localObject[n];
        f1 = f4 + (f1 - f2) / (f3 - f2) * (f5 - f4);
        m = this.jdField_b_of_type_Int;
        this.jdField_j_of_type_Int = (m + Math.round((this.jdField_d_of_type_Int - m) * f1));
        this.jdField_j_of_type_Int = Math.min(this.jdField_j_of_type_Int, this.jdField_g_of_type_Int);
        this.jdField_j_of_type_Int = Math.max(this.jdField_j_of_type_Int, this.jdField_f_of_type_Int);
        m = this.jdField_c_of_type_Int;
        this.jdField_k_of_type_Int = (m + Math.round(f1 * (this.jdField_e_of_type_Int - m)));
        this.jdField_k_of_type_Int = Math.min(this.jdField_k_of_type_Int, this.jdField_i_of_type_Int);
        this.jdField_k_of_type_Int = Math.max(this.jdField_k_of_type_Int, this.jdField_h_of_type_Int);
        if ((this.jdField_j_of_type_Int != this.jdField_d_of_type_Int) || (this.jdField_k_of_type_Int != this.jdField_e_of_type_Int)) {
          break;
        }
        this.jdField_c_of_type_Boolean = true;
        return true;
      case 0: 
        f1 = m * this.jdField_a_of_type_Float;
        localObject = this.jdField_a_of_type_AndroidViewAnimationInterpolator;
        if (localObject == null) {
          f1 = a(f1);
        } else {
          f1 = ((Interpolator)localObject).getInterpolation(f1);
        }
        this.jdField_j_of_type_Int = (this.jdField_b_of_type_Int + Math.round(this.jdField_b_of_type_Float * f1));
        this.jdField_k_of_type_Int = (this.jdField_c_of_type_Int + Math.round(f1 * this.jdField_c_of_type_Float));
        return true;
      }
    }
    else
    {
      this.jdField_j_of_type_Int = this.jdField_d_of_type_Int;
      this.jdField_k_of_type_Int = this.jdField_e_of_type_Int;
      this.jdField_c_of_type_Boolean = true;
    }
    return true;
  }
  
  public final void forceFinished(boolean paramBoolean)
  {
    this.jdField_c_of_type_Boolean = paramBoolean;
  }
  
  public final int getCurrX()
  {
    return this.jdField_j_of_type_Int;
  }
  
  public final int getCurrY()
  {
    return this.jdField_k_of_type_Int;
  }
  
  public final int getDuration()
  {
    return this.jdField_l_of_type_Int;
  }
  
  public final int getFinalX()
  {
    return this.jdField_d_of_type_Int;
  }
  
  public final int getFinalY()
  {
    return this.jdField_e_of_type_Int;
  }
  
  public final int getStartX()
  {
    return this.jdField_b_of_type_Int;
  }
  
  public final int getStartY()
  {
    return this.jdField_c_of_type_Int;
  }
  
  public final boolean isFinished()
  {
    return this.jdField_c_of_type_Boolean;
  }
  
  public final void setFriction(float paramFloat)
  {
    this.jdField_i_of_type_Float = b(paramFloat);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\listbox\MttScroller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */