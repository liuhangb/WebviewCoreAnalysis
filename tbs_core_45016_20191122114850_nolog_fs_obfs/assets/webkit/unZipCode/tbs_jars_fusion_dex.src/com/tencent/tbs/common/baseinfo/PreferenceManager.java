package com.tencent.tbs.common.baseinfo;

import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.MTT.PreferencesKeyValue;
import com.tencent.tbs.common.MTT.PreferencesRsp;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.util.ArrayList;
import java.util.Iterator;

public class PreferenceManager
{
  private static final String TAG = "PreferenceManager";
  private static PreferenceManager sInstance;
  
  public static PreferenceManager getInstancce()
  {
    if (sInstance == null) {
      sInstance = new PreferenceManager();
    }
    return sInstance;
  }
  
  public String getPreference(byte paramByte, String paramString)
  {
    try
    {
      if (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB) {
        return PublicSettingManager.getInstance().getMiniQBPref(paramString);
      }
      return "";
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return "";
  }
  
  public int getPreferenceInt(byte paramByte, String paramString, int paramInt)
  {
    for (;;)
    {
      try
      {
        if (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB)
        {
          paramString = PublicSettingManager.getInstance().getMiniQBPref(paramString);
          paramByte = Integer.valueOf(paramString).intValue();
          return paramByte;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        return paramInt;
      }
      paramString = "";
    }
  }
  
  public void onPreference(WUPResponseBase paramWUPResponseBase, byte paramByte)
  {
    if (paramWUPResponseBase == null) {
      return;
    }
    Object localObject1 = (PreferencesRsp)paramWUPResponseBase.get("rsp");
    if (localObject1 == null)
    {
      LogUtils.d("PreferenceManager", "preference result list is empty");
      return;
    }
    Object localObject2 = ((PreferencesRsp)localObject1).vPreferencesKeyValue;
    if ((localObject2 != null) && (!((ArrayList)localObject2).isEmpty()))
    {
      paramWUPResponseBase = PublicSettingManager.getInstance();
      paramWUPResponseBase.setBreakCommit(true);
      localObject2 = ((ArrayList)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        PreferencesKeyValue localPreferencesKeyValue = (PreferencesKeyValue)((Iterator)localObject2).next();
        if (localPreferencesKeyValue != null)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(localPreferencesKeyValue.sKey);
          localStringBuilder.append(localPreferencesKeyValue.sValue);
          if ((!TextUtils.isEmpty(localStringBuilder.toString())) && (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB))
          {
            paramWUPResponseBase.setMiniQBPref(localPreferencesKeyValue.sKey, localPreferencesKeyValue.sValue);
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("MINIQB PREFS: key = ");
            localStringBuilder.append(localPreferencesKeyValue.sKey);
            localStringBuilder.append(", value = ");
            localStringBuilder.append(localPreferencesKeyValue.sValue);
            LogUtils.d("PreferenceManager", localStringBuilder.toString());
          }
        }
      }
      int i = ((PreferencesRsp)localObject1).iRspTime;
      if (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB)
      {
        paramWUPResponseBase.setMiniQBPreferenceUpdateTime(i);
        paramWUPResponseBase.setLastMiniqbVersionForPrefs(TbsBaseModuleShell.getMiniQBVersion());
      }
      else
      {
        paramWUPResponseBase.setPreferenceUpdateTime(i);
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("onPreferences: rsp.vPreferencesKeyValue size = ");
      ((StringBuilder)localObject2).append(((PreferencesRsp)localObject1).vPreferencesKeyValue.size());
      LogUtils.d("debugPreference", ((StringBuilder)localObject2).toString());
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("onPreferences: update prefernce time=");
      ((StringBuilder)localObject1).append(i);
      LogUtils.d("debugPreference", ((StringBuilder)localObject1).toString());
      paramWUPResponseBase.setBreakCommit(false);
      paramWUPResponseBase.commitAll();
      return;
    }
    LogUtils.d("PreferenceManager", "vPreferencesKeyValue list is empty, ignore");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\PreferenceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */