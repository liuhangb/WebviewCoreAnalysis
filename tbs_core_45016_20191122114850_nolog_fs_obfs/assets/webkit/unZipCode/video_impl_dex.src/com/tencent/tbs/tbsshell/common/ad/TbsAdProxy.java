package com.tencent.tbs.tbsshell.common.ad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.tencent.common.plugin.IQBPluginSystemCallback;
import com.tencent.common.plugin.QBPluginItemInfo;
import com.tencent.common.plugin.QBPluginSystem;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LinuxToolsJni;
import com.tencent.common.utils.TbsMode;
import com.tencent.mtt.ContextHolder;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.DomainMatcher;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

public class TbsAdProxy
{
  private static final String CLASS_NAME = "com.tencent.tbs.ad.TbsAdBridge";
  private static final String DEX_NAME = "native_ad_plugin_dex.jar";
  private static final String FILE_SPLIT_STRING = "_";
  private static final String PLUGIN_PACKAGE_NAME = "com.tencent.tbs.ad.plugin";
  private static final String TAG = "adProxy";
  private static DexClassLoader mAdProxyLoader;
  private static DexClassLoader mBaseLoader;
  private static String mDexOptPath;
  private static int mLoaderDexTimer;
  private static String mTbsInstallLocation;
  private static volatile TbsAdProxy sInstance;
  boolean adResearchInRatioControl = false;
  boolean isAdResearchInRatioControlInitied = false;
  private boolean mHasLoadPlugin = false;
  private QBPluginSystem mPluginSystem;
  private String mPluginUnZipPath = null;
  private IQBPluginSystemCallback mQbPluginCallback;
  private Object mTbsAdBridge = null;
  
  private void cleanOldPlugin(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return;
    }
    for (;;)
    {
      int i;
      Object localObject2;
      int k;
      int m;
      try
      {
        int i1 = Integer.parseInt(paramString2);
        j = paramString1.lastIndexOf(File.separatorChar);
        if (j == -1) {
          return;
        }
        i = 0;
        paramString2 = new File(paramString1.substring(0, j));
        if (!paramString2.exists()) {
          return;
        }
        boolean bool = paramString2.isDirectory();
        if (!bool) {
          return;
        }
        try
        {
          paramString2 = paramString2.listFiles();
        }
        catch (Error paramString2)
        {
          Log.e("adProxy", paramString2.toString());
          paramString2 = null;
        }
        if (paramString2 != null)
        {
          if (paramString2.length < 1) {
            return;
          }
          int i3 = paramString2.length;
          j = i1;
          localObject1 = null;
          n = 0;
          if (i < i3)
          {
            localObject2 = paramString2[i];
            if (!((File)localObject2).isDirectory())
            {
              k = n;
              localObject2 = localObject1;
              m = j;
              break label377;
            }
            String str = ((File)localObject2).getAbsolutePath();
            if (TextUtils.isEmpty(str))
            {
              k = n;
              localObject2 = localObject1;
              m = j;
              break label377;
            }
            if (!str.startsWith(paramString1))
            {
              k = n;
              localObject2 = localObject1;
              m = j;
              break label377;
            }
            localObject2 = str.substring(str.lastIndexOf(File.separatorChar) + 1).split("_");
            if (localObject2.length != 3)
            {
              k = n;
              localObject2 = localObject1;
              m = j;
              break label377;
            }
            int i2 = Integer.parseInt(localObject2[1]);
            k = n;
            localObject2 = localObject1;
            m = j;
            if (i2 >= i1) {
              break label377;
            }
            n += 1;
            k = n;
            localObject2 = localObject1;
            m = j;
            if (i2 >= j) {
              break label377;
            }
            localObject2 = str;
            m = i2;
            k = n;
            break label377;
          }
          if ((n > 2) && (localObject1 != null))
          {
            delete(new File((String)localObject1));
            paramString1 = new StringBuilder();
            paramString1.append("cleanOldPlugin deletePath:");
            paramString1.append((String)localObject1);
            paramString1.toString();
          }
        }
        else
        {
          return;
        }
      }
      catch (Exception paramString1)
      {
        Log.e("adProxy", paramString1.toString());
      }
      return;
      label377:
      i += 1;
      int n = k;
      Object localObject1 = localObject2;
      int j = m;
    }
  }
  
  public static void delete(File paramFile)
  {
    if (paramFile != null)
    {
      if (!paramFile.exists()) {
        return;
      }
      if (paramFile.isFile())
      {
        paramFile.delete();
        return;
      }
      File[] arrayOfFile = paramFile.listFiles();
      if (arrayOfFile == null) {
        return;
      }
      int j = arrayOfFile.length;
      int i = 0;
      while (i < j)
      {
        delete(arrayOfFile[i]);
        i += 1;
      }
      paramFile.delete();
      return;
    }
  }
  
  @SuppressLint({"NewApi"})
  private Method getAdBridgeMethod(Context paramContext, String paramString, Class<?>[] paramArrayOfClass)
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
  {
    Class localClass = mAdProxyLoader.loadClass("com.tencent.tbs.ad.TbsAdBridge");
    if (this.mTbsAdBridge == null)
    {
      Method localMethod = localClass.getMethod("getInstance", new Class[] { Context.class, DexClassLoader.class });
      localMethod.setAccessible(true);
      this.mTbsAdBridge = localMethod.invoke(null, new Object[] { paramContext, mBaseLoader });
    }
    paramContext = localClass.getMethod(paramString, paramArrayOfClass);
    paramContext.setAccessible(true);
    return paramContext;
  }
  
  private File getDexFile(boolean paramBoolean)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getDexFile preload :");
    ((StringBuilder)localObject).append(paramBoolean);
    ((StringBuilder)localObject).toString();
    updatePlugin(paramBoolean);
    this.mPluginUnZipPath = PublicSettingManager.getInstance().getAdPluginPath();
    mDexOptPath = this.mPluginUnZipPath;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getAdPluginPath mPluginUnZipPath :");
    ((StringBuilder)localObject).append(this.mPluginUnZipPath);
    ((StringBuilder)localObject).toString();
    if (!TextUtils.isEmpty(this.mPluginUnZipPath))
    {
      localObject = new File(this.mPluginUnZipPath, "native_ad_plugin_dex.jar");
      if (((File)localObject).exists())
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("use plugin dex file:");
        localStringBuilder.append(((File)localObject).getAbsolutePath());
        localStringBuilder.toString();
        return (File)localObject;
      }
    }
    localObject = new File(mTbsInstallLocation, "native_ad_plugin_dex.jar");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("mTbsInstallLocation:");
    localStringBuilder.append(mTbsInstallLocation);
    localStringBuilder.toString();
    if (!((File)localObject).exists()) {
      return null;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("use default dex file:");
    localStringBuilder.append(((File)localObject).getAbsolutePath());
    localStringBuilder.toString();
    return (File)localObject;
  }
  
  public static TbsAdProxy getInstance(Context paramContext)
  {
    if (sInstance == null) {
      try
      {
        if (sInstance == null) {
          sInstance = new TbsAdProxy();
        }
      }
      finally {}
    }
    return sInstance;
  }
  
  private void initDex(boolean paramBoolean)
  {
    for (;;)
    {
      File localFile;
      try
      {
        if ((mAdProxyLoader == null) && (mLoaderDexTimer <= 2))
        {
          boolean bool = Configuration.getInstance(ContextHolder.getAppContext()).isTbsAdPluginEnable();
          if (!bool) {
            return;
          }
          localFile = getDexFile(paramBoolean);
          if (!paramBoolean) {
            if (localFile != null) {}
          }
        }
      }
      finally {}
      try
      {
        mAdProxyLoader = new DexClassLoader(localFile.getAbsolutePath(), mDexOptPath, null, getClass().getClassLoader());
      }
      catch (Throwable localThrowable) {}
    }
    mLoaderDexTimer += 1;
    mAdProxyLoader = null;
    return;
    return;
  }
  
  private void onFailed(int paramInt, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onFailed:");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" errorno:");
    localStringBuilder.append(paramInt);
    localStringBuilder.toString();
  }
  
  private void updatePlugin(boolean paramBoolean)
  {
    for (;;)
    {
      try
      {
        if ((this.mPluginSystem != null) && (!this.mHasLoadPlugin))
        {
          this.mQbPluginCallback = new IQBPluginSystemCallback()
          {
            public void onDownloadCreateed(String paramAnonymousString1, String paramAnonymousString2) {}
            
            public void onDownloadProgress(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2) {}
            
            public void onDownloadStart(String paramAnonymousString, int paramAnonymousInt) {}
            
            public void onDownloadSuccessed(String paramAnonymousString1, String paramAnonymousString2) {}
            
            public void onNeedDownloadNotify(String paramAnonymousString, boolean paramAnonymousBoolean) {}
            
            public void onPrepareFinished(String paramAnonymousString, final QBPluginItemInfo paramAnonymousQBPluginItemInfo, int paramAnonymousInt1, int paramAnonymousInt2)
            {
              if ((paramAnonymousInt2 == 0) && (paramAnonymousQBPluginItemInfo != null))
              {
                BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
                {
                  public void doRun()
                  {
                    Object localObject1 = paramAnonymousQBPluginItemInfo;
                    if (localObject1 != null)
                    {
                      if (TextUtils.isEmpty(((QBPluginItemInfo)localObject1).mUnzipDir)) {
                        return;
                      }
                      localObject1 = new StringBuilder();
                      ((StringBuilder)localObject1).append("plugin install success mUnzipDir:");
                      ((StringBuilder)localObject1).append(paramAnonymousQBPluginItemInfo.mUnzipDir);
                      ((StringBuilder)localObject1).append(" Version:");
                      ((StringBuilder)localObject1).append(paramAnonymousQBPluginItemInfo.mVersion);
                      ((StringBuilder)localObject1).append(" md5:");
                      ((StringBuilder)localObject1).append(paramAnonymousQBPluginItemInfo.mMd5);
                      ((StringBuilder)localObject1).toString();
                      localObject1 = new StringBuilder();
                      ((StringBuilder)localObject1).append(paramAnonymousQBPluginItemInfo.mUnzipDir);
                      ((StringBuilder)localObject1).append("_");
                      ((StringBuilder)localObject1).append(paramAnonymousQBPluginItemInfo.mVersion);
                      ((StringBuilder)localObject1).append("_");
                      ((StringBuilder)localObject1).append(paramAnonymousQBPluginItemInfo.mMd5);
                      localObject1 = ((StringBuilder)localObject1).toString();
                      localObject2 = new File((String)localObject1);
                      if (!((File)localObject2).exists())
                      {
                        ((File)localObject2).mkdirs();
                        if (!FileUtils.copyFolder(paramAnonymousQBPluginItemInfo.mUnzipDir, (String)localObject1)) {
                          return;
                        }
                      }
                    }
                    try
                    {
                      LinuxToolsJni localLinuxToolsJni = new LinuxToolsJni();
                      if (TbsMode.TBSISQB()) {
                        break label220;
                      }
                      localLinuxToolsJni.Chmod(((File)localObject2).getAbsolutePath(), "755");
                    }
                    catch (Exception localException)
                    {
                      for (;;) {}
                    }
                    catch (Error localError)
                    {
                      label220:
                      for (;;) {}
                    }
                    Object localObject2 = new StringBuilder();
                    ((StringBuilder)localObject2).append("plugin install success path:");
                    ((StringBuilder)localObject2).append((String)localObject1);
                    ((StringBuilder)localObject2).toString();
                    PublicSettingManager.getInstance().setAdPluginPath((String)localObject1);
                    if (TbsAdProxy.mAdProxyLoader != null) {
                      return;
                    }
                    TbsAdProxy.access$202(TbsAdProxy.this, (String)localObject1);
                    TbsAdProxy.access$302((String)localObject1);
                    return;
                  }
                });
                return;
              }
              if ((paramAnonymousInt2 != 3014) && (paramAnonymousInt2 != 6008))
              {
                if (paramAnonymousInt2 == 3010)
                {
                  TbsAdProxy.this.onFailed(paramAnonymousInt2, "plugin install, user canceled");
                  return;
                }
                TbsAdProxy.this.onFailed(paramAnonymousInt2, "plugin install, unkonw error");
                return;
              }
              TbsAdProxy.this.onFailed(paramAnonymousInt2, "plugin install,  no space");
            }
            
            public void onPrepareStart(String paramAnonymousString) {}
          };
          if (TbsMode.TBSISQB())
          {
            i = 1;
            if (paramBoolean) {
              this.mPluginSystem.usePluginAsync("com.tencent.tbs.ad.plugin", 1, this.mQbPluginCallback, null, null, i);
            } else {
              this.mPluginSystem.LoadLocalPlugin("com.tencent.tbs.ad.plugin", this.mQbPluginCallback, null, i);
            }
            this.mHasLoadPlugin = true;
          }
        }
        else
        {
          return;
        }
      }
      finally {}
      int i = 2;
    }
  }
  
  public void adResearch(Context paramContext, boolean paramBoolean, String paramString1, String paramString2)
  {
    if (!this.isAdResearchInRatioControlInitied)
    {
      this.isAdResearchInRatioControlInitied = true;
      this.adResearchInRatioControl = PublicSettingManager.getInstance().satisfyRatioControl(PublicSettingManager.getInstance().getCloudStringSwitch("TBS_AD_COMPETITOR_REPORT_RATIO_CONTROL", "[1,0]"));
    }
    if (!this.adResearchInRatioControl) {
      return;
    }
    initDex(false);
    if (mAdProxyLoader == null) {
      return;
    }
    try
    {
      paramContext = getAdBridgeMethod(paramContext, "adResearch", new Class[] { Boolean.TYPE, String.class, String.class });
      if (paramContext == null) {
        return;
      }
      paramContext.invoke(this.mTbsAdBridge, new Object[] { Boolean.valueOf(paramBoolean), paramString1, paramString2 });
      return;
    }
    catch (Throwable paramContext)
    {
      paramString1 = new StringBuilder();
      paramString1.append("adResearch Throwable = ");
      paramString1.append(paramContext.toString());
      paramString1.toString();
      return;
    }
    catch (NoSuchMethodException paramContext)
    {
      paramString1 = new StringBuilder();
      paramString1.append("NoSuchMethodException:");
      paramString1.append(paramContext.toString());
      paramString1.toString();
      return;
    }
    catch (ClassNotFoundException paramContext)
    {
      paramString1 = new StringBuilder();
      paramString1.append("ClassNotFoundException:");
      paramString1.append(paramContext.toString());
      paramString1.toString();
    }
  }
  
  public boolean canShowScreenAd(Context paramContext, String paramString, IX5WebView paramIX5WebView)
  {
    if (isUrlMatchDomainListWithPattern(paramString, 327)) {
      return false;
    }
    if (!isUrlMatchDomainListWithPattern(paramString, 326)) {
      return false;
    }
    return isOutFrequencyControl(paramContext, paramString, paramIX5WebView);
  }
  
  public boolean canShowScreenAdWhenBackEvent(Context paramContext, String paramString, IX5WebView paramIX5WebView)
  {
    if (isUrlMatchDomainListWithPattern(paramString, 342)) {
      return false;
    }
    if (!isUrlMatchDomainListWithPattern(paramString, 341)) {
      return false;
    }
    return isOutFrequencyControl(paramContext, paramString, paramIX5WebView);
  }
  
  public InputStream getDomDistillerJS()
  {
    initDex(false);
    Object localObject = this.mPluginUnZipPath;
    if (localObject == null) {
      return null;
    }
    localObject = new File((String)localObject, "domdistiller.js");
    if (!((File)localObject).exists()) {
      return null;
    }
    try
    {
      localObject = new FileInputStream((File)localObject);
      return (InputStream)localObject;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public InputStream getTbsAdReinstallTipJS()
  {
    initDex(false);
    Object localObject = this.mPluginUnZipPath;
    if (localObject == null) {
      return null;
    }
    localObject = new File((String)localObject, "tbs_reinstall_tips.js");
    if (!((File)localObject).exists()) {
      return null;
    }
    try
    {
      localObject = new FileInputStream((File)localObject);
      return (InputStream)localObject;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public InputStream getTbsAutoInsertAdJS()
  {
    initDex(false);
    if (this.mPluginUnZipPath == null) {
      return null;
    }
    Object localObject = getDomDistillerJS();
    if (localObject != null) {
      return (InputStream)localObject;
    }
    localObject = new File(this.mPluginUnZipPath, "tbs_autoad.js");
    if (!((File)localObject).exists()) {
      return null;
    }
    try
    {
      localObject = new FileInputStream((File)localObject);
      return (InputStream)localObject;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public void hideAdByType(Context paramContext, int paramInt, IX5WebView paramIX5WebView)
  {
    if (mAdProxyLoader != null)
    {
      if (this.mTbsAdBridge == null) {
        return;
      }
      try
      {
        paramContext = getAdBridgeMethod(paramContext, "hideAdByType", new Class[] { Integer.TYPE, IX5WebView.class });
        if (paramContext == null) {
          return;
        }
        paramContext = paramContext.invoke(this.mTbsAdBridge, new Object[] { Integer.valueOf(paramInt), paramIX5WebView });
        paramIX5WebView = new StringBuilder();
        paramIX5WebView.append("hideAdByType:");
        paramIX5WebView.append(paramContext);
        paramIX5WebView.toString();
        return;
      }
      catch (Throwable paramContext)
      {
        Log.e("adProxy", paramContext.toString());
        return;
      }
      catch (NoSuchMethodException paramContext)
      {
        paramIX5WebView = new StringBuilder();
        paramIX5WebView.append("NoSuchMethodException:");
        paramIX5WebView.append(paramContext.toString());
        Log.e("adProxy", paramIX5WebView.toString());
        return;
      }
      catch (ClassNotFoundException paramContext)
      {
        paramIX5WebView = new StringBuilder();
        paramIX5WebView.append("ClassNotFoundException:");
        paramIX5WebView.append(paramContext.toString());
        Log.e("adProxy", paramIX5WebView.toString());
        return;
      }
    }
  }
  
  public void hideAdByUid(Context paramContext, int paramInt)
  {
    if (mAdProxyLoader != null)
    {
      if (this.mTbsAdBridge == null) {
        return;
      }
      try
      {
        paramContext = getAdBridgeMethod(paramContext, "hideAdByUid", new Class[] { Integer.TYPE });
        if (paramContext == null) {
          return;
        }
        paramContext = paramContext.invoke(this.mTbsAdBridge, new Object[] { Integer.valueOf(paramInt) });
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("hideAdByUid:");
        localStringBuilder.append(paramContext);
        localStringBuilder.toString();
        return;
      }
      catch (Throwable paramContext)
      {
        Log.e("adProxy", paramContext.toString());
        return;
      }
      catch (NoSuchMethodException paramContext)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("NoSuchMethodException:");
        localStringBuilder.append(paramContext.toString());
        Log.e("adProxy", localStringBuilder.toString());
        return;
      }
      catch (ClassNotFoundException paramContext)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("ClassNotFoundException:");
        localStringBuilder.append(paramContext.toString());
        Log.e("adProxy", localStringBuilder.toString());
        return;
      }
    }
  }
  
  public int hideScreenAd(Context paramContext, Object paramObject)
  {
    if ((paramObject instanceof IX5WebView))
    {
      hideScreenAd(paramContext, (IX5WebView)paramObject);
      return 0;
    }
    return -2;
  }
  
  public void hideScreenAd(Context paramContext, IX5WebView paramIX5WebView)
  {
    if (mAdProxyLoader != null)
    {
      if (this.mTbsAdBridge == null) {
        return;
      }
      try
      {
        paramContext = getAdBridgeMethod(paramContext, "hideScreenAd", new Class[] { IX5WebView.class });
        if (paramContext == null) {
          return;
        }
        paramContext = paramContext.invoke(this.mTbsAdBridge, new Object[] { paramIX5WebView });
        paramIX5WebView = new StringBuilder();
        paramIX5WebView.append("hideScreenAd:");
        paramIX5WebView.append(paramContext);
        paramIX5WebView.toString();
        return;
      }
      catch (Throwable paramContext)
      {
        Log.e("adProxy", paramContext.toString());
        return;
      }
      catch (NoSuchMethodException paramContext)
      {
        paramIX5WebView = new StringBuilder();
        paramIX5WebView.append("NoSuchMethodException:");
        paramIX5WebView.append(paramContext.toString());
        Log.e("adProxy", paramIX5WebView.toString());
        return;
      }
      catch (ClassNotFoundException paramContext)
      {
        paramIX5WebView = new StringBuilder();
        paramIX5WebView.append("ClassNotFoundException:");
        paramIX5WebView.append(paramContext.toString());
        Log.e("adProxy", paramIX5WebView.toString());
        return;
      }
    }
  }
  
  public void init(Context paramContext, DexClassLoader paramDexClassLoader, String paramString1, String paramString2)
  {
    mBaseLoader = paramDexClassLoader;
    mTbsInstallLocation = paramString1;
    mDexOptPath = paramString2;
    if (this.mPluginSystem == null) {
      this.mPluginSystem = QBPluginSystem.getInstance(paramContext);
    }
    initDex(true);
  }
  
  public boolean isOutFrequencyControl(Context paramContext, String paramString, IX5WebView paramIX5WebView)
  {
    initDex(false);
    if (mAdProxyLoader == null) {
      return false;
    }
    try
    {
      Method localMethod = getAdBridgeMethod(paramIX5WebView.getView().getContext(), "isOutFrequencyControl", new Class[] { Context.class, String.class, IX5WebView.class });
      if (localMethod == null) {
        return false;
      }
      paramContext = localMethod.invoke(this.mTbsAdBridge, new Object[] { paramContext, paramString, paramIX5WebView });
      paramString = new StringBuilder();
      paramString.append("isOutFrequencyControl ret:");
      paramString.append(paramContext);
      paramString.toString();
      if ((paramContext instanceof Boolean))
      {
        boolean bool = ((Boolean)paramContext).booleanValue();
        return bool;
      }
    }
    catch (Throwable paramContext)
    {
      Log.e("adProxy", paramContext.toString());
      return false;
    }
    catch (NoSuchMethodException paramContext)
    {
      paramString = new StringBuilder();
      paramString.append("NoSuchMethodException:");
      paramString.append(paramContext.toString());
      Log.e("adProxy", paramString.toString());
      return false;
    }
    catch (ClassNotFoundException paramContext)
    {
      paramString = new StringBuilder();
      paramString.append("ClassNotFoundException:");
      paramString.append(paramContext.toString());
      Log.e("adProxy", paramString.toString());
    }
    return false;
  }
  
  public boolean isScreenAdDomainWhiteListMatched(String paramString)
  {
    return (isUrlMatchDomainListWithPattern(paramString, 326)) || (isUrlMatchDomainListWithPattern(paramString, 341));
  }
  
  public boolean isUrlMatchDomainListWithPattern(String paramString, int paramInt)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    try
    {
      Object localObject1 = new URL(paramString);
      paramString = ((URL)localObject1).getHost();
      localObject1 = ((URL)localObject1).getPath();
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("isUrlMatchDomainListWithPattern host:");
      ((StringBuilder)localObject2).append(paramString);
      ((StringBuilder)localObject2).append(" path:");
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).toString();
      if (TextUtils.isEmpty(paramString)) {
        return false;
      }
      Object localObject3 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(paramInt);
      localObject2 = new DomainMatcher();
      if ((localObject3 != null) && (((ArrayList)localObject3).size() > 0))
      {
        localObject3 = ((ArrayList)localObject3).iterator();
        while (((Iterator)localObject3).hasNext())
        {
          String str = (String)((Iterator)localObject3).next();
          Object localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("isUrlMatchDomainListWithPattern item:");
          ((StringBuilder)localObject4).append(str);
          ((StringBuilder)localObject4).toString();
          if (str.indexOf('/') > 0)
          {
            localObject4 = str.substring(0, str.indexOf('/'));
            str = str.substring(str.indexOf('/'));
            if ((!TextUtils.isEmpty((CharSequence)localObject1)) && (((String)localObject1).startsWith(str))) {
              ((DomainMatcher)localObject2).addDomain((String)localObject4);
            }
          }
          else
          {
            ((DomainMatcher)localObject2).addDomain(str);
          }
        }
        return ((DomainMatcher)localObject2).isContainsDomain(paramString);
      }
      return false;
    }
    catch (Exception paramString)
    {
      paramString.toString();
    }
    return false;
  }
  
  public void onWebviewStatusChange(IX5WebView paramIX5WebView, int paramInt, JSONObject paramJSONObject)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onWebviewStatusChange status:");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).toString();
    initDex(false);
    if (mAdProxyLoader == null) {
      return;
    }
    try
    {
      localObject = getAdBridgeMethod(paramIX5WebView.getView().getContext(), "onWebviewStatusChange", new Class[] { IX5WebView.class, Integer.TYPE, JSONObject.class });
      if (localObject == null) {
        return;
      }
      ((Method)localObject).invoke(this.mTbsAdBridge, new Object[] { paramIX5WebView, Integer.valueOf(paramInt), paramJSONObject });
      return;
    }
    catch (Throwable paramIX5WebView)
    {
      paramJSONObject = new StringBuilder();
      paramJSONObject.append("onWebviewStatusChange Throwable = ");
      paramJSONObject.append(paramIX5WebView.toString());
      paramJSONObject.toString();
      return;
    }
    catch (NoSuchMethodException paramJSONObject)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("NoSuchMethodException:");
      ((StringBuilder)localObject).append(paramJSONObject.toString());
      ((StringBuilder)localObject).toString();
      try
      {
        paramJSONObject = getAdBridgeMethod(paramIX5WebView.getView().getContext(), "onWebviewStatusChange", new Class[] { IX5WebView.class, Integer.TYPE });
        if (paramJSONObject == null) {
          return;
        }
        paramJSONObject.invoke(this.mTbsAdBridge, new Object[] { paramIX5WebView, Integer.valueOf(paramInt) });
        return;
      }
      catch (Throwable paramIX5WebView)
      {
        paramIX5WebView.toString();
        return;
      }
      catch (NoSuchMethodException paramIX5WebView)
      {
        paramJSONObject = new StringBuilder();
        paramJSONObject.append("NoSuchMethodException:");
        paramJSONObject.append(paramIX5WebView.toString());
        paramJSONObject.toString();
        return;
      }
      catch (ClassNotFoundException paramIX5WebView)
      {
        paramJSONObject = new StringBuilder();
        paramJSONObject.append("ClassNotFoundException:");
        paramJSONObject.append(paramIX5WebView.toString());
        paramJSONObject.toString();
        return;
      }
    }
    catch (ClassNotFoundException paramIX5WebView)
    {
      paramJSONObject = new StringBuilder();
      paramJSONObject.append("ClassNotFoundException:");
      paramJSONObject.append(paramIX5WebView.toString());
      paramJSONObject.toString();
    }
  }
  
  public View openLandPage(Context paramContext, String paramString, ViewGroup paramViewGroup)
  {
    initDex(false);
    if (mAdProxyLoader == null) {
      return null;
    }
    try
    {
      Method localMethod = getAdBridgeMethod(paramContext, "openLandPage", new Class[] { Context.class, String.class, ViewGroup.class });
      if (localMethod == null) {
        return null;
      }
      paramContext = localMethod.invoke(this.mTbsAdBridge, new Object[] { paramContext, paramString, paramViewGroup });
      paramString = new StringBuilder();
      paramString.append("openLandPage ret:");
      paramString.append(paramContext);
      paramString.toString();
      if ((paramContext instanceof ViewGroup))
      {
        paramContext = (ViewGroup)paramContext;
        return paramContext;
      }
    }
    catch (Throwable paramContext)
    {
      Log.e("adProxy", paramContext.toString());
      paramContext.printStackTrace();
      return null;
    }
    catch (NoSuchMethodException paramContext)
    {
      paramString = new StringBuilder();
      paramString.append("NoSuchMethodException:");
      paramString.append(paramContext.toString());
      Log.e("adProxy", paramString.toString());
      paramContext.printStackTrace();
      return null;
    }
    catch (ClassNotFoundException paramContext)
    {
      paramString = new StringBuilder();
      paramString.append("ClassNotFoundException:");
      paramString.append(paramContext.toString());
      Log.e("adProxy", paramString.toString());
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public int preLoadScreenAd(Context paramContext, String paramString)
  {
    if (this.mPluginSystem == null) {
      this.mPluginSystem = QBPluginSystem.getInstance(paramContext);
    }
    initDex(false);
    if (mAdProxyLoader == null) {
      return -1;
    }
    try
    {
      paramContext = getAdBridgeMethod(paramContext, "preLoadScreenAd", new Class[] { String.class });
      if (paramContext == null) {
        return -2;
      }
      paramContext = paramContext.invoke(this.mTbsAdBridge, new Object[] { paramString });
      paramString = new StringBuilder();
      paramString.append("preLoad:");
      paramString.append(paramContext);
      paramString.toString();
      if ((paramContext instanceof Integer))
      {
        int i = ((Integer)paramContext).intValue();
        return i;
      }
      return 0;
    }
    catch (Throwable paramContext)
    {
      Log.e("adProxy", paramContext.toString());
      return -5;
    }
    catch (NoSuchMethodException paramContext)
    {
      paramString = new StringBuilder();
      paramString.append("NoSuchMethodException:");
      paramString.append(paramContext.toString());
      Log.e("adProxy", paramString.toString());
      return -4;
    }
    catch (ClassNotFoundException paramContext)
    {
      paramString = new StringBuilder();
      paramString.append("ClassNotFoundException:");
      paramString.append(paramContext.toString());
      Log.e("adProxy", paramString.toString());
    }
    return -3;
  }
  
  public int showAd(Context paramContext, int paramInt, IX5WebView paramIX5WebView, View paramView)
  {
    initDex(false);
    if (mAdProxyLoader == null) {
      return -1;
    }
    try
    {
      Method localMethod = getAdBridgeMethod(paramContext, "showAdByType", new Class[] { Context.class, Integer.TYPE, IX5WebView.class, View.class });
      if (localMethod == null) {
        return -2;
      }
      paramContext = localMethod.invoke(this.mTbsAdBridge, new Object[] { paramContext, Integer.valueOf(paramInt), paramIX5WebView, paramView });
      paramIX5WebView = new StringBuilder();
      paramIX5WebView.append("showScreenAd:");
      paramIX5WebView.append(paramContext);
      paramIX5WebView.toString();
      if ((paramContext instanceof Integer))
      {
        paramInt = ((Integer)paramContext).intValue();
        return paramInt;
      }
    }
    catch (Throwable paramContext)
    {
      Log.e("adProxy", paramContext.toString());
    }
    catch (NoSuchMethodException paramContext)
    {
      paramIX5WebView = new StringBuilder();
      paramIX5WebView.append("NoSuchMethodException:");
      paramIX5WebView.append(paramContext.toString());
      Log.e("adProxy", paramIX5WebView.toString());
    }
    catch (ClassNotFoundException paramContext)
    {
      paramIX5WebView = new StringBuilder();
      paramIX5WebView.append("ClassNotFoundException:");
      paramIX5WebView.append(paramContext.toString());
      Log.e("adProxy", paramIX5WebView.toString());
    }
    return -3;
  }
  
  public int showScreenAd(Context paramContext, IX5WebView paramIX5WebView, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    initDex(false);
    if (mAdProxyLoader == null) {
      return -1;
    }
    try
    {
      Method localMethod = getAdBridgeMethod(paramContext, "showScreenAd", new Class[] { Context.class, IX5WebView.class, String.class, Boolean.TYPE, Boolean.TYPE });
      if (localMethod == null) {
        return -2;
      }
      paramContext = localMethod.invoke(this.mTbsAdBridge, new Object[] { paramContext, paramIX5WebView, paramString, Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2) });
      paramIX5WebView = new StringBuilder();
      paramIX5WebView.append("showScreenAd:");
      paramIX5WebView.append(paramContext);
      paramIX5WebView.toString();
      if ((paramContext instanceof Integer))
      {
        int i = ((Integer)paramContext).intValue();
        return i;
      }
    }
    catch (Throwable paramContext)
    {
      Log.e("adProxy", paramContext.toString());
      return -2;
    }
    catch (NoSuchMethodException paramContext)
    {
      paramIX5WebView = new StringBuilder();
      paramIX5WebView.append("NoSuchMethodException:");
      paramIX5WebView.append(paramContext.toString());
      Log.e("adProxy", paramIX5WebView.toString());
      return -2;
    }
    catch (ClassNotFoundException paramContext)
    {
      paramIX5WebView = new StringBuilder();
      paramIX5WebView.append("ClassNotFoundException:");
      paramIX5WebView.append(paramContext.toString());
      Log.e("adProxy", paramIX5WebView.toString());
    }
    return -2;
  }
  
  public int showScreenAd(Context paramContext, Object paramObject, String paramString)
  {
    if ((paramObject instanceof IX5WebView)) {
      return showScreenAd(paramContext, (IX5WebView)paramObject, paramString, true, false);
    }
    return -1;
  }
  
  public void snapshotScreenAd(Bitmap paramBitmap, float paramFloat1, float paramFloat2, IX5WebView paramIX5WebView)
  {
    if (mAdProxyLoader != null)
    {
      if (this.mTbsAdBridge == null) {
        return;
      }
      try
      {
        Method localMethod = getAdBridgeMethod(paramIX5WebView.getView().getContext(), "snapshotVisibleWithBitmap", new Class[] { Bitmap.class, Float.TYPE, Float.TYPE, IX5WebView.class });
        if (localMethod == null) {
          return;
        }
        paramBitmap = localMethod.invoke(this.mTbsAdBridge, new Object[] { paramBitmap, Float.valueOf(paramFloat1), Float.valueOf(paramFloat2), paramIX5WebView });
        paramIX5WebView = new StringBuilder();
        paramIX5WebView.append("snapshotScreenAd:");
        paramIX5WebView.append(paramBitmap);
        paramIX5WebView.toString();
        return;
      }
      catch (Throwable paramBitmap)
      {
        Log.e("adProxy", paramBitmap.toString());
        return;
      }
      catch (NoSuchMethodException paramBitmap)
      {
        paramIX5WebView = new StringBuilder();
        paramIX5WebView.append("NoSuchMethodException:");
        paramIX5WebView.append(paramBitmap.toString());
        Log.e("adProxy", paramIX5WebView.toString());
        return;
      }
      catch (ClassNotFoundException paramBitmap)
      {
        paramIX5WebView = new StringBuilder();
        paramIX5WebView.append("ClassNotFoundException:");
        paramIX5WebView.append(paramBitmap.toString());
        Log.e("adProxy", paramIX5WebView.toString());
        return;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\ad\TbsAdProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */