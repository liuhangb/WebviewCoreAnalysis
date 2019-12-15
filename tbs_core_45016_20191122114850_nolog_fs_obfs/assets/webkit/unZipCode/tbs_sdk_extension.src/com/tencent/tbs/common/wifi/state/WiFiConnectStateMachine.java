package com.tencent.tbs.common.wifi.state;

import android.content.Context;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.wifi.WifiApInfo;
import com.tencent.tbs.common.wifi.WifiCommonUtils;
import com.tencent.tbs.common.wifi.WifiConnector;
import com.tencent.tbs.common.wifi.WifiUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class WiFiConnectStateMachine
  extends StateMachine
  implements Handler.Callback, WifiBaseStateMonitor.WifiBaseStateObserver
{
  public static final int MSG_AUTHENTICATING = 2;
  public static final int MSG_CONNECTED = 4;
  public static final int MSG_CONNECTTING = 1;
  public static final int MSG_CONNECTTING_BY_USER = 201;
  public static final int MSG_DISCONNECTED = 5;
  public static final int MSG_DISCONNECT_BY_USER = 204;
  public static final int MSG_EXIT_RETRY_MODE = 203;
  private static final int MSG_NOTIFY = 1000;
  public static final int MSG_OBTAINING_ADDR = 3;
  public static final int MSG_ON_DISCONNECT_REASON = 101;
  public static final int MSG_RESET_STATE = 205;
  public static final int MSG_RETRY_MODE = 202;
  private static WiFiConnectStateMachine mInstance;
  final int MAX_PATH_COUNTS;
  private final String TAG = "QBWiFiStateMachine";
  AuthenticatingState mAuthenticatingState = new AuthenticatingState();
  ConnectedState mConnectedState = new ConnectedState();
  ConnecttingState mConnecttingState = new ConnecttingState();
  WiFiConnectState mCurState;
  DisconnectByUserState mDisconnectByUserState = new DisconnectByUserState();
  int mDisconnectReason;
  DisconnectedState mDisconnectedState = new DisconnectedState();
  String mLastReportSsid;
  private ConcurrentLinkedQueue<WiFiConnectStateChangeListener> mListeners = new ConcurrentLinkedQueue();
  ObtainingAddrState mObtainingIpAddrState = new ObtainingAddrState();
  QBConnecttingState mQBConnecttingState = new QBConnecttingState();
  QBObtainingIpAddrState mQBObtainingIpAddrState = new QBObtainingIpAddrState();
  ArrayList<String> mStatePath;
  Handler mUIHandler;
  
  protected WiFiConnectStateMachine()
  {
    super("WiFiConnectStateMachine", BrowserExecutorSupplier.getLooperForRunShortTime());
    Object localObject1 = null;
    this.mCurState = null;
    this.mUIHandler = new Handler(Looper.getMainLooper(), this);
    this.mDisconnectReason = -1;
    this.MAX_PATH_COUNTS = 30;
    this.mStatePath = new ArrayList();
    this.mLastReportSsid = "";
    LogUtils.d("QBWiFiStateMachine", "WiFiConnectStateMachine --> init");
    addState(this.mConnectedState);
    addState(this.mDisconnectedState);
    addState(this.mConnecttingState);
    addState(this.mObtainingIpAddrState);
    addState(this.mQBConnecttingState);
    addState(this.mQBObtainingIpAddrState);
    addState(this.mDisconnectByUserState);
    addState(this.mAuthenticatingState);
    Object localObject2 = WifiBaseStateMonitor.getInstance().getLastState();
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("state : ");
    ((StringBuilder)localObject3).append(localObject2);
    LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject3).toString());
    if (localObject2 != null)
    {
      localObject3 = ((Bundle)localObject2).getString("SSID");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("state : [");
      localStringBuilder.append((String)localObject3);
      localStringBuilder.append("] [");
      localStringBuilder.append(NetworkInfo.DetailedState.values()[localObject2.getInt("STATE")].name());
      localStringBuilder.append("]");
      LogUtils.d("QBWiFiStateMachine", localStringBuilder.toString());
      switch (NetworkInfo.DetailedState.values()[localObject2.getInt("STATE")])
      {
      default: 
        break;
      case ???: 
        localObject1 = this.mObtainingIpAddrState;
        ((ObtainingAddrState)localObject1).mSsid = ((String)localObject3);
        setInitialState((State)localObject1);
        localObject1 = this.mObtainingIpAddrState;
        break;
      case ???: 
        localObject1 = this.mConnecttingState;
        ((ConnecttingState)localObject1).mSsid = ((String)localObject3);
        setInitialState((State)localObject1);
        localObject1 = this.mConnecttingState;
        break;
      case ???: 
        localObject1 = this.mAuthenticatingState;
        ((AuthenticatingState)localObject1).mSsid = ((String)localObject3);
        setInitialState((State)localObject1);
        localObject1 = this.mAuthenticatingState;
        break;
      case ???: 
      case ???: 
      case ???: 
        localObject1 = this.mConnectedState;
        ((ConnectedState)localObject1).mSsid = ((String)localObject3);
        ((ConnectedState)localObject1).mDetialInfo.mConfiguration = ((WifiConfiguration)((Bundle)localObject2).getParcelable("CONFIG"));
        setInitialState(this.mConnectedState);
        localObject1 = this.mConnectedState;
        break;
      case ???: 
      case ???: 
      case ???: 
      case ???: 
      case ???: 
      case ???: 
      case ???: 
        localObject1 = this.mDisconnectedState;
        ((DisconnectedState)localObject1).mSsid = ((String)localObject3);
        setInitialState((State)localObject1);
        localObject1 = this.mDisconnectedState;
        break;
      }
    }
    else
    {
      localObject1 = this.mDisconnectedState;
      ((DisconnectedState)localObject1).mSsid = "";
      setInitialState((State)localObject1);
      localObject1 = this.mDisconnectedState;
    }
    this.mCurState = ((WiFiConnectState)localObject1);
    if (localObject2 != null)
    {
      localObject2 = ((Bundle)localObject2).getParcelable("CONFIG");
      if ((localObject2 instanceof WifiConfiguration)) {
        this.mCurState.mDetialInfo.mSafeType = WifiUtils.getSecurity((WifiConfiguration)localObject2);
      }
    }
    this.mCurState.mIsInitialState = true;
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("[");
    ((StringBuilder)localObject2).append(this.mCurState.getSsid());
    ((StringBuilder)localObject2).append("]");
    addPath(((StringBuilder)localObject2).toString());
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("InitialState SSID : ");
    ((StringBuilder)localObject2).append(((WiFiConnectState)localObject1).mSsid);
    ((StringBuilder)localObject2).append(" [ ");
    ((StringBuilder)localObject2).append(((WiFiConnectState)localObject1).getSsid());
    ((StringBuilder)localObject2).append(" ]");
    LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject2).toString());
    start();
    WifiBaseStateMonitor.getInstance().addObserver(this);
  }
  
  public static WiFiConnectStateMachine getInstance()
  {
    if (mInstance == null) {
      try
      {
        if (mInstance == null) {
          mInstance = new WiFiConnectStateMachine();
        }
      }
      finally {}
    }
    return mInstance;
  }
  
  public void addListener(WiFiConnectStateChangeListener paramWiFiConnectStateChangeListener)
  {
    if (!this.mListeners.contains(paramWiFiConnectStateChangeListener)) {
      this.mListeners.add(paramWiFiConnectStateChangeListener);
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
      LogUtils.d("CSMPATH", getPathString());
      return;
    }
    finally {}
  }
  
  public WiFiConnectState getCurState()
  {
    return this.mCurState;
  }
  
  public int getDisconnectReason()
  {
    return this.mDisconnectReason;
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
  
  public boolean handleMessage(Message paramMessage)
  {
    if (paramMessage.what == 1000)
    {
      paramMessage = (WiFiConnectState)paramMessage.obj;
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("[Notify] --> SSID : ");
      ((StringBuilder)localObject).append(paramMessage.getSsid());
      ((StringBuilder)localObject).append(" [ ");
      ((StringBuilder)localObject).append(paramMessage.getName());
      ((StringBuilder)localObject).append(" ]");
      LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject).toString());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(System.currentTimeMillis());
      ((StringBuilder)localObject).append(" : [Notify] --> SSID : ");
      ((StringBuilder)localObject).append(paramMessage.getSsid());
      ((StringBuilder)localObject).append(" [ ");
      ((StringBuilder)localObject).append(paramMessage.getName());
      ((StringBuilder)localObject).append(" ]");
      LogUtils.d("perform-test", ((StringBuilder)localObject).toString());
      try
      {
        localObject = this.mListeners.iterator();
        while (((Iterator)localObject).hasNext()) {
          ((WiFiConnectStateChangeListener)((Iterator)localObject).next()).onStateChange(paramMessage);
        }
        return true;
      }
      catch (IncompatibleClassChangeError paramMessage)
      {
        paramMessage.printStackTrace();
      }
    }
  }
  
  public void notifyStateChange()
  {
    this.mUIHandler.obtainMessage(1000, getCurState()).sendToTarget();
  }
  
  public void onStateChange(Bundle paramBundle, final int paramInt)
  {
    final Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("CurState : ");
    ((StringBuilder)localObject1).append(getCurState().getName());
    LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject1).toString());
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("onStateChange --> [ stateType : ");
    ((StringBuilder)localObject1).append(paramInt);
    ((StringBuilder)localObject1).append("]  [ data : ");
    ((StringBuilder)localObject1).append(paramBundle);
    ((StringBuilder)localObject1).append("]  ");
    LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject1).toString());
    if (paramInt == 0)
    {
      paramBundle = WifiBaseStateMonitor.getInstance().getLastState();
      if (paramBundle != null)
      {
        localObject1 = NetworkInfo.DetailedState.values()[paramBundle.getInt("STATE")];
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("onStateChange --> [ detailedState : ");
        ((StringBuilder)localObject2).append(((NetworkInfo.DetailedState)localObject1).name());
        ((StringBuilder)localObject2).append("]  ");
        LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject2).toString());
        switch (localObject1)
        {
        default: 
          return;
        case ???: 
          sendMessage(3, paramBundle);
          return;
        case ???: 
          sendMessage(1, paramBundle);
          return;
        case ???: 
          sendMessage(2, paramBundle);
          return;
        case ???: 
          localObject1 = (String)WifiBaseStateMonitor.get(paramBundle, "SSID");
          if ((!TextUtils.isEmpty((CharSequence)localObject1)) && (!TextUtils.equals(this.mLastReportSsid, (CharSequence)localObject1)))
          {
            this.mLastReportSsid = ((String)localObject1);
            localObject2 = (WifiInfo)WifiBaseStateMonitor.get(paramBundle, "WIFIINFO");
            if (localObject2 != null)
            {
              paramInt = ((WifiInfo)localObject2).getNetworkId();
              BrowserExecutorSupplier.backgroundTaskExecutor().execute(new Runnable()
              {
                public void run()
                {
                  WiFiConnectStateMachine.this.reportConfig(localObject1, "1", paramInt);
                }
              });
            }
          }
        case ???: 
        case ???: 
          sendMessage(4, paramBundle);
          return;
        }
        sendMessage(5, paramBundle);
      }
    }
    else if (paramInt == 1)
    {
      paramBundle = WifiBaseStateMonitor.getInstance().getLastState();
      if ((paramBundle != null) && (paramBundle.getInt("REASON") == 1)) {
        sendMessage(101, paramBundle);
      }
    }
  }
  
  public void removeListener(WiFiConnectStateChangeListener paramWiFiConnectStateChangeListener)
  {
    this.mListeners.remove(paramWiFiConnectStateChangeListener);
  }
  
  void reportConfig(String paramString1, String paramString2, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("report_config:");
    localStringBuilder.append(paramString2);
    LogUtils.d("QBWiFiStateMachine", localStringBuilder.toString());
    if (PublicSettingManager.getInstance().getWifiEnableConfigUpload())
    {
      paramString2 = (WifiManager)ContextHolder.getAppContext().getSystemService("wifi");
      localStringBuilder = null;
      if (paramString2 != null) {
        try
        {
          paramString2 = paramString2.getConfiguredNetworks();
        }
        catch (Exception paramString2)
        {
          paramString2.printStackTrace();
        }
      } else {
        paramString2 = null;
      }
      Object localObject;
      if (paramString2 != null)
      {
        localObject = paramString2.iterator();
        while (((Iterator)localObject).hasNext())
        {
          paramString2 = (WifiConfiguration)((Iterator)localObject).next();
          if (paramString2.networkId == paramInt) {
            break label125;
          }
        }
      }
      paramString2 = null;
      label125:
      if (paramString2 != null)
      {
        localObject = getInstance().getCurState();
        if ((localObject != null) && (!((WiFiConnectState)localObject).mIsInitialState))
        {
          String str = WifiCommonUtils.getSsidWithoutQuotation(paramString2.SSID);
          if ((TextUtils.equals(str, ((WiFiConnectState)localObject).getSsid())) && (TextUtils.equals(str, WifiCommonUtils.getSsidWithoutQuotation(paramString1))))
          {
            try
            {
              paramString1 = WifiUtils.convertObject(paramString2);
            }
            catch (IllegalAccessException paramString1)
            {
              paramString1.printStackTrace();
              paramString1 = localStringBuilder;
            }
            if (paramString1 != null)
            {
              LogUtils.d("report_config", "-----------------report config----------");
              localStringBuilder = new StringBuilder();
              localStringBuilder.append("isActiveByMe : ");
              if (((WiFiConnectState)localObject).isActiveByMe()) {
                paramString2 = WifiConnector.mLastSdkTAG;
              } else {
                paramString2 = "0";
              }
              localStringBuilder.append(paramString2);
              LogUtils.d("report_config", localStringBuilder.toString());
              paramString2 = new StringBuilder();
              paramString2.append("SSID : ");
              paramString2.append(paramString1.get("SSID"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("BSSID : ");
              paramString2.append(paramString1.get("BSSID"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("FQDN : ");
              paramString2.append(paramString1.get("FQDN"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("allowedAuthAlgorithms : ");
              paramString2.append(paramString1.get("allowedAuthAlgorithms"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("allowedGroupCiphers : ");
              paramString2.append(paramString1.get("allowedGroupCiphers"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("allowedKeyManagement : ");
              paramString2.append(paramString1.get("allowedKeyManagement"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("allowedPairwiseCiphers : ");
              paramString2.append(paramString1.get("allowedPairwiseCiphers"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("allowedProtocols : ");
              paramString2.append(paramString1.get("allowedProtocols"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("hiddenSSID : ");
              paramString2.append(paramString1.get("hiddenSSID"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("priority : ");
              paramString2.append(paramString1.get("priority"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("providerFriendlyName : ");
              paramString2.append(paramString1.get("providerFriendlyName"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("status : ");
              paramString2.append(paramString1.get("status"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("wepTxKeyIndex : ");
              paramString2.append(paramString1.get("wepTxKeyIndex"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("disableReason : ");
              paramString2.append(paramString1.get("disableReason"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("apBand : ");
              paramString2.append(paramString1.get("apBand"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("apChannel : ");
              paramString2.append(paramString1.get("apChannel"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("hiddenSSID : ");
              paramString2.append(paramString1.get("hiddenSSID"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("updateIdentifier : ");
              paramString2.append(paramString1.get("updateIdentifier"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("mIpConfiguration : ");
              paramString2.append(paramString1.get("mIpConfiguration"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("dhcpServer : ");
              paramString2.append(paramString1.get("dhcpServer"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("defaultGwMacAddress : ");
              paramString2.append(paramString1.get("defaultGwMacAddress"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("lastFailure : ");
              paramString2.append(paramString1.get("lastFailure"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("validatedInternetAccess : ");
              paramString2.append(paramString1.get("validatedInternetAccess"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("creatorUid : ");
              paramString2.append(paramString1.get("creatorUid"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("lastConnectUid : ");
              paramString2.append(paramString1.get("lastConnectUid"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("lastUpdateUid : ");
              paramString2.append(paramString1.get("lastUpdateUid"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("creatorName : ");
              paramString2.append(paramString1.get("creatorName"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("lastUpdateName : ");
              paramString2.append(paramString1.get("lastUpdateName"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("autoJoinBSSID : ");
              paramString2.append(paramString1.get("autoJoinBSSID"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("visibility : ");
              paramString2.append(paramString1.get("visibility"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("autoJoinStatus : ");
              paramString2.append(paramString1.get("autoJoinStatus"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("numConnectionFailures : ");
              paramString2.append(paramString1.get("numConnectionFailures"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("numIpConfigFailures : ");
              paramString2.append(paramString1.get("numIpConfigFailures"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("numAuthFailures : ");
              paramString2.append(paramString1.get("numAuthFailures"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("lastRoamingFailureReason : ");
              paramString2.append(paramString1.get("lastRoamingFailureReason"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("peerWifiConfiguration : ");
              paramString2.append(paramString1.get("peerWifiConfiguration"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
              paramString2 = new StringBuilder();
              paramString2.append("numAssociation : ");
              paramString2.append(paramString1.get("numAssociation"));
              paramString2.append("");
              LogUtils.d("report_config", paramString2.toString());
            }
          }
        }
      }
    }
  }
  
  protected void unhandledMessage(Message paramMessage)
  {
    super.unhandledMessage(paramMessage);
    LogUtils.d("QBWiFiStateMachine", "unhandledMessage");
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("what : ");
    ((StringBuilder)localObject1).append(paramMessage.what);
    LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject1).toString());
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("data : ");
    ((StringBuilder)localObject1).append(paramMessage.obj);
    LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject1).toString());
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("curState : ");
    ((StringBuilder)localObject1).append(getCurState());
    LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject1).toString());
    int j = paramMessage.what;
    int i;
    if (j != 101)
    {
      i = 1;
      localObject1 = null;
      boolean bool2 = false;
      Object localObject3;
      Object localObject2;
      Object localObject4;
      switch (j)
      {
      default: 
        return;
      case 205: 
        LogUtils.d("QBWiFiStateMachine", "MSG_RESET_STATE --> ");
        localObject1 = getCurState();
        if (localObject1 == null) {
          break;
        }
        localObject3 = WifiBaseStateMonitor.getInstance().getLastState();
        if (localObject3 != null)
        {
          localObject2 = ((Bundle)localObject3).getString("SSID");
          localObject1 = "";
          if ((paramMessage.obj instanceof String)) {
            localObject1 = (String)paramMessage.obj;
          }
          paramMessage = new StringBuilder();
          paramMessage.append("MSG_RESET_STATE --> [ stateSsid : ");
          paramMessage.append((String)localObject2);
          paramMessage.append("]  [ targetSsid : ");
          paramMessage.append((String)localObject1);
          paramMessage.append("]  ");
          LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
          localObject4 = NetworkInfo.DetailedState.values()[localObject3.getInt("STATE")];
          j = getCurState().getIntStateType();
          bool2 = getCurState().mActiveByMe;
          paramMessage = getCurState().mDetialInfo;
          boolean bool1 = TextUtils.equals((CharSequence)localObject1, (CharSequence)localObject2) ^ true;
          localObject1 = (WifiConfiguration)((Bundle)localObject3).getParcelable("CONFIG");
          int k = WifiUtils.getSecurity((WifiConfiguration)localObject1);
          switch (localObject4)
          {
          default: 
            i = -1;
            break;
          case ???: 
            i = 1;
            break;
          case ???: 
            i = 0;
            break;
          case ???: 
            i = 7;
            break;
          case ???: 
          case ???: 
          case ???: 
            i = 2;
            break;
          case ???: 
          case ???: 
          case ???: 
          case ???: 
          case ???: 
          case ???: 
          case ???: 
            i = 3;
          }
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("MSG_RESET_STATE --> [ newState : ");
          ((StringBuilder)localObject3).append(i);
          ((StringBuilder)localObject3).append("]  [ oldState : ");
          ((StringBuilder)localObject3).append(j);
          ((StringBuilder)localObject3).append("]  ");
          LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject3).toString());
          if (i != 7) {
            switch (i)
            {
            default: 
              return;
            case 3: 
              if (bool1)
              {
                paramMessage = this.mDisconnectedState;
                paramMessage.mSsid = ((String)localObject2);
                paramMessage.mActiveByMe = false;
                paramMessage.mDetialInfo.copy(null);
                this.mDisconnectedState.mDetialInfo.mSafeType = k;
                paramMessage = new StringBuilder();
                paramMessage.append("mActiveByMe --> [");
                paramMessage.append(this.mDisconnectedState.mActiveByMe);
                paramMessage.append("] [");
                paramMessage.append(this.mDisconnectedState.getName());
                paramMessage.append("] [");
                paramMessage.append(this.mDisconnectedState.mSsid);
                paramMessage.append("]");
                paramMessage.append(this.mDisconnectedState.mDetialInfo);
                LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
                transitionTo(this.mDisconnectedState);
                return;
              }
              if (j == i) {
                break;
              }
              localObject1 = this.mDisconnectedState;
              ((DisconnectedState)localObject1).mSsid = ((String)localObject2);
              ((DisconnectedState)localObject1).mActiveByMe = bool2;
              ((DisconnectedState)localObject1).mDetialInfo.copy(paramMessage);
              paramMessage = new StringBuilder();
              paramMessage.append("mActiveByMe --> [");
              paramMessage.append(this.mDisconnectedState.mActiveByMe);
              paramMessage.append("] [");
              paramMessage.append(this.mDisconnectedState.getName());
              paramMessage.append("] [");
              paramMessage.append(this.mDisconnectedState.mSsid);
              paramMessage.append("]");
              paramMessage.append(this.mDisconnectedState.mDetialInfo);
              LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
              transitionTo(this.mDisconnectedState);
              return;
            case 2: 
              if (bool1)
              {
                paramMessage = this.mConnectedState;
                paramMessage.mSsid = ((String)localObject2);
                paramMessage.mActiveByMe = false;
                paramMessage.mDetialInfo.copy(null);
                this.mConnectedState.mDetialInfo.mSafeType = k;
                this.mConnectedState.mDetialInfo.mConfiguration = ((WifiConfiguration)localObject1);
                paramMessage = new StringBuilder();
                paramMessage.append("mActiveByMe --> [");
                paramMessage.append(this.mConnectedState.mActiveByMe);
                paramMessage.append("] [");
                paramMessage.append(this.mConnectedState.getName());
                paramMessage.append("] [");
                paramMessage.append(this.mConnectedState.mSsid);
                paramMessage.append("]");
                paramMessage.append(this.mConnectedState.mDetialInfo);
                LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
                transitionTo(this.mConnectedState);
                return;
              }
              if (j == i) {
                break;
              }
              localObject3 = this.mConnectedState;
              ((ConnectedState)localObject3).mSsid = ((String)localObject2);
              ((ConnectedState)localObject3).mActiveByMe = bool2;
              ((ConnectedState)localObject3).mDetialInfo.copy(paramMessage);
              this.mConnectedState.mDetialInfo.mConfiguration = ((WifiConfiguration)localObject1);
              paramMessage = new StringBuilder();
              paramMessage.append("mActiveByMe --> [");
              paramMessage.append(this.mConnectedState.mActiveByMe);
              paramMessage.append("] [");
              paramMessage.append(this.mConnectedState.getName());
              paramMessage.append("] [");
              paramMessage.append(this.mConnectedState.mSsid);
              paramMessage.append("]");
              paramMessage.append(this.mConnectedState.mDetialInfo);
              LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
              transitionTo(this.mConnectedState);
              return;
            case 1: 
              if (bool1)
              {
                paramMessage = this.mObtainingIpAddrState;
                paramMessage.mSsid = ((String)localObject2);
                paramMessage.mActiveByMe = false;
                paramMessage.mDetialInfo.copy(null);
                this.mObtainingIpAddrState.mDetialInfo.mSafeType = k;
                paramMessage = new StringBuilder();
                paramMessage.append("mActiveByMe --> [");
                paramMessage.append(this.mObtainingIpAddrState.mActiveByMe);
                paramMessage.append("] [");
                paramMessage.append(this.mObtainingIpAddrState.getName());
                paramMessage.append("] [");
                paramMessage.append(this.mObtainingIpAddrState.mSsid);
                paramMessage.append("]");
                paramMessage.append(this.mObtainingIpAddrState.mDetialInfo);
                LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
                transitionTo(this.mObtainingIpAddrState);
                return;
              }
              if (j == i) {
                break;
              }
              localObject1 = this.mObtainingIpAddrState;
              ((ObtainingAddrState)localObject1).mSsid = ((String)localObject2);
              ((ObtainingAddrState)localObject1).mActiveByMe = bool2;
              ((ObtainingAddrState)localObject1).mDetialInfo.copy(paramMessage);
              paramMessage = new StringBuilder();
              paramMessage.append("mActiveByMe --> [");
              paramMessage.append(this.mObtainingIpAddrState.mActiveByMe);
              paramMessage.append("] [");
              paramMessage.append(this.mObtainingIpAddrState.getName());
              paramMessage.append("] [");
              paramMessage.append(this.mObtainingIpAddrState.mSsid);
              paramMessage.append("]");
              paramMessage.append(this.mObtainingIpAddrState.mDetialInfo);
              LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
              transitionTo(this.mObtainingIpAddrState);
              return;
            case 0: 
              if (bool1)
              {
                paramMessage = this.mConnecttingState;
                paramMessage.mSsid = ((String)localObject2);
                paramMessage.mActiveByMe = false;
                paramMessage.mDetialInfo.copy(null);
                this.mConnecttingState.mDetialInfo.mSafeType = k;
                paramMessage = new StringBuilder();
                paramMessage.append("mActiveByMe --> [");
                paramMessage.append(this.mConnecttingState.mActiveByMe);
                paramMessage.append("] [");
                paramMessage.append(this.mConnecttingState.getName());
                paramMessage.append("] [");
                paramMessage.append(this.mConnecttingState.mSsid);
                paramMessage.append("]");
                paramMessage.append(this.mConnecttingState.mDetialInfo);
                LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
                transitionTo(this.mConnecttingState);
                return;
              }
              if (j == i) {
                break;
              }
              localObject1 = this.mConnecttingState;
              ((ConnecttingState)localObject1).mSsid = ((String)localObject2);
              ((ConnecttingState)localObject1).mActiveByMe = bool2;
              ((ConnecttingState)localObject1).mDetialInfo.copy(paramMessage);
              paramMessage = new StringBuilder();
              paramMessage.append("mActiveByMe --> [");
              paramMessage.append(this.mConnecttingState.mActiveByMe);
              paramMessage.append("] [");
              paramMessage.append(this.mConnecttingState.getName());
              paramMessage.append("] [");
              paramMessage.append(this.mConnecttingState.mSsid);
              paramMessage.append("]");
              paramMessage.append(this.mConnecttingState.mDetialInfo);
              LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
              transitionTo(this.mConnecttingState);
              return;
            }
          }
          if (bool1)
          {
            paramMessage = this.mAuthenticatingState;
            paramMessage.mSsid = ((String)localObject2);
            paramMessage.mActiveByMe = false;
            paramMessage.mDetialInfo.copy(null);
            this.mAuthenticatingState.mDetialInfo.mSafeType = k;
            paramMessage = new StringBuilder();
            paramMessage.append("mActiveByMe --> [");
            paramMessage.append(this.mAuthenticatingState.mActiveByMe);
            paramMessage.append("] [");
            paramMessage.append(this.mAuthenticatingState.getName());
            paramMessage.append("] [");
            paramMessage.append(this.mAuthenticatingState.mSsid);
            paramMessage.append("]");
            paramMessage.append(this.mAuthenticatingState.mDetialInfo);
            LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
            transitionTo(this.mAuthenticatingState);
            return;
          }
          if (j == i) {
            break;
          }
          localObject1 = this.mAuthenticatingState;
          ((AuthenticatingState)localObject1).mSsid = ((String)localObject2);
          ((AuthenticatingState)localObject1).mActiveByMe = bool2;
          ((AuthenticatingState)localObject1).mDetialInfo.copy(paramMessage);
          paramMessage = new StringBuilder();
          paramMessage.append("mActiveByMe --> [");
          paramMessage.append(this.mAuthenticatingState.mActiveByMe);
          paramMessage.append("] [");
          paramMessage.append(this.mAuthenticatingState.getName());
          paramMessage.append("] [");
          paramMessage.append(this.mAuthenticatingState.mSsid);
          paramMessage.append("]");
          paramMessage.append(this.mAuthenticatingState.mDetialInfo);
          LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
          transitionTo(this.mAuthenticatingState);
          return;
        }
        this.mDisconnectReason = -1;
        this.mDisconnectedState.mSsid = ((WiFiConnectState)localObject1).getSsid();
        this.mDisconnectedState.mActiveByMe = ((WiFiConnectState)localObject1).isActiveByMe();
        this.mDisconnectedState.mDetialInfo.copy(((WiFiConnectState)localObject1).mDetialInfo);
        paramMessage = new StringBuilder();
        paramMessage.append("mActiveByMe --> [");
        paramMessage.append(this.mDisconnectedState.mActiveByMe);
        paramMessage.append("] [");
        paramMessage.append(this.mDisconnectedState.getName());
        paramMessage.append("] [");
        paramMessage.append(this.mDisconnectedState.mSsid);
        paramMessage.append("]");
        paramMessage.append(this.mDisconnectedState.mDetialInfo);
        LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
        transitionTo(this.mDisconnectedState);
        return;
      case 204: 
        if (!(paramMessage.obj instanceof String)) {
          break;
        }
        localObject2 = (String)paramMessage.obj;
        paramMessage = new StringBuilder();
        paramMessage.append("MSG_DISCONNECT_BY_USER --> [ ");
        paramMessage.append((String)localObject2);
        paramMessage.append("]");
        LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
        paramMessage = getCurState();
        if ((paramMessage == null) || (!TextUtils.equals((CharSequence)localObject2, paramMessage.getSsid())) || (paramMessage.getIntStateType() == 3) || (paramMessage.getIntStateType() == 6)) {
          i = 0;
        }
        if (i == 0) {
          break;
        }
        localObject3 = getCurState();
        paramMessage = (Message)localObject1;
        if (localObject3 != null)
        {
          bool2 = ((WiFiConnectState)localObject3).isActiveByMe();
          paramMessage = ((WiFiConnectState)localObject3).mDetialInfo;
        }
        this.mDisconnectReason = -1;
        localObject1 = this.mDisconnectByUserState;
        ((DisconnectByUserState)localObject1).mSsid = ((String)localObject2);
        ((DisconnectByUserState)localObject1).mActiveByMe = bool2;
        ((DisconnectByUserState)localObject1).mDetialInfo.copy(paramMessage);
        paramMessage = new StringBuilder();
        paramMessage.append("mActiveByMe --> [");
        paramMessage.append(this.mDisconnectByUserState.mActiveByMe);
        paramMessage.append("] [");
        paramMessage.append(this.mDisconnectByUserState.getName());
        paramMessage.append("] [");
        paramMessage.append(this.mDisconnectByUserState.mSsid);
        paramMessage.append("]");
        paramMessage.append(this.mDisconnectByUserState.mDetialInfo);
        LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
        transitionTo(this.mDisconnectByUserState);
        return;
      case 203: 
        LogUtils.d("QBWiFiStateMachine", "MSG_EXIT_RETRY_MODE --> ");
        localObject2 = getCurState();
        if ((localObject2 == null) || ((((WiFiConnectState)localObject2).getIntStateType() != 6) && (((WiFiConnectState)localObject2).getIntStateType() != 5) && (((WiFiConnectState)localObject2).getIntStateType() != 4) && ((((WiFiConnectState)localObject2).getIntStateType() != 7) || (!((WiFiConnectState)localObject2).isActiveByMe())))) {
          break;
        }
        localObject3 = WifiBaseStateMonitor.getInstance().getLastState();
        localObject1 = "";
        if ((paramMessage.obj instanceof String)) {
          localObject1 = (String)paramMessage.obj;
        }
        if (localObject3 != null)
        {
          paramMessage = ((Bundle)localObject3).getString("SSID");
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("MSG_EXIT_RETRY_MODE --> [ stateSsid : ");
          ((StringBuilder)localObject4).append(paramMessage);
          ((StringBuilder)localObject4).append("]  [ targetSsid : ");
          ((StringBuilder)localObject4).append((String)localObject1);
          ((StringBuilder)localObject4).append("]  ");
          LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject4).toString());
          if ((!TextUtils.equals((CharSequence)localObject1, paramMessage)) || (WifiBaseStateMonitor.isDisconnectedState(((Bundle)localObject3).getInt("STATE"))))
          {
            paramMessage = this.mDisconnectedState;
            paramMessage.mSsid = ((String)localObject1);
            paramMessage.mActiveByMe = true;
            paramMessage.mDetialInfo.copy(((WiFiConnectState)localObject2).mDetialInfo);
            paramMessage = new StringBuilder();
            paramMessage.append("mActiveByMe --> [");
            paramMessage.append(this.mDisconnectedState.mActiveByMe);
            paramMessage.append("] [");
            paramMessage.append(this.mDisconnectedState.getName());
            paramMessage.append("] [");
            paramMessage.append(this.mDisconnectedState.mSsid);
            paramMessage.append("]");
            paramMessage.append(this.mDisconnectedState.mDetialInfo);
            LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
            transitionTo(this.mDisconnectedState);
          }
        }
        paramMessage = obtainMessage(205);
        paramMessage.obj = localObject1;
        paramMessage.sendToTarget();
        return;
      case 202: 
        LogUtils.d("QBWiFiStateMachine", "MSG_RETRY_MODE --> ");
        return;
      case 201: 
        if (!(paramMessage.obj instanceof WifiApInfo)) {
          break;
        }
        this.mDisconnectReason = -1;
        paramMessage = (WifiApInfo)paramMessage.obj;
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("MSG_CONNECTTING_BY_USER --> [ ");
        ((StringBuilder)localObject1).append(paramMessage.mSsid);
        ((StringBuilder)localObject1).append("]");
        LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject1).toString());
        this.mQBConnecttingState.mSsid = paramMessage.mSsid;
        this.mQBConnecttingState.mDetialInfo.copy(null);
        this.mQBConnecttingState.mDetialInfo.mType = paramMessage.mConnectType;
        this.mQBConnecttingState.mDetialInfo.mSafeType = paramMessage.mSafeType;
        this.mQBConnecttingState.mActiveByMe = true;
        paramMessage = new StringBuilder();
        paramMessage.append("mActiveByMe --> [");
        paramMessage.append(this.mQBConnecttingState.mActiveByMe);
        paramMessage.append("] [");
        paramMessage.append(this.mQBConnecttingState.getName());
        paramMessage.append("] [");
        paramMessage.append(this.mQBConnecttingState.mSsid);
        paramMessage.append("]");
        paramMessage.append(this.mQBConnecttingState.mDetialInfo);
        LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
        transitionTo(this.mQBConnecttingState);
        return;
      }
    }
    else if ((paramMessage.obj instanceof Bundle))
    {
      localObject1 = getCurState();
      if (localObject1 != null)
      {
        i = ((WiFiConnectState)localObject1).getIntStateType();
        if (i != 7) {
          switch (i)
          {
          default: 
            return;
          }
        }
        if (((WiFiConnectState)localObject1).isActiveByMe()) {
          this.mDisconnectReason = ((Bundle)paramMessage.obj).getInt("REASON", -1);
        }
      }
    }
  }
  
  class AuthenticatingState
    extends WiFiConnectStateMachine.WiFiConnectState
  {
    AuthenticatingState()
    {
      super();
    }
    
    public void enter()
    {
      super.enter();
      WiFiConnectStateMachine.this.mLastReportSsid = "";
    }
    
    protected WiFiConnectStateMachine.WiFiConnectState getAInstance()
    {
      return new AuthenticatingState(WiFiConnectStateMachine.this);
    }
    
    public int getIntStateType()
    {
      return 7;
    }
    
    public String getName()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("鉴权中");
      String str;
      if (isActiveByMe()) {
        str = "(QB内)";
      } else {
        str = "";
      }
      localStringBuilder.append(str);
      return localStringBuilder.toString();
    }
    
    public boolean processMessage(Message paramMessage)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(getName());
      ((StringBuilder)localObject).append(" processMessage --> what : ");
      ((StringBuilder)localObject).append(paramMessage.what);
      LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject).toString());
      boolean bool2 = isActiveByMe();
      boolean bool1 = false;
      if (bool2)
      {
        if ((paramMessage.obj instanceof Bundle))
        {
          localObject = (Bundle)paramMessage.obj;
          String str = ((Bundle)localObject).getString("SSID");
          switch (paramMessage.what)
          {
          default: 
            return false;
          case 4: 
            if (TextUtils.equals(str, getSsid())) {
              transitionToTarget(isActiveByMe(), this.mDetialInfo, (Bundle)localObject, WiFiConnectStateMachine.this.mConnectedState);
            }
            return true;
          case 3: 
            if (TextUtils.equals(str, getSsid())) {
              transitionToTarget(isActiveByMe(), this.mDetialInfo, (Bundle)localObject, WiFiConnectStateMachine.this.mQBObtainingIpAddrState);
            }
            return true;
          }
          return true;
        }
      }
      else {
        bool1 = super.processMessage(paramMessage);
      }
      return bool1;
    }
  }
  
  class ConnectedState
    extends WiFiConnectStateMachine.WiFiConnectState
  {
    ConnectedState()
    {
      super();
    }
    
    protected WiFiConnectStateMachine.WiFiConnectState getAInstance()
    {
      return new ConnectedState(WiFiConnectStateMachine.this);
    }
    
    public int getIntStateType()
    {
      return 2;
    }
    
    public String getName()
    {
      return "已连接";
    }
  }
  
  class ConnecttingState
    extends WiFiConnectStateMachine.WiFiConnectState
  {
    ConnecttingState()
    {
      super();
    }
    
    public void enter()
    {
      super.enter();
      WiFiConnectStateMachine.this.mLastReportSsid = "";
    }
    
    protected WiFiConnectStateMachine.WiFiConnectState getAInstance()
    {
      return new ConnecttingState(WiFiConnectStateMachine.this);
    }
    
    public int getIntStateType()
    {
      return 0;
    }
    
    public String getName()
    {
      return "连接中";
    }
  }
  
  class DisconnectByUserState
    extends WiFiConnectStateMachine.WiFiConnectState
  {
    DisconnectByUserState()
    {
      super();
    }
    
    public void enter()
    {
      super.enter();
      WiFiConnectStateMachine.this.mLastReportSsid = getSsid();
    }
    
    protected WiFiConnectStateMachine.WiFiConnectState getAInstance()
    {
      return new DisconnectByUserState(WiFiConnectStateMachine.this);
    }
    
    public int getIntStateType()
    {
      return 6;
    }
    
    public String getName()
    {
      return "用户主动断开(QB内)";
    }
    
    public boolean processMessage(Message paramMessage)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(getName());
      ((StringBuilder)localObject).append(" processMessage --> what : ");
      ((StringBuilder)localObject).append(paramMessage.what);
      LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject).toString());
      boolean bool2 = paramMessage.obj instanceof Bundle;
      boolean bool1 = true;
      if (bool2)
      {
        localObject = ((Bundle)paramMessage.obj).getString("SSID");
        switch (paramMessage.what)
        {
        default: 
          break;
        case 5: 
          WiFiConnectStateMachine localWiFiConnectStateMachine = WiFiConnectStateMachine.this;
          localWiFiConnectStateMachine.mDisconnectReason = -1;
          localWiFiConnectStateMachine.mDisconnectedState.mSsid = ((String)localObject);
          WiFiConnectStateMachine.this.mDisconnectedState.mActiveByMe = this.mActiveByMe;
          WiFiConnectStateMachine.this.mDisconnectedState.mDetialInfo.copy(this.mDetialInfo);
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("mActiveByMe --> [");
          ((StringBuilder)localObject).append(WiFiConnectStateMachine.this.mDisconnectedState.mActiveByMe);
          ((StringBuilder)localObject).append("] [");
          ((StringBuilder)localObject).append(WiFiConnectStateMachine.this.mDisconnectedState.getName());
          ((StringBuilder)localObject).append("] [");
          ((StringBuilder)localObject).append(WiFiConnectStateMachine.this.mDisconnectedState.mSsid);
          ((StringBuilder)localObject).append("]");
          ((StringBuilder)localObject).append(WiFiConnectStateMachine.this.mDisconnectedState.mDetialInfo);
          LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject).toString());
          localObject = WiFiConnectStateMachine.this;
          ((WiFiConnectStateMachine)localObject).transitionTo(((WiFiConnectStateMachine)localObject).mDisconnectedState);
          break;
        case 1: 
        case 2: 
        case 3: 
        case 4: 
          if ((TextUtils.equals((CharSequence)localObject, this.mSsid)) && (this.mActiveByMe)) {
            break label326;
          }
        }
      }
      bool1 = false;
      label326:
      bool2 = bool1;
      if (!bool1) {
        bool2 = super.processMessage(paramMessage);
      }
      return bool2;
    }
  }
  
  class DisconnectedState
    extends WiFiConnectStateMachine.WiFiConnectState
  {
    DisconnectedState()
    {
      super();
    }
    
    public void enter()
    {
      super.enter();
      final String str = getSsid();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("DisconnectedState->[enter][");
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).append("]");
      LogUtils.d("report_config", ((StringBuilder)localObject).toString());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("mLastReportSsid[");
      ((StringBuilder)localObject).append(WiFiConnectStateMachine.this.mLastReportSsid);
      ((StringBuilder)localObject).append("]");
      LogUtils.d("report_config", ((StringBuilder)localObject).toString());
      if ((!TextUtils.isEmpty(str)) && (!TextUtils.equals(WiFiConnectStateMachine.this.mLastReportSsid, str)))
      {
        localObject = (WifiConfiguration)WifiBaseStateMonitor.get(WifiBaseStateMonitor.getInstance().getLastState(), "CONFIG");
        if ((localObject != null) && (TextUtils.equals(str, WifiCommonUtils.getSsidWithoutQuotation(((WifiConfiguration)localObject).SSID))))
        {
          WiFiConnectStateMachine.this.mLastReportSsid = str;
          final int i = ((WifiConfiguration)localObject).networkId;
          BrowserExecutorSupplier.backgroundTaskExecutor().execute(new Runnable()
          {
            public void run()
            {
              WiFiConnectStateMachine.this.reportConfig(str, "0", i);
            }
          });
        }
      }
    }
    
    protected WiFiConnectStateMachine.WiFiConnectState getAInstance()
    {
      return new DisconnectedState(WiFiConnectStateMachine.this);
    }
    
    public int getIntStateType()
    {
      return 3;
    }
    
    public String getName()
    {
      return "已断开";
    }
  }
  
  class ObtainingAddrState
    extends WiFiConnectStateMachine.WiFiConnectState
  {
    ObtainingAddrState()
    {
      super();
    }
    
    public void enter()
    {
      super.enter();
      WiFiConnectStateMachine.this.mLastReportSsid = "";
    }
    
    protected WiFiConnectStateMachine.WiFiConnectState getAInstance()
    {
      return new ObtainingAddrState(WiFiConnectStateMachine.this);
    }
    
    public int getIntStateType()
    {
      return 1;
    }
    
    public String getName()
    {
      return "获取IP地址";
    }
  }
  
  class QBConnecttingState
    extends WiFiConnectStateMachine.WiFiConnectState
  {
    QBConnecttingState()
    {
      super();
    }
    
    public void enter()
    {
      super.enter();
      WiFiConnectStateMachine.this.mLastReportSsid = "";
    }
    
    protected WiFiConnectStateMachine.WiFiConnectState getAInstance()
    {
      return new QBConnecttingState(WiFiConnectStateMachine.this);
    }
    
    public int getIntStateType()
    {
      return 4;
    }
    
    public String getName()
    {
      return "连接中(QB内)";
    }
    
    public boolean processMessage(Message paramMessage)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(getName());
      ((StringBuilder)localObject).append(" processMessage --> what : ");
      ((StringBuilder)localObject).append(paramMessage.what);
      LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject).toString());
      boolean bool1 = paramMessage.obj instanceof Bundle;
      boolean bool2 = true;
      String str;
      if (bool1)
      {
        localObject = (Bundle)paramMessage.obj;
        str = ((Bundle)localObject).getString("SSID");
        bool1 = bool2;
      }
      switch (paramMessage.what)
      {
      default: 
        break;
      case 4: 
        bool1 = bool2;
        if (TextUtils.equals(str, getSsid()))
        {
          transitionToTarget(true, this.mDetialInfo, (Bundle)localObject, WiFiConnectStateMachine.this.mConnectedState);
          return true;
        }
        break;
      case 3: 
        WiFiConnectStateMachine.this.mDisconnectReason = -1;
        bool1 = bool2;
        if (TextUtils.equals(str, this.mSsid))
        {
          transitionToTarget(true, this.mDetialInfo, (Bundle)localObject, WiFiConnectStateMachine.this.mQBObtainingIpAddrState);
          return true;
        }
        break;
      case 2: 
        WiFiConnectStateMachine.this.mDisconnectReason = -1;
        bool1 = bool2;
        if (TextUtils.equals(str, this.mSsid))
        {
          transitionToTarget(true, this.mDetialInfo, (Bundle)localObject, WiFiConnectStateMachine.this.mAuthenticatingState);
          return true;
          bool1 = false;
        }
        break;
      }
      return bool1;
    }
  }
  
  class QBObtainingIpAddrState
    extends WiFiConnectStateMachine.WiFiConnectState
  {
    QBObtainingIpAddrState()
    {
      super();
    }
    
    public void enter()
    {
      super.enter();
      WiFiConnectStateMachine.this.mLastReportSsid = "";
    }
    
    protected WiFiConnectStateMachine.WiFiConnectState getAInstance()
    {
      return new QBObtainingIpAddrState(WiFiConnectStateMachine.this);
    }
    
    public int getIntStateType()
    {
      return 5;
    }
    
    public String getName()
    {
      return "获取IP地址(QB内)";
    }
    
    public boolean processMessage(Message paramMessage)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(getName());
      ((StringBuilder)localObject).append(" processMessage --> what : ");
      ((StringBuilder)localObject).append(paramMessage.what);
      LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject).toString());
      boolean bool1 = paramMessage.obj instanceof Bundle;
      boolean bool2 = true;
      String str;
      if (bool1)
      {
        localObject = (Bundle)paramMessage.obj;
        str = ((Bundle)localObject).getString("SSID");
        bool1 = bool2;
      }
      switch (paramMessage.what)
      {
      default: 
        break;
      case 4: 
        bool1 = bool2;
        if (TextUtils.equals(str, getSsid()))
        {
          transitionToTarget(true, this.mDetialInfo, (Bundle)localObject, WiFiConnectStateMachine.this.mConnectedState);
          return true;
          bool1 = false;
        }
        break;
      }
      return bool1;
    }
  }
  
  public abstract class WiFiConnectState
    extends State
  {
    public static final int STATE_AUTHENTICATING = 7;
    public static final int STATE_CONNECTED = 2;
    public static final int STATE_CONNECTTING = 0;
    public static final int STATE_DISCONNECTED = 3;
    public static final int STATE_OBTAINING_ADDR = 1;
    public static final int STATE_QB_CONNECTING = 4;
    public static final int STATE_QB_DISCONNECT_BY_USER = 6;
    public static final int STATE_QB_OBTAINING_ADDR = 5;
    boolean mActiveByMe = false;
    public WiFiConnectStateMachine.WifiDetialInfo mDetialInfo = new WiFiConnectStateMachine.WifiDetialInfo(WiFiConnectStateMachine.this);
    public boolean mIsInitialState = false;
    protected String mSsid = "";
    
    public WiFiConnectState() {}
    
    protected WiFiConnectState clone()
    {
      WiFiConnectState localWiFiConnectState = getAInstance();
      localWiFiConnectState.mSsid = this.mSsid;
      localWiFiConnectState.mActiveByMe = this.mActiveByMe;
      localWiFiConnectState.mIsInitialState = this.mIsInitialState;
      localWiFiConnectState.mDetialInfo = new WiFiConnectStateMachine.WifiDetialInfo(WiFiConnectStateMachine.this, this.mDetialInfo);
      return localWiFiConnectState;
    }
    
    public void enter()
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("enter -->  [");
      ((StringBuilder)localObject).append(this.mActiveByMe);
      ((StringBuilder)localObject).append("] [");
      ((StringBuilder)localObject).append(getName());
      ((StringBuilder)localObject).append("] [");
      ((StringBuilder)localObject).append(this.mSsid);
      ((StringBuilder)localObject).append("]");
      ((StringBuilder)localObject).append(this.mDetialInfo);
      LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject).toString());
      super.enter();
      localObject = "";
      if (WiFiConnectStateMachine.this.mCurState != null) {
        localObject = WiFiConnectStateMachine.this.mCurState.getSsid();
      }
      WiFiConnectStateMachine.this.mCurState = clone();
      if (!TextUtils.equals(WiFiConnectStateMachine.this.mCurState.getSsid(), (CharSequence)localObject))
      {
        localObject = WiFiConnectStateMachine.this;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("[");
        localStringBuilder.append(WiFiConnectStateMachine.this.mCurState.getSsid());
        localStringBuilder.append("]");
        ((WiFiConnectStateMachine)localObject).addPath(localStringBuilder.toString());
      }
      WiFiConnectStateMachine.this.addPath(getName());
      WiFiConnectStateMachine.this.notifyStateChange();
    }
    
    public void exit()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(getName());
      localStringBuilder.append("--> exit()");
      LogUtils.d("QBWiFiStateMachine", localStringBuilder.toString());
      super.exit();
      this.mIsInitialState = false;
    }
    
    protected abstract WiFiConnectState getAInstance();
    
    public abstract int getIntStateType();
    
    public String getSsid()
    {
      return this.mSsid;
    }
    
    public boolean isActiveByMe()
    {
      return this.mActiveByMe;
    }
    
    public boolean processMessage(Message paramMessage)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(getName());
      ((StringBuilder)localObject).append(" processMessage --> what : ");
      ((StringBuilder)localObject).append(paramMessage.what);
      LogUtils.d("QBWiFiStateMachine", ((StringBuilder)localObject).toString());
      boolean bool2 = paramMessage.obj instanceof Bundle;
      boolean bool1 = true;
      if (bool2)
      {
        localObject = (Bundle)paramMessage.obj;
        String str = ((Bundle)localObject).getString("SSID");
        int i = WifiUtils.getSecurity((WifiConfiguration)((Bundle)localObject).getParcelable("CONFIG"));
        switch (paramMessage.what)
        {
        default: 
          break;
        case 5: 
          if (getIntStateType() == 3) {
            return true;
          }
          WiFiConnectStateMachine.this.mDisconnectedState.mSsid = str;
          WiFiConnectStateMachine.this.mDisconnectedState.mActiveByMe = this.mActiveByMe;
          WiFiConnectStateMachine.this.mDisconnectedState.mDetialInfo.copy(this.mDetialInfo);
          paramMessage = new StringBuilder();
          paramMessage.append("mActiveByMe --> [");
          paramMessage.append(WiFiConnectStateMachine.this.mDisconnectedState.mActiveByMe);
          paramMessage.append("] [");
          paramMessage.append(WiFiConnectStateMachine.this.mDisconnectedState.getName());
          paramMessage.append("] [");
          paramMessage.append(WiFiConnectStateMachine.this.mDisconnectedState.mSsid);
          paramMessage.append("]");
          paramMessage.append(WiFiConnectStateMachine.this.mDisconnectedState.mDetialInfo);
          LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
          paramMessage = WiFiConnectStateMachine.this;
          paramMessage.transitionTo(paramMessage.mDisconnectedState);
          return true;
        case 4: 
          WiFiConnectStateMachine.this.mDisconnectReason = -1;
          if (getIntStateType() == 2)
          {
            if (TextUtils.equals(str, this.mSsid)) {
              return bool1;
            }
            this.mSsid = str;
            this.mActiveByMe = false;
            this.mDetialInfo.copy(null);
            this.mDetialInfo.mSafeType = i;
            WiFiConnectStateMachine.this.mConnectedState.mDetialInfo.mConfiguration = ((WifiConfiguration)((Bundle)localObject).getParcelable("CONFIG"));
            WiFiConnectStateMachine.this.mConnectedState.mDetialInfo.mInfo = ((WifiInfo)((Bundle)localObject).getParcelable("WIFIINFO"));
            paramMessage = new StringBuilder();
            paramMessage.append("SSID : ");
            paramMessage.append(this.mSsid);
            paramMessage.append(" [ ");
            paramMessage.append(getName());
            paramMessage.append(" ]");
            LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
            paramMessage = new StringBuilder();
            paramMessage.append("mActiveByMe --> [");
            paramMessage.append(this.mActiveByMe);
            paramMessage.append("] [");
            paramMessage.append(getName());
            paramMessage.append("] [");
            paramMessage.append(this.mSsid);
            paramMessage.append("]");
            paramMessage.append(this.mDetialInfo);
            LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
            WiFiConnectStateMachine.this.notifyStateChange();
            return true;
          }
          WiFiConnectStateMachine.this.mConnectedState.mSsid = str;
          WiFiConnectStateMachine.this.mConnectedState.mActiveByMe = this.mActiveByMe;
          WiFiConnectStateMachine.this.mConnectedState.mDetialInfo.copy(this.mDetialInfo);
          if (getIntStateType() == 3)
          {
            WiFiConnectStateMachine.this.mConnectedState.mActiveByMe = false;
            WiFiConnectStateMachine.this.mConnectedState.mDetialInfo.copy(null);
          }
          WiFiConnectStateMachine.this.mConnectedState.mDetialInfo.mSafeType = i;
          WiFiConnectStateMachine.this.mConnectedState.mDetialInfo.mConfiguration = ((WifiConfiguration)((Bundle)localObject).getParcelable("CONFIG"));
          WiFiConnectStateMachine.this.mConnectedState.mDetialInfo.mInfo = ((WifiInfo)((Bundle)localObject).getParcelable("WIFIINFO"));
          paramMessage = new StringBuilder();
          paramMessage.append("mActiveByMe --> [");
          paramMessage.append(WiFiConnectStateMachine.this.mConnectedState.mActiveByMe);
          paramMessage.append("] [");
          paramMessage.append(WiFiConnectStateMachine.this.mConnectedState.getName());
          paramMessage.append("] [");
          paramMessage.append(WiFiConnectStateMachine.this.mConnectedState.mSsid);
          paramMessage.append("]");
          paramMessage.append(WiFiConnectStateMachine.this.mConnectedState.mDetialInfo);
          LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
          paramMessage = WiFiConnectStateMachine.this;
          paramMessage.transitionTo(paramMessage.mConnectedState);
          return true;
        case 3: 
          WiFiConnectStateMachine.this.mDisconnectReason = -1;
          if (getIntStateType() == 1)
          {
            if (TextUtils.equals(str, this.mSsid)) {
              return bool1;
            }
            this.mSsid = str;
            this.mActiveByMe = false;
            this.mDetialInfo.copy(null);
            this.mDetialInfo.mSafeType = i;
            paramMessage = new StringBuilder();
            paramMessage.append("SSID : ");
            paramMessage.append(this.mSsid);
            paramMessage.append(" [ ");
            paramMessage.append(getName());
            paramMessage.append(" ]");
            LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
            WiFiConnectStateMachine.this.notifyStateChange();
            paramMessage = new StringBuilder();
            paramMessage.append("mActiveByMe --> [");
            paramMessage.append(this.mActiveByMe);
            paramMessage.append("] [");
            paramMessage.append(getName());
            paramMessage.append("] [");
            paramMessage.append(this.mSsid);
            paramMessage.append("]");
            paramMessage.append(this.mDetialInfo);
            LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
            return true;
          }
          WiFiConnectStateMachine.this.mObtainingIpAddrState.mSsid = str;
          WiFiConnectStateMachine.this.mObtainingIpAddrState.mActiveByMe = false;
          WiFiConnectStateMachine.this.mObtainingIpAddrState.mDetialInfo.copy(null);
          WiFiConnectStateMachine.this.mObtainingIpAddrState.mDetialInfo.mSafeType = i;
          paramMessage = new StringBuilder();
          paramMessage.append("mActiveByMe --> [");
          paramMessage.append(WiFiConnectStateMachine.this.mObtainingIpAddrState.mActiveByMe);
          paramMessage.append("] [");
          paramMessage.append(WiFiConnectStateMachine.this.mObtainingIpAddrState.getName());
          paramMessage.append("] [");
          paramMessage.append(WiFiConnectStateMachine.this.mObtainingIpAddrState.mSsid);
          paramMessage.append("]");
          paramMessage.append(WiFiConnectStateMachine.this.mObtainingIpAddrState.mDetialInfo);
          LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
          paramMessage = WiFiConnectStateMachine.this;
          paramMessage.transitionTo(paramMessage.mObtainingIpAddrState);
          return true;
        case 2: 
          WiFiConnectStateMachine.this.mDisconnectReason = -1;
          if (getIntStateType() == 7)
          {
            if (TextUtils.equals(str, this.mSsid)) {
              return bool1;
            }
            transitionToTarget(false, this.mDetialInfo, (Bundle)localObject, this);
            WiFiConnectStateMachine.this.notifyStateChange();
            return true;
          }
          WiFiConnectStateMachine.this.mAuthenticatingState.mSsid = str;
          WiFiConnectStateMachine.this.mAuthenticatingState.mActiveByMe = isActiveByMe();
          WiFiConnectStateMachine.this.mAuthenticatingState.mDetialInfo.copy(null);
          WiFiConnectStateMachine.this.mAuthenticatingState.mDetialInfo.mSafeType = i;
          paramMessage = new StringBuilder();
          paramMessage.append("mActiveByMe --> [");
          paramMessage.append(WiFiConnectStateMachine.this.mAuthenticatingState.mActiveByMe);
          paramMessage.append("] [");
          paramMessage.append(WiFiConnectStateMachine.this.mAuthenticatingState.getName());
          paramMessage.append("] [");
          paramMessage.append(WiFiConnectStateMachine.this.mAuthenticatingState.mSsid);
          paramMessage.append("]");
          paramMessage.append(WiFiConnectStateMachine.this.mAuthenticatingState.mDetialInfo);
          LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
          paramMessage = WiFiConnectStateMachine.this;
          paramMessage.transitionTo(paramMessage.mAuthenticatingState);
          return true;
        case 1: 
          WiFiConnectStateMachine.this.mDisconnectReason = -1;
          if ((getIntStateType() != 0) && (getIntStateType() != 7))
          {
            WiFiConnectStateMachine.this.mConnecttingState.mSsid = str;
            WiFiConnectStateMachine.this.mConnecttingState.mActiveByMe = false;
            WiFiConnectStateMachine.this.mConnecttingState.mDetialInfo.copy(null);
            WiFiConnectStateMachine.this.mConnecttingState.mDetialInfo.mSafeType = i;
            paramMessage = new StringBuilder();
            paramMessage.append("mActiveByMe --> [");
            paramMessage.append(WiFiConnectStateMachine.this.mConnecttingState.mActiveByMe);
            paramMessage.append("] [");
            paramMessage.append(WiFiConnectStateMachine.this.mConnecttingState.getName());
            paramMessage.append("] [");
            paramMessage.append(WiFiConnectStateMachine.this.mConnecttingState.mSsid);
            paramMessage.append("]");
            paramMessage.append(WiFiConnectStateMachine.this.mConnecttingState.mDetialInfo);
            LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
            paramMessage = WiFiConnectStateMachine.this;
            paramMessage.transitionTo(paramMessage.mConnecttingState);
            return true;
          }
          if (TextUtils.equals(str, this.mSsid)) {
            return bool1;
          }
          this.mSsid = str;
          this.mActiveByMe = false;
          this.mDetialInfo.copy(null);
          this.mDetialInfo.mSafeType = i;
          paramMessage = new StringBuilder();
          paramMessage.append("SSID : ");
          paramMessage.append(this.mSsid);
          paramMessage.append(" [ ");
          paramMessage.append(getName());
          paramMessage.append(" ]");
          LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
          WiFiConnectStateMachine.this.notifyStateChange();
          paramMessage = new StringBuilder();
          paramMessage.append("mActiveByMe --> [");
          paramMessage.append(this.mActiveByMe);
          paramMessage.append("] [");
          paramMessage.append(getName());
          paramMessage.append("] [");
          paramMessage.append(this.mSsid);
          paramMessage.append("]");
          paramMessage.append(this.mDetialInfo);
          LogUtils.d("QBWiFiStateMachine", paramMessage.toString());
          return true;
        }
      }
      bool1 = false;
      return bool1;
    }
    
    protected void transitionToTarget(boolean paramBoolean, WiFiConnectStateMachine.WifiDetialInfo paramWifiDetialInfo, Bundle paramBundle, WiFiConnectState paramWiFiConnectState)
    {
      paramWiFiConnectState.mSsid = paramBundle.getString("SSID");
      paramWiFiConnectState.mActiveByMe = paramBoolean;
      paramWiFiConnectState.mDetialInfo.copy(paramWifiDetialInfo);
      paramWiFiConnectState.mDetialInfo.mConfiguration = ((WifiConfiguration)paramBundle.getParcelable("CONFIG"));
      paramWifiDetialInfo = new StringBuilder();
      paramWifiDetialInfo.append("mActiveByMe --> [");
      paramWifiDetialInfo.append(paramWiFiConnectState.mActiveByMe);
      paramWifiDetialInfo.append("] [");
      paramWifiDetialInfo.append(paramWiFiConnectState.getName());
      paramWifiDetialInfo.append("] [");
      paramWifiDetialInfo.append(paramWiFiConnectState.mSsid);
      paramWifiDetialInfo.append("]");
      paramWifiDetialInfo.append(paramWiFiConnectState.mDetialInfo);
      LogUtils.d("QBWiFiStateMachine", paramWifiDetialInfo.toString());
      WiFiConnectStateMachine.this.transitionTo(paramWiFiConnectState);
    }
  }
  
  public static abstract interface WiFiConnectStateChangeListener
  {
    public abstract void onStateChange(WiFiConnectStateMachine.WiFiConnectState paramWiFiConnectState);
  }
  
  public class WifiDetialInfo
  {
    public WifiConfiguration mConfiguration = null;
    public WifiInfo mInfo = null;
    public int mSafeType = -1;
    public int mType = 0;
    
    public WifiDetialInfo() {}
    
    public WifiDetialInfo(WifiDetialInfo paramWifiDetialInfo)
    {
      copy(paramWifiDetialInfo);
    }
    
    public void copy(WifiDetialInfo paramWifiDetialInfo)
    {
      if (paramWifiDetialInfo != null)
      {
        this.mSafeType = paramWifiDetialInfo.mSafeType;
        this.mType = paramWifiDetialInfo.mType;
        this.mConfiguration = paramWifiDetialInfo.mConfiguration;
        this.mInfo = paramWifiDetialInfo.mInfo;
        return;
      }
      this.mSafeType = -1;
      this.mType = 0;
      this.mConfiguration = null;
      this.mInfo = null;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder("WifiDetialInfo");
      localStringBuilder.append("[");
      localStringBuilder.append("TYPE:");
      localStringBuilder.append(this.mType);
      localStringBuilder.append("]，");
      localStringBuilder.append("[");
      localStringBuilder.append("mSafeType:");
      localStringBuilder.append(this.mSafeType);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\state\WiFiConnectStateMachine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */