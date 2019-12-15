package com.tencent.mtt.video.export;

import dalvik.system.DexClassLoader;
import java.lang.reflect.Method;

public class DexLoaderUtils
{
  public static final String X5QQBROWSER_PKG_NAME = "com.tencent.mtt";
  
  public static Object invokeStaticMethod(DexClassLoader paramDexClassLoader, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramDexClassLoader = paramDexClassLoader.loadClass(paramString1).getMethod(paramString2, paramArrayOfClass);
      paramDexClassLoader.setAccessible(true);
      paramDexClassLoader = paramDexClassLoader.invoke(null, paramVarArgs);
      return paramDexClassLoader;
    }
    catch (Exception paramDexClassLoader)
    {
      paramDexClassLoader.printStackTrace();
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\DexLoaderUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */