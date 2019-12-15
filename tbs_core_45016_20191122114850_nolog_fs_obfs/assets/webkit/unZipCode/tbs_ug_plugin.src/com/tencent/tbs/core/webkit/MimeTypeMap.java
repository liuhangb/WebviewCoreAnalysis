package com.tencent.tbs.core.webkit;

import android.text.TextUtils;
import java.util.regex.Pattern;
import libcore.net.MimeUtils;

public class MimeTypeMap
{
  private static final MimeTypeMap sMimeTypeMap = new MimeTypeMap();
  
  public static String getFileExtensionFromUrl(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      int i = paramString.lastIndexOf('#');
      String str = paramString;
      if (i > 0) {
        str = paramString.substring(0, i);
      }
      i = str.lastIndexOf('?');
      paramString = str;
      if (i > 0) {
        paramString = str.substring(0, i);
      }
      i = paramString.lastIndexOf('/');
      str = paramString;
      if (i >= 0) {
        str = paramString.substring(i + 1);
      }
      if ((!str.isEmpty()) && (Pattern.matches("[a-zA-Z_0-9\\.\\-\\(\\)\\%]+", str)))
      {
        i = str.lastIndexOf('.');
        if (i >= 0) {
          return str.substring(i + 1);
        }
      }
    }
    return "";
  }
  
  public static MimeTypeMap getSingleton()
  {
    return sMimeTypeMap;
  }
  
  private static String mimeTypeFromExtension(String paramString)
  {
    return MimeUtils.guessMimeTypeFromExtension(paramString);
  }
  
  public String getExtensionFromMimeType(String paramString)
  {
    return MimeUtils.guessExtensionFromMimeType(paramString);
  }
  
  public String getMimeTypeFromExtension(String paramString)
  {
    return MimeUtils.guessMimeTypeFromExtension(paramString);
  }
  
  public boolean hasExtension(String paramString)
  {
    return MimeUtils.hasExtension(paramString);
  }
  
  public boolean hasMimeType(String paramString)
  {
    return MimeUtils.hasMimeType(paramString);
  }
  
  String remapGenericMimeType(String paramString1, String paramString2, String paramString3)
  {
    if ((!"text/plain".equals(paramString1)) && (!"application/octet-stream".equals(paramString1)))
    {
      if ("text/vnd.wap.wml".equals(paramString1)) {
        return "text/plain";
      }
      paramString2 = paramString1;
      if ("application/vnd.wap.xhtml+xml".equals(paramString1)) {
        return "application/xhtml+xml";
      }
    }
    else
    {
      if (paramString3 != null) {
        paramString3 = URLUtil.parseContentDisposition(paramString3);
      } else {
        paramString3 = null;
      }
      if (paramString3 != null) {
        paramString2 = paramString3;
      }
      paramString3 = getMimeTypeFromExtension(getFileExtensionFromUrl(paramString2));
      paramString2 = paramString1;
      if (paramString3 != null) {
        paramString2 = paramString3;
      }
    }
    return paramString2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\MimeTypeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */