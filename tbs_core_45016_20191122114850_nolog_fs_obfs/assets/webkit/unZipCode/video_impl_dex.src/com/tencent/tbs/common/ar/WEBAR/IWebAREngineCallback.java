package com.tencent.tbs.common.ar.WEBAR;

public abstract interface IWebAREngineCallback
{
  public abstract void onDownloadProgress(int paramInt);
  
  public abstract void onDownloadStart(int paramInt);
  
  public abstract void onLoadFailed(int paramInt1, int paramInt2, String paramString);
  
  public abstract void onLoadSuccess(int paramInt, String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ar\WEBAR\IWebAREngineCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */