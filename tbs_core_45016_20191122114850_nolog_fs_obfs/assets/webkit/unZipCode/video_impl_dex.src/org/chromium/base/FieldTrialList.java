package org.chromium.base;

import org.chromium.base.annotations.MainDex;

@MainDex
public class FieldTrialList
{
  public static String findFullName(String paramString)
  {
    return nativeFindFullName(paramString);
  }
  
  public static String getVariationParameter(String paramString1, String paramString2)
  {
    return nativeGetVariationParameter(paramString1, paramString2);
  }
  
  private static native String nativeFindFullName(String paramString);
  
  private static native String nativeGetVariationParameter(String paramString1, String paramString2);
  
  private static native boolean nativeTrialExists(String paramString);
  
  public static boolean trialExists(String paramString)
  {
    return nativeTrialExists(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\FieldTrialList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */