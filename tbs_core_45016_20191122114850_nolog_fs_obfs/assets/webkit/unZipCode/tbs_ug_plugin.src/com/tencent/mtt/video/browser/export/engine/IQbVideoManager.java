package com.tencent.mtt.video.browser.export.engine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.tencent.common.utils.bitmap.QImageParams;
import com.tencent.mtt.video.browser.export.data.IVideoDataManager;
import com.tencent.mtt.video.browser.export.player.IMusicPlayer;
import com.tencent.mtt.video.browser.export.wc.IWonderCacheTaskMgr;
import com.tencent.mtt.video.export.IH5VideoPlayer;
import com.tencent.mtt.video.export.IMTTVideoPlayer;
import com.tencent.mtt.video.export.IVideoManager;
import java.io.IOException;
import java.util.ArrayList;

public abstract interface IQbVideoManager
  extends IVideoManager
{
  public abstract IMusicPlayer createMusicPlayer(Context paramContext);
  
  public abstract void destroyLitePlayers();
  
  public abstract void destroyPlayers();
  
  public abstract void destroyVideoPlayer(IH5VideoPlayer paramIH5VideoPlayer);
  
  public abstract void doOnActivityDestroy(Activity paramActivity);
  
  public abstract void exitFullScreenPlayers(byte paramByte);
  
  public abstract String getCurrentVideoUrl(String paramString);
  
  public abstract IVideoDataManager getDataManager();
  
  public abstract String getExtraInfo();
  
  public abstract Bitmap getFrameAtTime(String paramString, int paramInt, QImageParams paramQImageParams)
    throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;
  
  public abstract Bundle getSettingValues(String paramString);
  
  public abstract ArrayList<IMTTVideoPlayer> getVideoPlayerList();
  
  public abstract long getVideoTotalDuration(String paramString);
  
  public abstract IWonderCacheTaskMgr getWonderCacheMgr();
  
  public abstract boolean hasPlayerActive();
  
  public abstract Object invokeMiscMethod(String paramString, Object paramObject);
  
  public abstract boolean isVideoInFullScreen();
  
  public abstract void onAppExit();
  
  public abstract void onApplicationStop();
  
  public abstract void onChaseVideoChanged(String paramString);
  
  public abstract void onPause(Activity paramActivity);
  
  public abstract void onPushMsgArrived(Object paramObject);
  
  public abstract void onResume(Activity paramActivity);
  
  public abstract void onScreenChange(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void pauseLiteWndVideo();
  
  public abstract void putSettingValues(String paramString, Bundle paramBundle);
  
  public abstract void quitQvodService();
  
  public abstract void shutdown();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\engine\IQbVideoManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */