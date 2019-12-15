package com.tencent.tbs.common.internal.service;

import java.util.Map;

public abstract interface IQBSmttService
{
  public abstract String getOAID();
  
  public abstract String getQAID();
  
  public abstract String getQIMEI();
  
  public abstract String getTAID();
  
  public abstract void onReportMetrics(String paramString1, long paramLong1, long paramLong2, int paramInt1, String paramString2, boolean paramBoolean1, int paramInt2, String paramString3, String paramString4, boolean paramBoolean2, int paramInt3, boolean paramBoolean3);
  
  public abstract void reportAppLog(String paramString, boolean paramBoolean);
  
  public abstract void showQBToast(String paramString, int paramInt);
  
  public abstract void upLoadToBeacon(String paramString, Map<String, String> paramMap);
  
  public abstract void upLoadToBeacon(String paramString, Map<String, String> paramMap, boolean paramBoolean);
  
  public abstract void upLoadToBeacon(String paramString, boolean paramBoolean1, long paramLong1, long paramLong2, Map<String, String> paramMap, boolean paramBoolean2);
  
  public abstract void userBehaviorStatistics(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\internal\service\IQBSmttService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */