package com.tencent.downloadprovider;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler.Callback;
import android.os.Message;
import com.tencent.basesupport.FLogger;
import com.tencent.mtt.base.utils.DLReporter;
import com.tencent.mtt.base.utils.DLReporterManager;
import com.tencent.mtt.browser.download.engine.DownloadDataBuffer;
import com.tencent.mtt.browser.download.engine.DownloadOperation;
import com.tencent.mtt.browser.download.engine.DownloadTask;
import com.tencent.mtt.browser.download.engine.DownloadTask.Worker;
import com.tencent.mtt.browser.download.engine.DownloadTaskManager;
import com.tencent.mtt.browser.download.engine.HttpFileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class c
  implements Handler.Callback
{
  private Context jdField_a_of_type_AndroidContentContext = null;
  private DownloadTaskManager jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
  
  public c(Context paramContext, DownloadTaskManager paramDownloadTaskManager)
  {
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager = paramDownloadTaskManager;
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    Object localObject2;
    if ((paramMessage.obj instanceof a))
    {
      localObject1 = (a)paramMessage.obj;
      long l1 = System.currentTimeMillis();
      long l2 = ((a)localObject1).jdField_a_of_type_Long;
      localObject2 = DLReporterManager.getReporter(((a)localObject1).jdField_a_of_type_Int);
      paramMessage = (Message)localObject1;
      if (localObject2 != null)
      {
        paramMessage = new StringBuilder();
        paramMessage.append("<S1:(u:");
        paramMessage.append(l1 - l2);
        paramMessage.append(",");
        paramMessage.append(((a)localObject1).jdField_a_of_type_ComTencentDownloadproviderA$a);
        paramMessage.append(",");
        paramMessage.append(((DLReporter)localObject2).calDeltTime());
        paramMessage.append(")>");
        ((DLReporter)localObject2).addStep(paramMessage.toString());
        paramMessage = (Message)localObject1;
      }
    }
    else
    {
      paramMessage = null;
    }
    Object localObject1 = DownloadproviderHelper.b();
    if (((List)localObject1).isEmpty())
    {
      DownloadDataBuffer.GCAllBuffer();
      return true;
    }
    localObject1 = ((List)localObject1).iterator();
    boolean bool = true;
    for (;;)
    {
      if ((localObject1 == null) || (!((Iterator)localObject1).hasNext())) {
        break label911;
      }
      localObject2 = (DownloadTask)((Iterator)localObject1).next();
      Object localObject3;
      Object localObject4;
      if ((paramMessage != null) && (((DownloadTask)localObject2).getDownloadTaskId() == paramMessage.jdField_a_of_type_Int))
      {
        localObject3 = DLReporterManager.getReporter(paramMessage.jdField_a_of_type_Int);
        if (localObject3 != null)
        {
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("<S2:(t:");
          ((StringBuilder)localObject4).append(((DownloadTask)localObject2).getStatus());
          ((StringBuilder)localObject4).append(",");
          ((StringBuilder)localObject4).append(paramMessage.jdField_a_of_type_ComTencentDownloadproviderA$a);
          ((StringBuilder)localObject4).append(",");
          ((StringBuilder)localObject4).append(((DLReporter)localObject3).calDeltTime());
          ((StringBuilder)localObject4).append(")>");
          ((DLReporter)localObject3).addStep(((StringBuilder)localObject4).toString());
        }
      }
      int i = ((DownloadTask)localObject2).getStatus();
      if (i != 0)
      {
        if (i != 6) {
          switch (i)
          {
          default: 
            break;
          case 12: 
            FLogger.d("ZHTAG", "task foregrand with file by Task scheduler");
            this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager.foregrandBackAutoRunningTask(((DownloadTask)localObject2).getDownloadTaskId());
            break;
          case 11: 
            FLogger.d("ZHTAG", "task canceling by Task scheduler");
            this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager.pauseTask((DownloadTask)localObject2);
            break;
          case 10: 
            FLogger.d("ZHTAG", "task restarted by Task scheduler");
            this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager.deleteTask(((DownloadTask)localObject2).getDownloadTaskId(), true, false);
            ((DownloadTask)localObject2).deleteCfgFile();
            FLogger.d("ZHTAG", "task restarted by Task scheduler after");
            ((DownloadTask)localObject2).setStatus((byte)0);
            break;
          case 9: 
            FLogger.d("ZHTAG", "task delete with file by Task scheduler");
            this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager.deleteTask(((DownloadTask)localObject2).getDownloadTaskId(), true, true);
            break;
          case 8: 
            FLogger.d("ZHTAG", "task delete by Task scheduler");
            this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager.deleteTask(((DownloadTask)localObject2).getDownloadTaskId(), false, true);
            break;
          }
        }
        if (((DownloadTask)localObject2).isBackAutoTask())
        {
          FLogger.d("ZHTAG", "back ground task");
          localObject3 = DLReporterManager.getReporter(((DownloadTask)localObject2).getDownloadTaskId());
          if (localObject3 != null)
          {
            localObject4 = new StringBuilder();
            ((StringBuilder)localObject4).append("<S4:(");
            ((StringBuilder)localObject4).append(((DLReporter)localObject3).calDeltTime());
            ((StringBuilder)localObject4).append(")>");
            ((DLReporter)localObject3).addStep(((StringBuilder)localObject4).toString());
          }
          this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager.execute(((DownloadTask)localObject2).getDownloadTaskId());
        }
        if (((DownloadTask)localObject2).mDownloadOps.mOperationCmd == 1)
        {
          localObject3 = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager.workerList().iterator();
          if (!((Iterator)localObject3).hasNext()) {
            continue;
          }
          localObject4 = (DownloadTask.Worker)((Iterator)localObject3).next();
          if (!((DownloadTask.Worker)localObject4).isWorkerForTask(((DownloadTask)localObject2).getDownloadTaskId())) {
            break;
          }
          ((DownloadTask.Worker)localObject4).getTask().mDownloadOps.init(((DownloadTask)localObject2).mDownloadOps.mOperationCmd, ((DownloadTask)localObject2).mDownloadOps.mValue);
          HttpFileWriter.getInstance().renameTaskFileName(((DownloadTask.Worker)localObject4).getTask());
          continue;
        }
        if (((DownloadTask)localObject2).mDownloadOps.mOperationCmd != 2) {
          continue;
        }
        FLogger.d("hijack", "retry url");
        localObject3 = new ContentValues();
        ((ContentValues)localObject3).put("id", Integer.valueOf(((DownloadTask)localObject2).getDownloadTaskId()));
        ((ContentValues)localObject3).put("download_operations", "");
        DownloadproviderHelper.a((ContentValues)localObject3, true);
        localObject3 = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager.workerList().iterator();
        if (!((Iterator)localObject3).hasNext()) {
          continue;
        }
        localObject4 = (DownloadTask.Worker)((Iterator)localObject3).next();
        if (!((DownloadTask.Worker)localObject4).isWorkerForTask(((DownloadTask)localObject2).getDownloadTaskId())) {
          break;
        }
        if (!((DownloadTask.Worker)localObject4).getTask().canRetry()) {
          continue;
        }
        FLogger.d("hijack", "real retry url");
        ((DownloadTask.Worker)localObject4).getTask().retryWithNewUrl();
        continue;
      }
      FLogger.d("ZHTAG", "task created by Task scheduler");
      if (bool)
      {
        localObject3 = DLReporterManager.getReporter(((DownloadTask)localObject2).getDownloadTaskId());
        if (localObject3 != null)
        {
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("<S3:(");
          ((StringBuilder)localObject4).append(((DLReporter)localObject3).calDeltTime());
          ((StringBuilder)localObject4).append(")>");
          ((DLReporter)localObject3).addStep(((StringBuilder)localObject4).toString());
        }
        bool = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager.execute(((DownloadTask)localObject2).getDownloadTaskId());
      }
    }
    label911:
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\downloadprovider\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */