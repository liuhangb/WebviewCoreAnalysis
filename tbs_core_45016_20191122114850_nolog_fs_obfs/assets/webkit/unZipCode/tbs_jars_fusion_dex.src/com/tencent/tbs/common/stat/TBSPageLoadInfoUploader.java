package com.tencent.tbs.common.stat;

import android.content.Context;
import com.tencent.common.utils.DesUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.MTT.DomainTimeVector;
import com.tencent.tbs.common.wup.MultiWUPRequest;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.common.wup.WUPTaskProxy;
import java.util.ArrayList;
import java.util.Iterator;

public class TBSPageLoadInfoUploader
  implements IWUPRequestCallBack
{
  private static final String TAG = "TBSPageLoadInfo";
  private static final byte TBSPAGELOADINFO_REQUEST_TYPE = 0;
  private static Context mAppContext;
  private static TBSPageLoadInfoUploader mInstance;
  
  public static TBSPageLoadInfoUploader getInstance(Context paramContext)
  {
    if (mInstance == null)
    {
      mAppContext = paramContext;
      mInstance = new TBSPageLoadInfoUploader();
    }
    return mInstance;
  }
  
  private WUPRequest getTbsInfoRequest(PageLoadInfoData paramPageLoadInfoData)
  {
    WUPRequest localWUPRequest = new WUPRequest("stat", "reportDomainTimeInfo");
    paramPageLoadInfoData = getDomainTimeInfoVector(paramPageLoadInfoData);
    if (paramPageLoadInfoData == null) {
      return null;
    }
    try
    {
      localWUPRequest.put("crypt", DesUtils.DesEncrypt(TbsStatDataUploader.REPORT_KEY_TEA, paramPageLoadInfoData.toByteArray(), 1));
      localWUPRequest.setRequestCallBack(this);
      localWUPRequest.setType((byte)0);
      return localWUPRequest;
    }
    catch (Exception paramPageLoadInfoData)
    {
      paramPageLoadInfoData.printStackTrace();
    }
    return null;
  }
  
  public DomainTimeVector getDomainTimeInfoVector(PageLoadInfoData paramPageLoadInfoData)
  {
    try
    {
      DomainTimeVector localDomainTimeVector = new DomainTimeVector();
      localDomainTimeVector.vDomainTime = new ArrayList(paramPageLoadInfoData.mRecordInfos);
      int i = 0;
      while (i < localDomainTimeVector.vDomainTime.size())
      {
        paramPageLoadInfoData = new StringBuilder();
        paramPageLoadInfoData.append("getTbsPvStat i=");
        paramPageLoadInfoData.append(i);
        paramPageLoadInfoData.append(" ");
        paramPageLoadInfoData.append((String)localDomainTimeVector.vDomainTime.get(i));
        LogUtils.d("TBSPageLoadInfo", paramPageLoadInfoData.toString());
        i += 1;
      }
      return localDomainTimeVector;
    }
    catch (Exception paramPageLoadInfoData)
    {
      paramPageLoadInfoData.printStackTrace();
    }
    return null;
  }
  
  public ArrayList<WUPRequest> getTBSInfoRequests()
  {
    Object localObject1 = new ArrayList();
    TBSPageLoadInfoManager.getInstance(mAppContext).SaveCurrentAndMakeNewStatData();
    Object localObject2 = TBSPageLoadInfoManager.getInstance(mAppContext).getPreDataList();
    if ((localObject2 != null) && (((ArrayList)localObject2).size() != 0))
    {
      LogUtils.d("TBSPageLoadInfo", " upload tbs data, get tbs request, tbs data ok");
      localObject2 = ((ArrayList)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        PageLoadInfoData localPageLoadInfoData = (PageLoadInfoData)((Iterator)localObject2).next();
        if (localPageLoadInfoData != null)
        {
          Object localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("upload stat data, get stat request, stat data id=");
          ((StringBuilder)localObject3).append(localPageLoadInfoData.mId);
          LogUtils.d("TBSPageLoadInfo", ((StringBuilder)localObject3).toString());
          localObject3 = getTbsInfoRequest(localPageLoadInfoData);
          if (localObject3 != null)
          {
            ((WUPRequest)localObject3).setBindObject(Integer.valueOf(localPageLoadInfoData.mId));
            ((ArrayList)localObject1).add(localObject3);
          }
        }
      }
      return (ArrayList<WUPRequest>)localObject1;
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(" upload but data is null threadname=");
    ((StringBuilder)localObject1).append(Thread.currentThread().getName());
    ((StringBuilder)localObject1).append(" ");
    LogUtils.d("TBSPageLoadInfo", ((StringBuilder)localObject1).toString());
    return null;
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onWUPTaskFail threadname=");
    localStringBuilder.append(Thread.currentThread().getName());
    LogUtils.d("TBSPageLoadInfo", localStringBuilder.toString());
    if (paramWUPRequestBase == null) {
      return;
    }
    paramWUPRequestBase = (Integer)paramWUPRequestBase.getBindObject();
    if (paramWUPRequestBase == null) {
      return;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("onWUPTaskFail fileId=");
    localStringBuilder.append(paramWUPRequestBase);
    LogUtils.d("TBSPageLoadInfo", localStringBuilder.toString());
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onWUPTaskSuccess threadname=");
    localStringBuilder.append(Thread.currentThread().getName());
    LogUtils.d("TBSPageLoadInfo", localStringBuilder.toString());
    if (paramWUPRequestBase != null)
    {
      if (paramWUPResponseBase == null) {
        return;
      }
      paramWUPRequestBase = (Integer)paramWUPRequestBase.getBindObject();
      if (paramWUPRequestBase == null) {
        return;
      }
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("onWUPTaskSuccess fileId=");
      paramWUPResponseBase.append(paramWUPRequestBase);
      paramWUPResponseBase.append(" threadname=");
      paramWUPResponseBase.append(Thread.currentThread().getName());
      LogUtils.d("TBSPageLoadInfo", paramWUPResponseBase.toString());
      TBSPageLoadInfoManager.getInstance(mAppContext).deleteFileByID(paramWUPRequestBase.intValue());
      return;
    }
  }
  
  public void upload()
  {
    MultiWUPRequest localMultiWUPRequest = new MultiWUPRequest();
    Object localObject = getTBSInfoRequests();
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
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(" upload setRequestName multi_task_domaintime threadname=");
    ((StringBuilder)localObject).append(Thread.currentThread().getName());
    ((StringBuilder)localObject).append(" ");
    LogUtils.d("TBSPageLoadInfo", ((StringBuilder)localObject).toString());
    localMultiWUPRequest.setRequestName("multi_task_domaintime");
    WUPTaskProxy.send(localMultiWUPRequest);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\stat\TBSPageLoadInfoUploader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */