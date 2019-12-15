package org.chromium.media.mojom;

import java.io.PrintStream;
import org.chromium.gfx.mojom.Rect;
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

class AndroidOverlay_Internal
{
  public static final Interface.Manager<AndroidOverlay, AndroidOverlay.Proxy> a = new Interface.Manager()
  {
    public AndroidOverlay_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new AndroidOverlay_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public AndroidOverlay_Internal.Stub a(Core paramAnonymousCore, AndroidOverlay paramAnonymousAndroidOverlay)
    {
      return new AndroidOverlay_Internal.Stub(paramAnonymousCore, paramAnonymousAndroidOverlay);
    }
    
    public AndroidOverlay[] a(int paramAnonymousInt)
    {
      return new AndroidOverlay[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "media::mojom::AndroidOverlay";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class AndroidOverlayScheduleLayoutParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public Rect a;
    
    public AndroidOverlayScheduleLayoutParams()
    {
      this(0);
    }
    
    private AndroidOverlayScheduleLayoutParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static AndroidOverlayScheduleLayoutParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        AndroidOverlayScheduleLayoutParams localAndroidOverlayScheduleLayoutParams = new AndroidOverlayScheduleLayoutParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localAndroidOverlayScheduleLayoutParams.jdField_a_of_type_OrgChromiumGfxMojomRect = Rect.a(paramDecoder.a(8, false));
        }
        return localAndroidOverlayScheduleLayoutParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static AndroidOverlayScheduleLayoutParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_OrgChromiumGfxMojomRect, 8, false);
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
      paramObject = (AndroidOverlayScheduleLayoutParams)paramObject;
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumGfxMojomRect, ((AndroidOverlayScheduleLayoutParams)paramObject).jdField_a_of_type_OrgChromiumGfxMojomRect);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumGfxMojomRect);
    }
  }
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements AndroidOverlay.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void scheduleLayout(Rect paramRect)
    {
      AndroidOverlay_Internal.AndroidOverlayScheduleLayoutParams localAndroidOverlayScheduleLayoutParams = new AndroidOverlay_Internal.AndroidOverlayScheduleLayoutParams();
      localAndroidOverlayScheduleLayoutParams.a = paramRect;
      a().a().accept(localAndroidOverlayScheduleLayoutParams.a(a().a(), new MessageHeader(0)));
    }
  }
  
  static final class Stub
    extends Interface.Stub<AndroidOverlay>
  {
    Stub(Core paramCore, AndroidOverlay paramAndroidOverlay)
    {
      super(paramAndroidOverlay);
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
          paramMessage = AndroidOverlay_Internal.AndroidOverlayScheduleLayoutParams.a(paramMessage.a());
          ((AndroidOverlay)a()).scheduleLayout(paramMessage.a);
          return true;
        }
        boolean bool = InterfaceControlMessagesHelper.a(AndroidOverlay_Internal.a, paramMessage);
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
        boolean bool = InterfaceControlMessagesHelper.a(a(), AndroidOverlay_Internal.a, paramMessage, paramMessageReceiver);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\mojom\AndroidOverlay_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */