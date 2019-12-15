package org.chromium.services.service_manager;

import org.chromium.mojo.bindings.ConnectionErrorHandler;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy.Handler;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.mojo.system.MojoException;
import org.chromium.service_manager.mojom.InterfaceProvider.Proxy;

public class InterfaceProvider
  implements ConnectionErrorHandler
{
  private Core jdField_a_of_type_OrgChromiumMojoSystemCore;
  private InterfaceProvider.Proxy jdField_a_of_type_OrgChromiumService_managerMojomInterfaceProvider$Proxy;
  
  public InterfaceProvider(MessagePipeHandle paramMessagePipeHandle)
  {
    this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramMessagePipeHandle.getCore();
    this.jdField_a_of_type_OrgChromiumService_managerMojomInterfaceProvider$Proxy = ((InterfaceProvider.Proxy)org.chromium.service_manager.mojom.InterfaceProvider.a.attachProxy(paramMessagePipeHandle, 0));
    this.jdField_a_of_type_OrgChromiumService_managerMojomInterfaceProvider$Proxy.getProxyHandler().setErrorHandler(this);
  }
  
  public void onConnectionError(MojoException paramMojoException)
  {
    this.jdField_a_of_type_OrgChromiumService_managerMojomInterfaceProvider$Proxy.close();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\services\service_manager\InterfaceProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */