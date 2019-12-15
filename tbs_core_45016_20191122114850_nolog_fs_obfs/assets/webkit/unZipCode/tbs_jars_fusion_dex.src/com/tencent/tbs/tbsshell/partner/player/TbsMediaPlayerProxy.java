package com.tencent.tbs.tbsshell.partner.player;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.mtt.video.export.FeatureSupport;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.IX5VideoPlayer;
import com.tencent.mtt.video.export.PlayerEnv;
import com.tencent.mtt.video.export.VideoProxyDefault;

public class TbsMediaPlayerProxy
  extends VideoProxyDefault
  implements TbsWhitePlayerTimer.TbsWhitePlayerTimerCallback
{
  public static final int DATA_TYPE_BITRATE = 0;
  public static final int DATA_TYPE_CACHEFRAME_CNT = 11;
  public static final int DATA_TYPE_CODING_FORMAT = 2;
  public static final int DATA_TYPE_CONTAINER_FORMAT = 1;
  public static final int DATA_TYPE_DECODER_FRAMES = 8;
  public static final int DATA_TYPE_DECODER_NAME = 10;
  public static final int DATA_TYPE_FRAMERATE = 4;
  public static final int DATA_TYPE_LAST_MINUTE_FPS = 22;
  public static final int DATA_TYPE_LAST_MINUTE_SPEED = 23;
  public static final int DATA_TYPE_META_TITLE = 3;
  public static final int DATA_TYPE_NOVIDEO_TIME = 21;
  public static final int DATA_TYPE_ROTATE_ACTION = 13;
  public static final int DATA_TYPE_SUBTITLE = 6;
  public static final int DATA_TYPE_VIDEO_ROTATE = 5;
  public static final int DATA_TYPE_VIDEO_SAR = 7;
  public static final int DATA_TYPE_VIDEO_SEEKS = 9;
  private Context mAppContext = null;
  private AudioManager mAudioManager = null;
  private TbsMediaPlayerListener mListener = null;
  private TbsWhitePlayerTimer mProgressTimer = null;
  private SurfaceTexture mSurfaceTexture = null;
  private IX5VideoPlayer mX5VideoPlayer = null;
  
  public TbsMediaPlayerProxy(Context paramContext)
  {
    this.mAppContext = paramContext;
    this.mAudioManager = ((AudioManager)this.mAppContext.getSystemService("audio"));
    paramContext = new PlayerEnv(this.mAppContext)
    {
      public int getPlayerEnvType()
      {
        return 3;
      }
    };
    FeatureSupport localFeatureSupport = new FeatureSupport();
    paramContext = (IX5VideoPlayer)TbsVideoManager.getInstance().createVideoPlayer(this.mAppContext, this, null, localFeatureSupport, paramContext);
    if (paramContext != null)
    {
      this.mX5VideoPlayer = paramContext;
      this.mPlayer = this.mX5VideoPlayer;
    }
    if (this.mX5VideoPlayer != null)
    {
      this.mProgressTimer = new TbsWhitePlayerTimer(500L, this);
      return;
    }
    throw new RuntimeException("create x5 video player failure !!!");
  }
  
  public void audio(int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("audio_track_index", paramInt);
    this.mX5VideoPlayer.onMiscCallBack("updateAudioTrack", localBundle);
  }
  
  public boolean canPagePlay()
  {
    return true;
  }
  
  public void close()
  {
    this.mX5VideoPlayer.doDestory();
    this.mProgressTimer.stopTimer();
  }
  
  public void createSurfaceTexture()
  {
    this.mX5VideoPlayer.onSurfaceTextureCreated(this.mSurfaceTexture);
  }
  
  public int getProxyType()
  {
    return 1;
  }
  
  public float getVolume()
  {
    int i = this.mAudioManager.getStreamMaxVolume(3);
    return this.mAudioManager.getStreamVolume(3) * 1.0F / i;
  }
  
  public void onBufferingUpdate(int paramInt)
  {
    TbsMediaPlayerListener localTbsMediaPlayerListener = this.mListener;
    if (localTbsMediaPlayerListener != null) {
      localTbsMediaPlayerListener.onBufferingUpdate(paramInt * 1.0F / 100.0F);
    }
  }
  
  public void onCompletion()
  {
    TbsMediaPlayerListener localTbsMediaPlayerListener = this.mListener;
    if (localTbsMediaPlayerListener != null) {
      localTbsMediaPlayerListener.onPlayerCompleted();
    }
  }
  
  public void onError2(int paramInt1, int paramInt2, Throwable paramThrowable)
  {
    TbsMediaPlayerListener localTbsMediaPlayerListener = this.mListener;
    if (localTbsMediaPlayerListener != null) {
      localTbsMediaPlayerListener.onPlayerError("wonder player error !!!", paramInt1, paramInt2, paramThrowable);
    }
  }
  
  public void onInfo(int paramInt1, int paramInt2)
  {
    TbsMediaPlayerListener localTbsMediaPlayerListener = this.mListener;
    if (localTbsMediaPlayerListener != null) {
      localTbsMediaPlayerListener.onPlayerInfo(paramInt1, paramInt2);
    }
  }
  
  public void onPaused()
  {
    TbsMediaPlayerListener localTbsMediaPlayerListener = this.mListener;
    if (localTbsMediaPlayerListener != null) {
      localTbsMediaPlayerListener.onPlayerPaused();
    }
  }
  
  public void onPlayed()
  {
    TbsMediaPlayerListener localTbsMediaPlayerListener = this.mListener;
    if (localTbsMediaPlayerListener != null) {
      localTbsMediaPlayerListener.onPlayerPlaying();
    }
  }
  
  public void onPrepared(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mListener != null)
    {
      localObject1 = this.mX5VideoPlayer.getData(13);
      localObject2 = this.mX5VideoPlayer.getData(5);
    }
    for (;;)
    {
      try
      {
        if (!TextUtils.isEmpty((CharSequence)localObject1)) {
          i = Integer.parseInt((String)localObject1);
        } else {
          i = 0;
        }
      }
      catch (Exception localException1)
      {
        int i;
        int j;
        Object localObject4;
        Object localObject6;
        Object localObject5;
        continue;
      }
      try
      {
        if (!TextUtils.isEmpty((CharSequence)localObject2)) {
          j = Integer.parseInt((String)localObject2);
        } else {
          j = 0;
        }
      }
      catch (Exception localException2) {}
    }
    i = 0;
    j = 0;
    this.mListener.onPlayerPrepared(paramInt1, paramInt2, paramInt3, i, j);
    paramInt2 = -1;
    localObject4 = null;
    localObject3 = null;
    localObject2 = localObject4;
    try
    {
      localObject6 = this.mX5VideoPlayer.onMiscCallBack("getAudioTrackTitles", null);
      localObject2 = localObject4;
      localObject5 = this.mX5VideoPlayer.onMiscCallBack("getAudioTrackIndex", null);
      localObject1 = localObject3;
      if (localObject6 != null)
      {
        localObject1 = localObject3;
        localObject2 = localObject4;
        if ((localObject6 instanceof String[]))
        {
          localObject2 = localObject4;
          localObject1 = (String[])localObject6;
        }
      }
      paramInt1 = paramInt2;
      localObject3 = localObject1;
      if (localObject5 != null)
      {
        paramInt1 = paramInt2;
        localObject3 = localObject1;
        localObject2 = localObject1;
        if ((localObject5 instanceof Integer))
        {
          localObject2 = localObject1;
          paramInt1 = ((Integer)localObject5).intValue();
          localObject3 = localObject1;
        }
      }
    }
    catch (Exception localException3)
    {
      for (;;)
      {
        paramInt1 = paramInt2;
        localObject3 = localObject2;
      }
    }
    if ((localObject3 != null) && (paramInt1 >= 0))
    {
      this.mListener.onPlayerExtra(TbsMediaPlayerListener.MEDIA_EXTRA_AUDIOTRACK_TITLES, localObject3);
      this.mListener.onPlayerExtra(TbsMediaPlayerListener.MEDIA_EXTRA_AUDIOTRACK_INDEX, Integer.valueOf(paramInt1));
    }
    Object localObject1 = this.mX5VideoPlayer.getData(6);
    try
    {
      if (!TextUtils.isEmpty((CharSequence)localObject1)) {
        paramInt1 = Integer.parseInt((String)localObject1);
      } else {
        paramInt1 = 0;
      }
    }
    catch (Exception localException4)
    {
      for (;;) {}
    }
    paramInt1 = 0;
    this.mListener.onPlayerExtra(TbsMediaPlayerListener.MEDIA_EXTRA_SUBTITLE_COUNT, Integer.valueOf(paramInt1));
    if (paramInt1 > 0) {
      this.mListener.onPlayerExtra(TbsMediaPlayerListener.MEDIA_EXTRA_SUBTITLE_INDEX, Integer.valueOf(0));
    }
  }
  
  public void onSeekComplete(int paramInt)
  {
    TbsMediaPlayerListener localTbsMediaPlayerListener = this.mListener;
    if (localTbsMediaPlayerListener != null) {
      localTbsMediaPlayerListener.onPlayerSeeked(paramInt);
    }
  }
  
  public void onSimpleTimerCall(TbsWhitePlayerTimer paramTbsWhitePlayerTimer)
  {
    paramTbsWhitePlayerTimer = this.mListener;
    if (paramTbsWhitePlayerTimer != null) {
      paramTbsWhitePlayerTimer.onPlayerProgress(this.mX5VideoPlayer.getCurrentPosition());
    }
  }
  
  public void onTimedText(String paramString)
  {
    TbsMediaPlayerListener localTbsMediaPlayerListener = this.mListener;
    if (localTbsMediaPlayerListener != null) {
      localTbsMediaPlayerListener.onPlayerSubtitle(paramString);
    }
  }
  
  public void pause()
  {
    this.mX5VideoPlayer.pause(1);
    this.mProgressTimer.stopTimer();
  }
  
  public void play()
  {
    this.mX5VideoPlayer.play(this.mVideoInfo, 1);
    this.mProgressTimer.startTimer();
  }
  
  public void seek(long paramLong)
  {
    this.mX5VideoPlayer.seek((int)paramLong);
  }
  
  public void setPlayerListener(Object paramObject)
  {
    if (paramObject == null) {
      return;
    }
    this.mListener = new TbsMediaPlayerListener(paramObject);
  }
  
  public void setSurfaceTexture(SurfaceTexture paramSurfaceTexture)
  {
    this.mSurfaceTexture = paramSurfaceTexture;
  }
  
  public void setVolume(float paramFloat)
  {
    int i = (int)(paramFloat * this.mAudioManager.getStreamMaxVolume(3));
    this.mAudioManager.setStreamVolume(3, i, 8);
  }
  
  public void startPlay(String paramString, Bundle paramBundle)
  {
    this.mVideoInfo = new H5VideoInfo();
    this.mVideoInfo.mPostion = -1;
    this.mVideoInfo.mWebUrl = null;
    this.mVideoInfo.mVideoUrl = paramString;
    this.mVideoInfo.mExtraData.putInt("vrType", 0);
    this.mVideoInfo.mExtraData.putInt("vrMode", 0);
    this.mVideoInfo.mExtraData.putBoolean("isVR", false);
    this.mVideoInfo.mExtraData.putBoolean("isHardwareAccelerated", true);
    if (paramBundle != null)
    {
      this.mVideoInfo.mPostion = paramBundle.getInt("time", -1);
      this.mVideoInfo.mSnifferReffer = paramBundle.getString("referer", "");
      this.mVideoInfo.mUA = paramBundle.getString("userAgent", null);
      this.mVideoInfo.mExtraData.putAll(paramBundle);
    }
    this.mVideoInfo.mScreenMode = 101;
    this.mX5VideoPlayer.play(this.mVideoInfo, 1);
    this.mProgressTimer.startTimer();
  }
  
  public void subtitle(int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("subtitle_index", paramInt);
    this.mX5VideoPlayer.onMiscCallBack("updateSubtitle", localBundle);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\player\TbsMediaPlayerProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */