package org.chromium.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.chromium.base.annotations.CalledByNative;

public class ThreadUtils
{
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  private static boolean b;
  private static boolean c;
  
  private static Handler a()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_a_of_type_AndroidOsHandler == null) {
        if (!b) {
          jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper());
        } else {
          throw new RuntimeException("Did not yet override the UI thread");
        }
      }
      Handler localHandler = jdField_a_of_type_AndroidOsHandler;
      return localHandler;
    }
  }
  
  public static void assertOnBackgroundThread()
  {
    if (c) {
      return;
    }
    if (!jdField_a_of_type_Boolean)
    {
      if (!runningOnUiThread()) {
        return;
      }
      throw new AssertionError("Must be called on a thread other than UI.");
    }
  }
  
  public static void assertOnUiThread()
  {
    if (c) {
      return;
    }
    if (!jdField_a_of_type_Boolean)
    {
      if (runningOnUiThread()) {
        return;
      }
      throw new AssertionError("Must be called on the UI thread.");
    }
  }
  
  public static void checkUiThread()
  {
    if (!c)
    {
      if (runningOnUiThread()) {
        return;
      }
      throw new IllegalStateException("Must be called on the UI thread.");
    }
  }
  
  public static Looper getUiThreadLooper()
  {
    return a().getLooper();
  }
  
  @CalledByNative
  private static boolean isThreadPriorityAudio(int paramInt)
  {
    return Process.getThreadPriority(paramInt) == -16;
  }
  
  public static <T> FutureTask<T> postOnUiThread(FutureTask<T> paramFutureTask)
  {
    a().post(paramFutureTask);
    return paramFutureTask;
  }
  
  public static void postOnUiThread(Runnable paramRunnable)
  {
    a().post(paramRunnable);
  }
  
  @VisibleForTesting
  public static void postOnUiThreadDelayed(Runnable paramRunnable, long paramLong)
  {
    a().postDelayed(paramRunnable, paramLong);
  }
  
  public static <T> FutureTask<T> runOnUiThread(Callable<T> paramCallable)
  {
    return runOnUiThread(new FutureTask(paramCallable));
  }
  
  public static <T> FutureTask<T> runOnUiThread(FutureTask<T> paramFutureTask)
  {
    if (runningOnUiThread())
    {
      paramFutureTask.run();
      return paramFutureTask;
    }
    postOnUiThread(paramFutureTask);
    return paramFutureTask;
  }
  
  public static void runOnUiThread(Runnable paramRunnable)
  {
    if (runningOnUiThread())
    {
      paramRunnable.run();
      return;
    }
    a().post(paramRunnable);
  }
  
  public static <T> T runOnUiThreadBlocking(Callable<T> paramCallable)
    throws ExecutionException
  {
    paramCallable = new FutureTask(paramCallable);
    runOnUiThread(paramCallable);
    try
    {
      paramCallable = paramCallable.get();
      return paramCallable;
    }
    catch (InterruptedException paramCallable)
    {
      throw new RuntimeException("Interrupted waiting for callable", paramCallable);
    }
  }
  
  public static void runOnUiThreadBlocking(Runnable paramRunnable)
  {
    if (runningOnUiThread())
    {
      paramRunnable.run();
      return;
    }
    paramRunnable = new FutureTask(paramRunnable, null);
    postOnUiThread(paramRunnable);
    try
    {
      paramRunnable.get();
      return;
    }
    catch (Exception paramRunnable)
    {
      throw new RuntimeException("Exception occurred while waiting for runnable", paramRunnable);
    }
  }
  
  @VisibleForTesting
  public static <T> T runOnUiThreadBlockingNoException(Callable<T> paramCallable)
  {
    try
    {
      paramCallable = runOnUiThreadBlocking(paramCallable);
      return paramCallable;
    }
    catch (ExecutionException paramCallable)
    {
      throw new RuntimeException("Error occurred waiting for callable", paramCallable);
    }
  }
  
  public static boolean runningOnUiThread()
  {
    return a().getLooper() == Looper.myLooper();
  }
  
  public static void setThreadAssertsDisabledForTesting(boolean paramBoolean)
  {
    c = paramBoolean;
  }
  
  @CalledByNative
  public static void setThreadPriorityAudio(int paramInt)
  {
    Process.setThreadPriority(paramInt, -16);
  }
  
  @VisibleForTesting
  public static void setUiThread(Looper paramLooper)
  {
    localObject = jdField_a_of_type_JavaLangObject;
    if (paramLooper == null) {}
    try
    {
      jdField_a_of_type_AndroidOsHandler = null;
      return;
    }
    finally {}
    if ((jdField_a_of_type_AndroidOsHandler != null) && (jdField_a_of_type_AndroidOsHandler.getLooper() != paramLooper))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("UI thread looper is already set to ");
      localStringBuilder.append(jdField_a_of_type_AndroidOsHandler.getLooper());
      localStringBuilder.append(" (Main thread looper is ");
      localStringBuilder.append(Looper.getMainLooper());
      localStringBuilder.append("), cannot set to new looper ");
      localStringBuilder.append(paramLooper);
      throw new RuntimeException(localStringBuilder.toString());
    }
    jdField_a_of_type_AndroidOsHandler = new Handler(paramLooper);
  }
  
  public static void setWillOverrideUiThread()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      b = true;
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\ThreadUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */