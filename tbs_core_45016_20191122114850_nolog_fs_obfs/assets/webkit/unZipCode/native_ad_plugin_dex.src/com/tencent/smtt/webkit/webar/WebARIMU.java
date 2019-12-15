package com.tencent.smtt.webkit.webar;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("webar")
public class WebARIMU
  implements SensorEventListener
{
  private static boolean jdField_a_of_type_Boolean = false;
  private long jdField_a_of_type_Long;
  private final Context jdField_a_of_type_AndroidContentContext;
  private Sensor jdField_a_of_type_AndroidHardwareSensor;
  private SensorManager jdField_a_of_type_AndroidHardwareSensorManager;
  private Sensor b;
  
  WebARIMU(Context paramContext, long paramLong)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_AndroidHardwareSensorManager = ((SensorManager)this.jdField_a_of_type_AndroidContentContext.getSystemService("sensor"));
    this.jdField_a_of_type_AndroidHardwareSensor = this.jdField_a_of_type_AndroidHardwareSensorManager.getDefaultSensor(9);
    this.b = this.jdField_a_of_type_AndroidHardwareSensorManager.getDefaultSensor(4);
  }
  
  @CalledByNative
  static WebARIMU createWebARIMU(Context paramContext, long paramLong)
  {
    return new WebARIMU(paramContext, paramLong);
  }
  
  private native void nativeUpdateGravity(long paramLong1, long paramLong2, float paramFloat1, float paramFloat2, float paramFloat3);
  
  private native void nativeUpdateGyroscope(long paramLong1, long paramLong2, float paramFloat1, float paramFloat2, float paramFloat3);
  
  @CalledByNative
  private static void setUseJavaTimestamp()
  {
    jdField_a_of_type_Boolean = true;
  }
  
  @CalledByNative
  private void start()
  {
    this.jdField_a_of_type_AndroidHardwareSensorManager.registerListener(this, this.jdField_a_of_type_AndroidHardwareSensor, 3);
    this.jdField_a_of_type_AndroidHardwareSensorManager.registerListener(this, this.b, 3);
  }
  
  @CalledByNative
  private void stop()
  {
    this.jdField_a_of_type_AndroidHardwareSensorManager.unregisterListener(this);
    this.jdField_a_of_type_Long = 0L;
  }
  
  public void onAccuracyChanged(Sensor paramSensor, int paramInt) {}
  
  public void onSensorChanged(SensorEvent paramSensorEvent)
  {
    if (this.jdField_a_of_type_Long != 0L)
    {
      long l = paramSensorEvent.timestamp;
      if (jdField_a_of_type_Boolean) {
        l = SystemClock.elapsedRealtimeNanos();
      }
      if (paramSensorEvent.sensor.getType() == 9)
      {
        nativeUpdateGravity(this.jdField_a_of_type_Long, l, paramSensorEvent.values[0], paramSensorEvent.values[1], paramSensorEvent.values[2]);
        return;
      }
      if (paramSensorEvent.sensor.getType() == 4) {
        nativeUpdateGyroscope(this.jdField_a_of_type_Long, l, paramSensorEvent.values[0], paramSensorEvent.values[1], paramSensorEvent.values[2]);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\webar\WebARIMU.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */