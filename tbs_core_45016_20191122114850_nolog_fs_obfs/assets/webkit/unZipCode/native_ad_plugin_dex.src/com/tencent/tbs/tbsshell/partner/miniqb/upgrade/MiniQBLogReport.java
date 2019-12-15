package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.content.Context;
import android.util.Log;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.tbsshell.TBSShellFactory;
import com.tencent.tbs.tbsshell.common.SmttServiceCommon;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class MiniQBLogReport
{
  static final int MAX_CALLSTACK_LENGTH = 1024;
  private String mApn;
  private String mCheckErrorDetail;
  private Context mContext;
  private long mDownConsumeTime;
  private int mDownFinalFlag;
  private String mDownUrl;
  private int mDownloadCancel;
  private int mDownloadMiniQBVersion;
  private long mDownloadSize;
  int mErrorCode;
  private String mFailDetail;
  private int mHttpCode;
  private int mNetworkChange;
  private int mNetworkType;
  private int mPatchUpdateFlag;
  private long mPkgSize;
  private String mResolveIp;
  private int mUnpkgFlag;
  
  public MiniQBLogReport(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    resetArgs();
  }
  
  private void resetArgs()
  {
    this.mDownUrl = null;
    this.mResolveIp = null;
    this.mHttpCode = 0;
    this.mPatchUpdateFlag = 0;
    this.mDownloadCancel = 0;
    this.mUnpkgFlag = 2;
    this.mApn = "unknown";
    this.mNetworkType = 0;
    this.mDownFinalFlag = 2;
    this.mPkgSize = 0L;
    this.mDownConsumeTime = 0L;
    this.mNetworkChange = 1;
    this.mErrorCode = 0;
    this.mCheckErrorDetail = null;
    this.mFailDetail = null;
    this.mDownloadSize = 0L;
  }
  
  public int getDownFinalFlag()
  {
    return this.mDownFinalFlag;
  }
  
  public void report(EventType paramEventType)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("event_type", String.valueOf(paramEventType.value));
    localHashMap.put("qua2", TBSShellFactory.getSmttServiceCommon().getQUA2());
    localHashMap.put("tbs_version", String.valueOf(MiniQBUpgradeManager.getInstance().getTbsCoreVersion()));
    localHashMap.put("miniqb_version", String.valueOf(MiniQBUpgradeManager.getInstance().getMiniQBVersion()));
    paramEventType = this.mContext.getPackageName();
    localHashMap.put("app_name", paramEventType);
    if ("com.tencent.mm".equals(paramEventType)) {
      localHashMap.put("app_version", AppUtil.getAppMetadata(this.mContext, "com.tencent.mm.BuildInfo.CLIENT_VERSION"));
    } else {
      localHashMap.put("app_version", String.valueOf(AppUtil.getAppVersionCode(this.mContext)));
    }
    localHashMap.put("down_url", this.mDownUrl);
    localHashMap.put("resolve_ip", this.mResolveIp);
    localHashMap.put("http_code", String.valueOf(this.mHttpCode));
    localHashMap.put("patch_update_flag", String.valueOf(this.mPatchUpdateFlag));
    localHashMap.put("f_download_cancel", String.valueOf(this.mDownloadCancel));
    localHashMap.put("unpkg_flag", String.valueOf(this.mUnpkgFlag));
    localHashMap.put("apn", this.mApn);
    localHashMap.put("network_type", String.valueOf(this.mNetworkType));
    localHashMap.put("down_final_flag", String.valueOf(this.mDownFinalFlag));
    localHashMap.put("download_size", String.valueOf(this.mDownloadSize));
    localHashMap.put("pkg_size", String.valueOf(this.mPkgSize));
    localHashMap.put("down_consume_time", String.valueOf(this.mDownConsumeTime));
    localHashMap.put("network_change", String.valueOf(this.mNetworkChange));
    localHashMap.put("error_code", String.valueOf(this.mErrorCode));
    localHashMap.put("check_error_detail", this.mCheckErrorDetail);
    localHashMap.put("fail_detail", this.mFailDetail);
    localHashMap.put("down_miniqb_version", String.valueOf(this.mDownloadMiniQBVersion));
    resetArgs();
    paramEventType = localHashMap.keySet().iterator();
    while (paramEventType.hasNext())
    {
      String str1 = (String)paramEventType.next();
      String str2 = (String)localHashMap.get(str1);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("MiniQBLogReport.report key=");
      localStringBuilder.append(str1);
      localStringBuilder.append(",value=");
      localStringBuilder.append(str2);
      localStringBuilder.toString();
    }
    X5CoreBeaconUploader.getInstance(this.mContext).upLoadToBeacon("TBS_MINIQB_UPGRADE", localHashMap);
  }
  
  public void setApn(String paramString)
  {
    this.mApn = paramString;
  }
  
  public void setCheckErrorDetail(String paramString)
  {
    setErrorCode(108);
    this.mCheckErrorDetail = paramString;
  }
  
  public void setDownConsumeTime(long paramLong)
  {
    this.mDownConsumeTime += paramLong;
  }
  
  public void setDownFinalFlag(int paramInt)
  {
    this.mDownFinalFlag = paramInt;
  }
  
  public void setDownloadCancel(int paramInt)
  {
    this.mDownloadCancel = paramInt;
  }
  
  public void setDownloadMiniQBVersion(int paramInt)
  {
    this.mDownloadMiniQBVersion = paramInt;
  }
  
  public void setDownloadSize(long paramLong)
  {
    this.mDownloadSize += paramLong;
  }
  
  public void setDownloadUrl(String paramString)
  {
    if (this.mDownUrl == null)
    {
      this.mDownUrl = paramString;
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mDownUrl);
    localStringBuilder.append(";");
    localStringBuilder.append(paramString);
    this.mDownUrl = localStringBuilder.toString();
  }
  
  public void setErrorCode(int paramInt)
  {
    if ((paramInt != 100) && (paramInt != 110) && (paramInt != 120) && (paramInt != 111) && (paramInt < 400))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("MiniQBLogReport.setErrorCode error occured, errorCode:");
      localStringBuilder.append(paramInt);
      localStringBuilder.toString();
    }
    this.mErrorCode = paramInt;
  }
  
  public void setFailDetail(String paramString)
  {
    this.mFailDetail = paramString;
  }
  
  public void setFailDetail(Throwable paramThrowable)
  {
    if (paramThrowable == null)
    {
      this.mFailDetail = "";
      return;
    }
    String str = Log.getStackTraceString(paramThrowable);
    paramThrowable = str;
    if (str.length() > 1024) {
      paramThrowable = str.substring(0, 1024);
    }
    this.mFailDetail = paramThrowable;
  }
  
  public void setHttpCode(int paramInt)
  {
    this.mHttpCode = paramInt;
  }
  
  public void setInstallErrorCode(int paramInt, String paramString)
  {
    if ((paramInt != 200) && (paramInt != 220) && (paramInt != 221))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("error occured in installation, errorCode:");
      localStringBuilder.append(paramInt);
      localStringBuilder.toString();
    }
    setErrorCode(paramInt);
    setFailDetail(paramString);
    report(EventType.TYPE_INSTALL);
  }
  
  public void setInstallErrorCode(int paramInt, Throwable paramThrowable)
  {
    setFailDetail(paramThrowable);
    setInstallErrorCode(paramInt, this.mFailDetail);
  }
  
  public void setNetworkChange(int paramInt)
  {
    new Exception().printStackTrace();
    this.mNetworkChange = paramInt;
  }
  
  public void setNetworkType(int paramInt)
  {
    this.mNetworkType = paramInt;
  }
  
  public void setPatchUpdateFlag(int paramInt)
  {
    this.mPatchUpdateFlag = paramInt;
  }
  
  public void setPkgSize(long paramLong)
  {
    this.mPkgSize = paramLong;
  }
  
  public void setResolveIp(String paramString)
  {
    this.mResolveIp = paramString;
  }
  
  public void setUnpkgFlag(int paramInt)
  {
    this.mUnpkgFlag = paramInt;
  }
  
  public static enum EventType
  {
    TYPE_DOWNLOAD(0),  TYPE_INSTALL(1),  TYPE_LOAD(2);
    
    int value;
    
    private EventType(int paramInt)
    {
      this.value = paramInt;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\MiniQBLogReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */