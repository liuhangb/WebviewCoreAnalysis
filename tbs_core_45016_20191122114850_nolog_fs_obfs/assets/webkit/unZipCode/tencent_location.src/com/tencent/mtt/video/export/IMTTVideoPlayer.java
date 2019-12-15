package com.tencent.mtt.video.export;

import com.tencent.mtt.video.browser.export.data.H5VideoEpisodeInfo;

public abstract interface IMTTVideoPlayer
  extends IH5VideoPlayer
{
  public abstract void doExitPlay(boolean paramBoolean);
  
  public abstract long getCurrentLiveId();
  
  public abstract H5VideoEpisodeInfo getEpisodeInfo();
  
  public abstract H5VideoEpisodeInfo getLocalFileToEpisodeInfo();
  
  public abstract int getVideoID();
  
  public abstract String getWebUrl();
  
  public abstract boolean hasMoreDataConfirmed();
  
  public abstract boolean isActive();
  
  public abstract boolean isPlayerInMyVideo();
  
  public abstract void playEpisode(H5VideoEpisodeInfo paramH5VideoEpisodeInfo);
  
  public abstract void playLive(H5VideoInfo paramH5VideoInfo);
  
  public abstract void showPlayer();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\IMTTVideoPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */