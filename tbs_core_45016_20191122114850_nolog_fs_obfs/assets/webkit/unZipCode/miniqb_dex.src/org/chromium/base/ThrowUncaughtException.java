package org.chromium.base;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.MainDex;

@MainDex
abstract class ThrowUncaughtException
{
  @CalledByNative
  private static void post()
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        throw new RuntimeException("Intentional exception not caught by JNI");
      }
    });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\ThrowUncaughtException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */