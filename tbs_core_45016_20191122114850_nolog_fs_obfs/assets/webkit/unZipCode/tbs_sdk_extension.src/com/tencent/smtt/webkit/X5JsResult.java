package com.tencent.smtt.webkit;

public class X5JsResult
{
  private final ResultReceiver jdField_a_of_type_ComTencentSmttWebkitX5JsResult$ResultReceiver;
  private boolean jdField_a_of_type_Boolean;
  
  private final void wakeUp()
  {
    this.jdField_a_of_type_ComTencentSmttWebkitX5JsResult$ResultReceiver.onJsResultComplete(this);
  }
  
  public final void cancel()
  {
    this.jdField_a_of_type_Boolean = false;
    wakeUp();
  }
  
  public final void confirm()
  {
    this.jdField_a_of_type_Boolean = true;
    wakeUp();
  }
  
  public final boolean getResult()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public static abstract interface ResultReceiver
  {
    public abstract void onJsResultComplete(X5JsResult paramX5JsResult);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\X5JsResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */