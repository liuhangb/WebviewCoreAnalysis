package com.tencent.common.threadpool;

import android.os.Process;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class QBThreadFactory
  implements ThreadFactory
{
  int jdField_a_of_type_Int = 5;
  private final AtomicInteger jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger = new AtomicInteger(1);
  public String mThreadPoolName = null;
  
  QBThreadFactory(String paramString, int paramInt)
  {
    this.mThreadPoolName = paramString;
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public Thread newThread(Runnable paramRunnable)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TP-");
    localStringBuilder.append(this.mThreadPoolName);
    localStringBuilder.append("-");
    localStringBuilder.append(this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger.getAndIncrement());
    paramRunnable = new Thread(paramRunnable, localStringBuilder.toString())
    {
      public void run()
      {
        try
        {
          Process.setThreadPriority(QBThreadFactory.this.a);
          super.run();
          return;
        }
        catch (Throwable localThrowable)
        {
          for (;;) {}
        }
      }
    };
    if (paramRunnable.isDaemon()) {
      paramRunnable.setDaemon(false);
    }
    return paramRunnable;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\QBThreadFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */