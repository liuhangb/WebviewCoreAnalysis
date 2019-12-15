package com.tencent.common.threadpool.debug;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.ComparableFutureTask;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class QBThreadTimeoutWatcher
{
  public static final long BACKGROUND_RESUME_NO_REPORT = 500L;
  public static final long BACKGROUND_START_REPORT = 30000L;
  public static final long CHECK_TIME_INTERVAL = 30000L;
  public static final String EVENT_NAME = "MTT_THREADPOOL_CHECKER_EVENT";
  public static final long MAX_RUNNABLE_ANR_THRESHOLD = 5000L;
  public static final long MAX_RUNNABLE_EXECUTE_THRESHOLD = 5000L;
  public static final long MAX_THREAD_REMOVE_THRESHOLD = 60000L;
  public static final int MSG_DO_CHECK_THREAD = 2;
  public static final int MSG_DO_REPORT = 1;
  public static final int NOT_REPORT_STACK = 2;
  public static final int REPORT_STACK = 1;
  protected static Handler mCheckerHandler;
  protected static long mInBackgroundTime = 2147483647L;
  protected static boolean mIsEnable = true;
  protected static boolean mIsInit = false;
  protected static boolean mIsPause = false;
  protected static ConcurrentHashMap<Long, QBThreadRunInfo> mMapRunStatus = new ConcurrentHashMap();
  protected static long mOutBackgroundTime = -2147483648L;
  protected static IQBThreadTimeoutWatcherReportListener mReportListener;
  
  private static void a(@NonNull QBThreadRunInfo paramQBThreadRunInfo)
  {
    long l2 = paramQBThreadRunInfo.b;
    long l3 = paramQBThreadRunInfo.jdField_a_of_type_Long;
    boolean bool;
    if ((mIsPause) && (System.currentTimeMillis() - mInBackgroundTime > 30000L)) {
      bool = true;
    } else {
      bool = false;
    }
    long l1;
    if (bool) {
      l1 = 500L;
    } else {
      l1 = 0L;
    }
    paramQBThreadRunInfo.jdField_a_of_type_Boolean = bool;
    if ((l2 - l3 > 5000L) || (bool)) {
      report(paramQBThreadRunInfo.clone(), l1);
    }
  }
  
  public static void afterExecute(Runnable paramRunnable, Throwable paramThrowable, String paramString)
  {
    if (mIsEnable)
    {
      if (!mIsInit) {
        return;
      }
      long l = Thread.currentThread().getId();
      paramThrowable = (QBThreadRunInfo)mMapRunStatus.get(Long.valueOf(l));
      if ((paramThrowable != null) && (paramThrowable.jdField_a_of_type_JavaLangThread != null) && (paramThrowable.jdField_a_of_type_JavaLangRunnable == paramRunnable))
      {
        paramThrowable.b = System.currentTimeMillis();
        a(paramThrowable);
        paramThrowable.clear();
      }
      return;
    }
  }
  
  public static void beforeExecute(Thread paramThread, Runnable paramRunnable, String paramString)
  {
    if (mIsEnable)
    {
      if (!mIsInit) {
        return;
      }
      long l = paramThread.getId();
      if (l > 0L)
      {
        QBThreadRunInfo localQBThreadRunInfo2 = (QBThreadRunInfo)mMapRunStatus.get(Long.valueOf(l));
        QBThreadRunInfo localQBThreadRunInfo1 = localQBThreadRunInfo2;
        if (localQBThreadRunInfo2 == null)
        {
          localQBThreadRunInfo1 = new QBThreadRunInfo();
          mMapRunStatus.put(Long.valueOf(l), localQBThreadRunInfo1);
        }
        localQBThreadRunInfo1.jdField_a_of_type_Long = System.currentTimeMillis();
        localQBThreadRunInfo1.jdField_a_of_type_JavaLangRunnable = paramRunnable;
        localQBThreadRunInfo1.jdField_a_of_type_JavaLangThread = paramThread;
        localQBThreadRunInfo1.jdField_a_of_type_JavaLangString = paramString;
      }
      return;
    }
  }
  
  public static void doReport(@NonNull QBThreadRunInfo paramQBThreadRunInfo, boolean paramBoolean)
  {
    if (paramQBThreadRunInfo.jdField_a_of_type_JavaLangThread != null)
    {
      if (paramQBThreadRunInfo.jdField_a_of_type_JavaLangRunnable == null) {
        return;
      }
      long l1 = paramQBThreadRunInfo.b;
      long l2 = paramQBThreadRunInfo.jdField_a_of_type_Long;
      if ((paramQBThreadRunInfo.jdField_a_of_type_Boolean) && (l1 - l2 < 5000L) && (mOutBackgroundTime - paramQBThreadRunInfo.jdField_a_of_type_Long < 500L))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("doReport backresume:");
        ((StringBuilder)localObject1).append(paramQBThreadRunInfo);
        ((StringBuilder)localObject1).toString();
        return;
      }
      Object localObject1 = new HashMap();
      Object localObject2;
      Object localObject3;
      if ((paramQBThreadRunInfo.jdField_a_of_type_JavaLangRunnable instanceof ComparableFutureTask))
      {
        localObject2 = ((ComparableFutureTask)paramQBThreadRunInfo.jdField_a_of_type_JavaLangRunnable).task;
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("");
        ((StringBuilder)localObject3).append(localObject2.getClass().getName());
        ((HashMap)localObject1).put("name", ((StringBuilder)localObject3).toString());
        ((HashMap)localObject1).put("expand", getReportExtInfo(localObject2));
      }
      else
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("");
        ((StringBuilder)localObject2).append(paramQBThreadRunInfo.jdField_a_of_type_JavaLangRunnable.getClass().getName());
        ((HashMap)localObject1).put("name", ((StringBuilder)localObject2).toString());
        ((HashMap)localObject1).put("expand", getReportExtInfo(paramQBThreadRunInfo.jdField_a_of_type_JavaLangRunnable));
      }
      if (paramBoolean)
      {
        localObject2 = new StringBuilder();
        localObject3 = paramQBThreadRunInfo.jdField_a_of_type_JavaLangThread.getStackTrace();
        if (localObject3 != null)
        {
          int i = 0;
          while (i < localObject3.length)
          {
            ((StringBuilder)localObject2).append(localObject3[i].toString());
            ((StringBuilder)localObject2).append("\n");
            i += 1;
          }
        }
        ((HashMap)localObject1).put("stack", ((StringBuilder)localObject2).toString());
      }
      ((HashMap)localObject1).put("threadname", paramQBThreadRunInfo.jdField_a_of_type_JavaLangThread.getName());
      ((HashMap)localObject1).put("poolname", paramQBThreadRunInfo.jdField_a_of_type_JavaLangString);
      if (paramQBThreadRunInfo.b > 0L) {
        ((HashMap)localObject1).put("time", String.valueOf(paramQBThreadRunInfo.b - paramQBThreadRunInfo.jdField_a_of_type_Long));
      } else {
        ((HashMap)localObject1).put("time", String.valueOf(System.currentTimeMillis() - paramQBThreadRunInfo.jdField_a_of_type_Long));
      }
      paramQBThreadRunInfo = mReportListener;
      if (paramQBThreadRunInfo != null) {
        paramQBThreadRunInfo.onReport("MTT_THREADPOOL_CHECKER_EVENT", (HashMap)localObject1);
      }
      paramQBThreadRunInfo = new StringBuilder();
      paramQBThreadRunInfo.append("doReport:");
      paramQBThreadRunInfo.append(((HashMap)localObject1).toString());
      paramQBThreadRunInfo.toString();
      return;
    }
  }
  
  public static void doThreadCheck()
  {
    try
    {
      Iterator localIterator = new ConcurrentHashMap(mMapRunStatus).entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (localEntry != null)
        {
          QBThreadRunInfo localQBThreadRunInfo = (QBThreadRunInfo)localEntry.getValue();
          if (localQBThreadRunInfo != null)
          {
            long l = System.currentTimeMillis();
            if ((localQBThreadRunInfo.jdField_a_of_type_Long > 0L) && (localQBThreadRunInfo.b <= 0L))
            {
              if (l - localQBThreadRunInfo.jdField_a_of_type_Long > 5000L) {
                doReport(localQBThreadRunInfo, true);
              }
            }
            else if ((localQBThreadRunInfo.jdField_a_of_type_Long > 0L) && (localQBThreadRunInfo.b > 0L) && (l - localQBThreadRunInfo.jdField_a_of_type_Long > 60000L)) {
              mMapRunStatus.remove(localEntry.getKey());
            }
          }
        }
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public static String getReportExtInfo(Object paramObject)
  {
    if (paramObject != null) {}
    try
    {
      Method localMethod = Class.forName(paramObject.getClass().getName()).getMethod("getReportString", new Class[0]);
      localMethod.setAccessible(true);
      paramObject = (String)localMethod.invoke(paramObject, null);
      return (String)paramObject;
    }
    catch (Throwable paramObject) {}
    return "";
    return "";
  }
  
  public static void loopThreadCheck()
  {
    Handler localHandler = mCheckerHandler;
    if ((localHandler != null) && (!mIsPause))
    {
      localHandler.removeMessages(2);
      mCheckerHandler.obtainMessage(2);
      mCheckerHandler.sendEmptyMessageDelayed(2, 30000L);
    }
  }
  
  public static void pauseWatcherChecker()
  {
    if (mIsEnable)
    {
      if (!mIsInit) {
        return;
      }
      mIsPause = true;
      Handler localHandler = mCheckerHandler;
      if (localHandler != null) {
        localHandler.removeMessages(2);
      }
      mInBackgroundTime = System.currentTimeMillis();
      mOutBackgroundTime = -2147483648L;
      return;
    }
  }
  
  public static void report(@NonNull QBThreadRunInfo paramQBThreadRunInfo, long paramLong)
  {
    Object localObject = mCheckerHandler;
    if (localObject != null)
    {
      localObject = ((Handler)localObject).obtainMessage(1);
      ((Message)localObject).obj = paramQBThreadRunInfo;
      ((Message)localObject).arg1 = 2;
      mCheckerHandler.sendMessageDelayed((Message)localObject, paramLong);
    }
  }
  
  public static void resumeWatcherChecker()
  {
    if (mIsEnable)
    {
      if (!mIsInit) {
        return;
      }
      mIsPause = false;
      loopThreadCheck();
      mInBackgroundTime = 2147483647L;
      mOutBackgroundTime = System.currentTimeMillis();
      return;
    }
  }
  
  public static void setReportListener(IQBThreadTimeoutWatcherReportListener paramIQBThreadTimeoutWatcherReportListener)
  {
    mReportListener = paramIQBThreadTimeoutWatcherReportListener;
  }
  
  public static void startWatcher()
  {
    if (mIsInit) {
      return;
    }
    mIsInit = true;
    mCheckerHandler = new Handler(BrowserExecutorSupplier.getDebugWatcherLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        super.handleMessage(paramAnonymousMessage);
        int i = paramAnonymousMessage.what;
        boolean bool = true;
        if (i == 1)
        {
          if ((paramAnonymousMessage.obj instanceof QBThreadRunInfo))
          {
            QBThreadRunInfo localQBThreadRunInfo = (QBThreadRunInfo)paramAnonymousMessage.obj;
            if (paramAnonymousMessage.arg1 != 1) {
              bool = false;
            }
            QBThreadTimeoutWatcher.doReport(localQBThreadRunInfo, bool);
          }
        }
        else if (paramAnonymousMessage.what == 2)
        {
          QBThreadTimeoutWatcher.doThreadCheck();
          QBThreadTimeoutWatcher.loopThreadCheck();
        }
      }
    };
    loopThreadCheck();
  }
  
  public static abstract interface IQBThreadTimeoutWatcherReportListener
  {
    public abstract void onReport(String paramString, HashMap<String, String> paramHashMap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\debug\QBThreadTimeoutWatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */