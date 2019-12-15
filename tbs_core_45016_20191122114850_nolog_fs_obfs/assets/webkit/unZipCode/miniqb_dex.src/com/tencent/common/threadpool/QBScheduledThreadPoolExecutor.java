package com.tencent.common.threadpool;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class QBScheduledThreadPoolExecutor
  extends ScheduledThreadPoolExecutor
{
  int a = 1;
  int b = 1;
  
  public QBScheduledThreadPoolExecutor(int paramInt1, int paramInt2, ThreadFactory paramThreadFactory)
  {
    super(paramInt1, paramThreadFactory);
    this.a = paramInt1;
    this.b = paramInt2;
  }
  
  public QBScheduledThreadPoolExecutor(int paramInt, ThreadFactory paramThreadFactory)
  {
    super(paramInt, paramThreadFactory);
    this.a = paramInt;
    this.b = paramInt;
  }
  
  public void resetPoolSize()
  {
    int i = this.b;
    if (i > this.a) {
      setCorePoolSize(i);
    }
  }
  
  public void shutdown() {}
  
  public List<Runnable> shutdownNow()
  {
    return null;
  }
  
  protected void terminated() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\QBScheduledThreadPoolExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */