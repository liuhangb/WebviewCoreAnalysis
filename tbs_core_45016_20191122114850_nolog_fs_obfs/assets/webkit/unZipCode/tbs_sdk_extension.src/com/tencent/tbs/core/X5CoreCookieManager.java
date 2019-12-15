package com.tencent.tbs.core;

import android.content.Context;
import android.webkit.ValueCallback;
import android.webview.chromium.tencent.TencentCookieManagerAdapter;
import com.tencent.smtt.export.external.interfaces.IX5CoreCookieManager;
import com.tencent.smtt.webkit.SMTTCookieManager;
import com.tencent.tbs.core.webkit.CookieSyncManager;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class X5CoreCookieManager
  implements IX5CoreCookieManager
{
  private static X5CoreCookieManager sInstance;
  
  public static X5CoreCookieManager getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new X5CoreCookieManager();
      }
      X5CoreCookieManager localX5CoreCookieManager = sInstance;
      return localX5CoreCookieManager;
    }
    finally {}
  }
  
  public boolean acceptCookie()
  {
    return TencentCookieManagerAdapter.getInstance(false).acceptCookie();
  }
  
  public boolean acceptThirdPartyCookies(Object paramObject)
  {
    return TencentCookieManagerAdapter.getInstance(false).acceptThirdPartyCookies(paramObject);
  }
  
  public void appendDomain(URL paramURL)
  {
    SMTTCookieManager.getInstance().appendDomain(paramURL);
  }
  
  public void flush()
  {
    TencentCookieManagerAdapter.getInstance(false).flush();
  }
  
  public String getCookie(String paramString)
  {
    return SMTTCookieManager.getInstance().getCookie(paramString);
  }
  
  public String getCookie(String paramString, boolean paramBoolean)
  {
    return SMTTCookieManager.getInstance(paramBoolean).getCookie(paramString);
  }
  
  public String getQCookie(String paramString)
  {
    return SMTTCookieManager.getInstance().getQCookie(paramString);
  }
  
  public boolean hasCookies()
  {
    return TencentCookieManagerAdapter.getInstance(false).hasCookies();
  }
  
  public void removeAllCookie()
  {
    TencentCookieManagerAdapter.getInstance(false).removeAllCookie();
  }
  
  public void removeAllCookies(ValueCallback<Boolean> paramValueCallback)
  {
    TencentCookieManagerAdapter.getInstance(false).removeAllCookies(paramValueCallback);
  }
  
  public void removeExpiredCookie()
  {
    TencentCookieManagerAdapter.getInstance(false).removeExpiredCookie();
  }
  
  public void removeSessionCookie()
  {
    TencentCookieManagerAdapter.getInstance(false).removeSessionCookie();
  }
  
  public void removeSessionCookies(ValueCallback<Boolean> paramValueCallback)
  {
    TencentCookieManagerAdapter.getInstance(false).removeSessionCookies(paramValueCallback);
  }
  
  public void setAcceptCookie(boolean paramBoolean)
  {
    TencentCookieManagerAdapter.getInstance(false).setAcceptCookie(paramBoolean);
  }
  
  public void setAcceptThirdPartyCookies(Object paramObject, boolean paramBoolean)
  {
    TencentCookieManagerAdapter.getInstance(false).setAcceptThirdPartyCookies(paramObject, paramBoolean);
  }
  
  public void setCookie(String paramString1, String paramString2)
  {
    TencentCookieManagerAdapter.getInstance(false).setCookie(paramString1, paramString2);
  }
  
  public void setCookie(String paramString1, String paramString2, ValueCallback<Boolean> paramValueCallback)
  {
    TencentCookieManagerAdapter.getInstance(false).setCookie(paramString1, paramString2, paramValueCallback);
  }
  
  public void setCookie(URL paramURL, Map<String, List<String>> paramMap)
  {
    SMTTCookieManager.getInstance().setCookie(paramURL, paramMap);
  }
  
  public boolean setCookies(Map<String, String[]> paramMap)
  {
    if ((paramMap instanceof HashMap))
    {
      TencentCookieManagerAdapter.getInstance(false).setCookies((HashMap)paramMap);
      return true;
    }
    return false;
  }
  
  public void setQCookie(String paramString1, String paramString2)
  {
    SMTTCookieManager.getInstance().setQCookie(paramString1, paramString2);
  }
  
  public void syncImmediately()
  {
    SMTTCookieManager.getInstance().syncImmediately();
  }
  
  public void syncManagerCreateInstance(Context paramContext)
  {
    CookieSyncManager.createInstance(paramContext);
  }
  
  public void syncManagerStartSync()
  {
    CookieSyncManager.getInstance().startSync();
  }
  
  public void syncManagerStopSync()
  {
    CookieSyncManager.getInstance().stopSync();
  }
  
  public void syncManagerSync()
  {
    CookieSyncManager.getInstance().sync();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\X5CoreCookieManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */