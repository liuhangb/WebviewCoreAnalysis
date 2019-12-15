package com.tencent.tbs.tbsshell.partner;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.basesupport.ModuleProxy;
import com.tencent.basesupport.PackageInfo.PackageInfoProvider;
import com.tencent.common.http.Apn;
import com.tencent.common.plugin.QBPluginFactory;
import com.tencent.common.plugin.QBPluginServiceImpl;
import com.tencent.common.plugin.QBPluginServiceImpl.IDownloadSpecicalWhiteList;
import com.tencent.common.wup.WUPProxyHolder;
import com.tencent.mtt.AppInfoHolder;
import com.tencent.mtt.miniqb.export.IMiniQBDexLoaderProvider;
import com.tencent.mtt.video.export.IVideoDexLoaderProvider;
import com.tencent.mtt.video.export.VideoEngine;
import com.tencent.smtt.downloader.TbsDownloader;
import com.tencent.smtt.downloader.a;
import com.tencent.tbs.common.baseinfo.ICoreInfoFetcher;
import com.tencent.tbs.common.baseinfo.ICoreService;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.log.LogManager;
import com.tencent.tbs.common.wup.TbsWupClientProxy;
import com.tencent.tbs.modulebridge.TBSModuleBridgeFactory;
import com.tencent.tbs.tbsshell.IWebViewDexLoaderProvider;
import com.tencent.tbs.tbsshell.TBSShell;
import com.tencent.tbs.tbsshell.TBSShellDelegate;
import com.tencent.tbs.tbsshell.TBSShellFactory;
import com.tencent.tbs.tbsshell.WebCoreProxy;
import com.tencent.tbs.tbsshell.WebHook;
import com.tencent.tbs.tbsshell.common.ISmttServicePartner;
import com.tencent.tbs.tbsshell.common.QProxyPolicies;
import com.tencent.tbs.tbsshell.common.SmttServiceCommon;
import com.tencent.tbs.tbsshell.common.TbsLog;
import com.tencent.tbs.tbsshell.common.plugin.PluginRelateFuncImpl;
import com.tencent.tbs.tbsshell.common.x5core.X5CoreInit;
import com.tencent.tbs.tbsshell.partner.debug.TbsLoadFailuresReport;
import com.tencent.tbs.tbsshell.partner.fullscreenplayer.FullscreenPlayerPorxy;
import com.tencent.tbs.tbsshell.partner.miniqb.TbsMiniQBProxy;
import com.tencent.tbs.tbsshell.partner.miniqb.upgrade.MiniQBUpgradeManager;
import com.tencent.tbs.tbsshell.partner.modulebridge.TBSModuleBrigeFactoryImpl;
import com.tencent.tbs.tbsshell.partner.ug.TbsUgEngine;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TBSShellPartner
  extends TBSShellDelegate
{
  private void doDownloadTbsCoreIfNeeded(Context paramContext)
  {
    if ("com.tencent.mm".equals(paramContext.getApplicationInfo().packageName))
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("doDownloadTbsCoreIfNeeded,process=");
      ((StringBuilder)localObject).append(a.a(paramContext));
      ((StringBuilder)localObject).toString();
      if (!"com.tencent.mm:tools".equals(getCurrentProcessName(paramContext))) {
        return;
      }
      localObject = new File(new File(a.a(paramContext), "core_share"), "tbs.conf");
      HashMap localHashMap = new HashMap();
      localHashMap.put("is_exist", Boolean.valueOf(((File)localObject).exists()));
      X5CoreBeaconUploader.getInstance(paramContext).upLoadToBeacon("TBS_64BIT_DOWNLOAD_STATUS", localHashMap);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("upload to beacon,map = ");
      localStringBuilder.append(localHashMap);
      localStringBuilder.toString();
      if (((File)localObject).exists())
      {
        TbsLog.i("TBSShell", "no need doDownloadTbs");
        return;
      }
      if (TbsDownloader.a(paramContext, false)) {
        TbsDownloader.a(paramContext);
      }
    }
  }
  
  public static String getCurrentProcessName(Context paramContext)
  {
    try
    {
      int i = Process.myPid();
      Object localObject = "";
      Iterator localIterator = ((ActivityManager)paramContext.getApplicationContext().getSystemService("activity")).getRunningAppProcesses().iterator();
      paramContext = (Context)localObject;
      while (localIterator.hasNext())
      {
        localObject = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        if (((ActivityManager.RunningAppProcessInfo)localObject).pid == i) {
          paramContext = ((ActivityManager.RunningAppProcessInfo)localObject).processName;
        }
      }
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    return "";
  }
  
  private void initPackageInfoProvider()
  {
    com.tencent.basesupport.PackageInfo.PROXY = ModuleProxy.newProxy(PackageInfo.PackageInfoProvider.class, new PackageInfo.PackageInfoProvider()
    {
      public String broadcastPermission()
      {
        return null;
      }
      
      public String keyName()
      {
        return packageName();
      }
      
      public String packageName()
      {
        return "com.tencent.mtt";
      }
    });
  }
  
  private void initTbsUg(String paramString)
  {
    TbsUgEngine.getInstance().init(TBSShellFactory.getSmttServicePartner(), this.sBaseDexLoader, paramString, this.mCallerAppContext);
  }
  
  private boolean isProxySettingEnabled()
  {
    return (TbsUserInfo.getInstance() == null) || (TbsUserInfo.getInstance().serverAllowQProxyStatus() != 0);
  }
  
  private void reportTbsLoadFailure()
  {
    reportTbsLoadFailure(900, false);
  }
  
  private void reportTbsLoadFailure(int paramInt, boolean paramBoolean)
  {
    try
    {
      Context localContext = this.mCallerAppContext.getApplicationContext();
      localObject = localContext.getPackageName();
      if (!paramBoolean)
      {
        if ((((String)localObject).equalsIgnoreCase("com.tencent.mobileqq")) || (((String)localObject).equalsIgnoreCase("com.tencent.mm")) || (((String)localObject).equalsIgnoreCase("com.tencent.mtt"))) {
          return;
        }
        if (((String)localObject).equalsIgnoreCase("com.qzone")) {
          return;
        }
      }
      int i = Integer.parseInt(this.mTbsCoreVersion);
      localObject = new StringBuilder("tbs_sdk_ver: ");
      ((StringBuilder)localObject).append(this.mSdkVersionName);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("; errDetails: ");
      localStringBuilder.append(this.mLoadFailureDetails.toString());
      ((StringBuilder)localObject).append(localStringBuilder.toString());
      TbsLoadFailuresReport.getInstance(localContext).setLoadErrorCode(paramInt, ((StringBuilder)localObject).toString(), i);
      return;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("report load error exception: ");
      ((StringBuilder)localObject).append(localThrowable);
      TbsLog.e("TBSShell", ((StringBuilder)localObject).toString());
      return;
    }
  }
  
  public void addTimerTask()
  {
    TimerTask local6 = new TimerTask()
    {
      public void run()
      {
        try
        {
          Thread.sleep(10000L);
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
        MiniQBUpgradeManager.getInstance().initEnvironmentIfNeed();
        MiniQBUpgradeManager.getInstance().doMiniQBUpgradeTask();
      }
    };
    try
    {
      new Timer().schedule(local6, 3000L);
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  public String getLoadFailureDetails()
  {
    return this.mLoadFailureDetails.toString();
  }
  
  public String getResApkPath(String paramString1, String paramString2)
  {
    return new File(paramString1, paramString2).getAbsolutePath();
  }
  
  public String getTbsCoreVersion(Context paramContext, String paramString1, String paramString2)
  {
    if (paramString2 != null)
    {
      paramContext = paramString2;
      if (!DEFAULT_TBS_CORE_VERSION.equals(paramString2)) {}
    }
    else
    {
      paramContext = TBSShell.getCurrentTbsCoreVer();
    }
    return paramContext;
  }
  
  public void initBeacon(Context paramContext)
  {
    X5CoreBeaconUploader.setContext(paramContext);
  }
  
  public boolean initGame(Context paramContext1, Context paramContext2, String paramString1, String paramString2)
  {
    return this.mTbsGameInited;
  }
  
  public boolean initMiniQB(Context paramContext1, final String paramString, final Context paramContext2)
  {
    if (!this.mTbsMiniQBInited)
    {
      paramContext2 = new StringBuilder();
      paramContext2.append("initMiniQB - mtesInstallLocation=");
      paramContext2.append(this.mTbsInstallLocation);
      paramContext2.append(",callerAppContext is activity?");
      paramContext2.append(paramContext1 instanceof Activity);
      paramContext2.toString();
      paramContext2 = paramContext1.getApplicationContext();
      TbsMiniQBProxy.getInstance().init(paramContext1, this.sBaseDexLoader, new IMiniQBDexLoaderProvider()
      {
        public DexClassLoader getDexClassLoader()
        {
          Object localObject1 = MiniQBUpgradeManager.getInstance().getMiniQBLoadPath();
          Object localObject2 = TBSShellPartner.this.getMiniQBFileList((String)localObject1);
          if ((localObject2 != null) && (localObject2.length != 0))
          {
            Object localObject3 = new StringBuilder();
            ((StringBuilder)localObject3).append("initMiniQB dexOutPutDir=");
            ((StringBuilder)localObject3).append(paramString);
            ((StringBuilder)localObject3).toString();
            MiniQBUpgradeManager.getInstance().initEnvironmentIfNeed();
            localObject3 = TBSShellPartner.this;
            Context localContext = paramContext2;
            DexClassLoader localDexClassLoader = ((TBSShellPartner)localObject3).sBaseDexLoader;
            if (MiniQBUpgradeManager.getInstance().isThirdPartyApp()) {
              localObject1 = paramString;
            }
            localObject1 = ((TBSShellPartner)localObject3).createtDexLoader(localContext, localDexClassLoader, (String[])localObject2, (String)localObject1, null);
          }
          else
          {
            localObject1 = TBSShellPartner.this.sBaseDexLoader;
          }
          TbsMiniQBProxy.getInstance().setDexLoader((DexClassLoader)localObject1);
          if (localObject1 != null) {
            localObject2 = "true";
          } else {
            localObject2 = "false";
          }
          LogManager.logL1(9001, (String)localObject2, new Object[0]);
          return (DexClassLoader)localObject1;
        }
      });
      this.mTbsMiniQBInited = true;
    }
    return this.mTbsMiniQBInited;
  }
  
  public void initMiniQBUpgrader(Context paramContext1, Context paramContext2)
  {
    MiniQBUpgradeManager.getInstance().init(paramContext1, paramContext2, this.mTbsInstallLocation, this.mTbsCoreVersion, this.mSdkVersionCode);
  }
  
  public boolean initPlugin(Context paramContext1, Context paramContext2)
  {
    QBPluginServiceImpl.getInstance().initApplicationCommon(paramContext1, paramContext2, false);
    QBPluginServiceImpl.getInstance().initPluginSystem(new PluginRelateFuncImpl(), null, 2);
    QBPluginServiceImpl.getInstance().setIDownloadSpecicalWhiteList(new QBPluginServiceImpl.IDownloadSpecicalWhiteList()
    {
      public ArrayList<String> getDownloadSpecicalWhiteList()
      {
        return TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(129);
      }
    });
    QBPluginFactory.getInstance(paramContext1).setLocalPluginServiceImpl(QBPluginServiceImpl.getInstance());
    return true;
  }
  
  public void initTBSModuleBridge()
  {
    TBSModuleBridgeFactory.getInstance().setImpl(new TBSModuleBrigeFactoryImpl());
  }
  
  public int initTbsRuntimeEnvironment(final Context paramContext1, Context paramContext2, DexClassLoader paramDexClassLoader, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
  {
    initPackageInfoProvider();
    paramInt = super.initTbsRuntimeEnvironment(paramContext1, paramContext2, paramDexClassLoader, paramString1, paramString2, paramString3, paramInt, paramString4);
    initTbsUg(paramString1);
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          Thread.sleep(10000L);
          boolean bool = Configuration.getInstance(paramContext1).isEnableAutoDownloadTbsCore();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("enable = ");
          localStringBuilder.append(bool);
          localStringBuilder.toString();
          if (bool)
          {
            TBSShellPartner.this.doDownloadTbsCoreIfNeeded(paramContext1);
            return;
          }
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
      }
    }).start();
    return paramInt;
  }
  
  public void initTestExtraModule(Context paramContext, String paramString1, String paramString2)
  {
    paramContext = new StringBuilder();
    paramContext.append("initTestExtraModule start:");
    paramContext.append(paramString1);
    paramContext.append(",");
    paramContext.append(paramString2);
    paramContext.append(",TBS_APMTEST_FLAG:");
    paramContext.append(false);
    paramContext.toString();
  }
  
  public boolean initVideo(final Context paramContext1, final String paramString, final Context paramContext2)
  {
    if (!this.mTbsVideoInited)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("initVideo,callerContext is activity?");
      ((StringBuilder)localObject).append(paramContext1 instanceof Activity);
      ((StringBuilder)localObject).toString();
      localObject = VideoEngine.getInstance();
      VideoEngine.sTbsInstallDir = this.mTbsInstallLocation;
      VideoEngine.sTbsDexOutPutDir = paramString;
      ((VideoEngine)localObject).init(paramContext1, new IVideoDexLoaderProvider()
      {
        public DexClassLoader getDexClassLoader()
        {
          TBSShellPartner localTBSShellPartner = TBSShellPartner.this;
          Context localContext = paramContext1;
          DexClassLoader localDexClassLoader = localTBSShellPartner.sBaseDexLoader;
          Object localObject = new StringBuilder();
          ((StringBuilder)localObject).append(TBSShellPartner.this.mTbsInstallLocation);
          ((StringBuilder)localObject).append("/video_impl_dex.jar");
          localObject = ((StringBuilder)localObject).toString();
          String str = paramString;
          return localTBSShellPartner.createtDexLoader(localContext, localDexClassLoader, new String[] { localObject }, str, null);
        }
        
        public DexClassLoader getDexClassLoader(String[] paramAnonymousArrayOfString, String paramAnonymousString1, String paramAnonymousString2)
        {
          if ((paramAnonymousArrayOfString != null) && (paramAnonymousArrayOfString.length != 0))
          {
            TBSShellPartner localTBSShellPartner = TBSShellPartner.this;
            paramAnonymousArrayOfString = localTBSShellPartner.createtDexLoader(paramContext1, localTBSShellPartner.sBaseDexLoader, paramAnonymousArrayOfString, paramAnonymousString1, paramAnonymousString2);
          }
          else
          {
            paramAnonymousArrayOfString = TBSShellPartner.this.sBaseDexLoader;
          }
          VideoEngine.sTbsThirdcallMode = TBSShellPartner.this.isThirdTesCallMode(paramContext1, paramContext2);
          return paramAnonymousArrayOfString;
        }
      });
      this.mTbsVideoInited = true;
    }
    return this.mTbsVideoInited;
  }
  
  public boolean initVideoModule(DexClassLoader paramDexClassLoader, final Context paramContext1, final Context paramContext2, String paramString1, final String paramString2)
  {
    paramDexClassLoader = VideoEngine.getInstance();
    paramContext1 = paramContext1.getApplicationContext();
    paramDexClassLoader.init(paramContext1, new IVideoDexLoaderProvider()
    {
      public DexClassLoader getDexClassLoader()
      {
        TBSShellPartner localTBSShellPartner = TBSShellPartner.this;
        Context localContext = paramContext1;
        DexClassLoader localDexClassLoader = localTBSShellPartner.sBaseDexLoader;
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append(TBSShellPartner.this.mTbsInstallLocation);
        ((StringBuilder)localObject).append("/video_impl_dex.jar");
        localObject = ((StringBuilder)localObject).toString();
        String str = paramString2;
        return localTBSShellPartner.createtDexLoader(localContext, localDexClassLoader, new String[] { localObject }, str, null);
      }
      
      public DexClassLoader getDexClassLoader(String[] paramAnonymousArrayOfString, String paramAnonymousString1, String paramAnonymousString2)
      {
        VideoEngine.sTbsThirdcallMode = TBSShellPartner.this.isThirdTesCallMode(paramContext1, paramContext2);
        return TBSShellPartner.this.sBaseDexLoader;
      }
    });
    return true;
  }
  
  public void initWebCore(final Context paramContext1, final String paramString, Context paramContext2)
  {
    int i;
    if (!this.mTbsWebViewInited)
    {
      WebCoreProxy.init(new IWebViewDexLoaderProvider()
      {
        public DexClassLoader getDexClassLoader()
        {
          Object localObject1 = TBSShellPartner.this;
          localObject1 = ((TBSShellPartner)localObject1).getWebCoreFileList(((TBSShellPartner)localObject1).mTbsInstallLocation);
          if ((localObject1 != null) && (localObject1.length != 0))
          {
            Object localObject2 = TBSShellPartner.this;
            localObject1 = ((TBSShellPartner)localObject2).createtDexLoader(paramContext1, ((TBSShellPartner)localObject2).sBaseDexLoader, (String[])localObject1, paramString, null);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("WebCoreProxy dexOutPutDir=");
            ((StringBuilder)localObject2).append(paramString);
            ((StringBuilder)localObject2).append(" webCoreDexLoader: ");
            ((StringBuilder)localObject2).append(localObject1);
            ((StringBuilder)localObject2).append(", sBaseDexLoader: ");
            ((StringBuilder)localObject2).append(TBSShellPartner.this.sBaseDexLoader);
            ((StringBuilder)localObject2).toString();
          }
          else
          {
            localObject1 = TBSShellPartner.this.sBaseDexLoader;
          }
          FullscreenPlayerPorxy.getInstance(paramContext1).init((DexClassLoader)localObject1, TBSShellPartner.this.mTbsInstallLocation, paramString);
          return (DexClassLoader)localObject1;
        }
      }, true, paramContext1, paramContext2, this.mTbsInstallLocation, paramString);
      i = 0;
    }
    try
    {
      int k = this.mSdkVersionName.indexOf(".");
      int j = Integer.parseInt(this.mSdkVersionName.substring(0, k));
      paramContext1 = this.mSdkVersionName;
      k += 1;
      k = Integer.parseInt(paramContext1.substring(k, this.mSdkVersionName.indexOf(".", k)));
      i = k + j * 10;
    }
    catch (Exception paramContext1)
    {
      for (;;) {}
    }
    if ((i < 22) && (Looper.getMainLooper() != Looper.myLooper())) {
      WebCoreProxy.canUseX5();
    }
    this.mTbsWebViewInited = true;
  }
  
  public void initWup(Context paramContext1, Context paramContext2)
  {
    WUPProxyHolder.setPublicWUPProxy(new TbsWupClientProxy());
    TbsBaseModuleShell.setCoreInfoFetcher(new ICoreInfoFetcher()
    {
      public byte getCoreType()
      {
        if ((TBSShellPartner.this.mTbsCoreVersion != null) && (!TBSShellPartner.DEFAULT_TBS_CORE_VERSION.equals(TBSShellPartner.this.mTbsCoreVersion))) {
          return 1;
        }
        return 0;
      }
      
      public String getCoreVersion()
      {
        return TBSShellPartner.this.mTbsCoreVersion;
      }
      
      public boolean getIsPad()
      {
        return TBSShellPartner.this.mIsPad;
      }
      
      public int getMaxReportNumber()
      {
        return TbsUserInfo.getInstance().mMaxReportNumber;
      }
      
      public boolean getMiniqbDebugFlag()
      {
        return TBSShellFactory.getSmttServicePartner().isDebugMiniQBEnv();
      }
      
      public String getQbCHID()
      {
        return TBSShellPartner.this.mCHID;
      }
      
      public String getQbLCID()
      {
        return TBSShellPartner.this.mLCID;
      }
      
      public String getQbPB()
      {
        return TBSShellPartner.this.mPB;
      }
      
      public String getQbPPVN()
      {
        return TBSShellPartner.this.mPPVN;
      }
      
      public String getQbVE()
      {
        return TBSShellPartner.this.mVE;
      }
      
      public String getQua1FromQbUi()
      {
        return TBSShellPartner.this.mQua1;
      }
      
      public boolean shouldUseQProxyAccordingToFlag(byte paramAnonymousByte)
      {
        return QProxyPolicies.shouldUseQProxyAccordingToFlag(paramAnonymousByte);
      }
    });
    TbsBaseModuleShell.setTesVersionName(this.mSdkVersionName);
    TbsBaseModuleShell.setTesVersionCode(this.mSdkVersionCode);
    AppInfoHolder.setAppInfoProvider(new TbsAppInfoProvider());
  }
  
  public void initWupAsync(Context paramContext1, Context paramContext2)
  {
    TbsBaseModuleShell.setTesVersionName(this.mSdkVersionName);
    TbsBaseModuleShell.setCoreService(new ICoreService()
    {
      public void onConnectivityChanged(int paramAnonymousInt)
      {
        if ((Apn.isNetworkConnected()) && (paramAnonymousInt == 4) && (TBSShellPartner.this.isProxySettingEnabled()))
        {
          TBSShellFactory.getSmttServiceCommon().setNeedWIFILogin(true);
          TBSShellPartner.this.detectWifiWebLogin();
        }
        else
        {
          TBSShellFactory.getSmttServiceCommon().setNeedWIFILogin(false);
        }
        if ((TBSShellPartner.this.mX5CoreInit != null) && (WebCoreProxy.mX5Used)) {
          WebCoreProxy.invokeStaticMethod(WebCoreProxy.mIsDynamicMode, "com.tencent.smtt.net.AwNetworkUtils", "setNetworkTypeChangedState", new Class[] { Integer.TYPE }, new Object[] { Integer.valueOf(paramAnonymousInt) });
        }
        if (TBSShellPartner.this.mX5CoreInit != null) {
          TBSShellPartner.this.mX5CoreInit.connectivityChangedSoRefreshJavaCoreApnState();
        }
      }
      
      public void refreshSpdyProxy() {}
      
      public void setForceDirect(boolean paramAnonymousBoolean) {}
      
      public void setSafeDomainList(Map<Integer, ArrayList<String>> paramAnonymousMap) {}
      
      public void sweepAll(Context paramAnonymousContext)
      {
        WebCoreProxy.clearAllCache(paramAnonymousContext);
      }
      
      public void sweepCookieCache(Context paramAnonymousContext) {}
      
      public void sweepDNSCache(Context paramAnonymousContext) {}
      
      public void sweepFileCache(Context paramAnonymousContext) {}
      
      public void sweepWebStorageCache(Context paramAnonymousContext) {}
    });
  }
  
  public void loadFailure(Context paramContext1, Context paramContext2, DexClassLoader paramDexClassLoader, String paramString)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public boolean needTbsResourceAdaptation(Context paramContext1, Context paramContext2)
  {
    try
    {
      if (TBSShell.isThirdPartyApp(paramContext1))
      {
        if (((paramContext1 instanceof Context)) && (paramContext1.getApplicationContext() != null) && ("com.achievo.vipshop".equalsIgnoreCase(paramContext1.getApplicationContext().getApplicationInfo().packageName))) {
          return true;
        }
        initWup(paramContext1, paramContext2);
        boolean bool = Configuration.getInstance(paramContext1).isTbsResourceAdaptEnabled();
        if (bool) {
          return true;
        }
      }
    }
    catch (Throwable paramContext1)
    {
      paramContext1.printStackTrace();
    }
    return false;
  }
  
  public void printLog(String paramString1, String paramString2, Context paramContext1, Context paramContext2)
  {
    LogManager.logL1(1001, null, new Object[0]);
    LogManager.logHeader("tbsver", paramString1);
    LogManager.logHeader("tbsdir", paramString2);
    LogManager.logHeader("caller", paramContext1.getPackageName());
    LogManager.logHeader("x5host", paramContext2.getPackageName());
  }
  
  public void registerWebHook(String paramString)
  {
    if (WebHook.shouldDoHook()) {
      new WebHook(paramString).hookDvmDlopen();
    }
  }
  
  public void setLoadFailureDetails(String paramString)
  {
    if ((!TextUtils.isEmpty(paramString)) && (paramString.contains("is 32-bit instead of 64-bit"))) {
      Log.e("ERROR:", ".....................................................................................\n.............................................................................................\nX5 does not support the 64-bit mode to run, please refer to the solution: https://x5.tencent.com/tbs/technical.html#/detail/sdk/1/34cf1488-7dc2-41ca-a77f-0014112bcab7\n.....................................................................................\n.....................................................................................\n");
    }
    LogManager.logL1(1002, paramString, new Object[0]);
    if (paramString != null)
    {
      StringBuilder localStringBuilder1 = this.mLoadFailureDetails;
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append("#");
      localStringBuilder2.append(paramString);
      localStringBuilder1.append(localStringBuilder2.toString());
      reportTbsLoadFailure();
      return;
    }
    this.mLoadFailureDetails.append("#NULL");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\TBSShellPartner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */