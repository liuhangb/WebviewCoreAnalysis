package com.tencent.smtt.webkit.ui.zoomImg;

public class a
{
  private double jdField_a_of_type_Double;
  private int jdField_a_of_type_Int;
  private double jdField_b_of_type_Double;
  private int jdField_b_of_type_Int;
  private double jdField_c_of_type_Double;
  private int jdField_c_of_type_Int;
  private int d;
  private int e;
  private int f;
  private int g;
  private int h;
  private int i;
  private int j;
  private int k;
  private int l;
  
  private double a(float paramFloat)
  {
    double d1 = this.h * 4 * 1000;
    double d2 = Math.pow(1.0F - paramFloat, 3.0D);
    Double.isNaN(d1);
    double d3 = this.g;
    Double.isNaN(d3);
    return d1 * d2 / d3;
  }
  
  private int a(float paramFloat)
  {
    double d1 = this.jdField_a_of_type_Int;
    double d2 = paramFloat * this.h;
    double d3 = this.jdField_b_of_type_Double;
    Double.isNaN(d2);
    Double.isNaN(d1);
    int n = (int)Math.round(d1 + d2 * d3);
    int i1;
    if (this.jdField_b_of_type_Double > 0.0D)
    {
      m = this.jdField_a_of_type_Int;
      i1 = this.e;
      if (m <= i1) {
        return Math.min(n, i1);
      }
    }
    int m = n;
    if (this.jdField_b_of_type_Double < 0.0D)
    {
      i1 = this.jdField_a_of_type_Int;
      int i2 = this.jdField_c_of_type_Int;
      m = n;
      if (i1 >= i2) {
        m = Math.max(n, i2);
      }
    }
    return m;
  }
  
  private int b(float paramFloat)
  {
    double d1 = this.jdField_b_of_type_Int;
    double d2 = paramFloat * this.h;
    double d3 = this.jdField_a_of_type_Double;
    Double.isNaN(d2);
    Double.isNaN(d1);
    int n = (int)Math.round(d1 + d2 * d3);
    int i1;
    if (this.jdField_a_of_type_Double > 0.0D)
    {
      m = this.jdField_b_of_type_Int;
      i1 = this.f;
      if (m <= i1) {
        return Math.min(n, i1);
      }
    }
    int m = n;
    if (this.jdField_a_of_type_Double < 0.0D)
    {
      i1 = this.jdField_b_of_type_Int;
      int i2 = this.d;
      m = n;
      if (i1 >= i2) {
        m = Math.max(n, i2);
      }
    }
    return m;
  }
  
  public int a()
  {
    return this.g;
  }
  
  public void a(float paramFloat)
  {
    paramFloat = Math.min(paramFloat, 1.0F);
    float f1 = 1.0F - (float)Math.pow(1.0F - paramFloat, 4.0D);
    this.k = a(f1);
    this.l = b(f1);
    this.jdField_c_of_type_Double = a(paramFloat);
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    this.jdField_a_of_type_Int = paramInt1;
    this.jdField_b_of_type_Int = paramInt2;
    this.jdField_c_of_type_Int = paramInt5;
    this.d = paramInt7;
    this.e = paramInt6;
    this.f = paramInt8;
    double d2 = paramInt3;
    double d3 = paramInt4;
    double d1 = Math.hypot(d2, d3);
    Double.isNaN(d3);
    this.jdField_a_of_type_Double = (d3 / d1);
    Double.isNaN(d2);
    this.jdField_b_of_type_Double = (d2 / d1);
    this.g = ((int)Math.round(Math.pow(Math.abs(d1), 0.3333333333333333D) * 50.0D));
    d2 = this.g;
    Double.isNaN(d2);
    this.h = ((int)Math.round(d1 * d2 / 4.0D / 1000.0D));
    this.i = a(1.0F);
    this.j = b(1.0F);
  }
  
  public int b()
  {
    return this.k;
  }
  
  public int c()
  {
    return this.l;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\zoomImg\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */