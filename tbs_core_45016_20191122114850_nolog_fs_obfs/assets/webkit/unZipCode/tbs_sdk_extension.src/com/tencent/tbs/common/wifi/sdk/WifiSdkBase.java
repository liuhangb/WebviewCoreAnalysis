package com.tencent.tbs.common.wifi.sdk;

import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.wifi.WifiApInfo;
import com.tencent.tbs.common.wifi.WifiConnector;
import com.tencent.tbs.common.wifi.WifiConnector.ConnectEvent;
import com.tencent.tbs.common.wifi.WifiEngine;
import com.tencent.tbs.common.wifi.WifiWupRequester.WifiKeyQureyRspInfo;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine.WiFiConnectState;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine.WiFiConnectStateChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class WifiSdkBase
  implements Handler.Callback, WifiConnector.ConnectEvent, WiFiConnectStateMachine.WiFiConnectStateChangeListener
{
  public static final int CONN_FREE_WIFI_RESULT_ALREADY_CONNECTED = -4;
  public static final int CONN_FREE_WIFI_RESULT_ARG_ILLEGAL = -2;
  public static final int CONN_FREE_WIFI_RESULT_FAIL = -1;
  public static final int CONN_FREE_WIFI_RESULT_NOT_FREE_WIFI = -3;
  public static final int CONN_FREE_WIFI_RESULT_SUCC = 0;
  public static final int CONN_FREE_WIFI_RESULT_WIFI_DISABLED = -5;
  public static final int ID_QB = 2;
  public static final int ID_TMS = 1;
  public static final String KEY_BSSID = "key2";
  public static final String KEY_PWD_MD5 = "key4";
  public static final String KEY_RESULT = "key6";
  public static final String KEY_SSID = "key1";
  public static final String KEY_TARGET_SDK = "key5";
  public static final String KEY_TYPE = "key3";
  public static final String KEY_TYPE_NAME = "type";
  private static int MAX_CACHE_SIZE = 1000;
  private static final String TAG = "WifiSdkBase";
  String DEBUG_ACTION = "MTT_DEV_DEBUG_ACTION";
  protected Runnable mAutoConnectTask = null;
  protected WifiApInfo mAutoConnectWifiInfo = null;
  long mAvailableTime = 10800000L;
  protected WifiApInfo mConnecttingApInfo = null;
  WifiConnector mCurWifiConnector = null;
  public boolean mIsForceUse = false;
  public int mMaxRetryTimes = 5;
  protected CacheHashMap mPasswordCache = new CacheHashMap();
  protected PwdComparator mPwdComparator = new PwdComparator();
  protected int mPwdIndex = 0;
  protected boolean mRetryMode = false;
  protected ArrayList<WifiWupRequester.WifiKeyQureyRspInfo> mRetryPwdList = new ArrayList();
  protected int mSafeType = 0;
  ConcurrentLinkedQueue<ISdkCallback> mSdkCallbacks = new ConcurrentLinkedQueue();
  protected WifiApInfo mTargetAp = null;
  public int mTimeOut = 6000;
  int mTryIndex = -1;
  Handler mWorkerHandler = null;
  protected ConcurrentLinkedQueue<String> mWrongPwdList = new ConcurrentLinkedQueue();
  
  public WifiSdkBase(Looper paramLooper)
  {
    WiFiConnectStateMachine.getInstance().addListener(this);
  }
  
  public void addCallback(ISdkCallback paramISdkCallback)
  {
    if (!this.mSdkCallbacks.contains(paramISdkCallback)) {
      this.mSdkCallbacks.add(paramISdkCallback);
    }
  }
  
  protected RqdAndRspInfo checkCache(List<ScanResult> paramList, boolean paramBoolean)
  {
    if (paramList != null)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("checkCache() scanList: ");
      ((StringBuilder)localObject1).append(paramList.size());
      ((StringBuilder)localObject1).append(" ");
      ((StringBuilder)localObject1).append(paramList);
      LogUtils.d("cacheOpt-WifiSdkBase", ((StringBuilder)localObject1).toString());
    }
    else
    {
      LogUtils.d("cacheOpt-WifiSdkBase", "checkCache() scanList: null");
    }
    Object localObject1 = new RqdAndRspInfo();
    ((RqdAndRspInfo)localObject1).mReqFromUI = new ArrayList(paramList);
    if (!paramBoolean)
    {
      Object localObject4 = new ArrayList();
      ArrayList localArrayList = new ArrayList();
      Object localObject2 = new ArrayList();
      Object localObject3 = new ArrayList();
      HashMap localHashMap = new HashMap();
      HashSet localHashSet = new HashSet();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        ScanResult localScanResult = (ScanResult)localIterator.next();
        if (localScanResult != null)
        {
          Cache localCache = this.mPasswordCache.get(localScanResult.BSSID);
          if (localCache != null)
          {
            localArrayList.add(localScanResult);
            if (localCache.mIsDirty) {
              ((List)localObject2).add(localScanResult);
            }
            Object localObject5 = (String)localHashMap.get(localScanResult.SSID);
            Object localObject6;
            if (TextUtils.isEmpty((CharSequence)localObject5))
            {
              localObject5 = localScanResult.SSID;
              localObject6 = new StringBuilder();
              ((StringBuilder)localObject6).append("");
              ((StringBuilder)localObject6).append(localCache.updateTime);
              localHashMap.put(localObject5, ((StringBuilder)localObject6).toString());
              ((List)localObject3).add(localScanResult);
              localObject5 = new StringBuilder();
              ((StringBuilder)localObject5).append("checkCache() bridgeList.add: ");
              ((StringBuilder)localObject5).append(localScanResult.SSID);
              ((StringBuilder)localObject5).append("_");
              ((StringBuilder)localObject5).append(localCache.updateTime);
              LogUtils.d("cacheOpt-WifiSdkBase", ((StringBuilder)localObject5).toString());
            }
            else
            {
              localObject6 = new StringBuilder();
              ((StringBuilder)localObject6).append("");
              ((StringBuilder)localObject6).append(localCache.updateTime);
              if (!((String)localObject5).contains(((StringBuilder)localObject6).toString()))
              {
                localObject6 = localScanResult.SSID;
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append((String)localObject5);
                localStringBuilder.append("_");
                localStringBuilder.append(localCache.updateTime);
                localHashMap.put(localObject6, localStringBuilder.toString());
                ((List)localObject3).add(localScanResult);
                localObject5 = new StringBuilder();
                ((StringBuilder)localObject5).append("checkCache() bridgeList.add: ");
                ((StringBuilder)localObject5).append(localScanResult.SSID);
                ((StringBuilder)localObject5).append("_");
                ((StringBuilder)localObject5).append(localCache.updateTime);
                LogUtils.d("cacheOpt-WifiSdkBase", ((StringBuilder)localObject5).toString());
              }
            }
          }
          else
          {
            ((List)localObject4).add(localScanResult);
            localHashSet.add(localScanResult.SSID);
          }
        }
      }
      if (localArrayList.isEmpty())
      {
        LogUtils.d("cacheOpt-WifiSdkBase", "checkCache() none hit, req all");
        ((RqdAndRspInfo)localObject1).mReqToServer = new ArrayList(paramList);
        return (RqdAndRspInfo)localObject1;
      }
      paramList = new StringBuilder();
      paramList.append("checkCache() hitList: ");
      paramList.append(localArrayList.size());
      paramList.append(" ");
      paramList.append(localArrayList);
      LogUtils.d("cacheOpt-WifiSdkBase", paramList.toString());
      paramList = new StringBuilder();
      paramList.append("checkCache() dirtyList: ");
      paramList.append(((List)localObject2).size());
      paramList.append(" ");
      paramList.append(localObject2);
      LogUtils.d("cacheOpt-WifiSdkBase", paramList.toString());
      if (((List)localObject4).isEmpty())
      {
        paramList = new StringBuilder();
        paramList.append("checkCache() all hit, req hit aps whitch are dirty: ");
        paramList.append(localObject2);
        LogUtils.d("cacheOpt-WifiSdkBase", paramList.toString());
        ((RqdAndRspInfo)localObject1).mReqToServer = new ArrayList((Collection)localObject2);
        return (RqdAndRspInfo)localObject1;
      }
      paramList = new StringBuilder();
      paramList.append("checkCache() bridgeList: ");
      paramList.append(((List)localObject3).size());
      paramList.append(" ");
      paramList.append(localObject3);
      LogUtils.d("cacheOpt-WifiSdkBase", paramList.toString());
      paramList = ((List)localObject3).iterator();
      while (paramList.hasNext()) {
        if (!localHashSet.contains(((ScanResult)paramList.next()).SSID)) {
          paramList.remove();
        }
      }
      paramList = new StringBuilder();
      paramList.append("checkCache() trimed bridgeList: ");
      paramList.append(((List)localObject3).size());
      paramList.append(" ");
      paramList.append(localObject3);
      LogUtils.d("cacheOpt-WifiSdkBase", paramList.toString());
      paramList = new StringBuilder();
      paramList.append("checkCache() deltaList: ");
      paramList.append(((List)localObject4).size());
      paramList.append(" ");
      paramList.append(localObject4);
      LogUtils.d("cacheOpt-WifiSdkBase", paramList.toString());
      paramList = new ArrayList((Collection)localObject4);
      if (((List)localObject3).isEmpty())
      {
        localObject2 = ((List)localObject2).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (ScanResult)((Iterator)localObject2).next();
          if (!paramList.contains(localObject3)) {
            paramList.add(localObject3);
          }
        }
        ((RqdAndRspInfo)localObject1).mReqToServer = paramList;
      }
      else
      {
        localObject3 = ((List)localObject3).iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject4 = (ScanResult)((Iterator)localObject3).next();
          if (!paramList.contains(localObject4)) {
            paramList.add(localObject4);
          }
        }
        localObject2 = ((List)localObject2).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (ScanResult)((Iterator)localObject2).next();
          if (!paramList.contains(localObject3)) {
            paramList.add(localObject3);
          }
        }
        ((RqdAndRspInfo)localObject1).mReqToServer = paramList;
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("checkCache() reqList: ");
      ((StringBuilder)localObject2).append(paramList.size());
      ((StringBuilder)localObject2).append(" ");
      ((StringBuilder)localObject2).append(paramList);
      LogUtils.d("cacheOpt-WifiSdkBase", ((StringBuilder)localObject2).toString());
      return (RqdAndRspInfo)localObject1;
    }
    ((RqdAndRspInfo)localObject1).mReqToServer = new ArrayList(paramList);
    return (RqdAndRspInfo)localObject1;
  }
  
  public abstract int connect(WifiApInfo paramWifiApInfo);
  
  public void doConnect(String paramString)
  {
    WifiApInfo localWifiApInfo = this.mTargetAp;
    if ((localWifiApInfo != null) && (TextUtils.equals(localWifiApInfo.mSsid, paramString))) {
      BrowserExecutorSupplier.postForTimeoutTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          WifiSdkBase localWifiSdkBase = WifiSdkBase.this;
          if ((localWifiSdkBase.connect(localWifiSdkBase.mTargetAp) != 0) && (WifiSdkBase.this.mCurWifiConnector != null)) {
            WifiSdkBase.this.mCurWifiConnector.doConnectFail();
          }
        }
      });
    }
  }
  
  public boolean enterRetryMod(ArrayList<WifiApInfo> paramArrayList)
  {
    this.mWrongPwdList.clear();
    HashSet localHashSet = new HashSet();
    CacheHashMap localCacheHashMap = new CacheHashMap();
    localCacheHashMap.putAll(this.mPasswordCache);
    this.mRetryPwdList.clear();
    paramArrayList = paramArrayList.iterator();
    while (paramArrayList.hasNext())
    {
      Object localObject = (WifiApInfo)paramArrayList.next();
      this.mSafeType = ((WifiApInfo)localObject).mSafeType;
      localObject = localCacheHashMap.get(((WifiApInfo)localObject).mBssid);
      if ((localObject != null) && (((Cache)localObject).mPasswordList != null) && (!((Cache)localObject).mPasswordList.isEmpty()))
      {
        localObject = ((Cache)localObject).mPasswordList.iterator();
        while (((Iterator)localObject).hasNext())
        {
          WifiWupRequester.WifiKeyQureyRspInfo localWifiKeyQureyRspInfo = (WifiWupRequester.WifiKeyQureyRspInfo)((Iterator)localObject).next();
          if (((localWifiKeyQureyRspInfo.force) || (!localHashSet.contains(localWifiKeyQureyRspInfo.pwd))) && (localWifiKeyQureyRspInfo.hasPwd))
          {
            this.mRetryPwdList.add(localWifiKeyQureyRspInfo);
            localHashSet.add(localWifiKeyQureyRspInfo.pwd);
            if (localWifiKeyQureyRspInfo.force) {
              this.mIsForceUse = true;
            }
          }
        }
      }
    }
    if (this.mRetryPwdList.size() > 0)
    {
      this.mPwdIndex = 0;
      Collections.sort(this.mRetryPwdList, this.mPwdComparator);
      this.mRetryMode = true;
    }
    else
    {
      this.mRetryMode = false;
    }
    this.mTryIndex = -1;
    return this.mRetryMode;
  }
  
  public void exitRetryMod(int paramInt)
  {
    this.mWrongPwdList.clear();
    this.mRetryPwdList.clear();
    this.mRetryMode = false;
    this.mIsForceUse = false;
    this.mSafeType = 0;
    this.mPwdIndex = 0;
    this.mTryIndex = -1;
    stopConnecting(paramInt);
  }
  
  protected WifiApInfo formatRspInfo2WifiApInfo(WifiWupRequester.WifiKeyQureyRspInfo paramWifiKeyQureyRspInfo)
  {
    WifiApInfo localWifiApInfo = new WifiApInfo();
    localWifiApInfo.mBssid = paramWifiKeyQureyRspInfo.bssid;
    localWifiApInfo.mSsid = paramWifiKeyQureyRspInfo.ssid;
    localWifiApInfo.mSdkId = getId();
    localWifiApInfo.mActionType = paramWifiKeyQureyRspInfo.acitonType;
    localWifiApInfo.mBrand = paramWifiKeyQureyRspInfo.brand;
    localWifiApInfo.mPortal = paramWifiKeyQureyRspInfo.portal;
    localWifiApInfo.mWeixinSchama = paramWifiKeyQureyRspInfo.weixinSchema;
    localWifiApInfo.mScore = paramWifiKeyQureyRspInfo.score;
    if ((!TextUtils.isEmpty(paramWifiKeyQureyRspInfo.pwd)) || (paramWifiKeyQureyRspInfo.hasPwd)) {
      localWifiApInfo.mHasCloudPassword = true;
    }
    return localWifiApInfo;
  }
  
  protected ArrayList<WifiApInfo> formatRspInfo2WifiApInfo(List<WifiWupRequester.WifiKeyQureyRspInfo> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramList != null)
    {
      paramList = paramList.iterator();
      while (paramList.hasNext()) {
        localArrayList.add(formatRspInfo2WifiApInfo((WifiWupRequester.WifiKeyQureyRspInfo)paramList.next()));
      }
    }
    return localArrayList;
  }
  
  public WifiApInfo getConnectedInfo()
  {
    return this.mConnecttingApInfo;
  }
  
  public abstract int getId();
  
  public int getScore(String paramString1, String paramString2)
  {
    LogUtils.d("WifiSdkBase", "isPwdIn");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ssid : ");
    localStringBuilder.append(paramString1);
    LogUtils.d("WifiSdkBase", localStringBuilder.toString());
    paramString1 = new StringBuilder();
    paramString1.append("bssid : ");
    paramString1.append(paramString2);
    LogUtils.d("WifiSdkBase", paramString1.toString());
    paramString1 = this.mPasswordCache.get(paramString2);
    int k = 0;
    int i = 0;
    int j = k;
    if (paramString1 != null)
    {
      paramString1 = paramString1.mPasswordList;
      j = k;
      if (paramString1 != null)
      {
        paramString1 = new ArrayList(paramString1).iterator();
        for (;;)
        {
          j = i;
          if (!paramString1.hasNext()) {
            break;
          }
          paramString2 = (WifiWupRequester.WifiKeyQureyRspInfo)paramString1.next();
          if ((paramString2 != null) && (paramString2.score > i)) {
            i = paramString2.score;
          }
        }
      }
    }
    return j;
  }
  
  public int getTryCounts()
  {
    if (this.mRetryMode) {
      return this.mRetryPwdList.size();
    }
    return 0;
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    return false;
  }
  
  public boolean isForceUse(WifiApInfo paramWifiApInfo)
  {
    return false;
  }
  
  public boolean isInRetryMod()
  {
    return this.mRetryMode;
  }
  
  public boolean isPwdIn(String paramString1, String paramString2)
  {
    Object localObject = this.mPasswordCache.get(paramString2);
    if (localObject != null)
    {
      localObject = ((Cache)localObject).mPasswordList;
      if (localObject != null)
      {
        localObject = new ArrayList((Collection)localObject).iterator();
        while (((Iterator)localObject).hasNext())
        {
          WifiWupRequester.WifiKeyQureyRspInfo localWifiKeyQureyRspInfo = (WifiWupRequester.WifiKeyQureyRspInfo)((Iterator)localObject).next();
          if ((localWifiKeyQureyRspInfo != null) && (localWifiKeyQureyRspInfo.hasPwd) && (TextUtils.equals(paramString1, localWifiKeyQureyRspInfo.ssid)))
          {
            bool = true;
            break label95;
          }
        }
      }
    }
    boolean bool = false;
    label95:
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("isPwdIn() ssid: ");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", bssid");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(", hasPwd: ");
    ((StringBuilder)localObject).append(bool);
    LogUtils.d("WifiSdkBase", ((StringBuilder)localObject).toString());
    return bool;
  }
  
  public boolean needPullPwd(ArrayList<WifiApInfo> paramArrayList)
  {
    return false;
  }
  
  protected void notifyPwdGetted(List<ScanResult> paramList, int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    LogUtils.d("WifiSdkBase", "load data from cache");
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Object localObject = (ScanResult)paramList.next();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("scanResult : ");
      localStringBuilder.append(localObject);
      LogUtils.d("WifiSdkBase", localStringBuilder.toString());
      localObject = this.mPasswordCache.get(((ScanResult)localObject).BSSID);
      if ((localObject != null) && (((Cache)localObject).mPasswordList != null) && (!((Cache)localObject).mPasswordList.isEmpty()))
      {
        localArrayList.addAll(((Cache)localObject).mPasswordList);
        LogUtils.d("WifiSdkBase", "has pwd");
      }
    }
    paramList = this.mSdkCallbacks;
    if (paramList != null)
    {
      paramList = paramList.iterator();
      while (paramList.hasNext()) {
        ((ISdkCallback)paramList.next()).onRequestCompleted(formatRspInfo2WifiApInfo(localArrayList), getId(), paramInt);
      }
    }
  }
  
  public void onConnected()
  {
    Object localObject = this.mSdkCallbacks;
    if ((localObject != null) && (this.mTargetAp != null))
    {
      localObject = ((ConcurrentLinkedQueue)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((ISdkCallback)((Iterator)localObject).next()).onConnectCompleted(this.mTargetAp.mSsid, 0, getId());
      }
    }
  }
  
  public void onConnectedFailed(byte paramByte)
  {
    Object localObject = this.mSdkCallbacks;
    if ((localObject != null) && (this.mTargetAp != null))
    {
      localObject = ((ConcurrentLinkedQueue)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((ISdkCallback)((Iterator)localObject).next()).onConnectCompleted(this.mTargetAp.mSsid, -1, getId());
      }
    }
  }
  
  public void onPwdError(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[onPwdError] ssid:");
    localStringBuilder.append(paramString);
    LogUtils.d("WifiSdkBase", localStringBuilder.toString());
  }
  
  public void onStateChange(WiFiConnectStateMachine.WiFiConnectState paramWiFiConnectState)
  {
    final WifiApInfo localWifiApInfo;
    switch (paramWiFiConnectState.getIntStateType())
    {
    default: 
      
    case 6: 
      localWifiApInfo = this.mConnecttingApInfo;
      if ((localWifiApInfo != null) && (TextUtils.equals(paramWiFiConnectState.getSsid(), localWifiApInfo.mSsid)))
      {
        paramWiFiConnectState = new StringBuilder();
        paramWiFiConnectState.append("DISCONNECT_BY_USER[");
        paramWiFiConnectState.append(localWifiApInfo.mSsid);
        paramWiFiConnectState.append("][");
        paramWiFiConnectState.append(getId());
        paramWiFiConnectState.append("]");
        LogUtils.d("WifiSdkBase", paramWiFiConnectState.toString());
        this.mConnecttingApInfo = null;
        return;
      }
      break;
    case 3: 
      localWifiApInfo = this.mConnecttingApInfo;
      if ((localWifiApInfo != null) && (TextUtils.equals(paramWiFiConnectState.getSsid(), localWifiApInfo.mSsid)))
      {
        paramWiFiConnectState = new StringBuilder();
        paramWiFiConnectState.append("DISCONNECTED[");
        paramWiFiConnectState.append(localWifiApInfo.mSsid);
        paramWiFiConnectState.append("][");
        paramWiFiConnectState.append(getId());
        paramWiFiConnectState.append("]");
        LogUtils.d("WifiSdkBase", paramWiFiConnectState.toString());
        this.mConnecttingApInfo = null;
        BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
        {
          public void doRun()
          {
            WifiEngine.getInstance().forgetAP(localWifiApInfo, false);
          }
        });
        return;
      }
      break;
    case 2: 
      localWifiApInfo = this.mConnecttingApInfo;
      ContextHolder.getAppContext();
      if ((localWifiApInfo != null) && (TextUtils.equals(paramWiFiConnectState.getSsid(), localWifiApInfo.mSsid)))
      {
        paramWiFiConnectState = new StringBuilder();
        paramWiFiConnectState.append("CONNCETED[");
        paramWiFiConnectState.append(localWifiApInfo.mSsid);
        paramWiFiConnectState.append("][");
        paramWiFiConnectState.append(getId());
        paramWiFiConnectState.append("]");
        LogUtils.d("WifiSdkBase", paramWiFiConnectState.toString());
        this.mConnecttingApInfo = null;
        LogUtils.d("WifiSdkBase", "pwd VALID");
        paramWiFiConnectState = new StringBuilder();
        paramWiFiConnectState.append("copyInfo : ");
        paramWiFiConnectState.append(localWifiApInfo.toString());
        LogUtils.d("WifiSdkBase", paramWiFiConnectState.toString());
        return;
      }
      break;
    case 0: 
    case 1: 
    case 4: 
    case 5: 
    case 7: 
      localWifiApInfo = this.mConnecttingApInfo;
      if ((localWifiApInfo != null) && (!TextUtils.equals(paramWiFiConnectState.getSsid(), localWifiApInfo.mSsid)))
      {
        this.mConnecttingApInfo = null;
        if (getId() != 1) {
          return;
        }
        if (this.mTryIndex == 0)
        {
          paramWiFiConnectState = new StringBuilder();
          paramWiFiConnectState.append("SSID : ");
          paramWiFiConnectState.append(localWifiApInfo.mBssid);
          paramWiFiConnectState.append(" | TMS ap切换");
          LogUtils.d("Wifi-PV", paramWiFiConnectState.toString());
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D_TMS_ER_C_3");
        }
      }
      break;
    }
  }
  
  public void pullPwd(ArrayList<WifiApInfo> paramArrayList) {}
  
  public void removeCallback(ISdkCallback paramISdkCallback)
  {
    if (this.mSdkCallbacks.contains(paramISdkCallback)) {
      this.mSdkCallbacks.remove(paramISdkCallback);
    }
  }
  
  public abstract void requestCloudWifi(List<ScanResult> paramList, boolean paramBoolean, int paramInt);
  
  public void stopConnecting(int paramInt)
  {
    WifiConnector localWifiConnector = this.mCurWifiConnector;
    if (localWifiConnector != null) {
      localWifiConnector.stop(paramInt);
    }
  }
  
  protected ArrayList<Cache> trimCache(List<ScanResult> paramList)
  {
    HashSet localHashSet = new HashSet();
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localHashSet.add(((ScanResult)paramList.next()).BSSID);
    }
    long l = System.currentTimeMillis();
    Object localObject1 = new HashSet();
    paramList = this.mPasswordCache.entrySet().iterator();
    Object localObject3;
    Object localObject2;
    while (paramList.hasNext())
    {
      localObject3 = (Map.Entry)paramList.next();
      localObject2 = (Cache)((Map.Entry)localObject3).getValue();
      if (l - ((Cache)localObject2).updateTime > this.mAvailableTime + 60000L)
      {
        String str = (String)((Map.Entry)localObject3).getKey();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append((String)((Map.Entry)localObject3).getKey());
        localStringBuilder.append("  out of time remove it!");
        LogUtils.d("WifiSdkBase", localStringBuilder.toString());
        if (localHashSet.contains(str))
        {
          ((Cache)localObject2).mIsDirty = true;
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("MARK Dirty SSID: ");
          ((StringBuilder)localObject3).append(((Cache)localObject2).mSsid);
          ((StringBuilder)localObject3).append(" bssid : ");
          ((StringBuilder)localObject3).append(((Cache)localObject2).mBssid);
          LogUtils.d("WifiSdkBase", ((StringBuilder)localObject3).toString());
        }
        else
        {
          ((HashSet)localObject1).add(str);
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("remove SSID: ");
          ((StringBuilder)localObject3).append(((Cache)localObject2).mSsid);
          ((StringBuilder)localObject3).append(" bssid : ");
          ((StringBuilder)localObject3).append(((Cache)localObject2).mBssid);
          LogUtils.d("WifiSdkBase", ((StringBuilder)localObject3).toString());
        }
      }
    }
    paramList = new ArrayList();
    localObject1 = ((HashSet)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (String)((Iterator)localObject1).next();
      localObject2 = (Cache)this.mPasswordCache.remove(localObject2);
      if (localObject2 != null) {
        paramList.add(localObject2);
      }
    }
    if (this.mPasswordCache.size() > MAX_CACHE_SIZE)
    {
      LogUtils.d("WifiSdkBase", "out of size!");
      localObject1 = new ArrayList(this.mPasswordCache.values());
      Collections.sort((List)localObject1, new CacheTimeComparator());
      int i = ((ArrayList)localObject1).size() - 1;
      while ((i >= 0) && (this.mPasswordCache.size() > MAX_CACHE_SIZE))
      {
        localObject2 = ((Cache)((ArrayList)localObject1).get(i)).mBssid;
        if (localHashSet.contains(localObject2))
        {
          localObject2 = this.mPasswordCache.get(localObject2);
          if (localObject2 != null)
          {
            ((Cache)localObject2).mIsDirty = true;
            localObject3 = new StringBuilder();
            ((StringBuilder)localObject3).append("MARK Dirty SSID: ");
            ((StringBuilder)localObject3).append(((Cache)localObject2).mSsid);
            ((StringBuilder)localObject3).append(" bssid : ");
            ((StringBuilder)localObject3).append(((Cache)localObject2).mBssid);
            LogUtils.d("WifiSdkBase", ((StringBuilder)localObject3).toString());
          }
        }
        else
        {
          localObject2 = (Cache)this.mPasswordCache.remove(localObject2);
          if (localObject2 != null)
          {
            paramList.add(localObject2);
            localObject3 = new StringBuilder();
            ((StringBuilder)localObject3).append("remove SSID: ");
            ((StringBuilder)localObject3).append(((Cache)localObject2).mSsid);
            ((StringBuilder)localObject3).append(" bssid : ");
            ((StringBuilder)localObject3).append(((Cache)localObject2).mBssid);
            LogUtils.d("WifiSdkBase", ((StringBuilder)localObject3).toString());
          }
        }
        i -= 1;
      }
    }
    return paramList;
  }
  
  public void tryPwd(int paramInt)
  {
    this.mTryIndex = paramInt;
    WifiApInfo localWifiApInfo1 = new WifiApInfo();
    if (!this.mRetryPwdList.isEmpty())
    {
      localObject = (WifiWupRequester.WifiKeyQureyRspInfo)this.mRetryPwdList.get(0);
      localWifiApInfo1.mSsid = ((WifiWupRequester.WifiKeyQureyRspInfo)localObject).ssid;
      localWifiApInfo1.mBssid = ((WifiWupRequester.WifiKeyQureyRspInfo)localObject).bssid;
      localWifiApInfo1.mPassword = ((WifiWupRequester.WifiKeyQureyRspInfo)localObject).pwd;
      localWifiApInfo1.mSafeType = this.mSafeType;
    }
    this.mTargetAp = localWifiApInfo1;
    this.mConnecttingApInfo = this.mTargetAp;
    if (getId() != 2)
    {
      localObject = "";
    }
    else
    {
      if (this.mTryIndex == 0)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("SSID : ");
        ((StringBuilder)localObject).append(localWifiApInfo1.mSsid);
        ((StringBuilder)localObject).append(" | QB 连接");
        LogUtils.d("Wifi-PV", ((StringBuilder)localObject).toString());
      }
      localObject = "qb";
    }
    paramInt = -1;
    try
    {
      WifiApInfo localWifiApInfo2 = WifiEngine.getInstance().getApInfo(localWifiApInfo1.mSsid);
      if (localWifiApInfo2 != null) {
        paramInt = localWifiApInfo2.mLevel;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      paramInt = -1;
    }
    this.mCurWifiConnector = new WifiConnector(localWifiApInfo1.mSsid, this, (String)localObject, paramInt, this.mTryIndex);
    Object localObject = this.mCurWifiConnector;
    ((WifiConnector)localObject).mMaxRetryTimes = this.mMaxRetryTimes;
    ((WifiConnector)localObject).mTimeOut = this.mTimeOut;
    ((WifiConnector)localObject).mPwdCount = this.mRetryPwdList.size();
    this.mCurWifiConnector.start();
  }
  
  protected void updateCache(ArrayList<WifiWupRequester.WifiKeyQureyRspInfo> paramArrayList, List<ScanResult> paramList)
  {
    if (paramList != null)
    {
      if (paramList.isEmpty()) {
        return;
      }
      long l = System.currentTimeMillis();
      Object localObject2 = paramList.iterator();
      Object localObject1;
      while (((Iterator)localObject2).hasNext())
      {
        ScanResult localScanResult = (ScanResult)((Iterator)localObject2).next();
        paramList = new StringBuilder();
        paramList.append("scanResult : ");
        paramList.append(localScanResult);
        LogUtils.d("WifiSdkBase", paramList.toString());
        localObject1 = this.mPasswordCache.get(localScanResult.BSSID);
        paramList = (List<ScanResult>)localObject1;
        if (localObject1 == null)
        {
          paramList = new Cache();
          paramList.mSsid = localScanResult.SSID;
          paramList.mBssid = localScanResult.BSSID;
          this.mPasswordCache.put(localScanResult.BSSID, paramList);
        }
        paramList.mIsDirty = false;
        if (paramList.mPasswordList != null) {
          paramList.mPasswordList.clear();
        }
        paramList.updateTime = l;
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("updateCache() update timestamp ssid: ");
        ((StringBuilder)localObject1).append(paramList.mSsid);
        ((StringBuilder)localObject1).append(" bssid : ");
        ((StringBuilder)localObject1).append(paramList.mBssid);
        ((StringBuilder)localObject1).append(" t :");
        ((StringBuilder)localObject1).append(l);
        LogUtils.d("WifiSdkBase", ((StringBuilder)localObject1).toString());
      }
      if ((paramArrayList != null) && (!paramArrayList.isEmpty()))
      {
        localObject1 = paramArrayList.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (WifiWupRequester.WifiKeyQureyRspInfo)((Iterator)localObject1).next();
          paramArrayList = new StringBuilder();
          paramArrayList.append("ssid : ");
          paramArrayList.append(((WifiWupRequester.WifiKeyQureyRspInfo)localObject2).ssid);
          LogUtils.d("WifiSdkBase", paramArrayList.toString());
          paramList = this.mPasswordCache.get(((WifiWupRequester.WifiKeyQureyRspInfo)localObject2).bssid);
          paramArrayList = paramList;
          if (paramList == null)
          {
            LogUtils.d("WifiSdkBase", "impossable !!!");
            paramArrayList = new Cache();
            paramArrayList.mSsid = ((WifiWupRequester.WifiKeyQureyRspInfo)localObject2).ssid;
            paramArrayList.mBssid = ((WifiWupRequester.WifiKeyQureyRspInfo)localObject2).bssid;
            paramArrayList.updateTime = l;
            this.mPasswordCache.put(((WifiWupRequester.WifiKeyQureyRspInfo)localObject2).bssid, paramArrayList);
          }
          paramArrayList.mIsDirty = false;
          if (paramArrayList.mPasswordList == null) {
            paramArrayList.mPasswordList = new ArrayList();
          }
          paramList = new StringBuilder();
          paramList.append("add keyInfo : ");
          paramList.append(localObject2);
          LogUtils.d("WifiSdkBase", paramList.toString());
          paramArrayList.mPasswordList.add(localObject2);
          paramList = new StringBuilder();
          paramList.append("update pwd SSID: ");
          paramList.append(paramArrayList.mSsid);
          paramList.append(" bssid : ");
          paramList.append(paramArrayList.mBssid);
          LogUtils.d("WifiSdkBase", paramList.toString());
        }
      }
      return;
    }
  }
  
  protected class Cache
  {
    public String mBssid = "";
    boolean mIsDirty = false;
    public ArrayList<WifiWupRequester.WifiKeyQureyRspInfo> mPasswordList = new ArrayList();
    public String mSsid = "";
    public long updateTime = 0L;
    
    protected Cache() {}
  }
  
  protected class CacheHashMap
    extends ConcurrentHashMap<String, WifiSdkBase.Cache>
  {
    protected CacheHashMap() {}
    
    public WifiSdkBase.Cache get(Object paramObject)
    {
      if ((paramObject instanceof String)) {
        return (WifiSdkBase.Cache)super.get(((String)paramObject).toLowerCase(Locale.ENGLISH));
      }
      return (WifiSdkBase.Cache)super.get(paramObject);
    }
    
    public WifiSdkBase.Cache put(String paramString, WifiSdkBase.Cache paramCache)
    {
      if ((paramString instanceof String)) {
        return (WifiSdkBase.Cache)super.put(paramString.toLowerCase(Locale.ENGLISH), paramCache);
      }
      return (WifiSdkBase.Cache)super.put(paramString, paramCache);
    }
  }
  
  public class CacheTimeComparator
    implements Comparator<WifiSdkBase.Cache>
  {
    public CacheTimeComparator() {}
    
    public int compare(WifiSdkBase.Cache paramCache1, WifiSdkBase.Cache paramCache2)
    {
      if (paramCache1 == paramCache2) {
        return 0;
      }
      if (paramCache1 == null) {
        return 1;
      }
      if (paramCache2 == null) {
        return -1;
      }
      return (int)(paramCache1.updateTime - paramCache2.updateTime);
    }
  }
  
  public class LevelComparator
    implements Comparator<ScanResult>
  {
    public LevelComparator() {}
    
    public int compare(ScanResult paramScanResult1, ScanResult paramScanResult2)
    {
      if (paramScanResult1 == paramScanResult2) {
        return 0;
      }
      if (paramScanResult1 == null) {
        return 1;
      }
      if (paramScanResult2 == null) {
        return -1;
      }
      return paramScanResult2.level - paramScanResult1.level;
    }
  }
  
  public class PwdComparator
    implements Comparator<WifiWupRequester.WifiKeyQureyRspInfo>
  {
    public PwdComparator() {}
    
    public int compare(WifiWupRequester.WifiKeyQureyRspInfo paramWifiKeyQureyRspInfo1, WifiWupRequester.WifiKeyQureyRspInfo paramWifiKeyQureyRspInfo2)
    {
      if (paramWifiKeyQureyRspInfo1 == paramWifiKeyQureyRspInfo2) {
        return 0;
      }
      if (paramWifiKeyQureyRspInfo1 == null) {
        return 1;
      }
      if (paramWifiKeyQureyRspInfo2 == null) {
        return -1;
      }
      if (paramWifiKeyQureyRspInfo1.force == paramWifiKeyQureyRspInfo2.force) {
        return 0;
      }
      if (paramWifiKeyQureyRspInfo1.force) {
        return -1;
      }
      if (paramWifiKeyQureyRspInfo2.force) {
        return 1;
      }
      return 0;
    }
  }
  
  protected class RqdAndRspInfo
  {
    public ArrayList<WifiApInfo> mCheckRsp = null;
    public List<ScanResult> mReqFromUI = null;
    public int mReqNum = 0;
    public List<ScanResult> mReqToServer = null;
    public ArrayList<WifiWupRequester.WifiKeyQureyRspInfo> mRsp = null;
    
    protected RqdAndRspInfo() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\sdk\WifiSdkBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */