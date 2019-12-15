package com.tencent.common.plugin;

public abstract interface IQBPluginSystemCallback
{
  public abstract void onDownloadCreateed(String paramString1, String paramString2);
  
  public abstract void onDownloadProgress(String paramString, int paramInt1, int paramInt2);
  
  public abstract void onDownloadStart(String paramString, int paramInt);
  
  public abstract void onDownloadSuccessed(String paramString1, String paramString2);
  
  public abstract void onNeedDownloadNotify(String paramString, boolean paramBoolean);
  
  public abstract void onPrepareFinished(String paramString, QBPluginItemInfo paramQBPluginItemInfo, int paramInt1, int paramInt2);
  
  public abstract void onPrepareStart(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\IQBPluginSystemCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */