package org.chromium.base;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.MessageQueue.IdleHandler;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("base::android")
public class JavaHandlerThread
{
  private final HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  private Throwable jdField_a_of_type_JavaLangThrowable;
  
  public JavaHandlerThread(String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread(paramString);
  }
  
  private boolean a()
  {
    return this.jdField_a_of_type_AndroidOsHandlerThread.getState() != Thread.State.NEW;
  }
  
  @CalledByNative
  private static JavaHandlerThread create(String paramString)
  {
    return new JavaHandlerThread(paramString);
  }
  
  @CalledByNative
  private Throwable getUncaughtExceptionIfAny()
  {
    return this.jdField_a_of_type_JavaLangThrowable;
  }
  
  @CalledByNative
  private boolean isAlive()
  {
    return this.jdField_a_of_type_AndroidOsHandlerThread.isAlive();
  }
  
  @CalledByNative
  private void joinThread()
  {
    int i = 0;
    while (i == 0) {
      try
      {
        this.jdField_a_of_type_AndroidOsHandlerThread.join();
        i = 1;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;) {}
      }
    }
  }
  
  @CalledByNative
  private void listenForUncaughtExceptionsForTesting()
  {
    this.jdField_a_of_type_AndroidOsHandlerThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
    {
      public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable)
      {
        JavaHandlerThread.a(JavaHandlerThread.this, paramAnonymousThrowable);
      }
    });
  }
  
  private native void nativeInitializeThread(long paramLong1, long paramLong2);
  
  private native void nativeOnLooperStopped(long paramLong);
  
  private native void nativeStopThread(long paramLong);
  
  @CalledByNative
  private void startAndInitialize(final long paramLong1, long paramLong2)
  {
    maybeStart();
    new Handler(this.jdField_a_of_type_AndroidOsHandlerThread.getLooper()).post(new Runnable()
    {
      public void run()
      {
        JavaHandlerThread.a(JavaHandlerThread.this, paramLong1, this.b);
      }
    });
  }
  
  @CalledByNative
  private void stop(final long paramLong)
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    Looper localLooper = this.jdField_a_of_type_AndroidOsHandlerThread.getLooper();
    if (isAlive())
    {
      if (localLooper == null) {
        return;
      }
      new Handler(localLooper).post(new Runnable()
      {
        public void run()
        {
          JavaHandlerThread.b(JavaHandlerThread.this, paramLong);
        }
      });
      joinThread();
      return;
    }
  }
  
  @CalledByNative
  private void stopOnThread(final long paramLong)
  {
    nativeStopThread(paramLong);
    Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler()
    {
      public boolean queueIdle()
      {
        JavaHandlerThread.a(JavaHandlerThread.this).getLooper().quit();
        JavaHandlerThread.a(JavaHandlerThread.this, paramLong);
        return false;
      }
    });
  }
  
  public Looper getLooper()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_AndroidOsHandlerThread.getLooper();
  }
  
  public void maybeStart()
  {
    if (a()) {
      return;
    }
    this.jdField_a_of_type_AndroidOsHandlerThread.start();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\JavaHandlerThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */