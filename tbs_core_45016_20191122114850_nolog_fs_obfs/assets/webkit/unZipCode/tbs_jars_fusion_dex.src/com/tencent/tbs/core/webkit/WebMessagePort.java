package com.tencent.tbs.core.webkit;

import android.os.Handler;

public abstract class WebMessagePort
{
  public abstract void close();
  
  public abstract void postMessage(WebMessage paramWebMessage);
  
  public abstract void setWebMessageCallback(WebMessageCallback paramWebMessageCallback);
  
  public abstract void setWebMessageCallback(WebMessageCallback paramWebMessageCallback, Handler paramHandler);
  
  public static abstract class WebMessageCallback
  {
    public void onMessage(WebMessagePort paramWebMessagePort, WebMessage paramWebMessage) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\WebMessagePort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */