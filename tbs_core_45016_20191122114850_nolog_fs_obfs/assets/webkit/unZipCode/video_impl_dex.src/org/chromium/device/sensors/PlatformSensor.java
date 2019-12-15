package org.chromium.device.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build.VERSION;
import java.util.List;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("device")
public class PlatformSensor
  implements SensorEventListener
{
  private double jdField_a_of_type_Double;
  private final int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private final Sensor jdField_a_of_type_AndroidHardwareSensor;
  private final PlatformSensorProvider jdField_a_of_type_OrgChromiumDeviceSensorsPlatformSensorProvider;
  private double[] jdField_a_of_type_ArrayOfDouble = new double[3];
  private float[] jdField_a_of_type_ArrayOfFloat = new float[9];
  private final int b;
  
  protected PlatformSensor(Sensor paramSensor, int paramInt, PlatformSensorProvider paramPlatformSensorProvider)
  {
    this.b = paramInt;
    this.jdField_a_of_type_OrgChromiumDeviceSensorsPlatformSensorProvider = paramPlatformSensorProvider;
    this.jdField_a_of_type_AndroidHardwareSensor = paramSensor;
    this.jdField_a_of_type_Int = this.jdField_a_of_type_AndroidHardwareSensor.getMinDelay();
  }
  
  private int a(double paramDouble)
  {
    return (int)(1.0D / paramDouble * 1000000.0D);
  }
  
  public static PlatformSensor a(int paramInt1, int paramInt2, PlatformSensorProvider paramPlatformSensorProvider)
  {
    List localList = paramPlatformSensorProvider.a().getSensorList(paramInt1);
    if (localList.isEmpty()) {
      return null;
    }
    return new PlatformSensor((Sensor)localList.get(0), paramInt2, paramPlatformSensorProvider);
  }
  
  private void a(float[] paramArrayOfFloat, double[] paramArrayOfDouble)
  {
    SensorManager.getRotationMatrixFromVector(this.jdField_a_of_type_ArrayOfFloat, paramArrayOfFloat);
    a(this.jdField_a_of_type_ArrayOfFloat, paramArrayOfDouble);
    int i = 0;
    while (i < 3)
    {
      paramArrayOfDouble[i] = Math.toDegrees(paramArrayOfDouble[i]);
      i += 1;
    }
  }
  
  private static double[] a(float[] paramArrayOfFloat, double[] paramArrayOfDouble)
  {
    if (paramArrayOfFloat.length != 9) {
      return paramArrayOfDouble;
    }
    if (paramArrayOfFloat[8] > 0.0F)
    {
      paramArrayOfDouble[0] = Math.atan2(-paramArrayOfFloat[1], paramArrayOfFloat[4]);
      paramArrayOfDouble[1] = Math.asin(paramArrayOfFloat[7]);
      paramArrayOfDouble[2] = Math.atan2(-paramArrayOfFloat[6], paramArrayOfFloat[8]);
    }
    else
    {
      double d2;
      double d1;
      if (paramArrayOfFloat[8] < 0.0F)
      {
        paramArrayOfDouble[0] = Math.atan2(paramArrayOfFloat[1], -paramArrayOfFloat[4]);
        paramArrayOfDouble[1] = (-Math.asin(paramArrayOfFloat[7]));
        d2 = paramArrayOfDouble[1];
        if (paramArrayOfDouble[1] >= 0.0D) {
          d1 = -3.141592653589793D;
        } else {
          d1 = 3.141592653589793D;
        }
        paramArrayOfDouble[1] = (d2 + d1);
        paramArrayOfDouble[2] = Math.atan2(paramArrayOfFloat[6], -paramArrayOfFloat[8]);
      }
      else
      {
        float f = paramArrayOfFloat[6];
        d1 = -1.5707963267948966D;
        if (f > 0.0F)
        {
          paramArrayOfDouble[0] = Math.atan2(-paramArrayOfFloat[1], paramArrayOfFloat[4]);
          paramArrayOfDouble[1] = Math.asin(paramArrayOfFloat[7]);
          paramArrayOfDouble[2] = -1.5707963267948966D;
        }
        else if (paramArrayOfFloat[6] < 0.0F)
        {
          paramArrayOfDouble[0] = Math.atan2(paramArrayOfFloat[1], -paramArrayOfFloat[4]);
          paramArrayOfDouble[1] = (-Math.asin(paramArrayOfFloat[7]));
          d2 = paramArrayOfDouble[1];
          if (paramArrayOfDouble[1] >= 0.0D) {
            d1 = -3.141592653589793D;
          } else {
            d1 = 3.141592653589793D;
          }
          paramArrayOfDouble[1] = (d2 + d1);
          paramArrayOfDouble[2] = -1.5707963267948966D;
        }
        else
        {
          paramArrayOfDouble[0] = Math.atan2(paramArrayOfFloat[3], paramArrayOfFloat[0]);
          if (paramArrayOfFloat[7] > 0.0F) {
            d1 = 1.5707963267948966D;
          }
          paramArrayOfDouble[1] = d1;
          paramArrayOfDouble[2] = 0.0D;
        }
      }
    }
    if (paramArrayOfDouble[0] < 0.0D) {
      paramArrayOfDouble[0] += 6.283185307179586D;
    }
    return paramArrayOfDouble;
  }
  
  private void b()
  {
    if (this.jdField_a_of_type_Double == 0.0D) {
      return;
    }
    this.jdField_a_of_type_OrgChromiumDeviceSensorsPlatformSensorProvider.a().unregisterListener(this, this.jdField_a_of_type_AndroidHardwareSensor);
  }
  
  private native void nativeNotifyConvertByJava(long paramLong, boolean paramBoolean);
  
  private native void nativeNotifyPlatformSensorError(long paramLong);
  
  private native void nativeUpdatePlatformSensorReading(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5);
  
  protected void a()
  {
    nativeNotifyPlatformSensorError(this.jdField_a_of_type_Long);
  }
  
  protected void a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5)
  {
    nativeUpdatePlatformSensorReading(this.jdField_a_of_type_Long, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5);
  }
  
  @CalledByNative
  protected boolean checkSensorConfiguration(double paramDouble)
  {
    return this.jdField_a_of_type_Int <= a(paramDouble);
  }
  
  @CalledByNative
  protected double getDefaultConfiguration()
  {
    return 5.0D;
  }
  
  @CalledByNative
  protected double getMaximumSupportedFrequency()
  {
    int i = this.jdField_a_of_type_Int;
    if (i == 0) {
      return getDefaultConfiguration();
    }
    double d = i;
    Double.isNaN(d);
    return 1.0D / (d * 1.0E-6D);
  }
  
  @CalledByNative
  protected int getReportingMode()
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      if (this.jdField_a_of_type_AndroidHardwareSensor.getReportingMode() == 0) {
        return 1;
      }
      return 0;
    }
    return 1;
  }
  
  @CalledByNative
  protected void initPlatformSensorAndroid(long paramLong)
  {
    if ((!jdField_a_of_type_Boolean) && (paramLong == 0L)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_Long = paramLong;
  }
  
  public void onAccuracyChanged(Sensor paramSensor, int paramInt) {}
  
  public void onSensorChanged(SensorEvent paramSensorEvent)
  {
    if (this.jdField_a_of_type_Long == 0L) {
      return;
    }
    double d = paramSensorEvent.timestamp;
    Double.isNaN(d);
    d *= 1.0E-9D;
    if ((this.jdField_a_of_type_AndroidHardwareSensor.getType() == 11) && (paramSensorEvent.values.length == 3))
    {
      a(paramSensorEvent.values, this.jdField_a_of_type_ArrayOfDouble);
      nativeNotifyConvertByJava(this.jdField_a_of_type_Long, true);
      paramSensorEvent = this.jdField_a_of_type_ArrayOfDouble;
      a(d, paramSensorEvent[0], paramSensorEvent[1], paramSensorEvent[2], 0.0D);
      return;
    }
    nativeNotifyConvertByJava(this.jdField_a_of_type_Long, false);
    if (paramSensorEvent.values.length < this.b)
    {
      a();
      stopSensor();
      return;
    }
    switch (paramSensorEvent.values.length)
    {
    default: 
      a(d, paramSensorEvent.values[0], paramSensorEvent.values[1], paramSensorEvent.values[2], paramSensorEvent.values[3]);
      return;
    case 3: 
      a(d, paramSensorEvent.values[0], paramSensorEvent.values[1], paramSensorEvent.values[2], 0.0D);
      return;
    case 2: 
      a(d, paramSensorEvent.values[0], paramSensorEvent.values[1], 0.0D, 0.0D);
      return;
    }
    a(d, paramSensorEvent.values[0], 0.0D, 0.0D, 0.0D);
  }
  
  @CalledByNative
  protected void sensorDestroyed()
  {
    stopSensor();
    this.jdField_a_of_type_Long = 0L;
  }
  
  @CalledByNative
  protected boolean startSensor(double paramDouble)
  {
    if (this.jdField_a_of_type_Double == paramDouble) {
      return true;
    }
    b();
    this.jdField_a_of_type_OrgChromiumDeviceSensorsPlatformSensorProvider.a(this);
    boolean bool = this.jdField_a_of_type_OrgChromiumDeviceSensorsPlatformSensorProvider.a().registerListener(this, this.jdField_a_of_type_AndroidHardwareSensor, a(paramDouble), this.jdField_a_of_type_OrgChromiumDeviceSensorsPlatformSensorProvider.a());
    if (!bool)
    {
      stopSensor();
      return bool;
    }
    this.jdField_a_of_type_Double = paramDouble;
    return bool;
  }
  
  @CalledByNative
  protected void stopSensor()
  {
    b();
    this.jdField_a_of_type_OrgChromiumDeviceSensorsPlatformSensorProvider.b(this);
    this.jdField_a_of_type_Double = 0.0D;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\sensors\PlatformSensor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */