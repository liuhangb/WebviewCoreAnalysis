package com.tencent.tbs.core.partner.jsextension;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;

public class JsExtensions
{
  private Context mContext;
  protected jsPackages mJsPackage;
  private TencentWebViewProxy mWebview;
  
  public JsExtensions(TencentWebViewProxy paramTencentWebViewProxy)
  {
    if (paramTencentWebViewProxy != null) {
      this.mContext = paramTencentWebViewProxy.getContext();
    }
    this.mWebview = paramTencentWebViewProxy;
  }
  
  @JavascriptInterface
  public String getMiniQBUA()
  {
    return SmttServiceProxy.getInstance().getMiniQBUA();
  }
  
  @JavascriptInterface
  public String getMiniQBVersionCode()
  {
    return SmttServiceProxy.getInstance().getMiniQBVC();
  }
  
  @JavascriptInterface
  public String getMiniQBVersionName()
  {
    return SmttServiceProxy.getInstance().getMiniQbVersionName();
  }
  
  @JavascriptInterface
  public String getQUA2()
  {
    return SmttServiceProxy.getInstance().getQUA2();
  }
  
  @JavascriptInterface
  public boolean isTbsJsapiEnabled()
  {
    return SmttServiceProxy.getInstance().isTbsJsapiEnabled(this.mContext);
  }
  
  @JavascriptInterface
  public void jsAPITest(final String paramString1, final String paramString2)
  {
    new Handler(Looper.getMainLooper()).post(new Runnable()
    {
      public void run()
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("(");
        ((StringBuilder)localObject).append(paramString2);
        ((StringBuilder)localObject).append(")('");
        ((StringBuilder)localObject).append(paramString1);
        ((StringBuilder)localObject).append("')");
        localObject = ((StringBuilder)localObject).toString();
        JsExtensions.this.mWebview.evaluateJavascript((String)localObject, null);
        TencentWebViewProxy localTencentWebViewProxy = JsExtensions.this.mWebview;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("javascript:");
        localStringBuilder.append((String)localObject);
        localTencentWebViewProxy.loadUrl(localStringBuilder.toString());
      }
    });
  }
  
  @JavascriptInterface
  public String onReady(String paramString1, String paramString2)
  {
    return jsApiDomain.getInstance().onReady(paramString1, paramString2, this.mWebview);
  }
  
  @JavascriptInterface
  public jsPackages packages()
  {
    if (this.mJsPackage == null) {
      this.mJsPackage = new jsPackages(this.mContext);
    }
    return this.mJsPackage;
  }
  
  @JavascriptInterface
  public void uploadQQErrorpageShowLittleGame()
  {
    SmttServiceProxy.getInstance().userBehaviorStatistics("BZOD001");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\jsextension\JsExtensions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */