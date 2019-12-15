package com.tencent.mtt.browser.download.engine;

import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.mtt.base.task.Task;
import com.tencent.mtt.base.task.TaskObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class DownloadEventManager
{
  private static DownloadEventManager jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager;
  private DownloadEventOnDownloadedTaskListener jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventOnDownloadedTaskListener = new DownloadEventOnDownloadedTaskListener();
  private DownloadEventTaskObserver jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventTaskObserver = new DownloadEventTaskObserver();
  private final String jdField_a_of_type_JavaLangString = "defaule_url";
  
  public static DownloadEventManager getInstance()
  {
    try
    {
      if (jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager == null) {
        jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager = new DownloadEventManager();
      }
      DownloadEventManager localDownloadEventManager = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager;
      return localDownloadEventManager;
    }
    finally {}
  }
  
  public void addDownloadedTaskListener(ExtNotififyListener paramExtNotififyListener)
  {
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventOnDownloadedTaskListener.addDownloadedTaskListener(paramExtNotififyListener);
  }
  
  public void addTaskObserver(TaskObserver paramTaskObserver)
  {
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventTaskObserver.addTaskObserver("defaule_url", paramTaskObserver);
  }
  
  public void addTaskObserver(String paramString, TaskObserver paramTaskObserver)
  {
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventTaskObserver.addTaskObserver(paramString, paramTaskObserver);
  }
  
  public void cancelNotification(DownloadTask paramDownloadTask)
  {
    DownloadEventOnDownloadedTaskListener localDownloadEventOnDownloadedTaskListener = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventOnDownloadedTaskListener;
    if (localDownloadEventOnDownloadedTaskListener != null) {
      localDownloadEventOnDownloadedTaskListener.cancelNotification(paramDownloadTask);
    }
  }
  
  public DownloadEventOnDownloadedTaskListener downloadEventOnDownloadedTaskListener()
  {
    return this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventOnDownloadedTaskListener;
  }
  
  public DownloadEventTaskObserver downloadEventTaskObserver()
  {
    return this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventTaskObserver;
  }
  
  public void notifyTaskCanceled(String paramString)
  {
    DownloadEventOnDownloadedTaskListener localDownloadEventOnDownloadedTaskListener = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventOnDownloadedTaskListener;
    if (localDownloadEventOnDownloadedTaskListener != null) {
      localDownloadEventOnDownloadedTaskListener.notifyTaskCanceled(paramString);
    }
  }
  
  public void notifyTaskDeleted(DownloadTask paramDownloadTask)
  {
    DownloadEventOnDownloadedTaskListener localDownloadEventOnDownloadedTaskListener = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventOnDownloadedTaskListener;
    if (localDownloadEventOnDownloadedTaskListener != null) {
      localDownloadEventOnDownloadedTaskListener.notifyTaskDeleted(paramDownloadTask);
    }
  }
  
  public void notifyTaskPrepareStart(DownloadTask paramDownloadTask)
  {
    DownloadEventOnDownloadedTaskListener localDownloadEventOnDownloadedTaskListener = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventOnDownloadedTaskListener;
    if (localDownloadEventOnDownloadedTaskListener != null) {
      localDownloadEventOnDownloadedTaskListener.notifyTaskPrepareStart(paramDownloadTask);
    }
  }
  
  public void notifyUser(String paramString, int paramInt)
  {
    DownloadEventOnDownloadedTaskListener localDownloadEventOnDownloadedTaskListener = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventOnDownloadedTaskListener;
    if (localDownloadEventOnDownloadedTaskListener != null) {
      localDownloadEventOnDownloadedTaskListener.notifyUser(paramString, paramInt);
    }
  }
  
  public void removeDownloadedTaskListener(ExtNotififyListener paramExtNotififyListener)
  {
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventOnDownloadedTaskListener.removeDownloadedTaskListener(paramExtNotififyListener);
  }
  
  public void removeTaskObserver(TaskObserver paramTaskObserver)
  {
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadEventManager$DownloadEventTaskObserver.removeTaskObserver(paramTaskObserver);
  }
  
  public class DownloadEventOnDownloadedTaskListener
    implements DownloadEventManager.ExtNotififyListener
  {
    private List<DownloadEventManager.ExtNotififyListener> jdField_a_of_type_JavaUtilList = Collections.synchronizedList(new ArrayList());
    
    public DownloadEventOnDownloadedTaskListener() {}
    
    public void addDownloadedTaskListener(DownloadEventManager.ExtNotififyListener paramExtNotififyListener)
    {
      synchronized (this.jdField_a_of_type_JavaUtilList)
      {
        if (!this.jdField_a_of_type_JavaUtilList.contains(paramExtNotififyListener)) {
          this.jdField_a_of_type_JavaUtilList.add(paramExtNotififyListener);
        }
        return;
      }
    }
    
    public void cancelNotification(DownloadTask paramDownloadTask)
    {
      synchronized (this.jdField_a_of_type_JavaUtilList)
      {
        Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
        while (localIterator.hasNext()) {
          ((DownloadEventManager.ExtNotififyListener)localIterator.next()).cancelNotification(paramDownloadTask);
        }
        return;
      }
    }
    
    public void notifyTaskCanceled(String paramString)
    {
      synchronized (this.jdField_a_of_type_JavaUtilList)
      {
        Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
        while (localIterator.hasNext()) {
          ((DownloadEventManager.ExtNotififyListener)localIterator.next()).notifyTaskCanceled(paramString);
        }
        return;
      }
    }
    
    public void notifyTaskDeleted(DownloadTask paramDownloadTask)
    {
      synchronized (this.jdField_a_of_type_JavaUtilList)
      {
        Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
        while (localIterator.hasNext()) {
          ((DownloadEventManager.ExtNotififyListener)localIterator.next()).notifyTaskDeleted(paramDownloadTask);
        }
        return;
      }
    }
    
    public void notifyTaskLength(DownloadTask paramDownloadTask, String paramString, long paramLong1, long paramLong2)
    {
      synchronized (this.jdField_a_of_type_JavaUtilList)
      {
        Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
        while (localIterator.hasNext()) {
          ((DownloadEventManager.ExtNotififyListener)localIterator.next()).notifyTaskLength(paramDownloadTask, paramString, paramLong1, paramLong2);
        }
        return;
      }
    }
    
    public void notifyTaskPrepareStart(DownloadTask paramDownloadTask)
    {
      synchronized (this.jdField_a_of_type_JavaUtilList)
      {
        Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
        while (localIterator.hasNext()) {
          ((DownloadEventManager.ExtNotififyListener)localIterator.next()).notifyTaskPrepareStart(paramDownloadTask);
        }
        return;
      }
    }
    
    public void notifyUser(String paramString, int paramInt)
    {
      synchronized (this.jdField_a_of_type_JavaUtilList)
      {
        Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
        while (localIterator.hasNext()) {
          ((DownloadEventManager.ExtNotififyListener)localIterator.next()).notifyUser(paramString, paramInt);
        }
        return;
      }
    }
    
    public void removeDownloadedTaskListener(DownloadEventManager.ExtNotififyListener paramExtNotififyListener)
    {
      this.jdField_a_of_type_JavaUtilList.remove(paramExtNotififyListener);
    }
  }
  
  public class DownloadEventTaskObserver
    implements TaskObserver
  {
    private final HashMap<String, List<TaskObserver>> jdField_a_of_type_JavaUtilHashMap = new HashMap();
    
    public DownloadEventTaskObserver() {}
    
    private void a(Task paramTask, DownloadEventManager.a parama, String paramString)
    {
      if (parama != null)
      {
        if (paramTask == null) {
          return;
        }
        synchronized (this.jdField_a_of_type_JavaUtilHashMap)
        {
          Object localObject = new StringBuilder();
          ((StringBuilder)localObject).append("DownloadEventTaskObserver loopObserver: ");
          ((StringBuilder)localObject).append(paramString);
          ((StringBuilder)localObject).append(", name:");
          ((StringBuilder)localObject).append(((DownloadTask)paramTask).getFileName());
          FLogger.d("ZHTAG", ((StringBuilder)localObject).toString());
          paramString = ((DownloadTask)paramTask).getTaskUrl();
          localObject = new ArrayList();
          if ((!TextUtils.isEmpty(paramString)) && (this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString))) {
            ((List)localObject).addAll((Collection)this.jdField_a_of_type_JavaUtilHashMap.get(paramString));
          }
          if (this.jdField_a_of_type_JavaUtilHashMap.containsKey("defaule_url")) {
            ((List)localObject).addAll((Collection)this.jdField_a_of_type_JavaUtilHashMap.get("defaule_url"));
          }
          if (!((List)localObject).isEmpty())
          {
            paramString = ((List)localObject).iterator();
            while (paramString.hasNext())
            {
              localObject = (TaskObserver)paramString.next();
              if (localObject != null) {
                parama.a((TaskObserver)localObject, paramTask);
              }
            }
          }
          return;
        }
      }
    }
    
    public void addTaskObserver(String paramString, TaskObserver paramTaskObserver)
    {
      synchronized (this.jdField_a_of_type_JavaUtilHashMap)
      {
        if (this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString))
        {
          paramString = (List)this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
          if (!paramString.contains(paramTaskObserver)) {
            paramString.add(paramTaskObserver);
          }
        }
        else
        {
          ArrayList localArrayList = new ArrayList();
          localArrayList.add(paramTaskObserver);
          this.jdField_a_of_type_JavaUtilHashMap.put(paramString, localArrayList);
        }
        return;
      }
    }
    
    public void onTaskCompleted(Task paramTask)
    {
      a(paramTask, new DownloadEventManager.a()
      {
        public void a(TaskObserver paramAnonymousTaskObserver, Task paramAnonymousTask)
        {
          paramAnonymousTaskObserver.onTaskCompleted(paramAnonymousTask);
        }
      }, "onTaskCompleted");
    }
    
    public void onTaskCreated(Task paramTask)
    {
      FLogger.d("ZHTAG", "DownloadEventManager onTaskCreated");
      a(paramTask, new DownloadEventManager.a()
      {
        public void a(TaskObserver paramAnonymousTaskObserver, Task paramAnonymousTask)
        {
          paramAnonymousTaskObserver.onTaskCreated(paramAnonymousTask);
        }
      }, "onTaskCreated");
    }
    
    public void onTaskExtEvent(Task paramTask)
    {
      a(paramTask, new DownloadEventManager.a()
      {
        public void a(TaskObserver paramAnonymousTaskObserver, Task paramAnonymousTask)
        {
          paramAnonymousTaskObserver.onTaskExtEvent(paramAnonymousTask);
        }
      }, "onTaskExtEvent");
    }
    
    public void onTaskFailed(Task paramTask)
    {
      a(paramTask, new DownloadEventManager.a()
      {
        public void a(TaskObserver paramAnonymousTaskObserver, Task paramAnonymousTask)
        {
          paramAnonymousTaskObserver.onTaskFailed(paramAnonymousTask);
        }
      }, "onTaskFailed");
    }
    
    public void onTaskProgress(Task paramTask)
    {
      a(paramTask, new DownloadEventManager.a()
      {
        public void a(TaskObserver paramAnonymousTaskObserver, Task paramAnonymousTask)
        {
          paramAnonymousTaskObserver.onTaskProgress(paramAnonymousTask);
        }
      }, "onTaskProgress");
    }
    
    public void onTaskStarted(Task paramTask)
    {
      a(paramTask, new DownloadEventManager.a()
      {
        public void a(TaskObserver paramAnonymousTaskObserver, Task paramAnonymousTask)
        {
          paramAnonymousTaskObserver.onTaskStarted(paramAnonymousTask);
        }
      }, "onTaskStarted");
    }
    
    public void removeTaskObserver(TaskObserver paramTaskObserver)
    {
      synchronized (this.jdField_a_of_type_JavaUtilHashMap)
      {
        Iterator localIterator1 = this.jdField_a_of_type_JavaUtilHashMap.entrySet().iterator();
        while (localIterator1.hasNext())
        {
          List localList = (List)((Map.Entry)localIterator1.next()).getValue();
          Iterator localIterator2 = localList.iterator();
          while (localIterator2.hasNext()) {
            if (paramTaskObserver == localIterator2.next())
            {
              localIterator2.remove();
              if (localList.isEmpty()) {
                localIterator1.remove();
              }
            }
          }
        }
        return;
      }
    }
  }
  
  public static abstract interface ExtNotififyListener
  {
    public abstract void cancelNotification(DownloadTask paramDownloadTask);
    
    public abstract void notifyTaskCanceled(String paramString);
    
    public abstract void notifyTaskDeleted(DownloadTask paramDownloadTask);
    
    public abstract void notifyTaskLength(DownloadTask paramDownloadTask, String paramString, long paramLong1, long paramLong2);
    
    public abstract void notifyTaskPrepareStart(DownloadTask paramDownloadTask);
    
    public abstract void notifyUser(String paramString, int paramInt);
  }
  
  private static abstract interface a
  {
    public abstract void a(TaskObserver paramTaskObserver, Task paramTask);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DownloadEventManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */