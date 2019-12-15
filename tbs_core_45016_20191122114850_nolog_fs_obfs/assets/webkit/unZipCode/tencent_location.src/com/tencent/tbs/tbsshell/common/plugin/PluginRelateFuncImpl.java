package com.tencent.tbs.tbsshell.common.plugin;

import com.tencent.common.plugin.QBPluginServiceImpl.IPluginRelateFunc;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.download.BaseDownloadManager;
import com.tencent.tbs.common.download.IDownloadManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import java.util.HashMap;

public class PluginRelateFuncImpl
  implements QBPluginServiceImpl.IPluginRelateFunc
{
  public byte[] getByteGuid()
  {
    return GUIDFactory.getInstance().getByteGuid();
  }
  
  public IDownloadManager getDownloadManager()
  {
    return BaseDownloadManager.getInstance();
  }
  
  public String getQUA()
  {
    return TbsInfoUtils.getQUA2();
  }
  
  public void setPushSvrNotifyUpdatePluginList(boolean paramBoolean) {}
  
  public void upLoadToBeacon(String paramString, HashMap<String, String> paramHashMap)
  {
    X5CoreBeaconUploader.getInstance(TbsBaseModuleShell.getCallerContext()).upLoadToBeacon(paramString, paramHashMap);
  }
  
  public void userBehaviorStatistics(String paramString)
  {
    TBSStatManager.getInstance().userBehaviorStatistics(paramString);
  }
  
  public void userBehaviorStatistics(String paramString, int paramInt)
  {
    TBSStatManager.getInstance().userBehaviorStatistics(paramString, paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\plugin\PluginRelateFuncImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */