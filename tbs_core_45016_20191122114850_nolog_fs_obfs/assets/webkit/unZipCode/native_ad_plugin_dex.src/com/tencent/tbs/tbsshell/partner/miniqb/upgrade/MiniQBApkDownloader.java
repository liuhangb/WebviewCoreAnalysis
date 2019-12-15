package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

class MiniQBApkDownloader
{
  private static final String APK_FILENAME = "miniqb.tbs";
  private static final String APK_TEMP_FILENAME = "miniqb.tbs.temp";
  public static final String BACKUPNAME = "backup";
  static final String BACKUP_APK_FILENAME = "miniqb.tbs.org";
  private static final int BUFFER_SIZE = 8192;
  public static final long DOWNLOAD_PERIOD = 86400000L;
  private static final int MAX_RETRY_TIMES = 5;
  private static final long MIN_RETRY_INTERVAL = 20000L;
  private static final int MSG_NETWORK_DETECTOR = 150;
  private static final long NETWORK_DETECT_RETRY_TIME = 120000L;
  private static final long NETWORK_WIFI_CHECK_STEP_SIZE = 1048576L;
  private static final int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
  private static final int SC_TEMPORARY_REDIRECT = 307;
  private static final int WALLED_GARDEN_SOCKET_TIMEOUT_MS = 10000;
  private static final String WALLED_GARDEN_URL = "http://dnet.mb.qq.com/rsp204";
  private static Handler mNetworkDetectorHandler;
  private String mApnInfo;
  private int mApnType;
  private boolean mCanceled;
  private int mConnectTimeout = 20000;
  private long mContentLength;
  private Context mContext;
  private String mDownloadUrl;
  private File mFilePath;
  private boolean mFinished;
  private boolean mHasTmpTryNoRange;
  private HttpURLConnection mHttpRequest;
  private String mLastDownloadUrl;
  private String mLocation;
  private MiniQBLogReport mMiniQBLogReport;
  private boolean mNeedReportLog;
  private int mReadTimeout = 30000;
  private int mRetryTimes;
  private int mRetryTimes302;
  private Set<String> mWifiUnavailableList;
  
  public MiniQBApkDownloader(Context paramContext)
    throws NullPointerException
  {
    this.mContext = paramContext.getApplicationContext();
    this.mWifiUnavailableList = new HashSet();
    this.mFilePath = MiniQBUpgradeManager.getInstance().getMiniQBUpgradeDir(this.mContext);
    if (this.mFilePath != null)
    {
      this.mMiniQBLogReport = new MiniQBLogReport(this.mContext);
      resetArgs();
      this.mApnInfo = null;
      this.mApnType = -1;
      return;
    }
    throw new NullPointerException("getMiniQBUpgradeDir is null!");
  }
  
  public static void backupApk(File paramFile, Context paramContext)
  {
    if (paramFile != null) {
      if (!paramFile.exists()) {
        return;
      }
    }
    try
    {
      paramContext = backupApkPath(paramContext);
      if (paramContext != null)
      {
        paramContext = new File(paramContext, "miniqb.tbs.org");
        paramContext.delete();
        FileUtil.copyFiles(paramFile, paramContext);
      }
      return;
    }
    catch (Exception paramFile) {}
    return;
  }
  
  @TargetApi(8)
  static File backupApkPath(Context paramContext)
  {
    for (;;)
    {
      try
      {
        if (Build.VERSION.SDK_INT >= 8)
        {
          paramContext = new File(FileUtil.getTBSSdcardFilePath(paramContext, 4));
          if ((paramContext != null) && (!paramContext.exists()) && (!paramContext.isDirectory())) {
            paramContext.mkdirs();
          }
          return paramContext;
        }
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("[MiniQBApkDownloader.backupApkPath] Exception is ");
        localStringBuilder.append(paramContext.getMessage());
        Log.e("MiniQBUpgrade", localStringBuilder.toString());
        return null;
      }
      paramContext = null;
    }
  }
  
  public static void clearAllApkFile(Context paramContext)
  {
    try
    {
      File localFile = MiniQBUpgradeManager.getInstance().getMiniQBUpgradeDir(paramContext);
      new File(localFile, "miniqb.tbs").delete();
      new File(localFile, "miniqb.tbs.temp").delete();
      paramContext = backupApkPath(paramContext);
      if (paramContext != null) {
        new File(paramContext, "miniqb.tbs.org").delete();
      }
      return;
    }
    catch (Exception paramContext) {}
  }
  
  private void closeHttpRequest()
  {
    Object localObject = this.mHttpRequest;
    if (localObject != null) {
      if (!this.mCanceled) {
        this.mMiniQBLogReport.setResolveIp(getDomainIp(((HttpURLConnection)localObject).getURL()));
      }
    }
    try
    {
      this.mHttpRequest.disconnect();
      this.mHttpRequest = null;
      if ((!this.mCanceled) && (this.mNeedReportLog))
      {
        localObject = Apn.getApnInfo(this.mContext);
        int i = Apn.getApnType(this.mContext);
        this.mMiniQBLogReport.setApn((String)localObject);
        this.mMiniQBLogReport.setNetworkType(i);
        if ((i != this.mApnType) || (!((String)localObject).equals(this.mApnInfo))) {
          this.mMiniQBLogReport.setNetworkChange(0);
        }
        if (((this.mMiniQBLogReport.mErrorCode == 0) || (this.mMiniQBLogReport.mErrorCode == 107)) && (this.mMiniQBLogReport.getDownFinalFlag() == 0)) {
          if (!Apn.isNetworkAvailable(this.mContext)) {
            setDownloadError(101, "NETWORK_UNAVAILABLE", true);
          } else if (!ping()) {
            setDownloadError(101, "NETWORK_UNAVAILABLE", true);
          }
        }
        this.mMiniQBLogReport.report(MiniQBLogReport.EventType.TYPE_DOWNLOAD);
      }
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  private void closeStream(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException paramCloseable) {}
  }
  
  private boolean copyApkFromBackupToInstall()
  {
    try
    {
      File localFile = new File(this.mFilePath, "miniqb.tbs");
      localObject = backupApkPath(this.mContext);
      if (localObject != null)
      {
        localObject = new File((File)localObject, "miniqb.tbs.org");
        localFile.delete();
        FileUtil.copyFiles((File)localObject, localFile);
      }
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("[MiniQBApkDownloader.copyApkFromBackupToInstall] Exception is ");
      ((StringBuilder)localObject).append(localException.getMessage());
      Log.e("MiniQBUpgrade", ((StringBuilder)localObject).toString());
    }
    return false;
  }
  
  private boolean deleteFile(boolean paramBoolean)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[MiniQBApkDownloader.deleteFile] isApk=");
    ((StringBuilder)localObject).append(paramBoolean);
    ((StringBuilder)localObject).toString();
    if (paramBoolean) {
      localObject = new File(this.mFilePath, "miniqb.tbs");
    } else {
      localObject = new File(this.mFilePath, "miniqb.tbs.temp");
    }
    paramBoolean = true;
    if (((File)localObject).exists()) {
      paramBoolean = ((File)localObject).delete();
    }
    return paramBoolean;
  }
  
  private void deleteFileBackuped()
  {
    try
    {
      File localFile = new File(FileUtil.getTBSSdcardFilePath(this.mContext, 4), "miniqb.tbs.org");
      if (localFile.exists())
      {
        localFile.delete();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  /* Error */
  private boolean detectWifiNetworkAvailable()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   4: invokestatic 246	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/Apn:getApnType	(Landroid/content/Context;)I
    //   7: istore_1
    //   8: iconst_0
    //   9: istore 4
    //   11: iconst_0
    //   12: istore 5
    //   14: iconst_0
    //   15: istore_3
    //   16: iload_1
    //   17: iconst_3
    //   18: if_icmpne +8 -> 26
    //   21: iconst_1
    //   22: istore_2
    //   23: goto +5 -> 28
    //   26: iconst_0
    //   27: istore_2
    //   28: new 189	java/lang/StringBuilder
    //   31: dup
    //   32: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   35: astore 6
    //   37: aload 6
    //   39: ldc_w 319
    //   42: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload 6
    //   48: iload_2
    //   49: invokevirtual 311	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: aload 6
    //   55: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   58: pop
    //   59: aconst_null
    //   60: astore 8
    //   62: aconst_null
    //   63: astore 6
    //   65: iload_2
    //   66: ifeq +262 -> 328
    //   69: aload_0
    //   70: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   73: invokestatic 322	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/Apn:getWifiSSID	(Landroid/content/Context;)Ljava/lang/String;
    //   76: astore 9
    //   78: new 189	java/lang/StringBuilder
    //   81: dup
    //   82: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   85: astore 7
    //   87: aload 7
    //   89: ldc_w 324
    //   92: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: aload 7
    //   98: aload 9
    //   100: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   103: pop
    //   104: aload 7
    //   106: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   109: pop
    //   110: new 326	java/net/URL
    //   113: dup
    //   114: ldc 51
    //   116: invokespecial 327	java/net/URL:<init>	(Ljava/lang/String;)V
    //   119: invokevirtual 331	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   122: checkcast 220	java/net/HttpURLConnection
    //   125: astore 7
    //   127: aload 7
    //   129: iconst_0
    //   130: invokevirtual 335	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   133: aload 7
    //   135: sipush 10000
    //   138: invokevirtual 338	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   141: aload 7
    //   143: sipush 10000
    //   146: invokevirtual 341	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   149: aload 7
    //   151: iconst_0
    //   152: invokevirtual 344	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   155: aload 7
    //   157: invokevirtual 348	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   160: pop
    //   161: aload 7
    //   163: invokevirtual 351	java/net/HttpURLConnection:getResponseCode	()I
    //   166: istore_1
    //   167: new 189	java/lang/StringBuilder
    //   170: dup
    //   171: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   174: astore 6
    //   176: aload 6
    //   178: ldc_w 353
    //   181: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   184: pop
    //   185: aload 6
    //   187: iload_1
    //   188: invokevirtual 356	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   191: pop
    //   192: aload 6
    //   194: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   197: pop
    //   198: iload_3
    //   199: istore_2
    //   200: iload_1
    //   201: sipush 204
    //   204: if_icmpne +5 -> 209
    //   207: iconst_1
    //   208: istore_2
    //   209: aload 9
    //   211: astore 6
    //   213: iload_2
    //   214: istore_3
    //   215: aload 7
    //   217: ifnull +117 -> 334
    //   220: iload_2
    //   221: istore_3
    //   222: aload 7
    //   224: invokevirtual 234	java/net/HttpURLConnection:disconnect	()V
    //   227: aload 9
    //   229: astore 6
    //   231: iload_2
    //   232: istore_3
    //   233: goto +101 -> 334
    //   236: aload 9
    //   238: astore 6
    //   240: goto +94 -> 334
    //   243: astore 6
    //   245: goto +70 -> 315
    //   248: astore 8
    //   250: goto +26 -> 276
    //   253: astore 8
    //   255: aload 6
    //   257: astore 7
    //   259: aload 8
    //   261: astore 6
    //   263: goto +52 -> 315
    //   266: astore 6
    //   268: aload 8
    //   270: astore 7
    //   272: aload 6
    //   274: astore 8
    //   276: aload 7
    //   278: astore 6
    //   280: aload 8
    //   282: invokevirtual 357	java/lang/Throwable:printStackTrace	()V
    //   285: aload 9
    //   287: astore 6
    //   289: iload 4
    //   291: istore_3
    //   292: aload 7
    //   294: ifnull +40 -> 334
    //   297: iload 5
    //   299: istore_3
    //   300: aload 7
    //   302: invokevirtual 234	java/net/HttpURLConnection:disconnect	()V
    //   305: aload 9
    //   307: astore 6
    //   309: iload 4
    //   311: istore_3
    //   312: goto +22 -> 334
    //   315: aload 7
    //   317: ifnull +8 -> 325
    //   320: aload 7
    //   322: invokevirtual 234	java/net/HttpURLConnection:disconnect	()V
    //   325: aload 6
    //   327: athrow
    //   328: aconst_null
    //   329: astore 6
    //   331: iload 4
    //   333: istore_3
    //   334: iload_3
    //   335: ifne +66 -> 401
    //   338: aload 6
    //   340: invokestatic 363	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   343: ifne +58 -> 401
    //   346: aload_0
    //   347: getfield 103	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mWifiUnavailableList	Ljava/util/Set;
    //   350: aload 6
    //   352: invokeinterface 368 2 0
    //   357: ifne +44 -> 401
    //   360: aload_0
    //   361: getfield 103	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mWifiUnavailableList	Ljava/util/Set;
    //   364: aload 6
    //   366: invokeinterface 371 2 0
    //   371: pop
    //   372: aload_0
    //   373: invokespecial 374	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:initNetworkDetectorHandlerIfNeeded	()V
    //   376: getstatic 376	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mNetworkDetectorHandler	Landroid/os/Handler;
    //   379: sipush 150
    //   382: aload 6
    //   384: invokevirtual 382	android/os/Handler:obtainMessage	(ILjava/lang/Object;)Landroid/os/Message;
    //   387: astore 7
    //   389: getstatic 376	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mNetworkDetectorHandler	Landroid/os/Handler;
    //   392: aload 7
    //   394: ldc2_w 38
    //   397: invokevirtual 386	android/os/Handler:sendMessageDelayed	(Landroid/os/Message;J)Z
    //   400: pop
    //   401: iload_3
    //   402: ifeq +29 -> 431
    //   405: aload_0
    //   406: getfield 103	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mWifiUnavailableList	Ljava/util/Set;
    //   409: aload 6
    //   411: invokeinterface 368 2 0
    //   416: ifeq +15 -> 431
    //   419: aload_0
    //   420: getfield 103	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mWifiUnavailableList	Ljava/util/Set;
    //   423: aload 6
    //   425: invokeinterface 389 2 0
    //   430: pop
    //   431: iload_3
    //   432: ireturn
    //   433: astore 6
    //   435: goto -199 -> 236
    //   438: astore 7
    //   440: goto -115 -> 325
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	443	0	this	MiniQBApkDownloader
    //   7	198	1	i	int
    //   22	210	2	bool1	boolean
    //   15	417	3	bool2	boolean
    //   9	323	4	bool3	boolean
    //   12	286	5	bool4	boolean
    //   35	204	6	localObject1	Object
    //   243	13	6	localObject2	Object
    //   261	1	6	localObject3	Object
    //   266	7	6	localThrowable1	Throwable
    //   278	146	6	localObject4	Object
    //   433	1	6	localException1	Exception
    //   85	308	7	localObject5	Object
    //   438	1	7	localException2	Exception
    //   60	1	8	localObject6	Object
    //   248	1	8	localThrowable2	Throwable
    //   253	16	8	localObject7	Object
    //   274	7	8	localThrowable3	Throwable
    //   76	230	9	str	String
    // Exception table:
    //   from	to	target	type
    //   127	198	243	finally
    //   127	198	248	java/lang/Throwable
    //   110	127	253	finally
    //   280	285	253	finally
    //   110	127	266	java/lang/Throwable
    //   222	227	433	java/lang/Exception
    //   300	305	433	java/lang/Exception
    //   320	325	438	java/lang/Exception
  }
  
  private boolean downloadFileExists()
  {
    return new File(this.mFilePath, "miniqb.tbs.temp").exists();
  }
  
  private long downloadFileSize()
  {
    File localFile = new File(this.mFilePath, "miniqb.tbs.temp");
    if (localFile.exists()) {
      return localFile.length();
    }
    return 0L;
  }
  
  private void downloadSuccess(boolean paramBoolean)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("MiniQBApkDownloader.downloadSucess isDownload=");
    ((StringBuilder)localObject).append(paramBoolean);
    ((StringBuilder)localObject).toString();
    localObject = MiniQBDownloadConfig.getInstance(this.mContext);
    ((MiniQBDownloadConfig)localObject).setNeedDownload(false);
    ((MiniQBDownloadConfig)localObject).commit();
    int i = ((MiniQBDownloadConfig)localObject).getMiniQBResponseCode();
    int j = ((MiniQBDownloadConfig)localObject).getMiniQBDownloadVersion();
    if ((i != 3) && (i <= 10000))
    {
      MiniQBInstaller.getInstance().installCore(this.mContext, new File(this.mFilePath, "miniqb.tbs").getAbsolutePath(), j);
      backupApk(new File(this.mFilePath, "miniqb.tbs"), this.mContext);
      return;
    }
    File localFile = backupApkPath(this.mContext);
    if (localFile != null)
    {
      localFile = new File(localFile, "miniqb.tbs.org");
      int k = ApkUtil.getApkVersion(this.mContext, localFile);
      localObject = new File(this.mFilePath, "miniqb.tbs");
      if (((File)localObject).exists()) {
        localObject = ((File)localObject).getAbsolutePath();
      } else {
        localObject = null;
      }
      Bundle localBundle = new Bundle();
      localBundle.putInt("operation", i);
      localBundle.putInt("old_core_ver", k);
      localBundle.putInt("new_core_ver", j);
      localBundle.putString("old_apk_location", localFile.getAbsolutePath());
      localBundle.putString("new_apk_location", (String)localObject);
      localBundle.putString("diff_file_location", (String)localObject);
      localBundle.putBoolean("is_miniqb", true);
      MiniQBInstaller.getInstance().installLocalMiniqbCoreExInThread(this.mContext, localBundle);
      return;
    }
    clearCache();
    ((MiniQBDownloadConfig)localObject).setNeedDownload(true);
    ((MiniQBDownloadConfig)localObject).commit();
  }
  
  private String getDomainIp(URL paramURL)
  {
    try
    {
      paramURL = InetAddress.getByName(paramURL.getHost()).getHostAddress();
      return paramURL;
    }
    catch (Error paramURL)
    {
      paramURL.printStackTrace();
      return "";
    }
    catch (Exception paramURL)
    {
      paramURL.printStackTrace();
    }
    return "";
  }
  
  private void initHttpRequest(String paramString)
    throws Exception
  {
    this.mHttpRequest = ((HttpURLConnection)new URL(paramString).openConnection());
    this.mHttpRequest.setRequestProperty("User-Agent", MiniQBDownloader.getDefaultUserAgent(this.mContext));
    this.mHttpRequest.setRequestProperty("Accept-Encoding", "identity");
    this.mHttpRequest.setRequestMethod("GET");
    this.mHttpRequest.setInstanceFollowRedirects(false);
    this.mHttpRequest.setConnectTimeout(this.mConnectTimeout);
    this.mHttpRequest.setReadTimeout(this.mReadTimeout);
  }
  
  private void initNetworkDetectorHandlerIfNeeded()
  {
    if (mNetworkDetectorHandler == null) {
      try
      {
        mNetworkDetectorHandler = new Handler(MiniQBHandlerThread.getInstance().getLooper())
        {
          public void handleMessage(Message paramAnonymousMessage)
          {
            if (paramAnonymousMessage.what == 150) {
              MiniQBApkDownloader.this.detectWifiNetworkAvailable();
            }
          }
        };
        return;
      }
      catch (Throwable localThrowable)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("initNetworkDetectorHandlerIfNeeded exception:");
        localStringBuilder.append(localThrowable.toString());
        localStringBuilder.toString();
      }
    }
  }
  
  /* Error */
  private boolean ping()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   4: invokevirtual 96	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   7: invokevirtual 525	android/content/Context:getPackageName	()Ljava/lang/String;
    //   10: ldc_w 527
    //   13: invokevirtual 259	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   16: istore_3
    //   17: iload_3
    //   18: ifeq +12 -> 30
    //   21: iconst_1
    //   22: ireturn
    //   23: astore 6
    //   25: aload 6
    //   27: invokevirtual 187	java/lang/Exception:printStackTrace	()V
    //   30: invokestatic 533	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   33: astore 6
    //   35: iconst_0
    //   36: istore 5
    //   38: iconst_0
    //   39: istore 4
    //   41: aconst_null
    //   42: astore 9
    //   44: aconst_null
    //   45: astore 10
    //   47: new 189	java/lang/StringBuilder
    //   50: dup
    //   51: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   54: astore 7
    //   56: aload 7
    //   58: ldc_w 535
    //   61: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: pop
    //   65: aload 7
    //   67: ldc_w 537
    //   70: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   73: pop
    //   74: aload 6
    //   76: aload 7
    //   78: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   81: invokevirtual 541	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
    //   84: invokevirtual 544	java/lang/Process:getInputStream	()Ljava/io/InputStream;
    //   87: astore 8
    //   89: new 546	java/io/InputStreamReader
    //   92: dup
    //   93: aload 8
    //   95: invokespecial 549	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   98: astore 7
    //   100: new 551	java/io/BufferedReader
    //   103: dup
    //   104: aload 7
    //   106: invokespecial 554	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   109: astore 9
    //   111: iconst_0
    //   112: istore_1
    //   113: aload 9
    //   115: invokevirtual 557	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   118: astore 6
    //   120: iload 4
    //   122: istore_3
    //   123: aload 6
    //   125: ifnull +49 -> 174
    //   128: aload 6
    //   130: ldc_w 559
    //   133: invokevirtual 561	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   136: ifne +36 -> 172
    //   139: aload 6
    //   141: ldc_w 563
    //   144: invokevirtual 561	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   147: istore_3
    //   148: iload_3
    //   149: ifeq +6 -> 155
    //   152: goto +20 -> 172
    //   155: iload_1
    //   156: iconst_1
    //   157: iadd
    //   158: istore_2
    //   159: iload_2
    //   160: istore_1
    //   161: iload_2
    //   162: iconst_5
    //   163: if_icmplt -50 -> 113
    //   166: iload 4
    //   168: istore_3
    //   169: goto +5 -> 174
    //   172: iconst_1
    //   173: istore_3
    //   174: aload_0
    //   175: aload 8
    //   177: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   180: aload 7
    //   182: astore 6
    //   184: aload 9
    //   186: astore 7
    //   188: goto +128 -> 316
    //   191: astore 6
    //   193: aload 7
    //   195: astore 10
    //   197: aload 9
    //   199: astore 7
    //   201: goto +139 -> 340
    //   204: astore 6
    //   206: aload 7
    //   208: astore 10
    //   210: aload 9
    //   212: astore 7
    //   214: goto +52 -> 266
    //   217: astore 6
    //   219: aconst_null
    //   220: astore 9
    //   222: aload 7
    //   224: astore 10
    //   226: aload 9
    //   228: astore 7
    //   230: goto +110 -> 340
    //   233: astore 6
    //   235: aconst_null
    //   236: astore 9
    //   238: aload 7
    //   240: astore 10
    //   242: aload 9
    //   244: astore 7
    //   246: goto +20 -> 266
    //   249: astore 6
    //   251: aconst_null
    //   252: astore 7
    //   254: goto +90 -> 344
    //   257: astore 6
    //   259: aconst_null
    //   260: astore 10
    //   262: aload 10
    //   264: astore 7
    //   266: aload 6
    //   268: astore 9
    //   270: aload 10
    //   272: astore 6
    //   274: goto +28 -> 302
    //   277: astore 6
    //   279: aconst_null
    //   280: astore 8
    //   282: aload 8
    //   284: astore 7
    //   286: goto +58 -> 344
    //   289: astore 9
    //   291: aconst_null
    //   292: astore 6
    //   294: aload 6
    //   296: astore 7
    //   298: aload 10
    //   300: astore 8
    //   302: aload 9
    //   304: invokevirtual 357	java/lang/Throwable:printStackTrace	()V
    //   307: aload_0
    //   308: aload 8
    //   310: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   313: iload 5
    //   315: istore_3
    //   316: aload_0
    //   317: aload 6
    //   319: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   322: aload_0
    //   323: aload 7
    //   325: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   328: iload_3
    //   329: ireturn
    //   330: astore 9
    //   332: aload 6
    //   334: astore 10
    //   336: aload 9
    //   338: astore 6
    //   340: aload 10
    //   342: astore 9
    //   344: aload_0
    //   345: aload 8
    //   347: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   350: aload_0
    //   351: aload 9
    //   353: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   356: aload_0
    //   357: aload 7
    //   359: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   362: aload 6
    //   364: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	365	0	this	MiniQBApkDownloader
    //   112	49	1	i	int
    //   158	6	2	j	int
    //   16	313	3	bool1	boolean
    //   39	128	4	bool2	boolean
    //   36	278	5	bool3	boolean
    //   23	3	6	localException	Exception
    //   33	150	6	localObject1	Object
    //   191	1	6	localObject2	Object
    //   204	1	6	localThrowable1	Throwable
    //   217	1	6	localObject3	Object
    //   233	1	6	localThrowable2	Throwable
    //   249	1	6	localObject4	Object
    //   257	10	6	localThrowable3	Throwable
    //   272	1	6	localObject5	Object
    //   277	1	6	localObject6	Object
    //   292	71	6	localObject7	Object
    //   54	304	7	localObject8	Object
    //   87	259	8	localObject9	Object
    //   42	227	9	localObject10	Object
    //   289	14	9	localThrowable4	Throwable
    //   330	7	9	localObject11	Object
    //   342	10	9	localObject12	Object
    //   45	296	10	localObject13	Object
    // Exception table:
    //   from	to	target	type
    //   0	17	23	java/lang/Exception
    //   113	120	191	finally
    //   128	148	191	finally
    //   113	120	204	java/lang/Throwable
    //   128	148	204	java/lang/Throwable
    //   100	111	217	finally
    //   100	111	233	java/lang/Throwable
    //   89	100	249	finally
    //   89	100	257	java/lang/Throwable
    //   47	89	277	finally
    //   47	89	289	java/lang/Throwable
    //   302	307	330	finally
  }
  
  private void resetArgs()
  {
    this.mRetryTimes = 0;
    this.mRetryTimes302 = 0;
    this.mContentLength = -1L;
    this.mLocation = null;
    this.mHasTmpTryNoRange = false;
    this.mCanceled = false;
    this.mFinished = false;
    this.mNeedReportLog = false;
  }
  
  private void retry(long paramLong)
  {
    long l = paramLong;
    if (paramLong <= 0L) {}
    try
    {
      l = retryInterval();
      Thread.sleep(l);
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    this.mRetryTimes += 1;
  }
  
  private long retryInterval()
  {
    int i = this.mRetryTimes;
    long l = 20000L;
    switch (i)
    {
    default: 
      break;
    case 3: 
    case 4: 
      l = 100000L;
      break;
    case 1: 
    case 2: 
      return i * 20000L;
    }
    return 10L * l;
  }
  
  private long saveDownloadErrorData(long paramLong1, long paramLong2)
  {
    long l = System.currentTimeMillis();
    this.mMiniQBLogReport.setDownConsumeTime(l - paramLong1);
    this.mMiniQBLogReport.setDownloadSize(paramLong2);
    return l;
  }
  
  private void setDownloadError(int paramInt, String paramString, boolean paramBoolean)
  {
    if ((paramBoolean) || (this.mRetryTimes > 5))
    {
      this.mMiniQBLogReport.setErrorCode(paramInt);
      this.mMiniQBLogReport.setFailDetail(paramString);
    }
  }
  
  private void setDownloadError(int paramInt, Throwable paramThrowable, boolean paramBoolean)
  {
    if ((paramBoolean) || (this.mRetryTimes > 5))
    {
      this.mMiniQBLogReport.setErrorCode(paramInt);
      this.mMiniQBLogReport.setFailDetail(paramThrowable);
    }
  }
  
  private boolean verifyApk(boolean paramBoolean1, boolean paramBoolean2)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("[MiniQBApkDownloader.verifyApk] isTempFile=");
    ((StringBuilder)localObject1).append(paramBoolean1);
    ((StringBuilder)localObject1).toString();
    Object localObject2 = this.mFilePath;
    if (!paramBoolean1) {
      localObject1 = "miniqb.tbs";
    } else {
      localObject1 = "miniqb.tbs.temp";
    }
    localObject2 = new File((File)localObject2, (String)localObject1);
    if (!((File)localObject2).exists()) {
      return false;
    }
    Object localObject3 = MiniQBDownloadConfig.getInstance(this.mContext).getMd5();
    localObject1 = ApkUtil.getMd5((File)localObject2);
    StringBuilder localStringBuilder1;
    if ((localObject3 != null) && (((String)localObject3).equals(localObject1)))
    {
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("[MiniQBApkDownloader.verifyApk] md5(");
      ((StringBuilder)localObject3).append((String)localObject1);
      ((StringBuilder)localObject3).append(") successful!");
      ((StringBuilder)localObject3).toString();
      long l1 = 0L;
      long l2 = l1;
      if (paramBoolean1)
      {
        long l3 = MiniQBDownloadConfig.getInstance(this.mContext).getMiniQBFileSize();
        l2 = l1;
        if (((File)localObject2).exists())
        {
          l2 = l1;
          if (l3 > 0L)
          {
            l1 = ((File)localObject2).length();
            l2 = l1;
            if (l3 != l1) {
              l2 = l1;
            }
          }
        }
        else
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("[MiniQBApkDownloader.verifyApk] isTempFile=");
          ((StringBuilder)localObject1).append(paramBoolean1);
          ((StringBuilder)localObject1).append(" filelength failed");
          ((StringBuilder)localObject1).toString();
          localObject1 = this.mMiniQBLogReport;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("fileLength:");
          ((StringBuilder)localObject2).append(l2);
          ((StringBuilder)localObject2).append(",contentLength:");
          ((StringBuilder)localObject2).append(l3);
          ((MiniQBLogReport)localObject1).setCheckErrorDetail(((StringBuilder)localObject2).toString());
          return false;
        }
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("[MiniQBApkDownloader.verifyApk] length(");
      ((StringBuilder)localObject1).append(l2);
      ((StringBuilder)localObject1).append(") successful!");
      ((StringBuilder)localObject1).toString();
      int i = -1;
      if (paramBoolean2)
      {
        int j = ApkUtil.getApkVersion(this.mContext, (File)localObject2);
        int k = MiniQBDownloadConfig.getInstance(this.mContext).getMiniQBDownloadVersion();
        i = j;
        if (k != j)
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("[MiniQBApkDownloader.verifyApk] isTempFile=");
          ((StringBuilder)localObject1).append(paramBoolean1);
          ((StringBuilder)localObject1).append(" versionCode failed isApkFile:");
          ((StringBuilder)localObject1).append(paramBoolean2);
          ((StringBuilder)localObject1).toString();
          if (paramBoolean1)
          {
            localObject1 = this.mMiniQBLogReport;
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("fileVersion:");
            ((StringBuilder)localObject2).append(j);
            ((StringBuilder)localObject2).append(",configVersion:");
            ((StringBuilder)localObject2).append(k);
            ((MiniQBLogReport)localObject1).setCheckErrorDetail(((StringBuilder)localObject2).toString());
          }
          return false;
        }
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("[MiniQBApkDownloader.verifyApk] ApkVersionCode(");
      ((StringBuilder)localObject1).append(i);
      ((StringBuilder)localObject1).append(") successful!");
      ((StringBuilder)localObject1).toString();
      if (paramBoolean1)
      {
        localObject1 = null;
        try
        {
          paramBoolean1 = ((File)localObject2).renameTo(new File(this.mFilePath, "miniqb.tbs"));
        }
        catch (Exception localException)
        {
          paramBoolean1 = false;
        }
        paramBoolean2 = paramBoolean1;
        if (!paramBoolean1)
        {
          setDownloadError(109, localException, true);
          return false;
        }
      }
      else
      {
        paramBoolean2 = false;
      }
      localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("[MiniQBApkDownloader.verifyApk] rename(");
      localStringBuilder1.append(paramBoolean2);
      localStringBuilder1.append(") successful!");
      localStringBuilder1.toString();
      return true;
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("[MiniQBApkDownloader.verifyApk] isTempFile=");
    ((StringBuilder)localObject2).append(paramBoolean1);
    ((StringBuilder)localObject2).append(" md5 failed");
    ((StringBuilder)localObject2).toString();
    if (paramBoolean1)
    {
      localObject2 = this.mMiniQBLogReport;
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append("fileMd5 not match filemd5:");
      localStringBuilder2.append(localStringBuilder1);
      localStringBuilder2.append(",configMd5:");
      localStringBuilder2.append((String)localObject3);
      ((MiniQBLogReport)localObject2).setCheckErrorDetail(localStringBuilder2.toString());
    }
    return false;
  }
  
  private boolean verifyApkBackuped()
  {
    File localFile = new File(FileUtil.getTBSSdcardFilePath(this.mContext, 4), "miniqb.tbs.org");
    int i = MiniQBDownloadConfig.getInstance(this.mContext).getMiniQBDownloadVersion();
    return ApkUtil.verifyTbsApk(this.mContext, localFile, 0L, i);
  }
  
  private boolean verifyLocalApk(File paramFile)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[MiniQBApkDownloader.verifyLocalApk] apk=");
    localStringBuilder.append(paramFile);
    localStringBuilder.toString();
    if (!"3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a".equals(ApkUtil.getSignatureFromApk(this.mContext, paramFile)))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("[MiniQBApkDownloader.verifyLocalApk] apk:");
      localStringBuilder.append(paramFile);
      localStringBuilder.append(" signature failed");
      localStringBuilder.toString();
      return false;
    }
    return true;
  }
  
  public int backupApkVersion()
  {
    File localFile = backupApkPath(this.mContext);
    if (localFile == null) {
      return 0;
    }
    return ApkUtil.getApkVersion(this.mContext, new File(localFile, "miniqb.tbs.org"));
  }
  
  public void clearCache()
  {
    stopDownload();
    deleteFile(false);
    deleteFile(true);
  }
  
  public boolean hasEnoughFreeSpace()
  {
    long l = FileUtil.getFreeSpace(this.mFilePath.getAbsolutePath());
    boolean bool;
    if (l >= MiniQBDownloadConfig.getInstance(this.mContext).getMinFreeSpace()) {
      bool = true;
    } else {
      bool = false;
    }
    if (!bool)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[MiniQBApkDownloader.hasEnoughFreeSpace] freeSpace too small,  freeSpace = ");
      localStringBuilder.append(l);
      Log.e("MiniQBUpgrade", localStringBuilder.toString());
    }
    return bool;
  }
  
  public void removeApkIfNeeded(int paramInt)
  {
    try
    {
      File localFile = new File(this.mFilePath, "miniqb.tbs");
      int i = ApkUtil.getApkVersion(this.mContext, localFile);
      if ((paramInt > 0) && (paramInt == i)) {
        localFile.delete();
      }
      return;
    }
    catch (Exception localException) {}
  }
  
  /* Error */
  public void startDownload()
  {
    // Byte code:
    //   0: invokestatic 420	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:getInstance	()Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller;
    //   3: aload_0
    //   4: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   7: invokevirtual 711	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstaller:isMiniQBLocalInstalled	(Landroid/content/Context;)Z
    //   10: ifeq +4 -> 14
    //   13: return
    //   14: aload_0
    //   15: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   18: invokestatic 403	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig;
    //   21: invokevirtual 412	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getMiniQBResponseCode	()I
    //   24: istore_1
    //   25: iload_1
    //   26: iconst_1
    //   27: if_icmpeq +16 -> 43
    //   30: iload_1
    //   31: iconst_2
    //   32: if_icmpne +6 -> 38
    //   35: goto +8 -> 43
    //   38: iconst_0
    //   39: istore_3
    //   40: goto +5 -> 45
    //   43: iconst_1
    //   44: istore_3
    //   45: iload_3
    //   46: ifeq +27 -> 73
    //   49: aload_0
    //   50: invokespecial 713	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:verifyApkBackuped	()Z
    //   53: ifeq +16 -> 69
    //   56: aload_0
    //   57: invokespecial 715	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:copyApkFromBackupToInstall	()Z
    //   60: ifeq +13 -> 73
    //   63: aload_0
    //   64: iconst_0
    //   65: invokespecial 717	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:downloadSuccess	(Z)V
    //   68: return
    //   69: aload_0
    //   70: invokespecial 719	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:deleteFileBackuped	()V
    //   73: aload_0
    //   74: iconst_0
    //   75: iload_3
    //   76: invokespecial 721	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:verifyApk	(ZZ)Z
    //   79: ifeq +9 -> 88
    //   82: aload_0
    //   83: iconst_0
    //   84: invokespecial 717	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:downloadSuccess	(Z)V
    //   87: return
    //   88: aload_0
    //   89: iconst_1
    //   90: invokespecial 696	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:deleteFile	(Z)Z
    //   93: ifne +21 -> 114
    //   96: aload_0
    //   97: iconst_1
    //   98: invokespecial 696	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:deleteFile	(Z)Z
    //   101: ifne +13 -> 114
    //   104: ldc -54
    //   106: ldc_w 723
    //   109: invokestatic 211	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   112: pop
    //   113: return
    //   114: aload_0
    //   115: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   118: invokestatic 403	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig;
    //   121: astore 24
    //   123: aload_0
    //   124: aload 24
    //   126: invokevirtual 726	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getMiniQBDownloadUrl	()Ljava/lang/String;
    //   129: putfield 728	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mDownloadUrl	Ljava/lang/String;
    //   132: new 189	java/lang/StringBuilder
    //   135: dup
    //   136: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   139: astore 19
    //   141: aload 19
    //   143: ldc_w 730
    //   146: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: pop
    //   150: aload 19
    //   152: aload_0
    //   153: getfield 728	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mDownloadUrl	Ljava/lang/String;
    //   156: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   159: pop
    //   160: aload 19
    //   162: ldc_w 732
    //   165: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   168: pop
    //   169: aload 19
    //   171: aload_0
    //   172: getfield 575	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mLocation	Ljava/lang/String;
    //   175: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   178: pop
    //   179: aload 19
    //   181: ldc_w 734
    //   184: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   187: pop
    //   188: aload 19
    //   190: aload_0
    //   191: getfield 218	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mCanceled	Z
    //   194: invokevirtual 311	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   197: pop
    //   198: aload 19
    //   200: ldc_w 736
    //   203: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   206: pop
    //   207: aload 19
    //   209: aload_0
    //   210: getfield 216	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   213: invokevirtual 680	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   216: pop
    //   217: aload 19
    //   219: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   222: pop
    //   223: aload_0
    //   224: getfield 728	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mDownloadUrl	Ljava/lang/String;
    //   227: ifnonnull +11 -> 238
    //   230: aload_0
    //   231: getfield 575	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mLocation	Ljava/lang/String;
    //   234: ifnonnull +4 -> 238
    //   237: return
    //   238: aload_0
    //   239: getfield 216	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   242: ifnull +11 -> 253
    //   245: aload_0
    //   246: getfield 218	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mCanceled	Z
    //   249: ifne +4 -> 253
    //   252: return
    //   253: aload_0
    //   254: getfield 103	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mWifiUnavailableList	Ljava/util/Set;
    //   257: aload_0
    //   258: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   261: invokestatic 322	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/Apn:getWifiSSID	(Landroid/content/Context;)Ljava/lang/String;
    //   264: invokeinterface 368 2 0
    //   269: ifeq +4 -> 273
    //   272: return
    //   273: aload_0
    //   274: invokespecial 124	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:resetArgs	()V
    //   277: aload 24
    //   279: invokevirtual 739	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getDownloadMaxFlow	()J
    //   282: lstore 15
    //   284: aload_0
    //   285: getfield 567	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mRetryTimes	I
    //   288: iconst_5
    //   289: if_icmple +6 -> 295
    //   292: goto +2036 -> 2328
    //   295: aload_0
    //   296: getfield 569	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mRetryTimes302	I
    //   299: bipush 8
    //   301: if_icmple +6 -> 307
    //   304: goto +2024 -> 2328
    //   307: invokestatic 600	java/lang/System:currentTimeMillis	()J
    //   310: lstore 11
    //   312: lload 11
    //   314: aload 24
    //   316: invokevirtual 742	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getDownloadStartTime	()J
    //   319: lsub
    //   320: ldc2_w 28
    //   323: lcmp
    //   324: ifle +19 -> 343
    //   327: aload 24
    //   329: lload 11
    //   331: invokevirtual 745	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:setDownloadStartTime	(J)V
    //   334: aload 24
    //   336: lconst_0
    //   337: invokevirtual 748	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:setDownloadFlow	(J)V
    //   340: goto +53 -> 393
    //   343: aload 24
    //   345: invokevirtual 751	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getDownloadFlow	()J
    //   348: lstore 5
    //   350: new 189	java/lang/StringBuilder
    //   353: dup
    //   354: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   357: astore 19
    //   359: aload 19
    //   361: ldc_w 753
    //   364: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   367: pop
    //   368: aload 19
    //   370: lload 5
    //   372: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   375: pop
    //   376: aload 19
    //   378: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   381: pop
    //   382: lload 5
    //   384: lload 15
    //   386: lcmp
    //   387: iflt +6 -> 393
    //   390: goto +1938 -> 2328
    //   393: aload_0
    //   394: invokevirtual 755	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:hasEnoughFreeSpace	()Z
    //   397: ifne +6 -> 403
    //   400: goto +1928 -> 2328
    //   403: aload_0
    //   404: iconst_1
    //   405: putfield 236	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mNeedReportLog	Z
    //   408: aload_0
    //   409: getfield 575	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mLocation	Ljava/lang/String;
    //   412: ifnull +12 -> 424
    //   415: aload_0
    //   416: getfield 575	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mLocation	Ljava/lang/String;
    //   419: astore 19
    //   421: goto +9 -> 430
    //   424: aload_0
    //   425: getfield 728	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mDownloadUrl	Ljava/lang/String;
    //   428: astore 19
    //   430: aload 19
    //   432: aload_0
    //   433: getfield 757	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mLastDownloadUrl	Ljava/lang/String;
    //   436: invokevirtual 259	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   439: ifne +12 -> 451
    //   442: aload_0
    //   443: getfield 121	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mMiniQBLogReport	Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport;
    //   446: aload 19
    //   448: invokevirtual 760	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport:setDownloadUrl	(Ljava/lang/String;)V
    //   451: aload_0
    //   452: getfield 121	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mMiniQBLogReport	Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport;
    //   455: aload 24
    //   457: invokevirtual 415	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getMiniQBDownloadVersion	()I
    //   460: invokevirtual 763	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport:setDownloadMiniQBVersion	(I)V
    //   463: aload_0
    //   464: aload 19
    //   466: putfield 757	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mLastDownloadUrl	Ljava/lang/String;
    //   469: aload_0
    //   470: aload 19
    //   472: invokespecial 765	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:initHttpRequest	(Ljava/lang/String;)V
    //   475: aload_0
    //   476: getfield 577	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHasTmpTryNoRange	Z
    //   479: ifne +2065 -> 2544
    //   482: aload_0
    //   483: invokespecial 767	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:downloadFileSize	()J
    //   486: lstore 5
    //   488: new 189	java/lang/StringBuilder
    //   491: dup
    //   492: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   495: astore 19
    //   497: aload 19
    //   499: ldc_w 769
    //   502: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   505: pop
    //   506: aload 19
    //   508: lload 5
    //   510: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   513: pop
    //   514: aload 19
    //   516: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   519: pop
    //   520: aload_0
    //   521: getfield 573	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContentLength	J
    //   524: lconst_0
    //   525: lcmp
    //   526: ifgt +92 -> 618
    //   529: new 189	java/lang/StringBuilder
    //   532: dup
    //   533: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   536: astore 19
    //   538: aload 19
    //   540: ldc_w 771
    //   543: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   546: pop
    //   547: aload 19
    //   549: lload 5
    //   551: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   554: pop
    //   555: aload 19
    //   557: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   560: pop
    //   561: aload_0
    //   562: getfield 216	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   565: astore 19
    //   567: new 189	java/lang/StringBuilder
    //   570: dup
    //   571: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   574: astore 20
    //   576: aload 20
    //   578: ldc_w 773
    //   581: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   584: pop
    //   585: aload 20
    //   587: lload 5
    //   589: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   592: pop
    //   593: aload 20
    //   595: ldc_w 775
    //   598: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   601: pop
    //   602: aload 19
    //   604: ldc_w 777
    //   607: aload 20
    //   609: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   612: invokevirtual 498	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   615: goto +121 -> 736
    //   618: new 189	java/lang/StringBuilder
    //   621: dup
    //   622: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   625: astore 19
    //   627: aload 19
    //   629: ldc_w 779
    //   632: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   635: pop
    //   636: aload 19
    //   638: lload 5
    //   640: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   643: pop
    //   644: aload 19
    //   646: ldc_w 781
    //   649: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   652: pop
    //   653: aload 19
    //   655: aload_0
    //   656: getfield 573	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContentLength	J
    //   659: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   662: pop
    //   663: aload 19
    //   665: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   668: pop
    //   669: aload_0
    //   670: getfield 216	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   673: astore 19
    //   675: new 189	java/lang/StringBuilder
    //   678: dup
    //   679: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   682: astore 20
    //   684: aload 20
    //   686: ldc_w 773
    //   689: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   692: pop
    //   693: aload 20
    //   695: lload 5
    //   697: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   700: pop
    //   701: aload 20
    //   703: ldc_w 775
    //   706: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   709: pop
    //   710: aload 20
    //   712: aload_0
    //   713: getfield 573	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContentLength	J
    //   716: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   719: pop
    //   720: aload 19
    //   722: ldc_w 777
    //   725: aload 20
    //   727: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   730: invokevirtual 498	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   733: goto +3 -> 736
    //   736: iload_3
    //   737: istore 4
    //   739: aload_0
    //   740: getfield 121	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mMiniQBLogReport	Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport;
    //   743: astore 19
    //   745: lload 5
    //   747: lconst_0
    //   748: lcmp
    //   749: ifne +1801 -> 2550
    //   752: iconst_0
    //   753: istore_1
    //   754: goto +3 -> 757
    //   757: aload 19
    //   759: iload_1
    //   760: invokevirtual 784	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport:setDownloadCancel	(I)V
    //   763: aload_0
    //   764: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   767: invokestatic 246	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/Apn:getApnType	(Landroid/content/Context;)I
    //   770: istore_1
    //   771: aload_0
    //   772: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   775: invokestatic 242	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/Apn:getApnInfo	(Landroid/content/Context;)Ljava/lang/String;
    //   778: astore 19
    //   780: aload_0
    //   781: getfield 126	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mApnInfo	Ljava/lang/String;
    //   784: ifnonnull +25 -> 809
    //   787: aload_0
    //   788: getfield 128	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mApnType	I
    //   791: iconst_m1
    //   792: if_icmpne +17 -> 809
    //   795: aload_0
    //   796: aload 19
    //   798: putfield 126	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mApnInfo	Ljava/lang/String;
    //   801: aload_0
    //   802: iload_1
    //   803: putfield 128	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mApnType	I
    //   806: goto +42 -> 848
    //   809: iload_1
    //   810: aload_0
    //   811: getfield 128	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mApnType	I
    //   814: if_icmpne +15 -> 829
    //   817: aload 19
    //   819: aload_0
    //   820: getfield 126	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mApnInfo	Ljava/lang/String;
    //   823: invokevirtual 259	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   826: ifne +22 -> 848
    //   829: aload_0
    //   830: getfield 121	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mMiniQBLogReport	Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport;
    //   833: iconst_0
    //   834: invokevirtual 262	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport:setNetworkChange	(I)V
    //   837: aload_0
    //   838: aload 19
    //   840: putfield 126	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mApnInfo	Ljava/lang/String;
    //   843: aload_0
    //   844: iload_1
    //   845: putfield 128	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mApnType	I
    //   848: aload_0
    //   849: getfield 567	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mRetryTimes	I
    //   852: iconst_1
    //   853: if_icmplt +17 -> 870
    //   856: aload_0
    //   857: getfield 216	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   860: ldc_w 786
    //   863: aload_0
    //   864: getfield 728	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mDownloadUrl	Ljava/lang/String;
    //   867: invokevirtual 789	java/net/HttpURLConnection:addRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   870: aload_0
    //   871: getfield 216	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   874: invokevirtual 351	java/net/HttpURLConnection:getResponseCode	()I
    //   877: istore_1
    //   878: new 189	java/lang/StringBuilder
    //   881: dup
    //   882: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   885: astore 19
    //   887: aload 19
    //   889: ldc_w 791
    //   892: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   895: pop
    //   896: aload 19
    //   898: iload_1
    //   899: invokevirtual 356	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   902: pop
    //   903: aload 19
    //   905: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   908: pop
    //   909: aload_0
    //   910: getfield 121	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mMiniQBLogReport	Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport;
    //   913: iload_1
    //   914: invokevirtual 794	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport:setHttpCode	(I)V
    //   917: aload_0
    //   918: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   921: invokestatic 246	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/Apn:getApnType	(Landroid/content/Context;)I
    //   924: iconst_3
    //   925: if_icmpne +13 -> 938
    //   928: aload_0
    //   929: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   932: invokestatic 246	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/Apn:getApnType	(Landroid/content/Context;)I
    //   935: ifne +7 -> 942
    //   938: aload_0
    //   939: invokevirtual 694	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:stopDownload	()V
    //   942: aload_0
    //   943: getfield 218	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mCanceled	Z
    //   946: ifeq +1609 -> 2555
    //   949: goto +1379 -> 2328
    //   952: iload_1
    //   953: sipush 300
    //   956: if_icmplt +52 -> 1008
    //   959: iload_1
    //   960: sipush 307
    //   963: if_icmpgt +45 -> 1008
    //   966: aload_0
    //   967: getfield 216	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   970: ldc_w 796
    //   973: invokevirtual 800	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
    //   976: astore 19
    //   978: aload 19
    //   980: invokestatic 363	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   983: ifne +1345 -> 2328
    //   986: aload_0
    //   987: aload 19
    //   989: putfield 575	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mLocation	Ljava/lang/String;
    //   992: aload_0
    //   993: aload_0
    //   994: getfield 569	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mRetryTimes302	I
    //   997: iconst_1
    //   998: iadd
    //   999: putfield 569	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mRetryTimes302	I
    //   1002: iload 4
    //   1004: istore_3
    //   1005: goto -721 -> 284
    //   1008: new 189	java/lang/StringBuilder
    //   1011: dup
    //   1012: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   1015: astore 19
    //   1017: aload 19
    //   1019: ldc_w 802
    //   1022: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1025: pop
    //   1026: aload 19
    //   1028: iload_1
    //   1029: invokevirtual 356	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1032: pop
    //   1033: aload_0
    //   1034: bipush 102
    //   1036: aload 19
    //   1038: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1041: iconst_0
    //   1042: invokespecial 279	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:setDownloadError	(ILjava/lang/String;Z)V
    //   1045: iload_1
    //   1046: sipush 416
    //   1049: if_icmpne +1523 -> 2572
    //   1052: aload_0
    //   1053: iconst_1
    //   1054: iconst_1
    //   1055: invokespecial 721	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:verifyApk	(ZZ)Z
    //   1058: ifeq +9 -> 1067
    //   1061: iconst_1
    //   1062: istore 4
    //   1064: goto +1267 -> 2331
    //   1067: aload_0
    //   1068: iconst_0
    //   1069: invokespecial 696	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:deleteFile	(Z)Z
    //   1072: pop
    //   1073: goto +1255 -> 2328
    //   1076: aload_0
    //   1077: getfield 573	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContentLength	J
    //   1080: ldc2_w 570
    //   1083: lcmp
    //   1084: ifne +1505 -> 2589
    //   1087: goto +1241 -> 2328
    //   1090: aload_0
    //   1091: getfield 567	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mRetryTimes	I
    //   1094: iconst_5
    //   1095: if_icmpge +37 -> 1132
    //   1098: iload_1
    //   1099: sipush 503
    //   1102: if_icmpne +30 -> 1132
    //   1105: aload_0
    //   1106: aload_0
    //   1107: getfield 216	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   1110: ldc_w 804
    //   1113: invokevirtual 800	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
    //   1116: invokestatic 809	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   1119: invokespecial 811	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:retry	(J)V
    //   1122: aload_0
    //   1123: getfield 218	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mCanceled	Z
    //   1126: ifeq +1476 -> 2602
    //   1129: goto +1199 -> 2328
    //   1132: aload_0
    //   1133: getfield 567	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mRetryTimes	I
    //   1136: iconst_5
    //   1137: if_icmpge +39 -> 1176
    //   1140: iload_1
    //   1141: sipush 408
    //   1144: if_icmpeq +17 -> 1161
    //   1147: iload_1
    //   1148: sipush 504
    //   1151: if_icmpeq +10 -> 1161
    //   1154: iload_1
    //   1155: sipush 502
    //   1158: if_icmpne +18 -> 1176
    //   1161: aload_0
    //   1162: lconst_0
    //   1163: invokespecial 811	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:retry	(J)V
    //   1166: aload_0
    //   1167: getfield 218	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mCanceled	Z
    //   1170: ifeq +1438 -> 2608
    //   1173: goto +1155 -> 2328
    //   1176: aload_0
    //   1177: invokespecial 767	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:downloadFileSize	()J
    //   1180: lconst_0
    //   1181: lcmp
    //   1182: ifgt +1146 -> 2328
    //   1185: aload_0
    //   1186: getfield 577	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHasTmpTryNoRange	Z
    //   1189: ifne +1139 -> 2328
    //   1192: iload_1
    //   1193: sipush 410
    //   1196: if_icmpeq +1132 -> 2328
    //   1199: aload_0
    //   1200: iconst_1
    //   1201: putfield 577	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHasTmpTryNoRange	Z
    //   1204: iload 4
    //   1206: istore_3
    //   1207: goto -923 -> 284
    //   1210: aload_0
    //   1211: aload_0
    //   1212: getfield 216	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   1215: invokevirtual 814	java/net/HttpURLConnection:getContentLength	()I
    //   1218: i2l
    //   1219: lload 5
    //   1221: ladd
    //   1222: putfield 573	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContentLength	J
    //   1225: new 189	java/lang/StringBuilder
    //   1228: dup
    //   1229: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   1232: astore 19
    //   1234: aload 19
    //   1236: ldc_w 816
    //   1239: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1242: pop
    //   1243: aload 19
    //   1245: aload_0
    //   1246: getfield 573	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContentLength	J
    //   1249: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1252: pop
    //   1253: aload 19
    //   1255: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1258: pop
    //   1259: aload_0
    //   1260: getfield 121	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mMiniQBLogReport	Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport;
    //   1263: aload_0
    //   1264: getfield 573	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContentLength	J
    //   1267: invokevirtual 819	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport:setPkgSize	(J)V
    //   1270: aload 24
    //   1272: invokevirtual 633	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getMiniQBFileSize	()J
    //   1275: lstore 7
    //   1277: lload 7
    //   1279: lconst_0
    //   1280: lcmp
    //   1281: ifeq +154 -> 1435
    //   1284: aload_0
    //   1285: getfield 573	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContentLength	J
    //   1288: lload 7
    //   1290: lcmp
    //   1291: ifeq +144 -> 1435
    //   1294: new 189	java/lang/StringBuilder
    //   1297: dup
    //   1298: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   1301: astore 19
    //   1303: aload 19
    //   1305: ldc_w 821
    //   1308: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1311: pop
    //   1312: aload 19
    //   1314: lload 7
    //   1316: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1319: pop
    //   1320: aload 19
    //   1322: ldc_w 823
    //   1325: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1328: pop
    //   1329: aload 19
    //   1331: aload_0
    //   1332: getfield 573	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContentLength	J
    //   1335: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1338: pop
    //   1339: aload 19
    //   1341: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1344: pop
    //   1345: aload_0
    //   1346: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   1349: invokestatic 273	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/Apn:isNetworkAvailable	(Landroid/content/Context;)Z
    //   1352: ifeq +70 -> 1422
    //   1355: aload_0
    //   1356: invokespecial 141	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:detectWifiNetworkAvailable	()Z
    //   1359: ifeq +63 -> 1422
    //   1362: new 189	java/lang/StringBuilder
    //   1365: dup
    //   1366: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   1369: astore 19
    //   1371: aload 19
    //   1373: ldc_w 825
    //   1376: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1379: pop
    //   1380: aload 19
    //   1382: lload 7
    //   1384: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1387: pop
    //   1388: aload 19
    //   1390: ldc_w 823
    //   1393: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1396: pop
    //   1397: aload 19
    //   1399: aload_0
    //   1400: getfield 573	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContentLength	J
    //   1403: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1406: pop
    //   1407: aload_0
    //   1408: bipush 113
    //   1410: aload 19
    //   1412: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1415: iconst_1
    //   1416: invokespecial 279	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:setDownloadError	(ILjava/lang/String;Z)V
    //   1419: goto +909 -> 2328
    //   1422: aload_0
    //   1423: bipush 101
    //   1425: ldc_w 827
    //   1428: iconst_1
    //   1429: invokespecial 279	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:setDownloadError	(ILjava/lang/String;Z)V
    //   1432: goto +896 -> 2328
    //   1435: aload_0
    //   1436: getfield 216	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   1439: invokevirtual 348	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   1442: astore 19
    //   1444: aload 19
    //   1446: ifnull +558 -> 2004
    //   1449: aload_0
    //   1450: getfield 216	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   1453: invokevirtual 830	java/net/HttpURLConnection:getContentEncoding	()Ljava/lang/String;
    //   1456: astore 20
    //   1458: aload 20
    //   1460: ifnull +28 -> 1488
    //   1463: aload 20
    //   1465: ldc_w 832
    //   1468: invokevirtual 561	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   1471: ifeq +17 -> 1488
    //   1474: new 834	java/util/zip/GZIPInputStream
    //   1477: dup
    //   1478: aload 19
    //   1480: invokespecial 835	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   1483: astore 20
    //   1485: goto +45 -> 1530
    //   1488: aload 20
    //   1490: ifnull +36 -> 1526
    //   1493: aload 20
    //   1495: ldc_w 837
    //   1498: invokevirtual 561	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   1501: ifeq +25 -> 1526
    //   1504: new 839	java/util/zip/InflaterInputStream
    //   1507: dup
    //   1508: aload 19
    //   1510: new 841	java/util/zip/Inflater
    //   1513: dup
    //   1514: iconst_1
    //   1515: invokespecial 843	java/util/zip/Inflater:<init>	(Z)V
    //   1518: invokespecial 846	java/util/zip/InflaterInputStream:<init>	(Ljava/io/InputStream;Ljava/util/zip/Inflater;)V
    //   1521: astore 20
    //   1523: goto +7 -> 1530
    //   1526: aload 19
    //   1528: astore 20
    //   1530: sipush 8192
    //   1533: newarray <illegal type>
    //   1535: astore 22
    //   1537: new 848	java/io/FileOutputStream
    //   1540: dup
    //   1541: new 147	java/io/File
    //   1544: dup
    //   1545: aload_0
    //   1546: getfield 115	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mFilePath	Ljava/io/File;
    //   1549: ldc 16
    //   1551: invokespecial 156	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   1554: iconst_1
    //   1555: invokespecial 851	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
    //   1558: astore 21
    //   1560: invokestatic 600	java/lang/System:currentTimeMillis	()J
    //   1563: lstore 9
    //   1565: lload 5
    //   1567: lstore 7
    //   1569: aload_0
    //   1570: getfield 218	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mCanceled	Z
    //   1573: ifeq +6 -> 1579
    //   1576: goto +434 -> 2010
    //   1579: aload 20
    //   1581: aload 22
    //   1583: iconst_0
    //   1584: sipush 8192
    //   1587: invokevirtual 857	java/io/InputStream:read	([BII)I
    //   1590: istore_1
    //   1591: iload_1
    //   1592: ifgt +11 -> 1603
    //   1595: aload_0
    //   1596: iconst_1
    //   1597: putfield 579	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mFinished	Z
    //   1600: goto +410 -> 2010
    //   1603: aload 21
    //   1605: aload 22
    //   1607: iconst_0
    //   1608: iload_1
    //   1609: invokevirtual 861	java/io/FileOutputStream:write	([BII)V
    //   1612: aload 21
    //   1614: invokevirtual 864	java/io/FileOutputStream:flush	()V
    //   1617: aload 24
    //   1619: invokevirtual 751	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getDownloadFlow	()J
    //   1622: lstore 13
    //   1624: iload_1
    //   1625: i2l
    //   1626: lstore 17
    //   1628: lload 13
    //   1630: lload 17
    //   1632: ladd
    //   1633: lstore 13
    //   1635: aload 24
    //   1637: lload 13
    //   1639: invokevirtual 748	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:setDownloadFlow	(J)V
    //   1642: lload 13
    //   1644: lload 15
    //   1646: lcmp
    //   1647: iflt +110 -> 1757
    //   1650: new 189	java/lang/StringBuilder
    //   1653: dup
    //   1654: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   1657: astore 22
    //   1659: aload 22
    //   1661: ldc_w 866
    //   1664: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1667: pop
    //   1668: aload 22
    //   1670: lload 13
    //   1672: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1675: pop
    //   1676: aload 22
    //   1678: ldc_w 868
    //   1681: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1684: pop
    //   1685: aload 22
    //   1687: lload 15
    //   1689: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1692: pop
    //   1693: aload 22
    //   1695: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1698: pop
    //   1699: new 189	java/lang/StringBuilder
    //   1702: dup
    //   1703: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   1706: astore 22
    //   1708: aload 22
    //   1710: ldc_w 870
    //   1713: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1716: pop
    //   1717: aload 22
    //   1719: lload 13
    //   1721: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1724: pop
    //   1725: aload 22
    //   1727: ldc_w 868
    //   1730: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1733: pop
    //   1734: aload 22
    //   1736: lload 15
    //   1738: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1741: pop
    //   1742: aload_0
    //   1743: bipush 112
    //   1745: aload 22
    //   1747: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1750: iconst_1
    //   1751: invokespecial 279	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:setDownloadError	(ILjava/lang/String;Z)V
    //   1754: goto +256 -> 2010
    //   1757: aload_0
    //   1758: invokevirtual 755	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:hasEnoughFreeSpace	()Z
    //   1761: ifne +72 -> 1833
    //   1764: new 189	java/lang/StringBuilder
    //   1767: dup
    //   1768: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   1771: astore 22
    //   1773: aload 22
    //   1775: ldc_w 872
    //   1778: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1781: pop
    //   1782: aload 22
    //   1784: aload_0
    //   1785: getfield 115	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mFilePath	Ljava/io/File;
    //   1788: invokevirtual 423	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   1791: invokestatic 701	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:getFreeSpace	(Ljava/lang/String;)J
    //   1794: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1797: pop
    //   1798: aload 22
    //   1800: ldc_w 874
    //   1803: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1806: pop
    //   1807: aload 22
    //   1809: aload 24
    //   1811: invokevirtual 704	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getMinFreeSpace	()J
    //   1814: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1817: pop
    //   1818: aload_0
    //   1819: bipush 105
    //   1821: aload 22
    //   1823: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1826: iconst_1
    //   1827: invokespecial 279	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:setDownloadError	(ILjava/lang/String;Z)V
    //   1830: goto +180 -> 2010
    //   1833: aload_0
    //   1834: lload 11
    //   1836: lload 17
    //   1838: invokespecial 876	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:saveDownloadErrorData	(JJ)J
    //   1841: lstore 11
    //   1843: invokestatic 600	java/lang/System:currentTimeMillis	()J
    //   1846: lstore 13
    //   1848: lload 5
    //   1850: lload 17
    //   1852: ladd
    //   1853: lstore 5
    //   1855: lload 13
    //   1857: lload 9
    //   1859: lsub
    //   1860: ldc2_w 877
    //   1863: lcmp
    //   1864: ifle +104 -> 1968
    //   1867: new 189	java/lang/StringBuilder
    //   1870: dup
    //   1871: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   1874: astore 23
    //   1876: aload 23
    //   1878: ldc_w 880
    //   1881: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1884: pop
    //   1885: aload 23
    //   1887: lload 5
    //   1889: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1892: pop
    //   1893: aload 23
    //   1895: ldc_w 781
    //   1898: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1901: pop
    //   1902: aload 23
    //   1904: aload_0
    //   1905: getfield 573	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContentLength	J
    //   1908: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1911: pop
    //   1912: aload 23
    //   1914: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1917: pop
    //   1918: lload 5
    //   1920: lload 7
    //   1922: lsub
    //   1923: ldc2_w 41
    //   1926: lcmp
    //   1927: ifle +34 -> 1961
    //   1930: aload_0
    //   1931: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   1934: invokestatic 246	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/Apn:getApnType	(Landroid/content/Context;)I
    //   1937: iconst_3
    //   1938: if_icmpne +16 -> 1954
    //   1941: aload_0
    //   1942: getfield 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mContext	Landroid/content/Context;
    //   1945: invokestatic 246	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/Apn:getApnType	(Landroid/content/Context;)I
    //   1948: ifne +666 -> 2614
    //   1951: goto +3 -> 1954
    //   1954: aload_0
    //   1955: invokevirtual 694	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:stopDownload	()V
    //   1958: goto +52 -> 2010
    //   1961: lload 13
    //   1963: lstore 9
    //   1965: goto +3 -> 1968
    //   1968: goto -399 -> 1569
    //   1971: astore 22
    //   1973: goto +301 -> 2274
    //   1976: astore 22
    //   1978: goto +86 -> 2064
    //   1981: astore 21
    //   1983: goto +56 -> 2039
    //   1986: astore 22
    //   1988: aconst_null
    //   1989: astore 21
    //   1991: goto +73 -> 2064
    //   1994: astore 21
    //   1996: goto +40 -> 2036
    //   1999: astore 22
    //   2001: goto +57 -> 2058
    //   2004: aconst_null
    //   2005: astore 20
    //   2007: aconst_null
    //   2008: astore 21
    //   2010: aload_0
    //   2011: aload 21
    //   2013: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2016: aload_0
    //   2017: aload 20
    //   2019: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2022: aload_0
    //   2023: aload 19
    //   2025: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2028: goto +300 -> 2328
    //   2031: astore 21
    //   2033: aconst_null
    //   2034: astore 19
    //   2036: aconst_null
    //   2037: astore 20
    //   2039: aconst_null
    //   2040: astore 23
    //   2042: aload 21
    //   2044: astore 22
    //   2046: aload 23
    //   2048: astore 21
    //   2050: goto +224 -> 2274
    //   2053: astore 22
    //   2055: aconst_null
    //   2056: astore 19
    //   2058: aconst_null
    //   2059: astore 21
    //   2061: aconst_null
    //   2062: astore 20
    //   2064: aload 22
    //   2066: invokevirtual 881	java/io/IOException:printStackTrace	()V
    //   2069: aload 22
    //   2071: instanceof 883
    //   2074: ifne +150 -> 2224
    //   2077: aload 22
    //   2079: instanceof 885
    //   2082: ifeq +6 -> 2088
    //   2085: goto +139 -> 2224
    //   2088: aload_0
    //   2089: invokevirtual 755	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:hasEnoughFreeSpace	()Z
    //   2092: ifne +84 -> 2176
    //   2095: new 189	java/lang/StringBuilder
    //   2098: dup
    //   2099: invokespecial 190	java/lang/StringBuilder:<init>	()V
    //   2102: astore 22
    //   2104: aload 22
    //   2106: ldc_w 872
    //   2109: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2112: pop
    //   2113: aload 22
    //   2115: aload_0
    //   2116: getfield 115	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mFilePath	Ljava/io/File;
    //   2119: invokevirtual 423	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   2122: invokestatic 701	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/FileUtil:getFreeSpace	(Ljava/lang/String;)J
    //   2125: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   2128: pop
    //   2129: aload 22
    //   2131: ldc_w 874
    //   2134: invokevirtual 196	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2137: pop
    //   2138: aload 22
    //   2140: aload 24
    //   2142: invokevirtual 704	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getMinFreeSpace	()J
    //   2145: invokevirtual 640	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   2148: pop
    //   2149: aload_0
    //   2150: bipush 105
    //   2152: aload 22
    //   2154: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2157: iconst_1
    //   2158: invokespecial 279	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:setDownloadError	(ILjava/lang/String;Z)V
    //   2161: aload_0
    //   2162: aload 21
    //   2164: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2167: aload_0
    //   2168: aload 20
    //   2170: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2173: goto -151 -> 2022
    //   2176: aload_0
    //   2177: lconst_0
    //   2178: invokespecial 811	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:retry	(J)V
    //   2181: aload_0
    //   2182: invokespecial 887	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:downloadFileExists	()Z
    //   2185: ifne +15 -> 2200
    //   2188: aload_0
    //   2189: bipush 106
    //   2191: aload 22
    //   2193: iconst_0
    //   2194: invokespecial 661	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:setDownloadError	(ILjava/lang/Throwable;Z)V
    //   2197: goto +12 -> 2209
    //   2200: aload_0
    //   2201: bipush 104
    //   2203: aload 22
    //   2205: iconst_0
    //   2206: invokespecial 661	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:setDownloadError	(ILjava/lang/Throwable;Z)V
    //   2209: aload_0
    //   2210: aload 21
    //   2212: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2215: aload_0
    //   2216: aload 20
    //   2218: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2221: goto +41 -> 2262
    //   2224: aload_0
    //   2225: ldc_w 888
    //   2228: putfield 88	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mReadTimeout	I
    //   2231: aload_0
    //   2232: aload_0
    //   2233: getfield 567	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mRetryTimes	I
    //   2236: iconst_1
    //   2237: iadd
    //   2238: putfield 567	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mRetryTimes	I
    //   2241: aload_0
    //   2242: bipush 103
    //   2244: aload 22
    //   2246: iconst_0
    //   2247: invokespecial 661	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:setDownloadError	(ILjava/lang/Throwable;Z)V
    //   2250: aload_0
    //   2251: aload 21
    //   2253: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2256: aload_0
    //   2257: aload 20
    //   2259: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2262: aload_0
    //   2263: aload 19
    //   2265: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2268: iload 4
    //   2270: istore_3
    //   2271: goto -1987 -> 284
    //   2274: aload_0
    //   2275: aload 21
    //   2277: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2280: aload_0
    //   2281: aload 20
    //   2283: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2286: aload_0
    //   2287: aload 19
    //   2289: invokespecial 565	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeStream	(Ljava/io/Closeable;)V
    //   2292: aload 22
    //   2294: athrow
    //   2295: astore 19
    //   2297: goto +5 -> 2302
    //   2300: astore 19
    //   2302: aload 19
    //   2304: invokevirtual 357	java/lang/Throwable:printStackTrace	()V
    //   2307: aload_0
    //   2308: lconst_0
    //   2309: invokespecial 811	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:retry	(J)V
    //   2312: aload_0
    //   2313: bipush 107
    //   2315: aload 19
    //   2317: iconst_0
    //   2318: invokespecial 661	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:setDownloadError	(ILjava/lang/Throwable;Z)V
    //   2321: aload_0
    //   2322: getfield 218	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mCanceled	Z
    //   2325: ifeq +216 -> 2541
    //   2328: iconst_0
    //   2329: istore 4
    //   2331: aload_0
    //   2332: getfield 218	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mCanceled	Z
    //   2335: ifne +196 -> 2531
    //   2338: aload_0
    //   2339: getfield 579	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mFinished	Z
    //   2342: ifeq +114 -> 2456
    //   2345: iload 4
    //   2347: ifne +14 -> 2361
    //   2350: aload_0
    //   2351: iconst_1
    //   2352: iload_3
    //   2353: invokespecial 721	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:verifyApk	(ZZ)Z
    //   2356: istore 4
    //   2358: goto +3 -> 2361
    //   2361: aload_0
    //   2362: getfield 121	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mMiniQBLogReport	Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport;
    //   2365: astore 19
    //   2367: iload 4
    //   2369: ifeq +8 -> 2377
    //   2372: iconst_1
    //   2373: istore_1
    //   2374: goto +5 -> 2379
    //   2377: iconst_0
    //   2378: istore_1
    //   2379: aload 19
    //   2381: iload_1
    //   2382: invokevirtual 891	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport:setUnpkgFlag	(I)V
    //   2385: iload_3
    //   2386: ifne +30 -> 2416
    //   2389: aload_0
    //   2390: getfield 121	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mMiniQBLogReport	Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport;
    //   2393: astore 19
    //   2395: iload 4
    //   2397: ifeq +8 -> 2405
    //   2400: iconst_1
    //   2401: istore_1
    //   2402: goto +5 -> 2407
    //   2405: iconst_2
    //   2406: istore_1
    //   2407: aload 19
    //   2409: iload_1
    //   2410: invokevirtual 894	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport:setPatchUpdateFlag	(I)V
    //   2413: goto +11 -> 2424
    //   2416: aload_0
    //   2417: getfield 121	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mMiniQBLogReport	Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport;
    //   2420: iconst_0
    //   2421: invokevirtual 894	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport:setPatchUpdateFlag	(I)V
    //   2424: iload 4
    //   2426: ifeq +21 -> 2447
    //   2429: aload_0
    //   2430: iconst_1
    //   2431: invokespecial 717	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:downloadSuccess	(Z)V
    //   2434: aload_0
    //   2435: bipush 100
    //   2437: ldc_w 896
    //   2440: iconst_1
    //   2441: invokespecial 279	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:setDownloadError	(ILjava/lang/String;Z)V
    //   2444: goto +12 -> 2456
    //   2447: aload_0
    //   2448: iconst_0
    //   2449: invokespecial 696	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:deleteFile	(Z)Z
    //   2452: pop
    //   2453: goto +3 -> 2456
    //   2456: iconst_1
    //   2457: istore_1
    //   2458: iload 4
    //   2460: ifeq +18 -> 2478
    //   2463: aload 24
    //   2465: aload 24
    //   2467: invokevirtual 899	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getDownloadSuccessRetryTimes	()I
    //   2470: iconst_1
    //   2471: iadd
    //   2472: invokevirtual 902	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:setDownloadSuccessRetryTimes	(I)V
    //   2475: goto +34 -> 2509
    //   2478: aload 24
    //   2480: invokevirtual 905	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getDownloadFailedRetryTimes	()I
    //   2483: iconst_1
    //   2484: iadd
    //   2485: istore_2
    //   2486: aload 24
    //   2488: iload_2
    //   2489: invokevirtual 908	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:setDownloadFailedRetryTimes	(I)V
    //   2492: iload_2
    //   2493: aload 24
    //   2495: invokevirtual 911	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:getDownloadFailedMaxRetryTimes	()I
    //   2498: if_icmpne +11 -> 2509
    //   2501: aload_0
    //   2502: getfield 121	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mMiniQBLogReport	Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport;
    //   2505: iconst_2
    //   2506: invokevirtual 784	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport:setDownloadCancel	(I)V
    //   2509: aload_0
    //   2510: getfield 121	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:mMiniQBLogReport	Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport;
    //   2513: astore 19
    //   2515: iload 4
    //   2517: ifeq +6 -> 2523
    //   2520: goto +5 -> 2525
    //   2523: iconst_0
    //   2524: istore_1
    //   2525: aload 19
    //   2527: iload_1
    //   2528: invokevirtual 914	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBLogReport:setDownFinalFlag	(I)V
    //   2531: aload 24
    //   2533: invokevirtual 409	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:commit	()V
    //   2536: aload_0
    //   2537: invokespecial 916	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBApkDownloader:closeHttpRequest	()V
    //   2540: return
    //   2541: goto -2257 -> 284
    //   2544: lconst_0
    //   2545: lstore 5
    //   2547: goto -1811 -> 736
    //   2550: iconst_1
    //   2551: istore_1
    //   2552: goto -1795 -> 757
    //   2555: iload_1
    //   2556: sipush 200
    //   2559: if_icmpeq -1349 -> 1210
    //   2562: iload_1
    //   2563: sipush 206
    //   2566: if_icmpne -1614 -> 952
    //   2569: goto -1359 -> 1210
    //   2572: iload_1
    //   2573: sipush 403
    //   2576: if_icmpeq -1500 -> 1076
    //   2579: iload_1
    //   2580: sipush 406
    //   2583: if_icmpne +6 -> 2589
    //   2586: goto -1510 -> 1076
    //   2589: iload_1
    //   2590: sipush 202
    //   2593: if_icmpne -1503 -> 1090
    //   2596: iload 4
    //   2598: istore_3
    //   2599: goto -2315 -> 284
    //   2602: iload 4
    //   2604: istore_3
    //   2605: goto -2321 -> 284
    //   2608: iload 4
    //   2610: istore_3
    //   2611: goto -2327 -> 284
    //   2614: lload 5
    //   2616: lstore 7
    //   2618: goto -657 -> 1961
    //   2621: astore 22
    //   2623: goto -349 -> 2274
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	2626	0	this	MiniQBApkDownloader
    //   24	2570	1	i	int
    //   2485	14	2	j	int
    //   39	2572	3	bool1	boolean
    //   737	1872	4	bool2	boolean
    //   348	2267	5	l1	long
    //   1275	1342	7	l2	long
    //   1563	401	9	l3	long
    //   310	1532	11	l4	long
    //   1622	340	13	l5	long
    //   282	1455	15	l6	long
    //   1626	225	17	l7	long
    //   139	2149	19	localObject1	Object
    //   2295	1	19	localThrowable1	Throwable
    //   2300	16	19	localThrowable2	Throwable
    //   2365	161	19	localMiniQBLogReport	MiniQBLogReport
    //   574	1708	20	localObject2	Object
    //   1558	55	21	localFileOutputStream	java.io.FileOutputStream
    //   1981	1	21	localObject3	Object
    //   1989	1	21	localObject4	Object
    //   1994	1	21	localObject5	Object
    //   2008	4	21	localCloseable	Closeable
    //   2031	12	21	localObject6	Object
    //   2048	228	21	localObject7	Object
    //   1535	287	22	localObject8	Object
    //   1971	1	22	localObject9	Object
    //   1976	1	22	localIOException1	IOException
    //   1986	1	22	localIOException2	IOException
    //   1999	1	22	localIOException3	IOException
    //   2044	1	22	localObject10	Object
    //   2053	25	22	localIOException4	IOException
    //   2102	191	22	localStringBuilder1	StringBuilder
    //   2621	1	22	localObject11	Object
    //   1874	173	23	localStringBuilder2	StringBuilder
    //   121	2411	24	localMiniQBDownloadConfig	MiniQBDownloadConfig
    // Exception table:
    //   from	to	target	type
    //   1560	1565	1971	finally
    //   1569	1576	1971	finally
    //   1579	1591	1971	finally
    //   1595	1600	1971	finally
    //   1603	1624	1971	finally
    //   1635	1642	1971	finally
    //   1650	1754	1971	finally
    //   1757	1830	1971	finally
    //   1833	1848	1971	finally
    //   1867	1918	1971	finally
    //   1930	1951	1971	finally
    //   1954	1958	1971	finally
    //   1560	1565	1976	java/io/IOException
    //   1569	1576	1976	java/io/IOException
    //   1579	1591	1976	java/io/IOException
    //   1595	1600	1976	java/io/IOException
    //   1603	1624	1976	java/io/IOException
    //   1635	1642	1976	java/io/IOException
    //   1650	1754	1976	java/io/IOException
    //   1757	1830	1976	java/io/IOException
    //   1833	1848	1976	java/io/IOException
    //   1867	1918	1976	java/io/IOException
    //   1930	1951	1976	java/io/IOException
    //   1954	1958	1976	java/io/IOException
    //   1530	1560	1981	finally
    //   1530	1560	1986	java/io/IOException
    //   1449	1458	1994	finally
    //   1463	1485	1994	finally
    //   1493	1523	1994	finally
    //   1449	1458	1999	java/io/IOException
    //   1463	1485	1999	java/io/IOException
    //   1493	1523	1999	java/io/IOException
    //   1435	1444	2031	finally
    //   1435	1444	2053	java/io/IOException
    //   710	733	2295	java/lang/Throwable
    //   739	745	2295	java/lang/Throwable
    //   757	806	2295	java/lang/Throwable
    //   809	829	2295	java/lang/Throwable
    //   829	848	2295	java/lang/Throwable
    //   848	870	2295	java/lang/Throwable
    //   870	938	2295	java/lang/Throwable
    //   938	942	2295	java/lang/Throwable
    //   942	949	2295	java/lang/Throwable
    //   966	1002	2295	java/lang/Throwable
    //   1008	1045	2295	java/lang/Throwable
    //   1052	1061	2295	java/lang/Throwable
    //   1067	1073	2295	java/lang/Throwable
    //   1076	1087	2295	java/lang/Throwable
    //   1090	1098	2295	java/lang/Throwable
    //   1105	1129	2295	java/lang/Throwable
    //   1132	1140	2295	java/lang/Throwable
    //   1161	1173	2295	java/lang/Throwable
    //   1176	1192	2295	java/lang/Throwable
    //   1199	1204	2295	java/lang/Throwable
    //   1210	1277	2295	java/lang/Throwable
    //   1284	1419	2295	java/lang/Throwable
    //   1422	1432	2295	java/lang/Throwable
    //   2010	2022	2295	java/lang/Throwable
    //   2022	2028	2295	java/lang/Throwable
    //   2161	2173	2295	java/lang/Throwable
    //   2209	2221	2295	java/lang/Throwable
    //   2250	2262	2295	java/lang/Throwable
    //   2262	2268	2295	java/lang/Throwable
    //   2274	2295	2295	java/lang/Throwable
    //   312	340	2300	java/lang/Throwable
    //   343	382	2300	java/lang/Throwable
    //   393	400	2300	java/lang/Throwable
    //   403	421	2300	java/lang/Throwable
    //   424	430	2300	java/lang/Throwable
    //   430	451	2300	java/lang/Throwable
    //   451	615	2300	java/lang/Throwable
    //   618	710	2300	java/lang/Throwable
    //   2064	2085	2621	finally
    //   2088	2161	2621	finally
    //   2176	2197	2621	finally
    //   2200	2209	2621	finally
    //   2224	2250	2621	finally
  }
  
  public void stopDownload()
  {
    this.mCanceled = true;
  }
  
  private static abstract interface Header
  {
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String RANGE = "Range";
    public static final String REFERER = "Referer";
    public static final String USER_AGENT = "User-Agent";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\MiniQBApkDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */