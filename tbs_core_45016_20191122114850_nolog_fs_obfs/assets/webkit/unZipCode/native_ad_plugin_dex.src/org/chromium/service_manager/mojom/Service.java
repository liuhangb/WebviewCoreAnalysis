package org.chromium.service_manager.mojom;

import org.chromium.mojo.bindings.AssociatedInterfaceRequestNotSupported;
import org.chromium.mojo.bindings.Callbacks.Callback0;
import org.chromium.mojo.bindings.Callbacks.Callback2;
import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;
import org.chromium.mojo.bindings.InterfaceRequest;
import org.chromium.mojo.system.MessagePipeHandle;

public abstract interface Service
  extends Interface
{
  public static final Interface.Manager<Service, Proxy> a = Service_Internal.a;
  
  public abstract void onBindInterface(BindSourceInfo paramBindSourceInfo, String paramString, MessagePipeHandle paramMessagePipeHandle, OnBindInterfaceResponse paramOnBindInterfaceResponse);
  
  public abstract void onStart(Identity paramIdentity, OnStartResponse paramOnStartResponse);
  
  public static abstract interface OnBindInterfaceResponse
    extends Callbacks.Callback0
  {}
  
  public static abstract interface OnStartResponse
    extends Callbacks.Callback2<InterfaceRequest<Connector>, AssociatedInterfaceRequestNotSupported>
  {}
  
  public static abstract interface Proxy
    extends Interface.Proxy, Service
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\Service.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */