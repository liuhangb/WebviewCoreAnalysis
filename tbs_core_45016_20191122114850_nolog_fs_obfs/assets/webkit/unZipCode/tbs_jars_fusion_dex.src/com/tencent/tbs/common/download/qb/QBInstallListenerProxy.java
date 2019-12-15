package com.tencent.tbs.common.download.qb;

class QBInstallListenerProxy
  implements QBInstallListener
{
  private Object mDecorator;
  
  public QBInstallListenerProxy(Object paramObject)
  {
    this.mDecorator = paramObject;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QBInstallListenerProxy decorator=");
    localStringBuilder.append(paramObject);
    localStringBuilder.toString();
  }
  
  public boolean onInstallFinished()
  {
    Object localObject = this.mDecorator;
    boolean bool = false;
    if (localObject != null)
    {
      localObject = QBDownloadProxy.invokeMethod(localObject, "onInstallFinished", null, new Object[0]);
      if ((localObject instanceof Boolean)) {
        bool = ((Boolean)localObject).booleanValue();
      }
      return bool;
    }
    return false;
  }
  
  public boolean onInstalling()
  {
    Object localObject = this.mDecorator;
    boolean bool = false;
    if (localObject != null)
    {
      localObject = QBDownloadProxy.invokeMethod(localObject, "onInstalling", null, new Object[0]);
      if ((localObject instanceof Boolean)) {
        bool = ((Boolean)localObject).booleanValue();
      }
      return bool;
    }
    return false;
  }
  
  public boolean onUninstallFinished()
  {
    Object localObject = this.mDecorator;
    boolean bool = false;
    if (localObject != null)
    {
      localObject = QBDownloadProxy.invokeMethod(localObject, "onUninstallFinished", null, new Object[0]);
      if ((localObject instanceof Boolean)) {
        bool = ((Boolean)localObject).booleanValue();
      }
      return bool;
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\QBInstallListenerProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */