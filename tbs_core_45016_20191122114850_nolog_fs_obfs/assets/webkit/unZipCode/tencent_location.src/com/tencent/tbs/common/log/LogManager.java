package com.tencent.tbs.common.log;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.webkit.ValueCallback;
import com.tencent.common.utils.Base64;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.DeviceUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

public class LogManager
  implements Runnable
{
  private static final boolean DEBUG_FLAG = false;
  public static final char LOG_FLUSH_BY_ALL = 'A';
  public static final char LOG_FLUSH_BY_COUNT = 'C';
  public static final char LOG_FLUSH_BY_SIZE = 'S';
  public static final char LOG_FLUSH_BY_TIME = 'T';
  private static final String TAG = "LogManager";
  public static final char TYPE_ACTION = 'A';
  public static final char TYPE_EXCEPTION = 'E';
  public static final char TYPE_LEVEL1 = '1';
  public static final char TYPE_LEVEL2 = '2';
  public static final char TYPE_MEMORY = 'M';
  public static final char TYPE_NETWORK = 'N';
  public static final char TYPE_UPDATE = 'U';
  public static final char TYPE_URL = 'L';
  private static LogManager sInstance;
  private int[] mCacheCharCounts = { 0, 0, 0 };
  private boolean mClearOldDisabled = false;
  private File[] mCurrentWritingFiles = { null, null, null };
  private long[] mCurrentWritingStarts = { 0L, 0L, 0L };
  private String[] mFileCurTemplates = { "tmp1_%d_.tmp", "tmp2_%d_.tmp", "tmp3_%d_.tmp" };
  private String[] mFileOldTemplates = { "tbs_%s_%s_%d_%s.qlog", "tbs_%s_%s_%d_%s.qlog", "tbs_%s_%s_%d_%s.qlog" };
  private long[] mLastFlushTimes = { 0L, 0L, 0L };
  private List<String>[] mLogCacheEntityArray = { new LinkedList(), new LinkedList(), new LinkedList() };
  private Cipher mLogDecCipher = null;
  private boolean mLogEnableMode = false;
  private Cipher mLogEncCipher = null;
  private String mLogEncKey = null;
  private boolean mLogEncrypt = true;
  private String mLogFileFolder = null;
  private char mLogFlushMode = 'A';
  private Map<String, String> mLogHeaders = new HashMap();
  private boolean mLogInited = false;
  private SecretKeySpec mLogKeySpec = null;
  private Map<String, String> mLogPreferences = new HashMap();
  private List<LogEntityItem> mLogWriteQueue = new LinkedList();
  private Thread mLogWriteThread = null;
  private int mMaxFlushCount = 200;
  private int mMaxFlushSize = 524288;
  private long mMaxFlushTime = 600000L;
  private long mMaxLogCacheTime = 864000000L;
  private long mMaxLogFileSize = 5242880L;
  private long mMaxLogFileTime = 86400000L;
  private String mPackageName = null;
  private String mProcessName = null;
  private SimpleDateFormat mTimeFormater = new SimpleDateFormat("yyyyMMddHHmmss");
  
  /* Error */
  private void appendAllRecords(Writer paramWriter, boolean paramBoolean, long paramLong1, long paramLong2, int paramInt, char... paramVarArgs)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 151	com/tencent/tbs/common/log/LogManager:mLogFileFolder	Ljava/lang/String;
    //   4: ifnonnull +4 -> 8
    //   7: return
    //   8: lload 5
    //   10: lconst_0
    //   11: lcmp
    //   12: ifgt +1255 -> 1267
    //   15: invokestatic 220	java/lang/System:currentTimeMillis	()J
    //   18: lstore 5
    //   20: goto +3 -> 23
    //   23: lload_3
    //   24: lconst_0
    //   25: lcmp
    //   26: ifgt +1244 -> 1270
    //   29: lload 5
    //   31: aload_0
    //   32: getfield 185	com/tencent/tbs/common/log/LogManager:mMaxLogCacheTime	J
    //   35: lsub
    //   36: lstore_3
    //   37: goto +3 -> 40
    //   40: new 171	java/io/File
    //   43: dup
    //   44: aload_0
    //   45: getfield 151	com/tencent/tbs/common/log/LogManager:mLogFileFolder	Ljava/lang/String;
    //   48: invokespecial 221	java/io/File:<init>	(Ljava/lang/String;)V
    //   51: astore 29
    //   53: aload 29
    //   55: invokevirtual 225	java/io/File:exists	()Z
    //   58: ifne +9 -> 67
    //   61: aload 29
    //   63: invokevirtual 228	java/io/File:mkdirs	()Z
    //   66: pop
    //   67: iconst_2
    //   68: iconst_0
    //   69: iload 7
    //   71: iconst_1
    //   72: isub
    //   73: invokestatic 234	java/lang/Math:max	(II)I
    //   76: invokestatic 237	java/lang/Math:min	(II)I
    //   79: istore 13
    //   81: aload_0
    //   82: iconst_1
    //   83: putfield 153	com/tencent/tbs/common/log/LogManager:mClearOldDisabled	Z
    //   86: iconst_0
    //   87: istore 9
    //   89: iconst_0
    //   90: istore 10
    //   92: aload 8
    //   94: astore 26
    //   96: iload 9
    //   98: iload 13
    //   100: if_icmpgt +1125 -> 1225
    //   103: iload 9
    //   105: iconst_1
    //   106: iadd
    //   107: istore 9
    //   109: aload 29
    //   111: new 16	com/tencent/tbs/common/log/LogManager$5
    //   114: dup
    //   115: aload_0
    //   116: iload 9
    //   118: invokestatic 241	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   121: invokespecial 244	com/tencent/tbs/common/log/LogManager$5:<init>	(Lcom/tencent/tbs/common/log/LogManager;Ljava/lang/String;)V
    //   124: invokevirtual 248	java/io/File:list	(Ljava/io/FilenameFilter;)[Ljava/lang/String;
    //   127: astore 27
    //   129: new 250	java/util/ArrayList
    //   132: dup
    //   133: aload 27
    //   135: arraylength
    //   136: invokespecial 253	java/util/ArrayList:<init>	(I)V
    //   139: astore 25
    //   141: aload 27
    //   143: arraylength
    //   144: istore 11
    //   146: iload 10
    //   148: iload 11
    //   150: if_icmpge +25 -> 175
    //   153: aload 25
    //   155: aload 27
    //   157: iload 10
    //   159: aaload
    //   160: invokeinterface 257 2 0
    //   165: pop
    //   166: iload 10
    //   168: iconst_1
    //   169: iadd
    //   170: istore 10
    //   172: goto -26 -> 146
    //   175: aload 25
    //   177: new 18	com/tencent/tbs/common/log/LogManager$6
    //   180: dup
    //   181: aload_0
    //   182: invokespecial 260	com/tencent/tbs/common/log/LogManager$6:<init>	(Lcom/tencent/tbs/common/log/LogManager;)V
    //   185: invokestatic 266	java/util/Collections:sort	(Ljava/util/List;Ljava/util/Comparator;)V
    //   188: invokestatic 220	java/lang/System:currentTimeMillis	()J
    //   191: lstore 21
    //   193: new 132	java/util/LinkedList
    //   196: dup
    //   197: invokespecial 133	java/util/LinkedList:<init>	()V
    //   200: astore 27
    //   202: aload 25
    //   204: invokeinterface 270 1 0
    //   209: iconst_1
    //   210: isub
    //   211: istore 10
    //   213: iload 10
    //   215: iflt +168 -> 383
    //   218: aload 25
    //   220: iload 10
    //   222: invokeinterface 274 2 0
    //   227: checkcast 155	java/lang/String
    //   230: astore 28
    //   232: aload 28
    //   234: ldc_w 276
    //   237: invokevirtual 280	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   240: astore 31
    //   242: aload 31
    //   244: arraylength
    //   245: iconst_5
    //   246: if_icmpeq +6 -> 252
    //   249: goto +125 -> 374
    //   252: aload 31
    //   254: iconst_4
    //   255: aaload
    //   256: invokevirtual 283	java/lang/String:length	()I
    //   259: istore 11
    //   261: iload 11
    //   263: bipush 19
    //   265: if_icmpeq +6 -> 271
    //   268: goto +106 -> 374
    //   271: aload_0
    //   272: getfield 202	com/tencent/tbs/common/log/LogManager:mTimeFormater	Ljava/text/SimpleDateFormat;
    //   275: astore 30
    //   277: aload 31
    //   279: iconst_4
    //   280: aaload
    //   281: astore 31
    //   283: aload 30
    //   285: aload 31
    //   287: iconst_0
    //   288: bipush 14
    //   290: invokevirtual 287	java/lang/String:substring	(II)Ljava/lang/String;
    //   293: invokevirtual 291	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   296: invokevirtual 296	java/util/Date:getTime	()J
    //   299: lstore 23
    //   301: lload 21
    //   303: lload_3
    //   304: lcmp
    //   305: ifge +968 -> 1273
    //   308: goto +66 -> 374
    //   311: new 298	java/lang/StringBuilder
    //   314: dup
    //   315: invokespecial 299	java/lang/StringBuilder:<init>	()V
    //   318: astore 30
    //   320: aload 30
    //   322: aload_0
    //   323: getfield 151	com/tencent/tbs/common/log/LogManager:mLogFileFolder	Ljava/lang/String;
    //   326: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   329: pop
    //   330: aload 30
    //   332: ldc_w 305
    //   335: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   338: pop
    //   339: aload 30
    //   341: aload 28
    //   343: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   346: pop
    //   347: aload 27
    //   349: iconst_0
    //   350: new 171	java/io/File
    //   353: dup
    //   354: aload 30
    //   356: invokevirtual 309	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   359: invokespecial 221	java/io/File:<init>	(Ljava/lang/String;)V
    //   362: invokeinterface 312 3 0
    //   367: lload 23
    //   369: lstore 21
    //   371: goto +3 -> 374
    //   374: iload 10
    //   376: iconst_1
    //   377: isub
    //   378: istore 10
    //   380: goto -167 -> 213
    //   383: iload 9
    //   385: istore 11
    //   387: aload_1
    //   388: ldc_w 314
    //   391: invokevirtual 319	java/io/Writer:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   394: pop
    //   395: iload 9
    //   397: istore 11
    //   399: aload_1
    //   400: iload 9
    //   402: invokestatic 241	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   405: invokevirtual 319	java/io/Writer:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   408: pop
    //   409: iload 9
    //   411: istore 11
    //   413: aload_1
    //   414: ldc_w 321
    //   417: invokevirtual 319	java/io/Writer:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   420: pop
    //   421: iload 9
    //   423: istore 11
    //   425: new 323	java/util/HashSet
    //   428: dup
    //   429: invokespecial 324	java/util/HashSet:<init>	()V
    //   432: astore 30
    //   434: iload 9
    //   436: istore 11
    //   438: aload 26
    //   440: arraylength
    //   441: istore 12
    //   443: iconst_0
    //   444: istore 10
    //   446: iload 10
    //   448: iload 12
    //   450: if_icmpge +32 -> 482
    //   453: iload 9
    //   455: istore 11
    //   457: aload 30
    //   459: aload 26
    //   461: iload 10
    //   463: caload
    //   464: invokestatic 329	java/lang/Character:valueOf	(C)Ljava/lang/Character;
    //   467: invokeinterface 332 2 0
    //   472: pop
    //   473: iload 10
    //   475: iconst_1
    //   476: iadd
    //   477: istore 10
    //   479: goto -33 -> 446
    //   482: iload 9
    //   484: istore 11
    //   486: aload 30
    //   488: invokeinterface 333 1 0
    //   493: ifeq +797 -> 1290
    //   496: iload 9
    //   498: istore 11
    //   500: aload 30
    //   502: bipush 42
    //   504: invokestatic 329	java/lang/Character:valueOf	(C)Ljava/lang/Character;
    //   507: invokeinterface 336 2 0
    //   512: ifeq +772 -> 1284
    //   515: goto +775 -> 1290
    //   518: iload 9
    //   520: istore 11
    //   522: iload 10
    //   524: aload 25
    //   526: invokeinterface 270 1 0
    //   531: if_icmpge +641 -> 1172
    //   534: iload 9
    //   536: istore 11
    //   538: aload 25
    //   540: iload 10
    //   542: invokeinterface 274 2 0
    //   547: checkcast 171	java/io/File
    //   550: astore 26
    //   552: iload 9
    //   554: istore 11
    //   556: aload 26
    //   558: invokevirtual 225	java/io/File:exists	()Z
    //   561: istore 20
    //   563: iload 20
    //   565: ifne +10 -> 575
    //   568: aload 25
    //   570: astore 26
    //   572: goto +740 -> 1312
    //   575: new 338	java/io/BufferedReader
    //   578: dup
    //   579: new 340	java/io/FileReader
    //   582: dup
    //   583: aload 26
    //   585: invokespecial 343	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   588: invokespecial 346	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   591: astore 26
    //   593: aload 26
    //   595: invokevirtual 349	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   598: astore 31
    //   600: aload 31
    //   602: ifnull +389 -> 991
    //   605: aload 31
    //   607: bipush 93
    //   609: invokevirtual 353	java/lang/String:indexOf	(I)I
    //   612: istore 17
    //   614: iload 17
    //   616: ifge +6 -> 622
    //   619: goto -26 -> 593
    //   622: aload 25
    //   624: astore 28
    //   626: iload 17
    //   628: iconst_2
    //   629: iadd
    //   630: istore 18
    //   632: iload 9
    //   634: istore 11
    //   636: aload 31
    //   638: bipush 93
    //   640: iload 18
    //   642: invokevirtual 355	java/lang/String:indexOf	(II)I
    //   645: istore 19
    //   647: iload 19
    //   649: ifge +14 -> 663
    //   652: aload 28
    //   654: astore 25
    //   656: iload 11
    //   658: istore 9
    //   660: goto -67 -> 593
    //   663: iload 10
    //   665: istore 12
    //   667: iload 19
    //   669: iconst_2
    //   670: iadd
    //   671: istore 15
    //   673: aload 26
    //   675: astore 27
    //   677: aload 31
    //   679: bipush 93
    //   681: iload 15
    //   683: invokevirtual 355	java/lang/String:indexOf	(II)I
    //   686: istore 16
    //   688: iload 16
    //   690: ifge +22 -> 712
    //   693: aload 28
    //   695: astore 25
    //   697: iload 11
    //   699: istore 9
    //   701: iload 12
    //   703: istore 10
    //   705: aload 27
    //   707: astore 26
    //   709: goto -116 -> 593
    //   712: aload 31
    //   714: iconst_1
    //   715: iload 17
    //   717: invokevirtual 287	java/lang/String:substring	(II)Ljava/lang/String;
    //   720: invokestatic 361	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   723: lstore 21
    //   725: lload_3
    //   726: lload 21
    //   728: lcmp
    //   729: ifgt +223 -> 952
    //   732: lload 21
    //   734: lload 5
    //   736: lcmp
    //   737: ifle +22 -> 759
    //   740: aload 28
    //   742: astore 25
    //   744: iload 11
    //   746: istore 9
    //   748: iload 12
    //   750: istore 10
    //   752: aload 27
    //   754: astore 26
    //   756: goto -163 -> 593
    //   759: aload 31
    //   761: iload 18
    //   763: iload 19
    //   765: invokevirtual 287	java/lang/String:substring	(II)Ljava/lang/String;
    //   768: invokestatic 367	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   771: istore 17
    //   773: iload 7
    //   775: iload 17
    //   777: if_icmpge +22 -> 799
    //   780: aload 28
    //   782: astore 25
    //   784: iload 11
    //   786: istore 9
    //   788: iload 12
    //   790: istore 10
    //   792: aload 27
    //   794: astore 26
    //   796: goto -203 -> 593
    //   799: iload 14
    //   801: ifne +48 -> 849
    //   804: aload 30
    //   806: aload 31
    //   808: iload 15
    //   810: iload 16
    //   812: invokevirtual 287	java/lang/String:substring	(II)Ljava/lang/String;
    //   815: iconst_0
    //   816: invokevirtual 371	java/lang/String:charAt	(I)C
    //   819: invokestatic 329	java/lang/Character:valueOf	(C)Ljava/lang/Character;
    //   822: invokeinterface 336 2 0
    //   827: ifne +22 -> 849
    //   830: aload 28
    //   832: astore 25
    //   834: iload 11
    //   836: istore 9
    //   838: iload 12
    //   840: istore 10
    //   842: aload 27
    //   844: astore 26
    //   846: goto -253 -> 593
    //   849: iload_2
    //   850: ifne +21 -> 871
    //   853: aload_1
    //   854: aload 31
    //   856: invokevirtual 319	java/io/Writer:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   859: pop
    //   860: aload_1
    //   861: ldc_w 373
    //   864: invokevirtual 319	java/io/Writer:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   867: pop
    //   868: goto +55 -> 923
    //   871: aload 31
    //   873: bipush 93
    //   875: invokevirtual 376	java/lang/String:lastIndexOf	(I)I
    //   878: istore 15
    //   880: iload 15
    //   882: iconst_1
    //   883: iadd
    //   884: istore 15
    //   886: aload_1
    //   887: aload 31
    //   889: iconst_0
    //   890: iload 15
    //   892: invokevirtual 287	java/lang/String:substring	(II)Ljava/lang/String;
    //   895: invokevirtual 319	java/io/Writer:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   898: pop
    //   899: aload_1
    //   900: aload_0
    //   901: aload 31
    //   903: iload 15
    //   905: invokevirtual 378	java/lang/String:substring	(I)Ljava/lang/String;
    //   908: invokespecial 382	com/tencent/tbs/common/log/LogManager:decrypt	(Ljava/lang/String;)Ljava/lang/String;
    //   911: invokevirtual 319	java/io/Writer:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   914: pop
    //   915: aload_1
    //   916: ldc_w 373
    //   919: invokevirtual 319	java/io/Writer:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   922: pop
    //   923: aload 28
    //   925: astore 25
    //   927: iload 11
    //   929: istore 9
    //   931: iload 12
    //   933: istore 10
    //   935: aload 27
    //   937: astore 26
    //   939: goto -346 -> 593
    //   942: astore 25
    //   944: goto +207 -> 1151
    //   947: astore 27
    //   949: goto +89 -> 1038
    //   952: aload 28
    //   954: astore 25
    //   956: iload 11
    //   958: istore 9
    //   960: iload 12
    //   962: istore 10
    //   964: aload 27
    //   966: astore 26
    //   968: goto -375 -> 593
    //   971: astore 25
    //   973: goto +52 -> 1025
    //   976: astore 27
    //   978: goto +60 -> 1038
    //   981: astore 25
    //   983: goto +42 -> 1025
    //   986: astore 27
    //   988: goto +50 -> 1038
    //   991: iload 10
    //   993: istore 11
    //   995: iload 9
    //   997: istore 10
    //   999: iload 10
    //   1001: istore 9
    //   1003: aload 26
    //   1005: invokevirtual 385	java/io/BufferedReader:close	()V
    //   1008: aload 25
    //   1010: astore 26
    //   1012: iload 10
    //   1014: istore 9
    //   1016: iload 11
    //   1018: istore 10
    //   1020: goto +292 -> 1312
    //   1023: astore 25
    //   1025: iload 9
    //   1027: istore 11
    //   1029: aload 26
    //   1031: astore 27
    //   1033: goto +118 -> 1151
    //   1036: astore 27
    //   1038: aload 25
    //   1040: astore 28
    //   1042: aload 26
    //   1044: astore 25
    //   1046: aload 27
    //   1048: astore 26
    //   1050: iload 9
    //   1052: istore 11
    //   1054: iload 10
    //   1056: istore 12
    //   1058: goto +36 -> 1094
    //   1061: astore 25
    //   1063: aconst_null
    //   1064: astore 27
    //   1066: iload 9
    //   1068: istore 11
    //   1070: goto +81 -> 1151
    //   1073: astore 26
    //   1075: aconst_null
    //   1076: astore 27
    //   1078: iload 10
    //   1080: istore 12
    //   1082: iload 9
    //   1084: istore 11
    //   1086: aload 25
    //   1088: astore 28
    //   1090: aload 27
    //   1092: astore 25
    //   1094: aload 26
    //   1096: invokevirtual 388	java/lang/Exception:printStackTrace	()V
    //   1099: ldc 37
    //   1101: aload 26
    //   1103: invokevirtual 391	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   1106: invokestatic 397	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   1109: pop
    //   1110: aload 28
    //   1112: astore 26
    //   1114: iload 11
    //   1116: istore 9
    //   1118: iload 12
    //   1120: istore 10
    //   1122: aload 25
    //   1124: ifnull +188 -> 1312
    //   1127: iload 11
    //   1129: istore 9
    //   1131: aload 25
    //   1133: invokevirtual 385	java/io/BufferedReader:close	()V
    //   1136: aload 28
    //   1138: astore 26
    //   1140: iload 11
    //   1142: istore 9
    //   1144: iload 12
    //   1146: istore 10
    //   1148: goto +164 -> 1312
    //   1151: aload 27
    //   1153: ifnull +12 -> 1165
    //   1156: iload 11
    //   1158: istore 9
    //   1160: aload 27
    //   1162: invokevirtual 385	java/io/BufferedReader:close	()V
    //   1165: iload 11
    //   1167: istore 9
    //   1169: aload 25
    //   1171: athrow
    //   1172: iload 9
    //   1174: istore 10
    //   1176: iload 10
    //   1178: istore 9
    //   1180: aload_1
    //   1181: ldc_w 373
    //   1184: invokevirtual 319	java/io/Writer:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   1187: pop
    //   1188: goto +150 -> 1338
    //   1191: astore 25
    //   1193: goto +9 -> 1202
    //   1196: astore 25
    //   1198: iload 11
    //   1200: istore 9
    //   1202: aload 25
    //   1204: invokevirtual 388	java/lang/Exception:printStackTrace	()V
    //   1207: ldc 37
    //   1209: aload 25
    //   1211: invokevirtual 391	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   1214: invokestatic 397	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   1217: pop
    //   1218: iload 9
    //   1220: istore 10
    //   1222: goto +116 -> 1338
    //   1225: aload_0
    //   1226: iconst_0
    //   1227: putfield 153	com/tencent/tbs/common/log/LogManager:mClearOldDisabled	Z
    //   1230: goto +17 -> 1247
    //   1233: aload_1
    //   1234: invokevirtual 398	java/lang/Throwable:printStackTrace	()V
    //   1237: ldc 37
    //   1239: aload_1
    //   1240: invokevirtual 399	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   1243: invokestatic 397	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   1246: pop
    //   1247: aload_0
    //   1248: iconst_0
    //   1249: putfield 153	com/tencent/tbs/common/log/LogManager:mClearOldDisabled	Z
    //   1252: return
    //   1253: astore 28
    //   1255: goto -881 -> 374
    //   1258: astore 28
    //   1260: goto -886 -> 374
    //   1263: astore_1
    //   1264: goto -31 -> 1233
    //   1267: goto -1244 -> 23
    //   1270: goto -1230 -> 40
    //   1273: lload 23
    //   1275: lload 5
    //   1277: lcmp
    //   1278: ifle -967 -> 311
    //   1281: goto -907 -> 374
    //   1284: iconst_0
    //   1285: istore 14
    //   1287: goto +6 -> 1293
    //   1290: iconst_1
    //   1291: istore 14
    //   1293: iconst_0
    //   1294: istore 10
    //   1296: aload 27
    //   1298: astore 25
    //   1300: goto -782 -> 518
    //   1303: astore 26
    //   1305: aload 27
    //   1307: astore 25
    //   1309: goto -215 -> 1094
    //   1312: iload 10
    //   1314: iconst_1
    //   1315: iadd
    //   1316: istore 10
    //   1318: aload 26
    //   1320: astore 25
    //   1322: goto -804 -> 518
    //   1325: astore 26
    //   1327: aload 25
    //   1329: astore 27
    //   1331: aload 26
    //   1333: astore 25
    //   1335: goto -184 -> 1151
    //   1338: iload 10
    //   1340: istore 9
    //   1342: goto -1253 -> 89
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1345	0	this	LogManager
    //   0	1345	1	paramWriter	Writer
    //   0	1345	2	paramBoolean	boolean
    //   0	1345	3	paramLong1	long
    //   0	1345	5	paramLong2	long
    //   0	1345	7	paramInt	int
    //   0	1345	8	paramVarArgs	char[]
    //   87	1254	9	i	int
    //   90	1249	10	j	int
    //   144	1055	11	k	int
    //   441	704	12	m	int
    //   79	22	13	n	int
    //   799	493	14	i1	int
    //   671	233	15	i2	int
    //   686	125	16	i3	int
    //   612	166	17	i4	int
    //   630	132	18	i5	int
    //   645	119	19	i6	int
    //   561	3	20	bool	boolean
    //   191	542	21	l1	long
    //   299	975	23	l2	long
    //   139	787	25	localObject1	Object
    //   942	1	25	localObject2	Object
    //   954	1	25	localObject3	Object
    //   971	1	25	localObject4	Object
    //   981	28	25	localObject5	Object
    //   1023	16	25	localObject6	Object
    //   1044	1	25	localObject7	Object
    //   1061	26	25	localObject8	Object
    //   1092	78	25	localObject9	Object
    //   1191	1	25	localException1	Exception
    //   1196	14	25	localException2	Exception
    //   1298	36	25	localObject10	Object
    //   94	955	26	localObject11	Object
    //   1073	29	26	localException3	Exception
    //   1112	27	26	localObject12	Object
    //   1303	16	26	localException4	Exception
    //   1325	7	26	localObject13	Object
    //   127	809	27	localObject14	Object
    //   947	18	27	localException5	Exception
    //   976	1	27	localException6	Exception
    //   986	1	27	localException7	Exception
    //   1031	1	27	localObject15	Object
    //   1036	11	27	localException8	Exception
    //   1064	266	27	localObject16	Object
    //   230	907	28	localObject17	Object
    //   1253	1	28	localException9	Exception
    //   1258	1	28	localException10	Exception
    //   51	59	29	localFile	File
    //   275	530	30	localObject18	Object
    //   240	662	31	localObject19	Object
    // Exception table:
    //   from	to	target	type
    //   886	923	942	finally
    //   886	923	947	java/lang/Exception
    //   677	688	971	finally
    //   712	725	971	finally
    //   759	773	971	finally
    //   804	830	971	finally
    //   853	868	971	finally
    //   871	880	971	finally
    //   677	688	976	java/lang/Exception
    //   712	725	976	java/lang/Exception
    //   759	773	976	java/lang/Exception
    //   871	880	976	java/lang/Exception
    //   636	647	981	finally
    //   636	647	986	java/lang/Exception
    //   593	600	1023	finally
    //   605	614	1023	finally
    //   593	600	1036	java/lang/Exception
    //   605	614	1036	java/lang/Exception
    //   575	593	1061	finally
    //   575	593	1073	java/lang/Exception
    //   1003	1008	1191	java/lang/Exception
    //   1131	1136	1191	java/lang/Exception
    //   1160	1165	1191	java/lang/Exception
    //   1169	1172	1191	java/lang/Exception
    //   1180	1188	1191	java/lang/Exception
    //   387	395	1196	java/lang/Exception
    //   399	409	1196	java/lang/Exception
    //   413	421	1196	java/lang/Exception
    //   425	434	1196	java/lang/Exception
    //   438	443	1196	java/lang/Exception
    //   457	473	1196	java/lang/Exception
    //   486	496	1196	java/lang/Exception
    //   500	515	1196	java/lang/Exception
    //   522	534	1196	java/lang/Exception
    //   538	552	1196	java/lang/Exception
    //   556	563	1196	java/lang/Exception
    //   271	277	1253	java/lang/Exception
    //   283	301	1258	java/lang/Exception
    //   311	367	1258	java/lang/Exception
    //   15	20	1263	java/lang/Throwable
    //   29	37	1263	java/lang/Throwable
    //   40	67	1263	java/lang/Throwable
    //   67	86	1263	java/lang/Throwable
    //   109	146	1263	java/lang/Throwable
    //   153	166	1263	java/lang/Throwable
    //   175	213	1263	java/lang/Throwable
    //   218	249	1263	java/lang/Throwable
    //   252	261	1263	java/lang/Throwable
    //   271	277	1263	java/lang/Throwable
    //   283	301	1263	java/lang/Throwable
    //   311	367	1263	java/lang/Throwable
    //   387	395	1263	java/lang/Throwable
    //   399	409	1263	java/lang/Throwable
    //   413	421	1263	java/lang/Throwable
    //   425	434	1263	java/lang/Throwable
    //   438	443	1263	java/lang/Throwable
    //   457	473	1263	java/lang/Throwable
    //   486	496	1263	java/lang/Throwable
    //   500	515	1263	java/lang/Throwable
    //   522	534	1263	java/lang/Throwable
    //   538	552	1263	java/lang/Throwable
    //   556	563	1263	java/lang/Throwable
    //   1003	1008	1263	java/lang/Throwable
    //   1131	1136	1263	java/lang/Throwable
    //   1160	1165	1263	java/lang/Throwable
    //   1169	1172	1263	java/lang/Throwable
    //   1180	1188	1263	java/lang/Throwable
    //   1202	1218	1263	java/lang/Throwable
    //   1225	1230	1263	java/lang/Throwable
    //   804	830	1303	java/lang/Exception
    //   853	868	1303	java/lang/Exception
    //   1094	1110	1325	finally
  }
  
  private void appendCoreLogs(Writer paramWriter, boolean paramBoolean)
  {
    for (;;)
    {
      try
      {
        paramWriter.append("####corelogs####\r\n");
        Object localObject1 = Class.forName("com.tencent.tbs.tbsshell.WebCoreProxy");
        if (localObject1 == null) {
          return;
        }
        localObject1 = ((Class)localObject1).getDeclaredMethod("getCrashExtraMessage", new Class[0]);
        if (localObject1 == null) {
          return;
        }
        localObject1 = ((Method)localObject1).invoke(null, new Object[0]);
        if (localObject1 == null) {
          return;
        }
        localObject1 = localObject1.toString().split("\r\n");
        int j = Math.max(0, localObject1.length - 13);
        if (j < localObject1.length)
        {
          Object localObject2 = localObject1[j];
          if (!((String)localObject2).startsWith("#"))
          {
            continue;
            if (i >= ((String)localObject2).length()) {
              break label344;
            }
            if (((String)localObject2).charAt(i) == '#') {
              break label337;
            }
            break label346;
            localObject2 = ((String)localObject2).substring(i).split(" ");
            if (localObject2.length >= 3)
            {
              long l = Long.parseLong(localObject2[0]);
              StringBuffer localStringBuffer = new StringBuffer();
              i = 2;
              if (i < localObject2.length)
              {
                localStringBuffer.append(localObject2[i]);
                localStringBuffer.append(" ");
                i += 1;
                continue;
              }
              paramWriter.append(String.format("[%d][%d][%c][%d]", new Object[] { Long.valueOf(l), Integer.valueOf(1), Character.valueOf('1'), Integer.valueOf(0) }));
              localObject2 = localStringBuffer.toString();
              if (paramBoolean)
              {
                paramWriter.append((CharSequence)localObject2);
                paramWriter.append("\r\n");
              }
              else
              {
                paramWriter.append(encrypt((String)localObject2));
                paramWriter.append("\r\n");
              }
            }
            j += 1;
          }
        }
        else
        {
          return;
        }
      }
      catch (Exception paramWriter)
      {
        paramWriter.printStackTrace();
        Log.w("LogManager", paramWriter.getMessage());
      }
      int i = 0;
      continue;
      label337:
      i += 1;
      continue;
      label344:
      i = 0;
      label346:
      if (i != 0) {}
    }
  }
  
  private void appendHeaders(Writer paramWriter)
  {
    for (;;)
    {
      try
      {
        paramWriter.append("####headers####\r\n");
        Iterator localIterator = this.mLogHeaders.keySet().iterator();
        if (localIterator.hasNext())
        {
          String str2 = (String)localIterator.next();
          str1 = (String)this.mLogHeaders.get(str2);
          paramWriter.append(str2);
          paramWriter.append(':');
          if (str1 != null)
          {
            paramWriter.append(str1);
            paramWriter.append("\r\n");
          }
        }
        else
        {
          return;
        }
      }
      catch (IOException paramWriter)
      {
        paramWriter.printStackTrace();
        Log.w("LogManager", paramWriter.getMessage());
      }
      String str1 = "";
    }
  }
  
  private void appendPreferences(Writer paramWriter)
  {
    for (;;)
    {
      try
      {
        paramWriter.append("####preferences####\r\n");
        Iterator localIterator = this.mLogPreferences.keySet().iterator();
        if (localIterator.hasNext())
        {
          String str2 = (String)localIterator.next();
          str1 = (String)this.mLogPreferences.get(str2);
          paramWriter.append(str2);
          paramWriter.append(':');
          if (str1 != null)
          {
            paramWriter.append(str1);
            paramWriter.append("\r\n");
          }
        }
        else
        {
          return;
        }
      }
      catch (IOException paramWriter)
      {
        paramWriter.printStackTrace();
        Log.w("LogManager", paramWriter.getMessage());
      }
      String str1 = "";
    }
  }
  
  private void appendSdkLogs(Writer paramWriter, boolean paramBoolean)
  {
    for (;;)
    {
      try
      {
        paramWriter.append("####sdklogs####\r\n");
        Object localObject = Class.forName("com.tencent.smtt.utils.TbsLog");
        if (localObject == null) {
          return;
        }
        localObject = ((Class)localObject).getDeclaredField("sTbsLogList");
        if (localObject == null) {
          return;
        }
        ((Field)localObject).setAccessible(true);
        localObject = (List)((Field)localObject).get(null);
        if (localObject == null) {
          return;
        }
        int i = 0;
        try
        {
          if (i < ((List)localObject).size())
          {
            String str = (String)((List)localObject).get(i);
            if (paramBoolean)
            {
              paramWriter.append(str);
              paramWriter.append("\r\n");
            }
            else
            {
              int j = str.lastIndexOf(']') + 1;
              if (j >= 0)
              {
                paramWriter.append(str.substring(0, j));
                paramWriter.append(encrypt(str.substring(j)));
                paramWriter.append("\r\n");
              }
            }
          }
          else
          {
            return;
          }
        }
        finally {}
        i += 1;
      }
      catch (Exception paramWriter)
      {
        paramWriter.printStackTrace();
        Log.w("LogManager", paramWriter.getMessage());
        return;
      }
    }
  }
  
  private void checkNeedReport()
  {
    try
    {
      TbsBaseModuleShell.getCallerContext();
      String str = PublicSettingManager.getInstance().getTbsLogReportPrefs();
      if ((str != null) && (str.length() > 0) && (str.indexOf('&') > 0))
      {
        checkWUPKeyAndReport(10000L, str);
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("checkNeedReport:");
      localStringBuilder.append(localException.getMessage());
      Log.w("LogManager", localStringBuilder.toString());
    }
  }
  
  private void clearOldCacheLogFiles(boolean paramBoolean)
  {
    if (this.mClearOldDisabled) {
      return;
    }
    try
    {
      if (this.mLogFileFolder != null)
      {
        Object localObject1 = new File(this.mLogFileFolder);
        if (!((File)localObject1).exists()) {
          ((File)localObject1).mkdirs();
        }
        localObject1 = ((File)localObject1).list(new FilenameFilter()
        {
          public boolean accept(File paramAnonymousFile, String paramAnonymousString)
          {
            int i = paramAnonymousString.split("_").length;
            boolean bool2 = false;
            if (i != 5) {
              return false;
            }
            boolean bool1 = bool2;
            if (paramAnonymousString.startsWith("tbs"))
            {
              bool1 = bool2;
              if (paramAnonymousString.endsWith(".qlog")) {
                bool1 = true;
              }
            }
            return bool1;
          }
        });
        long l1 = System.currentTimeMillis();
        int j = localObject1.length;
        int i = 0;
        while (i < j)
        {
          String str = localObject1[i];
          Object localObject2 = str.split("_");
          if (localObject2.length == 5)
          {
            long l2 = new Date(Integer.parseInt(localObject2[4].substring(0, 4)) - 1900, Integer.parseInt(localObject2[4].substring(4, 6)) - 1, Integer.parseInt(localObject2[4].substring(6, 8)), Integer.parseInt(localObject2[4].substring(8, 10)), Integer.parseInt(localObject2[4].substring(10, 12)), Integer.parseInt(localObject2[4].substring(12, 14))).getTime();
            if (!paramBoolean)
            {
              long l3 = this.mMaxLogCacheTime;
              if (l1 - l2 <= l3) {}
            }
            else
            {
              try
              {
                localObject2 = new StringBuilder();
                ((StringBuilder)localObject2).append(this.mLogFileFolder);
                ((StringBuilder)localObject2).append("/");
                ((StringBuilder)localObject2).append(str);
                str = ((StringBuilder)localObject2).toString();
                new File(str).delete();
                localObject2 = new StringBuilder();
                ((StringBuilder)localObject2).append("过期文件：");
                ((StringBuilder)localObject2).append(str);
                ((StringBuilder)localObject2).toString();
              }
              catch (Exception localException2)
              {
                localException2.printStackTrace();
                Log.w("LogManager", localException2.getMessage());
              }
            }
          }
          i += 1;
        }
      }
      return;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Log.w("LogManager", localException1.getMessage());
    }
  }
  
  private String decrypt(String paramString)
  {
    if ((this.mLogEncrypt) && (paramString != null))
    {
      if (paramString.length() == 0) {
        return paramString;
      }
      try
      {
        if (this.mLogKeySpec == null) {
          this.mLogKeySpec = new SecretKeySpec(this.mLogEncKey.getBytes("UTF8"), "RC4");
        }
        if (this.mLogDecCipher == null)
        {
          this.mLogDecCipher = Cipher.getInstance("RC4");
          if (this.mLogDecCipher == null) {
            return null;
          }
          this.mLogDecCipher.init(2, this.mLogKeySpec);
        }
        paramString = Base64.decode(paramString);
        paramString = new String(this.mLogDecCipher.doFinal(paramString));
        return paramString;
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
        return null;
      }
    }
    return paramString;
  }
  
  private void doIniting()
  {
    try
    {
      Context localContext = TbsBaseModuleShell.getCallerContext();
      if (localContext == null) {
        return;
      }
      String str = DeviceUtils.getIMEI(TbsBaseModuleShell.getContext());
      if (str != null)
      {
        if (str.length() == 0) {
          return;
        }
        loadSettings();
        this.mLogInited = true;
        if (!this.mLogEnableMode) {
          return;
        }
        this.mLogEncKey = String.valueOf(str.hashCode());
        this.mPackageName = localContext.getPackageName();
        this.mProcessName = ThreadUtils.getCurrentProcessName(localContext);
        try
        {
          if (this.mProcessName.equals(this.mPackageName)) {
            this.mProcessName = "main";
          } else if ((this.mProcessName.startsWith(this.mPackageName)) && (this.mProcessName.indexOf(':') >= 0)) {
            this.mProcessName = this.mProcessName.split(":")[1];
          }
          if (this.mProcessName.length() == 0) {
            this.mProcessName = "none";
          }
          this.mPackageName = this.mPackageName.replace('_', '-');
          this.mProcessName = this.mProcessName.replace('_', '-');
          this.mProcessName = this.mProcessName.replace(':', '-');
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        reloadTbsLogSavePath();
        reloadLastWritingFile(0);
        reloadLastWritingFile(1);
        reloadLastWritingFile(2);
        reloadAllPreferences();
        header("starttime", this.mTimeFormater.format(Long.valueOf(System.currentTimeMillis())));
        header("deviceimei", str);
        String.format("encrypt key = %s", new Object[] { this.mLogEncKey });
        String.format("package:%s, process:%s", new Object[] { this.mPackageName, this.mProcessName });
        startWriteThread();
        checkNeedReport();
        return;
      }
      return;
    }
    catch (Exception localException1)
    {
      this.mLogEnableMode = false;
      localException1.printStackTrace();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("doIniting:");
      localStringBuilder.append(localException1.getMessage());
      Log.w("LogManager", localStringBuilder.toString());
    }
  }
  
  private String encrypt(String paramString)
  {
    if ((this.mLogEncrypt) && (paramString != null))
    {
      if (paramString.length() == 0) {
        return paramString;
      }
      try
      {
        if (this.mLogKeySpec == null) {
          this.mLogKeySpec = new SecretKeySpec(this.mLogEncKey.getBytes("UTF8"), "RC4");
        }
        if (this.mLogEncCipher == null)
        {
          this.mLogEncCipher = Cipher.getInstance("RC4");
          if (this.mLogEncCipher == null) {
            return null;
          }
          this.mLogEncCipher.init(1, this.mLogKeySpec);
        }
        paramString = paramString.getBytes("UTF8");
        paramString = Base64.encode(this.mLogEncCipher.doFinal(paramString));
        return paramString;
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
        return null;
      }
    }
    return paramString;
  }
  
  private void flushLogsInCacheList(int paramInt)
  {
    if (paramInt >= 0)
    {
      if (paramInt > 2) {
        return;
      }
      try
      {
        if (this.mCurrentWritingFiles[paramInt] == null) {
          return;
        }
        i = this.mLogCacheEntityArray[paramInt].size();
        if (i == 0) {
          return;
        }
      }
      catch (Exception localException1)
      {
        int i;
        localException1.printStackTrace();
        FileWriter localFileWriter;
        try
        {
          localFileWriter = new FileWriter(this.mCurrentWritingFiles[paramInt], true);
          i = 0;
          try
          {
            while (i < this.mLogCacheEntityArray[paramInt].size())
            {
              localFileWriter.append((String)this.mLogCacheEntityArray[paramInt].get(i));
              localFileWriter.append("\r\n");
              i += 1;
            }
            this.mLastFlushTimes[paramInt] = System.currentTimeMillis();
            this.mCacheCharCounts[paramInt] = 0;
            this.mLogCacheEntityArray[paramInt].clear();
            localFileWriter.flush();
            localFileWriter.close();
            return;
          }
          catch (Exception localException2) {}
          localException3.printStackTrace();
        }
        catch (Exception localException3)
        {
          localFileWriter = null;
        }
        Log.w("LogManager", localException3.getMessage());
        if (localFileWriter != null) {
          try
          {
            localFileWriter.close();
            return;
          }
          catch (IOException localIOException)
          {
            localIOException.printStackTrace();
            Log.w("LogManager", localException3.getMessage());
          }
        }
        return;
      }
    }
  }
  
  public static LogManager getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new LogManager();
      }
      LogManager localLogManager = sInstance;
      return localLogManager;
    }
    finally {}
  }
  
  private boolean isInited()
  {
    return this.mLogInited;
  }
  
  private void loadSettings()
  {
    for (;;)
    {
      boolean bool;
      try
      {
        localObject2 = TbsBaseModuleShell.getCallerContext();
        localObject1 = PublicSettingManager.getInstance();
        localObject3 = ((PublicSettingManager)localObject1).getTbsLogEnablePrefs();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("enablePrefs:");
        localStringBuilder.append((String)localObject3);
        localStringBuilder.toString();
        if (localObject3 != null)
        {
          i = ((String)localObject3).length();
          if (i <= 0) {}
        }
      }
      catch (Exception localException1)
      {
        Object localObject1;
        Object localObject3;
        int i;
        label104:
        label263:
        label450:
        localException1.printStackTrace();
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("loadSettings:");
        ((StringBuilder)localObject2).append(localException1.getMessage());
        Log.w("LogManager", ((StringBuilder)localObject2).toString());
        return;
      }
      try
      {
        if (Integer.parseInt((String)localObject3) != 1) {
          break label540;
        }
        bool = true;
      }
      catch (Exception localException2)
      {
        continue;
        label540:
        bool = false;
      }
    }
    this.mLogEnableMode = bool;
    break label104;
    this.mLogEnableMode = false;
    break label104;
    this.mLogEnableMode = Configuration.getInstance((Context)localObject2).isTbsLogManagerEnable();
    localObject2 = ((PublicSettingManager)localObject1).getTbsLogCacheStrategyPrefs();
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("cachePrefs:");
    ((StringBuilder)localObject3).append((String)localObject2);
    ((StringBuilder)localObject3).toString();
    if (localObject2 != null)
    {
      i = ((String)localObject2).length();
      if (i <= 0) {}
    }
    try
    {
      localObject2 = ((String)localObject2).split("\\|");
      if (localObject2.length != 3) {
        break label263;
      }
      this.mMaxLogFileTime = (Long.parseLong(localObject2[0]) * 60L * 60L * 1000L);
      this.mMaxLogCacheTime = (Long.parseLong(localObject2[1]) * 60L * 60L * 1000L);
      this.mMaxLogFileSize = (Long.parseLong(localObject2[2]) * 1024L * 1024L);
    }
    catch (Throwable localThrowable2)
    {
      for (;;) {}
    }
    this.mMaxLogFileTime = 86400000L;
    this.mMaxLogCacheTime = 864000000L;
    this.mMaxLogFileSize = 5242880L;
    localObject1 = ((PublicSettingManager)localObject1).getTbsLogFlushStrategyPrefs();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("flushPrefs:");
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).toString();
    if (localObject1 != null)
    {
      i = ((String)localObject1).length();
      if (i <= 0) {}
    }
    try
    {
      localObject1 = ((String)localObject1).split("\\|");
      if (localObject1.length != 4) {
        break label450;
      }
      this.mLogFlushMode = localObject1[0].charAt(0);
      if ((this.mLogFlushMode != 'A') && (this.mLogFlushMode != 'T') && (this.mLogFlushMode != 'S') && (this.mLogFlushMode != 'C')) {
        this.mLogFlushMode = 'A';
      }
      this.mMaxFlushTime = (Long.parseLong(localObject1[1]) * 1000L);
      this.mMaxFlushCount = Integer.parseInt(localObject1[2]);
      this.mMaxFlushSize = (Integer.parseInt(localObject1[3]) * 1024 * 1024);
    }
    catch (Throwable localThrowable1)
    {
      for (;;) {}
    }
    this.mLogFlushMode = 'A';
    this.mMaxFlushTime = 600000L;
    this.mMaxFlushCount = 200;
    this.mMaxFlushSize = 524288;
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("mLogEnableMode:");
    ((StringBuilder)localObject1).append(this.mLogEnableMode);
    ((StringBuilder)localObject1).toString();
  }
  
  public static void logHeader(String paramString1, String paramString2)
  {
    getInstance().header(paramString1, paramString2);
  }
  
  public static void logL1(int paramInt, String paramString, Object... paramVarArgs)
  {
    getInstance().log((byte)1, '1', paramInt, paramString, paramVarArgs);
  }
  
  public static void logL2(int paramInt, String paramString, Object... paramVarArgs)
  {
    getInstance().log((byte)2, '2', paramInt, paramString, paramVarArgs);
  }
  
  public static void logL3(char paramChar, int paramInt, String paramString, Object... paramVarArgs)
  {
    getInstance().log((byte)3, paramChar, paramInt, paramString, paramVarArgs);
  }
  
  public static void logL3WithAction(int paramInt, String paramString, Object... paramVarArgs)
  {
    logL3('A', paramInt, paramString, paramVarArgs);
  }
  
  public static void logL3WithMemory(int paramInt, String paramString, Object... paramVarArgs)
  {
    logL3('M', paramInt, paramString, paramVarArgs);
  }
  
  public static void logL3WithNetwork(int paramInt, String paramString, Object... paramVarArgs)
  {
    logL3('N', paramInt, paramString, paramVarArgs);
  }
  
  public static void logL3WithURL(int paramInt, String paramString, Object... paramVarArgs)
  {
    logL3('L', paramInt, paramString, paramVarArgs);
  }
  
  public static void logL3WithUpdate(int paramInt, String paramString, Object... paramVarArgs)
  {
    logL3('U', paramInt, paramString, paramVarArgs);
  }
  
  public static void logPreference(String paramString1, String paramString2)
  {
    getInstance().preference(paramString1, paramString2);
  }
  
  public static void logReport(long paramLong1, long paramLong2, int paramInt, char... paramVarArgs)
  {
    getInstance().report(paramLong1, paramLong2, paramInt, paramVarArgs);
  }
  
  private void reloadLastWritingFile(final int paramInt)
  {
    if (paramInt >= 0) {
      if (paramInt > 2) {
        return;
      }
    }
    for (;;)
    {
      int i;
      try
      {
        if ((this.mLogFileFolder != null) && (this.mCurrentWritingFiles[paramInt] == null))
        {
          Object localObject1 = new File(this.mLogFileFolder);
          if (!((File)localObject1).exists()) {
            ((File)localObject1).mkdirs();
          }
          localObject1 = ((File)localObject1).list(new FilenameFilter()
          {
            public boolean accept(File paramAnonymousFile, String paramAnonymousString)
            {
              paramAnonymousFile = new StringBuilder();
              paramAnonymousFile.append("tmp");
              paramAnonymousFile.append(paramInt + 1);
              paramAnonymousFile.append("_");
              return (paramAnonymousString.startsWith(paramAnonymousFile.toString())) && (paramAnonymousString.endsWith("_.tmp"));
            }
          });
          int j = 1;
          Object localObject2;
          if ((localObject1 != null) && (localObject1.length > 0))
          {
            localObject2 = localObject1[0];
            Object localObject3 = ((String)localObject2).split("_");
            i = j;
            if (localObject3.length == 3)
            {
              this.mCurrentWritingStarts[paramInt] = Long.parseLong(localObject3[1]);
              localObject3 = this.mCurrentWritingFiles;
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append(this.mLogFileFolder);
              localStringBuilder.append("/");
              localStringBuilder.append((String)localObject2);
              localObject3[paramInt] = new File(localStringBuilder.toString());
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append("上次文件：");
              ((StringBuilder)localObject2).append(this.mCurrentWritingFiles[paramInt]);
              ((StringBuilder)localObject2).toString();
              i = j;
            }
            if (i < localObject1.length)
            {
              localObject2 = localObject1[i];
              localObject3 = new StringBuilder();
              ((StringBuilder)localObject3).append(this.mLogFileFolder);
              ((StringBuilder)localObject3).append("/");
              ((StringBuilder)localObject3).append((String)localObject2);
              localObject2 = new File(((StringBuilder)localObject3).toString());
              if (!((File)localObject2).exists()) {
                break label417;
              }
              ((File)localObject2).delete();
              break label417;
            }
          }
          else
          {
            this.mCurrentWritingStarts[paramInt] = System.currentTimeMillis();
            localObject1 = this.mCurrentWritingFiles;
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append(this.mLogFileFolder);
            ((StringBuilder)localObject2).append("/");
            ((StringBuilder)localObject2).append(String.format(this.mFileCurTemplates[paramInt], new Object[] { Long.valueOf(this.mCurrentWritingStarts[paramInt]) }));
            localObject1[paramInt] = new File(((StringBuilder)localObject2).toString());
            this.mCurrentWritingFiles[paramInt].createNewFile();
            return;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        Log.w("LogManager", localException.getMessage());
      }
      return;
      return;
      label417:
      i += 1;
    }
  }
  
  private void reloadTbsLogSavePath()
  {
    for (;;)
    {
      try
      {
        if (this.mLogFileFolder == null)
        {
          localContext = TbsBaseModuleShell.getCallerContext();
          if (localContext != null)
          {
            try
            {
              i = localContext.getPackageManager().checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", localContext.getPackageName());
              if (i == 0) {
                i = 1;
              } else {
                i = 0;
              }
            }
            catch (Exception localException1)
            {
              localException1.printStackTrace();
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append("checkPermission:");
              ((StringBuilder)localObject2).append(localException1.getMessage());
              Log.w("LogManager", ((StringBuilder)localObject2).toString());
              i = 0;
            }
            localObject2 = null;
            localObject1 = localObject2;
          }
        }
      }
      catch (Exception localException2)
      {
        Context localContext;
        int i;
        Object localObject2;
        Object localObject1;
        Object localObject3;
        localException2.printStackTrace();
        Log.w("LogManager", localException2.getMessage());
      }
      try
      {
        localObject3 = localContext.getPackageName();
        localObject1 = localObject2;
        localObject2 = ThreadUtils.getCurrentProcessName(localContext);
        localObject1 = localObject2;
        if (((String)localObject2).equals(localObject3))
        {
          localObject2 = "main";
        }
        else
        {
          localObject1 = localObject2;
          if (((String)localObject2).startsWith((String)localObject3))
          {
            localObject1 = localObject2;
            if (((String)localObject2).length() > ((String)localObject3).length() + 1)
            {
              localObject1 = localObject2;
              localObject2 = ((String)localObject2).substring(((String)localObject3).length() + 1);
              localObject1 = localObject2;
              localObject2 = ((String)localObject2).replace(':', '_');
              continue;
            }
          }
          localObject1 = localObject2;
          localObject2 = ((String)localObject2).replace(':', '_');
        }
        localObject1 = localObject2;
        localObject3 = new StringBuilder();
        localObject1 = localObject2;
        ((StringBuilder)localObject3).append("processName=");
        localObject1 = localObject2;
        ((StringBuilder)localObject3).append((String)localObject2);
        localObject1 = localObject2;
        ((StringBuilder)localObject3).toString();
        localObject1 = localObject2;
      }
      catch (Exception localException4) {}
    }
    if (localObject1 == null)
    {
      this.mLogEnableMode = false;
      return;
    }
    if (i != 0) {}
    try
    {
      this.mLogFileFolder = String.format("%s/tencent/tbs/tbslog/%s/%s", new Object[] { Environment.getExternalStorageDirectory().getAbsolutePath(), localContext.getPackageName(), localObject1 });
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    this.mLogFileFolder = String.format("%s/tencent/tbs/tbslog/nonepackage/%s", new Object[] { Environment.getExternalStorageDirectory().getAbsolutePath(), localObject1 });
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("LogFileFolder 1:");
    ((StringBuilder)localObject1).append(this.mLogFileFolder);
    ((StringBuilder)localObject1).toString();
    try
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(localContext.getExternalFilesDir("tbslog").getAbsolutePath());
      ((StringBuilder)localObject2).append(File.separator);
      ((StringBuilder)localObject2).append((String)localObject1);
      this.mLogFileFolder = ((StringBuilder)localObject2).toString();
    }
    catch (Exception localException3)
    {
      for (;;) {}
    }
    this.mLogEnableMode = false;
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("LogFileFolder 2:");
    ((StringBuilder)localObject1).append(this.mLogFileFolder);
    ((StringBuilder)localObject1).toString();
    if (this.mLogFileFolder != null)
    {
      localObject1 = new File(this.mLogFileFolder);
      if (!((File)localObject1).exists()) {
        ((File)localObject1).mkdirs();
      }
    }
    clearOldCacheLogFiles(false);
    return;
  }
  
  /* Error */
  private void reportWithCommand(String paramString, final boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 151	com/tencent/tbs/common/log/LogManager:mLogFileFolder	Ljava/lang/String;
    //   4: ifnonnull +4 -> 8
    //   7: return
    //   8: ldc 2
    //   10: monitorenter
    //   11: iconst_0
    //   12: istore_3
    //   13: iload_3
    //   14: iconst_2
    //   15: if_icmpgt +20 -> 35
    //   18: aload_0
    //   19: iload_3
    //   20: invokespecial 793	com/tencent/tbs/common/log/LogManager:flushLogsInCacheList	(I)V
    //   23: aload_0
    //   24: iload_3
    //   25: invokespecial 796	com/tencent/tbs/common/log/LogManager:saveAsCurrentLogFile	(I)V
    //   28: iload_3
    //   29: iconst_1
    //   30: iadd
    //   31: istore_3
    //   32: goto -19 -> 13
    //   35: new 250	java/util/ArrayList
    //   38: dup
    //   39: invokespecial 797	java/util/ArrayList:<init>	()V
    //   42: astore 6
    //   44: new 298	java/lang/StringBuilder
    //   47: dup
    //   48: invokespecial 299	java/lang/StringBuilder:<init>	()V
    //   51: astore 4
    //   53: aload 4
    //   55: aload_0
    //   56: getfield 151	com/tencent/tbs/common/log/LogManager:mLogFileFolder	Ljava/lang/String;
    //   59: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: pop
    //   63: aload 4
    //   65: ldc_w 799
    //   68: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: pop
    //   72: new 171	java/io/File
    //   75: dup
    //   76: aload 4
    //   78: invokevirtual 309	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   81: invokespecial 221	java/io/File:<init>	(Ljava/lang/String;)V
    //   84: astore 4
    //   86: new 655	java/io/FileWriter
    //   89: dup
    //   90: aload 4
    //   92: iconst_0
    //   93: invokespecial 658	java/io/FileWriter:<init>	(Ljava/io/File;Z)V
    //   96: astore 5
    //   98: aload_0
    //   99: aload 5
    //   101: invokespecial 801	com/tencent/tbs/common/log/LogManager:appendHeaders	(Ljava/io/Writer;)V
    //   104: aload 5
    //   106: ldc_w 373
    //   109: invokevirtual 659	java/io/FileWriter:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   112: pop
    //   113: aload_0
    //   114: aload 5
    //   116: invokespecial 803	com/tencent/tbs/common/log/LogManager:appendPreferences	(Ljava/io/Writer;)V
    //   119: aload 5
    //   121: ldc_w 373
    //   124: invokevirtual 659	java/io/FileWriter:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   127: pop
    //   128: aload_0
    //   129: aload 5
    //   131: iconst_0
    //   132: invokespecial 805	com/tencent/tbs/common/log/LogManager:appendSdkLogs	(Ljava/io/Writer;Z)V
    //   135: aload 5
    //   137: ldc_w 373
    //   140: invokevirtual 659	java/io/FileWriter:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   143: pop
    //   144: aload_0
    //   145: aload 5
    //   147: iconst_0
    //   148: invokespecial 807	com/tencent/tbs/common/log/LogManager:appendCoreLogs	(Ljava/io/Writer;Z)V
    //   151: aload 5
    //   153: ldc_w 373
    //   156: invokevirtual 659	java/io/FileWriter:append	(Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   159: pop
    //   160: aload 5
    //   162: invokevirtual 665	java/io/FileWriter:flush	()V
    //   165: aload 5
    //   167: invokevirtual 666	java/io/FileWriter:close	()V
    //   170: aload 6
    //   172: aload 4
    //   174: invokeinterface 257 2 0
    //   179: pop
    //   180: goto +39 -> 219
    //   183: astore 4
    //   185: goto +8 -> 193
    //   188: astore 4
    //   190: aconst_null
    //   191: astore 5
    //   193: aload 4
    //   195: invokevirtual 388	java/lang/Exception:printStackTrace	()V
    //   198: ldc 37
    //   200: aload 4
    //   202: invokevirtual 391	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   205: invokestatic 397	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   208: pop
    //   209: aload 5
    //   211: ifnull +8 -> 219
    //   214: aload 5
    //   216: invokevirtual 666	java/io/FileWriter:close	()V
    //   219: aload_1
    //   220: aload_0
    //   221: getfield 151	com/tencent/tbs/common/log/LogManager:mLogFileFolder	Ljava/lang/String;
    //   224: aload 6
    //   226: new 12	com/tencent/tbs/common/log/LogManager$3
    //   229: dup
    //   230: aload_0
    //   231: iload_2
    //   232: invokespecial 810	com/tencent/tbs/common/log/LogManager$3:<init>	(Lcom/tencent/tbs/common/log/LogManager;Z)V
    //   235: invokestatic 816	com/tencent/tbs/common/log/LogUploader:executeCmd	(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Landroid/webkit/ValueCallback;)V
    //   238: ldc 2
    //   240: monitorexit
    //   241: return
    //   242: astore_1
    //   243: ldc 2
    //   245: monitorexit
    //   246: aload_1
    //   247: athrow
    //   248: astore 4
    //   250: goto -31 -> 219
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	253	0	this	LogManager
    //   0	253	1	paramString	String
    //   0	253	2	paramBoolean	boolean
    //   12	20	3	i	int
    //   51	122	4	localObject	Object
    //   183	1	4	localException1	Exception
    //   188	13	4	localException2	Exception
    //   248	1	4	localIOException	IOException
    //   96	119	5	localFileWriter	FileWriter
    //   42	183	6	localArrayList	java.util.ArrayList
    // Exception table:
    //   from	to	target	type
    //   98	170	183	java/lang/Exception
    //   44	98	188	java/lang/Exception
    //   170	180	188	java/lang/Exception
    //   18	28	242	finally
    //   35	44	242	finally
    //   44	98	242	finally
    //   98	170	242	finally
    //   170	180	242	finally
    //   193	209	242	finally
    //   214	219	242	finally
    //   219	241	242	finally
    //   243	246	242	finally
    //   214	219	248	java/io/IOException
  }
  
  /* Error */
  private void saveAsCurrentLogFile(int paramInt)
  {
    // Byte code:
    //   0: iload_1
    //   1: iflt +323 -> 324
    //   4: iload_1
    //   5: iconst_2
    //   6: if_icmple +4 -> 10
    //   9: return
    //   10: aload_0
    //   11: getfield 167	com/tencent/tbs/common/log/LogManager:mFileOldTemplates	[Ljava/lang/String;
    //   14: iload_1
    //   15: aaload
    //   16: iconst_4
    //   17: anewarray 4	java/lang/Object
    //   20: dup
    //   21: iconst_0
    //   22: aload_0
    //   23: getfield 191	com/tencent/tbs/common/log/LogManager:mPackageName	Ljava/lang/String;
    //   26: aastore
    //   27: dup
    //   28: iconst_1
    //   29: aload_0
    //   30: getfield 193	com/tencent/tbs/common/log/LogManager:mProcessName	Ljava/lang/String;
    //   33: aastore
    //   34: dup
    //   35: iconst_2
    //   36: iload_1
    //   37: iconst_1
    //   38: iadd
    //   39: invokestatic 446	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   42: aastore
    //   43: dup
    //   44: iconst_3
    //   45: aload_0
    //   46: getfield 202	com/tencent/tbs/common/log/LogManager:mTimeFormater	Ljava/text/SimpleDateFormat;
    //   49: aload_0
    //   50: getfield 169	com/tencent/tbs/common/log/LogManager:mCurrentWritingStarts	[J
    //   53: iload_1
    //   54: laload
    //   55: invokestatic 443	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   58: invokevirtual 631	java/text/SimpleDateFormat:format	(Ljava/lang/Object;)Ljava/lang/String;
    //   61: aastore
    //   62: invokestatic 450	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   65: astore_2
    //   66: new 298	java/lang/StringBuilder
    //   69: dup
    //   70: invokespecial 299	java/lang/StringBuilder:<init>	()V
    //   73: astore_3
    //   74: aload_3
    //   75: aload_0
    //   76: getfield 151	com/tencent/tbs/common/log/LogManager:mLogFileFolder	Ljava/lang/String;
    //   79: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: pop
    //   83: aload_3
    //   84: ldc_w 305
    //   87: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: pop
    //   91: aload_3
    //   92: aload_2
    //   93: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: pop
    //   97: new 171	java/io/File
    //   100: dup
    //   101: aload_3
    //   102: invokevirtual 309	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   105: invokespecial 221	java/io/File:<init>	(Ljava/lang/String;)V
    //   108: astore_2
    //   109: aload_0
    //   110: getfield 173	com/tencent/tbs/common/log/LogManager:mCurrentWritingFiles	[Ljava/io/File;
    //   113: iload_1
    //   114: aaload
    //   115: aload_2
    //   116: invokevirtual 820	java/io/File:renameTo	(Ljava/io/File;)Z
    //   119: pop
    //   120: new 298	java/lang/StringBuilder
    //   123: dup
    //   124: invokespecial 299	java/lang/StringBuilder:<init>	()V
    //   127: astore_3
    //   128: aload_3
    //   129: ldc_w 822
    //   132: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: aload_3
    //   137: aload_2
    //   138: invokevirtual 745	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   141: pop
    //   142: aload_3
    //   143: invokevirtual 309	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   146: pop
    //   147: aload_2
    //   148: invokevirtual 225	java/io/File:exists	()Z
    //   151: ifeq +17 -> 168
    //   154: aload_2
    //   155: invokevirtual 824	java/io/File:length	()J
    //   158: lconst_0
    //   159: lcmp
    //   160: ifne +8 -> 168
    //   163: aload_2
    //   164: invokevirtual 542	java/io/File:delete	()Z
    //   167: pop
    //   168: aload_0
    //   169: getfield 169	com/tencent/tbs/common/log/LogManager:mCurrentWritingStarts	[J
    //   172: iload_1
    //   173: invokestatic 220	java/lang/System:currentTimeMillis	()J
    //   176: lastore
    //   177: aload_0
    //   178: getfield 173	com/tencent/tbs/common/log/LogManager:mCurrentWritingFiles	[Ljava/io/File;
    //   181: astore_2
    //   182: new 298	java/lang/StringBuilder
    //   185: dup
    //   186: invokespecial 299	java/lang/StringBuilder:<init>	()V
    //   189: astore_3
    //   190: aload_3
    //   191: aload_0
    //   192: getfield 151	com/tencent/tbs/common/log/LogManager:mLogFileFolder	Ljava/lang/String;
    //   195: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: pop
    //   199: aload_3
    //   200: ldc_w 305
    //   203: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   206: pop
    //   207: aload_3
    //   208: aload_0
    //   209: getfield 163	com/tencent/tbs/common/log/LogManager:mFileCurTemplates	[Ljava/lang/String;
    //   212: iload_1
    //   213: aaload
    //   214: iconst_1
    //   215: anewarray 4	java/lang/Object
    //   218: dup
    //   219: iconst_0
    //   220: aload_0
    //   221: getfield 169	com/tencent/tbs/common/log/LogManager:mCurrentWritingStarts	[J
    //   224: iload_1
    //   225: laload
    //   226: invokestatic 443	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   229: aastore
    //   230: invokestatic 450	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   233: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   236: pop
    //   237: aload_2
    //   238: iload_1
    //   239: new 171	java/io/File
    //   242: dup
    //   243: aload_3
    //   244: invokevirtual 309	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   247: invokespecial 221	java/io/File:<init>	(Ljava/lang/String;)V
    //   250: aastore
    //   251: aload_0
    //   252: getfield 173	com/tencent/tbs/common/log/LogManager:mCurrentWritingFiles	[Ljava/io/File;
    //   255: iload_1
    //   256: aaload
    //   257: invokevirtual 748	java/io/File:createNewFile	()Z
    //   260: pop
    //   261: new 298	java/lang/StringBuilder
    //   264: dup
    //   265: invokespecial 299	java/lang/StringBuilder:<init>	()V
    //   268: astore_2
    //   269: aload_2
    //   270: ldc_w 826
    //   273: invokevirtual 303	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   276: pop
    //   277: aload_2
    //   278: aload_0
    //   279: getfield 173	com/tencent/tbs/common/log/LogManager:mCurrentWritingFiles	[Ljava/io/File;
    //   282: iload_1
    //   283: aaload
    //   284: invokevirtual 745	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   287: pop
    //   288: aload_2
    //   289: invokevirtual 309	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   292: pop
    //   293: return
    //   294: astore_2
    //   295: aload_2
    //   296: invokevirtual 388	java/lang/Exception:printStackTrace	()V
    //   299: ldc 37
    //   301: aload_2
    //   302: invokevirtual 391	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   305: invokestatic 397	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   308: pop
    //   309: aload_0
    //   310: getfield 173	com/tencent/tbs/common/log/LogManager:mCurrentWritingFiles	[Ljava/io/File;
    //   313: iload_1
    //   314: aconst_null
    //   315: aastore
    //   316: aload_0
    //   317: getfield 169	com/tencent/tbs/common/log/LogManager:mCurrentWritingStarts	[J
    //   320: iload_1
    //   321: lconst_0
    //   322: lastore
    //   323: return
    //   324: return
    //   325: astore_2
    //   326: goto -158 -> 168
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	329	0	this	LogManager
    //   0	329	1	paramInt	int
    //   65	224	2	localObject	Object
    //   294	8	2	localException1	Exception
    //   325	1	2	localException2	Exception
    //   73	171	3	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   10	147	294	java/lang/Exception
    //   168	293	294	java/lang/Exception
    //   147	168	325	java/lang/Exception
  }
  
  private void startWriteThread()
  {
    if (this.mLogWriteThread == null) {
      try
      {
        this.mLogWriteThread = new Thread(this);
        this.mLogWriteThread.start();
        return;
      }
      catch (Exception localException)
      {
        this.mLogEnableMode = false;
        localException.printStackTrace();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("startWriteThread:");
        localStringBuilder.append(localException.getMessage());
        Log.w("LogManager", localStringBuilder.toString());
      }
    }
  }
  
  private boolean writeAllLogs(Writer paramWriter, boolean paramBoolean, long paramLong1, long paramLong2, int paramInt, char... paramVarArgs)
  {
    try
    {
      int j = Math.min(2, Math.max(0, paramInt - 1));
      int i = 0;
      while (i <= j)
      {
        flushLogsInCacheList(i);
        saveAsCurrentLogFile(i);
        i += 1;
      }
      appendHeaders(paramWriter);
      paramWriter.append("\r\n");
      appendPreferences(paramWriter);
      paramWriter.append("\r\n");
      appendSdkLogs(paramWriter, paramBoolean);
      paramWriter.append("\r\n");
      appendCoreLogs(paramWriter, paramBoolean);
      paramWriter.append("\r\n");
      appendAllRecords(paramWriter, paramBoolean, paramLong1, paramLong2, paramInt, paramVarArgs);
      paramWriter.append("\r\n");
      return true;
    }
    catch (Throwable paramWriter)
    {
      paramWriter.printStackTrace();
      Log.w("LogManager", paramWriter.getMessage());
      return false;
      throw paramWriter;
    }
    finally
    {
      for (;;) {}
    }
  }
  
  public void checkWUPKeyAndReport(final long paramLong, String paramString)
  {
    if (this.mLogInited)
    {
      if (!this.mLogEnableMode) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("checkWUPKeyAndReport:");
      localStringBuilder.append(paramLong);
      localStringBuilder.toString();
      new Thread(new Runnable()
      {
        public void run()
        {
          try
          {
            Thread.sleep(paramLong);
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("command from check:");
            localStringBuilder.append(this.val$wupKeyValue);
            localStringBuilder.toString();
            try
            {
              LogManager.this.reportWithCommand(this.val$wupKeyValue, true);
              return;
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
              return;
            }
          }
          catch (InterruptedException localInterruptedException)
          {
            for (;;) {}
          }
        }
      }).start();
      return;
    }
  }
  
  public void clearLog()
  {
    synchronized (this.mLogWriteQueue)
    {
      this.mLogWriteQueue.clear();
      clearOldCacheLogFiles(true);
      return;
    }
  }
  
  public String getAllHeaders()
  {
    if ((this.mLogInited) && (this.mLogEnableMode))
    {
      StringWriter localStringWriter = new StringWriter();
      try
      {
        appendHeaders(localStringWriter);
        return localStringWriter.toString();
      }
      finally {}
    }
    return null;
  }
  
  public String getAllLogs(boolean paramBoolean, long paramLong1, long paramLong2, int paramInt, char... paramVarArgs)
  {
    if ((this.mLogInited) && (this.mLogEnableMode))
    {
      StringWriter localStringWriter = new StringWriter();
      writeAllLogs(localStringWriter, paramBoolean, paramLong1, paramLong2, paramInt, paramVarArgs);
      return localStringWriter.toString();
    }
    return null;
  }
  
  public boolean getAllLogs(Writer paramWriter, boolean paramBoolean, long paramLong1, long paramLong2, int paramInt, char... paramVarArgs)
  {
    if ((this.mLogInited) && (this.mLogEnableMode)) {
      return writeAllLogs(paramWriter, paramBoolean, paramLong1, paramLong2, paramInt, paramVarArgs);
    }
    return false;
  }
  
  public String getAllPreferences()
  {
    if ((this.mLogInited) && (this.mLogEnableMode))
    {
      StringWriter localStringWriter = new StringWriter();
      try
      {
        appendPreferences(localStringWriter);
        return localStringWriter.toString();
      }
      finally {}
    }
    return null;
  }
  
  /* Error */
  public String getAllRecords(boolean paramBoolean, long paramLong1, long paramLong2, int paramInt, char... paramVarArgs)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 108	com/tencent/tbs/common/log/LogManager:mLogInited	Z
    //   4: ifeq +155 -> 159
    //   7: aload_0
    //   8: getfield 110	com/tencent/tbs/common/log/LogManager:mLogEnableMode	Z
    //   11: ifne +6 -> 17
    //   14: goto +145 -> 159
    //   17: new 852	java/io/StringWriter
    //   20: dup
    //   21: invokespecial 853	java/io/StringWriter:<init>	()V
    //   24: astore 10
    //   26: ldc 2
    //   28: monitorenter
    //   29: iconst_0
    //   30: istore 8
    //   32: iconst_2
    //   33: iconst_0
    //   34: iload 6
    //   36: iconst_1
    //   37: isub
    //   38: invokestatic 234	java/lang/Math:max	(II)I
    //   41: invokestatic 237	java/lang/Math:min	(II)I
    //   44: istore 9
    //   46: iload 8
    //   48: iload 9
    //   50: if_icmpgt +24 -> 74
    //   53: aload_0
    //   54: iload 8
    //   56: invokespecial 793	com/tencent/tbs/common/log/LogManager:flushLogsInCacheList	(I)V
    //   59: aload_0
    //   60: iload 8
    //   62: invokespecial 796	com/tencent/tbs/common/log/LogManager:saveAsCurrentLogFile	(I)V
    //   65: iload 8
    //   67: iconst_1
    //   68: iadd
    //   69: istore 8
    //   71: goto -25 -> 46
    //   74: aload_0
    //   75: aload 10
    //   77: iload_1
    //   78: invokespecial 805	com/tencent/tbs/common/log/LogManager:appendSdkLogs	(Ljava/io/Writer;Z)V
    //   81: aload 10
    //   83: ldc_w 373
    //   86: invokevirtual 863	java/io/StringWriter:append	(Ljava/lang/CharSequence;)Ljava/io/StringWriter;
    //   89: pop
    //   90: aload_0
    //   91: aload 10
    //   93: iload_1
    //   94: invokespecial 807	com/tencent/tbs/common/log/LogManager:appendCoreLogs	(Ljava/io/Writer;Z)V
    //   97: aload 10
    //   99: ldc_w 373
    //   102: invokevirtual 863	java/io/StringWriter:append	(Ljava/lang/CharSequence;)Ljava/io/StringWriter;
    //   105: pop
    //   106: aload_0
    //   107: aload 10
    //   109: iload_1
    //   110: lload_2
    //   111: lload 4
    //   113: iload 6
    //   115: aload 7
    //   117: invokespecial 840	com/tencent/tbs/common/log/LogManager:appendAllRecords	(Ljava/io/Writer;ZJJI[C)V
    //   120: aload 10
    //   122: ldc_w 373
    //   125: invokevirtual 863	java/io/StringWriter:append	(Ljava/lang/CharSequence;)Ljava/io/StringWriter;
    //   128: pop
    //   129: goto +15 -> 144
    //   132: astore 7
    //   134: goto +19 -> 153
    //   137: astore 7
    //   139: aload 7
    //   141: invokevirtual 388	java/lang/Exception:printStackTrace	()V
    //   144: ldc 2
    //   146: monitorexit
    //   147: aload 10
    //   149: invokevirtual 854	java/io/StringWriter:toString	()Ljava/lang/String;
    //   152: areturn
    //   153: ldc 2
    //   155: monitorexit
    //   156: aload 7
    //   158: athrow
    //   159: aconst_null
    //   160: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	161	0	this	LogManager
    //   0	161	1	paramBoolean	boolean
    //   0	161	2	paramLong1	long
    //   0	161	4	paramLong2	long
    //   0	161	6	paramInt	int
    //   0	161	7	paramVarArgs	char[]
    //   30	40	8	i	int
    //   44	7	9	j	int
    //   24	124	10	localStringWriter	StringWriter
    // Exception table:
    //   from	to	target	type
    //   32	46	132	finally
    //   53	65	132	finally
    //   74	129	132	finally
    //   139	144	132	finally
    //   144	147	132	finally
    //   153	156	132	finally
    //   32	46	137	java/lang/Exception
    //   53	65	137	java/lang/Exception
    //   74	129	137	java/lang/Exception
  }
  
  /* Error */
  public void header(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 108	com/tencent/tbs/common/log/LogManager:mLogInited	Z
    //   4: ifeq +57 -> 61
    //   7: aload_0
    //   8: getfield 110	com/tencent/tbs/common/log/LogManager:mLogEnableMode	Z
    //   11: ifne +4 -> 15
    //   14: return
    //   15: ldc 2
    //   17: monitorenter
    //   18: aload_0
    //   19: getfield 126	com/tencent/tbs/common/log/LogManager:mLogHeaders	Ljava/util/Map;
    //   22: aload_1
    //   23: aload_2
    //   24: invokeinterface 867 3 0
    //   29: pop
    //   30: goto +22 -> 52
    //   33: astore_1
    //   34: goto +22 -> 56
    //   37: astore_1
    //   38: aload_1
    //   39: invokevirtual 398	java/lang/Throwable:printStackTrace	()V
    //   42: ldc 37
    //   44: aload_1
    //   45: invokevirtual 399	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   48: invokestatic 397	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   51: pop
    //   52: ldc 2
    //   54: monitorexit
    //   55: return
    //   56: ldc 2
    //   58: monitorexit
    //   59: aload_1
    //   60: athrow
    //   61: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	62	0	this	LogManager
    //   0	62	1	paramString1	String
    //   0	62	2	paramString2	String
    // Exception table:
    //   from	to	target	type
    //   18	30	33	finally
    //   38	52	33	finally
    //   52	55	33	finally
    //   56	59	33	finally
    //   18	30	37	java/lang/Throwable
  }
  
  public void log(byte paramByte, char paramChar, int paramInt, String paramString, Object... paramVarArgs)
  {
    log(System.currentTimeMillis(), paramByte, paramChar, paramInt, paramString, paramVarArgs);
  }
  
  public void log(long paramLong, byte paramByte, char paramChar, int paramInt, String arg6, Object... paramVarArgs)
  {
    int i;
    if (this.mLogInited)
    {
      if (!this.mLogEnableMode) {
        return;
      }
      i = Math.min(2, Math.max(0, paramByte - 1));
      localObject2 = null;
      localObject1 = localObject2;
      if (??? == null) {}
    }
    try
    {
      try
      {
        localObject1 = String.format(???, paramVarArgs);
      }
      catch (Throwable ???)
      {
        break label176;
      }
    }
    catch (Exception ???)
    {
      for (;;)
      {
        label176:
        localObject1 = localObject2;
        continue;
        ??? = "";
      }
    }
    if (localObject1 != null)
    {
      ??? = ((String)localObject1).replace('\r', ' ').replace('\n', ' ');
      paramVarArgs = String.format("[%d][%d][%c][%d]%s", new Object[] { Long.valueOf(paramLong), Byte.valueOf(paramByte), Character.valueOf(paramChar), Integer.valueOf(paramInt), encrypt(???) });
      synchronized (this.mLogWriteQueue)
      {
        this.mLogWriteQueue.add(new LogEntityItem(i, paramVarArgs));
        return;
      }
      ???.printStackTrace();
      Log.w("LogManager", ???.getMessage());
      return;
      return;
    }
  }
  
  /* Error */
  public void preference(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 108	com/tencent/tbs/common/log/LogManager:mLogInited	Z
    //   4: ifeq +57 -> 61
    //   7: aload_0
    //   8: getfield 110	com/tencent/tbs/common/log/LogManager:mLogEnableMode	Z
    //   11: ifne +4 -> 15
    //   14: return
    //   15: ldc 2
    //   17: monitorenter
    //   18: aload_0
    //   19: getfield 128	com/tencent/tbs/common/log/LogManager:mLogPreferences	Ljava/util/Map;
    //   22: aload_1
    //   23: aload_2
    //   24: invokeinterface 867 3 0
    //   29: pop
    //   30: goto +22 -> 52
    //   33: astore_1
    //   34: goto +22 -> 56
    //   37: astore_1
    //   38: aload_1
    //   39: invokevirtual 398	java/lang/Throwable:printStackTrace	()V
    //   42: ldc 37
    //   44: aload_1
    //   45: invokevirtual 399	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   48: invokestatic 397	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   51: pop
    //   52: ldc 2
    //   54: monitorexit
    //   55: return
    //   56: ldc 2
    //   58: monitorexit
    //   59: aload_1
    //   60: athrow
    //   61: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	62	0	this	LogManager
    //   0	62	1	paramString1	String
    //   0	62	2	paramString2	String
    // Exception table:
    //   from	to	target	type
    //   18	30	33	finally
    //   38	52	33	finally
    //   52	55	33	finally
    //   56	59	33	finally
    //   18	30	37	java/lang/Throwable
  }
  
  public void reloadAllPreferences()
  {
    if (this.mLogInited)
    {
      if (!this.mLogEnableMode) {
        return;
      }
      try
      {
        if (TbsBaseModuleShell.getCallerContext() == null) {
          return;
        }
        Object localObject1 = PublicSettingManager.getInstance().getPreference();
        if (localObject1 == null) {
          return;
        }
        localObject1 = ((SharedPreferences)localObject1).getAll();
        Object localObject2 = ((Map)localObject1).keySet().iterator();
        while (((Iterator)localObject2).hasNext())
        {
          String str = (String)((Iterator)localObject2).next();
          Object localObject3 = ((Map)localObject1).get(str);
          if (localObject3 != null) {
            preference(str, String.valueOf(localObject3));
          }
        }
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("reloadAllPreferences:");
        ((StringBuilder)localObject2).append(localException.getMessage());
        Log.w("LogManager", ((StringBuilder)localObject2).toString());
      }
    }
  }
  
  public void report(long paramLong1, long paramLong2, int paramInt, char... paramVarArgs)
  {
    if (this.mLogInited)
    {
      if (!this.mLogEnableMode) {
        return;
      }
      if (this.mLogFileFolder == null) {
        return;
      }
    }
    for (;;)
    {
      try
      {
        Math.min(2, Math.max(0, paramInt - 1));
        l = paramLong2;
        if (paramLong2 <= 0L) {
          l = System.currentTimeMillis();
        }
        paramLong2 = paramLong1;
        if (paramLong1 > 0L) {
          break label271;
        }
        paramLong2 = l - this.mMaxLogCacheTime;
      }
      catch (Exception paramVarArgs)
      {
        long l;
        Object localObject;
        paramVarArgs.printStackTrace();
        return;
      }
      paramLong1 = (l + paramLong2) / 2L;
      paramInt = Math.max(1, (int)((float)((l - paramLong2) / 2L) * 1.0F / 1000.0F / 60.0F / 60.0F));
      localObject = this.mTimeFormater.format(Long.valueOf(paramLong1));
      paramVarArgs = new StringBuffer();
      paramVarArgs.append("c=3&t=0&s=0");
      paramVarArgs.append("&lv=");
      paramVarArgs.append(String.valueOf(i));
      paramVarArgs.append("&date=");
      paramVarArgs.append((String)localObject);
      paramVarArgs.append("&adate=");
      paramVarArgs.append(String.valueOf(paramInt));
      paramVarArgs.append("&bdate=");
      paramVarArgs.append(String.valueOf(paramInt));
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("command from debugx5:");
      ((StringBuilder)localObject).append(paramVarArgs.toString());
      ((StringBuilder)localObject).toString();
      reportWithCommand(paramVarArgs.toString(), false);
      return;
      return;
      label271:
      int j = 1;
      int i = 0;
      while (j <= paramInt)
      {
        i |= 1 << j - 1;
        j += 1;
      }
    }
  }
  
  public void run()
  {
    for (;;)
    {
      if ((this.mLogInited) && (this.mLogEnableMode))
      {
        Object localObject1 = null;
        try
        {
          synchronized (this.mLogWriteQueue)
          {
            i = this.mLogWriteQueue.size();
            m = 0;
            if (i > 0) {
              localObject1 = (LogEntityItem)this.mLogWriteQueue.remove(0);
            }
            if (localObject1 == null) {}
            try
            {
              Thread.sleep(500L);
            }
            catch (Exception localException2)
            {
              for (;;)
              {
                continue;
                i = 0;
                continue;
                int j = 0;
                continue;
                int k = 0;
                if ((i == 0) && (j == 0))
                {
                  i = m;
                  if (k == 0) {}
                }
                else
                {
                  i = 1;
                }
              }
            }
            continue;
            try
            {
              int n = ((LogEntityItem)localObject1).index;
              localObject1 = ((LogEntityItem)localObject1).logline;
              this.mLogCacheEntityArray[n].add(localObject1);
              ??? = this.mCacheCharCounts;
              ???[n] += ((String)localObject1).length();
              if (this.mLogFileFolder == null) {
                reloadTbsLogSavePath();
              }
              if (this.mCurrentWritingFiles[n] == null) {
                reloadLastWritingFile(n);
              }
              if ((this.mCurrentWritingFiles[n] != null) && (System.currentTimeMillis() - this.mCurrentWritingStarts[n] > this.mMaxLogFileTime))
              {
                saveAsCurrentLogFile(n);
                clearOldCacheLogFiles(false);
              }
              if (this.mLogFlushMode == 'A')
              {
                if (System.currentTimeMillis() - this.mLastFlushTimes[n] < this.mMaxFlushTime) {
                  break label490;
                }
                i = 1;
                if (this.mCacheCharCounts[n] <= this.mMaxFlushSize) {
                  break label495;
                }
                j = 1;
                if (this.mLogCacheEntityArray[n].size() < this.mMaxFlushCount) {
                  break label500;
                }
                k = 1;
                break label502;
              }
              if (this.mLogFlushMode == 'T')
              {
                i = m;
                if (System.currentTimeMillis() - this.mLastFlushTimes[n] >= this.mMaxFlushTime) {
                  i = 1;
                }
              }
              else if (this.mLogFlushMode == 'S')
              {
                i = m;
                if (this.mCacheCharCounts[n] > this.mMaxFlushSize) {
                  i = 1;
                }
              }
              else
              {
                i = m;
                if (this.mLogFlushMode == 'C')
                {
                  i = m;
                  if (this.mLogCacheEntityArray[n].size() >= this.mMaxFlushCount) {
                    i = 1;
                  }
                }
              }
              if (i != 0)
              {
                flushLogsInCacheList(n);
                if (this.mCurrentWritingFiles[n].length() >= this.mMaxLogFileSize) {
                  saveAsCurrentLogFile(n);
                }
              }
            }
            finally {}
          }
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
          ??? = new StringBuilder();
          ((StringBuilder)???).append("run()");
          ((StringBuilder)???).append(localException1.getMessage());
          Log.w("LogManager", ((StringBuilder)???).toString());
        }
      }
    }
  }
  
  private static class LogEntityItem
  {
    public int index = 0;
    public String logline = null;
    
    public LogEntityItem(int paramInt, String paramString)
    {
      this.index = paramInt;
      this.logline = paramString;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\log\LogManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */