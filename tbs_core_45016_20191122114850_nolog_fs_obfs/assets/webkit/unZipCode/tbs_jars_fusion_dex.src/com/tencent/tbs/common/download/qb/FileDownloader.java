package com.tencent.tbs.common.download.qb;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class FileDownloader
{
  private static final int BUFFER_SIZE = 8192;
  public static final long DOWNLOAD_CONFIG_CHECK_PERIOD = 86400000L;
  public static String KEY_VF_LEN = "key_verify_len";
  public static String KEY_VF_MD5 = "key_verify_md5";
  public static String KEY_VF_SIGN = "key_verify_signature";
  private static int MAX_RETRY_TIMES_DEFAULT = 5;
  private static int MAX_RETRY_TIMES_FOREGROUND = 1;
  private static final long MIN_RETRY_INTERVAL = 20000L;
  private static final long NETWORK_WIFI_CHECK_STEP_SIZE = 1048576L;
  private static final long PROGRESS_NOTIFY_INTERVAL = 1000L;
  private static int mMaxRetryTimes = MAX_RETRY_TIMES_DEFAULT;
  long lastrange = 0L;
  boolean mAllowFlowcheck = true;
  private boolean mCanceled;
  private int mConnectTimeout = 20000;
  private long mContentLength;
  long mDefaultDownloadMaxFlow = 62914560L;
  private DownloadConfig mDownloadConfig;
  private File mDownloadFile;
  private QBDownloadListener mDownloadListener;
  FileDownloadHook mDownloadOverHook;
  private String mDownloadUrl;
  private boolean mFinished;
  private boolean mHasTmpTryNoRange;
  private HttpURLConnection mHttpRequest;
  private boolean mIsDownloading;
  private boolean mIsSilent;
  private String mLocation;
  private Bundle mParams;
  private int mReadTimeout = 30000;
  TbsDownloadHelperConfig mReqDownloadConfig;
  private int mRetryTimes;
  private int mRetryTimes302;
  private boolean mSkipNetworkTypeCheck;
  private File mTempDownloadFile;
  private Bundle mVerifyInfo;
  
  public FileDownloader(Context paramContext, File paramFile, String paramString1, String paramString2, TbsDownloadHelperConfig paramTbsDownloadHelperConfig, boolean paramBoolean)
  {
    this.mTempDownloadFile = new File(paramFile, paramString1);
    this.mDownloadFile = new File(paramFile, paramString2);
    this.mDownloadConfig = new DownloadConfig(paramFile, paramString2);
    this.mSkipNetworkTypeCheck = false;
    this.mIsSilent = paramBoolean;
    this.mReqDownloadConfig = paramTbsDownloadHelperConfig;
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
      this.mIsDownloading = false;
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
  
  private void notifyProgress(long paramLong)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("notifyProgress:");
    ((StringBuilder)localObject).append(paramLong);
    ((StringBuilder)localObject).append(",lastrange:");
    ((StringBuilder)localObject).append(this.lastrange);
    ((StringBuilder)localObject).toString();
    long l;
    if (this.mDownloadOverHook != null)
    {
      if (paramLong == -1L)
      {
        l = this.lastrange;
      }
      else
      {
        l = paramLong;
        if (paramLong == -2L) {
          l = this.mContentLength;
        }
      }
    }
    else
    {
      l = paramLong;
      if (paramLong == -1L) {
        l = this.mContentLength;
      }
    }
    this.lastrange = l;
    int i = (int)(l * 100L / this.mContentLength);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("QBDownloader.startDownload silent progress=");
    ((StringBuilder)localObject).append(i);
    ((StringBuilder)localObject).toString();
    localObject = this.mDownloadListener;
    if (localObject != null) {
      ((QBDownloadListener)localObject).onDownloadProgress(this.mIsSilent, i);
    }
  }
  
  /* Error */
  private boolean renameApkFile(File paramFile1, File paramFile2)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 290	java/io/File:exists	()Z
    //   4: ifeq +156 -> 160
    //   7: new 292	java/io/FileInputStream
    //   10: dup
    //   11: aload_1
    //   12: invokespecial 295	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   15: astore 4
    //   17: new 297	java/io/FileOutputStream
    //   20: dup
    //   21: aload_2
    //   22: invokespecial 298	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   25: astore_2
    //   26: ldc_w 299
    //   29: newarray <illegal type>
    //   31: astore 5
    //   33: aload 4
    //   35: aload 5
    //   37: invokevirtual 303	java/io/FileInputStream:read	([B)I
    //   40: istore_3
    //   41: iload_3
    //   42: iconst_m1
    //   43: if_icmpeq +14 -> 57
    //   46: aload_2
    //   47: aload 5
    //   49: iconst_0
    //   50: iload_3
    //   51: invokevirtual 307	java/io/FileOutputStream:write	([BII)V
    //   54: goto -21 -> 33
    //   57: aload_2
    //   58: invokevirtual 310	java/io/FileOutputStream:flush	()V
    //   61: aload_1
    //   62: invokevirtual 313	java/io/File:delete	()Z
    //   65: pop
    //   66: aload 4
    //   68: invokevirtual 314	java/io/FileInputStream:close	()V
    //   71: aload_2
    //   72: invokevirtual 315	java/io/FileOutputStream:close	()V
    //   75: iconst_1
    //   76: ireturn
    //   77: astore 5
    //   79: aload_2
    //   80: astore_1
    //   81: aload 5
    //   83: astore_2
    //   84: goto +26 -> 110
    //   87: aload_2
    //   88: astore_1
    //   89: goto +50 -> 139
    //   92: astore_2
    //   93: aconst_null
    //   94: astore_1
    //   95: goto +15 -> 110
    //   98: aconst_null
    //   99: astore_1
    //   100: goto +39 -> 139
    //   103: astore_2
    //   104: aconst_null
    //   105: astore 4
    //   107: aload 4
    //   109: astore_1
    //   110: aload 4
    //   112: ifnull +11 -> 123
    //   115: aload 4
    //   117: invokevirtual 314	java/io/FileInputStream:close	()V
    //   120: goto +3 -> 123
    //   123: aload_1
    //   124: ifnull +7 -> 131
    //   127: aload_1
    //   128: invokevirtual 315	java/io/FileOutputStream:close	()V
    //   131: aload_2
    //   132: athrow
    //   133: aconst_null
    //   134: astore 4
    //   136: aload 4
    //   138: astore_1
    //   139: aload 4
    //   141: ifnull +11 -> 152
    //   144: aload 4
    //   146: invokevirtual 314	java/io/FileInputStream:close	()V
    //   149: goto +3 -> 152
    //   152: aload_1
    //   153: ifnull +7 -> 160
    //   156: aload_1
    //   157: invokevirtual 315	java/io/FileOutputStream:close	()V
    //   160: iconst_0
    //   161: ireturn
    //   162: astore_1
    //   163: goto -30 -> 133
    //   166: astore_1
    //   167: goto -69 -> 98
    //   170: astore_1
    //   171: goto -84 -> 87
    //   174: astore_1
    //   175: goto -104 -> 71
    //   178: astore_1
    //   179: goto -104 -> 75
    //   182: astore 4
    //   184: goto -61 -> 123
    //   187: astore_1
    //   188: goto -57 -> 131
    //   191: astore_2
    //   192: goto -40 -> 152
    //   195: astore_1
    //   196: iconst_0
    //   197: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	198	0	this	FileDownloader
    //   0	198	1	paramFile1	File
    //   0	198	2	paramFile2	File
    //   40	11	3	i	int
    //   15	130	4	localFileInputStream	java.io.FileInputStream
    //   182	1	4	localException	Exception
    //   31	17	5	arrayOfByte	byte[]
    //   77	5	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   26	33	77	finally
    //   33	41	77	finally
    //   46	54	77	finally
    //   57	66	77	finally
    //   17	26	92	finally
    //   7	17	103	finally
    //   7	17	162	java/lang/Throwable
    //   17	26	166	java/lang/Throwable
    //   26	33	170	java/lang/Throwable
    //   33	41	170	java/lang/Throwable
    //   46	54	170	java/lang/Throwable
    //   57	66	170	java/lang/Throwable
    //   66	71	174	java/lang/Exception
    //   71	75	178	java/lang/Exception
    //   115	120	182	java/lang/Exception
    //   127	131	187	java/lang/Exception
    //   144	149	191	java/lang/Exception
    //   156	160	195	java/lang/Exception
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
    this.lastrange = 0L;
  }
  
  private void retry(long paramLong)
  {
    this.mRetryTimes += 1;
    long l = paramLong;
    if (paramLong <= 0L) {}
    try
    {
      l = retryInterval();
      Thread.sleep(l);
      return;
    }
    catch (Exception localException) {}
  }
  
  private long retryInterval()
  {
    int i = this.mRetryTimes;
    switch (i)
    {
    default: 
      return 200000L;
    case 3: 
    case 4: 
      return 100000L;
    }
    return i * 20000L;
  }
  
  private boolean verifyPackage(File paramFile)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("verifyPackage，");
    ((StringBuilder)localObject).append(TBSDownloaderHelper.getBundleMsg(this.mVerifyInfo));
    ((StringBuilder)localObject).toString();
    if (this.mVerifyInfo == null) {
      return true;
    }
    if (!paramFile.exists()) {
      return false;
    }
    localObject = this.mVerifyInfo.getString(KEY_VF_MD5, "");
    String str = AppUtil.getMd5(paramFile);
    if ((!TextUtils.isEmpty((CharSequence)localObject)) && (!((String)localObject).equals(str)))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("[FileDownloader.verifyPackage] tbsApkTempFile=");
      ((StringBuilder)localObject).append(paramFile);
      ((StringBuilder)localObject).append(" md5 failed!");
      ((StringBuilder)localObject).toString();
      return false;
    }
    long l = this.mVerifyInfo.getLong(KEY_VF_LEN, 0L);
    if ((paramFile != null) && (paramFile.exists()) && ((l <= 0L) || (l == paramFile.length())))
    {
      paramFile = new StringBuilder();
      paramFile.append("[FileDownloader.verifyPackage] length(");
      paramFile.append(l);
      paramFile.append(") successful!");
      paramFile.toString();
      return true;
    }
    paramFile = new StringBuilder();
    paramFile.append("[FileDownloader.verifyPackage]  fileLength error:");
    paramFile.append(l);
    paramFile.toString();
    return false;
  }
  
  private void writeFileLength(long paramLong)
  {
    this.mDownloadConfig.setFileLength(paramLong);
    this.mDownloadConfig.writeConfig();
  }
  
  protected boolean checkUpgradeIfNeeded()
  {
    if (!this.mDownloadFile.exists()) {
      return false;
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
    return (l1 - l2 > 86400000L) || (this.mDownloadConfig.isNeedUpgrade());
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
  
  public void deleteDownloadedFile()
  {
    try
    {
      if ((this.mTempDownloadFile != null) && (this.mTempDownloadFile.exists())) {
        this.mTempDownloadFile.delete();
      }
      if ((this.mDownloadFile != null) && (this.mDownloadFile.exists()))
      {
        this.mDownloadFile.delete();
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public File getDownloadFile()
  {
    return this.mDownloadFile;
  }
  
  public int getDownloadProgress()
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
  
  protected boolean isDownloading()
  {
    return this.mIsDownloading;
  }
  
  protected boolean isSilentDownloading()
  {
    return this.mIsSilent;
  }
  
  boolean needCheckDownloadFlow()
  {
    boolean bool1 = this.mAllowFlowcheck;
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    bool1 = bool2;
    if (this.mDownloadOverHook == null)
    {
      bool1 = bool2;
      if (this.mReqDownloadConfig != null) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public void setDownloadListener(QBDownloadListener paramQBDownloadListener)
  {
    this.mDownloadListener = paramQBDownloadListener;
  }
  
  protected void setExtraParams(boolean paramBoolean, QBDownloadListener paramQBDownloadListener, Bundle paramBundle, FileDownloadHook paramFileDownloadHook)
  {
    this.mIsSilent = paramBoolean;
    this.mDownloadListener = paramQBDownloadListener;
    int i;
    if (this.mIsSilent) {
      i = 5;
    } else {
      i = 1;
    }
    mMaxRetryTimes = i;
    this.mDownloadOverHook = paramFileDownloadHook;
    this.mParams = paramBundle;
  }
  
  protected void setSkipNetworkTypeCheck(boolean paramBoolean)
  {
    this.mSkipNetworkTypeCheck = paramBoolean;
  }
  
  public void setVerifyInfo(Bundle paramBundle)
  {
    this.mVerifyInfo = paramBundle;
  }
  
  public void setmAllowFlowcheck(boolean paramBoolean)
  {
    this.mAllowFlowcheck = paramBoolean;
  }
  
  public int startDownload(String paramString, boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  protected void stopDownload()
  {
    this.mCanceled = true;
  }
  
  protected static class DownloadConfig
  {
    private static final String DES_KEY = "!Q2@df0D";
    public static final String DOWNLOAD_CONFIG_FILENAME_SUFFIX = ".data";
    static final String KEY_DOWNLOAD_FILE_LENGTH = "download_file_length";
    static final String KEY_DOWNLOAD_URL = "download_url";
    static final String KEY_FILE_SIZE = "file_size";
    static final String KEY_LAST_CHECK = "last_check";
    static final String KEY_NEED_UPGRADE = "need_upgrade";
    static final String KEY_UPGRADE_URL = "upgrade_url";
    private File mDownloadConfigFile = null;
    long mDownloadFileLength = 0L;
    String mDownloadUrl = null;
    long mFileLength = 0L;
    long mLastCheck = 0L;
    boolean mNeedUpgrade = false;
    String mUpgradeUrl = null;
    
    protected DownloadConfig(File paramFile, String paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append(".data");
      this.mDownloadConfigFile = new File(paramFile, localStringBuilder.toString());
      paramFile = this.mDownloadConfigFile;
      if ((paramFile != null) && (!paramFile.exists())) {
        try
        {
          this.mDownloadConfigFile.createNewFile();
        }
        catch (Exception paramFile)
        {
          paramString = new StringBuilder();
          paramString.append("DownloadConfig create mDownloadConfigFile eror:");
          paramString.append(paramFile.toString());
          paramString.append(",mfile:");
          paramString.append(this.mDownloadConfigFile.getAbsolutePath());
          paramString.toString();
        }
      }
      readConfig();
    }
    
    /* Error */
    private void readConfig()
    {
      // Byte code:
      //   0: new 99	java/io/FileInputStream
      //   3: dup
      //   4: aload_0
      //   5: getfield 63	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mDownloadConfigFile	Ljava/io/File;
      //   8: invokespecial 102	java/io/FileInputStream:<init>	(Ljava/io/File;)V
      //   11: astore_1
      //   12: ldc 104
      //   14: invokestatic 110	javax/crypto/Cipher:getInstance	(Ljava/lang/String;)Ljavax/crypto/Cipher;
      //   17: astore_2
      //   18: aload_2
      //   19: iconst_2
      //   20: new 112	javax/crypto/spec/SecretKeySpec
      //   23: dup
      //   24: ldc 11
      //   26: invokevirtual 118	java/lang/String:getBytes	()[B
      //   29: ldc 104
      //   31: invokespecial 121	javax/crypto/spec/SecretKeySpec:<init>	([BLjava/lang/String;)V
      //   34: invokevirtual 125	javax/crypto/Cipher:init	(ILjava/security/Key;)V
      //   37: new 127	javax/crypto/CipherInputStream
      //   40: dup
      //   41: aload_1
      //   42: aload_2
      //   43: invokespecial 130	javax/crypto/CipherInputStream:<init>	(Ljava/io/InputStream;Ljavax/crypto/Cipher;)V
      //   46: astore_3
      //   47: new 132	java/util/Properties
      //   50: dup
      //   51: invokespecial 133	java/util/Properties:<init>	()V
      //   54: astore_2
      //   55: aload_2
      //   56: aload_3
      //   57: invokevirtual 137	java/util/Properties:load	(Ljava/io/InputStream;)V
      //   60: aload_0
      //   61: aload_2
      //   62: ldc 20
      //   64: invokevirtual 141	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
      //   67: putfield 51	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mDownloadUrl	Ljava/lang/String;
      //   70: aload_0
      //   71: aload_2
      //   72: ldc 26
      //   74: ldc -113
      //   76: invokevirtual 146	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   79: invokestatic 152	java/lang/Long:parseLong	(Ljava/lang/String;)J
      //   82: putfield 53	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mLastCheck	J
      //   85: aload_0
      //   86: aload_2
      //   87: ldc 32
      //   89: invokevirtual 141	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
      //   92: putfield 55	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mUpgradeUrl	Ljava/lang/String;
      //   95: aload_0
      //   96: aload_2
      //   97: ldc 29
      //   99: ldc -102
      //   101: invokevirtual 146	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   104: invokestatic 160	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
      //   107: putfield 57	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mNeedUpgrade	Z
      //   110: aload_0
      //   111: aload_2
      //   112: ldc 23
      //   114: ldc -113
      //   116: invokevirtual 146	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   119: invokestatic 152	java/lang/Long:parseLong	(Ljava/lang/String;)J
      //   122: putfield 59	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mFileLength	J
      //   125: aload_0
      //   126: aload_2
      //   127: ldc 17
      //   129: ldc -113
      //   131: invokevirtual 146	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   134: invokestatic 152	java/lang/Long:parseLong	(Ljava/lang/String;)J
      //   137: putfield 61	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mDownloadFileLength	J
      //   140: aload_3
      //   141: invokevirtual 163	javax/crypto/CipherInputStream:close	()V
      //   144: aload_1
      //   145: invokevirtual 164	java/io/FileInputStream:close	()V
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
      //   190: invokevirtual 163	javax/crypto/CipherInputStream:close	()V
      //   193: goto +3 -> 196
      //   196: aload 4
      //   198: ifnull +8 -> 206
      //   201: aload 4
      //   203: invokevirtual 164	java/io/FileInputStream:close	()V
      //   206: aload_2
      //   207: athrow
      //   208: aconst_null
      //   209: astore_1
      //   210: aload_1
      //   211: astore_2
      //   212: aload_2
      //   213: ifnull +10 -> 223
      //   216: aload_2
      //   217: invokevirtual 163	javax/crypto/CipherInputStream:close	()V
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
      //   1: invokestatic 187	java/lang/System:currentTimeMillis	()J
      //   4: putfield 53	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mLastCheck	J
      //   7: aconst_null
      //   8: astore 4
      //   10: aconst_null
      //   11: astore_2
      //   12: aconst_null
      //   13: astore 6
      //   15: new 132	java/util/Properties
      //   18: dup
      //   19: invokespecial 133	java/util/Properties:<init>	()V
      //   22: astore 5
      //   24: aload_0
      //   25: getfield 51	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mDownloadUrl	Ljava/lang/String;
      //   28: ifnonnull +9 -> 37
      //   31: ldc -67
      //   33: astore_1
      //   34: goto +8 -> 42
      //   37: aload_0
      //   38: getfield 51	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mDownloadUrl	Ljava/lang/String;
      //   41: astore_1
      //   42: aload 5
      //   44: ldc 20
      //   46: aload_1
      //   47: invokevirtual 193	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   50: pop
      //   51: aload 5
      //   53: ldc 26
      //   55: aload_0
      //   56: getfield 53	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mLastCheck	J
      //   59: invokestatic 197	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   62: invokevirtual 193	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   65: pop
      //   66: aload_0
      //   67: getfield 55	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mUpgradeUrl	Ljava/lang/String;
      //   70: ifnonnull +9 -> 79
      //   73: ldc -67
      //   75: astore_1
      //   76: goto +8 -> 84
      //   79: aload_0
      //   80: getfield 55	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mUpgradeUrl	Ljava/lang/String;
      //   83: astore_1
      //   84: aload 5
      //   86: ldc 32
      //   88: aload_1
      //   89: invokevirtual 193	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   92: pop
      //   93: aload 5
      //   95: ldc 29
      //   97: aload_0
      //   98: getfield 57	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mNeedUpgrade	Z
      //   101: invokestatic 200	java/lang/String:valueOf	(Z)Ljava/lang/String;
      //   104: invokevirtual 193	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   107: pop
      //   108: aload 5
      //   110: ldc 23
      //   112: aload_0
      //   113: getfield 59	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mFileLength	J
      //   116: invokestatic 197	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   119: invokevirtual 193	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   122: pop
      //   123: aload 5
      //   125: ldc 17
      //   127: aload_0
      //   128: getfield 61	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mDownloadFileLength	J
      //   131: invokestatic 197	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   134: invokevirtual 193	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   137: pop
      //   138: new 202	java/io/FileOutputStream
      //   141: dup
      //   142: aload_0
      //   143: getfield 63	com/tencent/tbs/common/download/qb/FileDownloader$DownloadConfig:mDownloadConfigFile	Ljava/io/File;
      //   146: invokespecial 203	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
      //   149: astore_1
      //   150: aload 4
      //   152: astore_2
      //   153: aload_1
      //   154: astore_3
      //   155: ldc 104
      //   157: invokestatic 110	javax/crypto/Cipher:getInstance	(Ljava/lang/String;)Ljavax/crypto/Cipher;
      //   160: astore 7
      //   162: aload 4
      //   164: astore_2
      //   165: aload_1
      //   166: astore_3
      //   167: aload 7
      //   169: iconst_1
      //   170: new 112	javax/crypto/spec/SecretKeySpec
      //   173: dup
      //   174: ldc 11
      //   176: invokevirtual 118	java/lang/String:getBytes	()[B
      //   179: ldc 104
      //   181: invokespecial 121	javax/crypto/spec/SecretKeySpec:<init>	([BLjava/lang/String;)V
      //   184: invokevirtual 125	javax/crypto/Cipher:init	(ILjava/security/Key;)V
      //   187: aload 4
      //   189: astore_2
      //   190: aload_1
      //   191: astore_3
      //   192: new 205	javax/crypto/CipherOutputStream
      //   195: dup
      //   196: aload_1
      //   197: aload 7
      //   199: invokespecial 208	javax/crypto/CipherOutputStream:<init>	(Ljava/io/OutputStream;Ljavax/crypto/Cipher;)V
      //   202: astore 4
      //   204: aload 5
      //   206: aload 4
      //   208: aconst_null
      //   209: invokevirtual 212	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
      //   212: aload 4
      //   214: invokevirtual 213	javax/crypto/CipherOutputStream:close	()V
      //   217: aload_1
      //   218: invokevirtual 214	java/io/FileOutputStream:close	()V
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
      //   264: invokevirtual 217	java/lang/Exception:printStackTrace	()V
      //   267: aload 4
      //   269: ifnull +11 -> 280
      //   272: aload 4
      //   274: invokevirtual 213	javax/crypto/CipherOutputStream:close	()V
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
      //   300: invokevirtual 213	javax/crypto/CipherOutputStream:close	()V
      //   303: goto +3 -> 306
      //   306: aload_1
      //   307: ifnull +7 -> 314
      //   310: aload_1
      //   311: invokevirtual 214	java/io/FileOutputStream:close	()V
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
  
  static abstract interface FileDownloadHook
  {
    public abstract boolean process();
  }
  
  private static abstract interface Header
  {
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String RANGE = "Range";
    public static final String REFERER = "Referer";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\FileDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */