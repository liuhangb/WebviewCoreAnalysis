package com.tencent.smtt.util;

import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.smtt.webkit.WebViewChromiumExtension;
import com.tencent.smtt.webkit.e;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import org.chromium.tencent.TencentContentViewCore;

public final class CrashExtraMessage
{
  private static boolean mCanUseRQD = e.a().f();
  private static Class<?> mRqdClass;
  private static Method mRqdMethod_D;
  private static Method mRqdMethod_UserData;
  private static Object mRqdObj;
  
  private static Runnable CreateRunableOfLogToRQD(String paramString1, final String paramString2)
  {
    new Runnable()
    {
      public void run()
      {
        if ((CrashExtraMessage.mRqdMethod_D == null) || (CrashExtraMessage.mRqdObj == null)) {
          CrashExtraMessage.access$200();
        }
        if ((CrashExtraMessage.mRqdMethod_D != null) && (CrashExtraMessage.mRqdObj != null)) {
          try
          {
            CrashExtraMessage.mRqdMethod_D.invoke(CrashExtraMessage.mRqdObj, new Object[] { this.a, paramString2 });
            return;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      }
    };
  }
  
  private static Runnable CreateRunableOfUserDataToRQD(String paramString1, final String paramString2)
  {
    new Runnable()
    {
      public void run()
      {
        if ((CrashExtraMessage.mRqdMethod_UserData == null) || (CrashExtraMessage.mRqdObj == null)) {
          CrashExtraMessage.access$200();
        }
        if ((CrashExtraMessage.mRqdMethod_UserData != null) && (CrashExtraMessage.mRqdObj != null)) {
          try
          {
            CrashExtraMessage.mRqdMethod_UserData.invoke(CrashExtraMessage.mRqdObj, new Object[] { this.a, paramString2 });
            return;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      }
    };
  }
  
  private static void InitReflect()
  {
    try
    {
      if (mRqdClass == null)
      {
        mRqdClass = Class.forName("com.tencent.mtt.external.rqd.RQDManager");
        if (mRqdClass == null) {
          return;
        }
      }
      if (mRqdObj == null)
      {
        Method localMethod = mRqdClass.getDeclaredMethod("getInstance", (Class[])null);
        if (localMethod == null) {
          return;
        }
        mRqdObj = localMethod.invoke(null, (Object[])null);
        if (mRqdObj == null) {
          return;
        }
      }
      if ((mRqdClass != null) && (mRqdObj != null))
      {
        mRqdMethod_D = mRqdClass.getDeclaredMethod("d", new Class[] { String.class, String.class });
        mRqdMethod_UserData = mRqdClass.getDeclaredMethod("putUserData", new Class[] { String.class, String.class });
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public static void ReportExtraMessageLogToRQD(String paramString1, String paramString2)
  {
    if (mCanUseRQD) {
      BrowserExecutorSupplier.singleThreadExecutorForSharePreference().execute(CreateRunableOfLogToRQD(paramString1, paramString2));
    }
  }
  
  public static void ReportExtraMessageUserDataToRQD(String paramString1, String paramString2)
  {
    if (mCanUseRQD) {
      BrowserExecutorSupplier.singleThreadExecutorForSharePreference().execute(CreateRunableOfUserDataToRQD(paramString1, paramString2));
    }
  }
  
  public static String getCrashExtraMessage()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("; gIsCommandLineInited: ");
    localStringBuilder.append(TencentContentViewCore.isCommandLineInited());
    localStringBuilder.append("\n");
    localStringBuilder.append(WebViewChromiumExtension.getCrashExtraMessage());
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\CrashExtraMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */