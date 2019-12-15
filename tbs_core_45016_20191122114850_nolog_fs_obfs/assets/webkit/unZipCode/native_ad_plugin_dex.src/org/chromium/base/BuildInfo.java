package org.chromium.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import org.chromium.base.annotations.CalledByNative;

public class BuildInfo
{
  public static final int ABI_NAME_INDEX = 14;
  public static final int ANDROID_BUILD_FP_INDEX = 11;
  public static final int ANDROID_BUILD_ID_INDEX = 2;
  public static final int BRAND_INDEX = 0;
  public static final int DEVICE_INDEX = 1;
  public static final int GMS_CORE_VERSION_INDEX = 12;
  public static final int INSTALLER_PACKAGE_NAME_INDEX = 13;
  public static final int MODEL_INDEX = 4;
  
  private static String a()
  {
    return Build.FINGERPRINT.substring(0, Math.min(Build.FINGERPRINT.length(), 128));
  }
  
  private static String a(PackageManager paramPackageManager)
  {
    try
    {
      paramPackageManager = Integer.toString(paramPackageManager.getPackageInfo("com.google.android.gms", 0).versionCode);
      return paramPackageManager;
    }
    catch (PackageManager.NameNotFoundException paramPackageManager) {}
    return "gms versionCode not available.";
  }
  
  @CalledByNative
  public static String[] getAll()
  {
    try
    {
      String str3 = ContextUtils.getApplicationContext().getPackageName();
      Object localObject4 = ContextUtils.getApplicationContext().getPackageManager();
      Object localObject5 = ((PackageManager)localObject4).getPackageInfo(str3, 0);
      String str1;
      if (((PackageInfo)localObject5).versionCode <= 0) {
        str1 = "";
      } else {
        str1 = Integer.toString(((PackageInfo)localObject5).versionCode);
      }
      String str2;
      if (((PackageInfo)localObject5).versionName == null) {
        str2 = "";
      } else {
        str2 = ((PackageInfo)localObject5).versionName;
      }
      Object localObject1 = ((PackageManager)localObject4).getApplicationLabel(((PackageInfo)localObject5).applicationInfo);
      if (localObject1 == null) {
        localObject1 = "";
      } else {
        localObject1 = ((CharSequence)localObject1).toString();
      }
      Object localObject3 = ((PackageManager)localObject4).getInstallerPackageName(str3);
      Object localObject2 = localObject3;
      if (localObject3 == null) {
        localObject2 = "";
      }
      if (Build.VERSION.SDK_INT >= 21)
      {
        localObject3 = TextUtils.join(", ", Build.SUPPORTED_ABIS);
      }
      else
      {
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("ABI1: ");
        ((StringBuilder)localObject3).append(Build.CPU_ABI);
        ((StringBuilder)localObject3).append(", ABI2: ");
        ((StringBuilder)localObject3).append(Build.CPU_ABI2);
        localObject3 = ((StringBuilder)localObject3).toString();
      }
      long l;
      if (((PackageInfo)localObject5).versionCode > 10) {
        l = ((PackageInfo)localObject5).versionCode;
      } else {
        l = ((PackageInfo)localObject5).lastUpdateTime;
      }
      localObject5 = String.format("@%s", new Object[] { Long.toHexString(l) });
      String str4 = Build.BRAND;
      String str5 = Build.DEVICE;
      String str6 = Build.ID;
      String str7 = Build.MANUFACTURER;
      String str8 = Build.MODEL;
      int i = Build.VERSION.SDK_INT;
      String str9 = Build.TYPE;
      String str10 = a();
      localObject4 = a((PackageManager)localObject4);
      String str11 = BuildConfig.FIREBASE_APP_ID;
      return new String[] { str4, str5, str6, str7, str8, String.valueOf(i), str9, localObject1, str3, str1, str2, str10, localObject4, localObject2, localObject3, str11, localObject5 };
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new RuntimeException(localNameNotFoundException);
    }
  }
  
  public static String getExtractedFileSuffix()
  {
    return getAll()[16];
  }
  
  public static String getPackageLabel()
  {
    return getAll()[7];
  }
  
  public static String getPackageName()
  {
    return ContextUtils.getApplicationContext().getPackageName();
  }
  
  public static String getPackageVersionCode()
  {
    return getAll()[9];
  }
  
  public static String getPackageVersionName()
  {
    return getAll()[10];
  }
  
  public static boolean isAtLeastP()
  {
    return (Build.VERSION.CODENAME.equals("P")) || (Build.VERSION.CODENAME.equals("Q"));
  }
  
  public static boolean isAtLeastQ()
  {
    return Build.VERSION.SDK_INT >= 29;
  }
  
  public static boolean isDebugAndroid()
  {
    return ("eng".equals(Build.TYPE)) || ("userdebug".equals(Build.TYPE));
  }
  
  public static boolean targetsAtLeastP()
  {
    return (isAtLeastP()) && (ContextUtils.getApplicationContext().getApplicationInfo().targetSdkVersion == 10000);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\BuildInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */