package org.chromium.mojo.system;

public class MojoException
  extends RuntimeException
{
  private final int a;
  
  public MojoException(int paramInt)
  {
    this.a = paramInt;
  }
  
  public MojoException(Throwable paramThrowable)
  {
    super(paramThrowable);
    this.a = 2;
  }
  
  public int getMojoResult()
  {
    return this.a;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("MojoResult(");
    localStringBuilder.append(this.a);
    localStringBuilder.append("): ");
    localStringBuilder.append(MojoResult.a(this.a));
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\MojoException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */