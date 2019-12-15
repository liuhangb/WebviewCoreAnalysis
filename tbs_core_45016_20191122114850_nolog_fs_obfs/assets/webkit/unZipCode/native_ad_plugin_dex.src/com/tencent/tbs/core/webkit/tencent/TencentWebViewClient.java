package com.tencent.tbs.core.webkit.tencent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.ReflectionUtils;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewClient;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.smtt.livelog.a;
import com.tencent.smtt.net.AwNetworkUtils;
import com.tencent.smtt.net.PersistentSessionManager;
import com.tencent.smtt.net.h;
import com.tencent.smtt.webkit.AdInfoManager;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.WebViewProviderExtension;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.s;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.CookieManager;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.WebViewClient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;

public class TencentWebViewClient
  extends WebViewClient
{
  private static boolean mNewShouldInterceptRequestVersion = true;
  private static boolean mNewShouldInterceptRequestVersion_V2 = true;
  private static boolean mNewshouldOverrideUrlLoadingVersion = true;
  private static boolean mShouldUseWebResourceResponseNewInterface = true;
  private static boolean shouldShowSllErrorDialog = true;
  private static boolean skipgodCmd = true;
  private boolean domainListTestEnv = false;
  private Field field_AwWebResourceRequest = null;
  public String lastRequestUrl = "";
  private int mAbnormalRecoverPreState = 65535;
  private boolean mCompatibleOnPageStartedOrFinishedMethod = false;
  private Field mField = null;
  private Method mMethod = null;
  private Set<String> mRecoverUrls = new HashSet();
  private TencentWebViewProxy mX5WebView = null;
  private IX5WebViewClient mX5WebViewClient = null;
  public int onPageFinishCount = 0;
  private boolean preferenceTestEnv = false;
  
  public TencentWebViewClient() {}
  
  public TencentWebViewClient(TencentWebViewProxy paramTencentWebViewProxy, IX5WebViewClient paramIX5WebViewClient)
  {
    this.mX5WebView = paramTencentWebViewProxy;
    this.mX5WebViewClient = paramIX5WebViewClient;
  }
  
  private void countPVContentCache(String paramString)
  {
    try
    {
      if ((this.mMethod != null) && (this.mX5WebViewClient != null))
      {
        this.mMethod.invoke(this.mX5WebViewClient, new Object[] { paramString });
        return;
      }
      if ((this.mField != null) && (this.mX5WebView != null))
      {
        this.mField.setInt(this.mX5WebView.getRealWebView().getParent(), this.mField.getInt(this.mX5WebView.getRealWebView().getParent()) + 1);
        return;
      }
      Log.e("WebViewCallback", "countPVContentCache mMethod=null mField=null");
      return;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public static boolean isVpnUsed()
  {
    try
    {
      Object localObject = NetworkInterface.getNetworkInterfaces();
      if (localObject != null)
      {
        localObject = Collections.list((Enumeration)localObject).iterator();
        while (((Iterator)localObject).hasNext())
        {
          NetworkInterface localNetworkInterface = (NetworkInterface)((Iterator)localObject).next();
          if ((localNetworkInterface.isUp()) && (localNetworkInterface.getInterfaceAddresses().size() != 0)) {
            if (!"tun0".equals(localNetworkInterface.getName()))
            {
              boolean bool = "ppp0".equals(localNetworkInterface.getName());
              if (!bool) {
                break;
              }
            }
            else
            {
              return true;
            }
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return false;
  }
  
  public void OnContentCachePageSwapIn(String paramString)
  {
    countPVContentCache(paramString);
  }
  
  boolean canShowAlertDialog(Context paramContext)
  {
    return ((paramContext instanceof Activity)) && (!((Activity)paramContext).isFinishing());
  }
  
  public boolean countPVContentCacheCallBack()
  {
    if ((this.mMethod == null) && (this.mField == null))
    {
      localObject = this.mX5WebView;
      if ((localObject == null) || (!((IX5WebView)localObject).isMiniQB()))
      {
        if (!e.a().h()) {
          return true;
        }
        if (ContextHolder.getInstance().getSdkVersion() < 36844) {}
      }
    }
    try
    {
      if ((this.mX5WebViewClient != null) && (this.mX5WebView != null))
      {
        this.mMethod = this.mX5WebViewClient.getClass().getDeclaredMethod("countPVContentCacheCallBack", new Class[] { String.class });
        if (this.mMethod != null)
        {
          this.mMethod.setAccessible(true);
          return true;
        }
      }
    }
    catch (Throwable localThrowable1)
    {
      int j;
      int i;
      for (;;) {}
    }
    Object localObject = new String[6];
    localObject[0] = "ozh";
    localObject[1] = "ovT";
    localObject[2] = "oAV";
    localObject[3] = "njG";
    localObject[4] = "obr";
    localObject[5] = "nhW";
    j = localObject.length;
    i = 0;
    while (i < j)
    {
      String str = localObject[i];
      try
      {
        this.mField = Class.forName("com.tencent.smtt.sdk.WebView").getDeclaredField(str);
        if (this.mField != null)
        {
          this.mField.setAccessible(true);
          return true;
        }
      }
      catch (Throwable localThrowable2)
      {
        for (;;) {}
      }
      i += 1;
    }
    return false;
    return true;
  }
  
  public void doUpdateVisitedHistory(WebView paramWebView, String paramString, boolean paramBoolean)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null)
    {
      localIX5WebViewClient.doUpdateVisitedHistory(this.mX5WebView, paramString, paramBoolean);
      return;
    }
    super.doUpdateVisitedHistory(paramWebView, paramString, paramBoolean);
  }
  
  public IX5WebViewClient getX5WebViewClient()
  {
    return this.mX5WebViewClient;
  }
  
  public void onContentSizeChanged(WebView paramWebView, int paramInt1, int paramInt2)
  {
    paramWebView = this.mX5WebViewClient;
    if (paramWebView != null) {
      paramWebView.onContentSizeChanged(this.mX5WebView, paramInt1, paramInt2);
    }
    paramWebView = this.mX5WebView;
    if ((paramWebView != null) && (paramWebView.getH5VideoProxy() != null)) {
      this.mX5WebView.getH5VideoProxy().a(paramInt1, paramInt2);
    }
  }
  
  public void onDetectedBlankScreen(WebView paramWebView, String paramString, int paramInt)
  {
    paramWebView = this.mX5WebViewClient;
    if ((paramWebView != null) && (this.mX5WebView != null)) {
      try
      {
        paramWebView = paramWebView.getClass().getDeclaredMethod("onDetectedBlankScreen", new Class[] { IX5WebViewBase.class, String.class, Integer.TYPE });
        paramWebView.setAccessible(true);
        paramWebView.invoke(this.mX5WebViewClient, new Object[] { this.mX5WebView, paramString, Integer.valueOf(paramInt) });
        return;
      }
      catch (Throwable paramWebView)
      {
        paramWebView.printStackTrace();
      }
    }
  }
  
  public void onFormResubmission(WebView paramWebView, Message paramMessage1, Message paramMessage2)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null)
    {
      localIX5WebViewClient.onFormResubmission(this.mX5WebView, paramMessage1, paramMessage2);
      return;
    }
    super.onFormResubmission(paramWebView, paramMessage1, paramMessage2);
  }
  
  public void onLoadResource(WebView paramWebView, String paramString)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null)
    {
      localIX5WebViewClient.onLoadResource(this.mX5WebView, paramString);
      return;
    }
    super.onLoadResource(paramWebView, paramString);
  }
  
  public void onPageCommitVisible(WebView paramWebView, String paramString)
  {
    paramWebView = this.mX5WebViewClient;
    if ((paramWebView != null) && (this.mX5WebView != null)) {
      try
      {
        paramWebView = paramWebView.getClass().getDeclaredMethod("onPageCommitVisible", new Class[] { IX5WebViewBase.class, String.class });
        paramWebView.setAccessible(true);
        paramWebView.invoke(this.mX5WebViewClient, new Object[] { this.mX5WebView, paramString });
        return;
      }
      catch (Throwable paramWebView)
      {
        paramWebView.printStackTrace();
      }
    }
  }
  
  public void onPageFinished(WebView paramWebView, String paramString)
  {
    if (this.mX5WebViewClient != null)
    {
      if ((!paramString.isEmpty()) && (this.lastRequestUrl != paramString) && (SmttServiceProxy.getInstance().getEnablevConsole()))
      {
        this.lastRequestUrl = paramString;
        paramWebView = new StringBuilder();
        paramWebView.append("var newscript = document.createElement(\"script\");");
        paramWebView.append("newscript.src=\"http://wechatfe.github.io/vconsole/lib/vconsole.min.js?v=3.0.0.0\";");
        paramWebView = paramWebView.toString();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramWebView);
        localStringBuilder.append("newscript.onload=function(){var vConsole = new VConsole();console.log('-- debugx5-vconsole --');};");
        paramWebView = localStringBuilder.toString();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramWebView);
        localStringBuilder.append("document.body.appendChild(newscript);");
        paramWebView = localStringBuilder.toString();
        this.mX5WebView.evaluateJavascript(paramWebView, null);
      }
      setNetWorkLoadPolicyToDefault(paramString);
      if (this.mCompatibleOnPageStartedOrFinishedMethod) {
        try
        {
          paramWebView = this.mX5WebViewClient.getClass().getDeclaredMethod("onPageFinished", new Class[] { IX5WebViewBase.class, Integer.TYPE, Integer.TYPE, String.class });
          paramWebView.setAccessible(true);
          paramWebView.invoke(this.mX5WebViewClient, new Object[] { this.mX5WebView, Integer.valueOf(0), Integer.valueOf(0), paramString });
        }
        catch (Throwable paramWebView)
        {
          paramWebView.printStackTrace();
        }
      }
    }
    try
    {
      paramWebView = this.mX5WebViewClient.getClass().getDeclaredMethod("onPageFinished", new Class[] { IX5WebViewBase.class, String.class });
      paramWebView.setAccessible(true);
      paramWebView.invoke(this.mX5WebViewClient, new Object[] { this.mX5WebView, paramString });
    }
    catch (Throwable paramWebView)
    {
      for (;;) {}
    }
    this.mCompatibleOnPageStartedOrFinishedMethod = true;
    try
    {
      paramWebView = this.mX5WebViewClient.getClass().getDeclaredMethod("onPageFinished", new Class[] { IX5WebViewBase.class, Integer.TYPE, Integer.TYPE, String.class });
      paramWebView.setAccessible(true);
      paramWebView.invoke(this.mX5WebViewClient, new Object[] { this.mX5WebView, Integer.valueOf(0), Integer.valueOf(0), paramString });
    }
    catch (Throwable paramWebView)
    {
      for (;;) {}
    }
    SmttServiceProxy.getInstance().onPageLoadFinished(paramString);
    AdInfoManager.getInstance().syncAdInfoFromRamToFlash();
    this.mX5WebView.notifyPageFinished(paramString);
    PersistentSessionManager.getInstance().syncSessionInfoFromRamToFlash();
    if (SmttServiceProxy.getInstance().isLongPressMenuExplorerEnabled(this.mX5WebView.getContext(), this.mX5WebView)) {
      this.mX5WebView.getWebViewProvider().getExtension().requestExplorerStatistics();
    }
    CookieManager.getInstance().flush();
    this.domainListTestEnv = SmttServiceProxy.getInstance().isLastWupReqFromTestEvn("domainList");
    this.preferenceTestEnv = SmttServiceProxy.getInstance().isLastWupReqFromTestEvn("preference");
    if (((this.domainListTestEnv) || (this.preferenceTestEnv)) && (this.mX5WebView.getParent() != null) && (this.onPageFinishCount == 0)) {
      ((ViewGroup)this.mX5WebView.getParent()).addView(new View(this.mX5WebView.getContext())
      {
        public void draw(Canvas paramAnonymousCanvas)
        {
          super.draw(paramAnonymousCanvas);
          paramAnonymousCanvas.save();
          Paint localPaint = new Paint();
          localPaint.setColor(2147418112);
          localPaint.setTextSize(35.0F);
          localPaint.setAntiAlias(true);
          if (TencentWebViewClient.this.domainListTestEnv) {
            paramAnonymousCanvas.drawText("WUPDomian in test", 10.0F, 150.0F, localPaint);
          }
          if (TencentWebViewClient.this.preferenceTestEnv) {
            paramAnonymousCanvas.drawText("WUPKey in test", 10.0F, 200.0F, localPaint);
          }
          paramAnonymousCanvas.restore();
        }
      });
    }
    this.onPageFinishCount += 1;
    if (e.a().n()) {
      AdInfoManager.getInstance().initSmartAdFilterFiles();
    }
    if ((!e.a().n()) && (skipgodCmd))
    {
      skipgodCmd = false;
      a.a().c(SmttServiceProxy.getInstance().getLiveLogGodCmd());
      if (SmttServiceProxy.getInstance().canClearDNSCacheGodCmd())
      {
        AwNetworkUtils.clearDNSCache();
        return;
        super.onPageFinished(paramWebView, paramString);
      }
    }
  }
  
  public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null) {
      if (this.mCompatibleOnPageStartedOrFinishedMethod) {
        try
        {
          paramWebView = localIX5WebViewClient.getClass().getDeclaredMethod("onPageStarted", new Class[] { IX5WebViewBase.class, Integer.TYPE, Integer.TYPE, String.class, Bitmap.class });
          paramWebView.setAccessible(true);
          paramWebView.invoke(this.mX5WebViewClient, new Object[] { this.mX5WebView, Integer.valueOf(0), Integer.valueOf(0), paramString, this.mX5WebView.getRealWebView().getFavicon() });
        }
        catch (Throwable paramWebView)
        {
          paramWebView.printStackTrace();
        }
      }
    }
    try
    {
      paramWebView = localIX5WebViewClient.getClass().getDeclaredMethod("onPageStarted", new Class[] { IX5WebViewBase.class, String.class, Bitmap.class });
      paramWebView.setAccessible(true);
      paramWebView.invoke(this.mX5WebViewClient, new Object[] { this.mX5WebView, paramString, this.mX5WebView.getRealWebView().getFavicon() });
    }
    catch (Throwable paramWebView)
    {
      try
      {
        paramWebView = this.mX5WebView.getClass().getDeclaredMethod("onPageStarted", new Class[] { IX5WebViewBase.class, Integer.TYPE, Integer.TYPE, String.class, Bitmap.class });
        paramWebView.setAccessible(true);
        paramWebView.invoke(this.mX5WebView, new Object[] { this.mX5WebView, Integer.valueOf(0), Integer.valueOf(0), paramString, this.mX5WebView.getRealWebView().getFavicon() });
        this.mX5WebView.clearExplorerInfo();
        this.mX5WebView.notifyPageStarted(paramString);
        BrowserExecutorSupplier.postForDbTasks(new BrowserExecutorSupplier.BackgroundRunable()
        {
          public void doRun()
          {
            SmttServiceProxy.getInstance().setVPNConnected(TencentWebViewClient.isVpnUsed());
          }
        });
        break label352;
        super.onPageStarted(paramWebView, paramString, paramBitmap);
        if (!e.a().n()) {
          break label368;
        }
        h.a().a(paramString);
        return;
        paramWebView = paramWebView;
      }
      catch (Throwable paramWebView)
      {
        for (;;) {}
      }
    }
    this.mCompatibleOnPageStartedOrFinishedMethod = true;
  }
  
  public void onReceivedClientCertRequest(WebView paramWebView, com.tencent.tbs.core.webkit.ClientCertRequest paramClientCertRequest)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null)
    {
      TencentWebViewProxy localTencentWebViewProxy = this.mX5WebView;
      if (paramClientCertRequest == null) {
        paramWebView = null;
      } else {
        paramWebView = new ClientCertRequestImpl(paramClientCertRequest);
      }
      localIX5WebViewClient.onReceivedClientCertRequest(localTencentWebViewProxy, paramWebView);
      return;
    }
    super.onReceivedClientCertRequest(paramWebView, paramClientCertRequest);
  }
  
  public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null)
    {
      localIX5WebViewClient.onReceivedError(this.mX5WebView, paramInt, paramString1, paramString2);
      return;
    }
    super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
  }
  
  public void onReceivedError(WebView paramWebView, com.tencent.tbs.core.webkit.WebResourceRequest paramWebResourceRequest, com.tencent.tbs.core.webkit.WebResourceError paramWebResourceError)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null)
    {
      TencentWebViewProxy localTencentWebViewProxy = this.mX5WebView;
      Object localObject = null;
      if (paramWebResourceRequest == null) {
        paramWebView = null;
      } else {
        paramWebView = new WebResourceRequestImpl(paramWebResourceRequest);
      }
      if (paramWebResourceError == null) {
        paramWebResourceRequest = (com.tencent.tbs.core.webkit.WebResourceRequest)localObject;
      } else {
        paramWebResourceRequest = new WebResourceErrorImpl(paramWebResourceError);
      }
      localIX5WebViewClient.onReceivedError(localTencentWebViewProxy, paramWebView, paramWebResourceRequest);
      return;
    }
    super.onReceivedError(paramWebView, paramWebResourceRequest, paramWebResourceError);
  }
  
  public void onReceivedHttpAuthRequest(WebView paramWebView, com.tencent.tbs.core.webkit.HttpAuthHandler paramHttpAuthHandler, String paramString1, String paramString2)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null)
    {
      TencentWebViewProxy localTencentWebViewProxy = this.mX5WebView;
      if (paramHttpAuthHandler == null) {
        paramWebView = null;
      } else {
        paramWebView = new HttpAuthHandlerImpl(paramHttpAuthHandler);
      }
      localIX5WebViewClient.onReceivedHttpAuthRequest(localTencentWebViewProxy, paramWebView, paramString1, paramString2);
      return;
    }
    super.onReceivedHttpAuthRequest(paramWebView, paramHttpAuthHandler, paramString1, paramString2);
  }
  
  public void onReceivedHttpError(WebView paramWebView, com.tencent.tbs.core.webkit.WebResourceRequest paramWebResourceRequest, com.tencent.tbs.core.webkit.WebResourceResponse paramWebResourceResponse)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null)
    {
      TencentWebViewProxy localTencentWebViewProxy = this.mX5WebView;
      if (paramWebResourceRequest == null) {
        paramWebView = null;
      } else {
        paramWebView = new WebResourceRequestImpl(paramWebResourceRequest);
      }
      if (paramWebResourceResponse == null) {
        paramWebResourceRequest = null;
      } else {
        paramWebResourceRequest = new com.tencent.smtt.export.external.interfaces.WebResourceResponse(paramWebResourceResponse.getMimeType(), paramWebResourceResponse.getEncoding(), paramWebResourceResponse.getStatusCode(), paramWebResourceResponse.getReasonPhrase(), paramWebResourceResponse.getResponseHeaders(), paramWebResourceResponse.getData());
      }
      localIX5WebViewClient.onReceivedHttpError(localTencentWebViewProxy, paramWebView, paramWebResourceRequest);
      return;
    }
    super.onReceivedHttpError(paramWebView, paramWebResourceRequest, paramWebResourceResponse);
  }
  
  public void onReceivedLoginRequest(WebView paramWebView, String paramString1, String paramString2, String paramString3)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null)
    {
      localIX5WebViewClient.onReceivedLoginRequest(this.mX5WebView, paramString1, paramString2, paramString3);
      return;
    }
    super.onReceivedLoginRequest(paramWebView, paramString1, paramString2, paramString3);
  }
  
  public void onReceivedSslError(WebView paramWebView, com.tencent.tbs.core.webkit.SslErrorHandler paramSslErrorHandler, android.net.http.SslError paramSslError)
  {
    onReceivedSslError(paramWebView, paramSslErrorHandler, paramSslError, "", "", "", false, false);
  }
  
  public void onReceivedSslError(WebView paramWebView, final com.tencent.tbs.core.webkit.SslErrorHandler paramSslErrorHandler, final android.net.http.SslError paramSslError, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mX5WebViewClient != null)
    {
      paramWebView = new com.tencent.smtt.export.external.interfaces.SslErrorHandler()
      {
        public void cancel()
        {
          paramSslErrorHandler.cancel();
          try
          {
            Method localMethod = TencentWebViewClient.this.mX5WebView.getWebViewClientExtension().getClass().getDeclaredMethod("onReceivedSslErrorCancel", new Class[0]);
            localMethod.setAccessible(true);
            localMethod.invoke(TencentWebViewClient.this.mX5WebView.getWebViewClientExtension(), new Object[0]);
            return;
          }
          catch (Throwable localThrowable) {}
        }
        
        public void proceed()
        {
          paramSslErrorHandler.proceed();
        }
      };
      paramSslErrorHandler = new com.tencent.smtt.export.external.interfaces.SslError()
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
          if (Build.VERSION.SDK_INT >= 14) {
            return paramSslError.getUrl();
          }
          return "";
        }
        
        public boolean hasError(int paramAnonymousInt)
        {
          return paramSslError.hasError(paramAnonymousInt);
        }
      };
    }
    for (;;)
    {
      try
      {
        paramSslError = ReflectionUtils.getMemberClass(this.mX5WebViewClient, "class com.tencent.smtt.sdk.WebViewClient");
        Class localClass = Class.forName("com.tencent.smtt.sdk.WebView");
        if ((paramSslError == null) || (localClass == null)) {
          break label194;
        }
        if (ReflectionUtils.hasMethod(paramSslError, "onReceivedSslError", new Class[] { localClass, com.tencent.smtt.export.external.interfaces.SslErrorHandler.class, com.tencent.smtt.export.external.interfaces.SslError.class }))
        {
          this.mX5WebViewClient.onReceivedSslError(this.mX5WebView, paramWebView, paramSslErrorHandler);
          return;
        }
        if ((!shouldShowSllErrorDialog) || (!canShowAlertDialog(this.mX5WebView.getContext()))) {
          break label194;
        }
        paramSslError = this.mX5WebView.getContext();
        if (this.mX5WebView.getSettingsExtension().getDayOrNight()) {
          break label195;
        }
        bool = true;
        paramWebView = new com.tencent.smtt.webkit.ui.d(paramSslError, paramWebView, paramSslErrorHandler, paramString1, paramString2, paramString3, paramBoolean1, bool, paramBoolean2);
        paramWebView.a();
        paramWebView.show();
        return;
      }
      catch (Exception paramWebView)
      {
        paramWebView.printStackTrace();
        return;
      }
      super.onReceivedSslError(paramWebView, paramSslErrorHandler, paramSslError);
      label194:
      return;
      label195:
      boolean bool = false;
    }
  }
  
  public void onScaleChanged(WebView paramWebView, float paramFloat1, float paramFloat2)
  {
    if (this.mX5WebViewClient != null)
    {
      paramWebView = this.mX5WebView;
      if (paramWebView != null) {
        paramWebView.getViewManager().a();
      }
      paramWebView = this.mX5WebView;
      if ((paramWebView != null) && (paramWebView.getH5VideoProxy() != null)) {
        this.mX5WebView.getH5VideoProxy().a(paramFloat1, paramFloat2);
      }
      this.mX5WebViewClient.onScaleChanged(this.mX5WebView, paramFloat1, paramFloat2);
      return;
    }
    super.onScaleChanged(paramWebView, paramFloat1, paramFloat2);
  }
  
  @Deprecated
  public void onTooManyRedirects(WebView paramWebView, Message paramMessage1, Message paramMessage2)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null)
    {
      localIX5WebViewClient.onTooManyRedirects(this.mX5WebView, paramMessage1, paramMessage2);
      return;
    }
    super.onTooManyRedirects(paramWebView, paramMessage1, paramMessage2);
  }
  
  public void onUnhandledInputEvent(WebView paramWebView, InputEvent paramInputEvent)
  {
    super.onUnhandledInputEvent(paramWebView, paramInputEvent);
  }
  
  public void onUnhandledKeyEvent(WebView paramWebView, KeyEvent paramKeyEvent)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null)
    {
      localIX5WebViewClient.onUnhandledKeyEvent(this.mX5WebView, paramKeyEvent);
      return;
    }
    super.onUnhandledKeyEvent(paramWebView, paramKeyEvent);
  }
  
  public void restorePreNetWorkLoadPolicy()
  {
    TencentWebViewProxy localTencentWebViewProxy = this.mX5WebView;
    if (localTencentWebViewProxy == null) {
      return;
    }
    if (65535 != this.mAbnormalRecoverPreState)
    {
      localTencentWebViewProxy.getSettings().setCacheMode(this.mAbnormalRecoverPreState);
      this.mAbnormalRecoverPreState = 65535;
    }
  }
  
  public void setNetWorkLoadPolicy(int paramInt)
  {
    TencentWebViewProxy localTencentWebViewProxy = this.mX5WebView;
    if (localTencentWebViewProxy == null) {
      return;
    }
    if (65535 == this.mAbnormalRecoverPreState) {
      this.mAbnormalRecoverPreState = localTencentWebViewProxy.getSettings().getCacheMode();
    }
    this.mX5WebView.getSettings().setCacheMode(paramInt);
  }
  
  public void setNetWorkLoadPolicyToCacheFirst(String paramString)
  {
    if (!this.mRecoverUrls.contains(paramString)) {
      this.mRecoverUrls.add(paramString);
    }
    setNetWorkLoadPolicy(1);
  }
  
  public void setNetWorkLoadPolicyToDefault(String paramString)
  {
    if (this.mRecoverUrls.contains(paramString))
    {
      this.mRecoverUrls.remove(paramString);
      if (this.mRecoverUrls.isEmpty()) {
        restorePreNetWorkLoadPolicy();
      }
    }
  }
  
  public com.tencent.tbs.core.webkit.WebResourceResponse shouldInterceptRequest(WebView paramWebView, com.tencent.tbs.core.webkit.WebResourceRequest paramWebResourceRequest)
  {
    if (this.mX5WebViewClient != null) {
      if (this.field_AwWebResourceRequest == null)
      {
        paramWebView = paramWebResourceRequest.getClass().getDeclaredFields();
        int j = paramWebView.length;
        i = 0;
        while (i < j)
        {
          localObject = paramWebView[i];
          if (((Field)localObject).getType() == AwContentsClient.AwWebResourceRequest.class)
          {
            this.field_AwWebResourceRequest = ((Field)localObject);
            this.field_AwWebResourceRequest.setAccessible(true);
          }
          i += 1;
        }
        if (this.field_AwWebResourceRequest == null) {
          return null;
        }
      }
    }
    for (;;)
    {
      try
      {
        paramWebView = (AwContentsClient.AwWebResourceRequest)this.field_AwWebResourceRequest.get(paramWebResourceRequest);
        if (paramWebView == null) {
          return null;
        }
        if (mNewShouldInterceptRequestVersion) {
          if (paramWebResourceRequest == null) {
            localWebResourceRequestImpl = null;
          }
        }
      }
      catch (Throwable paramWebView)
      {
        WebResourceRequestImpl localWebResourceRequestImpl;
        boolean bool;
        return null;
      }
      try
      {
        localWebResourceRequestImpl = new WebResourceRequestImpl(paramWebResourceRequest);
        localObject = new Bundle();
        ((Bundle)localObject).putInt("resourceType", paramWebView.resourceType);
        ((Bundle)localObject).putBoolean("isRedirect", paramWebView.isRedirect);
        bool = mNewShouldInterceptRequestVersion_V2;
        if (!bool) {}
      }
      catch (Throwable paramWebView)
      {
        continue;
      }
      try
      {
        localObject = this.mX5WebViewClient.shouldInterceptRequest(this.mX5WebView, localWebResourceRequestImpl, (Bundle)localObject);
      }
      catch (Throwable paramWebView) {}
    }
    mNewShouldInterceptRequestVersion_V2 = false;
    localObject = null;
    paramWebView = (WebView)localObject;
    try
    {
      if (mNewShouldInterceptRequestVersion_V2) {
        break label249;
      }
      paramWebView = this.mX5WebViewClient.shouldInterceptRequest(this.mX5WebView, localWebResourceRequestImpl);
    }
    catch (Throwable paramWebView)
    {
      for (;;)
      {
        paramWebView = (WebView)localObject;
      }
    }
    paramWebView = this.mX5WebViewClient.shouldInterceptRequest(this.mX5WebView, localWebResourceRequestImpl);
    break label249;
    paramWebView = null;
    mNewShouldInterceptRequestVersion = false;
    label249:
    if (!mNewShouldInterceptRequestVersion)
    {
      paramWebView = this.mX5WebViewClient.shouldInterceptRequest(this.mX5WebView, paramWebResourceRequest.getUrl().toString());
      break label304;
      paramWebView = this.mX5WebViewClient.shouldInterceptRequest(this.mX5WebView, paramWebResourceRequest.getUrl().toString());
    }
    label304:
    if (paramWebView == null) {
      return null;
    }
    if (mShouldUseWebResourceResponseNewInterface) {}
    try
    {
      i = paramWebView.getStatusCode();
    }
    catch (Throwable paramWebResourceRequest)
    {
      for (;;) {}
    }
    mShouldUseWebResourceResponseNewInterface = false;
    int i = 200;
    if (mShouldUseWebResourceResponseNewInterface)
    {
      paramWebResourceRequest = paramWebView.getResponseHeaders();
      if (paramWebResourceRequest == null) {
        paramWebResourceRequest = new HashMap();
      }
      return new com.tencent.tbs.core.webkit.WebResourceResponse(false, paramWebView.getMimeType(), paramWebView.getEncoding(), i, paramWebView.getReasonPhrase(), paramWebResourceRequest, paramWebView.getData());
    }
    paramWebResourceRequest = new HashMap();
    return new com.tencent.tbs.core.webkit.WebResourceResponse(false, paramWebView.getMimeType(), paramWebView.getEncoding(), 200, "", paramWebResourceRequest, paramWebView.getData());
    return super.shouldInterceptRequest(paramWebView, paramWebResourceRequest);
  }
  
  @Deprecated
  public com.tencent.tbs.core.webkit.WebResourceResponse shouldInterceptRequest(WebView paramWebView, String paramString)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null)
    {
      paramString = localIX5WebViewClient.shouldInterceptRequest(this.mX5WebView, paramString);
      i = 200;
      if (!mShouldUseWebResourceResponseNewInterface) {}
    }
    try
    {
      int j = paramString.getStatusCode();
      i = j;
      paramWebView = paramString.getReasonPhrase();
      i = j;
    }
    catch (Throwable paramWebView)
    {
      for (;;) {}
    }
    mShouldUseWebResourceResponseNewInterface = false;
    paramWebView = "";
    break label71;
    paramWebView = "";
    int i = 200;
    label71:
    if (paramString == null) {
      return null;
    }
    return new com.tencent.tbs.core.webkit.WebResourceResponse(false, paramString.getMimeType(), paramString.getEncoding(), i, paramWebView, paramString.getResponseHeaders(), paramString.getData());
    return super.shouldInterceptRequest(paramWebView, paramString);
  }
  
  public boolean shouldOverrideKeyEvent(WebView paramWebView, KeyEvent paramKeyEvent)
  {
    IX5WebViewClient localIX5WebViewClient = this.mX5WebViewClient;
    if (localIX5WebViewClient != null) {
      return localIX5WebViewClient.shouldOverrideKeyEvent(this.mX5WebView, paramKeyEvent);
    }
    return super.shouldOverrideKeyEvent(paramWebView, paramKeyEvent);
  }
  
  public boolean shouldOverrideUrlLoading(WebView paramWebView, com.tencent.tbs.core.webkit.WebResourceRequest paramWebResourceRequest)
  {
    Object localObject2 = null;
    if ((paramWebResourceRequest != null) && (paramWebResourceRequest.getUrl() != null)) {
      localObject1 = paramWebResourceRequest.getUrl().toString();
    } else {
      localObject1 = null;
    }
    if (localObject1 != null)
    {
      TencentWebViewProxy localTencentWebViewProxy = this.mX5WebView;
      if ((localTencentWebViewProxy != null) && (localTencentWebViewProxy.shouldOverrideUrlLoadingForReadMode((String)localObject1))) {
        return true;
      }
    }
    Object localObject1 = this.mX5WebViewClient;
    if (localObject1 != null) {
      if (mNewshouldOverrideUrlLoadingVersion) {
        if (paramWebResourceRequest == null) {
          paramWebView = (WebView)localObject2;
        }
      }
    }
    try
    {
      paramWebView = new WebResourceRequestImpl(paramWebResourceRequest);
      boolean bool = this.mX5WebViewClient.shouldOverrideUrlLoading(this.mX5WebView, paramWebView);
      return bool;
    }
    catch (Throwable paramWebView)
    {
      for (;;) {}
    }
    mNewshouldOverrideUrlLoadingVersion = false;
    return this.mX5WebViewClient.shouldOverrideUrlLoading(this.mX5WebView, paramWebResourceRequest.getUrl().toString());
    return ((IX5WebViewClient)localObject1).shouldOverrideUrlLoading(this.mX5WebView, paramWebResourceRequest.getUrl().toString());
    return super.shouldOverrideUrlLoading(paramWebView, paramWebResourceRequest);
  }
  
  public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
  {
    if (paramString != null)
    {
      localObject = this.mX5WebView;
      if ((localObject != null) && (((TencentWebViewProxy)localObject).shouldOverrideUrlLoadingForReadMode(paramString))) {
        return true;
      }
    }
    Object localObject = this.mX5WebViewClient;
    if (localObject != null) {
      return ((IX5WebViewClient)localObject).shouldOverrideUrlLoading(this.mX5WebView, paramString);
    }
    return super.shouldOverrideUrlLoading(paramWebView, paramString);
  }
  
  private static class ClientCertRequestImpl
    extends com.tencent.smtt.export.external.interfaces.ClientCertRequest
  {
    private final String mHost;
    private final String[] mKeyTypes;
    private final int mPort;
    private final Principal[] mPrincipals;
    private final com.tencent.tbs.core.webkit.ClientCertRequest mRequest;
    
    public ClientCertRequestImpl(com.tencent.tbs.core.webkit.ClientCertRequest paramClientCertRequest)
    {
      this.mRequest = paramClientCertRequest;
      this.mKeyTypes = paramClientCertRequest.getKeyTypes();
      this.mPrincipals = paramClientCertRequest.getPrincipals();
      this.mHost = paramClientCertRequest.getHost();
      this.mPort = paramClientCertRequest.getPort();
    }
    
    public void cancel()
    {
      this.mRequest.cancel();
    }
    
    public String getHost()
    {
      return this.mHost;
    }
    
    public String[] getKeyTypes()
    {
      return this.mKeyTypes;
    }
    
    public int getPort()
    {
      return this.mPort;
    }
    
    public Principal[] getPrincipals()
    {
      return this.mPrincipals;
    }
    
    public void ignore()
    {
      this.mRequest.ignore();
    }
    
    public void proceed(PrivateKey paramPrivateKey, X509Certificate[] paramArrayOfX509Certificate)
    {
      this.mRequest.proceed(paramPrivateKey, paramArrayOfX509Certificate);
    }
  }
  
  private static class HttpAuthHandlerImpl
    implements com.tencent.smtt.export.external.interfaces.HttpAuthHandler
  {
    private com.tencent.tbs.core.webkit.HttpAuthHandler mHandler;
    
    public HttpAuthHandlerImpl(com.tencent.tbs.core.webkit.HttpAuthHandler paramHttpAuthHandler)
    {
      this.mHandler = paramHttpAuthHandler;
    }
    
    public void cancel()
    {
      this.mHandler.cancel();
    }
    
    public void proceed(String paramString1, String paramString2)
    {
      String str = paramString1;
      if (paramString1 == null) {
        str = "";
      }
      paramString1 = paramString2;
      if (paramString2 == null) {
        paramString1 = "";
      }
      this.mHandler.proceed(str, paramString1);
    }
    
    public boolean useHttpAuthUsernamePassword()
    {
      return this.mHandler.useHttpAuthUsernamePassword();
    }
  }
  
  private static class WebResourceErrorImpl
    extends com.tencent.smtt.export.external.interfaces.WebResourceError
  {
    private final com.tencent.tbs.core.webkit.WebResourceError mError;
    
    public WebResourceErrorImpl(com.tencent.tbs.core.webkit.WebResourceError paramWebResourceError)
    {
      this.mError = paramWebResourceError;
    }
    
    public CharSequence getDescription()
    {
      return this.mError.getDescription();
    }
    
    public int getErrorCode()
    {
      return this.mError.getErrorCode();
    }
  }
  
  private static class WebResourceRequestImpl
    implements com.tencent.smtt.export.external.interfaces.WebResourceRequest
  {
    private final com.tencent.tbs.core.webkit.WebResourceRequest mRequest;
    
    public WebResourceRequestImpl(com.tencent.tbs.core.webkit.WebResourceRequest paramWebResourceRequest)
    {
      this.mRequest = paramWebResourceRequest;
    }
    
    public String getMethod()
    {
      return this.mRequest.getMethod();
    }
    
    public Map<String, String> getRequestHeaders()
    {
      return this.mRequest.getRequestHeaders();
    }
    
    public Uri getUrl()
    {
      return this.mRequest.getUrl();
    }
    
    public boolean hasGesture()
    {
      return this.mRequest.hasGesture();
    }
    
    public boolean isForMainFrame()
    {
      return this.mRequest.isForMainFrame();
    }
    
    public boolean isRedirect()
    {
      return this.mRequest.isRedirect();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentWebViewClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */