package org.chromium.device.mojom;

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
import org.chromium.mojo.bindings.SideEffectFreeCloseable;
import org.chromium.mojo.bindings.Struct;
import org.chromium.mojo.system.Core;

class VibrationManager_Internal
{
  public static final Interface.Manager<VibrationManager, VibrationManager.Proxy> a = new Interface.Manager()
  {
    public VibrationManager_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new VibrationManager_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public VibrationManager_Internal.Stub a(Core paramAnonymousCore, VibrationManager paramAnonymousVibrationManager)
    {
      return new VibrationManager_Internal.Stub(paramAnonymousCore, paramAnonymousVibrationManager);
    }
    
    public VibrationManager[] a(int paramAnonymousInt)
    {
      return new VibrationManager[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "device::mojom::VibrationManager";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements VibrationManager.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void cancel(VibrationManager.CancelResponse paramCancelResponse)
    {
      VibrationManager_Internal.VibrationManagerCancelParams localVibrationManagerCancelParams = new VibrationManager_Internal.VibrationManagerCancelParams();
      a().a().acceptWithResponder(localVibrationManagerCancelParams.a(a().a(), new MessageHeader(1, 1, 0L)), new VibrationManager_Internal.VibrationManagerCancelResponseParamsForwardToCallback(paramCancelResponse));
    }
    
    public void vibrate(long paramLong, VibrationManager.VibrateResponse paramVibrateResponse)
    {
      VibrationManager_Internal.VibrationManagerVibrateParams localVibrationManagerVibrateParams = new VibrationManager_Internal.VibrationManagerVibrateParams();
      localVibrationManagerVibrateParams.a = paramLong;
      a().a().acceptWithResponder(localVibrationManagerVibrateParams.a(a().a(), new MessageHeader(0, 1, 0L)), new VibrationManager_Internal.VibrationManagerVibrateResponseParamsForwardToCallback(paramVibrateResponse));
    }
  }
  
  static final class Stub
    extends Interface.Stub<VibrationManager>
  {
    Stub(Core paramCore, VibrationManager paramVibrationManager)
    {
      super(paramVibrationManager);
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
        if (localMessageHeader.b() != -2) {
          return false;
        }
        boolean bool = InterfaceControlMessagesHelper.a(VibrationManager_Internal.a, paramMessage);
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
        localObject = paramMessage.a();
        paramMessage = ((ServiceMessage)localObject).a();
        if (!paramMessage.b(1)) {
          return false;
        }
        switch (paramMessage.b())
        {
        case 1: 
          VibrationManager_Internal.VibrationManagerCancelParams.a(((ServiceMessage)localObject).a());
          ((VibrationManager)a()).cancel(new VibrationManager_Internal.VibrationManagerCancelResponseParamsProxyToResponder(a(), paramMessageReceiver, paramMessage.a()));
          return true;
        }
      }
      catch (DeserializationException paramMessage)
      {
        Object localObject;
        boolean bool;
        System.err.println(paramMessage.toString());
        return false;
      }
      localObject = VibrationManager_Internal.VibrationManagerVibrateParams.a(((ServiceMessage)localObject).a());
      ((VibrationManager)a()).vibrate(((VibrationManager_Internal.VibrationManagerVibrateParams)localObject).a, new VibrationManager_Internal.VibrationManagerVibrateResponseParamsProxyToResponder(a(), paramMessageReceiver, paramMessage.a()));
      return true;
      bool = InterfaceControlMessagesHelper.a(a(), VibrationManager_Internal.a, (ServiceMessage)localObject, paramMessageReceiver);
      return bool;
      return false;
    }
  }
  
  static final class VibrationManagerCancelParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(8, 0) };
    
    public VibrationManagerCancelParams()
    {
      this(0);
    }
    
    private VibrationManagerCancelParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static VibrationManagerCancelParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        VibrationManagerCancelParams localVibrationManagerCancelParams = new VibrationManagerCancelParams(paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader).b);
        return localVibrationManagerCancelParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static VibrationManagerCancelParams a(Message paramMessage)
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
  
  static final class VibrationManagerCancelResponseParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(8, 0) };
    
    public VibrationManagerCancelResponseParams()
    {
      this(0);
    }
    
    private VibrationManagerCancelResponseParams(int paramInt)
    {
      super(paramInt);
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
  
  static class VibrationManagerCancelResponseParamsForwardToCallback
    extends SideEffectFreeCloseable
    implements MessageReceiver
  {
    private final VibrationManager.CancelResponse a;
    
    VibrationManagerCancelResponseParamsForwardToCallback(VibrationManager.CancelResponse paramCancelResponse)
    {
      this.a = paramCancelResponse;
    }
    
    public boolean accept(Message paramMessage)
    {
      try
      {
        if (!paramMessage.a().a().a(1, 2)) {
          return false;
        }
        this.a.call();
        return true;
      }
      catch (DeserializationException paramMessage) {}
      return false;
    }
  }
  
  static class VibrationManagerCancelResponseParamsProxyToResponder
    implements VibrationManager.CancelResponse
  {
    private final long jdField_a_of_type_Long;
    private final MessageReceiver jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
    private final Core jdField_a_of_type_OrgChromiumMojoSystemCore;
    
    VibrationManagerCancelResponseParamsProxyToResponder(Core paramCore, MessageReceiver paramMessageReceiver, long paramLong)
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver = paramMessageReceiver;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void call()
    {
      ServiceMessage localServiceMessage = new VibrationManager_Internal.VibrationManagerCancelResponseParams().a(this.jdField_a_of_type_OrgChromiumMojoSystemCore, new MessageHeader(1, 2, this.jdField_a_of_type_Long));
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver.accept(localServiceMessage);
    }
  }
  
  static final class VibrationManagerVibrateParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public long a;
    
    public VibrationManagerVibrateParams()
    {
      this(0);
    }
    
    private VibrationManagerVibrateParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static VibrationManagerVibrateParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        VibrationManagerVibrateParams localVibrationManagerVibrateParams = new VibrationManagerVibrateParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localVibrationManagerVibrateParams.jdField_a_of_type_Long = paramDecoder.a(8);
        }
        return localVibrationManagerVibrateParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static VibrationManagerVibrateParams a(Message paramMessage)
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
      paramObject = (VibrationManagerVibrateParams)paramObject;
      return this.jdField_a_of_type_Long == ((VibrationManagerVibrateParams)paramObject).jdField_a_of_type_Long;
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_Long);
    }
  }
  
  static final class VibrationManagerVibrateResponseParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(8, 0) };
    
    public VibrationManagerVibrateResponseParams()
    {
      this(0);
    }
    
    private VibrationManagerVibrateResponseParams(int paramInt)
    {
      super(paramInt);
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
  
  static class VibrationManagerVibrateResponseParamsForwardToCallback
    extends SideEffectFreeCloseable
    implements MessageReceiver
  {
    private final VibrationManager.VibrateResponse a;
    
    VibrationManagerVibrateResponseParamsForwardToCallback(VibrationManager.VibrateResponse paramVibrateResponse)
    {
      this.a = paramVibrateResponse;
    }
    
    public boolean accept(Message paramMessage)
    {
      try
      {
        if (!paramMessage.a().a().a(0, 2)) {
          return false;
        }
        this.a.call();
        return true;
      }
      catch (DeserializationException paramMessage) {}
      return false;
    }
  }
  
  static class VibrationManagerVibrateResponseParamsProxyToResponder
    implements VibrationManager.VibrateResponse
  {
    private final long jdField_a_of_type_Long;
    private final MessageReceiver jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
    private final Core jdField_a_of_type_OrgChromiumMojoSystemCore;
    
    VibrationManagerVibrateResponseParamsProxyToResponder(Core paramCore, MessageReceiver paramMessageReceiver, long paramLong)
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver = paramMessageReceiver;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void call()
    {
      ServiceMessage localServiceMessage = new VibrationManager_Internal.VibrationManagerVibrateResponseParams().a(this.jdField_a_of_type_OrgChromiumMojoSystemCore, new MessageHeader(0, 2, this.jdField_a_of_type_Long));
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver.accept(localServiceMessage);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\mojom\VibrationManager_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */