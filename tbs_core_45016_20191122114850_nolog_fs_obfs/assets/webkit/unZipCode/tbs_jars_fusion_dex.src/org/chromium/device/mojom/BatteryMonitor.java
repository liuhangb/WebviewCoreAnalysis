package org.chromium.device.mojom;

import org.chromium.mojo.bindings.Callbacks.Callback1;
import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;

public abstract interface BatteryMonitor
  extends Interface
{
  public static final Interface.Manager<BatteryMonitor, Proxy> a = BatteryMonitor_Internal.a;
  
  public abstract void queryNextStatus(QueryNextStatusResponse paramQueryNextStatusResponse);
  
  public static abstract interface Proxy
    extends BatteryMonitor, Interface.Proxy
  {}
  
  public static abstract interface QueryNextStatusResponse
    extends Callbacks.Callback1<BatteryStatus>
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\mojom\BatteryMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */