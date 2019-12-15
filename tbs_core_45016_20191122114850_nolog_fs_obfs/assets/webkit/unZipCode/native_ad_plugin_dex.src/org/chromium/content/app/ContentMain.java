package org.chromium.content.app;

import dalvik.system.DexClassLoader;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("content")
@MainDex
public class ContentMain
{
  public static DexClassLoader mICUClassLoader;
  
  private static native int nativeStart(Object paramObject);
  
  public static int start()
  {
    return nativeStart(mICUClassLoader);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\app\ContentMain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */