package org.chromium.content.browser;

import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("content")
public class InterstitialPageDelegateAndroid
{
  private long a = nativeInit(paramString);
  
  @VisibleForTesting
  public InterstitialPageDelegateAndroid(String paramString) {}
  
  private native void nativeDontProceed(long paramLong);
  
  private native long nativeInit(String paramString);
  
  private native void nativeProceed(long paramLong);
  
  @CalledByNative
  private void onNativeDestroyed()
  {
    this.a = 0L;
  }
  
  @CalledByNative
  protected void commandReceived(String paramString) {}
  
  @VisibleForTesting
  public long getNative()
  {
    return this.a;
  }
  
  @CalledByNative
  protected void onDontProceed() {}
  
  @CalledByNative
  protected void onProceed() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\InterstitialPageDelegateAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */