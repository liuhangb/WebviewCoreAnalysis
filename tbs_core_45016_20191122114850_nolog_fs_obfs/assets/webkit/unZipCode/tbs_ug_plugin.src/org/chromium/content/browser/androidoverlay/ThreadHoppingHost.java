package org.chromium.content.browser.androidoverlay;

import android.os.Handler;
import android.view.Surface;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class ThreadHoppingHost
  implements DialogOverlayCore.Host
{
  private Handler jdField_a_of_type_AndroidOsHandler = new Handler();
  private final Semaphore jdField_a_of_type_JavaUtilConcurrentSemaphore = new Semaphore(0);
  private final DialogOverlayCore.Host jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore$Host;
  
  public ThreadHoppingHost(DialogOverlayCore.Host paramHost)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserAndroidoverlayDialogOverlayCore$Host = paramHost;
  }
  
  public void enforceClose()
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        ThreadHoppingHost.a(ThreadHoppingHost.this).enforceClose();
      }
    });
  }
  
  public void onClose()
  {
    this.jdField_a_of_type_JavaUtilConcurrentSemaphore.release(1);
  }
  
  public void onOverlayDestroyed()
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        ThreadHoppingHost.a(ThreadHoppingHost.this).onOverlayDestroyed();
      }
    });
  }
  
  public void onSurfaceReady(final Surface paramSurface)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        ThreadHoppingHost.a(ThreadHoppingHost.this).onSurfaceReady(paramSurface);
      }
    });
  }
  
  public void waitForClose()
  {
    for (;;)
    {
      try
      {
        this.jdField_a_of_type_JavaUtilConcurrentSemaphore.tryAcquire(2L, TimeUnit.SECONDS);
        return;
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\androidoverlay\ThreadHoppingHost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */