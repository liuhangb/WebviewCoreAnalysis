package android.webview.chromium;

import com.tencent.tbs.core.webkit.WebSettings;
import com.tencent.tbs.core.webkit.WebView;
import org.chromium.android_webview.AwSettings;

public class WebkitToSharedGlueConverter
{
  public static WebViewChromiumAwInit getGlobalAwInit()
  {
    return WebViewChromiumFactoryProvider.getSingleton().getAwInit();
  }
  
  public static AwSettings getSettings(WebSettings paramWebSettings)
  {
    return ((ContentSettingsAdapter)paramWebSettings).getAwSettings();
  }
  
  public static SharedWebViewChromium getSharedWebViewChromium(WebView paramWebView)
  {
    return ((WebViewChromium)paramWebView.getWebViewProvider()).getSharedWebViewChromium();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebkitToSharedGlueConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */