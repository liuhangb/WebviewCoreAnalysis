package com.tencent.tbs.core.webkit;

import android.net.ParseException;
import android.net.Uri;
import android.net.WebAddress;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.chromium.base.annotations.UsedByReflection;

public class URLUtil
{
  static final String ASSET_BASE = "file:///android_asset/";
  static final String CONTENT_BASE = "content:";
  private static final Pattern CONTENT_DISPOSITION_PATTERN = Pattern.compile("attachment;\\s*filename\\s*=\\s*(\"?)([^\"]*)\\1\\s*$", 2);
  static final String FILE_BASE = "file://";
  private static final String LOGTAG = "webkit";
  static final String PROXY_BASE = "file:///cookieless_proxy/";
  static final String RESOURCE_BASE = "file:///android_res/";
  private static final boolean TRACE = false;
  
  @UsedByReflection("WebCoreProxy.java")
  public static String composeSearchUrl(String paramString1, String paramString2, String paramString3)
  {
    int i = paramString2.indexOf(paramString3);
    if (i < 0) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString2.substring(0, i));
    try
    {
      localStringBuilder.append(URLEncoder.encode(paramString1, "utf-8"));
      localStringBuilder.append(paramString2.substring(i + paramString3.length()));
      return localStringBuilder.toString();
    }
    catch (UnsupportedEncodingException paramString1) {}
    return null;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static byte[] decode(byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    if (paramArrayOfByte.length == 0) {
      return new byte[0];
    }
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];
    int k = 0;
    int m = 0;
    while (k < paramArrayOfByte.length)
    {
      int j = paramArrayOfByte[k];
      int n = k;
      int i = j;
      if (j == 37) {
        if (paramArrayOfByte.length - k > 2)
        {
          int i1 = parseHex(paramArrayOfByte[(k + 1)]);
          n = k + 2;
          i = (byte)(i1 * 16 + parseHex(paramArrayOfByte[n]));
        }
        else
        {
          throw new IllegalArgumentException("Invalid format");
        }
      }
      arrayOfByte[m] = i;
      k = n + 1;
      m += 1;
    }
    paramArrayOfByte = new byte[m];
    System.arraycopy(arrayOfByte, 0, paramArrayOfByte, 0, m);
    return paramArrayOfByte;
  }
  
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
  
  @UsedByReflection("WebCoreProxy.java")
  public static final String guessFileName(String paramString1, String paramString2, String paramString3)
  {
    Object localObject3 = null;
    Object localObject4 = null;
    if (paramString2 != null)
    {
      localObject1 = parseContentDisposition(paramString2);
      paramString2 = (String)localObject1;
      if (localObject1 != null)
      {
        i = ((String)localObject1).lastIndexOf('/') + 1;
        paramString2 = (String)localObject1;
        if (i > 0) {
          paramString2 = ((String)localObject1).substring(i);
        }
      }
    }
    else
    {
      paramString2 = null;
    }
    Object localObject2 = paramString2;
    if (paramString2 == null)
    {
      localObject1 = Uri.decode(paramString1);
      localObject2 = paramString2;
      if (localObject1 != null)
      {
        i = ((String)localObject1).indexOf('?');
        paramString1 = (String)localObject1;
        if (i > 0) {
          paramString1 = ((String)localObject1).substring(0, i);
        }
        localObject2 = paramString2;
        if (!paramString1.endsWith("/"))
        {
          i = paramString1.lastIndexOf('/') + 1;
          localObject2 = paramString2;
          if (i > 0) {
            localObject2 = paramString1.substring(i);
          }
        }
      }
    }
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = "downloadfile";
    }
    int i = ((String)localObject1).indexOf('.');
    if (i < 0)
    {
      paramString2 = (String)localObject4;
      if (paramString3 != null)
      {
        paramString1 = MimeTypeMap.getSingleton().getExtensionFromMimeType(paramString3);
        paramString2 = paramString1;
        if (paramString1 != null)
        {
          paramString2 = new StringBuilder();
          paramString2.append(".");
          paramString2.append(paramString1);
          paramString2 = paramString2.toString();
        }
      }
      paramString1 = paramString2;
      localObject2 = localObject1;
      if (paramString2 == null) {
        if ((paramString3 != null) && (paramString3.toLowerCase(Locale.ROOT).startsWith("text/")))
        {
          if (paramString3.equalsIgnoreCase("text/html"))
          {
            paramString1 = ".html";
            localObject2 = localObject1;
          }
          else
          {
            paramString1 = ".txt";
            localObject2 = localObject1;
          }
        }
        else
        {
          paramString1 = ".bin";
          localObject2 = localObject1;
        }
      }
    }
    else
    {
      paramString1 = (String)localObject3;
      if (paramString3 != null)
      {
        int j = ((String)localObject1).lastIndexOf('.');
        paramString2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(((String)localObject1).substring(j + 1));
        paramString1 = (String)localObject3;
        if (paramString2 != null)
        {
          paramString1 = (String)localObject3;
          if (!paramString2.equalsIgnoreCase(paramString3))
          {
            paramString2 = MimeTypeMap.getSingleton().getExtensionFromMimeType(paramString3);
            paramString1 = paramString2;
            if (paramString2 != null)
            {
              paramString1 = new StringBuilder();
              paramString1.append(".");
              paramString1.append(paramString2);
              paramString1 = paramString1.toString();
            }
          }
        }
      }
      paramString2 = paramString1;
      if (paramString1 == null) {
        paramString2 = ((String)localObject1).substring(i);
      }
      localObject2 = ((String)localObject1).substring(0, i);
      paramString1 = paramString2;
    }
    paramString2 = new StringBuilder();
    paramString2.append((String)localObject2);
    paramString2.append(paramString1);
    return paramString2.toString();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static String guessUrl(String paramString)
  {
    if (paramString.length() == 0) {
      return paramString;
    }
    if (paramString.startsWith("about:")) {
      return paramString;
    }
    if (paramString.startsWith("data:")) {
      return paramString;
    }
    if (paramString.startsWith("file:")) {
      return paramString;
    }
    if (paramString.startsWith("javascript:")) {
      return paramString;
    }
    Object localObject;
    if (paramString.endsWith(".") == true) {
      localObject = paramString.substring(0, paramString.length() - 1);
    } else {
      localObject = paramString;
    }
    try
    {
      localObject = new WebAddress((String)localObject);
      if (((WebAddress)localObject).getHost().indexOf('.') == -1)
      {
        paramString = new StringBuilder();
        paramString.append("www.");
        paramString.append(((WebAddress)localObject).getHost());
        paramString.append(".com");
        ((WebAddress)localObject).setHost(paramString.toString());
      }
      return ((WebAddress)localObject).toString();
    }
    catch (ParseException localParseException) {}
    return paramString;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static boolean isAboutUrl(String paramString)
  {
    return (paramString != null) && (paramString.startsWith("about:"));
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static boolean isAssetUrl(String paramString)
  {
    return (paramString != null) && (paramString.startsWith("file:///android_asset/"));
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static boolean isContentUrl(String paramString)
  {
    return (paramString != null) && (paramString.startsWith("content:"));
  }
  
  @Deprecated
  @UsedByReflection("WebCoreProxy.java")
  public static boolean isCookielessProxyUrl(String paramString)
  {
    return (paramString != null) && (paramString.startsWith("file:///cookieless_proxy/"));
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static boolean isDataUrl(String paramString)
  {
    return (paramString != null) && (paramString.startsWith("data:"));
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static boolean isFileUrl(String paramString)
  {
    return (paramString != null) && (paramString.startsWith("file://")) && (!paramString.startsWith("file:///android_asset/")) && (!paramString.startsWith("file:///cookieless_proxy/"));
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static boolean isHttpUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 6)
      {
        bool1 = bool2;
        if (paramString.substring(0, 7).equalsIgnoreCase("http://")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static boolean isHttpsUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 7)
      {
        bool1 = bool2;
        if (paramString.substring(0, 8).equalsIgnoreCase("https://")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static boolean isJavaScriptUrl(String paramString)
  {
    return (paramString != null) && (paramString.startsWith("javascript:"));
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static boolean isNetworkUrl(String paramString)
  {
    boolean bool = false;
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return false;
      }
      if ((isHttpUrl(paramString)) || (isHttpsUrl(paramString))) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  public static boolean isResourceUrl(String paramString)
  {
    return (paramString != null) && (paramString.startsWith("file:///android_res/"));
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
      if ((isAssetUrl(paramString)) || (isResourceUrl(paramString)) || (isFileUrl(paramString)) || (isAboutUrl(paramString)) || (isHttpUrl(paramString)) || (isHttpsUrl(paramString)) || (isJavaScriptUrl(paramString)) || (isContentUrl(paramString))) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  static String parseContentDisposition(String paramString)
  {
    try
    {
      paramString = CONTENT_DISPOSITION_PATTERN.matcher(paramString);
      if (paramString.find())
      {
        paramString = paramString.group(2);
        return paramString;
      }
    }
    catch (IllegalStateException paramString)
    {
      for (;;) {}
    }
    return null;
  }
  
  private static int parseHex(byte paramByte)
  {
    if ((paramByte >= 48) && (paramByte <= 57)) {
      return paramByte - 48;
    }
    if ((paramByte >= 65) && (paramByte <= 70)) {
      return paramByte - 65 + 10;
    }
    if ((paramByte >= 97) && (paramByte <= 102)) {
      return paramByte - 97 + 10;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Invalid hex char '");
    localStringBuilder.append(paramByte);
    localStringBuilder.append("'");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static String stripAnchor(String paramString)
  {
    int i = paramString.indexOf('#');
    if (i != -1) {
      return paramString.substring(0, i);
    }
    return paramString;
  }
  
  static boolean verifyURLEncoding(String paramString)
  {
    int j = paramString.length();
    if (j == 0) {
      return false;
    }
    int i = paramString.indexOf('%');
    for (;;)
    {
      if ((i < 0) || (i >= j)) {
        break label77;
      }
      if (i < j - 2) {
        i += 1;
      }
      label77:
      try
      {
        parseHex((byte)paramString.charAt(i));
        i += 1;
        parseHex((byte)paramString.charAt(i));
        i = paramString.indexOf('%', i + 1);
      }
      catch (IllegalArgumentException paramString) {}
    }
    return false;
    return true;
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\URLUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */