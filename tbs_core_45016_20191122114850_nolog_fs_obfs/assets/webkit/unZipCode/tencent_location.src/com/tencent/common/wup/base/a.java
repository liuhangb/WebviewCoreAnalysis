package com.tencent.common.wup.base;

class a
{
  private final double jdField_a_of_type_Double;
  private final int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long = 0L;
  private double b = -1.0D;
  
  public a(double paramDouble)
  {
    this.jdField_a_of_type_Double = paramDouble;
    int i;
    if (paramDouble == 0.0D) {
      i = Integer.MAX_VALUE;
    } else {
      i = (int)Math.ceil(1.0D / paramDouble);
    }
    this.jdField_a_of_type_Int = i;
  }
  
  protected double a()
  {
    return this.b;
  }
  
  protected void a()
  {
    this.b = -1.0D;
    this.jdField_a_of_type_Long = 0L;
  }
  
  protected void a(double paramDouble)
  {
    if (paramDouble <= 0.0D) {
      return;
    }
    double d1 = 1.0D - this.jdField_a_of_type_Double;
    long l = this.jdField_a_of_type_Long;
    if (l > this.jdField_a_of_type_Int)
    {
      this.b = Math.exp(d1 * Math.log(this.b) + this.jdField_a_of_type_Double * Math.log(paramDouble));
    }
    else if (l > 0L)
    {
      double d2 = l;
      Double.isNaN(d2);
      double d3 = l;
      Double.isNaN(d3);
      d1 = d1 * d2 / (d3 + 1.0D);
      this.b = Math.exp(d1 * Math.log(this.b) + (1.0D - d1) * Math.log(paramDouble));
    }
    else
    {
      this.b = paramDouble;
    }
    this.jdField_a_of_type_Long += 1L;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\base\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */