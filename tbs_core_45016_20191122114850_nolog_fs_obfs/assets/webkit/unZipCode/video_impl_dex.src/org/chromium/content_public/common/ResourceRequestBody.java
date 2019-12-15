package org.chromium.content_public.common;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("content")
public final class ResourceRequestBody
{
  private byte[] a;
  
  private ResourceRequestBody(byte[] paramArrayOfByte)
  {
    this.a = paramArrayOfByte;
  }
  
  public static ResourceRequestBody a(byte[] paramArrayOfByte)
  {
    return createFromEncodedNativeForm(nativeCreateResourceRequestBodyFromBytes(paramArrayOfByte));
  }
  
  @CalledByNative
  private static ResourceRequestBody createFromEncodedNativeForm(byte[] paramArrayOfByte)
  {
    return new ResourceRequestBody(paramArrayOfByte);
  }
  
  @CalledByNative
  private byte[] getEncodedNativeForm()
  {
    return this.a;
  }
  
  private static native byte[] nativeCreateResourceRequestBodyFromBytes(byte[] paramArrayOfByte);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\common\ResourceRequestBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */