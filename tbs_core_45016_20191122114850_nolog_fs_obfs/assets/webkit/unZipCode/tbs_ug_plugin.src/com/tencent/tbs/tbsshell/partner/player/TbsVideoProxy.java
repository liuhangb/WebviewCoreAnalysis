package com.tencent.tbs.tbsshell.partner.player;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.mtt.video.browser.export.player.IVideoWebViewProxy;
import com.tencent.mtt.video.export.FeatureSupport;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.IH5VideoPlayer;
import com.tencent.mtt.video.export.ITBSVideoPlayer;
import com.tencent.mtt.video.export.PlayerEnv;
import com.tencent.mtt.video.export.VideoProxyDefault;
import dalvik.system.DexClassLoader;

public class TbsVideoProxy
  extends VideoProxyDefault
{
  private Context mAppCtx;
  private int mCurrentBufferPercentage;
  private PlayerEnv mPlayerEnv;
  private FrameLayout mViewContainer;
  
  public TbsVideoProxy(Context paramContext, DexClassLoader paramDexClassLoader)
  {
    this.mAppCtx = paramContext;
  }
  
  public boolean canPagePlay()
  {
    return false;
  }
  
  public int getBufferPercentage()
  {
    return this.mCurrentBufferPercentage;
  }
  
  public Context getContext()
  {
    PlayerEnv localPlayerEnv = this.mPlayerEnv;
    if (localPlayerEnv != null) {
      return localPlayerEnv.getContext();
    }
    return null;
  }
  
  public IVideoWebViewProxy getH5VideoWebViewProxy()
  {
    return new TBSVideoWebViewProxy(this.mAppCtx);
  }
  
  public int getProxyType()
  {
    PlayerEnv localPlayerEnv = this.mPlayerEnv;
    if ((localPlayerEnv != null) && ((localPlayerEnv instanceof TbsWhitePlayerEnv))) {
      return 4;
    }
    return super.getProxyType();
  }
  
  public IH5VideoPlayer getVideoPlayer()
  {
    return this.mPlayer;
  }
  
  public void invokeMiscMethod(String paramString, Bundle paramBundle) {}
  
  public boolean isPlaying()
  {
    return super.isVideoPlaying();
  }
  
  public void onActivity(Activity paramActivity, int paramInt)
  {
    PlayerEnv localPlayerEnv = this.mPlayerEnv;
    if (localPlayerEnv != null) {
      localPlayerEnv.onActivity(paramActivity, paramInt);
    }
  }
  
  public void onAttachVideoView(View paramView, int paramInt1, int paramInt2)
  {
    if ((this.mViewContainer != null) && (paramView.getParent() == null))
    {
      FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1);
      this.mViewContainer.addView(paramView, localLayoutParams);
    }
  }
  
  public void onError(int paramInt1, int paramInt2) {}
  
  public void onPaused() {}
  
  public void onPlayed() {}
  
  public void onUserStateChanged() {}
  
  public void pause()
  {
    dispatchPause(1);
  }
  
  public boolean play(Bundle paramBundle, FrameLayout paramFrameLayout, Object paramObject)
  {
    this.mViewContainer = paramFrameLayout;
    this.mVideoInfo = new H5VideoInfo();
    this.mVideoInfo.mWebUrl = paramBundle.getString("webUrl");
    this.mVideoInfo.mWebTitle = paramBundle.getString("title");
    this.mVideoInfo.mVideoUrl = paramBundle.getString("videoUrl");
    this.mVideoInfo.mFromWhere = 2;
    this.mVideoInfo.mPostion = paramBundle.getInt("position", -1);
    this.mVideoInfo.mScreenMode = paramBundle.getInt("screenMode", 105);
    this.mVideoInfo.mSnifferReffer = paramBundle.getString("referer");
    int i = paramBundle.getInt("callMode");
    paramBundle = new FeatureSupport();
    if (i == 1)
    {
      this.mPlayerEnv = new TbsBlackPlayerEnv(this.mAppCtx);
      paramBundle.addFeatureFlag(128L);
      paramBundle.addFeatureFlag(256L);
    }
    else if (i == 2)
    {
      this.mPlayerEnv = new TbsWhitePlayerEnv(this.mAppCtx, this);
    }
    paramBundle.addFeatureFlag(1024L);
    this.mPlayer = TbsVideoManager.getInstance().createVideoPlayer(this.mAppCtx, this, this.mVideoInfo, paramBundle, this.mPlayerEnv);
    if (this.mPlayer != null)
    {
      this.mPlayer.play(this.mVideoInfo, 1);
      return true;
    }
    return false;
  }
  
  public void resume()
  {
    dispatchPlay(1);
  }
  
  public void seekTo(int paramInt)
  {
    dispatchSeek(paramInt, 0);
  }
  
  public void setVideoPlayer(IH5VideoPlayer paramIH5VideoPlayer)
  {
    super.setVideoPlayer(paramIH5VideoPlayer);
    if (paramIH5VideoPlayer == null)
    {
      this.mPlayerEnv = null;
      this.mViewContainer = null;
    }
  }
  
  public void stopPlayback()
  {
    if ((this.mPlayer instanceof ITBSVideoPlayer)) {
      ((ITBSVideoPlayer)this.mPlayer).doExitPlay(false);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\player\TbsVideoProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */