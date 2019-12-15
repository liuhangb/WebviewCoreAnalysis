package org.chromium.components.url_formatter;

import org.chromium.base.annotations.JNINamespace;

@JNINamespace("url_formatter::android")
public final class UrlFormatter
{
  private static native String nativeFixupUrl(String paramString);
  
  private static native String nativeFormatUrlForDisplay(String paramString);
  
  private static native String nativeFormatUrlForSecurityDisplay(String paramString);
  
  private static native String nativeFormatUrlForSecurityDisplayOmitScheme(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\url_formatter\UrlFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */