package com.tencent.mtt.video.browser.export.player.ui;

import android.content.Context;

public abstract class VideoMediaAbilityControllerBase
{
  protected Context mContext;
  protected IH5VideoMediaController mMediaController;
  
  public VideoMediaAbilityControllerBase(Context paramContext, IH5VideoMediaController paramIH5VideoMediaController)
  {
    this.mContext = paramContext;
    this.mMediaController = paramIH5VideoMediaController;
    paramIH5VideoMediaController.registerController(this);
  }
  
  public abstract void cancel();
  
  public abstract void destory();
  
  public abstract void onVideoStartShowing();
  
  public void request(Object paramObject)
  {
    this.mMediaController.onMediaAbilityControllerShow(this);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\ui\VideoMediaAbilityControllerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */