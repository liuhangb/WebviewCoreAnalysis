package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.os.HandlerThread;

class MiniQBHandlerThread
  extends HandlerThread
{
  private static final String THREAD_NAME = "MiniQBHandlerThread";
  private static MiniQBHandlerThread mHandlerThread;
  
  public MiniQBHandlerThread(String paramString)
  {
    super(paramString);
  }
  
  public static MiniQBHandlerThread getInstance()
    throws Throwable
  {
    try
    {
      if ((mHandlerThread == null) || (mHandlerThread.getLooper() == null))
      {
        mHandlerThread = new MiniQBHandlerThread("MiniQBHandlerThread");
        mHandlerThread.start();
      }
      MiniQBHandlerThread localMiniQBHandlerThread = mHandlerThread;
      return localMiniQBHandlerThread;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\MiniQBHandlerThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */