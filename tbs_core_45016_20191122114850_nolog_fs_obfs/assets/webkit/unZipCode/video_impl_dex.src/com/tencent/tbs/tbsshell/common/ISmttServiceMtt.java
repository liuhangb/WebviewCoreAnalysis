package com.tencent.tbs.tbsshell.common;

import android.webkit.ValueCallback;
import java.util.Map;

public abstract interface ISmttServiceMtt
{
  public abstract String getAdBlockSourceMD5Value();
  
  public abstract String getAdJsMD5Value();
  
  public abstract boolean getBDSearchPredictEnable();
  
  public abstract int getDnsResolveTypeForThisDomain(String paramString);
  
  public abstract boolean getHitRateEnabled();
  
  public abstract boolean getIsX5ProEnabled();
  
  public abstract String getJsTemplate();
  
  public abstract String getModuleMD5Value();
  
  public abstract boolean getPopupHideAdDialogEnabled();
  
  public abstract String getSWApiReportWhiteList();
  
  public abstract String[] getSessionPersistentSupported0RTTDomains();
  
  public abstract String getSmartAdLiveLog();
  
  public abstract String getSubsetAdRuleMD5Value();
  
  public abstract void giveToastTips(String paramString);
  
  public abstract boolean isDomDistillWhiteList(String paramString);
  
  public abstract boolean isEnableX5Proxy();
  
  public abstract boolean isHttps0RTTEnabled();
  
  public abstract boolean isReportCallWebviewApiOnWrongThreadEnabled();
  
  public abstract void onBeaconReport(String paramString, Map<String, String> paramMap);
  
  public abstract void onReportApiExecResult(String paramString, int paramInt1, int paramInt2);
  
  public abstract void onReportJSDoThrow(String paramString1, String paramString2);
  
  public abstract void onReportPageTotalTimeV2(String paramString1, long paramLong1, long paramLong2, long paramLong3, long paramLong4, String paramString2, byte paramByte, String paramString3, boolean paramBoolean);
  
  public abstract void onReportSWNetFlowInfo(String paramString, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void onReportSWRegistInfo(String paramString);
  
  public abstract void reportAccessibilityEnableCause(String paramString);
  
  public abstract void reportCallWebviewApiOnWrongThread(String paramString);
  
  public abstract void reportErrorPageLearn(String paramString, int paramInt);
  
  public abstract void reportMSEUrl(String paramString);
  
  public abstract void reportPageImageInfo(Map<String, String> paramMap);
  
  public abstract void reportSavePageToDiskInfo(String paramString1, String paramString2, int paramInt);
  
  public abstract void reportVibrationInfo(String paramString, int paramInt);
  
  public abstract void resetAdBlockSourceMD5();
  
  public abstract void resetJSAndModelMD5(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void resetSubsetAdRuleMD5();
  
  public abstract void sendMainResourceSourceToBeacon(String paramString1, String paramString2);
  
  public abstract void setQBPageVideoSearchWupCallback(ValueCallback<Map<String, String>> paramValueCallback);
  
  public abstract boolean shouldEnableSessionPersistentForThisDomain(String paramString);
  
  public abstract boolean shouldPopupHideAdDialogByUrl(String paramString);
  
  public abstract boolean shouldReportSearchVideoForDomain(String paramString);
  
  public abstract boolean shouldUseTbsAudioPlayerWithNoClick(String paramString);
  
  public abstract boolean shouldUseTbsMediaPlayer(String paramString);
  
  public abstract boolean uploadPageErrorMetaInfo(String paramString1, int paramInt1, String paramString2, String paramString3, boolean paramBoolean1, String paramString4, String paramString5, boolean paramBoolean2, boolean paramBoolean3, String paramString6, int paramInt2, String paramString7, boolean paramBoolean4, int paramInt3, int paramInt4, long paramLong, boolean paramBoolean5, int paramInt5);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\ISmttServiceMtt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */