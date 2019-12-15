package com.tencent.tbs.common.task;

import com.tencent.common.http.MttResponse;
import com.tencent.common.http.Requester;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.http.MttRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Task
  implements Runnable
{
  private static final String DEFAULT_TASK_URL = "";
  public static final int ERR_APN_EXCEPTION = 1;
  public static final int ERR_FILE_NOTFOUND = 7;
  public static final int ERR_NETWORK_EXCEPTION = 2;
  public static final int ERR_NON = 0;
  public static final int ERR_REDIRECT_TOOMUCH = 5;
  public static final int ERR_SOCKET_TIMEOUT = 4;
  public static final int ERR_TOOLARGE_FILE = 6;
  public static final int ERR_UNKNOWN = 8;
  public static final int ERR_UNKNOWN_HOST = 3;
  public static final int FAIL_TO_BROKER_TASK_MASK = 8;
  public static final int LOCAL_TASK_MASK = 4;
  public static final int LOGIN_TASK_MASK = 16;
  public static final int MAX_TRYING_TIME = 60000;
  public static final byte METHOD_GET = 0;
  public static final byte METHOD_HEAD = 3;
  public static final byte METHOD_POST = 1;
  public static final byte NETWORK_STATUS_CONNECTED = 2;
  public static final byte NETWORK_STATUS_CREATED = 1;
  public static final byte NETWORK_STATUS_RECEIVED = 4;
  public static final byte NETWORK_STATUS_SENDED = 3;
  public static final byte NETWORK_STATUS_UNKNOW = -1;
  public static final int PREREAD_TASK_MASK = 1;
  public static final int RELOAD_TASK_MASK = 2;
  public static final int SWITCH_LOGIN_TASK_MASK = 32;
  private static final String TAG = "Task";
  public static final byte TASK_CSS = 2;
  public static final byte TASK_DOWNLOAD = 3;
  public static final byte TASK_EXT_EVENT_DELETED = 1;
  public static final byte TASK_IMAGE = 1;
  public static final byte TASK_JS = 4;
  public static final byte TASK_NONE = -1;
  public static final byte TASK_PAGE = 0;
  public static final byte TASK_RUNNING_STATE_DONE = 2;
  public static final byte TASK_RUNNING_STATE_INIT = 0;
  public static final byte TASK_RUNNING_STATE_RUNNING = 1;
  private static int mTaskIdOri = 1;
  private int mApn;
  protected boolean mCanceled = false;
  private String mEncodeName = "UTF-8";
  public int mErrorCode = 0;
  private int mExtEvent;
  private Throwable mFailedReason = null;
  private boolean mIsBootTask = false;
  private Object mLock = new Object();
  protected MttRequest mMttRequest;
  protected MttResponse mMttResponse;
  private boolean mNeedNotifyCanceled = false;
  private boolean mNeedStatFlow = false;
  public ArrayList<Long> mNetTimeList = new ArrayList();
  protected int mNetworkStatus = -1;
  protected List<TaskObserver> mObservers;
  private String mRequestName = "";
  protected Requester mRequester;
  public byte mRunningState = 0;
  public byte mStatus = 0;
  private int mTaskAttr = 0;
  public int mTaskId = 0;
  public byte mTaskType = -1;
  public long mThreadWaitTime = -1L;
  protected int mWasteTime;
  
  public Task()
  {
    int i = mTaskIdOri + 1;
    mTaskIdOri = i;
    this.mTaskId = i;
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
  
  public void addTaskAttr(int paramInt)
  {
    this.mTaskAttr = (paramInt | this.mTaskAttr);
  }
  
  public abstract void cancel();
  
  public void closeQuietly()
  {
    LogUtils.d("Task", "CloseQuietly");
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
  
  public String failedReason()
  {
    Throwable localThrowable = this.mFailedReason;
    if (localThrowable != null) {
      return localThrowable.toString();
    }
    return "";
  }
  
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
  
  public void fireObserverEvent()
  {
    fireObserverEvent(this.mStatus);
  }
  
  public void fireObserverEvent(int paramInt)
  {
    List localList = this.mObservers;
    if (localList == null) {
      return;
    }
    switch (paramInt)
    {
    default: 
      
    case 6: 
      if (this.mNeedNotifyCanceled) {
        try
        {
          Object localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(this);
          ((StringBuilder)localObject1).append(" notify cancel");
          LogUtils.d("Task", ((StringBuilder)localObject1).toString());
          localObject1 = this.mObservers.iterator();
          while (((Iterator)localObject1).hasNext()) {
            ((TaskObserver)((Iterator)localObject1).next()).onTaskFailed(this);
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
  
  public int getApn()
  {
    return this.mApn;
  }
  
  public String getEncodeName()
  {
    return this.mEncodeName;
  }
  
  public int getExtEvent()
  {
    return this.mExtEvent;
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
      i = ((MttRequest)localObject).getFlow();
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
    MttRequest localMttRequest = this.mMttRequest;
    if (localMttRequest != null) {
      return localMttRequest.getIsBackGroudRequest();
    }
    return false;
  }
  
  public MttRequest getMttRequest()
  {
    return this.mMttRequest;
  }
  
  public MttResponse getMttResponse()
  {
    return this.mMttResponse;
  }
  
  public boolean getNeedStatFlow()
  {
    return this.mNeedStatFlow;
  }
  
  public int getNetworkStatus()
  {
    return this.mNetworkStatus;
  }
  
  public int getReceivedFlow()
  {
    MttResponse localMttResponse = this.mMttResponse;
    if (localMttResponse != null) {
      return localMttResponse.getFlow();
    }
    return 0;
  }
  
  public String getRequestName()
  {
    return this.mRequestName;
  }
  
  public Requester getRequester()
  {
    return this.mRequester;
  }
  
  public int getSentFlow()
  {
    MttRequest localMttRequest = this.mMttRequest;
    if (localMttRequest != null) {
      return localMttRequest.getFlow();
    }
    return 0;
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
  
  public int getWasteTime()
  {
    return this.mWasteTime;
  }
  
  public boolean isBootTask()
  {
    return this.mIsBootTask;
  }
  
  public boolean isCanceled()
  {
    return this.mCanceled;
  }
  
  public boolean isTaskAttrAdded(int paramInt)
  {
    return (paramInt & this.mTaskAttr) != 0;
  }
  
  public void onTaskOver() {}
  
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
  
  public void setIsBootTask(boolean paramBoolean)
  {
    this.mIsBootTask = paramBoolean;
  }
  
  public void setMttResponse(MttResponse paramMttResponse)
  {
    this.mMttResponse = paramMttResponse;
    paramMttResponse = this.mRequester;
    if (paramMttResponse != null)
    {
      this.mApn = paramMttResponse.getApn();
      return;
    }
    this.mApn = 0;
  }
  
  public void setNeedNotfiyCanceled(boolean paramBoolean)
  {
    this.mNeedNotifyCanceled = paramBoolean;
  }
  
  public void setNeedStatFlow(boolean paramBoolean)
  {
    this.mNeedStatFlow = paramBoolean;
  }
  
  public void setRequestName(String paramString)
  {
    this.mRequestName = paramString;
  }
  
  public void setStatus(byte paramByte)
  {
    this.mStatus = paramByte;
  }
  
  public void setTaskType(byte paramByte)
  {
    this.mTaskType = paramByte;
  }
  
  public boolean shouldRun()
  {
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\task\Task.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */