package org.chromium.tencent.video;

import android.content.Context;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.media.MediaPlayer.TrackInfo;
import android.net.Uri;
import android.view.Surface;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.HashMap;

public abstract interface IMediaPlayer
{
  public static final int MEDIA_ERROR_NETWORK = 100000;
  
  public abstract void enterFullscreen();
  
  public abstract void exitFullscreen();
  
  public abstract int getCurrentPosition();
  
  public abstract int getDuration();
  
  public abstract MediaPlayer.TrackInfo[] getTrackInfo();
  
  public abstract int getVideoHeight();
  
  public abstract int getVideoWidth();
  
  public abstract boolean isAllowRenderingWithinPage();
  
  public abstract boolean isPlaying();
  
  public abstract void pause();
  
  public abstract void preload(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract void prepareAsync();
  
  public abstract void release();
  
  public abstract void reset(String paramString);
  
  public abstract void seekTo(int paramInt);
  
  public abstract void setDataSource(Context paramContext, Uri paramUri)
    throws IOException;
  
  public abstract void setDataSource(Context paramContext, Uri paramUri, HashMap<String, String> paramHashMap)
    throws IOException;
  
  public abstract void setDataSource(FileDescriptor paramFileDescriptor, long paramLong1, long paramLong2)
    throws IOException;
  
  public abstract void setIsFixedPositionVideo(boolean paramBoolean);
  
  public abstract void setIsVRVideo(boolean paramBoolean);
  
  public abstract void setMediaPlayerListener(IMediaPlayerListener paramIMediaPlayerListener);
  
  public abstract void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener paramOnBufferingUpdateListener);
  
  public abstract void setOnCompletionListener(MediaPlayer.OnCompletionListener paramOnCompletionListener);
  
  public abstract void setOnErrorListener(MediaPlayer.OnErrorListener paramOnErrorListener);
  
  public abstract void setOnPreparedListener(MediaPlayer.OnPreparedListener paramOnPreparedListener);
  
  public abstract void setOnSeekCompleteListener(MediaPlayer.OnSeekCompleteListener paramOnSeekCompleteListener);
  
  public abstract void setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener paramOnVideoSizeChangedListener);
  
  public abstract void setOrignalSrc(String paramString);
  
  public abstract void setPlayType(int paramInt);
  
  public abstract void setPlaybackRate(double paramDouble);
  
  public abstract void setSurface(Surface paramSurface);
  
  public abstract void setSurfaceTexture(Object paramObject);
  
  public abstract void setVideoAttr(String paramString1, String paramString2);
  
  public abstract void setVideoAttrs(String paramString);
  
  public abstract void setVolume(float paramFloat1, float paramFloat2);
  
  public abstract boolean shouldOverrideStandardPlay(boolean paramBoolean);
  
  public abstract void start();
  
  public abstract void updateVideoPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public static abstract interface IMediaPlayerListener
  {
    public abstract void onBufferingUpdate(IMediaPlayer paramIMediaPlayer, int paramInt);
    
    public abstract void onCompletion(IMediaPlayer paramIMediaPlayer);
    
    public abstract boolean onError(IMediaPlayer paramIMediaPlayer, int paramInt1, int paramInt2);
    
    public abstract void onPrepared(IMediaPlayer paramIMediaPlayer);
    
    public abstract void onSeekComplete(IMediaPlayer paramIMediaPlayer);
    
    public abstract void onVideoSizeChanged(IMediaPlayer paramIMediaPlayer, int paramInt1, int paramInt2);
    
    public abstract void onVideoStartShowing(IMediaPlayer paramIMediaPlayer);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\video\IMediaPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */