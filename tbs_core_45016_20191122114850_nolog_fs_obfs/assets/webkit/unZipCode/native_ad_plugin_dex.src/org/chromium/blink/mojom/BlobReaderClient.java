package org.chromium.blink.mojom;

import org.chromium.mojo.bindings.Interface;
import org.chromium.mojo.bindings.Interface.Manager;
import org.chromium.mojo.bindings.Interface.Proxy;

public abstract interface BlobReaderClient
  extends Interface
{
  public static final Interface.Manager<BlobReaderClient, Proxy> a = BlobReaderClient_Internal.a;
  
  public abstract void onCalculatedSize(long paramLong1, long paramLong2);
  
  public abstract void onComplete(int paramInt, long paramLong);
  
  public static abstract interface Proxy
    extends BlobReaderClient, Interface.Proxy
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\blink\mojom\BlobReaderClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */