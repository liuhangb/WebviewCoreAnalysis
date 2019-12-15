package com.tencent.smtt.jscore;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("jscore")
public class X5JsVirtualMachineImpl
{
  private static final AtomicInteger sCount = new AtomicInteger(1);
  private final Context mContext;
  private Handler mHandler;
  private final boolean mHoldsThread;
  private final HashSet<WeakReference<X5JsContextImpl>> mJsContexts = new HashSet();
  private long mNativeX5JsVirtualMachine;
  private final HashSet<Object> mRetainedJavaScriptObjects = new HashSet();
  
  public X5JsVirtualMachineImpl(Context paramContext, Looper paramLooper)
  {
    this.mContext = paramContext;
    if (paramLooper == null)
    {
      paramContext = new StringBuilder();
      paramContext.append("X5JsCore #");
      paramContext.append(sCount.getAndIncrement());
      paramContext = new HandlerThread(paramContext.toString());
      paramContext.start();
      paramLooper = paramContext.getLooper();
      this.mHoldsThread = true;
    }
    else
    {
      this.mHoldsThread = false;
    }
    this.mHandler = new Handler(paramLooper);
    if (checkNeedsPost())
    {
      runOnExecutorThread(new Runnable()
      {
        public void run()
        {
          X5JsVirtualMachineImpl.this.initialize();
        }
      });
      return;
    }
    initialize();
  }
  
  @CalledByNative
  private void addRetainedJavaScriptObject(Object paramObject)
  {
    this.mRetainedJavaScriptObjects.add(paramObject);
  }
  
  private void initialize()
  {
    this.mNativeX5JsVirtualMachine = nativeInit();
  }
  
  private native void nativeDestroy(long paramLong);
  
  private native long nativeInit();
  
  private native void nativeSetIsPaused(long paramLong, boolean paramBoolean);
  
  @CalledByNative
  private void removeRetainedJavaScriptObject(Object paramObject)
  {
    this.mRetainedJavaScriptObjects.remove(paramObject);
  }
  
  protected boolean checkNeedsPost()
  {
    return runningOnExecutorThread() ^ true;
  }
  
  public X5JsContextImpl createJsContext()
  {
    if (isDestroyed()) {
      return null;
    }
    X5JsContextImpl localX5JsContextImpl = new X5JsContextImpl(this);
    this.mJsContexts.add(new WeakReference(localX5JsContextImpl));
    return localX5JsContextImpl;
  }
  
  public void destroy()
  {
    if (isDestroyed()) {
      return;
    }
    Iterator localIterator = this.mJsContexts.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      if (localWeakReference.get() != null) {
        ((X5JsContextImpl)localWeakReference.get()).destroy();
      }
    }
    runOnExecutorThread(new Runnable()
    {
      public void run()
      {
        if (X5JsVirtualMachineImpl.this.isDestroyed()) {
          return;
        }
        if (X5JsVirtualMachineImpl.this.mNativeX5JsVirtualMachine != 0L)
        {
          X5JsVirtualMachineImpl localX5JsVirtualMachineImpl = X5JsVirtualMachineImpl.this;
          localX5JsVirtualMachineImpl.nativeDestroy(localX5JsVirtualMachineImpl.mNativeX5JsVirtualMachine);
          X5JsVirtualMachineImpl.access$102(X5JsVirtualMachineImpl.this, 0L);
        }
        if (X5JsVirtualMachineImpl.this.mHoldsThread) {
          X5JsVirtualMachineImpl.this.mHandler.getLooper().quit();
        }
        X5JsVirtualMachineImpl.access$402(X5JsVirtualMachineImpl.this, null);
      }
    });
  }
  
  public Looper getLooper()
  {
    return this.mHandler.getLooper();
  }
  
  protected long getNativePointer()
  {
    return this.mNativeX5JsVirtualMachine;
  }
  
  protected boolean isDestroyed()
  {
    return this.mHandler == null;
  }
  
  public void onPause()
  {
    long l = this.mNativeX5JsVirtualMachine;
    if (l == 0L) {
      return;
    }
    nativeSetIsPaused(l, true);
  }
  
  public void onResume()
  {
    long l = this.mNativeX5JsVirtualMachine;
    if (l == 0L) {
      return;
    }
    nativeSetIsPaused(l, false);
  }
  
  protected void runOnExecutorThread(Runnable paramRunnable)
  {
    if (runningOnExecutorThread())
    {
      paramRunnable.run();
      return;
    }
    this.mHandler.post(paramRunnable);
  }
  
  protected <T> T runOnExecutorThreadBlocking(Callable<T> paramCallable)
  {
    paramCallable = new FutureTask(paramCallable);
    runOnExecutorThread(paramCallable);
    try
    {
      paramCallable = paramCallable.get(2L, TimeUnit.SECONDS);
      return paramCallable;
    }
    catch (Exception paramCallable)
    {
      throw new RuntimeException("Interrupted waiting for callable", paramCallable);
    }
  }
  
  protected boolean runningOnExecutorThread()
  {
    return this.mHandler.getLooper() == Looper.myLooper();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("(");
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append(" - ");
    localStringBuilder.append(this.mNativeX5JsVirtualMachine);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jscore\X5JsVirtualMachineImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */