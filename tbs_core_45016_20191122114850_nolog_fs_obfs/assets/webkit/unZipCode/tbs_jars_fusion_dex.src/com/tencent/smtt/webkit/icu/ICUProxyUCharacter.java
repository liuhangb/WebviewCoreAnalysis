package com.tencent.smtt.webkit.icu;

import android.icu.lang.UCharacter;
import android.os.Build.VERSION;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.PersistLog;

@JNINamespace("tencent")
public class ICUProxyUCharacter
{
  private static final String TAG = "ICU_PROXY_LOG(Java_ICUProxyUCharacter)";
  private static boolean isSupportRef;
  
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
  
  @CalledByNative
  public static int getCombiningClass(int paramInt)
  {
    try
    {
      paramInt = Integer.valueOf(UCharacter.getCombiningClass(paramInt)).intValue();
      return paramInt;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getCombiningClass:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyUCharacter)", localStringBuilder.toString());
    }
    return 0;
  }
  
  @CalledByNative
  public static int getIntPropertyValue(int paramInt1, int paramInt2)
  {
    try
    {
      paramInt1 = UCharacter.getIntPropertyValue(paramInt1, paramInt2);
      return paramInt1;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getIntPropertyValue:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyUCharacter)", localStringBuilder.toString());
    }
    return 0;
  }
  
  @CalledByNative
  public static int getPropertyEnum(String paramString)
  {
    try
    {
      int i = Integer.valueOf(UCharacter.getPropertyEnum(paramString)).intValue();
      return i;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getPropertyEnum:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyUCharacter)", localStringBuilder.toString());
    }
    return 0;
  }
  
  @CalledByNative
  public static String getPropertyName(int paramInt1, int paramInt2)
  {
    try
    {
      String str = UCharacter.getPropertyName(paramInt1, paramInt2);
      return str;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getPropertyName:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyUCharacter)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static int getPropertyValueEnum(int paramInt, String paramString)
  {
    try
    {
      paramInt = Integer.valueOf(UCharacter.getPropertyValueEnum(paramInt, paramString)).intValue();
      return paramInt;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getPropertyValueEnum:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyUCharacter)", localStringBuilder.toString());
    }
    return 0;
  }
  
  @CalledByNative
  public static String getPropertyValueName(int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      String str = UCharacter.getPropertyValueName(paramInt1, paramInt2, paramInt3);
      return str;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getPropertyValueName:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyUCharacter)", localStringBuilder.toString());
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyUCharacter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */