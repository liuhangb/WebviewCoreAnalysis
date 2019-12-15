package com.tencent.mtt.video.browser.export.data;

public abstract interface IVideoDataListener
{
  public abstract VideoRequestBoby getReqBody();
  
  public abstract void onReqEpisodesCompleted(boolean paramBoolean, int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\data\IVideoDataListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */