package org.chromium.blink.mojom;

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

class BlobReaderClient_Internal
{
  public static final Interface.Manager<BlobReaderClient, BlobReaderClient.Proxy> a = new Interface.Manager()
  {
    public BlobReaderClient_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new BlobReaderClient_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public BlobReaderClient_Internal.Stub a(Core paramAnonymousCore, BlobReaderClient paramAnonymousBlobReaderClient)
    {
      return new BlobReaderClient_Internal.Stub(paramAnonymousCore, paramAnonymousBlobReaderClient);
    }
    
    public BlobReaderClient[] a(int paramAnonymousInt)
    {
      return new BlobReaderClient[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "blink::mojom::BlobReaderClient";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class BlobReaderClientOnCalculatedSizeParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
    public long a;
    public long b;
    
    public BlobReaderClientOnCalculatedSizeParams()
    {
      this(0);
    }
    
    private BlobReaderClientOnCalculatedSizeParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static BlobReaderClientOnCalculatedSizeParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        BlobReaderClientOnCalculatedSizeParams localBlobReaderClientOnCalculatedSizeParams = new BlobReaderClientOnCalculatedSizeParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localBlobReaderClientOnCalculatedSizeParams.jdField_a_of_type_Long = paramDecoder.a(8);
        }
        if (localDataHeader.b >= 0) {
          localBlobReaderClientOnCalculatedSizeParams.b = paramDecoder.a(16);
        }
        return localBlobReaderClientOnCalculatedSizeParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static BlobReaderClientOnCalculatedSizeParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_Long, 8);
      paramEncoder.a(this.b, 16);
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
      paramObject = (BlobReaderClientOnCalculatedSizeParams)paramObject;
      if (this.jdField_a_of_type_Long != ((BlobReaderClientOnCalculatedSizeParams)paramObject).jdField_a_of_type_Long) {
        return false;
      }
      return this.b == ((BlobReaderClientOnCalculatedSizeParams)paramObject).b;
    }
    
    public int hashCode()
    {
      return ((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_Long)) * 31 + BindingsHelper.a(this.b);
    }
  }
  
  static final class BlobReaderClientOnCompleteParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
    public int a;
    public long a;
    
    public BlobReaderClientOnCompleteParams()
    {
      this(0);
    }
    
    private BlobReaderClientOnCompleteParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static BlobReaderClientOnCompleteParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        BlobReaderClientOnCompleteParams localBlobReaderClientOnCompleteParams = new BlobReaderClientOnCompleteParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localBlobReaderClientOnCompleteParams.jdField_a_of_type_Int = paramDecoder.a(8);
        }
        if (localDataHeader.b >= 0) {
          localBlobReaderClientOnCompleteParams.jdField_a_of_type_Long = paramDecoder.a(16);
        }
        return localBlobReaderClientOnCompleteParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static BlobReaderClientOnCompleteParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_Int, 8);
      paramEncoder.a(this.jdField_a_of_type_Long, 16);
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
      paramObject = (BlobReaderClientOnCompleteParams)paramObject;
      if (this.jdField_a_of_type_Int != ((BlobReaderClientOnCompleteParams)paramObject).jdField_a_of_type_Int) {
        return false;
      }
      return this.jdField_a_of_type_Long == ((BlobReaderClientOnCompleteParams)paramObject).jdField_a_of_type_Long;
    }
    
    public int hashCode()
    {
      return ((getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int)) * 31 + BindingsHelper.a(this.jdField_a_of_type_Long);
    }
  }
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements BlobReaderClient.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void onCalculatedSize(long paramLong1, long paramLong2)
    {
      BlobReaderClient_Internal.BlobReaderClientOnCalculatedSizeParams localBlobReaderClientOnCalculatedSizeParams = new BlobReaderClient_Internal.BlobReaderClientOnCalculatedSizeParams();
      localBlobReaderClientOnCalculatedSizeParams.jdField_a_of_type_Long = paramLong1;
      localBlobReaderClientOnCalculatedSizeParams.b = paramLong2;
      a().a().accept(localBlobReaderClientOnCalculatedSizeParams.a(a().a(), new MessageHeader(0)));
    }
    
    public void onComplete(int paramInt, long paramLong)
    {
      BlobReaderClient_Internal.BlobReaderClientOnCompleteParams localBlobReaderClientOnCompleteParams = new BlobReaderClient_Internal.BlobReaderClientOnCompleteParams();
      localBlobReaderClientOnCompleteParams.jdField_a_of_type_Int = paramInt;
      localBlobReaderClientOnCompleteParams.jdField_a_of_type_Long = paramLong;
      a().a().accept(localBlobReaderClientOnCompleteParams.a(a().a(), new MessageHeader(1)));
    }
  }
  
  static final class Stub
    extends Interface.Stub<BlobReaderClient>
  {
    Stub(Core paramCore, BlobReaderClient paramBlobReaderClient)
    {
      super(paramBlobReaderClient);
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
        case 1: 
          paramMessage = BlobReaderClient_Internal.BlobReaderClientOnCompleteParams.a(paramMessage.a());
          ((BlobReaderClient)a()).onComplete(paramMessage.jdField_a_of_type_Int, paramMessage.jdField_a_of_type_Long);
          return true;
        case 0: 
          paramMessage = BlobReaderClient_Internal.BlobReaderClientOnCalculatedSizeParams.a(paramMessage.a());
          ((BlobReaderClient)a()).onCalculatedSize(paramMessage.jdField_a_of_type_Long, paramMessage.b);
          return true;
          boolean bool = InterfaceControlMessagesHelper.a(BlobReaderClient_Internal.a, paramMessage);
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
        boolean bool = InterfaceControlMessagesHelper.a(a(), BlobReaderClient_Internal.a, paramMessage, paramMessageReceiver);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\blink\mojom\BlobReaderClient_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */