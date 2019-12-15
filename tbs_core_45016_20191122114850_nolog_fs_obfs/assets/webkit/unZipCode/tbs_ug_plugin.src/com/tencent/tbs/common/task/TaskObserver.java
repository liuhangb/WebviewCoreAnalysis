package com.tencent.tbs.common.task;

public abstract interface TaskObserver
{
  public abstract void onTaskCompleted(Task paramTask);
  
  public abstract void onTaskCreated(Task paramTask);
  
  public abstract void onTaskExtEvent(Task paramTask);
  
  public abstract void onTaskFailed(Task paramTask);
  
  public abstract void onTaskProgress(Task paramTask);
  
  public abstract void onTaskStarted(Task paramTask);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\task\TaskObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */