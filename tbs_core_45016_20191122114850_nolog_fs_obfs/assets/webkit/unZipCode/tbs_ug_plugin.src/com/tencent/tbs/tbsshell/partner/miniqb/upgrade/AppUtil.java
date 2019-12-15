package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

class AppUtil
{
  public static String getAppMetadata(Context paramContext, String paramString)
  {
    try
    {
      String str = paramContext.getPackageName();
      paramContext = String.valueOf(paramContext.getPackageManager().getApplicationInfo(str, 128).metaData.get(paramString));
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    try
    {
      paramString = String.valueOf(Integer.toHexString(Integer.parseInt(paramContext)));
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
    return paramContext;
  }
  
  public static int getAppVersionCode(Context paramContext)
  {
    try
    {
      String str = paramContext.getPackageName();
      int i = paramContext.getPackageManager().getPackageInfo(str, 0).versionCode;
      return i;
    }
    catch (Exception paramContext) {}
    return 0;
  }
  
  public static String getAppVersionName(Context paramContext)
  {
    try
    {
      String str = paramContext.getPackageName();
      paramContext = paramContext.getPackageManager().getPackageInfo(str, 0).versionName;
      return paramContext;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\AppUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */