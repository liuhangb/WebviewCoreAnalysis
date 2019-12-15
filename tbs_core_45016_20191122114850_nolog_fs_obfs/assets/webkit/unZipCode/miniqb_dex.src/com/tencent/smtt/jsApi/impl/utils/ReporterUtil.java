package com.tencent.smtt.jsApi.impl.utils;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.common.utils.TbsMode;
import com.tencent.common.utils.UrlUtils;
import com.tencent.mtt.base.task.NetworkTask;
import com.tencent.smtt.jsApi.impl.OpenJsHelper;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.QBUrlUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import com.tencent.tbs.common.utils.URLUtil;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class ReporterUtil
{
  public static final String DOWNLOAD_ABORT = "6";
  public static final String DOWNLOAD_CANCEL = "4";
  public static final String DOWNLOAD_END = "1";
  public static final String DOWNLOAD_FAILED = "5";
  public static final String DOWNLOAD_PAUSE = "3";
  public static final String DOWNLOAD_RETRY = "9";
  public static final String DOWNLOAD_START = "0";
  public static final String DOWNLOAD_UNSTART = "10";
  public static final String INSTALL_FAILED = "8";
  public static final String INSTALL_FINISH = "2";
  public static final String INSTALL_START = "7";
  public static String REINSTALL_CLICK = "5";
  public static String REINSTALL_CLOSE = "4";
  public static String REINSTALL_EXEC_JS = "2";
  public static String REINSTALL_INSTALLED = "6";
  public static String REINSTALL_LOAD_JS = "1";
  public static String REINSTALL_SHOW = "3";
  private static volatile ReporterUtil jdField_a_of_type_ComTencentSmttJsApiImplUtilsReporterUtil;
  private Map<String, Map<String, ReportInfo>> jdField_a_of_type_JavaUtilMap = new HashMap();
  private boolean jdField_a_of_type_Boolean = false;
  
  private ReporterUtil()
  {
    String str = PublicSettingManager.getInstance().getCloudStringSwitch("TBS_JS_DOWNLOAD_STATS_RATIO_CONTROL", "[0,10000]");
    this.jdField_a_of_type_Boolean = PublicSettingManager.getInstance().satisfyRatioControl(str);
  }
  
  private static String a()
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(Long.valueOf(System.currentTimeMillis()));
  }
  
  private String a(OpenJsHelper paramOpenJsHelper)
  {
    paramOpenJsHelper = paramOpenJsHelper.getHistoryUrl(-1);
    if (!TextUtils.isEmpty(paramOpenJsHelper)) {
      return UrlUtils.getHost(paramOpenJsHelper);
    }
    return null;
  }
  
  private static String a(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("(^|&)");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("=([^&]*)(&|$)");
    paramString1 = Pattern.compile(localStringBuilder.toString()).matcher(paramString1);
    if (paramString1.find()) {
      return paramString1.group(0).split("=")[1].replace("&", "");
    }
    return null;
  }
  
  private void a(Context paramContext, String paramString1, ReportInfo paramReportInfo, String paramString2, String paramString3, String paramString4)
  {
    if (paramReportInfo == null) {
      return;
    }
    NetworkTask localNetworkTask;
    if ((URLUtil.isHttpUrl(paramReportInfo.reportUrl)) || (URLUtil.isHttpsUrl(paramReportInfo.reportUrl))) {
      localNetworkTask = new NetworkTask(paramReportInfo.reportUrl);
    }
    try
    {
      if (QBUrlUtils.isQQDomain(paramReportInfo.reportUrl, false))
      {
        String str1 = GUIDFactory.getInstance().getStrGuid();
        String str2 = TbsInfoUtils.getQUA2();
        localNetworkTask.addHeader("Q-GUID", str1);
        localNetworkTask.addHeader("Q-UA2", str2);
      }
      localNetworkTask.start();
      if (!this.jdField_a_of_type_Boolean) {
        return;
      }
      a(paramContext, paramReportInfo.pageUrl, paramString1, paramString2, paramString3, paramString4, null, paramReportInfo.refer);
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  private void a(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if ((this.jdField_a_of_type_Boolean) && (paramString1 != null)) {
      if (paramString3 == null) {
        return;
      }
    }
    try
    {
      if (this.jdField_a_of_type_JavaUtilMap.containsKey(paramString1))
      {
        Map localMap = (Map)this.jdField_a_of_type_JavaUtilMap.get(paramString1);
        if (localMap.get(paramString3) == null) {
          return;
        }
        a(paramContext, ((ReportInfo)localMap.get(paramString3)).pageUrl, paramString1, paramString2, null, paramString4, paramString5, ((ReportInfo)localMap.get(paramString3)).refer);
      }
      return;
    }
    catch (Exception paramContext) {}
    return;
  }
  
  private static void a(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("type", paramString3);
    localHashMap.put("eventTime", a());
    StringBuilder localStringBuilder = new StringBuilder(paramString1);
    paramString3 = localStringBuilder;
    if (!TextUtils.isEmpty(paramString1))
    {
      int i = paramString1.indexOf('?');
      String str1 = paramString1.substring(i + 1);
      paramString3 = localStringBuilder;
      if (paramString1.contains("ag.qq.com"))
      {
        String str2 = a(str1, "pos");
        paramString3 = localStringBuilder;
        if (!TextUtils.isEmpty(str2))
        {
          paramString3 = new StringBuilder(paramString1.substring(0, i));
          paramString3.append("?");
          paramString3.append("pos=");
          paramString3.append(str2);
          paramString3.append("&");
          paramString3.append(str1);
        }
      }
    }
    if (!TextUtils.isEmpty(paramString3.toString())) {
      localHashMap.put("url", paramString3.toString());
    }
    if (!TextUtils.isEmpty(paramString2)) {
      localHashMap.put("link", paramString2);
    }
    if (!TextUtils.isEmpty(paramString4)) {
      localHashMap.put("appchan", paramString4);
    }
    if (!TextUtils.isEmpty(paramString5)) {
      localHashMap.put("pkgName", paramString5);
    }
    if (!TextUtils.isEmpty(paramString6)) {
      localHashMap.put("errorInfo", paramString6);
    }
    if (!TextUtils.isEmpty(paramString7)) {
      localHashMap.put("host", paramString7);
    }
    paramString1 = new StringBuilder();
    paramString1.append("upload MTT_AD_REPORT:");
    paramString1.append(localHashMap.toString());
    paramString1.toString();
    a(paramContext, "MTT_AD_REPORT", localHashMap);
  }
  
  private static void a(Context paramContext, String paramString, HashMap<String, String> paramHashMap)
  {
    if (TbsMode.TBSISQB()) {
      paramHashMap.put("QUA2", TbsInfoUtils.getQUA2());
    }
    X5CoreBeaconUploader.getInstance(paramContext).upLoadToBeacon(paramString, paramHashMap);
  }
  
  public static ReporterUtil getSingleton()
  {
    if (jdField_a_of_type_ComTencentSmttJsApiImplUtilsReporterUtil == null) {
      try
      {
        if (jdField_a_of_type_ComTencentSmttJsApiImplUtilsReporterUtil == null) {
          jdField_a_of_type_ComTencentSmttJsApiImplUtilsReporterUtil = new ReporterUtil();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentSmttJsApiImplUtilsReporterUtil;
  }
  
  public ReportInfo getReportInfo(Context paramContext, String paramString1, String paramString2)
  {
    if (this.jdField_a_of_type_JavaUtilMap.containsKey(paramString1))
    {
      paramContext = (Map)this.jdField_a_of_type_JavaUtilMap.get(paramString1);
      if (paramContext.containsKey(paramString2)) {
        return (ReportInfo)paramContext.get(paramString2);
      }
    }
    return null;
  }
  
  public void reportAbortDownload(Context paramContext, String paramString)
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    a(paramContext, null, paramString, "6", null, null, null, null);
  }
  
  public void reportDownloadMsg(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("reportDownloadMsg:");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(" url:");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).toString();
    if (this.jdField_a_of_type_JavaUtilMap.containsKey(paramString1))
    {
      localObject = (Map)this.jdField_a_of_type_JavaUtilMap.get(paramString1);
      if (((Map)localObject).containsKey(paramString2))
      {
        a(paramContext, paramString1, (ReportInfo)((Map)localObject).get(paramString2), paramString2, paramString3, paramString4);
        ((Map)localObject).remove(paramString2);
        if (((Map)localObject).size() == 0) {
          this.jdField_a_of_type_JavaUtilMap.remove(paramString1);
        }
      }
      else if ("0".equals(paramString2))
      {
        a(paramContext, paramString1, "0", "1", paramString4, null);
      }
    }
  }
  
  public void reportInstallStart(Context paramContext, String paramString1, String paramString2)
  {
    a(paramContext, paramString1, "7", "2", paramString2, null);
  }
  
  public void reportSystemReplacePackageEvent(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    String str = paramString5;
    if (paramString5 == null)
    {
      str = paramString5;
      if (this.jdField_a_of_type_JavaUtilMap.containsKey(paramString2))
      {
        Map localMap = (Map)this.jdField_a_of_type_JavaUtilMap.get(paramString2);
        str = paramString5;
        if (localMap.containsKey("2")) {
          str = ((ReportInfo)localMap.get("2")).refer;
        }
      }
    }
    paramString5 = new HashMap();
    if (!TextUtils.isEmpty(paramString1)) {
      paramString5.put("pkgName", paramString1);
    }
    if (!TextUtils.isEmpty(paramString2)) {
      paramString5.put("link", paramString2);
    }
    if (!TextUtils.isEmpty(str)) {
      paramString5.put("cpHost", str);
    }
    if (!TextUtils.isEmpty(paramString3)) {
      paramString5.put("bmd5", paramString3);
    }
    if (!TextUtils.isEmpty(paramString4)) {
      paramString5.put("amd5", paramString4);
    }
    a(paramContext, "TBS_AD_APP_REPLACE_BY_SYSTEM", paramString5);
  }
  
  public void reportUnStartMsg(Context paramContext, String paramString1, String paramString2)
  {
    a(paramContext, paramString1, "10", "1", null, paramString2);
  }
  
  public void reportUncompletedMsg(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    a(paramContext, paramString1, paramString2, "1", null, paramString3);
  }
  
  public String sendMsgByUrl(String paramString1, String paramString2, String paramString3, OpenJsHelper paramOpenJsHelper)
  {
    if ((!TextUtils.isEmpty(paramString1)) && ((URLUtil.isHttpUrl(paramString1)) || (URLUtil.isHttpsUrl(paramString1))))
    {
      if (!TextUtils.isEmpty(paramString2))
      {
        if (!TextUtils.isEmpty(paramString3))
        {
          if (!this.jdField_a_of_type_JavaUtilMap.containsKey(paramString1))
          {
            HashMap localHashMap = new HashMap();
            this.jdField_a_of_type_JavaUtilMap.put(paramString1, localHashMap);
          }
          paramString3 = new ReportInfo(paramOpenJsHelper.getUrl(), paramString3, a(paramOpenJsHelper));
          ((Map)this.jdField_a_of_type_JavaUtilMap.get(paramString1)).put(paramString2, paramString3);
          return "0";
        }
        return "-3";
      }
      return "-2";
    }
    return "-1";
  }
  
  public void statReinstallEvent(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("type", paramString1);
    if (!TextUtils.isEmpty(paramString2)) {
      localHashMap.put("showTipPage", paramString2);
    }
    if (!TextUtils.isEmpty(paramString3)) {
      localHashMap.put("pkgName", paramString3);
    }
    if (!TextUtils.isEmpty(paramString4)) {
      localHashMap.put("link", paramString4);
    }
    if (!TextUtils.isEmpty(paramString6)) {
      localHashMap.put("site", paramString6);
    }
    if (!TextUtils.isEmpty(paramString5)) {
      localHashMap.put("cpHost", paramString5);
    }
    a(paramContext, "TBS_AD_REINSTALL", localHashMap);
    paramContext = new StringBuilder();
    paramContext.append("upload TBS_AD_REINSTALL:");
    paramContext.append(localHashMap.toString());
    paramContext.toString();
  }
  
  public void statReinstallEvent(Context paramContext, String paramString1, String paramString2, JSONObject paramJSONObject)
  {
    try
    {
      String str4 = paramJSONObject.getString("pageUrl");
      String str5 = paramJSONObject.getString("refer");
      String str1 = paramJSONObject.getString("linkUrl");
      String str2 = paramJSONObject.getString("packageName");
      String str3 = UrlUtils.getHost(paramString2);
      paramJSONObject = UrlUtils.getHost(str5);
      paramString2 = UrlUtils.getHost(str4);
      if (paramJSONObject == null) {
        paramJSONObject = paramString2;
      }
      if (paramString2 != null)
      {
        if (paramString2.contains("gdt")) {
          paramString2 = "gdt";
        } else {
          paramString2 = "tbs";
        }
      }
      else {
        paramString2 = null;
      }
      statReinstallEvent(paramContext, paramString1, str3, str2, str1, paramJSONObject, paramString2);
      return;
    }
    catch (JSONException paramContext) {}
  }
  
  public static class ReportInfo
  {
    public String pageUrl;
    public String refer;
    public String reportUrl;
    
    public ReportInfo(String paramString1, String paramString2, String paramString3)
    {
      this.reportUrl = paramString2;
      this.pageUrl = paramString1;
      this.refer = paramString3;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\utils\ReporterUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */