package com.tencent.mtt.video.browser.export.wc;

import android.os.Bundle;
import java.io.File;
import java.util.Map;

public abstract interface IWonderCacheTaskMgr
{
  public abstract void clear();
  
  public abstract void clearDir(File paramFile);
  
  public abstract IWonderCacheTask createCacheTask(int paramInt, String paramString1, String paramString2, String paramString3, Map<String, String> paramMap, IWonderCacheTaskOwner paramIWonderCacheTaskOwner, Bundle paramBundle);
  
  public abstract void deleteCache(String paramString);
  
  public abstract long getRealFileSize(String paramString);
  
  public abstract boolean stopCacheTask(IWonderCacheTask paramIWonderCacheTask, IWonderCacheTaskOwner paramIWonderCacheTaskOwner);
  
  public abstract void userDeleteCacheFile();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\wc\IWonderCacheTaskMgr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */