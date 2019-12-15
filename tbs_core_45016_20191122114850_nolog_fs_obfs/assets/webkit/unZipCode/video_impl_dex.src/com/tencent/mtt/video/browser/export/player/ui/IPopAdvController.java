package com.tencent.mtt.video.browser.export.player.ui;

public abstract interface IPopAdvController
{
  public abstract boolean isAdvShowing(IPopAdvProvider paramIPopAdvProvider);
  
  public abstract void reqHideAdv(IPopAdvProvider paramIPopAdvProvider);
  
  public abstract void reqShowAdv(IPopAdvProvider paramIPopAdvProvider);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\ui\IPopAdvController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */