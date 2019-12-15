package com.tencent.mtt.video.export;

import android.os.Bundle;

public abstract interface IX5VideoPlayer
  extends IH5VideoPlayer
{
  public abstract void active();
  
  public abstract void deactive();
  
  public abstract void doDestory();
  
  public abstract void doExitPlay(boolean paramBoolean);
  
  public abstract void fillVideoInfo(H5VideoInfo paramH5VideoInfo);
  
  public abstract String getData(int paramInt);
  
  public abstract boolean handlePluginTag(String paramString1, String paramString2, boolean paramBoolean, String paramString3);
  
  public abstract void onAppExit();
  
  public abstract void onCustomViewHidden();
  
  public abstract Object onMiscCallBack(String paramString, Bundle paramBundle);
  
  public abstract void onVideoSizeChanged(int paramInt1, int paramInt2);
  
  public abstract void preload(H5VideoInfo paramH5VideoInfo);
  
  public abstract boolean renderRelease();
  
  public abstract void unmountProxy();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\IX5VideoPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */