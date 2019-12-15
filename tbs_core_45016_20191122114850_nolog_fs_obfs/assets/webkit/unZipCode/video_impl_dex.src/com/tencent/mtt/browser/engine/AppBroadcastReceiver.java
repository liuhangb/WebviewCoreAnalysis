package com.tencent.mtt.browser.engine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.StringUtils;
import com.tencent.mtt.ContextHolder;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public final class AppBroadcastReceiver
  implements Handler.Callback
{
  private static AppBroadcastReceiver jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver;
  public static Boolean mRegistered = Boolean.valueOf(false);
  Handler jdField_a_of_type_AndroidOsHandler = null;
  private a jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$a = new a(null);
  private b jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$b = new b(null);
  private c jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$c = new c(null);
  private d jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$d = new d(null);
  public Intent mBatteryIntent = null;
  public CopyOnWriteArrayList<AppBroadcastObserver> mBroadcastObserver = new CopyOnWriteArrayList();
  
  public static AppBroadcastReceiver getInstance()
  {
    if (jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver == null) {
      try
      {
        if (jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver == null) {
          jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver = new AppBroadcastReceiver();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver;
  }
  
  public void addBroadcastObserver(AppBroadcastObserver paramAppBroadcastObserver)
  {
    addBroadcastObserver(paramAppBroadcastObserver, false);
  }
  
  public void addBroadcastObserver(AppBroadcastObserver paramAppBroadcastObserver, boolean paramBoolean)
  {
    if (paramBoolean) {}
    try
    {
      paramBoolean = mRegistered.booleanValue();
      if (paramBoolean) {
        try
        {
          ContextHolder.getAppContext().unregisterReceiver(getSystemReceiver());
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
        }
      }
      try
      {
        ContextHolder.getAppContext().registerReceiver(getSystemReceiver(), getSysIntentFilter(true));
        mRegistered = Boolean.valueOf(true);
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      if (paramAppBroadcastObserver == null) {
        break label92;
      }
      this.jdField_a_of_type_AndroidOsHandler.obtainMessage(1, paramAppBroadcastObserver).sendToTarget();
    }
    finally
    {
      label92:
      for (;;) {}
    }
    throw paramAppBroadcastObserver;
  }
  
  public IntentFilter getCustomIntentFilter()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.tencent.QQBrowser.action.qqbrowser.uppay");
    localIntentFilter.addAction("com.tencent.mtt.action.jump_result_ok");
    localIntentFilter.addAction("com.tencent.mtt.action.jump_result_cancel");
    localIntentFilter.addAction("com.tencent.QQBrowser.action.weather.REFRESH");
    localIntentFilter.addAction("com.tencent.QQBrowser.action.weather.REFRESH.FAIL");
    localIntentFilter.addAction("com.tencent.mtt.action.siwtch_skin");
    localIntentFilter.addAction("com.tencent.mtt.wifi.BRAND_INFO");
    localIntentFilter.addAction("com.tencent.mtt.browser.homepage.SPLASH");
    localIntentFilter.addAction("common.internal.apdl.notification");
    return localIntentFilter;
  }
  
  public a getCustomReceiver()
  {
    if (this.jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$a == null) {
      this.jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$a = new a(null);
    }
    return this.jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$a;
  }
  
  public IntentFilter getPhoneStateChangeFilter()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.PHONE_STATE");
    return localIntentFilter;
  }
  
  public b getPhoneStateChangeReceiver()
  {
    if (this.jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$b == null) {
      this.jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$b = new b(null);
    }
    return this.jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$b;
  }
  
  public IntentFilter getSysIntentFilter(boolean paramBoolean)
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    localIntentFilter.addAction("android.intent.action.SCREEN_OFF");
    localIntentFilter.addAction("android.intent.action.SCREEN_ON");
    localIntentFilter.addAction("android.intent.action.USER_PRESENT");
    localIntentFilter.addAction("android.intent.action.BATTERY_CHANGED");
    if (paramBoolean) {
      localIntentFilter.addAction("android.intent.action.TIME_TICK");
    }
    localIntentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
    localIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
    localIntentFilter.addAction("android.net.wifi.STATE_CHANGE");
    localIntentFilter.addAction("android.net.wifi.SCAN_RESULTS");
    localIntentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
    localIntentFilter.addAction("android.net.wifi.RSSI_CHANGED");
    localIntentFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
    localIntentFilter.addAction("android.intent.action.HEADSET_PLUG");
    localIntentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
    return localIntentFilter;
  }
  
  public IntentFilter getSysIntentFilterNeedScheme()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
    localIntentFilter.addAction("android.intent.action.PACKAGE_INSTALL");
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
  
  public BroadcastReceiver getSystemReceiver()
  {
    if (this.jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$c == null) {
      this.jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$c = new c(null);
    }
    return this.jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$c;
  }
  
  public d getSystemReceiverNeedScheme()
  {
    if (this.jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$d == null) {
      this.jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$d = new d(null);
    }
    return this.jdField_a_of_type_ComTencentMttBrowserEngineAppBroadcastReceiver$d;
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default: 
      return false;
    case 3: 
      this.mBroadcastObserver.clear();
      return true;
    case 2: 
      paramMessage = (AppBroadcastObserver)paramMessage.obj;
      this.mBroadcastObserver.remove(paramMessage);
      return true;
    }
    paramMessage = (AppBroadcastObserver)paramMessage.obj;
    if (!this.mBroadcastObserver.contains(paramMessage)) {
      this.mBroadcastObserver.add(paramMessage);
    }
    return true;
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
    try
    {
      boolean bool = mRegistered.booleanValue();
      if (bool) {
        return;
      }
      if (paramContext != null)
      {
        paramContext.registerReceiver(getSystemReceiver(), getSysIntentFilter(false));
        paramContext.registerReceiver(getSystemReceiverNeedScheme(), getSysIntentFilterNeedScheme());
        paramContext.registerReceiver(getCustomReceiver(), getCustomIntentFilter(), "com.tencent.mtt.broadcast", null);
        paramContext.registerReceiver(getPhoneStateChangeReceiver(), getPhoneStateChangeFilter());
        mRegistered = Boolean.valueOf(true);
      }
      return;
    }
    finally {}
  }
  
  public void removeBroadcastObserver(AppBroadcastObserver paramAppBroadcastObserver)
  {
    if (paramAppBroadcastObserver != null) {
      this.jdField_a_of_type_AndroidOsHandler.obtainMessage(2, paramAppBroadcastObserver).sendToTarget();
    }
  }
  
  public void unregister(Context paramContext)
  {
    try
    {
      boolean bool = mRegistered.booleanValue();
      if (!bool) {
        return;
      }
      this.jdField_a_of_type_AndroidOsHandler.obtainMessage(3).sendToTarget();
      if (paramContext != null)
      {
        paramContext.unregisterReceiver(getSystemReceiver());
        paramContext.unregisterReceiver(getSystemReceiverNeedScheme());
        paramContext.unregisterReceiver(getCustomReceiver());
        paramContext.unregisterReceiver(getPhoneStateChangeReceiver());
        mRegistered = Boolean.valueOf(false);
      }
      return;
    }
    finally {}
  }
  
  private class a
    extends BroadcastReceiver
  {
    private a() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent == null) {
        return;
      }
      paramContext = paramIntent.getAction();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("action:");
      localStringBuilder.append(paramContext);
      LogUtils.d("AppBroadcastReceiver", localStringBuilder.toString());
      if (TextUtils.isEmpty(paramContext)) {
        return;
      }
      if ("com.tencent.QQBrowser.action.qqbrowser.uppay".equals(paramContext))
      {
        paramContext = AppBroadcastReceiver.this.mBroadcastObserver.iterator();
        while (paramContext.hasNext()) {
          ((AppBroadcastObserver)paramContext.next()).onBroadcastReceiver(paramIntent);
        }
      }
      if ((StringUtils.isStringEqual(paramContext, "com.tencent.mtt.action.jump_result_ok")) || (StringUtils.isStringEqual(paramContext, "com.tencent.mtt.action.jump_result_cancel")) || (StringUtils.isStringEqual(paramContext, "com.tencent.QQBrowser.action.weather.REFRESH")) || (StringUtils.isStringEqual(paramContext, "com.tencent.QQBrowser.action.weather.REFRESH.FAIL")) || (StringUtils.isStringEqual(paramContext, "com.tencent.mtt.action.siwtch_skin")) || (StringUtils.isStringEqual(paramContext, "com.tencent.mtt.wifi.BRAND_INFO")) || (StringUtils.isStringEqual(paramContext, "com.tencent.mtt.browser.homepage.SPLASH")) || (StringUtils.isStringEqual(paramContext, "common.internal.apdl.notification")))
      {
        paramContext = AppBroadcastReceiver.this.mBroadcastObserver.iterator();
        while (paramContext.hasNext()) {
          ((AppBroadcastObserver)paramContext.next()).onBroadcastReceiver(paramIntent);
        }
      }
    }
  }
  
  private class b
    extends BroadcastReceiver
  {
    private b() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent == null) {
        return;
      }
      paramContext = paramIntent.getAction();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("action:");
      localStringBuilder.append(paramContext);
      LogUtils.d("AppBroadcastReceiver", localStringBuilder.toString());
      if (TextUtils.isEmpty(paramContext)) {
        return;
      }
      if ("android.intent.action.PHONE_STATE".equals(paramContext))
      {
        paramContext = AppBroadcastReceiver.this.mBroadcastObserver.iterator();
        while (paramContext.hasNext()) {
          ((AppBroadcastObserver)paramContext.next()).onBroadcastReceiver(paramIntent);
        }
      }
    }
  }
  
  private class c
    extends BroadcastReceiver
  {
    private c() {}
    
    public void onReceive(Context paramContext, final Intent paramIntent)
    {
      AppBroadcastReceiver.this.a.post(new Runnable()
      {
        public void run()
        {
          LogUtils.startTiming("AppSystemReceiver.onReceive");
          LogUtils.d("AppBroadcastReceiver", "AppSystemReceiver onReceive");
          Object localObject1 = paramIntent;
          if (localObject1 == null) {
            return;
          }
          localObject1 = ((Intent)localObject1).getAction();
          Object localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("action:");
          ((StringBuilder)localObject2).append((String)localObject1);
          LogUtils.d("AppBroadcastReceiver", ((StringBuilder)localObject2).toString());
          if (TextUtils.isEmpty((CharSequence)localObject1)) {
            return;
          }
          if (("android.net.conn.CONNECTIVITY_CHANGE".equals(localObject1)) || ("android.intent.action.SCREEN_ON".equals(localObject1)) || ("android.intent.action.SCREEN_OFF".equals(localObject1)) || ("android.intent.action.USER_PRESENT".equals(localObject1)) || ("android.intent.action.BATTERY_CHANGED".equals(localObject1)) || ("android.intent.action.TIME_TICK".equals(localObject1)) || ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(localObject1)) || ("android.net.wifi.WIFI_STATE_CHANGED".equals(localObject1)) || ("android.net.wifi.STATE_CHANGE".equals(localObject1)) || ("android.net.wifi.SCAN_RESULTS".equals(localObject1)) || ("android.net.wifi.supplicant.STATE_CHANGE".equals(localObject1)) || ("android.net.wifi.RSSI_CHANGED".equals(localObject1)) || ("android.net.wifi.CONFIGURED_NETWORKS_CHANGE".equals(localObject1)) || ("android.intent.action.HEADSET_PLUG".equals(localObject1)) || ("android.intent.action.CONFIGURATION_CHANGED".equals(localObject1)))
          {
            LogUtils.d("AppBroadcastReceiver", "handle BroadcastReceiver action...");
            if ("android.intent.action.BATTERY_CHANGED".equals(localObject1)) {
              AppBroadcastReceiver.this.mBatteryIntent = paramIntent;
            }
            localObject1 = AppBroadcastReceiver.this.mBroadcastObserver.iterator();
            while (((Iterator)localObject1).hasNext())
            {
              localObject2 = (AppBroadcastObserver)((Iterator)localObject1).next();
              if (localObject2 != null) {
                ((AppBroadcastObserver)localObject2).onBroadcastReceiver(paramIntent);
              }
            }
          }
          LogUtils.printCostTime("AppBroadcastReceiver", "AppSystemReceiver.onReceive", "AppSystemReceiver.onReceive");
        }
      });
    }
  }
  
  private class d
    extends BroadcastReceiver
  {
    private d() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      LogUtils.d("AppBroadcastReceiver", "AppSystemReceiver onReceive");
      if (paramIntent == null) {
        return;
      }
      paramContext = paramIntent.getAction();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("action:");
      localStringBuilder.append(paramContext);
      LogUtils.d("AppBroadcastReceiver", localStringBuilder.toString());
      if (TextUtils.isEmpty(paramContext)) {
        return;
      }
      if ((paramContext.equalsIgnoreCase("android.intent.action.PACKAGE_ADDED")) || (paramContext.equalsIgnoreCase("android.intent.action.PACKAGE_REMOVED")) || ("android.intent.action.MEDIA_UNMOUNTED".equalsIgnoreCase(paramContext)) || ("android.intent.action.MEDIA_MOUNTED".equalsIgnoreCase(paramContext)) || ("android.intent.action.MEDIA_EJECT".equalsIgnoreCase(paramContext)) || ("android.intent.action.MEDIA_REMOVED".equalsIgnoreCase(paramContext)) || ("android.intent.action.MEDIA_BAD_REMOVAL".equalsIgnoreCase(paramContext)) || (paramContext.equalsIgnoreCase("android.intent.action.PACKAGE_INSTALL")))
      {
        paramContext = AppBroadcastReceiver.this.mBroadcastObserver.iterator();
        while (paramContext.hasNext()) {
          ((AppBroadcastObserver)paramContext.next()).onBroadcastReceiver(paramIntent);
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\engine\AppBroadcastReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */