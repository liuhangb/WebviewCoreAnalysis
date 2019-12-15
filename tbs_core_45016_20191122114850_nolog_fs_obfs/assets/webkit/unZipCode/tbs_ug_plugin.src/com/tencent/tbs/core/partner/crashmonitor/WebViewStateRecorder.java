package com.tencent.tbs.core.partner.crashmonitor;

import com.tencent.tbs.core.webkit.WebView;

public class WebViewStateRecorder
  implements IWebViewStateChangeListener
{
  private Tombstone mTombstone;
  
  public WebViewStateRecorder(Tombstone paramTombstone)
  {
    this.mTombstone = paramTombstone;
  }
  
  private void updateWebViewState(boolean paramBoolean)
  {
    if (paramBoolean) {
      CrashMonitor.getInstance().updateCrashMonitorMessage();
    }
  }
  
  public void onWebViewAttachedToWindow(WebView paramWebView)
  {
    boolean bool;
    if (paramWebView != null) {
      bool = this.mTombstone.setWebViewState(paramWebView, Tombstone.WebViewState.WEBVIEW_ATTACHEDTOWINDOW);
    } else {
      bool = false;
    }
    updateWebViewState(bool);
  }
  
  public void onWebViewBackground(WebView paramWebView)
  {
    boolean bool;
    if (paramWebView != null) {
      bool = this.mTombstone.setWebViewState(paramWebView, Tombstone.WebViewState.WEBVIEW_BACKGROUND);
    } else {
      bool = false;
    }
    updateWebViewState(bool);
  }
  
  public void onWebViewDestroyed(WebView paramWebView)
  {
    boolean bool;
    if (paramWebView != null) {
      bool = this.mTombstone.setWebViewState(paramWebView, Tombstone.WebViewState.WEBVIEW_DESTROY);
    } else {
      bool = false;
    }
    updateWebViewState(bool);
  }
  
  public void onWebViewDetachedFromWindow(WebView paramWebView)
  {
    boolean bool;
    if (paramWebView != null) {
      bool = this.mTombstone.setWebViewState(paramWebView, Tombstone.WebViewState.WEBVIEW_DETACHEDFROMWINDOW);
    } else {
      bool = false;
    }
    updateWebViewState(bool);
  }
  
  public void onWebViewForeground(WebView paramWebView)
  {
    boolean bool;
    if (paramWebView != null) {
      bool = this.mTombstone.setWebViewState(paramWebView, Tombstone.WebViewState.WEBVIEW_FOREGROUND);
    } else {
      bool = false;
    }
    updateWebViewState(bool);
  }
  
  public void onWebViewInited(WebView paramWebView)
  {
    boolean bool;
    if (paramWebView != null) {
      bool = this.mTombstone.setWebViewState(paramWebView, Tombstone.WebViewState.WEBVIEW_INIT);
    } else {
      bool = false;
    }
    updateWebViewState(bool);
  }
  
  public void onWebViewPageFinished(WebView paramWebView)
  {
    if (paramWebView != null) {
      this.mTombstone.setLastLoadUrl(paramWebView.getUrl());
    }
    updateWebViewState(true);
  }
  
  public void onWebViewPageStarted(WebView paramWebView) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\crashmonitor\WebViewStateRecorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */