package org.chromium.tencent.ui.devices;

import android.hardware.input.InputManager.InputDeviceListener;
import android.os.Build.VERSION;

public abstract class TencentInputDeviceListener
{
  private InputDeviceListenerAdapter mInputDeviceListenerAdapter = null;
  
  protected TencentInputDeviceListener()
  {
    if (Build.VERSION.SDK_INT >= 16) {
      this.mInputDeviceListenerAdapter = new InputDeviceListenerAdapter(null);
    }
  }
  
  protected InputManager.InputDeviceListener getTencentInputDeviceListener()
  {
    return this.mInputDeviceListenerAdapter;
  }
  
  public abstract void onInputDeviceAdded(int paramInt);
  
  public abstract void onInputDeviceChanged(int paramInt);
  
  public abstract void onInputDeviceRemoved(int paramInt);
  
  private class InputDeviceListenerAdapter
    implements InputManager.InputDeviceListener
  {
    private InputDeviceListenerAdapter() {}
    
    public void onInputDeviceAdded(int paramInt)
    {
      TencentInputDeviceListener.this.onInputDeviceAdded(paramInt);
    }
    
    public void onInputDeviceChanged(int paramInt)
    {
      TencentInputDeviceListener.this.onInputDeviceChanged(paramInt);
    }
    
    public void onInputDeviceRemoved(int paramInt)
    {
      TencentInputDeviceListener.this.onInputDeviceRemoved(paramInt);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\ui\devices\TencentInputDeviceListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */