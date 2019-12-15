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
import org.chromium.mojo.bindings.SideEffectFreeCloseable;
import org.chromium.mojo.bindings.Struct;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.InvalidHandle;
import org.chromium.mojo.system.MessagePipeHandle;

class Connector_Internal
{
  public static final Interface.Manager<Connector, Connector.Proxy> a = new Interface.Manager()
  {
    public Connector_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new Connector_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public Connector_Internal.Stub a(Core paramAnonymousCore, Connector paramAnonymousConnector)
    {
      return new Connector_Internal.Stub(paramAnonymousCore, paramAnonymousConnector);
    }
    
    public Connector[] a(int paramAnonymousInt)
    {
      return new Connector[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "service_manager::mojom::Connector";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class ConnectorBindInterfaceParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(32, 0) };
    public String a;
    public MessagePipeHandle a;
    public Identity a;
    
    public ConnectorBindInterfaceParams()
    {
      this(0);
    }
    
    private ConnectorBindInterfaceParams(int paramInt)
    {
      super(paramInt);
      this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = InvalidHandle.a;
    }
    
    public static ConnectorBindInterfaceParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ConnectorBindInterfaceParams localConnectorBindInterfaceParams = new ConnectorBindInterfaceParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localConnectorBindInterfaceParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(8, false));
        }
        if (localDataHeader.b >= 0) {
          localConnectorBindInterfaceParams.jdField_a_of_type_JavaLangString = paramDecoder.a(16, false);
        }
        if (localDataHeader.b >= 0) {
          localConnectorBindInterfaceParams.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = paramDecoder.a(24, false);
        }
        return localConnectorBindInterfaceParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ConnectorBindInterfaceParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, 8, false);
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
      paramObject = (ConnectorBindInterfaceParams)paramObject;
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ConnectorBindInterfaceParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) {
        return false;
      }
      if (!BindingsHelper.a(this.jdField_a_of_type_JavaLangString, ((ConnectorBindInterfaceParams)paramObject).jdField_a_of_type_JavaLangString)) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle, ((ConnectorBindInterfaceParams)paramObject).jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle);
    }
    
    public int hashCode()
    {
      return (((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) * 31 + BindingsHelper.a(this.jdField_a_of_type_JavaLangString)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle);
    }
  }
  
  static final class ConnectorBindInterfaceResponseParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
    public int a;
    public Identity a;
    
    public ConnectorBindInterfaceResponseParams()
    {
      this(0);
    }
    
    private ConnectorBindInterfaceResponseParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ConnectorBindInterfaceResponseParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ConnectorBindInterfaceResponseParams localConnectorBindInterfaceResponseParams = new ConnectorBindInterfaceResponseParams(localDataHeader.b);
        if (localDataHeader.b >= 0)
        {
          localConnectorBindInterfaceResponseParams.jdField_a_of_type_Int = paramDecoder.a(8);
          ConnectResult.a(localConnectorBindInterfaceResponseParams.jdField_a_of_type_Int);
        }
        if (localDataHeader.b >= 0) {
          localConnectorBindInterfaceResponseParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(16, false));
        }
        return localConnectorBindInterfaceResponseParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ConnectorBindInterfaceResponseParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_Int, 8);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, 16, false);
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
      paramObject = (ConnectorBindInterfaceResponseParams)paramObject;
      if (this.jdField_a_of_type_Int != ((ConnectorBindInterfaceResponseParams)paramObject).jdField_a_of_type_Int) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ConnectorBindInterfaceResponseParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
    
    public int hashCode()
    {
      return ((getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
  }
  
  static class ConnectorBindInterfaceResponseParamsForwardToCallback
    extends SideEffectFreeCloseable
    implements MessageReceiver
  {
    private final Connector.BindInterfaceResponse a;
    
    ConnectorBindInterfaceResponseParamsForwardToCallback(Connector.BindInterfaceResponse paramBindInterfaceResponse)
    {
      this.a = paramBindInterfaceResponse;
    }
    
    public boolean accept(Message paramMessage)
    {
      try
      {
        paramMessage = paramMessage.a();
        if (!paramMessage.a().a(0, 2)) {
          return false;
        }
        paramMessage = Connector_Internal.ConnectorBindInterfaceResponseParams.a(paramMessage.a());
        this.a.call(Integer.valueOf(paramMessage.jdField_a_of_type_Int), paramMessage.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
        return true;
      }
      catch (DeserializationException paramMessage) {}
      return false;
    }
  }
  
  static class ConnectorBindInterfaceResponseParamsProxyToResponder
    implements Connector.BindInterfaceResponse
  {
    private final long jdField_a_of_type_Long;
    private final MessageReceiver jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
    private final Core jdField_a_of_type_OrgChromiumMojoSystemCore;
    
    ConnectorBindInterfaceResponseParamsProxyToResponder(Core paramCore, MessageReceiver paramMessageReceiver, long paramLong)
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver = paramMessageReceiver;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void a(Integer paramInteger, Identity paramIdentity)
    {
      Connector_Internal.ConnectorBindInterfaceResponseParams localConnectorBindInterfaceResponseParams = new Connector_Internal.ConnectorBindInterfaceResponseParams();
      localConnectorBindInterfaceResponseParams.jdField_a_of_type_Int = paramInteger.intValue();
      localConnectorBindInterfaceResponseParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = paramIdentity;
      paramInteger = localConnectorBindInterfaceResponseParams.a(this.jdField_a_of_type_OrgChromiumMojoSystemCore, new MessageHeader(0, 2, this.jdField_a_of_type_Long));
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver.accept(paramInteger);
    }
  }
  
  static final class ConnectorCloneParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public InterfaceRequest<Connector> a;
    
    public ConnectorCloneParams()
    {
      this(0);
    }
    
    private ConnectorCloneParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ConnectorCloneParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ConnectorCloneParams localConnectorCloneParams = new ConnectorCloneParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localConnectorCloneParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramDecoder.a(8, false);
        }
        return localConnectorCloneParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ConnectorCloneParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, 8, false);
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
      paramObject = (ConnectorCloneParams)paramObject;
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, ((ConnectorCloneParams)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest);
    }
  }
  
  static final class ConnectorFilterInterfacesParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(40, 0) };
    public String a;
    public InterfaceRequest<InterfaceProvider> a;
    public Identity a;
    public InterfaceProvider a;
    
    public ConnectorFilterInterfacesParams()
    {
      this(0);
    }
    
    private ConnectorFilterInterfacesParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ConnectorFilterInterfacesParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ConnectorFilterInterfacesParams localConnectorFilterInterfacesParams = new ConnectorFilterInterfacesParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localConnectorFilterInterfacesParams.jdField_a_of_type_JavaLangString = paramDecoder.a(8, false);
        }
        if (localDataHeader.b >= 0) {
          localConnectorFilterInterfacesParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(16, false));
        }
        if (localDataHeader.b >= 0) {
          localConnectorFilterInterfacesParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramDecoder.a(24, false);
        }
        if (localDataHeader.b >= 0) {
          localConnectorFilterInterfacesParams.jdField_a_of_type_OrgChromiumService_managerMojomInterfaceProvider = ((InterfaceProvider)paramDecoder.a(28, false, InterfaceProvider.a));
        }
        return localConnectorFilterInterfacesParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ConnectorFilterInterfacesParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_JavaLangString, 8, false);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, 16, false);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, 24, false);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomInterfaceProvider, 28, false, InterfaceProvider.a);
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
      paramObject = (ConnectorFilterInterfacesParams)paramObject;
      if (!BindingsHelper.a(this.jdField_a_of_type_JavaLangString, ((ConnectorFilterInterfacesParams)paramObject).jdField_a_of_type_JavaLangString)) {
        return false;
      }
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ConnectorFilterInterfacesParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) {
        return false;
      }
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, ((ConnectorFilterInterfacesParams)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest)) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomInterfaceProvider, ((ConnectorFilterInterfacesParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomInterfaceProvider);
    }
    
    public int hashCode()
    {
      return ((((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_JavaLangString)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomInterfaceProvider);
    }
  }
  
  static final class ConnectorQueryServiceParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public Identity a;
    
    public ConnectorQueryServiceParams()
    {
      this(0);
    }
    
    private ConnectorQueryServiceParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ConnectorQueryServiceParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ConnectorQueryServiceParams localConnectorQueryServiceParams = new ConnectorQueryServiceParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localConnectorQueryServiceParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(8, false));
        }
        return localConnectorQueryServiceParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ConnectorQueryServiceParams a(Message paramMessage)
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
      paramObject = (ConnectorQueryServiceParams)paramObject;
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ConnectorQueryServiceParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
  }
  
  static final class ConnectorQueryServiceResponseParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
    public int a;
    public String a;
    
    public ConnectorQueryServiceResponseParams()
    {
      this(0);
    }
    
    private ConnectorQueryServiceResponseParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ConnectorQueryServiceResponseParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ConnectorQueryServiceResponseParams localConnectorQueryServiceResponseParams = new ConnectorQueryServiceResponseParams(localDataHeader.b);
        if (localDataHeader.b >= 0)
        {
          localConnectorQueryServiceResponseParams.jdField_a_of_type_Int = paramDecoder.a(8);
          ConnectResult.a(localConnectorQueryServiceResponseParams.jdField_a_of_type_Int);
        }
        if (localDataHeader.b >= 0) {
          localConnectorQueryServiceResponseParams.jdField_a_of_type_JavaLangString = paramDecoder.a(16, false);
        }
        return localConnectorQueryServiceResponseParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ConnectorQueryServiceResponseParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_Int, 8);
      paramEncoder.a(this.jdField_a_of_type_JavaLangString, 16, false);
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
      paramObject = (ConnectorQueryServiceResponseParams)paramObject;
      if (this.jdField_a_of_type_Int != ((ConnectorQueryServiceResponseParams)paramObject).jdField_a_of_type_Int) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_JavaLangString, ((ConnectorQueryServiceResponseParams)paramObject).jdField_a_of_type_JavaLangString);
    }
    
    public int hashCode()
    {
      return ((getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int)) * 31 + BindingsHelper.a(this.jdField_a_of_type_JavaLangString);
    }
  }
  
  static class ConnectorQueryServiceResponseParamsForwardToCallback
    extends SideEffectFreeCloseable
    implements MessageReceiver
  {
    private final Connector.QueryServiceResponse a;
    
    ConnectorQueryServiceResponseParamsForwardToCallback(Connector.QueryServiceResponse paramQueryServiceResponse)
    {
      this.a = paramQueryServiceResponse;
    }
    
    public boolean accept(Message paramMessage)
    {
      try
      {
        paramMessage = paramMessage.a();
        if (!paramMessage.a().a(1, 2)) {
          return false;
        }
        paramMessage = Connector_Internal.ConnectorQueryServiceResponseParams.a(paramMessage.a());
        this.a.call(Integer.valueOf(paramMessage.jdField_a_of_type_Int), paramMessage.jdField_a_of_type_JavaLangString);
        return true;
      }
      catch (DeserializationException paramMessage) {}
      return false;
    }
  }
  
  static class ConnectorQueryServiceResponseParamsProxyToResponder
    implements Connector.QueryServiceResponse
  {
    private final long jdField_a_of_type_Long;
    private final MessageReceiver jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
    private final Core jdField_a_of_type_OrgChromiumMojoSystemCore;
    
    ConnectorQueryServiceResponseParamsProxyToResponder(Core paramCore, MessageReceiver paramMessageReceiver, long paramLong)
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver = paramMessageReceiver;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void a(Integer paramInteger, String paramString)
    {
      Connector_Internal.ConnectorQueryServiceResponseParams localConnectorQueryServiceResponseParams = new Connector_Internal.ConnectorQueryServiceResponseParams();
      localConnectorQueryServiceResponseParams.jdField_a_of_type_Int = paramInteger.intValue();
      localConnectorQueryServiceResponseParams.jdField_a_of_type_JavaLangString = paramString;
      paramInteger = localConnectorQueryServiceResponseParams.a(this.jdField_a_of_type_OrgChromiumMojoSystemCore, new MessageHeader(1, 2, this.jdField_a_of_type_Long));
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver.accept(paramInteger);
    }
  }
  
  static final class ConnectorStartServiceParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public Identity a;
    
    public ConnectorStartServiceParams()
    {
      this(0);
    }
    
    private ConnectorStartServiceParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ConnectorStartServiceParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ConnectorStartServiceParams localConnectorStartServiceParams = new ConnectorStartServiceParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localConnectorStartServiceParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(8, false));
        }
        return localConnectorStartServiceParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ConnectorStartServiceParams a(Message paramMessage)
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
      paramObject = (ConnectorStartServiceParams)paramObject;
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ConnectorStartServiceParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
  }
  
  static final class ConnectorStartServiceResponseParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
    public int a;
    public Identity a;
    
    public ConnectorStartServiceResponseParams()
    {
      this(0);
    }
    
    private ConnectorStartServiceResponseParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ConnectorStartServiceResponseParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ConnectorStartServiceResponseParams localConnectorStartServiceResponseParams = new ConnectorStartServiceResponseParams(localDataHeader.b);
        if (localDataHeader.b >= 0)
        {
          localConnectorStartServiceResponseParams.jdField_a_of_type_Int = paramDecoder.a(8);
          ConnectResult.a(localConnectorStartServiceResponseParams.jdField_a_of_type_Int);
        }
        if (localDataHeader.b >= 0) {
          localConnectorStartServiceResponseParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(16, false));
        }
        return localConnectorStartServiceResponseParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ConnectorStartServiceResponseParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_Int, 8);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, 16, false);
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
      paramObject = (ConnectorStartServiceResponseParams)paramObject;
      if (this.jdField_a_of_type_Int != ((ConnectorStartServiceResponseParams)paramObject).jdField_a_of_type_Int) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ConnectorStartServiceResponseParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
    
    public int hashCode()
    {
      return ((getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
  }
  
  static class ConnectorStartServiceResponseParamsForwardToCallback
    extends SideEffectFreeCloseable
    implements MessageReceiver
  {
    private final Connector.StartServiceResponse a;
    
    ConnectorStartServiceResponseParamsForwardToCallback(Connector.StartServiceResponse paramStartServiceResponse)
    {
      this.a = paramStartServiceResponse;
    }
    
    public boolean accept(Message paramMessage)
    {
      try
      {
        paramMessage = paramMessage.a();
        if (!paramMessage.a().a(2, 2)) {
          return false;
        }
        paramMessage = Connector_Internal.ConnectorStartServiceResponseParams.a(paramMessage.a());
        this.a.call(Integer.valueOf(paramMessage.jdField_a_of_type_Int), paramMessage.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
        return true;
      }
      catch (DeserializationException paramMessage) {}
      return false;
    }
  }
  
  static class ConnectorStartServiceResponseParamsProxyToResponder
    implements Connector.StartServiceResponse
  {
    private final long jdField_a_of_type_Long;
    private final MessageReceiver jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
    private final Core jdField_a_of_type_OrgChromiumMojoSystemCore;
    
    ConnectorStartServiceResponseParamsProxyToResponder(Core paramCore, MessageReceiver paramMessageReceiver, long paramLong)
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver = paramMessageReceiver;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void a(Integer paramInteger, Identity paramIdentity)
    {
      Connector_Internal.ConnectorStartServiceResponseParams localConnectorStartServiceResponseParams = new Connector_Internal.ConnectorStartServiceResponseParams();
      localConnectorStartServiceResponseParams.jdField_a_of_type_Int = paramInteger.intValue();
      localConnectorStartServiceResponseParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = paramIdentity;
      paramInteger = localConnectorStartServiceResponseParams.a(this.jdField_a_of_type_OrgChromiumMojoSystemCore, new MessageHeader(2, 2, this.jdField_a_of_type_Long));
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver.accept(paramInteger);
    }
  }
  
  static final class ConnectorStartServiceWithProcessParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
    public InterfaceRequest<PidReceiver> a;
    public MessagePipeHandle a;
    public Identity a;
    
    public ConnectorStartServiceWithProcessParams()
    {
      this(0);
    }
    
    private ConnectorStartServiceWithProcessParams(int paramInt)
    {
      super(paramInt);
      this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = InvalidHandle.a;
    }
    
    public static ConnectorStartServiceWithProcessParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ConnectorStartServiceWithProcessParams localConnectorStartServiceWithProcessParams = new ConnectorStartServiceWithProcessParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localConnectorStartServiceWithProcessParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(8, false));
        }
        if (localDataHeader.b >= 0) {
          localConnectorStartServiceWithProcessParams.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = paramDecoder.a(16, false);
        }
        if (localDataHeader.b >= 0) {
          localConnectorStartServiceWithProcessParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramDecoder.a(20, false);
        }
        return localConnectorStartServiceWithProcessParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ConnectorStartServiceWithProcessParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, 8, false);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle, 16, false);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, 20, false);
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
      paramObject = (ConnectorStartServiceWithProcessParams)paramObject;
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ConnectorStartServiceWithProcessParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) {
        return false;
      }
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle, ((ConnectorStartServiceWithProcessParams)paramObject).jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle)) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, ((ConnectorStartServiceWithProcessParams)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest);
    }
    
    public int hashCode()
    {
      return (((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest);
    }
  }
  
  static final class ConnectorStartServiceWithProcessResponseParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
    public int a;
    public Identity a;
    
    public ConnectorStartServiceWithProcessResponseParams()
    {
      this(0);
    }
    
    private ConnectorStartServiceWithProcessResponseParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ConnectorStartServiceWithProcessResponseParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ConnectorStartServiceWithProcessResponseParams localConnectorStartServiceWithProcessResponseParams = new ConnectorStartServiceWithProcessResponseParams(localDataHeader.b);
        if (localDataHeader.b >= 0)
        {
          localConnectorStartServiceWithProcessResponseParams.jdField_a_of_type_Int = paramDecoder.a(8);
          ConnectResult.a(localConnectorStartServiceWithProcessResponseParams.jdField_a_of_type_Int);
        }
        if (localDataHeader.b >= 0) {
          localConnectorStartServiceWithProcessResponseParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(16, false));
        }
        return localConnectorStartServiceWithProcessResponseParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ConnectorStartServiceWithProcessResponseParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_Int, 8);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, 16, false);
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
      paramObject = (ConnectorStartServiceWithProcessResponseParams)paramObject;
      if (this.jdField_a_of_type_Int != ((ConnectorStartServiceWithProcessResponseParams)paramObject).jdField_a_of_type_Int) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ConnectorStartServiceWithProcessResponseParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
    
    public int hashCode()
    {
      return ((getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
  }
  
  static class ConnectorStartServiceWithProcessResponseParamsForwardToCallback
    extends SideEffectFreeCloseable
    implements MessageReceiver
  {
    private final Connector.StartServiceWithProcessResponse a;
    
    ConnectorStartServiceWithProcessResponseParamsForwardToCallback(Connector.StartServiceWithProcessResponse paramStartServiceWithProcessResponse)
    {
      this.a = paramStartServiceWithProcessResponse;
    }
    
    public boolean accept(Message paramMessage)
    {
      try
      {
        paramMessage = paramMessage.a();
        if (!paramMessage.a().a(3, 2)) {
          return false;
        }
        paramMessage = Connector_Internal.ConnectorStartServiceWithProcessResponseParams.a(paramMessage.a());
        this.a.call(Integer.valueOf(paramMessage.jdField_a_of_type_Int), paramMessage.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
        return true;
      }
      catch (DeserializationException paramMessage) {}
      return false;
    }
  }
  
  static class ConnectorStartServiceWithProcessResponseParamsProxyToResponder
    implements Connector.StartServiceWithProcessResponse
  {
    private final long jdField_a_of_type_Long;
    private final MessageReceiver jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
    private final Core jdField_a_of_type_OrgChromiumMojoSystemCore;
    
    ConnectorStartServiceWithProcessResponseParamsProxyToResponder(Core paramCore, MessageReceiver paramMessageReceiver, long paramLong)
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver = paramMessageReceiver;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void a(Integer paramInteger, Identity paramIdentity)
    {
      Connector_Internal.ConnectorStartServiceWithProcessResponseParams localConnectorStartServiceWithProcessResponseParams = new Connector_Internal.ConnectorStartServiceWithProcessResponseParams();
      localConnectorStartServiceWithProcessResponseParams.jdField_a_of_type_Int = paramInteger.intValue();
      localConnectorStartServiceWithProcessResponseParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = paramIdentity;
      paramInteger = localConnectorStartServiceWithProcessResponseParams.a(this.jdField_a_of_type_OrgChromiumMojoSystemCore, new MessageHeader(3, 2, this.jdField_a_of_type_Long));
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver.accept(paramInteger);
    }
  }
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements Connector.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void bindInterface(Identity paramIdentity, String paramString, MessagePipeHandle paramMessagePipeHandle, Connector.BindInterfaceResponse paramBindInterfaceResponse)
    {
      Connector_Internal.ConnectorBindInterfaceParams localConnectorBindInterfaceParams = new Connector_Internal.ConnectorBindInterfaceParams();
      localConnectorBindInterfaceParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = paramIdentity;
      localConnectorBindInterfaceParams.jdField_a_of_type_JavaLangString = paramString;
      localConnectorBindInterfaceParams.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = paramMessagePipeHandle;
      a().a().acceptWithResponder(localConnectorBindInterfaceParams.a(a().a(), new MessageHeader(0, 1, 0L)), new Connector_Internal.ConnectorBindInterfaceResponseParamsForwardToCallback(paramBindInterfaceResponse));
    }
    
    public void clone(InterfaceRequest<Connector> paramInterfaceRequest)
    {
      Connector_Internal.ConnectorCloneParams localConnectorCloneParams = new Connector_Internal.ConnectorCloneParams();
      localConnectorCloneParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramInterfaceRequest;
      a().a().accept(localConnectorCloneParams.a(a().a(), new MessageHeader(4)));
    }
    
    public void filterInterfaces(String paramString, Identity paramIdentity, InterfaceRequest<InterfaceProvider> paramInterfaceRequest, InterfaceProvider paramInterfaceProvider)
    {
      Connector_Internal.ConnectorFilterInterfacesParams localConnectorFilterInterfacesParams = new Connector_Internal.ConnectorFilterInterfacesParams();
      localConnectorFilterInterfacesParams.jdField_a_of_type_JavaLangString = paramString;
      localConnectorFilterInterfacesParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = paramIdentity;
      localConnectorFilterInterfacesParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramInterfaceRequest;
      localConnectorFilterInterfacesParams.jdField_a_of_type_OrgChromiumService_managerMojomInterfaceProvider = paramInterfaceProvider;
      a().a().accept(localConnectorFilterInterfacesParams.a(a().a(), new MessageHeader(5)));
    }
    
    public void queryService(Identity paramIdentity, Connector.QueryServiceResponse paramQueryServiceResponse)
    {
      Connector_Internal.ConnectorQueryServiceParams localConnectorQueryServiceParams = new Connector_Internal.ConnectorQueryServiceParams();
      localConnectorQueryServiceParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = paramIdentity;
      a().a().acceptWithResponder(localConnectorQueryServiceParams.a(a().a(), new MessageHeader(1, 1, 0L)), new Connector_Internal.ConnectorQueryServiceResponseParamsForwardToCallback(paramQueryServiceResponse));
    }
    
    public void startService(Identity paramIdentity, Connector.StartServiceResponse paramStartServiceResponse)
    {
      Connector_Internal.ConnectorStartServiceParams localConnectorStartServiceParams = new Connector_Internal.ConnectorStartServiceParams();
      localConnectorStartServiceParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = paramIdentity;
      a().a().acceptWithResponder(localConnectorStartServiceParams.a(a().a(), new MessageHeader(2, 1, 0L)), new Connector_Internal.ConnectorStartServiceResponseParamsForwardToCallback(paramStartServiceResponse));
    }
    
    public void startServiceWithProcess(Identity paramIdentity, MessagePipeHandle paramMessagePipeHandle, InterfaceRequest<PidReceiver> paramInterfaceRequest, Connector.StartServiceWithProcessResponse paramStartServiceWithProcessResponse)
    {
      Connector_Internal.ConnectorStartServiceWithProcessParams localConnectorStartServiceWithProcessParams = new Connector_Internal.ConnectorStartServiceWithProcessParams();
      localConnectorStartServiceWithProcessParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = paramIdentity;
      localConnectorStartServiceWithProcessParams.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = paramMessagePipeHandle;
      localConnectorStartServiceWithProcessParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramInterfaceRequest;
      a().a().acceptWithResponder(localConnectorStartServiceWithProcessParams.a(a().a(), new MessageHeader(3, 1, 0L)), new Connector_Internal.ConnectorStartServiceWithProcessResponseParamsForwardToCallback(paramStartServiceWithProcessResponse));
    }
  }
  
  static final class Stub
    extends Interface.Stub<Connector>
  {
    Stub(Core paramCore, Connector paramConnector)
    {
      super(paramConnector);
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
        case 5: 
          paramMessage = Connector_Internal.ConnectorFilterInterfacesParams.a(paramMessage.a());
          ((Connector)a()).filterInterfaces(paramMessage.jdField_a_of_type_JavaLangString, paramMessage.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, paramMessage.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, paramMessage.jdField_a_of_type_OrgChromiumService_managerMojomInterfaceProvider);
          return true;
        case 4: 
          paramMessage = Connector_Internal.ConnectorCloneParams.a(paramMessage.a());
          ((Connector)a()).clone(paramMessage.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest);
          return true;
          boolean bool = InterfaceControlMessagesHelper.a(Connector_Internal.a, paramMessage);
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
        localObject = paramMessage.a();
        paramMessage = ((ServiceMessage)localObject).a();
        if (!paramMessage.b(1)) {
          return false;
        }
        switch (paramMessage.b())
        {
        case 3: 
          localObject = Connector_Internal.ConnectorStartServiceWithProcessParams.a(((ServiceMessage)localObject).a());
          ((Connector)a()).startServiceWithProcess(((Connector_Internal.ConnectorStartServiceWithProcessParams)localObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((Connector_Internal.ConnectorStartServiceWithProcessParams)localObject).jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle, ((Connector_Internal.ConnectorStartServiceWithProcessParams)localObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, new Connector_Internal.ConnectorStartServiceWithProcessResponseParamsProxyToResponder(a(), paramMessageReceiver, paramMessage.a()));
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
      localObject = Connector_Internal.ConnectorStartServiceParams.a(((ServiceMessage)localObject).a());
      ((Connector)a()).startService(((Connector_Internal.ConnectorStartServiceParams)localObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity, new Connector_Internal.ConnectorStartServiceResponseParamsProxyToResponder(a(), paramMessageReceiver, paramMessage.a()));
      return true;
      localObject = Connector_Internal.ConnectorQueryServiceParams.a(((ServiceMessage)localObject).a());
      ((Connector)a()).queryService(((Connector_Internal.ConnectorQueryServiceParams)localObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity, new Connector_Internal.ConnectorQueryServiceResponseParamsProxyToResponder(a(), paramMessageReceiver, paramMessage.a()));
      return true;
      localObject = Connector_Internal.ConnectorBindInterfaceParams.a(((ServiceMessage)localObject).a());
      ((Connector)a()).bindInterface(((Connector_Internal.ConnectorBindInterfaceParams)localObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((Connector_Internal.ConnectorBindInterfaceParams)localObject).jdField_a_of_type_JavaLangString, ((Connector_Internal.ConnectorBindInterfaceParams)localObject).jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle, new Connector_Internal.ConnectorBindInterfaceResponseParamsProxyToResponder(a(), paramMessageReceiver, paramMessage.a()));
      return true;
      bool = InterfaceControlMessagesHelper.a(a(), Connector_Internal.a, (ServiceMessage)localObject, paramMessageReceiver);
      return bool;
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\Connector_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */