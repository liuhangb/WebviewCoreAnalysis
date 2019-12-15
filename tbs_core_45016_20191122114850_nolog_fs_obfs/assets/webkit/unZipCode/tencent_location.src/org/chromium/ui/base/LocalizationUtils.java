package org.chromium.ui.base;

import android.content.Context;
import android.content.res.Resources;
import java.util.Locale;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("l10n_util")
public class LocalizationUtils
{
  private static Boolean jdField_a_of_type_JavaLangBoolean;
  
  @CalledByNative
  private static String getDisplayNameForLocale(Locale paramLocale1, Locale paramLocale2)
  {
    return paramLocale1.getDisplayName(paramLocale2);
  }
  
  @CalledByNative
  private static Locale getJavaLocale(String paramString1, String paramString2, String paramString3)
  {
    return new Locale(paramString1, paramString2, paramString3);
  }
  
  @CalledByNative
  public static boolean isLayoutRtl()
  {
    if (jdField_a_of_type_JavaLangBoolean == null)
    {
      int i = ApiCompatibilityUtils.getLayoutDirection(ContextUtils.getApplicationContext().getResources().getConfiguration());
      boolean bool = true;
      if (i != 1) {
        bool = false;
      }
      jdField_a_of_type_JavaLangBoolean = Boolean.valueOf(bool);
    }
    return jdField_a_of_type_JavaLangBoolean.booleanValue();
  }
  
  private static native int nativeGetFirstStrongCharacterDirection(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\base\LocalizationUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */