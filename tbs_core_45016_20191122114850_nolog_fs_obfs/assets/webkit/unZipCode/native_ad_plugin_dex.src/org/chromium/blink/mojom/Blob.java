package org.chromium.blink.mojom;

import org.chromium.mojo.bindings.Callbacks.Callback1;
import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;
import org.chromium.mojo.bindings.InterfaceRequest;
import org.chromium.mojo.system.DataPipe.ProducerHandle;

public abstract interface Blob
  extends Interface
{
  public static final Interface.Manager<Blob, Proxy> a = Blob_Internal.a;
  
  public abstract void clone(InterfaceRequest<Blob> paramInterfaceRequest);
  
  public abstract void getInternalUuid(GetInternalUuidResponse paramGetInternalUuidResponse);
  
  public abstract void readAll(DataPipe.ProducerHandle paramProducerHandle, BlobReaderClient paramBlobReaderClient);
  
  public abstract void readRange(long paramLong1, long paramLong2, DataPipe.ProducerHandle paramProducerHandle, BlobReaderClient paramBlobReaderClient);
  
  public static abstract interface GetInternalUuidResponse
    extends Callbacks.Callback1<String>
  {}
  
  public static abstract interface Proxy
    extends Blob, Interface.Proxy
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\blink\mojom\Blob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */