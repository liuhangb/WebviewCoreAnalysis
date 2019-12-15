package com.tencent.smtt.jscore.devtools;

import android.os.Handler;
import android.os.Looper;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("jscore")
public class Session
{
  private Channel mChannel;
  private Handler mHandler;
  private long mNativeSession;
  
  public Session(final long paramLong, Looper paramLooper, Channel paramChannel)
  {
    this.mHandler = new Handler(paramLooper);
    this.mChannel = paramChannel;
    if (checkNeedsPost())
    {
      runOnExecutorThread(new Runnable()
      {
        public void run()
        {
          Session.this.initialize(paramLong);
        }
      });
      return;
    }
    initialize(paramLong);
  }
  
  private void initialize(long paramLong)
  {
    this.mNativeSession = nativeInit(paramLong);
  }
  
  private native void nativeDisconnect(long paramLong);
  
  private native long nativeInit(long paramLong);
  
  private native void nativeSendMessage(long paramLong, String paramString);
  
  private void onDisconnect()
  {
    Channel localChannel = this.mChannel;
    if (localChannel != null) {
      localChannel.onDisconnect();
    }
  }
  
  @CalledByNative
  private void sendProtocolResponse(String paramString)
  {
    Channel localChannel = this.mChannel;
    if (localChannel != null) {
      localChannel.onMessage(paramString);
    }
  }
  
  protected boolean checkNeedsPost()
  {
    return runningOnExecutorThread() ^ true;
  }
  
  public void disconnect()
  {
    long l = this.mNativeSession;
    if (l != 0L) {
      nativeDisconnect(l);
    }
    this.mHandler = null;
  }
  
  public boolean isDisconnected()
  {
    return this.mHandler == null;
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
  
  protected boolean runningOnExecutorThread()
  {
    return this.mHandler.getLooper() == Looper.myLooper();
  }
  
  public void sendMessage(String paramString)
  {
    long l = this.mNativeSession;
    if (l == 0L) {
      return;
    }
    nativeSendMessage(l, paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jscore\devtools\Session.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */