package com.tencent.tbs.core.partner.ad.stats;

import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.HashMap;

public class TbsSpecialSiteRecorder
{
  public static final String ERROR = "0";
  public static final String HTTP_ERROR = "1";
  private static final String TAG = "TbsSpecialSiteRecorder";
  public static final String TBS_SPECIAL_SITE_INTERCEPT_REPORT = "TBS_SPECIAL_SITE_INTERCEPT_REPORT";
  public static final String TBS_SPECIAL_SITE_REPORT = "TBS_SPECIAL_SITE_REPORT";
  public static final boolean inSpecialSiteReportRatioControl = SmttServiceProxy.getInstance().satisfyRatioControl(SmttServiceProxy.getInstance().getSpecialSiteReportRatioInterval());
  private static volatile TbsSpecialSiteRecorder instance;
  
  public static TbsSpecialSiteRecorder getInstance()
  {
    if (instance == null) {
      try
      {
        if (instance == null) {
          instance = new TbsSpecialSiteRecorder();
        }
      }
      finally {}
    }
    return instance;
  }
  
  public static void upLoadDetectToBeacon(String paramString1, String paramString2, String paramString3)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("host", paramString2);
    localHashMap.put("type", paramString3);
    SmttServiceProxy.getInstance().upLoadToBeacon(paramString1, localHashMap);
  }
  
  public static void upLoadInterceptToBeacon(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("host", paramString2);
    localHashMap.put("errorType", paramString3);
    localHashMap.put("isMainframe", paramString4);
    localHashMap.put("resourceType", paramString5);
    SmttServiceProxy.getInstance().upLoadToBeacon(paramString1, localHashMap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\stats\TbsSpecialSiteRecorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */