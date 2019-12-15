package org.chromium.base;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.MainDex;
import org.chromium.tencent.base.TencentMemoryPressureListener;

@MainDex
public class MemoryPressureListener
{
  private static final String ACTION_LOW_MEMORY = "org.chromium.base.ACTION_LOW_MEMORY";
  private static final String ACTION_TRIM_MEMORY = "org.chromium.base.ACTION_TRIM_MEMORY";
  private static final String ACTION_TRIM_MEMORY_MODERATE = "org.chromium.base.ACTION_TRIM_MEMORY_MODERATE";
  private static final String ACTION_TRIM_MEMORY_RUNNING_CRITICAL = "org.chromium.base.ACTION_TRIM_MEMORY_RUNNING_CRITICAL";
  private static OnMemoryPressureCallbackForTesting sOnMemoryPressureCallbackForTesting;
  
  private static boolean handleDebugIntent(Activity paramActivity, String paramString)
  {
    if ("org.chromium.base.ACTION_LOW_MEMORY".equals(paramString))
    {
      simulateLowMemoryPressureSignal(paramActivity);
    }
    else if ("org.chromium.base.ACTION_TRIM_MEMORY".equals(paramString))
    {
      simulateTrimMemoryPressureSignal(paramActivity, 80);
    }
    else if ("org.chromium.base.ACTION_TRIM_MEMORY_RUNNING_CRITICAL".equals(paramString))
    {
      simulateTrimMemoryPressureSignal(paramActivity, 15);
    }
    else
    {
      if (!"org.chromium.base.ACTION_TRIM_MEMORY_MODERATE".equals(paramString)) {
        break label69;
      }
      simulateTrimMemoryPressureSignal(paramActivity, 60);
    }
    return true;
    label69:
    return false;
  }
  
  protected static native void nativeOnMemoryPressure(int paramInt);
  
  protected static native int nativeOnMemoryPressureInOrder(int paramInt1, int[] paramArrayOfInt, int paramInt2, boolean paramBoolean);
  
  public static void onMemoryPressure(int paramInt)
  {
    OnMemoryPressureCallbackForTesting localOnMemoryPressureCallbackForTesting = sOnMemoryPressureCallbackForTesting;
    if (localOnMemoryPressureCallbackForTesting != null) {
      localOnMemoryPressureCallbackForTesting.apply(paramInt);
    }
    nativeOnMemoryPressure(paramInt);
  }
  
  @VisibleForTesting
  @CalledByNative
  public static void registerSystemCallback()
  {
    ContextUtils.getApplicationContext().registerComponentCallbacks(new ComponentCallbacks2()
    {
      public void onConfigurationChanged(Configuration paramAnonymousConfiguration) {}
      
      public void onLowMemory()
      {
        TencentMemoryPressureListener.onLowMemory(ContextUtils.getApplicationContext());
      }
      
      public void onTrimMemory(int paramAnonymousInt)
      {
        TencentMemoryPressureListener.onTrimMemory(ContextUtils.getApplicationContext(), paramAnonymousInt);
      }
    });
  }
  
  @VisibleForTesting
  public static void setOnMemoryPressureCallbackForTesting(OnMemoryPressureCallbackForTesting paramOnMemoryPressureCallbackForTesting)
  {
    sOnMemoryPressureCallbackForTesting = paramOnMemoryPressureCallbackForTesting;
  }
  
  private static void simulateLowMemoryPressureSignal(Activity paramActivity)
  {
    paramActivity.getApplication().onLowMemory();
    paramActivity.onLowMemory();
  }
  
  private static void simulateTrimMemoryPressureSignal(Activity paramActivity, int paramInt)
  {
    paramActivity.getApplication().onTrimMemory(paramInt);
    paramActivity.onTrimMemory(paramInt);
  }
  
  @FunctionalInterface
  @VisibleForTesting
  public static abstract interface OnMemoryPressureCallbackForTesting
  {
    public abstract void apply(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\MemoryPressureListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */