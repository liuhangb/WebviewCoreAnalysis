package org.chromium.ui.gfx;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.ui.gfx.ViewConfigurationWrapper;

@JNINamespace("gfx")
public class ViewConfigurationHelper
{
  private float jdField_a_of_type_Float = ContextUtils.getApplicationContext().getResources().getDisplayMetrics().density;
  private ViewConfigurationWrapper jdField_a_of_type_OrgChromiumTencentUiGfxViewConfigurationWrapper = ViewConfigurationWrapper.get(ContextUtils.getApplicationContext());
  
  private ViewConfigurationHelper()
  {
    if (!jdField_a_of_type_Boolean)
    {
      if (this.jdField_a_of_type_Float > 0.0F) {
        return;
      }
      throw new AssertionError();
    }
  }
  
  private float a(int paramInt)
  {
    return paramInt / this.jdField_a_of_type_Float;
  }
  
  private int a()
  {
    return (int)TypedValue.applyDimension(5, 12.0F, ContextUtils.getApplicationContext().getResources().getDisplayMetrics());
  }
  
  private void a()
  {
    ContextUtils.getApplicationContext().registerComponentCallbacks(new ComponentCallbacks()
    {
      public void onConfigurationChanged(Configuration paramAnonymousConfiguration)
      {
        ViewConfigurationHelper.a(ViewConfigurationHelper.this);
      }
      
      public void onLowMemory() {}
    });
  }
  
  private void b()
  {
    ViewConfigurationWrapper localViewConfigurationWrapper = ViewConfigurationWrapper.get(ContextUtils.getApplicationContext());
    if (this.jdField_a_of_type_OrgChromiumTencentUiGfxViewConfigurationWrapper == localViewConfigurationWrapper)
    {
      if (!jdField_a_of_type_Boolean)
      {
        if (this.jdField_a_of_type_Float == ContextUtils.getApplicationContext().getResources().getDisplayMetrics().density) {
          return;
        }
        throw new AssertionError();
      }
      return;
    }
    this.jdField_a_of_type_OrgChromiumTencentUiGfxViewConfigurationWrapper = localViewConfigurationWrapper;
    this.jdField_a_of_type_Float = ContextUtils.getApplicationContext().getResources().getDisplayMetrics().density;
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Float <= 0.0F)) {
      throw new AssertionError();
    }
    nativeUpdateSharedViewConfiguration(getMaximumFlingVelocity(), getMinimumFlingVelocity(), getTouchSlop(), getDoubleTapSlop(), getMinScalingSpan());
  }
  
  @CalledByNative
  private static ViewConfigurationHelper createWithListener()
  {
    ViewConfigurationHelper localViewConfigurationHelper = new ViewConfigurationHelper();
    localViewConfigurationHelper.a();
    return localViewConfigurationHelper;
  }
  
  @CalledByNative
  private float getDoubleTapSlop()
  {
    return a(this.jdField_a_of_type_OrgChromiumTencentUiGfxViewConfigurationWrapper.getScaledDoubleTapSlop());
  }
  
  @CalledByNative
  private static int getDoubleTapTimeout()
  {
    return ViewConfiguration.getDoubleTapTimeout();
  }
  
  @CalledByNative
  private static int getLongPressTimeout()
  {
    return ViewConfiguration.getLongPressTimeout();
  }
  
  @CalledByNative
  private float getMaximumFlingVelocity()
  {
    return a(this.jdField_a_of_type_OrgChromiumTencentUiGfxViewConfigurationWrapper.getScaledMaximumFlingVelocity());
  }
  
  @CalledByNative
  private float getMinScalingSpan()
  {
    return a(a());
  }
  
  @CalledByNative
  private float getMinimumFlingVelocity()
  {
    return a(this.jdField_a_of_type_OrgChromiumTencentUiGfxViewConfigurationWrapper.getScaledMinimumFlingVelocity());
  }
  
  @CalledByNative
  private static int getTapTimeout()
  {
    return ViewConfiguration.getTapTimeout();
  }
  
  @CalledByNative
  private float getTouchSlop()
  {
    return a(this.jdField_a_of_type_OrgChromiumTencentUiGfxViewConfigurationWrapper.getScaledTouchSlop());
  }
  
  private native void nativeUpdateSharedViewConfiguration(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\gfx\ViewConfigurationHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */