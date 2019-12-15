package com.tencent.common.connectivitydetect;

import com.tencent.common.http.Apn;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectivityDetector
{
  private static final byte[] a = "9a6f75849b".getBytes();
  
  private static boolean a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if ((paramArrayOfByte1 == null) && (paramArrayOfByte2 == null)) {
      return true;
    }
    if ((paramArrayOfByte1 == null) && (paramArrayOfByte2 != null)) {
      return false;
    }
    if ((paramArrayOfByte1 != null) && (paramArrayOfByte2 == null)) {
      return false;
    }
    if (paramArrayOfByte1.length != paramArrayOfByte2.length) {
      return false;
    }
    int i = 0;
    while (i < paramArrayOfByte1.length)
    {
      if (paramArrayOfByte1[i] != paramArrayOfByte2[i]) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  public static boolean checkNetworkConnectivity()
  {
    if (!Apn.isNetworkConnected()) {
      return false;
    }
    if (Apn.isWifiMode()) {
      return detectWithCDNFile();
    }
    return detectWithTCPPing();
  }
  
  /* Error */
  public static boolean detectWithCDNFile()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 7
    //   6: aconst_null
    //   7: astore_1
    //   8: aconst_null
    //   9: astore 6
    //   11: new 45	java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   18: astore_2
    //   19: aload_2
    //   20: ldc 48
    //   22: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: pop
    //   26: aload_2
    //   27: invokestatic 58	java/lang/System:currentTimeMillis	()J
    //   30: invokevirtual 61	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   33: pop
    //   34: new 63	java/net/URL
    //   37: dup
    //   38: aload_2
    //   39: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   42: invokespecial 70	java/net/URL:<init>	(Ljava/lang/String;)V
    //   45: invokevirtual 74	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   48: checkcast 76	java/net/HttpURLConnection
    //   51: astore 5
    //   53: aload 6
    //   55: astore_3
    //   56: aload 7
    //   58: astore_1
    //   59: aload 5
    //   61: astore_2
    //   62: aload 5
    //   64: iconst_0
    //   65: invokevirtual 80	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   68: aload 6
    //   70: astore_3
    //   71: aload 7
    //   73: astore_1
    //   74: aload 5
    //   76: astore_2
    //   77: aload 5
    //   79: sipush 5000
    //   82: invokevirtual 84	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   85: aload 6
    //   87: astore_3
    //   88: aload 7
    //   90: astore_1
    //   91: aload 5
    //   93: astore_2
    //   94: aload 5
    //   96: sipush 5000
    //   99: invokevirtual 87	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   102: aload 6
    //   104: astore_3
    //   105: aload 7
    //   107: astore_1
    //   108: aload 5
    //   110: astore_2
    //   111: aload 5
    //   113: iconst_0
    //   114: invokevirtual 90	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   117: aload 6
    //   119: astore_3
    //   120: aload 7
    //   122: astore_1
    //   123: aload 5
    //   125: astore_2
    //   126: aload 5
    //   128: ldc 92
    //   130: invokevirtual 95	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   133: aload 6
    //   135: astore_3
    //   136: aload 7
    //   138: astore_1
    //   139: aload 5
    //   141: astore_2
    //   142: aload 5
    //   144: ldc 97
    //   146: ldc 99
    //   148: invokevirtual 103	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   151: aload 6
    //   153: astore_3
    //   154: aload 7
    //   156: astore_1
    //   157: aload 5
    //   159: astore_2
    //   160: aload 5
    //   162: ldc 105
    //   164: ldc 107
    //   166: invokevirtual 103	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   169: aload 6
    //   171: astore_3
    //   172: aload 7
    //   174: astore_1
    //   175: aload 5
    //   177: astore_2
    //   178: aload 5
    //   180: invokevirtual 110	java/net/HttpURLConnection:connect	()V
    //   183: aload 6
    //   185: astore_3
    //   186: aload 7
    //   188: astore_1
    //   189: aload 5
    //   191: astore_2
    //   192: aload 5
    //   194: invokevirtual 114	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   197: astore 4
    //   199: aload 4
    //   201: astore_3
    //   202: aload 4
    //   204: astore_1
    //   205: aload 5
    //   207: astore_2
    //   208: aload 5
    //   210: invokevirtual 118	java/net/HttpURLConnection:getResponseCode	()I
    //   213: istore_0
    //   214: aload 4
    //   216: astore_3
    //   217: aload 4
    //   219: astore_1
    //   220: aload 5
    //   222: astore_2
    //   223: new 45	java/lang/StringBuilder
    //   226: dup
    //   227: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   230: astore 6
    //   232: aload 4
    //   234: astore_3
    //   235: aload 4
    //   237: astore_1
    //   238: aload 5
    //   240: astore_2
    //   241: aload 6
    //   243: ldc 120
    //   245: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   248: pop
    //   249: aload 4
    //   251: astore_3
    //   252: aload 4
    //   254: astore_1
    //   255: aload 5
    //   257: astore_2
    //   258: aload 6
    //   260: iload_0
    //   261: invokevirtual 123	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   264: pop
    //   265: aload 4
    //   267: astore_3
    //   268: aload 4
    //   270: astore_1
    //   271: aload 5
    //   273: astore_2
    //   274: ldc 125
    //   276: aload 6
    //   278: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   281: invokestatic 130	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   284: iload_0
    //   285: sipush 200
    //   288: if_icmpeq +56 -> 344
    //   291: aload 4
    //   293: astore_3
    //   294: aload 4
    //   296: astore_1
    //   297: aload 5
    //   299: astore_2
    //   300: ldc 125
    //   302: ldc -124
    //   304: invokestatic 130	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   307: aload 4
    //   309: ifnull +16 -> 325
    //   312: aload 4
    //   314: invokevirtual 136	java/io/InputStream:close	()V
    //   317: goto +8 -> 325
    //   320: astore_1
    //   321: aload_1
    //   322: invokevirtual 139	java/lang/Exception:printStackTrace	()V
    //   325: aload 5
    //   327: ifnull +15 -> 342
    //   330: aload 5
    //   332: invokevirtual 142	java/net/HttpURLConnection:disconnect	()V
    //   335: iconst_0
    //   336: ireturn
    //   337: astore_1
    //   338: aload_1
    //   339: invokevirtual 139	java/lang/Exception:printStackTrace	()V
    //   342: iconst_0
    //   343: ireturn
    //   344: aload 4
    //   346: astore_3
    //   347: aload 4
    //   349: astore_1
    //   350: aload 5
    //   352: astore_2
    //   353: getstatic 18	com/tencent/common/connectivitydetect/ConnectivityDetector:a	[B
    //   356: arraylength
    //   357: newarray <illegal type>
    //   359: astore 6
    //   361: aload 4
    //   363: astore_3
    //   364: aload 4
    //   366: astore_1
    //   367: aload 5
    //   369: astore_2
    //   370: aload 4
    //   372: aload 6
    //   374: invokevirtual 146	java/io/InputStream:read	([B)I
    //   377: aload 6
    //   379: arraylength
    //   380: if_icmpne +141 -> 521
    //   383: aload 4
    //   385: astore_3
    //   386: aload 4
    //   388: astore_1
    //   389: aload 5
    //   391: astore_2
    //   392: aload 6
    //   394: getstatic 18	com/tencent/common/connectivitydetect/ConnectivityDetector:a	[B
    //   397: invokestatic 148	com/tencent/common/connectivitydetect/ConnectivityDetector:a	([B[B)Z
    //   400: ifne +6 -> 406
    //   403: goto +118 -> 521
    //   406: aload 4
    //   408: astore_3
    //   409: aload 4
    //   411: astore_1
    //   412: aload 5
    //   414: astore_2
    //   415: new 45	java/lang/StringBuilder
    //   418: dup
    //   419: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   422: astore 7
    //   424: aload 4
    //   426: astore_3
    //   427: aload 4
    //   429: astore_1
    //   430: aload 5
    //   432: astore_2
    //   433: aload 7
    //   435: ldc -106
    //   437: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   440: pop
    //   441: aload 4
    //   443: astore_3
    //   444: aload 4
    //   446: astore_1
    //   447: aload 5
    //   449: astore_2
    //   450: aload 7
    //   452: new 12	java/lang/String
    //   455: dup
    //   456: aload 6
    //   458: invokespecial 153	java/lang/String:<init>	([B)V
    //   461: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   464: pop
    //   465: aload 4
    //   467: astore_3
    //   468: aload 4
    //   470: astore_1
    //   471: aload 5
    //   473: astore_2
    //   474: ldc 125
    //   476: aload 7
    //   478: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   481: invokestatic 130	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   484: aload 4
    //   486: ifnull +16 -> 502
    //   489: aload 4
    //   491: invokevirtual 136	java/io/InputStream:close	()V
    //   494: goto +8 -> 502
    //   497: astore_1
    //   498: aload_1
    //   499: invokevirtual 139	java/lang/Exception:printStackTrace	()V
    //   502: aload 5
    //   504: ifnull +15 -> 519
    //   507: aload 5
    //   509: invokevirtual 142	java/net/HttpURLConnection:disconnect	()V
    //   512: iconst_1
    //   513: ireturn
    //   514: astore_1
    //   515: aload_1
    //   516: invokevirtual 139	java/lang/Exception:printStackTrace	()V
    //   519: iconst_1
    //   520: ireturn
    //   521: aload 4
    //   523: astore_3
    //   524: aload 4
    //   526: astore_1
    //   527: aload 5
    //   529: astore_2
    //   530: new 45	java/lang/StringBuilder
    //   533: dup
    //   534: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   537: astore 7
    //   539: aload 4
    //   541: astore_3
    //   542: aload 4
    //   544: astore_1
    //   545: aload 5
    //   547: astore_2
    //   548: aload 7
    //   550: ldc -101
    //   552: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   555: pop
    //   556: aload 4
    //   558: astore_3
    //   559: aload 4
    //   561: astore_1
    //   562: aload 5
    //   564: astore_2
    //   565: aload 7
    //   567: new 12	java/lang/String
    //   570: dup
    //   571: aload 6
    //   573: invokespecial 153	java/lang/String:<init>	([B)V
    //   576: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   579: pop
    //   580: aload 4
    //   582: astore_3
    //   583: aload 4
    //   585: astore_1
    //   586: aload 5
    //   588: astore_2
    //   589: ldc 125
    //   591: aload 7
    //   593: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   596: invokestatic 130	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   599: aload 4
    //   601: ifnull +16 -> 617
    //   604: aload 4
    //   606: invokevirtual 136	java/io/InputStream:close	()V
    //   609: goto +8 -> 617
    //   612: astore_1
    //   613: aload_1
    //   614: invokevirtual 139	java/lang/Exception:printStackTrace	()V
    //   617: aload 5
    //   619: ifnull +15 -> 634
    //   622: aload 5
    //   624: invokevirtual 142	java/net/HttpURLConnection:disconnect	()V
    //   627: iconst_0
    //   628: ireturn
    //   629: astore_1
    //   630: aload_1
    //   631: invokevirtual 139	java/lang/Exception:printStackTrace	()V
    //   634: iconst_0
    //   635: ireturn
    //   636: astore_1
    //   637: aload_3
    //   638: astore 4
    //   640: aload 5
    //   642: astore_3
    //   643: aload_1
    //   644: astore 5
    //   646: goto +13 -> 659
    //   649: astore_2
    //   650: aconst_null
    //   651: astore_3
    //   652: goto +59 -> 711
    //   655: astore 5
    //   657: aconst_null
    //   658: astore_3
    //   659: aload 4
    //   661: astore_1
    //   662: aload_3
    //   663: astore_2
    //   664: aload 5
    //   666: invokevirtual 156	java/lang/Throwable:printStackTrace	()V
    //   669: aload 4
    //   671: ifnull +16 -> 687
    //   674: aload 4
    //   676: invokevirtual 136	java/io/InputStream:close	()V
    //   679: goto +8 -> 687
    //   682: astore_1
    //   683: aload_1
    //   684: invokevirtual 139	java/lang/Exception:printStackTrace	()V
    //   687: aload_3
    //   688: ifnull +14 -> 702
    //   691: aload_3
    //   692: invokevirtual 142	java/net/HttpURLConnection:disconnect	()V
    //   695: iconst_0
    //   696: ireturn
    //   697: astore_1
    //   698: aload_1
    //   699: invokevirtual 139	java/lang/Exception:printStackTrace	()V
    //   702: iconst_0
    //   703: ireturn
    //   704: astore 4
    //   706: aload_2
    //   707: astore_3
    //   708: aload 4
    //   710: astore_2
    //   711: aload_1
    //   712: ifnull +15 -> 727
    //   715: aload_1
    //   716: invokevirtual 136	java/io/InputStream:close	()V
    //   719: goto +8 -> 727
    //   722: astore_1
    //   723: aload_1
    //   724: invokevirtual 139	java/lang/Exception:printStackTrace	()V
    //   727: aload_3
    //   728: ifnull +15 -> 743
    //   731: aload_3
    //   732: invokevirtual 142	java/net/HttpURLConnection:disconnect	()V
    //   735: goto +8 -> 743
    //   738: astore_1
    //   739: aload_1
    //   740: invokevirtual 139	java/lang/Exception:printStackTrace	()V
    //   743: aload_2
    //   744: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   213	76	0	i	int
    //   7	290	1	localObject1	Object
    //   320	2	1	localException1	Exception
    //   337	2	1	localException2	Exception
    //   349	122	1	localObject2	Object
    //   497	2	1	localException3	Exception
    //   514	2	1	localException4	Exception
    //   526	60	1	localObject3	Object
    //   612	2	1	localException5	Exception
    //   629	2	1	localException6	Exception
    //   636	8	1	localThrowable1	Throwable
    //   661	1	1	localObject4	Object
    //   682	2	1	localException7	Exception
    //   697	19	1	localException8	Exception
    //   722	2	1	localException9	Exception
    //   738	2	1	localException10	Exception
    //   18	571	2	localObject5	Object
    //   649	1	2	localObject6	Object
    //   663	81	2	localObject7	Object
    //   55	677	3	localObject8	Object
    //   1	674	4	localObject9	Object
    //   704	5	4	localObject10	Object
    //   51	594	5	localObject11	Object
    //   655	10	5	localThrowable2	Throwable
    //   9	563	6	localObject12	Object
    //   4	588	7	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   312	317	320	java/lang/Exception
    //   330	335	337	java/lang/Exception
    //   489	494	497	java/lang/Exception
    //   507	512	514	java/lang/Exception
    //   604	609	612	java/lang/Exception
    //   622	627	629	java/lang/Exception
    //   62	68	636	java/lang/Throwable
    //   77	85	636	java/lang/Throwable
    //   94	102	636	java/lang/Throwable
    //   111	117	636	java/lang/Throwable
    //   126	133	636	java/lang/Throwable
    //   142	151	636	java/lang/Throwable
    //   160	169	636	java/lang/Throwable
    //   178	183	636	java/lang/Throwable
    //   192	199	636	java/lang/Throwable
    //   208	214	636	java/lang/Throwable
    //   223	232	636	java/lang/Throwable
    //   241	249	636	java/lang/Throwable
    //   258	265	636	java/lang/Throwable
    //   274	284	636	java/lang/Throwable
    //   300	307	636	java/lang/Throwable
    //   353	361	636	java/lang/Throwable
    //   370	383	636	java/lang/Throwable
    //   392	403	636	java/lang/Throwable
    //   415	424	636	java/lang/Throwable
    //   433	441	636	java/lang/Throwable
    //   450	465	636	java/lang/Throwable
    //   474	484	636	java/lang/Throwable
    //   530	539	636	java/lang/Throwable
    //   548	556	636	java/lang/Throwable
    //   565	580	636	java/lang/Throwable
    //   589	599	636	java/lang/Throwable
    //   11	53	649	finally
    //   11	53	655	java/lang/Throwable
    //   674	679	682	java/lang/Exception
    //   691	695	697	java/lang/Exception
    //   62	68	704	finally
    //   77	85	704	finally
    //   94	102	704	finally
    //   111	117	704	finally
    //   126	133	704	finally
    //   142	151	704	finally
    //   160	169	704	finally
    //   178	183	704	finally
    //   192	199	704	finally
    //   208	214	704	finally
    //   223	232	704	finally
    //   241	249	704	finally
    //   258	265	704	finally
    //   274	284	704	finally
    //   300	307	704	finally
    //   353	361	704	finally
    //   370	383	704	finally
    //   392	403	704	finally
    //   415	424	704	finally
    //   433	441	704	finally
    //   450	465	704	finally
    //   474	484	704	finally
    //   530	539	704	finally
    //   548	556	704	finally
    //   565	580	704	finally
    //   589	599	704	finally
    //   664	669	704	finally
    //   715	719	722	java/lang/Exception
    //   731	735	738	java/lang/Exception
  }
  
  /* Error */
  public static boolean detectWithRsp204()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 4
    //   3: iconst_0
    //   4: istore_3
    //   5: iconst_0
    //   6: istore_1
    //   7: new 63	java/net/URL
    //   10: dup
    //   11: ldc -97
    //   13: invokespecial 70	java/net/URL:<init>	(Ljava/lang/String;)V
    //   16: invokevirtual 74	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   19: checkcast 76	java/net/HttpURLConnection
    //   22: astore 6
    //   24: aload 6
    //   26: astore 5
    //   28: aload 6
    //   30: iconst_0
    //   31: invokevirtual 80	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   34: aload 6
    //   36: astore 5
    //   38: aload 6
    //   40: sipush 5000
    //   43: invokevirtual 84	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   46: aload 6
    //   48: astore 5
    //   50: aload 6
    //   52: sipush 5000
    //   55: invokevirtual 87	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   58: aload 6
    //   60: astore 5
    //   62: aload 6
    //   64: iconst_0
    //   65: invokevirtual 90	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   68: aload 6
    //   70: astore 5
    //   72: aload 6
    //   74: ldc 92
    //   76: invokevirtual 95	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   79: aload 6
    //   81: astore 5
    //   83: aload 6
    //   85: ldc 97
    //   87: ldc 99
    //   89: invokevirtual 103	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   92: aload 6
    //   94: astore 5
    //   96: aload 6
    //   98: ldc 105
    //   100: ldc 107
    //   102: invokevirtual 103	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   105: aload 6
    //   107: astore 5
    //   109: aload 6
    //   111: invokevirtual 110	java/net/HttpURLConnection:connect	()V
    //   114: aload 6
    //   116: astore 5
    //   118: aload 6
    //   120: invokevirtual 118	java/net/HttpURLConnection:getResponseCode	()I
    //   123: istore_0
    //   124: aload 6
    //   126: astore 5
    //   128: new 45	java/lang/StringBuilder
    //   131: dup
    //   132: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   135: astore 7
    //   137: aload 6
    //   139: astore 5
    //   141: aload 7
    //   143: ldc -95
    //   145: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   148: pop
    //   149: aload 6
    //   151: astore 5
    //   153: aload 7
    //   155: iload_0
    //   156: invokevirtual 123	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   159: pop
    //   160: aload 6
    //   162: astore 5
    //   164: ldc 125
    //   166: aload 7
    //   168: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   171: invokestatic 130	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   174: iload_0
    //   175: sipush 204
    //   178: if_icmpne +16 -> 194
    //   181: aload 6
    //   183: astore 5
    //   185: ldc 125
    //   187: ldc -93
    //   189: invokestatic 130	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   192: iconst_1
    //   193: istore_1
    //   194: iload_1
    //   195: istore_2
    //   196: aload 6
    //   198: ifnull +65 -> 263
    //   201: iload_1
    //   202: istore_2
    //   203: aload 6
    //   205: invokevirtual 142	java/net/HttpURLConnection:disconnect	()V
    //   208: iload_1
    //   209: ireturn
    //   210: astore 5
    //   212: aload 5
    //   214: invokevirtual 156	java/lang/Throwable:printStackTrace	()V
    //   217: iload_2
    //   218: ireturn
    //   219: astore 7
    //   221: goto +16 -> 237
    //   224: astore 5
    //   226: aconst_null
    //   227: astore 6
    //   229: goto +46 -> 275
    //   232: astore 7
    //   234: aconst_null
    //   235: astore 6
    //   237: aload 6
    //   239: astore 5
    //   241: aload 7
    //   243: invokevirtual 156	java/lang/Throwable:printStackTrace	()V
    //   246: iload_3
    //   247: istore_2
    //   248: aload 6
    //   250: ifnull +13 -> 263
    //   253: iload 4
    //   255: istore_2
    //   256: aload 6
    //   258: invokevirtual 142	java/net/HttpURLConnection:disconnect	()V
    //   261: iload_3
    //   262: istore_2
    //   263: iload_2
    //   264: ireturn
    //   265: astore 7
    //   267: aload 5
    //   269: astore 6
    //   271: aload 7
    //   273: astore 5
    //   275: aload 6
    //   277: ifnull +18 -> 295
    //   280: aload 6
    //   282: invokevirtual 142	java/net/HttpURLConnection:disconnect	()V
    //   285: goto +10 -> 295
    //   288: astore 6
    //   290: aload 6
    //   292: invokevirtual 156	java/lang/Throwable:printStackTrace	()V
    //   295: aload 5
    //   297: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   123	56	0	i	int
    //   6	203	1	bool1	boolean
    //   195	69	2	bool2	boolean
    //   4	258	3	bool3	boolean
    //   1	253	4	bool4	boolean
    //   26	158	5	localObject1	Object
    //   210	3	5	localThrowable1	Throwable
    //   224	1	5	localObject2	Object
    //   239	57	5	localObject3	Object
    //   22	259	6	localObject4	Object
    //   288	3	6	localThrowable2	Throwable
    //   135	32	7	localStringBuilder	StringBuilder
    //   219	1	7	localThrowable3	Throwable
    //   232	10	7	localThrowable4	Throwable
    //   265	7	7	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   203	208	210	java/lang/Throwable
    //   256	261	210	java/lang/Throwable
    //   28	34	219	java/lang/Throwable
    //   38	46	219	java/lang/Throwable
    //   50	58	219	java/lang/Throwable
    //   62	68	219	java/lang/Throwable
    //   72	79	219	java/lang/Throwable
    //   83	92	219	java/lang/Throwable
    //   96	105	219	java/lang/Throwable
    //   109	114	219	java/lang/Throwable
    //   118	124	219	java/lang/Throwable
    //   128	137	219	java/lang/Throwable
    //   141	149	219	java/lang/Throwable
    //   153	160	219	java/lang/Throwable
    //   164	174	219	java/lang/Throwable
    //   185	192	219	java/lang/Throwable
    //   7	24	224	finally
    //   7	24	232	java/lang/Throwable
    //   28	34	265	finally
    //   38	46	265	finally
    //   50	58	265	finally
    //   62	68	265	finally
    //   72	79	265	finally
    //   83	92	265	finally
    //   96	105	265	finally
    //   109	114	265	finally
    //   118	124	265	finally
    //   128	137	265	finally
    //   141	149	265	finally
    //   153	160	265	finally
    //   164	174	265	finally
    //   185	192	265	finally
    //   241	246	265	finally
    //   280	285	288	java/lang/Throwable
  }
  
  public static boolean detectWithTCPPing()
  {
    try
    {
      Socket localSocket = new Socket();
      localSocket.connect(new InetSocketAddress("www.qq.com", 80), 5000);
      localSocket.close();
      return true;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\connectivitydetect\ConnectivityDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */