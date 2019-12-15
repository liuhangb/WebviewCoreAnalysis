package com.tencent.tbs.tbsshell.partner.ug;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import com.tencent.common.plugin.IQBPluginSystemCallback;
import com.tencent.common.plugin.QBPluginItemInfo;
import com.tencent.common.plugin.QBPluginServiceImpl;
import com.tencent.common.plugin.QBPluginSystem;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.ReflectionUtils;
import com.tencent.common.utils.TbsMode;
import com.tencent.mtt.ContextHolder;
import com.tencent.mtt.base.utils.PackageUtils;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder.DownloadListener;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder.IntentFilterCallback;
import com.tencent.tbs.tbsshell.common.ISmttServicePartner;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class TbsUgEngine
{
  private static boolean DEBUG_FLAG = false;
  public static final String DIALOG_TYPE_HW = "hw";
  public static final String DIALOG_TYPE_HW_SEARCH = "hwsearch";
  private static final String PLUGIN_JAR_NAME = "tbs_ug_plugin.jar";
  private static final String PLUGIN_JAR_NAME_NEW = "tbs_ug_plugin_new.jar";
  private static final String PLUGIN_PACKAGE_NAME = "com.tencent.tbs.ug";
  private static final String TAG = "TbsUgEngine";
  private static final String TBS_FOLDER_NAME = "tbs";
  private static final String TBS_SHARE_NAME = "share";
  private static final String TBS_UG_NAME = "ug";
  private static final String TBS_UG_VERSION = "tbsug_version";
  private static TbsUgEngine inst = new TbsUgEngine();
  private boolean is_plugin_load_success = false;
  private ClassLoader mBaseClassLoader;
  private DexClassLoader mClassLoader;
  private String mCoreShareDir;
  private String mInstallLocation;
  private String mPluginPath;
  private QBPluginSystem mPluginSystem;
  private ITbsService mTbsService;
  private ITbsUg mTbsUg;
  
  private TbsUgEngine()
  {
    DEBUG_FLAG = false;
  }
  
  private int compareVersion(String paramString1, String paramString2)
  {
    String str2 = getTbsUgConfValue(paramString1, "tbsug_version");
    String str1 = getTbsUgConfValue(paramString2, "tbsug_version");
    paramString1 = str2;
    if (str2 == null) {
      paramString1 = "000000";
    }
    paramString2 = str1;
    if (str1 == null) {
      paramString2 = "000000";
    }
    return paramString1.compareTo(paramString2);
  }
  
  private Context createHostContext(String paramString)
  {
    try
    {
      paramString = ContextHolder.getAppContext().createPackageContext(paramString, 2);
      return paramString;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return null;
  }
  
  private DexClassLoader createtDexLoader(ClassLoader paramClassLoader, String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("@");
    localStringBuilder.append(Thread.currentThread().getStackTrace()[2].getMethodName());
    localStringBuilder.append("...");
    Log.w("TbsUgEngine", localStringBuilder.toString());
    if (paramClassLoader != null)
    {
      if (paramString1 == null) {
        return null;
      }
      try
      {
        paramClassLoader = new DexClassLoader(paramString1, paramString2, null, paramClassLoader);
        return paramClassLoader;
      }
      catch (Throwable paramClassLoader)
      {
        paramClassLoader.printStackTrace();
        return null;
      }
    }
    return null;
  }
  
  private void downloadPlugin(final PluginLoadCallback paramPluginLoadCallback)
  {
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("@");
    ((StringBuilder)localObject1).append(Thread.currentThread().getStackTrace()[2].getMethodName());
    ((StringBuilder)localObject1).append("...");
    Log.w("TbsUgEngine", ((StringBuilder)localObject1).toString());
    Object localObject4 = "null0";
    localObject1 = localObject4;
    try
    {
      JSONObject localJSONObject = QBPluginServiceImpl.getInstance().pluginsToJson(2);
      localObject1 = localObject4;
      Iterator localIterator = localJSONObject.keys();
      do
      {
        localObject2 = localObject4;
        localObject1 = localObject4;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject1 = localObject4;
        localObject2 = localJSONObject.getJSONArray(localIterator.next().toString());
        localObject1 = localObject4;
      } while (!TextUtils.equals(((JSONArray)localObject2).getString(3), "com.tencent.tbs.ug"));
      localObject1 = localObject4;
      localObject2 = ((JSONArray)localObject2).getString(5);
      localObject1 = localObject2;
      localObject4 = new StringBuilder();
      localObject1 = localObject2;
      ((StringBuilder)localObject4).append("tbsug_plunin_list_version:");
      localObject1 = localObject2;
      ((StringBuilder)localObject4).append((String)localObject2);
      localObject1 = localObject2;
      ((StringBuilder)localObject4).toString();
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Object localObject2;
        Object localObject3 = localObject1;
      }
    }
    if (shouldUpload())
    {
      localObject1 = X5CoreBeaconUploader.getInstance(ContextHolder.getAppContext());
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append("BZUGPLV_");
      ((StringBuilder)localObject4).append((String)localObject2);
      ((X5CoreBeaconUploader)localObject1).userBehaviorStatistics(((StringBuilder)localObject4).toString());
    }
    paramPluginLoadCallback = new IQBPluginSystemCallback()
    {
      private boolean mNewPluginDownloading = false;
      
      public void onDownloadCreateed(String paramAnonymousString1, String paramAnonymousString2)
      {
        paramAnonymousString1 = new StringBuilder();
        paramAnonymousString1.append("@");
        paramAnonymousString1.append(Thread.currentThread().getStackTrace()[2].getMethodName());
        paramAnonymousString1.append("...");
        Log.w("TbsUgEngine", paramAnonymousString1.toString());
      }
      
      public void onDownloadProgress(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadStart :: ");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.append(", progress: ");
        localStringBuilder.append(paramAnonymousInt2);
        localStringBuilder.toString();
        paramPluginLoadCallback.onDownloadProgress(paramAnonymousInt2);
      }
      
      public void onDownloadStart(String paramAnonymousString, int paramAnonymousInt)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadStart :: ");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.append(", totalSize: ");
        localStringBuilder.append(paramAnonymousInt);
        localStringBuilder.toString();
        TbsUgEngine.this.uploadBeacon("008");
        this.mNewPluginDownloading = true;
        paramPluginLoadCallback.onDownloadStart(paramAnonymousInt);
      }
      
      public void onDownloadSuccessed(String paramAnonymousString1, String paramAnonymousString2)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadStart :: ");
        localStringBuilder.append(paramAnonymousString1);
        localStringBuilder.append(", filePath: ");
        localStringBuilder.append(paramAnonymousString2);
        localStringBuilder.toString();
        paramPluginLoadCallback.onInstallStart();
      }
      
      public void onNeedDownloadNotify(String paramAnonymousString, boolean paramAnonymousBoolean)
      {
        paramAnonymousString = new StringBuilder();
        paramAnonymousString.append("@");
        paramAnonymousString.append(Thread.currentThread().getStackTrace()[2].getMethodName());
        paramAnonymousString.append("...");
        Log.w("TbsUgEngine", paramAnonymousString.toString());
      }
      
      public void onPrepareFinished(String paramAnonymousString, final QBPluginItemInfo paramAnonymousQBPluginItemInfo, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadStart :: ");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.append(", info: ");
        localStringBuilder.append(paramAnonymousQBPluginItemInfo);
        localStringBuilder.append(", errorCode: ");
        localStringBuilder.append(paramAnonymousInt2);
        Log.d("TbsUgEngine", localStringBuilder.toString(), new Throwable());
        if (TbsUgEngine.this.shouldUpload())
        {
          paramAnonymousString = X5CoreBeaconUploader.getInstance(ContextHolder.getAppContext());
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("BZUGEGEC_");
          localStringBuilder.append(paramAnonymousInt2);
          paramAnonymousString.userBehaviorStatistics(localStringBuilder.toString());
        }
        if (paramAnonymousQBPluginItemInfo == null) {
          TbsUgEngine.this.uploadBeacon("012");
        }
        if ((paramAnonymousInt2 == 0) && (paramAnonymousQBPluginItemInfo != null))
        {
          BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
          {
            public void doRun()
            {
              try
              {
                StringBuilder localStringBuilder1 = new StringBuilder();
                localStringBuilder1.append("plugin download finish mDownloadDir: ");
                localStringBuilder1.append(paramAnonymousQBPluginItemInfo.mDownloadDir);
                localStringBuilder1.append(", mDownloadFileName");
                localStringBuilder1.append(paramAnonymousQBPluginItemInfo.mDownloadFileName);
                localStringBuilder1.append(", mNewPluginDownloading: ");
                localStringBuilder1.append(TbsUgEngine.3.this.mNewPluginDownloading);
                localStringBuilder1.toString();
                TbsUgEngine.this.doLoadDex(TbsUgEngine.3.this.val$callback, paramAnonymousQBPluginItemInfo.mDownloadDir, paramAnonymousQBPluginItemInfo.mDownloadFileName, TbsUgEngine.3.this.mNewPluginDownloading);
                return;
              }
              catch (Throwable localThrowable)
              {
                StringBuilder localStringBuilder2 = new StringBuilder();
                localStringBuilder2.append("plugin download finish exception: ");
                localStringBuilder2.append(localThrowable.getMessage());
                Log.e("TbsUgEngine", localStringBuilder2.toString());
              }
            }
          });
          return;
        }
        if ((paramAnonymousInt2 != 3014) && (paramAnonymousInt2 != 6008))
        {
          if (paramAnonymousInt2 == 3010)
          {
            paramAnonymousString = new StringBuilder();
            paramAnonymousString.append("plugin install, user canceled, code:");
            paramAnonymousString.append(paramAnonymousInt2);
            paramAnonymousString.toString();
            return;
          }
          if (paramAnonymousInt2 == 3029)
          {
            paramAnonymousString = new StringBuilder();
            paramAnonymousString.append("plugin install, NOPLUGINIFO, code:");
            paramAnonymousString.append(paramAnonymousInt2);
            paramAnonymousString.toString();
            return;
          }
          paramAnonymousString = new StringBuilder();
          paramAnonymousString.append("plugin install, unkonw error, code:");
          paramAnonymousString.append(paramAnonymousInt2);
          paramAnonymousString.toString();
          return;
        }
        paramAnonymousString = new StringBuilder();
        paramAnonymousString.append("plugin install,  no space, code:");
        paramAnonymousString.append(paramAnonymousInt2);
        paramAnonymousString.toString();
      }
      
      public void onPrepareStart(String paramAnonymousString)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onPrepareStart :: ");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.toString();
      }
    };
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("mPluginSystem.usePluginAsync: com.tencent.tbs.ug, type: ");
    ((StringBuilder)localObject1).append(2);
    ((StringBuilder)localObject1).toString();
    this.mPluginSystem.usePluginAsync("com.tencent.tbs.ug", 9, paramPluginLoadCallback, null, null, 2);
  }
  
  private DexClassLoader getDexClassLoader(String paramString)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("@");
    ((StringBuilder)localObject1).append(Thread.currentThread().getStackTrace()[2].getMethodName());
    ((StringBuilder)localObject1).append("...");
    Log.w("TbsUgEngine", ((StringBuilder)localObject1).toString());
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(this.mInstallLocation);
    ((StringBuilder)localObject1).append(File.separator);
    ((StringBuilder)localObject1).append("optDir");
    localObject1 = ((StringBuilder)localObject1).toString();
    Object localObject2 = new File((String)localObject1);
    if (((File)localObject2).exists()) {
      FileUtils.deleteQuietly((File)localObject2);
    }
    if (!((File)localObject2).exists()) {
      ((File)localObject2).mkdirs();
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("initTbsUg dexOutPutDir=");
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).toString();
    localObject1 = createtDexLoader(this.mBaseClassLoader, paramString, (String)localObject1);
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("initTbsUg dexloader=");
    ((StringBuilder)localObject2).append(localObject1);
    ((StringBuilder)localObject2).toString();
    this.mPluginPath = paramString;
    return (DexClassLoader)localObject1;
  }
  
  public static TbsUgEngine getInstance()
  {
    return inst;
  }
  
  private ITbsUg getTbsUg()
  {
    if (this.mTbsService != null)
    {
      ITbsUg localITbsUg = getTbsUgDynamic();
      if (localITbsUg != null) {
        localITbsUg.init(this.mTbsService);
      }
      return localITbsUg;
    }
    throw new RuntimeException("TbsUg must be inited first!");
  }
  
  /* Error */
  private String getTbsUgConfValue(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 311	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifne +590 -> 594
    //   7: aload_2
    //   8: invokestatic 311	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   11: ifne +583 -> 594
    //   14: new 256	java/io/File
    //   17: dup
    //   18: aload_1
    //   19: invokespecial 263	java/io/File:<init>	(Ljava/lang/String;)V
    //   22: invokevirtual 266	java/io/File:exists	()Z
    //   25: ifne +5 -> 30
    //   28: aconst_null
    //   29: areturn
    //   30: new 313	java/util/zip/ZipFile
    //   33: dup
    //   34: aload_1
    //   35: invokespecial 314	java/util/zip/ZipFile:<init>	(Ljava/lang/String;)V
    //   38: astore_1
    //   39: aload_1
    //   40: ldc_w 316
    //   43: invokevirtual 320	java/util/zip/ZipFile:getEntry	(Ljava/lang/String;)Ljava/util/zip/ZipEntry;
    //   46: astore_3
    //   47: aload_3
    //   48: ifnonnull +42 -> 90
    //   51: aload_1
    //   52: invokevirtual 323	java/util/zip/ZipFile:close	()V
    //   55: aconst_null
    //   56: areturn
    //   57: astore_1
    //   58: new 140	java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   65: astore_2
    //   66: aload_2
    //   67: ldc_w 325
    //   70: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   73: pop
    //   74: aload_2
    //   75: aload_1
    //   76: invokevirtual 326	java/lang/Exception:toString	()Ljava/lang/String;
    //   79: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: pop
    //   83: aload_2
    //   84: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   87: pop
    //   88: aconst_null
    //   89: areturn
    //   90: aload_1
    //   91: aload_3
    //   92: invokevirtual 330	java/util/zip/ZipFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   95: astore 5
    //   97: aload 5
    //   99: ifnonnull +86 -> 185
    //   102: aload 5
    //   104: ifnull +42 -> 146
    //   107: aload 5
    //   109: invokevirtual 333	java/io/InputStream:close	()V
    //   112: goto +34 -> 146
    //   115: astore_2
    //   116: new 140	java/lang/StringBuilder
    //   119: dup
    //   120: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   123: astore_3
    //   124: aload_3
    //   125: ldc_w 325
    //   128: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: pop
    //   132: aload_3
    //   133: aload_2
    //   134: invokevirtual 326	java/lang/Exception:toString	()Ljava/lang/String;
    //   137: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   140: pop
    //   141: aload_3
    //   142: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   145: pop
    //   146: aload_1
    //   147: invokevirtual 323	java/util/zip/ZipFile:close	()V
    //   150: aconst_null
    //   151: areturn
    //   152: astore_1
    //   153: new 140	java/lang/StringBuilder
    //   156: dup
    //   157: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   160: astore_2
    //   161: aload_2
    //   162: ldc_w 325
    //   165: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   168: pop
    //   169: aload_2
    //   170: aload_1
    //   171: invokevirtual 326	java/lang/Exception:toString	()Ljava/lang/String;
    //   174: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   177: pop
    //   178: aload_2
    //   179: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   182: pop
    //   183: aconst_null
    //   184: areturn
    //   185: aload_1
    //   186: astore_3
    //   187: aload 5
    //   189: astore 4
    //   191: new 335	java/util/Properties
    //   194: dup
    //   195: invokespecial 336	java/util/Properties:<init>	()V
    //   198: astore 6
    //   200: aload_1
    //   201: astore_3
    //   202: aload 5
    //   204: astore 4
    //   206: aload 6
    //   208: aload 5
    //   210: invokevirtual 340	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   213: aload_1
    //   214: astore_3
    //   215: aload 5
    //   217: astore 4
    //   219: aload 6
    //   221: aload_2
    //   222: invokevirtual 344	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   225: astore_2
    //   226: aload 5
    //   228: ifnull +46 -> 274
    //   231: aload 5
    //   233: invokevirtual 333	java/io/InputStream:close	()V
    //   236: goto +38 -> 274
    //   239: astore_3
    //   240: new 140	java/lang/StringBuilder
    //   243: dup
    //   244: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   247: astore 4
    //   249: aload 4
    //   251: ldc_w 325
    //   254: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   257: pop
    //   258: aload 4
    //   260: aload_3
    //   261: invokevirtual 326	java/lang/Exception:toString	()Ljava/lang/String;
    //   264: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   267: pop
    //   268: aload 4
    //   270: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   273: pop
    //   274: aload_1
    //   275: invokevirtual 323	java/util/zip/ZipFile:close	()V
    //   278: aload_2
    //   279: areturn
    //   280: astore_1
    //   281: new 140	java/lang/StringBuilder
    //   284: dup
    //   285: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   288: astore_3
    //   289: aload_3
    //   290: ldc_w 325
    //   293: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   296: pop
    //   297: aload_3
    //   298: aload_1
    //   299: invokevirtual 326	java/lang/Exception:toString	()Ljava/lang/String;
    //   302: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   305: pop
    //   306: aload_3
    //   307: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   310: pop
    //   311: aload_2
    //   312: areturn
    //   313: astore_3
    //   314: aload_1
    //   315: astore_2
    //   316: aload 5
    //   318: astore_1
    //   319: aload_3
    //   320: astore 5
    //   322: goto +37 -> 359
    //   325: astore_2
    //   326: aconst_null
    //   327: astore 4
    //   329: goto +173 -> 502
    //   332: astore 5
    //   334: aconst_null
    //   335: astore_3
    //   336: aload_1
    //   337: astore_2
    //   338: aload_3
    //   339: astore_1
    //   340: goto +19 -> 359
    //   343: astore_2
    //   344: aconst_null
    //   345: astore 4
    //   347: aload 4
    //   349: astore_1
    //   350: goto +152 -> 502
    //   353: astore 5
    //   355: aconst_null
    //   356: astore_1
    //   357: aload_1
    //   358: astore_2
    //   359: aload_2
    //   360: astore_3
    //   361: aload_1
    //   362: astore 4
    //   364: new 140	java/lang/StringBuilder
    //   367: dup
    //   368: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   371: astore 6
    //   373: aload_2
    //   374: astore_3
    //   375: aload_1
    //   376: astore 4
    //   378: aload 6
    //   380: ldc_w 325
    //   383: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   386: pop
    //   387: aload_2
    //   388: astore_3
    //   389: aload_1
    //   390: astore 4
    //   392: aload 6
    //   394: aload 5
    //   396: invokevirtual 326	java/lang/Exception:toString	()Ljava/lang/String;
    //   399: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   402: pop
    //   403: aload_2
    //   404: astore_3
    //   405: aload_1
    //   406: astore 4
    //   408: aload 6
    //   410: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   413: pop
    //   414: aload_1
    //   415: ifnull +41 -> 456
    //   418: aload_1
    //   419: invokevirtual 333	java/io/InputStream:close	()V
    //   422: goto +34 -> 456
    //   425: astore_1
    //   426: new 140	java/lang/StringBuilder
    //   429: dup
    //   430: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   433: astore_3
    //   434: aload_3
    //   435: ldc_w 325
    //   438: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   441: pop
    //   442: aload_3
    //   443: aload_1
    //   444: invokevirtual 326	java/lang/Exception:toString	()Ljava/lang/String;
    //   447: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   450: pop
    //   451: aload_3
    //   452: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   455: pop
    //   456: aload_2
    //   457: ifnull +40 -> 497
    //   460: aload_2
    //   461: invokevirtual 323	java/util/zip/ZipFile:close	()V
    //   464: aconst_null
    //   465: areturn
    //   466: astore_1
    //   467: new 140	java/lang/StringBuilder
    //   470: dup
    //   471: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   474: astore_2
    //   475: aload_2
    //   476: ldc_w 325
    //   479: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   482: pop
    //   483: aload_2
    //   484: aload_1
    //   485: invokevirtual 326	java/lang/Exception:toString	()Ljava/lang/String;
    //   488: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   491: pop
    //   492: aload_2
    //   493: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   496: pop
    //   497: aconst_null
    //   498: areturn
    //   499: astore_2
    //   500: aload_3
    //   501: astore_1
    //   502: aload 4
    //   504: ifnull +46 -> 550
    //   507: aload 4
    //   509: invokevirtual 333	java/io/InputStream:close	()V
    //   512: goto +38 -> 550
    //   515: astore_3
    //   516: new 140	java/lang/StringBuilder
    //   519: dup
    //   520: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   523: astore 4
    //   525: aload 4
    //   527: ldc_w 325
    //   530: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   533: pop
    //   534: aload 4
    //   536: aload_3
    //   537: invokevirtual 326	java/lang/Exception:toString	()Ljava/lang/String;
    //   540: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   543: pop
    //   544: aload 4
    //   546: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   549: pop
    //   550: aload_1
    //   551: ifnull +41 -> 592
    //   554: aload_1
    //   555: invokevirtual 323	java/util/zip/ZipFile:close	()V
    //   558: goto +34 -> 592
    //   561: astore_1
    //   562: new 140	java/lang/StringBuilder
    //   565: dup
    //   566: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   569: astore_3
    //   570: aload_3
    //   571: ldc_w 325
    //   574: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   577: pop
    //   578: aload_3
    //   579: aload_1
    //   580: invokevirtual 326	java/lang/Exception:toString	()Ljava/lang/String;
    //   583: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   586: pop
    //   587: aload_3
    //   588: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   591: pop
    //   592: aload_2
    //   593: athrow
    //   594: aconst_null
    //   595: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	596	0	this	TbsUgEngine
    //   0	596	1	paramString1	String
    //   0	596	2	paramString2	String
    //   46	169	3	localObject1	Object
    //   239	22	3	localException1	Exception
    //   288	19	3	localStringBuilder1	StringBuilder
    //   313	7	3	localException2	Exception
    //   335	166	3	localObject2	Object
    //   515	22	3	localException3	Exception
    //   569	19	3	localStringBuilder2	StringBuilder
    //   189	356	4	localObject3	Object
    //   95	226	5	localObject4	Object
    //   332	1	5	localException4	Exception
    //   353	42	5	localException5	Exception
    //   198	211	6	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   51	55	57	java/lang/Exception
    //   107	112	115	java/lang/Exception
    //   146	150	152	java/lang/Exception
    //   231	236	239	java/lang/Exception
    //   274	278	280	java/lang/Exception
    //   191	200	313	java/lang/Exception
    //   206	213	313	java/lang/Exception
    //   219	226	313	java/lang/Exception
    //   39	47	325	finally
    //   90	97	325	finally
    //   39	47	332	java/lang/Exception
    //   90	97	332	java/lang/Exception
    //   30	39	343	finally
    //   30	39	353	java/lang/Exception
    //   418	422	425	java/lang/Exception
    //   460	464	466	java/lang/Exception
    //   191	200	499	finally
    //   206	213	499	finally
    //   219	226	499	finally
    //   364	373	499	finally
    //   378	387	499	finally
    //   392	403	499	finally
    //   408	414	499	finally
    //   507	512	515	java/lang/Exception
    //   554	558	561	java/lang/Exception
  }
  
  private ITbsUg getTbsUgDynamic()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("@");
    ((StringBuilder)localObject).append(Thread.currentThread().getStackTrace()[2].getMethodName());
    ((StringBuilder)localObject).append("...");
    Log.w("TbsUgEngine", ((StringBuilder)localObject).toString());
    uploadBeacon("001");
    if (!loadDebugPlugin())
    {
      boolean bool = loadRemotePlugin();
      tryToDownloadPluginOrUpdateAsync(new PluginLoadCallback());
      if (!bool) {
        loadLocalPlugin();
      }
    }
    if (this.mTbsUg == null)
    {
      localObject = this.mClassLoader;
      if (localObject != null)
      {
        localObject = ReflectionUtils.newInstance((DexClassLoader)localObject, "com.tencent.tbs.ug.core.TbsUg");
        if ((localObject instanceof ITbsUg)) {
          this.mTbsUg = ((ITbsUg)localObject);
        }
      }
    }
    return this.mTbsUg;
  }
  
  private void initUgInstallLocation()
  {
    if (this.mInstallLocation == null)
    {
      File localFile = new File(new File(ContextHolder.getAppContext().getDir("tbs", 0), "share"), "ug");
      if (!localFile.exists()) {
        localFile.mkdirs();
      }
      this.mInstallLocation = localFile.getAbsolutePath();
    }
  }
  
  private boolean loadDebugPlugin()
  {
    boolean bool2 = DEBUG_FLAG;
    boolean bool1 = false;
    if (bool2)
    {
      Context localContext = createHostContext("com.tencent.tbs.ug");
      if (localContext != null)
      {
        this.mClassLoader = getDexClassLoader(localContext.getPackageResourcePath());
        if (this.mClassLoader != null) {
          bool1 = true;
        }
        return bool1;
      }
    }
    return false;
  }
  
  private boolean loadLocalPlugin()
  {
    uploadBeacon("007");
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.mCoreShareDir);
    ((StringBuilder)localObject).append(File.separator);
    ((StringBuilder)localObject).append("tbs_ug_plugin.jar");
    localObject = ((StringBuilder)localObject).toString();
    if (this.mClassLoader == null) {
      this.mClassLoader = getDexClassLoader((String)localObject);
    }
    return this.mClassLoader != null;
  }
  
  private boolean loadRemotePlugin()
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("@");
    ((StringBuilder)localObject1).append(Thread.currentThread().getStackTrace()[2].getMethodName());
    ((StringBuilder)localObject1).append("...");
    Log.w("TbsUgEngine", ((StringBuilder)localObject1).toString());
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(this.mCoreShareDir);
    ((StringBuilder)localObject1).append(File.separator);
    ((StringBuilder)localObject1).append("tbs_ug_plugin.jar");
    localObject1 = ((StringBuilder)localObject1).toString();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(this.mInstallLocation);
    ((StringBuilder)localObject2).append(File.separator);
    ((StringBuilder)localObject2).append("tbs_ug_plugin.jar");
    localObject2 = ((StringBuilder)localObject2).toString();
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append(this.mInstallLocation);
    ((StringBuilder)localObject3).append(File.separator);
    ((StringBuilder)localObject3).append("tbs_ug_plugin_new.jar");
    localObject3 = ((StringBuilder)localObject3).toString();
    File localFile1 = new File((String)localObject2);
    File localFile2 = new File((String)localObject3);
    boolean bool1 = localFile1.exists();
    boolean bool2 = true;
    int i;
    if ((!bool1) && (!localFile2.exists()))
    {
      Log.e("TbsUgEngine", "plugin dex not existed!!");
      uploadBeacon("004");
      i = 0;
      bool1 = false;
    }
    else
    {
      if ((localFile2.exists()) && (compareVersion((String)localObject3, (String)localObject2) > 0))
      {
        uploadBeacon("002");
        if (localFile1.exists()) {
          localFile1.delete();
        }
        bool1 = localFile2.renameTo(localFile1);
        localFile2.delete();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("plugin updated: ");
        ((StringBuilder)localObject3).append(bool1);
        ((StringBuilder)localObject3).toString();
      }
      else
      {
        uploadBeacon("003");
        bool1 = false;
      }
      i = 1;
    }
    int j;
    if (compareVersion((String)localObject2, (String)localObject1) > 0) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0) {
      uploadBeacon("005");
    } else {
      uploadBeacon("006");
    }
    if ((i != 0) && (j != 0))
    {
      if ((bool1) || (this.mClassLoader == null)) {
        this.mClassLoader = getDexClassLoader((String)localObject2);
      }
      if (this.mClassLoader != null) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      this.is_plugin_load_success = bool1;
      if (!this.is_plugin_load_success) {
        Log.e("TbsUgEngine", "plugin_is_ready & load plugin failed!");
      }
    }
    else
    {
      Log.w("TbsUgEngine", "plugin is not ready & load plugin failed!");
      this.is_plugin_load_success = false;
    }
    return this.is_plugin_load_success;
  }
  
  private boolean shouldUpload()
  {
    String str = PublicSettingManager.getInstance().getUgPluginUploadInterval();
    return PublicSettingManager.getInstance().satisfyRatioControl(str);
  }
  
  private void tryToDownloadPluginOrUpdateAsync(final PluginLoadCallback paramPluginLoadCallback)
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        try
        {
          if (TbsUgEngine.this.mPluginSystem == null) {
            TbsUgEngine.access$002(TbsUgEngine.this, QBPluginSystem.getInstance(ContextHolder.getAppContext()));
          }
          TbsUgEngine.this.downloadPlugin(paramPluginLoadCallback);
          return;
        }
        catch (Throwable localThrowable)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("downloadPlugin exception: ");
          localStringBuilder.append(localThrowable.getMessage());
          Log.e("TbsUgEngine", localStringBuilder.toString());
        }
      }
    });
  }
  
  private void uploadBeacon(String paramString)
  {
    if (shouldUpload())
    {
      X5CoreBeaconUploader localX5CoreBeaconUploader = X5CoreBeaconUploader.getInstance(ContextHolder.getAppContext());
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("BZUGEG_");
      localStringBuilder.append(paramString);
      localX5CoreBeaconUploader.userBehaviorStatistics(localStringBuilder.toString());
    }
  }
  
  public void ActiveQBHeartBeat(Context paramContext, String paramString, int paramInt, Bundle paramBundle)
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("UG_ARGS_SID_CTX", paramContext);
      localHashMap.put("UG_ARGS_SID_QUA", paramString);
      localHashMap.put("UG_ARGS_SID_AQB_HB_FREQUENCY", Integer.valueOf(paramInt));
      localHashMap.put("UG_ARGS_SID_GUID_BUNDLE", paramBundle);
      localITbsUg.ActiveQBHeartBeat(localHashMap);
    }
  }
  
  protected void doLoadDex(PluginLoadCallback paramPluginLoadCallback, String paramString1, String paramString2, boolean paramBoolean)
  {
    paramPluginLoadCallback = new StringBuilder();
    paramPluginLoadCallback.append("@");
    paramPluginLoadCallback.append(Thread.currentThread().getStackTrace()[2].getMethodName());
    paramPluginLoadCallback.append("...");
    Log.w("TbsUgEngine", paramPluginLoadCallback.toString());
    uploadBeacon("009");
    new File(this.mInstallLocation, "tbs_ug_plugin.jar");
    if (!paramBoolean)
    {
      uploadBeacon("010");
      return;
    }
    if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)))
    {
      paramPluginLoadCallback = new StringBuilder();
      paramPluginLoadCallback.append("downloadDir = ");
      paramPluginLoadCallback.append(paramString1);
      paramPluginLoadCallback.append(", installPath: ");
      paramPluginLoadCallback.append(this.mInstallLocation);
      paramPluginLoadCallback.toString();
      paramPluginLoadCallback = new File(this.mInstallLocation);
      if (!paramPluginLoadCallback.isDirectory())
      {
        if (paramPluginLoadCallback.exists()) {
          paramPluginLoadCallback.delete();
        }
        paramPluginLoadCallback.mkdirs();
      }
      paramPluginLoadCallback = new StringBuilder();
      paramPluginLoadCallback.append(paramString1);
      paramPluginLoadCallback.append(File.separator);
      paramPluginLoadCallback.append(paramString2);
      paramPluginLoadCallback = paramPluginLoadCallback.toString();
      paramString1 = new StringBuilder();
      paramString1.append(this.mInstallLocation);
      paramString1.append(File.separator);
      paramString1.append("tbs_ug_plugin.jar");
      paramString1.toString();
      paramString1 = new StringBuilder();
      paramString1.append(this.mInstallLocation);
      paramString1.append(File.separator);
      paramString1.append("tbs_ug_plugin_new.jar");
      paramString1 = paramString1.toString();
      FileUtils.copyFile(paramPluginLoadCallback, paramString1);
      paramPluginLoadCallback = new StringBuilder();
      paramPluginLoadCallback.append("isNewPlugin; update it the next time: ");
      paramPluginLoadCallback.append(paramString1);
      paramPluginLoadCallback.toString();
      return;
    }
    uploadBeacon("011");
  }
  
  public String getCrashExtraInfo()
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null) {
      return localITbsUg.getCrashExtraInfo();
    }
    return "";
  }
  
  public String getDownloadFileInfo(Object paramObject)
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null) {
      return localITbsUg.getDownloadFileInfo(paramObject);
    }
    return null;
  }
  
  public String getInterceptDownloadMessage()
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null) {
      return localITbsUg.getInterceptDownloadMessage();
    }
    return "";
  }
  
  public String getPluginPath()
  {
    return this.mPluginPath;
  }
  
  public void headsupActiveQB(Context paramContext, String paramString)
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("UG_ARGS_SID_CTX", paramContext);
      localHashMap.put("UG_ARGS_SID_QUA", paramString);
      localITbsUg.headsupActiveQB(localHashMap);
    }
  }
  
  public void init(ISmttServicePartner paramISmttServicePartner, ClassLoader paramClassLoader, String paramString, Context paramContext)
  {
    this.mBaseClassLoader = paramClassLoader;
    this.mCoreShareDir = paramString;
    this.mTbsService = new TbsServiceImpl(paramContext, paramISmttServicePartner);
    initUgInstallLocation();
    tryToDownloadPluginOrUpdateAsync();
  }
  
  public void interceptVideoPlay(Object paramObject, Context paramContext, Handler paramHandler)
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null) {
      localITbsUg.interceptVideoPlay(paramObject, paramContext, paramHandler);
    }
  }
  
  public void monitorAppRemoved(Context paramContext, String paramString)
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("UG_ARGS_SID_CTX", paramContext);
      localHashMap.put("UG_ARGS_SID_QUA", paramString);
      localITbsUg.monitorAppRemoved(localHashMap);
    }
  }
  
  public void onWindowFocusChanged(Context paramContext, boolean paramBoolean)
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("UG_ARGS_SID_CTX", paramContext);
      localHashMap.put("UG_ARGS_SID_WINDOWS_FOCUS", Boolean.valueOf(paramBoolean));
      localITbsUg.onWindowFocusChanged(localHashMap);
    }
  }
  
  public boolean openBrowserListDialog(String paramString, final Context paramContext, final Intent paramIntent, Map<String, Drawable> paramMap, final TBSDialogBuilder.IntentFilterCallback paramIntentFilterCallback)
  {
    final ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("event", "show");
      localHashMap.put("type", paramString);
      if (paramIntentFilterCallback.isDownloadIntercept()) {
        paramString = "download";
      } else {
        paramString = "webview";
      }
      localHashMap.put("scene", paramString);
      localHashMap.put("context", paramContext);
      localHashMap.put("drawables", paramMap);
      localHashMap.put("pickerDialogCallback", new ValueCallback()
      {
        public void onReceiveValue(String paramAnonymousString)
        {
          String str = paramAnonymousString;
          if (paramAnonymousString == null) {
            str = TbsMode.QB_PKGNAME();
          }
          if ((TextUtils.equals(TbsMode.QB_PKGNAME(), str)) && (!PackageUtils.isInstalledPKGExist(TbsMode.QB_PKGNAME(), paramContext)))
          {
            paramIntentFilterCallback.onStartDownload(null, new TBSDialogBuilder.DownloadListener()
            {
              public void onFinished(File paramAnonymous2File)
              {
                paramAnonymous2File = new HashMap();
                paramAnonymous2File.put("event", "finishDownload");
                TbsUgEngine.1.this.val$iTbsUg.handleBrowserListDialog(paramAnonymous2File);
                paramAnonymous2File = new HashMap();
                paramAnonymous2File.put("event", "dismiss");
                TbsUgEngine.1.this.val$iTbsUg.handleBrowserListDialog(paramAnonymous2File);
              }
              
              public void onProgress(int paramAnonymous2Int)
              {
                HashMap localHashMap = new HashMap();
                localHashMap.put("event", "progress");
                localHashMap.put("progress", Integer.valueOf(paramAnonymous2Int));
                TbsUgEngine.1.this.val$iTbsUg.handleBrowserListDialog(localHashMap);
              }
              
              public void onStarted()
              {
                HashMap localHashMap = new HashMap();
                localHashMap.put("event", "startDownload");
                TbsUgEngine.1.this.val$iTbsUg.handleBrowserListDialog(localHashMap);
              }
            });
            return;
          }
          paramAnonymousString = new ResolveInfo();
          paramAnonymousString.activityInfo = new ActivityInfo();
          paramAnonymousString.activityInfo.packageName = str;
          paramIntentFilterCallback.onSendingIntent(paramIntent, paramAnonymousString, true);
          paramAnonymousString = new HashMap();
          paramAnonymousString.put("event", "dismiss");
          localITbsUg.handleBrowserListDialog(paramAnonymousString);
        }
      });
      return localITbsUg.handleBrowserListDialog(localHashMap);
    }
    return false;
  }
  
  public boolean shouldInterceptDownload(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong, String paramString6, String paramString7, String paramString8, String paramString9, byte[] paramArrayOfByte, IX5WebViewBase paramIX5WebViewBase, Object paramObject, String paramString10)
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("UG_ARGS_SID_CTX", paramContext);
      localHashMap.put("UG_ARGS_SID_URL", paramString1);
      localHashMap.put("UG_ARGS_SID_COOKIE", paramString2);
      localHashMap.put("UG_ARGS_SID_USER_AGENT", paramString3);
      localHashMap.put("UG_ARGS_SID_CONTENT_DISPOSITION", paramString4);
      localHashMap.put("UG_ARGS_SID_MIMETYPE", paramString5);
      localHashMap.put("UG_ARGS_SID_CONTENT_LENGTH", Long.valueOf(paramLong));
      localHashMap.put("UG_ARGS_SID_QPROXY_RESP", paramString6);
      localHashMap.put("UG_ARGS_SID_METHOD", paramString7);
      localHashMap.put("UG_ARGS_SID_REFERER", paramString8);
      localHashMap.put("UG_ARGS_SID_URL_BEFORE_REDIRECT", paramString9);
      localHashMap.put("UG_ARGS_SID_REQUEST_BODY", paramArrayOfByte);
      localHashMap.put("UG_ARGS_SID_WEBVIEW", paramIX5WebViewBase);
      localHashMap.put("UG_ARGS_SID_DOWNLOAD_LISTENER", paramObject);
      localHashMap.put("UG_ARGS_SID_APPINFO", paramString10);
      return localITbsUg.shouldInterceptDownload(localHashMap);
    }
    return false;
  }
  
  public int showUgDialog(Context paramContext, String paramString, Bundle paramBundle, ValueCallback<Bundle> paramValueCallback)
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null) {
      return localITbsUg.showUgDialog(paramContext, paramString, paramBundle, paramValueCallback);
    }
    return -1;
  }
  
  public int showUgH5(Context paramContext, ValueCallback<String> paramValueCallback, String paramString, Bundle paramBundle, ValueCallback<Bundle> paramValueCallback1)
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null) {
      return localITbsUg.showUgH5(paramContext, paramValueCallback, paramString, paramBundle, paramValueCallback1);
    }
    return -1;
  }
  
  public int showUgView(Context paramContext, ValueCallback<View> paramValueCallback, String paramString, Bundle paramBundle, ValueCallback<Bundle> paramValueCallback1)
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null) {
      return localITbsUg.showUgView(paramContext, paramValueCallback, paramString, paramBundle, paramValueCallback1);
    }
    return -1;
  }
  
  public void tryToDownloadPluginOrUpdateAsync()
  {
    tryToDownloadPluginOrUpdateAsync(new PluginLoadCallback());
  }
  
  public String ugJsApiExec(String paramString1, ValueCallback<JSONObject> paramValueCallback, JSONObject paramJSONObject, String paramString2, Map<String, Object> paramMap)
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null) {
      return localITbsUg.ugJsApiExec(paramString1, paramValueCallback, paramJSONObject, paramString2, paramMap);
    }
    return null;
  }
  
  public void uploadUgLog()
  {
    ITbsUg localITbsUg = getTbsUg();
    if (localITbsUg != null) {
      localITbsUg.uploadUgLog();
    }
  }
  
  static class PluginLoadCallback
  {
    public void onDownloadProgress(int paramInt) {}
    
    public void onDownloadStart(int paramInt) {}
    
    public void onInstallStart() {}
    
    public void onLoadFailed(int paramInt) {}
    
    public void onLoadFailed(String paramString) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\ug\TbsUgEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */