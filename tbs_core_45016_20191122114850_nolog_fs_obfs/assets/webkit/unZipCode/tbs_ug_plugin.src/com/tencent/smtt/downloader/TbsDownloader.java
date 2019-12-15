package com.tencent.smtt.downloader;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.tencent.smtt.downloader.utils.FileUtil;
import com.tencent.smtt.downloader.utils.HttpUtil;
import com.tencent.smtt.downloader.utils.HttpUtil.HttpResponseListener;
import com.tencent.smtt.downloader.utils.g;
import com.tencent.smtt.downloader.utils.h;
import com.tencent.smtt.downloader.utils.i;
import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class TbsDownloader
{
  private static long jdField_a_of_type_Long = -1L;
  private static Context jdField_a_of_type_AndroidContentContext;
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  private static TbsApkDownloader jdField_a_of_type_ComTencentSmttDownloaderTbsApkDownloader;
  private static Object jdField_a_of_type_JavaLangObject = new byte[0];
  private static String jdField_a_of_type_JavaLangString;
  public static boolean a = false;
  private static String jdField_b_of_type_JavaLangString;
  static boolean jdField_b_of_type_Boolean;
  private static boolean c = false;
  private static boolean d = false;
  private static boolean e = false;
  
  public static long a()
  {
    return jdField_a_of_type_Long;
  }
  
  static String a(Context paramContext)
  {
    if (!TextUtils.isEmpty(jdField_a_of_type_JavaLangString)) {
      return jdField_a_of_type_JavaLangString;
    }
    Locale localLocale = Locale.getDefault();
    StringBuffer localStringBuffer = new StringBuffer();
    paramContext = Build.VERSION.RELEASE;
    try
    {
      str = new String(paramContext.getBytes("UTF-8"), "ISO8859-1");
      paramContext = str;
    }
    catch (Exception localException1)
    {
      String str;
      for (;;) {}
    }
    if (paramContext == null) {
      localStringBuffer.append("1.0");
    } else if (paramContext.length() > 0) {
      localStringBuffer.append(paramContext);
    } else {
      localStringBuffer.append("1.0");
    }
    localStringBuffer.append("; ");
    paramContext = localLocale.getLanguage();
    if (paramContext != null)
    {
      localStringBuffer.append(paramContext.toLowerCase());
      paramContext = localLocale.getCountry();
      if (paramContext != null)
      {
        localStringBuffer.append("-");
        localStringBuffer.append(paramContext.toLowerCase());
      }
    }
    else
    {
      localStringBuffer.append("en");
    }
    if ("REL".equals(Build.VERSION.CODENAME)) {
      paramContext = Build.MODEL;
    }
    try
    {
      str = new String(paramContext.getBytes("UTF-8"), "ISO8859-1");
      paramContext = str;
    }
    catch (Exception localException2)
    {
      for (;;) {}
    }
    if (paramContext == null)
    {
      localStringBuffer.append("; ");
    }
    else if (paramContext.length() > 0)
    {
      localStringBuffer.append("; ");
      localStringBuffer.append(paramContext);
    }
    if (Build.ID == null) {
      paramContext = "";
    } else {
      paramContext = Build.ID;
    }
    paramContext = paramContext.replaceAll("[一-龥]", "");
    if (paramContext == null)
    {
      localStringBuffer.append(" Build/");
      localStringBuffer.append("00");
    }
    else if (paramContext.length() > 0)
    {
      localStringBuffer.append(" Build/");
      localStringBuffer.append(paramContext);
    }
    paramContext = String.format("Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 Mobile Safari/533.1", new Object[] { localStringBuffer });
    jdField_a_of_type_JavaLangString = paramContext;
    return paramContext;
  }
  
  private static String a(String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    return str;
  }
  
  public static String a(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (com.tencent.smtt.downloader.utils.b.a()) {
        return "x5.tbs.decouple.64";
      }
      return "x5.tbs.decouple";
    }
    if (com.tencent.smtt.downloader.utils.b.a()) {
      return "x5.tbs.org.64";
    }
    return "x5.tbs.org";
  }
  
  private static JSONArray a(boolean paramBoolean)
  {
    return new JSONArray();
  }
  
  private static JSONObject a(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("[TbsDownloader.postJsonData]isQuery: ");
    ((StringBuilder)localObject1).append(paramBoolean1);
    ((StringBuilder)localObject1).append(" forDecoupleCore is ");
    ((StringBuilder)localObject1).append(paramBoolean3);
    h.a("TbsDownload", ((StringBuilder)localObject1).toString());
    c localc = c.a(jdField_a_of_type_AndroidContentContext);
    String str2 = a(jdField_a_of_type_AndroidContentContext);
    String str3 = com.tencent.smtt.downloader.utils.b.d(jdField_a_of_type_AndroidContentContext);
    String str4 = com.tencent.smtt.downloader.utils.b.c(jdField_a_of_type_AndroidContentContext);
    String str5 = com.tencent.smtt.downloader.utils.b.e(jdField_a_of_type_AndroidContentContext);
    Object localObject3 = "";
    String str1 = "";
    Object localObject4 = TimeZone.getDefault().getID();
    if (localObject4 != null) {
      localObject3 = localObject4;
    }
    try
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)jdField_a_of_type_AndroidContentContext.getSystemService("phone");
      localObject1 = localObject4;
      if (localTelephonyManager != null) {
        localObject1 = localTelephonyManager.getSimCountryIso();
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      localObject2 = localObject4;
    }
    localObject4 = str1;
    if (localObject2 != null) {
      localObject4 = localObject2;
    }
    Object localObject2 = new JSONObject();
    for (;;)
    {
      int i;
      try
      {
        if (b.a(jdField_a_of_type_AndroidContentContext).b("tpatch_num") < 5) {
          ((JSONObject)localObject2).put("REQUEST_TPATCH", 1);
        } else {
          ((JSONObject)localObject2).put("REQUEST_TPATCH", 0);
        }
        ((JSONObject)localObject2).put("TIMEZONEID", localObject3);
        ((JSONObject)localObject2).put("COUNTRYISO", localObject4);
        ((JSONObject)localObject2).put("REQUEST_64", 1);
        ((JSONObject)localObject2).put("PROTOCOLVERSION", 1);
        if (paramBoolean3) {
          j = e.a().a(jdField_a_of_type_AndroidContentContext);
        } else {
          j = e.a().b(jdField_a_of_type_AndroidContentContext);
        }
        i = j;
        if (j == 0)
        {
          i = j;
          if (e.a().b(jdField_a_of_type_AndroidContentContext))
          {
            j = -1;
            i = j;
            if ("com.tencent.mobileqq".equals(jdField_a_of_type_AndroidContentContext.getApplicationInfo().packageName))
            {
              TbsDownloadUpload.a();
              localObject3 = TbsDownloadUpload.a(jdField_a_of_type_AndroidContentContext);
              ((TbsDownloadUpload)localObject3).jdField_a_of_type_JavaUtilMap.put("tbs_local_core_version", Integer.valueOf(-1));
              ((TbsDownloadUpload)localObject3).c();
              i = j;
            }
          }
        }
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("[TbsDownloader.postJsonData] tbsLocalVersion=");
        ((StringBuilder)localObject3).append(i);
        ((StringBuilder)localObject3).append(" isDownloadForeground=");
        ((StringBuilder)localObject3).append(paramBoolean2);
        h.a("TbsDownload", ((StringBuilder)localObject3).toString());
        j = i;
        if (paramBoolean2)
        {
          if (!e.a().b(jdField_a_of_type_AndroidContentContext)) {
            break label930;
          }
          j = i;
        }
        if (!paramBoolean1) {
          break label936;
        }
        ((JSONObject)localObject2).put("FUNCTION", 2);
        continue;
        ((JSONObject)localObject2).put("FUNCTION", i);
        localObject3 = a(paramBoolean3);
        if ((com.tencent.smtt.downloader.utils.a.a(jdField_a_of_type_AndroidContentContext) != 3) && (((JSONArray)localObject3).length() != 0) && (j == 0) && (paramBoolean1)) {
          ((JSONObject)localObject2).put("TBSBACKUPARR", localObject3);
        }
        ((JSONObject)localObject2).put("APPN", jdField_a_of_type_AndroidContentContext.getPackageName());
        ((JSONObject)localObject2).put("APPVN", a(localc.jdField_a_of_type_AndroidContentSharedPreferences.getString("app_versionname", null)));
        ((JSONObject)localObject2).put("APPVC", localc.jdField_a_of_type_AndroidContentSharedPreferences.getInt("app_versioncode", 0));
        ((JSONObject)localObject2).put("APPMETA", a(localc.jdField_a_of_type_AndroidContentSharedPreferences.getString("app_metadata", null)));
        ((JSONObject)localObject2).put("TBSSDKV", 43999);
        ((JSONObject)localObject2).put("TBSV", j);
        if (!paramBoolean3) {
          break label951;
        }
        i = 1;
        ((JSONObject)localObject2).put("DOWNLOADDECOUPLECORE", i);
        localObject3 = localc.jdField_a_of_type_JavaUtilMap;
        if (!paramBoolean3) {
          break label956;
        }
        i = 1;
        ((Map)localObject3).put("tbs_downloaddecouplecore", Integer.valueOf(i));
        localc.a();
        if (j != 0) {
          ((JSONObject)localObject2).put("TBSBACKUPV", jdField_a_of_type_ComTencentSmttDownloaderTbsApkDownloader.a(paramBoolean3));
        }
        ((JSONObject)localObject2).put("CPU", jdField_b_of_type_JavaLangString);
        ((JSONObject)localObject2).put("UA", str2);
        ((JSONObject)localObject2).put("IMSI", a(str3));
        ((JSONObject)localObject2).put("IMEI", a(str4));
        ((JSONObject)localObject2).put("ANDROID_ID", a(str5));
        ((JSONObject)localObject2).put("GUID", com.tencent.smtt.downloader.utils.b.b(jdField_a_of_type_AndroidContentContext));
        ((JSONObject)localObject2).put("STATUS", 0);
        ((JSONObject)localObject2).put("TBSDV", 0);
        c.a(jdField_a_of_type_AndroidContentContext).jdField_a_of_type_AndroidContentSharedPreferences.getBoolean("request_full_package", false);
        ((JSONObject)localObject2).put("TBSBUV", e.a().c(jdField_a_of_type_AndroidContentContext));
        if (b(jdField_a_of_type_AndroidContentContext)) {
          ((JSONObject)localObject2).put("OVERSEA", 1);
        }
        if (paramBoolean2) {
          ((JSONObject)localObject2).put("DOWNLOAD_FOREGROUND", 1);
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[TbsDownloader.postJsonData] jsonData=");
      localStringBuilder.append(((JSONObject)localObject2).toString());
      h.a("TbsDownload", localStringBuilder.toString());
      return (JSONObject)localObject2;
      label930:
      int j = 0;
      continue;
      label936:
      if (j == 0)
      {
        i = 0;
      }
      else
      {
        i = 1;
        continue;
        label951:
        i = 0;
        continue;
        label956:
        i = 0;
      }
    }
  }
  
  public static void a()
  {
    if (c) {
      return;
    }
    h.a("TbsDownload", "[TbsDownloader.stopDownload]");
    Object localObject = jdField_a_of_type_ComTencentSmttDownloaderTbsApkDownloader;
    if (localObject != null) {
      ((TbsApkDownloader)localObject).a();
    }
    localObject = jdField_a_of_type_AndroidOsHandler;
    if (localObject != null)
    {
      ((Handler)localObject).removeMessages(100);
      jdField_a_of_type_AndroidOsHandler.removeMessages(101);
      jdField_a_of_type_AndroidOsHandler.removeMessages(108);
    }
  }
  
  public static void a(Context paramContext)
  {
    a(paramContext, false);
  }
  
  public static void a(Context paramContext, boolean paramBoolean)
  {
    for (;;)
    {
      try
      {
        TbsDownloadUpload localTbsDownloadUpload = TbsDownloadUpload.a(paramContext);
        localTbsDownloadUpload.jdField_a_of_type_JavaUtilMap.put("tbs_startdownload_code", Integer.valueOf(160));
        localTbsDownloadUpload.c();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("[TbsDownloader.startDownload] sAppContext=");
        localStringBuilder.append(jdField_a_of_type_AndroidContentContext);
        h.a("TbsDownload", localStringBuilder.toString());
        if (e.a)
        {
          localTbsDownloadUpload.jdField_a_of_type_JavaUtilMap.put("tbs_startdownload_code", Integer.valueOf(161));
          localTbsDownloadUpload.c();
          return;
        }
        i = 1;
        jdField_b_of_type_Boolean = true;
        jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
        c.a(jdField_a_of_type_AndroidContentContext).a(65336);
        if (Build.VERSION.SDK_INT < 8)
        {
          a.a.onDownloadFinish(110);
          c.a(jdField_a_of_type_AndroidContentContext).a(65335);
          localTbsDownloadUpload.jdField_a_of_type_JavaUtilMap.put("tbs_startdownload_code", Integer.valueOf(162));
          localTbsDownloadUpload.c();
          return;
        }
        b();
        if (c)
        {
          a.a.onDownloadFinish(121);
          c.a(jdField_a_of_type_AndroidContentContext).a(65334);
          localTbsDownloadUpload.jdField_a_of_type_JavaUtilMap.put("tbs_startdownload_code", Integer.valueOf(163));
          localTbsDownloadUpload.c();
          return;
        }
        if (paramBoolean) {
          a();
        }
        jdField_a_of_type_AndroidOsHandler.removeMessages(101);
        jdField_a_of_type_AndroidOsHandler.removeMessages(100);
        paramContext = Message.obtain(jdField_a_of_type_AndroidOsHandler, 101, a.a);
        if (paramBoolean)
        {
          paramContext.arg1 = i;
          paramContext.sendToTarget();
          return;
        }
      }
      finally {}
      int i = 0;
    }
  }
  
  private static void a(boolean paramBoolean1, TbsDownloaderCallback paramTbsDownloaderCallback, boolean paramBoolean2)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private static boolean a()
  {
    c localc = c.a(jdField_a_of_type_AndroidContentContext);
    if (localc.jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_success_retrytimes", 0) >= localc.a())
    {
      h.a("TbsDownload", "[TbsDownloader.needStartDownload] out of success retrytimes", true);
      localc.a(-115);
      return false;
    }
    if (localc.jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_failed_retrytimes", 0) >= localc.b())
    {
      h.a("TbsDownload", "[TbsDownloader.needStartDownload] out of failed retrytimes", true);
      localc.a(-116);
      return false;
    }
    if (!FileUtil.a(jdField_a_of_type_AndroidContentContext))
    {
      h.a("TbsDownload", "[TbsDownloader.needStartDownload] local rom freespace limit", true);
      localc.a(-117);
      return false;
    }
    if (System.currentTimeMillis() - localc.jdField_a_of_type_AndroidContentSharedPreferences.getLong("tbs_downloadstarttime", 0L) <= 86400000L)
    {
      long l = localc.jdField_a_of_type_AndroidContentSharedPreferences.getLong("tbs_downloadflow", 0L);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[TbsDownloader.needStartDownload] downloadFlow=");
      localStringBuilder.append(l);
      h.a("TbsDownload", localStringBuilder.toString());
      if (l >= localc.a())
      {
        h.a("TbsDownload", "[TbsDownloader.needStartDownload] failed because you exceeded max flow!", true);
        localc.a(-120);
        return false;
      }
    }
    return true;
  }
  
  static boolean a(Context paramContext)
  {
    return c.a(paramContext).jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_downloaddecouplecore", 0) == 1;
  }
  
  public static boolean a(Context paramContext, boolean paramBoolean)
  {
    return a(paramContext, paramBoolean, false, true, null);
  }
  
  private static boolean a(Context paramContext, boolean paramBoolean1, boolean paramBoolean2)
  {
    Object localObject = c.a(paramContext);
    if (!paramBoolean1)
    {
      paramContext = ((c)localObject).jdField_a_of_type_AndroidContentSharedPreferences.getString("app_versionname", null);
      int i = ((c)localObject).jdField_a_of_type_AndroidContentSharedPreferences.getInt("app_versioncode", 0);
      String str1 = ((c)localObject).jdField_a_of_type_AndroidContentSharedPreferences.getString("app_metadata", null);
      String str2 = com.tencent.smtt.downloader.utils.b.a(jdField_a_of_type_AndroidContentContext);
      int j = com.tencent.smtt.downloader.utils.b.a(jdField_a_of_type_AndroidContentContext);
      String str3 = com.tencent.smtt.downloader.utils.b.a(jdField_a_of_type_AndroidContentContext, "com.tencent.mm.BuildInfo.CLIENT_VERSION");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[TbsDownloader.needSendQueryRequest] appVersionName=");
      localStringBuilder.append(str2);
      localStringBuilder.append(" oldAppVersionName=");
      localStringBuilder.append(paramContext);
      localStringBuilder.append(" appVersionCode=");
      localStringBuilder.append(j);
      localStringBuilder.append(" oldAppVersionCode=");
      localStringBuilder.append(i);
      localStringBuilder.append(" appMetadata=");
      localStringBuilder.append(str3);
      localStringBuilder.append(" oldAppVersionMetadata=");
      localStringBuilder.append(str1);
      h.a("TbsDownload", localStringBuilder.toString());
      long l2 = System.currentTimeMillis();
      long l3 = ((c)localObject).jdField_a_of_type_AndroidContentSharedPreferences.getLong("last_check", 0L);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("[TbsDownloader.needSendQueryRequest] timeLastCheck=");
      localStringBuilder.append(l3);
      localStringBuilder.append(" timeNow=");
      localStringBuilder.append(l2);
      h.a("TbsDownload", localStringBuilder.toString());
      long l1 = l3;
      if (paramBoolean2)
      {
        paramBoolean1 = ((c)localObject).jdField_a_of_type_AndroidContentSharedPreferences.contains("last_check");
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("[TbsDownloader.needSendQueryRequest] hasLaskCheckKey=");
        localStringBuilder.append(paramBoolean1);
        h.a("TbsDownload", localStringBuilder.toString());
        if (paramBoolean1)
        {
          l1 = l3;
          if (l3 == 0L) {
            l1 = l2;
          }
        }
        else
        {
          l1 = l3;
        }
      }
      ((c)localObject).jdField_a_of_type_AndroidContentSharedPreferences.getLong("last_request_success", 0L);
      ((c)localObject).jdField_a_of_type_AndroidContentSharedPreferences.getLong("count_request_fail_in_24hours", 0L);
      l3 = ((c)localObject).b();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("retryInterval = ");
      ((StringBuilder)localObject).append(l3);
      ((StringBuilder)localObject).append(" s");
      h.a("TbsDownload", ((StringBuilder)localObject).toString());
      c.a(jdField_a_of_type_AndroidContentContext).jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_version", 0);
      if (l2 - l1 > l3 * 1000L) {
        return true;
      }
      return (str2 != null) && (j != 0) && (str3 != null) && ((!str2.equals(paramContext)) || (j != i) || (!str3.equals(str1)));
    }
    return true;
  }
  
  public static boolean a(Context paramContext, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, TbsDownloaderCallback paramTbsDownloaderCallback)
  {
    TbsDownloadUpload.a();
    TbsDownloadUpload localTbsDownloadUpload = TbsDownloadUpload.a(paramContext);
    localTbsDownloadUpload.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_code", Integer.valueOf(140));
    localTbsDownloadUpload.c();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[TbsDownloader.needDownload] oversea=");
    localStringBuilder.append(paramBoolean1);
    localStringBuilder.append(",isDownloadForeground=");
    localStringBuilder.append(paramBoolean2);
    h.a("TbsDownload", localStringBuilder.toString());
    h.a(paramContext);
    if (e.a)
    {
      if (paramTbsDownloaderCallback != null) {
        paramTbsDownloaderCallback.onNeedDownloadFinish(false, 0);
      }
      paramContext = new StringBuilder();
      paramContext.append("[TbsDownloader.needDownload]#1,return ");
      paramContext.append(false);
      h.a("TbsDownload", paramContext.toString());
      localTbsDownloadUpload.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_return", Integer.valueOf(171));
      localTbsDownloadUpload.c();
      return false;
    }
    h.a("TbsDownload", paramContext);
    jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
    paramContext = c.a(jdField_a_of_type_AndroidContentContext);
    paramContext.a(-100);
    if (!b(jdField_a_of_type_AndroidContentContext, paramBoolean1))
    {
      paramContext = new StringBuilder();
      paramContext.append("[TbsDownloader.needDownload]#2,return ");
      paramContext.append(false);
      h.a("TbsDownload", paramContext.toString());
      localTbsDownloadUpload.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_code", Integer.valueOf(141));
      localTbsDownloadUpload.c();
      localTbsDownloadUpload.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_return", Integer.valueOf(172));
      localTbsDownloadUpload.c();
      if (paramTbsDownloaderCallback != null) {
        paramTbsDownloaderCallback.onNeedDownloadFinish(false, 0);
      }
      return false;
    }
    b();
    if (c)
    {
      if (paramTbsDownloaderCallback != null) {
        paramTbsDownloaderCallback.onNeedDownloadFinish(false, 0);
      }
      paramContext.a(-105);
      paramContext = new StringBuilder();
      paramContext.append("[TbsDownloader.needDownload]#3,return ");
      paramContext.append(false);
      h.a("TbsDownload", paramContext.toString());
      localTbsDownloadUpload.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_code", Integer.valueOf(142));
      localTbsDownloadUpload.c();
      localTbsDownloadUpload.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_return", Integer.valueOf(173));
      localTbsDownloadUpload.c();
      if (paramTbsDownloaderCallback != null) {
        paramTbsDownloaderCallback.onNeedDownloadFinish(false, 0);
      }
      return false;
    }
    boolean bool = a(jdField_a_of_type_AndroidContentContext, paramBoolean2, false);
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("[TbsDownloader.needDownload],needSendRequest=");
    localStringBuilder.append(bool);
    h.a("TbsDownload", localStringBuilder.toString());
    if (bool)
    {
      a(paramBoolean2, paramTbsDownloaderCallback, paramBoolean3);
      paramContext.a(-114);
    }
    else
    {
      localTbsDownloadUpload.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_code", Integer.valueOf(143));
      localTbsDownloadUpload.c();
    }
    jdField_a_of_type_AndroidOsHandler.removeMessages(102);
    Message.obtain(jdField_a_of_type_AndroidOsHandler, 102).sendToTarget();
    paramBoolean2 = paramContext.jdField_a_of_type_AndroidContentSharedPreferences.contains("tbs_needdownload");
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("[TbsDownloader.needDownload] hasNeedDownloadKey=");
    localStringBuilder.append(paramBoolean2);
    h.a("TbsDownload", localStringBuilder.toString());
    if (!paramBoolean2) {
      paramBoolean1 = true;
    } else {
      paramBoolean1 = paramContext.jdField_a_of_type_AndroidContentSharedPreferences.getBoolean("tbs_needdownload", false);
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("[TbsDownloader.needDownload]#4,needDownload=");
    localStringBuilder.append(paramBoolean1);
    localStringBuilder.append(",hasNeedDownloadKey=");
    localStringBuilder.append(paramBoolean2);
    h.a("TbsDownload", localStringBuilder.toString());
    int i;
    if (paramBoolean1)
    {
      if (!a())
      {
        h.a("TbsDownload", "[TbsDownloader.needDownload]#5,set needDownload = false");
        paramBoolean1 = false;
      }
      else
      {
        paramContext.a(-118);
        h.a("TbsDownload", "[TbsDownloader.needDownload]#6");
      }
    }
    else
    {
      i = e.a().b(jdField_a_of_type_AndroidContentContext);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("[TbsDownloader.needDownload]#7,tbsLocalVersion=");
      localStringBuilder.append(i);
      localStringBuilder.append(",needSendRequest=");
      localStringBuilder.append(bool);
      h.a("TbsDownload", localStringBuilder.toString());
      if ((!bool) && (i > 0))
      {
        paramContext.a(-119);
      }
      else
      {
        jdField_a_of_type_AndroidOsHandler.removeMessages(103);
        if ((i <= 0) && (!bool)) {
          Message.obtain(jdField_a_of_type_AndroidOsHandler, 103, 0, 0, jdField_a_of_type_AndroidContentContext).sendToTarget();
        } else {
          Message.obtain(jdField_a_of_type_AndroidOsHandler, 103, 1, 0, jdField_a_of_type_AndroidContentContext).sendToTarget();
        }
        paramContext.a(-121);
      }
    }
    if ((!bool) && (paramTbsDownloaderCallback != null)) {
      paramTbsDownloaderCallback.onNeedDownloadFinish(false, 0);
    }
    paramContext = new StringBuilder();
    paramContext.append("[TbsDownloader.needDownload] needDownload=");
    paramContext.append(paramBoolean1);
    h.a("TbsDownload", paramContext.toString());
    paramContext = localTbsDownloadUpload.jdField_a_of_type_JavaUtilMap;
    if (paramBoolean1) {
      i = 170;
    } else {
      i = 174;
    }
    paramContext.put("tbs_needdownload_return", Integer.valueOf(i));
    localTbsDownloadUpload.c();
    return paramBoolean1;
  }
  
  @TargetApi(11)
  private static boolean a(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws Exception
  {
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("[TbsDownloader.readResponse] response=");
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).append("isNeedInstall=");
    ((StringBuilder)localObject1).append(paramBoolean3);
    h.a("TbsDownload", ((StringBuilder)localObject1).toString());
    localc = c.a(jdField_a_of_type_AndroidContentContext);
    if (TextUtils.isEmpty(paramString))
    {
      if (paramBoolean1) {
        localc.a(-108);
      } else {
        localc.a(65328);
      }
      h.a("TbsDownload", "[TbsDownloader.readResponse] return #1,response is empty...");
      return false;
    }
    localJSONObject = new JSONObject(paramString);
    i = localJSONObject.getInt("RET");
    if (i != 0)
    {
      if (paramBoolean1) {
        localc.a(-109);
      } else {
        localc.a(65327);
      }
      paramString = new StringBuilder();
      paramString.append("[TbsDownloader.readResponse] return #2,returnCode=");
      paramString.append(i);
      h.a("TbsDownload", paramString.toString());
      return false;
    }
    m = localJSONObject.getInt("RESPONSECODE");
    str1 = localJSONObject.getString("DOWNLOADURL");
    str2 = localJSONObject.optString("URLLIST", "");
    k = localJSONObject.getInt("TBSAPKSERVERVERSION");
    n = localJSONObject.getInt("DOWNLOADMAXFLOW");
    i1 = localJSONObject.getInt("DOWNLOAD_MIN_FREE_SPACE");
    i2 = localJSONObject.getInt("DOWNLOAD_SUCCESS_MAX_RETRYTIMES");
    i3 = localJSONObject.getInt("DOWNLOAD_FAILED_MAX_RETRYTIMES");
    l3 = localJSONObject.getLong("DOWNLOAD_SINGLE_TIMEOUT");
    l4 = localJSONObject.getLong("TBSAPKFILESIZE");
    l1 = localJSONObject.optLong("RETRY_INTERVAL", 0L);
    i4 = localJSONObject.optInt("FLOWCTR", -1);
    try
    {
      i = localJSONObject.getInt("USEBBACKUPVER");
    }
    catch (Exception localException10)
    {
      try
      {
        paramString = localJSONObject.getString("PKGMD5");
      }
      catch (Exception localException10)
      {
        try
        {
          localJSONObject.getInt("RESETX5");
          j = localJSONObject.getInt("UPLOADLOG");
          localObject4 = "";
        }
        catch (Exception localException10)
        {
          try
          {
            if (!localJSONObject.has("RESETTOKEN")) {
              break label409;
            }
            i = localJSONObject.getInt("RESETTOKEN");
            if (i == 0) {
              break label403;
            }
            i = 1;
            break label406;
            i = 0;
            break label412;
            i = 0;
            localObject1 = localObject4;
            localObject3 = localObject4;
          }
          catch (Exception localException10)
          {
            try
            {
              if (!localJSONObject.has("SETTOKEN")) {
                break label445;
              }
              localObject3 = localObject4;
              localObject1 = localJSONObject.getString("SETTOKEN");
              localObject3 = localObject1;
              if (!localJSONObject.has("ENABLE_LOAD_RENAME_FILE_LOCK")) {
                break label491;
              }
              localObject3 = localObject1;
              i5 = localJSONObject.getInt("ENABLE_LOAD_RENAME_FILE_LOCK");
              if (i5 == 0) {
                break label485;
              }
              bool1 = true;
              break label488;
              bool1 = false;
              break label494;
              bool1 = true;
            }
            catch (Exception localException10)
            {
              try
              {
                if (!localJSONObject.has("ENABLE_LOAD_RENAME_FILE_LOCK_WAIT")) {
                  break label532;
                }
                i5 = localJSONObject.getInt("ENABLE_LOAD_RENAME_FILE_LOCK_WAIT");
                if (i5 == 0) {
                  break label526;
                }
                bool2 = true;
                break label535;
                bool2 = false;
                break label535;
                bool2 = true;
                break label559;
                break label543;
                paramString = null;
                j = 0;
                localObject1 = "";
                i = 0;
                bool1 = true;
                bool2 = true;
              }
              catch (Exception localException10)
              {
                try
                {
                  localJSONObject.getInt("RESETDECOUPLECORE");
                }
                catch (Exception localException10)
                {
                  try
                  {
                    for (;;)
                    {
                      Object localObject4;
                      boolean bool1;
                      boolean bool2;
                      localJSONObject.getInt("RESETTODECOUPLECORE");
                      Object localObject3 = jdField_a_of_type_JavaLangObject;
                      if (i == 0) {
                        break label2361;
                      }
                      for (;;)
                      {
                        long l2;
                        try
                        {
                          localc.jdField_a_of_type_JavaUtilMap.put("tbs_deskey_token", "");
                          if ((!TextUtils.isEmpty((CharSequence)localObject1)) && (((String)localObject1).length() == 96))
                          {
                            localObject4 = new StringBuilder();
                            ((StringBuilder)localObject4).append((String)localObject1);
                            ((StringBuilder)localObject4).append("&");
                            ((StringBuilder)localObject4).append(com.tencent.smtt.downloader.utils.e.b());
                            localObject1 = ((StringBuilder)localObject4).toString();
                            localc.jdField_a_of_type_JavaUtilMap.put("tbs_deskey_token", localObject1);
                          }
                          if (!bool1) {
                            localc.a(bool1);
                          }
                          if (!bool2) {
                            localc.b(bool2);
                          }
                          if (j == 1)
                          {
                            jdField_a_of_type_AndroidOsHandler.removeMessages(104);
                            Message.obtain(jdField_a_of_type_AndroidOsHandler, 104).sendToTarget();
                          }
                          i5 = e.a().c(jdField_a_of_type_AndroidContentContext);
                        }
                        finally {}
                        try
                        {
                          i = localJSONObject.optInt("SWITCHBUCORE", 0);
                          if (i != 0) {
                            i = 1;
                          } else {
                            i = 0;
                          }
                        }
                        catch (Exception localException4)
                        {
                          Object localObject2;
                          continue;
                        }
                        try
                        {
                          j = localJSONObject.optInt("SERVERBUV", 0);
                        }
                        catch (Exception localException5)
                        {
                          continue;
                        }
                        try
                        {
                          localObject1 = localJSONObject.getString("BUDELFILELIST");
                          localc.jdField_a_of_type_JavaUtilMap.put("backupcore_delfilelist", localObject1);
                        }
                        catch (Exception localException6) {}
                      }
                      i = 0;
                      int j = 0;
                      if ((j != 0) && (j == paramInt) && (i5 != j))
                      {
                        jdField_a_of_type_AndroidOsHandler.removeMessages(105);
                        Message.obtain(jdField_a_of_type_AndroidOsHandler, 105).sendToTarget();
                      }
                      if (j == -1)
                      {
                        jdField_a_of_type_AndroidOsHandler.removeMessages(107);
                        Message.obtain(jdField_a_of_type_AndroidOsHandler, 107).sendToTarget();
                      }
                      if (i != 0)
                      {
                        jdField_a_of_type_AndroidOsHandler.removeMessages(106);
                        Message.obtain(jdField_a_of_type_AndroidOsHandler, 106).sendToTarget();
                      }
                      if (i4 == 1)
                      {
                        l2 = 604800L;
                        if (l1 > 604800L) {
                          l1 = l2;
                        }
                        if (l1 > 0L) {}
                      }
                      else
                      {
                        l1 = 86400L;
                      }
                      if (a() >= 0L) {
                        l1 = a();
                      }
                      localc.jdField_a_of_type_JavaUtilMap.put("retry_interval", Long.valueOf(l1));
                      if (paramBoolean1) {}
                      try
                      {
                        i = localJSONObject.getInt("DECOUPLECOREVERSION");
                      }
                      catch (Exception localException7)
                      {
                        for (;;) {}
                      }
                      i = c.a(jdField_a_of_type_AndroidContentContext).jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_decouplecoreversion", 0);
                      break label1028;
                      i = 0;
                      try
                      {
                        j = c.a(jdField_a_of_type_AndroidContentContext).jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_downloaddecouplecore", 0);
                      }
                      catch (Exception localException8)
                      {
                        for (;;) {}
                      }
                      j = 0;
                      localObject1 = new StringBuilder();
                      ((StringBuilder)localObject1).append("in response decoupleCoreVersion is ");
                      ((StringBuilder)localObject1).append(i);
                      h.a("TbsDownload", ((StringBuilder)localObject1).toString());
                      localc.jdField_a_of_type_JavaUtilMap.put("tbs_decouplecoreversion", Integer.valueOf(i));
                      localc.jdField_a_of_type_JavaUtilMap.put("tbs_downloaddecouplecore", Integer.valueOf(j));
                      TextUtils.isEmpty(str1);
                      localObject1 = new StringBuilder();
                      ((StringBuilder)localObject1).append("in response responseCode is ");
                      ((StringBuilder)localObject1).append(m);
                      h.a("TbsDownload", ((StringBuilder)localObject1).toString());
                      if (m == 0)
                      {
                        localc.jdField_a_of_type_JavaUtilMap.put("tbs_responsecode", Integer.valueOf(m));
                        localc.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload", Boolean.valueOf(false));
                        if (paramBoolean1)
                        {
                          localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_interrupt_code_reason", Integer.valueOf(-111));
                        }
                        else
                        {
                          localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_interrupt_code_reason", Integer.valueOf(65325));
                          localc.a(65325);
                        }
                        localc.a();
                        h.a("TbsDownload", "[TbsDownloader.readResponse] return #5,responseCode=0");
                        return false;
                      }
                      i4 = c.a(jdField_a_of_type_AndroidContentContext).jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_version", 0);
                      if (i4 > k)
                      {
                        jdField_a_of_type_ComTencentSmttDownloaderTbsApkDownloader.b();
                        e.a().a(jdField_a_of_type_AndroidContentContext);
                      }
                      int i5 = e.a().a(jdField_a_of_type_AndroidContentContext, 0);
                      if (i5 >= k) {
                        i = 1;
                      } else {
                        i = 0;
                      }
                      localObject1 = new StringBuilder();
                      ((StringBuilder)localObject1).append("tmpCoreVersion is ");
                      ((StringBuilder)localObject1).append(i5);
                      ((StringBuilder)localObject1).append(" tbsDownloadVersion is");
                      ((StringBuilder)localObject1).append(k);
                      h.a("TbsDownload", ((StringBuilder)localObject1).toString());
                      if (((paramInt >= k) || (TextUtils.isEmpty(str1)) || (i != 0)) && (j != 1))
                      {
                        localc.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload", Boolean.valueOf(false));
                        if (paramBoolean1)
                        {
                          if (TextUtils.isEmpty(str1)) {
                            localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_interrupt_code_reason", Integer.valueOf(-124));
                          } else if (k <= 0) {
                            localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_interrupt_code_reason", Integer.valueOf(-125));
                          } else if (paramInt >= k) {
                            localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_interrupt_code_reason", Integer.valueOf(-127));
                          } else {
                            localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_interrupt_code_reason", Integer.valueOf(-112));
                          }
                        }
                        else
                        {
                          i = 65324;
                          if (TextUtils.isEmpty(str1)) {
                            i = 65319;
                          } else if (k <= 0) {
                            i = 65318;
                          } else if (paramInt >= k) {
                            i = 65317;
                          }
                          localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_interrupt_code_reason", Integer.valueOf(i));
                          localc.a(i);
                        }
                        localc.a();
                        paramString = new StringBuilder();
                        paramString.append("version error or downloadUrl empty ,return ahead tbsLocalVersion=");
                        paramString.append(paramInt);
                        paramString.append(" tbsDownloadVersion=");
                        paramString.append(k);
                        paramString.append(" tbsLastDownloadVersion=");
                        paramString.append(i4);
                        paramString.append(" downloadUrl=");
                        paramString.append(str1);
                        h.a("TbsDownload", paramString.toString());
                        return false;
                      }
                      if (!str1.equals(localc.jdField_a_of_type_AndroidContentSharedPreferences.getString("tbs_downloadurl", null)))
                      {
                        jdField_a_of_type_ComTencentSmttDownloaderTbsApkDownloader.b();
                        localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_failed_retrytimes", Integer.valueOf(0));
                        localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_success_retrytimes", Integer.valueOf(0));
                      }
                      localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_version", Integer.valueOf(k));
                      localObject1 = new StringBuilder();
                      ((StringBuilder)localObject1).append("put KEY_TBS_DOWNLOAD_V is ");
                      ((StringBuilder)localObject1).append(k);
                      h.a("TbsDownload", ((StringBuilder)localObject1).toString());
                      if (k > 0)
                      {
                        if (j == 1) {
                          localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_version_type", Integer.valueOf(1));
                        } else {
                          localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_version_type", Integer.valueOf(0));
                        }
                        localObject1 = new StringBuilder();
                        ((StringBuilder)localObject1).append("put KEY_TBS_DOWNLOAD_V_TYPE is ");
                        ((StringBuilder)localObject1).append(j);
                        h.a("TbsDownload", ((StringBuilder)localObject1).toString());
                      }
                      localc.jdField_a_of_type_JavaUtilMap.put("tbs_downloadurl", str1);
                      localc.jdField_a_of_type_JavaUtilMap.put("tbs_downloadurl_list", str2);
                      localc.jdField_a_of_type_JavaUtilMap.put("tbs_responsecode", Integer.valueOf(m));
                      localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_maxflow", Integer.valueOf(n));
                      localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_min_free_space", Integer.valueOf(i1));
                      localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_success_max_retrytimes", Integer.valueOf(i2));
                      localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_failed_max_retrytimes", Integer.valueOf(i3));
                      localc.jdField_a_of_type_JavaUtilMap.put("tbs_single_timeout", Long.valueOf(l3));
                      localc.jdField_a_of_type_JavaUtilMap.put("tbs_apkfilesize", Long.valueOf(l4));
                      localc.a();
                      if (paramString != null) {
                        localc.jdField_a_of_type_JavaUtilMap.put("tbs_apk_md5", paramString);
                      }
                      if ((!paramBoolean2) && (paramBoolean3) && (e.a().a(jdField_a_of_type_AndroidContentContext, k)))
                      {
                        if (paramBoolean1)
                        {
                          localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_interrupt_code_reason", Integer.valueOf(-113));
                        }
                        else
                        {
                          localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_interrupt_code_reason", Integer.valueOf(65323));
                          localc.a(65323);
                        }
                        localc.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload", Boolean.valueOf(false));
                        h.a("TbsDownload", "[TbsDownloader.readResponse] ##6 set needDownload=false");
                      }
                      else
                      {
                        if (!paramBoolean1) {
                          localc.a(65320);
                        }
                        localc.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload", Boolean.valueOf(true));
                        h.a("TbsDownload", "[TbsDownloader.readResponse] ##9 set needDownload=true");
                      }
                      if (localJSONObject.optInt("stop_pre_oat", 0) == 1) {
                        localc.jdField_a_of_type_JavaUtilMap.put("tbs_stop_preoat", Boolean.valueOf(true));
                      }
                      localc.a();
                      return true;
                      paramString = paramString;
                      continue;
                      paramString = paramString;
                      continue;
                      localException1 = localException1;
                      continue;
                      localException2 = localException2;
                      continue;
                      localException3 = localException3;
                      localObject2 = localObject3;
                      continue;
                      localException9 = localException9;
                      continue;
                      localException10 = localException10;
                    }
                  }
                  catch (Exception localException11)
                  {
                    for (;;) {}
                  }
                }
              }
            }
          }
        }
      }
    }
    i = 0;
    localc.jdField_a_of_type_JavaUtilMap.put("use_backup_version", Integer.valueOf(i));
  }
  
  private static void b()
  {
    for (;;)
    {
      try
      {
        if (jdField_a_of_type_AndroidOsHandlerThread == null) {
          jdField_a_of_type_AndroidOsHandlerThread = d.a();
        }
      }
      finally {}
      try
      {
        jdField_a_of_type_ComTencentSmttDownloaderTbsApkDownloader = new TbsApkDownloader(jdField_a_of_type_AndroidContentContext);
        jdField_a_of_type_AndroidOsHandler = new Handler(jdField_a_of_type_AndroidOsHandlerThread.getLooper())
        {
          public void handleMessage(Message paramAnonymousMessage)
          {
            int i = paramAnonymousMessage.what;
            boolean bool3 = true;
            boolean bool1;
            Object localObject;
            switch (i)
            {
            default: 
              
            case 104: 
              h.a("TbsDownload", "[TbsDownloader.handleMessage] MSG_UPLOAD_TBSLOG");
              return;
            case 103: 
              h.a("TbsDownload", "[TbsDownloader.handleMessage] MSG_CONTINUEINSTALL_TBSCORE");
              return;
            case 102: 
              h.a("TbsDownload", "[TbsDownloader.handleMessage] MSG_REPORT_DOWNLOAD_STAT");
              i = e.a().b(TbsDownloader.a());
              paramAnonymousMessage = new StringBuilder();
              paramAnonymousMessage.append("[TbsDownloader.handleMessage] localTbsVersion=");
              paramAnonymousMessage.append(i);
              h.a("TbsDownload", paramAnonymousMessage.toString());
              TbsDownloader.a().a(i);
              i.a(TbsDownloader.a()).a();
              return;
            case 101: 
            case 108: 
              if (paramAnonymousMessage.arg1 == 1) {
                bool1 = true;
              } else {
                bool1 = false;
              }
              localObject = c.a(TbsDownloader.a());
              boolean bool2;
              if (108 == paramAnonymousMessage.what) {
                bool2 = true;
              } else {
                bool2 = false;
              }
              if (TbsDownloader.a(false, bool1, bool2, true))
              {
                if ((bool1) && (e.a().a(TbsDownloader.a(), c.a(TbsDownloader.a()).a.getInt("tbs_download_version", 0))))
                {
                  a.a.onDownloadFinish(122);
                  ((c)localObject).a(65323);
                }
                else if (((c)localObject).a.getBoolean("tbs_needdownload", false))
                {
                  c.a(TbsDownloader.a()).a(65321);
                  localObject = TbsDownloader.a();
                  if (108 == paramAnonymousMessage.what) {
                    bool2 = bool3;
                  } else {
                    bool2 = false;
                  }
                  ((TbsApkDownloader)localObject).a(bool1, bool2);
                }
                else
                {
                  a.a.onDownloadFinish(110);
                }
              }
              else {
                a.a.onDownloadFinish(110);
              }
              h.a("TbsDownload", "------freeFileLock called :");
              FileUtil.a(null, null);
              return;
            case 100: 
              if (paramAnonymousMessage.arg1 == 1) {
                i = 1;
              } else {
                i = 0;
              }
              if (paramAnonymousMessage.arg2 == 1) {
                bool1 = true;
              } else {
                bool1 = false;
              }
              bool1 = TbsDownloader.a(true, false, false, bool1);
              if ((paramAnonymousMessage.obj != null) && ((paramAnonymousMessage.obj instanceof TbsDownloader.TbsDownloaderCallback)))
              {
                localObject = new StringBuilder();
                ((StringBuilder)localObject).append("needDownload-onNeedDownloadFinish needStartDownload=");
                ((StringBuilder)localObject).append(bool1);
                h.a("TbsDownload", ((StringBuilder)localObject).toString());
                String str = "";
                localObject = str;
                if (TbsDownloader.a() != null)
                {
                  localObject = str;
                  if (TbsDownloader.a().getApplicationContext() != null)
                  {
                    localObject = str;
                    if (TbsDownloader.a().getApplicationContext().getApplicationInfo() != null) {
                      localObject = TbsDownloader.a().getApplicationContext().getApplicationInfo().packageName;
                    }
                  }
                }
                if ((bool1) && (i == 0))
                {
                  if (("com.tencent.mm".equals(localObject)) || ("com.tencent.mobileqq".equals(localObject)))
                  {
                    localObject = new StringBuilder();
                    ((StringBuilder)localObject).append("needDownload-onNeedDownloadFinish in mm or QQ callback needStartDownload = ");
                    ((StringBuilder)localObject).append(bool1);
                    h.a("TbsDownload", ((StringBuilder)localObject).toString());
                    ((TbsDownloader.TbsDownloaderCallback)paramAnonymousMessage.obj).onNeedDownloadFinish(bool1, c.a(TbsDownloader.a()).a.getInt("tbs_download_version", 0));
                  }
                }
                else {
                  ((TbsDownloader.TbsDownloaderCallback)paramAnonymousMessage.obj).onNeedDownloadFinish(bool1, c.a(TbsDownloader.a()).a.getInt("tbs_download_version", 0));
                }
              }
              break;
            }
          }
        };
      }
      catch (Exception localException) {}
    }
    c = true;
    h.b("TbsDownload", "TbsApkDownloader init has Exception");
    return;
  }
  
  public static boolean b(Context paramContext)
  {
    try
    {
      if (!e)
      {
        e = true;
        paramContext = c.a(paramContext);
        if (paramContext.jdField_a_of_type_AndroidContentSharedPreferences.contains("is_oversea"))
        {
          d = paramContext.jdField_a_of_type_AndroidContentSharedPreferences.getBoolean("is_oversea", false);
          paramContext = new StringBuilder();
          paramContext.append("[TbsDownloader.getOverSea]  first called. sOverSea = ");
          paramContext.append(d);
          h.a("TbsDownload", paramContext.toString());
        }
        paramContext = new StringBuilder();
        paramContext.append("[TbsDownloader.getOverSea]  sOverSea = ");
        paramContext.append(d);
        h.a("TbsDownload", paramContext.toString());
      }
      boolean bool = d;
      return bool;
    }
    finally {}
  }
  
  private static boolean b(Context paramContext, boolean paramBoolean)
  {
    c localc = c.a(paramContext);
    if (Build.VERSION.SDK_INT < 8)
    {
      localc.a(-102);
      return false;
    }
    if (!localc.jdField_a_of_type_AndroidContentSharedPreferences.contains("is_oversea"))
    {
      boolean bool = paramBoolean;
      if (paramBoolean)
      {
        bool = paramBoolean;
        if (!"com.tencent.mm".equals(paramContext.getApplicationInfo().packageName))
        {
          h.a("TbsDownload", "needDownload-oversea is true, but not WX");
          bool = false;
        }
      }
      localc.jdField_a_of_type_JavaUtilMap.put("is_oversea", Boolean.valueOf(bool));
      localc.a();
      d = bool;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("needDownload-first-called--isoversea = ");
      ((StringBuilder)localObject).append(bool);
      h.a("TbsDownload", ((StringBuilder)localObject).toString());
    }
    if ((b(paramContext)) && (Build.VERSION.SDK_INT != 16) && (Build.VERSION.SDK_INT != 17) && (Build.VERSION.SDK_INT != 18))
    {
      paramContext = new StringBuilder();
      paramContext.append("needDownload- return false,  because of  version is ");
      paramContext.append(Build.VERSION.SDK_INT);
      paramContext.append(", and overea");
      h.a("TbsDownload", paramContext.toString());
      localc.a(-103);
      return false;
    }
    Object localObject = localc.jdField_a_of_type_AndroidContentSharedPreferences;
    paramContext = null;
    jdField_b_of_type_JavaLangString = ((SharedPreferences)localObject).getString("device_cpuabi", null);
    if (!TextUtils.isEmpty(jdField_b_of_type_JavaLangString)) {}
    try
    {
      localObject = Pattern.compile("i686|mips|x86_64").matcher(jdField_b_of_type_JavaLangString);
      paramContext = (Context)localObject;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    if ((paramContext != null) && (paramContext.find()))
    {
      h.b("TbsDownload", "can not support x86 devices!!");
      localc.a(-104);
      return false;
    }
    return true;
  }
  
  private static boolean b(final boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    if (paramBoolean1)
    {
      localObject1 = TbsDownloadUpload.a(jdField_a_of_type_AndroidContentContext);
      ((TbsDownloadUpload)localObject1).jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_code", Integer.valueOf(144));
      ((TbsDownloadUpload)localObject1).c();
    }
    else if (!paramBoolean3)
    {
      localObject1 = TbsDownloadUpload.a(jdField_a_of_type_AndroidContentContext);
      ((TbsDownloadUpload)localObject1).jdField_a_of_type_JavaUtilMap.put("tbs_startdownload_code", Integer.valueOf(164));
      ((TbsDownloadUpload)localObject1).c();
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("[TbsDownloader.sendRequest]isQuery: ");
    ((StringBuilder)localObject1).append(paramBoolean1);
    ((StringBuilder)localObject1).append(" forDecoupleCore is ");
    ((StringBuilder)localObject1).append(paramBoolean3);
    h.a("TbsDownload", ((StringBuilder)localObject1).toString());
    if (e.a().a(jdField_a_of_type_AndroidContentContext))
    {
      h.a("TbsDownload", "[TbsDownloader.sendRequest] -- isTbsLocalInstalled!");
      if (paramBoolean1)
      {
        localObject1 = TbsDownloadUpload.a(jdField_a_of_type_AndroidContentContext);
        ((TbsDownloadUpload)localObject1).jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_code", Integer.valueOf(146));
        ((TbsDownloadUpload)localObject1).c();
        return false;
      }
      if (!paramBoolean3)
      {
        localObject1 = TbsDownloadUpload.a(jdField_a_of_type_AndroidContentContext);
        ((TbsDownloadUpload)localObject1).jdField_a_of_type_JavaUtilMap.put("tbs_startdownload_code", Integer.valueOf(166));
        ((TbsDownloadUpload)localObject1).c();
      }
      return false;
    }
    c localc = c.a(jdField_a_of_type_AndroidContentContext);
    Object localObject2 = FileUtil.a(jdField_a_of_type_AndroidContentContext, 1);
    if (b(jdField_a_of_type_AndroidContentContext)) {
      localObject1 = "x5.oversea.tbs.org";
    } else {
      localObject1 = a(false);
    }
    localObject2 = new File((String)localObject2, (String)localObject1);
    Object localObject3 = FileUtil.a(jdField_a_of_type_AndroidContentContext, 2);
    if (b(jdField_a_of_type_AndroidContentContext)) {
      localObject1 = "x5.oversea.tbs.org";
    } else {
      localObject1 = a(false);
    }
    localObject3 = new File((String)localObject3, (String)localObject1);
    Object localObject4 = FileUtil.a(jdField_a_of_type_AndroidContentContext, 3);
    if (b(jdField_a_of_type_AndroidContentContext)) {
      localObject1 = "x5.oversea.tbs.org";
    } else {
      localObject1 = a(false);
    }
    localObject4 = new File((String)localObject4, (String)localObject1);
    String str = FileUtil.a(jdField_a_of_type_AndroidContentContext, 4);
    if (b(jdField_a_of_type_AndroidContentContext)) {
      localObject1 = "x5.oversea.tbs.org";
    } else {
      localObject1 = a(false);
    }
    localObject1 = new File(str, (String)localObject1);
    if (!((File)localObject1).exists()) {
      if (((File)localObject4).exists()) {
        ((File)localObject4).renameTo((File)localObject1);
      } else if (((File)localObject3).exists()) {
        ((File)localObject3).renameTo((File)localObject1);
      } else if (((File)localObject2).exists()) {
        ((File)localObject2).renameTo((File)localObject1);
      }
    }
    if (jdField_b_of_type_JavaLangString == null)
    {
      jdField_b_of_type_JavaLangString = com.tencent.smtt.downloader.utils.b.a();
      localc.jdField_a_of_type_JavaUtilMap.put("device_cpuabi", jdField_b_of_type_JavaLangString);
      localc.a();
    }
    if (!TextUtils.isEmpty(jdField_b_of_type_JavaLangString)) {
      localObject1 = null;
    }
    try
    {
      localObject2 = Pattern.compile("i686|mips|x86_64").matcher(jdField_b_of_type_JavaLangString);
      localObject1 = localObject2;
    }
    catch (Exception localException1)
    {
      int i;
      for (;;) {}
    }
    if ((localObject1 != null) && (((Matcher)localObject1).find()))
    {
      if (paramBoolean1) {
        localc.a(-104);
      } else {
        localc.a(65331);
      }
      i = 1;
    }
    else
    {
      i = 0;
    }
    localc.jdField_a_of_type_JavaUtilMap.put("app_versionname", com.tencent.smtt.downloader.utils.b.a(jdField_a_of_type_AndroidContentContext));
    localc.jdField_a_of_type_JavaUtilMap.put("app_versioncode", Integer.valueOf(com.tencent.smtt.downloader.utils.b.a(jdField_a_of_type_AndroidContentContext)));
    localc.a();
    localObject1 = a(paramBoolean1, paramBoolean2, paramBoolean3);
    try
    {
      j = ((JSONObject)localObject1).getInt("TBSV");
    }
    catch (Exception localException2)
    {
      int j;
      long l;
      for (;;) {}
    }
    j = -1;
    if ((i != 0) || (j != -1))
    {
      l = System.currentTimeMillis();
      localc.jdField_a_of_type_JavaUtilMap.put("request_fail", Long.valueOf(l));
      localc.jdField_a_of_type_JavaUtilMap.put("app_versionname", com.tencent.smtt.downloader.utils.b.a(jdField_a_of_type_AndroidContentContext));
      localc.jdField_a_of_type_JavaUtilMap.put("app_versioncode", Integer.valueOf(com.tencent.smtt.downloader.utils.b.a(jdField_a_of_type_AndroidContentContext)));
      localc.jdField_a_of_type_JavaUtilMap.put("app_metadata", com.tencent.smtt.downloader.utils.b.a(jdField_a_of_type_AndroidContentContext, "com.tencent.mm.BuildInfo.CLIENT_VERSION"));
      localc.a();
      if (i != 0)
      {
        if (paramBoolean1)
        {
          localObject1 = TbsDownloadUpload.a(jdField_a_of_type_AndroidContentContext);
          ((TbsDownloadUpload)localObject1).jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_code", Integer.valueOf(147));
          ((TbsDownloadUpload)localObject1).c();
          return false;
        }
        if (!paramBoolean3)
        {
          localObject1 = TbsDownloadUpload.a(jdField_a_of_type_AndroidContentContext);
          ((TbsDownloadUpload)localObject1).jdField_a_of_type_JavaUtilMap.put("tbs_startdownload_code", Integer.valueOf(167));
          ((TbsDownloadUpload)localObject1).c();
        }
        return false;
      }
    }
    if ((j == -1) && (!paramBoolean3))
    {
      if (paramBoolean1)
      {
        localObject1 = TbsDownloadUpload.a(jdField_a_of_type_AndroidContentContext);
        ((TbsDownloadUpload)localObject1).jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_code", Integer.valueOf(149));
        ((TbsDownloadUpload)localObject1).c();
        return false;
      }
      if (!paramBoolean3)
      {
        localObject1 = TbsDownloadUpload.a(jdField_a_of_type_AndroidContentContext);
        ((TbsDownloadUpload)localObject1).jdField_a_of_type_JavaUtilMap.put("tbs_startdownload_code", Integer.valueOf(169));
        ((TbsDownloadUpload)localObject1).c();
        return false;
      }
    }
    else
    {
      try
      {
        localObject2 = g.a(jdField_a_of_type_AndroidContentContext).a();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("[TbsDownloader.sendRequest] postUrl=");
        ((StringBuilder)localObject3).append((String)localObject2);
        h.a("TbsDownload", ((StringBuilder)localObject3).toString());
        if (paramBoolean1)
        {
          localObject3 = TbsDownloadUpload.a(jdField_a_of_type_AndroidContentContext);
          ((TbsDownloadUpload)localObject3).jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_code", Integer.valueOf(148));
          ((TbsDownloadUpload)localObject3).jdField_a_of_type_JavaUtilMap.put("tbs_needdownload_sent", Integer.valueOf(1));
          ((TbsDownloadUpload)localObject3).c();
          h.a("TbsDownload", "sendRequest query 148");
        }
        else if (!paramBoolean3)
        {
          localObject3 = TbsDownloadUpload.a(jdField_a_of_type_AndroidContentContext);
          ((TbsDownloadUpload)localObject3).jdField_a_of_type_JavaUtilMap.put("tbs_startdownload_code", Integer.valueOf(168));
          ((TbsDownloadUpload)localObject3).jdField_a_of_type_JavaUtilMap.put("tbs_startdownload_sent", Integer.valueOf(1));
          ((TbsDownloadUpload)localObject3).c();
          h.a("TbsDownload", "sendRequest download 168");
        }
        paramBoolean2 = a(HttpUtil.a((String)localObject2, ((JSONObject)localObject1).toString().getBytes("utf-8"), new HttpUtil.HttpResponseListener()
        {
          public void onHttpResponseCode(int paramAnonymousInt)
          {
            long l = System.currentTimeMillis();
            this.jdField_a_of_type_ComTencentSmttDownloaderC.a.put("last_check", Long.valueOf(l));
            this.jdField_a_of_type_ComTencentSmttDownloaderC.a();
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("[TbsDownloader.sendRequest] httpResponseCode=");
            localStringBuilder.append(paramAnonymousInt);
            h.a("TbsDownload", localStringBuilder.toString());
            if (paramAnonymousInt >= 300)
            {
              if (paramBoolean1)
              {
                this.jdField_a_of_type_ComTencentSmttDownloaderC.a(-107);
                return;
              }
              this.jdField_a_of_type_ComTencentSmttDownloaderC.a(65329);
            }
          }
        }, false), j, paramBoolean1, paramBoolean2, paramBoolean4);
        return paramBoolean2;
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
        if (paramBoolean1)
        {
          localc.a(-106);
          return false;
        }
        localc.a(65330);
      }
    }
    return false;
  }
  
  public static abstract interface TbsDownloaderCallback
  {
    public abstract void onNeedDownloadFinish(boolean paramBoolean, int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\TbsDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */