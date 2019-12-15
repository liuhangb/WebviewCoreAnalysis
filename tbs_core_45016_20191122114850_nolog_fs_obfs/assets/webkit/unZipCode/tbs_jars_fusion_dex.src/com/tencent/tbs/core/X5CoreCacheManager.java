package com.tencent.tbs.core;

import com.tencent.smtt.export.external.interfaces.IX5CoreCacheManager;
import com.tencent.tbs.core.webkit.tencent.TencentCacheManager;
import com.tencent.tbs.core.webkit.tencent.TencentCacheManager.CacheResult;
import java.io.File;
import java.io.InputStream;

public class X5CoreCacheManager
  implements IX5CoreCacheManager
{
  private static X5CoreCacheManager sInstance;
  
  public static X5CoreCacheManager getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new X5CoreCacheManager();
      }
      X5CoreCacheManager localX5CoreCacheManager = sInstance;
      return localX5CoreCacheManager;
    }
    finally {}
  }
  
  public boolean cacheDisabled()
  {
    return TencentCacheManager.cacheDisabled();
  }
  
  public void clearLocalStorage() {}
  
  public void clearNetworkCache()
  {
    TencentCacheManager.removeAllCacheFiles();
  }
  
  public InputStream getCacheFile(String paramString, boolean paramBoolean)
  {
    paramString = TencentCacheManager.getCacheFile(paramString, null);
    boolean bool = paramString.isNeedToRedirect();
    String str = paramString.getLocation();
    if (bool) {
      paramString = TencentCacheManager.getCacheFile(str, null);
    }
    if (paramString != null)
    {
      bool = paramString.isExpired();
      if ((paramBoolean) || (!bool)) {
        return paramString.getInputStream();
      }
    }
    return null;
  }
  
  public File getCacheFileBaseDir()
  {
    return TencentCacheManager.getCacheFileBaseDir();
  }
  
  public String getLocalPath(String paramString)
  {
    return TencentCacheManager.getCacheFile(paramString, null).getLocalPath();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\X5CoreCacheManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */