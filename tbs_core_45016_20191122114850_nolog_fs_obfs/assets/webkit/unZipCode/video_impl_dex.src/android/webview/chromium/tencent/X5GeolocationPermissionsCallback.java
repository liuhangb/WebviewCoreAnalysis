package android.webview.chromium.tencent;

import com.tencent.tbs.core.webkit.GeolocationPermissions.Callback;
import org.chromium.android_webview.AwGeolocationPermissions.Callback;

public class X5GeolocationPermissionsCallback
  implements GeolocationPermissions.Callback
{
  private AwGeolocationPermissions.Callback mCallback;
  
  public X5GeolocationPermissionsCallback(AwGeolocationPermissions.Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }
  
  public void invoke(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    AwGeolocationPermissions.Callback localCallback = this.mCallback;
    if (localCallback != null)
    {
      localCallback.invoke(paramString, paramBoolean1, paramBoolean2);
      this.mCallback = null;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\X5GeolocationPermissionsCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */