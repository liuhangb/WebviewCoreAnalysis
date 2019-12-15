package com.tencent.tbs.core.partner.crashmonitor;

import com.tencent.tbs.core.webkit.WebView;

public abstract interface IWebViewStateChangeListener
{
  public abstract void onWebViewAttachedToWindow(WebView paramWebView);
  
  public abstract void onWebViewBackground(WebView paramWebView);
  
  public abstract void onWebViewDestroyed(WebView paramWebView);
  
  public abstract void onWebViewDetachedFromWindow(WebView paramWebView);
  
  public abstract void onWebViewForeground(WebView paramWebView);
  
  public abstract void onWebViewInited(WebView paramWebView);
  
  public abstract void onWebViewPageFinished(WebView paramWebView);
  
  public abstract void onWebViewPageStarted(WebView paramWebView);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\crashmonitor\IWebViewStateChangeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */