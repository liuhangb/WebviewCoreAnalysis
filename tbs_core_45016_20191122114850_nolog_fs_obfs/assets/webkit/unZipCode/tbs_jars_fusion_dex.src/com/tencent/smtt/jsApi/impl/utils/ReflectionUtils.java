package com.tencent.smtt.jsApi.impl.utils;

import android.util.Log;
import dalvik.system.DexClassLoader;
import java.lang.reflect.Method;

public class ReflectionUtils
{
  public static Object invokeMethod(Object paramObject, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    for (;;)
    {
      try
      {
        localClass = paramObject.getClass();
      }
      catch (Throwable paramObject)
      {
        Class localClass;
        Object localObject1;
        Object localObject2;
        paramArrayOfClass = new StringBuilder();
        paramArrayOfClass.append("'");
        paramArrayOfClass.append(paramString1);
        paramArrayOfClass.append("' invoke method '");
        paramArrayOfClass.append(paramString2);
        paramArrayOfClass.append("' failed");
        Log.e("JSAPI", paramArrayOfClass.toString(), (Throwable)paramObject);
        return null;
      }
      try
      {
        localObject1 = localClass.getMethod(paramString2, paramArrayOfClass);
      }
      catch (NoSuchMethodException localNoSuchMethodException1) {}
    }
    localObject1 = null;
    localObject2 = localObject1;
    if (localObject1 == null) {
      for (;;)
      {
        localObject2 = localObject1;
        if (localClass == null) {
          break;
        }
        localObject2 = localObject1;
        if (localClass == Object.class) {
          break;
        }
        try
        {
          localObject2 = localClass.getDeclaredMethod(paramString2, paramArrayOfClass);
          localObject1 = localObject2;
        }
        catch (NoSuchMethodException localNoSuchMethodException2)
        {
          for (;;) {}
        }
        if (localObject1 != null)
        {
          localObject2 = localObject1;
          break;
        }
        localClass = localClass.getSuperclass();
      }
    }
    paramObject = ((Method)localObject2).invoke(paramObject, paramVarArgs);
    return paramObject;
  }
  
  public static Object invokeStaticMethod(DexClassLoader paramDexClassLoader, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramDexClassLoader = paramDexClassLoader.loadClass(paramString1).getMethod(paramString2, paramArrayOfClass);
      paramDexClassLoader.setAccessible(true);
      paramDexClassLoader = paramDexClassLoader.invoke(null, paramVarArgs);
      return paramDexClassLoader;
    }
    catch (Throwable paramDexClassLoader)
    {
      paramArrayOfClass = new StringBuilder();
      paramArrayOfClass.append("'");
      paramArrayOfClass.append(paramString1);
      paramArrayOfClass.append("' invoke static method '");
      paramArrayOfClass.append(paramString2);
      paramArrayOfClass.append("' failed");
      Log.e("invokeStaticMethod", paramArrayOfClass.toString(), paramDexClassLoader);
    }
    return null;
  }
  
  public static Object invokeStaticMethod(String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramArrayOfClass = Class.forName(paramString1).getMethod(paramString2, paramArrayOfClass);
      paramArrayOfClass.setAccessible(true);
      paramArrayOfClass = paramArrayOfClass.invoke(null, paramVarArgs);
      return paramArrayOfClass;
    }
    catch (Throwable paramArrayOfClass)
    {
      paramVarArgs = new StringBuilder();
      paramVarArgs.append("'");
      paramVarArgs.append(paramString1);
      paramVarArgs.append("' invoke static method '");
      paramVarArgs.append(paramString2);
      paramVarArgs.append("' failed");
      Log.e("invokeStaticMethod", paramVarArgs.toString(), paramArrayOfClass);
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\utils\ReflectionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */