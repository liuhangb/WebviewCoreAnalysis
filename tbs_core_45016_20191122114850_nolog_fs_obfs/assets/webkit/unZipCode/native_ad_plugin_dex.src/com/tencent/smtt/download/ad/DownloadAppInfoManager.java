package com.tencent.smtt.download.ad;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.Md5Utils;
import com.tencent.mtt.base.utils.PackageUtils;
import com.tencent.smtt.processutil.models.AndroidAppProcess;
import com.tencent.tbs.common.baseinfo.NioFileUtils;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.utils.AES256Utils;
import com.tencent.tbs.common.utils.DomainMatcher;
import com.tencent.tbs.common.utils.TbsFileUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class DownloadAppInfoManager
{
  private static volatile DownloadAppInfoManager jdField_a_of_type_ComTencentSmttDownloadAdDownloadAppInfoManager;
  private List<a> jdField_a_of_type_JavaUtilList = new ArrayList();
  private volatile boolean jdField_a_of_type_Boolean = false;
  private boolean b = true;
  private boolean c = false;
  
  public static DownloadAppInfoManager a()
  {
    if (jdField_a_of_type_ComTencentSmttDownloadAdDownloadAppInfoManager == null) {
      try
      {
        if (jdField_a_of_type_ComTencentSmttDownloadAdDownloadAppInfoManager == null) {
          jdField_a_of_type_ComTencentSmttDownloadAdDownloadAppInfoManager = new DownloadAppInfoManager();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentSmttDownloadAdDownloadAppInfoManager;
  }
  
  private String a()
  {
    JSONArray localJSONArray = new JSONArray();
    for (;;)
    {
      int i;
      try
      {
        int k;
        try
        {
          k = this.jdField_a_of_type_JavaUtilList.size();
          j = k - 50;
          i = j;
          if (j >= 0) {
            break label156;
          }
          i = 0;
        }
        finally {}
        if (j < k)
        {
          localJSONArray.put(((a)this.jdField_a_of_type_JavaUtilList.get(j)).a());
          j += 1;
          continue;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("toJsonString: number of DownloadAppInfo being saved:");
        localStringBuilder.append(k - i);
        Log.d("DownloadAppInfoManager", localStringBuilder.toString());
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        LogUtils.d("DownloadAppInfoManager", Log.getStackTraceString(localOutOfMemoryError));
      }
      catch (Exception localException)
      {
        LogUtils.d("DownloadAppInfoManager", Log.getStackTraceString(localException));
      }
      return localJSONArray.toString();
      label156:
      int j = i;
    }
  }
  
  public static String a(Context paramContext)
  {
    ApplicationInfo localApplicationInfo = paramContext.getApplicationInfo();
    if (localApplicationInfo != null) {
      return (String)localApplicationInfo.loadLabel(paramContext.getPackageManager());
    }
    paramContext = TbsInfoUtils.getQUA2();
    if (!TextUtils.isEmpty(paramContext)) {
      return paramContext.substring(paramContext.indexOf("PP=") + 3, paramContext.indexOf("&PPVN="));
    }
    return null;
  }
  
  static String a(Context paramContext, String paramString)
  {
    try
    {
      paramContext = paramContext.createPackageContext(paramString, 2);
      if (paramContext != null)
      {
        paramContext = Md5Utils.getMD5(new File(paramContext.getPackageResourcePath()));
        return paramContext;
      }
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      for (;;) {}
    }
    return null;
  }
  
  private JSONArray a()
  {
    Object localObject = NioFileUtils.getInstance().readWithMappedByteBuffer(TbsFileUtils.getSharedDownloadAppJsonFile(), 2);
    Log.d("DownloadAppInfoManager", "loadJsonObject");
    if (localObject != null)
    {
      if (localObject.length <= 2) {
        return null;
      }
      long l = System.currentTimeMillis();
      try
      {
        byte[] arrayOfByte = AES256Utils.decrypt((byte[])localObject, AES256Utils.generateKey());
        localObject = arrayOfByte;
      }
      catch (Exception localException2)
      {
        LogUtils.d("DownloadAppInfoManager", Log.getStackTraceString(localException2));
      }
      if ((localObject != null) && (localObject.length > 2))
      {
        StringBuilder localStringBuilder2 = new StringBuilder();
        localStringBuilder2.append("loadJsonObject bytes =");
        localStringBuilder2.append(localObject.length);
        localStringBuilder2.append(", decrypt time = ");
        localStringBuilder2.append(System.currentTimeMillis() - l);
        LogUtils.d("DownloadAppInfoManager", localStringBuilder2.toString());
        try
        {
          localObject = new JSONArray(new String((byte[])localObject));
          return (JSONArray)localObject;
        }
        catch (OutOfMemoryError localOutOfMemoryError)
        {
          LogUtils.d("DownloadAppInfoManager", Log.getStackTraceString(localOutOfMemoryError));
          return null;
        }
        catch (Exception localException1)
        {
          LogUtils.d("DownloadAppInfoManager", Log.getStackTraceString(localException1));
          return null;
        }
      }
      StringBuilder localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("key is error. delete file : ");
      localStringBuilder1.append(TbsFileUtils.getSharedDownloadAppJsonFile().getName());
      LogUtils.d("DownloadAppInfoManager", localStringBuilder1.toString());
      TbsFileUtils.getSharedDownloadAppJsonFile().delete();
      return null;
    }
    return null;
  }
  
  private void a()
  {
    this.jdField_a_of_type_Boolean = true;
    BrowserExecutorSupplier.getInstance().getScheduledExecutor().schedule(new Runnable()
    {
      public void run()
      {
        DownloadAppInfoManager.a(DownloadAppInfoManager.this);
      }
    }, 100L, TimeUnit.MILLISECONDS);
  }
  
  static void a(Context paramContext, a parama, int paramInt)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("channelAppName", a(paramContext));
    localHashMap.put("packageName", parama.jdField_a_of_type_JavaLangString);
    localHashMap.put("activatedType", String.valueOf(paramInt));
    localHashMap.put("channelDomain", parama.c);
    parama = new StringBuilder();
    parama.append("reportActivatedAppInfo: MTT_TBS_AD_ACTIVATED_PACKAGE data=");
    parama.append(localHashMap);
    Log.d("DownloadAppInfoManager", parama.toString());
    X5CoreBeaconUploader.getInstance(paramContext).upLoadToBeacon("MTT_TBS_AD_ACTIVATED_PACKAGE", localHashMap);
  }
  
  private void a(Context paramContext, final String paramString, final int paramInt)
  {
    paramString = new HashMap() {};
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("reportRunningTimeInBackground: MTT_TBS_AD_CHANNEL_APP_INVISIBLE_SECONDS data=");
    localStringBuilder.append(paramString);
    Log.d("DownloadAppInfoManager", localStringBuilder.toString());
    X5CoreBeaconUploader.getInstance(paramContext).upLoadToBeacon("MTT_TBS_AD_CHANNEL_APP_INVISIBLE_SECONDS", paramString);
  }
  
  private void a(Context paramContext, String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2, IRunningInBackgroundListener paramIRunningInBackgroundListener)
  {
    Log.d("DownloadAppInfoManager", "runningInBackgroundFewSeconds: ");
    if (paramContext != null)
    {
      if (paramIRunningInBackgroundListener == null) {
        return;
      }
      int i;
      if (Build.VERSION.SDK_INT < 24) {
        i = 1;
      } else {
        i = 0;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("runningInBackgroundFewSeconds: api level=");
      localStringBuilder.append(Build.VERSION.SDK_INT);
      localStringBuilder.append(" target package=");
      localStringBuilder.append(paramString);
      Log.d("DownloadAppInfoManager", localStringBuilder.toString());
      int j = 0;
      while (j < paramInt) {
        try
        {
          Thread.sleep(1000L);
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("runningInBackgroundFewSeconds: isVisible=");
          localStringBuilder.append(this.b);
          Log.d("DownloadAppInfoManager", localStringBuilder.toString());
          if ((!paramBoolean2) && (i != 0) && (c(paramString)))
          {
            paramIRunningInBackgroundListener.onRunningOver(true);
            return;
          }
          if ((this.b) || (paramBoolean1))
          {
            paramIRunningInBackgroundListener.onRunningOver(false);
            a(paramContext, paramString, j + 2);
            return;
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
          j += 1;
        }
      }
      paramIRunningInBackgroundListener.onRunningOver(this.b ^ true);
      return;
    }
  }
  
  private void a(Context paramContext, boolean paramBoolean)
  {
    Log.d("DownloadAppInfoManager", "restore start-----------------------");
    JSONArray localJSONArray = a();
    if (localJSONArray == null) {
      return;
    }
    try
    {
      localArrayList = new ArrayList();
      i = 0;
    }
    catch (JSONException paramContext)
    {
      for (;;)
      {
        ArrayList localArrayList;
        int i;
        Object localObject;
        StringBuilder localStringBuilder;
        continue;
        i += 1;
      }
    }
    if (i < localJSONArray.length())
    {
      localObject = localJSONArray.getJSONObject(i);
      if (localObject != null)
      {
        localObject = new a((JSONObject)localObject);
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("restore: info=");
        localStringBuilder.append(((a)localObject).jdField_a_of_type_JavaLangString);
        Log.d("DownloadAppInfoManager", localStringBuilder.toString());
        if ((!paramBoolean) || (a(paramContext, (a)localObject)))
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("restore: add info=");
          localStringBuilder.append(localObject);
          Log.d("DownloadAppInfoManager", localStringBuilder.toString());
          localArrayList.add(localObject);
        }
      }
    }
    else
    {
      paramContext = new StringBuilder();
      paramContext.append("restore: list.size=");
      paramContext.append(localArrayList.size());
      Log.d("DownloadAppInfoManager", paramContext.toString());
      try
      {
        this.jdField_a_of_type_JavaUtilList = null;
        this.jdField_a_of_type_JavaUtilList = localArrayList;
        Log.d("DownloadAppInfoManager", "restore end-----------------------");
      }
      finally {}
      a();
      return;
    }
  }
  
  private boolean a()
  {
    return Looper.getMainLooper() == Looper.myLooper();
  }
  
  private boolean a(Context paramContext, a parama)
  {
    if ((!TextUtils.isEmpty(parama.d)) && (!TextUtils.isEmpty(parama.e)))
    {
      if (TextUtils.isEmpty(parama.jdField_a_of_type_JavaLangString)) {
        return false;
      }
      Object localObject;
      if (parama.jdField_b_of_type_Int == 1)
      {
        if (PackageUtils.getInstalledPKGInfo(parama.jdField_a_of_type_JavaLangString, paramContext) == null)
        {
          c(paramContext, parama);
          return false;
        }
        paramContext = a(paramContext, parama.jdField_a_of_type_JavaLangString);
        if ((!TextUtils.isEmpty(paramContext)) && (!paramContext.equals(parama.e)))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("checkSuccessBeforeRestore: installedMd5=");
          ((StringBuilder)localObject).append(paramContext);
          ((StringBuilder)localObject).append(" info.md5=");
          ((StringBuilder)localObject).append(parama.e);
          Log.d("DownloadAppInfoManager", ((StringBuilder)localObject).toString());
          return false;
        }
      }
      else if (parama.jdField_b_of_type_Int == 0)
      {
        paramContext = new File(parama.d);
        if (!paramContext.exists())
        {
          paramContext = new StringBuilder();
          paramContext.append("checkSuccessBeforeRestore: info.packagePath=");
          paramContext.append(parama.d);
          Log.d("DownloadAppInfoManager", paramContext.toString());
          return false;
        }
        localObject = Md5Utils.getMD5(paramContext);
        if ((localObject == null) || (!((String)localObject).equals(parama.e)))
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("checkSuccessBeforeRestore: md5=");
          localStringBuilder.append((String)localObject);
          localStringBuilder.append(" info.md5=");
          localStringBuilder.append(parama.e);
          Log.d("DownloadAppInfoManager", localStringBuilder.toString());
          paramContext.delete();
          return false;
        }
      }
      return TextUtils.isEmpty(parama.jdField_b_of_type_JavaLangString) ^ true;
    }
    return false;
  }
  
  private boolean a(String paramString)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("inChannelAppBlacklistControl: packageName=");
    ((StringBuilder)localObject1).append(paramString);
    Log.d("DownloadAppInfoManager", ((StringBuilder)localObject1).toString());
    localObject1 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(371);
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("inChannelAppBlacklistControl: blacklist=");
    ((StringBuilder)localObject2).append(localObject1);
    Log.d("DownloadAppInfoManager", ((StringBuilder)localObject2).toString());
    if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0))
    {
      localObject2 = new DomainMatcher();
      ((DomainMatcher)localObject2).addDomainList((ArrayList)localObject1);
      return ((DomainMatcher)localObject2).isContainsDomain(paramString);
    }
    return false;
  }
  
  private String b(Context paramContext)
  {
    int i = Process.myPid();
    paramContext = (ActivityManager)paramContext.getSystemService("activity");
    if (paramContext != null)
    {
      paramContext = paramContext.getRunningAppProcesses().iterator();
      while (paramContext.hasNext())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)paramContext.next();
        if (localRunningAppProcessInfo.pid == i) {
          return localRunningAppProcessInfo.processName;
        }
      }
    }
    return null;
  }
  
  private void b()
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    Object localObject = a();
    this.jdField_a_of_type_Boolean = false;
    byte[] arrayOfByte;
    try
    {
      localObject = AES256Utils.encrypt(((String)localObject).getBytes(), AES256Utils.generateKey());
    }
    catch (Exception localException)
    {
      LogUtils.d("DownloadAppInfoManager", Log.getStackTraceString(localException));
      arrayOfByte = null;
    }
    if (arrayOfByte == null) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("save:");
    localStringBuilder.append(arrayOfByte.length);
    Log.d("DownloadAppInfoManager", localStringBuilder.toString());
    NioFileUtils.getInstance().writeWithMappedByteBuffer(TbsFileUtils.getSharedDownloadAppJsonFile(), arrayOfByte, 2);
  }
  
  private void b(final Context paramContext)
  {
    BrowserExecutorSupplier.getInstance().getScheduledExecutor().schedule(new Runnable()
    {
      public void run()
      {
        Object localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("wakeupDownloadPersistence: processName=");
        ((StringBuilder)localObject1).append(DownloadAppInfoManager.a(DownloadAppInfoManager.this, paramContext));
        ((StringBuilder)localObject1).append(" try to get lock");
        LogUtils.d("DownloadAppInfoManager", ((StringBuilder)localObject1).toString());
        localObject1 = new File(FileUtils.getDataDir(paramContext), ".juststrategy.lock");
        Object localObject4 = null;
        try
        {
          localObject1 = new RandomAccessFile((File)localObject1, "rw");
          try
          {
            FileLock localFileLock = ((RandomAccessFile)localObject1).getChannel().tryLock();
          }
          catch (IOException localIOException3) {}catch (FileNotFoundException localFileNotFoundException1) {}
          Object localObject2;
          localFileNotFoundException2.printStackTrace();
        }
        catch (IOException localIOException4)
        {
          localObject1 = null;
          localIOException4.printStackTrace();
          localObject2 = localObject4;
        }
        catch (FileNotFoundException localFileNotFoundException2)
        {
          localObject1 = null;
        }
        Object localObject3 = localObject4;
        if (localObject3 == null)
        {
          if (localObject1 != null) {
            try
            {
              ((RandomAccessFile)localObject1).close();
              return;
            }
            catch (IOException localIOException1)
            {
              localIOException1.printStackTrace();
            }
          }
          return;
        }
        localObject4 = new StringBuilder();
        ((StringBuilder)localObject4).append("wakeupDownloadPersistence: processName=");
        ((StringBuilder)localObject4).append(DownloadAppInfoManager.a(DownloadAppInfoManager.this, paramContext));
        ((StringBuilder)localObject4).append(" got lock!");
        LogUtils.d("DownloadAppInfoManager", ((StringBuilder)localObject4).toString());
        localObject4 = paramContext.getSharedPreferences("tbsadstrategy", 0);
        if (localObject4 != null)
        {
          localObject4 = ((SharedPreferences)localObject4).edit();
          long l = Long.valueOf(new SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(new Date(System.currentTimeMillis()))).longValue();
          ((SharedPreferences.Editor)localObject4).putLong("lastInstalledTime", System.currentTimeMillis());
          ((SharedPreferences.Editor)localObject4).putLong("lastApplyDay", l);
          ((SharedPreferences.Editor)localObject4).apply();
        }
        try
        {
          ((FileLock)localObject3).release();
          localIOException1.close();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("wakeupDownloadPersistence: processName=");
          localStringBuilder.append(DownloadAppInfoManager.a(DownloadAppInfoManager.this, paramContext));
          localStringBuilder.append(" release lock");
          LogUtils.d("DownloadAppInfoManager", localStringBuilder.toString());
          return;
        }
        catch (IOException localIOException2)
        {
          localIOException2.printStackTrace();
        }
      }
    }, 100L, TimeUnit.MILLISECONDS);
  }
  
  private void b(Context paramContext, a parama)
  {
    if (parama == null) {
      return;
    }
    a(paramContext, false);
    Object localObject = null;
    try
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      do
      {
        paramContext = (Context)localObject;
        if (!localIterator.hasNext()) {
          break;
        }
        paramContext = (a)localIterator.next();
      } while ((TextUtils.isEmpty(parama.d)) || (!parama.d.equals(paramContext.d)));
      if (paramContext == null) {
        return;
      }
      parama = new StringBuilder();
      parama.append("removeDownloadAppInfo:");
      parama.append(paramContext.jdField_a_of_type_JavaLangString);
      Log.d("DownloadAppInfoManager", parama.toString());
      this.jdField_a_of_type_JavaUtilList.remove(paramContext);
      a();
      return;
    }
    finally {}
  }
  
  private boolean b(String paramString)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("inChannelDomainBlacklistControl: host=");
    ((StringBuilder)localObject1).append(paramString);
    Log.d("DownloadAppInfoManager", ((StringBuilder)localObject1).toString());
    localObject1 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(372);
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("inChannelAppBlacklistControl: blacklist=");
    ((StringBuilder)localObject2).append(localObject1);
    Log.d("DownloadAppInfoManager", ((StringBuilder)localObject2).toString());
    if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0))
    {
      localObject2 = new DomainMatcher();
      ((DomainMatcher)localObject2).addDomainList((ArrayList)localObject1);
      return ((DomainMatcher)localObject2).isContainsDomain(paramString);
    }
    return false;
  }
  
  private void c(Context paramContext, a parama)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("channelAppName", a(paramContext));
    localHashMap.put("packageName", parama.jdField_a_of_type_JavaLangString);
    localHashMap.put("channelDomain", parama.c);
    parama = new StringBuilder();
    parama.append("reportRemovedAppInfo: MTT_TBS_AD_REMOVED_PACKAGE data=");
    parama.append(localHashMap);
    Log.d("DownloadAppInfoManager", parama.toString());
    X5CoreBeaconUploader.getInstance(paramContext).upLoadToBeacon("MTT_TBS_AD_REMOVED_PACKAGE", localHashMap);
  }
  
  static void c(Context paramContext, final String paramString)
  {
    paramString = new HashMap() {};
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("reportCalledForInstalledAppInfos: MTT_TBS_AD_CALLED_FOR_INSTALLED_PACKAGES data=");
    localStringBuilder.append(paramString);
    Log.d("DownloadAppInfoManager", localStringBuilder.toString());
    X5CoreBeaconUploader.getInstance(paramContext).upLoadToBeacon("MTT_TBS_AD_CALLED_FOR_INSTALLED_PACKAGES", paramString);
  }
  
  private boolean c(String paramString)
  {
    Iterator localIterator = com.tencent.smtt.processutil.a.a().iterator();
    while (localIterator.hasNext())
    {
      AndroidAppProcess localAndroidAppProcess = (AndroidAppProcess)localIterator.next();
      if ((localAndroidAppProcess.jdField_a_of_type_Boolean) && (paramString.equals(localAndroidAppProcess.a()))) {
        return true;
      }
    }
    return false;
  }
  
  List<a> a(Context paramContext)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramContext != null)
    {
      if (TextUtils.isEmpty(paramContext.getPackageName())) {
        return localArrayList;
      }
      int i = 0;
      while (i < this.jdField_a_of_type_JavaUtilList.size())
      {
        a locala = (a)this.jdField_a_of_type_JavaUtilList.get(i);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("getAppList: item ");
        localStringBuilder.append(i);
        localStringBuilder.append(" :");
        localStringBuilder.append(locala);
        Log.d("DownloadAppInfoManager", localStringBuilder.toString());
        if ((!this.c) || (paramContext.getPackageName().equals(locala.h))) {
          localArrayList.add(locala);
        }
        i += 1;
      }
      return localArrayList;
    }
    return localArrayList;
  }
  
  List<a> a(Context paramContext, String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramContext != null)
    {
      if (TextUtils.isEmpty(paramContext.getPackageName())) {
        return localArrayList;
      }
      boolean bool = TextUtils.isEmpty(paramString);
      int i = 0;
      if (!bool) {
        bool = b(paramString);
      } else {
        bool = false;
      }
      while (i < this.jdField_a_of_type_JavaUtilList.size())
      {
        a locala = (a)this.jdField_a_of_type_JavaUtilList.get(i);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("getAppList: item ");
        localStringBuilder.append(i);
        localStringBuilder.append(" :");
        localStringBuilder.append(locala);
        Log.d("DownloadAppInfoManager", localStringBuilder.toString());
        if (((!this.c) || (paramContext.getPackageName().equals(locala.h))) && ((TextUtils.isEmpty(paramString)) || (!bool) || (paramString.equals(locala.c)))) {
          localArrayList.add(locala);
        }
        i += 1;
      }
      return localArrayList;
    }
    return localArrayList;
  }
  
  void a(final Context paramContext)
  {
    if (a())
    {
      BrowserExecutorSupplier.forIoTasks().execute(new Runnable()
      {
        public void run()
        {
          DownloadAppInfoManager localDownloadAppInfoManager = DownloadAppInfoManager.this;
          DownloadAppInfoManager.a(localDownloadAppInfoManager, DownloadAppInfoManager.a(localDownloadAppInfoManager, paramContext.getPackageName()));
          DownloadAppInfoManager.a(DownloadAppInfoManager.this, paramContext, true);
        }
      });
      return;
    }
    this.c = a(paramContext.getPackageName());
    a(paramContext, true);
  }
  
  void a(Context paramContext, a parama)
  {
    if (parama == null) {
      return;
    }
    a(paramContext, false);
    Object localObject = null;
    try
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      do
      {
        paramContext = (Context)localObject;
        if (!localIterator.hasNext()) {
          break;
        }
        paramContext = (a)localIterator.next();
      } while (!paramContext.d.equals(parama.d));
      this.jdField_a_of_type_JavaUtilList.remove(paramContext);
      this.jdField_a_of_type_JavaUtilList.add(parama);
      paramContext = new StringBuilder();
      paramContext.append("addDownloadAppInfo package=");
      paramContext.append(parama.jdField_a_of_type_JavaLangString);
      Log.d("DownloadAppInfoManager", paramContext.toString());
      Collections.sort(this.jdField_a_of_type_JavaUtilList, new Comparator()
      {
        public int a(a paramAnonymousa1, a paramAnonymousa2)
        {
          if (paramAnonymousa1.a > paramAnonymousa2.a) {
            return 1;
          }
          if (paramAnonymousa1.a == paramAnonymousa2.a) {
            return 0;
          }
          return -1;
        }
      });
      a();
      return;
    }
    finally {}
  }
  
  public void a(final Context paramContext, final a parama, final int paramInt, final boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("reportActivatedAppInfoAfterFewSecondsIfNeed: downloadAppInfo=");
    localStringBuilder.append(parama);
    Log.d("DownloadAppInfoManager", localStringBuilder.toString());
    if (TextUtils.isEmpty(parama.jdField_a_of_type_JavaLangString)) {
      return;
    }
    BrowserExecutorSupplier.getInstance().getScheduledExecutor().schedule(new Runnable()
    {
      public void run()
      {
        DownloadAppInfoManager.a(DownloadAppInfoManager.this, paramContext, parama.a, paramInt, paramBoolean, false, new DownloadAppInfoManager.IRunningInBackgroundListener()
        {
          public void onRunningOver(boolean paramAnonymous2Boolean)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("reportActivatedAppInfoAfterFewSecondsIfNeed-onRunningOver--expected = ");
            localStringBuilder.append(paramAnonymous2Boolean);
            Log.d("DownloadAppInfoManager", localStringBuilder.toString());
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("reportActivatedAppInfoAfterFewSecondsIfNeed-onRunningOver--ACTIVATED_BY_APPLAUNCH = ");
            localStringBuilder.append(com.tencent.smtt.a.a.a().a(DownloadAppInfoManager.10.this.jdField_a_of_type_ComTencentSmttDownloadAdA.a));
            Log.d("DownloadAppInfoManager", localStringBuilder.toString());
            if (paramAnonymous2Boolean)
            {
              if (com.tencent.smtt.a.a.a().a(DownloadAppInfoManager.10.this.jdField_a_of_type_ComTencentSmttDownloadAdA.a))
              {
                DownloadAppInfoManager.a(DownloadAppInfoManager.10.this.jdField_a_of_type_AndroidContentContext, DownloadAppInfoManager.10.this.jdField_a_of_type_ComTencentSmttDownloadAdA, 3);
                return;
              }
              DownloadAppInfoManager.a(DownloadAppInfoManager.10.this.jdField_a_of_type_AndroidContentContext, DownloadAppInfoManager.10.this.jdField_a_of_type_ComTencentSmttDownloadAdA, 0);
            }
          }
        });
      }
    }, 1000L, TimeUnit.MILLISECONDS);
  }
  
  void a(final Context paramContext, final String paramString)
  {
    for (;;)
    {
      try
      {
        Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
        if (localIterator.hasNext())
        {
          a locala = (a)localIterator.next();
          if (!paramString.equals(locala.d)) {
            continue;
          }
          locala.jdField_b_of_type_Long = System.currentTimeMillis();
          locala.jdField_a_of_type_Int += 1;
          paramString = locala;
          if (paramString != null) {
            BrowserExecutorSupplier.forIoTasks().execute(new Runnable()
            {
              public void run()
              {
                DownloadAppInfoManager.this.a(paramContext, paramString);
              }
            });
          }
          return;
        }
      }
      finally {}
      paramString = null;
    }
  }
  
  void a(final Context paramContext, final String paramString, final boolean paramBoolean)
  {
    if (paramString == null) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setInstalledStateAsyc: package name=");
    localStringBuilder.append(paramString);
    Log.d("DownloadAppInfoManager", localStringBuilder.toString());
    BrowserExecutorSupplier.forIoTasks().execute(new Runnable()
    {
      public void run()
      {
        int i = 0;
        for (;;)
        {
          try
          {
            if (i < DownloadAppInfoManager.a(DownloadAppInfoManager.this).size())
            {
              a locala = (a)DownloadAppInfoManager.a(DownloadAppInfoManager.this).get(i);
              if (!paramString.equals(paramString))
              {
                i += 1;
                continue;
              }
              if (locala.b != 1)
              {
                locala.b = 1;
                locala.c = System.currentTimeMillis();
                if (locala != null)
                {
                  Object localObject3 = DownloadAppInfoManager.a(paramContext, paramString);
                  if ((!TextUtils.isEmpty((CharSequence)localObject3)) && (!((String)localObject3).equals(locala.e)))
                  {
                    DownloadAppInfoManager.a(DownloadAppInfoManager.this, paramContext, locala);
                    return;
                  }
                  DownloadAppInfoManager.this.a(paramContext, locala);
                  DownloadAppInfoManager.a(DownloadAppInfoManager.this, paramContext);
                  localObject3 = new StringBuilder();
                  ((StringBuilder)localObject3).append("setInstalledStateAsyc: installed info=");
                  ((StringBuilder)localObject3).append(locala);
                  Log.d("DownloadAppInfoManager", ((StringBuilder)localObject3).toString());
                  if (com.tencent.smtt.a.a.a().a() != 0)
                  {
                    DownloadAppInfoManager.this.b(paramContext, locala, com.tencent.smtt.a.a.a().a(), paramBoolean);
                    return;
                  }
                  DownloadAppInfoManager.this.a(paramContext, locala, 7, paramBoolean);
                }
                return;
              }
            }
          }
          finally {}
          Object localObject2 = null;
        }
      }
    });
  }
  
  public void b(final Context paramContext, final a parama, final int paramInt, final boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("checkHostAppIsShowAfterFewSeconds: downloadAppInfo=");
    localStringBuilder.append(parama);
    Log.d("DownloadAppInfoManager", localStringBuilder.toString());
    if (TextUtils.isEmpty(parama.jdField_a_of_type_JavaLangString)) {
      return;
    }
    BrowserExecutorSupplier.getInstance().getScheduledExecutor().schedule(new Runnable()
    {
      public void run()
      {
        DownloadAppInfoManager.a(DownloadAppInfoManager.this, paramContext, parama.a, paramInt, paramBoolean, true, new DownloadAppInfoManager.IRunningInBackgroundListener()
        {
          public void onRunningOver(boolean paramAnonymous2Boolean)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("checkHostAppIsShowAfterFewSeconds-onRunningOver--expected = ");
            localStringBuilder.append(paramAnonymous2Boolean);
            Log.d("DownloadAppInfoManager", localStringBuilder.toString());
            if (paramAnonymous2Boolean)
            {
              if (com.tencent.smtt.a.a.a().a(DownloadAppInfoManager.2.this.jdField_a_of_type_ComTencentSmttDownloadAdA.a))
              {
                DownloadAppInfoManager.a(DownloadAppInfoManager.2.this.jdField_a_of_type_AndroidContentContext, DownloadAppInfoManager.2.this.jdField_a_of_type_ComTencentSmttDownloadAdA, 3);
                return;
              }
              DownloadAppInfoManager.a(DownloadAppInfoManager.2.this.jdField_a_of_type_AndroidContentContext, DownloadAppInfoManager.2.this.jdField_a_of_type_ComTencentSmttDownloadAdA, 0);
              return;
            }
            if (!com.tencent.smtt.a.a.a().a(DownloadAppInfoManager.2.this.jdField_a_of_type_ComTencentSmttDownloadAdA.a)) {
              com.tencent.smtt.a.a.a().a(DownloadAppInfoManager.2.this.jdField_a_of_type_AndroidContentContext, DownloadAppInfoManager.2.this.jdField_a_of_type_ComTencentSmttDownloadAdA, DownloadAppInfoManager.2.this.jdField_a_of_type_Boolean);
            }
          }
        });
      }
    }, 1000L, TimeUnit.MILLISECONDS);
  }
  
  void b(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    StringBuilder localStringBuilder = null;
    try
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      Object localObject;
      do
      {
        localObject = localStringBuilder;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject = (a)localIterator.next();
      } while (!paramString.equals(((a)localObject).e));
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("removeDownloadAppInfo:");
      localStringBuilder.append(paramString);
      Log.d("DownloadAppInfoManager", localStringBuilder.toString());
      b(paramContext, (a)localObject);
      return;
    }
    finally {}
  }
  
  static abstract interface IRunningInBackgroundListener
  {
    public abstract void onRunningOver(boolean paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\download\ad\DownloadAppInfoManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */