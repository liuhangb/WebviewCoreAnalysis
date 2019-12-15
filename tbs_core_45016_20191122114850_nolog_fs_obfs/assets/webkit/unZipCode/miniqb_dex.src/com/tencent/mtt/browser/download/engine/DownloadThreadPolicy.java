package com.tencent.mtt.browser.download.engine;

import android.os.HandlerThread;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.tencent.common.manifest.EventEmiter;
import com.tencent.common.manifest.EventMessage;
import com.tencent.downloadprovider.DownloadproviderHelper;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadThreadPolicy
{
  private static ThreadPoolExecutor jdField_a_of_type_JavaUtilConcurrentThreadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), new ThreadFactory()
  {
    public Thread newThread(Runnable paramAnonymousRunnable)
    {
      return DownloadThreadPolicy.a(paramAnonymousRunnable);
    }
  });
  private static AtomicInteger jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger = new AtomicInteger(0);
  
  static void a(DownloadTask paramDownloadTask, Runnable paramRunnable)
  {
    if ((paramDownloadTask != null) && (paramRunnable != null)) {
      jdField_a_of_type_JavaUtilConcurrentThreadPoolExecutor.execute(new a(paramDownloadTask, paramRunnable, null));
    }
  }
  
  private static Thread b(Runnable paramRunnable)
  {
    new Thread()
    {
      public void run()
      {
        try
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("TP-DownPool-");
          localStringBuilder.append(DownloadThreadPolicy.a().incrementAndGet());
          setName(localStringBuilder.toString());
          Process.setThreadPriority(10);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        Runnable localRunnable = this.a;
        if (localRunnable != null) {
          localRunnable.run();
        }
      }
    };
  }
  
  private static void b(long paramLong, DownloadTask paramDownloadTask, int paramInt)
  {
    Object localObject1 = new StringBuilder();
    if (paramDownloadTask != null)
    {
      ((StringBuilder)localObject1).append("\n");
      ((StringBuilder)localObject1).append("CUR_TASK u=[");
      ((StringBuilder)localObject1).append(paramDownloadTask.mDownloadUrl);
      ((StringBuilder)localObject1).append("],s=[");
      ((StringBuilder)localObject1).append(paramDownloadTask.getStatus());
      ((StringBuilder)localObject1).append("],t=[");
      ((StringBuilder)localObject1).append(paramLong);
      ((StringBuilder)localObject1).append("],");
      ((StringBuilder)localObject1).append("p=[");
      ((StringBuilder)localObject1).append(paramInt);
      ((StringBuilder)localObject1).append("]\n");
      localObject2 = DownloadproviderHelper.d();
      if ((localObject2 != null) && (!((List)localObject2).isEmpty()))
      {
        localObject2 = ((List)localObject2).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          DownloadTask localDownloadTask = (DownloadTask)((Iterator)localObject2).next();
          ((StringBuilder)localObject1).append("ING_TASK u=[");
          ((StringBuilder)localObject1).append(paramDownloadTask.mDownloadUrl);
          ((StringBuilder)localObject1).append("],s=[");
          ((StringBuilder)localObject1).append(localDownloadTask.getStatus());
          ((StringBuilder)localObject1).append("]\n");
        }
      }
    }
    else
    {
      ((StringBuilder)localObject1).append("NO_TASK_INFO");
    }
    paramDownloadTask = ((StringBuilder)localObject1).toString();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("reportTooLongSchedule() reportString = [");
    ((StringBuilder)localObject1).append(paramDownloadTask);
    ((StringBuilder)localObject1).append("]");
    Log.d("DownloadThreadPolicy", ((StringBuilder)localObject1).toString());
    localObject1 = Thread.currentThread();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("ScheduleTooLongException used:");
    ((StringBuilder)localObject2).append(paramLong);
    paramDownloadTask = new EventMessage("com.tencent.mtt.external.rqd.RQDManager.handleCatchException", new Object[] { localObject1, new ScheduleTooLongException(((StringBuilder)localObject2).toString()), paramDownloadTask });
    EventEmiter.getDefault().emit(paramDownloadTask);
  }
  
  public static HandlerThread createDownloadScheduler(String paramString)
  {
    return new HandlerThread(paramString, -1);
  }
  
  private static class ScheduleTooLongException
    extends RuntimeException
  {
    ScheduleTooLongException(String paramString)
    {
      super();
    }
  }
  
  private static class a
    implements Runnable
  {
    final long jdField_a_of_type_Long;
    final DownloadTask jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask;
    final Runnable jdField_a_of_type_JavaLangRunnable;
    
    private a(DownloadTask paramDownloadTask, Runnable paramRunnable)
    {
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = paramDownloadTask;
      this.jdField_a_of_type_JavaLangRunnable = paramRunnable;
      this.jdField_a_of_type_Long = SystemClock.elapsedRealtime();
    }
    
    public void run()
    {
      long l = SystemClock.elapsedRealtime() - this.jdField_a_of_type_Long;
      int i;
      try
      {
        i = Process.getThreadPriority(Process.myTid());
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        i = 64512;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("SCHE_DOWN used=[");
      localStringBuilder.append(l);
      localStringBuilder.append("], task=[");
      localStringBuilder.append(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getDownloadTaskId());
      localStringBuilder.append("], file=[");
      localStringBuilder.append(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getFileName());
      localStringBuilder.append("], tp=[");
      localStringBuilder.append(Thread.currentThread().getName());
      localStringBuilder.append("], prio=[");
      localStringBuilder.append(i);
      localStringBuilder.append("]");
      Log.d("DownloadThreadPolicy", localStringBuilder.toString());
      if (l >= 10000L) {
        DownloadThreadPolicy.a(l, this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask, i);
      }
      this.jdField_a_of_type_JavaLangRunnable.run();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DownloadThreadPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */