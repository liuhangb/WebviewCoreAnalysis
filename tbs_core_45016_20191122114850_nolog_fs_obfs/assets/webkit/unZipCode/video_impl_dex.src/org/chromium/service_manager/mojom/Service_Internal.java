package org.chromium.service_manager.mojom;

import java.io.PrintStream;
import org.chromium.mojo.bindings.AssociatedInterfaceRequestNotSupported;
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
import org.chromium.mojo.bindings.SideEffectFreeCloseable;
import org.chromium.mojo.bindings.Struct;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.InvalidHandle;
import org.chromium.mojo.system.MessagePipeHandle;

class Service_Internal
{
  public static final Interface.Manager<Service, Service.Proxy> a = new Interface.Manager()
  {
    public Service_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new Service_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public Service_Internal.Stub a(Core paramAnonymousCore, Service paramAnonymousService)
    {
      return new Service_Internal.Stub(paramAnonymousCore, paramAnonymousService);
    }
    
    public Service[] a(int paramAnonymousInt)
    {
      return new Service[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "service_manager::mojom::Service";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements Service.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void onBindInterface(BindSourceInfo paramBindSourceInfo, String paramString, MessagePipeHandle paramMessagePipeHandle, Service.OnBindInterfaceResponse paramOnBindInterfaceResponse)
    {
      Service_Internal.ServiceOnBindInterfaceParams localServiceOnBindInterfaceParams = new Service_Internal.ServiceOnBindInterfaceParams();
      localServiceOnBindInterfaceParams.jdField_a_of_type_OrgChromiumService_managerMojomBindSourceInfo = paramBindSourceInfo;
      localServiceOnBindInterfaceParams.jdField_a_of_type_JavaLangString = paramString;
      localServiceOnBindInterfaceParams.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = paramMessagePipeHandle;
      a().a().acceptWithResponder(localServiceOnBindInterfaceParams.a(a().a(), new MessageHeader(1, 1, 0L)), new Service_Internal.ServiceOnBindInterfaceResponseParamsForwardToCallback(paramOnBindInterfaceResponse));
    }
    
    public void onStart(Identity paramIdentity, Service.OnStartResponse paramOnStartResponse)
    {
      Service_Internal.ServiceOnStartParams localServiceOnStartParams = new Service_Internal.ServiceOnStartParams();
      localServiceOnStartParams.a = paramIdentity;
      a().a().acceptWithResponder(localServiceOnStartParams.a(a().a(), new MessageHeader(0, 1, 0L)), new Service_Internal.ServiceOnStartResponseParamsForwardToCallback(paramOnStartResponse));
    }
  }
  
  static final class ServiceOnBindInterfaceParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(32, 0) };
    public String a;
    public MessagePipeHandle a;
    public BindSourceInfo a;
    
    public ServiceOnBindInterfaceParams()
    {
      this(0);
    }
    
    private ServiceOnBindInterfaceParams(int paramInt)
    {
      super(paramInt);
      this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = InvalidHandle.a;
    }
    
    public static ServiceOnBindInterfaceParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ServiceOnBindInterfaceParams localServiceOnBindInterfaceParams = new ServiceOnBindInterfaceParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localServiceOnBindInterfaceParams.jdField_a_of_type_OrgChromiumService_managerMojomBindSourceInfo = BindSourceInfo.a(paramDecoder.a(8, false));
        }
        if (localDataHeader.b >= 0) {
          localServiceOnBindInterfaceParams.jdField_a_of_type_JavaLangString = paramDecoder.a(16, false);
        }
        if (localDataHeader.b >= 0) {
          localServiceOnBindInterfaceParams.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = paramDecoder.a(24, false);
        }
        return localServiceOnBindInterfaceParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ServiceOnBindInterfaceParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomBindSourceInfo, 8, false);
      paramEncoder.a(this.jdField_a_of_type_JavaLangString, 16, false);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle, 24, false);
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
      paramObject = (ServiceOnBindInterfaceParams)paramObject;
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomBindSourceInfo, ((ServiceOnBindInterfaceParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomBindSourceInfo)) {
        return false;
      }
      if (!BindingsHelper.a(this.jdField_a_of_type_JavaLangString, ((ServiceOnBindInterfaceParams)paramObject).jdField_a_of_type_JavaLangString)) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle, ((ServiceOnBindInterfaceParams)paramObject).jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle);
    }
    
    public int hashCode()
    {
      return (((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomBindSourceInfo)) * 31 + BindingsHelper.a(this.jdField_a_of_type_JavaLangString)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle);
    }
  }
  
  static final class ServiceOnBindInterfaceResponseParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(8, 0) };
    
    public ServiceOnBindInterfaceResponseParams()
    {
      this(0);
    }
    
    private ServiceOnBindInterfaceResponseParams(int paramInt)
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
  
  static class ServiceOnBindInterfaceResponseParamsForwardToCallback
    extends SideEffectFreeCloseable
    implements MessageReceiver
  {
    private final Service.OnBindInterfaceResponse a;
    
    ServiceOnBindInterfaceResponseParamsForwardToCallback(Service.OnBindInterfaceResponse paramOnBindInterfaceResponse)
    {
      this.a = paramOnBindInterfaceResponse;
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
  
  static class ServiceOnBindInterfaceResponseParamsProxyToResponder
    implements Service.OnBindInterfaceResponse
  {
    private final long jdField_a_of_type_Long;
    private final MessageReceiver jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
    private final Core jdField_a_of_type_OrgChromiumMojoSystemCore;
    
    ServiceOnBindInterfaceResponseParamsProxyToResponder(Core paramCore, MessageReceiver paramMessageReceiver, long paramLong)
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver = paramMessageReceiver;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void call()
    {
      ServiceMessage localServiceMessage = new Service_Internal.ServiceOnBindInterfaceResponseParams().a(this.jdField_a_of_type_OrgChromiumMojoSystemCore, new MessageHeader(1, 2, this.jdField_a_of_type_Long));
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver.accept(localServiceMessage);
    }
  }
  
  static final class ServiceOnStartParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public Identity a;
    
    public ServiceOnStartParams()
    {
      this(0);
    }
    
    private ServiceOnStartParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ServiceOnStartParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ServiceOnStartParams localServiceOnStartParams = new ServiceOnStartParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localServiceOnStartParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(8, false));
        }
        return localServiceOnStartParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ServiceOnStartParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, 8, false);
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
      paramObject = (ServiceOnStartParams)paramObject;
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ServiceOnStartParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
  }
  
  static final class ServiceOnStartResponseParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public AssociatedInterfaceRequestNotSupported a;
    public InterfaceRequest<Connector> a;
    
    public ServiceOnStartResponseParams()
    {
      this(0);
    }
    
    private ServiceOnStartResponseParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ServiceOnStartResponseParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ServiceOnStartResponseParams localServiceOnStartResponseParams = new ServiceOnStartResponseParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localServiceOnStartResponseParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramDecoder.a(8, true);
        }
        if (localDataHeader.b >= 0) {
          localServiceOnStartResponseParams.jdField_a_of_type_OrgChromiumMojoBindingsAssociatedInterfaceRequestNotSupported = paramDecoder.a(12, true);
        }
        return localServiceOnStartResponseParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ServiceOnStartResponseParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, 8, true);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoBindingsAssociatedInterfaceRequestNotSupported, 12, true);
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
      paramObject = (ServiceOnStartResponseParams)paramObject;
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, ((ServiceOnStartResponseParams)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest)) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsAssociatedInterfaceRequestNotSupported, ((ServiceOnStartResponseParams)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsAssociatedInterfaceRequestNotSupported);
    }
    
    public int hashCode()
    {
      return ((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsAssociatedInterfaceRequestNotSupported);
    }
  }
  
  static class ServiceOnStartResponseParamsForwardToCallback
    extends SideEffectFreeCloseable
    implements MessageReceiver
  {
    private final Service.OnStartResponse a;
    
    ServiceOnStartResponseParamsForwardToCallback(Service.OnStartResponse paramOnStartResponse)
    {
      this.a = paramOnStartResponse;
    }
    
    public boolean accept(Message paramMessage)
    {
      try
      {
        paramMessage = paramMessage.a();
        if (!paramMessage.a().a(0, 2)) {
          return false;
        }
        paramMessage = Service_Internal.ServiceOnStartResponseParams.a(paramMessage.a());
        this.a.call(paramMessage.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, paramMessage.jdField_a_of_type_OrgChromiumMojoBindingsAssociatedInterfaceRequestNotSupported);
        return true;
      }
      catch (DeserializationException paramMessage) {}
      return false;
    }
  }
  
  static class ServiceOnStartResponseParamsProxyToResponder
    implements Service.OnStartResponse
  {
    private final long jdField_a_of_type_Long;
    private final MessageReceiver jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
    private final Core jdField_a_of_type_OrgChromiumMojoSystemCore;
    
    ServiceOnStartResponseParamsProxyToResponder(Core paramCore, MessageReceiver paramMessageReceiver, long paramLong)
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver = paramMessageReceiver;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void a(InterfaceRequest<Connector> paramInterfaceRequest, AssociatedInterfaceRequestNotSupported paramAssociatedInterfaceRequestNotSupported)
    {
      Service_Internal.ServiceOnStartResponseParams localServiceOnStartResponseParams = new Service_Internal.ServiceOnStartResponseParams();
      localServiceOnStartResponseParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramInterfaceRequest;
      localServiceOnStartResponseParams.jdField_a_of_type_OrgChromiumMojoBindingsAssociatedInterfaceRequestNotSupported = paramAssociatedInterfaceRequestNotSupported;
      paramInterfaceRequest = localServiceOnStartResponseParams.a(this.jdField_a_of_type_OrgChromiumMojoSystemCore, new MessageHeader(0, 2, this.jdField_a_of_type_Long));
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver.accept(paramInterfaceRequest);
    }
  }
  
  static final class Stub
    extends Interface.Stub<Service>
  {
    Stub(Core paramCore, Service paramService)
    {
      super(paramService);
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
        boolean bool = InterfaceControlMessagesHelper.a(Service_Internal.a, paramMessage);
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
          localObject = Service_Internal.ServiceOnBindInterfaceParams.a(((ServiceMessage)localObject).a());
          ((Service)a()).onBindInterface(((Service_Internal.ServiceOnBindInterfaceParams)localObject).jdField_a_of_type_OrgChromiumService_managerMojomBindSourceInfo, ((Service_Internal.ServiceOnBindInterfaceParams)localObject).jdField_a_of_type_JavaLangString, ((Service_Internal.ServiceOnBindInterfaceParams)localObject).jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle, new Service_Internal.ServiceOnBindInterfaceResponseParamsProxyToResponder(a(), paramMessageReceiver, paramMessage.a()));
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
      localObject = Service_Internal.ServiceOnStartParams.a(((ServiceMessage)localObject).a());
      ((Service)a()).onStart(((Service_Internal.ServiceOnStartParams)localObject).a, new Service_Internal.ServiceOnStartResponseParamsProxyToResponder(a(), paramMessageReceiver, paramMessage.a()));
      return true;
      bool = InterfaceControlMessagesHelper.a(a(), Service_Internal.a, (ServiceMessage)localObject, paramMessageReceiver);
      return bool;
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\Service_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */