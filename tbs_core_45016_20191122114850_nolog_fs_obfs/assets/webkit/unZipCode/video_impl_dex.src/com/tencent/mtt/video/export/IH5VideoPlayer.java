package com.tencent.mtt.video.export;

import android.view.View;
import com.tencent.mtt.video.browser.export.player.ui.IVideoExtraAbilityControllerHolder;

public abstract interface IH5VideoPlayer
{
  public static final int FAKE_FULL_SCREEN = 107;
  public static final int FULL_SCREEN_AUTO_MODE = 105;
  public static final int FULL_SCREEN_LAND_MODE = 102;
  public static final int FULL_SCREEN_PORTRAIT_MODE = 104;
  public static final int LITE_VIDEO_MODE = 103;
  public static final int PAGE_FLOAT_MODE = 106;
  public static final int PAGE_VIDEO_MODE = 101;
  public static final int REQUEST_TYPE_WONDER_OR_SYS_PLAYER = 1;
  public static final int UA_CHROMIUM = 203;
  public static final int UA_DEFAULT = 200;
  public static final int UA_IPAD = 202;
  public static final int UA_IPHONE = 201;
  public static final int UA_NONE = 204;
  public static final int UNKNOWN_VIDEO_MODE = 100;
  public static final int VIDEO_ACTION_FROM_CORE = 3;
  public static final int VIDEO_ACTION_FROM_DECODER = 4;
  public static final int VIDEO_ACTION_FROM_HOST = 2;
  public static final int VIDEO_ACTION_FROM_USER = 1;
  public static final int VIDEO_FRAME_MODE_ADAPTER = 2;
  public static final int VIDEO_FRAME_MODE_CROP = 4;
  public static final int VIDEO_FRAME_MODE_FULLSCREEN = 3;
  public static final int VIDEO_FRAME_MODE_ORIGINAL = 1;
  
  public abstract int getCurrentPosition();
  
  public abstract int getDuration();
  
  public abstract int getScreenMode();
  
  public abstract int getVideoHeight();
  
  public abstract String getVideoUrl();
  
  public abstract View getVideoView();
  
  public abstract int getVideoWidth();
  
  public abstract boolean isVideoPlaying();
  
  public abstract void onSurfaceTextureCreated(Object paramObject);
  
  public abstract void pause(int paramInt);
  
  public abstract void play(H5VideoInfo paramH5VideoInfo, int paramInt);
  
  public abstract void seek(int paramInt);
  
  public abstract void setExtraAbilityHolder(IVideoExtraAbilityControllerHolder paramIVideoExtraAbilityControllerHolder);
  
  public abstract void setPlaybackRate(double paramDouble);
  
  public abstract void setVideoProxy(VideoProxyDefault paramVideoProxyDefault);
  
  public abstract void setVolume(float paramFloat1, float paramFloat2);
  
  public abstract void switchScreen(int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\IH5VideoPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */