package com.tencent.tbs.tbsshell.partner;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import com.tencent.common.utils.MttLoader;
import java.util.Iterator;
import java.util.List;

public class QBPushManager
{
  public static final String ACTIVATE_PUSH_BY_SDK = "com.tencent.mtt.ACTION_ACTIVATE_PUSH_BY_SDK";
  private static String LIGHT_APP_PUSH_ACTION = "com.tencent.lightapp.Tencent.pushservice.ACTION_DEFAULT";
  private static final int MAX_SENT_ACTIVATE_BROADCAST = 1;
  private static String TENCENT_LIGHTAPP_PACKAGE_NAME = "com.tencent.lightapp.Tencent";
  private static final String TMSPackageName = "com.android.browser";
  private static int browserVersionCode = -1;
  private static Context mContext;
  private static int mHuaweiSysManagerVersionCode = -1;
  private static QBPushManager mInstance;
  private static int sentActivateBroadcastCount = 0;
  private static int tencentLightappVersionCode = -1;
  private static int tmsPushVersion = -1;
  ComponentName cnQB = new ComponentName("com.tencent.mtt", "com.tencent.mtt.sdk.BrowserSdkService");
  ComponentName cnTms = new ComponentName("com.tencent.tmsbrowser", "com.tencent.tmsbrowser.sdk.BrowserSdkService");
  
  private static int getBrowserInfo(Context paramContext)
  {
    if (MttLoader.isQbFakeNoInstallation(paramContext)) {
      return 0;
    }
    int i = browserVersionCode;
    if (i != -1) {
      return i;
    }
    browserVersionCode = 0;
    try
    {
      paramContext = paramContext.getPackageManager();
      browserVersionCode = paramContext.getPackageInfo("com.tencent.mtt", 0).versionCode;
    }
    catch (Exception|PackageManager.NameNotFoundException paramContext)
    {
      for (;;) {}
    }
    return browserVersionCode;
  }
  
  public static QBPushManager getInstance(Context paramContext)
  {
    if (mInstance == null)
    {
      mContext = paramContext;
      mInstance = new QBPushManager();
    }
    return mInstance;
  }
  
  private static int getTMSPushVersion(Context paramContext)
  {
    int i = tmsPushVersion;
    if (i != -1) {
      return i;
    }
    tmsPushVersion = 0;
    try
    {
      Object localObject = new Intent("com.tencent.tmsbrowser.ACTION_INSTALL_X5");
      localObject = paramContext.getPackageManager().queryIntentServices((Intent)localObject, 0).iterator();
      while (((Iterator)localObject).hasNext()) {
        if ("com.android.browser".equals(((ResolveInfo)((Iterator)localObject).next()).serviceInfo.packageName)) {
          tmsPushVersion = 1;
        }
      }
      localObject = new Intent("com.tencent.tmsbrowser.ACTION_ACTIVE_PUSH");
      paramContext = paramContext.getPackageManager().queryIntentServices((Intent)localObject, 0).iterator();
      while (paramContext.hasNext()) {
        if ("com.android.browser".equals(((ResolveInfo)paramContext.next()).serviceInfo.packageName)) {
          tmsPushVersion = 2;
        }
      }
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    return tmsPushVersion;
  }
  
  private static int getTencentLightappInfo(Context paramContext)
  {
    int i = tencentLightappVersionCode;
    if (i != -1) {
      return i;
    }
    tencentLightappVersionCode = 0;
    try
    {
      paramContext = paramContext.getPackageManager();
      tencentLightappVersionCode = paramContext.getPackageInfo(TENCENT_LIGHTAPP_PACKAGE_NAME, 0).versionCode;
    }
    catch (Exception|PackageManager.NameNotFoundException paramContext)
    {
      for (;;) {}
    }
    return tencentLightappVersionCode;
  }
  
  private boolean isQBPushRunning()
  {
    Object localObject = ((ActivityManager)mContext.getSystemService("activity")).getRunningServices(50);
    if (localObject == null) {
      return false;
    }
    localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      ActivityManager.RunningServiceInfo localRunningServiceInfo = (ActivityManager.RunningServiceInfo)((Iterator)localObject).next();
      if (((this.cnQB.equals(localRunningServiceInfo.service)) || (this.cnTms.equals(localRunningServiceInfo.service))) && (localRunningServiceInfo.started)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean shouldDoPullUp()
  {
    int i = mHuaweiSysManagerVersionCode;
    boolean bool = false;
    Object localObject;
    if (i == -1) {
      localObject = mContext.getPackageManager();
    }
    try
    {
      localObject = ((PackageManager)localObject).getPackageInfo("com.huawei.systemmanager", 0);
      if (localObject != null) {
        mHuaweiSysManagerVersionCode = ((PackageInfo)localObject).versionCode;
      } else {
        mHuaweiSysManagerVersionCode = 0;
      }
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    mHuaweiSysManagerVersionCode = 0;
    if (mHuaweiSysManagerVersionCode <= 331522) {
      bool = true;
    }
    return bool;
  }
  
  public boolean callPushService(Bundle paramBundle)
  {
    return false;
  }
  
  public void pullUpPushService() {}
  
  public void pullUpTencentLightappPushService() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\QBPushManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */