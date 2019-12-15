package com.tencent.mtt.base.utils;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.mtt.ContextHolder;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils
{
  public static final int NETWORK_CLASS_2_G = 1;
  public static final int NETWORK_CLASS_3_G = 2;
  public static final int NETWORK_CLASS_4_G = 3;
  public static final int NETWORK_CLASS_UNKNOWN = 0;
  public static final int NETWORK_MODULE = 1;
  public static final int NETWORK_TYPE_CHROMNET = 2;
  public static final int NETWORK_TYPE_DEFAULT = 0;
  public static final int NETWORK_TYPE_JAVANET = 1;
  public static final String NET_WORK_SERVER_TYPE_SOCKET = "2";
  public static final String NET_WORK_SERVER_TYPE_WUP = "0";
  public static final String NOIP = "0.0.0.0";
  public static final String NOMAC = "00:00:00:00:00:00";
  public static final String NOMASK = "255.255.255.255";
  public static final String OPENURL = "http://res.imtt.qq.com/comm_res/wifi.html";
  public static final String TAG = "NetworkUtils";
  private static String jdField_a_of_type_JavaLangString;
  private static HostnameVerifier jdField_a_of_type_JavaxNetSslHostnameVerifier = ;
  
  public static int IpToCidr(String paramString)
  {
    paramString = paramString.split("\\.");
    int j = paramString.length;
    double d = -2.0D;
    int i = 0;
    while (i < j)
    {
      d += 256.0D - Double.parseDouble(paramString[i]);
      i += 1;
    }
    return 32 - (int)(Math.log(d) / Math.log(2.0D));
  }
  
  private static String a(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      if (new File(paramString1).exists() == true)
      {
        paramString3 = Pattern.compile(paramString3);
        Runtime localRuntime = Runtime.getRuntime();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString1);
        localStringBuilder.append(paramString2);
        paramString1 = new BufferedReader(new InputStreamReader(localRuntime.exec(localStringBuilder.toString()).getInputStream()), 8192);
        do
        {
          paramString2 = paramString1.readLine();
          if (paramString2 == null) {
            break;
          }
          paramString2 = paramString3.matcher(paramString2);
        } while (!paramString2.matches());
        paramString1 = paramString2.group(1);
        return paramString1;
      }
      return null;
    }
    catch (Exception paramString1)
    {
      paramString2 = new StringBuilder();
      paramString2.append("Can't use native command: ");
      paramString2.append(paramString1.getMessage());
      Log.e("NetworkUtils", paramString2.toString());
    }
    return null;
  }
  
  private static String a(NetworkInterface paramNetworkInterface)
  {
    if (paramNetworkInterface != null)
    {
      paramNetworkInterface = paramNetworkInterface.getInetAddresses();
      while (paramNetworkInterface.hasMoreElements())
      {
        InetAddress localInetAddress = (InetAddress)paramNetworkInterface.nextElement();
        if ((!localInetAddress.isLoopbackAddress()) && (!(localInetAddress instanceof Inet6Address))) {
          return localInetAddress.getHostAddress();
        }
      }
    }
    return "0.0.0.0";
  }
  
  private static boolean a(ScanResult paramScanResult)
  {
    return (!paramScanResult.capabilities.contains("WEP")) && (!paramScanResult.capabilities.contains("PSK")) && (!paramScanResult.capabilities.contains("EAP"));
  }
  
  public static int getCidr(Context paramContext)
  {
    Object localObject = "255.255.255.255";
    try
    {
      WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
      paramContext = (Context)localObject;
      if (localWifiManager != null) {
        paramContext = getIpFromIntSigned(localWifiManager.getDhcpInfo().netmask);
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
      paramContext = (Context)localObject;
    }
    if (paramContext != "255.255.255.255") {
      return IpToCidr(paramContext);
    }
    try
    {
      paramContext = getIntf();
      localObject = a("/system/xbin/ip", String.format(" -f inet addr show %s", new Object[] { paramContext }), String.format("\\s*inet [0-9\\.]+\\/([0-9]+) brd [0-9\\.]+ scope global %s$", new Object[] { paramContext }));
      if (localObject != null) {
        return Integer.parseInt((String)localObject);
      }
      localObject = a("/system/xbin/ip", String.format(" -f inet addr show %s", new Object[] { paramContext }), String.format("\\s*inet [0-9\\.]+ peer [0-9\\.]+\\/([0-9]+) scope global %s$", new Object[] { paramContext }));
      if (localObject != null) {
        return Integer.parseInt((String)localObject);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(" ");
      ((StringBuilder)localObject).append(paramContext);
      paramContext = a("/system/bin/ifconfig", ((StringBuilder)localObject).toString(), String.format("^%s: ip [0-9\\.]+ mask ([0-9\\.]+) flags.*", new Object[] { paramContext }));
      if (paramContext != null)
      {
        int i = IpToCidr(paramContext);
        return i;
      }
    }
    catch (NumberFormatException paramContext)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramContext.getMessage());
      ((StringBuilder)localObject).append(" -> cannot find cidr, using default /24");
      ((StringBuilder)localObject).toString();
    }
    return 24;
  }
  
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
  
  public static String getGatewayMac(Context paramContext)
  {
    try
    {
      paramContext = (WifiManager)paramContext.getSystemService("wifi");
      if ((paramContext != null) && (paramContext.isWifiEnabled()))
      {
        paramContext = paramContext.getConnectionInfo();
        if (paramContext != null)
        {
          paramContext = paramContext.getBSSID();
          return paramContext;
        }
      }
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return "";
  }
  
  /* Error */
  public static String getHardwareAddress(String paramString)
  {
    // Byte code:
    //   0: ldc 30
    //   2: astore_2
    //   3: aconst_null
    //   4: astore 4
    //   6: aconst_null
    //   7: astore_3
    //   8: aconst_null
    //   9: astore 5
    //   11: aload_0
    //   12: ifnull +103 -> 115
    //   15: aload 4
    //   17: astore_1
    //   18: ldc_w 298
    //   21: iconst_1
    //   22: anewarray 4	java/lang/Object
    //   25: dup
    //   26: iconst_0
    //   27: aload_0
    //   28: ldc_w 300
    //   31: ldc 59
    //   33: invokevirtual 304	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   36: aastore
    //   37: invokestatic 249	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   40: invokestatic 101	java/util/regex/Pattern:compile	(Ljava/lang/String;)Ljava/util/regex/Pattern;
    //   43: astore 5
    //   45: aload 4
    //   47: astore_1
    //   48: new 116	java/io/BufferedReader
    //   51: dup
    //   52: new 306	java/io/FileReader
    //   55: dup
    //   56: ldc_w 308
    //   59: invokespecial 309	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   62: sipush 8192
    //   65: invokespecial 138	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   68: astore_0
    //   69: aload_0
    //   70: invokevirtual 141	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   73: astore_3
    //   74: aload_2
    //   75: astore_1
    //   76: aload_3
    //   77: ifnull +23 -> 100
    //   80: aload 5
    //   82: aload_3
    //   83: invokevirtual 145	java/util/regex/Pattern:matcher	(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   86: astore_1
    //   87: aload_1
    //   88: invokevirtual 150	java/util/regex/Matcher:matches	()Z
    //   91: ifeq -22 -> 69
    //   94: aload_1
    //   95: iconst_1
    //   96: invokevirtual 154	java/util/regex/Matcher:group	(I)Ljava/lang/String;
    //   99: astore_1
    //   100: goto +32 -> 132
    //   103: astore_2
    //   104: aload_0
    //   105: astore_1
    //   106: aload_2
    //   107: astore_0
    //   108: goto +122 -> 230
    //   111: astore_2
    //   112: goto +50 -> 162
    //   115: aload 4
    //   117: astore_1
    //   118: ldc 39
    //   120: ldc_w 311
    //   123: invokestatic 165	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   126: pop
    //   127: aload 5
    //   129: astore_0
    //   130: aload_2
    //   131: astore_1
    //   132: aload_0
    //   133: ifnull +20 -> 153
    //   136: aload_0
    //   137: invokevirtual 314	java/io/BufferedReader:close	()V
    //   140: aload_1
    //   141: areturn
    //   142: astore_0
    //   143: ldc 39
    //   145: aload_0
    //   146: invokevirtual 315	java/io/IOException:getMessage	()Ljava/lang/String;
    //   149: invokestatic 165	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   152: pop
    //   153: aload_1
    //   154: areturn
    //   155: astore_0
    //   156: goto +74 -> 230
    //   159: astore_2
    //   160: aload_3
    //   161: astore_0
    //   162: aload_0
    //   163: astore_1
    //   164: new 109	java/lang/StringBuilder
    //   167: dup
    //   168: invokespecial 110	java/lang/StringBuilder:<init>	()V
    //   171: astore_3
    //   172: aload_0
    //   173: astore_1
    //   174: aload_3
    //   175: ldc_w 317
    //   178: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   181: pop
    //   182: aload_0
    //   183: astore_1
    //   184: aload_3
    //   185: aload_2
    //   186: invokevirtual 315	java/io/IOException:getMessage	()Ljava/lang/String;
    //   189: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: pop
    //   193: aload_0
    //   194: astore_1
    //   195: ldc 39
    //   197: aload_3
    //   198: invokevirtual 122	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   201: invokestatic 165	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   204: pop
    //   205: aload_0
    //   206: ifnull +21 -> 227
    //   209: aload_0
    //   210: invokevirtual 314	java/io/BufferedReader:close	()V
    //   213: ldc 30
    //   215: areturn
    //   216: astore_0
    //   217: ldc 39
    //   219: aload_0
    //   220: invokevirtual 315	java/io/IOException:getMessage	()Ljava/lang/String;
    //   223: invokestatic 165	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   226: pop
    //   227: ldc 30
    //   229: areturn
    //   230: aload_1
    //   231: ifnull +21 -> 252
    //   234: aload_1
    //   235: invokevirtual 314	java/io/BufferedReader:close	()V
    //   238: goto +14 -> 252
    //   241: astore_1
    //   242: ldc 39
    //   244: aload_1
    //   245: invokevirtual 315	java/io/IOException:getMessage	()Ljava/lang/String;
    //   248: invokestatic 165	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   251: pop
    //   252: aload_0
    //   253: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	254	0	paramString	String
    //   17	218	1	localObject1	Object
    //   241	4	1	localIOException1	java.io.IOException
    //   2	73	2	str	String
    //   103	4	2	localObject2	Object
    //   111	20	2	localIOException2	java.io.IOException
    //   159	27	2	localIOException3	java.io.IOException
    //   7	191	3	localObject3	Object
    //   4	112	4	localObject4	Object
    //   9	119	5	localPattern	Pattern
    // Exception table:
    //   from	to	target	type
    //   69	74	103	finally
    //   80	100	103	finally
    //   69	74	111	java/io/IOException
    //   80	100	111	java/io/IOException
    //   136	140	142	java/io/IOException
    //   18	45	155	finally
    //   48	69	155	finally
    //   118	127	155	finally
    //   164	172	155	finally
    //   174	182	155	finally
    //   184	193	155	finally
    //   195	205	155	finally
    //   18	45	159	java/io/IOException
    //   48	69	159	java/io/IOException
    //   118	127	159	java/io/IOException
    //   209	213	216	java/io/IOException
    //   234	238	241	java/io/IOException
  }
  
  public static String getIMEI()
  {
    if (jdField_a_of_type_JavaLangString == null) {}
    try
    {
      Object localObject = getTelephonyManager(ContextHolder.getAppContext());
      if (localObject == null) {
        return "";
      }
      localObject = ((TelephonyManager)localObject).getDeviceId();
      if (localObject == null) {
        return "";
      }
      jdField_a_of_type_JavaLangString = (String)localObject;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return "";
    return jdField_a_of_type_JavaLangString;
  }
  
  public static String getIMSI()
  {
    try
    {
      Object localObject = getTelephonyManager(ContextHolder.getAppContext());
      if (localObject == null) {
        return "";
      }
      localObject = ((TelephonyManager)localObject).getSubscriberId();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return "";
  }
  
  public static String getIntf()
  {
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3;
    try
    {
      Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();
      Object localObject4;
      do
      {
        localObject1 = localObject2;
        localObject3 = localObject2;
        if (!localEnumeration.hasMoreElements()) {
          break;
        }
        localObject1 = localObject2;
        localObject4 = (NetworkInterface)localEnumeration.nextElement();
        localObject1 = localObject2;
        localObject3 = ((NetworkInterface)localObject4).getName();
        localObject1 = localObject3;
        localObject4 = a((NetworkInterface)localObject4);
        localObject2 = localObject3;
      } while (localObject4 == "0.0.0.0");
      return (String)localObject3;
    }
    catch (SocketException localSocketException)
    {
      Log.e("NetworkUtils", localSocketException.getMessage());
      localObject3 = localObject1;
    }
    return (String)localObject3;
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
  
  public static String getIpFromIntSigned(int paramInt)
  {
    String str = "";
    int i = 0;
    while (i < 4)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(str);
      localStringBuilder.append(paramInt >> i * 8 & 0xFF);
      localStringBuilder.append(".");
      str = localStringBuilder.toString();
      i += 1;
    }
    return str.substring(0, str.length() - 1);
  }
  
  public static String getIpFromLongUnsigned(long paramLong)
  {
    String str = "";
    int i = 3;
    while (i > -1)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(str);
      localStringBuilder.append(paramLong >> i * 8 & 0xFF);
      localStringBuilder.append(".");
      str = localStringBuilder.toString();
      i -= 1;
    }
    return str.substring(0, str.length() - 1);
  }
  
  public static int getNetworkClass()
  {
    switch ()
    {
    default: 
      return 0;
    case 13: 
      return 3;
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
    return 1;
  }
  
  public static String getNetworkType()
  {
    String str = "UNKNOWN";
    int i = getNetworkTypeInt();
    if (i == 1) {
      return "GPRS";
    }
    if (i == 2) {
      return "EDGE";
    }
    if (i == 3) {
      return "UMTS";
    }
    if (i == 8) {
      return "HSDPA";
    }
    if (i == 9) {
      return "HSUPA";
    }
    if (i == 10) {
      return "HSPA";
    }
    if (i == 4) {
      return "CDMA";
    }
    if (i == 5) {
      return "CDMAEVDO0";
    }
    if (i == 6) {
      return "CDMAEVDOA";
    }
    if (i == 7) {
      return "CDMA1xRTT";
    }
    if (i == 11) {
      return "IDEN";
    }
    if (i == 12) {
      return "CDMAEVDOB";
    }
    if (i == 13) {
      return "LTE";
    }
    if (i == 14) {
      return "EHRPD";
    }
    if (i == 15) {
      str = "HSPAP";
    }
    return str;
  }
  
  public static int getNetworkTypeInt()
  {
    TelephonyManager localTelephonyManager = getTelephonyManager(ContextHolder.getAppContext());
    if (localTelephonyManager != null) {
      return localTelephonyManager.getNetworkType();
    }
    return 0;
  }
  
  public static String getPhoneNumber()
  {
    try
    {
      Object localObject = getTelephonyManager(ContextHolder.getAppContext());
      if (localObject == null) {
        return "";
      }
      localObject = ((TelephonyManager)localObject).getLine1Number();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return "";
  }
  
  public static String getSimOperator()
  {
    TelephonyManager localTelephonyManager = getTelephonyManager(ContextHolder.getAppContext());
    if (localTelephonyManager == null) {
      return "UNKNOW";
    }
    return localTelephonyManager.getSimOperator();
  }
  
  public static TelephonyManager getTelephonyManager(Context paramContext)
  {
    if (paramContext != null) {
      try
      {
        paramContext = (TelephonyManager)paramContext.getSystemService("phone");
        return paramContext;
      }
      catch (Error paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return null;
  }
  
  public static long getUnsignedLongFromIp(String paramString)
  {
    paramString = paramString.split("\\.");
    return Integer.parseInt(paramString[0]) * 16777216 + Integer.parseInt(paramString[1]) * 65536 + Integer.parseInt(paramString[2]) * 256 + Integer.parseInt(paramString[3]);
  }
  
  public static ArrayList<ScanResult> getWifiList(Context paramContext)
  {
    try
    {
      paramContext = (WifiManager)paramContext.getSystemService("wifi");
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
      paramContext = null;
    }
    if (paramContext != null) {
      return (ArrayList)paramContext.getScanResults();
    }
    return null;
  }
  
  public static boolean isOpenedWifiCheck(Context paramContext)
  {
    Object localObject = (WifiManager)paramContext.getSystemService("wifi");
    for (;;)
    {
      try
      {
        paramContext = ((WifiManager)localObject).getConnectionInfo();
        if (paramContext != null) {
          if (TextUtils.isEmpty(paramContext.getBSSID())) {
            return false;
          }
        }
      }
      catch (Exception paramContext)
      {
        return false;
      }
      try
      {
        localObject = ((WifiManager)localObject).getScanResults();
        if (localObject == null) {
          return false;
        }
        localObject = ((List)localObject).iterator();
        if (((Iterator)localObject).hasNext())
        {
          ScanResult localScanResult = (ScanResult)((Iterator)localObject).next();
          if ((localScanResult != null) && (localScanResult.BSSID.equals(paramContext.getBSSID()))) {
            return a(localScanResult);
          }
        }
        else
        {
          return false;
        }
      }
      catch (Exception paramContext) {}
    }
    return false;
    return false;
  }
  
  public static void restoreHttpsHostNameVerifier()
  {
    HttpsURLConnection.setDefaultHostnameVerifier(jdField_a_of_type_JavaxNetSslHostnameVerifier);
  }
  
  public static void showWifiLoginNotify(Context paramContext, int paramInt) {}
  
  public static String tryDecodeSSID(Context paramContext, String paramString)
  {
    paramContext = paramString;
    if (paramString.length() > 25) {
      paramContext = paramString.substring(0, 25);
    }
    return paramContext;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\NetworkUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */