package com.tencent.common.wup;

public class WUPTaskProxy
{
  private static WUPTaskClient a = new WUPTaskClient(WUPProxyHolder.getPublicWUPProxy());
  
  public static void notifyPendingTask()
  {
    a.notifyPendingTasks();
  }
  
  public static boolean send(MultiWUPRequestBase paramMultiWUPRequestBase)
  {
    return a.send(paramMultiWUPRequestBase);
  }
  
  public static boolean send(WUPRequestBase paramWUPRequestBase)
  {
    return a.send(paramWUPRequestBase);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\WUPTaskProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */