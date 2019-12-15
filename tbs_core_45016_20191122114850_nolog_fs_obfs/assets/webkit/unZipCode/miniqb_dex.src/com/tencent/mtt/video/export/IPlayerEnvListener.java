package com.tencent.mtt.video.export;

import android.app.Activity;

public abstract interface IPlayerEnvListener
{
  public abstract void onDestroy(Activity paramActivity);
  
  public abstract void onPause(Activity paramActivity);
  
  public abstract void onResume(Activity paramActivity);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\IPlayerEnvListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */