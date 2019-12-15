package org.chromium.ui.resources;

import android.graphics.Bitmap;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;
import org.chromium.ui.base.WindowAndroid;

@JNINamespace("ui")
@MainDex
public class ResourceManager
{
  private long jdField_a_of_type_Long;
  
  private ResourceManager(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
  }
  
  @CalledByNative
  private static ResourceManager create(WindowAndroid paramWindowAndroid, long paramLong)
  {
    return new ResourceManager(paramLong);
  }
  
  @CalledByNative
  private void destroy()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_Long = 0L;
  }
  
  @CalledByNative
  private long getNativePtr()
  {
    return this.jdField_a_of_type_Long;
  }
  
  private native void nativeClearTintedResourceCache(long paramLong);
  
  private native void nativeOnResourceReady(long paramLong1, int paramInt1, int paramInt2, Bitmap paramBitmap, long paramLong2);
  
  private native void nativeRemoveResource(long paramLong, int paramInt1, int paramInt2);
  
  @CalledByNative
  private void preloadResource(int paramInt1, int paramInt2) {}
  
  @CalledByNative
  private void resourceRequested(int paramInt1, int paramInt2) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\resources\ResourceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */