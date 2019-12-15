package org.chromium.tencent.media;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.media.MediaPlayerListener;
import org.chromium.tencent.video.IMediaPlayer;
import org.chromium.tencent.video.IMediaPlayer.IMediaPlayerListener;

@JNINamespace("media")
class TencentMediaPlayerListener
  extends MediaPlayerListener
  implements IMediaPlayer.IMediaPlayerListener
{
  private static final int MEDIA_ERROR_NETWORK = 5;
  private static final String TAG = "TencentMediaPlayerListener";
  
  private TencentMediaPlayerListener(long paramLong)
  {
    super(paramLong);
  }
  
  @CalledByNative
  private static TencentMediaPlayerListener create(long paramLong, TencentMediaPlayerBridge paramTencentMediaPlayerBridge)
  {
    TencentMediaPlayerListener localTencentMediaPlayerListener = new TencentMediaPlayerListener(paramLong);
    if (paramTencentMediaPlayerBridge != null) {
      paramTencentMediaPlayerBridge.setMediaPLayerListener(localTencentMediaPlayerListener);
    }
    return localTencentMediaPlayerListener;
  }
  
  private native void nativeOnVideoStartShowing(long paramLong);
  
  public void onBufferingUpdate(IMediaPlayer paramIMediaPlayer, int paramInt)
  {
    nativeOnBufferingUpdate(this.mNativeMediaPlayerListener, paramInt);
  }
  
  public void onCompletion(IMediaPlayer paramIMediaPlayer)
  {
    nativeOnPlaybackComplete(this.mNativeMediaPlayerListener);
  }
  
  public boolean onError(IMediaPlayer paramIMediaPlayer, int paramInt1, int paramInt2)
  {
    int i = 3;
    if (paramInt1 != 1)
    {
      if (paramInt1 != 100)
      {
        if (paramInt1 != 200)
        {
          if (paramInt1 != 100000) {
            paramInt1 = i;
          } else {
            paramInt1 = 5;
          }
        }
        else {
          paramInt1 = 2;
        }
      }
      else {
        paramInt1 = 4;
      }
    }
    else if (paramInt2 != 64529)
    {
      paramInt1 = i;
      if (paramInt2 != -110) {
        paramInt1 = 0;
      }
    }
    else
    {
      paramInt1 = 1;
    }
    nativeOnMediaError(this.mNativeMediaPlayerListener, paramInt1);
    return true;
  }
  
  public void onPrepared(IMediaPlayer paramIMediaPlayer)
  {
    nativeOnMediaPrepared(this.mNativeMediaPlayerListener);
  }
  
  public void onSeekComplete(IMediaPlayer paramIMediaPlayer)
  {
    nativeOnSeekComplete(this.mNativeMediaPlayerListener);
  }
  
  public void onVideoSizeChanged(IMediaPlayer paramIMediaPlayer, int paramInt1, int paramInt2)
  {
    nativeOnVideoSizeChanged(this.mNativeMediaPlayerListener, paramInt1, paramInt2);
  }
  
  public void onVideoStartShowing(IMediaPlayer paramIMediaPlayer)
  {
    nativeOnVideoStartShowing(this.mNativeMediaPlayerListener);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\media\TencentMediaPlayerListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */