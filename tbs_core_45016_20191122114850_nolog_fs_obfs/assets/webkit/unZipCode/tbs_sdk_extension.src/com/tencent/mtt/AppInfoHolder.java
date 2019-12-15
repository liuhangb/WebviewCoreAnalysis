package com.tencent.mtt;

import com.tencent.common.manifest.AppManifest;
import com.tencent.common.manifest.annotation.Extension;

public class AppInfoHolder
{
  private static IAppInfoProvider a;
  public static boolean sIsDevVersion = false;
  
  static IAppInfoProvider a()
  {
    IAppInfoProvider localIAppInfoProvider = a;
    if (localIAppInfoProvider != null) {
      return localIAppInfoProvider;
    }
    for (;;)
    {
      try
      {
        localIAppInfoProvider = a;
        if (localIAppInfoProvider != null) {}
      }
      finally {}
      try
      {
        a = (IAppInfoProvider)AppManifest.getInstance().queryExtension(IAppInfoProvider.class, null);
      }
      catch (Throwable localThrowable) {}
    }
    a = null;
    return a;
  }
  
  public static String getAppInfoByID(AppInfoID paramAppInfoID)
  {
    if (a() == null) {
      return "";
    }
    return a().getAppInfoById(paramAppInfoID);
  }
  
  public static void setAppInfoProvider(IAppInfoProvider paramIAppInfoProvider)
  {
    a = paramIAppInfoProvider;
  }
  
  public static enum AppInfoID
  {
    static
    {
      APP_INFO_LC = new AppInfoID("APP_INFO_LC", 3);
      APP_INFO_LCID = new AppInfoID("APP_INFO_LCID", 4);
      APP_INFO_BUILD_NO = new AppInfoID("APP_INFO_BUILD_NO", 5);
      APP_INFO_QIMEI = new AppInfoID("APP_INFO_QIMEI", 6);
      APP_INFO_ACTIVE_CHANNEL_ID = new AppInfoID("APP_INFO_ACTIVE_CHANNEL_ID", 7);
      APP_INFO_CURRENT_CHANNEL_ID = new AppInfoID("APP_INFO_CURRENT_CHANNEL_ID", 8);
      APP_INFO_ASSETS_CHANNEL_ID = new AppInfoID("APP_INFO_ASSETS_CHANNEL_ID", 9);
      APP_INFO_FACTORY_CHANNEL_ID = new AppInfoID("APP_INFO_FACTORY_CHANNEL_ID", 10);
      APP_INFO_GUID = new AppInfoID("APP_INFO_GUID", 11);
      APP_INFO_QAUTH = new AppInfoID("APP_INFO_QAUTH", 12);
      APP_INFO_USER_AGENT = new AppInfoID("APP_INFO_USER_AGENT", 13);
      APP_INFO_MAIN_VERSION = new AppInfoID("APP_INFO_MAIN_VERSION", 14);
      APP_INFO_VERSION_NAME = new AppInfoID("APP_INFO_VERSION_NAME", 15);
      APP_INFO_VERSION_REVISE = new AppInfoID("APP_INFO_VERSION_REVISE", 16);
    }
    
    private AppInfoID() {}
  }
  
  @Extension
  public static abstract interface IAppInfoProvider
  {
    public abstract String getAppInfoById(AppInfoHolder.AppInfoID paramAppInfoID);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\AppInfoHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */