package com.tencent.smtt.jsApi.impl.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.Toast;
import com.tencent.common.dns.DnsProvider;
import com.tencent.common.http.Apn;
import com.tencent.common.serverconfig.DnsManager;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.Md5Utils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.downloadprovider.DownloadproviderHelper;
import com.tencent.mtt.base.task.Task;
import com.tencent.mtt.base.task.TaskObserver;
import com.tencent.mtt.browser.download.engine.DownloadInfo;
import com.tencent.mtt.browser.download.engine.DownloadTask;
import com.tencent.smtt.download.ad.b;
import com.tencent.smtt.jsApi.impl.OpenJsHelper;
import com.tencent.smtt.jsApi.impl.jsDownload;
import com.tencent.tbs.common.baseinfo.TBSBroadcastReceiver;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.download.BaseDownloadManager;
import com.tencent.tbs.common.download.qb.QBDownloadManager;
import com.tencent.tbs.common.download.qb.QBInstallListener;
import com.tencent.tbs.common.observer.AppBroadcastObserver;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

public class DownloadController
  implements TaskObserver, QBInstallListener, AppBroadcastObserver
{
  private static HashMap<String, AppAdMediaInfo> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  private Handler jdField_a_of_type_AndroidOsHandler;
  private HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  private SparseArray<String> jdField_a_of_type_AndroidUtilSparseArray;
  private IDownloadCallback jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback;
  private Object jdField_a_of_type_JavaLangObject = new Object();
  private ArrayList<String> jdField_a_of_type_JavaUtilArrayList;
  private Map<Integer, String> jdField_a_of_type_JavaUtilMap;
  private Map<Integer, Boolean> b;
  
  @SuppressLint({"UseSparseArrays"})
  public DownloadController(IDownloadCallback paramIDownloadCallback)
  {
    a();
    this.jdField_a_of_type_JavaUtilMap = new HashMap();
    this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
    this.b = new HashMap();
    this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback = paramIDownloadCallback;
    this.jdField_a_of_type_AndroidUtilSparseArray = new SparseArray();
    BaseDownloadManager.getInstance().init();
    BaseDownloadManager.getInstance().addTaskObserver(this);
    QBDownloadManager.getInstance().registerQBInstallListener(this);
    TBSBroadcastReceiver.getInstance().addBroadcastObserver(this);
    b();
  }
  
  private File a()
  {
    File localFile1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(".");
      ((StringBuilder)localObject).append(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext().getPackageName());
      File localFile2 = new File(localFile1, ((StringBuilder)localObject).toString());
      localObject = localFile2;
      if (!localFile2.exists())
      {
        localObject = localFile2;
        if (!localFile2.mkdir()) {
          localObject = localFile1;
        }
      }
      if ((((File)localObject).exists()) && (!((File)localObject).canWrite()))
      {
        localObject = this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (localObject == null) {
          return localFile1;
        }
        return (File)localObject;
      }
      return (File)localObject;
    }
    catch (Throwable localThrowable) {}
    return localFile1;
  }
  
  private String a(JSONObject paramJSONObject, String paramString)
  {
    return a(paramJSONObject, paramString, null);
  }
  
  private String a(JSONObject paramJSONObject, String paramString1, String paramString2)
  {
    if (paramJSONObject != null) {
      if (!paramJSONObject.has(paramString1)) {
        return paramString2;
      }
    }
    try
    {
      paramJSONObject = paramJSONObject.getString(paramString1);
      return paramJSONObject;
    }
    catch (JSONException paramJSONObject) {}
    return paramString2;
    return paramString2;
  }
  
  private void a()
  {
    this.jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread("DownloadController");
    try
    {
      this.jdField_a_of_type_AndroidOsHandlerThread.start();
    }
    catch (Throwable localThrowable)
    {
      Object localObject;
      for (;;) {}
    }
    this.jdField_a_of_type_AndroidOsHandlerThread = null;
    localObject = this.jdField_a_of_type_AndroidOsHandlerThread;
    if (localObject != null) {
      localObject = ((HandlerThread)localObject).getLooper();
    } else {
      localObject = Looper.getMainLooper();
    }
    this.jdField_a_of_type_AndroidOsHandler = new Handler((Looper)localObject)
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.obj == null) {
          return;
        }
        String[] arrayOfString = (String[])paramAnonymousMessage.obj;
        arrayOfString[2] = DownloadController.this.b(paramAnonymousMessage.what);
        if (DownloadController.a(DownloadController.this) != null) {
          DownloadController.a(DownloadController.this).notifyJSDownloadStatus(arrayOfString);
        }
      }
    };
  }
  
  private void a(Context paramContext, String paramString)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("reportPackageReplaceEventIfNeed:");
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).toString();
    Object localObject2 = b.a().a(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.isWebViewDestroyed(), paramString);
    if (localObject2 != null)
    {
      b.a().b(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getOpenJsHelper().getInternalWebView(), paramString);
      ReporterUtil.getSingleton().statReinstallEvent(paramContext, ReporterUtil.REINSTALL_INSTALLED, null, (JSONObject)localObject2);
    }
    try
    {
      String str2 = ((JSONObject)localObject2).getString("packagePath");
      localObject1 = ((JSONObject)localObject2).getString("refer");
      localObject2 = ((JSONObject)localObject2).getString("linkUrl");
      String str1 = PackageUtils.getPackageMd5(paramString);
      str2 = Md5Utils.getMD5(new File(str2));
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("reportPackageReplaceEventIfNeed:");
      localStringBuilder.append(paramString);
      localStringBuilder.append(" installedMd5:");
      localStringBuilder.append(str1);
      localStringBuilder.append(" downloadMd5");
      localStringBuilder.append(str2);
      localStringBuilder.toString();
      if ((!TextUtils.isEmpty(str1)) && (!str1.equals(str2)))
      {
        ReporterUtil.getSingleton().reportSystemReplacePackageEvent(paramContext, paramString, (String)localObject2, str2, str1, (String)localObject1);
        return;
      }
      return;
    }
    catch (JSONException paramContext) {}
  }
  
  private void a(DownloadTask paramDownloadTask)
  {
    int i = paramDownloadTask.getDownloadTaskId();
    String str2 = (String)this.jdField_a_of_type_AndroidUtilSparseArray.get(i);
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("startInstall businessId:");
    ((StringBuilder)localObject1).append(str2);
    ((StringBuilder)localObject1).toString();
    int j = SystemInstallReceiver.getInstance().installApkFile(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), paramDownloadTask.getTaskUrl(), this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback, (String)this.jdField_a_of_type_JavaUtilMap.get(Integer.valueOf(i)), i, str2);
    a(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback);
    if ((j != 0) && (this.b.containsKey(Integer.valueOf(i)))) {
      this.b.remove(Integer.valueOf(paramDownloadTask.getDownloadTaskId()));
    }
    Object localObject6 = "unknow";
    String str1 = "unknow";
    Object localObject5;
    Object localObject3;
    if (j == 0)
    {
      localObject1 = localObject6;
      localObject5 = str1;
      try
      {
        Object localObject2 = new StringBuilder();
        localObject1 = localObject6;
        localObject5 = str1;
        ((StringBuilder)localObject2).append("get channel stat:");
        localObject1 = localObject6;
        localObject5 = str1;
        ((StringBuilder)localObject2).append(System.currentTimeMillis());
        localObject1 = localObject6;
        localObject5 = str1;
        ((StringBuilder)localObject2).toString();
        localObject1 = localObject6;
        localObject5 = str1;
        localObject2 = BaseDownloadManager.getInstance().getDownloadFileByTask(paramDownloadTask);
        localObject1 = localObject6;
        localObject5 = str1;
        AppChannelInfo localAppChannelInfo = SystemInstallReceiver.getAPPChannel(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), (File)localObject2);
        localObject4 = localObject6;
        localObject2 = str1;
        if (localAppChannelInfo != null)
        {
          localObject4 = localObject6;
          localObject1 = localObject6;
          localObject5 = str1;
          if (!TextUtils.isEmpty(localAppChannelInfo.channel))
          {
            localObject1 = localObject6;
            localObject5 = str1;
            localObject4 = localAppChannelInfo.channel;
          }
          localObject1 = localObject4;
          localObject5 = str1;
          localObject2 = localAppChannelInfo.pkgname;
        }
        localObject1 = localObject4;
        localObject5 = localObject2;
        localObject6 = new StringBuilder();
        localObject1 = localObject4;
        localObject5 = localObject2;
        ((StringBuilder)localObject6).append("get channel end:");
        localObject1 = localObject4;
        localObject5 = localObject2;
        ((StringBuilder)localObject6).append(System.currentTimeMillis());
        localObject1 = localObject4;
        localObject5 = localObject2;
        ((StringBuilder)localObject6).toString();
        localObject1 = localObject4;
      }
      catch (Throwable localThrowable)
      {
        localObject4 = new StringBuilder();
        ((StringBuilder)localObject4).append("get channle:");
        ((StringBuilder)localObject4).append(localThrowable.toString());
        ((StringBuilder)localObject4).toString();
        localObject3 = localObject5;
      }
      localObject4 = localObject1;
      localObject1 = localObject3;
      localObject3 = localObject4;
    }
    else
    {
      localObject1 = ReporterUtil.getSingleton();
      localObject3 = this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext();
      localObject4 = paramDownloadTask.mDownloadUrl;
      localObject5 = new StringBuilder();
      ((StringBuilder)localObject5).append("InstallErrorCode=");
      ((StringBuilder)localObject5).append(j);
      ((ReporterUtil)localObject1).reportUncompletedMsg((Context)localObject3, (String)localObject4, "8", ((StringBuilder)localObject5).toString());
      localObject3 = "unknow";
      localObject1 = "unknow";
    }
    Object localObject4 = ReporterUtil.getSingleton().getReportInfo(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), paramDownloadTask.getTaskUrl(), "1");
    if ((localObject4 != null) && (!isRestrictedAds(((ReporterUtil.ReportInfo)localObject4).pageUrl)))
    {
      localObject5 = BaseDownloadManager.getInstance().getDownloadFileByTask(paramDownloadTask);
      localObject4 = ((ReporterUtil.ReportInfo)localObject4).refer;
      if (TextUtils.isEmpty((CharSequence)localObject4))
      {
        localObject6 = (AppAdMediaInfo)jdField_a_of_type_JavaUtilHashMap.get(localObject1);
        if (localObject6 != null)
        {
          jdField_a_of_type_JavaUtilHashMap.remove(localObject1);
          localObject4 = ((AppAdMediaInfo)localObject6).b;
          localObject6 = new StringBuilder();
          ((StringBuilder)localObject6).append("startInstall: get refer=");
          ((StringBuilder)localObject6).append((String)localObject4);
          ((StringBuilder)localObject6).append(" from media map");
          ((StringBuilder)localObject6).toString();
        }
      }
      b.a().a((String)localObject1, (String)localObject4, paramDownloadTask.getTaskUrl(), ((File)localObject5).getAbsolutePath(), str2);
      b.a().a(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getOpenJsHelper().getInternalWebView(), (String)localObject1);
    }
    ReporterUtil.getSingleton().reportDownloadMsg(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), paramDownloadTask.getTaskUrl(), "1", (String)localObject3, (String)localObject1);
  }
  
  @SuppressLint({"NewApi"})
  private void a(String paramString, long paramLong)
  {
    try
    {
      if (!Configuration.getInstance(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext()).isJSDownloadOptimizationEnabled()) {
        return;
      }
      if (new File(paramString).getFreeSpace() > paramLong + paramLong) {
        return;
      }
      a(paramString, paramLong, 777600000L);
      return;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("pruneFloder failed:");
      localStringBuilder.append(paramString.toString());
      localStringBuilder.toString();
    }
  }
  
  private void a(String paramString, long paramLong1, long paramLong2)
  {
    if (paramLong2 < 7200000L) {
      return;
    }
    Object localObject2 = new File(paramString);
    if (!((File)localObject2).exists()) {
      return;
    }
    if (!((File)localObject2).isDirectory()) {
      return;
    }
    Object localObject1 = null;
    try
    {
      localObject2 = ((File)localObject2).listFiles();
      localObject1 = localObject2;
    }
    catch (Error localError)
    {
      localError.printStackTrace();
    }
    if (localObject1 != null)
    {
      if (localObject1.length < 1) {
        return;
      }
      long l4 = System.currentTimeMillis();
      int j = localObject1.length;
      long l1 = 0L;
      int i = 0;
      while (i < j)
      {
        Object localObject3 = localObject1[i];
        long l3 = l1;
        try
        {
          if (((File)localObject3).isFile())
          {
            l3 = l1;
            if (((File)localObject3).lastModified() < l4 - paramLong2)
            {
              l3 = ((File)localObject3).length();
              boolean bool = ((File)localObject3).delete();
              long l2 = l1;
              if (bool) {
                l2 = l1 + l3;
              }
              l3 = l2;
              if (l2 > paramLong1) {
                return;
              }
            }
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          l3 = l1;
          i += 1;
          l1 = l3;
        }
      }
      if (l1 < paramLong1) {
        a(paramString, paramLong1 - l1, paramLong2 / 3L);
      }
      return;
    }
  }
  
  private boolean a(DownloadTask paramDownloadTask)
  {
    try
    {
      if (!Configuration.getInstance(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext()).isJSDownloadOptimizationEnabled()) {
        return false;
      }
      String str1 = paramDownloadTask.getTaskUrl();
      String str2 = new URL(str1).getHost();
      String str3 = DnsProvider.resolveIPAddress(str2);
      String str4 = DnsManager.getInstance().resolveDomain(str2);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("addRetryUrl ip1:");
      ((StringBuilder)localObject).append(str3);
      ((StringBuilder)localObject).append(" ip2:");
      ((StringBuilder)localObject).append(str4);
      ((StringBuilder)localObject).toString();
      if (!TextUtils.isEmpty(str3))
      {
        if (TextUtils.isEmpty(str4)) {
          return false;
        }
        ArrayList localArrayList = paramDownloadTask.getRetryUrlList();
        localObject = localArrayList;
        if (localArrayList == null) {
          localObject = new ArrayList();
        }
        if (((ArrayList)localObject).size() > 3) {
          return false;
        }
        if (!TextUtils.isEmpty(str3)) {
          ((ArrayList)localObject).add(str1.replace(str2, str3));
        }
        if (!TextUtils.isEmpty(str4)) {
          ((ArrayList)localObject).add(str1.replace(str2, str4));
        }
        paramDownloadTask.setRetryUrls((ArrayList)localObject, true);
        paramDownloadTask = new StringBuilder();
        paramDownloadTask.append("addRetryUrl ");
        paramDownloadTask.append(localObject);
        paramDownloadTask.toString();
        return true;
      }
      return false;
    }
    catch (Exception paramDownloadTask)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("addRetryUrl failed:");
      ((StringBuilder)localObject).append(paramDownloadTask.toString());
      ((StringBuilder)localObject).toString();
    }
    return false;
  }
  
  private boolean a(JSONObject paramJSONObject, String paramString, boolean paramBoolean)
  {
    if (paramJSONObject != null) {
      if (!paramJSONObject.has(paramString)) {
        return paramBoolean;
      }
    }
    try
    {
      boolean bool = paramJSONObject.getBoolean(paramString);
      return bool;
    }
    catch (JSONException paramJSONObject) {}
    return paramBoolean;
    return paramBoolean;
  }
  
  private void b()
  {
    try
    {
      if (!Configuration.getInstance(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext()).isJSDownloadOptimizationEnabled()) {
        return;
      }
      Iterator localIterator = DownloadproviderHelper.e().iterator();
      Object localObject1;
      while (localIterator.hasNext())
      {
        localObject1 = (DownloadTask)localIterator.next();
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("checkAbortTask :");
        ((StringBuilder)localObject2).append(((DownloadTask)localObject1).getTaskUrl());
        ((StringBuilder)localObject2).append("(");
        ((StringBuilder)localObject2).append(((DownloadTask)localObject1).getDownloadTaskId());
        ((StringBuilder)localObject2).append(")");
        ((StringBuilder)localObject2).append(((DownloadTask)localObject1).getStatus());
        ((StringBuilder)localObject2).toString();
        if ((((DownloadTask)localObject1).getFromWhere() != null) && (((DownloadTask)localObject1).getStatus() == 2) && (((DownloadTask)localObject1).getCreateTime() > 0L))
        {
          if (((DownloadTask)localObject1).getDownloadedSize() * 20L / 1024L / 1024L <= (System.currentTimeMillis() - ((DownloadTask)localObject1).getCreateTime()) / 1000L)
          {
            localObject2 = ((DownloadTask)localObject1).getTaskUrl();
            ReporterUtil.getSingleton().reportAbortDownload(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), (String)localObject2);
            ((DownloadTask)localObject1).setFromWhere(null, true);
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("abort task :");
            localStringBuilder.append((String)localObject2);
            localStringBuilder.append("(");
            localStringBuilder.append(((DownloadTask)localObject1).getDownloadTaskId());
            localStringBuilder.append(")");
            localStringBuilder.append(((DownloadTask)localObject1).getStatus());
            localStringBuilder.toString();
          }
        }
        else if ((((DownloadTask)localObject1).getStatus() != 5) && (((DownloadTask)localObject1).getStatus() != 4))
        {
          if (System.currentTimeMillis() - ((DownloadTask)localObject1).getCreateTime() > 1296000000L) {
            BaseDownloadManager.getInstance().deleteTask((DownloadTask)localObject1, true);
          }
        }
        else {
          BaseDownloadManager.getInstance().deleteTask((DownloadTask)localObject1, true);
        }
      }
      return;
    }
    catch (Exception localException)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("checkAbortTask:");
      ((StringBuilder)localObject1).append(localException.toString());
      ((StringBuilder)localObject1).toString();
    }
  }
  
  String a(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "TASK_STATUS_NONE";
    case 5: 
      return "TASK_STATUS_CANCELED";
    case 4: 
      return "TASK_STATUS_STARTED";
    case 3: 
      return "TASK_STATUS_FAILED";
    case 2: 
      return "TASK_STATUS_PROGRESS";
    }
    return "TASK_STATUS_COMPLETED";
  }
  
  void a(final IDownloadCallback paramIDownloadCallback)
  {
    paramIDownloadCallback = new TimerTask()
    {
      public void run()
      {
        paramIDownloadCallback.destroyimpl();
      }
    };
    new Timer().schedule(paramIDownloadCallback, 60000L);
  }
  
  void a(String paramString, byte paramByte, long paramLong1, long paramLong2)
  {
    String str1 = Byte.toString(paramByte);
    String str2 = Long.toString(paramLong1);
    String str3 = Long.toString(paramLong2);
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage(paramByte);
    localMessage.obj = new String[] { null, paramString, str1, str2, str3 };
    localMessage.sendToTarget();
  }
  
  String b(int paramInt)
  {
    if (paramInt != 100)
    {
      switch (paramInt)
      {
      default: 
        return "TASK_STATUS_NONE";
      case 6: 
        return "TASK_STATUS_CANCELED";
      case 5: 
        return "TASK_STATUS_FAILED";
      case 4: 
        return "TASK_STATUS_TIMEOUT";
      case 3: 
        return "TASK_STATUS_COMPLETED";
      case 2: 
        return "TASK_STATUS_PROGRESS";
      case 1: 
        return "TASK_STATUS_STARTED";
      }
      return "TASK_STATUS_CREATED";
    }
    return "TASK_STATUS_FILE_HAS_DELETED";
  }
  
  public String cancelDownload(String paramString)
  {
    if (isQbUrl(paramString))
    {
      QBDownloadManager.getInstance().cancelDownload();
      return "0";
    }
    DownloadTask localDownloadTask = BaseDownloadManager.getInstance().getTask(paramString);
    if (localDownloadTask != null)
    {
      int i = localDownloadTask.getDownloadTaskId();
      if (i > 0)
      {
        BaseDownloadManager.getInstance().cancelTask(i);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("cancelDownload: 2 url ");
        localStringBuilder.append(paramString);
        localStringBuilder.toString();
        ReporterUtil.getSingleton().reportUncompletedMsg(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), paramString, "4", "cancelDownload");
        localDownloadTask.setFromWhere(null, true);
        return "0";
      }
      return "-2";
    }
    return "-1";
  }
  
  public void destroy()
  {
    HandlerThread localHandlerThread = this.jdField_a_of_type_AndroidOsHandlerThread;
    if ((localHandlerThread != null) && (localHandlerThread.isAlive()) && (this.jdField_a_of_type_AndroidOsHandlerThread.getLooper() != null)) {
      try
      {
        this.jdField_a_of_type_AndroidOsHandlerThread.getLooper().quit();
      }
      catch (RuntimeException localRuntimeException)
      {
        localRuntimeException.printStackTrace();
      }
    }
    ??? = this.jdField_a_of_type_JavaUtilArrayList;
    if (??? != null) {
      ((ArrayList)???).clear();
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.jdField_a_of_type_JavaUtilMap.clear();
      BaseDownloadManager.getInstance().removeTaskObserver(this);
      QBDownloadManager.getInstance().unregisterQBInstallListener(this);
      TBSBroadcastReceiver.getInstance().removeBroadcastObserver(this);
      return;
    }
  }
  
  public int downloadFile(JSONObject arg1, String paramString)
  {
    for (;;)
    {
      try
      {
        str2 = ???.getString("url");
        if (TextUtils.isEmpty(str2)) {
          return -10;
        }
        localObject = a(???, "filename");
        str5 = a(???, "filesize");
        str3 = a(???, "onWifiDownload");
        str4 = a(???, "pkgName");
        bool2 = a(???, "isApk", true);
        str1 = a(???, "businessId");
        ??? = new StringBuilder();
        ???.append("url:");
        ???.append(str2);
        LogUtils.d("DownloadController", ???.toString());
        localBaseDownloadManager = BaseDownloadManager.getInstance();
        localDownloadInfo = new DownloadInfo();
        localDownloadInfo.url = str2;
        if (str5 == null) {}
      }
      catch (JSONException ???)
      {
        String str2;
        Object localObject;
        String str5;
        String str3;
        String str4;
        boolean bool2;
        String str1;
        BaseDownloadManager localBaseDownloadManager;
        DownloadInfo localDownloadInfo;
        long l;
        int i;
        continue;
      }
      try
      {
        l = Long.parseLong(str5);
        if (l > 0L) {
          localDownloadInfo.fileSize = l;
        }
      }
      catch (NumberFormatException ???) {}
    }
    bool1 = TextUtils.isEmpty((CharSequence)localObject);
    if (bool1) {}
    try
    {
      localDownloadInfo.fileName = Uri.parse(str2).getLastPathSegment();
    }
    catch (Throwable ???)
    {
      for (;;)
      {
        continue;
        bool1 = false;
      }
    }
    localDownloadInfo.fileName = ((String)localObject);
    if (TextUtils.isEmpty(localDownloadInfo.fileName))
    {
      localObject = str2.substring(str2.lastIndexOf('/') + 1);
      i = ((String)localObject).indexOf('?');
      ??? = (JSONObject)localObject;
      if (i > 0) {
        ??? = ((String)localObject).substring(0, i);
      }
      localDownloadInfo.fileName = ???;
    }
    localDownloadInfo.fileFolderPath = a().getAbsolutePath();
    localDownloadInfo.flag |= 0x0;
    if (str3 != null)
    {
      bool1 = Boolean.parseBoolean(str3);
      if (!bool1)
      {
        localDownloadInfo.flag |= 0x4;
        if ((Apn.isNetworkAvailable()) && (Apn.isCharge())) {
          Toast.makeText(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), "正在使用移动网络下载", 1).show();
        }
      }
      i = localBaseDownloadManager.startDownload(localDownloadInfo);
      ReporterUtil.getSingleton().reportDownloadMsg(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), str2, "0", null, str4);
      ??? = localBaseDownloadManager.getTaskByID(i);
      if (??? == null) {
        return -11;
      }
      ???.setFromWhere("tbs user create from page", true);
      ???.setCreateTime(System.currentTimeMillis(), true);
      DnsProvider.setDnsResolver(DnsManager.getInstance());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("create task:");
      ((StringBuilder)localObject).append(???.getTaskUrl());
      ((StringBuilder)localObject).append("(");
      ((StringBuilder)localObject).append(i);
      ((StringBuilder)localObject).append(")");
      ((StringBuilder)localObject).toString();
      if (i >= 0) {
        synchronized (this.jdField_a_of_type_JavaLangObject)
        {
          this.jdField_a_of_type_JavaUtilMap.put(Integer.valueOf(i), paramString);
          if (bool2) {
            this.b.put(Integer.valueOf(i), Boolean.valueOf(bool2));
          }
          if (!TextUtils.isEmpty(str1))
          {
            this.jdField_a_of_type_AndroidUtilSparseArray.append(i, str1);
            return 0;
          }
        }
      }
      return 0;
      return -12;
    }
  }
  
  public String downloadQB(String paramString1, JSONObject paramJSONObject, String paramString2)
  {
    if (PackageUtils.isQbAppInstalled(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext())) {
      return String.valueOf(-1);
    }
    String str1 = a(paramJSONObject, "toast", "");
    String str2 = a(paramJSONObject, "triggerKey", "");
    String str3 = a(paramJSONObject, "installedKey", "");
    String str4 = a(paramJSONObject, "positionId");
    String str5 = a(paramJSONObject, "featureId");
    paramJSONObject = a(paramJSONObject, "functionId");
    Bundle localBundle = new Bundle();
    localBundle.putString("param_key_positionid", str4);
    localBundle.putString("param_key_featureid", str5);
    localBundle.putString("param_key_functionid", paramJSONObject);
    localBundle.putString("param_key_download_channel", paramString1);
    this.jdField_a_of_type_JavaUtilArrayList.add(paramString2);
    return String.valueOf(QBDownloadManager.getInstance().startDownload(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), false, str2, str3, str1, null, null, localBundle));
  }
  
  public long getDownloadFileTotalSize(String paramString)
  {
    if (isQbUrl(paramString)) {
      return 100L;
    }
    DownloadTask localDownloadTask = BaseDownloadManager.getInstance().getTask(paramString);
    if (localDownloadTask != null) {
      return localDownloadTask.getTotalSize();
    }
    paramString = BaseDownloadManager.getInstance().getDownloadCompletedTaskFromDatabase(paramString);
    if (paramString != null)
    {
      paramString = BaseDownloadManager.getInstance().getDownloadFileByTask(paramString);
      if ((paramString != null) && (paramString.exists())) {
        return paramString.length();
      }
    }
    return 0L;
  }
  
  public long getDownloadProgress(String paramString)
  {
    if (isQbUrl(paramString)) {
      return QBDownloadManager.getInstance().getQBDownloadProgress();
    }
    paramString = BaseDownloadManager.getInstance().getTask(paramString);
    if (paramString != null) {
      return paramString.getDownloadedSize();
    }
    return 0L;
  }
  
  public String getDownloadStatus(String paramString)
  {
    if (isQbUrl(paramString)) {
      return a(QBDownloadManager.getInstance().getDownloadStatus());
    }
    paramString = BaseDownloadManager.getInstance().getTask(paramString);
    if (paramString == null) {
      return "TASK_STATUS_NONE";
    }
    if (paramString.getStatus() == 3)
    {
      File localFile = BaseDownloadManager.getInstance().getDownloadFileByTask(paramString);
      if ((localFile == null) || (!localFile.exists())) {
        return b(100);
      }
    }
    return b(paramString.getStatus());
  }
  
  public File getDownloadedFile(String paramString)
  {
    paramString = BaseDownloadManager.getInstance().getDownloadCompletedTaskFromDatabase(paramString);
    if (paramString != null) {
      return BaseDownloadManager.getInstance().getDownloadFileByTask(paramString);
    }
    return null;
  }
  
  public String getInstalledAppInfo(final Context paramContext, JSONObject paramJSONObject, final String paramString1, final String paramString2)
  {
    if ((this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback != null) && (paramJSONObject != null))
    {
      final int i = paramJSONObject.optInt("expectedcount", 1);
      BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          JSONObject localJSONObject1 = new JSONObject();
          long l = System.currentTimeMillis();
          Object localObject = b.a().a(paramContext, this.jdField_a_of_type_JavaLangString, i);
          JSONObject localJSONObject2;
          try
          {
            localJSONObject1.put("data", localObject);
          }
          catch (JSONException localJSONException)
          {
            localJSONException.printStackTrace();
            localJSONObject2 = null;
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("getInstalledAppInfo: expected count=");
          ((StringBuilder)localObject).append(i);
          ((StringBuilder)localObject).append(" cost time=");
          ((StringBuilder)localObject).append(System.currentTimeMillis() - l);
          ((StringBuilder)localObject).toString();
          DownloadController.a(DownloadController.this).sendSuccJsCallbackInFrame(paramString2, paramString1, localJSONObject2);
        }
      });
      return "0";
    }
    return "-1";
  }
  
  public String getUninstallAppInfo(final Context paramContext, JSONObject paramJSONObject, final String paramString1, final String paramString2)
  {
    if ((this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback != null) && (paramJSONObject != null))
    {
      final String str = paramJSONObject.optString("exclude", null);
      final int i = paramJSONObject.optInt("type", 1);
      final int j = paramJSONObject.optInt("expectedcount", 1);
      BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          long l = System.currentTimeMillis();
          int i = i;
          Object localObject1 = null;
          Object localObject2;
          if (i == 1)
          {
            localObject1 = b.a().a(paramContext, this.jdField_a_of_type_JavaLangString, str);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("get UnInstallPackageInfo:");
            ((StringBuilder)localObject2).append(String.valueOf(localObject1));
            ((StringBuilder)localObject2).toString();
          }
          else if (i == 2)
          {
            Object localObject3 = b.a().a(paramContext, this.jdField_a_of_type_JavaLangString, str, j);
            localObject2 = new JSONObject();
            try
            {
              ((JSONObject)localObject2).put("data", localObject3);
              localObject3 = new StringBuilder();
              ((StringBuilder)localObject3).append("get uninstalled package infos----result=");
              ((StringBuilder)localObject3).append(((JSONObject)localObject2).toString());
              ((StringBuilder)localObject3).toString();
              localObject1 = localObject2;
            }
            catch (JSONException localJSONException)
            {
              localJSONException.printStackTrace();
            }
          }
          else if (i == 99)
          {
            localObject1 = b.a().b(paramContext, this.jdField_a_of_type_JavaLangString, str);
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("caleb type=");
          localStringBuilder.append(i);
          localStringBuilder.append(" cost time=");
          localStringBuilder.append(System.currentTimeMillis() - l);
          localStringBuilder.toString();
          DownloadController.a(DownloadController.this).sendSuccJsCallbackInFrame(paramString2, paramString1, (JSONObject)localObject1);
        }
      });
      return "0";
    }
    return "-1";
  }
  
  public boolean hasAPKDownloadTask()
  {
    return this.b.size() > 0;
  }
  
  public int installAppWithKey(Context paramContext, String paramString, JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return -1;
    }
    try
    {
      paramString = (String)paramJSONObject.get("key");
      if (TextUtils.isEmpty(paramString)) {
        return -3;
      }
      paramJSONObject = b.a().a(paramString);
      if (paramJSONObject == null) {
        return -4;
      }
      int i = b.a().a(paramContext, paramString);
      if (i == 0) {
        ReporterUtil.getSingleton().statReinstallEvent(paramContext, ReporterUtil.REINSTALL_CLICK, this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getCurrentUrl(), paramJSONObject);
      }
      return i;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return -2;
  }
  
  public String installQB(String paramString1, JSONObject paramJSONObject, String paramString2)
  {
    return downloadQB(paramString1, paramJSONObject, paramString2);
  }
  
  public boolean isQbUrl(String paramString)
  {
    return jsDownload.isQbUrl(paramString);
  }
  
  public boolean isRestrictedAds(String paramString)
  {
    String str = UrlUtils.getHost(paramString);
    boolean bool2 = TextUtils.isEmpty(str);
    boolean bool1 = true;
    if (bool2) {
      return true;
    }
    if (str.endsWith(".gdt.qq.com"))
    {
      if (!paramString.contains("gdt_media_id=90903124471793")) {
        return bool1;
      }
      if (paramString.contains("siteset=15")) {
        return true;
      }
    }
    bool1 = false;
    return bool1;
  }
  
  public void onApkInstallSuccess(int paramInt)
  {
    if (!this.b.containsKey(Integer.valueOf(paramInt))) {
      return;
    }
    this.b.remove(Integer.valueOf(paramInt));
  }
  
  public void onBroadcastReceiver(Intent paramIntent)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("onBroadcastReceiver intent=");
    ((StringBuilder)localObject1).append(paramIntent);
    ((StringBuilder)localObject1).toString();
    if ((paramIntent != null) && ("android.net.conn.CONNECTIVITY_CHANGE".equals(paramIntent.getAction())))
    {
      BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          ??? = new StringBuilder();
          ((StringBuilder)???).append("onBroadcastReceiver isNetworkAvailable:");
          ((StringBuilder)???).append(Apn.isNetworkAvailable());
          ((StringBuilder)???).toString();
          ??? = new StringBuilder();
          ((StringBuilder)???).append("onBroadcastReceiver isCharge:");
          ((StringBuilder)???).append(Apn.isCharge());
          ((StringBuilder)???).toString();
          int i;
          DownloadTask localDownloadTask;
          StringBuilder localStringBuilder;
          if ((Apn.isNetworkAvailable()) && (!Apn.isCharge()))
          {
            if (!Apn.isWifiMode()) {
              break label366;
            }
            if ((DownloadController.a(DownloadController.this) != null) && (DownloadController.a(DownloadController.this).destroyimpl())) {
              return;
            }
            synchronized (DownloadController.a(DownloadController.this))
            {
              Iterator localIterator1 = DownloadController.a(DownloadController.this).keySet().iterator();
              while (localIterator1.hasNext())
              {
                i = ((Integer)localIterator1.next()).intValue();
                localDownloadTask = BaseDownloadManager.getInstance().getTaskByID(i);
                if ((localDownloadTask != null) && (localDownloadTask.getIsPausdedByNoWifi()))
                {
                  localStringBuilder = new StringBuilder();
                  localStringBuilder.append("onBroadcastReceiver resume task:");
                  localStringBuilder.append(i);
                  localStringBuilder.toString();
                  localDownloadTask.setPausedByNoWifi(false, true);
                  BaseDownloadManager.getInstance().resumeTask(i);
                }
              }
              return;
            }
          }
          synchronized (DownloadController.a(DownloadController.this))
          {
            Iterator localIterator2 = DownloadController.a(DownloadController.this).keySet().iterator();
            while (localIterator2.hasNext())
            {
              i = ((Integer)localIterator2.next()).intValue();
              localDownloadTask = BaseDownloadManager.getInstance().getTaskByID(i);
              if (localDownloadTask != null)
              {
                localStringBuilder = new StringBuilder();
                localStringBuilder.append("onBroadcastReceiver pause task:");
                localStringBuilder.append(i);
                localStringBuilder.toString();
                localDownloadTask.setPausedByNoWifi(true, true);
                localDownloadTask.setStatus((byte)11);
              }
            }
            if (DownloadController.a(DownloadController.this) != null) {
              DownloadController.a(DownloadController.this).destroyimpl();
            }
            label366:
            return;
          }
        }
      });
      return;
    }
    if ((paramIntent != null) && (paramIntent.getAction() != null) && (paramIntent.getAction().equals("android.intent.action.PACKAGE_ADDED")))
    {
      if (this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback == null) {
        return;
      }
      Object localObject2 = null;
      localObject1 = localObject2;
      if (paramIntent.getData() != null)
      {
        localObject1 = localObject2;
        if ("package".equals(paramIntent.getData().getScheme())) {
          localObject1 = paramIntent.getData().getSchemeSpecificPart();
        }
      }
      a(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), (String)localObject1);
    }
  }
  
  public boolean onInstallFinished()
  {
    LogUtils.d("DownloadController", "onInstallFinished...");
    if (this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback != null)
    {
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("status", "2");
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getOpenJsHelper().sendSuccJsCallback(str, localJSONObject, true);
      }
    }
    return false;
  }
  
  public boolean onInstalling()
  {
    LogUtils.d("DownloadController", "onInstalling...");
    if (this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback != null)
    {
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("status", "1");
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getOpenJsHelper().sendSuccJsCallback(str, localJSONObject, true);
      }
    }
    return false;
  }
  
  public void onTaskCompleted(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      paramTask = (DownloadTask)paramTask;
      int i = paramTask.getDownloadTaskId();
      if (this.jdField_a_of_type_JavaUtilMap.containsKey(Integer.valueOf(i)))
      {
        a(paramTask.getTaskUrl(), paramTask.getStatus(), paramTask.getDownloadedSize(), paramTask.getTotalSize());
        a(paramTask);
      }
    }
  }
  
  public void onTaskCreated(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      paramTask = (DownloadTask)paramTask;
      if (this.jdField_a_of_type_JavaUtilMap.containsKey(Integer.valueOf(paramTask.getDownloadTaskId()))) {
        a(paramTask.getTaskUrl(), paramTask.getStatus(), paramTask.getDownloadedSize(), paramTask.getTotalSize());
      }
      a(paramTask.getFileFolderPath(), paramTask.getTotalSize());
    }
  }
  
  public void onTaskExtEvent(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      paramTask = (DownloadTask)paramTask;
      if (this.jdField_a_of_type_JavaUtilMap.containsKey(Integer.valueOf(paramTask.getDownloadTaskId()))) {
        a(paramTask.getTaskUrl(), paramTask.getStatus(), paramTask.getDownloadedSize(), paramTask.getTotalSize());
      }
    }
  }
  
  public void onTaskFailed(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      paramTask = (DownloadTask)paramTask;
      Object localObject1;
      Object localObject3;
      if ((paramTask.mDownloadErrorCode == 34) && (a(paramTask)))
      {
        ReporterUtil.getSingleton().reportUncompletedMsg(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), paramTask.mDownloadUrl, "9", "DownloadErrorCode=resolvedomin");
        BaseDownloadManager.getInstance().resumeTask(paramTask.getDownloadTaskId());
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("onTaskFailed:retry ");
        ((StringBuilder)localObject1).append(paramTask.getDownloadTaskId());
        ((StringBuilder)localObject1).toString();
        localObject3 = paramTask.getTaskUrl();
        if ((localObject3 != null) && (((String)localObject3).contains("app=qqbrowser")))
        {
          localObject1 = new HashMap();
          ((HashMap)localObject1).put("errorcode", "ERRORCODE_NETWORK_UNKOWN_HOST_ERROR addRetryUrl");
          paramTask = "JSDOWNLOADQBFAIL";
          localObject2 = ((String)localObject3).split("channel=");
          if (localObject2.length >= 2)
          {
            paramTask = new StringBuilder();
            paramTask.append("JSDOWNLOADQBFAIL");
            paramTask.append(localObject2[1]);
            paramTask = paramTask.toString();
          }
          else
          {
            ((HashMap)localObject1).put("url", localObject3);
          }
          X5CoreBeaconUploader.getInstance(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext()).upLoadToBeacon(paramTask, (Map)localObject1);
        }
        return;
      }
      paramTask.setFromWhere(null, true);
      if (this.jdField_a_of_type_JavaUtilMap.containsKey(Integer.valueOf(paramTask.getDownloadTaskId())))
      {
        a(paramTask.getTaskUrl(), paramTask.getStatus(), paramTask.getDownloadedSize(), paramTask.getTotalSize());
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("onTaskFailed: taskId=");
        ((StringBuilder)localObject1).append(paramTask.getDownloadTaskId());
        ((StringBuilder)localObject1).append(" DownloadErrorCode=");
        ((StringBuilder)localObject1).append(paramTask.mDownloadErrorCode);
        ((StringBuilder)localObject1).append(" url=");
        ((StringBuilder)localObject1).append(paramTask.mDownloadUrl);
        ((StringBuilder)localObject1).toString();
        localObject1 = ReporterUtil.getSingleton();
        localObject2 = this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext();
        localObject3 = paramTask.mDownloadUrl;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("DownloadErrorCode=");
        localStringBuilder.append(paramTask.mDownloadErrorCode);
        ((ReporterUtil)localObject1).reportUncompletedMsg((Context)localObject2, (String)localObject3, "5", localStringBuilder.toString());
        this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.destroyimpl();
      }
      Object localObject2 = paramTask.getTaskUrl();
      if ((localObject2 != null) && (((String)localObject2).contains("app=qqbrowser")))
      {
        localObject1 = new HashMap();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("");
        ((StringBuilder)localObject3).append(paramTask.mDownloadErrorCode);
        ((HashMap)localObject1).put("errorcode", ((StringBuilder)localObject3).toString());
        paramTask = "JSDOWNLOADQBFAIL";
        localObject3 = ((String)localObject2).split("channel=");
        if (localObject3.length >= 2)
        {
          paramTask = new StringBuilder();
          paramTask.append("JSDOWNLOADQBFAIL");
          paramTask.append(localObject3[1]);
          paramTask = paramTask.toString();
        }
        else
        {
          ((HashMap)localObject1).put("url", localObject2);
        }
        X5CoreBeaconUploader.getInstance(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext()).upLoadToBeacon(paramTask, (Map)localObject1);
      }
    }
  }
  
  public void onTaskProgress(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      paramTask = (DownloadTask)paramTask;
      if (this.jdField_a_of_type_JavaUtilMap.containsKey(Integer.valueOf(paramTask.getDownloadTaskId()))) {
        a(paramTask.getTaskUrl(), paramTask.getStatus(), paramTask.getDownloadedSize(), paramTask.getTotalSize());
      }
    }
  }
  
  public void onTaskStarted(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      paramTask = (DownloadTask)paramTask;
      if (this.jdField_a_of_type_JavaUtilMap.containsKey(Integer.valueOf(paramTask.getDownloadTaskId()))) {
        a(paramTask.getTaskUrl(), paramTask.getStatus(), paramTask.getDownloadedSize(), paramTask.getTotalSize());
      }
    }
  }
  
  public boolean onUninstallFinished()
  {
    LogUtils.d("DownloadController", "onUninstallFinished...");
    return false;
  }
  
  public String pauseDownload(String paramString)
  {
    if (isQbUrl(paramString))
    {
      QBDownloadManager.getInstance().pauseDownload();
      return "0";
    }
    DownloadTask localDownloadTask = BaseDownloadManager.getInstance().getTask(paramString);
    if (localDownloadTask != null)
    {
      int i = localDownloadTask.getDownloadTaskId();
      if (i > 0)
      {
        BaseDownloadManager.getInstance().cancelTask(i);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("pauseDownload 2 url=");
        localStringBuilder.append(paramString);
        localStringBuilder.toString();
        ReporterUtil.getSingleton().reportUncompletedMsg(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback.getContext(), paramString, "3", "pauseDownload");
        localDownloadTask.setFromWhere(null, true);
        return "0";
      }
      return "-2";
    }
    return "-1";
  }
  
  public String resumeDownloadFile(String arg1, String paramString2)
  {
    DownloadTask localDownloadTask = BaseDownloadManager.getInstance().getTask(???);
    if (localDownloadTask != null)
    {
      int i = localDownloadTask.getDownloadTaskId();
      if (i > 0) {
        synchronized (this.jdField_a_of_type_JavaLangObject)
        {
          this.jdField_a_of_type_JavaUtilMap.put(Integer.valueOf(i), paramString2);
          a(localDownloadTask.getFileFolderPath(), localDownloadTask.getTotalSize());
          BaseDownloadManager.getInstance().resumeTask(i);
          if (!this.b.containsKey(Integer.valueOf(i))) {
            this.b.put(Integer.valueOf(i), Boolean.valueOf(true));
          }
          return "0";
        }
      }
      return "-2";
    }
    return "-1";
  }
  
  public String setMediaInfo(JSONObject paramJSONObject)
  {
    if ((this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsIDownloadCallback != null) && (paramJSONObject != null))
    {
      if (jdField_a_of_type_JavaUtilHashMap.size() > 10) {
        return "-2";
      }
      String str1 = paramJSONObject.optString("packagename");
      String str2 = paramJSONObject.optString("tbsfrontdomain");
      paramJSONObject = paramJSONObject.optString("tbsadkey");
      if (TextUtils.isEmpty(str1)) {
        return "-1";
      }
      jdField_a_of_type_JavaUtilHashMap.put(str1, new AppAdMediaInfo(str1, str2, paramJSONObject));
      paramJSONObject = new StringBuilder();
      paramJSONObject.append("setMediaInfo: mediaInfoMap=");
      paramJSONObject.append(jdField_a_of_type_JavaUtilHashMap);
      paramJSONObject.toString();
      return "0";
    }
    return "-1";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\utils\DownloadController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */