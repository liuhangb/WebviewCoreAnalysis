package com.tencent.tbs.common.wup;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.tencent.common.wup.interfaces.IWUPClientProxy;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.utils.TbsCommonConfig;
import com.tencent.tbs.common.utils.TbsInfoUtils;

public class TbsWupClientProxy
  extends IWUPClientProxy
{
  private static String mSetWupAddressByForce = "";
  private PublicSettingManager mPbSettingManager = null;
  
  private SharedPreferences getPreference()
  {
    try
    {
      if (this.mPbSettingManager == null) {
        this.mPbSettingManager = PublicSettingManager.getInstance();
      }
      SharedPreferences localSharedPreferences = this.mPbSettingManager.getPreference();
      return localSharedPreferences;
    }
    finally {}
  }
  
  public boolean getBooleanConfiguration(String paramString, boolean paramBoolean)
  {
    return getPreference().getBoolean(paramString, paramBoolean);
  }
  
  public byte[] getByteGuid()
  {
    return GUIDFactory.getInstance().getByteGuid();
  }
  
  public String getCustomWupProxyAddress()
  {
    mSetWupAddressByForce = PublicSettingManager.getInstance().getCustomedWupAddress();
    if (!TextUtils.isEmpty(mSetWupAddressByForce)) {
      return mSetWupAddressByForce;
    }
    Object localObject = TbsBaseModuleShell.getCallerContext();
    if (localObject != null) {
      localObject = TbsCommonConfig.getInstance((Context)localObject).getWupProxyAddress();
    } else {
      localObject = null;
    }
    if (!TextUtils.isEmpty((CharSequence)localObject)) {
      return (String)localObject;
    }
    return null;
  }
  
  public int getIntConfiguration(String paramString, int paramInt)
  {
    return getPreference().getInt(paramString, paramInt);
  }
  
  public long getLongConfiguration(String paramString, long paramLong)
  {
    return getPreference().getLong(paramString, paramLong);
  }
  
  public String getQUA(boolean paramBoolean)
  {
    if (((TbsBaseModuleShell.getEnableQua1()) || (paramBoolean)) && (TbsInfoUtils.getQUA() != null) && (TbsInfoUtils.getQUA().length() > 0)) {
      return TbsInfoUtils.getQUA();
    }
    return null;
  }
  
  public String getQUA2_V3()
  {
    return TbsInfoUtils.getQUA2();
  }
  
  public void setBooleanConfiguration(String paramString, boolean paramBoolean)
  {
    PublicSettingManager.editorApply(getPreference().edit().putBoolean(paramString, paramBoolean));
  }
  
  public void setIntConfiguration(String paramString, int paramInt)
  {
    PublicSettingManager.editorApply(getPreference().edit().putInt(paramString, paramInt));
  }
  
  public void setLongConfiguration(String paramString, long paramLong)
  {
    PublicSettingManager.editorApply(getPreference().edit().putLong(paramString, paramLong));
  }
  
  public void userBehaviorStatistics(String paramString)
  {
    TBSStatManager.getInstance().userBehaviorStatistics(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wup\TbsWupClientProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */