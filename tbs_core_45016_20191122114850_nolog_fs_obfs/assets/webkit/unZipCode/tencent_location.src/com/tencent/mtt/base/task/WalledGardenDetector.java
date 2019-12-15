package com.tencent.mtt.base.task;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.connectivity.ConnectivityAdapterHolder;
import com.tencent.common.http.Apn;
import com.tencent.mtt.ContextHolder;
import java.util.ArrayList;
import java.util.Iterator;

public class WalledGardenDetector
  implements Handler.Callback, WalledGardenTaskObserver
{
  public static final int CONNECTED = 40;
  public static final int DISCONNECTED = 20;
  public static final int NEED_AUTH = 30;
  public static final String TAG = "WalledGardenDetector";
  public static final int UNKNOWN = 10;
  public static final int UN_DETECT = -1;
  private int jdField_a_of_type_Int = -1;
  private Handler jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper(), this);
  private WalledGardenDetectListener jdField_a_of_type_ComTencentMttBaseTaskWalledGardenDetector$WalledGardenDetectListener;
  private String jdField_a_of_type_JavaLangString;
  private ArrayList<BaseWalledGardenTask> jdField_a_of_type_JavaUtilArrayList;
  private boolean jdField_a_of_type_Boolean = false;
  private ArrayList<WalledGardenMessage> b;
  public ArrayList<WalledGardenMessage> mLastMessages;
  
  private ConnectivityManager a()
  {
    try
    {
      ConnectivityManager localConnectivityManager1 = (ConnectivityManager)ContextHolder.getAppContext().getSystemService("connectivity");
      return localConnectivityManager1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      try
      {
        ConnectivityManager localConnectivityManager2 = (ConnectivityManager)ContextHolder.getAppContext().getSystemService("connectivity");
        return localConnectivityManager2;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
    return null;
  }
  
  private Network a()
  {
    FLogger.d("WalledGardenDetector", "getWiFiNetWork");
    ConnectivityManager localConnectivityManager = a();
    Object localObject4 = null;
    Object localObject5 = null;
    Object localObject1 = null;
    Object localObject3 = localObject5;
    if (localConnectivityManager != null)
    {
      Object localObject2 = localObject4;
      localObject3 = localObject5;
      try
      {
        if (Build.VERSION.SDK_INT >= 21)
        {
          localObject2 = localObject4;
          Network[] arrayOfNetwork = localConnectivityManager.getAllNetworks();
          localObject3 = localObject5;
          if (arrayOfNetwork != null)
          {
            localObject2 = localObject4;
            int j = arrayOfNetwork.length;
            int i = 0;
            for (;;)
            {
              localObject3 = localObject1;
              if (i >= j) {
                break;
              }
              localObject3 = arrayOfNetwork[i];
              localObject2 = localObject1;
              localObject4 = new StringBuilder();
              localObject2 = localObject1;
              ((StringBuilder)localObject4).append("XXX-network : ");
              localObject2 = localObject1;
              ((StringBuilder)localObject4).append(localObject3);
              localObject2 = localObject1;
              FLogger.d("WalledGardenDetector", ((StringBuilder)localObject4).toString());
              localObject2 = localObject1;
              localObject4 = localConnectivityManager.getNetworkInfo((Network)localObject3);
              localObject2 = localObject1;
              if (localObject4 != null)
              {
                localObject2 = localObject1;
                int k = ((NetworkInfo)localObject4).getType();
                localObject2 = localObject1;
                if (k == 1) {
                  localObject2 = localObject3;
                }
              }
              i += 1;
              localObject1 = localObject2;
            }
          }
        }
        localStringBuilder = new StringBuilder();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        localObject3 = localObject2;
      }
    }
    StringBuilder localStringBuilder;
    localStringBuilder.append("XXX-selected network : ");
    localStringBuilder.append(localObject3);
    FLogger.d("WalledGardenDetector", localStringBuilder.toString());
    return (Network)localObject3;
  }
  
  private void a(Network paramNetwork)
  {
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(102);
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(103);
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = 102;
    localMessage.obj = paramNetwork;
    localMessage.arg1 = 1;
    this.jdField_a_of_type_AndroidOsHandler.sendMessageDelayed(localMessage, 1000L);
    this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(103, 15000L);
  }
  
  private void a(BaseWalledGardenTask paramBaseWalledGardenTask)
  {
    if (!this.jdField_a_of_type_JavaUtilArrayList.contains(paramBaseWalledGardenTask)) {
      return;
    }
    this.jdField_a_of_type_JavaUtilArrayList.remove(paramBaseWalledGardenTask);
    if ((paramBaseWalledGardenTask != null) && ((paramBaseWalledGardenTask instanceof WalledGardenDetectTask)))
    {
      WalledGardenDetectTask localWalledGardenDetectTask = (WalledGardenDetectTask)paramBaseWalledGardenTask;
      if ((TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) || (TextUtils.equals(this.jdField_a_of_type_JavaLangString, localWalledGardenDetectTask.mSsid)))
      {
        if (localWalledGardenDetectTask.getResult() == 0) {
          i = 40;
        } else if (localWalledGardenDetectTask.getResult() == 2) {
          i = 30;
        } else if (localWalledGardenDetectTask.getResult() == 1) {
          i = 20;
        } else {
          i = 10;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("seq=1;detect SSID=");
        localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
        localStringBuilder.append(";status=");
        localStringBuilder.append(i);
        localStringBuilder.append(";tag=");
        localStringBuilder.append(paramBaseWalledGardenTask.mTag);
        FLogger.d("WalledGardenDetector", localStringBuilder.toString());
        int j = this.jdField_a_of_type_Int;
        if ((i != j) && (i > j)) {
          this.jdField_a_of_type_Int = i;
        }
        this.b.add(localWalledGardenDetectTask.getMessage());
        paramBaseWalledGardenTask = new StringBuilder();
        paramBaseWalledGardenTask.append("seq=2;mPendingStatus=");
        paramBaseWalledGardenTask.append(this.jdField_a_of_type_Int);
        paramBaseWalledGardenTask.append(";mCurrentTasks.size()=");
        paramBaseWalledGardenTask.append(this.jdField_a_of_type_JavaUtilArrayList.size());
        paramBaseWalledGardenTask.append(";mHandler.hasMessages(MSG_RUN_DETECT_TASK)=");
        paramBaseWalledGardenTask.append(this.jdField_a_of_type_AndroidOsHandler.hasMessages(102));
        FLogger.d("WalledGardenDetector", paramBaseWalledGardenTask.toString());
        if ((this.jdField_a_of_type_JavaUtilArrayList.size() == 0) && (!this.jdField_a_of_type_AndroidOsHandler.hasMessages(102)))
        {
          this.jdField_a_of_type_AndroidOsHandler.removeMessages(102);
          this.jdField_a_of_type_AndroidOsHandler.removeMessages(103);
          paramBaseWalledGardenTask = new ArrayList();
          paramBaseWalledGardenTask.addAll(this.b);
          this.mLastMessages = paramBaseWalledGardenTask;
          paramBaseWalledGardenTask = this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenDetector$WalledGardenDetectListener;
          if (paramBaseWalledGardenTask != null) {
            paramBaseWalledGardenTask.onDetectResult(this.jdField_a_of_type_JavaLangString, this.jdField_a_of_type_Int, localWalledGardenDetectTask.getRedirectUrl());
          }
          this.jdField_a_of_type_JavaLangString = null;
          this.jdField_a_of_type_AndroidOsHandler.removeMessages(102);
          this.jdField_a_of_type_AndroidOsHandler.removeMessages(103);
          return;
        }
        int i = this.jdField_a_of_type_Int;
        if ((i == 40) || (i == 20) || (i == 30))
        {
          this.jdField_a_of_type_AndroidOsHandler.removeMessages(102);
          paramBaseWalledGardenTask = this.jdField_a_of_type_JavaUtilArrayList.iterator();
          while (paramBaseWalledGardenTask.hasNext()) {
            ((BaseWalledGardenTask)paramBaseWalledGardenTask.next()).cancel();
          }
          paramBaseWalledGardenTask = new ArrayList();
          paramBaseWalledGardenTask.addAll(this.b);
          this.mLastMessages = paramBaseWalledGardenTask;
          paramBaseWalledGardenTask = this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenDetector$WalledGardenDetectListener;
          if (paramBaseWalledGardenTask != null) {
            paramBaseWalledGardenTask.onDetectResult(this.jdField_a_of_type_JavaLangString, this.jdField_a_of_type_Int, localWalledGardenDetectTask.getRedirectUrl());
          }
          this.jdField_a_of_type_JavaLangString = null;
          this.jdField_a_of_type_JavaUtilArrayList.clear();
          this.jdField_a_of_type_AndroidOsHandler.removeMessages(102);
          this.jdField_a_of_type_AndroidOsHandler.removeMessages(103);
        }
      }
    }
  }
  
  private void a(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("doDetect..................");
    localStringBuilder.append(paramString);
    FLogger.d("WalledGardenDetector", localStringBuilder.toString());
    this.jdField_a_of_type_JavaLangString = paramString;
    paramString = this.jdField_a_of_type_JavaUtilArrayList;
    if ((paramString != null) && (paramString.size() > 0))
    {
      paramString = this.jdField_a_of_type_JavaUtilArrayList.iterator();
      while (paramString.hasNext()) {
        ((BaseWalledGardenTask)paramString.next()).cancel();
      }
    }
    if (this.jdField_a_of_type_JavaUtilArrayList == null) {
      this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
    }
    if (this.b == null) {
      this.b = new ArrayList();
    }
    this.b.clear();
    this.jdField_a_of_type_Int = -1;
    int i = Build.VERSION.SDK_INT;
    boolean bool = true;
    if (i >= 21)
    {
      paramString = Apn.getActiveNetworkInfo(false);
      if ((paramString != null) && (paramString.getType() != 1)) {}
    }
    else
    {
      bool = false;
    }
    paramString = new StringBuilder();
    paramString.append("doDetect() needNetworkRequest: ");
    paramString.append(bool);
    FLogger.d("WalledGardenDetector", paramString.toString());
    if (bool) {
      try
      {
        a(a());
        return;
      }
      catch (Throwable paramString)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("seq=1;exception=");
        localStringBuilder.append(paramString.getMessage());
        FLogger.d("WalledGardenDetector", localStringBuilder.toString());
        paramString.printStackTrace();
        a(null);
        return;
      }
    }
    a(null);
  }
  
  public void detect(String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(100);
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = 100;
    localMessage.obj = paramString;
    this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    int i = paramMessage.what;
    Network localNetwork = null;
    switch (i)
    {
    default: 
      break;
    case 103: 
      this.jdField_a_of_type_AndroidOsHandler.removeMessages(102);
      paramMessage = this.jdField_a_of_type_JavaUtilArrayList.iterator();
      while (paramMessage.hasNext()) {
        ((BaseWalledGardenTask)paramMessage.next()).cancel();
      }
      paramMessage = new ArrayList();
      paramMessage.addAll(this.b);
      this.mLastMessages = paramMessage;
      paramMessage = this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenDetector$WalledGardenDetectListener;
      if (paramMessage != null) {
        paramMessage.onDetectResult(this.jdField_a_of_type_JavaLangString, 10, "");
      }
      this.jdField_a_of_type_JavaLangString = null;
      this.jdField_a_of_type_JavaUtilArrayList.clear();
      this.jdField_a_of_type_AndroidOsHandler.removeMessages(102);
      break;
    case 102: 
      if (paramMessage.obj != null) {
        localNetwork = (Network)paramMessage.obj;
      }
      WalledGardenDetectTask localWalledGardenDetectTask = new WalledGardenDetectTask();
      localWalledGardenDetectTask.setSsid(this.jdField_a_of_type_JavaLangString);
      localWalledGardenDetectTask.addObserver(this);
      localWalledGardenDetectTask.setNetwork(localNetwork);
      i = paramMessage.arg1;
      paramMessage = new StringBuilder();
      paramMessage.append("ID============================");
      paramMessage.append(i);
      FLogger.d("WalledGardenDetector", paramMessage.toString());
      if (i % 2 == 1)
      {
        paramMessage = new StringBuilder();
        paramMessage.append("QB204_");
        paramMessage.append(i);
        localWalledGardenDetectTask.mTag = paramMessage.toString();
      }
      else
      {
        localWalledGardenDetectTask.setWalledGardenUrl("http://connect.rom.miui.com/generate_204");
        paramMessage = new StringBuilder();
        paramMessage.append("MIUI204_");
        paramMessage.append(i);
        localWalledGardenDetectTask.mTag = paramMessage.toString();
      }
      this.jdField_a_of_type_JavaUtilArrayList.add(localWalledGardenDetectTask);
      ConnectivityAdapterHolder.execute(localWalledGardenDetectTask);
      if (i < 4)
      {
        paramMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
        paramMessage.what = 102;
        paramMessage.obj = localNetwork;
        paramMessage.arg1 = (i + 1);
        this.jdField_a_of_type_AndroidOsHandler.sendMessageDelayed(paramMessage, 2000L);
      }
      else
      {
        this.jdField_a_of_type_AndroidOsHandler.removeMessages(102);
      }
      break;
    case 101: 
      a((BaseWalledGardenTask)paramMessage.obj);
      break;
    case 100: 
      if (paramMessage.obj != null) {
        a((String)paramMessage.obj);
      }
      break;
    }
    return false;
  }
  
  public void onDetectTaskComplete(BaseWalledGardenTask paramBaseWalledGardenTask)
  {
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = 101;
    localMessage.obj = paramBaseWalledGardenTask;
    this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  public void setWalledGardenDetectListener(WalledGardenDetectListener paramWalledGardenDetectListener)
  {
    this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenDetector$WalledGardenDetectListener = paramWalledGardenDetectListener;
  }
  
  public static abstract interface WalledGardenDetectListener
  {
    public abstract void onDetectResult(String paramString1, int paramInt, String paramString2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\task\WalledGardenDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */