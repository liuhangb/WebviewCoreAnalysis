package com.tencent.tbs.core;

import com.tencent.smtt.export.external.interfaces.IX5CoreUrlUtil;
import com.tencent.tbs.core.webkit.tencent.TencentURLUtil;

public class X5CoreUrlUtil
  implements IX5CoreUrlUtil
{
  private static X5CoreUrlUtil sInstance;
  
  public static X5CoreUrlUtil getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new X5CoreUrlUtil();
      }
      X5CoreUrlUtil localX5CoreUrlUtil = sInstance;
      return localX5CoreUrlUtil;
    }
    finally {}
  }
  
  public String composeSearchUrl(String paramString1, String paramString2, String paramString3)
  {
    return TencentURLUtil.composeSearchUrl(paramString1, paramString2, paramString3);
  }
  
  public byte[] decode(byte[] paramArrayOfByte)
  {
    return TencentURLUtil.decode(paramArrayOfByte);
  }
  
  public String guessFileName(String paramString1, String paramString2, String paramString3)
  {
    return TencentURLUtil.guessFileName(paramString1, paramString2, paramString3);
  }
  
  public String guessUrl(String paramString)
  {
    return TencentURLUtil.guessUrl(paramString);
  }
  
  public boolean isAboutUrl(String paramString)
  {
    return TencentURLUtil.isAboutUrl(paramString);
  }
  
  public boolean isAssetUrl(String paramString)
  {
    return TencentURLUtil.isAssetUrl(paramString);
  }
  
  public boolean isContentUrl(String paramString)
  {
    return TencentURLUtil.isContentUrl(paramString);
  }
  
  public boolean isCookielessProxyUrl(String paramString)
  {
    return TencentURLUtil.isCookielessProxyUrl(paramString);
  }
  
  public boolean isDataUrl(String paramString)
  {
    return TencentURLUtil.isDataUrl(paramString);
  }
  
  public boolean isFileUrl(String paramString)
  {
    return TencentURLUtil.isFileUrl(paramString);
  }
  
  public boolean isHttpUrl(String paramString)
  {
    return TencentURLUtil.isHttpUrl(paramString);
  }
  
  public boolean isHttpsUrl(String paramString)
  {
    return TencentURLUtil.isHttpsUrl(paramString);
  }
  
  public boolean isJavaScriptUrl(String paramString)
  {
    return TencentURLUtil.isJavaScriptUrl(paramString);
  }
  
  public boolean isNetworkUrl(String paramString)
  {
    return TencentURLUtil.isNetworkUrl(paramString);
  }
  
  public boolean isValidUrl(String paramString)
  {
    return TencentURLUtil.isValidUrl(paramString);
  }
  
  public String stripAnchor(String paramString)
  {
    return TencentURLUtil.stripAnchor(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\X5CoreUrlUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */