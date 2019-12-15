package com.tencent.tbs.core.webkit;

import android.content.Context;
import org.chromium.base.annotations.UsedByReflection;

@Deprecated
public final class CookieSyncManager
  extends WebSyncManager
{
  private static boolean sGetInstanceAllowed = false;
  private static CookieSyncManager sRef;
  
  private CookieSyncManager()
  {
    super(null, null);
  }
  
  private static void checkInstanceIsAllowed()
  {
    if (sGetInstanceAllowed) {
      return;
    }
    throw new IllegalStateException("CookieSyncManager::createInstance() needs to be called before CookieSyncManager::getInstance()");
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static CookieSyncManager createInstance(Context paramContext)
  {
    if (paramContext != null) {
      try
      {
        setGetInstanceIsAllowed();
        paramContext = getInstance();
        return paramContext;
      }
      finally
      {
        break label33;
      }
    }
    throw new IllegalArgumentException("Invalid context argument");
    label33:
    throw paramContext;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static CookieSyncManager getInstance()
  {
    try
    {
      checkInstanceIsAllowed();
      if (sRef == null) {
        sRef = new CookieSyncManager();
      }
      CookieSyncManager localCookieSyncManager = sRef;
      return localCookieSyncManager;
    }
    finally {}
  }
  
  static void setGetInstanceIsAllowed()
  {
    sGetInstanceAllowed = true;
  }
  
  @Deprecated
  public void resetSync() {}
  
  @Deprecated
  @UsedByReflection("WebCoreProxy.java")
  public void startSync() {}
  
  @Deprecated
  @UsedByReflection("WebCoreProxy.java")
  public void stopSync() {}
  
  @Deprecated
  @UsedByReflection("WebCoreProxy.java")
  public void sync()
  {
    CookieManager.getInstance().flush();
  }
  
  @Deprecated
  protected void syncFromRamToFlash()
  {
    CookieManager.getInstance().flush();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\CookieSyncManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */