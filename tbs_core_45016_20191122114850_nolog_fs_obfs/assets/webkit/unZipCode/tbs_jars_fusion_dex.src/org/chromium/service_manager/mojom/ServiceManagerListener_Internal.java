package org.chromium.service_manager.mojom;

import java.io.PrintStream;
import java.util.Arrays;
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

class ServiceManagerListener_Internal
{
  public static final Interface.Manager<ServiceManagerListener, ServiceManagerListener.Proxy> a = new Interface.Manager()
  {
    public ServiceManagerListener_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new ServiceManagerListener_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public ServiceManagerListener_Internal.Stub a(Core paramAnonymousCore, ServiceManagerListener paramAnonymousServiceManagerListener)
    {
      return new ServiceManagerListener_Internal.Stub(paramAnonymousCore, paramAnonymousServiceManagerListener);
    }
    
    public ServiceManagerListener[] a(int paramAnonymousInt)
    {
      return new ServiceManagerListener[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "service_manager::mojom::ServiceManagerListener";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements ServiceManagerListener.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void onInit(RunningServiceInfo[] paramArrayOfRunningServiceInfo)
    {
      ServiceManagerListener_Internal.ServiceManagerListenerOnInitParams localServiceManagerListenerOnInitParams = new ServiceManagerListener_Internal.ServiceManagerListenerOnInitParams();
      localServiceManagerListenerOnInitParams.a = paramArrayOfRunningServiceInfo;
      a().a().accept(localServiceManagerListenerOnInitParams.a(a().a(), new MessageHeader(0)));
    }
    
    public void onServiceCreated(RunningServiceInfo paramRunningServiceInfo)
    {
      ServiceManagerListener_Internal.ServiceManagerListenerOnServiceCreatedParams localServiceManagerListenerOnServiceCreatedParams = new ServiceManagerListener_Internal.ServiceManagerListenerOnServiceCreatedParams();
      localServiceManagerListenerOnServiceCreatedParams.a = paramRunningServiceInfo;
      a().a().accept(localServiceManagerListenerOnServiceCreatedParams.a(a().a(), new MessageHeader(1)));
    }
    
    public void onServiceFailedToStart(Identity paramIdentity)
    {
      ServiceManagerListener_Internal.ServiceManagerListenerOnServiceFailedToStartParams localServiceManagerListenerOnServiceFailedToStartParams = new ServiceManagerListener_Internal.ServiceManagerListenerOnServiceFailedToStartParams();
      localServiceManagerListenerOnServiceFailedToStartParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = paramIdentity;
      a().a().accept(localServiceManagerListenerOnServiceFailedToStartParams.a(a().a(), new MessageHeader(4)));
    }
    
    public void onServicePidReceived(Identity paramIdentity, int paramInt)
    {
      ServiceManagerListener_Internal.ServiceManagerListenerOnServicePidReceivedParams localServiceManagerListenerOnServicePidReceivedParams = new ServiceManagerListener_Internal.ServiceManagerListenerOnServicePidReceivedParams();
      localServiceManagerListenerOnServicePidReceivedParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = paramIdentity;
      localServiceManagerListenerOnServicePidReceivedParams.jdField_a_of_type_Int = paramInt;
      a().a().accept(localServiceManagerListenerOnServicePidReceivedParams.a(a().a(), new MessageHeader(3)));
    }
    
    public void onServiceStarted(Identity paramIdentity, int paramInt)
    {
      ServiceManagerListener_Internal.ServiceManagerListenerOnServiceStartedParams localServiceManagerListenerOnServiceStartedParams = new ServiceManagerListener_Internal.ServiceManagerListenerOnServiceStartedParams();
      localServiceManagerListenerOnServiceStartedParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = paramIdentity;
      localServiceManagerListenerOnServiceStartedParams.jdField_a_of_type_Int = paramInt;
      a().a().accept(localServiceManagerListenerOnServiceStartedParams.a(a().a(), new MessageHeader(2)));
    }
    
    public void onServiceStopped(Identity paramIdentity)
    {
      ServiceManagerListener_Internal.ServiceManagerListenerOnServiceStoppedParams localServiceManagerListenerOnServiceStoppedParams = new ServiceManagerListener_Internal.ServiceManagerListenerOnServiceStoppedParams();
      localServiceManagerListenerOnServiceStoppedParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = paramIdentity;
      a().a().accept(localServiceManagerListenerOnServiceStoppedParams.a(a().a(), new MessageHeader(5)));
    }
  }
  
  static final class ServiceManagerListenerOnInitParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public RunningServiceInfo[] a;
    
    public ServiceManagerListenerOnInitParams()
    {
      this(0);
    }
    
    private ServiceManagerListenerOnInitParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ServiceManagerListenerOnInitParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        Object localObject2 = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ServiceManagerListenerOnInitParams localServiceManagerListenerOnInitParams = new ServiceManagerListenerOnInitParams(((DataHeader)localObject2).b);
        if (((DataHeader)localObject2).b >= 0)
        {
          localObject2 = paramDecoder.a(8, false);
          DataHeader localDataHeader = ((Decoder)localObject2).b(-1);
          localServiceManagerListenerOnInitParams.jdField_a_of_type_ArrayOfOrgChromiumService_managerMojomRunningServiceInfo = new RunningServiceInfo[localDataHeader.b];
          int i = 0;
          while (i < localDataHeader.b)
          {
            Decoder localDecoder = ((Decoder)localObject2).a(i * 8 + 8, false);
            localServiceManagerListenerOnInitParams.jdField_a_of_type_ArrayOfOrgChromiumService_managerMojomRunningServiceInfo[i] = RunningServiceInfo.a(localDecoder);
            i += 1;
          }
        }
        return localServiceManagerListenerOnInitParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ServiceManagerListenerOnInitParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      RunningServiceInfo[] arrayOfRunningServiceInfo = this.jdField_a_of_type_ArrayOfOrgChromiumService_managerMojomRunningServiceInfo;
      if (arrayOfRunningServiceInfo == null)
      {
        paramEncoder.a(8, false);
        return;
      }
      paramEncoder = paramEncoder.a(arrayOfRunningServiceInfo.length, 8, -1);
      int i = 0;
      for (;;)
      {
        arrayOfRunningServiceInfo = this.jdField_a_of_type_ArrayOfOrgChromiumService_managerMojomRunningServiceInfo;
        if (i >= arrayOfRunningServiceInfo.length) {
          break;
        }
        paramEncoder.a(arrayOfRunningServiceInfo[i], i * 8 + 8, false);
        i += 1;
      }
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
      paramObject = (ServiceManagerListenerOnInitParams)paramObject;
      return Arrays.deepEquals(this.jdField_a_of_type_ArrayOfOrgChromiumService_managerMojomRunningServiceInfo, ((ServiceManagerListenerOnInitParams)paramObject).jdField_a_of_type_ArrayOfOrgChromiumService_managerMojomRunningServiceInfo);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + Arrays.deepHashCode(this.jdField_a_of_type_ArrayOfOrgChromiumService_managerMojomRunningServiceInfo);
    }
  }
  
  static final class ServiceManagerListenerOnServiceCreatedParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public RunningServiceInfo a;
    
    public ServiceManagerListenerOnServiceCreatedParams()
    {
      this(0);
    }
    
    private ServiceManagerListenerOnServiceCreatedParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ServiceManagerListenerOnServiceCreatedParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ServiceManagerListenerOnServiceCreatedParams localServiceManagerListenerOnServiceCreatedParams = new ServiceManagerListenerOnServiceCreatedParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localServiceManagerListenerOnServiceCreatedParams.jdField_a_of_type_OrgChromiumService_managerMojomRunningServiceInfo = RunningServiceInfo.a(paramDecoder.a(8, false));
        }
        return localServiceManagerListenerOnServiceCreatedParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ServiceManagerListenerOnServiceCreatedParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_OrgChromiumService_managerMojomRunningServiceInfo, 8, false);
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
      paramObject = (ServiceManagerListenerOnServiceCreatedParams)paramObject;
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomRunningServiceInfo, ((ServiceManagerListenerOnServiceCreatedParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomRunningServiceInfo);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomRunningServiceInfo);
    }
  }
  
  static final class ServiceManagerListenerOnServiceFailedToStartParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public Identity a;
    
    public ServiceManagerListenerOnServiceFailedToStartParams()
    {
      this(0);
    }
    
    private ServiceManagerListenerOnServiceFailedToStartParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ServiceManagerListenerOnServiceFailedToStartParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ServiceManagerListenerOnServiceFailedToStartParams localServiceManagerListenerOnServiceFailedToStartParams = new ServiceManagerListenerOnServiceFailedToStartParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localServiceManagerListenerOnServiceFailedToStartParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(8, false));
        }
        return localServiceManagerListenerOnServiceFailedToStartParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ServiceManagerListenerOnServiceFailedToStartParams a(Message paramMessage)
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
      paramObject = (ServiceManagerListenerOnServiceFailedToStartParams)paramObject;
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ServiceManagerListenerOnServiceFailedToStartParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
  }
  
  static final class ServiceManagerListenerOnServicePidReceivedParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
    public int a;
    public Identity a;
    
    public ServiceManagerListenerOnServicePidReceivedParams()
    {
      this(0);
    }
    
    private ServiceManagerListenerOnServicePidReceivedParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ServiceManagerListenerOnServicePidReceivedParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ServiceManagerListenerOnServicePidReceivedParams localServiceManagerListenerOnServicePidReceivedParams = new ServiceManagerListenerOnServicePidReceivedParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localServiceManagerListenerOnServicePidReceivedParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(8, false));
        }
        if (localDataHeader.b >= 0) {
          localServiceManagerListenerOnServicePidReceivedParams.jdField_a_of_type_Int = paramDecoder.a(16);
        }
        return localServiceManagerListenerOnServicePidReceivedParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ServiceManagerListenerOnServicePidReceivedParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, 8, false);
      paramEncoder.a(this.jdField_a_of_type_Int, 16);
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
      paramObject = (ServiceManagerListenerOnServicePidReceivedParams)paramObject;
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ServiceManagerListenerOnServicePidReceivedParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) {
        return false;
      }
      return this.jdField_a_of_type_Int == ((ServiceManagerListenerOnServicePidReceivedParams)paramObject).jdField_a_of_type_Int;
    }
    
    public int hashCode()
    {
      return ((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int);
    }
  }
  
  static final class ServiceManagerListenerOnServiceStartedParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
    public int a;
    public Identity a;
    
    public ServiceManagerListenerOnServiceStartedParams()
    {
      this(0);
    }
    
    private ServiceManagerListenerOnServiceStartedParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ServiceManagerListenerOnServiceStartedParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ServiceManagerListenerOnServiceStartedParams localServiceManagerListenerOnServiceStartedParams = new ServiceManagerListenerOnServiceStartedParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localServiceManagerListenerOnServiceStartedParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(8, false));
        }
        if (localDataHeader.b >= 0) {
          localServiceManagerListenerOnServiceStartedParams.jdField_a_of_type_Int = paramDecoder.a(16);
        }
        return localServiceManagerListenerOnServiceStartedParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ServiceManagerListenerOnServiceStartedParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, 8, false);
      paramEncoder.a(this.jdField_a_of_type_Int, 16);
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
      paramObject = (ServiceManagerListenerOnServiceStartedParams)paramObject;
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ServiceManagerListenerOnServiceStartedParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) {
        return false;
      }
      return this.jdField_a_of_type_Int == ((ServiceManagerListenerOnServiceStartedParams)paramObject).jdField_a_of_type_Int;
    }
    
    public int hashCode()
    {
      return ((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int);
    }
  }
  
  static final class ServiceManagerListenerOnServiceStoppedParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public Identity a;
    
    public ServiceManagerListenerOnServiceStoppedParams()
    {
      this(0);
    }
    
    private ServiceManagerListenerOnServiceStoppedParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static ServiceManagerListenerOnServiceStoppedParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        ServiceManagerListenerOnServiceStoppedParams localServiceManagerListenerOnServiceStoppedParams = new ServiceManagerListenerOnServiceStoppedParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localServiceManagerListenerOnServiceStoppedParams.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(8, false));
        }
        return localServiceManagerListenerOnServiceStoppedParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static ServiceManagerListenerOnServiceStoppedParams a(Message paramMessage)
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
      paramObject = (ServiceManagerListenerOnServiceStoppedParams)paramObject;
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((ServiceManagerListenerOnServiceStoppedParams)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
    }
  }
  
  static final class Stub
    extends Interface.Stub<ServiceManagerListener>
  {
    Stub(Core paramCore, ServiceManagerListener paramServiceManagerListener)
    {
      super(paramServiceManagerListener);
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
          paramMessage = ServiceManagerListener_Internal.ServiceManagerListenerOnServiceStoppedParams.a(paramMessage.a());
          ((ServiceManagerListener)a()).onServiceStopped(paramMessage.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
          return true;
        case 4: 
          paramMessage = ServiceManagerListener_Internal.ServiceManagerListenerOnServiceFailedToStartParams.a(paramMessage.a());
          ((ServiceManagerListener)a()).onServiceFailedToStart(paramMessage.jdField_a_of_type_OrgChromiumService_managerMojomIdentity);
          return true;
        case 3: 
          paramMessage = ServiceManagerListener_Internal.ServiceManagerListenerOnServicePidReceivedParams.a(paramMessage.a());
          ((ServiceManagerListener)a()).onServicePidReceived(paramMessage.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, paramMessage.jdField_a_of_type_Int);
          return true;
        case 2: 
          paramMessage = ServiceManagerListener_Internal.ServiceManagerListenerOnServiceStartedParams.a(paramMessage.a());
          ((ServiceManagerListener)a()).onServiceStarted(paramMessage.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, paramMessage.jdField_a_of_type_Int);
          return true;
        case 1: 
          paramMessage = ServiceManagerListener_Internal.ServiceManagerListenerOnServiceCreatedParams.a(paramMessage.a());
          ((ServiceManagerListener)a()).onServiceCreated(paramMessage.a);
          return true;
        case 0: 
          paramMessage = ServiceManagerListener_Internal.ServiceManagerListenerOnInitParams.a(paramMessage.a());
          ((ServiceManagerListener)a()).onInit(paramMessage.a);
          return true;
          boolean bool = InterfaceControlMessagesHelper.a(ServiceManagerListener_Internal.a, paramMessage);
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
        boolean bool = InterfaceControlMessagesHelper.a(a(), ServiceManagerListener_Internal.a, paramMessage, paramMessageReceiver);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\ServiceManagerListener_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */