package com.tencent.mtt.browser.download.engine;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.http.Apn;
import com.tencent.common.http.QueenConfig;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.Md5Utils;
import com.tencent.downloadprovider.DownloadproviderHelper;
import com.tencent.downloadprovider.d;
import com.tencent.mtt.base.task.Task;
import com.tencent.mtt.base.task.TaskObserver;
import com.tencent.mtt.base.utils.DLMttFileUtils;
import com.tencent.mtt.base.utils.DLPackageUtils;
import com.tencent.mtt.base.utils.DLReporter;
import com.tencent.mtt.base.utils.DLReporterManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DownloadTaskManager
  implements TaskObserver
{
  static DownloadTaskManager jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
  int jdField_a_of_type_Int;
  Context jdField_a_of_type_AndroidContentContext;
  d jdField_a_of_type_ComTencentDownloadproviderD;
  DownloadTask jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = null;
  Timer jdField_a_of_type_JavaUtilTimer;
  ConcurrentLinkedQueue<DownloadTask.Worker> jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue;
  boolean jdField_a_of_type_Boolean = true;
  int jdField_b_of_type_Int;
  DownloadTask jdField_b_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = null;
  int jdField_c_of_type_Int = 0;
  DownloadTask jdField_c_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = null;
  int jdField_d_of_type_Int = 0;
  DownloadTask jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = null;
  int e = 0;
  
  DownloadTaskManager(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue = new ConcurrentLinkedQueue();
    g();
    this.jdField_b_of_type_Int = 2;
    DownloadTask.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager = this;
  }
  
  private void g()
  {
    this.jdField_a_of_type_Int = 2;
  }
  
  public static ArrayList<File> getVideoDownloadFiles(String paramString1, String paramString2, String paramString3)
  {
    Object localObject1 = new File(paramString1);
    ArrayList localArrayList = new ArrayList();
    if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)))
    {
      if (TextUtils.isEmpty(paramString3)) {
        return localArrayList;
      }
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(paramString2);
      ((StringBuilder)localObject2).append(".qbdltmp");
      localObject2 = new File(paramString1, ((StringBuilder)localObject2).toString());
      if (((File)localObject2).exists()) {
        localArrayList.add(localObject2);
      }
      paramString2 = new File(paramString1, paramString2);
      if (paramString2.exists()) {
        localArrayList.add(paramString2);
      }
      if ((((File)localObject1).exists()) && (((File)localObject1).isDirectory()))
      {
        paramString2 = new StringBuilder();
        paramString2.append(paramString1);
        paramString2.append("/.");
        paramString2.append(Md5Utils.getMD5(paramString3));
        paramString2 = paramString2.toString();
        paramString1 = new File(paramString2);
        if ((paramString1.exists()) && (paramString1.isDirectory()))
        {
          paramString2 = new File(paramString2, "config.dat");
          if (paramString2.exists()) {
            localArrayList.add(paramString2);
          }
          paramString2 = paramString1.listFiles();
          if (paramString2 == null) {
            return localArrayList;
          }
          int j = paramString2.length;
          int i = 0;
          while (i < j)
          {
            paramString3 = paramString2[i];
            if (!paramString3.isDirectory())
            {
              localObject1 = paramString3.getName();
              if ((((String)localObject1).toLowerCase().endsWith(".ts")) || (((String)localObject1).toLowerCase().endsWith(".qbdltmp")) || (((String)localObject1).toLowerCase().endsWith(".m3u8")) || (((String)localObject1).toLowerCase().endsWith(".lm3u8")) || (((String)localObject1).toLowerCase().endsWith(".seg"))) {
                localArrayList.add(paramString3);
              }
            }
            i += 1;
          }
          localArrayList.add(paramString1);
        }
      }
      return localArrayList;
    }
    return localArrayList;
  }
  
  public static DownloadTaskManager newInstance(Context paramContext)
  {
    try
    {
      if (jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager == null) {
        jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager = new DownloadTaskManager(paramContext);
      }
      paramContext = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      return paramContext;
    }
    finally {}
  }
  
  DownloadTask a(int paramInt)
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.iterator();
    while (localIterator.hasNext())
    {
      DownloadTask.Worker localWorker = (DownloadTask.Worker)localIterator.next();
      if (localWorker.isWorkerForTask(paramInt)) {
        return localWorker.getTask();
      }
    }
    return null;
  }
  
  void a()
  {
    f();
    this.jdField_c_of_type_Int = 0;
    this.jdField_d_of_type_Int = 0;
    this.e = 0;
    Iterator localIterator = this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.iterator();
    while (localIterator.hasNext())
    {
      DownloadTask localDownloadTask = ((DownloadTask.Worker)localIterator.next()).getTask();
      if (localDownloadTask != null) {
        if (!localDownloadTask.isHidden())
        {
          this.jdField_c_of_type_Int += 1;
        }
        else if (localDownloadTask.isBackAutoTask())
        {
          this.e += 1;
          this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = localDownloadTask;
        }
        else
        {
          this.jdField_d_of_type_Int += 1;
        }
      }
    }
  }
  
  void a(DownloadTask paramDownloadTask)
  {
    if ((!paramDownloadTask.isHidden()) && (paramDownloadTask.getStatus() == 0))
    {
      if ((paramDownloadTask.hasFlag(262144)) && (this.jdField_b_of_type_ComTencentMttBrowserDownloadEngineDownloadTask == null)) {
        this.jdField_b_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = paramDownloadTask;
      }
      if ((!paramDownloadTask.hasFlag(262144)) && (this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask == null)) {
        this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = paramDownloadTask;
      }
    }
    if ((paramDownloadTask.isHidden()) && (paramDownloadTask.getStatus() == 0)) {
      if (paramDownloadTask.isBackAutoTask())
      {
        if (this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask == null) {
          this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = paramDownloadTask;
        }
      }
      else if (this.jdField_c_of_type_ComTencentMttBrowserDownloadEngineDownloadTask == null) {
        this.jdField_c_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = paramDownloadTask;
      }
    }
  }
  
  boolean a()
  {
    try
    {
      if ((this.jdField_a_of_type_Boolean) && (this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask != null) && ((Apn.isFreeWifi()) || (QueenConfig.isQueenEnable())) && (this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.isEmpty()))
      {
        resumeTask(this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask);
        return true;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return false;
  }
  
  boolean a(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    f();
    Iterator localIterator = this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.iterator();
    DownloadTask.Worker localWorker;
    boolean bool;
    while (localIterator.hasNext())
    {
      localWorker = (DownloadTask.Worker)localIterator.next();
      if (localWorker.isWorkerForTask(paramInt)) {
        bool = true;
      }
    }
    try
    {
      localIterator.remove();
      if (paramBoolean1) {
        localWorker.getTask().cancel();
      } else {
        localWorker.getTask().cancel();
      }
      HttpFileWriter.getInstance().removeDownloadingTask(localWorker.getTask(), false);
      if (paramBoolean2) {
        localWorker.getTask().deleteCfgFile();
      }
      paramBoolean1 = bool;
      if (this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.size() == 0)
      {
        e();
        return true;
        paramBoolean1 = false;
      }
      return paramBoolean1;
    }
    catch (Exception localException) {}
    return true;
  }
  
  void b()
  {
    f();
    try
    {
      if ((this.jdField_a_of_type_Boolean) && (this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask != null))
      {
        this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.setStatus((byte)11);
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  void b(DownloadTask paramDownloadTask)
  {
    f();
    if (paramDownloadTask == null) {
      return;
    }
    Object localObject = DLReporterManager.getReporter(paramDownloadTask.getDownloadTaskId());
    if (localObject != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("(C1:");
      localStringBuilder.append(((DLReporter)localObject).calDeltTime());
      localStringBuilder.append(")");
      ((DLReporter)localObject).addStep(localStringBuilder.toString());
    }
    paramDownloadTask.addObserver(this);
    paramDownloadTask.addObserver(DownloadEventManager.getInstance().downloadEventTaskObserver());
    paramDownloadTask.setStatus((byte)1);
    localObject = paramDownloadTask.getWorker();
    HttpFileWriter.getInstance().addDownloadingTask(paramDownloadTask);
    this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.add(localObject);
    ((DownloadTask.Worker)localObject).startTaskRun();
    d();
  }
  
  void c()
  {
    f();
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = null;
    this.jdField_b_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = null;
    this.jdField_c_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = null;
    Object localObject1 = DownloadproviderHelper.c();
    Object localObject2 = ((List)localObject1).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      DownloadTask localDownloadTask = (DownloadTask)((Iterator)localObject2).next();
      if (localDownloadTask.getExtFlagCreateFirst())
      {
        localDownloadTask.setExtFlagCreateFirst(false, true);
        a(localDownloadTask);
        ((Iterator)localObject2).remove();
      }
    }
    localObject1 = ((List)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (DownloadTask)((Iterator)localObject1).next();
      if ((((DownloadTask)localObject2).getTaskRunRightNow()) && (((DownloadTask)localObject2).getStatus() == 0)) {
        b((DownloadTask)localObject2);
      } else {
        a((DownloadTask)localObject2);
      }
    }
  }
  
  public void cancelAllTask()
  {
    f();
    Iterator localIterator = this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.iterator();
    for (;;)
    {
      Object localObject;
      if (localIterator.hasNext()) {
        localObject = (DownloadTask.Worker)localIterator.next();
      }
      try
      {
        localIterator.remove();
        localObject = ((DownloadTask.Worker)localObject).getTask();
        ((DownloadTask)localObject).cancel(false);
        HttpFileWriter.getInstance().removeDownloadingTask((DownloadTask)localObject, false);
        if (this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.size() != 0) {
          continue;
        }
        e();
      }
      catch (Exception localException) {}
      return;
    }
  }
  
  void d()
  {
    if (this.jdField_a_of_type_JavaUtilTimer == null)
    {
      this.jdField_a_of_type_JavaUtilTimer = new Timer("DownloadTaskManager", true);
      this.jdField_a_of_type_JavaUtilTimer.schedule(new TimerTask()
      {
        public void run()
        {
          ArrayList localArrayList = new ArrayList();
          Iterator localIterator = DownloadTaskManager.this.a.iterator();
          while (localIterator.hasNext())
          {
            DownloadTask localDownloadTask = ((DownloadTask.Worker)localIterator.next()).getTask();
            if (localDownloadTask.getStatus() == 2)
            {
              localDownloadTask.updateCostTime();
              localDownloadTask.f = System.currentTimeMillis();
              localDownloadTask.accumulateSpeedData();
              localDownloadTask.fireObserverEvent(localDownloadTask.mStatus);
              ContentValues localContentValues = new ContentValues();
              localContentValues.put("id", Integer.valueOf(localDownloadTask.getDownloadTaskId()));
              localContentValues.put("downloadedsize", Long.valueOf(localDownloadTask.mDownloadedSize));
              localContentValues.put("extend_5", Integer.valueOf(localDownloadTask.mPercent));
              Object localObject = localDownloadTask.getSectionData();
              DownloadSection localDownloadSection;
              if (((DownloadSections)localObject).size() > 0)
              {
                localDownloadSection = ((DownloadSections)localObject).getSection(0);
                localContentValues.put("startpos1", localDownloadSection.getSectionStartPos());
                localContentValues.put("endpos1", localDownloadSection.getSectionEndPos());
                localContentValues.put("writepos1", localDownloadSection.getSectionWriteLen());
              }
              else
              {
                localContentValues.put("startpos1", Integer.valueOf(0));
                localContentValues.put("endpos1", Integer.valueOf(-1));
                localContentValues.put("writepos1", Integer.valueOf(0));
              }
              if (((DownloadSections)localObject).size() > 1)
              {
                localDownloadSection = ((DownloadSections)localObject).getSection(1);
                localContentValues.put("startpos2", localDownloadSection.getSectionStartPos());
                localContentValues.put("endpos2", localDownloadSection.getSectionEndPos());
                localContentValues.put("writepos2", localDownloadSection.getSectionWriteLen());
              }
              else
              {
                localContentValues.put("startpos2", Integer.valueOf(0));
                localContentValues.put("endpos2", Integer.valueOf(-1));
                localContentValues.put("writepos2", Integer.valueOf(0));
              }
              if (((DownloadSections)localObject).size() > 2)
              {
                localObject = ((DownloadSections)localObject).getSection(2);
                localContentValues.put("startpos3", ((DownloadSection)localObject).getSectionStartPos());
                localContentValues.put("endpos3", ((DownloadSection)localObject).getSectionEndPos());
                localContentValues.put("writepos3", ((DownloadSection)localObject).getSectionWriteLen());
              }
              else
              {
                localContentValues.put("startpos3", Integer.valueOf(0));
                localContentValues.put("endpos3", Integer.valueOf(-1));
                localContentValues.put("writepos3", Integer.valueOf(0));
              }
              localContentValues.put("downloadsize", Long.valueOf(localDownloadTask.getWrittenSize()));
              localArrayList.add(localContentValues);
            }
          }
          DownloadproviderHelper.a(localArrayList);
          HttpFileWriter.getInstance().tellWriteThreadWriteFile();
        }
      }, 0L, 1000L);
    }
  }
  
  public DownloadTask deleteTask(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    f();
    if (paramInt == -1) {
      return null;
    }
    DownloadTask localDownloadTask1 = DownloadproviderHelper.a(paramInt);
    if (localDownloadTask1 != null)
    {
      FLogger.d("DownloadTaskManager", "task != null");
      a(paramInt, true, true);
      synchronized (localDownloadTask1.mReadWriteProgressLock)
      {
        localDownloadTask1.deleteCfgFile();
        DLMttFileUtils.deleteDownloadTypeIconFile(localDownloadTask1.getFileFolderPath(), localDownloadTask1.getFileName());
        if (paramBoolean1)
        {
          if ((!localDownloadTask1.isM3U8()) && (!localDownloadTask1.isMp4()))
          {
            DLMttFileUtils.deleteDownloadFile(localDownloadTask1.getFileFolderPath(), localDownloadTask1.getFileName());
          }
          else
          {
            ??? = getVideoDownloadFiles(localDownloadTask1.getFileFolderPath(), localDownloadTask1.getFileName(), localDownloadTask1.getTaskUrl());
            int i = 0;
            while (i < ((ArrayList)???).size())
            {
              FileUtilsF.deleteFileOnThread((File)((ArrayList)???).get(i));
              i += 1;
            }
          }
        }
        else {
          DLMttFileUtils.deleteDownloadTempFile(localDownloadTask1.getFileFolderPath(), localDownloadTask1.getFileName());
        }
        if (paramBoolean2)
        {
          FLogger.d("ZAYTAG", "real delete task");
          DownloadTask.sStatusLocks.remove(Integer.valueOf(paramInt));
          DownloadproviderHelper.a(paramInt);
          DownloadEventManager.getInstance().notifyTaskDeleted(localDownloadTask1);
          return localDownloadTask1;
        }
      }
    }
    return localDownloadTask2;
  }
  
  void e()
  {
    Timer localTimer = this.jdField_a_of_type_JavaUtilTimer;
    if (localTimer != null)
    {
      localTimer.cancel();
      this.jdField_a_of_type_JavaUtilTimer = null;
    }
  }
  
  public boolean execute(int paramInt)
  {
    f();
    a();
    c();
    DLReporter localDLReporter = DLReporterManager.getReporter(paramInt);
    if (localDLReporter != null) {
      localDLReporter.addStep(":I[");
    }
    Object localObject;
    if (this.jdField_c_of_type_Int < this.jdField_a_of_type_Int)
    {
      localObject = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask;
      if (localObject != null)
      {
        b((DownloadTask)localObject);
        this.jdField_c_of_type_Int += 1;
        if (localDLReporter != null) {
          localDLReporter.addStep("-1");
        }
        bool2 = true;
        i = 1;
        break label97;
      }
    }
    boolean bool2 = false;
    int i = 0;
    label97:
    boolean bool1 = bool2;
    paramInt = i;
    if (this.jdField_c_of_type_Int < this.jdField_a_of_type_Int)
    {
      localObject = this.jdField_b_of_type_ComTencentMttBrowserDownloadEngineDownloadTask;
      bool1 = bool2;
      paramInt = i;
      if (localObject != null)
      {
        b((DownloadTask)localObject);
        this.jdField_c_of_type_Int += 1;
        if (localDLReporter != null) {
          localDLReporter.addStep("-2");
        }
        bool1 = true;
        paramInt = 1;
      }
    }
    bool2 = bool1;
    i = paramInt;
    if (this.jdField_d_of_type_Int < this.jdField_b_of_type_Int)
    {
      localObject = this.jdField_c_of_type_ComTencentMttBrowserDownloadEngineDownloadTask;
      bool2 = bool1;
      i = paramInt;
      if (localObject != null)
      {
        b((DownloadTask)localObject);
        this.jdField_d_of_type_Int += 1;
        if (localDLReporter != null) {
          localDLReporter.addStep("-3");
        }
        bool2 = true;
        i = 1;
      }
    }
    bool1 = bool2;
    if (this.jdField_c_of_type_Int == 0)
    {
      bool1 = bool2;
      if (this.jdField_d_of_type_Int == 0)
      {
        bool1 = bool2;
        if (this.e == 0)
        {
          bool1 = bool2;
          try
          {
            if (this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask != null) {
              if (!Apn.isFreeWifi())
              {
                bool1 = bool2;
                if (!QueenConfig.isQueenEnable()) {}
              }
              else
              {
                if (this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.isCanceled())
                {
                  localObject = new StringBuilder();
                  ((StringBuilder)localObject).append("start AutoBackTask=");
                  ((StringBuilder)localObject).append(this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getTaskUrl());
                  FLogger.d("DownloadTaskManager", ((StringBuilder)localObject).toString());
                  resumeTask(this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask);
                  this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.setCancelled(false);
                  if (localDLReporter != null) {
                    localDLReporter.addStep("-4");
                  }
                }
                else
                {
                  localObject = new StringBuilder();
                  ((StringBuilder)localObject).append("start AutoBackTask=");
                  ((StringBuilder)localObject).append(this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getTaskUrl());
                  FLogger.d("DownloadTaskManager", ((StringBuilder)localObject).toString());
                  b(this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask);
                  if (localDLReporter != null) {
                    localDLReporter.addStep("-5");
                  }
                }
                this.e += 1;
                bool1 = true;
              }
            }
          }
          catch (Throwable localThrowable)
          {
            localThrowable.printStackTrace();
            bool1 = bool2;
          }
        }
      }
    }
    bool2 = bool1;
    if (i != 0)
    {
      bool2 = bool1;
      if (this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("pause AutoBackTask=");
        localStringBuilder.append(this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getDownloadTaskId());
        FLogger.d("DownloadTaskManager", localStringBuilder.toString());
        b();
        if (localDLReporter != null) {
          localDLReporter.addStep("-6");
        }
        bool2 = true;
      }
    }
    if (localDLReporter != null) {
      localDLReporter.addStep("-7]");
    }
    return bool2;
  }
  
  void f()
  {
    if (Thread.currentThread().getName().equals("down_schedule")) {
      return;
    }
    FLogger.d("ZHTAG", "this method can only be called in down_schedule.");
    throw new RuntimeException("this method can only be called in down_schedule.");
  }
  
  public void foregrandBackAutoRunningTask(int paramInt)
  {
    DownloadTask localDownloadTask1 = DownloadproviderHelper.b(paramInt);
    int i = 1;
    if ((localDownloadTask1 != null) && (localDownloadTask1.StatusIsBackAutoTask()))
    {
      localDownloadTask1.setHidden(false, true);
      localDownloadTask1.setIsBackAutoTask(false, true);
    }
    else
    {
      i = 0;
    }
    try
    {
      if ((this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask != null) && (this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getDownloadTaskId() == paramInt)) {
        this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = null;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    if (i == 0)
    {
      DownloadTask localDownloadTask2 = DownloadproviderHelper.a(paramInt);
      if (localDownloadTask2 != null)
      {
        localDownloadTask2.setHidden(false, false);
        localDownloadTask2.setIsBackAutoTask(false, false);
        DownloadproviderHelper.a(localDownloadTask2);
      }
    }
  }
  
  public List<DownloadTask> getNotCompletedTaskList(boolean paramBoolean)
  {
    LinkedList localLinkedList = new LinkedList();
    Iterator localIterator = DownloadproviderHelper.c().iterator();
    while (localIterator.hasNext())
    {
      DownloadTask localDownloadTask = (DownloadTask)localIterator.next();
      if (localDownloadTask.isHidden() == paramBoolean) {
        localLinkedList.add(localDownloadTask);
      }
    }
    return localLinkedList;
  }
  
  public void initDownloadNum(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public void onTaskCompleted(final Task paramTask)
  {
    FLogger.d("ZHTAG", "task manager, onTaskCompleted");
    this.jdField_a_of_type_ComTencentDownloadproviderD.a(new Runnable()
    {
      public void run()
      {
        FLogger.d("DownloadTaskManager", "onTaskCompleted()");
        Object localObject1 = (DownloadTask)paramTask;
        boolean bool = DownloadTaskManager.this.taskCompleted((DownloadTask)localObject1, true);
        if (!bool)
        {
          ((DownloadTask)localObject1).setStatusWithoutDB((byte)11);
          ((DownloadTask)localObject1).setIsNeedRestartTask(true);
          DownloadproviderHelper.a((DownloadTask)localObject1);
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("boolean bNeedRetry = mTaskManager.taskCompleted(dt, true)=");
          ((StringBuilder)localObject1).append(bool);
          FLogger.d("DownloadTaskManager", ((StringBuilder)localObject1).toString());
          return;
        }
        ((DownloadTask)localObject1).setDoneTime(System.currentTimeMillis(), false);
        Object localObject2 = DownloadproviderHelper.a(((DownloadTask)localObject1).getTaskUrl());
        if ((localObject2 != null) && ((((DownloadTask)localObject2).getStatus() == 8) || (((DownloadTask)localObject2).getStatus() == 9))) {
          ((DownloadTask)localObject1).setStatusWithoutDB(((DownloadTask)localObject2).getStatus());
        }
        DownloadproviderHelper.a((DownloadTask)localObject1, true);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("file://");
        ((StringBuilder)localObject2).append(((DownloadTask)localObject1).getFileFolderPath());
        ((StringBuilder)localObject2).append("/");
        ((StringBuilder)localObject2).append(((DownloadTask)localObject1).getFileName());
        localObject1 = Uri.parse(((StringBuilder)localObject2).toString());
        DownloadTaskManager.this.a.getApplicationContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", (Uri)localObject1));
      }
    });
  }
  
  public void onTaskCreated(Task paramTask) {}
  
  public void onTaskExtEvent(Task paramTask)
  {
    DownloadproviderHelper.a((DownloadTask)paramTask);
  }
  
  public void onTaskFailed(final Task paramTask)
  {
    this.jdField_a_of_type_ComTencentDownloadproviderD.a(new Runnable()
    {
      public void run()
      {
        if (paramTask.getStatus() != 5) {
          return;
        }
        DownloadTask localDownloadTask = (DownloadTask)paramTask;
        DownloadTaskManager.this.taskCompleted(localDownloadTask, false);
        DownloadproviderHelper.a(localDownloadTask, true);
      }
    });
  }
  
  public void onTaskProgress(Task paramTask) {}
  
  public void onTaskStarted(Task paramTask) {}
  
  public void pauseTask(DownloadTask paramDownloadTask)
  {
    f();
    if (paramDownloadTask == null) {
      return;
    }
    Iterator localIterator = this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.iterator();
    while (localIterator.hasNext())
    {
      DownloadTask.Worker localWorker = (DownloadTask.Worker)localIterator.next();
      if (localWorker.isWorkerForTask(paramDownloadTask.getDownloadTaskId())) {
        localWorker.getTask().setFlag(paramDownloadTask.getFlag(), true);
      }
    }
    if (!a(paramDownloadTask.getDownloadTaskId(), true, false))
    {
      paramDownloadTask.setStatus((byte)6);
      DownloadEventManager.getInstance().notifyTaskCanceled(paramDownloadTask.getTaskUrl());
    }
  }
  
  public void pauseTaskFromDownloader(final DownloadTask paramDownloadTask)
  {
    this.jdField_a_of_type_ComTencentDownloadproviderD.a(new Runnable()
    {
      public void run()
      {
        DownloadTaskManager.this.pauseTask(paramDownloadTask);
        DownloadEventManager.getInstance().cancelNotification(paramDownloadTask);
      }
    });
  }
  
  public DownloadTask resumeTask(DownloadTask paramDownloadTask)
  {
    if (paramDownloadTask == null) {
      return null;
    }
    paramDownloadTask.setPausedByNoWifi(false, true);
    paramDownloadTask.setStatus((byte)0);
    return paramDownloadTask;
  }
  
  public void setScheduleHandler(d paramd)
  {
    this.jdField_a_of_type_ComTencentDownloadproviderD = paramd;
  }
  
  public boolean taskCompleted(DownloadTask paramDownloadTask, boolean paramBoolean)
  {
    f();
    if (!paramDownloadTask.isCanceled())
    {
      FLogger.d("DownloadTaskManager", "Worker - Task not cancelled.");
      Object localObject1 = this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.iterator();
      Object localObject2;
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (DownloadTask.Worker)((Iterator)localObject1).next();
        if (((DownloadTask.Worker)localObject2).isWorkerForTask(paramDownloadTask.getDownloadTaskId()))
        {
          HttpFileWriter.getInstance().removeDownloadingTask(((DownloadTask.Worker)localObject2).getTask(), true);
          paramDownloadTask.removeObserver(DownloadEventManager.getInstance().downloadEventTaskObserver());
          paramDownloadTask.removeObserver(this);
          paramDownloadTask.cancel(false);
          ((Iterator)localObject1).remove();
          if (this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.size() == 0) {
            e();
          }
        }
      }
      if (this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask != null) {
        if (paramDownloadTask.getDownloadTaskId() != this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getDownloadTaskId()) {
          a();
        } else {
          this.jdField_d_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = null;
        }
      }
      if ((paramBoolean) && (paramDownloadTask.isNeedCheckAfterDownload()))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(paramDownloadTask.getFileFolderPath());
        ((StringBuilder)localObject1).append("/");
        ((StringBuilder)localObject1).append(paramDownloadTask.getFileName());
        localObject1 = ((StringBuilder)localObject1).toString();
        if (TextUtils.isEmpty((CharSequence)localObject1)) {
          return false;
        }
        try
        {
          localObject2 = DLPackageUtils.getSignMd5((String)localObject1);
          localObject1 = paramDownloadTask.getAnnotation();
          if ((!TextUtils.isEmpty((CharSequence)localObject1)) && (((String)localObject1).equals(localObject2)))
          {
            if ((paramDownloadTask.isBackAutoTask()) && (paramDownloadTask.isHidden()))
            {
              localObject2 = new File(paramDownloadTask.getFileFolderPath(), (String)localObject1);
              if (((File)localObject2).exists()) {
                ((File)localObject2).delete();
              }
              FileUtilsF.renameTo(new File(paramDownloadTask.getFileFolderPath(), paramDownloadTask.getFileName()), (File)localObject2);
              paramDownloadTask.setFileName((String)localObject1, true);
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("backAutoTask rename=");
              ((StringBuilder)localObject1).append(paramDownloadTask.getFileName());
              FLogger.d("DownloadTaskManager", ((StringBuilder)localObject1).toString());
            }
            return true;
          }
          return false;
        }
        catch (Exception paramDownloadTask)
        {
          paramDownloadTask.printStackTrace();
          return false;
        }
      }
    }
    return true;
  }
  
  public ConcurrentLinkedQueue<DownloadTask.Worker> workerList()
  {
    return this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DownloadTaskManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */