package com.tencent.tbs.common.wifi.state;

import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.browser.engine.AppBroadcastObserver;
import com.tencent.mtt.browser.engine.AppBroadcastReceiver;
import com.tencent.tbs.common.wifi.WifiApInfo;
import com.tencent.tbs.common.wifi.WifiCommonUtils;
import com.tencent.tbs.common.wifi.WifiUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NewWifiDetector
  implements AppBroadcastObserver, WifiBaseStateMonitor.WifiBaseStateObserver
{
  private static final String TAG = "NewWifiDetector";
  private static NewWifiDetector sInstance;
  private ConcurrentLinkedQueue<String> mNewSsidHashSet = new ConcurrentLinkedQueue();
  private HashSet<String> mSsidHashSet = new HashSet();
  
  private NewWifiDetector()
  {
    Object localObject = WifiUtils.getConfiguredNetworks();
    if (localObject != null)
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        WifiConfiguration localWifiConfiguration = (WifiConfiguration)((Iterator)localObject).next();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(localWifiConfiguration);
        LogUtils.d("NewWifiDetector", localStringBuilder.toString());
        this.mSsidHashSet.add(WifiCommonUtils.getSsidWithoutQuotation(localWifiConfiguration.SSID));
      }
    }
    AppBroadcastReceiver.getInstance().addBroadcastObserver(this);
    WifiBaseStateMonitor.getInstance().addObserver(this);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("NewWifiDetector() inited, mSsidHashSet.size: ");
    ((StringBuilder)localObject).append(this.mSsidHashSet.size());
    LogUtils.d("NewWifiDetector", ((StringBuilder)localObject).toString());
  }
  
  public static NewWifiDetector getInstance()
  {
    if (sInstance == null) {
      try
      {
        if (sInstance == null) {
          sInstance = new NewWifiDetector();
        }
      }
      finally {}
    }
    return sInstance;
  }
  
  public boolean isNewAdd(String paramString)
  {
    boolean bool = this.mNewSsidHashSet.contains(paramString);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("isNewAdd() ssid: ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(", mNewSsidHashSet.size: ");
    localStringBuilder.append(this.mNewSsidHashSet.size());
    localStringBuilder.append(", contains: ");
    localStringBuilder.append(bool);
    LogUtils.d("NewWifiDetector", localStringBuilder.toString());
    return bool;
  }
  
  public boolean isSaved(WifiApInfo paramWifiApInfo)
  {
    if (paramWifiApInfo != null) {
      return isSaved(paramWifiApInfo.mSsid);
    }
    return false;
  }
  
  public boolean isSaved(String paramString)
  {
    return this.mSsidHashSet.contains(paramString);
  }
  
  public void onBroadcastReceiver(Intent paramIntent)
  {
    LogUtils.d("NewWifiDetector", "onBroadcastReceiver");
    if (paramIntent != null)
    {
      Object localObject = paramIntent.getAction();
      int i;
      StringBuilder localStringBuilder;
      if (TextUtils.equals((CharSequence)localObject, "android.net.wifi.CONFIGURED_NETWORKS_CHANGE"))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("intent : ");
        ((StringBuilder)localObject).append(paramIntent);
        LogUtils.d("NewWifiDetector", ((StringBuilder)localObject).toString());
        i = -1;
        try
        {
          int j = paramIntent.getIntExtra("changeReason", -100);
          i = j;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("changeReason : ");
        localStringBuilder.append(i);
        LogUtils.d("NewWifiDetector-REMOVE", localStringBuilder.toString());
        if (i == 1)
        {
          paramIntent = paramIntent.getParcelableExtra("wifiConfiguration");
          if (paramIntent != null)
          {
            paramIntent = WifiCommonUtils.getSsidWithoutQuotation(((WifiConfiguration)paramIntent).SSID);
            this.mSsidHashSet.remove(paramIntent);
            refrashData();
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("onBroadcastReceiver() CONFIGURED_NETWORKS_CHANGE mSsidHashSet/mNewSsidHashSet remove ssid: ");
            localStringBuilder.append(paramIntent);
            LogUtils.d("NewWifiDetector", localStringBuilder.toString());
          }
        }
      }
      else if (TextUtils.equals(localStringBuilder, "android.net.wifi.WIFI_STATE_CHANGED"))
      {
        i = paramIntent.getIntExtra("wifi_state", 4);
        if (i != 3)
        {
          switch (i)
          {
          default: 
            return;
          }
          LogUtils.d("NewWifiDetector", "WIFI DISABLE");
          return;
        }
        LogUtils.d("NewWifiDetector", "WIFI ENABLE");
        this.mNewSsidHashSet.clear();
        refrashData();
      }
    }
  }
  
  public void onStateChange(Bundle paramBundle, int paramInt)
  {
    if (paramInt != 0) {
      return;
    }
    if (paramBundle != null)
    {
      StringBuilder localStringBuilder;
      switch (android.net.NetworkInfo.DetailedState.values()[paramBundle.getInt("STATE")])
      {
      default: 
        
      case ???: 
        paramBundle = (String)WifiBaseStateMonitor.get(paramBundle, "SSID");
        if (!TextUtils.isEmpty(paramBundle))
        {
          paramBundle = WifiCommonUtils.getSsidWithoutQuotation(paramBundle);
          this.mNewSsidHashSet.remove(paramBundle);
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("onStateChange() mNewSsidHashSet remove ssid: ");
          localStringBuilder.append(paramBundle);
          LogUtils.d("NewWifiDetector", localStringBuilder.toString());
          return;
        }
        break;
      case ???: 
      case ???: 
        paramBundle = (String)WifiBaseStateMonitor.get(paramBundle, "SSID");
        if (!TextUtils.isEmpty(paramBundle))
        {
          paramBundle = WifiCommonUtils.getSsidWithoutQuotation(paramBundle);
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("CONNECTING : ");
          localStringBuilder.append(paramBundle);
          LogUtils.d("NewWifiDetector-NEW", localStringBuilder.toString());
          if (!this.mSsidHashSet.contains(paramBundle))
          {
            this.mSsidHashSet.add(paramBundle);
            if (!this.mNewSsidHashSet.contains(paramBundle))
            {
              this.mNewSsidHashSet.add(paramBundle);
              localStringBuilder = new StringBuilder();
              localStringBuilder.append("onStateChange() mNewSsidHashSet add ssid: ");
              localStringBuilder.append(paramBundle);
              LogUtils.d("NewWifiDetector", localStringBuilder.toString());
            }
          }
        }
        break;
      }
    }
  }
  
  public void refrashData()
  {
    this.mSsidHashSet.clear();
    Object localObject = WifiUtils.getConfiguredNetworks();
    if (localObject != null)
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        WifiConfiguration localWifiConfiguration = (WifiConfiguration)((Iterator)localObject).next();
        this.mSsidHashSet.add(WifiCommonUtils.getSsidWithoutQuotation(localWifiConfiguration.SSID));
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\state\NewWifiDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */