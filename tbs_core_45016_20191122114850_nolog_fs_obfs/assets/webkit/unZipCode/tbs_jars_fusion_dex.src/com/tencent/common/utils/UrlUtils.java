package com.tencent.common.utils;

import android.net.Uri;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.http.MimeTypeMap;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class UrlUtils
{
  public static String[] SUFFIX;
  public static final String URL_CODING = "utf-8";
  static char jdField_a_of_type_Char = '-';
  static int jdField_a_of_type_Int = 0;
  private static Object jdField_a_of_type_JavaLangObject = new Object();
  private static Pattern jdField_a_of_type_JavaUtilRegexPattern = null;
  private static boolean jdField_a_of_type_Boolean = false;
  static int jdField_b_of_type_Int;
  private static Pattern jdField_b_of_type_JavaUtilRegexPattern = null;
  static int jdField_c_of_type_Int;
  private static Pattern jdField_c_of_type_JavaUtilRegexPattern = null;
  static int jdField_d_of_type_Int;
  private static Pattern jdField_d_of_type_JavaUtilRegexPattern = null;
  static int jdField_e_of_type_Int;
  private static Pattern jdField_e_of_type_JavaUtilRegexPattern = null;
  static int jdField_f_of_type_Int;
  private static Pattern jdField_f_of_type_JavaUtilRegexPattern = null;
  static int jdField_g_of_type_Int;
  private static Pattern jdField_g_of_type_JavaUtilRegexPattern = null;
  private static Pattern h = null;
  private static Pattern i = null;
  private static Pattern j = null;
  private static Pattern k = null;
  private static Pattern l = null;
  
  static
  {
    SUFFIX = new String[] { "gd", "com", "cn", "ac", "edu", "gov", "mil", "arpa", "net", "org", "biz", "info", "pro", "name", "coop", "mobi", "int", "us", "travel", "xxx", "idv", "co", "so", "tv", "hk", "asia", "me", "cc", "tw" };
    jdField_a_of_type_Int = 1;
    jdField_b_of_type_Int = 26;
    jdField_c_of_type_Int = 36;
    jdField_d_of_type_Int = 128;
    jdField_e_of_type_Int = 72;
    jdField_f_of_type_Int = 700;
    jdField_g_of_type_Int = 38;
  }
  
  public static Pattern CONTENT_DISPOSITION_PATTERN()
  {
    a();
    return i;
  }
  
  public static Pattern INLINE_CONTENT_DISPOSITION_PATTERN()
  {
    a();
    return k;
  }
  
  public static Pattern OTHER_CONTENT_DISPOSITION_PATTERN()
  {
    a();
    return l;
  }
  
  public static Pattern USER_CONTENT_DISPOSITION_PATTERN()
  {
    a();
    return j;
  }
  
  public static Pattern VALID_GAME_URL()
  {
    a();
    return h;
  }
  
  public static Pattern VALID_IPV6_ADDRESS()
  {
    a();
    return jdField_g_of_type_JavaUtilRegexPattern;
  }
  
  public static Pattern VALID_IP_ADDRESS()
  {
    a();
    return jdField_f_of_type_JavaUtilRegexPattern;
  }
  
  public static Pattern VALID_LOCAL_URL()
  {
    a();
    return jdField_b_of_type_JavaUtilRegexPattern;
  }
  
  public static Pattern VALID_MTT_URL()
  {
    a();
    return jdField_c_of_type_JavaUtilRegexPattern;
  }
  
  public static Pattern VALID_PAY_URL()
  {
    a();
    return jdField_e_of_type_JavaUtilRegexPattern;
  }
  
  public static Pattern VALID_QB_URL()
  {
    a();
    return jdField_d_of_type_JavaUtilRegexPattern;
  }
  
  public static Pattern VALID_URL()
  {
    a();
    return jdField_a_of_type_JavaUtilRegexPattern;
  }
  
  private static long a(String paramString)
  {
    paramString = paramString.split("\\.");
    return Integer.parseInt(paramString[0]) * 256L * 256L * 256L + Integer.parseInt(paramString[1]) * 256L * 256L + Integer.parseInt(paramString[2]) * 256L + Integer.parseInt(paramString[3]);
  }
  
  private static String a(Pattern paramPattern, String paramString)
  {
    for (;;)
    {
      int m;
      try
      {
        Object localObject1 = paramPattern.matcher(paramString);
        if (((Matcher)localObject1).find())
        {
          paramPattern = ((Matcher)localObject1).group(2);
          boolean bool = TextUtils.isEmpty(paramPattern);
          int n = 0;
          paramString = paramPattern;
          if (!bool)
          {
            paramString = paramPattern;
            if (paramPattern.endsWith(";")) {
              paramString = paramPattern.substring(0, paramPattern.length() - 1);
            }
          }
          if (((Matcher)localObject1).groupCount() < 3) {
            break label484;
          }
          str1 = ((Matcher)localObject1).group(3);
          break label487;
          localObject1 = "";
          paramPattern = paramString.replace("=?UTF8?B?", "=?UTF-8?B?");
          m = 0;
          int i1 = paramPattern.indexOf("=?UTF-8?B?");
          if (i1 == -1) {
            break label493;
          }
          Object localObject2 = paramPattern;
          Pattern localPattern = paramPattern;
          try
          {
            i1 = paramPattern.indexOf("=?UTF-8?B?");
            localObject2 = paramPattern;
            localPattern = paramPattern;
            int i2 = paramPattern.indexOf("?=");
            if ((i1 == -1) || (i2 == -1)) {
              break label493;
            }
            localObject2 = paramPattern;
            localPattern = paramPattern;
            String str2 = paramPattern.substring(i1 + 10, i2);
            localObject2 = paramPattern;
            localPattern = paramPattern;
            paramPattern = paramPattern.substring(i2 + 2);
            localObject2 = paramPattern;
            localPattern = paramPattern;
            str2 = new String(Base64.decode(str2));
            localObject2 = paramPattern;
            localPattern = paramPattern;
            StringBuilder localStringBuilder = new StringBuilder();
            localObject2 = paramPattern;
            localPattern = paramPattern;
            localStringBuilder.append((String)localObject1);
            localObject2 = paramPattern;
            localPattern = paramPattern;
            localStringBuilder.append(str2);
            localObject2 = paramPattern;
            localPattern = paramPattern;
            str2 = localStringBuilder.toString();
            m = 1;
            localObject1 = str2;
          }
          catch (ArrayIndexOutOfBoundsException paramPattern)
          {
            paramPattern.printStackTrace();
            paramPattern = (Pattern)localObject2;
          }
          catch (UnsupportedEncodingException paramPattern)
          {
            paramPattern.printStackTrace();
            paramPattern = localPattern;
          }
          continue;
          if (a(paramString))
          {
            localObject2 = paramString.toCharArray();
            i1 = localObject2.length;
            localObject1 = new byte[i1];
            m = n;
            paramPattern = (Pattern)localObject1;
            if (m < i1)
            {
              localObject1[m] = ((byte)localObject2[m]);
              m += 1;
              continue;
            }
          }
          else
          {
            try
            {
              paramPattern = paramString.getBytes("utf-8");
            }
            catch (UnsupportedEncodingException localUnsupportedEncodingException)
            {
              try
              {
                paramPattern = paramString.getBytes("GBK");
              }
              catch (UnsupportedEncodingException paramPattern)
              {
                paramPattern.printStackTrace();
                paramPattern = null;
              }
              localUnsupportedEncodingException.printStackTrace();
            }
          }
          if (str1 == null) {}
        }
      }
      catch (IllegalStateException paramPattern)
      {
        return null;
      }
      try
      {
        if (str1.equalsIgnoreCase("gbk")) {
          return DecoderUtil.decodeEncodedWords(new String(paramPattern, "GBK"));
        }
        if (str1.equalsIgnoreCase("utf-8")) {
          return DecoderUtil.decodeEncodedWords(new String(paramPattern, "utf-8"));
        }
        if (ByteUtils.guessCharacterEncoding(paramPattern) == 2) {
          return DecoderUtil.decodeEncodedWords(new String(paramPattern, "GBK"));
        }
        paramPattern = DecoderUtil.decodeEncodedWords(decode(new String(paramPattern, "utf-8")));
        return paramPattern;
      }
      catch (Exception paramPattern)
      {
        return paramString;
      }
      return null;
      label484:
      String str1 = null;
      label487:
      if (paramString == null)
      {
        return null;
        label493:
        if (m != 0) {
          paramString = localUnsupportedEncodingException;
        }
      }
    }
  }
  
  private static void a()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_a_of_type_Boolean) {
        return;
      }
      if (!jdField_a_of_type_Boolean)
      {
        jdField_a_of_type_JavaUtilRegexPattern = Pattern.compile(".+\\.[A-Za-z]+(?:/.+[^\\w]*.*)?", 2);
        jdField_b_of_type_JavaUtilRegexPattern = Pattern.compile("(^file://.+)|(.+localhost:?\\d*/.+\\..+)", 2);
        jdField_c_of_type_JavaUtilRegexPattern = Pattern.compile("mtt://(.+)", 2);
        jdField_d_of_type_JavaUtilRegexPattern = Pattern.compile("qb://(.+)", 2);
        h = Pattern.compile("x5gameplayer://(.+)", 2);
        jdField_e_of_type_JavaUtilRegexPattern = Pattern.compile("(tenpay|alipay)://(.+)", 2);
        jdField_f_of_type_JavaUtilRegexPattern = Pattern.compile("(\\d){1,3}\\.(\\d){1,3}\\.(\\d){1,3}\\.(\\d){1,3}(:\\d{1,4})?(/(.*))?", 2);
        jdField_g_of_type_JavaUtilRegexPattern = Pattern.compile("((^((http://|https://)?\\[(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|([0-9a-fA-F]{1,4}:){1,7}:|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))]))(.*))", 2);
        j = Pattern.compile("\\s*filename\\s*=\\s*(\"?)([^\"]*)\\1\\s*[;\\s*charset=\\s*]*([^\"]*)\\s*$", 2);
        i = Pattern.compile("attachment;\\s*filename\\s*=\\s*(\"?)([^\"]*)\\1\\s*[;\\s*charset=\\s*]*([^\"]*)\\s*$", 2);
        k = Pattern.compile("inline;\\s*filename\\s*=\\s*(\"?)([^\"]*)\\1\\s*[;\\s*charset=\\s*]*([^\"]*)\\s*$", 2);
        l = Pattern.compile("attachment;\\s*filename\\*\\s*=UTF-8''\\s*(\"?)([^\"]*)\\1\\s*[;\\s*charset=\\s*]*([^\"]*)\\s*$", 2);
        jdField_a_of_type_Boolean = true;
      }
      return;
    }
  }
  
  private static boolean a(long paramLong1, long paramLong2, long paramLong3)
  {
    return (paramLong1 >= paramLong2) && (paramLong1 <= paramLong3);
  }
  
  private static boolean a(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    paramString = paramString.toCharArray();
    int n = paramString.length;
    int m = 0;
    while (m < n)
    {
      if ((byte)(paramString[m] >> '\b' & 0xFF) != 0) {
        return false;
      }
      m += 1;
    }
    return true;
  }
  
  public static int adapt(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramInt1 /= jdField_f_of_type_Int;
    } else {
      paramInt1 /= 2;
    }
    paramInt2 = paramInt1 + paramInt1 / paramInt2;
    paramInt1 = 0;
    int m;
    int n;
    for (;;)
    {
      m = jdField_c_of_type_Int;
      n = jdField_a_of_type_Int;
      if (paramInt2 <= (m - n) * jdField_b_of_type_Int / 2) {
        break;
      }
      paramInt2 /= (m - n);
      paramInt1 += m;
    }
    return paramInt1 + (m - n + 1) * paramInt2 / (paramInt2 + jdField_g_of_type_Int);
  }
  
  public static String addParamsToUrl(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return paramString1;
      }
      String str2 = "";
      int m = paramString1.indexOf('#');
      String str1 = paramString1;
      if (m > 0)
      {
        str1 = paramString1.substring(0, m);
        str2 = paramString1.substring(m);
      }
      paramString1 = "?";
      if (str1.contains("?")) {
        paramString1 = "&";
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(str1);
      localStringBuilder.append(paramString1);
      localStringBuilder.append(paramString2);
      localStringBuilder.append(str2);
      return localStringBuilder.toString();
    }
    return paramString1;
  }
  
  public static int codepoint2digit(int paramInt)
    throws Exception
  {
    int m = paramInt - 48;
    if (m < 10) {
      return m + 26;
    }
    paramInt -= 97;
    if (paramInt < 26) {
      return paramInt;
    }
    throw new Exception("BAD_INPUT");
  }
  
  public static String decode(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return paramString;
    }
    try
    {
      String str = URLDecoder.decode(paramString, "utf-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
      return paramString;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    FLogger.d("UrlUtility", "decode url failure");
    return paramString;
  }
  
  public static String deleteCustomPrefix(String paramString)
  {
    String str;
    if ((!isDttpUrl(paramString)) && (!isSecurityCacheUrl(paramString)) && (!isSecurityFileUrl(paramString)) && (!isBrokerUrl(paramString)))
    {
      str = paramString;
      if (!isWebkitUrl(paramString)) {}
    }
    else
    {
      str = deletePrefix(paramString);
    }
    return str;
  }
  
  public static String deleteHttpPrefix(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return paramString;
    }
    if (paramString.startsWith("http://")) {
      return paramString.substring(7);
    }
    if (paramString.startsWith("https://")) {
      return paramString.substring(8);
    }
    return paramString;
  }
  
  public static String deletePrefix(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    if (paramString.startsWith("page://"))
    {
      m = paramString.indexOf("http://");
      if (m != -1) {
        return paramString.substring(m);
      }
    }
    int m = paramString.indexOf("://");
    String str = paramString;
    if (m >= 0) {
      str = paramString.substring(m + 3);
    }
    return str;
  }
  
  public static int digit2codepoint(int paramInt)
    throws Exception
  {
    if (paramInt < 26) {
      return paramInt + 97;
    }
    if (paramInt < 36) {
      return paramInt - 26 + 48;
    }
    throw new Exception("BAD_INPUT");
  }
  
  public static String encode(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    try
    {
      String str = URLEncoder.encode(paramString, "utf-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
      return paramString;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    FLogger.d("UrlUtility", "encode url failure");
    return paramString;
  }
  
  public static String escape(String paramString)
  {
    if (paramString != null) {
      if (paramString.trim().length() == 0) {
        return paramString;
      }
    }
    for (;;)
    {
      int n;
      int i2;
      int i1;
      int i3;
      int m;
      try
      {
        n = paramString.indexOf('?');
        if (n != -1)
        {
          n += 1;
          String str = paramString.substring(0, n);
          Object localObject1 = paramString.substring(n).split("&");
          Object localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append(str);
          int i4 = localObject1.length;
          i2 = 0;
          n = 0;
          if (i2 < i4)
          {
            str = localObject1[i2];
            i1 = n;
            if (str == null) {
              break label400;
            }
            i1 = n;
            if (str.length() <= 0) {
              break label400;
            }
            n = str.indexOf('=');
            if (n != -1)
            {
              n += 1;
              ((StringBuilder)localObject2).append(str.substring(0, n));
              str = str.substring(n);
              if ((str != null) && (str.length() > 0))
              {
                n = 0;
                i1 = 1;
                i3 = 0;
                if (n < str.length())
                {
                  m = str.charAt(n);
                  if (m < 19968) {
                    break label369;
                  }
                  if (m <= 40959) {
                    break label383;
                  }
                  break label369;
                  if (i1 == 0) {
                    ((StringBuilder)localObject2).append(URLEncoder.encode(str.substring(i3, n), "utf-8"));
                  }
                  if (m == 47)
                  {
                    ((StringBuilder)localObject2).append(m);
                    break label388;
                  }
                  ((StringBuilder)localObject2).append(m);
                  break label388;
                }
                if ((i1 == 0) && (i3 < n)) {
                  ((StringBuilder)localObject2).append(URLEncoder.encode(str.substring(i3, n), "utf-8"));
                }
              }
            }
            else
            {
              ((StringBuilder)localObject2).append(str);
            }
            ((StringBuilder)localObject2).append('&');
            i1 = 1;
            break label400;
          }
          localObject2 = ((StringBuilder)localObject2).toString();
          localObject1 = localObject2;
          if (n != 0)
          {
            localObject1 = localObject2;
            if (((String)localObject2).charAt(((String)localObject2).length() - 1) == '&') {
              localObject1 = ((String)localObject2).substring(0, ((String)localObject2).length() - 1);
            }
          }
          return (String)localObject1;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return paramString;
      return paramString;
      label369:
      if ((m >= 65072) && (m <= 65440))
      {
        label383:
        i1 = 0;
        break label393;
        label388:
        i3 = n;
        i1 = 1;
        label393:
        n += 1;
        continue;
        label400:
        i2 += 1;
        n = i1;
      }
    }
  }
  
  public static String escapeAllChineseChar(String paramString)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    int n = 0;
    while (n < paramString.length())
    {
      int m = paramString.charAt(n);
      if (((m >= 19968) && (m <= 40959)) || ((m >= 65072) && (m <= 65440))) {
        try
        {
          String str = URLEncoder.encode(String.valueOf(m), "utf-8");
          StringBuilder localStringBuilder2 = new StringBuilder();
          localStringBuilder2.append(" : ");
          localStringBuilder2.append(str);
          FLogger.d("Url", localStringBuilder2.toString());
          localStringBuilder1.append(str);
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          localUnsupportedEncodingException.printStackTrace();
        }
      } else {
        localStringBuilder1.append(m);
      }
      n += 1;
    }
    return localStringBuilder1.toString();
  }
  
  public static String getAction(String paramString)
  {
    paramString = getStringAfterHost(paramString);
    if (TextUtils.isEmpty(paramString)) {
      return "";
    }
    paramString = paramString.split("/|\\?");
    if ((paramString != null) && (paramString.length > 0)) {
      return paramString[0];
    }
    return "";
  }
  
  public static String getDataFromQbUrl(String paramString1, String paramString2)
  {
    String str = "";
    if (paramString1 == null) {
      return "";
    }
    String[] arrayOfString = paramString1.substring(paramString1.indexOf('?') + 1).split("&");
    try
    {
      int n = arrayOfString.length;
      int m = 0;
      for (;;)
      {
        paramString1 = str;
        if (m >= n) {
          break;
        }
        paramString1 = arrayOfString[m].split("=");
        if (paramString1[0].equalsIgnoreCase(paramString2))
        {
          paramString1 = paramString1[1];
          return paramString1;
        }
        m += 1;
      }
    }
    catch (Exception paramString1)
    {
      for (;;) {}
    }
    paramString1 = null;
    return paramString1;
  }
  
  public static String getDefaultExtensionByMimeType(String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    String str = null;
    if (bool) {
      return null;
    }
    if ((paramString != null) && (paramString.toLowerCase().startsWith("text/")))
    {
      if (paramString.equalsIgnoreCase("text/html")) {
        return ".html";
      }
      return ".txt";
    }
    if ((paramString != null) && (paramString.toLowerCase().startsWith("image/")))
    {
      if (paramString.equalsIgnoreCase("image/png")) {
        return ".png";
      }
      if (paramString.equalsIgnoreCase("image/jpeg")) {
        return ".jpeg";
      }
      if (paramString.equalsIgnoreCase("image/jpg")) {
        return ".jpg";
      }
      if (paramString.equalsIgnoreCase("image/gif")) {
        return ".gif";
      }
    }
    else if ((paramString != null) && (paramString.toLowerCase().startsWith("video/")))
    {
      if (paramString.equalsIgnoreCase("video/flv")) {
        return ".flv";
      }
    }
    else
    {
      str = ".bin";
    }
    return str;
  }
  
  public static String getDomainIp(String paramString)
  {
    try
    {
      paramString = InetAddress.getByName(new URI(paramString).getHost()).getHostAddress();
      return paramString;
    }
    catch (Error paramString)
    {
      paramString.printStackTrace();
      return "";
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return "";
  }
  
  public static String getHost(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      int m = paramString.indexOf("://");
      if (m != -1) {
        m += 3;
      } else {
        m = 0;
      }
      int n = paramString.indexOf('/', m);
      if (n != -1)
      {
        paramString = paramString.substring(m, n);
      }
      else
      {
        n = paramString.indexOf('?', m);
        if (n != -1) {
          paramString = paramString.substring(m, n);
        } else {
          paramString = paramString.substring(m);
        }
      }
      m = paramString.lastIndexOf(":");
      String str = paramString;
      if (m >= 0) {
        str = paramString.substring(0, m);
      }
      return str;
    }
    return null;
  }
  
  public static String getJavascriptCommand(String paramString)
  {
    int m = paramString.indexOf(':');
    int i1 = paramString.indexOf(';');
    if (m == -1) {
      m = 0;
    } else {
      m += 1;
    }
    int n = i1;
    if (i1 == -1) {
      n = paramString.length();
    }
    return paramString.substring(m, n);
  }
  
  public static String getMailUrl(String paramString)
  {
    if (paramString != null)
    {
      if ("".equalsIgnoreCase(paramString)) {
        return null;
      }
      if (isMailUrl(paramString)) {
        return paramString;
      }
      return null;
    }
    return null;
  }
  
  public static String getPara(String paramString)
  {
    paramString = getStringAfterHost(paramString);
    if (TextUtils.isEmpty(paramString)) {
      return "";
    }
    paramString = paramString.split("/|\\?");
    if ((paramString != null) && (paramString.length > 1)) {
      return paramString[1];
    }
    return "";
  }
  
  public static String getPath(String paramString)
  {
    String str = null;
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return null;
      }
      int m = 0;
      int n = paramString.indexOf("://");
      if (n != -1) {
        m = n + 3;
      }
      m = paramString.indexOf('/', m);
      if (m != -1)
      {
        n = paramString.indexOf('?', m);
        if (n != -1) {
          return paramString.substring(m + 1, n);
        }
        str = paramString.substring(m + 1);
      }
      return str;
    }
    return null;
  }
  
  public static String getPathAndQuery(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return "";
    }
    try
    {
      paramString = new URL(paramString);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString.getPath());
      localStringBuilder.append(paramString.getQuery());
      paramString = localStringBuilder.toString();
      return paramString;
    }
    catch (MalformedURLException paramString)
    {
      paramString.printStackTrace();
    }
    return "";
  }
  
  public static String getSchema(String paramString)
  {
    String str2 = "";
    String str1 = str2;
    if (!TextUtils.isEmpty(paramString))
    {
      int m = paramString.indexOf("://");
      str1 = str2;
      if (m > 0) {
        str1 = paramString.substring(0, m + 3);
      }
    }
    return str1;
  }
  
  public static String getSmsTextFromUrl(String paramString)
  {
    if (isSmsUrl(paramString))
    {
      int m = paramString.indexOf('?');
      int n;
      if ((m > -1) && (m < paramString.length() - 1))
      {
        paramString = paramString.substring(m + 1).split("&");
        n = paramString.length;
        m = 0;
      }
      while (m < n)
      {
        Object localObject = paramString[m];
        if (((String)localObject).startsWith("body="))
        {
          int i1 = ((String)localObject).indexOf('=');
          if ((i1 > -1) && (i1 < ((String)localObject).length() - 1)) {
            return ((String)localObject).substring(i1 + 1);
          }
        }
        m += 1;
        continue;
        return null;
      }
    }
    return null;
  }
  
  public static Uri getSmsUriFromUrl(String paramString)
  {
    if (isSmsUrl(paramString))
    {
      paramString = paramString.replaceFirst("sms:", "smsto:");
      int m = paramString.indexOf('?');
      if (m > -1) {
        return Uri.parse(paramString.substring(0, m));
      }
      return Uri.parse(paramString);
    }
    return null;
  }
  
  public static String getStringAfterHost(String paramString)
  {
    String str = null;
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return null;
      }
      int m = 0;
      int n = paramString.indexOf("://");
      if (n != -1) {
        m = n + 3;
      }
      m = paramString.indexOf('/', m);
      if (m != -1) {
        str = paramString.substring(m + 1);
      }
      return str;
    }
    return null;
  }
  
  public static String getTelUrl(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    if (isTelUrl(paramString)) {
      return paramString;
    }
    paramString = getWtaiUrl(paramString);
    if (paramString != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("tel:");
      localStringBuilder.append(paramString);
      return localStringBuilder.toString();
    }
    return null;
  }
  
  public static HashMap<String, String> getUrlParam(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    HashMap localHashMap = new HashMap();
    int m = paramString.indexOf('?');
    if (m != -1)
    {
      String[] arrayOfString = paramString.substring(m + 1).split("&");
      if ((arrayOfString != null) && (arrayOfString.length > 0))
      {
        int n = arrayOfString.length;
        m = 0;
        while (m < n)
        {
          paramString = arrayOfString[m];
          int i1 = paramString.indexOf('=');
          if (i1 != -1)
          {
            String str2 = paramString.substring(0, i1);
            paramString = paramString.substring(i1 + 1, paramString.length());
            try
            {
              String str1 = URLDecoder.decode(paramString, "UTF-8");
              paramString = str1;
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
            }
            catch (UnsupportedEncodingException localUnsupportedEncodingException)
            {
              localUnsupportedEncodingException.printStackTrace();
            }
            localHashMap.put(str2, paramString);
          }
          m += 1;
        }
      }
    }
    return localHashMap;
  }
  
  public static String getUrlParamValue(String paramString1, String paramString2)
  {
    Object localObject1 = "";
    if (!TextUtils.isEmpty(paramString1))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return "";
      }
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("(#|\\?|&)(");
      ((StringBuilder)localObject2).append(paramString2);
      ((StringBuilder)localObject2).append("=.*?)(#|&|$)");
      localObject2 = Pattern.compile(((StringBuilder)localObject2).toString(), 2).matcher(paramString1);
      paramString1 = (String)localObject1;
      if (localObject2 != null)
      {
        paramString1 = (String)localObject1;
        if (((Matcher)localObject2).find())
        {
          paramString1 = (String)localObject1;
          if (((Matcher)localObject2).groupCount() > 2)
          {
            paramString1 = ((Matcher)localObject2).group(2);
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append(paramString2);
            ((StringBuilder)localObject1).append("=");
            paramString1 = paramString1.replace(((StringBuilder)localObject1).toString(), "");
          }
        }
      }
      return paramString1;
    }
    return "";
  }
  
  public static String getValueByKey(String paramString1, String paramString2, String paramString3, char paramChar)
  {
    if (!paramString2.contains(paramString3)) {
      return "";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    int m = paramString2.indexOf(paramString3) + paramString3.length();
    while ((m < paramString1.length()) && (paramString1.charAt(m) != paramChar))
    {
      localStringBuffer.append(paramString1.charAt(m));
      m += 1;
    }
    return localStringBuffer.toString();
  }
  
  public static String getWtaiUrl(String paramString)
  {
    if (isWtaiUrl(paramString))
    {
      int m = paramString.indexOf("?", 13);
      if (m != -1) {
        return paramString.substring(13, m);
      }
      return paramString.substring(13);
    }
    return null;
  }
  
  public static final String guessFileName(String paramString1, String paramString2, String paramString3)
  {
    Object localObject3 = null;
    Object localObject4 = null;
    int n = 1;
    if (paramString2 != null)
    {
      localObject1 = a(USER_CONTENT_DISPOSITION_PATTERN(), paramString2);
      if (localObject1 != null)
      {
        m = ((String)localObject1).lastIndexOf('/') + 1;
        if (m > 0) {
          paramString2 = ((String)localObject1).substring(m);
        } else {
          paramString2 = (String)localObject1;
        }
      }
      else
      {
        localObject1 = a(CONTENT_DISPOSITION_PATTERN(), paramString2);
        if (localObject1 != null)
        {
          m = ((String)localObject1).lastIndexOf('/') + 1;
          if (m > 0) {
            paramString2 = ((String)localObject1).substring(m);
          } else {
            paramString2 = (String)localObject1;
          }
        }
        else
        {
          localObject1 = a(INLINE_CONTENT_DISPOSITION_PATTERN(), paramString2);
          if (localObject1 != null)
          {
            m = ((String)localObject1).lastIndexOf('/') + 1;
            if (m > 0) {
              paramString2 = ((String)localObject1).substring(m);
            } else {
              paramString2 = (String)localObject1;
            }
          }
          else
          {
            localObject1 = a(OTHER_CONTENT_DISPOSITION_PATTERN(), paramString2);
            paramString2 = (String)localObject1;
            if (localObject1 != null)
            {
              m = ((String)localObject1).lastIndexOf('/') + 1;
              paramString2 = (String)localObject1;
              if (m > 0) {
                paramString2 = ((String)localObject1).substring(m);
              }
            }
          }
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
        m = ((String)localObject1).indexOf('?');
        paramString1 = (String)localObject1;
        if (m > 0) {
          paramString1 = ((String)localObject1).substring(0, m);
        }
        localObject2 = paramString2;
        if (!paramString1.endsWith("/"))
        {
          m = paramString1.lastIndexOf('/') + 1;
          localObject2 = paramString2;
          if (m > 0) {
            localObject2 = paramString1.substring(m);
          }
        }
      }
    }
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = "downloadfile";
    }
    int m = ((String)localObject1).lastIndexOf('.');
    if (m < 0)
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
      if (paramString2 == null)
      {
        paramString1 = getDefaultExtensionByMimeType(paramString3);
        localObject2 = localObject1;
      }
    }
    else
    {
      paramString2 = ((String)localObject1).substring(m + 1);
      if ((paramString2 != null) && (("png".equalsIgnoreCase(paramString2)) || ("jpeg".equalsIgnoreCase(paramString2)) || ("jpg".equalsIgnoreCase(paramString2)) || ("gif".equalsIgnoreCase(paramString2))))
      {
        paramString1 = new StringBuilder();
        paramString1.append(".");
        paramString1.append(paramString2);
        paramString1 = paramString1.toString();
      }
      else
      {
        paramString1 = (String)localObject3;
        if (TextUtils.isEmpty(paramString2))
        {
          paramString1 = (String)localObject3;
          if (paramString3 != null) {
            if ((paramString2 != null) && (paramString2.equalsIgnoreCase("apk")))
            {
              paramString1 = ".apk";
            }
            else
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
      }
      paramString2 = paramString1;
      if (paramString1 == null) {
        paramString2 = ((String)localObject1).substring(m);
      }
      localObject2 = ((String)localObject1).substring(0, m);
      paramString1 = paramString2;
    }
    if ((paramString1 == null) && (paramString3 != null) && (paramString3.startsWith("image/")))
    {
      paramString2 = new StringBuilder();
      paramString2.append(".");
      paramString2.append(paramString3.substring(paramString3.indexOf("/") + 1));
      paramString2 = paramString2.toString();
      m = n;
      if (!"png".equalsIgnoreCase(paramString2))
      {
        m = n;
        if (!"jpeg".equalsIgnoreCase(paramString2))
        {
          m = n;
          if (!"jpg".equalsIgnoreCase(paramString2)) {
            if ("gif".equalsIgnoreCase(paramString2)) {
              m = n;
            } else {
              m = 0;
            }
          }
        }
      }
      if (m != 0) {
        paramString1 = paramString2;
      }
    }
    paramString2 = paramString1;
    if (paramString1 == null) {
      paramString2 = "";
    }
    paramString1 = new StringBuilder();
    paramString1.append((String)localObject2);
    paramString1.append(paramString2);
    return paramString1.toString();
  }
  
  public static boolean hasValidProtocal(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return false;
      }
      int m = paramString.indexOf("://");
      int n = paramString.indexOf('.');
      if ((m > 0) && (n > 0) && (m > n)) {
        return false;
      }
      return paramString.contains("://");
    }
    return false;
  }
  
  public static boolean isAnchorUrl(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      return paramString.startsWith("#");
    }
    return false;
  }
  
  public static boolean isBasic(char paramChar)
  {
    return paramChar < '';
  }
  
  public static boolean isBrokerUrl(String paramString)
  {
    boolean bool = false;
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return false;
      }
      if ((paramString.startsWith("page:")) || (paramString.startsWith("hotpre:"))) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  public static boolean isDataBase64Url(String paramString)
  {
    return (paramString != null) && (paramString.startsWith("data:text/html; charset=utf-8;base64,"));
  }
  
  public static boolean isDataUrl(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() < 5) {
        return false;
      }
      return paramString.substring(0, 5).equalsIgnoreCase("data:");
    }
    return false;
  }
  
  public static boolean isDeprecatedSechema(String paramString)
  {
    return (isWebkitUrl(paramString)) || (isMttUrl(paramString)) || (isBrokerUrl(paramString)) || (isDttpUrl(paramString)) || (isTencentUrl(paramString));
  }
  
  public static boolean isDttpUrl(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      return paramString.toLowerCase().startsWith("dttp://");
    }
    return false;
  }
  
  public static boolean isEmptyUrl(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0) || (paramString.trim().equals("/"));
  }
  
  public static boolean isFileUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 6)
      {
        bool1 = bool2;
        if (paramString.substring(0, 7).equalsIgnoreCase("file://")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean isFtpUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 5)
      {
        bool1 = bool2;
        if (paramString.substring(0, 6).equalsIgnoreCase("ftp://")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
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
  
  public static boolean isInnerIP(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    String str = paramString.trim();
    if (!VALID_IP_ADDRESS().matcher(str).find()) {
      return false;
    }
    try
    {
      long l1 = a(paramString);
      long l2 = a("10.0.0.0");
      long l3 = a("10.255.255.255");
      long l4 = a("172.16.0.0");
      long l5 = a("172.31.255.255");
      long l6 = a("192.168.0.0");
      long l7 = a("192.168.255.255");
      if ((!a(l1, l2, l3)) && (!a(l1, l4, l5)) && (!a(l1, l6, l7)) && (!paramString.equals("127.0.0.1")))
      {
        boolean bool = paramString.equals("1.1.1.1");
        if (!bool) {}
      }
      else
      {
        return true;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public static boolean isIpUrl(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return false;
      }
      paramString = paramString.trim();
      return VALID_IP_ADDRESS().matcher(paramString).find();
    }
    return false;
  }
  
  public static boolean isIpv6Url(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return false;
      }
      paramString = paramString.trim();
      return VALID_IPV6_ADDRESS().matcher(paramString).find();
    }
    return false;
  }
  
  public static boolean isJavascript(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 10)
      {
        bool1 = bool2;
        if (paramString.substring(0, 11).equalsIgnoreCase("javascript:")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean isMailUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 7)
      {
        bool1 = bool2;
        if (paramString.substring(0, 7).equalsIgnoreCase("mailto:")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean isMarketUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 8)
      {
        bool1 = bool2;
        if (paramString.substring(0, 9).equalsIgnoreCase("market://")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean isMmsUrl(String paramString)
  {
    if (paramString != null)
    {
      if ((paramString.length() > 6) && (paramString.substring(0, 6).equalsIgnoreCase("mms://"))) {
        return true;
      }
      if ((paramString.length() > 7) && (paramString.substring(0, 7).equalsIgnoreCase("mmsh://"))) {
        return true;
      }
      return (paramString.length() > 7) && (paramString.substring(0, 7).equalsIgnoreCase("mmst://"));
    }
    return false;
  }
  
  public static boolean isMttUrl(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      return paramString.toLowerCase().startsWith("mtt://");
    }
    return false;
  }
  
  public static boolean isRtmpUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 6)
      {
        bool1 = bool2;
        if (paramString.substring(0, 7).equalsIgnoreCase("rtmp://")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean isRtspUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 6)
      {
        bool1 = bool2;
        if (paramString.substring(0, 7).equalsIgnoreCase("rtsp://")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean isSecurityCacheUrl(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      return paramString.startsWith("security://");
    }
    return false;
  }
  
  public static boolean isSecurityFileUrl(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      return paramString.startsWith("securityFile://");
    }
    return false;
  }
  
  public static boolean isSmsUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 4)
      {
        bool1 = bool2;
        if (paramString.substring(0, 4).equalsIgnoreCase("sms:")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean isTelUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 4)
      {
        bool1 = bool2;
        if (paramString.substring(0, 4).equalsIgnoreCase("tel:")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean isTencentUrl(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      return paramString.toLowerCase().startsWith("tencent://");
    }
    return false;
  }
  
  public static boolean isWebUrl(String paramString)
  {
    return (isHttpUrl(paramString)) || (isHttpsUrl(paramString));
  }
  
  public static boolean isWebkitUrl(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      return paramString.toLowerCase().startsWith("webkit://");
    }
    return false;
  }
  
  public static boolean isWsUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 4)
      {
        bool1 = bool2;
        if (paramString.substring(0, 5).equalsIgnoreCase("ws://")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean isWssUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 5)
      {
        bool1 = bool2;
        if (paramString.substring(0, 6).equalsIgnoreCase("wss://")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean isWtaiUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 13)
      {
        bool1 = bool2;
        if (paramString.substring(0, 13).equalsIgnoreCase("wtai://wp/mc;")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static String prepareUrl(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return paramString;
      }
      if (paramString.charAt(0) == '#') {
        return paramString;
      }
      String str = paramString;
      try
      {
        paramString = paramString.replaceAll(" ", "%20");
        str = paramString;
        paramString = paramString.replaceAll("&amp;", "&");
        str = paramString;
        paramString = paramString.replaceAll("\\|", "%7C");
        str = paramString;
        paramString = paramString.replaceAll("\\^", "%5E");
        str = paramString;
        paramString = paramString.replaceAll("<", "%3C");
        str = paramString;
        paramString = paramString.replaceAll(">", "%3E");
        str = paramString;
        paramString = paramString.replaceAll("\\{", "%7B");
        str = paramString;
        paramString = paramString.replaceAll("\\}", "%7D");
        str = paramString;
      }
      catch (PatternSyntaxException paramString)
      {
        paramString.printStackTrace();
      }
      paramString = str;
      if (!isSmsUrl(str)) {
        paramString = escapeAllChineseChar(str);
      }
      return paramString;
    }
    return paramString;
  }
  
  public static String punyCodedecode(String paramString)
    throws Exception
  {
    int m = jdField_d_of_type_Int;
    int i2 = jdField_e_of_type_Int;
    StringBuilder localStringBuilder = new StringBuilder();
    int i1 = paramString.lastIndexOf(jdField_a_of_type_Char);
    int n;
    int i3;
    if (i1 > 0)
    {
      n = 0;
      while (n < i1)
      {
        char c1 = paramString.charAt(n);
        if (isBasic(c1))
        {
          localStringBuilder.append(c1);
          n += 1;
        }
        else
        {
          throw new Exception("BAD_INPUT");
        }
      }
      n = i1 + 1;
      i1 = m;
      m = 0;
    }
    else
    {
      i3 = 0;
      n = 0;
      i1 = m;
      m = i3;
    }
    if (n < paramString.length())
    {
      int i4 = jdField_c_of_type_Int;
      int i5 = 1;
      int i6 = m;
      for (;;)
      {
        if (n != paramString.length())
        {
          i3 = n + 1;
          int i7 = codepoint2digit(paramString.charAt(n));
          if (i7 <= (Integer.MAX_VALUE - i6) / i5)
          {
            i6 += i7 * i5;
            if (i4 <= i2)
            {
              n = jdField_a_of_type_Int;
            }
            else
            {
              n = jdField_b_of_type_Int;
              if (i4 < i2 + n) {
                n = i4 - i2;
              }
            }
            if (i7 < n)
            {
              n = localStringBuilder.length();
              boolean bool;
              if (m == 0) {
                bool = true;
              } else {
                bool = false;
              }
              i2 = adapt(i6 - m, n + 1, bool);
              if (i6 / (localStringBuilder.length() + 1) <= Integer.MAX_VALUE - i1)
              {
                i1 += i6 / (localStringBuilder.length() + 1);
                m = i6 % (localStringBuilder.length() + 1);
                localStringBuilder.insert(m, (char)i1);
                m += 1;
                n = i3;
                break;
              }
              throw new Exception("OVERFLOW");
            }
            i7 = jdField_c_of_type_Int;
            i5 *= (i7 - n);
            i4 += i7;
            n = i3;
            continue;
          }
          throw new Exception("OVERFLOW");
        }
      }
      throw new Exception("BAD_INPUT");
    }
    return localStringBuilder.toString();
  }
  
  public static String punyCodeencode(String paramString)
    throws Exception
  {
    int i2 = jdField_d_of_type_Int;
    int i4 = jdField_e_of_type_Int;
    StringBuilder localStringBuilder = new StringBuilder();
    int m = 0;
    for (int n = 0; m < paramString.length(); n = i1)
    {
      char c1 = paramString.charAt(m);
      i1 = n;
      if (isBasic(c1))
      {
        localStringBuilder.append(c1);
        i1 = n + 1;
      }
      m += 1;
    }
    if (n > 0) {
      localStringBuilder.append(jdField_a_of_type_Char);
    }
    int i1 = 0;
    m = n;
    while (m < paramString.length())
    {
      int i5 = 0;
      int i7;
      int i6;
      for (int i3 = Integer.MAX_VALUE; i5 < paramString.length(); i3 = i6)
      {
        i7 = paramString.charAt(i5);
        i6 = i3;
        if (i7 >= i2)
        {
          i6 = i3;
          if (i7 < i3) {
            i6 = i7;
          }
        }
        i5 += 1;
      }
      i2 = i3 - i2;
      i5 = m + 1;
      if (i2 <= (Integer.MAX_VALUE - i1) / i5)
      {
        i2 = i1 + i2 * i5;
        i5 = 0;
        i1 = m;
        while (i5 < paramString.length())
        {
          int i8 = paramString.charAt(i5);
          m = i2;
          if (i8 < i3)
          {
            m = i2 + 1;
            if (m == 0) {
              throw new Exception("OVERFLOW");
            }
          }
          i6 = i1;
          i2 = m;
          i7 = i4;
          if (i8 == i3)
          {
            i6 = jdField_c_of_type_Int;
            i7 = m;
            for (;;)
            {
              if (i6 <= i4)
              {
                i2 = jdField_a_of_type_Int;
              }
              else
              {
                i2 = jdField_b_of_type_Int;
                if (i6 < i4 + i2) {
                  i2 = i6 - i4;
                }
              }
              if (i7 < i2)
              {
                localStringBuilder.append((char)digit2codepoint(i7));
                i6 = i1 + 1;
                boolean bool;
                if (i1 == n) {
                  bool = true;
                } else {
                  bool = false;
                }
                i7 = adapt(m, i6, bool);
                i2 = 0;
                break;
              }
              i7 -= i2;
              localStringBuilder.append((char)digit2codepoint(i7 % (jdField_c_of_type_Int - i2) + i2));
              i8 = jdField_c_of_type_Int;
              i7 /= (i8 - i2);
              i6 += i8;
            }
          }
          i5 += 1;
          i1 = i6;
          i4 = i7;
        }
        i5 = i2 + 1;
        i2 = i3 + 1;
        m = i1;
        i1 = i5;
      }
      else
      {
        throw new Exception("OVERFLOW");
      }
    }
    return localStringBuilder.toString();
  }
  
  public static String removeArg(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return "";
    }
    Object localObject = paramString1;
    if (!TextUtils.isEmpty(paramString1))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("?");
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).append("=");
      if (!paramString1.contains(((StringBuilder)localObject).toString()))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("&");
        localStringBuilder.append(paramString2);
        localStringBuilder.append("=");
        localObject = paramString1;
        if (!paramString1.contains(localStringBuilder.toString())) {}
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString2);
        ((StringBuilder)localObject).append("=");
        int m = paramString1.indexOf(((StringBuilder)localObject).toString());
        localObject = paramString1;
        if (m != -1)
        {
          paramString2 = paramString1.substring(0, m);
          localObject = paramString1.substring(m + 1);
          if ((!TextUtils.isEmpty((CharSequence)localObject)) && (((String)localObject).indexOf("&") > 0))
          {
            paramString1 = ((String)localObject).substring(((String)localObject).indexOf("&") + 1);
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append(paramString2);
            ((StringBuilder)localObject).append(paramString1);
            return ((StringBuilder)localObject).toString();
          }
          localObject = paramString1.substring(0, m - 1);
        }
      }
    }
    return (String)localObject;
  }
  
  public static String removeMMSprefix(String paramString)
  {
    if ((!TextUtils.isEmpty(paramString)) && ((paramString.toLowerCase().startsWith("sms:")) || (paramString.toLowerCase().startsWith("smsto:")) || (paramString.toLowerCase().startsWith("mms:")) || (paramString.toLowerCase().startsWith("mmsto:")))) {
      return paramString.substring(paramString.indexOf(":") + 1, paramString.length());
    }
    return paramString;
  }
  
  public static String replaceValueByKey(String paramString1, String paramString2, String paramString3)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return paramString1;
      }
      StringBuilder localStringBuilder = new StringBuilder("");
      String str = "?";
      int m = paramString1.indexOf('?');
      if (m != -1) {
        localStringBuilder.append(paramString1.substring(0, m));
      } else {
        str = "";
      }
      String[] arrayOfString1 = paramString1.substring(m + 1).split("&");
      int n = arrayOfString1.length;
      m = 0;
      while (m < n)
      {
        String[] arrayOfString2 = arrayOfString1[m].split("=");
        if ((arrayOfString2[0].equalsIgnoreCase(paramString2)) && (arrayOfString2.length == 2)) {
          arrayOfString2[1] = paramString3;
        }
        if (m == 0) {
          paramString1 = str;
        } else {
          paramString1 = "&";
        }
        localStringBuilder.append(paramString1);
        localStringBuilder.append(arrayOfString2[0]);
        localStringBuilder.append("=");
        if (arrayOfString2.length == 2) {
          paramString1 = arrayOfString2[1];
        } else {
          paramString1 = "";
        }
        localStringBuilder.append(paramString1);
        m += 1;
      }
      return localStringBuilder.toString();
    }
    return paramString1;
  }
  
  public static String resolvValidSqlUrl(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return paramString;
    }
    return paramString.replaceAll("'", "''");
  }
  
  public static String resolveBase(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = prepareUrl(toURL(paramString1).toString());
      paramString2 = prepareUrl(paramString2);
      paramString1 = new URI(paramString1).resolve(paramString2).toURL();
      return paramString1.toString();
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
      return "";
    }
    catch (MalformedURLException paramString1)
    {
      paramString1.printStackTrace();
    }
    return "";
  }
  
  public static String stripAnhcor(String paramString)
    throws MalformedURLException
  {
    int m = paramString.indexOf('#');
    if (m != -1) {
      return paramString.substring(0, m);
    }
    return paramString;
  }
  
  public static String stripPath(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    String str = getHost(paramString);
    if ((str != null) && (!"".equals(str)))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(getSchema(paramString));
      localStringBuilder.append(str);
      return localStringBuilder.toString();
    }
    return null;
  }
  
  public static URL stripQuery(URL paramURL)
    throws MalformedURLException
  {
    String str = paramURL.getFile();
    int m = str.indexOf("?");
    if (m == -1) {
      return paramURL;
    }
    str = str.substring(0, m);
    return new URL(paramURL.getProtocol(), paramURL.getHost(), paramURL.getPort(), str);
  }
  
  public static String stripSchemePrefix(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return paramString;
    }
    int m = paramString.indexOf("://");
    if (m != -1) {
      return paramString.substring(m + 3);
    }
    return paramString;
  }
  
  public static URL toURL(String paramString)
    throws MalformedURLException
  {
    URL localURL = new URL(paramString);
    Object localObject2;
    if (localURL.getPath() != null)
    {
      localObject2 = localURL;
      if (!"".equals(localURL.getPath())) {}
    }
    else
    {
      Object localObject1 = localURL;
      if (localURL.getFile() != null)
      {
        localObject1 = localURL;
        if (localURL.getFile().startsWith("?"))
        {
          int m = paramString.indexOf('?');
          localObject1 = localURL;
          if (m != -1)
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append(paramString.substring(0, m));
            ((StringBuilder)localObject1).append('/');
            ((StringBuilder)localObject1).append(paramString.substring(m));
            localObject1 = new URL(((StringBuilder)localObject1).toString());
          }
        }
      }
      if (((URL)localObject1).getFile() != null)
      {
        localObject2 = localObject1;
        if (!"".equals(((URL)localObject1).getFile())) {}
      }
      else
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(paramString);
        ((StringBuilder)localObject1).append("/");
        localObject2 = new URL(((StringBuilder)localObject1).toString());
      }
    }
    return (URL)localObject2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\UrlUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */