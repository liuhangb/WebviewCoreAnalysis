package org.chromium.base;

public class BuildConfig
{
  public static String[] COMPRESSED_LOCALES = new String[0];
  public static boolean DCHECK_IS_ON = false;
  public static String FIREBASE_APP_ID = "";
  public static boolean IS_UBSAN = false;
  public static String[] UNCOMPRESSED_LOCALES = new String[0];
  
  public static boolean isMultidexEnabled()
  {
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\BuildConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */