package com.tencent.tbs.common.download;

import android.content.ContentValues;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.basesupport.FLogger;
import com.tencent.common.http.Apn;
import com.tencent.common.http.QueenConfig;
import com.tencent.downloadprovider.DownloadproviderHelper;
import com.tencent.downloadprovider.b;
import com.tencent.mtt.ContextHolder;
import com.tencent.mtt.base.task.Task;
import com.tencent.mtt.base.task.TaskObserver;
import com.tencent.mtt.base.utils.DLMttFileUtils;
import com.tencent.mtt.base.utils.DLReporter;
import com.tencent.mtt.base.utils.DLReporterManager;
import com.tencent.mtt.browser.download.engine.DownloadEventManager;
import com.tencent.mtt.browser.download.engine.DownloadEventManager.DownloadEventTaskObserver;
import com.tencent.mtt.browser.download.engine.DownloadEventManager.ExtNotififyListener;
import com.tencent.mtt.browser.download.engine.DownloadInfo;
import com.tencent.mtt.browser.download.engine.DownloadInfo.DownloadTaskConfirmObserver;
import com.tencent.mtt.browser.download.engine.DownloadOperation;
import com.tencent.mtt.browser.download.engine.DownloadTask;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class BaseDownloadManager
  implements TaskObserver, DownloadEventManager.ExtNotififyListener, IDownloadManager
{
  private static BaseDownloadManager mInstance;
  private final List<OnDownloadedTaskListener> mDownloadedTaskListener = new LinkedList();
  
  private BaseDownloadManager()
  {
    DownloadEventManager.getInstance().addTaskObserver(this);
    DownloadEventManager.getInstance().addDownloadedTaskListener(this);
    b.a().a(ContextHolder.getAppContext());
  }
  
  public static BaseDownloadManager getInstance()
  {
    try
    {
      if (mInstance == null) {
        mInstance = new BaseDownloadManager();
      }
      BaseDownloadManager localBaseDownloadManager = mInstance;
      return localBaseDownloadManager;
    }
    finally {}
  }
  
  public void addDownloadedTaskListener(OnDownloadedTaskListener paramOnDownloadedTaskListener)
  {
    synchronized (this.mDownloadedTaskListener)
    {
      if (!this.mDownloadedTaskListener.contains(paramOnDownloadedTaskListener)) {
        this.mDownloadedTaskListener.add(paramOnDownloadedTaskListener);
      }
      return;
    }
  }
  
  public DownloadTask addTask(DownloadTask paramDownloadTask, boolean paramBoolean)
  {
    return addTask(paramDownloadTask, paramBoolean, null);
  }
  
  public DownloadTask addTask(DownloadTask paramDownloadTask, boolean paramBoolean, DLReporter paramDLReporter)
  {
    if (paramDLReporter != null) {
      paramDLReporter.addStep(":A[");
    }
    if ((paramDownloadTask != null) && (paramDownloadTask.getDownloadTaskId() == -1))
    {
      Object localObject = isTaskAlreadyAdded(paramDownloadTask);
      if (localObject != null)
      {
        DLReporter localDLReporter = DLReporterManager.getReporter(((DownloadTask)localObject).getDownloadTaskId());
        if ((localDLReporter != null) && (paramDLReporter != null)) {
          paramDLReporter.addDLReporter(localDLReporter);
        }
        if (paramDLReporter != null) {
          paramDLReporter.addStep("-2");
        }
        if ((((DownloadTask)localObject).isHidden()) && (!paramDownloadTask.isHidden())) {
          ((DownloadTask)localObject).setHidden(false, true);
        }
        if ((((DownloadTask)localObject).isStartOnWifiTask()) && (!paramDownloadTask.isStartOnWifiTask())) {
          ((DownloadTask)localObject).setFlag(((DownloadTask)localObject).getFlag() & 0x7FFFFFFF, true);
        }
        if ((!((DownloadTask)localObject).getTaskRunRightNow()) && (paramDownloadTask.getTaskRunRightNow()))
        {
          ((DownloadTask)localObject).setFlag(((DownloadTask)localObject).getFlag() | 0x4, true);
          if (paramDLReporter != null) {
            paramDLReporter.addStep("-3");
          }
        }
        if ((!((DownloadTask)localObject).getExtFlagAutoInstall()) && (paramDownloadTask.getExtFlagAutoInstall())) {
          ((DownloadTask)localObject).setExtFlagAutoInstall(true, true);
        }
        if (paramDownloadTask.getFileFolderPath() != null) {
          ((DownloadTask)localObject).setFileFolderPath(paramDownloadTask.getFileFolderPath(), true);
        }
        if (((DownloadTask)localObject).getIsTencentDNS() != paramDownloadTask.getIsTencentDNS()) {
          ((DownloadTask)localObject).setTencentDNS(paramDownloadTask.getIsTencentDNS(), true);
        }
        if (((DownloadTask)localObject).getStatus() == 3)
        {
          if (paramDLReporter != null) {
            paramDLReporter.addStep("-4");
          }
          if (!new File(((DownloadTask)localObject).getFileFolderPath(), ((DownloadTask)localObject).getFileName()).exists())
          {
            FLogger.d("ZAYTAG", "file restart");
            if (paramDLReporter != null) {
              paramDLReporter.addStep("-5");
            }
            restartTask(((DownloadTask)localObject).getDownloadTaskId(), paramDLReporter);
          }
        }
        else
        {
          FLogger.d("ZAYTAG", "file resume");
          if (paramDLReporter != null) {
            paramDLReporter.addStep("-6");
          }
          resumeTask(((DownloadTask)localObject).getDownloadTaskId(), paramDLReporter);
        }
        if (((DownloadTask)localObject).getDownloadTaskId() == -1) {
          ((DownloadTask)localObject).mFrom = 1;
        }
        if (paramDLReporter != null)
        {
          paramDLReporter.setId(((DownloadTask)localObject).getDownloadTaskId());
          paramDLReporter.addStep("-7]");
        }
        return (DownloadTask)localObject;
      }
      if (!paramBoolean) {
        paramDownloadTask.setCreateTime(System.currentTimeMillis(), false);
      }
      int i = DownloadproviderHelper.a(paramDownloadTask, paramDLReporter);
      if (paramDLReporter != null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("-8(");
        ((StringBuilder)localObject).append(paramDLReporter.calDeltTime());
        ((StringBuilder)localObject).append(")");
        paramDLReporter.addStep(((StringBuilder)localObject).toString());
      }
      if (i != -1)
      {
        if (paramDLReporter != null) {
          paramDLReporter.addStep("-9");
        }
        DownloadEventManager.getInstance().downloadEventTaskObserver().onTaskCreated(paramDownloadTask);
      }
      else
      {
        if (i == -1) {
          paramDownloadTask.mFrom = 2;
        }
        if (paramDLReporter != null) {
          paramDLReporter.addStep("-10");
        }
      }
      if (paramDLReporter != null) {
        paramDLReporter.addStep("-11]");
      }
      paramDLReporter = new StringBuilder();
      paramDLReporter.append("startDownloadTask task added, ");
      paramDLReporter.append(paramDownloadTask.getFileName());
      FLogger.d("ZAYTAG", paramDLReporter.toString());
      return paramDownloadTask;
    }
    if (paramDLReporter != null) {
      paramDLReporter.addStep("-1]");
    }
    return null;
  }
  
  public void addTaskListener(String paramString, TaskObserver paramTaskObserver)
  {
    BusinessDownloadEventManager.getInstance().addTaskObserver(paramString, paramTaskObserver);
  }
  
  public void addTaskObserver(TaskObserver paramTaskObserver)
  {
    BusinessDownloadEventManager.getInstance().addTaskObserver(paramTaskObserver);
  }
  
  public void cancelNotification(DownloadTask paramDownloadTask) {}
  
  public DownloadTask cancelTask(int paramInt)
  {
    if (!hasInitCompleted()) {
      return null;
    }
    DownloadTask localDownloadTask = DownloadproviderHelper.a(paramInt);
    if (localDownloadTask != null) {
      localDownloadTask.setStatus((byte)11);
    }
    return localDownloadTask;
  }
  
  public void cancelTaskNoRet(int paramInt)
  {
    cancelTask(paramInt);
  }
  
  public boolean checkTaskDownloading(String paramString)
  {
    return DownloadproviderHelper.d(paramString) != null;
  }
  
  public boolean deleteTask(int paramInt, boolean paramBoolean)
  {
    return deleteTask(DownloadproviderHelper.a(paramInt), paramBoolean);
  }
  
  public boolean deleteTask(DownloadTask paramDownloadTask, boolean paramBoolean)
  {
    if (paramDownloadTask == null) {
      return false;
    }
    if (paramBoolean)
    {
      FLogger.d("ZAYTAG", "deleteTask task 1");
      paramDownloadTask.setStatus((byte)9);
    }
    else
    {
      FLogger.d("ZAYTAG", "deleteTask task 2");
      paramDownloadTask.setStatus((byte)8);
    }
    if (paramDownloadTask != null)
    {
      String str = paramDownloadTask.getFileFolderPath();
      DLMttFileUtils.deleteDownloadTypeIconFile(paramDownloadTask.getFileName(), str);
    }
    return false;
  }
  
  public boolean deleteTask(String paramString, boolean paramBoolean)
  {
    return deleteTask(DownloadproviderHelper.a(paramString), paramBoolean);
  }
  
  public boolean fireTaskSuccess(String paramString, TaskObserver paramTaskObserver)
  {
    paramString = DownloadproviderHelper.c(paramString);
    if ((paramString != null) && (paramTaskObserver != null))
    {
      paramTaskObserver.onTaskCompleted(paramString);
      return true;
    }
    return false;
  }
  
  public DownloadTask getDownloadCompletedTaskFromDatabase(String paramString)
  {
    return DownloadproviderHelper.c(paramString);
  }
  
  public File getDownloadFileByTask(DownloadTask paramDownloadTask)
  {
    if (paramDownloadTask == null) {
      return null;
    }
    String str = paramDownloadTask.getFileFolderPath();
    paramDownloadTask = paramDownloadTask.getFileName();
    if (!TextUtils.isEmpty(str))
    {
      if (TextUtils.isEmpty(paramDownloadTask)) {
        return null;
      }
      return new File(str, paramDownloadTask);
    }
    return null;
  }
  
  public DownloadTask getTask(String paramString)
  {
    return DownloadproviderHelper.a(paramString);
  }
  
  public DownloadTask getTaskByID(int paramInt)
  {
    return DownloadproviderHelper.a(paramInt);
  }
  
  public int getTaskID(String paramString)
  {
    paramString = DownloadproviderHelper.a(paramString);
    if (paramString != null) {
      return paramString.getDownloadTaskId();
    }
    return -1;
  }
  
  public boolean hasInitCompleted()
  {
    return true;
  }
  
  public void init() {}
  
  public void initDownloadNum(int paramInt)
  {
    b.a().a(paramInt);
  }
  
  DownloadTask isTaskAlreadyAdded(DownloadTask paramDownloadTask)
  {
    if (paramDownloadTask.isPostTask()) {
      return null;
    }
    paramDownloadTask = paramDownloadTask.getTaskUrl();
    if (TextUtils.isEmpty(paramDownloadTask)) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("isTaskAlreadyAdded : ");
    localStringBuilder.append(paramDownloadTask);
    FLogger.d("ZAYTAG", localStringBuilder.toString());
    return DownloadproviderHelper.b(paramDownloadTask);
  }
  
  public void notifyTaskCanceled(String paramString)
  {
    FLogger.d("ZHDEBUG", "notifyTaskCanceled");
    synchronized (this.mDownloadedTaskListener)
    {
      Iterator localIterator = this.mDownloadedTaskListener.iterator();
      while (localIterator.hasNext()) {
        ((OnDownloadedTaskListener)localIterator.next()).notifyTaskCanceled(paramString);
      }
      return;
    }
  }
  
  public void notifyTaskDeleted(DownloadTask arg1)
  {
    FLogger.d("ZHDEBUG", "notifyTaskDeleted");
    DownloadInfo localDownloadInfo = new DownloadInfo();
    localDownloadInfo.taskId = ???.getDownloadTaskId();
    localDownloadInfo.statusCache = 7;
    boolean bool;
    if (???.getStatus() == 3) {
      bool = true;
    } else {
      bool = false;
    }
    localDownloadInfo.alreadyCompleted = bool;
    localDownloadInfo.flag = ???.getFlag();
    localDownloadInfo.annotation = ???.getAnnotation();
    localDownloadInfo.fileFolderPath = ???.getFileFolderPath();
    localDownloadInfo.fileName = ???.getFileName();
    localDownloadInfo.url = ???.getTaskUrl();
    localDownloadInfo.createTime = ???.getCreateTime();
    synchronized (this.mDownloadedTaskListener)
    {
      Iterator localIterator = this.mDownloadedTaskListener.iterator();
      while (localIterator.hasNext()) {
        ((OnDownloadedTaskListener)localIterator.next()).notifyTaskDeleted(localDownloadInfo);
      }
      return;
    }
  }
  
  public void notifyTaskLength(DownloadTask paramDownloadTask, String arg2, long paramLong1, long paramLong2)
  {
    synchronized (this.mDownloadedTaskListener)
    {
      Iterator localIterator = this.mDownloadedTaskListener.iterator();
      while (localIterator.hasNext()) {
        ((OnDownloadedTaskListener)localIterator.next()).notifyTaskLength(paramDownloadTask, paramLong1, paramLong2);
      }
      return;
    }
  }
  
  public void notifyTaskPrepareStart(DownloadTask paramDownloadTask)
  {
    FLogger.d("ZHDEBUG", "notifyTaskPrepareStart");
    synchronized (this.mDownloadedTaskListener)
    {
      Iterator localIterator = this.mDownloadedTaskListener.iterator();
      while (localIterator.hasNext()) {
        ((OnDownloadedTaskListener)localIterator.next()).notifyTaskPrepareStart(paramDownloadTask);
      }
      return;
    }
  }
  
  public void notifyUser(String paramString, int paramInt) {}
  
  public void onTaskCompleted(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      DownloadTask localDownloadTask = (DownloadTask)paramTask;
      DLReporter localDLReporter = DLReporterManager.getReporter(localDownloadTask.getDownloadTaskId());
      if (localDLReporter != null)
      {
        long l = localDLReporter.calDeltTime();
        if ((l >= 60000L) || (localDownloadTask.getCostTime() >= 60000L))
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("(200:");
          localStringBuilder.append(l);
          localStringBuilder.append(")");
          localDLReporter.addStep(localStringBuilder.toString());
          localDLReporter.setReportFlag("1");
          localDLReporter.addDownPath(localDownloadTask.getDownloaderRunPath(true));
          DLReporterManager.report(localDLReporter, true);
        }
      }
    }
    BusinessDownloadEventManager.getInstance().downloadEventTaskObserver().onTaskCompleted(paramTask);
  }
  
  public void onTaskCreated(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      DLReporter localDLReporter = DLReporterManager.getReporter(((DownloadTask)paramTask).getDownloadTaskId());
      if (localDLReporter != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("(100:");
        localStringBuilder.append(localDLReporter.calDeltTime());
        localStringBuilder.append(")");
        localDLReporter.addStep(localStringBuilder.toString());
      }
    }
    BusinessDownloadEventManager.getInstance().downloadEventTaskObserver().onTaskCreated(paramTask);
  }
  
  public void onTaskExtEvent(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      DLReporter localDLReporter = DLReporterManager.getReporter(((DownloadTask)paramTask).getDownloadTaskId());
      if (localDLReporter != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("(104:");
        localStringBuilder.append(localDLReporter.calDeltTime());
        localStringBuilder.append(")");
        localDLReporter.addStep(localStringBuilder.toString());
      }
    }
    BusinessDownloadEventManager.getInstance().downloadEventTaskObserver().onTaskExtEvent(paramTask);
  }
  
  public void onTaskFailed(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      DownloadTask localDownloadTask = (DownloadTask)paramTask;
      DLReporter localDLReporter = DLReporterManager.getReporter(localDownloadTask.getDownloadTaskId());
      if (localDLReporter != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("(500:");
        localStringBuilder.append(localDLReporter.calDeltTime());
        localStringBuilder.append(")");
        localDLReporter.addStep(localStringBuilder.toString());
        localDLReporter.setReportFlag("-1");
        localDLReporter.addDownPath(localDownloadTask.getDownloaderRunPath(true));
        DLReporterManager.report(localDLReporter, true);
      }
    }
    BusinessDownloadEventManager.getInstance().downloadEventTaskObserver().onTaskFailed(paramTask);
  }
  
  public void onTaskProgress(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      DLReporter localDLReporter = DLReporterManager.getReporter(((DownloadTask)paramTask).getDownloadTaskId());
      if (localDLReporter != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("(102:");
        localStringBuilder.append(localDLReporter.calDeltTime());
        localStringBuilder.append(")");
        localDLReporter.addStep(localStringBuilder.toString());
      }
    }
    BusinessDownloadEventManager.getInstance().downloadEventTaskObserver().onTaskProgress(paramTask);
  }
  
  public void onTaskStarted(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      DLReporter localDLReporter = DLReporterManager.getReporter(((DownloadTask)paramTask).getDownloadTaskId());
      if (localDLReporter != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("(101:");
        localStringBuilder.append(localDLReporter.calDeltTime());
        localStringBuilder.append(")");
        localDLReporter.addStep(localStringBuilder.toString());
      }
    }
    BusinessDownloadEventManager.getInstance().downloadEventTaskObserver().onTaskStarted(paramTask);
  }
  
  public void removeDownloadedTaskListener(OnDownloadedTaskListener paramOnDownloadedTaskListener)
  {
    synchronized (this.mDownloadedTaskListener)
    {
      this.mDownloadedTaskListener.remove(paramOnDownloadedTaskListener);
      return;
    }
  }
  
  public void removeTaskListener(String paramString, TaskObserver paramTaskObserver)
  {
    BusinessDownloadEventManager.getInstance().removeTaskObserver(paramTaskObserver);
  }
  
  public void removeTaskObserver(TaskObserver paramTaskObserver)
  {
    BusinessDownloadEventManager.getInstance().removeTaskObserver(paramTaskObserver);
  }
  
  public DownloadTask restartTask(int paramInt)
  {
    return restartTask(paramInt, null);
  }
  
  public DownloadTask restartTask(int paramInt, DLReporter paramDLReporter)
  {
    if (paramDLReporter != null) {
      paramDLReporter.addStep(":RS[");
    }
    if (!hasInitCompleted())
    {
      if (paramDLReporter != null) {
        paramDLReporter.addStep("-1]");
      }
      return null;
    }
    DownloadTask localDownloadTask = DownloadproviderHelper.a(paramInt);
    if (paramDLReporter != null) {
      paramDLReporter.addStep("-2");
    }
    if (localDownloadTask != null)
    {
      localDownloadTask.setDownloadTaskId(paramInt);
      localDownloadTask.setStatus((byte)10);
      if (paramDLReporter != null) {
        paramDLReporter.addStep("-3");
      }
    }
    if (paramDLReporter != null) {
      paramDLReporter.addStep("-4]");
    }
    return localDownloadTask;
  }
  
  public void resumeTask(int paramInt)
  {
    resumeTask(paramInt, null);
  }
  
  public void resumeTask(int paramInt, DLReporter paramDLReporter)
  {
    if (paramDLReporter != null) {
      paramDLReporter.addStep(":RE[");
    }
    if ((paramInt != -1) && (hasInitCompleted()))
    {
      DownloadTask localDownloadTask = DownloadproviderHelper.a(paramInt);
      if (localDownloadTask == null)
      {
        if (paramDLReporter != null) {
          paramDLReporter.addStep("-2]");
        }
        return;
      }
      localDownloadTask.setPausedByNoWifi(false, false);
      if ((localDownloadTask.getStatus() == 6) || (localDownloadTask.getStatus() == 5) || (localDownloadTask.getStatus() == 4))
      {
        localDownloadTask.setStatus((byte)0);
        if (paramDLReporter != null) {
          paramDLReporter.addStep("-3]");
        }
      }
      return;
    }
    if (paramDLReporter != null) {
      paramDLReporter.addStep("-1]");
    }
  }
  
  public void retryUrl(DownloadTask paramDownloadTask)
  {
    paramDownloadTask = DownloadproviderHelper.d(paramDownloadTask.getTaskUrl());
    if (paramDownloadTask != null)
    {
      paramDownloadTask.mDownloadOps.init(2, null);
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(paramDownloadTask.getDownloadTaskId()));
      localContentValues.put("download_operations", paramDownloadTask.mDownloadOps.objectToDatabase());
      DownloadproviderHelper.a(localContentValues, true);
    }
  }
  
  public int startDownload(DownloadInfo paramDownloadInfo)
  {
    if ((paramDownloadInfo != null) && ((!TextUtils.isEmpty(paramDownloadInfo.url)) || (!TextUtils.isEmpty(paramDownloadInfo.mWebUrl))))
    {
      if ((!TextUtils.isEmpty(paramDownloadInfo.url)) && (TextUtils.isEmpty(paramDownloadInfo.url.trim())))
      {
        FLogger.d("ZAYTAG", "startDownloadTask url or webUrl is null");
        return -3;
      }
      DownloadTask localDownloadTask = startRealDownlodTask(paramDownloadInfo);
      if (paramDownloadInfo.observer != null) {
        paramDownloadInfo.observer.onTaskCreated(localDownloadTask);
      }
      if (localDownloadTask == null) {
        return -4;
      }
      if (localDownloadTask.getDownloadTaskId() == -1)
      {
        if (localDownloadTask.mFrom == 1) {
          return -5;
        }
        if (localDownloadTask.mFrom == 2) {
          return -6;
        }
        return -7;
      }
      return localDownloadTask.getDownloadTaskId();
    }
    FLogger.d("ZAYTAG", "startDownloadTask url or webUrl is null");
    return -2;
  }
  
  DownloadTask startRealDownlodTask(DownloadInfo paramDownloadInfo)
  {
    if (paramDownloadInfo == null) {
      return null;
    }
    DLReporter localDLReporter = new DLReporter(paramDownloadInfo.fileName);
    localDLReporter.addStep(":S[");
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("<u:");
    ((StringBuilder)localObject).append(paramDownloadInfo.url);
    ((StringBuilder)localObject).append(",f:");
    ((StringBuilder)localObject).append(paramDownloadInfo.flag);
    ((StringBuilder)localObject).append(">");
    localDLReporter.addDownPath(((StringBuilder)localObject).toString());
    localObject = DownloadproviderHelper.c(paramDownloadInfo.url);
    if (localObject != null)
    {
      deleteTask(((DownloadTask)localObject).getDownloadTaskId(), true);
      localDLReporter.addStep("-1");
    }
    localObject = new DownloadTask(ContextHolder.getAppContext(), paramDownloadInfo.url, paramDownloadInfo.fileName, paramDownloadInfo.fileFolderPath);
    ((DownloadTask)localObject).setNeedNotification(paramDownloadInfo.needNotification, false);
    ((DownloadTask)localObject).setExtFlagPlugin(true, false);
    if (!TextUtils.isEmpty(paramDownloadInfo.annotation)) {
      ((DownloadTask)localObject).setAnnotation(paramDownloadInfo.annotation, false);
    }
    String str = paramDownloadInfo.skinName;
    if (!TextUtils.isEmpty(str)) {
      ((DownloadTask)localObject).setAnnotation(str, false);
    }
    if (!TextUtils.isEmpty(paramDownloadInfo.annotationExt)) {
      ((DownloadTask)localObject).setAnnotationExt(paramDownloadInfo.annotationExt, false);
    }
    ((DownloadTask)localObject).setFlag(paramDownloadInfo.flag, false);
    ((DownloadTask)localObject).setHidden(true, false);
    ((DownloadTask)localObject).setRetryUrls(paramDownloadInfo.retryUrls);
    ((DownloadTask)localObject).setFileSize(paramDownloadInfo.fileSize, false);
    ((DownloadTask)localObject).setFileSizeForHijack(paramDownloadInfo.fileSize, false);
    if (!TextUtils.isEmpty(paramDownloadInfo.mCookie)) {
      ((DownloadTask)localObject).setCookie(paramDownloadInfo.mCookie, false);
    }
    if (((DownloadTask)localObject).isBackAutoTask()) {
      localDLReporter.addStep("-bg");
    } else {
      localDLReporter.addStep("-nm");
    }
    localDLReporter.addStep("-2");
    paramDownloadInfo = addTask((DownloadTask)localObject, false, localDLReporter);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("xxxxx ");
    ((StringBuilder)localObject).append(paramDownloadInfo.getFileName());
    ((StringBuilder)localObject).append(",tempTask.getTaskRunRightNow()=");
    ((StringBuilder)localObject).append(paramDownloadInfo.getTaskRunRightNow());
    ((StringBuilder)localObject).append(",Apn.isFreeWifi()=");
    ((StringBuilder)localObject).append(Apn.isFreeWifi());
    FLogger.d("ZAYTAG", ((StringBuilder)localObject).toString());
    if ((!paramDownloadInfo.getTaskRunRightNow()) && (!Apn.isFreeWifi()) && (!QueenConfig.isQueenEnable()))
    {
      paramDownloadInfo.setPausedByNoWifi(true, true);
      cancelTask(paramDownloadInfo.getDownloadTaskId());
      paramDownloadInfo.mStatus = 6;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("pause, ");
      ((StringBuilder)localObject).append(paramDownloadInfo.getFileName());
      ((StringBuilder)localObject).append(",tempTask.getTaskRunRightNow()=");
      ((StringBuilder)localObject).append(paramDownloadInfo.getTaskRunRightNow());
      ((StringBuilder)localObject).append(",Apn.isFreeWifi()=");
      ((StringBuilder)localObject).append(Apn.isFreeWifi());
      FLogger.d("ZAYTAG", ((StringBuilder)localObject).toString());
      localDLReporter.addStep("-3");
    }
    localDLReporter.addStep("-4]");
    if (paramDownloadInfo.getDownloadTaskId() != -1)
    {
      localDLReporter.setId(paramDownloadInfo.getDownloadTaskId());
      DLReporterManager.addReporter(localDLReporter);
    }
    return paramDownloadInfo;
  }
  
  public static abstract interface BackgroundDownloadDialogCallback
  {
    public abstract void goOnDownload();
    
    public abstract void stopDownload();
  }
  
  public static class BusinessDownloadEventManager
  {
    static BusinessDownloadEventManager mInstance;
    final String DEFAULT_URL = "defaule_url";
    private BusinessDownloadEventTaskObserver mDownloadEventTaskObserver = new BusinessDownloadEventTaskObserver();
    private Handler mNotifyHandler;
    
    BusinessDownloadEventManager()
    {
      HandlerThread localHandlerThread = new HandlerThread("DOWNLOAD_NOTIFY", 10);
      localHandlerThread.start();
      try
      {
        for (;;)
        {
          Looper localLooper = localHandlerThread.getLooper();
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
        this.mNotifyHandler = new Handler(localHandlerThread.getLooper());
        return;
      }
      finally {}
    }
    
    public static BusinessDownloadEventManager getInstance()
    {
      try
      {
        if (mInstance == null) {
          mInstance = new BusinessDownloadEventManager();
        }
        BusinessDownloadEventManager localBusinessDownloadEventManager = mInstance;
        return localBusinessDownloadEventManager;
      }
      finally {}
    }
    
    public void addTaskObserver(TaskObserver paramTaskObserver)
    {
      this.mDownloadEventTaskObserver.addTaskObserver("defaule_url", paramTaskObserver);
    }
    
    public void addTaskObserver(String paramString, TaskObserver paramTaskObserver)
    {
      this.mDownloadEventTaskObserver.addTaskObserver(paramString, paramTaskObserver);
    }
    
    public BusinessDownloadEventTaskObserver downloadEventTaskObserver()
    {
      return this.mDownloadEventTaskObserver;
    }
    
    public void removeTaskObserver(TaskObserver paramTaskObserver)
    {
      this.mDownloadEventTaskObserver.removeTaskObserver(paramTaskObserver);
    }
    
    public class BusinessDownloadEventTaskObserver
      implements TaskObserver
    {
      private final HashMap<String, List<TaskObserver>> mDownloadTaskIdTaskMapObserver = new HashMap();
      
      public BusinessDownloadEventTaskObserver() {}
      
      private void loopObserver(final Task paramTask, final BaseDownloadManager.BusinessDownloadEventManager.TaskCallbakRunner paramTaskCallbakRunner, String paramString)
      {
        if (paramTaskCallbakRunner != null) {
          if (paramTask == null) {
            return;
          }
        }
        for (;;)
        {
          synchronized (this.mDownloadTaskIdTaskMapObserver)
          {
            final Object localObject = new StringBuilder();
            ((StringBuilder)localObject).append("BusinessDownloadEventTaskObserver loopObserver: ");
            ((StringBuilder)localObject).append(paramString);
            ((StringBuilder)localObject).append(", name:");
            ((StringBuilder)localObject).append(((DownloadTask)paramTask).getFileName());
            FLogger.d("ZHTAG", ((StringBuilder)localObject).toString());
            paramString = ((DownloadTask)paramTask).getTaskUrl();
            localObject = new ArrayList();
            if ((!TextUtils.isEmpty(paramString)) && (this.mDownloadTaskIdTaskMapObserver.containsKey(paramString))) {
              ((List)localObject).addAll((Collection)this.mDownloadTaskIdTaskMapObserver.get(paramString));
            }
            if (this.mDownloadTaskIdTaskMapObserver.containsKey("defaule_url")) {
              ((List)localObject).addAll((Collection)this.mDownloadTaskIdTaskMapObserver.get("defaule_url"));
            }
            if (!((List)localObject).isEmpty())
            {
              paramString = ((List)localObject).iterator();
              if (paramString.hasNext())
              {
                localObject = (TaskObserver)paramString.next();
                if (localObject == null) {
                  continue;
                }
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("  BusinessDownloadEventTaskObserver: ");
                localStringBuilder.append(localObject);
                localStringBuilder.append(", mNotifyHandler == null: ");
                if (BaseDownloadManager.BusinessDownloadEventManager.this.mNotifyHandler != null) {
                  break label307;
                }
                bool = true;
                localStringBuilder.append(bool);
                Log.d("ZHTAG", localStringBuilder.toString());
                BaseDownloadManager.BusinessDownloadEventManager.this.mNotifyHandler.post(new Runnable()
                {
                  public void run()
                  {
                    paramTaskCallbakRunner.run(localObject, paramTask);
                  }
                });
                continue;
              }
            }
            return;
          }
          return;
          label307:
          boolean bool = false;
        }
      }
      
      public void addTaskObserver(String paramString, TaskObserver paramTaskObserver)
      {
        synchronized (this.mDownloadTaskIdTaskMapObserver)
        {
          if (this.mDownloadTaskIdTaskMapObserver.containsKey(paramString))
          {
            paramString = (List)this.mDownloadTaskIdTaskMapObserver.get(paramString);
            if (!paramString.contains(paramTaskObserver)) {
              paramString.add(paramTaskObserver);
            }
          }
          else
          {
            ArrayList localArrayList = new ArrayList();
            localArrayList.add(paramTaskObserver);
            this.mDownloadTaskIdTaskMapObserver.put(paramString, localArrayList);
          }
          return;
        }
      }
      
      public void onTaskCompleted(Task paramTask)
      {
        loopObserver(paramTask, new BaseDownloadManager.BusinessDownloadEventManager.TaskCallbakRunner()
        {
          public void run(TaskObserver paramAnonymousTaskObserver, Task paramAnonymousTask)
          {
            paramAnonymousTaskObserver.onTaskCompleted(paramAnonymousTask);
          }
        }, "onTaskCompleted");
      }
      
      public void onTaskCreated(Task paramTask)
      {
        loopObserver(paramTask, new BaseDownloadManager.BusinessDownloadEventManager.TaskCallbakRunner()
        {
          public void run(TaskObserver paramAnonymousTaskObserver, Task paramAnonymousTask)
          {
            paramAnonymousTaskObserver.onTaskCreated(paramAnonymousTask);
          }
        }, "onTaskCreated");
      }
      
      public void onTaskExtEvent(Task paramTask)
      {
        loopObserver(paramTask, new BaseDownloadManager.BusinessDownloadEventManager.TaskCallbakRunner()
        {
          public void run(TaskObserver paramAnonymousTaskObserver, Task paramAnonymousTask)
          {
            paramAnonymousTaskObserver.onTaskExtEvent(paramAnonymousTask);
          }
        }, "onTaskExtEvent");
      }
      
      public void onTaskFailed(Task paramTask)
      {
        loopObserver(paramTask, new BaseDownloadManager.BusinessDownloadEventManager.TaskCallbakRunner()
        {
          public void run(TaskObserver paramAnonymousTaskObserver, Task paramAnonymousTask)
          {
            paramAnonymousTaskObserver.onTaskFailed(paramAnonymousTask);
          }
        }, "onTaskFailed");
      }
      
      public void onTaskProgress(Task paramTask)
      {
        loopObserver(paramTask, new BaseDownloadManager.BusinessDownloadEventManager.TaskCallbakRunner()
        {
          public void run(TaskObserver paramAnonymousTaskObserver, Task paramAnonymousTask)
          {
            paramAnonymousTaskObserver.onTaskProgress(paramAnonymousTask);
          }
        }, "onTaskProgress");
      }
      
      public void onTaskStarted(Task paramTask)
      {
        loopObserver(paramTask, new BaseDownloadManager.BusinessDownloadEventManager.TaskCallbakRunner()
        {
          public void run(TaskObserver paramAnonymousTaskObserver, Task paramAnonymousTask)
          {
            paramAnonymousTaskObserver.onTaskStarted(paramAnonymousTask);
          }
        }, "onTaskStarted");
      }
      
      public void removeTaskObserver(TaskObserver paramTaskObserver)
      {
        synchronized (this.mDownloadTaskIdTaskMapObserver)
        {
          Iterator localIterator1 = this.mDownloadTaskIdTaskMapObserver.entrySet().iterator();
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
    
    private static abstract interface TaskCallbakRunner
    {
      public abstract void run(TaskObserver paramTaskObserver, Task paramTask);
    }
  }
  
  public static abstract interface OnBatchAddedCallback
  {
    public abstract void onBatchAdded(ArrayList<DownloadTask> paramArrayList);
    
    public abstract void onBatchCanceled();
  }
  
  public static abstract interface OnDownloadFeedbackListener
  {
    public abstract void notifyFeedbackEvent(int paramInt);
  }
  
  public static abstract interface OnDownloadedTaskListener
  {
    public abstract void notifyTaskCanceled(String paramString);
    
    public abstract void notifyTaskDeleted(DownloadInfo paramDownloadInfo);
    
    public abstract void notifyTaskLength(DownloadTask paramDownloadTask, long paramLong1, long paramLong2);
    
    public abstract void notifyTaskPrepareStart(DownloadTask paramDownloadTask);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\BaseDownloadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */