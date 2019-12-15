package com.tencent.mtt.video.browser.export.wc;

import android.os.Bundle;

public abstract interface IWonderCacheTaskOwner
{
  public abstract boolean canMemoryCache();
  
  public abstract String getJumpUrl(String paramString);
  
  public abstract int getPriority();
  
  public abstract void onCacheCompletion(IWonderCacheTask paramIWonderCacheTask, long paramLong1, long paramLong2, boolean paramBoolean);
  
  public abstract void onCacheError(IWonderCacheTask paramIWonderCacheTask, int paramInt, String paramString);
  
  public abstract void onCacheInfo(IWonderCacheTask paramIWonderCacheTask);
  
  public abstract void onCacheProgress(IWonderCacheTask paramIWonderCacheTask, int paramInt1, long paramLong, int paramInt2);
  
  public abstract void onCacheStatusInfo(int paramInt, String paramString, Bundle paramBundle);
  
  public abstract void onDataReceived(int paramInt);
  
  public abstract void onWonderCacheTaskCreated(IWonderCacheTask paramIWonderCacheTask);
  
  public abstract boolean supportParallelDownload();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\wc\IWonderCacheTaskOwner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */