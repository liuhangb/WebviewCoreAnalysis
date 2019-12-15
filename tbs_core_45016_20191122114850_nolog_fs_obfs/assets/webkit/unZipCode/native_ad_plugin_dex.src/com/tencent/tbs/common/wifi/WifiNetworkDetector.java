package com.tencent.tbs.common.wifi;

import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.mtt.base.task.WalledGardenDetector;
import com.tencent.mtt.base.task.WalledGardenDetector.WalledGardenDetectListener;
import com.tencent.mtt.base.task.WalledGardenMessage;
import com.tencent.tbs.common.utils.NetworkUtils;
import com.tencent.tbs.common.wifi.state.WifiBaseStateMonitor;
import com.tencent.tbs.common.wifi.state.WifiBaseStateMonitor.WifiBaseStateObserver;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public final class WifiNetworkDetector
  implements Handler.Callback, WalledGardenDetector.WalledGardenDetectListener, WifiBaseStateMonitor.WifiBaseStateObserver
{
  public static final int CONNECTED = 40;
  public static final int DISCONNECTED = 20;
  private static final String EVENT_NAME = "MTT_DEV_DEBUG_ACTION";
  private static final String KEY_CONNECTIVITY_BSSID = "key2";
  private static final String KEY_CONNECTIVITY_RESULT_FINAL = "key1";
  private static final String KEY_CONNECTIVITY_SIGNAL = "key3";
  private static final String KEY_COST_TIME = "key4";
  private static final String KEY_ELAPSED_ = "key";
  private static final String KEY_INDEX = "key6";
  private static final String KEY_SSID = "key5";
  private static final String KEY_TYPE = "type";
  public static final int NEED_AUTH = 30;
  public static final String TAG = "WifiNetworkDetector";
  public static final int UNKNOWN = 10;
  public static final int UN_DETECT = -1;
  private static final String VALUE_TYPE = "wifi_det";
  private static WifiNetworkDetector mInstance;
  private String mCurrentRequestSSID;
  private CopyOnWriteArrayList<NetworkDetectListener> mDetectListeners;
  private Handler mHandler = new Handler(Looper.getMainLooper(), this);
  protected int mIndex = 1;
  public String mLastStatSsid = "";
  private String mPortalUrl;
  private String mSsid = "";
  long mStartTime = -1L;
  private int mStatus = -1;
  public ArrayList<WalledGardenMessage> mWalledGardenMessages;
  public int mWalledGardenRssi;
  private WalledGardenDetector mWifi204Detector = new WalledGardenDetector();
  
  private WifiNetworkDetector()
  {
    this.mWifi204Detector.setWalledGardenDetectListener(this);
    this.mDetectListeners = new CopyOnWriteArrayList();
    WifiBaseStateMonitor.getInstance().addObserver(this);
  }
  
  public static WifiNetworkDetector getInstance()
  {
    if (mInstance == null) {
      try
      {
        if (mInstance == null) {
          mInstance = new WifiNetworkDetector();
        }
      }
      finally {}
    }
    return mInstance;
  }
  
  public void addDetectListener(NetworkDetectListener paramNetworkDetectListener)
  {
    if (!this.mDetectListeners.contains(paramNetworkDetectListener)) {
      this.mDetectListeners.add(paramNetworkDetectListener);
    }
  }
  
  public void detect(boolean paramBoolean)
  {
    String str = WifiCommonUtils.getSsidWithoutQuotation(NetworkUtils.getCurrentSSID(ContextHolder.getAppContext()));
    if ((!TextUtils.isEmpty(this.mLastStatSsid)) && (!TextUtils.equals(this.mLastStatSsid, str)))
    {
      this.mLastStatSsid = "";
      this.mStatus = -1;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("doDetect: C[");
    localStringBuilder.append(this.mCurrentRequestSSID);
    localStringBuilder.append("] L[");
    localStringBuilder.append(this.mLastStatSsid);
    localStringBuilder.append("][");
    localStringBuilder.append(str);
    localStringBuilder.append("][");
    localStringBuilder.append(paramBoolean);
    localStringBuilder.append("][");
    localStringBuilder.append(this.mStatus);
    localStringBuilder.append("]");
    LogUtils.d("WifiNetworkDetector", localStringBuilder.toString());
    if (TextUtils.isEmpty(str)) {
      return;
    }
    if ((this.mStatus != -1) && (!paramBoolean)) {
      return;
    }
    if ((!TextUtils.isEmpty(this.mCurrentRequestSSID)) && (TextUtils.equals(this.mCurrentRequestSSID, str))) {
      return;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("send detect ssid :　");
    localStringBuilder.append(str);
    LogUtils.d("WifiNetworkDetector", localStringBuilder.toString());
    this.mCurrentRequestSSID = str;
    this.mStartTime = System.currentTimeMillis();
    this.mWifi204Detector.detect(this.mCurrentRequestSSID);
  }
  
  public String getCurrentDetectLog()
  {
    Object localObject1 = this.mWalledGardenMessages;
    if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("探测时信号强度:");
      ((StringBuilder)localObject1).append(this.mWalledGardenRssi);
      Object localObject2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      ((StringBuilder)localObject1).append("探测时间:");
      ((StringBuilder)localObject1).append(((SimpleDateFormat)localObject2).format(new Date()));
      ((StringBuilder)localObject1).append("|探测结果:");
      localObject2 = this.mWalledGardenMessages.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        WalledGardenMessage localWalledGardenMessage = (WalledGardenMessage)((Iterator)localObject2).next();
        ((StringBuilder)localObject1).append("{");
        ((StringBuilder)localObject1).append(localWalledGardenMessage.toString());
        ((StringBuilder)localObject1).append("}\n");
      }
      return ((StringBuilder)localObject1).toString();
    }
    return "";
  }
  
  public int getCurrentStatus()
  {
    return this.mStatus;
  }
  
  public int getCurrentStatus(String paramString)
  {
    if (TextUtils.equals(paramString, this.mSsid)) {
      return this.mStatus;
    }
    return -1;
  }
  
  public String getPortalUrl()
  {
    if (this.mStatus == 30) {
      return this.mPortalUrl;
    }
    return null;
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    return false;
  }
  
  public void onDetectResult(String paramString1, int paramInt, String paramString2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onDetectResult: C[");
    ((StringBuilder)localObject).append(this.mCurrentRequestSSID);
    ((StringBuilder)localObject).append("] L[");
    ((StringBuilder)localObject).append(this.mLastStatSsid);
    ((StringBuilder)localObject).append("][");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append("]L[");
    ((StringBuilder)localObject).append(this.mStatus);
    ((StringBuilder)localObject).append("][");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append("]");
    LogUtils.d("WifiNetworkDetector", ((StringBuilder)localObject).toString());
    if (TextUtils.equals(paramString1, this.mCurrentRequestSSID))
    {
      this.mSsid = paramString1;
      this.mWalledGardenRssi = -1;
      localObject = WifiUtils.getConnectionInfo();
      if (localObject != null) {
        this.mWalledGardenRssi = ((WifiInfo)localObject).getRssi();
      }
      WifiUtils.getSignalIntensity(this.mWalledGardenRssi);
      this.mWalledGardenMessages = this.mWifi204Detector.mLastMessages;
      LogUtils.d("WifiNetworkDetector", getCurrentDetectLog());
      int i = this.mStatus;
      this.mPortalUrl = paramString2;
      this.mCurrentRequestSSID = "";
      this.mStatus = paramInt;
      paramString2 = this.mDetectListeners.iterator();
      while (paramString2.hasNext()) {
        ((NetworkDetectListener)paramString2.next()).onDetectStatusChanged(paramString1, this.mStatus, i);
      }
    }
  }
  
  public void onStateChange(Bundle paramBundle, int paramInt)
  {
    if ((paramInt == 0) && (WifiBaseStateMonitor.isDisconnectedState(paramBundle.getInt("STATE"))) && (this.mStatus != -1))
    {
      paramBundle = WifiCommonUtils.getSsidWithoutQuotation(NetworkUtils.getCurrentSSID(ContextHolder.getAppContext()));
      this.mStatus = -1;
      this.mPortalUrl = null;
      this.mCurrentRequestSSID = "";
      this.mIndex = 1;
      this.mSsid = "";
      Iterator localIterator = this.mDetectListeners.iterator();
      while (localIterator.hasNext())
      {
        NetworkDetectListener localNetworkDetectListener = (NetworkDetectListener)localIterator.next();
        paramInt = this.mStatus;
        localNetworkDetectListener.onDetectStatusChanged(paramBundle, paramInt, paramInt);
      }
    }
  }
  
  public void removeDetectListener(NetworkDetectListener paramNetworkDetectListener)
  {
    this.mDetectListeners.remove(paramNetworkDetectListener);
  }
  
  public void reset()
  {
    this.mStatus = -1;
    this.mPortalUrl = null;
    this.mCurrentRequestSSID = "";
  }
  
  public static abstract interface NetworkDetectListener
  {
    public abstract void onDetectStatusChanged(String paramString, int paramInt1, int paramInt2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\WifiNetworkDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */