package com.tencent.mtt.video.browser.export.player;

import com.tencent.mtt.video.browser.export.data.H5VideoEpisodeInfo;
import com.tencent.mtt.video.export.H5VideoInfo;

public abstract interface ILocalFileLister
{
  public abstract boolean fetchNextLocalInfo();
  
  public abstract void findEpisodeInfoForLocalFile(String paramString);
  
  public abstract H5VideoEpisodeInfo getLocalFileToEpisodeInfo();
  
  public abstract H5VideoInfo getNextVideoInfo();
  
  public abstract boolean isLocalFile();
  
  public abstract void reset();
  
  public abstract void setListener(ILocalFileListerListener paramILocalFileListerListener);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\ILocalFileLister.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */