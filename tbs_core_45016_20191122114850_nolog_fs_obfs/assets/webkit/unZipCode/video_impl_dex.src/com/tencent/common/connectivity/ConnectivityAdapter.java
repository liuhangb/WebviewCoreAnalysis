package com.tencent.common.connectivity;

import android.os.Looper;
import com.tencent.common.manifest.annotation.Service;
import java.util.concurrent.ExecutorService;

@Service
public abstract interface ConnectivityAdapter
{
  public abstract Looper getHandlerThreadLooper();
  
  public abstract ExecutorService getThreadExecutor();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\connectivity\ConnectivityAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */