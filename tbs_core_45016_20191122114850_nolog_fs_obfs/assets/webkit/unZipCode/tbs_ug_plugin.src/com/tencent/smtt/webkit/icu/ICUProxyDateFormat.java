package com.tencent.smtt.webkit.icu;

import android.os.Build.VERSION;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.PersistLog;

@JNINamespace("tencent")
public class ICUProxyDateFormat
{
  private static final String TAG = "ICU_PROXY_LOG(Java_DateFormat)";
  private static Method funGBDTP;
  
  @CalledByNative
  public static java.text.DateFormat CreateDateFormat(String paramString, int paramInt1, int paramInt2)
  {
    paramString = ICUProxy.GetLocale(paramString);
    if ((paramInt1 < 0) && (paramInt2 < 0)) {
      return java.text.DateFormat.getDateTimeInstance();
    }
    if (paramInt1 < 0) {}
    try
    {
      return java.text.DateFormat.getTimeInstance(paramInt2, paramString);
    }
    catch (Exception localException)
    {
      Object localObject;
      for (;;) {}
    }
    if (paramInt2 < 0) {
      return java.text.DateFormat.getDateInstance(paramInt1, paramString);
    }
    paramString = java.text.DateFormat.getDateTimeInstance(paramInt1, paramInt2, paramString);
    return paramString;
    paramString = new StringBuilder();
    paramString.append("CreateDateFormat:");
    paramString.append(((Exception)localObject).getMessage());
    PersistLog.e("ICU_PROXY_LOG(Java_DateFormat)", paramString.toString());
    return java.text.DateFormat.getDateTimeInstance();
  }
  
  @CalledByNative
  public static String GetDecimalTextAttribute(String paramString, int paramInt)
  {
    paramString = NumberFormat.getInstance(ICUProxy.GetLocale(paramString));
    if ((paramString instanceof DecimalFormat))
    {
      paramString = (DecimalFormat)paramString;
      if (paramInt == 0) {
        return paramString.getPositivePrefix();
      }
      if (paramInt == 1) {
        return paramString.getPositiveSuffix();
      }
      if (paramInt == 2) {
        return paramString.getNegativePrefix();
      }
      if (paramInt == 3) {
        return paramString.getNegativeSuffix();
      }
    }
    return null;
  }
  
  @CalledByNative
  public static String[] GetDisplayNames(java.text.DateFormat paramDateFormat, String paramString, int paramInt)
  {
    if (paramDateFormat == null) {
      return null;
    }
    paramString = ICUProxy.GetLocale(paramString);
    paramDateFormat = paramDateFormat.getCalendar();
    int i = paramInt;
    if (paramInt == 4) {
      i = 7;
    }
    int k = 1;
    if ((i != 1) && (i != 10))
    {
      paramInt = 1;
      j = i;
    }
    else
    {
      j = 2;
      paramInt = 2;
    }
    if (j != 2)
    {
      i = j;
      if (j != 11) {}
    }
    else
    {
      i = 2;
    }
    int j = paramInt;
    paramInt = i;
    if (i == 5)
    {
      paramInt = 9;
      j = 2;
    }
    try
    {
      paramString = paramDateFormat.getDisplayNames(paramInt, j, paramString);
      if ((paramInt == 9) && (paramString.size() == 2))
      {
        paramDateFormat = new String[2];
        paramString = paramString.keySet().iterator();
        paramInt = k;
      }
      while (paramString.hasNext())
      {
        paramDateFormat[paramInt] = ((String)paramString.next());
        paramInt -= 1;
        continue;
        paramDateFormat = (String[])paramString.keySet().toArray(new String[paramString.size()]);
        return paramDateFormat;
      }
    }
    catch (Exception paramDateFormat)
    {
      PersistLog.printStackTrace(paramDateFormat);
      return null;
    }
    return paramDateFormat;
  }
  
  @CalledByNative
  public static int GetFirstDayOfWeek(java.text.DateFormat paramDateFormat)
  {
    if (paramDateFormat == null) {
      return 0;
    }
    return paramDateFormat.getCalendar().getFirstDayOfWeek();
  }
  
  @CalledByNative
  public static String GetFormatForSkeleton(String paramString1, String paramString2)
  {
    if (Build.VERSION.SDK_INT < 18) {
      return null;
    }
    try
    {
      paramString1 = ICUProxy.GetLocale(paramString1);
      if (funGBDTP == null) {
        funGBDTP = android.text.format.DateFormat.class.getMethod("getBestDateTimePattern", new Class[] { Locale.class, String.class });
      }
      if (funGBDTP != null)
      {
        paramString1 = (String)funGBDTP.invoke(null, new Object[] { paramString1, paramString2 });
        return paramString1;
      }
      return null;
    }
    catch (Exception paramString1) {}
    return null;
  }
  
  @CalledByNative
  public static String GetNumberFormatSymbol(String paramString, int paramInt)
  {
    paramString = NumberFormat.getInstance(ICUProxy.GetLocale(paramString));
    if ((paramString instanceof DecimalFormat))
    {
      paramString = ((DecimalFormat)paramString).getDecimalFormatSymbols();
      StringBuilder localStringBuilder;
      if (paramInt == 0)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString.getDecimalSeparator());
        localStringBuilder.append("");
        return localStringBuilder.toString();
      }
      if (paramInt == 1)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString.getGroupingSeparator());
        localStringBuilder.append("");
        return localStringBuilder.toString();
      }
      if (paramInt == 4)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString.getZeroDigit());
        localStringBuilder.append("");
        return localStringBuilder.toString();
      }
    }
    return null;
  }
  
  @CalledByNative
  public static String ToPattern(java.text.DateFormat paramDateFormat)
  {
    if (paramDateFormat != null) {
      return ((SimpleDateFormat)paramDateFormat).toLocalizedPattern();
    }
    return "";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyDateFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */