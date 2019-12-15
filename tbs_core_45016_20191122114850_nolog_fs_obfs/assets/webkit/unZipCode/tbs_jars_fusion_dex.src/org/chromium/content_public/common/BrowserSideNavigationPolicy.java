package org.chromium.content_public.common;

import org.chromium.base.annotations.JNINamespace;

@JNINamespace("content")
public final class BrowserSideNavigationPolicy
{
  public static boolean a()
  {
    return nativeIsBrowserSideNavigationEnabled();
  }
  
  private static native boolean nativeIsBrowserSideNavigationEnabled();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\common\BrowserSideNavigationPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */