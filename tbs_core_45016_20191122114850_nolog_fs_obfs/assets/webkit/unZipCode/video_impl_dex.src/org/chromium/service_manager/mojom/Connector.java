package org.chromium.service_manager.mojom;

import org.chromium.mojo.bindings.Callbacks.Callback2;
import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;
import org.chromium.mojo.bindings.InterfaceRequest;
import org.chromium.mojo.system.MessagePipeHandle;

public abstract interface Connector
  extends Interface
{
  public static final Interface.Manager<Connector, Proxy> a = Connector_Internal.a;
  
  public abstract void bindInterface(Identity paramIdentity, String paramString, MessagePipeHandle paramMessagePipeHandle, BindInterfaceResponse paramBindInterfaceResponse);
  
  public abstract void clone(InterfaceRequest<Connector> paramInterfaceRequest);
  
  public abstract void filterInterfaces(String paramString, Identity paramIdentity, InterfaceRequest<InterfaceProvider> paramInterfaceRequest, InterfaceProvider paramInterfaceProvider);
  
  public abstract void queryService(Identity paramIdentity, QueryServiceResponse paramQueryServiceResponse);
  
  public abstract void startService(Identity paramIdentity, StartServiceResponse paramStartServiceResponse);
  
  public abstract void startServiceWithProcess(Identity paramIdentity, MessagePipeHandle paramMessagePipeHandle, InterfaceRequest<PidReceiver> paramInterfaceRequest, StartServiceWithProcessResponse paramStartServiceWithProcessResponse);
  
  public static abstract interface BindInterfaceResponse
    extends Callbacks.Callback2<Integer, Identity>
  {}
  
  public static abstract interface Proxy
    extends Interface.Proxy, Connector
  {}
  
  public static abstract interface QueryServiceResponse
    extends Callbacks.Callback2<Integer, String>
  {}
  
  public static abstract interface StartServiceResponse
    extends Callbacks.Callback2<Integer, Identity>
  {}
  
  public static abstract interface StartServiceWithProcessResponse
    extends Callbacks.Callback2<Integer, Identity>
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\Connector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */