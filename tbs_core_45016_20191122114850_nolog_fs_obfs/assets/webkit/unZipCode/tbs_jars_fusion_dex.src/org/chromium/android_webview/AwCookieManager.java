package org.chromium.android_webview;

import android.os.Handler;
import android.os.Looper;
import java.util.HashMap;
import org.chromium.base.Callback;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.library_loader.LibraryLoader;
import org.chromium.base.library_loader.ProcessInitException;

@JNINamespace("android_webview")
public final class AwCookieManager
{
  public AwCookieManager()
  {
    try
    {
      LibraryLoader.get(3).ensureInitialized();
      return;
    }
    catch (ProcessInitException localProcessInitException)
    {
      throw new RuntimeException("Error initializing WebView library", localProcessInitException);
    }
  }
  
  private static String a(String paramString1, String paramString2)
  {
    if (paramString1.matches("^.*(?i);[\\t ]*Domain[\\t ]*=.*$")) {
      return paramString1;
    }
    if (paramString1.matches("^.*;\\s*$"))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append(" Domain=");
      localStringBuilder.append(paramString2);
      return localStringBuilder.toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append("; Domain=");
    localStringBuilder.append(paramString2);
    return localStringBuilder.toString();
  }
  
  private static UrlValue a(String paramString1, String paramString2)
  {
    Object localObject = paramString1;
    String str = paramString2;
    if (paramString1.startsWith("http:///."))
    {
      str = paramString1.substring(8);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("http://");
      ((StringBuilder)localObject).append(paramString1.substring(9));
      localObject = ((StringBuilder)localObject).toString();
      str = a(paramString2, str);
    }
    return new UrlValue((String)localObject, str);
  }
  
  @CalledByNative
  public static void invokeBooleanCookieCallback(CookieCallback<Boolean> paramCookieCallback, boolean paramBoolean)
  {
    paramCookieCallback.onReceiveValue(Boolean.valueOf(paramBoolean));
  }
  
  private native boolean nativeAllowFileSchemeCookies();
  
  private native void nativeFlushCookieStore();
  
  private native String nativeGetCookie(String paramString);
  
  private native boolean nativeGetShouldAcceptCookies();
  
  private native boolean nativeHasCookies();
  
  private native void nativePreInitCookieStore();
  
  private native void nativeRemoveAllCookies(CookieCallback<Boolean> paramCookieCallback);
  
  private native void nativeRemoveAllCookiesSync();
  
  private native void nativeRemoveExpiredCookies();
  
  private native void nativeRemoveSessionCookies(CookieCallback<Boolean> paramCookieCallback);
  
  private native void nativeRemoveSessionCookiesSync();
  
  private native void nativeSetAcceptFileSchemeCookies(boolean paramBoolean);
  
  private native void nativeSetCookie(String paramString1, String paramString2, CookieCallback<Boolean> paramCookieCallback);
  
  private native void nativeSetCookieSync(String paramString1, String paramString2);
  
  private native void nativeSetCookiesSync(HashMap paramHashMap);
  
  private native void nativeSetShouldAcceptCookies(boolean paramBoolean);
  
  public boolean acceptCookie()
  {
    return nativeGetShouldAcceptCookies();
  }
  
  public boolean allowFileSchemeCookies()
  {
    return nativeAllowFileSchemeCookies();
  }
  
  public void flushCookieStore()
  {
    nativeFlushCookieStore();
  }
  
  public String getCookie(String paramString)
  {
    String str = nativeGetCookie(paramString.toString());
    if (str != null)
    {
      paramString = str;
      if (!str.trim().isEmpty()) {}
    }
    else
    {
      paramString = null;
    }
    return paramString;
  }
  
  public boolean hasCookies()
  {
    return nativeHasCookies();
  }
  
  public void preInitCookieStore()
  {
    nativePreInitCookieStore();
  }
  
  public void removeAllCookies()
  {
    nativeRemoveAllCookiesSync();
  }
  
  public void removeAllCookies(Callback<Boolean> paramCallback)
  {
    try
    {
      nativeRemoveAllCookies(CookieCallback.convert(paramCallback));
      return;
    }
    catch (IllegalStateException paramCallback)
    {
      for (;;) {}
    }
    throw new IllegalStateException("removeAllCookies must be called on a thread with a running Looper.");
  }
  
  public void removeExpiredCookies()
  {
    nativeRemoveExpiredCookies();
  }
  
  public void removeSessionCookies()
  {
    nativeRemoveSessionCookiesSync();
  }
  
  public void removeSessionCookies(Callback<Boolean> paramCallback)
  {
    try
    {
      nativeRemoveSessionCookies(CookieCallback.convert(paramCallback));
      return;
    }
    catch (IllegalStateException paramCallback)
    {
      for (;;) {}
    }
    throw new IllegalStateException("removeSessionCookies must be called on a thread with a running Looper.");
  }
  
  public void setAcceptCookie(boolean paramBoolean)
  {
    nativeSetShouldAcceptCookies(paramBoolean);
  }
  
  public void setAcceptFileSchemeCookies(boolean paramBoolean)
  {
    nativeSetAcceptFileSchemeCookies(paramBoolean);
  }
  
  public void setCookie(String paramString1, String paramString2)
  {
    paramString1 = a(paramString1, paramString2);
    nativeSetCookieSync(paramString1.mUrl, paramString1.mValue);
  }
  
  public void setCookie(String paramString1, String paramString2, Callback<Boolean> paramCallback)
  {
    try
    {
      paramString1 = a(paramString1, paramString2);
      nativeSetCookie(paramString1.mUrl, paramString1.mValue, CookieCallback.convert(paramCallback));
      return;
    }
    catch (IllegalStateException paramString1)
    {
      for (;;) {}
    }
    throw new IllegalStateException("SetCookie must be called on a thread with a running Looper.");
  }
  
  public void setCookies(HashMap<String, String[]> paramHashMap)
  {
    nativeSetCookiesSync(paramHashMap);
  }
  
  private static class CookieCallback<T>
  {
    Handler jdField_a_of_type_AndroidOsHandler;
    Callback<T> jdField_a_of_type_OrgChromiumBaseCallback;
    
    public CookieCallback(Callback<T> paramCallback, Handler paramHandler)
    {
      this.jdField_a_of_type_OrgChromiumBaseCallback = paramCallback;
      this.jdField_a_of_type_AndroidOsHandler = paramHandler;
    }
    
    public static <T> CookieCallback<T> convert(Callback<T> paramCallback)
      throws IllegalStateException
    {
      if (paramCallback == null) {
        return null;
      }
      if (Looper.myLooper() != null) {
        return new CookieCallback(paramCallback, new Handler());
      }
      throw new IllegalStateException("CookieCallback.convert should be called on a thread with a running Looper.");
    }
    
    public void onReceiveValue(final T paramT)
    {
      this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
      {
        public void run()
        {
          AwCookieManager.CookieCallback.this.a.onResult(paramT);
        }
      });
    }
  }
  
  private static class UrlValue
  {
    public String mUrl;
    public String mValue;
    
    public UrlValue(String paramString1, String paramString2)
    {
      this.mUrl = paramString1;
      this.mValue = paramString2;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwCookieManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */