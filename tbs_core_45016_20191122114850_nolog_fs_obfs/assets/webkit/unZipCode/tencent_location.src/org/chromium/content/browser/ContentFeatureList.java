package org.chromium.content.browser;

import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("content::android")
@MainDex
public abstract class ContentFeatureList
{
  public static final String ENHANCED_SELECTION_INSERTION_HANDLE = "EnhancedSelectionInsertionHandle";
  public static final String REQUEST_UNBUFFERED_DISPATCH = "RequestUnbufferedDispatch";
  
  public static boolean isEnabled(String paramString)
  {
    return nativeIsEnabled(paramString);
  }
  
  private static native boolean nativeIsEnabled(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\ContentFeatureList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */