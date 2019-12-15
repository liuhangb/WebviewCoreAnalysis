package org.chromium.mojo.system.impl;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.mojo.system.Core.HandleSignals;
import org.chromium.mojo.system.Handle;
import org.chromium.mojo.system.Watcher;
import org.chromium.mojo.system.Watcher.Callback;

@JNINamespace("mojo::android")
class WatcherImpl
  implements Watcher
{
  private long jdField_a_of_type_Long = nativeCreateWatcher();
  private Watcher.Callback jdField_a_of_type_OrgChromiumMojoSystemWatcher$Callback;
  
  private native void nativeCancel(long paramLong);
  
  private native long nativeCreateWatcher();
  
  private native void nativeDelete(long paramLong);
  
  private native int nativeStart(long paramLong, int paramInt1, int paramInt2);
  
  @CalledByNative
  private void onHandleReady(int paramInt)
  {
    this.jdField_a_of_type_OrgChromiumMojoSystemWatcher$Callback.onResult(paramInt);
  }
  
  public void cancel()
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return;
    }
    this.jdField_a_of_type_OrgChromiumMojoSystemWatcher$Callback = null;
    nativeCancel(l);
  }
  
  public void destroy()
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return;
    }
    nativeDelete(l);
    this.jdField_a_of_type_Long = 0L;
  }
  
  public int start(Handle paramHandle, Core.HandleSignals paramHandleSignals, Watcher.Callback paramCallback)
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return 3;
    }
    if (!(paramHandle instanceof HandleBase)) {
      return 3;
    }
    int i = nativeStart(l, ((HandleBase)paramHandle).a(), paramHandleSignals.a());
    if (i == 0) {
      this.jdField_a_of_type_OrgChromiumMojoSystemWatcher$Callback = paramCallback;
    }
    return i;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\impl\WatcherImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */