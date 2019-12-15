package com.tencent.tbs.tbsshell;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.http.Apn;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.CpuInfoUtils;
import com.tencent.common.utils.GpuInfoUtils;
import com.tencent.common.utils.LinuxToolsJni;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.QBKeyStore;
import com.tencent.tbs.common.baseinfo.GodCmdWupManager;
import com.tencent.tbs.common.baseinfo.TBSBroadcastReceiver;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.config.DefaultConfigurationManager;
import com.tencent.tbs.common.lbs.LBS;
import com.tencent.tbs.common.net.ConnectivityChangedObserver;
import com.tencent.tbs.common.net.NetworkDetector;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.tbsshell.common.NetworkDetectorCallbackImpl;
import com.tencent.tbs.tbsshell.common.SmttServiceCommon;
import com.tencent.tbs.tbsshell.common.TbsLog;
import com.tencent.tbs.tbsshell.common.ad.TbsAdProxy;
import com.tencent.tbs.tbsshell.common.x5core.QProxyDetect;
import com.tencent.tbs.tbsshell.common.x5core.X5CoreInit;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class TBSShellDelegate
{
  protected static String DEFAULT_TBS_CORE_VERSION = "";
  private static final String JAVACORE_PACKAGE_PREFIX = "org.chromium";
  private static final boolean PERFORMANCE_LOG_VERBOSE = false;
  protected static final String TAG = "TBSShell";
  private static final int TBS_INIT_PARAM_ERROR = -1;
  public static final long WEBCORE_FILE_LENGTH = 42455596L;
  private static final String X5_FILE_RES_APK = "res.apk";
  private static volatile boolean is_apn_inited = false;
  private volatile boolean isTbsEnvAsyncInited = true;
  protected String mCHID = "0";
  protected Context mCallerAppContext;
  private String mDexOutPutDir;
  private int mInitCount = 0;
  protected boolean mIsPad = false;
  protected String mLCID = "9422";
  protected StringBuilder mLoadFailureDetails = new StringBuilder("");
  protected String mPB = "GE";
  protected String mPPVN = "";
  private Map<String, String> mPerformanceRecord = new HashMap();
  protected String mQua1 = "";
  protected int mSdkVersionCode;
  protected String mSdkVersionName;
  private boolean mTbsBaseInited;
  protected String mTbsCoreVersion;
  protected boolean mTbsGameInited = false;
  protected String mTbsInstallLocation;
  protected boolean mTbsMiniQBInited = false;
  private String mTbsResourcesPath;
  protected boolean mTbsVideoInited;
  protected boolean mTbsWebViewInited;
  protected String mVE = "GA";
  protected X5CoreInit mX5CoreInit = null;
  protected DexClassLoader sBaseDexLoader;
  protected Context x5CoreProvidAppContext;
  
  private void checkLocalWebCoreMd5IfNeeded(String paramString)
  {
    if (!Configuration.getInstance(this.mCallerAppContext).isWebCoreCheckEnabled()) {
      return;
    }
    long l = new File(paramString, "libmttwebview.so").length();
    if (42455596L == l) {
      return;
    }
    paramString = new HashMap();
    paramString.put("expected_length", String.valueOf(42455596L));
    paramString.put("local_md5", String.valueOf(l));
    X5CoreBeaconUploader.getInstance(this.mCallerAppContext).upLoadToBeacon("verify_md5", paramString);
    paramString = new StringBuilder();
    paramString.append("wrong webcore length:");
    paramString.append(l);
    paramString.append("expected:");
    paramString.append(42455596L);
    throw new RuntimeException(paramString.toString());
  }
  
  private int initBase(Context paramContext1, Context paramContext2, DexClassLoader paramDexClassLoader, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
  {
    this.mSdkVersionName = paramString3;
    this.mSdkVersionCode = paramInt;
    this.mTbsCoreVersion = paramString4;
    if ((paramContext1 != null) && (paramContext2 != null) && (paramDexClassLoader != null) && (!TextUtils.isEmpty(paramString1)))
    {
      paramString3 = paramContext2;
      if (paramContext2.getApplicationContext() != null) {
        paramString3 = paramContext2.getApplicationContext();
      }
      this.x5CoreProvidAppContext = paramString3;
      paramContext2 = paramContext1.getApplicationContext();
      if (paramContext2 != null) {
        paramContext1 = paramContext2;
      }
      if (this.sBaseDexLoader == null) {
        this.sBaseDexLoader = paramDexClassLoader;
      }
      this.mCallerAppContext = paramContext1;
      this.mTbsInstallLocation = paramString1;
      this.mDexOutPutDir = paramString2;
      TbsBaseModuleShell.setCallerContext(this.mCallerAppContext);
      TbsBaseModuleShell.setX5CoreHostAppContext(this.x5CoreProvidAppContext);
      return 0;
    }
    loadFailure(paramContext1, paramContext2, paramDexClassLoader, paramString1);
    return -1;
  }
  
  private void preInitX5Core(Context paramContext1, Context paramContext2, String paramString)
  {
    X5CoreInit localX5CoreInit = this.mX5CoreInit;
    if (localX5CoreInit != null) {
      localX5CoreInit.preInit(paramContext1, paramContext2, paramString);
    }
  }
  
  private void preInitX5Core0(final Context paramContext1, final Context paramContext2, final String paramString)
  {
    if (this.mX5CoreInit != null) {
      BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          TBSShellDelegate.this.mX5CoreInit.preInit0(paramContext1, paramContext2, paramString);
        }
      });
    }
  }
  
  private void preInitX5Core2()
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        TBSShellDelegate.this.mX5CoreInit.preInit2();
      }
    });
  }
  
  public abstract void addTimerTask();
  
  public void checkKingCardCondition() {}
  
  protected DexClassLoader createtDexLoader(Context paramContext, DexClassLoader paramDexClassLoader, String[] paramArrayOfString, String paramString1, String paramString2)
  {
    if ((paramDexClassLoader != null) && (paramArrayOfString != null)) {
      if (paramArrayOfString.length == 0) {
        return null;
      }
    }
    for (;;)
    {
      int i;
      try
      {
        if (TextUtils.isEmpty(paramString2))
        {
          paramContext = new StringBuilder();
          paramContext.append(this.mTbsInstallLocation);
          paramContext.append(File.pathSeparator);
          paramContext.append("/system/lib");
          paramString2 = paramContext.toString();
        }
        else
        {
          paramContext = new StringBuilder();
          paramContext.append(paramString2);
          paramContext.append(File.pathSeparator);
          paramContext.append(this.mTbsInstallLocation);
          paramContext.append(File.pathSeparator);
          paramContext.append("/system/lib");
          paramString2 = paramContext.toString();
        }
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
        return null;
      }
      if (i < paramArrayOfString.length)
      {
        if (TBSShell.useTbsCorePrivateClassLoader()) {
          paramContext = new TbsCorePrivateClassLoader(paramArrayOfString[i], paramString1, paramString2, paramContext);
        } else {
          paramContext = new DexClassLoader(paramArrayOfString[i], paramString1, paramString2, paramContext);
        }
        i += 1;
      }
      else
      {
        return paramContext;
        return null;
        i = 0;
        paramContext = paramDexClassLoader;
      }
    }
  }
  
  public void detectWifiWebLogin()
  {
    if (this.mCallerAppContext != null)
    {
      LogUtils.d("NetworkDetector", "smttservice--detectWifiWebLogin");
      NetworkDetector.getInstance(this.mCallerAppContext).detectWifiWebLogin(new NetworkDetectorCallbackImpl());
    }
  }
  
  public Map<String, String> getDataRecord()
  {
    return this.mPerformanceRecord;
  }
  
  protected String[] getGameFileList(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("/game_impl_dex.jar");
    return new String[] { localStringBuilder.toString() };
  }
  
  public abstract String getLoadFailureDetails();
  
  protected String[] getMiniQBFileList(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("/miniqb_dex.jar");
    return new String[] { localStringBuilder.toString() };
  }
  
  public abstract String getResApkPath(String paramString1, String paramString2);
  
  public String getSdkVersionName()
  {
    return this.mSdkVersionName;
  }
  
  public abstract String getTbsCoreVersion(Context paramContext, String paramString1, String paramString2);
  
  public String getTbsInstallLocation()
  {
    return this.mTbsInstallLocation;
  }
  
  protected String[] getWebCoreFileList(String paramString)
  {
    return null;
  }
  
  public abstract void initBeacon(Context paramContext);
  
  public abstract boolean initGame(Context paramContext1, Context paramContext2, String paramString1, String paramString2);
  
  public abstract boolean initMiniQB(Context paramContext1, String paramString, Context paramContext2);
  
  public abstract void initMiniQBUpgrader(Context paramContext1, Context paramContext2);
  
  public abstract boolean initPlugin(Context paramContext1, Context paramContext2);
  
  public abstract void initTBSModuleBridge();
  
  public void initTbsEnvAsync(Context paramContext1, Context paramContext2, String paramString)
  {
    if (!this.isTbsEnvAsyncInited) {
      return;
    }
    initWupAsync(paramContext1, paramContext2);
    this.isTbsEnvAsyncInited = false;
  }
  
  public int initTbsRuntimeEnvironment(Context paramContext1, Context paramContext2, DexClassLoader paramDexClassLoader, final String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
  {
    try
    {
      TbsLog.d("TBSShell", "TBSShellImpl.initTbsRuntimeEnvironment...");
      paramInt = initBase(paramContext1, paramContext2, paramDexClassLoader, paramString1, paramString2, paramString3, paramInt, paramString4);
      if (paramInt != 0) {
        return paramInt;
      }
      this.mTbsResourcesPath = getResApkPath(this.mTbsInstallLocation, "res.apk");
      initWebCore(this.mCallerAppContext, this.mDexOutPutDir, this.x5CoreProvidAppContext);
      long l = System.currentTimeMillis();
      if (this.mX5CoreInit == null) {
        this.mX5CoreInit = new X5CoreInit(WebCoreProxy.x5WebviewDexInit(), true, true, this.mTbsResourcesPath);
      }
      preInitX5Core0(this.mCallerAppContext, this.x5CoreProvidAppContext, this.mDexOutPutDir);
      preInitX5Core(this.mCallerAppContext, this.x5CoreProvidAppContext, this.mDexOutPutDir);
      paramDexClassLoader = new StringBuilder();
      paramDexClassLoader.append("preInitX5Core0 cost: ");
      paramDexClassLoader.append(System.currentTimeMillis() - l);
      paramDexClassLoader.toString();
      initBeacon(this.mCallerAppContext);
      if (needTbsResourceAdaptation(this.mCallerAppContext, this.x5CoreProvidAppContext)) {
        TBSResources.setContext(this.mCallerAppContext, this.mTbsResourcesPath, true);
      } else {
        TBSResources.setContext(this.mCallerAppContext, this.mTbsResourcesPath, false);
      }
      initMiniQBUpgrader(this.mCallerAppContext, this.x5CoreProvidAppContext);
      if (!this.mTbsBaseInited)
      {
        Apn.setApplicationContext(this.mCallerAppContext);
        BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
        {
          public void doRun()
          {
            TBSShellDelegate.this.mX5CoreInit.preInitWithResources(this.val$app_context);
            if (!TBSShellDelegate.is_apn_inited)
            {
              Apn.tbsInit(this.val$app_context);
              TBSShellDelegate.access$002(true);
            }
            PublicSettingManager.getInstance().getFirstScreenDetectEnable();
            CpuInfoUtils.init(paramString1);
            LinuxToolsJni.init(paramString1);
            GpuInfoUtils.init(paramString1);
            QBKeyStore.init(paramString1);
            TBSShellDelegate localTBSShellDelegate = TBSShellDelegate.this;
            localTBSShellDelegate.initGame(localTBSShellDelegate.mCallerAppContext, TBSShellDelegate.this.x5CoreProvidAppContext, TBSShellDelegate.this.mTbsInstallLocation, TBSShellDelegate.this.mDexOutPutDir);
            TBSBroadcastReceiver.getInstance().register(TBSShellDelegate.this.mCallerAppContext);
            TBSBroadcastReceiver.getInstance().addBroadcastObserver(new ConnectivityChangedObserver());
            TBSShellDelegate.this.addTimerTask();
          }
        });
        registerWebHook(paramString1);
        if (!is_apn_inited)
        {
          Apn.tbsInit(this.mCallerAppContext);
          is_apn_inited = true;
        }
        preInitX5Core2();
        initTBSModuleBridge();
        LBS.init(this.mCallerAppContext, paramString1, this.mDexOutPutDir);
        this.mTbsCoreVersion = getTbsCoreVersion(paramContext2, paramString1, this.mTbsCoreVersion);
        TbsBaseModuleShell.setX5CoreVesion(this.mTbsCoreVersion);
        printLog(this.mTbsCoreVersion, paramString1, paramContext1, paramContext2);
        initWup(this.mCallerAppContext, this.x5CoreProvidAppContext);
        initPlugin(this.mCallerAppContext, this.x5CoreProvidAppContext);
        this.mTbsBaseInited = true;
      }
      if (this.mPerformanceRecord != null) {
        this.mPerformanceRecord.put("miniqb_upgrademanager_init_begin", String.valueOf(System.currentTimeMillis()));
      }
      if (this.mPerformanceRecord != null) {
        this.mPerformanceRecord.put("miniqb_upgrademanager_init_end", String.valueOf(System.currentTimeMillis()));
      }
      initVideo(this.mCallerAppContext, this.mDexOutPutDir, this.x5CoreProvidAppContext);
      initMiniQB(paramContext1, this.mDexOutPutDir, this.x5CoreProvidAppContext);
      return 0;
    }
    catch (Throwable paramContext1)
    {
      paramContext2 = new StringBuilder();
      paramContext2.append("init TesEnviroment Error!");
      paramContext2.append(Log.getStackTraceString(paramContext1));
      TbsLog.e("TBSShell", paramContext2.toString());
      throw new RuntimeException(paramContext1);
    }
  }
  
  public int initTbsRuntimeEnvironmentAndNotLoadSo(Context paramContext1, Context paramContext2, DexClassLoader paramDexClassLoader, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
  {
    paramInt = initBase(paramContext1, paramContext2, paramDexClassLoader, paramString1, paramString2, paramString3, paramInt, paramString4);
    if (paramInt != 0) {
      return paramInt;
    }
    this.mTbsResourcesPath = getResApkPath(this.mTbsInstallLocation, "res.apk");
    initWebCore(this.mCallerAppContext, this.mDexOutPutDir, this.x5CoreProvidAppContext);
    System.currentTimeMillis();
    if (this.mX5CoreInit == null) {
      this.mX5CoreInit = new X5CoreInit(WebCoreProxy.x5WebviewDexInit(), true, true, this.mTbsResourcesPath);
    }
    preInitX5Core0(this.mCallerAppContext, this.x5CoreProvidAppContext, this.mDexOutPutDir);
    preInitX5Core(this.mCallerAppContext, this.x5CoreProvidAppContext, this.mDexOutPutDir);
    this.mX5CoreInit.preInit0(this.mCallerAppContext, this.x5CoreProvidAppContext, paramString2);
    this.mX5CoreInit.preInit2();
    TBSResources.setContext(this.mCallerAppContext, this.mTbsResourcesPath);
    this.mX5CoreInit.preInitWithResources(this.mCallerAppContext);
    return 0;
  }
  
  public abstract void initTestExtraModule(Context paramContext, String paramString1, String paramString2);
  
  public abstract boolean initVideo(Context paramContext1, String paramString, Context paramContext2);
  
  public abstract boolean initVideoModule(DexClassLoader paramDexClassLoader, Context paramContext1, Context paramContext2, String paramString1, String paramString2);
  
  public abstract void initWebCore(Context paramContext1, String paramString, Context paramContext2);
  
  public abstract void initWup(Context paramContext1, Context paramContext2);
  
  public abstract void initWupAsync(Context paramContext1, Context paramContext2);
  
  public boolean initX5CoreImpl(DexClassLoader paramDexClassLoader, Context paramContext1, Context paramContext2, String paramString)
  {
    TbsLog.d("TBSShell", "initX5Core...");
    this.mInitCount += 1;
    if (this.mX5CoreInit == null) {
      this.mX5CoreInit = new X5CoreInit(paramDexClassLoader, true, true, this.mTbsResourcesPath);
    }
    System.currentTimeMillis();
    paramDexClassLoader = TBSShellFactory.getSmttServiceCommon();
    this.mX5CoreInit.setLocalSmttService(paramDexClassLoader, TBSShellFactory.getSmttServicePartner(), TBSShellFactory.getSmttServiceMtt());
    paramDexClassLoader.setContext(paramContext1);
    paramDexClassLoader.setTbsSmttServiceCallBack();
    QProxyDetect.getInstance().setX5CoreInit(this.mX5CoreInit);
    this.mX5CoreInit.SmttResource_UpdateContext(TBSResources.getResourceContext(), this.mTbsResourcesPath);
    checkLocalWebCoreMd5IfNeeded(paramString);
    paramDexClassLoader = this.mPerformanceRecord;
    if (paramDexClassLoader != null) {
      paramDexClassLoader.put("tbs_load_so_begin", String.valueOf(System.currentTimeMillis()));
    }
    paramDexClassLoader = this.mX5CoreInit.setContextHolderParams(paramContext1, paramContext2, paramString, this.mDexOutPutDir, true, TBSShell.getTbsSettings());
    paramContext2 = this.mPerformanceRecord;
    if (paramContext2 != null) {
      paramContext2.put("tbs_load_so_end", String.valueOf(System.currentTimeMillis()));
    }
    if (((Boolean)paramDexClassLoader[0]).booleanValue())
    {
      if (this.mInitCount == 1)
      {
        this.mX5CoreInit.preInitWithLoadSo(TbsBaseModuleShell.getCallerContext());
        this.mX5CoreInit.setTbsVersionInfo(TBSShell.VERSION, this.mTbsCoreVersion);
        this.mX5CoreInit.initCookieModule(paramContext1);
        setLoadFailureDetails(null);
        return true;
      }
      paramDexClassLoader = new StringBuilder();
      paramDexClassLoader.append("initX5Core - loadSucc is true but iInitCount is");
      paramDexClassLoader.append(this.mInitCount);
      paramDexClassLoader = paramDexClassLoader.toString();
      TbsLog.e("TBSShell", paramDexClassLoader);
      setLoadFailureDetails(paramDexClassLoader);
      return false;
    }
    if ((paramDexClassLoader[1] instanceof Throwable))
    {
      paramDexClassLoader = (Throwable)paramDexClassLoader[1];
      paramContext1 = new StringBuilder();
      paramContext1.append("initX5Core -- loadSucc: false; exception: ");
      paramContext1.append(paramDexClassLoader);
      paramContext1.append("; cause: ");
      paramContext1.append(Log.getStackTraceString(paramDexClassLoader.getCause()));
      paramDexClassLoader = paramContext1.toString();
      TbsLog.e("TBSShell", paramDexClassLoader);
      setLoadFailureDetails(paramDexClassLoader);
      return false;
    }
    setLoadFailureDetails("initX5Core -- loadSucc: false; ret is null");
    return false;
  }
  
  protected boolean isThirdTesCallMode(Context paramContext1, Context paramContext2)
  {
    paramContext1 = paramContext1.getPackageName();
    paramContext2 = paramContext2.getPackageName();
    if (!TextUtils.isEmpty(paramContext1))
    {
      if (TextUtils.isEmpty(paramContext2)) {
        return true;
      }
      return paramContext1.equalsIgnoreCase(paramContext2) ^ true;
    }
    return true;
  }
  
  public abstract void loadFailure(Context paramContext1, Context paramContext2, DexClassLoader paramDexClassLoader, String paramString);
  
  public abstract boolean needTbsResourceAdaptation(Context paramContext1, Context paramContext2);
  
  public void onX5InitCallback(final boolean paramBoolean)
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        TBSShellDelegate.this.onX5InitCompletedCallbackImpl(paramBoolean);
      }
    });
  }
  
  public void onX5InitCompletedCallbackImpl(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      Context localContext = TbsBaseModuleShell.getCallerContext();
      DefaultConfigurationManager.getInstance().setAppContext(localContext);
      WebCoreProxy.doWupTask();
      if (this.mX5CoreInit != null)
      {
        LogUtils.d("TbsShellDelegate", "onX5InitCompletedCallbackImpl");
        checkKingCardCondition();
        TbsUserInfo.getInstance().checkKingCardCondition();
        this.mX5CoreInit.initAdInfoDb(localContext);
        this.mX5CoreInit.initLiveLog(localContext);
        this.mX5CoreInit.initCacheManager(localContext);
        if (DefaultConfigurationManager.getInstance().enableGodCmd())
        {
          TbsUserInfo.getInstance().init();
          GodCmdWupManager.getInstance().init();
          GodCmdWupManager.getInstance().excuteGodCmd();
        }
      }
      DexClassLoader localDexClassLoader = this.sBaseDexLoader;
      TbsAdProxy.getInstance(localContext).init(localContext, localDexClassLoader, this.mTbsInstallLocation, this.mDexOutPutDir);
    }
  }
  
  public abstract void printLog(String paramString1, String paramString2, Context paramContext1, Context paramContext2);
  
  public abstract void registerWebHook(String paramString);
  
  public abstract void setLoadFailureDetails(String paramString);
  
  public void setQbInfoForQua2_v3(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean)
  {
    this.mCHID = paramString1;
    this.mLCID = paramString2;
    this.mPB = paramString3;
    this.mVE = paramString4;
    this.mPPVN = paramString5;
    this.mIsPad = paramBoolean;
  }
  
  public void setQua1FromUi(String paramString)
  {
    this.mQua1 = paramString;
  }
  
  public void setSdkVersionCode(int paramInt)
  {
    this.mSdkVersionCode = paramInt;
  }
  
  public void setSdkVersionName(String paramString)
  {
    this.mSdkVersionName = paramString;
  }
  
  private static class TbsCorePrivateClassLoader
    extends DexClassLoader
  {
    public TbsCorePrivateClassLoader(String paramString1, String paramString2, String paramString3, ClassLoader paramClassLoader)
    {
      super(paramString2, paramString3, paramClassLoader);
    }
    
    protected Class<?> loadClass(String paramString, boolean paramBoolean)
      throws ClassNotFoundException
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("WebCoreClassLoader - loadClass(");
      ((StringBuilder)localObject1).append(paramString);
      ((StringBuilder)localObject1).append(",");
      ((StringBuilder)localObject1).append(paramBoolean);
      ((StringBuilder)localObject1).append(")...");
      ((StringBuilder)localObject1).toString();
      Object localObject2;
      if ((paramString != null) && (paramString.startsWith("org.chromium")))
      {
        localObject2 = findLoadedClass(paramString);
        localObject1 = localObject2;
        if (localObject2 != null) {}
      }
      try
      {
        localObject1 = findClass(paramString);
        localObject2 = localObject1;
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        ClassLoader localClassLoader;
        for (;;) {}
      }
      localObject1 = localObject2;
      if (localObject2 == null)
      {
        localClassLoader = getParent();
        localObject1 = localObject2;
        if (localClassLoader != null) {
          localObject1 = localClassLoader.loadClass(paramString);
        }
      }
      return (Class<?>)localObject1;
      return super.loadClass(paramString, paramBoolean);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\TBSShellDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */