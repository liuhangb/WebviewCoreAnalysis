package com.tencent.tbs.common.ui.dialog.interfaces;

public abstract interface TBSDialog
{
  public abstract void cancel();
  
  public abstract void dismiss();
  
  public abstract boolean isShowing();
  
  public abstract void setCanceledOnTouchOutside(boolean paramBoolean);
  
  public abstract void show();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\interfaces\TBSDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */