package com.tencent.beacontbs.c;

import android.util.Log;
import java.util.Locale;

public final class a
{
  public static boolean a = false;
  public static boolean b = false;
  
  private static void a(String paramString1, String paramString2, Object... paramVarArgs)
  {
    if (a)
    {
      String str;
      if (paramString2 == null)
      {
        str = "null";
      }
      else
      {
        str = paramString2;
        if (paramVarArgs != null) {
          if (paramVarArgs.length == 0) {
            str = paramString2;
          } else {
            str = String.format(Locale.US, paramString2, paramVarArgs);
          }
        }
      }
      Log.d(paramString1, str);
    }
  }
  
  public static void a(String paramString, Object... paramVarArgs)
  {
    if (a)
    {
      if (paramString == null) {
        paramString = "null";
      } else if (paramVarArgs.length != 0) {
        paramString = String.format(Locale.US, paramString, paramVarArgs);
      }
      Log.i("beacon", paramString);
    }
  }
  
  public static void a(Throwable paramThrowable)
  {
    if (a)
    {
      paramThrowable.printStackTrace();
      return;
    }
    d(paramThrowable.getMessage(), new Object[0]);
  }
  
  public static void b(String paramString, Object... paramVarArgs)
  {
    if (a)
    {
      if (paramString == null) {
        paramString = "null";
      } else if (paramVarArgs.length != 0) {
        paramString = String.format(Locale.US, paramString, paramVarArgs);
      }
      Log.d("beacon", paramString);
    }
  }
  
  public static void c(String paramString, Object... paramVarArgs)
  {
    if (a)
    {
      if (paramString == null) {
        paramString = "null";
      } else if (paramVarArgs.length != 0) {
        paramString = String.format(Locale.US, paramString, paramVarArgs);
      }
      Log.w("beacon", paramString);
    }
  }
  
  public static void d(String paramString, Object... paramVarArgs)
  {
    if (a)
    {
      if (paramString == null) {
        paramString = "null";
      } else if (paramVarArgs.length != 0) {
        paramString = String.format(Locale.US, paramString, paramVarArgs);
      }
      Log.e("beacon", paramString);
    }
  }
  
  public static void e(String paramString, Object... paramVarArgs)
  {
    a("beacon_step_api", paramString, paramVarArgs);
  }
  
  public static void f(String paramString, Object... paramVarArgs)
  {
    a("beacon_step_buffer", paramString, paramVarArgs);
  }
  
  public static void g(String paramString, Object... paramVarArgs)
  {
    a("beacon_step_db", paramString, paramVarArgs);
  }
  
  public static void h(String paramString, Object... paramVarArgs)
  {
    a("beacon_step_upload", paramString, paramVarArgs);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\c\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */