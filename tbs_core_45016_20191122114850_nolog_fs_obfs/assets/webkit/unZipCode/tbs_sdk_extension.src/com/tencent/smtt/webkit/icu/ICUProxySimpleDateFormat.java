package com.tencent.smtt.webkit.icu;

import android.os.Build.VERSION;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class ICUProxySimpleDateFormat
{
  private static final String TAG = "ICU_PROXY_LOG(Java_ICUProxySimpleDateFormat)";
  private static boolean isSupportRef;
  private SplDtFmt m_obj;
  
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
  
  public ICUProxySimpleDateFormat(String paramString, Object paramObject)
  {
    if (isSupportRef)
    {
      this.m_obj = new c(paramString, paramObject);
      return;
    }
    this.m_obj = new d(paramString, paramObject);
  }
  
  @CalledByNative
  public static ICUProxySimpleDateFormat CreateSimpleDateFormat(String paramString1, String paramString2)
  {
    if (isSupportRef) {
      paramString2 = ICUProxyULocale.createULocale(paramString2);
    } else {
      paramString2 = ICUProxy.GetLocale(paramString2);
    }
    return new ICUProxySimpleDateFormat(paramString1, paramString2);
  }
  
  @CalledByNative
  public static String[] GetAvailableLocales()
  {
    if (isSupportRef) {
      return c.a();
    }
    return d.a();
  }
  
  @CalledByNative
  public String format(long paramLong)
  {
    SplDtFmt localSplDtFmt = this.m_obj;
    if (localSplDtFmt == null) {
      return null;
    }
    return localSplDtFmt.format(paramLong);
  }
  
  @CalledByNative
  public String getCalendarType()
  {
    SplDtFmt localSplDtFmt = this.m_obj;
    if (localSplDtFmt == null) {
      return null;
    }
    return localSplDtFmt.getCalendarType();
  }
  
  @CalledByNative
  public String getTimeZoneID()
  {
    SplDtFmt localSplDtFmt = this.m_obj;
    if (localSplDtFmt == null) {
      return null;
    }
    return localSplDtFmt.getTimeZoneID();
  }
  
  @CalledByNative
  public long parse(String paramString)
  {
    SplDtFmt localSplDtFmt = this.m_obj;
    if (localSplDtFmt == null) {
      return -1L;
    }
    return localSplDtFmt.parse(paramString);
  }
  
  @CalledByNative
  public void setCalendar(ICUProxyCalendar paramICUProxyCalendar)
  {
    SplDtFmt localSplDtFmt = this.m_obj;
    if (localSplDtFmt == null) {
      return;
    }
    localSplDtFmt.setCalendar(paramICUProxyCalendar);
  }
  
  @CalledByNative
  public String toPattern()
  {
    SplDtFmt localSplDtFmt = this.m_obj;
    if (localSplDtFmt == null) {
      return null;
    }
    return localSplDtFmt.toPattern();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxySimpleDateFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */