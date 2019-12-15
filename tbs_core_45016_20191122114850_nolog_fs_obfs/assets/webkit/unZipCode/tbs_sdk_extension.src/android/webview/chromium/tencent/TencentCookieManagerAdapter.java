package android.webview.chromium.tencent;

import android.webview.chromium.CookieManagerAdapter;
import com.tencent.smtt.net.ParseException;
import com.tencent.smtt.net.m;
import com.tencent.smtt.util.MttLog;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.util.HashMap;
import org.chromium.android_webview.AwCookieManager;
import org.chromium.base.Log;
import org.chromium.base.annotations.UsedByReflection;
import org.chromium.tencent.TencentAwCookieManager;

public class TencentCookieManagerAdapter
  extends CookieManagerAdapter
{
  private static final String TAG = "CookieManager";
  AwCookieManager mChromeCookieManager;
  TencentAwCookieManager mTencentChromeCookieManager;
  
  public TencentCookieManagerAdapter(AwCookieManager paramAwCookieManager)
  {
    super(paramAwCookieManager);
    this.mChromeCookieManager = paramAwCookieManager;
    this.mTencentChromeCookieManager = new TencentAwCookieManager();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static TencentCookieManagerAdapter getInstance(boolean paramBoolean)
  {
    try
    {
      TencentCookieManagerAdapter localTencentCookieManagerAdapter = (TencentCookieManagerAdapter)getInstance();
      return localTencentCookieManagerAdapter;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public boolean acceptThirdPartyCookies(Object paramObject)
  {
    return acceptThirdPartyCookies((TencentWebViewProxy.InnerWebView)paramObject);
  }
  
  public void appendDomain(String paramString) {}
  
  @UsedByReflection("X5CoreInit.java")
  public void preInitCookieStore()
  {
    if (MttLog.isEnableLog()) {
      MttLog.d("CookieManagerAdapter preInitCookieStore");
    }
    new Thread(new Runnable()
    {
      public void run()
      {
        synchronized (TencentCookieManagerAdapter.this)
        {
          TencentCookieManagerAdapter.this.mChromeCookieManager.preInitCookieStore();
          return;
        }
      }
    }).start();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void removeAllCookies(final android.webkit.ValueCallback<Boolean> paramValueCallback)
  {
    if (paramValueCallback == null) {
      paramValueCallback = null;
    } else {
      paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
      {
        public void onReceiveValue(Boolean paramAnonymousBoolean)
        {
          paramValueCallback.onReceiveValue(paramAnonymousBoolean);
        }
      };
    }
    removeAllCookies(paramValueCallback);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void removeSessionCookies(final android.webkit.ValueCallback<Boolean> paramValueCallback)
  {
    if (paramValueCallback == null) {
      paramValueCallback = null;
    } else {
      paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
      {
        public void onReceiveValue(Boolean paramAnonymousBoolean)
        {
          paramValueCallback.onReceiveValue(paramAnonymousBoolean);
        }
      };
    }
    removeSessionCookies(paramValueCallback);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setAcceptThirdPartyCookies(Object paramObject, boolean paramBoolean)
  {
    setAcceptThirdPartyCookies((TencentWebViewProxy.InnerWebView)paramObject, paramBoolean);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setCookie(m paramm, String paramString)
  {
    setCookie(paramm.toString(), paramString);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setCookie(String paramString1, String paramString2, final android.webkit.ValueCallback<Boolean> paramValueCallback)
  {
    if (paramValueCallback == null) {
      paramValueCallback = null;
    } else {
      paramValueCallback = new com.tencent.tbs.core.webkit.ValueCallback()
      {
        public void onReceiveValue(Boolean paramAnonymousBoolean)
        {
          paramValueCallback.onReceiveValue(paramAnonymousBoolean);
        }
      };
    }
    setCookie(paramString1, paramString2, paramValueCallback);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setCookies(HashMap<String, String[]> paramHashMap)
  {
    try
    {
      this.mChromeCookieManager.setCookies(paramHashMap);
      return;
    }
    catch (ParseException paramHashMap)
    {
      Log.e("CookieManager", "Not setting cookie due to error ..", new Object[] { paramHashMap });
    }
  }
  
  public void sync()
  {
    flush();
  }
  
  public void syncImmediately()
  {
    flush();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentCookieManagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */