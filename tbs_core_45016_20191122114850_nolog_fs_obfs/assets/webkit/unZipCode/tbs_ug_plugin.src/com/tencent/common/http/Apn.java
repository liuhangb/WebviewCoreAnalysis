package com.tencent.common.http;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.connectivity.ConnectivityAdapterHolder;
import com.tencent.mtt.ContextHolder;
import java.lang.reflect.Method;

public class Apn
{
  public static final String APN_3GNET = "3gnet";
  public static final String APN_3GWAP = "3gwap";
  public static final String APN_777 = "#777";
  public static final String APN_CMLTE = "cmnet";
  public static final String APN_CMNET = "cmnet";
  public static final String APN_CMWAP = "cmwap";
  public static final String APN_CTLTE = "ctlte";
  public static final String APN_CTNET = "ctnet";
  public static final String APN_CTWAP = "ctwap";
  public static final String APN_NET = "Net";
  public static final String APN_UNINET = "uninet";
  public static final String APN_UNIWAP = "uniwap";
  public static final String APN_UNKNOWN = "N/A";
  public static final String APN_WAP = "Wap";
  public static final String APN_WIFI = "Wlan";
  public static final String APN_WONET = "wonet";
  public static final int NETWORK_TYPE_1xRTT = 7;
  public static final int NETWORK_TYPE_CDMA = 4;
  public static final int NETWORK_TYPE_EDGE = 2;
  public static final int NETWORK_TYPE_EHRPD = 14;
  public static final int NETWORK_TYPE_EVDO_0 = 5;
  public static final int NETWORK_TYPE_EVDO_A = 6;
  public static final int NETWORK_TYPE_EVDO_B = 12;
  public static final int NETWORK_TYPE_GPRS = 1;
  public static final int NETWORK_TYPE_HSDPA = 8;
  public static final int NETWORK_TYPE_HSPA = 10;
  public static final int NETWORK_TYPE_HSPAP = 15;
  public static final int NETWORK_TYPE_HSUPA = 9;
  public static final int NETWORK_TYPE_IDEN = 11;
  public static final int NETWORK_TYPE_LTE = 13;
  public static final int NETWORK_TYPE_UMTS = 3;
  public static final int NETWORK_TYPE_UNKNOWN = 0;
  public static final byte PROXY_TYPE_CM = 0;
  public static final byte PROXY_TYPE_CT = 1;
  public static final String TAG = "Apn";
  public static final int TYPE_NET = 1;
  public static final int TYPE_UNKNOWN = 0;
  public static final int TYPE_WAP = 2;
  public static final int TYPE_WIFI = 4;
  public static final int T_APN_3GNET = 512;
  public static final int T_APN_3GWAP = 16;
  public static final int T_APN_CMLTE = 8192;
  public static final int T_APN_CMNET = 1024;
  public static final int T_APN_CMWAP = 8;
  public static final int T_APN_CTLTE = 2048;
  public static final int T_APN_CTNET = 128;
  public static final int T_APN_CTWAP = 64;
  public static final int T_APN_UNINET = 256;
  public static final int T_APN_UNIWAP = 32;
  public static final int T_APN_WONET = 4096;
  private static Context jdField_a_of_type_AndroidContentContext;
  private static ConnectivityManager jdField_a_of_type_AndroidNetConnectivityManager;
  static NetworkInfo jdField_a_of_type_AndroidNetNetworkInfo = null;
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static ApnInfo jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo;
  private static ApnProxyInfo jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo = new ApnProxyInfo();
  private static ConnectionChangeHandler jdField_a_of_type_ComTencentCommonHttpConnectionChangeHandler;
  private static Object jdField_a_of_type_JavaLangObject;
  static Runnable jdField_a_of_type_JavaLangRunnable = null;
  static String jdField_a_of_type_JavaLangString = "Wlan";
  private static volatile boolean jdField_a_of_type_Boolean = false;
  private static String jdField_b_of_type_JavaLangString = "N/A";
  private static boolean jdField_b_of_type_Boolean = false;
  public static int sApnType = 4;
  public static int sApnTypeS = 4;
  public static String sCurrentDnsServer = "get_dns_failed";
  
  static
  {
    jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo = new ApnInfo();
    jdField_a_of_type_AndroidContentContext = null;
    jdField_a_of_type_AndroidNetConnectivityManager = null;
    jdField_a_of_type_JavaLangObject = new Object();
    jdField_a_of_type_Boolean = false;
  }
  
  private static void a(int paramInt1, int paramInt2)
  {
    jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo.reset();
    ApnInfo.a(jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo, paramInt1);
    ApnInfo.b(jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo, paramInt2);
    ApnInfo.c(jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo, sApnType);
    ApnInfo.d(jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo, sApnTypeS);
    ApnInfo.a(jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo, jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo);
    ApnInfo.a(jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo, sCurrentDnsServer);
    ApnInfo localApnInfo = jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo;
    boolean bool2 = false;
    boolean bool1;
    if (paramInt1 == 1) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    ApnInfo.a(localApnInfo, bool1);
    localApnInfo = jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo;
    if (paramInt1 == 0) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    ApnInfo.b(localApnInfo, bool1);
    if (ApnInfo.a(jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo))
    {
      if ((paramInt2 == 1) || (paramInt2 == 2) || (paramInt2 == 4) || (paramInt2 == 7) || (paramInt2 == 11)) {
        ApnInfo.e(jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo, 1);
      }
      if ((paramInt2 != 1) && (paramInt2 != 2) && (paramInt2 != 4) && (paramInt2 != 7) && (paramInt2 != 11) && (paramInt2 != 13)) {
        ApnInfo.e(jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo, 2);
      }
      if (paramInt2 == 13) {
        ApnInfo.e(jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo, 3);
      }
      localApnInfo = jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo;
      bool1 = bool2;
      if (ApnInfo.a(localApnInfo) == 2) {
        bool1 = true;
      }
      ApnInfo.c(localApnInfo, bool1);
    }
  }
  
  /* Error */
  private static void a(NetworkInfo paramNetworkInfo, boolean paramBoolean)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: iconst_1
    //   4: putstatic 165	com/tencent/common/http/Apn:jdField_a_of_type_Boolean	Z
    //   7: invokestatic 229	com/tencent/common/http/Apn:d	()V
    //   10: iconst_0
    //   11: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   14: iconst_0
    //   15: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   18: ldc 56
    //   20: putstatic 231	com/tencent/common/http/Apn:jdField_b_of_type_JavaLangString	Ljava/lang/String;
    //   23: iconst_m1
    //   24: istore_3
    //   25: aload_0
    //   26: ifnull +88 -> 114
    //   29: aload_0
    //   30: invokevirtual 237	android/net/NetworkInfo:getType	()I
    //   33: istore_2
    //   34: aload_0
    //   35: invokevirtual 240	android/net/NetworkInfo:getSubtype	()I
    //   38: istore_3
    //   39: aload_0
    //   40: invokevirtual 244	android/net/NetworkInfo:getExtraInfo	()Ljava/lang/String;
    //   43: astore 6
    //   45: aload 6
    //   47: ifnonnull +22 -> 69
    //   50: iconst_0
    //   51: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   54: iconst_0
    //   55: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   58: iload_2
    //   59: istore 4
    //   61: iload_3
    //   62: istore_2
    //   63: iload 4
    //   65: istore_3
    //   66: goto +53 -> 119
    //   69: aload 6
    //   71: invokevirtual 249	java/lang/String:trim	()Ljava/lang/String;
    //   74: invokevirtual 252	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   77: astore 6
    //   79: iload_2
    //   80: istore 4
    //   82: iload_3
    //   83: istore_2
    //   84: iload 4
    //   86: istore_3
    //   87: goto +32 -> 119
    //   90: astore_0
    //   91: iload_2
    //   92: istore 4
    //   94: iload_3
    //   95: istore_2
    //   96: iload 4
    //   98: istore_3
    //   99: goto +710 -> 809
    //   102: astore_0
    //   103: iload_2
    //   104: istore_3
    //   105: goto +4 -> 109
    //   108: astore_0
    //   109: iconst_m1
    //   110: istore_2
    //   111: goto +698 -> 809
    //   114: aconst_null
    //   115: astore 6
    //   117: iconst_m1
    //   118: istore_2
    //   119: iload_3
    //   120: iconst_1
    //   121: if_icmpne +40 -> 161
    //   124: iconst_4
    //   125: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   128: iconst_4
    //   129: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   132: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   135: iconst_0
    //   136: putfield 255	com/tencent/common/http/Apn$ApnProxyInfo:apnUseProxy	Z
    //   139: iload_2
    //   140: istore 5
    //   142: iload_3
    //   143: istore 4
    //   145: iload_1
    //   146: ifne +673 -> 819
    //   149: invokestatic 257	com/tencent/common/http/Apn:e	()V
    //   152: iload_2
    //   153: istore 5
    //   155: iload_3
    //   156: istore 4
    //   158: goto +661 -> 819
    //   161: aload 6
    //   163: ifnonnull +14 -> 177
    //   166: iconst_0
    //   167: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   170: iconst_0
    //   171: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   174: goto +335 -> 509
    //   177: aload 6
    //   179: ldc 41
    //   181: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   184: ifeq +22 -> 206
    //   187: iload_2
    //   188: bipush 6
    //   190: if_icmpne +16 -> 206
    //   193: iconst_1
    //   194: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   197: sipush 128
    //   200: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   203: goto +306 -> 509
    //   206: aload 6
    //   208: ldc 41
    //   210: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   213: ifne +13 -> 226
    //   216: aload 6
    //   218: ldc 38
    //   220: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   223: ifeq +22 -> 245
    //   226: iload_2
    //   227: bipush 13
    //   229: if_icmplt +16 -> 245
    //   232: iconst_1
    //   233: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   236: sipush 2048
    //   239: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   242: goto +267 -> 509
    //   245: aload 6
    //   247: ldc 31
    //   249: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   252: ifne +13 -> 265
    //   255: aload 6
    //   257: ldc 35
    //   259: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   262: ifeq +22 -> 284
    //   265: iload_2
    //   266: bipush 13
    //   268: if_icmpne +16 -> 284
    //   271: iconst_1
    //   272: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   275: sipush 8192
    //   278: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   281: goto +228 -> 509
    //   284: aload 6
    //   286: ldc 65
    //   288: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   291: ifne +13 -> 304
    //   294: aload 6
    //   296: ldc 22
    //   298: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   301: ifeq +22 -> 323
    //   304: iload_2
    //   305: bipush 13
    //   307: if_icmplt +16 -> 323
    //   310: iconst_1
    //   311: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   314: sipush 4096
    //   317: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   320: goto +189 -> 509
    //   323: aload 6
    //   325: ldc 35
    //   327: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   330: ifeq +15 -> 345
    //   333: iconst_2
    //   334: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   337: bipush 8
    //   339: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   342: goto +167 -> 509
    //   345: aload 6
    //   347: ldc 53
    //   349: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   352: ifeq +15 -> 367
    //   355: iconst_2
    //   356: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   359: bipush 32
    //   361: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   364: goto +145 -> 509
    //   367: aload 6
    //   369: ldc 25
    //   371: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   374: ifeq +15 -> 389
    //   377: iconst_2
    //   378: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   381: bipush 16
    //   383: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   386: goto +123 -> 509
    //   389: aload 6
    //   391: ldc 44
    //   393: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   396: ifeq +15 -> 411
    //   399: iconst_2
    //   400: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   403: bipush 64
    //   405: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   408: goto +101 -> 509
    //   411: aload 6
    //   413: ldc 31
    //   415: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   418: ifeq +16 -> 434
    //   421: iconst_1
    //   422: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   425: sipush 1024
    //   428: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   431: goto +78 -> 509
    //   434: aload 6
    //   436: ldc 50
    //   438: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   441: ifeq +16 -> 457
    //   444: iconst_1
    //   445: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   448: sipush 256
    //   451: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   454: goto +55 -> 509
    //   457: aload 6
    //   459: ldc 22
    //   461: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   464: ifeq +16 -> 480
    //   467: iconst_1
    //   468: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   471: sipush 512
    //   474: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   477: goto +32 -> 509
    //   480: aload 6
    //   482: ldc 28
    //   484: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   487: ifeq +14 -> 501
    //   490: iconst_0
    //   491: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   494: iconst_0
    //   495: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   498: goto +11 -> 509
    //   501: iconst_0
    //   502: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   505: iconst_0
    //   506: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   509: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   512: iconst_0
    //   513: putfield 255	com/tencent/common/http/Apn$ApnProxyInfo:apnUseProxy	Z
    //   516: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   519: invokestatic 266	android/net/Proxy:getDefaultHost	()Ljava/lang/String;
    //   522: putfield 269	com/tencent/common/http/Apn$ApnProxyInfo:apnProxy	Ljava/lang/String;
    //   525: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   528: invokestatic 272	android/net/Proxy:getDefaultPort	()I
    //   531: putfield 275	com/tencent/common/http/Apn$ApnProxyInfo:apnPort	I
    //   534: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   537: getfield 269	com/tencent/common/http/Apn$ApnProxyInfo:apnProxy	Ljava/lang/String;
    //   540: ifnull +18 -> 558
    //   543: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   546: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   549: getfield 269	com/tencent/common/http/Apn$ApnProxyInfo:apnProxy	Ljava/lang/String;
    //   552: invokevirtual 249	java/lang/String:trim	()Ljava/lang/String;
    //   555: putfield 269	com/tencent/common/http/Apn$ApnProxyInfo:apnProxy	Ljava/lang/String;
    //   558: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   561: getfield 269	com/tencent/common/http/Apn$ApnProxyInfo:apnProxy	Ljava/lang/String;
    //   564: invokestatic 280	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   567: ifne +54 -> 621
    //   570: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   573: iconst_1
    //   574: putfield 255	com/tencent/common/http/Apn$ApnProxyInfo:apnUseProxy	Z
    //   577: iconst_2
    //   578: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   581: ldc_w 282
    //   584: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   587: getfield 269	com/tencent/common/http/Apn$ApnProxyInfo:apnProxy	Ljava/lang/String;
    //   590: invokevirtual 286	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   593: ifeq +18 -> 611
    //   596: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   599: iconst_1
    //   600: putfield 289	com/tencent/common/http/Apn$ApnProxyInfo:apnProxyType	B
    //   603: bipush 64
    //   605: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   608: goto +45 -> 653
    //   611: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   614: iconst_0
    //   615: putfield 289	com/tencent/common/http/Apn$ApnProxyInfo:apnProxyType	B
    //   618: goto +35 -> 653
    //   621: getstatic 153	com/tencent/common/http/Apn:jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo	Lcom/tencent/common/http/Apn$ApnProxyInfo;
    //   624: iconst_0
    //   625: putfield 255	com/tencent/common/http/Apn$ApnProxyInfo:apnUseProxy	Z
    //   628: iconst_1
    //   629: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   632: aload 6
    //   634: ifnull +19 -> 653
    //   637: aload 6
    //   639: ldc 28
    //   641: invokevirtual 261	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   644: ifeq +9 -> 653
    //   647: sipush 128
    //   650: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   653: getstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   656: ifeq +188 -> 844
    //   659: getstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   662: ifne +97 -> 759
    //   665: goto +179 -> 844
    //   668: aload_0
    //   669: ifnull +90 -> 759
    //   672: goto +42 -> 714
    //   675: iconst_1
    //   676: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   679: sipush 4096
    //   682: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   685: goto +74 -> 759
    //   688: iconst_1
    //   689: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   692: sipush 2048
    //   695: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   698: goto +61 -> 759
    //   701: iconst_1
    //   702: putstatic 192	com/tencent/common/http/Apn:sApnType	I
    //   705: sipush 8192
    //   708: putstatic 197	com/tencent/common/http/Apn:sApnTypeS	I
    //   711: goto +48 -> 759
    //   714: new 291	java/lang/StringBuilder
    //   717: dup
    //   718: invokespecial 292	java/lang/StringBuilder:<init>	()V
    //   721: astore 6
    //   723: aload 6
    //   725: getstatic 231	com/tencent/common/http/Apn:jdField_b_of_type_JavaLangString	Ljava/lang/String;
    //   728: invokevirtual 296	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   731: pop
    //   732: aload 6
    //   734: ldc_w 298
    //   737: invokevirtual 296	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   740: pop
    //   741: aload 6
    //   743: aload_0
    //   744: invokevirtual 244	android/net/NetworkInfo:getExtraInfo	()Ljava/lang/String;
    //   747: invokevirtual 296	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   750: pop
    //   751: aload 6
    //   753: invokevirtual 301	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   756: putstatic 231	com/tencent/common/http/Apn:jdField_b_of_type_JavaLangString	Ljava/lang/String;
    //   759: iload_2
    //   760: istore 5
    //   762: iload_3
    //   763: istore 4
    //   765: iload_1
    //   766: ifne +53 -> 819
    //   769: invokestatic 306	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   772: ldc_w 308
    //   775: invokevirtual 314	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   778: checkcast 316	android/telephony/TelephonyManager
    //   781: astore_0
    //   782: aload_0
    //   783: ifnonnull +8 -> 791
    //   786: iconst_0
    //   787: istore_1
    //   788: goto +8 -> 796
    //   791: aload_0
    //   792: invokevirtual 320	android/telephony/TelephonyManager:isNetworkRoaming	()Z
    //   795: istore_1
    //   796: iload_1
    //   797: putstatic 171	com/tencent/common/http/Apn:jdField_b_of_type_Boolean	Z
    //   800: iload_2
    //   801: istore 5
    //   803: iload_3
    //   804: istore 4
    //   806: goto +13 -> 819
    //   809: aload_0
    //   810: invokevirtual 323	java/lang/Exception:printStackTrace	()V
    //   813: iload_3
    //   814: istore 4
    //   816: iload_2
    //   817: istore 5
    //   819: iload 4
    //   821: iload 5
    //   823: invokestatic 325	com/tencent/common/http/Apn:a	(II)V
    //   826: iconst_0
    //   827: putstatic 165	com/tencent/common/http/Apn:jdField_a_of_type_Boolean	Z
    //   830: ldc 2
    //   832: monitorexit
    //   833: return
    //   834: astore_0
    //   835: ldc 2
    //   837: monitorexit
    //   838: aload_0
    //   839: athrow
    //   840: astore_0
    //   841: goto -32 -> 809
    //   844: iload_2
    //   845: tableswitch	default:+27->872, 13:+-144->701, 14:+-157->688, 15:+-170->675
    //   872: goto -204 -> 668
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	875	0	paramNetworkInfo	NetworkInfo
    //   0	875	1	paramBoolean	boolean
    //   33	812	2	i	int
    //   24	790	3	j	int
    //   59	761	4	k	int
    //   140	682	5	m	int
    //   43	709	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   39	45	90	java/lang/Exception
    //   50	58	90	java/lang/Exception
    //   69	79	90	java/lang/Exception
    //   34	39	102	java/lang/Exception
    //   29	34	108	java/lang/Exception
    //   3	23	834	finally
    //   29	34	834	finally
    //   34	39	834	finally
    //   39	45	834	finally
    //   50	58	834	finally
    //   69	79	834	finally
    //   124	139	834	finally
    //   149	152	834	finally
    //   166	174	834	finally
    //   177	187	834	finally
    //   193	203	834	finally
    //   206	226	834	finally
    //   232	242	834	finally
    //   245	265	834	finally
    //   271	281	834	finally
    //   284	304	834	finally
    //   310	320	834	finally
    //   323	342	834	finally
    //   345	364	834	finally
    //   367	386	834	finally
    //   389	408	834	finally
    //   411	431	834	finally
    //   434	454	834	finally
    //   457	477	834	finally
    //   480	498	834	finally
    //   501	509	834	finally
    //   509	558	834	finally
    //   558	608	834	finally
    //   611	618	834	finally
    //   621	632	834	finally
    //   637	653	834	finally
    //   653	665	834	finally
    //   675	685	834	finally
    //   688	698	834	finally
    //   701	711	834	finally
    //   714	759	834	finally
    //   769	782	834	finally
    //   791	796	834	finally
    //   796	800	834	finally
    //   809	813	834	finally
    //   819	830	834	finally
    //   124	139	840	java/lang/Exception
    //   149	152	840	java/lang/Exception
    //   166	174	840	java/lang/Exception
    //   177	187	840	java/lang/Exception
    //   193	203	840	java/lang/Exception
    //   206	226	840	java/lang/Exception
    //   232	242	840	java/lang/Exception
    //   245	265	840	java/lang/Exception
    //   271	281	840	java/lang/Exception
    //   284	304	840	java/lang/Exception
    //   310	320	840	java/lang/Exception
    //   323	342	840	java/lang/Exception
    //   345	364	840	java/lang/Exception
    //   367	386	840	java/lang/Exception
    //   389	408	840	java/lang/Exception
    //   411	431	840	java/lang/Exception
    //   434	454	840	java/lang/Exception
    //   457	477	840	java/lang/Exception
    //   480	498	840	java/lang/Exception
    //   501	509	840	java/lang/Exception
    //   509	558	840	java/lang/Exception
    //   558	608	840	java/lang/Exception
    //   611	618	840	java/lang/Exception
    //   621	632	840	java/lang/Exception
    //   637	653	840	java/lang/Exception
    //   653	665	840	java/lang/Exception
    //   675	685	840	java/lang/Exception
    //   688	698	840	java/lang/Exception
    //   701	711	840	java/lang/Exception
    //   714	759	840	java/lang/Exception
    //   769	782	840	java/lang/Exception
    //   791	796	840	java/lang/Exception
    //   796	800	840	java/lang/Exception
  }
  
  private static Context b()
  {
    Context localContext = jdField_a_of_type_AndroidContentContext;
    if (localContext != null) {
      return localContext;
    }
    jdField_a_of_type_AndroidContentContext = ContextHolder.getAppContext();
    return jdField_a_of_type_AndroidContentContext;
  }
  
  private static void b()
  {
    ConnectivityManager localConnectivityManager = getConnectivityManager();
    localStringBuilder2 = null;
    Object localObject1 = localStringBuilder2;
    if (localConnectivityManager != null) {}
    try
    {
      localObject1 = localConnectivityManager.getActiveNetworkInfo();
    }
    catch (Exception localException)
    {
      for (;;)
      {
        StringBuilder localStringBuilder1 = localStringBuilder2;
      }
    }
    a((NetworkInfo)localObject1, false);
    jdField_a_of_type_AndroidNetNetworkInfo = (NetworkInfo)localObject1;
    localObject1 = (WifiManager)jdField_a_of_type_AndroidContentContext.getSystemService("wifi");
    if (localObject1 != null) {
      try
      {
        localObject1 = ((WifiManager)localObject1).getConnectionInfo();
        if (localObject1 != null) {
          try
          {
            localStringBuilder2 = new StringBuilder();
            localStringBuilder2.append("Wlan");
            localStringBuilder2.append(((WifiInfo)localObject1).getBSSID());
            jdField_a_of_type_JavaLangString = localStringBuilder2.toString();
            localStringBuilder2 = new StringBuilder();
            localStringBuilder2.append("Apn--wifiInfo.getBSSID():");
            localStringBuilder2.append(((WifiInfo)localObject1).getBSSID());
            FLogger.d("ip-list", localStringBuilder2.toString());
            return;
          }
          finally {}
        }
        return;
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
  }
  
  private static void c()
  {
    a(getActiveNetworkInfo(false), false);
  }
  
  private static void d()
  {
    try
    {
      Method localMethod = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[] { String.class });
      localMethod.setAccessible(true);
      sCurrentDnsServer = (String)localMethod.invoke(null, new Object[] { "net.dns1" });
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      sCurrentDnsServer = "get_dns_failed";
    }
  }
  
  private static void e()
  {
    if (jdField_a_of_type_AndroidOsHandler == null) {
      jdField_a_of_type_AndroidOsHandler = new Handler(ConnectivityAdapterHolder.getHandlerThreadLooper());
    }
    jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        try
        {
          WifiInfo localWifiInfo = ((WifiManager)Apn.a().getSystemService("wifi")).getConnectionInfo();
          if (localWifiInfo != null) {
            try
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("Wlan");
              localStringBuilder.append(localWifiInfo.getBSSID());
              Apn.a = localStringBuilder.toString();
              localStringBuilder = new StringBuilder();
              localStringBuilder.append("Apn--wifiInfo.getBSSID():");
              localStringBuilder.append(localWifiInfo.getBSSID());
              FLogger.d("ip-list", localStringBuilder.toString());
              return;
            }
            finally {}
          }
          return;
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
      }
    });
  }
  
  public static NetworkInfo getActiveNetworkInfo(boolean paramBoolean)
  {
    Object localObject1;
    if (paramBoolean)
    {
      localObject2 = jdField_a_of_type_AndroidNetNetworkInfo;
      localObject1 = localObject2;
      if (localObject2 != null) {
        return (NetworkInfo)localObject2;
      }
    }
    else
    {
      localObject1 = null;
    }
    Object localObject2 = getConnectivityManager();
    if (localObject2 != null) {
      try
      {
        localObject1 = ((ConnectivityManager)localObject2).getActiveNetworkInfo();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
    }
    jdField_a_of_type_AndroidNetNetworkInfo = localException;
    return localException;
  }
  
  public static ApnInfo getApnInfo()
  {
    return getApnInfo(false);
  }
  
  public static ApnInfo getApnInfo(boolean paramBoolean)
  {
    a(getActiveNetworkInfo(paramBoolean), paramBoolean);
    return jdField_a_of_type_ComTencentCommonHttpApn$ApnInfo;
  }
  
  public static String getApnName(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return jdField_b_of_type_JavaLangString;
    case 8192: 
      return "cmnet";
    case 4096: 
      return "wonet";
    case 2048: 
      return "ctlte";
    case 1024: 
      return "cmnet";
    case 512: 
      return "3gnet";
    case 256: 
      return "uninet";
    case 128: 
      return "ctnet";
    case 64: 
      return "ctwap";
    case 32: 
      return "uniwap";
    case 16: 
      return "3gwap";
    case 8: 
      return "cmwap";
    }
    return "Wlan";
  }
  
  public static String getApnNameWithBSSID(int paramInt)
  {
    if (paramInt != 4) {
      return getApnName(paramInt);
    }
    try
    {
      String str = jdField_a_of_type_JavaLangString;
      return str;
    }
    finally {}
  }
  
  public static ApnProxyInfo getApnProxyInfo()
  {
    try
    {
      c();
      ApnProxyInfo localApnProxyInfo = jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo;
      return localApnProxyInfo;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static int getApnType()
  {
    try
    {
      c();
      int i = sApnType;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static int getApnTypeS()
  {
    try
    {
      c();
      int i = sApnTypeS;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static ConnectivityManager getConnectivityManager()
  {
    ??? = jdField_a_of_type_AndroidNetConnectivityManager;
    if (??? != null) {
      return (ConnectivityManager)???;
    }
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      ConnectivityManager localConnectivityManager = jdField_a_of_type_AndroidNetConnectivityManager;
      if (localConnectivityManager == null) {
        try
        {
          jdField_a_of_type_AndroidNetConnectivityManager = (ConnectivityManager)b().getSystemService("connectivity");
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return null;
        }
      }
      return jdField_a_of_type_AndroidNetConnectivityManager;
    }
  }
  
  public static int getType(String paramString)
  {
    if (!TextUtils.isEmpty(paramString)) {
      try
      {
        paramString = paramString.trim().toLowerCase();
        if ((paramString.indexOf("wifi") == -1) && (paramString.indexOf("wlan") == -1))
        {
          if (paramString.indexOf("net") != -1) {
            return 1;
          }
          int i = paramString.indexOf("wap");
          if (i != -1) {
            return 2;
          }
        }
        else
        {
          return 4;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
    }
    return 0;
  }
  
  public static boolean is2GMode()
  {
    return is2GMode(false);
  }
  
  public static boolean is2GMode(boolean paramBoolean)
  {
    NetworkInfo localNetworkInfo = getActiveNetworkInfo(paramBoolean);
    if (localNetworkInfo == null) {
      return false;
    }
    int i = localNetworkInfo.getType();
    if (i == 1) {
      return false;
    }
    if (i == 0)
    {
      i = localNetworkInfo.getSubtype();
      if ((i != 4) && (i != 7) && (i != 11)) {
        switch (i)
        {
        default: 
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public static boolean is3GMode()
  {
    return is3GMode(false);
  }
  
  public static boolean is3GMode(boolean paramBoolean)
  {
    NetworkInfo localNetworkInfo = getActiveNetworkInfo(paramBoolean);
    if (localNetworkInfo == null) {
      return false;
    }
    int i = localNetworkInfo.getType();
    if (i == 1) {
      return false;
    }
    if (i == 0)
    {
      i = localNetworkInfo.getSubtype();
      if ((i != 4) && (i != 7) && (i != 11) && (i != 13)) {
        switch (i)
        {
        default: 
          return true;
        }
      }
      return false;
    }
    return false;
  }
  
  @Deprecated
  public static boolean is3GOr2GMode()
  {
    return is3GOr2GMode(false);
  }
  
  @Deprecated
  public static boolean is3GOr2GMode(boolean paramBoolean)
  {
    return isMobileNetwork(paramBoolean);
  }
  
  public static boolean is4GMode()
  {
    return is4GMode(false);
  }
  
  public static boolean is4GMode(boolean paramBoolean)
  {
    NetworkInfo localNetworkInfo = getActiveNetworkInfo(paramBoolean);
    if (localNetworkInfo == null) {
      return false;
    }
    int i = localNetworkInfo.getType();
    if (i == 1) {
      return false;
    }
    if (i == 0) {
      return localNetworkInfo.getSubtype() == 13;
    }
    return false;
  }
  
  public static boolean isActiveNetworkMetered()
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      ConnectivityManager localConnectivityManager = getConnectivityManager();
      if (localConnectivityManager == null) {
        return true;
      }
      return localConnectivityManager.isActiveNetworkMetered();
    }
    return false;
  }
  
  public static boolean isCharge()
  {
    Object localObject = getConnectivityManager();
    if (localObject == null) {
      return true;
    }
    try
    {
      localObject = ((ConnectivityManager)localObject).getActiveNetworkInfo();
      if (localObject == null) {
        return true;
      }
      return ((NetworkInfo)localObject).getType() == 0;
    }
    catch (Exception localException) {}
    return true;
  }
  
  public static boolean isFreeWifi()
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (isWifiMode(false))
    {
      bool1 = bool2;
      if (!isCharge()) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public static boolean isMobileNetwork()
  {
    return isMobileNetwork(false);
  }
  
  public static boolean isMobileNetwork(boolean paramBoolean)
  {
    NetworkInfo localNetworkInfo = getActiveNetworkInfo(paramBoolean);
    paramBoolean = false;
    if (localNetworkInfo == null) {
      return false;
    }
    if (localNetworkInfo.getType() == 0) {
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  public static boolean isNetworkAvailable()
  {
    return isNetworkConnected();
  }
  
  public static boolean isNetworkConnected()
  {
    NetworkInfo localNetworkInfo = getActiveNetworkInfo(false);
    if (localNetworkInfo == null) {
      return false;
    }
    return (localNetworkInfo.isConnected()) || (localNetworkInfo.isAvailable());
  }
  
  public static boolean isNetworkRemoating()
  {
    return jdField_b_of_type_Boolean;
  }
  
  public static boolean isNetworkStateConnectingOrConnected()
  {
    try
    {
      Object localObject = getConnectivityManager();
      NetworkInfo localNetworkInfo = ((ConnectivityManager)localObject).getNetworkInfo(0);
      localObject = ((ConnectivityManager)localObject).getNetworkInfo(1);
      if (localNetworkInfo != null)
      {
        if (localObject == null) {
          return false;
        }
        if (!localNetworkInfo.isConnectedOrConnecting())
        {
          boolean bool = ((NetworkInfo)localObject).isConnectedOrConnecting();
          return bool;
        }
        return true;
      }
      return false;
    }
    catch (Exception localException) {}
    return false;
  }
  
  public static boolean isWifiMode()
  {
    return isWifiMode(false);
  }
  
  public static boolean isWifiMode(boolean paramBoolean)
  {
    NetworkInfo localNetworkInfo = getActiveNetworkInfo(paramBoolean);
    return (localNetworkInfo != null) && (localNetworkInfo.getType() == 1);
  }
  
  public static void refreshApntype()
  {
    try
    {
      FLogger.d("ip-list", "Apn--refreshApntype()");
      if (!jdField_a_of_type_Boolean) {
        c();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static void registerRecevier()
  {
    if (jdField_a_of_type_ComTencentCommonHttpConnectionChangeHandler != null) {
      return;
    }
    jdField_a_of_type_ComTencentCommonHttpConnectionChangeHandler = new ConnectionChangeHandler(new ConnectionChangeHandler.ConnectionChangeCallback()
    {
      public void onConnectionChanged() {}
      
      public void onConnectionChanging() {}
    });
    jdField_a_of_type_ComTencentCommonHttpConnectionChangeHandler.startObserve();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.USER_PRESENT");
    localIntentFilter.addAction("android.intent.action.SCREEN_OFF");
    b().registerReceiver(new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        paramAnonymousContext = paramAnonymousIntent.getAction();
        if ((TextUtils.equals(paramAnonymousContext, "android.intent.action.USER_PRESENT")) || (TextUtils.equals(paramAnonymousContext, "android.intent.action.SCREEN_OFF")))
        {
          if (Apn.a() == null) {
            Apn.a(new Handler(ConnectivityAdapterHolder.getHandlerThreadLooper()));
          }
          Apn.a().post(new Runnable()
          {
            public void run() {}
          });
        }
      }
    }, localIntentFilter);
  }
  
  public static void setApplicationContext(Context paramContext)
  {
    jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  public static void tbsInit(Context paramContext)
  {
    setApplicationContext(paramContext);
    c();
  }
  
  public static String userNetworkSummary()
  {
    Object localObject2 = getActiveNetworkInfo(false);
    if (localObject2 == null) {
      return "";
    }
    Object localObject1 = b();
    if (localObject1 == null) {
      return "";
    }
    try
    {
      localObject1 = (TelephonyManager)((Context)localObject1).getSystemService("phone");
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      str = null;
    }
    if (str == null) {
      return "";
    }
    String str = str.getSimOperator();
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append("\nNetwork Information:\n");
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append("ExtraInfo=");
    localStringBuilder2.append(((NetworkInfo)localObject2).getExtraInfo());
    localStringBuilder2.append("\n");
    localStringBuilder1.append(localStringBuilder2.toString());
    localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append("SubtypeName=");
    localStringBuilder2.append(((NetworkInfo)localObject2).getSubtypeName());
    localStringBuilder2.append(" SubType = ");
    localStringBuilder2.append(((NetworkInfo)localObject2).getSubtype());
    localStringBuilder2.append("\n");
    localStringBuilder1.append(localStringBuilder2.toString());
    localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append("TypeName=");
    localStringBuilder2.append(((NetworkInfo)localObject2).getTypeName());
    localStringBuilder2.append(" Type = ");
    localStringBuilder2.append(((NetworkInfo)localObject2).getType());
    localStringBuilder2.append("\n");
    localStringBuilder1.append(localStringBuilder2.toString());
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("SimOperator=");
    ((StringBuilder)localObject2).append(str);
    ((StringBuilder)localObject2).append("\n");
    localStringBuilder1.append(((StringBuilder)localObject2).toString());
    return localStringBuilder1.toString();
  }
  
  public static class ApnInfo
  {
    public static final int MOBILE_NETWORK_TYPE_2G = 1;
    public static final int MOBILE_NETWORK_TYPE_3G = 2;
    public static final int MOBILE_NETWORK_TYPE_4G = 3;
    public static final int MOBILE_NETWORK_TYPE_UNKNOW = -1;
    private int jdField_a_of_type_Int = -1;
    private Apn.ApnProxyInfo jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo = null;
    private String jdField_a_of_type_JavaLangString = "get_dns_failed";
    private boolean jdField_a_of_type_Boolean = false;
    private int jdField_b_of_type_Int = -1;
    private boolean jdField_b_of_type_Boolean = false;
    private int jdField_c_of_type_Int = 4;
    private boolean jdField_c_of_type_Boolean = false;
    private int d = 4;
    private int e = -1;
    
    public int getApnType()
    {
      return this.jdField_c_of_type_Int;
    }
    
    public int getApnTypeS()
    {
      return this.d;
    }
    
    public String getCurrentDnsServer()
    {
      return this.jdField_a_of_type_JavaLangString;
    }
    
    public int getMobileNetworkType()
    {
      return this.e;
    }
    
    public int getNetworkType()
    {
      return this.jdField_a_of_type_Int;
    }
    
    public Apn.ApnProxyInfo getProxyInfo()
    {
      return this.jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo;
    }
    
    public int getSubNetworkType()
    {
      return this.jdField_b_of_type_Int;
    }
    
    public boolean isMobileNetwork()
    {
      return this.jdField_b_of_type_Boolean;
    }
    
    public boolean isWapPoint()
    {
      return this.jdField_c_of_type_Boolean;
    }
    
    public boolean isWifiNetwork()
    {
      return this.jdField_a_of_type_Boolean;
    }
    
    public void reset()
    {
      this.jdField_a_of_type_Int = -1;
      this.jdField_b_of_type_Int = -1;
      this.jdField_c_of_type_Int = 4;
      this.d = 4;
      this.jdField_a_of_type_ComTencentCommonHttpApn$ApnProxyInfo = null;
      this.jdField_a_of_type_JavaLangString = "get_dns_failed";
      this.jdField_a_of_type_Boolean = false;
      this.jdField_b_of_type_Boolean = false;
      this.e = -1;
    }
  }
  
  public static class ApnProxyInfo
  {
    public int apnPort = 80;
    public String apnProxy = null;
    public byte apnProxyType = 0;
    public boolean apnUseProxy = false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\Apn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */