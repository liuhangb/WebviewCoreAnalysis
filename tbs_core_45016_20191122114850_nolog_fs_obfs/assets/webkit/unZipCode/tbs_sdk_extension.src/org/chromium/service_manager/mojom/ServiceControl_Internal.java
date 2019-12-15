package org.chromium.service_manager.mojom;

import java.io.PrintStream;
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

class ServiceControl_Internal
{
  public static final Interface.Manager<ServiceControl, ServiceControl.Proxy> a = new Interface.Manager()
  {
    public ServiceControl_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new ServiceControl_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public ServiceControl_Internal.Stub a(Core paramAnonymousCore, ServiceControl paramAnonymousServiceControl)
    {
      return new ServiceControl_Internal.Stub(paramAnonymousCore, paramAnonymousServiceControl);
    }
    
    public ServiceControl[] a(int paramAnonymousInt)
    {
      return new ServiceControl[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "service_manager::mojom::ServiceControl";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements ServiceControl.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void requestQuit()
    {
      ServiceControl_Internal.ServiceControlRequestQuitParams localServiceControlRequestQuitParams = new ServiceControl_Internal.ServiceControlRequestQuitParams();
      a().a().accept(localServiceControlRequestQuitParams.a(a().a(), new MessageHeader(0)));
    }
  }
  
  static final class ServiceControlRequestQuitParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(8, 0) };
    
    public ServiceControlRequestQuitParams()
    {
      this(0);
    }
    
    private ServiceControlRequestQuitParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ServiceControlRequestQuitParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        ServiceControlRequestQuitParams localServiceControlRequestQuitParams = new ServiceControlRequestQuitParams(paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader).b);
        return localServiceControlRequestQuitParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ServiceControlRequestQuitParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    }
    
    public boolean equals(Object paramObject)
    {
      if (paramObject == this) {
        return true;
      }
      if (paramObject == null) {
        return false;
      }
      return getClass() == paramObject.getClass();
    }
    
    public int hashCode()
    {
      return getClass().hashCode() + 31;
    }
  }
  
  static final class Stub
    extends Interface.Stub<ServiceControl>
  {
    Stub(Core paramCore, ServiceControl paramServiceControl)
    {
      super(paramServiceControl);
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
          ServiceControl_Internal.ServiceControlRequestQuitParams.a(paramMessage.a());
          ((ServiceControl)a()).requestQuit();
          return true;
        }
        boolean bool = InterfaceControlMessagesHelper.a(ServiceControl_Internal.a, paramMessage);
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
        boolean bool = InterfaceControlMessagesHelper.a(a(), ServiceControl_Internal.a, paramMessage, paramMessageReceiver);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\ServiceControl_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */