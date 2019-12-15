package org.chromium.base.metrics;

import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("base::android")
public class RecordUserAction
{
  private static long jdField_a_of_type_Long;
  private static Throwable jdField_a_of_type_JavaLangThrowable;
  
  private static native long nativeAddActionCallbackForTesting(UserActionCallback paramUserActionCallback);
  
  private static native void nativeRecordUserAction(String paramString);
  
  private static native void nativeRemoveActionCallbackForTesting(long paramLong);
  
  public static void record(String paramString)
  {
    if (jdField_a_of_type_JavaLangThrowable != null) {
      return;
    }
    if (ThreadUtils.runningOnUiThread())
    {
      nativeRecordUserAction(paramString);
      return;
    }
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        RecordUserAction.a(this.a);
      }
    });
  }
  
  public static void removeActionCallbackForTesting()
  {
    if ((!jdField_a_of_type_Boolean) && (jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    nativeRemoveActionCallbackForTesting(jdField_a_of_type_Long);
    jdField_a_of_type_Long = 0L;
  }
  
  public static void setActionCallbackForTesting(UserActionCallback paramUserActionCallback)
  {
    if ((!jdField_a_of_type_Boolean) && (jdField_a_of_type_Long != 0L)) {
      throw new AssertionError();
    }
    jdField_a_of_type_Long = nativeAddActionCallbackForTesting(paramUserActionCallback);
  }
  
  @VisibleForTesting
  public static void setDisabledForTests(boolean paramBoolean)
  {
    Throwable localThrowable;
    if (paramBoolean)
    {
      localThrowable = jdField_a_of_type_JavaLangThrowable;
      if (localThrowable != null) {
        throw new IllegalStateException("UserActions are already disabled.", localThrowable);
      }
    }
    if (paramBoolean) {
      localThrowable = new Throwable();
    } else {
      localThrowable = null;
    }
    jdField_a_of_type_JavaLangThrowable = localThrowable;
  }
  
  public static abstract interface UserActionCallback
  {
    @CalledByNative("UserActionCallback")
    public abstract void onActionRecorded(String paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\metrics\RecordUserAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */