package com.tencent.common.utils;

import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import com.tencent.basesupport.FLogger;
import java.io.BufferedReader;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class StringUtils
{
  public static final float G = 1.07374182E9F;
  public static final float K = 1024.0F;
  public static final float M = 1048576.0F;
  public static final String TAG = "StringUtil";
  public static final int TEXT_BLOCK = 6;
  public static final int TEXT_COMMON = 0;
  public static final int TEXT_DEC = 4;
  public static final int TEXT_HEX = 3;
  public static final int TEXT_NUM = 2;
  public static final int TEXT_OCTAL = 5;
  public static final int TEXT_SPEC = 1;
  private static GdiMeasureImpl jdField_a_of_type_ComTencentCommonUtilsGdiMeasureImpl;
  private static Pattern jdField_a_of_type_JavaUtilRegexPattern;
  private static Pattern b;
  
  public static String RandomNumberString(int paramInt)
  {
    Random localRandom = new Random();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    while (i < paramInt)
    {
      localStringBuffer.append("0123456789".charAt(localRandom.nextInt(10)));
      i += 1;
    }
    return localStringBuffer.toString();
  }
  
  private static GdiMeasureImpl a()
  {
    if (jdField_a_of_type_ComTencentCommonUtilsGdiMeasureImpl == null) {
      jdField_a_of_type_ComTencentCommonUtilsGdiMeasureImpl = new GdiMeasureImpl();
    }
    return jdField_a_of_type_ComTencentCommonUtilsGdiMeasureImpl;
  }
  
  private static Pattern a()
  {
    if (jdField_a_of_type_JavaUtilRegexPattern == null) {
      jdField_a_of_type_JavaUtilRegexPattern = Pattern.compile("(((https?://|ftp://)(?:(?:[-_0-9a-zA-Z.~!$&'\\(\\)*+,;=%]+\\.)+[-_0-9a-zA-Z.~!$&'\\(\\)*+,;=%]+))|(([-_0-9a-zA-Z.~!$&'\\(\\)*+,;=%]+@)?(?<![-_0-9a-zA-Z~$&'*+])www\\.([-_0-9a-zA-Z.~!$&'\\(\\)*+,;=%]+\\.)+[-_0-9a-zA-Z.~!$&'\\(\\)*+,;=%]+)|(([-_0-9a-zA-Z.~!$&'\\(\\)*+,;=%]+@)?(?<![-_0-9a-zA-Z~$&'*+])(([0-9a-zA-Z]|%\\d\\d)+\\.)*([0-9a-zA-Z]|%\\d\\d)+(\\.com\\b|\\.net\\b|\\.cn\\b|\\.org\\b))|(https?://|ftp://)((\\d{1,3}\\.){3}\\d{1,3}))(?:\\:\\d+)?(([/#][-_0-9a-zA-Z.~!$&'\\(\\)*+,;=:@/?#%\\|]*)|(\\?[-_0-9a-zA-Z.~!$&'\\(\\)*+,;=:@/?#%\\|]+))?");
    }
    return jdField_a_of_type_JavaUtilRegexPattern;
  }
  
  private static Pattern b()
  {
    if (b == null) {
      b = Pattern.compile("http://(.+)(\\.)(.+)[^\\w]*(.*)(\\.rmvb|\\.avi|\\.wmv|\\.mp4|\\.rm|\\.3gp|\\.mov|\\.asf|\\.mkv|\\.mpg|\\.mpeg|\\.m3u8|\\.f4v|\\.flv)");
    }
    return b;
  }
  
  public static int compareString(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      return -1;
    }
    if (paramString2 == null) {
      return 1;
    }
    int i = 0;
    for (;;)
    {
      if (paramString1.length() == i) {
        return -1;
      }
      if (paramString2.length() == i) {
        return 1;
      }
      if (paramString1.charAt(i) != paramString2.charAt(i))
      {
        boolean bool1 = isChinese(paramString1.charAt(i));
        boolean bool2 = isChinese(paramString2.charAt(i));
        if ((bool1) && (!bool2)) {
          return 1;
        }
        if ((!bool1) && (bool2)) {
          return -1;
        }
        return Collator.getInstance().compare(paramString1.substring(i), paramString2.substring(i));
      }
      i += 1;
    }
  }
  
  public static String convertString(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    paramString = paramString.toCharArray();
    int m = 0;
    int i1 = 0;
    int j = 0;
    int k;
    for (int n = 0; m < paramString.length; n = k)
    {
      switch (i1)
      {
      default: 
        break;
      case 6: 
        if ((paramString[m] != ' ') && (paramString[m] != '\r') && (paramString[m] != '\n') && (paramString[m] != '\t'))
        {
          m -= 1;
          i = 0;
          k = n;
        }
        break;
      case 5: 
        k = j;
        if (paramString[m] <= '7')
        {
          k = j;
          if (paramString[m] >= '0')
          {
            i = j + 1;
            if (j < 8)
            {
              k = n * 8 + (paramString[m] - '0');
              j = i;
              i = i1;
              break label2848;
            }
            k = i;
          }
        }
        if (paramString[m] == ';')
        {
          if (n <= 65535) {
            localStringBuffer.append((char)n);
          }
          i = 0;
          j = k;
          k = n;
        }
        else
        {
          localStringBuffer.append(paramString[m]);
          i = 0;
          j = k;
          k = n;
        }
        break;
      case 4: 
        k = j;
        if (paramString[m] <= '9')
        {
          k = j;
          if (paramString[m] >= '0')
          {
            i = j + 1;
            if (j < 5)
            {
              k = n * 10 + (paramString[m] - '0');
              j = i;
              i = i1;
              break label2848;
            }
            k = i;
          }
        }
        if (paramString[m] == ';')
        {
          if (n <= 65535) {
            localStringBuffer.append((char)n);
          }
          i = 0;
          j = k;
          k = n;
        }
        else
        {
          localStringBuffer.append(paramString[m]);
          i = 0;
          j = k;
          k = n;
        }
        break;
      case 3: 
        i = j;
        if (paramString[m] <= '9')
        {
          i = j;
          if (paramString[m] >= '0')
          {
            i = j + 1;
            if (j < 4)
            {
              k = n * 16 + (paramString[m] - '0');
              j = i;
              i = i1;
              break label2848;
            }
          }
        }
        k = i;
        if (paramString[m] <= 'F')
        {
          k = i;
          if (paramString[m] >= 'A')
          {
            j = i + 1;
            if (i < 4)
            {
              k = n * 16 + (paramString[m] - 'A' + 10);
              i = i1;
              break label2848;
            }
            k = j;
          }
        }
        j = k;
        if (paramString[m] <= 'f')
        {
          j = k;
          if (paramString[m] >= 'a')
          {
            i = k + 1;
            if (k < 4)
            {
              k = n * 16 + (paramString[m] - 'a' + 10);
              j = i;
              i = i1;
              break label2848;
            }
            j = i;
          }
        }
        if (paramString[m] == ';')
        {
          if (n <= 65535) {
            localStringBuffer.append((char)n);
          }
          i = 0;
          k = n;
        }
        else
        {
          localStringBuffer.append(paramString[m]);
          i = 0;
          k = n;
        }
        break;
      case 2: 
        if ((paramString[m] != 'x') && (paramString[m] != 'X'))
        {
          if ((paramString[m] != 'o') && (paramString[m] != 'O'))
          {
            if ((paramString[m] <= '9') && (paramString[m] >= '0'))
            {
              m -= 1;
              i = 4;
              k = n;
              break label2848;
            }
          }
          else
          {
            i = 5;
            k = n;
            break label2848;
          }
        }
        else
        {
          i = 3;
          k = n;
        }
        break;
      case 1: 
        if (paramString[m] == '#')
        {
          i = 2;
          j = 0;
          k = 0;
        }
        else
        {
          i = m + 4;
          if ((i <= paramString.length) && ((paramString[m] == 'a') || (paramString[m] == 'A')))
          {
            k = m + 1;
            if ((paramString[k] == 'm') || (paramString[k] == 'M'))
            {
              k = m + 2;
              if ((paramString[k] == 'p') || (paramString[k] == 'P'))
              {
                k = m + 3;
                if (paramString[k] == ';')
                {
                  localStringBuffer.append("&");
                  i = k;
                  break label2745;
                }
              }
            }
          }
          i1 = m + 3;
          if ((i1 <= paramString.length) && ((paramString[m] == 'l') || (paramString[m] == 'L')))
          {
            k = m + 1;
            if ((paramString[k] == 't') || (paramString[k] == 'T'))
            {
              k = m + 2;
              if (paramString[k] == ';')
              {
                localStringBuffer.append("<");
                i = k;
                break label2745;
              }
            }
          }
          if ((i1 <= paramString.length) && ((paramString[m] == 'g') || (paramString[m] == 'G')))
          {
            k = m + 1;
            if ((paramString[k] == 't') || (paramString[k] == 'T'))
            {
              k = m + 2;
              if (paramString[k] == ';')
              {
                localStringBuffer.append(">");
                i = k;
                break label2745;
              }
            }
          }
          k = m + 5;
          if ((k <= paramString.length) && ((paramString[m] == 'a') || (paramString[m] == 'A')))
          {
            i2 = m + 1;
            if ((paramString[i2] == 'p') || (paramString[i2] == 'P'))
            {
              i2 = m + 2;
              if (((paramString[i2] == 'o') || (paramString[i2] == 'O')) && ((paramString[i1] == 's') || (paramString[i1] == 'S')) && (paramString[i] == ';'))
              {
                localStringBuffer.append("'");
                break label2745;
              }
            }
          }
          if ((k <= paramString.length) && ((paramString[m] == 'q') || (paramString[m] == 'Q')))
          {
            i2 = m + 1;
            if ((paramString[i2] == 'u') || (paramString[i2] == 'U'))
            {
              i2 = m + 2;
              if (((paramString[i2] == 'o') || (paramString[i2] == 'O')) && ((paramString[i1] == 't') || (paramString[i1] == 'T')) && (paramString[i] == ';'))
              {
                localStringBuffer.append("\"");
                break label2745;
              }
            }
          }
          if ((k <= paramString.length) && ((paramString[m] == 'n') || (paramString[m] == 'N')))
          {
            i2 = m + 1;
            if ((paramString[i2] == 'b') || (paramString[i2] == 'B'))
            {
              i2 = m + 2;
              if (((paramString[i2] == 's') || (paramString[i2] == 'S')) && ((paramString[i1] == 'p') || (paramString[i1] == 'P')) && (paramString[i] == ';'))
              {
                localStringBuffer.append(" ");
                break label2745;
              }
            }
          }
          if ((i <= paramString.length) && ((paramString[m] == 'y') || (paramString[m] == 'Y')))
          {
            i2 = m + 1;
            if ((paramString[i2] == 'e') || (paramString[i2] == 'E'))
            {
              i2 = m + 2;
              if (((paramString[i2] == 'n') || (paramString[i2] == 'N')) && (paramString[i1] == ';'))
              {
                localStringBuffer.append("￥");
                i = i1;
                break label2745;
              }
            }
          }
          if ((k <= paramString.length) && ((paramString[m] == 'c') || (paramString[m] == 'C')))
          {
            i2 = m + 1;
            if ((paramString[i2] == 'o') || (paramString[i2] == 'O'))
            {
              i2 = m + 2;
              if (((paramString[i2] == 'p') || (paramString[i2] == 'P')) && ((paramString[i1] == 'y') || (paramString[i1] == 'Y')) && (paramString[i] == ';'))
              {
                localStringBuffer.append("©");
                break label2745;
              }
            }
          }
          int i2 = m + 6;
          int i3;
          if ((i2 <= paramString.length) && ((paramString[m] == 'l') || (paramString[m] == 'L')))
          {
            i3 = m + 1;
            if ((paramString[i3] == 'd') || (paramString[i3] == 'D'))
            {
              i3 = m + 2;
              if (((paramString[i3] == 'q') || (paramString[i3] == 'Q')) && ((paramString[i1] == 'u') || (paramString[i1] == 'U')) && ((paramString[i] == 'o') || (paramString[i] == 'O')) && (paramString[k] == ';'))
              {
                localStringBuffer.append("“");
                i = k;
                break label2745;
              }
            }
          }
          if ((i2 <= paramString.length) && ((paramString[m] == 'r') || (paramString[m] == 'R')))
          {
            i3 = m + 1;
            if ((paramString[i3] == 'd') || (paramString[i3] == 'D'))
            {
              i3 = m + 2;
              if (((paramString[i3] == 'q') || (paramString[i3] == 'Q')) && ((paramString[i1] == 'u') || (paramString[i1] == 'U')) && ((paramString[i] == 'o') || (paramString[i] == 'O')) && (paramString[k] == ';'))
              {
                localStringBuffer.append("”");
                i = k;
                break label2745;
              }
            }
          }
          if ((k <= paramString.length) && ((paramString[m] == 'u') || (paramString[m] == 'U')))
          {
            i3 = m + 1;
            if ((paramString[i3] == 'a') || (paramString[i3] == 'A'))
            {
              i3 = m + 2;
              if (paramString[i3] != 'r') {
                if (paramString[i3] != 'R') {
                  break label1851;
                }
              }
              if (((paramString[i1] == 'r') || (paramString[i1] == 'R')) && (paramString[i] == ';'))
              {
                localStringBuffer.append("↑");
                break label2745;
              }
            }
          }
          if ((k <= paramString.length) && ((paramString[m] == 'r') || (paramString[m] == 'R')))
          {
            i3 = m + 1;
            if ((paramString[i3] == 'a') || (paramString[i3] == 'A'))
            {
              i3 = m + 2;
              if (paramString[i3] != 'r') {
                if (paramString[i3] != 'R') {
                  break label1963;
                }
              }
              if (((paramString[i1] == 'r') || (paramString[i1] == 'R')) && (paramString[i] == ';'))
              {
                localStringBuffer.append("→");
                break label2745;
              }
            }
          }
          if ((k <= paramString.length) && ((paramString[m] == 'd') || (paramString[m] == 'D')))
          {
            i3 = m + 1;
            if ((paramString[i3] == 'a') || (paramString[i3] == 'A'))
            {
              i3 = m + 2;
              if (paramString[i3] != 'r') {
                if (paramString[i3] != 'R') {
                  break label2075;
                }
              }
              if (((paramString[i1] == 'r') || (paramString[i1] == 'R')) && (paramString[i] == ';'))
              {
                localStringBuffer.append("↓");
                break label2745;
              }
            }
          }
          if ((k <= paramString.length) && ((paramString[m] == 'l') || (paramString[m] == 'L')))
          {
            i3 = m + 1;
            if ((paramString[i3] == 'a') || (paramString[i3] == 'A'))
            {
              i3 = m + 2;
              if (paramString[i3] != 'r') {
                if (paramString[i3] != 'R') {
                  break label2187;
                }
              }
              if (((paramString[i1] == 'r') || (paramString[i1] == 'R')) && (paramString[i] == ';'))
              {
                localStringBuffer.append("←");
                break label2745;
              }
            }
          }
          if ((i2 <= paramString.length) && ((paramString[m] == 't') || (paramString[m] == 'T')))
          {
            i3 = m + 1;
            if ((paramString[i3] == 'r') || (paramString[i3] == 'R'))
            {
              i3 = m + 2;
              if (((paramString[i3] == 'a') || (paramString[i3] == 'A')) && ((paramString[i1] == 'd') || (paramString[i1] == 'D')) && ((paramString[i] == 'e') || (paramString[i] == 'E')) && (paramString[k] == ';'))
              {
                localStringBuffer.append("蒂");
                i = k;
                break label2745;
              }
            }
          }
          if ((i2 <= paramString.length) && ((paramString[m] == 'n') || (paramString[m] == 'N')))
          {
            i3 = m + 1;
            if ((paramString[i3] == 'd') || (paramString[i3] == 'D'))
            {
              i3 = m + 2;
              if (((paramString[i3] == 'a') || (paramString[i3] == 'A')) && ((paramString[i1] == 's') || (paramString[i1] == 'S')) && ((paramString[i] == 'h') || (paramString[i] == 'H')) && (paramString[k] == ';'))
              {
                localStringBuffer.append("–");
                i = k;
                break label2745;
              }
            }
          }
          if ((i2 <= paramString.length) && ((paramString[m] == 'm') || (paramString[m] == 'M')))
          {
            i3 = m + 1;
            if ((paramString[i3] == 'd') || (paramString[i3] == 'D'))
            {
              i3 = m + 2;
              if (((paramString[i3] == 'a') || (paramString[i3] == 'A')) && ((paramString[i1] == 's') || (paramString[i1] == 'S')) && ((paramString[i] == 'h') || (paramString[i] == 'H')) && (paramString[k] == ';'))
              {
                localStringBuffer.append("—");
                i = k;
                break label2745;
              }
            }
          }
          if ((m + 7 <= paramString.length) && ((paramString[m] == 'r') || (paramString[m] == 'R')))
          {
            i3 = m + 1;
            if ((paramString[i3] == 's') || (paramString[i3] == 'S'))
            {
              i3 = m + 2;
              if (((paramString[i3] == 'a') || (paramString[i3] == 'A')) && ((paramString[i1] == 'q') || (paramString[i1] == 'Q')) && ((paramString[i] == 'u') || (paramString[i] == 'U')) && ((paramString[k] == 'o') || (paramString[k] == 'O')) && (paramString[i2] == ';'))
              {
                localStringBuffer.append("›");
                i = i2;
                break label2745;
              }
            }
          }
          localStringBuffer.append(paramString[(m - 1)]);
          localStringBuffer.append(paramString[m]);
          i = m;
          k = 0;
          m = i;
          i = k;
          k = n;
        }
        break;
      case 0: 
        label1851:
        label1963:
        label2075:
        label2187:
        label2745:
        i = paramString[m];
        if ((i != 13) && (i != 32)) {
          if (i == 38) {}
        }
        switch (i)
        {
        default: 
          localStringBuffer.append(paramString[m]);
          break;
          i = 1;
          k = n;
          break;
        case 9: 
        case 10: 
          localStringBuffer.append(' ');
          i = 6;
          k = n;
        }
        break;
      }
      k = n;
      int i = i1;
      label2848:
      m += 1;
      i1 = i;
    }
    return localStringBuffer.toString();
  }
  
  public static String dataFormatTranslator(long paramLong, String paramString)
  {
    Date localDate = new Date();
    localDate.setTime(paramLong);
    paramString = new SimpleDateFormat(paramString);
    paramString.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    return paramString.format(localDate);
  }
  
  public static String enCrypto(String paramString1, String paramString2)
  {
    if ((TextUtils.isEmpty(paramString1)) || (!paramString1.startsWith("enCryped"))) {}
    for (;;)
    {
      int i;
      try
      {
        StringBuffer localStringBuffer = new StringBuffer();
        DESKeySpec localDESKeySpec = new DESKeySpec(paramString2.getBytes());
        StringBuilder localStringBuilder = null;
        try
        {
          paramString2 = SecretKeyFactory.getInstance("DES");
          try
          {
            Cipher localCipher = Cipher.getInstance("DES");
          }
          catch (NoSuchAlgorithmException localNoSuchAlgorithmException1) {}
          localNoSuchAlgorithmException2.printStackTrace();
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException2)
        {
          paramString2 = null;
        }
        Object localObject = localStringBuilder;
        ((Cipher)localObject).init(1, paramString2.generateSecret(localDESKeySpec));
        paramString2 = ((Cipher)localObject).doFinal(paramString1.getBytes());
        i = 0;
        if (i < paramString2.length)
        {
          localObject = Integer.toHexString(paramString2[i] & 0xFF);
          if (((String)localObject).length() == 1)
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("0");
            localStringBuilder.append((String)localObject);
            localStringBuffer.append(localStringBuilder.toString());
            break label207;
          }
          localStringBuffer.append((String)localObject);
          break label207;
        }
        paramString2 = new StringBuilder();
        paramString2.append("enCryped");
        paramString2.append(localStringBuffer.toString().toUpperCase());
        paramString2 = paramString2.toString();
        return paramString2;
      }
      catch (Exception paramString2)
      {
        return paramString1;
      }
      return paramString1;
      return paramString1;
      label207:
      i += 1;
    }
  }
  
  public static String formatDownloadCount(long paramLong)
  {
    if (paramLong < 0L) {
      return "";
    }
    StringBuilder localStringBuilder;
    if (paramLong < 10000L)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramLong);
      localStringBuilder.append("");
      return localStringBuilder.toString();
    }
    if (paramLong < 100000000L)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramLong / 10000L);
      localStringBuilder.append("万");
      return localStringBuilder.toString();
    }
    return String.format("%.2f亿", new Object[] { Float.valueOf((float)paramLong / 1.0E8F) });
  }
  
  public static String[] getAttributeFromTagA(String paramString)
  {
    if ((!isEmpty(paramString)) && (paramString.contains("<a")))
    {
      if (!paramString.contains("/a>")) {
        return null;
      }
      try
      {
        int i = paramString.indexOf("<a");
        int j = paramString.indexOf("/a>");
        if ((i != -1) && (j != -1))
        {
          paramString = paramString.substring(i, j + 3);
          i = paramString.indexOf("href=");
          if (i <= 0) {
            return null;
          }
          String str = paramString.substring(i);
          i = str.indexOf(">");
          if (i <= 0) {
            return null;
          }
          paramString = new String[2];
          paramString[0] = "";
          paramString[1] = "";
          paramString[0] = str.substring(6, i - 1);
          str = str.substring(i);
          i = str.indexOf("<");
          if (i <= 0) {
            return null;
          }
          paramString[1] = str.substring(1, i);
          return paramString;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
      return null;
    }
    return null;
  }
  
  public static String getDownloadSizeString(long paramLong)
  {
    if (paramLong <= 0L) {
      return "--";
    }
    return getSizeString((float)paramLong, 1);
  }
  
  public static String getExceptionTrace(Throwable paramThrowable)
  {
    Object localObject = "";
    try
    {
      String str = "".concat(paramThrowable.toString());
      localObject = str;
      StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
      localObject = str;
      if (arrayOfStackTraceElement == null) {
        break label96;
      }
      localObject = str;
      int j = arrayOfStackTraceElement.length;
      int i = 0;
      for (paramThrowable = str;; paramThrowable = (Throwable)localObject)
      {
        localObject = paramThrowable;
        if (i >= j) {
          break;
        }
        str = arrayOfStackTraceElement[i];
        localObject = paramThrowable;
        if (str != null)
        {
          localObject = paramThrowable;
          paramThrowable = paramThrowable.concat(str.toString());
          localObject = paramThrowable;
        }
        i += 1;
      }
    }
    catch (Throwable paramThrowable)
    {
      label96:
      for (;;) {}
    }
    localObject = ((String)localObject).concat("getstacktraceerror");
    return (String)localObject;
  }
  
  public static String getFileSizeString(long paramLong)
  {
    if (paramLong <= 0L) {
      return "0B";
    }
    return getDownloadSizeString(paramLong);
  }
  
  public static ArrayList<String> getQvodUrl(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    paramString = b().matcher(paramString);
    while (paramString.find()) {
      localArrayList.add(paramString.group());
    }
    return localArrayList;
  }
  
  public static String getQvodVideoName(String paramString)
  {
    if (paramString != null)
    {
      paramString = paramString.split("\\|");
      if (paramString.length >= 3)
      {
        paramString = paramString[2];
        if ((paramString.length() > 0) && (!paramString.contains("\\u")) && (!paramString.contains("%"))) {
          return paramString;
        }
      }
    }
    return null;
  }
  
  public static String getSaveFlowSizeString(long paramLong)
  {
    if (paramLong <= 0L) {
      return "";
    }
    return getSizeString((float)paramLong);
  }
  
  public static String getSignificantFigure(float paramFloat, int paramInt)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("%.");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append("f");
    localObject = String.format(((StringBuilder)localObject).toString(), new Object[] { Float.valueOf(paramFloat) });
    int j = ((String)localObject).indexOf('.');
    int i = j;
    if (j < paramInt) {
      i = paramInt + 1;
    }
    return ((String)localObject).substring(0, i);
  }
  
  public static String getSizeString(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      return "未知";
    }
    if (paramFloat < 1024.0F) {
      return String.format("%dB", new Object[] { Integer.valueOf((int)paramFloat) });
    }
    if (paramFloat < 1048576.0F) {
      return String.format("%.1fKB", new Object[] { Float.valueOf(paramFloat / 1024.0F) });
    }
    if (paramFloat < 1.07374182E9F) {
      return String.format("%.1fMB", new Object[] { Float.valueOf(paramFloat / 1048576.0F) });
    }
    return String.format("%.1fGB", new Object[] { Float.valueOf(paramFloat / 1.07374182E9F) });
  }
  
  public static String getSizeString(float paramFloat, int paramInt)
  {
    if (paramFloat < 0.0F) {
      return "未知";
    }
    if (paramFloat < 1024.0F)
    {
      localStringBuilder = new StringBuilder();
      f = paramInt;
      localStringBuilder.append((int)(paramFloat * 10.0F * f) / (f * 10.0F));
      localStringBuilder.append("B");
      return localStringBuilder.toString();
    }
    if (paramFloat < 1048576.0F)
    {
      localStringBuilder = new StringBuilder();
      paramFloat /= 1024.0F;
      f = paramInt;
      localStringBuilder.append((int)(paramFloat * 10.0F * f) / (f * 10.0F));
      localStringBuilder.append("KB");
      return localStringBuilder.toString();
    }
    if (paramFloat < 1.07374182E9F)
    {
      localStringBuilder = new StringBuilder();
      paramFloat /= 1048576.0F;
      f = paramInt;
      localStringBuilder.append((int)(paramFloat * 10.0F * f) / (f * 10.0F));
      localStringBuilder.append("MB");
      return localStringBuilder.toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    paramFloat /= 1.07374182E9F;
    float f = paramInt;
    localStringBuilder.append((int)(paramFloat * 10.0F * f) / (f * 10.0F));
    localStringBuilder.append("GB");
    return localStringBuilder.toString();
  }
  
  public static String getSizeString(long paramLong)
  {
    return getSizeString((float)paramLong);
  }
  
  public static String getSizeStringByPrecision(float paramFloat, int paramInt)
  {
    if ((paramFloat >= 0.0F) && (paramInt >= 0))
    {
      if (paramFloat < 1024.0F) {
        return String.format("%dB", new Object[] { Integer.valueOf((int)paramFloat) });
      }
      if (paramFloat < 1048576.0F)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("%.");
        localStringBuilder.append(paramInt);
        localStringBuilder.append("fKB");
        return String.format(localStringBuilder.toString(), new Object[] { Float.valueOf(paramFloat / 1024.0F) });
      }
      if (paramFloat < 1.07374182E9F)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("%.");
        localStringBuilder.append(paramInt);
        localStringBuilder.append("fMB");
        return String.format(localStringBuilder.toString(), new Object[] { Float.valueOf(paramFloat / 1048576.0F) });
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("%.");
      localStringBuilder.append(paramInt);
      localStringBuilder.append("fGB");
      return String.format(localStringBuilder.toString(), new Object[] { Float.valueOf(paramFloat / 1.07374182E9F) });
    }
    return "未知";
  }
  
  public static String getSizeStringFixK(float paramFloat)
  {
    if ((paramFloat < 1048576.0F) && (paramFloat > 1024.0F)) {
      return String.format("%.1fKB", new Object[] { Float.valueOf(paramFloat / 1024.0F) });
    }
    return getSizeString(paramFloat);
  }
  
  public static String getSizeStringWithoutB(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      return "未知";
    }
    if (paramFloat < 1024.0F) {
      return "0K";
    }
    if (paramFloat < 1048576.0F) {
      return String.format("%.1fKB", new Object[] { Float.valueOf(paramFloat / 1024.0F) });
    }
    if (paramFloat < 1.07374182E9F) {
      return String.format("%.1fMB", new Object[] { Float.valueOf(paramFloat / 1048576.0F) });
    }
    return String.format("%.1fGB", new Object[] { Float.valueOf(paramFloat / 1.07374182E9F) });
  }
  
  public static String getSizeUnit(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      return "";
    }
    if (paramFloat < 1024.0F) {
      return "B";
    }
    if (paramFloat < 1048576.0F) {
      return "KB";
    }
    if (paramFloat < 1.07374182E9F) {
      return "MB";
    }
    return "GB";
  }
  
  public static String getSizeWithoutUnit(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      return "未知";
    }
    if (paramFloat < 1024.0F) {
      return "0";
    }
    if (paramFloat < 1048576.0F) {
      return String.format("%.2f", new Object[] { Float.valueOf(paramFloat / 1024.0F) });
    }
    if (paramFloat < 1.07374182E9F) {
      return String.format("%.2f", new Object[] { Float.valueOf(paramFloat / 1048576.0F) });
    }
    return String.format("%.2f", new Object[] { Float.valueOf(paramFloat / 1.07374182E9F) });
  }
  
  public static String getSkinSizeString(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      return "未知";
    }
    if (paramFloat < 1024.0F) {
      return String.format("%dB", new Object[] { Integer.valueOf((int)paramFloat) });
    }
    if (paramFloat < 1048576.0F) {
      return String.format("%.0fKB", new Object[] { Float.valueOf(paramFloat / 1024.0F) });
    }
    if (paramFloat < 1.07374182E9F) {
      return String.format("%.1fMB", new Object[] { Float.valueOf(paramFloat / 1048576.0F) });
    }
    return String.format("%.1fGB", new Object[] { Float.valueOf(paramFloat / 1.07374182E9F) });
  }
  
  public static String getSpeedString(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      return String.format("%dB/S", new Object[] { Integer.valueOf(0) });
    }
    if (paramFloat < 1024.0F) {
      return String.format("%dB/S", new Object[] { Integer.valueOf((int)paramFloat) });
    }
    if (paramFloat < 1048576.0F) {
      return String.format("%.2fKB/S", new Object[] { Float.valueOf(paramFloat / 1024.0F) });
    }
    if (paramFloat < 1.07374182E9F) {
      return String.format("%.2fMB/S", new Object[] { Float.valueOf(paramFloat / 1048576.0F) });
    }
    return String.format("%.2fMB/S", new Object[] { Float.valueOf(paramFloat / 1.07374182E9F) });
  }
  
  public static String getSpeedString(float paramFloat, int paramInt)
  {
    if (paramFloat < 0.0F) {
      return "0B/S";
    }
    if (paramFloat < 1024.0F)
    {
      localStringBuilder = new StringBuilder();
      f = paramInt;
      localStringBuilder.append((int)(paramFloat * 10.0F * f) / (f * 10.0F));
      localStringBuilder.append("B/S");
      return localStringBuilder.toString();
    }
    if (paramFloat < 1048576.0F)
    {
      localStringBuilder = new StringBuilder();
      paramFloat /= 1024.0F;
      f = paramInt;
      localStringBuilder.append((int)(paramFloat * 10.0F * f) / (f * 10.0F));
      localStringBuilder.append("KB/S");
      return localStringBuilder.toString();
    }
    if (paramFloat < 1.07374182E9F)
    {
      localStringBuilder = new StringBuilder();
      paramFloat /= 1048576.0F;
      f = paramInt;
      localStringBuilder.append((int)(paramFloat * 10.0F * f) / (f * 10.0F));
      localStringBuilder.append("MB/S");
      return localStringBuilder.toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    paramFloat /= 1.07374182E9F;
    float f = paramInt;
    localStringBuilder.append((int)(paramFloat * 10.0F * f) / (f * 10.0F));
    localStringBuilder.append("GB/S");
    return localStringBuilder.toString();
  }
  
  public static int getStringHeight(int paramInt)
  {
    GdiMeasureImpl localGdiMeasureImpl = new GdiMeasureImpl();
    localGdiMeasureImpl.setFontSize(paramInt);
    QSize localQSize = new QSize();
    localGdiMeasureImpl.getStringWidthHeight("高度", localQSize);
    return localQSize.mHeight;
  }
  
  public static ArrayList<String> getStringUrl(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    paramString = a().matcher(paramString);
    while (paramString.find()) {
      localArrayList.add(paramString.group());
    }
    return localArrayList;
  }
  
  public static int getStringWidth(String paramString, int paramInt)
  {
    if (isEmpty(paramString)) {
      return 0;
    }
    GdiMeasureImpl localGdiMeasureImpl = a();
    localGdiMeasureImpl.setFontSize(paramInt);
    return localGdiMeasureImpl.getStringWidthInt(paramString);
  }
  
  public static int getTruncatPos(String paramString, int paramInt1, int paramInt2)
  {
    if ((!isEmpty(paramString)) && (paramInt2 >= 1))
    {
      GdiMeasureImpl localGdiMeasureImpl = a();
      localGdiMeasureImpl.setFontSize(paramInt1);
      return localGdiMeasureImpl.breakText(paramString, paramInt2, null);
    }
    return 0;
  }
  
  /* Error */
  public static String getUrlFromLocalFile(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 287	com/tencent/common/utils/StringUtils:isEmpty	(Ljava/lang/String;)Z
    //   4: ifne +345 -> 349
    //   7: aload_0
    //   8: ldc_w 486
    //   11: invokevirtual 489	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   14: ifeq +335 -> 349
    //   17: aload_0
    //   18: ldc_w 491
    //   21: invokevirtual 205	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   24: ifne +5 -> 29
    //   27: aload_0
    //   28: areturn
    //   29: aload_0
    //   30: bipush 8
    //   32: invokevirtual 108	java/lang/String:substring	(I)Ljava/lang/String;
    //   35: ldc_w 493
    //   38: ldc_w 495
    //   41: invokevirtual 498	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   44: astore 4
    //   46: aload 4
    //   48: ldc_w 500
    //   51: invokestatic 505	java/net/URLDecoder:decode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   54: astore 5
    //   56: aload 5
    //   58: astore 4
    //   60: goto +21 -> 81
    //   63: ldc 15
    //   65: ldc_w 507
    //   68: invokestatic 513	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   71: goto +10 -> 81
    //   74: astore 5
    //   76: aload 5
    //   78: invokevirtual 514	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   81: aload 4
    //   83: ifnonnull +5 -> 88
    //   86: aload_0
    //   87: areturn
    //   88: new 516	java/io/File
    //   91: dup
    //   92: aload 4
    //   94: invokespecial 517	java/io/File:<init>	(Ljava/lang/String;)V
    //   97: astore 5
    //   99: aload 5
    //   101: invokevirtual 520	java/io/File:exists	()Z
    //   104: ifne +5 -> 109
    //   107: aload_0
    //   108: areturn
    //   109: aconst_null
    //   110: astore 4
    //   112: aload 5
    //   114: invokestatic 526	com/tencent/common/utils/FileUtilsF:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   117: astore 5
    //   119: aload 5
    //   121: astore 4
    //   123: new 528	java/io/BufferedReader
    //   126: dup
    //   127: new 530	java/io/InputStreamReader
    //   130: dup
    //   131: aload 5
    //   133: invokespecial 533	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   136: invokespecial 536	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   139: invokevirtual 539	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   142: astore 6
    //   144: aload 5
    //   146: astore 4
    //   148: aload 6
    //   150: invokestatic 287	com/tencent/common/utils/StringUtils:isEmpty	(Ljava/lang/String;)Z
    //   153: ifne +97 -> 250
    //   156: aload 5
    //   158: astore 4
    //   160: aload 6
    //   162: ldc 125
    //   164: invokevirtual 298	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   167: istore_1
    //   168: aload 5
    //   170: astore 4
    //   172: aload 6
    //   174: ldc 127
    //   176: invokevirtual 298	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   179: istore_2
    //   180: iload_1
    //   181: iconst_m1
    //   182: if_icmpeq +68 -> 250
    //   185: iload_2
    //   186: iconst_m1
    //   187: if_icmpeq +63 -> 250
    //   190: iload_2
    //   191: iload_1
    //   192: if_icmple +58 -> 250
    //   195: aload 5
    //   197: astore 4
    //   199: aload 6
    //   201: iload_1
    //   202: iconst_1
    //   203: iadd
    //   204: iload_2
    //   205: invokevirtual 301	java/lang/String:substring	(II)Ljava/lang/String;
    //   208: astore 6
    //   210: aload 5
    //   212: astore 4
    //   214: aload 6
    //   216: invokestatic 287	com/tencent/common/utils/StringUtils:isEmpty	(Ljava/lang/String;)Z
    //   219: ifne +31 -> 250
    //   222: aload 5
    //   224: astore 4
    //   226: aload 6
    //   228: ldc_w 541
    //   231: invokevirtual 205	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   234: istore_3
    //   235: iload_3
    //   236: ifeq +14 -> 250
    //   239: aload 5
    //   241: astore 4
    //   243: aload 6
    //   245: astore 5
    //   247: goto +27 -> 274
    //   250: aload_0
    //   251: astore 6
    //   253: aload 5
    //   255: astore 4
    //   257: aload 6
    //   259: astore 5
    //   261: goto +13 -> 274
    //   264: astore 5
    //   266: aload 5
    //   268: invokevirtual 542	java/io/IOException:printStackTrace	()V
    //   271: aload_0
    //   272: astore 5
    //   274: aload 4
    //   276: ifnull +18 -> 294
    //   279: aload 4
    //   281: invokevirtual 547	java/io/FileInputStream:close	()V
    //   284: goto +10 -> 294
    //   287: astore 4
    //   289: aload 4
    //   291: invokevirtual 542	java/io/IOException:printStackTrace	()V
    //   294: new 248	java/lang/StringBuilder
    //   297: dup
    //   298: invokespecial 249	java/lang/StringBuilder:<init>	()V
    //   301: astore 4
    //   303: aload 4
    //   305: ldc_w 549
    //   308: invokevirtual 254	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   311: pop
    //   312: aload 4
    //   314: aload_0
    //   315: invokevirtual 254	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: pop
    //   319: aload 4
    //   321: ldc_w 551
    //   324: invokevirtual 254	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   327: pop
    //   328: aload 4
    //   330: aload 5
    //   332: invokevirtual 254	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   335: pop
    //   336: ldc 15
    //   338: aload 4
    //   340: invokevirtual 255	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   343: invokestatic 513	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   346: aload 5
    //   348: areturn
    //   349: aload_0
    //   350: areturn
    //   351: astore 5
    //   353: goto -290 -> 63
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	356	0	paramString	String
    //   167	37	1	i	int
    //   179	26	2	j	int
    //   234	2	3	bool	boolean
    //   44	236	4	localObject1	Object
    //   287	3	4	localIOException1	java.io.IOException
    //   301	38	4	localStringBuilder	StringBuilder
    //   54	3	5	str1	String
    //   74	3	5	localUnsupportedEncodingException	java.io.UnsupportedEncodingException
    //   97	163	5	localObject2	Object
    //   264	3	5	localIOException2	java.io.IOException
    //   272	75	5	str2	String
    //   351	1	5	localException	Exception
    //   142	116	6	str3	String
    // Exception table:
    //   from	to	target	type
    //   46	56	74	java/io/UnsupportedEncodingException
    //   112	119	264	java/io/IOException
    //   123	144	264	java/io/IOException
    //   148	156	264	java/io/IOException
    //   160	168	264	java/io/IOException
    //   172	180	264	java/io/IOException
    //   199	210	264	java/io/IOException
    //   214	222	264	java/io/IOException
    //   226	235	264	java/io/IOException
    //   279	284	287	java/io/IOException
    //   46	56	351	java/lang/Exception
  }
  
  public static String getWrapString(String paramString, int paramInt1, int paramInt2, TextUtils.TruncateAt paramTruncateAt)
  {
    if (isEmpty(paramString)) {
      return paramString;
    }
    int i;
    int j;
    if (paramTruncateAt == TextUtils.TruncateAt.MIDDLE)
    {
      if (paramInt2 < 1) {
        return paramString;
      }
      paramTruncateAt = new float[paramString.length()];
      TextPaint localTextPaint = new TextPaint();
      localTextPaint.setTextSize(paramInt1);
      localTextPaint.getTextWidths(paramString, paramTruncateAt);
      int m = paramTruncateAt.length;
      int k = getStringWidth("...", paramInt1);
      i = m - 1;
      for (paramInt1 = 0;; paramInt1 = j)
      {
        j = paramInt1;
        if (paramInt1 > i) {
          break;
        }
        k = (int)(k + paramTruncateAt[paramInt1]);
        if (k > paramInt2)
        {
          j = paramInt1;
          break;
        }
        j = paramInt1 + 1;
        paramInt1 = k;
        if (j <= i)
        {
          k = (int)(k + paramTruncateAt[i]);
          paramInt1 = k;
          if (k > paramInt2) {
            break;
          }
        }
        i -= 1;
        k = paramInt1;
      }
      if ((j <= i) && (j >= 1))
      {
        if (i > m - 2) {
          return paramString;
        }
        paramTruncateAt = new StringBuilder();
        paramTruncateAt.append(paramString.substring(0, j - 1));
        paramTruncateAt.append("...");
        paramTruncateAt.append(paramString.substring(i + 1));
        return paramTruncateAt.toString();
      }
      return paramString;
    }
    if (paramTruncateAt == TextUtils.TruncateAt.END)
    {
      j = getTruncatPos(paramString, paramInt1, paramInt2);
      i = j;
      if (j >= paramString.length()) {
        return paramString;
      }
      while (i > 0)
      {
        paramTruncateAt = new StringBuilder();
        paramTruncateAt.append(paramString.substring(0, i));
        paramTruncateAt.append("...");
        if (getStringWidth(paramTruncateAt.toString(), paramInt1) < paramInt2) {
          break;
        }
        i -= 1;
      }
      paramTruncateAt = new StringBuilder();
      paramTruncateAt.append(paramString.substring(0, i));
      paramTruncateAt.append("...");
      return paramTruncateAt.toString();
    }
    return paramString;
  }
  
  public static boolean haveChineseChar(String paramString)
  {
    if (isEmpty(paramString)) {
      return false;
    }
    int j = paramString.length();
    int i = 0;
    while (i < j)
    {
      int k = paramString.charAt(i);
      if (((k >= 19968) && (k <= 40959)) || ((k >= 65072) && (k <= 65440))) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public static String intsToString(ArrayList<Integer> paramArrayList)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    paramArrayList = paramArrayList.iterator();
    while (paramArrayList.hasNext())
    {
      Integer localInteger = (Integer)paramArrayList.next();
      if (localStringBuilder1.length() != 0) {
        localStringBuilder1.append(',');
      }
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(localInteger);
      localStringBuilder2.append("");
      localStringBuilder1.append(localStringBuilder2.toString());
    }
    return localStringBuilder1.toString();
  }
  
  public static boolean isCanPrintString(String paramString)
  {
    if (isEmpty(paramString)) {
      return false;
    }
    int i = paramString.length() - 1;
    while (i >= 0)
    {
      int j = paramString.charAt(i);
      if (((j < 19968) || (j > 40959)) && ((j < 65072) || (j > 65440)) && ((j < 97) || (j > 122)) && ((j < 65) || (j > 90)))
      {
        if (j < 48) {
          break label99;
        }
        if (j > 57) {
          return false;
        }
      }
      i -= 1;
      continue;
      label99:
      return false;
    }
    return true;
  }
  
  public static boolean isChinese(char paramChar)
  {
    return ((paramChar >= '一') && (paramChar <= 40959)) || ((paramChar >= 65072) && (paramChar <= 65440));
  }
  
  @Deprecated
  public static boolean isEmpty(String paramString)
  {
    return (paramString == null) || ("".equals(paramString.trim()));
  }
  
  public static boolean isEnglishWord(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    int j = paramString.length();
    int i = 0;
    while (i < j)
    {
      if (paramString.charAt(i) > '') {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  public static boolean isIPAddress(String paramString)
  {
    if (paramString != null) {
      if (paramString.length() < 8) {
        return false;
      }
    }
    for (;;)
    {
      int i;
      try
      {
        paramString = paramString.split("\\.", 5);
        j = paramString.length;
        if (j == 4) {
          break label75;
        }
        return false;
      }
      catch (Exception paramString)
      {
        int j;
        int k;
        return false;
      }
      if (i < j)
      {
        k = Integer.parseInt(paramString[i]);
        if (k <= 255)
        {
          if (k < 0) {
            return false;
          }
          i += 1;
        }
        else
        {
          return false;
        }
      }
      else
      {
        return true;
        return false;
        label75:
        i = 0;
      }
    }
  }
  
  public static boolean isNumeric(String paramString)
  {
    if (isEmpty(paramString)) {
      return false;
    }
    int i = paramString.length();
    int j;
    do
    {
      i -= 1;
      if (i < 0) {
        break;
      }
      j = paramString.charAt(i);
    } while ((j >= 48) && (j <= 57));
    return false;
    return true;
  }
  
  public static boolean isStringEqual(String paramString1, String paramString2)
  {
    return ((isEmpty(paramString1)) && (isEmpty(paramString2))) || ((paramString1 != null) && (paramString1.equals(paramString2)));
  }
  
  public static boolean isStringEqualsIgnoreCase(String paramString1, String paramString2)
  {
    return ((isEmpty(paramString1)) && (isEmpty(paramString2))) || ((paramString1 != null) && (paramString1.equalsIgnoreCase(paramString2)));
  }
  
  public static byte parseByte(String paramString, byte paramByte)
  {
    try
    {
      byte b1 = Byte.parseByte(paramString);
      return b1;
    }
    catch (Exception paramString) {}
    return paramByte;
  }
  
  public static float parseFloat(String paramString, float paramFloat)
  {
    try
    {
      float f = Float.parseFloat(paramString);
      return f;
    }
    catch (Exception paramString) {}
    return paramFloat;
  }
  
  public static int parseInt(String paramString, int paramInt)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      return i;
    }
    catch (Exception paramString) {}
    return paramInt;
  }
  
  public static ArrayList<Integer> parseInts(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    if (isEmpty(paramString)) {
      return localArrayList;
    }
    paramString = paramString.split(",");
    int j = paramString.length;
    int i = 0;
    for (;;)
    {
      String str;
      if (i < j) {
        str = paramString[i];
      }
      try
      {
        localArrayList.add(Integer.valueOf(str));
        i += 1;
        continue;
        return localArrayList;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
    }
  }
  
  public static long parseLong(String paramString, long paramLong)
  {
    try
    {
      long l = Long.parseLong(paramString);
      return l;
    }
    catch (Exception paramString) {}
    return paramLong;
  }
  
  public static short parseShort(String paramString, int paramInt)
  {
    try
    {
      short s = Short.parseShort(paramString);
      return s;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return (short)paramInt;
  }
  
  public static ArrayList<String> removeEmptyStr(ArrayList<String> paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    paramArrayList = paramArrayList.iterator();
    while (paramArrayList.hasNext())
    {
      String str = (String)paramArrayList.next();
      if (!isEmpty(str)) {
        localArrayList.add(str);
      }
    }
    return localArrayList;
  }
  
  public static String removeHeadSpace(String paramString)
  {
    while (paramString.startsWith(" ")) {
      paramString = paramString.substring(1);
    }
    return paramString;
  }
  
  public static String removeNextLine(String paramString)
  {
    if (isEmpty(paramString)) {
      return paramString;
    }
    paramString = Pattern.compile("\r|\n").matcher(paramString).replaceAll(" ");
    FLogger.d("StringUtil", paramString);
    return paramString;
  }
  
  public static String replace(String paramString1, String paramString2, String paramString3)
  {
    return Pattern.compile("\r|\n").matcher(paramString1).replaceAll(paramString3);
  }
  
  public static void replaceAndDye(SpannableStringBuilder paramSpannableStringBuilder, String paramString1, String paramString2, int paramInt)
  {
    int i = paramSpannableStringBuilder.toString().indexOf(paramString1);
    if (i >= 0)
    {
      paramSpannableStringBuilder.replace(i, paramString1.length() + i, paramString2);
      int j = paramString2.length();
      paramSpannableStringBuilder.setSpan(new ForegroundColorSpan(paramInt), i, j + i, 18);
    }
  }
  
  public static String restoreQuotation(String paramString)
  {
    if (isEmpty(paramString)) {
      return paramString;
    }
    paramString = Pattern.compile("%22").matcher(paramString).replaceAll("\"");
    FLogger.d("StringUtil", paramString);
    return paramString;
  }
  
  public static String[] splitStringLines(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    paramString = new BufferedReader(new StringReader(paramString), 2048);
    ArrayList localArrayList = new ArrayList();
    try
    {
      try
      {
        for (;;)
        {
          String str = paramString.readLine();
          if (str == null) {
            break;
          }
          localArrayList.add(str);
        }
        if (!localArrayList.isEmpty()) {
          break label91;
        }
      }
      catch (Throwable localThrowable)
      {
        try
        {
          paramString.close();
        }
        catch (Throwable paramString)
        {
          paramString.printStackTrace();
        }
        localThrowable = localThrowable;
        localThrowable.printStackTrace();
        paramString.close();
      }
      return null;
    }
    finally
    {
      try
      {
        label91:
        paramString.close();
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
      }
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public static String stringForTime(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Formatter localFormatter = new Formatter(localStringBuilder, Locale.getDefault());
    int j = paramInt / 1000;
    paramInt = j % 60;
    int i = j / 60 % 60;
    j /= 3600;
    localStringBuilder.setLength(0);
    if (j <= 0) {
      return localFormatter.format("%02d:%02d", new Object[] { Integer.valueOf(i), Integer.valueOf(paramInt) }).toString();
    }
    return localFormatter.format("%02d:%02d:%02d", new Object[] { Integer.valueOf(j), Integer.valueOf(i), Integer.valueOf(paramInt) }).toString();
  }
  
  public static String textCutoff(String paramString, Paint paramPaint, int paramInt)
  {
    if ((!isEmpty(paramString)) && (paramPaint != null))
    {
      if (paramInt <= 0) {
        return paramString;
      }
      for (float f = paramPaint.measureText(paramString); f > paramInt; f = paramPaint.measureText(paramString)) {
        paramString = paramString.substring(0, paramString.length() - 1);
      }
      return paramString;
    }
    return paramString;
  }
  
  public static String textCutoffNew(String paramString, int paramInt1, int paramInt2)
  {
    if (isEmpty(paramString)) {
      return paramString;
    }
    paramInt1 = getTruncatPos(paramString, paramInt1, paramInt2);
    if (paramInt1 >= paramString.length()) {
      return paramString;
    }
    return paramString.substring(0, paramInt1);
  }
  
  public static String trunc(String paramString, int paramInt)
  {
    if (paramString != null)
    {
      if (paramString.length() * 2 <= paramInt) {
        return paramString;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      int k = paramString.length();
      int i = 0;
      while (i < k)
      {
        char c = paramString.charAt(i);
        localStringBuilder.append(c);
        int j = paramInt - 1;
        paramInt = j;
        if (isChinese(c)) {
          paramInt = j - 1;
        }
        if (paramInt <= 0) {
          break;
        }
        i += 1;
      }
      return localStringBuilder.toString();
    }
    return paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */