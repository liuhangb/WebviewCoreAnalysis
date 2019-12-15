package com.tencent.smtt.jsApi.impl.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.tencent.common.utils.Md5Utils;
import com.tencent.mtt.ContextHolder;
import java.io.File;

public class PackageUtils
{
  public static final String QB_MAINACTIVITY = "com.tencent.mtt.MainActivity";
  public static final String QB_PACKAGE_NAME = "com.tencent.mtt";
  
  private static Context a(String paramString)
  {
    try
    {
      paramString = ContextHolder.getAppContext().createPackageContext(paramString, 2);
      return paramString;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static PackageInfo getInstalledPKGInfo(String paramString, Context paramContext, int paramInt)
  {
    if (paramContext != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return null;
      }
      paramContext = paramContext.getPackageManager();
    }
    try
    {
      paramString = paramContext.getPackageInfo(paramString, paramInt);
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
    return null;
  }
  
  public static String getPackageMd5(String paramString)
  {
    paramString = a(paramString);
    if (paramString != null) {
      return Md5Utils.getMD5(new File(paramString.getPackageResourcePath()));
    }
    return null;
  }
  
  public static boolean isQQHost(Context paramContext)
  {
    return TextUtils.equals(paramContext.getApplicationContext().getPackageName(), "com.tencent.mobileqq");
  }
  
  public static boolean isQbAppInstalled(Context paramContext)
  {
    paramContext = getInstalledPKGInfo("com.tencent.mtt", paramContext, 128);
    boolean bool = false;
    if (paramContext != null)
    {
      if (paramContext.versionCode >= 541100) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  public static boolean isWeiXinHost(Context paramContext)
  {
    return TextUtils.equals(paramContext.getApplicationContext().getPackageName(), "com.tencent.mm");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\utils\PackageUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */