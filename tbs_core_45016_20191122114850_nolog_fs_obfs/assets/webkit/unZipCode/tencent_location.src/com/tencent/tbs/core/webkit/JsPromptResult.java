package com.tencent.tbs.core.webkit;

public class JsPromptResult
  extends JsResult
{
  private String mStringResult;
  
  public JsPromptResult(JsResult.ResultReceiver paramResultReceiver)
  {
    super(paramResultReceiver);
  }
  
  public void confirm(String paramString)
  {
    this.mStringResult = paramString;
    confirm();
  }
  
  public String getStringResult()
  {
    return this.mStringResult;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\JsPromptResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */