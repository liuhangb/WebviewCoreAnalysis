package org.chromium.base.metrics;

import org.chromium.base.annotations.JNINamespace;

@JNINamespace("base::android")
public final class StatisticsRecorderAndroid
{
  private static native String nativeToJson(int paramInt);
  
  public static String toJson(int paramInt)
  {
    return nativeToJson(paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\metrics\StatisticsRecorderAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */