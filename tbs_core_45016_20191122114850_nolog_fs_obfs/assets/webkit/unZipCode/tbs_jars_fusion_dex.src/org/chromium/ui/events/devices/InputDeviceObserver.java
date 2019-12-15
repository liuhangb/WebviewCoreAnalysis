package org.chromium.ui.events.devices;

import android.content.Context;
import android.hardware.input.InputManager;
import org.chromium.base.ContextUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.ui.devices.TencentInputDeviceListener;

@JNINamespace("ui")
public class InputDeviceObserver
  extends TencentInputDeviceListener
{
  private static final InputDeviceObserver jdField_a_of_type_OrgChromiumUiEventsDevicesInputDeviceObserver = new InputDeviceObserver();
  private int jdField_a_of_type_Int;
  private InputManager jdField_a_of_type_AndroidHardwareInputInputManager;
  
  private void a()
  {
    if (getTencentInputDeviceListener() == null) {
      return;
    }
    int i = this.jdField_a_of_type_Int;
    this.jdField_a_of_type_Int = (i + 1);
    if (i == 0)
    {
      this.jdField_a_of_type_AndroidHardwareInputInputManager = ((InputManager)ContextUtils.getApplicationContext().getSystemService("input"));
      this.jdField_a_of_type_AndroidHardwareInputInputManager.registerInputDeviceListener(getTencentInputDeviceListener(), null);
    }
  }
  
  @CalledByNative
  public static void addObserver()
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    jdField_a_of_type_OrgChromiumUiEventsDevicesInputDeviceObserver.a();
  }
  
  private void b()
  {
    if (getTencentInputDeviceListener() == null) {
      return;
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Int <= 0)) {
      throw new AssertionError();
    }
    int i = this.jdField_a_of_type_Int - 1;
    this.jdField_a_of_type_Int = i;
    if (i == 0)
    {
      this.jdField_a_of_type_AndroidHardwareInputInputManager.unregisterInputDeviceListener(getTencentInputDeviceListener());
      this.jdField_a_of_type_AndroidHardwareInputInputManager = null;
    }
  }
  
  private native void nativeInputConfigurationChanged();
  
  @CalledByNative
  public static void removeObserver()
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    jdField_a_of_type_OrgChromiumUiEventsDevicesInputDeviceObserver.b();
  }
  
  public void onInputDeviceAdded(int paramInt)
  {
    nativeInputConfigurationChanged();
  }
  
  public void onInputDeviceChanged(int paramInt)
  {
    nativeInputConfigurationChanged();
  }
  
  public void onInputDeviceRemoved(int paramInt)
  {
    nativeInputConfigurationChanged();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\events\devices\InputDeviceObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */