package org.chromium.tencent.ui.gfx;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

@JNINamespace("gfx")
public class DeviceDisplayInfo
{
  private final Context mAppContext;
  private DisplayMetrics mTempMetrics = new DisplayMetrics();
  private Point mTempPoint = new Point();
  private final WindowManager mWinManager;
  
  private DeviceDisplayInfo(Context paramContext)
  {
    this.mAppContext = paramContext.getApplicationContext();
    this.mWinManager = ((WindowManager)this.mAppContext.getSystemService("window"));
  }
  
  @CalledByNative
  public static DeviceDisplayInfo create(Context paramContext)
  {
    return new DeviceDisplayInfo(paramContext);
  }
  
  private Display getDisplay()
  {
    return this.mWinManager.getDefaultDisplay();
  }
  
  private DisplayMetrics getMetrics()
  {
    return this.mAppContext.getResources().getDisplayMetrics();
  }
  
  private int getPixelFormat()
  {
    if (Build.VERSION.SDK_INT < 17) {
      return getDisplay().getPixelFormat();
    }
    return 1;
  }
  
  @CalledByNative
  private int getSmallestDIPWidth()
  {
    return X5ApiCompatibilityUtils.smallestScreenWidthDp(this.mAppContext.getResources().getConfiguration());
  }
  
  private native void nativeUpdateSharedDeviceDisplayInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, double paramDouble, int paramInt7, int paramInt8);
  
  @CalledByNative
  public int getBitsPerComponent()
  {
    switch (getPixelFormat())
    {
    case 5: 
    default: 
      return 8;
    case 11: 
      return 2;
    case 8: 
    case 9: 
    case 10: 
      return 0;
    case 7: 
      return 4;
    case 6: 
      return 5;
    case 4: 
      return 5;
    }
    return 8;
  }
  
  @CalledByNative
  public int getBitsPerPixel()
  {
    int i = getPixelFormat();
    PixelFormat localPixelFormat = new PixelFormat();
    PixelFormat.getPixelFormatInfo(i, localPixelFormat);
    return localPixelFormat.bitsPerPixel;
  }
  
  @CalledByNative
  public double getDIPScale()
  {
    getDisplay().getMetrics(this.mTempMetrics);
    return this.mTempMetrics.density;
  }
  
  @CalledByNative
  public int getDisplayHeight()
  {
    if (Build.VERSION.SDK_INT < 13) {
      return getMetrics().heightPixels;
    }
    getDisplay().getSize(this.mTempPoint);
    return this.mTempPoint.y;
  }
  
  @CalledByNative
  public int getDisplayWidth()
  {
    if (Build.VERSION.SDK_INT < 13) {
      return getMetrics().heightPixels;
    }
    getDisplay().getSize(this.mTempPoint);
    return this.mTempPoint.x;
  }
  
  @TargetApi(17)
  @CalledByNative
  public int getPhysicalDisplayHeight()
  {
    if (Build.VERSION.SDK_INT < 17) {
      return 0;
    }
    getDisplay().getRealSize(this.mTempPoint);
    return this.mTempPoint.y;
  }
  
  @TargetApi(17)
  @CalledByNative
  public int getPhysicalDisplayWidth()
  {
    if (Build.VERSION.SDK_INT < 17) {
      return 0;
    }
    getDisplay().getRealSize(this.mTempPoint);
    return this.mTempPoint.x;
  }
  
  @CalledByNative
  public int getRotationDegrees()
  {
    switch (getDisplay().getRotation())
    {
    default: 
      return 0;
    case 3: 
      return 270;
    case 2: 
      return 180;
    case 1: 
      return 90;
    }
    return 0;
  }
  
  public void updateNativeSharedDisplayInfo()
  {
    nativeUpdateSharedDeviceDisplayInfo(getDisplayHeight(), getDisplayWidth(), getPhysicalDisplayHeight(), getPhysicalDisplayWidth(), getBitsPerPixel(), getBitsPerComponent(), getDIPScale(), getSmallestDIPWidth(), getRotationDegrees());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\ui\gfx\DeviceDisplayInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */