package com.tencent.tbs.core.partner.testutils;

import android.graphics.Bitmap;
import android.os.Message;
import android.view.View;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.QuotaUpdater;
import com.tencent.smtt.export.external.proxy.ProxyWebChromeClient;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;

class RobotiumWebClient
  extends ProxyWebChromeClient
{
  private IX5WebChromeClient originalWebChromeClient = null;
  private IX5WebChromeClient robotiumWebClient;
  WebElementCreator webElementCreator;
  
  public RobotiumWebClient(WebElementCreator paramWebElementCreator)
  {
    this.webElementCreator = paramWebElementCreator;
    this.robotiumWebClient = this;
  }
  
  public void enableJavascriptAndSetRobotiumWebClient(final TencentWebViewProxy paramTencentWebViewProxy, IX5WebChromeClient paramIX5WebChromeClient)
  {
    this.originalWebChromeClient = paramIX5WebChromeClient;
    paramTencentWebViewProxy.getRealWebView().post(new Runnable()
    {
      public void run()
      {
        paramTencentWebViewProxy.getSettings().setJavaScriptEnabled(true);
        paramTencentWebViewProxy.setWebChromeClient(RobotiumWebClient.this.robotiumWebClient);
      }
    });
  }
  
  public Bitmap getDefaultVideoPoster()
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      return localIX5WebChromeClient.getDefaultVideoPoster();
    }
    return null;
  }
  
  public void onCloseWindow(IX5WebViewBase paramIX5WebViewBase)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onCloseWindow(paramIX5WebViewBase);
    }
  }
  
  public void onConsoleMessage(String paramString1, int paramInt, String paramString2)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onConsoleMessage(paramString1, paramInt, paramString2);
    }
  }
  
  public boolean onConsoleMessage(ConsoleMessage paramConsoleMessage)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      return localIX5WebChromeClient.onConsoleMessage(paramConsoleMessage);
    }
    return true;
  }
  
  public boolean onCreateWindow(IX5WebViewBase paramIX5WebViewBase, boolean paramBoolean1, boolean paramBoolean2, Message paramMessage)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      return localIX5WebChromeClient.onCreateWindow(paramIX5WebViewBase, paramBoolean1, paramBoolean2, paramMessage);
    }
    return true;
  }
  
  public void onExceededDatabaseQuota(String paramString1, String paramString2, long paramLong1, long paramLong2, long paramLong3, QuotaUpdater paramQuotaUpdater)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onExceededDatabaseQuota(paramString1, paramString2, paramLong1, paramLong2, paramLong3, paramQuotaUpdater);
    }
  }
  
  public void onGeolocationPermissionsHidePrompt()
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onGeolocationPermissionsHidePrompt();
    }
  }
  
  public void onGeolocationPermissionsShowPrompt(String paramString, GeolocationPermissionsCallback paramGeolocationPermissionsCallback)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onGeolocationPermissionsShowPrompt(paramString, paramGeolocationPermissionsCallback);
    }
  }
  
  public void onHideCustomView()
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onHideCustomView();
    }
  }
  
  public boolean onJsAlert(IX5WebViewBase paramIX5WebViewBase, String paramString1, String paramString2, JsResult paramJsResult)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      return localIX5WebChromeClient.onJsAlert(paramIX5WebViewBase, paramString1, paramString2, paramJsResult);
    }
    return true;
  }
  
  public boolean onJsBeforeUnload(IX5WebViewBase paramIX5WebViewBase, String paramString1, String paramString2, JsResult paramJsResult)
  {
    if (this.originalWebChromeClient.onJsBeforeUnload(paramIX5WebViewBase, paramString1, paramString2, paramJsResult)) {
      return this.originalWebChromeClient.onJsBeforeUnload(paramIX5WebViewBase, paramString1, paramString2, paramJsResult);
    }
    return true;
  }
  
  public boolean onJsConfirm(IX5WebViewBase paramIX5WebViewBase, String paramString1, String paramString2, JsResult paramJsResult)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      return localIX5WebChromeClient.onJsConfirm(paramIX5WebViewBase, paramString1, paramString2, paramJsResult);
    }
    return true;
  }
  
  public boolean onJsPrompt(IX5WebViewBase paramIX5WebViewBase, String paramString1, String paramString2, String paramString3, JsPromptResult paramJsPromptResult)
  {
    if ((paramString2 != null) && ((paramString2.contains(";,")) || (paramString2.contains("robotium-finished"))))
    {
      if (paramString2.equals("robotium-finished")) {
        this.webElementCreator.setFinished(true);
      } else {
        this.webElementCreator.createWebElementAndAddInList(paramString2, (TencentWebViewProxy)paramIX5WebViewBase);
      }
      paramJsPromptResult.confirm();
      return true;
    }
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      return localIX5WebChromeClient.onJsPrompt(paramIX5WebViewBase, paramString1, paramString2, paramString3, paramJsPromptResult);
    }
    return true;
  }
  
  public boolean onJsTimeout()
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      return localIX5WebChromeClient.onJsTimeout();
    }
    return true;
  }
  
  public void onProgressChanged(IX5WebViewBase paramIX5WebViewBase, int paramInt)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onProgressChanged(paramIX5WebViewBase, paramInt);
    }
  }
  
  public void onReachedMaxAppCacheSize(long paramLong1, long paramLong2, QuotaUpdater paramQuotaUpdater)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onReachedMaxAppCacheSize(paramLong1, paramLong2, paramQuotaUpdater);
    }
  }
  
  public void onReceivedIcon(IX5WebViewBase paramIX5WebViewBase, Bitmap paramBitmap)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onReceivedIcon(paramIX5WebViewBase, paramBitmap);
    }
  }
  
  public void onReceivedTitle(IX5WebViewBase paramIX5WebViewBase, String paramString)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onReceivedTitle(paramIX5WebViewBase, paramString);
    }
  }
  
  public void onReceivedTouchIconUrl(IX5WebViewBase paramIX5WebViewBase, String paramString, boolean paramBoolean)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onReceivedTouchIconUrl(paramIX5WebViewBase, paramString, paramBoolean);
    }
  }
  
  public void onRequestFocus(IX5WebViewBase paramIX5WebViewBase)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onRequestFocus(paramIX5WebViewBase);
    }
  }
  
  public void onShowCustomView(View paramView, IX5WebChromeClient.CustomViewCallback paramCustomViewCallback)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.originalWebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onShowCustomView(paramView, paramCustomViewCallback);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\testutils\RobotiumWebClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */