package com.tencent.smtt.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.utils.LogUtils;
import com.tencent.smtt.download.ad.DownloadAppInfoManager;
import com.tencent.tbs.common.baseinfo.CommonConfigListMangager;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.util.HashMap;

public class a
{
  private static volatile a jdField_a_of_type_ComTencentSmttAA;
  private String jdField_a_of_type_JavaLangString = "";
  
  public static a a()
  {
    if (jdField_a_of_type_ComTencentSmttAA == null) {
      try
      {
        if (jdField_a_of_type_ComTencentSmttAA == null) {
          jdField_a_of_type_ComTencentSmttAA = new a();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentSmttAA;
  }
  
  static void a(Context paramContext, com.tencent.smtt.download.ad.a parama, String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("channelAppName", DownloadAppInfoManager.a(paramContext));
    localHashMap.put("packageName", parama.jdField_a_of_type_JavaLangString);
    localHashMap.put("activation", String.valueOf(paramString));
    localHashMap.put("channelDomain", parama.c);
    parama = new StringBuilder();
    parama.append("reportActivationAfterInstall: MTT_TBS_AD_ACTIVATION_AFTER_INSTALL data=");
    parama.append(localHashMap);
    Log.d("AppLaunchUtil", parama.toString());
    X5CoreBeaconUploader.getInstance(paramContext).upLoadToBeacon("MTT_TBS_AD_ACTIVATION_AFTER_INSTALL", localHashMap);
  }
  
  private void a(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
    localStringBuilder.append(paramString);
    localStringBuilder.append(",");
    this.jdField_a_of_type_JavaLangString = localStringBuilder.toString();
  }
  
  public int a()
  {
    String str = PublicSettingManager.getInstance().getCommonConfigListContent(1002);
    try
    {
      if (!TextUtils.isEmpty(str))
      {
        int i = Integer.parseInt(str);
        return i;
      }
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("appLaunchSwitch e = ");
      localStringBuilder.append(localException.toString());
      LogUtils.d("AppLaunchUtil", localStringBuilder.toString());
    }
    return 0;
  }
  
  public void a()
  {
    LogUtils.d("AppLaunchUtil", "startRequestLaunchAfterInstallSwitch...");
    CommonConfigListMangager.getInstance().requestCommonConfigList(false, 1002, "");
  }
  
  public void a(Context paramContext, com.tencent.smtt.download.ad.a parama, boolean paramBoolean)
  {
    LogUtils.d("AppLaunchUtil", "LaunchApp start...1");
    if (paramContext != null)
    {
      if (TextUtils.isEmpty(parama.jdField_a_of_type_JavaLangString)) {
        return;
      }
      if (a() == 0) {
        return;
      }
      LogUtils.d("AppLaunchUtil", "LaunchApp start...2");
      Object localObject = paramContext.getPackageManager();
      if (localObject != null)
      {
        localObject = ((PackageManager)localObject).getLaunchIntentForPackage(parama.jdField_a_of_type_JavaLangString);
        if (localObject == null)
        {
          paramContext = new StringBuilder();
          paramContext.append("App is not install...packageName = ");
          paramContext.append(parama.jdField_a_of_type_JavaLangString);
          LogUtils.d("AppLaunchUtil", paramContext.toString());
          return;
        }
        try
        {
          if (!(paramContext instanceof Activity))
          {
            LogUtils.d("AppLaunchUtil", "App launch context is not Activity");
            ((Intent)localObject).setFlags(268435456);
          }
          paramContext.startActivity((Intent)localObject);
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("App launch startActivity = ");
          localStringBuilder.append(((Intent)localObject).toString());
          LogUtils.d("AppLaunchUtil", localStringBuilder.toString());
          DownloadAppInfoManager.a().a(paramContext, parama, 7, paramBoolean);
        }
        catch (Exception paramContext)
        {
          parama = new StringBuilder();
          parama.append("App launch error = ");
          parama.append(paramContext.toString());
          LogUtils.d("AppLaunchUtil", parama.toString());
          return;
        }
      }
      a(parama.jdField_a_of_type_JavaLangString);
      a(paramContext, parama, "appLaunch");
      return;
    }
  }
  
  public boolean a(String paramString)
  {
    return this.jdField_a_of_type_JavaLangString.contains(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */