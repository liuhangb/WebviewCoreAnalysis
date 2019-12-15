package com.tencent.tbs.tbsshell.partner.player.controller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import com.tencent.mtt.video.browser.export.player.IPlayConfirmController;
import com.tencent.mtt.video.browser.export.player.OnPlayConfirmListener;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.ui.dialog.TBSDefaultDialogBuilder;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialog;

public class PlayConfirmController
  implements IPlayConfirmController
{
  private TBSDialog mConfirmPlaydlg;
  private TBSDialog mContinuewPlaydlg;
  private OnPlayConfirmListener mListener;
  
  public PlayConfirmController(OnPlayConfirmListener paramOnPlayConfirmListener)
  {
    this.mListener = paramOnPlayConfirmListener;
  }
  
  private boolean isDialogShowing(TBSDialog paramTBSDialog)
  {
    return (paramTBSDialog != null) && (paramTBSDialog.isShowing());
  }
  
  public void dismiss() {}
  
  public boolean isAlertDialogShowing()
  {
    return (isDialogShowing(this.mConfirmPlaydlg)) || (isDialogShowing(this.mContinuewPlaydlg));
  }
  
  public boolean isShowing(int paramInt)
  {
    if (paramInt == 2) {
      return isDialogShowing(this.mContinuewPlaydlg);
    }
    if (paramInt == 3) {
      return isDialogShowing(this.mConfirmPlaydlg);
    }
    return false;
  }
  
  public void showConfirmDlg(int paramInt, String paramString, Context paramContext)
  {
    if (!(paramContext instanceof Activity)) {
      return;
    }
    if (paramInt == 3)
    {
      paramString = new TBSDefaultDialogBuilder(paramContext);
      paramString.setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          paramAnonymousDialogInterface.dismiss();
          PlayConfirmController.this.mListener.onPlayCanceled();
        }
      });
      paramContext = new StringBuilder();
      paramContext.append(TBSResources.getText("video_play_confirm_msg"));
      paramContext.append("");
      paramString.setMessage(paramContext.toString());
      paramContext = new StringBuilder();
      paramContext.append(TBSResources.getText("video_cancel"));
      paramContext.append("");
      paramString.setNegativeButton(paramContext.toString(), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          PlayConfirmController.this.mListener.onPlayCanceled();
        }
      });
      paramContext = new StringBuilder();
      paramContext.append(TBSResources.getText("video_2g3g_confirm_and_donot_show"));
      paramContext.append("");
      paramString.setNeutralButton(paramContext.toString(), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          PlayConfirmController.this.mListener.onPlayConfirmed(1);
        }
      });
      paramContext = new StringBuilder();
      paramContext.append(TBSResources.getText("video_play_confirm"));
      paramContext.append("");
      paramString.setPositiveButton(paramContext.toString(), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          PlayConfirmController.this.mListener.onPlayConfirmed(0);
        }
      });
      this.mConfirmPlaydlg = paramString.create();
      this.mConfirmPlaydlg.show();
      return;
    }
    if (paramInt == 2)
    {
      paramString = new TBSDefaultDialogBuilder(paramContext);
      paramString.setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          paramAnonymousDialogInterface.dismiss();
          PlayConfirmController.this.mListener.onContinuePlayCanceled();
        }
      });
      paramContext = new StringBuilder();
      paramContext.append(TBSResources.getText("video_play_confirm_msg"));
      paramContext.append("");
      paramString.setMessage(paramContext.toString());
      paramContext = new StringBuilder();
      paramContext.append(TBSResources.getText("video_cancel"));
      paramContext.append("");
      paramString.setNegativeButton(paramContext.toString(), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          PlayConfirmController.this.mListener.onContinuePlayCanceled();
        }
      });
      paramContext = new StringBuilder();
      paramContext.append(TBSResources.getText("video_2g3g_confirm_and_donot_show"));
      paramContext.append("");
      paramString.setNeutralButton(paramContext.toString(), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          PlayConfirmController.this.mListener.onContinuePlay(1);
        }
      });
      paramContext = new StringBuilder();
      paramContext.append(TBSResources.getText("video_play_confirm"));
      paramContext.append("");
      paramString.setPositiveButton(paramContext.toString(), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          PlayConfirmController.this.mListener.onContinuePlay(0);
        }
      });
      this.mContinuewPlaydlg = paramString.create();
      this.mContinuewPlaydlg.show();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\player\controller\PlayConfirmController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */