package com.tencent.smtt.b;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Debug.MemoryInfo;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import android.view.Display;
import android.view.WindowManager;
import com.tencent.smtt.webkit.ContextHolder;
import java.util.ArrayList;

public class b
{
  private static volatile b jdField_a_of_type_ComTencentSmttBB;
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private Context jdField_a_of_type_AndroidContentContext;
  private Choreographer.FrameCallback jdField_a_of_type_AndroidViewChoreographer$FrameCallback;
  private Choreographer jdField_a_of_type_AndroidViewChoreographer;
  public volatile boolean a;
  private int jdField_b_of_type_Int;
  private long jdField_b_of_type_Long;
  private long c;
  private long d;
  private long e;
  private long f = -1L;
  private long g = -1L;
  private long h = -1L;
  private long i = -1L;
  
  private b(Context paramContext)
  {
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    if (Build.VERSION.SDK_INT >= 16)
    {
      if (Looper.myLooper() == Looper.getMainLooper()) {
        this.jdField_a_of_type_AndroidViewChoreographer = Choreographer.getInstance();
      }
      this.jdField_a_of_type_AndroidViewChoreographer$FrameCallback = new Choreographer.FrameCallback()
      {
        @TargetApi(16)
        public void doFrame(long paramAnonymousLong)
        {
          long l1 = System.currentTimeMillis();
          long l2 = SystemClock.uptimeMillis();
          paramAnonymousLong /= 1000000L;
          a.a();
          a.o.add(Long.valueOf(paramAnonymousLong + (l1 - l2)));
          if (b.a(b.this) != null) {
            b.a(b.this).postFrameCallback(this);
          }
        }
      };
    }
  }
  
  /* Error */
  private Bundle a(int paramInt, String paramString)
  {
    // Byte code:
    //   0: new 82	android/os/Bundle
    //   3: dup
    //   4: invokespecial 83	android/os/Bundle:<init>	()V
    //   7: astore 14
    //   9: lconst_0
    //   10: lstore 7
    //   12: iload_1
    //   13: tableswitch	default:+31->44, 1:+618->631, 2:+542->555, 3:+266->279, 4:+36->49
    //   44: aconst_null
    //   45: astore_2
    //   46: goto +729 -> 775
    //   49: new 85	java/io/BufferedReader
    //   52: dup
    //   53: new 87	java/io/InputStreamReader
    //   56: dup
    //   57: new 89	java/io/FileInputStream
    //   60: dup
    //   61: aload_2
    //   62: invokespecial 92	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   65: invokespecial 95	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   68: sipush 1000
    //   71: invokespecial 98	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   74: astore 13
    //   76: aload 13
    //   78: astore_2
    //   79: aload 13
    //   81: astore 11
    //   83: aload 13
    //   85: astore 12
    //   87: aload 13
    //   89: invokevirtual 102	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   92: astore 15
    //   94: aload 15
    //   96: ifnull +112 -> 208
    //   99: aload 13
    //   101: astore_2
    //   102: aload 13
    //   104: astore 11
    //   106: aload 13
    //   108: astore 12
    //   110: aload 15
    //   112: ldc 104
    //   114: invokevirtual 110	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   117: astore 15
    //   119: aload 13
    //   121: astore_2
    //   122: aload 13
    //   124: astore 11
    //   126: aload 13
    //   128: astore 12
    //   130: aload 15
    //   132: bipush 13
    //   134: aaload
    //   135: invokestatic 116	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   138: aload 15
    //   140: bipush 14
    //   142: aaload
    //   143: invokestatic 116	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   146: ladd
    //   147: aload 15
    //   149: bipush 15
    //   151: aaload
    //   152: invokestatic 116	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   155: ladd
    //   156: aload 15
    //   158: bipush 16
    //   160: aaload
    //   161: invokestatic 116	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   164: ladd
    //   165: lstore 7
    //   167: aload 13
    //   169: astore_2
    //   170: aload 13
    //   172: astore 11
    //   174: aload 13
    //   176: astore 12
    //   178: aload_0
    //   179: getfield 118	com/tencent/smtt/b/b:d	J
    //   182: lstore 9
    //   184: aload 13
    //   186: astore_2
    //   187: aload 13
    //   189: astore 11
    //   191: aload 13
    //   193: astore 12
    //   195: aload_0
    //   196: lload 7
    //   198: putfield 118	com/tencent/smtt/b/b:d	J
    //   201: lload 7
    //   203: lload 9
    //   205: lsub
    //   206: lstore 7
    //   208: lload 7
    //   210: l2d
    //   211: dstore_3
    //   212: aload 13
    //   214: astore_2
    //   215: aload 13
    //   217: astore 11
    //   219: aload 13
    //   221: astore 12
    //   223: aload_0
    //   224: getfield 120	com/tencent/smtt/b/b:jdField_a_of_type_Long	J
    //   227: lstore 7
    //   229: lload 7
    //   231: l2d
    //   232: dstore 5
    //   234: dload_3
    //   235: invokestatic 126	java/lang/Double:isNaN	(D)Z
    //   238: pop
    //   239: dload 5
    //   241: invokestatic 126	java/lang/Double:isNaN	(D)Z
    //   244: pop
    //   245: dload_3
    //   246: dload 5
    //   248: ddiv
    //   249: dstore_3
    //   250: aload 13
    //   252: astore_2
    //   253: aload 13
    //   255: astore 11
    //   257: aload 13
    //   259: astore 12
    //   261: aload 14
    //   263: ldc -128
    //   265: dload_3
    //   266: ldc2_w 129
    //   269: dmul
    //   270: invokevirtual 134	android/os/Bundle:putDouble	(Ljava/lang/String;D)V
    //   273: aload 13
    //   275: astore_2
    //   276: goto +499 -> 775
    //   279: ldc2_w 135
    //   282: dstore_3
    //   283: new 85	java/io/BufferedReader
    //   286: dup
    //   287: new 87	java/io/InputStreamReader
    //   290: dup
    //   291: new 89	java/io/FileInputStream
    //   294: dup
    //   295: aload_2
    //   296: invokespecial 92	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   299: invokespecial 95	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   302: sipush 1000
    //   305: invokespecial 98	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   308: astore 13
    //   310: aload 13
    //   312: astore_2
    //   313: aload 13
    //   315: astore 11
    //   317: aload 13
    //   319: astore 12
    //   321: aload 13
    //   323: invokevirtual 102	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   326: astore 15
    //   328: aload 15
    //   330: ifnull +200 -> 530
    //   333: aload 13
    //   335: astore_2
    //   336: aload 13
    //   338: astore 11
    //   340: aload 13
    //   342: astore 12
    //   344: aload 15
    //   346: ldc 104
    //   348: invokevirtual 110	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   351: astore 15
    //   353: aload 13
    //   355: astore_2
    //   356: aload 13
    //   358: astore 11
    //   360: aload 13
    //   362: astore 12
    //   364: aload 15
    //   366: iconst_2
    //   367: aaload
    //   368: invokestatic 116	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   371: aload 15
    //   373: iconst_3
    //   374: aaload
    //   375: invokestatic 116	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   378: ladd
    //   379: aload 15
    //   381: iconst_4
    //   382: aaload
    //   383: invokestatic 116	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   386: ladd
    //   387: aload 15
    //   389: bipush 6
    //   391: aaload
    //   392: invokestatic 116	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   395: ladd
    //   396: aload 15
    //   398: bipush 7
    //   400: aaload
    //   401: invokestatic 116	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   404: ladd
    //   405: aload 15
    //   407: bipush 8
    //   409: aaload
    //   410: invokestatic 116	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   413: ladd
    //   414: lstore 7
    //   416: aload 13
    //   418: astore_2
    //   419: aload 13
    //   421: astore 11
    //   423: aload 13
    //   425: astore 12
    //   427: aload 15
    //   429: iconst_5
    //   430: aaload
    //   431: invokestatic 116	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   434: lstore 9
    //   436: aload 13
    //   438: astore_2
    //   439: aload 13
    //   441: astore 11
    //   443: aload 13
    //   445: astore 12
    //   447: aload_0
    //   448: lload 7
    //   450: aload_0
    //   451: getfield 138	com/tencent/smtt/b/b:jdField_b_of_type_Long	J
    //   454: lsub
    //   455: lload 9
    //   457: ladd
    //   458: aload_0
    //   459: getfield 140	com/tencent/smtt/b/b:c	J
    //   462: lsub
    //   463: putfield 120	com/tencent/smtt/b/b:jdField_a_of_type_Long	J
    //   466: aload 13
    //   468: astore_2
    //   469: aload 13
    //   471: astore 11
    //   473: aload 13
    //   475: astore 12
    //   477: lload 7
    //   479: aload_0
    //   480: getfield 138	com/tencent/smtt/b/b:jdField_b_of_type_Long	J
    //   483: lsub
    //   484: l2f
    //   485: ldc -115
    //   487: fmul
    //   488: aload_0
    //   489: getfield 120	com/tencent/smtt/b/b:jdField_a_of_type_Long	J
    //   492: l2f
    //   493: fdiv
    //   494: f2d
    //   495: dstore_3
    //   496: aload 13
    //   498: astore_2
    //   499: aload 13
    //   501: astore 11
    //   503: aload 13
    //   505: astore 12
    //   507: aload_0
    //   508: lload 7
    //   510: putfield 138	com/tencent/smtt/b/b:jdField_b_of_type_Long	J
    //   513: aload 13
    //   515: astore_2
    //   516: aload 13
    //   518: astore 11
    //   520: aload 13
    //   522: astore 12
    //   524: aload_0
    //   525: lload 9
    //   527: putfield 140	com/tencent/smtt/b/b:c	J
    //   530: aload 13
    //   532: astore_2
    //   533: aload 13
    //   535: astore 11
    //   537: aload 13
    //   539: astore 12
    //   541: aload 14
    //   543: ldc -128
    //   545: dload_3
    //   546: invokevirtual 134	android/os/Bundle:putDouble	(Ljava/lang/String;D)V
    //   549: aload 13
    //   551: astore_2
    //   552: goto +223 -> 775
    //   555: new 85	java/io/BufferedReader
    //   558: dup
    //   559: new 143	java/io/FileReader
    //   562: dup
    //   563: aload_2
    //   564: invokespecial 144	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   567: sipush 8192
    //   570: invokespecial 98	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   573: astore 13
    //   575: aload 13
    //   577: astore_2
    //   578: aload 13
    //   580: astore 11
    //   582: aload 13
    //   584: astore 12
    //   586: aload 14
    //   588: ldc -128
    //   590: aload 13
    //   592: invokevirtual 102	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   595: ldc -110
    //   597: ldc -108
    //   599: invokevirtual 152	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   602: invokevirtual 155	java/lang/String:trim	()Ljava/lang/String;
    //   605: ldc 104
    //   607: invokevirtual 110	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   610: iconst_0
    //   611: aaload
    //   612: invokestatic 159	java/lang/Long:valueOf	(Ljava/lang/String;)Ljava/lang/Long;
    //   615: invokevirtual 163	java/lang/Long:longValue	()J
    //   618: ldc2_w 164
    //   621: lmul
    //   622: invokevirtual 169	android/os/Bundle:putLong	(Ljava/lang/String;J)V
    //   625: aload 13
    //   627: astore_2
    //   628: goto +147 -> 775
    //   631: new 85	java/io/BufferedReader
    //   634: dup
    //   635: new 143	java/io/FileReader
    //   638: dup
    //   639: aload_2
    //   640: invokespecial 144	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   643: invokespecial 172	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   646: astore 13
    //   648: aload 13
    //   650: astore_2
    //   651: aload 13
    //   653: astore 11
    //   655: aload 13
    //   657: astore 12
    //   659: aload 13
    //   661: invokevirtual 102	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   664: astore 15
    //   666: aload 15
    //   668: ifnull +27 -> 695
    //   671: aload 13
    //   673: astore_2
    //   674: aload 13
    //   676: astore 11
    //   678: aload 13
    //   680: astore 12
    //   682: aload 15
    //   684: invokevirtual 155	java/lang/String:trim	()Ljava/lang/String;
    //   687: invokestatic 159	java/lang/Long:valueOf	(Ljava/lang/String;)Ljava/lang/Long;
    //   690: invokevirtual 163	java/lang/Long:longValue	()J
    //   693: lstore 7
    //   695: aload 13
    //   697: astore_2
    //   698: aload 13
    //   700: astore 11
    //   702: aload 13
    //   704: astore 12
    //   706: aload 14
    //   708: ldc -128
    //   710: lload 7
    //   712: invokevirtual 169	android/os/Bundle:putLong	(Ljava/lang/String;J)V
    //   715: aload 13
    //   717: astore_2
    //   718: goto +57 -> 775
    //   721: astore 12
    //   723: aload_2
    //   724: astore 11
    //   726: aload 12
    //   728: astore_2
    //   729: goto +7 -> 736
    //   732: astore_2
    //   733: aconst_null
    //   734: astore 11
    //   736: aload 11
    //   738: ifnull +8 -> 746
    //   741: aload 11
    //   743: invokevirtual 175	java/io/BufferedReader:close	()V
    //   746: aload_2
    //   747: athrow
    //   748: aconst_null
    //   749: astore 12
    //   751: aload 12
    //   753: ifnull +29 -> 782
    //   756: aload 12
    //   758: astore_2
    //   759: aload_2
    //   760: invokevirtual 175	java/io/BufferedReader:close	()V
    //   763: aload 14
    //   765: areturn
    //   766: aconst_null
    //   767: astore_2
    //   768: aload_2
    //   769: ifnull +13 -> 782
    //   772: goto -13 -> 759
    //   775: aload_2
    //   776: ifnull +6 -> 782
    //   779: goto -20 -> 759
    //   782: aload 14
    //   784: areturn
    //   785: astore_2
    //   786: goto -20 -> 766
    //   789: astore_2
    //   790: goto -42 -> 748
    //   793: astore_2
    //   794: aload 11
    //   796: astore_2
    //   797: goto -29 -> 768
    //   800: astore_2
    //   801: goto -50 -> 751
    //   804: astore 11
    //   806: goto -60 -> 746
    //   809: astore_2
    //   810: aload 14
    //   812: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	813	0	this	b
    //   0	813	1	paramInt	int
    //   0	813	2	paramString	String
    //   211	335	3	d1	double
    //   232	15	5	d2	double
    //   10	701	7	l1	long
    //   182	344	9	l2	long
    //   81	714	11	localObject1	Object
    //   804	1	11	localException	Exception
    //   85	620	12	localBufferedReader1	java.io.BufferedReader
    //   721	6	12	localObject2	Object
    //   749	8	12	localObject3	Object
    //   74	642	13	localBufferedReader2	java.io.BufferedReader
    //   7	804	14	localBundle	Bundle
    //   92	591	15	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   87	94	721	finally
    //   110	119	721	finally
    //   130	167	721	finally
    //   178	184	721	finally
    //   195	201	721	finally
    //   223	229	721	finally
    //   261	273	721	finally
    //   321	328	721	finally
    //   344	353	721	finally
    //   364	416	721	finally
    //   427	436	721	finally
    //   447	466	721	finally
    //   477	496	721	finally
    //   507	513	721	finally
    //   524	530	721	finally
    //   541	549	721	finally
    //   586	625	721	finally
    //   659	666	721	finally
    //   682	695	721	finally
    //   706	715	721	finally
    //   49	76	732	finally
    //   283	310	732	finally
    //   555	575	732	finally
    //   631	648	732	finally
    //   49	76	785	java/io/FileNotFoundException
    //   283	310	785	java/io/FileNotFoundException
    //   555	575	785	java/io/FileNotFoundException
    //   631	648	785	java/io/FileNotFoundException
    //   49	76	789	java/io/IOException
    //   283	310	789	java/io/IOException
    //   555	575	789	java/io/IOException
    //   631	648	789	java/io/IOException
    //   87	94	793	java/io/FileNotFoundException
    //   110	119	793	java/io/FileNotFoundException
    //   130	167	793	java/io/FileNotFoundException
    //   178	184	793	java/io/FileNotFoundException
    //   195	201	793	java/io/FileNotFoundException
    //   223	229	793	java/io/FileNotFoundException
    //   261	273	793	java/io/FileNotFoundException
    //   321	328	793	java/io/FileNotFoundException
    //   344	353	793	java/io/FileNotFoundException
    //   364	416	793	java/io/FileNotFoundException
    //   427	436	793	java/io/FileNotFoundException
    //   447	466	793	java/io/FileNotFoundException
    //   477	496	793	java/io/FileNotFoundException
    //   507	513	793	java/io/FileNotFoundException
    //   524	530	793	java/io/FileNotFoundException
    //   541	549	793	java/io/FileNotFoundException
    //   586	625	793	java/io/FileNotFoundException
    //   659	666	793	java/io/FileNotFoundException
    //   682	695	793	java/io/FileNotFoundException
    //   706	715	793	java/io/FileNotFoundException
    //   87	94	800	java/io/IOException
    //   110	119	800	java/io/IOException
    //   130	167	800	java/io/IOException
    //   178	184	800	java/io/IOException
    //   195	201	800	java/io/IOException
    //   223	229	800	java/io/IOException
    //   261	273	800	java/io/IOException
    //   321	328	800	java/io/IOException
    //   344	353	800	java/io/IOException
    //   364	416	800	java/io/IOException
    //   427	436	800	java/io/IOException
    //   447	466	800	java/io/IOException
    //   477	496	800	java/io/IOException
    //   507	513	800	java/io/IOException
    //   524	530	800	java/io/IOException
    //   541	549	800	java/io/IOException
    //   586	625	800	java/io/IOException
    //   659	666	800	java/io/IOException
    //   682	695	800	java/io/IOException
    //   706	715	800	java/io/IOException
    //   741	746	804	java/lang/Exception
    //   759	763	809	java/lang/Exception
  }
  
  public static b a(Context paramContext)
  {
    if (jdField_a_of_type_ComTencentSmttBB == null) {
      try
      {
        if (jdField_a_of_type_ComTencentSmttBB == null) {
          jdField_a_of_type_ComTencentSmttBB = new b(paramContext);
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentSmttBB;
  }
  
  private void a(Context paramContext)
  {
    this.jdField_a_of_type_Int = Process.myPid();
    this.jdField_b_of_type_Int = Process.myUid();
    int j = Runtime.getRuntime().availableProcessors();
    long l1 = a(1, "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq").getLong("result");
    long l2 = a(2, "/proc/meminfo").getLong("result");
    this.e = l2;
    int k = Build.VERSION.SDK_INT;
    paramContext = Build.MODEL;
    int m = (int)((WindowManager)this.jdField_a_of_type_AndroidContentContext.getSystemService("window")).getDefaultDisplay().getRefreshRate();
    String str = ContextHolder.getInstance().getTbsCoreVersion();
    this.f = TrafficStats.getTotalRxBytes();
    this.g = TrafficStats.getTotalTxBytes();
    this.h = TrafficStats.getUidRxBytes(this.jdField_b_of_type_Int);
    this.i = TrafficStats.getUidTxBytes(this.jdField_b_of_type_Int);
    a.a();
    a.jdField_a_of_type_Int = j;
    a.a();
    a.jdField_a_of_type_Long = l1;
    a.a();
    a.jdField_b_of_type_Long = l2;
    a.a();
    a.jdField_b_of_type_JavaLangString = paramContext;
    a.a();
    a.jdField_b_of_type_Int = k;
    a.a();
    a.jdField_c_of_type_Int = m;
    a.a();
    a.jdField_c_of_type_JavaLangString = str;
  }
  
  private void b(Context paramContext)
  {
    long l1 = System.currentTimeMillis();
    int k = (int)(a(3, "/proc/stat").getDouble("result") * 100.0D);
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("/proc/");
    ((StringBuilder)localObject).append(this.jdField_a_of_type_Int);
    ((StringBuilder)localObject).append("/stat");
    int m = (int)(a(4, ((StringBuilder)localObject).toString()).getDouble("result") * 100.0D);
    long l2 = System.currentTimeMillis();
    paramContext = (ActivityManager)paramContext.getSystemService("activity");
    localObject = new ActivityManager.MemoryInfo();
    paramContext.getMemoryInfo((ActivityManager.MemoryInfo)localObject);
    double d1 = ((ActivityManager.MemoryInfo)localObject).availMem;
    double d2 = this.e;
    Double.isNaN(d1);
    Double.isNaN(d2);
    int n = (int)((1.0D - d1 / d2) * 100.0D * 100.0D);
    int i1 = this.jdField_a_of_type_Int;
    int j = 0;
    paramContext = paramContext.getProcessMemoryInfo(new int[] { i1 });
    i1 = paramContext.length;
    d1 = 0.0D;
    while (j < i1)
    {
      localObject = paramContext[j];
      if (localObject != null)
      {
        d1 = ((Debug.MemoryInfo)localObject).getTotalPss() * 1024;
        d2 = this.e;
        Double.isNaN(d1);
        Double.isNaN(d2);
        d1 /= d2;
      }
      j += 1;
    }
    j = (int)(d1 * 100.0D * 100.0D);
    long l3 = System.currentTimeMillis();
    i1 = (int)(a(5, "/sys/class/kgsl/kgsl-3d0/gpubusy").getDouble("result") * 100.0D * 100.0D);
    long l4 = System.currentTimeMillis();
    long l5 = TrafficStats.getTotalRxBytes();
    long l6 = this.f;
    long l7 = TrafficStats.getTotalTxBytes();
    long l8 = this.g;
    long l9 = TrafficStats.getUidRxBytes(this.jdField_b_of_type_Int);
    long l10 = this.h;
    long l11 = TrafficStats.getUidTxBytes(this.jdField_b_of_type_Int);
    long l12 = this.i;
    a.a();
    a.jdField_a_of_type_JavaUtilArrayList.add(Long.valueOf(l1));
    a.a();
    a.jdField_b_of_type_JavaUtilArrayList.add(Integer.valueOf(k));
    a.a();
    a.jdField_c_of_type_JavaUtilArrayList.add(Integer.valueOf(m));
    a.a();
    a.d.add(Long.valueOf(l2));
    a.a();
    a.e.add(Integer.valueOf(n));
    a.a();
    a.f.add(Integer.valueOf(j));
    a.a();
    a.g.add(Long.valueOf(l3));
    a.a();
    a.h.add(Integer.valueOf(i1));
    a.a();
    a.i.add(Integer.valueOf(i1));
    a.a();
    a.j.add(Long.valueOf(l4));
    a.a();
    a.k.add(Long.valueOf(l5 - l6));
    a.a();
    a.l.add(Long.valueOf(l7 - l8));
    a.a();
    a.m.add(Long.valueOf(l9 - l10));
    a.a();
    a.n.add(Long.valueOf(l11 - l12));
  }
  
  public void a()
  {
    if (!this.jdField_a_of_type_Boolean)
    {
      this.jdField_a_of_type_Boolean = true;
      new Thread(new a(null)).start();
    }
  }
  
  public void b()
  {
    this.jdField_a_of_type_Boolean = false;
  }
  
  private class a
    implements Runnable
  {
    private a() {}
    
    public void run()
    {
      b localb = b.this;
      b.a(localb, b.a(localb));
      if ((b.a(b.this) != null) && (b.this.a) && (Build.VERSION.SDK_INT >= 16)) {
        b.a(b.this).postFrameCallback(b.a(b.this));
      }
      while (b.this.a)
      {
        long l = System.currentTimeMillis();
        localb = b.this;
        b.b(localb, b.a(localb));
        try
        {
          l = 100L - (System.currentTimeMillis() - l);
          if (l > 0L) {
            Thread.sleep(l);
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;) {}
        }
      }
      if ((b.a(b.this) != null) && (Build.VERSION.SDK_INT >= 16)) {
        b.a(b.this).removeFrameCallback(b.a(b.this));
      }
      if (!b.this.a) {
        a.a().a();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */