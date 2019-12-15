package com.tencent.tbs.core.webkit.tencent;

import android.app.ActivityThread;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.webview.chromium.tencent.TencentWebViewChromiumFactoryProvider;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.tbs.core.webkit.WebViewFactory;
import com.tencent.tbs.core.webkit.WebViewFactoryProvider;
import dalvik.system.PathClassLoader;
import java.util.Map;
import org.chromium.tencent.SharedResource;

public class TencentWebViewFactory
  extends WebViewFactory
{
  private static final String CHROMIUM_WEBVIEW_JAR = "/system/framework/webviewchromium.jar";
  private static final String DEFAULT_WEBVIEW_FACTORY = "com.tencent.smtt.webkit.WebViewClassic$Factory";
  private static TencentWebViewFactoryProvider sProviderInstance;
  private static final Object sProviderLock = new Object();
  
  private static WebViewFactoryProvider getFactoryByName(String paramString, ClassLoader paramClassLoader)
  {
    try
    {
      paramClassLoader = (WebViewFactoryProvider)Class.forName(paramString, true, paramClassLoader).newInstance();
      return paramClassLoader;
    }
    catch (InstantiationException paramClassLoader)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("error loading ");
      localStringBuilder.append(paramString);
      Log.e("WebViewFactory", localStringBuilder.toString(), paramClassLoader);
    }
    catch (IllegalAccessException paramClassLoader)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("error loading ");
      localStringBuilder.append(paramString);
      Log.e("WebViewFactory", localStringBuilder.toString(), paramClassLoader);
    }
    catch (ClassNotFoundException paramClassLoader)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("error loading ");
      localStringBuilder.append(paramString);
      Log.e("WebViewFactory", localStringBuilder.toString(), paramClassLoader);
    }
    return null;
  }
  
  public static PackageInfo getLoadedPackageInfo()
  {
    if (sPackageInfo == null) {
      try
      {
        sPackageInfo = ContextHolder.getInstance().getApplicationContext().getPackageManager().getPackageInfo(ActivityThread.currentPackageName(), 0);
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        localNameNotFoundException.printStackTrace();
      }
    }
    return sPackageInfo;
  }
  
  public static TencentWebViewFactoryProvider getProvider()
  {
    synchronized (sProviderLock)
    {
      if (sProviderInstance != null)
      {
        localTencentWebViewFactoryProvider = sProviderInstance;
        return localTencentWebViewFactoryProvider;
      }
      if (sProviderInstance == null)
      {
        SharedResource.getPerformanceData().put("new_webviewchromiumfactoryprovider_begin", String.valueOf(System.currentTimeMillis()));
        sProviderInstance = new TencentWebViewChromiumFactoryProvider();
        SharedResource.getPerformanceData().put("new_webviewchromiumfactoryprovider_end", String.valueOf(System.currentTimeMillis()));
      }
      TencentWebViewFactoryProvider localTencentWebViewFactoryProvider = sProviderInstance;
      return localTencentWebViewFactoryProvider;
    }
  }
  
  private static WebViewFactoryProvider loadChromiumProvider()
  {
    return getFactoryByName("com.android.webview.chromium.WebViewChromiumFactoryProvider", new PathClassLoader("/system/framework/webviewchromium.jar", null, TencentWebViewFactory.class.getClassLoader()));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentWebViewFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */