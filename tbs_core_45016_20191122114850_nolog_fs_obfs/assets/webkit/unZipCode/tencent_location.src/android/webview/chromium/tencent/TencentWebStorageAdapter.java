package android.webview.chromium.tencent;

import android.webview.chromium.WebStorageAdapter;
import android.webview.chromium.WebViewChromiumFactoryProvider;
import java.util.Map;
import org.chromium.android_webview.AwQuotaManagerBridge;
import org.chromium.base.annotations.UsedByReflection;

public class TencentWebStorageAdapter
  extends WebStorageAdapter
{
  @UsedByReflection("WebCoreProxy.java")
  public TencentWebStorageAdapter(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider, AwQuotaManagerBridge paramAwQuotaManagerBridge)
  {
    super(paramWebViewChromiumFactoryProvider, paramAwQuotaManagerBridge);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void getOrigins(final android.webkit.ValueCallback<Map> paramValueCallback)
  {
    if (paramValueCallback == null) {
      paramValueCallback = null;
    } else {
      paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
      {
        public void onReceiveValue(Map paramAnonymousMap)
        {
          paramValueCallback.onReceiveValue(paramAnonymousMap);
        }
      };
    }
    getOrigins(paramValueCallback);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void getQuotaForOrigin(String paramString, final android.webkit.ValueCallback<Long> paramValueCallback)
  {
    if (paramValueCallback == null) {
      paramValueCallback = null;
    } else {
      paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
      {
        public void onReceiveValue(Long paramAnonymousLong)
        {
          paramValueCallback.onReceiveValue(paramAnonymousLong);
        }
      };
    }
    getQuotaForOrigin(paramString, paramValueCallback);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void getUsageForOrigin(String paramString, final android.webkit.ValueCallback<Long> paramValueCallback)
  {
    if (paramValueCallback == null) {
      paramValueCallback = null;
    } else {
      paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
      {
        public void onReceiveValue(Long paramAnonymousLong)
        {
          paramValueCallback.onReceiveValue(paramAnonymousLong);
        }
      };
    }
    getUsageForOrigin(paramString, paramValueCallback);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentWebStorageAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */