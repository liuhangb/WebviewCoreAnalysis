package com.tencent.tbs.tbsshell.partner.ug;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.webkit.ValueCallback;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract interface ITbsService
{
  public abstract void addJavascriptInterface(Object paramObject1, Object paramObject2, String paramString);
  
  public abstract int convertDownloadInterceptthreeswitchLevel();
  
  public abstract void fileInfoDetect(Context paramContext, String paramString, ValueCallback<String> paramValueCallback);
  
  public abstract String fixIllegalPath(String paramString);
  
  public abstract Context getAppContext();
  
  public abstract HashMap<String, Drawable> getBrowserListIcons(Context paramContext, Intent paramIntent, Object paramObject);
  
  public abstract List<String> getDownloadInterceptApkBlackList();
  
  public abstract List<String> getDownloadInterceptApkWhiteList();
  
  public abstract List<String> getDownloadInterceptFileTypeWhiteList();
  
  public abstract String getDownloadInterceptKeyMessage();
  
  public abstract List<String> getDownloadInterceptNoApkBlackList();
  
  public abstract int getDownloadInterceptToQBPopMenuStyle();
  
  public abstract String getGUID();
  
  public abstract String getHistoryUrl(Object paramObject, int paramInt);
  
  public abstract boolean getInterceptDownloadInQB();
  
  public abstract boolean getInterceptDownloadInTencentFile();
  
  public abstract String getMiniQbVersionName();
  
  public abstract int getNotifyDownloadQBCount();
  
  public abstract int getNotifyDownloadQBTimesLimit();
  
  public abstract String getPluginPath();
  
  public abstract int getQQInterruptApkType();
  
  public abstract int getQQInterruptNotApkType();
  
  public abstract String getQUA2_V3();
  
  public abstract int getTBSGeneralFeatureSwitch(String paramString);
  
  public abstract String getTbsSdkVersion();
  
  public abstract String getUrl(Object paramObject);
  
  public abstract String getX5CoreVersion();
  
  public abstract String guessFileName(String paramString1, String paramString2, String paramString3);
  
  public abstract boolean isBrowserInstalled();
  
  public abstract boolean isDebugMiniQBEnv();
  
  public abstract boolean isDownloadFileInterceptFileTypeWhiteList(String paramString);
  
  public abstract boolean isDownloadFileInterceptNotAPKDomainBlackList(String paramString);
  
  public abstract boolean isDownloadFileInterceptWhiteList(String paramString);
  
  public abstract boolean isDownloadInterceptSwitchMatch();
  
  public abstract boolean isInMiniProgram(Object paramObject);
  
  public abstract boolean isInterceptDownloadRequestEnabled();
  
  public abstract boolean isMiniQB(Object paramObject);
  
  public abstract boolean isMiniQBDisabled();
  
  public abstract boolean isSafeDownloadInterceptEnabled();
  
  public abstract boolean isSupportingDownloadRefer();
  
  public abstract void loadUrl(Object paramObject, String paramString);
  
  public abstract Context newPluginContextWrapper(Context paramContext, String paramString);
  
  public abstract Object onMiscCallback(String paramString, Map<String, Object> paramMap);
  
  public abstract void openBrowserListWithGeneralDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap);
  
  public abstract void openBrowserListWithQBDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap, Bundle paramBundle);
  
  public abstract boolean openUrlAndDownloadInQQBrowserWithReport(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9);
  
  public abstract boolean openUrlInQQBrowserWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract void postForBackgroundTasks(Runnable paramRunnable);
  
  public abstract void pullWupPreferenceByForce();
  
  public abstract void removeJavascriptInterface(Object paramObject, String paramString);
  
  public abstract void setNotifyDownloadQBCount(int paramInt);
  
  public abstract void startDownloadInMiniQB(Context paramContext, String paramString1, String paramString2, byte[] paramArrayOfByte, String paramString3, String paramString4, String paramString5, long paramLong, String paramString6, String paramString7);
  
  public abstract void upLoadToBeacon(String paramString, Map<String, String> paramMap);
  
  public abstract void userBehaviorStatistics(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\ug\ITbsService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */