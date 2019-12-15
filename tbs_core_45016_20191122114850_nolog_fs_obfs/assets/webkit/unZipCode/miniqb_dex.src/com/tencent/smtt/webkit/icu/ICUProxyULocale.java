package com.tencent.smtt.webkit.icu;

import android.icu.util.ULocale;
import android.os.Build.VERSION;
import android.text.TextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.PersistLog;

@JNINamespace("tencent")
public class ICUProxyULocale
{
  private static final String TAG = "ICU_PROXY_LOG(Java_ICUProxyULocale)";
  private static boolean isSupportRef;
  private ULocale uLocaleObject = null;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 24) {
      bool = true;
    } else {
      bool = false;
    }
    isSupportRef = bool;
  }
  
  ICUProxyULocale(ULocale paramULocale)
  {
    this.uLocaleObject = paramULocale;
  }
  
  @CalledByNative
  public static String addLikelySubtags(String paramString)
  {
    try
    {
      paramString = ULocale.addLikelySubtags(new ULocale(paramString));
      if (paramString == null) {
        return null;
      }
      paramString = new ICUProxyULocale(paramString).getName();
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("addLikelySubtags:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String canonicalize(String paramString)
  {
    try
    {
      paramString = ULocale.canonicalize(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("canonicalize:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  public static ULocale createULocale(String paramString)
  {
    try
    {
      paramString = new ULocale(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("createULocal:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String forLanguageTag(String paramString)
  {
    try
    {
      paramString = ULocale.forLanguageTag(paramString);
      if (paramString == null) {
        return null;
      }
      paramString = new ICUProxyULocale(paramString).getName();
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("forLanguageTag:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String getBaseName(String paramString)
  {
    try
    {
      paramString = ULocale.getBaseName(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getBaseName:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String getCountry(String paramString)
  {
    try
    {
      paramString = ULocale.getCountry(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getCountry:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String getDefault()
  {
    try
    {
      Object localObject = ULocale.getDefault();
      StringBuilder localStringBuilder;
      if (localObject != null) {
        try
        {
          localObject = new ICUProxyULocale((ULocale)localObject).getName();
          return (String)localObject;
        }
        catch (Exception localException1)
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("getDefault:");
          localStringBuilder.append(localException1.getMessage());
          PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
          return null;
        }
      }
      return null;
    }
    catch (Exception localException2)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("getDefault:");
      localStringBuilder.append(localException2.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
  }
  
  @CalledByNative
  public static String getISO3Country(String paramString)
  {
    try
    {
      paramString = ULocale.getISO3Country(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getISO3Country:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String getISO3Language(String paramString)
  {
    try
    {
      paramString = ULocale.getISO3Language(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getISO3Language:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String getKeywordValue(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = ULocale.getKeywordValue(paramString1, paramString2);
      return paramString1;
    }
    catch (Exception paramString1)
    {
      paramString2 = new StringBuilder();
      paramString2.append("getKeywordValue:");
      paramString2.append(paramString1.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", paramString2.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String getLanguage(String paramString)
  {
    try
    {
      paramString = ULocale.getLanguage(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getLanguage:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String getName(String paramString)
  {
    try
    {
      paramString = ULocale.getName(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getName:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String getScript(String paramString)
  {
    try
    {
      paramString = ULocale.getScript(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getKeywordValue:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String getVariant(String paramString)
  {
    try
    {
      paramString = ULocale.getVariant(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getVariant:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String toLanguageTag(String paramString)
  {
    do
    {
      try
      {
        if (TextUtils.isEmpty(paramString)) {
          paramString = ULocale.getDefault();
        } else {
          paramString = new ULocale(paramString);
        }
      }
      catch (Exception paramString)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("toLanguageTag:");
        localStringBuilder.append(paramString.getMessage());
        PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
        return null;
      }
      paramString = paramString.toLanguageTag();
      return paramString;
    } while (paramString != null);
    return null;
  }
  
  @CalledByNative
  public static String toLegacyKey(String paramString)
  {
    try
    {
      paramString = ULocale.toLegacyKey(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("toLegacyKey:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String toLegacyType(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = ULocale.toLegacyType(paramString1, paramString2);
      return paramString1;
    }
    catch (Exception paramString1)
    {
      paramString2 = new StringBuilder();
      paramString2.append("toLegacyType:");
      paramString2.append(paramString1.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", paramString2.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String toUnicodeLocaleKey(String paramString)
  {
    try
    {
      paramString = ULocale.toUnicodeLocaleKey(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("toUnicodeLocaleKey:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static String toUnicodeLocaleType(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = ULocale.toUnicodeLocaleType(paramString1, paramString2);
      return paramString1;
    }
    catch (Exception paramString1)
    {
      paramString2 = new StringBuilder();
      paramString2.append("toUnicodeLocaleType:");
      paramString2.append(paramString1.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", paramString2.toString());
    }
    return null;
  }
  
  public String getName()
  {
    Object localObject = this.uLocaleObject;
    if (localObject == null) {
      return null;
    }
    try
    {
      localObject = ((ULocale)localObject).getName();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getName:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyULocale)", localStringBuilder.toString());
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyULocale.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */