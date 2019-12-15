package org.chromium.device.vr;

import android.content.Context;
import android.view.WindowManager;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("device")
public class NonPresentingGvrContext
{
  private long jdField_a_of_type_Long;
  private boolean jdField_a_of_type_Boolean;
  
  private NonPresentingGvrContext(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    ((WindowManager)ContextUtils.getApplicationContext().getSystemService("window")).getDefaultDisplay();
    resume();
  }
  
  @CalledByNative
  private static NonPresentingGvrContext create(long paramLong)
  {
    try
    {
      NonPresentingGvrContext localNonPresentingGvrContext = new NonPresentingGvrContext(paramLong);
      return localNonPresentingGvrContext;
    }
    catch (IllegalStateException|UnsatisfiedLinkError localIllegalStateException)
    {
      for (;;) {}
    }
    return null;
  }
  
  @CalledByNative
  private long getNativeGvrContext()
  {
    return 0L;
  }
  
  private native void nativeOnDisplayConfigurationChanged(long paramLong);
  
  @CalledByNative
  private void pause()
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_Boolean = false;
  }
  
  @CalledByNative
  private void resume()
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_Boolean = true;
  }
  
  @CalledByNative
  private void shutdown()
  {
    this.jdField_a_of_type_Long = 0L;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\vr\NonPresentingGvrContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */