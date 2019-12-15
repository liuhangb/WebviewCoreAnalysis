package com.tencent.tbs.core.partner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Global;
import com.tencent.smtt.webkit.service.SmttServiceProxy;

public class MeiZuNightModeAdapter
{
  private static MeiZuNightModeAdapter mInstance;
  private MeizuNightModeCallback mCallBack;
  private NightModeReceiver modeReceiver;
  
  public static MeiZuNightModeAdapter getInstance()
  {
    try
    {
      if (mInstance == null) {
        mInstance = new MeiZuNightModeAdapter();
      }
      MeiZuNightModeAdapter localMeiZuNightModeAdapter = mInstance;
      return localMeiZuNightModeAdapter;
    }
    finally {}
  }
  
  private NightModeReceiver getNightModeReceiver()
  {
    if (this.modeReceiver == null) {
      this.modeReceiver = new NightModeReceiver();
    }
    return this.modeReceiver;
  }
  
  private boolean isDeviceSupport()
  {
    try
    {
      Object localObject = Build.DISPLAY.trim();
      if (((String)localObject).startsWith("Flyme"))
      {
        localObject = ((String)localObject).split("\\.");
        if (localObject.length > 0)
        {
          int i = Integer.parseInt(localObject[0].replaceAll("[^\\d]", ""));
          if (i >= 7) {
            return true;
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  private boolean isFearureEnabled()
  {
    return (SmttServiceProxy.getInstance().isMeizuNightModeEnabled()) && (isDeviceSupport());
  }
  
  public void init(Context paramContext, MeizuNightModeCallback paramMeizuNightModeCallback)
  {
    if (!isFearureEnabled()) {
      return;
    }
    try
    {
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("com.meizu.flymelab.nightmode.action.flymelab.SETTINGS_CHANGED");
      this.mCallBack = paramMeizuNightModeCallback;
      paramContext.getApplicationContext().registerReceiver(getNightModeReceiver(), localIntentFilter);
      paramContext = paramContext.getContentResolver();
      int i = 0;
      if (Settings.Global.getInt(paramContext, "flymelab_flyme_night_mode", 0) == 1) {
        i = 1;
      }
      if (i != 0)
      {
        paramMeizuNightModeCallback.onChangeNightMode(true);
        return;
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public void unRegister(Context paramContext)
  {
    if (!isFearureEnabled()) {
      return;
    }
    this.mCallBack = null;
    try
    {
      paramContext.getApplicationContext().unregisterReceiver(getNightModeReceiver());
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    this.modeReceiver = null;
  }
  
  public static abstract interface MeizuNightModeCallback
  {
    public abstract void onChangeNightMode(boolean paramBoolean);
  }
  
  class NightModeReceiver
    extends BroadcastReceiver
  {
    NightModeReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent != null) {
        try
        {
          paramContext = paramIntent.getExtras();
          if (paramContext.containsKey("flymelab_flyme_night_mode")) {
            if (paramContext.getBoolean("flymelab_flyme_night_mode", false))
            {
              if (MeiZuNightModeAdapter.this.mCallBack != null) {
                MeiZuNightModeAdapter.this.mCallBack.onChangeNightMode(true);
              }
            }
            else
            {
              MeiZuNightModeAdapter.this.mCallBack.onChangeNightMode(false);
              return;
            }
          }
        }
        catch (Exception paramContext)
        {
          paramContext.printStackTrace();
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\MeiZuNightModeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */