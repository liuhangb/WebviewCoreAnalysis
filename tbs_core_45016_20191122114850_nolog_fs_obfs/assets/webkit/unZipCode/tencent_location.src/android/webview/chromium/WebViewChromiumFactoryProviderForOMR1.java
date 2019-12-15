package android.webview.chromium;

import android.webkit.WebViewDelegate;

class WebViewChromiumFactoryProviderForOMR1
  extends WebViewChromiumFactoryProvider
{
  protected WebViewChromiumFactoryProviderForOMR1(WebViewDelegate paramWebViewDelegate)
  {
    super(paramWebViewDelegate);
  }
  
  public static WebViewChromiumFactoryProvider create(WebViewDelegate paramWebViewDelegate)
  {
    return new WebViewChromiumFactoryProviderForOMR1(paramWebViewDelegate);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebViewChromiumFactoryProviderForOMR1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */