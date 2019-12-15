package com.tencent.tbs.core;

import android.content.Context;
import android.view.View;
import com.tencent.tbs.core.webkit.DownloadListener;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import org.chromium.android_webview.AwContentsClient.AwWebResourceError;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.android_webview.AwWebResourceResponse;

public class ProxyWebViewObserver
  implements WebViewObserver
{
  private WebView mWebView;
  
  public ProxyWebViewObserver(WebView paramWebView)
  {
    this.mWebView = paramWebView;
  }
  
  public void onAttachedToWindow() {}
  
  public void onContentPageSwapIn(String paramString) {}
  
  public void onContentsSizeChanged(int paramInt1, int paramInt2) {}
  
  public void onDetachedFromWindow() {}
  
  public void onDetectSpecialSite(String paramString, int paramInt) {}
  
  public boolean onDownloadStartCustom(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte, TencentWebViewProxy paramTencentWebViewProxy, DownloadListener paramDownloadListener, String paramString9)
  {
    return false;
  }
  
  public void onFirstScreenTime(long paramLong1, long paramLong2) {}
  
  public void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {}
  
  public void onPageFinished(String paramString) {}
  
  public void onPageStarted(String paramString) {}
  
  public void onQProxyResponseReceived(String paramString) {}
  
  public void onReceivedError(int paramInt, String paramString1, String paramString2) {}
  
  public void onReceivedError2(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwContentsClient.AwWebResourceError paramAwWebResourceError) {}
  
  public void onReceivedHttpError(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse) {}
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void onUpdateScrollState(AwScrollOffsetManager paramAwScrollOffsetManager, int paramInt1, int paramInt2) {}
  
  public void onWebViewCreated(TencentWebViewProxy paramTencentWebViewProxy) {}
  
  public void onWebViewDestroyed() {}
  
  public void onWebViewDestroyed(Context paramContext) {}
  
  public void onWebViewPaused(Context paramContext) {}
  
  public void onWebViewVisibilityChanged(Context paramContext, View paramView, int paramInt) {}
  
  public void onWindowVisibilityChanged(int paramInt) {}
  
  public WebView webView()
  {
    return this.mWebView;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\ProxyWebViewObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */