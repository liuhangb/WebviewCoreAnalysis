package org.chromium.ui.display;

import android.annotation.TargetApi;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import org.chromium.base.CommandLine;

class PhysicalDisplayAndroid
  extends DisplayAndroid
{
  private static Float a;
  
  PhysicalDisplayAndroid(Display paramDisplay)
  {
    super(paramDisplay.getDisplayId());
  }
  
  private static int a(int paramInt)
  {
    if (paramInt == 1) {
      return 24;
    }
    PixelFormat localPixelFormat = new PixelFormat();
    PixelFormat.getPixelFormatInfo(paramInt, localPixelFormat);
    if (!PixelFormat.formatHasAlpha(paramInt)) {
      return localPixelFormat.bitsPerPixel;
    }
    if (paramInt != 1)
    {
      if (paramInt != 43)
      {
        switch (paramInt)
        {
        default: 
          return 24;
        case 7: 
          return 12;
        }
        return 15;
      }
      return 30;
    }
    if (d) {
      return 24;
    }
    throw new AssertionError();
  }
  
  private static boolean a()
  {
    Object localObject = a;
    boolean bool = false;
    if (localObject == null)
    {
      localObject = CommandLine.getInstance().getSwitchValue("force-device-scale-factor");
      if (localObject == null) {
        a = Float.valueOf(0.0F);
      }
    }
    try
    {
      a = Float.valueOf((String)localObject);
      float f = a.floatValue();
      if (f <= 0.0F) {
        i = 1;
      } else {
        i = 0;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      int i;
      for (;;) {}
    }
    i = 1;
    if (i != 0) {
      a = Float.valueOf(0.0F);
    }
    if (a.floatValue() > 0.0F) {
      bool = true;
    }
    return bool;
  }
  
  private static int b(int paramInt)
  {
    switch (paramInt)
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
  
  @TargetApi(17)
  void a(Display paramDisplay)
  {
    if (paramDisplay == null) {
      return;
    }
    Point localPoint = new Point();
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    if (Build.VERSION.SDK_INT >= 17)
    {
      try
      {
        paramDisplay.getRealSize(localPoint);
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
      paramDisplay.getRealMetrics(localDisplayMetrics);
    }
    else
    {
      paramDisplay.getSize(localPoint);
      paramDisplay.getMetrics(localDisplayMetrics);
    }
    if (a()) {
      localDisplayMetrics.density = a.floatValue();
    }
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (Build.VERSION.SDK_INT >= 26) {
      try
      {
        bool1 = paramDisplay.isWideColorGamut();
      }
      catch (NoSuchMethodError localNoSuchMethodError)
      {
        localNoSuchMethodError.printStackTrace();
        bool1 = bool2;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        bool1 = bool2;
      }
    }
    int i;
    if (Build.VERSION.SDK_INT < 17) {
      i = paramDisplay.getPixelFormat();
    } else {
      i = 1;
    }
    super.a(localPoint, Float.valueOf(localDisplayMetrics.density), Integer.valueOf(a(i)), Integer.valueOf(b(i)), Integer.valueOf(paramDisplay.getRotation()), Boolean.valueOf(bool1), null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\display\PhysicalDisplayAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */