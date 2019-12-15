package com.tencent.smtt.net;

import android.webkit.JavascriptInterface;
import com.tencent.smtt.webkit.WebViewProviderExtension;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProvider;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;

public class X5LogReportJsApi
{
  public static final String BBSMB = "https://bbs.mb.qq.com/mobilefb/fb";
  public static final String BBSMBTEST = "http://fbtest.sparta.html5.qq.com/mobilefb";
  public static final String MYTEST = "http://www.coffee1993.com";
  public static String TAG = "X5LogReportJsApi";
  private TencentWebViewProxy a;
  
  public X5LogReportJsApi(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.a = paramTencentWebViewProxy;
  }
  
  @JavascriptInterface
  public void uploadLivelog()
  {
    String str = this.a.getWebViewProvider().getExtension().getNavigateUrl();
    if ((str != null) && ((str.toLowerCase().startsWith("https://bbs.mb.qq.com/mobilefb/fb")) || (str.toLowerCase().startsWith("http://www.coffee1993.com")) || (str.toLowerCase().startsWith("http://fbtest.sparta.html5.qq.com/mobilefb"))))
    {
      com.tencent.smtt.livelog.a.a().a();
      com.tencent.smtt.c.a.a().a();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\X5LogReportJsApi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */