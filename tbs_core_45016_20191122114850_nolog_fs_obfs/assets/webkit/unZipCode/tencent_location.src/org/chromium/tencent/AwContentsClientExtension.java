package org.chromium.tencent;

import android.content.Context;
import android.net.http.SslError;
import android.view.View;
import org.chromium.android_webview.JsPromptResultReceiver;
import org.chromium.android_webview.JsResultReceiver;
import org.chromium.android_webview.permission.AwPermissionRequest;
import org.chromium.base.Callback;

public abstract interface AwContentsClientExtension
{
  public abstract boolean controlsResizeView();
  
  public abstract int getBrowserControlsHeight();
  
  public abstract long getRemoteServerTimeStamp();
  
  public abstract long getSntpTime();
  
  public abstract String getWebViewIdentity();
  
  public abstract void handleAudioAutoPlayPrompt(String paramString, JsResultReceiver paramJsResultReceiver);
  
  public abstract void handleCameraOpenAuthorized(String paramString, JsPromptResultReceiver paramJsPromptResultReceiver);
  
  public abstract void handleGeolocationAuthorizedStatus(String paramString, JsPromptResultReceiver paramJsPromptResultReceiver);
  
  public abstract void handleVibrationAuthorized(String paramString, JsResultReceiver paramJsResultReceiver);
  
  public abstract boolean isLbsDontAlertWhiteList(String paramString);
  
  public abstract boolean needReSizeForDisplayCut(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void notifyFirstScreenParameter(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public abstract void notifyFirstScreenTime(long paramLong1, long paramLong2);
  
  public abstract void notifyResponseStartTime(long paramLong);
  
  public abstract void notifyScreenStatus(int paramInt);
  
  public abstract void notifyUrlSecurityLevelResp(String paramString1, String paramString2);
  
  public abstract void onDestroy();
  
  public abstract void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte);
  
  public abstract void onFlingScrollBegin(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void onNotifyResourceRequestURL(String paramString);
  
  public abstract void onPageStarted(String paramString, boolean paramBoolean);
  
  public abstract void onPause();
  
  public abstract void onPermissionRequest(AwPermissionRequest paramAwPermissionRequest);
  
  public abstract void onPinchBeginEvent();
  
  public abstract void onPinchEndEvent(float paramFloat1, float paramFloat2);
  
  public abstract void onPrefetchResourceHit(boolean paramBoolean);
  
  public abstract void onReceivedSslError(Callback<Boolean> paramCallback, SslError paramSslError, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void onReportMainresourceInDirectMode(String paramString);
  
  public abstract void onReportNightMode();
  
  public abstract void onReportPrivacyHeaders(String paramString);
  
  public abstract void onReportResourceInfo(AwContentsClient.AwResourceMetrics paramAwResourceMetrics);
  
  public abstract void onReportStgwTime(AwContentsClient.AwStgwTime paramAwStgwTime);
  
  public abstract void onResolveProxyHeadersCmd(String paramString);
  
  public abstract void onScrollEnded();
  
  public abstract void onTopControlsChanged(float paramFloat);
  
  public abstract void onVisibilityChanged(View paramView, int paramInt);
  
  public abstract void onWindowVisibilityChanged(int paramInt);
  
  public abstract void recordCertErrorInfo(String paramString1, String paramString2, String paramString3, int paramInt1, String paramString4, int paramInt2);
  
  public abstract void reportResourceAllInfo(AwContentsClient.AwWebRequestInfo paramAwWebRequestInfo);
  
  public abstract void reportResponseFailedWithErrCode(String paramString1, String paramString2, String paramString3, int paramInt);
  
  public abstract void reportTrafficAnomalyInfo(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, int paramInt3, String paramString4, int paramInt4);
  
  public abstract void restoreAddressBarDisplayMode();
  
  public abstract boolean shouldBlockTheRequestCustom(String paramString1, String paramString2, int paramInt);
  
  public abstract boolean shouldIgnoreNavigation(Context paramContext, String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt, String paramString2);
  
  public abstract boolean shouldReportPerformanceToServer(String paramString);
  
  public abstract int shouldReportSubResourcePerformance();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\AwContentsClientExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */