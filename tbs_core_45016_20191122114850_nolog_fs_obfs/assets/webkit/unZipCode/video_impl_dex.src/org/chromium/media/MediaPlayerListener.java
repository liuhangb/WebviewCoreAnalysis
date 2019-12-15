package org.chromium.media;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("media")
public class MediaPlayerListener
  implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener
{
  protected static final int MEDIA_ERROR_DECODE = 1;
  protected static final int MEDIA_ERROR_FORMAT = 0;
  protected static final int MEDIA_ERROR_INVALID_CODE = 3;
  public static final int MEDIA_ERROR_MALFORMED = -1007;
  protected static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 2;
  protected static final int MEDIA_ERROR_SERVER_DIED = 4;
  public static final int MEDIA_ERROR_TIMED_OUT = -110;
  protected long mNativeMediaPlayerListener;
  
  protected MediaPlayerListener(long paramLong)
  {
    this.mNativeMediaPlayerListener = paramLong;
  }
  
  private static MediaPlayerListener create(long paramLong, MediaPlayerBridge paramMediaPlayerBridge)
  {
    MediaPlayerListener localMediaPlayerListener = new MediaPlayerListener(paramLong);
    if (paramMediaPlayerBridge != null)
    {
      paramMediaPlayerBridge.setOnBufferingUpdateListener(localMediaPlayerListener);
      paramMediaPlayerBridge.setOnCompletionListener(localMediaPlayerListener);
      paramMediaPlayerBridge.setOnErrorListener(localMediaPlayerListener);
      paramMediaPlayerBridge.setOnPreparedListener(localMediaPlayerListener);
      paramMediaPlayerBridge.setOnSeekCompleteListener(localMediaPlayerListener);
      paramMediaPlayerBridge.setOnVideoSizeChangedListener(localMediaPlayerListener);
    }
    return localMediaPlayerListener;
  }
  
  protected native void nativeOnBufferingUpdate(long paramLong, int paramInt);
  
  protected native void nativeOnMediaError(long paramLong, int paramInt);
  
  protected native void nativeOnMediaInterrupted(long paramLong);
  
  protected native void nativeOnMediaPrepared(long paramLong);
  
  protected native void nativeOnPlaybackComplete(long paramLong);
  
  protected native void nativeOnSeekComplete(long paramLong);
  
  protected native void nativeOnVideoSizeChanged(long paramLong, int paramInt1, int paramInt2);
  
  public void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt)
  {
    nativeOnBufferingUpdate(this.mNativeMediaPlayerListener, paramInt);
  }
  
  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
    nativeOnPlaybackComplete(this.mNativeMediaPlayerListener);
  }
  
  public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    int i = 3;
    if (paramInt1 != 1)
    {
      if (paramInt1 != 100)
      {
        if (paramInt1 != 200) {
          paramInt1 = i;
        } else {
          paramInt1 = 2;
        }
      }
      else {
        paramInt1 = 4;
      }
    }
    else if (paramInt2 != 64529)
    {
      paramInt1 = i;
      if (paramInt2 != -110) {
        paramInt1 = 0;
      }
    }
    else
    {
      paramInt1 = 1;
    }
    nativeOnMediaError(this.mNativeMediaPlayerListener, paramInt1);
    return true;
  }
  
  public void onPrepared(MediaPlayer paramMediaPlayer)
  {
    nativeOnMediaPrepared(this.mNativeMediaPlayerListener);
  }
  
  public void onSeekComplete(MediaPlayer paramMediaPlayer)
  {
    nativeOnSeekComplete(this.mNativeMediaPlayerListener);
  }
  
  public void onVideoSizeChanged(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    nativeOnVideoSizeChanged(this.mNativeMediaPlayerListener, paramInt1, paramInt2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\MediaPlayerListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */