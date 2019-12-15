package com.tencent.common.threadpool;

import android.os.Build.VERSION;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import com.tencent.basesupport.FLogger;
import com.tencent.basesupport.ModuleProxy;
import com.tencent.common.utils.ICostTimeLite;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BrowserExecutorSupplier
{
  public static String TAG = "BrowserExecutorSupplier";
  static int jdField_a_of_type_Int = 1;
  private static volatile BrowserExecutorSupplier jdField_a_of_type_ComTencentCommonThreadpoolBrowserExecutorSupplier = new BrowserExecutorSupplier();
  HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  MainThreadExecutor jdField_a_of_type_ComTencentCommonThreadpoolMainThreadExecutor;
  QBScheduledThreadPoolExecutor jdField_a_of_type_ComTencentCommonThreadpoolQBScheduledThreadPoolExecutor;
  QBThreadPoolExecutor jdField_a_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
  ShortTimeExecutor jdField_a_of_type_ComTencentCommonThreadpoolShortTimeExecutor;
  Object jdField_a_of_type_JavaLangObject = new Object();
  Executor jdField_a_of_type_JavaUtilConcurrentExecutor;
  ExecutorService jdField_a_of_type_JavaUtilConcurrentExecutorService;
  ScheduledExecutorService jdField_a_of_type_JavaUtilConcurrentScheduledExecutorService;
  boolean jdField_a_of_type_Boolean = false;
  HandlerThread jdField_b_of_type_AndroidOsHandlerThread;
  QBThreadPoolExecutor jdField_b_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
  Object jdField_b_of_type_JavaLangObject = new Object();
  HandlerThread jdField_c_of_type_AndroidOsHandlerThread;
  QBThreadPoolExecutor jdField_c_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
  Object jdField_c_of_type_JavaLangObject = new Object();
  HandlerThread jdField_d_of_type_AndroidOsHandlerThread;
  QBThreadPoolExecutor jdField_d_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
  Object jdField_d_of_type_JavaLangObject = new Object();
  QBThreadPoolExecutor jdField_e_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
  Object jdField_e_of_type_JavaLangObject = new Object();
  QBThreadPoolExecutor jdField_f_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
  Object jdField_f_of_type_JavaLangObject = new Object();
  Object g = new Object();
  Object h = new Object();
  Object i = new Object();
  Object j = new Object();
  Object k = new Object();
  Object l = new Object();
  Object m = new Object();
  Object n = new Object();
  Object o = new Object();
  Object p = new Object();
  
  private BrowserExecutorSupplier()
  {
    try
    {
      jdField_a_of_type_Int = Runtime.getRuntime().availableProcessors();
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    jdField_a_of_type_Int = 4;
    if (jdField_a_of_type_Int < 4) {
      jdField_a_of_type_Int = 4;
    }
  }
  
  public static ThreadPoolExecutor backgroundTaskExecutor()
  {
    return coreTaskExecutor();
  }
  
  public static ThreadPoolExecutor coreTaskExecutor()
  {
    return getInstance().getCoreTaskExecutor();
  }
  
  public static Executor forBackgroundTasks()
  {
    return getInstance().getCoreTaskExecutor();
  }
  
  public static Executor forDbTasks()
  {
    return getInstance().getIoExecutor();
  }
  
  public static Executor forIoTasks()
  {
    return getInstance().getIoExecutor();
  }
  
  public static Executor forMainThreadTasks()
  {
    return getInstance().getMainThreadExecutor();
  }
  
  public static Executor forTimeoutTasks()
  {
    return getInstance().getTimeOutExecutor();
  }
  
  public static Looper getDebugWatcherLooper()
  {
    return getInstance().d().getLooper();
  }
  
  public static BrowserExecutorSupplier getInstance()
  {
    if (jdField_a_of_type_ComTencentCommonThreadpoolBrowserExecutorSupplier == null) {
      try
      {
        if (jdField_a_of_type_ComTencentCommonThreadpoolBrowserExecutorSupplier == null) {
          jdField_a_of_type_ComTencentCommonThreadpoolBrowserExecutorSupplier = new BrowserExecutorSupplier();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentCommonThreadpoolBrowserExecutorSupplier;
  }
  
  public static Looper getLooperForRunLongTime()
  {
    return getInstance().a().getLooper();
  }
  
  public static Looper getLooperForRunShortTime()
  {
    return getInstance().b().getLooper();
  }
  
  public static Looper getStreamConnLooper()
  {
    return getInstance().c().getLooper();
  }
  
  public static ThreadPoolExecutor pictureTaskExecutor()
  {
    return getInstance().getPictureTasktExecutor();
  }
  
  public static void postForBackgroundTasks(BackgroundRunable paramBackgroundRunable)
  {
    getInstance().getCpuBoundExecutor().execute(paramBackgroundRunable);
  }
  
  public static void postForDbTasks(BackgroundRunable paramBackgroundRunable)
  {
    getInstance().getIoExecutor().execute(paramBackgroundRunable);
  }
  
  public static void postForIoTasks(BackgroundRunable paramBackgroundRunable)
  {
    getInstance().getIoExecutor().execute(paramBackgroundRunable);
  }
  
  public static void postForTimeoutTasks(BackgroundRunable paramBackgroundRunable)
  {
    getInstance().getTimeOutExecutor().execute(paramBackgroundRunable);
  }
  
  public static ScheduledExecutorService reportExecutor()
  {
    return getInstance().getReportExecutor();
  }
  
  public static ExecutorService singleThreadExecutorForSharePreference()
  {
    return getInstance().a();
  }
  
  HandlerThread a()
  {
    ??? = this.jdField_a_of_type_AndroidOsHandlerThread;
    if (??? != null) {
      return (HandlerThread)???;
    }
    synchronized (this.h)
    {
      if (this.jdField_a_of_type_AndroidOsHandlerThread == null)
      {
        HandlerThread localHandlerThread = new HandlerThread("ThreadPool_threadhandler_time_consuming", 19);
        localHandlerThread.start();
        this.jdField_a_of_type_AndroidOsHandlerThread = localHandlerThread;
      }
      return this.jdField_a_of_type_AndroidOsHandlerThread;
    }
  }
  
  ExecutorService a()
  {
    ??? = this.jdField_a_of_type_JavaUtilConcurrentExecutorService;
    if (??? != null) {
      return (ExecutorService)???;
    }
    synchronized (this.l)
    {
      if (this.jdField_a_of_type_JavaUtilConcurrentExecutorService == null) {
        this.jdField_a_of_type_JavaUtilConcurrentExecutorService = Executors.newSingleThreadExecutor(new QBThreadFactory("SharePrefrence", 10));
      }
      return this.jdField_a_of_type_JavaUtilConcurrentExecutorService;
    }
  }
  
  HandlerThread b()
  {
    ??? = this.jdField_b_of_type_AndroidOsHandlerThread;
    if (??? != null) {
      return (HandlerThread)???;
    }
    synchronized (this.i)
    {
      if (this.jdField_b_of_type_AndroidOsHandlerThread == null)
      {
        HandlerThread localHandlerThread = new HandlerThread("ThreadPool_threadhandler_time_fast");
        localHandlerThread.start();
        this.jdField_b_of_type_AndroidOsHandlerThread = localHandlerThread;
      }
      return this.jdField_b_of_type_AndroidOsHandlerThread;
    }
  }
  
  HandlerThread c()
  {
    ??? = this.jdField_c_of_type_AndroidOsHandlerThread;
    if (??? != null) {
      return (HandlerThread)???;
    }
    synchronized (this.j)
    {
      if (this.jdField_c_of_type_AndroidOsHandlerThread == null)
      {
        HandlerThread localHandlerThread = new HandlerThread("ThreadPool_threadhandler_stream_conn");
        localHandlerThread.start();
        this.jdField_c_of_type_AndroidOsHandlerThread = localHandlerThread;
      }
      return this.jdField_c_of_type_AndroidOsHandlerThread;
    }
  }
  
  HandlerThread d()
  {
    ??? = this.jdField_d_of_type_AndroidOsHandlerThread;
    if (??? != null) {
      return (HandlerThread)???;
    }
    synchronized (this.k)
    {
      if (this.jdField_d_of_type_AndroidOsHandlerThread == null)
      {
        HandlerThread localHandlerThread = new HandlerThread("ThreadPool_threadhandler_debug_watcher");
        localHandlerThread.start();
        this.jdField_d_of_type_AndroidOsHandlerThread = localHandlerThread;
      }
      return this.jdField_d_of_type_AndroidOsHandlerThread;
    }
  }
  
  public ThreadPoolExecutor getCoreTaskExecutor()
  {
    ??? = this.jdField_f_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (??? != null) {
      return (ThreadPoolExecutor)???;
    }
    for (;;)
    {
      synchronized (this.m)
      {
        if (this.jdField_f_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor == null)
        {
          if (!this.jdField_a_of_type_Boolean) {
            break label105;
          }
          i1 = 2;
          this.jdField_f_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor = new QBThreadPoolExecutor(2, 4, i1, Integer.MAX_VALUE, 120L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new QBThreadFactory("CoreTask", 12));
          if (Build.VERSION.SDK_INT >= 9) {
            this.jdField_f_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor.allowCoreThreadTimeOut(true);
          }
        }
        return this.jdField_f_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
      }
      label105:
      int i1 = Integer.MAX_VALUE;
    }
  }
  
  public ThreadPoolExecutor getCpuBoundExecutor()
  {
    ??? = this.jdField_b_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (??? != null) {
      return (ThreadPoolExecutor)???;
    }
    for (;;)
    {
      int i2;
      synchronized (this.jdField_b_of_type_JavaLangObject)
      {
        if (this.jdField_b_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor == null)
        {
          int i3 = jdField_a_of_type_Int;
          i2 = jdField_a_of_type_Int + 1;
          if (this.jdField_a_of_type_Boolean)
          {
            i1 = 2;
            this.jdField_b_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor = new QBThreadPoolExecutor(2, i3 + 1, i1, i2, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new QBThreadFactory("CPUBound", 10));
          }
        }
        else
        {
          return this.jdField_b_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
        }
      }
      int i1 = i2;
    }
  }
  
  public Executor getImmediateExecutor()
  {
    ??? = this.jdField_a_of_type_JavaUtilConcurrentExecutor;
    if (??? != null) {
      return (Executor)???;
    }
    synchronized (this.o)
    {
      if (this.jdField_a_of_type_JavaUtilConcurrentExecutor == null) {
        this.jdField_a_of_type_JavaUtilConcurrentExecutor = new a(null);
      }
      return this.jdField_a_of_type_JavaUtilConcurrentExecutor;
    }
  }
  
  public ExecutorService getIoExecutor()
  {
    ??? = this.jdField_a_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (??? != null) {
      return (ExecutorService)???;
    }
    for (;;)
    {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        if (this.jdField_a_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor == null)
        {
          if (this.jdField_a_of_type_Boolean)
          {
            i1 = 2;
            this.jdField_a_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor = new QBThreadPoolExecutor(2, 4, i1, 4, 60L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new QBThreadFactory("IoBound", 12));
          }
        }
        else {
          return this.jdField_a_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
        }
      }
      int i1 = 4;
    }
  }
  
  public MainThreadExecutor getMainThreadExecutor()
  {
    ??? = this.jdField_a_of_type_ComTencentCommonThreadpoolMainThreadExecutor;
    if (??? != null) {
      return (MainThreadExecutor)???;
    }
    synchronized (this.jdField_f_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_ComTencentCommonThreadpoolMainThreadExecutor == null) {
        this.jdField_a_of_type_ComTencentCommonThreadpoolMainThreadExecutor = new MainThreadExecutor();
      }
      return this.jdField_a_of_type_ComTencentCommonThreadpoolMainThreadExecutor;
    }
  }
  
  public ThreadPoolExecutor getNetworkExecutor()
  {
    ??? = this.jdField_d_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (??? != null) {
      return (ThreadPoolExecutor)???;
    }
    for (;;)
    {
      synchronized (this.jdField_d_of_type_JavaLangObject)
      {
        if (this.jdField_d_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor == null)
        {
          if (this.jdField_a_of_type_Boolean)
          {
            i1 = 2;
            PriorityBlockingQueue localPriorityBlockingQueue = new PriorityBlockingQueue(11, AbstractTaskComparator.get());
            QBThreadFactory localQBThreadFactory = new QBThreadFactory("Network", 10);
            QBRejectedExecutionHandler localQBRejectedExecutionHandler = QBRejectedExecutionHandler.a;
            this.jdField_d_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor = new QBThreadPoolExecutor(2, 4, i1, 4, 60L, TimeUnit.SECONDS, localPriorityBlockingQueue, localQBThreadFactory, localQBRejectedExecutionHandler);
          }
        }
        else {
          return this.jdField_d_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
        }
      }
      int i1 = 4;
    }
  }
  
  public ThreadPoolExecutor getPictureTasktExecutor()
  {
    ??? = this.jdField_e_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (??? != null) {
      return (ThreadPoolExecutor)???;
    }
    for (;;)
    {
      synchronized (this.jdField_e_of_type_JavaLangObject)
      {
        if (this.jdField_e_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor == null)
        {
          if (this.jdField_a_of_type_Boolean)
          {
            i1 = 2;
            PriorityBlockingQueue localPriorityBlockingQueue = new PriorityBlockingQueue(11, AbstractTaskComparator.get());
            QBThreadFactory localQBThreadFactory = new QBThreadFactory("Picture", 10);
            QBRejectedExecutionHandler localQBRejectedExecutionHandler = QBRejectedExecutionHandler.a;
            this.jdField_e_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor = new QBThreadPoolExecutor(2, 4, i1, 6, 60L, TimeUnit.SECONDS, localPriorityBlockingQueue, localQBThreadFactory, localQBRejectedExecutionHandler);
          }
        }
        else {
          return this.jdField_e_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
        }
      }
      int i1 = 6;
    }
  }
  
  public ScheduledExecutorService getReportExecutor()
  {
    ??? = this.jdField_a_of_type_ComTencentCommonThreadpoolQBScheduledThreadPoolExecutor;
    if (??? != null) {
      return (ScheduledExecutorService)???;
    }
    for (;;)
    {
      synchronized (this.n)
      {
        if (this.jdField_a_of_type_ComTencentCommonThreadpoolQBScheduledThreadPoolExecutor == null)
        {
          if (this.jdField_a_of_type_Boolean)
          {
            i1 = 2;
            this.jdField_a_of_type_ComTencentCommonThreadpoolQBScheduledThreadPoolExecutor = new QBScheduledThreadPoolExecutor(i1, 3, new QBThreadFactory("Report", 19));
          }
        }
        else {
          return this.jdField_a_of_type_ComTencentCommonThreadpoolQBScheduledThreadPoolExecutor;
        }
      }
      int i1 = 3;
    }
  }
  
  public ScheduledExecutorService getScheduledExecutor()
  {
    ??? = this.jdField_a_of_type_JavaUtilConcurrentScheduledExecutorService;
    if (??? != null) {
      return (ScheduledExecutorService)???;
    }
    synchronized (this.p)
    {
      if (this.jdField_a_of_type_JavaUtilConcurrentScheduledExecutorService == null) {
        this.jdField_a_of_type_JavaUtilConcurrentScheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new QBThreadFactory("Scheduled delay", 10));
      }
      return this.jdField_a_of_type_JavaUtilConcurrentScheduledExecutorService;
    }
  }
  
  public ShortTimeExecutor getShortTimeExecutor()
  {
    ??? = this.jdField_a_of_type_ComTencentCommonThreadpoolShortTimeExecutor;
    if (??? != null) {
      return (ShortTimeExecutor)???;
    }
    synchronized (this.g)
    {
      if (this.jdField_a_of_type_ComTencentCommonThreadpoolShortTimeExecutor == null) {
        this.jdField_a_of_type_ComTencentCommonThreadpoolShortTimeExecutor = new ShortTimeExecutor();
      }
      return this.jdField_a_of_type_ComTencentCommonThreadpoolShortTimeExecutor;
    }
  }
  
  public ExecutorService getTimeOutExecutor()
  {
    ??? = this.jdField_c_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (??? != null) {
      return (ExecutorService)???;
    }
    synchronized (this.jdField_c_of_type_JavaLangObject)
    {
      if (this.jdField_c_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor == null) {
        this.jdField_c_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor = new QBThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), new QBThreadFactory("Timeout", 10));
      }
      return this.jdField_c_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    }
  }
  
  public void onBootComplete()
  {
    FLogger.d("BrowserExecutorSupplier", "onBootComplete...");
    setNormalAttributesDelay(false);
    Object localObject = this.jdField_f_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (localObject != null) {
      ((QBThreadPoolExecutor)localObject).reSetPoolSize();
    }
    localObject = this.jdField_f_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (localObject != null) {
      ((QBThreadPoolExecutor)localObject).reSetPoolSize();
    }
    localObject = this.jdField_a_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (localObject != null) {
      ((QBThreadPoolExecutor)localObject).reSetPoolSize();
    }
    localObject = this.jdField_b_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (localObject != null) {
      ((QBThreadPoolExecutor)localObject).reSetPoolSize();
    }
    localObject = this.jdField_c_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (localObject != null) {
      ((QBThreadPoolExecutor)localObject).reSetPoolSize();
    }
    localObject = this.jdField_d_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (localObject != null) {
      ((QBThreadPoolExecutor)localObject).reSetPoolSize();
    }
    localObject = this.jdField_e_of_type_ComTencentCommonThreadpoolQBThreadPoolExecutor;
    if (localObject != null) {
      ((QBThreadPoolExecutor)localObject).reSetPoolSize();
    }
    localObject = this.jdField_a_of_type_ComTencentCommonThreadpoolQBScheduledThreadPoolExecutor;
    if (localObject != null) {
      ((QBScheduledThreadPoolExecutor)localObject).resetPoolSize();
    }
  }
  
  public <T> Future<T> postForTimeoutTasks(Callable<T> paramCallable)
  {
    return getInstance().getTimeOutExecutor().submit(paramCallable);
  }
  
  public void setNormalAttributesDelay(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public void shutDown() {}
  
  public static abstract class BackgroundRunable
    implements Runnable
  {
    private final Throwable mConstructed = new Throwable("BackgroundRunable创建于此");
    
    public abstract void doRun();
    
    public void run()
    {
      try
      {
        if (Looper.getMainLooper().getThread().getId() != Thread.currentThread().getId()) {
          Process.setThreadPriority(11);
        } else {
          FLogger.e(BrowserExecutorSupplier.TAG, new Throwable("不要在主线程运行BackgroundRunable，谁post的谁改一下", this.mConstructed));
        }
      }
      catch (Exception localException)
      {
        ICostTimeLite localICostTimeLite;
        String str;
        for (;;) {}
      }
      localICostTimeLite = (ICostTimeLite)ICostTimeLite.PROXY.get();
      str = null;
      if (localICostTimeLite != null)
      {
        str = getClass().getName();
        localICostTimeLite.start(str);
      }
      doRun();
      if (localICostTimeLite != null) {
        localICostTimeLite.end(str);
      }
    }
  }
  
  private static class a
    implements Executor
  {
    private ThreadLocal<Integer> a = new ThreadLocal();
    
    private int a()
    {
      Integer localInteger2 = (Integer)this.a.get();
      Integer localInteger1 = localInteger2;
      if (localInteger2 == null) {
        localInteger1 = Integer.valueOf(0);
      }
      int i = localInteger1.intValue() + 1;
      this.a.set(Integer.valueOf(i));
      return i;
    }
    
    private int b()
    {
      Integer localInteger2 = (Integer)this.a.get();
      Integer localInteger1 = localInteger2;
      if (localInteger2 == null) {
        localInteger1 = Integer.valueOf(0);
      }
      int i = localInteger1.intValue() - 1;
      if (i == 0)
      {
        this.a.remove();
        return i;
      }
      this.a.set(Integer.valueOf(i));
      return i;
    }
    
    public void execute(Runnable paramRunnable)
    {
      if (a() <= 15) {}
      try
      {
        paramRunnable.run();
        break label27;
        BrowserExecutorSupplier.forBackgroundTasks().execute(paramRunnable);
        label27:
        return;
      }
      finally
      {
        b();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\BrowserExecutorSupplier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */