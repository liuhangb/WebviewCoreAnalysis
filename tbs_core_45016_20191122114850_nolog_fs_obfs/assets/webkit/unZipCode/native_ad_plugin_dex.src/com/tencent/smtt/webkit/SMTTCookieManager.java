package com.tencent.smtt.webkit;

import android.webview.chromium.tencent.TencentCookieManagerAdapter;
import com.tencent.smtt.net.m;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.chromium.base.annotations.UsedByReflection;

public final class SMTTCookieManager
{
  static String SET_COOKIE = "Set-Cookie";
  static String SET_COOKIE2 = "Set-Cookie2";
  static String SET_QCOOKIE = "Set-QCookie";
  private static SMTTCookieManager sPrivateBrowsingRef;
  private static SMTTCookieManager sRef;
  private TencentCookieManagerAdapter mCookiemanager = null;
  
  private m NewURI(m paramm)
  {
    if (!paramm.b.endsWith(".qqbrowser"))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramm.b);
      localStringBuilder.append(".qqbrowser");
      paramm.b = localStringBuilder.toString();
    }
    return paramm;
  }
  
  private String NewURL(String paramString)
  {
    return NewURI(new m(paramString)).toString();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static SMTTCookieManager getInstance()
  {
    try
    {
      if (sRef == null) {
        sRef = new SMTTCookieManager();
      }
      sRef.mCookiemanager = TencentCookieManagerAdapter.getInstance(false);
      SMTTCookieManager localSMTTCookieManager = sRef;
      return localSMTTCookieManager;
    }
    finally {}
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static SMTTCookieManager getInstance(boolean paramBoolean)
  {
    if (paramBoolean) {}
    try
    {
      localSMTTCookieManager = getPrivateBrowsingInstance();
      return localSMTTCookieManager;
    }
    finally {}
    SMTTCookieManager localSMTTCookieManager = getInstance();
    return localSMTTCookieManager;
  }
  
  private static SMTTCookieManager getPrivateBrowsingInstance()
  {
    try
    {
      if (sPrivateBrowsingRef == null) {
        sPrivateBrowsingRef = new SMTTCookieManager();
      }
      sPrivateBrowsingRef.mCookiemanager = TencentCookieManagerAdapter.getInstance(true);
      SMTTCookieManager localSMTTCookieManager = sPrivateBrowsingRef;
      return localSMTTCookieManager;
    }
    finally {}
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void appendDomain(URL paramURL)
  {
    this.mCookiemanager.appendDomain(paramURL.toString());
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public String getCookie(String paramString)
  {
    return this.mCookiemanager.getCookie(paramString);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public String getQCookie(String paramString)
  {
    return this.mCookiemanager.getCookie(NewURL(paramString));
  }
  
  public void removeAllCookie()
  {
    this.mCookiemanager.removeAllCookie();
  }
  
  public void removeExpiredCookie()
  {
    this.mCookiemanager.removeExpiredCookie();
  }
  
  public void removeSessionCookie()
  {
    this.mCookiemanager.removeSessionCookie();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setCookie(m paramm, String paramString)
  {
    try
    {
      this.mCookiemanager.setCookie(paramm, paramString);
      return;
    }
    finally
    {
      paramm = finally;
      throw paramm;
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setCookie(String paramString1, String paramString2)
  {
    this.mCookiemanager.setCookie(paramString1, paramString2);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setCookie(URL paramURL, Map<String, List<String>> paramMap)
  {
    if ((paramURL != null) && (paramMap != null))
    {
      Iterator localIterator1 = paramMap.keySet().iterator();
      while (localIterator1.hasNext())
      {
        String str1 = (String)localIterator1.next();
        if ((str1 != null) && ((str1.equalsIgnoreCase(SET_COOKIE)) || (str1.equalsIgnoreCase(SET_COOKIE2)) || (str1.equalsIgnoreCase(SET_QCOOKIE))))
        {
          Iterator localIterator2 = ((List)paramMap.get(str1)).iterator();
          while (localIterator2.hasNext())
          {
            String str2 = (String)localIterator2.next();
            if (str1.equalsIgnoreCase(SET_QCOOKIE)) {
              setQCookie(paramURL.toString(), str2);
            } else {
              setCookie(paramURL.toString(), str2);
            }
          }
        }
      }
      return;
    }
    throw new IllegalArgumentException("arg is null");
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setQCookie(m paramm, String paramString)
  {
    try
    {
      this.mCookiemanager.setCookie(NewURI(paramm), paramString);
      return;
    }
    finally
    {
      paramm = finally;
      throw paramm;
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setQCookie(String paramString1, String paramString2)
  {
    this.mCookiemanager.setCookie(NewURL(paramString1), paramString2);
  }
  
  public void sync()
  {
    this.mCookiemanager.sync();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void syncImmediately()
  {
    this.mCookiemanager.syncImmediately();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\SMTTCookieManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */