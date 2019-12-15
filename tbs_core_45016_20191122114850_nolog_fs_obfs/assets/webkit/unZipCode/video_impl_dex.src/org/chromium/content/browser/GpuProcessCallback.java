package org.chromium.content.browser;

import android.view.Surface;
import org.chromium.base.UnguessableToken;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.common.IGpuProcessCallback.Stub;
import org.chromium.content.common.SurfaceWrapper;

@JNINamespace("content")
class GpuProcessCallback
  extends IGpuProcessCallback.Stub
{
  private static native void nativeCompleteScopedSurfaceRequest(UnguessableToken paramUnguessableToken, Surface paramSurface);
  
  private static native Surface nativeGetViewSurface(int paramInt);
  
  public void forwardSurfaceForSurfaceRequest(UnguessableToken paramUnguessableToken, Surface paramSurface)
  {
    nativeCompleteScopedSurfaceRequest(paramUnguessableToken, paramSurface);
  }
  
  public SurfaceWrapper getViewSurface(int paramInt)
  {
    Surface localSurface = nativeGetViewSurface(paramInt);
    if (localSurface == null) {
      return null;
    }
    return new SurfaceWrapper(localSurface);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\GpuProcessCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */