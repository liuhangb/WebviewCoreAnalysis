package com.tencent.common.http;

import java.net.URL;
import java.util.List;
import java.util.Map;

public abstract interface IHttpCookieManager
{
  public abstract String getCookie(String paramString);
  
  public abstract String getQCookie(String paramString);
  
  public abstract boolean isQQDomain(URL paramURL);
  
  public abstract void setCookie(URL paramURL, Map<String, List<String>> paramMap);
  
  public abstract void setQCookie(String paramString1, String paramString2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\IHttpCookieManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */