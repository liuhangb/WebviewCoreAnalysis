package com.tencent.common.serverconfig.netchecker;

import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.serverconfig.DnsManager;
import com.tencent.common.serverconfig.DnsManager.DnsData;
import com.tencent.common.serverconfig.IPListUtils;
import com.tencent.common.serverconfig.WupProxyDomainRouter;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class WupNetworkCheckTask
  implements Runnable
{
  private static int jdField_a_of_type_Int = 2;
  private static final byte[] jdField_a_of_type_ArrayOfByte = "<html>hello WupMonitor!</html>".getBytes();
  private String jdField_a_of_type_JavaLangString = "";
  private CountDownLatch jdField_a_of_type_JavaUtilConcurrentCountDownLatch;
  private boolean jdField_a_of_type_Boolean;
  
  public WupNetworkCheckTask(String paramString, CountDownLatch paramCountDownLatch)
  {
    this.jdField_a_of_type_JavaUtilConcurrentCountDownLatch = paramCountDownLatch;
    this.jdField_a_of_type_Boolean = false;
  }
  
  private String a(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return paramString;
    }
    Object localObject3 = IPListUtils.resolveValidIP(paramString);
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("START resolve domain: ");
    ((StringBuilder)localObject1).append(paramString);
    FLogger.d("WupIPListSelfCheckerTask", ((StringBuilder)localObject1).toString());
    Object localObject2;
    try
    {
      String str = new URL((String)localObject3).getHost();
      localObject1 = localObject3;
      if (WupProxyDomainRouter.isWupProxyHost(str))
      {
        DnsManager.DnsData localDnsData = DnsManager.getInstance().getIPAddressSync(str);
        localObject1 = localObject3;
        if (localObject3 != null)
        {
          localObject1 = localObject3;
          if (localDnsData != null)
          {
            localObject1 = localObject3;
            if (!TextUtils.isEmpty(localDnsData.mIP)) {
              localObject1 = ((String)localObject3).replace(str, localDnsData.mIP);
            }
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      localObject2 = localObject3;
    }
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("END resolve domain: ");
    ((StringBuilder)localObject3).append(paramString);
    ((StringBuilder)localObject3).append(", resolved = ");
    ((StringBuilder)localObject3).append((String)localObject2);
    FLogger.d("WupIPListSelfCheckerTask", ((StringBuilder)localObject3).toString());
    return (String)localObject2;
  }
  
  /* Error */
  private boolean a(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 8
    //   6: aconst_null
    //   7: astore 4
    //   9: aconst_null
    //   10: astore 7
    //   12: aload_1
    //   13: astore_3
    //   14: aload_1
    //   15: ldc 120
    //   17: invokevirtual 123	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   20: ifne +29 -> 49
    //   23: new 57	java/lang/StringBuilder
    //   26: dup
    //   27: invokespecial 58	java/lang/StringBuilder:<init>	()V
    //   30: astore_3
    //   31: aload_3
    //   32: aload_1
    //   33: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: pop
    //   37: aload_3
    //   38: ldc 120
    //   40: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: pop
    //   44: aload_3
    //   45: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   48: astore_3
    //   49: new 57	java/lang/StringBuilder
    //   52: dup
    //   53: invokespecial 58	java/lang/StringBuilder:<init>	()V
    //   56: astore_1
    //   57: aload_1
    //   58: aload_3
    //   59: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: pop
    //   63: aload_1
    //   64: ldc 125
    //   66: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: pop
    //   70: aload_1
    //   71: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   74: astore_1
    //   75: new 57	java/lang/StringBuilder
    //   78: dup
    //   79: invokespecial 58	java/lang/StringBuilder:<init>	()V
    //   82: astore_3
    //   83: aload_3
    //   84: ldc 127
    //   86: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   89: pop
    //   90: aload_3
    //   91: aload_1
    //   92: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: ldc 66
    //   98: aload_3
    //   99: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   102: invokestatic 76	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   105: new 57	java/lang/StringBuilder
    //   108: dup
    //   109: invokespecial 58	java/lang/StringBuilder:<init>	()V
    //   112: astore_3
    //   113: aload_3
    //   114: aload_1
    //   115: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: pop
    //   119: aload_3
    //   120: invokestatic 133	java/lang/System:currentTimeMillis	()J
    //   123: invokevirtual 136	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   126: pop
    //   127: new 78	java/net/URL
    //   130: dup
    //   131: aload_3
    //   132: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   135: invokespecial 81	java/net/URL:<init>	(Ljava/lang/String;)V
    //   138: invokevirtual 140	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   141: checkcast 142	java/net/HttpURLConnection
    //   144: astore 6
    //   146: aload 7
    //   148: astore 4
    //   150: aload 8
    //   152: astore_1
    //   153: aload 6
    //   155: astore_3
    //   156: aload 6
    //   158: iconst_0
    //   159: invokevirtual 146	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   162: aload 7
    //   164: astore 4
    //   166: aload 8
    //   168: astore_1
    //   169: aload 6
    //   171: astore_3
    //   172: aload 6
    //   174: sipush 5000
    //   177: invokevirtual 150	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   180: aload 7
    //   182: astore 4
    //   184: aload 8
    //   186: astore_1
    //   187: aload 6
    //   189: astore_3
    //   190: aload 6
    //   192: sipush 5000
    //   195: invokevirtual 153	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   198: aload 7
    //   200: astore 4
    //   202: aload 8
    //   204: astore_1
    //   205: aload 6
    //   207: astore_3
    //   208: aload 6
    //   210: iconst_0
    //   211: invokevirtual 156	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   214: aload 7
    //   216: astore 4
    //   218: aload 8
    //   220: astore_1
    //   221: aload 6
    //   223: astore_3
    //   224: aload 6
    //   226: ldc -98
    //   228: invokevirtual 161	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   231: aload 7
    //   233: astore 4
    //   235: aload 8
    //   237: astore_1
    //   238: aload 6
    //   240: astore_3
    //   241: aload 6
    //   243: ldc -93
    //   245: ldc -91
    //   247: invokevirtual 168	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   250: aload 7
    //   252: astore 4
    //   254: aload 8
    //   256: astore_1
    //   257: aload 6
    //   259: astore_3
    //   260: aload 6
    //   262: ldc -86
    //   264: ldc -84
    //   266: invokevirtual 168	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   269: aload 7
    //   271: astore 4
    //   273: aload 8
    //   275: astore_1
    //   276: aload 6
    //   278: astore_3
    //   279: aload 6
    //   281: invokevirtual 175	java/net/HttpURLConnection:connect	()V
    //   284: aload 7
    //   286: astore 4
    //   288: aload 8
    //   290: astore_1
    //   291: aload 6
    //   293: astore_3
    //   294: aload 6
    //   296: invokevirtual 179	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   299: astore 5
    //   301: aload 5
    //   303: astore 4
    //   305: aload 5
    //   307: astore_1
    //   308: aload 6
    //   310: astore_3
    //   311: aload 6
    //   313: invokevirtual 183	java/net/HttpURLConnection:getResponseCode	()I
    //   316: istore_2
    //   317: aload 5
    //   319: astore 4
    //   321: aload 5
    //   323: astore_1
    //   324: aload 6
    //   326: astore_3
    //   327: new 57	java/lang/StringBuilder
    //   330: dup
    //   331: invokespecial 58	java/lang/StringBuilder:<init>	()V
    //   334: astore 7
    //   336: aload 5
    //   338: astore 4
    //   340: aload 5
    //   342: astore_1
    //   343: aload 6
    //   345: astore_3
    //   346: aload 7
    //   348: ldc -71
    //   350: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   353: pop
    //   354: aload 5
    //   356: astore 4
    //   358: aload 5
    //   360: astore_1
    //   361: aload 6
    //   363: astore_3
    //   364: aload 7
    //   366: iload_2
    //   367: invokevirtual 188	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   370: pop
    //   371: aload 5
    //   373: astore 4
    //   375: aload 5
    //   377: astore_1
    //   378: aload 6
    //   380: astore_3
    //   381: ldc 66
    //   383: aload 7
    //   385: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   388: invokestatic 76	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   391: iload_2
    //   392: sipush 200
    //   395: if_icmpeq +57 -> 452
    //   398: aload 5
    //   400: astore 4
    //   402: aload 5
    //   404: astore_1
    //   405: aload 6
    //   407: astore_3
    //   408: ldc 66
    //   410: ldc -66
    //   412: invokestatic 76	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   415: aload 5
    //   417: ifnull +16 -> 433
    //   420: aload 5
    //   422: invokevirtual 194	java/io/InputStream:close	()V
    //   425: goto +8 -> 433
    //   428: astore_1
    //   429: aload_1
    //   430: invokevirtual 195	java/lang/Exception:printStackTrace	()V
    //   433: aload 6
    //   435: ifnull +15 -> 450
    //   438: aload 6
    //   440: invokevirtual 198	java/net/HttpURLConnection:disconnect	()V
    //   443: iconst_0
    //   444: ireturn
    //   445: astore_1
    //   446: aload_1
    //   447: invokevirtual 195	java/lang/Exception:printStackTrace	()V
    //   450: iconst_0
    //   451: ireturn
    //   452: aload 5
    //   454: astore 4
    //   456: aload 5
    //   458: astore_1
    //   459: aload 6
    //   461: astore_3
    //   462: getstatic 24	com/tencent/common/serverconfig/netchecker/WupNetworkCheckTask:jdField_a_of_type_ArrayOfByte	[B
    //   465: arraylength
    //   466: newarray <illegal type>
    //   468: astore 7
    //   470: aload 5
    //   472: astore 4
    //   474: aload 5
    //   476: astore_1
    //   477: aload 6
    //   479: astore_3
    //   480: aload 5
    //   482: aload 7
    //   484: invokevirtual 202	java/io/InputStream:read	([B)I
    //   487: aload 7
    //   489: arraylength
    //   490: if_icmpne +146 -> 636
    //   493: aload 5
    //   495: astore 4
    //   497: aload 5
    //   499: astore_1
    //   500: aload 6
    //   502: astore_3
    //   503: aload 7
    //   505: getstatic 24	com/tencent/common/serverconfig/netchecker/WupNetworkCheckTask:jdField_a_of_type_ArrayOfByte	[B
    //   508: invokestatic 208	com/tencent/common/utils/ByteUtils:isEqual	([B[B)Z
    //   511: ifne +6 -> 517
    //   514: goto +122 -> 636
    //   517: aload 5
    //   519: astore 4
    //   521: aload 5
    //   523: astore_1
    //   524: aload 6
    //   526: astore_3
    //   527: new 57	java/lang/StringBuilder
    //   530: dup
    //   531: invokespecial 58	java/lang/StringBuilder:<init>	()V
    //   534: astore 8
    //   536: aload 5
    //   538: astore 4
    //   540: aload 5
    //   542: astore_1
    //   543: aload 6
    //   545: astore_3
    //   546: aload 8
    //   548: ldc -46
    //   550: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   553: pop
    //   554: aload 5
    //   556: astore 4
    //   558: aload 5
    //   560: astore_1
    //   561: aload 6
    //   563: astore_3
    //   564: aload 8
    //   566: new 18	java/lang/String
    //   569: dup
    //   570: aload 7
    //   572: invokespecial 213	java/lang/String:<init>	([B)V
    //   575: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   578: pop
    //   579: aload 5
    //   581: astore 4
    //   583: aload 5
    //   585: astore_1
    //   586: aload 6
    //   588: astore_3
    //   589: ldc 66
    //   591: aload 8
    //   593: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   596: invokestatic 76	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   599: aload 5
    //   601: ifnull +16 -> 617
    //   604: aload 5
    //   606: invokevirtual 194	java/io/InputStream:close	()V
    //   609: goto +8 -> 617
    //   612: astore_1
    //   613: aload_1
    //   614: invokevirtual 195	java/lang/Exception:printStackTrace	()V
    //   617: aload 6
    //   619: ifnull +15 -> 634
    //   622: aload 6
    //   624: invokevirtual 198	java/net/HttpURLConnection:disconnect	()V
    //   627: iconst_1
    //   628: ireturn
    //   629: astore_1
    //   630: aload_1
    //   631: invokevirtual 195	java/lang/Exception:printStackTrace	()V
    //   634: iconst_1
    //   635: ireturn
    //   636: aload 5
    //   638: astore 4
    //   640: aload 5
    //   642: astore_1
    //   643: aload 6
    //   645: astore_3
    //   646: new 57	java/lang/StringBuilder
    //   649: dup
    //   650: invokespecial 58	java/lang/StringBuilder:<init>	()V
    //   653: astore 8
    //   655: aload 5
    //   657: astore 4
    //   659: aload 5
    //   661: astore_1
    //   662: aload 6
    //   664: astore_3
    //   665: aload 8
    //   667: ldc -41
    //   669: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   672: pop
    //   673: aload 5
    //   675: astore 4
    //   677: aload 5
    //   679: astore_1
    //   680: aload 6
    //   682: astore_3
    //   683: aload 8
    //   685: new 18	java/lang/String
    //   688: dup
    //   689: aload 7
    //   691: invokespecial 213	java/lang/String:<init>	([B)V
    //   694: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   697: pop
    //   698: aload 5
    //   700: astore 4
    //   702: aload 5
    //   704: astore_1
    //   705: aload 6
    //   707: astore_3
    //   708: ldc 66
    //   710: aload 8
    //   712: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   715: invokestatic 76	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   718: aload 5
    //   720: ifnull +16 -> 736
    //   723: aload 5
    //   725: invokevirtual 194	java/io/InputStream:close	()V
    //   728: goto +8 -> 736
    //   731: astore_1
    //   732: aload_1
    //   733: invokevirtual 195	java/lang/Exception:printStackTrace	()V
    //   736: aload 6
    //   738: ifnull +15 -> 753
    //   741: aload 6
    //   743: invokevirtual 198	java/net/HttpURLConnection:disconnect	()V
    //   746: iconst_0
    //   747: ireturn
    //   748: astore_1
    //   749: aload_1
    //   750: invokevirtual 195	java/lang/Exception:printStackTrace	()V
    //   753: iconst_0
    //   754: ireturn
    //   755: astore 7
    //   757: aload 4
    //   759: astore 5
    //   761: aload 6
    //   763: astore 4
    //   765: goto +22 -> 787
    //   768: astore_3
    //   769: aconst_null
    //   770: astore 5
    //   772: aload 4
    //   774: astore_1
    //   775: aload 5
    //   777: astore 4
    //   779: goto +156 -> 935
    //   782: astore 7
    //   784: aconst_null
    //   785: astore 4
    //   787: aload 5
    //   789: astore_1
    //   790: aload 4
    //   792: astore_3
    //   793: new 57	java/lang/StringBuilder
    //   796: dup
    //   797: invokespecial 58	java/lang/StringBuilder:<init>	()V
    //   800: astore 6
    //   802: aload 5
    //   804: astore_1
    //   805: aload 4
    //   807: astore_3
    //   808: aload 6
    //   810: ldc -39
    //   812: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   815: pop
    //   816: aload 5
    //   818: astore_1
    //   819: aload 4
    //   821: astore_3
    //   822: aload 6
    //   824: aload_0
    //   825: getfield 35	com/tencent/common/serverconfig/netchecker/WupNetworkCheckTask:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   828: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   831: pop
    //   832: aload 5
    //   834: astore_1
    //   835: aload 4
    //   837: astore_3
    //   838: aload 6
    //   840: ldc -37
    //   842: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   845: pop
    //   846: aload 5
    //   848: astore_1
    //   849: aload 4
    //   851: astore_3
    //   852: aload 6
    //   854: aload 7
    //   856: invokevirtual 222	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   859: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   862: pop
    //   863: aload 5
    //   865: astore_1
    //   866: aload 4
    //   868: astore_3
    //   869: ldc 66
    //   871: aload 6
    //   873: invokevirtual 70	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   876: invokestatic 76	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   879: aload 5
    //   881: astore_1
    //   882: aload 4
    //   884: astore_3
    //   885: aload 7
    //   887: invokevirtual 112	java/lang/Throwable:printStackTrace	()V
    //   890: aload 5
    //   892: ifnull +16 -> 908
    //   895: aload 5
    //   897: invokevirtual 194	java/io/InputStream:close	()V
    //   900: goto +8 -> 908
    //   903: astore_1
    //   904: aload_1
    //   905: invokevirtual 195	java/lang/Exception:printStackTrace	()V
    //   908: aload 4
    //   910: ifnull +15 -> 925
    //   913: aload 4
    //   915: invokevirtual 198	java/net/HttpURLConnection:disconnect	()V
    //   918: iconst_0
    //   919: ireturn
    //   920: astore_1
    //   921: aload_1
    //   922: invokevirtual 195	java/lang/Exception:printStackTrace	()V
    //   925: iconst_0
    //   926: ireturn
    //   927: astore 5
    //   929: aload_3
    //   930: astore 4
    //   932: aload 5
    //   934: astore_3
    //   935: aload_1
    //   936: ifnull +15 -> 951
    //   939: aload_1
    //   940: invokevirtual 194	java/io/InputStream:close	()V
    //   943: goto +8 -> 951
    //   946: astore_1
    //   947: aload_1
    //   948: invokevirtual 195	java/lang/Exception:printStackTrace	()V
    //   951: aload 4
    //   953: ifnull +16 -> 969
    //   956: aload 4
    //   958: invokevirtual 198	java/net/HttpURLConnection:disconnect	()V
    //   961: goto +8 -> 969
    //   964: astore_1
    //   965: aload_1
    //   966: invokevirtual 195	java/lang/Exception:printStackTrace	()V
    //   969: aload_3
    //   970: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	971	0	this	WupNetworkCheckTask
    //   0	971	1	paramString	String
    //   316	80	2	i	int
    //   13	695	3	localObject1	Object
    //   768	1	3	localObject2	Object
    //   792	178	3	localObject3	Object
    //   7	950	4	localObject4	Object
    //   1	895	5	localObject5	Object
    //   927	6	5	localObject6	Object
    //   144	728	6	localObject7	Object
    //   10	680	7	localObject8	Object
    //   755	1	7	localThrowable1	Throwable
    //   782	104	7	localThrowable2	Throwable
    //   4	707	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   420	425	428	java/lang/Exception
    //   438	443	445	java/lang/Exception
    //   604	609	612	java/lang/Exception
    //   622	627	629	java/lang/Exception
    //   723	728	731	java/lang/Exception
    //   741	746	748	java/lang/Exception
    //   156	162	755	java/lang/Throwable
    //   172	180	755	java/lang/Throwable
    //   190	198	755	java/lang/Throwable
    //   208	214	755	java/lang/Throwable
    //   224	231	755	java/lang/Throwable
    //   241	250	755	java/lang/Throwable
    //   260	269	755	java/lang/Throwable
    //   279	284	755	java/lang/Throwable
    //   294	301	755	java/lang/Throwable
    //   311	317	755	java/lang/Throwable
    //   327	336	755	java/lang/Throwable
    //   346	354	755	java/lang/Throwable
    //   364	371	755	java/lang/Throwable
    //   381	391	755	java/lang/Throwable
    //   408	415	755	java/lang/Throwable
    //   462	470	755	java/lang/Throwable
    //   480	493	755	java/lang/Throwable
    //   503	514	755	java/lang/Throwable
    //   527	536	755	java/lang/Throwable
    //   546	554	755	java/lang/Throwable
    //   564	579	755	java/lang/Throwable
    //   589	599	755	java/lang/Throwable
    //   646	655	755	java/lang/Throwable
    //   665	673	755	java/lang/Throwable
    //   683	698	755	java/lang/Throwable
    //   708	718	755	java/lang/Throwable
    //   14	49	768	finally
    //   49	146	768	finally
    //   14	49	782	java/lang/Throwable
    //   49	146	782	java/lang/Throwable
    //   895	900	903	java/lang/Exception
    //   913	918	920	java/lang/Exception
    //   156	162	927	finally
    //   172	180	927	finally
    //   190	198	927	finally
    //   208	214	927	finally
    //   224	231	927	finally
    //   241	250	927	finally
    //   260	269	927	finally
    //   279	284	927	finally
    //   294	301	927	finally
    //   311	317	927	finally
    //   327	336	927	finally
    //   346	354	927	finally
    //   364	371	927	finally
    //   381	391	927	finally
    //   408	415	927	finally
    //   462	470	927	finally
    //   480	493	927	finally
    //   503	514	927	finally
    //   527	536	927	finally
    //   546	554	927	finally
    //   564	579	927	finally
    //   589	599	927	finally
    //   646	655	927	finally
    //   665	673	927	finally
    //   683	698	927	finally
    //   708	718	927	finally
    //   793	802	927	finally
    //   808	816	927	finally
    //   822	832	927	finally
    //   838	846	927	finally
    //   852	863	927	finally
    //   869	879	927	finally
    //   885	890	927	finally
    //   939	943	946	java/lang/Exception
    //   956	961	964	java/lang/Exception
  }
  
  private boolean b(String paramString)
  {
    boolean bool2 = TextUtils.isEmpty(paramString);
    boolean bool1 = false;
    if (bool2) {
      return false;
    }
    int i = 0;
    while ((!bool1) && (i < jdField_a_of_type_Int))
    {
      i += 1;
      bool1 = a(paramString);
    }
    return bool1;
  }
  
  public boolean getResult()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public void run()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("start check ");
    ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaLangString);
    FLogger.d("WupIPListSelfCheckerTask", ((StringBuilder)localObject).toString());
    this.jdField_a_of_type_Boolean = b(this.jdField_a_of_type_JavaLangString);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("end check ");
    ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaLangString);
    ((StringBuilder)localObject).append(", result=");
    ((StringBuilder)localObject).append(this.jdField_a_of_type_Boolean);
    FLogger.d("WupIPListSelfCheckerTask", ((StringBuilder)localObject).toString());
    localObject = this.jdField_a_of_type_JavaUtilConcurrentCountDownLatch;
    if (localObject != null) {
      ((CountDownLatch)localObject).countDown();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\serverconfig\netchecker\WupNetworkCheckTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */