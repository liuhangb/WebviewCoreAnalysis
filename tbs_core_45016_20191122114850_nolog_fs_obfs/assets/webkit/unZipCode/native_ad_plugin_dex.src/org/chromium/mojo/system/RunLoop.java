package org.chromium.mojo.system;

import java.io.Closeable;

public abstract interface RunLoop
  extends Closeable
{
  public abstract void close();
  
  public abstract void postDelayedTask(Runnable paramRunnable, long paramLong);
  
  public abstract void quit();
  
  public abstract void run();
  
  public abstract void runUntilIdle();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\RunLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */