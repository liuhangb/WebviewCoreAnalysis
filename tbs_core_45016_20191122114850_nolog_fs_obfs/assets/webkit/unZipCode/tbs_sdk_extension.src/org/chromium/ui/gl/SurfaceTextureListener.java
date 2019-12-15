package org.chromium.ui.gl;

import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("gl")
@MainDex
class SurfaceTextureListener
  implements SurfaceTexture.OnFrameAvailableListener
{
  private final long jdField_a_of_type_Long;
  
  SurfaceTextureListener(long paramLong)
  {
    if ((!jdField_a_of_type_Boolean) && (paramLong == 0L)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_Long = paramLong;
  }
  
  private native void nativeDestroy(long paramLong);
  
  private native void nativeFrameAvailable(long paramLong);
  
  protected void finalize()
    throws Throwable
  {
    try
    {
      nativeDestroy(this.jdField_a_of_type_Long);
      return;
    }
    finally
    {
      super.finalize();
    }
  }
  
  public void onFrameAvailable(SurfaceTexture paramSurfaceTexture)
  {
    nativeFrameAvailable(this.jdField_a_of_type_Long);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\gl\SurfaceTextureListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */