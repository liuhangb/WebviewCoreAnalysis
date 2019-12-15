package com.tencent.tbs.common.zxing;

public final class FormatException
  extends ReaderException
{
  private static final FormatException INSTANCE = new FormatException();
  
  static
  {
    INSTANCE.setStackTrace(NO_TRACE);
  }
  
  private FormatException() {}
  
  private FormatException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
  
  public static FormatException getFormatInstance()
  {
    if (isStackTrace) {
      return new FormatException();
    }
    return INSTANCE;
  }
  
  public static FormatException getFormatInstance(Throwable paramThrowable)
  {
    if (isStackTrace) {
      return new FormatException(paramThrowable);
    }
    return INSTANCE;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\zxing\FormatException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */