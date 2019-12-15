package com.tencent.common.connectivity;

import android.os.HandlerThread;
import android.os.Looper;
import com.tencent.common.manifest.AppManifest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectivityAdapterHolder
{
  private static Looper jdField_a_of_type_AndroidOsLooper;
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  private static ExecutorService jdField_a_of_type_JavaUtilConcurrentExecutorService;
  
  private static ExecutorService a()
  {
    ??? = jdField_a_of_type_JavaUtilConcurrentExecutorService;
    if (??? != null) {
      return (ExecutorService)???;
    }
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_a_of_type_JavaUtilConcurrentExecutorService == null)
      {
        ConnectivityAdapter localConnectivityAdapter = (ConnectivityAdapter)AppManifest.getInstance().queryService(ConnectivityAdapter.class);
        if (localConnectivityAdapter != null) {
          jdField_a_of_type_JavaUtilConcurrentExecutorService = localConnectivityAdapter.getThreadExecutor();
        }
        if (jdField_a_of_type_JavaUtilConcurrentExecutorService == null) {
          jdField_a_of_type_JavaUtilConcurrentExecutorService = Executors.newCachedThreadPool();
        }
      }
      return jdField_a_of_type_JavaUtilConcurrentExecutorService;
    }
  }
  
  public static void execute(Runnable paramRunnable)
  {
    ExecutorService localExecutorService = a();
    if ((localExecutorService != null) && (!localExecutorService.isShutdown())) {
      localExecutorService.execute(paramRunnable);
    }
  }
  
  public static Looper getHandlerThreadLooper()
  {
    ??? = jdField_a_of_type_AndroidOsLooper;
    if (??? != null) {
      return (Looper)???;
    }
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_a_of_type_AndroidOsLooper == null)
      {
        Object localObject2 = (ConnectivityAdapter)AppManifest.getInstance().queryService(ConnectivityAdapter.class);
        if (localObject2 != null) {
          jdField_a_of_type_AndroidOsLooper = ((ConnectivityAdapter)localObject2).getHandlerThreadLooper();
        }
        if (jdField_a_of_type_AndroidOsLooper == null)
        {
          localObject2 = new HandlerThread("connectivity", 10);
          ((HandlerThread)localObject2).start();
          jdField_a_of_type_AndroidOsLooper = ((HandlerThread)localObject2).getLooper();
        }
      }
      return jdField_a_of_type_AndroidOsLooper;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\connectivity\ConnectivityAdapterHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */