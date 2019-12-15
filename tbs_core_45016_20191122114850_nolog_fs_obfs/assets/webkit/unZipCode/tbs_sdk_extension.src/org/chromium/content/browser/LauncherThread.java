package org.chromium.content.browser;

import android.os.Handler;
import android.os.Looper;
import org.chromium.base.JavaHandlerThread;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("content::android")
public final class LauncherThread
{
  private static final Handler jdField_a_of_type_AndroidOsHandler = new Handler(jdField_a_of_type_OrgChromiumBaseJavaHandlerThread.getLooper());
  private static final JavaHandlerThread jdField_a_of_type_OrgChromiumBaseJavaHandlerThread = new JavaHandlerThread("Chrome_ProcessLauncherThread");
  private static Handler b = jdField_a_of_type_AndroidOsHandler;
  
  static
  {
    jdField_a_of_type_OrgChromiumBaseJavaHandlerThread.maybeStart();
  }
  
  public static Handler getHandler()
  {
    return b;
  }
  
  @CalledByNative
  private static JavaHandlerThread getHandlerThread()
  {
    return jdField_a_of_type_OrgChromiumBaseJavaHandlerThread;
  }
  
  public static void post(Runnable paramRunnable)
  {
    b.post(paramRunnable);
  }
  
  public static void postDelayed(Runnable paramRunnable, long paramLong)
  {
    b.postDelayed(paramRunnable, paramLong);
  }
  
  public static void removeCallbacks(Runnable paramRunnable)
  {
    b.removeCallbacks(paramRunnable);
  }
  
  public static boolean runningOnLauncherThread()
  {
    return b.getLooper() == Looper.myLooper();
  }
  
  @VisibleForTesting
  public static void setCurrentThreadAsLauncherThread()
  {
    b = new Handler();
  }
  
  @VisibleForTesting
  public static void setLauncherThreadAsLauncherThread()
  {
    b = jdField_a_of_type_AndroidOsHandler;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\LauncherThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */