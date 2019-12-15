package com.tencent.mtt.video.export;

import android.content.Context;

public abstract interface IVideoManager
{
  public abstract IH5VideoPlayer createVideoPlayer(Context paramContext, VideoProxyDefault paramVideoProxyDefault, H5VideoInfo paramH5VideoInfo, FeatureSupport paramFeatureSupport, PlayerEnv paramPlayerEnv);
  
  public abstract void setContext(Context paramContext);
  
  public abstract void setVideoHost(VideoHost paramVideoHost);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\IVideoManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */