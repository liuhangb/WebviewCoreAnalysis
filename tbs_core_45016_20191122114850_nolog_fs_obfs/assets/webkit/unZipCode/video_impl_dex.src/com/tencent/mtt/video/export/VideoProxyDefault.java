package com.tencent.mtt.video.export;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import com.tencent.mtt.video.browser.export.player.IVideoWebViewProxy;
import java.util.Map;
import org.apache.http.HttpHost;

public class VideoProxyDefault
{
  public static final int PROXY_TYPE_MTT = 2;
  public static final int PROXY_TYPE_NATIVE = 6;
  public static final int PROXY_TYPE_SERVICE = 3;
  public static final int PROXY_TYPE_TBS_BLACK = 5;
  public static final int PROXY_TYPE_TBS_WHITE = 4;
  public static final int PROXY_TYPE_X5 = 1;
  protected IH5VideoPlayer mPlayer = null;
  protected H5VideoInfo mVideoInfo;
  
  public boolean canPagePlay()
  {
    return false;
  }
  
  public void createSurfaceTexture() {}
  
  public void dispatchPause(int paramInt)
  {
    IH5VideoPlayer localIH5VideoPlayer = this.mPlayer;
    if (localIH5VideoPlayer != null) {
      localIH5VideoPlayer.pause(paramInt);
    }
  }
  
  public void dispatchPlay(int paramInt)
  {
    IH5VideoPlayer localIH5VideoPlayer = this.mPlayer;
    if (localIH5VideoPlayer != null) {
      localIH5VideoPlayer.play(this.mVideoInfo, paramInt);
    }
  }
  
  public void dispatchSeek(int paramInt1, int paramInt2)
  {
    IH5VideoPlayer localIH5VideoPlayer = this.mPlayer;
    if (localIH5VideoPlayer != null) {
      localIH5VideoPlayer.seek(paramInt1);
    }
  }
  
  public void dispatchTouchEvent(MotionEvent paramMotionEvent) {}
  
  public HttpHost getActualQProxy()
  {
    return null;
  }
  
  public Context getContext()
  {
    return null;
  }
  
  public IVideoWebViewProxy getH5VideoWebViewProxy()
  {
    return null;
  }
  
  public int getProxyType()
  {
    return 2;
  }
  
  public int getScreenMode()
  {
    IH5VideoPlayer localIH5VideoPlayer = this.mPlayer;
    if (localIH5VideoPlayer != null) {
      return localIH5VideoPlayer.getScreenMode();
    }
    return 0;
  }
  
  public int getSniffVideoID()
  {
    return 0;
  }
  
  public String getSniffVideoRefer()
  {
    return null;
  }
  
  public Object invokeWebViewClientMiscCallBackMethod(String paramString, Bundle paramBundle)
  {
    return null;
  }
  
  public boolean isActive()
  {
    return true;
  }
  
  public boolean isInPrefetchPage()
  {
    return false;
  }
  
  public boolean isVideoPlaying()
  {
    IH5VideoPlayer localIH5VideoPlayer = this.mPlayer;
    if (localIH5VideoPlayer != null) {
      return localIH5VideoPlayer.isVideoPlaying();
    }
    return false;
  }
  
  public boolean isVisible()
  {
    return false;
  }
  
  public void loadUrl(String paramString, Map<String, String> paramMap) {}
  
  public void onAttachVideoView(View paramView, int paramInt1, int paramInt2) {}
  
  public void onBufferingUpdate(int paramInt) {}
  
  public void onCompletion() {}
  
  public void onError() {}
  
  public void onError(int paramInt1, int paramInt2) {}
  
  public void onError2(int paramInt1, int paramInt2, Throwable paramThrowable) {}
  
  public void onInfo(int paramInt1, int paramInt2) {}
  
  public void onNetworkStateChanged(NetworkState paramNetworkState) {}
  
  public void onPaused() {}
  
  public void onPlayed() {}
  
  public void onPlayerDestroyed(IH5VideoPlayer paramIH5VideoPlayer)
  {
    if (this.mPlayer == paramIH5VideoPlayer) {
      this.mPlayer = null;
    }
  }
  
  public void onPrepared(int paramInt1, int paramInt2, int paramInt3) {}
  
  public boolean onScreenModeChangeBefore(int paramInt1, int paramInt2)
  {
    return false;
  }
  
  public void onScreenModeChanged(int paramInt1, int paramInt2) {}
  
  public void onSeekComplete(int paramInt) {}
  
  public void onSniffFailed(String paramString) {}
  
  public void onTimedText(String paramString) {}
  
  public void onVideoSizeChanged(int paramInt1, int paramInt2) {}
  
  public void onVideoStartShowing() {}
  
  public void onVideoViewMove(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void releaseSurfaceTexture() {}
  
  public void setScreenMode(int paramInt) {}
  
  public void setVideoInfo(H5VideoInfo paramH5VideoInfo)
  {
    this.mVideoInfo = paramH5VideoInfo;
  }
  
  public void setVideoPlayer(IH5VideoPlayer paramIH5VideoPlayer)
  {
    this.mPlayer = paramIH5VideoPlayer;
  }
  
  public int videoCountOnThePage()
  {
    return 0;
  }
  
  public static enum NetworkState
  {
    static
    {
      Loaded = new NetworkState("Loaded", 3);
      FormatError = new NetworkState("FormatError", 4);
      NetworkError = new NetworkState("NetworkError", 5);
    }
    
    private NetworkState() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\VideoProxyDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */