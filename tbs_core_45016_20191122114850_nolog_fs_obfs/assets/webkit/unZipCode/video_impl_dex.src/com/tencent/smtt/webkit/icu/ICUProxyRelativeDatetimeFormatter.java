package com.tencent.smtt.webkit.icu;

import android.icu.text.RelativeDateTimeFormatter;
import android.icu.text.RelativeDateTimeFormatter.AbsoluteUnit;
import android.icu.text.RelativeDateTimeFormatter.Direction;
import android.icu.text.RelativeDateTimeFormatter.RelativeUnit;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.PersistLog;

@JNINamespace("tencent")
public class ICUProxyRelativeDatetimeFormatter
{
  private static final String TAG = "ICU_PROXY_LOG(Java_ICUProxyRelativeDatetimeFormatter)";
  private RelativeDateTimeFormatter mRdtf;
  
  ICUProxyRelativeDatetimeFormatter(RelativeDateTimeFormatter paramRelativeDateTimeFormatter)
  {
    this.mRdtf = paramRelativeDateTimeFormatter;
  }
  
  private RelativeDateTimeFormatter.AbsoluteUnit convertAbsoluteUnit(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return RelativeDateTimeFormatter.AbsoluteUnit.SUNDAY;
    case 12: 
      return RelativeDateTimeFormatter.AbsoluteUnit.SATURDAY;
    case 11: 
      return RelativeDateTimeFormatter.AbsoluteUnit.NOW;
    case 10: 
      return RelativeDateTimeFormatter.AbsoluteUnit.YEAR;
    case 9: 
      return RelativeDateTimeFormatter.AbsoluteUnit.MONTH;
    case 8: 
      return RelativeDateTimeFormatter.AbsoluteUnit.WEEK;
    case 7: 
      return RelativeDateTimeFormatter.AbsoluteUnit.DAY;
    case 6: 
      return RelativeDateTimeFormatter.AbsoluteUnit.SATURDAY;
    case 5: 
      return RelativeDateTimeFormatter.AbsoluteUnit.FRIDAY;
    case 4: 
      return RelativeDateTimeFormatter.AbsoluteUnit.THURSDAY;
    case 3: 
      return RelativeDateTimeFormatter.AbsoluteUnit.WEDNESDAY;
    case 2: 
      return RelativeDateTimeFormatter.AbsoluteUnit.TUESDAY;
    case 1: 
      return RelativeDateTimeFormatter.AbsoluteUnit.MONDAY;
    }
    return RelativeDateTimeFormatter.AbsoluteUnit.SUNDAY;
  }
  
  private RelativeDateTimeFormatter.Direction convertDirection(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return RelativeDateTimeFormatter.Direction.LAST_2;
    case 5: 
      return RelativeDateTimeFormatter.Direction.PLAIN;
    case 4: 
      return RelativeDateTimeFormatter.Direction.NEXT_2;
    case 3: 
      return RelativeDateTimeFormatter.Direction.NEXT;
    case 2: 
      return RelativeDateTimeFormatter.Direction.THIS;
    case 1: 
      return RelativeDateTimeFormatter.Direction.LAST;
    }
    return RelativeDateTimeFormatter.Direction.LAST_2;
  }
  
  private RelativeDateTimeFormatter.RelativeUnit convertRelativeUnit(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return RelativeDateTimeFormatter.RelativeUnit.SECONDS;
    case 6: 
      return RelativeDateTimeFormatter.RelativeUnit.YEARS;
    case 5: 
      return RelativeDateTimeFormatter.RelativeUnit.MONTHS;
    case 4: 
      return RelativeDateTimeFormatter.RelativeUnit.WEEKS;
    case 3: 
      return RelativeDateTimeFormatter.RelativeUnit.DAYS;
    case 2: 
      return RelativeDateTimeFormatter.RelativeUnit.HOURS;
    case 1: 
      return RelativeDateTimeFormatter.RelativeUnit.MINUTES;
    }
    return RelativeDateTimeFormatter.RelativeUnit.SECONDS;
  }
  
  @CalledByNative
  public static ICUProxyRelativeDatetimeFormatter getInstance(String paramString, int paramInt1, int paramInt2)
  {
    try
    {
      paramString = new ICUProxyRelativeDatetimeFormatter(RelativeDateTimeFormatter.getInstance(ICUProxyULocale.createULocale(paramString)));
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getInstance:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyRelativeDatetimeFormatter)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public String format(double paramDouble, int paramInt1, int paramInt2)
  {
    Object localObject = this.mRdtf;
    if (localObject == null) {
      return null;
    }
    try
    {
      localObject = ((RelativeDateTimeFormatter)localObject).format(paramDouble, convertDirection(paramInt1), convertRelativeUnit(paramInt2));
      return (String)localObject;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("format:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyRelativeDatetimeFormatter)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public String formatAbsoluteUnit(int paramInt1, int paramInt2)
  {
    Object localObject = this.mRdtf;
    if (localObject == null) {
      return null;
    }
    try
    {
      localObject = ((RelativeDateTimeFormatter)localObject).format(convertDirection(paramInt1), convertAbsoluteUnit(paramInt2));
      return (String)localObject;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("formatAbsoluteUnit:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyRelativeDatetimeFormatter)", localStringBuilder.toString());
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyRelativeDatetimeFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */