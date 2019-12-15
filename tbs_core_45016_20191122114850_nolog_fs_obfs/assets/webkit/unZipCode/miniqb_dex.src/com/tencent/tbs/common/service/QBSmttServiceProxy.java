package com.tencent.tbs.common.service;

import android.util.Log;
import com.tencent.tbs.common.internal.service.IQBSmttService;
import java.util.Map;

public class QBSmttServiceProxy
{
  private static final String TAG = "SmttServiceProxy";
  private static QBSmttServiceProxy mInstance;
  private static IQBSmttService mLocalSmttService;
  
  public static QBSmttServiceProxy getInstance()
  {
    if (mInstance == null) {
      mInstance = new QBSmttServiceProxy();
    }
    return mInstance;
  }
  
  public static IQBSmttService getLocalSmttService()
  {
    return mLocalSmttService;
  }
  
  public static void setLocalSmttService(IQBSmttService paramIQBSmttService)
  {
    Log.e("pangym", "setLocalSmttService");
    mLocalSmttService = paramIQBSmttService;
  }
  
  public String getOAID()
  {
    IQBSmttService localIQBSmttService = mLocalSmttService;
    if (localIQBSmttService == null) {
      return "";
    }
    return localIQBSmttService.getOAID();
  }
  
  public String getQAID()
  {
    IQBSmttService localIQBSmttService = mLocalSmttService;
    if (localIQBSmttService == null) {
      return "";
    }
    return localIQBSmttService.getQAID();
  }
  
  public String getQIMEI()
  {
    IQBSmttService localIQBSmttService = mLocalSmttService;
    if (localIQBSmttService == null) {
      return "";
    }
    return localIQBSmttService.getQIMEI();
  }
  
  public String getTAID()
  {
    IQBSmttService localIQBSmttService = mLocalSmttService;
    if (localIQBSmttService == null) {
      return "";
    }
    return localIQBSmttService.getTAID();
  }
  
  public void onReportAppLogInfo(String paramString, boolean paramBoolean)
  {
    if (mLocalSmttService == null) {
      return;
    }
    Log.e("QBSmttServiceProxy", "onReportAppLogInfo");
    mLocalSmttService.reportAppLog(paramString, paramBoolean);
  }
  
  public void onReportMetrics(String paramString1, long paramLong1, long paramLong2, int paramInt1, String paramString2, boolean paramBoolean1, int paramInt2, String paramString3, String paramString4, boolean paramBoolean2, int paramInt3, boolean paramBoolean3)
  {
    IQBSmttService localIQBSmttService = mLocalSmttService;
    if (localIQBSmttService == null) {
      return;
    }
    localIQBSmttService.onReportMetrics(paramString1, paramLong1, paramLong2, paramInt1, paramString2, paramBoolean1, paramInt2, paramString3, paramString4, paramBoolean2, paramInt3, paramBoolean3);
  }
  
  public void showQBToast(String paramString, int paramInt)
  {
    IQBSmttService localIQBSmttService = mLocalSmttService;
    if (localIQBSmttService == null) {
      return;
    }
    localIQBSmttService.showQBToast(paramString, paramInt);
  }
  
  public void upLoadToBeacon(String paramString, Map<String, String> paramMap)
  {
    IQBSmttService localIQBSmttService = mLocalSmttService;
    if (localIQBSmttService == null) {
      return;
    }
    localIQBSmttService.upLoadToBeacon(paramString, paramMap);
  }
  
  public void upLoadToBeacon(String paramString, Map<String, String> paramMap, boolean paramBoolean)
  {
    IQBSmttService localIQBSmttService = mLocalSmttService;
    if (localIQBSmttService == null) {
      return;
    }
    localIQBSmttService.upLoadToBeacon(paramString, paramMap, paramBoolean);
  }
  
  public void upLoadToBeacon(String paramString, boolean paramBoolean1, long paramLong1, long paramLong2, Map<String, String> paramMap, boolean paramBoolean2)
  {
    IQBSmttService localIQBSmttService = mLocalSmttService;
    if (localIQBSmttService == null) {
      return;
    }
    localIQBSmttService.upLoadToBeacon(paramString, paramBoolean1, paramLong1, paramLong2, paramMap, paramBoolean2);
  }
  
  public void userBehaviorStatistics(String paramString)
  {
    IQBSmttService localIQBSmttService = mLocalSmttService;
    if (localIQBSmttService == null) {
      return;
    }
    localIQBSmttService.userBehaviorStatistics(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\service\QBSmttServiceProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */