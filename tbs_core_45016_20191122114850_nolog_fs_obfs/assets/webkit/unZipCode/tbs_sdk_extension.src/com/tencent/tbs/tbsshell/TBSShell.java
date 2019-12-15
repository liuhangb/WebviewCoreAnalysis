package com.tencent.tbs.tbsshell;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.tbsshell.common.SmttServiceCommon;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TBSShell
{
  public static final String APP_DEMO = "com.tencent.tbs";
  public static final String APP_DEMO_TEST = "com.tencent.mtt.sdk.test";
  public static final String APP_LIB_FOLDER_NAME = "lib";
  public static final String APP_QB = "com.tencent.mtt";
  public static final String APP_QQ = "com.tencent.mobileqq";
  public static final String APP_QZONE = "com.qzone";
  public static final String APP_WX = "com.tencent.mm";
  public static final String DATA_FOLDER_NAME = "/data/data";
  private static String DEFAULT_TES_CORE_VER = "";
  public static final String TAG = "TBSShell";
  public static final boolean TBS_APMTEST_FLAG = false;
  private static final String TBS_CORE_VERSION = "";
  public static final boolean TBS_DEBUG_FLAG = false;
  public static final boolean TBS_TESTSWITCH_FLAG = false;
  public static int VERSION = 0;
  public static final String X5_CORE_FOLDER_NAME = "x5core";
  public static final String X5_FILE_RES_APK = "res.apk";
  public static final String X5_SHARE_FOLDER_NAME = "x5_share";
  private static Context mCallerAppContext;
  private static String mDexOutPutDir = null;
  private static Map<String, Object> mSettings;
  public static String mTbsResourcesPath = null;
  private static String[] mTbsWebViewBaseFileList;
  private static String mTesCoreVer = DEFAULT_TES_CORE_VER;
  private static boolean mUseTbsCorePrivateClassLoader = false;
  private static File mX5CorePath;
  private static File mX5SharePath;
  public static DexLoader sBaseDexLoader;
  public static boolean sCompatibleDexLoaderGetClassLoader = false;
  private static ConcurrentHashMap<String[], DexLoader> sMapDexDict;
  public static final boolean sShouldPackageWebViewDexIntoFusionDex = true;
  
  static
  {
    VERSION = 1;
    sCompatibleDexLoaderGetClassLoader = true;
    mCallerAppContext = null;
    mUseTbsCorePrivateClassLoader = false;
    mSettings = null;
  }
  
  public static Context getCallerAppContect()
  {
    return mCallerAppContext;
  }
  
  public static String getCurrentTbsCoreVer()
  {
    return "";
  }
  
  public static DexLoader getDexLoader(String[] paramArrayOfString)
  {
    if ((paramArrayOfString != null) && (paramArrayOfString.length != 0))
    {
      if (sMapDexDict == null) {
        sMapDexDict = new ConcurrentHashMap();
      }
      return (DexLoader)sMapDexDict.get(paramArrayOfString);
    }
    return null;
  }
  
  public static String getLoadFailureDetails()
  {
    return TBSShellFactory.getTbsShellDelegate().getLoadFailureDetails();
  }
  
  protected static String[] getReaderFileList(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("/reader_base_dex.jar");
    return new String[] { localStringBuilder.toString() };
  }
  
  private static DexClassLoader getSysDexClassLoader(DexLoader paramDexLoader)
    throws NoSuchMethodError
  {
    if (paramDexLoader != null) {
      try
      {
        DexClassLoader localDexClassLoader = paramDexLoader.getClassLoader();
        return localDexClassLoader;
      }
      catch (NoSuchMethodError localNoSuchMethodError)
      {
        if ((localNoSuchMethodError.getMessage() != null) && (localNoSuchMethodError.getMessage().contains("getClassLoader"))) {}
        Field localField;
        try
        {
          localField = paramDexLoader.getClass().getDeclaredField("mClassLoader");
          localField.setAccessible(true);
          paramDexLoader = (DexClassLoader)localField.get(paramDexLoader);
          localField.setAccessible(false);
          return paramDexLoader;
        }
        catch (Throwable paramDexLoader) {}
        throw localField;
      }
    } else {
      return null;
    }
    return null;
  }
  
  public static String getTbsCoreVersionCode()
  {
    return mTesCoreVer;
  }
  
  public static Map<String, Object> getTbsSettings()
  {
    return mSettings;
  }
  
  protected static String[] getVideoFileList(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("/video_impl_dex.jar");
    return new String[] { localStringBuilder.toString() };
  }
  
  public static void initTbsSettings(Map<String, Object> paramMap)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("initTbsSettings - ");
    localStringBuilder.append(paramMap);
    localStringBuilder.toString();
    mSettings = paramMap;
    if (paramMap != null)
    {
      try
      {
        mUseTbsCorePrivateClassLoader = ((Boolean)paramMap.get("use_private_classloader")).booleanValue();
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
      try
      {
        paramMap = (String)paramMap.get("AppKey");
        if (paramMap != null)
        {
          X5CoreBeaconUploader.setAppKey(paramMap);
          return;
        }
      }
      catch (Throwable paramMap)
      {
        paramMap.printStackTrace();
      }
    }
  }
  
  public static int initTesRuntimeEnvironment(Context paramContext1, Context paramContext2, DexLoader paramDexLoader, String paramString1, String paramString2)
  {
    try
    {
      int i = TBSShellFactory.getTbsShellDelegate().initTbsRuntimeEnvironment(paramContext1, paramContext2, getSysDexClassLoader(paramDexLoader), paramString1, paramString2, TBSShellFactory.getTbsShellDelegate().getSdkVersionName(), VERSION, mTesCoreVer);
      return i;
    }
    finally
    {
      paramContext1 = finally;
      throw paramContext1;
    }
  }
  
  public static int initTesRuntimeEnvironment(Context paramContext1, Context paramContext2, DexLoader paramDexLoader, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
  {
    try
    {
      setTesSdkVersionName(paramString3);
      VERSION = paramInt;
      mTesCoreVer = paramString4;
      paramInt = initTesRuntimeEnvironment(paramContext1, paramContext2, getSysDexClassLoader(paramDexLoader), paramString1, paramString2);
      return paramInt;
    }
    finally
    {
      paramContext1 = finally;
      throw paramContext1;
    }
  }
  
  public static int initTesRuntimeEnvironment(Context paramContext1, Context paramContext2, DexClassLoader paramDexClassLoader, String paramString1, String paramString2)
  {
    try
    {
      int i = TBSShellFactory.getTbsShellDelegate().initTbsRuntimeEnvironment(paramContext1, paramContext2, paramDexClassLoader, paramString1, paramString2, TBSShellFactory.getTbsShellDelegate().getSdkVersionName(), VERSION, mTesCoreVer);
      return i;
    }
    finally
    {
      paramContext1 = finally;
      throw paramContext1;
    }
  }
  
  public static int initTesRuntimeEnvironmentAndNotLoadSo(Context paramContext1, Context paramContext2, DexLoader paramDexLoader, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
  {
    try
    {
      setTesSdkVersionName(paramString3);
      VERSION = paramInt;
      mTesCoreVer = paramString4;
      paramInt = TBSShellFactory.getTbsShellDelegate().initTbsRuntimeEnvironmentAndNotLoadSo(paramContext1, paramContext2, getSysDexClassLoader(paramDexLoader), paramString1, paramString2, TBSShellFactory.getTbsShellDelegate().getSdkVersionName(), VERSION, mTesCoreVer);
      return paramInt;
    }
    finally
    {
      paramContext1 = finally;
      throw paramContext1;
    }
  }
  
  private static boolean initVideoModule(DexLoader paramDexLoader, Context paramContext1, Context paramContext2, String paramString1, String paramString2)
  {
    return TBSShellFactory.getTbsShellDelegate().initVideoModule(getSysDexClassLoader(paramDexLoader), paramContext1, paramContext2, paramString1, paramString2);
  }
  
  public static boolean isThirdPartyApp(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getApplicationContext().getApplicationInfo().packageName;
      if ((!paramContext.equals("com.tencent.mm")) && (!paramContext.equals("com.tencent.mtt")) && (!paramContext.equals("com.tencent.mobileqq")) && (!paramContext.equals("com.tencent.tbs")) && (!paramContext.equals("com.tencent.mtt.sdk.test")))
      {
        boolean bool = paramContext.equals("com.qzone");
        if (!bool) {}
      }
      else
      {
        return false;
      }
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return true;
  }
  
  public static void putInfo(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4) {}
  
  public static void setCustomQProxy(String paramString)
  {
    TBSShellFactory.getSmttServiceCommon().setCustomQProxy(paramString);
  }
  
  public static void setLoadFailureDetails(String paramString)
  {
    if ((!TextUtils.isEmpty(paramString)) && (paramString.contains("is 32-bit instead of 64-bit"))) {
      Log.e("ERROR:", ".....................................................................................\n.............................................................................................\nX5 does not support the 64-bit mode to run, please refer to the solution: https://x5.tencent.com/tbs/technical.html#/detail/sdk/1/34cf1488-7dc2-41ca-a77f-0014112bcab7\n.....................................................................................\n.....................................................................................\n");
    }
    TBSShellFactory.getTbsShellDelegate().setLoadFailureDetails(paramString);
  }
  
  public static void setQbInfoForQua2_v3(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean)
  {
    TBSShellFactory.getTbsShellDelegate().setQbInfoForQua2_v3(paramString1, paramString2, paramString3, paramString4, paramString5, paramBoolean);
  }
  
  public static void setQua1FromUi(String paramString)
  {
    TBSShellFactory.getTbsShellDelegate().setQua1FromUi(paramString);
  }
  
  public static void setTesSdkVersionCode(int paramInt)
  {
    TBSShellFactory.getTbsShellDelegate().setSdkVersionCode(paramInt);
  }
  
  public static void setTesSdkVersionName(String paramString)
  {
    TBSShellFactory.getTbsShellDelegate().setSdkVersionName(paramString);
  }
  
  public static boolean useTbsCorePrivateClassLoader()
  {
    return mUseTbsCorePrivateClassLoader;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\TBSShell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */