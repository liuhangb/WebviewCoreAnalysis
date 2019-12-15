package org.chromium.service_manager.mojom;

import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;
import org.chromium.mojo.bindings.InterfaceRequest;

public abstract interface ServiceFactory
  extends Interface
{
  public static final Interface.Manager<ServiceFactory, Proxy> a = ServiceFactory_Internal.a;
  
  public abstract void createService(InterfaceRequest<Service> paramInterfaceRequest, String paramString, PidReceiver paramPidReceiver);
  
  public static abstract interface Proxy
    extends Interface.Proxy, ServiceFactory
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\ServiceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */