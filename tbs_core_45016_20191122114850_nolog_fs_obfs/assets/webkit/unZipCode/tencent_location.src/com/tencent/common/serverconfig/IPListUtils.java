package com.tencent.common.serverconfig;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.net.URL;

public class IPListUtils
{
  public static final String NET_WORK_SERVER_TYPE_PUSH = "1";
  public static final String NET_WORK_SERVER_TYPE_SOCKET = "2";
  public static final String NET_WORK_SERVER_TYPE_WUP = "0";
  
  public static String buildCurrNetworkInfo(Context paramContext, String paramString)
  {
    String str = paramString;
    if (TextUtils.isEmpty(paramString)) {
      str = "";
    }
    int i = getConnectType(paramContext);
    if (i == 1) {
      paramString = getWifiBSSID(paramContext);
    } else {
      paramString = getConnectExtraInfo(paramContext);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getConnectTypeName(paramContext));
    localStringBuilder.append(i);
    localStringBuilder.append(paramString);
    localStringBuilder.append(getMCC(paramContext));
    localStringBuilder.append(getMNC(paramContext));
    localStringBuilder.append(str);
    return localStringBuilder.toString();
  }
  
  public static NetworkInfo getActiveNetworkInfo(Context paramContext)
  {
    if (paramContext != null) {}
    try
    {
      paramContext = (ConnectivityManager)paramContext.getSystemService("connectivity");
    }
    catch (Throwable paramContext)
    {
      for (;;)
      {
        try
        {
          paramContext = paramContext.getActiveNetworkInfo();
          return paramContext;
        }
        catch (Throwable paramContext) {}
        paramContext = paramContext;
      }
    }
    paramContext = null;
    break label24;
    paramContext = null;
    label24:
    if (paramContext == null) {
      return null;
    }
    return null;
  }
  
  public static String getConnectExtraInfo(Context paramContext)
  {
    paramContext = getActiveNetworkInfo(paramContext);
    if (paramContext == null) {
      return "UNKNOW";
    }
    paramContext = paramContext.getExtraInfo();
    if (TextUtils.isEmpty(paramContext)) {
      return "UNKNOW";
    }
    paramContext = paramContext.toLowerCase();
    if (paramContext.contains("cmwap")) {
      return "cmwap";
    }
    if (paramContext.contains("uniwap")) {
      return "uniwap";
    }
    if (paramContext.contains("3gwap")) {
      return "3gwap";
    }
    if (paramContext.contains("ctwap")) {
      return "ctwap";
    }
    if (paramContext.contains("cmnet")) {
      return "cmnet";
    }
    if (paramContext.contains("uninet")) {
      return "uninet";
    }
    if (paramContext.contains("3gnet")) {
      return "3gnet";
    }
    if (paramContext.contains("ctnet")) {
      return "ctnet";
    }
    if (paramContext.contains("ctlte")) {
      return "ctlte";
    }
    return paramContext;
  }
  
  public static int getConnectType(Context paramContext)
  {
    paramContext = getActiveNetworkInfo(paramContext);
    if (paramContext == null) {
      return -1;
    }
    return paramContext.getType();
  }
  
  public static String getConnectTypeName(Context paramContext)
  {
    paramContext = getActiveNetworkInfo(paramContext);
    if (paramContext == null) {
      return "UNKNOW";
    }
    return paramContext.getTypeName();
  }
  
  public static String getMCC(Context paramContext)
  {
    paramContext = getTelephonyManager(paramContext);
    if (paramContext == null) {
      return "NULL";
    }
    try
    {
      paramContext = paramContext.getNetworkOperator();
      if (TextUtils.isEmpty(paramContext)) {
        return "NULL";
      }
      paramContext = paramContext.substring(0, 3);
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    return "NULL";
  }
  
  public static String getMNC(Context paramContext)
  {
    paramContext = getTelephonyManager(paramContext);
    if (paramContext == null) {
      return "NULL";
    }
    try
    {
      paramContext = paramContext.getNetworkOperator();
      if (TextUtils.isEmpty(paramContext)) {
        return "NULL";
      }
      paramContext = paramContext.substring(3);
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    return "NULL";
  }
  
  public static int getPortFromUrl(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return -1;
    }
    try
    {
      paramString = new URL(paramString);
      int j = paramString.getPort();
      int i = j;
      if (j < 0) {
        i = paramString.getDefaultPort();
      }
      return i;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
    }
    return -1;
  }
  
  public static TelephonyManager getTelephonyManager(Context paramContext)
  {
    if (paramContext != null) {
      try
      {
        paramContext = (TelephonyManager)paramContext.getSystemService("phone");
        return paramContext;
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return null;
  }
  
  public static String getWUPNetEnvironment(Context paramContext)
  {
    return buildCurrNetworkInfo(paramContext, "wup");
  }
  
  public static String getWifiBSSID(Context paramContext)
  {
    Object localObject = getWifiManager(paramContext);
    paramContext = null;
    if (localObject == null) {
      return null;
    }
    try
    {
      localObject = ((WifiManager)localObject).getConnectionInfo();
      paramContext = (Context)localObject;
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    if (paramContext == null) {
      return "UNKNOW";
    }
    return paramContext.getBSSID();
  }
  
  public static WifiManager getWifiManager(Context paramContext)
  {
    if (paramContext != null) {
      try
      {
        paramContext = (WifiManager)paramContext.getSystemService("wifi");
        return paramContext;
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return null;
  }
  
  public static boolean isIPV4(String paramString)
  {
    paramString = removeScheme(paramString);
    boolean bool = false;
    if (paramString != null)
    {
      if (paramString.indexOf(":") == paramString.lastIndexOf(':')) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  public static String removeScheme(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return paramString;
    }
    int i = paramString.indexOf("://");
    if (i >= 0) {
      return paramString.substring(i + 3);
    }
    return paramString;
  }
  
  public static String resolveValidIP(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      int i = paramString.indexOf("://");
      int j = paramString.indexOf('.');
      if ((i > 0) && (j > 0) && (i < j)) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        return paramString;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("http://");
      localStringBuilder.append(paramString);
      return localStringBuilder.toString();
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\serverconfig\IPListUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */