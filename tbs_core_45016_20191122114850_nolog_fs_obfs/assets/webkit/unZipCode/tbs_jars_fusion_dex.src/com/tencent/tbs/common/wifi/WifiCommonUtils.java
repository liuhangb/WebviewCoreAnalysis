package com.tencent.tbs.common.wifi;

import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.Md5Utils;
import com.tencent.mtt.ContextHolder;
import java.io.File;

public class WifiCommonUtils
{
  public static final String DIR_WIFI = "wf";
  public static final String FILE_PREFIX_IMG = "conf_img_";
  private static final String TAG = "WifiCommonUtils";
  public static final String WIFI_MANAGER_APK_NAME = "com.tencent.wifimanager.apk";
  public static final String WIFI_MANAGER_PKG_NAME = "com.tencent.wifimanager";
  public static final String WIFI_MANAGER_URL_4_DL = "http://tools.3g.qq.com/j/llqhz";
  
  public static WifiInfo getConnectionInfo()
  {
    Object localObject = (WifiManager)ContextHolder.getAppContext().getSystemService("wifi");
    try
    {
      localObject = ((WifiManager)localObject).getConnectionInfo();
      return (WifiInfo)localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public static String getSsidWithoutQuotation(String paramString)
  {
    String str;
    if (!TextUtils.isEmpty(paramString))
    {
      int i = paramString.indexOf("\"");
      int j = paramString.lastIndexOf("\"");
      str = paramString;
      if (i == 0)
      {
        str = paramString;
        if (j == paramString.length() - 1) {
          return paramString.substring(1, j);
        }
      }
    }
    else
    {
      str = "";
    }
    return str;
  }
  
  public static File getWifiConfigImgFile(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("conf_img_");
    localStringBuilder.append(Md5Utils.getMD5(paramString));
    return getWifiFile(localStringBuilder.toString());
  }
  
  public static File getWifiDir()
  {
    File localFile = new File(FileUtils.getPublicFilesDir(), "wf");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getWifiDir() path: ");
    localStringBuilder.append(localFile.getAbsolutePath());
    LogUtils.d("WifiCommonUtils", localStringBuilder.toString());
    if (!localFile.exists())
    {
      localFile.mkdir();
      return localFile;
    }
    if (!localFile.isDirectory())
    {
      localFile.delete();
      localFile.mkdir();
    }
    return localFile;
  }
  
  public static File getWifiFile(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    return new File(getWifiDir(), paramString);
  }
  
  public static boolean isConnected()
  {
    if (isWifiEnabled())
    {
      WifiInfo localWifiInfo = getConnectionInfo();
      if ((localWifiInfo != null) && (isSsidValid(localWifiInfo.getSSID())) && (localWifiInfo.getSupplicantState() == SupplicantState.COMPLETED)) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isSimAbsent()
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)ContextHolder.getAppContext().getSystemService("phone");
    int i = localTelephonyManager.getSimState();
    boolean bool = true;
    if (i != 1)
    {
      if (localTelephonyManager.getSimState() == 0) {
        return true;
      }
      bool = false;
    }
    return bool;
  }
  
  public static boolean isSsidValid(String paramString)
  {
    return (!TextUtils.isEmpty(paramString)) && (!TextUtils.equals(paramString, "0x")) && (!TextUtils.equals(paramString, "<unknown ssid>"));
  }
  
  public static boolean isWifiEnabled()
  {
    Object localObject1 = null;
    try
    {
      localObject2 = (WifiManager)ContextHolder.getAppContext().getSystemService("wifi");
    }
    catch (Exception localException3)
    {
      Object localObject2;
      boolean bool;
      label21:
      for (;;) {}
    }
    try
    {
      bool = ((WifiManager)localObject2).isWifiEnabled();
      return bool;
    }
    catch (Exception localException2)
    {
      break label21;
    }
    localObject1 = localObject2;
    localObject2 = localObject1;
    if (localObject1 == null) {}
    try
    {
      localObject2 = (WifiManager)ContextHolder.getAppContext().getSystemService("wifi");
      bool = ((WifiManager)localObject2).isWifiEnabled();
      return bool;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\WifiCommonUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */