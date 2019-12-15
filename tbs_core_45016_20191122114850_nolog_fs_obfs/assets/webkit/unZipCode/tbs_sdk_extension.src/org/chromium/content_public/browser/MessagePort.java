package org.chromium.content_public.browser;

import android.os.Handler;
import org.chromium.base.annotations.UsedByReflection;

@UsedByReflection("")
public abstract interface MessagePort
{
  public abstract void close();
  
  public abstract boolean isClosed();
  
  public abstract boolean isStarted();
  
  public abstract boolean isTransferred();
  
  public abstract void postMessage(String paramString, MessagePort[] paramArrayOfMessagePort);
  
  public abstract void setMessageCallback(MessageCallback paramMessageCallback, Handler paramHandler);
  
  public static abstract interface MessageCallback
  {
    public abstract void onMessage(String paramString, MessagePort[] paramArrayOfMessagePort);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\MessagePort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */