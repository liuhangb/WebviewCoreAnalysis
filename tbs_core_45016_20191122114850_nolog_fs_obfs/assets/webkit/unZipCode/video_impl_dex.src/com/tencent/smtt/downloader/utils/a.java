package com.tencent.smtt.downloader.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class a
{
  public static int a(Context paramContext)
  {
    paramContext = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    int j = 0;
    int i = j;
    if (paramContext != null)
    {
      i = j;
      if (paramContext.isConnectedOrConnecting())
      {
        switch (paramContext.getType())
        {
        default: 
          return 0;
        case 1: 
          return 3;
        }
        switch (paramContext.getSubtype())
        {
        default: 
          return 0;
        case 13: 
          return 4;
        case 3: 
        case 5: 
        case 6: 
        case 8: 
        case 9: 
        case 10: 
        case 12: 
        case 14: 
        case 15: 
          return 2;
        }
        i = 1;
      }
    }
    return i;
  }
  
  public static String a(Context paramContext)
  {
    String str = "unknown";
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      paramContext = str;
      if (localNetworkInfo != null)
      {
        paramContext = str;
        if (!localNetworkInfo.isConnectedOrConnecting()) {}
      }
      switch (localNetworkInfo.getType())
      {
      case 0: 
        paramContext = localNetworkInfo.getExtraInfo();
        return paramContext;
      }
    }
    catch (Exception paramContext)
    {
      return "unknown";
    }
    return "unknown";
    return "wifi";
  }
  
  public static boolean a(Context paramContext)
  {
    paramContext = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if (paramContext == null) {
      return false;
    }
    return (paramContext.isConnected()) || (paramContext.isAvailable());
  }
  
  public static String b(Context paramContext)
  {
    Object localObject = null;
    try
    {
      WifiInfo localWifiInfo = ((WifiManager)paramContext.getSystemService("wifi")).getConnectionInfo();
      paramContext = (Context)localObject;
      if (localWifiInfo != null) {
        paramContext = localWifiInfo.getBSSID();
      }
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return "";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\utils\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */