package com.tencent.tbs.common.logupload;

public class ZipHelper
{
  private static final int BUFFER = 2048;
  private final String[] files;
  private final String zipFile;
  
  public ZipHelper(String[] paramArrayOfString, String paramString)
  {
    this.files = paramArrayOfString;
    this.zipFile = paramString;
  }
  
  /* Error */
  private static void replaceWrongZipByte(java.io.File paramFile)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore_3
    //   5: new 29	java/io/RandomAccessFile
    //   8: dup
    //   9: aload_0
    //   10: ldc 31
    //   12: invokespecial 34	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   15: astore_0
    //   16: ldc 36
    //   18: iconst_2
    //   19: invokestatic 42	java/lang/Integer:parseInt	(Ljava/lang/String;I)I
    //   22: istore_1
    //   23: aload_0
    //   24: ldc2_w 43
    //   27: invokevirtual 48	java/io/RandomAccessFile:seek	(J)V
    //   30: aload_0
    //   31: invokevirtual 52	java/io/RandomAccessFile:read	()I
    //   34: istore_2
    //   35: iload_2
    //   36: iload_1
    //   37: iand
    //   38: ifle +23 -> 61
    //   41: aload_0
    //   42: ldc2_w 43
    //   45: invokevirtual 48	java/io/RandomAccessFile:seek	(J)V
    //   48: aload_0
    //   49: iload_1
    //   50: iconst_m1
    //   51: ixor
    //   52: sipush 255
    //   55: iand
    //   56: iload_2
    //   57: iand
    //   58: invokevirtual 56	java/io/RandomAccessFile:write	(I)V
    //   61: aload_0
    //   62: invokevirtual 59	java/io/RandomAccessFile:close	()V
    //   65: return
    //   66: astore_3
    //   67: goto +45 -> 112
    //   70: astore 4
    //   72: goto +18 -> 90
    //   75: astore 4
    //   77: aload_3
    //   78: astore_0
    //   79: aload 4
    //   81: astore_3
    //   82: goto +30 -> 112
    //   85: astore 4
    //   87: aload 5
    //   89: astore_0
    //   90: aload_0
    //   91: astore_3
    //   92: aload 4
    //   94: invokevirtual 62	java/lang/Exception:printStackTrace	()V
    //   97: aload_0
    //   98: ifnull +13 -> 111
    //   101: aload_0
    //   102: invokevirtual 59	java/io/RandomAccessFile:close	()V
    //   105: return
    //   106: astore_0
    //   107: aload_0
    //   108: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   111: return
    //   112: aload_0
    //   113: ifnull +15 -> 128
    //   116: aload_0
    //   117: invokevirtual 59	java/io/RandomAccessFile:close	()V
    //   120: goto +8 -> 128
    //   123: astore_0
    //   124: aload_0
    //   125: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   128: aload_3
    //   129: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	130	0	paramFile	java.io.File
    //   22	30	1	i	int
    //   34	24	2	j	int
    //   4	1	3	localObject1	Object
    //   66	12	3	localObject2	Object
    //   81	48	3	localObject3	Object
    //   70	1	4	localException1	Exception
    //   75	5	4	localObject4	Object
    //   85	8	4	localException2	Exception
    //   1	87	5	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   16	35	66	finally
    //   41	61	66	finally
    //   16	35	70	java/lang/Exception
    //   41	61	70	java/lang/Exception
    //   5	16	75	finally
    //   92	97	75	finally
    //   5	16	85	java/lang/Exception
    //   61	65	106	java/io/IOException
    //   101	105	106	java/io/IOException
    //   116	120	123	java/io/IOException
  }
  
  /* Error */
  public void Zip()
  {
    // Byte code:
    //   0: new 67	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: getfield 20	com/tencent/tbs/common/logupload/ZipHelper:zipFile	Ljava/lang/String;
    //   8: invokespecial 70	java/io/File:<init>	(Ljava/lang/String;)V
    //   11: astore 4
    //   13: aload 4
    //   15: invokevirtual 74	java/io/File:exists	()Z
    //   18: ifeq +9 -> 27
    //   21: aload 4
    //   23: invokevirtual 77	java/io/File:delete	()Z
    //   26: pop
    //   27: new 79	java/io/FileOutputStream
    //   30: dup
    //   31: aload_0
    //   32: getfield 20	com/tencent/tbs/common/logupload/ZipHelper:zipFile	Ljava/lang/String;
    //   35: invokespecial 80	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   38: astore 5
    //   40: new 82	java/util/zip/ZipOutputStream
    //   43: dup
    //   44: new 84	java/io/BufferedOutputStream
    //   47: dup
    //   48: aload 5
    //   50: invokespecial 87	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   53: invokespecial 88	java/util/zip/ZipOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   56: astore 10
    //   58: aload 5
    //   60: astore 6
    //   62: aload 10
    //   64: astore 7
    //   66: sipush 2048
    //   69: newarray <illegal type>
    //   71: astore 12
    //   73: aload 5
    //   75: astore 6
    //   77: aload 10
    //   79: astore 7
    //   81: aload_0
    //   82: getfield 18	com/tencent/tbs/common/logupload/ZipHelper:files	[Ljava/lang/String;
    //   85: astore 13
    //   87: aload 5
    //   89: astore 6
    //   91: aload 10
    //   93: astore 7
    //   95: aload 13
    //   97: arraylength
    //   98: istore_2
    //   99: iconst_0
    //   100: istore_1
    //   101: iload_1
    //   102: iload_2
    //   103: if_icmpge +610 -> 713
    //   106: aload 13
    //   108: iload_1
    //   109: aaload
    //   110: astore 8
    //   112: aload 5
    //   114: astore 6
    //   116: aload 10
    //   118: astore 7
    //   120: new 90	java/lang/StringBuilder
    //   123: dup
    //   124: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   127: astore 4
    //   129: aload 5
    //   131: astore 6
    //   133: aload 10
    //   135: astore 7
    //   137: aload 4
    //   139: ldc 93
    //   141: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   144: pop
    //   145: aload 5
    //   147: astore 6
    //   149: aload 10
    //   151: astore 7
    //   153: aload 4
    //   155: aload 8
    //   157: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: pop
    //   161: aload 5
    //   163: astore 6
    //   165: aload 10
    //   167: astore 7
    //   169: aload 4
    //   171: invokevirtual 101	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   174: pop
    //   175: aload 5
    //   177: astore 6
    //   179: aload 10
    //   181: astore 7
    //   183: new 67	java/io/File
    //   186: dup
    //   187: aload 8
    //   189: invokespecial 70	java/io/File:<init>	(Ljava/lang/String;)V
    //   192: astore 4
    //   194: aload 5
    //   196: astore 6
    //   198: aload 10
    //   200: astore 7
    //   202: aload 4
    //   204: invokevirtual 74	java/io/File:exists	()Z
    //   207: ifeq +696 -> 903
    //   210: aload 5
    //   212: astore 6
    //   214: aload 10
    //   216: astore 7
    //   218: aload 4
    //   220: invokevirtual 104	java/io/File:isDirectory	()Z
    //   223: ifeq +6 -> 229
    //   226: goto +677 -> 903
    //   229: aload 5
    //   231: astore 6
    //   233: aload 10
    //   235: astore 7
    //   237: new 90	java/lang/StringBuilder
    //   240: dup
    //   241: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   244: astore 4
    //   246: aload 5
    //   248: astore 6
    //   250: aload 10
    //   252: astore 7
    //   254: aload 4
    //   256: ldc 106
    //   258: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   261: pop
    //   262: aload 5
    //   264: astore 6
    //   266: aload 10
    //   268: astore 7
    //   270: aload 4
    //   272: aload 8
    //   274: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   277: pop
    //   278: aload 5
    //   280: astore 6
    //   282: aload 10
    //   284: astore 7
    //   286: aload 4
    //   288: invokevirtual 101	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   291: pop
    //   292: new 108	java/io/FileInputStream
    //   295: dup
    //   296: aload 8
    //   298: invokespecial 109	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   301: astore 4
    //   303: new 111	java/io/BufferedInputStream
    //   306: dup
    //   307: aload 4
    //   309: sipush 2048
    //   312: invokespecial 114	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;I)V
    //   315: astore 11
    //   317: aload 4
    //   319: astore 6
    //   321: aload 11
    //   323: astore 7
    //   325: aload 10
    //   327: new 116	java/util/zip/ZipEntry
    //   330: dup
    //   331: aload 8
    //   333: aload 8
    //   335: ldc 118
    //   337: invokevirtual 124	java/lang/String:lastIndexOf	(Ljava/lang/String;)I
    //   340: iconst_1
    //   341: iadd
    //   342: invokevirtual 128	java/lang/String:substring	(I)Ljava/lang/String;
    //   345: invokespecial 129	java/util/zip/ZipEntry:<init>	(Ljava/lang/String;)V
    //   348: invokevirtual 133	java/util/zip/ZipOutputStream:putNextEntry	(Ljava/util/zip/ZipEntry;)V
    //   351: aload 4
    //   353: astore 6
    //   355: aload 11
    //   357: astore 7
    //   359: aload 11
    //   361: aload 12
    //   363: iconst_0
    //   364: sipush 2048
    //   367: invokevirtual 136	java/io/BufferedInputStream:read	([BII)I
    //   370: istore_3
    //   371: iload_3
    //   372: iconst_m1
    //   373: if_icmpeq +23 -> 396
    //   376: aload 4
    //   378: astore 6
    //   380: aload 11
    //   382: astore 7
    //   384: aload 10
    //   386: aload 12
    //   388: iconst_0
    //   389: iload_3
    //   390: invokevirtual 139	java/util/zip/ZipOutputStream:write	([BII)V
    //   393: goto -42 -> 351
    //   396: aload 4
    //   398: astore 6
    //   400: aload 11
    //   402: astore 7
    //   404: aload 10
    //   406: invokevirtual 142	java/util/zip/ZipOutputStream:flush	()V
    //   409: aload 4
    //   411: astore 6
    //   413: aload 11
    //   415: astore 7
    //   417: aload 10
    //   419: invokevirtual 145	java/util/zip/ZipOutputStream:closeEntry	()V
    //   422: aload 5
    //   424: astore 6
    //   426: aload 10
    //   428: astore 7
    //   430: aload 11
    //   432: invokevirtual 146	java/io/BufferedInputStream:close	()V
    //   435: goto +18 -> 453
    //   438: astore 8
    //   440: aload 5
    //   442: astore 6
    //   444: aload 10
    //   446: astore 7
    //   448: aload 8
    //   450: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   453: aload 5
    //   455: astore 6
    //   457: aload 10
    //   459: astore 7
    //   461: aload 4
    //   463: invokevirtual 147	java/io/FileInputStream:close	()V
    //   466: goto +437 -> 903
    //   469: astore 4
    //   471: aload 5
    //   473: astore 6
    //   475: aload 10
    //   477: astore 7
    //   479: aload 4
    //   481: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   484: goto +419 -> 903
    //   487: astore 9
    //   489: aload 4
    //   491: astore 8
    //   493: aload 11
    //   495: astore 4
    //   497: goto +48 -> 545
    //   500: astore 6
    //   502: goto +24 -> 526
    //   505: astore 9
    //   507: aconst_null
    //   508: astore 6
    //   510: aload 4
    //   512: astore 8
    //   514: aload 6
    //   516: astore 4
    //   518: goto +27 -> 545
    //   521: astore 6
    //   523: aconst_null
    //   524: astore 4
    //   526: aconst_null
    //   527: astore 9
    //   529: aload 6
    //   531: astore 8
    //   533: goto +97 -> 630
    //   536: astore 9
    //   538: aconst_null
    //   539: astore 8
    //   541: aload 8
    //   543: astore 4
    //   545: aload 8
    //   547: astore 6
    //   549: aload 4
    //   551: astore 7
    //   553: aload 9
    //   555: invokevirtual 62	java/lang/Exception:printStackTrace	()V
    //   558: aload 4
    //   560: ifnull +34 -> 594
    //   563: aload 5
    //   565: astore 6
    //   567: aload 10
    //   569: astore 7
    //   571: aload 4
    //   573: invokevirtual 146	java/io/BufferedInputStream:close	()V
    //   576: goto +18 -> 594
    //   579: astore 4
    //   581: aload 5
    //   583: astore 6
    //   585: aload 10
    //   587: astore 7
    //   589: aload 4
    //   591: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   594: aload 8
    //   596: ifnull +307 -> 903
    //   599: aload 5
    //   601: astore 6
    //   603: aload 10
    //   605: astore 7
    //   607: aload 8
    //   609: invokevirtual 147	java/io/FileInputStream:close	()V
    //   612: goto +291 -> 903
    //   615: astore 4
    //   617: goto -146 -> 471
    //   620: astore 8
    //   622: aload 7
    //   624: astore 9
    //   626: aload 6
    //   628: astore 4
    //   630: aload 9
    //   632: ifnull +34 -> 666
    //   635: aload 5
    //   637: astore 6
    //   639: aload 10
    //   641: astore 7
    //   643: aload 9
    //   645: invokevirtual 146	java/io/BufferedInputStream:close	()V
    //   648: goto +18 -> 666
    //   651: astore 9
    //   653: aload 5
    //   655: astore 6
    //   657: aload 10
    //   659: astore 7
    //   661: aload 9
    //   663: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   666: aload 4
    //   668: ifnull +34 -> 702
    //   671: aload 5
    //   673: astore 6
    //   675: aload 10
    //   677: astore 7
    //   679: aload 4
    //   681: invokevirtual 147	java/io/FileInputStream:close	()V
    //   684: goto +18 -> 702
    //   687: astore 4
    //   689: aload 5
    //   691: astore 6
    //   693: aload 10
    //   695: astore 7
    //   697: aload 4
    //   699: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   702: aload 5
    //   704: astore 6
    //   706: aload 10
    //   708: astore 7
    //   710: aload 8
    //   712: athrow
    //   713: aload 5
    //   715: astore 6
    //   717: aload 10
    //   719: astore 7
    //   721: new 67	java/io/File
    //   724: dup
    //   725: aload_0
    //   726: getfield 20	com/tencent/tbs/common/logupload/ZipHelper:zipFile	Ljava/lang/String;
    //   729: invokespecial 70	java/io/File:<init>	(Ljava/lang/String;)V
    //   732: invokestatic 149	com/tencent/tbs/common/logupload/ZipHelper:replaceWrongZipByte	(Ljava/io/File;)V
    //   735: aload 10
    //   737: invokevirtual 150	java/util/zip/ZipOutputStream:close	()V
    //   740: goto +10 -> 750
    //   743: astore 4
    //   745: aload 4
    //   747: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   750: aload 5
    //   752: invokevirtual 151	java/io/FileOutputStream:close	()V
    //   755: return
    //   756: astore 8
    //   758: aload 10
    //   760: astore 4
    //   762: goto +40 -> 802
    //   765: astore 4
    //   767: aconst_null
    //   768: astore 7
    //   770: goto +90 -> 860
    //   773: astore 8
    //   775: aconst_null
    //   776: astore 4
    //   778: goto +24 -> 802
    //   781: astore 4
    //   783: aconst_null
    //   784: astore 7
    //   786: aload 7
    //   788: astore 5
    //   790: goto +70 -> 860
    //   793: astore 8
    //   795: aconst_null
    //   796: astore 4
    //   798: aload 4
    //   800: astore 5
    //   802: aload 5
    //   804: astore 6
    //   806: aload 4
    //   808: astore 7
    //   810: aload 8
    //   812: invokevirtual 62	java/lang/Exception:printStackTrace	()V
    //   815: aload 4
    //   817: ifnull +18 -> 835
    //   820: aload 4
    //   822: invokevirtual 150	java/util/zip/ZipOutputStream:close	()V
    //   825: goto +10 -> 835
    //   828: astore 4
    //   830: aload 4
    //   832: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   835: aload 5
    //   837: ifnull +16 -> 853
    //   840: aload 5
    //   842: invokevirtual 151	java/io/FileOutputStream:close	()V
    //   845: return
    //   846: astore 4
    //   848: aload 4
    //   850: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   853: return
    //   854: astore 4
    //   856: aload 6
    //   858: astore 5
    //   860: aload 7
    //   862: ifnull +18 -> 880
    //   865: aload 7
    //   867: invokevirtual 150	java/util/zip/ZipOutputStream:close	()V
    //   870: goto +10 -> 880
    //   873: astore 6
    //   875: aload 6
    //   877: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   880: aload 5
    //   882: ifnull +18 -> 900
    //   885: aload 5
    //   887: invokevirtual 151	java/io/FileOutputStream:close	()V
    //   890: goto +10 -> 900
    //   893: astore 5
    //   895: aload 5
    //   897: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   900: aload 4
    //   902: athrow
    //   903: iload_1
    //   904: iconst_1
    //   905: iadd
    //   906: istore_1
    //   907: goto -806 -> 101
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	910	0	this	ZipHelper
    //   100	807	1	i	int
    //   98	6	2	j	int
    //   370	20	3	k	int
    //   11	451	4	localObject1	Object
    //   469	21	4	localIOException1	java.io.IOException
    //   495	77	4	localObject2	Object
    //   579	11	4	localIOException2	java.io.IOException
    //   615	1	4	localIOException3	java.io.IOException
    //   628	52	4	localObject3	Object
    //   687	11	4	localIOException4	java.io.IOException
    //   743	3	4	localIOException5	java.io.IOException
    //   760	1	4	localObject4	Object
    //   765	1	4	localObject5	Object
    //   776	1	4	localObject6	Object
    //   781	1	4	localObject7	Object
    //   796	25	4	localObject8	Object
    //   828	3	4	localIOException6	java.io.IOException
    //   846	3	4	localIOException7	java.io.IOException
    //   854	47	4	localObject9	Object
    //   38	848	5	localObject10	Object
    //   893	3	5	localIOException8	java.io.IOException
    //   60	414	6	localObject11	Object
    //   500	1	6	localObject12	Object
    //   508	7	6	localObject13	Object
    //   521	9	6	localObject14	Object
    //   547	310	6	localObject15	Object
    //   873	3	6	localIOException9	java.io.IOException
    //   64	802	7	localObject16	Object
    //   110	224	8	str	String
    //   438	11	8	localIOException10	java.io.IOException
    //   491	117	8	localObject17	Object
    //   620	91	8	localObject18	Object
    //   756	1	8	localException1	Exception
    //   773	1	8	localException2	Exception
    //   793	18	8	localException3	Exception
    //   487	1	9	localException4	Exception
    //   505	1	9	localException5	Exception
    //   527	1	9	localObject19	Object
    //   536	18	9	localException6	Exception
    //   624	20	9	localObject20	Object
    //   651	11	9	localIOException11	java.io.IOException
    //   56	703	10	localZipOutputStream	java.util.zip.ZipOutputStream
    //   315	179	11	localBufferedInputStream	java.io.BufferedInputStream
    //   71	316	12	arrayOfByte	byte[]
    //   85	22	13	arrayOfString	String[]
    // Exception table:
    //   from	to	target	type
    //   430	435	438	java/io/IOException
    //   461	466	469	java/io/IOException
    //   325	351	487	java/lang/Exception
    //   359	371	487	java/lang/Exception
    //   384	393	487	java/lang/Exception
    //   404	409	487	java/lang/Exception
    //   417	422	487	java/lang/Exception
    //   303	317	500	finally
    //   303	317	505	java/lang/Exception
    //   292	303	521	finally
    //   292	303	536	java/lang/Exception
    //   571	576	579	java/io/IOException
    //   607	612	615	java/io/IOException
    //   325	351	620	finally
    //   359	371	620	finally
    //   384	393	620	finally
    //   404	409	620	finally
    //   417	422	620	finally
    //   553	558	620	finally
    //   643	648	651	java/io/IOException
    //   679	684	687	java/io/IOException
    //   735	740	743	java/io/IOException
    //   66	73	756	java/lang/Exception
    //   81	87	756	java/lang/Exception
    //   95	99	756	java/lang/Exception
    //   120	129	756	java/lang/Exception
    //   137	145	756	java/lang/Exception
    //   153	161	756	java/lang/Exception
    //   169	175	756	java/lang/Exception
    //   183	194	756	java/lang/Exception
    //   202	210	756	java/lang/Exception
    //   218	226	756	java/lang/Exception
    //   237	246	756	java/lang/Exception
    //   254	262	756	java/lang/Exception
    //   270	278	756	java/lang/Exception
    //   286	292	756	java/lang/Exception
    //   430	435	756	java/lang/Exception
    //   448	453	756	java/lang/Exception
    //   461	466	756	java/lang/Exception
    //   479	484	756	java/lang/Exception
    //   571	576	756	java/lang/Exception
    //   589	594	756	java/lang/Exception
    //   607	612	756	java/lang/Exception
    //   643	648	756	java/lang/Exception
    //   661	666	756	java/lang/Exception
    //   679	684	756	java/lang/Exception
    //   697	702	756	java/lang/Exception
    //   710	713	756	java/lang/Exception
    //   721	735	756	java/lang/Exception
    //   40	58	765	finally
    //   40	58	773	java/lang/Exception
    //   0	27	781	finally
    //   27	40	781	finally
    //   0	27	793	java/lang/Exception
    //   27	40	793	java/lang/Exception
    //   820	825	828	java/io/IOException
    //   750	755	846	java/io/IOException
    //   840	845	846	java/io/IOException
    //   66	73	854	finally
    //   81	87	854	finally
    //   95	99	854	finally
    //   120	129	854	finally
    //   137	145	854	finally
    //   153	161	854	finally
    //   169	175	854	finally
    //   183	194	854	finally
    //   202	210	854	finally
    //   218	226	854	finally
    //   237	246	854	finally
    //   254	262	854	finally
    //   270	278	854	finally
    //   286	292	854	finally
    //   430	435	854	finally
    //   448	453	854	finally
    //   461	466	854	finally
    //   479	484	854	finally
    //   571	576	854	finally
    //   589	594	854	finally
    //   607	612	854	finally
    //   643	648	854	finally
    //   661	666	854	finally
    //   679	684	854	finally
    //   697	702	854	finally
    //   710	713	854	finally
    //   721	735	854	finally
    //   810	815	854	finally
    //   865	870	873	java/io/IOException
    //   885	890	893	java/io/IOException
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\logupload\ZipHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */