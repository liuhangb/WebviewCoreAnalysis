package org.chromium.net;

import org.chromium.base.annotations.JNINamespace;

@JNINamespace("net")
public final class GURLUtils
{
  public static String getOrigin(String paramString)
  {
    return nativeGetOrigin(paramString);
  }
  
  public static String getScheme(String paramString)
  {
    return nativeGetScheme(paramString);
  }
  
  private static native String nativeGetOrigin(String paramString);
  
  private static native String nativeGetScheme(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\GURLUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */