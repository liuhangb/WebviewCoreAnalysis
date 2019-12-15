package com.tencent.mtt.video.browser.export.player.ui;

import com.tencent.mtt.video.browser.export.data.H5VideoEpisodeInfo;
import com.tencent.mtt.video.browser.export.player.IH5VideoEpisoder;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.VideoProxyDefault;

public abstract interface IH5VideoMediaController
{
  public static final int PLAYER_STATE_PLAYING = 3;
  
  public abstract boolean canDownloadVideo();
  
  public abstract void dispatchPlay(int paramInt);
  
  public abstract void doExitPlay(boolean paramBoolean);
  
  public abstract void exitPlayerAndJmmpPage(String paramString);
  
  public abstract int getCurrentClassifyId();
  
  public abstract long getCurrentLiveId();
  
  public abstract int getCurrentPosition();
  
  public abstract VideoProxyDefault getCurrentProxy();
  
  public abstract H5VideoEpisodeInfo getEpisodeInfo();
  
  public abstract IH5VideoEpisoder getEpisoder();
  
  public abstract Boolean getIsCompletioned();
  
  public abstract H5VideoEpisodeInfo getLocalFileToEpisodeInfo();
  
  public abstract int getPlayerScreenMode();
  
  public abstract String getVideoFromSp();
  
  public abstract String getVideoTitle();
  
  public abstract int getVideoType();
  
  public abstract String getVideoUrl();
  
  public abstract String getWebUrl();
  
  public abstract void hidePlayer();
  
  public abstract boolean isDownloadBlackSite();
  
  public abstract boolean isLiveStreaming();
  
  public abstract boolean isLocalVideo();
  
  public abstract boolean isPlayerInMyVideo();
  
  public abstract Boolean isPlaying();
  
  public abstract boolean isShowEpisodesButton();
  
  public abstract boolean isVideoUrlChanged();
  
  public abstract void makeText(String paramString);
  
  public abstract void onCallRingPause();
  
  public abstract void onMediaAbilityControllerShow(VideoMediaAbilityControllerBase paramVideoMediaAbilityControllerBase);
  
  public abstract void onResponse(boolean paramBoolean, Object paramObject, VideoMediaAbilityControllerBase paramVideoMediaAbilityControllerBase);
  
  public abstract void play(H5VideoInfo paramH5VideoInfo, int paramInt);
  
  public abstract void playEpisode(H5VideoEpisodeInfo paramH5VideoEpisodeInfo, boolean paramBoolean);
  
  public abstract void playLive(H5VideoInfo paramH5VideoInfo);
  
  public abstract void registerController(VideoMediaAbilityControllerBase paramVideoMediaAbilityControllerBase);
  
  public abstract void setControllerBtnStatus(int paramInt1, int paramInt2);
  
  public abstract void setCurrentClassifyId(int paramInt);
  
  public abstract void setCurrentLiveID(long paramLong);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\ui\IH5VideoMediaController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */