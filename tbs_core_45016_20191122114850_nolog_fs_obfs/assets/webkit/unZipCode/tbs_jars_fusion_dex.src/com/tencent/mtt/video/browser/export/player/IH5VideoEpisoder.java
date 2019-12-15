package com.tencent.mtt.video.browser.export.player;

import com.tencent.mtt.video.browser.export.data.H5VideoEpisodeInfo;

public abstract interface IH5VideoEpisoder
{
  public abstract H5VideoEpisodeInfo getEpisodeInfo();
  
  public abstract boolean hasMoreDataConfirmed();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\IH5VideoEpisoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */