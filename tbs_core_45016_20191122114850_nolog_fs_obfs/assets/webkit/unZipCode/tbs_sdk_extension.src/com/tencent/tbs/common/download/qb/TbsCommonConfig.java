package com.tencent.tbs.common.download.qb;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class TbsCommonConfig
{
  private static final String COMMON_CONFIG_FILE = "tbsnet.conf";
  private static final String FORMAL_PV_POST_URL = "https://log.tbs.qq.com/ajax?c=pu&v=2&k=";
  private static final String FORMAL_PV_POST_URL_TK = "https://log.tbs.qq.com/ajax?c=pu&tk=";
  private static final String FORMAL_TBSLOG_POST_URL = "http://log.tbs.qq.com/ajax?c=ul&v=2&k=";
  private static final String FORMAL_TBS_CMD_POST_URL = "http://log.tbs.qq.com/ajax?c=ucfu&k=";
  private static final String FORMAL_TBS_DOWNLOADER_POST_URL = "http://cfg.imtt.qq.com/tbs?mk=";
  private static final String FORMAL_TBS_DOWNLOADER_POST_URL_V2 = "http://cfg.imtt.qq.com/tbs?v=2&mk=";
  private static final String FORMAL_TBS_DOWNLOAD_STAT_POST_URL = "http://log.tbs.qq.com/ajax?c=dl&k=";
  private static final String FORMAL_TIPS_URL = "http://mqqad.html5.qq.com/adjs";
  private static final String KEY_PV_POST_URL = "pv_post_url";
  private static final String KEY_PV_POST_URLWITHTK = "pv_post_url_tk";
  private static final String KEY_TBS_CMD_POST_URL = "tbs_cmd_post_url";
  private static final String KEY_TBS_DOWNLOADER_POST_URL = "tbs_downloader_post_url";
  private static final String KEY_TBS_DOWNLOAD_STAT_POST_URL = "tbs_download_stat_post_url";
  private static final String KEY_TBS_LOG_POST_URL = "tbs_log_post_url";
  private static final String KEY_TIPS_URL = "tips_url";
  private static final String KEY_WUP_PROXY_DOMAIN = "wup_proxy_domain";
  private static final String LOGTAG = "TbsCommonConfig";
  private static final String TBS_FOLDER_NAME = "tbs";
  private static final String TEST_PV_POST_URL = "http://tr.cs0309.imtt.qq.com/ajax?c=pu&k=";
  private static final String TEST_TBSLOG_POST_URL = "http://tr.cs0309.imtt.qq.com/ajax?c=ul&k=";
  private static final String TEST_TBS_CMD_POST_URL = "http://tr.cs0309.imtt.qq.com/ajax?c=ucfu&k=";
  private static final String TEST_TBS_DOWNLOADER_POST_URL = "http://cfg.cs0309.imtt.qq.com/tbs?mk=";
  private static final String TEST_TBS_DOWNLOAD_STAT_POST_URL = "http://tr.cs0309.imtt.qq.com/ajax?c=dl&k=";
  private static final String TEST_TIPS_URL = "http://mqqad.cs0309.html5.qq.com/adjs";
  private static final String WUP_PROXY_DOMAIN = "http://wup.imtt.qq.com:8080";
  private static TbsCommonConfig mInstance;
  private Context mContext = null;
  private String mPvUploadPostUrl = "https://log.tbs.qq.com/ajax?c=pu&v=2&k=";
  private String mPvUploadPostUrlTK = "https://log.tbs.qq.com/ajax?c=pu&tk=";
  private String mTbsCmdPostUrl = "http://log.tbs.qq.com/ajax?c=ucfu&k=";
  private File mTbsConfigDir = null;
  private String mTbsDownloadStatPostUrl = "http://log.tbs.qq.com/ajax?c=dl&k=";
  private String mTbsDownloaderPostUrl = "http://cfg.imtt.qq.com/tbs?v=2&mk=";
  private String mTbsLogPostUrl = "http://log.tbs.qq.com/ajax?c=ul&v=2&k=";
  private String mTipsUrl = "http://mqqad.html5.qq.com/adjs";
  private String mWupProxyDomain = "http://wup.imtt.qq.com:8080";
  
  @TargetApi(11)
  private TbsCommonConfig(Context paramContext)
  {
    Log.w("TbsCommonConfig", "TbsCommonConfig constructing...");
    this.mContext = paramContext.getApplicationContext();
    loadProperties();
  }
  
  private File getConfigFile()
  {
    StringWriter localStringWriter = null;
    Object localObject;
    try
    {
      if (this.mTbsConfigDir == null)
      {
        this.mTbsConfigDir = new File(getFileDirStr(this.mContext));
        if (this.mTbsConfigDir == null) {
          break label210;
        }
        if (!this.mTbsConfigDir.isDirectory()) {
          return null;
        }
      }
      localObject = new File(this.mTbsConfigDir, "tbsnet.conf");
      StringBuilder localStringBuilder1;
      if (!((File)localObject).exists())
      {
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("Get file(");
        localStringBuilder1.append(((File)localObject).getCanonicalPath());
        localStringBuilder1.append(") failed!");
        Log.e("TbsCommonConfig", localStringBuilder1.toString());
        return null;
      }
      try
      {
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("pathc:");
        localStringBuilder1.append(((File)localObject).getCanonicalPath());
        Log.w("TbsCommonConfig", localStringBuilder1.toString());
        return (File)localObject;
      }
      catch (Throwable localThrowable1) {}
      localStringWriter = new StringWriter();
    }
    catch (Throwable localThrowable2)
    {
      localObject = localStringWriter;
    }
    localThrowable2.printStackTrace(new PrintWriter(localStringWriter));
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append("exceptions occurred2:");
    localStringBuilder2.append(localStringWriter.toString());
    Log.e("TbsCommonConfig", localStringBuilder2.toString());
    return (File)localObject;
    label210:
    return null;
  }
  
  public static TbsCommonConfig getInstance()
  {
    try
    {
      TbsCommonConfig localTbsCommonConfig = mInstance;
      return localTbsCommonConfig;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static TbsCommonConfig getInstance(Context paramContext)
  {
    try
    {
      if (mInstance == null) {
        mInstance = new TbsCommonConfig(paramContext);
      }
      paramContext = mInstance;
      return paramContext;
    }
    finally {}
  }
  
  /* Error */
  private void loadProperties()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial 217	com/tencent/tbs/common/download/qb/TbsCommonConfig:getConfigFile	()Ljava/io/File;
    //   6: astore_1
    //   7: aload_1
    //   8: ifnonnull +14 -> 22
    //   11: ldc 59
    //   13: ldc -37
    //   15: invokestatic 189	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   18: pop
    //   19: aload_0
    //   20: monitorexit
    //   21: return
    //   22: new 221	java/io/BufferedInputStream
    //   25: dup
    //   26: new 223	java/io/FileInputStream
    //   29: dup
    //   30: aload_1
    //   31: invokespecial 226	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   34: invokespecial 229	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   37: astore_2
    //   38: aload_2
    //   39: astore_1
    //   40: new 231	java/util/Properties
    //   43: dup
    //   44: invokespecial 232	java/util/Properties:<init>	()V
    //   47: astore_3
    //   48: aload_2
    //   49: astore_1
    //   50: aload_3
    //   51: aload_2
    //   52: invokevirtual 235	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   55: aload_2
    //   56: astore_1
    //   57: aload_3
    //   58: ldc 35
    //   60: ldc -19
    //   62: invokevirtual 241	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   65: astore 4
    //   67: aload_2
    //   68: astore_1
    //   69: ldc -19
    //   71: aload 4
    //   73: invokevirtual 247	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   76: ifne +11 -> 87
    //   79: aload_2
    //   80: astore_1
    //   81: aload_0
    //   82: aload 4
    //   84: putfield 113	com/tencent/tbs/common/download/qb/TbsCommonConfig:mPvUploadPostUrl	Ljava/lang/String;
    //   87: aload_2
    //   88: astore_1
    //   89: aload_3
    //   90: ldc 56
    //   92: ldc -19
    //   94: invokevirtual 241	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   97: astore 4
    //   99: aload_2
    //   100: astore_1
    //   101: ldc -19
    //   103: aload 4
    //   105: invokevirtual 247	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   108: ifne +11 -> 119
    //   111: aload_2
    //   112: astore_1
    //   113: aload_0
    //   114: aload 4
    //   116: putfield 117	com/tencent/tbs/common/download/qb/TbsCommonConfig:mWupProxyDomain	Ljava/lang/String;
    //   119: aload_2
    //   120: astore_1
    //   121: aload_3
    //   122: ldc 47
    //   124: ldc -19
    //   126: invokevirtual 241	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   129: astore 4
    //   131: aload_2
    //   132: astore_1
    //   133: ldc -19
    //   135: aload 4
    //   137: invokevirtual 247	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   140: ifne +11 -> 151
    //   143: aload_2
    //   144: astore_1
    //   145: aload_0
    //   146: aload 4
    //   148: putfield 119	com/tencent/tbs/common/download/qb/TbsCommonConfig:mTbsDownloadStatPostUrl	Ljava/lang/String;
    //   151: aload_2
    //   152: astore_1
    //   153: aload_3
    //   154: ldc 44
    //   156: ldc -19
    //   158: invokevirtual 241	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   161: astore 4
    //   163: aload_2
    //   164: astore_1
    //   165: ldc -19
    //   167: aload 4
    //   169: invokevirtual 247	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   172: ifne +11 -> 183
    //   175: aload_2
    //   176: astore_1
    //   177: aload_0
    //   178: aload 4
    //   180: putfield 121	com/tencent/tbs/common/download/qb/TbsCommonConfig:mTbsDownloaderPostUrl	Ljava/lang/String;
    //   183: aload_2
    //   184: astore_1
    //   185: aload_3
    //   186: ldc 50
    //   188: ldc -19
    //   190: invokevirtual 241	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   193: astore 4
    //   195: aload_2
    //   196: astore_1
    //   197: ldc -19
    //   199: aload 4
    //   201: invokevirtual 247	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   204: ifne +11 -> 215
    //   207: aload_2
    //   208: astore_1
    //   209: aload_0
    //   210: aload 4
    //   212: putfield 123	com/tencent/tbs/common/download/qb/TbsCommonConfig:mTbsLogPostUrl	Ljava/lang/String;
    //   215: aload_2
    //   216: astore_1
    //   217: aload_3
    //   218: ldc 53
    //   220: ldc -19
    //   222: invokevirtual 241	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   225: astore 4
    //   227: aload_2
    //   228: astore_1
    //   229: ldc -19
    //   231: aload 4
    //   233: invokevirtual 247	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   236: ifne +11 -> 247
    //   239: aload_2
    //   240: astore_1
    //   241: aload_0
    //   242: aload 4
    //   244: putfield 125	com/tencent/tbs/common/download/qb/TbsCommonConfig:mTipsUrl	Ljava/lang/String;
    //   247: aload_2
    //   248: astore_1
    //   249: aload_3
    //   250: ldc 41
    //   252: ldc -19
    //   254: invokevirtual 241	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   257: astore 4
    //   259: aload_2
    //   260: astore_1
    //   261: ldc -19
    //   263: aload 4
    //   265: invokevirtual 247	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   268: ifne +11 -> 279
    //   271: aload_2
    //   272: astore_1
    //   273: aload_0
    //   274: aload 4
    //   276: putfield 127	com/tencent/tbs/common/download/qb/TbsCommonConfig:mTbsCmdPostUrl	Ljava/lang/String;
    //   279: aload_2
    //   280: astore_1
    //   281: aload_3
    //   282: ldc 38
    //   284: ldc -19
    //   286: invokevirtual 241	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   289: astore_3
    //   290: aload_2
    //   291: astore_1
    //   292: ldc -19
    //   294: aload_3
    //   295: invokevirtual 247	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   298: ifne +10 -> 308
    //   301: aload_2
    //   302: astore_1
    //   303: aload_0
    //   304: aload_3
    //   305: putfield 115	com/tencent/tbs/common/download/qb/TbsCommonConfig:mPvUploadPostUrlTK	Ljava/lang/String;
    //   308: aload_2
    //   309: invokevirtual 250	java/io/BufferedInputStream:close	()V
    //   312: goto +108 -> 420
    //   315: astore_1
    //   316: aload_1
    //   317: invokevirtual 252	java/io/IOException:printStackTrace	()V
    //   320: goto +100 -> 420
    //   323: astore_3
    //   324: goto +12 -> 336
    //   327: astore_1
    //   328: aconst_null
    //   329: astore_2
    //   330: goto +98 -> 428
    //   333: astore_3
    //   334: aconst_null
    //   335: astore_2
    //   336: aload_2
    //   337: astore_1
    //   338: new 193	java/io/StringWriter
    //   341: dup
    //   342: invokespecial 194	java/io/StringWriter:<init>	()V
    //   345: astore 4
    //   347: aload_2
    //   348: astore_1
    //   349: aload_3
    //   350: new 196	java/io/PrintWriter
    //   353: dup
    //   354: aload 4
    //   356: invokespecial 199	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   359: invokevirtual 203	java/lang/Throwable:printStackTrace	(Ljava/io/PrintWriter;)V
    //   362: aload_2
    //   363: astore_1
    //   364: new 170	java/lang/StringBuilder
    //   367: dup
    //   368: invokespecial 171	java/lang/StringBuilder:<init>	()V
    //   371: astore_3
    //   372: aload_2
    //   373: astore_1
    //   374: aload_3
    //   375: ldc -2
    //   377: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   380: pop
    //   381: aload_2
    //   382: astore_1
    //   383: aload_3
    //   384: aload 4
    //   386: invokevirtual 206	java/io/StringWriter:toString	()Ljava/lang/String;
    //   389: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   392: pop
    //   393: aload_2
    //   394: astore_1
    //   395: ldc 59
    //   397: aload_3
    //   398: invokevirtual 186	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   401: invokestatic 189	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   404: pop
    //   405: aload_2
    //   406: ifnull +14 -> 420
    //   409: aload_2
    //   410: invokevirtual 250	java/io/BufferedInputStream:close	()V
    //   413: goto +7 -> 420
    //   416: astore_1
    //   417: goto -101 -> 316
    //   420: aload_0
    //   421: monitorexit
    //   422: return
    //   423: astore_3
    //   424: aload_1
    //   425: astore_2
    //   426: aload_3
    //   427: astore_1
    //   428: aload_2
    //   429: ifnull +15 -> 444
    //   432: aload_2
    //   433: invokevirtual 250	java/io/BufferedInputStream:close	()V
    //   436: goto +8 -> 444
    //   439: astore_2
    //   440: aload_2
    //   441: invokevirtual 252	java/io/IOException:printStackTrace	()V
    //   444: aload_1
    //   445: athrow
    //   446: astore_1
    //   447: aload_0
    //   448: monitorexit
    //   449: aload_1
    //   450: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	451	0	this	TbsCommonConfig
    //   6	297	1	localObject1	Object
    //   315	2	1	localIOException1	java.io.IOException
    //   327	1	1	localObject2	Object
    //   337	58	1	localObject3	Object
    //   416	9	1	localIOException2	java.io.IOException
    //   427	18	1	localObject4	Object
    //   446	4	1	localObject5	Object
    //   37	396	2	localObject6	Object
    //   439	2	2	localIOException3	java.io.IOException
    //   47	258	3	localObject7	Object
    //   323	1	3	localThrowable1	Throwable
    //   333	17	3	localThrowable2	Throwable
    //   371	27	3	localStringBuilder	StringBuilder
    //   423	4	3	localObject8	Object
    //   65	320	4	localObject9	Object
    // Exception table:
    //   from	to	target	type
    //   308	312	315	java/io/IOException
    //   40	48	323	java/lang/Throwable
    //   50	55	323	java/lang/Throwable
    //   57	67	323	java/lang/Throwable
    //   69	79	323	java/lang/Throwable
    //   81	87	323	java/lang/Throwable
    //   89	99	323	java/lang/Throwable
    //   101	111	323	java/lang/Throwable
    //   113	119	323	java/lang/Throwable
    //   121	131	323	java/lang/Throwable
    //   133	143	323	java/lang/Throwable
    //   145	151	323	java/lang/Throwable
    //   153	163	323	java/lang/Throwable
    //   165	175	323	java/lang/Throwable
    //   177	183	323	java/lang/Throwable
    //   185	195	323	java/lang/Throwable
    //   197	207	323	java/lang/Throwable
    //   209	215	323	java/lang/Throwable
    //   217	227	323	java/lang/Throwable
    //   229	239	323	java/lang/Throwable
    //   241	247	323	java/lang/Throwable
    //   249	259	323	java/lang/Throwable
    //   261	271	323	java/lang/Throwable
    //   273	279	323	java/lang/Throwable
    //   281	290	323	java/lang/Throwable
    //   292	301	323	java/lang/Throwable
    //   303	308	323	java/lang/Throwable
    //   2	7	327	finally
    //   11	19	327	finally
    //   22	38	327	finally
    //   2	7	333	java/lang/Throwable
    //   11	19	333	java/lang/Throwable
    //   22	38	333	java/lang/Throwable
    //   409	413	416	java/io/IOException
    //   40	48	423	finally
    //   50	55	423	finally
    //   57	67	423	finally
    //   69	79	423	finally
    //   81	87	423	finally
    //   89	99	423	finally
    //   101	111	423	finally
    //   113	119	423	finally
    //   121	131	423	finally
    //   133	143	423	finally
    //   145	151	423	finally
    //   153	163	423	finally
    //   165	175	423	finally
    //   177	183	423	finally
    //   185	195	423	finally
    //   197	207	423	finally
    //   209	215	423	finally
    //   217	227	423	finally
    //   229	239	423	finally
    //   241	247	423	finally
    //   249	259	423	finally
    //   261	271	423	finally
    //   273	279	423	finally
    //   281	290	423	finally
    //   292	301	423	finally
    //   303	308	423	finally
    //   338	347	423	finally
    //   349	362	423	finally
    //   364	372	423	finally
    //   374	381	423	finally
    //   383	393	423	finally
    //   395	405	423	finally
    //   432	436	439	java/io/IOException
    //   308	312	446	finally
    //   316	320	446	finally
    //   409	413	446	finally
    //   432	436	446	finally
    //   440	444	446	finally
    //   444	446	446	finally
  }
  
  String getFileDirStr(Context paramContext)
  {
    String str = paramContext.getApplicationInfo().packageName;
    paramContext = "";
    try
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(Environment.getExternalStorageDirectory());
      ((StringBuilder)localObject).append(File.separator);
      localObject = ((StringBuilder)localObject).toString();
      paramContext = (Context)localObject;
    }
    catch (Throwable localThrowable)
    {
      Object localObject;
      for (;;) {}
    }
    if (paramContext.equals("")) {
      return paramContext;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramContext);
    ((StringBuilder)localObject).append("tencent");
    ((StringBuilder)localObject).append(File.separator);
    ((StringBuilder)localObject).append("tbs");
    ((StringBuilder)localObject).append(File.separator);
    ((StringBuilder)localObject).append(str);
    return ((StringBuilder)localObject).toString();
  }
  
  public String getPvUploadPostUrl()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getPvUploadPostUrl:");
    localStringBuilder.append(this.mPvUploadPostUrl);
    localStringBuilder.toString();
    return this.mPvUploadPostUrl;
  }
  
  public String getPvUploadPostUrlWithToken()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getPvUploadPostUrl:");
    localStringBuilder.append(this.mPvUploadPostUrlTK);
    localStringBuilder.toString();
    return this.mPvUploadPostUrlTK;
  }
  
  public String getTbsDownloadStatPostUrl()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getTbsDownloadStatPostUrl:");
    localStringBuilder.append(this.mTbsDownloadStatPostUrl);
    localStringBuilder.toString();
    return this.mTbsDownloadStatPostUrl;
  }
  
  public String getTbsDownloaderPostUrl()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getTbsDownloaderPostUrl:");
    localStringBuilder.append(this.mTbsDownloaderPostUrl);
    localStringBuilder.toString();
    return this.mTbsDownloaderPostUrl;
  }
  
  public String getTbsLogPostUrl()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getTbsLogPostUrl:");
    localStringBuilder.append(this.mTbsLogPostUrl);
    localStringBuilder.toString();
    return this.mTbsLogPostUrl;
  }
  
  public String getTipsUrl()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getTipsUrl:");
    localStringBuilder.append(this.mTipsUrl);
    localStringBuilder.toString();
    return this.mTipsUrl;
  }
  
  public String getWupProxyDomain()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getWupProxyDomain:");
    localStringBuilder.append(this.mWupProxyDomain);
    localStringBuilder.toString();
    return this.mWupProxyDomain;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\TbsCommonConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */