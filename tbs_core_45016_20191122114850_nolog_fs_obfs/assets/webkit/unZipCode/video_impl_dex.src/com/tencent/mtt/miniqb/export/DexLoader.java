package com.tencent.mtt.miniqb.export;

import android.annotation.SuppressLint;
import android.util.Log;
import dalvik.system.DexClassLoader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@SuppressLint({"NewApi"})
public class DexLoader
{
  private DexClassLoader a;
  
  public DexLoader(DexClassLoader paramDexClassLoader)
  {
    this.a = paramDexClassLoader;
  }
  
  public DexClassLoader getDexLoader()
  {
    return this.a;
  }
  
  public Object invokeMethod(Object paramObject, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramArrayOfClass = this.a.loadClass(paramString1).getMethod(paramString2, paramArrayOfClass);
      paramArrayOfClass.setAccessible(true);
      paramObject = paramArrayOfClass.invoke(paramObject, paramVarArgs);
      return paramObject;
    }
    catch (Throwable paramObject)
    {
      paramArrayOfClass = getClass().getSimpleName();
      paramVarArgs = new StringBuilder();
      paramVarArgs.append("'");
      paramVarArgs.append(paramString1);
      paramVarArgs.append("' invoke method '");
      paramVarArgs.append(paramString2);
      paramVarArgs.append("' failed");
      Log.e(paramArrayOfClass, paramVarArgs.toString(), (Throwable)paramObject);
    }
    return null;
  }
  
  public Object invokeStaticMethod(String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramArrayOfClass = this.a.loadClass(paramString1).getMethod(paramString2, paramArrayOfClass);
      paramArrayOfClass.setAccessible(true);
      paramArrayOfClass = paramArrayOfClass.invoke(null, paramVarArgs);
      return paramArrayOfClass;
    }
    catch (Throwable paramArrayOfClass)
    {
      paramVarArgs = getClass().getSimpleName();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("'");
      localStringBuilder.append(paramString1);
      localStringBuilder.append("' invoke static method '");
      localStringBuilder.append(paramString2);
      localStringBuilder.append("' failed");
      Log.e(paramVarArgs, localStringBuilder.toString(), paramArrayOfClass);
    }
    return null;
  }
  
  public Object newInstance(String paramString)
  {
    try
    {
      Object localObject = this.a.loadClass(paramString).newInstance();
      return localObject;
    }
    catch (Throwable localThrowable)
    {
      String str = getClass().getSimpleName();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("create ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(" instance failed");
      Log.e(str, localStringBuilder.toString(), localThrowable);
    }
    return null;
  }
  
  public Object newInstance(String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramArrayOfClass = this.a.loadClass(paramString).getConstructor(paramArrayOfClass).newInstance(paramVarArgs);
      return paramArrayOfClass;
    }
    catch (Throwable paramArrayOfClass)
    {
      paramVarArgs = getClass().getSimpleName();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("create '");
      localStringBuilder.append(paramString);
      localStringBuilder.append("' instance failed");
      Log.e(paramVarArgs, localStringBuilder.toString(), paramArrayOfClass);
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\miniqb\export\DexLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */