package com.tencent.common.threadpool;

import com.tencent.mtt.base.task.Task;
import com.tencent.mtt.base.task.Task.Priority;
import java.util.Comparator;

public class TaskComparator
  implements Comparator<Runnable>
{
  static TaskComparator a = new TaskComparator();
  
  public int compare(Runnable paramRunnable1, Runnable paramRunnable2)
  {
    int j = 0;
    if (paramRunnable1 != null)
    {
      if (paramRunnable2 == null) {
        return 0;
      }
      boolean bool = paramRunnable1 instanceof Task;
      Task.Priority localPriority = null;
      if (bool)
      {
        paramRunnable1 = (Task)paramRunnable1;
      }
      else
      {
        if ((paramRunnable1 instanceof ComparableFutureTask))
        {
          paramRunnable1 = (ComparableFutureTask)paramRunnable1;
          if ((paramRunnable1.task instanceof Task))
          {
            paramRunnable1 = (Task)paramRunnable1.task;
            break label70;
          }
        }
        paramRunnable1 = null;
      }
      label70:
      Object localObject;
      if ((paramRunnable2 instanceof Task))
      {
        localObject = (Task)paramRunnable2;
      }
      else
      {
        localObject = localPriority;
        if ((paramRunnable2 instanceof ComparableFutureTask))
        {
          paramRunnable2 = (ComparableFutureTask)paramRunnable2;
          localObject = localPriority;
          if ((paramRunnable2.task instanceof Task)) {
            localObject = (Task)paramRunnable2.task;
          }
        }
      }
      if (paramRunnable1 == null) {
        paramRunnable2 = Task.Priority.NORMAL;
      } else {
        paramRunnable2 = paramRunnable1.mPriority;
      }
      if (localObject == null) {
        localPriority = Task.Priority.NORMAL;
      } else {
        localPriority = ((Task)localObject).mPriority;
      }
      if (paramRunnable2 == localPriority)
      {
        int i;
        if (paramRunnable1 == null) {
          i = 0;
        } else {
          i = paramRunnable1.mSequence.intValue();
        }
        if (localObject != null) {
          j = ((Task)localObject).mSequence.intValue();
        }
        return i - j;
      }
      return localPriority.ordinal() - paramRunnable2.ordinal();
    }
    return 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\TaskComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */