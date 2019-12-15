package org.chromium.media;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodec.CryptoException;
import android.media.MediaCodec.CryptoInfo;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.Surface;
import java.nio.ByteBuffer;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("media")
class MediaCodecBridge
{
  private long jdField_a_of_type_Long;
  protected MediaCodec a;
  private BitrateAdjuster jdField_a_of_type_OrgChromiumMediaBitrateAdjuster;
  private ByteBuffer[] jdField_a_of_type_ArrayOfJavaNioByteBuffer;
  private boolean jdField_b_of_type_Boolean;
  private ByteBuffer[] jdField_b_of_type_ArrayOfJavaNioByteBuffer;
  
  MediaCodecBridge(MediaCodec paramMediaCodec, BitrateAdjuster paramBitrateAdjuster)
  {
    if ((!jdField_a_of_type_Boolean) && (paramMediaCodec == null)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_AndroidMediaMediaCodec = paramMediaCodec;
    this.jdField_a_of_type_Long = 0L;
    this.jdField_b_of_type_Boolean = true;
    this.jdField_a_of_type_OrgChromiumMediaBitrateAdjuster = paramBitrateAdjuster;
  }
  
  private int a(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unsupported cipher mode: ");
      localStringBuilder.append(paramInt);
      Log.e("cr_MediaCodecBridge", localStringBuilder.toString(), new Object[0]);
      return -1;
    case 2: 
      return 2;
    case 1: 
      return 1;
    }
    return 0;
  }
  
  private void a(long paramLong)
  {
    if (this.jdField_b_of_type_Boolean)
    {
      this.jdField_a_of_type_Long = Math.max(paramLong - 100000L, 0L);
      this.jdField_b_of_type_Boolean = false;
    }
  }
  
  @CalledByNative
  private DequeueInputResult dequeueInputBuffer(long paramLong)
  {
    int j = -1;
    int i = 5;
    int k;
    try
    {
      k = this.jdField_a_of_type_AndroidMediaMediaCodec.dequeueInputBuffer(paramLong);
      if (k < 0) {
        break label115;
      }
      j = k;
      i = 0;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder;
      Log.e("cr_MediaCodecBridge", "Failed to dequeue input buffer", new Object[] { localException });
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unexpected index_or_status: ");
    localStringBuilder.append(k);
    Log.e("cr_MediaCodecBridge", localStringBuilder.toString(), new Object[0]);
    if (!jdField_a_of_type_Boolean) {
      throw new AssertionError();
    }
    for (;;)
    {
      return new DequeueInputResult(i, j, null);
      label115:
      if (k != -1) {
        break;
      }
      i = 1;
    }
  }
  
  @CalledByNative
  private DequeueOutputResult dequeueOutputBuffer(long paramLong)
  {
    MediaCodec.BufferInfo localBufferInfo = new MediaCodec.BufferInfo();
    int j = -1;
    int i = 5;
    int k;
    try
    {
      k = a(localBufferInfo, paramLong);
      if (localBufferInfo.presentationTimeUs < this.jdField_a_of_type_Long) {
        localBufferInfo.presentationTimeUs = this.jdField_a_of_type_Long;
      }
      this.jdField_a_of_type_Long = localBufferInfo.presentationTimeUs;
      if (k >= 0)
      {
        j = k;
        i = 0;
      }
      else if (k == -3)
      {
        this.jdField_b_of_type_ArrayOfJavaNioByteBuffer = this.jdField_a_of_type_AndroidMediaMediaCodec.getOutputBuffers();
        i = 2;
      }
      else if (k == -2)
      {
        this.jdField_a_of_type_AndroidMediaMediaCodec.getOutputFormat();
        i = 3;
      }
    }
    catch (IllegalStateException localIllegalStateException)
    {
      StringBuilder localStringBuilder;
      Log.e("cr_MediaCodecBridge", "Failed to dequeue output buffer", new Object[] { localIllegalStateException });
      i = 5;
      j = -1;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unexpected index_or_status: ");
    localStringBuilder.append(k);
    Log.e("cr_MediaCodecBridge", localStringBuilder.toString(), new Object[0]);
    if (!jdField_a_of_type_Boolean) {
      throw new AssertionError();
    }
    for (;;)
    {
      return new DequeueOutputResult(i, j, localBufferInfo.flags, localBufferInfo.offset, localBufferInfo.presentationTimeUs, localBufferInfo.size, null);
      if (k != -1) {
        break;
      }
      i = 1;
    }
  }
  
  @CalledByNative
  private int flush()
  {
    try
    {
      this.jdField_b_of_type_Boolean = true;
      this.jdField_a_of_type_AndroidMediaMediaCodec.flush();
      return 0;
    }
    catch (Exception localException)
    {
      Log.e("cr_MediaCodecBridge", "Failed to flush MediaCodec", new Object[] { localException });
    }
    return 5;
  }
  
  @SuppressLint({"NewApi"})
  @CalledByNative
  private ByteBuffer getInputBuffer(int paramInt)
  {
    if (Build.VERSION.SDK_INT > 19) {
      try
      {
        ByteBuffer localByteBuffer = this.jdField_a_of_type_AndroidMediaMediaCodec.getInputBuffer(paramInt);
        return localByteBuffer;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        Log.e("cr_MediaCodecBridge", "Failed to get input buffer", new Object[] { localIllegalStateException });
        return null;
      }
    }
    return this.jdField_a_of_type_ArrayOfJavaNioByteBuffer[paramInt];
  }
  
  @TargetApi(19)
  @CalledByNative
  private String getName()
  {
    try
    {
      String str = this.jdField_a_of_type_AndroidMediaMediaCodec.getName();
      return str;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_MediaCodecBridge", "Cannot get codec name", new Object[] { localIllegalStateException });
    }
    return "unknown";
  }
  
  @CalledByNative
  private GetOutputFormatResult getOutputFormat()
  {
    int i = 0;
    MediaFormat localMediaFormat2;
    try
    {
      MediaFormat localMediaFormat1 = this.jdField_a_of_type_AndroidMediaMediaCodec.getOutputFormat();
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_MediaCodecBridge", "Failed to get output format", new Object[] { localIllegalStateException });
      i = 5;
      localMediaFormat2 = null;
    }
    return new GetOutputFormatResult(i, localMediaFormat2, null);
  }
  
  @CalledByNative
  private int queueInputBuffer(int paramInt1, int paramInt2, int paramInt3, long paramLong, int paramInt4)
  {
    a(paramLong);
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaCodec.queueInputBuffer(paramInt1, paramInt2, paramInt3, paramLong, paramInt4);
      return 0;
    }
    catch (Exception localException)
    {
      Log.e("cr_MediaCodecBridge", "Failed to queue input buffer", new Object[] { localException });
    }
    return 5;
  }
  
  @SuppressLint({"WrongConstant"})
  @CalledByNative
  private int queueSecureInputBuffer(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong)
  {
    a(paramLong);
    for (;;)
    {
      int i;
      try
      {
        i = a(paramInt4);
        if (i != -1) {
          break label266;
        }
        return 5;
      }
      catch (IllegalStateException paramArrayOfByte1)
      {
        MediaCodec.CryptoInfo localCryptoInfo;
        paramArrayOfByte2 = new StringBuilder();
        paramArrayOfByte2.append("Failed to queue secure input buffer, IllegalStateException ");
        paramArrayOfByte2.append(paramArrayOfByte1);
        Log.e("cr_MediaCodecBridge", paramArrayOfByte2.toString(), new Object[0]);
        return 5;
      }
      catch (IllegalArgumentException paramArrayOfByte1)
      {
        paramArrayOfByte2 = new StringBuilder();
        paramArrayOfByte2.append("Failed to queue secure input buffer, IllegalArgumentException ");
        paramArrayOfByte2.append(paramArrayOfByte1);
        Log.e("cr_MediaCodecBridge", paramArrayOfByte2.toString(), new Object[0]);
        return 5;
      }
      catch (MediaCodec.CryptoException paramArrayOfByte1)
      {
        if (paramArrayOfByte1.getErrorCode() != 1) {
          continue;
        }
        return 4;
        paramArrayOfByte2 = new StringBuilder();
        paramArrayOfByte2.append("Failed to queue secure input buffer, CryptoException with error code ");
        paramArrayOfByte2.append(paramArrayOfByte1.getErrorCode());
        Log.e("cr_MediaCodecBridge", paramArrayOfByte2.toString(), new Object[0]);
        return 5;
      }
      if ((paramInt4 != 0) && (!MediaCodecUtil.platformSupportsCbcsEncryption(Build.VERSION.SDK_INT)))
      {
        Log.e("cr_MediaCodecBridge", "Encryption scheme 'cbcs' not supported on this platform.", new Object[0]);
        return 5;
      }
      localCryptoInfo = new MediaCodec.CryptoInfo();
      localCryptoInfo.set(paramInt3, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfByte2, paramArrayOfByte1, i);
      if ((paramInt5 != 0) && (paramInt6 != 0)) {
        if (paramInt4 != 0)
        {
          MediaCodecUtil.a(localCryptoInfo, paramInt5, paramInt6);
        }
        else
        {
          Log.e("cr_MediaCodecBridge", "Pattern encryption only supported for 'cbcs' scheme (CBC mode).", new Object[0]);
          return 5;
        }
      }
      this.jdField_a_of_type_AndroidMediaMediaCodec.queueSecureInputBuffer(paramInt1, paramInt2, localCryptoInfo, paramLong, 0);
      return 0;
      label266:
      if (i == 2) {
        paramInt4 = 1;
      } else {
        paramInt4 = 0;
      }
    }
  }
  
  @TargetApi(19)
  @CalledByNative
  private void requestKeyFrameSoon()
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("request-sync", 0);
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaCodec.setParameters(localBundle);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_MediaCodecBridge", "Failed to set MediaCodec parameters", new Object[] { localIllegalStateException });
    }
  }
  
  @TargetApi(23)
  @CalledByNative
  private boolean setSurface(Surface paramSurface)
  {
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaCodec.setOutputSurface(paramSurface);
      return true;
    }
    catch (IllegalStateException paramSurface) {}catch (IllegalArgumentException paramSurface) {}
    Log.e("cr_MediaCodecBridge", "Cannot set output surface", new Object[] { paramSurface });
    return false;
  }
  
  @TargetApi(19)
  @CalledByNative
  private void setVideoBitrate(int paramInt1, int paramInt2)
  {
    paramInt1 = this.jdField_a_of_type_OrgChromiumMediaBitrateAdjuster.getTargetBitrate(paramInt1, paramInt2);
    Bundle localBundle = new Bundle();
    localBundle.putInt("video-bitrate", paramInt1);
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaCodec.setParameters(localBundle);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_MediaCodecBridge", "Failed to set MediaCodec parameters", new Object[] { localIllegalStateException });
    }
  }
  
  @CalledByNative
  private void stop()
  {
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaCodec.stop();
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_MediaCodecBridge", "Failed to stop MediaCodec", new Object[] { localIllegalStateException });
    }
  }
  
  protected int a(MediaCodec.BufferInfo paramBufferInfo, long paramLong)
  {
    return this.jdField_a_of_type_AndroidMediaMediaCodec.dequeueOutputBuffer(paramBufferInfo, paramLong);
  }
  
  boolean a()
  {
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaCodec.start();
      if (Build.VERSION.SDK_INT <= 19)
      {
        this.jdField_a_of_type_ArrayOfJavaNioByteBuffer = this.jdField_a_of_type_AndroidMediaMediaCodec.getInputBuffers();
        this.jdField_b_of_type_ArrayOfJavaNioByteBuffer = this.jdField_a_of_type_AndroidMediaMediaCodec.getOutputBuffers();
      }
      return true;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Log.e("cr_MediaCodecBridge", "Cannot start the media codec", new Object[] { localIllegalArgumentException });
      return false;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_MediaCodecBridge", "Cannot start the media codec", new Object[] { localIllegalStateException });
    }
    return false;
  }
  
  boolean a(MediaFormat paramMediaFormat, MediaCrypto paramMediaCrypto, int paramInt)
  {
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaCodec.configure(paramMediaFormat, null, paramMediaCrypto, paramInt);
      return true;
    }
    catch (Exception paramMediaFormat)
    {
      Log.e("cr_MediaCodecBridge", "Cannot configure the audio codec", new Object[] { paramMediaFormat });
      return false;
    }
    catch (MediaCodec.CryptoException paramMediaFormat)
    {
      Log.e("cr_MediaCodecBridge", "Cannot configure the audio codec: DRM error", new Object[] { paramMediaFormat });
      return false;
    }
    catch (IllegalStateException paramMediaFormat)
    {
      Log.e("cr_MediaCodecBridge", "Cannot configure the audio codec", new Object[] { paramMediaFormat });
      return false;
    }
    catch (IllegalArgumentException paramMediaFormat)
    {
      Log.e("cr_MediaCodecBridge", "Cannot configure the audio codec", new Object[] { paramMediaFormat });
    }
    return false;
  }
  
  boolean a(MediaFormat paramMediaFormat, Surface paramSurface, MediaCrypto paramMediaCrypto, int paramInt)
  {
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaCodec.configure(paramMediaFormat, paramSurface, paramMediaCrypto, paramInt);
      return true;
    }
    catch (Exception paramMediaFormat)
    {
      Log.e("cr_MediaCodecBridge", "Cannot configure the video codec", new Object[] { paramMediaFormat });
      return false;
    }
    catch (MediaCodec.CryptoException paramMediaFormat)
    {
      Log.e("cr_MediaCodecBridge", "Cannot configure the video codec: DRM error", new Object[] { paramMediaFormat });
      return false;
    }
    catch (IllegalStateException paramMediaFormat)
    {
      Log.e("cr_MediaCodecBridge", "Cannot configure the video codec", new Object[] { paramMediaFormat });
      return false;
    }
    catch (IllegalArgumentException paramMediaFormat)
    {
      Log.e("cr_MediaCodecBridge", "Cannot configure the video codec, wrong format or surface", new Object[] { paramMediaFormat });
    }
    return false;
  }
  
  @SuppressLint({"NewApi"})
  @CalledByNative
  protected ByteBuffer getOutputBuffer(int paramInt)
  {
    if (Build.VERSION.SDK_INT > 19) {
      try
      {
        ByteBuffer localByteBuffer = this.jdField_a_of_type_AndroidMediaMediaCodec.getOutputBuffer(paramInt);
        return localByteBuffer;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        Log.e("cr_MediaCodecBridge", "Failed to get output buffer", new Object[] { localIllegalStateException });
        return null;
      }
    }
    return this.jdField_b_of_type_ArrayOfJavaNioByteBuffer[paramInt];
  }
  
  @CalledByNative
  void release()
  {
    try
    {
      if (Build.VERSION.SDK_INT >= 18) {
        this.jdField_a_of_type_AndroidMediaMediaCodec.getName();
      }
      this.jdField_a_of_type_AndroidMediaMediaCodec.release();
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_MediaCodecBridge", "Cannot release media codec", new Object[] { localIllegalStateException });
    }
    this.jdField_a_of_type_AndroidMediaMediaCodec = null;
  }
  
  @CalledByNative
  protected void releaseOutputBuffer(int paramInt, boolean paramBoolean)
  {
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaCodec.releaseOutputBuffer(paramInt, paramBoolean);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_MediaCodecBridge", "Failed to release output buffer", new Object[] { localIllegalStateException });
    }
  }
  
  @MainDex
  private static class DequeueInputResult
  {
    private final int a;
    private final int b;
    
    private DequeueInputResult(int paramInt1, int paramInt2)
    {
      this.a = paramInt1;
      this.b = paramInt2;
    }
    
    @CalledByNative("DequeueInputResult")
    private int index()
    {
      return this.b;
    }
    
    @CalledByNative("DequeueInputResult")
    private int status()
    {
      return this.a;
    }
  }
  
  @MainDex
  private static class DequeueOutputResult
  {
    private final int jdField_a_of_type_Int;
    private final long jdField_a_of_type_Long;
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    
    private DequeueOutputResult(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong, int paramInt5)
    {
      this.jdField_a_of_type_Int = paramInt1;
      this.b = paramInt2;
      this.c = paramInt3;
      this.d = paramInt4;
      this.jdField_a_of_type_Long = paramLong;
      this.e = paramInt5;
    }
    
    @CalledByNative("DequeueOutputResult")
    private int flags()
    {
      return this.c;
    }
    
    @CalledByNative("DequeueOutputResult")
    private int index()
    {
      return this.b;
    }
    
    @CalledByNative("DequeueOutputResult")
    private int numBytes()
    {
      return this.e;
    }
    
    @CalledByNative("DequeueOutputResult")
    private int offset()
    {
      return this.d;
    }
    
    @CalledByNative("DequeueOutputResult")
    private long presentationTimeMicroseconds()
    {
      return this.jdField_a_of_type_Long;
    }
    
    @CalledByNative("DequeueOutputResult")
    private int status()
    {
      return this.jdField_a_of_type_Int;
    }
  }
  
  @MainDex
  private static class GetOutputFormatResult
  {
    private final int jdField_a_of_type_Int;
    private final MediaFormat jdField_a_of_type_AndroidMediaMediaFormat;
    
    private GetOutputFormatResult(int paramInt, MediaFormat paramMediaFormat)
    {
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_AndroidMediaMediaFormat = paramMediaFormat;
    }
    
    private boolean a()
    {
      return (this.jdField_a_of_type_AndroidMediaMediaFormat.containsKey("crop-right")) && (this.jdField_a_of_type_AndroidMediaMediaFormat.containsKey("crop-left")) && (this.jdField_a_of_type_AndroidMediaMediaFormat.containsKey("crop-bottom")) && (this.jdField_a_of_type_AndroidMediaMediaFormat.containsKey("crop-top"));
    }
    
    @CalledByNative("GetOutputFormatResult")
    private int channelCount()
    {
      return this.jdField_a_of_type_AndroidMediaMediaFormat.getInteger("channel-count");
    }
    
    @CalledByNative("GetOutputFormatResult")
    private int height()
    {
      if (a()) {
        return this.jdField_a_of_type_AndroidMediaMediaFormat.getInteger("crop-bottom") - this.jdField_a_of_type_AndroidMediaMediaFormat.getInteger("crop-top") + 1;
      }
      return this.jdField_a_of_type_AndroidMediaMediaFormat.getInteger("height");
    }
    
    @CalledByNative("GetOutputFormatResult")
    private int sampleRate()
    {
      return this.jdField_a_of_type_AndroidMediaMediaFormat.getInteger("sample-rate");
    }
    
    @CalledByNative("GetOutputFormatResult")
    private int status()
    {
      return this.jdField_a_of_type_Int;
    }
    
    @CalledByNative("GetOutputFormatResult")
    private int width()
    {
      if (a()) {
        return this.jdField_a_of_type_AndroidMediaMediaFormat.getInteger("crop-right") - this.jdField_a_of_type_AndroidMediaMediaFormat.getInteger("crop-left") + 1;
      }
      return this.jdField_a_of_type_AndroidMediaMediaFormat.getInteger("width");
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\MediaCodecBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */