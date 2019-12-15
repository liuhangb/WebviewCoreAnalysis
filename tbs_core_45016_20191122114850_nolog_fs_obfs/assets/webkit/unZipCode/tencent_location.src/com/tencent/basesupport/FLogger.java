package com.tencent.basesupport;

import android.util.Log;
import com.tencent.mtt.proguard.KeepNameAndPublic;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@KeepNameAndPublic
public class FLogger
{
  private static Map<String, Long> a = new ConcurrentHashMap();
  
  public static void d(String paramString1, String paramString2)
  {
    ILogger localILogger = (ILogger)ILogger.PROXY.get();
    if (localILogger != null) {
      localILogger.log(2, paramString1, paramString2);
    }
  }
  
  public static void d(String paramString1, String paramString2, Throwable paramThrowable)
  {
    ILogger localILogger = (ILogger)ILogger.PROXY.get();
    if (localILogger != null)
    {
      localILogger.log(3, paramString1, paramString2, paramThrowable);
      return;
    }
    Log.e(paramString1, paramString2, paramThrowable);
  }
  
  public static void e(String paramString1, String paramString2)
  {
    ILogger localILogger = (ILogger)ILogger.PROXY.get();
    if (localILogger != null)
    {
      localILogger.log(6, paramString1, paramString2);
      return;
    }
    Log.e(paramString1, paramString2);
  }
  
  public static void e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    ILogger localILogger = (ILogger)ILogger.PROXY.get();
    if (localILogger != null)
    {
      localILogger.log(6, paramString1, paramString2, paramThrowable);
      return;
    }
    Log.e(paramString1, paramString2, paramThrowable);
  }
  
  public static void e(String paramString, Throwable paramThrowable)
  {
    ILogger localILogger = (ILogger)ILogger.PROXY.get();
    if (localILogger != null)
    {
      localILogger.log(6, paramString, paramThrowable);
      return;
    }
    Log.e(paramString, paramThrowable.toString(), paramThrowable);
  }
  
  public static void i(String paramString1, String paramString2)
  {
    ILogger localILogger = (ILogger)ILogger.PROXY.get();
    if (localILogger != null) {
      localILogger.log(4, paramString1, paramString2);
    }
  }
  
  public static void i(String paramString1, String paramString2, Throwable paramThrowable)
  {
    ILogger localILogger = (ILogger)ILogger.PROXY.get();
    if (localILogger != null)
    {
      localILogger.log(4, paramString1, paramString2, paramThrowable);
      return;
    }
    Log.e(paramString1, paramString2, paramThrowable);
  }
  
  public static void printCostTime(String paramString1, String paramString2, String paramString3)
  {
    long l1;
    if (a.containsKey(paramString3))
    {
      localObject = (Long)a.get(paramString3);
      if (localObject == null) {
        return;
      }
      l1 = ((Long)localObject).longValue();
    }
    else
    {
      l1 = 0L;
    }
    long l2 = System.currentTimeMillis();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(", cost time:");
    ((StringBuilder)localObject).append(l2 - l1);
    d(paramString1, ((StringBuilder)localObject).toString());
    a.put(paramString3, Long.valueOf(l2));
  }
  
  public static void startTiming(String paramString)
  {
    long l = System.currentTimeMillis();
    a.put(paramString, Long.valueOf(l));
  }
  
  public static void v(String paramString1, String paramString2, Throwable paramThrowable)
  {
    ILogger localILogger = (ILogger)ILogger.PROXY.get();
    if (localILogger != null)
    {
      localILogger.log(2, paramString1, paramString2, paramThrowable);
      return;
    }
    Log.e(paramString1, paramString2, paramThrowable);
  }
  
  public static void w(String paramString1, String paramString2)
  {
    ILogger localILogger = (ILogger)ILogger.PROXY.get();
    if (localILogger != null)
    {
      localILogger.log(5, paramString1, paramString2);
      return;
    }
    Log.w(paramString1, paramString2);
  }
  
  public static void w(String paramString1, String paramString2, Throwable paramThrowable)
  {
    ILogger localILogger = (ILogger)ILogger.PROXY.get();
    if (localILogger != null)
    {
      localILogger.log(5, paramString1, paramString2, paramThrowable);
      return;
    }
    Log.e(paramString1, paramString2, paramThrowable);
  }
  
  public static void w(String paramString, Throwable paramThrowable)
  {
    ILogger localILogger = (ILogger)ILogger.PROXY.get();
    if (localILogger != null)
    {
      localILogger.log(6, paramString, paramThrowable);
      return;
    }
    Log.e(paramString, paramThrowable.toString(), paramThrowable);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\basesupport\FLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */