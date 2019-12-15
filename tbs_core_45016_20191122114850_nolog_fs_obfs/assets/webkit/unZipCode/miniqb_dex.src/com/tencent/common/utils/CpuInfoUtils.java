package com.tencent.common.utils;

import android.text.TextUtils;
import com.tencent.basesupport.FLogger;

public class CpuInfoUtils
{
  public static final int ANDROID_CPU_ARM64_FEATURE_AES = 4;
  public static final int ANDROID_CPU_ARM64_FEATURE_ASIMD = 2;
  public static final int ANDROID_CPU_ARM64_FEATURE_CRC32 = 64;
  public static final int ANDROID_CPU_ARM64_FEATURE_FP = 1;
  public static final int ANDROID_CPU_ARM64_FEATURE_PMULL = 8;
  public static final int ANDROID_CPU_ARM64_FEATURE_SHA1 = 16;
  public static final int ANDROID_CPU_ARM64_FEATURE_SHA2 = 32;
  public static final int ANDROID_CPU_ARM_FEATURE_AES = 4096;
  public static final int ANDROID_CPU_ARM_FEATURE_ARMv7 = 1;
  public static final int ANDROID_CPU_ARM_FEATURE_CRC32 = 65536;
  public static final int ANDROID_CPU_ARM_FEATURE_IDIV_ARM = 512;
  public static final int ANDROID_CPU_ARM_FEATURE_IDIV_THUMB2 = 1024;
  public static final int ANDROID_CPU_ARM_FEATURE_LDREX_STREX = 8;
  public static final int ANDROID_CPU_ARM_FEATURE_NEON = 4;
  public static final int ANDROID_CPU_ARM_FEATURE_NEON_FMA = 256;
  public static final int ANDROID_CPU_ARM_FEATURE_PMULL = 8192;
  public static final int ANDROID_CPU_ARM_FEATURE_SHA1 = 16384;
  public static final int ANDROID_CPU_ARM_FEATURE_SHA2 = 32768;
  public static final int ANDROID_CPU_ARM_FEATURE_VFP_D32 = 32;
  public static final int ANDROID_CPU_ARM_FEATURE_VFP_FMA = 128;
  public static final int ANDROID_CPU_ARM_FEATURE_VFP_FP16 = 64;
  public static final int ANDROID_CPU_ARM_FEATURE_VFPv2 = 16;
  public static final int ANDROID_CPU_ARM_FEATURE_VFPv3 = 2;
  public static final int ANDROID_CPU_ARM_FEATURE_iWMMXt = 2048;
  public static final int ANDROID_CPU_FAMILY_ARM = 1;
  public static final int ANDROID_CPU_FAMILY_ARM64 = 4;
  public static final int ANDROID_CPU_FAMILY_MAX = 7;
  public static final int ANDROID_CPU_FAMILY_MIPS = 3;
  public static final int ANDROID_CPU_FAMILY_MIPS64 = 6;
  public static final int ANDROID_CPU_FAMILY_UNKNOWN = 0;
  public static final int ANDROID_CPU_FAMILY_X86 = 2;
  public static final int ANDROID_CPU_FAMILY_X86_64 = 5;
  public static final int ANDROID_CPU_X86_FEATURE_MOVBE = 4;
  public static final int ANDROID_CPU_X86_FEATURE_POPCNT = 2;
  public static final int ANDROID_CPU_X86_FEATURE_SSSE3 = 1;
  public static final int ARM_FEATURE_ARMV8 = 18;
  public static final int ARM_FEATURE_ARMv6 = -2;
  public static final int ARM_FEATURE_ARMv7 = 5;
  public static final int ARM_FEATURE_NEON = 17;
  public static final int ARM_FEATURE_NOT_ARMv7 = 33;
  public static final int ARM_FEATURE_VFPv3 = 9;
  public static final int CPU_UNKNOWN = -1000;
  public static final int X86_FEATURE_SSSE3 = 6;
  private static int jdField_a_of_type_Int = -1;
  private static long jdField_a_of_type_Long = -1L;
  private static int b = -1;
  private static int c = -1;
  private static int d = -1;
  private static int e = -1;
  public static int sCupType = -1000;
  public static int sCupTypeFromJava = -1000;
  
  private static int a()
  {
    Object[] arrayOfObject = a();
    int j = 0;
    if ("V8".equals(arrayOfObject[0])) {
      return 18;
    }
    if ("ARM".equals(arrayOfObject[0]))
    {
      boolean bool = "neon".equals(arrayOfObject[2]);
      int i;
      if ((((Integer)arrayOfObject[1]).intValue() == 7) && ("".equals(arrayOfObject[2]))) {
        i = 1;
      } else {
        i = 0;
      }
      if (((Integer)arrayOfObject[1]).intValue() == 6) {
        j = 1;
      }
      if (bool) {
        return 17;
      }
      if (i != 0) {
        return 9;
      }
      if (j != 0) {
        return -2;
      }
      return 33;
    }
    if ("INTEL".equals(arrayOfObject[0])) {
      return 6;
    }
    return 64536;
  }
  
  /* Error */
  private static Object[] a()
  {
    // Byte code:
    //   0: iconst_3
    //   1: anewarray 4	java/lang/Object
    //   4: astore 4
    //   6: aload 4
    //   8: iconst_0
    //   9: ldc 116
    //   11: aastore
    //   12: aload 4
    //   14: iconst_1
    //   15: iconst_0
    //   16: invokestatic 128	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   19: aastore
    //   20: aload 4
    //   22: iconst_0
    //   23: ldc 116
    //   25: aastore
    //   26: new 130	java/io/File
    //   29: dup
    //   30: ldc -124
    //   32: invokespecial 135	java/io/File:<init>	(Ljava/lang/String;)V
    //   35: invokevirtual 139	java/io/File:exists	()Z
    //   38: ifeq +538 -> 576
    //   41: new 141	java/io/BufferedReader
    //   44: dup
    //   45: new 143	java/io/FileReader
    //   48: dup
    //   49: new 130	java/io/File
    //   52: dup
    //   53: ldc -124
    //   55: invokespecial 135	java/io/File:<init>	(Ljava/lang/String;)V
    //   58: invokespecial 146	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   61: invokespecial 149	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   64: astore_2
    //   65: aload_2
    //   66: astore_1
    //   67: aload_2
    //   68: invokevirtual 153	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   71: astore_3
    //   72: aload_3
    //   73: ifnull +435 -> 508
    //   76: aload_2
    //   77: astore_1
    //   78: aload_3
    //   79: invokevirtual 156	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   82: astore 5
    //   84: aload_2
    //   85: astore_1
    //   86: aload 5
    //   88: ldc -98
    //   90: invokevirtual 162	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   93: astore_3
    //   94: aload_2
    //   95: astore_1
    //   96: new 164	java/lang/StringBuilder
    //   99: dup
    //   100: invokespecial 165	java/lang/StringBuilder:<init>	()V
    //   103: astore 6
    //   105: aload_2
    //   106: astore_1
    //   107: aload 6
    //   109: ldc -89
    //   111: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: pop
    //   115: aload_2
    //   116: astore_1
    //   117: aload 6
    //   119: aload 5
    //   121: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: pop
    //   125: aload_2
    //   126: astore_1
    //   127: ldc -83
    //   129: aload 6
    //   131: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   134: invokestatic 181	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   137: aload_2
    //   138: astore_1
    //   139: aload_3
    //   140: arraylength
    //   141: iconst_2
    //   142: if_icmpeq +6 -> 148
    //   145: goto -80 -> 65
    //   148: aload_2
    //   149: astore_1
    //   150: aload_3
    //   151: iconst_0
    //   152: aaload
    //   153: invokevirtual 184	java/lang/String:trim	()Ljava/lang/String;
    //   156: astore 6
    //   158: aload_2
    //   159: astore_1
    //   160: aload_3
    //   161: iconst_1
    //   162: aaload
    //   163: invokevirtual 184	java/lang/String:trim	()Ljava/lang/String;
    //   166: astore 5
    //   168: aload_2
    //   169: astore_1
    //   170: aload 6
    //   172: ldc -70
    //   174: invokevirtual 190	java/lang/String:compareTo	(Ljava/lang/String;)I
    //   177: ifne +197 -> 374
    //   180: aload_2
    //   181: astore_1
    //   182: aload 4
    //   184: iconst_0
    //   185: aaload
    //   186: checkcast 101	java/lang/String
    //   189: invokestatic 196	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   192: ifeq +182 -> 374
    //   195: aload_2
    //   196: astore_1
    //   197: aload 5
    //   199: ldc -58
    //   201: invokevirtual 201	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   204: ifeq +12 -> 216
    //   207: aload 4
    //   209: iconst_0
    //   210: ldc 99
    //   212: aastore
    //   213: goto -148 -> 65
    //   216: ldc 116
    //   218: astore_3
    //   219: aload_2
    //   220: astore_1
    //   221: aload 5
    //   223: ldc -53
    //   225: invokevirtual 206	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   228: iconst_4
    //   229: iadd
    //   230: istore_0
    //   231: aload_2
    //   232: astore_1
    //   233: iload_0
    //   234: aload 5
    //   236: invokevirtual 209	java/lang/String:length	()I
    //   239: if_icmpge +104 -> 343
    //   242: aload_2
    //   243: astore_1
    //   244: new 164	java/lang/StringBuilder
    //   247: dup
    //   248: invokespecial 165	java/lang/StringBuilder:<init>	()V
    //   251: astore 6
    //   253: aload_2
    //   254: astore_1
    //   255: aload 6
    //   257: aload 5
    //   259: iload_0
    //   260: invokevirtual 213	java/lang/String:charAt	(I)C
    //   263: invokevirtual 216	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   266: pop
    //   267: aload_2
    //   268: astore_1
    //   269: aload 6
    //   271: ldc 116
    //   273: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   276: pop
    //   277: aload_2
    //   278: astore_1
    //   279: aload 6
    //   281: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   284: astore 6
    //   286: aload_2
    //   287: astore_1
    //   288: aload 6
    //   290: ldc -38
    //   292: invokevirtual 222	java/lang/String:matches	(Ljava/lang/String;)Z
    //   295: ifeq +48 -> 343
    //   298: aload_2
    //   299: astore_1
    //   300: new 164	java/lang/StringBuilder
    //   303: dup
    //   304: invokespecial 165	java/lang/StringBuilder:<init>	()V
    //   307: astore 7
    //   309: aload_2
    //   310: astore_1
    //   311: aload 7
    //   313: aload_3
    //   314: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   317: pop
    //   318: aload_2
    //   319: astore_1
    //   320: aload 7
    //   322: aload 6
    //   324: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   327: pop
    //   328: aload_2
    //   329: astore_1
    //   330: aload 7
    //   332: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   335: astore_3
    //   336: iload_0
    //   337: iconst_1
    //   338: iadd
    //   339: istore_0
    //   340: goto -109 -> 231
    //   343: aload 4
    //   345: iconst_0
    //   346: ldc 107
    //   348: aastore
    //   349: aload_2
    //   350: astore_1
    //   351: aload_3
    //   352: invokestatic 196	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   355: ifne -290 -> 65
    //   358: aload_2
    //   359: astore_1
    //   360: aload 4
    //   362: iconst_1
    //   363: aload_3
    //   364: invokestatic 225	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   367: invokestatic 128	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   370: aastore
    //   371: goto -306 -> 65
    //   374: aload_2
    //   375: astore_1
    //   376: aload 6
    //   378: ldc -29
    //   380: invokevirtual 230	java/lang/String:compareToIgnoreCase	(Ljava/lang/String;)I
    //   383: ifne +24 -> 407
    //   386: aload_2
    //   387: astore_1
    //   388: aload 5
    //   390: ldc 109
    //   392: invokevirtual 201	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   395: ifeq -330 -> 65
    //   398: aload 4
    //   400: iconst_2
    //   401: ldc 109
    //   403: aastore
    //   404: goto -339 -> 65
    //   407: aload_2
    //   408: astore_1
    //   409: aload 6
    //   411: ldc -24
    //   413: invokevirtual 230	java/lang/String:compareToIgnoreCase	(Ljava/lang/String;)I
    //   416: ifne +30 -> 446
    //   419: aload_2
    //   420: astore_1
    //   421: aload 5
    //   423: ldc -22
    //   425: invokevirtual 201	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   428: ifeq -363 -> 65
    //   431: aload 4
    //   433: iconst_0
    //   434: ldc 118
    //   436: aastore
    //   437: aload 4
    //   439: iconst_2
    //   440: ldc -20
    //   442: aastore
    //   443: goto -378 -> 65
    //   446: aload_2
    //   447: astore_1
    //   448: aload 6
    //   450: ldc -18
    //   452: invokevirtual 230	java/lang/String:compareToIgnoreCase	(Ljava/lang/String;)I
    //   455: istore_0
    //   456: iload_0
    //   457: ifne +20 -> 477
    //   460: aload_2
    //   461: astore_1
    //   462: aload 4
    //   464: iconst_1
    //   465: aload 5
    //   467: invokestatic 225	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   470: invokestatic 128	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   473: aastore
    //   474: goto -409 -> 65
    //   477: aload_2
    //   478: astore_1
    //   479: aload 6
    //   481: ldc -16
    //   483: invokevirtual 230	java/lang/String:compareToIgnoreCase	(Ljava/lang/String;)I
    //   486: istore_0
    //   487: iload_0
    //   488: ifne -423 -> 65
    //   491: aload_2
    //   492: astore_1
    //   493: aload 4
    //   495: iconst_1
    //   496: aload 5
    //   498: invokestatic 225	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   501: invokestatic 128	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   504: aastore
    //   505: goto -440 -> 65
    //   508: aload_2
    //   509: invokevirtual 243	java/io/BufferedReader:close	()V
    //   512: aload 4
    //   514: areturn
    //   515: astore_3
    //   516: goto +12 -> 528
    //   519: astore_1
    //   520: aconst_null
    //   521: astore_2
    //   522: goto +36 -> 558
    //   525: astore_3
    //   526: aconst_null
    //   527: astore_2
    //   528: aload_2
    //   529: astore_1
    //   530: aload_3
    //   531: invokevirtual 246	java/lang/Throwable:printStackTrace	()V
    //   534: aload_2
    //   535: ifnull +41 -> 576
    //   538: aload_2
    //   539: invokevirtual 243	java/io/BufferedReader:close	()V
    //   542: aload 4
    //   544: areturn
    //   545: astore_1
    //   546: aload_1
    //   547: invokevirtual 247	java/io/IOException:printStackTrace	()V
    //   550: aload 4
    //   552: areturn
    //   553: astore_3
    //   554: aload_1
    //   555: astore_2
    //   556: aload_3
    //   557: astore_1
    //   558: aload_2
    //   559: ifnull +15 -> 574
    //   562: aload_2
    //   563: invokevirtual 243	java/io/BufferedReader:close	()V
    //   566: goto +8 -> 574
    //   569: astore_2
    //   570: aload_2
    //   571: invokevirtual 247	java/io/IOException:printStackTrace	()V
    //   574: aload_1
    //   575: athrow
    //   576: aload 4
    //   578: areturn
    //   579: astore_1
    //   580: goto -515 -> 65
    // Local variable table:
    //   start	length	slot	name	signature
    //   230	258	0	i	int
    //   66	427	1	localObject1	Object
    //   519	1	1	localObject2	Object
    //   529	1	1	localObject3	Object
    //   545	10	1	localIOException1	java.io.IOException
    //   557	18	1	localObject4	Object
    //   579	1	1	localException	Exception
    //   64	499	2	localObject5	Object
    //   569	2	2	localIOException2	java.io.IOException
    //   71	293	3	localObject6	Object
    //   515	1	3	localThrowable1	Throwable
    //   525	6	3	localThrowable2	Throwable
    //   553	4	3	localObject7	Object
    //   4	573	4	arrayOfObject	Object[]
    //   82	415	5	str	String
    //   103	377	6	localObject8	Object
    //   307	24	7	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   67	72	515	java/lang/Throwable
    //   78	84	515	java/lang/Throwable
    //   86	94	515	java/lang/Throwable
    //   96	105	515	java/lang/Throwable
    //   107	115	515	java/lang/Throwable
    //   117	125	515	java/lang/Throwable
    //   127	137	515	java/lang/Throwable
    //   139	145	515	java/lang/Throwable
    //   150	158	515	java/lang/Throwable
    //   160	168	515	java/lang/Throwable
    //   170	180	515	java/lang/Throwable
    //   182	195	515	java/lang/Throwable
    //   197	207	515	java/lang/Throwable
    //   221	231	515	java/lang/Throwable
    //   233	242	515	java/lang/Throwable
    //   244	253	515	java/lang/Throwable
    //   255	267	515	java/lang/Throwable
    //   269	277	515	java/lang/Throwable
    //   279	286	515	java/lang/Throwable
    //   288	298	515	java/lang/Throwable
    //   300	309	515	java/lang/Throwable
    //   311	318	515	java/lang/Throwable
    //   320	328	515	java/lang/Throwable
    //   330	336	515	java/lang/Throwable
    //   351	358	515	java/lang/Throwable
    //   360	371	515	java/lang/Throwable
    //   376	386	515	java/lang/Throwable
    //   388	398	515	java/lang/Throwable
    //   409	419	515	java/lang/Throwable
    //   421	431	515	java/lang/Throwable
    //   448	456	515	java/lang/Throwable
    //   462	474	515	java/lang/Throwable
    //   479	487	515	java/lang/Throwable
    //   493	505	515	java/lang/Throwable
    //   41	65	519	finally
    //   41	65	525	java/lang/Throwable
    //   508	512	545	java/io/IOException
    //   538	542	545	java/io/IOException
    //   67	72	553	finally
    //   78	84	553	finally
    //   86	94	553	finally
    //   96	105	553	finally
    //   107	115	553	finally
    //   117	125	553	finally
    //   127	137	553	finally
    //   139	145	553	finally
    //   150	158	553	finally
    //   160	168	553	finally
    //   170	180	553	finally
    //   182	195	553	finally
    //   197	207	553	finally
    //   221	231	553	finally
    //   233	242	553	finally
    //   244	253	553	finally
    //   255	267	553	finally
    //   269	277	553	finally
    //   279	286	553	finally
    //   288	298	553	finally
    //   300	309	553	finally
    //   311	318	553	finally
    //   320	328	553	finally
    //   330	336	553	finally
    //   351	358	553	finally
    //   360	371	553	finally
    //   376	386	553	finally
    //   388	398	553	finally
    //   409	419	553	finally
    //   421	431	553	finally
    //   448	456	553	finally
    //   462	474	553	finally
    //   479	487	553	finally
    //   493	505	553	finally
    //   530	534	553	finally
    //   562	566	569	java/io/IOException
    //   351	358	579	java/lang/Exception
    //   360	371	579	java/lang/Exception
    //   462	474	579	java/lang/Exception
    //   493	505	579	java/lang/Exception
  }
  
  /* Error */
  public static boolean checkArmVersion(String paramString)
  {
    // Byte code:
    //   0: new 130	java/io/File
    //   3: dup
    //   4: ldc -124
    //   6: invokespecial 135	java/io/File:<init>	(Ljava/lang/String;)V
    //   9: invokevirtual 139	java/io/File:exists	()Z
    //   12: istore_2
    //   13: iconst_0
    //   14: istore 4
    //   16: iconst_0
    //   17: istore_3
    //   18: iload_2
    //   19: ifeq +153 -> 172
    //   22: aconst_null
    //   23: astore 7
    //   25: aconst_null
    //   26: astore 5
    //   28: new 141	java/io/BufferedReader
    //   31: dup
    //   32: new 143	java/io/FileReader
    //   35: dup
    //   36: new 130	java/io/File
    //   39: dup
    //   40: ldc -124
    //   42: invokespecial 135	java/io/File:<init>	(Ljava/lang/String;)V
    //   45: invokespecial 146	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   48: invokespecial 149	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   51: astore 6
    //   53: aload 6
    //   55: invokevirtual 153	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   58: astore 5
    //   60: iload_3
    //   61: istore_2
    //   62: aload 5
    //   64: ifnull +20 -> 84
    //   67: aload 5
    //   69: invokevirtual 156	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   72: aload_0
    //   73: invokevirtual 206	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   76: istore_1
    //   77: iconst_m1
    //   78: iload_1
    //   79: if_icmpeq -26 -> 53
    //   82: iconst_1
    //   83: istore_2
    //   84: iload_2
    //   85: istore_3
    //   86: aload 6
    //   88: invokevirtual 243	java/io/BufferedReader:close	()V
    //   91: iload_2
    //   92: ireturn
    //   93: astore_0
    //   94: aload_0
    //   95: invokevirtual 247	java/io/IOException:printStackTrace	()V
    //   98: iload_3
    //   99: ireturn
    //   100: astore_0
    //   101: aload 6
    //   103: astore 5
    //   105: goto +45 -> 150
    //   108: astore 5
    //   110: aload 6
    //   112: astore_0
    //   113: aload 5
    //   115: astore 6
    //   117: goto +12 -> 129
    //   120: astore_0
    //   121: goto +29 -> 150
    //   124: astore 6
    //   126: aload 7
    //   128: astore_0
    //   129: aload_0
    //   130: astore 5
    //   132: aload 6
    //   134: invokevirtual 247	java/io/IOException:printStackTrace	()V
    //   137: aload_0
    //   138: ifnull +34 -> 172
    //   141: iload 4
    //   143: istore_3
    //   144: aload_0
    //   145: invokevirtual 243	java/io/BufferedReader:close	()V
    //   148: iconst_0
    //   149: ireturn
    //   150: aload 5
    //   152: ifnull +18 -> 170
    //   155: aload 5
    //   157: invokevirtual 243	java/io/BufferedReader:close	()V
    //   160: goto +10 -> 170
    //   163: astore 5
    //   165: aload 5
    //   167: invokevirtual 247	java/io/IOException:printStackTrace	()V
    //   170: aload_0
    //   171: athrow
    //   172: iconst_0
    //   173: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	174	0	paramString	String
    //   76	4	1	i	int
    //   12	80	2	bool1	boolean
    //   17	127	3	bool2	boolean
    //   14	128	4	bool3	boolean
    //   26	78	5	localObject1	Object
    //   108	6	5	localIOException1	java.io.IOException
    //   130	26	5	str	String
    //   163	3	5	localIOException2	java.io.IOException
    //   51	65	6	localObject2	Object
    //   124	9	6	localIOException3	java.io.IOException
    //   23	104	7	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   86	91	93	java/io/IOException
    //   144	148	93	java/io/IOException
    //   53	60	100	finally
    //   67	77	100	finally
    //   53	60	108	java/io/IOException
    //   67	77	108	java/io/IOException
    //   28	53	120	finally
    //   132	137	120	finally
    //   28	53	124	java/io/IOException
    //   155	160	163	java/io/IOException
  }
  
  public static int getCPUType()
  {
    long l = getCpuFeatures();
    int i = getCpuFamily();
    if (i == 1)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("getCPUTypeWrapper type,cpu family is arm,value:");
      localStringBuilder.append(i);
      localStringBuilder.append(",cpufeature value is:");
      localStringBuilder.append(l);
      FLogger.d("CpuInfoUtils", localStringBuilder.toString());
      if ((0x4 & l) != 0L)
      {
        FLogger.d("CpuInfoUtils", "getCPUTypeWrapper type,cpu feature is neon(vfpv3+v7+vfp_d32)");
        return 17;
      }
      if ((0x2 & l) != 0L)
      {
        FLogger.d("CpuInfoUtils", "getCPUTypeWrapper type,cpu feature is vfpv3(vfpv2+v7)");
        return 9;
      }
      if ((l & 1L) == 1L)
      {
        if (isMT6573()) {
          return -2;
        }
        FLogger.d("CpuInfoUtils", "getCPUTypeWrapper type,cpu feature is v7");
        return 5;
      }
      if (isArmV5())
      {
        FLogger.d("CpuInfoUtils", "getCPUTypeWrapper type,cpu feature is NOT v7(v4,v5)");
        return 33;
      }
      FLogger.d("CpuInfoUtils", "getCPUTypeWrapper type,cpu feature is v6");
      return -2;
    }
    if (i == 4)
    {
      FLogger.d("CpuInfoUtils", "getCPUTypeWrapper type,cpu feature is v8");
      return 18;
    }
    if (i == 2)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("getCPUTypeWrapper type,cpu family is x86,value:");
      localStringBuilder.append(i);
      localStringBuilder.append(",cpufeature value is:");
      localStringBuilder.append(l);
      FLogger.d("CpuInfoUtils", localStringBuilder.toString());
      if ((l & 1L) != 0L)
      {
        FLogger.d("CpuInfoUtils", "getCPUTypeWrapper type,cpu feature is SSE3");
        return 6;
      }
      return -1;
    }
    if (sCupTypeFromJava == 64536) {
      sCupTypeFromJava = a();
    }
    int j = sCupTypeFromJava;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getCPUTypeWrapper type, getCpuTypeFromJava,value is:");
    localStringBuilder.append(sCupTypeFromJava);
    localStringBuilder.append(", cpufamily:");
    localStringBuilder.append(i);
    FLogger.d("CpuInfoUtils", localStringBuilder.toString());
    return j;
  }
  
  public static int getCpuCount()
  {
    if ((b < 0) && (init())) {}
    try
    {
      b = nativeGetCpuCount();
      return b;
    }
    catch (Exception|Error localException)
    {
      for (;;) {}
    }
  }
  
  public static int getCpuFamily()
  {
    if ((jdField_a_of_type_Int < 0) && (init())) {}
    try
    {
      jdField_a_of_type_Int = nativeGetCpuFamily();
      return jdField_a_of_type_Int;
    }
    catch (Exception|Error localException)
    {
      for (;;) {}
    }
  }
  
  public static long getCpuFeatures()
  {
    if ((jdField_a_of_type_Long < 0L) && (init())) {}
    try
    {
      jdField_a_of_type_Long = nativeGetCpuFeatures();
      return jdField_a_of_type_Long;
    }
    catch (Exception|Error localException)
    {
      for (;;) {}
    }
  }
  
  public static boolean init()
  {
    synchronized (LinuxToolsJni.gBaseModuleSoLock)
    {
      boolean bool = LinuxToolsJni.gJniloaded;
      if (!bool) {
        try
        {
          String str1 = QBSoLoader.tinkerSoLoadLibraryPath("common_basemodule_jni");
          if (TextUtils.isEmpty(str1)) {
            System.loadLibrary("common_basemodule_jni");
          } else {
            System.load(str1);
          }
          LinuxToolsJni.gJniloaded = true;
        }
        catch (Throwable localThrowable1)
        {
          localThrowable1.printStackTrace();
          try
          {
            String str2 = QBSoLoader.tinkerSoLoadPath("/data/data/com.tencent.mtt/lib/libcommon_basemodule_jni.so");
            if (TextUtils.isEmpty(str2)) {
              System.load("/data/data/com.tencent.mtt/lib/libcommon_basemodule_jni.so");
            } else {
              System.load(str2);
            }
            LinuxToolsJni.gJniloaded = true;
          }
          catch (Throwable localThrowable2)
          {
            localThrowable2.printStackTrace();
          }
        }
      }
      return LinuxToolsJni.gJniloaded;
    }
  }
  
  public static boolean init(String paramString)
  {
    synchronized (LinuxToolsJni.gBaseModuleSoLock)
    {
      if (LinuxToolsJni.gJniloaded)
      {
        bool = LinuxToolsJni.gJniloaded;
        return bool;
      }
      try
      {
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(paramString);
        ((StringBuilder)localObject2).append("/libcommon_basemodule_jni.so");
        localObject2 = QBSoLoader.tinkerSoLoadPath(((StringBuilder)localObject2).toString());
        if (TextUtils.isEmpty((CharSequence)localObject2))
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append(paramString);
          ((StringBuilder)localObject2).append("/libcommon_basemodule_jni.so");
          System.load(((StringBuilder)localObject2).toString());
        }
        else
        {
          System.load((String)localObject2);
        }
        LinuxToolsJni.gJniloaded = true;
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
        LinuxToolsJni.gJniloaded = false;
      }
      boolean bool = LinuxToolsJni.gJniloaded;
      return bool;
    }
  }
  
  public static boolean isArmV5()
  {
    if (c < 0) {
      if (checkArmVersion("cpu architecture: 5")) {
        c = 1;
      } else {
        c = 0;
      }
    }
    return c > 0;
  }
  
  public static boolean isMT6573()
  {
    if (d < 0) {
      if (checkArmVersion(": mt6573")) {
        d = 1;
      } else {
        d = 0;
      }
    }
    return d > 0;
  }
  
  public static boolean isMT6577()
  {
    if (e < 0) {
      if (checkArmVersion(": mt6577")) {
        e = 1;
      } else {
        e = 0;
      }
    }
    return e > 0;
  }
  
  public static boolean isSupportedCPU()
  {
    if (sCupType == 64536) {
      sCupType = getCPUType();
    }
    int i = sCupType;
    return (i == 17) || (i == 9) || (i == 6) || (i == 18);
  }
  
  private static native int nativeGetCpuCount();
  
  private static native int nativeGetCpuFamily();
  
  private static native long nativeGetCpuFeatures();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\CpuInfoUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */