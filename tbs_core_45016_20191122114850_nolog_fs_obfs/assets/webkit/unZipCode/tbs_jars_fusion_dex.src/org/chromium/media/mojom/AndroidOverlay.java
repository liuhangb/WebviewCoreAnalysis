package org.chromium.media.mojom;

import org.chromium.gfx.mojom.Rect;
import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;

public abstract interface AndroidOverlay
  extends Interface
{
  public static final Interface.Manager<AndroidOverlay, Proxy> a = AndroidOverlay_Internal.a;
  
  public abstract void scheduleLayout(Rect paramRect);
  
  public static abstract interface Proxy
    extends AndroidOverlay, Interface.Proxy
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\mojom\AndroidOverlay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */