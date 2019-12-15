package com.tencent.tbs.common.beacon;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.tencent.beacontbs.event.c;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.ReflectionUtils;
import com.tencent.tbs.common.MTT.UserBehaviorPV;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsSmttServiceProxy;
import com.tencent.tbs.common.baseinfo.TbsWupManager;
import com.tencent.tbs.common.service.QBSmttServiceProxy;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class X5CoreBeaconUploader
{
  private static final String APP_KEY = "AppKey";
  private static String LAST_UPLOAD_DEVCE_TIME = "last_upload_device_time";
  public static final int MAX_GUID_RETYR_TIME = 5;
  private static final int MAX_LOAD_BEACON_RETRY = 5;
  private static final int MAX_STAT_COUNT = 10;
  public static final String TAG = "X5CoreBeaconUploader";
  private static final long TIME_24_HOUR = 86400000L;
  private static long lastUploadTime = -1L;
  public static int mAccumulationValue;
  private static String mAppKey;
  private static Context mBeaconContext;
  private static boolean mHasDoneBeaconTask;
  private static boolean mIsInitBeacon;
  private static byte[] sBeaconLock = new byte[0];
  private static X5CoreBeaconUploader sInstance;
  private boolean mEnable = false;
  private String mGUID = null;
  private int mGuidRetryTime = 0;
  private byte[] mPendingLock = new byte[0];
  private List<X5CoreBeaconUploadTask> mPendingTasks = null;
  private Object mProtocolLock = new byte[0];
  int mStatCount = 0;
  private HashMap<String, UserBehaviorPV> mUserBehaviorPV = null;
  private HashMap<String, UserBehaviorPV> mUserBehaviorPVForTbs = null;
  private HashMap<String, UserBehaviorPV> mUserBehaviorePVForMiniQB = null;
  private Handler mWorkerHandler = null;
  private Looper mWorkerLooper = null;
  private HandlerThread mWorkerThread = null;
  
  static
  {
    mBeaconContext = null;
    mIsInitBeacon = false;
    mHasDoneBeaconTask = false;
    mAccumulationValue = -1;
    mAppKey = null;
  }
  
  private X5CoreBeaconUploader(Context paramContext)
  {
    this.mWorkerThread.start();
    this.mWorkerLooper = this.mWorkerThread.getLooper();
    this.mWorkerHandler = new Handler(this.mWorkerLooper);
    mBeaconContext = paramContext;
  }
  
  private HashMap<String, UserBehaviorPV> generateProtocalMap(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (this.mUserBehaviorePVForMiniQB == null) {
        this.mUserBehaviorePVForMiniQB = new HashMap();
      }
      return this.mUserBehaviorePVForMiniQB;
    }
    if (this.mUserBehaviorPVForTbs == null) {
      this.mUserBehaviorPVForTbs = new HashMap();
    }
    return this.mUserBehaviorPVForTbs;
  }
  
  public static X5CoreBeaconUploader getInstance(Context paramContext)
  {
    synchronized (sBeaconLock)
    {
      if (sInstance == null) {
        if (mBeaconContext != null) {
          sInstance = new X5CoreBeaconUploader(mBeaconContext);
        } else if (paramContext != null) {
          sInstance = new X5CoreBeaconUploader(paramContext);
        }
      }
      return sInstance;
    }
  }
  
  public static String getQIMEI()
  {
    try
    {
      String str = c.d();
      return str;
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    return "";
  }
  
  private void initBeacon()
  {
    if (!mIsInitBeacon)
    {
      mIsInitBeacon = true;
      this.mEnable = mBeaconContext.getSharedPreferences("x5_proxy_setting", 0).getBoolean("log_beaconupload", false);
      boolean bool = this.mEnable;
      c.a(bool, bool);
      c.c("0M300GVKSH1IMBEK");
      Object localObject = TbsSmttServiceProxy.getInstance().getX5CoreVersion();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("coreVer =");
      localStringBuilder.append((String)localObject);
      localStringBuilder.toString();
      if (localObject != null) {
        c.a((String)localObject);
      } else {
        c.a("0");
      }
      localObject = new HashMap();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(TbsInfoUtils.getQUA2());
      localStringBuilder.append(" ");
      ((HashMap)localObject).put("QUA2", localStringBuilder.toString());
      c.a((Map)localObject);
      c.a(mBeaconContext);
    }
  }
  
  private void notifyPendingTasks()
  {
    if (TextUtils.isEmpty(this.mGUID)) {
      return;
    }
    for (;;)
    {
      int i;
      synchronized (this.mPendingLock)
      {
        if ((this.mPendingTasks != null) && (!this.mPendingTasks.isEmpty()))
        {
          i = this.mPendingTasks.size() - 1;
          if (i >= 0)
          {
            Object localObject1 = (X5CoreBeaconUploadTask)this.mPendingTasks.get(i);
            if (localObject1 == null)
            {
              this.mPendingTasks.remove(i);
            }
            else
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("notify pending task, upload ");
              localStringBuilder.append(((X5CoreBeaconUploadTask)localObject1).getEventName());
              localStringBuilder.toString();
              if (!postTaskToThread((X5CoreBeaconUploadTask)localObject1))
              {
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("notify pending tasks async, postTaskToThread fail. Pending task remains = ");
                ((StringBuilder)localObject1).append(this.mPendingTasks.size());
                ((StringBuilder)localObject1).toString();
              }
              else
              {
                this.mPendingTasks.remove(i);
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("notify pending task async,  postTaskToThread succ. Pending task remains = ");
                ((StringBuilder)localObject1).append(this.mPendingTasks.size());
                ((StringBuilder)localObject1).toString();
              }
            }
          }
        }
        else
        {
          return;
        }
      }
      i -= 1;
    }
  }
  
  public static void setAppKey(String paramString)
  {
    mAppKey = paramString;
  }
  
  public static void setContext(Context paramContext)
  {
    mBeaconContext = paramContext;
  }
  
  private boolean shouldReportDeviceInfo()
  {
    long l1 = System.currentTimeMillis();
    if (lastUploadTime < 0L)
    {
      Context localContext = mBeaconContext;
      if (localContext != null) {
        lastUploadTime = localContext.getSharedPreferences("x5_proxy_setting", 0).getLong(LAST_UPLOAD_DEVCE_TIME, 0L);
      }
    }
    long l2 = lastUploadTime;
    return (l1 - l2 >= 86400000L) || (l1 < l2);
  }
  
  private boolean startBootUpload()
  {
    if (TextUtils.isEmpty(this.mGUID))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("startBootUpload called, mGuid is empty, try get guid, retry =");
      localStringBuilder.append(this.mGuidRetryTime);
      localStringBuilder.toString();
      if (GUIDFactory.getInstance().getStrGuid() != null) {
        this.mGUID = GUIDFactory.getInstance().getStrGuid();
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("startBootUpload called, try get guid=");
      localStringBuilder.append(this.mGUID);
      localStringBuilder.toString();
      if (TextUtils.isEmpty(this.mGUID))
      {
        int i = this.mGuidRetryTime;
        if (i < 5)
        {
          this.mGuidRetryTime = (i + 1);
          postTaskToThread(new Runnable()
          {
            public void run()
            {
              X5CoreBeaconUploader.this.startBootUpload();
            }
          }, 5000L);
          return false;
        }
      }
    }
    c.b(this.mGUID);
    notifyPendingTasks();
    return true;
  }
  
  private void uploadDeviceInfo()
  {
    lastUploadTime = System.currentTimeMillis();
    Object localObject1 = mBeaconContext;
    if (localObject1 != null) {
      ((Context)localObject1).getSharedPreferences("x5_proxy_setting", 0).edit().putLong(LAST_UPLOAD_DEVCE_TIME, lastUploadTime).commit();
    }
    Object localObject3 = "0";
    str2 = "0";
    Context localContext = mBeaconContext;
    localObject1 = localObject3;
    Object localObject2 = str2;
    if (localContext != null)
    {
      localObject1 = localObject3;
      localObject2 = str2;
      if (localContext.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0) {
        localObject1 = localObject3;
      }
    }
    try
    {
      localObject2 = ((TelephonyManager)mBeaconContext.getSystemService("phone")).getSimSerialNumber();
      localObject1 = localObject2;
      localObject3 = Build.SERIAL;
      localObject1 = localObject2;
      localObject2 = localObject3;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        String str1 = str2;
      }
    }
    localObject3 = new HashMap();
    ((HashMap)localObject3).put("simNo", localObject1);
    ((HashMap)localObject3).put("serNo", localObject2);
    localObject1 = (String)ReflectionUtils.invokeStatic("com.tencent.smtt.sdk.QbSdk", "getTID");
    if (localObject1 != null) {
      ((HashMap)localObject3).put("tidNo", localObject1);
    }
    upLoadToBeacon("MTT_CORE_DEVICE_INFO", (Map)localObject3, false);
  }
  
  private void uploadToBeaconUserBehavior(boolean paramBoolean)
  {
    synchronized (this.mProtocolLock)
    {
      HashMap localHashMap1 = generateProtocalMap(paramBoolean);
      if (!localHashMap1.isEmpty())
      {
        Object localObject3 = changeStructToString(new ArrayList(localHashMap1.values()));
        HashMap localHashMap2 = new HashMap();
        localHashMap2.put("TBSUserBehavior", localObject3);
        if (paramBoolean) {
          localHashMap2.put("MTT_MINIQB_VERSION", TbsBaseModuleShell.getMiniQBVersion());
        }
        int i = PublicSettingManager.getInstance().getTbsVideoVersion();
        if (i > 0)
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("tbsVideoVersion:");
          ((StringBuilder)localObject3).append(i);
          LogUtils.d("X5CoreBeaconUploader", ((StringBuilder)localObject3).toString());
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append(i);
          ((StringBuilder)localObject3).append("");
          localHashMap2.put("TBS_VIDEO_VERSION", ((StringBuilder)localObject3).toString());
        }
        upLoadToBeacon("MTT_CORE_STAT_PROTOCOL", localHashMap2);
        localHashMap1.clear();
      }
      return;
    }
  }
  
  protected void addPendingTask(X5CoreBeaconUploadTask paramX5CoreBeaconUploadTask)
  {
    if (paramX5CoreBeaconUploadTask == null) {
      return;
    }
    synchronized (this.mPendingLock)
    {
      if (this.mPendingTasks == null) {
        this.mPendingTasks = new ArrayList();
      }
      this.mPendingTasks.add(paramX5CoreBeaconUploadTask);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("add pending task ok, detai=");
      localStringBuilder.append(paramX5CoreBeaconUploadTask.toString());
      localStringBuilder.append(", pending size=");
      localStringBuilder.append(this.mPendingTasks.size());
      localStringBuilder.toString();
      return;
    }
  }
  
  public String changeStructToString(ArrayList<UserBehaviorPV> paramArrayList)
  {
    String str = "";
    int k = paramArrayList.size();
    int j = 0;
    int i;
    if (k > 0)
    {
      str = "";
      i = 0;
      while (i < k)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(str);
        ((StringBuilder)localObject).append(((UserBehaviorPV)paramArrayList.get(i)).getKeyValue());
        ((StringBuilder)localObject).append(",");
        ((StringBuilder)localObject).append(((UserBehaviorPV)paramArrayList.get(i)).getIPV());
        ((StringBuilder)localObject).append(";");
        str = ((StringBuilder)localObject).toString();
        i += 1;
      }
    }
    Object localObject = str;
    if (k > 0)
    {
      i = j;
      for (;;)
      {
        j = k - 1;
        if (i >= j) {
          break;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(str);
        ((StringBuilder)localObject).append(((UserBehaviorPV)paramArrayList.get(i)).getKeyValue());
        ((StringBuilder)localObject).append("=");
        ((StringBuilder)localObject).append(((UserBehaviorPV)paramArrayList.get(i)).getIPV());
        ((StringBuilder)localObject).append("&");
        str = ((StringBuilder)localObject).toString();
        i += 1;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).append(((UserBehaviorPV)paramArrayList.get(j)).getKeyValue());
      ((StringBuilder)localObject).append("=");
      ((StringBuilder)localObject).append(((UserBehaviorPV)paramArrayList.get(j)).getIPV());
      localObject = ((StringBuilder)localObject).toString();
    }
    paramArrayList = new StringBuilder();
    paramArrayList.append("UserBehaviorPV data:");
    paramArrayList.append((String)localObject);
    paramArrayList.toString();
    return (String)localObject;
  }
  
  public void doOverNightUpload()
  {
    if (!mHasDoneBeaconTask)
    {
      mHasDoneBeaconTask = true;
      postTaskToThread(new Runnable()
      {
        public void run()
        {
          X5CoreBeaconUploader.access$102(false);
        }
      }, 180000L);
      if (TbsWupManager.getInstance().isNewDay())
      {
        uploadToBeaconUserBehavior();
        flushBeaconDB(false);
        c.a();
      }
    }
  }
  
  protected void doUploadToBeacon(String paramString, boolean paramBoolean1, long paramLong1, long paramLong2, Map<String, String> paramMap, boolean paramBoolean2)
  {
    paramBoolean1 = c.a(paramString, paramBoolean1, paramLong1, paramLong2, paramMap, paramBoolean2);
    paramString = new StringBuilder();
    paramString.append("doUploadToBeacon finish , result=");
    paramString.append(paramBoolean1);
    paramString.toString();
  }
  
  protected void flushBeaconDB(boolean paramBoolean)
  {
    c.a(paramBoolean);
  }
  
  protected boolean initBeaconAndGetGuidIfNeeded()
  {
    initBeacon();
    return startBootUpload();
  }
  
  public void onReportAppLogInfo(String paramString, boolean paramBoolean)
  {
    if (QBSmttServiceProxy.getLocalSmttService() != null) {
      QBSmttServiceProxy.getInstance().onReportAppLogInfo(paramString, paramBoolean);
    }
  }
  
  protected boolean postTaskToThread(X5CoreBeaconUploadTask paramX5CoreBeaconUploadTask)
  {
    if (paramX5CoreBeaconUploadTask != null)
    {
      localObject = this.mWorkerHandler;
      if (localObject != null)
      {
        bool = ((Handler)localObject).post(paramX5CoreBeaconUploadTask);
        break label24;
      }
    }
    boolean bool = false;
    label24:
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("postTaskToThread ends: result = ");
    ((StringBuilder)localObject).append(bool);
    ((StringBuilder)localObject).append(",  details = ");
    ((StringBuilder)localObject).append(paramX5CoreBeaconUploadTask);
    if (((StringBuilder)localObject).toString() == null) {
      return bool;
    }
    paramX5CoreBeaconUploadTask.toString();
    return bool;
  }
  
  protected boolean postTaskToThread(Runnable paramRunnable, long paramLong)
  {
    if (paramRunnable != null)
    {
      Handler localHandler = this.mWorkerHandler;
      if (localHandler != null) {
        return localHandler.postDelayed(paramRunnable, paramLong);
      }
    }
    return false;
  }
  
  public void savetobeacon()
  {
    if (!TextUtils.isEmpty(this.mGUID))
    {
      notifyPendingTasks();
      uploadToBeaconUserBehavior();
      if (this.mPendingTasks != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("after notify pending tasks, current pending size = ");
        localStringBuilder.append(this.mPendingTasks.size());
        localStringBuilder.toString();
        this.mPendingTasks.clear();
      }
      flushBeaconDB(false);
    }
  }
  
  public void setLogAble(boolean paramBoolean)
  {
    c.a(paramBoolean, paramBoolean);
  }
  
  public void upLoadToBeacon(String paramString, Map<String, String> paramMap)
  {
    if (QBSmttServiceProxy.getLocalSmttService() != null) {
      QBSmttServiceProxy.getInstance().upLoadToBeacon(paramString, paramMap, false);
    } else {
      upLoadToBeacon(paramString, paramMap, this.mEnable);
    }
    if (shouldReportDeviceInfo()) {
      uploadDeviceInfo();
    }
  }
  
  public void upLoadToBeacon(String paramString, Map<String, String> paramMap, boolean paramBoolean)
  {
    if (QBSmttServiceProxy.getLocalSmttService() != null)
    {
      QBSmttServiceProxy.getInstance().upLoadToBeacon(paramString, paramMap, paramBoolean);
      return;
    }
    upLoadToBeacon(paramString, true, -1L, -1L, paramMap, paramBoolean);
  }
  
  public void upLoadToBeacon(String paramString, boolean paramBoolean1, long paramLong1, long paramLong2, Map<String, String> paramMap, boolean paramBoolean2)
  {
    if (!PublicSettingManager.getInstance().getBeaconUploadEnable()) {
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("upLoadToBeacon called, detail:eventName=");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(", isSucc=");
    ((StringBuilder)localObject).append(paramBoolean1);
    ((StringBuilder)localObject).append(", elapse=");
    ((StringBuilder)localObject).append(paramLong1);
    ((StringBuilder)localObject).append(", size=");
    ((StringBuilder)localObject).append(paramLong2);
    ((StringBuilder)localObject).append(", isrealtime=");
    ((StringBuilder)localObject).append(paramBoolean2);
    ((StringBuilder)localObject).toString();
    if (paramMap != null)
    {
      localObject = mAppKey;
      if (localObject != null) {
        paramMap.put("AppKey", localObject);
      }
    }
    paramString = new X5CoreBeaconUploadTask(paramString, paramBoolean1, paramLong1, paramLong2, paramMap, paramBoolean2);
    paramString.setBeaconUploader(this);
    if (!postTaskToThread(paramString)) {
      addPendingTask(paramString);
    }
  }
  
  public void uploadToBeaconUserBehavior()
  {
    uploadToBeaconUserBehavior(false);
    uploadToBeaconUserBehavior(true);
  }
  
  public void userBehaviorStatistics(UserBehaviorPV paramUserBehaviorPV, boolean paramBoolean)
  {
    userBehaviorStatistics(paramUserBehaviorPV, paramBoolean, false);
  }
  
  public void userBehaviorStatistics(final UserBehaviorPV paramUserBehaviorPV, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    this.mWorkerHandler.post(new Runnable()
    {
      public void run()
      {
        synchronized (X5CoreBeaconUploader.this.mProtocolLock)
        {
          if (paramUserBehaviorPV.mBehaviorType == -1) {
            localObject1 = paramUserBehaviorPV.mBehaviorAction;
          } else {
            localObject1 = Integer.toString(paramUserBehaviorPV.mBehaviorType);
          }
          if (!BeaconUserBehaviorManager.getInstance(X5CoreBeaconUploader.mBeaconContext).shouldUploadToBeacon((String)localObject1)) {
            return;
          }
          Object localObject5 = X5CoreBeaconUploader.this.generateProtocalMap(paramBoolean2);
          Object localObject4 = (UserBehaviorPV)((HashMap)localObject5).get(localObject1);
          if (localObject4 == null)
          {
            ((HashMap)localObject5).put(localObject1, paramUserBehaviorPV);
            localObject4 = new StringBuilder();
            ((StringBuilder)localObject4).append("UB : key=");
            ((StringBuilder)localObject4).append((String)localObject1);
            ((StringBuilder)localObject4).append(" pv=");
            ((StringBuilder)localObject4).append(paramUserBehaviorPV.mPV);
            ((StringBuilder)localObject4).toString();
          }
          else
          {
            if (paramBoolean1) {
              i = ((UserBehaviorPV)localObject4).mPV + paramUserBehaviorPV.mPV;
            } else {
              i = paramUserBehaviorPV.mPV;
            }
            ((UserBehaviorPV)localObject4).mPV = i;
            localObject5 = new StringBuilder();
            ((StringBuilder)localObject5).append("UB : key=");
            ((StringBuilder)localObject5).append((String)localObject1);
            ((StringBuilder)localObject5).append(" pv=");
            ((StringBuilder)localObject5).append(((UserBehaviorPV)localObject4).mPV);
            ((StringBuilder)localObject5).toString();
          }
          Object localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("userBehaviorStatistics mStatCount=");
          ((StringBuilder)localObject1).append(X5CoreBeaconUploader.this.mStatCount);
          ((StringBuilder)localObject1).toString();
          if (X5CoreBeaconUploader.mAccumulationValue == -1) {
            X5CoreBeaconUploader.mAccumulationValue = PublicSettingManager.getInstance().getAccumulationValueUB();
          }
          localObject1 = X5CoreBeaconUploader.this;
          int i = ((X5CoreBeaconUploader)localObject1).mStatCount;
          ((X5CoreBeaconUploader)localObject1).mStatCount = (i + 1);
          if (i >= X5CoreBeaconUploader.mAccumulationValue)
          {
            X5CoreBeaconUploader.this.uploadToBeaconUserBehavior();
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("统计埋点  reach the max stat count, send to beacon, count= ");
            ((StringBuilder)localObject1).append(X5CoreBeaconUploader.this.mStatCount);
            ((StringBuilder)localObject1).append(",max count=");
            ((StringBuilder)localObject1).append(X5CoreBeaconUploader.mAccumulationValue);
            ((StringBuilder)localObject1).toString();
            X5CoreBeaconUploader.this.mStatCount = 0;
          }
          return;
        }
      }
    });
  }
  
  public void userBehaviorStatistics(String paramString)
  {
    if (QBSmttServiceProxy.getLocalSmttService() != null)
    {
      QBSmttServiceProxy.getInstance().userBehaviorStatistics(paramString);
    }
    else
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("userBehaviorStatistics upload failed action: ");
      localStringBuilder.append(paramString);
      localStringBuilder.toString();
    }
    userBehaviorStatistics(paramString, 1, true, false);
  }
  
  public void userBehaviorStatistics(String paramString, int paramInt)
  {
    userBehaviorStatistics(paramString, paramInt, true, false);
  }
  
  public void userBehaviorStatistics(String paramString, int paramInt, boolean paramBoolean)
  {
    UserBehaviorPV localUserBehaviorPV = new UserBehaviorPV();
    localUserBehaviorPV.mBehaviorAction = paramString;
    localUserBehaviorPV.mPV = paramInt;
    userBehaviorStatistics(localUserBehaviorPV, paramBoolean);
  }
  
  public void userBehaviorStatistics(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    UserBehaviorPV localUserBehaviorPV = new UserBehaviorPV();
    localUserBehaviorPV.mBehaviorAction = paramString;
    localUserBehaviorPV.mPV = paramInt;
    userBehaviorStatistics(localUserBehaviorPV, paramBoolean1, paramBoolean2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\beacon\X5CoreBeaconUploader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */