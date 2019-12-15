package com.tencent.beacontbs.event;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Process;
import com.tencent.beacontbs.a.b;
import com.tencent.beacontbs.a.e;
import com.tencent.beacontbs.a.f;
import java.util.HashMap;
import java.util.Map;

public class c
{
  private static Context jdField_a_of_type_AndroidContentContext;
  private static Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public final void run()
    {
      com.tencent.beacontbs.c.a.b(" db events to up on app call", new Object[0]);
      try
      {
        k.a(false);
        return;
      }
      catch (Throwable localThrowable)
      {
        com.tencent.beacontbs.c.a.a(localThrowable);
      }
    }
  };
  private static String jdField_a_of_type_JavaLangString = "";
  protected static Map<String, String> a;
  private static String b = "10000";
  private static String c = "";
  
  public static String a()
  {
    return b;
  }
  
  public static Map<String, String> a()
  {
    return jdField_a_of_type_JavaUtilMap;
  }
  
  public static void a()
  {
    b.a().a(jdField_a_of_type_JavaLangRunnable);
  }
  
  public static void a(Context paramContext)
  {
    a(paramContext, true);
  }
  
  @TargetApi(14)
  private static void a(Context paramContext, com.tencent.beacontbs.upload.a parama, boolean paramBoolean, long paramLong)
  {
    if (paramContext == null)
    {
      com.tencent.beacontbs.c.a.c(" the context is null! init beacon sdk failed!", new Object[0]);
      return;
    }
    Context localContext = paramContext.getApplicationContext();
    if (localContext != null) {
      jdField_a_of_type_AndroidContentContext = localContext;
    } else {
      jdField_a_of_type_AndroidContentContext = paramContext;
    }
    if (paramLong > 0L)
    {
      long l = paramLong;
      if (paramLong > 10000L) {
        l = 10000L;
      }
      com.tencent.beacontbs.a.b.c.a(l);
    }
    if (Integer.valueOf(Build.VERSION.SDK).intValue() >= 14)
    {
      paramContext = new f();
      ((Application)jdField_a_of_type_AndroidContentContext).registerActivityLifecycleCallbacks(paramContext);
    }
    paramContext = k.a(jdField_a_of_type_AndroidContentContext, a(paramBoolean));
    k.a(jdField_a_of_type_AndroidContentContext, paramContext, parama).b(true);
  }
  
  public static void a(Context paramContext, boolean paramBoolean)
  {
    a(paramContext, null, paramBoolean, 0L);
  }
  
  public static void a(String paramString)
  {
    if ((paramString != null) && (!paramString.trim().equals(""))) {
      com.tencent.beacontbs.a.a.a(paramString);
    }
  }
  
  public static void a(Map<String, String> paramMap)
  {
    if ((paramMap != null) && (paramMap.size() <= 20))
    {
      if (jdField_a_of_type_JavaUtilMap == null) {
        jdField_a_of_type_JavaUtilMap = new HashMap();
      }
      jdField_a_of_type_JavaUtilMap.putAll(paramMap);
    }
  }
  
  public static void a(boolean paramBoolean)
  {
    k.a(paramBoolean);
  }
  
  public static void a(boolean paramBoolean1, boolean paramBoolean2)
  {
    com.tencent.beacontbs.c.a.a = paramBoolean1;
    com.tencent.beacontbs.c.a.b = paramBoolean2;
  }
  
  public static boolean a(String paramString, boolean paramBoolean1, long paramLong1, long paramLong2, Map<String, String> paramMap, boolean paramBoolean2)
  {
    return a(paramString, paramBoolean1, paramLong1, paramLong2, paramMap, paramBoolean2, false);
  }
  
  public static boolean a(String paramString, boolean paramBoolean1, long paramLong1, long paramLong2, Map<String, String> paramMap, boolean paramBoolean2, boolean paramBoolean3)
  {
    if ((paramString != null) && (!"".equals(paramString.trim())))
    {
      Object localObject = paramString.replace('|', '_').trim();
      if (((String)localObject).length() == 0)
      {
        com.tencent.beacontbs.c.a.c("eventName is invalid!! eventName length == 0!", new Object[0]);
        paramString = null;
      }
      else if (e.a((String)localObject))
      {
        if (((String)localObject).length() > 128)
        {
          StringBuilder localStringBuilder = new StringBuilder("eventName is invalid!! eventName length should be less than 128! eventName:");
          localStringBuilder.append(paramString);
          com.tencent.beacontbs.c.a.c(localStringBuilder.toString(), new Object[0]);
          paramString = ((String)localObject).substring(0, 128);
        }
        else
        {
          paramString = (String)localObject;
        }
      }
      else
      {
        localObject = new StringBuilder("eventName is invalid!! eventName should be ASCII code in 32-126! eventName:");
        ((StringBuilder)localObject).append(paramString);
        com.tencent.beacontbs.c.a.c(((StringBuilder)localObject).toString(), new Object[0]);
        paramString = null;
      }
      if (paramString == null) {
        return false;
      }
      return k.a(paramString, paramBoolean1, paramLong1, paramLong2, paramMap, paramBoolean2, paramBoolean3);
    }
    com.tencent.beacontbs.c.a.c("param eventName is null or \"\", please check it, return false! ", new Object[0]);
    return false;
  }
  
  private static boolean a(boolean paramBoolean)
  {
    if (!paramBoolean) {
      return false;
    }
    String str = com.tencent.beacontbs.a.a.a(jdField_a_of_type_AndroidContentContext, "pid_stat", "");
    int k = Process.myPid();
    if ("".equals(str))
    {
      com.tencent.beacontbs.a.a.c(jdField_a_of_type_AndroidContentContext, String.valueOf(k));
      return true;
    }
    int i = -1;
    try
    {
      int j = Integer.valueOf(str).intValue();
      i = j;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    if (i == k) {
      return true;
    }
    if (!com.tencent.beacontbs.a.a.a(jdField_a_of_type_AndroidContentContext, i))
    {
      com.tencent.beacontbs.a.a.c(jdField_a_of_type_AndroidContentContext, str);
      return true;
    }
    return false;
  }
  
  public static String b()
  {
    return jdField_a_of_type_JavaLangString;
  }
  
  public static void b(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder(" setUserID:");
    localStringBuilder.append(paramString);
    com.tencent.beacontbs.c.a.a(localStringBuilder.toString(), new Object[0]);
    if ((paramString != null) && (paramString.trim().length() > 0) && (!"10000".equals(paramString)) && (!"10000".equals(e.a(paramString))))
    {
      b = paramString;
      if (jdField_a_of_type_JavaUtilMap == null) {
        jdField_a_of_type_JavaUtilMap = new HashMap();
      }
      jdField_a_of_type_JavaUtilMap.put("A1", paramString);
    }
  }
  
  public static String c()
  {
    return c;
  }
  
  public static void c(String paramString)
  {
    if ((paramString != null) && (!paramString.equals(""))) {
      c = paramString;
    }
  }
  
  public static String d()
  {
    if ((jdField_a_of_type_AndroidContentContext != null) && (k.a() != null)) {
      return d.a(jdField_a_of_type_AndroidContentContext).e();
    }
    com.tencent.beacontbs.c.a.d("please initUserAction first!", new Object[0]);
    String str3 = "";
    Context localContext = jdField_a_of_type_AndroidContentContext;
    String str2 = str3;
    String str1;
    if (localContext != null) {
      str1 = str3;
    }
    try
    {
      e.a(localContext);
      str1 = str3;
      str3 = e.a(jdField_a_of_type_AndroidContentContext);
      str2 = str3;
      str1 = str3;
      if ("".equals(str3))
      {
        str1 = str3;
        e.a(jdField_a_of_type_AndroidContentContext);
        str1 = str3;
        str2 = e.c(jdField_a_of_type_AndroidContentContext);
        return str2;
      }
      return str2;
    }
    catch (Exception localException) {}
    return str1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\event\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */