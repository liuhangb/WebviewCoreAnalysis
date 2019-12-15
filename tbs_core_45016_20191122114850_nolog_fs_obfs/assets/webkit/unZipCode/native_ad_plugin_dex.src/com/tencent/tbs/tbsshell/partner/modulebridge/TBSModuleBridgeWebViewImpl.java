package com.tencent.tbs.tbsshell.partner.modulebridge;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.tencent.common.utils.LogUtils;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.ClientCertRequest;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.tbs.modulebridge.webview.IBrigeWebViewClientProxy;
import com.tencent.tbs.modulebridge.webview.IBrigeWebViewProxy;
import com.tencent.tbs.tbsshell.WebCoreProxy;
import java.util.Iterator;
import java.util.List;

public class TBSModuleBridgeWebViewImpl
  implements IBrigeWebViewProxy
{
  private static final String TAG = "ModuleBridgeWebViewImpl";
  protected IBrigeWebViewClientProxy mClient = null;
  private Context mContext = null;
  IX5WebViewBase mWebView = null;
  protected TBSModuleBrigeWebViewClient mWebviewClient = null;
  
  public TBSModuleBridgeWebViewImpl(Context paramContext)
  {
    this.mContext = paramContext;
    initWebView();
  }
  
  private void initWebView()
  {
    this.mWebView = WebCoreProxy.createSDKWebview(this.mContext);
    this.mWebviewClient = new TBSModuleBrigeWebViewClient(null);
    this.mWebView.setWebViewClient(this.mWebviewClient);
    this.mWebView.getSettings().setJavaScriptEnabled(true);
    this.mWebView.getSettings().setSupportZoom(false);
    this.mWebView.getSettings().setBuiltInZoomControls(false);
    this.mWebView.getSettings().setDomStorageEnabled(true);
    this.mWebView.getSettings().setDatabaseEnabled(true);
    this.mWebView.getSettings().setLoadWithOverviewMode(true);
    this.mWebView.getSettings().setUseWideViewPort(true);
    this.mWebView.getSettings().setAppCacheEnabled(true);
    this.mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
    this.mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
    IX5WebViewBase localIX5WebViewBase = this.mWebView;
    if ((localIX5WebViewBase instanceof IX5WebView)) {
      ((IX5WebView)localIX5WebViewBase).setWebViewClientExtension(new ProxyWebViewClientExtension()
      {
        public void computeScroll(View paramAnonymousView) {}
        
        public boolean dispatchTouchEvent(MotionEvent paramAnonymousMotionEvent, View paramAnonymousView)
        {
          return false;
        }
        
        public void handlePluginTag(String paramAnonymousString1, String paramAnonymousString2, boolean paramAnonymousBoolean, String paramAnonymousString3) {}
        
        public void hideAddressBar() {}
        
        public void invalidate() {}
        
        public boolean notifyAutoAudioPlay(String paramAnonymousString, JsResult paramAnonymousJsResult)
        {
          return false;
        }
        
        public void onDoubleTapStart() {}
        
        public void onFlingScrollBegin(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public void onFlingScrollEnd() {}
        
        public void onHideListBox() {}
        
        public void onHistoryItemChanged() {}
        
        public void onInputBoxTextChanged(IX5WebViewExtension paramAnonymousIX5WebViewExtension, String paramAnonymousString) {}
        
        public boolean onInterceptTouchEvent(MotionEvent paramAnonymousMotionEvent, View paramAnonymousView)
        {
          return false;
        }
        
        public void onMetricsSavedCountReceived(String paramAnonymousString1, boolean paramAnonymousBoolean, long paramAnonymousLong, String paramAnonymousString2, int paramAnonymousInt) {}
        
        public Object onMiscCallBack(String paramAnonymousString, Bundle paramAnonymousBundle)
        {
          return null;
        }
        
        public Object onMiscCallBack(String paramAnonymousString, Bundle paramAnonymousBundle, Object paramAnonymousObject1, Object paramAnonymousObject2, Object paramAnonymousObject3, Object paramAnonymousObject4)
        {
          return null;
        }
        
        public void onMissingPluginClicked(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, int paramAnonymousInt) {}
        
        public void onNativeCrashReport(int paramAnonymousInt, String paramAnonymousString) {}
        
        public void onOverScrolled(int paramAnonymousInt1, int paramAnonymousInt2, boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, View paramAnonymousView) {}
        
        public void onPinchToZoomStart() {}
        
        public void onPreReadFinished() {}
        
        public void onPromptScaleSaved() {}
        
        public void onReportAdFilterInfo(int paramAnonymousInt1, int paramAnonymousInt2, String paramAnonymousString, boolean paramAnonymousBoolean) {}
        
        public void onReportHtmlInfo(int paramAnonymousInt, String paramAnonymousString) {}
        
        public void onResponseReceived(WebResourceRequest paramAnonymousWebResourceRequest, WebResourceResponse paramAnonymousWebResourceResponse, int paramAnonymousInt) {}
        
        public void onScrollChanged(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4) {}
        
        public void onScrollChanged(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, View paramAnonymousView) {}
        
        public void onSetButtonStatus(boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2) {}
        
        public void onShowListBox(String[] paramAnonymousArrayOfString, int[] paramAnonymousArrayOfInt1, int[] paramAnonymousArrayOfInt2, int paramAnonymousInt) {}
        
        public boolean onShowLongClickPopupMenu()
        {
          return false;
        }
        
        public void onShowMutilListBox(String[] paramAnonymousArrayOfString, int[] paramAnonymousArrayOfInt1, int[] paramAnonymousArrayOfInt2, int[] paramAnonymousArrayOfInt3) {}
        
        public void onSlidingTitleOffScreen(int paramAnonymousInt1, int paramAnonymousInt2) {}
        
        public void onSoftKeyBoardHide(int paramAnonymousInt) {}
        
        public void onSoftKeyBoardShow() {}
        
        public boolean onTouchEvent(MotionEvent paramAnonymousMotionEvent, View paramAnonymousView)
        {
          return false;
        }
        
        public void onTransitionToCommitted() {}
        
        public void onUploadProgressChange(int paramAnonymousInt1, int paramAnonymousInt2, String paramAnonymousString) {}
        
        public void onUploadProgressStart(int paramAnonymousInt) {}
        
        public void onUrlChange(String paramAnonymousString1, String paramAnonymousString2) {}
        
        public boolean overScrollBy(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8, boolean paramAnonymousBoolean, View paramAnonymousView)
        {
          return false;
        }
        
        public boolean preShouldOverrideUrlLoading(IX5WebViewExtension paramAnonymousIX5WebViewExtension, String paramAnonymousString)
        {
          return false;
        }
        
        public void showTranslateBubble(int paramAnonymousInt1, String paramAnonymousString1, String paramAnonymousString2, int paramAnonymousInt2) {}
      });
    }
  }
  
  public void destroy()
  {
    IX5WebViewBase localIX5WebViewBase = this.mWebView;
    if (localIX5WebViewBase != null) {
      localIX5WebViewBase.destroy();
    }
  }
  
  public Object getIX5WebViewBase()
  {
    return this.mWebView;
  }
  
  public Object getOpenJsBridge()
  {
    return null;
  }
  
  public View getView()
  {
    IX5WebViewBase localIX5WebViewBase = this.mWebView;
    if (localIX5WebViewBase != null) {
      return localIX5WebViewBase.getView();
    }
    return null;
  }
  
  public void loadUrl(String paramString)
  {
    IX5WebViewBase localIX5WebViewBase = this.mWebView;
    if (localIX5WebViewBase != null) {
      localIX5WebViewBase.loadUrl(paramString);
    }
  }
  
  public void onPause()
  {
    IX5WebViewBase localIX5WebViewBase = this.mWebView;
    if (localIX5WebViewBase != null) {
      localIX5WebViewBase.onPause();
    }
  }
  
  public void onResume()
  {
    IX5WebViewBase localIX5WebViewBase = this.mWebView;
    if (localIX5WebViewBase != null) {
      localIX5WebViewBase.onResume();
    }
  }
  
  public void reload()
  {
    IX5WebViewBase localIX5WebViewBase = this.mWebView;
    if (localIX5WebViewBase != null) {
      localIX5WebViewBase.reload();
    }
  }
  
  public void setCookie(String paramString, List<String> paramList)
  {
    WebCoreProxy.cookieManager_setAcceptCookie(true);
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      WebCoreProxy.cookieManager_setCookie(paramString, (String)paramList.next());
    }
    WebCoreProxy.cookieSyncManager_Sync();
  }
  
  public void setWebViewClient(IBrigeWebViewClientProxy paramIBrigeWebViewClientProxy)
  {
    this.mClient = paramIBrigeWebViewClientProxy;
  }
  
  private class TBSModuleBrigeWebViewClient
    implements IX5WebViewClient
  {
    private TBSModuleBrigeWebViewClient() {}
    
    public void doUpdateVisitedHistory(IX5WebViewBase paramIX5WebViewBase, String paramString, boolean paramBoolean) {}
    
    public void onContentSizeChanged(IX5WebViewBase paramIX5WebViewBase, int paramInt1, int paramInt2) {}
    
    public void onDetectedBlankScreen(IX5WebViewBase paramIX5WebViewBase, String paramString, int paramInt) {}
    
    public void onFormResubmission(IX5WebViewBase paramIX5WebViewBase, Message paramMessage1, Message paramMessage2) {}
    
    public void onLoadResource(IX5WebViewBase paramIX5WebViewBase, String paramString) {}
    
    public void onPageCommitVisible(IX5WebViewBase paramIX5WebViewBase, String paramString) {}
    
    public void onPageFinished(IX5WebViewBase paramIX5WebViewBase, int paramInt1, int paramInt2, String paramString)
    {
      paramIX5WebViewBase = new StringBuilder();
      paramIX5WebViewBase.append("onPageFinished: ");
      paramIX5WebViewBase.append(paramString);
      LogUtils.d("ModuleBridgeWebViewImpl", paramIX5WebViewBase.toString());
      if (TBSModuleBridgeWebViewImpl.this.mClient != null) {
        TBSModuleBridgeWebViewImpl.this.mClient.onPageFinished(TBSModuleBridgeWebViewImpl.this, paramInt1, paramInt2, paramString);
      }
    }
    
    public void onPageFinished(IX5WebViewBase paramIX5WebViewBase, String paramString)
    {
      paramIX5WebViewBase = new StringBuilder();
      paramIX5WebViewBase.append("onPageFinished: ");
      paramIX5WebViewBase.append(paramString);
      LogUtils.d("ModuleBridgeWebViewImpl", paramIX5WebViewBase.toString());
      if (TBSModuleBridgeWebViewImpl.this.mClient != null) {
        TBSModuleBridgeWebViewImpl.this.mClient.onPageFinished(TBSModuleBridgeWebViewImpl.this, 0, 0, paramString);
      }
    }
    
    public void onPageStarted(IX5WebViewBase paramIX5WebViewBase, int paramInt1, int paramInt2, String paramString, Bitmap paramBitmap)
    {
      paramIX5WebViewBase = new StringBuilder();
      paramIX5WebViewBase.append("onPageStarted: ");
      paramIX5WebViewBase.append(paramString);
      LogUtils.d("ModuleBridgeWebViewImpl", paramIX5WebViewBase.toString());
      if (TBSModuleBridgeWebViewImpl.this.mClient != null) {
        TBSModuleBridgeWebViewImpl.this.mClient.onPageStarted(TBSModuleBridgeWebViewImpl.this, paramInt1, paramInt2, paramString, paramBitmap);
      }
    }
    
    public void onPageStarted(IX5WebViewBase paramIX5WebViewBase, String paramString, Bitmap paramBitmap)
    {
      paramIX5WebViewBase = new StringBuilder();
      paramIX5WebViewBase.append("onPageStarted: ");
      paramIX5WebViewBase.append(paramString);
      LogUtils.d("ModuleBridgeWebViewImpl", paramIX5WebViewBase.toString());
      if (TBSModuleBridgeWebViewImpl.this.mClient != null) {
        TBSModuleBridgeWebViewImpl.this.mClient.onPageStarted(TBSModuleBridgeWebViewImpl.this, 0, 0, paramString, paramBitmap);
      }
    }
    
    public void onReceivedClientCertRequest(IX5WebViewBase paramIX5WebViewBase, ClientCertRequest paramClientCertRequest) {}
    
    public void onReceivedError(IX5WebViewBase paramIX5WebViewBase, int paramInt, String paramString1, String paramString2)
    {
      LogUtils.d("ModuleBridgeWebViewImpl", "onReceivedError");
      if (TBSModuleBridgeWebViewImpl.this.mClient != null) {
        TBSModuleBridgeWebViewImpl.this.mClient.onReceivedError(TBSModuleBridgeWebViewImpl.this, paramInt, paramString1, paramString2);
      }
    }
    
    public void onReceivedError(IX5WebViewBase paramIX5WebViewBase, WebResourceRequest paramWebResourceRequest, WebResourceError paramWebResourceError) {}
    
    public void onReceivedHttpAuthRequest(IX5WebViewBase paramIX5WebViewBase, HttpAuthHandler paramHttpAuthHandler, String paramString1, String paramString2) {}
    
    public void onReceivedHttpError(IX5WebViewBase paramIX5WebViewBase, WebResourceRequest paramWebResourceRequest, WebResourceResponse paramWebResourceResponse) {}
    
    public void onReceivedLoginRequest(IX5WebViewBase paramIX5WebViewBase, String paramString1, String paramString2, String paramString3) {}
    
    public void onReceivedSslError(IX5WebViewBase paramIX5WebViewBase, SslErrorHandler paramSslErrorHandler, SslError paramSslError) {}
    
    public void onScaleChanged(IX5WebViewBase paramIX5WebViewBase, float paramFloat1, float paramFloat2) {}
    
    public void onTooManyRedirects(IX5WebViewBase paramIX5WebViewBase, Message paramMessage1, Message paramMessage2) {}
    
    public void onUnhandledKeyEvent(IX5WebViewBase paramIX5WebViewBase, KeyEvent paramKeyEvent) {}
    
    public WebResourceResponse shouldInterceptRequest(IX5WebViewBase paramIX5WebViewBase, WebResourceRequest paramWebResourceRequest)
    {
      return null;
    }
    
    public WebResourceResponse shouldInterceptRequest(IX5WebViewBase paramIX5WebViewBase, WebResourceRequest paramWebResourceRequest, Bundle paramBundle)
    {
      return null;
    }
    
    public WebResourceResponse shouldInterceptRequest(IX5WebViewBase paramIX5WebViewBase, String paramString)
    {
      return null;
    }
    
    public boolean shouldOverrideKeyEvent(IX5WebViewBase paramIX5WebViewBase, KeyEvent paramKeyEvent)
    {
      return false;
    }
    
    public boolean shouldOverrideUrlLoading(IX5WebViewBase paramIX5WebViewBase, WebResourceRequest paramWebResourceRequest)
    {
      paramIX5WebViewBase = new StringBuilder();
      paramIX5WebViewBase.append("shouldOverrideUrlLoading: ");
      paramIX5WebViewBase.append(paramWebResourceRequest.getUrl().toString());
      LogUtils.d("ModuleBridgeWebViewImpl", paramIX5WebViewBase.toString());
      return false;
    }
    
    public boolean shouldOverrideUrlLoading(IX5WebViewBase paramIX5WebViewBase, String paramString)
    {
      paramIX5WebViewBase = new StringBuilder();
      paramIX5WebViewBase.append("shouldOverrideUrlLoading ");
      paramIX5WebViewBase.append(paramString);
      LogUtils.d("ModuleBridgeWebViewImpl", paramIX5WebViewBase.toString());
      if (TBSModuleBridgeWebViewImpl.this.mClient != null) {
        return TBSModuleBridgeWebViewImpl.this.mClient.shouldOverrideUrlLoading(TBSModuleBridgeWebViewImpl.this, paramString);
      }
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\modulebridge\TBSModuleBridgeWebViewImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */