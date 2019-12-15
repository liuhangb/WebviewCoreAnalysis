package com.tencent.smtt.memory;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.Build.VERSION;
import android.os.Debug.MemoryInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.smtt.os.SMTTAdaptation;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.tencent.base.TencentMemoryPressureListener;
import org.chromium.tencent.base.TencentMemoryPressureListener.Helper;
import org.chromium.tencent.base.TencentMemoryPressureListener.MemoryTrimCallback;

public class MemoryChecker
{
  private static int CHECK_AVAILABLE_CONTINUOUS_VM32 = 0;
  private static int CHECK_AVAILABLE_CONTINUOUS_VM64 = 0;
  public static final int CHECK_REASON_ON_LARGE_MEM_ALLOC = 31;
  public static final int CHECK_REASON_ON_NATIVE_CRITICAL = 1004;
  public static final int CHECK_REASON_ON_NATIVE_GLOOM = 1003;
  public static final int CHECK_REASON_ON_NATIVE_IDLE_REQUEST = 1002;
  public static final int CHECK_REASON_ON_NATIVE_REQUEST = 1000;
  public static final int CHECK_REASON_ON_NATIVE_V8_REQUEST = 1001;
  public static final int CHECK_REASON_ON_OOM_HANDLE = 30;
  public static final int CHECK_REASON_ON_PAGE_LOAD_FINISHED = 2;
  public static final int CHECK_REASON_ON_PAGE_SHOW = 3;
  public static final int CHECK_REASON_ON_PRUNE_MEMORY_CACHE = 1;
  public static final int CHECK_REASON_ON_RESUME = 2001;
  public static final int CHECK_REASON_ON_TIMER = 4;
  public static final int CHECK_REASON_ON_TRIM_MEMORY = 5;
  private static boolean FLAG_MEMORY_CHECKER = true;
  private static int MIN_AVAILABLE_VM = 0;
  private static int MIN_AVAILABLE_VM64 = 0;
  private static int MIN_CONTINUOUS_VM = 0;
  private static final String PROC_CPU_INFO_PATH = "/proc/cpuinfo";
  private static int RESERVED_CONTINUOUS_VM = 0;
  private static final String SHARE_PREFERENCES_NAME = "tbs_public_settings";
  private static int SYS_BITS = 0;
  private static int SYS_VSS_LIMIT_VM32 = 0;
  private static final int TRIM_MEMORY_NONE = 0;
  private static final int TRIM_MEMORY_PM = 2;
  private static final int TRIM_MEMORY_VM = 1;
  private static int sAvailableMemoryThreshold = 0;
  private static final String sAvailableMemoryThresholdKey = "availableMemoryThreshold";
  private static boolean sInitWithX5MemConfig = false;
  private static Future<a> sMemoryCheckHandlerFuture;
  private static volatile a sMemoryCheckHandlerInstance;
  private static HandlerThread sMemoryCheckThread;
  private static List<Integer> sMemoryThresholdList = new LinkedList();
  private static int sMemoryThresholdListMaxCount = 10;
  private static boolean sNativeMemUpdated;
  private static SharedPreferences sPreference = null;
  private static int sRunningMemAgressiveGrowthLimit;
  private static int sRunningMemGentleGrowthLimit;
  private static boolean sRunningMemGrowthLimitInited;
  
  static
  {
    sAvailableMemoryThreshold = -1;
    sRunningMemAgressiveGrowthLimit = -1;
    sRunningMemGentleGrowthLimit = -1;
    sRunningMemGrowthLimitInited = false;
    sInitWithX5MemConfig = false;
    sMemoryCheckThread = null;
    sMemoryCheckHandlerFuture = createMemoryCheckHandler();
    sMemoryCheckHandlerInstance = null;
    sNativeMemUpdated = false;
    MIN_CONTINUOUS_VM = 51200;
    SYS_BITS = -1;
    MIN_AVAILABLE_VM = 307200;
    MIN_AVAILABLE_VM64 = 819200;
    RESERVED_CONTINUOUS_VM = 102400;
    CHECK_AVAILABLE_CONTINUOUS_VM64 = 1048576;
    CHECK_AVAILABLE_CONTINUOUS_VM32 = 512000;
    SYS_VSS_LIMIT_VM32 = 3481600;
  }
  
  public static void check(int paramInt)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("check: checkReason = ");
    ((StringBuilder)localObject).append(paramInt);
    c.a("MemoryChecker", ((StringBuilder)localObject).toString());
    localObject = getMemoryCheckHandler();
    if (localObject != null) {
      ((a)localObject).a(paramInt);
    }
  }
  
  @CalledByNative
  public static void checkFormNative(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("checkFormNative: checkReason = ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(";FLAG_MEMORY_CHECKER=");
    localStringBuilder.append(FLAG_MEMORY_CHECKER);
    c.a("MemoryChecker", localStringBuilder.toString());
    if (!FLAG_MEMORY_CHECKER)
    {
      c.a("MemoryChecker", "checkFormNative()  do nothing  return");
      return;
    }
    try
    {
      check(paramInt);
      if ((paramInt == 1003) || (paramInt == 1004))
      {
        trim(80);
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  private static void checkOnMemoryCheckThread(int paramInt)
  {
    HashMap localHashMap = new HashMap();
    Object localObject1 = MemoryInfo.getNativeMemInfo();
    Object localObject2 = new b(paramInt, localHashMap);
    int j = 0;
    int i = 0;
    int k;
    int m;
    for (;;)
    {
      k = 1;
      if (i == -1) {
        break;
      }
      m = ((b)localObject2).a();
      if (m == 0) {
        break;
      }
      if (m == 1) {
        i = TencentMemoryPressureListener.maybeNotifyMemoryPresureInDefaultOrder(80, i, true);
      } else {
        i = TencentMemoryPressureListener.maybeNotifyMemoryPresureInDefaultOrder(100, i, false);
      }
    }
    if (((b)localObject2).a())
    {
      localObject2 = MemoryInfo.getNativeMemInfo();
      m = ((MemoryInfo.NativeMemInfo)localObject1).getCachedPages() + ((MemoryInfo.NativeMemInfo)localObject1).getDeactiveNaviViews();
      int n = ((MemoryInfo.NativeMemInfo)localObject1).getCachedPages() - ((MemoryInfo.NativeMemInfo)localObject2).getCachedPages() + (((MemoryInfo.NativeMemInfo)localObject1).getPrunedNaviViews() - ((MemoryInfo.NativeMemInfo)localObject2).getPrunedNaviViews());
      i = j;
      if (needTrimMemory(paramInt, null) == 0) {
        i = 1;
      }
      if (i != 0) {
        paramInt = k;
      } else {
        paramInt = -1;
      }
      MemoryInfo.updateTrimStatus(paramInt);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("trimMemory: trimablePages = ");
      ((StringBuilder)localObject1).append(m);
      ((StringBuilder)localObject1).append(", trimPages = ");
      ((StringBuilder)localObject1).append(n);
      ((StringBuilder)localObject1).append(", trimSatus = ");
      ((StringBuilder)localObject1).append(paramInt);
      c.a("MemoryChecker", ((StringBuilder)localObject1).toString());
      localHashMap.put("trimablePages", Integer.toString(m));
      localHashMap.put("trimPages", Integer.toString(n));
      localHashMap.put("trimSatus", Integer.toString(paramInt));
      a.a("MTT_CORE_MEMORY_CHECKER_TRIM", localHashMap);
      if (paramInt == -1)
      {
        localHashMap = new HashMap();
        localHashMap.put("urls", e.b());
        a.a("MTT_CORE_MEMORY_EXHAUSTED_URLS", localHashMap);
      }
    }
    MemoryInfo.updateMemInfoForReport();
  }
  
  private static Future<a> createMemoryCheckHandler()
  {
    BrowserExecutorSupplier.getInstance().postForTimeoutTasks(new Callable()
    {
      public MemoryChecker.a a()
        throws Exception
      {
        try
        {
          MemoryChecker.access$002(new HandlerThread("MemoryCheckThread"));
          MemoryChecker.sMemoryCheckThread.start();
          MemoryChecker.a locala = new MemoryChecker.a(MemoryChecker.sMemoryCheckThread.getLooper());
          return locala;
        }
        catch (Throwable localThrowable)
        {
          c.a(localThrowable);
        }
        return null;
      }
    });
  }
  
  private static int getAvailablePmThresholdMem()
  {
    int j = getAvailableThresholdMem();
    int i = j;
    if (j < MemoryInfo.getThresholdMem()) {
      i = MemoryInfo.getThresholdMem();
    }
    return i;
  }
  
  private static int getAvailableThresholdMem()
  {
    int i = sAvailableMemoryThreshold;
    if (i != -1) {
      return i;
    }
    Object localObject1 = sPreference;
    int j = 0;
    if (localObject1 == null) {
      sPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("tbs_public_settings", 0);
    }
    localObject1 = sPreference.getString("availableMemoryThreshold", "");
    if (!((String)localObject1).isEmpty())
    {
      localObject1 = ((String)localObject1).split(";");
      int m = localObject1.length;
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("getAvailableThresholdMem: strArray.length = ");
      ((StringBuilder)localObject2).append(localObject1.length);
      c.a("MemoryChecker", ((StringBuilder)localObject2).toString());
      for (i = 0;; i = k)
      {
        k = i;
        if (j >= m) {
          break;
        }
        k = i;
        if (!localObject1[j].isEmpty())
        {
          localObject2 = Integer.valueOf(localObject1[j]);
          sMemoryThresholdList.add(localObject2);
          k = i + ((Integer)localObject2).intValue() / m;
        }
        j += 1;
      }
    }
    int k = MemoryInfo.getThresholdMem();
    sAvailableMemoryThreshold = k;
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("getAvailableThresholdMem: sAvailableMemoryThreshold = ");
    ((StringBuilder)localObject1).append(sAvailableMemoryThreshold);
    ((StringBuilder)localObject1).append(", systemThreshold = ");
    ((StringBuilder)localObject1).append(MemoryInfo.getThresholdMem());
    c.a("MemoryChecker", ((StringBuilder)localObject1).toString());
    return k;
  }
  
  private static int getAvailableVmThresholdMem()
  {
    int i = getAvailableThresholdMem();
    if (SYS_BITS == -1) {
      if (is64Bit()) {
        SYS_BITS = 1;
      } else {
        SYS_BITS = 0;
      }
    }
    int j = SYS_BITS;
    if (j == 0)
    {
      j = MIN_AVAILABLE_VM;
      if (i < j) {
        return j;
      }
    }
    else if (j == 1)
    {
      j = MIN_AVAILABLE_VM64;
      if (i < j) {
        return j;
      }
    }
    return i;
  }
  
  private static int getConsumeMem()
  {
    Debug.MemoryInfo localMemoryInfo = MemoryInfo.getDebugMemInfo();
    return localMemoryInfo.dalvikPss + localMemoryInfo.otherPss + getNativeAlloc();
  }
  
  private static a getMemoryCheckHandler()
  {
    if (sMemoryCheckHandlerInstance != null) {
      return sMemoryCheckHandlerInstance;
    }
    try
    {
      if (sMemoryCheckHandlerInstance != null)
      {
        a locala = sMemoryCheckHandlerInstance;
        return locala;
      }
      try
      {
        sMemoryCheckHandlerInstance = (a)sMemoryCheckHandlerFuture.get(100L, TimeUnit.MILLISECONDS);
        if (sMemoryCheckHandlerInstance != null)
        {
          sMemoryCheckHandlerInstance.a();
          a.a(sMemoryCheckHandlerInstance);
          a.b(sMemoryCheckHandlerInstance);
        }
      }
      catch (Throwable localThrowable)
      {
        c.a(localThrowable);
      }
      return sMemoryCheckHandlerInstance;
    }
    finally {}
  }
  
  private static int getNativeAlloc()
  {
    return MemoryInfo.getNativeMemInfo().getTotalAlloc();
  }
  
  private static int getRunningMemAgressiveGrowthLimit()
  {
    int i = com.tencent.smtt.browser.x5.a.a();
    if (i > 0) {
      return i;
    }
    if (!sRunningMemGrowthLimitInited) {
      initRunningMem();
    }
    return sRunningMemAgressiveGrowthLimit;
  }
  
  private static int getRunningMemGentleGrowthLimit()
  {
    int i = com.tencent.smtt.browser.x5.a.b();
    if (i > 0) {
      return i;
    }
    if (!sRunningMemGrowthLimitInited) {
      initRunningMem();
    }
    return sRunningMemGentleGrowthLimit;
  }
  
  private static void initRunningMem()
  {
    int i = MemoryInfo.getTotalRAMMemory();
    if (i == 0)
    {
      sRunningMemAgressiveGrowthLimit = 100000;
      sRunningMemGentleGrowthLimit = 100000;
    }
    else if (i < 200000)
    {
      sRunningMemAgressiveGrowthLimit = 80000;
      sRunningMemGentleGrowthLimit = 80000;
    }
    else if (i < 300000)
    {
      sRunningMemAgressiveGrowthLimit = 100000;
      sRunningMemGentleGrowthLimit = 100000;
    }
    else if (i < 500000)
    {
      sRunningMemAgressiveGrowthLimit = i * 4 / 10;
      sRunningMemGentleGrowthLimit = i * 6 / 10;
    }
    else if (i < 1000000)
    {
      sRunningMemAgressiveGrowthLimit = i * 4 / 10;
      sRunningMemGentleGrowthLimit = i * 6 / 10;
    }
    else
    {
      sRunningMemAgressiveGrowthLimit = i * 4 / 10;
      sRunningMemGentleGrowthLimit = i * 7 / 10;
    }
    loadX5MemConfig();
    sRunningMemGrowthLimitInited = true;
  }
  
  /* Error */
  private static boolean is64Bit()
  {
    // Byte code:
    //   0: new 486	java/io/File
    //   3: dup
    //   4: ldc 57
    //   6: invokespecial 489	java/io/File:<init>	(Ljava/lang/String;)V
    //   9: astore_2
    //   10: aload_2
    //   11: invokevirtual 492	java/io/File:exists	()Z
    //   14: ifeq +298 -> 312
    //   17: new 494	java/io/FileInputStream
    //   20: dup
    //   21: aload_2
    //   22: invokespecial 497	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   25: astore_3
    //   26: new 499	java/io/BufferedReader
    //   29: dup
    //   30: new 501	java/io/InputStreamReader
    //   33: dup
    //   34: aload_3
    //   35: invokespecial 504	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   38: sipush 512
    //   41: invokespecial 507	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   44: astore_2
    //   45: aload_2
    //   46: astore 4
    //   48: aload_3
    //   49: astore 5
    //   51: aload_2
    //   52: invokevirtual 510	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   55: astore 6
    //   57: aload 6
    //   59: ifnull +115 -> 174
    //   62: aload_2
    //   63: astore 4
    //   65: aload_3
    //   66: astore 5
    //   68: aload 6
    //   70: invokevirtual 513	java/lang/String:length	()I
    //   73: ifle +101 -> 174
    //   76: aload_2
    //   77: astore 4
    //   79: aload_3
    //   80: astore 5
    //   82: aload 6
    //   84: getstatic 519	java/util/Locale:US	Ljava/util/Locale;
    //   87: invokevirtual 523	java/lang/String:toLowerCase	(Ljava/util/Locale;)Ljava/lang/String;
    //   90: ldc_w 525
    //   93: invokevirtual 529	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   96: ifeq +78 -> 174
    //   99: aload_2
    //   100: astore 4
    //   102: aload_3
    //   103: astore 5
    //   105: invokestatic 532	com/tencent/smtt/memory/b:a	()I
    //   108: istore_0
    //   109: aload_2
    //   110: astore 4
    //   112: aload_3
    //   113: astore 5
    //   115: getstatic 145	com/tencent/smtt/memory/MemoryChecker:SYS_VSS_LIMIT_VM32	I
    //   118: istore_1
    //   119: iload_0
    //   120: iload_1
    //   121: if_icmple +28 -> 149
    //   124: aload_2
    //   125: invokevirtual 535	java/io/BufferedReader:close	()V
    //   128: goto +8 -> 136
    //   131: astore_2
    //   132: aload_2
    //   133: invokevirtual 536	java/lang/Exception:printStackTrace	()V
    //   136: aload_3
    //   137: invokevirtual 539	java/io/InputStream:close	()V
    //   140: iconst_1
    //   141: ireturn
    //   142: astore_2
    //   143: aload_2
    //   144: invokevirtual 536	java/lang/Exception:printStackTrace	()V
    //   147: iconst_1
    //   148: ireturn
    //   149: aload_2
    //   150: invokevirtual 535	java/io/BufferedReader:close	()V
    //   153: goto +8 -> 161
    //   156: astore_2
    //   157: aload_2
    //   158: invokevirtual 536	java/lang/Exception:printStackTrace	()V
    //   161: aload_3
    //   162: invokevirtual 539	java/io/InputStream:close	()V
    //   165: iconst_0
    //   166: ireturn
    //   167: astore_2
    //   168: aload_2
    //   169: invokevirtual 536	java/lang/Exception:printStackTrace	()V
    //   172: iconst_0
    //   173: ireturn
    //   174: aload_2
    //   175: invokevirtual 535	java/io/BufferedReader:close	()V
    //   178: goto +8 -> 186
    //   181: astore_2
    //   182: aload_2
    //   183: invokevirtual 536	java/lang/Exception:printStackTrace	()V
    //   186: aload_3
    //   187: invokevirtual 539	java/io/InputStream:close	()V
    //   190: iconst_0
    //   191: ireturn
    //   192: astore 6
    //   194: goto +32 -> 226
    //   197: astore_2
    //   198: aconst_null
    //   199: astore 4
    //   201: goto +73 -> 274
    //   204: astore 6
    //   206: aconst_null
    //   207: astore_2
    //   208: goto +18 -> 226
    //   211: astore_2
    //   212: aconst_null
    //   213: astore_3
    //   214: aload_3
    //   215: astore 4
    //   217: goto +57 -> 274
    //   220: astore 6
    //   222: aconst_null
    //   223: astore_3
    //   224: aload_3
    //   225: astore_2
    //   226: aload_2
    //   227: astore 4
    //   229: aload_3
    //   230: astore 5
    //   232: aload 6
    //   234: invokevirtual 272	java/lang/Throwable:printStackTrace	()V
    //   237: aload_2
    //   238: ifnull +15 -> 253
    //   241: aload_2
    //   242: invokevirtual 535	java/io/BufferedReader:close	()V
    //   245: goto +8 -> 253
    //   248: astore_2
    //   249: aload_2
    //   250: invokevirtual 536	java/lang/Exception:printStackTrace	()V
    //   253: aload_3
    //   254: ifnull +58 -> 312
    //   257: aload_3
    //   258: invokevirtual 539	java/io/InputStream:close	()V
    //   261: iconst_0
    //   262: ireturn
    //   263: astore_2
    //   264: aload_2
    //   265: invokevirtual 536	java/lang/Exception:printStackTrace	()V
    //   268: iconst_0
    //   269: ireturn
    //   270: astore_2
    //   271: aload 5
    //   273: astore_3
    //   274: aload 4
    //   276: ifnull +18 -> 294
    //   279: aload 4
    //   281: invokevirtual 535	java/io/BufferedReader:close	()V
    //   284: goto +10 -> 294
    //   287: astore 4
    //   289: aload 4
    //   291: invokevirtual 536	java/lang/Exception:printStackTrace	()V
    //   294: aload_3
    //   295: ifnull +15 -> 310
    //   298: aload_3
    //   299: invokevirtual 539	java/io/InputStream:close	()V
    //   302: goto +8 -> 310
    //   305: astore_3
    //   306: aload_3
    //   307: invokevirtual 536	java/lang/Exception:printStackTrace	()V
    //   310: aload_2
    //   311: athrow
    //   312: iconst_0
    //   313: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   108	14	0	i	int
    //   118	4	1	j	int
    //   9	116	2	localObject1	Object
    //   131	2	2	localException1	Exception
    //   142	8	2	localException2	Exception
    //   156	2	2	localException3	Exception
    //   167	8	2	localException4	Exception
    //   181	2	2	localException5	Exception
    //   197	1	2	localObject2	Object
    //   207	1	2	localObject3	Object
    //   211	1	2	localObject4	Object
    //   225	17	2	localObject5	Object
    //   248	2	2	localException6	Exception
    //   263	2	2	localException7	Exception
    //   270	41	2	localObject6	Object
    //   25	274	3	localObject7	Object
    //   305	2	3	localException8	Exception
    //   46	234	4	localObject8	Object
    //   287	3	4	localException9	Exception
    //   49	223	5	localObject9	Object
    //   55	28	6	str	String
    //   192	1	6	localThrowable1	Throwable
    //   204	1	6	localThrowable2	Throwable
    //   220	13	6	localThrowable3	Throwable
    // Exception table:
    //   from	to	target	type
    //   124	128	131	java/lang/Exception
    //   136	140	142	java/lang/Exception
    //   149	153	156	java/lang/Exception
    //   161	165	167	java/lang/Exception
    //   174	178	181	java/lang/Exception
    //   51	57	192	java/lang/Throwable
    //   68	76	192	java/lang/Throwable
    //   82	99	192	java/lang/Throwable
    //   105	109	192	java/lang/Throwable
    //   115	119	192	java/lang/Throwable
    //   26	45	197	finally
    //   26	45	204	java/lang/Throwable
    //   17	26	211	finally
    //   17	26	220	java/lang/Throwable
    //   241	245	248	java/lang/Exception
    //   186	190	263	java/lang/Exception
    //   257	261	263	java/lang/Exception
    //   51	57	270	finally
    //   68	76	270	finally
    //   82	99	270	finally
    //   105	109	270	finally
    //   115	119	270	finally
    //   232	237	270	finally
    //   279	284	287	java/lang/Exception
    //   298	302	305	java/lang/Exception
  }
  
  private static void loadX5MemConfig()
  {
    if (sInitWithX5MemConfig)
    {
      Object localObject1 = null;
      try
      {
        InputStream localInputStream = ContextHolder.getInstance().getApplicationContext().getAssets().open("x5memcfg.properties");
        localObject1 = localInputStream;
        Object localObject2 = new Properties();
        localObject1 = localInputStream;
        ((Properties)localObject2).load(localInputStream);
        localObject1 = localInputStream;
        int i = Integer.parseInt(((Properties)localObject2).getProperty("RunningMemAgressiveGrowthLimit", String.valueOf(sRunningMemAgressiveGrowthLimit)));
        localObject1 = localInputStream;
        StringBuilder localStringBuilder = new StringBuilder();
        localObject1 = localInputStream;
        localStringBuilder.append("loadX5MemConfig: limit = ");
        localObject1 = localInputStream;
        localStringBuilder.append(i);
        localObject1 = localInputStream;
        localStringBuilder.append(", sRunningMemAgressiveGrowthLimit = ");
        localObject1 = localInputStream;
        localStringBuilder.append(sRunningMemAgressiveGrowthLimit);
        localObject1 = localInputStream;
        c.a("MemoryChecker", localStringBuilder.toString());
        if (i > 0)
        {
          localObject1 = localInputStream;
          sRunningMemAgressiveGrowthLimit = i;
        }
        localObject1 = localInputStream;
        i = Integer.parseInt(((Properties)localObject2).getProperty("RunningMemGentleGrowthLimit", String.valueOf(sRunningMemGentleGrowthLimit)));
        localObject1 = localInputStream;
        localObject2 = new StringBuilder();
        localObject1 = localInputStream;
        ((StringBuilder)localObject2).append("loadX5MemConfig: limit = ");
        localObject1 = localInputStream;
        ((StringBuilder)localObject2).append(i);
        localObject1 = localInputStream;
        ((StringBuilder)localObject2).append(", sRunningMemGentleGrowthLimit = ");
        localObject1 = localInputStream;
        ((StringBuilder)localObject2).append(sRunningMemGentleGrowthLimit);
        localObject1 = localInputStream;
        c.a("MemoryChecker", ((StringBuilder)localObject2).toString());
        localObject1 = localInputStream;
        if (i > 0)
        {
          localObject1 = localInputStream;
          sRunningMemGentleGrowthLimit = i;
          localObject1 = localInputStream;
        }
      }
      catch (Throwable localThrowable3)
      {
        c.a(localThrowable3);
        c.a("MemoryChecker", "loadX5MemConfig: load error!!");
      }
      if (localObject1 != null) {
        try
        {
          ((InputStream)localObject1).close();
        }
        catch (Throwable localThrowable1)
        {
          c.a(localThrowable1);
          c.a("MemoryChecker", "loadX5MemConfig: close error!!");
        }
      }
    }
    try
    {
      SharedPreferences.Editor localEditor = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_mem_cfg", 0).edit();
      localEditor.putInt("RunningMemAgressiveGrowthLimit", sRunningMemAgressiveGrowthLimit);
      localEditor.putInt("sRunningMemGentleGrowthLimit", sRunningMemGentleGrowthLimit);
      localEditor.commit();
      return;
    }
    catch (Throwable localThrowable2)
    {
      c.a(localThrowable2);
      c.a("MemoryChecker", "loadX5MemConfig: store error!!");
    }
  }
  
  private static native void nativeSetupOOMHandler();
  
  private static native boolean nativeTryMapMemory(int paramInt);
  
  private static native boolean nativeTryReserveMemory(int paramInt);
  
  private static int needTrimMemory(int paramInt, HashMap<String, String> paramHashMap)
  {
    int i6 = d.a.a().a();
    Object localObject = MemoryInfo.getNativeMemInfo();
    int i7 = MemoryInfo.getAvailMem();
    int i = ((MemoryInfo.NativeMemInfo)localObject).getdiscardableMemPinned();
    int i8 = MemoryInfo.getCurPss() + i;
    int i9 = getRunningMemAgressiveGrowthLimit();
    int i10 = getRunningMemGentleGrowthLimit();
    int i11 = getAvailableThresholdMem();
    int i12 = getAvailablePmThresholdMem();
    int i13 = getAvailableVmThresholdMem();
    int i14 = ((MemoryInfo.NativeMemInfo)localObject).getTotalAlloc();
    if (i8 >= i10) {
      i = 1;
    } else {
      i = 0;
    }
    boolean bool2 = SMTTAdaptation.getIsBuildForArch64();
    int i5 = 2;
    int k;
    int m;
    int i3;
    if ((i == 0) && (!bool2))
    {
      int i4 = b.a();
      if (i4 != -1) {
        k = i4;
      } else {
        k = 3145728;
      }
      k -= i6;
      localObject = getMemoryCheckHandler();
      if (SYS_BITS == 1)
      {
        if (k <= CHECK_AVAILABLE_CONTINUOUS_VM64)
        {
          if (localObject != null) {
            ((a)localObject).c();
          }
        }
        else if (localObject != null) {
          ((a)localObject).d();
        }
      }
      else if (k <= CHECK_AVAILABLE_CONTINUOUS_VM32)
      {
        if (localObject != null) {
          ((a)localObject).c();
        }
      }
      else if (localObject != null) {
        ((a)localObject).d();
      }
      if (k > 0)
      {
        if (k <= i13) {
          n = 1;
        } else {
          n = 0;
        }
        i1 = 2;
        i2 = 1;
      }
      else if (i4 != -1)
      {
        i1 = 20;
        i2 = 1;
        n = i;
      }
      else
      {
        i1 = 1;
        i2 = 2;
        n = i;
      }
      m = n;
      i3 = i4;
      i = i1;
      k = i2;
      if (n == 0)
      {
        m = n;
        i3 = i4;
        i = i1;
        k = i2;
        if (i4 == -1)
        {
          if (i14 > 1000000) {
            m = 1;
          } else {
            m = 0;
          }
          i = 21;
          k = 1;
          i3 = i4;
        }
      }
    }
    else
    {
      m = i;
      i3 = -1;
      i = 1;
      k = 2;
    }
    int i1 = m;
    int i2 = i;
    int n = k;
    if (m == 0)
    {
      i1 = m;
      i2 = i;
      n = k;
      if (i8 <= 0)
      {
        if (i14 > 1000000) {
          i1 = 1;
        } else {
          i1 = 0;
        }
        i2 = 22;
        n = 1;
      }
    }
    i = i1;
    boolean bool1;
    if (i1 == 0)
    {
      bool1 = nativeTryMapMemory(MIN_CONTINUOUS_VM) ^ true;
      i2 = 3;
      n = 1;
    }
    if (!bool1)
    {
      bool1 = nativeTryReserveMemory(RESERVED_CONTINUOUS_VM) ^ true;
      m = 31;
      n = 1;
    }
    else
    {
      m = i2;
    }
    if ((!bool1) && ((i8 >= i9) || (i8 == 0)))
    {
      if (paramInt != 5)
      {
        bool1 = false;
        k = 0;
        m = 5;
      }
      else
      {
        if (i7 <= i12) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        m = 4;
        k = i5;
      }
    }
    else {
      k = n;
    }
    if (!bool1)
    {
      n = 6;
      i1 = MemoryInfo.needTrimMemoryFromNative();
      m = n;
      k = i1;
      if (i1 != 0)
      {
        bool1 = true;
        m = n;
        k = i1;
      }
    }
    if (!bool1)
    {
      m = 0;
      k = 0;
    }
    MemoryInfo.updateTrimMemory(i6, i8, i7, m, paramInt);
    int j = MemoryInfo.getTotalRAMMemory();
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("needTrimMemory: trimFrom = ");
    ((StringBuilder)localObject).append(m);
    ((StringBuilder)localObject).append(", avail = ");
    ((StringBuilder)localObject).append(i7);
    ((StringBuilder)localObject).append(", vmSize = ");
    ((StringBuilder)localObject).append(i6);
    ((StringBuilder)localObject).append(", vssLimit = ");
    ((StringBuilder)localObject).append(i3);
    ((StringBuilder)localObject).append(", totalPss = ");
    ((StringBuilder)localObject).append(i8);
    ((StringBuilder)localObject).append(", pssLimit = ");
    ((StringBuilder)localObject).append(j);
    ((StringBuilder)localObject).append(", agressiveGrowthLimit = ");
    ((StringBuilder)localObject).append(i9);
    ((StringBuilder)localObject).append(", gentleGrowthLimit = ");
    ((StringBuilder)localObject).append(i10);
    ((StringBuilder)localObject).append(", availablePmThreshold = ");
    ((StringBuilder)localObject).append(i12);
    ((StringBuilder)localObject).append(", availableVmThreshold = ");
    ((StringBuilder)localObject).append(i13);
    c.a("MemoryChecker", ((StringBuilder)localObject).toString());
    if ((m > 0) && (paramHashMap != null))
    {
      paramHashMap.put("sdkVer", Integer.toString(Build.VERSION.SDK_INT));
      paramHashMap.put("checkReason", Integer.toString(paramInt));
      paramHashMap.put("trimFrom", Integer.toString(m));
      paramHashMap.put("nativeAlloc", Integer.toString(i14));
      paramHashMap.put("avail", Integer.toString(i7));
      paramHashMap.put("vmSize", Integer.toString(i6));
      paramHashMap.put("vssLimit", Integer.toString(i3));
      paramHashMap.put("arch64", Boolean.toString(bool2));
      paramHashMap.put("totalPss", Integer.toString(i8));
      paramHashMap.put("pssLimit", Integer.toString(j));
      paramHashMap.put("agressiveGrowthLimit", Integer.toString(i9));
      paramHashMap.put("gentleGrowthLimit", Integer.toString(i10));
      paramHashMap.put("availableThreshold", Integer.toString(i11));
      paramHashMap.put("availablePmThreshold", Integer.toString(i12));
      paramHashMap.put("availableVmThreshold", Integer.toString(i13));
    }
    if (k == 0)
    {
      com.tencent.smtt.webkit.e.a().a();
      return k;
    }
    com.tencent.smtt.webkit.e.a().b();
    return k;
  }
  
  private static boolean prepareMemory()
  {
    return nativeTryReserveMemory(RESERVED_CONTINUOUS_VM);
  }
  
  public static void reportBeacon(a parama)
  {
    a locala = getMemoryCheckHandler();
    if (locala != null) {
      locala.a(parama);
    }
  }
  
  public static void reportUIHidden()
  {
    a locala = getMemoryCheckHandler();
    if (locala != null) {
      locala.b();
    }
  }
  
  public static void reportWebStatus(e parame)
  {
    a locala = getMemoryCheckHandler();
    if (locala != null) {
      locala.a(parame);
    }
  }
  
  public static void resumeTimerCheck()
  {
    FLAG_MEMORY_CHECKER = true;
    check(2001);
  }
  
  public static void stopTimerCheck()
  {
    FLAG_MEMORY_CHECKER = false;
    a locala = getMemoryCheckHandler();
    if (locala != null) {
      locala.d();
    }
  }
  
  private static int toAndroidPressureLevel(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return 5;
    case 4: 
      return 110;
    case 3: 
      return 100;
    case 2: 
      return 15;
    }
    return 10;
  }
  
  public static void trim(int paramInt)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("trim: level = ");
    ((StringBuilder)localObject).append(paramInt);
    c.a("MemoryChecker", ((StringBuilder)localObject).toString());
    localObject = getMemoryCheckHandler();
    if (localObject != null) {
      ((a)localObject).a(paramInt, 2, null);
    }
  }
  
  public static void trimInOrder(int paramInt, int[] paramArrayOfInt)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("check: forceTrim = ");
    ((StringBuilder)localObject).append(paramInt);
    c.a("MemoryChecker", ((StringBuilder)localObject).toString());
    localObject = getMemoryCheckHandler();
    if (localObject != null) {
      ((a)localObject).a(paramInt, 2, paramArrayOfInt);
    }
  }
  
  @CalledByNative
  public static void trimInOrderFromNative(int paramInt, int[] paramArrayOfInt)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("trimInOrderFromNative: level = ");
    ((StringBuilder)localObject).append(paramInt);
    c.a("MemoryChecker", ((StringBuilder)localObject).toString());
    localObject = getMemoryCheckHandler();
    if (localObject != null) {
      ((a)localObject).a(toAndroidPressureLevel(paramInt), 2, paramArrayOfInt);
    }
  }
  
  private static void trimOnMemoryCheckThread(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    MemoryInfo.updateTrimLevel(paramInt1, false, true);
    MemoryInfo.updateMemInfoForReport();
    if (paramArrayOfInt != null) {
      TencentMemoryPressureListener.maybeNotifyMemoryPresure(paramInt1);
    }
    boolean bool;
    for (int i = 0; i != -1; i = TencentMemoryPressureListener.maybeNotifyMemoryPresureInOrder(paramInt1, paramArrayOfInt, i, bool)) {
      if (paramInt2 == 1) {
        bool = true;
      } else {
        bool = false;
      }
    }
  }
  
  public static void trimVM(int paramInt)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("trimVM: level = ");
    ((StringBuilder)localObject).append(paramInt);
    c.a("MemoryChecker", ((StringBuilder)localObject).toString());
    localObject = getMemoryCheckHandler();
    if (localObject != null) {
      ((a)localObject).a(paramInt, 1, null);
    }
  }
  
  public static void updateAvailableMemoryThreshold(int paramInt)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("updateAvailableMemoryThreshold: sAvailableMemoryThreshold = ");
    ((StringBuilder)localObject1).append(getAvailableThresholdMem());
    c.a("MemoryChecker", ((StringBuilder)localObject1).toString());
    if (sMemoryThresholdList.size() >= sMemoryThresholdListMaxCount) {
      sMemoryThresholdList.remove(0);
    }
    sMemoryThresholdList.add(Integer.valueOf(paramInt));
    int k = sMemoryThresholdList.size();
    localObject1 = "";
    int i = 0;
    int j = 0;
    while (i < k)
    {
      localObject2 = (Integer)sMemoryThresholdList.get(i);
      j += ((Integer)localObject2).intValue() / k;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append((String)localObject1);
      localStringBuilder.append(((Integer)localObject2).toString());
      localStringBuilder.append(";");
      localObject1 = localStringBuilder.toString();
      i += 1;
    }
    sAvailableMemoryThreshold = j;
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("updateAvailableMemoryThreshold: threshold = ");
    ((StringBuilder)localObject2).append(paramInt);
    ((StringBuilder)localObject2).append(", sAvailableMemoryThreshold = ");
    ((StringBuilder)localObject2).append(sAvailableMemoryThreshold);
    ((StringBuilder)localObject2).append(", sMemoryThresholdList.size() = ");
    ((StringBuilder)localObject2).append(sMemoryThresholdList.size());
    c.a("MemoryChecker", ((StringBuilder)localObject2).toString());
    MemoryInfo.updateTrimThreshold(getRunningMemAgressiveGrowthLimit(), getRunningMemGentleGrowthLimit(), sAvailableMemoryThreshold);
    if (sPreference == null) {
      sPreference = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("tbs_public_settings", 0);
    }
    localObject2 = sPreference.edit();
    ((SharedPreferences.Editor)localObject2).putString("availableMemoryThreshold", (String)localObject1);
    ((SharedPreferences.Editor)localObject2).commit();
  }
  
  private static void updateBootupMemInfo()
  {
    if (!sNativeMemUpdated)
    {
      MemoryInfo.updateMemLimit(b.a(), MemoryInfo.getTotalRAMMemory());
      MemoryInfo.updateTrimThreshold(getRunningMemAgressiveGrowthLimit(), getRunningMemGentleGrowthLimit(), getAvailableThresholdMem());
      MemoryInfo.updateBootupMemory(MemoryInfo.getCurVss(), MemoryInfo.getCurPss());
    }
    sNativeMemUpdated = true;
  }
  
  private static class a
    extends Handler
  {
    private static int jdField_a_of_type_Int = -1;
    private static boolean jdField_b_of_type_Boolean = false;
    private boolean jdField_a_of_type_Boolean = false;
    private int jdField_b_of_type_Int = 0;
    private int c = 0;
    private int d = 0;
    
    public a(Looper paramLooper)
    {
      super();
      TencentMemoryPressureListener.setMemoryPressureHelper(new a(null));
      TencentMemoryPressureListener.registerMemoryTrimCallback(new b(null));
    }
    
    public static int a()
    {
      int i1 = d.a.a().a();
      Object localObject = MemoryInfo.getNativeMemInfo();
      MemoryInfo.getAvailMem();
      int i = ((MemoryInfo.NativeMemInfo)localObject).getdiscardableMemPinned();
      int i3 = MemoryInfo.getCurPss() + i;
      MemoryChecker.access$700();
      i = MemoryChecker.access$800();
      MemoryChecker.access$900();
      MemoryChecker.access$1000();
      int i5 = MemoryChecker.access$1100();
      int i4 = ((MemoryInfo.NativeMemInfo)localObject).getTotalAlloc();
      if (i3 >= i) {
        i = 1;
      } else {
        i = 0;
      }
      int n = 2;
      boolean bool2 = SMTTAdaptation.getIsBuildForArch64();
      int m = i;
      int j = n;
      if (i == 0)
      {
        m = i;
        j = n;
        if (!bool2)
        {
          int i2 = b.a();
          if (i2 != -1) {
            j = i2;
          } else {
            j = 3145728;
          }
          j -= i1;
          localObject = MemoryChecker.access$300();
          if (MemoryChecker.SYS_BITS == 1)
          {
            if (j <= MemoryChecker.CHECK_AVAILABLE_CONTINUOUS_VM64)
            {
              if (localObject != null) {
                ((a)localObject).c();
              }
            }
            else if (localObject != null) {
              ((a)localObject).d();
            }
          }
          else if (j <= MemoryChecker.CHECK_AVAILABLE_CONTINUOUS_VM32)
          {
            if (localObject != null) {
              ((a)localObject).c();
            }
          }
          else if (localObject != null) {
            ((a)localObject).d();
          }
          if (j > 0)
          {
            if (j <= i5) {
              i1 = 1;
            } else {
              i1 = 0;
            }
            n = 1;
          }
          else
          {
            i1 = i;
            if (i2 != -1)
            {
              n = 1;
              i1 = i;
            }
          }
          m = i1;
          j = n;
          if (i1 == 0)
          {
            m = i1;
            j = n;
            if (i2 == -1)
            {
              if (i4 > 1000000) {
                m = 1;
              } else {
                m = 0;
              }
              j = 1;
            }
          }
        }
      }
      i = m;
      n = j;
      if (m == 0)
      {
        i = m;
        n = j;
        if (i3 <= 0)
        {
          if (i4 > 1000000) {
            i = 1;
          } else {
            i = 0;
          }
          n = 1;
        }
      }
      j = i;
      int k;
      if (i == 0)
      {
        k = MemoryChecker.nativeTryMapMemory(MemoryChecker.MIN_CONTINUOUS_VM) ^ true;
        n = 1;
      }
      boolean bool1;
      if (k == 0)
      {
        bool1 = MemoryChecker.nativeTryReserveMemory(MemoryChecker.RESERVED_CONTINUOUS_VM) ^ true;
        n = 1;
      }
      else
      {
        bool1 = k;
      }
      if (!bool1)
      {
        m = MemoryInfo.needTrimMemoryFromNative();
        k = m;
        if (m != 0)
        {
          bool1 = true;
          k = m;
        }
      }
      else
      {
        k = n;
      }
      if (!bool1) {
        k = 0;
      }
      return k;
    }
    
    private static List<ActivityManager.RunningAppProcessInfo> a(ActivityManager paramActivityManager, int paramInt)
    {
      paramActivityManager = new Callable()
      {
        public List<ActivityManager.RunningAppProcessInfo> a()
          throws Exception
        {
          return this.a.getRunningAppProcesses();
        }
      };
      paramActivityManager = BrowserExecutorSupplier.getInstance().postForTimeoutTasks(paramActivityManager);
      long l = paramInt;
      try
      {
        paramActivityManager = (List)paramActivityManager.get(l, TimeUnit.MILLISECONDS);
        return paramActivityManager;
      }
      catch (Throwable paramActivityManager)
      {
        c.a(paramActivityManager);
      }
      return null;
    }
    
    private void a(Message paramMessage)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("handleMessage: what = ");
      ((StringBuilder)localObject).append(paramMessage.what);
      c.a("MemoryChecker", ((StringBuilder)localObject).toString());
      switch (paramMessage.what)
      {
      default: 
        
      case 8: 
        jdField_b_of_type_Boolean = true;
        j();
        return;
      case 7: 
        paramMessage = (a)paramMessage.obj;
        SmttServiceProxy.getInstance().onBeaconReport(paramMessage.a(), paramMessage.a());
        return;
      case 6: 
        c.a("MemoryChecker", "HANDLE_SETUP_OOM_HANDLER: ");
        MemoryChecker.access$2400();
        return;
      case 5: 
        c.a("MemoryChecker", "HANDLE_PREPARE_MEMORY: ");
        MemoryChecker.access$2300();
        return;
      case 4: 
        c.a("MemoryChecker", "HANDLE_TIMEER_CHECK: ");
        MemoryChecker.checkOnMemoryCheckThread(4);
        h();
        return;
      case 3: 
        c.a("MemoryChecker", "HANDLE_WEBSTATUS_CHECK: ");
        paramMessage = (e)paramMessage.obj;
        int j = paramMessage.a();
        int i = paramMessage.b();
        localObject = paramMessage.a();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("pageId = ");
        localStringBuilder.append(j);
        localStringBuilder.append(", reportStatus = ");
        localStringBuilder.append(i);
        localStringBuilder.append(", pageUrl = ");
        localStringBuilder.append((String)localObject);
        localStringBuilder.append(", loadStatus = ");
        localStringBuilder.append(paramMessage.c());
        localStringBuilder.append(", visibleStatus = ");
        localStringBuilder.append(paramMessage.d());
        c.a("MemoryChecker", localStringBuilder.toString());
        if (i == 0)
        {
          this.c = j;
          this.d = MemoryChecker.access$2200();
          j();
        }
        if ((i == 1) && (!((String)localObject).contains("about:blank")))
        {
          if ((j == this.c) && (!((String)localObject).contains("qb://home")))
          {
            j = MemoryChecker.access$2200() - this.d;
            if (j > 0) {
              MemoryChecker.updateAvailableMemoryThreshold(j);
            }
          }
          MemoryChecker.checkOnMemoryCheckThread(2);
          c.a("MemoryChecker", "startTimerCheck, from  loaded ");
          if (!jdField_b_of_type_Boolean) {
            g();
          }
        }
        if (i == 4) {
          if (paramMessage.c() != 0)
          {
            MemoryChecker.checkOnMemoryCheckThread(2);
            c.a("MemoryChecker", "startTimerCheck, from show loaded");
            jdField_b_of_type_Boolean = false;
            g();
          }
          else
          {
            c.a("MemoryChecker", "startTimerCheck, from show opening");
            i();
          }
        }
        if (i == 5)
        {
          c.a("MemoryChecker", "stopTimerCheck, from  hide");
          i();
          return;
        }
        break;
      case 2: 
        c.a("MemoryChecker", "HANDLE_MEM_TRIM: ");
        MemoryChecker.trimOnMemoryCheckThread(paramMessage.arg1, paramMessage.arg2, (int[])paramMessage.obj);
        return;
      case 1: 
        c.a("MemoryChecker", "HANDLE_MEM_CHECK: ");
        MemoryChecker.checkOnMemoryCheckThread(paramMessage.arg1);
        return;
      case 0: 
        c.a("MemoryChecker", "HANDLE_UPDATE_BOOTUP_MEMINFO: ");
        MemoryChecker.access$1900();
      }
    }
    
    private static int b()
    {
      if (jdField_a_of_type_Int == -1) {
        jdField_a_of_type_Int = Process.myPid();
      }
      return jdField_a_of_type_Int;
    }
    
    private static boolean b(Context paramContext)
    {
      boolean bool2 = false;
      try
      {
        localObject = a((ActivityManager)paramContext.getSystemService("activity"), 200);
        if (localObject == null)
        {
          c.a("MemoryChecker", "isForegroundApp lr is null");
          return false;
        }
        i = b();
        Iterator localIterator = ((List)localObject).iterator();
        do
        {
          if (!localIterator.hasNext()) {
            break;
          }
          localObject = (ActivityManager.RunningAppProcessInfo)localIterator.next();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("processName = ");
          localStringBuilder.append(((ActivityManager.RunningAppProcessInfo)localObject).processName);
          localStringBuilder.append(", packgename = ");
          localStringBuilder.append(paramContext.getPackageName());
          localStringBuilder.append(", import = ");
          localStringBuilder.append(((ActivityManager.RunningAppProcessInfo)localObject).importance);
          c.a("MemoryChecker", localStringBuilder.toString());
        } while (((ActivityManager.RunningAppProcessInfo)localObject).pid != i);
        paramContext = new StringBuilder();
        paramContext.append("isForegroundApp ret = ");
        if (((ActivityManager.RunningAppProcessInfo)localObject).importance != 100) {
          break label243;
        }
        bool1 = true;
      }
      catch (Throwable paramContext)
      {
        for (;;)
        {
          Object localObject;
          int i;
          continue;
          boolean bool1 = false;
        }
      }
      paramContext.append(bool1);
      c.a("MemoryChecker", paramContext.toString());
      i = ((ActivityManager.RunningAppProcessInfo)localObject).importance;
      bool1 = bool2;
      if (i == 100) {
        bool1 = true;
      }
      return bool1;
      c.b("MemoryChecker", "isForegroundApp error happened!!");
      c.a("MemoryChecker", "isForegroundApp return false");
      return false;
    }
    
    private void e()
    {
      obtainMessage(5).sendToTarget();
    }
    
    private void f()
    {
      obtainMessage(6).sendToTarget();
    }
    
    private void g()
    {
      if (!this.jdField_a_of_type_Boolean) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("startTimerCheck, mTimerCheckCount = ");
      localStringBuilder.append(this.jdField_b_of_type_Int);
      c.a("MemoryChecker", localStringBuilder.toString());
      if (this.jdField_b_of_type_Int == 0)
      {
        sendMessageDelayed(obtainMessage(4), 5000L);
        c.a("MemoryChecker", "startTimerCheck, now!");
      }
      this.jdField_b_of_type_Int += 1;
    }
    
    private void h()
    {
      if (!this.jdField_a_of_type_Boolean) {
        return;
      }
      sendMessageDelayed(obtainMessage(4), 5000L);
      c.a("MemoryChecker", "triggleTimerCheck, now!");
    }
    
    private void i()
    {
      if (!this.jdField_a_of_type_Boolean) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("stopTimerCheck, mTimerCheckCount = ");
      localStringBuilder.append(this.jdField_b_of_type_Int);
      c.a("MemoryChecker", localStringBuilder.toString());
      int i = this.jdField_b_of_type_Int;
      if (i == 0) {
        return;
      }
      this.jdField_b_of_type_Int = (i - 1);
      if (this.jdField_b_of_type_Int == 0)
      {
        removeMessages(4);
        c.a("MemoryChecker", "stopTimerCheck, now!");
      }
    }
    
    private void j()
    {
      if (!this.jdField_a_of_type_Boolean) {
        return;
      }
      c.a("MemoryChecker", "stopAllTimerCheck, now!");
      removeMessages(4);
      this.jdField_b_of_type_Int = 0;
    }
    
    public void a()
    {
      obtainMessage(0).sendToTarget();
    }
    
    public void a(int paramInt)
    {
      if (!hasMessages(1)) {
        obtainMessage(1, paramInt, 0).sendToTarget();
      }
    }
    
    public void a(int paramInt1, int paramInt2, int[] paramArrayOfInt)
    {
      if (a() == 1) {
        paramInt1 = 80;
      }
      if (!hasMessages(2)) {
        obtainMessage(2, paramInt1, paramInt2, paramArrayOfInt).sendToTarget();
      }
    }
    
    public void a(a parama)
    {
      obtainMessage(7, parama).sendToTarget();
    }
    
    public void a(e parame)
    {
      obtainMessage(3, parame).sendToTarget();
    }
    
    public void b()
    {
      obtainMessage(8).sendToTarget();
    }
    
    public void c()
    {
      this.jdField_a_of_type_Boolean = true;
      if (!hasMessages(4)) {
        obtainMessage(4).sendToTarget();
      }
    }
    
    public void d()
    {
      this.jdField_a_of_type_Boolean = false;
    }
    
    public void handleMessage(Message paramMessage)
    {
      try
      {
        a(paramMessage);
        return;
      }
      catch (Throwable paramMessage)
      {
        c.a(paramMessage);
      }
    }
    
    private static class a
      extends TencentMemoryPressureListener.Helper
    {
      public void trimInOrder(int paramInt, int[] paramArrayOfInt)
      {
        MemoryChecker.trimInOrder(paramInt, paramArrayOfInt);
      }
    }
    
    private static class b
      extends TencentMemoryPressureListener.MemoryTrimCallback
    {
      public void onLowMemory(Context paramContext)
      {
        MemoryInfo.updateTrimLevel(100, false, false);
        MemoryInfo.updateMemInfoForReport();
        TencentMemoryPressureListener.maybeNotifyMemoryPresure(80);
        MemoryChecker.trim(80);
      }
      
      public void onTrimMemory(Context paramContext, int paramInt)
      {
        MemoryChecker.a locala = MemoryChecker.access$300();
        if (paramInt == 20)
        {
          c.a("MemoryChecker", "report UI Hidden");
          if (locala != null) {
            locala.b();
          }
        }
        MemoryInfo.updateTrimLevel(paramInt, MemoryChecker.a.a(paramContext), false);
        MemoryInfo.updateMemInfoForReport();
        MemoryChecker.check(5);
        if (locala != null) {
          locala.b();
        }
      }
    }
  }
  
  private static class b
  {
    private int jdField_a_of_type_Int;
    private HashMap<String, String> jdField_a_of_type_JavaUtilHashMap;
    private boolean jdField_a_of_type_Boolean = true;
    private boolean b = false;
    
    public b(int paramInt, HashMap<String, String> paramHashMap)
    {
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_JavaUtilHashMap = paramHashMap;
    }
    
    public int a()
    {
      HashMap localHashMap;
      if (this.jdField_a_of_type_Boolean) {
        localHashMap = this.jdField_a_of_type_JavaUtilHashMap;
      } else {
        localHashMap = null;
      }
      this.jdField_a_of_type_Boolean = false;
      int i = MemoryChecker.needTrimMemory(this.jdField_a_of_type_Int, localHashMap);
      if ((i != 0) && (!this.b))
      {
        this.b = true;
        MemoryInfo.updateTrimStatus(2);
      }
      return i;
    }
    
    public boolean a()
    {
      return this.b;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\memory\MemoryChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */