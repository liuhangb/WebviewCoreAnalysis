package org.chromium.base;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("base::android")
@MainDex
public class JavaExceptionReporter
  implements Thread.UncaughtExceptionHandler
{
  private final Thread.UncaughtExceptionHandler jdField_a_of_type_JavaLangThread$UncaughtExceptionHandler;
  private final boolean b;
  private boolean c;
  
  private JavaExceptionReporter(Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaLangThread$UncaughtExceptionHandler = paramUncaughtExceptionHandler;
    this.b = paramBoolean;
  }
  
  @CalledByNative
  private static void installHandler(boolean paramBoolean)
  {
    Thread.setDefaultUncaughtExceptionHandler(new JavaExceptionReporter(Thread.getDefaultUncaughtExceptionHandler(), paramBoolean));
  }
  
  private static native void nativeReportJavaException(boolean paramBoolean, Throwable paramThrowable);
  
  private static native void nativeReportJavaStackTrace(String paramString);
  
  public static void reportStackTrace(String paramString)
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    nativeReportJavaStackTrace(paramString);
  }
  
  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    if (!this.c)
    {
      this.c = true;
      nativeReportJavaException(this.b, paramThrowable);
    }
    Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = this.jdField_a_of_type_JavaLangThread$UncaughtExceptionHandler;
    if (localUncaughtExceptionHandler != null) {
      localUncaughtExceptionHandler.uncaughtException(paramThread, paramThrowable);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\JavaExceptionReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */