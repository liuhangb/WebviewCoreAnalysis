package com.tencent.tbs.tbsshell.partner.miniqb;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.PreferenceManager;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.baseinfo.TbsWupManager;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import java.util.ArrayList;
import java.util.HashMap;

public class TbsMiniQBService
{
  private Context mAppContext;
  private byte mImplType = 0;
  
  public TbsMiniQBService(byte paramByte)
  {
    this.mImplType = paramByte;
    if (this.mImplType == 1) {
      TbsBaseModuleShell.setMiniQBVersion(TbsMiniQBProxy.getMiniQbVersion());
    }
  }
  
  public void doMultiWupRequest()
  {
    TbsWupManager.getInstance().doMultiWupRequestForThirdParty(this.mImplType);
  }
  
  public void forceDomainAndPrefs(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("shell force MultiWupRequest, type=");
    localStringBuilder.append(this.mImplType);
    LogUtils.d("SmttService", localStringBuilder.toString());
    TbsWupManager.getInstance().setForcePullDomainAndPrefs(paramBoolean, this.mImplType);
  }
  
  public byte[] getByteGuid()
  {
    LogUtils.d("SmttService", "getByteGuid, called ");
    return GUIDFactory.getInstance().getByteGuid();
  }
  
  public ArrayList<String> getDomainList(int paramInt)
  {
    return TbsDomainListDataProvider.getInstance(this.mImplType).getDomainList(paramInt);
  }
  
  public String getPreference(String paramString)
  {
    return PreferenceManager.getInstancce().getPreference(this.mImplType, paramString);
  }
  
  public int getPreferenceInt(String paramString, int paramInt)
  {
    return PreferenceManager.getInstancce().getPreferenceInt(this.mImplType, paramString, paramInt);
  }
  
  public String getQUA2()
  {
    return TbsInfoUtils.getQUA2();
  }
  
  public ArrayList<String> getSocketServerList()
  {
    return TbsUserInfo.getInstance().getSocketServerList();
  }
  
  public boolean isDebugMiniQBEnv()
  {
    return Configuration.getInstance(this.mAppContext).isDebugMiniQB();
  }
  
  public boolean isGuidValidate()
  {
    LogUtils.d("SmttService", "isGuidValidate, called ");
    return GUIDFactory.getInstance().isGuidValidate();
  }
  
  public void sendWUPRequest(Object paramObject1, Object paramObject2, byte[] paramArrayOfByte, Bundle paramBundle)
  {
    if (this.mImplType == 1)
    {
      LogUtils.d("SmttService", "MiniQB send request at shell");
      ShellWupHandler.getInstance().handleMiniQBWupRequest(paramObject1, paramObject2, paramArrayOfByte, paramBundle);
    }
  }
  
  public void setContext(Context paramContext)
  {
    this.mAppContext = paramContext.getApplicationContext();
  }
  
  public void statWithBeacon(String paramString, boolean paramBoolean1, long paramLong1, long paramLong2, HashMap<String, String> paramHashMap, boolean paramBoolean2)
  {
    if ((!TextUtils.isEmpty(paramString)) && (paramHashMap != null))
    {
      if (paramHashMap.isEmpty()) {
        return;
      }
      if (this.mImplType == 1) {
        paramHashMap.put("MTT_MINIQB_VERSION", TbsMiniQBProxy.getMiniQbVersion());
      }
      X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon(paramString, paramBoolean1, paramLong1, paramLong2, paramHashMap, paramBoolean2);
      return;
    }
  }
  
  public void userBehaviorStatistics(String paramString, int paramInt, boolean paramBoolean)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    boolean bool1 = PublicSettingManager.getInstance().getIsUploadPvinWup();
    boolean bool2 = false;
    if (bool1)
    {
      localObject = TBSStatManager.getInstance();
      if (this.mImplType == 1) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      ((TBSStatManager)localObject).userBehaviorStatistics(paramString, paramInt, paramBoolean, false, bool1);
    }
    Object localObject = X5CoreBeaconUploader.getInstance(this.mAppContext);
    bool1 = bool2;
    if (this.mImplType == 1) {
      bool1 = true;
    }
    ((X5CoreBeaconUploader)localObject).userBehaviorStatistics(paramString, paramInt, paramBoolean, bool1);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("UB: action=");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(", value=");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append(", isAccu=");
    ((StringBuilder)localObject).append(paramBoolean);
    ((StringBuilder)localObject).append(", implType=");
    ((StringBuilder)localObject).append(this.mImplType);
    LogUtils.d("SmttService", ((StringBuilder)localObject).toString());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\TbsMiniQBService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */