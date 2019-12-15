package com.tencent.tbs.core.webkit.tencent;

import com.tencent.tbs.core.webkit.URLUtil;
import org.chromium.base.annotations.UsedByReflection;

public final class TencentURLUtil
  extends URLUtil
{
  public static String getHost(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      int i = paramString.indexOf("://");
      if (i != -1) {
        i += 3;
      } else {
        i = 0;
      }
      int j = paramString.indexOf('/', i);
      if (j != -1)
      {
        paramString = paramString.substring(i, j);
      }
      else
      {
        j = paramString.indexOf('?', i);
        if (j != -1) {
          paramString = paramString.substring(i, j);
        } else {
          paramString = paramString.substring(i);
        }
      }
      i = paramString.indexOf(":");
      String str = paramString;
      if (i >= 0) {
        str = paramString.substring(0, i);
      }
      return str;
    }
    return null;
  }
  
  public static String getPath(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      int i = paramString.indexOf("://");
      if (i != -1) {
        i += 3;
      } else {
        i = 0;
      }
      if (paramString.indexOf('/', i) != -1) {
        return paramString.substring(0, paramString.lastIndexOf("/") + 1);
      }
      i = paramString.indexOf('?', i);
      String str = paramString;
      if (i != -1) {
        str = paramString.substring(0, i);
      }
      return str;
    }
    return null;
  }
  
  public static String getPathWithoutScheme(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      int i = 0;
      int j = paramString.indexOf("://");
      if (j != -1) {
        i = j + 3;
      }
      if (paramString.indexOf('/', i) != -1) {
        return paramString.substring(i, paramString.lastIndexOf("/") + 1);
      }
      j = paramString.indexOf('?', i);
      if (j != -1) {
        return paramString.substring(i, j);
      }
      return paramString.substring(i);
    }
    return null;
  }
  
  public static String getUrlWithoutQuery(String paramString)
  {
    int i = paramString.indexOf('?');
    if (i == -1) {
      return paramString;
    }
    return paramString.substring(0, i);
  }
  
  public static boolean isTencentUrl(String paramString)
  {
    return (paramString != null) && (paramString.startsWith("tencent://"));
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static boolean isValidUrl(String paramString)
  {
    boolean bool = false;
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return false;
      }
      if ((isAssetUrl(paramString)) || (isResourceUrl(paramString)) || (isFileUrl(paramString)) || (isAboutUrl(paramString)) || (isHttpUrl(paramString)) || (isHttpsUrl(paramString)) || (isJavaScriptUrl(paramString)) || (isContentUrl(paramString)) || (isTencentUrl(paramString))) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentURLUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */