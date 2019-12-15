package com.tencent.tbs.common.baseinfo;

import com.tencent.common.utils.LogUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.common.wup.WUPTaskProxy;
import com.tencent.tbs.common.MTT.COMMON_ERRORCODE;
import com.tencent.tbs.common.MTT.CommonConfigReq;
import com.tencent.tbs.common.MTT.CommonConfigRsp;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import com.tencent.tbs.common.wup.WUPRequest;
import java.util.ArrayList;

public class CommonConfigListMangager
  implements IWUPRequestCallBack
{
  public static final int APP_LAUNCH_FOR_TBS_AD = 1002;
  public static final int APP_PUSHSERVICE_FOR_TBS_AD = 1001;
  public static final int DEBUG_TBS_AD = 1003;
  public static final int INSTALL_CANCEL_ENCROUGE_WINDOW = 1006;
  private static final String TAG = "CommonConfigListMangager";
  private static final String mFucName = "getConfig";
  private static CommonConfigListMangager mInstance;
  private static final String mServerName = "CMD_DOMAIN_WHITE_LIST";
  
  public static CommonConfigListMangager getInstance()
  {
    if (mInstance == null) {
      try
      {
        if (mInstance == null) {
          mInstance = new CommonConfigListMangager();
        }
      }
      finally {}
    }
    return mInstance;
  }
  
  private void onCommonConfigFailed(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onCommonConfigFailed: errorMsg = ");
    localStringBuilder.append(paramString);
    LogUtils.d("CommonConfigListMangager", localStringBuilder.toString());
  }
  
  private void onCommonConfigNoData(CommonConfigRsp paramCommonConfigRsp)
  {
    LogUtils.d("CommonConfigListMangager", "onCommonConfigNoData...");
  }
  
  private void onCommonConfigSuccess(CommonConfigRsp paramCommonConfigRsp)
  {
    int i = paramCommonConfigRsp.iAppId;
    PublicSettingManager.getInstance().setCommonConfigTime(i, paramCommonConfigRsp.iDBTime);
    PublicSettingManager.getInstance().setCommonConfigMd5(i, paramCommonConfigRsp.sContentMd5);
    if (i != 1006) {
      switch (i)
      {
      default: 
        return;
      case 1003: 
        if ((paramCommonConfigRsp.vConfig == null) || (paramCommonConfigRsp.vConfig.size() <= 0)) {
          break;
        }
        PublicSettingManager.getInstance().setCommonConfigListContent(i, (String)paramCommonConfigRsp.vConfig.get(0));
        return;
      case 1002: 
        if ((paramCommonConfigRsp.vConfig == null) || (paramCommonConfigRsp.vConfig.size() <= 0)) {
          break;
        }
        PublicSettingManager.getInstance().setCommonConfigListContent(i, (String)paramCommonConfigRsp.vConfig.get(0));
        return;
      case 1001: 
        new AdAppPushServiceConfig().onCommonConfigTaskSuccess(i, paramCommonConfigRsp.vConfig);
        return;
      }
    } else if ((paramCommonConfigRsp.vConfig != null) && (paramCommonConfigRsp.vConfig.size() > 0)) {
      PublicSettingManager.getInstance().setCommonConfigListContent(i, (String)paramCommonConfigRsp.vConfig.get(0));
    }
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    if (paramWUPRequestBase == null)
    {
      LogUtils.d("CommonConfigListMangager", "onWUPTaskFail request == null");
      return;
    }
    onCommonConfigFailed(paramWUPRequestBase.getFailedReason());
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    LogUtils.d("CommonConfigListMangager", "onWUPTaskSuccess start");
    if (paramWUPResponseBase == null)
    {
      LogUtils.d("CommonConfigListMangager", "onWUPTaskSuccess response = null");
      return;
    }
    paramWUPRequestBase = paramWUPResponseBase.getResponseData("rsp");
    if ((paramWUPRequestBase != null) && ((paramWUPRequestBase instanceof CommonConfigRsp)))
    {
      paramWUPRequestBase = (CommonConfigRsp)paramWUPRequestBase;
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("start request: resp appid: ");
      paramWUPResponseBase.append(paramWUPRequestBase.iAppId);
      LogUtils.d("CommonConfigListMangager", paramWUPResponseBase.toString());
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("start request: resp sContentMd5: ");
      paramWUPResponseBase.append(paramWUPRequestBase.sContentMd5);
      LogUtils.d("CommonConfigListMangager", paramWUPResponseBase.toString());
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("start request: resp iDBTime: ");
      paramWUPResponseBase.append(paramWUPRequestBase.iDBTime);
      LogUtils.d("CommonConfigListMangager", paramWUPResponseBase.toString());
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("start request: resp :iRet: ");
      paramWUPResponseBase.append(paramWUPRequestBase.iRet);
      LogUtils.d("CommonConfigListMangager", paramWUPResponseBase.toString());
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("start request: resp vConfig: ");
      paramWUPResponseBase.append(paramWUPRequestBase.vConfig);
      LogUtils.d("CommonConfigListMangager", paramWUPResponseBase.toString());
      if (paramWUPRequestBase.iRet == 0)
      {
        onCommonConfigSuccess(paramWUPRequestBase);
        return;
      }
      if (paramWUPRequestBase.iRet == 2)
      {
        onCommonConfigNoData(paramWUPRequestBase);
        return;
      }
      paramWUPResponseBase = COMMON_ERRORCODE.convert(paramWUPRequestBase.iRet);
      if (paramWUPResponseBase != null)
      {
        onCommonConfigFailed(paramWUPResponseBase.toString());
        return;
      }
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("Invalid errorcode, iRet = ");
      paramWUPResponseBase.append(paramWUPRequestBase.iRet);
      onCommonConfigFailed(paramWUPResponseBase.toString());
      return;
    }
    LogUtils.d("CommonConfigListMangager", "onWUPTaskSuccess data is not CommonConfigRsp");
  }
  
  public void requestCommonConfigList(boolean paramBoolean, int paramInt, String paramString)
  {
    CommonConfigReq localCommonConfigReq = new CommonConfigReq();
    localCommonConfigReq.iAppId = paramInt;
    int i;
    if (paramBoolean) {
      i = 0;
    } else {
      i = PublicSettingManager.getInstance().getCommonConfigTime(paramInt);
    }
    localCommonConfigReq.iDBTime = i;
    String str;
    if (paramBoolean) {
      str = "";
    } else {
      str = PublicSettingManager.getInstance().getCommonConfigMd5(paramInt);
    }
    localCommonConfigReq.sContentMd5 = str;
    localCommonConfigReq.sGuid = GUIDFactory.getInstance().getStrGuid();
    localCommonConfigReq.sQua2 = TbsInfoUtils.getQUA2();
    localCommonConfigReq.sQua2ExInfo = paramString;
    paramString = new WUPRequest("CMD_DOMAIN_WHITE_LIST", "getConfig");
    paramString.setClassLoader(getClass().getClassLoader());
    paramString.put("req", localCommonConfigReq);
    paramString.setRequestCallBack(this);
    WUPTaskProxy.send(paramString);
    paramString = new StringBuilder();
    paramString.append("requestCommonConfigList start  sContentMd5 = ");
    paramString.append(localCommonConfigReq.sContentMd5);
    LogUtils.d("CommonConfigListMangager", paramString.toString());
    paramString = new StringBuilder();
    paramString.append("requestCommonConfigList start  sGuid = ");
    paramString.append(localCommonConfigReq.sGuid);
    LogUtils.d("CommonConfigListMangager", paramString.toString());
    paramString = new StringBuilder();
    paramString.append("requestCommonConfigList start  sQua2 = ");
    paramString.append(localCommonConfigReq.sQua2);
    LogUtils.d("CommonConfigListMangager", paramString.toString());
    paramString = new StringBuilder();
    paramString.append("requestCommonConfigList start  sQua2ExInfo = ");
    paramString.append(localCommonConfigReq.sQua2ExInfo);
    LogUtils.d("CommonConfigListMangager", paramString.toString());
    paramString = new StringBuilder();
    paramString.append("requestCommonConfigList start  iAppId = ");
    paramString.append(localCommonConfigReq.iAppId);
    LogUtils.d("CommonConfigListMangager", paramString.toString());
    paramString = new StringBuilder();
    paramString.append("requestCommonConfigList start  iDBTime = ");
    paramString.append(localCommonConfigReq.iDBTime);
    LogUtils.d("CommonConfigListMangager", paramString.toString());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\CommonConfigListMangager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */