package com.tencent.tbs.core.webkit;

public abstract class ServiceWorkerController
{
  public static ServiceWorkerController getInstance()
  {
    return WebViewFactory.getProvider().getServiceWorkerController();
  }
  
  public abstract ServiceWorkerWebSettings getServiceWorkerWebSettings();
  
  public abstract void setServiceWorkerClient(ServiceWorkerClient paramServiceWorkerClient);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\ServiceWorkerController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */