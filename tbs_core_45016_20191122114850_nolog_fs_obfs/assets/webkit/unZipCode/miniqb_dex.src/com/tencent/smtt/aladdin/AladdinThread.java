package com.tencent.smtt.aladdin;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("aladdin")
class AladdinThread
{
  private static AladdinThread jdField_a_of_type_ComTencentSmttAladdinAladdinThread;
  private static Object jdField_a_of_type_JavaLangObject = new Object();
  private long jdField_a_of_type_Long;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private HandlerThread jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread("Aladdin");
  
  private AladdinThread()
  {
    this.jdField_a_of_type_AndroidOsHandlerThread.start();
    this.jdField_a_of_type_AndroidOsHandler = new Handler(this.jdField_a_of_type_AndroidOsHandlerThread.getLooper());
  }
  
  public static AladdinThread a()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_a_of_type_ComTencentSmttAladdinAladdinThread == null) {
        jdField_a_of_type_ComTencentSmttAladdinAladdinThread = new AladdinThread();
      }
      AladdinThread localAladdinThread = jdField_a_of_type_ComTencentSmttAladdinAladdinThread;
      return localAladdinThread;
    }
  }
  
  private native void nativeDestroy(long paramLong);
  
  private native long nativeInit();
  
  public Looper a()
  {
    return this.jdField_a_of_type_AndroidOsHandlerThread.getLooper();
  }
  
  public void a()
  {
    a(new Runnable()
    {
      public void run()
      {
        AladdinThread localAladdinThread = AladdinThread.this;
        AladdinThread.a(localAladdinThread, AladdinThread.a(localAladdinThread));
      }
    });
  }
  
  public void a(Runnable paramRunnable)
  {
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(paramRunnable);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\aladdin\AladdinThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */