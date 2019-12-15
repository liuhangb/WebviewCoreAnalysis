package com.tencent.common.serverconfig.netchecker;

import android.content.Context;
import android.os.SystemClock;
import com.tencent.basesupport.FLogger;
import com.tencent.common.connectivitydetect.ConnectivityDetector;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class BaseWupSelfChecker
  implements Runnable
{
  protected static int MAX_WAIT_CHECK_TIME_SECONDS = 16;
  protected static long SELF_CHECK_TIME_GAP = 1200000L;
  protected static ConcurrentHashMap<String, Long> sCheckingMap = new ConcurrentHashMap();
  protected ISelfCheckCallback mCallback = null;
  protected String mCheckTag = "";
  protected Context mContext;
  protected ExecutorService mCoreTaskExecuter = null;
  
  public BaseWupSelfChecker(String paramString, Context paramContext)
  {
    this.mCheckTag = paramString;
    this.mContext = paramContext;
    this.mCoreTaskExecuter = BrowserExecutorSupplier.getInstance().getTimeOutExecutor();
  }
  
  protected void doStartSelfCheck()
  {
    ArrayList localArrayList = provideAddress();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("doStartSelfCheck: currentIps = ");
    ((StringBuilder)localObject).append(localArrayList);
    FLogger.d("BaseWupSelfChecker", ((StringBuilder)localObject).toString());
    if ((localArrayList != null) && (!localArrayList.isEmpty()) && (localArrayList.size() <= 10))
    {
      boolean bool = ConnectivityDetector.detectWithCDNFile();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("doStartSelfCheck: networkCheckResult = ");
      ((StringBuilder)localObject).append(bool);
      FLogger.d("BaseWupSelfChecker", ((StringBuilder)localObject).toString());
      int j = localArrayList.size();
      if (bool)
      {
        FLogger.d("BaseWupSelfChecker", "doStartSelfCheck: network is OK, begin check ");
        CountDownLatch localCountDownLatch = new CountDownLatch(j);
        localObject = new WupNetworkCheckTask[j];
        int i = 0;
        while (i < j)
        {
          localObject[i] = new WupNetworkCheckTask((String)localArrayList.get(i), localCountDownLatch);
          try
          {
            this.mCoreTaskExecuter.execute(localObject[i]);
            i += 1;
          }
          catch (Throwable localThrowable1)
          {
            localThrowable1.printStackTrace();
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("doStartSelfCheck: error starting tasks, e=");
            ((StringBuilder)localObject).append(localThrowable1);
            FLogger.d("BaseWupSelfChecker", ((StringBuilder)localObject).toString());
            onCheckComplete(this.mCheckTag, new ArrayList());
            return;
          }
        }
        try
        {
          FLogger.d("BaseWupSelfChecker", "doStartSelfCheck: begin wait check result");
          localCountDownLatch.await(MAX_WAIT_CHECK_TIME_SECONDS, TimeUnit.SECONDS);
          FLogger.d("BaseWupSelfChecker", "doStartSelfCheck: end wait check result");
        }
        catch (Throwable localThrowable2)
        {
          StringBuilder localStringBuilder2 = new StringBuilder();
          localStringBuilder2.append("doStartSelfCheck: exception in latch.await, error=");
          localStringBuilder2.append(localThrowable2);
          FLogger.d("BaseWupSelfChecker", localStringBuilder2.toString());
        }
        StringBuilder localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("doStartSelfCheck: beofore check, ips=");
        localStringBuilder1.append(localThrowable1);
        FLogger.d("BaseWupSelfChecker", localStringBuilder1.toString());
        kickBadAddressOut((WupNetworkCheckTask[])localObject, localThrowable1);
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("doStartSelfCheck: after check, ips=");
        ((StringBuilder)localObject).append(localThrowable1);
        FLogger.d("BaseWupSelfChecker", ((StringBuilder)localObject).toString());
        onCheckComplete(this.mCheckTag, localThrowable1);
        return;
      }
      FLogger.d("BaseWupSelfChecker", "doStartSelfCheck: network is NOT OK, DO NOT check ");
      onCheckComplete(this.mCheckTag, new ArrayList());
      return;
    }
    FLogger.d("BaseWupSelfChecker", "doStartSelfCheck: param not available, retrun ");
    onCheckComplete(this.mCheckTag, new ArrayList());
  }
  
  protected void kickBadAddressOut(WupNetworkCheckTask[] paramArrayOfWupNetworkCheckTask, ArrayList<String> paramArrayList)
  {
    if ((paramArrayOfWupNetworkCheckTask != null) && (paramArrayList != null) && (paramArrayOfWupNetworkCheckTask.length != 0) && (!paramArrayList.isEmpty()) && (paramArrayList.size() == paramArrayOfWupNetworkCheckTask.length))
    {
      FLogger.d("BaseWupSelfChecker", "processCheckResults: start!!!");
      paramArrayList = paramArrayList.iterator();
      if (paramArrayList == null)
      {
        FLogger.d("BaseWupSelfChecker", "processCheckResults: iterator==null, ignore");
        return;
      }
      int i = 0;
      while (paramArrayList.hasNext())
      {
        String str = (String)paramArrayList.next();
        Object localObject = paramArrayOfWupNetworkCheckTask[i];
        if (localObject != null)
        {
          boolean bool = ((WupNetworkCheckTask)localObject).getResult();
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("processCheckResults: begin check one ip, ip=");
          ((StringBuilder)localObject).append(str);
          ((StringBuilder)localObject).append(", result=");
          ((StringBuilder)localObject).append(bool);
          FLogger.d("BaseWupSelfChecker", ((StringBuilder)localObject).toString());
          if (!bool)
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("processCheckResults: ip=");
            ((StringBuilder)localObject).append(str);
            ((StringBuilder)localObject).append(", not valid ,remove");
            FLogger.d("BaseWupSelfChecker", ((StringBuilder)localObject).toString());
            paramArrayList.remove();
          }
        }
        i += 1;
      }
      return;
    }
    FLogger.d("BaseWupSelfChecker", "processCheckResults: param not valid, return");
  }
  
  protected abstract void onCheckComplete(String paramString, List<String> paramList);
  
  protected abstract ArrayList<String> provideAddress();
  
  public final void run()
  {
    FLogger.d("BaseWupSelfChecker", "startSelfCheckIPV4List: BEGIN self check in thread");
    doStartSelfCheck();
    FLogger.d("BaseWupSelfChecker", "startSelfCheckIPV4List: END self check in thread");
  }
  
  public void setCallback(ISelfCheckCallback paramISelfCheckCallback)
  {
    this.mCallback = paramISelfCheckCallback;
  }
  
  public boolean startSelfCheck()
  {
    try
    {
      Long localLong = (Long)sCheckingMap.get(this.mCheckTag);
      long l = SystemClock.elapsedRealtime();
      if ((localLong != null) && (l - localLong.longValue() < SELF_CHECK_TIME_GAP))
      {
        FLogger.d("BaseWupSelfChecker", "startSelfCheckIPV4List: but time gap not available");
        return false;
      }
      sCheckingMap.put(this.mCheckTag, Long.valueOf(l));
      try
      {
        this.mCoreTaskExecuter.execute(this);
      }
      catch (Throwable localThrowable)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("startSelfCheckIPV4List: fail to post to thread, e=");
        localStringBuilder.append(localThrowable);
        FLogger.d("BaseWupSelfChecker", localStringBuilder.toString());
        localThrowable.printStackTrace();
      }
      return true;
    }
    finally {}
  }
  
  public boolean startSelfCheck(boolean paramBoolean)
  {
    if (paramBoolean) {
      try
      {
        sCheckingMap.remove(this.mCheckTag);
      }
      finally {}
    }
    return startSelfCheck();
  }
  
  public static abstract interface ISelfCheckCallback
  {
    public abstract void onSelfCheckResult(String paramString, List<String> paramList);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\serverconfig\netchecker\BaseWupSelfChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */