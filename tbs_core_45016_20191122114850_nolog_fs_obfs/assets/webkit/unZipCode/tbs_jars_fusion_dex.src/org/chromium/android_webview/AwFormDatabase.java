package org.chromium.android_webview;

import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwFormDatabase
{
  public static void clearFormData() {}
  
  public static boolean hasFormData()
  {
    return nativeHasFormData();
  }
  
  private static native void nativeClearFormData();
  
  private static native boolean nativeHasFormData();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwFormDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */