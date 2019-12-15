package android.webview.chromium;

import android.net.ParseException;
import android.net.WebAddress;
import com.tencent.tbs.core.webkit.CookieManager;
import com.tencent.tbs.core.webkit.ValueCallback;
import com.tencent.tbs.core.webkit.WebSettings;
import com.tencent.tbs.core.webkit.WebView;
import org.chromium.android_webview.AwCookieManager;
import org.chromium.base.Log;
import org.chromium.base.annotations.UsedByReflection;

public class CookieManagerAdapter
  extends CookieManager
{
  private static final String TAG = "CookieManager";
  AwCookieManager mChromeCookieManager;
  
  public CookieManagerAdapter(AwCookieManager paramAwCookieManager)
  {
    this.mChromeCookieManager = paramAwCookieManager;
  }
  
  private static String fixupUrl(String paramString)
    throws ParseException
  {
    return new WebAddress(paramString).toString();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public boolean acceptCookie()
  {
    try
    {
      boolean bool = this.mChromeCookieManager.acceptCookie();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean acceptThirdPartyCookies(WebView paramWebView)
  {
    try
    {
      boolean bool = paramWebView.getSettings().getAcceptThirdPartyCookies();
      return bool;
    }
    finally
    {
      paramWebView = finally;
      throw paramWebView;
    }
  }
  
  protected boolean allowFileSchemeCookiesImpl()
  {
    return this.mChromeCookieManager.allowFileSchemeCookies();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void flush()
  {
    this.mChromeCookieManager.flushCookieStore();
  }
  
  public String getCookie(WebAddress paramWebAddress)
  {
    try
    {
      paramWebAddress = this.mChromeCookieManager.getCookie(paramWebAddress.toString());
      return paramWebAddress;
    }
    finally
    {
      paramWebAddress = finally;
      throw paramWebAddress;
    }
  }
  
  public String getCookie(String paramString)
  {
    try
    {
      String str = this.mChromeCookieManager.getCookie(fixupUrl(paramString));
      return str;
    }
    catch (ParseException localParseException)
    {
      Log.e("CookieManager", "Unable to get cookies due to error parsing URL: %s", new Object[] { paramString, localParseException });
    }
    return null;
  }
  
  public String getCookie(String paramString, boolean paramBoolean)
  {
    return getCookie(paramString);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public boolean hasCookies()
  {
    try
    {
      boolean bool = this.mChromeCookieManager.hasCookies();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public boolean hasCookies(boolean paramBoolean)
  {
    try
    {
      paramBoolean = this.mChromeCookieManager.hasCookies();
      return paramBoolean;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void removeAllCookie()
  {
    this.mChromeCookieManager.removeAllCookies();
  }
  
  public void removeAllCookies(ValueCallback<Boolean> paramValueCallback)
  {
    this.mChromeCookieManager.removeAllCookies(CallbackConverter.fromValueCallback(paramValueCallback));
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void removeExpiredCookie()
  {
    this.mChromeCookieManager.removeExpiredCookies();
  }
  
  public void removeSessionCookie()
  {
    this.mChromeCookieManager.removeSessionCookies();
  }
  
  public void removeSessionCookies(ValueCallback<Boolean> paramValueCallback)
  {
    this.mChromeCookieManager.removeSessionCookies(CallbackConverter.fromValueCallback(paramValueCallback));
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setAcceptCookie(boolean paramBoolean)
  {
    try
    {
      this.mChromeCookieManager.setAcceptCookie(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  protected void setAcceptFileSchemeCookiesImpl(boolean paramBoolean)
  {
    this.mChromeCookieManager.setAcceptFileSchemeCookies(paramBoolean);
  }
  
  public void setAcceptThirdPartyCookies(WebView paramWebView, boolean paramBoolean)
  {
    try
    {
      paramWebView.getSettings().setAcceptThirdPartyCookies(paramBoolean);
      return;
    }
    finally
    {
      paramWebView = finally;
      throw paramWebView;
    }
  }
  
  public void setCookie(String paramString1, String paramString2)
  {
    if (paramString2 == null)
    {
      Log.e("CookieManager", "Not setting cookie with null value for URL: %s", new Object[] { paramString1 });
      return;
    }
    try
    {
      this.mChromeCookieManager.setCookie(fixupUrl(paramString1), paramString2);
      return;
    }
    catch (ParseException paramString2)
    {
      Log.e("CookieManager", "Not setting cookie due to error parsing URL: %s", new Object[] { paramString1, paramString2 });
    }
  }
  
  public void setCookie(String paramString1, String paramString2, ValueCallback<Boolean> paramValueCallback)
  {
    if (paramString2 == null)
    {
      Log.e("CookieManager", "Not setting cookie with null value for URL: %s", new Object[] { paramString1 });
      return;
    }
    try
    {
      this.mChromeCookieManager.setCookie(fixupUrl(paramString1), paramString2, CallbackConverter.fromValueCallback(paramValueCallback));
      return;
    }
    catch (ParseException paramString2)
    {
      Log.e("CookieManager", "Not setting cookie due to error parsing URL: %s", new Object[] { paramString1, paramString2 });
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\CookieManagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */