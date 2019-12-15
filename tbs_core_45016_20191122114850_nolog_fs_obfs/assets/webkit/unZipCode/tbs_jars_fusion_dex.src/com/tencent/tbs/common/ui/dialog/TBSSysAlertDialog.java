package com.tencent.tbs.common.ui.dialog;

import android.app.AlertDialog;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialog;

public class TBSSysAlertDialog
  implements TBSDialog
{
  private AlertDialog mSysDialog;
  
  public TBSSysAlertDialog(AlertDialog paramAlertDialog)
  {
    this.mSysDialog = paramAlertDialog;
  }
  
  public void cancel()
  {
    this.mSysDialog.cancel();
  }
  
  public void dismiss()
  {
    this.mSysDialog.dismiss();
  }
  
  public boolean isShowing()
  {
    return this.mSysDialog.isShowing();
  }
  
  public void setCanceledOnTouchOutside(boolean paramBoolean)
  {
    this.mSysDialog.setCanceledOnTouchOutside(paramBoolean);
  }
  
  public void show()
  {
    this.mSysDialog.show();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\TBSSysAlertDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */