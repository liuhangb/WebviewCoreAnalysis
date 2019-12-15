package com.tencent.mtt.video.browser.export.wc;

import java.io.File;

public abstract interface IWonderCacheTask
{
  public abstract long getContentLength();
  
  public abstract long getCostTime();
  
  public abstract long getDownloadedSize();
  
  public abstract int getHttpStatus();
  
  public abstract String getJumpUrl();
  
  public abstract int getProgress();
  
  public abstract File getTaskCacheDir();
  
  public abstract String getTaskFileName();
  
  public abstract boolean isSupportResume();
  
  public abstract boolean isUseFileCache();
  
  public abstract void pause(boolean paramBoolean);
  
  public abstract void resume(boolean paramBoolean);
  
  public abstract void setFinalCacheFile(String paramString);
  
  public abstract void setFinalCacheFolder(String paramString);
  
  public abstract void updateCostTime();
  
  public static enum TaskState
  {
    static
    {
      State_Pause = new TaskState("State_Pause", 1);
      State_Pause_NETWORKCHANGED = new TaskState("State_Pause_NETWORKCHANGED", 2);
      State_Downloading = new TaskState("State_Downloading", 3);
    }
    
    private TaskState() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\wc\IWonderCacheTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */