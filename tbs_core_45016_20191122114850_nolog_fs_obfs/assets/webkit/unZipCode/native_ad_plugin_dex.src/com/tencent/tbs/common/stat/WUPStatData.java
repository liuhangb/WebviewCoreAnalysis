package com.tencent.tbs.common.stat;

import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.MTT.CommStatData;
import com.tencent.tbs.common.MTT.PerformanceStat;
import com.tencent.tbs.common.MTT.ReqRecord;
import com.tencent.tbs.common.MTT.STCommonAppInfo;
import com.tencent.tbs.common.MTT.STPV;
import com.tencent.tbs.common.MTT.STTotal;
import com.tencent.tbs.common.MTT.UserBehaviorPV;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class WUPStatData
{
  private final String WUP_STAT_DATA_VERSION = "000000";
  public HashMap<String, CommStatData> mCommStatDatas;
  public ArrayList<STCommonAppInfo> mCommonAppInfos;
  public int mId = 0;
  public HashMap<String, STPV> mOuterPV;
  public HashMap<String, PerformanceStat> mPerformanceStat;
  public long mTime = 0L;
  public HashMap<String, STTotal> mTotal;
  public HashMap<String, UserBehaviorPV> mUserBehaviorPV;
  public String version = "000000";
  
  public WUPStatData()
  {
    reset();
  }
  
  public static String getPerformanceStatKey(PerformanceStat paramPerformanceStat)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramPerformanceStat.sAPN);
    localStringBuffer.append('#');
    localStringBuffer.append(paramPerformanceStat.iProxyType);
    localStringBuffer.append('#');
    localStringBuffer.append(paramPerformanceStat.sProxyIP);
    localStringBuffer.append(paramPerformanceStat.sUrl);
    return localStringBuffer.toString();
  }
  
  public static String getPerformanceStatKey(String paramString1, byte paramByte, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString1);
    localStringBuffer.append('#');
    localStringBuffer.append(paramByte);
    localStringBuffer.append('#');
    localStringBuffer.append(paramString2);
    localStringBuffer.append(paramString3);
    return localStringBuffer.toString();
  }
  
  private void reset()
  {
    try
    {
      this.version = "000000";
      this.mTotal = new HashMap();
      this.mOuterPV = new HashMap();
      this.mUserBehaviorPV = new HashMap();
      this.mPerformanceStat = new HashMap();
      this.mCommonAppInfos = new ArrayList();
      this.mCommStatDatas = new HashMap();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public WUPStatData copy()
  {
    try
    {
      WUPStatData localWUPStatData = new WUPStatData();
      try
      {
        localWUPStatData.mOuterPV = new HashMap(this.mOuterPV);
        localWUPStatData.mPerformanceStat = new HashMap(this.mPerformanceStat);
        localWUPStatData.mTotal = new HashMap(this.mTotal);
        localWUPStatData.mUserBehaviorPV = new HashMap(this.mUserBehaviorPV);
        localWUPStatData.version = new String(this.version);
        localWUPStatData.mId = this.mId;
        localWUPStatData.mTime = this.mTime;
        localWUPStatData.mCommonAppInfos = new ArrayList(this.mCommonAppInfos);
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        localOutOfMemoryError.printStackTrace();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return localWUPStatData;
    }
    finally {}
  }
  
  public int trimPerformanceStat()
  {
    for (;;)
    {
      int k;
      try
      {
        if (this.mPerformanceStat != null)
        {
          i = this.mPerformanceStat.size();
          if (i >= 1) {
            try
            {
              Iterator localIterator = this.mPerformanceStat.entrySet().iterator();
              int j = 0;
              if (localIterator.hasNext())
              {
                PerformanceStat localPerformanceStat = (PerformanceStat)((Map.Entry)localIterator.next()).getValue();
                Object localObject2 = localPerformanceStat.vReqRecord;
                if (localObject2 != null) {
                  break label338;
                }
                localObject2 = new StringBuilder();
                ((StringBuilder)localObject2).append("A performance-stat without any main-resource-record is being deleted: ");
                ((StringBuilder)localObject2).append(getPerformanceStatKey(localPerformanceStat));
                LogUtils.d("StatManager", ((StringBuilder)localObject2).toString());
                localIterator.remove();
                continue;
                if (i < ((ArrayList)localObject2).size())
                {
                  ReqRecord localReqRecord = (ReqRecord)((ArrayList)localObject2).get(i);
                  k = i;
                  if (localReqRecord.iFirstScreenTime != 0) {
                    break label343;
                  }
                  k = i;
                  if (localReqRecord.iRet != 200) {
                    break label343;
                  }
                  StringBuilder localStringBuilder = new StringBuilder();
                  localStringBuilder.append("A performance-stat's record without first-screen but with status-code=200 is being deleted: ");
                  localStringBuilder.append(getPerformanceStatKey(localPerformanceStat));
                  localStringBuilder.append(", record: ");
                  localStringBuilder.append(localReqRecord);
                  LogUtils.d("StatManager", localStringBuilder.toString());
                  ((ArrayList)localObject2).remove(i);
                  k = i - 1;
                  break label343;
                }
                if (((ArrayList)localObject2).size() < 1)
                {
                  localObject2 = new StringBuilder();
                  ((StringBuilder)localObject2).append("A performance-stat without any main-resource-record is being deleted: ");
                  ((StringBuilder)localObject2).append(getPerformanceStatKey(localPerformanceStat));
                  LogUtils.d("StatManager", ((StringBuilder)localObject2).toString());
                  localIterator.remove();
                  continue;
                }
                i = ((ArrayList)localObject2).size();
                j += i;
                continue;
              }
              return j;
            }
            catch (Error localError)
            {
              localError.printStackTrace();
              return 0;
            }
          }
        }
        return 0;
      }
      finally {}
      label338:
      int i = 0;
      continue;
      label343:
      i = k + 1;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\stat\WUPStatData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */