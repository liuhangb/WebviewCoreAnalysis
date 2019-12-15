package com.tencent.mtt.base.net.frame;

import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.mtt.base.task.ITaskExecutors;
import com.tencent.mtt.base.task.Task;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class RequestManager
{
  public static final boolean USE_THREAD_DELIVERY = true;
  static ITaskExecutors a = new ITaskExecutors()
  {
    public ExecutorService getDeliveryExecutor()
    {
      return BrowserExecutorSupplier.coreTaskExecutor();
    }
    
    public ExecutorService getRetryExecutor()
    {
      return BrowserExecutorSupplier.backgroundTaskExecutor();
    }
  };
  
  public static void execute(Task paramTask)
  {
    if (paramTask == null) {
      return;
    }
    paramTask.setTaskExecutors(a);
    BrowserExecutorSupplier.getInstance().getNetworkExecutor().execute(paramTask);
  }
  
  public static void executeBackground(Task paramTask)
  {
    if (paramTask == null) {
      return;
    }
    paramTask.setTaskExecutors(a);
    BrowserExecutorSupplier.forBackgroundTasks().execute(paramTask);
  }
  
  public static void executeEmergency(Task paramTask)
  {
    if (paramTask == null) {
      return;
    }
    paramTask.setTaskExecutors(a);
    BrowserExecutorSupplier.coreTaskExecutor().execute(paramTask);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\net\frame\RequestManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */