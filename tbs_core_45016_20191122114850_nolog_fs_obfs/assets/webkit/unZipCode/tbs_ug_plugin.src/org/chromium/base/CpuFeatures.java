package org.chromium.base;

import org.chromium.base.annotations.JNINamespace;

@JNINamespace("base::android")
public abstract class CpuFeatures
{
  public static int getCount()
  {
    return nativeGetCoreCount();
  }
  
  public static long getMask()
  {
    return nativeGetCpuFeatures();
  }
  
  private static native int nativeGetCoreCount();
  
  private static native long nativeGetCpuFeatures();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\CpuFeatures.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */