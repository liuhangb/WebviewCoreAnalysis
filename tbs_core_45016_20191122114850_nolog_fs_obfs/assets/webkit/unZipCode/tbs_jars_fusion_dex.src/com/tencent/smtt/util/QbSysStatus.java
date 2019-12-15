package com.tencent.smtt.util;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.TrafficStats;
import android.os.Debug.MemoryInfo;
import android.os.Process;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

public class QbSysStatus
{
  static int a = 1024;
  
  public static double JNIgetBatteryLevel(Context paramContext)
  {
    if (paramContext == null) {
      return 0.0D;
    }
    try
    {
      paramContext = X5ApiCompatibilityUtils.x5RegisterReceiver(paramContext, null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
      int i = paramContext.getIntExtra("level", -1);
      int j = paramContext.getIntExtra("scale", -1);
      if ((i != -1) && (j != -1)) {
        return i / j * 100.0F;
      }
      return 50.0D;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return 0.0D;
  }
  
  public static double JNIgetCpuUsage()
  {
    return a();
  }
  
  public static long JNIgetCurrentTraffic()
  {
    long l1 = TrafficStats.getTotalRxBytes();
    long l2 = TrafficStats.getTotalTxBytes();
    if ((l1 != -1L) && (l2 != -1L)) {
      return l1 + l2;
    }
    return 0L;
  }
  
  public static long JNIgetMemoryUsage(Context paramContext)
  {
    if (paramContext == null) {
      return 0L;
    }
    int i = Process.myPid();
    return ((android.app.ActivityManager)paramContext.getSystemService("activity")).getProcessMemoryInfo(new int[] { i })[0].getTotalPss() * a;
  }
  
  /* Error */
  public static long JNIgetTotalCpuTime()
  {
    // Byte code:
    //   0: lconst_0
    //   1: lstore_2
    //   2: new 100	java/io/RandomAccessFile
    //   5: dup
    //   6: ldc 102
    //   8: ldc 104
    //   10: invokespecial 107	java/io/RandomAccessFile:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   13: astore 12
    //   15: lload_2
    //   16: lstore 4
    //   18: lload_2
    //   19: lstore 6
    //   21: lload_2
    //   22: lstore 8
    //   24: aload 12
    //   26: invokevirtual 111	java/io/RandomAccessFile:readLine	()Ljava/lang/String;
    //   29: ldc 113
    //   31: invokevirtual 119	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   34: astore 14
    //   36: lload_2
    //   37: lstore 4
    //   39: lload_2
    //   40: lstore 6
    //   42: lload_2
    //   43: lstore 8
    //   45: aload 14
    //   47: arraylength
    //   48: istore_1
    //   49: iconst_0
    //   50: istore_0
    //   51: lload_2
    //   52: lstore 4
    //   54: aload 12
    //   56: astore 13
    //   58: iload_0
    //   59: iload_1
    //   60: if_icmpge +140 -> 200
    //   63: aload 14
    //   65: iload_0
    //   66: aaload
    //   67: astore 13
    //   69: lload_2
    //   70: lstore 4
    //   72: lload_2
    //   73: lstore 6
    //   75: lload_2
    //   76: lstore 8
    //   78: aload 13
    //   80: ldc 121
    //   82: invokevirtual 125	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   85: ifeq +6 -> 91
    //   88: goto +24 -> 112
    //   91: lload_2
    //   92: lstore 4
    //   94: lload_2
    //   95: lstore 6
    //   97: lload_2
    //   98: lstore 8
    //   100: aload 13
    //   102: invokestatic 131	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   105: lstore 10
    //   107: lload_2
    //   108: lload 10
    //   110: ladd
    //   111: lstore_2
    //   112: iload_0
    //   113: iconst_1
    //   114: iadd
    //   115: istore_0
    //   116: goto -65 -> 51
    //   119: astore 13
    //   121: lload 4
    //   123: lstore_2
    //   124: goto +24 -> 148
    //   127: astore 13
    //   129: lload 6
    //   131: lstore_2
    //   132: goto +36 -> 168
    //   135: astore 13
    //   137: lload 8
    //   139: lstore_2
    //   140: goto +48 -> 188
    //   143: astore 13
    //   145: aconst_null
    //   146: astore 12
    //   148: aload 13
    //   150: invokevirtual 132	java/lang/NumberFormatException:printStackTrace	()V
    //   153: lload_2
    //   154: lstore 4
    //   156: aload 12
    //   158: astore 13
    //   160: goto +40 -> 200
    //   163: astore 13
    //   165: aconst_null
    //   166: astore 12
    //   168: aload 13
    //   170: invokevirtual 133	java/io/IOException:printStackTrace	()V
    //   173: lload_2
    //   174: lstore 4
    //   176: aload 12
    //   178: astore 13
    //   180: goto +20 -> 200
    //   183: astore 13
    //   185: aconst_null
    //   186: astore 12
    //   188: aload 13
    //   190: invokevirtual 134	java/io/FileNotFoundException:printStackTrace	()V
    //   193: aload 12
    //   195: astore 13
    //   197: lload_2
    //   198: lstore 4
    //   200: aload 13
    //   202: ifnull +18 -> 220
    //   205: aload 13
    //   207: invokevirtual 137	java/io/RandomAccessFile:close	()V
    //   210: lload 4
    //   212: lreturn
    //   213: astore 12
    //   215: aload 12
    //   217: invokevirtual 133	java/io/IOException:printStackTrace	()V
    //   220: lload 4
    //   222: lreturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   50	66	0	i	int
    //   48	13	1	j	int
    //   1	197	2	l1	long
    //   16	205	4	l2	long
    //   19	111	6	l3	long
    //   22	116	8	l4	long
    //   105	4	10	l5	long
    //   13	181	12	localRandomAccessFile1	RandomAccessFile
    //   213	3	12	localIOException1	IOException
    //   56	45	13	localObject	Object
    //   119	1	13	localNumberFormatException1	NumberFormatException
    //   127	1	13	localIOException2	IOException
    //   135	1	13	localFileNotFoundException1	java.io.FileNotFoundException
    //   143	6	13	localNumberFormatException2	NumberFormatException
    //   158	1	13	localRandomAccessFile2	RandomAccessFile
    //   163	6	13	localIOException3	IOException
    //   178	1	13	localRandomAccessFile3	RandomAccessFile
    //   183	6	13	localFileNotFoundException2	java.io.FileNotFoundException
    //   195	11	13	localRandomAccessFile4	RandomAccessFile
    //   34	30	14	arrayOfString	String[]
    // Exception table:
    //   from	to	target	type
    //   24	36	119	java/lang/NumberFormatException
    //   45	49	119	java/lang/NumberFormatException
    //   78	88	119	java/lang/NumberFormatException
    //   100	107	119	java/lang/NumberFormatException
    //   24	36	127	java/io/IOException
    //   45	49	127	java/io/IOException
    //   78	88	127	java/io/IOException
    //   100	107	127	java/io/IOException
    //   24	36	135	java/io/FileNotFoundException
    //   45	49	135	java/io/FileNotFoundException
    //   78	88	135	java/io/FileNotFoundException
    //   100	107	135	java/io/FileNotFoundException
    //   2	15	143	java/lang/NumberFormatException
    //   2	15	163	java/io/IOException
    //   2	15	183	java/io/FileNotFoundException
    //   205	210	213	java/io/IOException
  }
  
  public static long JNIgetTotalProcessCpuTime()
  {
    Object localObject;
    try
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("/proc/");
      ((StringBuilder)localObject).append(Process.myPid());
      ((StringBuilder)localObject).append("/stat");
      localObject = new RandomAccessFile(((StringBuilder)localObject).toString(), "r");
      try
      {
        String[] arrayOfString = ((RandomAccessFile)localObject).readLine().split(" +");
        l1 = Long.parseLong(arrayOfString[13]);
        long l2 = Long.parseLong(arrayOfString[14]);
        long l3 = Long.parseLong(arrayOfString[15]);
        long l4 = Long.parseLong(arrayOfString[16]);
        l1 = l1 + l2 + l3 + l4;
      }
      catch (Exception localException1) {}
      localException2.printStackTrace();
    }
    catch (Exception localException2)
    {
      localObject = null;
    }
    long l1 = 0L;
    if (localObject != null) {
      try
      {
        ((RandomAccessFile)localObject).close();
        return l1;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }
    return l1;
  }
  
  /* Error */
  private static double a()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 22
    //   3: aconst_null
    //   4: astore 24
    //   6: new 100	java/io/RandomAccessFile
    //   9: dup
    //   10: ldc 102
    //   12: ldc 104
    //   14: invokespecial 107	java/io/RandomAccessFile:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   17: astore 21
    //   19: aload 21
    //   21: invokevirtual 111	java/io/RandomAccessFile:readLine	()Ljava/lang/String;
    //   24: ldc 113
    //   26: invokevirtual 119	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   29: astore 22
    //   31: aload 22
    //   33: arraylength
    //   34: istore 5
    //   36: lconst_0
    //   37: lstore 7
    //   39: iconst_0
    //   40: istore 4
    //   42: iload 4
    //   44: iload 5
    //   46: if_icmpge +36 -> 82
    //   49: aload 22
    //   51: iload 4
    //   53: aaload
    //   54: astore 23
    //   56: aload 23
    //   58: ldc 121
    //   60: invokevirtual 125	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   63: ifeq +6 -> 69
    //   66: goto +688 -> 754
    //   69: lload 7
    //   71: aload 23
    //   73: invokestatic 131	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   76: ladd
    //   77: lstore 7
    //   79: goto +675 -> 754
    //   82: new 140	java/lang/StringBuilder
    //   85: dup
    //   86: invokespecial 141	java/lang/StringBuilder:<init>	()V
    //   89: astore 22
    //   91: aload 22
    //   93: ldc -113
    //   95: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   98: pop
    //   99: aload 22
    //   101: invokestatic 70	android/os/Process:myPid	()I
    //   104: invokevirtual 150	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   107: pop
    //   108: aload 22
    //   110: ldc -104
    //   112: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: pop
    //   116: new 100	java/io/RandomAccessFile
    //   119: dup
    //   120: aload 22
    //   122: invokevirtual 155	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   125: ldc 104
    //   127: invokespecial 107	java/io/RandomAccessFile:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   130: astore 22
    //   132: aload 22
    //   134: invokevirtual 111	java/io/RandomAccessFile:readLine	()Ljava/lang/String;
    //   137: ldc 113
    //   139: invokevirtual 119	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   142: astore 23
    //   144: aload 23
    //   146: bipush 13
    //   148: aaload
    //   149: invokestatic 131	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   152: aload 23
    //   154: bipush 14
    //   156: aaload
    //   157: invokestatic 131	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   160: ladd
    //   161: aload 23
    //   163: bipush 15
    //   165: aaload
    //   166: invokestatic 131	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   169: ladd
    //   170: aload 23
    //   172: bipush 16
    //   174: aaload
    //   175: invokestatic 131	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   178: ladd
    //   179: lstore 11
    //   181: bipush 10
    //   183: istore 4
    //   185: dconst_0
    //   186: dstore_0
    //   187: ldc2_w 158
    //   190: invokestatic 165	java/lang/Thread:sleep	(J)V
    //   193: aload 21
    //   195: lconst_0
    //   196: invokevirtual 168	java/io/RandomAccessFile:seek	(J)V
    //   199: aload 21
    //   201: invokevirtual 111	java/io/RandomAccessFile:readLine	()Ljava/lang/String;
    //   204: ldc 113
    //   206: invokevirtual 119	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   209: astore 23
    //   211: aload 23
    //   213: arraylength
    //   214: istore 6
    //   216: lconst_0
    //   217: lstore 9
    //   219: iconst_0
    //   220: istore 5
    //   222: iload 5
    //   224: iload 6
    //   226: if_icmpge +36 -> 262
    //   229: aload 23
    //   231: iload 5
    //   233: aaload
    //   234: astore 24
    //   236: aload 24
    //   238: ldc 121
    //   240: invokevirtual 125	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   243: ifeq +6 -> 249
    //   246: goto +517 -> 763
    //   249: lload 9
    //   251: aload 24
    //   253: invokestatic 131	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   256: ladd
    //   257: lstore 9
    //   259: goto +504 -> 763
    //   262: aload 22
    //   264: lconst_0
    //   265: invokevirtual 168	java/io/RandomAccessFile:seek	(J)V
    //   268: aload 22
    //   270: invokevirtual 111	java/io/RandomAccessFile:readLine	()Ljava/lang/String;
    //   273: ldc 113
    //   275: invokevirtual 119	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   278: astore 23
    //   280: aload 23
    //   282: bipush 13
    //   284: aaload
    //   285: invokestatic 131	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   288: lstore 13
    //   290: aload 23
    //   292: bipush 14
    //   294: aaload
    //   295: invokestatic 131	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   298: lstore 15
    //   300: aload 23
    //   302: bipush 15
    //   304: aaload
    //   305: invokestatic 131	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   308: lstore 17
    //   310: aload 23
    //   312: bipush 16
    //   314: aaload
    //   315: invokestatic 131	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   318: lstore 19
    //   320: lload 13
    //   322: lload 15
    //   324: ladd
    //   325: lload 17
    //   327: ladd
    //   328: lload 19
    //   330: ladd
    //   331: lstore 13
    //   333: lload 13
    //   335: lload 11
    //   337: lcmp
    //   338: iflt +81 -> 419
    //   341: lload 9
    //   343: lload 7
    //   345: lcmp
    //   346: ifgt +6 -> 352
    //   349: goto +70 -> 419
    //   352: lload 9
    //   354: lload 7
    //   356: lsub
    //   357: lstore 9
    //   359: lload 9
    //   361: lconst_0
    //   362: lcmp
    //   363: ifeq +42 -> 405
    //   366: lload 13
    //   368: lload 11
    //   370: lsub
    //   371: l2d
    //   372: dstore_0
    //   373: lload 9
    //   375: l2d
    //   376: dstore_2
    //   377: dload_0
    //   378: invokestatic 174	java/lang/Double:isNaN	(D)Z
    //   381: pop
    //   382: dload_2
    //   383: invokestatic 174	java/lang/Double:isNaN	(D)Z
    //   386: pop
    //   387: dload_0
    //   388: dload_2
    //   389: ddiv
    //   390: dstore_0
    //   391: iconst_0
    //   392: istore 6
    //   394: iload 4
    //   396: istore 5
    //   398: iload 6
    //   400: istore 4
    //   402: goto +26 -> 428
    //   405: iconst_0
    //   406: istore 6
    //   408: iload 4
    //   410: istore 5
    //   412: iload 6
    //   414: istore 4
    //   416: goto +12 -> 428
    //   419: iload 4
    //   421: iconst_1
    //   422: isub
    //   423: istore 5
    //   425: iconst_1
    //   426: istore 4
    //   428: iload 4
    //   430: ifeq +18 -> 448
    //   433: iload 5
    //   435: ifgt +6 -> 441
    //   438: goto +10 -> 448
    //   441: iload 5
    //   443: istore 4
    //   445: goto -258 -> 187
    //   448: aload 21
    //   450: invokevirtual 137	java/io/RandomAccessFile:close	()V
    //   453: aload 22
    //   455: invokevirtual 137	java/io/RandomAccessFile:close	()V
    //   458: dload_0
    //   459: dreturn
    //   460: astore 23
    //   462: goto +174 -> 636
    //   465: astore 23
    //   467: goto +24 -> 491
    //   470: astore 23
    //   472: goto +39 -> 511
    //   475: goto +194 -> 669
    //   478: astore 23
    //   480: aconst_null
    //   481: astore 22
    //   483: goto +153 -> 636
    //   486: astore 23
    //   488: aconst_null
    //   489: astore 22
    //   491: aload 23
    //   493: astore 25
    //   495: aload 21
    //   497: astore 24
    //   499: aload 22
    //   501: astore 21
    //   503: goto +46 -> 549
    //   506: astore 23
    //   508: aconst_null
    //   509: astore 22
    //   511: aload 23
    //   513: astore 25
    //   515: aload 21
    //   517: astore 24
    //   519: aload 22
    //   521: astore 21
    //   523: goto +69 -> 592
    //   526: aconst_null
    //   527: astore 22
    //   529: goto +140 -> 669
    //   532: astore 23
    //   534: aconst_null
    //   535: astore 21
    //   537: aload 21
    //   539: astore 22
    //   541: goto +95 -> 636
    //   544: astore 25
    //   546: aconst_null
    //   547: astore 21
    //   549: aload 24
    //   551: astore 23
    //   553: aload 21
    //   555: astore 22
    //   557: aload 25
    //   559: invokevirtual 132	java/lang/NumberFormatException:printStackTrace	()V
    //   562: aload 24
    //   564: ifnull +11 -> 575
    //   567: aload 24
    //   569: invokevirtual 137	java/io/RandomAccessFile:close	()V
    //   572: goto +3 -> 575
    //   575: aload 21
    //   577: ifnull +121 -> 698
    //   580: goto +111 -> 691
    //   583: astore 25
    //   585: aconst_null
    //   586: astore 21
    //   588: aload 22
    //   590: astore 24
    //   592: aload 24
    //   594: astore 23
    //   596: aload 21
    //   598: astore 22
    //   600: aload 25
    //   602: invokevirtual 175	java/lang/InterruptedException:printStackTrace	()V
    //   605: aload 24
    //   607: ifnull +11 -> 618
    //   610: aload 24
    //   612: invokevirtual 137	java/io/RandomAccessFile:close	()V
    //   615: goto +3 -> 618
    //   618: aload 21
    //   620: ifnull +78 -> 698
    //   623: goto +68 -> 691
    //   626: astore 24
    //   628: aload 23
    //   630: astore 21
    //   632: aload 24
    //   634: astore 23
    //   636: aload 21
    //   638: ifnull +11 -> 649
    //   641: aload 21
    //   643: invokevirtual 137	java/io/RandomAccessFile:close	()V
    //   646: goto +3 -> 649
    //   649: aload 22
    //   651: ifnull +8 -> 659
    //   654: aload 22
    //   656: invokevirtual 137	java/io/RandomAccessFile:close	()V
    //   659: aload 23
    //   661: athrow
    //   662: aconst_null
    //   663: astore 21
    //   665: aload 21
    //   667: astore 22
    //   669: aload 21
    //   671: ifnull +11 -> 682
    //   674: aload 21
    //   676: invokevirtual 137	java/io/RandomAccessFile:close	()V
    //   679: goto +3 -> 682
    //   682: aload 22
    //   684: ifnull +14 -> 698
    //   687: aload 22
    //   689: astore 21
    //   691: aload 21
    //   693: invokevirtual 137	java/io/RandomAccessFile:close	()V
    //   696: dconst_0
    //   697: dreturn
    //   698: dconst_0
    //   699: dreturn
    //   700: astore 21
    //   702: goto -40 -> 662
    //   705: astore 22
    //   707: goto -181 -> 526
    //   710: astore 23
    //   712: goto -237 -> 475
    //   715: astore 21
    //   717: goto -264 -> 453
    //   720: astore 21
    //   722: dload_0
    //   723: dreturn
    //   724: astore 22
    //   726: goto -151 -> 575
    //   729: astore 22
    //   731: goto -113 -> 618
    //   734: astore 21
    //   736: goto -87 -> 649
    //   739: astore 21
    //   741: goto -82 -> 659
    //   744: astore 21
    //   746: goto -64 -> 682
    //   749: astore 21
    //   751: goto -55 -> 696
    //   754: iload 4
    //   756: iconst_1
    //   757: iadd
    //   758: istore 4
    //   760: goto -718 -> 42
    //   763: iload 5
    //   765: iconst_1
    //   766: iadd
    //   767: istore 5
    //   769: goto -547 -> 222
    // Local variable table:
    //   start	length	slot	name	signature
    //   186	537	0	d1	double
    //   376	13	2	d2	double
    //   40	719	4	i	int
    //   34	734	5	j	int
    //   214	199	6	k	int
    //   37	318	7	l1	long
    //   217	157	9	l2	long
    //   179	190	11	l3	long
    //   288	79	13	l4	long
    //   298	25	15	l5	long
    //   308	18	17	l6	long
    //   318	11	19	l7	long
    //   17	675	21	localObject1	Object
    //   700	1	21	localIOException1	IOException
    //   715	1	21	localException1	Exception
    //   720	1	21	localException2	Exception
    //   734	1	21	localException3	Exception
    //   739	1	21	localException4	Exception
    //   744	1	21	localException5	Exception
    //   749	1	21	localException6	Exception
    //   1	687	22	localObject2	Object
    //   705	1	22	localIOException2	IOException
    //   724	1	22	localException7	Exception
    //   729	1	22	localException8	Exception
    //   54	257	23	localObject3	Object
    //   460	1	23	localObject4	Object
    //   465	1	23	localNumberFormatException1	NumberFormatException
    //   470	1	23	localInterruptedException1	InterruptedException
    //   478	1	23	localObject5	Object
    //   486	6	23	localNumberFormatException2	NumberFormatException
    //   506	6	23	localInterruptedException2	InterruptedException
    //   532	1	23	localObject6	Object
    //   551	109	23	localObject7	Object
    //   710	1	23	localIOException3	IOException
    //   4	607	24	localObject8	Object
    //   626	7	24	localObject9	Object
    //   493	21	25	localObject10	Object
    //   544	14	25	localNumberFormatException3	NumberFormatException
    //   583	18	25	localInterruptedException3	InterruptedException
    // Exception table:
    //   from	to	target	type
    //   132	181	460	finally
    //   187	216	460	finally
    //   236	246	460	finally
    //   249	259	460	finally
    //   262	320	460	finally
    //   132	181	465	java/lang/NumberFormatException
    //   187	216	465	java/lang/NumberFormatException
    //   236	246	465	java/lang/NumberFormatException
    //   249	259	465	java/lang/NumberFormatException
    //   262	320	465	java/lang/NumberFormatException
    //   132	181	470	java/lang/InterruptedException
    //   187	216	470	java/lang/InterruptedException
    //   236	246	470	java/lang/InterruptedException
    //   249	259	470	java/lang/InterruptedException
    //   262	320	470	java/lang/InterruptedException
    //   19	36	478	finally
    //   56	66	478	finally
    //   69	79	478	finally
    //   82	132	478	finally
    //   19	36	486	java/lang/NumberFormatException
    //   56	66	486	java/lang/NumberFormatException
    //   69	79	486	java/lang/NumberFormatException
    //   82	132	486	java/lang/NumberFormatException
    //   19	36	506	java/lang/InterruptedException
    //   56	66	506	java/lang/InterruptedException
    //   69	79	506	java/lang/InterruptedException
    //   82	132	506	java/lang/InterruptedException
    //   6	19	532	finally
    //   6	19	544	java/lang/NumberFormatException
    //   6	19	583	java/lang/InterruptedException
    //   557	562	626	finally
    //   600	605	626	finally
    //   6	19	700	java/io/IOException
    //   19	36	705	java/io/IOException
    //   56	66	705	java/io/IOException
    //   69	79	705	java/io/IOException
    //   82	132	705	java/io/IOException
    //   132	181	710	java/io/IOException
    //   187	216	710	java/io/IOException
    //   236	246	710	java/io/IOException
    //   249	259	710	java/io/IOException
    //   262	320	710	java/io/IOException
    //   448	453	715	java/lang/Exception
    //   453	458	720	java/lang/Exception
    //   567	572	724	java/lang/Exception
    //   610	615	729	java/lang/Exception
    //   641	646	734	java/lang/Exception
    //   654	659	739	java/lang/Exception
    //   674	679	744	java/lang/Exception
    //   691	696	749	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\QbSysStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */