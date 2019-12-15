package android.webview.chromium;

import java.util.concurrent.Callable;
import org.chromium.android_webview.AwContents;
import org.chromium.android_webview.AwContents.VisualStateCallback;
import org.chromium.android_webview.WebViewChromiumRunQueue;
import org.chromium.base.ThreadUtils;
import org.chromium.content_public.browser.MessagePort;

public class SharedWebViewChromium
{
  private AwContents mAwContents;
  private final WebViewChromiumAwInit mAwInit;
  private final WebViewChromiumRunQueue mRunQueue;
  
  public SharedWebViewChromium(WebViewChromiumRunQueue paramWebViewChromiumRunQueue, WebViewChromiumAwInit paramWebViewChromiumAwInit)
  {
    this.mRunQueue = paramWebViewChromiumRunQueue;
    this.mAwInit = paramWebViewChromiumAwInit;
  }
  
  protected boolean checkNeedsPost()
  {
    boolean bool;
    if ((this.mRunQueue.chromiumHasStarted()) && (ThreadUtils.runningOnUiThread())) {
      bool = false;
    } else {
      bool = true;
    }
    if (!bool)
    {
      if (this.mAwContents != null) {
        return bool;
      }
      throw new IllegalStateException("AwContents must be created if we are not posting!");
    }
    return bool;
  }
  
  public MessagePort[] createWebMessageChannel()
  {
    this.mAwInit.startYourEngines(true);
    if (checkNeedsPost()) {
      (MessagePort[])this.mRunQueue.runOnUiThreadBlocking(new Callable()
      {
        public MessagePort[] call()
        {
          return SharedWebViewChromium.this.createWebMessageChannel();
        }
      });
    }
    return this.mAwContents.createMessageChannel();
  }
  
  public AwContents getAwContents()
  {
    return this.mAwContents;
  }
  
  public void insertVisualStateCallback(final long paramLong, AwContents.VisualStateCallback paramVisualStateCallback)
  {
    if (checkNeedsPost())
    {
      this.mRunQueue.addTask(new Runnable()
      {
        public void run()
        {
          SharedWebViewChromium.this.insertVisualStateCallback(paramLong, this.val$callback);
        }
      });
      return;
    }
    this.mAwContents.insertVisualStateCallback(paramLong, paramVisualStateCallback);
  }
  
  public void postMessageToMainFrame(final String paramString1, final String paramString2, final MessagePort[] paramArrayOfMessagePort)
  {
    if (checkNeedsPost())
    {
      this.mRunQueue.addTask(new Runnable()
      {
        public void run()
        {
          SharedWebViewChromium.this.postMessageToMainFrame(paramString1, paramString2, paramArrayOfMessagePort);
        }
      });
      return;
    }
    this.mAwContents.postMessageToFrame(null, paramString1, paramString2, paramArrayOfMessagePort);
  }
  
  public void setAwContentsOnUiThread(AwContents paramAwContents)
  {
    if (this.mAwContents == null)
    {
      this.mAwContents = paramAwContents;
      return;
    }
    throw new RuntimeException("Cannot create multiple AwContents for the same SharedWebViewChromium");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\SharedWebViewChromium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */