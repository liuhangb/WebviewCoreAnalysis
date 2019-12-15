package com.tencent.tbs.common.download.qb;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.MainThreadExecutor;
import com.tencent.common.utils.SignatureUtil;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.config.DefaultConfigurationManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import java.io.File;
import java.lang.reflect.Method;

public class InstallUtil
{
  public static final String INSTALL_ERROR_PREFIX = "BZAZ";
  private static String KEY = "big_brother_source_key";
  private static final String YYB_PKGNAME = "com.tencent.android.qqdownloader";
  
  private static boolean canInstallUseYYB(Context paramContext)
  {
    if (!Configuration.getInstance(paramContext).isEnableInstallAppUseYYB()) {
      return false;
    }
    return SignatureUtil.getInstalledPKGInfo("com.tencent.android.qqdownloader", paramContext, 0) != null;
  }
  
  public static String getProviderAuthor()
  {
    try
    {
      Object localObject = new ComponentName(ContextHolder.getAppContext().getApplicationInfo().packageName, "android.support.v4.content.FileProvider");
      localObject = ContextHolder.getAppContext().getPackageManager().getProviderInfo((ComponentName)localObject, 0).authority;
      return (String)localObject;
    }
    catch (Exception localException)
    {
      TBSStatManager.getInstance().userBehaviorStatistics("BZAZ6");
      localException.printStackTrace();
    }
    return "";
  }
  
  public static boolean installUseYYB(Context paramContext, File paramFile)
  {
    if (!canInstallUseYYB(paramContext)) {
      return false;
    }
    Object localObject1 = ContextHolder.getAppContext().getPackageManager().getPackageArchiveInfo(paramFile.getAbsolutePath(), 1);
    Object localObject2;
    if (localObject1 != null)
    {
      if (TextUtils.isEmpty(((PackageInfo)localObject1).packageName)) {
        return false;
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("tmast://externalinstall?via=ANDROID.BROWSER.INSTALLER&pname=");
      ((StringBuilder)localObject2).append(((PackageInfo)localObject1).packageName);
      ((StringBuilder)localObject2).append("&versioncode=");
      ((StringBuilder)localObject2).append(((PackageInfo)localObject1).versionCode);
      ((StringBuilder)localObject2).append("&filesize=");
      ((StringBuilder)localObject2).append(paramFile.length());
      ((StringBuilder)localObject2).append("&filepath=");
      ((StringBuilder)localObject2).append(paramFile.getAbsolutePath());
      localObject1 = Uri.parse(((StringBuilder)localObject2).toString());
      localObject2 = new Intent("android.intent.action.VIEW", (Uri)localObject1);
      ((Intent)localObject2).setPackage("com.tencent.android.qqdownloader");
    }
    try
    {
      paramContext.startActivity((Intent)localObject2);
      paramContext = new StringBuilder();
      paramContext.append(paramFile.getAbsolutePath());
      paramContext.append("调起应用宝");
      paramContext.toString();
      paramContext = new StringBuilder();
      paramContext.append("uri:");
      paramContext.append(((Uri)localObject1).toString());
      paramContext.toString();
      return true;
    }
    catch (Exception paramContext) {}
    return false;
    return false;
  }
  
  public static void startInstall(Context paramContext, File paramFile)
  {
    startInstall(paramContext, paramFile, null, false);
  }
  
  public static void startInstall(Context paramContext, File paramFile, String paramString)
  {
    startInstall(paramContext, paramFile, paramString, false);
  }
  
  public static void startInstall(Context paramContext, File paramFile, String paramString, boolean paramBoolean)
  {
    if ((paramBoolean) && (installUseYYB(paramContext, paramFile))) {
      return;
    }
    final Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.addFlags(268435456);
    localIntent.addFlags(1073741824);
    localIntent.setDataAndType(Uri.fromFile(paramFile), "application/vnd.android.package-archive");
    Object localObject;
    if (DefaultConfigurationManager.getInstance().enableSetBusinessIdForInstallApk()) {
      if (!TextUtils.isEmpty(paramString))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("set businessId:");
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).toString();
        localIntent.putExtra(KEY, paramString);
      }
      else
      {
        localIntent.putExtra(KEY, "biz_src_ydll");
      }
    }
    if (Build.VERSION.SDK_INT >= 24)
    {
      paramString = getProviderAuthor();
      if (TextUtils.isEmpty(paramString)) {}
    }
    try
    {
      localIntent.addFlags(1);
      localObject = Class.forName("android.support.v4.content.FileProvider");
      if (localObject != null)
      {
        localObject = ((Class)localObject).getDeclaredMethod("getUriForFile", new Class[] { Context.class, String.class, File.class });
        if (localObject != null)
        {
          ((Method)localObject).setAccessible(true);
          paramFile = ((Method)localObject).invoke(null, new Object[] { paramContext, paramString, paramFile });
          if ((paramFile instanceof Uri)) {
            localIntent.setDataAndType((Uri)paramFile, "application/vnd.android.package-archive");
          } else {
            TBSStatManager.getInstance().userBehaviorStatistics("BZAZ5");
          }
        }
        else
        {
          TBSStatManager.getInstance().userBehaviorStatistics("BZAZ4");
        }
      }
      else
      {
        TBSStatManager.getInstance().userBehaviorStatistics("BZAZ3");
      }
    }
    catch (Exception paramFile)
    {
      for (;;) {}
    }
    TBSStatManager.getInstance().userBehaviorStatistics("BZAZ2");
    BrowserExecutorSupplier.getInstance().getMainThreadExecutor().execute(new Runnable()
    {
      public void run()
      {
        try
        {
          this.val$context.startActivity(localIntent);
          return;
        }
        catch (Exception localException)
        {
          for (;;) {}
        }
        TBSStatManager.getInstance().userBehaviorStatistics("BZAZ1");
      }
    });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\InstallUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */