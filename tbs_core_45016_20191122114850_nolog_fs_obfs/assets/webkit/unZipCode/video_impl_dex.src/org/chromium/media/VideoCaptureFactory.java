package org.chromium.media;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("media")
public class VideoCaptureFactory
{
  private static boolean b()
  {
    return Build.VERSION.SDK_INT >= 21;
  }
  
  @CalledByNative
  static VideoCapture createVideoCapture(int paramInt, long paramLong)
  {
    if ((b()) && (!VideoCaptureCamera2.a(paramInt))) {
      return new VideoCaptureCamera2(paramInt, paramLong);
    }
    return new VideoCaptureCamera(paramInt, paramLong);
  }
  
  @CalledByNative
  static int getCaptureApiType(int paramInt)
  {
    if (b()) {
      return VideoCaptureCamera2.a(paramInt);
    }
    return VideoCaptureCamera.a(paramInt);
  }
  
  @CalledByNative
  static int getCaptureFormatFramerate(VideoCaptureFormat paramVideoCaptureFormat)
  {
    return paramVideoCaptureFormat.c();
  }
  
  @CalledByNative
  static int getCaptureFormatHeight(VideoCaptureFormat paramVideoCaptureFormat)
  {
    return paramVideoCaptureFormat.b();
  }
  
  @CalledByNative
  static int getCaptureFormatPixelFormat(VideoCaptureFormat paramVideoCaptureFormat)
  {
    return paramVideoCaptureFormat.d();
  }
  
  @CalledByNative
  static int getCaptureFormatWidth(VideoCaptureFormat paramVideoCaptureFormat)
  {
    return paramVideoCaptureFormat.a();
  }
  
  @CalledByNative
  static String getDeviceName(int paramInt)
  {
    if ((b()) && (!VideoCaptureCamera2.a(paramInt))) {
      return VideoCaptureCamera2.a(paramInt);
    }
    return VideoCaptureCamera.a(paramInt);
  }
  
  @CalledByNative
  static VideoCaptureFormat[] getDeviceSupportedFormats(int paramInt)
  {
    if ((b()) && (!VideoCaptureCamera2.a(paramInt))) {
      return VideoCaptureCamera2.a(paramInt);
    }
    return VideoCaptureCamera.a(paramInt);
  }
  
  @CalledByNative
  public static int getNumberOfCameras()
  {
    return ChromiumCameraInfo.a();
  }
  
  public static boolean isCamera2LegacyOrDeprecatedDevice(int paramInt)
  {
    return (!b()) || (VideoCaptureCamera2.b(paramInt));
  }
  
  @CalledByNative
  public static boolean isLegacyOrDeprecatedDevice(int paramInt)
  {
    return (!b()) || (VideoCaptureCamera2.a(paramInt));
  }
  
  static class ChromiumCameraInfo
  {
    private static int a = -1;
    
    private static int b()
    {
      if (a == -1) {
        if ((Build.VERSION.SDK_INT < 23) && (ContextUtils.getApplicationContext().getPackageManager().checkPermission("android.permission.CAMERA", ContextUtils.getApplicationContext().getPackageName()) != 0)) {
          a = 0;
        } else if (VideoCaptureFactory.a()) {
          a = VideoCaptureCamera2.a();
        } else {
          a = VideoCaptureCamera.a();
        }
      }
      return a;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\VideoCaptureFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */