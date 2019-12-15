package org.chromium.tencent;

import android.graphics.Bitmap;

public abstract interface ITencentMediaPlayerBridge
{
  public abstract void createSurfaceTexture();
  
  public abstract void evaluateJavascript(String paramString);
  
  public abstract void exitFullsceenByMediaPlayer();
  
  public abstract void onHaveVideoData();
  
  public abstract void onMediaPlayerEnterFullScreen();
  
  public abstract void onMediaPlayerExitFullScreen();
  
  public abstract void onNoVideoData();
  
  public abstract void onSnapshotReady(Bitmap paramBitmap);
  
  public abstract void onVideoRenderModeChanged();
  
  public abstract void pauseByMediaPlayer(int paramInt, boolean paramBoolean);
  
  public abstract void playByMediaPlayer();
  
  public abstract void releaseSurfaceTexture();
  
  public abstract void seekByMediaPlayer(int paramInt1, int paramInt2);
  
  public abstract void videoUrlRedirect(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\ITencentMediaPlayerBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */