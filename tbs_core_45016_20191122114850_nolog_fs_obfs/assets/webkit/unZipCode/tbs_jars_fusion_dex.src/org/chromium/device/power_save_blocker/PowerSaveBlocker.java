package org.chromium.device.power_save_blocker;

import android.view.View;
import java.lang.ref.WeakReference;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("device")
class PowerSaveBlocker
{
  private WeakReference<View> jdField_a_of_type_JavaLangRefWeakReference;
  
  @CalledByNative
  private void applyBlock(View paramView)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_JavaLangRefWeakReference != null)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_JavaLangRefWeakReference = new WeakReference(paramView);
    paramView.setKeepScreenOn(true);
  }
  
  @CalledByNative
  private static PowerSaveBlocker create()
  {
    return new PowerSaveBlocker();
  }
  
  @CalledByNative
  private void removeBlock()
  {
    Object localObject = this.jdField_a_of_type_JavaLangRefWeakReference;
    if (localObject == null) {
      return;
    }
    localObject = (View)((WeakReference)localObject).get();
    this.jdField_a_of_type_JavaLangRefWeakReference = null;
    if (localObject == null) {
      return;
    }
    ((View)localObject).setKeepScreenOn(false);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\power_save_blocker\PowerSaveBlocker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */