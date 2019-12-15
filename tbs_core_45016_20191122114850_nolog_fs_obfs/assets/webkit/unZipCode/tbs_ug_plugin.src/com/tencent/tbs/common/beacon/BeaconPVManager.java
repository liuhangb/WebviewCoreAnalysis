package com.tencent.tbs.common.beacon;

import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import java.util.HashMap;

public class BeaconPVManager
{
  private static final String TAG = "BeaconPVManager";
  
  public static void uploadPvToBeacon(String paramString1, long paramLong1, long paramLong2, int paramInt1, String paramString2, boolean paramBoolean1, int paramInt2, boolean paramBoolean2, int paramInt3, boolean paramBoolean3, int paramInt4, int paramInt5)
  {
    if (TextUtils.isEmpty(paramString1))
    {
      LogUtils.d("BeaconPVManager", "report metrics, domain==null");
      return;
    }
    paramInt1 = (int)(paramLong1 + paramLong2);
    paramString2 = "";
    if (paramBoolean3) {
      paramString2 = TbsBaseModuleShell.getMiniQBVersion();
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("PvType", "web");
    localHashMap.put("domain", paramString1);
    localHashMap.put("pvcount", "1");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramInt1);
    localStringBuilder.append("");
    localHashMap.put("Flow", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramBoolean1);
    localStringBuilder.append("");
    localHashMap.put("isProxy", localStringBuilder.toString());
    localHashMap.put("MTT_MINIQB_VERSION", paramString2);
    paramString2 = new StringBuilder();
    paramString2.append(paramInt4);
    paramString2.append("");
    localHashMap.put("mProxyType", paramString2.toString());
    paramString2 = new StringBuilder();
    paramString2.append(paramInt3);
    paramString2.append("");
    localHashMap.put("mCacheType", paramString2.toString());
    paramString2 = new StringBuilder();
    paramString2.append(paramInt5);
    paramString2.append("");
    localHashMap.put("connectInfo", paramString2.toString());
    if ("servicewechat.com".equals(paramString1))
    {
      X5CoreBeaconUploader.getInstance(TbsBaseModuleShell.getCallerContext()).upLoadToBeacon("MTT_CORE_PV_INFO_SAMPLING", localHashMap);
      return;
    }
    paramString1 = new StringBuilder();
    paramString1.append("send to beacon MTT_CORE_PV_INFO ");
    paramString1.append(localHashMap.toString());
    LogUtils.d("BeaconPVManager", paramString1.toString());
    X5CoreBeaconUploader.getInstance(TbsBaseModuleShell.getCallerContext()).upLoadToBeacon("MTT_CORE_PV_INFO", localHashMap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\beacon\BeaconPVManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */