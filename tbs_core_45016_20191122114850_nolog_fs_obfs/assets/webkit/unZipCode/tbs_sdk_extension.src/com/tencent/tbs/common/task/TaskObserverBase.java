package com.tencent.tbs.common.task;

public class TaskObserverBase
  implements TaskObserver
{
  public void onTaskCompleted(Task paramTask) {}
  
  public void onTaskCreated(Task paramTask) {}
  
  public void onTaskExtEvent(Task paramTask) {}
  
  public void onTaskFailed(Task paramTask)
  {
    TaskManager.getInstance().taskOver(paramTask);
  }
  
  public void onTaskProgress(Task paramTask) {}
  
  public void onTaskStarted(Task paramTask) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\task\TaskObserverBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */