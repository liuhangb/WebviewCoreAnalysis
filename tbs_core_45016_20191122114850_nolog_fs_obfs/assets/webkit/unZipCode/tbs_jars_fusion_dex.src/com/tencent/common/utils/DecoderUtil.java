package com.tencent.common.utils;

import com.tencent.basesupport.FLogger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecoderUtil
{
  private static final Pattern a = Pattern.compile("(.*?)=\\?([^\\?]+?)\\?(\\w)\\?([^\\?]+?)\\?=", 32);
  
  private static String a(String paramString1, String paramString2, String paramString3)
  {
    Object localObject = CharsetUtil.toJavaCharset(paramString1);
    if (localObject == null) {
      return null;
    }
    if (!CharsetUtil.isDecodingSupported((String)localObject)) {
      return null;
    }
    if (paramString3.length() == 0)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Missing encoded text in encoded word: '");
      ((StringBuilder)localObject).append(b(paramString1, paramString2, paramString3));
      ((StringBuilder)localObject).append("'");
      FLogger.d("DecoderUtil", ((StringBuilder)localObject).toString());
      return null;
    }
    try
    {
      if (paramString2.equalsIgnoreCase("Q")) {
        return decodeQ(paramString3, (String)localObject);
      }
      if (paramString2.equalsIgnoreCase("B")) {
        return new String(decodeBase64(paramString3), (String)localObject);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Warning: Unknown encoding in encoded word '");
      ((StringBuilder)localObject).append(b(paramString1, paramString2, paramString3));
      ((StringBuilder)localObject).append("'");
      FLogger.d("DecoderUtil", ((StringBuilder)localObject).toString());
      return null;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;) {}
    }
    catch (RuntimeException localRuntimeException)
    {
      for (;;) {}
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Could not decode encoded word '");
    ((StringBuilder)localObject).append(b(paramString1, paramString2, paramString3));
    ((StringBuilder)localObject).append("'");
    FLogger.d("DecoderUtil", ((StringBuilder)localObject).toString());
    return null;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unsupported encoding in encoded word '");
    ((StringBuilder)localObject).append(b(paramString1, paramString2, paramString3));
    ((StringBuilder)localObject).append("'");
    FLogger.d("DecoderUtil", ((StringBuilder)localObject).toString());
    return null;
  }
  
  private static String b(String paramString1, String paramString2, String paramString3)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("=?");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("?");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("?");
    localStringBuilder.append(paramString3);
    localStringBuilder.append("?=");
    return localStringBuilder.toString();
  }
  
  public static String decodeB(String paramString1, String paramString2)
    throws UnsupportedEncodingException
  {
    return new String(decodeBase64(paramString1), paramString2);
  }
  
  public static byte[] decodeBase64(String paramString)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      paramString = new Base64InputStream(new ByteArrayInputStream(paramString.getBytes("US-ASCII")), false);
      for (;;)
      {
        int i = paramString.read();
        if (i == -1) {
          break;
        }
        localByteArrayOutputStream.write(i);
      }
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException paramString)
    {
      FLogger.e("DecoderUtil", paramString);
      throw new IllegalStateException(paramString);
    }
  }
  
  public static String decodeEncodedWords(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    Matcher localMatcher = a.matcher(paramString);
    int j = 0;
    int i = 0;
    while (localMatcher.find())
    {
      int k = 1;
      String str1 = localMatcher.group(1);
      String str2 = a(localMatcher.group(2), localMatcher.group(3), localMatcher.group(4));
      if (str2 == null)
      {
        localStringBuilder.append(localMatcher.group(0));
      }
      else
      {
        if ((i == 0) || (!CharsetUtil.isWhitespace(str1))) {
          localStringBuilder.append(str1);
        }
        localStringBuilder.append(str2);
      }
      j = localMatcher.end();
      if (str2 != null) {
        i = k;
      } else {
        i = 0;
      }
    }
    if (j == 0) {
      return paramString;
    }
    localStringBuilder.append(paramString.substring(j));
    return localStringBuilder.toString();
  }
  
  public static String decodeQ(String paramString1, String paramString2)
    throws UnsupportedEncodingException
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    int i = 0;
    while (i < paramString1.length())
    {
      char c = paramString1.charAt(i);
      if (c == '_') {
        localStringBuilder.append("=20");
      } else {
        localStringBuilder.append(c);
      }
      i += 1;
    }
    return new String(decodeQuotedPrintable(localStringBuilder.toString()), paramString2);
  }
  
  public static byte[] decodeQuotedPrintable(String paramString)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      paramString = new QuotedPrintableInputStream(new ByteArrayInputStream(paramString.getBytes("US-ASCII")));
      for (;;)
      {
        int i = paramString.read();
        if (i == -1) {
          break;
        }
        localByteArrayOutputStream.write(i);
      }
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException paramString)
    {
      FLogger.e("DecoderUtil", paramString);
      throw new IllegalStateException(paramString);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\DecoderUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */