package org.chromium.media.mojom;

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

class AndroidOverlayClient_Internal
{
  public static final Interface.Manager<AndroidOverlayClient, AndroidOverlayClient.Proxy> a = new Interface.Manager()
  {
    public AndroidOverlayClient_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new AndroidOverlayClient_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public AndroidOverlayClient_Internal.Stub a(Core paramAnonymousCore, AndroidOverlayClient paramAnonymousAndroidOverlayClient)
    {
      return new AndroidOverlayClient_Internal.Stub(paramAnonymousCore, paramAnonymousAndroidOverlayClient);
    }
    
    public AndroidOverlayClient[] a(int paramAnonymousInt)
    {
      return new AndroidOverlayClient[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "media::mojom::AndroidOverlayClient";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class AndroidOverlayClientOnDestroyedParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(8, 0) };
    
    public AndroidOverlayClientOnDestroyedParams()
    {
      this(0);
    }
    
    private AndroidOverlayClientOnDestroyedParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static AndroidOverlayClientOnDestroyedParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        AndroidOverlayClientOnDestroyedParams localAndroidOverlayClientOnDestroyedParams = new AndroidOverlayClientOnDestroyedParams(paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader).b);
        return localAndroidOverlayClientOnDestroyedParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static AndroidOverlayClientOnDestroyedParams a(Message paramMessage)
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
  
  static final class AndroidOverlayClientOnPowerEfficientStateParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public boolean a;
    
    public AndroidOverlayClientOnPowerEfficientStateParams()
    {
      this(0);
    }
    
    private AndroidOverlayClientOnPowerEfficientStateParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static AndroidOverlayClientOnPowerEfficientStateParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        AndroidOverlayClientOnPowerEfficientStateParams localAndroidOverlayClientOnPowerEfficientStateParams = new AndroidOverlayClientOnPowerEfficientStateParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localAndroidOverlayClientOnPowerEfficientStateParams.jdField_a_of_type_Boolean = paramDecoder.a(8, 0);
        }
        return localAndroidOverlayClientOnPowerEfficientStateParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static AndroidOverlayClientOnPowerEfficientStateParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_Boolean, 8, 0);
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
      paramObject = (AndroidOverlayClientOnPowerEfficientStateParams)paramObject;
      return this.jdField_a_of_type_Boolean == ((AndroidOverlayClientOnPowerEfficientStateParams)paramObject).jdField_a_of_type_Boolean;
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_Boolean);
    }
  }
  
  static final class AndroidOverlayClientOnSurfaceReadyParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public long a;
    
    public AndroidOverlayClientOnSurfaceReadyParams()
    {
      this(0);
    }
    
    private AndroidOverlayClientOnSurfaceReadyParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static AndroidOverlayClientOnSurfaceReadyParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        AndroidOverlayClientOnSurfaceReadyParams localAndroidOverlayClientOnSurfaceReadyParams = new AndroidOverlayClientOnSurfaceReadyParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localAndroidOverlayClientOnSurfaceReadyParams.jdField_a_of_type_Long = paramDecoder.a(8);
        }
        return localAndroidOverlayClientOnSurfaceReadyParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static AndroidOverlayClientOnSurfaceReadyParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_Long, 8);
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
      paramObject = (AndroidOverlayClientOnSurfaceReadyParams)paramObject;
      return this.jdField_a_of_type_Long == ((AndroidOverlayClientOnSurfaceReadyParams)paramObject).jdField_a_of_type_Long;
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_Long);
    }
  }
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements AndroidOverlayClient.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void onDestroyed()
    {
      AndroidOverlayClient_Internal.AndroidOverlayClientOnDestroyedParams localAndroidOverlayClientOnDestroyedParams = new AndroidOverlayClient_Internal.AndroidOverlayClientOnDestroyedParams();
      a().a().accept(localAndroidOverlayClientOnDestroyedParams.a(a().a(), new MessageHeader(1)));
    }
    
    public void onPowerEfficientState(boolean paramBoolean)
    {
      AndroidOverlayClient_Internal.AndroidOverlayClientOnPowerEfficientStateParams localAndroidOverlayClientOnPowerEfficientStateParams = new AndroidOverlayClient_Internal.AndroidOverlayClientOnPowerEfficientStateParams();
      localAndroidOverlayClientOnPowerEfficientStateParams.a = paramBoolean;
      a().a().accept(localAndroidOverlayClientOnPowerEfficientStateParams.a(a().a(), new MessageHeader(2)));
    }
    
    public void onSurfaceReady(long paramLong)
    {
      AndroidOverlayClient_Internal.AndroidOverlayClientOnSurfaceReadyParams localAndroidOverlayClientOnSurfaceReadyParams = new AndroidOverlayClient_Internal.AndroidOverlayClientOnSurfaceReadyParams();
      localAndroidOverlayClientOnSurfaceReadyParams.a = paramLong;
      a().a().accept(localAndroidOverlayClientOnSurfaceReadyParams.a(a().a(), new MessageHeader(0)));
    }
  }
  
  static final class Stub
    extends Interface.Stub<AndroidOverlayClient>
  {
    Stub(Core paramCore, AndroidOverlayClient paramAndroidOverlayClient)
    {
      super(paramAndroidOverlayClient);
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
        if (i != -2) {}
        switch (i)
        {
        case 2: 
          paramMessage = AndroidOverlayClient_Internal.AndroidOverlayClientOnPowerEfficientStateParams.a(paramMessage.a());
          ((AndroidOverlayClient)a()).onPowerEfficientState(paramMessage.a);
          return true;
        case 1: 
          AndroidOverlayClient_Internal.AndroidOverlayClientOnDestroyedParams.a(paramMessage.a());
          ((AndroidOverlayClient)a()).onDestroyed();
          return true;
        case 0: 
          paramMessage = AndroidOverlayClient_Internal.AndroidOverlayClientOnSurfaceReadyParams.a(paramMessage.a());
          ((AndroidOverlayClient)a()).onSurfaceReady(paramMessage.a);
          return true;
          boolean bool = InterfaceControlMessagesHelper.a(AndroidOverlayClient_Internal.a, paramMessage);
          return bool;
        }
      }
      catch (DeserializationException paramMessage)
      {
        System.err.println(paramMessage.toString());
        return false;
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
        boolean bool = InterfaceControlMessagesHelper.a(a(), AndroidOverlayClient_Internal.a, paramMessage, paramMessageReceiver);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\mojom\AndroidOverlayClient_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */