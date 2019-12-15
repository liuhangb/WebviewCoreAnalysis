package org.chromium.tencent.audio;

public abstract interface RemotePlayerListenerDelegate
{
  public abstract void OnBufferingUpdate(int paramInt);
  
  public abstract void OnCompletion();
  
  public abstract void OnErrorListener(int paramInt1, int paramInt2);
  
  public abstract void OnPrepared();
  
  public abstract void OnSeekComplete();
  
  public abstract boolean isWebViewActive();
  
  public abstract void onMediaInterruptedByRemote();
  
  public abstract void onPauseByRemote();
  
  public abstract void onPlayByRemote();
  
  public abstract void onSeekByRemote(int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\audio\RemotePlayerListenerDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */