package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.content.Context;
import android.util.Log;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.log.LogManager;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.tbsshell.common.TbsLog;
import java.io.File;

public class MiniQBUpgradeManager
{
  static final String MINIQB_CORE_FOLDER_NAME = "core";
  static final String MINIQB_FILE_CONF = "miniqb.conf";
  static final String MINIQB_TMP_CORE_TEMP_FOLDER_NAME = "core_tmp";
  static final String MINIQB_UPGRADE_FOLDER_NAME = "miniqb";
  static final String MINIQB_VERSION_KEY = "miniqb_version";
  public static final String TAG = "MiniQBUpgrade";
  static final String TBS_FOLDER_NAME = "tbs";
  static final String TBS_PRIVATE_FOLDER_NAME = "core_private";
  static final String TBS_SHARE_FOLDER_NAME = "share";
  private static MiniQBUpgradeManager mInstance;
  private boolean isIniting = false;
  private String mAndroidID;
  private Context mAppContext;
  private String mCupInfo;
  private boolean mHasStartDownload;
  private String mImei;
  private String mImsi;
  private int mMiniQBDownloadedVersion;
  private int mMiniQBOriginalVersion;
  private MiniQBShareManager mMiniQBShareManager;
  private int mMiniQBUpgradeVersion;
  private int mMiniQBVersion = -1;
  private int mTbsCoreVersion;
  private String mTbsInstallLocation;
  private int mTbsSdkVersion;
  private Context mX5CoreProviderContext;
  
  private int getDownloadedVersion()
  {
    Object localObject = getMiniQBUpgradeCoreDir(this.mAppContext);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getDownloadedVersion upgradeCoreDir=");
    localStringBuilder.append(localObject);
    localStringBuilder.toString();
    int i = getMiniQBVersion((File)localObject);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getDownloadedVersion upgradeVersion=");
    ((StringBuilder)localObject).append(i);
    ((StringBuilder)localObject).toString();
    return i;
  }
  
  public static MiniQBUpgradeManager getInstance()
  {
    try
    {
      if (mInstance == null) {
        mInstance = new MiniQBUpgradeManager();
      }
      MiniQBUpgradeManager localMiniQBUpgradeManager = mInstance;
      return localMiniQBUpgradeManager;
    }
    finally {}
  }
  
  /* Error */
  private int getMiniQBVersion(File paramFile)
  {
    // Byte code:
    //   0: new 73	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   7: astore_3
    //   8: aload_3
    //   9: ldc 107
    //   11: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: pop
    //   15: aload_3
    //   16: aload_1
    //   17: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   20: pop
    //   21: aload_3
    //   22: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   25: pop
    //   26: aconst_null
    //   27: astore 4
    //   29: aconst_null
    //   30: astore_3
    //   31: aconst_null
    //   32: astore 7
    //   34: new 109	java/io/File
    //   37: dup
    //   38: aload_1
    //   39: ldc 11
    //   41: invokespecial 112	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   44: astore_1
    //   45: aload_1
    //   46: invokevirtual 116	java/io/File:exists	()Z
    //   49: ifne +5 -> 54
    //   52: iconst_0
    //   53: ireturn
    //   54: new 118	java/util/Properties
    //   57: dup
    //   58: invokespecial 119	java/util/Properties:<init>	()V
    //   61: astore 6
    //   63: new 121	java/io/FileInputStream
    //   66: dup
    //   67: aload_1
    //   68: invokespecial 124	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   71: astore_1
    //   72: aload 4
    //   74: astore_3
    //   75: aload_1
    //   76: astore 4
    //   78: new 126	java/io/BufferedInputStream
    //   81: dup
    //   82: aload_1
    //   83: invokespecial 129	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   86: astore 5
    //   88: aload 6
    //   90: aload 5
    //   92: invokevirtual 132	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   95: aload 6
    //   97: ldc 20
    //   99: invokevirtual 136	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   102: astore_3
    //   103: aload_3
    //   104: ifnonnull +58 -> 162
    //   107: aload 5
    //   109: invokevirtual 139	java/io/BufferedInputStream:close	()V
    //   112: goto +37 -> 149
    //   115: astore_3
    //   116: new 73	java/lang/StringBuilder
    //   119: dup
    //   120: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   123: astore 4
    //   125: aload 4
    //   127: ldc -115
    //   129: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: pop
    //   133: aload 4
    //   135: aload_3
    //   136: invokevirtual 142	java/io/IOException:toString	()Ljava/lang/String;
    //   139: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   142: pop
    //   143: aload 4
    //   145: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   148: pop
    //   149: aload_1
    //   150: invokevirtual 143	java/io/FileInputStream:close	()V
    //   153: iconst_0
    //   154: ireturn
    //   155: astore_1
    //   156: aload_1
    //   157: invokevirtual 146	java/io/IOException:printStackTrace	()V
    //   160: iconst_0
    //   161: ireturn
    //   162: aload_3
    //   163: invokestatic 152	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   166: istore_2
    //   167: aload 5
    //   169: invokevirtual 139	java/io/BufferedInputStream:close	()V
    //   172: goto +37 -> 209
    //   175: astore_3
    //   176: new 73	java/lang/StringBuilder
    //   179: dup
    //   180: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   183: astore 4
    //   185: aload 4
    //   187: ldc -115
    //   189: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: pop
    //   193: aload 4
    //   195: aload_3
    //   196: invokevirtual 142	java/io/IOException:toString	()Ljava/lang/String;
    //   199: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: pop
    //   203: aload 4
    //   205: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   208: pop
    //   209: aload_1
    //   210: invokevirtual 143	java/io/FileInputStream:close	()V
    //   213: iload_2
    //   214: ireturn
    //   215: astore_1
    //   216: aload_1
    //   217: invokevirtual 146	java/io/IOException:printStackTrace	()V
    //   220: iload_2
    //   221: ireturn
    //   222: astore 4
    //   224: aload 5
    //   226: astore_3
    //   227: aload 4
    //   229: astore 5
    //   231: goto +159 -> 390
    //   234: astore 6
    //   236: goto +27 -> 263
    //   239: astore 6
    //   241: aload 7
    //   243: astore 5
    //   245: goto +18 -> 263
    //   248: astore 5
    //   250: aconst_null
    //   251: astore_1
    //   252: goto +138 -> 390
    //   255: astore 6
    //   257: aconst_null
    //   258: astore_1
    //   259: aload 7
    //   261: astore 5
    //   263: aload 5
    //   265: astore_3
    //   266: aload_1
    //   267: astore 4
    //   269: new 73	java/lang/StringBuilder
    //   272: dup
    //   273: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   276: astore 7
    //   278: aload 5
    //   280: astore_3
    //   281: aload_1
    //   282: astore 4
    //   284: aload 7
    //   286: ldc -102
    //   288: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   291: pop
    //   292: aload 5
    //   294: astore_3
    //   295: aload_1
    //   296: astore 4
    //   298: aload 7
    //   300: aload 6
    //   302: invokevirtual 155	java/lang/Exception:toString	()Ljava/lang/String;
    //   305: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   308: pop
    //   309: aload 5
    //   311: astore_3
    //   312: aload_1
    //   313: astore 4
    //   315: aload 7
    //   317: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   320: pop
    //   321: aload 5
    //   323: ifnull +45 -> 368
    //   326: aload 5
    //   328: invokevirtual 139	java/io/BufferedInputStream:close	()V
    //   331: goto +37 -> 368
    //   334: astore_3
    //   335: new 73	java/lang/StringBuilder
    //   338: dup
    //   339: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   342: astore 4
    //   344: aload 4
    //   346: ldc -115
    //   348: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   351: pop
    //   352: aload 4
    //   354: aload_3
    //   355: invokevirtual 142	java/io/IOException:toString	()Ljava/lang/String;
    //   358: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   361: pop
    //   362: aload 4
    //   364: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   367: pop
    //   368: aload_1
    //   369: ifnull +14 -> 383
    //   372: aload_1
    //   373: invokevirtual 143	java/io/FileInputStream:close	()V
    //   376: iconst_0
    //   377: ireturn
    //   378: astore_1
    //   379: aload_1
    //   380: invokevirtual 146	java/io/IOException:printStackTrace	()V
    //   383: iconst_0
    //   384: ireturn
    //   385: astore 5
    //   387: aload 4
    //   389: astore_1
    //   390: aload_3
    //   391: ifnull +44 -> 435
    //   394: aload_3
    //   395: invokevirtual 139	java/io/BufferedInputStream:close	()V
    //   398: goto +37 -> 435
    //   401: astore_3
    //   402: new 73	java/lang/StringBuilder
    //   405: dup
    //   406: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   409: astore 4
    //   411: aload 4
    //   413: ldc -115
    //   415: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   418: pop
    //   419: aload 4
    //   421: aload_3
    //   422: invokevirtual 142	java/io/IOException:toString	()Ljava/lang/String;
    //   425: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   428: pop
    //   429: aload 4
    //   431: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   434: pop
    //   435: aload_1
    //   436: ifnull +15 -> 451
    //   439: aload_1
    //   440: invokevirtual 143	java/io/FileInputStream:close	()V
    //   443: goto +8 -> 451
    //   446: astore_1
    //   447: aload_1
    //   448: invokevirtual 146	java/io/IOException:printStackTrace	()V
    //   451: aload 5
    //   453: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	454	0	this	MiniQBUpgradeManager
    //   0	454	1	paramFile	File
    //   166	55	2	i	int
    //   7	97	3	localObject1	Object
    //   115	48	3	localIOException1	java.io.IOException
    //   175	21	3	localIOException2	java.io.IOException
    //   226	86	3	localObject2	Object
    //   334	61	3	localIOException3	java.io.IOException
    //   401	21	3	localIOException4	java.io.IOException
    //   27	177	4	localObject3	Object
    //   222	6	4	localObject4	Object
    //   267	163	4	localObject5	Object
    //   86	158	5	localObject6	Object
    //   248	1	5	localObject7	Object
    //   261	66	5	localStringBuilder1	StringBuilder
    //   385	67	5	localObject8	Object
    //   61	35	6	localProperties	java.util.Properties
    //   234	1	6	localException1	Exception
    //   239	1	6	localException2	Exception
    //   255	46	6	localException3	Exception
    //   32	284	7	localStringBuilder2	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   107	112	115	java/io/IOException
    //   149	153	155	java/io/IOException
    //   167	172	175	java/io/IOException
    //   209	213	215	java/io/IOException
    //   88	103	222	finally
    //   162	167	222	finally
    //   88	103	234	java/lang/Exception
    //   162	167	234	java/lang/Exception
    //   78	88	239	java/lang/Exception
    //   34	52	248	finally
    //   54	72	248	finally
    //   34	52	255	java/lang/Exception
    //   54	72	255	java/lang/Exception
    //   326	331	334	java/io/IOException
    //   372	376	378	java/io/IOException
    //   78	88	385	finally
    //   269	278	385	finally
    //   284	292	385	finally
    //   298	309	385	finally
    //   315	321	385	finally
    //   394	398	401	java/io/IOException
    //   439	443	446	java/io/IOException
  }
  
  private int getOriginalVersion()
  {
    int i = getMiniQBVersion(getTbsCoreShareDir());
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getOriginalVersion originalVersion=");
    localStringBuilder.append(i);
    localStringBuilder.toString();
    return i;
  }
  
  private int getUpgradeVersion()
  {
    if (isThirdPartyApp()) {
      localObject = this.mX5CoreProviderContext;
    } else {
      localObject = this.mAppContext;
    }
    Object localObject = getMiniQBUpgradeCoreDir((Context)localObject);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getUpgradeVersion upgradeCoreDir=");
    localStringBuilder.append(localObject);
    localStringBuilder.toString();
    int i = getMiniQBVersion((File)localObject);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getUpgradeVersion upgradeVersion=");
    ((StringBuilder)localObject).append(i);
    ((StringBuilder)localObject).toString();
    return i;
  }
  
  private File mkdir(File paramFile, String paramString)
  {
    paramFile = new File(paramFile, paramString);
    if ((!paramFile.isDirectory()) && (!paramFile.mkdir())) {
      return null;
    }
    return paramFile;
  }
  
  public void clearMiniQBUpgradeFile(Context paramContext)
  {
    MiniQBDownloadConfig.getInstance(paramContext).clearConfig();
    MiniQBApkDownloader.clearAllApkFile(paramContext);
    MiniQBInstaller.getInstance().clearAllInstallFiles(paramContext);
  }
  
  public void doMiniQBUpgradeTask()
  {
    try
    {
      if ((!this.mHasStartDownload) && (this.mAppContext != null))
      {
        boolean bool = Configuration.getInstance(this.mAppContext).isMiniqbUpdateEnable();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("isMiniqbUpgradeEnabled :");
        localStringBuilder.append(bool);
        localStringBuilder.toString();
        if (!bool) {
          return;
        }
        MiniQBDownloader.startDownload(this.mAppContext);
        this.mHasStartDownload = true;
      }
      return;
    }
    finally {}
  }
  
  String getAndroidID()
  {
    return this.mAndroidID;
  }
  
  String getCpuInfo()
  {
    return this.mCupInfo;
  }
  
  String getImei()
  {
    return this.mImei;
  }
  
  String getImsi()
  {
    return this.mImsi;
  }
  
  public String getMiniQBLoadPath()
  {
    int i = getMiniQBVersion();
    Object localObject;
    if (i == 0)
    {
      localObject = getTbsCoreShareDir().getAbsolutePath();
    }
    else if (i == this.mMiniQBOriginalVersion)
    {
      localObject = getTbsCoreShareDir().getAbsolutePath();
    }
    else if (i == this.mMiniQBUpgradeVersion)
    {
      if (isThirdPartyApp()) {
        localObject = this.mX5CoreProviderContext;
      } else {
        localObject = this.mAppContext;
      }
      localObject = getMiniQBUpgradeCoreDir((Context)localObject).getAbsolutePath();
    }
    else if (MiniQBShareManager.mAllowThirdAppUpgradeMiniqb)
    {
      localObject = getMiniQBUpgradeCoreDir(this.mAppContext).getAbsolutePath();
    }
    else
    {
      localObject = null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getMiniQBLoadPath miniQBLoadPath=");
    localStringBuilder.append((String)localObject);
    localStringBuilder.toString();
    return (String)localObject;
  }
  
  public String getMiniQBProvider()
  {
    Context localContext = this.mX5CoreProviderContext;
    if (localContext == null) {
      return "";
    }
    return localContext.getPackageName();
  }
  
  File getMiniQBUpgradeCoreDir(Context paramContext)
  {
    paramContext = getMiniQBUpgradeDir(paramContext);
    if (paramContext != null) {
      return mkdir(paramContext, "core");
    }
    return null;
  }
  
  File getMiniQBUpgradeCoreTempDir(Context paramContext)
  {
    paramContext = getMiniQBUpgradeDir(paramContext);
    if (paramContext != null) {
      return mkdir(paramContext, "core_tmp");
    }
    return null;
  }
  
  File getMiniQBUpgradeDir(Context paramContext)
  {
    paramContext = getTbsShareDir(paramContext);
    if (paramContext != null) {
      return mkdir(paramContext, "miniqb");
    }
    return null;
  }
  
  public int getMiniQBVersion()
  {
    initEnvironmentIfNeed();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getMiniQBVersion mMiniQBVersion=");
    localStringBuilder.append(this.mMiniQBVersion);
    localStringBuilder.toString();
    return this.mMiniQBVersion;
  }
  
  int[] getMiniQBVersionArray()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getMiniQBVersionArray mMiniQBUpgradeVersion=");
    localStringBuilder.append(this.mMiniQBUpgradeVersion);
    localStringBuilder.append(" mMiniQBOriginalVersion=");
    localStringBuilder.append(this.mMiniQBOriginalVersion);
    localStringBuilder.append(",mMiniQBDownloadedVersion=");
    localStringBuilder.append(this.mMiniQBDownloadedVersion);
    localStringBuilder.toString();
    if (!MiniQBShareManager.mAllowThirdAppUpgradeMiniqb) {
      return new int[] { this.mMiniQBUpgradeVersion, this.mMiniQBOriginalVersion };
    }
    return new int[] { this.mMiniQBUpgradeVersion, this.mMiniQBOriginalVersion, this.mMiniQBDownloadedVersion };
  }
  
  File getTbsCorePrivateDir(Context paramContext)
  {
    return mkdir(paramContext.getDir("tbs", 0), "core_private");
  }
  
  File getTbsCoreShareDir()
  {
    return new File(this.mTbsInstallLocation);
  }
  
  int getTbsCoreVersion()
  {
    return this.mTbsCoreVersion;
  }
  
  int getTbsSdkVersion()
  {
    return this.mTbsSdkVersion;
  }
  
  File getTbsShareDir(Context paramContext)
  {
    return mkdir(paramContext.getDir("tbs", 0), "share");
  }
  
  int getUpgradeVersion(Context paramContext)
  {
    paramContext = getMiniQBUpgradeCoreDir(paramContext);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getUpgradeVersion upgradeCoreDir=");
    localStringBuilder.append(paramContext);
    localStringBuilder.toString();
    int i = getMiniQBVersion(paramContext);
    paramContext = new StringBuilder();
    paramContext.append("getUpgradeVersion upgradeVersion=");
    paramContext.append(i);
    paramContext.toString();
    return i;
  }
  
  public void init(Context paramContext1, Context paramContext2, String paramString1, String paramString2, int paramInt)
  {
    this.mTbsCoreVersion = Integer.parseInt(paramString2);
    this.mTbsSdkVersion = paramInt;
    this.mAppContext = paramContext1;
    this.mX5CoreProviderContext = paramContext2;
    this.mTbsInstallLocation = paramString1;
  }
  
  public int initEnvironmentIfNeed()
  {
    label553:
    label558:
    label563:
    label566:
    for (;;)
    {
      int j;
      try
      {
        if (this.isIniting)
        {
          i = this.mMiniQBVersion;
          return i;
        }
        this.isIniting = true;
        if (this.mMiniQBVersion != -1)
        {
          i = this.mMiniQBVersion;
          return i;
        }
        this.mMiniQBShareManager = new MiniQBShareManager(this.mAppContext);
        if (this.mImei == null) {
          this.mImei = DeviceUtils.getIMEI(this.mAppContext);
        }
        if (this.mImsi == null) {
          this.mImsi = DeviceUtils.getIMSI(this.mAppContext);
        }
        if (this.mCupInfo == null) {
          this.mCupInfo = DeviceUtils.getDeviceCpuabi();
        }
        if (this.mAndroidID == null) {
          this.mAndroidID = DeviceUtils.getAndroidId();
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("initMiniQBVersion,threadid:");
        localStringBuilder.append(Thread.currentThread().getId());
        localStringBuilder.toString();
        MiniQBInstaller.getInstance().renameCoreIfNeeded(this.mAppContext);
        this.mMiniQBOriginalVersion = getOriginalVersion();
        this.mMiniQBUpgradeVersion = getUpgradeVersion();
        LogManager.logL1(9000, "o=%d,u=%d,d=%d,v=%d", new Object[] { Integer.valueOf(this.mMiniQBOriginalVersion), Integer.valueOf(this.mMiniQBUpgradeVersion), Integer.valueOf(this.mMiniQBDownloadedVersion), Integer.valueOf(this.mMiniQBVersion) });
        if (isThirdPartyApp())
        {
          j = this.mMiniQBShareManager.getShareVersion();
          if (j == 0)
          {
            i = 0;
          }
          else
          {
            if (j == this.mMiniQBOriginalVersion) {
              break label558;
            }
            if (j != this.mMiniQBUpgradeVersion) {
              break label553;
            }
            break label558;
            if (!MiniQBShareManager.mAllowThirdAppUpgradeMiniqb) {
              break label566;
            }
            this.mMiniQBDownloadedVersion = getDownloadedVersion();
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("third app allowThirdAppUpgradeMiniqb - shareVersion : ");
            localStringBuilder.append(j);
            localStringBuilder.append(" ,targetVersion: ");
            localStringBuilder.append(i);
            localStringBuilder.append(", downloadedVersion:");
            localStringBuilder.append(this.mMiniQBDownloadedVersion);
            localStringBuilder.toString();
            if ((this.mMiniQBDownloadedVersion == 0) || (this.mMiniQBDownloadedVersion <= i)) {
              break label566;
            }
            if (j == this.mMiniQBDownloadedVersion)
            {
              j = this.mMiniQBShareManager.getmMiniqbTbsCoreVersion();
              localStringBuilder = new StringBuilder();
              localStringBuilder.append("third app allowThirdAppUpgradeMiniqb - mTbsCoreVersion ");
              localStringBuilder.append(this.mTbsCoreVersion);
              localStringBuilder.append(" ,upgradeTbsCoreVersion: ");
              localStringBuilder.append(j);
              TbsLog.i("MiniQBUpgrade", localStringBuilder.toString());
              if ((j == 0) || (this.mTbsCoreVersion != j)) {
                break label563;
              }
              TbsLog.i("MiniQBUpgrade", "third app allowThirdAppUpgradeMiniqb use downloadedVersion ");
              i = this.mMiniQBDownloadedVersion;
            }
            else
            {
              Log.e("MiniQBUpgrade", "initEnvironmentIfNeed disable miniqb!!!");
              MiniQBDownloadConfig.getInstance(this.mAppContext).setMiniQBDisabled(j, true);
              MiniQBDownloadConfig.getInstance(this.mAppContext).commit();
              break label566;
            }
          }
        }
        else
        {
          i = Math.max(this.mMiniQBUpgradeVersion, this.mMiniQBOriginalVersion);
        }
        this.mMiniQBVersion = i;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("use target miniqbversion is : ");
        localStringBuilder.append(this.mMiniQBVersion);
        localStringBuilder.toString();
        this.isIniting = false;
        i = this.mMiniQBVersion;
        return i;
      }
      finally {}
      int i = 0;
      continue;
      i = j;
      continue;
    }
  }
  
  public boolean isMiniQBDisabled()
  {
    return MiniQBDownloadConfig.getInstance(this.mAppContext).isMiniQBDisabled(getMiniQBVersion());
  }
  
  public boolean isTestMiniQBEnv()
  {
    return MiniQBDownloadConfig.getInstance(this.mAppContext).isTestMiniQBEnv();
  }
  
  public boolean isThirdPartyApp()
  {
    return this.mMiniQBShareManager.isThirdPartyApp();
  }
  
  public void setTbsCoreVersion(int paramInt)
  {
    this.mTbsCoreVersion = paramInt;
  }
  
  public void setTbsSdkVersion(int paramInt)
  {
    this.mTbsSdkVersion = paramInt;
  }
  
  public void setTestMiniQBEnv(boolean paramBoolean)
  {
    MiniQBDownloadConfig.getInstance(this.mAppContext).setTestMiniQBEnv(paramBoolean);
  }
  
  void updateShareCoreVersion(int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("updateShareCoreVersion coreVersion=");
    localStringBuilder.append(paramInt1);
    localStringBuilder.toString();
    this.mMiniQBShareManager.updateShareCoreVersion(paramInt1, paramInt2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\MiniQBUpgradeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */