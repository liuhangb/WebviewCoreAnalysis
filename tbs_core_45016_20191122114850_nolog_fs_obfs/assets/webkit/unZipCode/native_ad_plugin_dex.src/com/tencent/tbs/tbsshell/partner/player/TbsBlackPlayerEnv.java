package com.tencent.tbs.tbsshell.partner.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.video.export.IPlayerEnvListener;
import com.tencent.mtt.video.export.PlayerEnv;

public class TbsBlackPlayerEnv
  extends PlayerEnv
{
  protected Context mApplicationContext;
  
  public TbsBlackPlayerEnv(Context paramContext)
  {
    super(paramContext);
    this.mApplicationContext = paramContext.getApplicationContext();
  }
  
  public int getPlayerEnvType()
  {
    return 5;
  }
  
  public boolean handleBackPress()
  {
    if ((this.mContext instanceof Activity)) {
      ((Activity)this.mContext).finish();
    }
    return true;
  }
  
  public boolean handleEnterLiteWnd()
  {
    reqMoveTaskBackground();
    return true;
  }
  
  public void onActivity(Activity paramActivity, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onActivity state = ");
    localStringBuilder.append(paramInt);
    LogUtils.d("TbsBlackPlayerEnv", localStringBuilder.toString());
    if ((paramInt == 1) || (paramInt == 4)) {
      this.mMainState = 1;
    }
    if (paramInt == 2) {
      this.mMainState = 0;
    }
    if (this.mPlayerEnvListener != null)
    {
      if (this.mContext == paramActivity)
      {
        if (paramInt == 3) {
          this.mPlayerEnvListener.onPause(paramActivity);
        }
        if (paramInt == 4)
        {
          this.mPlayerEnvListener.onDestroy(paramActivity);
          this.mContext = null;
        }
      }
      if (paramInt == 2)
      {
        this.mContext = paramActivity;
        this.mPlayerEnvListener.onResume(paramActivity);
      }
    }
  }
  
  public void reqMoveTaskBackground()
  {
    if ((this.mContext instanceof Activity)) {
      ((Activity)this.mContext).finish();
    }
  }
  
  public boolean reqMoveTaskForeground()
  {
    Intent localIntent = new Intent("com.tencent.smtt.tbs.video.PLAY");
    localIntent.addFlags(268435456);
    localIntent.setPackage(this.mApplicationContext.getPackageName());
    this.mApplicationContext.startActivity(localIntent);
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\player\TbsBlackPlayerEnv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */