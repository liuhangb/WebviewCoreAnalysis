package org.chromium.media;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("media")
@MainDex
class HdrMetadata
{
  private long jdField_a_of_type_Long;
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  
  HdrMetadata()
  {
    this.jdField_a_of_type_Long = 0L;
  }
  
  private HdrMetadata(long paramLong)
  {
    if ((!jdField_a_of_type_Boolean) && (paramLong == 0L)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_Long = paramLong;
  }
  
  private float a()
  {
    return nativePrimaryRChromaticityX(this.jdField_a_of_type_Long);
  }
  
  private int a()
  {
    int i = nativePrimaries(this.jdField_a_of_type_Long);
    if (i != 1)
    {
      if (i != 9)
      {
        switch (i)
        {
        default: 
          return -1;
        }
        return 4;
      }
      return 6;
    }
    return 1;
  }
  
  private float b()
  {
    return nativePrimaryRChromaticityY(this.jdField_a_of_type_Long);
  }
  
  private int b()
  {
    int i = nativeColorTransfer(this.jdField_a_of_type_Long);
    if (i != 1)
    {
      if (i != 16) {
        if (i == 18) {}
      }
      switch (i)
      {
      default: 
        return -1;
      case 8: 
        return 1;
        return 7;
        return 6;
      }
    }
    return 3;
  }
  
  private float c()
  {
    return nativePrimaryGChromaticityX(this.jdField_a_of_type_Long);
  }
  
  private int c()
  {
    switch (nativeRange(this.jdField_a_of_type_Long))
    {
    default: 
      return -1;
    case 2: 
      return 1;
    }
    return 2;
  }
  
  @CalledByNative
  private void close()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.jdField_a_of_type_Long = 0L;
      return;
    }
  }
  
  @CalledByNative
  private static HdrMetadata create(long paramLong)
  {
    return new HdrMetadata(paramLong);
  }
  
  private float d()
  {
    return nativePrimaryGChromaticityY(this.jdField_a_of_type_Long);
  }
  
  private int d()
  {
    return nativeMaxContentLuminance(this.jdField_a_of_type_Long);
  }
  
  private float e()
  {
    return nativePrimaryBChromaticityX(this.jdField_a_of_type_Long);
  }
  
  private int e()
  {
    return nativeMaxFrameAverageLuminance(this.jdField_a_of_type_Long);
  }
  
  private float f()
  {
    return nativePrimaryBChromaticityY(this.jdField_a_of_type_Long);
  }
  
  private float g()
  {
    return nativeWhitePointChromaticityX(this.jdField_a_of_type_Long);
  }
  
  private float h()
  {
    return nativeWhitePointChromaticityY(this.jdField_a_of_type_Long);
  }
  
  private float i()
  {
    return nativeMaxMasteringLuminance(this.jdField_a_of_type_Long);
  }
  
  private float j()
  {
    return nativeMinMasteringLuminance(this.jdField_a_of_type_Long);
  }
  
  private native int nativeColorTransfer(long paramLong);
  
  private native int nativeMaxContentLuminance(long paramLong);
  
  private native int nativeMaxFrameAverageLuminance(long paramLong);
  
  private native float nativeMaxMasteringLuminance(long paramLong);
  
  private native float nativeMinMasteringLuminance(long paramLong);
  
  private native int nativePrimaries(long paramLong);
  
  private native float nativePrimaryBChromaticityX(long paramLong);
  
  private native float nativePrimaryBChromaticityY(long paramLong);
  
  private native float nativePrimaryGChromaticityX(long paramLong);
  
  private native float nativePrimaryGChromaticityY(long paramLong);
  
  private native float nativePrimaryRChromaticityX(long paramLong);
  
  private native float nativePrimaryRChromaticityY(long paramLong);
  
  private native int nativeRange(long paramLong);
  
  private native float nativeWhitePointChromaticityX(long paramLong);
  
  private native float nativeWhitePointChromaticityY(long paramLong);
  
  @TargetApi(24)
  public void a(MediaFormat paramMediaFormat)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
        throw new AssertionError();
      }
      if (Build.VERSION.SDK_INT < 24)
      {
        Log.e("HdrMetadata", "HDR not supported before Android N", new Object[0]);
        return;
      }
      int i = a();
      if (i != -1) {
        paramMediaFormat.setInteger("color-standard", i);
      }
      i = b();
      if (i != -1) {
        paramMediaFormat.setInteger("color-transfer", i);
      }
      i = c();
      if (i != -1) {
        paramMediaFormat.setInteger("color-range", i);
      }
      ByteBuffer localByteBuffer = ByteBuffer.wrap(new byte[25]);
      localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
      localByteBuffer.put((byte)0);
      localByteBuffer.putShort((short)(int)(a() * 50000.0F + 0.5F));
      localByteBuffer.putShort((short)(int)(b() * 50000.0F + 0.5F));
      localByteBuffer.putShort((short)(int)(c() * 50000.0F + 0.5F));
      localByteBuffer.putShort((short)(int)(d() * 50000.0F + 0.5F));
      localByteBuffer.putShort((short)(int)(e() * 50000.0F + 0.5F));
      localByteBuffer.putShort((short)(int)(f() * 50000.0F + 0.5F));
      localByteBuffer.putShort((short)(int)(g() * 50000.0F + 0.5F));
      localByteBuffer.putShort((short)(int)(h() * 50000.0F + 0.5F));
      localByteBuffer.putShort((short)(int)(i() + 0.5F));
      localByteBuffer.putShort((short)(int)(j() + 0.5F));
      localByteBuffer.putShort((short)d());
      localByteBuffer.putShort((short)e());
      localByteBuffer.rewind();
      paramMediaFormat.setByteBuffer("hdr-static-info", localByteBuffer);
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\HdrMetadata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */