package org.chromium.device.time_zone_monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

@JNINamespace("device")
class TimeZoneMonitor
{
  private long jdField_a_of_type_Long;
  private final BroadcastReceiver jdField_a_of_type_AndroidContentBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (!paramAnonymousIntent.getAction().equals("android.intent.action.TIMEZONE_CHANGED"))
      {
        Log.e("cr_TimeZoneMonitor", "unexpected intent", new Object[0]);
        return;
      }
      paramAnonymousContext = TimeZoneMonitor.this;
      TimeZoneMonitor.a(paramAnonymousContext, TimeZoneMonitor.a(paramAnonymousContext));
    }
  };
  private final IntentFilter jdField_a_of_type_AndroidContentIntentFilter = new IntentFilter("android.intent.action.TIMEZONE_CHANGED");
  
  private TimeZoneMonitor(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    X5ApiCompatibilityUtils.x5RegisterReceiver(ContextUtils.getApplicationContext(), this.jdField_a_of_type_AndroidContentBroadcastReceiver, this.jdField_a_of_type_AndroidContentIntentFilter);
  }
  
  @CalledByNative
  static TimeZoneMonitor getInstance(long paramLong)
  {
    return new TimeZoneMonitor(paramLong);
  }
  
  private native void nativeTimeZoneChangedFromJava(long paramLong);
  
  @CalledByNative
  void stop()
  {
    X5ApiCompatibilityUtils.x5UnRegisterReceiver(ContextUtils.getApplicationContext(), this.jdField_a_of_type_AndroidContentBroadcastReceiver);
    this.jdField_a_of_type_Long = 0L;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\time_zone_monitor\TimeZoneMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */