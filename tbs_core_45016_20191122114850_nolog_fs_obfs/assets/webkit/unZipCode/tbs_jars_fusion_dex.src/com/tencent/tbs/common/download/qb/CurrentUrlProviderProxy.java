package com.tencent.tbs.common.download.qb;

class CurrentUrlProviderProxy
  implements CurrentUrlProvider
{
  private Object mDecorator;
  
  public CurrentUrlProviderProxy(Object paramObject)
  {
    this.mDecorator = paramObject;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CurrentUrlProviderProxy decorator=");
    localStringBuilder.append(paramObject);
    localStringBuilder.toString();
  }
  
  public String getCurrentUrl()
  {
    Object localObject = this.mDecorator;
    String str = null;
    if (localObject != null)
    {
      localObject = QBDownloadProxy.invokeMethod(localObject, "getCurrentUrl", null, new Object[0]);
      if ((localObject instanceof String)) {
        str = (String)localObject;
      }
      return str;
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\CurrentUrlProviderProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */