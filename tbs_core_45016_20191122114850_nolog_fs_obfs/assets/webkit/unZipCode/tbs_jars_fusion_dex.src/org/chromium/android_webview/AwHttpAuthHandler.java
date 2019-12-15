package org.chromium.android_webview;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwHttpAuthHandler
{
  private long jdField_a_of_type_Long;
  private final boolean jdField_a_of_type_Boolean;
  
  private AwHttpAuthHandler(long paramLong, boolean paramBoolean)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  @CalledByNative
  public static AwHttpAuthHandler create(long paramLong, boolean paramBoolean)
  {
    return new AwHttpAuthHandler(paramLong, paramBoolean);
  }
  
  private native void nativeCancel(long paramLong);
  
  private native void nativeProceed(long paramLong, String paramString1, String paramString2);
  
  public void cancel()
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L)
    {
      nativeCancel(l);
      this.jdField_a_of_type_Long = 0L;
    }
  }
  
  @CalledByNative
  void handlerDestroyed()
  {
    this.jdField_a_of_type_Long = 0L;
  }
  
  public boolean isFirstAttempt()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public void proceed(String paramString1, String paramString2)
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L)
    {
      nativeProceed(l, paramString1, paramString2);
      this.jdField_a_of_type_Long = 0L;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwHttpAuthHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */