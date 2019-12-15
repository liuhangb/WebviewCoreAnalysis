package org.chromium.android_webview;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.base.TraceEvent;

public class CleanupReference
  extends WeakReference<Object>
{
  private static Object jdField_a_of_type_JavaLangObject;
  private static final Thread jdField_a_of_type_JavaLangThread;
  private static ReferenceQueue<Object> jdField_a_of_type_JavaLangRefReferenceQueue = new ReferenceQueue();
  private static Set<CleanupReference> jdField_a_of_type_JavaUtilSet = new HashSet();
  private Runnable jdField_a_of_type_JavaLangRunnable;
  
  static
  {
    jdField_a_of_type_JavaLangObject = new Object();
    jdField_a_of_type_JavaLangThread = new Thread("CleanupReference")
    {
      public void run()
      {
        try
        {
          for (;;)
          {
            CleanupReference localCleanupReference = (CleanupReference)CleanupReference.a().remove();
            synchronized (CleanupReference.a())
            {
              Message.obtain(CleanupReference.LazyHolder.a, 2, localCleanupReference).sendToTarget();
              CleanupReference.a().wait(500L);
            }
          }
        }
        catch (Exception localException)
        {
          Log.e("CleanupReference", "Queue remove exception:", new Object[] { localException });
        }
      }
    };
    jdField_a_of_type_JavaLangThread.setDaemon(true);
    jdField_a_of_type_JavaLangThread.start();
  }
  
  public CleanupReference(Object paramObject, Runnable paramRunnable)
  {
    super(paramObject, jdField_a_of_type_JavaLangRefReferenceQueue);
    this.jdField_a_of_type_JavaLangRunnable = paramRunnable;
    a(1);
  }
  
  private void a()
  {
    jdField_a_of_type_JavaUtilSet.remove(this);
    Runnable localRunnable = this.jdField_a_of_type_JavaLangRunnable;
    this.jdField_a_of_type_JavaLangRunnable = null;
    if (localRunnable != null) {
      localRunnable.run();
    }
    clear();
  }
  
  private void a(int paramInt)
  {
    Message localMessage = Message.obtain(LazyHolder.a, paramInt, this);
    if (Looper.myLooper() == localMessage.getTarget().getLooper())
    {
      localMessage.getTarget().handleMessage(localMessage);
      localMessage.recycle();
      return;
    }
    localMessage.sendToTarget();
  }
  
  public void cleanupNow()
  {
    a(2);
  }
  
  public boolean hasCleanedUp()
  {
    return this.jdField_a_of_type_JavaLangRunnable == null;
  }
  
  private static class LazyHolder
  {
    static final Handler a = new Handler(ThreadUtils.getUiThreadLooper())
    {
      public void handleMessage(Message arg1)
      {
        try
        {
          TraceEvent.begin("CleanupReference.LazyHolder.handleMessage");
          CleanupReference localCleanupReference = (CleanupReference)???.obj;
          switch (???.what)
          {
          case 2: 
            CleanupReference.a(localCleanupReference);
            break;
          case 1: 
            CleanupReference.a().add(localCleanupReference);
            break;
          }
          for (;;)
          {
            Log.e("CleanupReference", "Bad message=%d", new Object[] { Integer.valueOf(???.what) });
            synchronized (CleanupReference.a())
            {
              for (;;)
              {
                localCleanupReference = (CleanupReference)CleanupReference.a().poll();
                if (localCleanupReference == null) {
                  break;
                }
                CleanupReference.a(localCleanupReference);
              }
              CleanupReference.a().notifyAll();
              return;
            }
          }
        }
        finally
        {
          TraceEvent.end("CleanupReference.LazyHolder.handleMessage");
        }
      }
    };
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\CleanupReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */