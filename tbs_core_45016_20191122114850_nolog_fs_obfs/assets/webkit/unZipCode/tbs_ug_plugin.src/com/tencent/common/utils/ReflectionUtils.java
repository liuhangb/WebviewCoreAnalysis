package com.tencent.common.utils;

import android.util.Log;
import dalvik.system.DexClassLoader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils
{
  private static Class<?> a(boolean paramBoolean, DexClassLoader paramDexClassLoader, String paramString)
    throws Throwable
  {
    if (paramBoolean) {
      return paramDexClassLoader.loadClass(paramString);
    }
    return Class.forName(paramString);
  }
  
  public static Field getDeclaredField(Object paramObject, String paramString)
  {
    for (paramObject = paramObject.getClass(); paramObject != Object.class; paramObject = ((Class)paramObject).getSuperclass()) {
      try
      {
        Field localField = ((Class)paramObject).getDeclaredField(paramString);
        return localField;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
    }
    return null;
  }
  
  public static Object getInstanceField(Object paramObject, String paramString)
  {
    return getInstanceField(paramObject, null, paramString);
  }
  
  public static Object getInstanceField(Object paramObject, String paramString1, String paramString2)
  {
    if (paramObject == null) {
      return null;
    }
    if (paramString1 != null) {}
    try
    {
      paramString1 = Class.forName(paramString1, true, paramObject.getClass().getClassLoader());
      break label31;
      paramString1 = paramObject.getClass();
      label31:
      paramString1 = paramString1.getDeclaredField(paramString2);
      paramString1.setAccessible(true);
      paramObject = paramString1.get(paramObject);
      return paramObject;
    }
    catch (Throwable paramObject)
    {
      ((Throwable)paramObject).printStackTrace();
    }
    return null;
  }
  
  public static Class<?> getMemberClass(Object paramObject, String paramString)
  {
    if (paramObject != null) {
      if (paramString.isEmpty()) {
        return null;
      }
    }
    try
    {
      Field[] arrayOfField = paramObject.getClass().getDeclaredFields();
      int i = 0;
      while (i < arrayOfField.length)
      {
        Field localField = arrayOfField[i];
        if (localField.getType().toString().equalsIgnoreCase(paramString))
        {
          localField.setAccessible(true);
          paramObject = localField.get(paramObject).getClass();
          return (Class<?>)paramObject;
        }
        i += 1;
      }
      return null;
    }
    catch (Exception paramObject) {}
    return null;
    return null;
  }
  
  public static Object getStaticField(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = Class.forName(paramString1).getDeclaredField(paramString2);
      paramString1.setAccessible(true);
      paramString1 = paramString1.get(null);
      return paramString1;
    }
    catch (Throwable paramString1) {}
    return null;
  }
  
  public static Object getStaticField(String paramString1, String paramString2, ClassLoader paramClassLoader)
  {
    try
    {
      paramString1 = Class.forName(paramString1, true, paramClassLoader).getDeclaredField(paramString2);
      paramString1.setAccessible(true);
      paramString1 = paramString1.get(null);
      return paramString1;
    }
    catch (Throwable paramString1) {}
    return null;
  }
  
  public static Object getStaticField(boolean paramBoolean, DexClassLoader paramDexClassLoader, String paramString1, String paramString2)
  {
    try
    {
      paramDexClassLoader = a(paramBoolean, paramDexClassLoader, paramString1).getField(paramString2);
      paramDexClassLoader.setAccessible(true);
      paramDexClassLoader = paramDexClassLoader.get(null);
      return paramDexClassLoader;
    }
    catch (Throwable paramDexClassLoader)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("'");
      localStringBuilder.append(paramString1);
      localStringBuilder.append("' get field '");
      localStringBuilder.append(paramString2);
      localStringBuilder.append("' failed");
      Log.e("ReflectionUtils", localStringBuilder.toString(), paramDexClassLoader);
    }
    return null;
  }
  
  public static boolean hasMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
  {
    if (paramClass != null) {
      if (paramString.isEmpty()) {
        return false;
      }
    }
    try
    {
      paramClass.getDeclaredMethod(paramString, paramVarArgs);
      return true;
    }
    catch (NoSuchMethodException paramClass) {}
    return false;
    return false;
  }
  
  public static Object invokeInstance(Object paramObject, String paramString)
  {
    return invokeInstance(paramObject, paramString, null, new Object[0]);
  }
  
  public static Object invokeInstance(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (paramObject == null) {
      return null;
    }
    try
    {
      paramString = paramObject.getClass().getMethod(paramString, paramArrayOfClass);
      paramString.setAccessible(true);
      if (paramVarArgs.length == 0) {
        return paramString.invoke(paramObject, new Object[0]);
      }
      paramObject = paramString.invoke(paramObject, paramVarArgs);
      return paramObject;
    }
    catch (Throwable paramObject)
    {
      ((Throwable)paramObject).printStackTrace();
    }
    return null;
  }
  
  public static Object invokeMethod(boolean paramBoolean, DexClassLoader paramDexClassLoader, Object paramObject, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramDexClassLoader = a(paramBoolean, paramDexClassLoader, paramString1).getMethod(paramString2, paramArrayOfClass);
      paramDexClassLoader.setAccessible(true);
      paramDexClassLoader = paramDexClassLoader.invoke(paramObject, paramVarArgs);
      return paramDexClassLoader;
    }
    catch (Throwable paramDexClassLoader)
    {
      paramObject = new StringBuilder();
      ((StringBuilder)paramObject).append("'");
      ((StringBuilder)paramObject).append(paramString1);
      ((StringBuilder)paramObject).append("' invoke method '");
      ((StringBuilder)paramObject).append(paramString2);
      ((StringBuilder)paramObject).append("' failed");
      Log.e("ReflectionUtils", ((StringBuilder)paramObject).toString(), paramDexClassLoader);
    }
    return null;
  }
  
  public static Object invokeStatic(Class<?> paramClass, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramClass = paramClass.getMethod(paramString, paramArrayOfClass);
      paramClass.setAccessible(true);
      paramClass = paramClass.invoke(null, paramVarArgs);
      return paramClass;
    }
    catch (Throwable paramClass)
    {
      paramClass.printStackTrace();
    }
    return null;
  }
  
  public static Object invokeStatic(String paramString1, String paramString2)
  {
    return invokeStatic(paramString1, paramString2, null);
  }
  
  public static Object invokeStatic(String paramString1, String paramString2, ClassLoader paramClassLoader)
  {
    if (paramClassLoader == null) {}
    try
    {
      paramString1 = Class.forName(paramString1);
      break label19;
      paramString1 = Class.forName(paramString1, true, paramClassLoader);
      label19:
      paramString1 = paramString1.getMethod(paramString2, new Class[0]).invoke(null, new Object[0]);
      return paramString1;
    }
    catch (Throwable paramString1) {}
    return null;
  }
  
  public static Object invokeStaticMethod(boolean paramBoolean, DexClassLoader paramDexClassLoader, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramDexClassLoader = a(paramBoolean, paramDexClassLoader, paramString1).getMethod(paramString2, paramArrayOfClass);
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
      Log.e("ReflectionUtils", paramArrayOfClass.toString(), paramDexClassLoader);
    }
    return null;
  }
  
  public static Class<?> loadClass(boolean paramBoolean, DexClassLoader paramDexClassLoader, String paramString)
  {
    if (paramBoolean) {}
    try
    {
      return paramDexClassLoader.loadClass(paramString);
    }
    catch (Throwable paramDexClassLoader)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("loadClass '");
      localStringBuilder.append(paramString);
      localStringBuilder.append("' failed");
      Log.e("ReflectionUtils", localStringBuilder.toString(), paramDexClassLoader);
    }
    paramDexClassLoader = Class.forName(paramString);
    return paramDexClassLoader;
    return null;
  }
  
  public static Object newInstance(DexClassLoader paramDexClassLoader, String paramString)
  {
    try
    {
      paramDexClassLoader = paramDexClassLoader.loadClass(paramString).newInstance();
      return paramDexClassLoader;
    }
    catch (Throwable paramDexClassLoader)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("create ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(" instance failed");
      Log.e("ReflectionUtils", localStringBuilder.toString(), paramDexClassLoader);
    }
    return null;
  }
  
  public static Object newInstance(DexClassLoader paramDexClassLoader, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramDexClassLoader = paramDexClassLoader.loadClass(paramString).getConstructor(paramArrayOfClass).newInstance(paramVarArgs);
      return paramDexClassLoader;
    }
    catch (Throwable paramDexClassLoader)
    {
      if ("com.tencent.smtt.webkit.adapter.X5WebViewAdapter".equalsIgnoreCase(paramString))
      {
        paramArrayOfClass = new StringBuilder();
        paramArrayOfClass.append("'newInstance ");
        paramArrayOfClass.append(paramString);
        paramArrayOfClass.append(" failed");
        Log.e("ReflectionUtils", paramArrayOfClass.toString(), paramDexClassLoader);
        return paramDexClassLoader;
      }
      paramArrayOfClass = new StringBuilder();
      paramArrayOfClass.append("create '");
      paramArrayOfClass.append(paramString);
      paramArrayOfClass.append("' instance failed");
      Log.e("ReflectionUtils", paramArrayOfClass.toString(), paramDexClassLoader);
    }
    return null;
  }
  
  public static Object newInstance(String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (paramArrayOfClass == null) {}
    try
    {
      return Class.forName(paramString).newInstance();
    }
    catch (Throwable paramArrayOfClass)
    {
      paramVarArgs = new StringBuilder();
      paramVarArgs.append("create '");
      paramVarArgs.append(paramString);
      paramVarArgs.append("' instance failed");
      Log.e("ReflectionUtils", paramVarArgs.toString(), paramArrayOfClass);
    }
    paramArrayOfClass = Class.forName(paramString).getConstructor(paramArrayOfClass).newInstance(paramVarArgs);
    return paramArrayOfClass;
    return null;
  }
  
  public static boolean setInstanceField(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramObject1 == null) {
      return false;
    }
    Class localClass = paramObject1.getClass();
    try
    {
      paramString = localClass.getDeclaredField(paramString);
      paramString.setAccessible(true);
      paramString.set(paramObject1, paramObject2);
      return true;
    }
    catch (Exception paramObject1) {}
    return false;
  }
  
  public static void setStaticField(String paramString1, String paramString2, Object paramObject1, Object paramObject2)
  {
    try
    {
      paramString1 = Class.forName(paramString1).getDeclaredField(paramString2);
      paramString1.setAccessible(true);
      paramString1.set(paramObject1, paramObject2);
      return;
    }
    catch (Throwable paramString1) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\ReflectionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */