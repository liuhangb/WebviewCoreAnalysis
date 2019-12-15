package org.chromium.android_webview;

import java.io.File;
import java.io.IOException;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.UsedByReflection;

@JNINamespace("android_webview")
public class AwDebug
{
  @UsedByReflection("")
  public static boolean dumpWithoutCrashing(File paramFile)
  {
    try
    {
      paramFile = paramFile.getCanonicalPath();
      return nativeDumpWithoutCrashing(paramFile);
    }
    catch (IOException paramFile)
    {
      for (;;) {}
    }
    return false;
  }
  
  public static void initCrashKeysForTesting() {}
  
  private static native boolean nativeDumpWithoutCrashing(String paramString);
  
  private static native void nativeInitCrashKeysForWebViewTesting();
  
  private static native void nativeSetNonWhiteListedKeyForTesting();
  
  private static native void nativeSetWhiteListedKeyForTesting();
  
  public static void setNonWhiteListedKeyForTesting() {}
  
  public static void setWhiteListedKeyForTesting() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwDebug.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */