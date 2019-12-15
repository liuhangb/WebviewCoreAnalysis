package com.tencent.tbs.core.partner.tencentfiledownload;

import android.os.Bundle;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

class TFMDownloader
{
  private static final int BUFFER_SIZE = 8192;
  private static final long MIN_RETRY_INTERVAL = 20000L;
  private static final long PROGRESS_NOTIFY_INTERVAL = 1000L;
  public static String mErrorType = "tfdownloadererrorcode";
  private static int mMaxRetryTimes = 5;
  private static int mMaxRetryTimes302 = 10;
  private boolean mCanceled;
  private int mConnectTimeout = 60000;
  private long mContentLength = -1L;
  private DownloadConfig mDownloadConfig;
  private File mDownloadFile;
  private TFMDownloadListener mDownloadListener;
  private String mDownloadUrl;
  private boolean mFinished;
  private HttpURLConnection mHttpRequest;
  private boolean mIsDownloading;
  private String mLocation;
  private Bundle mParams;
  private int mReadTimeout = 90000;
  private int mRetryTimes;
  private int mRetryTimes302;
  
  public TFMDownloader(String paramString1, File paramFile, String paramString2)
  {
    this.mDownloadUrl = paramString1;
    this.mDownloadFile = new File(paramFile, paramString2);
    this.mDownloadConfig = new DownloadConfig(paramFile);
    resetArgs();
    this.mContentLength = this.mDownloadConfig.getFileLength();
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
  
  private void resetArgs()
  {
    this.mRetryTimes = 0;
    this.mRetryTimes302 = 0;
    this.mContentLength = 0L;
    this.mLocation = null;
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
  
  private void writeFileLength(long paramLong)
  {
    this.mDownloadConfig.setFileLength(paramLong);
    this.mDownloadConfig.writeConfig();
  }
  
  protected int currentDownloadProgress()
  {
    long l1;
    if (this.mDownloadFile.exists()) {
      l1 = this.mDownloadFile.length();
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
  
  protected void setExtraParams(TFMDownloadListener paramTFMDownloadListener, Bundle paramBundle)
  {
    this.mDownloadListener = paramTFMDownloadListener;
    this.mParams = paramBundle;
  }
  
  /* Error */
  public void startDownload()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 70	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadUrl	Ljava/lang/String;
    //   4: ifnonnull +11 -> 15
    //   7: aload_0
    //   8: getfield 165	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mLocation	Ljava/lang/String;
    //   11: ifnonnull +4 -> 15
    //   14: return
    //   15: aload_0
    //   16: invokespecial 85	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:resetArgs	()V
    //   19: aload_0
    //   20: iconst_1
    //   21: putfield 105	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mIsDownloading	Z
    //   24: sipush 10000
    //   27: istore_2
    //   28: aload_0
    //   29: getfield 97	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadListener	Lcom/tencent/tbs/core/partner/tencentfiledownload/TFMDownloadListener;
    //   32: astore 9
    //   34: iload_2
    //   35: istore_1
    //   36: aload 9
    //   38: ifnull +12 -> 50
    //   41: aload 9
    //   43: invokeinterface 212 1 0
    //   48: iload_2
    //   49: istore_1
    //   50: aload_0
    //   51: getfield 110	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   54: astore 9
    //   56: aconst_null
    //   57: astore 13
    //   59: aconst_null
    //   60: astore 11
    //   62: aconst_null
    //   63: astore 15
    //   65: aload 9
    //   67: ifnull +13 -> 80
    //   70: aload 9
    //   72: invokevirtual 115	java/net/HttpURLConnection:disconnect	()V
    //   75: aload_0
    //   76: aconst_null
    //   77: putfield 110	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   80: aload_0
    //   81: getfield 161	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mRetryTimes	I
    //   84: getstatic 214	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mMaxRetryTimes	I
    //   87: if_icmplt +6 -> 93
    //   90: goto +640 -> 730
    //   93: aload_0
    //   94: getfield 163	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mRetryTimes302	I
    //   97: getstatic 216	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mMaxRetryTimes302	I
    //   100: if_icmple +6 -> 106
    //   103: goto +627 -> 730
    //   106: aload_0
    //   107: getfield 165	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mLocation	Ljava/lang/String;
    //   110: ifnull +12 -> 122
    //   113: aload_0
    //   114: getfield 165	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mLocation	Ljava/lang/String;
    //   117: astore 9
    //   119: goto +9 -> 128
    //   122: aload_0
    //   123: getfield 70	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadUrl	Ljava/lang/String;
    //   126: astore 9
    //   128: aload_0
    //   129: aload 9
    //   131: invokespecial 218	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:initHttpRequest	(Ljava/lang/String;)V
    //   134: new 220	java/lang/StringBuilder
    //   137: dup
    //   138: invokespecial 221	java/lang/StringBuilder:<init>	()V
    //   141: astore 10
    //   143: aload 10
    //   145: ldc -33
    //   147: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   150: pop
    //   151: aload 10
    //   153: aload 9
    //   155: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: pop
    //   159: ldc -27
    //   161: aload 10
    //   163: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   166: invokestatic 239	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   169: pop
    //   170: aload_0
    //   171: getfield 110	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   174: invokevirtual 242	java/net/HttpURLConnection:getResponseCode	()I
    //   177: istore_2
    //   178: iload_2
    //   179: sipush 200
    //   182: if_icmpeq +75 -> 257
    //   185: iload_2
    //   186: sipush 206
    //   189: if_icmpne +6 -> 195
    //   192: goto +65 -> 257
    //   195: iload_2
    //   196: sipush 300
    //   199: if_icmplt +48 -> 247
    //   202: iload_2
    //   203: sipush 307
    //   206: if_icmpgt +41 -> 247
    //   209: aload_0
    //   210: getfield 110	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   213: ldc -12
    //   215: invokevirtual 248	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
    //   218: astore 9
    //   220: aload 9
    //   222: invokestatic 254	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   225: ifne +895 -> 1120
    //   228: aload_0
    //   229: aload 9
    //   231: putfield 165	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mLocation	Ljava/lang/String;
    //   234: aload_0
    //   235: aload_0
    //   236: getfield 163	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mRetryTimes302	I
    //   239: iconst_1
    //   240: iadd
    //   241: putfield 163	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mRetryTimes302	I
    //   244: goto -194 -> 50
    //   247: aload_0
    //   248: lconst_0
    //   249: invokespecial 256	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:retry	(J)V
    //   252: iload_2
    //   253: istore_1
    //   254: goto -204 -> 50
    //   257: aload_0
    //   258: aload_0
    //   259: getfield 110	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   262: invokevirtual 259	java/net/HttpURLConnection:getContentLength	()I
    //   265: i2l
    //   266: putfield 62	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mContentLength	J
    //   269: aload_0
    //   270: aload_0
    //   271: getfield 62	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mContentLength	J
    //   274: invokespecial 261	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:writeFileLength	(J)V
    //   277: aload_0
    //   278: getfield 77	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadFile	Ljava/io/File;
    //   281: invokevirtual 196	java/io/File:exists	()Z
    //   284: ifeq +52 -> 336
    //   287: aload_0
    //   288: getfield 62	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mContentLength	J
    //   291: aload_0
    //   292: getfield 77	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadFile	Ljava/io/File;
    //   295: invokevirtual 199	java/io/File:length	()J
    //   298: lcmp
    //   299: ifne +29 -> 328
    //   302: aload_0
    //   303: getfield 97	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadListener	Lcom/tencent/tbs/core/partner/tencentfiledownload/TFMDownloadListener;
    //   306: ifnull +14 -> 320
    //   309: aload_0
    //   310: getfield 97	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadListener	Lcom/tencent/tbs/core/partner/tencentfiledownload/TFMDownloadListener;
    //   313: bipush 100
    //   315: invokeinterface 264 2 0
    //   320: aload_0
    //   321: iconst_1
    //   322: putfield 169	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mFinished	Z
    //   325: goto +405 -> 730
    //   328: aload_0
    //   329: getfield 77	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadFile	Ljava/io/File;
    //   332: invokevirtual 267	java/io/File:delete	()Z
    //   335: pop
    //   336: aload_0
    //   337: getfield 110	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   340: invokevirtual 271	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   343: astore 10
    //   345: aload 10
    //   347: ifnull +354 -> 701
    //   350: aload_0
    //   351: getfield 110	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mHttpRequest	Ljava/net/HttpURLConnection;
    //   354: invokevirtual 274	java/net/HttpURLConnection:getContentEncoding	()Ljava/lang/String;
    //   357: astore 9
    //   359: aload 9
    //   361: ifnull +28 -> 389
    //   364: aload 9
    //   366: ldc_w 276
    //   369: invokevirtual 281	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   372: ifeq +17 -> 389
    //   375: new 283	java/util/zip/GZIPInputStream
    //   378: dup
    //   379: aload 10
    //   381: invokespecial 286	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   384: astore 9
    //   386: goto +45 -> 431
    //   389: aload 9
    //   391: ifnull +36 -> 427
    //   394: aload 9
    //   396: ldc_w 288
    //   399: invokevirtual 281	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   402: ifeq +25 -> 427
    //   405: new 290	java/util/zip/InflaterInputStream
    //   408: dup
    //   409: aload 10
    //   411: new 292	java/util/zip/Inflater
    //   414: dup
    //   415: iconst_1
    //   416: invokespecial 294	java/util/zip/Inflater:<init>	(Z)V
    //   419: invokespecial 297	java/util/zip/InflaterInputStream:<init>	(Ljava/io/InputStream;Ljava/util/zip/Inflater;)V
    //   422: astore 9
    //   424: goto +7 -> 431
    //   427: aload 10
    //   429: astore 9
    //   431: aload 10
    //   433: astore 14
    //   435: aload 11
    //   437: astore 13
    //   439: aload 9
    //   441: astore 12
    //   443: sipush 8192
    //   446: newarray <illegal type>
    //   448: astore 16
    //   450: aload 10
    //   452: astore 14
    //   454: aload 11
    //   456: astore 13
    //   458: aload 9
    //   460: astore 12
    //   462: new 299	java/io/FileOutputStream
    //   465: dup
    //   466: aload_0
    //   467: getfield 77	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadFile	Ljava/io/File;
    //   470: iconst_1
    //   471: invokespecial 302	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
    //   474: astore 11
    //   476: lconst_0
    //   477: lstore 5
    //   479: lload 5
    //   481: lstore_3
    //   482: aload 16
    //   484: astore 12
    //   486: aload_0
    //   487: getfield 167	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mCanceled	Z
    //   490: ifeq +18 -> 508
    //   493: sipush 999
    //   496: istore_2
    //   497: aload 9
    //   499: astore 13
    //   501: aload 11
    //   503: astore 12
    //   505: goto +205 -> 710
    //   508: aload 9
    //   510: aload 12
    //   512: iconst_0
    //   513: sipush 8192
    //   516: invokevirtual 308	java/io/InputStream:read	([BII)I
    //   519: istore_2
    //   520: iload_2
    //   521: ifge +59 -> 580
    //   524: lload 5
    //   526: aload_0
    //   527: getfield 62	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mContentLength	J
    //   530: lcmp
    //   531: ifne +594 -> 1125
    //   534: aload_0
    //   535: iconst_1
    //   536: putfield 169	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mFinished	Z
    //   539: iload_1
    //   540: istore_2
    //   541: aload 9
    //   543: astore 13
    //   545: aload 11
    //   547: astore 12
    //   549: aload_0
    //   550: getfield 97	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadListener	Lcom/tencent/tbs/core/partner/tencentfiledownload/TFMDownloadListener;
    //   553: ifnull +157 -> 710
    //   556: aload_0
    //   557: getfield 97	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadListener	Lcom/tencent/tbs/core/partner/tencentfiledownload/TFMDownloadListener;
    //   560: bipush 100
    //   562: invokeinterface 264 2 0
    //   567: iload_1
    //   568: istore_2
    //   569: aload 9
    //   571: astore 13
    //   573: aload 11
    //   575: astore 12
    //   577: goto +133 -> 710
    //   580: aload 11
    //   582: aload 12
    //   584: iconst_0
    //   585: iload_2
    //   586: invokevirtual 312	java/io/FileOutputStream:write	([BII)V
    //   589: aload 11
    //   591: invokevirtual 315	java/io/FileOutputStream:flush	()V
    //   594: lload 5
    //   596: iload_2
    //   597: i2l
    //   598: ladd
    //   599: lstore 5
    //   601: invokestatic 320	java/lang/System:currentTimeMillis	()J
    //   604: lstore 7
    //   606: lload 7
    //   608: lload_3
    //   609: lsub
    //   610: ldc2_w 21
    //   613: lcmp
    //   614: ifle +37 -> 651
    //   617: aload_0
    //   618: getfield 97	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadListener	Lcom/tencent/tbs/core/partner/tencentfiledownload/TFMDownloadListener;
    //   621: ifnull +24 -> 645
    //   624: aload_0
    //   625: getfield 97	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadListener	Lcom/tencent/tbs/core/partner/tencentfiledownload/TFMDownloadListener;
    //   628: ldc2_w 200
    //   631: lload 5
    //   633: lmul
    //   634: aload_0
    //   635: getfield 62	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mContentLength	J
    //   638: ldiv
    //   639: l2i
    //   640: invokeinterface 264 2 0
    //   645: lload 7
    //   647: lstore_3
    //   648: goto -162 -> 486
    //   651: goto -165 -> 486
    //   654: astore 12
    //   656: aload 11
    //   658: astore 13
    //   660: aload 12
    //   662: astore 11
    //   664: goto +416 -> 1080
    //   667: astore 12
    //   669: aload 11
    //   671: astore 15
    //   673: aload 12
    //   675: astore 11
    //   677: goto +242 -> 919
    //   680: astore 11
    //   682: goto +237 -> 919
    //   685: astore 11
    //   687: aconst_null
    //   688: astore 9
    //   690: goto +390 -> 1080
    //   693: astore 11
    //   695: aconst_null
    //   696: astore 9
    //   698: goto +221 -> 919
    //   701: aconst_null
    //   702: astore 13
    //   704: aload 13
    //   706: astore 12
    //   708: iload_1
    //   709: istore_2
    //   710: aload_0
    //   711: aload 12
    //   713: invokespecial 322	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:closeStream	(Ljava/io/Closeable;)V
    //   716: aload_0
    //   717: aload 13
    //   719: invokespecial 322	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:closeStream	(Ljava/io/Closeable;)V
    //   722: aload_0
    //   723: aload 10
    //   725: invokespecial 322	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:closeStream	(Ljava/io/Closeable;)V
    //   728: iload_2
    //   729: istore_1
    //   730: aload_0
    //   731: getfield 169	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mFinished	Z
    //   734: ifeq +75 -> 809
    //   737: aload_0
    //   738: getfield 82	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadConfig	Lcom/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig;
    //   741: astore 10
    //   743: aload_0
    //   744: getfield 165	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mLocation	Ljava/lang/String;
    //   747: astore 9
    //   749: aload 9
    //   751: ifnull +6 -> 757
    //   754: goto +9 -> 763
    //   757: aload_0
    //   758: getfield 70	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadUrl	Ljava/lang/String;
    //   761: astore 9
    //   763: aload 10
    //   765: aload 9
    //   767: invokevirtual 325	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:setDownloadUrl	(Ljava/lang/String;)V
    //   770: aload_0
    //   771: getfield 82	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadConfig	Lcom/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig;
    //   774: invokevirtual 190	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:writeConfig	()V
    //   777: aload_0
    //   778: getfield 97	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadListener	Lcom/tencent/tbs/core/partner/tencentfiledownload/TFMDownloadListener;
    //   781: astore 9
    //   783: aload 9
    //   785: ifnull +103 -> 888
    //   788: aload 9
    //   790: aload_0
    //   791: getfield 77	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadFile	Ljava/io/File;
    //   794: invokevirtual 328	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   797: aload_0
    //   798: getfield 101	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mParams	Landroid/os/Bundle;
    //   801: invokeinterface 332 3 0
    //   806: goto +82 -> 888
    //   809: aload_0
    //   810: getfield 101	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mParams	Landroid/os/Bundle;
    //   813: getstatic 334	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mErrorType	Ljava/lang/String;
    //   816: invokevirtual 339	android/os/Bundle:remove	(Ljava/lang/String;)V
    //   819: aload_0
    //   820: getfield 101	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mParams	Landroid/os/Bundle;
    //   823: getstatic 334	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mErrorType	Ljava/lang/String;
    //   826: iload_1
    //   827: invokevirtual 343	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   830: aload_0
    //   831: getfield 97	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mDownloadListener	Lcom/tencent/tbs/core/partner/tencentfiledownload/TFMDownloadListener;
    //   834: astore 9
    //   836: aload 9
    //   838: ifnull +14 -> 852
    //   841: aload 9
    //   843: aload_0
    //   844: getfield 101	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mParams	Landroid/os/Bundle;
    //   847: invokeinterface 347 2 0
    //   852: new 220	java/lang/StringBuilder
    //   855: dup
    //   856: invokespecial 221	java/lang/StringBuilder:<init>	()V
    //   859: astore 9
    //   861: aload 9
    //   863: ldc_w 349
    //   866: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   869: pop
    //   870: aload 9
    //   872: iload_1
    //   873: invokevirtual 352	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   876: pop
    //   877: ldc -27
    //   879: aload 9
    //   881: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   884: invokestatic 239	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   887: pop
    //   888: aload_0
    //   889: iconst_0
    //   890: putfield 105	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mIsDownloading	Z
    //   893: aload_0
    //   894: invokespecial 354	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:closeHttpRequest	()V
    //   897: return
    //   898: astore 11
    //   900: aconst_null
    //   901: astore 10
    //   903: aload 10
    //   905: astore 9
    //   907: goto +173 -> 1080
    //   910: astore 11
    //   912: aconst_null
    //   913: astore 10
    //   915: aload 10
    //   917: astore 9
    //   919: aload 10
    //   921: astore 14
    //   923: aload 15
    //   925: astore 13
    //   927: aload 9
    //   929: astore 12
    //   931: aload 11
    //   933: invokevirtual 357	java/io/IOException:printStackTrace	()V
    //   936: aload 10
    //   938: astore 14
    //   940: aload 15
    //   942: astore 13
    //   944: aload 9
    //   946: astore 12
    //   948: aload 11
    //   950: instanceof 359
    //   953: ifne +68 -> 1021
    //   956: aload 10
    //   958: astore 14
    //   960: aload 15
    //   962: astore 13
    //   964: aload 9
    //   966: astore 12
    //   968: aload 11
    //   970: instanceof 361
    //   973: ifeq +6 -> 979
    //   976: goto +45 -> 1021
    //   979: sipush 222
    //   982: istore_1
    //   983: aload 10
    //   985: astore 14
    //   987: aload 15
    //   989: astore 13
    //   991: aload 9
    //   993: astore 12
    //   995: aload_0
    //   996: lconst_0
    //   997: invokespecial 256	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:retry	(J)V
    //   1000: aload_0
    //   1001: aload 15
    //   1003: invokespecial 322	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:closeStream	(Ljava/io/Closeable;)V
    //   1006: aload_0
    //   1007: aload 9
    //   1009: invokespecial 322	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:closeStream	(Ljava/io/Closeable;)V
    //   1012: aload_0
    //   1013: aload 10
    //   1015: invokespecial 322	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:closeStream	(Ljava/io/Closeable;)V
    //   1018: goto -968 -> 50
    //   1021: aload 10
    //   1023: astore 14
    //   1025: aload 15
    //   1027: astore 13
    //   1029: aload 9
    //   1031: astore 12
    //   1033: aload_0
    //   1034: ldc_w 362
    //   1037: putfield 65	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mReadTimeout	I
    //   1040: aload 10
    //   1042: astore 14
    //   1044: aload 15
    //   1046: astore 13
    //   1048: aload 9
    //   1050: astore 12
    //   1052: aload_0
    //   1053: aload_0
    //   1054: getfield 161	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mRetryTimes	I
    //   1057: iconst_1
    //   1058: iadd
    //   1059: putfield 161	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:mRetryTimes	I
    //   1062: bipush 111
    //   1064: istore_1
    //   1065: aload_0
    //   1066: aload 15
    //   1068: invokespecial 322	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:closeStream	(Ljava/io/Closeable;)V
    //   1071: aload_0
    //   1072: aload 9
    //   1074: invokespecial 322	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:closeStream	(Ljava/io/Closeable;)V
    //   1077: goto -65 -> 1012
    //   1080: aload_0
    //   1081: aload 13
    //   1083: invokespecial 322	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:closeStream	(Ljava/io/Closeable;)V
    //   1086: aload_0
    //   1087: aload 9
    //   1089: invokespecial 322	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:closeStream	(Ljava/io/Closeable;)V
    //   1092: aload_0
    //   1093: aload 10
    //   1095: invokespecial 322	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:closeStream	(Ljava/io/Closeable;)V
    //   1098: aload 11
    //   1100: athrow
    //   1101: astore 9
    //   1103: aload 9
    //   1105: invokevirtual 363	java/lang/Throwable:printStackTrace	()V
    //   1108: aload_0
    //   1109: lconst_0
    //   1110: invokespecial 256	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader:retry	(J)V
    //   1113: sipush 777
    //   1116: istore_1
    //   1117: goto -1067 -> 50
    //   1120: iload_2
    //   1121: istore_1
    //   1122: goto -392 -> 730
    //   1125: bipush 101
    //   1127: istore_2
    //   1128: aload 9
    //   1130: astore 13
    //   1132: aload 11
    //   1134: astore 12
    //   1136: goto -426 -> 710
    //   1139: astore 11
    //   1141: aload 14
    //   1143: astore 10
    //   1145: aload 12
    //   1147: astore 9
    //   1149: goto -69 -> 1080
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1152	0	this	TFMDownloader
    //   35	1087	1	i	int
    //   27	1101	2	j	int
    //   481	167	3	l1	long
    //   477	155	5	l2	long
    //   604	42	7	l3	long
    //   32	1056	9	localObject1	Object
    //   1101	28	9	localThrowable	Throwable
    //   1147	1	9	localObject2	Object
    //   141	1003	10	localObject3	Object
    //   60	616	11	localObject4	Object
    //   680	1	11	localIOException1	IOException
    //   685	1	11	localObject5	Object
    //   693	1	11	localIOException2	IOException
    //   898	1	11	localObject6	Object
    //   910	223	11	localIOException3	IOException
    //   1139	1	11	localObject7	Object
    //   441	142	12	localObject8	Object
    //   654	7	12	localObject9	Object
    //   667	7	12	localIOException4	IOException
    //   706	440	12	localObject10	Object
    //   57	1074	13	localObject11	Object
    //   433	709	14	localObject12	Object
    //   63	1004	15	localObject13	Object
    //   448	35	16	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   486	493	654	finally
    //   508	520	654	finally
    //   524	539	654	finally
    //   549	567	654	finally
    //   580	594	654	finally
    //   601	606	654	finally
    //   617	645	654	finally
    //   486	493	667	java/io/IOException
    //   508	520	667	java/io/IOException
    //   524	539	667	java/io/IOException
    //   549	567	667	java/io/IOException
    //   580	594	667	java/io/IOException
    //   601	606	667	java/io/IOException
    //   617	645	667	java/io/IOException
    //   443	450	680	java/io/IOException
    //   462	476	680	java/io/IOException
    //   350	359	685	finally
    //   364	386	685	finally
    //   394	424	685	finally
    //   350	359	693	java/io/IOException
    //   364	386	693	java/io/IOException
    //   394	424	693	java/io/IOException
    //   336	345	898	finally
    //   336	345	910	java/io/IOException
    //   106	119	1101	java/lang/Throwable
    //   122	128	1101	java/lang/Throwable
    //   128	178	1101	java/lang/Throwable
    //   209	244	1101	java/lang/Throwable
    //   247	252	1101	java/lang/Throwable
    //   257	320	1101	java/lang/Throwable
    //   320	325	1101	java/lang/Throwable
    //   328	336	1101	java/lang/Throwable
    //   710	728	1101	java/lang/Throwable
    //   1000	1012	1101	java/lang/Throwable
    //   1012	1018	1101	java/lang/Throwable
    //   1065	1077	1101	java/lang/Throwable
    //   1080	1101	1101	java/lang/Throwable
    //   443	450	1139	finally
    //   462	476	1139	finally
    //   931	936	1139	finally
    //   948	956	1139	finally
    //   968	976	1139	finally
    //   995	1000	1139	finally
    //   1033	1040	1139	finally
    //   1052	1062	1139	finally
  }
  
  public void startVirtualDownload()
  {
    final Object localObject = this.mDownloadListener;
    if (localObject != null) {
      ((TFMDownloadListener)localObject).onDownloadStart();
    }
    final long l1;
    if (this.mDownloadFile.exists()) {
      l1 = this.mDownloadFile.length();
    } else {
      l1 = 0L;
    }
    long l2 = this.mContentLength;
    long l3 = l2 - l1;
    if ((l2 > 0L) && (l3 == 0L))
    {
      localObject = this.mDownloadListener;
      if (localObject != null) {
        ((TFMDownloadListener)localObject).onDownloadSucess(this.mDownloadFile.getAbsolutePath(), this.mParams);
      }
      return;
    }
    if (l3 < 0L)
    {
      this.mParams.remove(mErrorType);
      this.mParams.putInt(mErrorType, 9);
      localObject = this.mDownloadListener;
      if (localObject != null) {
        ((TFMDownloadListener)localObject).onDownloadFailed(this.mParams);
      }
      return;
    }
    localObject = this.mDownloadListener;
    if (localObject != null) {
      ((TFMDownloadListener)localObject).onDownloadProgress(0);
    }
    localObject = new Timer();
    ((Timer)localObject).schedule(new TimerTask()
    {
      static final long TIME_OUT = 60000L;
      long virtualDownloadTimeBegin = System.currentTimeMillis();
      int virtualProgress = 0;
      
      public void run()
      {
        long l;
        if (TFMDownloader.this.mDownloadFile.exists()) {
          l = TFMDownloader.this.mDownloadFile.length();
        } else {
          l = 0L;
        }
        int i = (int)((l - l1) * 100L / localObject);
        if (TFMDownloader.this.mDownloadFile.length() == TFMDownloader.this.mContentLength)
        {
          if (TFMDownloader.this.mDownloadListener != null) {
            TFMDownloader.this.mDownloadListener.onDownloadSucess(TFMDownloader.this.mDownloadFile.getAbsolutePath(), TFMDownloader.this.mParams);
          }
          TFMDownloader.access$402(TFMDownloader.this, false);
          this.val$timer.cancel();
          return;
        }
        if (i > this.virtualProgress)
        {
          this.virtualProgress = i;
          this.virtualDownloadTimeBegin = System.currentTimeMillis();
          if (TFMDownloader.this.mDownloadListener != null) {
            TFMDownloader.this.mDownloadListener.onDownloadProgress(this.virtualProgress);
          }
        }
        else if (System.currentTimeMillis() - this.virtualDownloadTimeBegin >= 60000L)
        {
          TFMDownloader.this.mParams.remove(TFMDownloader.mErrorType);
          TFMDownloader.this.mParams.putInt(TFMDownloader.mErrorType, 10);
          if (TFMDownloader.this.mDownloadListener != null) {
            TFMDownloader.this.mDownloadListener.onDownloadFailed(TFMDownloader.this.mParams);
          }
          TFMDownloader.access$402(TFMDownloader.this, false);
          this.val$timer.cancel();
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
    public static final String DOWNLOAD_CONFIG_FILENAME = "tftqc.data";
    static final String KEY_DOWNLOAD_FILE_LENGTH = "download_file_length";
    static final String KEY_DOWNLOAD_URL = "download_url";
    static final String KEY_FILE_SIZE = "file_size";
    static final String KEY_LAST_CHECK = "last_check";
    static final String KEY_NEED_UPGRADE = "need_upgrade";
    static final String KEY_UPGRADE_URL = "upgrade_url";
    static final String OLD_DOWNLOAD_CONFIG_FILENAME = "tbs_tfdownload.conf";
    private File mDownloadConfigFile = null;
    long mDownloadFileLength = 0L;
    String mDownloadUrl = null;
    long mFileLength = 0L;
    long mLastCheck = 0L;
    boolean mNeedUpgrade = false;
    String mUpgradeUrl = null;
    
    protected DownloadConfig(File paramFile)
    {
      File localFile = new File(paramFile, "tbs_tfdownload.conf");
      this.mDownloadConfigFile = new File(paramFile, "tftqc.data");
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
      //   5: getfield 66	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mDownloadConfigFile	Ljava/io/File;
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
      //   67: putfield 54	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mDownloadUrl	Ljava/lang/String;
      //   70: aload_0
      //   71: aload_2
      //   72: ldc 26
      //   74: ldc -126
      //   76: invokevirtual 133	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   79: invokestatic 139	java/lang/Long:parseLong	(Ljava/lang/String;)J
      //   82: putfield 56	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mLastCheck	J
      //   85: aload_0
      //   86: aload_2
      //   87: ldc 32
      //   89: invokevirtual 128	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
      //   92: putfield 58	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mUpgradeUrl	Ljava/lang/String;
      //   95: aload_0
      //   96: aload_2
      //   97: ldc 29
      //   99: ldc -115
      //   101: invokevirtual 133	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   104: invokestatic 147	java/lang/Boolean:parseBoolean	(Ljava/lang/String;)Z
      //   107: putfield 60	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mNeedUpgrade	Z
      //   110: aload_0
      //   111: aload_2
      //   112: ldc 23
      //   114: ldc -126
      //   116: invokevirtual 133	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   119: invokestatic 139	java/lang/Long:parseLong	(Ljava/lang/String;)J
      //   122: putfield 62	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mFileLength	J
      //   125: aload_0
      //   126: aload_2
      //   127: ldc 17
      //   129: ldc -126
      //   131: invokevirtual 133	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   134: invokestatic 139	java/lang/Long:parseLong	(Ljava/lang/String;)J
      //   137: putfield 64	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mDownloadFileLength	J
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
    
    public long getFileLength()
    {
      return this.mFileLength;
    }
    
    public void setDownloadUrl(String paramString)
    {
      this.mDownloadUrl = paramString;
    }
    
    public void setFileLength(long paramLong)
    {
      this.mFileLength = paramLong;
    }
    
    /* Error */
    public void writeConfig()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokestatic 165	java/lang/System:currentTimeMillis	()J
      //   4: putfield 56	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mLastCheck	J
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
      //   25: getfield 54	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mDownloadUrl	Ljava/lang/String;
      //   28: ifnonnull +9 -> 37
      //   31: ldc -89
      //   33: astore_1
      //   34: goto +8 -> 42
      //   37: aload_0
      //   38: getfield 54	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mDownloadUrl	Ljava/lang/String;
      //   41: astore_1
      //   42: aload 5
      //   44: ldc 20
      //   46: aload_1
      //   47: invokevirtual 171	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   50: pop
      //   51: aload 5
      //   53: ldc 26
      //   55: aload_0
      //   56: getfield 56	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mLastCheck	J
      //   59: invokestatic 175	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   62: invokevirtual 171	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   65: pop
      //   66: aload_0
      //   67: getfield 58	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mUpgradeUrl	Ljava/lang/String;
      //   70: ifnonnull +9 -> 79
      //   73: ldc -89
      //   75: astore_1
      //   76: goto +8 -> 84
      //   79: aload_0
      //   80: getfield 58	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mUpgradeUrl	Ljava/lang/String;
      //   83: astore_1
      //   84: aload 5
      //   86: ldc 32
      //   88: aload_1
      //   89: invokevirtual 171	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   92: pop
      //   93: aload 5
      //   95: ldc 29
      //   97: aload_0
      //   98: getfield 60	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mNeedUpgrade	Z
      //   101: invokestatic 178	java/lang/String:valueOf	(Z)Ljava/lang/String;
      //   104: invokevirtual 171	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   107: pop
      //   108: aload 5
      //   110: ldc 23
      //   112: aload_0
      //   113: getfield 62	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mFileLength	J
      //   116: invokestatic 175	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   119: invokevirtual 171	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   122: pop
      //   123: aload 5
      //   125: ldc 17
      //   127: aload_0
      //   128: getfield 64	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mDownloadFileLength	J
      //   131: invokestatic 175	java/lang/String:valueOf	(J)Ljava/lang/String;
      //   134: invokevirtual 171	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   137: pop
      //   138: new 180	java/io/FileOutputStream
      //   141: dup
      //   142: aload_0
      //   143: getfield 66	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloader$DownloadConfig:mDownloadConfigFile	Ljava/io/File;
      //   146: invokespecial 181	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
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
      //   192: new 183	javax/crypto/CipherOutputStream
      //   195: dup
      //   196: aload_1
      //   197: aload 7
      //   199: invokespecial 186	javax/crypto/CipherOutputStream:<init>	(Ljava/io/OutputStream;Ljavax/crypto/Cipher;)V
      //   202: astore 4
      //   204: aload 5
      //   206: aload 4
      //   208: aconst_null
      //   209: invokevirtual 190	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
      //   212: aload 4
      //   214: invokevirtual 191	javax/crypto/CipherOutputStream:close	()V
      //   217: aload_1
      //   218: invokevirtual 192	java/io/FileOutputStream:close	()V
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
      //   264: invokevirtual 195	java/lang/Exception:printStackTrace	()V
      //   267: aload 4
      //   269: ifnull +11 -> 280
      //   272: aload 4
      //   274: invokevirtual 191	javax/crypto/CipherOutputStream:close	()V
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
      //   300: invokevirtual 191	javax/crypto/CipherOutputStream:close	()V
      //   303: goto +3 -> 306
      //   306: aload_1
      //   307: ifnull +7 -> 314
      //   310: aload_1
      //   311: invokevirtual 192	java/io/FileOutputStream:close	()V
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\tencentfiledownload\TFMDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */