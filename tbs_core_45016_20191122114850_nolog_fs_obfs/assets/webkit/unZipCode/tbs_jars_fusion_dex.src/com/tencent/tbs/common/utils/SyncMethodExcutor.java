package com.tencent.tbs.common.utils;

public class SyncMethodExcutor
{
  private Object mSyncLock = new Object();
  
  public Object excuteMethod(SyncMethod paramSyncMethod, long paramLong, Object... paramVarArgs)
  {
    paramVarArgs = this.mSyncLock;
    if (paramLong > 0L) {}
    try
    {
      new ExcuteThread(this, paramSyncMethod).start();
      try
      {
        this.mSyncLock.wait(paramLong);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
      if (paramLong == 0L) {
        paramSyncMethod.methodImpl(new Object[0]);
      } else {
        new ExcuteThread(this, paramSyncMethod).start();
      }
      return paramSyncMethod.mReturnValue;
    }
    finally
    {
      for (;;) {}
    }
    throw paramSyncMethod;
  }
  
  void onComplited()
  {
    synchronized (this.mSyncLock)
    {
      this.mSyncLock.notifyAll();
      return;
    }
  }
  
  private static class ExcuteThread
    extends Thread
  {
    SyncMethodExcutor mExcutor;
    SyncMethod method = null;
    
    public ExcuteThread(SyncMethodExcutor paramSyncMethodExcutor, SyncMethod paramSyncMethod)
    {
      setName(paramSyncMethod.mThreadName);
      this.mExcutor = paramSyncMethodExcutor;
      this.method = paramSyncMethod;
    }
    
    public void run()
    {
      this.method.methodImpl(new Object[0]);
      this.mExcutor.onComplited();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\SyncMethodExcutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */