package com.tencent.common.plugin;

public abstract interface IPluginDownInstallCallback
{
  public abstract void onGetPluginListFailed(int paramInt);
  
  public abstract void onGetPluginListSucc(int paramInt);
  
  public abstract void onPluginDownloadCreated(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract void onPluginDownloadFailed(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void onPluginDownloadProgress(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void onPluginDownloadStarted(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void onPluginDownloadSuccessed(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean);
  
  public abstract void onPluginInstallFailed(String paramString, int paramInt1, int paramInt2);
  
  public abstract void onPluginInstallSuccessed(String paramString, QBPluginItemInfo paramQBPluginItemInfo, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\IPluginDownInstallCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */