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
import org.chromium.mojo.bindings.InterfaceRequest;
import org.chromium.mojo.bindings.Message;
import org.chromium.mojo.bindings.MessageHeader;
import org.chromium.mojo.bindings.MessageReceiver;
import org.chromium.mojo.bindings.MessageReceiverWithResponder;
import org.chromium.mojo.bindings.ServiceMessage;
import org.chromium.mojo.bindings.SideEffectFreeCloseable;
import org.chromium.mojo.bindings.Struct;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.DataPipe.ProducerHandle;
import org.chromium.mojo.system.InvalidHandle;

class Blob_Internal
{
  public static final Interface.Manager<Blob, Blob.Proxy> a = new Interface.Manager()
  {
    public Blob_Internal.Proxy a(Core paramAnonymousCore, MessageReceiverWithResponder paramAnonymousMessageReceiverWithResponder)
    {
      return new Blob_Internal.Proxy(paramAnonymousCore, paramAnonymousMessageReceiverWithResponder);
    }
    
    public Blob_Internal.Stub a(Core paramAnonymousCore, Blob paramAnonymousBlob)
    {
      return new Blob_Internal.Stub(paramAnonymousCore, paramAnonymousBlob);
    }
    
    public Blob[] a(int paramAnonymousInt)
    {
      return new Blob[paramAnonymousInt];
    }
    
    public String getName()
    {
      return "blink::mojom::Blob";
    }
    
    public int getVersion()
    {
      return 0;
    }
  };
  
  static final class BlobCloneParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public InterfaceRequest<Blob> a;
    
    public BlobCloneParams()
    {
      this(0);
    }
    
    private BlobCloneParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static BlobCloneParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        BlobCloneParams localBlobCloneParams = new BlobCloneParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localBlobCloneParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest = paramDecoder.a(8, false);
        }
        return localBlobCloneParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static BlobCloneParams a(Message paramMessage)
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
      paramObject = (BlobCloneParams)paramObject;
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest, ((BlobCloneParams)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfaceRequest);
    }
  }
  
  static final class BlobGetInternalUuidParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(8, 0) };
    
    public BlobGetInternalUuidParams()
    {
      this(0);
    }
    
    private BlobGetInternalUuidParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static BlobGetInternalUuidParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        BlobGetInternalUuidParams localBlobGetInternalUuidParams = new BlobGetInternalUuidParams(paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader).b);
        return localBlobGetInternalUuidParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static BlobGetInternalUuidParams a(Message paramMessage)
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
  
  static final class BlobGetInternalUuidResponseParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
    public String a;
    
    public BlobGetInternalUuidResponseParams()
    {
      this(0);
    }
    
    private BlobGetInternalUuidResponseParams(int paramInt)
    {
      super(paramInt);
    }
    
    public static BlobGetInternalUuidResponseParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        BlobGetInternalUuidResponseParams localBlobGetInternalUuidResponseParams = new BlobGetInternalUuidResponseParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localBlobGetInternalUuidResponseParams.jdField_a_of_type_JavaLangString = paramDecoder.a(8, false);
        }
        return localBlobGetInternalUuidResponseParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static BlobGetInternalUuidResponseParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_JavaLangString, 8, false);
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
      paramObject = (BlobGetInternalUuidResponseParams)paramObject;
      return BindingsHelper.a(this.jdField_a_of_type_JavaLangString, ((BlobGetInternalUuidResponseParams)paramObject).jdField_a_of_type_JavaLangString);
    }
    
    public int hashCode()
    {
      return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_JavaLangString);
    }
  }
  
  static class BlobGetInternalUuidResponseParamsForwardToCallback
    extends SideEffectFreeCloseable
    implements MessageReceiver
  {
    private final Blob.GetInternalUuidResponse a;
    
    BlobGetInternalUuidResponseParamsForwardToCallback(Blob.GetInternalUuidResponse paramGetInternalUuidResponse)
    {
      this.a = paramGetInternalUuidResponse;
    }
    
    public boolean accept(Message paramMessage)
    {
      try
      {
        paramMessage = paramMessage.a();
        if (!paramMessage.a().a(3, 2)) {
          return false;
        }
        paramMessage = Blob_Internal.BlobGetInternalUuidResponseParams.a(paramMessage.a());
        this.a.call(paramMessage.a);
        return true;
      }
      catch (DeserializationException paramMessage) {}
      return false;
    }
  }
  
  static class BlobGetInternalUuidResponseParamsProxyToResponder
    implements Blob.GetInternalUuidResponse
  {
    private final long jdField_a_of_type_Long;
    private final MessageReceiver jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
    private final Core jdField_a_of_type_OrgChromiumMojoSystemCore;
    
    BlobGetInternalUuidResponseParamsProxyToResponder(Core paramCore, MessageReceiver paramMessageReceiver, long paramLong)
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver = paramMessageReceiver;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void a(String paramString)
    {
      Blob_Internal.BlobGetInternalUuidResponseParams localBlobGetInternalUuidResponseParams = new Blob_Internal.BlobGetInternalUuidResponseParams();
      localBlobGetInternalUuidResponseParams.a = paramString;
      paramString = localBlobGetInternalUuidResponseParams.a(this.jdField_a_of_type_OrgChromiumMojoSystemCore, new MessageHeader(3, 2, this.jdField_a_of_type_Long));
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver.accept(paramString);
    }
  }
  
  static final class BlobReadAllParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
    public BlobReaderClient a;
    public DataPipe.ProducerHandle a;
    
    public BlobReadAllParams()
    {
      this(0);
    }
    
    private BlobReadAllParams(int paramInt)
    {
      super(paramInt);
      this.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle = InvalidHandle.a;
    }
    
    public static BlobReadAllParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        BlobReadAllParams localBlobReadAllParams = new BlobReadAllParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localBlobReadAllParams.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle = paramDecoder.a(8, false);
        }
        if (localDataHeader.b >= 0) {
          localBlobReadAllParams.jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient = ((BlobReaderClient)paramDecoder.a(12, true, BlobReaderClient.a));
        }
        return localBlobReadAllParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static BlobReadAllParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle, 8, false);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient, 12, true, BlobReaderClient.a);
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
      paramObject = (BlobReadAllParams)paramObject;
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle, ((BlobReadAllParams)paramObject).jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle)) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient, ((BlobReadAllParams)paramObject).jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient);
    }
    
    public int hashCode()
    {
      return ((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient);
    }
  }
  
  static final class BlobReadRangeParams
    extends Struct
  {
    private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
    private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(40, 0) };
    public long a;
    public BlobReaderClient a;
    public DataPipe.ProducerHandle a;
    public long b;
    
    public BlobReadRangeParams()
    {
      this(0);
    }
    
    private BlobReadRangeParams(int paramInt)
    {
      super(paramInt);
      this.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle = InvalidHandle.a;
    }
    
    public static BlobReadRangeParams a(Decoder paramDecoder)
    {
      if (paramDecoder == null) {
        return null;
      }
      paramDecoder.a();
      try
      {
        DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
        BlobReadRangeParams localBlobReadRangeParams = new BlobReadRangeParams(localDataHeader.b);
        if (localDataHeader.b >= 0) {
          localBlobReadRangeParams.jdField_a_of_type_Long = paramDecoder.a(8);
        }
        if (localDataHeader.b >= 0) {
          localBlobReadRangeParams.b = paramDecoder.a(16);
        }
        if (localDataHeader.b >= 0) {
          localBlobReadRangeParams.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle = paramDecoder.a(24, false);
        }
        if (localDataHeader.b >= 0) {
          localBlobReadRangeParams.jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient = ((BlobReaderClient)paramDecoder.a(28, true, BlobReaderClient.a));
        }
        return localBlobReadRangeParams;
      }
      finally
      {
        paramDecoder.b();
      }
    }
    
    public static BlobReadRangeParams a(Message paramMessage)
    {
      return a(new Decoder(paramMessage));
    }
    
    protected final void encode(Encoder paramEncoder)
    {
      paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
      paramEncoder.a(this.jdField_a_of_type_Long, 8);
      paramEncoder.a(this.b, 16);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle, 24, false);
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient, 28, true, BlobReaderClient.a);
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
      paramObject = (BlobReadRangeParams)paramObject;
      if (this.jdField_a_of_type_Long != ((BlobReadRangeParams)paramObject).jdField_a_of_type_Long) {
        return false;
      }
      if (this.b != ((BlobReadRangeParams)paramObject).b) {
        return false;
      }
      if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle, ((BlobReadRangeParams)paramObject).jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle)) {
        return false;
      }
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient, ((BlobReadRangeParams)paramObject).jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient);
    }
    
    public int hashCode()
    {
      return ((((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_Long)) * 31 + BindingsHelper.a(this.b)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient);
    }
  }
  
  static final class Proxy
    extends Interface.AbstractProxy
    implements Blob.Proxy
  {
    Proxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      super(paramMessageReceiverWithResponder);
    }
    
    public void clone(InterfaceRequest<Blob> paramInterfaceRequest)
    {
      Blob_Internal.BlobCloneParams localBlobCloneParams = new Blob_Internal.BlobCloneParams();
      localBlobCloneParams.a = paramInterfaceRequest;
      a().a().accept(localBlobCloneParams.a(a().a(), new MessageHeader(0)));
    }
    
    public void getInternalUuid(Blob.GetInternalUuidResponse paramGetInternalUuidResponse)
    {
      Blob_Internal.BlobGetInternalUuidParams localBlobGetInternalUuidParams = new Blob_Internal.BlobGetInternalUuidParams();
      a().a().acceptWithResponder(localBlobGetInternalUuidParams.a(a().a(), new MessageHeader(3, 1, 0L)), new Blob_Internal.BlobGetInternalUuidResponseParamsForwardToCallback(paramGetInternalUuidResponse));
    }
    
    public void readAll(DataPipe.ProducerHandle paramProducerHandle, BlobReaderClient paramBlobReaderClient)
    {
      Blob_Internal.BlobReadAllParams localBlobReadAllParams = new Blob_Internal.BlobReadAllParams();
      localBlobReadAllParams.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle = paramProducerHandle;
      localBlobReadAllParams.jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient = paramBlobReaderClient;
      a().a().accept(localBlobReadAllParams.a(a().a(), new MessageHeader(1)));
    }
    
    public void readRange(long paramLong1, long paramLong2, DataPipe.ProducerHandle paramProducerHandle, BlobReaderClient paramBlobReaderClient)
    {
      Blob_Internal.BlobReadRangeParams localBlobReadRangeParams = new Blob_Internal.BlobReadRangeParams();
      localBlobReadRangeParams.jdField_a_of_type_Long = paramLong1;
      localBlobReadRangeParams.b = paramLong2;
      localBlobReadRangeParams.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle = paramProducerHandle;
      localBlobReadRangeParams.jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient = paramBlobReaderClient;
      a().a().accept(localBlobReadRangeParams.a(a().a(), new MessageHeader(2)));
    }
  }
  
  static final class Stub
    extends Interface.Stub<Blob>
  {
    Stub(Core paramCore, Blob paramBlob)
    {
      super(paramBlob);
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
          paramMessage = Blob_Internal.BlobReadRangeParams.a(paramMessage.a());
          ((Blob)a()).readRange(paramMessage.jdField_a_of_type_Long, paramMessage.b, paramMessage.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle, paramMessage.jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient);
          return true;
        case 1: 
          paramMessage = Blob_Internal.BlobReadAllParams.a(paramMessage.a());
          ((Blob)a()).readAll(paramMessage.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$ProducerHandle, paramMessage.jdField_a_of_type_OrgChromiumBlinkMojomBlobReaderClient);
          return true;
        case 0: 
          paramMessage = Blob_Internal.BlobCloneParams.a(paramMessage.a());
          ((Blob)a()).clone(paramMessage.a);
          return true;
          boolean bool = InterfaceControlMessagesHelper.a(Blob_Internal.a, paramMessage);
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
        int i = localMessageHeader.b();
        if (i != -1)
        {
          if (i != 3) {
            return false;
          }
          Blob_Internal.BlobGetInternalUuidParams.a(paramMessage.a());
          ((Blob)a()).getInternalUuid(new Blob_Internal.BlobGetInternalUuidResponseParamsProxyToResponder(a(), paramMessageReceiver, localMessageHeader.a()));
          return true;
        }
        boolean bool = InterfaceControlMessagesHelper.a(a(), Blob_Internal.a, paramMessage, paramMessageReceiver);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\blink\mojom\Blob_Internal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */