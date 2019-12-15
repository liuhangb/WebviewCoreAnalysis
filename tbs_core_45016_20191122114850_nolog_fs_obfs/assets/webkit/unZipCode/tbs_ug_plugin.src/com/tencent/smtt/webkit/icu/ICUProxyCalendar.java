package com.tencent.smtt.webkit.icu;

import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build.VERSION;
import java.util.Date;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.PersistLog;

@JNINamespace("tencent")
public class ICUProxyCalendar
{
  private static final String TAG = "ICU_PROXY_LOG(Java_ICUProxyCalendar)";
  private static boolean isSupportRef;
  private Calendar mCalendar;
  
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
  
  ICUProxyCalendar(Calendar paramCalendar)
  {
    this.mCalendar = paramCalendar;
  }
  
  @CalledByNative
  public static ICUProxyCalendar getInstance(Object paramObject, String paramString)
  {
    try
    {
      if (!(paramObject instanceof ICUProxyTimeZone)) {
        return null;
      }
      paramObject = (ICUProxyTimeZone)paramObject;
      paramString = ICUProxyULocale.createULocale(paramString);
      paramObject = new ICUProxyCalendar(Calendar.getInstance(((ICUProxyTimeZone)paramObject).getInternalTimeZone(), paramString));
      return (ICUProxyCalendar)paramObject;
    }
    catch (Exception paramObject)
    {
      paramString = new StringBuilder();
      paramString.append("getInstance:");
      paramString.append(((Exception)paramObject).getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyCalendar)", paramString.toString());
    }
    return null;
  }
  
  public Calendar getCalendar()
  {
    return this.mCalendar;
  }
  
  @CalledByNative
  public Object getTimeZone()
  {
    Object localObject = this.mCalendar;
    if (localObject == null) {
      return null;
    }
    try
    {
      localObject = ((Calendar)localObject).getTimeZone();
      return localObject;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getTimeZone:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyCalendar)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public String getType()
  {
    Object localObject = this.mCalendar;
    if (localObject == null) {
      return null;
    }
    try
    {
      localObject = ((Calendar)localObject).getType();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getType:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyCalendar)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public int isGregorianCalendar()
  {
    Calendar localCalendar = this.mCalendar;
    if (localCalendar == null) {
      return 0;
    }
    if ((localCalendar instanceof GregorianCalendar)) {
      return 1;
    }
    return 0;
  }
  
  @CalledByNative
  public void setGregorianChange(double paramDouble)
  {
    Object localObject = this.mCalendar;
    if (localObject != null)
    {
      if (!(localObject instanceof GregorianCalendar)) {
        return;
      }
      try
      {
        localObject = new Date(paramDouble);
        ((GregorianCalendar)this.mCalendar).setGregorianChange((Date)localObject);
        return;
      }
      catch (Exception localException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("setGregorianChange:");
        localStringBuilder.append(localException.getMessage());
        PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyCalendar)", localStringBuilder.toString());
        return;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyCalendar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */