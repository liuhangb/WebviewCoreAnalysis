package org.chromium.base;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.os.LocaleList;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Locale.Builder;
import java.util.Map;
import org.chromium.base.annotations.CalledByNative;

public class LocaleUtils
{
  private static final Map<String, String> a;
  private static final Map<String, String> b;
  
  static
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("iw", "he");
    localHashMap.put("ji", "yi");
    localHashMap.put("in", "id");
    localHashMap.put("tl", "fil");
    a = Collections.unmodifiableMap(localHashMap);
    localHashMap = new HashMap();
    localHashMap.put("und", "");
    localHashMap.put("fil", "tl");
    b = Collections.unmodifiableMap(localHashMap);
  }
  
  public static Locale forLanguageTag(String paramString)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return getUpdatedLocaleForAndroid(Locale.forLanguageTag(paramString));
    }
    return forLanguageTagCompat(paramString);
  }
  
  public static Locale forLanguageTagCompat(String paramString)
  {
    Object localObject = paramString.split("-");
    if (localObject.length == 0) {
      return new Locale("");
    }
    paramString = getUpdatedLanguageForAndroid(localObject[0]);
    if (((paramString.length() != 2) && (paramString.length() != 3)) || (paramString.equals("und"))) {
      return new Locale("");
    }
    if (localObject.length == 1) {
      return new Locale(paramString);
    }
    localObject = localObject[1];
    if ((((String)localObject).length() != 2) && (((String)localObject).length() != 3)) {
      return new Locale(paramString);
    }
    return new Locale(paramString, (String)localObject);
  }
  
  @CalledByNative
  private static String getDefaultCountryCode()
  {
    CommandLine localCommandLine = CommandLine.getInstance();
    if (localCommandLine.hasSwitch("default-country-code")) {
      return localCommandLine.getSwitchValue("default-country-code");
    }
    return Locale.getDefault().getCountry();
  }
  
  public static String getDefaultLocaleListString()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return toLanguageTags(LocaleList.getDefault());
    }
    return getDefaultLocaleString();
  }
  
  @CalledByNative
  public static String getDefaultLocaleString()
  {
    return toLanguageTag(Locale.getDefault());
  }
  
  public static String getUpdatedLanguageForAndroid(String paramString)
  {
    String str = (String)b.get(paramString);
    if (str == null) {
      return paramString;
    }
    return str;
  }
  
  public static String getUpdatedLanguageForChromium(String paramString)
  {
    String str = (String)a.get(paramString);
    if (str == null) {
      return paramString;
    }
    return str;
  }
  
  @TargetApi(21)
  @VisibleForTesting
  public static Locale getUpdatedLocaleForAndroid(Locale paramLocale)
  {
    String str = (String)b.get(paramLocale.getLanguage());
    if (str == null) {
      return paramLocale;
    }
    return new Locale.Builder().setLocale(paramLocale).setLanguage(str).build();
  }
  
  @TargetApi(21)
  @VisibleForTesting
  public static Locale getUpdatedLocaleForChromium(Locale paramLocale)
  {
    String str = (String)a.get(paramLocale.getLanguage());
    if (str == null) {
      return paramLocale;
    }
    if (Build.VERSION.SDK_INT < 21) {
      return paramLocale;
    }
    return new Locale.Builder().setLocale(paramLocale).setLanguage(str).build();
  }
  
  public static String toLanguageTag(Locale paramLocale)
  {
    String str1 = getUpdatedLanguageForChromium(paramLocale.getLanguage());
    String str2 = paramLocale.getCountry();
    if ((str1.equals("no")) && (str2.equals("NO")) && (paramLocale.getVariant().equals("NY"))) {
      return "nn-NO";
    }
    if (str2.isEmpty()) {
      return str1;
    }
    paramLocale = new StringBuilder();
    paramLocale.append(str1);
    paramLocale.append("-");
    paramLocale.append(str2);
    return paramLocale.toString();
  }
  
  @TargetApi(24)
  public static String toLanguageTags(LocaleList paramLocaleList)
  {
    if (Build.VERSION.SDK_INT < 21) {
      return "";
    }
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramLocaleList.size())
    {
      localArrayList.add(toLanguageTag(getUpdatedLocaleForChromium(paramLocaleList.get(i))));
      i += 1;
    }
    return TextUtils.join(",", localArrayList);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\LocaleUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */