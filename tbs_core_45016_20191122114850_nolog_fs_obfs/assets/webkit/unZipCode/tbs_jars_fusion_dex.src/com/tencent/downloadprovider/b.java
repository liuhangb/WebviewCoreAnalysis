package com.tencent.downloadprovider;

import android.content.ContentValues;
import android.content.Context;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.tencent.basesupport.FLogger;
import com.tencent.common.http.Apn;
import com.tencent.common.http.QueenConfig;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.mtt.base.utils.DLConvertTools;
import com.tencent.mtt.base.utils.DLLogger;
import com.tencent.mtt.base.utils.DLReporterManager;
import com.tencent.mtt.browser.download.engine.DownloadTask;
import com.tencent.mtt.browser.download.engine.DownloadTaskManager;
import com.tencent.mtt.browser.download.engine.DownloadThreadPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class b
  implements QBDownloadProvider.a
{
  public static a a;
  public static b a;
  public static c a;
  private static b jdField_a_of_type_ComTencentDownloadproviderB;
  public static String a = "QBDownloadService";
  c jdField_a_of_type_ComTencentDownloadproviderC = null;
  d jdField_a_of_type_ComTencentDownloadproviderD;
  DownloadTaskManager jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
  
  public static b a()
  {
    try
    {
      if (jdField_a_of_type_ComTencentDownloadproviderB == null) {
        jdField_a_of_type_ComTencentDownloadproviderB = new b();
      }
      b localb = jdField_a_of_type_ComTencentDownloadproviderB;
      return localb;
    }
    finally {}
  }
  
  void a()
  {
    d locald = this.jdField_a_of_type_ComTencentDownloadproviderD;
    if (locald != null) {
      locald.a(new Runnable()
      {
        public void run()
        {
          long l1 = System.currentTimeMillis();
          List localList = DownloadproviderHelper.a();
          Object localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("CORRECT_DB_QUERY: [");
          ((StringBuilder)localObject2).append(System.currentTimeMillis() - l1);
          ((StringBuilder)localObject2).append("], TASK_SIZE: [");
          if (localList == null) {
            localObject1 = "0";
          } else {
            localObject1 = Integer.valueOf(localList.size());
          }
          ((StringBuilder)localObject2).append(localObject1);
          ((StringBuilder)localObject2).append("]");
          DLLogger.d(((StringBuilder)localObject2).toString());
          if (localList != null)
          {
            Object localObject3 = new ArrayList();
            localObject1 = new ArrayList();
            localObject2 = localList.iterator();
            DownloadTask localDownloadTask;
            while (((Iterator)localObject2).hasNext())
            {
              localDownloadTask = (DownloadTask)((Iterator)localObject2).next();
              if (localDownloadTask != null)
              {
                boolean bool = localDownloadTask.isApkFile();
                int k = 1;
                int i;
                if ((bool) && (!localDownloadTask.getExtFlagAutoInstall()))
                {
                  localDownloadTask.setExtFlagAutoInstall(true, false);
                  i = 1;
                }
                else
                {
                  i = 0;
                }
                int j = i;
                switch (localDownloadTask.getStatus())
                {
                case 10: 
                default: 
                  j = i;
                  break;
                case 11: 
                  localDownloadTask.setStatusInner((byte)6);
                  j = k;
                  break;
                case 9: 
                  localDownloadTask.setStatusInner((byte)9);
                  j = k;
                  break;
                case 8: 
                  localDownloadTask.setStatusInner((byte)8);
                  j = k;
                  break;
                case 6: 
                  FLogger.d("ZHNotify", "cancel 1.");
                  if (localDownloadTask.isBackAutoTask())
                  {
                    FLogger.d("ZHNotify", "cancel 2.");
                    localDownloadTask.setStatusInner((byte)0);
                    i = 1;
                  }
                  if (!localDownloadTask.isStartOnWifiTask())
                  {
                    j = i;
                    if (!localDownloadTask.getIsPausdedByNoWifi()) {
                      break;
                    }
                  }
                  else
                  {
                    j = i;
                    if (!localDownloadTask.getIsPausdedByUser())
                    {
                      FLogger.d("ZHNotify", "cancel 3.");
                      b.this.a(localDownloadTask);
                      j = k;
                    }
                  }
                  break;
                case 3: 
                  j = i;
                  if (b.a != null)
                  {
                    j = i;
                    if (localDownloadTask.isApkFile())
                    {
                      ((List)localObject1).add(localDownloadTask);
                      j = i;
                    }
                  }
                  break;
                case 0: 
                case 1: 
                case 2: 
                  localDownloadTask.setExtFlagCreateFirst(true, false);
                  b.this.a(localDownloadTask);
                  j = k;
                  break;
                }
                if (j != 0) {
                  ((List)localObject3).add(localDownloadTask);
                }
              }
            }
            if (!((List)localObject3).isEmpty())
            {
              localObject2 = new ArrayList(((List)localObject3).size());
              localObject3 = ((List)localObject3).iterator();
              while (((Iterator)localObject3).hasNext())
              {
                localDownloadTask = (DownloadTask)((Iterator)localObject3).next();
                ContentValues localContentValues = DLConvertTools.downloadTast2ContentValues(localDownloadTask);
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("corret_db updateTask :");
                localStringBuilder.append(localDownloadTask.getDownloadTaskId());
                localStringBuilder.append(", name: ");
                localStringBuilder.append(localDownloadTask.getFileName());
                localStringBuilder.append(", status:");
                localStringBuilder.append(localDownloadTask.getStatus());
                Log.d("FUCK", localStringBuilder.toString());
                if (localContentValues != null) {
                  ((ArrayList)localObject2).add(localContentValues);
                }
              }
              DownloadproviderHelper.a((ArrayList)localObject2);
            }
            if (b.a != null) {
              b.this.a(new BrowserExecutorSupplier.BackgroundRunable()
              {
                public void doRun()
                {
                  Iterator localIterator = localObject1.iterator();
                  while (localIterator.hasNext())
                  {
                    DownloadTask localDownloadTask = (DownloadTask)localIterator.next();
                    if (b.jdField_a_of_type_ComTencentDownloadproviderB$a != null) {
                      b.jdField_a_of_type_ComTencentDownloadproviderB$a.a(localDownloadTask);
                    }
                  }
                  if (b.jdField_a_of_type_ComTencentDownloadproviderB$b != null)
                  {
                    FLogger.d("ZAYRESTART", "notifyCompelete.");
                    b.jdField_a_of_type_ComTencentDownloadproviderB$b.a();
                  }
                }
              });
            }
          }
          long l2 = System.currentTimeMillis();
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("CORRECT_DB_USE: [");
          l1 = l2 - l1;
          ((StringBuilder)localObject2).append(l1);
          ((StringBuilder)localObject2).append("], TASK_SIZE: [");
          if (localList == null) {
            localObject1 = "0";
          } else {
            localObject1 = Integer.valueOf(localList.size());
          }
          ((StringBuilder)localObject2).append(localObject1);
          ((StringBuilder)localObject2).append("]");
          DLLogger.d(((StringBuilder)localObject2).toString());
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("<C_DB:");
          ((StringBuilder)localObject2).append(l1);
          ((StringBuilder)localObject2).append(",");
          if (localList == null) {
            localObject1 = "0";
          } else {
            localObject1 = Integer.valueOf(localList.size());
          }
          ((StringBuilder)localObject2).append(localObject1);
          ((StringBuilder)localObject2).append(">");
          DLReporterManager.addStepForAllReporter(((StringBuilder)localObject2).toString());
          final Object localObject1 = new a();
          ((a)localObject1).a = a.a.c;
          b.this.a((a)localObject1);
        }
      });
    }
  }
  
  public void a(int paramInt)
  {
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager.initDownloadNum(paramInt);
  }
  
  public void a(Context paramContext)
  {
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager = DownloadTaskManager.newInstance(paramContext);
    Apn.setApplicationContext(paramContext);
    this.jdField_a_of_type_ComTencentDownloadproviderC = new c(paramContext, this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager);
    paramContext = DownloadThreadPolicy.createDownloadScheduler("down_schedule");
    paramContext.start();
    try
    {
      for (;;)
      {
        Looper localLooper = paramContext.getLooper();
        if (localLooper != null) {
          break;
        }
        try
        {
          wait();
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
      }
      this.jdField_a_of_type_ComTencentDownloadproviderD = new d(paramContext.getLooper(), this.jdField_a_of_type_ComTencentDownloadproviderC);
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager.setScheduleHandler(this.jdField_a_of_type_ComTencentDownloadproviderD);
      QBDownloadProvider.a = this;
      FLogger.d("ZHBUG", "correctDB");
      a();
      return;
    }
    finally {}
  }
  
  void a(BrowserExecutorSupplier.BackgroundRunable paramBackgroundRunable)
  {
    BrowserExecutorSupplier.postForTimeoutTasks(paramBackgroundRunable);
  }
  
  void a(DownloadTask paramDownloadTask)
  {
    if (paramDownloadTask.getTaskRunRightNow())
    {
      paramDownloadTask.setStatusInner((byte)0);
      return;
    }
    if ((Apn.isNetworkConnected()) && (!paramDownloadTask.isHidden()))
    {
      if ((!Apn.isFreeWifi()) && (!QueenConfig.isQueenEnable()))
      {
        if (!paramDownloadTask.isHidden()) {
          paramDownloadTask.setPausedByNoWifi(true, false);
        }
        paramDownloadTask.setStatusInner((byte)6);
        if (jdField_a_of_type_ComTencentDownloadproviderB$b != null)
        {
          FLogger.d("ZHNotify", "notify dialog 1.");
          jdField_a_of_type_ComTencentDownloadproviderB$b.a(paramDownloadTask);
        }
        FLogger.d("ZHNotify", "notify dialog 2.");
        return;
      }
      if (jdField_a_of_type_ComTencentDownloadproviderB$c != null)
      {
        FLogger.d("ZHNotify", "notify dialog 3.");
        jdField_a_of_type_ComTencentDownloadproviderB$c.a(paramDownloadTask);
      }
      paramDownloadTask.setStatusInner((byte)0);
      return;
    }
    if (!paramDownloadTask.isHidden()) {
      paramDownloadTask.setPausedByNoWifi(true, false);
    }
    paramDownloadTask.setStatusInner((byte)6);
  }
  
  public boolean a(a parama)
  {
    d locald = this.jdField_a_of_type_ComTencentDownloadproviderD;
    if (locald != null)
    {
      locald.a(1);
      parama = Message.obtain(this.jdField_a_of_type_ComTencentDownloadproviderD.a(), 1, parama);
      this.jdField_a_of_type_ComTencentDownloadproviderD.a(parama);
      return true;
    }
    return false;
  }
  
  public static abstract interface a
  {
    public abstract void a(DownloadTask paramDownloadTask);
  }
  
  public static abstract interface b
  {
    public abstract void a();
    
    public abstract void a(DownloadTask paramDownloadTask);
  }
  
  public static abstract interface c
  {
    public abstract void a(DownloadTask paramDownloadTask);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\downloadprovider\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */