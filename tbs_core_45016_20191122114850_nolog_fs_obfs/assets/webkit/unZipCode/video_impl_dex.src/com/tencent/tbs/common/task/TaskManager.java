package com.tencent.tbs.common.task;

import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.utils.DeviceUtils;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskManager
{
  private static HandlerThread HandlerThreadWithLoop;
  private static final int KEEP_ALIVE_TIME = 60;
  private static final String TAG = "TaskManager";
  private static final int THREAD_CAPACITY_BACKGROUND = 1;
  private static final int THREAD_CAPACITY_CORE = 2;
  private static final int THREAD_CAPACITY_PICTURE = 1;
  private static TaskManager mInstance;
  private static Handler sHandlerThreadWithLoop;
  private ThreadPoolExecutor mCoreTaskExecutor = new ThreadPoolExecutor(2, 2, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
  
  public TaskManager()
  {
    if (DeviceUtils.getSdkVersion() >= 9) {
      this.mCoreTaskExecutor.allowCoreThreadTimeOut(true);
    }
  }
  
  public static TaskManager getInstance()
  {
    if (mInstance == null) {
      mInstance = new TaskManager();
    }
    return mInstance;
  }
  
  public static Handler tbsBaseModuleThreadHandler()
  {
    try
    {
      if (HandlerThreadWithLoop == null)
      {
        HandlerThreadWithLoop = new HandlerThread("tbs_basemodule_handle_thread");
        HandlerThreadWithLoop.start();
        sHandlerThreadWithLoop = new Handler(HandlerThreadWithLoop.getLooper());
      }
      Handler localHandler = sHandlerThreadWithLoop;
      return localHandler;
    }
    finally {}
  }
  
  public void addTask(Task paramTask)
  {
    if (paramTask == null) {
      return;
    }
    try
    {
      if (!paramTask.shouldRun()) {
        LogUtils.d("TaskManager", "Task is ignored");
      }
      try
      {
        this.mCoreTaskExecutor.execute(paramTask);
      }
      catch (OutOfMemoryError paramTask)
      {
        paramTask.printStackTrace();
      }
      return;
    }
    finally {}
  }
  
  public void cancelPictureTask(Task paramTask)
  {
    try
    {
      cancelTask(paramTask);
      return;
    }
    finally
    {
      paramTask = finally;
      throw paramTask;
    }
  }
  
  public void cancelTask(Task paramTask)
  {
    if (paramTask != null) {
      try
      {
        if ((paramTask.mStatus != 3) && (paramTask.mStatus != 5))
        {
          this.mCoreTaskExecutor.remove(paramTask);
          paramTask.cancel();
          return;
        }
      }
      finally {}
    }
  }
  
  public void taskOver(Task paramTask)
  {
    if (paramTask == null) {
      return;
    }
    try
    {
      paramTask.onTaskOver();
      return;
    }
    finally
    {
      paramTask = finally;
      throw paramTask;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\task\TaskManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */