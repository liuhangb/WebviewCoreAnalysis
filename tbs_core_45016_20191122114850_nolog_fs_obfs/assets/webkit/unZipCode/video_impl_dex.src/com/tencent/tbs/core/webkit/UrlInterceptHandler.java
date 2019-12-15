package com.tencent.tbs.core.webkit;

import android.webkit.CacheManager.CacheResult;
import android.webkit.PluginData;
import java.util.Map;

@Deprecated
public abstract interface UrlInterceptHandler
{
  @Deprecated
  public abstract PluginData getPluginData(String paramString, Map<String, String> paramMap);
  
  @Deprecated
  public abstract CacheManager.CacheResult service(String paramString, Map<String, String> paramMap);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\UrlInterceptHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */