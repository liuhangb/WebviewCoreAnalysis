package org.chromium.tencent.media;

import android.text.TextUtils;
import android.util.Log;
import java.util.StringTokenizer;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.tencent.ContentViewClientExtension;
import org.chromium.tencent.TencentContentViewCore;
import org.chromium.tencent.video.IMediaPlayer;
import org.chromium.tencent.video.SystemMediaPlayer;

@JNINamespace("media")
public class MediaSourceBridge
{
  private static final String TAG = "qbx5media";
  private ContentViewCore mContentViewCore;
  private boolean mIsVideo;
  private MSEMediaPlayerBridge mMSEMediaPlayerBridge;
  private long mNativeMediaSourceBridge;
  private IMediaPlayer mPlayer;
  
  protected MediaSourceBridge(long paramLong, MSEMediaPlayerBridge paramMSEMediaPlayerBridge, ContentViewCore paramContentViewCore, boolean paramBoolean)
  {
    this.mNativeMediaSourceBridge = paramLong;
    this.mMSEMediaPlayerBridge = paramMSEMediaPlayerBridge;
    this.mIsVideo = paramBoolean;
    this.mContentViewCore = paramContentViewCore;
  }
  
  @CalledByNative
  private static MediaSourceBridge create(long paramLong, MSEMediaPlayerBridge paramMSEMediaPlayerBridge, ContentViewCore paramContentViewCore, boolean paramBoolean)
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    return new MediaSourceBridge(paramLong, paramMSEMediaPlayerBridge, paramContentViewCore, paramBoolean);
  }
  
  private native void nativeCreateSurfaceTexture(long paramLong);
  
  private native void nativeEvaluateJavascript(long paramLong, String paramString);
  
  private native void nativeExitFullscreenByMediaPlayer(long paramLong);
  
  private native void nativeOnHaveVideoData(long paramLong);
  
  private native void nativeOnMediaPlayerEnterFullScreen(long paramLong);
  
  private native void nativeOnMediaPlayerExitFullScreen(long paramLong);
  
  private native void nativeOnNoVideoData(long paramLong);
  
  private native void nativeOnVideoRenderModeChanged(long paramLong);
  
  private native void nativeOnVideoUrlRedirect(long paramLong, String paramString);
  
  private native void nativePauseByMediaPlayer(long paramLong, int paramInt, boolean paramBoolean);
  
  private native void nativePlayByMediaPlayer(long paramLong);
  
  private native void nativeReleaseSurfaceTexture(long paramLong);
  
  private native void nativeSeekByMediaPlayer(long paramLong, int paramInt1, int paramInt2);
  
  public void createSurfaceTexture()
  {
    long l = this.mNativeMediaSourceBridge;
    if (l != 0L) {
      nativeCreateSurfaceTexture(l);
    }
  }
  
  @CalledByNative
  protected void destroy()
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    getLocalPlayer().exitFullscreen();
    this.mNativeMediaSourceBridge = 0L;
  }
  
  @CalledByNative
  protected void enterFullscreen()
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    getLocalPlayer().enterFullscreen();
  }
  
  public void evaluateJavascript(String paramString)
  {
    long l = this.mNativeMediaSourceBridge;
    if (l != 0L) {
      nativeEvaluateJavascript(l, paramString);
    }
  }
  
  public void exitFullsceenByMediaPlayer()
  {
    long l = this.mNativeMediaSourceBridge;
    if (l != 0L) {
      nativeExitFullscreenByMediaPlayer(l);
    }
  }
  
  @CalledByNative
  protected void exitFullscreen()
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    getLocalPlayer().exitFullscreen();
  }
  
  protected IMediaPlayer getLocalPlayer()
  {
    if (this.mPlayer == null)
    {
      ContentViewCore localContentViewCore = this.mContentViewCore;
      if ((localContentViewCore != null) && (((TencentContentViewCore)localContentViewCore).getContentViewClientExtension() != null)) {
        this.mPlayer = ((TencentContentViewCore)this.mContentViewCore).getContentViewClientExtension().createMediaSourcePlayer(this);
      } else {
        this.mPlayer = new SystemMediaPlayer();
      }
    }
    return this.mPlayer;
  }
  
  public MSEMediaPlayerBridge getMSEMediaPlayerBrige()
  {
    return this.mMSEMediaPlayerBridge;
  }
  
  @CalledByNative
  protected boolean isAllowRenderingWithinPage()
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    return getLocalPlayer().isAllowRenderingWithinPage();
  }
  
  public void onHaveVideoData()
  {
    nativeOnHaveVideoData(this.mNativeMediaSourceBridge);
  }
  
  public void onMediaPlayerEnterFullScreen()
  {
    long l = this.mNativeMediaSourceBridge;
    if (l != 0L) {
      nativeOnMediaPlayerEnterFullScreen(l);
    }
  }
  
  public void onMediaPlayerExitFullScreen()
  {
    long l = this.mNativeMediaSourceBridge;
    if (l != 0L) {
      nativeOnMediaPlayerExitFullScreen(l);
    }
  }
  
  public void onNoVideoData()
  {
    nativeOnNoVideoData(this.mNativeMediaSourceBridge);
  }
  
  public void onVideoRenderModeChanged()
  {
    nativeOnVideoRenderModeChanged(this.mNativeMediaSourceBridge);
  }
  
  public void pauseByMediaPlayer(int paramInt, boolean paramBoolean)
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    long l = this.mNativeMediaSourceBridge;
    if (l != 0L) {
      nativePauseByMediaPlayer(l, paramInt, paramBoolean);
    }
  }
  
  public void playByMediaPlayer()
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    long l = this.mNativeMediaSourceBridge;
    if (l != 0L) {
      nativePlayByMediaPlayer(l);
    }
  }
  
  @CalledByNative
  protected void release()
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    getLocalPlayer().release();
  }
  
  public void releaseSurfaceTexture()
  {
    long l = this.mNativeMediaSourceBridge;
    if (l != 0L) {
      nativeReleaseSurfaceTexture(l);
    }
  }
  
  public void seekByMediaPlayer(int paramInt1, int paramInt2)
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    MSEMediaPlayerBridge localMSEMediaPlayerBridge = this.mMSEMediaPlayerBridge;
    if (paramInt1 == paramInt2) {
      bool = true;
    } else {
      bool = false;
    }
    localMSEMediaPlayerBridge.seekToEnd(bool);
    long l = this.mNativeMediaSourceBridge;
    if (l != 0L) {
      nativeSeekByMediaPlayer(l, paramInt1, paramInt2);
    }
  }
  
  @CalledByNative
  protected void setIsFixedPositionVideo(boolean paramBoolean)
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    getLocalPlayer().setIsFixedPositionVideo(paramBoolean);
  }
  
  @CalledByNative
  protected void setSurfaceTexture(Object paramObject)
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    getLocalPlayer().setSurfaceTexture(paramObject);
  }
  
  @CalledByNative
  protected void setVideoAttr(String paramString1, String paramString2)
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    if (paramString1.equals("x5-mse-live-streaming"))
    {
      this.mMSEMediaPlayerBridge.setLiveStreaming(true);
      return;
    }
    getLocalPlayer().setVideoAttr(paramString1, paramString2);
  }
  
  @CalledByNative
  protected void setVideoAttrs(String paramString)
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString, "\n");
    while (localStringTokenizer1.hasMoreElements())
    {
      StringTokenizer localStringTokenizer2 = new StringTokenizer(localStringTokenizer1.nextToken(), "=");
      int i = localStringTokenizer2.countTokens();
      String str2 = null;
      String str1;
      if (i >= 1) {
        str1 = localStringTokenizer2.nextToken();
      } else {
        str1 = null;
      }
      if (localStringTokenizer2.countTokens() == 1) {
        str2 = localStringTokenizer2.nextToken();
      }
      try
      {
        if (TextUtils.isEmpty(str1)) {
          continue;
        }
        setVideoAttr(str1, str2);
      }
      catch (Throwable localThrowable)
      {
        for (;;) {}
      }
      if (str1 == null) {
        str1 = "null";
      }
      if (str2 == null) {
        str2 = "null";
      }
      Log.e("qbx5media", String.format("J MediaSourceBridge.setVideoAttr exceptions (this = %s, attrs = %s, key= %s, value = %s)", new Object[] { this, paramString, str1, str2 }));
    }
  }
  
  @CalledByNative
  protected void start()
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    getLocalPlayer().start();
  }
  
  @CalledByNative
  protected void updateVideoPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool = MSEMediaPlayerBridge.DEBUG_VIDEO;
    getLocalPlayer().updateVideoPosition(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void videoUrlRedirect(String paramString)
  {
    nativeOnVideoUrlRedirect(this.mNativeMediaSourceBridge, paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\media\MediaSourceBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */