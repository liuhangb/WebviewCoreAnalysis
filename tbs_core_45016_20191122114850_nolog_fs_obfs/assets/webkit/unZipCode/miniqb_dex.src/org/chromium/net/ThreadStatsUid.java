package org.chromium.net;

import android.net.TrafficStats;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ThreadStatsUid
{
  private static final Method a;
  private static final Method b;
  
  static
  {
    try
    {
      a = TrafficStats.class.getMethod("setThreadStatsUid", new Class[] { Integer.TYPE });
      b = TrafficStats.class.getMethod("clearThreadStatsUid", new Class[0]);
      return;
    }
    catch (SecurityException localSecurityException) {}catch (NoSuchMethodException localNoSuchMethodException) {}
    throw new RuntimeException("Unable to get TrafficStats methods", localNoSuchMethodException);
  }
  
  public static void a()
  {
    try
    {
      b.invoke(null, new Object[0]);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new RuntimeException("TrafficStats.clearThreadStatsUid failed", localInvocationTargetException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new RuntimeException("TrafficStats.clearThreadStatsUid failed", localIllegalAccessException);
    }
  }
  
  public static void a(int paramInt)
  {
    try
    {
      a.invoke(null, new Object[] { Integer.valueOf(paramInt) });
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new RuntimeException("TrafficStats.setThreadStatsUid failed", localInvocationTargetException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new RuntimeException("TrafficStats.setThreadStatsUid failed", localIllegalAccessException);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\ThreadStatsUid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */