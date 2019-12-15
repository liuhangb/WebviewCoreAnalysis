package com.tencent.tbs.core.partner.ad;

import android.content.Context;
import android.view.View;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.ProxyWebViewObserver;
import com.tencent.tbs.core.webkit.DownloadListener;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import org.chromium.android_webview.AwContentsClient.AwWebResourceError;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.android_webview.AwWebResourceResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class AdWebviewStatusChangeProxy
  extends ProxyWebViewObserver
{
  private TencentWebViewProxy mWebView = null;
  
  public AdWebviewStatusChangeProxy(TencentWebViewProxy paramTencentWebViewProxy)
  {
    super(paramTencentWebViewProxy.getRealWebView());
    this.mWebView = paramTencentWebViewProxy;
    paramTencentWebViewProxy.addObserver(this);
  }
  
  private void notifyWebviewStatusChange(int paramInt, JSONObject paramJSONObject)
  {
    SmttServiceProxy.getInstance().onWebviewStatusChange((X5WebViewAdapter)this.mWebView, paramInt, paramJSONObject);
  }
  
  public void onAttachedToWindow()
  {
    notifyWebviewStatusChange(19, null);
  }
  
  public void onContentPageSwapIn(String paramString)
  {
    notifyWebviewStatusChange(18, null);
  }
  
  public void onContentsSizeChanged(int paramInt1, int paramInt2)
  {
    notifyWebviewStatusChange(8, null);
  }
  
  public void onDetachedFromWindow()
  {
    notifyWebviewStatusChange(20, null);
  }
  
  public void onDetectSpecialSite(String paramString, int paramInt)
  {
    notifyWebviewStatusChange(22, null);
  }
  
  public boolean onDownloadStartCustom(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte, TencentWebViewProxy paramTencentWebViewProxy, DownloadListener paramDownloadListener, String paramString9)
  {
    notifyWebviewStatusChange(15, null);
    return false;
  }
  
  public void onFirstScreenTime(long paramLong1, long paramLong2)
  {
    notifyWebviewStatusChange(16, null);
  }
  
  public void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {}
  
  public void onPageFinished(String paramString)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("url", paramString);
      notifyWebviewStatusChange(7, localJSONObject);
      return;
    }
    catch (JSONException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void onPageStarted(String paramString)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("url", paramString);
      notifyWebviewStatusChange(6, localJSONObject);
      return;
    }
    catch (JSONException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void onQProxyResponseReceived(String paramString)
  {
    notifyWebviewStatusChange(11, null);
  }
  
  public void onReceivedError(int paramInt, String paramString1, String paramString2)
  {
    notifyWebviewStatusChange(13, null);
  }
  
  public void onReceivedError2(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwContentsClient.AwWebResourceError paramAwWebResourceError)
  {
    notifyWebviewStatusChange(23, null);
  }
  
  public void onReceivedHttpError(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse)
  {
    notifyWebviewStatusChange(14, null);
  }
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void onUpdateScrollState(AwScrollOffsetManager paramAwScrollOffsetManager, int paramInt1, int paramInt2)
  {
    notifyWebviewStatusChange(12, null);
  }
  
  public void onWebViewCreated(TencentWebViewProxy paramTencentWebViewProxy) {}
  
  public void onWebViewDestroyed()
  {
    notifyWebviewStatusChange(5, null);
  }
  
  public void onWebViewDestroyed(Context paramContext)
  {
    notifyWebviewStatusChange(4, null);
  }
  
  public void onWebViewPaused(Context paramContext)
  {
    notifyWebviewStatusChange(3, null);
  }
  
  public void onWebViewVisibilityChanged(Context paramContext, View paramView, int paramInt)
  {
    notifyWebviewStatusChange(2, null);
  }
  
  public void onWindowVisibilityChanged(int paramInt)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("visibility", paramInt);
      notifyWebviewStatusChange(9, localJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\AdWebviewStatusChangeProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */