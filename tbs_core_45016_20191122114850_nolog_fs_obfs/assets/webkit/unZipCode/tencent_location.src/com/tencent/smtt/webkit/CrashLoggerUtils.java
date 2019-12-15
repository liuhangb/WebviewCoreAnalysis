package com.tencent.smtt.webkit;

import com.tencent.common.threadpool.BrowserExecutorSupplier;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("debug")
public final class CrashLoggerUtils
{
  private static Class<?> jdField_a_of_type_JavaLangClass;
  private static Object jdField_a_of_type_JavaLangObject;
  private static Method jdField_a_of_type_JavaLangReflectMethod;
  private static Method b;
  
  @CalledByNative
  private static void ReportExtraMessageLogToRQD(String paramString1, String paramString2)
  {
    BrowserExecutorSupplier.singleThreadExecutorForSharePreference().execute(a(paramString1, paramString2));
  }
  
  @CalledByNative
  private static void ReportExtraMessageUserDataToRQD(String paramString1, String paramString2)
  {
    BrowserExecutorSupplier.singleThreadExecutorForSharePreference().execute(b(paramString1, paramString2));
  }
  
  private static Runnable a(String paramString1, final String paramString2)
  {
    new Runnable()
    {
      public void run()
      {
        if ((CrashLoggerUtils.a() == null) || (CrashLoggerUtils.a() == null)) {
          CrashLoggerUtils.a();
        }
        if ((CrashLoggerUtils.a() != null) && (CrashLoggerUtils.a() != null)) {
          try
          {
            CrashLoggerUtils.a().invoke(CrashLoggerUtils.a(), new Object[] { this.a, paramString2 });
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
  
  private static Runnable b(String paramString1, final String paramString2)
  {
    new Runnable()
    {
      public void run()
      {
        if ((CrashLoggerUtils.b() == null) || (CrashLoggerUtils.a() == null)) {
          CrashLoggerUtils.a();
        }
        if ((CrashLoggerUtils.b() != null) && (CrashLoggerUtils.a() != null)) {
          try
          {
            CrashLoggerUtils.b().invoke(CrashLoggerUtils.a(), new Object[] { this.a, paramString2 });
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
  
  private static void b()
  {
    try
    {
      if (jdField_a_of_type_JavaLangClass == null)
      {
        jdField_a_of_type_JavaLangClass = Class.forName("com.tencent.mtt.external.rqd.RQDManager");
        if (jdField_a_of_type_JavaLangClass == null) {
          return;
        }
      }
      if (jdField_a_of_type_JavaLangObject == null)
      {
        Method localMethod = jdField_a_of_type_JavaLangClass.getDeclaredMethod("getInstance", (Class[])null);
        if (localMethod == null) {
          return;
        }
        jdField_a_of_type_JavaLangObject = localMethod.invoke(null, (Object[])null);
        if (jdField_a_of_type_JavaLangObject == null) {
          return;
        }
      }
      if ((jdField_a_of_type_JavaLangClass != null) && (jdField_a_of_type_JavaLangObject != null))
      {
        jdField_a_of_type_JavaLangReflectMethod = jdField_a_of_type_JavaLangClass.getDeclaredMethod("d", new Class[] { String.class, String.class });
        b = jdField_a_of_type_JavaLangClass.getDeclaredMethod("putUserData", new Class[] { String.class, String.class });
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  private static native String nativeGetCrashExtraMessage();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\CrashLoggerUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */