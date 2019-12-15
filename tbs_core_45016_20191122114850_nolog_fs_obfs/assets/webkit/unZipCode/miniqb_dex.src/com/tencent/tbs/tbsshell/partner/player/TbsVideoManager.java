package com.tencent.tbs.tbsshell.partner.player;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.mtt.video.browser.export.engine.IQbVideoManager;
import com.tencent.mtt.video.browser.export.player.IMusicPlayer;
import com.tencent.mtt.video.browser.export.wc.IWonderCacheTaskMgr;
import com.tencent.mtt.video.export.FeatureSupport;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.IH5VideoPlayer;
import com.tencent.mtt.video.export.IMTTVideoPlayer;
import com.tencent.mtt.video.export.IVideoManager;
import com.tencent.mtt.video.export.PlayerEnv;
import com.tencent.mtt.video.export.VideoEngine;
import com.tencent.mtt.video.export.VideoProxyDefault;
import java.util.ArrayList;
import java.util.Iterator;

public class TbsVideoManager
{
  private static TbsVideoManager sInstance;
  VideoEngine mVideoEngine = null;
  
  public static TbsVideoManager getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new TbsVideoManager();
      }
      TbsVideoManager localTbsVideoManager = sInstance;
      return localTbsVideoManager;
    }
    finally {}
  }
  
  private VideoEngine getVideoEngine()
  {
    return this.mVideoEngine;
  }
  
  private void initVideoEngine(Context paramContext)
  {
    if (this.mVideoEngine == null)
    {
      this.mVideoEngine = VideoEngine.getInstance();
      this.mVideoEngine.setVideoHost(new TBSVideoHost(paramContext.getApplicationContext()));
    }
  }
  
  public static void pauseVideo()
  {
    Object localObject = sInstance;
    if (localObject == null) {
      return;
    }
    localObject = ((TbsVideoManager)localObject).getVideoEngine();
    if (localObject == null) {
      return;
    }
    localObject = ((VideoEngine)localObject).getVideoManager();
    if ((localObject instanceof IQbVideoManager))
    {
      localObject = ((IQbVideoManager)localObject).getVideoPlayerList();
      if (localObject != null)
      {
        localObject = ((ArrayList)localObject).iterator();
        while (((Iterator)localObject).hasNext())
        {
          IMTTVideoPlayer localIMTTVideoPlayer = (IMTTVideoPlayer)((Iterator)localObject).next();
          if ((localIMTTVideoPlayer != null) && (localIMTTVideoPlayer.getScreenMode() != 103)) {
            localIMTTVideoPlayer.doExitPlay(false);
          }
        }
      }
    }
  }
  
  public IMusicPlayer createMusicPlayer(Context paramContext)
  {
    if (paramContext == null) {
      return null;
    }
    initVideoEngine(paramContext);
    IVideoManager localIVideoManager = this.mVideoEngine.getVideoManager();
    if (localIVideoManager != null) {
      return ((IQbVideoManager)localIVideoManager).createMusicPlayer(paramContext);
    }
    return null;
  }
  
  public IH5VideoPlayer createVideoPlayer(Context paramContext, VideoProxyDefault paramVideoProxyDefault, H5VideoInfo paramH5VideoInfo, FeatureSupport paramFeatureSupport, PlayerEnv paramPlayerEnv)
  {
    if (paramContext == null) {
      paramContext = paramVideoProxyDefault.getContext();
    }
    initVideoEngine(paramContext);
    IVideoManager localIVideoManager = this.mVideoEngine.getVideoManager();
    if (localIVideoManager != null) {
      return localIVideoManager.createVideoPlayer(paramContext, paramVideoProxyDefault, paramH5VideoInfo, paramFeatureSupport, paramPlayerEnv);
    }
    return null;
  }
  
  public String getCrashExtraInfo()
  {
    Object localObject = sInstance;
    if (localObject == null) {
      return "";
    }
    localObject = ((TbsVideoManager)localObject).getVideoEngine();
    if (localObject != null)
    {
      localObject = ((VideoEngine)localObject).getVideoManager();
      if ((localObject instanceof IQbVideoManager))
      {
        localObject = ((IQbVideoManager)localObject).getExtraInfo();
        if (!TextUtils.isEmpty((CharSequence)localObject)) {
          return (String)localObject;
        }
      }
    }
    return "";
  }
  
  public String getExtendRule(String paramString)
  {
    Object localObject = this.mVideoEngine;
    if (localObject != null) {
      localObject = ((VideoEngine)localObject).getVideoManager();
    } else {
      localObject = null;
    }
    if ((localObject instanceof IQbVideoManager))
    {
      paramString = ((IQbVideoManager)localObject).invokeMiscMethod("getExtendRule", paramString);
      if ((paramString instanceof String)) {
        return (String)paramString;
      }
    }
    return null;
  }
  
  public IVideoManager getVideoManager(Context paramContext)
  {
    initVideoEngine(paramContext);
    return this.mVideoEngine.getVideoManager();
  }
  
  public IWonderCacheTaskMgr getWonderCacheTaskManager(Context paramContext)
  {
    paramContext = getVideoManager(paramContext);
    if ((paramContext instanceof IQbVideoManager)) {
      return ((IQbVideoManager)paramContext).getWonderCacheMgr();
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\player\TbsVideoManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */