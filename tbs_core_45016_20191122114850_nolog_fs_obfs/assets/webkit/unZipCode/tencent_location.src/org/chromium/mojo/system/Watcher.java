package org.chromium.mojo.system;

public abstract interface Watcher
{
  public abstract void cancel();
  
  public abstract void destroy();
  
  public abstract int start(Handle paramHandle, Core.HandleSignals paramHandleSignals, Callback paramCallback);
  
  public static abstract interface Callback
  {
    public abstract void onResult(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\Watcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */