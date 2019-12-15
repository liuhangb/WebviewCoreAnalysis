package com.tencent.tbs.tbsshell.partner;

import com.tencent.common.utils.ByteUtils;
import com.tencent.mtt.AppInfoHolder.AppInfoID;
import com.tencent.mtt.AppInfoHolder.IAppInfoProvider;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.ICoreInfoFetcher;
import com.tencent.tbs.common.baseinfo.QUAUtils;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;

public class TbsAppInfoProvider
  implements AppInfoHolder.IAppInfoProvider
{
  public String getAppInfoById(AppInfoHolder.AppInfoID paramAppInfoID)
  {
    switch (paramAppInfoID)
    {
    default: 
      return "";
    case ???: 
      return QUAUtils.getQUA2_V3();
    case ???: 
      return TbsInfoUtils.getQUA();
    case ???: 
      return DeviceUtils.getQIMEI();
    case ???: 
      return TbsInfoUtils.getLCID();
    case ???: 
      return TbsInfoUtils.getLC();
    case ???: 
      if (GUIDFactory.getInstance().isGuidValidate()) {
        return GUIDFactory.getInstance().getStrGuid();
      }
      return ByteUtils.byteToHexString(new byte[16]);
    case ???: 
      paramAppInfoID = TbsBaseModuleShell.getCoreInfoFetcher();
      if (paramAppInfoID != null) {
        return paramAppInfoID.getCoreVersion();
      }
      return "";
    }
    return "";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\TbsAppInfoProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */