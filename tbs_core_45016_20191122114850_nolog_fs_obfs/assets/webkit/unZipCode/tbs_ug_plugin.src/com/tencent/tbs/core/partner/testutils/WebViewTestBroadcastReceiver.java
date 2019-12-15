package com.tencent.tbs.core.partner.testutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewClient;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;

public class WebViewTestBroadcastReceiver
  extends BroadcastReceiver
{
  private static final String BY_CLASS_NAME = "class_name";
  private static final String BY_CSS_SELECTOR = "css_selector";
  private static final String BY_HREF = "href";
  private static final String BY_ID = "id";
  private static final String BY_NAME = "name";
  private static final String BY_TAG_NAME = "tag_name";
  private static final String BY_TEXT_CONTENT = "text_content";
  private static final String BY_XPATH = "xpath";
  private static final String FUN_CLEAR_TEXT = "clear_text";
  private static final String FUN_CLICK = "click";
  private static final String FUN_FOCUS = "focus";
  private static final String FUN_GET_ALL_WEBELEMENTS = "get_all_webelements";
  private static final String FUN_GO_BACK = "go_back";
  private static final String FUN_GO_FORWARD = "go_forward";
  private static final String FUN_LOAD_URL = "loadurl";
  private static final String FUN_TYPE_TEXT = "type_text";
  private boolean isSetWebClient = false;
  private WebUtils mWebUtils;
  private TencentWebViewProxy mWebView = null;
  
  private By getByType(String paramString1, String paramString2)
  {
    if (paramString1.equals("class_name")) {
      return new By.ClassName(paramString2);
    }
    if (paramString1.equals("css_selector")) {
      return new By.CssSelector(paramString2);
    }
    if (paramString1.equals("id")) {
      return new By.Id(paramString2);
    }
    if (paramString1.equals("name")) {
      return new By.Name(paramString2);
    }
    if (paramString1.equals("tag_name")) {
      return new By.TagName(paramString2);
    }
    if (paramString1.equals("text_content")) {
      return new By.Text(paramString2);
    }
    if (paramString1.equals("xpath")) {
      return new By.Xpath(paramString2.replaceAll("u002E", "\""));
    }
    if (paramString1.equals("href")) {
      return new By.Href(paramString2);
    }
    return null;
  }
  
  public void onReceive(final Context paramContext, Intent paramIntent)
  {
    if (paramIntent != null)
    {
      if (this.mWebView == null) {
        return;
      }
      if (paramIntent.getAction().equals("com.tbs.test"))
      {
        paramContext = paramIntent.getStringExtra("function");
        if (paramContext.equals("loadurl"))
        {
          paramContext = paramIntent.getStringExtra("url");
          this.mWebView.getRealWebView().post(new Runnable()
          {
            public void run()
            {
              WebViewTestBroadcastReceiver.this.mWebView.loadUrl(paramContext);
            }
          });
          return;
        }
        if (paramContext.equals("go_back"))
        {
          if (this.mWebView.canGoBack()) {
            this.mWebView.getRealWebView().post(new Runnable()
            {
              public void run()
              {
                WebViewTestBroadcastReceiver.this.mWebView.goBack();
              }
            });
          }
        }
        else if (paramContext.equals("go_forward"))
        {
          if (this.mWebView.canGoForward()) {
            this.mWebView.getRealWebView().post(new Runnable()
            {
              public void run()
              {
                WebViewTestBroadcastReceiver.this.mWebView.goForward();
              }
            });
          }
        }
        else
        {
          if (paramContext.equals("type_text"))
          {
            paramContext = paramIntent.getStringExtra("by");
            String str = paramIntent.getStringExtra("extra");
            paramIntent = paramIntent.getStringExtra("text");
            paramContext = getByType(paramContext, str);
            this.mWebUtils.enterTextIntoWebElement(paramContext, paramIntent);
            return;
          }
          if (paramContext.equals("clear_text"))
          {
            paramContext = getByType(paramIntent.getStringExtra("by"), paramIntent.getStringExtra("extra"));
            this.mWebUtils.enterTextIntoWebElement(paramContext, "");
            return;
          }
          if (paramContext.equals("click"))
          {
            paramContext = getByType(paramIntent.getStringExtra("by"), paramIntent.getStringExtra("extra"));
            this.mWebUtils.clickOnWebElement(paramContext);
            return;
          }
          if (paramContext.equals("focus"))
          {
            paramContext = getByType(paramIntent.getStringExtra("by"), paramIntent.getStringExtra("extra"));
            this.mWebUtils.clickOnWebElement(paramContext);
            return;
          }
          if (paramContext.equals("get_all_webelements"))
          {
            paramContext = paramIntent.getStringExtra("by");
            paramIntent = paramIntent.getStringExtra("extra");
            if ((!TextUtils.isEmpty(paramContext)) && (!TextUtils.isEmpty(paramIntent)))
            {
              new Thread(new Runnable()
              {
                public void run()
                {
                  WebViewTestBroadcastReceiver.this.mWebUtils.getWebElements(this.val$by, false);
                }
              }).start();
              return;
            }
            new Thread(new Runnable()
            {
              public void run()
              {
                WebViewTestBroadcastReceiver.this.mWebUtils.getWebElements(false);
              }
            }).start();
            return;
          }
          Log.e("for-test", "elements is null");
        }
      }
      return;
    }
  }
  
  public void setWebView(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.mWebView = paramTencentWebViewProxy;
    this.mWebUtils = new WebUtils(paramTencentWebViewProxy);
    if (!this.isSetWebClient)
    {
      paramTencentWebViewProxy = new TencentWebViewClient()
      {
        public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("page finished,url = ");
          localStringBuilder.append(paramAnonymousString);
          Log.e("for-test", localStringBuilder.toString());
          super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
        }
        
        public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("should override url loading,url = ");
          localStringBuilder.append(paramAnonymousString);
          Log.e("for-test", localStringBuilder.toString());
          paramAnonymousWebView.loadUrl(paramAnonymousString);
          return true;
        }
      };
      this.mWebView.setWebViewClient(paramTencentWebViewProxy);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\testutils\WebViewTestBroadcastReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */