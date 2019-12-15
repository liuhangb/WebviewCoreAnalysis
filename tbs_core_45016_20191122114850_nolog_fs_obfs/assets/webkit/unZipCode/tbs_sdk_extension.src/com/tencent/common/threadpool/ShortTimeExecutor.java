package com.tencent.common.threadpool;

import android.os.Handler;
import java.util.concurrent.Executor;

public class ShortTimeExecutor
  implements Executor
{
  private Handler a = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime());
  
  public void execute(Runnable paramRunnable)
  {
    this.a.post(paramRunnable);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\ShortTimeExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */