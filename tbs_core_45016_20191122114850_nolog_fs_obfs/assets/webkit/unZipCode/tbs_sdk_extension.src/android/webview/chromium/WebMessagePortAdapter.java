package android.webview.chromium;

import android.annotation.TargetApi;
import android.os.Handler;
import com.tencent.tbs.core.webkit.WebMessage;
import com.tencent.tbs.core.webkit.WebMessagePort;
import com.tencent.tbs.core.webkit.WebMessagePort.WebMessageCallback;
import org.chromium.content.browser.AppWebMessagePort;
import org.chromium.content_public.browser.MessagePort;
import org.chromium.content_public.browser.MessagePort.MessageCallback;

@TargetApi(23)
public class WebMessagePortAdapter
  extends WebMessagePort
{
  private MessagePort mPort;
  
  public WebMessagePortAdapter(MessagePort paramMessagePort)
  {
    this.mPort = paramMessagePort;
  }
  
  public static WebMessagePort[] fromMessagePorts(MessagePort[] paramArrayOfMessagePort)
  {
    if (paramArrayOfMessagePort == null) {
      return null;
    }
    WebMessagePort[] arrayOfWebMessagePort = new WebMessagePort[paramArrayOfMessagePort.length];
    int i = 0;
    while (i < paramArrayOfMessagePort.length)
    {
      arrayOfWebMessagePort[i] = new WebMessagePortAdapter(paramArrayOfMessagePort[i]);
      i += 1;
    }
    return arrayOfWebMessagePort;
  }
  
  public static MessagePort[] toMessagePorts(WebMessagePort[] paramArrayOfWebMessagePort)
  {
    if (paramArrayOfWebMessagePort == null) {
      return null;
    }
    AppWebMessagePort[] arrayOfAppWebMessagePort = new AppWebMessagePort[paramArrayOfWebMessagePort.length];
    int i = 0;
    while (i < paramArrayOfWebMessagePort.length)
    {
      arrayOfAppWebMessagePort[i] = ((WebMessagePortAdapter)paramArrayOfWebMessagePort[i]).getPort();
      i += 1;
    }
    return arrayOfAppWebMessagePort;
  }
  
  public void close()
  {
    this.mPort.close();
  }
  
  public MessagePort getPort()
  {
    return this.mPort;
  }
  
  public void postMessage(WebMessage paramWebMessage)
  {
    this.mPort.postMessage(paramWebMessage.getData(), toMessagePorts(paramWebMessage.getPorts()));
  }
  
  public void setWebMessageCallback(WebMessagePort.WebMessageCallback paramWebMessageCallback)
  {
    setWebMessageCallback(paramWebMessageCallback, null);
  }
  
  public void setWebMessageCallback(final WebMessagePort.WebMessageCallback paramWebMessageCallback, Handler paramHandler)
  {
    this.mPort.setMessageCallback(new MessagePort.MessageCallback()
    {
      public void onMessage(String paramAnonymousString, MessagePort[] paramAnonymousArrayOfMessagePort)
      {
        paramWebMessageCallback.onMessage(WebMessagePortAdapter.this, new WebMessage(paramAnonymousString, WebMessagePortAdapter.fromMessagePorts(paramAnonymousArrayOfMessagePort)));
      }
    }, paramHandler);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebMessagePortAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */