package org.chromium.tencent.utils;

import android.util.Log;

public class PersistLog
{
  public static void e(String paramString1, String paramString2)
  {
    Log.e(paramString1, paramString2);
  }
  
  public static void printStackTrace(Throwable paramThrowable)
  {
    paramThrowable.printStackTrace();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\utils\PersistLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */