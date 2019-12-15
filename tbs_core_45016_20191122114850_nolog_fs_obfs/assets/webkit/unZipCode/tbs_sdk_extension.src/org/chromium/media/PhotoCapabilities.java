package org.chromium.media;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("media")
class PhotoCapabilities
{
  public final double a;
  public final int a;
  public final boolean a;
  public final int[] a;
  public final double b;
  public final int b;
  public final boolean b;
  public final int[] b;
  public final double c;
  public final int c;
  public final boolean c;
  public final int[] c;
  public final double d;
  public final int d;
  public final int[] d;
  public final double e;
  public final int e;
  public final double f;
  public final int f;
  public final double g;
  public final int g;
  public final double h;
  public final int h;
  public final int i;
  public final int j;
  public final int k;
  public final int l;
  public final int m;
  public final int n;
  public final int o;
  public final int p;
  public final int q;
  public final int r;
  public final int s;
  
  PhotoCapabilities(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt13, int[] paramArrayOfInt1, int paramInt14, int[] paramArrayOfInt2, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, int paramInt15, int[] paramArrayOfInt3, int[] paramArrayOfInt4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt16, int paramInt17, int paramInt18, int paramInt19)
  {
    this.jdField_a_of_type_Int = paramInt1;
    this.jdField_b_of_type_Int = paramInt2;
    this.jdField_c_of_type_Int = paramInt3;
    this.jdField_d_of_type_Int = paramInt4;
    this.jdField_e_of_type_Int = paramInt5;
    this.jdField_f_of_type_Int = paramInt6;
    this.jdField_g_of_type_Int = paramInt7;
    this.jdField_h_of_type_Int = paramInt8;
    this.i = paramInt9;
    this.j = paramInt10;
    this.k = paramInt11;
    this.l = paramInt12;
    this.jdField_a_of_type_Double = paramDouble1;
    this.jdField_b_of_type_Double = paramDouble2;
    this.jdField_c_of_type_Double = paramDouble3;
    this.jdField_d_of_type_Double = paramDouble4;
    this.m = paramInt13;
    this.jdField_a_of_type_ArrayOfInt = paramArrayOfInt1;
    this.n = paramInt14;
    this.jdField_b_of_type_ArrayOfInt = paramArrayOfInt2;
    this.jdField_e_of_type_Double = paramDouble5;
    this.jdField_f_of_type_Double = paramDouble6;
    this.jdField_g_of_type_Double = paramDouble7;
    this.jdField_h_of_type_Double = paramDouble8;
    this.o = paramInt15;
    this.jdField_c_of_type_ArrayOfInt = paramArrayOfInt3;
    this.jdField_d_of_type_ArrayOfInt = paramArrayOfInt4;
    this.jdField_a_of_type_Boolean = paramBoolean1;
    this.jdField_b_of_type_Boolean = paramBoolean2;
    this.jdField_c_of_type_Boolean = paramBoolean3;
    this.p = paramInt16;
    this.q = paramInt17;
    this.r = paramInt18;
    this.s = paramInt19;
  }
  
  @CalledByNative
  public int getCurrentColorTemperature()
  {
    return this.r;
  }
  
  @CalledByNative
  public double getCurrentExposureCompensation()
  {
    return this.jdField_g_of_type_Double;
  }
  
  @CalledByNative
  public int getCurrentHeight()
  {
    return this.jdField_g_of_type_Int;
  }
  
  @CalledByNative
  public int getCurrentIso()
  {
    return this.jdField_c_of_type_Int;
  }
  
  @CalledByNative
  public int getCurrentWidth()
  {
    return this.k;
  }
  
  @CalledByNative
  public double getCurrentZoom()
  {
    return this.jdField_c_of_type_Double;
  }
  
  @CalledByNative
  public int getExposureMode()
  {
    return this.n;
  }
  
  @CalledByNative
  public int[] getExposureModes()
  {
    int[] arrayOfInt = this.jdField_b_of_type_ArrayOfInt;
    if (arrayOfInt != null) {
      return (int[])arrayOfInt.clone();
    }
    return new int[0];
  }
  
  @CalledByNative
  public int[] getFillLightModes()
  {
    int[] arrayOfInt = this.jdField_d_of_type_ArrayOfInt;
    if (arrayOfInt != null) {
      return (int[])arrayOfInt.clone();
    }
    return new int[0];
  }
  
  @CalledByNative
  public int getFocusMode()
  {
    return this.m;
  }
  
  @CalledByNative
  public int[] getFocusModes()
  {
    int[] arrayOfInt = this.jdField_a_of_type_ArrayOfInt;
    if (arrayOfInt != null) {
      return (int[])arrayOfInt.clone();
    }
    return new int[0];
  }
  
  @CalledByNative
  public int getMaxColorTemperature()
  {
    return this.p;
  }
  
  @CalledByNative
  public double getMaxExposureCompensation()
  {
    return this.jdField_e_of_type_Double;
  }
  
  @CalledByNative
  public int getMaxHeight()
  {
    return this.jdField_e_of_type_Int;
  }
  
  @CalledByNative
  public int getMaxIso()
  {
    return this.jdField_a_of_type_Int;
  }
  
  @CalledByNative
  public int getMaxWidth()
  {
    return this.i;
  }
  
  @CalledByNative
  public double getMaxZoom()
  {
    return this.jdField_a_of_type_Double;
  }
  
  @CalledByNative
  public int getMinColorTemperature()
  {
    return this.q;
  }
  
  @CalledByNative
  public double getMinExposureCompensation()
  {
    return this.jdField_f_of_type_Double;
  }
  
  @CalledByNative
  public int getMinHeight()
  {
    return this.jdField_f_of_type_Int;
  }
  
  @CalledByNative
  public int getMinIso()
  {
    return this.jdField_b_of_type_Int;
  }
  
  @CalledByNative
  public int getMinWidth()
  {
    return this.j;
  }
  
  @CalledByNative
  public double getMinZoom()
  {
    return this.jdField_b_of_type_Double;
  }
  
  @CalledByNative
  public boolean getRedEyeReduction()
  {
    return this.jdField_c_of_type_Boolean;
  }
  
  @CalledByNative
  public int getStepColorTemperature()
  {
    return this.s;
  }
  
  @CalledByNative
  public double getStepExposureCompensation()
  {
    return this.jdField_h_of_type_Double;
  }
  
  @CalledByNative
  public int getStepHeight()
  {
    return this.jdField_h_of_type_Int;
  }
  
  @CalledByNative
  public int getStepIso()
  {
    return this.jdField_d_of_type_Int;
  }
  
  @CalledByNative
  public int getStepWidth()
  {
    return this.l;
  }
  
  @CalledByNative
  public double getStepZoom()
  {
    return this.jdField_d_of_type_Double;
  }
  
  @CalledByNative
  public boolean getSupportsTorch()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  @CalledByNative
  public boolean getTorch()
  {
    return this.jdField_b_of_type_Boolean;
  }
  
  @CalledByNative
  public int getWhiteBalanceMode()
  {
    return this.o;
  }
  
  @CalledByNative
  public int[] getWhiteBalanceModes()
  {
    int[] arrayOfInt = this.jdField_c_of_type_ArrayOfInt;
    if (arrayOfInt != null) {
      return (int[])arrayOfInt.clone();
    }
    return new int[0];
  }
  
  public static class Builder
  {
    public double a;
    public int a;
    public boolean a;
    public int[] a;
    public double b;
    public int b;
    public boolean b;
    public int[] b;
    public double c;
    public int c;
    public boolean c;
    public int[] c;
    public double d;
    public int d;
    public int[] d;
    public double e;
    public int e;
    public double f;
    public int f;
    public double g;
    public int g;
    public double h;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public int o;
    public int p;
    public int q;
    public int r;
    public int s;
    
    public Builder a(double paramDouble)
    {
      this.jdField_a_of_type_Double = paramDouble;
      return this;
    }
    
    public Builder a(int paramInt)
    {
      this.jdField_a_of_type_Int = paramInt;
      return this;
    }
    
    public Builder a(boolean paramBoolean)
    {
      this.jdField_a_of_type_Boolean = paramBoolean;
      return this;
    }
    
    public Builder a(int[] paramArrayOfInt)
    {
      this.jdField_a_of_type_ArrayOfInt = ((int[])paramArrayOfInt.clone());
      return this;
    }
    
    public PhotoCapabilities a()
    {
      return new PhotoCapabilities(this.jdField_a_of_type_Int, this.jdField_b_of_type_Int, this.jdField_c_of_type_Int, this.jdField_d_of_type_Int, this.jdField_e_of_type_Int, this.jdField_f_of_type_Int, this.jdField_g_of_type_Int, this.jdField_h_of_type_Int, this.i, this.j, this.k, this.l, this.jdField_a_of_type_Double, this.jdField_b_of_type_Double, this.jdField_c_of_type_Double, this.jdField_d_of_type_Double, this.m, this.jdField_a_of_type_ArrayOfInt, this.n, this.jdField_b_of_type_ArrayOfInt, this.jdField_e_of_type_Double, this.jdField_f_of_type_Double, this.jdField_g_of_type_Double, this.jdField_h_of_type_Double, this.o, this.jdField_c_of_type_ArrayOfInt, this.jdField_d_of_type_ArrayOfInt, this.jdField_a_of_type_Boolean, this.jdField_b_of_type_Boolean, this.jdField_c_of_type_Boolean, this.p, this.q, this.r, this.s);
    }
    
    public Builder b(double paramDouble)
    {
      this.jdField_b_of_type_Double = paramDouble;
      return this;
    }
    
    public Builder b(int paramInt)
    {
      this.jdField_b_of_type_Int = paramInt;
      return this;
    }
    
    public Builder b(boolean paramBoolean)
    {
      this.jdField_b_of_type_Boolean = paramBoolean;
      return this;
    }
    
    public Builder b(int[] paramArrayOfInt)
    {
      this.jdField_b_of_type_ArrayOfInt = ((int[])paramArrayOfInt.clone());
      return this;
    }
    
    public Builder c(double paramDouble)
    {
      this.jdField_c_of_type_Double = paramDouble;
      return this;
    }
    
    public Builder c(int paramInt)
    {
      this.jdField_c_of_type_Int = paramInt;
      return this;
    }
    
    public Builder c(boolean paramBoolean)
    {
      this.jdField_c_of_type_Boolean = paramBoolean;
      return this;
    }
    
    public Builder c(int[] paramArrayOfInt)
    {
      this.jdField_c_of_type_ArrayOfInt = ((int[])paramArrayOfInt.clone());
      return this;
    }
    
    public Builder d(double paramDouble)
    {
      this.jdField_d_of_type_Double = paramDouble;
      return this;
    }
    
    public Builder d(int paramInt)
    {
      this.jdField_d_of_type_Int = paramInt;
      return this;
    }
    
    public Builder d(int[] paramArrayOfInt)
    {
      this.jdField_d_of_type_ArrayOfInt = ((int[])paramArrayOfInt.clone());
      return this;
    }
    
    public Builder e(double paramDouble)
    {
      this.jdField_e_of_type_Double = paramDouble;
      return this;
    }
    
    public Builder e(int paramInt)
    {
      this.jdField_e_of_type_Int = paramInt;
      return this;
    }
    
    public Builder f(double paramDouble)
    {
      this.jdField_f_of_type_Double = paramDouble;
      return this;
    }
    
    public Builder f(int paramInt)
    {
      this.jdField_f_of_type_Int = paramInt;
      return this;
    }
    
    public Builder g(double paramDouble)
    {
      this.jdField_g_of_type_Double = paramDouble;
      return this;
    }
    
    public Builder g(int paramInt)
    {
      this.jdField_g_of_type_Int = paramInt;
      return this;
    }
    
    public Builder h(double paramDouble)
    {
      this.jdField_h_of_type_Double = paramDouble;
      return this;
    }
    
    public Builder h(int paramInt)
    {
      this.jdField_h_of_type_Int = paramInt;
      return this;
    }
    
    public Builder i(int paramInt)
    {
      this.i = paramInt;
      return this;
    }
    
    public Builder j(int paramInt)
    {
      this.j = paramInt;
      return this;
    }
    
    public Builder k(int paramInt)
    {
      this.k = paramInt;
      return this;
    }
    
    public Builder l(int paramInt)
    {
      this.l = paramInt;
      return this;
    }
    
    public Builder m(int paramInt)
    {
      this.m = paramInt;
      return this;
    }
    
    public Builder n(int paramInt)
    {
      this.n = paramInt;
      return this;
    }
    
    public Builder o(int paramInt)
    {
      this.o = paramInt;
      return this;
    }
    
    public Builder p(int paramInt)
    {
      this.p = paramInt;
      return this;
    }
    
    public Builder q(int paramInt)
    {
      this.q = paramInt;
      return this;
    }
    
    public Builder r(int paramInt)
    {
      this.r = paramInt;
      return this;
    }
    
    public Builder s(int paramInt)
    {
      this.s = paramInt;
      return this;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\PhotoCapabilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */