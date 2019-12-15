package com.tencent.smtt.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import com.tencent.common.utils.UrlUtils;
import com.tencent.smtt.webkit.WebViewProviderExtension;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProvider;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;

public class X5BadJsReporter
{
  public static String TAG = "X5BadJs";
  private int jdField_a_of_type_Int = 0;
  private Handler jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what != 1) {
        return;
      }
      paramAnonymousMessage = (X5BadJsReporter.a)paramAnonymousMessage.obj;
      X5BadJsReporter.a(X5BadJsReporter.this).getWebViewProvider().getExtension().reportBadJsMsg(paramAnonymousMessage);
    }
  };
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private String jdField_a_of_type_JavaLangString;
  
  public X5BadJsReporter(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
  }
  
  @JavascriptInterface
  public void report(String paramString1, String paramString2, String paramString3)
  {
    if ((!TextUtils.isEmpty(paramString1)) && (paramString1.length() < 100) && (!TextUtils.isEmpty(paramString2)) && (!TextUtils.isEmpty(paramString3)))
    {
      String str = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebViewProvider().getExtension().getNavigateUrl();
      if (TextUtils.isEmpty(str)) {
        return;
      }
      if ((!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) && (this.jdField_a_of_type_JavaLangString.equals(str))) {
        this.jdField_a_of_type_Int += 1;
      } else {
        this.jdField_a_of_type_Int = 0;
      }
      this.jdField_a_of_type_JavaLangString = str;
      str = UrlUtils.getHost(str);
      if ((!TextUtils.isEmpty(str)) && (this.jdField_a_of_type_Int < 15) && ((str.endsWith(".qq.com")) || (SmttServiceProxy.getInstance().isAllowReportBadJs(str))))
      {
        if (paramString3.length() >= 500) {
          paramString3 = paramString3.substring(0, 490);
        }
        paramString3 = paramString3.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
        if (paramString2.length() >= 400) {
          paramString2 = paramString2.substring(0, 390);
        }
        paramString1 = new a(paramString1, paramString2, paramString3);
        paramString2 = this.jdField_a_of_type_AndroidOsHandler;
        paramString2.sendMessage(paramString2.obtainMessage(1, paramString1));
      }
    }
  }
  
  public class a
  {
    public String a;
    public String b;
    public String c;
    
    public a(String paramString1, String paramString2, String paramString3)
    {
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.b = paramString2;
      this.c = paramString3;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\X5BadJsReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */