package com.tencent.mtt.base.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.Md5Utils;
import com.tencent.common.utils.SignatureUtil;
import com.tencent.common.utils.bitmap.BitmapUtils;
import com.tencent.mtt.ContextHolder;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class PackageUtils
{
  public static final int INSTALLED_BIG_VERSION = 3;
  public static final int INSTALLED_NONE = 0;
  public static final int INSTALLED_SAME_VERSION = 1;
  public static final int INSTALLED_SMALL_VERSION = 2;
  public static final String TAG = "PackageCheckUtils";
  
  public static Drawable getAPKIcon(Context paramContext, String paramString)
  {
    try
    {
      Object localObject = paramContext.getPackageManager().getPackageArchiveInfo(paramString, 1);
      if (localObject != null)
      {
        if (((PackageInfo)localObject).applicationInfo == null) {
          return null;
        }
        localObject = ((PackageInfo)localObject).applicationInfo;
        Class localClass = Class.forName("android.content.res.AssetManager");
        AssetManager localAssetManager = (AssetManager)localClass.getConstructor((Class[])null).newInstance((Object[])null);
        localClass.getDeclaredMethod("addAssetPath", new Class[] { String.class }).invoke(localAssetManager, new Object[] { paramString });
        paramString = new DisplayMetrics();
        paramString.setToDefaults();
        paramContext = new Resources(localAssetManager, paramString, paramContext.getResources().getConfiguration());
        if (((ApplicationInfo)localObject).icon != 0)
        {
          paramContext = paramContext.getDrawable(((ApplicationInfo)localObject).icon);
          return paramContext;
        }
      }
      else
      {
        return null;
      }
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public static String getAPKVersion(Context paramContext, String paramString)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getPackageArchiveInfo(paramString, 0);
      if (paramContext == null) {
        return null;
      }
      paramContext = paramContext.versionName;
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public static List<PackageInfo> getAllInstalledPackage(Context paramContext, int paramInt)
  {
    if (paramContext == null) {
      return null;
    }
    try
    {
      paramContext = paramContext.getPackageManager().getInstalledPackages(paramInt);
      return paramContext;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public static String getApkPath()
  {
    try
    {
      String str = ContextHolder.getAppContext().getPackageManager().getPackageInfo(ContextHolder.getAppContext().getPackageName(), 16).applicationInfo.sourceDir;
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      FLogger.d("IIUpdate", localException.toString());
    }
    return "";
  }
  
  public static final String getAppLabel(Context paramContext, String paramString)
  {
    if (paramContext != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return null;
      }
      Object localObject = paramContext.getPackageManager();
      if (localObject != null)
      {
        try
        {
          paramContext = ((PackageManager)localObject).getPackageInfo(paramString, 0);
        }
        catch (PackageManager.NameNotFoundException paramContext)
        {
          paramContext.printStackTrace();
          paramContext = null;
        }
        if (paramContext != null)
        {
          if (paramContext.applicationInfo == null) {
            return null;
          }
          localObject = paramContext.applicationInfo.loadLabel((PackageManager)localObject);
          paramContext = (Context)localObject;
          if (TextUtils.isEmpty((CharSequence)localObject)) {
            paramContext = paramString;
          }
          return paramContext.toString();
        }
        return null;
      }
      return null;
    }
    return null;
  }
  
  public static int getAppVersionCode(Context paramContext, String paramString)
  {
    if (paramContext != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return 0;
      }
      PackageManager localPackageManager = paramContext.getPackageManager();
      if (localPackageManager != null)
      {
        paramContext = null;
        try
        {
          paramString = localPackageManager.getPackageInfo(paramString, 0);
          paramContext = paramString;
        }
        catch (PackageManager.NameNotFoundException paramString)
        {
          paramString.printStackTrace();
        }
        if (paramContext != null) {
          return paramContext.versionCode;
        }
      }
      return 0;
    }
    return 0;
  }
  
  public static String getHainaSignMd5(PackageInfo paramPackageInfo)
  {
    String str = "";
    Object localObject2 = str;
    if (paramPackageInfo != null)
    {
      Object localObject3 = str;
      Object localObject1 = str;
      try
      {
        paramPackageInfo = paramPackageInfo.signatures;
        localObject2 = str;
        if (paramPackageInfo != null)
        {
          localObject3 = str;
          localObject1 = str;
          localObject2 = str;
          if (paramPackageInfo.length > 0)
          {
            localObject3 = str;
            localObject1 = str;
            paramPackageInfo = ByteUtils.byteToHexString(Md5Utils.getMD5(paramPackageInfo[0].toByteArray()));
            localObject3 = paramPackageInfo;
            localObject1 = paramPackageInfo;
            localObject2 = paramPackageInfo;
            if (!TextUtils.isEmpty(paramPackageInfo))
            {
              localObject3 = paramPackageInfo;
              localObject1 = paramPackageInfo;
              paramPackageInfo = paramPackageInfo.toLowerCase();
              return paramPackageInfo;
            }
          }
        }
      }
      catch (Error paramPackageInfo)
      {
        paramPackageInfo.printStackTrace();
        return (String)localObject3;
      }
      catch (Exception paramPackageInfo)
      {
        paramPackageInfo.printStackTrace();
        localObject2 = localObject1;
      }
    }
    return (String)localObject2;
  }
  
  public static Drawable getInstalledApkIcon(String paramString, Context paramContext)
  {
    if ((paramContext != null) && (!TextUtils.isEmpty(paramString)))
    {
      paramString = getInstalledPKGInfo(paramString, paramContext);
      if (paramString == null) {}
    }
    try
    {
      paramString = paramString.applicationInfo.loadIcon(paramContext.getPackageManager());
      return paramString;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static Bitmap getInstalledApkIconBmp(String paramString, Context paramContext)
  {
    return BitmapUtils.drawableToBitmap(getInstalledApkIcon(paramString, paramContext));
  }
  
  public static PackageInfo getInstalledPKGInfo(String paramString, Context paramContext)
  {
    return getInstalledPKGInfo(paramString, paramContext, 0);
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
  
  public static List<ResolveInfo> getIntentActivities(Context paramContext, Intent paramIntent, int paramInt)
  {
    Object localObject = null;
    if (paramContext == null) {
      return null;
    }
    PackageManager localPackageManager = ContextHolder.getAppContext().getPackageManager();
    paramContext = (Context)localObject;
    if (localPackageManager != null) {}
    try
    {
      paramContext = localPackageManager.queryIntentActivities(paramIntent, paramInt);
      return paramContext;
    }
    catch (RuntimeException paramContext) {}
    return null;
  }
  
  public static String getPkgNameFromIntent(Intent paramIntent)
  {
    try
    {
      paramIntent = paramIntent.getDataString().substring(8);
      return paramIntent;
    }
    catch (Exception paramIntent)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static List<ResolveInfo> getResovleInfoByIntent(Intent paramIntent)
  {
    PackageManager localPackageManager = ContextHolder.getAppContext().getPackageManager();
    try
    {
      paramIntent = localPackageManager.queryIntentActivities(paramIntent, 32);
      return paramIntent;
    }
    catch (Exception paramIntent)
    {
      paramIntent.printStackTrace();
    }
    return null;
  }
  
  public static String getSignMd5(PackageInfo paramPackageInfo)
  {
    if (paramPackageInfo != null) {
      try
      {
        Signature[] arrayOfSignature = paramPackageInfo.signatures;
        String str = "";
        paramPackageInfo = str;
        if (arrayOfSignature != null)
        {
          paramPackageInfo = str;
          if (arrayOfSignature.length > 0) {
            paramPackageInfo = arrayOfSignature[0].toCharsString();
          }
        }
        if (!TextUtils.isEmpty(paramPackageInfo))
        {
          paramPackageInfo = Md5Utils.getMD5(paramPackageInfo).toLowerCase();
          return paramPackageInfo;
        }
      }
      catch (Error paramPackageInfo)
      {
        paramPackageInfo.printStackTrace();
        return "";
      }
      catch (Exception paramPackageInfo)
      {
        paramPackageInfo.printStackTrace();
      }
    }
    return "";
  }
  
  public static String getSignMd5(String paramString)
  {
    String str1 = "";
    Signature[] arrayOfSignature = SignatureUtil.collectCertificates(paramString);
    String str2 = "";
    paramString = str2;
    if (arrayOfSignature != null)
    {
      paramString = str2;
      if (arrayOfSignature.length > 0) {
        paramString = arrayOfSignature[0].toCharsString();
      }
    }
    if (!TextUtils.isEmpty(paramString)) {
      str1 = Md5Utils.getMD5(paramString).toLowerCase();
    }
    return str1;
  }
  
  public static byte isAPKInstalled(Context paramContext, String paramString)
  {
    try
    {
      paramContext = paramContext.getPackageManager();
      paramString = paramContext.getPackageArchiveInfo(paramString, 1);
      if (paramString != null)
      {
        paramContext = paramContext.getPackageInfo(paramString.packageName, 0);
        if (paramContext.versionCode == paramString.versionCode) {
          return 1;
        }
        int i = paramContext.versionCode;
        int j = paramString.versionCode;
        if (i > j) {
          return 3;
        }
        return 2;
      }
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return 0;
  }
  
  public static boolean isExpectedIntentFilter(List<ResolveInfo> paramList, String paramString)
  {
    if (paramList != null)
    {
      if (paramString == null) {
        return false;
      }
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        Object localObject = (ResolveInfo)paramList.next();
        if (((ResolveInfo)localObject).activityInfo != null)
        {
          localObject = ((ResolveInfo)localObject).activityInfo.packageName;
          if ((!TextUtils.isEmpty((CharSequence)localObject)) && (((String)localObject).equalsIgnoreCase(paramString))) {
            return true;
          }
        }
      }
      return false;
    }
    return false;
  }
  
  public static boolean isInBackground()
  {
    Object localObject = (ActivityManager)ContextHolder.getAppContext().getSystemService("activity");
    if (localObject != null)
    {
      localObject = ((ActivityManager)localObject).getRunningTasks(1);
      if ((localObject != null) && (!((List)localObject).isEmpty()) && (!((ActivityManager.RunningTaskInfo)((List)localObject).get(0)).topActivity.getPackageName().equals(ContextHolder.getAppContext().getPackageName()))) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isInstalledPKGExist(String paramString, Context paramContext)
  {
    try
    {
      paramContext.getPackageManager().getPackageInfo(paramString, 0);
      return true;
    }
    catch (Exception paramString) {}
    return false;
  }
  
  public static boolean isRunning(String paramString)
  {
    Object localObject2 = (ActivityManager)ContextHolder.getAppContext().getSystemService("activity");
    if (localObject2 != null)
    {
      Object localObject1 = null;
      try
      {
        localObject2 = ((ActivityManager)localObject2).getRunningAppProcesses();
        localObject1 = localObject2;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      if (localObject1 == null) {
        return false;
      }
      int i = 0;
      while (i < ((List)localObject1).size())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)((List)localObject1).get(i);
        if ((!localRunningAppProcessInfo.processName.startsWith(paramString)) && (!localRunningAppProcessInfo.pkgList[0].equals(paramString))) {
          i += 1;
        } else {
          return true;
        }
      }
    }
    return false;
  }
  
  public static boolean isSysPKGExistByClassName(String paramString)
  {
    try
    {
      Class.forName(paramString);
      return true;
    }
    catch (ClassNotFoundException paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public static boolean isSysPKGExistByPKGName(String paramString)
  {
    return Package.getPackage(paramString) != null;
  }
  
  public static boolean isSystemApp(PackageInfo paramPackageInfo)
  {
    boolean bool = false;
    if (paramPackageInfo == null) {
      return false;
    }
    if ((paramPackageInfo.applicationInfo.flags & 0x1) != 0) {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isSystemApp(String paramString, Context paramContext)
  {
    return isSystemApp(getInstalledPKGInfo(paramString, paramContext));
  }
  
  public static boolean isSystemUpdateApp(PackageInfo paramPackageInfo)
  {
    boolean bool = false;
    if (paramPackageInfo == null) {
      return false;
    }
    if ((paramPackageInfo.applicationInfo.flags & 0x80) != 0) {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isUserApp(PackageInfo paramPackageInfo)
  {
    return (!isSystemApp(paramPackageInfo)) && (!isSystemUpdateApp(paramPackageInfo));
  }
  
  public static boolean runApkByPackageName(String paramString)
  {
    try
    {
      Context localContext = ContextHolder.getAppContext();
      paramString = localContext.getPackageManager().getLaunchIntentForPackage(paramString);
      if (paramString != null)
      {
        paramString.addFlags(268435456);
        localContext.startActivity(paramString);
        return true;
      }
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public static boolean uninstallApk(Context paramContext, String paramString)
  {
    FLogger.d("PackageCheckUtils", "uninstallApk");
    if (TextUtils.isEmpty(paramString))
    {
      paramContext = new StringBuilder();
      paramContext.append("pkgName : ");
      paramContext.append(paramString);
      FLogger.d("PackageCheckUtils", paramContext.toString());
      return false;
    }
    if (getInstalledPKGInfo(paramString, paramContext) == null)
    {
      paramContext = new StringBuilder();
      paramContext.append(paramString);
      paramContext.append(" is not exist!!");
      FLogger.d("PackageCheckUtils", paramContext.toString());
      return false;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("package:");
    localStringBuilder.append(paramString);
    paramString = new Intent("android.intent.action.DELETE", Uri.parse(localStringBuilder.toString()));
    paramString.addFlags(268435456);
    try
    {
      paramContext.startActivity(paramString);
      return true;
    }
    catch (ActivityNotFoundException paramContext) {}
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\PackageUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */