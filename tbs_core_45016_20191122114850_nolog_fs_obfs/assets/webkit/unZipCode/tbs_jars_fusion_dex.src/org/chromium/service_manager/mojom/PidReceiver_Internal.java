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

class PidReceiver_Internal
{
  public static final Interface.Manager<PidReceiver, PidReceiver.Proxy> a = new Interface.Manager()
  {
    public PidReceiver_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new PidReceiver_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public PidReceiver_Internal.Stub a(Core paramAnonymousCore, PidReceiver paramAnonymousPidReceiver)
    {
      return new PidReceiver_Internal.Stub(paramAnonymousCore, paramAnonymousPidReceiver);
    }
    
    public PidReceiver[] a(int paramAnonymousInt)
    {
      return new PidReceiver[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "service_manager::mojom::PIDReceiver";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class PidReceiverSetPidParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public int a;
    
    public PidReceiverSetPidParams()
    {
      this(0);
    }
    
    private PidReceiverSetPidParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static PidReceiverSetPidParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        PidReceiverSetPidParams localPidReceiverSetPidParams = new PidReceiverSetPidParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localPidReceiverSetPidParams.jdField_a_of_type_Int = paramDecoder.a(8);
        }
        return localPidReceiverSetPidParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static PidReceiverSetPidParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_Int, 8);
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
      paramObject = (PidReceiverSetPidParams)paramObject;
      return this.jdField_a_of_type_Int == ((PidReceiverSetPidParams)paramObject).jdField_a_of_type_Int;
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int);
    }
  }
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements PidReceiver.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void setPid(int paramInt)
    {
      PidReceiver_Internal.PidReceiverSetPidParams localPidReceiverSetPidParams = new PidReceiver_Internal.PidReceiverSetPidParams();
      localPidReceiverSetPidParams.a = paramInt;
      a().a().accept(localPidReceiverSetPidParams.a(a().a(), new MessageHeader(0)));
    }
  }
  
  static final class Stub
    extends Interface.Stub<PidReceiver>
  {
    Stub(Core paramCore, PidReceiver paramPidReceiver)
    {
      super(paramPidReceiver);
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
          paramMessage = PidReceiver_Internal.PidReceiverSetPidParams.a(paramMessage.a());
          ((PidReceiver)a()).setPid(paramMessage.a);
          return true;
        }
        boolean bool = InterfaceControlMessagesHelper.a(PidReceiver_Internal.a, paramMessage);
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
        boolean bool = InterfaceControlMessagesHelper.a(a(), PidReceiver_Internal.a, paramMessage, paramMessageReceiver);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\PidReceiver_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */