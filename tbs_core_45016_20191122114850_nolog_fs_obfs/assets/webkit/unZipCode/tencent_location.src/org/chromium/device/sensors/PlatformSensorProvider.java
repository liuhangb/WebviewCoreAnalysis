package org.chromium.device.sensors;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("device")
class PlatformSensorProvider
{
  private SensorManager jdField_a_of_type_AndroidHardwareSensorManager;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  private final Set<PlatformSensor> jdField_a_of_type_JavaUtilSet = new HashSet();
  
  protected PlatformSensorProvider(Context paramContext)
  {
    this.jdField_a_of_type_AndroidHardwareSensorManager = ((SensorManager)paramContext.getSystemService("sensor"));
  }
  
  @CalledByNative
  protected static PlatformSensorProvider create()
  {
    return new PlatformSensorProvider(ContextUtils.getApplicationContext());
  }
  
  public SensorManager a()
  {
    return this.jdField_a_of_type_AndroidHardwareSensorManager;
  }
  
  public Handler a()
  {
    return this.jdField_a_of_type_AndroidOsHandler;
  }
  
  protected void a()
  {
    if (this.jdField_a_of_type_AndroidOsHandlerThread == null)
    {
      this.jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread("SensorsHandlerThread");
      this.jdField_a_of_type_AndroidOsHandlerThread.start();
      this.jdField_a_of_type_AndroidOsHandler = new Handler(this.jdField_a_of_type_AndroidOsHandlerThread.getLooper());
    }
  }
  
  public void a(PlatformSensor paramPlatformSensor)
  {
    synchronized (this.jdField_a_of_type_JavaUtilSet)
    {
      if (this.jdField_a_of_type_JavaUtilSet.isEmpty()) {
        a();
      }
      this.jdField_a_of_type_JavaUtilSet.add(paramPlatformSensor);
      return;
    }
  }
  
  protected void b()
  {
    if (this.jdField_a_of_type_AndroidOsHandlerThread != null)
    {
      if (Build.VERSION.SDK_INT >= 18) {
        this.jdField_a_of_type_AndroidOsHandlerThread.quitSafely();
      } else {
        this.jdField_a_of_type_AndroidOsHandlerThread.quit();
      }
      this.jdField_a_of_type_AndroidOsHandlerThread = null;
      this.jdField_a_of_type_AndroidOsHandler = null;
    }
  }
  
  public void b(PlatformSensor paramPlatformSensor)
  {
    synchronized (this.jdField_a_of_type_JavaUtilSet)
    {
      this.jdField_a_of_type_JavaUtilSet.remove(paramPlatformSensor);
      if (this.jdField_a_of_type_JavaUtilSet.isEmpty()) {
        b();
      }
      return;
    }
  }
  
  @CalledByNative
  protected PlatformSensor createSensor(int paramInt)
  {
    if (this.jdField_a_of_type_AndroidHardwareSensorManager == null) {
      return null;
    }
    if (paramInt != 1)
    {
      if (paramInt != 9)
      {
        if (paramInt != 11)
        {
          switch (paramInt)
          {
          default: 
            return null;
          case 6: 
            return PlatformSensor.a(2, 3, this);
          case 5: 
            return PlatformSensor.a(4, 3, this);
          case 4: 
            return PlatformSensor.a(10, 3, this);
          }
          return PlatformSensor.a(1, 3, this);
        }
        return PlatformSensor.a(15, 4, this);
      }
      return PlatformSensor.a(11, 4, this);
    }
    return PlatformSensor.a(5, 1, this);
  }
  
  @CalledByNative
  protected boolean hasSensorType(int paramInt)
  {
    if (this.jdField_a_of_type_AndroidHardwareSensorManager == null) {
      return false;
    }
    int i = 11;
    if (paramInt != 1)
    {
      if (paramInt != 9) {
        if (paramInt != 11) {
          switch (paramInt)
          {
          default: 
            return false;
          case 6: 
            i = 2;
            break;
          case 5: 
            i = 4;
            break;
          case 4: 
            i = 10;
            break;
          case 3: 
            i = 1;
            break;
          }
        } else {
          i = 15;
        }
      }
    }
    else {
      i = 5;
    }
    return this.jdField_a_of_type_AndroidHardwareSensorManager.getSensorList(i).isEmpty() ^ true;
  }
  
  @CalledByNative
  protected void setSensorManagerToNullForTesting()
  {
    this.jdField_a_of_type_AndroidHardwareSensorManager = null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\sensors\PlatformSensorProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */