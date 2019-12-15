package android.webview.chromium;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager.BadTokenException;
import com.tencent.tbs.core.webkit.ClientCertRequest;
import com.tencent.tbs.core.webkit.ConsoleMessage;
import com.tencent.tbs.core.webkit.ConsoleMessage.MessageLevel;
import com.tencent.tbs.core.webkit.DownloadListener;
import com.tencent.tbs.core.webkit.GeolocationPermissions.Callback;
import com.tencent.tbs.core.webkit.HttpAuthHandler;
import com.tencent.tbs.core.webkit.JsDialogHelper;
import com.tencent.tbs.core.webkit.JsPromptResult;
import com.tencent.tbs.core.webkit.JsResult;
import com.tencent.tbs.core.webkit.JsResult.ResultReceiver;
import com.tencent.tbs.core.webkit.PermissionRequest;
import com.tencent.tbs.core.webkit.SslErrorHandler;
import com.tencent.tbs.core.webkit.ValueCallback;
import com.tencent.tbs.core.webkit.WebBackForwardList;
import com.tencent.tbs.core.webkit.WebChromeClient;
import com.tencent.tbs.core.webkit.WebChromeClient.CustomViewCallback;
import com.tencent.tbs.core.webkit.WebChromeClient.FileChooserParams;
import com.tencent.tbs.core.webkit.WebResourceError;
import com.tencent.tbs.core.webkit.WebResourceResponse;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.WebView.FindListener;
import com.tencent.tbs.core.webkit.WebView.PictureListener;
import com.tencent.tbs.core.webkit.WebView.WebViewTransport;
import com.tencent.tbs.core.webkit.WebViewClient;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.WeakHashMap;
import org.chromium.android_webview.AwConsoleMessage;
import org.chromium.android_webview.AwContents;
import org.chromium.android_webview.AwContentsClient;
import org.chromium.android_webview.AwContentsClient.AwWebResourceError;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwContentsClient.CustomViewCallback;
import org.chromium.android_webview.AwContentsClient.FileChooserParamsImpl;
import org.chromium.android_webview.AwContentsClientBridge.ClientCertificateRequestCallback;
import org.chromium.android_webview.AwGeolocationPermissions.Callback;
import org.chromium.android_webview.AwHttpAuthHandler;
import org.chromium.android_webview.AwRenderProcessGoneDetail;
import org.chromium.android_webview.AwSafeBrowsingResponse;
import org.chromium.android_webview.AwWebResourceResponse;
import org.chromium.android_webview.JsPromptResultReceiver;
import org.chromium.android_webview.JsResultReceiver;
import org.chromium.android_webview.permission.AwPermissionRequest;
import org.chromium.base.Callback;
import org.chromium.base.ThreadUtils;
import org.chromium.base.TraceEvent;

public class WebViewContentsClientAdapter
  extends AwContentsClient
{
  protected static final int NEW_WEBVIEW_CREATED = 100;
  protected static final String TAG = "WebViewCallback";
  protected static final boolean TRACE = false;
  protected static WebViewClient sNullWebViewClient = new WebViewClient();
  protected final Context mContext;
  protected DownloadListener mDownloadListener;
  private WebView.FindListener mFindListener;
  private WeakHashMap<AwPermissionRequest, WeakReference<PermissionRequestAdapter>> mOngoingPermissionRequests;
  private WebView.PictureListener mPictureListener;
  private boolean mPictureListenerInvalidateOnly;
  protected Handler mUiThreadHandler;
  protected WebChromeClient mWebChromeClient;
  protected final WebView mWebView;
  protected WebViewClient mWebViewClient = sNullWebViewClient;
  protected WebViewDelegateFactory.WebViewDelegate mWebViewDelegate;
  
  @SuppressLint({"HandlerLeak"})
  public WebViewContentsClientAdapter(WebView paramWebView, Context paramContext, WebViewDelegateFactory.WebViewDelegate paramWebViewDelegate)
  {
    if ((paramWebView != null) && (paramWebViewDelegate != null))
    {
      if (paramContext != null)
      {
        this.mContext = paramContext;
        this.mWebView = paramWebView;
        this.mWebViewDelegate = paramWebViewDelegate;
        setWebViewClient(null);
        this.mUiThreadHandler = new Handler()
        {
          public void handleMessage(Message paramAnonymousMessage)
          {
            if (paramAnonymousMessage.what == 100)
            {
              paramAnonymousMessage = ((WebView.WebViewTransport)paramAnonymousMessage.obj).getWebView();
              if (paramAnonymousMessage != WebViewContentsClientAdapter.this.mWebView)
              {
                if ((paramAnonymousMessage != null) && (paramAnonymousMessage.copyBackForwardList().getSize() != 0)) {
                  throw new IllegalArgumentException("New WebView for popup window must not have been previously navigated.");
                }
                WebViewChromium.completeWindowCreation(WebViewContentsClientAdapter.this.mWebView, paramAnonymousMessage);
                return;
              }
              throw new IllegalArgumentException("Parent WebView cannot host it's own popup window. Please use WebSettings.setSupportMultipleWindows(false)");
            }
            throw new IllegalStateException();
          }
        };
        return;
      }
      throw new IllegalArgumentException("context can't be null.");
    }
    throw new IllegalArgumentException("webView or delegate can't be null.");
  }
  
  private static ConsoleMessage fromAwConsoleMessage(AwConsoleMessage paramAwConsoleMessage)
  {
    if (paramAwConsoleMessage == null) {
      return null;
    }
    return new ConsoleMessage(paramAwConsoleMessage.message(), paramAwConsoleMessage.sourceId(), paramAwConsoleMessage.lineNumber(), fromAwMessageLevel(paramAwConsoleMessage.messageLevel()));
  }
  
  public static WebChromeClient.FileChooserParams fromAwFileChooserParams(AwContentsClient.FileChooserParamsImpl paramFileChooserParamsImpl)
  {
    if (paramFileChooserParamsImpl == null) {
      return null;
    }
    new WebChromeClient.FileChooserParams()
    {
      public Intent createIntent()
      {
        return this.val$value.createIntent();
      }
      
      public String[] getAcceptTypes()
      {
        return this.val$value.getAcceptTypes();
      }
      
      public String getFilenameHint()
      {
        return this.val$value.getFilenameHint();
      }
      
      public int getMode()
      {
        return this.val$value.getMode();
      }
      
      public CharSequence getTitle()
      {
        return this.val$value.getTitle();
      }
      
      public boolean isCaptureEnabled()
      {
        return this.val$value.isCaptureEnabled();
      }
    };
  }
  
  private static ConsoleMessage.MessageLevel fromAwMessageLevel(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unsupported value: ");
      localStringBuilder.append(paramInt);
      throw new IllegalArgumentException(localStringBuilder.toString());
    case 4: 
      return ConsoleMessage.MessageLevel.DEBUG;
    case 3: 
      return ConsoleMessage.MessageLevel.ERROR;
    case 2: 
      return ConsoleMessage.MessageLevel.WARNING;
    case 1: 
      return ConsoleMessage.MessageLevel.LOG;
    }
    return ConsoleMessage.MessageLevel.TIP;
  }
  
  private static <T> boolean isMethodDeclaredInSubClass(Class<T> paramClass, Class<? extends T> paramClass1, String paramString, Class<?>... paramVarArgs)
  {
    try
    {
      boolean bool = paramClass1.getMethod(paramString, paramVarArgs).getDeclaringClass().equals(paramClass);
      return bool ^ true;
    }
    catch (SecurityException paramClass)
    {
      return false;
    }
    catch (NoSuchMethodException paramClass) {}
    return false;
  }
  
  public void doUpdateVisitedHistory(String paramString, boolean paramBoolean)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.doUpdateVisitedHistory");
      this.mWebViewClient.doUpdateVisitedHistory(this.mWebView, paramString, paramBoolean);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.doUpdateVisitedHistory");
    }
  }
  
  public Bitmap getDefaultVideoPoster()
  {
    for (;;)
    {
      try
      {
        TraceEvent.begin("WebViewContentsClientAdapter.getDefaultVideoPoster");
        if (this.mWebChromeClient != null)
        {
          Bitmap localBitmap1 = this.mWebChromeClient.getDefaultVideoPoster();
          Bitmap localBitmap2 = localBitmap1;
          if (localBitmap1 == null)
          {
            localBitmap1 = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            localBitmap2 = Bitmap.createBitmap(localBitmap1.getWidth(), localBitmap1.getHeight(), localBitmap1.getConfig());
            localBitmap2.eraseColor(-7829368);
            new Canvas(localBitmap2).drawBitmap(localBitmap1, 0.0F, 0.0F, null);
          }
          return localBitmap2;
        }
      }
      finally
      {
        TraceEvent.end("WebViewContentsClientAdapter.getDefaultVideoPoster");
      }
      Object localObject2 = null;
    }
  }
  
  protected View getVideoLoadingProgressView()
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.getVideoLoadingProgressView");
      View localView;
      if (this.mWebChromeClient != null) {
        localView = this.mWebChromeClient.getVideoLoadingProgressView();
      } else {
        localView = null;
      }
      return localView;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.getVideoLoadingProgressView");
    }
  }
  
  public void getVisitedHistory(Callback<String[]> paramCallback)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.getVisitedHistory");
      paramCallback = this.mWebChromeClient;
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.getVisitedHistory");
    }
  }
  
  WebChromeClient getWebChromeClient()
  {
    return this.mWebChromeClient;
  }
  
  WebViewClient getWebViewClient()
  {
    return this.mWebViewClient;
  }
  
  public void handleJsAlert(String paramString1, String paramString2, JsResultReceiver paramJsResultReceiver)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.handleJsAlert");
      if (this.mWebChromeClient != null)
      {
        JsPromptResult localJsPromptResult = new JsPromptResultReceiverAdapter(paramJsResultReceiver).getPromptResult();
        if ((!this.mWebChromeClient.onJsAlert(this.mWebView, paramString1, paramString2, localJsPromptResult)) && (!showDefaultJsDialog(localJsPromptResult, 1, null, paramString2, paramString1))) {
          paramJsResultReceiver.cancel();
        }
      }
      else
      {
        paramJsResultReceiver.cancel();
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.handleJsAlert");
    }
  }
  
  public void handleJsBeforeUnload(String paramString1, String paramString2, JsResultReceiver paramJsResultReceiver)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.handleJsBeforeUnload");
      if (this.mWebChromeClient != null)
      {
        JsPromptResult localJsPromptResult = new JsPromptResultReceiverAdapter(paramJsResultReceiver).getPromptResult();
        if ((!this.mWebChromeClient.onJsBeforeUnload(this.mWebView, paramString1, paramString2, localJsPromptResult)) && (!showDefaultJsDialog(localJsPromptResult, 4, null, paramString2, paramString1))) {
          paramJsResultReceiver.cancel();
        }
      }
      else
      {
        paramJsResultReceiver.cancel();
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.handleJsBeforeUnload");
    }
  }
  
  public void handleJsConfirm(String paramString1, String paramString2, JsResultReceiver paramJsResultReceiver)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.handleJsConfirm");
      if (this.mWebChromeClient != null)
      {
        JsPromptResult localJsPromptResult = new JsPromptResultReceiverAdapter(paramJsResultReceiver).getPromptResult();
        if ((!this.mWebChromeClient.onJsConfirm(this.mWebView, paramString1, paramString2, localJsPromptResult)) && (!showDefaultJsDialog(localJsPromptResult, 2, null, paramString2, paramString1))) {
          paramJsResultReceiver.cancel();
        }
      }
      else
      {
        paramJsResultReceiver.cancel();
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.handleJsConfirm");
    }
  }
  
  public void handleJsPrompt(String paramString1, String paramString2, String paramString3, JsPromptResultReceiver paramJsPromptResultReceiver)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.handleJsPrompt");
      if (this.mWebChromeClient != null)
      {
        JsPromptResult localJsPromptResult = new JsPromptResultReceiverAdapter(paramJsPromptResultReceiver).getPromptResult();
        if ((!this.mWebChromeClient.onJsPrompt(this.mWebView, paramString1, paramString2, paramString3, localJsPromptResult)) && (!showDefaultJsDialog(localJsPromptResult, 3, paramString3, paramString2, paramString1))) {
          paramJsPromptResultReceiver.cancel();
        }
      }
      else
      {
        paramJsPromptResultReceiver.cancel();
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.handleJsPrompt");
    }
  }
  
  public boolean hasWebViewClient()
  {
    return this.mWebViewClient != sNullWebViewClient;
  }
  
  public void onCloseWindow()
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onCloseWindow");
      if (this.mWebChromeClient != null) {
        this.mWebChromeClient.onCloseWindow(this.mWebView);
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onCloseWindow");
    }
  }
  
  public boolean onConsoleMessage(AwConsoleMessage paramAwConsoleMessage)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onConsoleMessage");
      boolean bool;
      if (this.mWebChromeClient != null) {
        bool = this.mWebChromeClient.onConsoleMessage(fromAwConsoleMessage(paramAwConsoleMessage));
      } else {
        bool = false;
      }
      return bool;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onConsoleMessage");
    }
  }
  
  public boolean onCreateWindow(boolean paramBoolean1, boolean paramBoolean2)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onCreateWindow");
      Object localObject1 = this.mUiThreadHandler;
      WebView localWebView = this.mWebView;
      localWebView.getClass();
      localObject1 = ((Handler)localObject1).obtainMessage(100, new WebView.WebViewTransport(localWebView));
      if (this.mWebChromeClient != null) {
        paramBoolean1 = this.mWebChromeClient.onCreateWindow(this.mWebView, paramBoolean1, paramBoolean2, (Message)localObject1);
      } else {
        paramBoolean1 = false;
      }
      return paramBoolean1;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onCreateWindow");
    }
  }
  
  public void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onDownloadStart");
      if (this.mDownloadListener != null) {
        this.mDownloadListener.onDownloadStart(paramString1, paramString2, paramString3, paramString4, paramLong);
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onDownloadStart");
    }
  }
  
  public void onFindResultReceived(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onFindResultReceived");
      WebView.FindListener localFindListener = this.mFindListener;
      if (localFindListener == null) {
        return;
      }
      this.mFindListener.onFindResultReceived(paramInt1, paramInt2, paramBoolean);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onFindResultReceived");
    }
  }
  
  public void onFormResubmission(Message paramMessage1, Message paramMessage2)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onFormResubmission");
      this.mWebViewClient.onFormResubmission(this.mWebView, paramMessage1, paramMessage2);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onFormResubmission");
    }
  }
  
  public void onGeolocationPermissionsHidePrompt()
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onGeolocationPermissionsHidePrompt");
      if (this.mWebChromeClient != null) {
        this.mWebChromeClient.onGeolocationPermissionsHidePrompt();
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onGeolocationPermissionsHidePrompt");
    }
  }
  
  public void onGeolocationPermissionsShowPrompt(String paramString, AwGeolocationPermissions.Callback paramCallback)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onGeolocationPermissionsShowPrompt");
      if (this.mWebChromeClient == null)
      {
        paramCallback.invoke(paramString, false, false);
        return;
      }
      if (!isMethodDeclaredInSubClass(WebChromeClient.class, this.mWebChromeClient.getClass(), "onGeolocationPermissionsShowPrompt", new Class[] { String.class, GeolocationPermissions.Callback.class }))
      {
        paramCallback.invoke(paramString, false, false);
        return;
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onGeolocationPermissionsShowPrompt");
    }
  }
  
  public void onHideCustomView()
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onHideCustomView");
      if (this.mWebChromeClient != null) {
        this.mWebChromeClient.onHideCustomView();
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onHideCustomView");
    }
  }
  
  public void onLoadResource(String paramString)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onLoadResource");
      this.mWebViewClient.onLoadResource(this.mWebView, paramString);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onLoadResource");
    }
  }
  
  public void onNewPicture(Picture paramPicture)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onNewPicture");
      WebView.PictureListener localPictureListener = this.mPictureListener;
      if (localPictureListener == null) {
        return;
      }
      this.mPictureListener.onNewPicture(this.mWebView, paramPicture);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onNewPicture");
    }
  }
  
  public void onPageCommitVisible(String paramString)
  {
    if (Build.VERSION.SDK_INT < 23) {
      return;
    }
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onPageCommitVisible");
      this.mWebViewClient.onPageCommitVisible(this.mWebView, paramString);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onPageCommitVisible");
    }
  }
  
  public void onPageFinished(String paramString)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onPageFinished");
      this.mWebViewClient.onPageFinished(this.mWebView, paramString);
      if (this.mPictureListener != null) {
        ThreadUtils.postOnUiThreadDelayed(new Runnable()
        {
          public void run()
          {
            if (WebViewContentsClientAdapter.this.mPictureListener != null)
            {
              WebView.PictureListener localPictureListener = WebViewContentsClientAdapter.this.mPictureListener;
              WebView localWebView = WebViewContentsClientAdapter.this.mWebView;
              Picture localPicture;
              if (WebViewContentsClientAdapter.this.mPictureListenerInvalidateOnly) {
                localPicture = null;
              } else {
                localPicture = new Picture();
              }
              localPictureListener.onNewPicture(localWebView, localPicture);
            }
          }
        }, 100L);
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onPageFinished");
    }
  }
  
  public void onPageStarted(String paramString)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onPageStarted");
      this.mWebViewClient.onPageStarted(this.mWebView, paramString, this.mWebView.getFavicon());
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onPageStarted");
    }
  }
  
  public void onPermissionRequest(AwPermissionRequest paramAwPermissionRequest)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onPermissionRequest");
      if (this.mWebChromeClient != null)
      {
        if (this.mOngoingPermissionRequests == null) {
          this.mOngoingPermissionRequests = new WeakHashMap();
        }
        PermissionRequestAdapter localPermissionRequestAdapter = new PermissionRequestAdapter(paramAwPermissionRequest);
        this.mOngoingPermissionRequests.put(paramAwPermissionRequest, new WeakReference(localPermissionRequestAdapter));
        this.mWebChromeClient.onPermissionRequest(localPermissionRequestAdapter);
      }
      else
      {
        paramAwPermissionRequest.deny();
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onPermissionRequest");
    }
  }
  
  public void onPermissionRequestCanceled(AwPermissionRequest paramAwPermissionRequest)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onPermissionRequestCanceled");
      if ((this.mWebChromeClient != null) && (this.mOngoingPermissionRequests != null))
      {
        paramAwPermissionRequest = (WeakReference)this.mOngoingPermissionRequests.get(paramAwPermissionRequest);
        if (paramAwPermissionRequest != null)
        {
          paramAwPermissionRequest = (PermissionRequestAdapter)paramAwPermissionRequest.get();
          if (paramAwPermissionRequest != null) {
            this.mWebChromeClient.onPermissionRequestCanceled(paramAwPermissionRequest);
          }
        }
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onPermissionRequestCanceled");
    }
  }
  
  public void onProgressChanged(int paramInt)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onProgressChanged");
      if (this.mWebChromeClient != null) {
        this.mWebChromeClient.onProgressChanged(this.mWebView, paramInt);
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onProgressChanged");
    }
  }
  
  public void onReceivedClientCertRequest(AwContentsClientBridge.ClientCertificateRequestCallback paramClientCertificateRequestCallback, String[] paramArrayOfString, Principal[] paramArrayOfPrincipal, String paramString, int paramInt)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onReceivedClientCertRequest");
      paramClientCertificateRequestCallback = new ClientCertRequestImpl(paramClientCertificateRequestCallback, paramArrayOfString, paramArrayOfPrincipal, paramString, paramInt);
      this.mWebViewClient.onReceivedClientCertRequest(this.mWebView, paramClientCertificateRequestCallback);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onReceivedClientCertRequest");
    }
  }
  
  public void onReceivedDidFailLoad(String paramString, int paramInt) {}
  
  public void onReceivedError(int paramInt, String paramString1, String paramString2)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return;
    }
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onReceivedError");
      String str;
      if (paramString1 != null)
      {
        str = paramString1;
        if (!paramString1.isEmpty()) {}
      }
      else
      {
        str = this.mWebViewDelegate.getErrorString(this.mContext, paramInt);
      }
      this.mWebViewClient.onReceivedError(this.mWebView, paramInt, str, paramString2);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onReceivedError");
    }
  }
  
  public void onReceivedError2(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwContentsClient.AwWebResourceError paramAwWebResourceError)
  {
    if (Build.VERSION.SDK_INT < 23) {
      return;
    }
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onReceivedError");
      if ((paramAwWebResourceError.description == null) || (paramAwWebResourceError.description.isEmpty())) {
        paramAwWebResourceError.description = this.mWebViewDelegate.getErrorString(this.mContext, paramAwWebResourceError.errorCode);
      }
      this.mWebViewClient.onReceivedError(this.mWebView, new WebResourceRequestAdapter(paramAwWebResourceRequest), new WebResourceErrorImpl(paramAwWebResourceError));
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onReceivedError");
    }
  }
  
  public void onReceivedHttpAuthRequest(AwHttpAuthHandler paramAwHttpAuthHandler, String paramString1, String paramString2)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onReceivedHttpAuthRequest");
      this.mWebViewClient.onReceivedHttpAuthRequest(this.mWebView, new AwHttpAuthHandlerAdapter(paramAwHttpAuthHandler), paramString1, paramString2);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onReceivedHttpAuthRequest");
    }
  }
  
  public void onReceivedHttpError(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse)
  {
    if (Build.VERSION.SDK_INT < 23) {
      return;
    }
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onReceivedHttpError");
      this.mWebViewClient.onReceivedHttpError(this.mWebView, new WebResourceRequestAdapter(paramAwWebResourceRequest), new WebResourceResponse(true, paramAwWebResourceResponse.getMimeType(), paramAwWebResourceResponse.getCharset(), paramAwWebResourceResponse.getStatusCode(), paramAwWebResourceResponse.getReasonPhrase(), paramAwWebResourceResponse.getResponseHeaders(), paramAwWebResourceResponse.getData()));
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onReceivedHttpError");
    }
  }
  
  public void onReceivedIcon(Bitmap paramBitmap)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onReceivedIcon");
      if (this.mWebChromeClient != null) {
        this.mWebChromeClient.onReceivedIcon(this.mWebView, paramBitmap);
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onReceivedIcon");
    }
  }
  
  public void onReceivedLoginRequest(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onReceivedLoginRequest");
      this.mWebViewClient.onReceivedLoginRequest(this.mWebView, paramString1, paramString2, paramString3);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onReceivedLoginRequest");
    }
  }
  
  public void onReceivedSslError(final Callback<Boolean> paramCallback, SslError paramSslError)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onReceivedSslError");
      paramCallback = new SslErrorHandler()
      {
        public void cancel()
        {
          paramCallback.onResult(Boolean.valueOf(false));
        }
        
        public void proceed()
        {
          paramCallback.onResult(Boolean.valueOf(true));
        }
      };
      this.mWebViewClient.onReceivedSslError(this.mWebView, paramCallback, paramSslError);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onReceivedSslError");
    }
  }
  
  public void onReceivedTitle(String paramString)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onReceivedTitle");
      if ((this.mWebChromeClient != null) && (this.mWebView.getUrl() != null)) {
        this.mWebChromeClient.onReceivedTitle(this.mWebView, paramString);
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onReceivedTitle");
    }
  }
  
  public void onReceivedTouchIconUrl(String paramString, boolean paramBoolean)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onReceivedTouchIconUrl");
      if (this.mWebChromeClient != null) {
        this.mWebChromeClient.onReceivedTouchIconUrl(this.mWebView, paramString, paramBoolean);
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onReceivedTouchIconUrl");
    }
  }
  
  @TargetApi(26)
  public boolean onRenderProcessGone(AwRenderProcessGoneDetail paramAwRenderProcessGoneDetail)
  {
    if (Build.VERSION.SDK_INT < 26) {
      return false;
    }
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onRenderProcessGone");
      return false;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onRenderProcessGone");
    }
  }
  
  public void onRequestFocus()
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onRequestFocus");
      if (this.mWebChromeClient != null) {
        this.mWebChromeClient.onRequestFocus(this.mWebView);
      }
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onRequestFocus");
    }
  }
  
  @TargetApi(27)
  public void onSafeBrowsingHit(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, int paramInt, Callback<AwSafeBrowsingResponse> paramCallback)
  {
    if (Build.VERSION.SDK_INT < 27)
    {
      paramCallback.onResult(new AwSafeBrowsingResponse(0, true));
      return;
    }
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onSafeBrowsingHit");
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onRenderProcessGone");
    }
  }
  
  public void onScaleChangedScaled(float paramFloat1, float paramFloat2)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onScaleChangedScaled");
      this.mWebViewClient.onScaleChanged(this.mWebView, paramFloat1, paramFloat2);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onScaleChangedScaled");
    }
  }
  
  public void onShowCustomView(View paramView, final AwContentsClient.CustomViewCallback paramCustomViewCallback)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onShowCustomView");
      if (this.mWebChromeClient != null)
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
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onShowCustomView");
    }
  }
  
  public void onUnhandledKeyEvent(KeyEvent paramKeyEvent)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.onUnhandledKeyEvent");
      this.mWebViewClient.onUnhandledKeyEvent(this.mWebView, paramKeyEvent);
      return;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.onUnhandledKeyEvent");
    }
  }
  
  void setDownloadListener(DownloadListener paramDownloadListener)
  {
    this.mDownloadListener = paramDownloadListener;
  }
  
  void setFindListener(WebView.FindListener paramFindListener)
  {
    this.mFindListener = paramFindListener;
  }
  
  void setPictureListener(WebView.PictureListener paramPictureListener, boolean paramBoolean)
  {
    this.mPictureListener = paramPictureListener;
    this.mPictureListenerInvalidateOnly = paramBoolean;
  }
  
  void setWebChromeClient(WebChromeClient paramWebChromeClient)
  {
    this.mWebChromeClient = paramWebChromeClient;
  }
  
  void setWebViewClient(WebViewClient paramWebViewClient)
  {
    if (paramWebViewClient != null)
    {
      this.mWebViewClient = paramWebViewClient;
      return;
    }
    this.mWebViewClient = sNullWebViewClient;
  }
  
  /* Error */
  public AwWebResourceResponse shouldInterceptRequest(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest)
  {
    // Byte code:
    //   0: ldc_w 715
    //   3: invokestatic 216	org/chromium/base/TraceEvent:begin	(Ljava/lang/String;)V
    //   6: aload_0
    //   7: getfield 83	android/webview/chromium/WebViewContentsClientAdapter:mWebViewClient	Lcom/tencent/tbs/core/webkit/WebViewClient;
    //   10: aload_0
    //   11: getfield 87	android/webview/chromium/WebViewContentsClientAdapter:mWebView	Lcom/tencent/tbs/core/webkit/WebView;
    //   14: new 559	android/webview/chromium/WebResourceRequestAdapter
    //   17: dup
    //   18: aload_1
    //   19: invokespecial 562	android/webview/chromium/WebResourceRequestAdapter:<init>	(Lorg/chromium/android_webview/AwContentsClient$AwWebResourceRequest;)V
    //   22: invokevirtual 718	com/tencent/tbs/core/webkit/WebViewClient:shouldInterceptRequest	(Lcom/tencent/tbs/core/webkit/WebView;Lcom/tencent/tbs/core/webkit/WebResourceRequest;)Lcom/tencent/tbs/core/webkit/WebResourceResponse;
    //   25: astore_3
    //   26: aload_3
    //   27: ifnonnull +11 -> 38
    //   30: ldc_w 715
    //   33: invokestatic 222	org/chromium/base/TraceEvent:end	(Ljava/lang/String;)V
    //   36: aconst_null
    //   37: areturn
    //   38: aload_3
    //   39: invokevirtual 719	com/tencent/tbs/core/webkit/WebResourceResponse:getResponseHeaders	()Ljava/util/Map;
    //   42: astore_2
    //   43: aload_2
    //   44: astore_1
    //   45: aload_2
    //   46: ifnonnull +11 -> 57
    //   49: new 721	java/util/HashMap
    //   52: dup
    //   53: invokespecial 722	java/util/HashMap:<init>	()V
    //   56: astore_1
    //   57: new 586	org/chromium/android_webview/AwWebResourceResponse
    //   60: dup
    //   61: aload_3
    //   62: invokevirtual 723	com/tencent/tbs/core/webkit/WebResourceResponse:getMimeType	()Ljava/lang/String;
    //   65: aload_3
    //   66: invokevirtual 726	com/tencent/tbs/core/webkit/WebResourceResponse:getEncoding	()Ljava/lang/String;
    //   69: aload_3
    //   70: invokevirtual 727	com/tencent/tbs/core/webkit/WebResourceResponse:getData	()Ljava/io/InputStream;
    //   73: aload_3
    //   74: invokevirtual 728	com/tencent/tbs/core/webkit/WebResourceResponse:getStatusCode	()I
    //   77: aload_3
    //   78: invokevirtual 729	com/tencent/tbs/core/webkit/WebResourceResponse:getReasonPhrase	()Ljava/lang/String;
    //   81: aload_1
    //   82: invokespecial 732	org/chromium/android_webview/AwWebResourceResponse:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/io/InputStream;ILjava/lang/String;Ljava/util/Map;)V
    //   85: astore_1
    //   86: ldc_w 715
    //   89: invokestatic 222	org/chromium/base/TraceEvent:end	(Ljava/lang/String;)V
    //   92: aload_1
    //   93: areturn
    //   94: astore_1
    //   95: goto +16 -> 111
    //   98: astore_1
    //   99: aload_1
    //   100: invokevirtual 735	java/lang/Exception:printStackTrace	()V
    //   103: ldc_w 715
    //   106: invokestatic 222	org/chromium/base/TraceEvent:end	(Ljava/lang/String;)V
    //   109: aconst_null
    //   110: areturn
    //   111: ldc_w 715
    //   114: invokestatic 222	org/chromium/base/TraceEvent:end	(Ljava/lang/String;)V
    //   117: aload_1
    //   118: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	119	0	this	WebViewContentsClientAdapter
    //   0	119	1	paramAwWebResourceRequest	AwContentsClient.AwWebResourceRequest
    //   42	4	2	localMap	java.util.Map
    //   25	53	3	localWebResourceResponse	WebResourceResponse
    // Exception table:
    //   from	to	target	type
    //   0	26	94	finally
    //   38	43	94	finally
    //   49	57	94	finally
    //   57	86	94	finally
    //   99	103	94	finally
    //   0	26	98	java/lang/Exception
    //   38	43	98	java/lang/Exception
    //   49	57	98	java/lang/Exception
    //   57	86	98	java/lang/Exception
  }
  
  public boolean shouldOverrideKeyEvent(KeyEvent paramKeyEvent)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.shouldOverrideKeyEvent");
      boolean bool = this.mWebViewClient.shouldOverrideKeyEvent(this.mWebView, paramKeyEvent);
      return bool;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.shouldOverrideKeyEvent");
    }
  }
  
  @TargetApi(24)
  public boolean shouldOverrideUrlLoading(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest)
  {
    try
    {
      TraceEvent.begin("WebViewContentsClientAdapter.shouldOverrideUrlLoading");
      boolean bool;
      if (Build.VERSION.SDK_INT >= 24) {
        bool = this.mWebViewClient.shouldOverrideUrlLoading(this.mWebView, new WebResourceRequestAdapter(paramAwWebResourceRequest));
      } else {
        bool = this.mWebViewClient.shouldOverrideUrlLoading(this.mWebView, paramAwWebResourceRequest.url);
      }
      return bool;
    }
    finally
    {
      TraceEvent.end("WebViewContentsClientAdapter.shouldOverrideUrlLoading");
    }
  }
  
  protected boolean showDefaultJsDialog(JsPromptResult paramJsPromptResult, int paramInt, String paramString1, String paramString2, String paramString3)
  {
    Activity localActivity = AwContents.activityFromContext(this.mContext);
    if (localActivity == null) {
      return false;
    }
    try
    {
      new JsDialogHelper(paramJsPromptResult, paramInt, paramString1, paramString2, paramString3).showDialog(localActivity);
      return true;
    }
    catch (WindowManager.BadTokenException paramJsPromptResult) {}
    return false;
  }
  
  public void showFileChooser(final Callback<String[]> paramCallback, AwContentsClient.FileChooserParamsImpl paramFileChooserParamsImpl)
  {
    for (;;)
    {
      try
      {
        TraceEvent.begin("WebViewContentsClientAdapter.showFileChooser");
        if (this.mWebChromeClient == null)
        {
          paramCallback.onResult(null);
          return;
        }
        Object localObject = new ValueCallback()
        {
          private boolean mCompleted;
          
          public void onReceiveValue(Uri[] paramAnonymousArrayOfUri)
          {
            if (!this.mCompleted)
            {
              this.mCompleted = true;
              Object localObject = null;
              if (paramAnonymousArrayOfUri != null)
              {
                String[] arrayOfString = new String[paramAnonymousArrayOfUri.length];
                int i = 0;
                for (;;)
                {
                  localObject = arrayOfString;
                  if (i >= paramAnonymousArrayOfUri.length) {
                    break;
                  }
                  arrayOfString[i] = paramAnonymousArrayOfUri[i].toString();
                  i += 1;
                }
              }
              paramCallback.onResult(localObject);
              return;
            }
            throw new IllegalStateException("showFileChooser result was already called");
          }
        };
        boolean bool = this.mWebChromeClient.onShowFileChooser(this.mWebView, (ValueCallback)localObject, fromAwFileChooserParams(paramFileChooserParamsImpl));
        if (bool) {
          return;
        }
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 21)
        {
          paramCallback.onResult(null);
          return;
        }
        localObject = new ValueCallback()
        {
          private boolean mCompleted;
          
          public void onReceiveValue(Uri paramAnonymousUri)
          {
            if (!this.mCompleted)
            {
              this.mCompleted = true;
              Callback localCallback = paramCallback;
              if (paramAnonymousUri == null) {
                paramAnonymousUri = null;
              } else {
                paramAnonymousUri = new String[] { paramAnonymousUri.toString() };
              }
              localCallback.onResult(paramAnonymousUri);
              return;
            }
            throw new IllegalStateException("showFileChooser result was already called");
          }
        };
        WebChromeClient localWebChromeClient = this.mWebChromeClient;
        String str = paramFileChooserParamsImpl.getAcceptTypesString();
        if (paramFileChooserParamsImpl.isCaptureEnabled())
        {
          paramCallback = "*";
          localWebChromeClient.openFileChooser((ValueCallback)localObject, str, paramCallback);
          return;
        }
      }
      finally
      {
        TraceEvent.end("WebViewContentsClientAdapter.showFileChooser");
      }
      paramCallback = "";
    }
  }
  
  private static class AwHttpAuthHandlerAdapter
    extends HttpAuthHandler
  {
    private AwHttpAuthHandler mAwHandler;
    
    public AwHttpAuthHandlerAdapter(AwHttpAuthHandler paramAwHttpAuthHandler)
    {
      this.mAwHandler = paramAwHttpAuthHandler;
    }
    
    public void cancel()
    {
      this.mAwHandler.cancel();
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
      this.mAwHandler.proceed(str, paramString1);
    }
    
    public boolean useHttpAuthUsernamePassword()
    {
      return this.mAwHandler.isFirstAttempt();
    }
  }
  
  private static class ClientCertRequestImpl
    extends ClientCertRequest
  {
    private final AwContentsClientBridge.ClientCertificateRequestCallback mCallback;
    private final String mHost;
    private final String[] mKeyTypes;
    private final int mPort;
    private final Principal[] mPrincipals;
    
    public ClientCertRequestImpl(AwContentsClientBridge.ClientCertificateRequestCallback paramClientCertificateRequestCallback, String[] paramArrayOfString, Principal[] paramArrayOfPrincipal, String paramString, int paramInt)
    {
      this.mCallback = paramClientCertificateRequestCallback;
      this.mKeyTypes = paramArrayOfString;
      this.mPrincipals = paramArrayOfPrincipal;
      this.mHost = paramString;
      this.mPort = paramInt;
    }
    
    public void cancel()
    {
      this.mCallback.cancel();
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
      this.mCallback.ignore();
    }
    
    public void proceed(PrivateKey paramPrivateKey, X509Certificate[] paramArrayOfX509Certificate)
    {
      this.mCallback.proceed(paramPrivateKey, paramArrayOfX509Certificate);
    }
  }
  
  private static class JsPromptResultReceiverAdapter
    implements JsResult.ResultReceiver
  {
    private JsPromptResultReceiver mChromePromptResultReceiver;
    private JsResultReceiver mChromeResultReceiver;
    private final JsPromptResult mPromptResult = new JsPromptResult(this);
    
    public JsPromptResultReceiverAdapter(JsPromptResultReceiver paramJsPromptResultReceiver)
    {
      this.mChromePromptResultReceiver = paramJsPromptResultReceiver;
    }
    
    public JsPromptResultReceiverAdapter(JsResultReceiver paramJsResultReceiver)
    {
      this.mChromeResultReceiver = paramJsResultReceiver;
    }
    
    public JsPromptResult getPromptResult()
    {
      return this.mPromptResult;
    }
    
    public void onJsResultComplete(JsResult paramJsResult)
    {
      if (this.mChromePromptResultReceiver != null)
      {
        if (this.mPromptResult.getResult())
        {
          this.mChromePromptResultReceiver.confirm(this.mPromptResult.getStringResult());
          return;
        }
        this.mChromePromptResultReceiver.cancel();
        return;
      }
      if (this.mPromptResult.getResult())
      {
        this.mChromeResultReceiver.confirm();
        return;
      }
      this.mChromeResultReceiver.cancel();
    }
  }
  
  public static class PermissionRequestAdapter
    extends PermissionRequest
  {
    private AwPermissionRequest mAwPermissionRequest;
    private final String[] mResources;
    
    public PermissionRequestAdapter(AwPermissionRequest paramAwPermissionRequest)
    {
      this.mAwPermissionRequest = paramAwPermissionRequest;
      this.mResources = toPermissionResources(this.mAwPermissionRequest.getResources());
    }
    
    private static long toAwPermissionResources(String[] paramArrayOfString)
    {
      int j = paramArrayOfString.length;
      long l2 = 0L;
      int i = 0;
      while (i < j)
      {
        String str = paramArrayOfString[i];
        long l1;
        if (str.equals("android.webkit.resource.VIDEO_CAPTURE"))
        {
          l1 = l2 | 0x2;
        }
        else if (str.equals("android.webkit.resource.AUDIO_CAPTURE"))
        {
          l1 = l2 | 0x4;
        }
        else if (str.equals("android.webkit.resource.PROTECTED_MEDIA_ID"))
        {
          l1 = l2 | 0x8;
        }
        else
        {
          l1 = l2;
          if (str.equals("android.webkit.resource.MIDI_SYSEX")) {
            l1 = l2 | 0x10;
          }
        }
        i += 1;
        l2 = l1;
      }
      return l2;
    }
    
    private static String[] toPermissionResources(long paramLong)
    {
      ArrayList localArrayList = new ArrayList();
      if ((0x2 & paramLong) != 0L) {
        localArrayList.add("android.webkit.resource.VIDEO_CAPTURE");
      }
      if ((0x4 & paramLong) != 0L) {
        localArrayList.add("android.webkit.resource.AUDIO_CAPTURE");
      }
      if ((0x8 & paramLong) != 0L) {
        localArrayList.add("android.webkit.resource.PROTECTED_MEDIA_ID");
      }
      if ((paramLong & 0x10) != 0L) {
        localArrayList.add("android.webkit.resource.MIDI_SYSEX");
      }
      return (String[])localArrayList.toArray(new String[localArrayList.size()]);
    }
    
    public void deny()
    {
      this.mAwPermissionRequest.deny();
    }
    
    public Uri getOrigin()
    {
      return this.mAwPermissionRequest.getOrigin();
    }
    
    public String[] getResources()
    {
      return (String[])this.mResources.clone();
    }
    
    public void grant(String[] paramArrayOfString)
    {
      long l = this.mAwPermissionRequest.getResources();
      if ((toAwPermissionResources(paramArrayOfString) & l) == l)
      {
        this.mAwPermissionRequest.grant();
        return;
      }
      this.mAwPermissionRequest.deny();
    }
  }
  
  @TargetApi(23)
  private static class WebResourceErrorImpl
    extends WebResourceError
  {
    private final AwContentsClient.AwWebResourceError mError;
    
    public WebResourceErrorImpl(AwContentsClient.AwWebResourceError paramAwWebResourceError)
    {
      this.mError = paramAwWebResourceError;
    }
    
    public CharSequence getDescription()
    {
      return this.mError.description;
    }
    
    public int getErrorCode()
    {
      return this.mError.errorCode;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebViewContentsClientAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */