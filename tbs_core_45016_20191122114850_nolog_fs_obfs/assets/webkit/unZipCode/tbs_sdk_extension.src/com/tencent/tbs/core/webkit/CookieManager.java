package com.tencent.tbs.core.webkit;

import android.net.WebAddress;
import org.chromium.base.annotations.UsedByReflection;

public abstract class CookieManager
{
  public static boolean allowFileSchemeCookies()
  {
    return getInstance().allowFileSchemeCookiesImpl();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static CookieManager getInstance()
  {
    try
    {
      CookieManager localCookieManager = WebViewFactory.getProvider().getCookieManager();
      return localCookieManager;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static void setAcceptFileSchemeCookies(boolean paramBoolean)
  {
    getInstance().setAcceptFileSchemeCookiesImpl(paramBoolean);
  }
  
  public abstract boolean acceptCookie();
  
  public abstract boolean acceptThirdPartyCookies(WebView paramWebView);
  
  protected abstract boolean allowFileSchemeCookiesImpl();
  
  protected Object clone()
    throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException("doesn't implement Cloneable");
  }
  
  public abstract void flush();
  
  public String getCookie(WebAddress paramWebAddress)
  {
    try
    {
      paramWebAddress = getCookie(paramWebAddress.toString());
      return paramWebAddress;
    }
    finally
    {
      paramWebAddress = finally;
      throw paramWebAddress;
    }
  }
  
  public abstract String getCookie(String paramString);
  
  public abstract String getCookie(String paramString, boolean paramBoolean);
  
  public abstract boolean hasCookies();
  
  public abstract boolean hasCookies(boolean paramBoolean);
  
  @Deprecated
  public abstract void removeAllCookie();
  
  public abstract void removeAllCookies(ValueCallback<Boolean> paramValueCallback);
  
  @Deprecated
  public abstract void removeExpiredCookie();
  
  public abstract void removeSessionCookie();
  
  public abstract void removeSessionCookies(ValueCallback<Boolean> paramValueCallback);
  
  public abstract void setAcceptCookie(boolean paramBoolean);
  
  protected abstract void setAcceptFileSchemeCookiesImpl(boolean paramBoolean);
  
  public abstract void setAcceptThirdPartyCookies(WebView paramWebView, boolean paramBoolean);
  
  public abstract void setCookie(String paramString1, String paramString2);
  
  public abstract void setCookie(String paramString1, String paramString2, ValueCallback<Boolean> paramValueCallback);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\CookieManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */