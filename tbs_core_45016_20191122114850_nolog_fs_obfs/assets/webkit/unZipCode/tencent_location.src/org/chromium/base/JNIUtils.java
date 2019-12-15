package org.chromium.base;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.MainDex;

@MainDex
public class JNIUtils
{
  private static Boolean sSelectiveJniRegistrationEnabled;
  
  public static void enableSelectiveJniRegistration()
  {
    sSelectiveJniRegistrationEnabled = Boolean.valueOf(true);
  }
  
  @CalledByNative
  public static Object getClassLoader()
  {
    return JNIUtils.class.getClassLoader();
  }
  
  @CalledByNative
  public static boolean isSelectiveJniRegistrationEnabled()
  {
    if (sSelectiveJniRegistrationEnabled == null) {
      sSelectiveJniRegistrationEnabled = Boolean.valueOf(false);
    }
    return sSelectiveJniRegistrationEnabled.booleanValue();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\JNIUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */