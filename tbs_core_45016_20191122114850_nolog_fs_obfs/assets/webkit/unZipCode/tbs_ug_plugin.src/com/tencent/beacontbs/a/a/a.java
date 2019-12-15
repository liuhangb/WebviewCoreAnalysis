package com.tencent.beacontbs.a.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class a
{
  private int a;
  public long a;
  public String a;
  private int b;
  public long b;
  private long c = -1L;
  
  public a()
  {
    this.jdField_a_of_type_Long = -1L;
    this.jdField_a_of_type_Int = -1;
    this.jdField_b_of_type_Int = -1;
    this.jdField_a_of_type_JavaLangString = null;
    this.jdField_b_of_type_Long = 0L;
  }
  
  public a(long paramLong, String paramString, int paramInt)
  {
    this.jdField_a_of_type_Long = -1L;
    this.jdField_a_of_type_Int = -1;
    this.jdField_b_of_type_Int = -1;
    this.jdField_a_of_type_JavaLangString = null;
    this.jdField_b_of_type_Long = 0L;
    this.jdField_a_of_type_Int = 1;
    this.jdField_b_of_type_Int = 3;
    this.c = paramLong;
    this.jdField_a_of_type_JavaLangString = paramString;
    if (paramString != null) {
      this.jdField_b_of_type_Long = paramInt;
    }
  }
  
  /* Error */
  public static int a(Context paramContext, int[] paramArrayOfInt)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: ldc 35
    //   5: iconst_0
    //   6: anewarray 4	java/lang/Object
    //   9: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   12: aload_0
    //   13: ifnonnull +17 -> 30
    //   16: ldc 42
    //   18: iconst_0
    //   19: anewarray 4	java/lang/Object
    //   22: invokestatic 44	com/tencent/beacontbs/c/a:a	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   25: ldc 2
    //   27: monitorexit
    //   28: iconst_m1
    //   29: ireturn
    //   30: ldc 46
    //   32: astore 4
    //   34: aload 4
    //   36: astore_3
    //   37: aload_1
    //   38: ifnull +114 -> 152
    //   41: aload 4
    //   43: astore_3
    //   44: aload_1
    //   45: arraylength
    //   46: ifle +106 -> 152
    //   49: ldc 48
    //   51: astore_3
    //   52: iconst_0
    //   53: istore_2
    //   54: iload_2
    //   55: aload_1
    //   56: arraylength
    //   57: if_icmpge +49 -> 106
    //   60: new 50	java/lang/StringBuilder
    //   63: dup
    //   64: invokespecial 51	java/lang/StringBuilder:<init>	()V
    //   67: astore 4
    //   69: aload 4
    //   71: aload_3
    //   72: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: pop
    //   76: aload 4
    //   78: ldc 57
    //   80: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: pop
    //   84: aload 4
    //   86: aload_1
    //   87: iload_2
    //   88: iaload
    //   89: invokevirtual 60	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   92: pop
    //   93: aload 4
    //   95: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   98: astore_3
    //   99: iload_2
    //   100: iconst_1
    //   101: iadd
    //   102: istore_2
    //   103: goto -49 -> 54
    //   106: aload_3
    //   107: iconst_4
    //   108: invokevirtual 70	java/lang/String:substring	(I)Ljava/lang/String;
    //   111: astore_1
    //   112: new 50	java/lang/StringBuilder
    //   115: dup
    //   116: invokespecial 51	java/lang/StringBuilder:<init>	()V
    //   119: astore_3
    //   120: aload_3
    //   121: ldc 46
    //   123: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: pop
    //   127: aload_3
    //   128: ldc 72
    //   130: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: pop
    //   134: aload_3
    //   135: aload_1
    //   136: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   139: pop
    //   140: aload_3
    //   141: ldc 74
    //   143: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: pop
    //   147: aload_3
    //   148: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   151: astore_3
    //   152: new 50	java/lang/StringBuilder
    //   155: dup
    //   156: ldc 76
    //   158: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   161: astore_1
    //   162: aload_1
    //   163: aload_3
    //   164: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: pop
    //   168: aload_1
    //   169: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   172: iconst_0
    //   173: anewarray 4	java/lang/Object
    //   176: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   179: aload_0
    //   180: invokestatic 84	com/tencent/beacontbs/a/a/c:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/a/c;
    //   183: invokevirtual 88	com/tencent/beacontbs/a/a/c:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   186: ldc 90
    //   188: aload_3
    //   189: aconst_null
    //   190: invokevirtual 96	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   193: istore_2
    //   194: new 50	java/lang/StringBuilder
    //   197: dup
    //   198: ldc 98
    //   200: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   203: astore_0
    //   204: aload_0
    //   205: iload_2
    //   206: invokevirtual 60	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   209: pop
    //   210: aload_0
    //   211: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   214: iconst_0
    //   215: anewarray 4	java/lang/Object
    //   218: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   221: ldc 100
    //   223: iconst_0
    //   224: anewarray 4	java/lang/Object
    //   227: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   230: goto +30 -> 260
    //   233: astore_0
    //   234: goto +31 -> 265
    //   237: astore_0
    //   238: aload_0
    //   239: invokevirtual 103	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   242: iconst_0
    //   243: anewarray 4	java/lang/Object
    //   246: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   249: ldc 100
    //   251: iconst_0
    //   252: anewarray 4	java/lang/Object
    //   255: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   258: iconst_m1
    //   259: istore_2
    //   260: ldc 2
    //   262: monitorexit
    //   263: iload_2
    //   264: ireturn
    //   265: ldc 100
    //   267: iconst_0
    //   268: anewarray 4	java/lang/Object
    //   271: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   274: aload_0
    //   275: athrow
    //   276: astore_0
    //   277: ldc 2
    //   279: monitorexit
    //   280: aload_0
    //   281: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	282	0	paramContext	Context
    //   0	282	1	paramArrayOfInt	int[]
    //   53	211	2	i	int
    //   36	153	3	localObject1	Object
    //   32	62	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   179	221	233	finally
    //   238	249	233	finally
    //   179	221	237	java/lang/Throwable
    //   3	12	276	finally
    //   16	25	276	finally
    //   44	49	276	finally
    //   54	99	276	finally
    //   106	152	276	finally
    //   152	179	276	finally
    //   221	230	276	finally
    //   249	258	276	finally
    //   265	276	276	finally
  }
  
  /* Error */
  public static int a(Context paramContext, Long[] paramArrayOfLong)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: ldc 106
    //   5: iconst_0
    //   6: anewarray 4	java/lang/Object
    //   9: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   12: aload_0
    //   13: ifnonnull +17 -> 30
    //   16: ldc 108
    //   18: iconst_0
    //   19: anewarray 4	java/lang/Object
    //   22: invokestatic 111	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   25: ldc 2
    //   27: monitorexit
    //   28: iconst_m1
    //   29: ireturn
    //   30: aload_1
    //   31: ifnull +355 -> 386
    //   34: aload_1
    //   35: arraylength
    //   36: istore_2
    //   37: iload_2
    //   38: ifgt +6 -> 44
    //   41: goto +345 -> 386
    //   44: aload_0
    //   45: invokestatic 84	com/tencent/beacontbs/a/a/c:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/a/c;
    //   48: invokevirtual 88	com/tencent/beacontbs/a/a/c:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   51: astore_0
    //   52: new 113	java/lang/StringBuffer
    //   55: dup
    //   56: invokespecial 114	java/lang/StringBuffer:<init>	()V
    //   59: astore 7
    //   61: iconst_0
    //   62: istore 4
    //   64: iconst_0
    //   65: istore_2
    //   66: iload_2
    //   67: istore_3
    //   68: iload 4
    //   70: aload_1
    //   71: arraylength
    //   72: if_icmpge +171 -> 243
    //   75: iload_2
    //   76: istore_3
    //   77: aload_1
    //   78: iload 4
    //   80: aaload
    //   81: invokevirtual 120	java/lang/Long:longValue	()J
    //   84: lstore 5
    //   86: iload_2
    //   87: istore_3
    //   88: new 50	java/lang/StringBuilder
    //   91: dup
    //   92: ldc 122
    //   94: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   97: astore 8
    //   99: iload_2
    //   100: istore_3
    //   101: aload 8
    //   103: lload 5
    //   105: invokevirtual 125	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   108: pop
    //   109: iload_2
    //   110: istore_3
    //   111: aload 7
    //   113: aload 8
    //   115: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   118: invokevirtual 128	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   121: pop
    //   122: iload_2
    //   123: istore_3
    //   124: iload 4
    //   126: ifle +271 -> 397
    //   129: iload_2
    //   130: istore_3
    //   131: iload 4
    //   133: bipush 25
    //   135: irem
    //   136: ifne +261 -> 397
    //   139: iload_2
    //   140: istore_3
    //   141: new 50	java/lang/StringBuilder
    //   144: dup
    //   145: ldc -126
    //   147: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   150: astore 8
    //   152: iload_2
    //   153: istore_3
    //   154: aload 8
    //   156: iload 4
    //   158: invokevirtual 60	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   161: pop
    //   162: iload_2
    //   163: istore_3
    //   164: aload 8
    //   166: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   169: iconst_0
    //   170: anewarray 4	java/lang/Object
    //   173: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   176: iload_2
    //   177: istore_3
    //   178: iload_2
    //   179: aload_0
    //   180: ldc 90
    //   182: aload 7
    //   184: iconst_4
    //   185: invokevirtual 131	java/lang/StringBuffer:substring	(I)Ljava/lang/String;
    //   188: aconst_null
    //   189: invokevirtual 96	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   192: iadd
    //   193: istore_2
    //   194: iload_2
    //   195: istore_3
    //   196: aload 7
    //   198: iconst_0
    //   199: invokevirtual 135	java/lang/StringBuffer:setLength	(I)V
    //   202: iload_2
    //   203: istore_3
    //   204: new 50	java/lang/StringBuilder
    //   207: dup
    //   208: ldc -119
    //   210: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   213: astore 8
    //   215: iload_2
    //   216: istore_3
    //   217: aload 8
    //   219: iload_2
    //   220: invokevirtual 60	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   223: pop
    //   224: iload_2
    //   225: istore_3
    //   226: aload 8
    //   228: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   231: iconst_0
    //   232: anewarray 4	java/lang/Object
    //   235: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   238: iload_2
    //   239: istore_3
    //   240: goto +157 -> 397
    //   243: iload_2
    //   244: istore_3
    //   245: aload 7
    //   247: invokevirtual 141	java/lang/StringBuffer:length	()I
    //   250: ifle +158 -> 408
    //   253: iload_2
    //   254: istore_3
    //   255: aload_0
    //   256: ldc 90
    //   258: aload 7
    //   260: iconst_4
    //   261: invokevirtual 131	java/lang/StringBuffer:substring	(I)Ljava/lang/String;
    //   264: aconst_null
    //   265: invokevirtual 96	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   268: istore 4
    //   270: iload 4
    //   272: iload_2
    //   273: iadd
    //   274: istore_2
    //   275: iload_2
    //   276: istore_3
    //   277: aload 7
    //   279: iconst_0
    //   280: invokevirtual 135	java/lang/StringBuffer:setLength	(I)V
    //   283: goto +3 -> 286
    //   286: iload_2
    //   287: istore_3
    //   288: new 50	java/lang/StringBuilder
    //   291: dup
    //   292: ldc -113
    //   294: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   297: astore_0
    //   298: iload_2
    //   299: istore_3
    //   300: aload_0
    //   301: iload_2
    //   302: invokevirtual 60	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   305: pop
    //   306: iload_2
    //   307: istore_3
    //   308: aload_0
    //   309: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   312: iconst_0
    //   313: anewarray 4	java/lang/Object
    //   316: invokestatic 44	com/tencent/beacontbs/c/a:a	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   319: ldc -111
    //   321: iconst_0
    //   322: anewarray 4	java/lang/Object
    //   325: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   328: goto +42 -> 370
    //   331: astore_0
    //   332: iload_3
    //   333: istore_2
    //   334: goto +16 -> 350
    //   337: astore_0
    //   338: iload_3
    //   339: istore_2
    //   340: goto +10 -> 350
    //   343: astore_0
    //   344: goto +31 -> 375
    //   347: astore_0
    //   348: iconst_0
    //   349: istore_2
    //   350: aload_0
    //   351: invokevirtual 103	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   354: iconst_0
    //   355: anewarray 4	java/lang/Object
    //   358: invokestatic 111	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   361: ldc -111
    //   363: iconst_0
    //   364: anewarray 4	java/lang/Object
    //   367: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   370: ldc 2
    //   372: monitorexit
    //   373: iload_2
    //   374: ireturn
    //   375: ldc -111
    //   377: iconst_0
    //   378: anewarray 4	java/lang/Object
    //   381: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   384: aload_0
    //   385: athrow
    //   386: ldc 2
    //   388: monitorexit
    //   389: iconst_0
    //   390: ireturn
    //   391: astore_0
    //   392: ldc 2
    //   394: monitorexit
    //   395: aload_0
    //   396: athrow
    //   397: iload 4
    //   399: iconst_1
    //   400: iadd
    //   401: istore 4
    //   403: iload_3
    //   404: istore_2
    //   405: goto -339 -> 66
    //   408: goto -122 -> 286
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	411	0	paramContext	Context
    //   0	411	1	paramArrayOfLong	Long[]
    //   36	369	2	i	int
    //   67	337	3	j	int
    //   62	340	4	k	int
    //   84	20	5	l	long
    //   59	219	7	localStringBuffer	StringBuffer
    //   97	130	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   277	283	331	java/lang/Throwable
    //   288	298	331	java/lang/Throwable
    //   300	306	331	java/lang/Throwable
    //   308	319	331	java/lang/Throwable
    //   68	75	337	java/lang/Throwable
    //   77	86	337	java/lang/Throwable
    //   88	99	337	java/lang/Throwable
    //   101	109	337	java/lang/Throwable
    //   111	122	337	java/lang/Throwable
    //   141	152	337	java/lang/Throwable
    //   154	162	337	java/lang/Throwable
    //   164	176	337	java/lang/Throwable
    //   178	194	337	java/lang/Throwable
    //   196	202	337	java/lang/Throwable
    //   204	215	337	java/lang/Throwable
    //   217	224	337	java/lang/Throwable
    //   226	238	337	java/lang/Throwable
    //   245	253	337	java/lang/Throwable
    //   255	270	337	java/lang/Throwable
    //   44	61	343	finally
    //   68	75	343	finally
    //   77	86	343	finally
    //   88	99	343	finally
    //   101	109	343	finally
    //   111	122	343	finally
    //   141	152	343	finally
    //   154	162	343	finally
    //   164	176	343	finally
    //   178	194	343	finally
    //   196	202	343	finally
    //   204	215	343	finally
    //   217	224	343	finally
    //   226	238	343	finally
    //   245	253	343	finally
    //   255	270	343	finally
    //   277	283	343	finally
    //   288	298	343	finally
    //   300	306	343	finally
    //   308	319	343	finally
    //   350	361	343	finally
    //   44	61	347	java/lang/Throwable
    //   3	12	391	finally
    //   16	25	391	finally
    //   34	37	391	finally
    //   319	328	391	finally
    //   361	370	391	finally
    //   375	386	391	finally
  }
  
  /* Error */
  public static List a(Context paramContext)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: ldc -106
    //   5: iconst_0
    //   6: anewarray 4	java/lang/Object
    //   9: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   12: aconst_null
    //   13: astore_1
    //   14: aconst_null
    //   15: astore_3
    //   16: aconst_null
    //   17: astore 4
    //   19: aload_0
    //   20: ifnonnull +17 -> 37
    //   23: ldc -104
    //   25: iconst_0
    //   26: anewarray 4	java/lang/Object
    //   29: invokestatic 111	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   32: ldc 2
    //   34: monitorexit
    //   35: aconst_null
    //   36: areturn
    //   37: aload_0
    //   38: invokestatic 84	com/tencent/beacontbs/a/a/c:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/a/c;
    //   41: invokevirtual 88	com/tencent/beacontbs/a/a/c:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   44: ldc -102
    //   46: aconst_null
    //   47: aconst_null
    //   48: aconst_null
    //   49: aconst_null
    //   50: aconst_null
    //   51: ldc -100
    //   53: ldc -98
    //   55: invokevirtual 162	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   58: astore_2
    //   59: aload 4
    //   61: astore_0
    //   62: aload_3
    //   63: astore_1
    //   64: aload_2
    //   65: invokeinterface 168 1 0
    //   70: ifeq +133 -> 203
    //   73: aload_3
    //   74: astore_1
    //   75: new 170	java/util/ArrayList
    //   78: dup
    //   79: invokespecial 171	java/util/ArrayList:<init>	()V
    //   82: astore_0
    //   83: aload_0
    //   84: aload_2
    //   85: aload_2
    //   86: ldc -83
    //   88: invokeinterface 177 2 0
    //   93: invokeinterface 181 2 0
    //   98: invokestatic 187	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   101: invokeinterface 193 2 0
    //   106: pop
    //   107: aload_0
    //   108: aload_2
    //   109: aload_2
    //   110: ldc -61
    //   112: invokeinterface 177 2 0
    //   117: invokeinterface 198 2 0
    //   122: invokeinterface 193 2 0
    //   127: pop
    //   128: aload_0
    //   129: aload_2
    //   130: aload_2
    //   131: ldc -56
    //   133: invokeinterface 177 2 0
    //   138: invokeinterface 204 2 0
    //   143: invokestatic 207	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   146: invokeinterface 193 2 0
    //   151: pop
    //   152: aload_0
    //   153: aload_2
    //   154: aload_2
    //   155: ldc -47
    //   157: invokeinterface 177 2 0
    //   162: invokeinterface 213 2 0
    //   167: invokeinterface 193 2 0
    //   172: pop
    //   173: aload_0
    //   174: aload_2
    //   175: aload_2
    //   176: ldc -41
    //   178: invokeinterface 177 2 0
    //   183: invokeinterface 181 2 0
    //   188: invokestatic 187	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   191: invokeinterface 193 2 0
    //   196: pop
    //   197: goto +6 -> 203
    //   200: goto +89 -> 289
    //   203: aload_0
    //   204: ifnull +22 -> 226
    //   207: aload_0
    //   208: astore_1
    //   209: ldc -39
    //   211: iconst_0
    //   212: anewarray 4	java/lang/Object
    //   215: invokestatic 44	com/tencent/beacontbs/c/a:a	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   218: goto +8 -> 226
    //   221: aload_1
    //   222: astore_0
    //   223: goto +66 -> 289
    //   226: aload_2
    //   227: ifnull +18 -> 245
    //   230: aload_2
    //   231: invokeinterface 220 1 0
    //   236: ifne +9 -> 245
    //   239: aload_2
    //   240: invokeinterface 223 1 0
    //   245: iconst_0
    //   246: anewarray 4	java/lang/Object
    //   249: astore_1
    //   250: ldc -31
    //   252: aload_1
    //   253: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   256: goto +60 -> 316
    //   259: aload_2
    //   260: ifnull +18 -> 278
    //   263: aload_2
    //   264: invokeinterface 220 1 0
    //   269: ifne +9 -> 278
    //   272: aload_2
    //   273: invokeinterface 223 1 0
    //   278: ldc -31
    //   280: iconst_0
    //   281: anewarray 4	java/lang/Object
    //   284: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   287: aload_0
    //   288: athrow
    //   289: aload_2
    //   290: ifnull +18 -> 308
    //   293: aload_2
    //   294: invokeinterface 220 1 0
    //   299: ifne +9 -> 308
    //   302: aload_2
    //   303: invokeinterface 223 1 0
    //   308: iconst_0
    //   309: anewarray 4	java/lang/Object
    //   312: astore_1
    //   313: goto -63 -> 250
    //   316: ldc 2
    //   318: monitorexit
    //   319: aload_0
    //   320: areturn
    //   321: astore_0
    //   322: ldc 2
    //   324: monitorexit
    //   325: aload_0
    //   326: athrow
    //   327: astore_0
    //   328: goto +21 -> 349
    //   331: astore_0
    //   332: goto -111 -> 221
    //   335: astore_1
    //   336: goto -136 -> 200
    //   339: astore_0
    //   340: goto -81 -> 259
    //   343: astore_0
    //   344: aconst_null
    //   345: astore_2
    //   346: goto -87 -> 259
    //   349: aconst_null
    //   350: astore_2
    //   351: aload_1
    //   352: astore_0
    //   353: goto -64 -> 289
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	356	0	paramContext	Context
    //   13	300	1	localObject1	Object
    //   335	17	1	localException	Exception
    //   58	293	2	localCursor	Cursor
    //   15	59	3	localObject2	Object
    //   17	43	4	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   3	12	321	finally
    //   23	32	321	finally
    //   230	245	321	finally
    //   245	250	321	finally
    //   250	256	321	finally
    //   263	278	321	finally
    //   278	289	321	finally
    //   293	308	321	finally
    //   308	313	321	finally
    //   37	59	327	java/lang/Exception
    //   64	73	331	java/lang/Exception
    //   75	83	331	java/lang/Exception
    //   209	218	331	java/lang/Exception
    //   83	197	335	java/lang/Exception
    //   64	73	339	finally
    //   75	83	339	finally
    //   83	197	339	finally
    //   209	218	339	finally
    //   37	59	343	finally
  }
  
  public static List<a> a(Context paramContext, int[] paramArrayOfInt, int paramInt)
  {
    try
    {
      paramContext = b(paramContext, paramArrayOfInt, paramInt);
      return paramContext;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
  
  private static List<a> a(Cursor paramCursor)
  {
    com.tencent.beacontbs.c.a.b(" in AnalyticsDAO.paserCursor() start", new Object[0]);
    if (paramCursor == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    int i = paramCursor.getColumnIndex("_id");
    int j = paramCursor.getColumnIndex("_prority");
    int k = paramCursor.getColumnIndex("_time");
    int m = paramCursor.getColumnIndex("_type");
    int n = paramCursor.getColumnIndex("_datas");
    int i1 = paramCursor.getColumnIndex("_length");
    while (paramCursor.moveToNext())
    {
      a locala = new a();
      locala.jdField_a_of_type_Long = paramCursor.getLong(i);
      locala.jdField_a_of_type_JavaLangString = paramCursor.getString(n);
      locala.jdField_b_of_type_Int = paramCursor.getInt(j);
      locala.c = paramCursor.getLong(k);
      locala.jdField_a_of_type_Int = paramCursor.getInt(m);
      locala.jdField_b_of_type_Long = paramCursor.getLong(i1);
      localArrayList.add(locala);
    }
    com.tencent.beacontbs.c.a.b(" in AnalyticsDAO.paserCursor() end", new Object[0]);
    return localArrayList;
  }
  
  /* Error */
  public static boolean a(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: ldc -10
    //   5: iconst_0
    //   6: anewarray 4	java/lang/Object
    //   9: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   12: aload_0
    //   13: ifnonnull +17 -> 30
    //   16: ldc 42
    //   18: iconst_0
    //   19: anewarray 4	java/lang/Object
    //   22: invokestatic 44	com/tencent/beacontbs/c/a:a	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   25: ldc 2
    //   27: monitorexit
    //   28: iconst_0
    //   29: ireturn
    //   30: aload_1
    //   31: ifnull +193 -> 224
    //   34: aload_1
    //   35: invokevirtual 249	java/lang/String:trim	()Ljava/lang/String;
    //   38: ldc 48
    //   40: invokevirtual 252	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   43: ifeq +6 -> 49
    //   46: goto +178 -> 224
    //   49: new 50	java/lang/StringBuilder
    //   52: dup
    //   53: ldc -2
    //   55: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   58: astore 4
    //   60: aload 4
    //   62: aload_1
    //   63: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: pop
    //   67: aload 4
    //   69: ldc_w 256
    //   72: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: pop
    //   76: aload 4
    //   78: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   81: astore_1
    //   82: new 50	java/lang/StringBuilder
    //   85: dup
    //   86: ldc 76
    //   88: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   91: astore 4
    //   93: aload 4
    //   95: aload_1
    //   96: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: pop
    //   100: aload 4
    //   102: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   105: iconst_0
    //   106: anewarray 4	java/lang/Object
    //   109: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   112: aload_0
    //   113: invokestatic 84	com/tencent/beacontbs/a/a/c:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/a/c;
    //   116: invokevirtual 88	com/tencent/beacontbs/a/a/c:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   119: ldc -102
    //   121: aload_1
    //   122: aconst_null
    //   123: invokevirtual 96	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   126: istore_2
    //   127: new 50	java/lang/StringBuilder
    //   130: dup
    //   131: ldc 98
    //   133: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   136: astore_0
    //   137: aload_0
    //   138: iload_2
    //   139: invokevirtual 60	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   142: pop
    //   143: aload_0
    //   144: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   147: iconst_0
    //   148: anewarray 4	java/lang/Object
    //   151: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   154: iconst_1
    //   155: istore_3
    //   156: iload_2
    //   157: iconst_1
    //   158: if_icmpne +6 -> 164
    //   161: goto +5 -> 166
    //   164: iconst_0
    //   165: istore_3
    //   166: ldc_w 258
    //   169: iconst_0
    //   170: anewarray 4	java/lang/Object
    //   173: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   176: goto +31 -> 207
    //   179: astore_0
    //   180: goto +32 -> 212
    //   183: astore_0
    //   184: aload_0
    //   185: invokevirtual 103	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   188: iconst_0
    //   189: anewarray 4	java/lang/Object
    //   192: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   195: ldc_w 258
    //   198: iconst_0
    //   199: anewarray 4	java/lang/Object
    //   202: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   205: iconst_0
    //   206: istore_3
    //   207: ldc 2
    //   209: monitorexit
    //   210: iload_3
    //   211: ireturn
    //   212: ldc_w 258
    //   215: iconst_0
    //   216: anewarray 4	java/lang/Object
    //   219: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   222: aload_0
    //   223: athrow
    //   224: ldc 2
    //   226: monitorexit
    //   227: iconst_0
    //   228: ireturn
    //   229: astore_0
    //   230: ldc 2
    //   232: monitorexit
    //   233: aload_0
    //   234: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	235	0	paramContext	Context
    //   0	235	1	paramString	String
    //   126	33	2	i	int
    //   155	56	3	bool	boolean
    //   58	43	4	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   112	154	179	finally
    //   184	195	179	finally
    //   112	154	183	java/lang/Throwable
    //   3	12	229	finally
    //   16	25	229	finally
    //   34	46	229	finally
    //   49	112	229	finally
    //   166	176	229	finally
    //   195	205	229	finally
    //   212	224	229	finally
  }
  
  public static boolean a(Context paramContext, List<a> paramList)
  {
    boolean bool = false;
    for (;;)
    {
      Context localContext2;
      Context localContext1;
      int i;
      try
      {
        com.tencent.beacontbs.c.a.b(" AnalyticsDAO.insert() start", new Object[0]);
        if (paramContext == null)
        {
          com.tencent.beacontbs.c.a.d(" AnalyticsDAO.insert() have null args", new Object[0]);
          return false;
        }
        if (paramList.size() <= 0)
        {
          com.tencent.beacontbs.c.a.b(" list size == 0 return true", new Object[0]);
          return true;
        }
        localContext2 = null;
        localContext1 = null;
      }
      finally {}
      try
      {
        try
        {
          paramContext = c.a(paramContext).getWritableDatabase();
          localContext1 = paramContext;
          localContext2 = paramContext;
          paramContext.beginTransaction();
          i = 0;
          localContext1 = paramContext;
          localContext2 = paramContext;
          if (i < paramList.size())
          {
            localContext1 = paramContext;
            localContext2 = paramContext;
            a locala = (a)paramList.get(i);
            localContext1 = paramContext;
            localContext2 = paramContext;
            ContentValues localContentValues = new ContentValues();
            localContext1 = paramContext;
            localContext2 = paramContext;
            if (locala.jdField_a_of_type_Long > 0L)
            {
              localContext1 = paramContext;
              localContext2 = paramContext;
              localContentValues.put("_id", Long.valueOf(locala.jdField_a_of_type_Long));
            }
            localContext1 = paramContext;
            localContext2 = paramContext;
            localContentValues.put("_prority", Integer.valueOf(locala.jdField_b_of_type_Int));
            localContext1 = paramContext;
            localContext2 = paramContext;
            localContentValues.put("_time", Long.valueOf(locala.c));
            localContext1 = paramContext;
            localContext2 = paramContext;
            localContentValues.put("_type", Integer.valueOf(locala.jdField_a_of_type_Int));
            localContext1 = paramContext;
            localContext2 = paramContext;
            localContentValues.put("_datas", locala.jdField_a_of_type_JavaLangString);
            localContext1 = paramContext;
            localContext2 = paramContext;
            localContentValues.put("_length", Long.valueOf(locala.jdField_b_of_type_Long));
            localContext1 = paramContext;
            localContext2 = paramContext;
            long l = paramContext.insert("t_event", "_id", localContentValues);
            if (l < 0L)
            {
              localContext1 = paramContext;
              localContext2 = paramContext;
              com.tencent.beacontbs.c.a.d(" AnalyticsDAO.insert() failure! return", new Object[0]);
            }
            localContext1 = paramContext;
            localContext2 = paramContext;
            locala.jdField_a_of_type_Long = l;
            i += 1;
            continue;
          }
          localContext1 = paramContext;
          localContext2 = paramContext;
          paramContext.setTransactionSuccessful();
        }
        finally {}
      }
      catch (Throwable paramContext)
      {
        continue;
      }
      try
      {
        paramContext.endTransaction();
      }
      catch (Exception paramContext) {}
    }
    com.tencent.beacontbs.c.a.b("AnalyticsDao db.endTransaction() error.", new Object[0]);
    com.tencent.beacontbs.c.a.b(" AnalyticsDAO.insert() end", new Object[0]);
    bool = true;
    break label421;
    localContext1 = localContext2;
    com.tencent.beacontbs.c.a.b("AnalyticsDAO.insert() failure!", new Object[0]);
    try
    {
      localContext2.endTransaction();
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    com.tencent.beacontbs.c.a.b("AnalyticsDao db.endTransaction() error.", new Object[0]);
    com.tencent.beacontbs.c.a.b(" AnalyticsDAO.insert() end", new Object[0]);
    label421:
    return bool;
    try
    {
      localContext1.endTransaction();
    }
    catch (Exception paramList)
    {
      for (;;) {}
    }
    com.tencent.beacontbs.c.a.b("AnalyticsDao db.endTransaction() error.", new Object[0]);
    com.tencent.beacontbs.c.a.b(" AnalyticsDAO.insert() end", new Object[0]);
    throw paramContext;
  }
  
  public static boolean a(Context paramContext, byte[] paramArrayOfByte, String paramString, int paramInt)
  {
    for (;;)
    {
      ContentValues localContentValues;
      boolean bool;
      try
      {
        com.tencent.beacontbs.c.a.b(" AnalyticsDAO.insertReqData() start", new Object[0]);
        if ((paramContext != null) && (paramArrayOfByte != null)) {
          if (paramString != null) {}
        }
      }
      finally {}
      try
      {
        try
        {
          paramContext = c.a(paramContext).getWritableDatabase();
          localContentValues = new ContentValues();
          localContentValues.put("_rid", paramString);
          localContentValues.put("_time", Long.valueOf(new Date().getTime()));
          localContentValues.put("_cnt", Integer.valueOf(paramInt));
          localContentValues.put("_datas", paramArrayOfByte);
          if (paramContext.insert("t_req_data", null, localContentValues) < 0L)
          {
            com.tencent.beacontbs.c.a.d(" AnalyticsDAO.insertReqData() failure! return", new Object[0]);
            bool = false;
          }
          else
          {
            bool = true;
          }
          com.tencent.beacontbs.c.a.b(" AnalyticsDAO.insertReqData() end", new Object[0]);
        }
        finally {}
      }
      catch (Throwable paramContext) {}
    }
    com.tencent.beacontbs.c.a.b("AnalyticsDAO.insertReqData() failure!", new Object[0]);
    com.tencent.beacontbs.c.a.b(" AnalyticsDAO.insertReqData() end", new Object[0]);
    bool = false;
    return bool;
    com.tencent.beacontbs.c.a.b(" AnalyticsDAO.insertReqData() end", new Object[0]);
    throw paramContext;
    com.tencent.beacontbs.c.a.d(" AnalyticsDAO.insertReqData() have null args", new Object[0]);
    return false;
  }
  
  /* Error */
  public static int b(Context paramContext, int[] paramArrayOfInt)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: ldc_w 329
    //   6: iconst_0
    //   7: anewarray 4	java/lang/Object
    //   10: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   13: aload_0
    //   14: ifnonnull +18 -> 32
    //   17: ldc_w 331
    //   20: iconst_0
    //   21: anewarray 4	java/lang/Object
    //   24: invokestatic 44	com/tencent/beacontbs/c/a:a	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   27: ldc 2
    //   29: monitorexit
    //   30: iconst_m1
    //   31: ireturn
    //   32: ldc 48
    //   34: astore_3
    //   35: iconst_0
    //   36: istore_2
    //   37: iload_2
    //   38: ifgt +49 -> 87
    //   41: new 50	java/lang/StringBuilder
    //   44: dup
    //   45: invokespecial 51	java/lang/StringBuilder:<init>	()V
    //   48: astore 4
    //   50: aload 4
    //   52: aload_3
    //   53: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   56: pop
    //   57: aload 4
    //   59: ldc 57
    //   61: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: pop
    //   65: aload 4
    //   67: aload_1
    //   68: iconst_0
    //   69: iaload
    //   70: invokevirtual 60	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   73: pop
    //   74: aload 4
    //   76: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   79: astore_3
    //   80: iload_2
    //   81: iconst_1
    //   82: iadd
    //   83: istore_2
    //   84: goto -47 -> 37
    //   87: aload_3
    //   88: iconst_4
    //   89: invokevirtual 70	java/lang/String:substring	(I)Ljava/lang/String;
    //   92: astore_1
    //   93: new 50	java/lang/StringBuilder
    //   96: dup
    //   97: invokespecial 51	java/lang/StringBuilder:<init>	()V
    //   100: astore_3
    //   101: aload_3
    //   102: ldc 46
    //   104: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: pop
    //   108: aload_3
    //   109: ldc 72
    //   111: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: pop
    //   115: aload_3
    //   116: aload_1
    //   117: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   120: pop
    //   121: aload_3
    //   122: ldc 74
    //   124: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: pop
    //   128: aload_3
    //   129: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   132: astore 4
    //   134: new 50	java/lang/StringBuilder
    //   137: dup
    //   138: ldc_w 333
    //   141: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   144: astore_1
    //   145: aload_1
    //   146: aload 4
    //   148: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: pop
    //   152: aload_1
    //   153: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   156: iconst_0
    //   157: anewarray 4	java/lang/Object
    //   160: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   163: aconst_null
    //   164: astore_3
    //   165: aconst_null
    //   166: astore_1
    //   167: aload_0
    //   168: invokestatic 84	com/tencent/beacontbs/a/a/c:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/a/c;
    //   171: invokevirtual 88	com/tencent/beacontbs/a/a/c:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   174: ldc 90
    //   176: iconst_1
    //   177: anewarray 66	java/lang/String
    //   180: dup
    //   181: iconst_0
    //   182: ldc_w 335
    //   185: aastore
    //   186: aload 4
    //   188: aconst_null
    //   189: aconst_null
    //   190: aconst_null
    //   191: aconst_null
    //   192: invokevirtual 338	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   195: astore_0
    //   196: aload_0
    //   197: astore_1
    //   198: aload_0
    //   199: astore_3
    //   200: aload_0
    //   201: invokeinterface 168 1 0
    //   206: pop
    //   207: aload_0
    //   208: astore_1
    //   209: aload_0
    //   210: astore_3
    //   211: aload_0
    //   212: aload_0
    //   213: ldc_w 340
    //   216: invokeinterface 177 2 0
    //   221: invokeinterface 181 2 0
    //   226: istore_2
    //   227: aload_0
    //   228: astore_1
    //   229: aload_0
    //   230: astore_3
    //   231: new 50	java/lang/StringBuilder
    //   234: dup
    //   235: ldc_w 342
    //   238: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   241: astore 4
    //   243: aload_0
    //   244: astore_1
    //   245: aload_0
    //   246: astore_3
    //   247: aload 4
    //   249: iload_2
    //   250: invokevirtual 60	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   253: pop
    //   254: aload_0
    //   255: astore_1
    //   256: aload_0
    //   257: astore_3
    //   258: aload 4
    //   260: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   263: iconst_0
    //   264: anewarray 4	java/lang/Object
    //   267: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   270: aload_0
    //   271: ifnull +18 -> 289
    //   274: aload_0
    //   275: invokeinterface 220 1 0
    //   280: ifne +9 -> 289
    //   283: aload_0
    //   284: invokeinterface 223 1 0
    //   289: ldc_w 344
    //   292: iconst_0
    //   293: anewarray 4	java/lang/Object
    //   296: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   299: goto +52 -> 351
    //   302: astore_0
    //   303: goto +53 -> 356
    //   306: astore_0
    //   307: aload_3
    //   308: astore_1
    //   309: aload_0
    //   310: invokevirtual 103	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   313: iconst_0
    //   314: anewarray 4	java/lang/Object
    //   317: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   320: aload_3
    //   321: ifnull +18 -> 339
    //   324: aload_3
    //   325: invokeinterface 220 1 0
    //   330: ifne +9 -> 339
    //   333: aload_3
    //   334: invokeinterface 223 1 0
    //   339: ldc_w 344
    //   342: iconst_0
    //   343: anewarray 4	java/lang/Object
    //   346: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   349: iconst_m1
    //   350: istore_2
    //   351: ldc 2
    //   353: monitorexit
    //   354: iload_2
    //   355: ireturn
    //   356: aload_1
    //   357: ifnull +18 -> 375
    //   360: aload_1
    //   361: invokeinterface 220 1 0
    //   366: ifne +9 -> 375
    //   369: aload_1
    //   370: invokeinterface 223 1 0
    //   375: ldc_w 344
    //   378: iconst_0
    //   379: anewarray 4	java/lang/Object
    //   382: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   385: aload_0
    //   386: athrow
    //   387: astore_0
    //   388: ldc 2
    //   390: monitorexit
    //   391: aload_0
    //   392: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	393	0	paramContext	Context
    //   0	393	1	paramArrayOfInt	int[]
    //   36	319	2	i	int
    //   34	300	3	localObject1	Object
    //   48	211	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   167	196	302	finally
    //   200	207	302	finally
    //   211	227	302	finally
    //   231	243	302	finally
    //   247	254	302	finally
    //   258	270	302	finally
    //   309	320	302	finally
    //   167	196	306	java/lang/Throwable
    //   200	207	306	java/lang/Throwable
    //   211	227	306	java/lang/Throwable
    //   231	243	306	java/lang/Throwable
    //   247	254	306	java/lang/Throwable
    //   258	270	306	java/lang/Throwable
    //   3	13	387	finally
    //   17	27	387	finally
    //   41	80	387	finally
    //   87	163	387	finally
    //   274	289	387	finally
    //   289	299	387	finally
    //   324	339	387	finally
    //   339	349	387	finally
    //   360	375	387	finally
    //   375	387	387	finally
  }
  
  private static List<a> b(Context paramContext, int[] paramArrayOfInt, int paramInt)
  {
    try
    {
      paramContext = c(paramContext, paramArrayOfInt, paramInt);
      return paramContext;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
  
  /* Error */
  private static List<a> c(Context paramContext, int[] paramArrayOfInt, int paramInt)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: ldc_w 348
    //   6: iconst_0
    //   7: anewarray 4	java/lang/Object
    //   10: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   13: aconst_null
    //   14: astore 7
    //   16: aconst_null
    //   17: astore 9
    //   19: aconst_null
    //   20: astore 6
    //   22: aconst_null
    //   23: astore 8
    //   25: aload_0
    //   26: ifnull +574 -> 600
    //   29: ldc 48
    //   31: astore 5
    //   33: aload 5
    //   35: astore 4
    //   37: aload_1
    //   38: ifnull +104 -> 142
    //   41: aload 5
    //   43: astore 4
    //   45: aload_1
    //   46: arraylength
    //   47: ifle +95 -> 142
    //   50: ldc 48
    //   52: astore 4
    //   54: iconst_0
    //   55: istore_3
    //   56: iload_3
    //   57: aload_1
    //   58: arraylength
    //   59: if_icmpge +51 -> 110
    //   62: new 50	java/lang/StringBuilder
    //   65: dup
    //   66: invokespecial 51	java/lang/StringBuilder:<init>	()V
    //   69: astore 5
    //   71: aload 5
    //   73: aload 4
    //   75: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: pop
    //   79: aload 5
    //   81: ldc 57
    //   83: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: pop
    //   87: aload 5
    //   89: aload_1
    //   90: iload_3
    //   91: iaload
    //   92: invokevirtual 60	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: aload 5
    //   98: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   101: astore 4
    //   103: iload_3
    //   104: iconst_1
    //   105: iadd
    //   106: istore_3
    //   107: goto -51 -> 56
    //   110: new 50	java/lang/StringBuilder
    //   113: dup
    //   114: invokespecial 51	java/lang/StringBuilder:<init>	()V
    //   117: astore_1
    //   118: aload_1
    //   119: ldc 48
    //   121: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: pop
    //   125: aload_1
    //   126: aload 4
    //   128: iconst_4
    //   129: invokevirtual 70	java/lang/String:substring	(I)Ljava/lang/String;
    //   132: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: aload_1
    //   137: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   140: astore 4
    //   142: aload 4
    //   144: invokevirtual 349	java/lang/String:length	()I
    //   147: ifle +474 -> 621
    //   150: new 50	java/lang/StringBuilder
    //   153: dup
    //   154: ldc_w 351
    //   157: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   160: astore_1
    //   161: aload_1
    //   162: aload 4
    //   164: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: pop
    //   168: aload_1
    //   169: ldc_w 353
    //   172: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   175: pop
    //   176: aload_1
    //   177: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   180: astore 4
    //   182: goto +3 -> 185
    //   185: new 50	java/lang/StringBuilder
    //   188: dup
    //   189: invokespecial 51	java/lang/StringBuilder:<init>	()V
    //   192: astore_1
    //   193: aload_1
    //   194: ldc 48
    //   196: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   199: pop
    //   200: aload_1
    //   201: ldc_w 355
    //   204: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   207: pop
    //   208: aload_1
    //   209: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   212: astore_1
    //   213: new 50	java/lang/StringBuilder
    //   216: dup
    //   217: invokespecial 51	java/lang/StringBuilder:<init>	()V
    //   220: astore 5
    //   222: aload 5
    //   224: aload_1
    //   225: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   228: pop
    //   229: aload 5
    //   231: ldc -100
    //   233: invokevirtual 55	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   236: pop
    //   237: aload 5
    //   239: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   242: astore 5
    //   244: aload 5
    //   246: ldc_w 357
    //   249: invokevirtual 361	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   252: ifeq +376 -> 628
    //   255: aload 5
    //   257: iconst_0
    //   258: aload 5
    //   260: invokevirtual 349	java/lang/String:length	()I
    //   263: iconst_3
    //   264: isub
    //   265: invokevirtual 364	java/lang/String:substring	(II)Ljava/lang/String;
    //   268: astore 5
    //   270: goto +3 -> 273
    //   273: ldc_w 366
    //   276: iconst_1
    //   277: anewarray 4	java/lang/Object
    //   280: dup
    //   281: iconst_0
    //   282: aload 4
    //   284: aastore
    //   285: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   288: aload 9
    //   290: astore_1
    //   291: aload_0
    //   292: invokestatic 84	com/tencent/beacontbs/a/a/c:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/a/c;
    //   295: invokevirtual 88	com/tencent/beacontbs/a/a/c:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   298: astore 10
    //   300: iload_2
    //   301: iflt +330 -> 631
    //   304: aload 9
    //   306: astore_1
    //   307: iload_2
    //   308: invokestatic 368	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   311: astore_0
    //   312: goto +3 -> 315
    //   315: aload 9
    //   317: astore_1
    //   318: aload 10
    //   320: ldc 90
    //   322: aconst_null
    //   323: aload 4
    //   325: aconst_null
    //   326: aconst_null
    //   327: aconst_null
    //   328: aload 5
    //   330: aload_0
    //   331: invokevirtual 162	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   334: astore 4
    //   336: aload 7
    //   338: astore_1
    //   339: new 50	java/lang/StringBuilder
    //   342: dup
    //   343: ldc_w 370
    //   346: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   349: astore_0
    //   350: aload 7
    //   352: astore_1
    //   353: aload_0
    //   354: aload 4
    //   356: invokeinterface 373 1 0
    //   361: invokevirtual 60	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   364: pop
    //   365: aload 7
    //   367: astore_1
    //   368: aload_0
    //   369: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   372: iconst_0
    //   373: anewarray 4	java/lang/Object
    //   376: invokestatic 44	com/tencent/beacontbs/c/a:a	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   379: aload 8
    //   381: astore_0
    //   382: aload 7
    //   384: astore_1
    //   385: aload 4
    //   387: invokeinterface 373 1 0
    //   392: ifle +12 -> 404
    //   395: aload 7
    //   397: astore_1
    //   398: aload 4
    //   400: invokestatic 375	com/tencent/beacontbs/a/a/a:a	(Landroid/database/Cursor;)Ljava/util/List;
    //   403: astore_0
    //   404: aload_0
    //   405: ifnull +45 -> 450
    //   408: aload_0
    //   409: astore_1
    //   410: new 50	java/lang/StringBuilder
    //   413: dup
    //   414: ldc_w 377
    //   417: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   420: astore 5
    //   422: aload_0
    //   423: astore_1
    //   424: aload 5
    //   426: aload_0
    //   427: invokeinterface 266 1 0
    //   432: invokevirtual 60	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   435: pop
    //   436: aload_0
    //   437: astore_1
    //   438: aload 5
    //   440: invokevirtual 64	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   443: iconst_0
    //   444: anewarray 4	java/lang/Object
    //   447: invokestatic 44	com/tencent/beacontbs/c/a:a	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   450: aload 4
    //   452: ifnull +20 -> 472
    //   455: aload 4
    //   457: invokeinterface 220 1 0
    //   462: ifne +10 -> 472
    //   465: aload 4
    //   467: invokeinterface 223 1 0
    //   472: ldc_w 379
    //   475: iconst_0
    //   476: anewarray 4	java/lang/Object
    //   479: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   482: goto +79 -> 561
    //   485: astore_0
    //   486: goto +80 -> 566
    //   489: astore 5
    //   491: aload 4
    //   493: astore_0
    //   494: aload_1
    //   495: astore 4
    //   497: goto +18 -> 515
    //   500: astore_0
    //   501: aload_1
    //   502: astore 4
    //   504: goto +62 -> 566
    //   507: astore 5
    //   509: aconst_null
    //   510: astore 4
    //   512: aload 6
    //   514: astore_0
    //   515: aload_0
    //   516: astore_1
    //   517: aload 5
    //   519: invokevirtual 103	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   522: iconst_0
    //   523: anewarray 4	java/lang/Object
    //   526: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   529: aload_0
    //   530: ifnull +18 -> 548
    //   533: aload_0
    //   534: invokeinterface 220 1 0
    //   539: ifne +9 -> 548
    //   542: aload_0
    //   543: invokeinterface 223 1 0
    //   548: ldc_w 379
    //   551: iconst_0
    //   552: anewarray 4	java/lang/Object
    //   555: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   558: aload 4
    //   560: astore_0
    //   561: ldc 2
    //   563: monitorexit
    //   564: aload_0
    //   565: areturn
    //   566: aload 4
    //   568: ifnull +20 -> 588
    //   571: aload 4
    //   573: invokeinterface 220 1 0
    //   578: ifne +10 -> 588
    //   581: aload 4
    //   583: invokeinterface 223 1 0
    //   588: ldc_w 379
    //   591: iconst_0
    //   592: anewarray 4	java/lang/Object
    //   595: invokestatic 40	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   598: aload_0
    //   599: athrow
    //   600: ldc_w 381
    //   603: iconst_0
    //   604: anewarray 4	java/lang/Object
    //   607: invokestatic 111	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   610: ldc 2
    //   612: monitorexit
    //   613: aconst_null
    //   614: areturn
    //   615: astore_0
    //   616: ldc 2
    //   618: monitorexit
    //   619: aload_0
    //   620: athrow
    //   621: ldc 48
    //   623: astore 4
    //   625: goto -440 -> 185
    //   628: goto -355 -> 273
    //   631: aconst_null
    //   632: astore_0
    //   633: goto -318 -> 315
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	636	0	paramContext	Context
    //   0	636	1	paramArrayOfInt	int[]
    //   0	636	2	paramInt	int
    //   55	52	3	i	int
    //   35	589	4	localObject1	Object
    //   31	408	5	localObject2	Object
    //   489	1	5	localThrowable1	Throwable
    //   507	11	5	localThrowable2	Throwable
    //   20	493	6	localObject3	Object
    //   14	382	7	localObject4	Object
    //   23	357	8	localObject5	Object
    //   17	299	9	localObject6	Object
    //   298	21	10	localSQLiteDatabase	SQLiteDatabase
    // Exception table:
    //   from	to	target	type
    //   339	350	485	finally
    //   353	365	485	finally
    //   368	379	485	finally
    //   385	395	485	finally
    //   398	404	485	finally
    //   410	422	485	finally
    //   424	436	485	finally
    //   438	450	485	finally
    //   339	350	489	java/lang/Throwable
    //   353	365	489	java/lang/Throwable
    //   368	379	489	java/lang/Throwable
    //   385	395	489	java/lang/Throwable
    //   398	404	489	java/lang/Throwable
    //   410	422	489	java/lang/Throwable
    //   424	436	489	java/lang/Throwable
    //   438	450	489	java/lang/Throwable
    //   291	300	500	finally
    //   307	312	500	finally
    //   318	336	500	finally
    //   517	529	500	finally
    //   291	300	507	java/lang/Throwable
    //   307	312	507	java/lang/Throwable
    //   318	336	507	java/lang/Throwable
    //   3	13	615	finally
    //   45	50	615	finally
    //   56	103	615	finally
    //   110	142	615	finally
    //   142	182	615	finally
    //   185	270	615	finally
    //   273	288	615	finally
    //   455	472	615	finally
    //   472	482	615	finally
    //   533	548	615	finally
    //   548	558	615	finally
    //   571	588	615	finally
    //   588	600	615	finally
    //   600	610	615	finally
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\a\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */