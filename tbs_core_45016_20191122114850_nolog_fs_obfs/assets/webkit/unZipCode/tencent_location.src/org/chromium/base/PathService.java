package org.chromium.base;

import org.chromium.base.annotations.JNINamespace;

@JNINamespace("base::android")
public abstract class PathService
{
  public static final int DIR_MODULE = 3;
  
  private static native void nativeOverride(int paramInt, String paramString);
  
  public static void override(int paramInt, String paramString)
  {
    nativeOverride(paramInt, paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\PathService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */