package com.tencent.smtt.webkit.icu;

import android.icu.util.TimeZone;
import android.os.Build.VERSION;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class ICUProxyTimeZone
{
  private static final String TAG = "ICU_PROXY_LOG(Java_ICUProxyTimeZone)";
  private static boolean isSupportRef;
  private TimeZone mTimeZone;
  
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
  
  ICUProxyTimeZone(TimeZone paramTimeZone)
  {
    this.mTimeZone = paramTimeZone;
  }
  
  @CalledByNative
  public static String getCanonicalID(String paramString)
  {
    return TimeZone.getCanonicalID(paramString);
  }
  
  @CalledByNative
  public static ICUProxyTimeZone getDefault()
  {
    TimeZone localTimeZone = TimeZone.getDefault();
    if (localTimeZone == null) {
      return null;
    }
    return new ICUProxyTimeZone(localTimeZone);
  }
  
  @CalledByNative
  public static ICUProxyTimeZone getTimeZone(String paramString)
  {
    paramString = TimeZone.getTimeZone(paramString);
    if (paramString == null) {
      return null;
    }
    return new ICUProxyTimeZone(paramString);
  }
  
  @CalledByNative
  public int getDstOffset(long paramLong, boolean paramBoolean)
  {
    TimeZone localTimeZone = this.mTimeZone;
    if (localTimeZone == null) {
      return 0;
    }
    int[] arrayOfInt = new int[2];
    localTimeZone.getOffset(paramLong, paramBoolean, arrayOfInt);
    return arrayOfInt[1];
  }
  
  @CalledByNative
  public String getID()
  {
    TimeZone localTimeZone = this.mTimeZone;
    if (localTimeZone == null) {
      return null;
    }
    return localTimeZone.getID();
  }
  
  public TimeZone getInternalTimeZone()
  {
    return this.mTimeZone;
  }
  
  @CalledByNative
  public int getOffset(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    TimeZone localTimeZone = this.mTimeZone;
    if (localTimeZone == null) {
      return 0;
    }
    return localTimeZone.getOffset(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  @CalledByNative
  public int getRawOffset()
  {
    TimeZone localTimeZone = this.mTimeZone;
    if (localTimeZone == null) {
      return 0;
    }
    return localTimeZone.getRawOffset();
  }
  
  @CalledByNative
  public void setRawOffset(int paramInt)
  {
    TimeZone localTimeZone = this.mTimeZone;
    if (localTimeZone == null) {
      return;
    }
    localTimeZone.setRawOffset(paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyTimeZone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */