package com.tencent.tbs.common.stat;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.common.http.Apn;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.MTT.CommStatData;
import com.tencent.tbs.common.MTT.UserBehaviorPV;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.util.HashMap;
import java.util.Map;

public class TBSStatManager
{
  private static final long MAX_UPLOAD_INTERVAL = 1000L;
  public static final String MINIQB_ENTRY_URL = "miniqb_entry_url";
  public static final String MTT_CORE_DIRECT_ADBLOCK_INFO = "MTT_CORE_PAGE_DIRECT_ADBLOCK_INFO";
  public static final String MTT_CORE_DIRECT_INFO = "MTT_CORE_DIRECT_INFO";
  public static final String MTT_CORE_PAGE_DROP_INFO = "MTT_CORE_PAGE_DROP_INFO";
  public static final String MTT_CORE_PAGE_DURATION = "MTT_CORE_PAGE_DURATION";
  public static final String MTT_CORE_PAGE_ERROR_LOAD_SIMPLE = "MTT_CORE_PAGE_ERROR_LOAD_SIMPLE";
  public static final String MTT_CORE_PAGE_INFO_FAILED = "MTT_CORE_PAGE_INFO_FAILED";
  public static final String MTT_CORE_PAGE_INFO_SUCCESS = "MTT_CORE_PAGE_INFO_SUCCESS";
  public static final String MTT_CORE_PAGE_PERFORMANCE_V3 = "MTT_CORE_PAGE_PERFORMANCE_V3";
  public static final String MTT_CORE_PROXY_FAILED_REPORT = "MTT_CORE_PROXY_FAILED_REPORT";
  public static final String MTT_CORE_PV_INFO = "MTT_CORE_PV_INFO";
  public static final String MTT_CORE_STAT_PROTOCOL = "MTT_CORE_STAT_PROTOCOL";
  public static final String MTT_CORE_USER_BEHAVIOUR_INFO = "MTT_CORE_USER_BEHAVIOUR_INFO";
  public static final String MTT_PARAM_MINIQB_VERSION = "MTT_MINIQB_VERSION";
  public static final byte PV_ADD = 0;
  public static final byte PV_IGNORE = 2;
  public static final byte PV_REPLACE = 1;
  public static final int PV_STAT_ALL = 255;
  public static final int PV_STAT_ENTRY = 3;
  public static final int PV_STAT_OUTER = 2;
  public static final int PV_STAT_TOTAL = 1;
  public static final String QBPluginCommKey = "QBPluginStat";
  public static final String QB_PAGE_LOAD_ERROR_KEY = "QBPageLoadErrorInfoKey";
  public static final int REPORT_BEACON_AND_WUP = 0;
  public static final int REPORT_BEACON_ONLY = 2;
  public static final int REPORT_WUP_ONLY = 1;
  public static final String TBS_INIT_PERFORMANCE = "TBS_INIT_PERFORMANCE";
  public static final String TBS_MINIQB_UPGRADE = "TBS_MINIQB_UPGRADE";
  public static final String TBS_WUP_WATCH = "TBS_WUP_WATCH";
  public static final String UPLOAD_DOWNLOAD_MINIQB = "MTT_UPLOAD_DOWNLOAD_MINIQB";
  private static TBSStatManager mTBSStatManager;
  private long mLastUploadTime = 0L;
  
  public static TBSStatManager getInstance()
  {
    try
    {
      if (mTBSStatManager == null) {
        mTBSStatManager = new TBSStatManager();
      }
      TBSStatManager localTBSStatManager = mTBSStatManager;
      return localTBSStatManager;
    }
    finally {}
  }
  
  public boolean getIsBeaconUploadEnabled(String paramString)
  {
    int i;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
      i = 0;
    }
    return (i == 0) || (i == 2);
  }
  
  public boolean getIsWupUploadEnabled(String paramString)
  {
    int i;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
      i = 0;
    }
    if (i != 0) {
      return i == 1;
    }
    return true;
  }
  
  public boolean isStatPerformance()
  {
    return TbsStatDataManager.getTBSStatManager().isStatPerformance();
  }
  
  public void load()
  {
    TbsStatDataManagerWrapper.getInstance().loadStatManagerData();
  }
  
  public void onReportPageTotalTimeV2(String paramString1, String paramString2, byte paramByte, long paramLong1, long paramLong2, long paramLong3, long paramLong4, String paramString3)
  {
    TbsStatDataManager.getTBSStatManager().onReportPageTotalTimeV2(paramString1, paramString2, paramByte, paramLong1, paramLong2, paramLong3, paramLong4, paramString3);
  }
  
  public void onReportResouceLoadError(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, long paramLong)
  {
    paramString = new StatEntry(paramString, paramBoolean1, paramBoolean2, paramBoolean3, (int)paramLong, Apn.getApnTypeS());
    TbsStatDataManager.getTBSStatManager().stat(paramString, true);
  }
  
  public void save()
  {
    TbsStatDataManagerWrapper.getInstance().saveStatData();
  }
  
  public void shutdown()
  {
    TbsStatDataManagerWrapper.getInstance().saveStatData();
  }
  
  public void statCommonData(Context paramContext, String paramString, Map<String, String> paramMap)
  {
    statCommonData(paramContext, paramString, paramMap, false);
  }
  
  public void statCommonData(Context paramContext, String paramString, Map<String, String> paramMap, boolean paramBoolean)
  {
    statCommonData(paramContext, paramString, true, -1L, -1L, paramMap, paramBoolean);
  }
  
  public void statCommonData(Context paramContext, String paramString, boolean paramBoolean1, long paramLong1, long paramLong2, Map<String, String> paramMap, boolean paramBoolean2)
  {
    String str = "1";
    PublicSettingManager localPublicSettingManager = PublicSettingManager.getInstance();
    if ((!paramString.equals("TBS_WUP_WATCH")) && (!paramString.equals("TBS_MINIQB_UPGRADE")) && (!paramString.equals("TBS_INIT_PERFORMANCE")) && (!paramString.equals("MTT_CORE_PROXY_FAILED_REPORT")) && (!paramString.equals("MTT_CORE_PAGE_ERROR_LOAD_SIMPLE")) && (!paramString.equals("MTT_CORE_PAGE_PERFORMANCE_V3")) && (!paramString.equals("MTT_CORE_DIRECT_INFO"))) {
      if (paramString.equals("MTT_CORE_PAGE_DURATION")) {
        str = localPublicSettingManager.getCorePageDurationPrefs();
      } else if ((!paramString.equals("MTT_CORE_PAGE_INFO_SUCCESS")) && (!paramString.equals("MTT_CORE_PAGE_INFO_FAILED")) && (!paramString.equals("MTT_CORE_USER_BEHAVIOUR_INFO")) && (!paramString.equals("MTT_CORE_STAT_PROTOCOL")) && (!paramString.equals("MTT_CORE_PAGE_DIRECT_ADBLOCK_INFO")) && (!paramString.equals("QBPageLoadErrorInfoKey")) && (!paramString.equals("miniqb_entry_url")) && (!paramString.equals("MTT_UPLOAD_DOWNLOAD_MINIQB")) && (!paramString.equals("MTT_CORE_PAGE_DROP_INFO"))) {
        paramString.equals("MTT_CORE_PV_INFO");
      }
    }
    if (getIsWupUploadEnabled(str)) {
      paramString.equals("miniqb_entry_url");
    }
    if (getIsBeaconUploadEnabled(str))
    {
      LogUtils.d(paramString, "###");
      X5CoreBeaconUploader.getInstance(paramContext).upLoadToBeacon(paramString, paramBoolean1, paramLong1, paramLong2, paramMap, paramBoolean2);
    }
  }
  
  public void statCommonData(String paramString, HashMap<String, String> paramHashMap)
  {
    if (paramString != null)
    {
      if (paramHashMap == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("statCommonData: eventName=");
      localStringBuilder.append(paramString);
      localStringBuilder.append(", info=");
      localStringBuilder.append(paramHashMap.toString());
      LogUtils.d("TesStatDataManager", localStringBuilder.toString());
      statSTCommonData(CommStatData.fromHashMap(paramString, paramHashMap));
      return;
    }
  }
  
  public void statDebugAction(String paramString) {}
  
  public void statFlow(int paramInt1, int paramInt2, String paramString, boolean paramBoolean) {}
  
  public void statMiniqbEntry(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("statMiniqbEntry url=");
    ((StringBuilder)localObject).append(paramString);
    LogUtils.d("TesStatDataManager", ((StringBuilder)localObject).toString());
    localObject = new CommStatData();
    ((CommStatData)localObject).sAppKey = "miniqb_entry_url";
    ((CommStatData)localObject).mDataOp = 0;
    ((CommStatData)localObject).sStatKey = paramString;
    ((CommStatData)localObject).put("entry_url", paramString);
    TbsStatDataManager.getSimpleQBStatManager().statCommonData((CommStatData)localObject);
  }
  
  public void statSTCommonData(CommStatData paramCommStatData)
  {
    TbsStatDataManager.getTBSStatManager().statCommonData(paramCommStatData);
  }
  
  public void upload()
  {
    LogUtils.d("ceason", "upload called");
    long l = System.currentTimeMillis();
    if (l - this.mLastUploadTime > 1000L)
    {
      TbsStatDataManagerWrapper.getInstance().uploadStatData();
      this.mLastUploadTime = l;
    }
  }
  
  public void userBehaviorStatistics(String paramString)
  {
    userBehaviorStatistics(paramString, 1, true);
  }
  
  public void userBehaviorStatistics(String paramString, int paramInt)
  {
    userBehaviorStatistics(paramString, paramInt, false);
  }
  
  public void userBehaviorStatistics(String paramString, int paramInt, boolean paramBoolean)
  {
    userBehaviorStatistics(paramString, paramInt, paramBoolean, true);
  }
  
  public void userBehaviorStatistics(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    userBehaviorStatistics(paramString, paramInt, paramBoolean1, paramBoolean2, false);
  }
  
  public void userBehaviorStatistics(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    UserBehaviorPV localUserBehaviorPV = new UserBehaviorPV();
    localUserBehaviorPV.mBehaviorAction = paramString;
    localUserBehaviorPV.mPV = paramInt;
    if (!paramBoolean3) {
      TbsStatDataManager.getTBSStatManager().userBehaviorStatistics(localUserBehaviorPV, paramBoolean1);
    } else {
      TbsStatDataManager.getSimpleQBStatManager().userBehaviorStatistics(localUserBehaviorPV, paramBoolean1);
    }
    if (paramBoolean2) {
      X5CoreBeaconUploader.getInstance(TbsBaseModuleShell.getCallerContext()).userBehaviorStatistics(new String(paramString), paramInt, paramBoolean1, paramBoolean3);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\stat\TBSStatManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */