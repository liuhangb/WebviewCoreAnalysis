package org.chromium.mojo.bindings;

import java.io.Closeable;

public abstract interface MessageReceiver
  extends Closeable
{
  public abstract boolean accept(Message paramMessage);
  
  public abstract void close();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\MessageReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */