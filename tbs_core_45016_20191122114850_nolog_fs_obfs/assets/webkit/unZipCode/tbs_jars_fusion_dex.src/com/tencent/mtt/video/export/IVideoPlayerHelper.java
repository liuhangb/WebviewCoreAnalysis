package com.tencent.mtt.video.export;

import android.content.Context;

public abstract interface IVideoPlayerHelper
{
  public abstract IH5VideoPlayer createVideoPlayer(Context paramContext, VideoProxyDefault paramVideoProxyDefault, H5VideoInfo paramH5VideoInfo, FeatureSupport paramFeatureSupport, PlayerEnv paramPlayerEnv);
  
  public abstract void createVideoPlayerAsync(Context paramContext, VideoProxyDefault paramVideoProxyDefault, H5VideoInfo paramH5VideoInfo, FeatureSupport paramFeatureSupport, PlayerEnv paramPlayerEnv, IVideoPlayerCreateListener paramIVideoPlayerCreateListener);
  
  public abstract void play(H5VideoInfo paramH5VideoInfo);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\IVideoPlayerHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */