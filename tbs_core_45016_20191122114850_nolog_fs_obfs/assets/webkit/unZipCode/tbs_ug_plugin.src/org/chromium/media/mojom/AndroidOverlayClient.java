package org.chromium.media.mojom;

import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;

public abstract interface AndroidOverlayClient
  extends Interface
{
  public static final Interface.Manager<AndroidOverlayClient, Proxy> a = AndroidOverlayClient_Internal.a;
  
  public abstract void onDestroyed();
  
  public abstract void onPowerEfficientState(boolean paramBoolean);
  
  public abstract void onSurfaceReady(long paramLong);
  
  public static abstract interface Proxy
    extends AndroidOverlayClient, Interface.Proxy
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\mojom\AndroidOverlayClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */