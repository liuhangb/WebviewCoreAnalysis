package com.tencent.smtt.util;

import android.webkit.JavascriptInterface;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import java.lang.ref.WeakReference;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class X5WebRTCJsHelper
{
  private IX5WebRTCPluginLoadCallback jdField_a_of_type_ComTencentSmttUtilX5WebRTCJsHelper$IX5WebRTCPluginLoadCallback = new IX5WebRTCPluginLoadCallback()
  {
    public void onLodingCallback(String paramAnonymousString)
    {
      X5WebRTCJsHelper.this.evaluateJavascript(paramAnonymousString);
    }
  };
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  
  public X5WebRTCJsHelper(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
  }
  
  public void evaluateJavascript(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("javascript:onX5WebRTCPluginStatus('");
    localStringBuilder.append(paramString);
    localStringBuilder.append("');");
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        X5WebRTCJsHelper.a(X5WebRTCJsHelper.this).evaluateJavascript(this.jdField_a_of_type_JavaLangString, null);
      }
    });
  }
  
  @JavascriptInterface
  public void initX5WebRTCPlugin()
  {
    q.a().a(new WeakReference(this.jdField_a_of_type_ComTencentSmttUtilX5WebRTCJsHelper$IX5WebRTCPluginLoadCallback));
  }
  
  @JavascriptInterface
  public boolean isX5WebRTCAvailable()
  {
    return q.a().a();
  }
  
  public static abstract interface IX5WebRTCPluginLoadCallback
  {
    public abstract void onLodingCallback(String paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\X5WebRTCJsHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */