package org.chromium.mojo.system;

import java.io.Closeable;

public abstract interface Handle
  extends Closeable
{
  public abstract void close();
  
  public abstract Core getCore();
  
  public abstract boolean isValid();
  
  public abstract Handle pass();
  
  public abstract Core.HandleSignalsState querySignalsState();
  
  public abstract int releaseNativeHandle();
  
  public abstract UntypedHandle toUntypedHandle();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\Handle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */