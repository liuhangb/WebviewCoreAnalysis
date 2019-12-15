package com.tencent.tbs.common.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.tencent.common.http.Apn;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.baseinfo.ICoreService;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsSmttServiceProxy;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.baseinfo.TbsWupManager;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils
{
  public static final int MOBILE_INTERFACE = 0;
  public static final int NETWORK_MODULE = 2;
  public static final int NETWORK_TYPE_CHROMNET = 2;
  public static final int NETWORK_TYPE_DEFAULT = 0;
  public static final int NETWORK_TYPE_JAVANET = 1;
  public static final String PROXY_DOMAIN = "http://wup.imtt.qq.com:8080";
  public static final int WIFI_INTERFACE = 1;
  public static final String WUP_PROXY_DOMAIN = "wup.imtt.qq.com";
  private static final int mProxyIndex = 0;
  public static String mobileMacAddr;
  private static HostnameVerifier sHNV = ;
  public static String wifiMacAddr;
  
  public static void dismissWifiLoginNotify(Context paramContext) {}
  
  public static String getCurrentSSID(Context paramContext)
  {
    paramContext = (WifiManager)paramContext.getSystemService("wifi");
    try
    {
      paramContext = paramContext.getConnectionInfo();
      if (paramContext != null)
      {
        paramContext = paramContext.getSSID();
        return paramContext;
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return "";
  }
  
  public static String getIpAddress(Context paramContext)
  {
    String str = "";
    Object localObject2 = null;
    try
    {
      localObject1 = (WifiManager)paramContext.getSystemService("wifi");
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
      localObject1 = null;
    }
    paramContext = (Context)localObject2;
    if (localObject1 != null) {
      try
      {
        paramContext = ((WifiManager)localObject1).getConnectionInfo();
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
        paramContext = (Context)localObject2;
      }
    }
    Object localObject1 = str;
    if (paramContext != null)
    {
      int i = paramContext.getIpAddress();
      paramContext = new StringBuilder();
      paramContext.append(i & 0xFF);
      paramContext.append(".");
      paramContext.append(i >> 8 & 0xFF);
      paramContext.append(".");
      paramContext.append(i >> 16 & 0xFF);
      paramContext.append(".");
      paramContext.append(i >> 24 & 0xFF);
      localObject1 = paramContext.toString();
    }
    return (String)localObject1;
  }
  
  public static String getMacAddress(int paramInt)
  {
    Object localObject1;
    if (paramInt == 1)
    {
      localObject1 = "wlan0";
      str = wifiMacAddr;
      if (str != null) {
        return str;
      }
    }
    else
    {
      if (paramInt != 0) {
        break label254;
      }
      localObject1 = "p2p0";
      str = mobileMacAddr;
      if (str != null) {
        return str;
      }
    }
    String str = "";
    Object localObject2;
    try
    {
      Object localObject3 = NetworkInterface.getByName((String)localObject1);
      if (localObject3 == null)
      {
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("No Mac found for ");
        ((StringBuilder)localObject3).append((String)localObject1);
        return ((StringBuilder)localObject3).toString();
      }
      byte[] arrayOfByte = ((NetworkInterface)localObject3).getHardwareAddress();
      if (arrayOfByte == null)
      {
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("No Mac found for ");
        ((StringBuilder)localObject3).append((String)localObject1);
        return ((StringBuilder)localObject3).toString();
      }
      StringBuffer localStringBuffer = new StringBuffer();
      int i = 0;
      while (i < arrayOfByte.length)
      {
        if (i != 0) {
          localStringBuffer.append(':');
        }
        localObject3 = Integer.toHexString(arrayOfByte[i] & 0xFF);
        localObject1 = localObject3;
        if (((String)localObject3).length() == 1)
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(0);
          ((StringBuilder)localObject1).append((String)localObject3);
          localObject1 = ((StringBuilder)localObject1).toString();
        }
        localStringBuffer.append((String)localObject1);
        i += 1;
      }
      localObject1 = localStringBuffer.toString().toUpperCase();
    }
    catch (SocketException localSocketException)
    {
      localSocketException.printStackTrace();
      localObject2 = str;
    }
    if (paramInt == 1) {
      wifiMacAddr = (String)localObject2;
    }
    if (paramInt == 0) {
      mobileMacAddr = (String)localObject2;
    }
    return (String)localObject2;
    label254:
    return "error netInterface";
  }
  
  private static boolean hasNoSecurity(ScanResult paramScanResult)
  {
    return (!paramScanResult.capabilities.contains("WEP")) && (!paramScanResult.capabilities.contains("PSK")) && (!paramScanResult.capabilities.contains("EAP"));
  }
  
  public static boolean isOpenedWifiCheck(Context paramContext)
  {
    Object localObject = (WifiManager)paramContext.getSystemService("wifi");
    paramContext = ((WifiManager)localObject).getConnectionInfo();
    if (paramContext != null) {
      if (TextUtils.isEmpty(paramContext.getBSSID())) {
        return false;
      }
    }
    try
    {
      localObject = ((WifiManager)localObject).getScanResults();
      if (localObject == null) {
        return false;
      }
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        ScanResult localScanResult = (ScanResult)((Iterator)localObject).next();
        if ((localScanResult != null) && (localScanResult.BSSID.equals(paramContext.getBSSID()))) {
          return hasNoSecurity(localScanResult);
        }
      }
      return false;
    }
    catch (Exception paramContext) {}
    return false;
    return false;
  }
  
  public static void onConnectivityChanged(int paramInt)
  {
    if (TbsBaseModuleShell.getCoreService() != null) {
      TbsBaseModuleShell.getCoreService().onConnectivityChanged(paramInt);
    }
    if (PublicSettingManager.getInstance().getCanIpv6Proxy())
    {
      LogUtils.d("x5-ip-list", "NetworkUtils onConnectivityChanged setNeedCheckQProxy For IPV6");
      TbsSmttServiceProxy.getInstance().setNeedCheckQProxy(true, false, true);
    }
  }
  
  public static void onIPListChanged(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("NetworkUtils onIPListChanged apn changed, newApn=");
    localStringBuilder.append(Apn.getApnNameWithBSSID(paramInt));
    LogUtils.d("x5-ip-list", localStringBuilder.toString());
    if ((Apn.isNetworkConnected()) && (TbsUserInfo.getInstance().shouldUpdateIPList(paramInt))) {
      TbsUserInfo.getInstance().tryGetIPList(paramInt, true, false, true);
    }
    TbsWupManager.getInstance().onNetworkChanged();
  }
  
  public static void restoreHttpsHostNameVerifier()
  {
    HttpsURLConnection.setDefaultHostnameVerifier(sHNV);
  }
  
  public static void setBackProxyState(String paramString, int paramInt)
  {
    TbsUserInfo.getInstance().setProtocolFlag(paramString, paramInt);
    if (TbsBaseModuleShell.getCoreService() != null)
    {
      paramString = TbsBaseModuleShell.getCoreService();
      boolean bool;
      if (paramInt == 0) {
        bool = true;
      } else {
        bool = false;
      }
      paramString.setForceDirect(bool);
    }
  }
  
  public static void showWifiLoginNotify(Context paramContext) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\NetworkUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */