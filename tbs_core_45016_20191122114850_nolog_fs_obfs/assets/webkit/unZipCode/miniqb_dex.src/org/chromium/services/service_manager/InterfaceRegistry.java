package org.chromium.services.service_manager;

import java.util.HashMap;
import java.util.Map;
import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.mojo.system.MojoException;
import org.chromium.service_manager.mojom.InterfaceProvider;

public class InterfaceRegistry
  implements InterfaceProvider
{
  private final Map<String, InterfaceBinder> a = new HashMap();
  
  public static InterfaceRegistry a(MessagePipeHandle paramMessagePipeHandle)
  {
    InterfaceRegistry localInterfaceRegistry = new InterfaceRegistry();
    InterfaceProvider.a.a(localInterfaceRegistry, paramMessagePipeHandle);
    return localInterfaceRegistry;
  }
  
  public <I extends Interface> void a(Interface.Manager<I, ? extends Interface.Proxy> paramManager, InterfaceFactory<I> paramInterfaceFactory)
  {
    this.a.put(paramManager.getName(), new InterfaceBinder(paramManager, paramInterfaceFactory));
  }
  
  public void close()
  {
    this.a.clear();
  }
  
  public void getInterface(String paramString, MessagePipeHandle paramMessagePipeHandle)
  {
    paramString = (InterfaceBinder)this.a.get(paramString);
    if (paramString == null) {
      return;
    }
    paramString.a(paramMessagePipeHandle);
  }
  
  public void onConnectionError(MojoException paramMojoException)
  {
    close();
  }
  
  private static class InterfaceBinder<I extends Interface>
  {
    private Interface.Manager<I, ? extends Interface.Proxy> jdField_a_of_type_OrgChromiumMojoBindingsInterface$Manager;
    private InterfaceFactory<I> jdField_a_of_type_OrgChromiumServicesService_managerInterfaceFactory;
    
    public InterfaceBinder(Interface.Manager<I, ? extends Interface.Proxy> paramManager, InterfaceFactory<I> paramInterfaceFactory)
    {
      this.jdField_a_of_type_OrgChromiumMojoBindingsInterface$Manager = paramManager;
      this.jdField_a_of_type_OrgChromiumServicesService_managerInterfaceFactory = paramInterfaceFactory;
    }
    
    public void a(MessagePipeHandle paramMessagePipeHandle)
    {
      Interface localInterface = this.jdField_a_of_type_OrgChromiumServicesService_managerInterfaceFactory.createImpl();
      if (localInterface == null)
      {
        paramMessagePipeHandle.close();
        return;
      }
      this.jdField_a_of_type_OrgChromiumMojoBindingsInterface$Manager.a(localInterface, paramMessagePipeHandle);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\services\service_manager\InterfaceRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */