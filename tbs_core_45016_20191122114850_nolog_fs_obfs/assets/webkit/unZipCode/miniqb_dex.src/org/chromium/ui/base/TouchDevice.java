package org.chromium.ui.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.InputDevice;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("ui")
public class TouchDevice
{
  private static boolean a(int paramInt1, int paramInt2)
  {
    return (paramInt1 & paramInt2) == paramInt2;
  }
  
  @CalledByNative
  public static int[] availablePointerAndHoverTypes()
  {
    int[] arrayOfInt1 = new int[2];
    int[] tmp7_5 = arrayOfInt1;
    tmp7_5[0] = 0;
    int[] tmp11_7 = tmp7_5;
    tmp11_7[1] = 0;
    tmp11_7;
    int[] arrayOfInt2 = InputDevice.getDeviceIds();
    int j = arrayOfInt2.length;
    int i = 0;
    while (i < j)
    {
      int k = arrayOfInt2[i];
      Object localObject = null;
      try
      {
        InputDevice localInputDevice = InputDevice.getDevice(k);
        localObject = localInputDevice;
      }
      catch (RuntimeException localRuntimeException)
      {
        for (;;) {}
      }
      if (localObject != null)
      {
        k = ((InputDevice)localObject).getSources();
        if ((!a(k, 8194)) && (!a(k, 16386)) && (!a(k, 1048584)) && (!a(k, 65540)))
        {
          if (a(k, 4098)) {
            arrayOfInt1[0] |= 0x2;
          }
        }
        else {
          arrayOfInt1[0] |= 0x4;
        }
        if ((a(k, 8194)) || (a(k, 1048584)) || (a(k, 65540))) {
          arrayOfInt1[1] |= 0x2;
        }
      }
      i += 1;
    }
    if (arrayOfInt1[0] == 0) {
      arrayOfInt1[0] = 1;
    }
    if (arrayOfInt1[1] == 0) {
      arrayOfInt1[1] = 1;
    }
    return arrayOfInt1;
  }
  
  @CalledByNative
  private static int maxTouchPoints()
  {
    try
    {
      if (ContextUtils.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.touchscreen.multitouch.jazzhand")) {
        return 5;
      }
      if (ContextUtils.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.touchscreen.multitouch.distinct")) {
        return 2;
      }
      if (ContextUtils.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.touchscreen.multitouch")) {
        return 2;
      }
      boolean bool = ContextUtils.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.touchscreen");
      if (bool) {
        return 1;
      }
      return 0;
    }
    catch (Throwable localThrowable) {}
    return 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\base\TouchDevice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */