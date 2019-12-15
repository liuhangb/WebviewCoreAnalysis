package com.tencent.tbs.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.tbs.common.settings.PublicSettingManager;

public class TbsWUPTestManager
{
  private static final String KEY_TEST_ENVIRONMENT_WUP_ADDRESS = "key_test_environment_wup_address";
  private static final String TAG = "TbsWUPTestManager";
  public static final String TEST_WUP_PROXY_ADDRESS = "http://14.17.41.197:18000";
  private static volatile TbsWUPTestManager mInstance;
  private Context mContext = null;
  private String mCustomedWupAddress = null;
  private boolean mIsDebugEnviroment = false;
  private SharedPreferences mPreference = null;
  
  private TbsWUPTestManager(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    loadSharedPreferences();
  }
  
  public static TbsWUPTestManager getInstance(Context paramContext)
  {
    if (mInstance == null) {
      try
      {
        if (mInstance == null) {
          mInstance = new TbsWUPTestManager(paramContext);
        }
      }
      finally {}
    }
    return mInstance;
  }
  
  private void judgeWupEnviroment(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.mIsDebugEnviroment = true;
      return;
    }
    this.mIsDebugEnviroment = false;
  }
  
  private void loadSharedPreferences()
  {
    if (this.mPreference == null)
    {
      Context localContext = this.mContext;
      PublicSettingManager.getInstance();
      this.mPreference = localContext.getSharedPreferences(PublicSettingManager.SHARE_PREFERENCES_NAME, 0);
    }
  }
  
  public String getCustomedWupAddress()
  {
    if (this.mCustomedWupAddress == null) {
      this.mCustomedWupAddress = this.mPreference.getString("key_test_environment_wup_address", null);
    }
    judgeWupEnviroment(this.mCustomedWupAddress);
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getCustomedWupAddress:");
    localStringBuilder.append(this.mCustomedWupAddress);
    Log.e(str, localStringBuilder.toString());
    return this.mCustomedWupAddress;
  }
  
  public boolean isDebugWupEnvironment()
  {
    return this.mIsDebugEnviroment;
  }
  
  public void setCustomedWupAddress(String paramString)
  {
    judgeWupEnviroment(paramString);
    this.mCustomedWupAddress = paramString;
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setCustomedWupAddress:");
    localStringBuilder.append(this.mCustomedWupAddress);
    Log.e(str, localStringBuilder.toString());
    this.mPreference.edit().putString("key_test_environment_wup_address", paramString).commit();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\TbsWUPTestManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */