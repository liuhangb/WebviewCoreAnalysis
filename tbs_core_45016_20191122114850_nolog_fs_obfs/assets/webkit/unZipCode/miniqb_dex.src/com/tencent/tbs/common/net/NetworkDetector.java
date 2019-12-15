package com.tencent.tbs.common.net;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.tencent.common.http.Apn;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.task.Task;
import com.tencent.tbs.common.task.TaskObserver;
import com.tencent.tbs.common.task.WalledGardenDetectTask;
import com.tencent.tbs.common.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NetworkDetector
  implements TaskObserver
{
  private static final int MAX_RETRY = 3;
  private static final int MSG_RE_DETECT_NETWORK = 100000;
  private static final int RETRY_DELAY_SCEOND = 30000;
  static NetworkDetector mInstance;
  final String TAG = "NetworkDetector";
  Context mContext;
  Handler mHandler = null;
  boolean mIsDetecting = false;
  private int mRetryCount = 0;
  boolean mShowWifiLoginFlag = false;
  List<INetworkDetectorCallback> mWifiConnCallbacks = new ArrayList();
  INetworkDetectorCallback mWifiLoginConnCallbacks = null;
  boolean mWifiLoginNeedCallback = true;
  
  public NetworkDetector(Context paramContext)
  {
    this.mContext = paramContext;
    startHandler();
  }
  
  private boolean addCallback(INetworkDetectorCallback paramINetworkDetectorCallback)
  {
    if (paramINetworkDetectorCallback != null) {
      try
      {
        if (!this.mWifiConnCallbacks.contains(paramINetworkDetectorCallback))
        {
          this.mWifiConnCallbacks.add(paramINetworkDetectorCallback);
          return true;
        }
        return false;
      }
      finally
      {
        paramINetworkDetectorCallback = finally;
        throw paramINetworkDetectorCallback;
      }
    }
    return false;
  }
  
  private void dealWalledGardenTask(Task paramTask)
  {
    this.mIsDetecting = false;
    if ((paramTask != null) && ((paramTask instanceof WalledGardenDetectTask)))
    {
      paramTask = (WalledGardenDetectTask)paramTask;
      if (paramTask.getStatus() == 5)
      {
        this.mWifiLoginNeedCallback = false;
        this.mShowWifiLoginFlag = false;
        dealWifiWebLogin(false);
        if (paramTask.getType() == 1) {
          handelCallback(false);
        }
      }
      else
      {
        StringBuilder localStringBuilder;
        if (paramTask.getType() == 1)
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("dealWalledGardenTask TYPE_WIFI_CONN ");
          localStringBuilder.append(paramTask.isSuccess());
          LogUtils.d("NetworkDetector", localStringBuilder.toString());
          handelCallback(paramTask.isSuccess());
          return;
        }
        int i;
        if (paramTask.getType() == 2)
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("dealWalledGardenTask TYPE_WIFI_LOGIN ");
          localStringBuilder.append(paramTask.isSuccess());
          LogUtils.d("NetworkDetector", localStringBuilder.toString());
          this.mShowWifiLoginFlag = true;
          dealWifiWebLogin(paramTask.isSuccess() ^ true);
          if (!paramTask.isSuccess())
          {
            if (this.mRetryCount < 3)
            {
              paramTask = new StringBuilder();
              paramTask.append("dealWalledGardenTask [LOGIN-openwifi] start reDetector mRetryCount = ");
              paramTask.append(this.mRetryCount);
              LogUtils.d("NetworkDetector", paramTask.toString());
              paramTask = this.mHandler.obtainMessage(100000);
              i = this.mRetryCount;
              this.mHandler.sendMessageDelayed(paramTask, (i * 3 + 1) * 30000);
              this.mRetryCount += 1;
            }
          }
          else {
            handleLoginCallback();
          }
        }
        else if (paramTask.getType() == 3)
        {
          if (!paramTask.isSuccess())
          {
            if (this.mRetryCount < 3)
            {
              paramTask = new StringBuilder();
              paramTask.append("dealWalledGardenTask TYPE_WIFI_RECONN start reDetectorm RetryCount = ");
              paramTask.append(this.mRetryCount);
              LogUtils.d("NetworkDetector", paramTask.toString());
              paramTask = this.mHandler.obtainMessage(100000);
              i = this.mRetryCount;
              this.mHandler.sendMessageDelayed(paramTask, (i * 3 + 1) * 30000);
              this.mRetryCount += 1;
            }
          }
          else {
            handleLoginCallback();
          }
        }
      }
      return;
    }
    LogUtils.d("NetworkDetector", "dealWalledGardenTask task null");
  }
  
  private void dealWifiWebLogin(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      NetworkUtils.showWifiLoginNotify(this.mContext);
      return;
    }
    NetworkUtils.dismissWifiLoginNotify(this.mContext);
  }
  
  private void doWalledGardenCheck(int paramInt)
  {
    try
    {
      LogUtils.d("NetworkDetector", "doWalledGardenCheck() --> ");
      this.mIsDetecting = true;
      WalledGardenDetectTask localWalledGardenDetectTask = new WalledGardenDetectTask();
      localWalledGardenDetectTask.addObserver(this);
      localWalledGardenDetectTask.setType(paramInt);
      runTask(localWalledGardenDetectTask);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static NetworkDetector getInstance(Context paramContext)
  {
    if (mInstance == null) {
      mInstance = new NetworkDetector(paramContext);
    }
    return mInstance;
  }
  
  private void handelCallback(boolean paramBoolean)
  {
    try
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("dealWalledGardenTask handelCallback ");
      ((StringBuilder)localObject1).append(this.mWifiConnCallbacks.size());
      LogUtils.d("NetworkDetector", ((StringBuilder)localObject1).toString());
      localObject1 = this.mWifiConnCallbacks.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        INetworkDetectorCallback localINetworkDetectorCallback = (INetworkDetectorCallback)((Iterator)localObject1).next();
        try
        {
          localINetworkDetectorCallback.onResult(paramBoolean);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
      this.mWifiConnCallbacks.clear();
      return;
    }
    finally {}
  }
  
  /* Error */
  private void handleLoginCallback()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 57	com/tencent/tbs/common/net/NetworkDetector:mWifiLoginConnCallbacks	Lcom/tencent/tbs/common/net/INetworkDetectorCallback;
    //   6: ifnull +25 -> 31
    //   9: aload_0
    //   10: getfield 57	com/tencent/tbs/common/net/NetworkDetector:mWifiLoginConnCallbacks	Lcom/tencent/tbs/common/net/INetworkDetectorCallback;
    //   13: iconst_1
    //   14: invokeinterface 204 2 0
    //   19: goto +12 -> 31
    //   22: astore_1
    //   23: goto +11 -> 34
    //   26: astore_1
    //   27: aload_1
    //   28: invokevirtual 207	java/lang/Exception:printStackTrace	()V
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_1
    //   37: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	38	0	this	NetworkDetector
    //   22	1	1	localObject	Object
    //   26	11	1	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   2	19	22	finally
    //   27	31	22	finally
    //   2	19	26	java/lang/Exception
  }
  
  private void init() {}
  
  public static boolean isInBackground(Context paramContext)
  {
    Object localObject = (ActivityManager)paramContext.getSystemService("activity");
    if (localObject != null)
    {
      localObject = ((ActivityManager)localObject).getRunningTasks(1);
      if ((localObject != null) && (!((List)localObject).isEmpty()) && (!((ActivityManager.RunningTaskInfo)((List)localObject).get(0)).topActivity.getPackageName().equals(paramContext.getPackageName()))) {
        return true;
      }
    }
    return false;
  }
  
  private void startHandler()
  {
    HandlerThread localHandlerThread = new HandlerThread("NetworkDetector", 10);
    localHandlerThread.start();
    this.mHandler = new Handler(localHandlerThread.getLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("handleMessage : ");
        localStringBuilder.append(paramAnonymousMessage.what);
        LogUtils.d("NetworkDetector", localStringBuilder.toString());
        if (paramAnonymousMessage.what == 100000)
        {
          paramAnonymousMessage = NetworkDetector.this;
          paramAnonymousMessage.reDetectWifiConn(paramAnonymousMessage.mWifiLoginConnCallbacks);
        }
      }
    };
  }
  
  private void tryCallback(final INetworkDetectorCallback paramINetworkDetectorCallback, final boolean paramBoolean)
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        try
        {
          paramINetworkDetectorCallback.onResult(paramBoolean);
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }
  
  public void detectWifiConn(INetworkDetectorCallback paramINetworkDetectorCallback)
  {
    LogUtils.d("NetworkDetector", "detectWifiConn");
    if (!Apn.isWifiMode())
    {
      LogUtils.d("NetworkDetector", "detectWifiConn !NetworkUtils.isWifiAvailable");
      tryCallback(paramINetworkDetectorCallback, true);
      return;
    }
    if (!setWifiLoginConnCallbacks(paramINetworkDetectorCallback)) {
      return;
    }
    if (this.mIsDetecting)
    {
      LogUtils.d("NetworkDetector", "detectWifiConn mIsDetecting");
      return;
    }
    doWalledGardenCheck(1);
  }
  
  public void detectWifiWebLogin(INetworkDetectorCallback paramINetworkDetectorCallback)
  {
    LogUtils.d("NetworkDetector", "detectWifiWebLogin");
    if (!setWifiLoginConnCallbacks(paramINetworkDetectorCallback)) {
      return;
    }
    if (this.mIsDetecting)
    {
      LogUtils.d("NetworkDetector", "detectWifiWebLogin mIsDetecting");
      this.mWifiLoginNeedCallback = true;
      return;
    }
    init();
    if (Apn.isWifiMode())
    {
      doWalledGardenCheck(2);
      return;
    }
    this.mShowWifiLoginFlag = false;
    tryCallback(paramINetworkDetectorCallback, true);
  }
  
  public boolean getShowWifiLoginFlag()
  {
    boolean bool = this.mShowWifiLoginFlag;
    this.mShowWifiLoginFlag = false;
    return bool;
  }
  
  public void onTaskCompleted(Task paramTask)
  {
    LogUtils.d("NetworkDetector", "onTaskCompleted(Task task) ");
    dealWalledGardenTask((WalledGardenDetectTask)paramTask);
  }
  
  public void onTaskCreated(Task paramTask) {}
  
  public void onTaskExtEvent(Task paramTask) {}
  
  public void onTaskFailed(Task paramTask)
  {
    LogUtils.d("NetworkDetector", "onTaskFailed");
    dealWalledGardenTask((WalledGardenDetectTask)paramTask);
  }
  
  public void onTaskProgress(Task paramTask) {}
  
  public void onTaskStarted(Task paramTask) {}
  
  public void reDetectWifiConn(INetworkDetectorCallback paramINetworkDetectorCallback)
  {
    LogUtils.d("NetworkDetector", "reDetectWifiConn");
    if (paramINetworkDetectorCallback == null) {
      return;
    }
    if (!Apn.isWifiMode())
    {
      LogUtils.d("NetworkDetector", "reDetectWifiConn !NetworkUtils.isWifiAvailable");
      tryCallback(paramINetworkDetectorCallback, true);
      return;
    }
    if (this.mIsDetecting)
    {
      LogUtils.d("NetworkDetector", "reDetectWifiConn mIsDetecting");
      return;
    }
    doWalledGardenCheck(3);
  }
  
  public void runTask(final Task paramTask)
  {
    BrowserExecutorSupplier.postForDbTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        paramTask.run();
      }
    });
  }
  
  public boolean setWifiLoginConnCallbacks(INetworkDetectorCallback paramINetworkDetectorCallback)
  {
    LogUtils.d("NetworkDetector", "setDetectCallback");
    if (paramINetworkDetectorCallback == null) {
      return false;
    }
    init();
    addCallback(paramINetworkDetectorCallback);
    this.mWifiLoginConnCallbacks = paramINetworkDetectorCallback;
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\net\NetworkDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */