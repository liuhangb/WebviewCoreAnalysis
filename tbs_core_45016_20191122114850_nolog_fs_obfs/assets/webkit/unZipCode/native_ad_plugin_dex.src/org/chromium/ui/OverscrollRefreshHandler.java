package org.chromium.ui;

import org.chromium.base.annotations.CalledByNative;

public abstract interface OverscrollRefreshHandler
{
  @CalledByNative
  public abstract void pull(float paramFloat);
  
  @CalledByNative
  public abstract void release(boolean paramBoolean);
  
  @CalledByNative
  public abstract void reset();
  
  public abstract void setEnabled(boolean paramBoolean);
  
  @CalledByNative
  public abstract boolean start();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\OverscrollRefreshHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */