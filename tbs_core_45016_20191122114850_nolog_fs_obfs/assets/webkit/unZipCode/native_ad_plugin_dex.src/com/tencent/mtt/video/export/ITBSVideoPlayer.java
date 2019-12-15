package com.tencent.mtt.video.export;

import com.tencent.mtt.video.browser.export.player.ui.IH5VideoQbAbilityControllerHolder;

public abstract interface ITBSVideoPlayer
  extends IX5VideoPlayer
{
  public abstract int getDuration();
  
  public abstract void setQbAbilityHolder(IH5VideoQbAbilityControllerHolder paramIH5VideoQbAbilityControllerHolder);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\ITBSVideoPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */