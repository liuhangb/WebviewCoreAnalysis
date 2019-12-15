package org.chromium.media;

import android.annotation.SuppressLint;
import android.media.AudioRecord;
import android.media.audiofx.AcousticEchoCanceler;
import android.os.Process;
import java.nio.ByteBuffer;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

@JNINamespace("media")
class AudioRecordInput
{
  private final int jdField_a_of_type_Int;
  private final long jdField_a_of_type_Long;
  private AudioRecord jdField_a_of_type_AndroidMediaAudioRecord;
  private AcousticEchoCanceler jdField_a_of_type_AndroidMediaAudiofxAcousticEchoCanceler;
  private ByteBuffer jdField_a_of_type_JavaNioByteBuffer;
  private AudioRecordThread jdField_a_of_type_OrgChromiumMediaAudioRecordInput$AudioRecordThread;
  private final boolean jdField_a_of_type_Boolean;
  private final int b;
  private final int c;
  
  private AudioRecordInput(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_Int = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_JavaNioByteBuffer = ByteBuffer.allocateDirect(paramInt4);
    nativeCacheDirectBufferAddress(this.jdField_a_of_type_Long, this.jdField_a_of_type_JavaNioByteBuffer);
  }
  
  @SuppressLint({"NewApi"})
  @CalledByNative
  private void close()
  {
    if (this.jdField_a_of_type_OrgChromiumMediaAudioRecordInput$AudioRecordThread != null)
    {
      Log.e("cr.media", "close() called before stop().", new Object[0]);
      return;
    }
    if (this.jdField_a_of_type_AndroidMediaAudioRecord == null) {
      return;
    }
    AcousticEchoCanceler localAcousticEchoCanceler = this.jdField_a_of_type_AndroidMediaAudiofxAcousticEchoCanceler;
    if (localAcousticEchoCanceler != null)
    {
      localAcousticEchoCanceler.release();
      this.jdField_a_of_type_AndroidMediaAudiofxAcousticEchoCanceler = null;
    }
    this.jdField_a_of_type_AndroidMediaAudioRecord.release();
    this.jdField_a_of_type_AndroidMediaAudioRecord = null;
  }
  
  @CalledByNative
  private static AudioRecordInput createAudioRecordInput(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    return new AudioRecordInput(paramLong, paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean);
  }
  
  private native void nativeCacheDirectBufferAddress(long paramLong, ByteBuffer paramByteBuffer);
  
  private native void nativeOnData(long paramLong, int paramInt1, int paramInt2);
  
  @SuppressLint({"NewApi"})
  @CalledByNative
  private boolean open()
  {
    if (this.jdField_a_of_type_AndroidMediaAudioRecord != null)
    {
      Log.e("cr.media", "open() called twice without a close()", new Object[0]);
      return false;
    }
    int i = this.b;
    if (i == 1)
    {
      i = 16;
    }
    else
    {
      if (i != 2) {
        break label254;
      }
      i = 12;
    }
    int j = this.c;
    if (j == 8)
    {
      j = 3;
    }
    else
    {
      if (j != 16) {
        break label234;
      }
      j = 2;
    }
    int k = AudioRecord.getMinBufferSize(this.jdField_a_of_type_Int, i, j);
    if (k < 0)
    {
      Log.e("cr.media", "getMinBufferSize error: %d", new Object[] { Integer.valueOf(k) });
      return false;
    }
    k = Math.max(this.jdField_a_of_type_JavaNioByteBuffer.capacity(), k);
    try
    {
      this.jdField_a_of_type_AndroidMediaAudioRecord = new AudioRecord(7, this.jdField_a_of_type_Int, i, j, k);
      if (X5ApiCompatibilityUtils.isAcousticEchoCancelerAvailable())
      {
        this.jdField_a_of_type_AndroidMediaAudiofxAcousticEchoCanceler = AcousticEchoCanceler.create(this.jdField_a_of_type_AndroidMediaAudioRecord.getAudioSessionId());
        AcousticEchoCanceler localAcousticEchoCanceler = this.jdField_a_of_type_AndroidMediaAudiofxAcousticEchoCanceler;
        if (localAcousticEchoCanceler == null)
        {
          Log.e("cr.media", "AcousticEchoCanceler.create failed", new Object[0]);
          return false;
        }
        i = localAcousticEchoCanceler.setEnabled(this.jdField_a_of_type_Boolean);
        if (i != 0)
        {
          Log.e("cr.media", "setEnabled error: %d", new Object[] { Integer.valueOf(i) });
          return false;
        }
      }
      return true;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Log.e("cr.media", "AudioRecord failed", new Object[] { localIllegalArgumentException });
      return false;
    }
    label234:
    Log.e("cr.media", "Unsupported bits per sample: %d", new Object[] { Integer.valueOf(j) });
    return false;
    label254:
    Log.e("cr.media", "Unsupported number of channels: %d", new Object[] { Integer.valueOf(i) });
    return false;
  }
  
  @CalledByNative
  private void start()
  {
    if (this.jdField_a_of_type_AndroidMediaAudioRecord == null)
    {
      Log.e("cr.media", "start() called before open().", new Object[0]);
      return;
    }
    if (this.jdField_a_of_type_OrgChromiumMediaAudioRecordInput$AudioRecordThread != null) {
      return;
    }
    this.jdField_a_of_type_OrgChromiumMediaAudioRecordInput$AudioRecordThread = new AudioRecordThread(null);
    this.jdField_a_of_type_OrgChromiumMediaAudioRecordInput$AudioRecordThread.start();
  }
  
  @CalledByNative
  private void stop()
  {
    AudioRecordThread localAudioRecordThread = this.jdField_a_of_type_OrgChromiumMediaAudioRecordInput$AudioRecordThread;
    if (localAudioRecordThread == null) {
      return;
    }
    localAudioRecordThread.a();
    this.jdField_a_of_type_OrgChromiumMediaAudioRecordInput$AudioRecordThread = null;
  }
  
  private class AudioRecordThread
    extends Thread
  {
    private volatile boolean jdField_a_of_type_Boolean = true;
    
    private AudioRecordThread() {}
    
    public void a()
    {
      this.jdField_a_of_type_Boolean = false;
      while (isAlive()) {
        try
        {
          join();
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;) {}
        }
      }
    }
    
    public void run()
    {
      Process.setThreadPriority(-19);
      try
      {
        AudioRecordInput.a(AudioRecordInput.this).startRecording();
        while (this.jdField_a_of_type_Boolean)
        {
          int i = AudioRecordInput.a(AudioRecordInput.this).read(AudioRecordInput.a(AudioRecordInput.this), AudioRecordInput.a(AudioRecordInput.this).capacity());
          if (i > 0)
          {
            AudioRecordInput localAudioRecordInput = AudioRecordInput.this;
            AudioRecordInput.a(localAudioRecordInput, AudioRecordInput.a(localAudioRecordInput), i, 100);
          }
          else
          {
            Log.e("cr.media", "read failed: %d", new Object[] { Integer.valueOf(i) });
            if (i == -3) {
              this.jdField_a_of_type_Boolean = false;
            }
          }
        }
        try
        {
          AudioRecordInput.a(AudioRecordInput.this).stop();
          return;
        }
        catch (IllegalStateException localIllegalStateException1)
        {
          Log.e("cr.media", "stop failed", new Object[] { localIllegalStateException1 });
          return;
        }
        return;
      }
      catch (IllegalStateException localIllegalStateException2)
      {
        Log.e("cr.media", "startRecording failed", new Object[] { localIllegalStateException2 });
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\AudioRecordInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */