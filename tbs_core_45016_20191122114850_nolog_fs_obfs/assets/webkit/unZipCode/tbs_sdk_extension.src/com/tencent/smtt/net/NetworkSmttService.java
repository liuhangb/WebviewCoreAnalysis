package com.tencent.smtt.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.tencent.common.http.Apn;
import com.tencent.smtt.util.MttLog;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.util.j;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.tbsshell.common.QProxyPolicies;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.PatternSyntaxException;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class NetworkSmttService
{
  private static int jdField_a_of_type_Int = 2;
  private static SharedPreferences jdField_a_of_type_AndroidContentSharedPreferences;
  public static String a = "";
  public static ArrayList<String> a;
  private static HashMap<String, Integer> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  private static boolean jdField_a_of_type_Boolean = false;
  private static int jdField_b_of_type_Int = 1;
  private static String jdField_b_of_type_JavaLangString = "java.NetworkSmttService";
  private static boolean jdField_b_of_type_Boolean = false;
  private static int jdField_c_of_type_Int = 0;
  private static String jdField_c_of_type_JavaLangString = "http://metis.wsd.com/mark";
  private static boolean jdField_c_of_type_Boolean = false;
  private static int jdField_d_of_type_Int;
  private static String jdField_d_of_type_JavaLangString;
  private static boolean jdField_d_of_type_Boolean = true;
  
  static
  {
    jdField_d_of_type_JavaLangString = "MTT_CORE_SEARCH_VIDEO_INFO";
    jdField_a_of_type_JavaUtilArrayList = new ArrayList();
  }
  
  @CalledByNative
  public static int GetProxyServerAndUserStatus()
  {
    try
    {
      int i = QProxyPolicies.setServerAndUserSettingSituation(0, SmttServiceProxy.getInstance().getServerAllowQProxyStatus(), WebSettingsExtension.getQProxyEnabled(), WebSettingsExtension.getIsHostUseQProxy());
      return i;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      localNoSuchMethodError.printStackTrace();
    }
    return 0;
  }
  
  @CalledByNative
  public static int MarkQuicFail(String paramString)
  {
    if (jdField_c_of_type_Boolean) {
      return 2;
    }
    if (jdField_d_of_type_Boolean) {
      a();
    }
    int i;
    if (jdField_a_of_type_JavaUtilHashMap.containsKey(paramString)) {
      i = ((Integer)jdField_a_of_type_JavaUtilHashMap.get(paramString)).intValue() + 1;
    } else {
      i = 1;
    }
    jdField_a_of_type_JavaUtilHashMap.put(paramString, Integer.valueOf(i));
    jdField_d_of_type_Int += 1;
    jdField_a_of_type_JavaUtilHashMap.put("quic_total_fail_count", Integer.valueOf(jdField_d_of_type_Int));
    jdField_a_of_type_JavaUtilHashMap.put("save_time", Integer.valueOf((int)(System.currentTimeMillis() / 60000L)));
    if (i >= 3) {
      i = 1;
    } else {
      i = 0;
    }
    if (jdField_d_of_type_Int >= 5)
    {
      jdField_c_of_type_Boolean = true;
      i = 2;
    }
    if (jdField_a_of_type_AndroidContentSharedPreferences == null) {
      jdField_a_of_type_AndroidContentSharedPreferences = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_quic_fail_list", 0);
    }
    paramString = "";
    Object localObject = jdField_a_of_type_JavaUtilHashMap.entrySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append(",");
      localStringBuilder.append((String)localEntry.getKey());
      localStringBuilder.append(" ");
      localStringBuilder.append(localEntry.getValue());
      paramString = localStringBuilder.toString();
    }
    if (paramString.length() > 1)
    {
      localObject = jdField_a_of_type_AndroidContentSharedPreferences.edit();
      ((SharedPreferences.Editor)localObject).putString("preferences_quic_fail_list", paramString.substring(1));
      ((SharedPreferences.Editor)localObject).commit();
    }
    return i;
  }
  
  @CalledByNative
  private static void ReportToBeacon(Object paramObject, String paramString)
  {
    if (paramObject != null) {
      paramObject = (HashMap)paramObject;
    } else {
      paramObject = null;
    }
    if ((paramObject != null) && (((HashMap)paramObject).size() > 0)) {
      SmttServiceProxy.getInstance().upLoadToBeacon(paramString, (Map)paramObject);
    }
  }
  
  public static int a()
  {
    return Apn.sApnTypeS;
  }
  
  public static int a(int paramInt1, int paramInt2)
  {
    return QProxyPolicies.setServerAndUserSettingSituation(QProxyPolicies.setDirectReason(QProxyPolicies.setFinallyUsedQProxy(paramInt1, false), paramInt2), SmttServiceProxy.getInstance().getServerAllowQProxyStatus(), WebSettingsExtension.getQProxyEnabled(), WebSettingsExtension.getIsHostUseQProxy());
  }
  
  private static void a()
  {
    if (jdField_a_of_type_AndroidContentSharedPreferences == null) {
      jdField_a_of_type_AndroidContentSharedPreferences = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_quic_fail_list", 0);
    }
    Object localObject = jdField_a_of_type_AndroidContentSharedPreferences.getString("preferences_quic_fail_list", "");
    if (TextUtils.isEmpty((CharSequence)localObject))
    {
      jdField_d_of_type_Boolean = false;
      return;
    }
    try
    {
      localObject = ((String)localObject).split(",");
      int i = 0;
      while (i < localObject.length)
      {
        String[] arrayOfString = localObject[i].split(" ");
        jdField_a_of_type_JavaUtilHashMap.put(arrayOfString[0], Integer.valueOf(Integer.parseInt(arrayOfString[1])));
        i += 1;
      }
      if (jdField_a_of_type_JavaUtilHashMap.containsKey("quic_total_fail_count")) {
        jdField_d_of_type_Int = ((Integer)jdField_a_of_type_JavaUtilHashMap.get("quic_total_fail_count")).intValue();
      }
      if (!jdField_a_of_type_JavaUtilHashMap.containsKey("save_time")) {
        break label189;
      }
      i = ((Integer)jdField_a_of_type_JavaUtilHashMap.get("save_time")).intValue();
      if (System.currentTimeMillis() / 60000L - i <= 1440L) {
        break label189;
      }
      jdField_d_of_type_Int = 0;
      jdField_a_of_type_JavaUtilHashMap.clear();
    }
    catch (Exception localException)
    {
      label189:
      for (;;) {}
    }
    MttLog.d(jdField_b_of_type_JavaLangString, "InitQuicJudge-convertdata-fail");
    jdField_d_of_type_Boolean = false;
  }
  
  public static void a(String paramString)
  {
    if ((!jdField_a_of_type_Boolean) && (paramString.startsWith(jdField_c_of_type_JavaLangString))) {
      jdField_a_of_type_Boolean = true;
    }
  }
  
  public static boolean a(String paramString)
  {
    int i = 0;
    for (;;)
    {
      if (i < jdField_a_of_type_JavaUtilArrayList.size())
      {
        String str = (String)jdField_a_of_type_JavaUtilArrayList.get(i);
        try
        {
          boolean bool = paramString.matches(str);
          if (bool) {
            break label48;
          }
        }
        catch (PatternSyntaxException localPatternSyntaxException)
        {
          localPatternSyntaxException.printStackTrace();
          i += 1;
        }
      }
    }
    label48:
    return i < jdField_a_of_type_JavaUtilArrayList.size();
  }
  
  @CalledByNative
  public static boolean allowUseContentCacheBusiness(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (!e.a().b(paramBoolean)) {
      return true;
    }
    return SmttServiceProxy.getInstance().allowUseContentCacheBusiness(paramString1, paramString2);
  }
  
  @CalledByNative
  public static boolean checkUseX5CookiePathPolicyEnabled()
  {
    return SmttServiceProxy.getInstance().checkUseX5CookiePathPolicyEnabled();
  }
  
  @CalledByNative
  private static Object createHashMap()
  {
    return new HashMap();
  }
  
  @CalledByNative
  public static String getApnNameWithSSID()
  {
    return Apn.getApnNameWithBSSID(Apn.sApnTypeS);
  }
  
  @CalledByNative
  public static String getDebugCustomHosts()
  {
    return SmttServiceProxy.getInstance().loadCustomHostsList();
  }
  
  @CalledByNative
  public static int getDebugTbsProxyType()
  {
    return SmttServiceProxy.getInstance().getQProxyType();
  }
  
  @CalledByNative
  public static int getDnsResolveTypeForThisDomain(String paramString)
  {
    if (WebSettingsExtension.shouldHttpDnsResolveForThisDomain(paramString))
    {
      X5Log.d("NETLOG_DNS", "shouldHttpDnsResolveForThisDomain return DNS_RESOLVE_TYPE_UDP(1)");
      return jdField_b_of_type_Int;
    }
    int i = SmttServiceProxy.getInstance().getDnsResolveTypeForThisDomain(paramString);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" WebSettingExtension domain no set ----> SmttServiceProxy.getDnsResolveTypeForThisDomain host is ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" ret is ");
    localStringBuilder.append(i);
    X5Log.d("NETLOG_DNS", localStringBuilder.toString());
    return i;
  }
  
  @CalledByNative
  public static String[] getExtraHttpsTunnelHeaders(boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    k.a(localArrayList, paramBoolean);
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  @CalledByNative
  public static String[] getExtraQHeaders(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject;
    if (paramBoolean1)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("q-proxy-type: ");
      ((StringBuilder)localObject).append(k.a(paramString, paramInt, paramBoolean2));
      localArrayList.add(((StringBuilder)localObject).toString());
      if ((paramBoolean2) && (WebSettingsExtension.getMiniQBInfo() != null) && (WebSettingsExtension.getMiniQBInfo().length() > 1))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("miniqb:");
        ((StringBuilder)localObject).append(WebSettingsExtension.getMiniQBInfo());
        localArrayList.add(((StringBuilder)localObject).toString());
      }
    }
    k.a(localArrayList, j.c(paramString), paramBoolean1);
    paramString = WebSettingsExtension.getAdditionalHttpHeaders();
    if ((paramString != null) && (!paramString.isEmpty()))
    {
      paramString = paramString.entrySet().iterator();
      while (paramString.hasNext())
      {
        localObject = (Map.Entry)paramString.next();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append((String)((Map.Entry)localObject).getKey());
        localStringBuilder.append(":");
        localStringBuilder.append((String)((Map.Entry)localObject).getValue());
        localArrayList.add(localStringBuilder.toString());
      }
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  @CalledByNative
  public static String getFetchPageScriptStr(String paramString)
  {
    return h.a().a(paramString);
  }
  
  @CalledByNative
  public static String getHttpDNSEncryptKey()
  {
    return SmttServiceProxy.getInstance().getHttpDNSEncryptKey();
  }
  
  @CalledByNative
  public static int getHttpDNSRequestID(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty())) {
      return SmttServiceProxy.getInstance().getHttpDNSRequestID(paramString);
    }
    return 0;
  }
  
  @CalledByNative
  public static String getHttpDNSServerIP()
  {
    return SmttServiceProxy.getInstance().getHttpDNSServerIP();
  }
  
  @CalledByNative
  public static String getHttpsTunnelProxyAddress(String paramString1, int paramInt, String paramString2)
  {
    if (SmttServiceProxy.getInstance().isEnableDirect()) {
      return "";
    }
    if (SmttServiceProxy.getInstance().isVPNConnected()) {
      return "";
    }
    int i;
    if (paramInt == 0) {
      i = Apn.getApnTypeS();
    } else {
      i = Apn.sApnTypeS;
    }
    if (!WebSettingsExtension.getQProxyEnabled()) {
      return "";
    }
    SmttServiceProxy localSmttServiceProxy = SmttServiceProxy.getInstance();
    boolean bool;
    if (paramInt == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return localSmttServiceProxy.getHttpsTunnelProxyAddress(paramString1, bool, i, WebSettingsExtension.getQProxyEnabled(), paramString2);
  }
  
  @CalledByNative
  public static int getProxyAddressType()
  {
    int i = SmttServiceProxy.getInstance().getQProxyType();
    if ((i >= 1) && (i <= 4)) {
      if (i != 1)
      {
        if (i == 3) {
          return 2;
        }
        if ((i == 2) || (i == 4)) {
          return 1;
        }
      }
      else
      {
        return 2;
      }
    }
    return SmttServiceProxy.getInstance().getProxyAddressType();
  }
  
  @CalledByNative
  public static int getRestrictErrorPageReloadSecond()
  {
    return SmttServiceProxy.getInstance().getRestrictErrorPageReloadSecond();
  }
  
  @CalledByNative
  public static int getSubResConnectType()
  {
    return SmttServiceProxy.getInstance().getSubResDirect();
  }
  
  @CalledByNative
  public static String getTbsGUID()
  {
    MttLog.v(jdField_b_of_type_JavaLangString, "getTbsGUID");
    return SmttServiceProxy.getInstance().getGUID();
  }
  
  @CalledByNative
  public static String[] getX5ProxyStatus(String paramString1, int paramInt, String paramString2)
  {
    ArrayList localArrayList = new ArrayList(4);
    boolean bool3 = true;
    boolean bool1;
    if ((paramInt != 0) && (paramInt != -1)) {
      bool1 = false;
    } else {
      bool1 = true;
    }
    a.a(a.a);
    if (SmttServiceProxy.getInstance().isEnableDirect())
    {
      localArrayList.add(Integer.toString(a(0, 2097152)));
      return (String[])localArrayList.toArray(new String[localArrayList.size()]);
    }
    if (SmttServiceProxy.getInstance().isEnableX5QUICDirect())
    {
      localArrayList.add(Integer.toString(a(0, 1610612736)));
      return (String[])localArrayList.toArray(new String[localArrayList.size()]);
    }
    if (SmttServiceProxy.getInstance().isVPNConnected())
    {
      localArrayList.add(Integer.toString(a(0, 4194304)));
      return (String[])localArrayList.toArray(new String[localArrayList.size()]);
    }
    if (bool1) {
      paramInt = Apn.getApnTypeS();
    } else {
      paramInt = Apn.sApnTypeS;
    }
    int i;
    boolean bool2;
    if ((!SmttServiceProxy.getInstance().isEnableX5HTTP2ProxyByForce()) && (!SmttServiceProxy.getInstance().isEnableX5QUICProxyByForce()) && ((!bool1) || (!paramString1.equals("http://x5.preconnect.com/"))))
    {
      i = SmttServiceProxy.getInstance().getQProxyUsingFlag(paramString1, bool1, WebSettingsExtension.getQProxyEnabled(), WebSettingsExtension.getIsKidMode(), WebSettingsExtension.getIsHostUseQProxy(), paramString2, SmttServiceProxy.getInstance().isKingCardUser());
      bool1 = QProxyPolicies.shouldUseQProxyAccordingToFlag(i);
      bool2 = QProxyPolicies.shouldForceUseQProxy(i);
    }
    else
    {
      i = QProxyPolicies.setForceQProxyReason(256);
      bool2 = true;
      bool1 = bool3;
    }
    int j = getProxyAddressType();
    if ((bool1) && (j != a.a(a.b))) {
      if (a(paramString1))
      {
        localArrayList.add(Integer.toString(a(0, 2048)));
      }
      else
      {
        paramString1 = SmttServiceProxy.getInstance().getQProxyAddress(paramInt);
        if ((paramString1 != null) && (paramString1.getHost() != null))
        {
          if (bool2) {
            localArrayList.add("force");
          } else {
            localArrayList.add("normal");
          }
          paramString2 = new StringBuilder();
          paramString2.append(paramString1.getHost());
          paramString2.append(":");
          paramString2.append(paramString1.getPort());
          localArrayList.add(paramString2.toString());
          localArrayList.add(Integer.toString(j));
        }
        else
        {
          localArrayList.add(Integer.toString(a(0, 67108864)));
        }
      }
    }
    localArrayList.add(Integer.toString(i));
    paramString1 = new StringBuilder();
    paramString1.append("getX5ProxyStatus iProxyType(QUIC temporary disable)=");
    paramString1.append(j);
    paramString1.append(" result=");
    paramString1.append(localArrayList.toString());
    paramString1.append("(");
    paramString1.append(QProxyPolicies.proxyTypeString(i));
    paramString1.append(")");
    X5Log.d("X5Proxy", paramString1.toString());
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  @CalledByNative
  public static String getXTbsKeyPrivateKey()
  {
    return SmttServiceProxy.getInstance().getXTbsKeyPrivateKey();
  }
  
  @CalledByNative
  public static String[] getforceGoQuicList()
  {
    return SmttServiceProxy.getInstance().getforceGoQuicList();
  }
  
  @CalledByNative
  public static boolean isAllowDnsStoreEnable()
  {
    return SmttServiceProxy.getInstance().isAllowDnsStoreEnable();
  }
  
  @CalledByNative
  public static boolean isDoNotTrackEnabled()
  {
    return WebSettingsExtension.getDoNotTrackEnabled();
  }
  
  @CalledByNative
  public static boolean isEnableDeadCodeDetectionInApp()
  {
    return e.a();
  }
  
  @CalledByNative
  public static int isEnableX5TBSHeaderType(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty())) {
      return SmttServiceProxy.getInstance().isEnableX5TBSHeaderType(paramString);
    }
    return -1;
  }
  
  @CalledByNative
  public static boolean isInNovelSiteAdFilterBlackList(String paramString)
  {
    return SmttServiceProxy.getInstance().isInNovelSiteAdFilterBlackList(paramString);
  }
  
  @CalledByNative
  public static boolean isInNovelSiteAdFilterWhiteList(String paramString)
  {
    return SmttServiceProxy.getInstance().isInNovelSiteAdFilterWhiteList(paramString);
  }
  
  @CalledByNative
  public static boolean isInSelectedAdFilterWhiteList(String paramString)
  {
    return SmttServiceProxy.getInstance().isInSelectedAdFilterWhiteList(paramString);
  }
  
  @CalledByNative
  public static boolean isKingCardUser()
  {
    return SmttServiceProxy.getInstance().isKingCardUser();
  }
  
  @CalledByNative
  public static boolean isNeedQHeaders(String paramString)
  {
    return SmttServiceProxy.getInstance().isNeedQHead(j.c(paramString));
  }
  
  @CalledByNative
  public static boolean isNetworkConnected()
  {
    return Apn.isNetworkConnected();
  }
  
  @CalledByNative
  public static boolean isPerformanceLogRecordOpen()
  {
    return SmttServiceProxy.getInstance().isPerformanceLogRecordOpen();
  }
  
  @CalledByNative
  public static boolean isRestrictErrorPageReload(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    return SmttServiceProxy.getInstance().isRestrictErrorPageReload(paramString);
  }
  
  @CalledByNative
  public static boolean isSpdyProxySignEnabled()
  {
    if (!jdField_b_of_type_Boolean)
    {
      jdField_a_of_type_Boolean = SmttServiceProxy.getInstance().getSPDYProxySign();
      jdField_b_of_type_Boolean = true;
    }
    return jdField_a_of_type_Boolean;
  }
  
  @CalledByNative
  public static boolean isTbsImpatientNetworkEnabled()
  {
    return SmttServiceProxy.getInstance().getTbsImpatientNetworkEnabled();
  }
  
  @CalledByNative
  public static boolean isUseDefaultDNSTTL(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty())) {
      return SmttServiceProxy.getInstance().isUseDefaultDNSTTL(paramString);
    }
    return false;
  }
  
  @CalledByNative
  public static boolean isX5CookieIsolationEnabled()
  {
    return SmttServiceProxy.getInstance().isX5CookieIsolationEnabled();
  }
  
  @CalledByNative
  public static boolean isX5CookieIsolationEnabledForTBS()
  {
    return SmttServiceProxy.getInstance().isX5CookieIsolationEnabledForTBS();
  }
  
  @CalledByNative
  public static int kingCardUserProxyJudgeCondition()
  {
    if (SmttServiceProxy.getInstance().isKingCardUser()) {
      return SmttServiceProxy.getInstance().kingCardUserProxyJudgeCondition();
    }
    return 0;
  }
  
  @CalledByNative
  public static void notifyDirectVisitDomain(String paramString, int paramInt)
  {
    String str = jdField_b_of_type_JavaLangString;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("notifyDirectVisitDomain url:");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" code:");
    localStringBuilder.append(paramInt);
    MttLog.v(str, localStringBuilder.toString());
    if ((paramInt != 802) && (paramInt != 803))
    {
      if (paramInt == 805) {
        SmttServiceProxy.getInstance().setQProxyBlackDomain(j.c(paramString));
      }
    }
    else {
      SmttServiceProxy.getInstance().setQProxyBlackUrl(paramString);
    }
  }
  
  @CalledByNative
  public static void notifyDirectVisitRegular(String paramString)
  {
    if (!jdField_a_of_type_JavaUtilArrayList.contains(paramString)) {
      jdField_a_of_type_JavaUtilArrayList.add(paramString);
    }
  }
  
  @CalledByNative
  public static void notifyHttpsTunnelProxyFail(String paramString, int paramInt)
  {
    String str = jdField_b_of_type_JavaLangString;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("notifyHttpsTunnelProxyFail proxyHost:");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" errorId = ");
    localStringBuilder.append(paramInt);
    X5Log.i(str, localStringBuilder.toString());
    SmttServiceProxy.getInstance().notifyHttpsTunnelProxyFail(Apn.sApnTypeS, paramString, paramInt);
  }
  
  @CalledByNative
  public static void notifyHttpsTunnelTokenExpired()
  {
    X5Log.i(jdField_b_of_type_JavaLangString, "notifyHttpsTunnelTokenExpired");
    SmttServiceProxy.getInstance().notifyHttpsTunnelExpired();
  }
  
  @CalledByNative
  public static void notifyProxyStatusReport(boolean paramBoolean1, String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2, int paramInt4, boolean paramBoolean2)
  {
    HashMap localHashMap = new HashMap();
    if (paramBoolean2) {
      paramInt4 = 11;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramBoolean1);
    localStringBuilder.append("");
    localHashMap.put("isSucceed", localStringBuilder.toString());
    localHashMap.put("url", paramString1);
    paramString1 = new StringBuilder();
    paramString1.append(paramInt3);
    paramString1.append("");
    localHashMap.put("errorid", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append(paramInt1);
    paramString1.append("");
    localHashMap.put("responsecode", paramString1.toString());
    localHashMap.put("proxyhost", paramString2);
    paramString1 = new StringBuilder();
    paramString1.append(paramInt2);
    paramString1.append("");
    localHashMap.put("resourceType", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append(paramInt4);
    paramString1.append("");
    localHashMap.put("proxyAddressType", paramString1.toString());
    SmttServiceProxy.getInstance().notifyProxyStatusReport(paramBoolean1, localHashMap);
  }
  
  @CalledByNative
  public static void notifySpdyProxyCheckResult(boolean paramBoolean, String paramString)
  {
    QProxyDetect.getInstance().notifyQProxyDetectResult(Boolean.valueOf(paramBoolean), paramString);
  }
  
  @CalledByNative
  public static void notifySpdyProxyFailHandler(String paramString, int paramInt)
  {
    String str = jdField_b_of_type_JavaLangString;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("notifySpdyProxyFailHandler proxyHost:");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" errorId = ");
    localStringBuilder.append(paramInt);
    X5Log.i(str, localStringBuilder.toString());
    SmttServiceProxy.getInstance().notifyQProxyFailHandler(Apn.sApnTypeS, paramString, paramInt);
  }
  
  @CalledByNative
  public static void notifyX5ErrorPageLoaded(int paramInt, String paramString1, String paramString2)
  {
    PageLoadDetector.a().a(paramInt, paramString1, paramString2);
  }
  
  @CalledByNative
  public static void onReportSmartBoxSearchVideoInfo(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    switch (paramInt)
    {
    default: 
      break;
    case 4: 
      SmttServiceProxy.getInstance().userBehaviorStatistics("CO044");
      break;
    case 3: 
      SmttServiceProxy.getInstance().userBehaviorStatistics("CO043");
      break;
    case 2: 
      SmttServiceProxy.getInstance().userBehaviorStatistics("CO042");
      break;
    case 1: 
      SmttServiceProxy.getInstance().userBehaviorStatistics("CO041");
      break;
    case 0: 
      SmttServiceProxy.getInstance().userBehaviorStatistics("CO040");
    }
    HashMap localHashMap = new HashMap();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramInt);
    localStringBuilder.append("");
    localHashMap.put("action", localStringBuilder.toString());
    localHashMap.put("guid", SmttServiceProxy.getInstance().getGUID());
    localHashMap.put("vid", paramString1);
    paramString1 = new StringBuilder();
    paramString1.append(System.currentTimeMillis());
    paramString1.append("");
    localHashMap.put("actiontime", paramString1.toString());
    localHashMap.put("docurl", paramString2);
    localHashMap.put("picurl", paramString3);
    localHashMap.put("qid", paramString4);
    SmttServiceProxy.getInstance().upLoadToBeacon(jdField_d_of_type_JavaLangString, localHashMap);
  }
  
  @CalledByNative
  public static void preConnectSubResources(String paramString)
  {
    c.a(paramString);
  }
  
  @CalledByNative
  public static void preconnectMainResources() {}
  
  @CalledByNative
  private static void recordResourceMap(Object paramObject, String paramString)
  {
    if (paramObject != null) {
      paramObject = (HashMap)paramObject;
    } else {
      paramObject = null;
    }
    Iterator localIterator = ((HashMap)paramObject).entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localEntry.getKey();
      localEntry.getValue();
    }
    if ((paramObject != null) && (((HashMap)paramObject).size() > 0)) {
      c.a(paramString, (HashMap)paramObject);
    }
  }
  
  @CalledByNative
  public static void reportDeadCode(String paramString1, String paramString2, boolean paramBoolean, String paramString3)
  {
    SmttServiceProxy.getInstance().setDeadCode(paramString1, paramString2, paramBoolean, paramString3);
  }
  
  @CalledByNative
  public static void reportDnsResolveResults(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt7)
  {
    int i = Apn.sApnTypeS;
    if (paramString3.equals("localdns")) {
      paramString3 = Apn.sCurrentDnsServer;
    }
    if (X5Log.isEnableLog())
    {
      String str = jdField_b_of_type_JavaLangString;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("reportDnsResolveResults dnsType:");
      localStringBuilder.append(paramInt1);
      localStringBuilder.append(" finalError = ");
      localStringBuilder.append(paramInt2);
      localStringBuilder.append(" osError = ");
      localStringBuilder.append(paramInt3);
      localStringBuilder.append(" httpdnsError = ");
      localStringBuilder.append(paramInt4);
      localStringBuilder.append(" dnsRequestTime = ");
      localStringBuilder.append(paramInt5);
      localStringBuilder.append(" requestId = ");
      localStringBuilder.append(paramInt6);
      localStringBuilder.append(" hostname = ");
      localStringBuilder.append(paramString1);
      localStringBuilder.append(" dnsResult = ");
      localStringBuilder.append(paramString2);
      localStringBuilder.append(" dnsServer = ");
      localStringBuilder.append(paramString3);
      localStringBuilder.append(" clientIp = ");
      localStringBuilder.append(paramString4);
      localStringBuilder.append(" resolvedIPCount = ");
      localStringBuilder.append(paramInt7);
      X5Log.d(str, localStringBuilder.toString());
    }
    SmttServiceProxy.getInstance().reportDnsResolveResults(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramString1, paramString2, paramString3, i, paramString4, paramInt7);
  }
  
  @CalledByNative
  public static void reportJsFetchResults(String paramString)
  {
    h.a().b(paramString);
  }
  
  @CalledByNative
  public static void reportMSEUrl(String paramString)
  {
    SmttServiceProxy.getInstance().reportMSEUrl(paramString);
  }
  
  @CalledByNative
  public static void reportRedirectIntercept(String paramString, int paramInt)
  {
    SmttServiceProxy.getInstance().onReportRedirectIntercept(paramString, paramInt);
  }
  
  @CalledByNative
  public static void reportSwitchSoftware(String paramString, int paramInt)
  {
    SmttServiceProxy.getInstance().setSwitchSoftwareToBeacon(paramString, paramInt);
  }
  
  @CalledByNative
  public static void reportTrafficAnomaly(int paramInt1, String paramString, int paramInt2)
  {
    String str = jdField_b_of_type_JavaLangString;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SmttService--ReportTrafficAnomaly() anomalytype:");
    localStringBuilder.append(paramInt1);
    localStringBuilder.append("url");
    localStringBuilder.append(paramString);
    localStringBuilder.append("length:");
    localStringBuilder.append(paramInt2);
    MttLog.v(str, localStringBuilder.toString());
    paramString = "";
    switch (paramInt1)
    {
    default: 
      break;
    case 1: 
      paramString = SmttResource.getString("x5_trafficTipsbyteThreshold", "string");
      break;
    case 0: 
      paramString = SmttResource.getString("x5_trafficTipsAPKintercept", "string");
    }
    SmttServiceProxy.getInstance().giveToastTips(paramString);
  }
  
  @CalledByNative
  public static void reportVibrationInfo(String paramString, int paramInt)
  {
    if (e.a().w()) {
      SmttServiceProxy.getInstance().reportVibrationInfo(paramString, paramInt);
    }
  }
  
  @CalledByNative
  public static void savePageVisitList() {}
  
  @CalledByNative
  public static String shouldAddSpecialArgumentForThisDomain(String paramString)
  {
    return WebSettingsExtension.shouldAddSpecialArgumentForThisDomain(paramString);
  }
  
  @CalledByNative
  public static boolean shouldConvertToHttpsForThisDomain(String paramString)
  {
    return SmttServiceProxy.getInstance().shouldConvertToHttpsForThisDomain(paramString);
  }
  
  @CalledByNative
  public static boolean shouldHSTSIgnore(String paramString)
  {
    return WebSettingsExtension.shouldHSTSIgnore(paramString);
  }
  
  @CalledByNative
  public static boolean shouldSupportQuicDirect(String paramString)
  {
    if (SmttServiceProxy.getInstance().isInQuicDirectBlacklist(paramString))
    {
      String str = jdField_b_of_type_JavaLangString;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("url <");
      localStringBuilder.append(paramString);
      localStringBuilder.append("> is_in_go_quic_black_list:true");
      X5Log.d(str, localStringBuilder.toString());
      return false;
    }
    return true;
  }
  
  @CalledByNative
  public static boolean shouldSupportTpgDec(String paramString)
  {
    return SmttServiceProxy.getInstance().shouldSupportTpgDec(paramString);
  }
  
  @CalledByNative
  public static void showNetExceptionTip(String paramString)
  {
    if (e.a().n()) {
      SmttServiceProxy.getInstance().showNetExceptionTip(paramString);
    }
  }
  
  @CalledByNative
  private static void updateHashMap(Object paramObject, String paramString1, String paramString2)
  {
    if (paramObject != null)
    {
      paramObject = (HashMap)paramObject;
      if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2))) {
        ((HashMap)paramObject).put(paramString1, paramString2);
      }
    }
  }
  
  @CalledByNative
  public static void updateSWlist(String[] paramArrayOfString)
  {
    if (ContextHolder.getInstance().getContext() == null) {
      return;
    }
    Object localObject2 = ContextHolder.getInstance().getContext().getCacheDir().getPath();
    Object localObject1 = localObject2;
    if (!((String)localObject2).endsWith("/"))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("/");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("service-worker.js");
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = jdField_b_of_type_JavaLangString;
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("updateSWlist:list-length");
    ((StringBuilder)localObject3).append(paramArrayOfString.length);
    ((StringBuilder)localObject3).append(";path:");
    ((StringBuilder)localObject3).append((String)localObject1);
    MttLog.v((String)localObject2, ((StringBuilder)localObject3).toString());
    localObject1 = new File((String)localObject1);
    try
    {
      localObject1 = new FileOutputStream((File)localObject1);
      localObject2 = new StringBuilder("");
      localObject3 = new Date();
      localObject3 = new SimpleDateFormat("yyyyMMddHHmm").format((Date)localObject3);
      ((StringBuilder)localObject2).append("const TIME_STAMP = '");
      ((StringBuilder)localObject2).append((String)localObject3);
      ((StringBuilder)localObject2).append("';");
      ((StringBuilder)localObject2).append("\n");
      ((StringBuilder)localObject2).append("\nthis.addEventListener('install', function(event) {");
      ((StringBuilder)localObject2).append("\n");
      ((StringBuilder)localObject2).append("\n\tvar urlsToPrefetch = [");
      int i = 0;
      while (i < paramArrayOfString.length)
      {
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("\n\t\t'");
        ((StringBuilder)localObject3).append(paramArrayOfString[i]);
        ((StringBuilder)localObject3).append("',");
        ((StringBuilder)localObject2).append(((StringBuilder)localObject3).toString());
        i += 1;
      }
      ((StringBuilder)localObject2).deleteCharAt(((StringBuilder)localObject2).length() - 1);
      ((StringBuilder)localObject2).append("\n\t\t];");
      ((StringBuilder)localObject2).append("\n\n  var cachePromises = urlsToPrefetch.map(function(urlToPrefetch) {  ");
      ((StringBuilder)localObject2).append("\n        return fetch(urlToPrefetch, {mode: 'no-cors'});");
      ((StringBuilder)localObject2).append("\n    });");
      ((StringBuilder)localObject2).append("\n\n  event.waitUntil(");
      ((StringBuilder)localObject2).append("\n      Promise.all(cachePromises)");
      ((StringBuilder)localObject2).append("\n  );");
      ((StringBuilder)localObject2).append("\n});");
      ((FileOutputStream)localObject1).write(((StringBuilder)localObject2).toString().getBytes());
      ((FileOutputStream)localObject1).close();
      return;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
    localObject1 = jdField_b_of_type_JavaLangString;
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("IOException");
    ((StringBuilder)localObject2).append(paramArrayOfString.length);
    MttLog.v((String)localObject1, ((StringBuilder)localObject2).toString());
  }
  
  static enum a
  {
    private int jdField_a_of_type_Int;
    
    static
    {
      jdField_a_of_type_ComTencentSmttNetNetworkSmttService$a = new a("HTTP2", 0, 1);
      b = new a("QUIC", 1, 2);
    }
    
    private a(int paramInt)
    {
      this.jdField_a_of_type_Int = paramInt;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\NetworkSmttService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */