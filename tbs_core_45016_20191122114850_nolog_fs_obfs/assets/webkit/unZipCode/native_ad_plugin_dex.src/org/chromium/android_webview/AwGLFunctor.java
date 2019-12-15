package org.chromium.android_webview;

import android.graphics.Canvas;
import android.view.ViewGroup;
import org.chromium.android_webview.gfx.AwDrawFnImpl;
import org.chromium.base.BuildInfo;
import org.chromium.base.CommandLine;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwGLFunctor
{
  private int jdField_a_of_type_Int;
  private final long jdField_a_of_type_Long = nativeCreate(this);
  private final ViewGroup jdField_a_of_type_AndroidViewViewGroup;
  private final Object jdField_a_of_type_JavaLangObject;
  private final Runnable jdField_a_of_type_JavaLangRunnable;
  private final AwContents.NativeDrawGLFunctor jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$NativeDrawGLFunctor;
  private final CleanupReference jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference;
  private AwDrawFnImpl jdField_a_of_type_OrgChromiumAndroid_webviewGfxAwDrawFnImpl = null;
  private final boolean jdField_a_of_type_Boolean;
  
  public AwGLFunctor(AwContents.NativeDrawGLFunctorFactory paramNativeDrawGLFunctorFactory, ViewGroup paramViewGroup)
  {
    if ((BuildInfo.isAtLeastQ()) && (CommandLine.getInstance().hasSwitch("enable-draw-webview-functor-on-androidQ"))) {
      this.jdField_a_of_type_Boolean = true;
    } else {
      this.jdField_a_of_type_Boolean = false;
    }
    if (this.jdField_a_of_type_Boolean) {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewGfxAwDrawFnImpl = new AwDrawFnImpl(this, getAwDrawGLViewContext());
    }
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$NativeDrawGLFunctor = paramNativeDrawGLFunctorFactory.createFunctor(getAwDrawGLViewContext());
    this.jdField_a_of_type_JavaLangObject = new Object();
    this.jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference = new CleanupReference(this.jdField_a_of_type_JavaLangObject, new DestroyRunnable(this.jdField_a_of_type_Long, this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$NativeDrawGLFunctor.getDestroyRunnable(), null));
    this.jdField_a_of_type_AndroidViewViewGroup = paramViewGroup;
    if (this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$NativeDrawGLFunctor.supportsDrawGLFunctorReleasedCallback())
    {
      this.jdField_a_of_type_JavaLangRunnable = new Runnable()
      {
        public void run()
        {
          AwGLFunctor.a(AwGLFunctor.this);
        }
      };
      return;
    }
    this.jdField_a_of_type_JavaLangRunnable = null;
  }
  
  private void a()
  {
    this.jdField_a_of_type_Int += 1;
  }
  
  private void b()
  {
    int i = this.jdField_a_of_type_Int - 1;
    this.jdField_a_of_type_Int = i;
    if (i == 0)
    {
      deleteHardwareRenderer();
      AwDrawFnImpl localAwDrawFnImpl = this.jdField_a_of_type_OrgChromiumAndroid_webviewGfxAwDrawFnImpl;
      if (localAwDrawFnImpl != null) {
        localAwDrawFnImpl.destroy();
      }
    }
  }
  
  @CalledByNative
  private void detachFunctorFromView()
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$NativeDrawGLFunctor.detach(this.jdField_a_of_type_AndroidViewViewGroup);
    this.jdField_a_of_type_AndroidViewViewGroup.invalidate();
  }
  
  public static long getAwDrawGLFunction()
  {
    return nativeGetAwDrawGLFunction();
  }
  
  @VisibleForTesting
  public static int getNativeInstanceCount()
  {
    return nativeGetNativeInstanceCount();
  }
  
  private static native long nativeCreate(AwGLFunctor paramAwGLFunctor);
  
  private native void nativeDeleteHardwareRenderer(long paramLong);
  
  private static native void nativeDestroy(long paramLong);
  
  private static native long nativeGetAwDrawGLFunction();
  
  private native long nativeGetAwDrawGLViewContext(long paramLong);
  
  private static native int nativeGetNativeInstanceCount();
  
  @CalledByNative
  private boolean requestInvokeGL(boolean paramBoolean)
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$NativeDrawGLFunctor.requestInvokeGL(this.jdField_a_of_type_AndroidViewViewGroup, paramBoolean);
  }
  
  public void deleteHardwareRenderer()
  {
    nativeDeleteHardwareRenderer(this.jdField_a_of_type_Long);
  }
  
  public long getAwDrawGLViewContext()
  {
    return nativeGetAwDrawGLViewContext(this.jdField_a_of_type_Long);
  }
  
  public long getNativeAwGLFunctor()
  {
    return this.jdField_a_of_type_Long;
  }
  
  public void onAttachedToWindow()
  {
    a();
  }
  
  public void onDetachedFromWindow()
  {
    b();
  }
  
  public boolean requestDrawGL(Canvas paramCanvas)
  {
    boolean bool;
    if (this.jdField_a_of_type_Boolean) {
      bool = this.jdField_a_of_type_OrgChromiumAndroid_webviewGfxAwDrawFnImpl.requestDraw(paramCanvas);
    } else {
      bool = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$NativeDrawGLFunctor.requestDrawGL(paramCanvas, this.jdField_a_of_type_JavaLangRunnable);
    }
    if ((bool) && (this.jdField_a_of_type_JavaLangRunnable != null)) {
      a();
    }
    return bool;
  }
  
  private static final class DestroyRunnable
    implements Runnable
  {
    private final long jdField_a_of_type_Long;
    private final Runnable jdField_a_of_type_JavaLangRunnable;
    
    private DestroyRunnable(long paramLong, Runnable paramRunnable)
    {
      this.jdField_a_of_type_Long = paramLong;
      this.jdField_a_of_type_JavaLangRunnable = paramRunnable;
    }
    
    public void run()
    {
      this.jdField_a_of_type_JavaLangRunnable.run();
      AwGLFunctor.a(this.jdField_a_of_type_Long);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwGLFunctor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */