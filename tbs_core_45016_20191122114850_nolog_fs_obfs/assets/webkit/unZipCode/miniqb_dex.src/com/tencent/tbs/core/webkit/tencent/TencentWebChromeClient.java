package com.tencent.tbs.core.webkit.tencent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.FileChooserParams;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.WebViewTransport;
import com.tencent.smtt.export.external.interfaces.QuotaUpdater;
import com.tencent.tbs.core.webkit.GeolocationPermissions.Callback;
import com.tencent.tbs.core.webkit.PermissionRequest;
import com.tencent.tbs.core.webkit.WebChromeClient;
import com.tencent.tbs.core.webkit.WebChromeClient.CustomViewCallback;
import com.tencent.tbs.core.webkit.WebChromeClient.FileChooserParams;
import com.tencent.tbs.core.webkit.WebStorage.QuotaUpdater;
import com.tencent.tbs.core.webkit.WebView;

public class TencentWebChromeClient
  extends WebChromeClient
{
  private IX5WebChromeClient mX5WebChromeClient = null;
  private IX5WebViewBase mX5WebView = null;
  
  public TencentWebChromeClient(IX5WebViewBase paramIX5WebViewBase, IX5WebChromeClient paramIX5WebChromeClient)
  {
    this.mX5WebView = paramIX5WebViewBase;
    this.mX5WebChromeClient = paramIX5WebChromeClient;
  }
  
  public Bitmap getDefaultVideoPoster()
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null) {
      return localIX5WebChromeClient.getDefaultVideoPoster();
    }
    return super.getDefaultVideoPoster();
  }
  
  public View getVideoLoadingProgressView()
  {
    return super.getVideoLoadingProgressView();
  }
  
  public void getVisitedHistory(final com.tencent.tbs.core.webkit.ValueCallback<String[]> paramValueCallback)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      if (paramValueCallback == null) {
        paramValueCallback = null;
      } else {
        paramValueCallback = new android.webkit.ValueCallback()
        {
          public void onReceiveValue(String[] paramAnonymousArrayOfString)
          {
            paramValueCallback.onReceiveValue(paramAnonymousArrayOfString);
          }
        };
      }
      localIX5WebChromeClient.getVisitedHistory(paramValueCallback);
    }
  }
  
  public IX5WebChromeClient getX5WebChromeClient()
  {
    return this.mX5WebChromeClient;
  }
  
  public void onCloseWindow(WebView paramWebView)
  {
    paramWebView = this.mX5WebChromeClient;
    if (paramWebView != null) {
      paramWebView.onCloseWindow(this.mX5WebView);
    }
  }
  
  @Deprecated
  public void onConsoleMessage(String paramString1, int paramInt, String paramString2)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      localIX5WebChromeClient.onConsoleMessage(paramString1, paramInt, paramString2);
      return;
    }
    super.onConsoleMessage(paramString1, paramInt, paramString2);
  }
  
  public boolean onConsoleMessage(final com.tencent.tbs.core.webkit.ConsoleMessage paramConsoleMessage)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      if (paramConsoleMessage == null) {
        paramConsoleMessage = null;
      } else {
        paramConsoleMessage = new com.tencent.smtt.export.external.interfaces.ConsoleMessage()
        {
          public int lineNumber()
          {
            return paramConsoleMessage.lineNumber();
          }
          
          public String message()
          {
            return paramConsoleMessage.message();
          }
          
          public com.tencent.smtt.export.external.interfaces.ConsoleMessage.MessageLevel messageLevel()
          {
            return com.tencent.smtt.export.external.interfaces.ConsoleMessage.MessageLevel.valueOf(paramConsoleMessage.messageLevel().name());
          }
          
          public String sourceId()
          {
            return paramConsoleMessage.sourceId();
          }
        };
      }
      return localIX5WebChromeClient.onConsoleMessage(paramConsoleMessage);
    }
    return false;
  }
  
  public boolean onCreateWindow(WebView paramWebView, boolean paramBoolean1, boolean paramBoolean2, Message paramMessage)
  {
    if (this.mX5WebChromeClient != null)
    {
      paramMessage.obj = new IX5WebViewBase.WebViewTransport();
      return this.mX5WebChromeClient.onCreateWindow(this.mX5WebView, paramBoolean1, paramBoolean2, paramMessage);
    }
    return false;
  }
  
  public void onExceededDatabaseQuota(String paramString1, String paramString2, long paramLong1, long paramLong2, long paramLong3, final WebStorage.QuotaUpdater paramQuotaUpdater)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      if (paramQuotaUpdater == null) {
        paramQuotaUpdater = null;
      } else {
        paramQuotaUpdater = new QuotaUpdater()
        {
          public void updateQuota(long paramAnonymousLong)
          {
            paramQuotaUpdater.updateQuota(paramAnonymousLong);
          }
        };
      }
      localIX5WebChromeClient.onExceededDatabaseQuota(paramString1, paramString2, paramLong1, paramLong2, paramLong3, paramQuotaUpdater);
    }
  }
  
  public void onGeolocationPermissionsHidePrompt()
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      localIX5WebChromeClient.onGeolocationPermissionsHidePrompt();
      return;
    }
    super.onGeolocationPermissionsHidePrompt();
  }
  
  public void onGeolocationPermissionsShowPrompt(String paramString, final GeolocationPermissions.Callback paramCallback)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      if (paramCallback == null) {
        paramCallback = null;
      } else {
        paramCallback = new GeolocationPermissionsCallback()
        {
          public void invoke(String paramAnonymousString, boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2)
          {
            paramCallback.invoke(paramAnonymousString, paramAnonymousBoolean1, paramAnonymousBoolean2);
          }
        };
      }
      localIX5WebChromeClient.onGeolocationPermissionsShowPrompt(paramString, paramCallback);
    }
  }
  
  public void onGeolocationStartUpdating(final com.tencent.tbs.core.webkit.ValueCallback<Location> paramValueCallback, final com.tencent.tbs.core.webkit.ValueCallback<Bundle> paramValueCallback1)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      Object localObject = null;
      if (paramValueCallback == null) {
        paramValueCallback = null;
      } else {
        paramValueCallback = new android.webkit.ValueCallback()
        {
          public void onReceiveValue(Location paramAnonymousLocation)
          {
            paramValueCallback.onReceiveValue(paramAnonymousLocation);
          }
        };
      }
      if (paramValueCallback1 == null) {
        paramValueCallback1 = (com.tencent.tbs.core.webkit.ValueCallback<Bundle>)localObject;
      } else {
        paramValueCallback1 = new android.webkit.ValueCallback()
        {
          public void onReceiveValue(Bundle paramAnonymousBundle)
          {
            paramValueCallback1.onReceiveValue(paramAnonymousBundle);
          }
        };
      }
      localIX5WebChromeClient.onGeolocationStartUpdating(paramValueCallback, paramValueCallback1);
    }
  }
  
  public void onGeolocationStopUpdating()
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null) {
      localIX5WebChromeClient.onGeolocationStopUpdating();
    }
  }
  
  public void onHideCustomView()
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      localIX5WebChromeClient.onHideCustomView();
      return;
    }
    super.onHideCustomView();
  }
  
  public boolean onJsAlert(WebView paramWebView, String paramString1, String paramString2, final com.tencent.tbs.core.webkit.JsResult paramJsResult)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      IX5WebViewBase localIX5WebViewBase = this.mX5WebView;
      if (paramJsResult == null) {
        paramWebView = null;
      } else {
        paramWebView = new com.tencent.smtt.export.external.interfaces.JsResult()
        {
          public void cancel()
          {
            paramJsResult.cancel();
          }
          
          public void confirm()
          {
            paramJsResult.confirm();
          }
        };
      }
      return localIX5WebChromeClient.onJsAlert(localIX5WebViewBase, paramString1, paramString2, paramWebView);
    }
    return false;
  }
  
  public boolean onJsBeforeUnload(WebView paramWebView, String paramString1, String paramString2, final com.tencent.tbs.core.webkit.JsResult paramJsResult)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      IX5WebViewBase localIX5WebViewBase = this.mX5WebView;
      if (paramJsResult == null) {
        paramWebView = null;
      } else {
        paramWebView = new com.tencent.smtt.export.external.interfaces.JsResult()
        {
          public void cancel()
          {
            paramJsResult.cancel();
          }
          
          public void confirm()
          {
            paramJsResult.confirm();
          }
        };
      }
      return localIX5WebChromeClient.onJsBeforeUnload(localIX5WebViewBase, paramString1, paramString2, paramWebView);
    }
    return false;
  }
  
  public boolean onJsConfirm(WebView paramWebView, String paramString1, String paramString2, final com.tencent.tbs.core.webkit.JsResult paramJsResult)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      IX5WebViewBase localIX5WebViewBase = this.mX5WebView;
      if (paramJsResult == null) {
        paramWebView = null;
      } else {
        paramWebView = new com.tencent.smtt.export.external.interfaces.JsResult()
        {
          public void cancel()
          {
            paramJsResult.cancel();
          }
          
          public void confirm()
          {
            paramJsResult.confirm();
          }
        };
      }
      return localIX5WebChromeClient.onJsConfirm(localIX5WebViewBase, paramString1, paramString2, paramWebView);
    }
    return false;
  }
  
  public boolean onJsPrompt(WebView paramWebView, String paramString1, String paramString2, String paramString3, final com.tencent.tbs.core.webkit.JsPromptResult paramJsPromptResult)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      IX5WebViewBase localIX5WebViewBase = this.mX5WebView;
      if (paramJsPromptResult == null) {
        paramWebView = null;
      } else {
        paramWebView = new com.tencent.smtt.export.external.interfaces.JsPromptResult()
        {
          public void cancel()
          {
            paramJsPromptResult.cancel();
          }
          
          public void confirm()
          {
            paramJsPromptResult.confirm();
          }
          
          public void confirm(String paramAnonymousString)
          {
            paramJsPromptResult.confirm(paramAnonymousString);
          }
        };
      }
      return localIX5WebChromeClient.onJsPrompt(localIX5WebViewBase, paramString1, paramString2, paramString3, paramWebView);
    }
    return false;
  }
  
  public boolean onJsTimeout()
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null) {
      return localIX5WebChromeClient.onJsTimeout();
    }
    return super.onJsTimeout();
  }
  
  public void onPermissionRequest(PermissionRequest paramPermissionRequest)
  {
    super.onPermissionRequest(paramPermissionRequest);
  }
  
  public void onPermissionRequestCanceled(PermissionRequest paramPermissionRequest)
  {
    super.onPermissionRequestCanceled(paramPermissionRequest);
  }
  
  public void onProgressChanged(WebView paramWebView, int paramInt)
  {
    paramWebView = this.mX5WebChromeClient;
    if (paramWebView != null) {
      paramWebView.onProgressChanged(this.mX5WebView, paramInt);
    }
    paramWebView = this.mX5WebView;
    if ((paramWebView != null) && ((paramWebView instanceof TencentWebViewProxy))) {
      ((TencentWebViewProxy)paramWebView).onProgressChanged(paramInt);
    }
  }
  
  public void onReachedMaxAppCacheSize(long paramLong1, long paramLong2, final WebStorage.QuotaUpdater paramQuotaUpdater)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      if (paramQuotaUpdater == null) {
        paramQuotaUpdater = null;
      } else {
        paramQuotaUpdater = new QuotaUpdater()
        {
          public void updateQuota(long paramAnonymousLong)
          {
            paramQuotaUpdater.updateQuota(paramAnonymousLong);
          }
        };
      }
      localIX5WebChromeClient.onReachedMaxAppCacheSize(paramLong1, paramLong2, paramQuotaUpdater);
    }
  }
  
  public void onReceivedIcon(WebView paramWebView, Bitmap paramBitmap)
  {
    paramWebView = this.mX5WebChromeClient;
    if (paramWebView != null) {
      paramWebView.onReceivedIcon(this.mX5WebView, paramBitmap);
    }
  }
  
  public void onReceivedTitle(WebView paramWebView, String paramString)
  {
    paramWebView = this.mX5WebChromeClient;
    if (paramWebView != null) {
      paramWebView.onReceivedTitle(this.mX5WebView, paramString);
    }
  }
  
  public void onReceivedTouchIconUrl(WebView paramWebView, String paramString, boolean paramBoolean)
  {
    paramWebView = this.mX5WebChromeClient;
    if (paramWebView != null) {
      paramWebView.onReceivedTouchIconUrl(this.mX5WebView, paramString, paramBoolean);
    }
  }
  
  public void onRequestFocus(WebView paramWebView)
  {
    paramWebView = this.mX5WebChromeClient;
    if (paramWebView != null) {
      paramWebView.onRequestFocus(this.mX5WebView);
    }
  }
  
  public void onShowCustomView(View paramView, int paramInt, final WebChromeClient.CustomViewCallback paramCustomViewCallback)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      if (paramCustomViewCallback == null) {
        paramCustomViewCallback = null;
      } else {
        paramCustomViewCallback = new IX5WebChromeClient.CustomViewCallback()
        {
          public void onCustomViewHidden()
          {
            paramCustomViewCallback.onCustomViewHidden();
          }
        };
      }
      localIX5WebChromeClient.onShowCustomView(paramView, paramInt, paramCustomViewCallback);
    }
  }
  
  public void onShowCustomView(View paramView, final WebChromeClient.CustomViewCallback paramCustomViewCallback)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      if (paramCustomViewCallback == null) {
        paramCustomViewCallback = null;
      } else {
        paramCustomViewCallback = new IX5WebChromeClient.CustomViewCallback()
        {
          public void onCustomViewHidden()
          {
            paramCustomViewCallback.onCustomViewHidden();
          }
        };
      }
      localIX5WebChromeClient.onShowCustomView(paramView, paramCustomViewCallback);
    }
  }
  
  public boolean onShowFileChooser(WebView paramWebView, final com.tencent.tbs.core.webkit.ValueCallback<Uri[]> paramValueCallback, final WebChromeClient.FileChooserParams paramFileChooserParams)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      IX5WebViewBase localIX5WebViewBase = this.mX5WebView;
      Object localObject = null;
      if (paramValueCallback == null) {
        paramWebView = null;
      } else {
        paramWebView = new android.webkit.ValueCallback()
        {
          public void onReceiveValue(Uri[] paramAnonymousArrayOfUri)
          {
            paramValueCallback.onReceiveValue(paramAnonymousArrayOfUri);
          }
        };
      }
      if (paramFileChooserParams == null) {
        paramValueCallback = (com.tencent.tbs.core.webkit.ValueCallback<Uri[]>)localObject;
      } else {
        paramValueCallback = new IX5WebChromeClient.FileChooserParams()
        {
          public Intent createIntent()
          {
            return paramFileChooserParams.createIntent();
          }
          
          public String[] getAcceptTypes()
          {
            return paramFileChooserParams.getAcceptTypes();
          }
          
          public String getFilenameHint()
          {
            return paramFileChooserParams.getFilenameHint();
          }
          
          public int getMode()
          {
            return paramFileChooserParams.getMode();
          }
          
          public CharSequence getTitle()
          {
            return paramFileChooserParams.getTitle();
          }
          
          public boolean isCaptureEnabled()
          {
            return paramFileChooserParams.isCaptureEnabled();
          }
        };
      }
      return localIX5WebChromeClient.onShowFileChooser(localIX5WebViewBase, paramWebView, paramValueCallback);
    }
    return false;
  }
  
  @Deprecated
  public void openFileChooser(com.tencent.tbs.core.webkit.ValueCallback<Uri> paramValueCallback, String paramString1, String paramString2)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    if (localIX5WebChromeClient != null)
    {
      localIX5WebChromeClient.openFileChooser(new android.webkit.ValueCallback()
      {
        public void onReceiveValue(Uri[] paramAnonymousArrayOfUri) {}
      }, paramString1, paramString2, false);
      return;
    }
    super.openFileChooser(paramValueCallback, paramString1, paramString2);
  }
  
  public void openFileChooser(final com.tencent.tbs.core.webkit.ValueCallback<Uri[]> paramValueCallback, String paramString1, String paramString2, boolean paramBoolean)
  {
    IX5WebChromeClient localIX5WebChromeClient = this.mX5WebChromeClient;
    Object localObject = null;
    if (localIX5WebChromeClient != null)
    {
      if (paramValueCallback == null) {
        paramValueCallback = (com.tencent.tbs.core.webkit.ValueCallback<Uri[]>)localObject;
      } else {
        paramValueCallback = new android.webkit.ValueCallback()
        {
          public void onReceiveValue(Uri[] paramAnonymousArrayOfUri)
          {
            paramValueCallback.onReceiveValue(paramAnonymousArrayOfUri);
          }
        };
      }
      localIX5WebChromeClient.openFileChooser(paramValueCallback, paramString1, paramString2, paramBoolean);
      return;
    }
    paramValueCallback.onReceiveValue(null);
  }
  
  public void setupAutoFill(Message paramMessage)
  {
    super.setupAutoFill(paramMessage);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentWebChromeClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */