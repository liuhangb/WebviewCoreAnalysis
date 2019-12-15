package org.chromium.base;

import org.chromium.base.annotations.JNINamespace;

@JNINamespace("base::android")
public class ImportantFileWriterAndroid
{
  private static native boolean nativeWriteFileAtomically(String paramString, byte[] paramArrayOfByte);
  
  public static boolean writeFileAtomically(String paramString, byte[] paramArrayOfByte)
  {
    return nativeWriteFileAtomically(paramString, paramArrayOfByte);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\ImportantFileWriterAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */