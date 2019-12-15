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
import org.chromium.mojo.bindings.InterfaceRequest;
import org.chromium.mojo.bindings.Message;
import org.chromium.mojo.bindings.MessageHeader;
import org.chromium.mojo.bindings.MessageReceiver;
import org.chromium.mojo.bindings.MessageReceiverWithResponder;
import org.chromium.mojo.bindings.ServiceMessage;
import org.chromium.mojo.bindings.Struct;
import org.chromium.mojo.system.Core;

class AndroidOverlayProvider_Internal
{
  public static final Interface.Manager<AndroidOverlayProvider, AndroidOverlayProvider.Proxy> a = new Interface.Manager()
  {
    public AndroidOverlayProvider_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new AndroidOverlayProvider_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public AndroidOverlayProvider_Internal.Stub a(Core paramAnonymousCore, AndroidOverlayProvider paramAnonymousAndroidOverlayProvider)
    {
      return new AndroidOverlayProvider_Internal.Stub(paramAnonymousCore, paramAnonymousAndroidOverlayProvider);
    }
    
    public AndroidOverlayProvider[] a(int paramAnonymousInt)
    {
      return new AndroidOverlayProvider[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "media::mojom::AndroidOverlayProvider";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class AndroidOverlayProviderCreateOverlayParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(32, 0) };
    public AndroidOverlayClient a;
    public AndroidOverlayConfig a;
    public InterfaceRequest<AndroidOverlay> a;
    
    public AndroidOverlayProviderCreateOverlayParams()
    {
      this(0);
    }
    
    private AndroidOverlayProviderCreateOverlayParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static AndroidOverlayProviderCreateOverlayParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        AndroidOverlayProviderCreateOverlayParams localAndroidOverlayProviderCreateOverlayParams = new AndroidOverlayProviderCreateOverlayParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localAndroidOverlayProviderCreateOverlayParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramDecoder.a(8, false);
        }
        if (localDataHeader.b >= 0) {
          localAndroidOverlayProviderCreateOverlayParams.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient = ((AndroidOverlayClient)paramDecoder.a(12, false, AndroidOverlayClient.a));
        }
        if (localDataHeader.b >= 0) {
          localAndroidOverlayProviderCreateOverlayParams.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayConfig = AndroidOverlayConfig.a(paramDecoder.a(24, false));
        }
        return localAndroidOverlayProviderCreateOverlayParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static AndroidOverlayProviderCreateOverlayParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, 8, false);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient, 12, false, AndroidOverlayClient.a);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayConfig, 24, false);
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
      paramObject = (AndroidOverlayProviderCreateOverlayParams)paramObject;
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, ((AndroidOverlayProviderCreateOverlayParams)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest)) {
        return false;
      }
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient, ((AndroidOverlayProviderCreateOverlayParams)paramObject).jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient)) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayConfig, ((AndroidOverlayProviderCreateOverlayParams)paramObject).jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayConfig);
    }
    
    public int hashCode()
    {
      return (((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayConfig);
    }
  }
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements AndroidOverlayProvider.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void createOverlay(InterfaceRequest<AndroidOverlay> paramInterfaceRequest, AndroidOverlayClient paramAndroidOverlayClient, AndroidOverlayConfig paramAndroidOverlayConfig)
    {
      AndroidOverlayProvider_Internal.AndroidOverlayProviderCreateOverlayParams localAndroidOverlayProviderCreateOverlayParams = new AndroidOverlayProvider_Internal.AndroidOverlayProviderCreateOverlayParams();
      localAndroidOverlayProviderCreateOverlayParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramInterfaceRequest;
      localAndroidOverlayProviderCreateOverlayParams.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient = paramAndroidOverlayClient;
      localAndroidOverlayProviderCreateOverlayParams.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayConfig = paramAndroidOverlayConfig;
      a().a().accept(localAndroidOverlayProviderCreateOverlayParams.a(a().a(), new MessageHeader(0)));
    }
  }
  
  static final class Stub
    extends Interface.Stub<AndroidOverlayProvider>
  {
    Stub(Core paramCore, AndroidOverlayProvider paramAndroidOverlayProvider)
    {
      super(paramAndroidOverlayProvider);
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
          paramMessage = AndroidOverlayProvider_Internal.AndroidOverlayProviderCreateOverlayParams.a(paramMessage.a());
          ((AndroidOverlayProvider)a()).createOverlay(paramMessage.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, paramMessage.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayClient, paramMessage.jdField_a_of_type_OrgChromiumMediaMojomAndroidOverlayConfig);
          return true;
        }
        boolean bool = InterfaceControlMessagesHelper.a(AndroidOverlayProvider_Internal.a, paramMessage);
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
        boolean bool = InterfaceControlMessagesHelper.a(a(), AndroidOverlayProvider_Internal.a, paramMessage, paramMessageReceiver);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\mojom\AndroidOverlayProvider_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */