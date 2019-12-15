package org.chromium.media.mojom;

import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;
import org.chromium.mojo.bindings.InterfaceRequest;

public abstract interface AndroidOverlayProvider
  extends Interface
{
  public static final Interface.Manager<AndroidOverlayProvider, Proxy> a = AndroidOverlayProvider_Internal.a;
  
  public abstract void createOverlay(InterfaceRequest<AndroidOverlay> paramInterfaceRequest, AndroidOverlayClient paramAndroidOverlayClient, AndroidOverlayConfig paramAndroidOverlayConfig);
  
  public static abstract interface Proxy
    extends AndroidOverlayProvider, Interface.Proxy
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\mojom\AndroidOverlayProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */