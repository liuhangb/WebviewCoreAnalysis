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
import com.tencent.smtt.export.external.interfaces.QuotaUpdater;
import com.tencent.tbs.core.webkit.GeolocationPermissions.Callback;
import com.tencent.tbs.core.webkit.JsResult.ResultReceiver;
import com.tencent.tbs.core.webkit.WebChromeClient;
import com.tencent.tbs.core.webkit.WebChromeClient.CustomViewCallback;
import com.tencent.tbs.core.webkit.WebChromeClient.FileChooserParams;
import com.tencent.tbs.core.webkit.WebStorage.QuotaUpdater;
import org.chromium.base.annotations.UsedByReflection;

@UsedByReflection("WebCoreProxy.java")
public class TencentWebChromeClientProxy
  implements IX5WebChromeClient
{
  private WebChromeClient mWebChromeClient;
  
  @UsedByReflection("WebCoreProxy.java")
  public TencentWebChromeClientProxy()
  {
    this.mWebChromeClient = new WebChromeClient();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public TencentWebChromeClientProxy(WebChromeClient paramWebChromeClient)
  {
    this.mWebChromeClient = paramWebChromeClient;
  }
  
  public Bitmap getDefaultVideoPoster()
  {
    return this.mWebChromeClient.getDefaultVideoPoster();
  }
  
  public void getVisitedHistory(final android.webkit.ValueCallback<String[]> paramValueCallback)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    if (paramValueCallback == null) {
      paramValueCallback = null;
    } else {
      paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
      {
        public void onReceiveValue(String[] paramAnonymousArrayOfString)
        {
          paramValueCallback.onReceiveValue(paramAnonymousArrayOfString);
        }
      };
    }
    localWebChromeClient.getVisitedHistory(paramValueCallback);
  }
  
  public void onCloseWindow(IX5WebViewBase paramIX5WebViewBase)
  {
    this.mWebChromeClient.onCloseWindow((TencentWebViewProxy.InnerWebView)paramIX5WebViewBase.getView());
  }
  
  public void onConsoleMessage(String paramString1, int paramInt, String paramString2)
  {
    this.mWebChromeClient.onConsoleMessage(paramString1, paramInt, paramString2);
  }
  
  public boolean onConsoleMessage(com.tencent.smtt.export.external.interfaces.ConsoleMessage paramConsoleMessage)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    if (paramConsoleMessage == null) {
      paramConsoleMessage = null;
    } else {
      paramConsoleMessage = new com.tencent.tbs.core.webkit.ConsoleMessage(paramConsoleMessage.message(), paramConsoleMessage.sourceId(), paramConsoleMessage.lineNumber(), com.tencent.tbs.core.webkit.ConsoleMessage.MessageLevel.valueOf(paramConsoleMessage.messageLevel().name()));
    }
    return localWebChromeClient.onConsoleMessage(paramConsoleMessage);
  }
  
  public boolean onCreateWindow(IX5WebViewBase paramIX5WebViewBase, boolean paramBoolean1, boolean paramBoolean2, Message paramMessage)
  {
    return this.mWebChromeClient.onCreateWindow((TencentWebViewProxy.InnerWebView)paramIX5WebViewBase.getView(), paramBoolean1, paramBoolean2, paramMessage);
  }
  
  public void onExceededDatabaseQuota(String paramString1, String paramString2, long paramLong1, long paramLong2, long paramLong3, final QuotaUpdater paramQuotaUpdater)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    if (paramQuotaUpdater == null) {
      paramQuotaUpdater = null;
    } else {
      paramQuotaUpdater = new WebStorage.QuotaUpdater()
      {
        public void updateQuota(long paramAnonymousLong)
        {
          paramQuotaUpdater.updateQuota(paramAnonymousLong);
        }
      };
    }
    localWebChromeClient.onExceededDatabaseQuota(paramString1, paramString2, paramLong1, paramLong2, paramLong3, paramQuotaUpdater);
  }
  
  public void onGeolocationPermissionsHidePrompt()
  {
    this.mWebChromeClient.onGeolocationPermissionsHidePrompt();
  }
  
  public void onGeolocationPermissionsShowPrompt(String paramString, final GeolocationPermissionsCallback paramGeolocationPermissionsCallback)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    if (paramGeolocationPermissionsCallback == null) {
      paramGeolocationPermissionsCallback = null;
    } else {
      paramGeolocationPermissionsCallback = new GeolocationPermissions.Callback()
      {
        public void invoke(String paramAnonymousString, boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2)
        {
          paramGeolocationPermissionsCallback.invoke(paramAnonymousString, paramAnonymousBoolean1, paramAnonymousBoolean2);
        }
      };
    }
    localWebChromeClient.onGeolocationPermissionsShowPrompt(paramString, paramGeolocationPermissionsCallback);
  }
  
  public void onGeolocationStartUpdating(final android.webkit.ValueCallback<Location> paramValueCallback, final android.webkit.ValueCallback<Bundle> paramValueCallback1)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    if ((localWebChromeClient instanceof TencentWebChromeClient))
    {
      TencentWebChromeClient localTencentWebChromeClient = (TencentWebChromeClient)localWebChromeClient;
      localWebChromeClient = null;
      if (paramValueCallback == null) {
        paramValueCallback = null;
      } else {
        paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
        {
          public void onReceiveValue(Location paramAnonymousLocation)
          {
            paramValueCallback.onReceiveValue(paramAnonymousLocation);
          }
        };
      }
      if (paramValueCallback1 == null) {
        paramValueCallback1 = localWebChromeClient;
      } else {
        paramValueCallback1 = new com.tencent.tbs.core.webkit.ValueCallback()
        {
          public void onReceiveValue(Bundle paramAnonymousBundle)
          {
            paramValueCallback1.onReceiveValue(paramAnonymousBundle);
          }
        };
      }
      localTencentWebChromeClient.onGeolocationStartUpdating(paramValueCallback, paramValueCallback1);
    }
  }
  
  public void onGeolocationStopUpdating()
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    if ((localWebChromeClient instanceof TencentWebChromeClient)) {
      ((TencentWebChromeClient)localWebChromeClient).onGeolocationStopUpdating();
    }
  }
  
  public void onHideCustomView()
  {
    this.mWebChromeClient.onHideCustomView();
  }
  
  public boolean onJsAlert(IX5WebViewBase paramIX5WebViewBase, String paramString1, String paramString2, final com.tencent.smtt.export.external.interfaces.JsResult paramJsResult)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    TencentWebViewProxy.InnerWebView localInnerWebView = (TencentWebViewProxy.InnerWebView)paramIX5WebViewBase.getView();
    paramIX5WebViewBase = null;
    if (paramJsResult != null) {
      paramIX5WebViewBase = new com.tencent.tbs.core.webkit.JsResult(null)
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
    return localWebChromeClient.onJsAlert(localInnerWebView, paramString1, paramString2, paramIX5WebViewBase);
  }
  
  public boolean onJsBeforeUnload(IX5WebViewBase paramIX5WebViewBase, String paramString1, String paramString2, final com.tencent.smtt.export.external.interfaces.JsResult paramJsResult)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    TencentWebViewProxy.InnerWebView localInnerWebView = (TencentWebViewProxy.InnerWebView)paramIX5WebViewBase.getView();
    paramIX5WebViewBase = null;
    if (paramJsResult != null) {
      paramIX5WebViewBase = new com.tencent.tbs.core.webkit.JsResult(null)
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
    return localWebChromeClient.onJsBeforeUnload(localInnerWebView, paramString1, paramString2, paramIX5WebViewBase);
  }
  
  public boolean onJsConfirm(IX5WebViewBase paramIX5WebViewBase, String paramString1, String paramString2, final com.tencent.smtt.export.external.interfaces.JsResult paramJsResult)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    TencentWebViewProxy.InnerWebView localInnerWebView = (TencentWebViewProxy.InnerWebView)paramIX5WebViewBase.getView();
    paramIX5WebViewBase = null;
    if (paramJsResult != null) {
      paramIX5WebViewBase = new com.tencent.tbs.core.webkit.JsResult(null)
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
    return localWebChromeClient.onJsConfirm(localInnerWebView, paramString1, paramString2, paramIX5WebViewBase);
  }
  
  public boolean onJsPrompt(IX5WebViewBase paramIX5WebViewBase, String paramString1, String paramString2, String paramString3, final com.tencent.smtt.export.external.interfaces.JsPromptResult paramJsPromptResult)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    TencentWebViewProxy.InnerWebView localInnerWebView = (TencentWebViewProxy.InnerWebView)paramIX5WebViewBase.getView();
    if (paramJsPromptResult == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = new com.tencent.tbs.core.webkit.JsPromptResult(null)
      {
        public void confirm(String paramAnonymousString)
        {
          paramJsPromptResult.confirm(paramAnonymousString);
        }
      };
    }
    return localWebChromeClient.onJsPrompt(localInnerWebView, paramString1, paramString2, paramString3, paramIX5WebViewBase);
  }
  
  public boolean onJsTimeout()
  {
    return this.mWebChromeClient.onJsTimeout();
  }
  
  public void onProgressChanged(IX5WebViewBase paramIX5WebViewBase, int paramInt)
  {
    this.mWebChromeClient.onProgressChanged((TencentWebViewProxy.InnerWebView)paramIX5WebViewBase.getView(), paramInt);
  }
  
  public void onReachedMaxAppCacheSize(long paramLong1, long paramLong2, final QuotaUpdater paramQuotaUpdater)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    if (paramQuotaUpdater == null) {
      paramQuotaUpdater = null;
    } else {
      paramQuotaUpdater = new WebStorage.QuotaUpdater()
      {
        public void updateQuota(long paramAnonymousLong)
        {
          paramQuotaUpdater.updateQuota(paramAnonymousLong);
        }
      };
    }
    localWebChromeClient.onReachedMaxAppCacheSize(paramLong1, paramLong2, paramQuotaUpdater);
  }
  
  public void onReceivedIcon(IX5WebViewBase paramIX5WebViewBase, Bitmap paramBitmap)
  {
    this.mWebChromeClient.onReceivedIcon((TencentWebViewProxy.InnerWebView)paramIX5WebViewBase.getView(), paramBitmap);
  }
  
  public void onReceivedTitle(IX5WebViewBase paramIX5WebViewBase, String paramString)
  {
    this.mWebChromeClient.onReceivedTitle((TencentWebViewProxy.InnerWebView)paramIX5WebViewBase.getView(), paramString);
  }
  
  public void onReceivedTouchIconUrl(IX5WebViewBase paramIX5WebViewBase, String paramString, boolean paramBoolean)
  {
    this.mWebChromeClient.onReceivedTouchIconUrl((TencentWebViewProxy.InnerWebView)paramIX5WebViewBase.getView(), paramString, paramBoolean);
  }
  
  public void onRequestFocus(IX5WebViewBase paramIX5WebViewBase)
  {
    this.mWebChromeClient.onRequestFocus((TencentWebViewProxy.InnerWebView)paramIX5WebViewBase.getView());
  }
  
  public void onShowCustomView(View paramView, int paramInt, final IX5WebChromeClient.CustomViewCallback paramCustomViewCallback)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    if (paramCustomViewCallback == null) {
      paramCustomViewCallback = null;
    } else {
      paramCustomViewCallback = new WebChromeClient.CustomViewCallback()
      {
        public void onCustomViewHidden()
        {
          paramCustomViewCallback.onCustomViewHidden();
        }
      };
    }
    localWebChromeClient.onShowCustomView(paramView, paramInt, paramCustomViewCallback);
  }
  
  public void onShowCustomView(View paramView, final IX5WebChromeClient.CustomViewCallback paramCustomViewCallback)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    if (paramCustomViewCallback == null) {
      paramCustomViewCallback = null;
    } else {
      paramCustomViewCallback = new WebChromeClient.CustomViewCallback()
      {
        public void onCustomViewHidden()
        {
          paramCustomViewCallback.onCustomViewHidden();
        }
      };
    }
    localWebChromeClient.onShowCustomView(paramView, paramCustomViewCallback);
  }
  
  public boolean onShowFileChooser(IX5WebViewBase paramIX5WebViewBase, final android.webkit.ValueCallback<Uri[]> paramValueCallback, final IX5WebChromeClient.FileChooserParams paramFileChooserParams)
  {
    WebChromeClient localWebChromeClient = this.mWebChromeClient;
    TencentWebViewProxy.InnerWebView localInnerWebView = (TencentWebViewProxy.InnerWebView)paramIX5WebViewBase.getView();
    Object localObject = null;
    if (paramValueCallback == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = new com.tencent.tbs.core.webkit.ValueCallback()
      {
        public void onReceiveValue(Uri[] paramAnonymousArrayOfUri)
        {
          paramValueCallback.onReceiveValue(paramAnonymousArrayOfUri);
        }
      };
    }
    if (paramFileChooserParams == null) {
      paramValueCallback = (android.webkit.ValueCallback<Uri[]>)localObject;
    } else {
      paramValueCallback = new WebChromeClient.FileChooserParams()
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
    return localWebChromeClient.onShowFileChooser(localInnerWebView, paramIX5WebViewBase, paramValueCallback);
  }
  
  public void openFileChooser(final android.webkit.ValueCallback<Uri[]> paramValueCallback, String paramString1, String paramString2, boolean paramBoolean)
  {
    Object localObject2 = this.mWebChromeClient;
    boolean bool = localObject2 instanceof TencentWebChromeClient;
    Object localObject1 = null;
    if (bool)
    {
      localObject2 = (TencentWebChromeClient)localObject2;
      if (paramValueCallback == null) {
        paramValueCallback = (android.webkit.ValueCallback<Uri[]>)localObject1;
      } else {
        paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
        {
          public void onReceiveValue(Uri[] paramAnonymousArrayOfUri)
          {
            paramValueCallback.onReceiveValue(paramAnonymousArrayOfUri);
          }
        };
      }
      ((TencentWebChromeClient)localObject2).openFileChooser(paramValueCallback, paramString1, paramString2, paramBoolean);
      return;
    }
    paramValueCallback.onReceiveValue(null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentWebChromeClientProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */