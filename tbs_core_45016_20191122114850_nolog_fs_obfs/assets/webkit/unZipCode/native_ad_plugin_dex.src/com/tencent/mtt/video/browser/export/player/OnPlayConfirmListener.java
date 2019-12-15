package com.tencent.mtt.video.browser.export.player;

public abstract interface OnPlayConfirmListener
{
  public abstract void onContinuePlay(int paramInt);
  
  public abstract void onContinuePlayCanceled();
  
  public abstract void onDownloadSoCanceled();
  
  public abstract void onDownloadSoConfirmed();
  
  public abstract void onPlayCanceled();
  
  public abstract void onPlayConfirmed(int paramInt);
  
  public abstract void onPrepareCanceled();
  
  public abstract void onPrepareConfirmed(int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\OnPlayConfirmListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */