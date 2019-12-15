package org.chromium.mojo.system.impl;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.mojo.system.RunLoop;

@JNINamespace("mojo::android")
class BaseRunLoop
  implements RunLoop
{
  private long jdField_a_of_type_Long;
  private final CoreImpl jdField_a_of_type_OrgChromiumMojoSystemImplCoreImpl;
  
  BaseRunLoop(CoreImpl paramCoreImpl)
  {
    this.jdField_a_of_type_OrgChromiumMojoSystemImplCoreImpl = paramCoreImpl;
    this.jdField_a_of_type_Long = nativeCreateBaseRunLoop();
  }
  
  private native long nativeCreateBaseRunLoop();
  
  private native void nativeDeleteMessageLoop(long paramLong);
  
  private native void nativePostDelayedTask(long paramLong1, Runnable paramRunnable, long paramLong2);
  
  private native void nativeQuit();
  
  private native void nativeRun();
  
  private native void nativeRunUntilIdle();
  
  @CalledByNative
  private static void runRunnable(Runnable paramRunnable)
  {
    paramRunnable.run();
  }
  
  public void close()
  {
    if (this.jdField_a_of_type_Long == 0L) {
      return;
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumMojoSystemImplCoreImpl.getCurrentRunLoop() != this)) {
      throw new AssertionError("Only the current run loop can be closed");
    }
    this.jdField_a_of_type_OrgChromiumMojoSystemImplCoreImpl.a();
    nativeDeleteMessageLoop(this.jdField_a_of_type_Long);
    this.jdField_a_of_type_Long = 0L;
  }
  
  public void postDelayedTask(Runnable paramRunnable, long paramLong)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError("The run loop cannot run tasks once closed");
    }
    nativePostDelayedTask(this.jdField_a_of_type_Long, paramRunnable, paramLong);
  }
  
  public void quit()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError("The run loop cannot be quitted run once closed");
    }
    nativeQuit();
  }
  
  public void run()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError("The run loop cannot run once closed");
    }
    nativeRun();
  }
  
  public void runUntilIdle()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError("The run loop cannot run once closed");
    }
    nativeRunUntilIdle();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\impl\BaseRunLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */