package com.tencent.smtt.webkit.icu;

import android.text.AndroidCharacter;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Time;
import com.tencent.smtt.webkit.icu.text.a;
import com.tencent.smtt.webkit.icu.text.b;
import java.lang.reflect.Method;
import java.net.IDN;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.text.Bidi;
import java.text.Collator;
import java.text.DateFormatSymbols;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.SortedMap;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.PersistLog;

@JNINamespace("tencent")
public class ICUProxy
{
  private static final int[] DIRECTIONALITY_MAP = { 0, 1, 13, 2, 3, 4, 5, 6, 17, 18, 7, 8, 9, 10, 11, 12, 14, 15, 16 };
  private static final String TAG = "ICU_PROXY_LOG(Java)";
  private static Method funForLanTag;
  private ByteBuffer mPreDecodeBuf = null;
  
  @CalledByNative
  public static int Category(int paramInt)
  {
    paramInt = Character.getType(paramInt);
    if (paramInt < 17) {
      return paramInt;
    }
    if (paramInt > 17) {
      return paramInt - 1;
    }
    return 0;
  }
  
  @CalledByNative
  public static byte[] CharsetConvert(byte[] paramArrayOfByte, String paramString1, String paramString2)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    try
    {
      paramArrayOfByte = Charset.forName(paramString1).decode(ByteBuffer.wrap(paramArrayOfByte));
      paramArrayOfByte = getByteFromByteBuffer(Charset.forName(paramString2).encode(paramArrayOfByte));
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      PersistLog.printStackTrace(paramArrayOfByte);
    }
    return null;
  }
  
  @CalledByNative
  public static char[] CharsetDecode(byte[] paramArrayOfByte, String paramString)
  {
    try
    {
      paramArrayOfByte = new String(paramArrayOfByte, paramString).toCharArray();
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      paramString = new StringBuilder();
      paramString.append("Decode failed: ");
      paramString.append(paramArrayOfByte);
      PersistLog.e("ICU_PROXY_LOG(Java)", paramString.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static byte[] CharsetEncode(char[] paramArrayOfChar, String paramString)
  {
    try
    {
      if (TextUtils.isEmpty(paramString)) {
        paramString = Charset.defaultCharset();
      } else {
        paramString = Charset.forName(paramString);
      }
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    paramString = Charset.defaultCharset();
    if (!paramString.canEncode()) {
      return null;
    }
    try
    {
      paramArrayOfChar = getByteFromByteBuffer(paramString.encode(CharBuffer.wrap(paramArrayOfChar)));
      return paramArrayOfChar;
    }
    catch (Exception paramArrayOfChar)
    {
      paramString = new StringBuilder();
      paramString.append("endcode failed: ");
      paramString.append(paramArrayOfChar);
      PersistLog.e("ICU_PROXY_LOG(Java)", paramString.toString());
      return null;
    }
  }
  
  @CalledByNative
  public static int CompareString16WithCollator(String paramString1, String paramString2, String paramString3)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return Collator.getInstance().compare(paramString2, paramString3);
    }
    return Collator.getInstance(GetLocale(paramString1)).compare(paramString2, paramString3);
  }
  
  @CalledByNative
  public static String[] DetectAllEncodingsFromBuffer(byte[] paramArrayOfByte)
  {
    ArrayList localArrayList = new ArrayList();
    a locala = new a();
    locala.a(paramArrayOfByte);
    paramArrayOfByte = locala.a();
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      localArrayList.add(paramArrayOfByte[i].a());
      i += 1;
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  @CalledByNative
  public static int Direction(int paramInt)
  {
    int i = Character.getDirectionality(paramInt);
    paramInt = i;
    if (i == -1) {
      paramInt = 0;
    }
    return DIRECTIONALITY_MAP[paramInt];
  }
  
  private static void DumpLog(String paramString)
  {
    int i = 0;
    while (i < paramString.length())
    {
      int j = paramString.length() - i;
      if (j > 2048) {
        i += 2048;
      } else {
        i += j;
      }
    }
  }
  
  @CalledByNative
  public static int FoldCase(int paramInt)
  {
    if (paramInt < 128)
    {
      if ((65 <= paramInt) && (paramInt <= 90)) {
        return (char)(paramInt + 32);
      }
      return paramInt;
    }
    return Character.toLowerCase(paramInt);
  }
  
  @CalledByNative
  public static String FormatDate(long paramLong, int paramInt, boolean paramBoolean)
  {
    Time localTime = new Time();
    localTime.set(paramLong);
    String str;
    switch (paramInt)
    {
    default: 
      str = "%b %e, %Y";
      break;
    case 2: 
      str = "%A, %B %e, %Y";
      break;
    case 1: 
      str = "%D";
      break;
    case 0: 
      str = "%b %e, %Y";
    }
    Object localObject = str;
    if (paramBoolean)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).append(" %r");
      localObject = ((StringBuilder)localObject).toString();
    }
    return localTime.format((String)localObject);
  }
  
  @CalledByNative
  public static String[] GetAllAliasesByEncoding(String paramString)
  {
    try
    {
      paramString = Charset.forName(paramString).aliases();
      paramString = (String[])paramString.toArray(new String[paramString.size()]);
      return paramString;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return null;
  }
  
  @CalledByNative
  public static String[] GetAllAvailableEncodings()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = Charset.availableCharsets().entrySet().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(((Charset)((Map.Entry)localIterator.next()).getValue()).name());
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  @CalledByNative
  public static String[] GetAllLocales()
  {
    ArrayList localArrayList = new ArrayList();
    Locale[] arrayOfLocale = Locale.getAvailableLocales();
    int j = arrayOfLocale.length;
    int i = 0;
    while (i < j)
    {
      localArrayList.add(arrayOfLocale[i].getDisplayName());
      i += 1;
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  @CalledByNative
  public static String[] GetAllShortMonthByLocaleName(String paramString)
  {
    try
    {
      paramString = GetLocale(paramString);
      if (paramString == null) {
        return new String[0];
      }
      paramString = new DateFormatSymbols(paramString).getShortMonths();
      return paramString;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return new String[0];
  }
  
  @CalledByNative
  public static String GetConfiguredLocale()
  {
    Object localObject = Locale.getDefault();
    try
    {
      String str1 = ((Locale)localObject).getLanguage();
      String str2 = ((Locale)localObject).getCountry();
      localObject = str1;
      if (!str2.isEmpty())
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(str1);
        ((StringBuilder)localObject).append("-");
        ((StringBuilder)localObject).append(str2);
        localObject = ((StringBuilder)localObject).toString();
      }
      return (String)localObject;
    }
    catch (MissingResourceException localMissingResourceException)
    {
      for (;;) {}
    }
    return "und";
  }
  
  @CalledByNative
  public static String GetDefaultLocaleCountry()
  {
    Object localObject = Locale.getDefault();
    try
    {
      localObject = ((Locale)localObject).getCountry();
      return (String)localObject;
    }
    catch (MissingResourceException localMissingResourceException)
    {
      for (;;) {}
    }
    return "";
  }
  
  @CalledByNative
  public static String GetDefaultLocaleLanguage()
  {
    Object localObject = Locale.getDefault();
    try
    {
      localObject = ((Locale)localObject).getLanguage();
      return (String)localObject;
    }
    catch (MissingResourceException localMissingResourceException)
    {
      for (;;) {}
    }
    return "und";
  }
  
  @CalledByNative
  public static Locale GetLocale(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty()))
    {
      int j = paramString.indexOf("-");
      int i = j;
      if (j == -1) {
        i = paramString.indexOf("_");
      }
      if (i == -1) {
        return new Locale(paramString);
      }
      return new Locale(paramString.substring(0, i), paramString.substring(i + 1));
    }
    return Locale.getDefault();
  }
  
  @CalledByNative
  public static boolean HasLineBreakingPropertyComplexContext(int paramInt)
  {
    return (Character.getType(paramInt) & 0xD) != 0;
  }
  
  @CalledByNative
  public static String IDNToASCII(String paramString)
  {
    try
    {
      paramString = IDN.toASCII(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      PersistLog.e("ICU_PROXY_LOG(Java)", paramString.toString());
    }
    return "";
  }
  
  @CalledByNative
  public static String IDNToUnicode(String paramString)
  {
    try
    {
      paramString = IDN.toUnicode(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      PersistLog.e("ICU_PROXY_LOG(Java)", paramString.toString());
    }
    return "";
  }
  
  @CalledByNative
  public static boolean Is24HourFormat()
  {
    return DateFormat.is24HourFormat(ContextUtils.getApplicationContext());
  }
  
  @CalledByNative
  public static boolean IsAlphanumeric(int paramInt)
  {
    return Character.isLetterOrDigit(paramInt);
  }
  
  @CalledByNative
  public static boolean IsArabicChar(int paramInt)
  {
    return Character.UnicodeBlock.of(paramInt) == Character.UnicodeBlock.ARABIC;
  }
  
  @CalledByNative
  public static boolean IsLower(int paramInt)
  {
    return Character.isLowerCase(paramInt);
  }
  
  @CalledByNative
  public static boolean IsLtr(String paramString)
  {
    return createBidi(paramString).isLeftToRight();
  }
  
  @CalledByNative
  public static boolean IsPunct(int paramInt)
  {
    return (Character.getType(paramInt) & 0x1F) != 0;
  }
  
  @CalledByNative
  public static boolean IsRtl(String paramString)
  {
    return createBidi(paramString).isRightToLeft();
  }
  
  @CalledByNative
  public static boolean IsSpace(int paramInt)
  {
    return Character.isSpaceChar(paramInt);
  }
  
  @CalledByNative
  public static boolean IsUpper(int paramInt)
  {
    return Character.isUpperCase(paramInt);
  }
  
  @CalledByNative
  public static boolean Isprint(int paramInt)
  {
    return TextUtils.isGraphic(CharBuffer.wrap(Character.toChars(paramInt)));
  }
  
  @CalledByNative
  public static int MirroredChar(int paramInt)
  {
    return AndroidCharacter.getMirror((char)paramInt);
  }
  
  @CalledByNative
  public static String Normalize(String paramString, int paramInt)
  {
    if (paramInt != 5) {
      switch (paramInt)
      {
      default: 
        break;
      case 3: 
        localForm = Normalizer.Form.NFKD;
        break;
      case 2: 
        localForm = Normalizer.Form.NFD;
        break;
      }
    }
    Normalizer.Form localForm = Normalizer.Form.NFKC;
    localForm = Normalizer.Form.NFC;
    return Normalizer.normalize(paramString, localForm);
  }
  
  @CalledByNative
  public static void SetDefaultLocale(String paramString)
  {
    Locale.setDefault(GetLocale(paramString));
  }
  
  @CalledByNative
  public static String TimeFormatTimeOfDay(long paramLong, boolean paramBoolean1, boolean paramBoolean2)
  {
    Time localTime = new Time();
    localTime.set(paramLong);
    String str = "%I:%M";
    if (paramBoolean1) {
      str = "%H:%M";
    }
    Object localObject = str;
    if (paramBoolean2)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).append(" %P");
      localObject = ((StringBuilder)localObject).toString();
    }
    return localTime.format((String)localObject);
  }
  
  @CalledByNative
  public static int ToLower(int paramInt)
  {
    return Character.toLowerCase(paramInt);
  }
  
  @CalledByNative
  public static String ToLowerString(String paramString)
  {
    return paramString.toLowerCase();
  }
  
  @CalledByNative
  public static int ToTitleCase(int paramInt)
  {
    return Character.toTitleCase(paramInt);
  }
  
  @CalledByNative
  private char[] ToUnicode(byte[] paramArrayOfByte, String paramString, boolean paramBoolean)
  {
    try
    {
      if (TextUtils.isEmpty(paramString)) {
        localObject1 = Charset.defaultCharset();
      } else {
        localObject1 = Charset.forName(paramString);
      }
    }
    catch (Exception localException1)
    {
      Object localObject1;
      Object localObject2;
      int i;
      for (;;) {}
    }
    localObject1 = Charset.defaultCharset();
    if (localObject1 == null) {
      return null;
    }
    localObject2 = ((Charset)localObject1).newDecoder();
    localObject1 = this.mPreDecodeBuf;
    if ((localObject1 != null) && (((ByteBuffer)localObject1).hasRemaining())) {
      i = this.mPreDecodeBuf.remaining();
    } else {
      i = 0;
    }
    ((CharsetDecoder)localObject2).onMalformedInput(CodingErrorAction.REPORT);
    ((CharsetDecoder)localObject2).onUnmappableCharacter(CodingErrorAction.REPLACE);
    localObject1 = ByteBuffer.allocate(paramArrayOfByte.length + i);
    if (i > 0)
    {
      this.mPreDecodeBuf.rewind();
      ((ByteBuffer)localObject1).put(this.mPreDecodeBuf);
      this.mPreDecodeBuf.clear();
      this.mPreDecodeBuf = null;
    }
    ((ByteBuffer)localObject1).put(ByteBuffer.wrap(paramArrayOfByte));
    ((ByteBuffer)localObject1).rewind();
    try
    {
      localObject2 = ((CharsetDecoder)localObject2).decode((ByteBuffer)localObject1);
      localObject2 = new String(((CharBuffer)localObject2).array(), 0, ((CharBuffer)localObject2).remaining()).toCharArray();
      return (char[])localObject2;
    }
    catch (MalformedInputException localMalformedInputException)
    {
      int j;
      for (;;) {}
    }
    catch (CharacterCodingException localCharacterCodingException)
    {
      for (;;) {}
    }
    catch (Exception localException2)
    {
      for (;;) {}
    }
    return toUnicodeByString(paramArrayOfByte, paramString);
    return toUnicodeByString(paramArrayOfByte, paramString);
    if ((((ByteBuffer)localObject1).position() > 0) && (((ByteBuffer)localObject1).remaining() < 3) && (!paramBoolean))
    {
      i = ((ByteBuffer)localObject1).remaining() + 1;
      paramArrayOfByte = this.mPreDecodeBuf;
      if (paramArrayOfByte == null)
      {
        this.mPreDecodeBuf = ByteBuffer.allocate(i);
      }
      else if (paramArrayOfByte.hasRemaining())
      {
        paramArrayOfByte = this.mPreDecodeBuf;
        paramArrayOfByte.position(paramArrayOfByte.limit());
      }
      j = ((ByteBuffer)localObject1).position() - 1;
      paramArrayOfByte = new byte[i];
      ((ByteBuffer)localObject1).position(j);
      ((ByteBuffer)localObject1).get(paramArrayOfByte, 0, i);
      this.mPreDecodeBuf.put(paramArrayOfByte);
      this.mPreDecodeBuf.flip();
      paramArrayOfByte = new byte[j];
      ((ByteBuffer)localObject1).rewind();
      ((ByteBuffer)localObject1).get(paramArrayOfByte, 0, paramArrayOfByte.length);
      return toUnicodeByString(paramArrayOfByte, paramString);
    }
    return toUnicodeByString(paramArrayOfByte, paramString);
  }
  
  @CalledByNative
  public static int ToUpper(int paramInt)
  {
    return Character.toUpperCase(paramInt);
  }
  
  @CalledByNative
  public static String ToUpperString(String paramString)
  {
    return paramString.toUpperCase();
  }
  
  @CalledByNative
  public static int Umemcasecmp(String paramString1, String paramString2)
  {
    return paramString1.compareToIgnoreCase(paramString2);
  }
  
  private static Boolean canDecode(byte[] paramArrayOfByte, CharsetDecoder paramCharsetDecoder)
  {
    try
    {
      paramCharsetDecoder.reset();
      paramCharsetDecoder.decode(ByteBuffer.wrap(paramArrayOfByte));
      return Boolean.valueOf(true);
    }
    catch (Exception paramArrayOfByte)
    {
      for (;;) {}
    }
    return Boolean.valueOf(false);
  }
  
  @CalledByNative
  public static String convertStr2Utf8(byte[] paramArrayOfByte, String paramString)
  {
    if (paramArrayOfByte == null) {
      return new String();
    }
    if (!TextUtils.isEmpty(paramString)) {}
    try
    {
      localObject1 = Charset.forName(paramString);
      if (canDecode(paramArrayOfByte, ((Charset)localObject1).newDecoder()).booleanValue())
      {
        localObject1 = ((Charset)localObject1).decode(ByteBuffer.wrap(paramArrayOfByte));
        return (String)localObject1;
      }
    }
    catch (Exception localException)
    {
      Object localObject1;
      Object localObject2;
      for (;;) {}
    }
    localObject2 = Charset.availableCharsets();
    localObject1 = new ArrayList();
    localObject2 = ((SortedMap)localObject2).entrySet().iterator();
    while (((Iterator)localObject2).hasNext())
    {
      Charset localCharset = (Charset)((Map.Entry)((Iterator)localObject2).next()).getValue();
      if (canDecode(paramArrayOfByte, localCharset.newDecoder()).booleanValue())
      {
        if ((!TextUtils.isEmpty(paramString)) && (localCharset.aliases().contains(paramString))) {
          return localCharset.decode(ByteBuffer.wrap(paramArrayOfByte));
        }
        ((List)localObject1).add(localCharset);
      }
    }
    if (((List)localObject1).isEmpty()) {
      return new String(paramArrayOfByte);
    }
    return ((Charset)((List)localObject1).get(0)).decode(ByteBuffer.wrap(paramArrayOfByte));
  }
  
  private static Bidi createBidi(String paramString)
  {
    if (!TextUtils.isEmpty(paramString)) {
      return new Bidi("text", -2);
    }
    return new Bidi(paramString, -2);
  }
  
  @CalledByNative
  public static ICUProxy createInstance()
  {
    return new ICUProxy();
  }
  
  public static byte[] getByteFromByteBuffer(ByteBuffer paramByteBuffer)
  {
    paramByteBuffer.rewind();
    byte[] arrayOfByte = new byte[paramByteBuffer.remaining()];
    paramByteBuffer.get(arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }
  
  public static char[] getCharFromCharBuffer(CharBuffer paramCharBuffer)
  {
    paramCharBuffer.rewind();
    char[] arrayOfChar = new char[paramCharBuffer.remaining()];
    paramCharBuffer.get(arrayOfChar, 0, arrayOfChar.length);
    return arrayOfChar;
  }
  
  public static String getLocaleName(Locale paramLocale)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str = paramLocale.getLanguage();
    paramLocale = paramLocale.getCountry();
    if (!TextUtils.isEmpty(str)) {
      localStringBuilder.append(str);
    }
    if (!TextUtils.isEmpty(paramLocale))
    {
      if (localStringBuilder.length() > 0) {
        localStringBuilder.append("_");
      }
      localStringBuilder.append(paramLocale);
    }
    return localStringBuilder.toString();
  }
  
  private static char[] toUnicodeByString(byte[] paramArrayOfByte, String paramString)
  {
    try
    {
      paramArrayOfByte = new String(paramArrayOfByte, paramString).toCharArray();
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      paramString = new StringBuilder();
      paramString.append("Decode failed: ");
      paramString.append(paramArrayOfByte);
      PersistLog.e("ICU_PROXY_LOG(Java)", paramString.toString());
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */