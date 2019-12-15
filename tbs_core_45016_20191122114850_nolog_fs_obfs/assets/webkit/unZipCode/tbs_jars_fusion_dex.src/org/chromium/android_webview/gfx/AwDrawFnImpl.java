package org.chromium.android_webview.gfx;

import android.graphics.Canvas;
import android.util.Log;
import org.chromium.android_webview.AwGLFunctor;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwDrawFnImpl
  implements AwFunctor
{
  private static DrawFnAccess jdField_a_of_type_OrgChromiumAndroid_webviewGfxAwDrawFnImpl$DrawFnAccess;
  private final int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private final AwGLFunctor jdField_a_of_type_OrgChromiumAndroid_webviewAwGLFunctor;
  
  public AwDrawFnImpl(AwGLFunctor paramAwGLFunctor, long paramLong)
  {
    this.jdField_a_of_type_Long = nativeCreateWithCompositorFrameConsumer(paramLong);
    this.jdField_a_of_type_Int = nativeGetFunctorHandle(this.jdField_a_of_type_Long);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwGLFunctor = paramAwGLFunctor;
  }
  
  private static native long nativeCreate();
  
  private static native long nativeCreateWithCompositorFrameConsumer(long paramLong);
  
  private native long nativeGetCompositorFrameConsumer(long paramLong);
  
  private native int nativeGetFunctorHandle(long paramLong);
  
  private native void nativeReleaseHandle(long paramLong);
  
  private static native void nativeSetDrawFnFunctionTable(long paramLong);
  
  public static void setDrawFnAccess(DrawFnAccess paramDrawFnAccess)
  {
    jdField_a_of_type_OrgChromiumAndroid_webviewGfxAwDrawFnImpl$DrawFnAccess = paramDrawFnAccess;
  }
  
  public static void setDrawFnFunctionTable(long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setDrawFnFunctionTable: ");
    localStringBuilder.append(paramLong);
    Log.e("penghu", localStringBuilder.toString());
    nativeSetDrawFnFunctionTable(paramLong);
  }
  
  public void destroy()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    nativeReleaseHandle(this.jdField_a_of_type_Long);
    this.jdField_a_of_type_Long = 0L;
  }
  
  public long getNativeCompositorFrameConsumer()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    return nativeGetCompositorFrameConsumer(this.jdField_a_of_type_Long);
  }
  
  public boolean requestDraw(Canvas paramCanvas)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    jdField_a_of_type_OrgChromiumAndroid_webviewGfxAwDrawFnImpl$DrawFnAccess.drawWebViewFunctor(paramCanvas, this.jdField_a_of_type_Int);
    return true;
  }
  
  public void trimMemory() {}
  
  public static abstract interface DrawFnAccess
  {
    public abstract void drawWebViewFunctor(Canvas paramCanvas, int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\gfx\AwDrawFnImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */