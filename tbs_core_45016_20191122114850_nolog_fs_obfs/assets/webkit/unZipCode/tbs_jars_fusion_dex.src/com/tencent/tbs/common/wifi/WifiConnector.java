package com.tencent.tbs.common.wifi;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.Md5Utils;
import com.tencent.common.utils.ReflectionUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine.WiFiConnectState;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine.WiFiConnectStateChangeListener;
import com.tencent.tbs.common.wifi.state.WifiBaseStateMonitor;
import com.tencent.tbs.common.wifi.state.WifiBaseStateMonitor.WifiBaseStateObserver;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WifiConnector
  implements Handler.Callback, WiFiConnectStateMachine.WiFiConnectStateChangeListener, WifiBaseStateMonitor.WifiBaseStateObserver
{
  public static final int DETAIL_ALL_PWD_FAIL = 1;
  public static final String KEY_BASE_STATE_PATH = "key6";
  public static final String KEY_BRAND = "key17";
  public static final String KEY_CONNECT_FAILED_COUNTS = "key14";
  public static final String KEY_CONNECT_STATE_PATH = "key7";
  public static final String KEY_CONNECT_TIME = "key16";
  public static final String KEY_DETAIL = "key15";
  public static final String KEY_ERROR_CODE = "error_code";
  public static final String KEY_ERR_PWD = "key12";
  public static final String KEY_EVENT_RESULT = "event_result";
  public static final String KEY_EVENT_TYPE = "event_type";
  public static final String KEY_FT_NAME = "ft_name";
  public static final String KEY_INTERVAL = "key3";
  public static final String KEY_LEVEL = "key4";
  public static final String KEY_MANUFACTURER = "key18";
  public static final String KEY_PER_CONNECT_TIME = "key13";
  public static final String KEY_PWD_ERR_COUNT = "key11";
  public static final String KEY_REASON = "key2";
  public static final String KEY_RETRY_TIMES = "key5";
  public static final String KEY_SDK = "key1";
  public static final String KEY_SYS_RESPONSE = "key9";
  public static final String KEY_TOTLE_PWD = "key10";
  public static final String KEY_TRY_INDEX = "key8";
  public static final String KEY_TYPE_NAME = "type";
  private static final int MAX_CONNNECT_DUR = 20000;
  static final int MAX_PATH_COUNTS = 8;
  public static final int MSG_CONNECT_NOW = 1;
  public static final int MSG_ON_CONNECT_STATE_CHANGE = 4;
  public static final int MSG_ON_DISCONNECT_REASON = 5;
  public static final int MSG_ON_DO_STOP = 6;
  public static final byte REASON_LOST_SIGNAL = -4;
  public static final byte REASON_OC = -2;
  public static final byte REASON_OT = -1;
  public static final byte REASON_OTC = -3;
  public static final byte REASON_UNKNOWN = 0;
  public static final int STOP_OUTER = 2;
  public static final int STOP_TYPE_CHANGE_AP = 0;
  private static final int STOP_TYPE_FINAL = 1;
  public static final String TAG = "WifiConnector";
  public static String mLastSdkTAG = "";
  static ArrayList<String> mStatePath = new ArrayList();
  String DEBUG_ACTION = "MTT_DEV_DEBUG_ACTION";
  ActionInvocationHandler mActionListener = null;
  long mActiveInterval = -1L;
  String mBaseStatePath = "B";
  ChannelInvocationHandler mChannelListener = null;
  private String mConnectDate = "";
  ConnectEvent mConnectEvent = null;
  int mConnectLevel = -1;
  long mConnectTime = -1L;
  int mConnetFailed = 0;
  String mConnetStatePath = "C";
  int mErrorPwdCount = 0;
  String mErrorPwdStr = "";
  Handler mHandler = null;
  boolean mHasReport = false;
  boolean mIsConnecting = false;
  public int mMaxRetryTimes = 5;
  String mPerConncetTimeStr = "";
  long mPerStartTime = 0L;
  public int mPwdCount = 0;
  int mRetryTimes = 0;
  String mSdkTag = "";
  String mSplitor = "_";
  private long mStartTime = -1L;
  boolean mStopRetry = false;
  private int mTargetNetWorkId = -1;
  String mTargetSsid = "";
  public int mTimeOut = 5000;
  int mTryIndex = 0;
  Handler mUIHandler = null;
  boolean mWaitingConnecting = false;
  WifiManager mWifiManager = null;
  
  public WifiConnector(String paramString1, ConnectEvent paramConnectEvent, String paramString2, int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("init[");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("][");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("][");
    localStringBuilder.append(paramInt1);
    localStringBuilder.append("][");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append("]");
    LogUtils.d("WifiConnector", localStringBuilder.toString());
    mLastSdkTAG = paramString2;
    this.mTryIndex = paramInt2;
    this.mSdkTag = paramString2;
    this.mConnectLevel = paramInt1;
    this.mTargetSsid = paramString1;
    this.mConnectEvent = paramConnectEvent;
    this.mHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime(), this);
    this.mWifiManager = WifiEngine.getInstance().getWifiManager();
    try
    {
      paramString1 = new Date();
      this.mConnectDate = new SimpleDateFormat("MM_dd_HH_mm").format(paramString1);
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    WifiBaseStateMonitor.getInstance().addObserver(this);
    WiFiConnectStateMachine.getInstance().addListener(this);
  }
  
  public WifiConnector(String paramString1, String paramString2, int paramInt)
  {
    this(paramString1, null, paramString2, paramInt, 0);
  }
  
  public static void addPath(String paramString)
  {
    try
    {
      mStatePath.add(0, paramString);
      if (mStatePath.size() > 8) {
        mStatePath.remove(mStatePath.size() - 1);
      }
      LogUtils.d("CNPATH", getPathString());
      return;
    }
    finally {}
  }
  
  private WifiConfiguration checkConfig(WifiConfiguration paramWifiConfiguration1, WifiConfiguration paramWifiConfiguration2)
  {
    LogUtils.d("WifiConnector", "checkConfig");
    LogUtils.d("WifiConnector", "------newConfig-----");
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(paramWifiConfiguration1);
    ((StringBuilder)localObject1).append("");
    LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
    LogUtils.d("WifiConnector", "------lastConfig-----");
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(paramWifiConfiguration2);
    ((StringBuilder)localObject1).append("");
    LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
    if (paramWifiConfiguration1 == null)
    {
      LogUtils.d("WifiConnector", "find lastConfig");
    }
    else
    {
      localObject1 = paramWifiConfiguration1;
      if (paramWifiConfiguration2 == null) {
        break label548;
      }
      int j = paramWifiConfiguration1.status;
      int i = 1;
      localObject1 = paramWifiConfiguration1;
      if (j != 1) {
        break label548;
      }
      if (paramWifiConfiguration2.status == paramWifiConfiguration1.status)
      {
        Object localObject4 = null;
        try
        {
          localObject1 = WifiUtils.convertObject(paramWifiConfiguration2);
          try
          {
            HashMap localHashMap = WifiUtils.convertObject(paramWifiConfiguration1);
            localObject3 = localObject1;
          }
          catch (IllegalAccessException localIllegalAccessException1) {}
          localIllegalAccessException2.printStackTrace();
        }
        catch (IllegalAccessException localIllegalAccessException2)
        {
          localObject1 = null;
        }
        Object localObject3 = localObject1;
        Object localObject2 = localObject4;
        localObject1 = paramWifiConfiguration1;
        if (localObject3 == null) {
          break label548;
        }
        localObject1 = paramWifiConfiguration1;
        if (localObject2 == null) {
          break label548;
        }
        LogUtils.d("WifiConnector", "check disableReason");
        localObject1 = ((HashMap)localObject3).get("disableReason");
        localObject4 = ((HashMap)localObject2).get("disableReason");
        if (((localObject1 instanceof Integer)) && ((localObject4 instanceof Integer)))
        {
          j = ((Integer)localObject1).intValue();
          int k = ((Integer)localObject4).intValue();
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("lastDisableReason : ");
          ((StringBuilder)localObject1).append(j);
          LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("newDisableReason : ");
          ((StringBuilder)localObject1).append(k);
          LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
          if (k == 3)
          {
            LogUtils.d("WifiConnector", "find lastConfig");
            paramWifiConfiguration1 = paramWifiConfiguration2;
            break label360;
          }
        }
        i = 0;
        label360:
        localObject1 = paramWifiConfiguration1;
        if (i != 0) {
          break label548;
        }
        LogUtils.d("WifiConnector", "check priority");
        localObject3 = ((HashMap)localObject3).get("priority");
        localObject2 = ((HashMap)localObject2).get("priority");
        localObject1 = paramWifiConfiguration1;
        if (!(localObject3 instanceof Integer)) {
          break label548;
        }
        localObject1 = paramWifiConfiguration1;
        if (!(localObject2 instanceof Integer)) {
          break label548;
        }
        i = ((Integer)localObject3).intValue();
        j = ((Integer)localObject2).intValue();
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("lastDisableReason : ");
        ((StringBuilder)localObject1).append(i);
        LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("newDisableReason : ");
        ((StringBuilder)localObject1).append(j);
        LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
        localObject1 = paramWifiConfiguration1;
        if (i <= j) {
          break label548;
        }
        LogUtils.d("WifiConnector", "find lastConfig");
        return paramWifiConfiguration2;
      }
      localObject1 = paramWifiConfiguration1;
      if (paramWifiConfiguration2.status == 1) {
        break label548;
      }
      LogUtils.d("WifiConnector", "find lastConfig");
    }
    localObject1 = paramWifiConfiguration2;
    label548:
    return (WifiConfiguration)localObject1;
  }
  
  public static String getPathString()
  {
    try
    {
      Object localObject1 = new StringBuilder();
      Iterator localIterator = mStatePath.iterator();
      while (localIterator.hasNext()) {
        ((StringBuilder)localObject1).append((String)localIterator.next());
      }
      localObject1 = ((StringBuilder)localObject1).toString();
      return (String)localObject1;
    }
    finally {}
  }
  
  private void stop()
  {
    stop(1);
  }
  
  public boolean connect(WifiApInfo paramWifiApInfo)
  {
    LogUtils.d("WifiConnector", "connect");
    boolean bool2 = false;
    if (paramWifiApInfo == null)
    {
      LogUtils.d("WifiConnector", "apInfo is null");
      return false;
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("[");
    ((StringBuilder)localObject1).append(paramWifiApInfo.mSsid);
    ((StringBuilder)localObject1).append("][");
    ((StringBuilder)localObject1).append(paramWifiApInfo.mForceNewConfig);
    ((StringBuilder)localObject1).append("]");
    LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
    Object localObject2 = null;
    localObject1 = null;
    Object localObject3 = WifiUtils.getConfiguredNetworks();
    if (localObject3 != null)
    {
      localObject3 = ((List)localObject3).iterator();
      for (;;)
      {
        localObject2 = localObject1;
        if (!((Iterator)localObject3).hasNext()) {
          break;
        }
        localObject2 = (WifiConfiguration)((Iterator)localObject3).next();
        if ((TextUtils.equals(paramWifiApInfo.mSsid, WifiCommonUtils.getSsidWithoutQuotation(((WifiConfiguration)localObject2).SSID))) && (WifiUtils.getSecurity((WifiConfiguration)localObject2) == paramWifiApInfo.mSafeType)) {
          if (paramWifiApInfo.mForceNewConfig)
          {
            this.mWifiManager.disableNetwork(((WifiConfiguration)localObject2).networkId);
            this.mWifiManager.removeNetwork(((WifiConfiguration)localObject2).networkId);
            this.mWifiManager.saveConfiguration();
          }
          else
          {
            localObject1 = checkConfig((WifiConfiguration)localObject2, (WifiConfiguration)localObject1);
            LogUtils.d("WifiConnector", "find saved config");
          }
        }
      }
    }
    int i;
    if (localObject2 == null)
    {
      i = createConfig(paramWifiApInfo).networkId;
    }
    else
    {
      LogUtils.d("WifiConnector", "savedConfig");
      i = ((WifiConfiguration)localObject2).networkId;
    }
    boolean bool1 = bool2;
    if (i != -1)
    {
      if (paramWifiApInfo.mForceNewConfig) {
        this.mTargetNetWorkId = i;
      }
      try
      {
        bool1 = connectByHideApi(i);
        return bool1;
      }
      catch (Exception paramWifiApInfo)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Exception : ");
        ((StringBuilder)localObject1).append(paramWifiApInfo.getMessage());
        LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
        paramWifiApInfo.printStackTrace();
        bool1 = bool2;
        if (this.mWifiManager.enableNetwork(i, true))
        {
          if (this.mRetryTimes == 1) {
            this.mConnectTime = System.currentTimeMillis();
          }
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  boolean connectByHideApi(int paramInt)
    throws Exception
  {
    LogUtils.d("WifiConnector", "connectByHideApi");
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("sdk version --> ");
    ((StringBuilder)localObject1).append(DeviceUtils.getSdkVersion());
    LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
    localObject1 = Class.forName("android.net.wifi.WifiManager");
    if (DeviceUtils.getSdkVersion() < 16)
    {
      if (this.mUIHandler == null) {
        this.mUIHandler = new Handler(Looper.getMainLooper());
      }
      ((Class)localObject1).getMethod("asyncConnect", new Class[] { Context.class, Handler.class }).invoke(ContextHolder.getAppContext(), new Object[] { this.mUIHandler });
      ((Class)localObject1).getMethod("connectNetwork", new Class[] { Integer.TYPE }).invoke(this.mWifiManager, new Object[] { Integer.valueOf(paramInt) });
      if (this.mRetryTimes == 1)
      {
        this.mConnectTime = System.currentTimeMillis();
        return true;
      }
    }
    else
    {
      Object localObject2;
      Object localObject3;
      if (DeviceUtils.getSdkVersion() == 16)
      {
        if (this.mChannelListener == null) {
          this.mChannelListener = new ChannelInvocationHandler();
        }
        localObject2 = getInvocationHandlerProxy(this.mChannelListener, "android.net.wifi.WifiManager$ChannelListener");
        localObject2 = ((Class)localObject1).getMethod("initialize", new Class[] { Context.class, Looper.class, Class.forName("android.net.wifi.WifiManager$ChannelListener") }).invoke(ContextHolder.getAppContext(), new Object[] { localObject2 });
        if (this.mActionListener == null) {
          this.mActionListener = new ActionInvocationHandler();
        }
        localObject3 = getInvocationHandlerProxy(this.mActionListener, "android.net.wifi.WifiManager$ActionListener");
        Class localClass = Class.forName("android.net.wifi.WifiManager$ActionListener");
        ((Class)localObject1).getMethod("connect", new Class[] { Class.forName("android.net.wifi.WifiManager$Channel"), Integer.TYPE, localClass }).invoke(this.mWifiManager, new Object[] { localObject2, Integer.valueOf(paramInt), localObject3 });
        if (this.mRetryTimes == 1)
        {
          this.mConnectTime = System.currentTimeMillis();
          return true;
        }
      }
      else
      {
        if (this.mActionListener == null) {
          this.mActionListener = new ActionInvocationHandler();
        }
        localObject2 = getInvocationHandlerProxy(this.mActionListener, "android.net.wifi.WifiManager$ActionListener");
        localObject3 = Class.forName("android.net.wifi.WifiManager$ActionListener");
        ((Class)localObject1).getMethod("connect", new Class[] { Integer.TYPE, localObject3 }).invoke(this.mWifiManager, new Object[] { Integer.valueOf(paramInt), localObject2 });
        if (this.mRetryTimes == 1) {
          this.mConnectTime = System.currentTimeMillis();
        }
      }
    }
    return true;
  }
  
  WifiConfiguration createConfig(WifiApInfo paramWifiApInfo)
  {
    if (paramWifiApInfo == null) {
      return null;
    }
    if (paramWifiApInfo.mSafeType == 3) {
      return createEapConfig(paramWifiApInfo.mSsid, 3, 0, paramWifiApInfo.mIdentity, paramWifiApInfo.mPassword, null, null, null, null);
    }
    return createNormalConfig(paramWifiApInfo.mSsid, paramWifiApInfo.mSafeType, paramWifiApInfo.mPassword);
  }
  
  WifiConfiguration createEapConfig(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, X509Certificate paramX509Certificate1, PrivateKey paramPrivateKey, X509Certificate paramX509Certificate2, String paramString4)
  {
    if (DeviceUtils.getSdkVersion() < 18) {
      return createEapConfigApi14(paramString1, paramInt1, paramInt2, paramString2, paramString3, paramX509Certificate1, paramPrivateKey, paramX509Certificate2, paramString4);
    }
    return createEapConfigApi18(paramString1, paramInt1, paramInt2, paramString2, paramString3, paramX509Certificate1, paramPrivateKey, paramX509Certificate2, paramString4);
  }
  
  WifiConfiguration createEapConfigApi14(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, X509Certificate paramX509Certificate1, PrivateKey paramPrivateKey, X509Certificate paramX509Certificate2, String paramString4)
  {
    paramX509Certificate1 = new StringBuilder();
    paramX509Certificate1.append("createEapConfigApi14 ssid:");
    paramX509Certificate1.append(paramString1);
    LogUtils.d("WifiConnector", paramX509Certificate1.toString());
    paramX509Certificate1 = new StringBuilder();
    paramX509Certificate1.append("createEapConfigApi14 eapMethod:");
    paramX509Certificate1.append(paramInt1);
    LogUtils.d("WifiConnector", paramX509Certificate1.toString());
    paramX509Certificate1 = new StringBuilder();
    paramX509Certificate1.append("createEapConfigApi14 phase2Method:");
    paramX509Certificate1.append(paramInt2);
    LogUtils.d("WifiConnector", paramX509Certificate1.toString());
    paramX509Certificate1 = new StringBuilder();
    paramX509Certificate1.append("createEapConfigApi14 identity:");
    paramX509Certificate1.append(paramString2);
    LogUtils.d("WifiConnector", paramX509Certificate1.toString());
    paramX509Certificate1 = new StringBuilder();
    paramX509Certificate1.append("createEapConfigApi14 password:");
    paramX509Certificate1.append(paramString3);
    LogUtils.d("WifiConnector", paramX509Certificate1.toString());
    if (TextUtils.isEmpty(paramString1)) {
      return null;
    }
    paramX509Certificate1 = new WifiConfiguration();
    paramPrivateKey = new StringBuilder();
    paramPrivateKey.append("\"");
    paramPrivateKey.append(paramString1);
    paramPrivateKey.append("\"");
    paramX509Certificate1.SSID = paramPrivateKey.toString();
    paramX509Certificate1.hiddenSSID = true;
    paramX509Certificate1.allowedKeyManagement.set(2);
    paramX509Certificate1.allowedKeyManagement.set(3);
    paramPrivateKey = ReflectionUtils.getInstanceField(paramX509Certificate1, "eap");
    paramString1 = "";
    switch (paramInt1)
    {
    default: 
      break;
    case 3: 
      paramString1 = "PWD";
      break;
    case 2: 
      paramString1 = "TTLS";
      break;
    case 1: 
      paramString1 = "TLS";
      break;
    case 0: 
      paramString1 = "PEAP";
    }
    ReflectionUtils.invokeInstance(paramPrivateKey, "setValue", new Class[] { String.class }, new Object[] { paramString1 });
    ReflectionUtils.invokeInstance(ReflectionUtils.getInstanceField(paramX509Certificate1, "phase2"), "setValue", new Class[] { String.class }, new Object[] { "" });
    ReflectionUtils.invokeInstance(ReflectionUtils.getInstanceField(paramX509Certificate1, "ca_cert"), "setValue", new Class[] { String.class }, new Object[] { "" });
    ReflectionUtils.invokeInstance(ReflectionUtils.getInstanceField(paramX509Certificate1, "client_cert"), "setValue", new Class[] { String.class }, new Object[] { "" });
    ReflectionUtils.invokeInstance(ReflectionUtils.getInstanceField(paramX509Certificate1, "key_id"), "setValue", new Class[] { String.class }, new Object[] { "" });
    ReflectionUtils.invokeInstance(ReflectionUtils.getInstanceField(paramX509Certificate1, "engine"), "setValue", new Class[] { String.class }, new Object[] { "" });
    ReflectionUtils.invokeInstance(ReflectionUtils.getInstanceField(paramX509Certificate1, "engine_id"), "setValue", new Class[] { String.class }, new Object[] { "0" });
    ReflectionUtils.invokeInstance(ReflectionUtils.getInstanceField(paramX509Certificate1, "identity"), "setValue", new Class[] { String.class }, new Object[] { paramString2 });
    ReflectionUtils.invokeInstance(ReflectionUtils.getInstanceField(paramX509Certificate1, "anonymous_identity"), "setValue", new Class[] { String.class }, new Object[] { paramString4 });
    ReflectionUtils.invokeInstance(ReflectionUtils.getInstanceField(paramX509Certificate1, "password"), "setValue", new Class[] { String.class }, new Object[] { paramString3 });
    paramX509Certificate1.status = 2;
    try
    {
      paramX509Certificate1.networkId = this.mWifiManager.addNetwork(paramX509Certificate1);
      return paramX509Certificate1;
    }
    catch (Exception paramString1)
    {
      LogUtils.d("WifiConnector", "addNetwork Exception");
      paramString1.printStackTrace();
    }
    return paramX509Certificate1;
  }
  
  @TargetApi(18)
  WifiConfiguration createEapConfigApi18(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, X509Certificate paramX509Certificate1, PrivateKey paramPrivateKey, X509Certificate paramX509Certificate2, String paramString4)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("createEapConfigApi18 ssid:");
    ((StringBuilder)localObject).append(paramString1);
    LogUtils.d("WifiConnector", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("createEapConfigApi18 eapMethod:");
    ((StringBuilder)localObject).append(paramInt1);
    LogUtils.d("WifiConnector", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("createEapConfigApi18 phase2Method:");
    ((StringBuilder)localObject).append(paramInt2);
    LogUtils.d("WifiConnector", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("createEapConfigApi18 identity:");
    ((StringBuilder)localObject).append(paramString2);
    LogUtils.d("WifiConnector", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("createEapConfigApi18 password:");
    ((StringBuilder)localObject).append(paramString3);
    LogUtils.d("WifiConnector", ((StringBuilder)localObject).toString());
    if (TextUtils.isEmpty(paramString1)) {
      return null;
    }
    localObject = new WifiConfiguration();
    ((WifiConfiguration)localObject).allowedAuthAlgorithms.clear();
    ((WifiConfiguration)localObject).allowedGroupCiphers.clear();
    ((WifiConfiguration)localObject).allowedKeyManagement.clear();
    ((WifiConfiguration)localObject).allowedPairwiseCiphers.clear();
    ((WifiConfiguration)localObject).allowedProtocols.clear();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("\"");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("\"");
    ((WifiConfiguration)localObject).SSID = localStringBuilder.toString();
    ((WifiConfiguration)localObject).hiddenSSID = true;
    ((WifiConfiguration)localObject).allowedKeyManagement.set(2);
    ((WifiConfiguration)localObject).allowedKeyManagement.set(3);
    ((WifiConfiguration)localObject).enterpriseConfig = new WifiEnterpriseConfig();
    ((WifiConfiguration)localObject).enterpriseConfig.setEapMethod(paramInt1);
    if (paramInt1 != 0) {
      ((WifiConfiguration)localObject).enterpriseConfig.setPhase2Method(paramInt2);
    } else {
      switch (paramInt2)
      {
      default: 
        paramString1 = new StringBuilder();
        paramString1.append("Unknown phase2 method");
        paramString1.append(paramInt2);
        LogUtils.e("WifiConnector", paramString1.toString());
        break;
      case 2: 
        ((WifiConfiguration)localObject).enterpriseConfig.setPhase2Method(4);
        break;
      case 1: 
        ((WifiConfiguration)localObject).enterpriseConfig.setPhase2Method(3);
        break;
      case 0: 
        ((WifiConfiguration)localObject).enterpriseConfig.setPhase2Method(0);
      }
    }
    ((WifiConfiguration)localObject).enterpriseConfig.setCaCertificate(paramX509Certificate1);
    ((WifiConfiguration)localObject).enterpriseConfig.setClientKeyEntry(paramPrivateKey, paramX509Certificate2);
    if (paramString2 == null) {
      paramString1 = "";
    } else {
      paramString1 = paramString2;
    }
    if (paramString4 == null) {
      paramString4 = "";
    }
    if ((paramInt1 != 4) && (paramInt1 != 5) && (paramInt1 != 6))
    {
      if (paramInt1 == 3)
      {
        ((WifiConfiguration)localObject).enterpriseConfig.setIdentity(paramString1);
        ((WifiConfiguration)localObject).enterpriseConfig.setAnonymousIdentity("");
      }
      else
      {
        ((WifiConfiguration)localObject).enterpriseConfig.setIdentity(paramString1);
        ((WifiConfiguration)localObject).enterpriseConfig.setAnonymousIdentity(paramString4);
      }
    }
    else
    {
      ((WifiConfiguration)localObject).enterpriseConfig.setIdentity("");
      ((WifiConfiguration)localObject).enterpriseConfig.setAnonymousIdentity("");
    }
    if (paramString3 == null) {
      paramString3 = "";
    }
    if (paramInt1 != 1) {
      ((WifiConfiguration)localObject).enterpriseConfig.setPassword(paramString3);
    }
    ((WifiConfiguration)localObject).status = 2;
    try
    {
      ((WifiConfiguration)localObject).networkId = this.mWifiManager.addNetwork((WifiConfiguration)localObject);
      return (WifiConfiguration)localObject;
    }
    catch (Exception paramString1)
    {
      LogUtils.d("WifiConnector", "addNetwork Exception");
      paramString1.printStackTrace();
    }
    return (WifiConfiguration)localObject;
  }
  
  WifiConfiguration createNormalConfig(String paramString1, int paramInt, String paramString2)
  {
    LogUtils.d("WifiConnector", "createNormalConfig");
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("ssid : ");
    ((StringBuilder)localObject).append(paramString1);
    LogUtils.d("WifiConnector", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("safeType : ");
    ((StringBuilder)localObject).append(paramInt);
    LogUtils.d("WifiConnector", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("pwd : ");
    ((StringBuilder)localObject).append(paramString2);
    LogUtils.d("WifiConnector", ((StringBuilder)localObject).toString());
    localObject = new WifiConfiguration();
    ((WifiConfiguration)localObject).allowedAuthAlgorithms.clear();
    ((WifiConfiguration)localObject).allowedGroupCiphers.clear();
    ((WifiConfiguration)localObject).allowedKeyManagement.clear();
    ((WifiConfiguration)localObject).allowedPairwiseCiphers.clear();
    ((WifiConfiguration)localObject).allowedProtocols.clear();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("\"");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("\"");
    ((WifiConfiguration)localObject).SSID = localStringBuilder.toString();
    ((WifiConfiguration)localObject).hiddenSSID = true;
    switch (paramInt)
    {
    default: 
      LogUtils.d("WifiConnector", "safeType unmatch");
      return null;
    case 2: 
      ((WifiConfiguration)localObject).allowedKeyManagement.set(0);
      ((WifiConfiguration)localObject).allowedProtocols.set(1);
      ((WifiConfiguration)localObject).allowedProtocols.set(0);
      ((WifiConfiguration)localObject).allowedAuthAlgorithms.set(0);
      ((WifiConfiguration)localObject).allowedAuthAlgorithms.set(1);
      ((WifiConfiguration)localObject).allowedPairwiseCiphers.set(2);
      ((WifiConfiguration)localObject).allowedPairwiseCiphers.set(1);
      ((WifiConfiguration)localObject).allowedGroupCiphers.set(0);
      ((WifiConfiguration)localObject).allowedGroupCiphers.set(1);
      paramString1 = ((WifiConfiguration)localObject).wepKeys;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("\"");
      localStringBuilder.append(paramString2);
      localStringBuilder.append("\"");
      paramString1[0] = localStringBuilder.toString();
      ((WifiConfiguration)localObject).wepTxKeyIndex = 0;
      break;
    case 1: 
      if (!TextUtils.isEmpty(paramString2)) {
        if (paramString2.matches("[0-9A-Fa-f]{64}"))
        {
          ((WifiConfiguration)localObject).preSharedKey = paramString2;
        }
        else
        {
          paramString1 = new StringBuilder();
          paramString1.append('"');
          paramString1.append(paramString2);
          paramString1.append('"');
          ((WifiConfiguration)localObject).preSharedKey = paramString1.toString();
        }
      }
      break;
    case 0: 
      ((WifiConfiguration)localObject).allowedKeyManagement.set(0);
    }
    ((WifiConfiguration)localObject).status = 2;
    try
    {
      ((WifiConfiguration)localObject).networkId = this.mWifiManager.addNetwork((WifiConfiguration)localObject);
      return (WifiConfiguration)localObject;
    }
    catch (Exception paramString1)
    {
      LogUtils.d("WifiConnector", "addNetwork Exception");
      paramString1.printStackTrace();
    }
    return (WifiConfiguration)localObject;
  }
  
  public void doConnectFail()
  {
    this.mConnetFailed += 1;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("doConnectFail[");
    localStringBuilder.append(this.mConnetFailed);
    localStringBuilder.append("]");
    LogUtils.d("WifiConnector", localStringBuilder.toString());
    LogUtils.d("WifiConnector", "remove MSG_CONNECT_NOW");
    this.mHandler.removeMessages(1);
    LogUtils.d("WifiConnector", "send MSG_CONNECT_NOW 100");
    this.mHandler.sendEmptyMessageDelayed(1, 100L);
  }
  
  Object getInvocationHandlerProxy(InvocationHandler paramInvocationHandler, String paramString)
    throws ClassNotFoundException
  {
    paramString = Class.forName(paramString);
    return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { paramString }, paramInvocationHandler);
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("handleMessage -> ");
    ((StringBuilder)localObject1).append(paramMessage.what);
    LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
    localObject1 = WifiBaseStateMonitor.getInstance().getLastState();
    int j = paramMessage.what;
    int i = 6;
    byte b1 = 0;
    Object localObject2;
    if (j != 1)
    {
      switch (j)
      {
      default: 
        return true;
      case 6: 
        LogUtils.d("WifiConnector", "MSG_ON_DO_STOP");
        switch (paramMessage.arg1)
        {
        default: 
          return true;
        case 2: 
          LogUtils.d("WifiConnector", "STOP_OUTER");
          this.mStopRetry = true;
          this.mHandler.removeMessages(1);
          if (!this.mIsConnecting) {
            break label6794;
          }
          if ((localObject1 != null) && (WifiBaseStateMonitor.isConnectingState(((Bundle)localObject1).getInt("STATE"))) && (TextUtils.equals(((Bundle)localObject1).getString("SSID"), this.mTargetSsid)))
          {
            LogUtils.d("WifiConnector", "CONNECTING");
            return true;
          }
          LogUtils.d("WifiConnector", "TRIGER UPLOAD");
          this.mHandler.sendEmptyMessage(1);
          return true;
        case 0: 
          paramMessage = new StringBuilder();
          paramMessage.append("STOP_TYPE_CHANGE_AP[");
          paramMessage.append(this.mHasReport);
          paramMessage.append("]");
          LogUtils.d("WifiConnector", paramMessage.toString());
          if (!this.mHasReport)
          {
            this.mHasReport = true;
            paramMessage = new HashMap();
            paramMessage.put("type", "con_fail_reason");
            paramMessage.put("key1", this.mSdkTag);
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("KEY_SDK : ");
            ((StringBuilder)localObject1).append(this.mSdkTag);
            LogUtils.d("con-report", ((StringBuilder)localObject1).toString());
            paramMessage.put("key2", "cancel");
            paramMessage.put("key5", String.valueOf(this.mRetryTimes));
            paramMessage.put("key3", String.valueOf(System.currentTimeMillis() - this.mStartTime));
            paramMessage.put("key4", String.valueOf(this.mConnectLevel));
            paramMessage.put("key6", this.mBaseStatePath);
            paramMessage.put("key7", this.mConnetStatePath);
            paramMessage.put("key8", String.valueOf(this.mTryIndex));
            paramMessage.put("key9", String.valueOf(this.mActiveInterval));
            paramMessage.put("key10", String.valueOf(this.mPwdCount));
            paramMessage.put("key12", this.mErrorPwdStr);
            paramMessage.put("key11", String.valueOf(this.mErrorPwdCount));
            paramMessage.put("key13", this.mPerConncetTimeStr);
            paramMessage.put("key14", String.valueOf(this.mConnetFailed));
            if (this.mPwdCount == this.mErrorPwdCount) {
              paramMessage.put("key15", String.valueOf(1));
            }
            paramMessage.put("key16", this.mConnectDate);
            paramMessage.put("key17", DeviceUtils.getDeviceBrand());
            paramMessage.put("key18", DeviceUtils.getDeviceManufacturer());
            paramMessage.put("error_code", "1");
            paramMessage.put("ft_name", "wifi");
            paramMessage.put("event_type", "1");
            paramMessage.put("event_result", "-1");
            paramMessage = new StringBuilder();
            paramMessage.append("FINAL PER_CONNECT_TIME : ");
            paramMessage.append(this.mPerConncetTimeStr);
            LogUtils.d("WifiConnector", paramMessage.toString());
            paramMessage = new StringBuilder();
            paramMessage.append("FINAL CONNECT_FAILED_COUNTS : ");
            paramMessage.append(this.mConnetFailed);
            LogUtils.d("WifiConnector", paramMessage.toString());
            paramMessage = new StringBuilder();
            paramMessage.append("FINAL TOTLE PWD : ");
            paramMessage.append(this.mErrorPwdCount);
            LogUtils.d("WifiConnector", paramMessage.toString());
            paramMessage = new StringBuilder();
            paramMessage.append("FINAL ERROR PWD : ");
            paramMessage.append(this.mErrorPwdStr);
            LogUtils.d("WifiConnector", paramMessage.toString());
            paramMessage = new StringBuilder();
            paramMessage.append("FINAL ERROR PWD COUNT : ");
            paramMessage.append(this.mErrorPwdCount);
            LogUtils.d("WifiConnector", paramMessage.toString());
            LogUtils.d("con-report", "--------------------------------");
            paramMessage = new StringBuilder();
            paramMessage.append("KEY_SDK : ");
            paramMessage.append(this.mSdkTag);
            LogUtils.d("con-report", paramMessage.toString());
            paramMessage = new StringBuilder();
            paramMessage.append("TARGET : ");
            paramMessage.append(this.mTargetSsid);
            LogUtils.d("con-report", paramMessage.toString());
            LogUtils.d("con-report", "KEY_REASON : cancel");
            paramMessage = new StringBuilder();
            paramMessage.append("KEY_RETRY_TIMES : ");
            paramMessage.append(this.mRetryTimes);
            LogUtils.d("con-report", paramMessage.toString());
            paramMessage = new StringBuilder();
            paramMessage.append("KEY_INTERVAL : ");
            paramMessage.append(String.valueOf(System.currentTimeMillis() - this.mStartTime));
            LogUtils.d("con-report", paramMessage.toString());
            paramMessage = new StringBuilder();
            paramMessage.append("KEY_LEVEL : ");
            paramMessage.append(this.mConnectLevel);
            LogUtils.d("con-report", paramMessage.toString());
          }
          break;
        }
        LogUtils.d("WifiConnector", "STOP_TYPE_FINAL");
        LogUtils.d("WifiConnector", "remove MSG_CONNECT_NOW");
        this.mHandler.removeMessages(6);
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(4);
        WifiBaseStateMonitor.getInstance().removeObserver(this);
        WiFiConnectStateMachine.getInstance().removeListener(this);
        this.mIsConnecting = false;
        this.mRetryTimes = 0;
        this.mStartTime = -1L;
        return true;
      case 5: 
        LogUtils.d("WifiConnector", "MSG_ON_DISCONNECT_REASON");
        if ((!this.mIsConnecting) || (!(paramMessage.obj instanceof Bundle))) {
          break;
        }
        paramMessage = (Bundle)paramMessage.obj;
        if (paramMessage.getInt("REASON") != 1) {
          break;
        }
        localObject2 = this.mConnectEvent.getConnectedInfo();
        if (localObject2 != null)
        {
          localObject1 = new StringBuilder("__");
          ((StringBuilder)localObject1).append(((WifiApInfo)localObject2).mSsid);
          ((StringBuilder)localObject1).append("_");
          ((StringBuilder)localObject1).append(((WifiApInfo)localObject2).mBssid);
          ((StringBuilder)localObject1).append("_");
          ((StringBuilder)localObject1).append(((WifiApInfo)localObject2).mSafeType);
          ((StringBuilder)localObject1).append("_");
          if (TextUtils.isEmpty(((WifiApInfo)localObject2).mPassword)) {
            ((StringBuilder)localObject1).append("EMP");
          } else {
            ((StringBuilder)localObject1).append(Md5Utils.getMD5(((WifiApInfo)localObject2).mPassword));
          }
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("ERROR PWD : ");
          ((StringBuilder)localObject2).append(((StringBuilder)localObject1).toString());
          LogUtils.d("WifiConnector", ((StringBuilder)localObject2).toString());
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append(this.mErrorPwdStr);
          ((StringBuilder)localObject2).append(((StringBuilder)localObject1).toString());
          this.mErrorPwdStr = ((StringBuilder)localObject2).toString();
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("[");
          ((StringBuilder)localObject1).append(this.mTargetSsid);
          ((StringBuilder)localObject1).append("]使用的sdk（");
          ((StringBuilder)localObject1).append(this.mSdkTag);
          ((StringBuilder)localObject1).append("）|信号强度（");
          ((StringBuilder)localObject1).append(this.mConnectLevel);
          ((StringBuilder)localObject1).append("）|重试(");
          ((StringBuilder)localObject1).append(this.mRetryTimes);
          ((StringBuilder)localObject1).append(")次 |连接时间（");
          ((StringBuilder)localObject1).append((System.currentTimeMillis() - this.mStartTime) / 1000L);
          ((StringBuilder)localObject1).append("秒）\r\n");
          addPath(((StringBuilder)localObject1).toString());
        }
        WifiUtils.forgetAp(WifiEngine.getInstance().getWifiManager(), this.mTargetNetWorkId);
        this.mErrorPwdCount += 1;
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("ERROR PWD COUNT: ");
        ((StringBuilder)localObject1).append(this.mErrorPwdCount);
        LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
        this.mConnectEvent.onPwdError(paramMessage.getString("SSID"));
        return true;
      case 4: 
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("MSG_ON_CONNECT_STATE_CHANGE[");
        ((StringBuilder)localObject1).append(this.mIsConnecting);
        ((StringBuilder)localObject1).append("]");
        LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
        if ((!this.mIsConnecting) || (!(paramMessage.obj instanceof Bundle))) {
          break;
        }
        paramMessage = (Bundle)paramMessage.obj;
        localObject1 = paramMessage.getString("SSID");
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("notifySsid : ");
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("[");
        ((StringBuilder)localObject2).append(WifiBaseStateMonitor.detailedState(paramMessage.getInt("STATE")).name());
        ((StringBuilder)localObject2).append("]");
        LogUtils.d("WifiConnector", ((StringBuilder)localObject2).toString());
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("mTargetSsid : ");
        ((StringBuilder)localObject2).append(this.mTargetSsid);
        LogUtils.d("WifiConnector", ((StringBuilder)localObject2).toString());
        if (TextUtils.equals((CharSequence)localObject1, this.mTargetSsid))
        {
          switch (WifiBaseStateMonitor.detailedState(paramMessage.getInt("STATE")))
          {
          default: 
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("state : [ ");
            ((StringBuilder)localObject1).append(this.mTargetSsid);
            ((StringBuilder)localObject1).append(" ] [ ");
            ((StringBuilder)localObject1).append(WifiBaseStateMonitor.detailedState(paramMessage.getInt("STATE")).name());
            ((StringBuilder)localObject1).append(" ]");
            LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
            if (WifiBaseStateMonitor.isDisconnectedState(paramMessage.getInt("STATE"))) {
              break label3285;
            }
            LogUtils.d("WifiConnector", "remove out time msg");
            LogUtils.d("WifiConnector", "remove MSG_CONNECT_NOW");
            this.mHandler.removeMessages(1);
            if ((this.mActiveInterval < 0L) && (this.mRetryTimes == 1))
            {
              this.mActiveInterval = (System.currentTimeMillis() - this.mConnectTime);
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("connecting interval : ");
              ((StringBuilder)localObject1).append(this.mActiveInterval);
              LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
            }
            break;
          case ???: 
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("CONNECTED[");
            ((StringBuilder)localObject1).append(this.mIsConnecting);
            ((StringBuilder)localObject1).append("]");
            LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
            if (this.mIsConnecting)
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append(this.mBaseStatePath);
              ((StringBuilder)localObject1).append(paramMessage.getInt("STATE"));
              ((StringBuilder)localObject1).append("_");
              this.mBaseStatePath = ((StringBuilder)localObject1).toString();
              if (!this.mHasReport)
              {
                this.mHasReport = true;
                localObject1 = new HashMap();
                ((Map)localObject1).put("type", "con_fail_reason");
                ((Map)localObject1).put("key1", this.mSdkTag);
                ((Map)localObject1).put("key2", "success");
                ((Map)localObject1).put("key5", String.valueOf(this.mRetryTimes));
                ((Map)localObject1).put("key3", String.valueOf(System.currentTimeMillis() - this.mStartTime));
                ((Map)localObject1).put("key4", String.valueOf(this.mConnectLevel));
                ((Map)localObject1).put("key6", this.mBaseStatePath);
                ((Map)localObject1).put("key7", this.mConnetStatePath);
                ((Map)localObject1).put("key8", String.valueOf(this.mTryIndex));
                ((Map)localObject1).put("key9", String.valueOf(this.mActiveInterval));
                ((Map)localObject1).put("key10", String.valueOf(this.mPwdCount));
                ((Map)localObject1).put("key12", this.mErrorPwdStr);
                ((Map)localObject1).put("key11", String.valueOf(this.mErrorPwdCount));
                ((Map)localObject1).put("key13", this.mPerConncetTimeStr);
                ((Map)localObject1).put("key14", String.valueOf(this.mConnetFailed));
                if (this.mPwdCount == this.mErrorPwdCount) {
                  ((Map)localObject1).put("key15", String.valueOf(1));
                }
                ((Map)localObject1).put("key16", this.mConnectDate);
                ((Map)localObject1).put("key17", DeviceUtils.getDeviceBrand());
                ((Map)localObject1).put("key18", DeviceUtils.getDeviceManufacturer());
                ((Map)localObject1).put("error_code", "0");
                ((Map)localObject1).put("ft_name", "wifi");
                ((Map)localObject1).put("event_type", "1");
                ((Map)localObject1).put("event_result", "0");
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("FINAL TOTLE PWD : ");
                ((StringBuilder)localObject1).append(this.mErrorPwdCount);
                LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("FINAL ERROR PWD : ");
                ((StringBuilder)localObject1).append(this.mErrorPwdStr);
                LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("FINAL ERROR PWD COUNT : ");
                ((StringBuilder)localObject1).append(this.mErrorPwdCount);
                LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
                LogUtils.d("con-report", "--------------------------------");
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("KEY_SDK : ");
                ((StringBuilder)localObject1).append(this.mSdkTag);
                LogUtils.d("con-report", ((StringBuilder)localObject1).toString());
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("TARGET : ");
                ((StringBuilder)localObject1).append(this.mTargetSsid);
                LogUtils.d("con-report", ((StringBuilder)localObject1).toString());
                LogUtils.d("con-report", "KEY_REASON : success");
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("KEY_RETRY_TIMES : ");
                ((StringBuilder)localObject1).append(this.mRetryTimes);
                LogUtils.d("con-report", ((StringBuilder)localObject1).toString());
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("KEY_INTERVAL : ");
                ((StringBuilder)localObject1).append(String.valueOf(System.currentTimeMillis() - this.mStartTime));
                LogUtils.d("con-report", ((StringBuilder)localObject1).toString());
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("KEY_LEVEL : ");
                ((StringBuilder)localObject1).append(this.mConnectLevel);
                LogUtils.d("con-report", ((StringBuilder)localObject1).toString());
              }
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("[");
              ((StringBuilder)localObject1).append(this.mTargetSsid);
              ((StringBuilder)localObject1).append("]使用的sdk（");
              ((StringBuilder)localObject1).append(this.mSdkTag);
              ((StringBuilder)localObject1).append("）|信号强度（");
              ((StringBuilder)localObject1).append(this.mConnectLevel);
              ((StringBuilder)localObject1).append("）|重试(");
              ((StringBuilder)localObject1).append(this.mRetryTimes);
              ((StringBuilder)localObject1).append(")次 |连接时间（");
              ((StringBuilder)localObject1).append((System.currentTimeMillis() - this.mStartTime) / 1000L);
              ((StringBuilder)localObject1).append("秒）| 結果 [成功]\r\n");
              addPath(((StringBuilder)localObject1).toString());
            }
            stop();
            this.mConnectEvent.onConnected();
            break;
          case ???: 
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("DISCONNECTED[");
            ((StringBuilder)localObject1).append(this.mWaitingConnecting);
            ((StringBuilder)localObject1).append("]");
            LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
            if (this.mWaitingConnecting) {
              break label3285;
            }
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("state : [ ");
            ((StringBuilder)localObject1).append(this.mTargetSsid);
            ((StringBuilder)localObject1).append(" ] [DISCONNECTED]");
            LogUtils.d("WifiConnector", ((StringBuilder)localObject1).toString());
            LogUtils.d("WifiConnector", "remove MSG_CONNECT_NOW");
            this.mHandler.removeMessages(1);
            LogUtils.d("WifiConnector", "send MSG_CONNECT_NOW 200 ");
            this.mHandler.sendEmptyMessageDelayed(1, 200L);
            break;
          }
          this.mWaitingConnecting = false;
        }
        else
        {
          switch (WifiBaseStateMonitor.detailedState(paramMessage.getInt("STATE")))
          {
          default: 
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("state : [ ");
            ((StringBuilder)localObject2).append((String)localObject1);
            ((StringBuilder)localObject2).append(" ] [ ");
            ((StringBuilder)localObject2).append(WifiBaseStateMonitor.detailedState(paramMessage.getInt("STATE")).name());
            ((StringBuilder)localObject2).append(" ]");
            LogUtils.d("WifiConnector", ((StringBuilder)localObject2).toString());
            break;
          case ???: 
          case ???: 
          case ???: 
            LogUtils.d("WifiConnector", "system changed  ap!");
            LogUtils.d("WifiConnector", "remove MSG_CONNECT_NOW");
            this.mHandler.removeMessages(1);
            LogUtils.d("WifiConnector", "send MSG_CONNECT_NOW 100");
            this.mHandler.sendEmptyMessageDelayed(1, 100L);
          }
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(this.mBaseStatePath);
          ((StringBuilder)localObject1).append("O");
          this.mBaseStatePath = ((StringBuilder)localObject1).toString();
        }
        label3285:
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(this.mBaseStatePath);
        ((StringBuilder)localObject1).append(paramMessage.getInt("STATE"));
        ((StringBuilder)localObject1).append("_");
        this.mBaseStatePath = ((StringBuilder)localObject1).toString();
        return true;
      }
    }
    else
    {
      paramMessage = new StringBuilder();
      paramMessage.append("MSG_CONNECT_NOW[");
      paramMessage.append(this.mRetryTimes);
      paramMessage.append("][");
      paramMessage.append(this.mStopRetry);
      paramMessage.append("]");
      LogUtils.d("WifiConnector", paramMessage.toString());
      j = this.mTargetNetWorkId;
      byte b2 = -1;
      if (j != -1)
      {
        LogUtils.d("WifiConnector", "remove config");
        WifiUtils.forgetAp(WifiUtils.getWifiManager(), this.mTargetNetWorkId);
      }
      LogUtils.d("WifiConnector", "MSG_CONNECT_NOW");
      if (this.mPerStartTime > 0L)
      {
        paramMessage = new StringBuilder();
        paramMessage.append(this.mPerConncetTimeStr);
        paramMessage.append(this.mRetryTimes);
        paramMessage.append("_");
        paramMessage.append(System.currentTimeMillis() - this.mPerStartTime);
        paramMessage.append("__");
        this.mPerConncetTimeStr = paramMessage.toString();
      }
      this.mPerStartTime = System.currentTimeMillis();
      if ((!this.mStopRetry) && (this.mRetryTimes <= this.mMaxRetryTimes) && (System.currentTimeMillis() - this.mStartTime < 20000L))
      {
        if (this.mRetryTimes > 0)
        {
          paramMessage = WifiUtils.getScanResults();
          if ((paramMessage != null) && (!paramMessage.isEmpty()))
          {
            paramMessage = paramMessage.iterator();
            while (paramMessage.hasNext()) {
              if (TextUtils.equals(WifiCommonUtils.getSsidWithoutQuotation(((ScanResult)paramMessage.next()).SSID), this.mTargetSsid))
              {
                i = 1;
                break label3621;
              }
            }
          }
          i = 0;
          label3621:
          if (i == 0)
          {
            paramMessage = new StringBuilder();
            paramMessage.append("SIGNAL_LOST[");
            paramMessage.append(this.mIsConnecting);
            paramMessage.append("]");
            LogUtils.d("WifiConnector", paramMessage.toString());
            if (this.mIsConnecting)
            {
              if (!this.mHasReport)
              {
                this.mHasReport = true;
                paramMessage = new HashMap();
                paramMessage.put("type", "con_fail_reason");
                paramMessage.put("key1", this.mSdkTag);
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("KEY_SDK : ");
                ((StringBuilder)localObject1).append(this.mSdkTag);
                LogUtils.d("con-report", ((StringBuilder)localObject1).toString());
                paramMessage.put("key2", "lostSignal");
                paramMessage.put("key5", String.valueOf(this.mRetryTimes));
                paramMessage.put("key3", String.valueOf(System.currentTimeMillis() - this.mStartTime));
                paramMessage.put("key4", String.valueOf(this.mConnectLevel));
                paramMessage.put("key6", this.mBaseStatePath);
                paramMessage.put("key7", this.mConnetStatePath);
                paramMessage.put("key8", String.valueOf(this.mTryIndex));
                paramMessage.put("key9", String.valueOf(this.mActiveInterval));
                paramMessage.put("key10", String.valueOf(this.mPwdCount));
                paramMessage.put("key12", this.mErrorPwdStr);
                paramMessage.put("key11", String.valueOf(this.mErrorPwdCount));
                paramMessage.put("key13", this.mPerConncetTimeStr);
                paramMessage.put("key14", String.valueOf(this.mConnetFailed));
                if (this.mPwdCount == this.mErrorPwdCount) {
                  paramMessage.put("key15", String.valueOf(1));
                }
                paramMessage.put("key16", this.mConnectDate);
                paramMessage.put("key17", DeviceUtils.getDeviceBrand());
                paramMessage.put("key18", DeviceUtils.getDeviceManufacturer());
                paramMessage.put("error_code", "2");
                paramMessage.put("ft_name", "wifi");
                paramMessage.put("event_type", "1");
                paramMessage.put("event_result", "-1");
                paramMessage = new StringBuilder();
                paramMessage.append("FINAL PER_CONNECT_TIME : ");
                paramMessage.append(this.mPerConncetTimeStr);
                LogUtils.d("WifiConnector", paramMessage.toString());
                paramMessage = new StringBuilder();
                paramMessage.append("FINAL CONNECT_FAILED_COUNTS : ");
                paramMessage.append(this.mConnetFailed);
                LogUtils.d("WifiConnector", paramMessage.toString());
                paramMessage = new StringBuilder();
                paramMessage.append("FINAL TOTLE PWD : ");
                paramMessage.append(this.mErrorPwdCount);
                LogUtils.d("WifiConnector", paramMessage.toString());
                paramMessage = new StringBuilder();
                paramMessage.append("FINAL ERROR PWD : ");
                paramMessage.append(this.mErrorPwdStr);
                LogUtils.d("WifiConnector", paramMessage.toString());
                paramMessage = new StringBuilder();
                paramMessage.append("FINAL ERROR PWD COUNT : ");
                paramMessage.append(this.mErrorPwdCount);
                LogUtils.d("WifiConnector", paramMessage.toString());
                LogUtils.d("con-report", "--------------------------------");
                paramMessage = new StringBuilder();
                paramMessage.append("KEY_SDK : ");
                paramMessage.append(this.mSdkTag);
                LogUtils.d("con-report", paramMessage.toString());
                paramMessage = new StringBuilder();
                paramMessage.append("TARGET : ");
                paramMessage.append(this.mTargetSsid);
                LogUtils.d("con-report", paramMessage.toString());
                LogUtils.d("con-report", "KEY_REASON : lostSignal");
                paramMessage = new StringBuilder();
                paramMessage.append("KEY_RETRY_TIMES : ");
                paramMessage.append(this.mRetryTimes);
                LogUtils.d("con-report", paramMessage.toString());
                paramMessage = new StringBuilder();
                paramMessage.append("KEY_INTERVAL : ");
                paramMessage.append(String.valueOf(System.currentTimeMillis() - this.mStartTime));
                LogUtils.d("con-report", paramMessage.toString());
                paramMessage = new StringBuilder();
                paramMessage.append("KEY_LEVEL : ");
                paramMessage.append(this.mConnectLevel);
                LogUtils.d("con-report", paramMessage.toString());
              }
              paramMessage = new StringBuilder();
              paramMessage.append("[");
              paramMessage.append(this.mTargetSsid);
              paramMessage.append("]使用的sdk（");
              paramMessage.append(this.mSdkTag);
              paramMessage.append("）|信号强度（");
              paramMessage.append(this.mConnectLevel);
              paramMessage.append("）|重试(");
              paramMessage.append(this.mRetryTimes);
              paramMessage.append(")次 |连接时间（");
              paramMessage.append((System.currentTimeMillis() - this.mStartTime) / 1000L);
              paramMessage.append("秒）|結果[失敗]  原因[信号丢失]\r\n");
              addPath(paramMessage.toString());
              stop();
              LogUtils.d("WifiConnector", "onConnectedFailed");
              this.mConnectEvent.onConnectedFailed((byte)-4);
            }
            return true;
          }
        }
        this.mRetryTimes += 1;
        if ((localObject1 != null) && (TextUtils.equals(((Bundle)localObject1).getString("SSID"), this.mTargetSsid)) && (!WifiBaseStateMonitor.isDisconnectedState(((Bundle)localObject1).getInt("STATE"))))
        {
          LogUtils.d("WifiConnector", "cur ap connectting...");
          if (WifiBaseStateMonitor.getInstance().isConnected())
          {
            LogUtils.d("WifiConnector", "onConnectedSuccess-already");
            this.mConnectEvent.onConnected();
            if (!this.mHasReport)
            {
              this.mHasReport = true;
              paramMessage = new HashMap();
              paramMessage.put("type", "con_fail_reason");
              paramMessage.put("key1", this.mSdkTag);
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("KEY_SDK : ");
              ((StringBuilder)localObject1).append(this.mSdkTag);
              LogUtils.d("con-report", ((StringBuilder)localObject1).toString());
              paramMessage.put("key2", "success");
              paramMessage.put("key5", String.valueOf(this.mRetryTimes));
              paramMessage.put("key3", String.valueOf(System.currentTimeMillis() - this.mStartTime));
              paramMessage.put("key4", String.valueOf(this.mConnectLevel));
              paramMessage.put("key8", String.valueOf(this.mTryIndex));
              paramMessage.put("key9", String.valueOf(this.mActiveInterval));
              paramMessage.put("key10", String.valueOf(this.mPwdCount));
              paramMessage.put("key12", this.mErrorPwdStr);
              paramMessage.put("key11", String.valueOf(this.mErrorPwdCount));
              paramMessage.put("key6", this.mBaseStatePath);
              paramMessage.put("key7", this.mConnetStatePath);
              paramMessage.put("key13", this.mPerConncetTimeStr);
              paramMessage.put("key14", String.valueOf(this.mConnetFailed));
              paramMessage.put("key15", "already");
              paramMessage.put("key16", this.mConnectDate);
              paramMessage.put("key17", DeviceUtils.getDeviceBrand());
              paramMessage.put("key18", DeviceUtils.getDeviceManufacturer());
              paramMessage.put("error_code", "0");
              paramMessage.put("ft_name", "wifi");
              paramMessage.put("event_type", "1");
              paramMessage.put("event_result", "0");
              paramMessage = new StringBuilder();
              paramMessage.append("FINAL TOTLE PWD : ");
              paramMessage.append(this.mErrorPwdCount);
              LogUtils.d("WifiConnector", paramMessage.toString());
              paramMessage = new StringBuilder();
              paramMessage.append("FINAL ERROR PWD : ");
              paramMessage.append(this.mErrorPwdStr);
              LogUtils.d("WifiConnector", paramMessage.toString());
              paramMessage = new StringBuilder();
              paramMessage.append("FINAL ERROR PWD COUNT : ");
              paramMessage.append(this.mErrorPwdCount);
              LogUtils.d("WifiConnector", paramMessage.toString());
              LogUtils.d("con-report", "--------------------------------");
              paramMessage = new StringBuilder();
              paramMessage.append("KEY_SDK : ");
              paramMessage.append(this.mSdkTag);
              LogUtils.d("con-report", paramMessage.toString());
              paramMessage = new StringBuilder();
              paramMessage.append("TARGET : ");
              paramMessage.append(this.mTargetSsid);
              LogUtils.d("con-report", paramMessage.toString());
              LogUtils.d("con-report", "KEY_REASON : success");
              paramMessage = new StringBuilder();
              paramMessage.append("KEY_RETRY_TIMES : ");
              paramMessage.append(this.mRetryTimes);
              LogUtils.d("con-report", paramMessage.toString());
              paramMessage = new StringBuilder();
              paramMessage.append("KEY_INTERVAL : ");
              paramMessage.append(String.valueOf(System.currentTimeMillis() - this.mStartTime));
              LogUtils.d("con-report", paramMessage.toString());
              paramMessage = new StringBuilder();
              paramMessage.append("KEY_LEVEL : ");
              paramMessage.append(this.mConnectLevel);
              LogUtils.d("con-report", paramMessage.toString());
            }
            paramMessage = new StringBuilder();
            paramMessage.append("[");
            paramMessage.append(this.mTargetSsid);
            paramMessage.append("]使用的sdk（");
            paramMessage.append(this.mSdkTag);
            paramMessage.append("）|信号强度（");
            paramMessage.append(this.mConnectLevel);
            paramMessage.append("）|重试(");
            paramMessage.append(this.mRetryTimes);
            paramMessage.append(")次 |连接时间（");
            paramMessage.append((System.currentTimeMillis() - this.mStartTime) / 1000L);
            paramMessage.append("秒）| 結果 [成功-其他连接]\r\n");
            addPath(paramMessage.toString());
            stop();
            return true;
          }
          paramMessage = new StringBuilder();
          paramMessage.append("doConnect1[");
          paramMessage.append(this.mTargetSsid);
          paramMessage.append("]");
          LogUtils.d("WifiConnector", paramMessage.toString());
          this.mWaitingConnecting = false;
          this.mIsConnecting = true;
          LogUtils.d("WifiConnector", "remove MSG_CONNECT_NOW");
          this.mHandler.removeMessages(1);
          LogUtils.d("WifiConnector", "send MSG_CONNECT_NOW 500");
          this.mHandler.sendEmptyMessageDelayed(1, this.mTimeOut);
          return true;
        }
        paramMessage = new StringBuilder();
        paramMessage.append("doConnect2[");
        paramMessage.append(this.mTargetSsid);
        paramMessage.append("]");
        LogUtils.d("WifiConnector", paramMessage.toString());
        this.mWaitingConnecting = true;
        this.mConnectEvent.doConnect(this.mTargetSsid);
        this.mIsConnecting = true;
        LogUtils.d("WifiConnector", "remove MSG_CONNECT_NOW");
        this.mHandler.removeMessages(1);
        LogUtils.d("WifiConnector", "send MSG_CONNECT_NOW 500");
        this.mHandler.sendEmptyMessageDelayed(1, this.mTimeOut);
        return true;
      }
      paramMessage = new StringBuilder();
      paramMessage.append("doConnectFailed[");
      paramMessage.append(this.mIsConnecting);
      paramMessage.append("]");
      LogUtils.d("WifiConnector", paramMessage.toString());
      if (this.mIsConnecting)
      {
        if (!this.mHasReport)
        {
          this.mHasReport = true;
          j = 3;
          localObject1 = new HashMap();
          ((Map)localObject1).put("type", "con_fail_reason");
          ((Map)localObject1).put("key1", this.mSdkTag);
          paramMessage = new StringBuilder();
          paramMessage.append("KEY_SDK : ");
          paramMessage.append(this.mSdkTag);
          LogUtils.d("con-report", paramMessage.toString());
          if ((this.mRetryTimes >= this.mMaxRetryTimes) && (System.currentTimeMillis() - this.mStartTime >= 20000L))
          {
            ((Map)localObject1).put("key2", "ov_all");
            LogUtils.d("con-report", "KEY_REASON : ov_all");
            b1 = -3;
            i = j;
          }
          else
          {
            b1 = b2;
            if (this.mRetryTimes >= this.mMaxRetryTimes) {
              b1 = -2;
            }
            if (this.mStopRetry)
            {
              paramMessage = "outer";
            }
            else
            {
              if (this.mRetryTimes >= this.mMaxRetryTimes) {
                paramMessage = "ov_c";
              } else {
                paramMessage = "ov_t";
              }
              if (this.mRetryTimes >= this.mMaxRetryTimes) {
                i = 4;
              } else {
                i = 5;
              }
            }
            ((Map)localObject1).put("key2", paramMessage);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("KEY_REASON : ");
            ((StringBuilder)localObject2).append(paramMessage);
            LogUtils.d("con-report", ((StringBuilder)localObject2).toString());
          }
          ((Map)localObject1).put("key5", String.valueOf(this.mRetryTimes));
          ((Map)localObject1).put("key3", String.valueOf(System.currentTimeMillis() - this.mStartTime));
          ((Map)localObject1).put("key4", String.valueOf(this.mConnectLevel));
          ((Map)localObject1).put("key8", String.valueOf(this.mTryIndex));
          ((Map)localObject1).put("key9", String.valueOf(this.mActiveInterval));
          ((Map)localObject1).put("key10", String.valueOf(this.mPwdCount));
          ((Map)localObject1).put("key12", this.mErrorPwdStr);
          ((Map)localObject1).put("key11", String.valueOf(this.mErrorPwdCount));
          ((Map)localObject1).put("key6", this.mBaseStatePath);
          ((Map)localObject1).put("key7", this.mConnetStatePath);
          ((Map)localObject1).put("key13", this.mPerConncetTimeStr);
          ((Map)localObject1).put("key14", String.valueOf(this.mConnetFailed));
          if (this.mPwdCount == this.mErrorPwdCount) {
            ((Map)localObject1).put("key15", String.valueOf(1));
          }
          ((Map)localObject1).put("key16", this.mConnectDate);
          ((Map)localObject1).put("key17", DeviceUtils.getDeviceBrand());
          ((Map)localObject1).put("key18", DeviceUtils.getDeviceManufacturer());
          ((Map)localObject1).put("error_code", String.valueOf(i));
          ((Map)localObject1).put("ft_name", "wifi");
          ((Map)localObject1).put("event_type", "1");
          ((Map)localObject1).put("event_result", "-1");
          paramMessage = new StringBuilder();
          paramMessage.append("FINAL TOTLE PWD : ");
          paramMessage.append(this.mErrorPwdCount);
          LogUtils.d("WifiConnector", paramMessage.toString());
          paramMessage = new StringBuilder();
          paramMessage.append("FINAL ERROR PWD : ");
          paramMessage.append(this.mErrorPwdStr);
          LogUtils.d("WifiConnector", paramMessage.toString());
          paramMessage = new StringBuilder();
          paramMessage.append("FINAL ERROR PWD COUNT : ");
          paramMessage.append(this.mErrorPwdCount);
          LogUtils.d("WifiConnector", paramMessage.toString());
          LogUtils.d("con-report", "--------------------------------");
          paramMessage = new StringBuilder();
          paramMessage.append("KEY_SDK : ");
          paramMessage.append(this.mSdkTag);
          LogUtils.d("con-report", paramMessage.toString());
          paramMessage = new StringBuilder();
          paramMessage.append("TARGET : ");
          paramMessage.append(this.mTargetSsid);
          LogUtils.d("con-report", paramMessage.toString());
          paramMessage = new StringBuilder();
          paramMessage.append("KEY_REASON : ");
          paramMessage.append((String)((Map)localObject1).get("key2"));
          LogUtils.d("con-report", paramMessage.toString());
          paramMessage = new StringBuilder();
          paramMessage.append("KEY_RETRY_TIMES : ");
          paramMessage.append(this.mRetryTimes);
          LogUtils.d("con-report", paramMessage.toString());
          paramMessage = new StringBuilder();
          paramMessage.append("KEY_INTERVAL : ");
          paramMessage.append(String.valueOf(System.currentTimeMillis() - this.mStartTime));
          LogUtils.d("con-report", paramMessage.toString());
          paramMessage = new StringBuilder();
          paramMessage.append("KEY_LEVEL : ");
          paramMessage.append(this.mConnectLevel);
          LogUtils.d("con-report", paramMessage.toString());
        }
        paramMessage = new StringBuilder();
        paramMessage.append("[");
        paramMessage.append(this.mTargetSsid);
        paramMessage.append("]使用的sdk（");
        paramMessage.append(this.mSdkTag);
        paramMessage.append("）|信号强度（");
        paramMessage.append(this.mConnectLevel);
        paramMessage.append("）|重试(");
        paramMessage.append(this.mRetryTimes);
        paramMessage.append(")次 |连接时间（");
        paramMessage.append((System.currentTimeMillis() - this.mStartTime) / 1000L);
        paramMessage.append("秒）| 結果 [失敗]\r\n");
        addPath(paramMessage.toString());
        stop();
        LogUtils.d("WifiConnector", "onConnectedFailed");
        paramMessage = new StringBuilder();
        paramMessage.append("reason[");
        paramMessage.append(b1);
        paramMessage.append("]");
        LogUtils.d("WifiConnector", paramMessage.toString());
        this.mConnectEvent.onConnectedFailed(b1);
      }
    }
    label6794:
    return true;
  }
  
  public void onStateChange(Bundle paramBundle, int paramInt)
  {
    Message localMessage;
    if (paramInt == 0)
    {
      localMessage = this.mHandler.obtainMessage(4);
      localMessage.obj = paramBundle;
      localMessage.sendToTarget();
      return;
    }
    if (paramInt == 1)
    {
      localMessage = this.mHandler.obtainMessage(5);
      localMessage.obj = paramBundle;
      localMessage.sendToTarget();
    }
  }
  
  public void onStateChange(WiFiConnectStateMachine.WiFiConnectState paramWiFiConnectState)
  {
    if (paramWiFiConnectState != null)
    {
      if (!TextUtils.equals(paramWiFiConnectState.getSsid(), this.mTargetSsid))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.mConnetStatePath);
        localStringBuilder.append("O");
        this.mConnetStatePath = localStringBuilder.toString();
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.mConnetStatePath);
      localStringBuilder.append(paramWiFiConnectState.getIntStateType());
      localStringBuilder.append("_");
      this.mConnetStatePath = localStringBuilder.toString();
    }
  }
  
  public void setConnectEvent(ConnectEvent paramConnectEvent)
  {
    this.mConnectEvent = paramConnectEvent;
  }
  
  public void start()
  {
    LogUtils.d("WifiConnector", "start");
    this.mStartTime = System.currentTimeMillis();
    LogUtils.d("WifiConnector", "start");
    LogUtils.d("WifiConnector", "remove MSG_CONNECT_NOW");
    this.mHandler.removeMessages(1);
    this.mHandler.removeMessages(4);
    LogUtils.d("WifiConnector", "send MSG_CONNECT_NOW");
    this.mHandler.sendEmptyMessage(1);
  }
  
  public void stop(int paramInt)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("stop[");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append("]");
    LogUtils.d("WifiConnector", ((StringBuilder)localObject).toString());
    localObject = this.mHandler.obtainMessage(6);
    ((Message)localObject).arg1 = paramInt;
    this.mHandler.sendMessageAtFrontOfQueue((Message)localObject);
  }
  
  class ActionInvocationHandler
    implements InvocationHandler
  {
    ActionInvocationHandler() {}
    
    public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
      throws Throwable
    {
      if ((paramMethod != null) && (paramMethod.getName().compareTo("onSuccess") != 0) && (paramMethod.getName().compareTo("onFailure") == 0))
      {
        LogUtils.d("WifiConnector", "remove MSG_CONNECT_NOW");
        WifiConnector.this.mHandler.removeMessages(1);
        WifiConnector.this.mHandler.sendEmptyMessageDelayed(1, 100L);
        LogUtils.d("WifiConnector", "send MSG_CONNECT_NOW 100");
        WifiUtils.forgetAp(WifiUtils.getWifiManager(), WifiConnector.this.mTargetNetWorkId);
      }
      return null;
    }
  }
  
  class ChannelInvocationHandler
    implements InvocationHandler
  {
    ChannelInvocationHandler() {}
    
    public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
      throws Throwable
    {
      return null;
    }
  }
  
  public static abstract interface ConnectEvent
  {
    public abstract void doConnect(String paramString);
    
    public abstract WifiApInfo getConnectedInfo();
    
    public abstract void onConnected();
    
    public abstract void onConnectedFailed(byte paramByte);
    
    public abstract void onPwdError(String paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\WifiConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */