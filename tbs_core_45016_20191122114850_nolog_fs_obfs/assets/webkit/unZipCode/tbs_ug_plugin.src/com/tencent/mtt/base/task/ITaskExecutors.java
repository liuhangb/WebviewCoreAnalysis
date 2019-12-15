package com.tencent.mtt.base.task;

import java.util.concurrent.ExecutorService;

public abstract interface ITaskExecutors
{
  public abstract ExecutorService getDeliveryExecutor();
  
  public abstract ExecutorService getRetryExecutor();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\task\ITaskExecutors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */