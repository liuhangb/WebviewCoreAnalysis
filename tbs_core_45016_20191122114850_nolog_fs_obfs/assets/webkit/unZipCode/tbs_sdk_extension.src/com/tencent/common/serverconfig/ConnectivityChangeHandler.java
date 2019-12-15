package com.tencent.common.serverconfig;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.basesupport.FLogger;
import com.tencent.common.http.Apn;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.wup.base.WupTimeOutController;

public class ConnectivityChangeHandler
{
  private volatile int jdField_a_of_type_Int = 0;
  Handler jdField_a_of_type_AndroidOsHandler = new Handler(BrowserExecutorSupplier.getLooperForRunLongTime())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what != 2002) {
        return;
      }
      ConnectivityChangeHandler.this.a();
    }
  };
  private a jdField_a_of_type_ComTencentCommonServerconfigA;
  
  public ConnectivityChangeHandler(a parama)
  {
    this.jdField_a_of_type_ComTencentCommonServerconfigA = parama;
  }
  
  void a()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("processNetworkChange BEGINS, mCheckNetwrokRetryTime = ");
    ((StringBuilder)localObject).append(this.jdField_a_of_type_Int);
    FLogger.d("ConnectivityChangeHandler", ((StringBuilder)localObject).toString());
    this.jdField_a_of_type_Int += 1;
    if ((!Apn.isNetworkConnected()) && (this.jdField_a_of_type_Int <= 5))
    {
      FLogger.d("ConnectivityChangeHandler", "onBroadcastReceiver, isNetworkConnected no!! wait for 1s");
      this.jdField_a_of_type_AndroidOsHandler.removeMessages(2002);
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(2002, 1000L);
      return;
    }
    FLogger.d("ConnectivityChangeHandler", "onBroadcastReceiver, isNetworkConnected yes!!");
    this.jdField_a_of_type_Int = 0;
    localObject = this.jdField_a_of_type_ComTencentCommonServerconfigA;
    if (localObject != null) {
      ((a)localObject).e("network_change");
    }
  }
  
  public void onConnectivityIntent(Intent paramIntent)
  {
    if ((paramIntent != null) && ("android.net.conn.CONNECTIVITY_CHANGE".equals(paramIntent.getAction())))
    {
      FLogger.d("ConnectivityChangeHandler", "onBroadcastReceiver, connect change");
      this.jdField_a_of_type_AndroidOsHandler.removeMessages(2002);
      this.jdField_a_of_type_Int = 0;
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(2002, 2000L);
      WupTimeOutController.getInstance().resetAvgReadTimeout();
      WupTimeOutController.getInstance().resetWUPConnTimeout();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\serverconfig\ConnectivityChangeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */