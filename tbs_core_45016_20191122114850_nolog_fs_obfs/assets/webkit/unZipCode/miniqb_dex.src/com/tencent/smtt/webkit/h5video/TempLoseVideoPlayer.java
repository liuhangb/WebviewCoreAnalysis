package com.tencent.smtt.webkit.h5video;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.tencent.mtt.video.browser.export.player.ui.IVideoExtraAbilityControllerHolder;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.IX5VideoPlayer;
import com.tencent.mtt.video.export.VideoProxyDefault;
import com.tencent.smtt.webkit.ui.e.a;

public class TempLoseVideoPlayer
  implements IX5VideoPlayer
{
  private int jdField_a_of_type_Int;
  private Context jdField_a_of_type_AndroidContentContext = null;
  private e.a jdField_a_of_type_ComTencentSmttWebkitUiE$a = null;
  private int b;
  private int c;
  
  public TempLoseVideoPlayer(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  public void a(int paramInt)
  {
    this.c = paramInt;
  }
  
  public void a(int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_Int = paramInt1;
    this.b = paramInt2;
  }
  
  public void active() {}
  
  public void deactive() {}
  
  public void doDestory() {}
  
  public void doExitPlay(boolean paramBoolean) {}
  
  public void fillVideoInfo(H5VideoInfo paramH5VideoInfo) {}
  
  public int getCurrentPosition()
  {
    return 0;
  }
  
  public String getData(int paramInt)
  {
    return null;
  }
  
  public int getDuration()
  {
    return this.c;
  }
  
  public int getScreenMode()
  {
    return 0;
  }
  
  public int getVideoHeight()
  {
    return this.b;
  }
  
  public String getVideoUrl()
  {
    return null;
  }
  
  public View getVideoView()
  {
    return null;
  }
  
  public int getVideoWidth()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public boolean handlePluginTag(String paramString1, String paramString2, boolean paramBoolean, String paramString3)
  {
    return false;
  }
  
  public boolean isVideoPlaying()
  {
    return false;
  }
  
  public void onAppExit() {}
  
  public void onCustomViewHidden() {}
  
  public Object onMiscCallBack(String paramString, Bundle paramBundle)
  {
    return null;
  }
  
  public void onSurfaceTextureCreated(Object paramObject) {}
  
  public void onVideoSizeChanged(int paramInt1, int paramInt2) {}
  
  public void pause(int paramInt) {}
  
  public void play(H5VideoInfo paramH5VideoInfo, int paramInt) {}
  
  public void preload(H5VideoInfo paramH5VideoInfo) {}
  
  public boolean renderRelease()
  {
    return false;
  }
  
  public void seek(int paramInt) {}
  
  public void setExtraAbilityHolder(IVideoExtraAbilityControllerHolder paramIVideoExtraAbilityControllerHolder) {}
  
  public void setPlaybackRate(double paramDouble) {}
  
  public void setVideoProxy(VideoProxyDefault paramVideoProxyDefault) {}
  
  public void setVolume(float paramFloat1, float paramFloat2) {}
  
  public void switchScreen(int paramInt) {}
  
  public void unmountProxy() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\h5video\TempLoseVideoPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */