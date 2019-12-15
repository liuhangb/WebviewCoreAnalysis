package com.tencent.common.http;

import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.ThreadUtils;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class QueenDirectCache
{
  static QueenDirectCache jdField_a_of_type_ComTencentCommonHttpQueenDirectCache;
  ConcurrentHashMap<String, Integer> jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap = null;
  boolean jdField_a_of_type_Boolean = false;
  
  public static QueenDirectCache getInstance()
  {
    QueenDirectCache localQueenDirectCache = jdField_a_of_type_ComTencentCommonHttpQueenDirectCache;
    if (localQueenDirectCache != null) {
      return localQueenDirectCache;
    }
    try
    {
      if (jdField_a_of_type_ComTencentCommonHttpQueenDirectCache == null)
      {
        jdField_a_of_type_ComTencentCommonHttpQueenDirectCache = new QueenDirectCache();
        jdField_a_of_type_ComTencentCommonHttpQueenDirectCache.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap = new ConcurrentHashMap();
      }
      return jdField_a_of_type_ComTencentCommonHttpQueenDirectCache;
    }
    finally {}
  }
  
  final File a()
  {
    String str = ThreadUtils.MTT_MAIN_PROCESS_NAME.replace(':', '-');
    File localFile = FileUtilsF.getDataDir();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(".directCache_");
    localStringBuilder.append(str);
    return new File(localFile, localStringBuilder.toString());
  }
  
  public void clearCache() {}
  
  public boolean isIpDirect(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    return this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.containsKey(paramString);
  }
  
  /* Error */
  public void loadCache()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 17	com/tencent/common/http/QueenDirectCache:jdField_a_of_type_Boolean	Z
    //   4: ifeq +4 -> 8
    //   7: return
    //   8: aload_0
    //   9: iconst_1
    //   10: putfield 17	com/tencent/common/http/QueenDirectCache:jdField_a_of_type_Boolean	Z
    //   13: aload_0
    //   14: invokevirtual 85	com/tencent/common/http/QueenDirectCache:a	()Ljava/io/File;
    //   17: astore 11
    //   19: new 87	com/tencent/common/utils/QBFileLock
    //   22: dup
    //   23: aload 11
    //   25: invokevirtual 90	java/io/File:getParentFile	()Ljava/io/File;
    //   28: aload 11
    //   30: invokevirtual 93	java/io/File:getName	()Ljava/lang/String;
    //   33: invokespecial 94	com/tencent/common/utils/QBFileLock:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   36: astore 10
    //   38: aconst_null
    //   39: astore 8
    //   41: aconst_null
    //   42: astore 9
    //   44: aconst_null
    //   45: astore 7
    //   47: aload 7
    //   49: astore 6
    //   51: aload 10
    //   53: invokevirtual 97	com/tencent/common/utils/QBFileLock:lock	()V
    //   56: aload 7
    //   58: astore 6
    //   60: aload 11
    //   62: invokevirtual 101	java/io/File:exists	()Z
    //   65: istore_3
    //   66: iload_3
    //   67: ifne +9 -> 76
    //   70: aload 10
    //   72: invokevirtual 104	com/tencent/common/utils/QBFileLock:releaseLock	()V
    //   75: return
    //   76: aload 7
    //   78: astore 6
    //   80: invokestatic 110	java/lang/System:currentTimeMillis	()J
    //   83: lstore 4
    //   85: aload 7
    //   87: astore 6
    //   89: new 112	java/io/DataInputStream
    //   92: dup
    //   93: aload 11
    //   95: invokestatic 116	com/tencent/common/utils/FileUtilsF:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   98: invokespecial 119	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   101: astore 7
    //   103: lload 4
    //   105: aload 7
    //   107: invokevirtual 122	java/io/DataInputStream:readLong	()J
    //   110: lsub
    //   111: invokestatic 128	java/lang/Math:abs	(J)J
    //   114: ldc2_w 129
    //   117: lcmp
    //   118: ifle +37 -> 155
    //   121: ldc -124
    //   123: ldc -122
    //   125: invokestatic 140	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   128: aload 11
    //   130: invokevirtual 143	java/io/File:delete	()Z
    //   133: pop
    //   134: aload 7
    //   136: invokevirtual 146	java/io/DataInputStream:close	()V
    //   139: goto +10 -> 149
    //   142: astore 6
    //   144: aload 6
    //   146: invokevirtual 149	java/io/IOException:printStackTrace	()V
    //   149: aload 10
    //   151: invokevirtual 104	com/tencent/common/utils/QBFileLock:releaseLock	()V
    //   154: return
    //   155: aload 7
    //   157: invokevirtual 153	java/io/DataInputStream:readInt	()I
    //   160: istore_2
    //   161: new 155	java/util/HashMap
    //   164: dup
    //   165: invokespecial 156	java/util/HashMap:<init>	()V
    //   168: astore 6
    //   170: iconst_0
    //   171: istore_1
    //   172: iload_1
    //   173: iload_2
    //   174: if_icmpge +25 -> 199
    //   177: aload 6
    //   179: aload 7
    //   181: invokevirtual 159	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   184: iconst_1
    //   185: invokestatic 165	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   188: invokevirtual 169	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   191: pop
    //   192: iload_1
    //   193: iconst_1
    //   194: iadd
    //   195: istore_1
    //   196: goto -24 -> 172
    //   199: new 47	java/lang/StringBuilder
    //   202: dup
    //   203: invokespecial 48	java/lang/StringBuilder:<init>	()V
    //   206: astore 8
    //   208: aload 8
    //   210: ldc -85
    //   212: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   215: pop
    //   216: aload 8
    //   218: iload_2
    //   219: invokevirtual 174	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   222: pop
    //   223: aload 8
    //   225: ldc -80
    //   227: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   230: pop
    //   231: aload 8
    //   233: invokestatic 110	java/lang/System:currentTimeMillis	()J
    //   236: lload 4
    //   238: lsub
    //   239: invokevirtual 179	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   242: pop
    //   243: ldc -124
    //   245: aload 8
    //   247: invokevirtual 60	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   250: invokestatic 140	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   253: aload_0
    //   254: getfield 19	com/tencent/common/http/QueenDirectCache:jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap	Ljava/util/concurrent/ConcurrentHashMap;
    //   257: ifnonnull +14 -> 271
    //   260: aload_0
    //   261: new 26	java/util/concurrent/ConcurrentHashMap
    //   264: dup
    //   265: invokespecial 27	java/util/concurrent/ConcurrentHashMap:<init>	()V
    //   268: putfield 19	com/tencent/common/http/QueenDirectCache:jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap	Ljava/util/concurrent/ConcurrentHashMap;
    //   271: aload_0
    //   272: getfield 19	com/tencent/common/http/QueenDirectCache:jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap	Ljava/util/concurrent/ConcurrentHashMap;
    //   275: aload 6
    //   277: invokevirtual 183	java/util/concurrent/ConcurrentHashMap:putAll	(Ljava/util/Map;)V
    //   280: aload 7
    //   282: invokevirtual 146	java/io/DataInputStream:close	()V
    //   285: goto +108 -> 393
    //   288: astore 6
    //   290: goto +98 -> 388
    //   293: astore 6
    //   295: goto +104 -> 399
    //   298: astore 8
    //   300: goto +31 -> 331
    //   303: astore 8
    //   305: goto +59 -> 364
    //   308: astore 8
    //   310: aload 6
    //   312: astore 7
    //   314: aload 8
    //   316: astore 6
    //   318: goto +81 -> 399
    //   321: astore 6
    //   323: aload 8
    //   325: astore 7
    //   327: aload 6
    //   329: astore 8
    //   331: aload 7
    //   333: astore 6
    //   335: aload 8
    //   337: invokevirtual 184	java/lang/OutOfMemoryError:printStackTrace	()V
    //   340: aload 7
    //   342: ifnull +51 -> 393
    //   345: aload 7
    //   347: invokevirtual 146	java/io/DataInputStream:close	()V
    //   350: goto +43 -> 393
    //   353: astore 6
    //   355: goto +33 -> 388
    //   358: astore 8
    //   360: aload 9
    //   362: astore 7
    //   364: aload 7
    //   366: astore 6
    //   368: aload 8
    //   370: invokevirtual 185	java/lang/Exception:printStackTrace	()V
    //   373: aload 7
    //   375: ifnull +18 -> 393
    //   378: aload 7
    //   380: invokevirtual 146	java/io/DataInputStream:close	()V
    //   383: goto +10 -> 393
    //   386: astore 6
    //   388: aload 6
    //   390: invokevirtual 149	java/io/IOException:printStackTrace	()V
    //   393: aload 10
    //   395: invokevirtual 104	com/tencent/common/utils/QBFileLock:releaseLock	()V
    //   398: return
    //   399: aload 7
    //   401: ifnull +18 -> 419
    //   404: aload 7
    //   406: invokevirtual 146	java/io/DataInputStream:close	()V
    //   409: goto +10 -> 419
    //   412: astore 7
    //   414: aload 7
    //   416: invokevirtual 149	java/io/IOException:printStackTrace	()V
    //   419: aload 10
    //   421: invokevirtual 104	com/tencent/common/utils/QBFileLock:releaseLock	()V
    //   424: aload 6
    //   426: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	427	0	this	QueenDirectCache
    //   171	25	1	i	int
    //   160	59	2	j	int
    //   65	2	3	bool	boolean
    //   83	154	4	l	long
    //   49	39	6	localObject1	Object
    //   142	3	6	localIOException1	java.io.IOException
    //   168	108	6	localHashMap	java.util.HashMap
    //   288	1	6	localIOException2	java.io.IOException
    //   293	18	6	localObject2	Object
    //   316	1	6	localObject3	Object
    //   321	7	6	localOutOfMemoryError1	OutOfMemoryError
    //   333	1	6	localObject4	Object
    //   353	1	6	localIOException3	java.io.IOException
    //   366	1	6	localObject5	Object
    //   386	39	6	localIOException4	java.io.IOException
    //   45	360	7	localObject6	Object
    //   412	3	7	localIOException5	java.io.IOException
    //   39	207	8	localStringBuilder	StringBuilder
    //   298	1	8	localOutOfMemoryError2	OutOfMemoryError
    //   303	1	8	localException1	Exception
    //   308	16	8	localObject7	Object
    //   329	7	8	localObject8	Object
    //   358	11	8	localException2	Exception
    //   42	319	9	localObject9	Object
    //   36	384	10	localQBFileLock	com.tencent.common.utils.QBFileLock
    //   17	112	11	localFile	File
    // Exception table:
    //   from	to	target	type
    //   134	139	142	java/io/IOException
    //   280	285	288	java/io/IOException
    //   103	134	293	finally
    //   155	170	293	finally
    //   177	192	293	finally
    //   199	271	293	finally
    //   271	280	293	finally
    //   103	134	298	java/lang/OutOfMemoryError
    //   155	170	298	java/lang/OutOfMemoryError
    //   177	192	298	java/lang/OutOfMemoryError
    //   199	271	298	java/lang/OutOfMemoryError
    //   271	280	298	java/lang/OutOfMemoryError
    //   103	134	303	java/lang/Exception
    //   155	170	303	java/lang/Exception
    //   177	192	303	java/lang/Exception
    //   199	271	303	java/lang/Exception
    //   271	280	303	java/lang/Exception
    //   51	56	308	finally
    //   60	66	308	finally
    //   80	85	308	finally
    //   89	103	308	finally
    //   335	340	308	finally
    //   368	373	308	finally
    //   51	56	321	java/lang/OutOfMemoryError
    //   60	66	321	java/lang/OutOfMemoryError
    //   80	85	321	java/lang/OutOfMemoryError
    //   89	103	321	java/lang/OutOfMemoryError
    //   345	350	353	java/io/IOException
    //   51	56	358	java/lang/Exception
    //   60	66	358	java/lang/Exception
    //   80	85	358	java/lang/Exception
    //   89	103	358	java/lang/Exception
    //   378	383	386	java/io/IOException
    //   404	409	412	java/io/IOException
  }
  
  public void put(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.contains(paramString)) {
        return;
      }
      if (this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.size() > 2000) {
        this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.clear();
      }
      this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.put(paramString, Integer.valueOf(1));
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("put: ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(", size: ");
      localStringBuilder.append(this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.size());
      FLogger.d("QueenDirectCache", localStringBuilder.toString());
      return;
    }
  }
  
  /* Error */
  public void saveCache()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 19	com/tencent/common/http/QueenDirectCache:jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap	Ljava/util/concurrent/ConcurrentHashMap;
    //   4: astore 4
    //   6: aload 4
    //   8: ifnull +12 -> 20
    //   11: aload 4
    //   13: invokevirtual 192	java/util/concurrent/ConcurrentHashMap:size	()I
    //   16: istore_1
    //   17: goto +5 -> 22
    //   20: iconst_0
    //   21: istore_1
    //   22: iload_1
    //   23: iconst_1
    //   24: if_icmpge +4 -> 28
    //   27: return
    //   28: invokestatic 207	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   31: invokestatic 211	com/tencent/common/utils/ThreadUtils:isMainProcess	(Landroid/content/Context;)Z
    //   34: ifne +4 -> 38
    //   37: return
    //   38: new 155	java/util/HashMap
    //   41: dup
    //   42: invokespecial 156	java/util/HashMap:<init>	()V
    //   45: astore 9
    //   47: aload 9
    //   49: aload_0
    //   50: getfield 19	com/tencent/common/http/QueenDirectCache:jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap	Ljava/util/concurrent/ConcurrentHashMap;
    //   53: invokevirtual 212	java/util/HashMap:putAll	(Ljava/util/Map;)V
    //   56: aload_0
    //   57: invokevirtual 85	com/tencent/common/http/QueenDirectCache:a	()Ljava/io/File;
    //   60: astore 10
    //   62: new 87	com/tencent/common/utils/QBFileLock
    //   65: dup
    //   66: aload 10
    //   68: invokevirtual 90	java/io/File:getParentFile	()Ljava/io/File;
    //   71: aload 10
    //   73: invokevirtual 93	java/io/File:getName	()Ljava/lang/String;
    //   76: invokespecial 94	com/tencent/common/utils/QBFileLock:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   79: astore 8
    //   81: aconst_null
    //   82: astore 6
    //   84: aconst_null
    //   85: astore 7
    //   87: aconst_null
    //   88: astore 5
    //   90: aload 5
    //   92: astore 4
    //   94: aload 8
    //   96: invokevirtual 97	com/tencent/common/utils/QBFileLock:lock	()V
    //   99: aload 5
    //   101: astore 4
    //   103: aload 10
    //   105: invokevirtual 101	java/io/File:exists	()Z
    //   108: ifne +13 -> 121
    //   111: aload 5
    //   113: astore 4
    //   115: aload 10
    //   117: invokevirtual 215	java/io/File:createNewFile	()Z
    //   120: pop
    //   121: aload 5
    //   123: astore 4
    //   125: invokestatic 110	java/lang/System:currentTimeMillis	()J
    //   128: lstore_2
    //   129: aload 5
    //   131: astore 4
    //   133: new 217	java/io/DataOutputStream
    //   136: dup
    //   137: aload 10
    //   139: invokestatic 221	com/tencent/common/utils/FileUtilsF:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
    //   142: invokespecial 224	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   145: astore 5
    //   147: aload 5
    //   149: lload_2
    //   150: invokevirtual 228	java/io/DataOutputStream:writeLong	(J)V
    //   153: aload 5
    //   155: iload_1
    //   156: invokevirtual 232	java/io/DataOutputStream:writeInt	(I)V
    //   159: aload 9
    //   161: invokevirtual 236	java/util/HashMap:entrySet	()Ljava/util/Set;
    //   164: invokeinterface 242 1 0
    //   169: astore 4
    //   171: aload 4
    //   173: invokeinterface 247 1 0
    //   178: ifeq +44 -> 222
    //   181: aload 4
    //   183: invokeinterface 251 1 0
    //   188: checkcast 253	java/util/Map$Entry
    //   191: invokeinterface 256 1 0
    //   196: checkcast 36	java/lang/String
    //   199: astore 6
    //   201: aload 6
    //   203: invokestatic 72	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   206: ifeq +6 -> 212
    //   209: goto -38 -> 171
    //   212: aload 5
    //   214: aload 6
    //   216: invokevirtual 259	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   219: goto -48 -> 171
    //   222: new 47	java/lang/StringBuilder
    //   225: dup
    //   226: invokespecial 48	java/lang/StringBuilder:<init>	()V
    //   229: astore 4
    //   231: aload 4
    //   233: ldc_w 261
    //   236: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   239: pop
    //   240: aload 4
    //   242: iload_1
    //   243: invokevirtual 174	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   246: pop
    //   247: aload 4
    //   249: ldc -80
    //   251: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   254: pop
    //   255: aload 4
    //   257: invokestatic 110	java/lang/System:currentTimeMillis	()J
    //   260: lload_2
    //   261: lsub
    //   262: invokevirtual 179	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   265: pop
    //   266: ldc -124
    //   268: aload 4
    //   270: invokevirtual 60	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   273: invokestatic 140	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   276: aload 5
    //   278: invokevirtual 262	java/io/DataOutputStream:close	()V
    //   281: goto +108 -> 389
    //   284: astore 4
    //   286: goto +98 -> 384
    //   289: astore 4
    //   291: goto +104 -> 395
    //   294: astore 6
    //   296: goto +31 -> 327
    //   299: astore 6
    //   301: goto +59 -> 360
    //   304: astore 6
    //   306: aload 4
    //   308: astore 5
    //   310: aload 6
    //   312: astore 4
    //   314: goto +81 -> 395
    //   317: astore 4
    //   319: aload 6
    //   321: astore 5
    //   323: aload 4
    //   325: astore 6
    //   327: aload 5
    //   329: astore 4
    //   331: aload 6
    //   333: invokevirtual 184	java/lang/OutOfMemoryError:printStackTrace	()V
    //   336: aload 5
    //   338: ifnull +51 -> 389
    //   341: aload 5
    //   343: invokevirtual 262	java/io/DataOutputStream:close	()V
    //   346: goto +43 -> 389
    //   349: astore 4
    //   351: goto +33 -> 384
    //   354: astore 6
    //   356: aload 7
    //   358: astore 5
    //   360: aload 5
    //   362: astore 4
    //   364: aload 6
    //   366: invokevirtual 185	java/lang/Exception:printStackTrace	()V
    //   369: aload 5
    //   371: ifnull +18 -> 389
    //   374: aload 5
    //   376: invokevirtual 262	java/io/DataOutputStream:close	()V
    //   379: goto +10 -> 389
    //   382: astore 4
    //   384: aload 4
    //   386: invokevirtual 149	java/io/IOException:printStackTrace	()V
    //   389: aload 8
    //   391: invokevirtual 104	com/tencent/common/utils/QBFileLock:releaseLock	()V
    //   394: return
    //   395: aload 5
    //   397: ifnull +18 -> 415
    //   400: aload 5
    //   402: invokevirtual 262	java/io/DataOutputStream:close	()V
    //   405: goto +10 -> 415
    //   408: astore 5
    //   410: aload 5
    //   412: invokevirtual 149	java/io/IOException:printStackTrace	()V
    //   415: aload 8
    //   417: invokevirtual 104	com/tencent/common/utils/QBFileLock:releaseLock	()V
    //   420: aload 4
    //   422: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	423	0	this	QueenDirectCache
    //   16	227	1	i	int
    //   128	133	2	l	long
    //   4	265	4	localObject1	Object
    //   284	1	4	localIOException1	java.io.IOException
    //   289	18	4	localObject2	Object
    //   312	1	4	localObject3	Object
    //   317	7	4	localOutOfMemoryError1	OutOfMemoryError
    //   329	1	4	localObject4	Object
    //   349	1	4	localIOException2	java.io.IOException
    //   362	1	4	localObject5	Object
    //   382	39	4	localIOException3	java.io.IOException
    //   88	313	5	localObject6	Object
    //   408	3	5	localIOException4	java.io.IOException
    //   82	133	6	str	String
    //   294	1	6	localOutOfMemoryError2	OutOfMemoryError
    //   299	1	6	localException1	Exception
    //   304	16	6	localObject7	Object
    //   325	7	6	localOutOfMemoryError3	OutOfMemoryError
    //   354	11	6	localException2	Exception
    //   85	272	7	localObject8	Object
    //   79	337	8	localQBFileLock	com.tencent.common.utils.QBFileLock
    //   45	115	9	localHashMap	java.util.HashMap
    //   60	78	10	localFile	File
    // Exception table:
    //   from	to	target	type
    //   276	281	284	java/io/IOException
    //   147	171	289	finally
    //   171	209	289	finally
    //   212	219	289	finally
    //   222	276	289	finally
    //   147	171	294	java/lang/OutOfMemoryError
    //   171	209	294	java/lang/OutOfMemoryError
    //   212	219	294	java/lang/OutOfMemoryError
    //   222	276	294	java/lang/OutOfMemoryError
    //   147	171	299	java/lang/Exception
    //   171	209	299	java/lang/Exception
    //   212	219	299	java/lang/Exception
    //   222	276	299	java/lang/Exception
    //   94	99	304	finally
    //   103	111	304	finally
    //   115	121	304	finally
    //   125	129	304	finally
    //   133	147	304	finally
    //   331	336	304	finally
    //   364	369	304	finally
    //   94	99	317	java/lang/OutOfMemoryError
    //   103	111	317	java/lang/OutOfMemoryError
    //   115	121	317	java/lang/OutOfMemoryError
    //   125	129	317	java/lang/OutOfMemoryError
    //   133	147	317	java/lang/OutOfMemoryError
    //   341	346	349	java/io/IOException
    //   94	99	354	java/lang/Exception
    //   103	111	354	java/lang/Exception
    //   115	121	354	java/lang/Exception
    //   125	129	354	java/lang/Exception
    //   133	147	354	java/lang/Exception
    //   374	379	382	java/io/IOException
    //   400	405	408	java/io/IOException
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\QueenDirectCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */