package android.webview.chromium;

import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.view.View;
import org.chromium.android_webview.AwContents.NativeDrawGLFunctor;

public class DrawGLFunctor
  implements AwContents.NativeDrawGLFunctor
{
  private static final String TAG = "DrawGLFunctor";
  private static final boolean sSupportFunctorReleasedCallback;
  private final DestroyRunnable mDestroyRunnable;
  private final WebViewDelegateFactory.WebViewDelegate mWebViewDelegate;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 24) {
      bool = true;
    } else {
      bool = false;
    }
    sSupportFunctorReleasedCallback = bool;
  }
  
  public DrawGLFunctor(long paramLong, WebViewDelegateFactory.WebViewDelegate paramWebViewDelegate)
  {
    this.mDestroyRunnable = new DestroyRunnable(nativeCreateGLFunctor(paramLong));
    this.mWebViewDelegate = paramWebViewDelegate;
  }
  
  private static native long nativeCreateGLFunctor(long paramLong);
  
  private static native void nativeDestroyGLFunctor(long paramLong);
  
  private static native void nativeSetChromiumAwDrawGLFunction(long paramLong);
  
  public static void setChromiumAwDrawGLFunction(long paramLong)
  {
    nativeSetChromiumAwDrawGLFunction(paramLong);
  }
  
  public void detach(View paramView)
  {
    if (this.mDestroyRunnable.mNativeDrawGLFunctor != 0L)
    {
      this.mWebViewDelegate.detachDrawGlFunctor(paramView, this.mDestroyRunnable.mNativeDrawGLFunctor);
      return;
    }
    throw new RuntimeException("detach on already destroyed DrawGLFunctor");
  }
  
  public Runnable getDestroyRunnable()
  {
    return this.mDestroyRunnable;
  }
  
  public boolean requestDrawGL(Canvas paramCanvas, Runnable paramRunnable)
  {
    if (this.mDestroyRunnable.mNativeDrawGLFunctor != 0L)
    {
      if (sSupportFunctorReleasedCallback) {
        this.mWebViewDelegate.callDrawGlFunction(paramCanvas, this.mDestroyRunnable.mNativeDrawGLFunctor, paramRunnable);
      } else {
        this.mWebViewDelegate.callDrawGlFunction(paramCanvas, this.mDestroyRunnable.mNativeDrawGLFunctor);
      }
      return true;
    }
    throw new RuntimeException("requestDrawGL on already destroyed DrawGLFunctor");
  }
  
  public boolean requestInvokeGL(View paramView, boolean paramBoolean)
  {
    if (this.mDestroyRunnable.mNativeDrawGLFunctor != 0L)
    {
      if ((!sSupportFunctorReleasedCallback) && (!this.mWebViewDelegate.canInvokeDrawGlFunctor(paramView))) {
        return false;
      }
      this.mWebViewDelegate.invokeDrawGlFunctor(paramView, this.mDestroyRunnable.mNativeDrawGLFunctor, paramBoolean);
      return true;
    }
    throw new RuntimeException("requestInvokeGL on already destroyed DrawGLFunctor");
  }
  
  public boolean supportsDrawGLFunctorReleasedCallback()
  {
    return sSupportFunctorReleasedCallback;
  }
  
  private static final class DestroyRunnable
    implements Runnable
  {
    private long mNativeDrawGLFunctor;
    
    DestroyRunnable(long paramLong)
    {
      this.mNativeDrawGLFunctor = paramLong;
    }
    
    public void run()
    {
      DrawGLFunctor.nativeDestroyGLFunctor(this.mNativeDrawGLFunctor);
      this.mNativeDrawGLFunctor = 0L;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\DrawGLFunctor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */