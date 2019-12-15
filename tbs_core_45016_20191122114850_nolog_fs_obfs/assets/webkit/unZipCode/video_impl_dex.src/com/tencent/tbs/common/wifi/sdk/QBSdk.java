package com.tencent.tbs.common.wifi.sdk;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.MTT.ZeusQRI;
import com.tencent.tbs.common.MTT.ZeusQRS;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.wifi.WifiApInfo;
import com.tencent.tbs.common.wifi.WifiBlocker;
import com.tencent.tbs.common.wifi.WifiConnector;
import com.tencent.tbs.common.wifi.WifiEngine;
import com.tencent.tbs.common.wifi.WifiUtils;
import com.tencent.tbs.common.wifi.WifiWupRequester;
import com.tencent.tbs.common.wifi.WifiWupRequester.KeyInfo;
import com.tencent.tbs.common.wifi.WifiWupRequester.WifiKeyQureyRspInfo;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine.WiFiConnectState;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QBSdk
  extends WifiSdkBase
  implements IWUPRequestCallBack
{
  public static final String LAST_CONNECT_TIME = "wifi_last_auto_connect_time";
  public static final int PWD_REQ_TYPE = 2;
  public static final String TAG = "QBSdk";
  final int MSG_DATA_RECIEVED = 1;
  private String mAppID = "a";
  private String mAppIDSecret = "b";
  ConcurrentLinkedQueue<WifiWupRequester.KeyInfo> mPaddingKeyInfo = new ConcurrentLinkedQueue();
  long mStartConnectTime = 0L;
  WifiBlocker mWifiBlocker = WifiBlocker.getInstance();
  private WifiManager mWifiManager = null;
  WifiWupRequester mWupRequester = new WifiWupRequester();
  
  public QBSdk(Looper paramLooper)
  {
    super(paramLooper);
    this.mWupRequester.mCallBack = this;
  }
  
  private ArrayList<WifiApInfo> formatScanResult2WifiApInfo(List<ScanResult> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      ScanResult localScanResult = (ScanResult)paramList.next();
      WifiApInfo localWifiApInfo = new WifiApInfo();
      localWifiApInfo.mBssid = localScanResult.BSSID;
      localWifiApInfo.mSsid = localScanResult.SSID;
      localWifiApInfo.mSafeType = WifiUtils.getSecurity(localScanResult);
      localWifiApInfo.mLevel = localScanResult.level;
      localWifiApInfo.mFrequency = localScanResult.frequency;
      localWifiApInfo.mSdkId = getId();
      localArrayList.add(localWifiApInfo);
    }
    return localArrayList;
  }
  
  private ArrayList<WifiWupRequester.WifiKeyQureyRspInfo> processRsp(ZeusQRS paramZeusQRS)
  {
    WifiEngine.getInstance().updateServerTime(paramZeusQRS.t);
    ArrayList localArrayList = new ArrayList();
    Object localObject1 = paramZeusQRS.vQ;
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("rspList ： ");
    ((StringBuilder)localObject2).append(localObject1);
    LogUtils.d("QBSdk", ((StringBuilder)localObject2).toString());
    if (localObject1 != null)
    {
      localObject1 = ((ArrayList)localObject1).iterator();
      while (((Iterator)localObject1).hasNext()) {
        localArrayList.add(new WifiWupRequester.WifiKeyQureyRspInfo((ZeusQRI)((Iterator)localObject1).next()));
      }
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("rsp.vO ： ");
    ((StringBuilder)localObject1).append(paramZeusQRS.vO);
    LogUtils.d("QBSdk", ((StringBuilder)localObject1).toString());
    StringBuilder localStringBuilder;
    if (paramZeusQRS.vO != null)
    {
      localObject1 = paramZeusQRS.vO.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = new WifiWupRequester.WifiKeyQureyRspInfo((ZeusQRI)((Iterator)localObject1).next());
        localArrayList.add(localObject2);
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("open ");
        localStringBuilder.append(((WifiWupRequester.WifiKeyQureyRspInfo)localObject2).ssid);
        localStringBuilder.append(" | ");
        localStringBuilder.append(((WifiWupRequester.WifiKeyQureyRspInfo)localObject2).bssid);
        localStringBuilder.append(" | ");
        localStringBuilder.append(((WifiWupRequester.WifiKeyQureyRspInfo)localObject2).acitonType);
        LogUtils.d("QBSdk", localStringBuilder.toString());
      }
    }
    localObject1 = paramZeusQRS.vF;
    if (localObject1 != null)
    {
      localObject1 = ((ArrayList)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (ZeusQRI)((Iterator)localObject1).next();
        if (!this.mWifiBlocker.isBlocked(((ZeusQRI)localObject2).s))
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("block ");
          localStringBuilder.append(((ZeusQRI)localObject2).s);
          localStringBuilder.append(" by server");
          LogUtils.d("normanchen", localStringBuilder.toString());
          this.mWifiBlocker.block(((ZeusQRI)localObject2).s, false);
        }
      }
    }
    paramZeusQRS = paramZeusQRS.sdkBlackList;
    if (paramZeusQRS != null)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("----====sdkBlackList size:");
      ((StringBuilder)localObject1).append(paramZeusQRS.size());
      LogUtils.d("black_list", ((StringBuilder)localObject1).toString());
      paramZeusQRS = paramZeusQRS.iterator();
      while (paramZeusQRS.hasNext())
      {
        localObject1 = (ZeusQRI)paramZeusQRS.next();
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("[");
        ((StringBuilder)localObject2).append(((ZeusQRI)localObject1).s);
        ((StringBuilder)localObject2).append("][");
        ((StringBuilder)localObject2).append(((ZeusQRI)localObject1).b);
        ((StringBuilder)localObject2).append("][");
        ((StringBuilder)localObject2).append(((ZeusQRI)localObject1).from);
        ((StringBuilder)localObject2).append("]");
        LogUtils.d("black_list", ((StringBuilder)localObject2).toString());
        if (!this.mWifiBlocker.checkBlocked(((ZeusQRI)localObject1).s, ((ZeusQRI)localObject1).from)) {
          this.mWifiBlocker.block(((ZeusQRI)localObject1).s, ((ZeusQRI)localObject1).from);
        }
      }
    }
    return localArrayList;
  }
  
  public int connect(WifiApInfo paramWifiApInfo)
  {
    LogUtils.d("QBSdk", "connect");
    if ((paramWifiApInfo != null) && (!TextUtils.isEmpty(paramWifiApInfo.mBssid)) && (!TextUtils.isEmpty(paramWifiApInfo.mSsid)))
    {
      Object localObject1 = this.mRetryPwdList;
      if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0) && (this.mWrongPwdList.size() != ((ArrayList)localObject1).size()))
      {
        int i = this.mPwdIndex;
        this.mPwdIndex = (i + 1);
        Object localObject2 = (WifiWupRequester.WifiKeyQureyRspInfo)((ArrayList)localObject1).get(i % ((ArrayList)localObject1).size());
        LogUtils.d("normanchen", "cur pwd : ");
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("SSID [");
        ((StringBuilder)localObject1).append(((WifiWupRequester.WifiKeyQureyRspInfo)localObject2).ssid);
        ((StringBuilder)localObject1).append("]BSSID [");
        ((StringBuilder)localObject1).append(((WifiWupRequester.WifiKeyQureyRspInfo)localObject2).bssid);
        ((StringBuilder)localObject1).append("]PWD [");
        ((StringBuilder)localObject1).append(((WifiWupRequester.WifiKeyQureyRspInfo)localObject2).pwd);
        ((StringBuilder)localObject1).append("]");
        LogUtils.d("QBSdk", ((StringBuilder)localObject1).toString());
        localObject1 = new WifiApInfo();
        ((WifiApInfo)localObject1).mBssid = paramWifiApInfo.mBssid;
        ((WifiApInfo)localObject1).mPassword = ((WifiWupRequester.WifiKeyQureyRspInfo)localObject2).pwd;
        ((WifiApInfo)localObject1).mSsid = paramWifiApInfo.mSsid;
        ((WifiApInfo)localObject1).mSafeType = paramWifiApInfo.mSafeType;
        int j = 1;
        ((WifiApInfo)localObject1).mForceNewConfig = true;
        paramWifiApInfo = this.mWrongPwdList;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(((WifiApInfo)localObject1).mSsid);
        ((StringBuilder)localObject2).append("|");
        ((StringBuilder)localObject2).append(((WifiApInfo)localObject1).mBssid);
        ((StringBuilder)localObject2).append("|");
        ((StringBuilder)localObject2).append(((WifiApInfo)localObject1).mPassword);
        i = j;
        if (!paramWifiApInfo.contains(((StringBuilder)localObject2).toString()))
        {
          this.mConnecttingApInfo = ((WifiApInfo)localObject1);
          LogUtils.d("QBSdk_WifiPathRecorder", "connect() fillConnPwdApRecordWithFrom");
          i = j;
          if (this.mCurWifiConnector.connect((WifiApInfo)localObject1))
          {
            LogUtils.d("normanchen", "do connect success");
            i = 0;
          }
        }
        if (i == 0)
        {
          LogUtils.d("QBSdk", "DO_CONNECT_SUCCESS");
          this.mStartConnectTime = System.currentTimeMillis();
          return 0;
        }
        LogUtils.d("normanchen", "pwd in blackList ");
        if (this.mTryIndex == 0) {
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D_QB_ER_1");
        }
      }
      else
      {
        LogUtils.d("QBSdk", "all pwd wrong");
        if (this.mTryIndex == 0) {
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D_QB_ER_2");
        }
      }
      this.mStartConnectTime = 0L;
      paramWifiApInfo = new StringBuilder();
      paramWifiApInfo.append("mConnecttingApInfo : ");
      paramWifiApInfo.append(this.mConnecttingApInfo);
      LogUtils.d("QBSdk", paramWifiApInfo.toString());
      return -1;
    }
    return -2;
  }
  
  public int getId()
  {
    return 2;
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    if (paramMessage.what != 1) {
      return true;
    }
    LogUtils.d("QBSdk", "MSG_DATA_RECIEVED");
    Object localObject;
    if ((paramMessage.obj instanceof BindObj))
    {
      paramMessage = (BindObj)paramMessage.obj;
      updateCache(paramMessage);
      localObject = this.mSdkCallbacks.iterator();
      while (((Iterator)localObject).hasNext()) {
        ((ISdkCallback)((Iterator)localObject).next()).onPullPwdCompleted(paramMessage.mReqList);
      }
    }
    if ((paramMessage.obj instanceof WifiSdkBase.RqdAndRspInfo))
    {
      paramMessage = (WifiSdkBase.RqdAndRspInfo)paramMessage.obj;
      updateCache(paramMessage.mRsp, paramMessage.mReqToServer);
      notifyPwdGetted(paramMessage.mReqFromUI, paramMessage.mReqNum);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("密码获取成功2:sdk[");
      ((StringBuilder)localObject).append(getId());
      ((StringBuilder)localObject).append("]序号[");
      ((StringBuilder)localObject).append(paramMessage.mReqNum);
      ((StringBuilder)localObject).append("]");
      WifiEngine.addDataRefrashPath(((StringBuilder)localObject).toString());
      return true;
    }
    LogUtils.e("QBSdk", "不可能走到这个分支！");
    if (this.mSdkCallbacks != null)
    {
      paramMessage = this.mSdkCallbacks.iterator();
      while (paramMessage.hasNext()) {
        ((ISdkCallback)paramMessage.next()).onRequestCompleted(null, getId(), -100);
      }
    }
    paramMessage = new StringBuilder();
    paramMessage.append("密码获取失败3:sdk[");
    paramMessage.append(getId());
    paramMessage.append("]序号[");
    paramMessage.append(-100);
    paramMessage.append("]");
    WifiEngine.addDataRefrashPath(paramMessage.toString());
    return true;
  }
  
  public boolean isForceUse(WifiApInfo paramWifiApInfo)
  {
    paramWifiApInfo = this.mPasswordCache.get(paramWifiApInfo.mBssid);
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramWifiApInfo != null)
    {
      paramWifiApInfo = paramWifiApInfo.mPasswordList;
      bool1 = bool2;
      if (paramWifiApInfo != null)
      {
        bool1 = bool2;
        if (!paramWifiApInfo.isEmpty()) {
          bool1 = ((WifiWupRequester.WifiKeyQureyRspInfo)paramWifiApInfo.get(0)).force;
        }
      }
    }
    return bool1;
  }
  
  public boolean needPullPwd(ArrayList<WifiApInfo> paramArrayList)
  {
    boolean bool2 = false;
    boolean bool1 = false;
    if (paramArrayList != null)
    {
      paramArrayList = paramArrayList.iterator();
      do
      {
        bool2 = bool1;
        if (!paramArrayList.hasNext()) {
          break;
        }
        Object localObject = (WifiApInfo)paramArrayList.next();
        localObject = this.mPasswordCache.get(((WifiApInfo)localObject).mBssid);
        if (localObject == null) {
          return true;
        }
        localObject = ((WifiSdkBase.Cache)localObject).mPasswordList.iterator();
        WifiWupRequester.WifiKeyQureyRspInfo localWifiKeyQureyRspInfo;
        do
        {
          bool2 = bool1;
          if (!((Iterator)localObject).hasNext()) {
            break;
          }
          localWifiKeyQureyRspInfo = (WifiWupRequester.WifiKeyQureyRspInfo)((Iterator)localObject).next();
        } while ((!localWifiKeyQureyRspInfo.hasPwd) || (!TextUtils.isEmpty(localWifiKeyQureyRspInfo.pwd)));
        bool2 = true;
        bool1 = bool2;
      } while (!bool2);
    }
    return bool2;
  }
  
  public void onPwdError(final String paramString)
  {
    Object localObject = this.mConnecttingApInfo;
    if ((localObject != null) && (TextUtils.equals(paramString, ((WifiApInfo)localObject).mSsid)))
    {
      LogUtils.d("QBSdk", "pwd INVALID");
      paramString = new StringBuilder();
      paramString.append("copyInfo : ");
      paramString.append(((WifiApInfo)localObject).toString());
      LogUtils.d("QBSdk", paramString.toString());
      paramString = new StringBuilder();
      paramString.append("pwd error -> SSID[");
      paramString.append(((WifiApInfo)localObject).mSsid);
      paramString.append("],pwd[");
      paramString.append(((WifiApInfo)localObject).mPassword);
      paramString.append("] ");
      LogUtils.d("normanchen", paramString.toString());
      paramString = new WifiWupRequester.KeyInfo();
      paramString.bssid = ((WifiApInfo)localObject).mBssid;
      paramString.ssid = ((WifiApInfo)localObject).mSsid;
      paramString.pwdAvialble = -1;
      paramString.type = ((WifiApInfo)localObject).mSafeType;
      paramString.from = 2L;
      paramString.password = ((WifiApInfo)localObject).mPassword;
      paramString.actionTime = WifiEngine.getInstance().getServerTime();
      this.mPaddingKeyInfo.add(paramString);
      paramString = new ArrayList(this.mPaddingKeyInfo);
      this.mPaddingKeyInfo.clear();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(((WifiApInfo)localObject).mSsid);
      localStringBuilder.append("|");
      localStringBuilder.append(((WifiApInfo)localObject).mBssid);
      localStringBuilder.append("|");
      localStringBuilder.append(((WifiApInfo)localObject).mPassword);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("wrong pwd  -> ");
      ((StringBuilder)localObject).append(localStringBuilder);
      LogUtils.d("QBSdk", ((StringBuilder)localObject).toString());
      this.mWrongPwdList.add(localStringBuilder.toString());
      BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          WifiWupRequester localWifiWupRequester = QBSdk.this.mWupRequester;
          ArrayList localArrayList = paramString;
          localWifiWupRequester.reportWifiInfo(localArrayList, localArrayList, 0, false);
        }
      });
    }
  }
  
  public void onStateChange(final WiFiConnectStateMachine.WiFiConnectState paramWiFiConnectState)
  {
    final WifiApInfo localWifiApInfo;
    switch (paramWiFiConnectState.getIntStateType())
    {
    default: 
      
    case 6: 
      localWifiApInfo = this.mConnecttingApInfo;
      if ((localWifiApInfo != null) && (TextUtils.equals(paramWiFiConnectState.getSsid(), localWifiApInfo.mSsid)))
      {
        this.mConnecttingApInfo = null;
        if (this.mTryIndex == 0)
        {
          paramWiFiConnectState = new StringBuilder();
          paramWiFiConnectState.append("SSID : ");
          paramWiFiConnectState.append(localWifiApInfo.mSsid);
          paramWiFiConnectState.append(" | QB 用户断开连接");
          LogUtils.d("Wifi-PV", paramWiFiConnectState.toString());
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-QB-DCU-1");
          return;
        }
      }
      break;
    case 3: 
      localWifiApInfo = this.mConnecttingApInfo;
      if ((localWifiApInfo != null) && (TextUtils.equals(paramWiFiConnectState.getSsid(), localWifiApInfo.mSsid)))
      {
        this.mConnecttingApInfo = null;
        if (this.mTryIndex == 0)
        {
          paramWiFiConnectState = new StringBuilder();
          paramWiFiConnectState.append("SSID : ");
          paramWiFiConnectState.append(localWifiApInfo.mSsid);
          paramWiFiConnectState.append(" | QB 连接失败!");
          LogUtils.d("Wifi-PV", paramWiFiConnectState.toString());
        }
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
      if ((localWifiApInfo != null) && (TextUtils.equals(paramWiFiConnectState.getSsid(), localWifiApInfo.mSsid)))
      {
        this.mConnecttingApInfo = null;
        LogUtils.d("QBSdk", "pwd VALID");
        paramWiFiConnectState = new StringBuilder();
        paramWiFiConnectState.append("copyInfo : ");
        paramWiFiConnectState.append(localWifiApInfo.toString());
        LogUtils.d("QBSdk", paramWiFiConnectState.toString());
        paramWiFiConnectState = new WifiWupRequester.KeyInfo();
        paramWiFiConnectState.bssid = localWifiApInfo.mBssid;
        paramWiFiConnectState.ssid = localWifiApInfo.mSsid;
        paramWiFiConnectState.pwdAvialble = 1;
        paramWiFiConnectState.type = localWifiApInfo.mSafeType;
        paramWiFiConnectState.from = 2L;
        paramWiFiConnectState.password = localWifiApInfo.mPassword;
        paramWiFiConnectState.actionTime = WifiEngine.getInstance().getServerTime();
        this.mPaddingKeyInfo.add(paramWiFiConnectState);
        paramWiFiConnectState = new ArrayList(this.mPaddingKeyInfo);
        this.mPaddingKeyInfo.clear();
        if (this.mTryIndex == 0)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("SSID : ");
          localStringBuilder.append(localWifiApInfo.mSsid);
          localStringBuilder.append(" | QB 连接成功");
          LogUtils.d("Wifi-PV", localStringBuilder.toString());
        }
        BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
        {
          public void doRun()
          {
            WifiWupRequester localWifiWupRequester = QBSdk.this.mWupRequester;
            ArrayList localArrayList = paramWiFiConnectState;
            localWifiWupRequester.reportWifiInfo(localArrayList, localArrayList, 0, false);
          }
        });
        return;
      }
      break;
    case 0: 
    case 1: 
    case 4: 
    case 5: 
      localWifiApInfo = this.mConnecttingApInfo;
      if ((localWifiApInfo != null) && (!TextUtils.equals(paramWiFiConnectState.getSsid(), localWifiApInfo.mSsid)))
      {
        LogUtils.d("QBSdk", "mConnecttingApInfo is not avilable!");
        this.mConnecttingApInfo = null;
        if ((this.mStartConnectTime > 0L) && (this.mTryIndex == 0))
        {
          long l = System.currentTimeMillis() - this.mStartConnectTime;
          this.mStartConnectTime = 0L;
          if (l < 1000L) {
            TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-QB-CG-TIME-1");
          } else if (l < 5000L) {
            TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-QB-CG-TIME-5");
          } else if (l < 10000L) {
            TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-QB-CG-TIME-10");
          } else if (l < 30000L) {
            TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-QB-CG-TIME-30");
          } else {
            TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-QB-CG-TIME-OV-30");
          }
        }
        else
        {
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-QB-CG-TIME-E");
        }
        paramWiFiConnectState = new StringBuilder();
        paramWiFiConnectState.append("SSID : ");
        paramWiFiConnectState.append(localWifiApInfo.mSsid);
        paramWiFiConnectState.append(" | QB ap被切换");
        LogUtils.d("Wifi-PV", paramWiFiConnectState.toString());
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-QB-CG-1");
      }
      break;
    }
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("");
    ((StringBuilder)localObject).append(paramWUPRequestBase.getFuncName());
    ((StringBuilder)localObject).append(" Failed!");
    LogUtils.d("QBSdk", ((StringBuilder)localObject).toString());
    if (paramWUPRequestBase != null)
    {
      paramWUPRequestBase = paramWUPRequestBase.getBindObject();
      if ((paramWUPRequestBase instanceof BindObj))
      {
        paramWUPRequestBase = (BindObj)paramWUPRequestBase;
        localObject = this.mSdkCallbacks.iterator();
        while (((Iterator)localObject).hasNext()) {
          ((ISdkCallback)((Iterator)localObject).next()).onPullPwdCompleted(paramWUPRequestBase.mReqList);
        }
      }
      if ((paramWUPRequestBase instanceof WifiSdkBase.RqdAndRspInfo))
      {
        paramWUPRequestBase = (WifiSdkBase.RqdAndRspInfo)paramWUPRequestBase;
        if (this.mSdkCallbacks != null)
        {
          localObject = this.mSdkCallbacks.iterator();
          while (((Iterator)localObject).hasNext()) {
            ((ISdkCallback)((Iterator)localObject).next()).onRequestCompleted(null, getId(), paramWUPRequestBase.mReqNum);
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("密码获取失败2:sdk[");
          ((StringBuilder)localObject).append(getId());
          ((StringBuilder)localObject).append("]序号[");
          ((StringBuilder)localObject).append(paramWUPRequestBase.mReqNum);
          ((StringBuilder)localObject).append("]");
          WifiEngine.addDataRefrashPath(((StringBuilder)localObject).toString());
        }
      }
      else if ((paramWUPRequestBase instanceof ArrayList))
      {
        this.mPaddingKeyInfo.addAll((ArrayList)paramWUPRequestBase);
      }
    }
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(System.currentTimeMillis());
    ((StringBuilder)localObject1).append(" : onWUPTaskSuccess");
    LogUtils.d("perform-test", ((StringBuilder)localObject1).toString());
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("onWUPTaskSuccess");
    ((StringBuilder)localObject1).append(paramWUPRequestBase.getFuncName());
    ((StringBuilder)localObject1).append(" Success!");
    LogUtils.d("QBSdk", ((StringBuilder)localObject1).toString());
    if (paramWUPRequestBase != null)
    {
      Object localObject2 = paramWUPRequestBase.getBindObject();
      if ((localObject2 instanceof BindObj))
      {
        localObject1 = null;
        Integer localInteger = paramWUPResponseBase.getReturnCode();
        paramWUPRequestBase = (WUPRequestBase)localObject1;
        if (localInteger != null)
        {
          paramWUPRequestBase = (WUPRequestBase)localObject1;
          if (localInteger.intValue() == 0)
          {
            paramWUPResponseBase = (ZeusQRS)paramWUPResponseBase.get("stRS");
            paramWUPRequestBase = (WUPRequestBase)localObject1;
            if (paramWUPResponseBase != null)
            {
              paramWUPRequestBase = (WUPRequestBase)localObject1;
              if (paramWUPResponseBase.code == 0) {
                paramWUPRequestBase = processRsp(paramWUPResponseBase);
              }
            }
          }
        }
        paramWUPResponseBase = (BindObj)localObject2;
        paramWUPResponseBase.mRspList = paramWUPRequestBase;
        this.mWorkerHandler.obtainMessage(1, paramWUPResponseBase).sendToTarget();
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF108");
      }
      else if ((localObject2 instanceof WifiSdkBase.RqdAndRspInfo))
      {
        localObject1 = (WifiSdkBase.RqdAndRspInfo)localObject2;
        localObject2 = paramWUPResponseBase.getReturnCode();
        if ((localObject2 != null) && (((Integer)localObject2).intValue() == 0))
        {
          paramWUPResponseBase = (ZeusQRS)paramWUPResponseBase.get("stRS");
          if ((paramWUPResponseBase != null) && (paramWUPResponseBase.code == 0))
          {
            paramWUPRequestBase = processRsp(paramWUPResponseBase);
            paramWUPResponseBase = new StringBuilder();
            paramWUPResponseBase.append("keyInfos size ： ");
            paramWUPResponseBase.append(paramWUPRequestBase.size());
            LogUtils.d("QBSdk", paramWUPResponseBase.toString());
            ((WifiSdkBase.RqdAndRspInfo)localObject1).mRsp = paramWUPRequestBase;
            this.mWorkerHandler.obtainMessage(1, localObject1).sendToTarget();
          }
          else
          {
            onWUPTaskFail(paramWUPRequestBase);
          }
        }
        else
        {
          onWUPTaskFail(paramWUPRequestBase);
        }
      }
    }
    if (!this.mPaddingKeyInfo.isEmpty())
    {
      paramWUPRequestBase = new ArrayList(this.mPaddingKeyInfo);
      this.mWupRequester.reportWifiInfo(paramWUPRequestBase, paramWUPRequestBase, 0, false);
      this.mPaddingKeyInfo.clear();
    }
  }
  
  public void pullPwd(ArrayList<WifiApInfo> paramArrayList)
  {
    BindObj localBindObj = new BindObj();
    localBindObj.mReqList = paramArrayList;
    this.mWupRequester.qureyPassword(paramArrayList, 0, localBindObj);
  }
  
  public void requestCloudWifi(List<ScanResult> paramList, boolean paramBoolean, int paramInt)
  {
    LogUtils.d("QBSdk", "requestCloudWifi");
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("mAvailableTime ： ");
    ((StringBuilder)localObject).append(this.mAvailableTime);
    LogUtils.d("QBSdk", ((StringBuilder)localObject).toString());
    if (!paramList.isEmpty())
    {
      trimCache(paramList);
      paramList = checkCache(paramList, paramBoolean);
      paramList.mReqNum = paramInt;
      if (paramList.mReqToServer.isEmpty())
      {
        notifyPwdGetted(paramList.mReqFromUI, paramList.mReqNum);
        LogUtils.d("QBSdk", "all req find from cache");
        paramList = new StringBuilder();
        paramList.append("密码获取成功1:sdk[");
        paramList.append(getId());
        paramList.append("]序号[");
        paramList.append(paramInt);
        paramList.append("]请求列表空");
        WifiEngine.addDataRefrashPath(paramList.toString());
        return;
      }
      localObject = formatScanResult2WifiApInfo(paramList.mReqToServer);
      this.mWupRequester.qureyPassword((ArrayList)localObject, 2, paramList);
      return;
    }
    if (this.mSdkCallbacks != null)
    {
      paramList = this.mSdkCallbacks.iterator();
      while (paramList.hasNext()) {
        ((ISdkCallback)paramList.next()).onRequestCompleted(null, getId(), paramInt);
      }
    }
    paramList = new StringBuilder();
    paramList.append("密码获取失败1:sdk[");
    paramList.append(getId());
    paramList.append("]序号[");
    paramList.append(paramInt);
    paramList.append("]");
    WifiEngine.addDataRefrashPath(paramList.toString());
  }
  
  protected void updateCache(BindObj paramBindObj)
  {
    LogUtils.d("QBSdk", "updateCache");
    if (paramBindObj == null) {
      return;
    }
    Object localObject1 = paramBindObj.mReqList;
    if (localObject1 != null)
    {
      if (((ArrayList)localObject1).isEmpty()) {
        return;
      }
      long l = System.currentTimeMillis();
      Object localObject3 = ((ArrayList)localObject1).iterator();
      Object localObject2;
      while (((Iterator)localObject3).hasNext())
      {
        WifiApInfo localWifiApInfo = (WifiApInfo)((Iterator)localObject3).next();
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("scanResult : ");
        ((StringBuilder)localObject1).append(localWifiApInfo);
        LogUtils.d("QBSdk", ((StringBuilder)localObject1).toString());
        localObject2 = this.mPasswordCache.get(localWifiApInfo.mBssid);
        localObject1 = localObject2;
        if (localObject2 == null)
        {
          localObject1 = new WifiSdkBase.Cache(this);
          ((WifiSdkBase.Cache)localObject1).mSsid = localWifiApInfo.mSsid;
          ((WifiSdkBase.Cache)localObject1).mBssid = localWifiApInfo.mBssid;
          this.mPasswordCache.put(localWifiApInfo.mBssid, (WifiSdkBase.Cache)localObject1);
        }
        ((WifiSdkBase.Cache)localObject1).mIsDirty = false;
        if (((WifiSdkBase.Cache)localObject1).mPasswordList != null) {
          ((WifiSdkBase.Cache)localObject1).mPasswordList.clear();
        }
        ((WifiSdkBase.Cache)localObject1).updateTime = l;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("updateCache() update timestamp ssid: ");
        ((StringBuilder)localObject2).append(((WifiSdkBase.Cache)localObject1).mSsid);
        ((StringBuilder)localObject2).append(" bssid : ");
        ((StringBuilder)localObject2).append(((WifiSdkBase.Cache)localObject1).mBssid);
        ((StringBuilder)localObject2).append(" t :");
        ((StringBuilder)localObject2).append(l);
        LogUtils.d("QBSdk", ((StringBuilder)localObject2).toString());
      }
      paramBindObj = paramBindObj.mRspList;
      if ((paramBindObj != null) && (!paramBindObj.isEmpty()))
      {
        localObject2 = paramBindObj.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (WifiWupRequester.WifiKeyQureyRspInfo)((Iterator)localObject2).next();
          paramBindObj = new StringBuilder();
          paramBindObj.append("ssid : ");
          paramBindObj.append(((WifiWupRequester.WifiKeyQureyRspInfo)localObject3).ssid);
          LogUtils.d("QBSdk", paramBindObj.toString());
          localObject1 = this.mPasswordCache.get(((WifiWupRequester.WifiKeyQureyRspInfo)localObject3).bssid);
          paramBindObj = (BindObj)localObject1;
          if (localObject1 == null)
          {
            paramBindObj = new WifiSdkBase.Cache(this);
            paramBindObj.mSsid = ((WifiWupRequester.WifiKeyQureyRspInfo)localObject3).ssid;
            paramBindObj.mBssid = ((WifiWupRequester.WifiKeyQureyRspInfo)localObject3).bssid;
            paramBindObj.updateTime = l;
            this.mPasswordCache.put(((WifiWupRequester.WifiKeyQureyRspInfo)localObject3).bssid, paramBindObj);
          }
          paramBindObj.mIsDirty = false;
          if (paramBindObj.mPasswordList == null) {
            paramBindObj.mPasswordList = new ArrayList();
          }
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("add keyInfo : ");
          ((StringBuilder)localObject1).append(localObject3);
          LogUtils.d("QBSdk", ((StringBuilder)localObject1).toString());
          paramBindObj.mPasswordList.add(localObject3);
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("update pwd SSID: ");
          ((StringBuilder)localObject1).append(paramBindObj.mSsid);
          ((StringBuilder)localObject1).append(" bssid : ");
          ((StringBuilder)localObject1).append(paramBindObj.mBssid);
          LogUtils.d("QBSdk", ((StringBuilder)localObject1).toString());
        }
      }
      return;
    }
  }
  
  class BindObj
  {
    ArrayList<WifiApInfo> mReqList = null;
    ArrayList<WifiWupRequester.WifiKeyQureyRspInfo> mRspList = null;
    
    BindObj() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\sdk\QBSdk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */