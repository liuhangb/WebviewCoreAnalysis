package org.chromium.tencent.android_webview.permission;

import android.net.Uri;
import android.util.Log;
import org.chromium.android_webview.permission.AwPermissionRequest;

public class TencentAwPermissionRequest
  extends AwPermissionRequest
{
  private static final String TAG = "TencentAwPermissionRequest";
  private AwPermissionRequestClient mClient = null;
  
  public TencentAwPermissionRequest(long paramLong1, Uri paramUri, long paramLong2)
  {
    super(paramLong1, paramUri, paramLong2);
  }
  
  public void deny()
  {
    if (this.mProcessed)
    {
      Log.e("TencentAwPermissionRequest", "duplicate deny/grant");
      return;
    }
    super.deny();
  }
  
  public void grant(long paramLong)
  {
    AwPermissionRequestClient localAwPermissionRequestClient = this.mClient;
    if (localAwPermissionRequestClient != null)
    {
      localAwPermissionRequestClient.onPermissionRequestOnUI(this, paramLong);
      return;
    }
    grantOnNative(paramLong);
  }
  
  public void grantOnNative(long paramLong)
  {
    if (this.mProcessed)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("duplicate grant/deny, allow:");
      localStringBuilder.append(paramLong);
      Log.e("TencentAwPermissionRequest", localStringBuilder.toString());
      return;
    }
    validate();
    if (this.mNativeAwPermissionRequest != 0L)
    {
      nativeOnAccept(this.mNativeAwPermissionRequest, paramLong);
      destroyNative();
    }
    this.mProcessed = true;
  }
  
  public void setPermissionRequestClient(AwPermissionRequestClient paramAwPermissionRequestClient)
  {
    this.mClient = paramAwPermissionRequestClient;
  }
  
  public static abstract interface AwPermissionRequestClient
  {
    public abstract void onPermissionRequestOnUI(TencentAwPermissionRequest paramTencentAwPermissionRequest, long paramLong);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\android_webview\permission\TencentAwPermissionRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */