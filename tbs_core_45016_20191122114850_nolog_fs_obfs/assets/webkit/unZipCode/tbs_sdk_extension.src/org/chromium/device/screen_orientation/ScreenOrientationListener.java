package org.chromium.device.screen_orientation;

import android.content.Context;
import android.provider.Settings.System;
import org.chromium.base.ContextUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.ui.display.DisplayAndroid;

@JNINamespace("device")
class ScreenOrientationListener
{
  @CalledByNative
  static boolean isAutoRotateEnabledByUser()
  {
    return Settings.System.getInt(ContextUtils.getApplicationContext().getContentResolver(), "accelerometer_rotation", 0) == 1;
  }
  
  @CalledByNative
  static void startAccurateListening()
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run() {}
    });
  }
  
  @CalledByNative
  static void stopAccurateListening()
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run() {}
    });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\screen_orientation\ScreenOrientationListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */