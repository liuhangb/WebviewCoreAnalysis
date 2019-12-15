package com.tencent.tbs.tbsshell.common.X5LoadPlugin;

import android.content.Context;

public class X5LoadPluginManagerService
{
  private static final String TAG = "X5LoadPluginManagerService";
  private static X5LoadPluginManagerService sInstance;
  private X5LoadPluginManager mPluginManager;
  
  private X5LoadPluginManagerService(Context paramContext)
  {
    this.mPluginManager = new X5LoadPluginManager(paramContext);
  }
  
  public static X5LoadPluginManagerService getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null) {
        sInstance = new X5LoadPluginManagerService(paramContext);
      }
      paramContext = sInstance;
      return paramContext;
    }
    finally {}
  }
  
  public void loadX5Plugin(int paramInt, IX5LoadPluginCallback paramIX5LoadPluginCallback)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("loadX5Plugin soType: ");
    localStringBuilder.append(paramInt);
    localStringBuilder.toString();
    this.mPluginManager.loadX5Plugin(paramInt, paramIX5LoadPluginCallback);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\X5LoadPlugin\X5LoadPluginManagerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */