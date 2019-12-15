package org.chromium.mojo.bindings;

public final class DataHeader
{
  public final int a;
  public final int b;
  
  public DataHeader(int paramInt1, int paramInt2)
  {
    this.a = paramInt1;
    this.b = paramInt2;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    if (getClass() != paramObject.getClass()) {
      return false;
    }
    paramObject = (DataHeader)paramObject;
    return (this.b == ((DataHeader)paramObject).b) && (this.a == ((DataHeader)paramObject).a);
  }
  
  public int hashCode()
  {
    return (this.b + 31) * 31 + this.a;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\DataHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */