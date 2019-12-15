package com.tencent.smtt.webkit.h5video;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.tencent.mtt.video.browser.export.player.ui.IVideoExtraAbilityControllerHolder;
import com.tencent.mtt.video.export.FeatureSupport;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.IH5VideoPlayer;
import com.tencent.mtt.video.export.IVideoPlayerCreateListener;
import com.tencent.mtt.video.export.IVideoPlayerHelper;
import com.tencent.mtt.video.export.IX5VideoPlayer;
import com.tencent.mtt.video.export.PlayerEnv;
import com.tencent.mtt.video.export.VideoProxyDefault;

public class a
  implements IVideoPlayerCreateListener, IX5VideoPlayer
{
  private int jdField_a_of_type_Int = -1;
  private H5VideoInfo jdField_a_of_type_ComTencentMttVideoExportH5VideoInfo = null;
  private IX5VideoPlayer jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
  private VideoProxyDefault jdField_a_of_type_ComTencentMttVideoExportVideoProxyDefault = null;
  private boolean jdField_a_of_type_Boolean = false;
  private H5VideoInfo b = null;
  
  public a(Context paramContext, VideoProxyDefault paramVideoProxyDefault, H5VideoInfo paramH5VideoInfo, FeatureSupport paramFeatureSupport, PlayerEnv paramPlayerEnv, IVideoPlayerHelper paramIVideoPlayerHelper)
  {
    this.jdField_a_of_type_ComTencentMttVideoExportVideoProxyDefault = paramVideoProxyDefault;
    if (paramIVideoPlayerHelper != null) {
      paramIVideoPlayerHelper.createVideoPlayerAsync(null, paramVideoProxyDefault, null, paramFeatureSupport, paramPlayerEnv, this);
    }
  }
  
  public void active()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.active();
    }
  }
  
  public void deactive()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.deactive();
    }
  }
  
  public void doDestory() {}
  
  public void doExitPlay(boolean paramBoolean)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.doExitPlay(paramBoolean);
    }
  }
  
  public void fillVideoInfo(H5VideoInfo paramH5VideoInfo)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.fillVideoInfo(paramH5VideoInfo);
    }
  }
  
  public int getCurrentPosition()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      return localIX5VideoPlayer.getCurrentPosition();
    }
    return 0;
  }
  
  public String getData(int paramInt)
  {
    return null;
  }
  
  public int getDuration()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      return localIX5VideoPlayer.getDuration();
    }
    return 0;
  }
  
  public int getScreenMode()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      return localIX5VideoPlayer.getScreenMode();
    }
    return 0;
  }
  
  public int getVideoHeight()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      return localIX5VideoPlayer.getVideoHeight();
    }
    return 0;
  }
  
  public String getVideoUrl()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      return localIX5VideoPlayer.getVideoUrl();
    }
    return null;
  }
  
  public View getVideoView()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      return localIX5VideoPlayer.getVideoView();
    }
    return null;
  }
  
  public int getVideoWidth()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      return localIX5VideoPlayer.getVideoWidth();
    }
    return 0;
  }
  
  public boolean handlePluginTag(String paramString1, String paramString2, boolean paramBoolean, String paramString3)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      return localIX5VideoPlayer.handlePluginTag(paramString1, paramString2, paramBoolean, paramString3);
    }
    return false;
  }
  
  public boolean isVideoPlaying()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      return localIX5VideoPlayer.isVideoPlaying();
    }
    return false;
  }
  
  public void onAppExit()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.onAppExit();
    }
  }
  
  public void onCustomViewHidden()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.onCustomViewHidden();
    }
  }
  
  public Object onMiscCallBack(String paramString, Bundle paramBundle)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      return localIX5VideoPlayer.onMiscCallBack(paramString, paramBundle);
    }
    return null;
  }
  
  public void onSurfaceTextureCreated(Object paramObject)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.onSurfaceTextureCreated(paramObject);
    }
  }
  
  public void onVideoPlayerCreated(IH5VideoPlayer paramIH5VideoPlayer)
  {
    if (paramIH5VideoPlayer != null) {
      this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer = ((IX5VideoPlayer)paramIH5VideoPlayer);
    } else {
      this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer = new NullVideoPlayer(this.jdField_a_of_type_ComTencentMttVideoExportVideoProxyDefault.getContext());
    }
    if (this.jdField_a_of_type_Boolean)
    {
      this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer.unmountProxy();
      return;
    }
    if (this.b != null)
    {
      paramIH5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportH5VideoInfo;
      if (paramIH5VideoPlayer != null)
      {
        this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer.preload(paramIH5VideoPlayer);
        this.jdField_a_of_type_ComTencentMttVideoExportH5VideoInfo = null;
      }
      this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer.play(this.b, this.jdField_a_of_type_Int);
      this.b = null;
      this.jdField_a_of_type_ComTencentMttVideoExportH5VideoInfo = null;
      this.jdField_a_of_type_Int = -1;
      return;
    }
    paramIH5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportH5VideoInfo;
    if (paramIH5VideoPlayer != null)
    {
      this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer.preload(paramIH5VideoPlayer);
      this.jdField_a_of_type_ComTencentMttVideoExportH5VideoInfo = null;
    }
  }
  
  public void onVideoSizeChanged(int paramInt1, int paramInt2)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.onVideoSizeChanged(paramInt1, paramInt2);
    }
  }
  
  public void pause(int paramInt)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.pause(paramInt);
    }
  }
  
  public void play(H5VideoInfo paramH5VideoInfo, int paramInt)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null)
    {
      localIX5VideoPlayer.play(paramH5VideoInfo, paramInt);
      return;
    }
    this.jdField_a_of_type_Int = paramInt;
    this.b = paramH5VideoInfo;
  }
  
  public void preload(H5VideoInfo paramH5VideoInfo)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null)
    {
      localIX5VideoPlayer.preload(paramH5VideoInfo);
      return;
    }
    this.jdField_a_of_type_ComTencentMttVideoExportH5VideoInfo = paramH5VideoInfo;
  }
  
  public boolean renderRelease()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      return localIX5VideoPlayer.renderRelease();
    }
    return false;
  }
  
  public void seek(int paramInt)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.seek(paramInt);
    }
  }
  
  public void setExtraAbilityHolder(IVideoExtraAbilityControllerHolder paramIVideoExtraAbilityControllerHolder)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.setExtraAbilityHolder(paramIVideoExtraAbilityControllerHolder);
    }
  }
  
  public void setPlaybackRate(double paramDouble)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.setPlaybackRate(paramDouble);
    }
  }
  
  public void setVideoProxy(VideoProxyDefault paramVideoProxyDefault) {}
  
  public void setVolume(float paramFloat1, float paramFloat2)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.setVolume(paramFloat1, paramFloat2);
    }
  }
  
  public void switchScreen(int paramInt)
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null) {
      localIX5VideoPlayer.switchScreen(paramInt);
    }
  }
  
  public void unmountProxy()
  {
    IX5VideoPlayer localIX5VideoPlayer = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    if (localIX5VideoPlayer != null)
    {
      localIX5VideoPlayer.unmountProxy();
      return;
    }
    this.jdField_a_of_type_Boolean = true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\h5video\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */