package com.tencent.tbs.common.zxing;

public abstract class ReaderException
  extends Exception
{
  protected static final StackTraceElement[] NO_TRACE = new StackTraceElement[0];
  protected static final boolean isStackTrace;
  
  static
  {
    boolean bool;
    if (System.getProperty("surefire.test.class.path") != null) {
      bool = true;
    } else {
      bool = false;
    }
    isStackTrace = bool;
  }
  
  ReaderException() {}
  
  ReaderException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
  
  public final Throwable fillInStackTrace()
  {
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\zxing\ReaderException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */