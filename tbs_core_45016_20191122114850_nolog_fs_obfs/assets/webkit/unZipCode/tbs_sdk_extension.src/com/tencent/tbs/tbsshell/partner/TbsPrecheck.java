package com.tencent.tbs.tbsshell.partner;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.util.Log;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TbsPrecheck
{
  public static final String FLAG_DISABLE = "TBS_PRECHECK_REPORT_DISABLE";
  public static final String FLAG_FIRST = "TBS_PRECHECK_REPORT_FIRST";
  public static final String FLAG_PAGE_FINISHED = "TBS_PRECHECK_REPORT_ON_PAGE_FINISHED";
  private static String MD5_FILE = "1";
  private static int PRELOAD_X5_CRASH_COUNTER_MAX = 3;
  private static final int PRELOAD_X5_CRASH_DISABLE_INTERVAL_HOURS_DEFAULT = 48;
  private static final int PRELOAD_X5_CRASH_DISABLE_INTERVAL_HOURS_MAX = 336;
  public static final int PRELOAD_X5_DISABLE_MSG = -10000;
  private static final String TAG = "TbsPrecheck";
  private static final String TBS_DOWNLOAD_CFG_FILE = "tbs_download_config_64";
  private static final String TBS_EXTENSION_CFG_FILE = "tbs_extension_config";
  private static final String TBS_FOLDER_NAME = "tbs";
  private static final long TBS_PRECHECK_CONFIG_CHECK_PERIOD = 604800000L;
  static final String TBS_PRECHECK_DISABLE_REPORTED = "tbs_precheck_disable_reported";
  static final String TBS_PRECHECK_DISABLE_VERSION_REPORTED = "tbs_precheck_disable_version_reported";
  private static final String TBS_PRECHECK_INIT_VERSION = "tbs_precheck_init_version";
  private static final String TBS_PRECHECK_LAST_CHECK_TIME = "tbs_precheck_last_check_time";
  static final String TBS_PRELOADX5_CHECK_CFG_FILE = "tbs_preloadx5_check_and_report.conf";
  private static final String TBS_PRELOAD_X5_CHECK_FAILURE_CNT = "tbs_precheck_disable_check_failure_cnt";
  static final String TBS_PRELOAD_X5_CRASH_COUNTER = "tbs_preload_x5_crash_counter";
  private static final String TBS_PRELOAD_X5_CRASH_DISABLE_START_TIME = "tbs_preload_x5_crash_disable_start_time";
  static final String TBS_PRELOAD_X5_VERSION = "tbs_preload_x5_version";
  static final String TBS_PRIVATE_FOLDER_NAME = "core_private";
  static volatile boolean gIsPreloadX5Checked = false;
  private static boolean isPreloadX5AfterChecked = false;
  private static boolean isPreloadX5Checked = false;
  private static BufferedInputStream mBufferedConfigInputStream;
  private static File mConfFile;
  private static Properties mConfigFileProp;
  private static FileInputStream mConfigInputStream;
  
  /* Error */
  public static void checkAfterLoadX5Core(Context paramContext)
  {
    // Byte code:
    //   0: getstatic 106	com/tencent/tbs/tbsshell/partner/TbsPrecheck:isPreloadX5AfterChecked	Z
    //   3: ifeq +4 -> 7
    //   6: return
    //   7: iconst_1
    //   8: putstatic 106	com/tencent/tbs/tbsshell/partner/TbsPrecheck:isPreloadX5AfterChecked	Z
    //   11: aload_0
    //   12: invokestatic 110	com/tencent/tbs/tbsshell/partner/TbsPrecheck:getConfigFileProp	(Landroid/content/Context;)Ljava/util/Properties;
    //   15: astore_2
    //   16: new 112	java/io/BufferedOutputStream
    //   19: dup
    //   20: new 114	java/io/FileOutputStream
    //   23: dup
    //   24: aload_0
    //   25: invokestatic 118	com/tencent/tbs/tbsshell/partner/TbsPrecheck:getConfigFile	(Landroid/content/Context;)Ljava/io/File;
    //   28: invokespecial 121	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   31: invokespecial 124	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   34: astore_0
    //   35: aload_0
    //   36: astore_1
    //   37: aload_2
    //   38: ldc 65
    //   40: ldc 126
    //   42: invokevirtual 132	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   45: pop
    //   46: aload_0
    //   47: astore_1
    //   48: aload_2
    //   49: aload_0
    //   50: ldc -122
    //   52: invokevirtual 138	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   55: getstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   58: astore_1
    //   59: aload_1
    //   60: ifnull +19 -> 79
    //   63: aload_1
    //   64: invokevirtual 145	java/io/BufferedInputStream:close	()V
    //   67: aconst_null
    //   68: putstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   71: goto +8 -> 79
    //   74: astore_1
    //   75: aload_1
    //   76: invokevirtual 148	java/io/IOException:printStackTrace	()V
    //   79: aload_0
    //   80: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   83: return
    //   84: astore_2
    //   85: goto +12 -> 97
    //   88: astore_0
    //   89: aconst_null
    //   90: astore_1
    //   91: goto +83 -> 174
    //   94: astore_2
    //   95: aconst_null
    //   96: astore_0
    //   97: aload_0
    //   98: astore_1
    //   99: new 151	java/lang/StringBuilder
    //   102: dup
    //   103: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   106: astore_3
    //   107: aload_0
    //   108: astore_1
    //   109: aload_3
    //   110: ldc -102
    //   112: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: pop
    //   116: aload_0
    //   117: astore_1
    //   118: aload_3
    //   119: aload_2
    //   120: invokevirtual 162	java/lang/Throwable:getLocalizedMessage	()Ljava/lang/String;
    //   123: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: pop
    //   127: aload_0
    //   128: astore_1
    //   129: aload_3
    //   130: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   133: pop
    //   134: getstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   137: astore_1
    //   138: aload_1
    //   139: ifnull +19 -> 158
    //   142: aload_1
    //   143: invokevirtual 145	java/io/BufferedInputStream:close	()V
    //   146: aconst_null
    //   147: putstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   150: goto +8 -> 158
    //   153: astore_1
    //   154: aload_1
    //   155: invokevirtual 148	java/io/IOException:printStackTrace	()V
    //   158: aload_0
    //   159: ifnull +13 -> 172
    //   162: aload_0
    //   163: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   166: return
    //   167: astore_0
    //   168: aload_0
    //   169: invokevirtual 148	java/io/IOException:printStackTrace	()V
    //   172: return
    //   173: astore_0
    //   174: getstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   177: astore_2
    //   178: aload_2
    //   179: ifnull +19 -> 198
    //   182: aload_2
    //   183: invokevirtual 145	java/io/BufferedInputStream:close	()V
    //   186: aconst_null
    //   187: putstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   190: goto +8 -> 198
    //   193: astore_2
    //   194: aload_2
    //   195: invokevirtual 148	java/io/IOException:printStackTrace	()V
    //   198: aload_1
    //   199: ifnull +15 -> 214
    //   202: aload_1
    //   203: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   206: goto +8 -> 214
    //   209: astore_1
    //   210: aload_1
    //   211: invokevirtual 148	java/io/IOException:printStackTrace	()V
    //   214: aload_0
    //   215: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	216	0	paramContext	Context
    //   36	28	1	localObject1	Object
    //   74	2	1	localIOException1	IOException
    //   90	53	1	localObject2	Object
    //   153	50	1	localIOException2	IOException
    //   209	2	1	localIOException3	IOException
    //   15	34	2	localProperties	Properties
    //   84	1	2	localThrowable1	Throwable
    //   94	26	2	localThrowable2	Throwable
    //   177	6	2	localBufferedInputStream	BufferedInputStream
    //   193	2	2	localIOException4	IOException
    //   106	24	3	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   63	71	74	java/io/IOException
    //   37	46	84	java/lang/Throwable
    //   48	55	84	java/lang/Throwable
    //   11	35	88	finally
    //   11	35	94	java/lang/Throwable
    //   142	150	153	java/io/IOException
    //   79	83	167	java/io/IOException
    //   162	166	167	java/io/IOException
    //   37	46	173	finally
    //   48	55	173	finally
    //   99	107	173	finally
    //   109	116	173	finally
    //   118	127	173	finally
    //   129	134	173	finally
    //   182	190	193	java/io/IOException
    //   202	206	209	java/io/IOException
  }
  
  /* Error */
  public static boolean checkBeforeLoadX5Core(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: getstatic 169	com/tencent/tbs/tbsshell/partner/TbsPrecheck:isPreloadX5Checked	Z
    //   3: ifeq +5 -> 8
    //   6: iconst_1
    //   7: ireturn
    //   8: iconst_1
    //   9: putstatic 169	com/tencent/tbs/tbsshell/partner/TbsPrecheck:isPreloadX5Checked	Z
    //   12: new 151	java/lang/StringBuilder
    //   15: dup
    //   16: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   19: astore 12
    //   21: aload 12
    //   23: ldc -85
    //   25: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: pop
    //   29: aload 12
    //   31: aload_0
    //   32: invokevirtual 177	android/content/Context:getApplicationInfo	()Landroid/content/pm/ApplicationInfo;
    //   35: getfield 182	android/content/pm/ApplicationInfo:processName	Ljava/lang/String;
    //   38: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: pop
    //   42: aload 12
    //   44: ldc -72
    //   46: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: pop
    //   50: aload 12
    //   52: invokestatic 190	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   55: invokevirtual 193	java/lang/Thread:getName	()Ljava/lang/String;
    //   58: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: pop
    //   62: aload 12
    //   64: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   67: pop
    //   68: aconst_null
    //   69: astore 14
    //   71: aconst_null
    //   72: astore 13
    //   74: aload 13
    //   76: astore 12
    //   78: aload_0
    //   79: invokestatic 110	com/tencent/tbs/tbsshell/partner/TbsPrecheck:getConfigFileProp	(Landroid/content/Context;)Ljava/util/Properties;
    //   82: astore 15
    //   84: aload 13
    //   86: astore 12
    //   88: aload 15
    //   90: ldc 65
    //   92: ldc 126
    //   94: invokevirtual 197	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   97: invokestatic 203	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   100: istore_3
    //   101: aload 13
    //   103: astore 12
    //   105: aload 15
    //   107: ldc 62
    //   109: ldc -51
    //   111: invokevirtual 197	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   114: invokestatic 203	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   117: istore_2
    //   118: aload 13
    //   120: astore 12
    //   122: new 151	java/lang/StringBuilder
    //   125: dup
    //   126: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   129: astore 16
    //   131: iload_3
    //   132: iconst_1
    //   133: iadd
    //   134: istore_3
    //   135: aload 13
    //   137: astore 12
    //   139: aload 16
    //   141: iload_3
    //   142: invokevirtual 208	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: aload 13
    //   148: astore 12
    //   150: aload 16
    //   152: ldc -122
    //   154: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: pop
    //   158: aload 13
    //   160: astore 12
    //   162: aload 15
    //   164: ldc 65
    //   166: aload 16
    //   168: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   171: invokevirtual 132	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   174: pop
    //   175: aload 13
    //   177: astore 12
    //   179: new 114	java/io/FileOutputStream
    //   182: dup
    //   183: aload_0
    //   184: invokestatic 118	com/tencent/tbs/tbsshell/partner/TbsPrecheck:getConfigFile	(Landroid/content/Context;)Ljava/io/File;
    //   187: invokespecial 121	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   190: astore 16
    //   192: aload 13
    //   194: astore 12
    //   196: new 112	java/io/BufferedOutputStream
    //   199: dup
    //   200: aload 16
    //   202: invokespecial 124	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   205: astore 13
    //   207: aload 15
    //   209: aload 13
    //   211: ldc -122
    //   213: invokevirtual 138	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   216: aload_0
    //   217: invokestatic 212	com/tencent/tbs/tbsshell/partner/TbsPrecheck:getPreloadX5CrashCounterMax	(Landroid/content/Context;)I
    //   220: istore 4
    //   222: new 151	java/lang/StringBuilder
    //   225: dup
    //   226: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   229: astore 12
    //   231: aload 12
    //   233: ldc -42
    //   235: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: pop
    //   239: aload 12
    //   241: iload 4
    //   243: invokevirtual 208	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   246: pop
    //   247: aload 12
    //   249: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   252: pop
    //   253: iload 4
    //   255: iconst_1
    //   256: iadd
    //   257: istore 5
    //   259: iload_3
    //   260: iload 5
    //   262: if_icmpeq +180 -> 442
    //   265: iload_3
    //   266: iload 4
    //   268: if_icmple +10 -> 278
    //   271: iload_2
    //   272: ifeq +6 -> 278
    //   275: goto +167 -> 442
    //   278: aload 15
    //   280: ldc 68
    //   282: ldc 126
    //   284: invokevirtual 197	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   287: invokestatic 220	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   290: lstore 6
    //   292: lload 6
    //   294: lconst_0
    //   295: lcmp
    //   296: ifeq +139 -> 435
    //   299: invokestatic 226	java/lang/System:currentTimeMillis	()J
    //   302: lstore 8
    //   304: aload_0
    //   305: invokestatic 229	com/tencent/tbs/tbsshell/partner/TbsPrecheck:getPreloadX5CrashDisableIntervalHours	(Landroid/content/Context;)I
    //   308: bipush 60
    //   310: imul
    //   311: bipush 60
    //   313: imul
    //   314: sipush 1000
    //   317: imul
    //   318: istore_2
    //   319: new 151	java/lang/StringBuilder
    //   322: dup
    //   323: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   326: astore_0
    //   327: aload_0
    //   328: ldc -25
    //   330: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   333: pop
    //   334: lload 8
    //   336: lload 6
    //   338: lsub
    //   339: lstore 10
    //   341: aload_0
    //   342: lload 10
    //   344: invokevirtual 234	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   347: pop
    //   348: aload_0
    //   349: ldc -20
    //   351: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   354: pop
    //   355: aload_0
    //   356: iload_2
    //   357: invokevirtual 208	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   360: pop
    //   361: aload_0
    //   362: ldc -18
    //   364: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   367: pop
    //   368: aload_0
    //   369: lload 6
    //   371: invokevirtual 234	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   374: pop
    //   375: aload_0
    //   376: ldc -16
    //   378: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   381: pop
    //   382: aload_0
    //   383: lload 8
    //   385: invokevirtual 234	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   388: pop
    //   389: aload_0
    //   390: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   393: pop
    //   394: lload 10
    //   396: iload_2
    //   397: i2l
    //   398: lcmp
    //   399: ifge +17 -> 416
    //   402: aload 13
    //   404: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   407: iconst_0
    //   408: ireturn
    //   409: astore_0
    //   410: aload_0
    //   411: invokevirtual 148	java/io/IOException:printStackTrace	()V
    //   414: iconst_0
    //   415: ireturn
    //   416: aload 15
    //   418: ldc 68
    //   420: ldc 126
    //   422: invokevirtual 132	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   425: pop
    //   426: aload 15
    //   428: aload 16
    //   430: ldc -122
    //   432: invokevirtual 138	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   435: aload 13
    //   437: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   440: iconst_1
    //   441: ireturn
    //   442: aload_0
    //   443: aload_1
    //   444: iconst_0
    //   445: ldc 10
    //   447: invokestatic 243	com/tencent/tbs/tbsshell/partner/TbsPrecheck:startTbsCheckTask	(Landroid/content/Context;Ljava/lang/String;ZLjava/lang/String;)V
    //   450: iload_3
    //   451: iload 5
    //   453: if_icmpne +85 -> 538
    //   456: invokestatic 226	java/lang/System:currentTimeMillis	()J
    //   459: lstore 6
    //   461: lload 6
    //   463: aload 15
    //   465: ldc 68
    //   467: ldc 126
    //   469: invokevirtual 197	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   472: invokestatic 220	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   475: lsub
    //   476: aload_0
    //   477: invokestatic 229	com/tencent/tbs/tbsshell/partner/TbsPrecheck:getPreloadX5CrashDisableIntervalHours	(Landroid/content/Context;)I
    //   480: bipush 60
    //   482: imul
    //   483: bipush 60
    //   485: imul
    //   486: sipush 1000
    //   489: imul
    //   490: i2l
    //   491: lcmp
    //   492: ifle +46 -> 538
    //   495: new 151	java/lang/StringBuilder
    //   498: dup
    //   499: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   502: astore_0
    //   503: aload_0
    //   504: ldc -122
    //   506: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   509: pop
    //   510: aload_0
    //   511: lload 6
    //   513: invokevirtual 234	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   516: pop
    //   517: aload 15
    //   519: ldc 68
    //   521: aload_0
    //   522: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   525: invokevirtual 132	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   528: pop
    //   529: aload 15
    //   531: aload 16
    //   533: ldc -122
    //   535: invokevirtual 138	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   538: aload 13
    //   540: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   543: iconst_0
    //   544: ireturn
    //   545: astore_0
    //   546: aload_0
    //   547: invokevirtual 148	java/io/IOException:printStackTrace	()V
    //   550: iconst_0
    //   551: ireturn
    //   552: astore_0
    //   553: goto +84 -> 637
    //   556: astore_1
    //   557: aload 13
    //   559: astore_0
    //   560: goto +15 -> 575
    //   563: astore_0
    //   564: aload 12
    //   566: astore 13
    //   568: goto +69 -> 637
    //   571: astore_1
    //   572: aload 14
    //   574: astore_0
    //   575: aload_0
    //   576: astore 12
    //   578: new 151	java/lang/StringBuilder
    //   581: dup
    //   582: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   585: astore 13
    //   587: aload_0
    //   588: astore 12
    //   590: aload 13
    //   592: ldc -11
    //   594: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   597: pop
    //   598: aload_0
    //   599: astore 12
    //   601: aload 13
    //   603: aload_1
    //   604: invokevirtual 162	java/lang/Throwable:getLocalizedMessage	()Ljava/lang/String;
    //   607: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   610: pop
    //   611: aload_0
    //   612: astore 12
    //   614: aload 13
    //   616: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   619: pop
    //   620: aload_0
    //   621: ifnull +14 -> 635
    //   624: aload_0
    //   625: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   628: iconst_1
    //   629: ireturn
    //   630: astore_0
    //   631: aload_0
    //   632: invokevirtual 148	java/io/IOException:printStackTrace	()V
    //   635: iconst_1
    //   636: ireturn
    //   637: aload 13
    //   639: ifnull +16 -> 655
    //   642: aload 13
    //   644: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   647: goto +8 -> 655
    //   650: astore_1
    //   651: aload_1
    //   652: invokevirtual 148	java/io/IOException:printStackTrace	()V
    //   655: aload_0
    //   656: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	657	0	paramContext	Context
    //   0	657	1	paramString	String
    //   117	280	2	i	int
    //   100	354	3	j	int
    //   220	49	4	k	int
    //   257	197	5	m	int
    //   290	222	6	l1	long
    //   302	82	8	l2	long
    //   339	56	10	l3	long
    //   19	594	12	localObject1	Object
    //   72	571	13	localObject2	Object
    //   69	504	14	localObject3	Object
    //   82	448	15	localProperties	Properties
    //   129	403	16	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   402	407	409	java/io/IOException
    //   538	543	545	java/io/IOException
    //   207	253	552	finally
    //   278	292	552	finally
    //   299	334	552	finally
    //   341	394	552	finally
    //   416	435	552	finally
    //   442	450	552	finally
    //   456	538	552	finally
    //   207	253	556	java/lang/Throwable
    //   278	292	556	java/lang/Throwable
    //   299	334	556	java/lang/Throwable
    //   341	394	556	java/lang/Throwable
    //   416	435	556	java/lang/Throwable
    //   442	450	556	java/lang/Throwable
    //   456	538	556	java/lang/Throwable
    //   78	84	563	finally
    //   88	101	563	finally
    //   105	118	563	finally
    //   122	131	563	finally
    //   139	146	563	finally
    //   150	158	563	finally
    //   162	175	563	finally
    //   179	192	563	finally
    //   196	207	563	finally
    //   578	587	563	finally
    //   590	598	563	finally
    //   601	611	563	finally
    //   614	620	563	finally
    //   78	84	571	java/lang/Throwable
    //   88	101	571	java/lang/Throwable
    //   105	118	571	java/lang/Throwable
    //   122	131	571	java/lang/Throwable
    //   139	146	571	java/lang/Throwable
    //   150	158	571	java/lang/Throwable
    //   162	175	571	java/lang/Throwable
    //   179	192	571	java/lang/Throwable
    //   196	207	571	java/lang/Throwable
    //   435	440	630	java/io/IOException
    //   624	628	630	java/io/IOException
    //   642	647	650	java/io/IOException
  }
  
  public static void delete(File paramFile, boolean paramBoolean)
  {
    if (paramFile != null)
    {
      if (!paramFile.exists()) {
        return;
      }
      if (paramFile.isFile())
      {
        paramFile.delete();
        return;
      }
      File[] arrayOfFile = paramFile.listFiles();
      if (arrayOfFile == null) {
        return;
      }
      int j = arrayOfFile.length;
      int i = 0;
      while (i < j)
      {
        delete(arrayOfFile[i], paramBoolean);
        i += 1;
      }
      if (!paramBoolean) {
        paramFile.delete();
      }
      return;
    }
  }
  
  private static File getConfigFile(Context paramContext)
    throws IOException
  {
    File localFile = mConfFile;
    if (localFile != null) {
      return localFile;
    }
    paramContext = new File(new File(paramContext.getDir("tbs", 0), "core_private"), "tbs_preloadx5_check_and_report.conf");
    if (!paramContext.exists()) {
      paramContext.createNewFile();
    }
    mConfFile = paramContext;
    return paramContext;
  }
  
  private static Properties getConfigFileProp(Context paramContext)
    throws IOException
  {
    Properties localProperties = mConfigFileProp;
    if (localProperties != null) {
      return localProperties;
    }
    localProperties = new Properties();
    mConfigInputStream = new FileInputStream(getConfigFile(paramContext));
    mBufferedConfigInputStream = new BufferedInputStream(mConfigInputStream);
    localProperties.load(mBufferedConfigInputStream);
    mConfigFileProp = localProperties;
    return mConfigFileProp;
  }
  
  /* Error */
  public static String getMd5(File paramFile)
  {
    // Byte code:
    //   0: bipush 16
    //   2: newarray <illegal type>
    //   4: astore 6
    //   6: aload 6
    //   8: dup
    //   9: iconst_0
    //   10: ldc 24
    //   12: castore
    //   13: dup
    //   14: iconst_1
    //   15: ldc_w 294
    //   18: castore
    //   19: dup
    //   20: iconst_2
    //   21: ldc_w 295
    //   24: castore
    //   25: dup
    //   26: iconst_3
    //   27: ldc_w 296
    //   30: castore
    //   31: dup
    //   32: iconst_4
    //   33: ldc_w 297
    //   36: castore
    //   37: dup
    //   38: iconst_5
    //   39: ldc_w 298
    //   42: castore
    //   43: dup
    //   44: bipush 6
    //   46: ldc_w 299
    //   49: castore
    //   50: dup
    //   51: bipush 7
    //   53: ldc_w 300
    //   56: castore
    //   57: dup
    //   58: bipush 8
    //   60: ldc_w 301
    //   63: castore
    //   64: dup
    //   65: bipush 9
    //   67: ldc_w 302
    //   70: castore
    //   71: dup
    //   72: bipush 10
    //   74: ldc_w 303
    //   77: castore
    //   78: dup
    //   79: bipush 11
    //   81: ldc_w 304
    //   84: castore
    //   85: dup
    //   86: bipush 12
    //   88: ldc_w 305
    //   91: castore
    //   92: dup
    //   93: bipush 13
    //   95: ldc_w 306
    //   98: castore
    //   99: dup
    //   100: bipush 14
    //   102: ldc_w 307
    //   105: castore
    //   106: dup
    //   107: bipush 15
    //   109: ldc_w 308
    //   112: castore
    //   113: pop
    //   114: bipush 32
    //   116: newarray <illegal type>
    //   118: astore 7
    //   120: ldc_w 310
    //   123: invokestatic 316	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   126: astore 8
    //   128: new 282	java/io/FileInputStream
    //   131: dup
    //   132: aload_0
    //   133: invokespecial 283	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   136: astore 5
    //   138: aload 5
    //   140: astore_0
    //   141: sipush 8192
    //   144: newarray <illegal type>
    //   146: astore 9
    //   148: aload 5
    //   150: astore_0
    //   151: aload 5
    //   153: aload 9
    //   155: invokevirtual 320	java/io/FileInputStream:read	([B)I
    //   158: istore_1
    //   159: iconst_0
    //   160: istore_2
    //   161: iload_1
    //   162: iconst_m1
    //   163: if_icmpeq +18 -> 181
    //   166: aload 5
    //   168: astore_0
    //   169: aload 8
    //   171: aload 9
    //   173: iconst_0
    //   174: iload_1
    //   175: invokevirtual 324	java/security/MessageDigest:update	([BII)V
    //   178: goto -30 -> 148
    //   181: aload 5
    //   183: astore_0
    //   184: aload 8
    //   186: invokevirtual 328	java/security/MessageDigest:digest	()[B
    //   189: astore 8
    //   191: iconst_0
    //   192: istore_1
    //   193: goto +98 -> 291
    //   196: aload 5
    //   198: astore_0
    //   199: new 330	java/lang/String
    //   202: dup
    //   203: aload 7
    //   205: invokespecial 333	java/lang/String:<init>	([C)V
    //   208: astore 6
    //   210: aload 5
    //   212: invokevirtual 334	java/io/FileInputStream:close	()V
    //   215: aload 6
    //   217: areturn
    //   218: astore_0
    //   219: aload_0
    //   220: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   223: aload 6
    //   225: areturn
    //   226: astore 6
    //   228: goto +15 -> 243
    //   231: astore 5
    //   233: aconst_null
    //   234: astore_0
    //   235: goto +37 -> 272
    //   238: astore 6
    //   240: aconst_null
    //   241: astore 5
    //   243: aload 5
    //   245: astore_0
    //   246: aload 6
    //   248: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   251: aload 5
    //   253: ifnull +15 -> 268
    //   256: aload 5
    //   258: invokevirtual 334	java/io/FileInputStream:close	()V
    //   261: aconst_null
    //   262: areturn
    //   263: astore_0
    //   264: aload_0
    //   265: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   268: aconst_null
    //   269: areturn
    //   270: astore 5
    //   272: aload_0
    //   273: ifnull +15 -> 288
    //   276: aload_0
    //   277: invokevirtual 334	java/io/FileInputStream:close	()V
    //   280: goto +8 -> 288
    //   283: astore_0
    //   284: aload_0
    //   285: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   288: aload 5
    //   290: athrow
    //   291: iload_2
    //   292: bipush 16
    //   294: if_icmpge -98 -> 196
    //   297: aload 8
    //   299: iload_2
    //   300: baload
    //   301: istore_3
    //   302: iload_1
    //   303: iconst_1
    //   304: iadd
    //   305: istore 4
    //   307: aload 7
    //   309: iload_1
    //   310: aload 6
    //   312: iload_3
    //   313: iconst_4
    //   314: iushr
    //   315: bipush 15
    //   317: iand
    //   318: caload
    //   319: castore
    //   320: iload 4
    //   322: iconst_1
    //   323: iadd
    //   324: istore_1
    //   325: aload 7
    //   327: iload 4
    //   329: aload 6
    //   331: iload_3
    //   332: bipush 15
    //   334: iand
    //   335: caload
    //   336: castore
    //   337: iload_2
    //   338: iconst_1
    //   339: iadd
    //   340: istore_2
    //   341: goto -50 -> 291
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	344	0	paramFile	File
    //   158	167	1	i	int
    //   160	181	2	j	int
    //   301	34	3	k	int
    //   305	23	4	m	int
    //   136	75	5	localFileInputStream	FileInputStream
    //   231	1	5	localObject1	Object
    //   241	16	5	localObject2	Object
    //   270	19	5	localObject3	Object
    //   4	220	6	localObject4	Object
    //   226	1	6	localThrowable1	Throwable
    //   238	92	6	localThrowable2	Throwable
    //   118	208	7	arrayOfChar	char[]
    //   126	172	8	localObject5	Object
    //   146	26	9	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   210	215	218	java/lang/Throwable
    //   141	148	226	java/lang/Throwable
    //   151	159	226	java/lang/Throwable
    //   169	178	226	java/lang/Throwable
    //   184	191	226	java/lang/Throwable
    //   199	210	226	java/lang/Throwable
    //   120	138	231	finally
    //   120	138	238	java/lang/Throwable
    //   256	261	263	java/lang/Throwable
    //   141	148	270	finally
    //   151	159	270	finally
    //   169	178	270	finally
    //   184	191	270	finally
    //   199	210	270	finally
    //   246	251	270	finally
    //   276	280	283	java/lang/Throwable
  }
  
  private static int getPreloadX5CrashCounterMax(Context paramContext)
  {
    int i;
    try
    {
      i = PublicSettingManager.getInstance().getCrashInspectionDisableMaxTimes();
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
      i = 0;
    }
    if (i > 0) {
      return i;
    }
    return PRELOAD_X5_CRASH_COUNTER_MAX;
  }
  
  public static int getPreloadX5CrashDisableIntervalHours(Context paramContext)
  {
    int i;
    try
    {
      i = PublicSettingManager.getInstance().getCrashInspectionDisableIntervalHours();
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
      i = 0;
    }
    if ((i > 0) && (i < 336)) {
      return i;
    }
    return 48;
  }
  
  /* Error */
  private static void preloadX5CheckTask(Context paramContext, String paramString1, boolean paramBoolean, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 110	com/tencent/tbs/tbsshell/partner/TbsPrecheck:getConfigFileProp	(Landroid/content/Context;)Ljava/util/Properties;
    //   4: astore 15
    //   6: new 112	java/io/BufferedOutputStream
    //   9: dup
    //   10: new 114	java/io/FileOutputStream
    //   13: dup
    //   14: aload_0
    //   15: invokestatic 118	com/tencent/tbs/tbsshell/partner/TbsPrecheck:getConfigFile	(Landroid/content/Context;)Ljava/io/File;
    //   18: invokespecial 121	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   21: invokespecial 124	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   24: astore 14
    //   26: aload 14
    //   28: astore 12
    //   30: ldc 16
    //   32: aload_3
    //   33: invokevirtual 353	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   36: ifeq +132 -> 168
    //   39: aload 14
    //   41: astore 12
    //   43: invokestatic 226	java/lang/System:currentTimeMillis	()J
    //   46: lstore 8
    //   48: aload 14
    //   50: astore 12
    //   52: aload 15
    //   54: ldc 56
    //   56: ldc 126
    //   58: invokevirtual 197	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   61: invokestatic 220	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   64: lstore 10
    //   66: lload 8
    //   68: lload 10
    //   70: lsub
    //   71: ldc2_w 43
    //   74: lcmp
    //   75: ifge +39 -> 114
    //   78: getstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   81: astore_0
    //   82: aload_0
    //   83: ifnull +19 -> 102
    //   86: aload_0
    //   87: invokevirtual 145	java/io/BufferedInputStream:close	()V
    //   90: aconst_null
    //   91: putstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   94: goto +8 -> 102
    //   97: astore_0
    //   98: aload_0
    //   99: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   102: aload 14
    //   104: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   107: return
    //   108: astore_0
    //   109: aload_0
    //   110: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   113: return
    //   114: aload 14
    //   116: astore 12
    //   118: new 151	java/lang/StringBuilder
    //   121: dup
    //   122: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   125: astore 13
    //   127: aload 14
    //   129: astore 12
    //   131: aload 13
    //   133: ldc -122
    //   135: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: pop
    //   139: aload 14
    //   141: astore 12
    //   143: aload 13
    //   145: lload 8
    //   147: invokevirtual 234	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   150: pop
    //   151: aload 14
    //   153: astore 12
    //   155: aload 15
    //   157: ldc 56
    //   159: aload 13
    //   161: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   164: invokevirtual 132	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   167: pop
    //   168: aload 14
    //   170: astore 12
    //   172: new 151	java/lang/StringBuilder
    //   175: dup
    //   176: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   179: astore 13
    //   181: aload 14
    //   183: astore 12
    //   185: aload 13
    //   187: ldc_w 355
    //   190: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   193: pop
    //   194: aload 14
    //   196: astore 12
    //   198: aload 13
    //   200: aload_3
    //   201: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   204: pop
    //   205: aload 14
    //   207: astore 12
    //   209: aload 13
    //   211: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   214: pop
    //   215: aload 14
    //   217: astore 12
    //   219: new 249	java/io/File
    //   222: dup
    //   223: aload_1
    //   224: invokespecial 358	java/io/File:<init>	(Ljava/lang/String;)V
    //   227: astore 16
    //   229: iconst_0
    //   230: istore 7
    //   232: new 249	java/io/File
    //   235: dup
    //   236: aload 16
    //   238: getstatic 360	com/tencent/tbs/tbsshell/partner/TbsPrecheck:MD5_FILE	Ljava/lang/String;
    //   241: invokespecial 273	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   244: astore_1
    //   245: new 128	java/util/Properties
    //   248: dup
    //   249: invokespecial 280	java/util/Properties:<init>	()V
    //   252: astore_3
    //   253: aload_1
    //   254: invokevirtual 253	java/io/File:exists	()Z
    //   257: ifeq +46 -> 303
    //   260: aload 15
    //   262: ifnull +41 -> 303
    //   265: new 142	java/io/BufferedInputStream
    //   268: dup
    //   269: new 282	java/io/FileInputStream
    //   272: dup
    //   273: aload_1
    //   274: invokespecial 283	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   277: invokespecial 288	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   280: astore_1
    //   281: aload_1
    //   282: astore 12
    //   284: aload_3
    //   285: aload_1
    //   286: invokevirtual 291	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   289: iconst_1
    //   290: istore 4
    //   292: aload_1
    //   293: astore 13
    //   295: goto +14 -> 309
    //   298: astore 13
    //   300: goto +75 -> 375
    //   303: aconst_null
    //   304: astore 13
    //   306: iconst_0
    //   307: istore 4
    //   309: aload_3
    //   310: astore_1
    //   311: iload 4
    //   313: istore 5
    //   315: aload 13
    //   317: ifnull +93 -> 410
    //   320: aload 14
    //   322: astore 12
    //   324: aload 13
    //   326: invokevirtual 145	java/io/BufferedInputStream:close	()V
    //   329: aload_3
    //   330: astore_1
    //   331: iload 4
    //   333: istore 5
    //   335: goto +75 -> 410
    //   338: astore_1
    //   339: aload 14
    //   341: astore 12
    //   343: aload_1
    //   344: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   347: aload_3
    //   348: astore_1
    //   349: iload 4
    //   351: istore 5
    //   353: goto +57 -> 410
    //   356: astore 13
    //   358: aconst_null
    //   359: astore_1
    //   360: goto +15 -> 375
    //   363: astore_0
    //   364: aconst_null
    //   365: astore_1
    //   366: goto +614 -> 980
    //   369: astore 13
    //   371: aconst_null
    //   372: astore_1
    //   373: aload_1
    //   374: astore_3
    //   375: aload_1
    //   376: astore 12
    //   378: aload 13
    //   380: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   383: aload_1
    //   384: ifnull +734 -> 1118
    //   387: aload 14
    //   389: astore 12
    //   391: aload_1
    //   392: invokevirtual 145	java/io/BufferedInputStream:close	()V
    //   395: goto +723 -> 1118
    //   398: astore_1
    //   399: aload 14
    //   401: astore 12
    //   403: aload_1
    //   404: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   407: goto +711 -> 1118
    //   410: aload 14
    //   412: astore 12
    //   414: aload 16
    //   416: invokevirtual 262	java/io/File:listFiles	()[Ljava/io/File;
    //   419: astore_3
    //   420: iconst_0
    //   421: istore 4
    //   423: iload 7
    //   425: istore 6
    //   427: aload 14
    //   429: astore 12
    //   431: iload 4
    //   433: aload_3
    //   434: arraylength
    //   435: if_icmpge +377 -> 812
    //   438: aload 14
    //   440: astore 12
    //   442: aload_3
    //   443: iload 4
    //   445: aaload
    //   446: invokevirtual 361	java/io/File:getName	()Ljava/lang/String;
    //   449: astore 13
    //   451: aload 14
    //   453: astore 12
    //   455: new 151	java/lang/StringBuilder
    //   458: dup
    //   459: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   462: astore 16
    //   464: aload 14
    //   466: astore 12
    //   468: aload 16
    //   470: ldc_w 363
    //   473: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   476: pop
    //   477: aload 14
    //   479: astore 12
    //   481: aload 16
    //   483: aload 13
    //   485: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   488: pop
    //   489: aload 14
    //   491: astore 12
    //   493: aload 16
    //   495: ldc_w 365
    //   498: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   501: pop
    //   502: aload 14
    //   504: astore 12
    //   506: aload 16
    //   508: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   511: pop
    //   512: aload 14
    //   514: astore 12
    //   516: aload 13
    //   518: ldc_w 367
    //   521: invokevirtual 371	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   524: ifne +69 -> 593
    //   527: aload 14
    //   529: astore 12
    //   531: aload 13
    //   533: ldc_w 373
    //   536: invokevirtual 371	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   539: ifne +54 -> 593
    //   542: aload 14
    //   544: astore 12
    //   546: new 151	java/lang/StringBuilder
    //   549: dup
    //   550: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   553: astore 16
    //   555: aload 14
    //   557: astore 12
    //   559: aload 16
    //   561: ldc_w 375
    //   564: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   567: pop
    //   568: aload 14
    //   570: astore 12
    //   572: aload 16
    //   574: aload 13
    //   576: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   579: pop
    //   580: aload 14
    //   582: astore 12
    //   584: aload 16
    //   586: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   589: pop
    //   590: goto +556 -> 1146
    //   593: aload 14
    //   595: astore 12
    //   597: aload_3
    //   598: iload 4
    //   600: aaload
    //   601: invokestatic 377	com/tencent/tbs/tbsshell/partner/TbsPrecheck:getMd5	(Ljava/io/File;)Ljava/lang/String;
    //   604: astore 16
    //   606: iload 5
    //   608: ifeq +533 -> 1141
    //   611: aload 14
    //   613: astore 12
    //   615: aload_1
    //   616: aload 13
    //   618: ldc -122
    //   620: invokevirtual 197	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   623: astore 17
    //   625: aload 14
    //   627: astore 12
    //   629: new 151	java/lang/StringBuilder
    //   632: dup
    //   633: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   636: astore 18
    //   638: aload 14
    //   640: astore 12
    //   642: aload 18
    //   644: ldc_w 379
    //   647: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   650: pop
    //   651: aload 14
    //   653: astore 12
    //   655: aload 18
    //   657: aload 13
    //   659: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   662: pop
    //   663: aload 14
    //   665: astore 12
    //   667: aload 18
    //   669: ldc_w 381
    //   672: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   675: pop
    //   676: aload 14
    //   678: astore 12
    //   680: aload 18
    //   682: aload 17
    //   684: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   687: pop
    //   688: aload 14
    //   690: astore 12
    //   692: aload 18
    //   694: ldc_w 365
    //   697: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   700: pop
    //   701: aload 14
    //   703: astore 12
    //   705: aload 18
    //   707: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   710: pop
    //   711: aload 14
    //   713: astore 12
    //   715: aload 17
    //   717: ldc -122
    //   719: invokevirtual 353	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   722: ifne +404 -> 1126
    //   725: aload 14
    //   727: astore 12
    //   729: aload 17
    //   731: aload 16
    //   733: invokevirtual 353	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   736: ifne +395 -> 1131
    //   739: goto +387 -> 1126
    //   742: aload 14
    //   744: astore 12
    //   746: new 151	java/lang/StringBuilder
    //   749: dup
    //   750: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   753: astore 13
    //   755: aload 14
    //   757: astore 12
    //   759: aload 13
    //   761: ldc_w 383
    //   764: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   767: pop
    //   768: aload 14
    //   770: astore 12
    //   772: aload 13
    //   774: iload_2
    //   775: invokevirtual 386	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   778: pop
    //   779: aload 14
    //   781: astore 12
    //   783: aload 13
    //   785: ldc_w 365
    //   788: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   791: pop
    //   792: aload 14
    //   794: astore 12
    //   796: aload 13
    //   798: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   801: pop
    //   802: iload_2
    //   803: ifne +343 -> 1146
    //   806: iconst_1
    //   807: istore 6
    //   809: goto +3 -> 812
    //   812: iload 6
    //   814: ifle +55 -> 869
    //   817: aload 14
    //   819: astore 12
    //   821: new 151	java/lang/StringBuilder
    //   824: dup
    //   825: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   828: astore_1
    //   829: aload 14
    //   831: astore 12
    //   833: aload_1
    //   834: ldc_w 388
    //   837: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   840: pop
    //   841: aload 14
    //   843: astore 12
    //   845: aload_1
    //   846: iload 6
    //   848: invokevirtual 208	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   851: pop
    //   852: aload 14
    //   854: astore 12
    //   856: aload_1
    //   857: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   860: pop
    //   861: aload 14
    //   863: astore 12
    //   865: aload_0
    //   866: invokestatic 391	com/tencent/tbs/tbsshell/partner/TbsPrecheck:reset	(Landroid/content/Context;)V
    //   869: aload 14
    //   871: astore 12
    //   873: aload 15
    //   875: ldc 65
    //   877: ldc 126
    //   879: invokevirtual 132	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   882: pop
    //   883: aload 14
    //   885: astore 12
    //   887: new 151	java/lang/StringBuilder
    //   890: dup
    //   891: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   894: astore_0
    //   895: aload 14
    //   897: astore 12
    //   899: aload_0
    //   900: ldc -122
    //   902: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   905: pop
    //   906: aload 14
    //   908: astore 12
    //   910: aload_0
    //   911: iload 6
    //   913: invokevirtual 208	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   916: pop
    //   917: aload 14
    //   919: astore 12
    //   921: aload 15
    //   923: ldc 62
    //   925: aload_0
    //   926: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   929: invokevirtual 132	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   932: pop
    //   933: aload 14
    //   935: astore 12
    //   937: aload 15
    //   939: aload 14
    //   941: ldc -122
    //   943: invokevirtual 138	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   946: getstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   949: astore_0
    //   950: aload_0
    //   951: ifnull +19 -> 970
    //   954: aload_0
    //   955: invokevirtual 145	java/io/BufferedInputStream:close	()V
    //   958: aconst_null
    //   959: putstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   962: goto +8 -> 970
    //   965: astore_0
    //   966: aload_0
    //   967: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   970: aload 14
    //   972: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   975: return
    //   976: astore_0
    //   977: aload 12
    //   979: astore_1
    //   980: aload_1
    //   981: ifnull +23 -> 1004
    //   984: aload 14
    //   986: astore 12
    //   988: aload_1
    //   989: invokevirtual 145	java/io/BufferedInputStream:close	()V
    //   992: goto +12 -> 1004
    //   995: astore_1
    //   996: aload 14
    //   998: astore 12
    //   1000: aload_1
    //   1001: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   1004: aload 14
    //   1006: astore 12
    //   1008: aload_0
    //   1009: athrow
    //   1010: astore_1
    //   1011: aload 14
    //   1013: astore_0
    //   1014: goto +13 -> 1027
    //   1017: astore_0
    //   1018: aconst_null
    //   1019: astore 12
    //   1021: goto +53 -> 1074
    //   1024: astore_1
    //   1025: aconst_null
    //   1026: astore_0
    //   1027: aload_0
    //   1028: astore 12
    //   1030: aload_1
    //   1031: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   1034: getstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   1037: astore_1
    //   1038: aload_1
    //   1039: ifnull +19 -> 1058
    //   1042: aload_1
    //   1043: invokevirtual 145	java/io/BufferedInputStream:close	()V
    //   1046: aconst_null
    //   1047: putstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   1050: goto +8 -> 1058
    //   1053: astore_1
    //   1054: aload_1
    //   1055: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   1058: aload_0
    //   1059: ifnull +13 -> 1072
    //   1062: aload_0
    //   1063: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   1066: return
    //   1067: astore_0
    //   1068: aload_0
    //   1069: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   1072: return
    //   1073: astore_0
    //   1074: getstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   1077: astore_1
    //   1078: aload_1
    //   1079: ifnull +19 -> 1098
    //   1082: aload_1
    //   1083: invokevirtual 145	java/io/BufferedInputStream:close	()V
    //   1086: aconst_null
    //   1087: putstatic 140	com/tencent/tbs/tbsshell/partner/TbsPrecheck:mBufferedConfigInputStream	Ljava/io/BufferedInputStream;
    //   1090: goto +8 -> 1098
    //   1093: astore_1
    //   1094: aload_1
    //   1095: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   1098: aload 12
    //   1100: ifnull +16 -> 1116
    //   1103: aload 12
    //   1105: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   1108: goto +8 -> 1116
    //   1111: astore_1
    //   1112: aload_1
    //   1113: invokevirtual 335	java/lang/Throwable:printStackTrace	()V
    //   1116: aload_0
    //   1117: athrow
    //   1118: iconst_1
    //   1119: istore 5
    //   1121: aload_3
    //   1122: astore_1
    //   1123: goto -713 -> 410
    //   1126: aload 16
    //   1128: ifnonnull +8 -> 1136
    //   1131: iconst_1
    //   1132: istore_2
    //   1133: goto -391 -> 742
    //   1136: iconst_0
    //   1137: istore_2
    //   1138: goto -396 -> 742
    //   1141: iconst_1
    //   1142: istore_2
    //   1143: goto -401 -> 742
    //   1146: iload 4
    //   1148: iconst_1
    //   1149: iadd
    //   1150: istore 4
    //   1152: goto -729 -> 423
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1155	0	paramContext	Context
    //   0	1155	1	paramString1	String
    //   0	1155	2	paramBoolean	boolean
    //   0	1155	3	paramString2	String
    //   290	861	4	i	int
    //   313	807	5	j	int
    //   425	487	6	k	int
    //   230	194	7	m	int
    //   46	100	8	l1	long
    //   64	5	10	l2	long
    //   28	1076	12	localObject1	Object
    //   125	169	13	localObject2	Object
    //   298	1	13	localThrowable1	Throwable
    //   304	21	13	localObject3	Object
    //   356	1	13	localThrowable2	Throwable
    //   369	10	13	localThrowable3	Throwable
    //   449	348	13	localObject4	Object
    //   24	988	14	localBufferedOutputStream	java.io.BufferedOutputStream
    //   4	934	15	localProperties	Properties
    //   227	900	16	localObject5	Object
    //   623	107	17	str	String
    //   636	70	18	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   86	94	97	java/lang/Throwable
    //   102	107	108	java/lang/Throwable
    //   284	289	298	java/lang/Throwable
    //   324	329	338	java/lang/Throwable
    //   253	260	356	java/lang/Throwable
    //   265	281	356	java/lang/Throwable
    //   232	253	363	finally
    //   253	260	363	finally
    //   265	281	363	finally
    //   232	253	369	java/lang/Throwable
    //   391	395	398	java/lang/Throwable
    //   954	962	965	java/lang/Throwable
    //   284	289	976	finally
    //   378	383	976	finally
    //   988	992	995	java/lang/Throwable
    //   30	39	1010	java/lang/Throwable
    //   43	48	1010	java/lang/Throwable
    //   52	66	1010	java/lang/Throwable
    //   118	127	1010	java/lang/Throwable
    //   131	139	1010	java/lang/Throwable
    //   143	151	1010	java/lang/Throwable
    //   155	168	1010	java/lang/Throwable
    //   172	181	1010	java/lang/Throwable
    //   185	194	1010	java/lang/Throwable
    //   198	205	1010	java/lang/Throwable
    //   209	215	1010	java/lang/Throwable
    //   219	229	1010	java/lang/Throwable
    //   343	347	1010	java/lang/Throwable
    //   403	407	1010	java/lang/Throwable
    //   414	420	1010	java/lang/Throwable
    //   431	438	1010	java/lang/Throwable
    //   442	451	1010	java/lang/Throwable
    //   455	464	1010	java/lang/Throwable
    //   468	477	1010	java/lang/Throwable
    //   481	489	1010	java/lang/Throwable
    //   493	502	1010	java/lang/Throwable
    //   506	512	1010	java/lang/Throwable
    //   516	527	1010	java/lang/Throwable
    //   531	542	1010	java/lang/Throwable
    //   546	555	1010	java/lang/Throwable
    //   559	568	1010	java/lang/Throwable
    //   572	580	1010	java/lang/Throwable
    //   584	590	1010	java/lang/Throwable
    //   597	606	1010	java/lang/Throwable
    //   615	625	1010	java/lang/Throwable
    //   629	638	1010	java/lang/Throwable
    //   642	651	1010	java/lang/Throwable
    //   655	663	1010	java/lang/Throwable
    //   667	676	1010	java/lang/Throwable
    //   680	688	1010	java/lang/Throwable
    //   692	701	1010	java/lang/Throwable
    //   705	711	1010	java/lang/Throwable
    //   715	725	1010	java/lang/Throwable
    //   729	739	1010	java/lang/Throwable
    //   746	755	1010	java/lang/Throwable
    //   759	768	1010	java/lang/Throwable
    //   772	779	1010	java/lang/Throwable
    //   783	792	1010	java/lang/Throwable
    //   796	802	1010	java/lang/Throwable
    //   821	829	1010	java/lang/Throwable
    //   833	841	1010	java/lang/Throwable
    //   845	852	1010	java/lang/Throwable
    //   856	861	1010	java/lang/Throwable
    //   865	869	1010	java/lang/Throwable
    //   873	883	1010	java/lang/Throwable
    //   887	895	1010	java/lang/Throwable
    //   899	906	1010	java/lang/Throwable
    //   910	917	1010	java/lang/Throwable
    //   921	933	1010	java/lang/Throwable
    //   937	946	1010	java/lang/Throwable
    //   1000	1004	1010	java/lang/Throwable
    //   1008	1010	1010	java/lang/Throwable
    //   0	26	1017	finally
    //   0	26	1024	java/lang/Throwable
    //   1042	1050	1053	java/lang/Throwable
    //   970	975	1067	java/lang/Throwable
    //   1062	1066	1067	java/lang/Throwable
    //   30	39	1073	finally
    //   43	48	1073	finally
    //   52	66	1073	finally
    //   118	127	1073	finally
    //   131	139	1073	finally
    //   143	151	1073	finally
    //   155	168	1073	finally
    //   172	181	1073	finally
    //   185	194	1073	finally
    //   198	205	1073	finally
    //   209	215	1073	finally
    //   219	229	1073	finally
    //   324	329	1073	finally
    //   343	347	1073	finally
    //   391	395	1073	finally
    //   403	407	1073	finally
    //   414	420	1073	finally
    //   431	438	1073	finally
    //   442	451	1073	finally
    //   455	464	1073	finally
    //   468	477	1073	finally
    //   481	489	1073	finally
    //   493	502	1073	finally
    //   506	512	1073	finally
    //   516	527	1073	finally
    //   531	542	1073	finally
    //   546	555	1073	finally
    //   559	568	1073	finally
    //   572	580	1073	finally
    //   584	590	1073	finally
    //   597	606	1073	finally
    //   615	625	1073	finally
    //   629	638	1073	finally
    //   642	651	1073	finally
    //   655	663	1073	finally
    //   667	676	1073	finally
    //   680	688	1073	finally
    //   692	701	1073	finally
    //   705	711	1073	finally
    //   715	725	1073	finally
    //   729	739	1073	finally
    //   746	755	1073	finally
    //   759	768	1073	finally
    //   772	779	1073	finally
    //   783	792	1073	finally
    //   796	802	1073	finally
    //   821	829	1073	finally
    //   833	841	1073	finally
    //   845	852	1073	finally
    //   856	861	1073	finally
    //   865	869	1073	finally
    //   873	883	1073	finally
    //   887	895	1073	finally
    //   899	906	1073	finally
    //   910	917	1073	finally
    //   921	933	1073	finally
    //   937	946	1073	finally
    //   988	992	1073	finally
    //   1000	1004	1073	finally
    //   1008	1010	1073	finally
    //   1030	1034	1073	finally
    //   1082	1090	1093	java/lang/Throwable
    //   1103	1108	1111	java/lang/Throwable
  }
  
  public static void reset(Context paramContext)
  {
    try
    {
      if (Build.VERSION.SDK_INT >= 11) {
        localObject = paramContext.getSharedPreferences("tbs_extension_config", 4);
      } else {
        localObject = paramContext.getSharedPreferences("tbs_extension_config", 0);
      }
      ((SharedPreferences)localObject).edit().clear().commit();
      if (Build.VERSION.SDK_INT >= 11) {
        localObject = paramContext.getSharedPreferences("tbs_download_config_64", 4);
      } else {
        localObject = paramContext.getSharedPreferences("tbs_download_config_64", 0);
      }
      ((SharedPreferences)localObject).edit().clear().commit();
      delete(paramContext.getDir("tbs", 0), false);
      return;
    }
    catch (Throwable paramContext)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("reset exception:");
      ((StringBuilder)localObject).append(Log.getStackTraceString(paramContext));
      Log.e("TbsPrecheck", ((StringBuilder)localObject).toString());
    }
  }
  
  public static void startTbsCheckTask(Context paramContext, final String paramString1, final boolean paramBoolean, final String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TbsPreckeck -- startTbsCheckTask -- msg: ");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("; isTbsCheckReport: ");
    localStringBuilder.append(paramBoolean);
    localStringBuilder.toString();
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          Thread.sleep(3000L);
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
        if (TbsPrecheck.gIsPreloadX5Checked) {
          return;
        }
        TbsPrecheck.gIsPreloadX5Checked = true;
        TbsPrecheck.preloadX5CheckTask(this.val$appContext, paramString1, paramBoolean, paramString2);
      }
    }).start();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\TbsPrecheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */