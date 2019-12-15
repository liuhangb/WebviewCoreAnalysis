package com.tencent.common.plugin;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import com.tencent.common.utils.ReflectionUtils;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;

public class JarFileParser
{
  public static final int MSG_PLUGIN_PACKAGE_ERROR = 2;
  public static final String sContextMenuIcon = "ContextMenuIcon";
  public static final String sContextMenuRes = "ContextMenuRes";
  public static final String sContextMenuText = "ContextMenuText";
  public static final String sLaunchMode = "LaunchMode";
  public static final String sLaunchMode_Activity = "Activity";
  public static final String sLaunchMode_Dialog = "Dialog";
  public static final String sLaunchMode_FullScreen = "FullScreen";
  public static final String sLaunchMode_Service = "Service";
  public static final String sSoName = "SoName";
  
  public static Bundle getPackageAppMeteData(String paramString)
  {
    try
    {
      Object localObject1 = Class.forName("android.content.pm.PackageParser");
      Class localClass = Class.forName("android.content.pm.PackageParser$Package");
      Object localObject2 = ((Class)localObject1).getConstructor(new Class[] { String.class });
      localObject1 = localClass.getField("mAppMetaData");
      localObject2 = ((Constructor)localObject2).newInstance(new Object[] { paramString });
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localDisplayMetrics.setToDefaults();
      File localFile = new File(paramString);
      paramString = ReflectionUtils.invokeInstance(localObject2, "parsePackage", new Class[] { File.class, String.class, DisplayMetrics.class, Integer.TYPE }, new Object[] { localFile, paramString, localDisplayMetrics, Integer.valueOf(0) });
      if (paramString == null) {
        return null;
      }
      ReflectionUtils.invokeInstance(localObject2, "collectCertificates", new Class[] { localClass, Integer.TYPE }, new Object[] { paramString, Integer.valueOf(64) });
      paramString = (Bundle)((Field)localObject1).get(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public static PackageInfo getPackageInfo(Context paramContext, String paramString, int paramInt)
  {
    localContext = paramContext.getApplicationContext();
    if (paramInt == 0) {}
    try
    {
      return localContext.getPackageManager().getPackageArchiveInfo(paramString, 1);
    }
    catch (Exception paramContext)
    {
      Class localClass1;
      Class localClass2;
      Object localObject1;
      Object localObject2;
      paramContext.printStackTrace();
      return localContext.getPackageManager().getPackageArchiveInfo(paramString, 1);
    }
    localClass1 = Class.forName("android.content.pm.PackageParser");
    localClass2 = Class.forName("android.content.pm.PackageParser$Package");
    localObject1 = localClass1.getConstructor(new Class[] { String.class }).newInstance(new Object[] { paramString });
    paramContext = new DisplayMetrics();
    paramContext.setToDefaults();
    localObject2 = new File(paramString);
    localObject2 = ReflectionUtils.invokeInstance(localObject1, "parsePackage", new Class[] { File.class, String.class, DisplayMetrics.class, Integer.TYPE }, new Object[] { localObject2, paramString, paramContext, Integer.valueOf(0) });
    if (localObject2 == null) {
      return null;
    }
    if ((paramInt & 0x40) != 0) {
      ReflectionUtils.invokeInstance(localObject1, "collectCertificates", new Class[] { File.class, String.class, DisplayMetrics.class, Integer.TYPE }, new Object[] { localObject2, Integer.valueOf(paramInt) });
    }
    for (;;)
    {
      try
      {
        paramContext = localClass1.getMethod("generatePackageInfo", new Class[] { localClass2, int[].class, Integer.TYPE, Long.TYPE, Long.TYPE }).invoke(localObject1, new Object[] { localObject2, null, Integer.valueOf(paramInt), Integer.valueOf(0), Integer.valueOf(0) });
      }
      catch (NoSuchMethodException paramContext)
      {
        continue;
      }
      try
      {
        paramContext = localClass1.getMethod("generatePackageInfo", new Class[] { localClass2, Integer.TYPE, Integer.TYPE }).invoke(localObject1, new Object[] { localObject2, null, Integer.valueOf(paramInt) });
      }
      catch (NoSuchMethodException paramContext)
      {
        continue;
      }
      try
      {
        paramContext = localClass1.getMethod("generatePackageInfo", new Class[] { localClass2, int[].class, Integer.TYPE }).invoke(localObject1, new Object[] { localObject2, null, Integer.valueOf(paramInt) });
      }
      catch (NoSuchMethodException paramContext)
      {
        continue;
      }
      try
      {
        paramContext = localClass1.getMethod("generatePackageInfo", new Class[] { localClass2, int[].class, Integer.TYPE, Long.TYPE, Long.TYPE, HashSet.class, Boolean.TYPE, Integer.TYPE, Integer.TYPE }).invoke(localObject1, new Object[] { localObject2, null, Integer.valueOf(paramInt), Integer.valueOf(0), Integer.valueOf(0), null, Boolean.valueOf(false), Integer.valueOf(0), Integer.valueOf(0) });
      }
      catch (NoSuchMethodException paramContext) {}
    }
    paramContext = Class.forName("android.content.pm.PackageUserState");
    paramContext = localClass1.getMethod("generatePackageInfo", new Class[] { localClass2, int[].class, Integer.TYPE, Long.TYPE, Long.TYPE, HashSet.class, paramContext }).invoke(localObject1, new Object[] { localObject2, null, Integer.valueOf(paramInt), Integer.valueOf(0), Integer.valueOf(0), null, paramContext.newInstance() });
    if ((paramContext != null) && ((paramContext instanceof PackageInfo)))
    {
      paramContext = (PackageInfo)paramContext;
      return paramContext;
    }
    return null;
  }
  
  public static boolean parseAPKFile(Context paramContext, String paramString, JarFileInfo paramJarFileInfo)
  {
    try
    {
      Object localObject1 = paramContext.getApplicationContext();
      Object localObject2 = ((Context)localObject1).getPackageManager().getPackageArchiveInfo(paramString, 192);
      if ((localObject2 != null) && (((PackageInfo)localObject2).applicationInfo != null))
      {
        paramContext = ((PackageInfo)localObject2).applicationInfo;
        if (paramContext.metaData != null)
        {
          paramJarFileInfo.mSupportResType = paramContext.metaData.getString("ContextMenuRes");
          paramJarFileInfo.mContextMenuText = paramContext.metaData.getString("ContextMenuText");
          paramJarFileInfo.mLaunchMode = paramContext.metaData.getString("LaunchMode");
          paramJarFileInfo.mSoName = paramContext.metaData.getString("SoName");
          paramContext.metaData.getString("ContextMenuIcon");
        }
        else
        {
          localObject3 = getPackageAppMeteData(paramString);
          if (localObject3 != null)
          {
            paramJarFileInfo.mSupportResType = ((Bundle)localObject3).getString("ContextMenuRes");
            paramJarFileInfo.mContextMenuText = ((Bundle)localObject3).getString("ContextMenuText");
            paramJarFileInfo.mLaunchMode = ((Bundle)localObject3).getString("LaunchMode");
            paramJarFileInfo.mSoName = ((Bundle)localObject3).getString("SoName");
            ((Bundle)localObject3).getString("ContextMenuIcon");
          }
        }
        paramJarFileInfo.mPackageName = paramContext.packageName;
        paramJarFileInfo.mVersionCode = ((PackageInfo)localObject2).versionCode;
        paramJarFileInfo.mVersionName = ((PackageInfo)localObject2).versionName;
        if ((((PackageInfo)localObject2).signatures != null) && (((PackageInfo)localObject2).signatures.length > 0)) {
          paramJarFileInfo.mSignature = localObject2.signatures[0];
        }
        localObject2 = (AssetManager)AssetManager.class.newInstance();
        Object localObject3 = AssetManager.class.getDeclaredMethod("addAssetPath", new Class[] { String.class });
        ((Method)localObject3).setAccessible(true);
        ((Method)localObject3).invoke(localObject2, new Object[] { paramString });
        paramString = new Resources((AssetManager)localObject2, ((Context)localObject1).getResources().getDisplayMetrics(), ((Context)localObject1).getResources().getConfiguration());
        if (paramContext.icon != 0)
        {
          localObject1 = paramString.getDrawable(paramContext.icon);
          if ((localObject1 instanceof BitmapDrawable)) {
            paramJarFileInfo.mAppIcon = ((BitmapDrawable)localObject1);
          }
        }
        if (paramContext.labelRes != 0) {
          paramJarFileInfo.mAppName = paramString.getString(paramContext.labelRes);
        }
        paramJarFileInfo.mHasParsed = true;
        return true;
      }
      paramJarFileInfo.mBrokenJarFile = true;
      return false;
    }
    catch (Throwable paramContext)
    {
      paramJarFileInfo.mHasParsed = true;
      paramContext.printStackTrace();
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\JarFileParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */