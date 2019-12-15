package org.chromium.base.library_loader;

public class ProcessInitException
  extends Exception
{
  private int a = 0;
  
  public ProcessInitException(int paramInt)
  {
    this.a = paramInt;
  }
  
  public ProcessInitException(int paramInt, Throwable paramThrowable)
  {
    super(null, paramThrowable);
    this.a = paramInt;
  }
  
  public int getErrorCode()
  {
    return this.a;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\library_loader\ProcessInitException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */