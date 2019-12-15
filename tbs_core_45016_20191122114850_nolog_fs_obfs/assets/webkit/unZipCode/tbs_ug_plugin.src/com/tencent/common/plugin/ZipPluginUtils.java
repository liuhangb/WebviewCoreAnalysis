package com.tencent.common.plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;

public class ZipPluginUtils
{
  private static String a = "ZipPluginUtils";
  
  private static int a(InputStream paramInputStream, File paramFile, String paramString1, String paramString2)
  {
    if (PluginFileUtils.createDir(paramFile.getParentFile(), paramFile.getName(), QBPluginServiceImpl.mFileMode) == null) {
      return 520;
    }
    return b(paramInputStream, paramFile, paramString1, paramString2);
  }
  
  /* Error */
  private static int b(InputStream paramInputStream, File paramFile, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 49	com/tencent/common/utils/LinuxToolsJni
    //   3: dup
    //   4: invokespecial 50	com/tencent/common/utils/LinuxToolsJni:<init>	()V
    //   7: astore 10
    //   9: invokestatic 56	com/tencent/common/utils/TbsMode:TBSISQB	()Z
    //   12: ifne +13 -> 25
    //   15: getstatic 60	com/tencent/common/utils/LinuxToolsJni:gJniloaded	Z
    //   18: ifne +7 -> 25
    //   21: sipush 511
    //   24: ireturn
    //   25: aconst_null
    //   26: astore 7
    //   28: aconst_null
    //   29: astore 8
    //   31: aconst_null
    //   32: astore 9
    //   34: aload 9
    //   36: astore 6
    //   38: ldc 61
    //   40: newarray <illegal type>
    //   42: astore 11
    //   44: aload 9
    //   46: astore 6
    //   48: new 63	java/util/zip/ZipInputStream
    //   51: dup
    //   52: aload_0
    //   53: invokespecial 66	java/util/zip/ZipInputStream:<init>	(Ljava/io/InputStream;)V
    //   56: astore_0
    //   57: aload_0
    //   58: invokevirtual 70	java/util/zip/ZipInputStream:getNextEntry	()Ljava/util/zip/ZipEntry;
    //   61: astore 6
    //   63: aload 6
    //   65: ifnull +503 -> 568
    //   68: aload 6
    //   70: invokevirtual 73	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   73: ldc 75
    //   75: invokevirtual 81	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   78: ifeq +6 -> 84
    //   81: goto -24 -> 57
    //   84: aload 6
    //   86: invokevirtual 84	java/util/zip/ZipEntry:isDirectory	()Z
    //   89: ifne +435 -> 524
    //   92: new 19	java/io/File
    //   95: dup
    //   96: aload_1
    //   97: aload 6
    //   99: invokevirtual 73	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   102: invokespecial 87	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   105: astore 7
    //   107: aload 7
    //   109: invokevirtual 23	java/io/File:getParentFile	()Ljava/io/File;
    //   112: invokevirtual 90	java/io/File:mkdirs	()Z
    //   115: pop
    //   116: new 92	java/io/FileOutputStream
    //   119: dup
    //   120: aload 7
    //   122: iconst_0
    //   123: invokespecial 95	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
    //   126: astore 6
    //   128: aload_0
    //   129: aload 11
    //   131: invokevirtual 99	java/util/zip/ZipInputStream:read	([B)I
    //   134: istore 4
    //   136: iload 4
    //   138: iconst_m1
    //   139: if_icmpeq +16 -> 155
    //   142: aload 6
    //   144: aload 11
    //   146: iconst_0
    //   147: iload 4
    //   149: invokevirtual 103	java/io/FileOutputStream:write	([BII)V
    //   152: goto -24 -> 128
    //   155: aload 6
    //   157: invokevirtual 106	java/io/FileOutputStream:close	()V
    //   160: goto +10 -> 170
    //   163: astore 6
    //   165: aload 6
    //   167: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   170: invokestatic 56	com/tencent/common/utils/TbsMode:TBSISQB	()Z
    //   173: ifne -116 -> 57
    //   176: aload 10
    //   178: aload 7
    //   180: invokevirtual 112	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   183: getstatic 32	com/tencent/common/plugin/QBPluginServiceImpl:mFileMode	Ljava/lang/String;
    //   186: invokevirtual 116	com/tencent/common/utils/LinuxToolsJni:Chmod	(Ljava/lang/String;Ljava/lang/String;)I
    //   189: pop
    //   190: goto -133 -> 57
    //   193: astore_1
    //   194: goto +313 -> 507
    //   197: astore_1
    //   198: aload_1
    //   199: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   202: aload_1
    //   203: invokevirtual 120	java/io/IOException:getMessage	()Ljava/lang/String;
    //   206: astore_1
    //   207: new 19	java/io/File
    //   210: dup
    //   211: aload_3
    //   212: invokespecial 123	java/io/File:<init>	(Ljava/lang/String;)V
    //   215: astore 7
    //   217: new 125	java/lang/StringBuilder
    //   220: dup
    //   221: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   224: astore 8
    //   226: aload 8
    //   228: aload 7
    //   230: invokestatic 132	com/tencent/common/utils/Md5Utils:getMD5	(Ljava/io/File;)Ljava/lang/String;
    //   233: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   236: pop
    //   237: aload 8
    //   239: ldc -118
    //   241: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: pop
    //   245: aload 8
    //   247: aload 7
    //   249: invokevirtual 142	java/io/File:length	()J
    //   252: invokevirtual 145	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   255: pop
    //   256: aload_2
    //   257: iconst_3
    //   258: aload 8
    //   260: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   263: invokestatic 154	com/tencent/common/plugin/PluginStatBehavior:setLocalMd5	(Ljava/lang/String;ILjava/lang/String;)V
    //   266: aload_2
    //   267: iconst_3
    //   268: aload_1
    //   269: invokestatic 157	com/tencent/common/plugin/PluginStatBehavior:setSvrMd5	(Ljava/lang/String;ILjava/lang/String;)V
    //   272: aload_1
    //   273: invokestatic 162	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   276: ifne +201 -> 477
    //   279: aload_1
    //   280: invokevirtual 165	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   283: ldc -89
    //   285: invokevirtual 81	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   288: ifeq +97 -> 385
    //   291: new 125	java/lang/StringBuilder
    //   294: dup
    //   295: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   298: astore 7
    //   300: aload 7
    //   302: aload_1
    //   303: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   306: pop
    //   307: aload 7
    //   309: ldc -87
    //   311: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   314: pop
    //   315: aload 7
    //   317: ldc -85
    //   319: invokestatic 177	com/tencent/common/utils/FileUtilsF:getSdcardFreeSpace	(Ljava/lang/String;)J
    //   322: invokevirtual 145	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   325: pop
    //   326: aload 7
    //   328: ldc -77
    //   330: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   333: pop
    //   334: aload 7
    //   336: ldc -85
    //   338: invokestatic 182	com/tencent/common/utils/FileUtilsF:getSdcardTotalSpace	(Ljava/lang/String;)J
    //   341: invokevirtual 145	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   344: pop
    //   345: aload_2
    //   346: iconst_3
    //   347: aload 7
    //   349: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   352: invokestatic 157	com/tencent/common/plugin/PluginStatBehavior:setSvrMd5	(Ljava/lang/String;ILjava/lang/String;)V
    //   355: aload 6
    //   357: invokevirtual 106	java/io/FileOutputStream:close	()V
    //   360: goto +8 -> 368
    //   363: astore_1
    //   364: aload_1
    //   365: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   368: aload_0
    //   369: invokevirtual 183	java/util/zip/ZipInputStream:close	()V
    //   372: sipush 574
    //   375: ireturn
    //   376: astore_0
    //   377: aload_0
    //   378: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   381: sipush 574
    //   384: ireturn
    //   385: aload_1
    //   386: invokevirtual 165	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   389: ldc -71
    //   391: invokevirtual 81	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   394: istore 5
    //   396: iload 5
    //   398: ifeq +33 -> 431
    //   401: aload 6
    //   403: invokevirtual 106	java/io/FileOutputStream:close	()V
    //   406: goto +8 -> 414
    //   409: astore_1
    //   410: aload_1
    //   411: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   414: aload_0
    //   415: invokevirtual 183	java/util/zip/ZipInputStream:close	()V
    //   418: sipush 573
    //   421: ireturn
    //   422: astore_0
    //   423: aload_0
    //   424: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   427: sipush 573
    //   430: ireturn
    //   431: aload_1
    //   432: invokevirtual 165	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   435: ldc -69
    //   437: invokevirtual 81	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   440: istore 5
    //   442: iload 5
    //   444: ifeq +33 -> 477
    //   447: aload 6
    //   449: invokevirtual 106	java/io/FileOutputStream:close	()V
    //   452: goto +8 -> 460
    //   455: astore_1
    //   456: aload_1
    //   457: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   460: aload_0
    //   461: invokevirtual 183	java/util/zip/ZipInputStream:close	()V
    //   464: sipush 575
    //   467: ireturn
    //   468: astore_0
    //   469: aload_0
    //   470: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   473: sipush 575
    //   476: ireturn
    //   477: aload 6
    //   479: invokevirtual 106	java/io/FileOutputStream:close	()V
    //   482: goto +8 -> 490
    //   485: astore_1
    //   486: aload_1
    //   487: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   490: aload_0
    //   491: invokevirtual 183	java/util/zip/ZipInputStream:close	()V
    //   494: sipush 570
    //   497: ireturn
    //   498: astore_0
    //   499: aload_0
    //   500: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   503: sipush 570
    //   506: ireturn
    //   507: aload 6
    //   509: invokevirtual 106	java/io/FileOutputStream:close	()V
    //   512: goto +10 -> 522
    //   515: astore 6
    //   517: aload 6
    //   519: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   522: aload_1
    //   523: athrow
    //   524: new 19	java/io/File
    //   527: dup
    //   528: aload_1
    //   529: aload 6
    //   531: invokevirtual 73	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   534: invokespecial 87	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   537: astore 6
    //   539: aload 6
    //   541: invokevirtual 90	java/io/File:mkdirs	()Z
    //   544: pop
    //   545: invokestatic 56	com/tencent/common/utils/TbsMode:TBSISQB	()Z
    //   548: ifne -491 -> 57
    //   551: aload 10
    //   553: aload 6
    //   555: invokevirtual 112	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   558: getstatic 32	com/tencent/common/plugin/QBPluginServiceImpl:mFileMode	Ljava/lang/String;
    //   561: invokevirtual 116	com/tencent/common/utils/LinuxToolsJni:Chmod	(Ljava/lang/String;Ljava/lang/String;)I
    //   564: pop
    //   565: goto -508 -> 57
    //   568: aload_0
    //   569: invokevirtual 183	java/util/zip/ZipInputStream:close	()V
    //   572: iconst_0
    //   573: ireturn
    //   574: astore_0
    //   575: aload_0
    //   576: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   579: iconst_0
    //   580: ireturn
    //   581: astore_1
    //   582: goto +254 -> 836
    //   585: astore_1
    //   586: goto +18 -> 604
    //   589: astore_1
    //   590: goto +132 -> 722
    //   593: astore_1
    //   594: aload 6
    //   596: astore_0
    //   597: goto +239 -> 836
    //   600: astore_1
    //   601: aload 7
    //   603: astore_0
    //   604: aload_0
    //   605: astore 6
    //   607: aload_1
    //   608: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   611: aload_0
    //   612: astore 6
    //   614: new 19	java/io/File
    //   617: dup
    //   618: aload_3
    //   619: invokespecial 123	java/io/File:<init>	(Ljava/lang/String;)V
    //   622: astore_3
    //   623: aload_0
    //   624: astore 6
    //   626: new 125	java/lang/StringBuilder
    //   629: dup
    //   630: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   633: astore 7
    //   635: aload_0
    //   636: astore 6
    //   638: aload 7
    //   640: aload_3
    //   641: invokestatic 132	com/tencent/common/utils/Md5Utils:getMD5	(Ljava/io/File;)Ljava/lang/String;
    //   644: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   647: pop
    //   648: aload_0
    //   649: astore 6
    //   651: aload 7
    //   653: ldc -118
    //   655: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   658: pop
    //   659: aload_0
    //   660: astore 6
    //   662: aload 7
    //   664: aload_3
    //   665: invokevirtual 142	java/io/File:length	()J
    //   668: invokevirtual 145	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   671: pop
    //   672: aload_0
    //   673: astore 6
    //   675: aload_2
    //   676: iconst_3
    //   677: aload 7
    //   679: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   682: invokestatic 154	com/tencent/common/plugin/PluginStatBehavior:setLocalMd5	(Ljava/lang/String;ILjava/lang/String;)V
    //   685: aload_0
    //   686: astore 6
    //   688: aload_2
    //   689: iconst_3
    //   690: aload_1
    //   691: invokevirtual 120	java/io/IOException:getMessage	()Ljava/lang/String;
    //   694: invokestatic 157	com/tencent/common/plugin/PluginStatBehavior:setSvrMd5	(Ljava/lang/String;ILjava/lang/String;)V
    //   697: aload_0
    //   698: ifnull +16 -> 714
    //   701: aload_0
    //   702: invokevirtual 183	java/util/zip/ZipInputStream:close	()V
    //   705: sipush 572
    //   708: ireturn
    //   709: astore_0
    //   710: aload_0
    //   711: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   714: sipush 572
    //   717: ireturn
    //   718: astore_1
    //   719: aload 8
    //   721: astore_0
    //   722: aload_0
    //   723: astore 6
    //   725: aload_1
    //   726: invokevirtual 188	java/io/FileNotFoundException:printStackTrace	()V
    //   729: aload_0
    //   730: astore 6
    //   732: new 19	java/io/File
    //   735: dup
    //   736: aload_3
    //   737: invokespecial 123	java/io/File:<init>	(Ljava/lang/String;)V
    //   740: astore_3
    //   741: aload_0
    //   742: astore 6
    //   744: new 125	java/lang/StringBuilder
    //   747: dup
    //   748: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   751: astore 7
    //   753: aload_0
    //   754: astore 6
    //   756: aload 7
    //   758: aload_3
    //   759: invokestatic 132	com/tencent/common/utils/Md5Utils:getMD5	(Ljava/io/File;)Ljava/lang/String;
    //   762: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   765: pop
    //   766: aload_0
    //   767: astore 6
    //   769: aload 7
    //   771: ldc -118
    //   773: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   776: pop
    //   777: aload_0
    //   778: astore 6
    //   780: aload 7
    //   782: aload_3
    //   783: invokevirtual 142	java/io/File:length	()J
    //   786: invokevirtual 145	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   789: pop
    //   790: aload_0
    //   791: astore 6
    //   793: aload_2
    //   794: iconst_3
    //   795: aload 7
    //   797: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   800: invokestatic 154	com/tencent/common/plugin/PluginStatBehavior:setLocalMd5	(Ljava/lang/String;ILjava/lang/String;)V
    //   803: aload_0
    //   804: astore 6
    //   806: aload_2
    //   807: iconst_3
    //   808: aload_1
    //   809: invokevirtual 189	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
    //   812: invokestatic 157	com/tencent/common/plugin/PluginStatBehavior:setSvrMd5	(Ljava/lang/String;ILjava/lang/String;)V
    //   815: aload_0
    //   816: ifnull +16 -> 832
    //   819: aload_0
    //   820: invokevirtual 183	java/util/zip/ZipInputStream:close	()V
    //   823: sipush 571
    //   826: ireturn
    //   827: astore_0
    //   828: aload_0
    //   829: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   832: sipush 571
    //   835: ireturn
    //   836: aload_0
    //   837: ifnull +15 -> 852
    //   840: aload_0
    //   841: invokevirtual 183	java/util/zip/ZipInputStream:close	()V
    //   844: goto +8 -> 852
    //   847: astore_0
    //   848: aload_0
    //   849: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   852: aload_1
    //   853: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	854	0	paramInputStream	InputStream
    //   0	854	1	paramFile	File
    //   0	854	2	paramString1	String
    //   0	854	3	paramString2	String
    //   134	14	4	i	int
    //   394	49	5	bool	boolean
    //   36	120	6	localObject1	Object
    //   163	345	6	localException1	Exception
    //   515	15	6	localException2	Exception
    //   537	268	6	localObject2	Object
    //   26	770	7	localObject3	Object
    //   29	691	8	localStringBuilder	StringBuilder
    //   32	13	9	localObject4	Object
    //   7	545	10	localLinuxToolsJni	com.tencent.common.utils.LinuxToolsJni
    //   42	103	11	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   155	160	163	java/lang/Exception
    //   128	136	193	finally
    //   142	152	193	finally
    //   198	355	193	finally
    //   385	396	193	finally
    //   431	442	193	finally
    //   128	136	197	java/io/IOException
    //   142	152	197	java/io/IOException
    //   355	360	363	java/lang/Exception
    //   368	372	376	java/io/IOException
    //   401	406	409	java/lang/Exception
    //   414	418	422	java/io/IOException
    //   447	452	455	java/lang/Exception
    //   460	464	468	java/io/IOException
    //   477	482	485	java/lang/Exception
    //   490	494	498	java/io/IOException
    //   507	512	515	java/lang/Exception
    //   568	572	574	java/io/IOException
    //   57	63	581	finally
    //   68	81	581	finally
    //   84	128	581	finally
    //   155	160	581	finally
    //   165	170	581	finally
    //   170	190	581	finally
    //   355	360	581	finally
    //   364	368	581	finally
    //   401	406	581	finally
    //   410	414	581	finally
    //   447	452	581	finally
    //   456	460	581	finally
    //   477	482	581	finally
    //   486	490	581	finally
    //   507	512	581	finally
    //   517	522	581	finally
    //   522	524	581	finally
    //   524	565	581	finally
    //   57	63	585	java/io/IOException
    //   68	81	585	java/io/IOException
    //   84	128	585	java/io/IOException
    //   155	160	585	java/io/IOException
    //   165	170	585	java/io/IOException
    //   170	190	585	java/io/IOException
    //   355	360	585	java/io/IOException
    //   364	368	585	java/io/IOException
    //   401	406	585	java/io/IOException
    //   410	414	585	java/io/IOException
    //   447	452	585	java/io/IOException
    //   456	460	585	java/io/IOException
    //   477	482	585	java/io/IOException
    //   486	490	585	java/io/IOException
    //   507	512	585	java/io/IOException
    //   517	522	585	java/io/IOException
    //   522	524	585	java/io/IOException
    //   524	565	585	java/io/IOException
    //   57	63	589	java/io/FileNotFoundException
    //   68	81	589	java/io/FileNotFoundException
    //   84	128	589	java/io/FileNotFoundException
    //   155	160	589	java/io/FileNotFoundException
    //   165	170	589	java/io/FileNotFoundException
    //   170	190	589	java/io/FileNotFoundException
    //   355	360	589	java/io/FileNotFoundException
    //   364	368	589	java/io/FileNotFoundException
    //   401	406	589	java/io/FileNotFoundException
    //   410	414	589	java/io/FileNotFoundException
    //   447	452	589	java/io/FileNotFoundException
    //   456	460	589	java/io/FileNotFoundException
    //   477	482	589	java/io/FileNotFoundException
    //   486	490	589	java/io/FileNotFoundException
    //   507	512	589	java/io/FileNotFoundException
    //   517	522	589	java/io/FileNotFoundException
    //   522	524	589	java/io/FileNotFoundException
    //   524	565	589	java/io/FileNotFoundException
    //   38	44	593	finally
    //   48	57	593	finally
    //   607	611	593	finally
    //   614	623	593	finally
    //   626	635	593	finally
    //   638	648	593	finally
    //   651	659	593	finally
    //   662	672	593	finally
    //   675	685	593	finally
    //   688	697	593	finally
    //   725	729	593	finally
    //   732	741	593	finally
    //   744	753	593	finally
    //   756	766	593	finally
    //   769	777	593	finally
    //   780	790	593	finally
    //   793	803	593	finally
    //   806	815	593	finally
    //   38	44	600	java/io/IOException
    //   48	57	600	java/io/IOException
    //   701	705	709	java/io/IOException
    //   38	44	718	java/io/FileNotFoundException
    //   48	57	718	java/io/FileNotFoundException
    //   819	823	827	java/io/IOException
    //   840	844	847	java/io/IOException
  }
  
  /* Error */
  public static int installPluginFromSdCard(android.content.Context paramContext1, android.content.Context paramContext2, File paramFile, String paramString1, String paramString2, int paramInt1, String paramString3, QBPluginItemInfo paramQBPluginItemInfo, int paramInt2)
    throws android.os.RemoteException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 12
    //   3: new 197	java/io/FileInputStream
    //   6: dup
    //   7: aload_3
    //   8: invokespecial 198	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   11: astore 6
    //   13: new 200	java/io/BufferedInputStream
    //   16: dup
    //   17: aload 6
    //   19: invokespecial 201	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   22: astore 12
    //   24: aload_0
    //   25: aload 4
    //   27: iconst_0
    //   28: invokestatic 205	com/tencent/common/plugin/QBPluginServiceImpl:getPluginInstallDir	(Landroid/content/Context;Ljava/lang/String;Z)Ljava/io/File;
    //   31: astore 18
    //   33: aload 18
    //   35: ifnonnull +109 -> 144
    //   38: new 125	java/lang/StringBuilder
    //   41: dup
    //   42: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   45: astore_0
    //   46: aload_0
    //   47: ldc -49
    //   49: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: aload_0
    //   54: aload 4
    //   56: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: pop
    //   60: aload_0
    //   61: ldc -47
    //   63: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: pop
    //   67: ldc -45
    //   69: aload_0
    //   70: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   73: invokestatic 217	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   76: aload 4
    //   78: iconst_3
    //   79: sipush 518
    //   82: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   85: aload 4
    //   87: iconst_3
    //   88: sipush 518
    //   91: invokestatic 224	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   94: aload 6
    //   96: invokevirtual 225	java/io/FileInputStream:close	()V
    //   99: aload 12
    //   101: invokevirtual 226	java/io/BufferedInputStream:close	()V
    //   104: goto +8 -> 112
    //   107: astore_0
    //   108: aload_0
    //   109: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   112: aload 4
    //   114: iconst_3
    //   115: bipush 50
    //   117: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   120: aload 4
    //   122: iconst_3
    //   123: iconst_0
    //   124: invokestatic 224	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   127: sipush 6007
    //   130: ireturn
    //   131: astore_3
    //   132: aload 6
    //   134: astore_0
    //   135: aload 12
    //   137: astore_1
    //   138: iconst_0
    //   139: istore 11
    //   141: goto +1478 -> 1619
    //   144: aload 18
    //   146: invokevirtual 112	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   149: astore 13
    //   151: aload_3
    //   152: invokevirtual 165	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   155: aload 13
    //   157: invokevirtual 165	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   160: invokevirtual 81	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   163: istore 11
    //   165: aload 6
    //   167: astore 15
    //   169: aload 12
    //   171: astore 14
    //   173: aload 6
    //   175: astore 16
    //   177: aload 12
    //   179: astore 13
    //   181: aload 12
    //   183: aload 18
    //   185: aload 4
    //   187: aload_3
    //   188: invokestatic 228	com/tencent/common/plugin/ZipPluginUtils:a	(Ljava/io/InputStream;Ljava/io/File;Ljava/lang/String;Ljava/lang/String;)I
    //   191: istore 9
    //   193: iload 9
    //   195: ifeq +667 -> 862
    //   198: aload 6
    //   200: astore 15
    //   202: aload 12
    //   204: astore 14
    //   206: aload 6
    //   208: astore 16
    //   210: aload 12
    //   212: astore 13
    //   214: aload 4
    //   216: iconst_3
    //   217: iload 9
    //   219: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   222: aload 6
    //   224: astore 15
    //   226: aload 12
    //   228: astore 14
    //   230: aload 6
    //   232: invokevirtual 225	java/io/FileInputStream:close	()V
    //   235: aload 6
    //   237: astore 15
    //   239: aload 12
    //   241: astore 14
    //   243: aload 12
    //   245: invokevirtual 226	java/io/BufferedInputStream:close	()V
    //   248: sipush 574
    //   251: iload 9
    //   253: if_icmpne +74 -> 327
    //   256: aload 6
    //   258: astore 15
    //   260: aload 12
    //   262: astore 14
    //   264: aload 18
    //   266: invokevirtual 231	java/io/File:exists	()Z
    //   269: ifeq +58 -> 327
    //   272: aload 6
    //   274: astore 15
    //   276: aload 12
    //   278: astore 14
    //   280: aload_3
    //   281: invokevirtual 165	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   284: aload 18
    //   286: invokevirtual 112	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   289: invokevirtual 165	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   292: invokevirtual 81	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   295: ifne +32 -> 327
    //   298: aload 6
    //   300: astore 15
    //   302: aload 12
    //   304: astore 14
    //   306: aload 4
    //   308: iconst_3
    //   309: ldc -23
    //   311: invokestatic 235	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;ILjava/lang/String;)V
    //   314: aload 6
    //   316: astore 15
    //   318: aload 12
    //   320: astore 14
    //   322: aload 18
    //   324: invokestatic 239	com/tencent/common/utils/FileUtilsF:cleanDirectory	(Ljava/io/File;)V
    //   327: aload 6
    //   329: astore 15
    //   331: aload 12
    //   333: astore 14
    //   335: new 197	java/io/FileInputStream
    //   338: dup
    //   339: aload_3
    //   340: invokespecial 198	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   343: astore 13
    //   345: new 200	java/io/BufferedInputStream
    //   348: dup
    //   349: aload 13
    //   351: invokespecial 201	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   354: astore 14
    //   356: aload 13
    //   358: astore 16
    //   360: aload 14
    //   362: astore 17
    //   364: aload 13
    //   366: astore 15
    //   368: aload 14
    //   370: astore 12
    //   372: aload 14
    //   374: aload 18
    //   376: aload 4
    //   378: aload_3
    //   379: invokestatic 228	com/tencent/common/plugin/ZipPluginUtils:a	(Ljava/io/InputStream;Ljava/io/File;Ljava/lang/String;Ljava/lang/String;)I
    //   382: istore 10
    //   384: aload 13
    //   386: astore 16
    //   388: aload 14
    //   390: astore 17
    //   392: iload 10
    //   394: istore 9
    //   396: aload 13
    //   398: astore 15
    //   400: aload 14
    //   402: astore 12
    //   404: aload 4
    //   406: iconst_3
    //   407: iload 10
    //   409: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   412: aload 13
    //   414: astore 6
    //   416: aload 14
    //   418: astore_3
    //   419: iload 10
    //   421: istore 9
    //   423: goto +87 -> 510
    //   426: astore_0
    //   427: aload 17
    //   429: astore_1
    //   430: aload 16
    //   432: astore 6
    //   434: aload_0
    //   435: astore_3
    //   436: aload 6
    //   438: astore_0
    //   439: goto -298 -> 141
    //   442: astore_3
    //   443: aload 13
    //   445: astore 6
    //   447: aload 14
    //   449: astore 12
    //   451: goto +36 -> 487
    //   454: astore_0
    //   455: aload 13
    //   457: astore_1
    //   458: goto +1087 -> 1545
    //   461: astore_3
    //   462: aload 13
    //   464: astore_0
    //   465: goto +15 -> 480
    //   468: astore_3
    //   469: aload 13
    //   471: astore 6
    //   473: goto +14 -> 487
    //   476: astore_3
    //   477: aload 6
    //   479: astore_0
    //   480: aload 12
    //   482: astore_1
    //   483: goto -342 -> 141
    //   486: astore_3
    //   487: aload 6
    //   489: astore 15
    //   491: aload 12
    //   493: astore 14
    //   495: aload 6
    //   497: astore 16
    //   499: aload 12
    //   501: astore 13
    //   503: aload_3
    //   504: invokevirtual 240	java/lang/Throwable:printStackTrace	()V
    //   507: aload 12
    //   509: astore_3
    //   510: aload 6
    //   512: astore 15
    //   514: aload_3
    //   515: astore 12
    //   517: new 125	java/lang/StringBuilder
    //   520: dup
    //   521: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   524: astore 13
    //   526: aload 6
    //   528: astore 15
    //   530: aload_3
    //   531: astore 12
    //   533: aload 13
    //   535: ldc -49
    //   537: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   540: pop
    //   541: aload 6
    //   543: astore 15
    //   545: aload_3
    //   546: astore 12
    //   548: aload 13
    //   550: aload 4
    //   552: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   555: pop
    //   556: aload 6
    //   558: astore 15
    //   560: aload_3
    //   561: astore 12
    //   563: aload 13
    //   565: ldc -14
    //   567: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   570: pop
    //   571: aload 6
    //   573: astore 15
    //   575: aload_3
    //   576: astore 12
    //   578: ldc -45
    //   580: aload 13
    //   582: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   585: invokestatic 217	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   588: iload 9
    //   590: ifeq +253 -> 843
    //   593: aload 6
    //   595: astore 15
    //   597: aload_3
    //   598: astore 12
    //   600: aload 4
    //   602: iconst_3
    //   603: iload 9
    //   605: invokestatic 224	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   608: sipush 574
    //   611: iload 9
    //   613: if_icmpne +68 -> 681
    //   616: aload 6
    //   618: invokevirtual 225	java/io/FileInputStream:close	()V
    //   621: aload_3
    //   622: invokevirtual 226	java/io/BufferedInputStream:close	()V
    //   625: goto +8 -> 633
    //   628: astore_0
    //   629: aload_0
    //   630: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   633: iload 11
    //   635: ifeq +27 -> 662
    //   638: aload 4
    //   640: iconst_3
    //   641: sipush 560
    //   644: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   647: aload_2
    //   648: invokestatic 245	com/tencent/common/utils/FileUtilsF:delete	(Ljava/io/File;)V
    //   651: goto +11 -> 662
    //   654: astore_0
    //   655: aload_0
    //   656: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   659: goto +3 -> 662
    //   662: aload 4
    //   664: iconst_3
    //   665: bipush 50
    //   667: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   670: aload 4
    //   672: iconst_3
    //   673: iconst_0
    //   674: invokestatic 224	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   677: sipush 6008
    //   680: ireturn
    //   681: sipush 572
    //   684: iload 9
    //   686: if_icmpeq +17 -> 703
    //   689: sipush 573
    //   692: iload 9
    //   694: if_icmpne +6 -> 700
    //   697: goto +6 -> 703
    //   700: goto +78 -> 778
    //   703: aload 6
    //   705: astore 15
    //   707: aload_3
    //   708: astore 12
    //   710: getstatic 249	com/tencent/common/plugin/QBPluginServiceImpl:mFilePushLogger	Lcom/tencent/common/plugin/QBPluginServiceImpl$ILogPushUploadHelper;
    //   713: astore_0
    //   714: aload_0
    //   715: ifnull +63 -> 778
    //   718: aload 6
    //   720: astore 16
    //   722: aload_3
    //   723: astore 17
    //   725: aload 6
    //   727: astore 15
    //   729: aload_3
    //   730: astore 12
    //   732: new 251	java/util/ArrayList
    //   735: dup
    //   736: invokespecial 252	java/util/ArrayList:<init>	()V
    //   739: astore_0
    //   740: aload 6
    //   742: astore 12
    //   744: aload_3
    //   745: astore 13
    //   747: aload_0
    //   748: aload_2
    //   749: invokevirtual 256	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   752: pop
    //   753: aload 6
    //   755: astore 12
    //   757: aload_3
    //   758: astore 13
    //   760: getstatic 249	com/tencent/common/plugin/QBPluginServiceImpl:mFilePushLogger	Lcom/tencent/common/plugin/QBPluginServiceImpl$ILogPushUploadHelper;
    //   763: aload_0
    //   764: invokeinterface 262 2 0
    //   769: goto +9 -> 778
    //   772: astore_0
    //   773: aload_3
    //   774: astore_1
    //   775: goto -341 -> 434
    //   778: aload 6
    //   780: invokevirtual 225	java/io/FileInputStream:close	()V
    //   783: aload_3
    //   784: invokevirtual 226	java/io/BufferedInputStream:close	()V
    //   787: goto +8 -> 795
    //   790: astore_0
    //   791: aload_0
    //   792: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   795: iload 11
    //   797: ifeq +27 -> 824
    //   800: aload 4
    //   802: iconst_3
    //   803: sipush 560
    //   806: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   809: aload_2
    //   810: invokestatic 245	com/tencent/common/utils/FileUtilsF:delete	(Ljava/io/File;)V
    //   813: goto +11 -> 824
    //   816: astore_0
    //   817: aload_0
    //   818: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   821: goto +3 -> 824
    //   824: aload 4
    //   826: iconst_3
    //   827: bipush 50
    //   829: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   832: aload 4
    //   834: iconst_3
    //   835: iconst_0
    //   836: invokestatic 224	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   839: sipush 6009
    //   842: ireturn
    //   843: goto +22 -> 865
    //   846: astore_0
    //   847: aload 15
    //   849: astore_1
    //   850: aload 12
    //   852: astore_3
    //   853: goto +670 -> 1523
    //   856: astore_0
    //   857: aload_3
    //   858: astore_1
    //   859: goto +670 -> 1529
    //   862: aload 12
    //   864: astore_3
    //   865: aload 6
    //   867: astore 12
    //   869: aload_3
    //   870: astore 13
    //   872: invokestatic 266	com/tencent/common/plugin/QBPluginServiceImpl:getInstance	()Lcom/tencent/common/plugin/QBPluginServiceImpl;
    //   875: aload 18
    //   877: invokevirtual 112	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   880: aload 4
    //   882: iload 5
    //   884: iload 8
    //   886: invokevirtual 270	com/tencent/common/plugin/QBPluginServiceImpl:addPluginInfoToLocalHashMap	(Ljava/lang/String;Ljava/lang/String;II)Z
    //   889: pop
    //   890: aload 6
    //   892: astore 12
    //   894: aload_3
    //   895: astore 13
    //   897: aload 18
    //   899: new 6	com/tencent/common/plugin/ZipPluginUtils$1
    //   902: dup
    //   903: invokespecial 271	com/tencent/common/plugin/ZipPluginUtils$1:<init>	()V
    //   906: invokevirtual 275	java/io/File:listFiles	(Ljava/io/FilenameFilter;)[Ljava/io/File;
    //   909: astore 14
    //   911: aload 6
    //   913: astore 12
    //   915: aload_3
    //   916: astore 13
    //   918: invokestatic 280	com/tencent/common/plugin/ZipPluginCheck:getInstance	()Lcom/tencent/common/plugin/ZipPluginCheck;
    //   921: pop
    //   922: aload 6
    //   924: astore 12
    //   926: aload_3
    //   927: astore 13
    //   929: aload 18
    //   931: invokevirtual 283	java/io/File:getAbsoluteFile	()Ljava/io/File;
    //   934: iload 5
    //   936: aload 4
    //   938: aload 14
    //   940: invokestatic 287	com/tencent/common/plugin/ZipPluginCheck:genCheckList	(Ljava/io/File;ILjava/lang/String;[Ljava/io/File;)Z
    //   943: ifne +174 -> 1117
    //   946: aload 6
    //   948: astore 12
    //   950: aload_3
    //   951: astore 13
    //   953: new 125	java/lang/StringBuilder
    //   956: dup
    //   957: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   960: astore_0
    //   961: aload 6
    //   963: astore 12
    //   965: aload_3
    //   966: astore 13
    //   968: aload_0
    //   969: ldc -49
    //   971: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   974: pop
    //   975: aload 6
    //   977: astore 12
    //   979: aload_3
    //   980: astore 13
    //   982: aload_0
    //   983: aload 4
    //   985: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   988: pop
    //   989: aload 6
    //   991: astore 12
    //   993: aload_3
    //   994: astore 13
    //   996: aload_0
    //   997: ldc_w 289
    //   1000: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1003: pop
    //   1004: aload 6
    //   1006: astore 12
    //   1008: aload_3
    //   1009: astore 13
    //   1011: ldc -45
    //   1013: aload_0
    //   1014: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1017: invokestatic 217	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   1020: aload 6
    //   1022: astore 12
    //   1024: aload_3
    //   1025: astore 13
    //   1027: aload 4
    //   1029: iconst_3
    //   1030: sipush 567
    //   1033: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   1036: aload 6
    //   1038: astore 12
    //   1040: aload_3
    //   1041: astore 13
    //   1043: aload 4
    //   1045: iconst_3
    //   1046: sipush 567
    //   1049: invokestatic 224	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   1052: aload 6
    //   1054: invokevirtual 225	java/io/FileInputStream:close	()V
    //   1057: aload_3
    //   1058: invokevirtual 226	java/io/BufferedInputStream:close	()V
    //   1061: goto +8 -> 1069
    //   1064: astore_0
    //   1065: aload_0
    //   1066: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   1069: iload 11
    //   1071: ifeq +27 -> 1098
    //   1074: aload 4
    //   1076: iconst_3
    //   1077: sipush 560
    //   1080: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   1083: aload_2
    //   1084: invokestatic 245	com/tencent/common/utils/FileUtilsF:delete	(Ljava/io/File;)V
    //   1087: goto +11 -> 1098
    //   1090: astore_0
    //   1091: aload_0
    //   1092: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   1095: goto +3 -> 1098
    //   1098: aload 4
    //   1100: iconst_3
    //   1101: bipush 50
    //   1103: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   1106: aload 4
    //   1108: iconst_3
    //   1109: iconst_0
    //   1110: invokestatic 224	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   1113: sipush 6010
    //   1116: ireturn
    //   1117: aload 6
    //   1119: astore 12
    //   1121: aload_3
    //   1122: astore 13
    //   1124: invokestatic 266	com/tencent/common/plugin/QBPluginServiceImpl:getInstance	()Lcom/tencent/common/plugin/QBPluginServiceImpl;
    //   1127: aload 4
    //   1129: iconst_1
    //   1130: iload 8
    //   1132: invokevirtual 293	com/tencent/common/plugin/QBPluginServiceImpl:setIsPluginInstall	(Ljava/lang/String;ZI)Z
    //   1135: pop
    //   1136: aload 6
    //   1138: astore 12
    //   1140: aload_3
    //   1141: astore 13
    //   1143: aload 7
    //   1145: iconst_1
    //   1146: putfield 299	com/tencent/common/plugin/QBPluginItemInfo:mIsInstall	I
    //   1149: aload 6
    //   1151: astore 12
    //   1153: aload_3
    //   1154: astore 13
    //   1156: aload_0
    //   1157: invokestatic 303	com/tencent/common/plugin/PluginFileUtils:getPluginDir	(Landroid/content/Context;)Ljava/io/File;
    //   1160: invokevirtual 112	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   1163: astore 14
    //   1165: aload 6
    //   1167: astore 12
    //   1169: aload_3
    //   1170: astore 13
    //   1172: aload 18
    //   1174: invokevirtual 112	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   1177: aload 14
    //   1179: invokevirtual 81	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   1182: ifne +113 -> 1295
    //   1185: aload 6
    //   1187: astore 12
    //   1189: aload_3
    //   1190: astore 13
    //   1192: aload_0
    //   1193: invokestatic 303	com/tencent/common/plugin/PluginFileUtils:getPluginDir	(Landroid/content/Context;)Ljava/io/File;
    //   1196: aload 4
    //   1198: invokestatic 306	com/tencent/common/utils/FileUtilsF:createDir	(Ljava/io/File;Ljava/lang/String;)Ljava/io/File;
    //   1201: astore_0
    //   1202: aload 6
    //   1204: astore 12
    //   1206: aload_3
    //   1207: astore 13
    //   1209: new 125	java/lang/StringBuilder
    //   1212: dup
    //   1213: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   1216: astore 14
    //   1218: aload 6
    //   1220: astore 12
    //   1222: aload_3
    //   1223: astore 13
    //   1225: aload 14
    //   1227: aload 4
    //   1229: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1232: pop
    //   1233: aload 6
    //   1235: astore 12
    //   1237: aload_3
    //   1238: astore 13
    //   1240: aload 14
    //   1242: ldc_w 308
    //   1245: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1248: pop
    //   1249: aload 6
    //   1251: astore 12
    //   1253: aload_3
    //   1254: astore 13
    //   1256: new 19	java/io/File
    //   1259: dup
    //   1260: aload_0
    //   1261: aload 14
    //   1263: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1266: invokespecial 87	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   1269: astore_0
    //   1270: aload 6
    //   1272: astore 12
    //   1274: aload_3
    //   1275: astore 13
    //   1277: aload_0
    //   1278: invokevirtual 231	java/io/File:exists	()Z
    //   1281: ifeq +14 -> 1295
    //   1284: aload 6
    //   1286: astore 12
    //   1288: aload_3
    //   1289: astore 13
    //   1291: aload_0
    //   1292: invokestatic 311	com/tencent/common/utils/FileUtilsF:deleteFileOnThread	(Ljava/io/File;)V
    //   1295: aload 6
    //   1297: astore 12
    //   1299: aload_3
    //   1300: astore 13
    //   1302: aload 7
    //   1304: aload 18
    //   1306: invokevirtual 112	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   1309: putfield 314	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   1312: aload 6
    //   1314: astore 12
    //   1316: aload_3
    //   1317: astore 13
    //   1319: aload 7
    //   1321: aload 18
    //   1323: invokevirtual 112	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   1326: putfield 317	com/tencent/common/plugin/QBPluginItemInfo:mUnzipDir	Ljava/lang/String;
    //   1329: aload 6
    //   1331: astore 12
    //   1333: aload_3
    //   1334: astore 13
    //   1336: new 125	java/lang/StringBuilder
    //   1339: dup
    //   1340: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   1343: astore_0
    //   1344: aload 6
    //   1346: astore 12
    //   1348: aload_3
    //   1349: astore 13
    //   1351: aload_0
    //   1352: iload 5
    //   1354: invokevirtual 320	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1357: pop
    //   1358: aload 6
    //   1360: astore 12
    //   1362: aload_3
    //   1363: astore 13
    //   1365: aload_0
    //   1366: ldc_w 322
    //   1369: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1372: pop
    //   1373: aload 6
    //   1375: astore 12
    //   1377: aload_3
    //   1378: astore 13
    //   1380: aload 7
    //   1382: aload_0
    //   1383: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1386: putfield 325	com/tencent/common/plugin/QBPluginItemInfo:mInstallVersion	Ljava/lang/String;
    //   1389: aload 6
    //   1391: astore 12
    //   1393: aload_3
    //   1394: astore 13
    //   1396: aload_1
    //   1397: invokestatic 330	com/tencent/common/plugin/QBPluginDBHelper:getInstance	(Landroid/content/Context;)Lcom/tencent/common/plugin/QBPluginDBHelper;
    //   1400: aload 4
    //   1402: aload 7
    //   1404: getfield 314	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   1407: iload 8
    //   1409: invokevirtual 334	com/tencent/common/plugin/QBPluginDBHelper:updatePluginInstallDir	(Ljava/lang/String;Ljava/lang/String;I)V
    //   1412: aload 6
    //   1414: astore 12
    //   1416: aload_3
    //   1417: astore 13
    //   1419: aload_1
    //   1420: invokestatic 330	com/tencent/common/plugin/QBPluginDBHelper:getInstance	(Landroid/content/Context;)Lcom/tencent/common/plugin/QBPluginDBHelper;
    //   1423: aload 4
    //   1425: aload 7
    //   1427: getfield 317	com/tencent/common/plugin/QBPluginItemInfo:mUnzipDir	Ljava/lang/String;
    //   1430: iload 8
    //   1432: invokevirtual 337	com/tencent/common/plugin/QBPluginDBHelper:updatePluginUnzipDir	(Ljava/lang/String;Ljava/lang/String;I)V
    //   1435: aload 6
    //   1437: astore 12
    //   1439: aload_3
    //   1440: astore 13
    //   1442: aload 4
    //   1444: iconst_3
    //   1445: aload 7
    //   1447: getfield 314	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   1450: invokestatic 340	com/tencent/common/plugin/PluginStatBehavior:setInstallDir	(Ljava/lang/String;ILjava/lang/String;)V
    //   1453: aload 6
    //   1455: invokevirtual 225	java/io/FileInputStream:close	()V
    //   1458: aload_3
    //   1459: invokevirtual 226	java/io/BufferedInputStream:close	()V
    //   1462: goto +8 -> 1470
    //   1465: astore_0
    //   1466: aload_0
    //   1467: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   1470: iload 11
    //   1472: ifeq +27 -> 1499
    //   1475: aload 4
    //   1477: iconst_3
    //   1478: sipush 560
    //   1481: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   1484: aload_2
    //   1485: invokestatic 245	com/tencent/common/utils/FileUtilsF:delete	(Ljava/io/File;)V
    //   1488: goto +11 -> 1499
    //   1491: astore_0
    //   1492: aload_0
    //   1493: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   1496: goto +3 -> 1499
    //   1499: aload 4
    //   1501: iconst_3
    //   1502: bipush 50
    //   1504: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   1507: aload 4
    //   1509: iconst_3
    //   1510: iconst_0
    //   1511: invokestatic 224	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   1514: iconst_0
    //   1515: ireturn
    //   1516: astore_0
    //   1517: aload 13
    //   1519: astore_3
    //   1520: aload 12
    //   1522: astore_1
    //   1523: goto +198 -> 1721
    //   1526: astore_0
    //   1527: aload_3
    //   1528: astore_1
    //   1529: aload_0
    //   1530: astore_3
    //   1531: aload 6
    //   1533: astore_0
    //   1534: goto +85 -> 1619
    //   1537: astore_0
    //   1538: aload 15
    //   1540: astore_1
    //   1541: aload 14
    //   1543: astore 12
    //   1545: aload 12
    //   1547: astore_3
    //   1548: goto +173 -> 1721
    //   1551: astore_3
    //   1552: aload 16
    //   1554: astore_0
    //   1555: aload 13
    //   1557: astore_1
    //   1558: goto +61 -> 1619
    //   1561: astore_0
    //   1562: aload 6
    //   1564: astore_1
    //   1565: aload 12
    //   1567: astore_3
    //   1568: goto +36 -> 1604
    //   1571: astore_3
    //   1572: aload 6
    //   1574: astore_0
    //   1575: aload 12
    //   1577: astore_1
    //   1578: goto +38 -> 1616
    //   1581: astore_0
    //   1582: aconst_null
    //   1583: astore_3
    //   1584: aload 6
    //   1586: astore_1
    //   1587: goto +17 -> 1604
    //   1590: astore_3
    //   1591: aconst_null
    //   1592: astore_1
    //   1593: aload 6
    //   1595: astore_0
    //   1596: goto +20 -> 1616
    //   1599: astore_0
    //   1600: aconst_null
    //   1601: astore_1
    //   1602: aload_1
    //   1603: astore_3
    //   1604: iconst_0
    //   1605: istore 11
    //   1607: goto +114 -> 1721
    //   1610: astore_3
    //   1611: aconst_null
    //   1612: astore_1
    //   1613: aload 12
    //   1615: astore_0
    //   1616: iconst_0
    //   1617: istore 11
    //   1619: aload 4
    //   1621: iconst_3
    //   1622: aload_3
    //   1623: invokevirtual 189	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
    //   1626: invokestatic 235	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;ILjava/lang/String;)V
    //   1629: aload 4
    //   1631: iconst_3
    //   1632: sipush 513
    //   1635: invokestatic 224	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   1638: aload_0
    //   1639: ifnull +10 -> 1649
    //   1642: aload_0
    //   1643: invokevirtual 225	java/io/FileInputStream:close	()V
    //   1646: goto +3 -> 1649
    //   1649: aload_1
    //   1650: ifnull +14 -> 1664
    //   1653: aload_1
    //   1654: invokevirtual 226	java/io/BufferedInputStream:close	()V
    //   1657: goto +7 -> 1664
    //   1660: aload_0
    //   1661: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   1664: iload 11
    //   1666: ifeq +27 -> 1693
    //   1669: aload 4
    //   1671: iconst_3
    //   1672: sipush 560
    //   1675: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   1678: aload_2
    //   1679: invokestatic 245	com/tencent/common/utils/FileUtilsF:delete	(Ljava/io/File;)V
    //   1682: goto +11 -> 1693
    //   1685: astore_0
    //   1686: aload_0
    //   1687: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   1690: goto +3 -> 1693
    //   1693: aload 4
    //   1695: iconst_3
    //   1696: bipush 50
    //   1698: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   1701: aload 4
    //   1703: iconst_3
    //   1704: iconst_0
    //   1705: invokestatic 224	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   1708: sipush 6009
    //   1711: ireturn
    //   1712: astore 6
    //   1714: aload_1
    //   1715: astore_3
    //   1716: aload_0
    //   1717: astore_1
    //   1718: aload 6
    //   1720: astore_0
    //   1721: aload_1
    //   1722: ifnull +10 -> 1732
    //   1725: aload_1
    //   1726: invokevirtual 225	java/io/FileInputStream:close	()V
    //   1729: goto +3 -> 1732
    //   1732: aload_3
    //   1733: ifnull +14 -> 1747
    //   1736: aload_3
    //   1737: invokevirtual 226	java/io/BufferedInputStream:close	()V
    //   1740: goto +7 -> 1747
    //   1743: aload_1
    //   1744: invokevirtual 117	java/io/IOException:printStackTrace	()V
    //   1747: iload 11
    //   1749: ifeq +27 -> 1776
    //   1752: aload 4
    //   1754: iconst_3
    //   1755: sipush 560
    //   1758: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   1761: aload_2
    //   1762: invokestatic 245	com/tencent/common/utils/FileUtilsF:delete	(Ljava/io/File;)V
    //   1765: goto +11 -> 1776
    //   1768: astore_1
    //   1769: aload_1
    //   1770: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   1773: goto +3 -> 1776
    //   1776: aload 4
    //   1778: iconst_3
    //   1779: bipush 50
    //   1781: invokestatic 221	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   1784: aload 4
    //   1786: iconst_3
    //   1787: iconst_0
    //   1788: invokestatic 224	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   1791: aload_0
    //   1792: athrow
    //   1793: astore_0
    //   1794: goto -134 -> 1660
    //   1797: astore_1
    //   1798: goto -55 -> 1743
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1801	0	paramContext1	android.content.Context
    //   0	1801	1	paramContext2	android.content.Context
    //   0	1801	2	paramFile	File
    //   0	1801	3	paramString1	String
    //   0	1801	4	paramString2	String
    //   0	1801	5	paramInt1	int
    //   0	1801	6	paramString3	String
    //   0	1801	7	paramQBPluginItemInfo	QBPluginItemInfo
    //   0	1801	8	paramInt2	int
    //   191	504	9	i	int
    //   382	38	10	j	int
    //   139	1609	11	bool	boolean
    //   1	1613	12	localObject1	Object
    //   149	1407	13	localObject2	Object
    //   171	1371	14	localObject3	Object
    //   167	1372	15	localObject4	Object
    //   175	1378	16	localObject5	Object
    //   362	362	17	localObject6	Object
    //   31	1291	18	localFile	File
    // Exception table:
    //   from	to	target	type
    //   94	104	107	java/io/IOException
    //   38	94	131	java/io/FileNotFoundException
    //   372	384	426	java/io/FileNotFoundException
    //   404	412	426	java/io/FileNotFoundException
    //   732	740	426	java/io/FileNotFoundException
    //   372	384	442	java/lang/Throwable
    //   404	412	442	java/lang/Throwable
    //   345	356	454	finally
    //   345	356	461	java/io/FileNotFoundException
    //   345	356	468	java/lang/Throwable
    //   230	235	476	java/io/FileNotFoundException
    //   243	248	476	java/io/FileNotFoundException
    //   264	272	476	java/io/FileNotFoundException
    //   280	298	476	java/io/FileNotFoundException
    //   306	314	476	java/io/FileNotFoundException
    //   322	327	476	java/io/FileNotFoundException
    //   335	345	476	java/io/FileNotFoundException
    //   230	235	486	java/lang/Throwable
    //   243	248	486	java/lang/Throwable
    //   264	272	486	java/lang/Throwable
    //   280	298	486	java/lang/Throwable
    //   306	314	486	java/lang/Throwable
    //   322	327	486	java/lang/Throwable
    //   335	345	486	java/lang/Throwable
    //   616	625	628	java/io/IOException
    //   647	651	654	java/lang/Exception
    //   747	753	772	java/io/FileNotFoundException
    //   760	769	772	java/io/FileNotFoundException
    //   778	787	790	java/io/IOException
    //   809	813	816	java/lang/Exception
    //   372	384	846	finally
    //   404	412	846	finally
    //   517	526	846	finally
    //   533	541	846	finally
    //   548	556	846	finally
    //   563	571	846	finally
    //   578	588	846	finally
    //   600	608	846	finally
    //   710	714	846	finally
    //   732	740	846	finally
    //   517	526	856	java/io/FileNotFoundException
    //   533	541	856	java/io/FileNotFoundException
    //   548	556	856	java/io/FileNotFoundException
    //   563	571	856	java/io/FileNotFoundException
    //   578	588	856	java/io/FileNotFoundException
    //   600	608	856	java/io/FileNotFoundException
    //   710	714	856	java/io/FileNotFoundException
    //   1052	1061	1064	java/io/IOException
    //   1083	1087	1090	java/lang/Exception
    //   1453	1462	1465	java/io/IOException
    //   1484	1488	1491	java/lang/Exception
    //   747	753	1516	finally
    //   760	769	1516	finally
    //   872	890	1516	finally
    //   897	911	1516	finally
    //   918	922	1516	finally
    //   929	946	1516	finally
    //   953	961	1516	finally
    //   968	975	1516	finally
    //   982	989	1516	finally
    //   996	1004	1516	finally
    //   1011	1020	1516	finally
    //   1027	1036	1516	finally
    //   1043	1052	1516	finally
    //   1124	1136	1516	finally
    //   1143	1149	1516	finally
    //   1156	1165	1516	finally
    //   1172	1185	1516	finally
    //   1192	1202	1516	finally
    //   1209	1218	1516	finally
    //   1225	1233	1516	finally
    //   1240	1249	1516	finally
    //   1256	1270	1516	finally
    //   1277	1284	1516	finally
    //   1291	1295	1516	finally
    //   1302	1312	1516	finally
    //   1319	1329	1516	finally
    //   1336	1344	1516	finally
    //   1351	1358	1516	finally
    //   1365	1373	1516	finally
    //   1380	1389	1516	finally
    //   1396	1412	1516	finally
    //   1419	1435	1516	finally
    //   1442	1453	1516	finally
    //   872	890	1526	java/io/FileNotFoundException
    //   897	911	1526	java/io/FileNotFoundException
    //   918	922	1526	java/io/FileNotFoundException
    //   929	946	1526	java/io/FileNotFoundException
    //   953	961	1526	java/io/FileNotFoundException
    //   968	975	1526	java/io/FileNotFoundException
    //   982	989	1526	java/io/FileNotFoundException
    //   996	1004	1526	java/io/FileNotFoundException
    //   1011	1020	1526	java/io/FileNotFoundException
    //   1027	1036	1526	java/io/FileNotFoundException
    //   1043	1052	1526	java/io/FileNotFoundException
    //   1124	1136	1526	java/io/FileNotFoundException
    //   1143	1149	1526	java/io/FileNotFoundException
    //   1156	1165	1526	java/io/FileNotFoundException
    //   1172	1185	1526	java/io/FileNotFoundException
    //   1192	1202	1526	java/io/FileNotFoundException
    //   1209	1218	1526	java/io/FileNotFoundException
    //   1225	1233	1526	java/io/FileNotFoundException
    //   1240	1249	1526	java/io/FileNotFoundException
    //   1256	1270	1526	java/io/FileNotFoundException
    //   1277	1284	1526	java/io/FileNotFoundException
    //   1291	1295	1526	java/io/FileNotFoundException
    //   1302	1312	1526	java/io/FileNotFoundException
    //   1319	1329	1526	java/io/FileNotFoundException
    //   1336	1344	1526	java/io/FileNotFoundException
    //   1351	1358	1526	java/io/FileNotFoundException
    //   1365	1373	1526	java/io/FileNotFoundException
    //   1380	1389	1526	java/io/FileNotFoundException
    //   1396	1412	1526	java/io/FileNotFoundException
    //   1419	1435	1526	java/io/FileNotFoundException
    //   1442	1453	1526	java/io/FileNotFoundException
    //   181	193	1537	finally
    //   214	222	1537	finally
    //   230	235	1537	finally
    //   243	248	1537	finally
    //   264	272	1537	finally
    //   280	298	1537	finally
    //   306	314	1537	finally
    //   322	327	1537	finally
    //   335	345	1537	finally
    //   503	507	1537	finally
    //   181	193	1551	java/io/FileNotFoundException
    //   214	222	1551	java/io/FileNotFoundException
    //   503	507	1551	java/io/FileNotFoundException
    //   24	33	1561	finally
    //   38	94	1561	finally
    //   144	165	1561	finally
    //   24	33	1571	java/io/FileNotFoundException
    //   144	165	1571	java/io/FileNotFoundException
    //   13	24	1581	finally
    //   13	24	1590	java/io/FileNotFoundException
    //   3	13	1599	finally
    //   3	13	1610	java/io/FileNotFoundException
    //   1678	1682	1685	java/lang/Exception
    //   1619	1638	1712	finally
    //   1761	1765	1768	java/lang/Exception
    //   1642	1646	1793	java/io/IOException
    //   1653	1657	1793	java/io/IOException
    //   1725	1729	1797	java/io/IOException
    //   1736	1740	1797	java/io/IOException
  }
  
  public static boolean isAcceptPluginFile(String paramString)
  {
    return (paramString.endsWith(".so")) || (paramString.endsWith(".jar")) || (paramString.endsWith(".dat")) || (paramString.endsWith(".apk"));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\ZipPluginUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */