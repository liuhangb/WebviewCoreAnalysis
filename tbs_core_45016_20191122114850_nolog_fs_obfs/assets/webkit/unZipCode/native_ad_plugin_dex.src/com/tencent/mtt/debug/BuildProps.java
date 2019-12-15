package com.tencent.mtt.debug;

public class BuildProps
{
  private static IBuildProps a;
  
  public static boolean isDiscardPushLog()
  {
    IBuildProps localIBuildProps = a;
    if (localIBuildProps != null) {
      return localIBuildProps.isDiscardPushLog();
    }
    return true;
  }
  
  public static void setImpl(IBuildProps paramIBuildProps)
  {
    a = paramIBuildProps;
  }
  
  public static abstract interface IBuildProps
  {
    public abstract boolean isDiscardPushLog();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\debug\BuildProps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */