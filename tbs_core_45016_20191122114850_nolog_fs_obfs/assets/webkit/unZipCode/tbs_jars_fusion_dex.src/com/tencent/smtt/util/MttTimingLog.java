package com.tencent.smtt.util;

import android.os.SystemClock;
import android.util.Log;
import com.tencent.smtt.os.FileUtils;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class MttTimingLog
{
  private static final int LOG_MESSAGES_MAX_COUNT = 48;
  private static final int LOG_MESSAGES_MIN_COUNT = 8;
  private static int MAXRECORDUANUM = 0;
  public static final String PERFORMANCE_LOG_SERVER_HOST = "qprostat.imtt.qq.com";
  public static final String PERFORMANCE_LOG_SERVER_URL = "https://qprostat.imtt.qq.com";
  private static final String ROOT;
  private static File liveLogZip;
  private static final int livelogZipFileContainNumber = 20;
  private static final List<String> livelogZipFileList;
  public static long logSize = 0L;
  private static long mBeginTime = 0L;
  private static boolean mEnabled = false;
  private static final boolean mIsDebug = false;
  private static Vector<String> mLogMessages = new Vector();
  private static int mLogMessagesCount = 0;
  private static String[] mOutputStr;
  private static final boolean mPerfDebugEnabled = false;
  private static boolean mPerfEnabled = false;
  private static boolean mPerfLogToFile = false;
  private static int recordNum = 0;
  public static int responseCode = 0;
  private static String sBaseDir;
  private static final String sDumpPackedZip;
  private static final String sDumpQQPackedZip;
  private static final String sDumpWXPackedZip;
  private static final int sFileNum = 5;
  private static final String sLiveLogPackedZipPath;
  private static final String sLogCatPackedZip;
  private static a sOrphanLog;
  private static final String sOrphanLogLock = "orphanLogLock";
  private static final String sOrphanLogName = "timing";
  private static final String sPackedZip;
  private static b sPerformanceRecorder;
  private static final String sRenderInfoPackedZip;
  private static d sUserActionsRecorder;
  public static boolean uploaded;
  static Object writeUserActionTableLock;
  
  static
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(MttLog.getSDCardOrPackageDir());
    localStringBuilder.append("/QQBrowser/");
    ROOT = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(MttLog.getSDCardOrPackageDir());
    localStringBuilder.append("/QQBrowser/timing-logs.zip");
    sPackedZip = localStringBuilder.toString();
    sBaseDir = null;
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(MttLog.getSDCardOrPackageDir());
    localStringBuilder.append("/QQBrowser/logCat.zip");
    sLogCatPackedZip = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(MttLog.getSDCardOrPackageDir());
    localStringBuilder.append("/QQBrowser/dump.zip");
    sDumpPackedZip = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(MttLog.getSDCardOrPackageDir());
    localStringBuilder.append("/QQBrowser/renderInfo.zip");
    sRenderInfoPackedZip = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(MttLog.getSDCardOrPackageDir());
    localStringBuilder.append("/QQBrowser/dump_tbs_wx.zip");
    sDumpWXPackedZip = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(MttLog.getSDCardOrPackageDir());
    localStringBuilder.append("/QQBrowser/dump_tbs_qq.zip");
    sDumpQQPackedZip = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(MttLog.getSDCardOrPackageDir());
    localStringBuilder.append("/QQBrowser/");
    sLiveLogPackedZipPath = localStringBuilder.toString();
    livelogZipFileList = new ArrayList();
    liveLogZip = null;
    sOrphanLog = null;
    sPerformanceRecorder = new b(null);
    sUserActionsRecorder = new d(null);
    recordNum = 0;
    MAXRECORDUANUM = 30;
    mOutputStr = new String[MAXRECORDUANUM];
    writeUserActionTableLock = new Object();
    uploaded = false;
    logSize = 0L;
    responseCode = -1;
    mBeginTime = 0L;
    mLogMessagesCount = -1;
  }
  
  public static void beginPerfLog()
  {
    if (true == enablePerfLog())
    {
      Log.e("performance", "beginPerfLog()");
      mBeginTime = SystemClock.uptimeMillis();
      synchronized (mLogMessages)
      {
        mLogMessagesCount = 0;
        mLogMessages.clear();
        return;
      }
    }
  }
  
  public static void debugPerfLog(String paramString) {}
  
  public static void delAllFiles(String paramString)
  {
    Object localObject;
    String[] arrayOfString;
    int i;
    if (paramString != null)
    {
      if (paramString.length() <= 0) {
        return;
      }
      localObject = new File(paramString);
      if (!((File)localObject).exists()) {
        return;
      }
      if (!((File)localObject).isDirectory()) {
        return;
      }
      arrayOfString = ((File)localObject).list();
      if (arrayOfString == null) {
        return;
      }
      i = 0;
    }
    for (;;)
    {
      if (i >= arrayOfString.length) {
        break label180;
      }
      if (paramString.endsWith(File.separator))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(arrayOfString[i]);
        localObject = new File(((StringBuilder)localObject).toString());
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(File.separator);
        ((StringBuilder)localObject).append(arrayOfString[i]);
        localObject = new File(((StringBuilder)localObject).toString());
      }
      try
      {
        if (((File)localObject).isFile()) {
          ((File)localObject).delete();
        }
        i += 1;
      }
      catch (Exception paramString)
      {
        label180:
        for (;;) {}
      }
    }
    SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_timinglog_delete_file_error", "string"), 0);
    return;
    return;
  }
  
  public static boolean enableLog()
  {
    return mEnabled;
  }
  
  public static boolean enablePerfLog()
  {
    return mPerfEnabled;
  }
  
  public static void endLog()
  {
    try
    {
      if (sOrphanLog != null) {
        sOrphanLog.b();
      }
      return;
    }
    finally {}
  }
  
  public static void endPerfLog()
  {
    synchronized (mLogMessages)
    {
      if ((enablePerfLog()) && (mLogMessagesCount >= 0))
      {
        flushPerfLog();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("endPerfLog(), count = ");
        localStringBuilder.append(mLogMessagesCount);
        Log.e("performance", localStringBuilder.toString());
        mLogMessages.clear();
        mLogMessagesCount = -1;
        return;
      }
      return;
    }
  }
  
  private static String fileNameForPage(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(sBaseDir);
    localStringBuilder.append("/");
    localStringBuilder.append(paramString);
    localStringBuilder.append(".log");
    return localStringBuilder.toString();
  }
  
  public static void flushPerfLog()
  {
    if (8 > mLogMessagesCount) {
      return;
    }
    try
    {
      Object localObject = new File("/sdcard/perf");
      if ((!((File)localObject).exists()) && (!((File)localObject).mkdirs())) {
        return;
      }
      localObject = new BufferedOutputStream(new FileOutputStream("/sdcard/perf/performance.log"), 8096);
      int i = 0;
      try
      {
        while (i < mLogMessagesCount)
        {
          ((BufferedOutputStream)localObject).write(((String)mLogMessages.get(i)).getBytes());
          i += 1;
        }
        ((BufferedOutputStream)localObject).flush();
      }
      catch (IOException localIOException2)
      {
        localIOException2.printStackTrace();
        Log.e("performance", localIOException2.getMessage());
      }
      ((BufferedOutputStream)localObject).close();
      return;
    }
    catch (IOException localIOException1)
    {
      localIOException1.printStackTrace();
      return;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      Log.e("performance", localFileNotFoundException.getMessage());
    }
  }
  
  public static String getPerformanceSummery()
  {
    return sPerformanceRecorder.toString();
  }
  
  public static String getUserActionsSummary()
  {
    return sUserActionsRecorder.a();
  }
  
  public static void log(String paramString)
  {
    if (MttLog.isEnableLog()) {
      sPerformanceRecorder.a(paramString);
    }
    try
    {
      if (sOrphanLog == null) {
        return;
      }
      if (!paramString.startsWith("[")) {
        return;
      }
      processIfNeeded(paramString);
      Date localDate = new Date();
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[");
      localStringBuilder.append(localSimpleDateFormat.format(localDate));
      localStringBuilder.append("]");
      localStringBuilder.append(paramString);
      localStringBuilder.append("\n");
      paramString = localStringBuilder.toString();
      sOrphanLog.a(paramString);
      return;
    }
    finally {}
  }
  
  public static native String nativeGetCrashExtraMessage();
  
  private static native void nativeJNIEnableLog(boolean paramBoolean);
  
  /* Error */
  public static void packAndUpload()
  {
    // Byte code:
    //   0: ldc 72
    //   2: monitorenter
    //   3: getstatic 149	com/tencent/smtt/util/MttTimingLog:sOrphanLog	Lcom/tencent/smtt/util/MttTimingLog$a;
    //   6: ifnonnull +7 -> 13
    //   9: ldc 72
    //   11: monitorexit
    //   12: return
    //   13: getstatic 149	com/tencent/smtt/util/MttTimingLog:sOrphanLog	Lcom/tencent/smtt/util/MttTimingLog$a;
    //   16: ifnull +13 -> 29
    //   19: getstatic 149	com/tencent/smtt/util/MttTimingLog:sOrphanLog	Lcom/tencent/smtt/util/MttTimingLog$a;
    //   22: invokevirtual 383	com/tencent/smtt/util/MttTimingLog$a:a	()V
    //   25: aconst_null
    //   26: putstatic 149	com/tencent/smtt/util/MttTimingLog:sOrphanLog	Lcom/tencent/smtt/util/MttTimingLog$a;
    //   29: new 231	java/io/File
    //   32: dup
    //   33: getstatic 118	com/tencent/smtt/util/MttTimingLog:sBaseDir	Ljava/lang/String;
    //   36: invokespecial 233	java/io/File:<init>	(Ljava/lang/String;)V
    //   39: astore_2
    //   40: aload_2
    //   41: invokevirtual 236	java/io/File:exists	()Z
    //   44: ifeq +229 -> 273
    //   47: aload_2
    //   48: invokevirtual 242	java/io/File:list	()[Ljava/lang/String;
    //   51: arraylength
    //   52: ifne +6 -> 58
    //   55: goto +218 -> 273
    //   58: aload_2
    //   59: invokevirtual 242	java/io/File:list	()[Ljava/lang/String;
    //   62: astore_2
    //   63: iconst_0
    //   64: istore_1
    //   65: iconst_0
    //   66: istore_0
    //   67: iload_0
    //   68: aload_2
    //   69: arraylength
    //   70: if_icmpge +49 -> 119
    //   73: new 88	java/lang/StringBuilder
    //   76: dup
    //   77: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   80: astore_3
    //   81: aload_3
    //   82: getstatic 118	com/tencent/smtt/util/MttTimingLog:sBaseDir	Ljava/lang/String;
    //   85: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: pop
    //   89: aload_3
    //   90: ldc_w 294
    //   93: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: pop
    //   97: aload_3
    //   98: aload_2
    //   99: iload_0
    //   100: aaload
    //   101: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   104: pop
    //   105: aload_2
    //   106: iload_0
    //   107: aload_3
    //   108: invokevirtual 110	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   111: aastore
    //   112: iload_0
    //   113: iconst_1
    //   114: iadd
    //   115: istore_0
    //   116: goto -49 -> 67
    //   119: new 14	com/tencent/smtt/util/MttTimingLog$c
    //   122: dup
    //   123: aload_2
    //   124: getstatic 116	com/tencent/smtt/util/MttTimingLog:sPackedZip	Ljava/lang/String;
    //   127: invokespecial 386	com/tencent/smtt/util/MttTimingLog$c:<init>	([Ljava/lang/String;Ljava/lang/String;)V
    //   130: invokevirtual 387	com/tencent/smtt/util/MttTimingLog$c:a	()V
    //   133: new 231	java/io/File
    //   136: dup
    //   137: getstatic 116	com/tencent/smtt/util/MttTimingLog:sPackedZip	Ljava/lang/String;
    //   140: invokespecial 233	java/io/File:<init>	(Ljava/lang/String;)V
    //   143: pop
    //   144: ldc_w 389
    //   147: ldc_w 265
    //   150: invokestatic 271	com/tencent/smtt/webkit/SmttResource:getString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   153: invokestatic 392	com/tencent/smtt/util/MttTimingLog:showTip	(Ljava/lang/String;)V
    //   156: getstatic 116	com/tencent/smtt/util/MttTimingLog:sPackedZip	Ljava/lang/String;
    //   159: invokestatic 395	com/tencent/smtt/util/MttTimingLog:post	(Ljava/lang/String;)Z
    //   162: ifeq +95 -> 257
    //   165: ldc_w 397
    //   168: ldc_w 265
    //   171: invokestatic 271	com/tencent/smtt/webkit/SmttResource:getString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   174: invokestatic 392	com/tencent/smtt/util/MttTimingLog:showTip	(Ljava/lang/String;)V
    //   177: new 231	java/io/File
    //   180: dup
    //   181: getstatic 118	com/tencent/smtt/util/MttTimingLog:sBaseDir	Ljava/lang/String;
    //   184: invokespecial 233	java/io/File:<init>	(Ljava/lang/String;)V
    //   187: astore_2
    //   188: aload_2
    //   189: invokevirtual 236	java/io/File:exists	()Z
    //   192: ifeq +50 -> 242
    //   195: aload_2
    //   196: invokevirtual 239	java/io/File:isDirectory	()Z
    //   199: ifeq +43 -> 242
    //   202: aload_2
    //   203: invokevirtual 401	java/io/File:listFiles	()[Ljava/io/File;
    //   206: astore_2
    //   207: aload_2
    //   208: ifnull +34 -> 242
    //   211: iload_1
    //   212: istore_0
    //   213: iload_0
    //   214: aload_2
    //   215: arraylength
    //   216: if_icmpge +26 -> 242
    //   219: aload_2
    //   220: iload_0
    //   221: aaload
    //   222: invokevirtual 252	java/io/File:isFile	()Z
    //   225: ifeq +10 -> 235
    //   228: aload_2
    //   229: iload_0
    //   230: aaload
    //   231: invokevirtual 255	java/io/File:delete	()Z
    //   234: pop
    //   235: iload_0
    //   236: iconst_1
    //   237: iadd
    //   238: istore_0
    //   239: goto -26 -> 213
    //   242: new 8	com/tencent/smtt/util/MttTimingLog$a
    //   245: dup
    //   246: ldc 75
    //   248: invokespecial 402	com/tencent/smtt/util/MttTimingLog$a:<init>	(Ljava/lang/String;)V
    //   251: putstatic 149	com/tencent/smtt/util/MttTimingLog:sOrphanLog	Lcom/tencent/smtt/util/MttTimingLog$a;
    //   254: goto +15 -> 269
    //   257: ldc_w 404
    //   260: ldc_w 265
    //   263: invokestatic 271	com/tencent/smtt/webkit/SmttResource:getString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   266: invokestatic 392	com/tencent/smtt/util/MttTimingLog:showTip	(Ljava/lang/String;)V
    //   269: ldc 72
    //   271: monitorexit
    //   272: return
    //   273: new 8	com/tencent/smtt/util/MttTimingLog$a
    //   276: dup
    //   277: ldc 75
    //   279: invokespecial 402	com/tencent/smtt/util/MttTimingLog$a:<init>	(Ljava/lang/String;)V
    //   282: putstatic 149	com/tencent/smtt/util/MttTimingLog:sOrphanLog	Lcom/tencent/smtt/util/MttTimingLog$a;
    //   285: ldc_w 406
    //   288: ldc_w 265
    //   291: invokestatic 271	com/tencent/smtt/webkit/SmttResource:getString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   294: invokestatic 392	com/tencent/smtt/util/MttTimingLog:showTip	(Ljava/lang/String;)V
    //   297: ldc 72
    //   299: monitorexit
    //   300: return
    //   301: astore_2
    //   302: ldc 72
    //   304: monitorexit
    //   305: aload_2
    //   306: athrow
    //   307: astore_2
    //   308: goto -39 -> 269
    //   311: astore_2
    //   312: goto -27 -> 285
    // Local variable table:
    //   start	length	slot	name	signature
    //   66	173	0	i	int
    //   64	148	1	j	int
    //   39	190	2	localObject1	Object
    //   301	5	2	localObject2	Object
    //   307	1	2	localIOException1	IOException
    //   311	1	2	localIOException2	IOException
    //   80	28	3	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   3	12	301	finally
    //   13	29	301	finally
    //   29	55	301	finally
    //   58	63	301	finally
    //   67	112	301	finally
    //   119	207	301	finally
    //   213	235	301	finally
    //   242	254	301	finally
    //   257	269	301	finally
    //   269	272	301	finally
    //   273	285	301	finally
    //   285	300	301	finally
    //   302	305	301	finally
    //   242	254	307	java/io/IOException
    //   273	285	311	java/io/IOException
  }
  
  public static void packAndUpload(String paramString)
  {
    if (MttLog.logDevice == 2)
    {
      showTip(SmttResource.getString("x5_timinglog_shutdown_switch", "string"));
      return;
    }
    MttLog.flushLogs();
    Object localObject = new File(paramString);
    if (!((File)localObject).exists())
    {
      showTip(SmttResource.getString("x5_timinglog_no_log", "string"));
      return;
    }
    String[] arrayOfString = ((File)localObject).list();
    if (arrayOfString != null)
    {
      if (arrayOfString.length == 0)
      {
        showTip(SmttResource.getString("x5_timinglog_no_log", "string"));
        return;
      }
      g.a().equals("");
      int i = 0;
      while (i < arrayOfString.length)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(localObject);
        localStringBuilder.append("/");
        localStringBuilder.append(arrayOfString[i]);
        arrayOfString[i] = localStringBuilder.toString();
        i += 1;
      }
      localObject = sLogCatPackedZip;
      new c(arrayOfString, (String)localObject).a();
      logSize = new File((String)localObject).length();
      showTip(SmttResource.getString("x5_timinglog_start_upload", "string"));
      if (post((String)localObject))
      {
        showTip(SmttResource.getString("x5_timinglog_upload_success", "string"));
        FileUtils.b(paramString);
      }
      else
      {
        paramString = new StringBuilder();
        paramString.append(SmttResource.getString("x5_timinglog_upload_failed", "string"));
        paramString.append("[");
        paramString.append(responseCode);
        paramString.append("]");
        showTip(paramString.toString());
      }
      g.a("");
      return;
    }
    showTip(SmttResource.getString("x5_timinglog_no_log", "string"));
  }
  
  public static void packAndUploadDump(String paramString)
  {
    if (paramString == null) {
      return;
    }
    Object localObject = new File(paramString);
    String[] arrayOfString = ((File)localObject).list();
    if (arrayOfString != null)
    {
      if (arrayOfString.length == 0) {
        return;
      }
      int i = 0;
      while (i < arrayOfString.length)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(localObject);
        localStringBuilder.append("/");
        localStringBuilder.append(arrayOfString[i]);
        arrayOfString[i] = localStringBuilder.toString();
        i += 1;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(((File)localObject).getParent());
      localStringBuilder.append("/dump.zip");
      localObject = localStringBuilder.toString();
      new c(arrayOfString, (String)localObject).a();
      MttLog.setDumpUploadFlag();
      if (post((String)localObject)) {
        FileUtils.b(paramString);
      }
      return;
    }
  }
  
  public static void packAndUploadRenderInfo(String paramString)
  {
    if (paramString == null) {
      return;
    }
    paramString = new File(paramString);
    String[] arrayOfString = paramString.list();
    if (arrayOfString != null)
    {
      if (arrayOfString.length == 0) {
        return;
      }
      int i = 0;
      while (i < arrayOfString.length)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString);
        localStringBuilder.append("/");
        localStringBuilder.append(arrayOfString[i]);
        arrayOfString[i] = localStringBuilder.toString();
        i += 1;
      }
      new c(arrayOfString, sRenderInfoPackedZip).a();
      paramString = new X5LogUploadManager.b(sRenderInfoPackedZip, "RenderUpload", g.a(), null);
      X5LogUploadManager.a().a(paramString);
      return;
    }
  }
  
  public static void packDetectLog(String paramString)
  {
    if (MttLog.logDevice == 2) {
      return;
    }
    MttLog.flushLogs();
    paramString = new File(paramString);
    if (paramString.exists())
    {
      if (paramString.list().length == 0) {
        return;
      }
      String[] arrayOfString = paramString.list();
      if (arrayOfString == null) {
        return;
      }
      int i = 0;
      while (i < arrayOfString.length)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString);
        localStringBuilder.append("/");
        localStringBuilder.append(arrayOfString[i]);
        arrayOfString[i] = localStringBuilder.toString();
        i += 1;
      }
      new c(arrayOfString, sLogCatPackedZip).a();
      return;
    }
  }
  
  public static void packDump(String paramString)
  {
    if (paramString == null) {
      return;
    }
    File localFile = new File(paramString);
    String[] arrayOfString = localFile.list();
    if (arrayOfString != null)
    {
      if (arrayOfString.length == 0) {
        return;
      }
      int i = 0;
      while (i < arrayOfString.length)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(localFile);
        localStringBuilder.append("/");
        localStringBuilder.append(arrayOfString[i]);
        arrayOfString[i] = localStringBuilder.toString();
        i += 1;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(localFile.getParent());
      localStringBuilder.append("/dump.zip");
      new c(arrayOfString, localStringBuilder.toString()).a();
      FileUtils.b(paramString);
      return;
    }
  }
  
  public static void perfLog(String paramString)
  {
    if (!enablePerfLog()) {
      return;
    }
    if (mPerfLogToFile)
    {
      X5Log.d("performance", paramString);
      return;
    }
    synchronized (mLogMessages)
    {
      if (mLogMessagesCount < 0) {
        return;
      }
      if (mLogMessagesCount < 48)
      {
        Vector localVector2 = mLogMessages;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString);
        localStringBuilder.append(", elapsed = ");
        localStringBuilder.append(SystemClock.uptimeMillis() - mBeginTime);
        localStringBuilder.append("\r\n");
        localVector2.add(localStringBuilder.toString());
        mLogMessagesCount += 1;
      }
      return;
    }
  }
  
  /* Error */
  private static boolean post(String paramString)
  {
    // Byte code:
    //   0: new 231	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 233	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_0
    //   9: aload_0
    //   10: invokevirtual 236	java/io/File:exists	()Z
    //   13: ifeq +419 -> 432
    //   16: aload_0
    //   17: invokevirtual 426	java/io/File:length	()J
    //   20: lconst_0
    //   21: lcmp
    //   22: ifeq +410 -> 432
    //   25: aload_0
    //   26: invokevirtual 252	java/io/File:isFile	()Z
    //   29: ifne +5 -> 34
    //   32: iconst_0
    //   33: ireturn
    //   34: new 482	java/net/URL
    //   37: dup
    //   38: ldc 32
    //   40: invokespecial 483	java/net/URL:<init>	(Ljava/lang/String;)V
    //   43: invokevirtual 487	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   46: checkcast 489	java/net/HttpURLConnection
    //   49: astore_2
    //   50: aload_2
    //   51: iconst_1
    //   52: invokevirtual 492	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   55: aload_2
    //   56: iconst_1
    //   57: invokevirtual 495	java/net/HttpURLConnection:setDoInput	(Z)V
    //   60: aload_2
    //   61: iconst_0
    //   62: invokevirtual 498	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   65: aload_2
    //   66: ldc_w 500
    //   69: invokevirtual 503	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   72: new 88	java/lang/StringBuilder
    //   75: dup
    //   76: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   79: astore_3
    //   80: aload_3
    //   81: ldc_w 420
    //   84: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: pop
    //   88: aload_3
    //   89: aload_0
    //   90: invokevirtual 426	java/io/File:length	()J
    //   93: invokevirtual 471	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   96: pop
    //   97: aload_2
    //   98: ldc_w 505
    //   101: aload_3
    //   102: invokevirtual 110	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   105: invokevirtual 508	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   108: aload_2
    //   109: ldc_w 510
    //   112: ldc_w 512
    //   115: invokevirtual 508	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   118: aload_2
    //   119: ldc_w 514
    //   122: ldc_w 516
    //   125: invokevirtual 508	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   128: aload_2
    //   129: ldc_w 518
    //   132: ldc_w 520
    //   135: invokevirtual 508	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   138: invokestatic 261	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   141: invokevirtual 523	com/tencent/smtt/webkit/service/SmttServiceProxy:getEnableQua1	()Z
    //   144: ifeq +29 -> 173
    //   147: invokestatic 261	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   150: invokevirtual 526	com/tencent/smtt/webkit/service/SmttServiceProxy:getQUA	()Ljava/lang/String;
    //   153: astore_3
    //   154: aload_3
    //   155: ifnull +18 -> 173
    //   158: aload_3
    //   159: invokevirtual 229	java/lang/String:length	()I
    //   162: ifle +11 -> 173
    //   165: aload_2
    //   166: ldc_w 528
    //   169: aload_3
    //   170: invokevirtual 508	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   173: aload_2
    //   174: ldc_w 530
    //   177: invokestatic 261	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   180: invokevirtual 533	com/tencent/smtt/webkit/service/SmttServiceProxy:getQUA2	()Ljava/lang/String;
    //   183: invokevirtual 508	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   186: aload_2
    //   187: ldc_w 535
    //   190: invokestatic 261	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   193: invokevirtual 538	com/tencent/smtt/webkit/service/SmttServiceProxy:getGUID	()Ljava/lang/String;
    //   196: invokevirtual 508	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   199: invokestatic 418	com/tencent/smtt/util/g:a	()Ljava/lang/String;
    //   202: ldc_w 420
    //   205: invokevirtual 424	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   208: ifne +23 -> 231
    //   211: aload_2
    //   212: ldc_w 540
    //   215: invokestatic 418	com/tencent/smtt/util/g:a	()Ljava/lang/String;
    //   218: invokevirtual 508	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   221: aload_2
    //   222: ldc_w 542
    //   225: invokestatic 544	com/tencent/smtt/util/g:b	()Ljava/lang/String;
    //   228: invokevirtual 508	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   231: aload_2
    //   232: invokevirtual 548	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   235: astore_3
    //   236: sipush 10240
    //   239: newarray <illegal type>
    //   241: astore 4
    //   243: new 550	java/io/FileInputStream
    //   246: dup
    //   247: aload_0
    //   248: invokespecial 553	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   251: astore_0
    //   252: aload_0
    //   253: aload 4
    //   255: iconst_0
    //   256: sipush 10240
    //   259: invokevirtual 557	java/io/FileInputStream:read	([BII)I
    //   262: istore_1
    //   263: iload_1
    //   264: iconst_m1
    //   265: if_icmpeq +14 -> 279
    //   268: aload_3
    //   269: aload 4
    //   271: iconst_0
    //   272: iload_1
    //   273: invokevirtual 562	java/io/OutputStream:write	([BII)V
    //   276: goto -24 -> 252
    //   279: aload_0
    //   280: invokevirtual 563	java/io/FileInputStream:close	()V
    //   283: aload_3
    //   284: invokevirtual 564	java/io/OutputStream:close	()V
    //   287: aload_2
    //   288: invokevirtual 567	java/net/HttpURLConnection:getResponseCode	()I
    //   291: putstatic 174	com/tencent/smtt/util/MttTimingLog:responseCode	I
    //   294: getstatic 174	com/tencent/smtt/util/MttTimingLog:responseCode	I
    //   297: istore_1
    //   298: sipush 200
    //   301: iload_1
    //   302: if_icmpne +5 -> 307
    //   305: iconst_1
    //   306: ireturn
    //   307: iconst_0
    //   308: ireturn
    //   309: astore_2
    //   310: goto +18 -> 328
    //   313: goto +37 -> 350
    //   316: goto +55 -> 371
    //   319: goto +73 -> 392
    //   322: goto +91 -> 413
    //   325: astore_2
    //   326: aconst_null
    //   327: astore_0
    //   328: aload_0
    //   329: ifnull +17 -> 346
    //   332: aload_0
    //   333: invokevirtual 563	java/io/FileInputStream:close	()V
    //   336: goto +10 -> 346
    //   339: astore_0
    //   340: aload_0
    //   341: invokevirtual 333	java/io/IOException:printStackTrace	()V
    //   344: iconst_0
    //   345: ireturn
    //   346: aload_2
    //   347: athrow
    //   348: aconst_null
    //   349: astore_0
    //   350: aload_0
    //   351: ifnull +16 -> 367
    //   354: aload_0
    //   355: invokevirtual 563	java/io/FileInputStream:close	()V
    //   358: iconst_0
    //   359: ireturn
    //   360: astore_0
    //   361: aload_0
    //   362: invokevirtual 333	java/io/IOException:printStackTrace	()V
    //   365: iconst_0
    //   366: ireturn
    //   367: iconst_0
    //   368: ireturn
    //   369: aconst_null
    //   370: astore_0
    //   371: aload_0
    //   372: ifnull +16 -> 388
    //   375: aload_0
    //   376: invokevirtual 563	java/io/FileInputStream:close	()V
    //   379: iconst_0
    //   380: ireturn
    //   381: astore_0
    //   382: aload_0
    //   383: invokevirtual 333	java/io/IOException:printStackTrace	()V
    //   386: iconst_0
    //   387: ireturn
    //   388: iconst_0
    //   389: ireturn
    //   390: aconst_null
    //   391: astore_0
    //   392: aload_0
    //   393: ifnull +16 -> 409
    //   396: aload_0
    //   397: invokevirtual 563	java/io/FileInputStream:close	()V
    //   400: iconst_0
    //   401: ireturn
    //   402: astore_0
    //   403: aload_0
    //   404: invokevirtual 333	java/io/IOException:printStackTrace	()V
    //   407: iconst_0
    //   408: ireturn
    //   409: iconst_0
    //   410: ireturn
    //   411: aconst_null
    //   412: astore_0
    //   413: aload_0
    //   414: ifnull +16 -> 430
    //   417: aload_0
    //   418: invokevirtual 563	java/io/FileInputStream:close	()V
    //   421: iconst_0
    //   422: ireturn
    //   423: astore_0
    //   424: aload_0
    //   425: invokevirtual 333	java/io/IOException:printStackTrace	()V
    //   428: iconst_0
    //   429: ireturn
    //   430: iconst_0
    //   431: ireturn
    //   432: iconst_0
    //   433: ireturn
    //   434: astore_0
    //   435: iconst_0
    //   436: ireturn
    //   437: astore_0
    //   438: goto -27 -> 411
    //   441: astore_0
    //   442: goto -52 -> 390
    //   445: astore_0
    //   446: goto -77 -> 369
    //   449: astore_0
    //   450: goto -102 -> 348
    //   453: astore_2
    //   454: goto -132 -> 322
    //   457: astore_2
    //   458: goto -139 -> 319
    //   461: astore_2
    //   462: goto -146 -> 316
    //   465: astore_2
    //   466: goto -153 -> 313
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	469	0	paramString	String
    //   262	41	1	i	int
    //   49	239	2	localHttpURLConnection	java.net.HttpURLConnection
    //   309	1	2	localObject1	Object
    //   325	22	2	localObject2	Object
    //   453	1	2	localFileNotFoundException	FileNotFoundException
    //   457	1	2	localIOException	IOException
    //   461	1	2	localOutOfMemoryError	OutOfMemoryError
    //   465	1	2	localSecurityException	SecurityException
    //   79	205	3	localObject3	Object
    //   241	29	4	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   252	263	309	finally
    //   268	276	309	finally
    //   279	283	309	finally
    //   243	252	325	finally
    //   332	336	339	java/io/IOException
    //   354	358	360	java/io/IOException
    //   375	379	381	java/io/IOException
    //   396	400	402	java/io/IOException
    //   417	421	423	java/io/IOException
    //   0	32	434	java/lang/Exception
    //   34	154	434	java/lang/Exception
    //   158	173	434	java/lang/Exception
    //   173	231	434	java/lang/Exception
    //   231	243	434	java/lang/Exception
    //   283	298	434	java/lang/Exception
    //   332	336	434	java/lang/Exception
    //   340	344	434	java/lang/Exception
    //   346	348	434	java/lang/Exception
    //   354	358	434	java/lang/Exception
    //   361	365	434	java/lang/Exception
    //   375	379	434	java/lang/Exception
    //   382	386	434	java/lang/Exception
    //   396	400	434	java/lang/Exception
    //   403	407	434	java/lang/Exception
    //   417	421	434	java/lang/Exception
    //   424	428	434	java/lang/Exception
    //   243	252	437	java/io/FileNotFoundException
    //   243	252	441	java/io/IOException
    //   243	252	445	java/lang/OutOfMemoryError
    //   243	252	449	java/lang/SecurityException
    //   252	263	453	java/io/FileNotFoundException
    //   268	276	453	java/io/FileNotFoundException
    //   279	283	453	java/io/FileNotFoundException
    //   252	263	457	java/io/IOException
    //   268	276	457	java/io/IOException
    //   279	283	457	java/io/IOException
    //   252	263	461	java/lang/OutOfMemoryError
    //   268	276	461	java/lang/OutOfMemoryError
    //   279	283	461	java/lang/OutOfMemoryError
    //   252	263	465	java/lang/SecurityException
    //   268	276	465	java/lang/SecurityException
    //   279	283	465	java/lang/SecurityException
  }
  
  private static void processIfNeeded(String paramString) {}
  
  public static void recordUserActions(String paramString)
  {
    sUserActionsRecorder.a(paramString);
  }
  
  public static void resetPerformanceRecorder()
  {
    sPerformanceRecorder.a();
  }
  
  public static void resetUserActionsSummary()
  {
    d.a(sUserActionsRecorder);
  }
  
  public static void setEnabled(boolean paramBoolean)
  {
    if (paramBoolean == mEnabled) {
      return;
    }
    nativeJNIEnableLog(paramBoolean);
    if (paramBoolean)
    {
      mEnabled = true;
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(ROOT);
      ((StringBuilder)localObject1).append("logs");
      sBaseDir = ((StringBuilder)localObject1).toString();
      localObject1 = new File(sBaseDir);
      if ((!((File)localObject1).exists()) && (!((File)localObject1).mkdirs())) {
        return;
      }
      try
      {
        sOrphanLog = new a("timing");
        return;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        return;
      }
    }
    mEnabled = false;
    try
    {
      if (sOrphanLog == null) {
        return;
      }
      if (sOrphanLog != null)
      {
        sOrphanLog.a();
        sOrphanLog = null;
      }
      return;
    }
    finally {}
  }
  
  public static void setPerfEnabled(boolean paramBoolean)
  {
    if (paramBoolean == mPerfEnabled) {
      return;
    }
    mPerfEnabled = paramBoolean;
  }
  
  public static void setPerfLogToFileEnabled(boolean paramBoolean)
  {
    mPerfLogToFile = paramBoolean;
    setPerfEnabled(paramBoolean);
  }
  
  private static void showTip(String paramString)
  {
    SmttServiceProxy.getInstance().showToast(paramString, 0);
  }
  
  public static void startLog() {}
  
  public static void uploadDump(String paramString)
  {
    if (paramString == null) {
      return;
    }
    MttLog.setDumpUploadFlag();
    post(paramString);
  }
  
  private static class a
  {
    private int jdField_a_of_type_Int = 0;
    private FileOutputStream jdField_a_of_type_JavaIoFileOutputStream = null;
    private byte[] jdField_a_of_type_ArrayOfByte = new byte['䀀'];
    
    a(String paramString)
      throws IOException
    {
      this.jdField_a_of_type_JavaIoFileOutputStream = new FileOutputStream(MttTimingLog.fileNameForPage(paramString));
    }
    
    public void a()
    {
      try
      {
        if (this.jdField_a_of_type_Int != 0)
        {
          this.jdField_a_of_type_JavaIoFileOutputStream.write(this.jdField_a_of_type_ArrayOfByte, 0, this.jdField_a_of_type_Int);
          this.jdField_a_of_type_JavaIoFileOutputStream.flush();
          this.jdField_a_of_type_JavaIoFileOutputStream.close();
          return;
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }
    
    public void a(String paramString)
    {
      if (paramString.length() > 16384) {
        return;
      }
      if (this.jdField_a_of_type_Int + paramString.length() > 16384) {}
      try
      {
        this.jdField_a_of_type_JavaIoFileOutputStream.write(this.jdField_a_of_type_ArrayOfByte, 0, this.jdField_a_of_type_Int);
        this.jdField_a_of_type_Int = 0;
        System.arraycopy(paramString.getBytes(), 0, this.jdField_a_of_type_ArrayOfByte, this.jdField_a_of_type_Int, paramString.length());
        this.jdField_a_of_type_Int += paramString.length();
        return;
      }
      catch (IOException paramString) {}
    }
    
    public void b()
    {
      try
      {
        if (this.jdField_a_of_type_Int != 0)
        {
          this.jdField_a_of_type_JavaIoFileOutputStream.write(this.jdField_a_of_type_ArrayOfByte, 0, this.jdField_a_of_type_Int);
          this.jdField_a_of_type_JavaIoFileOutputStream.flush();
          this.jdField_a_of_type_Int = 0;
          return;
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }
  }
  
  private static class b
  {
    private String a;
    public Date a;
    public Date b = null;
    public Date c = null;
    public Date d = null;
    public Date e = null;
    
    private b()
    {
      this.jdField_a_of_type_JavaUtilDate = null;
      this.jdField_a_of_type_JavaLangString = "";
    }
    
    private long a(Date paramDate1, Date paramDate2)
    {
      if ((paramDate1 != null) && (paramDate2 != null)) {
        return paramDate2.getTime() - paramDate1.getTime();
      }
      return 0L;
    }
    
    public static String[] a(String paramString)
    {
      paramString = paramString.substring(1).split("<");
      int i = 0;
      while (i < paramString.length)
      {
        if (paramString[i].length() != 0) {
          paramString[i] = paramString[i].substring(0, paramString[i].length() - 1);
        }
        i += 1;
      }
      return paramString;
    }
    
    private void b(String paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append("<");
      localStringBuilder.append(paramString);
      localStringBuilder.append(">");
      this.jdField_a_of_type_JavaLangString = localStringBuilder.toString();
    }
    
    public void a()
    {
      this.jdField_a_of_type_JavaLangString = "";
      this.e = null;
      this.d = null;
      this.c = null;
      this.b = null;
      this.jdField_a_of_type_JavaUtilDate = null;
    }
    
    public void a(String paramString)
    {
      if (!paramString.startsWith("======")) {
        return;
      }
      paramString = a(paramString.substring(6));
      if ((paramString[0].startsWith("load-started")) && (this.jdField_a_of_type_JavaUtilDate == null))
      {
        this.jdField_a_of_type_JavaUtilDate = new Date();
      }
      else if (paramString[0].startsWith("main-resource-finished"))
      {
        if (this.d != null) {
          return;
        }
        this.d = new Date();
      }
      else if (paramString[0].startsWith("first-text"))
      {
        this.b = new Date();
      }
      else if (paramString[0].startsWith("first-screen"))
      {
        this.c = new Date();
      }
      else if (paramString[0].startsWith("load-finished"))
      {
        this.e = new Date();
      }
      int i = 1;
      while (i < paramString.length)
      {
        b(paramString[i]);
        i += 1;
      }
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("======<first-text=");
      localStringBuilder.append(a(this.jdField_a_of_type_JavaUtilDate, this.b));
      localStringBuilder.append("><first-screen=");
      localStringBuilder.append(a(this.jdField_a_of_type_JavaUtilDate, this.c));
      localStringBuilder.append("><main-resource-time=");
      localStringBuilder.append(a(this.jdField_a_of_type_JavaUtilDate, this.d));
      localStringBuilder.append("><load-finished-time=");
      localStringBuilder.append(a(this.jdField_a_of_type_JavaUtilDate, this.e));
      localStringBuilder.append(">");
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      return localStringBuilder.toString();
    }
  }
  
  public static class c
  {
    private final String jdField_a_of_type_JavaLangString;
    private final String[] jdField_a_of_type_ArrayOfJavaLangString;
    
    public c(String[] paramArrayOfString, String paramString)
    {
      this.jdField_a_of_type_ArrayOfJavaLangString = paramArrayOfString;
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    /* Error */
    private static void a(File paramFile)
      throws IOException
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore 5
      //   3: aconst_null
      //   4: astore_3
      //   5: new 27	java/io/RandomAccessFile
      //   8: dup
      //   9: aload_0
      //   10: ldc 29
      //   12: invokespecial 32	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
      //   15: astore_0
      //   16: ldc 34
      //   18: iconst_2
      //   19: invokestatic 40	java/lang/Integer:parseInt	(Ljava/lang/String;I)I
      //   22: istore_1
      //   23: aload_0
      //   24: ldc2_w 41
      //   27: invokevirtual 46	java/io/RandomAccessFile:seek	(J)V
      //   30: aload_0
      //   31: invokevirtual 50	java/io/RandomAccessFile:read	()I
      //   34: istore_2
      //   35: iload_2
      //   36: iload_1
      //   37: iand
      //   38: ifle +23 -> 61
      //   41: aload_0
      //   42: ldc2_w 41
      //   45: invokevirtual 46	java/io/RandomAccessFile:seek	(J)V
      //   48: aload_0
      //   49: iload_1
      //   50: iconst_m1
      //   51: ixor
      //   52: sipush 255
      //   55: iand
      //   56: iload_2
      //   57: iand
      //   58: invokevirtual 54	java/io/RandomAccessFile:write	(I)V
      //   61: aload_0
      //   62: invokevirtual 57	java/io/RandomAccessFile:close	()V
      //   65: return
      //   66: astore_3
      //   67: goto +45 -> 112
      //   70: astore 4
      //   72: goto +18 -> 90
      //   75: astore 4
      //   77: aload_3
      //   78: astore_0
      //   79: aload 4
      //   81: astore_3
      //   82: goto +30 -> 112
      //   85: astore 4
      //   87: aload 5
      //   89: astore_0
      //   90: aload_0
      //   91: astore_3
      //   92: aload 4
      //   94: invokevirtual 60	java/lang/Exception:printStackTrace	()V
      //   97: aload_0
      //   98: ifnull +13 -> 111
      //   101: aload_0
      //   102: invokevirtual 57	java/io/RandomAccessFile:close	()V
      //   105: return
      //   106: astore_0
      //   107: aload_0
      //   108: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   111: return
      //   112: aload_0
      //   113: ifnull +15 -> 128
      //   116: aload_0
      //   117: invokevirtual 57	java/io/RandomAccessFile:close	()V
      //   120: goto +8 -> 128
      //   123: astore_0
      //   124: aload_0
      //   125: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   128: aload_3
      //   129: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	130	0	paramFile	File
      //   22	30	1	i	int
      //   34	24	2	j	int
      //   4	1	3	localObject1	Object
      //   66	12	3	localObject2	Object
      //   81	48	3	localObject3	Object
      //   70	1	4	localException1	Exception
      //   75	5	4	localObject4	Object
      //   85	8	4	localException2	Exception
      //   1	87	5	localObject5	Object
      // Exception table:
      //   from	to	target	type
      //   16	35	66	finally
      //   41	61	66	finally
      //   16	35	70	java/lang/Exception
      //   41	61	70	java/lang/Exception
      //   5	16	75	finally
      //   92	97	75	finally
      //   5	16	85	java/lang/Exception
      //   61	65	106	java/io/IOException
      //   101	105	106	java/io/IOException
      //   116	120	123	java/io/IOException
    }
    
    /* Error */
    public void a()
    {
      // Byte code:
      //   0: new 64	java/io/FileOutputStream
      //   3: dup
      //   4: aload_0
      //   5: getfield 19	com/tencent/smtt/util/MttTimingLog$c:jdField_a_of_type_JavaLangString	Ljava/lang/String;
      //   8: invokespecial 67	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
      //   11: astore 5
      //   13: new 69	java/util/zip/ZipOutputStream
      //   16: dup
      //   17: new 71	java/io/BufferedOutputStream
      //   20: dup
      //   21: aload 5
      //   23: invokespecial 74	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
      //   26: invokespecial 75	java/util/zip/ZipOutputStream:<init>	(Ljava/io/OutputStream;)V
      //   29: astore 10
      //   31: aload 5
      //   33: astore 6
      //   35: aload 10
      //   37: astore 7
      //   39: sipush 2048
      //   42: newarray <illegal type>
      //   44: astore 12
      //   46: aload 5
      //   48: astore 6
      //   50: aload 10
      //   52: astore 7
      //   54: aload_0
      //   55: getfield 17	com/tencent/smtt/util/MttTimingLog$c:jdField_a_of_type_ArrayOfJavaLangString	[Ljava/lang/String;
      //   58: astore 13
      //   60: aload 5
      //   62: astore 6
      //   64: aload 10
      //   66: astore 7
      //   68: aload 13
      //   70: arraylength
      //   71: istore_2
      //   72: iconst_0
      //   73: istore_1
      //   74: iload_1
      //   75: iload_2
      //   76: if_icmpge +437 -> 513
      //   79: aload 13
      //   81: iload_1
      //   82: aaload
      //   83: astore 8
      //   85: new 77	java/io/FileInputStream
      //   88: dup
      //   89: aload 8
      //   91: invokespecial 78	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
      //   94: astore 4
      //   96: new 80	java/io/BufferedInputStream
      //   99: dup
      //   100: aload 4
      //   102: sipush 2048
      //   105: invokespecial 83	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;I)V
      //   108: astore 11
      //   110: aload 4
      //   112: astore 6
      //   114: aload 11
      //   116: astore 7
      //   118: aload 10
      //   120: new 85	java/util/zip/ZipEntry
      //   123: dup
      //   124: aload 8
      //   126: aload 8
      //   128: ldc 87
      //   130: invokevirtual 93	java/lang/String:lastIndexOf	(Ljava/lang/String;)I
      //   133: iconst_1
      //   134: iadd
      //   135: invokevirtual 97	java/lang/String:substring	(I)Ljava/lang/String;
      //   138: invokespecial 98	java/util/zip/ZipEntry:<init>	(Ljava/lang/String;)V
      //   141: invokevirtual 102	java/util/zip/ZipOutputStream:putNextEntry	(Ljava/util/zip/ZipEntry;)V
      //   144: aload 4
      //   146: astore 6
      //   148: aload 11
      //   150: astore 7
      //   152: aload 11
      //   154: aload 12
      //   156: iconst_0
      //   157: sipush 2048
      //   160: invokevirtual 105	java/io/BufferedInputStream:read	([BII)I
      //   163: istore_3
      //   164: iload_3
      //   165: iconst_m1
      //   166: if_icmpeq +23 -> 189
      //   169: aload 4
      //   171: astore 6
      //   173: aload 11
      //   175: astore 7
      //   177: aload 10
      //   179: aload 12
      //   181: iconst_0
      //   182: iload_3
      //   183: invokevirtual 108	java/util/zip/ZipOutputStream:write	([BII)V
      //   186: goto -42 -> 144
      //   189: aload 4
      //   191: astore 6
      //   193: aload 11
      //   195: astore 7
      //   197: aload 10
      //   199: invokevirtual 111	java/util/zip/ZipOutputStream:flush	()V
      //   202: aload 4
      //   204: astore 6
      //   206: aload 11
      //   208: astore 7
      //   210: aload 10
      //   212: invokevirtual 114	java/util/zip/ZipOutputStream:closeEntry	()V
      //   215: aload 5
      //   217: astore 6
      //   219: aload 10
      //   221: astore 7
      //   223: aload 11
      //   225: invokevirtual 115	java/io/BufferedInputStream:close	()V
      //   228: goto +18 -> 246
      //   231: astore 8
      //   233: aload 5
      //   235: astore 6
      //   237: aload 10
      //   239: astore 7
      //   241: aload 8
      //   243: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   246: aload 5
      //   248: astore 6
      //   250: aload 10
      //   252: astore 7
      //   254: aload 4
      //   256: invokevirtual 116	java/io/FileInputStream:close	()V
      //   259: goto +154 -> 413
      //   262: astore 4
      //   264: aload 5
      //   266: astore 6
      //   268: aload 10
      //   270: astore 7
      //   272: aload 4
      //   274: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   277: goto +136 -> 413
      //   280: astore 9
      //   282: aload 4
      //   284: astore 8
      //   286: aload 11
      //   288: astore 4
      //   290: goto +48 -> 338
      //   293: astore 6
      //   295: goto +24 -> 319
      //   298: astore 9
      //   300: aconst_null
      //   301: astore 6
      //   303: aload 4
      //   305: astore 8
      //   307: aload 6
      //   309: astore 4
      //   311: goto +27 -> 338
      //   314: astore 6
      //   316: aconst_null
      //   317: astore 4
      //   319: aconst_null
      //   320: astore 9
      //   322: aload 6
      //   324: astore 8
      //   326: goto +104 -> 430
      //   329: astore 9
      //   331: aconst_null
      //   332: astore 8
      //   334: aload 8
      //   336: astore 4
      //   338: aload 8
      //   340: astore 6
      //   342: aload 4
      //   344: astore 7
      //   346: aload 9
      //   348: invokevirtual 60	java/lang/Exception:printStackTrace	()V
      //   351: aload 4
      //   353: ifnull +34 -> 387
      //   356: aload 5
      //   358: astore 6
      //   360: aload 10
      //   362: astore 7
      //   364: aload 4
      //   366: invokevirtual 115	java/io/BufferedInputStream:close	()V
      //   369: goto +18 -> 387
      //   372: astore 4
      //   374: aload 5
      //   376: astore 6
      //   378: aload 10
      //   380: astore 7
      //   382: aload 4
      //   384: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   387: aload 8
      //   389: ifnull +24 -> 413
      //   392: aload 5
      //   394: astore 6
      //   396: aload 10
      //   398: astore 7
      //   400: aload 8
      //   402: invokevirtual 116	java/io/FileInputStream:close	()V
      //   405: goto +8 -> 413
      //   408: astore 4
      //   410: goto -146 -> 264
      //   413: iload_1
      //   414: iconst_1
      //   415: iadd
      //   416: istore_1
      //   417: goto -343 -> 74
      //   420: astore 8
      //   422: aload 7
      //   424: astore 9
      //   426: aload 6
      //   428: astore 4
      //   430: aload 9
      //   432: ifnull +34 -> 466
      //   435: aload 5
      //   437: astore 6
      //   439: aload 10
      //   441: astore 7
      //   443: aload 9
      //   445: invokevirtual 115	java/io/BufferedInputStream:close	()V
      //   448: goto +18 -> 466
      //   451: astore 9
      //   453: aload 5
      //   455: astore 6
      //   457: aload 10
      //   459: astore 7
      //   461: aload 9
      //   463: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   466: aload 4
      //   468: ifnull +34 -> 502
      //   471: aload 5
      //   473: astore 6
      //   475: aload 10
      //   477: astore 7
      //   479: aload 4
      //   481: invokevirtual 116	java/io/FileInputStream:close	()V
      //   484: goto +18 -> 502
      //   487: astore 4
      //   489: aload 5
      //   491: astore 6
      //   493: aload 10
      //   495: astore 7
      //   497: aload 4
      //   499: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   502: aload 5
      //   504: astore 6
      //   506: aload 10
      //   508: astore 7
      //   510: aload 8
      //   512: athrow
      //   513: aload 5
      //   515: astore 6
      //   517: aload 10
      //   519: astore 7
      //   521: new 118	java/io/File
      //   524: dup
      //   525: aload_0
      //   526: getfield 19	com/tencent/smtt/util/MttTimingLog$c:jdField_a_of_type_JavaLangString	Ljava/lang/String;
      //   529: invokespecial 119	java/io/File:<init>	(Ljava/lang/String;)V
      //   532: invokestatic 121	com/tencent/smtt/util/MttTimingLog$c:a	(Ljava/io/File;)V
      //   535: aload 10
      //   537: invokevirtual 122	java/util/zip/ZipOutputStream:close	()V
      //   540: goto +10 -> 550
      //   543: astore 4
      //   545: aload 4
      //   547: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   550: aload 5
      //   552: invokevirtual 123	java/io/FileOutputStream:close	()V
      //   555: return
      //   556: astore 8
      //   558: aload 10
      //   560: astore 4
      //   562: goto +40 -> 602
      //   565: astore 4
      //   567: aconst_null
      //   568: astore 7
      //   570: goto +90 -> 660
      //   573: astore 8
      //   575: aconst_null
      //   576: astore 4
      //   578: goto +24 -> 602
      //   581: astore 4
      //   583: aconst_null
      //   584: astore 7
      //   586: aload 7
      //   588: astore 5
      //   590: goto +70 -> 660
      //   593: astore 8
      //   595: aconst_null
      //   596: astore 4
      //   598: aload 4
      //   600: astore 5
      //   602: aload 5
      //   604: astore 6
      //   606: aload 4
      //   608: astore 7
      //   610: aload 8
      //   612: invokevirtual 60	java/lang/Exception:printStackTrace	()V
      //   615: aload 4
      //   617: ifnull +18 -> 635
      //   620: aload 4
      //   622: invokevirtual 122	java/util/zip/ZipOutputStream:close	()V
      //   625: goto +10 -> 635
      //   628: astore 4
      //   630: aload 4
      //   632: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   635: aload 5
      //   637: ifnull +16 -> 653
      //   640: aload 5
      //   642: invokevirtual 123	java/io/FileOutputStream:close	()V
      //   645: return
      //   646: astore 4
      //   648: aload 4
      //   650: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   653: return
      //   654: astore 4
      //   656: aload 6
      //   658: astore 5
      //   660: aload 7
      //   662: ifnull +18 -> 680
      //   665: aload 7
      //   667: invokevirtual 122	java/util/zip/ZipOutputStream:close	()V
      //   670: goto +10 -> 680
      //   673: astore 6
      //   675: aload 6
      //   677: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   680: aload 5
      //   682: ifnull +18 -> 700
      //   685: aload 5
      //   687: invokevirtual 123	java/io/FileOutputStream:close	()V
      //   690: goto +10 -> 700
      //   693: astore 5
      //   695: aload 5
      //   697: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   700: aload 4
      //   702: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	703	0	this	c
      //   73	344	1	i	int
      //   71	6	2	j	int
      //   163	20	3	k	int
      //   94	161	4	localFileInputStream	java.io.FileInputStream
      //   262	21	4	localIOException1	IOException
      //   288	77	4	localObject1	Object
      //   372	11	4	localIOException2	IOException
      //   408	1	4	localIOException3	IOException
      //   428	52	4	localObject2	Object
      //   487	11	4	localIOException4	IOException
      //   543	3	4	localIOException5	IOException
      //   560	1	4	localZipOutputStream1	java.util.zip.ZipOutputStream
      //   565	1	4	localObject3	Object
      //   576	1	4	localObject4	Object
      //   581	1	4	localObject5	Object
      //   596	25	4	localObject6	Object
      //   628	3	4	localIOException6	IOException
      //   646	3	4	localIOException7	IOException
      //   654	47	4	localObject7	Object
      //   11	675	5	localObject8	Object
      //   693	3	5	localIOException8	IOException
      //   33	234	6	localObject9	Object
      //   293	1	6	localObject10	Object
      //   301	7	6	localObject11	Object
      //   314	9	6	localObject12	Object
      //   340	317	6	localObject13	Object
      //   673	3	6	localIOException9	IOException
      //   37	629	7	localObject14	Object
      //   83	44	8	str	String
      //   231	11	8	localIOException10	IOException
      //   284	117	8	localObject15	Object
      //   420	91	8	localObject16	Object
      //   556	1	8	localException1	Exception
      //   573	1	8	localException2	Exception
      //   593	18	8	localException3	Exception
      //   280	1	9	localException4	Exception
      //   298	1	9	localException5	Exception
      //   320	1	9	localObject17	Object
      //   329	18	9	localException6	Exception
      //   424	20	9	localObject18	Object
      //   451	11	9	localIOException11	IOException
      //   29	530	10	localZipOutputStream2	java.util.zip.ZipOutputStream
      //   108	179	11	localBufferedInputStream	java.io.BufferedInputStream
      //   44	136	12	arrayOfByte	byte[]
      //   58	22	13	arrayOfString	String[]
      // Exception table:
      //   from	to	target	type
      //   223	228	231	java/io/IOException
      //   254	259	262	java/io/IOException
      //   118	144	280	java/lang/Exception
      //   152	164	280	java/lang/Exception
      //   177	186	280	java/lang/Exception
      //   197	202	280	java/lang/Exception
      //   210	215	280	java/lang/Exception
      //   96	110	293	finally
      //   96	110	298	java/lang/Exception
      //   85	96	314	finally
      //   85	96	329	java/lang/Exception
      //   364	369	372	java/io/IOException
      //   400	405	408	java/io/IOException
      //   118	144	420	finally
      //   152	164	420	finally
      //   177	186	420	finally
      //   197	202	420	finally
      //   210	215	420	finally
      //   346	351	420	finally
      //   443	448	451	java/io/IOException
      //   479	484	487	java/io/IOException
      //   535	540	543	java/io/IOException
      //   39	46	556	java/lang/Exception
      //   54	60	556	java/lang/Exception
      //   68	72	556	java/lang/Exception
      //   223	228	556	java/lang/Exception
      //   241	246	556	java/lang/Exception
      //   254	259	556	java/lang/Exception
      //   272	277	556	java/lang/Exception
      //   364	369	556	java/lang/Exception
      //   382	387	556	java/lang/Exception
      //   400	405	556	java/lang/Exception
      //   443	448	556	java/lang/Exception
      //   461	466	556	java/lang/Exception
      //   479	484	556	java/lang/Exception
      //   497	502	556	java/lang/Exception
      //   510	513	556	java/lang/Exception
      //   521	535	556	java/lang/Exception
      //   13	31	565	finally
      //   13	31	573	java/lang/Exception
      //   0	13	581	finally
      //   0	13	593	java/lang/Exception
      //   620	625	628	java/io/IOException
      //   550	555	646	java/io/IOException
      //   640	645	646	java/io/IOException
      //   39	46	654	finally
      //   54	60	654	finally
      //   68	72	654	finally
      //   223	228	654	finally
      //   241	246	654	finally
      //   254	259	654	finally
      //   272	277	654	finally
      //   364	369	654	finally
      //   382	387	654	finally
      //   400	405	654	finally
      //   443	448	654	finally
      //   461	466	654	finally
      //   479	484	654	finally
      //   497	502	654	finally
      //   510	513	654	finally
      //   521	535	654	finally
      //   610	615	654	finally
      //   665	670	673	java/io/IOException
      //   685	690	693	java/io/IOException
    }
    
    /* Error */
    public void a(String paramString, byte[] paramArrayOfByte)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore 5
      //   3: aconst_null
      //   4: astore 7
      //   6: new 64	java/io/FileOutputStream
      //   9: dup
      //   10: aload_0
      //   11: getfield 19	com/tencent/smtt/util/MttTimingLog$c:jdField_a_of_type_JavaLangString	Ljava/lang/String;
      //   14: invokespecial 67	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
      //   17: astore_3
      //   18: aload_3
      //   19: astore 4
      //   21: new 69	java/util/zip/ZipOutputStream
      //   24: dup
      //   25: new 71	java/io/BufferedOutputStream
      //   28: dup
      //   29: aload_3
      //   30: invokespecial 74	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
      //   33: invokespecial 75	java/util/zip/ZipOutputStream:<init>	(Ljava/io/OutputStream;)V
      //   36: astore 6
      //   38: aload 6
      //   40: new 85	java/util/zip/ZipEntry
      //   43: dup
      //   44: aload_1
      //   45: aload_1
      //   46: ldc 87
      //   48: invokevirtual 93	java/lang/String:lastIndexOf	(Ljava/lang/String;)I
      //   51: iconst_1
      //   52: iadd
      //   53: invokevirtual 97	java/lang/String:substring	(I)Ljava/lang/String;
      //   56: invokespecial 98	java/util/zip/ZipEntry:<init>	(Ljava/lang/String;)V
      //   59: invokevirtual 102	java/util/zip/ZipOutputStream:putNextEntry	(Ljava/util/zip/ZipEntry;)V
      //   62: aload 6
      //   64: aload_2
      //   65: iconst_0
      //   66: aload_2
      //   67: arraylength
      //   68: invokevirtual 108	java/util/zip/ZipOutputStream:write	([BII)V
      //   71: aload 6
      //   73: invokevirtual 111	java/util/zip/ZipOutputStream:flush	()V
      //   76: aload 6
      //   78: invokevirtual 114	java/util/zip/ZipOutputStream:closeEntry	()V
      //   81: aload 6
      //   83: invokevirtual 122	java/util/zip/ZipOutputStream:close	()V
      //   86: goto +8 -> 94
      //   89: astore_1
      //   90: aload_1
      //   91: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   94: aload_3
      //   95: invokevirtual 123	java/io/FileOutputStream:close	()V
      //   98: return
      //   99: astore_1
      //   100: aload 6
      //   102: astore_2
      //   103: goto +79 -> 182
      //   106: astore_2
      //   107: aload 6
      //   109: astore_1
      //   110: goto +24 -> 134
      //   113: astore_2
      //   114: aload 7
      //   116: astore_1
      //   117: goto +17 -> 134
      //   120: astore_1
      //   121: aconst_null
      //   122: astore_3
      //   123: aload_3
      //   124: astore_2
      //   125: goto +57 -> 182
      //   128: astore_2
      //   129: aconst_null
      //   130: astore_3
      //   131: aload 7
      //   133: astore_1
      //   134: aload_1
      //   135: astore 5
      //   137: aload_3
      //   138: astore 4
      //   140: aload_2
      //   141: invokevirtual 60	java/lang/Exception:printStackTrace	()V
      //   144: aload_1
      //   145: ifnull +15 -> 160
      //   148: aload_1
      //   149: invokevirtual 122	java/util/zip/ZipOutputStream:close	()V
      //   152: goto +8 -> 160
      //   155: astore_1
      //   156: aload_1
      //   157: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   160: aload_3
      //   161: ifnull +13 -> 174
      //   164: aload_3
      //   165: invokevirtual 123	java/io/FileOutputStream:close	()V
      //   168: return
      //   169: astore_1
      //   170: aload_1
      //   171: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   174: return
      //   175: astore_1
      //   176: aload 5
      //   178: astore_2
      //   179: aload 4
      //   181: astore_3
      //   182: aload_2
      //   183: ifnull +15 -> 198
      //   186: aload_2
      //   187: invokevirtual 122	java/util/zip/ZipOutputStream:close	()V
      //   190: goto +8 -> 198
      //   193: astore_2
      //   194: aload_2
      //   195: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   198: aload_3
      //   199: ifnull +15 -> 214
      //   202: aload_3
      //   203: invokevirtual 123	java/io/FileOutputStream:close	()V
      //   206: goto +8 -> 214
      //   209: astore_2
      //   210: aload_2
      //   211: invokevirtual 61	java/io/IOException:printStackTrace	()V
      //   214: aload_1
      //   215: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	216	0	this	c
      //   0	216	1	paramString	String
      //   0	216	2	paramArrayOfByte	byte[]
      //   17	186	3	localObject1	Object
      //   19	161	4	localObject2	Object
      //   1	176	5	str	String
      //   36	72	6	localZipOutputStream	java.util.zip.ZipOutputStream
      //   4	128	7	localObject3	Object
      // Exception table:
      //   from	to	target	type
      //   81	86	89	java/io/IOException
      //   38	81	99	finally
      //   38	81	106	java/lang/Exception
      //   21	38	113	java/lang/Exception
      //   6	18	120	finally
      //   6	18	128	java/lang/Exception
      //   148	152	155	java/io/IOException
      //   94	98	169	java/io/IOException
      //   164	168	169	java/io/IOException
      //   21	38	175	finally
      //   140	144	175	finally
      //   186	190	193	java/io/IOException
      //   202	206	209	java/io/IOException
    }
  }
  
  private static class d
  {
    private static int jdField_a_of_type_Int;
    private boolean jdField_a_of_type_Boolean = false;
    
    private void a()
    {
      synchronized (MttTimingLog.writeUserActionTableLock)
      {
        int i = MttTimingLog.MAXRECORDUANUM - 1;
        while (i >= 0)
        {
          MttTimingLog.mOutputStr[i] = " ";
          i -= 1;
        }
        return;
      }
    }
    
    public String a()
    {
      Object localObject4 = MttTimingLog.writeUserActionTableLock;
      Object localObject1 = "";
      Object localObject3 = localObject1;
      for (;;)
      {
        try
        {
          if (this.jdField_a_of_type_Boolean)
          {
            i = MttTimingLog.recordNum;
            localObject3 = localObject1;
            if (i < MttTimingLog.MAXRECORDUANUM)
            {
              localObject3 = new StringBuilder();
              ((StringBuilder)localObject3).append((String)localObject1);
              ((StringBuilder)localObject3).append(MttTimingLog.mOutputStr[i]);
              localObject1 = ((StringBuilder)localObject3).toString();
              i += 1;
              continue;
              if (i < MttTimingLog.recordNum)
              {
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append((String)localObject3);
                ((StringBuilder)localObject1).append(MttTimingLog.mOutputStr[i]);
                localObject3 = ((StringBuilder)localObject1).toString();
                i += 1;
                continue;
              }
              return (String)localObject3;
            }
          }
        }
        finally {}
        int i = 0;
      }
    }
    
    public void a(String paramString)
    {
      if (paramString == null) {
        return;
      }
      for (;;)
      {
        Object localObject1;
        synchronized (MttTimingLog.writeUserActionTableLock)
        {
          if (MttTimingLog.recordNum < 1)
          {
            if (!this.jdField_a_of_type_Boolean) {
              localObject1 = MttTimingLog.mOutputStr[0];
            } else {
              localObject1 = MttTimingLog.mOutputStr[(MttTimingLog.MAXRECORDUANUM - 1)];
            }
          }
          else
          {
            localObject1 = MttTimingLog.mOutputStr[(MttTimingLog.recordNum - 1)];
            break label303;
            localObject1 = ((String)localObject1).split(" ");
            String[] arrayOfString = paramString.split(" ");
            if ((arrayOfString != null) && (arrayOfString[1] != null) && (arrayOfString[1].equals("SCROLL")))
            {
              jdField_a_of_type_Int += 1;
              if ((localObject1 != null) && (localObject1[1] != null) && (arrayOfString[1].equals(localObject1[1])))
              {
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append(paramString);
                ((StringBuilder)localObject1).append(" ");
                ((StringBuilder)localObject1).append(jdField_a_of_type_Int);
                paramString = ((StringBuilder)localObject1).toString();
                if (MttTimingLog.recordNum < 1)
                {
                  if (!this.jdField_a_of_type_Boolean) {
                    MttTimingLog.mOutputStr[0] = paramString;
                  } else {
                    MttTimingLog.mOutputStr[(MttTimingLog.MAXRECORDUANUM - 1)] = paramString;
                  }
                }
                else {
                  MttTimingLog.mOutputStr[(MttTimingLog.recordNum - 1)] = paramString;
                }
              }
              else
              {
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append(paramString);
                ((StringBuilder)localObject1).append(" ");
                ((StringBuilder)localObject1).append(1);
                paramString = ((StringBuilder)localObject1).toString();
                MttTimingLog.mOutputStr[MttTimingLog.recordNum] = paramString;
                MttTimingLog.access$308();
              }
            }
            else
            {
              jdField_a_of_type_Int = 0;
              MttTimingLog.mOutputStr[MttTimingLog.recordNum] = paramString;
              MttTimingLog.access$308();
            }
            if (MttTimingLog.recordNum >= MttTimingLog.MAXRECORDUANUM)
            {
              MttTimingLog.access$302(0);
              this.jdField_a_of_type_Boolean = true;
            }
            return;
          }
        }
        label303:
        if (localObject1 == null) {
          localObject1 = null;
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\MttTimingLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */