package org.chromium.tencent.ui.gfx;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.ViewConfiguration;

public class ViewConfigurationWrapper
{
  private static final int DOUBLE_TAP_SLOP = 100;
  private static final int MAXIMUM_FLING_VELOCITY = 8000;
  private static final int MINIMUM_FLING_VELOCITY = 50;
  private static final int TOUCH_SLOP = 8;
  static final SparseArray<ViewConfigurationWrapper> sConfigurations = new SparseArray(2);
  private float mDensity;
  private ViewConfiguration mViewConfiguration;
  
  private ViewConfigurationWrapper(Context paramContext)
  {
    try
    {
      this.mViewConfiguration = ViewConfiguration.get(paramContext);
      this.mDensity = paramContext.getResources().getDisplayMetrics().density;
      return;
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
  }
  
  public static ViewConfigurationWrapper get(Context paramContext)
  {
    int i = (int)(paramContext.getResources().getDisplayMetrics().density * 100.0F);
    ViewConfigurationWrapper localViewConfigurationWrapper2 = (ViewConfigurationWrapper)sConfigurations.get(i);
    ViewConfigurationWrapper localViewConfigurationWrapper1 = localViewConfigurationWrapper2;
    if (localViewConfigurationWrapper2 == null)
    {
      localViewConfigurationWrapper1 = new ViewConfigurationWrapper(paramContext);
      sConfigurations.put(i, localViewConfigurationWrapper1);
    }
    return localViewConfigurationWrapper1;
  }
  
  public int getScaledDoubleTapSlop()
  {
    ViewConfiguration localViewConfiguration = this.mViewConfiguration;
    if (localViewConfiguration != null) {
      return localViewConfiguration.getScaledDoubleTapSlop();
    }
    return (int)(this.mDensity * 100.0F);
  }
  
  public int getScaledMaximumFlingVelocity()
  {
    ViewConfiguration localViewConfiguration = this.mViewConfiguration;
    if (localViewConfiguration != null) {
      return localViewConfiguration.getScaledMaximumFlingVelocity();
    }
    return (int)(this.mDensity * 8000.0F);
  }
  
  public int getScaledMinimumFlingVelocity()
  {
    ViewConfiguration localViewConfiguration = this.mViewConfiguration;
    if (localViewConfiguration != null) {
      return localViewConfiguration.getScaledMinimumFlingVelocity();
    }
    return (int)(this.mDensity * 50.0F);
  }
  
  public int getScaledTouchSlop()
  {
    ViewConfiguration localViewConfiguration = this.mViewConfiguration;
    if (localViewConfiguration != null) {
      return localViewConfiguration.getScaledTouchSlop();
    }
    return (int)(this.mDensity * 8.0F);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\ui\gfx\ViewConfigurationWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */