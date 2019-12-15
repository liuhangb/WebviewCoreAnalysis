package com.tencent.common;

import android.os.Looper;
import com.tencent.common.connectivity.ConnectivityAdapter;
import com.tencent.common.manifest.annotation.CreateMethod;
import com.tencent.common.manifest.annotation.ServiceImpl;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import java.util.concurrent.ExecutorService;

@ServiceImpl(createMethod=CreateMethod.GET, service=ConnectivityAdapter.class)
public class QBConnectivityAdapter
  implements ConnectivityAdapter
{
  private static final ConnectivityAdapter a = new QBConnectivityAdapter();
  
  public static ConnectivityAdapter getInstance()
  {
    return a;
  }
  
  public Looper getHandlerThreadLooper()
  {
    return BrowserExecutorSupplier.getLooperForRunLongTime();
  }
  
  public ExecutorService getThreadExecutor()
  {
    return BrowserExecutorSupplier.getInstance().getTimeOutExecutor();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\QBConnectivityAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */