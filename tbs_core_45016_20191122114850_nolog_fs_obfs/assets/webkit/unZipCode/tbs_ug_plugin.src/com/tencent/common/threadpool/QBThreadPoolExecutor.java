package com.tencent.common.threadpool;

import com.tencent.common.threadpool.debug.QBThreadTimeoutWatcher;
import com.tencent.common.threadpool.debug.ThreadPoolSnapshot;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class QBThreadPoolExecutor
  extends ThreadPoolExecutor
{
  int jdField_a_of_type_Int = 1;
  String jdField_a_of_type_JavaLangString = "";
  Map<Runnable, Long> jdField_a_of_type_JavaUtilMap = Collections.synchronizedMap(new HashMap());
  ThreadFactory jdField_a_of_type_JavaUtilConcurrentThreadFactory = null;
  int b = 1;
  int c = 1;
  int d = 1;
  
  public QBThreadPoolExecutor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue, ThreadFactory paramThreadFactory)
  {
    super(paramInt1, paramInt3, paramLong, paramTimeUnit, paramBlockingQueue, paramThreadFactory);
    this.jdField_a_of_type_Int = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
    this.d = paramInt4;
    this.jdField_a_of_type_JavaUtilConcurrentThreadFactory = paramThreadFactory;
    if ((paramThreadFactory instanceof QBThreadFactory)) {
      this.jdField_a_of_type_JavaLangString = ((QBThreadFactory)this.jdField_a_of_type_JavaUtilConcurrentThreadFactory).mThreadPoolName;
    }
  }
  
  public QBThreadPoolExecutor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue, ThreadFactory paramThreadFactory, RejectedExecutionHandler paramRejectedExecutionHandler)
  {
    super(paramInt1, paramInt3, paramLong, paramTimeUnit, paramBlockingQueue, paramThreadFactory, paramRejectedExecutionHandler);
    this.jdField_a_of_type_Int = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
    this.d = paramInt4;
    this.jdField_a_of_type_JavaUtilConcurrentThreadFactory = paramThreadFactory;
    if ((paramThreadFactory instanceof QBThreadFactory)) {
      this.jdField_a_of_type_JavaLangString = ((QBThreadFactory)this.jdField_a_of_type_JavaUtilConcurrentThreadFactory).mThreadPoolName;
    }
  }
  
  public QBThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue, ThreadFactory paramThreadFactory)
  {
    super(paramInt1, paramInt2, paramLong, paramTimeUnit, paramBlockingQueue, paramThreadFactory);
    this.jdField_a_of_type_Int = paramInt1;
    this.b = paramInt1;
    this.c = paramInt2;
    this.d = paramInt2;
    this.jdField_a_of_type_JavaUtilConcurrentThreadFactory = paramThreadFactory;
    if ((paramThreadFactory instanceof QBThreadFactory)) {
      this.jdField_a_of_type_JavaLangString = ((QBThreadFactory)this.jdField_a_of_type_JavaUtilConcurrentThreadFactory).mThreadPoolName;
    }
  }
  
  protected void afterExecute(Runnable paramRunnable, Throwable paramThrowable)
  {
    super.afterExecute(paramRunnable, paramThrowable);
    QBThreadTimeoutWatcher.afterExecute(paramRunnable, paramThrowable, this.jdField_a_of_type_JavaLangString);
    ThreadPoolSnapshot.afterRun(paramRunnable);
  }
  
  protected void beforeExecute(Thread paramThread, Runnable paramRunnable)
  {
    ThreadPoolSnapshot.beforeRun(paramThread, paramRunnable, this.jdField_a_of_type_JavaLangString);
    super.beforeExecute(paramThread, paramRunnable);
    QBThreadTimeoutWatcher.beforeExecute(paramThread, paramRunnable, this.jdField_a_of_type_JavaLangString);
  }
  
  public void execute(Runnable paramRunnable)
  {
    ThreadPoolSnapshot.add(this.jdField_a_of_type_JavaLangString, paramRunnable);
    super.execute(paramRunnable);
  }
  
  protected <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT)
  {
    return new ComparableFutureTask(paramRunnable, paramT);
  }
  
  protected <T> RunnableFuture<T> newTaskFor(Callable<T> paramCallable)
  {
    return new ComparableFutureTask(paramCallable);
  }
  
  public void reSetPoolSize()
  {
    int i = this.b;
    if (i > this.jdField_a_of_type_Int) {
      setCorePoolSize(i);
    }
    i = this.d;
    if (i > this.c) {
      setMaximumPoolSize(i);
    }
  }
  
  public void shutdown() {}
  
  public List<Runnable> shutdownNow()
  {
    return null;
  }
  
  protected void terminated() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\QBThreadPoolExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */