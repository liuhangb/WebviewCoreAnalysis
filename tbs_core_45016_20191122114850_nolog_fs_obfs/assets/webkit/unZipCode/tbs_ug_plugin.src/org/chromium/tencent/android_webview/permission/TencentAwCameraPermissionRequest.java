package org.chromium.tencent.android_webview.permission;

import android.net.Uri;
import org.chromium.android_webview.JsPromptResultReceiver;

public class TencentAwCameraPermissionRequest
  extends TencentAwPermissionRequest
{
  private static final String TAG = "TencentAwCameraPermissionRequest";
  private JsPromptResultReceiver mReceiver;
  
  public TencentAwCameraPermissionRequest(long paramLong1, Uri paramUri, long paramLong2, JsPromptResultReceiver paramJsPromptResultReceiver)
  {
    super(paramLong1, paramUri, paramLong2);
    this.mReceiver = paramJsPromptResultReceiver;
  }
  
  public void deny()
  {
    this.mReceiver.cancel();
  }
  
  public void grantOnNative(long paramLong)
  {
    this.mReceiver.confirm(Boolean.toString(true));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\android_webview\permission\TencentAwCameraPermissionRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */