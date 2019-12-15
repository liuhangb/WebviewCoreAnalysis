package com.tencent.common.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ComparableFutureTask<V>
  extends FutureTask<V>
{
  public Object task;
  
  public ComparableFutureTask(Runnable paramRunnable, V paramV)
  {
    super(paramRunnable, paramV);
    this.task = paramRunnable;
  }
  
  public ComparableFutureTask(Callable<V> paramCallable)
  {
    super(paramCallable);
    this.task = paramCallable;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\ComparableFutureTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */