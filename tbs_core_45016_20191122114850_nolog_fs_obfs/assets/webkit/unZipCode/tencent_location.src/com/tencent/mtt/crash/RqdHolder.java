package com.tencent.mtt.crash;

public class RqdHolder
{
  private static IRqdFunc a;
  
  public static void addSoFile(String paramString)
  {
    IRqdFunc localIRqdFunc = a;
    if (localIRqdFunc != null) {
      localIRqdFunc.addSoFile(paramString);
    }
  }
  
  public static void addUserAction(int paramInt)
  {
    IRqdFunc localIRqdFunc = a;
    if (localIRqdFunc != null) {
      localIRqdFunc.addUserAction(paramInt);
    }
  }
  
  public static void init(IRqdFunc paramIRqdFunc)
  {
    a = paramIRqdFunc;
  }
  
  public static void reportCached(Thread paramThread, Throwable paramThrowable, String paramString)
  {
    IRqdFunc localIRqdFunc = a;
    if (localIRqdFunc != null) {
      localIRqdFunc.reportCatched(paramThread, paramThrowable, paramString);
    }
  }
  
  public static void rqdLog(String paramString1, String paramString2)
  {
    IRqdFunc localIRqdFunc = a;
    if (localIRqdFunc != null) {
      localIRqdFunc.rqdLog(paramString1, paramString2);
    }
  }
  
  public static abstract interface IRqdFunc
  {
    public abstract void addSoFile(String paramString);
    
    public abstract void addUserAction(int paramInt);
    
    public abstract void reportCatched(Thread paramThread, Throwable paramThrowable, String paramString);
    
    public abstract void rqdLog(String paramString1, String paramString2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\crash\RqdHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */