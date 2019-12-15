package com.tencent.tbs.common.wifi;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.common.http.Apn;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.baseinfo.TBSBroadcastReceiver;
import com.tencent.tbs.common.observer.AppBroadcastObserver;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.wifi.data.WifiBrandInfoHelper;
import com.tencent.tbs.common.wifi.sdk.ISdkCallback;
import com.tencent.tbs.common.wifi.sdk.QBSdk;
import com.tencent.tbs.common.wifi.sdk.WifiSdkBase;
import com.tencent.tbs.common.wifi.state.NewWifiDetector;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine.WiFiConnectState;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine.WiFiConnectStateChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import org.json.JSONObject;

public class WifiEngine
  implements Handler.Callback, AppBroadcastObserver, ISdkCallback, WiFiConnectStateMachine.WiFiConnectStateChangeListener
{
  public static final int DO_CONNECT_FAILED = 1;
  public static final int DO_CONNECT_SUCCESS = 0;
  static final String MAP_KEY_APINFO_LIST = "apinfo_list";
  static final String MAP_KEY_LISTENERS = "listeners";
  static final String MAP_KEY_SSID_SET = "ssid_set";
  static final String MAP_KEY_STATE = "state";
  static final String MAP_KEY_STATE_TYPE = "state_type";
  static final String MAP_KEY_WIFIAPINFO = "wifi_ap_info";
  static final int REQ_TYPE_NORMAL = 1;
  static final int REQ_TYPE_SKIP_CACHE = 2;
  public static final int STATE_TYPE_CLOUD_PWD = 4;
  public static final int STATE_TYPE_CONNCET = 1;
  public static final int STATE_TYPE_SAVED = 2;
  private static final String TAG = "WifiEngine";
  public static final byte WIFI_STATE_UNKNOWN = -1;
  private static ArrayList<String> mDataRefrashPath = new ArrayList();
  private static WifiEngine sInstance;
  public static long sLastEventTime = -1L;
  int MAX_ENTRY_COUNT = 500;
  long MAX_ENTRY_LIFETIME = 7200000L;
  final int MAX_PATH_COUNTS = 30;
  final int MSG_CLOUD_PWD_COMPLETED = 5;
  final int MSG_CLOUD_PWD_CONNECT = 4;
  final int MSG_DO_NOTIFY_ALL_PWD_RECV = 103;
  final int MSG_DO_NOTIFY_CONNECT_FAILED = 104;
  final int MSG_DO_NOTIFY_ONE_LISTENER = 105;
  final int MSG_LOCAL_SCAN_COMPLETED = 8;
  final int MSG_REFRESH_CLOUD_PWD = 101;
  final int MSG_REFRESH_ITEM_DATA = 102;
  final int MSG_REFRESH_SCAN_RESULT = 100;
  final int MSG_REQUEST_CLOUD_PWD = 3;
  final int MSG_SHOW_SET_DEFAULT_DLG = 106;
  final int MSG_WORKER_LOAD_LAST_SCAN_RSLT = 13;
  final int MSG_WORKER_LOCK_PRE_CONNECT = 16;
  final int MSG_WORKER_NETWORK_STATE_CHANGED = 9;
  final int MSG_WORKER_ON_CONNECTED = 22;
  final int MSG_WORKER_ON_DISCONNECTED = 23;
  final int MSG_WORKER_QUITE_RETRY = 21;
  final int MSG_WORKER_REQ_TIME_OUT = 24;
  final int MSG_WORKER_SDK_CONNECT_FAILD = 6;
  final int MSG_WORKER_SDK_CONNECT_SUCCESS = 7;
  final int MSG_WORKER_SUPPLICANT_STATE_CHANGED = 10;
  HashMap<String, ArrayList<WifiApInfo>> mApInfoCacheForConnect = new HashMap();
  Map<String, WifiApInfo> mApInfoCacheForUI = new ConcurrentHashMap();
  private ArrayList<Integer> mAvilableSdkList = new ArrayList();
  String mConnectApName = "";
  String mConnectBSSID = "";
  WifiApInfo mConnectInfo = null;
  long mConnectTimestamp = -1L;
  WifiConnector mConnector = null;
  Map<String, String> mFreeWifiCache = new HashMap();
  public boolean mHasInitApInfo = false;
  private ArrayList<Integer> mIDWaitForRsult = new ArrayList();
  private long mLocalTime = -1L;
  long mMaxTryingTime = 30000L;
  ArrayList<String> mPwdBlackList = new ArrayList();
  ConcurrentLinkedQueue<PwdRetryStateListener> mPwdRetryStateListenerList = new ConcurrentLinkedQueue();
  HashSet<Integer> mRepSdk = new HashSet();
  Integer mReqNum = Integer.valueOf(0);
  ConcurrentLinkedQueue<ScanResultListener> mScanListenerList = new ConcurrentLinkedQueue();
  List<ScanResult> mScanResults = null;
  Connector mSdkConnector = null;
  private HashMap<Integer, WifiSdkBase> mSdkHashMap = new HashMap();
  private long mServerTime = -1L;
  boolean mSkipCache = false;
  ArrayList<String> mStatePath = new ArrayList();
  ArrayList<String> mStatePath1 = new ArrayList();
  public boolean mTryingMultiPwd = false;
  public String mTryingSSID = "";
  Handler mUIHandler = null;
  WiFiConnectStateMachine mWiFiConnectStateMachine = WiFiConnectStateMachine.getInstance();
  ArrayList<WifiDataChangedListener> mWifiDataChangedListenerList = new ArrayList();
  WifiManager mWifiManager = null;
  public int mWifiState = -1;
  private ConcurrentLinkedQueue<WifiStateChangedListener> mWifiStateChangedListenerList = new ConcurrentLinkedQueue();
  Handler mWorkerHandler = null;
  
  private WifiEngine()
  {
    this.mPwdBlackList.add("Tencent-LabWiFi");
    this.mPwdBlackList.add("Tencent-StaffWiFi");
    this.mPwdBlackList.add("Tencent-OfficeWiFi");
    this.mPwdBlackList.add("Tencent-GuestWiFi");
    this.mPwdBlackList.add("Tencent-GuestWiFi-Wechat");
    this.mPwdBlackList.add("Tencent-DevNetWiFi");
    BrowserExecutorSupplier.postForTimeoutTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        LogUtils.d("wifiBoot-WifiEngine", "scanWhenWifiOn");
        WifiScanner.getInstance().scanWhenWifiOn();
      }
    });
    initSdk();
    Context localContext = ContextHolder.getAppContext();
    this.mWifiManager = ((WifiManager)localContext.getSystemService("wifi"));
    this.mUIHandler = new Handler(Looper.getMainLooper(), new Handler.Callback()
    {
      public boolean handleMessage(Message paramAnonymousMessage)
      {
        int i = paramAnonymousMessage.what;
        Object localObject;
        if (i != 100)
        {
          if (i == 103)
          {
            if (WifiEngine.this.getWifiState() != 3) {
              return true;
            }
            paramAnonymousMessage = ((ArrayList)((HashMap)paramAnonymousMessage.obj).get("listeners")).iterator();
            while (paramAnonymousMessage.hasNext())
            {
              localObject = (WifiEngine.WifiDataChangedListener)paramAnonymousMessage.next();
              if (localObject != null) {
                ((WifiEngine.WifiDataChangedListener)localObject).onCloudPwdRecievd();
              }
            }
          }
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("MSG_REFRESH_SCAN_RESULT start: ");
          ((StringBuilder)localObject).append(System.currentTimeMillis());
          LogUtils.d("time-engine", ((StringBuilder)localObject).toString());
          LogUtils.d("WifiEngine", " scan --> MSG_REFRESH_SCAN_RESULT");
          if (WifiEngine.this.getWifiState() != 3) {
            return true;
          }
          if ((paramAnonymousMessage.obj instanceof HashMap))
          {
            LogUtils.d("LIST_STABLE", "notify onListDataChanged");
            localObject = (HashMap)paramAnonymousMessage.obj;
            paramAnonymousMessage = (ArrayList)((HashMap)localObject).get("apinfo_list");
            localObject = ((ArrayList)((HashMap)localObject).get("listeners")).iterator();
            while (((Iterator)localObject).hasNext())
            {
              WifiEngine.WifiDataChangedListener localWifiDataChangedListener = (WifiEngine.WifiDataChangedListener)((Iterator)localObject).next();
              if (localWifiDataChangedListener != null) {
                localWifiDataChangedListener.onListDataChanged(paramAnonymousMessage);
              }
            }
          }
          paramAnonymousMessage = new StringBuilder();
          paramAnonymousMessage.append("MSG_REFRESH_SCAN_RESULT end: ");
          paramAnonymousMessage.append(System.currentTimeMillis());
          LogUtils.d("time-engine", paramAnonymousMessage.toString());
        }
        return false;
      }
    });
    this.mWorkerHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime(), this);
    refrashScanRslt(true, -1);
    WiFiConnectStateMachine.getInstance().addListener(this);
    TBSBroadcastReceiver.getInstance().addBroadcastObserver(this);
    try
    {
      TBSBroadcastReceiver.getInstance().register(localContext);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public static void addDataRefrashPath(String paramString)
  {
    try
    {
      long l2 = System.currentTimeMillis();
      long l3 = sLastEventTime;
      long l1 = 0L;
      if (l3 > 0L) {
        l1 = l2 - sLastEventTime;
      }
      sLastEventTime = l2;
      Object localObject = new Date();
      localObject = new SimpleDateFormat("HH:mm:ss").format((Date)localObject);
      ArrayList localArrayList = mDataRefrashPath;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append("[");
      localStringBuilder.append((String)localObject);
      localStringBuilder.append("][");
      localStringBuilder.append(l1);
      localStringBuilder.append("ms]");
      localArrayList.add(localStringBuilder.toString());
      if (mDataRefrashPath.size() > 20) {
        mDataRefrashPath.remove(0);
      }
      return;
    }
    finally {}
  }
  
  private void doConnect(final Message paramMessage)
  {
    LogUtils.d("WifiEngine", "Do Connect!");
    if (!(paramMessage.obj instanceof WifiApInfo)) {
      return;
    }
    paramMessage = (WifiApInfo)paramMessage.obj;
    this.mTryingSSID = paramMessage.mSsid;
    final Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("--doConnect[");
    ((StringBuilder)localObject1).append(this.mTryingSSID);
    ((StringBuilder)localObject1).append("]--");
    LogUtils.d("WifiEngine", ((StringBuilder)localObject1).toString());
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("[");
    ((StringBuilder)localObject1).append(paramMessage.mSsid);
    ((StringBuilder)localObject1).append("]");
    addPath(((StringBuilder)localObject1).toString());
    localObject1 = (ArrayList)this.mApInfoCacheForConnect.get(paramMessage.mSsid);
    int i;
    if ((localObject1 != null) && (!((ArrayList)localObject1).isEmpty()))
    {
      i = 0;
    }
    else
    {
      if (paramMessage.mConnectType != 3) {
        break label1203;
      }
      LogUtils.d("WifiEngine", "hiden");
      i = 1;
    }
    paramMessage.mIsSavedWifi = false;
    int k = paramMessage.mLevel;
    Object localObject3 = new ArrayList();
    Object localObject2 = this.mAvilableSdkList.iterator();
    int j = 0;
    Object localObject4;
    while (((Iterator)localObject2).hasNext())
    {
      localObject4 = (Integer)((Iterator)localObject2).next();
      if ((paramMessage.mTargetSdk < 0) || (((Integer)localObject4).intValue() == paramMessage.mTargetSdk))
      {
        localObject4 = (WifiSdkBase)this.mSdkHashMap.get(localObject4);
        if (localObject4 != null)
        {
          ((ArrayList)localObject3).add(localObject4);
          if (paramMessage.mTargetSdk >= 0) {
            j = 1;
          }
        }
      }
    }
    if ((i == 0) && ((j != 0) || (!paramMessage.mUserInputPwd)))
    {
      if (paramMessage.mHasCloudPassword)
      {
        LogUtils.d("WifiEngine", "cloudWifi");
        addPath("cloudWifi");
        LogUtils.d("WifiEngine-retry", "------------CHECEK NEED RETRY-------------");
        localObject2 = new ArrayList();
        localObject3 = ((ArrayList)localObject3).iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject4 = (WifiSdkBase)((Iterator)localObject3).next();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("sdk : ");
          localStringBuilder.append(localObject4);
          LogUtils.d("WifiEngine-retry", localStringBuilder.toString());
          if (((WifiSdkBase)localObject4).enterRetryMod((ArrayList)localObject1))
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("find sdk[");
            localStringBuilder.append(((WifiSdkBase)localObject4).getId());
            localStringBuilder.append("]");
            LogUtils.d("WifiEngine", localStringBuilder.toString());
            if (((WifiSdkBase)localObject4).mIsForceUse)
            {
              localStringBuilder = new StringBuilder();
              localStringBuilder.append("mIsForceUse : ");
              localStringBuilder.append(((WifiSdkBase)localObject4).mIsForceUse);
              LogUtils.d("WifiEngine-retry", localStringBuilder.toString());
              ((ArrayList)localObject2).add(0, localObject4);
              localStringBuilder = new StringBuilder();
              localStringBuilder.append("find_force_sdk(");
              localStringBuilder.append(((WifiSdkBase)localObject4).getId());
              localStringBuilder.append(")");
              addPath(localStringBuilder.toString());
            }
            else
            {
              ((ArrayList)localObject2).add(localObject4);
              localStringBuilder = new StringBuilder();
              localStringBuilder.append("find_sdk(");
              localStringBuilder.append(((WifiSdkBase)localObject4).getId());
              localStringBuilder.append(")");
              addPath(localStringBuilder.toString());
            }
            ((WifiSdkBase)localObject4).getTryCounts();
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("retryCount : ");
            localStringBuilder.append(((WifiSdkBase)localObject4).getTryCounts());
            LogUtils.d("WifiEngine-retry", localStringBuilder.toString());
          }
          else
          {
            LogUtils.d("WifiEngine", "find none");
            LogUtils.d("WifiEngine-retry", "unavilable");
          }
        }
        if (((ArrayList)localObject2).size() > 0)
        {
          LogUtils.d("WifiEngine", "create Connector");
          this.mSdkConnector = new Connector((ArrayList)localObject2, paramMessage.clone());
          this.mSdkConnector.start();
          localObject1 = this.mConnectInfo;
          if (localObject1 != null) {
            ((WifiApInfo)localObject1).mConnectBySdk = true;
          }
        }
        else
        {
          LogUtils.d("WifiEngine", "doConnect");
          doFinishTrying(paramMessage.mSsid, true, false);
          addPath("no_sdk");
        }
      }
      else if (WifiUtils.isValidInfo(paramMessage))
      {
        LogUtils.d("WifiEngine", "openWifi");
        addPath("openWifi");
        localObject1 = new WifiConnector(paramMessage.mSsid, "sys", k);
        ((WifiConnector)localObject1).setConnectEvent(new WifiConnector.ConnectEvent()
        {
          public void doConnect(String paramAnonymousString)
          {
            localObject1.connect(paramMessage);
          }
          
          public WifiApInfo getConnectedInfo()
          {
            return null;
          }
          
          public void onConnected() {}
          
          public void onConnectedFailed(byte paramAnonymousByte)
          {
            LogUtils.d("WifiEngine", "onConnectedFailed1");
            WifiEngine.this.doFinishTrying(paramMessage.mSsid, true, false);
          }
          
          public void onPwdError(String paramAnonymousString)
          {
            LogUtils.d("WifiEngine", "onPwdError1");
            WifiEngine.this.doFinishTrying(paramMessage.mSsid, true, false);
          }
        });
        ((WifiConnector)localObject1).start();
        this.mConnector = ((WifiConnector)localObject1);
      }
      else
      {
        LogUtils.d("WifiEngine", "apInfo_unavliable");
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("[");
        ((StringBuilder)localObject1).append(paramMessage.mSsid);
        ((StringBuilder)localObject1).append("][");
        ((StringBuilder)localObject1).append(paramMessage.mBssid);
        ((StringBuilder)localObject1).append("][");
        ((StringBuilder)localObject1).append(paramMessage.mSafeType);
        ((StringBuilder)localObject1).append("][");
        ((StringBuilder)localObject1).append(paramMessage.mPassword);
        ((StringBuilder)localObject1).append("][");
        ((StringBuilder)localObject1).append(paramMessage.mHasCloudPassword);
        ((StringBuilder)localObject1).append("][");
        ((StringBuilder)localObject1).append(paramMessage.mIsSavedWifi);
        ((StringBuilder)localObject1).append("]");
        LogUtils.d("WifiEngine", ((StringBuilder)localObject1).toString());
        addPath("apInfo_unavliable");
        LogUtils.d("WifiEngine", "remove MSG_WORKER_QUITE_RETRY");
        this.mWorkerHandler.removeMessages(21);
        paramMessage = this.mWorkerHandler.obtainMessage(21, paramMessage.mSsid);
        LogUtils.d("WifiEngine", "send MSG_WORKER_QUITE_RETRY 3000");
        this.mWorkerHandler.sendMessageDelayed(paramMessage, 3000L);
      }
    }
    else
    {
      LogUtils.d("WifiEngine", "savedWifi");
      addPath("savedWifi");
      localObject1 = new WifiConnector(paramMessage.mSsid, "sys", k);
      ((WifiConnector)localObject1).setConnectEvent(new WifiConnector.ConnectEvent()
      {
        public void doConnect(String paramAnonymousString)
        {
          localObject1.connect(paramMessage);
        }
        
        public WifiApInfo getConnectedInfo()
        {
          return null;
        }
        
        public void onConnected() {}
        
        public void onConnectedFailed(byte paramAnonymousByte)
        {
          LogUtils.d("WifiEngine", "onConnectedFailed1");
          WifiEngine.this.doFinishTrying(paramMessage.mSsid, true, false);
        }
        
        public void onPwdError(String paramAnonymousString)
        {
          LogUtils.d("WifiEngine", "onPwdError2");
          WifiEngine.this.doFinishTrying(paramMessage.mSsid, true, false);
        }
      });
      ((WifiConnector)localObject1).start();
      this.mConnector = ((WifiConnector)localObject1);
    }
    LogUtils.d("WifiEngine", "connecting...");
    addPath("connecting");
    LogUtils.d("WifiEngine", "remove MSG_WORKER_QUITE_RETRY");
    this.mWorkerHandler.removeMessages(21);
    paramMessage = this.mWorkerHandler.obtainMessage(21, paramMessage.mSsid);
    LogUtils.d("WifiEngine", "send MSG_WORKER_QUITE_RETRY 30000");
    this.mWorkerHandler.sendMessageDelayed(paramMessage, this.mMaxTryingTime);
    return;
    label1203:
    LogUtils.d("WifiEngine", "info null");
    LogUtils.d("WifiEngine", "no connect info!");
    addPath("LostSignal");
    LogUtils.d("WifiEngine", "remove MSG_WORKER_QUITE_RETRY");
    this.mWorkerHandler.removeMessages(21);
    paramMessage = this.mWorkerHandler.obtainMessage(21, paramMessage.mSsid);
    this.mWorkerHandler.sendMessageDelayed(paramMessage, 5000L);
  }
  
  private void doFinishTrying(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("doFinishTrying[");
    localStringBuilder.append(paramString);
    localStringBuilder.append("][");
    localStringBuilder.append(this.mTryingSSID);
    localStringBuilder.append("][");
    localStringBuilder.append(paramBoolean1);
    localStringBuilder.append("][");
    localStringBuilder.append(paramBoolean2);
    localStringBuilder.append("]");
    LogUtils.d("WifiEngine", localStringBuilder.toString());
    if (!TextUtils.equals(paramString, this.mTryingSSID))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append(" already canceled");
      LogUtils.d("WifiEngine", localStringBuilder.toString());
      return;
    }
    int i = 2;
    if (paramBoolean2) {
      i = 0;
    }
    paramString = new StringBuilder();
    paramString.append("========Finish ");
    paramString.append(this.mTryingSSID);
    paramString.append(" RETRYING========");
    LogUtils.d("WifiEngine-retry", paramString.toString());
    paramString = this.mSdkConnector;
    if (paramString != null) {
      paramString.quit(i);
    }
    paramString = this.mConnector;
    this.mConnector = null;
    if (paramString != null) {
      paramString.stop(i);
    }
    this.mTryingMultiPwd = false;
    paramString = this.mPwdRetryStateListenerList.iterator();
    while (paramString.hasNext()) {
      ((PwdRetryStateListener)paramString.next()).onRetryEnd();
    }
    LogUtils.d("WifiEngine", "remove MSG_WORKER_QUITE_RETRY");
    this.mWorkerHandler.removeMessages(21);
    if (paramBoolean1)
    {
      LogUtils.d("WifiEngine", "send MSG_EXIT_RETRY_MODE 0");
      paramString = this.mWiFiConnectStateMachine.obtainMessage(203);
      paramString.obj = this.mTryingSSID;
      paramString.sendToTarget();
      this.mTryingSSID = "";
    }
  }
  
  private void doRequestCloudPwd(List<ScanResult> paramList, boolean paramBoolean)
  {
    ??? = new StringBuilder();
    ((StringBuilder)???).append("doRequestCloudPwd[");
    ((StringBuilder)???).append(paramBoolean);
    ((StringBuilder)???).append("]");
    LogUtils.d("LIST_STABLE", ((StringBuilder)???).toString());
    ??? = new StringBuilder();
    ((StringBuilder)???).append(System.currentTimeMillis());
    ((StringBuilder)???).append(" : doRequestCloudPwd");
    LogUtils.d("perform-test", ((StringBuilder)???).toString());
    LogUtils.d("WifiEngine", "MSG_REQUEST_CLOUD_PWD");
    this.mWorkerHandler.removeMessages(24);
    if (Apn.getActiveNetworkInfo(false) == null) {
      synchronized (this.mRepSdk)
      {
        notifyCloudPwdRecievd();
        localObject2 = this.mReqNum;
        this.mReqNum = Integer.valueOf(this.mReqNum.intValue() + 1);
        this.mRepSdk.clear();
      }
    }
    long l = 4000L;
    if (Apn.is2GMode()) {
      l = 10000L;
    }
    this.mWorkerHandler.sendEmptyMessageDelayed(24, l);
    Object localObject2 = this.mSdkHashMap.values();
    LogUtils.d("WifiEngine", "start requestCloudWifi");
    synchronized (this.mRepSdk)
    {
      Object localObject3 = this.mReqNum;
      this.mReqNum = Integer.valueOf(this.mReqNum.intValue() + 1);
      this.mRepSdk.clear();
      ??? = ((Collection)localObject2).iterator();
      while (((Iterator)???).hasNext())
      {
        localObject2 = (WifiSdkBase)((Iterator)???).next();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("do request [");
        ((StringBuilder)localObject3).append(this.mReqNum);
        ((StringBuilder)localObject3).append("][");
        ((StringBuilder)localObject3).append(((WifiSdkBase)localObject2).getId());
        ((StringBuilder)localObject3).append("]");
        LogUtils.d("WifiEngine", ((StringBuilder)localObject3).toString());
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("获取密码:sdk[");
        ((StringBuilder)localObject3).append(((WifiSdkBase)localObject2).getId());
        ((StringBuilder)localObject3).append("]序号[");
        ((StringBuilder)localObject3).append(this.mReqNum);
        ((StringBuilder)localObject3).append("]使用缓存[");
        ((StringBuilder)localObject3).append(paramBoolean ^ true);
        ((StringBuilder)localObject3).append("]");
        addDataRefrashPath(((StringBuilder)localObject3).toString());
        ((WifiSdkBase)localObject2).requestCloudWifi(paramList, paramBoolean, this.mReqNum.intValue());
      }
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF107");
      return;
    }
  }
  
  private void doRequestCloudPwdCompleted(Message paramMessage)
  {
    LogUtils.d("WifiEngine", "----------------MSG_CLOUD_PWD_COMPLETED------------------");
    ??? = new StringBuilder();
    ((StringBuilder)???).append(System.currentTimeMillis());
    ((StringBuilder)???).append(" : doRequestCloudPwdCompleted ");
    LogUtils.d("perform-test", ((StringBuilder)???).toString());
    if ((paramMessage.obj instanceof ArrayList))
    {
      Object localObject2 = (ArrayList)paramMessage.obj;
      ??? = getScanResultList();
      localObject2 = ((ArrayList)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        WifiApInfo localWifiApInfo = (WifiApInfo)((Iterator)localObject2).next();
        Object localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("wifiApInfo : ");
        ((StringBuilder)localObject3).append(localWifiApInfo);
        LogUtils.d("WifiEngine-PWD", ((StringBuilder)localObject3).toString());
        if ((localWifiApInfo.mActionType > 0L) || (!TextUtils.isEmpty(localWifiApInfo.mBrand)) || (!TextUtils.isEmpty(localWifiApInfo.mPortal)) || (!TextUtils.isEmpty(localWifiApInfo.mWeixinSchama)))
        {
          localObject3 = (WifiApInfo)this.mApInfoCacheForUI.get(localWifiApInfo.mSsid);
          if (localObject3 != null)
          {
            localObject3 = ((WifiApInfo)localObject3).clone();
            ((WifiApInfo)localObject3).mActionType |= localWifiApInfo.mActionType;
            ((WifiApInfo)localObject3).mBrand = localWifiApInfo.mBrand;
            ((WifiApInfo)localObject3).mPortal = localWifiApInfo.mPortal;
            ((WifiApInfo)localObject3).mWeixinSchama = localWifiApInfo.mWeixinSchama;
            WifiBrandInfoHelper.getInstance().addInfo((WifiApInfo)localObject3, true, (ArrayList)???);
          }
        }
      }
    }
    if (paramMessage.arg2 == this.mReqNum.intValue()) {
      synchronized (this.mRepSdk)
      {
        if (paramMessage.arg2 == this.mReqNum.intValue()) {
          this.mRepSdk.add(Integer.valueOf(paramMessage.arg1));
        }
        i = this.mRepSdk.size();
        refrashScanRslt(false, i);
      }
    }
    int i = 0;
    if (i == this.mSdkHashMap.size()) {
      notifyCloudPwdRecievd();
    }
  }
  
  public static WifiEngine getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new WifiEngine();
      }
      WifiEngine localWifiEngine = sInstance;
      return localWifiEngine;
    }
    finally {}
  }
  
  private int getWiFiType(WifiApInfo paramWifiApInfo, String paramString)
  {
    if (paramWifiApInfo.mSafeType != 0)
    {
      if ((paramWifiApInfo.mIsSavedWifi) && (!TextUtils.equals(paramString, paramWifiApInfo.mSsid))) {
        return 4;
      }
      if (WifiUtils.isFreeWifi(paramWifiApInfo)) {
        return 1;
      }
      return 5;
    }
    if (WifiUtils.isFreeWifi(paramWifiApInfo)) {
      return 2;
    }
    return 3;
  }
  
  private void initSdk()
  {
    LogUtils.d("WifiEngine", "initSdk() ID_QB");
    this.mAvilableSdkList.add(Integer.valueOf(2));
    QBSdk localQBSdk = new QBSdk(BrowserExecutorSupplier.getLooperForRunShortTime());
    localQBSdk.addCallback(this);
    this.mSdkHashMap.put(Integer.valueOf(localQBSdk.getId()), localQBSdk);
  }
  
  private boolean needSync()
  {
    return Looper.myLooper() == this.mWorkerHandler.getLooper();
  }
  
  private void notifyCloudPwdRecievd()
  {
    HashMap localHashMap = new HashMap(1);
    localHashMap.put("listeners", new ArrayList(this.mWifiDataChangedListenerList));
    this.mUIHandler.obtainMessage(103, localHashMap).sendToTarget();
  }
  
  private void notifyRefreshList(Collection<WifiApInfo> paramCollection)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("notifyRefreshList start: ");
    ((StringBuilder)localObject).append(System.currentTimeMillis());
    LogUtils.d("time-engine", ((StringBuilder)localObject).toString());
    LogUtils.d("WifiEngine", "====================Notify UI Refresh List====================");
    localObject = paramCollection.iterator();
    while (((Iterator)localObject).hasNext())
    {
      WifiApInfo localWifiApInfo = (WifiApInfo)((Iterator)localObject).next();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("WiFi Para: ssid: ");
      localStringBuilder.append(localWifiApInfo.mSsid);
      localStringBuilder.append(" bssid：");
      localStringBuilder.append(localWifiApInfo.mBssid);
      localStringBuilder.append(" safeType：");
      localStringBuilder.append(localWifiApInfo.mSafeType);
      localStringBuilder.append(" mLevel：");
      localStringBuilder.append(localWifiApInfo.mLevel);
      LogUtils.d("WifiEngine", localStringBuilder.toString());
    }
    if (getWifiState() != 3) {
      paramCollection.clear();
    }
    localObject = new HashMap(2);
    ((HashMap)localObject).put("apinfo_list", new ArrayList(paramCollection));
    ((HashMap)localObject).put("listeners", new ArrayList(this.mWifiDataChangedListenerList));
    paramCollection = new StringBuilder();
    paramCollection.append("notifyRefreshList MSG_REFRESH_SCAN_RESULT send: ");
    paramCollection.append(System.currentTimeMillis());
    LogUtils.d("time-engine", paramCollection.toString());
    this.mUIHandler.obtainMessage(100, localObject).sendToTarget();
    LogUtils.d("WifiEngine", "====================Notify UI Refresh List End====================");
    paramCollection = new StringBuilder();
    paramCollection.append("notifyRefreshList end: ");
    paramCollection.append(System.currentTimeMillis());
    LogUtils.d("time-engine", paramCollection.toString());
  }
  
  private void notifyStateChange(HashSet<String> paramHashSet, int paramInt)
  {
    LogUtils.d("conn-WifiEngine", "notifyStateChange()");
    HashMap localHashMap = new HashMap(2);
    localHashMap.put("ssid_set", paramHashSet);
    localHashMap.put("listeners", new ArrayList(this.mWifiDataChangedListenerList));
    localHashMap.put("state_type", Integer.valueOf(paramInt));
    this.mUIHandler.obtainMessage(102, localHashMap).sendToTarget();
  }
  
  private void refrashScanRslt(boolean paramBoolean, int paramInt)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("refrashScanRslt start: ");
    ((StringBuilder)localObject1).append(System.currentTimeMillis());
    LogUtils.d("time-engine", ((StringBuilder)localObject1).toString());
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(System.currentTimeMillis());
    ((StringBuilder)localObject1).append(" : refrashScanRslt");
    LogUtils.d("perform-test", ((StringBuilder)localObject1).toString());
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("scan --> refrashScanRslt[");
    ((StringBuilder)localObject1).append(paramBoolean);
    ((StringBuilder)localObject1).append("][");
    ((StringBuilder)localObject1).append(paramInt);
    ((StringBuilder)localObject1).append("]");
    LogUtils.d("WifiEngine", ((StringBuilder)localObject1).toString());
    if (getWifiState() != 3) {
      return;
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("refrashScanRslt[");
    ((StringBuilder)localObject1).append(paramInt);
    ((StringBuilder)localObject1).append("][");
    ((StringBuilder)localObject1).append(this.mSdkHashMap.size());
    ((StringBuilder)localObject1).append("]");
    LogUtils.d("LIST_STABLE", ((StringBuilder)localObject1).toString());
    if ((paramInt != this.mSdkHashMap.size()) && (paramInt != -1)) {
      return;
    }
    LogUtils.d("WifiEngine", "Scan Local Wifi Completed");
    LogUtils.d("WifiEngine", "MSG_WORKER_REFRASH_APINFO_BY_SYS_SCAN");
    localObject1 = getScanResults();
    HashMap localHashMap = new HashMap();
    Object localObject2 = new ConcurrentHashMap();
    if ((localObject1 != null) && (!((List)localObject1).isEmpty()))
    {
      Object localObject3 = new ArrayList();
      Object localObject4 = ((List)localObject1).iterator();
      Object localObject5;
      while (((Iterator)localObject4).hasNext())
      {
        localObject1 = (ScanResult)((Iterator)localObject4).next();
        if (!TextUtils.isEmpty(((ScanResult)localObject1).SSID))
        {
          localObject5 = new WifiApInfo();
          ((WifiApInfo)localObject5).mBssid = ((ScanResult)localObject1).BSSID;
          ((WifiApInfo)localObject5).mSsid = ((ScanResult)localObject1).SSID;
          ((WifiApInfo)localObject5).mSafeType = WifiUtils.getSecurity((ScanResult)localObject1);
          ((WifiApInfo)localObject5).mLevel = ((ScanResult)localObject1).level;
          ((WifiApInfo)localObject5).mFrequency = ((ScanResult)localObject1).frequency;
          localObject2 = (ArrayList)localHashMap.get(((WifiApInfo)localObject5).mSsid);
          localObject1 = localObject2;
          if (localObject2 == null)
          {
            localObject1 = new ArrayList();
            localHashMap.put(((WifiApInfo)localObject5).mSsid, localObject1);
          }
          ((ArrayList)localObject1).add(localObject5);
          ((ArrayList)localObject3).add(localObject5);
        }
      }
      if ((!paramBoolean) && (!((ArrayList)localObject3).isEmpty()))
      {
        localObject2 = null;
        localObject3 = ((ArrayList)localObject3).iterator();
        if (((Iterator)localObject3).hasNext())
        {
          localObject4 = (WifiApInfo)((Iterator)localObject3).next();
          localObject5 = this.mSdkHashMap.values().iterator();
          localObject1 = localObject2;
          for (;;)
          {
            localObject2 = localObject1;
            if (!((Iterator)localObject5).hasNext()) {
              break;
            }
            localObject2 = (WifiSdkBase)((Iterator)localObject5).next();
            if (((localObject1 == null) || (!((Set)localObject1).contains(((WifiApInfo)localObject4).mSsid))) && (!this.mPwdBlackList.contains(((WifiApInfo)localObject4).mSsid)) && (((WifiSdkBase)localObject2).isPwdIn(((WifiApInfo)localObject4).mSsid, ((WifiApInfo)localObject4).mBssid))) {
              if (WifiBlocker.getInstance().isBlocked(((WifiApInfo)localObject4).mSsid))
              {
                localObject2 = localObject1;
                if (localObject1 == null) {
                  localObject2 = new HashSet(1);
                }
                ((Set)localObject2).add(((WifiApInfo)localObject4).mSsid);
                localObject1 = localObject2;
              }
              else
              {
                ((WifiApInfo)localObject4).mHasCloudPassword = true;
                ((WifiApInfo)localObject4).mSdkFlag |= ((WifiSdkBase)localObject2).getId();
                ((WifiApInfo)localObject4).mScore = ((WifiSdkBase)localObject2).getScore(((WifiApInfo)localObject4).mSsid, ((WifiApInfo)localObject4).mBssid);
              }
            }
          }
        }
      }
      localObject1 = WifiUtils.combineWifiApInfo(localHashMap);
      localObject3 = ((HashMap)localObject1).keySet();
      localObject2 = PublicSettingManager.getInstance().getLastConnectedFreeWifi();
      localObject3 = ((Set)localObject3).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject5 = (String)((Iterator)localObject3).next();
        localObject4 = (WifiApInfo)((HashMap)localObject1).get(localObject5);
        ((WifiApInfo)localObject4).mIsSavedWifi = NewWifiDetector.getInstance().isSaved((WifiApInfo)localObject4);
        localObject5 = WifiBrandInfoHelper.getInstance().get((String)localObject5);
        if (localObject5 != null)
        {
          ((WifiApInfo)localObject4).mActionType = ((JSONObject)localObject5).optInt("action");
          ((WifiApInfo)localObject4).mBrand = ((JSONObject)localObject5).optString("brand");
          ((WifiApInfo)localObject4).mPortal = ((JSONObject)localObject5).optString("portal");
          ((WifiApInfo)localObject4).mWeixinSchama = ((JSONObject)localObject5).optString("wxschema");
        }
        ((WifiApInfo)localObject4).mWiFiType = getWiFiType((WifiApInfo)localObject4, (String)localObject2);
      }
      LogUtils.d("LIST_STABLE", "data changed");
      this.mApInfoCacheForConnect = localHashMap;
      this.mApInfoCacheForUI = ((Map)localObject1);
      if (!this.mHasInitApInfo) {
        this.mHasInitApInfo = true;
      }
    }
    else
    {
      LogUtils.d("LIST_STABLE", "data changed");
      LogUtils.d("WifiEngine", "scanResults is null ");
      this.mApInfoCacheForConnect = localHashMap;
      this.mApInfoCacheForUI = ((Map)localObject2);
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("refrashScanRslt end: ");
    ((StringBuilder)localObject1).append(System.currentTimeMillis());
    LogUtils.d("time-engine", ((StringBuilder)localObject1).toString());
  }
  
  private void updateScanRslt()
  {
    try
    {
      this.mScanResults = ((WifiManager)ContextHolder.getAppContext().getSystemService("wifi")).getScanResults();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void addPath(String paramString)
  {
    try
    {
      this.mStatePath.add(0, paramString);
      if (this.mStatePath.size() > 30) {
        this.mStatePath.remove(this.mStatePath.size() - 1);
      }
      LogUtils.d("BSPATH", getPathString());
      return;
    }
    finally {}
  }
  
  public void addPath1(String paramString)
  {
    try
    {
      this.mStatePath1.add(0, paramString);
      if (this.mStatePath1.size() > 30) {
        this.mStatePath1.remove(this.mStatePath1.size() - 1);
      }
      LogUtils.d("BSPATH", getPathString());
      return;
    }
    finally {}
  }
  
  public void addPwdRetryStateListener(PwdRetryStateListener paramPwdRetryStateListener)
  {
    if (!this.mPwdRetryStateListenerList.contains(paramPwdRetryStateListener)) {
      this.mPwdRetryStateListenerList.add(paramPwdRetryStateListener);
    }
  }
  
  public void addScanResultListener(ScanResultListener paramScanResultListener)
  {
    if (!this.mScanListenerList.contains(paramScanResultListener)) {
      this.mScanListenerList.add(paramScanResultListener);
    }
  }
  
  public void addWifiDataChangedListener(WifiDataChangedListener paramWifiDataChangedListener)
  {
    if (!this.mWifiDataChangedListenerList.contains(paramWifiDataChangedListener)) {
      this.mWifiDataChangedListenerList.add(paramWifiDataChangedListener);
    }
  }
  
  public void addWifiStateChangedListener(WifiStateChangedListener paramWifiStateChangedListener)
  {
    if (!this.mWifiStateChangedListenerList.contains(paramWifiStateChangedListener)) {
      this.mWifiStateChangedListenerList.add(paramWifiStateChangedListener);
    }
  }
  
  public void cancelConnectAp(WifiApInfo paramWifiApInfo, boolean paramBoolean)
  {
    disableAP(paramWifiApInfo, paramBoolean, false);
  }
  
  void clearFreeWifiCache()
  {
    this.mFreeWifiCache.clear();
  }
  
  public void connect(WifiApInfo paramWifiApInfo, boolean paramBoolean)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("connect[");
    ((StringBuilder)localObject).append(paramWifiApInfo.mSsid);
    ((StringBuilder)localObject).append("][");
    ((StringBuilder)localObject).append(paramBoolean);
    ((StringBuilder)localObject).append("]");
    LogUtils.d("WifiEngine", ((StringBuilder)localObject).toString());
    LogUtils.d("WifiEngine", "connect");
    doFinishTrying(this.mTryingSSID, false, true);
    localObject = this.mSdkConnector;
    if (localObject != null) {
      ((Connector)localObject).quit(0);
    }
    localObject = this.mConnector;
    this.mConnector = null;
    if (localObject != null) {
      ((WifiConnector)localObject).stop(0);
    }
    this.mConnectInfo = paramWifiApInfo.clone();
    this.mConnectInfo.mIsSavedWifi = NewWifiDetector.getInstance().isSaved(paramWifiApInfo.mSsid);
    this.mConnectInfo.mUserInputPwd = paramBoolean;
    localObject = this.mWiFiConnectStateMachine.obtainMessage(201);
    ((Message)localObject).obj = this.mConnectInfo.clone();
    ((Message)localObject).sendToTarget();
    localObject = this.mWiFiConnectStateMachine.obtainMessage(202);
    ((Message)localObject).obj = paramWifiApInfo.mSsid;
    ((Message)localObject).sendToTarget();
    paramWifiApInfo = this.mWorkerHandler.obtainMessage(4);
    paramWifiApInfo.obj = this.mConnectInfo;
    paramWifiApInfo.sendToTarget();
  }
  
  public void disableAP(WifiApInfo paramWifiApInfo, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramWifiApInfo == null) {
      return;
    }
    if (paramBoolean1)
    {
      localObject1 = this.mWiFiConnectStateMachine.obtainMessage(204);
      ((Message)localObject1).obj = paramWifiApInfo.mSsid;
      ((Message)localObject1).sendToTarget();
    }
    paramBoolean1 = TextUtils.equals(paramWifiApInfo.mSsid, this.mTryingSSID);
    int i = 0;
    if (paramBoolean1)
    {
      LogUtils.d("WifiEngine", "disableAP");
      doFinishTrying(paramWifiApInfo.mSsid, true, false);
    }
    Object localObject1 = WifiUtils.getConfiguredNetworks();
    if (localObject1 != null)
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("\"");
      ((StringBuilder)localObject2).append(paramWifiApInfo.mSsid);
      ((StringBuilder)localObject2).append("\"");
      paramWifiApInfo = ((StringBuilder)localObject2).toString();
      int j = ((List)localObject1).size();
      while (i < j)
      {
        localObject2 = (WifiConfiguration)((List)localObject1).get(i);
        if (localObject2 != null)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("disableAP() netId = ");
          localStringBuilder.append(((WifiConfiguration)localObject2).networkId);
          localStringBuilder.append(", SSID = ");
          localStringBuilder.append(((WifiConfiguration)localObject2).SSID);
          localStringBuilder.append(", BSSID = ");
          localStringBuilder.append(((WifiConfiguration)localObject2).BSSID);
          localStringBuilder.append(", priority = ");
          localStringBuilder.append(((WifiConfiguration)localObject2).priority);
          LogUtils.d("WifiEngine", localStringBuilder.toString());
          if ((paramWifiApInfo.equals(((WifiConfiguration)localObject2).SSID)) && (((WifiConfiguration)localObject2).networkId != -1))
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("disableAP() removeNetwork and saveConfiguration netId = ");
            localStringBuilder.append(((WifiConfiguration)localObject2).networkId);
            LogUtils.d("WifiEngine", localStringBuilder.toString());
            this.mWifiManager.disableNetwork(((WifiConfiguration)localObject2).networkId);
            if (paramBoolean2) {
              this.mWifiManager.removeNetwork(((WifiConfiguration)localObject2).networkId);
            }
            this.mWifiManager.saveConfiguration();
          }
        }
        i += 1;
      }
    }
  }
  
  public void forgetAP(WifiApInfo paramWifiApInfo, boolean paramBoolean)
  {
    disableAP(paramWifiApInfo, paramBoolean, true);
    NewWifiDetector.getInstance().refrashData();
    if (!NewWifiDetector.getInstance().isSaved(paramWifiApInfo))
    {
      Object localObject = (WifiApInfo)this.mApInfoCacheForUI.get(paramWifiApInfo.mSsid);
      if (localObject != null) {
        ((WifiApInfo)localObject).mIsSavedWifi = false;
      } else {
        paramWifiApInfo.mIsSavedWifi = false;
      }
      localObject = new HashSet(1);
      ((HashSet)localObject).add(paramWifiApInfo.mSsid);
      notifyStateChange((HashSet)localObject, 2);
      return;
    }
    if (paramBoolean) {
      BrowserExecutorSupplier.forMainThreadTasks().execute(new Runnable()
      {
        public void run() {}
      });
    }
  }
  
  public WifiApInfo getApInfo(String paramString)
  {
    try
    {
      paramString = (WifiApInfo)this.mApInfoCacheForUI.get(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public WifiApInfo getCurApInfo()
  {
    WiFiConnectStateMachine.WiFiConnectState localWiFiConnectState = WiFiConnectStateMachine.getInstance().getCurState();
    if (localWiFiConnectState != null) {
      return getApInfo(localWiFiConnectState.getSsid());
    }
    return null;
  }
  
  public List<WifiApInfo> getFreeWifiInfoList()
  {
    Object localObject = WifiUtils.getScanResults();
    if ((localObject != null) && (!((List)localObject).isEmpty()))
    {
      removeExpiredFromFreeWifiCache();
      ArrayList localArrayList = new ArrayList();
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        ScanResult localScanResult = (ScanResult)((Iterator)localObject).next();
        if ((!TextUtils.isEmpty(localScanResult.SSID)) && (!TextUtils.isEmpty(localScanResult.BSSID)) && (hitFreeWifiCache(localScanResult.SSID, localScanResult.BSSID)))
        {
          WifiApInfo localWifiApInfo = new WifiApInfo();
          localWifiApInfo.mBssid = localScanResult.BSSID;
          localWifiApInfo.mSsid = localScanResult.SSID;
          localWifiApInfo.mSafeType = WifiUtils.getSecurity(localScanResult);
          localWifiApInfo.mLevel = localScanResult.level;
          localWifiApInfo.mFrequency = localScanResult.frequency;
          localWifiApInfo.mHasCloudPassword = true;
          localArrayList.add(localWifiApInfo);
        }
      }
      return localArrayList;
    }
    return null;
  }
  
  public String getPathString()
  {
    try
    {
      Object localObject1 = new StringBuilder();
      Iterator localIterator = this.mStatePath.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (str.startsWith("["))
        {
          ((StringBuilder)localObject1).append(str);
          ((StringBuilder)localObject1).append("\r\n");
        }
        else
        {
          ((StringBuilder)localObject1).append(str);
          ((StringBuilder)localObject1).append("<-");
        }
      }
      localObject1 = ((StringBuilder)localObject1).toString();
      return (String)localObject1;
    }
    finally {}
  }
  
  public ArrayList<WifiApInfo> getScanResultList()
  {
    if (getWifiState() != 3) {
      return new ArrayList();
    }
    return new ArrayList(this.mApInfoCacheForUI.values());
  }
  
  public List<ScanResult> getScanResults()
  {
    try
    {
      List localList1 = this.mScanResults;
      if (localList1 == null) {
        try
        {
          this.mScanResults = ((WifiManager)ContextHolder.getAppContext().getSystemService("wifi")).getScanResults();
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
      List localList2 = this.mScanResults;
      return localList2;
    }
    finally {}
  }
  
  public HashMap<Integer, WifiSdkBase> getSdkHashMap()
  {
    return this.mSdkHashMap;
  }
  
  public long getServerTime()
  {
    try
    {
      if (this.mServerTime > 0L)
      {
        l = this.mServerTime + (SystemClock.elapsedRealtime() - this.mLocalTime) / 1000L;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("getServerTime() nowServerTime: ");
        localStringBuilder.append(l);
        localStringBuilder.toString();
        return l;
      }
      long l = System.currentTimeMillis();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getServerTime() System.currentTimeMillis(): ");
      localStringBuilder.append(l);
      localStringBuilder.toString();
      l /= 1000L;
      return l;
    }
    finally {}
  }
  
  public WifiManager getWifiManager()
  {
    return this.mWifiManager;
  }
  
  public int getWifiState()
  {
    if (this.mWifiState == -1) {
      try
      {
        this.mWifiState = this.mWifiManager.getWifiState();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return this.mWifiState;
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    int i = paramMessage.what;
    if (i != 8)
    {
      switch (i)
      {
      default: 
        return false;
      case 5: 
        doRequestCloudPwdCompleted(paramMessage);
        return false;
      case 4: 
        doConnect(paramMessage);
        return false;
      }
      if ((paramMessage.obj instanceof List))
      {
        List localList = (List)paramMessage.obj;
        boolean bool;
        if (paramMessage.arg1 == 2) {
          bool = true;
        } else {
          bool = false;
        }
        doRequestCloudPwd(localList, bool);
        return false;
      }
    }
    else
    {
      refrashScanRslt(false, -1);
      notifyRefreshList(this.mApInfoCacheForUI.values());
    }
    return false;
  }
  
  boolean hitFreeWifiCache(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return false;
      }
      if (this.mFreeWifiCache.containsKey(paramString2))
      {
        Object localObject = ((String)this.mFreeWifiCache.get(paramString2)).split("_");
        if ((localObject != null) && (localObject.length == 2))
        {
          if (!paramString1.equals(localObject[0])) {
            return false;
          }
          if (System.currentTimeMillis() - Long.valueOf(localObject[1]).longValue() > this.MAX_ENTRY_LIFETIME) {
            return false;
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("hitFreeWifiCache() ssid: ");
          ((StringBuilder)localObject).append(paramString1);
          ((StringBuilder)localObject).append(", bssid: ");
          ((StringBuilder)localObject).append(paramString2);
          ((StringBuilder)localObject).append(" (hit)");
          ((StringBuilder)localObject).toString();
          return true;
        }
      }
      return false;
    }
    return false;
  }
  
  public void onBroadcastReceiver(Intent paramIntent)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("onBroadcastReceiver() ");
    ((StringBuilder)localObject1).append(paramIntent);
    ((StringBuilder)localObject1).toString();
    localObject1 = paramIntent.getAction();
    Object localObject2;
    if (TextUtils.equals((CharSequence)localObject1, "android.net.wifi.WIFI_STATE_CHANGED"))
    {
      int i = this.mWifiState;
      this.mWifiState = paramIntent.getIntExtra("wifi_state", 4);
      int j = this.mWifiState;
      if (i != j)
      {
        if (j != 3)
        {
          switch (j)
          {
          default: 
            break;
          case 0: 
          case 1: 
            LogUtils.d("WifiEngine", "WIFI DISABLE");
            WifiScanner.getInstance().stopBgScanWhenWifiOff();
            break;
          }
        }
        else
        {
          LogUtils.d("WifiEngine", "WIFI ENABLE");
          WifiScanner.getInstance().scanWhenWifiOn();
        }
        paramIntent = this.mWifiStateChangedListenerList.iterator();
        while (paramIntent.hasNext())
        {
          localObject1 = (WifiStateChangedListener)paramIntent.next();
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("listener ： ");
          ((StringBuilder)localObject2).append(localObject1);
          LogUtils.d("WifiEngine", ((StringBuilder)localObject2).toString());
          if (localObject1 != null) {
            ((WifiStateChangedListener)localObject1).onWifiStateChanged();
          }
        }
      }
    }
    else if (TextUtils.equals((CharSequence)localObject1, "android.net.wifi.SCAN_RESULTS"))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("SCAN_RESULTS_AVAILABLE_ACTION: ");
      ((StringBuilder)localObject1).append(System.currentTimeMillis());
      LogUtils.d("time-engine", ((StringBuilder)localObject1).toString());
      addDataRefrashPath("系统返回扫描结果");
      LogUtils.d("LIST_STABLE", "SCAN_RESULTS_AVAILABLE_ACTION");
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(System.currentTimeMillis());
      ((StringBuilder)localObject1).append(" : SCAN_RESULTS_AVAILABLE_ACTION");
      LogUtils.d("perform-test", ((StringBuilder)localObject1).toString());
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("onBroadcastReceiver() SCAN_RESULTS_AVAILABLE_ACTION curTime: ");
      ((StringBuilder)localObject1).append(System.currentTimeMillis());
      LogUtils.d("time-WifiEngine", ((StringBuilder)localObject1).toString());
      if (paramIntent.getBooleanExtra("resultsUpdated", true)) {
        updateScanRslt();
      }
      TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D_SCAN_CMPLT");
      paramIntent = getScanResults();
      localObject1 = this.mScanListenerList.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (ScanResultListener)((Iterator)localObject1).next();
        if (localObject2 != null) {
          ((ScanResultListener)localObject2).onScanResultReceived(paramIntent, this.mSkipCache);
        }
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("SCAN_RESULTS_AVAILABLE_ACTION MSG_LOCAL_SCAN_COMPLETED send: ");
      ((StringBuilder)localObject1).append(System.currentTimeMillis());
      LogUtils.d("time-engine", ((StringBuilder)localObject1).toString());
      this.mWorkerHandler.removeMessages(8);
      this.mWorkerHandler.obtainMessage(8).sendToTarget();
      this.mSkipCache = false;
      if ((paramIntent == null) || (paramIntent.size() == 0))
      {
        LogUtils.d("WifiEngine", "scanResults is null ");
        return;
      }
    }
  }
  
  public void onConnectCompleted(String paramString, int paramInt1, int paramInt2) {}
  
  public void onPullPwdCompleted(Object paramObject) {}
  
  public void onRequestCompleted(ArrayList<WifiApInfo> paramArrayList, int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onRequestCompleted[");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append("][");
    localStringBuilder.append(paramInt1);
    localStringBuilder.append("]");
    LogUtils.d("LIST_STABLE", localStringBuilder.toString());
    paramArrayList = this.mWorkerHandler.obtainMessage(5, paramArrayList);
    paramArrayList.arg1 = paramInt1;
    paramArrayList.arg2 = paramInt2;
    paramArrayList.sendToTarget();
  }
  
  public void onStateChange(WiFiConnectStateMachine.WiFiConnectState paramWiFiConnectState)
  {
    switch (paramWiFiConnectState.getIntStateType())
    {
    default: 
      
    case 6: 
      this.mConnectInfo = null;
      return;
    case 3: 
      WifiApInfo localWifiApInfo = this.mConnectInfo;
      if ((localWifiApInfo != null) && (TextUtils.equals(paramWiFiConnectState.getSsid(), localWifiApInfo.mSsid)))
      {
        this.mConnectInfo = null;
        HashMap localHashMap = new HashMap();
        localHashMap.put("wifi_ap_info", localWifiApInfo);
        localHashMap.put("state", paramWiFiConnectState);
        this.mWorkerHandler.obtainMessage(23, localHashMap).sendToTarget();
        return;
      }
      break;
    case 2: 
      paramWiFiConnectState = this.mConnectInfo;
      this.mWorkerHandler.obtainMessage(22, paramWiFiConnectState).sendToTarget();
    }
  }
  
  void removeExpiredFromFreeWifiCache()
  {
    if (!this.mFreeWifiCache.isEmpty())
    {
      long l = System.currentTimeMillis();
      Iterator localIterator = this.mFreeWifiCache.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        Object localObject1 = (String)this.mFreeWifiCache.get(str);
        if (TextUtils.isEmpty((CharSequence)localObject1))
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("removeExpiredFromFreeWifiCache() remove bssid: ");
          ((StringBuilder)localObject1).append(str);
          ((StringBuilder)localObject1).append(", value invalid");
          ((StringBuilder)localObject1).toString();
          this.mFreeWifiCache.remove(str);
        }
        else
        {
          Object localObject2 = ((String)localObject1).split("_");
          if ((localObject2 != null) && (localObject2.length == 2))
          {
            if (l - Long.valueOf(localObject2[1]).longValue() > this.MAX_ENTRY_LIFETIME)
            {
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append("removeExpiredFromFreeWifiCache() remove bssid: ");
              ((StringBuilder)localObject2).append(str);
              ((StringBuilder)localObject2).append(", value: ");
              ((StringBuilder)localObject2).append((String)localObject1);
              ((StringBuilder)localObject2).append(", expired");
              ((StringBuilder)localObject2).toString();
              this.mFreeWifiCache.remove(str);
            }
          }
          else
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("removeExpiredFromFreeWifiCache() remove bssid: ");
            ((StringBuilder)localObject1).append(str);
            ((StringBuilder)localObject1).append(", value invalid");
            ((StringBuilder)localObject1).toString();
            this.mFreeWifiCache.remove(str);
          }
        }
      }
    }
  }
  
  public void removePwdRetryStateListener(PwdRetryStateListener paramPwdRetryStateListener)
  {
    this.mPwdRetryStateListenerList.remove(paramPwdRetryStateListener);
  }
  
  public void removeScanResultListener(ScanResultListener paramScanResultListener)
  {
    this.mScanListenerList.remove(paramScanResultListener);
  }
  
  public void removeWifiDataChangedListener(WifiDataChangedListener paramWifiDataChangedListener)
  {
    this.mWifiDataChangedListenerList.remove(paramWifiDataChangedListener);
  }
  
  public void removeWifiStateChangedListener(WifiStateChangedListener paramWifiStateChangedListener)
  {
    this.mWifiStateChangedListenerList.remove(paramWifiStateChangedListener);
  }
  
  public void requestCloudPwd(List<ScanResult> paramList, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("requestCloudPwd[");
    localStringBuilder.append(paramBoolean);
    localStringBuilder.append("]");
    LogUtils.d("LIST_STABLE", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(System.currentTimeMillis());
    localStringBuilder.append(" : requestCloudPwd ");
    LogUtils.d("perform-test", localStringBuilder.toString());
    if (paramList != null)
    {
      if (paramList.size() == 0) {
        return;
      }
      if (needSync())
      {
        doRequestCloudPwd(paramList, paramBoolean);
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("requestCloudPwd() scanRslt.size: ");
      localStringBuilder.append(paramList.size());
      LogUtils.d("WifiEngine", localStringBuilder.toString());
      paramList = this.mWorkerHandler.obtainMessage(3, paramList);
      int i;
      if (paramBoolean) {
        i = 2;
      } else {
        i = 1;
      }
      paramList.arg1 = i;
      paramList.sendToTarget();
      return;
    }
  }
  
  public boolean scanWifi(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("scanWifi[");
    localStringBuilder.append(paramBoolean);
    localStringBuilder.append("][");
    localStringBuilder.append(this.mSkipCache);
    localStringBuilder.append("]");
    LogUtils.d("LIST_STABLE", localStringBuilder.toString());
    if (!this.mSkipCache) {
      this.mSkipCache = paramBoolean;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("scanWifi() curTime: ");
    localStringBuilder.append(System.currentTimeMillis());
    LogUtils.d("time-WifiEngine", localStringBuilder.toString());
    if (getWifiState() != 3)
    {
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF105");
      return false;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(System.currentTimeMillis());
    localStringBuilder.append(" : scanWifi ");
    LogUtils.d("perform-test", localStringBuilder.toString());
    try
    {
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF104");
      paramBoolean = this.mWifiManager.startScan();
      return paramBoolean;
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
      return false;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
    return false;
  }
  
  void updateFreeWifiCache(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return;
      }
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("updateFreeWifiCache() ssid: ");
      ((StringBuilder)localObject1).append(paramString1);
      ((StringBuilder)localObject1).append(", bssid: ");
      ((StringBuilder)localObject1).append(paramString2);
      ((StringBuilder)localObject1).toString();
      long l1 = System.currentTimeMillis();
      if (this.mFreeWifiCache.size() < this.MAX_ENTRY_COUNT)
      {
        localObject1 = this.mFreeWifiCache;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(paramString1);
        ((StringBuilder)localObject2).append("_");
        ((StringBuilder)localObject2).append(l1);
        ((Map)localObject1).put(paramString2, ((StringBuilder)localObject2).toString());
        return;
      }
      l1 = Long.MAX_VALUE;
      paramString1 = "";
      Object localObject2 = this.mFreeWifiCache.keySet().iterator();
      for (;;)
      {
        localObject1 = paramString1;
        if (!((Iterator)localObject2).hasNext()) {
          break label335;
        }
        localObject1 = (String)((Iterator)localObject2).next();
        Object localObject3 = (String)this.mFreeWifiCache.get(localObject1);
        if (TextUtils.isEmpty((CharSequence)localObject3))
        {
          paramString1 = new StringBuilder();
          paramString1.append("updateFreeWifiCache() remove bssid: ");
          paramString1.append((String)localObject1);
          paramString1.append(", value invalid");
          paramString1.toString();
          break label335;
        }
        localObject3 = ((String)localObject3).split("_");
        if ((localObject3 == null) || (localObject3.length != 2)) {
          break;
        }
        long l2 = Long.valueOf(localObject3[1]).longValue();
        if (l2 < l1)
        {
          paramString1 = paramString2;
          l1 = l2;
        }
      }
      paramString1 = new StringBuilder();
      paramString1.append("updateFreeWifiCache() remove bssid: ");
      paramString1.append((String)localObject1);
      paramString1.append(", value invalid");
      paramString1.toString();
      return;
      label335:
      this.mFreeWifiCache.remove(localObject1);
      return;
    }
  }
  
  public void updateServerTime(long paramLong)
  {
    try
    {
      LogUtils.d("WifiEngine", "updateServerTime");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("mServerTime : ");
      localStringBuilder.append(this.mServerTime);
      LogUtils.d("WifiEngine", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("mLocalTime : ");
      localStringBuilder.append(this.mLocalTime / 1000L);
      LogUtils.d("WifiEngine", localStringBuilder.toString());
      if (this.mServerTime < 0L)
      {
        this.mServerTime = paramLong;
        this.mLocalTime = SystemClock.elapsedRealtime();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private class Connector
    implements Handler.Callback, ISdkCallback
  {
    public static final int MSG_CONNECT = 4;
    public static final int MSG_FINISH = 2;
    public static final int MSG_NEXT = 1;
    public static final int MSG_PREPARE_PWD = 3;
    private static final String TAG = "Connector";
    private Handler mHandler = null;
    private int mMaxRetryTimes = 0;
    private int mRetryTimes = 0;
    public boolean mRunning = false;
    private ArrayList<WifiSdkBase> mSdkList = new ArrayList();
    private long mSdkStartTime = 0L;
    private long mStartTime = 0L;
    private WifiApInfo mTargetApInfo = null;
    private ArrayList<WifiApInfo> mTargetInfoList = null;
    private WifiSdkBase mTargetSdk = null;
    
    public Connector(WifiApInfo paramWifiApInfo)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("init : sdkList size[");
      localStringBuilder.append(paramWifiApInfo.size());
      localStringBuilder.append("] target [");
      WifiSdkBase localWifiSdkBase;
      localStringBuilder.append(localWifiSdkBase.mSsid);
      localStringBuilder.append("]");
      LogUtils.d("Connector", localStringBuilder.toString());
      this.mSdkList = paramWifiApInfo;
      this.mMaxRetryTimes = (paramWifiApInfo.size() * 1);
      paramWifiApInfo = new StringBuilder();
      paramWifiApInfo.append("[");
      paramWifiApInfo.append(localWifiSdkBase.mSsid);
      paramWifiApInfo.append("]");
      WifiEngine.this.addPath1(paramWifiApInfo.toString());
      if (this.mHandler == null) {
        this.mHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime(), this);
      }
      this.mTargetApInfo = localWifiSdkBase;
      LogUtils.d("Connector", "there are : ");
      paramWifiApInfo = this.mSdkList.iterator();
      while (paramWifiApInfo.hasNext())
      {
        localWifiSdkBase = (WifiSdkBase)paramWifiApInfo.next();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("find_sdk(");
        localStringBuilder.append(localWifiSdkBase.getId());
        localStringBuilder.append(")");
        WifiEngine.this.addPath1(localStringBuilder.toString());
        LogUtils.d("Connector", localWifiSdkBase.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("addCallback : ");
        localStringBuilder.append(localWifiSdkBase.toString());
        LogUtils.d("Connector", localStringBuilder.toString());
        localWifiSdkBase.addCallback(this);
      }
    }
    
    public boolean handleMessage(Message paramMessage)
    {
      LogUtils.d("Connector", "handleMessage");
      StringBuilder localStringBuilder;
      switch (paramMessage.what)
      {
      default: 
        return false;
      case 4: 
        LogUtils.d("Connector", "MSG_CONNECT");
        if ((paramMessage.obj instanceof ArrayList))
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("new target : ");
          localStringBuilder.append(this.mTargetSdk);
          LogUtils.d("Connector", localStringBuilder.toString());
          paramMessage = (ArrayList)paramMessage.obj;
          if ((paramMessage != null) && (this.mTargetSdk.enterRetryMod(paramMessage)))
          {
            paramMessage = WifiEngine.this;
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("start(");
            localStringBuilder.append(this.mTargetSdk.getId());
            localStringBuilder.append(")");
            paramMessage.addPath1(localStringBuilder.toString());
            this.mSdkStartTime = System.currentTimeMillis();
            LogUtils.d("Connector", "connecting");
            this.mTargetSdk.tryPwd(this.mRetryTimes - 1);
            return false;
          }
          paramMessage = WifiEngine.this;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("no_pwd(");
          localStringBuilder.append(this.mTargetSdk.getId());
          localStringBuilder.append(")");
          paramMessage.addPath1(localStringBuilder.toString());
          LogUtils.d("Connector", "no pwd");
          this.mHandler.removeMessages(1);
          this.mHandler.removeMessages(3);
          this.mHandler.sendEmptyMessageDelayed(1, 100L);
          return false;
        }
        paramMessage = WifiEngine.this;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("no_pwd(");
        localStringBuilder.append(this.mTargetSdk.getId());
        localStringBuilder.append(")");
        paramMessage.addPath1(localStringBuilder.toString());
        LogUtils.d("Connector", "no pwd");
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(3);
        this.mHandler.sendEmptyMessageDelayed(1, 100L);
        return false;
      case 3: 
        LogUtils.d("Connector", "MSG_PREPARE_PWD");
        if (((paramMessage.obj instanceof ArrayList)) && (this.mTargetSdk != null))
        {
          paramMessage = (ArrayList)paramMessage.obj;
          if (this.mTargetSdk.needPullPwd(paramMessage))
          {
            this.mTargetSdk.pullPwd(paramMessage);
            return false;
          }
          this.mHandler.obtainMessage(4, WifiEngine.this.mApInfoCacheForConnect.get(this.mTargetApInfo.mSsid)).sendToTarget();
          return false;
        }
        if (this.mTargetSdk != null)
        {
          paramMessage = WifiEngine.this;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("no_pwd(");
          localStringBuilder.append(this.mTargetSdk.getId());
          localStringBuilder.append(")");
          paramMessage.addPath1(localStringBuilder.toString());
        }
        LogUtils.d("Connector", "no pwd");
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(3);
        this.mHandler.sendEmptyMessageDelayed(1, 100L);
        return false;
      case 2: 
        this.mHandler.removeMessages(2);
        LogUtils.d("Connector", "MSG_FINISH");
        WifiEngine.this.doFinishTrying(this.mTargetApInfo.mSsid, true, false);
        return false;
      }
      LogUtils.d("Connector", "MSG_NEXT");
      LogUtils.d("Connector", "MSG_NEXT");
      if (!this.mRunning)
      {
        LogUtils.d("Connector", "quiting?");
        return false;
      }
      if ((System.currentTimeMillis() - this.mStartTime <= WifiEngine.this.mMaxTryingTime) && (this.mRetryTimes < this.mMaxRetryTimes))
      {
        if (this.mTargetSdk != null)
        {
          paramMessage = new StringBuilder();
          paramMessage.append("last target : ");
          paramMessage.append(this.mTargetSdk);
          LogUtils.d("Connector", paramMessage.toString());
          this.mTargetSdk.exitRetryMod(2);
        }
        paramMessage = new StringBuilder();
        paramMessage.append("mRetryTimes : ");
        paramMessage.append(this.mRetryTimes);
        LogUtils.d("Connector", paramMessage.toString());
        paramMessage = this.mSdkList;
        this.mTargetSdk = ((WifiSdkBase)paramMessage.get(this.mRetryTimes % paramMessage.size()));
        this.mRetryTimes += 1;
        this.mTargetInfoList = ((ArrayList)WifiEngine.this.mApInfoCacheForConnect.get(this.mTargetApInfo.mSsid));
        this.mHandler.obtainMessage(3, this.mTargetInfoList).sendToTarget();
        return false;
      }
      LogUtils.d("Connector", "over time??");
      this.mHandler.removeMessages(2);
      this.mHandler.sendEmptyMessageDelayed(2, 100L);
      return false;
    }
    
    public void onConnectCompleted(String paramString, int paramInt1, int paramInt2)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onConnectCompleted : ssid[");
      localStringBuilder.append(paramString);
      localStringBuilder.append("] rslt [");
      localStringBuilder.append(paramInt1);
      localStringBuilder.append("] sdkId [");
      localStringBuilder.append(paramInt2);
      localStringBuilder.append("]");
      LogUtils.d("Connector", localStringBuilder.toString());
      paramString = this.mTargetSdk;
      if ((paramString != null) && (paramString.getId() == paramInt2))
      {
        paramString = WifiEngine.this;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("rslt(");
        localStringBuilder.append(paramInt1);
        localStringBuilder.append(")<");
        localStringBuilder.append(paramInt2);
        localStringBuilder.append(">{");
        localStringBuilder.append((System.currentTimeMillis() - this.mSdkStartTime) / 1000L);
        localStringBuilder.append("}");
        paramString.addPath1(localStringBuilder.toString());
        this.mSdkStartTime = 0L;
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(3);
        if (paramInt1 != 0)
        {
          this.mHandler.sendEmptyMessageDelayed(1, 100L);
          return;
        }
        this.mHandler.removeMessages(2);
        this.mHandler.sendEmptyMessage(2);
        return;
      }
      LogUtils.d("Connector", "not currunt sdk!");
    }
    
    public void onPullPwdCompleted(Object paramObject)
    {
      LogUtils.d("Connector", "onPullPwdCompleted");
      if ((paramObject != null) && (paramObject == this.mTargetInfoList))
      {
        this.mHandler.removeMessages(4);
        this.mHandler.obtainMessage(4, this.mTargetInfoList).sendToTarget();
      }
    }
    
    public void onRequestCompleted(ArrayList<WifiApInfo> paramArrayList, int paramInt1, int paramInt2) {}
    
    public void quit(int paramInt)
    {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(3);
      this.mHandler.removeMessages(2);
      LogUtils.d("Connector", "quit");
      if (this.mTargetSdk != null)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("mTargetSdk : ");
        ((StringBuilder)localObject1).append(this.mTargetSdk);
        LogUtils.d("Connector", ((StringBuilder)localObject1).toString());
        this.mTargetSdk.exitRetryMod(paramInt);
      }
      Object localObject1 = this.mSdkList.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (WifiSdkBase)((Iterator)localObject1).next();
        ((WifiSdkBase)localObject2).removeCallback(this);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("removeCallback : ");
        localStringBuilder.append(localObject2.toString());
        LogUtils.d("Connector", localStringBuilder.toString());
      }
      localObject1 = WifiEngine.this;
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("quit{");
      ((StringBuilder)localObject2).append((System.currentTimeMillis() - this.mStartTime) / 1000L);
      ((StringBuilder)localObject2).append("}");
      ((WifiEngine)localObject1).addPath1(((StringBuilder)localObject2).toString());
    }
    
    public void start()
    {
      LogUtils.d("Connector", "start");
      this.mStartTime = System.currentTimeMillis();
      this.mRunning = true;
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(3);
      this.mHandler.removeMessages(2);
      this.mHandler.sendEmptyMessageDelayed(1, 0L);
      this.mHandler.sendEmptyMessageDelayed(2, WifiEngine.this.mMaxTryingTime);
    }
  }
  
  public static abstract interface PwdRetryStateListener
  {
    public abstract void onRetryEnd();
    
    public abstract void onRetryStart();
  }
  
  public static abstract interface ScanResultListener
  {
    public abstract void onScanResultReceived(List<ScanResult> paramList, boolean paramBoolean);
  }
  
  public static abstract interface WifiDataChangedListener
  {
    public abstract void onCloudPwdRecievd();
    
    public abstract void onListDataChanged(ArrayList<WifiApInfo> paramArrayList);
  }
  
  public static abstract interface WifiStateChangedListener
  {
    public abstract void onWifiStateChanged();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\WifiEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */