package com.tencent.mtt.video.browser.export.player;

import android.content.Context;

public abstract interface IPlayConfirmController
{
  public static final int COMFIRM_TYPE_CONTINUE_PLAY = 2;
  public static final int COMFIRM_TYPE_DOWNLOAD_SO = 1;
  public static final int COMFIRM_TYPE_PLAY = 3;
  public static final int COMFIRM_TYPE_PREPARE = 4;
  
  public abstract void dismiss();
  
  public abstract boolean isAlertDialogShowing();
  
  public abstract boolean isShowing(int paramInt);
  
  public abstract void showConfirmDlg(int paramInt, String paramString, Context paramContext);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\IPlayConfirmController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */