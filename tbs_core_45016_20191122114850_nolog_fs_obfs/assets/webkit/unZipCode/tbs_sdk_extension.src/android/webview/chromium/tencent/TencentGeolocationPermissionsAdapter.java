package android.webview.chromium.tencent;

import android.webview.chromium.GeolocationPermissionsAdapter;
import android.webview.chromium.WebViewChromiumFactoryProvider;
import java.util.Set;
import org.chromium.android_webview.AwGeolocationPermissions;
import org.chromium.base.annotations.UsedByReflection;

public class TencentGeolocationPermissionsAdapter
  extends GeolocationPermissionsAdapter
{
  @UsedByReflection("WebCoreProxy.java")
  public TencentGeolocationPermissionsAdapter(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider, AwGeolocationPermissions paramAwGeolocationPermissions)
  {
    super(paramWebViewChromiumFactoryProvider, paramAwGeolocationPermissions);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public final void getAllowed(String paramString, final android.webkit.ValueCallback<Boolean> paramValueCallback)
  {
    if (paramValueCallback == null) {
      paramValueCallback = null;
    } else {
      paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
      {
        public void onReceiveValue(Boolean paramAnonymousBoolean)
        {
          paramValueCallback.onReceiveValue(paramAnonymousBoolean);
        }
      };
    }
    getAllowed(paramString, paramValueCallback);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public final void getOrigins(final android.webkit.ValueCallback<Set<String>> paramValueCallback)
  {
    if (paramValueCallback == null) {
      paramValueCallback = null;
    } else {
      paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
      {
        public void onReceiveValue(Set<String> paramAnonymousSet)
        {
          paramValueCallback.onReceiveValue(paramAnonymousSet);
        }
      };
    }
    getOrigins(paramValueCallback);
  }
  
  public boolean hasOrigin(String paramString)
  {
    return this.mChromeGeolocationPermissions.hasOrigin(paramString);
  }
  
  public boolean isOriginAllowed(String paramString)
  {
    return this.mChromeGeolocationPermissions.isOriginAllowed(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentGeolocationPermissionsAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */