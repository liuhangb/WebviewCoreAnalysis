package android.webview.chromium;

import android.content.Context;
import com.tencent.tbs.core.webkit.WebViewFactory;
import org.chromium.base.library_loader.NativeLibraryPreloader;

public class MonochromeLibraryPreloader
  extends NativeLibraryPreloader
{
  public int loadLibrary(Context paramContext)
  {
    return WebViewFactory.loadWebViewNativeLibraryFromPackage(paramContext.getPackageName(), getClass().getClassLoader());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\MonochromeLibraryPreloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */