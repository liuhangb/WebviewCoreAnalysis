package org.chromium.service_manager.mojom;

import java.io.PrintStream;
import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.DeserializationException;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Interface.AbstractProxy;
import org.chromium.mojo.bindings.Interface.AbstractProxy.HandlerImpl;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Stub;
import org.chromium.mojo.bindings.InterfaceControlMessagesHelper;
import org.chromium.mojo.bindings.Message;
import org.chromium.mojo.bindings.MessageHeader;
import org.chromium.mojo.bindings.MessageReceiver;
import org.chromium.mojo.bindings.MessageReceiverWithResponder;
import org.chromium.mojo.bindings.ServiceMessage;
import org.chromium.mojo.bindings.Struct;
import org.chromium.mojo.system.Core;

class ServiceManager_Internal
{
  public static final Interface.Manager<ServiceManager, ServiceManager.Proxy> a = new Interface.Manager()
  {
    public ServiceManager_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new ServiceManager_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public ServiceManager_Internal.Stub a(Core paramAnonymousCore, ServiceManager paramAnonymousServiceManager)
    {
      return new ServiceManager_Internal.Stub(paramAnonymousCore, paramAnonymousServiceManager);
    }
    
    public ServiceManager[] a(int paramAnonymousInt)
    {
      return new ServiceManager[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "service_manager::mojom::ServiceManager";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements ServiceManager.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void addListener(ServiceManagerListener paramServiceManagerListener)
    {
      ServiceManager_Internal.ServiceManagerAddListenerParams localServiceManagerAddListenerParams = new ServiceManager_Internal.ServiceManagerAddListenerParams();
      localServiceManagerAddListenerParams.a = paramServiceManagerListener;
      a().a().accept(localServiceManagerAddListenerParams.a(a().a(), new MessageHeader(0)));
    }
  }
  
  static final class ServiceManagerAddListenerParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public ServiceManagerListener a;
    
    public ServiceManagerAddListenerParams()
    {
      this(0);
    }
    
    private ServiceManagerAddListenerParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ServiceManagerAddListenerParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ServiceManagerAddListenerParams localServiceManagerAddListenerParams = new ServiceManagerAddListenerParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localServiceManagerAddListenerParams.jdField_a_of_type_OrgChromiumService_managerMojomServiceManagerListener = ((ServiceManagerListener)paramDecoder.a(8, false, ServiceManagerListener.a));
        }
        return localServiceManagerAddListenerParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ServiceManagerAddListenerParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_OrgChromiumService_managerMojomServiceManagerListener, 8, false, ServiceManagerListener.a);
    }
    
    public boolean equals(Object paramObject)
    {
      if (paramObject == this) {
        return true;
      }
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (ServiceManagerAddListenerParams)paramObject;
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomServiceManagerListener, ((ServiceManagerAddListenerParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomServiceManagerListener);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomServiceManagerListener);
    }
  }
  
  static final class Stub
    extends Interface.Stub<ServiceManager>
  {
    Stub(Core paramCore, ServiceManager paramServiceManager)
    {
      super(paramServiceManager);
    }
    
    public boolean accept(Message paramMessage)
    {
      try
      {
        paramMessage = paramMessage.a();
        MessageHeader localMessageHeader = paramMessage.a();
        if (!localMessageHeader.b(0)) {
          return false;
        }
        int i = localMessageHeader.b();
        if (i != -2)
        {
          if (i != 0) {
            return false;
          }
          paramMessage = ServiceManager_Internal.ServiceManagerAddListenerParams.a(paramMessage.a());
          ((ServiceManager)a()).addListener(paramMessage.a);
          return true;
        }
        boolean bool = InterfaceControlMessagesHelper.a(ServiceManager_Internal.a, paramMessage);
        return bool;
      }
      catch (DeserializationException paramMessage)
      {
        System.err.println(paramMessage.toString());
      }
      return false;
    }
    
    public boolean acceptWithResponder(Message paramMessage, MessageReceiver paramMessageReceiver)
    {
      try
      {
        paramMessage = paramMessage.a();
        MessageHeader localMessageHeader = paramMessage.a();
        if (!localMessageHeader.b(1)) {
          return false;
        }
        if (localMessageHeader.b() != -1) {
          return false;
        }
        boolean bool = InterfaceControlMessagesHelper.a(a(), ServiceManager_Internal.a, paramMessage, paramMessageReceiver);
        return bool;
      }
      catch (DeserializationException paramMessage)
      {
        System.err.println(paramMessage.toString());
      }
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\ServiceManager_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */