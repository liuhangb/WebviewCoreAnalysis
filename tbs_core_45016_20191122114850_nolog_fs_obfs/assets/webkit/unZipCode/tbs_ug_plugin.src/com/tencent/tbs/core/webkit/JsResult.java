package com.tencent.tbs.core.webkit;

public class JsResult
{
  private final ResultReceiver mReceiver;
  private boolean mResult;
  
  public JsResult(ResultReceiver paramResultReceiver)
  {
    this.mReceiver = paramResultReceiver;
  }
  
  private final void wakeUp()
  {
    this.mReceiver.onJsResultComplete(this);
  }
  
  public void cancel()
  {
    this.mResult = false;
    wakeUp();
  }
  
  public void confirm()
  {
    this.mResult = true;
    wakeUp();
  }
  
  public final boolean getResult()
  {
    return this.mResult;
  }
  
  public static abstract interface ResultReceiver
  {
    public abstract void onJsResultComplete(JsResult paramJsResult);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\JsResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */