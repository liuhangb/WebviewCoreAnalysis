package com.tencent.mtt.video.browser.export.player;

public abstract interface IMusicEvent
{
  public abstract void onDownloadProgress(int paramInt);
  
  public abstract void onDownloadStart(int paramInt);
  
  public abstract void onDownloadWillStart();
  
  public abstract void onMusicBufferingUpdate(int paramInt);
  
  public abstract void onMusicCompletion();
  
  public abstract void onMusicError(int paramInt1, int paramInt2);
  
  public abstract void onMusicPrepare();
  
  public abstract void onMusicSeekCompletion(int paramInt);
  
  public abstract void onNeedDownloadWonderPlayer(String paramString1, String paramString2, boolean paramBoolean);
  
  public abstract void onPrepareFinished(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\IMusicEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */