package com.tencent.mtt.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DLReflectionUtils
{
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
      paramString1 = Class.forName(paramString1);
      break label23;
      paramString1 = paramObject.getClass();
      label23:
      paramString1 = paramString1.getDeclaredField(paramString2);
      paramString1.setAccessible(true);
      paramObject = paramString1.get(paramObject);
      return paramObject;
    }
    catch (Throwable paramObject) {}
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
      paramArrayOfClass = paramObject.getClass().getMethod(paramString, paramArrayOfClass);
      paramArrayOfClass.setAccessible(true);
      paramString = paramVarArgs;
      if (paramVarArgs.length == 0) {
        paramString = null;
      }
      paramObject = paramArrayOfClass.invoke(paramObject, paramString);
      return paramObject;
    }
    catch (Throwable paramObject) {}
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
    try
    {
      paramString1 = Class.forName(paramString1).getMethod(paramString2, new Class[0]).invoke(null, new Object[0]);
      return paramString1;
    }
    catch (Throwable paramString1) {}
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DLReflectionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */