package org.chromium.android_webview;

import org.chromium.base.ThreadUtils;

public class JsResultHandler
  implements JsPromptResultReceiver, JsResultReceiver
{
  private final int jdField_a_of_type_Int;
  private AwContentsClientBridge jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClientBridge;
  
  public JsResultHandler(AwContentsClientBridge paramAwContentsClientBridge, int paramInt)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClientBridge = paramAwContentsClientBridge;
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public void cancel()
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (JsResultHandler.a(JsResultHandler.this) != null) {
          JsResultHandler.a(JsResultHandler.this).cancelJsResult(JsResultHandler.a(JsResultHandler.this));
        }
        JsResultHandler.a(JsResultHandler.this, null);
      }
    });
  }
  
  public void confirm()
  {
    confirm(null);
  }
  
  public void confirm(final String paramString)
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (JsResultHandler.a(JsResultHandler.this) != null) {
          JsResultHandler.a(JsResultHandler.this).confirmJsResult(JsResultHandler.a(JsResultHandler.this), paramString);
        }
        JsResultHandler.a(JsResultHandler.this, null);
      }
    });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\JsResultHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */