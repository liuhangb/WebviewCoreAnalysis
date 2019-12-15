package com.tencent.tbs.tbsshell.common.readmode;

public abstract interface IWebpageReadModePluginLoadCallback
{
  public abstract void onPluginLoadFailed(int paramInt, String paramString);
  
  public abstract void onPluginLoadProgress(int paramInt);
  
  public abstract void onPluginLoadStarted(int paramInt);
  
  public abstract void onPluginLoadSuccess(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\readmode\IWebpageReadModePluginLoadCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */