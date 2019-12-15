package com.tencent.turingfd.sdk.tbs;

import android.text.TextUtils;
import java.lang.reflect.Method;
import java.util.HashMap;

public class r
{
  public static HashMap<String, Class<?>> a = new HashMap();
  public static HashMap<Class<?>, HashMap<String, Method>> b = new HashMap();
  
  static
  {
    new HashMap();
  }
  
  public static Object a(String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object paramObject, Object[] paramArrayOfObject)
  {
    Class localClass1;
    try
    {
      Class localClass2 = (Class)a.get(paramString1);
      localClass1 = localClass2;
      if (localClass2 != null) {
        break label89;
      }
      localClass1 = Class.forName(paramString1);
    }
    catch (Throwable paramString1) {}
    a.put(paramString1, localClass1);
    label89:
    for (;;)
    {
      try
      {
        paramString1.printStackTrace();
        localClass1 = null;
        try
        {
          paramString1 = a(localClass1, paramString2, paramArrayOfClass);
          if (paramString1 == null) {
            return null;
          }
          paramString1 = paramString1.invoke(paramObject, paramArrayOfObject);
          return paramString1;
        }
        catch (Throwable paramString1)
        {
          paramString1.printStackTrace();
          return null;
        }
        if (localClass1 != null) {
          break;
        }
      }
      catch (Throwable paramString1)
      {
        paramString1.printStackTrace();
        return null;
      }
    }
  }
  
  public static Method a(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
  {
    if (paramClass != null) {}
    Object localObject2;
    do
    {
      try
      {
        if (TextUtils.isEmpty(paramString)) {
          return null;
        }
        localObject1 = new StringBuffer(paramString);
        if (paramVarArgs != null)
        {
          int i = 0;
          while (i < paramVarArgs.length)
          {
            localObject2 = paramVarArgs[i];
            ((StringBuffer)localObject1).append("+");
            ((StringBuffer)localObject1).append(((Class)localObject2).getName());
            i += 1;
          }
        }
        str = ((StringBuffer)localObject1).toString();
        localObject2 = (HashMap)b.get(paramClass);
        localObject1 = localObject2;
        if (localObject2 == null)
        {
          localObject1 = new HashMap();
          b.put(paramClass, localObject1);
        }
        Method localMethod = (Method)((HashMap)localObject1).get(str);
        localObject2 = localMethod;
        if (localMethod != null) {
          continue;
        }
        localObject2 = paramClass.getDeclaredMethod(paramString, paramVarArgs);
      }
      catch (Throwable paramClass)
      {
        Object localObject1;
        String str;
        paramClass.printStackTrace();
      }
      ((Method)localObject2).setAccessible(true);
      ((HashMap)localObject1).put(str, localObject2);
      return (Method)localObject2;
      return null;
    } while (localObject2 != null);
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\r.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */