package com.tencent.mtt.video.export;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public abstract class PlayerEnv
{
  public static final int ENV_IN_DEFAULT = 0;
  public static final int ENV_IN_QB = 1;
  public static final int ENV_IN_QB_THRDCALL = 2;
  public static final int ENV_IN_TBS_BLACK_CALL = 5;
  public static final int ENV_IN_TBS_LOOP_PLAY_CALL = 7;
  public static final int ENV_IN_TBS_VIP_CALL = 6;
  public static final int ENV_IN_TBS_WHITE_CALL = 4;
  public static final int ENV_IN_X5_SDK = 3;
  public static final int ROTOTE_AUTO_REQUEST = 4;
  public static final int ROTOTE_EXIT = 1;
  public static final int ROTOTE_LANDSCAPE_REQUEST = 2;
  public static final int ROTOTE_NORMAL_LANDSCAPE_REQUEST = 6;
  public static final int ROTOTE_PORTRAIT_REQUEST = 3;
  public static final int ROTOTE_REVERSE_LANDSCAPE_REQUEST = 5;
  public static final int ROTOTE_UNSPECIFIED = 7;
  public static final int STATUS_BAR_EXIT = -1;
  public static final int STATUS_BAR_HIDE = 2;
  public static final int STATUS_BAR_SHOW = 1;
  public static final int TASK_BROWSER_TYPE = 1;
  public static final int TASK_DESKLAUNCH_TYPE = 2;
  public static final int TASK_NONE_TYPE = 4;
  public static final int TASK_NO_ACTIVITY_TYPE = 3;
  private static int sSdkVersion = -1;
  @Deprecated
  public Context mContext = null;
  protected int mCurrentRotateReq = 1;
  protected int mCurrentStatusBarReq = -1;
  protected boolean mIsStatusBarShowBefore;
  protected int mMainState = 1;
  protected int mOriginOrientation = -1000000;
  protected IPlayerEnvListener mPlayerEnvListener;
  
  public PlayerEnv(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private void setOrientation(Activity paramActivity, int paramInt)
  {
    try
    {
      paramActivity.setRequestedOrientation(paramInt);
      return;
    }
    catch (Exception paramActivity) {}
  }
  
  public Context getContext()
  {
    return this.mContext;
  }
  
  public abstract int getPlayerEnvType();
  
  public Activity getVideoMountedActivity()
  {
    Context localContext = this.mContext;
    if ((localContext instanceof Activity)) {
      return (Activity)localContext;
    }
    return null;
  }
  
  public boolean handleBackPress()
  {
    return false;
  }
  
  public boolean handleEnterLiteWnd()
  {
    return false;
  }
  
  public boolean isAppBackground()
  {
    Context localContext = getContext();
    return ((localContext instanceof Activity)) && (!((Activity)localContext).getWindow().getDecorView().hasWindowFocus());
  }
  
  public boolean isStandardFullScreen()
  {
    if (getPlayerEnvType() == 3)
    {
      Object localObject1 = getContext();
      if ((localObject1 instanceof Activity))
      {
        Object localObject2 = (Activity)localObject1;
        localObject1 = ((Activity)localObject2).getPackageManager();
        localObject2 = ((Activity)localObject2).getComponentName();
        try
        {
          int i = ((PackageManager)localObject1).getActivityInfo((ComponentName)localObject2, 128).configChanges;
          if ((i & 0x80) != 0) {
            return false;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
      return true;
    }
    return false;
  }
  
  public void onActivity(Activity paramActivity, int paramInt) {}
  
  public void onPlayScreenModeChangeBefore(int paramInt1, int paramInt2) {}
  
  public void onPlayScreenModeChanged(int paramInt1, int paramInt2) {}
  
  public void onPlayerExited() {}
  
  public void reqMoveTaskBackground() {}
  
  public boolean reqMoveTaskForeground()
  {
    Context localContext = getContext();
    if (localContext != null) {}
    try
    {
      localContext = localContext.getApplicationContext();
      Intent localIntent = localContext.getPackageManager().getLaunchIntentForPackage(localContext.getPackageName());
      localIntent.addFlags(268435456);
      localIntent.addFlags(2097152);
      localIntent.setPackage(null);
      localContext.startActivity(localIntent);
      return true;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return false;
  }
  
  public void reqStatusBar(Activity paramActivity, int paramInt)
  {
    if ((!isStandardFullScreen()) && (this.mCurrentStatusBarReq != paramInt))
    {
      if (paramActivity == null) {
        return;
      }
      if (paramInt == -1)
      {
        if (this.mIsStatusBarShowBefore) {
          paramActivity.getWindow().clearFlags(1024);
        }
        this.mCurrentStatusBarReq = -1;
        return;
      }
      if (paramInt == 2)
      {
        paramActivity = paramActivity.getWindow();
        boolean bool;
        if ((paramActivity.getAttributes().flags & 0x400) == 0) {
          bool = true;
        } else {
          bool = false;
        }
        this.mIsStatusBarShowBefore = bool;
        paramActivity.setFlags(1024, 1024);
        this.mCurrentStatusBarReq = 2;
      }
      return;
    }
  }
  
  public int requestScreen(Activity paramActivity, int paramInt1, int paramInt2)
  {
    if (isStandardFullScreen()) {
      return -1;
    }
    if (paramActivity == null) {
      return -1;
    }
    if (paramInt1 == 3)
    {
      if (this.mCurrentRotateReq == 1) {
        this.mOriginOrientation = paramActivity.getRequestedOrientation();
      }
      setOrientation(paramActivity, 1);
      this.mCurrentRotateReq = paramInt1;
      return -1;
    }
    if (paramInt1 == 2)
    {
      if (this.mCurrentRotateReq == 1) {
        this.mOriginOrientation = paramActivity.getRequestedOrientation();
      }
      setOrientation(paramActivity, 6);
      this.mCurrentRotateReq = paramInt1;
      return -1;
    }
    if (paramInt1 == 4)
    {
      if (this.mCurrentRotateReq == 1) {
        this.mOriginOrientation = paramActivity.getRequestedOrientation();
      }
      setOrientation(paramActivity, -1);
      this.mCurrentRotateReq = paramInt1;
      return -1;
    }
    if (paramInt1 == 5)
    {
      if (this.mCurrentRotateReq == 1) {
        this.mOriginOrientation = paramActivity.getRequestedOrientation();
      }
      setOrientation(paramActivity, 8);
      this.mCurrentRotateReq = paramInt1;
      return -1;
    }
    if (paramInt1 == 6)
    {
      if (this.mCurrentRotateReq == 1) {
        this.mOriginOrientation = paramActivity.getRequestedOrientation();
      }
      setOrientation(paramActivity, 0);
      this.mCurrentRotateReq = paramInt1;
      return -1;
    }
    if (paramInt1 == 7)
    {
      if (this.mCurrentRotateReq == 1) {
        this.mOriginOrientation = paramActivity.getRequestedOrientation();
      }
      setOrientation(paramActivity, -1);
      this.mCurrentRotateReq = paramInt1;
      return -1;
    }
    if (paramInt1 == 1)
    {
      paramInt1 = this.mOriginOrientation;
      if (paramInt1 != -1000000) {
        setOrientation(paramActivity, paramInt1);
      }
      this.mOriginOrientation = -1000000;
      this.mCurrentRotateReq = 1;
    }
    return -1;
  }
  
  public void resumePlayerRotateStatus()
  {
    requestScreen(getVideoMountedActivity(), this.mCurrentRotateReq, -1);
  }
  
  public void setPlayerEnvLisenter(IPlayerEnvListener paramIPlayerEnvListener)
  {
    this.mPlayerEnvListener = paramIPlayerEnvListener;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\PlayerEnv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */