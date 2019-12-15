package com.tencent.tbs.tbsshell;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.webkit.ValueCallback;
import com.tencent.common.http.Apn;
import com.tencent.common.http.MimeTypeMap;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.ReflectionUtils;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5DateSorter;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewClient;
import com.tencent.smtt.export.external.interfaces.IconListener;
import com.tencent.smtt.export.external.interfaces.UrlRequest.Callback;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.TbsWupManager;
import com.tencent.tbs.common.internal.service.IQBSmttService;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.stat.WupStatManager;
import com.tencent.tbs.common.utils.ApkPkgNameDetector;
import com.tencent.tbs.tbsshell.common.SmttServiceCommon;
import com.tencent.tbs.tbsshell.common.TbsLog;
import com.tencent.tbs.tbsshell.common.ad.TbsAdUserInfoCollector;
import com.tencent.tbs.tbsshell.common.utils.TbsInitPerformanceUtils;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

public class WebCoreProxy
{
  private static final String BRIDGE_CLASS_NAME = "com.tencent.smtt.webkit.X5JavaBridge";
  private static final String COMPAT_CLASS_NAME = "com.tencent.smtt.jscore.compat.X5JsCoreCompat";
  public static String TAG = "WebCoreProxy";
  private static Context mCallerAppContext;
  public static DexClassLoader mDexLoader;
  private static String mDexOutPutDir;
  private static IWebViewDexLoaderProvider mDexProvider;
  private static boolean mHasDoneWupTask = false;
  public static boolean mIsDynamicMode = false;
  private static String mTesInstallLocation;
  private static Context mX5CoreProvidAppContext;
  public static boolean mX5Used = false;
  private static String sBridgeClassName;
  
  public static void CheckTrim(int paramInt)
  {
    Object localObject = TAG;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("WebCoreProxy.java CheckTrim() level=");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).toString();
    if (canUseX5())
    {
      localObject = Integer.TYPE;
      invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.browser.x5.MemoryManager", "CheckTrim", new Class[] { localObject }, new Object[] { Integer.valueOf(paramInt) });
    }
  }
  
  public static void HTML5NotificationPresenter_exitCleanUp()
  {
    if (!canUseX5()) {
      return;
    }
    invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.HTML5NotificationPresenter", "exitCleanUp", null, new Object[0]);
  }
  
  public static void ScaleManager_destroy()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.ScaleManager", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "com.tencent.smtt.webkit.ScaleManager", "destroy", null, new Object[0]);
    }
  }
  
  public static Object TbsAd_getTbsUserInfo()
  {
    if (!canUseX5()) {
      return null;
    }
    return TbsAdUserInfoCollector.getInstance().getJsonObject();
  }
  
  public static Object UrlRequest_getX5UrlRequestProvider(String paramString1, int paramInt, UrlRequest.Callback paramCallback, Executor paramExecutor, boolean paramBoolean, String paramString2, ArrayList<Pair<String, String>> paramArrayList, String paramString3)
  {
    if (!canUseX5()) {
      return null;
    }
    return invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.net.X5UrlRequestProvider", "GetX5UrlRequestProvider", new Class[] { String.class, Integer.TYPE, UrlRequest.Callback.class, Executor.class, Boolean.TYPE, String.class, ArrayList.class, String.class }, new Object[] { paramString1, Integer.valueOf(paramInt), paramCallback, paramExecutor, Boolean.valueOf(paramBoolean), paramString2, paramArrayList, paramString3 });
  }
  
  public static String WebStorage_getH5FileSystemDir(String paramString, int paramInt)
  {
    return null;
  }
  
  public static Map<String, String> WebStorage_getLocalStroage(String paramString)
  {
    return null;
  }
  
  public static void addJavascriptInterface(Object paramObject1, String paramString, Object paramObject2)
  {
    if (!canUseX5())
    {
      paramObject1 = TAG;
      paramString = new StringBuilder();
      paramString.append("WebCoreProxy.addJavascriptInterface - canUseX5 = ");
      paramString.append(canUseX5());
      Log.e((String)paramObject1, paramString.toString());
      return;
    }
    if (paramObject2 != null) {
      invokeMethod(mIsDynamicMode, paramObject2, getX5JsCoreBridgeClassName(), "addJavascriptInterface", new Class[] { Object.class, String.class }, new Object[] { paramObject1, paramString });
    }
  }
  
  public static void appendDomain(URL paramURL)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.SMTTCookieManager", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "com.tencent.smtt.webkit.SMTTCookieManager", "appendDomain", new Class[] { URL.class }, new Object[] { paramURL });
    }
  }
  
  public static Object cacheDisabled()
  {
    if (!canUseX5()) {
      return null;
    }
    return invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager", "cacheDisabled", new Class[0], new Object[0]);
  }
  
  public static boolean canOpenFile(Context paramContext, String paramString)
  {
    return TBSShellFactory.getWebCoreProxyPartner().canOpenFile(paramContext, paramString);
  }
  
  public static boolean canUseX5()
  {
    boolean bool = mX5Used;
    if (bool) {
      return bool;
    }
    try
    {
      if (!mX5Used)
      {
        x5WebviewDexInit();
        DexClassLoader localDexClassLoader = mDexLoader;
        if (localDexClassLoader != null)
        {
          try
          {
            mX5Used = TBSShellFactory.getTbsShellDelegate().initX5CoreImpl(mDexLoader, mCallerAppContext, mX5CoreProvidAppContext, mTesInstallLocation);
          }
          catch (Throwable localThrowable)
          {
            Object localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("TBSShell.initX5Core ret: ");
            ((StringBuilder)localObject2).append(mX5Used);
            ((StringBuilder)localObject2).append("; exception: ");
            ((StringBuilder)localObject2).append(localThrowable);
            localObject2 = ((StringBuilder)localObject2).toString();
            Log.e(TAG, (String)localObject2, localThrowable);
            TBSShell.setLoadFailureDetails((String)localObject2);
          }
        }
        else
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("mDexLoader is null; mDexProvider: ");
          localStringBuilder.append(mDexProvider);
          TBSShell.setLoadFailureDetails(localStringBuilder.toString());
        }
      }
      if (!mX5Used) {
        TBSShellFactory.getWebCoreProxyPartner().onCannotUseX5();
      }
      long l = System.currentTimeMillis();
      TBSShellFactory.getTbsShellDelegate().onX5InitCallback(mX5Used);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onX5InitCallback cost: ");
      localStringBuilder.append(System.currentTimeMillis() - l);
      localStringBuilder.toString();
      bool = mX5Used;
      return bool;
    }
    finally {}
  }
  
  public static boolean canUseX5JsCore(Context paramContext)
  {
    PublicSettingManager localPublicSettingManager = PublicSettingManager.getInstance();
    boolean bool2 = false;
    if (localPublicSettingManager != null)
    {
      boolean bool1 = bool2;
      if (localPublicSettingManager.getCanUseX5Jscore())
      {
        bool1 = bool2;
        if (!isForceUseSysWebView(paramContext)) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  public static boolean canUseX5JsCoreNewAPI(Context paramContext)
  {
    PublicSettingManager localPublicSettingManager = PublicSettingManager.getInstance();
    boolean bool2 = false;
    if (localPublicSettingManager != null)
    {
      boolean bool1 = bool2;
      if (localPublicSettingManager.getCanUseX5Jscore())
      {
        bool1 = bool2;
        if (localPublicSettingManager.getCanUseX5JscoreNewAPI())
        {
          bool1 = bool2;
          if (!isForceUseSysWebView(paramContext)) {
            bool1 = true;
          }
        }
      }
      return bool1;
    }
    return false;
  }
  
  public static boolean canX5JsCoreUseBuffer(Context paramContext)
  {
    doWupTask();
    paramContext = PublicSettingManager.getInstance();
    if (paramContext != null) {
      return paramContext.getX5JsCoreBufferSwitch();
    }
    return false;
  }
  
  public static void clearAdWebviewInfo()
  {
    if (canUseX5())
    {
      Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.partner.ad.AdInfoHolder", "getInstance", null, new Object[0]);
      if (localObject != null) {
        invokeMethod(mIsDynamicMode, localObject, "com.tencent.tbs.core.partner.ad.AdInfoHolder", "clearAdInfo", null, new Object[0]);
      }
    }
  }
  
  public static void clearAllCache(Context paramContext)
  {
    if (!canUseX5()) {
      return;
    }
    webViewDatabaseClearUsernamePassword(paramContext);
    webViewDatabaseClearHttpAuthUsernamePassword(paramContext);
    webViewDatabaseClearFormData(paramContext);
    clearNetworkCache();
    invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager", "clearLocalStorage", null, new Object[0]);
    invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.CookieSyncManager", "createInstance", new Class[] { Context.class }, new Object[] { paramContext });
    cookieManager_removeAllCookie();
    cookieManager_removeExpiredCookie();
    paramContext = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.SmttPermanentPermissions", "getInstance", null, new Object[0]);
    if (paramContext != null) {
      invokeMethod(mIsDynamicMode, paramContext, "com.tencent.smtt.webkit.SmttPermanentPermissions", "clearAllPermanentPermission", null, new Object[0]);
    }
    removeAllIcons();
  }
  
  public static void clearAllCache(Context paramContext, boolean paramBoolean)
  {
    if (!canUseX5()) {
      return;
    }
    webViewDatabaseClearUsernamePassword(paramContext);
    webViewDatabaseClearHttpAuthUsernamePassword(paramContext);
    webViewDatabaseClearFormData(paramContext);
    clearNetworkCache();
    invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager", "clearLocalStorage", null, new Object[0]);
    if (paramBoolean)
    {
      invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.CookieSyncManager", "createInstance", new Class[] { Context.class }, new Object[] { paramContext });
      cookieManager_removeAllCookie();
      cookieManager_removeExpiredCookie();
    }
    paramContext = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.SmttPermanentPermissions", "getInstance", null, new Object[0]);
    if (paramContext != null) {
      invokeMethod(mIsDynamicMode, paramContext, "com.tencent.smtt.webkit.SmttPermanentPermissions", "clearAllPermanentPermission", null, new Object[0]);
    }
    removeAllIcons();
  }
  
  public static void clearAllPermanentPermission()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.SmttPermanentPermissions", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "com.tencent.smtt.webkit.SmttPermanentPermissions", "clearAllPermanentPermission", null, new Object[0]);
    }
  }
  
  public static void clearCache()
  {
    if (!canUseX5()) {
      return;
    }
    clearNetworkCache();
    invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager", "clearLocalStorage", null, new Object[0]);
    invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.gameengine.GameEngineProxy", "deleteAllCache", null, new Object[0]);
  }
  
  public static void clearCookie(Context paramContext)
  {
    if (!canUseX5()) {
      return;
    }
    invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.CookieSyncManager", "createInstance", new Class[] { Context.class }, new Object[] { paramContext });
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", new Class[] { Boolean.TYPE }, new Object[] { Boolean.valueOf(false) });
    if (localObject != null)
    {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "removeAllCookie", null, new Object[0]);
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "removeExpiredCookie", null, new Object[0]);
    }
    paramContext = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "getInstance", new Class[] { Context.class }, new Object[] { paramContext });
    if (paramContext != null) {
      invokeMethod(mIsDynamicMode, paramContext, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "clearFormData", null, new Object[0]);
    }
  }
  
  public static void clearDns() {}
  
  public static void clearFormData(Context paramContext)
  {
    if (!canUseX5()) {
      return;
    }
    paramContext = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "getInstance", new Class[] { Context.class }, new Object[] { paramContext });
    if (paramContext != null) {
      invokeMethod(mIsDynamicMode, paramContext, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "clearFormData", null, new Object[0]);
    }
  }
  
  public static void clearLocalStorage()
  {
    if (!canUseX5()) {
      return;
    }
    invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager", "clearLocalStorage", null, new Object[0]);
  }
  
  public static void clearNetworkCache()
  {
    if (!canUseX5()) {
      return;
    }
    invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager", "removeAllCacheFiles", null, new Object[0]);
  }
  
  public static void clearPasswords(Context paramContext)
  {
    if (!canUseX5()) {
      return;
    }
    paramContext = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "getInstance", new Class[] { Context.class }, new Object[] { paramContext });
    if (paramContext != null)
    {
      invokeMethod(mIsDynamicMode, paramContext, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "clearUsernamePassword", null, new Object[0]);
      invokeMethod(mIsDynamicMode, paramContext, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "clearHttpAuthUsernamePassword", null, new Object[0]);
    }
  }
  
  public static void closeFileReader()
  {
    TBSShellFactory.getWebCoreProxyPartner().closeFileReader();
  }
  
  public static void closeIconDB()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "close", null, new Object[0]);
    }
  }
  
  public static void closeMiniQB()
  {
    TBSShellFactory.getWebCoreProxyPartner().closeMiniQB();
  }
  
  public static boolean cookieManager_acceptCookie()
  {
    if (!canUseX5()) {
      return false;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null)
    {
      localObject = invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "acceptCookie", null, new Object[0]);
      if (localObject != null) {
        return ((Boolean)localObject).booleanValue();
      }
    }
    return false;
  }
  
  public static Boolean cookieManager_acceptThirdPartyCookies(Object paramObject)
  {
    if (!canUseX5()) {
      return Boolean.valueOf(false);
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null)
    {
      paramObject = invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "acceptThirdPartyCookies", new Class[] { Object.class }, new Object[] { paramObject });
      if (paramObject != null) {
        return (Boolean)paramObject;
      }
    }
    return Boolean.valueOf(false);
  }
  
  public static void cookieManager_flush()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "flush", new Class[0], new Object[0]);
    }
  }
  
  public static boolean cookieManager_hasCookies()
  {
    if (!canUseX5()) {
      return false;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null)
    {
      localObject = invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "hasCookies", null, new Object[0]);
      if (localObject != null) {
        return ((Boolean)localObject).booleanValue();
      }
    }
    return false;
  }
  
  public static void cookieManager_removeAllCookie()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "removeAllCookie", null, new Object[0]);
    }
  }
  
  public static void cookieManager_removeAllCookies(ValueCallback<Boolean> paramValueCallback)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "removeAllCookies", new Class[] { ValueCallback.class }, new Object[] { paramValueCallback });
    }
  }
  
  public static void cookieManager_removeExpiredCookie()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "removeExpiredCookie", null, new Object[0]);
    }
  }
  
  public static void cookieManager_removeSessionCookie()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "removeSessionCookie", null, new Object[0]);
    }
  }
  
  public static void cookieManager_removeSessionCookies(ValueCallback<Boolean> paramValueCallback)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "removeSessionCookies", new Class[] { ValueCallback.class }, new Object[] { paramValueCallback });
    }
  }
  
  public static void cookieManager_setAcceptCookie(boolean paramBoolean)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "setAcceptCookie", new Class[] { Boolean.TYPE }, new Object[] { Boolean.valueOf(paramBoolean) });
    }
  }
  
  public static void cookieManager_setAcceptThirdPartyCookies(Object paramObject, boolean paramBoolean)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "setAcceptThirdPartyCookies", new Class[] { Object.class, Boolean.TYPE }, new Object[] { paramObject, Boolean.valueOf(paramBoolean) });
    }
  }
  
  public static void cookieManager_setCookie(String paramString1, String paramString2)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "setCookie", new Class[] { String.class, String.class }, new Object[] { paramString1, paramString2 });
    }
  }
  
  public static void cookieManager_setCookie(String paramString1, String paramString2, ValueCallback<Boolean> paramValueCallback)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "setCookie", new Class[] { String.class, String.class, ValueCallback.class }, new Object[] { paramString1, paramString2, paramValueCallback });
    }
  }
  
  public static boolean cookieManager_setCookies(Map<String, String[]> paramMap)
  {
    if (!canUseX5()) {
      return false;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "setCookies", new Class[] { HashMap.class }, new Object[] { paramMap });
    }
    return true;
  }
  
  public static void cookieSyncManager_Sync()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.CookieSyncManager", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "com.tencent.tbs.core.webkit.CookieSyncManager", "sync", null, new Object[0]);
    }
  }
  
  public static void cookieSyncManager_createInstance(Context paramContext)
  {
    if (!canUseX5()) {
      return;
    }
    invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.CookieSyncManager", "createInstance", new Class[] { Context.class }, new Object[] { paramContext });
  }
  
  public static void cookieSyncManager_startSync()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.CookieSyncManager", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "com.tencent.tbs.core.webkit.CookieSyncManager", "startSync", null, new Object[0]);
    }
  }
  
  public static void cookieSyncManager_stopSync()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.CookieSyncManager", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "com.tencent.tbs.core.webkit.CookieSyncManager", "stopSync", null, new Object[0]);
    }
  }
  
  public static void createCacheWebView(Context paramContext)
  {
    TBSShellFactory.getWebCoreProxyPartner().createCacheWebView(paramContext);
  }
  
  public static IX5DateSorter createDateSorter(Context paramContext)
  {
    if (canUseX5()) {
      return (IX5DateSorter)newInstance("com.tencent.tbs.core.webkit.tencent.TencentDateSorter", new Class[] { Context.class }, new Object[] { paramContext });
    }
    return null;
  }
  
  public static IX5WebChromeClient createDefaultX5WebChromeClient()
  {
    if (canUseX5()) {
      return (IX5WebChromeClient)newInstance("com.tencent.tbs.core.webkit.tencent.TencentWebChromeClientProxy");
    }
    return null;
  }
  
  public static IX5WebViewClientExtension createDefaultX5WebChromeClientExtension()
  {
    if (canUseX5()) {
      return (IX5WebViewClientExtension)newInstance("com.tencent.smtt.webkit.WebViewClientExtension");
    }
    return null;
  }
  
  public static IX5WebViewClient createDefaultX5WebViewClient()
  {
    if (canUseX5()) {
      return (IX5WebViewClient)newInstance("com.tencent.tbs.core.webkit.tencent.TencentWebViewClientProxy");
    }
    return null;
  }
  
  public static Object createEaselView(Context paramContext)
  {
    if (canUseX5()) {
      return newInstance("com.tencent.smtt.aladdin.AladdinView", new Class[] { Context.class }, new Object[] { paramContext });
    }
    return null;
  }
  
  public static boolean createMiniQBShortCut(Context paramContext, String paramString1, String paramString2, Bitmap paramBitmap)
  {
    return TBSShellFactory.getWebCoreProxyPartner().createMiniQBShortCut(paramContext, paramString1, paramString2, paramBitmap);
  }
  
  public static IX5WebViewBase createSDKWebViewInstance(Context paramContext)
  {
    Object localObject = Boolean.TYPE;
    try
    {
      paramContext = mDexLoader.loadClass("com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter").getConstructor(new Class[] { Context.class, localObject }).newInstance(new Object[] { paramContext, Boolean.valueOf(false) });
    }
    catch (Throwable paramContext)
    {
      Log.e(TAG, "create 'com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter' instance failed: ", paramContext);
    }
    if ((paramContext instanceof IX5WebViewBase)) {
      return (IX5WebViewBase)paramContext;
    }
    if ((paramContext instanceof Throwable))
    {
      paramContext = (Throwable)paramContext;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("createSDKWebview failure - th: ");
      ((StringBuilder)localObject).append(paramContext);
      ((StringBuilder)localObject).append("; msg: ");
      ((StringBuilder)localObject).append(paramContext.getMessage());
      ((StringBuilder)localObject).append("; cause: ");
      ((StringBuilder)localObject).append(Log.getStackTraceString(paramContext.getCause()));
      paramContext = ((StringBuilder)localObject).toString();
      TBSShell.setLoadFailureDetails(paramContext);
      localObject = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("");
      localStringBuilder.append(paramContext);
      Log.e((String)localObject, localStringBuilder.toString());
      return null;
    }
    TBSShell.setLoadFailureDetails("X5WebViewAdapter newInstance null!");
    return null;
  }
  
  public static IX5WebViewBase createSDKWebview(Context paramContext)
  {
    try
    {
      paramContext = TBSShellFactory.getWebCoreProxyPartner().createSDKWebview(paramContext);
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("createSDKWebview Exception:");
      localStringBuilder.append(Log.getStackTraceString(paramContext));
      TbsLog.e("WebCoreProxy", localStringBuilder.toString());
      throw new RuntimeException(paramContext);
    }
  }
  
  public static Object createVideoSniffer(Context paramContext)
  {
    if (canUseX5()) {
      return newInstance("com.tencent.smtt.util.videosniffer.VideoSniffer", new Class[] { Context.class }, new Object[] { paramContext });
    }
    return null;
  }
  
  public static Object createX5JavaBridge(Context paramContext)
  {
    if (canUseX5())
    {
      TBSShellFactory.getSmttServiceCommon().userBehaviorStatistics("BDJC01");
      return newInstance(getX5JsCoreBridgeClassName(), new Class[] { Context.class }, new Object[] { paramContext });
    }
    return null;
  }
  
  public static Object createX5JsVirtualMachine(Context paramContext, Looper paramLooper)
  {
    if (canUseX5JsCore(paramContext))
    {
      TBSShellFactory.getSmttServiceCommon().userBehaviorStatistics("BDJC01");
      return newInstance("com.tencent.smtt.jscore.X5JsVirtualMachine", new Class[] { Context.class, Looper.class }, new Object[] { paramContext, paramLooper });
    }
    return null;
  }
  
  public static Bitmap createX5OptimizedBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig, boolean paramBoolean)
  {
    if (canUseX5())
    {
      Class localClass1 = Integer.TYPE;
      Class localClass2 = Integer.TYPE;
      Class localClass3 = Boolean.TYPE;
      return (Bitmap)invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.browser.x5.X5OptimizedBitmap", "createBitmap", new Class[] { localClass1, localClass2, Bitmap.Config.class, localClass3 }, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), paramConfig, Boolean.valueOf(paramBoolean) });
    }
    return Bitmap.createBitmap(paramInt1, paramInt2, paramConfig);
  }
  
  public static Object currentContextData()
  {
    return invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.jscore.X5JsContextImpl", "currentContextData", null, new Object[0]);
  }
  
  public static boolean deleteMiniQBShortCut(Context paramContext, String paramString1, String paramString2)
  {
    return false;
  }
  
  public static void destroyX5JsCore(Object paramObject)
  {
    if (!canUseX5())
    {
      paramObject = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("WebCoreProxy.destroyX5JsCore - canUseX5 = ");
      localStringBuilder.append(canUseX5());
      Log.e((String)paramObject, localStringBuilder.toString());
      return;
    }
    if (paramObject != null) {
      invokeMethod(mIsDynamicMode, paramObject, getX5JsCoreBridgeClassName(), "destroy", null, new Object[0]);
    }
  }
  
  public static void doSearch(Context paramContext, String paramString1, IX5WebView paramIX5WebView, String paramString2)
  {
    if (canUseX5()) {
      invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.partner.menu.SearchEngine", "doSearch", new Class[] { Context.class, String.class, IX5WebView.class, String.class }, new Object[] { paramContext, paramString1, paramIX5WebView, paramString2 });
    }
  }
  
  public static void doWupTask()
  {
    if (mHasDoneWupTask)
    {
      LogUtils.d(TAG, "has done wup task already, ignore");
      return;
    }
    LogUtils.d(TAG, "never done wup task, do it");
    mHasDoneWupTask = true;
    TbsWupManager.getInstance().doMultiWupRequest();
  }
  
  public static void evaluateJavascript(String paramString, ValueCallback<String> paramValueCallback, Object paramObject)
  {
    if (!canUseX5())
    {
      paramString = TAG;
      paramValueCallback = new StringBuilder();
      paramValueCallback.append("WebCoreProxy.addJavascriptInterface - canUseX5 = ");
      paramValueCallback.append(canUseX5());
      Log.e(paramString, paramValueCallback.toString());
      return;
    }
    if (paramObject != null) {
      invokeMethod(mIsDynamicMode, paramObject, getX5JsCoreBridgeClassName(), "evaluateJavaScript", new Class[] { String.class, ValueCallback.class }, new Object[] { paramString, paramValueCallback });
    }
  }
  
  public static void fileInfoDetect(Context paramContext, String paramString, ValueCallback<String> paramValueCallback)
  {
    ApkPkgNameDetector.getInstance(paramContext).addDetectorTask(paramString, paramValueCallback);
  }
  
  public static void generateTbsAdUserInfo(IX5WebView paramIX5WebView, Point paramPoint1, Point paramPoint2)
  {
    String str = TAG;
    TbsAdUserInfoCollector.getInstance().generateTbsAdUserInfo(paramIX5WebView, paramPoint1, paramPoint2);
  }
  
  public static void geolocationPermissionsAllow(String paramString)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentGeolocationPermissionsAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentGeolocationPermissionsAdapter", "allow", new Class[] { String.class }, new Object[] { paramString });
    }
  }
  
  public static void geolocationPermissionsClear(String paramString)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentGeolocationPermissionsAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentGeolocationPermissionsAdapter", "clear", new Class[] { String.class }, new Object[] { paramString });
    }
  }
  
  public static void geolocationPermissionsClearAll()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentGeolocationPermissionsAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentGeolocationPermissionsAdapter", "clearAll", null, new Object[0]);
    }
  }
  
  public static void geolocationPermissionsGetAllowed(String paramString, ValueCallback<Boolean> paramValueCallback)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentGeolocationPermissionsAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentGeolocationPermissionsAdapter", "getAllowed", new Class[] { String.class, ValueCallback.class }, new Object[] { paramString, paramValueCallback });
    }
  }
  
  public static void geolocationPermissionsGetOrigins(ValueCallback<Set<String>> paramValueCallback)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentGeolocationPermissionsAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentGeolocationPermissionsAdapter", "getOrigins", new Class[] { ValueCallback.class }, new Object[] { paramValueCallback });
    }
  }
  
  public static Bundle getAdWebViewInfoFromX5Core()
  {
    if (!canUseX5()) {
      return null;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.WebViewList", "getAdWebViewInfo", new Class[0], new Object[0]);
    if (localObject != null) {
      return (Bundle)localObject;
    }
    return null;
  }
  
  public static Object getCachFileBaseDir()
  {
    if (!canUseX5()) {
      return null;
    }
    return invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager", "getCacheFileBaseDir", new Class[0], new Object[0]);
  }
  
  public static Object getCacheFile(String paramString)
  {
    return getCacheFile(paramString, true);
  }
  
  public static Object getCacheFile(String paramString, boolean paramBoolean)
  {
    if (!canUseX5()) {
      return null;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager", "getCacheFile", new Class[] { String.class, Map.class }, new Object[] { paramString, null });
    boolean bool = ((Boolean)invokeMethod(mIsDynamicMode, paramString, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager$CacheResult", "isNeedToRedirect", null, new Object[0])).booleanValue();
    String str = (String)invokeMethod(mIsDynamicMode, paramString, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager$CacheResult", "getLocation", null, new Object[0]);
    if (bool) {
      paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager", "getCacheFile", new Class[] { String.class, Map.class }, new Object[] { str, null });
    }
    if (paramString != null)
    {
      bool = ((Boolean)invokeMethod(mIsDynamicMode, paramString, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager$CacheResult", "isExpired", null, new Object[0])).booleanValue();
      if ((paramBoolean) || (!bool)) {
        return invokeMethod(mIsDynamicMode, paramString, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager$CacheResult", "getInputStream", null, new Object[0]);
      }
    }
    return null;
  }
  
  public static String getCanonicalUrl(String paramString)
  {
    if (!canUseX5()) {
      return paramString;
    }
    return (String)invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.net.AwNetworkUtils", "getCanonicalUrl", new Class[] { String.class }, new Object[] { paramString });
  }
  
  private static File getConfigFile(Context paramContext)
  {
    try
    {
      paramContext = new File(paramContext.getDir("tbs", 0), "core_private");
      if (!paramContext.isDirectory()) {
        return null;
      }
      paramContext = new File(paramContext, "debug.conf");
      boolean bool = paramContext.exists();
      if (bool) {
        return paramContext;
      }
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public static String getCookie(String paramString)
  {
    if (!canUseX5()) {
      return null;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.SMTTCookieManager", "getInstance", null, new Object[0]);
    if (localObject != null) {
      return (String)invokeMethod(mIsDynamicMode, localObject, "com.tencent.smtt.webkit.SMTTCookieManager", "getCookie", new Class[] { String.class }, new Object[] { paramString });
    }
    return null;
  }
  
  public static String getCookie(String paramString, boolean paramBoolean)
  {
    if (!canUseX5()) {
      return null;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.SMTTCookieManager", "getInstance", new Class[] { Boolean.TYPE }, new Object[] { Boolean.valueOf(paramBoolean) });
    if (localObject != null) {
      return (String)invokeMethod(mIsDynamicMode, localObject, "com.tencent.smtt.webkit.SMTTCookieManager", "getCookie", new Class[] { String.class }, new Object[] { paramString });
    }
    return null;
  }
  
  public static String getCoreExtraMessage()
  {
    if (!canUseX5()) {
      return null;
    }
    return (String)invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.net.AwNetworkUtils", "getCoreExtraMessage", null, new Object[0]);
  }
  
  public static String getCrashExtraMessage()
  {
    if (!canUseX5()) {
      return null;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.util.CrashTracker", "getCrashExtraInfo", new Class[0], new Object[0]);
    if (localObject == null) {
      return null;
    }
    return String.valueOf(localObject);
  }
  
  public static String getDefaultUserAgent(Context paramContext)
  {
    if (canUseX5())
    {
      paramContext = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.WebSettings", "getDefaultUserAgent", new Class[] { Context.class }, new Object[] { paramContext });
      if ((paramContext instanceof String)) {
        return (String)paramContext;
      }
    }
    return null;
  }
  
  public static String getGUID()
  {
    return GUIDFactory.getInstance().getStrGuid();
  }
  
  public static Bitmap getIconForPageUrl(String paramString)
  {
    if (!canUseX5()) {
      return null;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      return (Bitmap)invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "getIconForPageUrl", new Class[] { String.class }, new Object[] { paramString });
    }
    return null;
  }
  
  public static Object getInputStream(String paramString)
  {
    return getCacheFile(paramString);
  }
  
  public static Object getLocalPath(String paramString)
  {
    if (!canUseX5()) {
      return null;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager", "getCacheFile", new Class[] { String.class, Map.class }, new Object[] { paramString, null });
    if (paramString != null) {
      return invokeMethod(mIsDynamicMode, paramString, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager$CacheResult", "getLocalPath", null, new Object[0]);
    }
    return null;
  }
  
  public static String getMiniQBVersion()
  {
    return TBSShellFactory.getWebCoreProxyPartner().getMiniQBVersion();
  }
  
  public static ByteBuffer getNativeBuffer(Object paramObject, int paramInt)
  {
    if (!canUseX5())
    {
      paramObject = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("WebCoreProxy.destroyX5JsCore - canUseX5 = ");
      localStringBuilder.append(canUseX5());
      Log.e((String)paramObject, localStringBuilder.toString());
      return null;
    }
    if (paramObject != null)
    {
      paramObject = invokeMethod(mIsDynamicMode, paramObject, getX5JsCoreBridgeClassName(), "getNativeBuffer", new Class[] { Integer.TYPE }, new Object[] { Integer.valueOf(paramInt) });
      if (paramObject != null) {
        return (ByteBuffer)paramObject;
      }
    }
    return null;
  }
  
  public static int getNativeBufferId(Object paramObject)
  {
    if (!canUseX5())
    {
      paramObject = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("WebCoreProxy.getNativeBufferId - canUseX5 = ");
      localStringBuilder.append(canUseX5());
      Log.e((String)paramObject, localStringBuilder.toString());
      return -1;
    }
    if (paramObject != null)
    {
      paramObject = invokeMethod(mIsDynamicMode, paramObject, getX5JsCoreBridgeClassName(), "getNativeBufferId", null, new Object[0]);
      if (paramObject == null) {}
    }
    try
    {
      int i = Integer.parseInt(paramObject.toString());
      return i;
    }
    catch (NumberFormatException paramObject) {}
    return -1;
    return -1;
  }
  
  public static String getQCookie(String paramString)
  {
    if (!canUseX5()) {
      return null;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.SMTTCookieManager", "getInstance", null, new Object[0]);
    if (localObject != null) {
      return (String)invokeMethod(mIsDynamicMode, localObject, "com.tencent.smtt.webkit.SMTTCookieManager", "getQCookie", new Class[] { String.class }, new Object[] { paramString });
    }
    return null;
  }
  
  public static Object getStaticField(boolean paramBoolean, String paramString1, String paramString2)
  {
    if (paramBoolean)
    {
      if (canUseX5()) {
        return ReflectionUtils.getStaticField(paramBoolean, mDexLoader, paramString1, paramString2);
      }
      return null;
    }
    return ReflectionUtils.getStaticField(paramBoolean, mDexLoader, paramString1, paramString2);
  }
  
  private static String getX5JsCoreBridgeClassName()
  {
    if (sBridgeClassName == null)
    {
      Object localObject = PublicSettingManager.getInstance();
      if ((localObject != null) && (!((PublicSettingManager)localObject).getX5JsCoreUseDeprecated())) {
        sBridgeClassName = "com.tencent.smtt.jscore.compat.X5JsCoreCompat";
      } else {
        sBridgeClassName = "com.tencent.smtt.webkit.X5JavaBridge";
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("use class ");
      ((StringBuilder)localObject).append(sBridgeClassName);
      Log.e("X5JsCore", ((StringBuilder)localObject).toString());
    }
    sBridgeClassName = "com.tencent.smtt.webkit.X5JavaBridge";
    return sBridgeClassName;
  }
  
  public static boolean getX5RenderMemoryDebug()
  {
    return false;
  }
  
  public static boolean getX5RenderPerformDebug()
  {
    if (!canUseX5()) {
      return false;
    }
    Object localObject = getStaticField(mIsDynamicMode, "android.webview.chromium.tencent.TencentContentSettingsAdapter", "X5RenderPerformDebug");
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = (Boolean)localObject;
    }
    return ((Boolean)localObject).booleanValue();
  }
  
  public static boolean getX5RenderTileDebug()
  {
    return false;
  }
  
  public static void init(IWebViewDexLoaderProvider paramIWebViewDexLoaderProvider, boolean paramBoolean, Context paramContext1, Context paramContext2, String paramString1, String paramString2)
  {
    mDexProvider = paramIWebViewDexLoaderProvider;
    mIsDynamicMode = paramBoolean;
    mCallerAppContext = paramContext1;
    mX5CoreProvidAppContext = paramContext2;
    mTesInstallLocation = paramString1;
    mDexOutPutDir = paramString2;
  }
  
  public static void init(DexClassLoader paramDexClassLoader, boolean paramBoolean1, boolean paramBoolean2)
  {
    mDexLoader = paramDexClassLoader;
    mX5Used = paramBoolean1;
    mIsDynamicMode = paramBoolean2;
  }
  
  public static boolean installLocalQbApk(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    return TBSShellFactory.getWebCoreProxyPartner().installLocalQbApk(paramContext, paramString1, paramString2, paramBundle);
  }
  
  public static Object invokeMethod(boolean paramBoolean, Object paramObject, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (paramBoolean)
    {
      if (canUseX5()) {
        return ReflectionUtils.invokeMethod(paramBoolean, mDexLoader, paramObject, paramString1, paramString2, paramArrayOfClass, paramVarArgs);
      }
      return null;
    }
    return ReflectionUtils.invokeMethod(paramBoolean, mDexLoader, paramObject, paramString1, paramString2, paramArrayOfClass, paramVarArgs);
  }
  
  public static Object invokeStaticMethod(boolean paramBoolean, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (paramBoolean)
    {
      if (canUseX5()) {
        return ReflectionUtils.invokeStaticMethod(paramBoolean, mDexLoader, paramString1, paramString2, paramArrayOfClass, paramVarArgs);
      }
      return null;
    }
    return ReflectionUtils.invokeStaticMethod(paramBoolean, mDexLoader, paramString1, paramString2, paramArrayOfClass, paramVarArgs);
  }
  
  public static boolean isAllowQHead()
  {
    return false;
  }
  
  /* Error */
  private static boolean isForceUseSysWebView(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 771	com/tencent/tbs/tbsshell/WebCoreProxy:getConfigFile	(Landroid/content/Context;)Ljava/io/File;
    //   4: astore_0
    //   5: aload_0
    //   6: ifnonnull +5 -> 11
    //   9: iconst_0
    //   10: ireturn
    //   11: new 773	java/io/BufferedInputStream
    //   14: dup
    //   15: new 775	java/io/FileInputStream
    //   18: dup
    //   19: aload_0
    //   20: invokespecial 778	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   23: invokespecial 781	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   26: astore_2
    //   27: aload_2
    //   28: astore_0
    //   29: new 783	java/util/Properties
    //   32: dup
    //   33: invokespecial 784	java/util/Properties:<init>	()V
    //   36: astore_3
    //   37: aload_2
    //   38: astore_0
    //   39: aload_3
    //   40: aload_2
    //   41: invokevirtual 787	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   44: aload_2
    //   45: astore_0
    //   46: aload_3
    //   47: ldc_w 789
    //   50: ldc_w 481
    //   53: invokevirtual 793	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   56: astore_3
    //   57: aload_2
    //   58: astore_0
    //   59: ldc_w 481
    //   62: aload_3
    //   63: invokevirtual 797	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   66: ifne +23 -> 89
    //   69: aload_2
    //   70: astore_0
    //   71: aload_3
    //   72: invokestatic 801	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
    //   75: istore_1
    //   76: aload_2
    //   77: invokevirtual 803	java/io/BufferedInputStream:close	()V
    //   80: iload_1
    //   81: ireturn
    //   82: astore_0
    //   83: aload_0
    //   84: invokevirtual 804	java/lang/Exception:printStackTrace	()V
    //   87: iload_1
    //   88: ireturn
    //   89: aload_2
    //   90: invokevirtual 803	java/io/BufferedInputStream:close	()V
    //   93: iconst_0
    //   94: ireturn
    //   95: astore_3
    //   96: goto +12 -> 108
    //   99: astore_0
    //   100: aconst_null
    //   101: astore_2
    //   102: goto +34 -> 136
    //   105: astore_3
    //   106: aconst_null
    //   107: astore_2
    //   108: aload_2
    //   109: astore_0
    //   110: aload_3
    //   111: invokevirtual 668	java/lang/Throwable:printStackTrace	()V
    //   114: aload_2
    //   115: ifnull +14 -> 129
    //   118: aload_2
    //   119: invokevirtual 803	java/io/BufferedInputStream:close	()V
    //   122: iconst_0
    //   123: ireturn
    //   124: astore_0
    //   125: aload_0
    //   126: invokevirtual 804	java/lang/Exception:printStackTrace	()V
    //   129: iconst_0
    //   130: ireturn
    //   131: astore_3
    //   132: aload_0
    //   133: astore_2
    //   134: aload_3
    //   135: astore_0
    //   136: aload_2
    //   137: ifnull +15 -> 152
    //   140: aload_2
    //   141: invokevirtual 803	java/io/BufferedInputStream:close	()V
    //   144: goto +8 -> 152
    //   147: astore_2
    //   148: aload_2
    //   149: invokevirtual 804	java/lang/Exception:printStackTrace	()V
    //   152: aload_0
    //   153: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	154	0	paramContext	Context
    //   75	13	1	bool	boolean
    //   26	115	2	localObject1	Object
    //   147	2	2	localException	Exception
    //   36	36	3	localObject2	Object
    //   95	1	3	localThrowable1	Throwable
    //   105	6	3	localThrowable2	Throwable
    //   131	4	3	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   76	80	82	java/lang/Exception
    //   29	37	95	java/lang/Throwable
    //   39	44	95	java/lang/Throwable
    //   46	57	95	java/lang/Throwable
    //   59	69	95	java/lang/Throwable
    //   71	76	95	java/lang/Throwable
    //   0	5	99	finally
    //   11	27	99	finally
    //   0	5	105	java/lang/Throwable
    //   11	27	105	java/lang/Throwable
    //   89	93	124	java/lang/Exception
    //   118	122	124	java/lang/Exception
    //   29	37	131	finally
    //   39	44	131	finally
    //   46	57	131	finally
    //   59	69	131	finally
    //   71	76	131	finally
    //   110	114	131	finally
    //   140	144	147	java/lang/Exception
  }
  
  public static boolean isHostInTbsAdAppInstallCheckWhitelist(String paramString)
  {
    Object localObject = TAG;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("isHostInTbsAdAppInstallCheckWhitelist host = ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).toString();
    return TBSShellFactory.getSmttServiceCommon().isHostInTbsAdAppInstallCheckWhitelist(paramString);
  }
  
  public static boolean isHostNameInSPAAdPageReportWhiteList(String paramString)
  {
    Object localObject = TAG;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("isHostNameInSPAAdPageReportWhiteList host = ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).toString();
    return TBSShellFactory.getSmttServiceCommon().isHostNameInSPAAdPageReportWhiteList(paramString);
  }
  
  public static boolean isMiniQBShortCutExist(Context paramContext, String paramString)
  {
    return TBSShellFactory.getWebCoreProxyPartner().isMiniQBShortCutExist(paramContext, paramString);
  }
  
  public static boolean isUploadingWebCoreLog2Server()
  {
    return !canUseX5();
  }
  
  public static boolean isWritingWebCoreLogToFile()
  {
    if (!canUseX5()) {
      return true;
    }
    return ((Boolean)invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.util.X5LogManager", "isOpenX5log", null, new Object[0])).booleanValue();
  }
  
  public static boolean isX5ShowMemValueEnabled()
  {
    return false;
  }
  
  public static void liveLog(String paramString) {}
  
  public static Class<?> loadClass(String paramString)
  {
    if (canUseX5()) {
      return ReflectionUtils.loadClass(true, mDexLoader, paramString);
    }
    return null;
  }
  
  public static String mimeTypeMapGetExtensionFromMimeType(String paramString)
  {
    if (!canUseX5()) {
      return null;
    }
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(paramString);
  }
  
  public static String mimeTypeMapGetFileExtensionFromUrl(String paramString)
  {
    if (!canUseX5()) {
      return null;
    }
    return MimeTypeMap.getFileExtensionFromUrl(paramString);
  }
  
  public static String mimeTypeMapGetMimeTypeFromExtension(String paramString)
  {
    if (!canUseX5()) {
      return null;
    }
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(paramString);
  }
  
  public static boolean mimeTypeMapHasExtension(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    return MimeTypeMap.getSingleton().hasExtension(paramString);
  }
  
  public static boolean mimeTypeMapHasMimeType(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    return MimeTypeMap.getSingleton().hasMimeType(paramString);
  }
  
  public static Object newInstance(String paramString)
  {
    if (canUseX5()) {
      return ReflectionUtils.newInstance(mDexLoader, paramString);
    }
    return null;
  }
  
  private static Object newInstance(String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (canUseX5()) {
      return ReflectionUtils.newInstance(mDexLoader, paramString, paramArrayOfClass, paramVarArgs);
    }
    return null;
  }
  
  public static void openIconDB(String paramString)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "open", new Class[] { String.class }, new Object[] { paramString });
    }
  }
  
  public static Uri[] parseFileChooserResult(int paramInt, Intent paramIntent)
  {
    if (!canUseX5()) {
      return null;
    }
    paramIntent = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.adapter.FileChooserParamsAdapter", "parseFileChooserResult", new Class[] { Integer.TYPE, Intent.class }, new Object[] { Integer.valueOf(paramInt), paramIntent });
    if (paramIntent == null) {
      return null;
    }
    return (Uri[])paramIntent;
  }
  
  public static void pause(Object paramObject)
  {
    if (!canUseX5())
    {
      paramObject = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("WebCoreProxy.pause - canUseX5 = ");
      localStringBuilder.append(canUseX5());
      Log.e((String)paramObject, localStringBuilder.toString());
      return;
    }
    if (paramObject != null) {
      invokeMethod(mIsDynamicMode, paramObject, getX5JsCoreBridgeClassName(), "pause", null, new Object[0]);
    }
  }
  
  public static void pauseTimers(Object paramObject)
  {
    if (!canUseX5())
    {
      paramObject = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("WebCoreProxy.pauseTimers - canUseX5 = ");
      localStringBuilder.append(canUseX5());
      Log.e((String)paramObject, localStringBuilder.toString());
      return;
    }
    if (paramObject != null) {
      invokeMethod(mIsDynamicMode, paramObject, getX5JsCoreBridgeClassName(), "pauseTimers", null, new Object[0]);
    }
  }
  
  public static boolean pinBitmap(Bitmap paramBitmap, boolean paramBoolean)
  {
    if (canUseX5())
    {
      Class localClass = Boolean.TYPE;
      return ((Boolean)invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.browser.x5.X5OptimizedBitmap", "pin", new Class[] { Bitmap.class, localClass }, new Object[] { paramBitmap, Boolean.valueOf(paramBoolean) })).booleanValue();
    }
    return true;
  }
  
  public static void preConnect(Context paramContext, String paramString, int paramInt)
  {
    if (!canUseX5()) {
      return;
    }
    if ((paramString != null) && (paramInt > 0)) {
      invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.net.AwNetworkUtils", "setPreConnect", new Class[] { String.class, Integer.TYPE }, new Object[] { paramString, Integer.valueOf(paramInt) });
    }
  }
  
  public static void preConnect(boolean paramBoolean, String paramString)
  {
    if ((canUseX5()) && (paramString != null)) {
      invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.net.AwNetworkUtils", "setPreConnect", new Class[] { String.class, Integer.TYPE }, new Object[] { paramString, Integer.valueOf(1) });
    }
  }
  
  public static void prefetchResource(Context paramContext, String paramString, Map<String, String> paramMap)
  {
    TBSShellFactory.getWebCoreProxyPartner().prefetchResource(paramContext, paramString, paramMap, mIsDynamicMode);
  }
  
  public static void pvUploadNotifybyUI()
  {
    if (!canUseX5()) {
      return;
    }
    TBSStatManager.getInstance().upload();
    WupStatManager.getInstance().saveAllAndUpload();
  }
  
  public static void refreshJavaCoreApnState()
  {
    if (!canUseX5()) {
      return;
    }
    Apn.getApnType();
  }
  
  public static void refreshPlugins(Context paramContext, boolean paramBoolean)
  {
    if (!canUseX5()) {
      return;
    }
    paramContext = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.PluginManager", "getInstance", new Class[] { Context.class }, new Object[] { paramContext });
    if (paramContext != null) {
      invokeMethod(mIsDynamicMode, paramContext, "com.tencent.smtt.webkit.PluginManager", "refreshPlugins", new Class[] { Boolean.TYPE }, new Object[] { Boolean.valueOf(paramBoolean) });
    }
  }
  
  public static void releaseIconForPageUrl(String paramString)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "releaseIconForPageUrl", new Class[] { String.class }, new Object[] { paramString });
    }
  }
  
  public static void removeAllIcons()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "removeAllIcons", null, new Object[0]);
    }
  }
  
  public static void removeJavascriptInterface(String paramString, Object paramObject)
  {
    if (!canUseX5())
    {
      paramString = TAG;
      paramObject = new StringBuilder();
      ((StringBuilder)paramObject).append("WebCoreProxy.addJavascriptInterface - canUseX5 = ");
      ((StringBuilder)paramObject).append(canUseX5());
      Log.e(paramString, ((StringBuilder)paramObject).toString());
      return;
    }
    if (paramObject != null) {
      invokeMethod(mIsDynamicMode, paramObject, getX5JsCoreBridgeClassName(), "removeJavascriptInterface", new Class[] { String.class }, new Object[] { paramString });
    }
  }
  
  public static void requestIconForPageUrl(String paramString, IconListener paramIconListener)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "requestIconForPageUrl", new Class[] { String.class, IconListener.class }, new Object[] { paramString, paramIconListener });
    }
  }
  
  public static void resetSpdySession()
  {
    if (!canUseX5()) {
      return;
    }
    invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.net.AwNetworkUtils", "closeAllSpdySessions", null, new Object[0]);
  }
  
  public static void resume(Object paramObject)
  {
    if (!canUseX5())
    {
      paramObject = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("WebCoreProxy.resume - canUseX5 = ");
      localStringBuilder.append(canUseX5());
      Log.e((String)paramObject, localStringBuilder.toString());
      return;
    }
    if (paramObject != null) {
      invokeMethod(mIsDynamicMode, paramObject, getX5JsCoreBridgeClassName(), "resume", null, new Object[0]);
    }
  }
  
  public static void resumeTimers(Object paramObject)
  {
    if (!canUseX5())
    {
      paramObject = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("WebCoreProxy.resumeTimers - canUseX5 = ");
      localStringBuilder.append(canUseX5());
      Log.e((String)paramObject, localStringBuilder.toString());
      return;
    }
    if (paramObject != null) {
      invokeMethod(mIsDynamicMode, paramObject, getX5JsCoreBridgeClassName(), "resumeTimers", null, new Object[0]);
    }
  }
  
  public static void retainIconForPageUrl(String paramString)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebIconDatabaseAdapter", "retainIconForPageUrl", new Class[] { String.class }, new Object[] { paramString });
    }
  }
  
  public static void setAdWebviewInfo(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4, int paramInt3, int paramInt4)
  {
    if (canUseX5())
    {
      Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.partner.ad.AdInfoHolder", "getInstance", null, new Object[0]);
      if (localObject != null) {
        invokeMethod(mIsDynamicMode, localObject, "com.tencent.tbs.core.partner.ad.AdInfoHolder", "setAdInfoUnit", new Class[] { Integer.TYPE, String.class, String.class, String.class, Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE }, new Object[] { Integer.valueOf(paramInt1), paramString1, paramString2, paramString3, Integer.valueOf(paramInt2), paramString4, Integer.valueOf(paramInt3), Integer.valueOf(paramInt4) });
      }
    }
  }
  
  public static void setAdWebviewShouldShow(boolean paramBoolean)
  {
    if (canUseX5())
    {
      Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.partner.ad.AdInfoHolder", "getInstance", null, new Object[0]);
      if (localObject != null) {
        invokeMethod(mIsDynamicMode, localObject, "com.tencent.tbs.core.partner.ad.AdInfoHolder", "setShouldShow", new Class[] { Boolean.TYPE }, new Object[] { Boolean.valueOf(paramBoolean) });
      }
    }
  }
  
  public static void setAllowQHead(boolean paramBoolean) {}
  
  public static void setCookie(URL paramURL, Map<String, List<String>> paramMap)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.SMTTCookieManager", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "com.tencent.smtt.webkit.SMTTCookieManager", "setCookie", new Class[] { URL.class, Map.class }, new Object[] { paramURL, paramMap });
    }
  }
  
  public static void setJsValueFactory(Object paramObject)
  {
    invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.jscore.X5JsValue", "setJsValueFactory", new Class[] { Object.class }, new Object[] { paramObject });
  }
  
  public static void setNativeBuffer(Object paramObject, int paramInt, ByteBuffer paramByteBuffer)
  {
    if (!canUseX5())
    {
      paramObject = TAG;
      paramByteBuffer = new StringBuilder();
      paramByteBuffer.append("WebCoreProxy.destroyX5JsCore - canUseX5 = ");
      paramByteBuffer.append(canUseX5());
      Log.e((String)paramObject, paramByteBuffer.toString());
      return;
    }
    if (paramObject != null) {
      invokeMethod(mIsDynamicMode, paramObject, getX5JsCoreBridgeClassName(), "setNativeBuffer", new Class[] { Integer.TYPE, ByteBuffer.class }, new Object[] { Integer.valueOf(paramInt), paramByteBuffer });
    }
  }
  
  public static void setQBSmttService(Object paramObject)
  {
    if (!canUseX5()) {
      return;
    }
    boolean bool = mIsDynamicMode;
    paramObject = (IQBSmttService)paramObject;
    invokeStaticMethod(bool, "com.tencent.tbs.common.service.QBSmttServiceProxy", "setLocalSmttService", new Class[] { IQBSmttService.class }, new Object[] { paramObject });
  }
  
  public static void setQCookie(String paramString1, String paramString2)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.SMTTCookieManager", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "com.tencent.smtt.webkit.SMTTCookieManager", "setQCookie", new Class[] { String.class, String.class }, new Object[] { paramString1, paramString2 });
    }
  }
  
  public static void setStaticBooleanField(String paramString1, String paramString2, boolean paramBoolean)
  {
    try
    {
      Object localObject = loadClass(paramString1);
      if (localObject != null)
      {
        localObject = ((Class)localObject).getField(paramString2);
        if (localObject != null)
        {
          ((Field)localObject).setAccessible(true);
          ((Field)localObject).setBoolean(null, paramBoolean);
          return;
        }
      }
    }
    catch (Exception localException)
    {
      String str = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("'");
      localStringBuilder.append(paramString1);
      localStringBuilder.append("' set field '");
      localStringBuilder.append(paramString2);
      localStringBuilder.append("' failed");
      Log.e(str, localStringBuilder.toString(), localException);
    }
  }
  
  public static void setTbsInitPerformanceData(int paramInt, Map<String, String> paramMap)
  {
    try
    {
      if ((canUseX5()) && (mCallerAppContext != null) && (paramMap != null) && (!paramMap.isEmpty()))
      {
        TbsInitPerformanceUtils.doReport(paramInt, paramMap, mIsDynamicMode, mCallerAppContext);
        return;
      }
      return;
    }
    finally {}
  }
  
  public static void setWebCoreLogWrite2FileFlag(boolean paramBoolean)
  {
    if (!canUseX5()) {
      return;
    }
    invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.util.X5LogManager", "setLogWrite2FileFlag", new Class[] { Boolean.TYPE, String.class }, new Object[] { Boolean.valueOf(paramBoolean), "qbiconclick" });
  }
  
  public static void setWebViewPoolSize(Context paramContext, int paramInt)
  {
    TBSShellFactory.getWebCoreProxyPartner().setWebViewPoolSize(paramContext, paramInt);
  }
  
  public static void setX5RenderMemoryDebug(boolean paramBoolean) {}
  
  public static void setX5RenderPerformDebug(boolean paramBoolean)
  {
    if (!canUseX5()) {
      return;
    }
    setStaticBooleanField("android.webview.chromium.tencent.TencentContentSettingsAdapter", "X5RenderPerformDebug", paramBoolean);
  }
  
  public static void setX5RenderTileDebug(boolean paramBoolean) {}
  
  public static void setX5ShowMemValueEnabled(boolean paramBoolean) {}
  
  public static int startMiniQB(Context paramContext, String paramString)
  {
    return startQBWeb(paramContext, paramString, null, null);
  }
  
  public static int startMiniQB(Context paramContext, String paramString1, String paramString2)
  {
    return startQBWeb(paramContext, paramString1, paramString2, null);
  }
  
  public static int startMiniQB(Context paramContext, String paramString, Map<String, String> paramMap)
  {
    return startQBWeb(paramContext, paramString, null, paramMap);
  }
  
  public static int startMiniQB(Context paramContext, String paramString, Map<String, String> paramMap, ValueCallback<String> paramValueCallback)
  {
    return TBSShellFactory.getWebCoreProxyPartner().startMiniQB(paramContext, paramString, paramMap, paramValueCallback);
  }
  
  public static int startQBWeb(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap)
  {
    return TBSShellFactory.getWebCoreProxyPartner().startQBWeb(paramContext, paramString1, paramString2, paramMap);
  }
  
  public static void switchContext(IX5WebViewBase paramIX5WebViewBase, Context paramContext)
  {
    ReflectionUtils.invokeMethod(true, mDexLoader, paramIX5WebViewBase, "com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter", "switchContext", new Class[] { Context.class }, new Object[] { paramContext });
  }
  
  public static void syncImmediately()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.webkit.SMTTCookieManager", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "com.tencent.smtt.webkit.SMTTCookieManager", "syncImmediately", null, new Object[0]);
    }
  }
  
  public static void traceBegin(int paramInt, String paramString)
  {
    if (!canUseX5()) {
      return;
    }
    invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.util.MttTraceEvent", "begin", new Class[] { Integer.TYPE, String.class }, new Object[] { Integer.valueOf(paramInt), paramString });
  }
  
  public static void traceEnd(int paramInt, String paramString)
  {
    if (!canUseX5()) {
      return;
    }
    invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.util.MttTraceEvent", "end", new Class[] { Integer.TYPE, String.class }, new Object[] { Integer.valueOf(paramInt), paramString });
  }
  
  public static void unpinBitmap(Bitmap paramBitmap)
  {
    if (canUseX5()) {
      invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.browser.x5.X5OptimizedBitmap", "unpin", new Class[] { Bitmap.class }, new Object[] { paramBitmap });
    }
  }
  
  public static void uploadWebCoreLog2Server()
  {
    if (!canUseX5()) {
      return;
    }
    invokeStaticMethod(mIsDynamicMode, "com.tencent.smtt.util.X5LogManager", "uploadX5LogFilesToServer", null, new Object[0]);
  }
  
  public static String urlUtilComposeSearchUrl(String paramString1, String paramString2, String paramString3)
  {
    if (!canUseX5()) {
      return null;
    }
    paramString1 = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "composeSearchUrl", new Class[] { String.class, String.class, String.class }, new Object[] { paramString1, paramString2, paramString3 });
    if (paramString1 != null) {
      return (String)paramString1;
    }
    return null;
  }
  
  public static byte[] urlUtilDecode(byte[] paramArrayOfByte)
  {
    if (!canUseX5()) {
      return null;
    }
    paramArrayOfByte = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "decode", new Class[] { String.class }, new Object[] { paramArrayOfByte });
    if (paramArrayOfByte != null) {
      return (byte[])paramArrayOfByte;
    }
    return null;
  }
  
  public static String urlUtilGuessFileName(String paramString1, String paramString2, String paramString3)
  {
    if (!canUseX5()) {
      return null;
    }
    paramString1 = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "guessFileName", new Class[] { String.class, String.class, String.class }, new Object[] { paramString1, paramString2, paramString3 });
    if (paramString1 != null) {
      return (String)paramString1;
    }
    return null;
  }
  
  public static String urlUtilGuessUrl(String paramString)
  {
    if (!canUseX5()) {
      return null;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "guessUrl", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return (String)paramString;
    }
    return null;
  }
  
  public static boolean urlUtilIsAboutUrl(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "isAboutUrl", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return ((Boolean)paramString).booleanValue();
    }
    return false;
  }
  
  public static boolean urlUtilIsAssetUrl(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "isAssetUrl", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return ((Boolean)paramString).booleanValue();
    }
    return false;
  }
  
  public static boolean urlUtilIsContentUrl(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "isContentUrl", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return ((Boolean)paramString).booleanValue();
    }
    return false;
  }
  
  public static boolean urlUtilIsCookielessProxyUrl(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "isCookielessProxyUrl", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return ((Boolean)paramString).booleanValue();
    }
    return false;
  }
  
  public static boolean urlUtilIsDataUrl(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "isDataUrl", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return ((Boolean)paramString).booleanValue();
    }
    return false;
  }
  
  public static boolean urlUtilIsFileUrl(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "isFileUrl", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return ((Boolean)paramString).booleanValue();
    }
    return false;
  }
  
  public static boolean urlUtilIsHttpUrl(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "isHttpUrl", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return ((Boolean)paramString).booleanValue();
    }
    return false;
  }
  
  public static boolean urlUtilIsHttpsUrl(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "isHttpsUrl", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return ((Boolean)paramString).booleanValue();
    }
    return false;
  }
  
  public static boolean urlUtilIsJavaScriptUrl(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "isJavaScriptUrl", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return ((Boolean)paramString).booleanValue();
    }
    return false;
  }
  
  public static boolean urlUtilIsNetworkUrl(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "isNetworkUrl", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return ((Boolean)paramString).booleanValue();
    }
    return false;
  }
  
  public static boolean urlUtilIsValidUrl(String paramString)
  {
    if (!canUseX5()) {
      return false;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "isValidUrl", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return ((Boolean)paramString).booleanValue();
    }
    return false;
  }
  
  public static String urlUtilStripAnchor(String paramString)
  {
    if (!canUseX5()) {
      return null;
    }
    paramString = invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentURLUtil", "stripAnchor", new Class[] { String.class }, new Object[] { paramString });
    if (paramString != null) {
      return (String)paramString;
    }
    return null;
  }
  
  public static void webStorageDeleteAllData()
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebStorageAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebStorageAdapter", "deleteAllData", null, new Object[0]);
    }
  }
  
  public static void webStorageDeleteOrigin(String paramString)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebStorageAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebStorageAdapter", "deleteOrigin", new Class[] { String.class }, new Object[] { paramString });
    }
  }
  
  public static void webStorageGetOrigins(ValueCallback<Map> paramValueCallback)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebStorageAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebStorageAdapter", "getOrigins", new Class[] { ValueCallback.class }, new Object[] { paramValueCallback });
    }
  }
  
  public static void webStorageGetQuotaForOrigin(String paramString, ValueCallback<Long> paramValueCallback)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebStorageAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebStorageAdapter", "getQuotaForOrigin", new Class[] { String.class, ValueCallback.class }, new Object[] { paramString, paramValueCallback });
    }
  }
  
  public static void webStorageGetUsageForOrigin(String paramString, ValueCallback<Long> paramValueCallback)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebStorageAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebStorageAdapter", "getUsageForOrigin", new Class[] { String.class, ValueCallback.class }, new Object[] { paramString, paramValueCallback });
    }
  }
  
  public static void webStorageSetQuotaForOrigin(String paramString, long paramLong)
  {
    if (!canUseX5()) {
      return;
    }
    Object localObject = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebStorageAdapter", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(mIsDynamicMode, localObject, "android.webview.chromium.tencent.TencentWebStorageAdapter", "setQuotaForOrigin", new Class[] { String.class, Long.TYPE }, new Object[] { paramString, Long.valueOf(paramLong) });
    }
  }
  
  public static void webViewDatabaseClearFormData(Context paramContext)
  {
    if (!canUseX5()) {
      return;
    }
    paramContext = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "getInstance", new Class[] { Context.class }, new Object[] { paramContext });
    if (paramContext != null) {
      invokeMethod(mIsDynamicMode, paramContext, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "clearFormData", null, new Object[0]);
    }
  }
  
  public static void webViewDatabaseClearHttpAuthUsernamePassword(Context paramContext)
  {
    if (!canUseX5()) {
      return;
    }
    paramContext = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "getInstance", new Class[] { Context.class }, new Object[] { paramContext });
    if (paramContext != null) {
      invokeMethod(mIsDynamicMode, paramContext, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "clearHttpAuthUsernamePassword", null, new Object[0]);
    }
  }
  
  public static void webViewDatabaseClearUsernamePassword(Context paramContext)
  {
    if (!canUseX5()) {
      return;
    }
    paramContext = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "getInstance", new Class[] { Context.class }, new Object[] { paramContext });
    if (paramContext != null) {
      invokeMethod(mIsDynamicMode, paramContext, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "clearUsernamePassword", null, new Object[0]);
    }
  }
  
  public static boolean webViewDatabaseHasFormData(Context paramContext)
  {
    if (!canUseX5()) {
      return false;
    }
    paramContext = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "getInstance", new Class[] { Context.class }, new Object[] { paramContext });
    if (paramContext != null) {
      return ((Boolean)invokeMethod(mIsDynamicMode, paramContext, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "hasFormData", null, new Object[0])).booleanValue();
    }
    return false;
  }
  
  public static boolean webViewDatabaseHasHttpAuthUsernamePassword(Context paramContext)
  {
    if (!canUseX5()) {
      return false;
    }
    paramContext = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "getInstance", new Class[] { Context.class }, new Object[] { paramContext });
    if (paramContext != null) {
      return ((Boolean)invokeMethod(mIsDynamicMode, paramContext, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "hasHttpAuthUsernamePassword", null, new Object[0])).booleanValue();
    }
    return false;
  }
  
  public static boolean webViewDatabaseHasUsernamePassword(Context paramContext)
  {
    if (!canUseX5()) {
      return false;
    }
    paramContext = invokeStaticMethod(mIsDynamicMode, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "getInstance", new Class[] { Context.class }, new Object[] { paramContext });
    if (paramContext != null) {
      return ((Boolean)invokeMethod(mIsDynamicMode, paramContext, "android.webview.chromium.tencent.TencentWebViewDatabaseAdapter", "hasUsernamePassword", null, new Object[0])).booleanValue();
    }
    return false;
  }
  
  public static void webview_setWebContentsDebuggingEnabled(boolean paramBoolean)
  {
    if (!canUseX5()) {
      return;
    }
    invokeStaticMethod(mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "setWebContentsDebuggingEnabled", new Class[] { Boolean.TYPE }, new Object[] { Boolean.valueOf(paramBoolean) });
  }
  
  public static DexClassLoader x5WebviewDexInit()
  {
    try
    {
      if ((mDexLoader == null) && (mDexProvider != null)) {
        mDexLoader = mDexProvider.getDexClassLoader();
      }
      DexClassLoader localDexClassLoader = mDexLoader;
      return localDexClassLoader;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\WebCoreProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */