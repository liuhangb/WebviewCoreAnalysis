package android.webview.chromium;

import com.tencent.tbs.core.webkit.GeolocationPermissions;
import com.tencent.tbs.core.webkit.ValueCallback;
import java.util.Set;
import org.chromium.android_webview.AwGeolocationPermissions;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.UsedByReflection;

public class GeolocationPermissionsAdapter
  extends GeolocationPermissions
{
  protected final AwGeolocationPermissions mChromeGeolocationPermissions;
  private final WebViewChromiumFactoryProvider mFactory;
  
  public GeolocationPermissionsAdapter(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider, AwGeolocationPermissions paramAwGeolocationPermissions)
  {
    this.mFactory = paramWebViewChromiumFactoryProvider;
    this.mChromeGeolocationPermissions = paramAwGeolocationPermissions;
  }
  
  private static boolean checkNeedsPost()
  {
    return ThreadUtils.runningOnUiThread() ^ true;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void allow(final String paramString)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          GeolocationPermissionsAdapter.this.mChromeGeolocationPermissions.allow(paramString);
        }
      });
      return;
    }
    this.mChromeGeolocationPermissions.allow(paramString);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void clear(final String paramString)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          GeolocationPermissionsAdapter.this.mChromeGeolocationPermissions.clear(paramString);
        }
      });
      return;
    }
    this.mChromeGeolocationPermissions.clear(paramString);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void clearAll()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          GeolocationPermissionsAdapter.this.mChromeGeolocationPermissions.clearAll();
        }
      });
      return;
    }
    this.mChromeGeolocationPermissions.clearAll();
  }
  
  public void getAllowed(final String paramString, final ValueCallback<Boolean> paramValueCallback)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          GeolocationPermissionsAdapter.this.mChromeGeolocationPermissions.getAllowed(paramString, CallbackConverter.fromValueCallback(paramValueCallback));
        }
      });
      return;
    }
    this.mChromeGeolocationPermissions.getAllowed(paramString, CallbackConverter.fromValueCallback(paramValueCallback));
  }
  
  public void getOrigins(final ValueCallback<Set<String>> paramValueCallback)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          GeolocationPermissionsAdapter.this.mChromeGeolocationPermissions.getOrigins(CallbackConverter.fromValueCallback(paramValueCallback));
        }
      });
      return;
    }
    this.mChromeGeolocationPermissions.getOrigins(CallbackConverter.fromValueCallback(paramValueCallback));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\GeolocationPermissionsAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */