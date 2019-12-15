package com.tencent.mtt.video.browser.export.player.ui;

import android.view.View;

public abstract interface IFloatAdvProvider
{
  public static final int SHOW_POS_BELOW_VIDEO = 2;
  public static final int SHOW_POS_BOTTOM_FIXED = 1;
  
  public abstract int getBarShowPosType();
  
  public abstract View getFloatAdvView();
  
  public abstract int getFloatBarHeight();
  
  public abstract void setFloatAdvController(IFloatAdvController paramIFloatAdvController);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\ui\IFloatAdvProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */