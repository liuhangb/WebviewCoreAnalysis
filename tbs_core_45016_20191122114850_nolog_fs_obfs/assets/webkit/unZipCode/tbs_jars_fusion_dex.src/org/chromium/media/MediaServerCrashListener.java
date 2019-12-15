package org.chromium.media;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("media")
@MainDex
public class MediaServerCrashListener
  implements MediaPlayer.OnErrorListener
{
  private long jdField_a_of_type_Long = -1L;
  private MediaPlayer jdField_a_of_type_AndroidMediaMediaPlayer;
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  private long b;
  
  private MediaServerCrashListener(long paramLong)
  {
    this.b = paramLong;
  }
  
  @CalledByNative
  private static MediaServerCrashListener create(long paramLong)
  {
    return new MediaServerCrashListener(paramLong);
  }
  
  private native void nativeOnMediaServerCrashDetected(long paramLong, boolean paramBoolean);
  
  public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    if (paramInt1 == 100)
    {
      nativeOnMediaServerCrashDetected(this.b, true);
      releaseWatchdog();
    }
    return true;
  }
  
  @CalledByNative
  public void releaseWatchdog()
  {
    MediaPlayer localMediaPlayer = this.jdField_a_of_type_AndroidMediaMediaPlayer;
    if (localMediaPlayer == null) {
      return;
    }
    localMediaPlayer.release();
    this.jdField_a_of_type_AndroidMediaMediaPlayer = null;
  }
  
  @CalledByNative
  public boolean startListening()
  {
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\MediaServerCrashListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */