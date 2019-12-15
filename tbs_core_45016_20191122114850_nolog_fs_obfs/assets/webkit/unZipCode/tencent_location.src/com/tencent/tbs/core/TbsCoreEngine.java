package com.tencent.tbs.core;

import com.tencent.tbs.core.partner.PartnerWebViewObserver;
import com.tencent.tbs.core.webkit.WebView;

public class TbsCoreEngine
{
  private static final boolean BUILD_MODE_PARTNER = true;
  
  public static WebViewObserver createWebViewObserver(WebView paramWebView)
  {
    return new PartnerWebViewObserver(paramWebView);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\TbsCoreEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */