package com.tencent.tbs.core.webkit.tencent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewClient;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Map;
import org.chromium.base.annotations.UsedByReflection;

@UsedByReflection("WebCoreProxy.java")
public class TencentWebViewClientProxy
  implements IX5WebViewClient
{
  private static boolean mShouldUseWebResourceResponseNewInterface = true;
  private TencentWebViewClient tencentWebViewClient = new TencentWebViewClient();
  
  public void doUpdateVisitedHistory(IX5WebViewBase paramIX5WebViewBase, String paramString, boolean paramBoolean)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.doUpdateVisitedHistory(paramIX5WebViewBase, paramString, paramBoolean);
  }
  
  public void onContentSizeChanged(IX5WebViewBase paramIX5WebViewBase, int paramInt1, int paramInt2)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onContentSizeChanged(paramIX5WebViewBase, paramInt1, paramInt2);
  }
  
  public void onDetectedBlankScreen(IX5WebViewBase paramIX5WebViewBase, String paramString, int paramInt)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onDetectedBlankScreen(paramIX5WebViewBase, paramString, paramInt);
  }
  
  public void onFormResubmission(IX5WebViewBase paramIX5WebViewBase, Message paramMessage1, Message paramMessage2)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onFormResubmission(paramIX5WebViewBase, paramMessage1, paramMessage2);
  }
  
  public void onLoadResource(IX5WebViewBase paramIX5WebViewBase, String paramString)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onLoadResource(paramIX5WebViewBase, paramString);
  }
  
  public void onPageCommitVisible(IX5WebViewBase paramIX5WebViewBase, String paramString)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onPageCommitVisible(paramIX5WebViewBase, paramString);
  }
  
  public void onPageFinished(IX5WebViewBase paramIX5WebViewBase, int paramInt1, int paramInt2, String paramString)
  {
    onPageFinished(paramIX5WebViewBase, paramString);
  }
  
  public void onPageFinished(IX5WebViewBase paramIX5WebViewBase, String paramString)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onPageFinished(paramIX5WebViewBase, paramString);
  }
  
  public void onPageStarted(IX5WebViewBase paramIX5WebViewBase, int paramInt1, int paramInt2, String paramString, Bitmap paramBitmap)
  {
    onPageStarted(paramIX5WebViewBase, paramString, paramBitmap);
  }
  
  public void onPageStarted(IX5WebViewBase paramIX5WebViewBase, String paramString, Bitmap paramBitmap)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onPageStarted(paramIX5WebViewBase, paramString, paramBitmap);
  }
  
  public void onReceivedClientCertRequest(IX5WebViewBase paramIX5WebViewBase, final com.tencent.smtt.export.external.interfaces.ClientCertRequest paramClientCertRequest)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    Object localObject = null;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    if (paramClientCertRequest == null) {
      paramClientCertRequest = (com.tencent.smtt.export.external.interfaces.ClientCertRequest)localObject;
    } else {
      paramClientCertRequest = new com.tencent.tbs.core.webkit.ClientCertRequest()
      {
        public void cancel()
        {
          paramClientCertRequest.cancel();
        }
        
        public String getHost()
        {
          return paramClientCertRequest.getHost();
        }
        
        public String[] getKeyTypes()
        {
          return paramClientCertRequest.getKeyTypes();
        }
        
        public int getPort()
        {
          return paramClientCertRequest.getPort();
        }
        
        public Principal[] getPrincipals()
        {
          return paramClientCertRequest.getPrincipals();
        }
        
        public void ignore()
        {
          paramClientCertRequest.ignore();
        }
        
        public void proceed(PrivateKey paramAnonymousPrivateKey, X509Certificate[] paramAnonymousArrayOfX509Certificate)
        {
          paramClientCertRequest.proceed(paramAnonymousPrivateKey, paramAnonymousArrayOfX509Certificate);
        }
      };
    }
    localTencentWebViewClient.onReceivedClientCertRequest(paramIX5WebViewBase, paramClientCertRequest);
  }
  
  public void onReceivedError(IX5WebViewBase paramIX5WebViewBase, int paramInt, String paramString1, String paramString2)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onReceivedError(paramIX5WebViewBase, paramInt, paramString1, paramString2);
  }
  
  public void onReceivedError(IX5WebViewBase paramIX5WebViewBase, com.tencent.smtt.export.external.interfaces.WebResourceRequest paramWebResourceRequest, com.tencent.smtt.export.external.interfaces.WebResourceError paramWebResourceError)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    Object localObject = null;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    if (paramWebResourceRequest == null) {
      paramWebResourceRequest = null;
    } else {
      paramWebResourceRequest = new WebResourceRequestProxy(paramWebResourceRequest);
    }
    if (paramWebResourceError == null) {
      paramWebResourceError = (com.tencent.smtt.export.external.interfaces.WebResourceError)localObject;
    } else {
      paramWebResourceError = new WebResourceErrorProxy(paramWebResourceError);
    }
    localTencentWebViewClient.onReceivedError(paramIX5WebViewBase, paramWebResourceRequest, paramWebResourceError);
  }
  
  public void onReceivedHttpAuthRequest(IX5WebViewBase paramIX5WebViewBase, final com.tencent.smtt.export.external.interfaces.HttpAuthHandler paramHttpAuthHandler, String paramString1, String paramString2)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    Object localObject = null;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    if (paramHttpAuthHandler == null) {
      paramHttpAuthHandler = (com.tencent.smtt.export.external.interfaces.HttpAuthHandler)localObject;
    } else {
      paramHttpAuthHandler = new com.tencent.tbs.core.webkit.HttpAuthHandler()
      {
        public void cancel()
        {
          paramHttpAuthHandler.cancel();
        }
        
        public void proceed(String paramAnonymousString1, String paramAnonymousString2)
        {
          paramHttpAuthHandler.proceed(paramAnonymousString1, paramAnonymousString2);
        }
        
        public boolean useHttpAuthUsernamePassword()
        {
          return paramHttpAuthHandler.useHttpAuthUsernamePassword();
        }
      };
    }
    localTencentWebViewClient.onReceivedHttpAuthRequest(paramIX5WebViewBase, paramHttpAuthHandler, paramString1, paramString2);
  }
  
  public void onReceivedHttpError(IX5WebViewBase paramIX5WebViewBase, com.tencent.smtt.export.external.interfaces.WebResourceRequest paramWebResourceRequest, com.tencent.smtt.export.external.interfaces.WebResourceResponse paramWebResourceResponse)
  {
    boolean bool = mShouldUseWebResourceResponseNewInterface;
    int i = 200;
    if (bool) {}
    try
    {
      int j = paramWebResourceResponse.getStatusCode();
      i = j;
      str = paramWebResourceResponse.getReasonPhrase();
      i = j;
    }
    catch (Throwable localThrowable)
    {
      String str;
      TencentWebViewClient localTencentWebViewClient;
      for (;;) {}
    }
    mShouldUseWebResourceResponseNewInterface = false;
    str = "";
    break label58;
    str = "";
    i = 200;
    label58:
    localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onReceivedHttpError(paramIX5WebViewBase, new WebResourceRequestProxy(paramWebResourceRequest), new com.tencent.tbs.core.webkit.WebResourceResponse(false, paramWebResourceResponse.getMimeType(), paramWebResourceResponse.getEncoding(), i, str, paramWebResourceResponse.getResponseHeaders(), paramWebResourceResponse.getData()));
  }
  
  public void onReceivedLoginRequest(IX5WebViewBase paramIX5WebViewBase, String paramString1, String paramString2, String paramString3)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onReceivedLoginRequest(paramIX5WebViewBase, paramString1, paramString2, paramString3);
  }
  
  public void onReceivedSslError(IX5WebViewBase paramIX5WebViewBase, final com.tencent.smtt.export.external.interfaces.SslErrorHandler paramSslErrorHandler, final com.tencent.smtt.export.external.interfaces.SslError paramSslError)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    if (paramSslErrorHandler == null) {
      paramSslErrorHandler = null;
    } else {
      paramSslErrorHandler = new com.tencent.tbs.core.webkit.SslErrorHandler()
      {
        public void cancel()
        {
          paramSslErrorHandler.cancel();
        }
        
        public void proceed()
        {
          paramSslErrorHandler.proceed();
        }
      };
    }
    if (paramSslError == null) {
      paramSslError = null;
    } else {
      paramSslError = new android.net.http.SslError(0, paramSslError.getCertificate(), "")
      {
        public boolean addError(int paramAnonymousInt)
        {
          return paramSslError.addError(paramAnonymousInt);
        }
        
        public SslCertificate getCertificate()
        {
          return paramSslError.getCertificate();
        }
        
        public int getPrimaryError()
        {
          return paramSslError.getPrimaryError();
        }
        
        public String getUrl()
        {
          try
          {
            String str = paramSslError.getUrl();
            return str;
          }
          catch (Throwable localThrowable)
          {
            localThrowable.printStackTrace();
          }
          return "";
        }
        
        public boolean hasError(int paramAnonymousInt)
        {
          return paramSslError.hasError(paramAnonymousInt);
        }
      };
    }
    localTencentWebViewClient.onReceivedSslError(paramIX5WebViewBase, paramSslErrorHandler, paramSslError);
  }
  
  public void onScaleChanged(IX5WebViewBase paramIX5WebViewBase, float paramFloat1, float paramFloat2)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onScaleChanged(paramIX5WebViewBase, paramFloat1, paramFloat2);
  }
  
  public void onTooManyRedirects(IX5WebViewBase paramIX5WebViewBase, Message paramMessage1, Message paramMessage2)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onTooManyRedirects(paramIX5WebViewBase, paramMessage1, paramMessage2);
  }
  
  public void onUnhandledKeyEvent(IX5WebViewBase paramIX5WebViewBase, KeyEvent paramKeyEvent)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    localTencentWebViewClient.onUnhandledKeyEvent(paramIX5WebViewBase, paramKeyEvent);
  }
  
  public com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(IX5WebViewBase paramIX5WebViewBase, com.tencent.smtt.export.external.interfaces.WebResourceRequest paramWebResourceRequest)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    if (paramWebResourceRequest == null) {
      paramWebResourceRequest = null;
    } else {
      paramWebResourceRequest = new WebResourceRequestProxy(paramWebResourceRequest);
    }
    paramWebResourceRequest = localTencentWebViewClient.shouldInterceptRequest(paramIX5WebViewBase, paramWebResourceRequest);
    if (paramWebResourceRequest == null) {
      return null;
    }
    paramIX5WebViewBase = new com.tencent.smtt.export.external.interfaces.WebResourceResponse();
    paramIX5WebViewBase.setData(paramWebResourceRequest.getData());
    paramIX5WebViewBase.setEncoding(paramWebResourceRequest.getEncoding());
    paramIX5WebViewBase.setMimeType(paramWebResourceRequest.getMimeType());
    paramIX5WebViewBase.setResponseHeaders(paramWebResourceRequest.getResponseHeaders());
    if (mShouldUseWebResourceResponseNewInterface) {}
    try
    {
      paramIX5WebViewBase.setStatusCodeAndReasonPhrase(paramWebResourceRequest.getStatusCode(), paramWebResourceRequest.getReasonPhrase());
      return paramIX5WebViewBase;
    }
    catch (Exception paramWebResourceRequest)
    {
      for (;;) {}
    }
    mShouldUseWebResourceResponseNewInterface = false;
    return paramIX5WebViewBase;
  }
  
  public com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(IX5WebViewBase paramIX5WebViewBase, com.tencent.smtt.export.external.interfaces.WebResourceRequest paramWebResourceRequest, Bundle paramBundle)
  {
    return shouldInterceptRequest(paramIX5WebViewBase, paramWebResourceRequest);
  }
  
  public com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(IX5WebViewBase paramIX5WebViewBase, String paramString)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    paramString = localTencentWebViewClient.shouldInterceptRequest(paramIX5WebViewBase, paramString);
    if (paramString == null) {
      return null;
    }
    paramIX5WebViewBase = new com.tencent.smtt.export.external.interfaces.WebResourceResponse();
    paramIX5WebViewBase.setData(paramString.getData());
    paramIX5WebViewBase.setEncoding(paramString.getEncoding());
    paramIX5WebViewBase.setMimeType(paramString.getMimeType());
    paramIX5WebViewBase.setResponseHeaders(paramString.getResponseHeaders());
    if (mShouldUseWebResourceResponseNewInterface) {}
    try
    {
      paramIX5WebViewBase.setStatusCodeAndReasonPhrase(paramString.getStatusCode(), paramString.getReasonPhrase());
      return paramIX5WebViewBase;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    mShouldUseWebResourceResponseNewInterface = false;
    return paramIX5WebViewBase;
  }
  
  public boolean shouldOverrideKeyEvent(IX5WebViewBase paramIX5WebViewBase, KeyEvent paramKeyEvent)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    return localTencentWebViewClient.shouldOverrideKeyEvent(paramIX5WebViewBase, paramKeyEvent);
  }
  
  public boolean shouldOverrideUrlLoading(IX5WebViewBase paramIX5WebViewBase, com.tencent.smtt.export.external.interfaces.WebResourceRequest paramWebResourceRequest)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    Object localObject = null;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    if (paramWebResourceRequest == null) {
      paramWebResourceRequest = (com.tencent.smtt.export.external.interfaces.WebResourceRequest)localObject;
    } else {
      paramWebResourceRequest = new WebResourceRequestProxy(paramWebResourceRequest);
    }
    return localTencentWebViewClient.shouldOverrideUrlLoading(paramIX5WebViewBase, paramWebResourceRequest);
  }
  
  public boolean shouldOverrideUrlLoading(IX5WebViewBase paramIX5WebViewBase, String paramString)
  {
    TencentWebViewClient localTencentWebViewClient = this.tencentWebViewClient;
    if (paramIX5WebViewBase == null) {
      paramIX5WebViewBase = null;
    } else {
      paramIX5WebViewBase = ((TencentWebViewProxy)paramIX5WebViewBase).getRealWebView();
    }
    return localTencentWebViewClient.shouldOverrideUrlLoading(paramIX5WebViewBase, paramString);
  }
  
  private static class WebResourceErrorProxy
    extends com.tencent.tbs.core.webkit.WebResourceError
  {
    private com.tencent.smtt.export.external.interfaces.WebResourceError realWebResourceError;
    
    public WebResourceErrorProxy(com.tencent.smtt.export.external.interfaces.WebResourceError paramWebResourceError)
    {
      this.realWebResourceError = paramWebResourceError;
    }
    
    public CharSequence getDescription()
    {
      return this.realWebResourceError.getDescription();
    }
    
    public int getErrorCode()
    {
      return this.realWebResourceError.getErrorCode();
    }
  }
  
  private static class WebResourceRequestProxy
    implements com.tencent.tbs.core.webkit.WebResourceRequest
  {
    private com.tencent.smtt.export.external.interfaces.WebResourceRequest realWebResourceRequest;
    
    public WebResourceRequestProxy(com.tencent.smtt.export.external.interfaces.WebResourceRequest paramWebResourceRequest)
    {
      this.realWebResourceRequest = paramWebResourceRequest;
    }
    
    public String getMethod()
    {
      return this.realWebResourceRequest.getMethod();
    }
    
    public Map<String, String> getRequestHeaders()
    {
      return this.realWebResourceRequest.getRequestHeaders();
    }
    
    public Uri getUrl()
    {
      return this.realWebResourceRequest.getUrl();
    }
    
    public boolean hasGesture()
    {
      return this.realWebResourceRequest.hasGesture();
    }
    
    public boolean isForMainFrame()
    {
      return this.realWebResourceRequest.isForMainFrame();
    }
    
    public boolean isRedirect()
    {
      return this.realWebResourceRequest.isRedirect();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentWebViewClientProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */