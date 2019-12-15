package org.chromium.android_webview;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
class InputStreamUtil
{
  private static String a(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Got exception when calling ");
    localStringBuilder.append(paramString);
    localStringBuilder.append("() on an InputStream returned from shouldInterceptRequest. This will cause the related request to fail.");
    return localStringBuilder.toString();
  }
  
  @CalledByNative
  public static int available(InputStream paramInputStream)
  {
    try
    {
      int i = Math.max(-1, paramInputStream.available());
      return i;
    }
    catch (IOException paramInputStream)
    {
      Log.e("InputStreamUtil", a("available"), paramInputStream);
    }
    return -2;
  }
  
  @CalledByNative
  public static void close(InputStream paramInputStream)
  {
    try
    {
      paramInputStream.close();
      return;
    }
    catch (IOException paramInputStream)
    {
      Log.e("InputStreamUtil", a("close"), paramInputStream);
    }
  }
  
  @CalledByNative
  public static int read(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      paramInt1 = Math.max(-1, paramInputStream.read(paramArrayOfByte, paramInt1, paramInt2));
      return paramInt1;
    }
    catch (IOException paramInputStream)
    {
      Log.e("InputStreamUtil", a("read"), paramInputStream);
    }
    return -2;
  }
  
  @CalledByNative
  public static long skip(InputStream paramInputStream, long paramLong)
  {
    try
    {
      paramLong = Math.max(-1L, paramInputStream.skip(paramLong));
      return paramLong;
    }
    catch (IOException paramInputStream)
    {
      Log.e("InputStreamUtil", a("skip"), paramInputStream);
    }
    return -2L;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\InputStreamUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */