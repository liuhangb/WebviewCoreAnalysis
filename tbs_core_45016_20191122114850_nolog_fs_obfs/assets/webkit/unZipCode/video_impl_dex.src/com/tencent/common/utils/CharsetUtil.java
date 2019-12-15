package com.tencent.common.utils;

import com.tencent.basesupport.FLogger;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class CharsetUtil
{
  public static final int CR = 13;
  public static final String CRLF = "\r\n";
  public static final Charset DEFAULT_CHARSET;
  public static final int HT = 9;
  public static final Charset ISO_8859_1;
  public static final int LF = 10;
  public static final int SP = 32;
  public static final Charset US_ASCII;
  public static final Charset UTF_8;
  static Map<String, a> jdField_a_of_type_JavaUtilMap;
  static SortedSet<String> jdField_a_of_type_JavaUtilSortedSet;
  static a[] jdField_a_of_type_ArrayOfComTencentCommonUtilsCharsetUtil$a = { new a("ISO8859_1", "ISO-8859-1", new String[] { "ISO_8859-1:1987", "iso-ir-100", "ISO_8859-1", "latin1", "l1", "IBM819", "CP819", "csISOLatin1", "8859_1", "819", "IBM-819", "ISO8859-1", "ISO_8859_1" }, null), new a("ISO8859_2", "ISO-8859-2", new String[] { "ISO_8859-2:1987", "iso-ir-101", "ISO_8859-2", "latin2", "l2", "csISOLatin2", "8859_2", "iso8859_2" }, null), new a("ISO8859_3", "ISO-8859-3", new String[] { "ISO_8859-3:1988", "iso-ir-109", "ISO_8859-3", "latin3", "l3", "csISOLatin3", "8859_3" }, null), new a("ISO8859_4", "ISO-8859-4", new String[] { "ISO_8859-4:1988", "iso-ir-110", "ISO_8859-4", "latin4", "l4", "csISOLatin4", "8859_4" }, null), new a("ISO8859_5", "ISO-8859-5", new String[] { "ISO_8859-5:1988", "iso-ir-144", "ISO_8859-5", "cyrillic", "csISOLatinCyrillic", "8859_5" }, null), new a("ISO8859_6", "ISO-8859-6", new String[] { "ISO_8859-6:1987", "iso-ir-127", "ISO_8859-6", "ECMA-114", "ASMO-708", "arabic", "csISOLatinArabic", "8859_6" }, null), new a("ISO8859_7", "ISO-8859-7", new String[] { "ISO_8859-7:1987", "iso-ir-126", "ISO_8859-7", "ELOT_928", "ECMA-118", "greek", "greek8", "csISOLatinGreek", "8859_7", "sun_eu_greek" }, null), new a("ISO8859_8", "ISO-8859-8", new String[] { "ISO_8859-8:1988", "iso-ir-138", "ISO_8859-8", "hebrew", "csISOLatinHebrew", "8859_8" }, null), new a("ISO8859_9", "ISO-8859-9", new String[] { "ISO_8859-9:1989", "iso-ir-148", "ISO_8859-9", "latin5", "l5", "csISOLatin5", "8859_9" }, null), new a("ISO8859_13", "ISO-8859-13", new String[0], null), new a("ISO8859_15", "ISO-8859-15", new String[] { "ISO_8859-15", "Latin-9", "8859_15", "csISOlatin9", "IBM923", "cp923", "923", "L9", "IBM-923", "ISO8859-15", "LATIN9", "LATIN0", "csISOlatin0", "ISO8859_15_FDIS" }, null), new a("KOI8_R", "KOI8-R", new String[] { "csKOI8R", "koi8" }, null), new a("ASCII", "US-ASCII", new String[] { "ANSI_X3.4-1968", "iso-ir-6", "ANSI_X3.4-1986", "ISO_646.irv:1991", "ISO646-US", "us", "IBM367", "cp367", "csASCII", "ascii7", "646", "iso_646.irv:1983" }, null), new a("UTF8", "UTF-8", new String[0], null), new a("UTF-16", "UTF-16", new String[] { "UTF_16" }, null), new a("UnicodeBigUnmarked", "UTF-16BE", new String[] { "X-UTF-16BE", "UTF_16BE", "ISO-10646-UCS-2" }, null), new a("UnicodeLittleUnmarked", "UTF-16LE", new String[] { "UTF_16LE", "X-UTF-16LE" }, null), new a("Big5", "Big5", new String[] { "csBig5", "CN-Big5", "BIG-FIVE", "BIGFIVE" }, null), new a("Big5_HKSCS", "Big5-HKSCS", new String[] { "big5hkscs" }, null), new a("EUC_JP", "EUC-JP", new String[] { "csEUCPkdFmtJapanese", "Extended_UNIX_Code_Packed_Format_for_Japanese", "eucjis", "x-eucjp", "eucjp", "x-euc-jp" }, null), new a("EUC_KR", "EUC-KR", new String[] { "csEUCKR", "ksc5601", "5601", "ksc5601_1987", "ksc_5601", "ksc5601-1987", "ks_c_5601-1987", "euckr" }, null), new a("GB18030", "GB18030", new String[] { "gb18030-2000" }, null), new a("EUC_CN", "GB2312", new String[] { "x-EUC-CN", "csGB2312", "euccn", "euc-cn", "gb2312-80", "gb2312-1980", "CN-GB", "CN-GB-ISOIR165" }, null), new a("GBK", "windows-936", new String[] { "CP936", "MS936", "ms_936", "x-mswin-936" }, null), new a("Cp037", "IBM037", new String[] { "ebcdic-cp-us", "ebcdic-cp-ca", "ebcdic-cp-wt", "ebcdic-cp-nl", "csIBM037" }, null), new a("Cp273", "IBM273", new String[] { "csIBM273" }, null), new a("Cp277", "IBM277", new String[] { "EBCDIC-CP-DK", "EBCDIC-CP-NO", "csIBM277" }, null), new a("Cp278", "IBM278", new String[] { "CP278", "ebcdic-cp-fi", "ebcdic-cp-se", "csIBM278" }, null), new a("Cp280", "IBM280", new String[] { "ebcdic-cp-it", "csIBM280" }, null), new a("Cp284", "IBM284", new String[] { "ebcdic-cp-es", "csIBM284" }, null), new a("Cp285", "IBM285", new String[] { "ebcdic-cp-gb", "csIBM285" }, null), new a("Cp297", "IBM297", new String[] { "ebcdic-cp-fr", "csIBM297" }, null), new a("Cp420", "IBM420", new String[] { "ebcdic-cp-ar1", "csIBM420" }, null), new a("Cp424", "IBM424", new String[] { "ebcdic-cp-he", "csIBM424" }, null), new a("Cp437", "IBM437", new String[] { "437", "csPC8CodePage437" }, null), new a("Cp500", "IBM500", new String[] { "ebcdic-cp-be", "ebcdic-cp-ch", "csIBM500" }, null), new a("Cp775", "IBM775", new String[] { "csPC775Baltic" }, null), new a("Cp838", "IBM-Thai", new String[0], null), new a("Cp850", "IBM850", new String[] { "850", "csPC850Multilingual" }, null), new a("Cp852", "IBM852", new String[] { "852", "csPCp852" }, null), new a("Cp855", "IBM855", new String[] { "855", "csIBM855" }, null), new a("Cp857", "IBM857", new String[] { "857", "csIBM857" }, null), new a("Cp858", "IBM00858", new String[] { "CCSID00858", "CP00858", "PC-Multilingual-850+euro" }, null), new a("Cp860", "IBM860", new String[] { "860", "csIBM860" }, null), new a("Cp861", "IBM861", new String[] { "861", "cp-is", "csIBM861" }, null), new a("Cp862", "IBM862", new String[] { "862", "csPC862LatinHebrew" }, null), new a("Cp863", "IBM863", new String[] { "863", "csIBM863" }, null), new a("Cp864", "IBM864", new String[] { "cp864", "csIBM864" }, null), new a("Cp865", "IBM865", new String[] { "865", "csIBM865" }, null), new a("Cp866", "IBM866", new String[] { "866", "csIBM866" }, null), new a("Cp868", "IBM868", new String[] { "cp-ar", "csIBM868" }, null), new a("Cp869", "IBM869", new String[] { "cp-gr", "csIBM869" }, null), new a("Cp870", "IBM870", new String[] { "ebcdic-cp-roece", "ebcdic-cp-yu", "csIBM870" }, null), new a("Cp871", "IBM871", new String[] { "ebcdic-cp-is", "csIBM871" }, null), new a("Cp918", "IBM918", new String[] { "ebcdic-cp-ar2", "csIBM918" }, null), new a("Cp1026", "IBM1026", new String[] { "csIBM1026" }, null), new a("Cp1047", "IBM1047", new String[] { "IBM-1047" }, null), new a("Cp1140", "IBM01140", new String[] { "CCSID01140", "CP01140", "ebcdic-us-37+euro" }, null), new a("Cp1141", "IBM01141", new String[] { "CCSID01141", "CP01141", "ebcdic-de-273+euro" }, null), new a("Cp1142", "IBM01142", new String[] { "CCSID01142", "CP01142", "ebcdic-dk-277+euro", "ebcdic-no-277+euro" }, null), new a("Cp1143", "IBM01143", new String[] { "CCSID01143", "CP01143", "ebcdic-fi-278+euro", "ebcdic-se-278+euro" }, null), new a("Cp1144", "IBM01144", new String[] { "CCSID01144", "CP01144", "ebcdic-it-280+euro" }, null), new a("Cp1145", "IBM01145", new String[] { "CCSID01145", "CP01145", "ebcdic-es-284+euro" }, null), new a("Cp1146", "IBM01146", new String[] { "CCSID01146", "CP01146", "ebcdic-gb-285+euro" }, null), new a("Cp1147", "IBM01147", new String[] { "CCSID01147", "CP01147", "ebcdic-fr-297+euro" }, null), new a("Cp1148", "IBM01148", new String[] { "CCSID01148", "CP01148", "ebcdic-international-500+euro" }, null), new a("Cp1149", "IBM01149", new String[] { "CCSID01149", "CP01149", "ebcdic-is-871+euro" }, null), new a("Cp1250", "windows-1250", new String[0], null), new a("Cp1251", "windows-1251", new String[0], null), new a("Cp1252", "windows-1252", new String[0], null), new a("Cp1253", "windows-1253", new String[0], null), new a("Cp1254", "windows-1254", new String[0], null), new a("Cp1255", "windows-1255", new String[0], null), new a("Cp1256", "windows-1256", new String[0], null), new a("Cp1257", "windows-1257", new String[0], null), new a("Cp1258", "windows-1258", new String[0], null), new a("ISO2022CN", "ISO-2022-CN", new String[0], null), new a("ISO2022JP", "ISO-2022-JP", new String[] { "csISO2022JP", "JIS", "jis_encoding", "csjisencoding" }, null), new a("ISO2022KR", "ISO-2022-KR", new String[] { "csISO2022KR" }, null), new a("JIS_X0201", "JIS_X0201", new String[] { "X0201", "JIS0201", "csHalfWidthKatakana" }, null), new a("JIS_X0212-1990", "JIS_X0212-1990", new String[] { "iso-ir-159", "x0212", "JIS0212", "csISO159JISX02121990" }, null), new a("JIS_C6626-1983", "JIS_C6626-1983", new String[] { "x-JIS0208", "JIS0208", "csISO87JISX0208", "x0208", "JIS_X0208-1983", "iso-ir-87" }, null), new a("SJIS", "Shift_JIS", new String[] { "MS_Kanji", "csShiftJIS", "shift-jis", "x-sjis", "pck" }, null), new a("TIS620", "TIS-620", new String[0], null), new a("MS932", "Windows-31J", new String[] { "windows-932", "csWindows31J", "x-ms-cp932" }, null), new a("EUC_TW", "EUC-TW", new String[] { "x-EUC-TW", "cns11643", "euctw" }, null), new a("x-Johab", "johab", new String[] { "johab", "cp1361", "ms1361", "ksc5601-1992", "ksc5601_1992" }, null), new a("MS950_HKSCS", "", new String[0], null), new a("MS874", "windows-874", new String[] { "cp874" }, null), new a("MS949", "windows-949", new String[] { "windows949", "ms_949", "x-windows-949" }, null), new a("MS950", "windows-950", new String[] { "x-windows-950" }, null), new a("Cp737", null, new String[0], null), new a("Cp856", null, new String[0], null), new a("Cp875", null, new String[0], null), new a("Cp921", null, new String[0], null), new a("Cp922", null, new String[0], null), new a("Cp930", null, new String[0], null), new a("Cp933", null, new String[0], null), new a("Cp935", null, new String[0], null), new a("Cp937", null, new String[0], null), new a("Cp939", null, new String[0], null), new a("Cp942", null, new String[0], null), new a("Cp942C", null, new String[0], null), new a("Cp943", null, new String[0], null), new a("Cp943C", null, new String[0], null), new a("Cp948", null, new String[0], null), new a("Cp949", null, new String[0], null), new a("Cp949C", null, new String[0], null), new a("Cp950", null, new String[0], null), new a("Cp964", null, new String[0], null), new a("Cp970", null, new String[0], null), new a("Cp1006", null, new String[0], null), new a("Cp1025", null, new String[0], null), new a("Cp1046", null, new String[0], null), new a("Cp1097", null, new String[0], null), new a("Cp1098", null, new String[0], null), new a("Cp1112", null, new String[0], null), new a("Cp1122", null, new String[0], null), new a("Cp1123", null, new String[0], null), new a("Cp1124", null, new String[0], null), new a("Cp1381", null, new String[0], null), new a("Cp1383", null, new String[0], null), new a("Cp33722", null, new String[0], null), new a("Big5_Solaris", null, new String[0], null), new a("EUC_JP_LINUX", null, new String[0], null), new a("EUC_JP_Solaris", null, new String[0], null), new a("ISCII91", null, new String[] { "x-ISCII91", "iscii" }, null), new a("ISO2022_CN_CNS", null, new String[0], null), new a("ISO2022_CN_GB", null, new String[0], null), new a("x-iso-8859-11", null, new String[0], null), new a("JISAutoDetect", null, new String[0], null), new a("MacArabic", null, new String[0], null), new a("MacCentralEurope", null, new String[0], null), new a("MacCroatian", null, new String[0], null), new a("MacCyrillic", null, new String[0], null), new a("MacDingbat", null, new String[0], null), new a("MacGreek", "MacGreek", new String[0], null), new a("MacHebrew", null, new String[0], null), new a("MacIceland", null, new String[0], null), new a("MacRoman", "MacRoman", new String[] { "Macintosh", "MAC", "csMacintosh" }, null), new a("MacRomania", null, new String[0], null), new a("MacSymbol", null, new String[0], null), new a("MacThai", null, new String[0], null), new a("MacTurkish", null, new String[0], null), new a("MacUkraine", null, new String[0], null), new a("UnicodeBig", null, new String[0], null), new a("UnicodeLittle", null, new String[0], null) };
  static SortedSet<String> b;
  
  static
  {
    jdField_a_of_type_JavaUtilSortedSet = null;
    b = null;
    jdField_a_of_type_JavaUtilMap = null;
    jdField_a_of_type_JavaUtilSortedSet = new TreeSet();
    b = new TreeSet();
    Object localObject1 = jdField_a_of_type_ArrayOfComTencentCommonUtilsCharsetUtil$a;
    int j = localObject1.length;
    int i = 0;
    for (;;)
    {
      Object localObject2;
      if (i < j) {
        localObject2 = localObject1[i];
      }
      try
      {
        localObject3 = ((a)localObject2).jdField_a_of_type_JavaLangString;
        new String(new byte[] { 100, 117, 109, 109, 121 }, (String)localObject3);
        jdField_a_of_type_JavaUtilSortedSet.add(((a)localObject2).jdField_a_of_type_JavaLangString.toLowerCase());
      }
      catch (UnsupportedOperationException|UnsupportedEncodingException localUnsupportedOperationException2)
      {
        try
        {
          for (;;)
          {
            Object localObject3;
            "dummy".getBytes(((a)localObject2).jdField_a_of_type_JavaLangString);
            b.add(((a)localObject2).jdField_a_of_type_JavaLangString.toLowerCase());
            i += 1;
            break;
            jdField_a_of_type_JavaUtilMap = new HashMap();
            localObject1 = jdField_a_of_type_ArrayOfComTencentCommonUtilsCharsetUtil$a;
            int k = localObject1.length;
            i = 0;
            while (i < k)
            {
              localObject2 = localObject1[i];
              jdField_a_of_type_JavaUtilMap.put(((a)localObject2).jdField_a_of_type_JavaLangString.toLowerCase(), localObject2);
              if (((a)localObject2).b != null) {
                jdField_a_of_type_JavaUtilMap.put(((a)localObject2).b.toLowerCase(), localObject2);
              }
              if (((a)localObject2).jdField_a_of_type_ArrayOfJavaLangString != null)
              {
                localObject3 = ((a)localObject2).jdField_a_of_type_ArrayOfJavaLangString;
                int m = localObject3.length;
                j = 0;
                while (j < m)
                {
                  Object localObject4 = localObject3[j];
                  jdField_a_of_type_JavaUtilMap.put(((String)localObject4).toLowerCase(), localObject2);
                  j += 1;
                }
              }
              i += 1;
            }
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("Character sets which support decoding: ");
            ((StringBuilder)localObject1).append(jdField_a_of_type_JavaUtilSortedSet);
            FLogger.d("CharsetUtil", ((StringBuilder)localObject1).toString());
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("Character sets which support encoding: ");
            ((StringBuilder)localObject1).append(b);
            FLogger.d("CharsetUtil", ((StringBuilder)localObject1).toString());
            US_ASCII = Charset.forName("US-ASCII");
            ISO_8859_1 = Charset.forName("ISO-8859-1");
            UTF_8 = Charset.forName("UTF-8");
            DEFAULT_CHARSET = US_ASCII;
            return;
            localUnsupportedOperationException2 = localUnsupportedOperationException2;
          }
        }
        catch (UnsupportedOperationException|UnsupportedEncodingException localUnsupportedOperationException1)
        {
          for (;;) {}
        }
      }
    }
  }
  
  public static Charset getCharset(String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "ISO-8859-1";
    }
    try
    {
      paramString = Charset.forName(str);
      return paramString;
    }
    catch (UnsupportedCharsetException paramString)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unsupported charset ");
      localStringBuilder.append(str);
      localStringBuilder.append(", fallback to ");
      localStringBuilder.append("ISO-8859-1");
      localStringBuilder.append(": ");
      localStringBuilder.append(paramString);
      FLogger.d("CharsetUtil", localStringBuilder.toString());
      return Charset.forName("ISO-8859-1");
    }
    catch (IllegalCharsetNameException paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Illegal charset ");
      localStringBuilder.append(str);
      localStringBuilder.append(", fallback to ");
      localStringBuilder.append("ISO-8859-1");
      localStringBuilder.append(": ");
      localStringBuilder.append(paramString);
      FLogger.d("CharsetUtil", localStringBuilder.toString());
    }
    return Charset.forName("ISO-8859-1");
  }
  
  public static boolean isASCII(char paramChar)
  {
    return (paramChar & 0xFF80) == 0;
  }
  
  public static boolean isASCII(String paramString)
  {
    if (paramString != null)
    {
      int j = paramString.length();
      int i = 0;
      while (i < j)
      {
        if (!isASCII(paramString.charAt(i))) {
          return false;
        }
        i += 1;
      }
      return true;
    }
    throw new IllegalArgumentException("String may not be null");
  }
  
  public static boolean isDecodingSupported(String paramString)
  {
    return jdField_a_of_type_JavaUtilSortedSet.contains(paramString.toLowerCase());
  }
  
  public static boolean isEncodingSupported(String paramString)
  {
    return b.contains(paramString.toLowerCase());
  }
  
  public static boolean isWhitespace(char paramChar)
  {
    return (paramChar == ' ') || (paramChar == '\t') || (paramChar == '\r') || (paramChar == '\n');
  }
  
  public static boolean isWhitespace(String paramString)
  {
    if (paramString != null)
    {
      int j = paramString.length();
      int i = 0;
      while (i < j)
      {
        if (!isWhitespace(paramString.charAt(i))) {
          return false;
        }
        i += 1;
      }
      return true;
    }
    throw new IllegalArgumentException("String may not be null");
  }
  
  public static String toJavaCharset(String paramString)
  {
    paramString = (a)jdField_a_of_type_JavaUtilMap.get(paramString.toLowerCase());
    if (paramString != null) {
      return paramString.jdField_a_of_type_JavaLangString;
    }
    return null;
  }
  
  public static String toMimeCharset(String paramString)
  {
    paramString = (a)jdField_a_of_type_JavaUtilMap.get(paramString.toLowerCase());
    if (paramString != null) {
      return paramString.b;
    }
    return null;
  }
  
  private static class a
    implements Comparable<a>
  {
    String jdField_a_of_type_JavaLangString = null;
    String[] jdField_a_of_type_ArrayOfJavaLangString = null;
    String b = null;
    
    private a(String paramString1, String paramString2, String[] paramArrayOfString)
    {
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.b = paramString2;
      this.jdField_a_of_type_ArrayOfJavaLangString = paramArrayOfString;
    }
    
    public int a(a parama)
    {
      return this.jdField_a_of_type_JavaLangString.compareTo(parama.jdField_a_of_type_JavaLangString);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\CharsetUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */