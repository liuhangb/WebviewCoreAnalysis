package org.chromium.tencent;

import android.net.http.SslError;
import android.view.View;
import com.tencent.tbs.core.webkit.GeolocationPermissions.Callback;
import com.tencent.tbs.core.webkit.WebChromeClient.CustomViewCallback;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.android_webview.AwWebResourceResponse;
import org.chromium.base.Callback;

public abstract class AwContentsClient
{
  public abstract void onContentsSizeChanged(int paramInt1, int paramInt2);
  
  public abstract boolean onCreateWindow(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  public abstract void onGeolocationPermissionsShowPrompt(String paramString, GeolocationPermissions.Callback paramCallback);
  
  public abstract void onReceivedSslError(Callback<Boolean> paramCallback, SslError paramSslError, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback);
  
  public abstract void updateScrollState(AwScrollOffsetManager paramAwScrollOffsetManager, int paramInt1, int paramInt2);
  
  public static class AwResourceMetrics
  {
    public int connectInfo;
    public long contentLength;
    public String domain;
    public boolean isHttpsTunnelFailed;
    public boolean isMiniQB;
    public boolean isProxyFailed;
    public boolean isSuccess;
    public String metricsSaved;
    public String mimeType;
    public String proxyAddress;
    public int proxyErrorCode;
    public int proxyStrategy;
    public int proxyType;
    public long receivedBytes;
    public String refer;
    public int requestErrorCode;
    public int resourceType;
    public long sentBytes;
    public String serverIP;
    public int statusCode;
    public String url;
    public boolean wasCached;
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("AwResourceMetrics [url=");
      localStringBuilder.append(this.url);
      localStringBuilder.append(", domain=");
      localStringBuilder.append(this.domain);
      localStringBuilder.append(", refer=");
      localStringBuilder.append(this.refer);
      localStringBuilder.append(", proxyStrategy=");
      localStringBuilder.append(this.proxyStrategy);
      localStringBuilder.append(", resourceType=");
      localStringBuilder.append(this.resourceType);
      localStringBuilder.append(", contentLength=");
      localStringBuilder.append(this.contentLength);
      localStringBuilder.append(", wasCached=");
      localStringBuilder.append(this.wasCached);
      localStringBuilder.append(", serverIP=");
      localStringBuilder.append(this.serverIP);
      localStringBuilder.append(", statusCode=");
      localStringBuilder.append(this.statusCode);
      localStringBuilder.append(", connectInfo=");
      localStringBuilder.append(this.connectInfo);
      localStringBuilder.append(", sentBytes=");
      localStringBuilder.append(this.sentBytes);
      localStringBuilder.append(", receivedBytes=");
      localStringBuilder.append(this.receivedBytes);
      localStringBuilder.append(", metricsSaved=");
      localStringBuilder.append(this.metricsSaved);
      localStringBuilder.append(", mimeType=");
      localStringBuilder.append(this.mimeType);
      localStringBuilder.append(", proxyType=");
      localStringBuilder.append(this.proxyType);
      localStringBuilder.append(", proxyErrorCode=");
      localStringBuilder.append(this.proxyErrorCode);
      localStringBuilder.append(", requestErrorCode=");
      localStringBuilder.append(this.requestErrorCode);
      localStringBuilder.append(", isProxyFailed=");
      localStringBuilder.append(this.isProxyFailed);
      localStringBuilder.append(", isHttpsTunnelFailed=");
      localStringBuilder.append(this.isHttpsTunnelFailed);
      localStringBuilder.append(", isMiniQB=");
      localStringBuilder.append(this.isMiniQB);
      localStringBuilder.append(", proxyAddress=");
      localStringBuilder.append(this.proxyAddress);
      localStringBuilder.append(", isSuccess=");
      localStringBuilder.append(this.isSuccess);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  public static class AwStgwTime
  {
    public String requestTime;
    public String upstreamResponseTime;
    public String url;
  }
  
  public static class AwWebRequestInfo
  {
    public AwContentsClient.AwWebResourceError error;
    public int filter;
    public AwContentsClient.AwWebResourcePerformanceInfo performance;
    public AwContentsClient.AwWebResourceRequest request;
    public AwWebResourceResponse response;
  }
  
  public static class AwWebResourceError
  {
    public String description;
    public int errorCode = -1;
  }
  
  public static class AwWebResourcePerformanceInfo
  {
    public int cipher_suit;
    public int connectInfo;
    public long connect_end;
    public long connect_start;
    public long dns_end;
    public long dns_start;
    public int error_id;
    public long expired_time;
    public String proxy_data;
    public int proxy_type;
    public int qProxyStrategy;
    public long recv_bytes;
    public long recv_end;
    public long recv_start;
    public long redirect_end;
    public long redirect_start;
    public int redirect_times;
    public String referer;
    public boolean report_server;
    public long request_begin;
    public long resourceRequestHandleTime;
    public long resourceRequestTriggerTime;
    public int resource_type;
    public int rtt;
    public long send_end;
    public long send_start;
    public int session_resumption;
    public boolean socket_reused;
    public long ssl_handshake_end;
    public long ssl_handshake_start;
    public int status_code;
    public String url;
    public boolean was_cached;
    public String website_address;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\AwContentsClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */