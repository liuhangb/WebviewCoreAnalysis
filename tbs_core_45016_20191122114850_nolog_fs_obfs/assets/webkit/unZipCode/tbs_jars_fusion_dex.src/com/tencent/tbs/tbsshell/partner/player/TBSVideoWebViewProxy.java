package com.tencent.tbs.tbsshell.partner.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.tencent.mtt.video.browser.export.player.IVideoWebViewClient;
import com.tencent.mtt.video.browser.export.player.IVideoWebViewProxy;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.proxy.ProxyWebViewClient;
import com.tencent.tbs.tbsshell.WebCoreProxy;

public class TBSVideoWebViewProxy
  implements IVideoWebViewProxy
{
  private IVideoWebViewClient mVideoWebViewClient;
  private IX5WebViewBase mX5WebView;
  
  public TBSVideoWebViewProxy(Context paramContext)
  {
    this.mX5WebView = WebCoreProxy.createSDKWebview(paramContext);
  }
  
  public void addJavascriptInterface(Object paramObject, String paramString)
  {
    paramObject = this.mX5WebView;
    if (paramObject == null) {
      return;
    }
    ((IX5WebViewBase)paramObject).setWebViewClient(new ProxyWebViewClient()
    {
      public void onPageFinished(IX5WebViewBase paramAnonymousIX5WebViewBase, String paramAnonymousString)
      {
        if (TBSVideoWebViewProxy.this.mVideoWebViewClient != null) {
          TBSVideoWebViewProxy.this.mVideoWebViewClient.onPageFinished(paramAnonymousString);
        }
      }
      
      public void onPageStarted(IX5WebViewBase paramAnonymousIX5WebViewBase, String paramAnonymousString, Bitmap paramAnonymousBitmap)
      {
        if (TBSVideoWebViewProxy.this.mVideoWebViewClient != null) {
          TBSVideoWebViewProxy.this.mVideoWebViewClient.onPageStarted(paramAnonymousString, paramAnonymousBitmap);
        }
      }
      
      public void onReceivedError(IX5WebViewBase paramAnonymousIX5WebViewBase, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
      {
        if (TBSVideoWebViewProxy.this.mVideoWebViewClient != null) {
          TBSVideoWebViewProxy.this.mVideoWebViewClient.onReceivedError(paramAnonymousInt, paramAnonymousString1, paramAnonymousString2);
        }
      }
      
      public boolean shouldOverrideUrlLoading(IX5WebViewBase paramAnonymousIX5WebViewBase, String paramAnonymousString)
      {
        paramAnonymousIX5WebViewBase = new StringBuilder();
        paramAnonymousIX5WebViewBase.append("VideoWebViewProxy.shouldOverrideUrlLoading url= ");
        paramAnonymousIX5WebViewBase.append(paramAnonymousString);
        paramAnonymousIX5WebViewBase.append(", mVideoWebViewClient = ");
        paramAnonymousIX5WebViewBase.append(TBSVideoWebViewProxy.this.mVideoWebViewClient);
        paramAnonymousIX5WebViewBase.toString();
        if (TBSVideoWebViewProxy.this.mVideoWebViewClient != null) {
          return TBSVideoWebViewProxy.this.mVideoWebViewClient.shouldOverrideUrlLoading(TBSVideoWebViewProxy.this, paramAnonymousString);
        }
        return false;
      }
    });
  }
  
  public void destroy()
  {
    IX5WebViewBase localIX5WebViewBase = this.mX5WebView;
    if (localIX5WebViewBase != null) {
      localIX5WebViewBase.destroy();
    }
  }
  
  public String getUrl()
  {
    IX5WebViewBase localIX5WebViewBase = this.mX5WebView;
    if (localIX5WebViewBase != null) {
      return localIX5WebViewBase.getUrl();
    }
    return null;
  }
  
  public View getView()
  {
    IX5WebViewBase localIX5WebViewBase = this.mX5WebView;
    if (localIX5WebViewBase != null) {
      return localIX5WebViewBase.getView();
    }
    return null;
  }
  
  public View getView(int paramInt)
  {
    return getView();
  }
  
  public void loadUrl(String paramString)
  {
    IX5WebViewBase localIX5WebViewBase = this.mX5WebView;
    if (localIX5WebViewBase != null) {
      localIX5WebViewBase.loadUrl(paramString);
    }
  }
  
  public void setVideoWebViewClient(IVideoWebViewClient paramIVideoWebViewClient)
  {
    this.mVideoWebViewClient = paramIVideoWebViewClient;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\player\TBSVideoWebViewProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */