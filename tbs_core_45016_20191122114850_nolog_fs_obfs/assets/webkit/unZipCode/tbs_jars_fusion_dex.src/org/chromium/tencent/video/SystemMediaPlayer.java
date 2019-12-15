package org.chromium.tencent.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.Surface;
import java.io.IOException;
import java.util.HashMap;
import org.chromium.tencent.SmttServiceClientProxy;

public class SystemMediaPlayer
  extends MediaPlayer
  implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener, IMediaPlayer
{
  private static final String LOGTAG = "qbx5media";
  IMediaPlayer.IMediaPlayerListener mListener;
  private String mReferer;
  
  public SystemMediaPlayer()
  {
    this(null);
  }
  
  public SystemMediaPlayer(String paramString)
  {
    setOnBufferingUpdateListener(this);
    setOnCompletionListener(this);
    setOnErrorListener(this);
    setOnPreparedListener(this);
    setOnSeekCompleteListener(this);
    setOnVideoSizeChangedListener(this);
    this.mReferer = paramString;
  }
  
  public void enterFullscreen() {}
  
  public void exitFullscreen() {}
  
  public boolean isAllowRenderingWithinPage()
  {
    return true;
  }
  
  public void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt)
  {
    paramMediaPlayer = this.mListener;
    if (paramMediaPlayer != null) {
      paramMediaPlayer.onBufferingUpdate(this, paramInt);
    }
  }
  
  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
    paramMediaPlayer = this.mListener;
    if (paramMediaPlayer != null) {
      paramMediaPlayer.onCompletion(this);
    }
  }
  
  public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    paramMediaPlayer = this.mListener;
    if (paramMediaPlayer != null) {
      return paramMediaPlayer.onError(this, paramInt1, paramInt2);
    }
    return false;
  }
  
  public void onPrepared(MediaPlayer paramMediaPlayer)
  {
    paramMediaPlayer = this.mListener;
    if (paramMediaPlayer != null) {
      paramMediaPlayer.onPrepared(this);
    }
  }
  
  public void onSeekComplete(MediaPlayer paramMediaPlayer)
  {
    paramMediaPlayer = this.mListener;
    if (paramMediaPlayer != null) {
      paramMediaPlayer.onSeekComplete(this);
    }
  }
  
  public void onVideoSizeChanged(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    paramMediaPlayer = this.mListener;
    if (paramMediaPlayer != null) {
      paramMediaPlayer.onVideoSizeChanged(this, paramInt1, paramInt2);
    }
  }
  
  public void preload(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {}
  
  public void reset(String paramString) {}
  
  public void setDataSource(Context paramContext, Uri paramUri, HashMap<String, String> paramHashMap)
    throws IOException
  {
    paramUri = Uri.parse(SmttServiceClientProxy.getInstance().getProxyURL(paramUri.toString()));
    if ((paramHashMap != null) && (!TextUtils.isEmpty(this.mReferer))) {
      paramHashMap.put("Referer", this.mReferer);
    }
    if (Build.VERSION.SDK_INT >= 14)
    {
      super.setDataSource(paramContext, paramUri, paramHashMap);
      return;
    }
    super.setDataSource(paramUri.toString());
  }
  
  public void setIsFixedPositionVideo(boolean paramBoolean) {}
  
  public void setIsVRVideo(boolean paramBoolean) {}
  
  public void setMediaPlayerListener(IMediaPlayer.IMediaPlayerListener paramIMediaPlayerListener)
  {
    this.mListener = paramIMediaPlayerListener;
  }
  
  public void setOrignalSrc(String paramString) {}
  
  public void setPlayType(int paramInt) {}
  
  public void setPlaybackRate(double paramDouble) {}
  
  public void setSurface(Surface paramSurface)
  {
    if (Build.VERSION.SDK_INT >= 14) {
      super.setSurface(paramSurface);
    }
  }
  
  public void setSurfaceTexture(Object paramObject) {}
  
  public void setVideoAttr(String paramString1, String paramString2) {}
  
  public void setVideoAttrs(String paramString) {}
  
  public boolean shouldOverrideStandardPlay(boolean paramBoolean)
  {
    return false;
  }
  
  public void updateVideoPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\video\SystemMediaPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */