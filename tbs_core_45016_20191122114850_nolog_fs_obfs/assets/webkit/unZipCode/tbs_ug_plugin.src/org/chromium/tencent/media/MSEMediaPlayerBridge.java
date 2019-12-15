package org.chromium.tencent.media;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import java.io.IOException;
import java.util.Map;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("media")
public class MSEMediaPlayerBridge
{
  public static boolean DEBUG_VIDEO = false;
  private static final String TAG = "MSEMediaPlayerBridge";
  private long mDuration;
  private boolean mIsLiveStreaming = false;
  private IListener mListener;
  private long mNativeMSEMediaPlayerBridge;
  private boolean mPrepared;
  private boolean mSeekToEnd = false;
  private int mVideoHeight;
  private int mVideoWidth;
  
  private MSEMediaPlayerBridge(long paramLong)
  {
    boolean bool = DEBUG_VIDEO;
    this.mNativeMSEMediaPlayerBridge = paramLong;
  }
  
  @CalledByNative
  private void Destory(boolean paramBoolean)
  {
    this.mNativeMSEMediaPlayerBridge = 0L;
  }
  
  @CalledByNative
  private void OnPlaybackCompleted(boolean paramBoolean)
  {
    paramBoolean = DEBUG_VIDEO;
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if ((MSEMediaPlayerBridge.this.mPrepared) && (MSEMediaPlayerBridge.this.mListener != null)) {
          MSEMediaPlayerBridge.this.mListener.onCompletion();
        }
      }
    });
  }
  
  @CalledByNative
  private static MSEMediaPlayerBridge create(long paramLong)
  {
    return new MSEMediaPlayerBridge(paramLong);
  }
  
  private native long nativeGetCurrentPosition(long paramLong);
  
  private native boolean nativeIsPlaying(long paramLong);
  
  private native void nativePause(long paramLong);
  
  private native void nativeRelease(long paramLong);
  
  private native void nativeSeek(long paramLong, int paramInt);
  
  private native void nativeSetSurface(long paramLong, Surface paramSurface);
  
  private native void nativeStart(long paramLong);
  
  @CalledByNative
  private void onDurationChanged(long paramLong, int paramInt1, int paramInt2)
  {
    boolean bool = DEBUG_VIDEO;
    if ((!this.mPrepared) && (paramInt1 > 0) && (paramInt2 > 0))
    {
      this.mPrepared = true;
      this.mDuration = paramLong;
      this.mVideoWidth = paramInt1;
      this.mVideoHeight = paramInt2;
      ThreadUtils.runOnUiThread(new Runnable()
      {
        public void run()
        {
          if (MSEMediaPlayerBridge.this.mListener != null) {
            MSEMediaPlayerBridge.this.mListener.onPrepared();
          }
        }
      });
    }
  }
  
  @CalledByNative
  private void onMediaMetadataChanged(long paramLong, int paramInt1, int paramInt2)
  {
    boolean bool = DEBUG_VIDEO;
    if (!this.mPrepared)
    {
      this.mPrepared = true;
      this.mDuration = paramLong;
      this.mVideoWidth = paramInt1;
      this.mVideoHeight = paramInt2;
      ThreadUtils.runOnUiThread(new Runnable()
      {
        public void run()
        {
          if (MSEMediaPlayerBridge.this.mListener != null) {
            MSEMediaPlayerBridge.this.mListener.onPrepared();
          }
        }
      });
    }
  }
  
  public byte[] getByteData(int paramInt)
  {
    return null;
  }
  
  public long getCacheReadPosition()
  {
    return 0L;
  }
  
  public long getConnTime()
  {
    return 0L;
  }
  
  public int getCurAudioTrackIdxWrap()
  {
    return 0;
  }
  
  public int getCurrentPosition()
  {
    boolean bool = DEBUG_VIDEO;
    if (this.mSeekToEnd) {
      return (int)this.mDuration;
    }
    long l2 = this.mNativeMSEMediaPlayerBridge;
    long l1 = 0L;
    if (l2 != 0L)
    {
      l1 = nativeGetCurrentPosition(l2);
      bool = DEBUG_VIDEO;
    }
    return (int)l1;
  }
  
  public int getCurrentSpeed()
  {
    return 0;
  }
  
  public String getData(int paramInt)
  {
    return null;
  }
  
  public long getDownloadCostTime()
  {
    return 0L;
  }
  
  public long getDownloadedSize()
  {
    return 0L;
  }
  
  public int getDuration()
  {
    return (int)this.mDuration;
  }
  
  public long getFileSize()
  {
    return 0L;
  }
  
  public int getHttpStatus()
  {
    return 200;
  }
  
  public String getJumpUrl(Object paramObject)
  {
    return null;
  }
  
  public void getMetadata(boolean paramBoolean1, boolean paramBoolean2)
  {
    paramBoolean1 = DEBUG_VIDEO;
  }
  
  public long getPlayTime()
  {
    return 0L;
  }
  
  public long getRealTimeDownloadedLen()
  {
    return 0L;
  }
  
  public Object[] getValidAudioTrackTitlesWrap()
  {
    return null;
  }
  
  public int getVideoHeight()
  {
    return this.mVideoHeight;
  }
  
  public int getVideoWidth()
  {
    return this.mVideoWidth;
  }
  
  public boolean isLiveStreaming()
  {
    return this.mIsLiveStreaming;
  }
  
  public boolean isPlaying()
  {
    long l = this.mNativeMSEMediaPlayerBridge;
    if (l != 0L) {
      return nativeIsPlaying(l);
    }
    return false;
  }
  
  public Object misCallMothed(int paramInt, Bundle paramBundle)
  {
    boolean bool = DEBUG_VIDEO;
    return null;
  }
  
  public void pause()
  {
    boolean bool = DEBUG_VIDEO;
    this.mSeekToEnd = false;
    long l = this.mNativeMSEMediaPlayerBridge;
    if (l != 0L) {
      nativePause(l);
    }
  }
  
  public void pauseCacheTask(boolean paramBoolean) {}
  
  public void prepareAsync()
    throws IllegalStateException
  {
    boolean bool = DEBUG_VIDEO;
    new Handler(Looper.getMainLooper()).post(new Runnable()
    {
      public void run()
      {
        if ((MSEMediaPlayerBridge.this.mPrepared) && (MSEMediaPlayerBridge.this.mListener != null)) {
          MSEMediaPlayerBridge.this.mListener.onPrepared();
        }
      }
    });
  }
  
  public void prepareAsyncEx()
    throws IllegalStateException
  {
    boolean bool = DEBUG_VIDEO;
  }
  
  public void release()
  {
    boolean bool = DEBUG_VIDEO;
    long l = this.mNativeMSEMediaPlayerBridge;
    if (l != 0L) {
      nativeRelease(l);
    }
  }
  
  public void reset()
  {
    boolean bool = DEBUG_VIDEO;
  }
  
  public void resumeCacheTask(boolean paramBoolean) {}
  
  public void seekTo(int paramInt)
  {
    boolean bool = DEBUG_VIDEO;
    this.mSeekToEnd = false;
    long l = this.mNativeMSEMediaPlayerBridge;
    if (l != 0L) {
      nativeSeek(l, paramInt);
    }
  }
  
  public void seekToEnd(boolean paramBoolean)
  {
    boolean bool = DEBUG_VIDEO;
    if (paramBoolean)
    {
      this.mSeekToEnd = true;
      ThreadUtils.runOnUiThread(new Runnable()
      {
        public void run()
        {
          if ((MSEMediaPlayerBridge.this.mPrepared) && (MSEMediaPlayerBridge.this.mListener != null)) {
            MSEMediaPlayerBridge.this.mListener.onSeekComplete();
          }
        }
      });
      OnPlaybackCompleted(true);
      return;
    }
    this.mSeekToEnd = false;
  }
  
  public boolean setAudioTrack(int paramInt)
  {
    return false;
  }
  
  public void setDataSource(Context paramContext, Uri paramUri, Map<String, String> paramMap)
    throws IOException, IllegalArgumentException, SecurityException, IllegalStateException
  {
    boolean bool = DEBUG_VIDEO;
  }
  
  public void setListener(IListener paramIListener)
  {
    boolean bool = DEBUG_VIDEO;
    this.mListener = paramIListener;
  }
  
  public void setLiveStreaming(boolean paramBoolean)
  {
    this.mIsLiveStreaming = paramBoolean;
  }
  
  public void setScreenOnWhilePlaying(boolean paramBoolean)
  {
    paramBoolean = DEBUG_VIDEO;
  }
  
  public void setSurface(Surface paramSurface)
  {
    boolean bool = DEBUG_VIDEO;
    long l = this.mNativeMSEMediaPlayerBridge;
    if (l != 0L) {
      nativeSetSurface(l, paramSurface);
    }
  }
  
  public boolean setSwitchStream(int paramInt1, int paramInt2)
  {
    return false;
  }
  
  public void setVideoVolume(float paramFloat1, float paramFloat2) {}
  
  public void setWakeMode(Context paramContext, int paramInt)
  {
    boolean bool = DEBUG_VIDEO;
  }
  
  public void setupDecode(int paramInt1, int paramInt2)
  {
    boolean bool = DEBUG_VIDEO;
  }
  
  public void start()
  {
    boolean bool = DEBUG_VIDEO;
    this.mSeekToEnd = false;
    long l = this.mNativeMSEMediaPlayerBridge;
    if (l != 0L) {
      nativeStart(l);
    }
  }
  
  public void stop()
  {
    boolean bool = DEBUG_VIDEO;
    this.mSeekToEnd = false;
  }
  
  public static abstract interface IListener
  {
    public abstract void onBufferingUpdate(int paramInt);
    
    public abstract void onCacheStatusInfo(int paramInt, String paramString, Bundle paramBundle);
    
    public abstract void onCompletion();
    
    public abstract void onDepInfo(String paramString);
    
    public abstract boolean onError(int paramInt1, int paramInt2, Throwable paramThrowable);
    
    public abstract void onHaveVideoData();
    
    public abstract boolean onInfo(int paramInt1, int paramInt2);
    
    public abstract void onMediaPlayerCreated();
    
    public abstract void onNoVideoData();
    
    public abstract void onPrepared();
    
    public abstract void onSeekComplete();
    
    public abstract void onTimedText(String paramString);
    
    public abstract void onVideoSizeChanged(int paramInt1, int paramInt2);
    
    public abstract void onVideoStartShowing();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\media\MSEMediaPlayerBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */