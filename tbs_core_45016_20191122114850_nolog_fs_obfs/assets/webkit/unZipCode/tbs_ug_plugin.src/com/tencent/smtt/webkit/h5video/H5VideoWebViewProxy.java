package com.tencent.smtt.webkit.h5video;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.webview.chromium.tencent.TencentContentSettingsAdapter;
import com.tencent.mtt.video.browser.export.player.IVideoWebViewClient;
import com.tencent.mtt.video.browser.export.player.IVideoWebViewProxy;
import com.tencent.smtt.export.external.interfaces.IX5ScrollListener;
import com.tencent.smtt.export.internal.interfaces.IX5QQBrowserSettings;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewClient;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;

public class H5VideoWebViewProxy
  extends X5WebViewAdapter
  implements IVideoWebViewProxy
{
  private static final String LOGTAG = "H5VideoWebViewProxy";
  private IVideoWebViewClient mVideoWebViewClient;
  
  H5VideoWebViewProxy(Context paramContext, TencentWebViewProxy paramTencentWebViewProxy)
  {
    super(paramContext, false);
    try
    {
      copyWebSettings(paramTencentWebViewProxy);
      setWebViewClient(new TencentWebViewClient()
      {
        public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
        {
          if (H5VideoWebViewProxy.this.mVideoWebViewClient != null) {
            H5VideoWebViewProxy.this.mVideoWebViewClient.onPageFinished(paramAnonymousString);
          }
        }
        
        public void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
        {
          if (H5VideoWebViewProxy.this.mVideoWebViewClient != null) {
            H5VideoWebViewProxy.this.mVideoWebViewClient.onPageStarted(paramAnonymousString, paramAnonymousBitmap);
          }
        }
        
        public void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
        {
          if (H5VideoWebViewProxy.this.mVideoWebViewClient != null) {
            H5VideoWebViewProxy.this.mVideoWebViewClient.onReceivedError(paramAnonymousInt, paramAnonymousString1, paramAnonymousString2);
          }
        }
        
        public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
        {
          return (H5VideoWebViewProxy.this.mVideoWebViewClient != null) && (H5VideoWebViewProxy.this.mVideoWebViewClient.shouldOverrideUrlLoading(H5VideoWebViewProxy.this, paramAnonymousString));
        }
      });
      setScrollListener(new IX5ScrollListener()
      {
        public boolean onOverScroll(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8, boolean paramAnonymousBoolean)
        {
          return (H5VideoWebViewProxy.this.mVideoWebViewClient != null) && (H5VideoWebViewProxy.this.mVideoWebViewClient.onOverScroll(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousInt5, paramAnonymousInt6, paramAnonymousInt7, paramAnonymousInt8, paramAnonymousBoolean));
        }
      });
      setAddressbarDisplayMode(3, false);
      if (getQQBrowserSettings() != null) {
        getQQBrowserSettings().setWebViewIdentity("video_product_operation");
      }
      return;
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
  }
  
  private void copyWebSettings(TencentWebViewProxy paramTencentWebViewProxy)
  {
    paramTencentWebViewProxy = (TencentContentSettingsAdapter)paramTencentWebViewProxy.getRealWebView().getSettings();
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = (TencentContentSettingsAdapter)getRealWebView().getSettings();
    localTencentContentSettingsAdapter.setJavaScriptEnabled(paramTencentWebViewProxy.getJavaScriptEnabled());
    localTencentContentSettingsAdapter.setAppCacheEnabled(paramTencentWebViewProxy.getAppCacheEnabled());
    localTencentContentSettingsAdapter.setDatabaseEnabled(paramTencentWebViewProxy.getDatabaseEnabled());
    localTencentContentSettingsAdapter.setDomStorageEnabled(paramTencentWebViewProxy.getDomStorageEnabled());
    localTencentContentSettingsAdapter.setGeolocationEnabled(paramTencentWebViewProxy.getGeolocationEnabled());
    localTencentContentSettingsAdapter.setAppCacheMaxSize(paramTencentWebViewProxy.getAppCacheMaxSize());
    localTencentContentSettingsAdapter.setAppCachePath(paramTencentWebViewProxy.getAppCachePath());
    localTencentContentSettingsAdapter.setDatabasePath(paramTencentWebViewProxy.getDatabasePath());
    localTencentContentSettingsAdapter.setGeolocationDatabasePath(paramTencentWebViewProxy.getGeolocationDatabasePath());
  }
  
  public View getView(int paramInt)
  {
    if (paramInt == 0) {
      getSettingsExtension().setDisplayCutoutEnable(false);
    }
    return getView();
  }
  
  public void setVideoWebViewClient(IVideoWebViewClient paramIVideoWebViewClient)
  {
    this.mVideoWebViewClient = paramIVideoWebViewClient;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\h5video\H5VideoWebViewProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */