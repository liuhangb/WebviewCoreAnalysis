package org.chromium.ui.gl;

import android.graphics.SurfaceTexture;
import android.os.Build.VERSION;
import android.util.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("gl")
class SurfaceTexturePlatformWrapper
{
  @CalledByNative
  private static void attachToGLContext(SurfaceTexture paramSurfaceTexture, int paramInt)
  {
    if (paramSurfaceTexture != null)
    {
      if (Build.VERSION.SDK_INT < 16) {
        return;
      }
      paramSurfaceTexture.attachToGLContext(paramInt);
      return;
    }
  }
  
  @CalledByNative
  private static SurfaceTexture create(int paramInt)
  {
    return new SurfaceTexture(paramInt);
  }
  
  @CalledByNative
  private static void destroy(SurfaceTexture paramSurfaceTexture)
  {
    if (paramSurfaceTexture == null) {
      return;
    }
    paramSurfaceTexture.setOnFrameAvailableListener(null);
    paramSurfaceTexture.release();
  }
  
  @CalledByNative
  private static void detachFromGLContext(SurfaceTexture paramSurfaceTexture)
  {
    if (paramSurfaceTexture != null)
    {
      if (Build.VERSION.SDK_INT < 16) {
        return;
      }
      paramSurfaceTexture.detachFromGLContext();
      return;
    }
  }
  
  @CalledByNative
  private static long getTimestamp(SurfaceTexture paramSurfaceTexture)
  {
    if (paramSurfaceTexture == null) {
      return 0L;
    }
    try
    {
      long l = paramSurfaceTexture.getTimestamp();
      return l;
    }
    catch (RuntimeException paramSurfaceTexture)
    {
      Log.e("SurfaceTexturePlatformWrapper", "Error calling getTimestamp", paramSurfaceTexture);
    }
    return 0L;
  }
  
  @CalledByNative
  private static void getTransformMatrix(SurfaceTexture paramSurfaceTexture, float[] paramArrayOfFloat)
  {
    if (paramSurfaceTexture == null) {
      return;
    }
    paramSurfaceTexture.getTransformMatrix(paramArrayOfFloat);
  }
  
  @CalledByNative
  private static void release(SurfaceTexture paramSurfaceTexture)
  {
    if (paramSurfaceTexture == null) {
      return;
    }
    paramSurfaceTexture.release();
  }
  
  @CalledByNative
  private static void setDefaultBufferSize(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2)
  {
    if (paramSurfaceTexture == null) {
      return;
    }
    paramSurfaceTexture.setDefaultBufferSize(paramInt1, paramInt2);
  }
  
  @CalledByNative
  private static void setFrameAvailableCallback(SurfaceTexture paramSurfaceTexture, long paramLong)
  {
    if (paramSurfaceTexture == null) {
      return;
    }
    paramSurfaceTexture.setOnFrameAvailableListener(new SurfaceTextureListener(paramLong));
  }
  
  @CalledByNative
  private static void updateTexImage(SurfaceTexture paramSurfaceTexture)
  {
    if (paramSurfaceTexture == null) {
      return;
    }
    try
    {
      paramSurfaceTexture.updateTexImage();
      return;
    }
    catch (RuntimeException paramSurfaceTexture)
    {
      Log.e("SurfaceTexturePlatformWrapper", "Error calling updateTexImage", paramSurfaceTexture);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\gl\SurfaceTexturePlatformWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */