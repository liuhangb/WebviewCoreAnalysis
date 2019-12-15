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
import org.chromium.mojo.system.InvalidHandle;
import org.chromium.mojo.system.MessagePipeHandle;

class InterfaceProvider_Internal
{
  public static final Interface.Manager<InterfaceProvider, InterfaceProvider.Proxy> a = new Interface.Manager()
  {
    public InterfaceProvider_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new InterfaceProvider_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public InterfaceProvider_Internal.Stub a(Core paramAnonymousCore, InterfaceProvider paramAnonymousInterfaceProvider)
    {
      return new InterfaceProvider_Internal.Stub(paramAnonymousCore, paramAnonymousInterfaceProvider);
    }
    
    public InterfaceProvider[] a(int paramAnonymousInt)
    {
      return new InterfaceProvider[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "service_manager::mojom::InterfaceProvider";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class InterfaceProviderGetInterfaceParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
    public String a;
    public MessagePipeHandle a;
    
    public InterfaceProviderGetInterfaceParams()
    {
      this(0);
    }
    
    private InterfaceProviderGetInterfaceParams(int paramInt)
    {
      super(paramInt);
      this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = InvalidHandle.a;
    }
    
    public static InterfaceProviderGetInterfaceParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        InterfaceProviderGetInterfaceParams localInterfaceProviderGetInterfaceParams = new InterfaceProviderGetInterfaceParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localInterfaceProviderGetInterfaceParams.jdField_a_of_type_JavaLangString = paramDecoder.a(8, false);
        }
        if (localDataHeader.b >= 0) {
          localInterfaceProviderGetInterfaceParams.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = paramDecoder.a(16, false);
        }
        return localInterfaceProviderGetInterfaceParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static InterfaceProviderGetInterfaceParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_JavaLangString, 8, false);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle, 16, false);
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
      paramObject = (InterfaceProviderGetInterfaceParams)paramObject;
      if (!BindingsHelper.a(this.jdField_a_of_type_JavaLangString, ((InterfaceProviderGetInterfaceParams)paramObject).jdField_a_of_type_JavaLangString)) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle, ((InterfaceProviderGetInterfaceParams)paramObject).jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle);
    }
    
    public int hashCode()
    {
      return ((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_JavaLangString)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle);
    }
  }
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements InterfaceProvider.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void getInterface(String paramString, MessagePipeHandle paramMessagePipeHandle)
    {
      InterfaceProvider_Internal.InterfaceProviderGetInterfaceParams localInterfaceProviderGetInterfaceParams = new InterfaceProvider_Internal.InterfaceProviderGetInterfaceParams();
      localInterfaceProviderGetInterfaceParams.jdField_a_of_type_JavaLangString = paramString;
      localInterfaceProviderGetInterfaceParams.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = paramMessagePipeHandle;
      a().a().accept(localInterfaceProviderGetInterfaceParams.a(a().a(), new MessageHeader(0)));
    }
  }
  
  static final class Stub
    extends Interface.Stub<InterfaceProvider>
  {
    Stub(Core paramCore, InterfaceProvider paramInterfaceProvider)
    {
      super(paramInterfaceProvider);
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
          paramMessage = InterfaceProvider_Internal.InterfaceProviderGetInterfaceParams.a(paramMessage.a());
          ((InterfaceProvider)a()).getInterface(paramMessage.jdField_a_of_type_JavaLangString, paramMessage.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle);
          return true;
        }
        boolean bool = InterfaceControlMessagesHelper.a(InterfaceProvider_Internal.a, paramMessage);
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
        boolean bool = InterfaceControlMessagesHelper.a(a(), InterfaceProvider_Internal.a, paramMessage, paramMessageReceiver);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\InterfaceProvider_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */