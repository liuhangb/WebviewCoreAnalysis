package com.tencent.tbs.common.download.qb;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.baseinfo.QUAUtils;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

class QBDownloader
{
  private static final int BUFFER_SIZE = 8192;
  public static final long DOWNLOAD_CONFIG_CHECK_PERIOD = 86400000L;
  private static final long MIN_RETRY_INTERVAL = 20000L;
  private static final long NETWORK_WIFI_CHECK_STEP_SIZE = 1048576L;
  private static final long PROGRESS_NOTIFY_INTERVAL = 1000L;
  private static int mMaxRetryTimes = 5;
  private boolean mCanceled;
  private int mConnectTimeout = 20000;
  private long mContentLength;
  private DownloadConfig mDownloadConfig;
  private File mDownloadFile;
  private QBDownloadListener mDownloadListener;
  private String mDownloadUrl;
  private boolean mFinished;
  private boolean mHasTmpTryNoRange;
  private HttpURLConnection mHttpRequest;
  private boolean mIsDownloading;
  private String mLocation;
  private Bundle mParams;
  private int mReadTimeout = 30000;
  private int mRetryTimes;
  private int mRetryTimes302;
  private boolean mSkipNetworkTypeCheck;
  private File mTempDownloadFile;
  
  public QBDownloader(String paramString1, File paramFile, String paramString2, String paramString3)
  {
    this.mDownloadUrl = paramString1;
    this.mTempDownloadFile = new File(paramFile, paramString2);
    this.mDownloadFile = new File(paramFile, paramString3);
    this.mDownloadConfig = new DownloadConfig(paramFile);
    this.mSkipNetworkTypeCheck = false;
    resetArgs();
  }
  
  private void closeHttpRequest()
  {
    HttpURLConnection localHttpURLConnection = this.mHttpRequest;
    if (localHttpURLConnection != null) {}
    try
    {
      localHttpURLConnection.disconnect();
      this.mHttpRequest = null;
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
  
  private void ensureListenerNotNull()
  {
    if (this.mDownloadListener == null) {
      this.mDownloadListener = new QBDownloadListener()
      {
        public void onDownloadFailed(boolean paramAnonymousBoolean, Bundle paramAnonymousBundle) {}
        
        public void onDownloadPause(boolean paramAnonymousBoolean, int paramAnonymousInt) {}
        
        public void onDownloadProgress(boolean paramAnonymousBoolean, int paramAnonymousInt) {}
        
        public void onDownloadResume(boolean paramAnonymousBoolean, int paramAnonymousInt) {}
        
        public void onDownloadStart(boolean paramAnonymousBoolean) {}
        
        public void onDownloadSucess(boolean paramAnonymousBoolean, String paramAnonymousString, Bundle paramAnonymousBundle) {}
      };
    }
  }
  
  private void initHttpRequest(String paramString)
    throws Exception
  {
    this.mHttpRequest = ((HttpURLConnection)new URL(paramString).openConnection());
    this.mHttpRequest.setRequestProperty("Accept-Encoding", "identity");
    this.mHttpRequest.setRequestProperty("Q-UA2", QUAUtils.getQUA2_V3());
    this.mHttpRequest.setRequestMethod("GET");
    this.mHttpRequest.setInstanceFollowRedirects(false);
    this.mHttpRequest.setConnectTimeout(this.mConnectTimeout);
    this.mHttpRequest.setReadTimeout(this.mReadTimeout);
  }
  
  private boolean isNeedUpgrade(String paramString, long paramLong)
  {
    boolean bool2 = true;
    boolean bool1;
    if ((paramLong > 0L) && (paramLong != this.mDownloadConfig.getDownloadFileLength())) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if ((this.mDownloadConfig.getDownloadUrl() != null) && (!isSameUrl(this.mDownloadConfig.getDownloadUrl(), paramString))) {
      bool1 = bool2;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QBDownloader.isNeedUpgrade isNeedUpgrade=");
    localStringBuilder.append(bool1);
    localStringBuilder.append(" (new:old) downloadUrl=");
    localStringBuilder.append(paramString);
    localStringBuilder.append(":");
    String str;
    if (this.mDownloadConfig.getDownloadUrl() == null) {
      str = "null";
    } else {
      str = this.mDownloadConfig.getDownloadUrl();
    }
    localStringBuilder.append(str);
    localStringBuilder.append(" fileLength=");
    localStringBuilder.append(paramLong);
    localStringBuilder.append(":");
    localStringBuilder.append(this.mDownloadConfig.getDownloadFileLength());
    localStringBuilder.toString();
    this.mDownloadConfig.setIsNeedUpgrade(bool1);
    this.mDownloadConfig.setUpgradeUrl(paramString);
    this.mDownloadConfig.writeConfig();
    return bool1;
  }
  
  private boolean isSameUrl(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = Uri.parse(paramString1);
      paramString2 = Uri.parse(paramString2);
      if ((paramString1 != null) && (paramString2 != null))
      {
        paramString1 = paramString1.getPath();
        paramString2 = paramString2.getPath();
        if (paramString1 != null)
        {
          boolean bool = paramString1.equals(paramString2);
          if (bool) {
            return true;
          }
        }
      }
    }
    catch (Exception paramString1)
    {
      for (;;) {}
    }
    return false;
  }
  
  /* Error */
  private boolean renameApkFile(File paramFile1, File paramFile2)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 241	java/io/File:exists	()Z
    //   4: ifeq +195 -> 199
    //   7: aconst_null
    //   8: astore 6
    //   10: aconst_null
    //   11: astore 5
    //   13: new 243	java/io/FileInputStream
    //   16: dup
    //   17: aload_1
    //   18: invokespecial 244	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   21: astore 4
    //   23: ldc -10
    //   25: invokevirtual 250	java/lang/String:getBytes	()[B
    //   28: astore 7
    //   30: bipush 127
    //   32: newarray <illegal type>
    //   34: astore 8
    //   36: new 252	java/util/Random
    //   39: dup
    //   40: invokespecial 253	java/util/Random:<init>	()V
    //   43: aload 8
    //   45: invokevirtual 257	java/util/Random:nextBytes	([B)V
    //   48: new 259	java/io/FileOutputStream
    //   51: dup
    //   52: aload_2
    //   53: invokespecial 260	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   56: astore_2
    //   57: aload_2
    //   58: aload 7
    //   60: invokevirtual 263	java/io/FileOutputStream:write	([B)V
    //   63: aload_2
    //   64: aload 8
    //   66: invokevirtual 263	java/io/FileOutputStream:write	([B)V
    //   69: ldc_w 264
    //   72: newarray <illegal type>
    //   74: astore 5
    //   76: aload 4
    //   78: aload 5
    //   80: invokevirtual 268	java/io/FileInputStream:read	([B)I
    //   83: istore_3
    //   84: iload_3
    //   85: iconst_m1
    //   86: if_icmpeq +14 -> 100
    //   89: aload_2
    //   90: aload 5
    //   92: iconst_0
    //   93: iload_3
    //   94: invokevirtual 271	java/io/FileOutputStream:write	([BII)V
    //   97: goto -21 -> 76
    //   100: aload_2
    //   101: invokevirtual 274	java/io/FileOutputStream:flush	()V
    //   104: aload_1
    //   105: invokevirtual 277	java/io/File:delete	()Z
    //   108: pop
    //   109: aload 4
    //   111: invokevirtual 278	java/io/FileInputStream:close	()V
    //   114: aload_2
    //   115: invokevirtual 279	java/io/FileOutputStream:close	()V
    //   118: iconst_1
    //   119: ireturn
    //   120: astore_1
    //   121: goto +28 -> 149
    //   124: aload_2
    //   125: astore_1
    //   126: goto +52 -> 178
    //   129: astore_1
    //   130: aload 5
    //   132: astore_2
    //   133: goto +16 -> 149
    //   136: aload 6
    //   138: astore_1
    //   139: goto +39 -> 178
    //   142: astore_1
    //   143: aconst_null
    //   144: astore 4
    //   146: aload 5
    //   148: astore_2
    //   149: aload 4
    //   151: ifnull +11 -> 162
    //   154: aload 4
    //   156: invokevirtual 278	java/io/FileInputStream:close	()V
    //   159: goto +3 -> 162
    //   162: aload_2
    //   163: ifnull +7 -> 170
    //   166: aload_2
    //   167: invokevirtual 279	java/io/FileOutputStream:close	()V
    //   170: aload_1
    //   171: athrow
    //   172: aconst_null
    //   173: astore 4
    //   175: aload 6
    //   177: astore_1
    //   178: aload 4
    //   180: ifnull +11 -> 191
    //   183: aload 4
    //   185: invokevirtual 278	java/io/FileInputStream:close	()V
    //   188: goto +3 -> 191
    //   191: aload_1
    //   192: ifnull +7 -> 199
    //   195: aload_1
    //   196: invokevirtual 279	java/io/FileOutputStream:close	()V
    //   199: iconst_0
    //   200: ireturn
    //   201: astore_1
    //   202: goto -30 -> 172
    //   205: astore_1
    //   206: goto -70 -> 136
    //   209: astore_1
    //   210: goto -86 -> 124
    //   213: astore_1
    //   214: goto -100 -> 114
    //   217: astore_1
    //   218: goto -100 -> 118
    //   221: astore 4
    //   223: goto -61 -> 162
    //   226: astore_2
    //   227: goto -57 -> 170
    //   230: astore_2
    //   231: goto -40 -> 191
    //   234: astore_1
    //   235: iconst_0
    //   236: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	237	0	this	QBDownloader
    //   0	237	1	paramFile1	File
    //   0	237	2	paramFile2	File
    //   83	11	3	i	int
    //   21	163	4	localFileInputStream	java.io.FileInputStream
    //   221	1	4	localException	Exception
    //   11	136	5	arrayOfByte1	byte[]
    //   8	168	6	localObject	Object
    //   28	31	7	arrayOfByte2	byte[]
    //   34	31	8	arrayOfByte3	byte[]
    // Exception table:
    //   from	to	target	type
    //   57	76	120	finally
    //   76	84	120	finally
    //   89	97	120	finally
    //   100	109	120	finally
    //   23	57	129	finally
    //   13	23	142	finally
    //   13	23	201	java/lang/Throwable
    //   23	57	205	java/lang/Throwable
    //   57	76	209	java/lang/Throwable
    //   76	84	209	java/lang/Throwable
    //   89	97	209	java/lang/Throwable
    //   100	109	209	java/lang/Throwable
    //   109	114	213	java/lang/Exception
    //   114	118	217	java/lang/Exception
    //   154	159	221	java/lang/Exception
    //   166	170	226	java/lang/Exception
    //   183	188	230	java/lang/Exception
    //   195	199	234	java/lang/Exception
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
  
  private void startDownload(boolean paramBoolean1, boolean paramBoolean2)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.useAs(TypeTransformer.java:868)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:668)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private void writeFileLength(long paramLong)
  {
    this.mDownloadConfig.setFileLength(paramLong);
    this.mDownloadConfig.writeConfig();
  }
  
  protected void checkUpgradeIfNeeded()
  {
    if (!this.mDownloadFile.exists()) {
      return;
    }
    long l1 = System.currentTimeMillis();
    long l2 = this.mDownloadConfig.getLastCheck();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QBDownloader.checkUpgradeIfNeeded timeNow=");
    localStringBuilder.append(l1);
    localStringBuilder.append(" timeLastCheck=");
    localStringBuilder.append(l2);
    localStringBuilder.append(" needUpgrade=");
    localStringBuilder.append(this.mDownloadConfig.isNeedUpgrade());
    localStringBuilder.toString();
    if ((l1 - l2 > 86400000L) || (this.mDownloadConfig.isNeedUpgrade())) {
      startDownload(false, true);
    }
  }
  
  protected int currentDownloadProgress()
  {
    long l1;
    if (this.mTempDownloadFile.exists()) {
      l1 = this.mTempDownloadFile.length();
    } else {
      l1 = 0L;
    }
    long l2 = this.mDownloadConfig.getFileLength();
    if (l2 == 0L) {
      return 0;
    }
    return (int)(l1 * 100L / l2);
  }
  
  public String getDownloadUrl()
  {
    return this.mDownloadUrl;
  }
  
  protected boolean isDownloading()
  {
    return this.mIsDownloading;
  }
  
  protected void setExtraParams(QBDownloadListener paramQBDownloadListener, Bundle paramBundle)
  {
    this.mDownloadListener = paramQBDownloadListener;
    mMaxRetryTimes = 1;
    this.mParams = paramBundle;
  }
  
  protected void setSkipNetworkTypeCheck(boolean paramBoolean)
  {
    this.mSkipNetworkTypeCheck = paramBoolean;
  }
  
  public void startDownload()
  {
    startDownload(false, false);
  }
  
  public void startVirtualDownload()
  {
    Log.w("QBDownloadManager", "QBDownloader.startVirtualDownload");
    ensureListenerNotNull();
    final Object localObject = this.mDownloadListener;
    if (localObject != null) {
      ((QBDownloadListener)localObject).onDownloadStart(false);
    }
    startDownload(true, false);
    final long l1;
    if (this.mTempDownloadFile.exists()) {
      l1 = this.mTempDownloadFile.length();
    } else {
      l1 = 0L;
    }
    long l2 = this.mContentLength - l1;
    if (l2 <= 0L)
    {
      if (this.mDownloadListener != null)
      {
        X5CoreBeaconUploader.getInstance(ContextHolder.getAppContext()).userBehaviorStatistics("BZDMEC_001");
        this.mDownloadListener.onDownloadFailed(false, this.mParams);
      }
      return;
    }
    localObject = this.mDownloadListener;
    if (localObject != null) {
      ((QBDownloadListener)localObject).onDownloadProgress(false, 0);
    }
    localObject = new Timer();
    ((Timer)localObject).schedule(new TimerTask()
    {
      static final long TIME_OUT = 20000L;
      long virtualDownloadTimeBegin = System.currentTimeMillis();
      int virtualProgress = 0;
      
      public void run()
      {
        long l;
        if (QBDownloader.this.mTempDownloadFile.exists()) {
          l = QBDownloader.this.mTempDownloadFile.length();
        } else {
          l = 0L;
        }
        int i = (int)((l - l1) * 100L / localObject);
        if (i > this.virtualProgress)
        {
          this.virtualProgress = i;
          this.virtualDownloadTimeBegin = System.currentTimeMillis();
          if (QBDownloader.this.mDownloadListener != null) {
            QBDownloader.this.mDownloadListener.onDownloadProgress(false, this.virtualProgress);
          }
        }
        else
        {
          if (QBDownloader.this.mDownloadFile.exists())
          {
            if (QBDownloader.this.mDownloadListener != null) {
              QBDownloader.this.mDownloadListener.onDownloadSucess(false, QBDownloader.this.mDownloadFile.getAbsolutePath(), QBDownloader.this.mParams);
            }
            QBDownloader.access$402(QBDownloader.this, false);
            this.val$timer.cancel();
            return;
          }
          if (System.currentTimeMillis() - this.virtualDownloadTimeBegin >= 20000L)
          {
            if (QBDownloader.this.mDownloadListener != null)
            {
              X5CoreBeaconUploader.getInstance(ContextHolder.getAppContext()).userBehaviorStatistics("BZDMEC_002");
              QBDownloader.this.mDownloadListener.onDownloadFailed(false, QBDownloader.this.mParams);
            }
            QBDownloader.access$402(QBDownloader.this, false);
            this.val$timer.cancel();
          }
        }
      }
    }, 0L, 2000L);
  }
  
  protected void stopDownload()
  {
    this.mCanceled = true;
  }
  
  protected static class DownloadConfig
  {
    private static final String DES_KEY = "!Q2@df0D";
    public static final String DOWNLOAD_CONFIG_FILENAME = "tqc.data";
    static final String KEY_DOWNLOAD_FILE_LENGTH = "download_file_length";
    static final String KEY_DOWNLOAD_URL = "download_url";
    static final String KEY_FILE_SIZE = "file_size";
    static final String KEY_LAST_CHECK = "last_check";
    static final String KEY_NEED_UPGRADE = "need_upgrade";
    static final String KEY_UPGRADE_URL = "upgrade_url";
    static final String OLD_DOWNLOAD_CONFIG_FILENAME = "tbs_qbdownload.conf";
    private File mDownloadConfigFile = null;
    long mDownloadFileLength = 0L;
    String mDownloadUrl = null;
    long mFileLength = 0L;
    long mLastCheck = 0L;
    boolean mNeedUpgrade = false;
    String mUpgradeUrl = null;
    
    protected DownloadConfig(File paramFile)
    {
      File localFile = new File(paramFile, "tbs_qbdownload.conf");
      this.mDownloadConfigFile = new File(paramFile, "tqc.data");
      if (localFile.exists()) {
        localFile.delete();
      }
      paramFile = this.mDownloadConfigFile;
      if ((paramFile != null) && (!paramFile.exists())) {}
      try
      {
        this.mDownloadConfigFile.createNewFile();
        readConfig();
        return;
      }
      catch (Exception paramFile)
      {
        for (;;) {}
      }
    }
    
    /* Error */
    private void readConfig()
    {
      // Byte code:
      //   0: new 87	java/io/FileInputStream
      //   3: dup
      //   4: aload_0
      //   5: getfield 66	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mDownloadConfigFile	Ljava/io/File;
      //   8: invokespecial 89	java/io/FileInputStream:<init>	(Ljava/io/File;)V
      //   11: astore_1
      //   12: ldc 91
      //   14: invokestatic 97	javax/crypto/Cipher:getInstance	(Ljava/lang/String;)Ljavax/crypto/Cipher;
      //   17: astore_2
      //   18: aload_2
      //   19: iconst_2
      //   20: new 99	javax/crypto/spec/SecretKeySpec
      //   23: dup
      //   24: ldc 11
      //   26: invokevirtual 105	java/lang/String:getBytes	()[B
      //   29: ldc 91
      //   31: invokespecial 108	javax/crypto/spec/SecretKeySpec:<init>	([BLjava/lang/String;)V
      //   34: invokevirtual 112	javax/crypto/Cipher:init	(ILjava/security/Key;)V
      //   37: new 114	javax/crypto/CipherInputStream
      //   40: dup
      //   41: aload_1
      //   42: aload_2
      //   43: invokespecial 117	javax/crypto/CipherInputStream:<init>	(Ljava/io/InputStream;Ljavax/crypto/Cipher;)V
      //   46: astore_3
      //   47: new 119	java/util/Properties
      //   50: dup
      //   51: invokespecial 120	java/util/Properties:<init>	()V
      //   54: astore_2
      //   55: aload_2
      //   56: aload_3
      //   57: invokevirtual 124	java/util/Properties:load	(Ljava/io/InputStream;)V
      //   60: aload_0
      //   61: aload_2
      //   62: ldc 20
      //   64: invokevirtual 128	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
      //   67: putfield 54	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mDownloadUrl	Ljava/lang/String;
      //   70: aload_0
      //   71: aload_2
      //   72: ldc 26
      //   74: ldc -126
      //   76: invokevirtual 133	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   79: invokestatic 139	java/lang/Long:parseLong	(Ljava/lang/String;)J
      //   82: putfield 56	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mLastCheck	J
      //   85: aload_0
      //   86: aload_2
      //   87: ldc 32
      //   89: invokevirtual 128	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
      //   92: putfield 58	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mUpgradeUrl	Ljava/lang/String;
      //   95: aload_0
      //   96: aload_2
      //   97: ldc 29
      //   99: ldc -115
      //   101: invokevirtual 133	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   104: invokestatic 147	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
      //   107: putfield 60	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mNeedUpgrade	Z
      //   110: aload_0
      //   111: aload_2
      //   112: ldc 23
      //   114: ldc -126
      //   116: invokevirtual 133	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   119: invokestatic 139	java/lang/Long:parseLong	(Ljava/lang/String;)J
      //   122: putfield 62	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mFileLength	J
      //   125: aload_0
      //   126: aload_2
      //   127: ldc 17
      //   129: ldc -126
      //   131: invokevirtual 133	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   134: invokestatic 139	java/lang/Long:parseLong	(Ljava/lang/String;)J
      //   137: putfield 64	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mDownloadFileLength	J
      //   140: aload_3
      //   141: invokevirtual 150	javax/crypto/CipherInputStream:close	()V
      //   144: aload_1
      //   145: invokevirtual 151	java/io/FileInputStream:close	()V
      //   148: return
      //   149: astore_2
      //   150: aload_1
      //   151: astore 4
      //   153: aload_3
      //   154: astore_1
      //   155: goto +30 -> 185
      //   158: aload_3
      //   159: astore_2
      //   160: goto +52 -> 212
      //   163: astore_2
      //   164: aconst_null
      //   165: astore_3
      //   166: aload_1
      //   167: astore 4
      //   169: aload_3
      //   170: astore_1
      //   171: goto +14 -> 185
      //   174: aconst_null
      //   175: astore_2
      //   176: goto +36 -> 212
      //   179: astore_2
      //   180: aconst_null
      //   181: astore_1
      //   182: aload_1
      //   183: astore 4
      //   185: aload_1
      //   186: ifnull +10 -> 196
      //   189: aload_1
      //   190: invokevirtual 150	javax/crypto/CipherInputStream:close	()V
      //   193: goto +3 -> 196
      //   196: aload 4
      //   198: ifnull +8 -> 206
      //   201: aload 4
      //   203: invokevirtual 151	java/io/FileInputStream:close	()V
      //   206: aload_2
      //   207: athrow
      //   208: aconst_null
      //   209: astore_1
      //   210: aload_1
      //   211: astore_2
      //   212: aload_2
      //   213: ifnull +10 -> 223
      //   216: aload_2
      //   217: invokevirtual 150	javax/crypto/CipherInputStream:close	()V
      //   220: goto +3 -> 223
      //   223: aload_1
      //   224: ifnull +6 -> 230
      //   227: goto -83 -> 144
      //   230: return
      //   231: astore_1
      //   232: goto -24 -> 208
      //   235: astore_2
      //   236: goto -62 -> 174
      //   239: astore_2
      //   240: goto -82 -> 158
      //   243: astore_2
      //   244: goto -100 -> 144
      //   247: astore_1
      //   248: return
      //   249: astore_1
      //   250: goto -54 -> 196
      //   253: astore_1
      //   254: goto -48 -> 206
      //   257: astore_2
      //   258: goto -35 -> 223
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	261	0	this	DownloadConfig
      //   11	213	1	localObject1	Object
      //   231	1	1	localException1	Exception
      //   247	1	1	localException2	Exception
      //   249	1	1	localException3	Exception
      //   253	1	1	localException4	Exception
      //   17	110	2	localObject2	Object
      //   149	1	2	localObject3	Object
      //   159	1	2	localObject4	Object
      //   163	1	2	localObject5	Object
      //   175	1	2	localObject6	Object
      //   179	28	2	localObject7	Object
      //   211	6	2	localObject8	Object
      //   235	1	2	localException5	Exception
      //   239	1	2	localException6	Exception
      //   243	1	2	localException7	Exception
      //   257	1	2	localException8	Exception
      //   46	124	3	localCipherInputStream	javax.crypto.CipherInputStream
      //   151	51	4	localObject9	Object
      // Exception table:
      //   from	to	target	type
      //   47	140	149	finally
      //   12	47	163	finally
      //   0	12	179	finally
      //   0	12	231	java/lang/Exception
      //   12	47	235	java/lang/Exception
      //   47	140	239	java/lang/Exception
      //   140	144	243	java/lang/Exception
      //   144	148	247	java/lang/Exception
      //   189	193	249	java/lang/Exception
      //   201	206	253	java/lang/Exception
      //   216	220	257	java/lang/Exception
    }
    
    public long getDownloadFileLength()
    {
      return this.mDownloadFileLength;
    }
    
    public String getDownloadUrl()
    {
      return this.mDownloadUrl;
    }
    
    public long getFileLength()
    {
      return this.mFileLength;
    }
    
    public long getLastCheck()
    {
      return this.mLastCheck;
    }
    
    public String getUpgradeUrl()
    {
      return this.mUpgradeUrl;
    }
    
    public boolean isNeedUpgrade()
    {
      return this.mNeedUpgrade;
    }
    
    public void setDownloadFileLength(long paramLong)
    {
      this.mDownloadFileLength = paramLong;
    }
    
    public void setDownloadUrl(String paramString)
    {
      this.mDownloadUrl = paramString;
    }
    
    public void setFileLength(long paramLong)
    {
      this.mFileLength = paramLong;
    }
    
    public void setIsNeedUpgrade(boolean paramBoolean)
    {
      this.mNeedUpgrade = paramBoolean;
    }
    
    public void setUpgradeUrl(String paramString)
    {
      this.mUpgradeUrl = paramString;
    }
    
    /* Error */
    public void writeConfig()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokestatic 175	java/lang/System:currentTimeMillis	()J
      //   4: putfield 56	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mLastCheck	J
      //   7: aconst_null
      //   8: astore 4
      //   10: aconst_null
      //   11: astore_2
      //   12: aconst_null
      //   13: astore 6
      //   15: new 119	java/util/Properties
      //   18: dup
      //   19: invokespecial 120	java/util/Properties:<init>	()V
      //   22: astore 5
      //   24: aload_0
      //   25: getfield 54	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mDownloadUrl	Ljava/lang/String;
      //   28: ifnonnull +9 -> 37
      //   31: ldc -79
      //   33: astore_1
      //   34: goto +8 -> 42
      //   37: aload_0
      //   38: getfield 54	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mDownloadUrl	Ljava/lang/String;
      //   41: astore_1
      //   42: aload 5
      //   44: ldc 20
      //   46: aload_1
      //   47: invokevirtual 181	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   50: pop
      //   51: aload 5
      //   53: ldc 26
      //   55: aload_0
      //   56: getfield 56	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mLastCheck	J
      //   59: invokestatic 185	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   62: invokevirtual 181	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   65: pop
      //   66: aload_0
      //   67: getfield 58	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mUpgradeUrl	Ljava/lang/String;
      //   70: ifnonnull +9 -> 79
      //   73: ldc -79
      //   75: astore_1
      //   76: goto +8 -> 84
      //   79: aload_0
      //   80: getfield 58	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mUpgradeUrl	Ljava/lang/String;
      //   83: astore_1
      //   84: aload 5
      //   86: ldc 32
      //   88: aload_1
      //   89: invokevirtual 181	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   92: pop
      //   93: aload 5
      //   95: ldc 29
      //   97: aload_0
      //   98: getfield 60	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mNeedUpgrade	Z
      //   101: invokestatic 188	java/lang/String:valueOf	(Z)Ljava/lang/String;
      //   104: invokevirtual 181	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   107: pop
      //   108: aload 5
      //   110: ldc 23
      //   112: aload_0
      //   113: getfield 62	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mFileLength	J
      //   116: invokestatic 185	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   119: invokevirtual 181	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   122: pop
      //   123: aload 5
      //   125: ldc 17
      //   127: aload_0
      //   128: getfield 64	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mDownloadFileLength	J
      //   131: invokestatic 185	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   134: invokevirtual 181	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   137: pop
      //   138: new 190	java/io/FileOutputStream
      //   141: dup
      //   142: aload_0
      //   143: getfield 66	com/tencent/tbs/common/download/qb/QBDownloader$DownloadConfig:mDownloadConfigFile	Ljava/io/File;
      //   146: invokespecial 191	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
      //   149: astore_1
      //   150: aload 4
      //   152: astore_2
      //   153: aload_1
      //   154: astore_3
      //   155: ldc 91
      //   157: invokestatic 97	javax/crypto/Cipher:getInstance	(Ljava/lang/String;)Ljavax/crypto/Cipher;
      //   160: astore 7
      //   162: aload 4
      //   164: astore_2
      //   165: aload_1
      //   166: astore_3
      //   167: aload 7
      //   169: iconst_1
      //   170: new 99	javax/crypto/spec/SecretKeySpec
      //   173: dup
      //   174: ldc 11
      //   176: invokevirtual 105	java/lang/String:getBytes	()[B
      //   179: ldc 91
      //   181: invokespecial 108	javax/crypto/spec/SecretKeySpec:<init>	([BLjava/lang/String;)V
      //   184: invokevirtual 112	javax/crypto/Cipher:init	(ILjava/security/Key;)V
      //   187: aload 4
      //   189: astore_2
      //   190: aload_1
      //   191: astore_3
      //   192: new 193	javax/crypto/CipherOutputStream
      //   195: dup
      //   196: aload_1
      //   197: aload 7
      //   199: invokespecial 196	javax/crypto/CipherOutputStream:<init>	(Ljava/io/OutputStream;Ljavax/crypto/Cipher;)V
      //   202: astore 4
      //   204: aload 5
      //   206: aload 4
      //   208: aconst_null
      //   209: invokevirtual 200	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
      //   212: aload 4
      //   214: invokevirtual 201	javax/crypto/CipherOutputStream:close	()V
      //   217: aload_1
      //   218: invokevirtual 202	java/io/FileOutputStream:close	()V
      //   221: return
      //   222: astore_3
      //   223: aload 4
      //   225: astore_2
      //   226: goto +69 -> 295
      //   229: astore 5
      //   231: goto +26 -> 257
      //   234: astore 5
      //   236: aload 6
      //   238: astore 4
      //   240: goto +17 -> 257
      //   243: astore_3
      //   244: aconst_null
      //   245: astore_1
      //   246: goto +49 -> 295
      //   249: astore 5
      //   251: aconst_null
      //   252: astore_1
      //   253: aload 6
      //   255: astore 4
      //   257: aload 4
      //   259: astore_2
      //   260: aload_1
      //   261: astore_3
      //   262: aload 5
      //   264: invokevirtual 205	java/lang/Exception:printStackTrace	()V
      //   267: aload 4
      //   269: ifnull +11 -> 280
      //   272: aload 4
      //   274: invokevirtual 201	javax/crypto/CipherOutputStream:close	()V
      //   277: goto +3 -> 280
      //   280: aload_1
      //   281: ifnull +6 -> 287
      //   284: goto -67 -> 217
      //   287: return
      //   288: astore 4
      //   290: aload_3
      //   291: astore_1
      //   292: aload 4
      //   294: astore_3
      //   295: aload_2
      //   296: ifnull +10 -> 306
      //   299: aload_2
      //   300: invokevirtual 201	javax/crypto/CipherOutputStream:close	()V
      //   303: goto +3 -> 306
      //   306: aload_1
      //   307: ifnull +7 -> 314
      //   310: aload_1
      //   311: invokevirtual 202	java/io/FileOutputStream:close	()V
      //   314: aload_3
      //   315: athrow
      //   316: astore_2
      //   317: goto -100 -> 217
      //   320: astore_1
      //   321: return
      //   322: astore_2
      //   323: goto -43 -> 280
      //   326: astore_2
      //   327: goto -21 -> 306
      //   330: astore_1
      //   331: goto -17 -> 314
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	334	0	this	DownloadConfig
      //   33	278	1	localObject1	Object
      //   320	1	1	localIOException1	IOException
      //   330	1	1	localIOException2	IOException
      //   11	289	2	localObject2	Object
      //   316	1	2	localIOException3	IOException
      //   322	1	2	localIOException4	IOException
      //   326	1	2	localIOException5	IOException
      //   154	38	3	localObject3	Object
      //   222	1	3	localObject4	Object
      //   243	1	3	localObject5	Object
      //   261	54	3	localObject6	Object
      //   8	265	4	localObject7	Object
      //   288	5	4	localObject8	Object
      //   22	183	5	localProperties	java.util.Properties
      //   229	1	5	localException1	Exception
      //   234	1	5	localException2	Exception
      //   249	14	5	localException3	Exception
      //   13	241	6	localObject9	Object
      //   160	38	7	localCipher	javax.crypto.Cipher
      // Exception table:
      //   from	to	target	type
      //   204	212	222	finally
      //   204	212	229	java/lang/Exception
      //   155	162	234	java/lang/Exception
      //   167	187	234	java/lang/Exception
      //   192	204	234	java/lang/Exception
      //   15	31	243	finally
      //   37	42	243	finally
      //   42	73	243	finally
      //   79	84	243	finally
      //   84	150	243	finally
      //   15	31	249	java/lang/Exception
      //   37	42	249	java/lang/Exception
      //   42	73	249	java/lang/Exception
      //   79	84	249	java/lang/Exception
      //   84	150	249	java/lang/Exception
      //   155	162	288	finally
      //   167	187	288	finally
      //   192	204	288	finally
      //   262	267	288	finally
      //   212	217	316	java/io/IOException
      //   217	221	320	java/io/IOException
      //   272	277	322	java/io/IOException
      //   299	303	326	java/io/IOException
      //   310	314	330	java/io/IOException
    }
  }
  
  private static abstract interface Header
  {
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String RANGE = "Range";
    public static final String REFERER = "Referer";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\QBDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */