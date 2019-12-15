package com.tencent.mtt.video.browser.export.player;

import android.view.View;

public abstract interface IVideoWebViewProxy
{
  public abstract void addJavascriptInterface(Object paramObject, String paramString);
  
  public abstract void destroy();
  
  public abstract String getUrl();
  
  public abstract View getView();
  
  public abstract View getView(int paramInt);
  
  public abstract void loadUrl(String paramString);
  
  public abstract void setVideoWebViewClient(IVideoWebViewClient paramIVideoWebViewClient);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\IVideoWebViewProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */