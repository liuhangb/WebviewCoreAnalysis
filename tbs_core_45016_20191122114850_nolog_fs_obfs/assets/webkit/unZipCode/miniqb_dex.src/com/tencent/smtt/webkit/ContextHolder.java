package com.tencent.smtt.webkit;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build.VERSION;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.tencent.common.utils.QBSoLoader;
import com.tencent.smtt.export.external.LibraryLoader;
import com.tencent.smtt.export.external.libwebp;
import com.tencent.smtt.os.FileUtils;
import com.tencent.smtt.os.SMTTAdaptation;
import com.tencent.smtt.util.MttTraceEvent;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.partner.crashmonitor.CrashMonitor;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.UsedByReflection;
import org.chromium.tencent.base.TencentPathUtils;

@JNINamespace("tencent")
public class ContextHolder
{
  private static final String APP_DEMO = "com.tencent.tbs";
  private static final String APP_DEMO_TEST = "com.tencent.mtt.sdk.test";
  private static final String APP_QB = "com.tencent.mtt";
  public static final String APP_QQ = "com.tencent.mobileqq";
  public static final String APP_QZONE = "com.qzone";
  public static final String APP_WX = "com.tencent.mm";
  public static final int ERROR_CODE_CLASS_NOT_FOUND = 3002;
  public static final int ERROR_CODE_JNI_REGISTER_ERROR = 8001;
  public static final int ERROR_CODE_LINKAGE_ERROR = 3001;
  public static final int ERROR_CODE_NO_CLASS_DEF_FOUND = 3003;
  private static String LOAD_V8_FOR_CORE_FAILED = "LOAD_V8_FOR_CORE_FAILED";
  private static final String TAG = "ContextHolder";
  private static final String WEBVIEW_TINKER_PREFS_NAME = "WebViewTinkerPrefs";
  private static Context mAppContext;
  private static Context mHostContext;
  private static int mSdkInt = 0;
  private static ContextHolder sInstance;
  private static final String v8Name = "libmttv8.so";
  private ConnectivityManager mConnectivityManager;
  private ConcurrentHashMap<Long, WeakReference<Context>> mContextMap = new ConcurrentHashMap();
  private WeakReference<Context> mCurrentContext;
  private String mDataPath;
  private DexClassLoader mDexLoader = null;
  private String mDexOutPutDir;
  private String mDynamicLibFolderPath;
  private boolean mIsTbsDevelopMode = false;
  private String[] mLibrarySearchPaths;
  private String mPackageName;
  private int mSDKVerison;
  private Map<String, Object> mSettings = null;
  private String mTbsCoreVersion = null;
  
  private ContextHolder()
  {
    mAppContext = null;
    mHostContext = null;
  }
  
  public static String getDataPathJNI()
  {
    return getInstance().mDataPath;
  }
  
  public static DisplayMetrics getDisplayMetrics()
  {
    if (getInstance().getContext() != null) {
      return getInstance().getContext().getResources().getDisplayMetrics();
    }
    return new DisplayMetrics();
  }
  
  public static int getDisplayOrientation()
  {
    if (getInstance().getContext() != null)
    {
      WindowManager localWindowManager = (WindowManager)getInstance().getContext().getSystemService("window");
      if (localWindowManager != null) {
        return localWindowManager.getDefaultDisplay().getOrientation();
      }
    }
    return 0;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static ContextHolder getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new ContextHolder();
      }
      ContextHolder localContextHolder = sInstance;
      return localContextHolder;
    }
    finally {}
  }
  
  public static String[] getLibrarySearchPathsJNI()
  {
    return getInstance().mLibrarySearchPaths;
  }
  
  public static String getSDCardRootPathJNI()
  {
    if (isSDCardExit()) {
      return FileUtils.a();
    }
    return null;
  }
  
  public static boolean isSDCardExit()
  {
    try
    {
      boolean bool = Environment.getExternalStorageState().equals("mounted");
      if (bool) {
        return true;
      }
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
    return false;
  }
  
  private void loadMttWebViewLib(Context paramContext, String paramString)
  {
    if (this.mPackageName.equals("com.tencent.mtt")) {
      loadSoLib(paramContext, paramString, "libmtt_shared.so");
    }
    libwebp.loadWepLibraryIfNeed(paramContext, paramString);
    if (this.mPackageName.equals("com.tencent.mtt")) {
      loadV8(paramContext, paramString);
    }
    loadSoLib(paramContext, paramString, "libmttwebview.so");
    loadSoLib(paramContext, paramString, "libmttwebview_plat_support.so");
    try
    {
      SMTTAdaptation.getIsBuildForArch64();
      CrashMonitor.getInstance().init(paramContext);
      return;
    }
    catch (UnsatisfiedLinkError paramContext)
    {
      for (;;) {}
    }
    throw new UnsatisfiedLinkError("native function call error");
  }
  
  private void loadSoLib(Context paramContext, String paramString1, String paramString2)
  {
    if (paramString1 != null)
    {
      paramContext = new StringBuilder();
      paramContext.append(paramString1);
      paramContext.append(File.separator);
      paramContext.append(paramString2);
      paramContext = QBSoLoader.tinkerSoLoadPath(paramContext.toString());
      if (TextUtils.isEmpty(paramContext))
      {
        paramContext = new StringBuilder();
        paramContext.append(paramString1);
        paramContext.append(File.separator);
        paramContext.append(paramString2);
        System.load(paramContext.toString());
        return;
      }
      if (paramString2.equalsIgnoreCase("libmttv8.so"))
      {
        loadV8OnTinkerPath(paramString1, paramContext);
        return;
      }
      System.load(paramContext);
      return;
    }
    LibraryLoader.loadLibrary(paramContext, paramString2);
  }
  
  private void loadV8(Context paramContext, String paramString)
  {
    try
    {
      loadSoLib(paramContext, paramString, "libmttv8.so");
      return;
    }
    catch (Throwable paramContext)
    {
      paramString = LOAD_V8_FOR_CORE_FAILED;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("FAILED_CORE_1: , errorMsg=");
      localStringBuilder.append(paramContext.toString());
      upLoadToBeaconForV8LoadFail(paramString, localStringBuilder.toString());
      paramContext.printStackTrace();
    }
  }
  
  private void loadV8OnTinkerPath(String paramString1, String paramString2)
  {
    SharedPreferences localSharedPreferences = getApplicationContext().getSharedPreferences("WebViewTinkerPrefs", 0);
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(paramString1);
    ((StringBuilder)localObject1).append(File.separator);
    ((StringBuilder)localObject1).append("tinker");
    paramString1 = new File(((StringBuilder)localObject1).toString());
    Object localObject2;
    if (!paramString1.exists())
    {
      paramString1.mkdir();
      localObject1 = Runtime.getRuntime();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("ln -s ");
      ((StringBuilder)localObject2).append(paramString2);
      ((StringBuilder)localObject2).append(" ");
      ((StringBuilder)localObject2).append(paramString1);
      ((StringBuilder)localObject2).append(File.separator);
      ((StringBuilder)localObject2).append("libmttv8.so");
      localObject2 = ((StringBuilder)localObject2).toString();
      try
      {
        ((Runtime)localObject1).exec((String)localObject2);
        localSharedPreferences.edit().putString("v8_tinker_path", paramString2).apply();
      }
      catch (Throwable paramString2)
      {
        paramString2.printStackTrace();
      }
    }
    else
    {
      localObject1 = localSharedPreferences.getString("v8_tinker_path", null);
      if ((TextUtils.isEmpty((CharSequence)localObject1)) || (!((String)localObject1).equals(paramString2)))
      {
        new File(paramString1, "libmttv8.so").delete();
        localObject1 = Runtime.getRuntime();
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("ln -s ");
        ((StringBuilder)localObject2).append(paramString2);
        ((StringBuilder)localObject2).append(" ");
        ((StringBuilder)localObject2).append(paramString1);
        ((StringBuilder)localObject2).append(File.separator);
        ((StringBuilder)localObject2).append("libmttv8.so");
        localObject2 = ((StringBuilder)localObject2).toString();
        try
        {
          ((Runtime)localObject1).exec((String)localObject2);
          localSharedPreferences.edit().putString("v8_tinker_path", paramString2).apply();
        }
        catch (Throwable paramString2)
        {
          paramString2.printStackTrace();
        }
      }
    }
    paramString2 = new StringBuilder();
    paramString2.append(paramString1);
    paramString2.append(File.separator);
    paramString2.append("libmttv8.so");
    System.load(paramString2.toString());
  }
  
  private static native boolean nativeRegisterJniMethod();
  
  private void setAppContext(Context paramContext)
  {
    if ((mAppContext == null) && (paramContext != null))
    {
      mAppContext = paramContext.getApplicationContext();
      e.a().a(paramContext, this.mSettings);
      paramContext = mAppContext;
      if ((paramContext != null) && (paramContext.getPackageName() != null)) {
        this.mPackageName = mAppContext.getPackageName();
      }
      mSdkInt = Build.VERSION.SDK_INT;
    }
  }
  
  private void setHostContext(Context paramContext)
  {
    if ((mHostContext == null) && (paramContext != null)) {
      mHostContext = paramContext.getApplicationContext();
    }
  }
  
  private static void upLoadToBeaconForV8LoadFail(String paramString1, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("errorMsg", paramString2);
    SmttServiceProxy.getInstance().upLoadToBeacon(paramString1, localHashMap);
  }
  
  public void associateContext(long paramLong, Context paramContext)
  {
    if ((mAppContext == null) && (paramContext != null)) {
      mAppContext = paramContext.getApplicationContext();
    }
    this.mCurrentContext = new WeakReference(paramContext);
    this.mContextMap.put(Long.valueOf(paramLong), new WeakReference(paramContext));
  }
  
  public void deAssociateContext(long paramLong)
  {
    try
    {
      this.mContextMap.remove(Long.valueOf(paramLong));
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
    }
  }
  
  public Context getApplicationContext()
  {
    if ((mAppContext == null) && (getContext() != null)) {
      mAppContext = getContext().getApplicationContext();
    }
    return mAppContext;
  }
  
  public AssetManager getAssetManager()
  {
    if (SmttResource.getResourceContext() != null) {
      return SmttResource.getResourceContext().getAssets();
    }
    return null;
  }
  
  public ConnectivityManager getConnectivityManager()
  {
    Context localContext = getContext();
    if ((this.mConnectivityManager == null) && (localContext != null)) {
      this.mConnectivityManager = ((ConnectivityManager)localContext.getSystemService("connectivity"));
    }
    return this.mConnectivityManager;
  }
  
  public Context getContext()
  {
    Object localObject = this.mContextMap.values().iterator();
    if (((Iterator)localObject).hasNext())
    {
      localObject = (Context)((WeakReference)((Iterator)localObject).next()).get();
      if (localObject == null) {
        Log.e("ContextHolder", "ContextHolder::getContext, the object weakrefer pointing to has been free");
      }
      return (Context)localObject;
    }
    return null;
  }
  
  public Context getCurrentContext()
  {
    WeakReference localWeakReference = this.mCurrentContext;
    if (localWeakReference != null) {
      return (Context)localWeakReference.get();
    }
    return null;
  }
  
  public DexClassLoader getDexClassLoader()
  {
    return this.mDexLoader;
  }
  
  public String getDynamicLibFolderPath()
  {
    return this.mDynamicLibFolderPath;
  }
  
  public String[] getLibrarySearchPaths()
  {
    return this.mLibrarySearchPaths;
  }
  
  public String getPackageName()
  {
    return this.mPackageName;
  }
  
  public int getSdkVersion()
  {
    return this.mSDKVerison;
  }
  
  public String getTbsCoreVersion()
  {
    return this.mTbsCoreVersion;
  }
  
  public Context getTbsHostContext()
  {
    return mHostContext;
  }
  
  public boolean isTbsDevelopMode()
  {
    return this.mIsTbsDevelopMode;
  }
  
  public boolean isThirdPartyApp(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getApplicationContext().getPackageName();
      if ((!paramContext.equals("com.tencent.mm")) && (!paramContext.equals("com.tencent.mtt")) && (!paramContext.equals("com.tencent.mobileqq")) && (!paramContext.equals("com.tencent.tbs")) && (!paramContext.equals("com.tencent.mtt.sdk.test")))
      {
        boolean bool = paramContext.equals("com.qzone");
        return !bool;
      }
      return false;
    }
    catch (Throwable paramContext) {}
    return false;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public boolean setContext(Context paramContext1, Context paramContext2)
  {
    setAppContext(paramContext1);
    setHostContext(paramContext2);
    this.mDataPath = paramContext1.getApplicationInfo().dataDir;
    this.mLibrarySearchPaths = LibraryLoader.getLibrarySearchPaths(paramContext1);
    try
    {
      loadMttWebViewLib(paramContext1, null);
      MttTraceEvent.setTraceEnableFlag(true);
      return true;
    }
    catch (UnsatisfiedLinkError paramContext1)
    {
      paramContext1.printStackTrace();
    }
    return false;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public boolean setContext(Context paramContext1, Context paramContext2, String paramString1, String paramString2)
  {
    setAppContext(paramContext1);
    setHostContext(paramContext2);
    this.mDataPath = paramContext1.getApplicationInfo().dataDir;
    this.mDexOutPutDir = paramString2;
    if (!TextUtils.isEmpty(paramString1))
    {
      this.mDynamicLibFolderPath = paramString1;
      TencentPathUtils.setNativeLibraryDirectory(paramString1);
      this.mLibrarySearchPaths = LibraryLoader.getLibrarySearchPaths(paramContext1);
      paramContext2 = new String[this.mLibrarySearchPaths.length + 1];
      int i = 0;
      paramContext2[0] = paramString1;
      for (;;)
      {
        paramString2 = this.mLibrarySearchPaths;
        if (i >= paramString2.length) {
          break;
        }
        int j = i + 1;
        paramContext2[j] = paramString2[i];
        i = j;
      }
      this.mLibrarySearchPaths = paramContext2;
      loadMttWebViewLib(paramContext1, paramString1);
    }
    else
    {
      this.mLibrarySearchPaths = LibraryLoader.getLibrarySearchPaths(paramContext1);
      loadMttWebViewLib(paramContext1, null);
    }
    MttTraceEvent.setTraceEnableFlag(true);
    return true;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public boolean setContext(Context paramContext1, Context paramContext2, String paramString1, String paramString2, Boolean paramBoolean)
    throws Exception
  {
    this.mIsTbsDevelopMode = paramBoolean.booleanValue();
    return setContext(paramContext1, paramContext2, paramString1, paramString2);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public boolean setContext(Context paramContext1, Context paramContext2, String paramString1, String paramString2, Boolean paramBoolean, DexClassLoader paramDexClassLoader, Map<String, Object> paramMap)
    throws Exception
  {
    this.mSettings = paramMap;
    this.mIsTbsDevelopMode = paramBoolean.booleanValue();
    this.mDexLoader = paramDexClassLoader;
    return setContext(paramContext1, paramContext2, paramString1, paramString2);
  }
  
  public void setLibrarySearchPaths(String[] paramArrayOfString)
  {
    this.mLibrarySearchPaths = paramArrayOfString;
  }
  
  public void setSdkVersion(int paramInt)
  {
    this.mSDKVerison = paramInt;
  }
  
  public void setTbsDevelopMode(boolean paramBoolean)
  {
    this.mIsTbsDevelopMode = paramBoolean;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setTbsVersionInfo(int paramInt, String paramString)
  {
    this.mSDKVerison = paramInt;
    this.mTbsCoreVersion = paramString;
    CrashMonitor.getInstance().setTbsVersion(paramString, paramInt);
  }
  
  public void updateContext(Context paramContext)
  {
    Enumeration localEnumeration = this.mContextMap.keys();
    while (localEnumeration.hasMoreElements())
    {
      Long localLong = (Long)localEnumeration.nextElement();
      this.mContextMap.put(localLong, new WeakReference(paramContext));
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ContextHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */