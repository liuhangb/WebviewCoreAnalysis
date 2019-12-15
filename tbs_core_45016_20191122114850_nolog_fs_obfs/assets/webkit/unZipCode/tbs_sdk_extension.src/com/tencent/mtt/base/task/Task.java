package com.tencent.mtt.base.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import com.tencent.basesupport.FLogger;
import com.tencent.common.http.MttRequestBase;
import com.tencent.common.http.MttResponse;
import com.tencent.common.http.Requester;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Task
  implements Comparable<Task>, Runnable
{
  public static final int BUFFER_SIZE = 4096;
  private static final String DEFAULT_TASK_URL = "";
  public static final int ERR_APN_EXCEPTION = 1;
  public static final int ERR_CANCEL = 100;
  public static final int ERR_FILE_NOTFOUND = 8;
  public static final int ERR_NETWORK_EXCEPTION = 2;
  public static final int ERR_NON = 0;
  public static final int ERR_OOM = 9;
  public static final int ERR_REDIRECT_TOOMUCH = 6;
  public static final int ERR_SOCKET_EXCEPTION = 4;
  public static final int ERR_SOCKET_TIMEOUT = 5;
  public static final int ERR_TOOLARGE_FILE = 7;
  public static final int ERR_UNKNOWN = -1;
  public static final int ERR_UNKNOWN_HOST = 3;
  public static final int FAIL_TO_BROKER_TASK_MASK = 8;
  public static final int LOCAL_TASK_MASK = 4;
  public static final int LOGIN_TASK_MASK = 16;
  public static final int MAX_TRYING_TIME = 60000;
  @Deprecated
  public static final byte METHOD_GET = 0;
  @Deprecated
  public static final byte METHOD_HEAD = 2;
  @Deprecated
  public static final byte METHOD_POST = 1;
  public static final int MSG_POST_TASK = 1;
  public static final byte NETWORK_STATUS_CONNECTED = 2;
  public static final byte NETWORK_STATUS_CREATED = 1;
  public static final byte NETWORK_STATUS_RECEIVED = 4;
  public static final byte NETWORK_STATUS_SENDED = 3;
  public static final byte NETWORK_STATUS_UNKNOW = -1;
  public static final int PREREAD_TASK_MASK = 1;
  public static final int RELOAD_TASK_MASK = 2;
  public static final boolean REPORT_PERFORMANCE = true;
  public static final long RETRY_DELAYED_MILLIS = 1000L;
  public static final int SWITCH_LOGIN_TASK_MASK = 32;
  private static final String TAG = "Task";
  public static final byte TASK_CSS = 2;
  public static final byte TASK_DOWNLOAD = 3;
  public static final byte TASK_EXT_EVENT_DELETED = 1;
  public static final byte TASK_IMAGE = 1;
  public static final byte TASK_JS = 4;
  public static final byte TASK_NONE = -1;
  public static final byte TASK_PAGE = 0;
  public static final byte TASK_RUNNING_PENDING = 4;
  public static final byte TASK_RUNNING_STATE_DONE = 2;
  public static final byte TASK_RUNNING_STATE_INIT = 0;
  public static final byte TASK_RUNNING_STATE_RETRY = 3;
  public static final byte TASK_RUNNING_STATE_RUNNING = 1;
  public static final boolean USE_THREAD_POOL = true;
  private static int mTaskIdOri = 1;
  private static Handler sHandler = null;
  static Object sHandlerLock = new Object();
  private static AtomicInteger sSequenceGenerator = new AtomicInteger();
  public int mApn;
  protected boolean mCanceled = false;
  private String mEncodeName = "UTF-8";
  public int mErrorCode = 0;
  public int mExtEvent;
  public Throwable mFailedReason = null;
  private boolean mIsBootTask = false;
  private Object mLock = new Object();
  protected MttRequestBase mMttRequest;
  protected MttResponse mMttResponse;
  public boolean mNeedNotifyCanceled = false;
  public boolean mNeedRetryNow = true;
  public boolean mNeedStatFlow = false;
  public ArrayList<Long> mNetTimeList = new ArrayList();
  protected int mNetworkStatus = -1;
  protected List<TaskObserver> mObservers;
  public Priority mPriority = Priority.NORMAL;
  public String mRequestName = "";
  protected Requester mRequester;
  public byte mRunningState = 0;
  public Integer mSequence;
  public boolean mShouldCache = false;
  public byte mStatus = 0;
  public Object mTag;
  public int mTaskAttr = 0;
  protected ITaskExecutors mTaskExecutors = null;
  public int mTaskId = 0;
  public byte mTaskType = -1;
  public long mThreadWaitTime = -1L;
  protected int mWasteTime;
  public TaskInfo taskInfo = null;
  
  public Task()
  {
    int i = mTaskIdOri + 1;
    mTaskIdOri = i;
    this.mTaskId = i;
    this.mSequence = Integer.valueOf(sSequenceGenerator.incrementAndGet());
    this.taskInfo = new TaskInfo();
  }
  
  public static String getCostTimeStr(long paramLong1, long paramLong2, long paramLong3)
  {
    int i = (int)(paramLong3 / paramLong2);
    if (paramLong1 > paramLong3)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(">");
      localStringBuilder.append(i * paramLong2);
      return localStringBuilder.toString();
    }
    i = (int)((float)(paramLong1 - 1L) / (float)paramLong2);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append((i + 1) * paramLong2);
    return localStringBuilder.toString();
  }
  
  public static Handler getHandler()
  {
    ??? = sHandler;
    if (??? != null) {
      return (Handler)???;
    }
    synchronized (sHandlerLock)
    {
      if (sHandler == null) {
        sHandler = new a(BrowserExecutorSupplier.getLooperForRunShortTime());
      }
      return sHandler;
    }
  }
  
  public void addObserver(TaskObserver paramTaskObserver)
  {
    if (paramTaskObserver == null) {
      return;
    }
    if (this.mObservers == null) {
      this.mObservers = new ArrayList(3);
    }
    synchronized (this.mObservers)
    {
      Iterator localIterator = this.mObservers.iterator();
      while (localIterator.hasNext()) {
        if ((TaskObserver)localIterator.next() == paramTaskObserver) {
          return;
        }
      }
      this.mObservers.add(paramTaskObserver);
      return;
    }
  }
  
  public void cancel()
  {
    if (ThreadUtils.isMainThread())
    {
      BrowserExecutorSupplier.postForTimeoutTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          synchronized (Task.this.mLock)
          {
            if (Task.this.mRequester != null) {
              Task.this.mRequester.abort();
            }
            return;
          }
        }
      });
      return;
    }
    synchronized (this.mLock)
    {
      if (this.mRequester != null) {
        this.mRequester.abort();
      }
      return;
    }
  }
  
  public void closeQuietly()
  {
    FLogger.d("Task", "CloseQuietly");
    synchronized (this.mLock)
    {
      Requester localRequester = this.mRequester;
      if (localRequester != null)
      {
        try
        {
          this.mRequester.close();
        }
        catch (Error localError)
        {
          localError.printStackTrace();
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        this.mRequester = null;
      }
      return;
    }
  }
  
  public int compareTo(Task paramTask)
  {
    Priority localPriority1 = this.mPriority;
    Priority localPriority2 = paramTask.mPriority;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("compareTo,  left: ");
    localStringBuilder.append(localPriority1);
    localStringBuilder.append(", right: ");
    localStringBuilder.append(localPriority2);
    FLogger.d("Task", localStringBuilder.toString());
    if (localPriority1 == localPriority2) {
      return this.mSequence.intValue() - paramTask.mSequence.intValue();
    }
    return localPriority2.ordinal() - localPriority1.ordinal();
  }
  
  public abstract void doRun();
  
  public String failedReason()
  {
    Throwable localThrowable = this.mFailedReason;
    if (localThrowable != null) {
      return localThrowable.toString();
    }
    return "";
  }
  
  public void finish(String paramString) {}
  
  public void fireExtEvent()
  {
    List localList = this.mObservers;
    if (localList == null) {
      return;
    }
    try
    {
      Iterator localIterator = this.mObservers.iterator();
      while (localIterator.hasNext()) {
        ((TaskObserver)localIterator.next()).onTaskExtEvent(this);
      }
      return;
    }
    finally {}
  }
  
  public void fireObserverEvent(int paramInt)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("fireObserverEvent, tag: ");
    ((StringBuilder)localObject1).append(this.mTag);
    ((StringBuilder)localObject1).append(", status: ");
    ((StringBuilder)localObject1).append(paramInt);
    FLogger.d("Task", ((StringBuilder)localObject1).toString());
    localObject1 = this.mObservers;
    if (localObject1 == null) {
      return;
    }
    switch (paramInt)
    {
    default: 
      
    case 6: 
      if (this.mNeedNotifyCanceled) {
        try
        {
          Object localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append(this);
          ((StringBuilder)localObject2).append(" notify cancel");
          FLogger.d("Task", ((StringBuilder)localObject2).toString());
          localObject2 = this.mObservers.iterator();
          while (((Iterator)localObject2).hasNext()) {
            ((TaskObserver)((Iterator)localObject2).next()).onTaskExtEvent(this);
          }
          return;
        }
        finally {}
      }
      break;
    case 4: 
    case 5: 
      try
      {
        Iterator localIterator1 = this.mObservers.iterator();
        while (localIterator1.hasNext()) {
          ((TaskObserver)localIterator1.next()).onTaskFailed(this);
        }
        return;
      }
      finally {}
    case 3: 
      try
      {
        Iterator localIterator2 = this.mObservers.iterator();
        while (localIterator2.hasNext()) {
          ((TaskObserver)localIterator2.next()).onTaskCompleted(this);
        }
        return;
      }
      finally {}
    case 2: 
      try
      {
        Iterator localIterator3 = this.mObservers.iterator();
        while (localIterator3.hasNext()) {
          ((TaskObserver)localIterator3.next()).onTaskProgress(this);
        }
        return;
      }
      finally {}
    case 1: 
      try
      {
        Iterator localIterator4 = this.mObservers.iterator();
        while (localIterator4.hasNext()) {
          ((TaskObserver)localIterator4.next()).onTaskStarted(this);
        }
        return;
      }
      finally {}
    case 0: 
      try
      {
        Iterator localIterator5 = this.mObservers.iterator();
        while (localIterator5.hasNext()) {
          ((TaskObserver)localIterator5.next()).onTaskCreated(this);
        }
        return;
      }
      finally {}
    }
  }
  
  public void firePreDeletedEvent()
  {
    List localList = this.mObservers;
    if (localList == null) {
      return;
    }
    try
    {
      Iterator localIterator = this.mObservers.iterator();
      while (localIterator.hasNext()) {
        ((TaskObserver)localIterator.next()).onTaskExtEvent(this);
      }
      return;
    }
    finally {}
  }
  
  public String getEncodeName()
  {
    return this.mEncodeName;
  }
  
  public Throwable getFailReason()
  {
    return this.mFailedReason;
  }
  
  public int getFlow()
  {
    Object localObject = this.mMttRequest;
    int j = 0;
    int i;
    if (localObject != null) {
      i = ((MttRequestBase)localObject).getFlow();
    } else {
      i = 0;
    }
    localObject = this.mMttResponse;
    if (localObject != null) {
      j = ((MttResponse)localObject).getFlow();
    }
    return i + j;
  }
  
  public boolean getIsBackGroudTask()
  {
    MttRequestBase localMttRequestBase = this.mMttRequest;
    if (localMttRequestBase != null) {
      return localMttRequestBase.getIsBackGroudRequest();
    }
    return false;
  }
  
  public MttRequestBase getMttRequest()
  {
    return this.mMttRequest;
  }
  
  public MttResponse getMttResponse()
  {
    return this.mMttResponse;
  }
  
  public int getNetworkStatus()
  {
    return this.mNetworkStatus;
  }
  
  public String getReportString()
  {
    return null;
  }
  
  public Requester getRequester()
  {
    return this.mRequester;
  }
  
  public final int getSequence()
  {
    Integer localInteger = this.mSequence;
    if (localInteger != null) {
      return localInteger.intValue();
    }
    throw new IllegalStateException("getSequence called before setSequence");
  }
  
  public byte getStatus()
  {
    return this.mStatus;
  }
  
  public byte getTaskType()
  {
    return this.mTaskType;
  }
  
  public String getTaskUrl()
  {
    return "";
  }
  
  public void handleResponse() {}
  
  public boolean isCanceled()
  {
    return this.mCanceled;
  }
  
  public boolean isPending()
  {
    return this.mRunningState == 4;
  }
  
  public boolean isRetring()
  {
    return this.mRunningState == 3;
  }
  
  public void onTaskOver() {}
  
  public MttResponse perform()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("perform: ");
    localStringBuilder.append(this);
    FLogger.d("Task", localStringBuilder.toString());
    doRun();
    return this.mMttResponse;
  }
  
  public void removeObserver(TaskObserver paramTaskObserver)
  {
    List localList = this.mObservers;
    if (localList != null) {
      try
      {
        this.mObservers.remove(paramTaskObserver);
        return;
      }
      finally {}
    }
  }
  
  public void removeTaskAttr(int paramInt)
  {
    this.mTaskAttr = ((paramInt ^ 0xFFFFFFFF) & this.mTaskAttr);
  }
  
  protected void resetStatus()
  {
    this.mStatus = 0;
    this.mErrorCode = 0;
    this.mCanceled = false;
    this.mNeedNotifyCanceled = false;
  }
  
  public void retry()
  {
    retry(1000L);
  }
  
  public void retry(long paramLong)
  {
    Handler localHandler = getHandler();
    Message localMessage = localHandler.obtainMessage();
    localMessage.what = 1;
    localMessage.obj = this;
    localHandler.sendMessageDelayed(localMessage, paramLong);
  }
  
  public void run()
  {
    Process.setThreadPriority(11);
    doRun();
  }
  
  public void setConnectionClose()
  {
    this.mMttRequest.addHeader("Connection", "Close");
  }
  
  public void setEncodeName(String paramString)
  {
    this.mEncodeName = paramString;
  }
  
  public void setFailedReason(Throwable paramThrowable)
  {
    this.mFailedReason = paramThrowable;
  }
  
  public void setIsBackgroudTask(boolean paramBoolean)
  {
    this.mMttRequest.setIsBackgroudRequest(paramBoolean);
  }
  
  public void setMttResponse(MttResponse arg1)
  {
    this.mMttResponse = ???;
    synchronized (this.mLock)
    {
      if (this.mRequester != null) {
        this.mApn = this.mRequester.getApn();
      } else {
        this.mApn = 0;
      }
      return;
    }
  }
  
  public void setStatus(byte paramByte)
  {
    this.mStatus = paramByte;
  }
  
  public void setTaskExecutors(ITaskExecutors paramITaskExecutors)
  {
    this.mTaskExecutors = paramITaskExecutors;
  }
  
  public void setTaskType(byte paramByte)
  {
    this.mTaskType = paramByte;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[taskid: ");
    localStringBuilder.append(this.mTaskId);
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  public static enum Priority
  {
    private Priority() {}
  }
  
  public static class TaskInfo
  {
    public long addToDeliverQueueTime = 0L;
    public long addToQueueTime = 0L;
    public long deliverTime = 0L;
    public long deliverWaitTime = 0L;
    public long id = -1L;
    public long performTime = 0L;
    public int requestCount = 0;
    public int responseCount = 0;
    public long waitTime = 0L;
  }
  
  static class a
    extends Handler
  {
    public a(Looper paramLooper)
    {
      super();
    }
    
    public void handleMessage(Message paramMessage)
    {
      if (paramMessage.what == 1)
      {
        paramMessage = (Task)paramMessage.obj;
        if (paramMessage != null)
        {
          if ((paramMessage.mTaskExecutors != null) && (paramMessage.mTaskExecutors.getRetryExecutor() != null))
          {
            paramMessage.mTaskExecutors.getRetryExecutor().execute(paramMessage);
            return;
          }
          paramMessage.mStatus = 5;
          paramMessage.fireObserverEvent(paramMessage.mStatus);
          paramMessage.finish("done");
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\task\Task.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */