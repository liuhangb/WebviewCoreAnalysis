package com.tencent.tbs.core.webkit;

import java.util.Map;
import org.chromium.base.annotations.UsedByReflection;

public class WebStorage
{
  @UsedByReflection("WebCoreProxy.java")
  public static WebStorage getInstance()
  {
    return WebViewFactory.getProvider().getWebStorage();
  }
  
  public void deleteAllData() {}
  
  public void deleteOrigin(String paramString) {}
  
  public void getOrigins(ValueCallback<Map> paramValueCallback) {}
  
  public void getQuotaForOrigin(String paramString, ValueCallback<Long> paramValueCallback) {}
  
  public void getUsageForOrigin(String paramString, ValueCallback<Long> paramValueCallback) {}
  
  @Deprecated
  public void setQuotaForOrigin(String paramString, long paramLong) {}
  
  public static class Origin
  {
    private String mOrigin = null;
    private long mQuota = 0L;
    private long mUsage = 0L;
    
    protected Origin(String paramString, long paramLong1, long paramLong2)
    {
      this.mOrigin = paramString;
      this.mQuota = paramLong1;
      this.mUsage = paramLong2;
    }
    
    public String getOrigin()
    {
      return this.mOrigin;
    }
    
    public long getQuota()
    {
      return this.mQuota;
    }
    
    public long getUsage()
    {
      return this.mUsage;
    }
  }
  
  @Deprecated
  public static abstract interface QuotaUpdater
  {
    public abstract void updateQuota(long paramLong);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\WebStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */