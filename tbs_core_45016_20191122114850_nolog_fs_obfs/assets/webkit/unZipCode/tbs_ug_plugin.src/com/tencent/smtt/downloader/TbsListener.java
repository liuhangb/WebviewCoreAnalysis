package com.tencent.smtt.downloader;

public abstract interface TbsListener
{
  public abstract void onDownloadFinish(int paramInt);
  
  public abstract void onDownloadProgress(int paramInt);
  
  public abstract void onInstallFinish(int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\TbsListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */