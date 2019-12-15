package com.tencent.tbs.core.webkit;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WebViewFactory
{
  private static final long CHROMIUM_WEBVIEW_DEFAULT_VMSIZE_BYTES = 104857600L;
  protected static final String CHROMIUM_WEBVIEW_FACTORY = "com.android.webview.chromium.WebViewChromiumFactoryProvider";
  private static final String CHROMIUM_WEBVIEW_NATIVE_RELRO_32 = "/data/misc/shared_relro/libwebviewchromium32.relro";
  private static final String CHROMIUM_WEBVIEW_NATIVE_RELRO_64 = "/data/misc/shared_relro/libwebviewchromium64.relro";
  public static final String CHROMIUM_WEBVIEW_VMSIZE_SIZE_PROPERTY = "persist.sys.webview.vmsize";
  protected static final boolean DEBUG = false;
  public static final int LIBLOAD_ADDRESS_SPACE_NOT_RESERVED = 2;
  public static final int LIBLOAD_FAILED_JNI_CALL = 7;
  public static final int LIBLOAD_FAILED_LISTING_WEBVIEW_PACKAGES = 4;
  public static final int LIBLOAD_FAILED_TO_FIND_NAMESPACE = 10;
  public static final int LIBLOAD_FAILED_TO_LOAD_LIBRARY = 6;
  public static final int LIBLOAD_FAILED_TO_OPEN_RELRO_FILE = 5;
  public static final int LIBLOAD_FAILED_WAITING_FOR_RELRO = 3;
  public static final int LIBLOAD_FAILED_WAITING_FOR_WEBVIEW_REASON_UNKNOWN = 8;
  public static final int LIBLOAD_SUCCESS = 0;
  public static final int LIBLOAD_WRONG_PACKAGE_NAME = 1;
  protected static final String LOGTAG = "WebViewFactory";
  private static final String NULL_WEBVIEW_FACTORY = "com.android.webview.nullwebview.NullWebViewFactoryProvider";
  private static boolean sAddressSpaceReserved = false;
  protected static PackageInfo sPackageInfo;
  private static WebViewFactoryProvider sProviderInstance;
  private static final Object sProviderLock = new Object();
  
  public static PackageInfo getLoadedPackageInfo()
  {
    try
    {
      Object localObject = Class.forName("com.tencent.tbs.core.webkit.tencent.TencentWebViewFactory").getDeclaredMethod("getLoadedPackageInfo", new Class[0]);
      ((Method)localObject).setAccessible(true);
      localObject = (PackageInfo)((Method)localObject).invoke(null, new Object[0]);
      return (PackageInfo)localObject;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return null;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
      return null;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
      return null;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    return null;
  }
  
  static WebViewFactoryProvider getProvider()
  {
    try
    {
      Object localObject = Class.forName("com.tencent.tbs.core.webkit.tencent.TencentWebViewFactory").getDeclaredMethod("getProvider", new Class[0]);
      ((Method)localObject).setAccessible(true);
      localObject = (WebViewFactoryProvider)((Method)localObject).invoke(null, new Object[0]);
      return (WebViewFactoryProvider)localObject;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return null;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
      return null;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
      return null;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    return null;
  }
  
  public static String getWebViewLibrary(ApplicationInfo paramApplicationInfo)
  {
    if (paramApplicationInfo.metaData != null) {
      return paramApplicationInfo.metaData.getString("com.android.webview.WebViewLibrary");
    }
    return null;
  }
  
  private static String getWebViewPreparationErrorReason(int paramInt)
  {
    if (paramInt != 8)
    {
      switch (paramInt)
      {
      default: 
        return "Unknown";
      case 4: 
        return "No WebView installed";
      }
      return "Time out waiting for Relro files being created";
    }
    return "Crashed for unknown reason";
  }
  
  public static int loadWebViewNativeLibraryFromPackage(String paramString, ClassLoader paramClassLoader)
  {
    return 6;
  }
  
  public static class MissingWebViewPackageException
    extends AndroidRuntimeException
  {
    public MissingWebViewPackageException(Exception paramException)
    {
      super();
    }
    
    public MissingWebViewPackageException(String paramString)
    {
      super();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\WebViewFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */