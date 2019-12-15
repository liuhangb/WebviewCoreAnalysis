package com.tencent.smtt.webkit.webar;

import android.content.Context;
import android.graphics.SurfaceTexture;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("webar")
public abstract class WebARVideoCapture
{
  protected final int a;
  protected final Context a;
  
  WebARVideoCapture(Context paramContext, int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  @CalledByNative
  static WebARVideoCapture createWebARVideoCapture(Context paramContext, int paramInt)
  {
    return new b(paramContext, paramInt);
  }
  
  @CalledByNative
  protected abstract boolean allocate(int paramInt1, int paramInt2, SurfaceTexture paramSurfaceTexture);
  
  @CalledByNative
  protected abstract boolean canUploadCameraPicture();
  
  @CalledByNative
  protected abstract void deallocate();
  
  @CalledByNative
  protected abstract void onFrameBegin();
  
  @CalledByNative
  protected abstract boolean startCapture();
  
  @CalledByNative
  protected abstract boolean stopCapture();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\webar\WebARVideoCapture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */