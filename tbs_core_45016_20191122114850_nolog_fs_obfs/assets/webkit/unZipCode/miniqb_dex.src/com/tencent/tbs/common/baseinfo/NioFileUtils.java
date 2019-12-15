package com.tencent.tbs.common.baseinfo;

public class NioFileUtils
{
  private static final String TAG = "NioFileUtils";
  
  public static NioFileUtils getInstance()
  {
    return InstanceHolder.INSTANCE;
  }
  
  /* Error */
  public byte[] readWithMappedByteBuffer(java.io.File paramFile, int paramInt)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +453 -> 454
    //   4: aload_1
    //   5: invokevirtual 38	java/io/File:exists	()Z
    //   8: ifeq +446 -> 454
    //   11: new 40	java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial 41	java/lang/StringBuilder:<init>	()V
    //   18: astore 7
    //   20: aload 7
    //   22: ldc 43
    //   24: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: pop
    //   28: aload 7
    //   30: invokestatic 53	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   33: invokevirtual 57	java/lang/Thread:getName	()Ljava/lang/String;
    //   36: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   39: pop
    //   40: aload 7
    //   42: ldc 59
    //   44: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: pop
    //   48: aload 7
    //   50: aload_0
    //   51: invokevirtual 63	java/lang/Object:hashCode	()I
    //   54: invokevirtual 66	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   57: pop
    //   58: ldc 13
    //   60: aload 7
    //   62: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   65: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   68: invokestatic 81	java/lang/System:currentTimeMillis	()J
    //   71: lstore_3
    //   72: new 83	java/io/RandomAccessFile
    //   75: dup
    //   76: aload_1
    //   77: ldc 85
    //   79: invokespecial 88	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   82: astore 7
    //   84: aload 7
    //   86: invokevirtual 92	java/io/RandomAccessFile:getChannel	()Ljava/nio/channels/FileChannel;
    //   89: astore 9
    //   91: aload 9
    //   93: invokevirtual 98	java/nio/channels/FileChannel:lock	()Ljava/nio/channels/FileLock;
    //   96: astore 8
    //   98: aload 7
    //   100: invokevirtual 101	java/io/RandomAccessFile:length	()J
    //   103: lstore 5
    //   105: lload 5
    //   107: l2i
    //   108: newarray <illegal type>
    //   110: astore 10
    //   112: aload 9
    //   114: getstatic 107	java/nio/channels/FileChannel$MapMode:READ_ONLY	Ljava/nio/channels/FileChannel$MapMode;
    //   117: lconst_0
    //   118: lload 5
    //   120: invokevirtual 111	java/nio/channels/FileChannel:map	(Ljava/nio/channels/FileChannel$MapMode;JJ)Ljava/nio/MappedByteBuffer;
    //   123: aload 10
    //   125: invokevirtual 117	java/nio/MappedByteBuffer:get	([B)Ljava/nio/ByteBuffer;
    //   128: pop
    //   129: new 40	java/lang/StringBuilder
    //   132: dup
    //   133: invokespecial 41	java/lang/StringBuilder:<init>	()V
    //   136: astore 11
    //   138: aload 11
    //   140: ldc 119
    //   142: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: aload 11
    //   148: invokestatic 81	java/lang/System:currentTimeMillis	()J
    //   151: lload_3
    //   152: lsub
    //   153: invokevirtual 122	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   156: pop
    //   157: aload 11
    //   159: ldc 124
    //   161: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   164: pop
    //   165: aload 11
    //   167: lload 5
    //   169: invokevirtual 122	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   172: pop
    //   173: ldc 13
    //   175: aload 11
    //   177: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   180: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   183: aload 8
    //   185: ifnull +21 -> 206
    //   188: aload 8
    //   190: invokevirtual 129	java/nio/channels/FileLock:release	()V
    //   193: goto +13 -> 206
    //   196: astore_1
    //   197: ldc 13
    //   199: aload_1
    //   200: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   203: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   206: aload 7
    //   208: invokevirtual 138	java/io/RandomAccessFile:close	()V
    //   211: goto +13 -> 224
    //   214: astore_1
    //   215: ldc 13
    //   217: aload_1
    //   218: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   221: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   224: aload 9
    //   226: ifnull +16 -> 242
    //   229: aload 9
    //   231: invokevirtual 139	java/nio/channels/FileChannel:close	()V
    //   234: aload 10
    //   236: areturn
    //   237: astore_1
    //   238: aload_1
    //   239: invokevirtual 142	java/io/IOException:printStackTrace	()V
    //   242: aload 10
    //   244: areturn
    //   245: astore_1
    //   246: goto +136 -> 382
    //   249: astore 10
    //   251: goto +48 -> 299
    //   254: astore_1
    //   255: goto +27 -> 282
    //   258: astore 10
    //   260: aconst_null
    //   261: astore 8
    //   263: goto +36 -> 299
    //   266: astore_1
    //   267: goto +12 -> 279
    //   270: astore 10
    //   272: goto +21 -> 293
    //   275: astore_1
    //   276: aconst_null
    //   277: astore 7
    //   279: aconst_null
    //   280: astore 9
    //   282: aconst_null
    //   283: astore 8
    //   285: goto +97 -> 382
    //   288: astore 10
    //   290: aconst_null
    //   291: astore 7
    //   293: aconst_null
    //   294: astore 8
    //   296: aconst_null
    //   297: astore 9
    //   299: ldc 13
    //   301: aload 10
    //   303: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   306: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   309: aload_1
    //   310: invokevirtual 145	java/io/File:delete	()Z
    //   313: pop
    //   314: aload 8
    //   316: ifnull +21 -> 337
    //   319: aload 8
    //   321: invokevirtual 129	java/nio/channels/FileLock:release	()V
    //   324: goto +13 -> 337
    //   327: astore_1
    //   328: ldc 13
    //   330: aload_1
    //   331: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   334: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   337: aload 7
    //   339: ifnull +21 -> 360
    //   342: aload 7
    //   344: invokevirtual 138	java/io/RandomAccessFile:close	()V
    //   347: goto +13 -> 360
    //   350: astore_1
    //   351: ldc 13
    //   353: aload_1
    //   354: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   357: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   360: aload 9
    //   362: ifnull +17 -> 379
    //   365: aload 9
    //   367: invokevirtual 139	java/nio/channels/FileChannel:close	()V
    //   370: aconst_null
    //   371: areturn
    //   372: astore_1
    //   373: aload_1
    //   374: invokevirtual 142	java/io/IOException:printStackTrace	()V
    //   377: aconst_null
    //   378: areturn
    //   379: aconst_null
    //   380: areturn
    //   381: astore_1
    //   382: aload 8
    //   384: ifnull +23 -> 407
    //   387: aload 8
    //   389: invokevirtual 129	java/nio/channels/FileLock:release	()V
    //   392: goto +15 -> 407
    //   395: astore 8
    //   397: ldc 13
    //   399: aload 8
    //   401: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   404: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   407: aload 7
    //   409: ifnull +23 -> 432
    //   412: aload 7
    //   414: invokevirtual 138	java/io/RandomAccessFile:close	()V
    //   417: goto +15 -> 432
    //   420: astore 7
    //   422: ldc 13
    //   424: aload 7
    //   426: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   429: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   432: aload 9
    //   434: ifnull +18 -> 452
    //   437: aload 9
    //   439: invokevirtual 139	java/nio/channels/FileChannel:close	()V
    //   442: goto +10 -> 452
    //   445: astore 7
    //   447: aload 7
    //   449: invokevirtual 142	java/io/IOException:printStackTrace	()V
    //   452: aload_1
    //   453: athrow
    //   454: aconst_null
    //   455: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	456	0	this	NioFileUtils
    //   0	456	1	paramFile	java.io.File
    //   0	456	2	paramInt	int
    //   71	81	3	l1	long
    //   103	65	5	l2	long
    //   18	395	7	localObject	Object
    //   420	5	7	localIOException1	java.io.IOException
    //   445	3	7	localIOException2	java.io.IOException
    //   96	292	8	localFileLock	java.nio.channels.FileLock
    //   395	5	8	localIOException3	java.io.IOException
    //   89	349	9	localFileChannel	java.nio.channels.FileChannel
    //   110	133	10	arrayOfByte	byte[]
    //   249	1	10	localException1	Exception
    //   258	1	10	localException2	Exception
    //   270	1	10	localException3	Exception
    //   288	14	10	localException4	Exception
    //   136	40	11	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   188	193	196	java/io/IOException
    //   206	211	214	java/io/IOException
    //   229	234	237	java/io/IOException
    //   98	183	245	finally
    //   98	183	249	java/lang/Exception
    //   91	98	254	finally
    //   91	98	258	java/lang/Exception
    //   84	91	266	finally
    //   84	91	270	java/lang/Exception
    //   72	84	275	finally
    //   72	84	288	java/lang/Exception
    //   319	324	327	java/io/IOException
    //   342	347	350	java/io/IOException
    //   365	370	372	java/io/IOException
    //   299	314	381	finally
    //   387	392	395	java/io/IOException
    //   412	417	420	java/io/IOException
    //   437	442	445	java/io/IOException
  }
  
  /* Error */
  public void writeWithMappedByteBuffer(java.io.File paramFile, byte[] paramArrayOfByte, int paramInt)
  {
    // Byte code:
    //   0: new 40	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 41	java/lang/StringBuilder:<init>	()V
    //   7: astore 6
    //   9: aload 6
    //   11: ldc -107
    //   13: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: aload 6
    //   19: invokestatic 53	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   22: invokevirtual 57	java/lang/Thread:getName	()Ljava/lang/String;
    //   25: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: pop
    //   29: aload 6
    //   31: ldc 59
    //   33: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: pop
    //   37: aload 6
    //   39: aload_0
    //   40: invokevirtual 63	java/lang/Object:hashCode	()I
    //   43: invokevirtual 66	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   46: pop
    //   47: ldc 13
    //   49: aload 6
    //   51: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   54: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   57: invokestatic 81	java/lang/System:currentTimeMillis	()J
    //   60: lstore 4
    //   62: aload_1
    //   63: ifnull +548 -> 611
    //   66: aload_2
    //   67: ifnonnull +4 -> 71
    //   70: return
    //   71: aconst_null
    //   72: astore 9
    //   74: aconst_null
    //   75: astore 11
    //   77: aconst_null
    //   78: astore 6
    //   80: aconst_null
    //   81: astore 7
    //   83: aload_1
    //   84: invokevirtual 38	java/io/File:exists	()Z
    //   87: ifne +8 -> 95
    //   90: aload_1
    //   91: invokevirtual 152	java/io/File:createNewFile	()Z
    //   94: pop
    //   95: new 83	java/io/RandomAccessFile
    //   98: dup
    //   99: aload_1
    //   100: ldc 85
    //   102: invokespecial 88	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   105: astore_1
    //   106: aload_1
    //   107: invokevirtual 92	java/io/RandomAccessFile:getChannel	()Ljava/nio/channels/FileChannel;
    //   110: astore 10
    //   112: aload 7
    //   114: astore 9
    //   116: aload_1
    //   117: astore 7
    //   119: aload 10
    //   121: astore 8
    //   123: aload 11
    //   125: astore 6
    //   127: aload 10
    //   129: invokevirtual 98	java/nio/channels/FileChannel:lock	()Ljava/nio/channels/FileLock;
    //   132: astore 11
    //   134: aload 11
    //   136: astore 9
    //   138: aload_1
    //   139: astore 7
    //   141: aload 10
    //   143: astore 8
    //   145: aload 11
    //   147: astore 6
    //   149: aload_2
    //   150: arraylength
    //   151: i2l
    //   152: aload_1
    //   153: invokevirtual 101	java/io/RandomAccessFile:length	()J
    //   156: lcmp
    //   157: ifeq +25 -> 182
    //   160: aload 11
    //   162: astore 9
    //   164: aload_1
    //   165: astore 7
    //   167: aload 10
    //   169: astore 8
    //   171: aload 11
    //   173: astore 6
    //   175: aload_1
    //   176: aload_2
    //   177: arraylength
    //   178: i2l
    //   179: invokevirtual 156	java/io/RandomAccessFile:setLength	(J)V
    //   182: aload 11
    //   184: astore 9
    //   186: aload_1
    //   187: astore 7
    //   189: aload 10
    //   191: astore 8
    //   193: aload 11
    //   195: astore 6
    //   197: aload 10
    //   199: getstatic 159	java/nio/channels/FileChannel$MapMode:READ_WRITE	Ljava/nio/channels/FileChannel$MapMode;
    //   202: lconst_0
    //   203: aload_2
    //   204: arraylength
    //   205: i2l
    //   206: invokevirtual 111	java/nio/channels/FileChannel:map	(Ljava/nio/channels/FileChannel$MapMode;JJ)Ljava/nio/MappedByteBuffer;
    //   209: aload_2
    //   210: invokevirtual 162	java/nio/MappedByteBuffer:put	([B)Ljava/nio/ByteBuffer;
    //   213: pop
    //   214: aload 11
    //   216: astore 9
    //   218: aload_1
    //   219: astore 7
    //   221: aload 10
    //   223: astore 8
    //   225: aload 11
    //   227: astore 6
    //   229: new 40	java/lang/StringBuilder
    //   232: dup
    //   233: invokespecial 41	java/lang/StringBuilder:<init>	()V
    //   236: astore 12
    //   238: aload 11
    //   240: astore 9
    //   242: aload_1
    //   243: astore 7
    //   245: aload 10
    //   247: astore 8
    //   249: aload 11
    //   251: astore 6
    //   253: aload 12
    //   255: ldc -92
    //   257: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   260: pop
    //   261: aload 11
    //   263: astore 9
    //   265: aload_1
    //   266: astore 7
    //   268: aload 10
    //   270: astore 8
    //   272: aload 11
    //   274: astore 6
    //   276: aload 12
    //   278: invokestatic 81	java/lang/System:currentTimeMillis	()J
    //   281: lload 4
    //   283: lsub
    //   284: invokevirtual 122	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   287: pop
    //   288: aload 11
    //   290: astore 9
    //   292: aload_1
    //   293: astore 7
    //   295: aload 10
    //   297: astore 8
    //   299: aload 11
    //   301: astore 6
    //   303: aload 12
    //   305: ldc -90
    //   307: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   310: pop
    //   311: aload 11
    //   313: astore 9
    //   315: aload_1
    //   316: astore 7
    //   318: aload 10
    //   320: astore 8
    //   322: aload 11
    //   324: astore 6
    //   326: aload 12
    //   328: aload_2
    //   329: arraylength
    //   330: invokevirtual 66	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   333: pop
    //   334: aload 11
    //   336: astore 9
    //   338: aload_1
    //   339: astore 7
    //   341: aload 10
    //   343: astore 8
    //   345: aload 11
    //   347: astore 6
    //   349: ldc 13
    //   351: aload 12
    //   353: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   356: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   359: aload 11
    //   361: ifnull +21 -> 382
    //   364: aload 11
    //   366: invokevirtual 129	java/nio/channels/FileLock:release	()V
    //   369: goto +13 -> 382
    //   372: astore_2
    //   373: ldc 13
    //   375: aload_2
    //   376: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   379: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   382: aload_1
    //   383: invokevirtual 138	java/io/RandomAccessFile:close	()V
    //   386: goto +13 -> 399
    //   389: astore_1
    //   390: ldc 13
    //   392: aload_1
    //   393: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   396: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   399: aload 10
    //   401: ifnull +139 -> 540
    //   404: aload 10
    //   406: invokevirtual 139	java/nio/channels/FileChannel:close	()V
    //   409: return
    //   410: astore 6
    //   412: aload_1
    //   413: astore_2
    //   414: aload 10
    //   416: astore_1
    //   417: aload 6
    //   419: astore 10
    //   421: goto +39 -> 460
    //   424: astore_2
    //   425: aconst_null
    //   426: astore 8
    //   428: goto +117 -> 545
    //   431: astore 10
    //   433: aconst_null
    //   434: astore 6
    //   436: aload_1
    //   437: astore_2
    //   438: aload 6
    //   440: astore_1
    //   441: goto +19 -> 460
    //   444: astore_2
    //   445: aconst_null
    //   446: astore 8
    //   448: aload 8
    //   450: astore_1
    //   451: goto +94 -> 545
    //   454: astore 10
    //   456: aconst_null
    //   457: astore_1
    //   458: aload_1
    //   459: astore_2
    //   460: aload_2
    //   461: astore 7
    //   463: aload_1
    //   464: astore 8
    //   466: aload 9
    //   468: astore 6
    //   470: ldc 13
    //   472: aload 10
    //   474: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   477: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   480: aload 9
    //   482: ifnull +23 -> 505
    //   485: aload 9
    //   487: invokevirtual 129	java/nio/channels/FileLock:release	()V
    //   490: goto +15 -> 505
    //   493: astore 6
    //   495: ldc 13
    //   497: aload 6
    //   499: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   502: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   505: aload_2
    //   506: ifnull +20 -> 526
    //   509: aload_2
    //   510: invokevirtual 138	java/io/RandomAccessFile:close	()V
    //   513: goto +13 -> 526
    //   516: astore_2
    //   517: ldc 13
    //   519: aload_2
    //   520: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   523: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   526: aload_1
    //   527: ifnull +13 -> 540
    //   530: aload_1
    //   531: invokevirtual 139	java/nio/channels/FileChannel:close	()V
    //   534: return
    //   535: astore_1
    //   536: aload_1
    //   537: invokevirtual 142	java/io/IOException:printStackTrace	()V
    //   540: return
    //   541: astore_2
    //   542: aload 7
    //   544: astore_1
    //   545: aload 6
    //   547: ifnull +23 -> 570
    //   550: aload 6
    //   552: invokevirtual 129	java/nio/channels/FileLock:release	()V
    //   555: goto +15 -> 570
    //   558: astore 6
    //   560: ldc 13
    //   562: aload 6
    //   564: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   567: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   570: aload_1
    //   571: ifnull +20 -> 591
    //   574: aload_1
    //   575: invokevirtual 138	java/io/RandomAccessFile:close	()V
    //   578: goto +13 -> 591
    //   581: astore_1
    //   582: ldc 13
    //   584: aload_1
    //   585: invokestatic 135	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   588: invokestatic 75	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   591: aload 8
    //   593: ifnull +16 -> 609
    //   596: aload 8
    //   598: invokevirtual 139	java/nio/channels/FileChannel:close	()V
    //   601: goto +8 -> 609
    //   604: astore_1
    //   605: aload_1
    //   606: invokevirtual 142	java/io/IOException:printStackTrace	()V
    //   609: aload_2
    //   610: athrow
    //   611: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	612	0	this	NioFileUtils
    //   0	612	1	paramFile	java.io.File
    //   0	612	2	paramArrayOfByte	byte[]
    //   0	612	3	paramInt	int
    //   60	222	4	l	long
    //   7	341	6	localObject1	Object
    //   410	8	6	localException1	Exception
    //   434	35	6	localObject2	Object
    //   493	58	6	localIOException1	java.io.IOException
    //   558	5	6	localIOException2	java.io.IOException
    //   81	462	7	localObject3	Object
    //   121	476	8	localObject4	Object
    //   72	414	9	localObject5	Object
    //   110	310	10	localObject6	Object
    //   431	1	10	localException2	Exception
    //   454	19	10	localException3	Exception
    //   75	290	11	localFileLock	java.nio.channels.FileLock
    //   236	116	12	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   364	369	372	java/io/IOException
    //   382	386	389	java/io/IOException
    //   127	134	410	java/lang/Exception
    //   149	160	410	java/lang/Exception
    //   175	182	410	java/lang/Exception
    //   197	214	410	java/lang/Exception
    //   229	238	410	java/lang/Exception
    //   253	261	410	java/lang/Exception
    //   276	288	410	java/lang/Exception
    //   303	311	410	java/lang/Exception
    //   326	334	410	java/lang/Exception
    //   349	359	410	java/lang/Exception
    //   106	112	424	finally
    //   106	112	431	java/lang/Exception
    //   83	95	444	finally
    //   95	106	444	finally
    //   83	95	454	java/lang/Exception
    //   95	106	454	java/lang/Exception
    //   485	490	493	java/io/IOException
    //   509	513	516	java/io/IOException
    //   404	409	535	java/io/IOException
    //   530	534	535	java/io/IOException
    //   127	134	541	finally
    //   149	160	541	finally
    //   175	182	541	finally
    //   197	214	541	finally
    //   229	238	541	finally
    //   253	261	541	finally
    //   276	288	541	finally
    //   303	311	541	finally
    //   326	334	541	finally
    //   349	359	541	finally
    //   470	480	541	finally
    //   550	555	558	java/io/IOException
    //   574	578	581	java/io/IOException
    //   596	601	604	java/io/IOException
  }
  
  private static class InstanceHolder
  {
    public static final NioFileUtils INSTANCE = new NioFileUtils(null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\NioFileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */