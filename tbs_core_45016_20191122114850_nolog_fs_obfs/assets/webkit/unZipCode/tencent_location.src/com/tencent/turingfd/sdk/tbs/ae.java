package com.tencent.turingfd.sdk.tbs;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Process;
import android.text.TextUtils;
import java.io.File;
import java.util.List;

public class ae
{
  public static long a;
  public static final String a;
  public static final String[] a;
  public static final String b;
  public static final String c;
  public static final String d;
  public static final String e;
  
  static
  {
    jdField_a_of_type_JavaLangString = o.a(o.d);
    b = o.a(o.e);
    c = o.a(o.f);
    d = o.a(o.g);
    e = o.a(o.h);
    jdField_a_of_type_Long = 0L;
    jdField_a_of_type_ArrayOfJavaLangString = new String[] { "^/data/user/\\d+$", "^/data/data$" };
  }
  
  public static String a()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(jdField_a_of_type_Long);
    return localStringBuilder.toString();
  }
  
  /* Error */
  public static String a(Context paramContext)
  {
    // Byte code:
    //   0: new 59	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   7: astore 10
    //   9: invokestatic 83	java/lang/System:currentTimeMillis	()J
    //   12: lstore 4
    //   14: new 85	java/util/ArrayList
    //   17: dup
    //   18: invokespecial 86	java/util/ArrayList:<init>	()V
    //   21: astore 11
    //   23: aload_0
    //   24: invokevirtual 92	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   27: invokevirtual 96	android/content/Context:getFilesDir	()Ljava/io/File;
    //   30: invokevirtual 101	java/io/File:getParentFile	()Ljava/io/File;
    //   33: astore 7
    //   35: aload 7
    //   37: ifnonnull +18 -> 55
    //   40: new 6	com/tencent/turingfd/sdk/tbs/ae$a
    //   43: dup
    //   44: iconst_0
    //   45: ldc 64
    //   47: invokespecial 104	com/tencent/turingfd/sdk/tbs/ae$a:<init>	(ZLjava/lang/String;)V
    //   50: astore 7
    //   52: goto +238 -> 290
    //   55: aload 7
    //   57: invokevirtual 101	java/io/File:getParentFile	()Ljava/io/File;
    //   60: astore 8
    //   62: aload 8
    //   64: ifnonnull +18 -> 82
    //   67: new 6	com/tencent/turingfd/sdk/tbs/ae$a
    //   70: dup
    //   71: iconst_0
    //   72: ldc 64
    //   74: invokespecial 104	com/tencent/turingfd/sdk/tbs/ae$a:<init>	(ZLjava/lang/String;)V
    //   77: astore 7
    //   79: goto +211 -> 290
    //   82: getstatic 55	com/tencent/turingfd/sdk/tbs/ae:jdField_a_of_type_ArrayOfJavaLangString	[Ljava/lang/String;
    //   85: astore 9
    //   87: aload 9
    //   89: arraylength
    //   90: istore_2
    //   91: iconst_0
    //   92: istore_1
    //   93: iload_1
    //   94: iload_2
    //   95: if_icmpge +37 -> 132
    //   98: aload 9
    //   100: iload_1
    //   101: aaload
    //   102: invokestatic 110	java/util/regex/Pattern:compile	(Ljava/lang/String;)Ljava/util/regex/Pattern;
    //   105: aload 8
    //   107: invokevirtual 113	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   110: invokevirtual 117	java/util/regex/Pattern:matcher	(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   113: invokevirtual 123	java/util/regex/Matcher:find	()Z
    //   116: ifeq +9 -> 125
    //   119: iconst_0
    //   120: istore 6
    //   122: goto +13 -> 135
    //   125: iload_1
    //   126: iconst_1
    //   127: iadd
    //   128: istore_1
    //   129: goto -36 -> 93
    //   132: iconst_1
    //   133: istore 6
    //   135: aload 7
    //   137: invokevirtual 113	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   140: astore 9
    //   142: aload_0
    //   143: invokevirtual 126	android/content/Context:getPackageName	()Ljava/lang/String;
    //   146: astore 8
    //   148: aload 9
    //   150: ldc -128
    //   152: bipush 6
    //   154: invokevirtual 132	java/lang/String:split	(Ljava/lang/String;I)[Ljava/lang/String;
    //   157: astore 12
    //   159: aload 9
    //   161: ldc -122
    //   163: invokevirtual 138	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   166: ifeq +29 -> 195
    //   169: aload 12
    //   171: arraylength
    //   172: iconst_4
    //   173: if_icmplt +22 -> 195
    //   176: aload 12
    //   178: iconst_3
    //   179: aaload
    //   180: invokestatic 144	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   183: ifne +12 -> 195
    //   186: aload 12
    //   188: iconst_3
    //   189: aaload
    //   190: astore 7
    //   192: goto +48 -> 240
    //   195: aload 8
    //   197: astore 7
    //   199: aload 9
    //   201: ldc -110
    //   203: invokevirtual 138	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   206: ifeq +34 -> 240
    //   209: aload 8
    //   211: astore 7
    //   213: aload 12
    //   215: arraylength
    //   216: iconst_5
    //   217: if_icmplt +23 -> 240
    //   220: aload 8
    //   222: astore 7
    //   224: aload 12
    //   226: iconst_4
    //   227: aaload
    //   228: invokestatic 144	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   231: ifne +9 -> 240
    //   234: aload 12
    //   236: iconst_4
    //   237: aaload
    //   238: astore 7
    //   240: iload 6
    //   242: ifeq +35 -> 277
    //   245: aload 7
    //   247: aload_0
    //   248: invokevirtual 126	android/content/Context:getPackageName	()Ljava/lang/String;
    //   251: invokevirtual 150	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   254: ifne +23 -> 277
    //   257: new 6	com/tencent/turingfd/sdk/tbs/ae$a
    //   260: dup
    //   261: iload 6
    //   263: aload_0
    //   264: aload 7
    //   266: invokestatic 153	com/tencent/turingfd/sdk/tbs/ae:a	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   269: invokespecial 104	com/tencent/turingfd/sdk/tbs/ae$a:<init>	(ZLjava/lang/String;)V
    //   272: astore 7
    //   274: goto +16 -> 290
    //   277: new 6	com/tencent/turingfd/sdk/tbs/ae$a
    //   280: dup
    //   281: iload 6
    //   283: ldc 64
    //   285: invokespecial 104	com/tencent/turingfd/sdk/tbs/ae$a:<init>	(ZLjava/lang/String;)V
    //   288: astore 7
    //   290: aload 7
    //   292: getfield 156	com/tencent/turingfd/sdk/tbs/ae$a:jdField_a_of_type_Boolean	Z
    //   295: ifeq +88 -> 383
    //   298: aload 7
    //   300: getfield 157	com/tencent/turingfd/sdk/tbs/ae$a:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   303: invokestatic 144	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   306: ifne +77 -> 383
    //   309: iconst_0
    //   310: iconst_1
    //   311: iconst_0
    //   312: invokestatic 162	com/tencent/turingfd/sdk/tbs/l:a	(IZI)I
    //   315: istore_1
    //   316: new 164	com/tencent/turingfd/sdk/tbs/w
    //   319: dup
    //   320: invokespecial 165	com/tencent/turingfd/sdk/tbs/w:<init>	()V
    //   323: astore 8
    //   325: new 59	java/lang/StringBuilder
    //   328: dup
    //   329: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   332: astore 9
    //   334: aload 9
    //   336: getstatic 26	com/tencent/turingfd/sdk/tbs/ae:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   339: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   342: pop
    //   343: aload 9
    //   345: getstatic 35	com/tencent/turingfd/sdk/tbs/ae:c	Ljava/lang/String;
    //   348: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   351: pop
    //   352: aload 8
    //   354: aload 9
    //   356: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   359: putfield 166	com/tencent/turingfd/sdk/tbs/w:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   362: aload 8
    //   364: aload 7
    //   366: getfield 157	com/tencent/turingfd/sdk/tbs/ae$a:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   369: putfield 167	com/tencent/turingfd/sdk/tbs/w:b	Ljava/lang/String;
    //   372: aload 11
    //   374: aload 8
    //   376: invokevirtual 170	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   379: pop
    //   380: goto +10 -> 390
    //   383: iconst_0
    //   384: iconst_0
    //   385: iconst_0
    //   386: invokestatic 162	com/tencent/turingfd/sdk/tbs/l:a	(IZI)I
    //   389: istore_1
    //   390: aload_0
    //   391: invokevirtual 92	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   394: astore 12
    //   396: aload 12
    //   398: ifnonnull +18 -> 416
    //   401: new 6	com/tencent/turingfd/sdk/tbs/ae$a
    //   404: dup
    //   405: iconst_0
    //   406: ldc 64
    //   408: invokespecial 104	com/tencent/turingfd/sdk/tbs/ae$a:<init>	(ZLjava/lang/String;)V
    //   411: astore 7
    //   413: goto +514 -> 927
    //   416: new 172	java/io/FileReader
    //   419: dup
    //   420: ldc -82
    //   422: invokespecial 177	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   425: astore 8
    //   427: new 179	java/io/BufferedReader
    //   430: dup
    //   431: aload 8
    //   433: invokespecial 182	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   436: astore 9
    //   438: aload 9
    //   440: invokevirtual 185	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   443: astore 7
    //   445: aload 7
    //   447: ifnull +240 -> 687
    //   450: aload 7
    //   452: bipush 47
    //   454: invokevirtual 189	java/lang/String:indexOf	(I)I
    //   457: istore_2
    //   458: iload_2
    //   459: iconst_m1
    //   460: if_icmpne +6 -> 466
    //   463: goto -25 -> 438
    //   466: aload 7
    //   468: iload_2
    //   469: invokevirtual 193	java/lang/String:substring	(I)Ljava/lang/String;
    //   472: invokevirtual 196	java/lang/String:trim	()Ljava/lang/String;
    //   475: astore 7
    //   477: getstatic 202	android/os/Build$VERSION:SDK_INT	I
    //   480: istore_2
    //   481: iload_2
    //   482: bipush 23
    //   484: if_icmpge +140 -> 624
    //   487: aload 7
    //   489: ldc -52
    //   491: invokevirtual 138	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   494: istore 6
    //   496: iload 6
    //   498: ifne +6 -> 504
    //   501: goto -63 -> 438
    //   504: aload 7
    //   506: ldc -50
    //   508: invokevirtual 209	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   511: ifne +6 -> 517
    //   514: goto -76 -> 438
    //   517: aload 7
    //   519: bipush 47
    //   521: invokevirtual 212	java/lang/String:lastIndexOf	(I)I
    //   524: istore_2
    //   525: iload_2
    //   526: iconst_m1
    //   527: if_icmpne +6 -> 533
    //   530: goto -92 -> 438
    //   533: aload 7
    //   535: iload_2
    //   536: aload 7
    //   538: invokevirtual 216	java/lang/String:length	()I
    //   541: bipush 12
    //   543: isub
    //   544: invokevirtual 219	java/lang/String:substring	(II)Ljava/lang/String;
    //   547: bipush 64
    //   549: bipush 47
    //   551: invokevirtual 223	java/lang/String:replace	(CC)Ljava/lang/String;
    //   554: astore 7
    //   556: aload 7
    //   558: ldc -31
    //   560: invokevirtual 138	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   563: ifne +6 -> 569
    //   566: goto -128 -> 438
    //   569: new 98	java/io/File
    //   572: dup
    //   573: aload 7
    //   575: invokespecial 226	java/io/File:<init>	(Ljava/lang/String;)V
    //   578: astore 7
    //   580: ldc -28
    //   582: aload 7
    //   584: invokevirtual 231	java/io/File:getName	()Ljava/lang/String;
    //   587: invokevirtual 150	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   590: ifeq +24 -> 614
    //   593: aload 7
    //   595: invokevirtual 101	java/io/File:getParentFile	()Ljava/io/File;
    //   598: ifnull +89 -> 687
    //   601: aload 7
    //   603: invokevirtual 101	java/io/File:getParentFile	()Ljava/io/File;
    //   606: invokevirtual 231	java/io/File:getName	()Ljava/lang/String;
    //   609: astore 7
    //   611: goto +79 -> 690
    //   614: aload 7
    //   616: invokevirtual 231	java/io/File:getName	()Ljava/lang/String;
    //   619: astore 7
    //   621: goto +69 -> 690
    //   624: aload 7
    //   626: ldc -31
    //   628: invokevirtual 138	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   631: istore 6
    //   633: iload 6
    //   635: ifne +6 -> 641
    //   638: goto -200 -> 438
    //   641: aload 7
    //   643: ldc -23
    //   645: invokevirtual 209	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   648: istore 6
    //   650: iload 6
    //   652: ifne +6 -> 658
    //   655: goto -217 -> 438
    //   658: aload 7
    //   660: ldc -128
    //   662: invokevirtual 236	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   665: astore 7
    //   667: aload 7
    //   669: arraylength
    //   670: bipush 7
    //   672: if_icmplt +12 -> 684
    //   675: aload 7
    //   677: iconst_3
    //   678: aaload
    //   679: astore 7
    //   681: goto +9 -> 690
    //   684: goto -246 -> 438
    //   687: aconst_null
    //   688: astore 7
    //   690: aload 8
    //   692: invokestatic 239	com/tencent/turingfd/sdk/tbs/l:a	(Ljava/io/Closeable;)V
    //   695: aload 9
    //   697: invokestatic 239	com/tencent/turingfd/sdk/tbs/l:a	(Ljava/io/Closeable;)V
    //   700: goto +55 -> 755
    //   703: astore_0
    //   704: goto +20 -> 724
    //   707: astore_0
    //   708: goto +13 -> 721
    //   711: aconst_null
    //   712: astore 7
    //   714: goto +28 -> 742
    //   717: astore_0
    //   718: aconst_null
    //   719: astore 8
    //   721: aconst_null
    //   722: astore 9
    //   724: aload 8
    //   726: invokestatic 239	com/tencent/turingfd/sdk/tbs/l:a	(Ljava/io/Closeable;)V
    //   729: aload 9
    //   731: invokestatic 239	com/tencent/turingfd/sdk/tbs/l:a	(Ljava/io/Closeable;)V
    //   734: aload_0
    //   735: athrow
    //   736: aconst_null
    //   737: astore 8
    //   739: aconst_null
    //   740: astore 7
    //   742: aload 8
    //   744: invokestatic 239	com/tencent/turingfd/sdk/tbs/l:a	(Ljava/io/Closeable;)V
    //   747: aload 7
    //   749: invokestatic 239	com/tencent/turingfd/sdk/tbs/l:a	(Ljava/io/Closeable;)V
    //   752: aconst_null
    //   753: astore 7
    //   755: aload 12
    //   757: invokevirtual 126	android/content/Context:getPackageName	()Ljava/lang/String;
    //   760: astore 8
    //   762: aload 7
    //   764: invokestatic 144	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   767: ifne +115 -> 882
    //   770: aload 7
    //   772: ldc -15
    //   774: invokevirtual 244	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   777: istore_2
    //   778: iload_2
    //   779: iconst_m1
    //   780: if_icmpne +6 -> 786
    //   783: goto +99 -> 882
    //   786: aload 7
    //   788: iconst_0
    //   789: iload_2
    //   790: invokevirtual 219	java/lang/String:substring	(II)Ljava/lang/String;
    //   793: astore 7
    //   795: aload 7
    //   797: invokestatic 144	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   800: ifeq +6 -> 806
    //   803: goto +79 -> 882
    //   806: new 59	java/lang/StringBuilder
    //   809: dup
    //   810: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   813: astore 9
    //   815: aload 9
    //   817: ldc -122
    //   819: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   822: pop
    //   823: aload 9
    //   825: aload 7
    //   827: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   830: pop
    //   831: new 98	java/io/File
    //   834: dup
    //   835: aload 9
    //   837: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   840: invokespecial 226	java/io/File:<init>	(Ljava/lang/String;)V
    //   843: astore 9
    //   845: aload 9
    //   847: invokevirtual 247	java/io/File:exists	()Z
    //   850: ifeq +14 -> 864
    //   853: aload 9
    //   855: invokevirtual 250	java/io/File:canWrite	()Z
    //   858: ifeq +6 -> 864
    //   861: goto +7 -> 868
    //   864: aload 8
    //   866: astore 7
    //   868: aload 8
    //   870: aload 7
    //   872: invokestatic 253	android/text/TextUtils:equals	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   875: iconst_1
    //   876: ixor
    //   877: istore 6
    //   879: goto +10 -> 889
    //   882: iconst_0
    //   883: istore 6
    //   885: aload 8
    //   887: astore 7
    //   889: iload 6
    //   891: ifeq +23 -> 914
    //   894: new 6	com/tencent/turingfd/sdk/tbs/ae$a
    //   897: dup
    //   898: iload 6
    //   900: aload_0
    //   901: aload 7
    //   903: invokestatic 153	com/tencent/turingfd/sdk/tbs/ae:a	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   906: invokespecial 104	com/tencent/turingfd/sdk/tbs/ae$a:<init>	(ZLjava/lang/String;)V
    //   909: astore 7
    //   911: goto +16 -> 927
    //   914: new 6	com/tencent/turingfd/sdk/tbs/ae$a
    //   917: dup
    //   918: iload 6
    //   920: ldc 64
    //   922: invokespecial 104	com/tencent/turingfd/sdk/tbs/ae$a:<init>	(ZLjava/lang/String;)V
    //   925: astore 7
    //   927: iload_1
    //   928: aload 7
    //   930: getfield 156	com/tencent/turingfd/sdk/tbs/ae$a:jdField_a_of_type_Boolean	Z
    //   933: iconst_1
    //   934: invokestatic 162	com/tencent/turingfd/sdk/tbs/l:a	(IZI)I
    //   937: istore_1
    //   938: aload 7
    //   940: getfield 156	com/tencent/turingfd/sdk/tbs/ae$a:jdField_a_of_type_Boolean	Z
    //   943: ifeq +67 -> 1010
    //   946: new 164	com/tencent/turingfd/sdk/tbs/w
    //   949: dup
    //   950: invokespecial 165	com/tencent/turingfd/sdk/tbs/w:<init>	()V
    //   953: astore 8
    //   955: new 59	java/lang/StringBuilder
    //   958: dup
    //   959: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   962: astore 9
    //   964: aload 9
    //   966: getstatic 26	com/tencent/turingfd/sdk/tbs/ae:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   969: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   972: pop
    //   973: aload 9
    //   975: getstatic 40	com/tencent/turingfd/sdk/tbs/ae:d	Ljava/lang/String;
    //   978: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   981: pop
    //   982: aload 8
    //   984: aload 9
    //   986: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   989: putfield 166	com/tencent/turingfd/sdk/tbs/w:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   992: aload 8
    //   994: aload 7
    //   996: getfield 157	com/tencent/turingfd/sdk/tbs/ae$a:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   999: putfield 167	com/tencent/turingfd/sdk/tbs/w:b	Ljava/lang/String;
    //   1002: aload 11
    //   1004: aload 8
    //   1006: invokevirtual 170	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   1009: pop
    //   1010: getstatic 202	android/os/Build$VERSION:SDK_INT	I
    //   1013: bipush 17
    //   1015: if_icmpge +18 -> 1033
    //   1018: new 6	com/tencent/turingfd/sdk/tbs/ae$a
    //   1021: dup
    //   1022: iconst_0
    //   1023: ldc 64
    //   1025: invokespecial 104	com/tencent/turingfd/sdk/tbs/ae$a:<init>	(ZLjava/lang/String;)V
    //   1028: astore 7
    //   1030: goto +382 -> 1412
    //   1033: new 59	java/lang/StringBuilder
    //   1036: dup
    //   1037: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   1040: astore 8
    //   1042: ldc -1
    //   1044: getstatic 260	android/os/Build:BRAND	Ljava/lang/String;
    //   1047: invokevirtual 263	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1050: ifeq +160 -> 1210
    //   1053: ldc_w 265
    //   1056: invokestatic 271	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   1059: astore 7
    //   1061: aload_0
    //   1062: invokevirtual 275	java/lang/Object:getClass	()Ljava/lang/Class;
    //   1065: astore 9
    //   1067: aload 9
    //   1069: ldc_w 277
    //   1072: invokevirtual 281	java/lang/Class:getField	(Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   1075: astore 9
    //   1077: aload 9
    //   1079: iconst_1
    //   1080: invokevirtual 287	java/lang/reflect/Field:setAccessible	(Z)V
    //   1083: aload_0
    //   1084: aload 9
    //   1086: aload_0
    //   1087: invokevirtual 291	java/lang/reflect/Field:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   1090: checkcast 49	java/lang/String
    //   1093: invokevirtual 295	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   1096: astore 9
    //   1098: aload 9
    //   1100: ifnonnull +6 -> 1106
    //   1103: goto +50 -> 1153
    //   1106: aload 7
    //   1108: ldc_w 297
    //   1111: iconst_0
    //   1112: anewarray 267	java/lang/Class
    //   1115: invokevirtual 301	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   1118: astore 7
    //   1120: aload 7
    //   1122: ifnonnull +6 -> 1128
    //   1125: goto +28 -> 1153
    //   1128: aload 7
    //   1130: iconst_1
    //   1131: invokevirtual 304	java/lang/reflect/Method:setAccessible	(Z)V
    //   1134: aload 7
    //   1136: aload 9
    //   1138: iconst_0
    //   1139: anewarray 4	java/lang/Object
    //   1142: invokevirtual 308	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   1145: checkcast 49	java/lang/String
    //   1148: astore 7
    //   1150: goto +7 -> 1157
    //   1153: ldc 64
    //   1155: astore 7
    //   1157: new 49	java/lang/String
    //   1160: dup
    //   1161: ldc_w 310
    //   1164: iconst_0
    //   1165: invokestatic 316	android/util/Base64:decode	(Ljava/lang/String;I)[B
    //   1168: invokespecial 319	java/lang/String:<init>	([B)V
    //   1171: astore 9
    //   1173: aload 7
    //   1175: invokestatic 144	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   1178: ifne +215 -> 1393
    //   1181: aload 9
    //   1183: aload 7
    //   1185: invokevirtual 150	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1188: istore 6
    //   1190: iload 6
    //   1192: ifeq +201 -> 1393
    //   1195: aload 8
    //   1197: ldc_w 321
    //   1200: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1203: pop
    //   1204: iconst_1
    //   1205: istore 6
    //   1207: goto +189 -> 1396
    //   1210: ldc_w 323
    //   1213: getstatic 260	android/os/Build:BRAND	Ljava/lang/String;
    //   1216: invokevirtual 263	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1219: ifeq +24 -> 1243
    //   1222: invokestatic 325	com/tencent/turingfd/sdk/tbs/ae:a	()Z
    //   1225: ifeq +168 -> 1393
    //   1228: aload 8
    //   1230: ldc_w 327
    //   1233: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1236: pop
    //   1237: iconst_1
    //   1238: istore 6
    //   1240: goto +156 -> 1396
    //   1243: ldc_w 329
    //   1246: getstatic 260	android/os/Build:BRAND	Ljava/lang/String;
    //   1249: invokevirtual 263	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1252: ifeq +24 -> 1276
    //   1255: invokestatic 325	com/tencent/turingfd/sdk/tbs/ae:a	()Z
    //   1258: ifeq +135 -> 1393
    //   1261: aload 8
    //   1263: ldc_w 331
    //   1266: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1269: pop
    //   1270: iconst_1
    //   1271: istore 6
    //   1273: goto +123 -> 1396
    //   1276: ldc_w 333
    //   1279: getstatic 260	android/os/Build:BRAND	Ljava/lang/String;
    //   1282: invokevirtual 263	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1285: ifeq +108 -> 1393
    //   1288: invokestatic 325	com/tencent/turingfd/sdk/tbs/ae:a	()Z
    //   1291: ifeq +18 -> 1309
    //   1294: aload 8
    //   1296: ldc_w 335
    //   1299: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1302: pop
    //   1303: iconst_1
    //   1304: istore 6
    //   1306: goto +90 -> 1396
    //   1309: new 49	java/lang/String
    //   1312: dup
    //   1313: ldc_w 337
    //   1316: invokestatic 340	com/tencent/turingfd/sdk/tbs/l:a	(Ljava/lang/String;)[B
    //   1319: invokespecial 319	java/lang/String:<init>	([B)V
    //   1322: astore 7
    //   1324: new 59	java/lang/StringBuilder
    //   1327: dup
    //   1328: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   1331: astore 9
    //   1333: aload 9
    //   1335: aload_0
    //   1336: invokevirtual 92	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   1339: invokevirtual 126	android/content/Context:getPackageName	()Ljava/lang/String;
    //   1342: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1345: pop
    //   1346: aload 9
    //   1348: ldc_w 342
    //   1351: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1354: pop
    //   1355: aload 7
    //   1357: aload 9
    //   1359: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1362: invokevirtual 345	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   1365: istore 6
    //   1367: iload 6
    //   1369: ifeq +24 -> 1393
    //   1372: aload 8
    //   1374: ldc_w 347
    //   1377: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1380: pop
    //   1381: iconst_1
    //   1382: istore 6
    //   1384: goto +12 -> 1396
    //   1387: iconst_0
    //   1388: istore 6
    //   1390: goto +6 -> 1396
    //   1393: iconst_0
    //   1394: istore 6
    //   1396: new 6	com/tencent/turingfd/sdk/tbs/ae$a
    //   1399: dup
    //   1400: iload 6
    //   1402: aload 8
    //   1404: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1407: invokespecial 104	com/tencent/turingfd/sdk/tbs/ae$a:<init>	(ZLjava/lang/String;)V
    //   1410: astore 7
    //   1412: iload_1
    //   1413: aload 7
    //   1415: getfield 156	com/tencent/turingfd/sdk/tbs/ae$a:jdField_a_of_type_Boolean	Z
    //   1418: iconst_2
    //   1419: invokestatic 162	com/tencent/turingfd/sdk/tbs/l:a	(IZI)I
    //   1422: istore_2
    //   1423: aload 7
    //   1425: getfield 156	com/tencent/turingfd/sdk/tbs/ae$a:jdField_a_of_type_Boolean	Z
    //   1428: ifeq +67 -> 1495
    //   1431: new 164	com/tencent/turingfd/sdk/tbs/w
    //   1434: dup
    //   1435: invokespecial 165	com/tencent/turingfd/sdk/tbs/w:<init>	()V
    //   1438: astore 8
    //   1440: new 59	java/lang/StringBuilder
    //   1443: dup
    //   1444: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   1447: astore 9
    //   1449: aload 9
    //   1451: getstatic 26	com/tencent/turingfd/sdk/tbs/ae:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1454: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1457: pop
    //   1458: aload 9
    //   1460: getstatic 45	com/tencent/turingfd/sdk/tbs/ae:e	Ljava/lang/String;
    //   1463: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1466: pop
    //   1467: aload 8
    //   1469: aload 9
    //   1471: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1474: putfield 166	com/tencent/turingfd/sdk/tbs/w:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1477: aload 8
    //   1479: aload 7
    //   1481: getfield 157	com/tencent/turingfd/sdk/tbs/ae$a:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1484: putfield 167	com/tencent/turingfd/sdk/tbs/w:b	Ljava/lang/String;
    //   1487: aload 11
    //   1489: aload 8
    //   1491: invokevirtual 170	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   1494: pop
    //   1495: invokestatic 352	android/os/Process:myUid	()I
    //   1498: istore_1
    //   1499: iload_1
    //   1500: sipush 10000
    //   1503: if_icmpge +17 -> 1520
    //   1506: new 6	com/tencent/turingfd/sdk/tbs/ae$a
    //   1509: dup
    //   1510: iconst_0
    //   1511: ldc 64
    //   1513: invokespecial 104	com/tencent/turingfd/sdk/tbs/ae$a:<init>	(ZLjava/lang/String;)V
    //   1516: astore_0
    //   1517: goto +92 -> 1609
    //   1520: iload_1
    //   1521: invokestatic 357	com/tencent/turingfd/sdk/tbs/s:a	(I)[Ljava/lang/String;
    //   1524: astore 8
    //   1526: aload_0
    //   1527: invokevirtual 126	android/content/Context:getPackageName	()Ljava/lang/String;
    //   1530: astore 9
    //   1532: aload 8
    //   1534: arraylength
    //   1535: istore_3
    //   1536: iconst_0
    //   1537: istore_1
    //   1538: iload_1
    //   1539: iload_3
    //   1540: if_icmpge +29 -> 1569
    //   1543: aload 8
    //   1545: iload_1
    //   1546: aaload
    //   1547: astore 7
    //   1549: aload 7
    //   1551: aload 9
    //   1553: invokevirtual 150	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1556: ifne +6 -> 1562
    //   1559: goto +13 -> 1572
    //   1562: iload_1
    //   1563: iconst_1
    //   1564: iadd
    //   1565: istore_1
    //   1566: goto -28 -> 1538
    //   1569: aconst_null
    //   1570: astore 7
    //   1572: aload 7
    //   1574: invokestatic 144	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   1577: ifne +21 -> 1598
    //   1580: new 6	com/tencent/turingfd/sdk/tbs/ae$a
    //   1583: dup
    //   1584: iconst_1
    //   1585: aload_0
    //   1586: aload 7
    //   1588: invokestatic 153	com/tencent/turingfd/sdk/tbs/ae:a	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   1591: invokespecial 104	com/tencent/turingfd/sdk/tbs/ae$a:<init>	(ZLjava/lang/String;)V
    //   1594: astore_0
    //   1595: goto +14 -> 1609
    //   1598: new 6	com/tencent/turingfd/sdk/tbs/ae$a
    //   1601: dup
    //   1602: iconst_0
    //   1603: ldc 64
    //   1605: invokespecial 104	com/tencent/turingfd/sdk/tbs/ae$a:<init>	(ZLjava/lang/String;)V
    //   1608: astore_0
    //   1609: iload_2
    //   1610: aload_0
    //   1611: getfield 156	com/tencent/turingfd/sdk/tbs/ae$a:jdField_a_of_type_Boolean	Z
    //   1614: iconst_3
    //   1615: invokestatic 162	com/tencent/turingfd/sdk/tbs/l:a	(IZI)I
    //   1618: istore_1
    //   1619: aload_0
    //   1620: getfield 156	com/tencent/turingfd/sdk/tbs/ae$a:jdField_a_of_type_Boolean	Z
    //   1623: ifeq +66 -> 1689
    //   1626: new 164	com/tencent/turingfd/sdk/tbs/w
    //   1629: dup
    //   1630: invokespecial 165	com/tencent/turingfd/sdk/tbs/w:<init>	()V
    //   1633: astore 7
    //   1635: new 59	java/lang/StringBuilder
    //   1638: dup
    //   1639: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   1642: astore 8
    //   1644: aload 8
    //   1646: getstatic 26	com/tencent/turingfd/sdk/tbs/ae:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1649: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1652: pop
    //   1653: aload 8
    //   1655: ldc_w 359
    //   1658: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1661: pop
    //   1662: aload 7
    //   1664: aload 8
    //   1666: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1669: putfield 166	com/tencent/turingfd/sdk/tbs/w:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1672: aload 7
    //   1674: aload_0
    //   1675: getfield 157	com/tencent/turingfd/sdk/tbs/ae$a:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1678: putfield 167	com/tencent/turingfd/sdk/tbs/w:b	Ljava/lang/String;
    //   1681: aload 11
    //   1683: aload 7
    //   1685: invokevirtual 170	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   1688: pop
    //   1689: iload_1
    //   1690: ifle +87 -> 1777
    //   1693: new 164	com/tencent/turingfd/sdk/tbs/w
    //   1696: dup
    //   1697: invokespecial 165	com/tencent/turingfd/sdk/tbs/w:<init>	()V
    //   1700: astore_0
    //   1701: new 59	java/lang/StringBuilder
    //   1704: dup
    //   1705: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   1708: astore 7
    //   1710: aload 7
    //   1712: getstatic 26	com/tencent/turingfd/sdk/tbs/ae:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1715: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1718: pop
    //   1719: aload 7
    //   1721: getstatic 30	com/tencent/turingfd/sdk/tbs/ae:b	Ljava/lang/String;
    //   1724: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1727: pop
    //   1728: aload_0
    //   1729: aload 7
    //   1731: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1734: putfield 166	com/tencent/turingfd/sdk/tbs/w:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1737: new 59	java/lang/StringBuilder
    //   1740: dup
    //   1741: invokespecial 62	java/lang/StringBuilder:<init>	()V
    //   1744: astore 7
    //   1746: aload 7
    //   1748: ldc 64
    //   1750: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1753: pop
    //   1754: aload 7
    //   1756: iload_1
    //   1757: invokevirtual 362	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1760: pop
    //   1761: aload_0
    //   1762: aload 7
    //   1764: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1767: putfield 167	com/tencent/turingfd/sdk/tbs/w:b	Ljava/lang/String;
    //   1770: aload 11
    //   1772: aload_0
    //   1773: invokevirtual 170	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   1776: pop
    //   1777: invokestatic 83	java/lang/System:currentTimeMillis	()J
    //   1780: lload 4
    //   1782: lsub
    //   1783: putstatic 47	com/tencent/turingfd/sdk/tbs/ae:jdField_a_of_type_Long	J
    //   1786: aload 11
    //   1788: invokevirtual 366	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   1791: astore_0
    //   1792: aload_0
    //   1793: invokeinterface 371 1 0
    //   1798: ifeq +56 -> 1854
    //   1801: aload_0
    //   1802: invokeinterface 375 1 0
    //   1807: checkcast 164	com/tencent/turingfd/sdk/tbs/w
    //   1810: astore 7
    //   1812: aload 10
    //   1814: aload 7
    //   1816: getfield 166	com/tencent/turingfd/sdk/tbs/w:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1819: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1822: pop
    //   1823: aload 10
    //   1825: bipush 58
    //   1827: invokevirtual 378	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   1830: pop
    //   1831: aload 10
    //   1833: aload 7
    //   1835: getfield 167	com/tencent/turingfd/sdk/tbs/w:b	Ljava/lang/String;
    //   1838: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1841: pop
    //   1842: aload 10
    //   1844: ldc_w 380
    //   1847: invokevirtual 68	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1850: pop
    //   1851: goto -59 -> 1792
    //   1854: aload 10
    //   1856: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1859: areturn
    //   1860: astore 7
    //   1862: goto -1126 -> 736
    //   1865: astore 7
    //   1867: goto -1156 -> 711
    //   1870: astore 7
    //   1872: aload 9
    //   1874: astore 7
    //   1876: goto -1134 -> 742
    //   1879: astore 7
    //   1881: goto -494 -> 1387
    //   1884: astore 7
    //   1886: goto -733 -> 1153
    //   1889: astore 7
    //   1891: goto -687 -> 1204
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1894	0	paramContext	Context
    //   92	1665	1	i	int
    //   90	1520	2	j	int
    //   1535	6	3	k	int
    //   12	1769	4	l	long
    //   120	1281	6	bool	boolean
    //   33	1801	7	localObject1	Object
    //   1860	1	7	localThrowable1	Throwable
    //   1865	1	7	localThrowable2	Throwable
    //   1870	1	7	localThrowable3	Throwable
    //   1874	1	7	localObject2	Object
    //   1879	1	7	localThrowable4	Throwable
    //   1884	1	7	localThrowable5	Throwable
    //   1889	1	7	localThrowable6	Throwable
    //   60	1605	8	localObject3	Object
    //   85	1788	9	localObject4	Object
    //   7	1848	10	localStringBuilder	StringBuilder
    //   21	1766	11	localArrayList	java.util.ArrayList
    //   157	599	12	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   438	445	703	finally
    //   450	458	703	finally
    //   466	481	703	finally
    //   487	496	703	finally
    //   504	514	703	finally
    //   517	525	703	finally
    //   533	556	703	finally
    //   556	566	703	finally
    //   569	580	703	finally
    //   580	611	703	finally
    //   614	621	703	finally
    //   624	633	703	finally
    //   641	650	703	finally
    //   658	675	703	finally
    //   427	438	707	finally
    //   416	427	717	finally
    //   416	427	1860	java/lang/Throwable
    //   427	438	1865	java/lang/Throwable
    //   438	445	1870	java/lang/Throwable
    //   450	458	1870	java/lang/Throwable
    //   466	481	1870	java/lang/Throwable
    //   487	496	1870	java/lang/Throwable
    //   504	514	1870	java/lang/Throwable
    //   517	525	1870	java/lang/Throwable
    //   533	556	1870	java/lang/Throwable
    //   556	566	1870	java/lang/Throwable
    //   569	580	1870	java/lang/Throwable
    //   580	611	1870	java/lang/Throwable
    //   614	621	1870	java/lang/Throwable
    //   624	633	1870	java/lang/Throwable
    //   641	650	1870	java/lang/Throwable
    //   658	675	1870	java/lang/Throwable
    //   1157	1190	1879	java/lang/Throwable
    //   1309	1346	1879	java/lang/Throwable
    //   1346	1367	1879	java/lang/Throwable
    //   1053	1067	1884	java/lang/Throwable
    //   1067	1098	1884	java/lang/Throwable
    //   1106	1120	1884	java/lang/Throwable
    //   1128	1150	1884	java/lang/Throwable
    //   1195	1204	1889	java/lang/Throwable
    //   1372	1381	1889	java/lang/Throwable
  }
  
  public static String a(Context paramContext, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("_");
    paramContext = paramContext.getPackageManager();
    try
    {
      paramContext = paramContext.getApplicationInfo(paramString, 0).sourceDir;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      for (;;) {}
    }
    paramContext = "";
    try
    {
      localStringBuilder.append((String)l.a(new File(paramContext)).get(0));
    }
    catch (Throwable paramString)
    {
      long l2;
      long l1;
      for (;;) {}
    }
    localStringBuilder.append("");
    localStringBuilder.append("_");
    l2 = -1L;
    l1 = l2;
    if (!TextUtils.isEmpty(paramContext))
    {
      paramContext = new File(paramContext);
      l1 = l2;
      if (paramContext.exists()) {
        l1 = paramContext.length();
      }
    }
    localStringBuilder.append(l1);
    localStringBuilder.append("_");
    localStringBuilder.append(Process.myUid());
    return localStringBuilder.toString();
  }
  
  public static boolean a()
  {
    try
    {
      int i = Process.myUid() / 100000;
      if (999 == i) {
        return true;
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    return false;
  }
  
  public static class a
  {
    public final String a;
    public final boolean a;
    
    public a(boolean paramBoolean, String paramString)
    {
      this.jdField_a_of_type_Boolean = paramBoolean;
      this.jdField_a_of_type_JavaLangString = paramString;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\ae.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */