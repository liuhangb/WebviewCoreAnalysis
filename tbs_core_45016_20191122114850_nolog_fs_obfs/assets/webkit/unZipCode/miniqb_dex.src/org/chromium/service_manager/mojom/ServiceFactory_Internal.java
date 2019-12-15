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
import org.chromium.mojo.bindings.InterfaceRequest;
import org.chromium.mojo.bindings.Message;
import org.chromium.mojo.bindings.MessageHeader;
import org.chromium.mojo.bindings.MessageReceiver;
import org.chromium.mojo.bindings.MessageReceiverWithResponder;
import org.chromium.mojo.bindings.ServiceMessage;
import org.chromium.mojo.bindings.Struct;
import org.chromium.mojo.system.Core;

class ServiceFactory_Internal
{
  public static final Interface.Manager<ServiceFactory, ServiceFactory.Proxy> a = new Interface.Manager()
  {
    public ServiceFactory_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new ServiceFactory_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public ServiceFactory_Internal.Stub a(Core paramAnonymousCore, ServiceFactory paramAnonymousServiceFactory)
    {
      return new ServiceFactory_Internal.Stub(paramAnonymousCore, paramAnonymousServiceFactory);
    }
    
    public ServiceFactory[] a(int paramAnonymousInt)
    {
      return new ServiceFactory[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "service_manager::mojom::ServiceFactory";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements ServiceFactory.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void createService(InterfaceRequest<Service> paramInterfaceRequest, String paramString, PidReceiver paramPidReceiver)
    {
      ServiceFactory_Internal.ServiceFactoryCreateServiceParams localServiceFactoryCreateServiceParams = new ServiceFactory_Internal.ServiceFactoryCreateServiceParams();
      localServiceFactoryCreateServiceParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramInterfaceRequest;
      localServiceFactoryCreateServiceParams.jdField_a_of_type_JavaLangString = paramString;
      localServiceFactoryCreateServiceParams.jdField_a_of_type_OrgChromiumService_managerMojomPidReceiver = paramPidReceiver;
      a().a().accept(localServiceFactoryCreateServiceParams.a(a().a(), new MessageHeader(0)));
    }
  }
  
  static final class ServiceFactoryCreateServiceParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(32, 0) };
    public String a;
    public InterfaceRequest<Service> a;
    public PidReceiver a;
    
    public ServiceFactoryCreateServiceParams()
    {
      this(0);
    }
    
    private ServiceFactoryCreateServiceParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ServiceFactoryCreateServiceParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ServiceFactoryCreateServiceParams localServiceFactoryCreateServiceParams = new ServiceFactoryCreateServiceParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localServiceFactoryCreateServiceParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramDecoder.a(8, false);
        }
        if (localDataHeader.b >= 0) {
          localServiceFactoryCreateServiceParams.jdField_a_of_type_JavaLangString = paramDecoder.a(16, false);
        }
        if (localDataHeader.b >= 0) {
          localServiceFactoryCreateServiceParams.jdField_a_of_type_OrgChromiumService_managerMojomPidReceiver = ((PidReceiver)paramDecoder.a(24, false, PidReceiver.a));
        }
        return localServiceFactoryCreateServiceParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ServiceFactoryCreateServiceParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, 8, false);
      paramEncoder.a(this.jdField_a_of_type_JavaLangString, 16, false);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomPidReceiver, 24, false, PidReceiver.a);
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
      paramObject = (ServiceFactoryCreateServiceParams)paramObject;
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, ((ServiceFactoryCreateServiceParams)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest)) {
        return false;
      }
      if (!BindingsHelper.a(this.jdField_a_of_type_JavaLangString, ((ServiceFactoryCreateServiceParams)paramObject).jdField_a_of_type_JavaLangString)) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomPidReceiver, ((ServiceFactoryCreateServiceParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomPidReceiver);
    }
    
    public int hashCode()
    {
      return (((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest)) * 31 + BindingsHelper.a(this.jdField_a_of_type_JavaLangString)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomPidReceiver);
    }
  }
  
  static final class Stub
    extends Interface.Stub<ServiceFactory>
  {
    Stub(Core paramCore, ServiceFactory paramServiceFactory)
    {
      super(paramServiceFactory);
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
          paramMessage = ServiceFactory_Internal.ServiceFactoryCreateServiceParams.a(paramMessage.a());
          ((ServiceFactory)a()).createService(paramMessage.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, paramMessage.jdField_a_of_type_JavaLangString, paramMessage.jdField_a_of_type_OrgChromiumService_managerMojomPidReceiver);
          return true;
        }
        boolean bool = InterfaceControlMessagesHelper.a(ServiceFactory_Internal.a, paramMessage);
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
        boolean bool = InterfaceControlMessagesHelper.a(a(), ServiceFactory_Internal.a, paramMessage, paramMessageReceiver);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\ServiceFactory_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */