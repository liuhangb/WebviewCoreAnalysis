package org.chromium.tencent;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwWebResourceResponse;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public abstract class TencentAwContentsIoThreadClient
{
  @CalledByNative
  protected static AwWebResourceResponse interceptRequest(String paramString)
  {
    if (paramString.equals("https://res.imtt.qq.com/key-resource/service-worker.js"))
    {
      if (ContextUtils.getApplicationContext() == null) {
        return null;
      }
      Object localObject = ContextUtils.getApplicationContext().getCacheDir().getPath();
      paramString = (String)localObject;
      if (!((String)localObject).endsWith("/"))
      {
        paramString = new StringBuilder();
        paramString.append((String)localObject);
        paramString.append("/");
        paramString = paramString.toString();
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append("service-worker.js");
      paramString = ((StringBuilder)localObject).toString();
      try
      {
        paramString = new FileInputStream(paramString);
        return new AwWebResourceResponse("text/javascript", "UTF-8", paramString);
      }
      catch (FileNotFoundException paramString)
      {
        paramString.printStackTrace();
        return null;
      }
    }
    return null;
  }
  
  @CalledByNative
  public abstract int getDataFilterForRequestInfo();
  
  @CalledByNative
  public abstract void notifyResponseStartTime(long paramLong);
  
  @CalledByNative
  public abstract void notifyUrlSecurityLevelResp(String paramString1, String paramString2);
  
  @CalledByNative
  public abstract void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte);
  
  @CalledByNative
  public abstract void onNotifyResourceRequestURL(String paramString);
  
  @CalledByNative
  public abstract void onReportMainresourceInDirectMode(String paramString);
  
  @CalledByNative
  public abstract void onReportPrivacyHeaders(String paramString);
  
  @CalledByNative
  protected void onReportResourceAllInfo(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, double paramDouble1, double paramDouble2, long paramLong1, long paramLong2, int paramInt3, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, int paramInt4, int paramInt5, int paramInt6, String paramString4, boolean paramBoolean1, boolean paramBoolean2, long paramLong15, int paramInt7, int paramInt8, boolean paramBoolean3, String paramString5, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString6, String paramString7, int paramInt9, String paramString8, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString9, int paramInt10, boolean paramBoolean4, int paramInt11, int paramInt12)
  {
    AwContentsClient.AwWebRequestInfo localAwWebRequestInfo = new AwContentsClient.AwWebRequestInfo();
    if (paramInt8 == -7) {
      paramLong7 = paramLong13;
    }
    AwContentsClient.AwWebResourcePerformanceInfo localAwWebResourcePerformanceInfo = new AwContentsClient.AwWebResourcePerformanceInfo();
    localAwWebResourcePerformanceInfo.url = paramString1;
    localAwWebResourcePerformanceInfo.referer = paramString2;
    localAwWebResourcePerformanceInfo.proxy_type = paramInt1;
    localAwWebResourcePerformanceInfo.qProxyStrategy = paramInt2;
    localAwWebResourcePerformanceInfo.website_address = paramString3;
    localAwWebResourcePerformanceInfo.resourceRequestTriggerTime = Double.valueOf(paramDouble1 * 1000.0D).longValue();
    localAwWebResourcePerformanceInfo.resourceRequestHandleTime = Double.valueOf(1000.0D * paramDouble2).longValue();
    localAwWebResourcePerformanceInfo.redirect_start = paramLong1;
    localAwWebResourcePerformanceInfo.redirect_end = paramLong2;
    localAwWebResourcePerformanceInfo.redirect_times = paramInt3;
    localAwWebResourcePerformanceInfo.request_begin = paramLong3;
    localAwWebResourcePerformanceInfo.dns_start = paramLong4;
    localAwWebResourcePerformanceInfo.dns_end = paramLong5;
    localAwWebResourcePerformanceInfo.connect_start = paramLong6;
    localAwWebResourcePerformanceInfo.connect_end = paramLong7;
    localAwWebResourcePerformanceInfo.ssl_handshake_start = paramLong8;
    localAwWebResourcePerformanceInfo.ssl_handshake_end = paramLong9;
    localAwWebResourcePerformanceInfo.send_start = paramLong10;
    localAwWebResourcePerformanceInfo.send_end = paramLong11;
    localAwWebResourcePerformanceInfo.recv_start = paramLong12;
    localAwWebResourcePerformanceInfo.recv_end = paramLong13;
    localAwWebResourcePerformanceInfo.recv_bytes = paramLong14;
    localAwWebResourcePerformanceInfo.status_code = paramInt4;
    localAwWebResourcePerformanceInfo.session_resumption = paramInt5;
    localAwWebResourcePerformanceInfo.cipher_suit = paramInt6;
    localAwWebResourcePerformanceInfo.proxy_data = paramString4;
    localAwWebResourcePerformanceInfo.socket_reused = paramBoolean1;
    localAwWebResourcePerformanceInfo.was_cached = paramBoolean2;
    localAwWebResourcePerformanceInfo.expired_time = paramLong15;
    localAwWebResourcePerformanceInfo.resource_type = paramInt7;
    localAwWebResourcePerformanceInfo.error_id = paramInt8;
    localAwWebResourcePerformanceInfo.report_server = paramBoolean4;
    localAwWebResourcePerformanceInfo.connectInfo = paramInt11;
    localAwWebResourcePerformanceInfo.rtt = paramInt12;
    paramString2 = new AwContentsClient.AwWebResourceRequest();
    paramString2.url = paramString1;
    paramInt2 = 0;
    if (paramInt1 == 0) {
      paramBoolean1 = true;
    } else {
      paramBoolean1 = false;
    }
    paramString2.isMainFrame = paramBoolean1;
    paramString2.hasUserGesture = paramBoolean3;
    paramString2.method = paramString5;
    paramString2.requestHeaders = new HashMap(paramArrayOfString1.length);
    paramInt1 = 0;
    while (paramInt1 < paramArrayOfString1.length)
    {
      paramString2.requestHeaders.put(paramArrayOfString1[paramInt1], paramArrayOfString2[paramInt1]);
      paramInt1 += 1;
    }
    if ((paramArrayOfString3 != null) && (paramArrayOfString3.length > 0))
    {
      paramString1 = new HashMap(paramArrayOfString3.length);
      paramInt1 = paramInt2;
      while (paramInt1 < paramArrayOfString3.length)
      {
        paramString1.put(paramArrayOfString3[paramInt1], paramArrayOfString4[paramInt1]);
        paramInt1 += 1;
      }
      paramString1 = new AwWebResourceResponse(paramString6, paramString7, null, paramInt9, paramString8, paramString1);
    }
    else
    {
      paramString1 = null;
    }
    paramString3 = new AwContentsClient.AwWebResourceError();
    paramString3.errorCode = paramInt8;
    paramString3.description = paramString9;
    localAwWebRequestInfo.performance = localAwWebResourcePerformanceInfo;
    localAwWebRequestInfo.request = paramString2;
    localAwWebRequestInfo.response = paramString1;
    localAwWebRequestInfo.error = paramString3;
    localAwWebRequestInfo.filter = paramInt10;
    reportResourceAllInfo(localAwWebRequestInfo);
  }
  
  @CalledByNative
  protected void onReportResourceFlow(String paramString1, boolean paramBoolean1, boolean paramBoolean2, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString3, String paramString4, int paramInt1, String paramString5, String[] paramArrayOfString3, String[] paramArrayOfString4, int paramInt2, String paramString6, int paramInt3)
  {
    AwContentsClient.AwWebRequestInfo localAwWebRequestInfo = new AwContentsClient.AwWebRequestInfo();
    AwContentsClient.AwWebResourceRequest localAwWebResourceRequest = new AwContentsClient.AwWebResourceRequest();
    localAwWebResourceRequest.url = paramString1;
    localAwWebResourceRequest.isMainFrame = paramBoolean1;
    localAwWebResourceRequest.hasUserGesture = paramBoolean2;
    localAwWebResourceRequest.method = paramString2;
    localAwWebResourceRequest.requestHeaders = new HashMap(paramArrayOfString1.length);
    int j = 0;
    int i = 0;
    while (i < paramArrayOfString1.length)
    {
      localAwWebResourceRequest.requestHeaders.put(paramArrayOfString1[i], paramArrayOfString2[i]);
      i += 1;
    }
    if ((paramArrayOfString3 != null) && (paramArrayOfString3.length > 0))
    {
      paramString1 = new HashMap(paramArrayOfString3.length);
      i = j;
      while (i < paramArrayOfString3.length)
      {
        paramString1.put(paramArrayOfString3[i], paramArrayOfString4[i]);
        i += 1;
      }
      paramString1 = new AwWebResourceResponse(paramString3, paramString4, null, paramInt1, paramString5, paramString1);
    }
    else
    {
      paramString1 = null;
    }
    paramString2 = new AwContentsClient.AwWebResourceError();
    paramString2.errorCode = paramInt2;
    paramString2.description = paramString6;
    localAwWebRequestInfo.performance = null;
    localAwWebRequestInfo.request = localAwWebResourceRequest;
    localAwWebRequestInfo.response = paramString1;
    localAwWebRequestInfo.error = paramString2;
    localAwWebRequestInfo.filter = paramInt3;
    reportResourceAllInfo(localAwWebRequestInfo);
  }
  
  @CalledByNative
  protected void onReportResourceInfo(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, long paramLong1, boolean paramBoolean1, String paramString4, int paramInt3, int paramInt4, long paramLong2, long paramLong3, String paramString5, String paramString6, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, String paramString7, boolean paramBoolean5)
  {
    AwContentsClient.AwResourceMetrics localAwResourceMetrics = new AwContentsClient.AwResourceMetrics();
    localAwResourceMetrics.url = paramString1;
    localAwResourceMetrics.domain = paramString2;
    localAwResourceMetrics.refer = paramString3;
    localAwResourceMetrics.proxyStrategy = paramInt1;
    localAwResourceMetrics.resourceType = paramInt2;
    localAwResourceMetrics.contentLength = paramLong1;
    localAwResourceMetrics.wasCached = paramBoolean1;
    localAwResourceMetrics.serverIP = paramString4;
    localAwResourceMetrics.statusCode = paramInt3;
    localAwResourceMetrics.connectInfo = paramInt4;
    localAwResourceMetrics.sentBytes = paramLong2;
    localAwResourceMetrics.receivedBytes = paramLong3;
    localAwResourceMetrics.metricsSaved = paramString5;
    localAwResourceMetrics.mimeType = paramString6;
    localAwResourceMetrics.proxyType = paramInt5;
    localAwResourceMetrics.proxyErrorCode = paramInt6;
    localAwResourceMetrics.requestErrorCode = paramInt7;
    localAwResourceMetrics.isProxyFailed = paramBoolean2;
    localAwResourceMetrics.isHttpsTunnelFailed = paramBoolean3;
    localAwResourceMetrics.isMiniQB = paramBoolean4;
    localAwResourceMetrics.proxyAddress = paramString7;
    localAwResourceMetrics.isSuccess = paramBoolean5;
    onReportResourceInfo(localAwResourceMetrics);
  }
  
  public abstract void onReportResourceInfo(AwContentsClient.AwResourceMetrics paramAwResourceMetrics);
  
  @CalledByNative
  protected void onReportResourcePerformance(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, double paramDouble1, double paramDouble2, long paramLong1, long paramLong2, int paramInt3, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, long paramLong10, long paramLong11, long paramLong12, long paramLong13, long paramLong14, int paramInt4, int paramInt5, int paramInt6, String paramString4, boolean paramBoolean1, boolean paramBoolean2, long paramLong15, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean3, int paramInt10)
  {
    AwContentsClient.AwWebRequestInfo localAwWebRequestInfo = new AwContentsClient.AwWebRequestInfo();
    AwContentsClient.AwWebResourcePerformanceInfo localAwWebResourcePerformanceInfo = new AwContentsClient.AwWebResourcePerformanceInfo();
    localAwWebResourcePerformanceInfo.url = paramString1;
    localAwWebResourcePerformanceInfo.referer = paramString2;
    localAwWebResourcePerformanceInfo.proxy_type = paramInt1;
    localAwWebResourcePerformanceInfo.qProxyStrategy = paramInt2;
    localAwWebResourcePerformanceInfo.website_address = paramString3;
    localAwWebResourcePerformanceInfo.resourceRequestTriggerTime = Double.valueOf(paramDouble1 * 1000.0D).longValue();
    localAwWebResourcePerformanceInfo.resourceRequestHandleTime = Double.valueOf(1000.0D * paramDouble2).longValue();
    localAwWebResourcePerformanceInfo.redirect_start = paramLong1;
    localAwWebResourcePerformanceInfo.redirect_end = paramLong2;
    localAwWebResourcePerformanceInfo.redirect_times = paramInt3;
    localAwWebResourcePerformanceInfo.request_begin = paramLong3;
    localAwWebResourcePerformanceInfo.dns_start = paramLong4;
    localAwWebResourcePerformanceInfo.dns_end = paramLong5;
    localAwWebResourcePerformanceInfo.connect_start = paramLong6;
    localAwWebResourcePerformanceInfo.connect_end = paramLong7;
    localAwWebResourcePerformanceInfo.ssl_handshake_start = paramLong8;
    localAwWebResourcePerformanceInfo.ssl_handshake_end = paramLong9;
    localAwWebResourcePerformanceInfo.send_start = paramLong10;
    localAwWebResourcePerformanceInfo.send_end = paramLong11;
    localAwWebResourcePerformanceInfo.recv_start = paramLong12;
    localAwWebResourcePerformanceInfo.recv_end = paramLong13;
    localAwWebResourcePerformanceInfo.recv_bytes = paramLong14;
    localAwWebResourcePerformanceInfo.status_code = paramInt4;
    localAwWebResourcePerformanceInfo.session_resumption = paramInt5;
    localAwWebResourcePerformanceInfo.cipher_suit = paramInt6;
    localAwWebResourcePerformanceInfo.proxy_data = paramString4;
    localAwWebResourcePerformanceInfo.socket_reused = paramBoolean1;
    localAwWebResourcePerformanceInfo.was_cached = paramBoolean2;
    localAwWebResourcePerformanceInfo.expired_time = paramLong15;
    localAwWebResourcePerformanceInfo.resource_type = paramInt7;
    localAwWebResourcePerformanceInfo.error_id = paramInt8;
    localAwWebResourcePerformanceInfo.report_server = paramBoolean3;
    localAwWebResourcePerformanceInfo.connectInfo = paramInt10;
    paramString1 = new AwContentsClient.AwWebResourceError();
    paramString1.errorCode = paramInt8;
    localAwWebRequestInfo.performance = localAwWebResourcePerformanceInfo;
    localAwWebRequestInfo.request = null;
    localAwWebRequestInfo.response = null;
    localAwWebRequestInfo.error = paramString1;
    localAwWebRequestInfo.filter = paramInt9;
    reportResourceAllInfo(localAwWebRequestInfo);
  }
  
  @CalledByNative
  protected void onReportStgwTime(String paramString1, String paramString2, String paramString3)
  {
    AwContentsClient.AwStgwTime localAwStgwTime = new AwContentsClient.AwStgwTime();
    localAwStgwTime.url = paramString1;
    localAwStgwTime.requestTime = paramString2;
    localAwStgwTime.upstreamResponseTime = paramString3;
    onReportStgwTime(localAwStgwTime);
  }
  
  public abstract void onReportStgwTime(AwContentsClient.AwStgwTime paramAwStgwTime);
  
  @CalledByNative
  public abstract void onResolveProxyHeadersCmd(String paramString);
  
  public abstract void reportResourceAllInfo(AwContentsClient.AwWebRequestInfo paramAwWebRequestInfo);
  
  @CalledByNative
  public abstract void reportResponseFailedWithErrCode(String paramString1, String paramString2, String paramString3, int paramInt);
  
  @CalledByNative
  public abstract void reportTrafficAnomalyInfo(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, int paramInt3, String paramString4, int paramInt4);
  
  public abstract void setAwContentsClientExtension(AwContentsClientExtension paramAwContentsClientExtension);
  
  public abstract void setTencentAwSettings(TencentAwSettings paramTencentAwSettings);
  
  @CalledByNative
  public abstract boolean shouldBlockTheRequestCustom(String paramString1, String paramString2, int paramInt);
  
  @CalledByNative
  public abstract boolean shouldReportPerformanceToServer(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\TencentAwContentsIoThreadClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */