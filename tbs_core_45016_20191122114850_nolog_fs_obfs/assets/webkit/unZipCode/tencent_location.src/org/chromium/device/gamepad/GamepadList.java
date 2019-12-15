package org.chromium.device.gamepad;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.input.InputManager;
import android.hardware.input.InputManager.InputDeviceListener;
import android.os.Build.VERSION;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("device")
public class GamepadList
{
  private int jdField_a_of_type_Int;
  private InputManager.InputDeviceListener jdField_a_of_type_AndroidHardwareInputInputManager$InputDeviceListener = new InputManager.InputDeviceListener()
  {
    public void onInputDeviceAdded(int paramAnonymousInt)
    {
      GamepadList.c(GamepadList.this, paramAnonymousInt);
    }
    
    public void onInputDeviceChanged(int paramAnonymousInt)
    {
      GamepadList.a(GamepadList.this, paramAnonymousInt);
    }
    
    public void onInputDeviceRemoved(int paramAnonymousInt)
    {
      GamepadList.b(GamepadList.this, paramAnonymousInt);
    }
  };
  private InputManager jdField_a_of_type_AndroidHardwareInputInputManager;
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  private final GamepadDevice[] jdField_a_of_type_ArrayOfOrgChromiumDeviceGamepadGamepadDevice = new GamepadDevice[4];
  private boolean b;
  
  private int a()
  {
    int i = 0;
    while (i < 4)
    {
      if (b(i) == null) {
        return i;
      }
      i += 1;
    }
    return -1;
  }
  
  private GamepadDevice a(int paramInt)
  {
    int i = 0;
    while (i < 4)
    {
      GamepadDevice localGamepadDevice = this.jdField_a_of_type_ArrayOfOrgChromiumDeviceGamepadGamepadDevice[i];
      if ((localGamepadDevice != null) && (localGamepadDevice.a() == paramInt)) {
        return localGamepadDevice;
      }
      i += 1;
    }
    return null;
  }
  
  private GamepadDevice a(InputEvent paramInputEvent)
  {
    return a(paramInputEvent.getDeviceId());
  }
  
  private static GamepadList a()
  {
    return LazyHolder.a();
  }
  
  @SuppressLint({"MissingSuperCall"})
  public static void a()
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    if (!a()) {
      return;
    }
    a().c();
  }
  
  private void a(int paramInt) {}
  
  private void a(long paramLong)
  {
    Object localObject1 = this.jdField_a_of_type_JavaLangObject;
    int i = 0;
    for (;;)
    {
      if (i < 4) {}
      try
      {
        GamepadDevice localGamepadDevice = b(i);
        if (localGamepadDevice != null)
        {
          localGamepadDevice.a();
          nativeSetGamepadData(paramLong, i, localGamepadDevice.a(), true, localGamepadDevice.a(), localGamepadDevice.a(), localGamepadDevice.a(), localGamepadDevice.b());
        }
        else
        {
          nativeSetGamepadData(paramLong, i, false, false, null, 0L, null, null);
        }
      }
      finally {}
      return;
      i += 1;
    }
  }
  
  public static void a(Context paramContext)
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    if (!a()) {
      return;
    }
    a().b(paramContext);
  }
  
  private void a(boolean paramBoolean)
  {
    for (;;)
    {
      int i;
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        this.b = paramBoolean;
        if (paramBoolean)
        {
          i = 0;
          if (i < 4)
          {
            GamepadDevice localGamepadDevice = b(i);
            if (localGamepadDevice == null) {
              break label56;
            }
            localGamepadDevice.b();
            break label56;
          }
        }
        return;
      }
      label56:
      i += 1;
    }
  }
  
  private static boolean a()
  {
    return Build.VERSION.SDK_INT >= 16;
  }
  
  private boolean a(InputDevice paramInputDevice)
  {
    int i = a();
    if (i == -1) {
      return false;
    }
    paramInputDevice = new GamepadDevice(i, paramInputDevice);
    this.jdField_a_of_type_ArrayOfOrgChromiumDeviceGamepadGamepadDevice[i] = paramInputDevice;
    return true;
  }
  
  public static boolean a(KeyEvent paramKeyEvent)
  {
    if (!a()) {
      return false;
    }
    if (!b(paramKeyEvent)) {
      return false;
    }
    return a().c(paramKeyEvent);
  }
  
  public static boolean a(MotionEvent paramMotionEvent)
  {
    if (!a()) {
      return false;
    }
    if (!b(paramMotionEvent)) {
      return false;
    }
    return a().c(paramMotionEvent);
  }
  
  private GamepadDevice b(int paramInt)
  {
    if ((!jdField_a_of_type_Boolean) && ((paramInt < 0) || (paramInt >= 4))) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_ArrayOfOrgChromiumDeviceGamepadGamepadDevice[paramInt];
  }
  
  @TargetApi(16)
  private void b()
  {
    try
    {
      int[] arrayOfInt = this.jdField_a_of_type_AndroidHardwareInputInputManager.getInputDeviceIds();
      int i = 0;
      while (i < arrayOfInt.length)
      {
        InputDevice localInputDevice = InputDevice.getDevice(arrayOfInt[i]);
        if (b(localInputDevice)) {
          a(localInputDevice);
        }
        i += 1;
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  private void b(int paramInt)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      d(paramInt);
      return;
    }
  }
  
  @TargetApi(16)
  private void b(Context arg1)
  {
    int i = this.jdField_a_of_type_Int;
    this.jdField_a_of_type_Int = (i + 1);
    if (i == 0)
    {
      this.jdField_a_of_type_AndroidHardwareInputInputManager = ((InputManager)???.getSystemService("input"));
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        b();
        this.jdField_a_of_type_AndroidHardwareInputInputManager.registerInputDeviceListener(this.jdField_a_of_type_AndroidHardwareInputInputManager$InputDeviceListener, null);
        return;
      }
    }
  }
  
  private static boolean b(InputDevice paramInputDevice)
  {
    boolean bool = false;
    if (paramInputDevice == null) {
      return false;
    }
    if ((paramInputDevice.getSources() & 0x1000010) == 16777232) {
      bool = true;
    }
    return bool;
  }
  
  public static boolean b(KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getKeyCode();
    switch (i)
    {
    default: 
      return KeyEvent.isGamepadButton(i);
    }
    return true;
  }
  
  public static boolean b(MotionEvent paramMotionEvent)
  {
    return (paramMotionEvent.getSource() & 0x1000010) == 16777232;
  }
  
  @TargetApi(16)
  private void c()
  {
    int i = this.jdField_a_of_type_Int - 1;
    this.jdField_a_of_type_Int = i;
    Object localObject1;
    if (i == 0)
    {
      localObject1 = this.jdField_a_of_type_JavaLangObject;
      i = 0;
      for (;;)
      {
        if (i < 4) {}
        try
        {
          this.jdField_a_of_type_ArrayOfOrgChromiumDeviceGamepadGamepadDevice[i] = null;
          i += 1;
        }
        finally {}
      }
      this.jdField_a_of_type_AndroidHardwareInputInputManager.unregisterInputDeviceListener(this.jdField_a_of_type_AndroidHardwareInputInputManager$InputDeviceListener);
      this.jdField_a_of_type_AndroidHardwareInputInputManager = null;
      return;
    }
  }
  
  private void c(int paramInt)
  {
    InputDevice localInputDevice = InputDevice.getDevice(paramInt);
    if (!b(localInputDevice)) {
      return;
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      a(localInputDevice);
      return;
    }
  }
  
  private boolean c(KeyEvent paramKeyEvent)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (!this.b) {
        return false;
      }
      GamepadDevice localGamepadDevice = a(paramKeyEvent);
      if (localGamepadDevice == null) {
        return false;
      }
      boolean bool = localGamepadDevice.a(paramKeyEvent);
      return bool;
    }
  }
  
  private boolean c(MotionEvent paramMotionEvent)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (!this.b) {
        return false;
      }
      GamepadDevice localGamepadDevice = a(paramMotionEvent);
      if (localGamepadDevice == null) {
        return false;
      }
      boolean bool = localGamepadDevice.a(paramMotionEvent);
      return bool;
    }
  }
  
  private void d(int paramInt)
  {
    GamepadDevice localGamepadDevice = a(paramInt);
    if (localGamepadDevice == null) {
      return;
    }
    paramInt = localGamepadDevice.b();
    this.jdField_a_of_type_ArrayOfOrgChromiumDeviceGamepadGamepadDevice[paramInt] = null;
  }
  
  private native void nativeSetGamepadData(long paramLong1, int paramInt, boolean paramBoolean1, boolean paramBoolean2, String paramString, long paramLong2, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2);
  
  @CalledByNative
  static void setGamepadAPIActive(boolean paramBoolean)
  {
    if (!a()) {
      return;
    }
    a().a(paramBoolean);
  }
  
  @CalledByNative
  static void updateGamepadData(long paramLong)
  {
    if (!a()) {
      return;
    }
    a().a(paramLong);
  }
  
  private static class LazyHolder
  {
    private static final GamepadList a = new GamepadList(null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\gamepad\GamepadList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */