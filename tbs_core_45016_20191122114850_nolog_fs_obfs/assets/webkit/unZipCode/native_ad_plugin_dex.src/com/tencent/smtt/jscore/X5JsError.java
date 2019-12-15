package com.tencent.smtt.jscore;

import com.tencent.smtt.export.external.jscore.interfaces.IX5JsError;

public class X5JsError
  implements IX5JsError
{
  private final String mMessage;
  private final String mStack;
  
  public X5JsError(String paramString1, String paramString2)
  {
    this.mMessage = paramString1;
    this.mStack = paramString2;
  }
  
  public String getMessage()
  {
    return this.mMessage;
  }
  
  public String getStack()
  {
    return this.mStack;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jscore\X5JsError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */