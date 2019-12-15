package com.tencent.smtt.net;

import android.os.Handler;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.net.URL;
import org.chromium.base.annotations.UsedByReflection;

public class QProxyDetect
{
  static Handler sHandler;
  static QProxyDetect sQProxyDetect;
  
  @UsedByReflection("X5CoreInit.java")
  public static QProxyDetect getInstance()
  {
    if (sQProxyDetect == null) {
      sQProxyDetect = new QProxyDetect();
    }
    return sQProxyDetect;
  }
  
  public void notifyQProxyDetectResult(final Boolean paramBoolean, final String paramString)
  {
    Handler localHandler = sHandler;
    if (localHandler != null)
    {
      localHandler.post(new Runnable()
      {
        public void run()
        {
          SmttServiceProxy.getInstance().notifyQProxyDetectResult(paramBoolean, paramString);
        }
      });
      return;
    }
    SmttServiceProxy.getInstance().notifyQProxyDetectResult(paramBoolean, paramString);
  }
  
  @UsedByReflection("X5CoreInit.java")
  public void startQProxyDetect(URL paramURL)
  {
    if (sHandler == null) {
      sHandler = new Handler();
    }
    AwNetworkUtils.startCheckSpdyProxy(paramURL.toString());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\QProxyDetect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */