package org.chromium.media;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.util.SparseArray;
import java.nio.ByteBuffer;
import org.chromium.base.Log;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("media")
class MediaCodecEncoder
  extends MediaCodecBridge
{
  private SparseArray<ByteBuffer> jdField_a_of_type_AndroidUtilSparseArray = new SparseArray();
  private ByteBuffer jdField_a_of_type_JavaNioByteBuffer = null;
  
  protected MediaCodecEncoder(MediaCodec paramMediaCodec, BitrateAdjuster paramBitrateAdjuster)
  {
    super(paramMediaCodec, paramBitrateAdjuster);
  }
  
  private ByteBuffer a(int paramInt)
  {
    ByteBuffer localByteBuffer = super.getOutputBuffer(paramInt);
    if (localByteBuffer != null) {
      return localByteBuffer;
    }
    throw new IllegalStateException("Got null output buffer");
  }
  
  protected int a(MediaCodec.BufferInfo paramBufferInfo, long paramLong)
  {
    int j = -1;
    for (;;)
    {
      try
      {
        int k = this.jdField_a_of_type_AndroidMediaMediaCodec.dequeueOutputBuffer(paramBufferInfo, paramLong);
        int i = k;
        Object localObject;
        if (k >= 0)
        {
          j = k;
          if ((paramBufferInfo.flags & 0x2) == 0) {
            break label501;
          }
          j = 1;
          i = k;
          if (j != 0)
          {
            j = k;
            localObject = a(k);
            j = k;
            ((ByteBuffer)localObject).position(paramBufferInfo.offset);
            j = k;
            ((ByteBuffer)localObject).limit(paramBufferInfo.offset + paramBufferInfo.size);
            j = k;
            this.jdField_a_of_type_JavaNioByteBuffer = ByteBuffer.allocateDirect(paramBufferInfo.size);
            j = k;
            this.jdField_a_of_type_JavaNioByteBuffer.put((ByteBuffer)localObject);
            j = k;
            localObject = new StringBuilder();
            i = 0;
            j = k;
            int m = paramBufferInfo.size;
            j = 8;
            if (m < 8)
            {
              j = k;
              m = paramBufferInfo.size;
              j = m;
            }
            if (i < j)
            {
              j = k;
              ((StringBuilder)localObject).append(Integer.toHexString(this.jdField_a_of_type_JavaNioByteBuffer.get(i) & 0xFF));
              j = k;
              ((StringBuilder)localObject).append(" ");
              i += 1;
              continue;
            }
            j = k;
            this.jdField_a_of_type_AndroidMediaMediaCodec.releaseOutputBuffer(k, false);
            j = k;
            i = this.jdField_a_of_type_AndroidMediaMediaCodec.dequeueOutputBuffer(paramBufferInfo, paramLong);
          }
        }
        j = i;
        if (i >= 0)
        {
          j = i;
          localObject = a(i);
          j = i;
          ((ByteBuffer)localObject).position(paramBufferInfo.offset);
          j = i;
          ((ByteBuffer)localObject).limit(paramBufferInfo.offset + paramBufferInfo.size);
          j = i;
          if ((paramBufferInfo.flags & 0x1) == 0) {
            break label507;
          }
          j = 1;
          if (j != 0)
          {
            j = i;
            if (this.jdField_a_of_type_JavaNioByteBuffer != null)
            {
              j = i;
              ByteBuffer localByteBuffer = ByteBuffer.allocateDirect(this.jdField_a_of_type_JavaNioByteBuffer.capacity() + paramBufferInfo.size);
              j = i;
              this.jdField_a_of_type_JavaNioByteBuffer.rewind();
              j = i;
              localByteBuffer.put(this.jdField_a_of_type_JavaNioByteBuffer);
              j = i;
              localByteBuffer.put((ByteBuffer)localObject);
              j = i;
              localByteBuffer.rewind();
              j = i;
              paramBufferInfo.offset = 0;
              j = i;
              paramBufferInfo.size += this.jdField_a_of_type_JavaNioByteBuffer.capacity();
              j = i;
              this.jdField_a_of_type_AndroidUtilSparseArray.put(i, localByteBuffer);
              return i;
            }
          }
          j = i;
          this.jdField_a_of_type_AndroidUtilSparseArray.put(i, localObject);
          return i;
        }
      }
      catch (IllegalStateException paramBufferInfo)
      {
        Log.e("cr_MediaCodecEncoder", "Failed to dequeue output buffer", new Object[] { paramBufferInfo });
      }
      return j;
      label501:
      j = 0;
      continue;
      label507:
      j = 0;
    }
  }
  
  protected ByteBuffer getOutputBuffer(int paramInt)
  {
    return (ByteBuffer)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramInt);
  }
  
  protected void releaseOutputBuffer(int paramInt, boolean paramBoolean)
  {
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaCodec.releaseOutputBuffer(paramInt, paramBoolean);
      this.jdField_a_of_type_AndroidUtilSparseArray.remove(paramInt);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_MediaCodecEncoder", "Failed to release output buffer", new Object[] { localIllegalStateException });
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\MediaCodecEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */