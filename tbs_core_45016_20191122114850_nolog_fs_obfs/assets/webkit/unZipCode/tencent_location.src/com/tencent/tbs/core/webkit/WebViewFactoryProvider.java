package com.tencent.tbs.core.webkit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public abstract interface WebViewFactoryProvider
{
  public abstract WebViewProvider createWebView(WebView paramWebView, WebView.PrivateAccess paramPrivateAccess);
  
  public abstract CookieManager getCookieManager();
  
  public abstract GeolocationPermissions getGeolocationPermissions();
  
  public abstract ServiceWorkerController getServiceWorkerController();
  
  public abstract Statics getStatics();
  
  public abstract TokenBindingService getTokenBindingService();
  
  public abstract WebIconDatabase getWebIconDatabase();
  
  public abstract WebStorage getWebStorage();
  
  public abstract WebViewDatabase getWebViewDatabase(Context paramContext);
  
  public static abstract interface Statics
  {
    public abstract void clearClientCertPreferences(Runnable paramRunnable);
    
    public abstract void enableSlowWholeDocumentDraw();
    
    public abstract String findAddress(String paramString);
    
    public abstract void freeMemoryForTests();
    
    public abstract String getDefaultUserAgent(Context paramContext);
    
    public abstract Uri[] parseFileChooserResult(int paramInt, Intent paramIntent);
    
    public abstract void setWebContentsDebuggingEnabled(boolean paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\WebViewFactoryProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */