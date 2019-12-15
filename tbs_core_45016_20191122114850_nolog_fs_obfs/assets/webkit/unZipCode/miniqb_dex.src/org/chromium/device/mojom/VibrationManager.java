package org.chromium.device.mojom;

import org.chromium.mojo.bindings.Callbacks.Callback0;
import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;

public abstract interface VibrationManager
  extends Interface
{
  public static final Interface.Manager<VibrationManager, Proxy> a = VibrationManager_Internal.a;
  
  public abstract void cancel(CancelResponse paramCancelResponse);
  
  public abstract void vibrate(long paramLong, VibrateResponse paramVibrateResponse);
  
  public static abstract interface CancelResponse
    extends Callbacks.Callback0
  {}
  
  public static abstract interface Proxy
    extends VibrationManager, Interface.Proxy
  {}
  
  public static abstract interface VibrateResponse
    extends Callbacks.Callback0
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\mojom\VibrationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */