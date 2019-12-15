package com.tencent.tbs.common.beacon;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.baseinfo.ICoreInfoFetcher;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.utils.DeviceUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class BeaconPerformanceStatManager
{
  private static final int MAX_STAT_TIMES = 40;
  private static BeaconPerformanceStatManager mInstance;
  private Context mContext = null;
  private boolean mIsCollectStat = true;
  public HashMap<String, PerformanceSTAT> mPerformanceStat;
  private int mStatTimes = 0;
  private StatWorkerThread mStatWorker = null;
  
  public BeaconPerformanceStatManager(Context paramContext)
  {
    this.mContext = paramContext;
    reset();
  }
  
  private PerformanceSTAT createOrGetPerformanceStat(String paramString1, byte paramByte, String paramString2, String paramString3)
  {
    String str1 = paramString3;
    if (paramString3.startsWith("Wlan")) {
      str1 = "Wlan";
    }
    Object localObject = TbsBaseModuleShell.getCoreInfoFetcher();
    paramString3 = paramString2;
    if (localObject != null)
    {
      paramString3 = paramString2;
      if (!((ICoreInfoFetcher)localObject).shouldUseQProxyAccordingToFlag(paramByte)) {
        paramString3 = "";
      }
    }
    String str2 = getPerformanceStatKey(str1, paramByte, paramString3, paramString1);
    localObject = (PerformanceSTAT)this.mPerformanceStat.get(str2);
    paramString2 = (String)localObject;
    if (localObject == null)
    {
      paramString2 = new PerformanceSTAT();
      paramString2.sUrl = paramString1;
      paramString2.sAPN = str1;
      paramString2.iProxyType = paramByte;
      paramString2.sProxyIP = paramString3;
      this.mPerformanceStat.put(str2, paramString2);
    }
    paramString2.sDevice = DeviceUtils.getDeviceName();
    return paramString2;
  }
  
  public static BeaconPerformanceStatManager getInstance(Context paramContext)
  {
    if (mInstance == null) {
      mInstance = new BeaconPerformanceStatManager(paramContext);
    }
    return mInstance;
  }
  
  public static String getPerformanceStatKey(PerformanceSTAT paramPerformanceSTAT)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramPerformanceSTAT.sAPN);
    localStringBuffer.append('#');
    localStringBuffer.append(paramPerformanceSTAT.iProxyType);
    localStringBuffer.append('#');
    localStringBuffer.append(paramPerformanceSTAT.sProxyIP);
    localStringBuffer.append(paramPerformanceSTAT.sUrl);
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
      this.mPerformanceStat = new HashMap();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void onReportPageTotalTimeV2(final String paramString1, final String paramString2, final byte paramByte, final long paramLong1, long paramLong2, final long paramLong3, long paramLong4, final String paramString3)
  {
    this.mStatWorker.post(new Runnable()
    {
      public void run()
      {
        if (!BeaconPerformanceStatManager.this.mIsCollectStat) {
          return;
        }
        BeaconPerformanceStatManager.PerformanceSTAT localPerformanceSTAT = BeaconPerformanceStatManager.this.createOrGetPerformanceStat(paramString1, paramByte, paramString2, paramString3);
        localPerformanceSTAT.iTotalTime += (int)paramLong3;
        localPerformanceSTAT.iNetLoadTime += (int)paramLong1;
        if (localPerformanceSTAT.vReqRecord != null)
        {
          int i = localPerformanceSTAT.vReqRecord.size();
          if (i > 0)
          {
            BeaconPerformanceStatManager.ReqRecord localReqRecord = (BeaconPerformanceStatManager.ReqRecord)localPerformanceSTAT.vReqRecord.get(i - 1);
            localReqRecord.iFirstWordTime = ((int)this.val$firstWordTime);
            localReqRecord.iFirstScreenTime = ((int)this.val$firstScreenTime);
            Object localObject = TbsBaseModuleShell.getCoreInfoFetcher();
            if ((localObject != null) && (BeaconPerformanceStatManager.this.trimPerformanceStat() >= ((ICoreInfoFetcher)localObject).getMaxReportNumber()))
            {
              LogUtils.d("BeaconPerformanceStatManager", "max-report-num reached, stop loging");
              BeaconPerformanceStatManager.access$002(BeaconPerformanceStatManager.this, false);
            }
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("first-word, first-screen, etc. statics are added to : ");
            ((StringBuilder)localObject).append(BeaconPerformanceStatManager.getPerformanceStatKey(localPerformanceSTAT));
            ((StringBuilder)localObject).append(", and the record's status-code: ");
            ((StringBuilder)localObject).append(localReqRecord.iRet);
            LogUtils.d("BeaconPerformanceStatManager", ((StringBuilder)localObject).toString());
          }
        }
      }
    });
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
                PerformanceSTAT localPerformanceSTAT = (PerformanceSTAT)((Map.Entry)localIterator.next()).getValue();
                Object localObject2 = localPerformanceSTAT.vReqRecord;
                if (localObject2 != null) {
                  break label338;
                }
                localObject2 = new StringBuilder();
                ((StringBuilder)localObject2).append("A performance-stat without any main-resource-record is being deleted: ");
                ((StringBuilder)localObject2).append(getPerformanceStatKey(localPerformanceSTAT));
                LogUtils.d("BeaconPerformanceStatManager", ((StringBuilder)localObject2).toString());
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
                  localStringBuilder.append(getPerformanceStatKey(localPerformanceSTAT));
                  localStringBuilder.append(", record: ");
                  localStringBuilder.append(localReqRecord);
                  LogUtils.d("BeaconPerformanceStatManager", localStringBuilder.toString());
                  ((ArrayList)localObject2).remove(i);
                  k = i - 1;
                  break label343;
                }
                if (((ArrayList)localObject2).size() < 1)
                {
                  localObject2 = new StringBuilder();
                  ((StringBuilder)localObject2).append("A performance-stat without any main-resource-record is being deleted: ");
                  ((StringBuilder)localObject2).append(getPerformanceStatKey(localPerformanceSTAT));
                  LogUtils.d("BeaconPerformanceStatManager", ((StringBuilder)localObject2).toString());
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
  
  public void uploadToBeacon()
  {
    Object localObject = this.mPerformanceStat;
    if ((localObject != null) && (((HashMap)localObject).size() > 0))
    {
      localObject = this.mPerformanceStat.entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        PerformanceSTAT localPerformanceSTAT = (PerformanceSTAT)((Map.Entry)((Iterator)localObject).next()).getValue();
        HashMap localHashMap = new HashMap();
        localHashMap.put("url", localPerformanceSTAT.sUrl);
        localHashMap.put("proxyip", localPerformanceSTAT.sProxyIP);
        StringBuilder localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("");
        localStringBuilder1.append(localPerformanceSTAT.iProxyType);
        localHashMap.put("iproxytype", localStringBuilder1.toString());
        localHashMap.put("apn", localPerformanceSTAT.sAPN);
        localHashMap.put("device", localPerformanceSTAT.sDevice);
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("");
        localStringBuilder1.append(localPerformanceSTAT.iSubHTTPRequestCount);
        localHashMap.put("isubhttprequestcount", localStringBuilder1.toString());
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("");
        localStringBuilder1.append(localPerformanceSTAT.iSubResouceCostTime);
        localHashMap.put("isubresoucecosttime", localStringBuilder1.toString());
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("");
        localStringBuilder1.append(localPerformanceSTAT.iSubResourceSize);
        localHashMap.put("isubresourcesize", localStringBuilder1.toString());
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("");
        localStringBuilder1.append(localPerformanceSTAT.iSubAvgDNSTime);
        localHashMap.put("isubavgdnstime", localStringBuilder1.toString());
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("");
        localStringBuilder1.append(localPerformanceSTAT.iSubAvgWaitRspTime);
        localHashMap.put("isubavgwaitrsptime", localStringBuilder1.toString());
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("");
        localStringBuilder1.append(localPerformanceSTAT.iTotalTime);
        localHashMap.put("itotaltime", localStringBuilder1.toString());
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("");
        localStringBuilder1.append(localPerformanceSTAT.iNetLoadTime);
        localHashMap.put("inetloadtime", localStringBuilder1.toString());
        localStringBuilder1 = new StringBuilder();
        if ((localPerformanceSTAT.vReqRecord != null) && (localPerformanceSTAT.vReqRecord.size() > 0))
        {
          int i = 0;
          while (i < localPerformanceSTAT.vReqRecord.size())
          {
            StringBuilder localStringBuilder2;
            if (i == 0)
            {
              localStringBuilder2 = new StringBuilder();
              localStringBuilder2.append("re:");
              localStringBuilder2.append(((ReqRecord)localPerformanceSTAT.vReqRecord.get(i)).toString());
              localStringBuilder1.append(localStringBuilder2.toString());
            }
            else
            {
              localStringBuilder2 = new StringBuilder();
              localStringBuilder2.append("|re:");
              localStringBuilder2.append(((ReqRecord)localPerformanceSTAT.vReqRecord.get(i)).toString());
              localStringBuilder1.append(localStringBuilder2.toString());
            }
            i += 1;
          }
        }
        localHashMap.put("mainresourcesdetail", localStringBuilder1.toString());
        X5CoreBeaconUploader.getInstance(this.mContext).upLoadToBeacon("MTT_CORE_PAGE_PERFORMANCE", localHashMap);
      }
    }
    this.mPerformanceStat.clear();
  }
  
  public void uploadToBeaconRunnable()
  {
    this.mStatWorker.post(new Runnable()
    {
      public void run()
      {
        BeaconPerformanceStatManager.this.uploadToBeacon();
      }
    });
  }
  
  class PerformanceSTAT
  {
    public int iNetLoadTime = 0;
    public int iPageVisitCount = 0;
    public byte iProxyType = 0;
    public double iSubAvgConnectTime = 0.0D;
    public double iSubAvgDNSTime = 0.0D;
    public double iSubAvgWaitRspTime = 0.0D;
    public int iSubHTTPRequestCount = 0;
    public int iSubResouceCostTime = 0;
    public int iSubResourceSize = 0;
    public int iTotalTime = 0;
    public String sAPN = "";
    public String sDevice = "";
    public String sProxyIP = "";
    public String sQQ = "";
    public String sRemoteIP = "";
    public String sUrl = "";
    public ArrayList<BeaconPerformanceStatManager.ReqRecord> vReqRecord = null;
    
    public PerformanceSTAT() {}
    
    public PerformanceSTAT(String paramString1, String paramString2, String paramString3, byte paramByte, String paramString4, String paramString5, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, ArrayList<BeaconPerformanceStatManager.ReqRecord> paramArrayList, int paramInt9)
    {
      this.sDevice = paramString1;
      this.sQQ = paramString2;
      this.sUrl = paramString3;
      this.sAPN = paramByte;
      this.iProxyType = paramString4;
      this.sRemoteIP = paramString5;
      this.sProxyIP = paramInt1;
      this.iTotalTime = paramInt2;
      this.iSubAvgDNSTime = paramInt3;
      this.iSubAvgConnectTime = paramInt4;
      this.iSubAvgWaitRspTime = paramInt5;
      this.iSubResourceSize = paramInt6;
      this.iSubResouceCostTime = paramInt7;
      this.iSubHTTPRequestCount = paramInt8;
      this.iPageVisitCount = paramArrayList;
      this.vReqRecord = paramInt9;
      int i;
      this.iNetLoadTime = i;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("PerformanceStat [sDevice=");
      localStringBuilder.append(this.sDevice);
      localStringBuilder.append(", sQQ=");
      localStringBuilder.append(this.sQQ);
      localStringBuilder.append(", sUrl=");
      localStringBuilder.append(this.sUrl);
      localStringBuilder.append(", sAPN=");
      localStringBuilder.append(this.sAPN);
      localStringBuilder.append(", iProxyType=");
      localStringBuilder.append(this.iProxyType);
      localStringBuilder.append(", sRemoteIP=");
      localStringBuilder.append(this.sRemoteIP);
      localStringBuilder.append(", sProxyIP=");
      localStringBuilder.append(this.sProxyIP);
      localStringBuilder.append(", iTotalTime=");
      localStringBuilder.append(this.iTotalTime);
      localStringBuilder.append(", iSubAvgDNSTime=");
      localStringBuilder.append(this.iSubAvgDNSTime);
      localStringBuilder.append(", iSubAvgConnectTime=");
      localStringBuilder.append(this.iSubAvgConnectTime);
      localStringBuilder.append(", iSubAvgWaitRspTime=");
      localStringBuilder.append(this.iSubAvgWaitRspTime);
      localStringBuilder.append(", iSubResourceSize=");
      localStringBuilder.append(this.iSubResourceSize);
      localStringBuilder.append(", iSubResouceCostTime=");
      localStringBuilder.append(this.iSubResouceCostTime);
      localStringBuilder.append(", iSubHTTPRequestCount=");
      localStringBuilder.append(this.iSubHTTPRequestCount);
      localStringBuilder.append(", iPageVisitCount=");
      localStringBuilder.append(this.iPageVisitCount);
      localStringBuilder.append(", vReqRecord=");
      localStringBuilder.append(this.vReqRecord);
      localStringBuilder.append(", iNetLoadTime=");
      localStringBuilder.append(this.iNetLoadTime);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  public final class ReqRecord
  {
    public int iConnectTime = 0;
    public int iConnectionUseCount = 0;
    public int iDNSTime = 0;
    public int iFirstScreenTime = 0;
    public int iFirstWordTime = 0;
    public long iRecordTime = 0L;
    public int iRecvRspTime = 0;
    public int iResType = 0;
    public int iRet = 0;
    public int iReuseConnectTime = 0;
    public int iSendingTime = 0;
    public int iSize = 0;
    public int iWaitRspTime = 0;
    public String sProxyData = "";
    public String sUrl = "";
    public String sWebSiteIP = "";
    
    public ReqRecord() {}
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.sUrl);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iResType);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iRecordTime);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iRet);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iDNSTime);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iConnectTime);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iSendingTime);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iWaitRspTime);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iRecvRspTime);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iSize);
      localStringBuilder.append("|");
      localStringBuilder.append(this.sWebSiteIP);
      localStringBuilder.append("|");
      localStringBuilder.append(this.sProxyData);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iReuseConnectTime);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iConnectionUseCount);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iFirstWordTime);
      localStringBuilder.append("|");
      localStringBuilder.append(this.iFirstScreenTime);
      return localStringBuilder.toString();
    }
  }
  
  private class StatWorkerThread
  {
    private Handler mHandler = null;
    private Looper mLooper = null;
    
    public StatWorkerThread()
    {
      this$1 = new HandlerThread("bpsm");
      BeaconPerformanceStatManager.this.start();
      this.mLooper = BeaconPerformanceStatManager.this.getLooper();
      this.mHandler = new Handler(this.mLooper);
    }
    
    public final boolean post(Runnable paramRunnable)
    {
      return this.mHandler.post(paramRunnable);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\beacon\BeaconPerformanceStatManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */