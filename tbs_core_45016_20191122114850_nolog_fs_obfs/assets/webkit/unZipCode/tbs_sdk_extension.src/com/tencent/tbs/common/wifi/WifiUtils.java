package com.tencent.tbs.common.wifi;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.ContextHolder;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class WifiUtils
{
  public static final byte CON_STATE_CONNECTED = 4;
  public static final int MAX_RSSI = -55;
  public static final int MIN_RSSI = -100;
  public static final int SECURITY_EAP = 3;
  public static final int SECURITY_NONE = 0;
  public static final int SECURITY_PSK = 1;
  public static final int SECURITY_UNKNOW = -1;
  public static final int SECURITY_WEP = 2;
  public static final int SIGNAL_BAD = 2;
  public static final int SIGNAL_GOOD = 4;
  public static final int SIGNAL_MID = 3;
  public static final int SIGNAL_UNKNOWN = -1;
  public static final int SIGNAL_VERY_BAD = 1;
  public static final int SOURCE_CANCEL_SHARE = 64;
  public static final int SOURCE_QBSDK = 2;
  public static final int SOURCE_SHARE = 16;
  public static final int SOURCE_TMS = 1;
  public static final int SOURCE_USER_INPUT = 4;
  public static final int SOURCE_WK = 8;
  public static final String TAG = "WifiUtils";
  public static final int WIFI_EAP_METHOD_AKA = 5;
  public static final int WIFI_EAP_METHOD_AKA_PRIME = 6;
  public static final int WIFI_EAP_METHOD_PEAP = 0;
  public static final int WIFI_EAP_METHOD_PWD = 3;
  public static final int WIFI_EAP_METHOD_SIM = 4;
  public static final int WIFI_EAP_METHOD_TLS = 1;
  public static final int WIFI_EAP_METHOD_TTLS = 2;
  public static final int WIFI_PEAP_PHASE2_GTC = 2;
  public static final int WIFI_PEAP_PHASE2_MSCHAPV2 = 1;
  public static final int WIFI_PEAP_PHASE2_NONE = 0;
  public static final int WIFI_TYPE_LOCKED_SAVED = 4;
  public static final int WIFI_TYPE_LOCKED_UNSAVED = 5;
  public static final int WIFI_TYPE_OPEN_WIFI = 3;
  public static final int WIFI_TYPE_RECOMMEND_OPEN_WIFI = 2;
  public static final int WIFI_TYPE_UNLOCKED = 1;
  
  public static int calculateSignalLevel(int paramInt1, int paramInt2)
  {
    if (paramInt1 <= -100) {
      return 0;
    }
    if (paramInt1 >= -55) {
      return paramInt2 - 1;
    }
    float f = paramInt2 - 1;
    return (int)((paramInt1 + 100) * f / 45.0F);
  }
  
  public static HashMap<String, WifiApInfo> combineWifiApInfo(HashMap<String, ArrayList<WifiApInfo>> paramHashMap)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      WifiApInfo localWifiApInfo2 = new WifiApInfo();
      Object localObject1 = null;
      try
      {
        ArrayList localArrayList = (ArrayList)paramHashMap.get(str1);
        localObject1 = localArrayList;
      }
      catch (Exception localException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        Object localObject2 = paramHashMap.keySet();
        if (localObject2 != null)
        {
          localObject2 = ((Set)localObject2).iterator();
          while (((Iterator)localObject2).hasNext())
          {
            String str2 = (String)((Iterator)localObject2).next();
            localStringBuilder.append(str2);
            localStringBuilder.append(":");
            localStringBuilder.append(paramHashMap.get(str2));
            localStringBuilder.append("\r\n");
          }
        }
        localException.printStackTrace();
      }
      if (localObject1 != null)
      {
        localObject1 = ((ArrayList)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          WifiApInfo localWifiApInfo1 = (WifiApInfo)((Iterator)localObject1).next();
          if (localWifiApInfo1.mLevel > localWifiApInfo2.mLevel)
          {
            localWifiApInfo2.mFrequency = localWifiApInfo1.mFrequency;
            localWifiApInfo2.mLevel = localWifiApInfo1.mLevel;
            localWifiApInfo2.mSafeType = localWifiApInfo1.mSafeType;
            localWifiApInfo2.mSsid = localWifiApInfo1.mSsid;
          }
          if (!localWifiApInfo2.mHasCloudPassword) {
            localWifiApInfo2.mHasCloudPassword = localWifiApInfo1.mHasCloudPassword;
          }
          if (isWiwide(localWifiApInfo1)) {
            localWifiApInfo2.mActionType |= 0x2;
          }
          if (localWifiApInfo1.mScore > localWifiApInfo2.mScore) {
            localWifiApInfo2.mScore = localWifiApInfo1.mScore;
          }
          int i = localWifiApInfo2.mSdkFlag;
          localWifiApInfo1.mSdkFlag |= i;
        }
      }
      localHashMap.put(str1, localWifiApInfo2);
    }
    return localHashMap;
  }
  
  public static HashMap<String, Object> convertObject(Object paramObject)
    throws IllegalAccessException
  {
    HashMap localHashMap = new HashMap();
    if (paramObject != null)
    {
      Field[] arrayOfField = paramObject.getClass().getFields();
      if (arrayOfField != null)
      {
        int j = arrayOfField.length;
        int i = 0;
        while (i < j)
        {
          Field localField = arrayOfField[i];
          if (!localField.isAccessible()) {
            localField.setAccessible(true);
          }
          localHashMap.put(localField.getName(), localField.get(paramObject));
          i += 1;
        }
      }
    }
    return localHashMap;
  }
  
  public static boolean forgetAp(WifiManager paramWifiManager, int paramInt)
  {
    boolean bool1;
    if ((paramWifiManager != null) && (paramInt != -1) && (paramWifiManager != null) && (paramInt != -1))
    {
      boolean bool2 = paramWifiManager.removeNetwork(paramInt);
      bool1 = bool2;
      if (bool2)
      {
        paramWifiManager.saveConfiguration();
        return bool2;
      }
    }
    else
    {
      bool1 = false;
    }
    return bool1;
  }
  
  public static List<WifiConfiguration> getConfiguredNetworks()
  {
    Object localObject = (WifiManager)ContextHolder.getAppContext().getSystemService("wifi");
    try
    {
      List localList = ((WifiManager)localObject).getConfiguredNetworks();
      return localList;
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
      try
      {
        localObject = ((WifiManager)localObject).getConfiguredNetworks();
        return (List<WifiConfiguration>)localObject;
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
    }
    return null;
  }
  
  public static String getConnectedSsid()
  {
    boolean bool = isWifiEnabled();
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (bool)
    {
      WifiInfo localWifiInfo = getConnectionInfo();
      localObject1 = localObject2;
      if (localWifiInfo != null) {
        localObject1 = localWifiInfo.getSSID();
      }
    }
    return (String)localObject1;
  }
  
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
  
  public static NetworkInfo getNetworkInfo()
  {
    Object localObject1;
    try
    {
      ConnectivityManager localConnectivityManager1 = (ConnectivityManager)ContextHolder.getAppContext().getSystemService("connectivity");
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      try
      {
        ConnectivityManager localConnectivityManager2 = (ConnectivityManager)ContextHolder.getAppContext().getSystemService("connectivity");
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        localObject1 = null;
      }
    }
    if (localObject1 != null) {}
    for (;;)
    {
      int i;
      try
      {
        if (Build.VERSION.SDK_INT < 21) {
          break label131;
        }
        Object localObject2 = ((ConnectivityManager)localObject1).getAllNetworks();
        if (localObject2 == null) {
          break label131;
        }
        int j = localObject2.length;
        i = 0;
        if (i >= j) {
          break label131;
        }
        localNetworkInfo = ((ConnectivityManager)localObject1).getNetworkInfo(localObject2[i]);
        if ((localNetworkInfo == null) || (localNetworkInfo.getType() != 1)) {
          break label124;
        }
        localObject2 = localNetworkInfo;
        if (localNetworkInfo == null) {
          localObject2 = ((ConnectivityManager)localObject1).getNetworkInfo(1);
        }
        return (NetworkInfo)localObject2;
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
      return null;
      label124:
      i += 1;
      continue;
      label131:
      NetworkInfo localNetworkInfo = null;
    }
  }
  
  public static List<ScanResult> getScanResults()
  {
    try
    {
      List localList = ((WifiManager)ContextHolder.getAppContext().getSystemService("wifi")).getScanResults();
      return localList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public static int getSecurity(ScanResult paramScanResult)
  {
    if (paramScanResult == null) {
      return -1;
    }
    return getSecurity(paramScanResult.capabilities);
  }
  
  public static int getSecurity(WifiConfiguration paramWifiConfiguration)
  {
    if (paramWifiConfiguration != null)
    {
      BitSet localBitSet = paramWifiConfiguration.allowedKeyManagement;
      if (localBitSet != null)
      {
        if (localBitSet.get(1)) {
          return 1;
        }
        if (!localBitSet.get(2))
        {
          if (localBitSet.get(3)) {
            return 3;
          }
          paramWifiConfiguration = paramWifiConfiguration.wepKeys;
          int i = 0;
          if (paramWifiConfiguration[0] != null) {
            i = 2;
          }
          return i;
        }
        return 3;
      }
    }
    return -1;
  }
  
  public static int getSecurity(String paramString)
  {
    if ((paramString != null) && (paramString != ""))
    {
      if (paramString.contains("WEP")) {
        return 2;
      }
      if (paramString.contains("PSK")) {
        return 1;
      }
      if (paramString.contains("EAP")) {
        return 3;
      }
      return 0;
    }
    return -1;
  }
  
  public static int getSignalIntensity(int paramInt)
  {
    int i = 4;
    try
    {
      int j = WifiManager.calculateSignalLevel(paramInt, 4);
      paramInt = j;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      paramInt = calculateSignalLevel(paramInt, 4);
    }
    switch (paramInt)
    {
    default: 
      return -1;
    case 2: 
      return 3;
    case 1: 
      return 2;
    case 0: 
      i = 1;
    }
    return i;
  }
  
  public static int getSsidCountWithMultiBssid(List<ScanResult> paramList)
  {
    int i = 0;
    if (paramList == null) {
      return 0;
    }
    int j = paramList.size();
    if (j == 0) {
      return 0;
    }
    HashSet localHashSet1 = new HashSet();
    HashSet localHashSet2 = new HashSet();
    while (i < j)
    {
      ScanResult localScanResult = (ScanResult)paramList.get(i);
      if (localScanResult != null) {
        if (localHashSet1.contains(localScanResult.SSID)) {
          localHashSet2.add(localScanResult.SSID);
        } else {
          localHashSet1.add(localScanResult.SSID);
        }
      }
      i += 1;
    }
    return localHashSet2.size();
  }
  
  public static WifiManager getWifiManager()
  {
    try
    {
      WifiManager localWifiManager = (WifiManager)ContextHolder.getAppContext().getSystemService("wifi");
      return localWifiManager;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return (WifiManager)ContextHolder.getAppContext().getSystemService("wifi");
  }
  
  public static boolean isConnected()
  {
    if (isWifiEnabled())
    {
      WifiInfo localWifiInfo = getConnectionInfo();
      if ((localWifiInfo != null) && (localWifiInfo.getSupplicantState() == SupplicantState.COMPLETED)) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isFreeWifi(WifiApInfo paramWifiApInfo)
  {
    return (isWeixinAndCanConnect(paramWifiApInfo)) || (isWiwide(paramWifiApInfo)) || (paramWifiApInfo.mHasCloudPassword) || (isRecommendOpenWiFi(paramWifiApInfo));
  }
  
  public static boolean isLocationProviderEnabled()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = true;
    boolean bool2 = true;
    if (i >= 23)
    {
      Object localObject = (LocationManager)ContextHolder.getAppContext().getSystemService("location");
      bool1 = bool2;
      try
      {
        bool2 = ((LocationManager)localObject).isProviderEnabled("gps");
        bool1 = bool2;
        StringBuilder localStringBuilder2 = new StringBuilder();
        bool1 = bool2;
        localStringBuilder2.append("[isLocationProviderEnabled] GPS_PROVIDER enabled: ");
        bool1 = bool2;
        localStringBuilder2.append(bool2);
        bool1 = bool2;
        LogUtils.d("WifiUtils", localStringBuilder2.toString());
        bool1 = bool2;
        if (!bool2)
        {
          bool1 = bool2;
          bool2 = ((LocationManager)localObject).isProviderEnabled("network");
          try
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("[isLocationProviderEnabled] NETWORK_PROVIDER enabled: ");
            ((StringBuilder)localObject).append(bool2);
            LogUtils.d("WifiUtils", ((StringBuilder)localObject).toString());
            bool1 = bool2;
          }
          catch (Exception localException1)
          {
            bool1 = bool2;
          }
          localException2.printStackTrace();
        }
      }
      catch (Exception localException2) {}
    }
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append("[isLocationProviderEnabled] enabled:");
    localStringBuilder1.append(bool1);
    LogUtils.d("WifiUtils", localStringBuilder1.toString());
    return bool1;
  }
  
  public static boolean isRecommendOpenWiFi(WifiApInfo paramWifiApInfo)
  {
    return (paramWifiApInfo != null) && (paramWifiApInfo.mSafeType == 0) && (((paramWifiApInfo.mActionType & 1L) > 0L) || ((paramWifiApInfo.mActionType & 0x30) == 16L));
  }
  
  public static boolean isSsidValid(String paramString)
  {
    return (!TextUtils.isEmpty(paramString)) && (!TextUtils.equals(paramString, "0x")) && (!TextUtils.equals(paramString, "<unknown ssid>"));
  }
  
  public static boolean isValidInfo(WifiApInfo paramWifiApInfo)
  {
    boolean bool = true;
    if ((paramWifiApInfo != null) && (!TextUtils.isEmpty(paramWifiApInfo.mSsid))) {}
    switch (paramWifiApInfo.mSafeType)
    {
    default: 
      break;
    case 3: 
      if ((!TextUtils.isEmpty(paramWifiApInfo.mPassword)) && (!TextUtils.isEmpty(paramWifiApInfo.mIdentity))) {
        return true;
      }
    case 2: 
      if ((!TextUtils.isEmpty(paramWifiApInfo.mPassword)) && (paramWifiApInfo.mPassword.length() >= 5) && (paramWifiApInfo.mPassword.length() <= 26)) {
        return true;
      }
    case 1: 
      if ((!TextUtils.isEmpty(paramWifiApInfo.mPassword)) && (paramWifiApInfo.mPassword.length() >= 8) && (paramWifiApInfo.mPassword.length() <= 63)) {
        return true;
      }
      bool = false;
    }
    return bool;
  }
  
  public static boolean isWeixinAndCanConnect(WifiApInfo paramWifiApInfo)
  {
    return false;
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
  
  public static boolean isWiwide(WifiApInfo paramWifiApInfo)
  {
    return (paramWifiApInfo != null) && (paramWifiApInfo.mSafeType == 0) && ((isWiwide(paramWifiApInfo.mBssid, paramWifiApInfo.mSafeType)) || ((paramWifiApInfo.mActionType & 0x2) > 0L));
  }
  
  public static boolean isWiwide(String paramString, int paramInt)
  {
    return (paramString != null) && (paramString.toLowerCase().startsWith("00:1f:7a")) && (paramInt == 0);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\WifiUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */