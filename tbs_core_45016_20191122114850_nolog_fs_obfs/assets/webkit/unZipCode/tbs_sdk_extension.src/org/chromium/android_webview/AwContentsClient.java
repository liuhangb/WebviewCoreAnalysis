package org.chromium.android_webview;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import java.security.Principal;
import java.util.HashMap;
import org.chromium.android_webview.permission.AwPermissionRequest;
import org.chromium.base.Callback;

public abstract class AwContentsClient
{
  private static final int INVALID_COLOR = 0;
  private static final String TAG = "AwContentsClient";
  private int mCachedRendererBackgroundColor = 0;
  private final AwContentsClientCallbackHelper mCallbackHelper = new AwContentsClientCallbackHelper(paramLooper, this);
  private String mTitle = "";
  
  public AwContentsClient()
  {
    this(Looper.myLooper());
  }
  
  public AwContentsClient(Looper paramLooper) {}
  
  public static Uri[] parseFileChooserResult(int paramInt, Intent paramIntent)
  {
    Uri[] arrayOfUri = null;
    if (paramInt == 0) {
      return null;
    }
    if ((paramIntent != null) && (paramInt == -1)) {
      paramIntent = paramIntent.getData();
    } else {
      paramIntent = null;
    }
    if (paramIntent != null)
    {
      arrayOfUri = new Uri[1];
      arrayOfUri[0] = paramIntent;
    }
    return arrayOfUri;
  }
  
  private static boolean sendBrowsingIntent(Context paramContext, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((!paramBoolean1) && (!paramBoolean2)) {
      return true;
    }
    if (paramString.startsWith("about:")) {
      return false;
    }
    try
    {
      paramString = Intent.parseUri(paramString, 1);
      paramString.addCategory("android.intent.category.BROWSABLE");
      paramString.setComponent(null);
      Intent localIntent = paramString.getSelector();
      if (localIntent != null)
      {
        localIntent.addCategory("android.intent.category.BROWSABLE");
        localIntent.setComponent(null);
      }
      paramString.putExtra("com.android.browser.application_id", paramContext.getPackageName());
      return AwContents.activityFromContext(paramContext) != null;
    }
    catch (Exception paramContext)
    {
      try
      {
        paramContext.startActivity(paramString);
        return true;
      }
      catch (ActivityNotFoundException paramContext) {}
      paramContext = paramContext;
      return false;
    }
  }
  
  public abstract void doUpdateVisitedHistory(String paramString, boolean paramBoolean);
  
  final int getCachedRendererBackgroundColor()
  {
    return this.mCachedRendererBackgroundColor;
  }
  
  final AwContentsClientCallbackHelper getCallbackHelper()
  {
    return this.mCallbackHelper;
  }
  
  public abstract Bitmap getDefaultVideoPoster();
  
  protected abstract View getVideoLoadingProgressView();
  
  public abstract void getVisitedHistory(Callback<String[]> paramCallback);
  
  protected abstract void handleJsAlert(String paramString1, String paramString2, JsResultReceiver paramJsResultReceiver);
  
  protected abstract void handleJsBeforeUnload(String paramString1, String paramString2, JsResultReceiver paramJsResultReceiver);
  
  protected abstract void handleJsConfirm(String paramString1, String paramString2, JsResultReceiver paramJsResultReceiver);
  
  protected abstract void handleJsPrompt(String paramString1, String paramString2, String paramString3, JsPromptResultReceiver paramJsPromptResultReceiver);
  
  public abstract boolean hasWebViewClient();
  
  final boolean isCachedRendererBackgroundColorValid()
  {
    return this.mCachedRendererBackgroundColor != 0;
  }
  
  final void onBackgroundColorChanged(int paramInt)
  {
    int i = paramInt;
    if (paramInt == 0) {
      i = 1;
    }
    this.mCachedRendererBackgroundColor = i;
  }
  
  protected abstract void onCloseWindow();
  
  public abstract boolean onConsoleMessage(AwConsoleMessage paramAwConsoleMessage);
  
  protected abstract boolean onCreateWindow(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong);
  
  public abstract void onFindResultReceived(int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract void onFormResubmission(Message paramMessage1, Message paramMessage2);
  
  public abstract void onGeolocationPermissionsHidePrompt();
  
  public abstract void onGeolocationPermissionsShowPrompt(String paramString, AwGeolocationPermissions.Callback paramCallback);
  
  public abstract void onHideCustomView();
  
  public abstract void onLoadResource(String paramString);
  
  public abstract void onNewPicture(Picture paramPicture);
  
  public abstract void onPageCommitVisible(String paramString);
  
  public abstract void onPageFinished(String paramString);
  
  public abstract void onPageStarted(String paramString);
  
  public abstract void onPermissionRequest(AwPermissionRequest paramAwPermissionRequest);
  
  public abstract void onPermissionRequestCanceled(AwPermissionRequest paramAwPermissionRequest);
  
  public abstract void onProgressChanged(int paramInt);
  
  public abstract void onReceivedClientCertRequest(AwContentsClientBridge.ClientCertificateRequestCallback paramClientCertificateRequestCallback, String[] paramArrayOfString, Principal[] paramArrayOfPrincipal, String paramString, int paramInt);
  
  public abstract void onReceivedDidFailLoad(String paramString, int paramInt);
  
  protected abstract void onReceivedError(int paramInt, String paramString1, String paramString2);
  
  public final void onReceivedError(AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceError paramAwWebResourceError)
  {
    onReceivedError2(paramAwWebResourceRequest, paramAwWebResourceError);
  }
  
  protected abstract void onReceivedError2(AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceError paramAwWebResourceError);
  
  public abstract void onReceivedHttpAuthRequest(AwHttpAuthHandler paramAwHttpAuthHandler, String paramString1, String paramString2);
  
  public abstract void onReceivedHttpError(AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse);
  
  public abstract void onReceivedIcon(Bitmap paramBitmap);
  
  public abstract void onReceivedLoginRequest(String paramString1, String paramString2, String paramString3);
  
  public abstract void onReceivedSslError(Callback<Boolean> paramCallback, SslError paramSslError);
  
  public abstract void onReceivedTitle(String paramString);
  
  public abstract void onReceivedTouchIconUrl(String paramString, boolean paramBoolean);
  
  public abstract boolean onRenderProcessGone(AwRenderProcessGoneDetail paramAwRenderProcessGoneDetail);
  
  protected abstract void onRequestFocus();
  
  protected abstract void onSafeBrowsingHit(AwWebResourceRequest paramAwWebResourceRequest, int paramInt, Callback<AwSafeBrowsingResponse> paramCallback);
  
  public abstract void onScaleChangedScaled(float paramFloat1, float paramFloat2);
  
  public abstract void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback);
  
  public abstract void onUnhandledKeyEvent(KeyEvent paramKeyEvent);
  
  public final boolean shouldIgnoreNavigation(Context paramContext, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    AwContentsClientCallbackHelper.CancelCallbackPoller localCancelCallbackPoller = this.mCallbackHelper.a();
    if ((localCancelCallbackPoller != null) && (localCancelCallbackPoller.shouldCancelAllCallbacks())) {
      return false;
    }
    if (hasWebViewClient())
    {
      paramContext = new AwWebResourceRequest();
      paramContext.url = paramString;
      paramContext.isMainFrame = paramBoolean1;
      paramContext.hasUserGesture = paramBoolean2;
      paramContext.isRedirect = paramBoolean3;
      paramContext.method = "GET";
      return shouldOverrideUrlLoading(paramContext);
    }
    return sendBrowsingIntent(paramContext, paramString, paramBoolean2, paramBoolean3);
  }
  
  public abstract AwWebResourceResponse shouldInterceptRequest(AwWebResourceRequest paramAwWebResourceRequest);
  
  public abstract boolean shouldOverrideKeyEvent(KeyEvent paramKeyEvent);
  
  public abstract boolean shouldOverrideUrlLoading(AwWebResourceRequest paramAwWebResourceRequest);
  
  public abstract void showFileChooser(Callback<String[]> paramCallback, FileChooserParamsImpl paramFileChooserParamsImpl);
  
  public boolean skipPageFinishEventForErrAborted(String paramString)
  {
    return false;
  }
  
  public boolean skipPageFinishEventForFailLoad(String paramString)
  {
    return false;
  }
  
  public final void updateTitle(String paramString, boolean paramBoolean)
  {
    if ((!paramBoolean) && (TextUtils.equals(this.mTitle, paramString))) {
      return;
    }
    this.mTitle = paramString;
    this.mCallbackHelper.postOnReceivedTitle(this.mTitle);
  }
  
  public static class AwWebResourceError
  {
    public String description;
    public int errorCode = -1;
  }
  
  public static class AwWebResourceRequest
  {
    public boolean hasUserGesture;
    public boolean isMainFrame;
    public boolean isRedirect;
    public String method;
    public HashMap<String, String> requestHeaders;
    public int resourceType;
    public String url;
  }
  
  public static abstract interface CustomViewCallback
  {
    public abstract void onCustomViewHidden();
  }
  
  public static class FileChooserParamsImpl
  {
    private int jdField_a_of_type_Int;
    private String jdField_a_of_type_JavaLangString;
    private boolean jdField_a_of_type_Boolean;
    private String b;
    private String c;
    
    public FileChooserParamsImpl(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    {
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.b = paramString2;
      this.c = paramString3;
      this.jdField_a_of_type_Boolean = paramBoolean;
    }
    
    public Intent createIntent()
    {
      Object localObject2 = "*/*";
      String str = this.jdField_a_of_type_JavaLangString;
      Object localObject1 = localObject2;
      if (str != null)
      {
        localObject1 = localObject2;
        if (!str.trim().isEmpty()) {
          localObject1 = this.jdField_a_of_type_JavaLangString.split(",")[0];
        }
      }
      localObject2 = new Intent("android.intent.action.GET_CONTENT");
      ((Intent)localObject2).addCategory("android.intent.category.OPENABLE");
      ((Intent)localObject2).setType((String)localObject1);
      return (Intent)localObject2;
    }
    
    public String[] getAcceptTypes()
    {
      String str = this.jdField_a_of_type_JavaLangString;
      if (str == null) {
        return new String[0];
      }
      return str.split(",");
    }
    
    public String getAcceptTypesString()
    {
      return this.jdField_a_of_type_JavaLangString;
    }
    
    public String getFilenameHint()
    {
      return this.c;
    }
    
    public int getMode()
    {
      return this.jdField_a_of_type_Int;
    }
    
    public CharSequence getTitle()
    {
      return this.b;
    }
    
    public boolean isCaptureEnabled()
    {
      return this.jdField_a_of_type_Boolean;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwContentsClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */