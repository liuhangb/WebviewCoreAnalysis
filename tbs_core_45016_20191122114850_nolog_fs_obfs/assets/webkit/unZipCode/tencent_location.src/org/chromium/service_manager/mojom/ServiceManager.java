package org.chromium.service_manager.mojom;

import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;

public abstract interface ServiceManager
  extends Interface
{
  public static final Interface.Manager<ServiceManager, Proxy> a = ServiceManager_Internal.a;
  
  public abstract void addListener(ServiceManagerListener paramServiceManagerListener);
  
  public static abstract interface Proxy
    extends Interface.Proxy, ServiceManager
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\ServiceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */