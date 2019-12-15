package com.tencent.mtt.video.export;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import java.io.IOException;
import java.util.Map;

public abstract interface IMSEMediaPlayer
{
  public abstract byte[] getByteData(int paramInt);
  
  public abstract long getCacheReadPosition();
  
  public abstract long getConnTime();
  
  public abstract int getCurAudioTrackIdxWrap();
  
  public abstract int getCurrentPosition();
  
  public abstract int getCurrentSpeed();
  
  public abstract String getData(int paramInt);
  
  public abstract long getDownloadCostTime();
  
  public abstract long getDownloadedSize();
  
  public abstract int getDuration();
  
  public abstract long getFileSize();
  
  public abstract int getHttpStatus();
  
  public abstract String getJumpUrl(Object paramObject);
  
  public abstract void getMetadata(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract long getPlayTime();
  
  public abstract long getRealTimeDownloadedLen();
  
  public abstract Object[] getValidAudioTrackTitlesWrap();
  
  public abstract int getVideoHeight();
  
  public abstract int getVideoWidth();
  
  public abstract boolean isLiveStreaming();
  
  public abstract boolean isPlaying();
  
  public abstract Object misCallMothed(int paramInt, Bundle paramBundle);
  
  public abstract void onActiveChanged(boolean paramBoolean);
  
  public abstract void pause();
  
  public abstract void pauseCacheTask(boolean paramBoolean);
  
  public abstract void pause_player_and_download();
  
  public abstract void prepareAsync()
    throws IllegalStateException;
  
  public abstract void prepareAsyncEx()
    throws IllegalStateException;
  
  public abstract void release();
  
  public abstract void reset();
  
  public abstract void resumeCacheTask(boolean paramBoolean);
  
  public abstract void seekTo(int paramInt);
  
  public abstract boolean setAudioTrack(int paramInt);
  
  public abstract void setDataSource(Context paramContext, Uri paramUri, Map<String, String> paramMap)
    throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;
  
  public abstract void setDisplay(SurfaceHolder paramSurfaceHolder);
  
  public abstract void setListener(IListener paramIListener);
  
  public abstract void setScreenOnWhilePlaying(boolean paramBoolean);
  
  public abstract void setSurface(Surface paramSurface);
  
  public abstract boolean setSwitchStream(int paramInt1, int paramInt2);
  
  public abstract void setVideoVolume(float paramFloat1, float paramFloat2);
  
  public abstract void setWakeMode(Context paramContext, int paramInt);
  
  public abstract void set_pause_when_back();
  
  public abstract void setupDecode(int paramInt1, int paramInt2);
  
  public abstract void start();
  
  public abstract void stop();
  
  public static abstract interface IListener
  {
    public abstract void onBufferingUpdate(IMSEMediaPlayer paramIMSEMediaPlayer, int paramInt);
    
    public abstract void onCacheStatusInfo(int paramInt, String paramString, Bundle paramBundle);
    
    public abstract void onCompletion(IMSEMediaPlayer paramIMSEMediaPlayer);
    
    public abstract void onDepInfo(IMSEMediaPlayer paramIMSEMediaPlayer, String paramString);
    
    public abstract boolean onError(IMSEMediaPlayer paramIMSEMediaPlayer, int paramInt1, int paramInt2, Throwable paramThrowable);
    
    public abstract void onHaveVideoData(IMSEMediaPlayer paramIMSEMediaPlayer);
    
    public abstract boolean onInfo(IMSEMediaPlayer paramIMSEMediaPlayer, int paramInt1, int paramInt2);
    
    public abstract void onMediaPlayerCreated();
    
    public abstract void onNoVideoData(IMSEMediaPlayer paramIMSEMediaPlayer);
    
    public abstract void onPrepared(IMSEMediaPlayer paramIMSEMediaPlayer);
    
    public abstract void onSeekComplete(IMSEMediaPlayer paramIMSEMediaPlayer);
    
    public abstract void onTimedText(IMSEMediaPlayer paramIMSEMediaPlayer, String paramString);
    
    public abstract void onVideoSizeChanged(IMSEMediaPlayer paramIMSEMediaPlayer, int paramInt1, int paramInt2);
    
    public abstract void onVideoStartShowing(IMSEMediaPlayer paramIMSEMediaPlayer);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\IMSEMediaPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */