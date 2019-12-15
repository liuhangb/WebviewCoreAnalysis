package com.tencent.tbs.common.wifi.state;

import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.mtt.ContextHolder;
import com.tencent.mtt.browser.engine.AppBroadcastObserver;
import com.tencent.mtt.browser.engine.AppBroadcastReceiver;
import com.tencent.tbs.common.wifi.WifiCommonUtils;
import com.tencent.tbs.common.wifi.WifiUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WifiBaseStateMonitor
  implements Handler.Callback, AppBroadcastObserver
{
  public static final String KEY_CONFIG = "CONFIG";
  public static final String KEY_DETAILSTATE = "DETAILSTATE";
  public static final String KEY_DISCONNECT_REASON = "REASON";
  public static final String KEY_INTENT = "INTENT";
  public static final String KEY_NETWORKINFO = "NETWORKINFO";
  public static final String KEY_SSID = "SSID";
  public static final String KEY_STATE = "STATE";
  public static final String KEY_WIFIINFO = "WIFIINFO";
  private static final int MSG_UI_NOTIFY_DISCONNECT_REASON = 1001;
  private static final int MSG_UI_NOTIFY_STATE_CHANGE = 1000;
  private static final int MSG_WORK_CHECK_SUPPLICANT_STATE = 6;
  private static final int MSG_WORK_ON_NETWORK_CONFIG_CHANGED = 2;
  private static final int MSG_WORK_ON_NETWORK_STATE_CHANGED = 3;
  private static final int MSG_WORK_ON_SCREEN_ON = 5;
  private static final int MSG_WORK_ON_SUPPLICANT_STATE_CHANGED = 4;
  public static final int STATE_TYPE_CONNECT_STATE = 0;
  public static final int STATE_TYPE_DISCONNECT_REASON = 1;
  private static final String TAG = "WifiBaseStateMonitor";
  private static WifiBaseStateMonitor mInstance;
  final int MAX_PATH_COUNTS;
  SparseArray<WifiConfiguration> mConfigMap = new SparseArray();
  private Bundle mLastState;
  private Bundle mLastStateCopy;
  ConcurrentLinkedQueue<WifiBaseStateObserver> mObservers;
  ArrayList<String> mStatePath;
  Handler mUIHandler;
  Handler mWorkerHandler;
  
  private WifiBaseStateMonitor()
  {
    NetworkInfo.DetailedState localDetailedState = null;
    this.mWorkerHandler = null;
    this.mUIHandler = null;
    this.mObservers = new ConcurrentLinkedQueue();
    this.mLastState = null;
    this.mLastStateCopy = null;
    this.MAX_PATH_COUNTS = 80;
    this.mStatePath = new ArrayList();
    this.mWorkerHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime(), this);
    this.mUIHandler = new Handler(Looper.getMainLooper(), this);
    refrashConfigMap();
    Object localObject2 = WifiUtils.getNetworkInfo();
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = WifiUtils.getNetworkInfo();
    }
    WifiInfo localWifiInfo = WifiUtils.getConnectionInfo();
    localObject2 = localWifiInfo;
    if (localWifiInfo == null) {
      localObject2 = WifiUtils.getConnectionInfo();
    }
    addPath("[init]");
    if (localObject1 != null) {
      localDetailedState = ((NetworkInfo)localObject1).getDetailedState();
    }
    refreshStateBundle((NetworkInfo)localObject1, (WifiInfo)localObject2, localDetailedState);
    localObject1 = this.mLastState;
    if (localObject1 != null) {
      this.mLastStateCopy = new Bundle((Bundle)localObject1);
    }
    AppBroadcastReceiver.getInstance().register(ContextHolder.getAppContext());
    AppBroadcastReceiver.getInstance().addBroadcastObserver(this);
  }
  
  public static final NetworkInfo.DetailedState detailedState(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < NetworkInfo.DetailedState.values().length)) {
      return NetworkInfo.DetailedState.values()[paramInt];
    }
    return null;
  }
  
  public static final NetworkInfo.DetailedState detailedState(Bundle paramBundle)
  {
    if (paramBundle != null) {
      return detailedState(paramBundle.getInt("STATE"));
    }
    return null;
  }
  
  public static <T> T get(Bundle paramBundle, String paramString)
  {
    if (paramBundle != null)
    {
      paramBundle = paramBundle.get(paramString);
      if (paramBundle != null) {
        return paramBundle;
      }
    }
    return null;
  }
  
  public static WifiBaseStateMonitor getInstance()
  {
    if (mInstance == null) {
      try
      {
        mInstance = new WifiBaseStateMonitor();
      }
      finally {}
    }
    return mInstance;
  }
  
  private boolean handleUIMsg(Message paramMessage)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("handleUIMsg ： ");
    ((StringBuilder)localObject).append(paramMessage.what);
    ((StringBuilder)localObject).toString();
    StringBuilder localStringBuilder;
    switch (paramMessage.what)
    {
    default: 
      return true;
    case 1001: 
      if ((paramMessage.obj instanceof Bundle))
      {
        localObject = (Bundle)paramMessage.obj;
        paramMessage = new StringBuilder();
        paramMessage.append("CONNECT-DISCONNECT-REASON-NOTIFY--");
        paramMessage.append(localObject);
        paramMessage.toString();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("CONNECT-DISCONNECT-REASON-NOTIFY [ ");
        localStringBuilder.append(((Bundle)localObject).getString("SSID"));
        localStringBuilder.append(" ] -->");
        if (((Bundle)localObject).getInt("STATE") < 0) {
          paramMessage = Integer.valueOf(-1);
        } else {
          paramMessage = NetworkInfo.DetailedState.values()[localObject.getInt("STATE")];
        }
        localStringBuilder.append(paramMessage);
        localStringBuilder.toString();
        paramMessage = this.mObservers.iterator();
      }
      break;
    case 1000: 
      while (paramMessage.hasNext())
      {
        ((WifiBaseStateObserver)paramMessage.next()).onStateChange((Bundle)localObject, 1);
        continue;
        if ((paramMessage.obj instanceof Bundle))
        {
          localObject = (Bundle)paramMessage.obj;
          paramMessage = new StringBuilder();
          paramMessage.append("CONNECT-STATE-NOTIFY--");
          paramMessage.append(localObject);
          paramMessage.toString();
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("CONNECT-STATE-NOTIFY [ ");
          localStringBuilder.append(((Bundle)localObject).getString("SSID"));
          localStringBuilder.append(" ] -->");
          if (((Bundle)localObject).getInt("STATE") < 0) {
            paramMessage = Integer.valueOf(-1);
          } else {
            paramMessage = NetworkInfo.DetailedState.values()[localObject.getInt("STATE")];
          }
          localStringBuilder.append(paramMessage);
          localStringBuilder.toString();
          localStringBuilder = new StringBuilder();
          localStringBuilder.append(System.currentTimeMillis());
          localStringBuilder.append(" : [ ");
          localStringBuilder.append(((Bundle)localObject).getString("SSID"));
          localStringBuilder.append(" ] -->");
          if (((Bundle)localObject).getInt("STATE") < 0) {
            paramMessage = Integer.valueOf(-1);
          } else {
            paramMessage = NetworkInfo.DetailedState.values()[localObject.getInt("STATE")];
          }
          localStringBuilder.append(paramMessage);
          localStringBuilder.toString();
          paramMessage = this.mObservers.iterator();
          while (paramMessage.hasNext()) {
            ((WifiBaseStateObserver)paramMessage.next()).onStateChange((Bundle)localObject, 0);
          }
        }
      }
    }
    return true;
  }
  
  private boolean handleWorkerMsg(Message paramMessage)
  {
    int j = paramMessage.what;
    Object localObject1 = null;
    boolean bool2 = false;
    int i = 0;
    switch (j)
    {
    default: 
      return true;
    case 6: 
      if (!(paramMessage.obj instanceof Intent)) {
        break;
      }
    }
    for (;;)
    {
      Object localObject2;
      Object localObject4;
      try
      {
        paramMessage = (Intent)paramMessage.obj;
        localObject3 = WifiUtils.getConnectionInfo();
        localObject1 = (SupplicantState)paramMessage.getParcelableExtra("newState");
        localObject2 = WifiUtils.getNetworkInfo();
        localObject4 = new StringBuilder();
        ((StringBuilder)localObject4).append("networkInfo : ");
        ((StringBuilder)localObject4).append(localObject2);
        ((StringBuilder)localObject4).toString();
        localObject4 = new StringBuilder();
        ((StringBuilder)localObject4).append("wifiInfo : ");
        ((StringBuilder)localObject4).append(localObject3);
        ((StringBuilder)localObject4).toString();
        if ((localObject2 != null) && (!((NetworkInfo)localObject2).isConnected())) {}
        switch (localObject1)
        {
        case ???: 
        case ???: 
        case ???: 
        case ???: 
        case ???: 
          localObject3 = new Bundle();
          ((Bundle)localObject3).putParcelable("NETWORKINFO", (Parcelable)localObject2);
          ((Bundle)localObject3).putInt("DETAILSTATE", WifiInfo.getDetailedStateOf((SupplicantState)localObject1).ordinal());
          localObject4 = this.mWorkerHandler.obtainMessage(3);
          ((Message)localObject4).obj = localObject3;
          ((Message)localObject4).sendToTarget();
          localObject3 = new Bundle();
          ((Bundle)localObject3).putParcelable("INTENT", paramMessage);
          ((Bundle)localObject3).putParcelable("NETWORKINFO", (Parcelable)localObject2);
          ((Bundle)localObject3).putInt("DETAILSTATE", WifiInfo.getDetailedStateOf((SupplicantState)localObject1).ordinal());
          paramMessage = this.mWorkerHandler.obtainMessage(4);
          paramMessage.obj = localObject3;
          paramMessage.sendToTarget();
          return true;
        }
      }
      catch (Throwable paramMessage)
      {
        paramMessage.printStackTrace();
        return true;
      }
      Object localObject3 = WifiUtils.getConnectionInfo();
      paramMessage = new StringBuilder();
      paramMessage.append("");
      paramMessage.append(localObject3);
      paramMessage.toString();
      if (localObject3 != null)
      {
        localObject2 = WifiCommonUtils.getSsidWithoutQuotation(((WifiInfo)localObject3).getSSID());
        paramMessage = (String)get(this.mLastStateCopy, "SSID");
        if (TextUtils.equals((CharSequence)localObject2, paramMessage))
        {
          localObject2 = (WifiInfo)get(this.mLastStateCopy, "WIFIINFO");
          if (localObject2 != null)
          {
            if (((WifiInfo)localObject2).getSupplicantState() != ((WifiInfo)localObject3).getSupplicantState())
            {
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append("[");
              ((StringBuilder)localObject2).append(paramMessage);
              ((StringBuilder)localObject2).append("]: SupplicantState mismatch");
              ((StringBuilder)localObject2).toString();
              i = 1;
            }
          }
          else
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("[");
            ((StringBuilder)localObject2).append(paramMessage);
            ((StringBuilder)localObject2).append("]: localInfo is null");
            ((StringBuilder)localObject2).toString();
            i = 1;
          }
        }
        else
        {
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("[");
          ((StringBuilder)localObject4).append(paramMessage);
          ((StringBuilder)localObject4).append("][");
          ((StringBuilder)localObject4).append((String)localObject2);
          ((StringBuilder)localObject4).append("]: ssid mismatch");
          ((StringBuilder)localObject4).toString();
          i = 1;
        }
      }
      else if (isConnected())
      {
        i = 1;
      }
      if (i != 0)
      {
        localObject2 = WifiUtils.getNetworkInfo();
        paramMessage = (Message)localObject2;
        if (localObject2 == null) {
          paramMessage = WifiUtils.getNetworkInfo();
        }
        if (paramMessage != null) {
          localObject1 = paramMessage.getDetailedState();
        }
        refreshStateBundle(paramMessage, (WifiInfo)localObject3, (NetworkInfo.DetailedState)localObject1);
        return true;
        if ((paramMessage.obj instanceof Bundle))
        {
          if (this.mLastState == null) {
            return true;
          }
          localObject2 = (Bundle)paramMessage.obj;
          try
          {
            paramMessage = (Intent)((Bundle)localObject2).get("INTENT");
          }
          catch (Throwable paramMessage)
          {
            paramMessage.printStackTrace();
            paramMessage = null;
          }
          if (paramMessage != null)
          {
            i = paramMessage.getIntExtra("supplicantError", -1);
            paramMessage = new StringBuilder();
            paramMessage.append("SUPPLICANT-STATE:::ERROR_CODE[ ");
            paramMessage.append(i);
            paramMessage.append(" ]");
            paramMessage.toString();
            if (i == 1)
            {
              localObject3 = WifiUtils.getConnectionInfo();
              paramMessage = new StringBuilder();
              paramMessage.append("SUPPLICANT-STATE:::WifiInfo->");
              paramMessage.append(localObject3);
              paramMessage.toString();
              paramMessage = "";
              if (localObject3 != null) {
                paramMessage = WifiCommonUtils.getSsidWithoutQuotation(((WifiInfo)localObject3).getSSID());
              }
              localObject1 = paramMessage;
              if (TextUtils.isEmpty(paramMessage)) {
                localObject1 = this.mLastState.getString("SSID");
              }
              if ((!TextUtils.equals(this.mLastState.getString("SSID"), (CharSequence)localObject1)) || (!isDisconnectedState(this.mLastState.getInt("STATE"))))
              {
                refreshStateBundle((NetworkInfo)((Bundle)localObject2).get("NETWORKINFO"), (WifiInfo)localObject3, NetworkInfo.DetailedState.values()[localObject2.getInt("DETAILSTATE")]);
                if (this.mLastState != null)
                {
                  paramMessage = this.mUIHandler.obtainMessage(1000);
                  this.mLastStateCopy = new Bundle(this.mLastState);
                  localObject2 = new StringBuilder();
                  ((StringBuilder)localObject2).append("SUPPLICANT-STATE:::NOTIFY : ");
                  ((StringBuilder)localObject2).append(this.mLastStateCopy);
                  ((StringBuilder)localObject2).toString();
                  paramMessage.obj = this.mLastStateCopy;
                  paramMessage.sendToTarget();
                }
              }
              paramMessage = this.mLastState;
              if ((paramMessage != null) && (TextUtils.equals(paramMessage.getString("SSID"), (CharSequence)localObject1)))
              {
                this.mLastState.putInt("REASON", i);
                paramMessage = this.mUIHandler.obtainMessage(1001);
                this.mLastStateCopy = new Bundle(this.mLastState);
                paramMessage.obj = this.mLastStateCopy;
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("SUPPLICANT-STATE:::NOTIFY : ");
                ((StringBuilder)localObject1).append(this.mLastStateCopy);
                ((StringBuilder)localObject1).toString();
                paramMessage.sendToTarget();
                return true;
                if ((paramMessage.obj instanceof Intent))
                {
                  paramMessage = (NetworkInfo)((Intent)paramMessage.obj).getParcelableExtra("networkInfo");
                  localObject1 = new StringBuilder();
                  ((StringBuilder)localObject1).append("CONNECT:::NetworkInfo : ");
                  ((StringBuilder)localObject1).append(paramMessage);
                  ((StringBuilder)localObject1).toString();
                  localObject1 = new StringBuilder();
                  ((StringBuilder)localObject1).append("CONNECT:::isConnected : ");
                  ((StringBuilder)localObject1).append(paramMessage.isConnected());
                  ((StringBuilder)localObject1).toString();
                  localObject1 = WifiUtils.getConnectionInfo();
                  localObject2 = new StringBuilder();
                  ((StringBuilder)localObject2).append("CONNECT:::WifiInfo : ");
                  ((StringBuilder)localObject2).append(localObject1);
                  ((StringBuilder)localObject2).toString();
                  if (localObject1 != null)
                  {
                    refreshStateBundle(paramMessage, (WifiInfo)localObject1, NetworkInfo.DetailedState.values()[paramMessage.getDetailedState().ordinal()]);
                    if (this.mLastState != null)
                    {
                      paramMessage = this.mUIHandler.obtainMessage(1000);
                      this.mLastStateCopy = new Bundle(this.mLastState);
                      localObject1 = new StringBuilder();
                      ((StringBuilder)localObject1).append("CONNECT:::NOTIFY : ");
                      ((StringBuilder)localObject1).append(this.mLastStateCopy);
                      ((StringBuilder)localObject1).toString();
                      paramMessage.obj = this.mLastStateCopy;
                      paramMessage.sendToTarget();
                      return true;
                      if ((paramMessage.obj instanceof Intent))
                      {
                        paramMessage = (Intent)paramMessage.obj;
                        boolean bool1;
                        try
                        {
                          bool1 = paramMessage.getExtras().getBoolean("multipleChanges");
                        }
                        catch (Throwable paramMessage)
                        {
                          paramMessage.printStackTrace();
                          bool1 = bool2;
                        }
                        if (!bool1) {
                          refrashConfigMap();
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      return true;
    }
  }
  
  public static final boolean isConnectedState(int paramInt)
  {
    NetworkInfo.DetailedState localDetailedState = detailedState(paramInt);
    if (localDetailedState == null) {
      return false;
    }
    return 1.$SwitchMap$android$net$NetworkInfo$DetailedState[localDetailedState.ordinal()] == 7;
  }
  
  public static final boolean isConnectingState(int paramInt)
  {
    NetworkInfo.DetailedState localDetailedState = detailedState(paramInt);
    if (localDetailedState == null) {
      return false;
    }
    switch (1.$SwitchMap$android$net$NetworkInfo$DetailedState[localDetailedState.ordinal()])
    {
    default: 
      return false;
    }
    return true;
  }
  
  public static final boolean isDisconnectedState(int paramInt)
  {
    NetworkInfo.DetailedState localDetailedState = detailedState(paramInt);
    if (localDetailedState == null) {
      return false;
    }
    paramInt = 1.$SwitchMap$android$net$NetworkInfo$DetailedState[localDetailedState.ordinal()];
    if (paramInt != 8) {
      switch (paramInt)
      {
      default: 
        return false;
      }
    }
    return true;
  }
  
  private void refrashConfigMap()
  {
    Object localObject = WifiUtils.getConfiguredNetworks();
    if ((localObject != null) && (!((List)localObject).isEmpty()))
    {
      this.mConfigMap.clear();
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        WifiConfiguration localWifiConfiguration = (WifiConfiguration)((Iterator)localObject).next();
        this.mConfigMap.put(localWifiConfiguration.networkId, localWifiConfiguration);
      }
    }
  }
  
  private void refreshStateBundle(NetworkInfo paramNetworkInfo, WifiInfo paramWifiInfo, NetworkInfo.DetailedState paramDetailedState)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("CONNECT-STATE::: [ networkInfo ] -->");
    ((StringBuilder)localObject1).append(paramNetworkInfo);
    ((StringBuilder)localObject1).toString();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("CONNECT-STATE::: [ wifiInfo ] -->");
    ((StringBuilder)localObject1).append(paramWifiInfo);
    ((StringBuilder)localObject1).toString();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("CONNECT-STATE:::[ SSID ]");
    localObject1 = this.mLastState;
    if (localObject1 != null) {
      localObject1 = ((Bundle)localObject1).getString("SSID");
    } else {
      localObject1 = "null";
    }
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(" [ lastState ] -->");
    if (this.mLastState == null) {
      localObject1 = Integer.valueOf(-1);
    } else {
      localObject1 = NetworkInfo.DetailedState.values()[this.mLastState.getInt("STATE")];
    }
    ((StringBuilder)localObject2).append(localObject1);
    ((StringBuilder)localObject2).toString();
    Bundle localBundle1 = this.mLastState;
    if ((paramWifiInfo != null) && (paramNetworkInfo != null) && (paramDetailedState != null))
    {
      int i = paramWifiInfo.getNetworkId();
      if (i == -1)
      {
        if (this.mLastState != null)
        {
          paramDetailedState = new Bundle();
          paramDetailedState.putParcelable("WIFIINFO", paramWifiInfo);
          paramDetailedState.putString("SSID", this.mLastState.getString("SSID"));
          paramDetailedState.putParcelable("NETWORKINFO", paramNetworkInfo);
          paramNetworkInfo = (WifiConfiguration)this.mLastState.getParcelable("CONFIG");
          if (paramNetworkInfo != null)
          {
            i = 0;
            while (i < this.mConfigMap.size())
            {
              paramWifiInfo = (WifiConfiguration)this.mConfigMap.valueAt(i);
              if (paramNetworkInfo.networkId == paramWifiInfo.networkId) {
                paramDetailedState.putParcelable("CONFIG", paramWifiInfo);
              }
              i += 1;
            }
          }
          i = this.mLastState.getInt("STATE");
          int j = NetworkInfo.DetailedState.DISCONNECTED.ordinal();
          if (i != j) {
            paramDetailedState.putInt("STATE", j);
          }
          this.mLastState = paramDetailedState;
        }
      }
      else if (i != -1)
      {
        if ((paramDetailedState == NetworkInfo.DetailedState.CONNECTED) && (!WifiUtils.isSsidValid(paramNetworkInfo.getExtraInfo())) && (paramWifiInfo.getSupplicantState() != SupplicantState.COMPLETED))
        {
          paramNetworkInfo = new StringBuilder();
          paramNetworkInfo.append("\r\n Wrong State: DetailedState[ CONNECTED ]/SupplicantState[ ");
          paramNetworkInfo.append(paramWifiInfo.getSupplicantState().name());
          paramNetworkInfo.append(" ]\r\n");
          addPath(paramNetworkInfo.toString());
          return;
        }
        localObject2 = (WifiConfiguration)this.mConfigMap.get(i);
        localObject1 = localObject2;
        if (localObject2 == null)
        {
          refrashConfigMap();
          localObject1 = (WifiConfiguration)this.mConfigMap.get(i);
        }
        if (localObject1 != null)
        {
          Bundle localBundle2 = new Bundle();
          localObject2 = WifiCommonUtils.getSsidWithoutQuotation(paramWifiInfo.getSSID());
          String str = WifiCommonUtils.getSsidWithoutQuotation(((WifiConfiguration)localObject1).SSID);
          if ((!WifiUtils.isSsidValid((String)localObject2)) || (TextUtils.equals((CharSequence)localObject2, str))) {
            localObject2 = str;
          }
          localBundle2.putString("SSID", WifiCommonUtils.getSsidWithoutQuotation((String)localObject2));
          localBundle2.putInt("STATE", paramDetailedState.ordinal());
          localBundle2.putParcelable("WIFIINFO", paramWifiInfo);
          localBundle2.putParcelable("CONFIG", (Parcelable)localObject1);
          localBundle2.putParcelable("NETWORKINFO", paramNetworkInfo);
          this.mLastState = localBundle2;
        }
        else
        {
          localObject1 = new WifiConfiguration();
          ((WifiConfiguration)localObject1).SSID = paramWifiInfo.getSSID();
          localObject2 = new Bundle();
          ((Bundle)localObject2).putString("SSID", WifiCommonUtils.getSsidWithoutQuotation(paramWifiInfo.getSSID()));
          ((Bundle)localObject2).putInt("STATE", paramDetailedState.ordinal());
          ((Bundle)localObject2).putParcelable("WIFIINFO", paramWifiInfo);
          ((Bundle)localObject2).putParcelable("CONFIG", (Parcelable)localObject1);
          ((Bundle)localObject2).putParcelable("NETWORKINFO", paramNetworkInfo);
          this.mLastState = ((Bundle)localObject2);
        }
      }
      if (this.mLastState != localBundle1)
      {
        paramNetworkInfo = "";
        if (localBundle1 != null) {
          paramNetworkInfo = localBundle1.getString("SSID");
        }
        paramWifiInfo = this.mLastState;
        if (paramWifiInfo != null)
        {
          if (!TextUtils.equals(paramNetworkInfo, paramWifiInfo.getString("SSID")))
          {
            paramNetworkInfo = new StringBuilder();
            paramNetworkInfo.append("[");
            paramNetworkInfo.append(this.mLastState.getString("SSID"));
            paramNetworkInfo.append("]");
            addPath(paramNetworkInfo.toString());
          }
          addPath(detailedState(this.mLastState.getInt("STATE")).name());
        }
      }
      paramWifiInfo = new StringBuilder();
      paramWifiInfo.append("CONNECT-STATE:::[ SSID ]");
      paramNetworkInfo = this.mLastState;
      if (paramNetworkInfo != null) {
        paramNetworkInfo = paramNetworkInfo.getString("SSID");
      } else {
        paramNetworkInfo = "null";
      }
      paramWifiInfo.append(paramNetworkInfo);
      paramWifiInfo.append(" [ newState ] -->");
      if (this.mLastState == null) {
        paramNetworkInfo = Integer.valueOf(-1);
      } else {
        paramNetworkInfo = NetworkInfo.DetailedState.values()[this.mLastState.getInt("STATE")];
      }
      paramWifiInfo.append(paramNetworkInfo);
      paramWifiInfo.toString();
      return;
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("wifiInfo : ");
    ((StringBuilder)localObject1).append(paramWifiInfo);
    ((StringBuilder)localObject1).toString();
    paramWifiInfo = new StringBuilder();
    paramWifiInfo.append("networkInfo : ");
    paramWifiInfo.append(paramNetworkInfo);
    paramWifiInfo.toString();
    paramNetworkInfo = new StringBuilder();
    paramNetworkInfo.append("detailedState : ");
    paramNetworkInfo.append(paramDetailedState);
    paramNetworkInfo.toString();
  }
  
  public void addObserver(WifiBaseStateObserver paramWifiBaseStateObserver)
  {
    try
    {
      if (!this.mObservers.contains(paramWifiBaseStateObserver)) {
        this.mObservers.add(paramWifiBaseStateObserver);
      }
      return;
    }
    finally
    {
      paramWifiBaseStateObserver = finally;
      throw paramWifiBaseStateObserver;
    }
  }
  
  public void addPath(String paramString)
  {
    try
    {
      this.mStatePath.add(0, paramString);
      if (this.mStatePath.size() > 80) {
        this.mStatePath.remove(this.mStatePath.size() - 1);
      }
      getPathString();
      return;
    }
    finally {}
  }
  
  public Bundle getLastState()
  {
    return this.mLastStateCopy;
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
  
  public boolean handleMessage(Message paramMessage)
  {
    if (paramMessage.what >= 1000) {
      return handleUIMsg(paramMessage);
    }
    return handleWorkerMsg(paramMessage);
  }
  
  public boolean isConnected()
  {
    Bundle localBundle = getLastState();
    return (localBundle != null) && (localBundle.getInt("STATE") == NetworkInfo.DetailedState.CONNECTED.ordinal());
  }
  
  public boolean isDisconnected()
  {
    Bundle localBundle = this.mLastStateCopy;
    boolean bool2 = true;
    boolean bool1 = bool2;
    if (localBundle != null)
    {
      bool1 = bool2;
      switch (1.$SwitchMap$android$net$NetworkInfo$DetailedState[NetworkInfo.DetailedState.values()[this.mLastStateCopy.getInt("STATE")].ordinal()])
      {
      default: 
        bool1 = false;
      }
    }
    return bool1;
  }
  
  public void onBroadcastReceiver(Intent paramIntent)
  {
    if (paramIntent != null)
    {
      Object localObject = paramIntent.getAction();
      if (TextUtils.equals((CharSequence)localObject, "android.net.wifi.STATE_CHANGE")) {
        try
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("intent : ");
          ((StringBuilder)localObject).append(paramIntent);
          ((StringBuilder)localObject).toString();
          localObject = this.mWorkerHandler.obtainMessage(3);
          ((Message)localObject).obj = paramIntent;
          ((Message)localObject).sendToTarget();
          return;
        }
        catch (Exception paramIntent)
        {
          paramIntent.printStackTrace();
          return;
        }
      }
      if (TextUtils.equals((CharSequence)localObject, "android.net.wifi.CONFIGURED_NETWORKS_CHANGE"))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("intent : ");
        ((StringBuilder)localObject).append(paramIntent);
        ((StringBuilder)localObject).toString();
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("CONFIG-CHANGE:::MULTIPLE_NETWORKS_CHANGED : ");
        ((StringBuilder)localObject).append(paramIntent.getBooleanExtra("multipleChanges", false));
        ((StringBuilder)localObject).toString();
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("CONFIG-CHANGE:::EXTRA_CHANGE_REASON : ");
        ((StringBuilder)localObject).append(paramIntent.getIntExtra("changeReason", -100));
        ((StringBuilder)localObject).toString();
        localObject = this.mWorkerHandler.obtainMessage(2);
        ((Message)localObject).obj = paramIntent;
        ((Message)localObject).sendToTarget();
        return;
      }
      if (TextUtils.equals((CharSequence)localObject, "android.net.wifi.supplicant.STATE_CHANGE"))
      {
        localObject = this.mWorkerHandler.obtainMessage(6);
        ((Message)localObject).obj = paramIntent;
        ((Message)localObject).sendToTarget();
        return;
      }
      if (TextUtils.equals(paramIntent.getAction(), "android.intent.action.USER_PRESENT")) {
        this.mWorkerHandler.sendEmptyMessage(5);
      }
    }
  }
  
  public void removeObserver(WifiBaseStateObserver paramWifiBaseStateObserver)
  {
    try
    {
      this.mObservers.remove(paramWifiBaseStateObserver);
      return;
    }
    finally
    {
      paramWifiBaseStateObserver = finally;
      throw paramWifiBaseStateObserver;
    }
  }
  
  public static abstract interface WifiBaseStateObserver
  {
    public abstract void onStateChange(Bundle paramBundle, int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\state\WifiBaseStateMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */