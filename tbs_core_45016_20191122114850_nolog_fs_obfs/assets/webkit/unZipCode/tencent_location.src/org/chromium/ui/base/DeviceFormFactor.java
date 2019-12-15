package org.chromium.ui.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;

public class DeviceFormFactor
{
  private static Boolean a;
  
  public static int a()
  {
    if (Build.VERSION.SDK_INT >= 17)
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)ContextUtils.getApplicationContext().getSystemService("window")).getDefaultDisplay().getRealMetrics(localDisplayMetrics);
      return Math.round(Math.min(localDisplayMetrics.heightPixels / localDisplayMetrics.density, localDisplayMetrics.widthPixels / localDisplayMetrics.density));
    }
    return ContextUtils.getApplicationContext().getResources().getConfiguration().smallestScreenWidthDp;
  }
  
  @CalledByNative
  public static boolean isTablet()
  {
    if (a == null)
    {
      boolean bool;
      if (a() >= 600) {
        bool = true;
      } else {
        bool = false;
      }
      a = Boolean.valueOf(bool);
    }
    return a.booleanValue();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\base\DeviceFormFactor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */