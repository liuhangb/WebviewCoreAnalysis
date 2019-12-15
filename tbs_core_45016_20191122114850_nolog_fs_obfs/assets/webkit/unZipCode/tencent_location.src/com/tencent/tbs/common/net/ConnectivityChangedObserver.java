package com.tencent.tbs.common.net;

import android.content.Intent;
import com.tencent.common.http.Apn;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.observer.AppBroadcastObserver;
import com.tencent.tbs.common.utils.NetworkUtils;

public class ConnectivityChangedObserver
  implements AppBroadcastObserver
{
  public void onBroadcastReceiver(Intent paramIntent)
  {
    if ((paramIntent != null) && ("android.net.conn.CONNECTIVITY_CHANGE".equals(paramIntent.getAction())))
    {
      LogUtils.d("x5-ip-list", "ConnectivityChangedObserver onBroadcastReceiver CONNECTIVITY_ACTION");
      BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          try
          {
            Apn.refreshApntype();
            NetworkUtils.onConnectivityChanged(Apn.getApnTypeS());
            return;
          }
          catch (Exception localException) {}
        }
      });
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\net\ConnectivityChangedObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */