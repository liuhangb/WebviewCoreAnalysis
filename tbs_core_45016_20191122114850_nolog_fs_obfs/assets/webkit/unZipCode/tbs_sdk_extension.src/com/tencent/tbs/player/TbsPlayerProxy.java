package com.tencent.tbs.player;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;
import dalvik.system.DexClassLoader;

public class TbsPlayerProxy
{
  private com.tencent.tbs.tbsshell.partner.player.TbsPlayerProxy mTbsPlayerProxy;
  
  public TbsPlayerProxy(Context paramContext, DexClassLoader paramDexClassLoader)
  {
    this.mTbsPlayerProxy = new com.tencent.tbs.tbsshell.partner.player.TbsPlayerProxy(paramContext, paramDexClassLoader);
  }
  
  public boolean isPlaying()
  {
    return this.mTbsPlayerProxy.isPlaying();
  }
  
  public void onActivity(Activity paramActivity, int paramInt)
  {
    this.mTbsPlayerProxy.onActivity(paramActivity, paramInt);
  }
  
  public void onUserStateChanged()
  {
    this.mTbsPlayerProxy.onUserStateChanged();
  }
  
  public void pause()
  {
    this.mTbsPlayerProxy.pause();
  }
  
  public boolean play(Bundle paramBundle, FrameLayout paramFrameLayout)
  {
    return this.mTbsPlayerProxy.play(paramBundle, paramFrameLayout);
  }
  
  public boolean play(Bundle paramBundle, FrameLayout paramFrameLayout, Object paramObject)
  {
    return this.mTbsPlayerProxy.play(paramBundle, paramFrameLayout, paramObject);
  }
  
  public void resume()
  {
    this.mTbsPlayerProxy.resume();
  }
  
  public void seekTo(int paramInt)
  {
    this.mTbsPlayerProxy.seekTo(paramInt);
  }
  
  public void stopPlayback()
  {
    this.mTbsPlayerProxy.stopPlayback();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\player\TbsPlayerProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */