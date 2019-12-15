package com.tencent.mtt.video.browser.export.player;

public abstract interface IMusicPlayer
{
  public static final int MUSIC_TYPE_DOWNLOAD = 2;
  public static final int MUSIC_TYPE_LOAD = 1;
  
  public abstract int getCurrentPosition();
  
  public abstract int getDuration();
  
  public abstract int getVersion();
  
  public abstract boolean isPlaying();
  
  public abstract void loadOrDownlad(int paramInt);
  
  public abstract void pause();
  
  public abstract void preLoad(boolean paramBoolean);
  
  public abstract void release();
  
  public abstract void seekTo(int paramInt);
  
  public abstract void setEvent(IMusicEvent paramIMusicEvent);
  
  public abstract void setMusicPath(String paramString);
  
  public abstract void start();
  
  public abstract void stop();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\IMusicPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */