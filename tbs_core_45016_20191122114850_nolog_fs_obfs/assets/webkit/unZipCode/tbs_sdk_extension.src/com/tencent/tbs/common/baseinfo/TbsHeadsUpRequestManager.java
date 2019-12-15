package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.taf.JceInputStream;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.wup.WUPRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TbsHeadsUpRequestManager
  implements IWUPRequestCallBack
{
  private static final String SHARE_PREFERENCES_NAME = "qqth";
  private static Context mContext;
  private static TbsHeadsUpRequestManager mInstance;
  private String KEY_HEADSUP_BTNTEXT = "btn_text";
  private String KEY_HEADSUP_BTNURL = "btn_url";
  private String KEY_HEADSUP_CONTENT = "content";
  private String KEY_HEADSUP_END = "end";
  private String KEY_HEADSUP_ICON = "icon";
  private String KEY_HEADSUP_ID = "id";
  private String KEY_HEADSUP_SHOWED = "headsup_showed";
  private String KEY_HEADSUP_START = "start";
  private String KEY_HEADSUP_STAT_URL_CLICK = "stat_url_click";
  private String KEY_HEADSUP_STAT_URL_SHOW = "stat_url_show";
  private String KEY_HEADSUP_TITLE = "title";
  private String KEY_HEADSUP_URL = "url";
  private SharedPreferences mPreferenceHeadsup = null;
  
  private Map<String, String>[] getHeadsup(Map<Integer, OperateItem> paramMap)
  {
    Object localObject1 = new Map[paramMap.size()];
    try
    {
      Iterator localIterator = paramMap.entrySet().iterator();
      int i = 0;
      for (;;)
      {
        paramMap = (Map<Integer, OperateItem>)localObject1;
        if (!localIterator.hasNext()) {
          break;
        }
        Object localObject2 = new JceInputStream(((OperateItem)((Map.Entry)localIterator.next()).getValue()).businessPrivateInfo);
        ((JceInputStream)localObject2).setServerEncoding("UTF-8");
        paramMap = new RmpPosData();
        paramMap.readFrom((JceInputStream)localObject2);
        Object localObject3 = new JceInputStream(paramMap.vPosData);
        ((JceInputStream)localObject3).setServerEncoding("UTF-8");
        localObject2 = new RmpHeadsUp();
        ((RmpHeadsUp)localObject2).readFrom((JceInputStream)localObject3);
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("onWUPTaskSuccess RmpHeadsUp is ");
        ((StringBuilder)localObject3).append(((RmpHeadsUp)localObject2).toString());
        ((StringBuilder)localObject3).toString();
        localObject1[i] = new HashMap();
        localObject3 = localObject1[i];
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(paramMap.stUIInfo.sAdId);
        ((Map)localObject3).put("id", localStringBuilder.toString());
        localObject3 = localObject1[i];
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(paramMap.stCommonInfo.effectiveTime);
        localStringBuilder.append("000");
        ((Map)localObject3).put("start", localStringBuilder.toString());
        localObject3 = localObject1[i];
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(paramMap.stCommonInfo.invalidTime);
        localStringBuilder.append("000");
        ((Map)localObject3).put("end", localStringBuilder.toString());
        localObject3 = localObject1[i];
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(paramMap.stUIInfo.sWording);
        ((Map)localObject3).put("title", localStringBuilder.toString());
        localObject3 = localObject1[i];
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(paramMap.stUIInfo.sDesc);
        ((Map)localObject3).put("content", localStringBuilder.toString());
        localObject3 = localObject1[i];
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(paramMap.stUIInfo.sLinkUrl);
        ((Map)localObject3).put("url", localStringBuilder.toString());
        localObject3 = localObject1[i];
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(((RmpHeadsUp)localObject2).sBtn);
        ((Map)localObject3).put("btn_text", localStringBuilder.toString());
        localObject3 = localObject1[i];
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(((RmpHeadsUp)localObject2).sUrl2);
        ((Map)localObject3).put("btn_url", localStringBuilder.toString());
        localObject2 = localObject1[i];
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("");
        ((StringBuilder)localObject3).append(paramMap.stUIInfo.sImageUrl);
        ((Map)localObject2).put("icon", ((StringBuilder)localObject3).toString());
        localObject2 = localObject1[i];
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("");
        ((StringBuilder)localObject3).append(paramMap.stControlInfo.mStatUrl.get(Integer.valueOf(0)));
        ((Map)localObject2).put("stat_url_click", ((StringBuilder)localObject3).toString());
        localObject2 = localObject1[i];
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("");
        ((StringBuilder)localObject3).append(paramMap.stControlInfo.mStatUrl.get(Integer.valueOf(1)));
        ((Map)localObject2).put("stat_url_show", ((StringBuilder)localObject3).toString());
        paramMap = new StringBuilder();
        paramMap.append("getHeadsup arrayHeadsup ");
        paramMap.append(i);
        paramMap.append(" is ");
        paramMap.append(localObject1[i].toString());
        paramMap.toString();
        i += 1;
      }
      return paramMap;
    }
    catch (Exception paramMap)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("error !!!! ");
      ((StringBuilder)localObject1).append(Log.getStackTraceString(paramMap));
      ((StringBuilder)localObject1).toString();
      paramMap = null;
    }
  }
  
  public static TbsHeadsUpRequestManager getInstance(Context paramContext)
  {
    mContext = paramContext;
    if (mInstance == null) {
      try
      {
        if (mInstance == null) {
          mInstance = new TbsHeadsUpRequestManager();
        }
      }
      finally {}
    }
    return mInstance;
  }
  
  private void write(Map<String, String> paramMap)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("write System.currentTimeMillis() is ");
    ((StringBuilder)localObject).append(System.currentTimeMillis());
    ((StringBuilder)localObject).toString();
    if (this.mPreferenceHeadsup == null) {
      this.mPreferenceHeadsup = mContext.getApplicationContext().getSharedPreferences("qqth", 0);
    }
    localObject = this.mPreferenceHeadsup;
    if (localObject != null)
    {
      localObject = ((SharedPreferences)localObject).edit();
      if (localObject != null)
      {
        long l1 = Long.parseLong(this.mPreferenceHeadsup.getString(this.KEY_HEADSUP_ID, "0"));
        long l2 = Long.parseLong((String)paramMap.get("id"));
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("requestHeadsupIfNeeded id_sp is ");
        localStringBuilder.append(l1);
        localStringBuilder.append(" id_new is ");
        localStringBuilder.append(l2);
        localStringBuilder.toString();
        if (l1 != l2) {
          ((SharedPreferences.Editor)localObject).putBoolean(this.KEY_HEADSUP_SHOWED, false);
        }
        ((SharedPreferences.Editor)localObject).putString(this.KEY_HEADSUP_ID, (String)paramMap.get("id"));
        ((SharedPreferences.Editor)localObject).putString(this.KEY_HEADSUP_START, (String)paramMap.get("start"));
        ((SharedPreferences.Editor)localObject).putString(this.KEY_HEADSUP_END, (String)paramMap.get("end"));
        ((SharedPreferences.Editor)localObject).putString(this.KEY_HEADSUP_URL, (String)paramMap.get("url"));
        ((SharedPreferences.Editor)localObject).putString(this.KEY_HEADSUP_TITLE, (String)paramMap.get("title"));
        ((SharedPreferences.Editor)localObject).putString(this.KEY_HEADSUP_BTNTEXT, (String)paramMap.get("btn_text"));
        ((SharedPreferences.Editor)localObject).putString(this.KEY_HEADSUP_CONTENT, (String)paramMap.get("content"));
        ((SharedPreferences.Editor)localObject).putString(this.KEY_HEADSUP_BTNURL, (String)paramMap.get("btn_url"));
        ((SharedPreferences.Editor)localObject).putString(this.KEY_HEADSUP_ICON, (String)paramMap.get("icon"));
        ((SharedPreferences.Editor)localObject).putString(this.KEY_HEADSUP_STAT_URL_CLICK, (String)paramMap.get("stat_url_click"));
        ((SharedPreferences.Editor)localObject).putString(this.KEY_HEADSUP_STAT_URL_SHOW, (String)paramMap.get("stat_url_show"));
        ((SharedPreferences.Editor)localObject).commit();
        TBSStatManager.getInstance().userBehaviorStatistics("TBSHHTAQ");
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("has headsup for show url is ");
        ((StringBuilder)localObject).append((String)paramMap.get("url"));
        ((StringBuilder)localObject).toString();
      }
    }
  }
  
  private void writeHeadsupSP(Map<String, String>[] paramArrayOfMap)
  {
    if (paramArrayOfMap == null) {
      return;
    }
    for (;;)
    {
      int j;
      int i;
      try
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("writeHeadsupSP System.currentTimeMillis() is ");
        localStringBuilder.append(System.currentTimeMillis());
        localStringBuilder.toString();
        j = 0;
        i = 0;
        if (i >= paramArrayOfMap.length) {
          break label221;
        }
        l1 = Long.parseLong((String)paramArrayOfMap[i].get("start"));
        long l2 = Long.parseLong((String)paramArrayOfMap[i].get("end"));
        if ((System.currentTimeMillis() <= l1) || (System.currentTimeMillis() >= l2)) {
          break label214;
        }
        write(paramArrayOfMap[i]);
        i = 1;
      }
      catch (Exception paramArrayOfMap)
      {
        long l1;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("error !!!! ");
        localStringBuilder.append(Log.getStackTraceString(paramArrayOfMap));
        localStringBuilder.toString();
      }
      if (i < paramArrayOfMap.length)
      {
        l1 = Long.parseLong((String)paramArrayOfMap[i].get("start"));
        Long.parseLong((String)paramArrayOfMap[i].get("end"));
        if (System.currentTimeMillis() < l1)
        {
          write(paramArrayOfMap[i]);
          return;
        }
        i += 1;
      }
      else
      {
        label214:
        label221:
        do
        {
          return;
          i += 1;
          break;
          i = 0;
        } while (i != 0);
        i = j;
      }
    }
  }
  
  public WUPRequest getWupRequest()
  {
    WUPRequest localWUPRequest = new WUPRequest("operateproxy", "getOperateInfoBatch", null);
    GetOperateInfoBatchReq localGetOperateInfoBatchReq = new GetOperateInfoBatchReq();
    Object localObject = new OperateUserInfo();
    ((OperateUserInfo)localObject).androidId = DeviceUtils.getAndroidId();
    ((OperateUserInfo)localObject).guid = GUIDFactory.getInstance().getStrGuid();
    ((OperateUserInfo)localObject).qua2 = QUAUtils.getQUA2_V3();
    localGetOperateInfoBatchReq.userInfo = ((OperateUserInfo)localObject);
    localObject = new ArrayList();
    GetOperateReqItem localGetOperateReqItem = new GetOperateReqItem();
    localGetOperateReqItem.sourceType = 100266;
    localGetOperateReqItem.extraInfo = new HashMap();
    ((ArrayList)localObject).add(localGetOperateReqItem);
    localGetOperateInfoBatchReq.reqItems = ((ArrayList)localObject);
    localWUPRequest.put("req", localGetOperateInfoBatchReq);
    localWUPRequest.setType((byte)89);
    localWUPRequest.setClassLoader(getClass().getClassLoader());
    localWUPRequest.setRequestCallBack(this);
    return localWUPRequest;
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase) {}
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    if (paramWUPRequestBase == null) {
      return;
    }
    if (paramWUPRequestBase.getFuncName().equals("getOperateInfoBatch"))
    {
      paramWUPRequestBase = ((GetOperateInfoBatchRsp)paramWUPResponseBase.get("rsp")).sourceBatch;
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("TbsHeadsUpRequestManager sourceB.size() is ");
      paramWUPResponseBase.append(paramWUPRequestBase.size());
      paramWUPResponseBase.toString();
      paramWUPRequestBase = paramWUPRequestBase.entrySet().iterator();
      if (paramWUPRequestBase.hasNext())
      {
        paramWUPRequestBase = ((UserOperateItemBatch)((Map.Entry)paramWUPRequestBase.next()).getValue()).sourceItems;
        paramWUPResponseBase = new StringBuilder();
        paramWUPResponseBase.append("TbsHeadsUpRequestManager sourceItems.size() is ");
        paramWUPResponseBase.append(paramWUPRequestBase.size());
        paramWUPResponseBase.toString();
        writeHeadsupSP(getHeadsup(paramWUPRequestBase));
      }
    }
    else
    {
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("TbsHeadsUpRequestManager onWUPTaskSuccess request.getFuncName() not match is ");
      paramWUPResponseBase.append(paramWUPRequestBase.getFuncName());
      paramWUPResponseBase.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\TbsHeadsUpRequestManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */