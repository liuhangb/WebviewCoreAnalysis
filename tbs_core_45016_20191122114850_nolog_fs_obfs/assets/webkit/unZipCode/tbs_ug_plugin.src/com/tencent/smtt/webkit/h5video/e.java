package com.tencent.smtt.webkit.h5video;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.tencent.mtt.video.export.IMSEMediaPlayer;
import com.tencent.mtt.video.export.IMSEMediaPlayer.IListener;
import java.io.IOException;
import java.util.Map;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.media.MSEMediaPlayerBridge;
import org.chromium.tencent.media.MSEMediaPlayerBridge.IListener;

@JNINamespace("media")
public class e
  implements IMSEMediaPlayer
{
  private Handler jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper());
  private IMSEMediaPlayer.IListener jdField_a_of_type_ComTencentMttVideoExportIMSEMediaPlayer$IListener;
  private final MSEMediaPlayerBridge jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge;
  
  public e(MSEMediaPlayerBridge paramMSEMediaPlayerBridge)
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge = paramMSEMediaPlayerBridge;
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.setListener(new MSEMediaPlayerBridge.IListener()
    {
      public void onBufferingUpdate(int paramAnonymousInt)
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onBufferingUpdate(e.this, paramAnonymousInt);
        }
      }
      
      public void onCacheStatusInfo(int paramAnonymousInt, String paramAnonymousString, Bundle paramAnonymousBundle)
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onCacheStatusInfo(paramAnonymousInt, paramAnonymousString, paramAnonymousBundle);
        }
      }
      
      public void onCompletion()
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onCompletion(e.this);
        }
      }
      
      public void onDepInfo(String paramAnonymousString)
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onDepInfo(e.this, paramAnonymousString);
        }
      }
      
      public boolean onError(int paramAnonymousInt1, int paramAnonymousInt2, Throwable paramAnonymousThrowable)
      {
        if (e.a(e.this) != null) {
          return e.a(e.this).onError(e.this, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousThrowable);
        }
        return false;
      }
      
      public void onHaveVideoData()
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onHaveVideoData(e.this);
        }
      }
      
      public boolean onInfo(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (e.a(e.this) != null) {
          return e.a(e.this).onInfo(e.this, paramAnonymousInt1, paramAnonymousInt2);
        }
        return false;
      }
      
      public void onMediaPlayerCreated()
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onMediaPlayerCreated();
        }
      }
      
      public void onNoVideoData()
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onNoVideoData(e.this);
        }
      }
      
      public void onPrepared()
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onPrepared(e.this);
        }
      }
      
      public void onSeekComplete()
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onSeekComplete(e.this);
        }
      }
      
      public void onTimedText(String paramAnonymousString)
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onTimedText(e.this, paramAnonymousString);
        }
      }
      
      public void onVideoSizeChanged(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onVideoSizeChanged(e.this, paramAnonymousInt1, paramAnonymousInt2);
        }
      }
      
      public void onVideoStartShowing()
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onVideoStartShowing(e.this);
        }
      }
    });
  }
  
  public byte[] getByteData(int paramInt)
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getByteData(paramInt);
  }
  
  public long getCacheReadPosition()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getCacheReadPosition();
  }
  
  public long getConnTime()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getConnTime();
  }
  
  public int getCurAudioTrackIdxWrap()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getCurAudioTrackIdxWrap();
  }
  
  public int getCurrentPosition()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getCurrentPosition();
  }
  
  public int getCurrentSpeed()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getCurrentSpeed();
  }
  
  public String getData(int paramInt)
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getData(paramInt);
  }
  
  public long getDownloadCostTime()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getDownloadCostTime();
  }
  
  public long getDownloadedSize()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getDownloadedSize();
  }
  
  public int getDuration()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getDuration();
  }
  
  public long getFileSize()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getFileSize();
  }
  
  public int getHttpStatus()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getHttpStatus();
  }
  
  public String getJumpUrl(Object paramObject)
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getJumpUrl(paramObject);
  }
  
  public void getMetadata(boolean paramBoolean1, boolean paramBoolean2) {}
  
  public long getPlayTime()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getPlayTime();
  }
  
  public long getRealTimeDownloadedLen()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getRealTimeDownloadedLen();
  }
  
  public Object[] getValidAudioTrackTitlesWrap()
  {
    return new Object[0];
  }
  
  public int getVideoHeight()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getVideoHeight();
  }
  
  public int getVideoWidth()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.getVideoWidth();
  }
  
  public boolean isLiveStreaming()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.isLiveStreaming();
  }
  
  public boolean isPlaying()
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.isPlaying();
  }
  
  public Object misCallMothed(int paramInt, Bundle paramBundle)
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.misCallMothed(paramInt, paramBundle);
  }
  
  public void onActiveChanged(boolean paramBoolean) {}
  
  public void pause()
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.pause();
  }
  
  public void pauseCacheTask(boolean paramBoolean)
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.pauseCacheTask(paramBoolean);
  }
  
  public void pause_player_and_download() {}
  
  public void prepareAsync()
    throws IllegalStateException
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.prepareAsync();
  }
  
  public void prepareAsyncEx()
    throws IllegalStateException
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.prepareAsyncEx();
  }
  
  public void release()
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.release();
  }
  
  public void reset()
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.reset();
  }
  
  public void resumeCacheTask(boolean paramBoolean)
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.resumeCacheTask(paramBoolean);
  }
  
  public void seekTo(int paramInt)
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.seekTo(paramInt);
  }
  
  public boolean setAudioTrack(int paramInt)
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.setAudioTrack(paramInt);
  }
  
  public void setDataSource(Context paramContext, Uri paramUri, Map<String, String> paramMap)
    throws IOException, IllegalArgumentException, SecurityException, IllegalStateException
  {}
  
  public void setDisplay(SurfaceHolder paramSurfaceHolder) {}
  
  public void setListener(IMSEMediaPlayer.IListener paramIListener)
  {
    this.jdField_a_of_type_ComTencentMttVideoExportIMSEMediaPlayer$IListener = paramIListener;
  }
  
  public void setScreenOnWhilePlaying(boolean paramBoolean)
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.setScreenOnWhilePlaying(paramBoolean);
  }
  
  public void setSurface(Surface paramSurface)
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.setSurface(paramSurface);
  }
  
  public boolean setSwitchStream(int paramInt1, int paramInt2)
  {
    return this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.setSwitchStream(paramInt1, paramInt2);
  }
  
  public void setVideoVolume(float paramFloat1, float paramFloat2)
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.setVideoVolume(paramFloat1, paramFloat2);
  }
  
  public void setWakeMode(Context paramContext, int paramInt) {}
  
  public void set_pause_when_back() {}
  
  public void setupDecode(int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.setupDecode(paramInt1, paramInt2);
  }
  
  public void start()
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.start();
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
    {
      public void run()
      {
        if (e.a(e.this) != null) {
          e.a(e.this).onVideoStartShowing(e.this);
        }
      }
    }, 100L);
  }
  
  public void stop()
  {
    this.jdField_a_of_type_OrgChromiumTencentMediaMSEMediaPlayerBridge.stop();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\h5video\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */