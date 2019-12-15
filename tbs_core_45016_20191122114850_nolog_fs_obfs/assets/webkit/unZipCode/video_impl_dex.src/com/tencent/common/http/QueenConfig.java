package com.tencent.common.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.manifest.AppManifest;
import com.tencent.common.manifest.annotation.Extension;
import com.tencent.common.manifest.annotation.Service;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.tbs.common.internal.service.StatServerHolder;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class QueenConfig
{
  public static final int ERROR_CODE_API26 = 8;
  public static final int ERROR_CODE_DIRECT_REQUEST = 10;
  public static final int ERROR_CODE_DIRECT_URL = 4;
  public static final int ERROR_CODE_DISABLE = -2;
  public static final int ERROR_CODE_IPLIST_EMPTY = 2;
  public static final int ERROR_CODE_LOCAL_URL = 6;
  public static final int ERROR_CODE_OK = 0;
  public static final int ERROR_CODE_PROXY_ERROR = 3;
  public static final int ERROR_CODE_TOKEN_EMPTY = 1;
  public static final int ERROR_CODE_UNKNOWN = -1;
  public static final int ERROR_CODE_URL_CONNECTION = 9;
  public static final int ERROR_CODE_WHITE_LIST = 5;
  public static final int ERROR_CODE_WUP = 7;
  public static final String IPLIST_TYPE_HTTP = "queen_http";
  public static final String IPLIST_TYPE_HTTPS = "queen_https";
  public static final String TAG = "QueenConfig";
  static int jdField_a_of_type_Int;
  static long jdField_a_of_type_Long;
  static Handler jdField_a_of_type_AndroidOsHandler;
  private static IQueenInfoProvider jdField_a_of_type_ComTencentCommonHttpQueenConfig$IQueenInfoProvider;
  static Object jdField_a_of_type_JavaLangObject = new Object();
  static HashMap<String, QueenProxy> jdField_a_of_type_JavaUtilHashMap = null;
  static AtomicBoolean jdField_a_of_type_JavaUtilConcurrentAtomicAtomicBoolean;
  private static HostnameVerifier jdField_a_of_type_JavaxNetSslHostnameVerifier = null;
  static int jdField_b_of_type_Int;
  private static long jdField_b_of_type_Long;
  static Object jdField_b_of_type_JavaLangObject;
  static HashMap<String, QueenProxy> jdField_b_of_type_JavaUtilHashMap;
  static AtomicBoolean jdField_b_of_type_JavaUtilConcurrentAtomicAtomicBoolean;
  static int jdField_c_of_type_Int = 0;
  private static long jdField_c_of_type_Long;
  static HashMap<String, a> jdField_c_of_type_JavaUtilHashMap;
  static int d;
  
  static
  {
    jdField_d_of_type_Int = 0;
    jdField_b_of_type_JavaLangObject = new Object();
    jdField_b_of_type_JavaUtilHashMap = null;
    jdField_c_of_type_JavaUtilHashMap = null;
    jdField_a_of_type_Long = 0L;
    jdField_a_of_type_AndroidOsHandler = null;
    jdField_a_of_type_ComTencentCommonHttpQueenConfig$IQueenInfoProvider = null;
    jdField_a_of_type_JavaUtilConcurrentAtomicAtomicBoolean = new AtomicBoolean(false);
    jdField_b_of_type_JavaUtilConcurrentAtomicAtomicBoolean = new AtomicBoolean(false);
    jdField_b_of_type_Long = 0L;
    jdField_c_of_type_Long = 0L;
  }
  
  static int a()
  {
    int i = 0;
    ArrayList localArrayList = getIpList(false);
    if (localArrayList != null) {
      i = localArrayList.size();
    }
    return i;
  }
  
  static Handler a()
  {
    Handler localHandler = jdField_a_of_type_AndroidOsHandler;
    if (localHandler != null) {
      return localHandler;
    }
    try
    {
      if (jdField_a_of_type_AndroidOsHandler != null)
      {
        localHandler = jdField_a_of_type_AndroidOsHandler;
        return localHandler;
      }
      jdField_a_of_type_AndroidOsHandler = new Handler(BrowserExecutorSupplier.getLooperForRunLongTime())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (paramAnonymousMessage.what == 1001)
          {
            QueenConfig.a((QueenConfig.a)paramAnonymousMessage.obj);
            return;
          }
          if (paramAnonymousMessage.what == 1000) {
            QueenConfig.a();
          }
        }
      };
      return jdField_a_of_type_AndroidOsHandler;
    }
    finally {}
  }
  
  static String a(int paramInt)
  {
    switch (paramInt)
    {
    case 117: 
    case 118: 
    default: 
      return "default";
    case 120: 
      return "x5audio";
    case 119: 
      return "sysmedia";
    case 116: 
      return "videodownload";
    case 115: 
      return "videocache";
    case 114: 
      return "apkdetect";
    case 113: 
      return "httpupload";
    case 112: 
      return "httpcom";
    case 111: 
      return "novel";
    case 110: 
      return "search";
    case 109: 
      return "videoreport";
    case 108: 
      return "feedsreport";
    case 107: 
      return "music";
    case 106: 
      return "picture";
    case 105: 
      return "wup";
    case 104: 
      return "download";
    case 103: 
      return "urlConnection";
    }
    return "directrequest";
  }
  
  static String a(int paramInt1, int paramInt2)
  {
    Object localObject = "unknown";
    if (paramInt1 == 2) {
      return "iplistEmpty";
    }
    if (paramInt1 == 1) {
      return "tokenEmpty";
    }
    if (paramInt1 == 3)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("statusCode_");
      ((StringBuilder)localObject).append(paramInt2);
      return ((StringBuilder)localObject).toString();
    }
    if (paramInt1 == 4) {
      return "directUrl";
    }
    if (paramInt1 == 5) {
      return "whiteList";
    }
    if (paramInt1 == 6) {
      return "localUrl";
    }
    if (paramInt1 == 7) {
      return "wup";
    }
    if (paramInt1 == 8) {
      return "api26";
    }
    if (paramInt1 == 9) {
      return "urlConnection";
    }
    if (paramInt1 == 10) {
      localObject = "directrequest";
    }
    return (String)localObject;
  }
  
  static String a(a parama)
  {
    if ((parama.jdField_a_of_type_Int != 7) && (parama.jdField_c_of_type_Int != 106) && (parama.jdField_c_of_type_Int != 109) && (parama.jdField_c_of_type_Int != 108)) {
      return parama.jdField_a_of_type_JavaLangString;
    }
    return UrlUtils.getHost(parama.jdField_a_of_type_JavaLangString);
  }
  
  static void a()
  {
    Object localObject = jdField_c_of_type_JavaUtilHashMap;
    if (localObject != null)
    {
      if (((HashMap)localObject).size() < 1) {
        return;
      }
      localObject = jdField_c_of_type_JavaUtilHashMap.entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        String str = (String)localEntry.getKey();
        b((a)localEntry.getValue());
      }
      jdField_c_of_type_JavaUtilHashMap.clear();
      jdField_a_of_type_Long = System.currentTimeMillis();
      return;
    }
  }
  
  static void a(long paramLong)
  {
    try
    {
      Thread.sleep(paramLong);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
  }
  
  static void a(MttRequestBase paramMttRequestBase, int paramInt, boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.useAs(TypeTransformer.java:868)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:668)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  static void a(a parama)
  {
    if (jdField_c_of_type_JavaUtilHashMap == null) {
      jdField_c_of_type_JavaUtilHashMap = new HashMap();
    }
    long l = System.currentTimeMillis();
    if (jdField_a_of_type_Long == 0L) {
      jdField_a_of_type_Long = l;
    }
    Handler localHandler = a();
    if (Math.abs(l - jdField_a_of_type_Long) > 10000L)
    {
      localHandler.removeMessages(1000);
      a();
    }
    String str = a(parama);
    a locala = (a)jdField_c_of_type_JavaUtilHashMap.get(str);
    if (locala == null)
    {
      parama.jdField_a_of_type_JavaLangString = str;
      jdField_c_of_type_JavaUtilHashMap.put(str, parama);
    }
    else
    {
      locala.jdField_a_of_type_Long += parama.jdField_a_of_type_Long;
    }
    localHandler.removeMessages(1000);
    localHandler.sendEmptyMessageDelayed(1000, 3000L);
  }
  
  static int b()
  {
    ArrayList localArrayList = getIpList(true);
    if (localArrayList != null) {
      return localArrayList.size();
    }
    return 0;
  }
  
  static void b(a parama)
  {
    HashMap localHashMap = new HashMap();
    if (UrlUtils.isHttpsUrl(parama.jdField_a_of_type_JavaLangString)) {
      localObject = "https_tunnel";
    } else {
      localObject = "http";
    }
    localHashMap.put("module", localObject);
    localHashMap.put("retcode", String.valueOf(parama.jdField_b_of_type_Int));
    localHashMap.put("url", parama.jdField_a_of_type_JavaLangString);
    if (parama.jdField_c_of_type_JavaLangString != null) {
      localObject = parama.jdField_c_of_type_JavaLangString;
    } else {
      localObject = "null";
    }
    localHashMap.put("refer", localObject);
    localHashMap.put("bytes", String.valueOf(parama.jdField_a_of_type_Long));
    localHashMap.put("resourcetype", a(parama.jdField_c_of_type_Int));
    localHashMap.put("directreason", a(parama.jdField_a_of_type_Int, parama.jdField_d_of_type_Int));
    if (!TextUtils.isEmpty(parama.jdField_b_of_type_JavaLangString)) {
      localObject = parama.jdField_b_of_type_JavaLangString;
    } else {
      localObject = "null";
    }
    localHashMap.put("dnsip", localObject);
    if (!TextUtils.isEmpty(parama.jdField_d_of_type_JavaLangString)) {
      localHashMap.put("callfrom", parama.jdField_d_of_type_JavaLangString);
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("report: ");
    ((StringBuilder)localObject).append(localHashMap);
    FLogger.d("QueenConfig", ((StringBuilder)localObject).toString());
    if (parama.jdField_a_of_type_Boolean) {
      parama = "QUEEN_ERROR";
    } else {
      parama = "QUEEN_PROXY";
    }
    StatServerHolder.statWithBeacon(parama, localHashMap);
  }
  
  public static HostnameVerifier getHostnameVerifier()
  {
    HostnameVerifier localHostnameVerifier = jdField_a_of_type_JavaxNetSslHostnameVerifier;
    if (localHostnameVerifier != null) {
      return localHostnameVerifier;
    }
    try
    {
      if (jdField_a_of_type_JavaxNetSslHostnameVerifier == null) {
        jdField_a_of_type_JavaxNetSslHostnameVerifier = new HostnameVerifier()
        {
          public boolean verify(String paramAnonymousString, SSLSession paramAnonymousSSLSession)
          {
            return paramAnonymousString.equals(paramAnonymousSSLSession.getPeerHost());
          }
        };
      }
      return jdField_a_of_type_JavaxNetSslHostnameVerifier;
    }
    finally {}
  }
  
  public static IQueenInfoProvider getInfoProvider()
  {
    IQueenInfoProvider localIQueenInfoProvider = jdField_a_of_type_ComTencentCommonHttpQueenConfig$IQueenInfoProvider;
    if (localIQueenInfoProvider != null) {
      return localIQueenInfoProvider;
    }
    try
    {
      localIQueenInfoProvider = jdField_a_of_type_ComTencentCommonHttpQueenConfig$IQueenInfoProvider;
      if (localIQueenInfoProvider == null)
      {
        try
        {
          jdField_a_of_type_ComTencentCommonHttpQueenConfig$IQueenInfoProvider = (IQueenInfoProvider)AppManifest.getInstance().queryExtension(IQueenInfoProvider.class, null);
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
        if (jdField_a_of_type_ComTencentCommonHttpQueenConfig$IQueenInfoProvider == null) {
          jdField_a_of_type_ComTencentCommonHttpQueenConfig$IQueenInfoProvider = new IQueenInfoProvider()
          {
            public ArrayList<String> getHttpIPList()
            {
              return null;
            }
            
            public ArrayList<String> getHttpsIPList()
            {
              return null;
            }
            
            public QueenConfig.DnsData getIPAddress(String paramAnonymousString)
            {
              return null;
            }
            
            public String getToken()
            {
              return null;
            }
            
            public boolean handleCmd(String paramAnonymousString)
            {
              return false;
            }
            
            public boolean isDownProxyEnable()
            {
              return false;
            }
            
            public boolean isInProxyList(String paramAnonymousString)
            {
              return false;
            }
            
            public boolean isInWhiteList(String paramAnonymousString)
            {
              return false;
            }
            
            public boolean isLocalProxyEnable()
            {
              return false;
            }
            
            public boolean isProxySwitchEnable()
            {
              return false;
            }
            
            public boolean isQueenSIM()
            {
              return false;
            }
            
            public boolean isQueenUser()
            {
              return false;
            }
            
            public boolean isTunnelProxyEnable()
            {
              return false;
            }
            
            public boolean isUrlDirect(String paramAnonymousString)
            {
              return false;
            }
            
            public void refreshToken(QueenConfig.IRefreshQueenTokenCallback paramAnonymousIRefreshQueenTokenCallback) {}
            
            public void updateIPList() {}
          };
        }
      }
      return jdField_a_of_type_ComTencentCommonHttpQueenConfig$IQueenInfoProvider;
    }
    finally {}
  }
  
  public static ArrayList<String> getIpList(boolean paramBoolean)
  {
    IQueenInfoProvider localIQueenInfoProvider = getInfoProvider();
    int j = 0;
    Object localObject2;
    int i;
    Object localObject1;
    if (paramBoolean)
    {
      localObject2 = localIQueenInfoProvider.getHttpsIPList();
      if (localObject2 != null) {
        i = ((ArrayList)localObject2).size();
      } else {
        i = 0;
      }
      localObject1 = localObject2;
      if (i != jdField_c_of_type_Int)
      {
        jdField_d_of_type_Int = 0;
        jdField_c_of_type_Int = i;
        localObject1 = localObject2;
      }
    }
    else
    {
      localObject2 = localIQueenInfoProvider.getHttpIPList();
      if (localObject2 != null) {
        i = ((ArrayList)localObject2).size();
      } else {
        i = 0;
      }
      localObject1 = localObject2;
      if (i != jdField_a_of_type_Int)
      {
        jdField_b_of_type_Int = 0;
        jdField_a_of_type_Int = i;
        localObject1 = localObject2;
      }
    }
    if (localObject1 != null)
    {
      localObject2 = localObject1;
      if (((ArrayList)localObject1).size() >= 1) {}
    }
    else
    {
      jdField_b_of_type_Int = 0;
      jdField_d_of_type_Int = 0;
      jdField_a_of_type_Int = 0;
      jdField_c_of_type_Int = 0;
      refreshIPListIfNeed();
      localObject2 = localObject1;
      if (!ThreadUtils.isMainThread())
      {
        localObject2 = localObject1;
        if (jdField_b_of_type_JavaUtilConcurrentAtomicAtomicBoolean.compareAndSet(false, true))
        {
          FLogger.d("QueenConfig", "WAIT IPLIST...");
          a(3000L);
          if (paramBoolean) {
            localObject1 = localIQueenInfoProvider.getHttpsIPList();
          } else {
            localObject1 = localIQueenInfoProvider.getHttpIPList();
          }
          i = j;
          if (localObject1 != null) {
            i = ((ArrayList)localObject1).size();
          }
          if (paramBoolean)
          {
            jdField_c_of_type_Int = i;
            return (ArrayList<String>)localObject1;
          }
          jdField_a_of_type_Int = i;
          localObject2 = localObject1;
        }
      }
    }
    return (ArrayList<String>)localObject2;
  }
  
  protected static QueenProxy getProxy(ArrayList<String> paramArrayList, int paramInt)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() < 1) {
        return null;
      }
      int i = -1;
      if (paramInt == 0) {
        i = jdField_b_of_type_Int;
      } else if (paramInt == 1) {
        i = jdField_d_of_type_Int;
      }
      int j;
      if (i >= 0)
      {
        j = i;
        if (i < paramArrayList.size()) {}
      }
      else
      {
        j = 0;
      }
      paramArrayList = (String)paramArrayList.get(j);
      if (paramArrayList == null) {
        return null;
      }
      Object localObject2;
      if (paramInt == 0) {
        synchronized (jdField_a_of_type_JavaLangObject)
        {
          if (jdField_a_of_type_JavaUtilHashMap != null)
          {
            localObject2 = (QueenProxy)jdField_a_of_type_JavaUtilHashMap.get(paramArrayList);
            if (localObject2 != null) {
              return (QueenProxy)localObject2;
            }
          }
          localObject2 = paramArrayList.split(":");
          if ((localObject2 != null) && (localObject2.length >= 2))
          {
            if (jdField_a_of_type_JavaUtilHashMap == null) {
              jdField_a_of_type_JavaUtilHashMap = new HashMap();
            }
            localObject2 = new QueenProxy(localObject2[0], Integer.parseInt(localObject2[1]));
            jdField_a_of_type_JavaUtilHashMap.put(paramArrayList, localObject2);
            return (QueenProxy)localObject2;
          }
          return null;
        }
      }
      if (paramInt == 1) {
        synchronized (jdField_b_of_type_JavaLangObject)
        {
          if (jdField_b_of_type_JavaUtilHashMap != null)
          {
            localObject2 = (QueenProxy)jdField_b_of_type_JavaUtilHashMap.get(paramArrayList);
            if (localObject2 != null) {
              return (QueenProxy)localObject2;
            }
          }
          localObject2 = paramArrayList.split(":");
          if ((localObject2 != null) && (localObject2.length >= 2))
          {
            if (jdField_b_of_type_JavaUtilHashMap == null) {
              jdField_b_of_type_JavaUtilHashMap = new HashMap();
            }
            localObject2 = new QueenProxy(localObject2[0], Integer.parseInt(localObject2[1]));
            jdField_b_of_type_JavaUtilHashMap.put(paramArrayList, localObject2);
            return (QueenProxy)localObject2;
          }
          return null;
        }
      }
      return null;
    }
    return null;
  }
  
  public static String getToken()
  {
    IQueenInfoProvider localIQueenInfoProvider = getInfoProvider();
    String str2 = localIQueenInfoProvider.getToken();
    String str1 = str2;
    if (TextUtils.isEmpty(str2))
    {
      refreshTokenIfNeed();
      str1 = str2;
      if (!ThreadUtils.isMainThread())
      {
        str1 = str2;
        if (jdField_a_of_type_JavaUtilConcurrentAtomicAtomicBoolean.compareAndSet(false, true))
        {
          FLogger.d("QueenConfig", "WAIT TOKEN...");
          a(3000L);
          str1 = localIQueenInfoProvider.getToken();
        }
      }
    }
    return str1;
  }
  
  public static boolean isQueenEnable()
  {
    IQueenInfoProvider localIQueenInfoProvider = getInfoProvider();
    return (localIQueenInfoProvider.isDownProxyEnable()) && (localIQueenInfoProvider.isTunnelProxyEnable()) && (localIQueenInfoProvider.isQueenUser());
  }
  
  protected static boolean isQueenProxyEnable()
  {
    IQueenInfoProvider localIQueenInfoProvider = getInfoProvider();
    Apn.ApnInfo localApnInfo = Apn.getApnInfo(true);
    return (localIQueenInfoProvider.isProxySwitchEnable()) || ((localApnInfo.isMobileNetwork()) && (!localApnInfo.isWapPoint()) && (localIQueenInfoProvider.isDownProxyEnable()) && (localIQueenInfoProvider.isQueenUser()));
  }
  
  public static boolean isQueenSIM()
  {
    return getInfoProvider().isQueenSIM();
  }
  
  public static void refreshIPListIfNeed()
  {
    long l = System.currentTimeMillis();
    if (Math.abs(l - jdField_b_of_type_Long) < 5000L) {
      return;
    }
    jdField_b_of_type_Long = l;
    getInfoProvider().updateIPList();
  }
  
  public static void refreshTokenIfNeed()
  {
    long l = System.currentTimeMillis();
    if (Math.abs(l - jdField_c_of_type_Long) < 5000L) {
      return;
    }
    jdField_c_of_type_Long = l;
    getInfoProvider().refreshToken(null);
  }
  
  public static void report(int paramInt1, String paramString1, String paramString2, int paramInt2, long paramLong, int paramInt3, int paramInt4, String paramString3, boolean paramBoolean, String paramString4)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return;
    }
    a locala = new a();
    locala.jdField_a_of_type_Int = paramInt1;
    locala.jdField_a_of_type_JavaLangString = paramString1;
    locala.jdField_c_of_type_JavaLangString = paramString2;
    locala.jdField_b_of_type_Int = paramInt2;
    locala.jdField_a_of_type_Long = paramLong;
    locala.jdField_c_of_type_Int = paramInt3;
    locala.jdField_d_of_type_Int = paramInt4;
    locala.jdField_b_of_type_JavaLangString = paramString3;
    locala.jdField_a_of_type_Boolean = paramBoolean;
    locala.jdField_d_of_type_JavaLangString = paramString4;
    paramString1 = a().obtainMessage();
    paramString1.obj = locala;
    paramString1.what = 1001;
    a().sendMessage(paramString1);
  }
  
  public static void reportProxyError(int paramInt1, String paramString1, int paramInt2, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return;
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("code", String.valueOf(paramInt1));
    localHashMap.put("url", paramString1);
    paramString1 = paramString2;
    if (TextUtils.isEmpty(paramString2)) {
      paramString1 = "null";
    }
    localHashMap.put("token", paramString1);
    paramString1 = new StringBuilder();
    paramString1.append("report proxy error: ");
    paramString1.append(localHashMap);
    FLogger.d("QueenConfig", paramString1.toString());
    StatServerHolder.statWithBeacon("QUEEN_PROXY_ERROR", localHashMap);
  }
  
  public static boolean switchHttpProxy()
  {
    int i = a();
    if (i < 1) {
      return false;
    }
    jdField_b_of_type_Int += 1;
    if (jdField_b_of_type_Int >= i)
    {
      jdField_b_of_type_Int = 0;
      return false;
    }
    return true;
  }
  
  public static boolean switchHttpsProxy()
  {
    int i = b();
    if (i < 1) {
      return false;
    }
    jdField_d_of_type_Int += 1;
    if (jdField_d_of_type_Int >= i)
    {
      jdField_d_of_type_Int = 0;
      return false;
    }
    return true;
  }
  
  public static class DnsData
  {
    long jdField_a_of_type_Long;
    String jdField_a_of_type_JavaLangString;
    long b = 1200000L;
    public int mFailTimes = 0;
    public String mIP;
    public String mNetworkInfo = "";
    public String mType = "NULL";
    
    boolean a()
    {
      return System.currentTimeMillis() >= this.jdField_a_of_type_Long + this.b;
    }
    
    public DnsData clone()
    {
      DnsData localDnsData = new DnsData();
      localDnsData.jdField_a_of_type_JavaLangString = this.jdField_a_of_type_JavaLangString;
      localDnsData.mIP = this.mIP;
      localDnsData.jdField_a_of_type_Long = this.jdField_a_of_type_Long;
      localDnsData.b = this.b;
      localDnsData.mType = this.mType;
      localDnsData.mNetworkInfo = this.mNetworkInfo;
      return localDnsData;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof DnsData;
      boolean bool2 = false;
      if (!bool1) {
        return false;
      }
      paramObject = (DnsData)paramObject;
      bool1 = bool2;
      if (TextUtils.equals(this.mIP, ((DnsData)paramObject).mIP))
      {
        bool1 = bool2;
        if (TextUtils.equals(this.jdField_a_of_type_JavaLangString, ((DnsData)paramObject).jdField_a_of_type_JavaLangString)) {
          bool1 = true;
        }
      }
      return bool1;
    }
    
    public int hashCode()
    {
      boolean bool = TextUtils.isEmpty(this.mIP);
      int j = 0;
      int i;
      if (bool) {
        i = 0;
      } else {
        i = this.mIP.hashCode();
      }
      if (!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) {
        j = this.jdField_a_of_type_JavaLangString.hashCode();
      }
      return i ^ j;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[domain=");
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(", ip=");
      localStringBuilder.append(this.mIP);
      localStringBuilder.append(", TTL=");
      localStringBuilder.append(this.b);
      localStringBuilder.append(", expired=");
      localStringBuilder.append(a());
      localStringBuilder.append(", type=");
      localStringBuilder.append(this.mType);
      localStringBuilder.append(", netInfo=");
      localStringBuilder.append(this.mNetworkInfo);
      localStringBuilder.append(", failTimes=");
      localStringBuilder.append(this.mFailTimes);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  @Extension
  @Service
  public static abstract interface IQueenInfoProvider
  {
    public abstract ArrayList<String> getHttpIPList();
    
    public abstract ArrayList<String> getHttpsIPList();
    
    public abstract QueenConfig.DnsData getIPAddress(String paramString);
    
    public abstract String getToken();
    
    public abstract boolean handleCmd(String paramString);
    
    public abstract boolean isDownProxyEnable();
    
    public abstract boolean isInProxyList(String paramString);
    
    public abstract boolean isInWhiteList(String paramString);
    
    public abstract boolean isLocalProxyEnable();
    
    public abstract boolean isProxySwitchEnable();
    
    public abstract boolean isQueenSIM();
    
    public abstract boolean isQueenUser();
    
    public abstract boolean isTunnelProxyEnable();
    
    public abstract boolean isUrlDirect(String paramString);
    
    public abstract void refreshToken(QueenConfig.IRefreshQueenTokenCallback paramIRefreshQueenTokenCallback);
    
    public abstract void updateIPList();
  }
  
  public static abstract interface IRefreshQueenTokenCallback
  {
    public abstract void onGetToken(String paramString);
  }
  
  public static class QueenConfigInfo
  {
    public String guid = null;
    public HashMap<String, String> headers = null;
    public HostnameVerifier hostnameVerifier = null;
    public boolean isHttpsRequest = false;
    public Proxy proxy = null;
    public QueenConfig.QueenProxy proxyInfo = null;
    public String qua = null;
    public String token = null;
    public String url = null;
  }
  
  public static class QueenProxy
  {
    public int port = 0;
    public String url = null;
    
    public QueenProxy(String paramString, int paramInt)
    {
      this.url = paramString;
      this.port = paramInt;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[");
      localStringBuilder.append(this.url);
      localStringBuilder.append(":");
      localStringBuilder.append(this.port);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  static class a
  {
    int jdField_a_of_type_Int = -1;
    long jdField_a_of_type_Long = 0L;
    String jdField_a_of_type_JavaLangString = null;
    boolean jdField_a_of_type_Boolean = false;
    int jdField_b_of_type_Int = 0;
    String jdField_b_of_type_JavaLangString = null;
    int jdField_c_of_type_Int = -1;
    String jdField_c_of_type_JavaLangString = null;
    int jdField_d_of_type_Int = -1;
    String jdField_d_of_type_JavaLangString = null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\QueenConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */