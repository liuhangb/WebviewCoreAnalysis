package org.chromium.tencent.media;

import android.graphics.Bitmap;
import android.view.Surface;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.content.browser.ContentViewCoreImpl;
import org.chromium.content_public.browser.WebContents;
import org.chromium.media.MediaPlayerBridge;
import org.chromium.media.MediaPlayerBridge.AllowedOperations;
import org.chromium.media.MediaPlayerBridge.LoadDataUriTask;
import org.chromium.tencent.ContentViewClientExtension;
import org.chromium.tencent.ITencentMediaPlayerBridge;
import org.chromium.tencent.SmttServiceClientProxy;
import org.chromium.tencent.TencentContentViewCore;
import org.chromium.tencent.video.IMediaPlayer;
import org.chromium.tencent.video.IMediaPlayer.IMediaPlayerListener;
import org.chromium.tencent.video.SystemMediaPlayer;

public class TencentMediaPlayerBridge
  extends MediaPlayerBridge
  implements ITencentMediaPlayerBridge
{
  private static final String TAG = "cr.media.tencent";
  private ContentViewCoreImpl mContentViewCore;
  private String mFrameUrl;
  private boolean mIsVideo;
  private boolean mUseAudioFramePlayer;
  
  protected TencentMediaPlayerBridge(long paramLong, ContentViewCoreImpl paramContentViewCoreImpl, boolean paramBoolean1, boolean paramBoolean2, String paramString)
  {
    super(paramLong);
    this.mIsVideo = paramBoolean1;
    this.mContentViewCore = paramContentViewCoreImpl;
    this.mUseAudioFramePlayer = paramBoolean2;
    this.mFrameUrl = paramString;
  }
  
  private boolean canUseRemotePlayer()
  {
    ContentViewCoreImpl localContentViewCoreImpl = this.mContentViewCore;
    if ((localContentViewCoreImpl != null) && (((TencentContentViewCore)localContentViewCoreImpl).getContentViewClientExtension() != null) && (this.mUseAudioFramePlayer) && (!SmttServiceClientProxy.getInstance().shouldUseTbsAudioPlayerWithNoClick(this.mContentViewCore.getWebContents().getVisibleUrl())) && (this.mNativeMediaPlayerBridge != 0L)) {
      playedByUserClicked();
    }
    return true;
  }
  
  @CalledByNative
  private static TencentMediaPlayerBridge create(long paramLong, ContentViewCoreImpl paramContentViewCoreImpl, boolean paramBoolean1, boolean paramBoolean2, String paramString)
  {
    return new TencentMediaPlayerBridge(paramLong, paramContentViewCoreImpl, paramBoolean1, paramBoolean2, paramString);
  }
  
  private boolean playedByUserClicked()
  {
    return nativeGetPlayType(this.mNativeMediaPlayerBridge) == 3;
  }
  
  public void createSurfaceTexture()
  {
    if (this.mNativeMediaPlayerBridge != 0L) {
      nativeCreateSurfaceTexture(this.mNativeMediaPlayerBridge);
    }
  }
  
  protected void destroy()
  {
    if (this.mLoadDataUriTask != null) {
      this.mLoadDataUriTask.a();
    }
    this.mContentViewCore = null;
    super.destroy();
  }
  
  @CalledByNative
  protected void enterFullscreen()
  {
    getLocalPlayer().enterFullscreen();
  }
  
  public void evaluateJavascript(String paramString)
  {
    if (this.mNativeMediaPlayerBridge != 0L) {
      nativeEvaluateJavascript(this.mNativeMediaPlayerBridge, paramString);
    }
  }
  
  public void exitFullsceenByMediaPlayer()
  {
    if (this.mNativeMediaPlayerBridge != 0L) {
      nativeExitFullscreenByMediaPlayer(this.mNativeMediaPlayerBridge);
    }
  }
  
  @CalledByNative
  protected void exitFullscreen()
  {
    getLocalPlayer().exitFullscreen();
  }
  
  protected MediaPlayerBridge.AllowedOperations getAllowedOperations()
  {
    Object localObject1 = getLocalPlayer();
    boolean bool3 = true;
    boolean bool4 = true;
    boolean bool5 = true;
    boolean bool1;
    label265:
    boolean bool2;
    try
    {
      localObject2 = localObject1.getClass().getSuperclass().getDeclaredMethod("getMetadata", new Class[] { Boolean.TYPE, Boolean.TYPE });
      ((Method)localObject2).setAccessible(true);
      localObject1 = ((Method)localObject2).invoke(localObject1, new Object[] { Boolean.valueOf(false), Boolean.valueOf(false) });
      if (localObject1 != null)
      {
        localObject2 = localObject1.getClass();
        Method localMethod1 = ((Class)localObject2).getDeclaredMethod("has", new Class[] { Integer.TYPE });
        Method localMethod2 = ((Class)localObject2).getDeclaredMethod("getBoolean", new Class[] { Integer.TYPE });
        int i = ((Integer)((Class)localObject2).getField("PAUSE_AVAILABLE").get(null)).intValue();
        int j = ((Integer)((Class)localObject2).getField("SEEK_FORWARD_AVAILABLE").get(null)).intValue();
        int k = ((Integer)((Class)localObject2).getField("SEEK_BACKWARD_AVAILABLE").get(null)).intValue();
        localMethod1.setAccessible(true);
        localMethod2.setAccessible(true);
        if (((Boolean)localMethod1.invoke(localObject1, new Object[] { Integer.valueOf(i) })).booleanValue())
        {
          bool1 = ((Boolean)localMethod2.invoke(localObject1, new Object[] { Integer.valueOf(i) })).booleanValue();
          if (!bool1)
          {
            bool1 = false;
            break label265;
          }
        }
        bool1 = true;
        try
        {
          if (((Boolean)localMethod1.invoke(localObject1, new Object[] { Integer.valueOf(j) })).booleanValue())
          {
            bool2 = ((Boolean)localMethod2.invoke(localObject1, new Object[] { Integer.valueOf(j) })).booleanValue();
            if (!bool2)
            {
              bool2 = false;
              break label335;
            }
          }
          bool2 = true;
          label335:
          bool3 = bool5;
          try
          {
            if (((Boolean)localMethod1.invoke(localObject1, new Object[] { Integer.valueOf(k) })).booleanValue())
            {
              bool3 = ((Boolean)localMethod2.invoke(localObject1, new Object[] { Integer.valueOf(k) })).booleanValue();
              if (bool3) {
                bool3 = bool5;
              } else {
                bool3 = false;
              }
            }
            bool4 = bool2;
            bool2 = bool3;
            bool3 = bool4;
          }
          catch (NoSuchFieldException localNoSuchFieldException1)
          {
            break label491;
          }
          catch (IllegalAccessException localIllegalAccessException1)
          {
            break label545;
          }
          catch (InvocationTargetException localInvocationTargetException1)
          {
            break label599;
          }
          catch (NoSuchMethodException localNoSuchMethodException1)
          {
            break label653;
          }
          bool2 = true;
        }
        catch (NoSuchFieldException localNoSuchFieldException2)
        {
          break label488;
        }
        catch (IllegalAccessException localIllegalAccessException2)
        {
          break label542;
        }
        catch (InvocationTargetException localInvocationTargetException2)
        {
          break label596;
        }
        catch (NoSuchMethodException localNoSuchMethodException2) {}
      }
      bool1 = true;
      bool4 = bool3;
      bool3 = bool2;
      bool2 = bool4;
    }
    catch (NoSuchFieldException localNoSuchFieldException3)
    {
      bool1 = true;
      bool2 = true;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Cannot find matching fields in Metadata class: ");
      ((StringBuilder)localObject2).append(localNoSuchFieldException3);
      Log.e("cr.media.tencent", ((StringBuilder)localObject2).toString(), new Object[0]);
      bool3 = bool4;
    }
    catch (IllegalAccessException localIllegalAccessException3)
    {
      bool1 = true;
      bool2 = true;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Cannot access metadata: ");
      ((StringBuilder)localObject2).append(localIllegalAccessException3);
      Log.e("cr.media.tencent", ((StringBuilder)localObject2).toString(), new Object[0]);
      bool3 = bool4;
    }
    catch (InvocationTargetException localInvocationTargetException3)
    {
      bool1 = true;
      bool2 = true;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Cannot invoke MediaPlayer.getMetadata() method: ");
      ((StringBuilder)localObject2).append(localInvocationTargetException3);
      Log.e("cr.media.tencent", ((StringBuilder)localObject2).toString(), new Object[0]);
      bool3 = bool4;
    }
    catch (NoSuchMethodException localNoSuchMethodException3)
    {
      label488:
      label491:
      label542:
      label545:
      label596:
      label599:
      bool1 = true;
      bool2 = true;
      label653:
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Cannot find getMetadata() method: ");
      ((StringBuilder)localObject2).append(localNoSuchMethodException3);
      Log.e("cr.media.tencent", ((StringBuilder)localObject2).toString(), new Object[0]);
      bool3 = bool4;
    }
    return new MediaPlayerBridge.AllowedOperations(bool1, bool2, bool3);
  }
  
  protected IMediaPlayer getLocalPlayer()
  {
    try
    {
      if ((this.mPlayer == null) && (this.mIsVideo) && (this.mContentViewCore != null) && (((TencentContentViewCore)this.mContentViewCore).getContentViewClientExtension() != null)) {
        this.mPlayer = ((TencentContentViewCore)this.mContentViewCore).getContentViewClientExtension().createMediaPlayer(this.mIsVideo, this);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    if (this.mPlayer == null) {
      this.mPlayer = new SystemMediaPlayer(this.mFrameUrl);
    }
    return this.mPlayer;
  }
  
  @CalledByNative
  protected boolean isAllowRenderingWithinPage()
  {
    return getLocalPlayer().isAllowRenderingWithinPage();
  }
  
  public boolean isVideo()
  {
    return this.mIsVideo;
  }
  
  public void onHaveVideoData()
  {
    nativeOnHaveVideoData(this.mNativeMediaPlayerBridge);
  }
  
  public void onInfo(int paramInt1, int paramInt2)
  {
    nativeOnMediaPlayeInfo(this.mNativeMediaPlayerBridge, paramInt1, paramInt2);
  }
  
  public void onMediaInterruptedByRemote()
  {
    nativeOnMediaInterruptedByRemote(this.mNativeMediaPlayerBridge);
  }
  
  public void onMediaPlayerEnterFullScreen()
  {
    if (this.mNativeMediaPlayerBridge != 0L) {
      nativeOnMediaPlayerEnterFullScreen(this.mNativeMediaPlayerBridge);
    }
  }
  
  public void onMediaPlayerExitFullScreen()
  {
    if (this.mNativeMediaPlayerBridge != 0L) {
      nativeOnMediaPlayerExitFullScreen(this.mNativeMediaPlayerBridge);
    }
  }
  
  public void onNoVideoData()
  {
    nativeOnNoVideoData(this.mNativeMediaPlayerBridge);
  }
  
  public void onSnapshotReady(Bitmap paramBitmap)
  {
    if (this.mNativeMediaPlayerBridge != 0L) {
      nativeOnSnapshotReady(this.mNativeMediaPlayerBridge, paramBitmap);
    }
  }
  
  public void onVideoRenderModeChanged()
  {
    nativeOnVideoRenderModeChanged(this.mNativeMediaPlayerBridge);
  }
  
  protected void pause()
  {
    super.pause();
  }
  
  public void pauseByMediaPlayer(int paramInt, boolean paramBoolean)
  {
    if (this.mNativeMediaPlayerBridge != 0L) {
      nativePauseByMediaPlayer(this.mNativeMediaPlayerBridge, paramInt, paramBoolean);
    }
  }
  
  public void playByMediaPlayer()
  {
    if (this.mNativeMediaPlayerBridge != 0L) {
      nativePlayByMediaPlayer(this.mNativeMediaPlayerBridge);
    }
  }
  
  @CalledByNative
  protected void preload(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    getLocalPlayer().preload(paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  public void releaseSurfaceTexture()
  {
    if (this.mNativeMediaPlayerBridge != 0L) {
      nativeReleaseSurfaceTexture(this.mNativeMediaPlayerBridge);
    }
  }
  
  @CalledByNative
  protected void reset(String paramString)
  {
    getLocalPlayer().reset(paramString);
  }
  
  public void seekByMediaPlayer(int paramInt1, int paramInt2)
  {
    if (this.mNativeMediaPlayerBridge != 0L) {
      nativeSeekByMediaPlayer(this.mNativeMediaPlayerBridge, paramInt1, paramInt2);
    }
  }
  
  protected void seekTo(int paramInt)
    throws IllegalStateException
  {
    super.seekTo(paramInt);
  }
  
  protected boolean setDataSource(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    return super.setDataSource(paramString1, paramString2, paramString3, paramBoolean);
  }
  
  @CalledByNative
  protected void setIsFixedPositionVideo(boolean paramBoolean)
  {
    getLocalPlayer().setIsFixedPositionVideo(paramBoolean);
  }
  
  @CalledByNative
  protected void setIsVRVideo(boolean paramBoolean)
  {
    getLocalPlayer().setIsVRVideo(paramBoolean);
  }
  
  protected void setMediaPLayerListener(IMediaPlayer.IMediaPlayerListener paramIMediaPlayerListener)
  {
    getLocalPlayer().setMediaPlayerListener(paramIMediaPlayerListener);
  }
  
  @CalledByNative
  protected void setOrignalSrc(String paramString)
  {
    getLocalPlayer().setOrignalSrc(paramString);
  }
  
  @CalledByNative
  protected void setPlayType(int paramInt)
  {
    getLocalPlayer().setPlayType(paramInt);
  }
  
  @CalledByNative
  protected void setPlaybackRate(double paramDouble)
  {
    getLocalPlayer().setPlaybackRate(paramDouble);
  }
  
  protected void setSurface(Surface paramSurface)
  {
    super.setSurface(paramSurface);
  }
  
  @CalledByNative
  protected void setSurfaceTexture(Object paramObject)
  {
    getLocalPlayer().setSurfaceTexture(paramObject);
  }
  
  @CalledByNative
  protected void setVideoAttr(String paramString1, String paramString2)
  {
    getLocalPlayer().setVideoAttr(paramString1, paramString2);
  }
  
  @CalledByNative
  protected void setVideoAttrs(String paramString)
  {
    getLocalPlayer().setVideoAttrs(paramString);
  }
  
  @CalledByNative
  protected boolean shouldOverrideStandardPlay(boolean paramBoolean)
  {
    return getLocalPlayer().shouldOverrideStandardPlay(paramBoolean);
  }
  
  protected void start()
  {
    try
    {
      super.start();
      return;
    }
    catch (Exception localException) {}
  }
  
  @CalledByNative
  protected void updateVideoPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    getLocalPlayer().updateVideoPosition(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void videoUrlRedirect(String paramString)
  {
    nativeOnVideoUrlRedirect(this.mNativeMediaPlayerBridge, paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\media\TencentMediaPlayerBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */