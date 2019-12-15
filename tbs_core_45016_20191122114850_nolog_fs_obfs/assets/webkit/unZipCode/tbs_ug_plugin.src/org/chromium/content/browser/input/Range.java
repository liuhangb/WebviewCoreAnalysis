package org.chromium.content.browser.input;

import org.chromium.base.VisibleForTesting;

public class Range
{
  private int a;
  private int b;
  
  public Range(int paramInt1, int paramInt2)
  {
    set(paramInt1, paramInt2);
  }
  
  public void clamp(int paramInt1, int paramInt2)
  {
    this.a = Math.min(Math.max(this.a, paramInt1), paramInt2);
    this.b = Math.max(Math.min(this.b, paramInt2), paramInt1);
  }
  
  public int end()
  {
    return this.b;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Range)) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    paramObject = (Range)paramObject;
    return (this.a == ((Range)paramObject).a) && (this.b == ((Range)paramObject).b);
  }
  
  public int hashCode()
  {
    return this.a * 11 + this.b * 31;
  }
  
  public boolean intersects(Range paramRange)
  {
    return (this.a <= paramRange.b) && (this.b >= paramRange.a);
  }
  
  @VisibleForTesting
  public void set(int paramInt1, int paramInt2)
  {
    this.a = Math.min(paramInt1, paramInt2);
    this.b = Math.max(paramInt1, paramInt2);
  }
  
  public int start()
  {
    return this.a;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[ ");
    localStringBuilder.append(this.a);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.b);
    localStringBuilder.append(" ]");
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\Range.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */