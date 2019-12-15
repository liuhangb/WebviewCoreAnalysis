package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.widget.FrameLayout;
import com.tencent.common.http.Apn;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.ReaderCallbackAction;
import com.tencent.mtt.external.reader.base.ReaderCheck;
import com.tencent.mtt.external.reader.base.ReaderCheck.CheckCallback;
import com.tencent.mtt.external.reader.base.ReaderLoadingView;
import com.tencent.mtt.external.reader.base.ReaderMessage;
import com.tencent.mtt.external.reader.base.ReaderMessage.MessageEvent;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.stat.TBSStatManager;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MttReaderCheck
  extends ReaderCheck
{
  public static final int DOWNLOAD_STATE_CANCELED = 2;
  public static final int DOWNLOAD_STATE_FAILED = 3;
  public static final int DOWNLOAD_STATE_INIT = 0;
  public static final int DOWNLOAD_STATE_PREPARED = 1;
  public static final int DOWNLOAD_STATE_UNKNOWN = -1;
  public static final int HIDE_PROGRESSTIP = 1;
  public static final int READER_OPEN_ERROR = 7;
  public static final int READER_SO_FAILE = 4;
  public static final int READER_SO_INTO_START = 9;
  public static final int READER_SO_PROGRESS = 5;
  public static final int READER_SO_START = 6;
  public static final int READER_SO_SUCCESS = 2;
  public static final int READER_SO_WILL_START = 8;
  public static final int READER_WAIT_IN_QUEUE = 3;
  private static Map<String, Class<?>> jdField_a_of_type_JavaUtilMap = new HashMap();
  private int jdField_a_of_type_Int = -1;
  Context jdField_a_of_type_AndroidContentContext = null;
  ReaderCallbackAction jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction = null;
  ReaderMessage.MessageEvent jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = null;
  ReaderMessage jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage = new ReaderMessage();
  FileLoadFailedView jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView = null;
  ReaderFileStatistic jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic = null;
  ReaderFiletypeDetector jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector = null;
  DexClassLoader jdField_a_of_type_DalvikSystemDexClassLoader = null;
  boolean jdField_a_of_type_Boolean = false;
  String b = "";
  
  public MttReaderCheck(Context paramContext, String paramString, DexClassLoader paramDexClassLoader, ReaderCallbackAction paramReaderCallbackAction, ReaderFileStatistic paramReaderFileStatistic)
  {
    super(paramString);
    this.b = paramString;
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_DalvikSystemDexClassLoader = paramDexClassLoader;
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic = paramReaderFileStatistic;
    this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction = paramReaderCallbackAction;
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector = new ReaderFiletypeDetector(paramString, a());
    b();
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.setEvent(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent);
  }
  
  private DexClassLoader a(Context paramContext, String paramString1, String paramString2)
  {
    if (paramContext == null) {
      return null;
    }
    Object localObject = paramContext.getApplicationContext();
    File localFile = ((Context)localObject).getDir("dynamic_jar_output", 0);
    if (localFile == null) {
      return null;
    }
    if (TextUtils.isEmpty(paramString1)) {
      paramContext = new File(localFile, paramString2);
    } else {
      paramContext = new File(paramString1, paramString2);
    }
    paramString1 = new StringBuilder();
    paramString1.append("[getDexClassLoader] dexFile path:");
    paramString1.append(paramContext.getAbsolutePath());
    LogUtils.d("mttlocalcheck", paramString1.toString());
    try
    {
      paramString1 = FileUtils.getNativeLibraryDir((Context)localObject);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("libPath:");
      ((StringBuilder)localObject).append(paramString1);
      LogUtils.d("mttlocalcheck", ((StringBuilder)localObject).toString());
      paramContext = new DexClassLoader(paramContext.getAbsolutePath(), localFile.getAbsolutePath(), paramString1, this.jdField_a_of_type_DalvikSystemDexClassLoader);
      return paramContext;
    }
    catch (Exception paramContext)
    {
      paramString1 = new StringBuilder();
      paramString1.append("[getDexClassLoader] ");
      paramString1.append(paramString2);
      paramString1.append(" failed");
      LogUtils.d("mttlocalcheck", paramString1.toString());
      paramContext.printStackTrace();
    }
    return null;
  }
  
  MttFileLoadingView.FileLoadingCallback a()
  {
    new MttFileLoadingView.FileLoadingCallback()
    {
      public void handleLoadingCancel()
      {
        if (Apn.is3GOr2GMode()) {
          TBSStatManager.getInstance().userBehaviorStatistics("AHNG802");
        }
        MttReaderCheck.a(MttReaderCheck.this, 2);
        MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic.openResult = 8;
        MttReaderCheck.this.a(FileLoadFailedView.FailedTypeSoNeedDownload);
        MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.cancel();
      }
    };
  }
  
  ReaderFiletypeDetector.onFiletypeDetectorCallback a()
  {
    new ReaderFiletypeDetector.onFiletypeDetectorCallback()
    {
      public void onSoDownloadError(int paramAnonymousInt)
      {
        MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(4, paramAnonymousInt);
      }
      
      public void onSoDownloadProgress(int paramAnonymousInt)
      {
        MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(5, paramAnonymousInt);
      }
      
      public void onSoDownloadStart(int paramAnonymousInt)
      {
        MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(6, paramAnonymousInt);
      }
      
      public void onSoDownloadSuccess(int paramAnonymousInt)
      {
        MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(2);
      }
      
      public void onSoDownloadWillStart()
      {
        MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(8);
      }
      
      public boolean onSoILoad()
      {
        MttReaderCheck localMttReaderCheck = MttReaderCheck.this;
        return localMttReaderCheck.a(localMttReaderCheck.getReaderPath(), MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getDexPath());
      }
    };
  }
  
  /* Error */
  Object a(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 201	com/tencent/mtt/external/reader/utils/AppEngine:getInstance	()Lcom/tencent/mtt/external/reader/utils/AppEngine;
    //   4: invokevirtual 202	com/tencent/mtt/external/reader/utils/AppEngine:getApplicationContext	()Landroid/content/Context;
    //   7: aload_1
    //   8: aload_2
    //   9: invokespecial 204	com/tencent/mtt/external/reader/internal/MttReaderCheck:a	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Ldalvik/system/DexClassLoader;
    //   12: astore 8
    //   14: iload 4
    //   16: ifeq +19 -> 35
    //   19: getstatic 61	com/tencent/mtt/external/reader/internal/MttReaderCheck:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   22: aload_3
    //   23: invokeinterface 210 2 0
    //   28: checkcast 212	java/lang/Class
    //   31: astore_1
    //   32: goto +5 -> 37
    //   35: aconst_null
    //   36: astore_1
    //   37: aload_1
    //   38: astore_2
    //   39: aload_1
    //   40: ifnonnull +200 -> 240
    //   43: aload_1
    //   44: astore_2
    //   45: aload 8
    //   47: ifnull +193 -> 240
    //   50: aload_1
    //   51: astore_2
    //   52: new 140	java/lang/StringBuilder
    //   55: dup
    //   56: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   59: astore 9
    //   61: aload_1
    //   62: astore_2
    //   63: aload 9
    //   65: ldc -42
    //   67: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: pop
    //   71: aload_1
    //   72: astore_2
    //   73: aload 9
    //   75: aload 8
    //   77: invokevirtual 215	dalvik/system/DexClassLoader:toString	()Ljava/lang/String;
    //   80: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: pop
    //   84: aload_1
    //   85: astore_2
    //   86: aload 9
    //   88: ldc -39
    //   90: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: pop
    //   94: aload_1
    //   95: astore_2
    //   96: aload 9
    //   98: aload_3
    //   99: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: aload_1
    //   104: astore_2
    //   105: ldc -103
    //   107: aload 9
    //   109: invokevirtual 156	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   112: invokestatic 161	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   115: aload_1
    //   116: astore_2
    //   117: aload 8
    //   119: aload_3
    //   120: invokevirtual 221	dalvik/system/DexClassLoader:loadClass	(Ljava/lang/String;)Ljava/lang/Class;
    //   123: astore_1
    //   124: iload 4
    //   126: ifeq +20 -> 146
    //   129: aload_1
    //   130: ifnull +16 -> 146
    //   133: aload_1
    //   134: astore_2
    //   135: getstatic 61	com/tencent/mtt/external/reader/internal/MttReaderCheck:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   138: aload_3
    //   139: aload_1
    //   140: invokeinterface 225 3 0
    //   145: pop
    //   146: aload_1
    //   147: astore_2
    //   148: new 140	java/lang/StringBuilder
    //   151: dup
    //   152: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   155: astore 8
    //   157: aload_1
    //   158: astore_2
    //   159: aload 8
    //   161: ldc -29
    //   163: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   166: pop
    //   167: aload_1
    //   168: astore_2
    //   169: aload 8
    //   171: aload_3
    //   172: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   175: pop
    //   176: aload_1
    //   177: astore_2
    //   178: ldc -103
    //   180: aload 8
    //   182: invokevirtual 156	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   185: invokestatic 161	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   188: aload_1
    //   189: astore_2
    //   190: goto +50 -> 240
    //   193: astore_1
    //   194: new 140	java/lang/StringBuilder
    //   197: dup
    //   198: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   201: astore 8
    //   203: aload 8
    //   205: ldc -27
    //   207: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: pop
    //   211: aload 8
    //   213: aload_3
    //   214: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   217: pop
    //   218: aload 8
    //   220: ldc -78
    //   222: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   225: pop
    //   226: ldc -103
    //   228: aload 8
    //   230: invokevirtual 156	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   233: invokestatic 161	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   236: aload_1
    //   237: invokevirtual 181	java/lang/Exception:printStackTrace	()V
    //   240: iconst_0
    //   241: anewarray 231	java/lang/Object
    //   244: astore 8
    //   246: aload_2
    //   247: ifnull +215 -> 462
    //   250: aload 8
    //   252: arraylength
    //   253: iflt +44 -> 297
    //   256: aload 8
    //   258: arraylength
    //   259: anewarray 212	java/lang/Class
    //   262: astore_3
    //   263: iconst_0
    //   264: istore 5
    //   266: aload_3
    //   267: astore_1
    //   268: iload 5
    //   270: aload 8
    //   272: arraylength
    //   273: if_icmpge +26 -> 299
    //   276: aload_3
    //   277: iload 5
    //   279: aload 8
    //   281: iload 5
    //   283: aaload
    //   284: invokevirtual 235	java/lang/Object:getClass	()Ljava/lang/Class;
    //   287: aastore
    //   288: iload 5
    //   290: iconst_1
    //   291: iadd
    //   292: istore 5
    //   294: goto -28 -> 266
    //   297: aconst_null
    //   298: astore_1
    //   299: aload_2
    //   300: invokevirtual 239	java/lang/Class:getDeclaredConstructors	()[Ljava/lang/reflect/Constructor;
    //   303: astore_3
    //   304: new 140	java/lang/StringBuilder
    //   307: dup
    //   308: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   311: astore_2
    //   312: aload_2
    //   313: ldc -15
    //   315: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: pop
    //   319: aload_2
    //   320: aload_3
    //   321: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   324: pop
    //   325: ldc -103
    //   327: aload_2
    //   328: invokevirtual 156	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   331: invokestatic 161	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   334: aload_3
    //   335: ifnull +176 -> 511
    //   338: iconst_0
    //   339: istore 5
    //   341: iload 5
    //   343: aload_3
    //   344: arraylength
    //   345: if_icmpge +166 -> 511
    //   348: aload_3
    //   349: iload 5
    //   351: aaload
    //   352: astore_2
    //   353: aload_2
    //   354: invokevirtual 250	java/lang/reflect/Constructor:getParameterTypes	()[Ljava/lang/Class;
    //   357: astore 9
    //   359: aload 9
    //   361: ifnonnull +103 -> 464
    //   364: aload_1
    //   365: ifnull +17 -> 382
    //   368: goto +134 -> 502
    //   371: aload 9
    //   373: arraylength
    //   374: aload_1
    //   375: arraylength
    //   376: if_icmpeq +6 -> 382
    //   379: goto +123 -> 502
    //   382: aload 9
    //   384: ifnull +87 -> 471
    //   387: aload 9
    //   389: arraylength
    //   390: istore 6
    //   392: goto +82 -> 474
    //   395: iload 7
    //   397: iload 6
    //   399: if_icmpge +90 -> 489
    //   402: aload 9
    //   404: iload 7
    //   406: aaload
    //   407: aload_1
    //   408: iload 7
    //   410: aaload
    //   411: invokevirtual 254	java/lang/Class:isAssignableFrom	(Ljava/lang/Class;)Z
    //   414: ifne +66 -> 480
    //   417: iconst_0
    //   418: istore 6
    //   420: goto +72 -> 492
    //   423: aload_1
    //   424: ifnull +38 -> 462
    //   427: aload_1
    //   428: aload 8
    //   430: invokevirtual 258	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   433: astore_1
    //   434: aload_1
    //   435: areturn
    //   436: astore_1
    //   437: aload_1
    //   438: invokevirtual 259	java/lang/reflect/InvocationTargetException:printStackTrace	()V
    //   441: aconst_null
    //   442: areturn
    //   443: astore_1
    //   444: aload_1
    //   445: invokevirtual 260	java/lang/IllegalAccessException:printStackTrace	()V
    //   448: aconst_null
    //   449: areturn
    //   450: astore_1
    //   451: aload_1
    //   452: invokevirtual 261	java/lang/InstantiationException:printStackTrace	()V
    //   455: aconst_null
    //   456: areturn
    //   457: astore_1
    //   458: aload_1
    //   459: invokevirtual 262	java/lang/IllegalArgumentException:printStackTrace	()V
    //   462: aconst_null
    //   463: areturn
    //   464: aload_1
    //   465: ifnonnull -94 -> 371
    //   468: goto +34 -> 502
    //   471: iconst_0
    //   472: istore 6
    //   474: iconst_0
    //   475: istore 7
    //   477: goto -82 -> 395
    //   480: iload 7
    //   482: iconst_1
    //   483: iadd
    //   484: istore 7
    //   486: goto -91 -> 395
    //   489: iconst_1
    //   490: istore 6
    //   492: iload 6
    //   494: ifeq +8 -> 502
    //   497: aload_2
    //   498: astore_1
    //   499: goto -76 -> 423
    //   502: iload 5
    //   504: iconst_1
    //   505: iadd
    //   506: istore 5
    //   508: goto -167 -> 341
    //   511: aconst_null
    //   512: astore_1
    //   513: goto -90 -> 423
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	516	0	this	MttReaderCheck
    //   0	516	1	paramString1	String
    //   0	516	2	paramString2	String
    //   0	516	3	paramString3	String
    //   0	516	4	paramBoolean	boolean
    //   264	243	5	i	int
    //   390	103	6	j	int
    //   395	90	7	k	int
    //   12	417	8	localObject1	Object
    //   59	344	9	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   52	61	193	java/lang/Exception
    //   63	71	193	java/lang/Exception
    //   73	84	193	java/lang/Exception
    //   86	94	193	java/lang/Exception
    //   96	103	193	java/lang/Exception
    //   105	115	193	java/lang/Exception
    //   117	124	193	java/lang/Exception
    //   135	146	193	java/lang/Exception
    //   148	157	193	java/lang/Exception
    //   159	167	193	java/lang/Exception
    //   169	176	193	java/lang/Exception
    //   178	188	193	java/lang/Exception
    //   299	334	436	java/lang/reflect/InvocationTargetException
    //   341	348	436	java/lang/reflect/InvocationTargetException
    //   353	359	436	java/lang/reflect/InvocationTargetException
    //   371	379	436	java/lang/reflect/InvocationTargetException
    //   387	392	436	java/lang/reflect/InvocationTargetException
    //   402	417	436	java/lang/reflect/InvocationTargetException
    //   427	434	436	java/lang/reflect/InvocationTargetException
    //   299	334	443	java/lang/IllegalAccessException
    //   341	348	443	java/lang/IllegalAccessException
    //   353	359	443	java/lang/IllegalAccessException
    //   371	379	443	java/lang/IllegalAccessException
    //   387	392	443	java/lang/IllegalAccessException
    //   402	417	443	java/lang/IllegalAccessException
    //   427	434	443	java/lang/IllegalAccessException
    //   299	334	450	java/lang/InstantiationException
    //   341	348	450	java/lang/InstantiationException
    //   353	359	450	java/lang/InstantiationException
    //   371	379	450	java/lang/InstantiationException
    //   387	392	450	java/lang/InstantiationException
    //   402	417	450	java/lang/InstantiationException
    //   427	434	450	java/lang/InstantiationException
    //   299	334	457	java/lang/IllegalArgumentException
    //   341	348	457	java/lang/IllegalArgumentException
    //   353	359	457	java/lang/IllegalArgumentException
    //   371	379	457	java/lang/IllegalArgumentException
    //   387	392	457	java/lang/IllegalArgumentException
    //   402	417	457	java/lang/IllegalArgumentException
    //   427	434	457	java/lang/IllegalArgumentException
  }
  
  void a()
  {
    FileLoadFailedView localFileLoadFailedView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView;
    if (localFileLoadFailedView != null)
    {
      localFileLoadFailedView.clearAndRemoveFromParent();
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView = null;
    }
  }
  
  void a(int paramInt)
  {
    if ((this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView == null) && (this.mLoadingView != null) && (this.mLoadingView.getFrameLayout() != null))
    {
      FileLoadFailedView.FileReLoadCallBack local1 = new FileLoadFailedView.FileReLoadCallBack()
      {
        public void fileReLoadCallback() {}
        
        public void fileSoReloadCallback()
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("fileSoReloadCallback, mDownloadState=");
          localStringBuilder.append(MttReaderCheck.a(MttReaderCheck.this));
          LogUtils.d("MttReaderCheck", localStringBuilder.toString());
          if (MttReaderCheck.a(MttReaderCheck.this) == 2)
          {
            MttReaderCheck.this.a.prepare(false);
            if (Apn.is3GOr2GMode()) {
              TBSStatManager.getInstance().userBehaviorStatistics("AHNG801");
            }
          }
          else if (MttReaderCheck.a(MttReaderCheck.this) == 3)
          {
            MttReaderCheck.this.a.prepare(false);
            if (Apn.is3GOr2GMode()) {
              TBSStatManager.getInstance().userBehaviorStatistics("AHNG803");
            }
          }
          else if (MttReaderCheck.a(MttReaderCheck.this) == 1)
          {
            if (Apn.is3GOr2GMode()) {
              TBSStatManager.getInstance().userBehaviorStatistics("AHNG801");
            }
            MttReaderCheck.this.a.downloadSo(false);
          }
          MttReaderCheck.this.a();
        }
      };
      Bundle localBundle = new Bundle();
      localBundle.putString("KEY_SO_SIZE", this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getSoSize());
      localBundle.putString("KEY_FILE_TYPE", this.b);
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView = new FileLoadFailedView(this.jdField_a_of_type_AndroidContentContext, this.mLoadingView.getFrameLayout(), local1, paramInt, localBundle);
    }
  }
  
  void a(int paramInt1, int paramInt2)
  {
    MttFilePreDownload.getInstance().start();
    if (this.mLoadingView != null) {
      this.mLoadingView.getFrameLayout().setVisibility(0);
    }
    if (this.mCallback == null) {
      return;
    }
    Object localObject;
    if (paramInt1 == 0)
    {
      if (this.mLoadingView != null)
      {
        this.mLoadingView.setText(TBSResources.getString("reader_progress_tip"));
        this.mLoadingView.setCancelable(false);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getReaderPath()=");
      ((StringBuilder)localObject).append(getReaderPath());
      ((StringBuilder)localObject).append(" mReaderTypeDectector.getDexPath()=");
      ((StringBuilder)localObject).append(this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getDexPath());
      ((StringBuilder)localObject).append(" mReaderTypeDectector.getDexClass()=");
      ((StringBuilder)localObject).append(this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getDexClass());
      LogUtils.d("MttReaderCheck", ((StringBuilder)localObject).toString());
      localObject = a(getReaderPath(), this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getDexPath(), this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getDexClass(), true);
      this.mCallback.onCheckEvent(paramInt1, paramInt2, localObject);
      if (localObject == null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("MttReaderCheck notifyEvent getReader ERR,dex=");
        ((StringBuilder)localObject).append(this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getDexPath());
        ((StringBuilder)localObject).append(",path=");
        ((StringBuilder)localObject).append(getReaderPath());
        localObject = ((StringBuilder)localObject).toString();
        this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic.doReport(null, 3, (String)localObject);
      }
    }
    else if (1 == paramInt1)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("notifyEnvet:err=");
      ((StringBuilder)localObject).append(paramInt2);
      LogUtils.d("MttReaderCheck", ((StringBuilder)localObject).toString());
      this.mCallback.onCheckEvent(paramInt1, paramInt2, null);
    }
  }
  
  void a(int paramInt, Object paramObject1, Object paramObject2)
  {
    ReaderCallbackAction localReaderCallbackAction = this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction;
    if (localReaderCallbackAction != null) {
      localReaderCallbackAction.doCallBackEvent(paramInt, paramObject1, paramObject2);
    }
  }
  
  boolean a(String paramString1, String paramString2)
  {
    if (!new File(paramString1, paramString2).exists())
    {
      paramString1 = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic;
      paramString1.openResult = 2;
      paramString1.errCode = 1005;
      paramString1.doReport(null, 1005, "MttReaderCheck:checkClass checkClass:err");
      return false;
    }
    return a(this.jdField_a_of_type_AndroidContentContext, paramString1, paramString2) != null;
  }
  
  void b()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = new ReaderMessage.MessageEvent()
    {
      public void onMessage(Message paramAnonymousMessage)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("MttReaderCheck onMessage: ");
        localStringBuilder.append(paramAnonymousMessage.what);
        LogUtils.d("MttReaderCheck", localStringBuilder.toString());
        switch (paramAnonymousMessage.what)
        {
        case 3: 
        case 7: 
        default: 
          
        case 9: 
          if ((MttReaderCheck.a(MttReaderCheck.this) == 0) && (Apn.is3GOr2GMode()))
          {
            paramAnonymousMessage = new Bundle();
            MttReaderCheck.this.a(5027, null, paramAnonymousMessage);
            if (paramAnonymousMessage.getBoolean("into_downloading", false))
            {
              MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.downloadSo(false);
              return;
            }
            MttReaderCheck.a(MttReaderCheck.this, 1);
            MttReaderCheck.this.a(FileLoadFailedView.FailedTypeSoNeedDownload);
            return;
          }
          MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.downloadSo(false);
          return;
        case 8: 
          if (MttReaderCheck.this.mLoadingView != null)
          {
            MttReaderCheck.this.mLoadingView.getFrameLayout().setVisibility(0);
            return;
          }
          break;
        case 6: 
          if (MttReaderCheck.this.mLoadingView != null)
          {
            MttReaderCheck.this.mLoadingView.setTotalSize(paramAnonymousMessage.arg1);
            return;
          }
          break;
        case 5: 
          int i = paramAnonymousMessage.arg1;
          if (MttReaderCheck.this.mLoadingView != null) {
            MttReaderCheck.this.mLoadingView.setDetailProgress(i);
          }
          MttReaderCheck.this.a(5028, Integer.valueOf(i), null);
          return;
        case 4: 
          MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic.errCode = paramAnonymousMessage.arg1;
          if (3010 == MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic.errCode) {
            MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic.openResult = 8;
          } else {
            MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic.openResult = 2;
          }
          MttReaderCheck.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic.addToStatManager(false);
          MttReaderCheck.a(MttReaderCheck.this, 3);
          MttReaderCheck.this.a(FileLoadFailedView.FailedTypeSo);
          TBSStatManager.getInstance().userBehaviorStatistics("AHNG819");
          MttReaderCheck.this.a(1, paramAnonymousMessage.arg1);
          return;
        case 2: 
          TBSStatManager.getInstance().userBehaviorStatistics("AHNG818");
          MttReaderCheck.a(MttReaderCheck.this, -1);
          MttReaderCheck.this.a(0, 0);
        }
      }
    };
  }
  
  public void cancel()
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic.openResult == -1) {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic.openResult = 8;
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.finish();
  }
  
  public void check()
  {
    if (this.mLoadingView != null)
    {
      this.mLoadingView.setCallbackListener(a());
      this.mLoadingView.setCancelable(true);
      ReaderLoadingView localReaderLoadingView = this.mLoadingView;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.b);
      localStringBuilder.append(TBSResources.getString("reader_progress_on_loading_tip"));
      localReaderLoadingView.setText(localStringBuilder.toString());
      this.mLoadingView.getFrameLayout().setVisibility(4);
    }
    this.jdField_a_of_type_Int = 0;
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.prepare(false);
  }
  
  public void close()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.cancelAll();
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.finish();
    a();
    setCallback(null);
    setView(null);
    this.jdField_a_of_type_AndroidContentContext = null;
    this.jdField_a_of_type_ComTencentMttExternalReaderReaderCallbackAction = null;
    this.jdField_a_of_type_Int = -1;
  }
  
  public String getReaderPath()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getSoCachePath();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\MttReaderCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */