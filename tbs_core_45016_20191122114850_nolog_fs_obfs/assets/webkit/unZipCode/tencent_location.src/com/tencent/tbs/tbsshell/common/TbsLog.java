package com.tencent.tbs.tbsshell.common;

import android.util.Log;
import java.lang.reflect.Method;

public class TbsLog
{
  private static String TBS_LOG_CLASS = "com.tencent.smtt.utils.TbsLog";
  private static boolean isTbsLogMethodsInited = false;
  private static String[] levels;
  private static Method[] tbslog_methods = { null, null, null, null, null };
  
  static
  {
    levels = new String[] { "i", "d", "e", "v", "w" };
  }
  
  public static void d(String paramString1, String paramString2)
  {
    tbslog("d", paramString1, paramString2);
  }
  
  public static void e(String paramString1, String paramString2)
  {
    tbslog("e", paramString1, paramString2);
  }
  
  public static void i(String paramString1, String paramString2)
  {
    tbslog("i", paramString1, paramString2);
  }
  
  private static void initTbsLogMethods()
    throws NoSuchMethodException, ClassNotFoundException
  {
    try
    {
      isTbsLogMethodsInited = true;
      Class localClass = Class.forName(TBS_LOG_CLASS);
      int i = 0;
      while (i < tbslog_methods.length)
      {
        tbslog_methods[i] = localClass.getMethod(levels[i], new Class[] { String.class, String.class });
        tbslog_methods[i].setAccessible(true);
        i += 1;
      }
      return;
    }
    finally {}
  }
  
  public static void tbslog(String paramString1, String paramString2, String paramString3)
  {
    int i;
    if ("i".equals(paramString1))
    {
      i = 0;
    }
    else if ("d".equals(paramString1))
    {
      i = 1;
    }
    else if ("e".equals(paramString1))
    {
      i = 2;
    }
    else if ("v".equals(paramString1))
    {
      i = 3;
    }
    else
    {
      if (!"w".equals(paramString1)) {
        break label140;
      }
      i = 4;
    }
    try
    {
      if (!isTbsLogMethodsInited) {
        initTbsLogMethods();
      }
      paramString1 = tbslog_methods[i];
      if (paramString1 != null)
      {
        paramString1.invoke(null, new Object[] { paramString2, paramString3 });
        return;
      }
    }
    catch (Throwable paramString1)
    {
      paramString3 = new StringBuilder();
      paramString3.append("tbslog exceptions:");
      paramString3.append(Log.getStackTraceString(paramString1));
      Log.e(paramString2, paramString3.toString());
    }
    return;
    label140:
    paramString3 = new StringBuilder();
    paramString3.append("tbslog exceptions - invalid level:");
    paramString3.append(paramString1);
    Log.e(paramString2, paramString3.toString());
  }
  
  public static void v(String paramString1, String paramString2)
  {
    tbslog("v", paramString1, paramString2);
  }
  
  public static void w(String paramString1, String paramString2)
  {
    tbslog("w", paramString1, paramString2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\TbsLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */