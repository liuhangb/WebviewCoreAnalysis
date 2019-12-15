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

class BatteryMonitor_Internal
{
  public static final Interface.Manager<BatteryMonitor, BatteryMonitor.Proxy> a = new Interface.Manager()
  {
    public BatteryMonitor_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new BatteryMonitor_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public BatteryMonitor_Internal.Stub a(Core paramAnonymousCore, BatteryMonitor paramAnonymousBatteryMonitor)
    {
      return new BatteryMonitor_Internal.Stub(paramAnonymousCore, paramAnonymousBatteryMonitor);
    }
    
    public BatteryMonitor[] a(int paramAnonymousInt)
    {
      return new BatteryMonitor[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "device::mojom::BatteryMonitor";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class BatteryMonitorQueryNextStatusParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(8, 0) };
    
    public BatteryMonitorQueryNextStatusParams()
    {
      this(0);
    }
    
    private BatteryMonitorQueryNextStatusParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static BatteryMonitorQueryNextStatusParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        BatteryMonitorQueryNextStatusParams localBatteryMonitorQueryNextStatusParams = new BatteryMonitorQueryNextStatusParams(paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader).b);
        return localBatteryMonitorQueryNextStatusParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static BatteryMonitorQueryNextStatusParams a(Message paramMessage)
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
  
  static final class BatteryMonitorQueryNextStatusResponseParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public BatteryStatus a;
    
    public BatteryMonitorQueryNextStatusResponseParams()
    {
      this(0);
    }
    
    private BatteryMonitorQueryNextStatusResponseParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static BatteryMonitorQueryNextStatusResponseParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        BatteryMonitorQueryNextStatusResponseParams localBatteryMonitorQueryNextStatusResponseParams = new BatteryMonitorQueryNextStatusResponseParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localBatteryMonitorQueryNextStatusResponseParams.jdField_a_of_type_OrgChromiumDeviceMojomBatteryStatus = BatteryStatus.a(paramDecoder.a(8, false));
        }
        return localBatteryMonitorQueryNextStatusResponseParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static BatteryMonitorQueryNextStatusResponseParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_OrgChromiumDeviceMojomBatteryStatus, 8, false);
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
      paramObject = (BatteryMonitorQueryNextStatusResponseParams)paramObject;
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumDeviceMojomBatteryStatus, ((BatteryMonitorQueryNextStatusResponseParams)paramObject).jdField_a_of_type_OrgChromiumDeviceMojomBatteryStatus);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumDeviceMojomBatteryStatus);
    }
  }
  
  static class BatteryMonitorQueryNextStatusResponseParamsForwardToCallback
    extends SideEffectFreeCloseable
    implements MessageReceiver
  {
    private final BatteryMonitor.QueryNextStatusResponse a;
    
    BatteryMonitorQueryNextStatusResponseParamsForwardToCallback(BatteryMonitor.QueryNextStatusResponse paramQueryNextStatusResponse)
    {
      this.a = paramQueryNextStatusResponse;
    }
    
    public boolean accept(Message paramMessage)
    {
      try
      {
        paramMessage = paramMessage.a();
        if (!paramMessage.a().a(0, 2)) {
          return false;
        }
        paramMessage = BatteryMonitor_Internal.BatteryMonitorQueryNextStatusResponseParams.a(paramMessage.a());
        this.a.call(paramMessage.a);
        return true;
      }
      catch (DeserializationException paramMessage) {}
      return false;
    }
  }
  
  static class BatteryMonitorQueryNextStatusResponseParamsProxyToResponder
    implements BatteryMonitor.QueryNextStatusResponse
  {
    private final long jdField_a_of_type_Long;
    private final MessageReceiver jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
    private final Core jdField_a_of_type_OrgChromiumMojoSystemCore;
    
    BatteryMonitorQueryNextStatusResponseParamsProxyToResponder(Core paramCore, MessageReceiver paramMessageReceiver, long paramLong)
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver = paramMessageReceiver;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void a(BatteryStatus paramBatteryStatus)
    {
      BatteryMonitor_Internal.BatteryMonitorQueryNextStatusResponseParams localBatteryMonitorQueryNextStatusResponseParams = new BatteryMonitor_Internal.BatteryMonitorQueryNextStatusResponseParams();
      localBatteryMonitorQueryNextStatusResponseParams.a = paramBatteryStatus;
      paramBatteryStatus = localBatteryMonitorQueryNextStatusResponseParams.a(this.jdField_a_of_type_OrgChromiumMojoSystemCore, new MessageHeader(0, 2, this.jdField_a_of_type_Long));
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver.accept(paramBatteryStatus);
    }
  }
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements BatteryMonitor.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void queryNextStatus(BatteryMonitor.QueryNextStatusResponse paramQueryNextStatusResponse)
    {
      BatteryMonitor_Internal.BatteryMonitorQueryNextStatusParams localBatteryMonitorQueryNextStatusParams = new BatteryMonitor_Internal.BatteryMonitorQueryNextStatusParams();
      a().a().acceptWithResponder(localBatteryMonitorQueryNextStatusParams.a(a().a(), new MessageHeader(0, 1, 0L)), new BatteryMonitor_Internal.BatteryMonitorQueryNextStatusResponseParamsForwardToCallback(paramQueryNextStatusResponse));
    }
  }
  
  static final class Stub
    extends Interface.Stub<BatteryMonitor>
  {
    Stub(Core paramCore, BatteryMonitor paramBatteryMonitor)
    {
      super(paramBatteryMonitor);
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
        boolean bool = InterfaceControlMessagesHelper.a(BatteryMonitor_Internal.a, paramMessage);
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
        switch (localMessageHeader.b())
        {
        case 0: 
          BatteryMonitor_Internal.BatteryMonitorQueryNextStatusParams.a(paramMessage.a());
          ((BatteryMonitor)a()).queryNextStatus(new BatteryMonitor_Internal.BatteryMonitorQueryNextStatusResponseParamsProxyToResponder(a(), paramMessageReceiver, localMessageHeader.a()));
          return true;
        }
      }
      catch (DeserializationException paramMessage)
      {
        boolean bool;
        System.err.println(paramMessage.toString());
        return false;
      }
      bool = InterfaceControlMessagesHelper.a(a(), BatteryMonitor_Internal.a, paramMessage, paramMessageReceiver);
      return bool;
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\mojom\BatteryMonitor_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */