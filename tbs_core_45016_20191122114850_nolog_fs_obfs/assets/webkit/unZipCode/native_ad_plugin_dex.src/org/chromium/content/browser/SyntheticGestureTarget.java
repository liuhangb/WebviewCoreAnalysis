package org.chromium.content.browser;

import android.view.View;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("content")
public class SyntheticGestureTarget
{
  private final MotionEventSynthesizer a;
  
  private SyntheticGestureTarget(View paramView)
  {
    this.a = new MotionEventSynthesizer(paramView);
  }
  
  @CalledByNative
  private static SyntheticGestureTarget create(View paramView)
  {
    return new SyntheticGestureTarget(paramView);
  }
  
  @CalledByNative
  private void inject(int paramInt1, int paramInt2, long paramLong)
  {
    this.a.inject(paramInt1, paramInt2, paramLong);
  }
  
  @CalledByNative
  private void setPointer(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.a.setPointer(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  @CalledByNative
  private void setScrollDeltas(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.a.setScrollDeltas(paramInt1, paramInt2, paramInt3, paramInt4);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\SyntheticGestureTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */