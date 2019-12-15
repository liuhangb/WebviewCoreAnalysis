package org.chromium.media;

import android.annotation.SuppressLint;
import android.media.AudioTrack;
import android.os.Build.VERSION;
import java.nio.ByteBuffer;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("media")
class AudioTrackOutputStream
{
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private AudioTrack jdField_a_of_type_AndroidMediaAudioTrack;
  private ByteBuffer jdField_a_of_type_JavaNioByteBuffer;
  private Callback jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$Callback;
  private WorkerThread jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$WorkerThread;
  private int jdField_b_of_type_Int;
  private long jdField_b_of_type_Long;
  private ByteBuffer jdField_b_of_type_JavaNioByteBuffer;
  private int jdField_c_of_type_Int;
  private long jdField_c_of_type_Long;
  
  private AudioTrackOutputStream(Callback paramCallback)
  {
    this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$Callback = paramCallback;
    if (this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$Callback != null) {
      return;
    }
    this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$Callback = new Callback()
    {
      public AudioTrack createAudioTrack(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6)
      {
        return new AudioTrack(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousInt5, paramAnonymousInt6);
      }
      
      public long getAddress(ByteBuffer paramAnonymousByteBuffer)
      {
        AudioTrackOutputStream localAudioTrackOutputStream = AudioTrackOutputStream.this;
        return AudioTrackOutputStream.a(localAudioTrackOutputStream, AudioTrackOutputStream.a(localAudioTrackOutputStream), paramAnonymousByteBuffer);
      }
      
      public int getMinBufferSize(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        return AudioTrack.getMinBufferSize(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
      }
      
      public void onError()
      {
        AudioTrackOutputStream localAudioTrackOutputStream = AudioTrackOutputStream.this;
        AudioTrackOutputStream.a(localAudioTrackOutputStream, AudioTrackOutputStream.a(localAudioTrackOutputStream));
      }
      
      public AudioTrackOutputStream.AudioBufferInfo onMoreData(ByteBuffer paramAnonymousByteBuffer, long paramAnonymousLong)
      {
        AudioTrackOutputStream localAudioTrackOutputStream = AudioTrackOutputStream.this;
        return AudioTrackOutputStream.a(localAudioTrackOutputStream, AudioTrackOutputStream.a(localAudioTrackOutputStream), paramAnonymousByteBuffer, paramAnonymousLong);
      }
    };
  }
  
  private int a()
  {
    if (this.jdField_c_of_type_Int == 0) {
      return 0;
    }
    int i = b();
    if (i < 0)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("AudioTrack.write() failed. Error:");
      localStringBuilder.append(i);
      Log.e("AudioTrackOutput", localStringBuilder.toString(), new Object[0]);
      this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$Callback.onError();
      return i;
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_c_of_type_Int < i)) {
      throw new AssertionError();
    }
    this.jdField_c_of_type_Int -= i;
    return this.jdField_c_of_type_Int;
  }
  
  private int a(int paramInt)
  {
    if (paramInt != 4)
    {
      if (paramInt != 6)
      {
        if (paramInt != 8)
        {
          switch (paramInt)
          {
          default: 
            return 1;
          case 2: 
            return 12;
          }
          return 4;
        }
        if (Build.VERSION.SDK_INT >= 23) {
          return 6396;
        }
        return 1020;
      }
      return 252;
    }
    return 204;
  }
  
  private ByteBuffer a(int paramInt1, int paramInt2)
  {
    int i = paramInt2 - 1;
    ByteBuffer localByteBuffer = ByteBuffer.allocateDirect(paramInt1 + i);
    paramInt2 = paramInt2 - (int)(this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$Callback.getAddress(localByteBuffer) & i) & i;
    localByteBuffer.position(paramInt2);
    localByteBuffer.limit(paramInt2 + paramInt1);
    return localByteBuffer.slice();
  }
  
  private void a()
  {
    boolean bool = jdField_a_of_type_Boolean;
    long l1 = 0L;
    if ((!bool) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    int i = this.jdField_a_of_type_AndroidMediaAudioTrack.getPlaybackHeadPosition();
    this.jdField_b_of_type_Long += i - this.jdField_b_of_type_Int;
    this.jdField_b_of_type_Int = i;
    long l2 = this.jdField_c_of_type_Long - this.jdField_b_of_type_Long;
    if (l2 >= 0L) {
      l1 = l2;
    }
    AudioBufferInfo localAudioBufferInfo = this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$Callback.onMoreData(this.jdField_a_of_type_JavaNioByteBuffer.duplicate(), l1);
    if (localAudioBufferInfo != null)
    {
      if (localAudioBufferInfo.b() <= 0) {
        return;
      }
      this.jdField_c_of_type_Long += localAudioBufferInfo.a();
      this.jdField_b_of_type_JavaNioByteBuffer = this.jdField_a_of_type_JavaNioByteBuffer.asReadOnlyBuffer();
      this.jdField_c_of_type_Int = localAudioBufferInfo.b();
      return;
    }
  }
  
  @SuppressLint({"NewApi"})
  private int b()
  {
    return this.jdField_a_of_type_AndroidMediaAudioTrack.write(this.jdField_b_of_type_JavaNioByteBuffer, this.jdField_c_of_type_Int, 0);
  }
  
  @CalledByNative
  private static AudioTrackOutputStream create()
  {
    return new AudioTrackOutputStream(null);
  }
  
  private native long nativeGetAddress(long paramLong, ByteBuffer paramByteBuffer);
  
  private native void nativeOnError(long paramLong);
  
  private native AudioBufferInfo nativeOnMoreData(long paramLong1, ByteBuffer paramByteBuffer, long paramLong2);
  
  @CalledByNative
  void close()
  {
    AudioTrack localAudioTrack = this.jdField_a_of_type_AndroidMediaAudioTrack;
    if (localAudioTrack != null)
    {
      localAudioTrack.release();
      this.jdField_a_of_type_AndroidMediaAudioTrack = null;
    }
  }
  
  @CalledByNative
  AudioBufferInfo createAudioBufferInfo(int paramInt1, int paramInt2)
  {
    return new AudioBufferInfo(paramInt1, paramInt2);
  }
  
  @CalledByNative
  boolean open(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidMediaAudioTrack != null)) {
      throw new AssertionError();
    }
    paramInt1 = a(paramInt1);
    this.jdField_a_of_type_Int = (this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$Callback.getMinBufferSize(paramInt2, paramInt1, paramInt3) * 3);
    try
    {
      this.jdField_a_of_type_AndroidMediaAudioTrack = this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$Callback.createAudioTrack(3, paramInt2, paramInt1, paramInt3, this.jdField_a_of_type_Int, 1);
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidMediaAudioTrack == null)) {
        throw new AssertionError();
      }
      if (this.jdField_a_of_type_AndroidMediaAudioTrack.getState() == 0)
      {
        Log.e("AudioTrackOutput", "Cannot create AudioTrack", new Object[0]);
        this.jdField_a_of_type_AndroidMediaAudioTrack = null;
        return false;
      }
      this.jdField_b_of_type_Int = 0;
      this.jdField_b_of_type_Long = 0L;
      return true;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Log.e("AudioTrackOutput", "Exception creating AudioTrack for playback: ", new Object[] { localIllegalArgumentException });
    }
    return false;
  }
  
  @CalledByNative
  void setVolume(double paramDouble)
  {
    double d = AudioTrack.getMaxVolume();
    Double.isNaN(d);
    float f = (float)(paramDouble * d);
    this.jdField_a_of_type_AndroidMediaAudioTrack.setStereoVolume(f, f);
  }
  
  @CalledByNative
  void start(long paramLong)
  {
    if (this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$WorkerThread != null) {
      return;
    }
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_c_of_type_Long = 0L;
    this.jdField_a_of_type_JavaNioByteBuffer = a(this.jdField_a_of_type_Int, 16);
    this.jdField_a_of_type_AndroidMediaAudioTrack.play();
    this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$WorkerThread = new WorkerThread();
    this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$WorkerThread.start();
  }
  
  @CalledByNative
  void stop()
  {
    WorkerThread localWorkerThread = this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$WorkerThread;
    if (localWorkerThread != null)
    {
      localWorkerThread.a();
      try
      {
        this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$WorkerThread.interrupt();
        this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$WorkerThread.join();
      }
      catch (InterruptedException localInterruptedException)
      {
        Log.e("AudioTrackOutput", "Exception while waiting for AudioTrack worker thread finished: ", new Object[] { localInterruptedException });
      }
      catch (SecurityException localSecurityException)
      {
        Log.e("AudioTrackOutput", "Exception while waiting for AudioTrack worker thread finished: ", new Object[] { localSecurityException });
      }
      this.jdField_a_of_type_OrgChromiumMediaAudioTrackOutputStream$WorkerThread = null;
    }
    this.jdField_a_of_type_AndroidMediaAudioTrack.pause();
    this.jdField_a_of_type_AndroidMediaAudioTrack.flush();
    this.jdField_b_of_type_Int = 0;
    this.jdField_b_of_type_Long = 0L;
    this.jdField_a_of_type_Long = 0L;
  }
  
  static class AudioBufferInfo
  {
    private final int a;
    private final int b;
    
    public AudioBufferInfo(int paramInt1, int paramInt2)
    {
      this.a = paramInt1;
      this.b = paramInt2;
    }
    
    public int a()
    {
      return this.a;
    }
    
    public int b()
    {
      return this.b;
    }
  }
  
  static abstract interface Callback
  {
    public abstract AudioTrack createAudioTrack(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
    
    public abstract long getAddress(ByteBuffer paramByteBuffer);
    
    public abstract int getMinBufferSize(int paramInt1, int paramInt2, int paramInt3);
    
    public abstract void onError();
    
    public abstract AudioTrackOutputStream.AudioBufferInfo onMoreData(ByteBuffer paramByteBuffer, long paramLong);
  }
  
  class WorkerThread
    extends Thread
  {
    private volatile boolean jdField_a_of_type_Boolean = false;
    
    WorkerThread() {}
    
    public void a()
    {
      this.jdField_a_of_type_Boolean = true;
    }
    
    public void run()
    {
      while (!this.jdField_a_of_type_Boolean)
      {
        int i = AudioTrackOutputStream.a(AudioTrackOutputStream.this);
        if (i < 0) {
          return;
        }
        if (i <= 0) {
          AudioTrackOutputStream.a(AudioTrackOutputStream.this);
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\AudioTrackOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */