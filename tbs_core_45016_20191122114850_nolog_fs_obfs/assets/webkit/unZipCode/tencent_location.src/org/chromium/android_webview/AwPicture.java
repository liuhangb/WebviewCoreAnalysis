package org.chromium.android_webview;

import android.graphics.Canvas;
import android.graphics.Picture;
import java.io.OutputStream;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
class AwPicture
  extends Picture
{
  private long jdField_a_of_type_Long;
  private CleanupReference jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference;
  
  AwPicture(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference = new CleanupReference(this, new DestroyRunnable(paramLong, null));
  }
  
  private void a()
  {
    throw new IllegalStateException("Unsupported in AwPicture");
  }
  
  private static native void nativeDestroy(long paramLong);
  
  private native void nativeDraw(long paramLong, Canvas paramCanvas);
  
  private native int nativeGetHeight(long paramLong);
  
  private native int nativeGetWidth(long paramLong);
  
  public Canvas beginRecording(int paramInt1, int paramInt2)
  {
    a();
    return null;
  }
  
  public void draw(Canvas paramCanvas)
  {
    nativeDraw(this.jdField_a_of_type_Long, paramCanvas);
  }
  
  public void endRecording() {}
  
  public int getHeight()
  {
    return nativeGetHeight(this.jdField_a_of_type_Long);
  }
  
  public int getWidth()
  {
    return nativeGetWidth(this.jdField_a_of_type_Long);
  }
  
  public void writeToStream(OutputStream paramOutputStream)
  {
    a();
  }
  
  private static final class DestroyRunnable
    implements Runnable
  {
    private long a;
    
    private DestroyRunnable(long paramLong)
    {
      this.a = paramLong;
    }
    
    public void run()
    {
      AwPicture.a(this.a);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwPicture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */