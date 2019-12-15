package com.tencent.mtt.base.task;

import android.net.Network;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.tbs.common.internal.service.StatServerHolder;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;

public class WalledGardenDetectTask
  extends BaseWalledGardenTask
  implements Handler.Callback
{
  public static final int DETECT_MODE_204 = 0;
  public static final int DETECT_MODE_FILE = 1;
  public static final int RESULT_CODE_AUTH = 2;
  public static final int RESULT_CODE_EXCEPTION = 3;
  public static final int RESULT_CODE_FAIL = 1;
  public static final int RESULT_CODE_SUCCESS = 0;
  public static final String TAG = "WalledGardenDetectTask";
  public static final String WALLED_GARDEN_URL = "http://dnet.mb.qq.com/rsp204";
  public static final String WALLED_GARDEN_URL_BY_FILE = "http://res.imtt.qq.com/qbprobe/netprobe.txt";
  private static String jdField_b_of_type_JavaLangString = "9a6f75849b";
  private static String d;
  int jdField_a_of_type_Int;
  private Network jdField_a_of_type_AndroidNetNetwork;
  private Handler jdField_a_of_type_AndroidOsHandler;
  WalledGardenMessage jdField_a_of_type_ComTencentMttBaseTaskWalledGardenMessage;
  String jdField_a_of_type_JavaLangString;
  private boolean jdField_a_of_type_Boolean = false;
  private int jdField_b_of_type_Int = 0;
  private int jdField_c_of_type_Int;
  private String jdField_c_of_type_JavaLangString = "http://dnet.mb.qq.com/rsp204";
  private String e;
  public String mSsid;
  
  /* Error */
  private int a()
  {
    // Byte code:
    //   0: new 56	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 57	java/lang/StringBuilder:<init>	()V
    //   7: astore_3
    //   8: aload_3
    //   9: ldc 59
    //   11: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: pop
    //   15: aload_3
    //   16: aload_0
    //   17: getfield 47	com/tencent/mtt/base/task/WalledGardenDetectTask:jdField_c_of_type_JavaLangString	Ljava/lang/String;
    //   20: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: ldc 21
    //   26: aload_3
    //   27: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   30: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   33: aconst_null
    //   34: astore 6
    //   36: aload_0
    //   37: aconst_null
    //   38: putfield 74	com/tencent/mtt/base/task/WalledGardenDetectTask:e	Ljava/lang/String;
    //   41: aload_0
    //   42: aconst_null
    //   43: putfield 76	com/tencent/mtt/base/task/WalledGardenDetectTask:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   46: aload_0
    //   47: iconst_0
    //   48: putfield 78	com/tencent/mtt/base/task/WalledGardenDetectTask:jdField_a_of_type_Int	I
    //   51: new 80	java/net/URL
    //   54: dup
    //   55: aload_0
    //   56: getfield 47	com/tencent/mtt/base/task/WalledGardenDetectTask:jdField_c_of_type_JavaLangString	Ljava/lang/String;
    //   59: invokespecial 83	java/net/URL:<init>	(Ljava/lang/String;)V
    //   62: astore_3
    //   63: aload_0
    //   64: getfield 85	com/tencent/mtt/base/task/WalledGardenDetectTask:jdField_a_of_type_AndroidNetNetwork	Landroid/net/Network;
    //   67: ifnull +26 -> 93
    //   70: getstatic 90	android/os/Build$VERSION:SDK_INT	I
    //   73: bipush 21
    //   75: if_icmplt +18 -> 93
    //   78: aload_0
    //   79: getfield 85	com/tencent/mtt/base/task/WalledGardenDetectTask:jdField_a_of_type_AndroidNetNetwork	Landroid/net/Network;
    //   82: aload_3
    //   83: invokevirtual 96	android/net/Network:openConnection	(Ljava/net/URL;)Ljava/net/URLConnection;
    //   86: checkcast 98	java/net/HttpURLConnection
    //   89: astore_3
    //   90: goto +11 -> 101
    //   93: aload_3
    //   94: invokevirtual 101	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   97: checkcast 98	java/net/HttpURLConnection
    //   100: astore_3
    //   101: aload_3
    //   102: astore 4
    //   104: aload_3
    //   105: iconst_0
    //   106: invokevirtual 105	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   109: aload_3
    //   110: astore 4
    //   112: aload_3
    //   113: ldc 106
    //   115: invokevirtual 110	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   118: aload_3
    //   119: astore 4
    //   121: aload_3
    //   122: ldc 106
    //   124: invokevirtual 113	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   127: aload_3
    //   128: astore 4
    //   130: aload_3
    //   131: iconst_0
    //   132: invokevirtual 116	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   135: aload_3
    //   136: astore 4
    //   138: getstatic 118	com/tencent/mtt/base/task/WalledGardenDetectTask:d	Ljava/lang/String;
    //   141: invokestatic 124	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   144: ifne +15 -> 159
    //   147: aload_3
    //   148: astore 4
    //   150: aload_3
    //   151: ldc 126
    //   153: getstatic 118	com/tencent/mtt/base/task/WalledGardenDetectTask:d	Ljava/lang/String;
    //   156: invokevirtual 129	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   159: aload_3
    //   160: astore 4
    //   162: aload_3
    //   163: invokevirtual 133	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   166: astore 7
    //   168: aload_3
    //   169: astore 4
    //   171: aload_3
    //   172: invokevirtual 136	java/net/HttpURLConnection:getResponseCode	()I
    //   175: istore_1
    //   176: aload_3
    //   177: astore 4
    //   179: aload_0
    //   180: iload_1
    //   181: putfield 78	com/tencent/mtt/base/task/WalledGardenDetectTask:jdField_a_of_type_Int	I
    //   184: aload_3
    //   185: astore 4
    //   187: new 56	java/lang/StringBuilder
    //   190: dup
    //   191: invokespecial 57	java/lang/StringBuilder:<init>	()V
    //   194: astore 5
    //   196: aload_3
    //   197: astore 4
    //   199: aload 5
    //   201: ldc -118
    //   203: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   206: pop
    //   207: aload_3
    //   208: astore 4
    //   210: aload 5
    //   212: iload_1
    //   213: invokevirtual 141	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   216: pop
    //   217: aload_3
    //   218: astore 4
    //   220: ldc 21
    //   222: aload 5
    //   224: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   227: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   230: iload_1
    //   231: sipush 200
    //   234: if_icmpne +493 -> 727
    //   237: aload_3
    //   238: astore 4
    //   240: aload_0
    //   241: aload 7
    //   243: invokespecial 144	com/tencent/mtt/base/task/WalledGardenDetectTask:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   246: astore 5
    //   248: aload_3
    //   249: astore 4
    //   251: new 56	java/lang/StringBuilder
    //   254: dup
    //   255: invokespecial 57	java/lang/StringBuilder:<init>	()V
    //   258: astore 8
    //   260: aload_3
    //   261: astore 4
    //   263: aload 8
    //   265: ldc -110
    //   267: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   270: pop
    //   271: aload_3
    //   272: astore 4
    //   274: aload 8
    //   276: aload 5
    //   278: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   281: pop
    //   282: aload_3
    //   283: astore 4
    //   285: ldc 21
    //   287: aload 8
    //   289: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   292: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   295: goto +3 -> 298
    //   298: aload_3
    //   299: astore 4
    //   301: aload_0
    //   302: getfield 49	com/tencent/mtt/base/task/WalledGardenDetectTask:jdField_b_of_type_Int	I
    //   305: iconst_1
    //   306: if_icmpne +47 -> 353
    //   309: iload_1
    //   310: sipush 200
    //   313: if_icmpne +40 -> 353
    //   316: aload_3
    //   317: astore 4
    //   319: aload_0
    //   320: aload 7
    //   322: invokespecial 144	com/tencent/mtt/base/task/WalledGardenDetectTask:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   325: astore 6
    //   327: aload_3
    //   328: astore 4
    //   330: aload 6
    //   332: getstatic 148	com/tencent/mtt/base/task/WalledGardenDetectTask:jdField_b_of_type_JavaLangString	Ljava/lang/String;
    //   335: invokestatic 152	android/text/TextUtils:equals	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   338: istore_2
    //   339: iload_2
    //   340: ifeq +30 -> 370
    //   343: aload_3
    //   344: ifnull +7 -> 351
    //   347: aload_3
    //   348: invokevirtual 155	java/net/HttpURLConnection:disconnect	()V
    //   351: iconst_0
    //   352: ireturn
    //   353: iload_1
    //   354: sipush 204
    //   357: if_icmpne +13 -> 370
    //   360: aload_3
    //   361: ifnull +7 -> 368
    //   364: aload_3
    //   365: invokevirtual 155	java/net/HttpURLConnection:disconnect	()V
    //   368: iconst_0
    //   369: ireturn
    //   370: iload_1
    //   371: sipush 200
    //   374: if_icmpeq +17 -> 391
    //   377: iload_1
    //   378: sipush 301
    //   381: if_icmplt +59 -> 440
    //   384: iload_1
    //   385: sipush 307
    //   388: if_icmpgt +52 -> 440
    //   391: aload_3
    //   392: astore 4
    //   394: aload_0
    //   395: aload_0
    //   396: aload_3
    //   397: aload 6
    //   399: invokespecial 158	com/tencent/mtt/base/task/WalledGardenDetectTask:a	(Ljava/net/HttpURLConnection;Ljava/lang/String;)Ljava/lang/String;
    //   402: putfield 74	com/tencent/mtt/base/task/WalledGardenDetectTask:e	Ljava/lang/String;
    //   405: iload_1
    //   406: sipush 301
    //   409: if_icmplt +31 -> 440
    //   412: iload_1
    //   413: sipush 307
    //   416: if_icmpgt +24 -> 440
    //   419: aload_3
    //   420: astore 4
    //   422: aload 5
    //   424: invokestatic 124	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   427: ifne +13 -> 440
    //   430: aload_3
    //   431: astore 4
    //   433: aload_0
    //   434: iload_1
    //   435: aload 5
    //   437: invokespecial 161	com/tencent/mtt/base/task/WalledGardenDetectTask:a	(ILjava/lang/String;)V
    //   440: aload_3
    //   441: astore 4
    //   443: new 56	java/lang/StringBuilder
    //   446: dup
    //   447: invokespecial 57	java/lang/StringBuilder:<init>	()V
    //   450: astore 5
    //   452: aload_3
    //   453: astore 4
    //   455: aload 5
    //   457: ldc -93
    //   459: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   462: pop
    //   463: aload_3
    //   464: astore 4
    //   466: aload 5
    //   468: aload_0
    //   469: getfield 165	com/tencent/mtt/base/task/WalledGardenDetectTask:mSsid	Ljava/lang/String;
    //   472: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   475: pop
    //   476: aload_3
    //   477: astore 4
    //   479: aload 5
    //   481: ldc -89
    //   483: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   486: pop
    //   487: aload_3
    //   488: astore 4
    //   490: aload 5
    //   492: aload_0
    //   493: getfield 74	com/tencent/mtt/base/task/WalledGardenDetectTask:e	Ljava/lang/String;
    //   496: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   499: pop
    //   500: aload_3
    //   501: astore 4
    //   503: ldc 21
    //   505: aload 5
    //   507: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   510: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   513: aload_3
    //   514: astore 4
    //   516: aload_0
    //   517: getfield 74	com/tencent/mtt/base/task/WalledGardenDetectTask:e	Ljava/lang/String;
    //   520: invokestatic 124	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   523: istore_2
    //   524: iload_2
    //   525: ifne +13 -> 538
    //   528: aload_3
    //   529: ifnull +7 -> 536
    //   532: aload_3
    //   533: invokevirtual 155	java/net/HttpURLConnection:disconnect	()V
    //   536: iconst_2
    //   537: ireturn
    //   538: iload_1
    //   539: sipush 200
    //   542: if_icmpne +13 -> 555
    //   545: aload_3
    //   546: ifnull +7 -> 553
    //   549: aload_3
    //   550: invokevirtual 155	java/net/HttpURLConnection:disconnect	()V
    //   553: iconst_2
    //   554: ireturn
    //   555: aload_3
    //   556: astore 4
    //   558: new 56	java/lang/StringBuilder
    //   561: dup
    //   562: invokespecial 57	java/lang/StringBuilder:<init>	()V
    //   565: astore 5
    //   567: aload_3
    //   568: astore 4
    //   570: aload 5
    //   572: ldc -87
    //   574: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   577: pop
    //   578: aload_3
    //   579: astore 4
    //   581: aload 5
    //   583: iload_1
    //   584: invokevirtual 141	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   587: pop
    //   588: aload_3
    //   589: astore 4
    //   591: ldc 21
    //   593: aload 5
    //   595: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   598: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   601: aload_3
    //   602: ifnull +7 -> 609
    //   605: aload_3
    //   606: invokevirtual 155	java/net/HttpURLConnection:disconnect	()V
    //   609: iconst_1
    //   610: ireturn
    //   611: astore 5
    //   613: goto +14 -> 627
    //   616: astore_3
    //   617: aconst_null
    //   618: astore 4
    //   620: goto +95 -> 715
    //   623: astore 5
    //   625: aconst_null
    //   626: astore_3
    //   627: aload_3
    //   628: astore 4
    //   630: new 56	java/lang/StringBuilder
    //   633: dup
    //   634: invokespecial 57	java/lang/StringBuilder:<init>	()V
    //   637: astore 6
    //   639: aload_3
    //   640: astore 4
    //   642: aload 6
    //   644: ldc -85
    //   646: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   649: pop
    //   650: aload_3
    //   651: astore 4
    //   653: aload 6
    //   655: aload 5
    //   657: invokevirtual 174	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   660: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   663: pop
    //   664: aload_3
    //   665: astore 4
    //   667: ldc 21
    //   669: aload 6
    //   671: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   674: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   677: aload_3
    //   678: astore 4
    //   680: aload_0
    //   681: aload 5
    //   683: invokevirtual 174	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   686: putfield 76	com/tencent/mtt/base/task/WalledGardenDetectTask:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   689: aload_3
    //   690: astore 4
    //   692: aload_0
    //   693: iconst_0
    //   694: ldc -80
    //   696: aload 5
    //   698: invokevirtual 174	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   701: invokespecial 179	com/tencent/mtt/base/task/WalledGardenDetectTask:a	(ILjava/lang/String;Ljava/lang/String;)V
    //   704: aload_3
    //   705: ifnull +7 -> 712
    //   708: aload_3
    //   709: invokevirtual 155	java/net/HttpURLConnection:disconnect	()V
    //   712: iconst_3
    //   713: ireturn
    //   714: astore_3
    //   715: aload 4
    //   717: ifnull +8 -> 725
    //   720: aload 4
    //   722: invokevirtual 155	java/net/HttpURLConnection:disconnect	()V
    //   725: aload_3
    //   726: athrow
    //   727: aconst_null
    //   728: astore 5
    //   730: goto -432 -> 298
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	733	0	this	WalledGardenDetectTask
    //   175	409	1	i	int
    //   338	187	2	bool	boolean
    //   7	599	3	localObject1	Object
    //   616	1	3	localObject2	Object
    //   626	83	3	localObject3	Object
    //   714	12	3	localObject4	Object
    //   102	619	4	localObject5	Object
    //   194	400	5	localObject6	Object
    //   611	1	5	localThrowable1	Throwable
    //   623	74	5	localThrowable2	Throwable
    //   728	1	5	localObject7	Object
    //   34	636	6	localObject8	Object
    //   166	155	7	localInputStream	java.io.InputStream
    //   258	30	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   104	109	611	java/lang/Throwable
    //   112	118	611	java/lang/Throwable
    //   121	127	611	java/lang/Throwable
    //   130	135	611	java/lang/Throwable
    //   138	147	611	java/lang/Throwable
    //   150	159	611	java/lang/Throwable
    //   162	168	611	java/lang/Throwable
    //   171	176	611	java/lang/Throwable
    //   179	184	611	java/lang/Throwable
    //   187	196	611	java/lang/Throwable
    //   199	207	611	java/lang/Throwable
    //   210	217	611	java/lang/Throwable
    //   220	230	611	java/lang/Throwable
    //   240	248	611	java/lang/Throwable
    //   251	260	611	java/lang/Throwable
    //   263	271	611	java/lang/Throwable
    //   274	282	611	java/lang/Throwable
    //   285	295	611	java/lang/Throwable
    //   301	309	611	java/lang/Throwable
    //   319	327	611	java/lang/Throwable
    //   330	339	611	java/lang/Throwable
    //   394	405	611	java/lang/Throwable
    //   422	430	611	java/lang/Throwable
    //   433	440	611	java/lang/Throwable
    //   443	452	611	java/lang/Throwable
    //   455	463	611	java/lang/Throwable
    //   466	476	611	java/lang/Throwable
    //   479	487	611	java/lang/Throwable
    //   490	500	611	java/lang/Throwable
    //   503	513	611	java/lang/Throwable
    //   516	524	611	java/lang/Throwable
    //   558	567	611	java/lang/Throwable
    //   570	578	611	java/lang/Throwable
    //   581	588	611	java/lang/Throwable
    //   591	601	611	java/lang/Throwable
    //   51	90	616	finally
    //   93	101	616	finally
    //   51	90	623	java/lang/Throwable
    //   93	101	623	java/lang/Throwable
    //   104	109	714	finally
    //   112	118	714	finally
    //   121	127	714	finally
    //   130	135	714	finally
    //   138	147	714	finally
    //   150	159	714	finally
    //   162	168	714	finally
    //   171	176	714	finally
    //   179	184	714	finally
    //   187	196	714	finally
    //   199	207	714	finally
    //   210	217	714	finally
    //   220	230	714	finally
    //   240	248	714	finally
    //   251	260	714	finally
    //   263	271	714	finally
    //   274	282	714	finally
    //   285	295	714	finally
    //   301	309	714	finally
    //   319	327	714	finally
    //   330	339	714	finally
    //   394	405	714	finally
    //   422	430	714	finally
    //   433	440	714	finally
    //   443	452	714	finally
    //   455	463	714	finally
    //   466	476	714	finally
    //   479	487	714	finally
    //   490	500	714	finally
    //   503	513	714	finally
    //   516	524	714	finally
    //   558	567	714	finally
    //   570	578	714	finally
    //   581	588	714	finally
    //   591	601	714	finally
    //   630	639	714	finally
    //   642	650	714	finally
    //   653	664	714	finally
    //   667	677	714	finally
    //   680	689	714	finally
    //   692	704	714	finally
  }
  
  /* Error */
  private String a(java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: new 183	java/io/BufferedReader
    //   3: dup
    //   4: new 185	java/io/InputStreamReader
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 188	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   12: invokespecial 191	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   15: astore_1
    //   16: new 56	java/lang/StringBuilder
    //   19: dup
    //   20: invokespecial 57	java/lang/StringBuilder:<init>	()V
    //   23: astore_2
    //   24: aload_1
    //   25: invokevirtual 194	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   28: astore_3
    //   29: aload_3
    //   30: ifnull +12 -> 42
    //   33: aload_2
    //   34: aload_3
    //   35: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: pop
    //   39: goto -15 -> 24
    //   42: aload_2
    //   43: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   46: astore_2
    //   47: aload_1
    //   48: invokevirtual 197	java/io/BufferedReader:close	()V
    //   51: aload_2
    //   52: areturn
    //   53: astore_1
    //   54: aload_1
    //   55: invokevirtual 200	java/io/IOException:printStackTrace	()V
    //   58: aload_2
    //   59: areturn
    //   60: astore_2
    //   61: goto +22 -> 83
    //   64: astore_2
    //   65: aload_2
    //   66: invokevirtual 200	java/io/IOException:printStackTrace	()V
    //   69: aload_1
    //   70: invokevirtual 197	java/io/BufferedReader:close	()V
    //   73: goto +8 -> 81
    //   76: astore_1
    //   77: aload_1
    //   78: invokevirtual 200	java/io/IOException:printStackTrace	()V
    //   81: aconst_null
    //   82: areturn
    //   83: aload_1
    //   84: invokevirtual 197	java/io/BufferedReader:close	()V
    //   87: goto +8 -> 95
    //   90: astore_1
    //   91: aload_1
    //   92: invokevirtual 200	java/io/IOException:printStackTrace	()V
    //   95: aload_2
    //   96: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	97	0	this	WalledGardenDetectTask
    //   0	97	1	paramInputStream	java.io.InputStream
    //   23	36	2	localObject1	Object
    //   60	1	2	localObject2	Object
    //   64	32	2	localIOException	java.io.IOException
    //   28	7	3	str	String
    // Exception table:
    //   from	to	target	type
    //   47	51	53	java/io/IOException
    //   24	29	60	finally
    //   33	39	60	finally
    //   42	47	60	finally
    //   65	69	60	finally
    //   24	29	64	java/io/IOException
    //   33	39	64	java/io/IOException
    //   42	47	64	java/io/IOException
    //   69	73	76	java/io/IOException
    //   83	87	90	java/io/IOException
  }
  
  private String a(HttpURLConnection paramHttpURLConnection, String paramString)
  {
    String str1 = null;
    Object localObject2 = null;
    localObject1 = localObject2;
    paramString = str1;
    try
    {
      if (TextUtils.isEmpty(null))
      {
        localObject1 = localObject2;
        paramString = str1;
        if (!TextUtils.isEmpty(paramHttpURLConnection.getHeaderField("Location")))
        {
          paramString = str1;
          localObject1 = paramHttpURLConnection.getHeaderField("Location");
        }
      }
      paramString = (String)localObject1;
      str1 = new URL(this.jdField_c_of_type_JavaLangString).getHost();
      paramString = (String)localObject1;
      String str2 = paramHttpURLConnection.getURL().getHost();
      paramString = (String)localObject1;
      localObject2 = new StringBuilder();
      paramString = (String)localObject1;
      ((StringBuilder)localObject2).append("seq=1;host=");
      paramString = (String)localObject1;
      ((StringBuilder)localObject2).append(str1);
      paramString = (String)localObject1;
      ((StringBuilder)localObject2).append(";mWalledGardenUrl=");
      paramString = (String)localObject1;
      ((StringBuilder)localObject2).append(this.jdField_c_of_type_JavaLangString);
      paramString = (String)localObject1;
      ((StringBuilder)localObject2).append(";newHost=");
      paramString = (String)localObject1;
      ((StringBuilder)localObject2).append(str2);
      paramString = (String)localObject1;
      FLogger.d("WalledGardenDetectTask", ((StringBuilder)localObject2).toString());
      localObject2 = localObject1;
      paramString = (String)localObject1;
      if (!TextUtils.equals(str1, str2))
      {
        paramString = (String)localObject1;
        localObject2 = paramHttpURLConnection.getURL().toExternalForm();
      }
      localObject1 = localObject2;
      paramString = (String)localObject2;
      if (TextUtils.isEmpty((CharSequence)localObject2))
      {
        localObject1 = localObject2;
        paramString = (String)localObject2;
        if (!TextUtils.isEmpty(paramHttpURLConnection.getHeaderField("Refresh")))
        {
          paramString = (String)localObject2;
          paramHttpURLConnection = paramHttpURLConnection.getHeaderField("Refresh").split(";");
          localObject1 = localObject2;
          if (paramHttpURLConnection != null)
          {
            localObject1 = localObject2;
            paramString = (String)localObject2;
            if (paramHttpURLConnection.length == 2)
            {
              paramString = (String)localObject2;
              localObject1 = paramHttpURLConnection[1].trim();
            }
          }
        }
      }
    }
    catch (Throwable paramHttpURLConnection)
    {
      for (;;)
      {
        localObject1 = paramString;
      }
    }
    paramHttpURLConnection = new StringBuilder();
    paramHttpURLConnection.append("seq=2;url=");
    paramHttpURLConnection.append((String)localObject1);
    FLogger.d("WalledGardenDetectTask", paramHttpURLConnection.toString());
    return (String)localObject1;
  }
  
  private void a(int paramInt, String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("type", "wifi_30x");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramInt);
    localStringBuilder.append("");
    localHashMap.put("key1", localStringBuilder.toString());
    localHashMap.put("key2", paramString);
    StatServerHolder.statWithBeacon("MTT_DEV_DEBUG_ACTION", localHashMap);
  }
  
  private void a(int paramInt, String paramString1, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("type", "wifi_link_error");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramInt);
    localStringBuilder.append("");
    localHashMap.put("key1", localStringBuilder.toString());
    localHashMap.put("key2", paramString1);
    localHashMap.put("key3", paramString2);
    StatServerHolder.statWithBeacon("MTT_DEV_DEBUG_ACTION", localHashMap);
  }
  
  public void cancel()
  {
    this.jdField_a_of_type_Boolean = true;
  }
  
  public boolean detect()
  {
    initUserAgent();
    return a() == 0;
  }
  
  public WalledGardenMessage getMessage()
  {
    return this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenMessage;
  }
  
  public String getRedirectUrl()
  {
    return this.e;
  }
  
  public int getResult()
  {
    return this.jdField_c_of_type_Int;
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    if (paramMessage.what == 100)
    {
      this.jdField_a_of_type_Boolean = true;
      this.jdField_c_of_type_Int = 3;
      if (this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenMessage == null)
      {
        this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenMessage = new WalledGardenMessage();
        this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenMessage.tag = this.mTag;
        this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenMessage.ssid = this.mSsid;
      }
      paramMessage = this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenMessage;
      paramMessage.httpCode = -1;
      paramMessage.exceptionMsg = "超时,主动返回";
      paramMessage.result = this.jdField_c_of_type_Int;
      paramMessage.delayed = 150000;
      fireObserverEvent();
      paramMessage = new StringBuilder();
      paramMessage.append("seq=1;mSsid=");
      paramMessage.append(this.mSsid);
      paramMessage.append(";mResult=");
      paramMessage.append(this.jdField_c_of_type_Int);
      FLogger.d("WalledGardenDetectTask", paramMessage.toString());
    }
    return false;
  }
  
  public void initUserAgent()
  {
    Locale localLocale;
    StringBuffer localStringBuffer;
    if (d == null)
    {
      localLocale = Locale.getDefault();
      localStringBuffer = new StringBuffer();
      localObject = Build.VERSION.RELEASE;
    }
    try
    {
      str = new String(((String)localObject).getBytes("UTF-8"), "ISO8859-1");
      localObject = str;
    }
    catch (Exception localException1)
    {
      String str;
      for (;;) {}
    }
    if (((String)localObject).length() > 0) {
      localStringBuffer.append((String)localObject);
    } else {
      localStringBuffer.append("1.0");
    }
    localStringBuffer.append("; ");
    Object localObject = localLocale.getLanguage();
    if (localObject != null)
    {
      localStringBuffer.append(((String)localObject).toLowerCase());
      localObject = localLocale.getCountry();
      if (localObject != null)
      {
        localStringBuffer.append("-");
        localStringBuffer.append(((String)localObject).toLowerCase());
      }
    }
    else
    {
      localStringBuffer.append("en");
    }
    if ("REL".equals(Build.VERSION.CODENAME)) {
      localObject = Build.MODEL;
    }
    try
    {
      str = new String(((String)localObject).getBytes("UTF-8"), "ISO8859-1");
      localObject = str;
    }
    catch (Exception localException2)
    {
      for (;;) {}
    }
    if (((String)localObject).length() > 0)
    {
      localStringBuffer.append("; ");
      localStringBuffer.append((String)localObject);
    }
    localObject = Build.ID.replaceAll("[一-龥]", "");
    if (((String)localObject).length() > 0)
    {
      localStringBuffer.append(" Build/");
      localStringBuffer.append((String)localObject);
    }
    d = String.format("Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 %s%s Mobile Safari/537.36", new Object[] { localStringBuffer, "Chrome/37.0.0.0", " MQQBrowser/6.9" });
  }
  
  public void run()
  {
    long l = SystemClock.elapsedRealtime();
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenMessage = new WalledGardenMessage();
    this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenMessage.tag = this.mTag;
    this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenMessage.ssid = this.mSsid;
    if (this.jdField_a_of_type_AndroidOsHandler == null) {
      this.jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper(), this);
    }
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(100);
    this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(100, 150000L);
    initUserAgent();
    this.jdField_c_of_type_Int = a();
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(100);
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    WalledGardenMessage localWalledGardenMessage = this.jdField_a_of_type_ComTencentMttBaseTaskWalledGardenMessage;
    localWalledGardenMessage.httpCode = this.jdField_a_of_type_Int;
    localWalledGardenMessage.exceptionMsg = this.jdField_a_of_type_JavaLangString;
    localWalledGardenMessage.result = this.jdField_c_of_type_Int;
    localWalledGardenMessage.delayed = ((int)(SystemClock.elapsedRealtime() - l));
    fireObserverEvent();
  }
  
  public void setDetectMode(int paramInt)
  {
    this.jdField_b_of_type_Int = paramInt;
    if (this.jdField_b_of_type_Int == 1) {
      setWalledGardenUrl("http://res.imtt.qq.com/qbprobe/netprobe.txt");
    }
  }
  
  public void setNetwork(Network paramNetwork)
  {
    this.jdField_a_of_type_AndroidNetNetwork = paramNetwork;
  }
  
  public void setSsid(String paramString)
  {
    this.mSsid = paramString;
  }
  
  public void setWalledGardenUrl(String paramString)
  {
    this.jdField_c_of_type_JavaLangString = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\task\WalledGardenDetectTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */