package com.tencent.mtt.video.browser.export.player.ui;

import android.view.View;

public abstract interface IPopAdvProvider
{
  public abstract PopAdvType getAdvType();
  
  public abstract PopAdvDlgParams getPopAdvDlgParams();
  
  public abstract View getPopAdvView();
  
  public abstract void setPopAdvController(IPopAdvController paramIPopAdvController);
  
  public static enum PopAdvType
  {
    private PopAdvType() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\ui\IPopAdvProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */