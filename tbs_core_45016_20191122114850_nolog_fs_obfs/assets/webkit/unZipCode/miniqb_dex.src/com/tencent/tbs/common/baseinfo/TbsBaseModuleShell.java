package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.tencent.common.utils.IntentUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.TbsInfoUtils;

public class TbsBaseModuleShell
{
  public static byte REQ_SRC_MINI_QB = 1;
  public static byte REQ_SRC_TBS = 0;
  public static byte REQ_SRC_TBS_VIDEO = 2;
  public static String SMiniQBVersion = "";
  public static String UIN = "Thisisqq";
  private static boolean mIsQBMode = false;
  private static Context sCallerContext;
  public static ICoreInfoFetcher sCoreInfoFetcher;
  public static ICoreService sCoreService;
  public static boolean sServiceMode = false;
  public static int sTesVersionCode = 0;
  public static String sTesVersionName = "0.0.0.0";
  private static Context sX5CoreHostAppContext;
  public static String sX5CoreVersion = "";
  
  public static Context getCallerContext()
  {
    return sCallerContext;
  }
  
  public static Context getContext()
  {
    return sCallerContext;
  }
  
  public static ICoreInfoFetcher getCoreInfoFetcher()
  {
    return sCoreInfoFetcher;
  }
  
  public static ICoreService getCoreService()
  {
    return sCoreService;
  }
  
  public static boolean getEnableQua1()
  {
    if (sCallerContext != null) {
      return PublicSettingManager.getInstance().getAndroidEnableQua1() != 0;
    }
    return true;
  }
  
  public static String getMiniQBVersion()
  {
    return SMiniQBVersion;
  }
  
  public static int getTBSGeneralFeatureSwitch(String paramString)
  {
    return PublicSettingManager.getInstance().getTBSGeneralFeatureSwitch(paramString);
  }
  
  public static int getTesVersionCode()
  {
    return sTesVersionCode;
  }
  
  public static String getTesVersionName()
  {
    return sTesVersionName;
  }
  
  public static Context getX5CoreHostAppContext()
  {
    return sX5CoreHostAppContext;
  }
  
  public static String getX5CoreVersion()
  {
    return sX5CoreVersion;
  }
  
  public static boolean isQBMode()
  {
    return mIsQBMode;
  }
  
  public static void setCallerContext(Context paramContext)
  {
    sCallerContext = paramContext;
    ContextHolder.initAppContext(sCallerContext);
    try
    {
      paramContext = sCallerContext.getApplicationInfo();
      Object localObject1 = sCallerContext.getPackageManager().getPackageInfo(paramContext.packageName, 0);
      Object localObject2 = paramContext.packageName;
      paramContext = Integer.toString(((PackageInfo)localObject1).versionCode);
      localObject1 = ((PackageInfo)localObject1).versionName;
      if (TextUtils.equals("com.tencent.mtt", (CharSequence)localObject2)) {
        mIsQBMode = true;
      }
      TbsInfoUtils.setParentPackageInfo((String)localObject2, paramContext);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("package name = ");
      localStringBuilder.append((String)localObject2);
      LogUtils.d("context", localStringBuilder.toString());
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("package versionCode = ");
      ((StringBuilder)localObject2).append(paramContext);
      LogUtils.d("context", ((StringBuilder)localObject2).toString());
      paramContext = new StringBuilder();
      paramContext.append("package versionName = ");
      paramContext.append((String)localObject1);
      LogUtils.d("context", paramContext.toString());
      return;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public static void setContext(Context paramContext)
  {
    setCallerContext(paramContext);
  }
  
  public static void setCoreInfoFetcher(ICoreInfoFetcher paramICoreInfoFetcher)
  {
    sCoreInfoFetcher = paramICoreInfoFetcher;
  }
  
  public static void setCoreService(ICoreService paramICoreService)
  {
    sCoreService = paramICoreService;
  }
  
  public static void setMiniQBVersion(String paramString)
  {
    SMiniQBVersion = paramString;
    if ((sCallerContext != null) && (!TextUtils.isEmpty(paramString))) {
      PublicSettingManager.getInstance().setMiniQBStatVersion(paramString);
    }
  }
  
  public static void setTesVersionCode(int paramInt)
  {
    sTesVersionCode = paramInt;
  }
  
  public static void setTesVersionName(String paramString)
  {
    sTesVersionName = paramString;
    IntentUtils.setTbsVersionName(sTesVersionName);
  }
  
  public static void setX5CoreHostAppContext(Context paramContext)
  {
    sX5CoreHostAppContext = paramContext;
  }
  
  public static void setX5CoreVesion(String paramString)
  {
    sX5CoreVersion = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\TbsBaseModuleShell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */