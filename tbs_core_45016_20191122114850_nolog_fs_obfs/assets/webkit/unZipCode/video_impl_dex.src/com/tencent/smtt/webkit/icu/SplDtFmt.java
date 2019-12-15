package com.tencent.smtt.webkit.icu;

public abstract class SplDtFmt
{
  public abstract String format(long paramLong);
  
  public abstract String getCalendarType();
  
  public abstract String getTimeZoneID();
  
  public abstract long parse(String paramString);
  
  public abstract void setCalendar(ICUProxyCalendar paramICUProxyCalendar);
  
  public abstract String toPattern();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\SplDtFmt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */