package com.tencent.smtt.webkit.icu;

import android.icu.text.DateTimePatternGenerator;
import android.os.Build.VERSION;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.PersistLog;

@JNINamespace("tencent")
public class ICUProxyDateTimePatternGenerator
{
  private static final String TAG = "ICU_PROXY_LOG(Java_DateTimePatternGenerator)";
  private static boolean isSupportRef;
  private DateTimePatternGenerator mPatternGen;
  
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
  
  ICUProxyDateTimePatternGenerator(DateTimePatternGenerator paramDateTimePatternGenerator)
  {
    this.mPatternGen = paramDateTimePatternGenerator;
  }
  
  @CalledByNative
  public static ICUProxyDateTimePatternGenerator getInstance(String paramString)
  {
    try
    {
      paramString = new ICUProxyDateTimePatternGenerator(DateTimePatternGenerator.getInstance(ICUProxyULocale.createULocale(paramString)));
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getInstance:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_DateTimePatternGenerator)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public String getBestPattern(String paramString)
  {
    Object localObject = this.mPatternGen;
    if (localObject == null) {
      return null;
    }
    try
    {
      paramString = ((DateTimePatternGenerator)localObject).getBestPattern(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getBestPattern:");
      ((StringBuilder)localObject).append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_DateTimePatternGenerator)", ((StringBuilder)localObject).toString());
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyDateTimePatternGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */