package org.chromium.service_manager.mojom;

import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;

public abstract interface ServiceManagerListener
  extends Interface
{
  public static final Interface.Manager<ServiceManagerListener, Proxy> a = ServiceManagerListener_Internal.a;
  
  public abstract void onInit(RunningServiceInfo[] paramArrayOfRunningServiceInfo);
  
  public abstract void onServiceCreated(RunningServiceInfo paramRunningServiceInfo);
  
  public abstract void onServiceFailedToStart(Identity paramIdentity);
  
  public abstract void onServicePidReceived(Identity paramIdentity, int paramInt);
  
  public abstract void onServiceStarted(Identity paramIdentity, int paramInt);
  
  public abstract void onServiceStopped(Identity paramIdentity);
  
  public static abstract interface Proxy
    extends Interface.Proxy, ServiceManagerListener
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\ServiceManagerListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */