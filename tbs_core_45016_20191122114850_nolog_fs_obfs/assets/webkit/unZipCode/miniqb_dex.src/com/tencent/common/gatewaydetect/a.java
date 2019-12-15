package com.tencent.common.gatewaydetect;

import android.os.SystemClock;
import android.util.Pair;
import com.tencent.basesupport.FLogger;
import com.tencent.common.connectivity.ConnectivityAdapterHolder;
import com.tencent.common.http.Apn;
import com.tencent.common.http.ConnectionChangeHandler;
import com.tencent.common.http.ConnectionChangeHandler.ConnectionChangeCallback;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

class a
  implements GatewayDetector.GatewayDetectCallback, ConnectionChangeHandler.ConnectionChangeCallback
{
  private ConnectionChangeHandler jdField_a_of_type_ComTencentCommonHttpConnectionChangeHandler = new ConnectionChangeHandler(this);
  private HashMap<String, Pair<Long, GatewayDetector.GatewayInfo>> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  private ReentrantLock jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock = new ReentrantLock();
  
  protected a()
  {
    this.jdField_a_of_type_ComTencentCommonHttpConnectionChangeHandler.startObserve();
  }
  
  private String a()
  {
    return Apn.getApnNameWithBSSID(Apn.getApnTypeS());
  }
  
  public GatewayDetector.GatewayInfo a()
  {
    Object localObject = a();
    localObject = (Pair)this.jdField_a_of_type_JavaUtilHashMap.get(localObject);
    if (localObject != null)
    {
      long l = ((Long)((Pair)localObject).first).longValue();
      localObject = (GatewayDetector.GatewayInfo)((Pair)localObject).second;
      if ((SystemClock.elapsedRealtime() - l < 180000L) && (localObject != null)) {
        return (GatewayDetector.GatewayInfo)localObject;
      }
    }
    return null;
  }
  
  public GatewayDetector.GatewayInfo b()
  {
    boolean bool = this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.tryLock();
    if (!bool) {
      this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.lock();
    }
    if (!bool) {}
    for (;;)
    {
      try
      {
        localObject1 = a();
        localObject4 = localObject1;
        if (localObject1 != null) {
          continue;
        }
        new b(this).run();
        localObject4 = a();
      }
      finally
      {
        Object localObject1;
        Object localObject4;
        continue;
      }
      this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
      throw ((Throwable)localObject1);
      this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("syncDetectGatewayInfo: ");
      ((StringBuilder)localObject1).append(localObject4);
      FLogger.d("NetworkDetector", ((StringBuilder)localObject1).toString());
      return (GatewayDetector.GatewayInfo)localObject4;
      Object localObject3 = null;
    }
  }
  
  public void onConnectionChanged()
  {
    ConnectivityAdapterHolder.execute(new Runnable()
    {
      public void run()
      {
        a.this.b();
      }
    });
  }
  
  public void onConnectionChanging() {}
  
  public void onGatewayDetectResult(boolean paramBoolean, GatewayDetector.GatewayInfo paramGatewayInfo)
  {
    if (!paramBoolean) {
      return;
    }
    String str = a();
    if ((Apn.isWifiMode()) && (paramGatewayInfo.type == 0))
    {
      paramGatewayInfo.type = 2;
      paramGatewayInfo.apnName = "wifi";
    }
    long l = SystemClock.elapsedRealtime();
    this.jdField_a_of_type_JavaUtilHashMap.put(str, new Pair(Long.valueOf(l), paramGatewayInfo));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\gatewaydetect\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */