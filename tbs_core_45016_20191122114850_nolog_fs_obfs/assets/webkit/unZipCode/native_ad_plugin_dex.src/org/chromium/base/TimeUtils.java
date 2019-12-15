package org.chromium.base;

import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("base::android")
@MainDex
public class TimeUtils
{
  public static native long nativeGetTimeTicksNowUs();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\TimeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */