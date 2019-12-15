package android.webview.chromium;

import com.tencent.tbs.core.webkit.ValueCallback;
import com.tencent.tbs.core.webkit.WebStorage;
import com.tencent.tbs.core.webkit.WebStorage.Origin;
import java.util.HashMap;
import java.util.Map;
import org.chromium.android_webview.AwQuotaManagerBridge;
import org.chromium.android_webview.AwQuotaManagerBridge.Origins;
import org.chromium.base.Callback;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.UsedByReflection;

public class WebStorageAdapter
  extends WebStorage
{
  private final WebViewChromiumFactoryProvider mFactory;
  private final AwQuotaManagerBridge mQuotaManagerBridge;
  
  public WebStorageAdapter(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider, AwQuotaManagerBridge paramAwQuotaManagerBridge)
  {
    this.mFactory = paramWebViewChromiumFactoryProvider;
    this.mQuotaManagerBridge = paramAwQuotaManagerBridge;
  }
  
  private static boolean checkNeedsPost()
  {
    return ThreadUtils.runningOnUiThread() ^ true;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void deleteAllData()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebStorageAdapter.this.mQuotaManagerBridge.deleteAllData();
        }
      });
      return;
    }
    this.mQuotaManagerBridge.deleteAllData();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void deleteOrigin(final String paramString)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebStorageAdapter.this.mQuotaManagerBridge.deleteOrigin(paramString);
        }
      });
      return;
    }
    this.mQuotaManagerBridge.deleteOrigin(paramString);
  }
  
  public void getOrigins(final ValueCallback<Map> paramValueCallback)
  {
    paramValueCallback = new Callback()
    {
      public void onResult(AwQuotaManagerBridge.Origins paramAnonymousOrigins)
      {
        HashMap localHashMap = new HashMap();
        int i = 0;
        while (i < paramAnonymousOrigins.mOrigins.length)
        {
          WebStorage.Origin local1 = new WebStorage.Origin(paramAnonymousOrigins.mOrigins[i], paramAnonymousOrigins.mQuotas[i], paramAnonymousOrigins.mUsages[i]) {};
          localHashMap.put(paramAnonymousOrigins.mOrigins[i], local1);
          i += 1;
        }
        paramValueCallback.onReceiveValue(localHashMap);
      }
    };
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebStorageAdapter.this.mQuotaManagerBridge.getOrigins(paramValueCallback);
        }
      });
      return;
    }
    this.mQuotaManagerBridge.getOrigins(paramValueCallback);
  }
  
  public void getQuotaForOrigin(final String paramString, final ValueCallback<Long> paramValueCallback)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebStorageAdapter.this.mQuotaManagerBridge.getQuotaForOrigin(paramString, CallbackConverter.fromValueCallback(paramValueCallback));
        }
      });
      return;
    }
    this.mQuotaManagerBridge.getQuotaForOrigin(paramString, CallbackConverter.fromValueCallback(paramValueCallback));
  }
  
  public void getUsageForOrigin(final String paramString, final ValueCallback<Long> paramValueCallback)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebStorageAdapter.this.mQuotaManagerBridge.getUsageForOrigin(paramString, CallbackConverter.fromValueCallback(paramValueCallback));
        }
      });
      return;
    }
    this.mQuotaManagerBridge.getUsageForOrigin(paramString, CallbackConverter.fromValueCallback(paramValueCallback));
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setQuotaForOrigin(String paramString, long paramLong) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebStorageAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */