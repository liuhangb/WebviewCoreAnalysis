package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.content.Context;

class MiniQBDownloadConfig
{
  public static final long CONFIG_CHECK_PERIOD = 86400000L;
  private static final int DEFAULT_DOWNLOAD_FAILED_MAX_RETRYTIMES = 100;
  private static final int DEFAULT_DOWNLOAD_MAX_FLOW = 20;
  private static final int DEFAULT_DOWNLOAD_MIN_FREE_SPACE = 70;
  private static final int DEFAULT_DOWNLOAD_SUCCESS_MAX_RETRYTIMES = 3;
  private static final String MINIQB_CFG_FILE = "miniqb_download.conf";
  private static MiniQBDownloadConfig mInstance;
  private String mAppMetaData;
  private int mAppVersionCode;
  private String mAppVersionName;
  private Context mContext;
  private String mDeviceCpu;
  private int mDownloadFailedMaxRetryTimes = 100;
  private int mDownloadFailedRetryTimes;
  private long mDownloadFlow;
  private long mDownloadMaxFlow = 20L;
  private long mDownloadStartTime;
  private int mDownloadSuccessMaxRetryTimes = 3;
  private int mDownloadSuccessRetryTimes;
  private boolean mIsFlowControl;
  private boolean mIsNeedDownload = true;
  private boolean mIsTestMiniQBEnv;
  private long mLastCheck;
  private String mMd5;
  private long mMinFreeSpace = 70L;
  private boolean mMiniQBDisabled;
  private String mMiniQBDownloadUrl;
  private int mMiniQBDownloadVersion;
  private long mMiniQBFileSize;
  private int mMiniQBLocalVersion;
  private int mMiniQBResponseCode;
  
  private MiniQBDownloadConfig(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    loadConfig();
  }
  
  public static MiniQBDownloadConfig getInstance(Context paramContext)
  {
    try
    {
      if (mInstance == null) {
        mInstance = new MiniQBDownloadConfig(paramContext);
      }
      paramContext = mInstance;
      return paramContext;
    }
    finally {}
  }
  
  /* Error */
  private void loadConfig()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: invokestatic 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getInstance	()Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager;
    //   5: aload_0
    //   6: getfield 79	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mContext	Landroid/content/Context;
    //   9: invokevirtual 102	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getMiniQBUpgradeDir	(Landroid/content/Context;)Ljava/io/File;
    //   12: astore_1
    //   13: aload_1
    //   14: ifnull +397 -> 411
    //   17: aload_1
    //   18: invokevirtual 108	java/io/File:isDirectory	()Z
    //   21: ifeq +390 -> 411
    //   24: new 104	java/io/File
    //   27: dup
    //   28: aload_1
    //   29: ldc 24
    //   31: invokespecial 111	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   34: astore_1
    //   35: aload_1
    //   36: invokevirtual 114	java/io/File:exists	()Z
    //   39: ifne +18 -> 57
    //   42: ldc 116
    //   44: ldc 118
    //   46: invokestatic 124	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   49: pop
    //   50: aload_0
    //   51: invokevirtual 127	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:commit	()V
    //   54: goto +357 -> 411
    //   57: new 129	java/io/FileInputStream
    //   60: dup
    //   61: aload_1
    //   62: invokespecial 132	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   65: astore_1
    //   66: new 134	java/io/BufferedInputStream
    //   69: dup
    //   70: aload_1
    //   71: invokespecial 137	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   74: astore 5
    //   76: aload_1
    //   77: astore_2
    //   78: aload 5
    //   80: astore 4
    //   82: new 139	java/util/Properties
    //   85: dup
    //   86: invokespecial 140	java/util/Properties:<init>	()V
    //   89: astore_3
    //   90: aload_1
    //   91: astore_2
    //   92: aload 5
    //   94: astore 4
    //   96: aload_3
    //   97: aload 5
    //   99: invokevirtual 143	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   102: aload_1
    //   103: astore_2
    //   104: aload 5
    //   106: astore 4
    //   108: aload_0
    //   109: aload_3
    //   110: ldc -111
    //   112: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   115: invokestatic 155	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   118: putfield 157	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mMiniQBResponseCode	I
    //   121: aload_1
    //   122: astore_2
    //   123: aload 5
    //   125: astore 4
    //   127: aload_0
    //   128: aload_3
    //   129: ldc -97
    //   131: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   134: invokestatic 165	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   137: putfield 167	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mLastCheck	J
    //   140: aload_1
    //   141: astore_2
    //   142: aload 5
    //   144: astore 4
    //   146: aload_0
    //   147: aload_3
    //   148: ldc -87
    //   150: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   153: invokestatic 155	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   156: putfield 171	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mMiniQBLocalVersion	I
    //   159: aload_1
    //   160: astore_2
    //   161: aload 5
    //   163: astore 4
    //   165: aload_0
    //   166: aload_3
    //   167: ldc -83
    //   169: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   172: invokestatic 179	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
    //   175: putfield 181	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mMiniQBDisabled	Z
    //   178: aload_1
    //   179: astore_2
    //   180: aload 5
    //   182: astore 4
    //   184: aload_0
    //   185: aload_3
    //   186: ldc -73
    //   188: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   191: invokestatic 179	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
    //   194: putfield 59	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mIsNeedDownload	Z
    //   197: aload_1
    //   198: astore_2
    //   199: aload 5
    //   201: astore 4
    //   203: aload_0
    //   204: aload_3
    //   205: ldc -71
    //   207: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   210: putfield 187	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mAppVersionName	Ljava/lang/String;
    //   213: aload_1
    //   214: astore_2
    //   215: aload 5
    //   217: astore 4
    //   219: aload_0
    //   220: aload_3
    //   221: ldc -67
    //   223: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   226: invokestatic 155	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   229: putfield 191	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mAppVersionCode	I
    //   232: aload_1
    //   233: astore_2
    //   234: aload 5
    //   236: astore 4
    //   238: aload_0
    //   239: aload_3
    //   240: ldc -63
    //   242: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   245: putfield 195	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mAppMetaData	Ljava/lang/String;
    //   248: aload_1
    //   249: astore_2
    //   250: aload 5
    //   252: astore 4
    //   254: aload_0
    //   255: aload_3
    //   256: ldc -59
    //   258: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   261: invokestatic 155	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   264: putfield 199	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mDownloadSuccessRetryTimes	I
    //   267: aload_1
    //   268: astore_2
    //   269: aload 5
    //   271: astore 4
    //   273: aload_0
    //   274: aload_3
    //   275: ldc -55
    //   277: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   280: invokestatic 155	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   283: putfield 203	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mDownloadFailedRetryTimes	I
    //   286: aload_1
    //   287: astore_2
    //   288: aload 5
    //   290: astore 4
    //   292: aload_0
    //   293: aload_3
    //   294: ldc -51
    //   296: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   299: invokestatic 165	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   302: putfield 207	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mDownloadStartTime	J
    //   305: aload_1
    //   306: astore_2
    //   307: aload 5
    //   309: astore 4
    //   311: aload_0
    //   312: aload_3
    //   313: ldc -47
    //   315: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   318: invokestatic 165	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   321: putfield 211	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mDownloadFlow	J
    //   324: aload_1
    //   325: astore_2
    //   326: aload 5
    //   328: astore 4
    //   330: aload_0
    //   331: aload_3
    //   332: ldc -43
    //   334: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   337: putfield 215	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mDeviceCpu	Ljava/lang/String;
    //   340: aload_1
    //   341: astore_2
    //   342: aload 5
    //   344: astore 4
    //   346: aload_0
    //   347: aload_3
    //   348: ldc -39
    //   350: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   353: putfield 219	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mMiniQBDownloadUrl	Ljava/lang/String;
    //   356: aload_1
    //   357: astore_2
    //   358: aload 5
    //   360: astore 4
    //   362: aload_0
    //   363: aload_3
    //   364: ldc -35
    //   366: ldc -33
    //   368: invokevirtual 226	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   371: invokestatic 179	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
    //   374: putfield 228	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mIsTestMiniQBEnv	Z
    //   377: aload 5
    //   379: astore_2
    //   380: goto +33 -> 413
    //   383: astore 6
    //   385: aload_1
    //   386: astore_3
    //   387: aload 5
    //   389: astore_1
    //   390: goto +59 -> 449
    //   393: astore_2
    //   394: aconst_null
    //   395: astore 4
    //   397: goto +93 -> 490
    //   400: astore 6
    //   402: aconst_null
    //   403: astore_2
    //   404: aload_1
    //   405: astore_3
    //   406: aload_2
    //   407: astore_1
    //   408: goto +41 -> 449
    //   411: aconst_null
    //   412: astore_1
    //   413: aload_2
    //   414: ifnull +10 -> 424
    //   417: aload_2
    //   418: invokevirtual 231	java/io/BufferedInputStream:close	()V
    //   421: goto +3 -> 424
    //   424: aload_1
    //   425: ifnull +59 -> 484
    //   428: aload_1
    //   429: invokevirtual 232	java/io/FileInputStream:close	()V
    //   432: return
    //   433: astore_2
    //   434: aconst_null
    //   435: astore 4
    //   437: aload 4
    //   439: astore_1
    //   440: goto +50 -> 490
    //   443: astore 6
    //   445: aconst_null
    //   446: astore_1
    //   447: aload_1
    //   448: astore_3
    //   449: aload_3
    //   450: astore_2
    //   451: aload_1
    //   452: astore 4
    //   454: aload 6
    //   456: invokevirtual 235	java/lang/Exception:printStackTrace	()V
    //   459: aload_1
    //   460: ifnull +10 -> 470
    //   463: aload_1
    //   464: invokevirtual 231	java/io/BufferedInputStream:close	()V
    //   467: goto +3 -> 470
    //   470: aload_3
    //   471: ifnull +13 -> 484
    //   474: aload_3
    //   475: invokevirtual 232	java/io/FileInputStream:close	()V
    //   478: return
    //   479: astore_1
    //   480: aload_1
    //   481: invokevirtual 236	java/io/IOException:printStackTrace	()V
    //   484: return
    //   485: astore_3
    //   486: aload_2
    //   487: astore_1
    //   488: aload_3
    //   489: astore_2
    //   490: aload 4
    //   492: ifnull +11 -> 503
    //   495: aload 4
    //   497: invokevirtual 231	java/io/BufferedInputStream:close	()V
    //   500: goto +3 -> 503
    //   503: aload_1
    //   504: ifnull +15 -> 519
    //   507: aload_1
    //   508: invokevirtual 232	java/io/FileInputStream:close	()V
    //   511: goto +8 -> 519
    //   514: astore_1
    //   515: aload_1
    //   516: invokevirtual 236	java/io/IOException:printStackTrace	()V
    //   519: aload_2
    //   520: athrow
    //   521: astore_2
    //   522: goto -98 -> 424
    //   525: astore_1
    //   526: goto -56 -> 470
    //   529: astore_3
    //   530: goto -27 -> 503
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	533	0	this	MiniQBDownloadConfig
    //   12	452	1	localObject1	Object
    //   479	2	1	localIOException1	java.io.IOException
    //   487	21	1	localObject2	Object
    //   514	2	1	localIOException2	java.io.IOException
    //   525	1	1	localIOException3	java.io.IOException
    //   1	379	2	localObject3	Object
    //   393	1	2	localObject4	Object
    //   403	15	2	localObject5	Object
    //   433	1	2	localObject6	Object
    //   450	70	2	localObject7	Object
    //   521	1	2	localIOException4	java.io.IOException
    //   89	386	3	localObject8	Object
    //   485	4	3	localObject9	Object
    //   529	1	3	localIOException5	java.io.IOException
    //   80	416	4	localObject10	Object
    //   74	314	5	localBufferedInputStream	java.io.BufferedInputStream
    //   383	1	6	localException1	Exception
    //   400	1	6	localException2	Exception
    //   443	12	6	localException3	Exception
    // Exception table:
    //   from	to	target	type
    //   82	90	383	java/lang/Exception
    //   96	102	383	java/lang/Exception
    //   108	121	383	java/lang/Exception
    //   127	140	383	java/lang/Exception
    //   146	159	383	java/lang/Exception
    //   165	178	383	java/lang/Exception
    //   184	197	383	java/lang/Exception
    //   203	213	383	java/lang/Exception
    //   219	232	383	java/lang/Exception
    //   238	248	383	java/lang/Exception
    //   254	267	383	java/lang/Exception
    //   273	286	383	java/lang/Exception
    //   292	305	383	java/lang/Exception
    //   311	324	383	java/lang/Exception
    //   330	340	383	java/lang/Exception
    //   346	356	383	java/lang/Exception
    //   362	377	383	java/lang/Exception
    //   66	76	393	finally
    //   66	76	400	java/lang/Exception
    //   2	13	433	finally
    //   17	54	433	finally
    //   57	66	433	finally
    //   2	13	443	java/lang/Exception
    //   17	54	443	java/lang/Exception
    //   57	66	443	java/lang/Exception
    //   428	432	479	java/io/IOException
    //   474	478	479	java/io/IOException
    //   82	90	485	finally
    //   96	102	485	finally
    //   108	121	485	finally
    //   127	140	485	finally
    //   146	159	485	finally
    //   165	178	485	finally
    //   184	197	485	finally
    //   203	213	485	finally
    //   219	232	485	finally
    //   238	248	485	finally
    //   254	267	485	finally
    //   273	286	485	finally
    //   292	305	485	finally
    //   311	324	485	finally
    //   330	340	485	finally
    //   346	356	485	finally
    //   362	377	485	finally
    //   454	459	485	finally
    //   507	511	514	java/io/IOException
    //   417	421	521	java/io/IOException
    //   463	467	525	java/io/IOException
    //   495	500	529	java/io/IOException
  }
  
  public void clearConfig()
  {
    this.mLastCheck = 0L;
    this.mMiniQBLocalVersion = 0;
    this.mMiniQBDisabled = false;
    this.mIsNeedDownload = true;
    this.mAppVersionName = "";
    this.mAppVersionCode = 0;
    this.mAppMetaData = "";
    this.mDownloadSuccessRetryTimes = 0;
    this.mDownloadFailedRetryTimes = 0;
    this.mDownloadStartTime = 0L;
    this.mDownloadFlow = 0L;
    this.mMiniQBDownloadUrl = "";
    this.mMiniQBDownloadVersion = 0;
    commit();
  }
  
  /* Error */
  public void commit()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aconst_null
    //   3: astore 6
    //   5: aconst_null
    //   6: astore_3
    //   7: aconst_null
    //   8: astore_2
    //   9: aconst_null
    //   10: astore 4
    //   12: invokestatic 98	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getInstance	()Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager;
    //   15: aload_0
    //   16: getfield 79	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mContext	Landroid/content/Context;
    //   19: invokevirtual 102	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBUpgradeManager:getMiniQBUpgradeDir	(Landroid/content/Context;)Ljava/io/File;
    //   22: astore_1
    //   23: aload_1
    //   24: ifnull +377 -> 401
    //   27: aload_1
    //   28: invokevirtual 108	java/io/File:isDirectory	()Z
    //   31: ifeq +370 -> 401
    //   34: new 104	java/io/File
    //   37: dup
    //   38: aload_1
    //   39: ldc 24
    //   41: invokespecial 111	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   44: astore 4
    //   46: aload 4
    //   48: invokevirtual 114	java/io/File:exists	()Z
    //   51: ifne +9 -> 60
    //   54: aload 4
    //   56: invokevirtual 244	java/io/File:createNewFile	()Z
    //   59: pop
    //   60: new 139	java/util/Properties
    //   63: dup
    //   64: invokespecial 140	java/util/Properties:<init>	()V
    //   67: astore 5
    //   69: aload 5
    //   71: ldc -111
    //   73: aload_0
    //   74: getfield 157	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mMiniQBResponseCode	I
    //   77: invokestatic 250	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   80: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   83: pop
    //   84: aload 5
    //   86: ldc -97
    //   88: aload_0
    //   89: getfield 167	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mLastCheck	J
    //   92: invokestatic 257	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   95: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   98: pop
    //   99: aload 5
    //   101: ldc -87
    //   103: aload_0
    //   104: getfield 171	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mMiniQBLocalVersion	I
    //   107: invokestatic 250	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   110: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   113: pop
    //   114: aload 5
    //   116: ldc -83
    //   118: aload_0
    //   119: getfield 181	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mMiniQBDisabled	Z
    //   122: invokestatic 260	java/lang/String:valueOf	(Z)Ljava/lang/String;
    //   125: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   128: pop
    //   129: aload 5
    //   131: ldc -73
    //   133: aload_0
    //   134: getfield 59	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mIsNeedDownload	Z
    //   137: invokestatic 260	java/lang/String:valueOf	(Z)Ljava/lang/String;
    //   140: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   143: pop
    //   144: aload_0
    //   145: getfield 187	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mAppVersionName	Ljava/lang/String;
    //   148: ifnonnull +9 -> 157
    //   151: ldc -17
    //   153: astore_1
    //   154: goto +8 -> 162
    //   157: aload_0
    //   158: getfield 187	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mAppVersionName	Ljava/lang/String;
    //   161: astore_1
    //   162: aload 5
    //   164: ldc -71
    //   166: aload_1
    //   167: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   170: pop
    //   171: aload 5
    //   173: ldc -67
    //   175: aload_0
    //   176: getfield 191	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mAppVersionCode	I
    //   179: invokestatic 250	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   182: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   185: pop
    //   186: aload_0
    //   187: getfield 195	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mAppMetaData	Ljava/lang/String;
    //   190: ifnonnull +9 -> 199
    //   193: ldc -17
    //   195: astore_1
    //   196: goto +8 -> 204
    //   199: aload_0
    //   200: getfield 195	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mAppMetaData	Ljava/lang/String;
    //   203: astore_1
    //   204: aload 5
    //   206: ldc -63
    //   208: aload_1
    //   209: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   212: pop
    //   213: aload 5
    //   215: ldc -59
    //   217: aload_0
    //   218: getfield 199	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mDownloadSuccessRetryTimes	I
    //   221: invokestatic 250	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   224: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   227: pop
    //   228: aload 5
    //   230: ldc -55
    //   232: aload_0
    //   233: getfield 203	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mDownloadFailedRetryTimes	I
    //   236: invokestatic 250	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   239: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   242: pop
    //   243: aload 5
    //   245: ldc -51
    //   247: aload_0
    //   248: getfield 207	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mDownloadStartTime	J
    //   251: invokestatic 257	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   254: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   257: pop
    //   258: aload 5
    //   260: ldc -47
    //   262: aload_0
    //   263: getfield 211	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mDownloadFlow	J
    //   266: invokestatic 257	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   269: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   272: pop
    //   273: aload_0
    //   274: getfield 215	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mDeviceCpu	Ljava/lang/String;
    //   277: ifnonnull +9 -> 286
    //   280: ldc -17
    //   282: astore_1
    //   283: goto +8 -> 291
    //   286: aload_0
    //   287: getfield 215	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mDeviceCpu	Ljava/lang/String;
    //   290: astore_1
    //   291: aload 5
    //   293: ldc -43
    //   295: aload_1
    //   296: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   299: pop
    //   300: aload_0
    //   301: getfield 219	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mMiniQBDownloadUrl	Ljava/lang/String;
    //   304: ifnonnull +9 -> 313
    //   307: ldc -17
    //   309: astore_1
    //   310: goto +8 -> 318
    //   313: aload_0
    //   314: getfield 219	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mMiniQBDownloadUrl	Ljava/lang/String;
    //   317: astore_1
    //   318: aload 5
    //   320: ldc -39
    //   322: aload_1
    //   323: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   326: pop
    //   327: aload 5
    //   329: ldc -35
    //   331: aload_0
    //   332: getfield 228	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBDownloadConfig:mIsTestMiniQBEnv	Z
    //   335: invokestatic 260	java/lang/String:valueOf	(Z)Ljava/lang/String;
    //   338: invokevirtual 254	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   341: pop
    //   342: new 262	java/io/FileOutputStream
    //   345: dup
    //   346: aload 4
    //   348: invokespecial 263	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   351: astore_1
    //   352: aload_3
    //   353: astore_2
    //   354: aload_1
    //   355: astore_3
    //   356: new 265	java/io/BufferedOutputStream
    //   359: dup
    //   360: aload_1
    //   361: invokespecial 268	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   364: astore 4
    //   366: aload 5
    //   368: aload 4
    //   370: aconst_null
    //   371: invokevirtual 272	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   374: aload 4
    //   376: astore_2
    //   377: goto +29 -> 406
    //   380: astore_3
    //   381: aload 4
    //   383: astore_2
    //   384: goto +115 -> 499
    //   387: astore 5
    //   389: goto +58 -> 447
    //   392: astore 5
    //   394: aload 6
    //   396: astore 4
    //   398: goto +49 -> 447
    //   401: aconst_null
    //   402: astore_1
    //   403: aload 4
    //   405: astore_2
    //   406: aload_2
    //   407: ifnull +7 -> 414
    //   410: aload_2
    //   411: invokevirtual 273	java/io/BufferedOutputStream:close	()V
    //   414: aload_1
    //   415: ifnull +74 -> 489
    //   418: aload_1
    //   419: invokevirtual 274	java/io/FileOutputStream:close	()V
    //   422: goto +67 -> 489
    //   425: astore_1
    //   426: aload_1
    //   427: invokevirtual 236	java/io/IOException:printStackTrace	()V
    //   430: goto +59 -> 489
    //   433: astore_3
    //   434: aconst_null
    //   435: astore_1
    //   436: goto +63 -> 499
    //   439: astore 5
    //   441: aconst_null
    //   442: astore_1
    //   443: aload 6
    //   445: astore 4
    //   447: aload 4
    //   449: astore_2
    //   450: aload_1
    //   451: astore_3
    //   452: aload 5
    //   454: invokevirtual 235	java/lang/Exception:printStackTrace	()V
    //   457: aload 4
    //   459: ifnull +15 -> 474
    //   462: aload 4
    //   464: invokevirtual 273	java/io/BufferedOutputStream:close	()V
    //   467: goto +7 -> 474
    //   470: astore_1
    //   471: goto +54 -> 525
    //   474: aload_1
    //   475: ifnull +14 -> 489
    //   478: aload_1
    //   479: invokevirtual 274	java/io/FileOutputStream:close	()V
    //   482: goto +7 -> 489
    //   485: astore_1
    //   486: goto -60 -> 426
    //   489: aload_0
    //   490: monitorexit
    //   491: return
    //   492: astore 4
    //   494: aload_3
    //   495: astore_1
    //   496: aload 4
    //   498: astore_3
    //   499: aload_2
    //   500: ifnull +7 -> 507
    //   503: aload_2
    //   504: invokevirtual 273	java/io/BufferedOutputStream:close	()V
    //   507: aload_1
    //   508: ifnull +15 -> 523
    //   511: aload_1
    //   512: invokevirtual 274	java/io/FileOutputStream:close	()V
    //   515: goto +8 -> 523
    //   518: astore_1
    //   519: aload_1
    //   520: invokevirtual 236	java/io/IOException:printStackTrace	()V
    //   523: aload_3
    //   524: athrow
    //   525: aload_0
    //   526: monitorexit
    //   527: aload_1
    //   528: athrow
    //   529: astore_2
    //   530: goto -116 -> 414
    //   533: astore_2
    //   534: goto -60 -> 474
    //   537: astore_2
    //   538: goto -31 -> 507
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	541	0	this	MiniQBDownloadConfig
    //   22	397	1	localObject1	Object
    //   425	2	1	localIOException1	java.io.IOException
    //   435	16	1	localObject2	Object
    //   470	9	1	localObject3	Object
    //   485	1	1	localIOException2	java.io.IOException
    //   495	17	1	localObject4	Object
    //   518	10	1	localIOException3	java.io.IOException
    //   8	496	2	localObject5	Object
    //   529	1	2	localIOException4	java.io.IOException
    //   533	1	2	localIOException5	java.io.IOException
    //   537	1	2	localIOException6	java.io.IOException
    //   6	350	3	localObject6	Object
    //   380	1	3	localObject7	Object
    //   433	1	3	localObject8	Object
    //   451	73	3	localObject9	Object
    //   10	453	4	localObject10	Object
    //   492	5	4	localObject11	Object
    //   67	300	5	localProperties	java.util.Properties
    //   387	1	5	localException1	Exception
    //   392	1	5	localException2	Exception
    //   439	14	5	localException3	Exception
    //   3	441	6	localObject12	Object
    // Exception table:
    //   from	to	target	type
    //   366	374	380	finally
    //   366	374	387	java/lang/Exception
    //   356	366	392	java/lang/Exception
    //   418	422	425	java/io/IOException
    //   12	23	433	finally
    //   27	60	433	finally
    //   60	151	433	finally
    //   157	162	433	finally
    //   162	193	433	finally
    //   199	204	433	finally
    //   204	280	433	finally
    //   286	291	433	finally
    //   291	307	433	finally
    //   313	318	433	finally
    //   318	352	433	finally
    //   12	23	439	java/lang/Exception
    //   27	60	439	java/lang/Exception
    //   60	151	439	java/lang/Exception
    //   157	162	439	java/lang/Exception
    //   162	193	439	java/lang/Exception
    //   199	204	439	java/lang/Exception
    //   204	280	439	java/lang/Exception
    //   286	291	439	java/lang/Exception
    //   291	307	439	java/lang/Exception
    //   313	318	439	java/lang/Exception
    //   318	352	439	java/lang/Exception
    //   410	414	470	finally
    //   418	422	470	finally
    //   426	430	470	finally
    //   462	467	470	finally
    //   478	482	470	finally
    //   503	507	470	finally
    //   511	515	470	finally
    //   519	523	470	finally
    //   523	525	470	finally
    //   478	482	485	java/io/IOException
    //   356	366	492	finally
    //   452	457	492	finally
    //   511	515	518	java/io/IOException
    //   410	414	529	java/io/IOException
    //   462	467	533	java/io/IOException
    //   503	507	537	java/io/IOException
  }
  
  public String getAppMetaData()
  {
    return this.mAppMetaData;
  }
  
  public int getAppVersionCode()
  {
    return this.mAppVersionCode;
  }
  
  public String getAppVersionName()
  {
    return this.mAppVersionName;
  }
  
  public String getDeviceCpu()
  {
    return this.mDeviceCpu;
  }
  
  public int getDownloadFailedMaxRetryTimes()
  {
    return this.mDownloadFailedMaxRetryTimes;
  }
  
  public int getDownloadFailedRetryTimes()
  {
    return this.mDownloadFailedRetryTimes;
  }
  
  public long getDownloadFlow()
  {
    return this.mDownloadFlow;
  }
  
  public long getDownloadMaxFlow()
  {
    long l2 = this.mDownloadMaxFlow;
    long l1 = l2;
    if (l2 == 0L) {
      l1 = 20L;
    }
    return l1 * 1024L * 1024L;
  }
  
  public long getDownloadStartTime()
  {
    return this.mDownloadStartTime;
  }
  
  public int getDownloadSuccessMaxRetryTimes()
  {
    return this.mDownloadSuccessMaxRetryTimes;
  }
  
  public int getDownloadSuccessRetryTimes()
  {
    return this.mDownloadSuccessRetryTimes;
  }
  
  public long getLastCheck()
  {
    return this.mLastCheck;
  }
  
  public String getMd5()
  {
    return this.mMd5;
  }
  
  public long getMinFreeSpace()
  {
    long l2 = this.mMinFreeSpace;
    long l1 = l2;
    if (l2 == 0L) {
      l1 = 70L;
    }
    return l1 * 1024L * 1024L;
  }
  
  public String getMiniQBDownloadUrl()
  {
    return this.mMiniQBDownloadUrl;
  }
  
  public int getMiniQBDownloadVersion()
  {
    return this.mMiniQBDownloadVersion;
  }
  
  public long getMiniQBFileSize()
  {
    return this.mMiniQBFileSize;
  }
  
  public int getMiniQBLocalVersion()
  {
    return this.mMiniQBLocalVersion;
  }
  
  public int getMiniQBResponseCode()
  {
    return this.mMiniQBResponseCode;
  }
  
  public Context getmContext()
  {
    return this.mContext;
  }
  
  public boolean isFlowControl()
  {
    return this.mIsFlowControl;
  }
  
  public boolean isMiniQBDisabled(int paramInt)
  {
    int i = this.mMiniQBLocalVersion;
    if (paramInt > i) {
      return false;
    }
    if (paramInt == i) {
      return this.mMiniQBDisabled;
    }
    return true;
  }
  
  public boolean isNeedDownload()
  {
    return this.mIsNeedDownload;
  }
  
  public boolean isTestMiniQBEnv()
  {
    return this.mIsTestMiniQBEnv;
  }
  
  public void setAppMetaData(String paramString)
  {
    this.mAppMetaData = paramString;
  }
  
  public void setAppVersionCode(int paramInt)
  {
    this.mAppVersionCode = paramInt;
  }
  
  public void setAppVersionName(String paramString)
  {
    this.mAppVersionName = paramString;
  }
  
  public void setDeviceCpu(String paramString)
  {
    this.mDeviceCpu = paramString;
  }
  
  public void setDownloadFailedMaxRetryTimes(int paramInt)
  {
    this.mDownloadFailedMaxRetryTimes = paramInt;
  }
  
  public void setDownloadFailedRetryTimes(int paramInt)
  {
    this.mDownloadFailedRetryTimes = paramInt;
  }
  
  public void setDownloadFlow(long paramLong)
  {
    this.mDownloadFlow = paramLong;
  }
  
  public void setDownloadMaxFlow(long paramLong)
  {
    this.mDownloadMaxFlow = paramLong;
  }
  
  public void setDownloadStartTime(long paramLong)
  {
    this.mDownloadStartTime = paramLong;
  }
  
  public void setDownloadSuccessMaxRetryTimes(int paramInt)
  {
    this.mDownloadSuccessMaxRetryTimes = paramInt;
  }
  
  public void setDownloadSuccessRetryTimes(int paramInt)
  {
    this.mDownloadSuccessRetryTimes = paramInt;
  }
  
  public void setFlowControl(boolean paramBoolean)
  {
    this.mIsFlowControl = paramBoolean;
  }
  
  public void setLastCheck(long paramLong)
  {
    this.mLastCheck = paramLong;
  }
  
  public void setMd5(String paramString)
  {
    this.mMd5 = paramString;
  }
  
  public void setMinFreeSpace(long paramLong)
  {
    this.mMinFreeSpace = paramLong;
  }
  
  public void setMiniQBDisabled(int paramInt, boolean paramBoolean)
  {
    this.mMiniQBLocalVersion = paramInt;
    this.mMiniQBDisabled = paramBoolean;
  }
  
  public void setMiniQBDownloadUrl(String paramString)
  {
    this.mMiniQBDownloadUrl = paramString;
  }
  
  public void setMiniQBDownloadVersion(int paramInt)
  {
    this.mMiniQBDownloadVersion = paramInt;
  }
  
  public void setMiniQBFileSize(long paramLong)
  {
    this.mMiniQBFileSize = paramLong;
  }
  
  public void setNeedDownload(boolean paramBoolean)
  {
    this.mIsNeedDownload = paramBoolean;
  }
  
  public void setTestMiniQBEnv(boolean paramBoolean)
  {
    this.mIsTestMiniQBEnv = paramBoolean;
    commit();
  }
  
  public void setmContext(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void setmMiniQBResponseCode(int paramInt)
  {
    this.mMiniQBResponseCode = paramInt;
  }
  
  public static abstract interface ConfigKey
  {
    public static final String KEY_APP_METADATA = "app_metadata";
    public static final String KEY_APP_VERSIONCODE = "app_versioncode";
    public static final String KEY_APP_VERSIONNAME = "app_versionname";
    public static final String KEY_DEVICE_CPUABI = "device_cpuabi";
    public static final String KEY_DOWNLOAD_FAILED_RETRYTIMES = "download_failed_retrytimes";
    public static final String KEY_DOWNLOAD_FLOW = "download_flow";
    public static final String KEY_DOWNLOAD_STARTTIME = "download_starttime";
    public static final String KEY_DOWNLOAD_SUCCESS_RETRYTIMES = "download_success_retrytimes";
    public static final String KEY_IS_TEST_MINIQB_ENV = "is_test_miniqb_env";
    public static final String KEY_LAST_CHECK = "last_check";
    public static final String KEY_MINIQB_DISABLED = "miniqb_disabled";
    public static final String KEY_MINIQB_DOWNLOADURL = "miniqb_downloadurl";
    public static final String KEY_MINIQB_LOCALVERSION = "miniqb_local_version";
    public static final String KEY_MINIQB_RESPONSECODE = "miniqb_responsecode";
    public static final String KEY_NEEDDOWNLOAD = "is_needdownload";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\MiniQBDownloadConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */