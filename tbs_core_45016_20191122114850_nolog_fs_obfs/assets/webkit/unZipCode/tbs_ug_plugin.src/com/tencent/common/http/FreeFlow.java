package com.tencent.common.http;

import android.webkit.ValueCallback;
import com.tencent.common.manifest.AppManifest;
import com.tencent.common.manifest.annotation.Extension;

public class FreeFlow
{
  private static IFreeFlowProvider a;
  
  static IFreeFlowProvider a()
  {
    if (a == null) {
      try
      {
        if (a == null)
        {
          a = (IFreeFlowProvider)AppManifest.getInstance().queryExtension(IFreeFlowProvider.class, null);
          if (a == null) {
            a = new a();
          }
        }
      }
      finally {}
    }
    return a;
  }
  
  public static void convertUrl(String paramString, ValueCallback<String> paramValueCallback)
  {
    a().convertUrl(paramString, paramValueCallback);
  }
  
  public static void convertUrlBatch(String[] paramArrayOfString, ValueCallback<String[]> paramValueCallback)
  {
    a().convertUrlBatch(paramArrayOfString, paramValueCallback);
  }
  
  public static String convertUrlSync(String paramString)
  {
    return a().convertUrlSync(paramString);
  }
  
  public static boolean isFreeFlowUser()
  {
    return a().isFreeFlowUser();
  }
  
  public static void onReceiveProxyStatusCode(int paramInt)
  {
    a().onReceiveProxyStatusCode(paramInt);
  }
  
  @Extension
  public static abstract interface IFreeFlowProvider
  {
    public abstract void convertUrl(String paramString, ValueCallback<String> paramValueCallback);
    
    public abstract void convertUrlBatch(String[] paramArrayOfString, ValueCallback<String[]> paramValueCallback);
    
    public abstract String convertUrlSync(String paramString);
    
    public abstract boolean isFreeFlowUser();
    
    public abstract void onReceiveProxyStatusCode(int paramInt);
  }
  
  static class a
    implements FreeFlow.IFreeFlowProvider
  {
    public void convertUrl(String paramString, ValueCallback<String> paramValueCallback)
    {
      if (paramValueCallback != null) {
        paramValueCallback.onReceiveValue(paramString);
      }
    }
    
    public void convertUrlBatch(String[] paramArrayOfString, ValueCallback<String[]> paramValueCallback)
    {
      if (paramValueCallback != null) {
        paramValueCallback.onReceiveValue(paramArrayOfString);
      }
    }
    
    public String convertUrlSync(String paramString)
    {
      return paramString;
    }
    
    public boolean isFreeFlowUser()
    {
      return false;
    }
    
    public void onReceiveProxyStatusCode(int paramInt) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\FreeFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */