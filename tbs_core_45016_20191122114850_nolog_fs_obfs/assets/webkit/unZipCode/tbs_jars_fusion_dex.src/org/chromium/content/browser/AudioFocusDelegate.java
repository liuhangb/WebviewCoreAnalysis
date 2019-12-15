package org.chromium.content.browser;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import org.chromium.base.ContextUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("content")
public class AudioFocusDelegate
  implements AudioManager.OnAudioFocusChangeListener
{
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private boolean b;
  
  private AudioFocusDelegate(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
  }
  
  private boolean a()
  {
    return ((AudioManager)ContextUtils.getApplicationContext().getSystemService("audio")).requestAudioFocus(this, 3, this.jdField_a_of_type_Int) == 1;
  }
  
  @CalledByNative
  private void abandonAudioFocus()
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    ((AudioManager)ContextUtils.getApplicationContext().getSystemService("audio")).abandonAudioFocus(this);
  }
  
  @CalledByNative
  private static AudioFocusDelegate create(long paramLong)
  {
    return new AudioFocusDelegate(paramLong);
  }
  
  private native void nativeOnResume(long paramLong);
  
  private native void nativeOnStartDucking(long paramLong);
  
  private native void nativeOnStopDucking(long paramLong);
  
  private native void nativeOnSuspend(long paramLong);
  
  private native void nativeRecordSessionDuck(long paramLong);
  
  @CalledByNative
  private boolean requestAudioFocus(boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    int i = 1;
    if (paramBoolean2) {
      return true;
    }
    if (paramBoolean1) {
      i = 3;
    }
    this.jdField_a_of_type_Int = i;
    return a();
  }
  
  @CalledByNative
  private void tearDown()
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    abandonAudioFocus();
    this.jdField_a_of_type_Long = 0L;
  }
  
  public void onAudioFocusChange(int paramInt)
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return;
    }
    if (paramInt != 1)
    {
      switch (paramInt)
      {
      default: 
        return;
      case -1: 
        abandonAudioFocus();
        nativeOnSuspend(this.jdField_a_of_type_Long);
        return;
      case -2: 
        nativeOnSuspend(l);
        return;
      }
      this.b = true;
      nativeRecordSessionDuck(l);
      nativeOnStartDucking(this.jdField_a_of_type_Long);
      return;
    }
    if (this.b)
    {
      nativeOnStopDucking(l);
      this.b = false;
      return;
    }
    nativeOnResume(l);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\AudioFocusDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */