package com.tencent.smtt.webkit.icu;

import android.text.TextUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.chromium.tencent.utils.PersistLog;

public class d
  extends SplDtFmt
{
  private SimpleDateFormat a;
  
  d(String paramString, Object paramObject)
  {
    this.a = new SimpleDateFormat(paramString, (Locale)paramObject);
  }
  
  public static String[] a()
  {
    ArrayList localArrayList = new ArrayList();
    Locale[] arrayOfLocale = DateFormat.getAvailableLocales();
    int j = arrayOfLocale.length;
    int i = 0;
    while (i < j)
    {
      String str = ICUProxy.getLocaleName(arrayOfLocale[i]);
      if (!TextUtils.isEmpty(str)) {
        localArrayList.add(str);
      }
      i += 1;
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public String format(long paramLong)
  {
    java.sql.Date localDate = new java.sql.Date(paramLong);
    return this.a.format(localDate);
  }
  
  public String getCalendarType()
  {
    if ((this.a.getCalendar() instanceof GregorianCalendar)) {
      return "gregorian";
    }
    return "unknown";
  }
  
  public String getTimeZoneID()
  {
    return this.a.getTimeZone().getID();
  }
  
  public long parse(String paramString)
  {
    try
    {
      long l = this.a.parse(paramString).getTime();
      return l;
    }
    catch (ParseException paramString)
    {
      PersistLog.printStackTrace(paramString);
    }
    return -1L;
  }
  
  public void setCalendar(ICUProxyCalendar paramICUProxyCalendar) {}
  
  public String toPattern()
  {
    return this.a.toPattern();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */