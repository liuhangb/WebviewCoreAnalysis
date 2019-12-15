package com.tencent.smtt.webkit.webar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import com.tencent.common.threadpool.BrowserExecutorSupplier;

@SuppressLint({"InlinedApi"})
public class a
  implements Camera.AutoFocusCallback, SensorEventListener
{
  private float jdField_a_of_type_Float;
  private int jdField_a_of_type_Int = 0;
  private long jdField_a_of_type_Long;
  private Sensor jdField_a_of_type_AndroidHardwareSensor;
  private SensorManager jdField_a_of_type_AndroidHardwareSensorManager;
  private Handler jdField_a_of_type_AndroidOsHandler;
  IWebARAutoFocusProvider jdField_a_of_type_ComTencentSmttWebkitWebarIWebARAutoFocusProvider;
  private Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public void run()
    {
      a.a(a.this, true);
    }
  };
  private boolean jdField_a_of_type_Boolean = false;
  private float jdField_b_of_type_Float;
  private int jdField_b_of_type_Int = 0;
  private float c;
  
  public a(IWebARAutoFocusProvider paramIWebARAutoFocusProvider, Context paramContext)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitWebarIWebARAutoFocusProvider = paramIWebARAutoFocusProvider;
    this.jdField_a_of_type_AndroidHardwareSensorManager = ((SensorManager)paramContext.getSystemService("sensor"));
    this.jdField_a_of_type_AndroidHardwareSensor = this.jdField_a_of_type_AndroidHardwareSensorManager.getDefaultSensor(10);
    if (this.jdField_a_of_type_AndroidHardwareSensor == null) {
      this.jdField_a_of_type_AndroidHardwareSensor = this.jdField_a_of_type_AndroidHardwareSensorManager.getDefaultSensor(1);
    }
    this.jdField_a_of_type_AndroidOsHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime());
  }
  
  private void a(int paramInt)
  {
    boolean bool = false;
    int i;
    if (paramInt != 4)
    {
      switch (paramInt)
      {
      default: 
        break;
      case 2: 
        i = this.jdField_a_of_type_Int;
        if (i == 2) {
          break;
        }
        if ((i != 1) && (i != 0))
        {
          if (i == 4)
          {
            a(false);
            this.jdField_a_of_type_Int = paramInt;
          }
        }
        else {
          this.jdField_a_of_type_Int = paramInt;
        }
        break;
      case 1: 
        if (this.jdField_a_of_type_Int != 4) {
          break;
        }
        this.jdField_a_of_type_Int = paramInt;
        break;
      }
    }
    else
    {
      i = this.jdField_a_of_type_Int;
      if ((i != 0) && (i != 2))
      {
        if ((i != 1) && (i == 4) && (this.jdField_a_of_type_Long + 500L < System.currentTimeMillis())) {
          onAutoFocus(false, null);
        }
      }
      else
      {
        this.jdField_b_of_type_Int = 0;
        if (a(true)) {
          this.jdField_a_of_type_Int = paramInt;
        }
      }
    }
    IWebARAutoFocusProvider localIWebARAutoFocusProvider = this.jdField_a_of_type_ComTencentSmttWebkitWebarIWebARAutoFocusProvider;
    if (localIWebARAutoFocusProvider != null)
    {
      if (this.jdField_a_of_type_Int == 1) {
        bool = true;
      }
      localIWebARAutoFocusProvider.onFocusChanged(bool);
    }
  }
  
  private boolean a(boolean paramBoolean)
  {
    IWebARAutoFocusProvider localIWebARAutoFocusProvider = this.jdField_a_of_type_ComTencentSmttWebkitWebarIWebARAutoFocusProvider;
    if ((localIWebARAutoFocusProvider != null) && (this.jdField_a_of_type_Boolean))
    {
      localIWebARAutoFocusProvider.autoFocus(paramBoolean);
      if (paramBoolean) {
        this.jdField_a_of_type_Long = System.currentTimeMillis();
      }
      return true;
    }
    return false;
  }
  
  public void a()
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_Boolean = true;
    this.jdField_a_of_type_AndroidHardwareSensorManager.registerListener(this, this.jdField_a_of_type_AndroidHardwareSensor, 3);
    a(4);
  }
  
  public void b()
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_ComTencentSmttWebkitWebarIWebARAutoFocusProvider = null;
    Object localObject = this.jdField_a_of_type_AndroidOsHandler;
    if (localObject != null) {
      ((Handler)localObject).removeCallbacks(this.jdField_a_of_type_JavaLangRunnable);
    }
    localObject = this.jdField_a_of_type_AndroidHardwareSensorManager;
    if (localObject != null) {
      ((SensorManager)localObject).unregisterListener(this);
    }
  }
  
  public void onAccuracyChanged(Sensor paramSensor, int paramInt) {}
  
  public void onAutoFocus(boolean paramBoolean, Camera paramCamera)
  {
    if (paramBoolean)
    {
      a(1);
      a(false);
      return;
    }
    int i = this.jdField_b_of_type_Int;
    if (i > 5)
    {
      a(1);
      a(false);
      return;
    }
    this.jdField_b_of_type_Int = (i + 1);
    this.jdField_a_of_type_AndroidOsHandler.postDelayed(this.jdField_a_of_type_JavaLangRunnable, 200L);
  }
  
  public void onSensorChanged(SensorEvent paramSensorEvent)
  {
    if (paramSensorEvent != null)
    {
      if (paramSensorEvent.sensor == null) {
        return;
      }
      if ((paramSensorEvent.sensor.getType() == 10) || (paramSensorEvent.sensor.getType() == 1))
      {
        this.jdField_a_of_type_Float = (this.jdField_a_of_type_Float * 0.8F + paramSensorEvent.values[0] * 0.19999999F);
        this.jdField_b_of_type_Float = (this.jdField_b_of_type_Float * 0.8F + paramSensorEvent.values[1] * 0.19999999F);
        this.c = (this.c * 0.8F + paramSensorEvent.values[2] * 0.19999999F);
        float f1 = paramSensorEvent.values[0] - this.jdField_a_of_type_Float;
        float f2 = paramSensorEvent.values[1] - this.jdField_b_of_type_Float;
        float f3 = paramSensorEvent.values[2] - this.c;
        f1 = f1 * f1 + f2 * f2 + f3 * f3;
        if (f1 > 2.0F) {
          a(2);
        } else {
          a(4);
        }
        if (f1 > 0.5F)
        {
          paramSensorEvent = this.jdField_a_of_type_ComTencentSmttWebkitWebarIWebARAutoFocusProvider;
          if (paramSensorEvent != null) {
            paramSensorEvent.onMovingStatusChanged(true);
          }
        }
        else
        {
          paramSensorEvent = this.jdField_a_of_type_ComTencentSmttWebkitWebarIWebARAutoFocusProvider;
          if (paramSensorEvent != null) {
            paramSensorEvent.onMovingStatusChanged(false);
          }
        }
      }
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\webar\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */