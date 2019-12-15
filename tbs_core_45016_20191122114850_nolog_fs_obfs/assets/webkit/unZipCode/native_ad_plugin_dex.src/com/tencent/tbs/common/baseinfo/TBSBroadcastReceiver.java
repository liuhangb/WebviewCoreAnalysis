package com.tencent.tbs.common.baseinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.observer.AppBroadcastObserver;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TBSBroadcastReceiver
{
  private static final String TAG = "TESDownloadManager";
  private static TBSBroadcastReceiver mTESBroadcastReceiver;
  private AppCustomReceiver mAppCustomReceiver = new AppCustomReceiver(null);
  private AppPhoneStateChangeReceiver mAppPhoneStateChangeReceiver = new AppPhoneStateChangeReceiver(null);
  private AppSystemReceiver mAppSystemReceiver = new AppSystemReceiver(null);
  private AppSystemReceiverNeedScheme mAppSystemReceiverNeedScheme = new AppSystemReceiverNeedScheme(null);
  public ConcurrentLinkedQueue<AppBroadcastObserver> mBroadcastObserver = new ConcurrentLinkedQueue();
  
  public static TBSBroadcastReceiver getInstance()
  {
    try
    {
      if (mTESBroadcastReceiver == null) {
        mTESBroadcastReceiver = new TBSBroadcastReceiver();
      }
      TBSBroadcastReceiver localTBSBroadcastReceiver = mTESBroadcastReceiver;
      return localTBSBroadcastReceiver;
    }
    finally {}
  }
  
  public void addBroadcastObserver(AppBroadcastObserver paramAppBroadcastObserver)
  {
    if ((paramAppBroadcastObserver != null) && (!this.mBroadcastObserver.contains(paramAppBroadcastObserver))) {
      this.mBroadcastObserver.add(paramAppBroadcastObserver);
    }
  }
  
  public IntentFilter getCustomIntentFilter()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.tencent.QQBrowser.action.qqbrowser.uppay");
    return localIntentFilter;
  }
  
  public AppCustomReceiver getCustomReceiver()
  {
    if (this.mAppCustomReceiver == null) {
      this.mAppCustomReceiver = new AppCustomReceiver(null);
    }
    return this.mAppCustomReceiver;
  }
  
  public IntentFilter getPhoneStateChangeFilter()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.PHONE_STATE");
    return localIntentFilter;
  }
  
  public AppPhoneStateChangeReceiver getPhoneStateChangeReceiver()
  {
    if (this.mAppPhoneStateChangeReceiver == null) {
      this.mAppPhoneStateChangeReceiver = new AppPhoneStateChangeReceiver(null);
    }
    return this.mAppPhoneStateChangeReceiver;
  }
  
  public IntentFilter getSysIntentFilter()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    localIntentFilter.addAction("android.intent.action.SCREEN_OFF");
    localIntentFilter.addAction("android.intent.action.SCREEN_ON");
    localIntentFilter.addAction("android.intent.action.USER_PRESENT");
    localIntentFilter.addAction("android.intent.action.BATTERY_CHANGED");
    localIntentFilter.addAction("android.intent.action.TIME_TICK");
    localIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
    localIntentFilter.addAction("android.net.wifi.SCAN_RESULTS");
    return localIntentFilter;
  }
  
  public IntentFilter getSysIntentFilterNeedScheme()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
    localIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
    localIntentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
    localIntentFilter.addAction("android.intent.action.MEDIA_EJECT");
    localIntentFilter.addAction("android.intent.action.MEDIA_REMOVED");
    localIntentFilter.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
    localIntentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
    localIntentFilter.addDataScheme("package");
    localIntentFilter.addDataScheme("file");
    return localIntentFilter;
  }
  
  public AppSystemReceiver getSystemReceiver()
  {
    if (this.mAppSystemReceiver == null) {
      this.mAppSystemReceiver = new AppSystemReceiver(null);
    }
    return this.mAppSystemReceiver;
  }
  
  public AppSystemReceiverNeedScheme getSystemReceiverNeedScheme()
  {
    if (this.mAppSystemReceiverNeedScheme == null) {
      this.mAppSystemReceiverNeedScheme = new AppSystemReceiverNeedScheme(null);
    }
    return this.mAppSystemReceiverNeedScheme;
  }
  
  public void notifyConnectivityAction(Intent paramIntent)
  {
    Iterator localIterator = this.mBroadcastObserver.iterator();
    while (localIterator.hasNext()) {
      ((AppBroadcastObserver)localIterator.next()).onBroadcastReceiver(paramIntent);
    }
  }
  
  public void register(Context paramContext)
  {
    if (paramContext != null) {}
    try
    {
      paramContext.registerReceiver(getSystemReceiver(), getSysIntentFilter());
      paramContext.registerReceiver(getSystemReceiverNeedScheme(), getSysIntentFilterNeedScheme());
      paramContext.registerReceiver(getCustomReceiver(), getCustomIntentFilter(), "com.tencent.com.tencent.tbs.common.MTT.broadcast", null);
      paramContext.registerReceiver(getPhoneStateChangeReceiver(), getPhoneStateChangeFilter());
      return;
    }
    catch (Exception paramContext) {}
  }
  
  public void removeBroadcastObserver(AppBroadcastObserver paramAppBroadcastObserver)
  {
    if (paramAppBroadcastObserver != null) {
      this.mBroadcastObserver.remove(paramAppBroadcastObserver);
    }
  }
  
  public void unregister(Context paramContext)
  {
    this.mBroadcastObserver.clear();
    if (paramContext != null) {}
    try
    {
      paramContext.unregisterReceiver(getSystemReceiver());
      paramContext.unregisterReceiver(getSystemReceiverNeedScheme());
      paramContext.unregisterReceiver(getCustomReceiver());
      paramContext.unregisterReceiver(getPhoneStateChangeReceiver());
      return;
    }
    catch (Exception paramContext) {}
  }
  
  private class AppCustomReceiver
    extends BroadcastReceiver
  {
    private AppCustomReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent == null) {
        return;
      }
      paramContext = paramIntent.getAction();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("action:");
      localStringBuilder.append(paramContext);
      LogUtils.d("TESDownloadManager", localStringBuilder.toString());
      if (TextUtils.isEmpty(paramContext)) {
        return;
      }
      if ("com.tencent.QQBrowser.action.qqbrowser.uppay".equals(paramContext))
      {
        paramContext = TBSBroadcastReceiver.this.mBroadcastObserver.iterator();
        while (paramContext.hasNext()) {
          ((AppBroadcastObserver)paramContext.next()).onBroadcastReceiver(paramIntent);
        }
      }
    }
  }
  
  private class AppPhoneStateChangeReceiver
    extends BroadcastReceiver
  {
    private AppPhoneStateChangeReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent == null) {
        return;
      }
      paramContext = paramIntent.getAction();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("action:");
      localStringBuilder.append(paramContext);
      LogUtils.d("TESDownloadManager", localStringBuilder.toString());
      if (TextUtils.isEmpty(paramContext)) {
        return;
      }
      if ("android.intent.action.PHONE_STATE".equals(paramContext))
      {
        paramContext = TBSBroadcastReceiver.this.mBroadcastObserver.iterator();
        while (paramContext.hasNext()) {
          ((AppBroadcastObserver)paramContext.next()).onBroadcastReceiver(paramIntent);
        }
      }
    }
  }
  
  private class AppSystemReceiver
    extends BroadcastReceiver
  {
    private AppSystemReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      LogUtils.d("TESDownloadManager", "AppSystemReceiver onReceive");
      if (paramIntent == null) {
        return;
      }
      paramContext = paramIntent.getAction();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("action:");
      localStringBuilder.append(paramContext);
      LogUtils.d("TESDownloadManager", localStringBuilder.toString());
      if (TextUtils.isEmpty(paramContext)) {
        return;
      }
      if (("android.net.conn.CONNECTIVITY_CHANGE".equals(paramContext)) || ("android.intent.action.SCREEN_ON".equals(paramContext)) || ("android.intent.action.SCREEN_OFF".equals(paramContext)) || ("android.intent.action.USER_PRESENT".equals(paramContext)) || ("android.intent.action.BATTERY_CHANGED".equals(paramContext)) || ("android.intent.action.TIME_TICK".equals(paramContext)) || ("android.net.wifi.WIFI_STATE_CHANGED".equals(paramContext)) || ("android.net.wifi.SCAN_RESULTS".equals(paramContext)))
      {
        paramContext = TBSBroadcastReceiver.this.mBroadcastObserver.iterator();
        while (paramContext.hasNext()) {
          ((AppBroadcastObserver)paramContext.next()).onBroadcastReceiver(paramIntent);
        }
      }
    }
  }
  
  private class AppSystemReceiverNeedScheme
    extends BroadcastReceiver
  {
    private AppSystemReceiverNeedScheme() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      LogUtils.d("TESDownloadManager", "AppSystemReceiver onReceive");
      if (paramIntent == null) {
        return;
      }
      paramContext = paramIntent.getAction();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("action:");
      localStringBuilder.append(paramContext);
      LogUtils.d("TESDownloadManager", localStringBuilder.toString());
      if (TextUtils.isEmpty(paramContext)) {
        return;
      }
      if ((paramContext.equalsIgnoreCase("android.intent.action.PACKAGE_ADDED")) || (paramContext.equalsIgnoreCase("android.intent.action.PACKAGE_REMOVED")) || ("android.intent.action.MEDIA_UNMOUNTED".equalsIgnoreCase(paramContext)) || ("android.intent.action.MEDIA_MOUNTED".equalsIgnoreCase(paramContext)) || ("android.intent.action.MEDIA_EJECT".equalsIgnoreCase(paramContext)) || ("android.intent.action.MEDIA_REMOVED".equalsIgnoreCase(paramContext)) || ("android.intent.action.MEDIA_BAD_REMOVAL".equalsIgnoreCase(paramContext)))
      {
        paramContext = TBSBroadcastReceiver.this.mBroadcastObserver.iterator();
        while (paramContext.hasNext()) {
          ((AppBroadcastObserver)paramContext.next()).onBroadcastReceiver(paramIntent);
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\TBSBroadcastReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */