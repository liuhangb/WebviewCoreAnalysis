package org.chromium.media;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("media")
public abstract class VideoCapture
{
  protected int a;
  protected final long a;
  protected VideoCaptureFormat a;
  protected boolean a;
  protected int b;
  protected boolean b;
  protected final int c;
  
  VideoCapture(int paramInt, long paramLong)
  {
    this.c = paramInt;
    this.jdField_a_of_type_Long = paramLong;
  }
  
  protected static FramerateRange a(List<FramerateRange> paramList, int paramInt)
  {
    (FramerateRange)Collections.min(paramList, new Comparator()
    {
      private int a(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
      {
        if (paramAnonymousInt1 < paramAnonymousInt2) {
          return paramAnonymousInt1 * paramAnonymousInt3;
        }
        return (paramAnonymousInt1 - paramAnonymousInt2) * paramAnonymousInt4 + paramAnonymousInt3 * paramAnonymousInt2;
      }
      
      int a(VideoCapture.FramerateRange paramAnonymousFramerateRange)
      {
        return a(paramAnonymousFramerateRange.a, 8000, 1, 4) + a(Math.abs(this.a - paramAnonymousFramerateRange.b), 5000, 1, 3);
      }
      
      public int a(VideoCapture.FramerateRange paramAnonymousFramerateRange1, VideoCapture.FramerateRange paramAnonymousFramerateRange2)
      {
        return a(paramAnonymousFramerateRange1) - a(paramAnonymousFramerateRange2);
      }
    });
  }
  
  protected static int[] a(ArrayList<Integer> paramArrayList)
  {
    int[] arrayOfInt = new int[paramArrayList.size()];
    int i = 0;
    while (i < paramArrayList.size())
    {
      arrayOfInt[i] = ((Integer)paramArrayList.get(i)).intValue();
      i += 1;
    }
    return arrayOfInt;
  }
  
  @CalledByNative
  public abstract boolean allocate(int paramInt1, int paramInt2, int paramInt3);
  
  @CalledByNative
  public abstract void deallocate();
  
  protected final int getCameraRotation()
  {
    int i;
    if (this.jdField_a_of_type_Boolean) {
      i = 360 - getDeviceRotation();
    } else {
      i = getDeviceRotation();
    }
    int j = i;
    if (!this.jdField_a_of_type_Boolean) {
      if (i != 90)
      {
        j = i;
        if (i != 270) {}
      }
      else
      {
        j = i + 180;
      }
    }
    return (this.jdField_a_of_type_Int + j) % 360;
  }
  
  @CalledByNative
  public final int getColorspace()
  {
    int i = this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.d;
    if (i != 17)
    {
      if (i != 35)
      {
        if (i != 842094169) {
          return 0;
        }
        return 842094169;
      }
      return 35;
    }
    return 17;
  }
  
  protected final int getDeviceRotation()
  {
    switch (((WindowManager)ContextUtils.getApplicationContext().getSystemService("window")).getDefaultDisplay().getRotation())
    {
    default: 
      return 0;
    case 3: 
      return 270;
    case 2: 
      return 180;
    }
    return 90;
  }
  
  @CalledByNative
  public abstract PhotoCapabilities getPhotoCapabilities();
  
  public native void nativeOnError(long paramLong, String paramString);
  
  public native void nativeOnFrameAvailable(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3);
  
  public native void nativeOnI420FrameAvailable(long paramLong1, ByteBuffer paramByteBuffer1, int paramInt1, ByteBuffer paramByteBuffer2, ByteBuffer paramByteBuffer3, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong2, boolean paramBoolean, int paramInt7);
  
  public native void nativeOnPhotoTaken(long paramLong1, long paramLong2, byte[] paramArrayOfByte);
  
  public native void nativeOnStarted(long paramLong);
  
  @CalledByNative
  public final int queryFrameRate()
  {
    return this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.c;
  }
  
  @CalledByNative
  public final int queryHeight()
  {
    return this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.b;
  }
  
  @CalledByNative
  public final int queryWidth()
  {
    return this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.jdField_a_of_type_Int;
  }
  
  @CalledByNative
  public abstract void setPhotoOptions(double paramDouble1, int paramInt1, int paramInt2, double paramDouble2, double paramDouble3, float[] paramArrayOfFloat, boolean paramBoolean1, double paramDouble4, int paramInt3, double paramDouble5, boolean paramBoolean2, boolean paramBoolean3, int paramInt4, boolean paramBoolean4, boolean paramBoolean5, double paramDouble6);
  
  @CalledByNative
  public final void setTestMode()
  {
    this.b = true;
  }
  
  @CalledByNative
  public abstract boolean startCapture();
  
  @CalledByNative
  public abstract boolean stopCapture();
  
  @CalledByNative
  public abstract boolean takePhoto(long paramLong);
  
  protected static class FramerateRange
  {
    public int a;
    public int b;
    
    public FramerateRange(int paramInt1, int paramInt2)
    {
      this.a = paramInt1;
      this.b = paramInt2;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\VideoCapture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */