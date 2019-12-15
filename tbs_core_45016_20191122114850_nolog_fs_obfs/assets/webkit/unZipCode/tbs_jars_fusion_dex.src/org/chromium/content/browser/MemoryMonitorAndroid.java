package org.chromium.content.browser;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("content")
class MemoryMonitorAndroid
{
  private static final ActivityManager.MemoryInfo jdField_a_of_type_AndroidAppActivityManager$MemoryInfo = new ActivityManager.MemoryInfo();
  private static ComponentCallbacks2 jdField_a_of_type_AndroidContentComponentCallbacks2;
  
  @CalledByNative
  private static void getMemoryInfo(long paramLong)
  {
    ActivityManager localActivityManager = (ActivityManager)ContextUtils.getApplicationContext().getSystemService("activity");
    try
    {
      localActivityManager.getMemoryInfo(jdField_a_of_type_AndroidAppActivityManager$MemoryInfo);
    }
    catch (RuntimeException localRuntimeException)
    {
      Log.e("MemoryMonitorAndroid", "Failed to get memory info due to a runtime exception: %s", new Object[] { localRuntimeException });
      ActivityManager.MemoryInfo localMemoryInfo = jdField_a_of_type_AndroidAppActivityManager$MemoryInfo;
      localMemoryInfo.availMem = 1L;
      localMemoryInfo.lowMemory = true;
      localMemoryInfo.threshold = 1L;
      localMemoryInfo.totalMem = 1L;
    }
    nativeGetMemoryInfoCallback(jdField_a_of_type_AndroidAppActivityManager$MemoryInfo.availMem, jdField_a_of_type_AndroidAppActivityManager$MemoryInfo.lowMemory, jdField_a_of_type_AndroidAppActivityManager$MemoryInfo.threshold, jdField_a_of_type_AndroidAppActivityManager$MemoryInfo.totalMem, paramLong);
  }
  
  private static native void nativeGetMemoryInfoCallback(long paramLong1, boolean paramBoolean, long paramLong2, long paramLong3, long paramLong4);
  
  private static native void nativeOnTrimMemory(int paramInt);
  
  @CalledByNative
  private static void registerComponentCallbacks()
  {
    jdField_a_of_type_AndroidContentComponentCallbacks2 = new ComponentCallbacks2()
    {
      public void onConfigurationChanged(Configuration paramAnonymousConfiguration) {}
      
      public void onLowMemory() {}
      
      public void onTrimMemory(int paramAnonymousInt)
      {
        if (paramAnonymousInt != 20) {
          MemoryMonitorAndroid.a(paramAnonymousInt);
        }
      }
    };
    ContextUtils.getApplicationContext().registerComponentCallbacks(jdField_a_of_type_AndroidContentComponentCallbacks2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\MemoryMonitorAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */