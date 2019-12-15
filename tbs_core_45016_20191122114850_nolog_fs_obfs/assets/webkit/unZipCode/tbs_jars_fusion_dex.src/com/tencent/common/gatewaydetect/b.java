package com.tencent.common.gatewaydetect;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

class b
  implements Runnable
{
  private GatewayDetector.GatewayDetectCallback jdField_a_of_type_ComTencentCommonGatewaydetectGatewayDetector$GatewayDetectCallback;
  private GatewayDetector.GatewayInfo jdField_a_of_type_ComTencentCommonGatewaydetectGatewayDetector$GatewayInfo = new GatewayDetector.GatewayInfo();
  
  public b(GatewayDetector.GatewayDetectCallback paramGatewayDetectCallback)
  {
    this.jdField_a_of_type_ComTencentCommonGatewaydetectGatewayDetector$GatewayDetectCallback = paramGatewayDetectCallback;
  }
  
  private String a(InputStream paramInputStream)
    throws Exception
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte['Ȁ'];
    for (;;)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i == -1) {
        break;
      }
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
    paramInputStream = localByteArrayOutputStream.toString();
    localByteArrayOutputStream.close();
    return paramInputStream;
  }
  
  private void a(boolean paramBoolean)
  {
    GatewayDetector.GatewayDetectCallback localGatewayDetectCallback = this.jdField_a_of_type_ComTencentCommonGatewaydetectGatewayDetector$GatewayDetectCallback;
    if (localGatewayDetectCallback != null) {
      localGatewayDetectCallback.onGatewayDetectResult(paramBoolean, this.jdField_a_of_type_ComTencentCommonGatewaydetectGatewayDetector$GatewayInfo);
    }
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: invokestatic 62	com/tencent/common/http/Apn:isNetworkAvailable	()Z
    //   3: ifne +9 -> 12
    //   6: aload_0
    //   7: iconst_0
    //   8: invokespecial 64	com/tencent/common/gatewaydetect/b:a	(Z)V
    //   11: return
    //   12: aconst_null
    //   13: astore 6
    //   15: aconst_null
    //   16: astore 9
    //   18: aconst_null
    //   19: astore_3
    //   20: aconst_null
    //   21: astore 8
    //   23: new 66	java/net/URL
    //   26: dup
    //   27: ldc 68
    //   29: invokespecial 71	java/net/URL:<init>	(Ljava/lang/String;)V
    //   32: invokevirtual 75	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   35: checkcast 77	java/net/HttpURLConnection
    //   38: astore 7
    //   40: aload 8
    //   42: astore 4
    //   44: aload 9
    //   46: astore_3
    //   47: aload 7
    //   49: astore 5
    //   51: aload 7
    //   53: iconst_0
    //   54: invokevirtual 80	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   57: aload 8
    //   59: astore 4
    //   61: aload 9
    //   63: astore_3
    //   64: aload 7
    //   66: astore 5
    //   68: aload 7
    //   70: sipush 5000
    //   73: invokevirtual 84	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   76: aload 8
    //   78: astore 4
    //   80: aload 9
    //   82: astore_3
    //   83: aload 7
    //   85: astore 5
    //   87: aload 7
    //   89: sipush 5000
    //   92: invokevirtual 87	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   95: aload 8
    //   97: astore 4
    //   99: aload 9
    //   101: astore_3
    //   102: aload 7
    //   104: astore 5
    //   106: aload 7
    //   108: iconst_0
    //   109: invokevirtual 90	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   112: aload 8
    //   114: astore 4
    //   116: aload 9
    //   118: astore_3
    //   119: aload 7
    //   121: astore 5
    //   123: aload 7
    //   125: ldc 92
    //   127: invokevirtual 95	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   130: aload 8
    //   132: astore 4
    //   134: aload 9
    //   136: astore_3
    //   137: aload 7
    //   139: astore 5
    //   141: aload 7
    //   143: ldc 97
    //   145: ldc 98
    //   147: invokevirtual 102	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   150: aload 8
    //   152: astore 4
    //   154: aload 9
    //   156: astore_3
    //   157: aload 7
    //   159: astore 5
    //   161: aload 7
    //   163: ldc 104
    //   165: ldc 106
    //   167: invokevirtual 102	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   170: aload 8
    //   172: astore 4
    //   174: aload 9
    //   176: astore_3
    //   177: aload 7
    //   179: astore 5
    //   181: aload 7
    //   183: invokevirtual 109	java/net/HttpURLConnection:connect	()V
    //   186: aload 8
    //   188: astore 4
    //   190: aload 9
    //   192: astore_3
    //   193: aload 7
    //   195: astore 5
    //   197: aload 7
    //   199: invokevirtual 113	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   202: astore 6
    //   204: aload 6
    //   206: astore 4
    //   208: aload 6
    //   210: astore_3
    //   211: aload 7
    //   213: astore 5
    //   215: aload 7
    //   217: invokevirtual 117	java/net/HttpURLConnection:getResponseCode	()I
    //   220: istore_1
    //   221: aload 6
    //   223: astore 4
    //   225: aload 6
    //   227: astore_3
    //   228: aload 7
    //   230: astore 5
    //   232: new 119	java/lang/StringBuilder
    //   235: dup
    //   236: invokespecial 120	java/lang/StringBuilder:<init>	()V
    //   239: astore 8
    //   241: aload 6
    //   243: astore 4
    //   245: aload 6
    //   247: astore_3
    //   248: aload 7
    //   250: astore 5
    //   252: aload 8
    //   254: ldc 122
    //   256: invokevirtual 126	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   259: pop
    //   260: aload 6
    //   262: astore 4
    //   264: aload 6
    //   266: astore_3
    //   267: aload 7
    //   269: astore 5
    //   271: aload 8
    //   273: iload_1
    //   274: invokevirtual 129	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   277: pop
    //   278: aload 6
    //   280: astore 4
    //   282: aload 6
    //   284: astore_3
    //   285: aload 7
    //   287: astore 5
    //   289: ldc -125
    //   291: aload 8
    //   293: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   296: invokestatic 137	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   299: iload_1
    //   300: sipush 200
    //   303: if_icmpeq +72 -> 375
    //   306: aload 6
    //   308: astore 4
    //   310: aload 6
    //   312: astore_3
    //   313: aload 7
    //   315: astore 5
    //   317: ldc -125
    //   319: ldc -117
    //   321: invokestatic 137	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   324: aload 6
    //   326: astore 4
    //   328: aload 6
    //   330: astore_3
    //   331: aload 7
    //   333: astore 5
    //   335: aload_0
    //   336: iconst_0
    //   337: invokespecial 64	com/tencent/common/gatewaydetect/b:a	(Z)V
    //   340: aload 6
    //   342: ifnull +16 -> 358
    //   345: aload 6
    //   347: invokevirtual 140	java/io/InputStream:close	()V
    //   350: goto +8 -> 358
    //   353: astore_3
    //   354: aload_3
    //   355: invokevirtual 143	java/lang/Exception:printStackTrace	()V
    //   358: aload 7
    //   360: ifnull +14 -> 374
    //   363: aload 7
    //   365: invokevirtual 146	java/net/HttpURLConnection:disconnect	()V
    //   368: return
    //   369: astore_3
    //   370: aload_3
    //   371: invokevirtual 143	java/lang/Exception:printStackTrace	()V
    //   374: return
    //   375: aload 6
    //   377: astore 4
    //   379: aload 6
    //   381: astore_3
    //   382: aload 7
    //   384: astore 5
    //   386: aload 7
    //   388: invokevirtual 113	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   391: astore 6
    //   393: aload 6
    //   395: astore 4
    //   397: aload 6
    //   399: astore_3
    //   400: aload 7
    //   402: astore 5
    //   404: aload_0
    //   405: aload 6
    //   407: invokespecial 148	com/tencent/common/gatewaydetect/b:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   410: astore 8
    //   412: aload 8
    //   414: ifnonnull +54 -> 468
    //   417: aload 6
    //   419: astore 4
    //   421: aload 6
    //   423: astore_3
    //   424: aload 7
    //   426: astore 5
    //   428: aload_0
    //   429: iconst_0
    //   430: invokespecial 64	com/tencent/common/gatewaydetect/b:a	(Z)V
    //   433: aload 6
    //   435: ifnull +16 -> 451
    //   438: aload 6
    //   440: invokevirtual 140	java/io/InputStream:close	()V
    //   443: goto +8 -> 451
    //   446: astore_3
    //   447: aload_3
    //   448: invokevirtual 143	java/lang/Exception:printStackTrace	()V
    //   451: aload 7
    //   453: ifnull +14 -> 467
    //   456: aload 7
    //   458: invokevirtual 146	java/net/HttpURLConnection:disconnect	()V
    //   461: return
    //   462: astore_3
    //   463: aload_3
    //   464: invokevirtual 143	java/lang/Exception:printStackTrace	()V
    //   467: return
    //   468: aload 6
    //   470: astore 4
    //   472: aload 6
    //   474: astore_3
    //   475: aload 7
    //   477: astore 5
    //   479: new 150	org/json/JSONObject
    //   482: dup
    //   483: aload 8
    //   485: invokespecial 151	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   488: astore 8
    //   490: aload 6
    //   492: astore 4
    //   494: aload 6
    //   496: astore_3
    //   497: aload 7
    //   499: astore 5
    //   501: aload 8
    //   503: ldc -103
    //   505: invokevirtual 157	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   508: istore_2
    //   509: iload_2
    //   510: iconst_2
    //   511: if_icmpgt +331 -> 842
    //   514: iload_2
    //   515: istore_1
    //   516: iload_2
    //   517: ifge +6 -> 523
    //   520: goto +322 -> 842
    //   523: aload 6
    //   525: astore 4
    //   527: aload 6
    //   529: astore_3
    //   530: aload 7
    //   532: astore 5
    //   534: aload_0
    //   535: getfield 19	com/tencent/common/gatewaydetect/b:jdField_a_of_type_ComTencentCommonGatewaydetectGatewayDetector$GatewayInfo	Lcom/tencent/common/gatewaydetect/GatewayDetector$GatewayInfo;
    //   538: iload_1
    //   539: putfield 161	com/tencent/common/gatewaydetect/GatewayDetector$GatewayInfo:type	I
    //   542: aload 6
    //   544: astore 4
    //   546: aload 6
    //   548: astore_3
    //   549: aload 7
    //   551: astore 5
    //   553: aload_0
    //   554: getfield 19	com/tencent/common/gatewaydetect/b:jdField_a_of_type_ComTencentCommonGatewaydetectGatewayDetector$GatewayInfo	Lcom/tencent/common/gatewaydetect/GatewayDetector$GatewayInfo;
    //   557: aload 8
    //   559: ldc -93
    //   561: invokevirtual 167	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   564: putfield 171	com/tencent/common/gatewaydetect/GatewayDetector$GatewayInfo:apnName	Ljava/lang/String;
    //   567: aload 6
    //   569: astore 4
    //   571: aload 6
    //   573: astore_3
    //   574: aload 7
    //   576: astore 5
    //   578: aload_0
    //   579: getfield 19	com/tencent/common/gatewaydetect/b:jdField_a_of_type_ComTencentCommonGatewaydetectGatewayDetector$GatewayInfo	Lcom/tencent/common/gatewaydetect/GatewayDetector$GatewayInfo;
    //   582: aload 8
    //   584: ldc -83
    //   586: invokevirtual 167	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   589: putfield 176	com/tencent/common/gatewaydetect/GatewayDetector$GatewayInfo:gatewayIP	Ljava/lang/String;
    //   592: aload 6
    //   594: astore 4
    //   596: aload 6
    //   598: astore_3
    //   599: aload 7
    //   601: astore 5
    //   603: aload_0
    //   604: iconst_1
    //   605: invokespecial 64	com/tencent/common/gatewaydetect/b:a	(Z)V
    //   608: aload 6
    //   610: astore 4
    //   612: aload 6
    //   614: astore_3
    //   615: aload 7
    //   617: astore 5
    //   619: new 119	java/lang/StringBuilder
    //   622: dup
    //   623: invokespecial 120	java/lang/StringBuilder:<init>	()V
    //   626: astore 9
    //   628: aload 6
    //   630: astore 4
    //   632: aload 6
    //   634: astore_3
    //   635: aload 7
    //   637: astore 5
    //   639: aload 9
    //   641: ldc -78
    //   643: invokevirtual 126	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   646: pop
    //   647: aload 6
    //   649: astore 4
    //   651: aload 6
    //   653: astore_3
    //   654: aload 7
    //   656: astore 5
    //   658: aload 9
    //   660: aload 8
    //   662: invokevirtual 181	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   665: pop
    //   666: aload 6
    //   668: astore 4
    //   670: aload 6
    //   672: astore_3
    //   673: aload 7
    //   675: astore 5
    //   677: ldc -125
    //   679: aload 9
    //   681: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   684: invokestatic 137	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   687: aload 6
    //   689: ifnull +16 -> 705
    //   692: aload 6
    //   694: invokevirtual 140	java/io/InputStream:close	()V
    //   697: goto +8 -> 705
    //   700: astore_3
    //   701: aload_3
    //   702: invokevirtual 143	java/lang/Exception:printStackTrace	()V
    //   705: aload 7
    //   707: ifnull +95 -> 802
    //   710: aload 7
    //   712: invokevirtual 146	java/net/HttpURLConnection:disconnect	()V
    //   715: return
    //   716: astore_3
    //   717: aload 4
    //   719: astore 6
    //   721: aload 7
    //   723: astore 4
    //   725: aload_3
    //   726: astore 7
    //   728: goto +16 -> 744
    //   731: astore 4
    //   733: aconst_null
    //   734: astore 5
    //   736: goto +69 -> 805
    //   739: astore 7
    //   741: aconst_null
    //   742: astore 4
    //   744: aload 6
    //   746: astore_3
    //   747: aload 4
    //   749: astore 5
    //   751: aload 7
    //   753: invokevirtual 182	java/lang/Throwable:printStackTrace	()V
    //   756: aload 6
    //   758: astore_3
    //   759: aload 4
    //   761: astore 5
    //   763: aload_0
    //   764: iconst_0
    //   765: invokespecial 64	com/tencent/common/gatewaydetect/b:a	(Z)V
    //   768: aload 6
    //   770: ifnull +16 -> 786
    //   773: aload 6
    //   775: invokevirtual 140	java/io/InputStream:close	()V
    //   778: goto +8 -> 786
    //   781: astore_3
    //   782: aload_3
    //   783: invokevirtual 143	java/lang/Exception:printStackTrace	()V
    //   786: aload 4
    //   788: ifnull +14 -> 802
    //   791: aload 4
    //   793: invokevirtual 146	java/net/HttpURLConnection:disconnect	()V
    //   796: return
    //   797: astore_3
    //   798: aload_3
    //   799: invokevirtual 143	java/lang/Exception:printStackTrace	()V
    //   802: return
    //   803: astore 4
    //   805: aload_3
    //   806: ifnull +15 -> 821
    //   809: aload_3
    //   810: invokevirtual 140	java/io/InputStream:close	()V
    //   813: goto +8 -> 821
    //   816: astore_3
    //   817: aload_3
    //   818: invokevirtual 143	java/lang/Exception:printStackTrace	()V
    //   821: aload 5
    //   823: ifnull +16 -> 839
    //   826: aload 5
    //   828: invokevirtual 146	java/net/HttpURLConnection:disconnect	()V
    //   831: goto +8 -> 839
    //   834: astore_3
    //   835: aload_3
    //   836: invokevirtual 143	java/lang/Exception:printStackTrace	()V
    //   839: aload 4
    //   841: athrow
    //   842: iconst_0
    //   843: istore_1
    //   844: goto -321 -> 523
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	847	0	this	b
    //   220	624	1	i	int
    //   508	9	2	j	int
    //   19	312	3	localObject1	Object
    //   353	2	3	localException1	Exception
    //   369	2	3	localException2	Exception
    //   381	43	3	localObject2	Object
    //   446	2	3	localException3	Exception
    //   462	2	3	localException4	Exception
    //   474	199	3	localObject3	Object
    //   700	2	3	localException5	Exception
    //   716	10	3	localThrowable1	Throwable
    //   746	13	3	localObject4	Object
    //   781	2	3	localException6	Exception
    //   797	13	3	localException7	Exception
    //   816	2	3	localException8	Exception
    //   834	2	3	localException9	Exception
    //   42	682	4	localObject5	Object
    //   731	1	4	localObject6	Object
    //   742	50	4	localObject7	Object
    //   803	37	4	localObject8	Object
    //   49	778	5	localObject9	Object
    //   13	761	6	localObject10	Object
    //   38	689	7	localObject11	Object
    //   739	13	7	localThrowable2	Throwable
    //   21	640	8	localObject12	Object
    //   16	664	9	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   345	350	353	java/lang/Exception
    //   363	368	369	java/lang/Exception
    //   438	443	446	java/lang/Exception
    //   456	461	462	java/lang/Exception
    //   692	697	700	java/lang/Exception
    //   51	57	716	java/lang/Throwable
    //   68	76	716	java/lang/Throwable
    //   87	95	716	java/lang/Throwable
    //   106	112	716	java/lang/Throwable
    //   123	130	716	java/lang/Throwable
    //   141	150	716	java/lang/Throwable
    //   161	170	716	java/lang/Throwable
    //   181	186	716	java/lang/Throwable
    //   197	204	716	java/lang/Throwable
    //   215	221	716	java/lang/Throwable
    //   232	241	716	java/lang/Throwable
    //   252	260	716	java/lang/Throwable
    //   271	278	716	java/lang/Throwable
    //   289	299	716	java/lang/Throwable
    //   317	324	716	java/lang/Throwable
    //   335	340	716	java/lang/Throwable
    //   386	393	716	java/lang/Throwable
    //   404	412	716	java/lang/Throwable
    //   428	433	716	java/lang/Throwable
    //   479	490	716	java/lang/Throwable
    //   501	509	716	java/lang/Throwable
    //   534	542	716	java/lang/Throwable
    //   553	567	716	java/lang/Throwable
    //   578	592	716	java/lang/Throwable
    //   603	608	716	java/lang/Throwable
    //   619	628	716	java/lang/Throwable
    //   639	647	716	java/lang/Throwable
    //   658	666	716	java/lang/Throwable
    //   677	687	716	java/lang/Throwable
    //   23	40	731	finally
    //   23	40	739	java/lang/Throwable
    //   773	778	781	java/lang/Exception
    //   710	715	797	java/lang/Exception
    //   791	796	797	java/lang/Exception
    //   51	57	803	finally
    //   68	76	803	finally
    //   87	95	803	finally
    //   106	112	803	finally
    //   123	130	803	finally
    //   141	150	803	finally
    //   161	170	803	finally
    //   181	186	803	finally
    //   197	204	803	finally
    //   215	221	803	finally
    //   232	241	803	finally
    //   252	260	803	finally
    //   271	278	803	finally
    //   289	299	803	finally
    //   317	324	803	finally
    //   335	340	803	finally
    //   386	393	803	finally
    //   404	412	803	finally
    //   428	433	803	finally
    //   479	490	803	finally
    //   501	509	803	finally
    //   534	542	803	finally
    //   553	567	803	finally
    //   578	592	803	finally
    //   603	608	803	finally
    //   619	628	803	finally
    //   639	647	803	finally
    //   658	666	803	finally
    //   677	687	803	finally
    //   751	756	803	finally
    //   763	768	803	finally
    //   809	813	816	java/lang/Exception
    //   826	831	834	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\gatewaydetect\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */