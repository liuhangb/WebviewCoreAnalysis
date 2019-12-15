package com.tencent.beacontbs.a;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Process;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class a
{
  private static int jdField_a_of_type_Int = 0;
  private static Boolean jdField_a_of_type_JavaLangBoolean;
  protected static String a;
  public static boolean a = false;
  private static String b;
  private static String c;
  private static String d = "";
  
  public static int a(Context paramContext)
  {
    try
    {
      if (jdField_a_of_type_Int == 0) {
        jdField_a_of_type_Int = Process.myPid();
      }
      paramContext = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses().iterator();
      while (paramContext.hasNext())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)paramContext.next();
        if (localRunningAppProcessInfo.pid == jdField_a_of_type_Int)
        {
          int i = localRunningAppProcessInfo.importance;
          return i;
        }
      }
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return 0;
  }
  
  public static String a()
  {
    if (!"".equals(d)) {
      return d;
    }
    try
    {
      if (jdField_a_of_type_Int == 0) {
        jdField_a_of_type_Int = Process.myPid();
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(d);
      localStringBuilder.append(jdField_a_of_type_Int);
      localStringBuilder.append("_");
      d = localStringBuilder.toString();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(d);
      localStringBuilder.append(new Date().getTime());
      d = localStringBuilder.toString();
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return d;
  }
  
  public static String a(Context paramContext)
  {
    if (paramContext == null) {
      return "";
    }
    String str = "";
    Object localObject8 = null;
    Object localObject9 = null;
    Object localObject7 = null;
    Object localObject1;
    for (;;)
    {
      AssetManager localAssetManager;
      Object localObject3;
      Object localObject5;
      Object localObject2;
      Object localObject6;
      try
      {
        localAssetManager = paramContext.getAssets();
        localObject3 = localObject8;
        localObject1 = str;
        localObject5 = localObject9;
      }
      finally {}
      try
      {
        try
        {
          localObject2 = paramContext.getSharedPreferences("DENGTA_META", 0).getString("key_channelpath", "");
          localObject6 = localObject2;
          localObject3 = localObject8;
          localObject1 = str;
          localObject5 = localObject9;
          if (((String)localObject2).equals(""))
          {
            localObject6 = "channel.ini";
            localObject3 = localObject8;
            localObject1 = str;
            localObject5 = localObject9;
            paramContext.getSharedPreferences("DENGTA_META", 0).edit().putString("key_channelpath", "channel.ini").commit();
          }
          localObject3 = localObject8;
          localObject1 = str;
          localObject5 = localObject9;
          localObject2 = new StringBuilder("channel path!! ");
          localObject3 = localObject8;
          localObject1 = str;
          localObject5 = localObject9;
          ((StringBuilder)localObject2).append((String)localObject6);
          localObject3 = localObject8;
          localObject1 = str;
          localObject5 = localObject9;
          com.tencent.beacontbs.c.a.a(((StringBuilder)localObject2).toString(), new Object[0]);
          localObject2 = str;
          localObject3 = localObject8;
          localObject1 = str;
          localObject5 = localObject9;
          if (!((String)localObject6).equals(""))
          {
            localObject3 = localObject8;
            localObject1 = str;
            localObject5 = localObject9;
            localObject6 = localAssetManager.open((String)localObject6);
            localObject3 = localObject6;
            localObject1 = str;
            localObject5 = localObject6;
            localObject2 = new Properties();
            localObject3 = localObject6;
            localObject1 = str;
            localObject5 = localObject6;
            ((Properties)localObject2).load((InputStream)localObject6);
            localObject3 = localObject6;
            localObject1 = str;
            localObject5 = localObject6;
            str = ((Properties)localObject2).getProperty("CHANNEL", "");
            localObject3 = localObject6;
            localObject1 = str;
            localObject5 = localObject6;
            localObject2 = new StringBuilder("channel !! ");
            localObject3 = localObject6;
            localObject1 = str;
            localObject5 = localObject6;
            ((StringBuilder)localObject2).append(str);
            localObject3 = localObject6;
            localObject1 = str;
            localObject5 = localObject6;
            com.tencent.beacontbs.c.a.a(((StringBuilder)localObject2).toString(), new Object[0]);
            localObject3 = localObject6;
            localObject1 = str;
            localObject5 = localObject6;
            boolean bool = "".equals(str);
            localObject2 = str;
            localObject7 = localObject6;
            if (!bool)
            {
              if (localObject6 != null) {
                try
                {
                  ((InputStream)localObject6).close();
                }
                catch (IOException paramContext)
                {
                  com.tencent.beacontbs.c.a.a(paramContext);
                }
              }
              return str;
            }
          }
          localObject3 = localObject2;
          if (localObject7 == null) {
            continue;
          }
          try
          {
            ((InputStream)localObject7).close();
            localObject3 = localObject2;
          }
          catch (IOException localIOException2)
          {
            localObject1 = localObject2;
          }
          com.tencent.beacontbs.c.a.a(localIOException2);
          localObject4 = localObject1;
        }
        finally
        {
          continue;
        }
      }
      catch (Exception localException)
      {
        continue;
      }
      Object localObject4 = localObject5;
      paramContext.getSharedPreferences("DENGTA_META", 0).edit().putString("key_channelpath", "").commit();
      localObject4 = localObject5;
      com.tencent.beacontbs.c.a.c("get app channel fail!", new Object[0]);
      localObject4 = localObject1;
      if (localObject5 == null) {
        continue;
      }
      try
      {
        ((InputStream)localObject5).close();
        localObject4 = localObject1;
      }
      catch (IOException localIOException3) {}
    }
    try
    {
      localObject1 = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128).metaData.get("CHANNEL_DENGTA");
      paramContext = localIOException3;
      if (localObject1 == null) {
        break label558;
      }
      paramContext = localObject1.toString();
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    com.tencent.beacontbs.c.a.a("no channel !!", new Object[0]);
    paramContext = localIOException3;
    label558:
    return paramContext;
    if (localIOException3 != null) {
      try
      {
        localIOException3.close();
      }
      catch (IOException localIOException1)
      {
        com.tencent.beacontbs.c.a.a(localIOException1);
      }
    }
    throw paramContext;
  }
  
  public static String a(Context paramContext, String paramString1, String paramString2)
  {
    return paramContext.getSharedPreferences("DENGTA_META", 0).getString(paramString1, paramString2);
  }
  
  public static void a(Context paramContext, String paramString)
  {
    paramContext.getSharedPreferences("DENGTA_META", 0).edit().putString("querytimes", paramString).commit();
  }
  
  public static void a(String paramString)
  {
    try
    {
      jdField_a_of_type_JavaLangString = paramString;
      c localc = c.a();
      if (localc != null) {
        localc.a(paramString);
      }
      return;
    }
    finally {}
  }
  
  public static boolean a(Context paramContext)
  {
    if (paramContext == null) {
      try
      {
        com.tencent.beacontbs.c.a.d("context == null return null", new Object[0]);
        return false;
      }
      finally
      {
        break label131;
      }
    }
    boolean bool1;
    try
    {
      String str2 = paramContext.getSharedPreferences("DENGTA_META", 4).getString("APPKEY_DENGTA", null);
      String str1 = com.tencent.beacontbs.event.c.c();
      if (str2 != null)
      {
        bool1 = str2.equals(str1);
        if (bool1)
        {
          bool1 = false;
          break label126;
        }
      }
      boolean bool2 = true;
      bool1 = true;
      try
      {
        paramContext = paramContext.getSharedPreferences("DENGTA_META", 0).edit();
        paramContext.putString("APPKEY_DENGTA", str1);
        paramContext.commit();
        bool1 = bool2;
      }
      catch (Exception paramContext) {}
      com.tencent.beacontbs.c.a.b("updateLocalAPPKEY fail!", new Object[0]);
    }
    catch (Exception paramContext)
    {
      bool1 = false;
    }
    com.tencent.beacontbs.c.a.a(paramContext);
    label126:
    return bool1;
    label131:
    throw paramContext;
  }
  
  public static boolean a(Context paramContext, int paramInt)
  {
    try
    {
      Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses().iterator();
      while (localIterator.hasNext())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        if (localRunningAppProcessInfo.pid == paramInt)
        {
          boolean bool = localRunningAppProcessInfo.processName.split(":")[0].equals(d(paramContext).split(":")[0]);
          if (bool) {
            return true;
          }
        }
      }
      return false;
    }
    catch (Exception paramContext) {}
    return false;
  }
  
  /* Error */
  private static boolean a(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +142 -> 143
    //   4: aload_0
    //   5: aload_1
    //   6: invokestatic 32	android/os/Process:myPid	()I
    //   9: invokestatic 257	android/os/Process:myUid	()I
    //   12: invokevirtual 261	android/content/Context:checkPermission	(Ljava/lang/String;II)I
    //   15: ifne +9 -> 24
    //   18: iconst_1
    //   19: istore 4
    //   21: goto +6 -> 27
    //   24: iconst_0
    //   25: istore 4
    //   27: iload 4
    //   29: ifne +111 -> 140
    //   32: aload_0
    //   33: invokevirtual 188	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   36: aload_0
    //   37: invokevirtual 191	android/content/Context:getPackageName	()Ljava/lang/String;
    //   40: sipush 4096
    //   43: invokevirtual 265	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   46: getfield 271	android/content/pm/PackageInfo:requestedPermissions	[Ljava/lang/String;
    //   49: astore_0
    //   50: iload 4
    //   52: istore 5
    //   54: aload_0
    //   55: ifnull +44 -> 99
    //   58: aload_0
    //   59: arraylength
    //   60: istore_3
    //   61: iconst_0
    //   62: istore_2
    //   63: iload 4
    //   65: istore 5
    //   67: iload_2
    //   68: iload_3
    //   69: if_icmpge +30 -> 99
    //   72: aload_1
    //   73: aload_0
    //   74: iload_2
    //   75: aaload
    //   76: invokevirtual 79	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   79: istore 5
    //   81: iload 5
    //   83: ifeq +9 -> 92
    //   86: iconst_1
    //   87: istore 5
    //   89: goto +10 -> 99
    //   92: iload_2
    //   93: iconst_1
    //   94: iadd
    //   95: istore_2
    //   96: goto -33 -> 63
    //   99: ldc_w 273
    //   102: iconst_0
    //   103: anewarray 4	java/lang/Object
    //   106: invokestatic 241	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   109: iload 5
    //   111: ireturn
    //   112: astore_0
    //   113: goto +15 -> 128
    //   116: astore_0
    //   117: aload_0
    //   118: invokestatic 180	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   121: iload 4
    //   123: istore 5
    //   125: goto -26 -> 99
    //   128: ldc_w 273
    //   131: iconst_0
    //   132: anewarray 4	java/lang/Object
    //   135: invokestatic 241	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   138: aload_0
    //   139: athrow
    //   140: iload 4
    //   142: ireturn
    //   143: ldc_w 275
    //   146: iconst_0
    //   147: anewarray 4	java/lang/Object
    //   150: invokestatic 231	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   153: iconst_0
    //   154: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	155	0	paramContext	Context
    //   0	155	1	paramString	String
    //   62	34	2	i	int
    //   60	10	3	j	int
    //   19	122	4	bool1	boolean
    //   52	72	5	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   32	50	112	finally
    //   58	61	112	finally
    //   72	81	112	finally
    //   117	121	112	finally
    //   32	50	116	java/lang/Throwable
    //   58	61	116	java/lang/Throwable
    //   72	81	116	java/lang/Throwable
  }
  
  public static boolean a(Context paramContext, String paramString1, String paramString2)
  {
    return paramContext.getSharedPreferences("DENGTA_META", 0).edit().putString(paramString1, paramString2).commit();
  }
  
  public static String b(Context paramContext)
  {
    if (paramContext == null) {
      return null;
    }
    return paramContext.getPackageName();
  }
  
  public static void b(Context paramContext, String paramString)
  {
    paramContext.getSharedPreferences("DENGTA_META", 0).edit().putString("initsdkdate", paramString).commit();
  }
  
  public static boolean b(Context paramContext)
  {
    if (paramContext == null)
    {
      com.tencent.beacontbs.c.a.d("context == null return null", new Object[0]);
      return false;
    }
    boolean bool;
    try
    {
      String str2 = paramContext.getSharedPreferences("DENGTA_META", 0).getString("APPVER_DENGTA", null);
      String str1 = e(paramContext);
      if (str2 != null)
      {
        bool = str2.equals(str1);
        if (bool) {
          return false;
        }
      }
      bool = true;
      try
      {
        paramContext = paramContext.getSharedPreferences("DENGTA_META", 0).edit();
        paramContext.putString("APPVER_DENGTA", str1);
        paramContext.commit();
        return true;
      }
      catch (Exception paramContext) {}
      com.tencent.beacontbs.c.a.b("updateLocalAPPKEY fail!", new Object[0]);
    }
    catch (Exception paramContext)
    {
      bool = false;
    }
    com.tencent.beacontbs.c.a.a(paramContext);
    return bool;
  }
  
  private static boolean b(Context paramContext, String paramString)
  {
    if ((paramContext != null) && (paramString != null)) {
      if (paramString.trim().length() <= 0) {
        return false;
      }
    }
    for (;;)
    {
      int i;
      try
      {
        paramContext = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses();
        if ((paramContext != null) && (paramContext.size() != 0))
        {
          paramContext = paramContext.iterator();
          if (paramContext.hasNext())
          {
            Object localObject = (ActivityManager.RunningAppProcessInfo)paramContext.next();
            if (((ActivityManager.RunningAppProcessInfo)localObject).importance != 100) {
              continue;
            }
            localObject = ((ActivityManager.RunningAppProcessInfo)localObject).pkgList;
            int j = localObject.length;
            i = 0;
            if (i >= j) {
              continue;
            }
            if (!paramString.equals(localObject[i])) {
              break label155;
            }
            return true;
          }
        }
        else
        {
          com.tencent.beacontbs.c.a.b("no running proc", new Object[0]);
          return false;
        }
      }
      catch (Throwable paramContext)
      {
        com.tencent.beacontbs.c.a.a(paramContext);
        com.tencent.beacontbs.c.a.d("Failed to judge }[%s]", new Object[] { paramContext.getLocalizedMessage() });
      }
      return false;
      return false;
      label155:
      i += 1;
    }
  }
  
  public static String c(Context paramContext)
  {
    if (b == null) {
      b = e(paramContext);
    }
    return b;
  }
  
  public static void c(Context paramContext, String paramString)
  {
    paramContext.getSharedPreferences("DENGTA_META", 0).edit().putString("pid_stat", paramString).commit();
  }
  
  public static boolean c(Context paramContext)
  {
    try
    {
      com.tencent.beacontbs.c.a.b("Read phone state permission check! start ", new Object[0]);
      if (jdField_a_of_type_JavaLangBoolean == null) {
        jdField_a_of_type_JavaLangBoolean = Boolean.valueOf(a(paramContext, "android.permission.READ_PHONE_STATE"));
      }
      boolean bool = jdField_a_of_type_JavaLangBoolean.booleanValue();
      return bool;
    }
    finally {}
  }
  
  public static String d(Context paramContext)
  {
    Object localObject = c;
    if (localObject != null) {
      return (String)localObject;
    }
    try
    {
      if (jdField_a_of_type_Int == 0) {
        jdField_a_of_type_Int = Process.myPid();
      }
      paramContext = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses().iterator();
      while (paramContext.hasNext())
      {
        localObject = (ActivityManager.RunningAppProcessInfo)paramContext.next();
        if (((ActivityManager.RunningAppProcessInfo)localObject).pid == jdField_a_of_type_Int)
        {
          paramContext = ((ActivityManager.RunningAppProcessInfo)localObject).processName;
          c = paramContext;
          return paramContext;
        }
      }
    }
    catch (Throwable paramContext)
    {
      com.tencent.beacontbs.c.a.a(paramContext);
    }
    return "";
  }
  
  public static boolean d(Context paramContext)
  {
    return b(paramContext, paramContext.getPackageName());
  }
  
  private static String e(Context paramContext)
  {
    String str;
    if (paramContext == null) {
      str = null;
    }
    for (;;)
    {
      int k;
      try
      {
        str = paramContext.getPackageName();
        int i;
        try
        {
          paramContext = paramContext.getPackageManager().getPackageInfo(str, 0);
          str = paramContext.versionName;
          int m = paramContext.versionCode;
          if ((str != null) && (str.trim().length() > 0))
          {
            str = str.trim().replace('\n', ' ').replace('\r', ' ').replace("|", "%7C");
            paramContext = str.toCharArray();
            i = 0;
            j = 0;
            if (i < paramContext.length)
            {
              k = j;
              if (paramContext[i] == '.') {
                k = j + 1;
              }
            }
            else
            {
              paramContext = str;
              if (j < 3)
              {
                com.tencent.beacontbs.c.a.a("add versionCode: %s", new Object[] { Integer.valueOf(m) });
                paramContext = new StringBuilder();
                paramContext.append(str);
                paramContext.append(".");
                paramContext.append(m);
                paramContext = paramContext.toString();
              }
              com.tencent.beacontbs.c.a.a("version: %s", new Object[] { paramContext });
              return paramContext;
            }
          }
          else
          {
            return String.valueOf(m);
          }
        }
        catch (Exception paramContext)
        {
          com.tencent.beacontbs.c.a.a(paramContext);
          com.tencent.beacontbs.c.a.d(paramContext.toString(), new Object[0]);
          return "";
        }
        i += 1;
      }
      finally {}
      int j = k;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */