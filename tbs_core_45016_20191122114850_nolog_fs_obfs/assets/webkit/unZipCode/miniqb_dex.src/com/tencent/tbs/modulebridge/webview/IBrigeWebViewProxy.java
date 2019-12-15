package com.tencent.tbs.modulebridge.webview;

import android.view.View;
import java.util.List;

public abstract interface IBrigeWebViewProxy
{
  public abstract void destroy();
  
  public abstract Object getIX5WebViewBase();
  
  public abstract Object getOpenJsBridge();
  
  public abstract View getView();
  
  public abstract void loadUrl(String paramString);
  
  public abstract void onPause();
  
  public abstract void onResume();
  
  public abstract void reload();
  
  public abstract void setCookie(String paramString, List<String> paramList);
  
  public abstract void setWebViewClient(IBrigeWebViewClientProxy paramIBrigeWebViewClientProxy);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\modulebridge\webview\IBrigeWebViewProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */