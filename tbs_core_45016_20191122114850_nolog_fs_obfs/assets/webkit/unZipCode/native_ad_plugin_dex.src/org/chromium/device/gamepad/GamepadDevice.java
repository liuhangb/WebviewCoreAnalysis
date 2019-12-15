package org.chromium.device.gamepad;

import android.os.SystemClock;
import android.view.InputDevice;
import android.view.InputDevice.MotionRange;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class GamepadDevice
{
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private String jdField_a_of_type_JavaLangString;
  private GamepadMappings jdField_a_of_type_OrgChromiumDeviceGamepadGamepadMappings;
  private final float[] jdField_a_of_type_ArrayOfFloat = new float[4];
  private int[] jdField_a_of_type_ArrayOfInt;
  private int jdField_b_of_type_Int;
  private final float[] jdField_b_of_type_ArrayOfFloat = new float[17];
  private final float[] c = new float['Ā'];
  private final float[] d = new float['Ā'];
  
  GamepadDevice(int paramInt, InputDevice paramInputDevice)
  {
    this.jdField_b_of_type_Int = paramInt;
    this.jdField_a_of_type_Int = paramInputDevice.getId();
    this.jdField_a_of_type_JavaLangString = paramInputDevice.getName();
    this.jdField_a_of_type_Long = SystemClock.uptimeMillis();
    Object localObject = paramInputDevice.getMotionRanges();
    this.jdField_a_of_type_ArrayOfInt = new int[((List)localObject).size()];
    localObject = ((List)localObject).iterator();
    paramInt = 0;
    while (((Iterator)localObject).hasNext())
    {
      InputDevice.MotionRange localMotionRange = (InputDevice.MotionRange)((Iterator)localObject).next();
      if ((localMotionRange.getSource() & 0x10) != 0)
      {
        int i = localMotionRange.getAxis();
        if ((!jdField_a_of_type_Boolean) && (i >= 256)) {
          throw new AssertionError();
        }
        this.jdField_a_of_type_ArrayOfInt[paramInt] = i;
        paramInt += 1;
      }
    }
    this.jdField_a_of_type_OrgChromiumDeviceGamepadGamepadMappings = GamepadMappings.a(paramInputDevice, this.jdField_a_of_type_ArrayOfInt);
  }
  
  public int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public long a()
  {
    return this.jdField_a_of_type_Long;
  }
  
  public String a()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public void a()
  {
    this.jdField_a_of_type_OrgChromiumDeviceGamepadGamepadMappings.mapToStandardGamepad(this.jdField_a_of_type_ArrayOfFloat, this.jdField_b_of_type_ArrayOfFloat, this.d, this.c);
  }
  
  public boolean a()
  {
    return this.jdField_a_of_type_OrgChromiumDeviceGamepadGamepadMappings.a();
  }
  
  public boolean a(KeyEvent paramKeyEvent)
  {
    if (!GamepadList.b(paramKeyEvent)) {
      return false;
    }
    int i = paramKeyEvent.getKeyCode();
    if ((!jdField_a_of_type_Boolean) && (i >= 256)) {
      throw new AssertionError();
    }
    if (paramKeyEvent.getAction() == 0) {
      this.c[i] = 1.0F;
    } else if (paramKeyEvent.getAction() == 1) {
      this.c[i] = 0.0F;
    }
    this.jdField_a_of_type_Long = paramKeyEvent.getEventTime();
    return true;
  }
  
  public boolean a(MotionEvent paramMotionEvent)
  {
    boolean bool = GamepadList.b(paramMotionEvent);
    int i = 0;
    if (!bool) {
      return false;
    }
    for (;;)
    {
      int[] arrayOfInt = this.jdField_a_of_type_ArrayOfInt;
      if (i >= arrayOfInt.length) {
        break;
      }
      int j = arrayOfInt[i];
      this.d[j] = paramMotionEvent.getAxisValue(j);
      i += 1;
    }
    this.jdField_a_of_type_Long = paramMotionEvent.getEventTime();
    return true;
  }
  
  public float[] a()
  {
    return this.jdField_a_of_type_ArrayOfFloat;
  }
  
  public int b()
  {
    return this.jdField_b_of_type_Int;
  }
  
  public void b()
  {
    Arrays.fill(this.jdField_a_of_type_ArrayOfFloat, 0.0F);
    Arrays.fill(this.d, 0.0F);
    Arrays.fill(this.jdField_b_of_type_ArrayOfFloat, 0.0F);
    Arrays.fill(this.c, 0.0F);
  }
  
  public float[] b()
  {
    return this.jdField_b_of_type_ArrayOfFloat;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\gamepad\GamepadDevice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */