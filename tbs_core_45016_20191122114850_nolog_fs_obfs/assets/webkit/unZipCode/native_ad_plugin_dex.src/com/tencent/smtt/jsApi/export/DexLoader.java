package com.tencent.smtt.jsApi.export;

import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;
import java.lang.reflect.Method;

public class DexLoader
{
  private DexClassLoader a;
  
  public DexLoader(Context paramContext, String paramString1, String paramString2)
  {
    this(paramContext, new String[] { paramString1 }, paramString2);
  }
  
  public DexLoader(Context paramContext, String[] paramArrayOfString, String paramString)
  {
    paramContext = paramContext.getClassLoader();
    int i = 0;
    while (i < paramArrayOfString.length)
    {
      paramContext = new DexClassLoader(paramArrayOfString[i], paramString, null, paramContext);
      this.a = paramContext;
      i += 1;
    }
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
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\export\DexLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */