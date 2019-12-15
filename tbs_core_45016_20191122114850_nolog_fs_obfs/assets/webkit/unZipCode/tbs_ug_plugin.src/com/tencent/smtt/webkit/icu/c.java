package com.tencent.smtt.webkit.icu;

import android.icu.text.SimpleDateFormat;
import android.icu.util.GregorianCalendar;
import android.icu.util.ULocale;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import org.chromium.tencent.utils.PersistLog;

public class c
  extends SplDtFmt
{
  private SimpleDateFormat a;
  
  c(String paramString, Object paramObject)
  {
    try
    {
      this.a = new SimpleDateFormat(paramString, (ULocale)paramObject);
      return;
    }
    catch (IllegalArgumentException paramString)
    {
      PersistLog.printStackTrace(paramString);
    }
  }
  
  public static String[] a()
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      Locale[] arrayOfLocale = SimpleDateFormat.getAvailableLocales();
      int j = arrayOfLocale.length;
      int i = 0;
      while (i < j)
      {
        localObject = ICUProxy.getLocaleName(arrayOfLocale[i]);
        if (!TextUtils.isEmpty((CharSequence)localObject)) {
          localArrayList.add(localObject);
        }
        i += 1;
      }
      return (String[])localArrayList.toArray(new String[localArrayList.size()]);
    }
    catch (Exception localException)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("GetAvailableLocales:");
      ((StringBuilder)localObject).append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalSimpleDateFormatNew)", ((StringBuilder)localObject).toString());
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public String format(long paramLong)
  {
    Object localObject1 = new Date(paramLong);
    Object localObject2 = this.a;
    if (localObject2 == null) {
      return null;
    }
    try
    {
      localObject1 = ((SimpleDateFormat)localObject2).format((Date)localObject1);
      return (String)localObject1;
    }
    catch (Exception localException)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("format:");
      ((StringBuilder)localObject2).append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalSimpleDateFormatNew)", ((StringBuilder)localObject2).toString());
    }
    return null;
  }
  
  public String getCalendarType()
  {
    SimpleDateFormat localSimpleDateFormat = this.a;
    if (localSimpleDateFormat == null) {
      return "unknown";
    }
    try
    {
      if ((localSimpleDateFormat.getCalendar() instanceof GregorianCalendar)) {
        return "gregorian";
      }
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getCalendarType:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalSimpleDateFormatNew)", localStringBuilder.toString());
    }
    return "unknown";
  }
  
  public String getTimeZoneID()
  {
    Object localObject = this.a;
    if (localObject == null) {
      return null;
    }
    try
    {
      localObject = new ICUProxyTimeZone(((SimpleDateFormat)localObject).getTimeZone()).getID();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getTimeZoneID:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalSimpleDateFormatNew)", localStringBuilder.toString());
    }
    return null;
  }
  
  public long parse(String paramString)
  {
    Object localObject = this.a;
    if (localObject == null) {
      return -1L;
    }
    try
    {
      long l = ((SimpleDateFormat)localObject).parse(paramString).getTime();
      return l;
    }
    catch (Exception paramString)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("format:");
      ((StringBuilder)localObject).append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalSimpleDateFormatNew)", ((StringBuilder)localObject).toString());
    }
    return -1L;
  }
  
  public void setCalendar(ICUProxyCalendar paramICUProxyCalendar)
  {
    Object localObject = this.a;
    if (localObject == null) {
      return;
    }
    try
    {
      ((SimpleDateFormat)localObject).setCalendar(paramICUProxyCalendar.getCalendar());
      return;
    }
    catch (Exception paramICUProxyCalendar)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("setCalendar:");
      ((StringBuilder)localObject).append(paramICUProxyCalendar.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalSimpleDateFormatNew)", ((StringBuilder)localObject).toString());
    }
  }
  
  public String toPattern()
  {
    Object localObject = this.a;
    if (localObject == null) {
      return null;
    }
    try
    {
      localObject = ((SimpleDateFormat)localObject).toPattern();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("toPattern:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalSimpleDateFormatNew)", localStringBuilder.toString());
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */