package com.tencent.smtt.webkit;

import android.webkit.JavascriptInterface;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;

public class DebugX5SettingsProxy
{
  public DebugX5SettingsProxy(TencentWebViewProxy paramTencentWebViewProxy) {}
  
  @JavascriptInterface
  public boolean getShowLayerBorders()
  {
    return d.a().a();
  }
  
  @JavascriptInterface
  public void setShowLayerBorders(boolean paramBoolean)
  {
    d.a().a(paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\DebugX5SettingsProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */