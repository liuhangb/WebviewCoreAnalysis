package com.tencent.tbs.common.baseinfo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class RoutineDaemon
{
  private static final String TAG = "RoutineDaemon";
  private static RoutineDaemon sInstance;
  private static Object sLock = new Object();
  private Handler mHandler = null;
  public Looper mLooper = null;
  
  private RoutineDaemon()
  {
    HandlerThread localHandlerThread = new HandlerThread("RoutineDaemon", 11);
    localHandlerThread.start();
    this.mLooper = localHandlerThread.getLooper();
    this.mHandler = new Handler(this.mLooper);
  }
  
  public static RoutineDaemon getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new RoutineDaemon();
      }
      RoutineDaemon localRoutineDaemon = sInstance;
      return localRoutineDaemon;
    }
    finally {}
  }
  
  public final boolean post(Runnable paramRunnable)
  {
    return this.mHandler.post(paramRunnable);
  }
  
  public final boolean postDelayed(Runnable paramRunnable, long paramLong)
  {
    return this.mHandler.postDelayed(paramRunnable, paramLong);
  }
  
  public final void removeCallbacks(Runnable paramRunnable)
  {
    this.mHandler.removeCallbacks(paramRunnable);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\RoutineDaemon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */