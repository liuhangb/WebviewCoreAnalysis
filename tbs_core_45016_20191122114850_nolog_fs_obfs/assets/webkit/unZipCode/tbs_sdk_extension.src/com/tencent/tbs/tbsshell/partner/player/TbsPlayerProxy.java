package com.tencent.tbs.tbsshell.partner.player;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;
import dalvik.system.DexClassLoader;

public class TbsPlayerProxy
{
  private Context mAppCtx;
  private DexClassLoader mClassLoader = null;
  private TbsVideoProxy mTbsVideoProxy;
  
  public TbsPlayerProxy(Context paramContext, DexClassLoader paramDexClassLoader)
  {
    this.mAppCtx = paramContext;
    this.mClassLoader = paramDexClassLoader;
  }
  
  public boolean isPlaying()
  {
    TbsVideoProxy localTbsVideoProxy = this.mTbsVideoProxy;
    if (localTbsVideoProxy != null) {
      return localTbsVideoProxy.isVideoPlaying();
    }
    return false;
  }
  
  public void onActivity(Activity paramActivity, int paramInt)
  {
    TbsVideoProxy localTbsVideoProxy = this.mTbsVideoProxy;
    if (localTbsVideoProxy != null) {
      localTbsVideoProxy.onActivity(paramActivity, paramInt);
    }
  }
  
  public void onUserStateChanged()
  {
    TbsVideoProxy localTbsVideoProxy = this.mTbsVideoProxy;
    if (localTbsVideoProxy != null) {
      localTbsVideoProxy.onUserStateChanged();
    }
  }
  
  public void pause()
  {
    TbsVideoProxy localTbsVideoProxy = this.mTbsVideoProxy;
    if (localTbsVideoProxy != null) {
      localTbsVideoProxy.dispatchPause(1);
    }
  }
  
  public boolean play(Bundle paramBundle, FrameLayout paramFrameLayout)
  {
    return play(paramBundle, paramFrameLayout, null);
  }
  
  public boolean play(Bundle paramBundle, FrameLayout paramFrameLayout, Object paramObject)
  {
    this.mTbsVideoProxy = new TbsVideoProxy(this.mAppCtx, this.mClassLoader);
    return this.mTbsVideoProxy.play(paramBundle, paramFrameLayout, paramObject);
  }
  
  public void resume()
  {
    TbsVideoProxy localTbsVideoProxy = this.mTbsVideoProxy;
    if (localTbsVideoProxy != null) {
      localTbsVideoProxy.dispatchPlay(1);
    }
  }
  
  public void seekTo(int paramInt)
  {
    TbsVideoProxy localTbsVideoProxy = this.mTbsVideoProxy;
    if (localTbsVideoProxy != null) {
      localTbsVideoProxy.dispatchSeek(paramInt, 0);
    }
  }
  
  public void stopPlayback()
  {
    TbsVideoProxy localTbsVideoProxy = this.mTbsVideoProxy;
    if (localTbsVideoProxy != null) {
      localTbsVideoProxy.stopPlayback();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\player\TbsPlayerProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */