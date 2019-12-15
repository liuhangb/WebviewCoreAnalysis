package com.tencent.mtt.base.utils;

import android.net.Uri;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.utils.UrlUtils;

public class DLQBUrlUtils
{
  public static final int CHECK_JSAPI_DOMAIN_STATUS_FAIL = 0;
  public static final int CHECK_JSAPI_DOMAIN_STATUS_NOAPI = -1;
  public static final int CHECK_JSAPI_DOMAIN_STATUS_SUCC = 1;
  
  public static String getDownloadFileName(String paramString)
  {
    if (!StringUtils.isEmpty(paramString))
    {
      if (!paramString.startsWith("http://")) {
        return null;
      }
      String str = Uri.decode(paramString);
      if (!StringUtils.isEmpty(paramString))
      {
        int i = str.indexOf('?');
        paramString = str;
        if (i > 0) {
          paramString = str.substring(0, i);
        }
        if (!paramString.endsWith("/"))
        {
          i = paramString.lastIndexOf('/') + 1;
          if (i > 0)
          {
            paramString = paramString.substring(i);
            break label83;
          }
        }
      }
      paramString = null;
      label83:
      if (StringUtils.isEmpty(paramString)) {
        return null;
      }
      if (DLMttFileUtils.isValidExtensionFileName(paramString)) {
        return paramString;
      }
      return null;
    }
    return null;
  }
  
  public static boolean isMobileQQUrl(String paramString)
  {
    return paramString.toLowerCase().trim().contains("mqqapi://");
  }
  
  public static String resolveBase(String paramString1, String paramString2)
  {
    if (StringUtils.isEmpty(paramString2)) {
      return paramString1;
    }
    if (paramString2.startsWith("#")) {
      return paramString2;
    }
    if (isMobileQQUrl(paramString2)) {
      return paramString2;
    }
    return UrlUtils.resolveBase(paramString1, paramString2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DLQBUrlUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */