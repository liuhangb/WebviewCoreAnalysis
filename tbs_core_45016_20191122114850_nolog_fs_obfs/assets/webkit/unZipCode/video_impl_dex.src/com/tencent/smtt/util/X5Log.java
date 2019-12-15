package com.tencent.smtt.util;

import com.tencent.smtt.livelog.LiveLog;

public class X5Log
{
  private static boolean mIsEnableLog = false;
  
  public static void d(String paramString1, String paramString2)
  {
    if ((mIsEnableLog) && (paramString1 != null) && (paramString2 != null)) {
      LiveLog.d(paramString1, paramString2);
    }
  }
  
  public static void d(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if ((mIsEnableLog) && (paramString1 != null) && (paramString2 != null))
    {
      Object localObject = paramString2;
      if (paramThrowable != null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString2);
        ((StringBuilder)localObject).append(" ");
        ((StringBuilder)localObject).append(paramThrowable.toString());
        localObject = ((StringBuilder)localObject).toString();
      }
      LiveLog.d(paramString1, (String)localObject);
    }
  }
  
  public static void e(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString2 != null)) {
      LiveLog.e(paramString1, paramString2);
    }
  }
  
  public static void e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if ((paramString1 != null) && (paramString2 != null))
    {
      Object localObject = paramString2;
      if (paramThrowable != null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString2);
        ((StringBuilder)localObject).append(" ");
        ((StringBuilder)localObject).append(paramThrowable.toString());
        localObject = ((StringBuilder)localObject).toString();
      }
      LiveLog.e(paramString1, (String)localObject);
    }
  }
  
  public static void i(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString2 != null)) {
      LiveLog.i(paramString1, paramString2);
    }
  }
  
  public static void i(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if ((paramString1 != null) && (paramString2 != null))
    {
      Object localObject = paramString2;
      if (paramThrowable != null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString2);
        ((StringBuilder)localObject).append(" ");
        ((StringBuilder)localObject).append(paramThrowable.toString());
        localObject = ((StringBuilder)localObject).toString();
      }
      LiveLog.i(paramString1, (String)localObject);
    }
  }
  
  public static boolean isEnableLog()
  {
    return mIsEnableLog;
  }
  
  public static void setX5LogStatus(boolean paramBoolean)
  {
    mIsEnableLog = paramBoolean;
  }
  
  public static void v(String paramString1, String paramString2)
  {
    if ((mIsEnableLog) && (paramString1 != null) && (paramString2 != null)) {
      LiveLog.v(paramString1, paramString2);
    }
  }
  
  public static void v(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if ((mIsEnableLog) && (paramString1 != null) && (paramString2 != null))
    {
      Object localObject = paramString2;
      if (paramThrowable != null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString2);
        ((StringBuilder)localObject).append(" ");
        ((StringBuilder)localObject).append(paramThrowable.toString());
        localObject = ((StringBuilder)localObject).toString();
      }
      LiveLog.v(paramString1, (String)localObject);
    }
  }
  
  public static void w(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString2 != null)) {
      LiveLog.w(paramString1, paramString2);
    }
  }
  
  public static void w(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if ((paramString1 != null) && (paramString2 != null))
    {
      Object localObject = paramString2;
      if (paramThrowable != null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString2);
        ((StringBuilder)localObject).append(" ");
        ((StringBuilder)localObject).append(paramThrowable.toString());
        localObject = ((StringBuilder)localObject).toString();
      }
      LiveLog.w(paramString1, (String)localObject);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\X5Log.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */