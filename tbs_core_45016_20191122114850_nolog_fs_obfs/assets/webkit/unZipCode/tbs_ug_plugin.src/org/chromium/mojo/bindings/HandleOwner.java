package org.chromium.mojo.bindings;

import java.io.Closeable;
import org.chromium.mojo.system.Handle;

public abstract interface HandleOwner<H extends Handle>
  extends Closeable
{
  public abstract void close();
  
  public abstract H passHandle();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\HandleOwner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */