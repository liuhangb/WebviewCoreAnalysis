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

public abstract interface WebViewObserver
{
  public abstract void onAttachedToWindow();
  
  public abstract void onContentPageSwapIn(String paramString);
  
  public abstract void onContentsSizeChanged(int paramInt1, int paramInt2);
  
  public abstract void onDetachedFromWindow();
  
  public abstract void onDetectSpecialSite(String paramString, int paramInt);
  
  public abstract boolean onDownloadStartCustom(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte, TencentWebViewProxy paramTencentWebViewProxy, DownloadListener paramDownloadListener, String paramString9);
  
  public abstract void onFirstScreenTime(long paramLong1, long paramLong2);
  
  public abstract void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void onPageFinished(String paramString);
  
  public abstract void onPageStarted(String paramString);
  
  public abstract void onQProxyResponseReceived(String paramString);
  
  public abstract void onReceivedError(int paramInt, String paramString1, String paramString2);
  
  public abstract void onReceivedError2(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwContentsClient.AwWebResourceError paramAwWebResourceError);
  
  public abstract void onReceivedHttpError(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse);
  
  public abstract void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void onUpdateScrollState(AwScrollOffsetManager paramAwScrollOffsetManager, int paramInt1, int paramInt2);
  
  public abstract void onWebViewCreated(TencentWebViewProxy paramTencentWebViewProxy);
  
  public abstract void onWebViewDestroyed();
  
  public abstract void onWebViewDestroyed(Context paramContext);
  
  public abstract void onWebViewPaused(Context paramContext);
  
  public abstract void onWebViewVisibilityChanged(Context paramContext, View paramView, int paramInt);
  
  public abstract void onWindowVisibilityChanged(int paramInt);
  
  public abstract WebView webView();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\WebViewObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */