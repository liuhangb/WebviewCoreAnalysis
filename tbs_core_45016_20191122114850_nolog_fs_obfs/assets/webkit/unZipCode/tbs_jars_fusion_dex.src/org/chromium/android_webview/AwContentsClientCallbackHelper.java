package org.chromium.android_webview;

import android.graphics.Picture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import java.util.concurrent.Callable;
import org.chromium.base.Callback;
import org.chromium.base.VisibleForTesting;

@VisibleForTesting
public class AwContentsClientCallbackHelper
{
  private long jdField_a_of_type_Long;
  private final Handler jdField_a_of_type_AndroidOsHandler;
  private final AwContentsClient jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient;
  private CancelCallbackPoller jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClientCallbackHelper$CancelCallbackPoller;
  private boolean jdField_a_of_type_Boolean;
  
  public AwContentsClientCallbackHelper(Looper paramLooper, AwContentsClient paramAwContentsClient)
  {
    this.jdField_a_of_type_AndroidOsHandler = new MyHandler(paramLooper, null);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient = paramAwContentsClient;
  }
  
  CancelCallbackPoller a()
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClientCallbackHelper$CancelCallbackPoller;
  }
  
  void a()
  {
    this.jdField_a_of_type_AndroidOsHandler.removeCallbacksAndMessages(null);
  }
  
  public void postDoUpdateVisitedHistory(String paramString, boolean paramBoolean)
  {
    paramString = new DoUpdateVisitedHistoryInfo(paramString, paramBoolean);
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessage(localHandler.obtainMessage(13, paramString));
  }
  
  public void postOnDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
  {
    paramString1 = new DownloadInfo(paramString1, paramString2, paramString3, paramString4, paramLong);
    paramString2 = this.jdField_a_of_type_AndroidOsHandler;
    paramString2.sendMessage(paramString2.obtainMessage(3, paramString1));
  }
  
  public void postOnFormResubmission(Message paramMessage1, Message paramMessage2)
  {
    paramMessage1 = new OnFormResubmissionInfo(paramMessage1, paramMessage2);
    paramMessage2 = this.jdField_a_of_type_AndroidOsHandler;
    paramMessage2.sendMessage(paramMessage2.obtainMessage(14, paramMessage1));
  }
  
  public void postOnLoadResource(String paramString)
  {
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessage(localHandler.obtainMessage(1, paramString));
  }
  
  public void postOnNewPicture(Callable<Picture> paramCallable)
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_Boolean = true;
    long l = Math.max(this.jdField_a_of_type_Long + 500L, SystemClock.uptimeMillis());
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessageAtTime(localHandler.obtainMessage(6, paramCallable), l);
  }
  
  public void postOnPageFinished(String paramString)
  {
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessage(localHandler.obtainMessage(9, paramString));
  }
  
  public void postOnPageStarted(String paramString)
  {
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessage(localHandler.obtainMessage(2, paramString));
  }
  
  public void postOnProgressChanged(int paramInt)
  {
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessage(localHandler.obtainMessage(11, paramInt, 0));
  }
  
  public void postOnReceivedError(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwContentsClient.AwWebResourceError paramAwWebResourceError)
  {
    paramAwWebResourceRequest = new OnReceivedErrorInfo(paramAwWebResourceRequest, paramAwWebResourceError);
    paramAwWebResourceError = this.jdField_a_of_type_AndroidOsHandler;
    paramAwWebResourceError.sendMessage(paramAwWebResourceError.obtainMessage(5, paramAwWebResourceRequest));
  }
  
  public void postOnReceivedFailLoad(String paramString, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("url", paramString);
    localBundle.putInt("errorCode", paramInt);
    paramString = this.jdField_a_of_type_AndroidOsHandler;
    paramString.sendMessage(paramString.obtainMessage(16, localBundle));
  }
  
  public void postOnReceivedHttpError(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse)
  {
    paramAwWebResourceRequest = new OnReceivedHttpErrorInfo(paramAwWebResourceRequest, paramAwWebResourceResponse);
    paramAwWebResourceResponse = this.jdField_a_of_type_AndroidOsHandler;
    paramAwWebResourceResponse.sendMessage(paramAwWebResourceResponse.obtainMessage(8, paramAwWebResourceRequest));
  }
  
  public void postOnReceivedLoginRequest(String paramString1, String paramString2, String paramString3)
  {
    paramString1 = new LoginRequestInfo(paramString1, paramString2, paramString3);
    paramString2 = this.jdField_a_of_type_AndroidOsHandler;
    paramString2.sendMessage(paramString2.obtainMessage(4, paramString1));
  }
  
  public void postOnReceivedTitle(String paramString)
  {
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessage(localHandler.obtainMessage(10, paramString));
  }
  
  public void postOnSafeBrowsingHit(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, int paramInt, Callback<AwSafeBrowsingResponse> paramCallback)
  {
    paramAwWebResourceRequest = new OnSafeBrowsingHitInfo(paramAwWebResourceRequest, paramInt, paramCallback);
    paramCallback = this.jdField_a_of_type_AndroidOsHandler;
    paramCallback.sendMessage(paramCallback.obtainMessage(15, paramAwWebResourceRequest));
  }
  
  public void postOnScaleChangedScaled(float paramFloat1, float paramFloat2)
  {
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessage(localHandler.obtainMessage(7, Float.floatToIntBits(paramFloat1), Float.floatToIntBits(paramFloat2)));
  }
  
  public void postSynthesizedPageLoadingForUrlBarUpdate(String paramString)
  {
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessage(localHandler.obtainMessage(12, paramString));
  }
  
  public void setCancelCallbackPoller(CancelCallbackPoller paramCancelCallbackPoller)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClientCallbackHelper$CancelCallbackPoller = paramCancelCallbackPoller;
  }
  
  public static abstract interface CancelCallbackPoller
  {
    public abstract boolean shouldCancelAllCallbacks();
  }
  
  private static class DoUpdateVisitedHistoryInfo
  {
    final String jdField_a_of_type_JavaLangString;
    final boolean jdField_a_of_type_Boolean;
    
    DoUpdateVisitedHistoryInfo(String paramString, boolean paramBoolean)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      this.jdField_a_of_type_Boolean = paramBoolean;
    }
  }
  
  private static class DownloadInfo
  {
    final long jdField_a_of_type_Long;
    final String jdField_a_of_type_JavaLangString;
    final String b;
    final String c;
    final String d;
    
    DownloadInfo(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
    {
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.b = paramString2;
      this.c = paramString3;
      this.d = paramString4;
      this.jdField_a_of_type_Long = paramLong;
    }
  }
  
  private static class LoginRequestInfo
  {
    final String a;
    final String b;
    final String c;
    
    LoginRequestInfo(String paramString1, String paramString2, String paramString3)
    {
      this.a = paramString1;
      this.b = paramString2;
      this.c = paramString3;
    }
  }
  
  private class MyHandler
    extends Handler
  {
    private MyHandler(Looper paramLooper)
    {
      super();
    }
    
    public void handleMessage(Message paramMessage)
    {
      AwContentsClientCallbackHelper.CancelCallbackPoller localCancelCallbackPoller = AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this);
      Object localObject = null;
      if ((localCancelCallbackPoller != null) && (AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).shouldCancelAllCallbacks()))
      {
        removeCallbacksAndMessages(null);
        return;
      }
      switch (paramMessage.what)
      {
      default: 
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("AwContentsClientCallbackHelper: unhandled message ");
        ((StringBuilder)localObject).append(paramMessage.what);
        throw new IllegalStateException(((StringBuilder)localObject).toString());
      case 16: 
        paramMessage = (Bundle)paramMessage.obj;
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onReceivedDidFailLoad(paramMessage.getString("url"), paramMessage.getInt("errorCode"));
        return;
      case 15: 
        paramMessage = (AwContentsClientCallbackHelper.OnSafeBrowsingHitInfo)paramMessage.obj;
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onSafeBrowsingHit(paramMessage.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient$AwWebResourceRequest, paramMessage.jdField_a_of_type_Int, paramMessage.jdField_a_of_type_OrgChromiumBaseCallback);
        return;
      case 14: 
        paramMessage = (AwContentsClientCallbackHelper.OnFormResubmissionInfo)paramMessage.obj;
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onFormResubmission(paramMessage.a, paramMessage.b);
        return;
      case 13: 
        paramMessage = (AwContentsClientCallbackHelper.DoUpdateVisitedHistoryInfo)paramMessage.obj;
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).doUpdateVisitedHistory(paramMessage.jdField_a_of_type_JavaLangString, paramMessage.jdField_a_of_type_Boolean);
        return;
      case 12: 
        paramMessage = (String)paramMessage.obj;
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onPageStarted(paramMessage);
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onLoadResource(paramMessage);
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onProgressChanged(100);
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onPageFinished(paramMessage);
        return;
      case 11: 
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onProgressChanged(paramMessage.arg1);
        return;
      case 10: 
        paramMessage = (String)paramMessage.obj;
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onReceivedTitle(paramMessage);
        return;
      case 9: 
        paramMessage = (String)paramMessage.obj;
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onPageFinished(paramMessage);
        return;
      case 8: 
        paramMessage = (AwContentsClientCallbackHelper.OnReceivedHttpErrorInfo)paramMessage.obj;
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onReceivedHttpError(paramMessage.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient$AwWebResourceRequest, paramMessage.jdField_a_of_type_OrgChromiumAndroid_webviewAwWebResourceResponse);
        return;
      case 7: 
        float f1 = Float.intBitsToFloat(paramMessage.arg1);
        float f2 = Float.intBitsToFloat(paramMessage.arg2);
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onScaleChangedScaled(f1, f2);
        return;
      case 6: 
        try
        {
          if (paramMessage.obj != null) {
            localObject = (Picture)((Callable)paramMessage.obj).call();
          }
          AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onNewPicture((Picture)localObject);
          AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this, SystemClock.uptimeMillis());
          AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this, false);
          return;
        }
        catch (Exception paramMessage)
        {
          throw new RuntimeException("Error getting picture", paramMessage);
        }
      case 5: 
        paramMessage = (AwContentsClientCallbackHelper.OnReceivedErrorInfo)paramMessage.obj;
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onReceivedError(paramMessage.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient$AwWebResourceRequest, paramMessage.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient$AwWebResourceError);
        return;
      case 4: 
        paramMessage = (AwContentsClientCallbackHelper.LoginRequestInfo)paramMessage.obj;
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onReceivedLoginRequest(paramMessage.jdField_a_of_type_JavaLangString, paramMessage.b, paramMessage.c);
        return;
      case 3: 
        paramMessage = (AwContentsClientCallbackHelper.DownloadInfo)paramMessage.obj;
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onDownloadStart(paramMessage.jdField_a_of_type_JavaLangString, paramMessage.b, paramMessage.c, paramMessage.d, paramMessage.jdField_a_of_type_Long);
        return;
      case 2: 
        paramMessage = (String)paramMessage.obj;
        AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onPageStarted(paramMessage);
        return;
      }
      paramMessage = (String)paramMessage.obj;
      AwContentsClientCallbackHelper.a(AwContentsClientCallbackHelper.this).onLoadResource(paramMessage);
    }
  }
  
  private static class OnFormResubmissionInfo
  {
    final Message a;
    final Message b;
    
    OnFormResubmissionInfo(Message paramMessage1, Message paramMessage2)
    {
      this.a = paramMessage1;
      this.b = paramMessage2;
    }
  }
  
  private static class OnReceivedErrorInfo
  {
    final AwContentsClient.AwWebResourceError jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient$AwWebResourceError;
    final AwContentsClient.AwWebResourceRequest jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient$AwWebResourceRequest;
    
    OnReceivedErrorInfo(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwContentsClient.AwWebResourceError paramAwWebResourceError)
    {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient$AwWebResourceRequest = paramAwWebResourceRequest;
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient$AwWebResourceError = paramAwWebResourceError;
    }
  }
  
  private static class OnReceivedHttpErrorInfo
  {
    final AwContentsClient.AwWebResourceRequest jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient$AwWebResourceRequest;
    final AwWebResourceResponse jdField_a_of_type_OrgChromiumAndroid_webviewAwWebResourceResponse;
    
    OnReceivedHttpErrorInfo(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse)
    {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient$AwWebResourceRequest = paramAwWebResourceRequest;
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwWebResourceResponse = paramAwWebResourceResponse;
    }
  }
  
  private static class OnSafeBrowsingHitInfo
  {
    final int jdField_a_of_type_Int;
    final AwContentsClient.AwWebResourceRequest jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient$AwWebResourceRequest;
    final Callback<AwSafeBrowsingResponse> jdField_a_of_type_OrgChromiumBaseCallback;
    
    OnSafeBrowsingHitInfo(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, int paramInt, Callback<AwSafeBrowsingResponse> paramCallback)
    {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient$AwWebResourceRequest = paramAwWebResourceRequest;
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_OrgChromiumBaseCallback = paramCallback;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwContentsClientCallbackHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */