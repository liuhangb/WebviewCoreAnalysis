package com.tencent.tbs.common.beacon;

import com.tencent.tbs.common.utils.DeviceUtils;
import java.util.HashMap;
import java.util.Map;

class X5CoreBeaconUploadTask
  implements Runnable
{
  public static final String TAG = "X5CoreBeaconUploadTask";
  private long mElapse;
  private String mEventName;
  private boolean mIsRealTime;
  private boolean mIsSucceed;
  private Map<String, String> mParams;
  private long mSize;
  private X5CoreBeaconUploader mUploader;
  
  public X5CoreBeaconUploadTask(String paramString, boolean paramBoolean1, long paramLong1, long paramLong2, Map<String, String> paramMap, boolean paramBoolean2)
  {
    this.mEventName = paramString;
    this.mIsSucceed = paramBoolean1;
    this.mElapse = paramLong1;
    this.mSize = paramLong2;
    this.mParams = new HashMap();
    this.mParams.putAll(paramMap);
    this.mIsRealTime = paramBoolean2;
  }
  
  public String getEventName()
  {
    return this.mEventName;
  }
  
  public void run()
  {
    if (this.mUploader == null) {
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("BeaconUploadTask begins: eventName = ");
    ((StringBuilder)localObject).append(this.mEventName);
    ((StringBuilder)localObject).toString();
    if (this.mUploader.initBeaconAndGetGuidIfNeeded())
    {
      if ("TBS_INIT_PERFORMANCE_COLD".equals(this.mEventName))
      {
        localObject = this.mParams;
        if (localObject != null)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("");
          localStringBuilder.append(DeviceUtils.getTotalRAM());
          ((Map)localObject).put("ram", localStringBuilder.toString());
        }
      }
      this.mUploader.doUploadToBeacon(this.mEventName, this.mIsSucceed, this.mElapse, this.mSize, this.mParams, this.mIsRealTime);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Beacon is ready, upload to beacon complete: eventName = ");
      ((StringBuilder)localObject).append(this.mEventName);
      ((StringBuilder)localObject).toString();
      return;
    }
    this.mUploader.addPendingTask(this);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Beacon is NOT ready, add to pending tasks: eventName = ");
    ((StringBuilder)localObject).append(this.mEventName);
    ((StringBuilder)localObject).toString();
  }
  
  public void setBeaconUploader(X5CoreBeaconUploader paramX5CoreBeaconUploader)
  {
    this.mUploader = paramX5CoreBeaconUploader;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("eventName=");
    localStringBuilder.append(this.mEventName);
    localStringBuilder.append(", isSuceed=");
    localStringBuilder.append(this.mIsSucceed);
    localStringBuilder.append(", eplapse=");
    localStringBuilder.append(this.mElapse);
    localStringBuilder.append(", size=");
    localStringBuilder.append(this.mSize);
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\beacon\X5CoreBeaconUploadTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */