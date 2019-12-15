package org.chromium.service_manager.mojom;

import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;

public abstract interface PidReceiver
  extends Interface
{
  public static final Interface.Manager<PidReceiver, Proxy> a = PidReceiver_Internal.a;
  
  public abstract void setPid(int paramInt);
  
  public static abstract interface Proxy
    extends Interface.Proxy, PidReceiver
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\PidReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */