package com.tencent.tbs.common.wifi;

import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.LogUtils;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

public class WifiNetworkDetectorFacade
  implements Handler.Callback, WifiNetworkDetector.NetworkDetectListener
{
  static final String TAG = "WifiNetworkDetectorFacade";
  private static WifiNetworkDetectorFacade mInstance;
  final int MSG_UI_STATUS_CHANGED = 10;
  final int MSG_WORK_STATUS_CHANGED = 1;
  private CopyOnWriteArraySet<WifiNetworkDetector.NetworkDetectListener> mListeners = new CopyOnWriteArraySet();
  Handler mUiHandler = new Handler(Looper.getMainLooper(), this);
  Handler mWorkHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime(), this);
  
  private WifiNetworkDetectorFacade()
  {
    WifiNetworkDetector.getInstance().addDetectListener(this);
  }
  
  public static WifiNetworkDetectorFacade getInstance()
  {
    if (mInstance == null) {
      try
      {
        if (mInstance == null) {
          mInstance = new WifiNetworkDetectorFacade();
        }
      }
      finally {}
    }
    return mInstance;
  }
  
  private int transResult(int paramInt, WifiInfo paramWifiInfo)
  {
    if ((paramInt != 20) && (paramInt != 10)) {
      return paramInt;
    }
    try
    {
      paramInt = WifiManager.calculateSignalLevel(paramWifiInfo.getRssi(), 100);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      paramInt = WifiUtils.calculateSignalLevel(paramWifiInfo.getRssi(), 100);
    }
    if (paramInt > 50) {
      return 20;
    }
    return 10;
  }
  
  public void addDetectListener(WifiNetworkDetector.NetworkDetectListener paramNetworkDetectListener)
  {
    if (paramNetworkDetectListener != null) {
      this.mListeners.add(paramNetworkDetectListener);
    }
  }
  
  public void detect(boolean paramBoolean)
  {
    WifiNetworkDetector.getInstance().detect(paramBoolean);
  }
  
  public int getCurrentStatus()
  {
    return WifiNetworkDetector.getInstance().getCurrentStatus();
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    int i = paramMessage.what;
    Object localObject;
    if (i != 1)
    {
      if (i != 10) {
        return false;
      }
      localObject = this.mListeners.iterator();
      while (((Iterator)localObject).hasNext()) {
        ((WifiNetworkDetector.NetworkDetectListener)((Iterator)localObject).next()).onDetectStatusChanged((String)paramMessage.obj, paramMessage.arg1, paramMessage.arg2);
      }
      return true;
    }
    if ((paramMessage.obj instanceof String))
    {
      LogUtils.d("WifiNetworkDetectorFacade", "[handleMessage] MSG_ON_DETECT_STATUS_CHANGED");
      localObject = WifiCommonUtils.getConnectionInfo();
      if ((localObject != null) && (((WifiInfo)localObject).getSupplicantState() == SupplicantState.COMPLETED))
      {
        Message localMessage = this.mUiHandler.obtainMessage(10);
        localMessage.obj = paramMessage.obj;
        localMessage.arg1 = transResult(paramMessage.arg1, (WifiInfo)localObject);
        localMessage.arg2 = paramMessage.arg2;
        localMessage.sendToTarget();
      }
    }
    return true;
  }
  
  public void onDetectStatusChanged(String paramString, int paramInt1, int paramInt2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[onDetectStatusChanged] ssid:");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(", status:");
    ((StringBuilder)localObject).append(paramInt1);
    ((StringBuilder)localObject).append(", lastStatus:");
    ((StringBuilder)localObject).append(paramInt2);
    LogUtils.d("WifiNetworkDetectorFacade", ((StringBuilder)localObject).toString());
    localObject = this.mWorkHandler.obtainMessage(1);
    ((Message)localObject).obj = paramString;
    ((Message)localObject).arg1 = paramInt1;
    ((Message)localObject).arg2 = paramInt2;
    ((Message)localObject).sendToTarget();
  }
  
  public void removeDetectListener(WifiNetworkDetector.NetworkDetectListener paramNetworkDetectListener)
  {
    if (paramNetworkDetectListener != null) {
      this.mListeners.remove(paramNetworkDetectListener);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\WifiNetworkDetectorFacade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */