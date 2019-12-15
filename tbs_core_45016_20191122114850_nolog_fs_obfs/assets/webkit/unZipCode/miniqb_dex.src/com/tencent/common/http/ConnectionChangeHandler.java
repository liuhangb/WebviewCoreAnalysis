package com.tencent.common.http;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.basesupport.FLogger;
import com.tencent.common.connectivity.ConnectivityAdapterHolder;
import com.tencent.mtt.ContextHolder;

public class ConnectionChangeHandler
  extends BroadcastReceiver
{
  private volatile int jdField_a_of_type_Int = 0;
  private Handler jdField_a_of_type_AndroidOsHandler = new Handler(ConnectivityAdapterHolder.getHandlerThreadLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        
      case 2003: 
        if (ConnectionChangeHandler.a(ConnectionChangeHandler.this) != null)
        {
          ConnectionChangeHandler.a(ConnectionChangeHandler.this).onConnectionChanging();
          return;
        }
        break;
      case 2002: 
        ConnectionChangeHandler.a(ConnectionChangeHandler.this);
      }
    }
  };
  private ConnectionChangeCallback jdField_a_of_type_ComTencentCommonHttpConnectionChangeHandler$ConnectionChangeCallback;
  private boolean jdField_a_of_type_Boolean;
  
  public ConnectionChangeHandler(ConnectionChangeCallback paramConnectionChangeCallback)
  {
    this.jdField_a_of_type_ComTencentCommonHttpConnectionChangeHandler$ConnectionChangeCallback = paramConnectionChangeCallback;
    this.jdField_a_of_type_Boolean = false;
  }
  
  private void a()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("processNetworkChange BEGINS, mCheckNetwrokRetryTime = ");
    ((StringBuilder)localObject).append(this.jdField_a_of_type_Int);
    FLogger.d("ConnectionChangeHandler", ((StringBuilder)localObject).toString());
    this.jdField_a_of_type_Int += 1;
    if ((!Apn.isNetworkConnected()) && (this.jdField_a_of_type_Int <= 5))
    {
      FLogger.d("ConnectionChangeHandler", "onBroadcastReceiver, isNetworkConnected no!! wait for 1s");
      this.jdField_a_of_type_AndroidOsHandler.removeMessages(2002);
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(2002, 1000L);
      return;
    }
    FLogger.d("ConnectionChangeHandler", "onBroadcastReceiver, isNetworkConnected yes!!");
    this.jdField_a_of_type_Int = 0;
    localObject = this.jdField_a_of_type_ComTencentCommonHttpConnectionChangeHandler$ConnectionChangeCallback;
    if (localObject != null) {
      ((ConnectionChangeCallback)localObject).onConnectionChanged();
    }
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((paramIntent != null) && ("android.net.conn.CONNECTIVITY_CHANGE".equals(paramIntent.getAction())))
    {
      FLogger.d("ConnectionChangeHandler", "onBroadcastReceiver, connect change");
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessage(2003);
      this.jdField_a_of_type_AndroidOsHandler.removeMessages(2002);
      this.jdField_a_of_type_Int = 0;
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(2002, 2000L);
    }
  }
  
  public void startObserve()
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    try
    {
      IntentFilter localIntentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
      ContextHolder.getAppContext().registerReceiver(this, localIntentFilter);
      FLogger.d("ConnectionChangeHandler", "addBroadcastObserver success");
      this.jdField_a_of_type_Boolean = true;
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public static abstract interface ConnectionChangeCallback
  {
    public abstract void onConnectionChanged();
    
    public abstract void onConnectionChanging();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\ConnectionChangeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */