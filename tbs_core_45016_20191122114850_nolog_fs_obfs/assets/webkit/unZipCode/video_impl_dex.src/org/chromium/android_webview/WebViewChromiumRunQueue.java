package org.chromium.android_webview;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.chromium.base.ThreadUtils;

public class WebViewChromiumRunQueue
{
  private final Queue<Runnable> jdField_a_of_type_JavaUtilQueue = new ConcurrentLinkedQueue();
  private final ChromiumHasStartedCallable jdField_a_of_type_OrgChromiumAndroid_webviewWebViewChromiumRunQueue$ChromiumHasStartedCallable;
  
  public WebViewChromiumRunQueue(ChromiumHasStartedCallable paramChromiumHasStartedCallable)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewWebViewChromiumRunQueue$ChromiumHasStartedCallable = paramChromiumHasStartedCallable;
  }
  
  public void addTask(Runnable paramRunnable)
  {
    this.jdField_a_of_type_JavaUtilQueue.add(paramRunnable);
    if (this.jdField_a_of_type_OrgChromiumAndroid_webviewWebViewChromiumRunQueue$ChromiumHasStartedCallable.hasStarted()) {
      ThreadUtils.runOnUiThread(new Runnable()
      {
        public void run()
        {
          WebViewChromiumRunQueue.this.drainQueue();
        }
      });
    }
  }
  
  public boolean chromiumHasStarted()
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewWebViewChromiumRunQueue$ChromiumHasStartedCallable.hasStarted();
  }
  
  public void drainQueue()
  {
    Object localObject = this.jdField_a_of_type_JavaUtilQueue;
    if (localObject != null)
    {
      if (((Queue)localObject).isEmpty()) {
        return;
      }
      for (localObject = (Runnable)this.jdField_a_of_type_JavaUtilQueue.poll(); localObject != null; localObject = (Runnable)this.jdField_a_of_type_JavaUtilQueue.poll()) {
        ((Runnable)localObject).run();
      }
      return;
    }
  }
  
  public <T> T runBlockingFuture(FutureTask<T> paramFutureTask)
  {
    if (chromiumHasStarted())
    {
      if (!ThreadUtils.runningOnUiThread())
      {
        addTask(paramFutureTask);
        try
        {
          paramFutureTask = paramFutureTask.get(4L, TimeUnit.SECONDS);
          return paramFutureTask;
        }
        catch (Exception paramFutureTask)
        {
          throw new RuntimeException(paramFutureTask);
        }
        catch (TimeoutException paramFutureTask)
        {
          throw new RuntimeException("Probable deadlock detected due to WebView API being called on incorrect thread while the UI thread is blocked.", paramFutureTask);
        }
      }
      throw new IllegalStateException("This method should only be called off the UI thread");
    }
    throw new RuntimeException("Must be started before we block!");
  }
  
  public <T> T runOnUiThreadBlocking(Callable<T> paramCallable)
  {
    return (T)runBlockingFuture(new FutureTask(paramCallable));
  }
  
  public void runVoidTaskOnUiThreadBlocking(Runnable paramRunnable)
  {
    runBlockingFuture(new FutureTask(paramRunnable, null));
  }
  
  public static abstract interface ChromiumHasStartedCallable
  {
    public abstract boolean hasStarted();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\WebViewChromiumRunQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */