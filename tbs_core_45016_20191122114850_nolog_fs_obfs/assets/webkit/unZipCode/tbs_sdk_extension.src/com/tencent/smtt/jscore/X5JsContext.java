package com.tencent.smtt.jscore;

import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsContext;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsError;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsValue;
import java.net.URL;

public class X5JsContext
  implements IX5JsContext
{
  private static final String CLAZZ_NAME = "X5JsContext";
  private ValueCallback<IX5JsError> mExceptionHandler;
  private final X5JsContextImpl mJsContext;
  
  public X5JsContext(X5JsVirtualMachine paramX5JsVirtualMachine, X5JsContextImpl paramX5JsContextImpl)
  {
    this.mJsContext = paramX5JsContextImpl;
  }
  
  public void addJavascriptInterface(Object paramObject, String paramString)
  {
    this.mJsContext.addJavascriptInterface(paramObject, paramString);
  }
  
  public void destroy()
  {
    this.mJsContext.destroy();
  }
  
  public void evaluateJavascript(String paramString, ValueCallback<String> paramValueCallback, URL paramURL)
  {
    this.mJsContext.evaluateJavascript(paramString, paramValueCallback, paramURL);
  }
  
  public IX5JsValue evaluateScript(String paramString, URL paramURL)
  {
    return this.mJsContext.evaluateScript(paramString, paramURL);
  }
  
  public void evaluateScriptAsync(String paramString, ValueCallback<IX5JsValue> paramValueCallback, URL paramURL)
  {
    this.mJsContext.evaluateScriptAsync(paramString, paramValueCallback, paramURL);
  }
  
  public byte[] getNativeBuffer(int paramInt)
  {
    return this.mJsContext.getNativeBuffer(paramInt);
  }
  
  public int getNativeBufferId()
  {
    return this.mJsContext.getNativeBufferId();
  }
  
  public void removeJavascriptInterface(String paramString)
  {
    this.mJsContext.removeJavascriptInterface(paramString);
  }
  
  public void setExceptionHandler(ValueCallback<IX5JsError> paramValueCallback)
  {
    this.mExceptionHandler = paramValueCallback;
    if (paramValueCallback == null)
    {
      this.mJsContext.setExceptionHandler(null);
      return;
    }
    this.mJsContext.setExceptionHandler(new X5JsContextImpl.ExceptionHandler()
    {
      public void handleException(X5JsContextImpl paramAnonymousX5JsContextImpl, String paramAnonymousString1, String paramAnonymousString2)
      {
        X5JsContext.this.mExceptionHandler.onReceiveValue(new X5JsError(paramAnonymousString1, paramAnonymousString2));
      }
    });
  }
  
  public void setName(String paramString)
  {
    this.mJsContext.setName(paramString);
  }
  
  public int setNativeBuffer(int paramInt, byte[] paramArrayOfByte)
  {
    return this.mJsContext.setNativeBuffer(paramInt, paramArrayOfByte);
  }
  
  public void setPerContextData(Object paramObject)
  {
    this.mJsContext.setPerContextData(paramObject);
  }
  
  public void stealValueFromOtherCtx(String paramString1, IX5JsContext paramIX5JsContext, String paramString2)
  {
    this.mJsContext.stealValueFromOtherCtx(paramString1, ((X5JsContext)paramIX5JsContext).mJsContext, paramString2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jscore\X5JsContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */