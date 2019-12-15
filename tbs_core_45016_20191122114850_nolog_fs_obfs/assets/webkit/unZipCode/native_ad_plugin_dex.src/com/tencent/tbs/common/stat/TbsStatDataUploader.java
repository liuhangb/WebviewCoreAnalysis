package com.tencent.tbs.common.stat;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.tencent.common.utils.DesUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.MTT.CommStatData;
import com.tencent.tbs.common.MTT.PerformanceInfo;
import com.tencent.tbs.common.MTT.STStat;
import com.tencent.tbs.common.MTT.STTime;
import com.tencent.tbs.common.MTT.UserBase;
import com.tencent.tbs.common.MTT.UserBehaviorPV;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.wup.MultiWUPRequest;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.common.wup.WUPTaskProxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class TbsStatDataUploader
  implements IWUPRequestCallBack
{
  public static final byte[] REPORT_KEY_TEA = { 98, -24, 57, -84, -115, 117, 55, 121 };
  private static final byte STAT_REQUEST_TYPE_TOTAL_STAT = 0;
  private static final byte STAT_REQUEST_TYPE_VIDEO = 1;
  private static final String TAG = "TesStatDataUploader";
  private static TbsStatDataUploader mInstance;
  private int mCurrentStatId;
  private StatUploaderThread mUploader = null;
  
  public static TbsStatDataUploader getInstance()
  {
    if (mInstance == null) {
      mInstance = new TbsStatDataUploader();
    }
    return mInstance;
  }
  
  private WUPRequest getStatRequest(WUPStatData paramWUPStatData, boolean paramBoolean)
  {
    WUPRequest localWUPRequest = new WUPRequest("stat", "stat");
    paramWUPStatData = getSTStat(paramWUPStatData, paramBoolean);
    if (paramWUPStatData == null) {
      return null;
    }
    try
    {
      localWUPRequest.put("crypt", DesUtils.DesEncrypt(REPORT_KEY_TEA, paramWUPStatData.toByteArray(), 1));
      localWUPRequest.setRequestCallBack(this);
      localWUPRequest.setType((byte)0);
      return localWUPRequest;
    }
    catch (Exception paramWUPStatData)
    {
      paramWUPStatData.printStackTrace();
    }
    return null;
  }
  
  public String changeStructToString(ArrayList<UserBehaviorPV> paramArrayList, WUPStatData paramWUPStatData)
  {
    if (paramArrayList == null) {
      return "";
    }
    paramWUPStatData = "";
    int i = 0;
    while (i < paramArrayList.size())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramWUPStatData);
      localStringBuilder.append(((UserBehaviorPV)paramArrayList.get(i)).getKeyValue());
      localStringBuilder.append("=");
      localStringBuilder.append(((UserBehaviorPV)paramArrayList.get(i)).getIPV());
      localStringBuilder.append("&");
      paramWUPStatData = localStringBuilder.toString();
      i += 1;
    }
    return paramWUPStatData;
  }
  
  public STStat getSTStat(WUPStatData paramWUPStatData, boolean paramBoolean)
  {
    try
    {
      STStat localSTStat = new STStat();
      localSTStat.stUB = UserBase.getInstance();
      localSTStat.stTime = new STTime();
      localSTStat.stTotal = new ArrayList(paramWUPStatData.mTotal.values());
      localSTStat.stInnerPv = new ArrayList();
      localSTStat.stOuterPv = new ArrayList(paramWUPStatData.mOuterPV.values());
      localSTStat.vEntryPv = null;
      localSTStat.sProtocol = changeStructToString(new ArrayList(paramWUPStatData.mUserBehaviorPV.values()), paramWUPStatData);
      localSTStat.vThirdUse = new ArrayList();
      Object localObject = new ArrayList();
      int i = 0;
      while (i < ((ArrayList)localObject).size())
      {
        ((PerformanceInfo)((ArrayList)localObject).get(i)).stat();
        i += 1;
      }
      localSTStat.vPerformanceInfo = ((ArrayList)localObject);
      paramWUPStatData.trimPerformanceStat();
      localObject = new ArrayList(paramWUPStatData.mPerformanceStat.values());
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("uploading PerformanceStat: ");
      localStringBuilder.append(localObject);
      LogUtils.d("StatManager", localStringBuilder.toString());
      localSTStat.vPerformanceStat = ((ArrayList)localObject);
      localSTStat.iPacketNum = paramWUPStatData.mId;
      localSTStat.sPacketTime = Long.toString(paramWUPStatData.mTime);
      localSTStat.iRetryCount = ((byte)((this.mCurrentStatId % 10 + 10 - paramWUPStatData.mId % 10) % 10));
      localSTStat.vSTCommonAppInfo = new ArrayList(paramWUPStatData.mCommonAppInfos);
      paramWUPStatData = paramWUPStatData.mCommStatDatas.entrySet().iterator();
      while (paramWUPStatData.hasNext())
      {
        localObject = (CommStatData)((Map.Entry)paramWUPStatData.next()).getValue();
        if (localObject != null)
        {
          localObject = ((CommStatData)localObject).toCommonAppInfo();
          if (localObject != null) {
            localSTStat.vSTCommonAppInfo.add(localObject);
          }
        }
      }
      localSTStat.sAdrID = DeviceUtils.getAndroidId();
      localSTStat.sQIMEI = DeviceUtils.getQIMEI();
      paramWUPStatData = PublicSettingManager.getInstance().getMiniQbStatVersion();
      localSTStat.setForSimpleQB(paramBoolean, paramWUPStatData);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getSTStat: forSimpleQb = ");
      ((StringBuilder)localObject).append(paramBoolean);
      ((StringBuilder)localObject).append(", miniQBVersion = ");
      ((StringBuilder)localObject).append(paramWUPStatData);
      LogUtils.d("TesStatDataUploader", ((StringBuilder)localObject).toString());
      return localSTStat;
    }
    catch (Exception paramWUPStatData)
    {
      paramWUPStatData.printStackTrace();
    }
    return null;
  }
  
  public ArrayList<WUPRequest> getStatRequests(boolean paramBoolean)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("upload stat data, get stat request; isSimpleQB=");
    ((StringBuilder)localObject1).append(paramBoolean);
    LogUtils.d("TesStatDataUploader", ((StringBuilder)localObject1).toString());
    ArrayList localArrayList = new ArrayList();
    if (paramBoolean) {
      localObject1 = TbsStatDataManager.getSimpleQBStatManager();
    } else {
      localObject1 = TbsStatDataManager.getTBSStatManager();
    }
    this.mCurrentStatId = ((TbsStatDataManager)localObject1).getCurrentStatId();
    ((TbsStatDataManager)localObject1).SaveCurrentAndMakeNewStatData();
    localObject1 = ((TbsStatDataManager)localObject1).getPreData();
    if ((localObject1 != null) && (((ArrayList)localObject1).size() != 0))
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("upload stat data, get stat request, stat data ok; isSimpleQB=");
      ((StringBuilder)localObject2).append(paramBoolean);
      LogUtils.d("TesStatDataUploader", ((StringBuilder)localObject2).toString());
      localObject1 = ((ArrayList)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (WUPStatData)((Iterator)localObject1).next();
        if (localObject2 != null)
        {
          Object localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("upload stat data, get stat request, stat data id=");
          ((StringBuilder)localObject3).append(((WUPStatData)localObject2).mId);
          ((StringBuilder)localObject3).append("; isSimpleQB=");
          ((StringBuilder)localObject3).append(paramBoolean);
          LogUtils.d("TesStatDataUploader", ((StringBuilder)localObject3).toString());
          localObject3 = getStatRequest((WUPStatData)localObject2, paramBoolean);
          if (localObject3 != null)
          {
            ((WUPRequest)localObject3).setBindObject(new StatRequestBindObj(((WUPStatData)localObject2).mId, paramBoolean));
            localArrayList.add(localObject3);
          }
        }
      }
      return localArrayList;
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("upload stat data, get stat request, stat data empty; isSimpleQB=");
    ((StringBuilder)localObject1).append(paramBoolean);
    LogUtils.d("TesStatDataUploader", ((StringBuilder)localObject1).toString());
    return null;
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    if (paramWUPRequestBase == null) {
      return;
    }
    switch (paramWUPRequestBase.getType())
    {
    default: 
      return;
    case 1: 
      LogUtils.d("TesStatDataUploader", "STAT_REQUEST_TYPE_VIDEO failed");
      return;
    }
    LogUtils.d("TesStatDataUploader", "STAT_REQUEST_TYPE_TOTAL_STAT failed");
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    if (paramWUPRequestBase != null)
    {
      if (paramWUPResponseBase == null) {
        return;
      }
      paramWUPResponseBase = paramWUPRequestBase.getBindObject();
      if (!(paramWUPResponseBase instanceof StatRequestBindObj)) {
        return;
      }
      paramWUPResponseBase = (StatRequestBindObj)paramWUPResponseBase;
      if (paramWUPResponseBase.flag) {
        TbsStatDataManager.getSimpleQBStatManager().deleteFileByID(paramWUPResponseBase.id);
      } else {
        TbsStatDataManager.getTBSStatManager().deleteFileByID(paramWUPResponseBase.id);
      }
      switch (paramWUPRequestBase.getType())
      {
      default: 
        return;
      case 1: 
        paramWUPRequestBase = new StringBuilder();
        paramWUPRequestBase.append("STAT_REQUEST_TYPE_VIDEO success, fileid=");
        paramWUPRequestBase.append(paramWUPResponseBase.id);
        paramWUPRequestBase.append(", simpleQB=");
        paramWUPRequestBase.append(paramWUPResponseBase.flag);
        LogUtils.d("TesStatDataUploader", paramWUPRequestBase.toString());
        return;
      }
      paramWUPRequestBase = new StringBuilder();
      paramWUPRequestBase.append("STAT_REQUEST_TYPE_TOTAL_STAT success, fileid=");
      paramWUPRequestBase.append(paramWUPResponseBase.id);
      paramWUPRequestBase.append(", simpleQB=");
      paramWUPRequestBase.append(paramWUPResponseBase.flag);
      LogUtils.d("TesStatDataUploader", paramWUPRequestBase.toString());
      return;
    }
  }
  
  public void upload(final boolean paramBoolean)
  {
    this.mUploader.post(new Runnable()
    {
      public void run()
      {
        MultiWUPRequest localMultiWUPRequest = new MultiWUPRequest();
        LogUtils.d("TesStatDataUploader", "upload stat data, check guid--0");
        LogUtils.d("TesStatDataUploader", "upload stat data, check guid ok , do get stats data");
        Object localObject = TbsStatDataUploader.this.getStatRequests(paramBoolean);
        if (localObject != null)
        {
          localObject = ((ArrayList)localObject).iterator();
          while (((Iterator)localObject).hasNext())
          {
            WUPRequest localWUPRequest = (WUPRequest)((Iterator)localObject).next();
            if (localWUPRequest != null) {
              localMultiWUPRequest.addWUPRequest(localWUPRequest);
            }
          }
        }
        localMultiWUPRequest.setRequestName("multi_task_stat");
        WUPTaskProxy.send(localMultiWUPRequest);
        LogUtils.d("TesStatDataUploaderpv-Report", "TesStatDataUploader-upload()");
      }
    });
  }
  
  public void uploadAll()
  {
    this.mUploader.post(new Runnable()
    {
      public void run()
      {
        Object localObject1 = new MultiWUPRequest();
        LogUtils.d("TesStatDataUploader", "upload miniQB and TBS stat data, check guid");
        LogUtils.d("TesStatDataUploader", "upload miniQB and TBS stat data, check guid ok , do get stats data");
        Object localObject2 = TbsStatDataUploader.this;
        int i = 0;
        int j = 0;
        localObject2 = ((TbsStatDataUploader)localObject2).getStatRequests(false);
        WUPRequest localWUPRequest;
        if (localObject2 != null)
        {
          localObject2 = ((ArrayList)localObject2).iterator();
          for (;;)
          {
            i = j;
            if (!((Iterator)localObject2).hasNext()) {
              break;
            }
            localWUPRequest = (WUPRequest)((Iterator)localObject2).next();
            if (localWUPRequest != null)
            {
              ((MultiWUPRequest)localObject1).addWUPRequest(localWUPRequest);
              j += 1;
            }
          }
        }
        localObject2 = TbsStatDataUploader.this.getStatRequests(true);
        j = i;
        if (localObject2 != null)
        {
          localObject2 = ((ArrayList)localObject2).iterator();
          for (;;)
          {
            j = i;
            if (!((Iterator)localObject2).hasNext()) {
              break;
            }
            localWUPRequest = (WUPRequest)((Iterator)localObject2).next();
            if (localWUPRequest != null)
            {
              ((MultiWUPRequest)localObject1).addWUPRequest(localWUPRequest);
              i += 1;
            }
          }
        }
        if (j > 0)
        {
          ((MultiWUPRequest)localObject1).setRequestName("multi_task_stat");
          WUPTaskProxy.send((MultiWUPRequest)localObject1);
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("there are ");
          ((StringBuilder)localObject1).append(j);
          ((StringBuilder)localObject1).append(" requests to be uploaded");
          LogUtils.d("TesStatDataUploader", ((StringBuilder)localObject1).toString());
        }
        LogUtils.d("TesStatDataUploader", "TbsStatDataUploader-uploadAll() finished");
      }
    });
  }
  
  private class StatRequestBindObj
  {
    boolean flag;
    int id;
    
    public StatRequestBindObj(int paramInt, boolean paramBoolean)
    {
      this.id = paramInt;
      this.flag = paramBoolean;
    }
  }
  
  private class StatUploaderThread
  {
    private Handler mHandler = null;
    private Looper mLooper = null;
    
    public StatUploaderThread()
    {
      this$1 = new HandlerThread("UP");
      TbsStatDataUploader.this.start();
      this.mLooper = TbsStatDataUploader.this.getLooper();
      this.mHandler = new Handler(this.mLooper);
    }
    
    public final boolean post(Runnable paramRunnable)
    {
      return this.mHandler.post(paramRunnable);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\stat\TbsStatDataUploader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */