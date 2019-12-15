package com.tencent.common.plugin;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.tencent.mtt.AppInfoHolder;
import com.tencent.mtt.AppInfoHolder.AppInfoID;

public class PluginSetting
{
  public static final String KEY_FOR_CPU_TYE = "key_plugin_cpu_tye";
  public static final String KEY_FOR_MINIQB_DEBUG = "key_plugin_debug_env";
  public static final String[] KEY_PLUGINLIST_FAIL_NEED_RETRY;
  public static final String[] KEY_PLUGINLIST_LASTPULL_PLUGINLIST = { "", "key_pluginlist_last_pull_pluignList_qb", "key_pluginlist_last_pull_pluignList_tbs" };
  public static final String[] KEY_PLUGIN_LIST_MD5 = { "", "key_plugin_list_md5_qb", "key_plugin_list_md5_tbs" };
  private static volatile PluginSetting jdField_a_of_type_ComTencentCommonPluginPluginSetting;
  public static String sBuildId = "";
  private SharedPreferences.Editor jdField_a_of_type_AndroidContentSharedPreferences$Editor;
  private SharedPreferences jdField_a_of_type_AndroidContentSharedPreferences;
  
  static
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("key_pluginlist_not_sync_tosvr_qb");
    ((StringBuilder)localObject).append(AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_BUILD_NO));
    localObject = ((StringBuilder)localObject).toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("key_pluginlist_not_sync_tosvr_tbs");
    localStringBuilder.append(AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_BUILD_NO));
    KEY_PLUGINLIST_FAIL_NEED_RETRY = new String[] { "", localObject, localStringBuilder.toString() };
  }
  
  private PluginSetting(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentSharedPreferences = paramContext.getSharedPreferences("plugin_setting", 0);
    this.jdField_a_of_type_AndroidContentSharedPreferences$Editor = this.jdField_a_of_type_AndroidContentSharedPreferences.edit();
  }
  
  public static PluginSetting getInstance(Context paramContext)
  {
    if (jdField_a_of_type_ComTencentCommonPluginPluginSetting == null) {
      try
      {
        if (jdField_a_of_type_ComTencentCommonPluginPluginSetting == null) {
          jdField_a_of_type_ComTencentCommonPluginPluginSetting = new PluginSetting(paramContext);
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentCommonPluginPluginSetting;
  }
  
  public String getCpuType()
  {
    SharedPreferences localSharedPreferences = this.jdField_a_of_type_AndroidContentSharedPreferences;
    if (localSharedPreferences != null) {
      return localSharedPreferences.getString("key_plugin_cpu_tye", "");
    }
    return "";
  }
  
  public boolean getMiniQBDebugFlag()
  {
    SharedPreferences localSharedPreferences = this.jdField_a_of_type_AndroidContentSharedPreferences;
    if (localSharedPreferences != null) {
      return localSharedPreferences.getBoolean("key_plugin_debug_env", false);
    }
    return false;
  }
  
  public long pluginListLastTime(int paramInt)
  {
    SharedPreferences localSharedPreferences;
    if (paramInt >= 0)
    {
      if (paramInt > 2) {
        return 0L;
      }
      localSharedPreferences = this.jdField_a_of_type_AndroidContentSharedPreferences;
      if (localSharedPreferences == null) {}
    }
    try
    {
      long l = localSharedPreferences.getLong(KEY_PLUGINLIST_LASTPULL_PLUGINLIST[paramInt], 0L);
      return l;
    }
    catch (Exception localException) {}
    return 0L;
    return 0L;
    return 0L;
  }
  
  public String pluginListRspMD5(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= 2))
    {
      SharedPreferences localSharedPreferences = this.jdField_a_of_type_AndroidContentSharedPreferences;
      if (localSharedPreferences != null) {
        return localSharedPreferences.getString(KEY_PLUGIN_LIST_MD5[paramInt], "");
      }
      return "";
    }
    return "";
  }
  
  public boolean pluginListSyncSameToSvr(int paramInt)
  {
    if (paramInt >= 0)
    {
      if (paramInt > 2) {
        return false;
      }
      SharedPreferences localSharedPreferences = this.jdField_a_of_type_AndroidContentSharedPreferences;
      if (localSharedPreferences != null) {
        return localSharedPreferences.getBoolean(KEY_PLUGINLIST_FAIL_NEED_RETRY[paramInt], false);
      }
      return false;
    }
    return false;
  }
  
  public void setCpuType(String paramString)
  {
    if (this.jdField_a_of_type_AndroidContentSharedPreferences != null) {
      this.jdField_a_of_type_AndroidContentSharedPreferences$Editor.putString("key_plugin_cpu_tye", paramString);
    }
  }
  
  public void setPluginListLastTime(int paramInt, long paramLong)
  {
    if (paramInt >= 0)
    {
      if (paramInt > 2) {
        return;
      }
      this.jdField_a_of_type_AndroidContentSharedPreferences$Editor.putLong(KEY_PLUGINLIST_LASTPULL_PLUGINLIST[paramInt], paramLong).commit();
      return;
    }
  }
  
  public void setPluginListRspMD5(int paramInt, String paramString)
  {
    if (paramInt >= 0)
    {
      if (paramInt > 2) {
        return;
      }
      this.jdField_a_of_type_AndroidContentSharedPreferences$Editor.putString(KEY_PLUGIN_LIST_MD5[paramInt], paramString).commit();
      return;
    }
  }
  
  public void setPluginListSyncSameToSvr(int paramInt, boolean paramBoolean)
  {
    if (paramInt >= 0)
    {
      if (paramInt > 2) {
        return;
      }
      this.jdField_a_of_type_AndroidContentSharedPreferences$Editor.putBoolean(KEY_PLUGINLIST_FAIL_NEED_RETRY[paramInt], paramBoolean).commit();
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\PluginSetting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */