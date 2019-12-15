package org.chromium.ui.base;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("ui")
final class ResourceBundle
{
  @CalledByNative
  private static String getLocalePakResourcePath(String paramString)
  {
    return "zh_cn.pak";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\base\ResourceBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */