package com.tencent.tbs.common.download;

import com.tencent.mtt.base.task.TaskObserver;
import com.tencent.mtt.browser.download.engine.DownloadInfo;

public abstract interface IDownloadManager
{
  public abstract void addDownloadedTaskListener(BaseDownloadManager.OnDownloadedTaskListener paramOnDownloadedTaskListener);
  
  public abstract void addTaskObserver(TaskObserver paramTaskObserver);
  
  public abstract void cancelTaskNoRet(int paramInt);
  
  public abstract boolean checkTaskDownloading(String paramString);
  
  public abstract boolean deleteTask(int paramInt, boolean paramBoolean);
  
  public abstract boolean fireTaskSuccess(String paramString, TaskObserver paramTaskObserver);
  
  public abstract int getTaskID(String paramString);
  
  public abstract boolean hasInitCompleted();
  
  public abstract void init();
  
  public abstract int startDownload(DownloadInfo paramDownloadInfo);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\IDownloadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */