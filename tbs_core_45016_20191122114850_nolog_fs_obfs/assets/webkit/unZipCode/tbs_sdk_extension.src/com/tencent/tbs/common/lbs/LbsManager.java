package com.tencent.tbs.common.lbs;

import android.content.Context;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.WifiInfo;
import com.tencent.common.utils.WifiInfo.BySigalLevelComparator;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public final class LbsManager
  implements ILbsListener
{
  private static final boolean DEBUG = false;
  private static final int ERROR_CREATED = 1;
  private static final int ERROR_LOCATION = 2;
  private static final String ERROR_MSG_LOCATION = "created error";
  public static final String KEY_ACTION = "action";
  public static final String KEY_ERROR = "error";
  public static final String KEY_MESSAGE = "message";
  public static final String KEY_TIME = "time";
  public static final String KEY_TYPE = "type";
  private static final String TAG = "LbsManager";
  public static final String TYPE_GPS = "gps";
  private static LbsManager sLbsManager;
  final ArrayList<LbsCallback> mCallbackMapOneShots = new ArrayList();
  final ArrayList<LbsCallback> mCallbackMapWatchers = new ArrayList();
  private boolean mDebugLbs = false;
  int mGpsCallBackCount = 0;
  long mGpsStartTime = 0L;
  private boolean mInited = false;
  Location mLastPostion = null;
  private LbsInfoManager mLbsInfoManager = null;
  ILogEvent mLogEvent;
  private String mStrGeoError = null;
  ITxLocationManagerProxy mTxLocationManagerProxy = null;
  
  private boolean getCellId(String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    Cell localCell = this.mLbsInfoManager.getCell();
    if (localCell == null) {
      return false;
    }
    paramArrayOfString[0] = Integer.toString(localCell.shMnc);
    paramArrayOfString[1] = Integer.toString(localCell.shMcc);
    paramArrayOfInt[0] = localCell.iLac;
    paramArrayOfInt[1] = localCell.iCellId;
    return true;
  }
  
  public static LbsManager getInstance()
  {
    try
    {
      if (sLbsManager == null) {
        sLbsManager = new LbsManager();
      }
      LbsManager localLbsManager = sLbsManager;
      return localLbsManager;
    }
    finally {}
  }
  
  public static String getLocalIpAddress()
  {
    try
    {
      InetAddress localInetAddress;
      do
      {
        localObject1 = NetworkInterface.getNetworkInterfaces();
        Object localObject2;
        while ((localObject2 == null) || (!((Enumeration)localObject2).hasMoreElements()))
        {
          do
          {
            if ((localObject1 == null) || (!((Enumeration)localObject1).hasMoreElements())) {
              break;
            }
            localObject2 = (NetworkInterface)((Enumeration)localObject1).nextElement();
          } while (localObject2 == null);
          localObject2 = ((NetworkInterface)localObject2).getInetAddresses();
        }
        localInetAddress = (InetAddress)((Enumeration)localObject2).nextElement();
      } while ((localInetAddress == null) || (localInetAddress.isLoopbackAddress()) || (!(localInetAddress instanceof Inet4Address)));
      Object localObject1 = localInetAddress.getHostAddress();
      return (String)localObject1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public static ArrayList<Long> getWifiMac()
  {
    ArrayList localArrayList1 = new ArrayList();
    Object localObject1 = (WifiManager)LBS.getContext().getApplicationContext().getSystemService("wifi");
    if (localObject1 != null) {
      try
      {
        int i = ((WifiManager)localObject1).getWifiState();
        if (i != 3) {
          return localArrayList1;
        }
        ArrayList localArrayList2 = new ArrayList();
        Object localObject2;
        try
        {
          localObject1 = ((WifiManager)localObject1).getScanResults();
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
          localObject2 = null;
        }
        if (localObject2 != null)
        {
          int k = ((List)localObject2).size();
          if (k <= 0) {
            return localArrayList1;
          }
          int j = 0;
          i = 0;
          while (i < k)
          {
            ScanResult localScanResult = (ScanResult)((List)localObject2).get(i);
            localArrayList2.add(new WifiInfo(localScanResult.SSID, localScanResult.BSSID, localScanResult.level));
            i += 1;
          }
          Collections.sort(localArrayList2, new WifiInfo.BySigalLevelComparator());
          i = j;
          while ((i < k) && (i < 5))
          {
            try
            {
              localArrayList1.add(Long.valueOf(((WifiInfo)localArrayList2.get(i)).getSsid()));
            }
            catch (Exception localException2)
            {
              localException2.printStackTrace();
            }
            i += 1;
          }
          return localArrayList1;
        }
        return localArrayList1;
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
        return localArrayList1;
      }
    }
    return localArrayList1;
  }
  
  private void init()
  {
    if (this.mTxLocationManagerProxy == null)
    {
      if (this.mInited) {
        return;
      }
      this.mInited = true;
      BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          try
          {
            LbsManager.this.mTxLocationManagerProxy = DexLoaderProxy.getTxLocationManagerProxy(LBS.getContext(), getClass().getClassLoader());
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            LbsManager.this.reportEvent("init", "init dex", "getTxLocationManagerProxy", 0L, localException);
          }
          if (LbsManager.this.mTxLocationManagerProxy != null)
          {
            LbsManager localLbsManager = LbsManager.this;
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("[onDexClassInstanceCreated] mCallbackMapOneShots.size():");
            localStringBuilder.append(LbsManager.this.mCallbackMapOneShots.size());
            localStringBuilder.append(", mCallbackMapWatchers.size():");
            localStringBuilder.append(LbsManager.this.mCallbackMapWatchers.size());
            localStringBuilder.append(", ");
            localStringBuilder.append(LbsManager.this.mStrGeoError);
            localLbsManager.logDebug("LbsManager", localStringBuilder.toString());
            if (LbsManager.this.mDebugLbs) {
              LbsManager.this.mTxLocationManagerProxy.setDebug(LbsManager.this.mDebugLbs);
            }
            if ((LbsManager.this.mCallbackMapOneShots.size() > 0) || (LbsManager.this.mCallbackMapWatchers.size() > 0)) {
              LbsManager.this.startUpdating();
            }
          }
          else
          {
            LbsManager.this.onLocationFailed(1, "created error");
          }
        }
      });
    }
  }
  
  private void logDebug(String paramString1, String paramString2) {}
  
  private void startUpdating()
  {
    int i;
    Object localObject1;
    boolean bool2;
    label62:
    label106:
    try
    {
      if (this.mTxLocationManagerProxy == null) {
        break label210;
      }
      logDebug("LbsManager", "startUpdating");
      i = 0;
      if (i >= this.mCallbackMapOneShots.size()) {
        break label190;
      }
      localObject1 = (LbsCallback)this.mCallbackMapOneShots.get(i);
      if ((localObject1 == null) || (!((LbsCallback)localObject1).gpsEnabled())) {
        break label183;
      }
      bool2 = true;
    }
    finally {}
    boolean bool1 = bool2;
    if (i < this.mCallbackMapWatchers.size())
    {
      localObject1 = (LbsCallback)this.mCallbackMapWatchers.get(i);
      if ((localObject1 == null) || (!((LbsCallback)localObject1).gpsEnabled())) {
        break label203;
      }
      bool1 = true;
    }
    this.mTxLocationManagerProxy.startRequestLocation(this, bool1);
    for (;;)
    {
      this.mGpsStartTime = System.currentTimeMillis();
      this.mGpsCallBackCount = 0;
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("startRequestLocation gpsEnabled=");
      ((StringBuilder)localObject1).append(bool1);
      reportEvent("gps", "start", ((StringBuilder)localObject1).toString(), 0L, null);
      return;
      label183:
      i += 1;
      break;
      label190:
      bool2 = false;
      bool1 = bool2;
      if (bool2) {
        break label106;
      }
      i = 0;
      break label62;
      label203:
      i += 1;
      break label62;
      label210:
      bool1 = false;
    }
  }
  
  private void stopRequestIfNeeded()
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[stopRequestIfNeeded] mTxLocationManagerProxy:");
      localStringBuilder.append(this.mTxLocationManagerProxy);
      localStringBuilder.append(", mCallbackMapWatchers.size():");
      localStringBuilder.append(this.mCallbackMapWatchers.size());
      localStringBuilder.append(", mCallbackMapOneShots.size():");
      localStringBuilder.append(this.mCallbackMapOneShots.size());
      logDebug("LbsManager", localStringBuilder.toString());
      if ((this.mTxLocationManagerProxy != null) && (this.mCallbackMapWatchers.size() == 0) && (this.mCallbackMapOneShots.size() == 0)) {
        stopUpdating();
      }
      return;
    }
    finally {}
  }
  
  private void stopUpdating()
  {
    try
    {
      if (this.mTxLocationManagerProxy != null)
      {
        logDebug("LbsManager", "stopUpdating");
        this.mTxLocationManagerProxy.stopRequestLocation();
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("stopRequestLocation mGpsCallBackCount=");
      localStringBuilder.append(this.mGpsCallBackCount);
      reportEvent("gps", "stop", localStringBuilder.toString(), 0L, null);
      return;
    }
    finally {}
  }
  
  public void getLbsCellIdInfo(int[] paramArrayOfInt, String[] paramArrayOfString)
  {
    Object localObject = new String[2];
    localObject[0] = "";
    localObject[1] = "";
    int[] arrayOfInt = new int[2];
    int[] tmp27_25 = arrayOfInt;
    tmp27_25[0] = -1;
    int[] tmp31_27 = tmp27_25;
    tmp31_27[1] = -1;
    tmp31_27;
    getCellId((String[])localObject, arrayOfInt);
    CharSequence localCharSequence = localObject[0];
    localObject = localObject[1];
    int i = arrayOfInt[0];
    int j = arrayOfInt[1];
    if (!TextUtils.isEmpty(localCharSequence)) {
      paramArrayOfInt[0] = Integer.parseInt(localCharSequence);
    }
    if (!TextUtils.isEmpty((CharSequence)localObject)) {
      paramArrayOfInt[1] = Integer.parseInt((String)localObject);
    }
    if (i > -1) {
      paramArrayOfString[0] = Integer.toString(i);
    }
    if (j > -1) {
      paramArrayOfString[1] = Integer.toString(j);
    }
  }
  
  public Map<String, byte[]> getLbsParams(String paramString)
  {
    return null;
  }
  
  public void onGeolocationTask(ValueCallback<Location> paramValueCallback, ValueCallback<Bundle> paramValueCallback1)
  {
    startGeolocationTask(null, paramValueCallback, paramValueCallback1, true);
  }
  
  public void onLocationFailed(int paramInt, String paramString)
  {
    this.mGpsCallBackCount += 1;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onLocationFailed: error:");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append(" ,reason:");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(" ,one=");
    ((StringBuilder)localObject).append(this.mCallbackMapOneShots.size());
    ((StringBuilder)localObject).append(",watcher=");
    ((StringBuilder)localObject).append(this.mCallbackMapWatchers.size());
    ((StringBuilder)localObject).append(" ,timespan:");
    reportEvent("gps", "response", ((StringBuilder)localObject).toString(), System.currentTimeMillis() - this.mGpsStartTime, null);
    for (;;)
    {
      try
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("onLocationFailed: error:");
        ((StringBuilder)localObject).append(paramInt);
        ((StringBuilder)localObject).append(", reason:");
        ((StringBuilder)localObject).append(paramString);
        logDebug("LbsManager", ((StringBuilder)localObject).toString());
        paramString = new Bundle();
        paramString.putInt("errCode", 2);
        paramString.putString("message", this.mStrGeoError);
        int i = 0;
        paramInt = 0;
        if (paramInt < this.mCallbackMapOneShots.size())
        {
          localObject = (LbsCallback)this.mCallbackMapOneShots.get(paramInt);
          if (localObject != null) {
            ((LbsCallback)localObject).onErrorCallback(paramString);
          }
        }
        else
        {
          this.mCallbackMapOneShots.clear();
          paramInt = i;
          if (paramInt < this.mCallbackMapWatchers.size())
          {
            localObject = (LbsCallback)this.mCallbackMapWatchers.get(paramInt);
            if (localObject == null) {
              break label315;
            }
            ((LbsCallback)localObject).onErrorCallback(paramString);
            break label315;
          }
          stopRequestIfNeeded();
          return;
        }
      }
      finally {}
      paramInt += 1;
      continue;
      label315:
      paramInt += 1;
    }
  }
  
  public void onLocationSuccess(Location paramLocation)
  {
    int i = this.mGpsCallBackCount + 1;
    this.mGpsCallBackCount = i;
    Object localObject;
    if (i == 1)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("onLocationSuccess: timespan:,one=");
      ((StringBuilder)localObject).append(this.mCallbackMapOneShots.size());
      ((StringBuilder)localObject).append(",watcher=");
      ((StringBuilder)localObject).append(this.mCallbackMapWatchers.size());
      reportEvent("gps", "response", ((StringBuilder)localObject).toString(), System.currentTimeMillis() - this.mGpsStartTime, null);
    }
    for (;;)
    {
      try
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("onLocationSuccess: Latitude:");
        ((StringBuilder)localObject).append(paramLocation.getLatitude());
        ((StringBuilder)localObject).append(", Longitude:");
        ((StringBuilder)localObject).append(paramLocation.getLongitude());
        ((StringBuilder)localObject).append(", Accuracy:");
        ((StringBuilder)localObject).append(paramLocation.getAccuracy());
        ((StringBuilder)localObject).append(", Altitude:");
        ((StringBuilder)localObject).append(paramLocation.getAltitude());
        ((StringBuilder)localObject).append(", Bearing:");
        ((StringBuilder)localObject).append(paramLocation.getBearing());
        ((StringBuilder)localObject).append(", Speed:");
        ((StringBuilder)localObject).append(paramLocation.getSpeed());
        logDebug("LbsManager", ((StringBuilder)localObject).toString());
        int j = 0;
        i = 0;
        if (i < this.mCallbackMapOneShots.size())
        {
          localObject = (LbsCallback)this.mCallbackMapOneShots.get(i);
          if (localObject != null) {
            ((LbsCallback)localObject).onSuccessCallback(paramLocation);
          }
        }
        else
        {
          this.mCallbackMapOneShots.clear();
          i = j;
          if (i < this.mCallbackMapWatchers.size())
          {
            localObject = (LbsCallback)this.mCallbackMapWatchers.get(i);
            if (localObject == null) {
              break label342;
            }
            ((LbsCallback)localObject).onSuccessCallback(paramLocation);
            break label342;
          }
          this.mLastPostion = paramLocation;
          stopRequestIfNeeded();
          return;
        }
      }
      finally {}
      i += 1;
      continue;
      label342:
      i += 1;
    }
  }
  
  public void onStatusUpdate(String paramString1, int paramInt, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onStatusUpdate: name:");
    localStringBuilder.append(paramString1);
    localStringBuilder.append(",status:");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(",desc:");
    localStringBuilder.append(paramString2);
    logDebug("LbsManager", localStringBuilder.toString());
  }
  
  void reportEvent(String paramString1, String paramString2, String paramString3, long paramLong, Exception paramException)
  {
    if (this.mLogEvent != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("type", paramString1);
      localBundle.putString("action", paramString2);
      localBundle.putString("message", paramString3);
      if (paramLong > 0L) {
        localBundle.putString("time", String.valueOf(paramLong));
      }
      if (paramException != null) {
        localBundle.putString("error", paramException.toString());
      }
    }
  }
  
  public void setLbsDebug(boolean paramBoolean)
  {
    try
    {
      this.mDebugLbs = paramBoolean;
      if (this.mTxLocationManagerProxy != null) {
        this.mTxLocationManagerProxy.setDebug(paramBoolean);
      }
      return;
    }
    finally {}
  }
  
  public void setLogEvent(ILogEvent paramILogEvent)
  {
    this.mLogEvent = paramILogEvent;
  }
  
  public void shutdown()
  {
    logDebug("LbsManager", "shutdown");
    stopUpdating();
  }
  
  public void startGeolocationTask(Object paramObject, ValueCallback<Location> paramValueCallback, ValueCallback<Bundle> paramValueCallback1, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[startGeolocationTask] mTxLocationManagerProxy:");
    localStringBuilder.append(this.mTxLocationManagerProxy);
    localStringBuilder.append(", client:");
    localStringBuilder.append(paramObject);
    localStringBuilder.append(", gpsEnabled:");
    localStringBuilder.append(paramBoolean);
    logDebug("LbsManager", localStringBuilder.toString());
    for (;;)
    {
      try
      {
        if (this.mCallbackMapWatchers.size() > 0)
        {
          i = 1;
          if (paramObject == null) {
            this.mCallbackMapOneShots.add(new LbsCallback(null, paramValueCallback, paramValueCallback1, paramBoolean));
          } else {
            this.mCallbackMapWatchers.add(new LbsCallback(paramObject, paramValueCallback, paramValueCallback1, paramBoolean));
          }
          if (this.mTxLocationManagerProxy == null)
          {
            init();
          }
          else if ((this.mLastPostion != null) && (i != 0))
          {
            logDebug("LbsManager", "[startGeolocationTask] exist watchers: return last postion");
            onLocationSuccess(this.mLastPostion);
          }
          else
          {
            startUpdating();
          }
          return;
        }
      }
      finally {}
      int i = 0;
    }
  }
  
  public void stopGeolocationTask()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[stopRequestLocation] mTxLocationManagerProxy:");
    localStringBuilder.append(this.mTxLocationManagerProxy);
    logDebug("LbsManager", localStringBuilder.toString());
    try
    {
      this.mCallbackMapOneShots.clear();
      stopRequestIfNeeded();
      return;
    }
    finally {}
  }
  
  public void stopRequestLocation(Object paramObject)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[stopRequestLocation] mTxLocationManagerProxy:");
    ((StringBuilder)localObject).append(this.mTxLocationManagerProxy);
    ((StringBuilder)localObject).append(", client:");
    ((StringBuilder)localObject).append(paramObject);
    logDebug("LbsManager", ((StringBuilder)localObject).toString());
    for (;;)
    {
      int i;
      try
      {
        i = this.mCallbackMapWatchers.size() - 1;
        if (i >= 0)
        {
          localObject = (LbsCallback)this.mCallbackMapWatchers.get(i);
          if ((localObject != null) && (paramObject == ((LbsCallback)localObject).getClient())) {
            this.mCallbackMapWatchers.remove(i);
          }
        }
        else
        {
          stopRequestIfNeeded();
          return;
        }
      }
      finally {}
      i -= 1;
    }
  }
  
  public boolean wgs84ToGcj02(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
  {
    ITxLocationManagerProxy localITxLocationManagerProxy = this.mTxLocationManagerProxy;
    if (localITxLocationManagerProxy != null) {
      return localITxLocationManagerProxy.wgs84ToGcj02(paramArrayOfDouble1, paramArrayOfDouble2);
    }
    return false;
  }
  
  public static abstract interface ILogEvent
  {
    public abstract void LbsEvent(Bundle paramBundle);
  }
  
  private class LbsCallback
  {
    private Object mClient = null;
    private ValueCallback<Bundle> mErrorCallback = null;
    private boolean mGpsEnabled = true;
    private ValueCallback<Location> mSuccessCallback = null;
    
    public LbsCallback(ValueCallback<Location> paramValueCallback, ValueCallback<Bundle> paramValueCallback1, boolean paramBoolean)
    {
      this.mClient = paramValueCallback;
      this.mSuccessCallback = paramValueCallback1;
      this.mErrorCallback = paramBoolean;
      boolean bool;
      this.mGpsEnabled = bool;
    }
    
    public Object getClient()
    {
      return this.mClient;
    }
    
    public boolean gpsEnabled()
    {
      return this.mGpsEnabled;
    }
    
    public void onErrorCallback(Bundle paramBundle)
    {
      ValueCallback localValueCallback = this.mErrorCallback;
      if (localValueCallback == null) {
        return;
      }
      try
      {
        localValueCallback.onReceiveValue(paramBundle);
        return;
      }
      catch (Exception paramBundle)
      {
        paramBundle.printStackTrace();
        LbsManager.this.reportEvent("gps", "response", "ErrorCallback error", 0L, paramBundle);
      }
    }
    
    public void onSuccessCallback(Location paramLocation)
    {
      ValueCallback localValueCallback = this.mSuccessCallback;
      if (localValueCallback == null) {
        return;
      }
      try
      {
        localValueCallback.onReceiveValue(paramLocation);
        return;
      }
      catch (Exception paramLocation)
      {
        paramLocation.printStackTrace();
        LbsManager.this.reportEvent("gps", "response", "SuccessCallback error", 0L, paramLocation);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\lbs\LbsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */