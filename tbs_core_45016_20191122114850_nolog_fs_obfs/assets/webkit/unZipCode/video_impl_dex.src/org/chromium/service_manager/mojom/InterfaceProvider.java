package org.chromium.service_manager.mojom;

import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;
import org.chromium.mojo.system.MessagePipeHandle;

public abstract interface InterfaceProvider
  extends Interface
{
  public static final Interface.Manager<InterfaceProvider, Proxy> a = InterfaceProvider_Internal.a;
  
  public abstract void getInterface(String paramString, MessagePipeHandle paramMessagePipeHandle);
  
  public static abstract interface Proxy
    extends Interface.Proxy, InterfaceProvider
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\InterfaceProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */