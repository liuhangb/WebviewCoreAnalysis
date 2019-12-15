package com.tencent.tbs.tbsshell.common.x5core;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.http.Apn;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.ReflectionUtils;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.tbsshell.TBSShell;
import com.tencent.tbs.tbsshell.TBSShellDelegate;
import com.tencent.tbs.tbsshell.TBSShellFactory;
import com.tencent.tbs.tbsshell.common.ISmttServiceCommon;
import com.tencent.tbs.tbsshell.common.ISmttServiceMtt;
import com.tencent.tbs.tbsshell.common.ISmttServicePartner;
import dalvik.system.DexClassLoader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;

public class X5CoreInit
{
  private static final String PRE_LOAD_IMPL_CLASS = "com.tencent.tbs.core.Preprocessor";
  private static final String WEBVIEW_IMPL_CLASS = "com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter";
  private static final String X5CORE_CONTEXTHOLDER_CLASSNAME = "com.tencent.smtt.webkit.ContextHolder";
  private static boolean isPreInited = false;
  private boolean isAdInfoDbInited = false;
  private boolean isCacheInited = false;
  private boolean isLiveLogInited = false;
  private boolean isSmttResourceContextUpdated = false;
  private Object mContextHolder = null;
  private DexClassLoader mDexLoader = null;
  protected boolean mIsDynamicMode = false;
  private String mTbsResourcesPath = null;
  protected boolean mX5Used = true;
  
  public X5CoreInit(DexClassLoader paramDexClassLoader, boolean paramBoolean1, boolean paramBoolean2, String paramString)
  {
    this.mDexLoader = paramDexClassLoader;
    this.mX5Used = paramBoolean1;
    this.mIsDynamicMode = paramBoolean2;
    this.mTbsResourcesPath = paramString;
  }
  
  private Object getContextHolder()
  {
    if (this.mContextHolder == null)
    {
      this.mContextHolder = invokeStaticMethod(this.mIsDynamicMode, "com.tencent.smtt.webkit.ContextHolder", "getInstance", null, new Object[0]);
      if (this.mContextHolder == null) {
        TBSShell.setLoadFailureDetails("invoke static method returned null");
      }
    }
    return this.mContextHolder;
  }
  
  private Object invokeMethod(boolean paramBoolean, Object paramObject, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (paramBoolean)
    {
      DexClassLoader localDexClassLoader = this.mDexLoader;
      if (localDexClassLoader != null) {
        return ReflectionUtils.invokeMethod(paramBoolean, localDexClassLoader, paramObject, paramString1, paramString2, paramArrayOfClass, paramVarArgs);
      }
      return null;
    }
    return ReflectionUtils.invokeMethod(paramBoolean, this.mDexLoader, paramObject, paramString1, paramString2, paramArrayOfClass, paramVarArgs);
  }
  
  private Object invokeStaticMethod(boolean paramBoolean, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (paramBoolean)
    {
      DexClassLoader localDexClassLoader = this.mDexLoader;
      if (localDexClassLoader != null) {
        return ReflectionUtils.invokeStaticMethod(paramBoolean, localDexClassLoader, paramString1, paramString2, paramArrayOfClass, paramVarArgs);
      }
      TBSShell.setLoadFailureDetails("invokeStaticMethod,mDexLoader=null!!!");
      return null;
    }
    return ReflectionUtils.invokeStaticMethod(paramBoolean, this.mDexLoader, paramString1, paramString2, paramArrayOfClass, paramVarArgs);
  }
  
  private void preloadCommonShellClasses()
  {
    try
    {
      Class.forName("com.tencent.tbs.common.resources.ResourcesImpl");
      Class.forName("com.tencent.common.plugin.QBPluginProxy");
      Class.forName("com.tencent.tbs.common.baseinfo.TbsWupManager");
      return;
    }
    catch (Throwable localThrowable)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("***ATTENTION*** preloadShellClasses failed: ");
      localStringBuilder.append(localThrowable.getMessage());
      Log.e("performance", localStringBuilder.toString());
    }
  }
  
  private void preloadMttClasses() {}
  
  private void preloadPartnerClasses()
  {
    try
    {
      Class.forName("com.tencent.tbs.common.download.qb.QBDownloadManager");
      Class.forName("com.tencent.tbs.tbsshell.partner.QBPushManager");
      Class.forName("com.tencent.common.plugin.IQBPluginService$Stub");
      Class.forName("com.tencent.common.plugin.QBPluginServiceImpl");
      Class.forName("com.tencent.tbs.common.beacon.X5CoreBeaconUploader");
      return;
    }
    catch (Throwable localThrowable)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("***ATTENTION*** preloadPartnerClasses failed: ");
      localStringBuilder.append(localThrowable.getMessage());
      Log.e("performance", localStringBuilder.toString());
    }
  }
  
  private void preloadShellClasses()
  {
    preloadCommonShellClasses();
    preloadPartnerClasses();
  }
  
  public void SmttResource_UpdateContext(Context paramContext, String paramString)
  {
    try
    {
      boolean bool = this.isSmttResourceContextUpdated;
      if (bool) {
        return;
      }
      bool = this.mX5Used;
      if (!bool) {
        return;
      }
      invokeStaticMethod(this.mIsDynamicMode, "com.tencent.smtt.webkit.SmttResource", "updateContext", new Class[] { Context.class, String.class }, new Object[] { paramContext, paramString });
      this.isSmttResourceContextUpdated = true;
      return;
    }
    finally {}
  }
  
  public void connectivityChangedSoRefreshJavaCoreApnState()
  {
    if (!this.mX5Used) {
      return;
    }
    Apn.getApnType();
  }
  
  public void initAdInfoDb(Context paramContext)
  {
    if (this.isAdInfoDbInited) {
      return;
    }
    if (!this.mX5Used) {
      return;
    }
    try
    {
      Object localObject = invokeStaticMethod(this.mIsDynamicMode, "com.tencent.smtt.webkit.AdInfoManager", "getInstance", null, new Object[0]);
      if (localObject != null) {
        invokeMethod(this.mIsDynamicMode, localObject, "com.tencent.smtt.webkit.AdInfoManager", "initAdManager", new Class[] { Context.class }, new Object[] { paramContext });
      }
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    this.isAdInfoDbInited = true;
  }
  
  public void initCacheManager(Context paramContext)
  {
    if (this.isCacheInited) {
      return;
    }
    if (!this.mX5Used) {
      return;
    }
    try
    {
      invokeStaticMethod(this.mIsDynamicMode, "com.tencent.tbs.core.webkit.tencent.TencentCacheManager", "init", new Class[] { Context.class }, new Object[] { paramContext });
      this.isCacheInited = true;
      return;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
  }
  
  public void initCookieModule(Context paramContext)
  {
    if (!this.mX5Used) {
      return;
    }
    paramContext = invokeStaticMethod(this.mIsDynamicMode, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "getInstance", new Class[] { Boolean.TYPE }, new Object[] { Boolean.valueOf(false) });
    if (paramContext != null) {
      invokeMethod(this.mIsDynamicMode, paramContext, "android.webview.chromium.tencent.TencentCookieManagerAdapter", "preInitCookieStore", null, new Object[0]);
    }
  }
  
  public void initLiveLog(Context paramContext)
  {
    if (this.isLiveLogInited) {
      return;
    }
    if (!this.mX5Used) {
      return;
    }
    try
    {
      Object localObject = invokeStaticMethod(this.mIsDynamicMode, "com.tencent.smtt.livelog.LiveLog", "getInstance", null, new Object[0]);
      if (localObject != null) {
        invokeMethod(this.mIsDynamicMode, localObject, "com.tencent.smtt.livelog.LiveLog", "init", new Class[] { Context.class }, new Object[] { paramContext });
      }
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    this.isLiveLogInited = true;
  }
  
  public void preInit(final Context paramContext1, final Context paramContext2, final String paramString)
  {
    if (isPreInited)
    {
      paramContext1 = Thread.currentThread();
      paramContext2 = new StringBuilder();
      paramContext2.append("X5CoreInit.preinit - isPreInited, thread: ");
      paramContext2.append(paramContext1);
      paramContext2.append("; priority:");
      paramContext2.append(paramContext1.getPriority());
      paramContext2.toString();
      return;
    }
    isPreInited = true;
    paramContext1 = new Thread(new Runnable()
    {
      public void run()
      {
        X5CoreInit.this.preInitImpl(paramContext1, paramContext2, paramString);
      }
    });
    paramContext1.setPriority(10);
    paramContext1.setName("preinit1");
    paramContext2 = new StringBuilder();
    paramContext2.append("X5CoreInit.preinit - preloadClass in a separate thread: ");
    paramContext2.append(paramContext1);
    paramContext2.append("; priority:");
    paramContext2.append(paramContext1.getPriority());
    paramContext2.toString();
    paramContext1.start();
  }
  
  public void preInit0(Context paramContext1, Context paramContext2, String paramString)
  {
    paramContext1 = Thread.currentThread();
    paramContext2 = new StringBuilder();
    paramContext2.append("X5CoreInit.preinit0 - preloadClass0 in a separate thread: ");
    paramContext2.append(paramContext1);
    paramContext2.append("; priority:");
    paramContext2.append(paramContext1.getPriority());
    paramContext2.toString();
    invokeStaticMethod(this.mIsDynamicMode, "com.tencent.tbs.core.Preprocessor", "preinit0", null, new Object[0]);
  }
  
  public void preInit2()
  {
    Thread localThread = Thread.currentThread();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("X5CoreInit.preInit2 - preloadClass in a separate thread: ");
    localStringBuilder.append(localThread);
    localStringBuilder.append("; priority:");
    localStringBuilder.append(localThread.getPriority());
    localStringBuilder.toString();
    invokeStaticMethod(this.mIsDynamicMode, "com.tencent.tbs.core.Preprocessor", "preinitClassForX5WebView", new Class[] { Boolean.TYPE }, new Object[] { Boolean.valueOf(true) });
  }
  
  public void preInitImpl(Context paramContext1, Context paramContext2, String paramString)
  {
    try
    {
      this.mDexLoader.loadClass("com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter");
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    invokeStaticMethod(this.mIsDynamicMode, "com.tencent.tbs.core.Preprocessor", "preinit", null, new Object[0]);
    TBSShellFactory.getTbsShellDelegate().initTbsEnvAsync(paramContext1, paramContext2, paramString);
  }
  
  public void preInitWithLoadSo(Context paramContext)
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        X5CoreInit localX5CoreInit = X5CoreInit.this;
        localX5CoreInit.invokeStaticMethod(localX5CoreInit.mIsDynamicMode, "com.tencent.tbs.core.Preprocessor", "preinitClassWithSo", null, new Object[0]);
      }
    });
  }
  
  public void preInitWithResources(Context paramContext)
  {
    TBSResources.initTbsResourcesAsync();
    SmttResource_UpdateContext(TBSResources.getResourceContext(), this.mTbsResourcesPath);
    preloadShellClasses();
  }
  
  public Object[] setContextHolderParams(Context paramContext1, Context paramContext2, String paramString1, String paramString2, boolean paramBoolean, Map<String, Object> paramMap)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setContextHolderParams - mX5Used: ");
    localStringBuilder.append(this.mX5Used);
    localStringBuilder.append(", mIsDynamicMode: ");
    localStringBuilder.append(this.mIsDynamicMode);
    localStringBuilder.append(", dexloader: ");
    localStringBuilder.append(this.mDexLoader);
    localStringBuilder.toString();
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Boolean.valueOf(false);
    localStringBuilder = null;
    arrayOfObject[1] = null;
    if (!this.mX5Used)
    {
      arrayOfObject[1] = new Throwable("mX5Used is false!");
      return arrayOfObject;
    }
    if ((this.mIsDynamicMode) && (!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)))
    {
      paramContext1 = new Object[] { paramContext1, paramContext2, paramString1, paramString2, Boolean.valueOf(paramBoolean), this.mDexLoader, paramMap };
      paramContext2 = new Class[] { Context.class, Context.class, String.class, String.class, Boolean.class, DexClassLoader.class, Map.class };
    }
    else
    {
      paramString2 = new StringBuilder();
      paramString2.append("setContextHolderParams NOT dynamic mode - mIsDynamicMode: ");
      paramString2.append(this.mIsDynamicMode);
      paramString2.append(", dynamicLibFolderPath: ");
      paramString2.append(paramString1);
      paramString1 = paramString2.toString();
      TBSShellFactory.getTbsShellDelegate().setLoadFailureDetails(paramString1);
      paramContext1 = new Object[] { paramContext1, paramContext2 };
      paramContext2 = new Class[] { Context.class, Context.class };
    }
    paramString1 = getContextHolder();
    if (paramString1 != null)
    {
      paramBoolean = this.mIsDynamicMode;
      if (paramBoolean) {
        try
        {
          paramContext2 = this.mDexLoader.loadClass("com.tencent.smtt.webkit.ContextHolder").getMethod("setContext", paramContext2);
          paramContext2.setAccessible(true);
          paramContext1 = paramContext2.invoke(paramString1, paramContext1);
        }
        catch (Throwable paramContext1)
        {
          arrayOfObject[1] = paramContext1;
          paramContext1 = localStringBuilder;
        }
      } else {
        paramContext1 = invokeMethod(paramBoolean, paramString1, "com.tencent.smtt.webkit.ContextHolder", "setContext", paramContext2, paramContext1);
      }
      if ((paramContext1 instanceof Boolean)) {
        paramBoolean = ((Boolean)paramContext1).booleanValue();
      } else {
        paramBoolean = false;
      }
    }
    else
    {
      arrayOfObject[1] = new Throwable("contextHolder is null!");
      paramBoolean = false;
    }
    arrayOfObject[0] = Boolean.valueOf(paramBoolean);
    return arrayOfObject;
  }
  
  public void setContextHolderPluginDevelopMode(boolean paramBoolean)
  {
    if (!this.mX5Used) {
      return;
    }
    Object localObject = getContextHolder();
    if (localObject != null) {
      invokeMethod(this.mIsDynamicMode, localObject, "com.tencent.smtt.webkit.ContextHolder", "setPluginDevelopMode", new Class[] { Boolean.TYPE }, new Object[] { Boolean.valueOf(paramBoolean) });
    }
  }
  
  public void setLocalSmttService(ISmttServiceCommon paramISmttServiceCommon, ISmttServicePartner paramISmttServicePartner, ISmttServiceMtt paramISmttServiceMtt)
  {
    if (!this.mX5Used) {
      return;
    }
    try
    {
      Object localObject = invokeStaticMethod(this.mIsDynamicMode, "com.tencent.smtt.webkit.service.SmttServiceProxy", "getInstance", null, new Object[0]);
      if (localObject != null)
      {
        invokeMethod(this.mIsDynamicMode, localObject, "com.tencent.smtt.webkit.service.SmttServiceProxy", "setLocalSmttService", new Class[] { ISmttServiceCommon.class, ISmttServicePartner.class, ISmttServiceMtt.class }, new Object[] { paramISmttServiceCommon, paramISmttServicePartner, paramISmttServiceMtt });
        return;
      }
    }
    catch (Exception paramISmttServiceCommon)
    {
      paramISmttServiceCommon.printStackTrace();
    }
  }
  
  public void setTbsVersionInfo(int paramInt, String paramString)
  {
    if (!this.mX5Used) {
      return;
    }
    Object localObject = getContextHolder();
    if (localObject != null) {
      invokeMethod(this.mIsDynamicMode, localObject, "com.tencent.smtt.webkit.ContextHolder", "setTbsVersionInfo", new Class[] { Integer.TYPE, String.class }, new Object[] { Integer.valueOf(paramInt), paramString });
    }
  }
  
  public void startQProxyDetectWithSpdy(URL paramURL)
  {
    if (!this.mX5Used) {
      return;
    }
    Object localObject = invokeStaticMethod(this.mIsDynamicMode, "com.tencent.smtt.net.QProxyDetect", "getInstance", null, new Object[0]);
    if (localObject != null) {
      invokeMethod(this.mIsDynamicMode, localObject, "com.tencent.smtt.net.QProxyDetect", "startQProxyDetect", new Class[] { URL.class }, new Object[] { paramURL });
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\x5core\X5CoreInit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */