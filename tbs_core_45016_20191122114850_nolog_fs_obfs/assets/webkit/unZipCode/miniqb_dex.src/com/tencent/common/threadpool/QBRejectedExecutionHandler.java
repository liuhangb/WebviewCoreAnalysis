package com.tencent.common.threadpool;

import com.tencent.basesupport.FLogger;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class QBRejectedExecutionHandler
  implements RejectedExecutionHandler
{
  static QBRejectedExecutionHandler a = new QBRejectedExecutionHandler();
  
  public void rejectedExecution(Runnable paramRunnable, ThreadPoolExecutor paramThreadPoolExecutor)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("rejectedExecution: ");
    localStringBuilder.append(paramRunnable);
    localStringBuilder.append(", executor: ");
    localStringBuilder.append(paramThreadPoolExecutor);
    FLogger.d("QBRejectedExecutionHandler", localStringBuilder.toString());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\QBRejectedExecutionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */