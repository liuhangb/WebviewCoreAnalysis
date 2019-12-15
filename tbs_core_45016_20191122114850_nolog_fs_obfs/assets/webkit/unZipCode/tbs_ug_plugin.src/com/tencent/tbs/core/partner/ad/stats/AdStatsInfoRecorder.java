package com.tencent.tbs.core.partner.ad.stats;

import android.text.TextUtils;
import com.tencent.common.utils.TbsMode;
import com.tencent.smtt.util.j;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AdStatsInfoRecorder
{
  private static final String EVENT_AD_REPORT = "MTT_TBS_AD_REPORT";
  private static final String EVENT_AD_REPORT_JS = "MTT_TBS_AD_REPORT_JS";
  public static String REINSTALL_EXEC_JS = "2";
  public static String REINSTALL_LOAD_JS = "1";
  public static final String STATS_EVENT_ON_AD_MAYBE_SHOWED = "AD_MAYBE_SHOWED";
  public static final String STATS_EVENT_ON_AD_PAGE_FINISHED = "AD_PAGE_FINISHED";
  public static final String STATS_EVENT_ON_AD_PAGE_LOADED = "AD_PAGE_LOADED";
  public static final String STATS_EVENT_ON_AD_PAGE_STARTED = "AD_PAGE_STARTED";
  public static final String STATS_EVENT_ON_PAGE_FINISHED = "PAGE_FINISHED";
  public static final String STATS_EVENT_ON_PAGE_STARTED = "PAGE_STARTED";
  public static final String STATS_EVENT_ON_REQUEST_ABORTED = "REQUEST_ABORTED";
  public static final String STATS_EVENT_ON_REQUEST_FAILED = "REQUEST_FAILED";
  public static final String STATS_EVENT_ON_REQUEST_STARTED = "REQUEST_STARTED";
  public static final String STATS_EVENT_ON_REQUEST_SUCCEED = "REQUEST_SUCCEED";
  private static final String TAG = "AdStatsInfoRecorder";
  public static final String TBS_ENCOURAGE_WINDOW_REPORT = "TBS_ENCOURAGE_WINDOW_REPORT";
  private static volatile AdStatsInfoRecorder instance;
  private boolean inRatioControl = SmttServiceProxy.getInstance().satisfyRatioControl(SmttServiceProxy.getInstance().getTbsAdStatsRatio());
  private String lastHost = null;
  private boolean lastHostIsAdStats = false;
  
  public static AdStatsInfoRecorder getInstance()
  {
    if (instance == null) {
      try
      {
        if (instance == null) {
          instance = new AdStatsInfoRecorder();
        }
      }
      finally {}
    }
    return instance;
  }
  
  private boolean isAdStatsUrl(String paramString)
  {
    paramString = j.c(paramString);
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    if (paramString.equals(this.lastHost)) {
      return this.lastHostIsAdStats;
    }
    this.lastHostIsAdStats = SmttServiceProxy.getInstance().isHostInTbsAdWhitelist(paramString);
    this.lastHost = paramString;
    return this.lastHostIsAdStats;
  }
  
  private Map<String, String> packageData(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("statsEvent", paramString1);
    localHashMap.put("url", paramString2);
    if (!TextUtils.isEmpty(paramString3)) {
      localHashMap.put("hostUrl", paramString3);
    }
    if (!TextUtils.isEmpty(paramString4)) {
      localHashMap.put("extraInfo", paramString4);
    }
    if (TbsMode.TBSISQB()) {
      localHashMap.put("QUA2", SmttServiceProxy.getInstance().getQUA2());
    }
    localHashMap.put("eventTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.CHINA).format(Long.valueOf(System.currentTimeMillis())));
    return localHashMap;
  }
  
  public static void uploadEncourageWindowEvent(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("cPackageName", paramString2);
    localHashMap.put("packageName", paramString3);
    localHashMap.put("type", paramString4);
    SmttServiceProxy.getInstance().upLoadToBeacon(paramString1, localHashMap);
  }
  
  private void uploadToBeacon(String paramString, Map<String, String> paramMap)
  {
    SmttServiceProxy.getInstance().uploadTbsAdStatsRealTime(paramString, paramMap);
  }
  
  public void sendJsAdStatsInfoToTbsAdServer(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if ((this.inRatioControl) && (SmttServiceProxy.getInstance().isHostInTbsJsAdReportWhitelist(j.c(paramString3)))) {
      uploadToBeacon("MTT_TBS_AD_REPORT_JS", packageData(paramString1, paramString2, paramString3, paramString4));
    }
  }
  
  public void sendStatsInfoToTbsAdServer(String paramString1, String paramString2)
  {
    sendStatsInfoToTbsAdServer(paramString1, paramString2, null);
  }
  
  public void sendStatsInfoToTbsAdServer(String paramString1, String paramString2, String paramString3)
  {
    sendStatsInfoToTbsAdServer(paramString1, paramString2, paramString3, null);
  }
  
  public void sendStatsInfoToTbsAdServer(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if ((this.inRatioControl) && ((isAdStatsUrl(paramString2)) || (isAdStatsUrl(paramString3)))) {
      uploadToBeacon("MTT_TBS_AD_REPORT", packageData(paramString1, paramString2, paramString3, paramString4));
    }
  }
  
  public void statReinstallTipsEvent(String paramString1, String paramString2, String paramString3)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("type", paramString1);
    if (!TextUtils.isEmpty(paramString2)) {
      localHashMap.put("showTipPage", paramString2);
    }
    if (!TextUtils.isEmpty(paramString3)) {
      localHashMap.put("msg", paramString3);
    }
    SmttServiceProxy.getInstance().upLoadToBeacon("TBS_AD_REINSTALL", localHashMap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\stats\AdStatsInfoRecorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */