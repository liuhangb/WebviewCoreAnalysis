package com.tencent.tbs.core;

import android.webkit.ValueCallback;
import android.webview.chromium.tencent.TencentWebStorageAdapter;
import com.tencent.smtt.export.external.interfaces.IX5CoreWebStorage;
import com.tencent.tbs.core.webkit.WebStorage;
import java.util.Map;

public class X5CoreWebStorage
  implements IX5CoreWebStorage
{
  private static X5CoreWebStorage sInstance;
  
  public static X5CoreWebStorage getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new X5CoreWebStorage();
      }
      X5CoreWebStorage localX5CoreWebStorage = sInstance;
      return localX5CoreWebStorage;
    }
    finally {}
  }
  
  public void deleteAllData()
  {
    TencentWebStorageAdapter.getInstance().deleteAllData();
  }
  
  public void deleteOrigin(String paramString)
  {
    TencentWebStorageAdapter.getInstance().deleteOrigin(paramString);
  }
  
  public void getOrigins(ValueCallback<Map> paramValueCallback)
  {
    WebStorage localWebStorage = TencentWebStorageAdapter.getInstance();
    if ((localWebStorage instanceof TencentWebStorageAdapter)) {
      ((TencentWebStorageAdapter)localWebStorage).getOrigins(paramValueCallback);
    }
  }
  
  public void getQuotaForOrigin(String paramString, ValueCallback<Long> paramValueCallback)
  {
    WebStorage localWebStorage = TencentWebStorageAdapter.getInstance();
    if ((localWebStorage instanceof TencentWebStorageAdapter)) {
      ((TencentWebStorageAdapter)localWebStorage).getQuotaForOrigin(paramString, paramValueCallback);
    }
  }
  
  public void getUsageForOrigin(String paramString, ValueCallback<Long> paramValueCallback)
  {
    WebStorage localWebStorage = TencentWebStorageAdapter.getInstance();
    if ((localWebStorage instanceof TencentWebStorageAdapter)) {
      ((TencentWebStorageAdapter)localWebStorage).getUsageForOrigin(paramString, paramValueCallback);
    }
  }
  
  public void setQuotaForOrigin(String paramString, long paramLong)
  {
    TencentWebStorageAdapter.getInstance().setQuotaForOrigin(paramString, paramLong);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\X5CoreWebStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */