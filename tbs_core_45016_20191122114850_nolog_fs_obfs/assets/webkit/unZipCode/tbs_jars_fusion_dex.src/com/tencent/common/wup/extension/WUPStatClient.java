package com.tencent.common.wup.extension;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.RequestPolicy;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.common.wup.WUPTaskProxy;
import com.tencent.mtt.ContextHolder;
import com.tencent.mtt.base.task.Task.Priority;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WUPStatClient
  implements IWUPRequestCallBack
{
  public static final String TAG = "WUPStatClient";
  private static AtomicInteger jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger = new AtomicInteger((int)(System.currentTimeMillis() % 5000L + 10000L));
  private static int jdField_b_of_type_Int = 25;
  volatile int jdField_a_of_type_Int = 0;
  b jdField_a_of_type_ComTencentCommonWupExtensionWUPStatClient$b = null;
  a jdField_a_of_type_ComTencentCommonWupExtensionA = null;
  volatile boolean jdField_a_of_type_Boolean = false;
  byte[] jdField_a_of_type_ArrayOfByte = new byte[0];
  a jdField_b_of_type_ComTencentCommonWupExtensionA = null;
  boolean jdField_b_of_type_Boolean = false;
  byte[] jdField_b_of_type_ArrayOfByte = new byte[0];
  boolean jdField_c_of_type_Boolean = false;
  byte[] jdField_c_of_type_ArrayOfByte = new byte[0];
  
  private WUPStatClient()
  {
    synchronized (this.jdField_a_of_type_ArrayOfByte)
    {
      this.jdField_a_of_type_ComTencentCommonWupExtensionA = new a();
      loadFailList();
      return;
    }
  }
  
  private static int a()
  {
    return jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger.getAndAdd(1);
  }
  
  /* Error */
  private List<WUPStatRequest> a(File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 8
    //   3: aconst_null
    //   4: astore 4
    //   6: aload_1
    //   7: ifnull +834 -> 841
    //   10: aload_1
    //   11: invokevirtual 107	java/io/File:exists	()Z
    //   14: ifne +6 -> 20
    //   17: goto +824 -> 841
    //   20: new 109	java/util/ArrayList
    //   23: dup
    //   24: invokespecial 110	java/util/ArrayList:<init>	()V
    //   27: astore 5
    //   29: new 112	java/io/DataInputStream
    //   32: dup
    //   33: aload_1
    //   34: invokestatic 118	com/tencent/common/utils/FileUtilsF:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   37: invokespecial 121	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   40: astore 7
    //   42: aload 5
    //   44: astore 4
    //   46: aload 7
    //   48: invokevirtual 124	java/io/DataInputStream:readInt	()I
    //   51: ldc 125
    //   53: if_icmpne +671 -> 724
    //   56: aload 5
    //   58: astore 4
    //   60: aload 7
    //   62: invokevirtual 124	java/io/DataInputStream:readInt	()I
    //   65: istore_3
    //   66: iconst_0
    //   67: istore_2
    //   68: iload_2
    //   69: iload_3
    //   70: if_icmpge +226 -> 296
    //   73: aload 5
    //   75: astore 4
    //   77: new 127	com/tencent/common/wup/extension/WUPStatRequest
    //   80: dup
    //   81: invokespecial 128	com/tencent/common/wup/extension/WUPStatRequest:<init>	()V
    //   84: astore 6
    //   86: aload 5
    //   88: astore 4
    //   90: aload 6
    //   92: aload 7
    //   94: invokevirtual 132	com/tencent/common/wup/extension/WUPStatRequest:readFrom	(Ljava/io/DataInputStream;)Z
    //   97: ifeq +78 -> 175
    //   100: aload 5
    //   102: astore 4
    //   104: new 134	java/lang/StringBuilder
    //   107: dup
    //   108: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   111: astore 8
    //   113: aload 5
    //   115: astore 4
    //   117: aload 8
    //   119: ldc -119
    //   121: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: pop
    //   125: aload 5
    //   127: astore 4
    //   129: aload 8
    //   131: aload 6
    //   133: invokevirtual 144	com/tencent/common/wup/extension/WUPStatRequest:getRequstID	()I
    //   136: invokevirtual 147	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   139: pop
    //   140: aload 5
    //   142: astore 4
    //   144: ldc 26
    //   146: aload 8
    //   148: invokevirtual 151	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   151: invokestatic 157	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   154: aload 5
    //   156: astore 4
    //   158: aload 5
    //   160: aload 6
    //   162: invokeinterface 163 2 0
    //   167: pop
    //   168: iload_2
    //   169: iconst_1
    //   170: iadd
    //   171: istore_2
    //   172: goto -104 -> 68
    //   175: aload 5
    //   177: astore 4
    //   179: new 134	java/lang/StringBuilder
    //   182: dup
    //   183: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   186: astore 6
    //   188: aload 5
    //   190: astore 4
    //   192: aload 6
    //   194: ldc -91
    //   196: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   199: pop
    //   200: aload 5
    //   202: astore 4
    //   204: aload 6
    //   206: aload_1
    //   207: invokevirtual 168	java/io/File:getName	()Ljava/lang/String;
    //   210: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   213: pop
    //   214: aload 5
    //   216: astore 4
    //   218: aload 6
    //   220: ldc -86
    //   222: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   225: pop
    //   226: aload 5
    //   228: astore 4
    //   230: ldc 26
    //   232: aload 6
    //   234: invokevirtual 151	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   237: invokestatic 157	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   240: aload 5
    //   242: astore 4
    //   244: new 134	java/lang/StringBuilder
    //   247: dup
    //   248: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   251: astore 6
    //   253: aload 5
    //   255: astore 4
    //   257: aload 6
    //   259: ldc -84
    //   261: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   264: pop
    //   265: aload 5
    //   267: astore 4
    //   269: aload 6
    //   271: aload_1
    //   272: invokevirtual 168	java/io/File:getName	()Ljava/lang/String;
    //   275: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   278: pop
    //   279: aload 5
    //   281: astore 4
    //   283: new 174	java/lang/RuntimeException
    //   286: dup
    //   287: aload 6
    //   289: invokevirtual 151	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   292: invokespecial 177	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   295: athrow
    //   296: aload 5
    //   298: astore 4
    //   300: new 134	java/lang/StringBuilder
    //   303: dup
    //   304: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   307: astore 6
    //   309: aload 5
    //   311: astore 4
    //   313: aload 6
    //   315: ldc -77
    //   317: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   320: pop
    //   321: aload 5
    //   323: astore 4
    //   325: aload 6
    //   327: aload 5
    //   329: invokeinterface 182 1 0
    //   334: invokevirtual 147	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   337: pop
    //   338: aload 5
    //   340: astore 4
    //   342: aload 6
    //   344: ldc -72
    //   346: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   349: pop
    //   350: aload 5
    //   352: astore 4
    //   354: aload 6
    //   356: aload_1
    //   357: invokevirtual 168	java/io/File:getName	()Ljava/lang/String;
    //   360: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   363: pop
    //   364: aload 5
    //   366: astore 4
    //   368: ldc 26
    //   370: aload 6
    //   372: invokevirtual 151	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   375: invokestatic 157	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   378: aload 5
    //   380: astore 6
    //   382: aload 5
    //   384: astore 4
    //   386: aload 5
    //   388: invokeinterface 182 1 0
    //   393: getstatic 55	com/tencent/common/wup/extension/WUPStatClient:jdField_b_of_type_Int	I
    //   396: if_icmple +226 -> 622
    //   399: aload 5
    //   401: astore 4
    //   403: aload 5
    //   405: invokeinterface 182 1 0
    //   410: getstatic 55	com/tencent/common/wup/extension/WUPStatClient:jdField_b_of_type_Int	I
    //   413: isub
    //   414: istore_2
    //   415: aload 5
    //   417: astore 4
    //   419: aload 5
    //   421: invokeinterface 182 1 0
    //   426: istore_3
    //   427: aload 5
    //   429: astore 4
    //   431: new 134	java/lang/StringBuilder
    //   434: dup
    //   435: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   438: astore 6
    //   440: aload 5
    //   442: astore 4
    //   444: aload 6
    //   446: ldc -70
    //   448: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   451: pop
    //   452: aload 5
    //   454: astore 4
    //   456: aload 6
    //   458: getstatic 55	com/tencent/common/wup/extension/WUPStatClient:jdField_b_of_type_Int	I
    //   461: invokevirtual 147	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   464: pop
    //   465: aload 5
    //   467: astore 4
    //   469: aload 6
    //   471: ldc -68
    //   473: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   476: pop
    //   477: aload 5
    //   479: astore 4
    //   481: aload 6
    //   483: iload_2
    //   484: invokevirtual 147	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   487: pop
    //   488: aload 5
    //   490: astore 4
    //   492: aload 6
    //   494: ldc -66
    //   496: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   499: pop
    //   500: aload 5
    //   502: astore 4
    //   504: aload 6
    //   506: iload_3
    //   507: iconst_1
    //   508: isub
    //   509: invokevirtual 147	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   512: pop
    //   513: aload 5
    //   515: astore 4
    //   517: aload 6
    //   519: ldc -64
    //   521: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   524: pop
    //   525: aload 5
    //   527: astore 4
    //   529: ldc 26
    //   531: aload 6
    //   533: invokevirtual 151	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   536: invokestatic 157	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   539: aload 5
    //   541: astore 4
    //   543: aload 5
    //   545: iload_2
    //   546: iload_3
    //   547: invokeinterface 196 3 0
    //   552: astore 6
    //   554: aload 6
    //   556: astore 4
    //   558: new 134	java/lang/StringBuilder
    //   561: dup
    //   562: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   565: astore 5
    //   567: aload 6
    //   569: astore 4
    //   571: aload 5
    //   573: ldc -58
    //   575: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   578: pop
    //   579: aload 6
    //   581: astore 4
    //   583: aload 5
    //   585: aload 6
    //   587: invokeinterface 182 1 0
    //   592: invokevirtual 147	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   595: pop
    //   596: aload 6
    //   598: astore 4
    //   600: aload 5
    //   602: ldc -56
    //   604: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   607: pop
    //   608: aload 6
    //   610: astore 4
    //   612: ldc 26
    //   614: aload 5
    //   616: invokevirtual 151	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   619: invokestatic 157	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   622: aload 6
    //   624: astore 4
    //   626: new 134	java/lang/StringBuilder
    //   629: dup
    //   630: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   633: astore 5
    //   635: aload 6
    //   637: astore 4
    //   639: aload 5
    //   641: ldc -54
    //   643: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   646: pop
    //   647: aload 6
    //   649: astore 4
    //   651: aload 5
    //   653: aload_1
    //   654: invokevirtual 168	java/io/File:getName	()Ljava/lang/String;
    //   657: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   660: pop
    //   661: aload 6
    //   663: astore 4
    //   665: aload 5
    //   667: ldc -52
    //   669: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   672: pop
    //   673: aload 6
    //   675: astore 4
    //   677: aload 5
    //   679: aload 6
    //   681: invokeinterface 182 1 0
    //   686: invokevirtual 147	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   689: pop
    //   690: aload 6
    //   692: astore 4
    //   694: aload 5
    //   696: ldc -50
    //   698: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   701: pop
    //   702: aload 6
    //   704: astore 4
    //   706: ldc 26
    //   708: aload 5
    //   710: invokevirtual 151	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   713: invokestatic 157	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   716: aload 7
    //   718: invokestatic 210	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   721: aload 6
    //   723: areturn
    //   724: aload 5
    //   726: astore 4
    //   728: new 134	java/lang/StringBuilder
    //   731: dup
    //   732: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   735: astore 6
    //   737: aload 5
    //   739: astore 4
    //   741: aload 6
    //   743: ldc -44
    //   745: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   748: pop
    //   749: aload 5
    //   751: astore 4
    //   753: aload 6
    //   755: aload_1
    //   756: invokevirtual 168	java/io/File:getName	()Ljava/lang/String;
    //   759: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   762: pop
    //   763: aload 5
    //   765: astore 4
    //   767: new 174	java/lang/RuntimeException
    //   770: dup
    //   771: aload 6
    //   773: invokevirtual 151	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   776: invokespecial 177	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   779: athrow
    //   780: astore_1
    //   781: goto +53 -> 834
    //   784: astore 6
    //   786: aload 7
    //   788: astore_1
    //   789: aload 4
    //   791: astore 5
    //   793: goto +16 -> 809
    //   796: astore_1
    //   797: aload 4
    //   799: astore 7
    //   801: goto +33 -> 834
    //   804: astore 6
    //   806: aload 8
    //   808: astore_1
    //   809: aload_1
    //   810: astore 4
    //   812: ldc 26
    //   814: ldc -42
    //   816: invokestatic 157	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   819: aload_1
    //   820: astore 4
    //   822: aload 6
    //   824: invokevirtual 217	java/lang/Throwable:printStackTrace	()V
    //   827: aload_1
    //   828: invokestatic 210	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   831: aload 5
    //   833: areturn
    //   834: aload 7
    //   836: invokestatic 210	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   839: aload_1
    //   840: athrow
    //   841: new 134	java/lang/StringBuilder
    //   844: dup
    //   845: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   848: astore 4
    //   850: aload 4
    //   852: ldc -37
    //   854: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   857: pop
    //   858: aload_1
    //   859: ifnonnull +9 -> 868
    //   862: ldc -35
    //   864: astore_1
    //   865: goto +36 -> 901
    //   868: new 134	java/lang/StringBuilder
    //   871: dup
    //   872: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   875: astore 5
    //   877: aload 5
    //   879: aload_1
    //   880: invokevirtual 168	java/io/File:getName	()Ljava/lang/String;
    //   883: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   886: pop
    //   887: aload 5
    //   889: ldc -33
    //   891: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   894: pop
    //   895: aload 5
    //   897: invokevirtual 151	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   900: astore_1
    //   901: aload 4
    //   903: aload_1
    //   904: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   907: pop
    //   908: ldc 26
    //   910: aload 4
    //   912: invokevirtual 151	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   915: invokestatic 157	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   918: aconst_null
    //   919: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	920	0	this	WUPStatClient
    //   0	920	1	paramFile	File
    //   67	479	2	i	int
    //   65	482	3	j	int
    //   4	907	4	localObject1	Object
    //   27	869	5	localObject2	Object
    //   84	688	6	localObject3	Object
    //   784	1	6	localThrowable1	Throwable
    //   804	19	6	localThrowable2	Throwable
    //   40	795	7	localObject4	Object
    //   1	806	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   46	56	780	finally
    //   60	66	780	finally
    //   77	86	780	finally
    //   90	100	780	finally
    //   104	113	780	finally
    //   117	125	780	finally
    //   129	140	780	finally
    //   144	154	780	finally
    //   158	168	780	finally
    //   179	188	780	finally
    //   192	200	780	finally
    //   204	214	780	finally
    //   218	226	780	finally
    //   230	240	780	finally
    //   244	253	780	finally
    //   257	265	780	finally
    //   269	279	780	finally
    //   283	296	780	finally
    //   300	309	780	finally
    //   313	321	780	finally
    //   325	338	780	finally
    //   342	350	780	finally
    //   354	364	780	finally
    //   368	378	780	finally
    //   386	399	780	finally
    //   403	415	780	finally
    //   419	427	780	finally
    //   431	440	780	finally
    //   444	452	780	finally
    //   456	465	780	finally
    //   469	477	780	finally
    //   481	488	780	finally
    //   492	500	780	finally
    //   504	513	780	finally
    //   517	525	780	finally
    //   529	539	780	finally
    //   543	554	780	finally
    //   558	567	780	finally
    //   571	579	780	finally
    //   583	596	780	finally
    //   600	608	780	finally
    //   612	622	780	finally
    //   626	635	780	finally
    //   639	647	780	finally
    //   651	661	780	finally
    //   665	673	780	finally
    //   677	690	780	finally
    //   694	702	780	finally
    //   706	716	780	finally
    //   728	737	780	finally
    //   741	749	780	finally
    //   753	763	780	finally
    //   767	780	780	finally
    //   46	56	784	java/lang/Throwable
    //   60	66	784	java/lang/Throwable
    //   77	86	784	java/lang/Throwable
    //   90	100	784	java/lang/Throwable
    //   104	113	784	java/lang/Throwable
    //   117	125	784	java/lang/Throwable
    //   129	140	784	java/lang/Throwable
    //   144	154	784	java/lang/Throwable
    //   158	168	784	java/lang/Throwable
    //   179	188	784	java/lang/Throwable
    //   192	200	784	java/lang/Throwable
    //   204	214	784	java/lang/Throwable
    //   218	226	784	java/lang/Throwable
    //   230	240	784	java/lang/Throwable
    //   244	253	784	java/lang/Throwable
    //   257	265	784	java/lang/Throwable
    //   269	279	784	java/lang/Throwable
    //   283	296	784	java/lang/Throwable
    //   300	309	784	java/lang/Throwable
    //   313	321	784	java/lang/Throwable
    //   325	338	784	java/lang/Throwable
    //   342	350	784	java/lang/Throwable
    //   354	364	784	java/lang/Throwable
    //   368	378	784	java/lang/Throwable
    //   386	399	784	java/lang/Throwable
    //   403	415	784	java/lang/Throwable
    //   419	427	784	java/lang/Throwable
    //   431	440	784	java/lang/Throwable
    //   444	452	784	java/lang/Throwable
    //   456	465	784	java/lang/Throwable
    //   469	477	784	java/lang/Throwable
    //   481	488	784	java/lang/Throwable
    //   492	500	784	java/lang/Throwable
    //   504	513	784	java/lang/Throwable
    //   517	525	784	java/lang/Throwable
    //   529	539	784	java/lang/Throwable
    //   543	554	784	java/lang/Throwable
    //   558	567	784	java/lang/Throwable
    //   571	579	784	java/lang/Throwable
    //   583	596	784	java/lang/Throwable
    //   600	608	784	java/lang/Throwable
    //   612	622	784	java/lang/Throwable
    //   626	635	784	java/lang/Throwable
    //   639	647	784	java/lang/Throwable
    //   651	661	784	java/lang/Throwable
    //   665	673	784	java/lang/Throwable
    //   677	690	784	java/lang/Throwable
    //   694	702	784	java/lang/Throwable
    //   706	716	784	java/lang/Throwable
    //   728	737	784	java/lang/Throwable
    //   741	749	784	java/lang/Throwable
    //   753	763	784	java/lang/Throwable
    //   767	780	784	java/lang/Throwable
    //   29	42	796	finally
    //   812	819	796	finally
    //   822	827	796	finally
    //   29	42	804	java/lang/Throwable
  }
  
  private void a()
  {
    this.jdField_a_of_type_Int += 1;
    if (this.jdField_a_of_type_Int > 10)
    {
      FLogger.d("WUPStatClient", "current data has been operated for  10 times, try save");
      saveCurrentFailList(false);
      this.jdField_a_of_type_Int = 0;
    }
  }
  
  private void a(WUPStatRequest paramWUPStatRequest)
  {
    if (paramWUPStatRequest == null) {
      return;
    }
    synchronized (this.jdField_a_of_type_ArrayOfByte)
    {
      if (this.jdField_a_of_type_ComTencentCommonWupExtensionA == null) {
        this.jdField_a_of_type_ComTencentCommonWupExtensionA = new a();
      }
      StringBuilder localStringBuilder;
      if (this.jdField_a_of_type_ComTencentCommonWupExtensionA.a(paramWUPStatRequest))
      {
        this.jdField_a_of_type_Boolean = true;
        a();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("addToCurrentList: add request to pending list (size = ");
        localStringBuilder.append(this.jdField_a_of_type_ComTencentCommonWupExtensionA.a());
        localStringBuilder.append(") , request funcName = ");
        localStringBuilder.append(paramWUPStatRequest.getFuncName());
        FLogger.d("WUPStatClient", localStringBuilder.toString());
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("addToCurrentList: request already exists in pending list, request funcName = ");
        localStringBuilder.append(paramWUPStatRequest.getFuncName());
        FLogger.d("WUPStatClient", localStringBuilder.toString());
      }
      return;
    }
  }
  
  private void a(a parama)
  {
    if ((parama != null) && (parama.a() > 0))
    {
      parama = parama.a(this);
      int i = 0;
      while (i < parama.size())
      {
        WUPRequestBase localWUPRequestBase = (WUPRequestBase)parama.get(i);
        localWUPRequestBase.setRequestName("multi_wup_stat");
        localWUPRequestBase.setRequestCallBack(this);
        localWUPRequestBase.setNeedEncrypt(true);
        localWUPRequestBase.setPriority(Task.Priority.LOW);
        localWUPRequestBase.setRequestPolicy(RequestPolicy.FAST_MODE_POLICY);
        WUPTaskProxy.send(localWUPRequestBase);
        i += 1;
      }
      return;
    }
    FLogger.d("WUPStatClient", "doSendPrevFailReqs: request is null, or empty, ignore");
  }
  
  /* Error */
  private void a(a parama, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +4 -> 5
    //   4: return
    //   5: iload_2
    //   6: iconst_1
    //   7: ixor
    //   8: invokestatic 95	com/tencent/common/wup/extension/WUPStatClient:b	(Z)Ljava/io/File;
    //   11: astore 6
    //   13: aload 6
    //   15: ifnull +266 -> 281
    //   18: aconst_null
    //   19: astore 5
    //   21: aconst_null
    //   22: astore 4
    //   24: aload 4
    //   26: astore_3
    //   27: aload 6
    //   29: invokevirtual 107	java/io/File:exists	()Z
    //   32: ifne +12 -> 44
    //   35: aload 4
    //   37: astore_3
    //   38: aload 6
    //   40: invokevirtual 309	java/io/File:createNewFile	()Z
    //   43: pop
    //   44: aload 4
    //   46: astore_3
    //   47: new 311	java/io/DataOutputStream
    //   50: dup
    //   51: aload 6
    //   53: invokestatic 315	com/tencent/common/utils/FileUtilsF:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
    //   56: invokespecial 318	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   59: astore 4
    //   61: aload 4
    //   63: ldc 125
    //   65: invokevirtual 321	java/io/DataOutputStream:writeInt	(I)V
    //   68: aload 4
    //   70: aload_1
    //   71: invokevirtual 249	com/tencent/common/wup/extension/a:a	()I
    //   74: invokevirtual 321	java/io/DataOutputStream:writeInt	(I)V
    //   77: aload_1
    //   78: getfield 324	com/tencent/common/wup/extension/a:a	Ljava/util/List;
    //   81: ifnull +96 -> 177
    //   84: aload_1
    //   85: getfield 324	com/tencent/common/wup/extension/a:a	Ljava/util/List;
    //   88: invokeinterface 328 1 0
    //   93: astore_1
    //   94: aload_1
    //   95: invokeinterface 333 1 0
    //   100: ifeq +77 -> 177
    //   103: aload_1
    //   104: invokeinterface 337 1 0
    //   109: checkcast 127	com/tencent/common/wup/extension/WUPStatRequest
    //   112: astore_3
    //   113: aload_3
    //   114: aload 4
    //   116: invokestatic 338	com/tencent/common/wup/extension/WUPStatClient:a	()I
    //   119: invokevirtual 342	com/tencent/common/wup/extension/WUPStatRequest:writeTo	(Ljava/io/DataOutputStream;I)Z
    //   122: ifne +14 -> 136
    //   125: ldc 26
    //   127: ldc_w 344
    //   130: invokestatic 157	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   133: goto -39 -> 94
    //   136: new 134	java/lang/StringBuilder
    //   139: dup
    //   140: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   143: astore 5
    //   145: aload 5
    //   147: ldc_w 346
    //   150: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   153: pop
    //   154: aload 5
    //   156: aload_3
    //   157: invokevirtual 144	com/tencent/common/wup/extension/WUPStatRequest:getRequstID	()I
    //   160: invokevirtual 147	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   163: pop
    //   164: ldc 26
    //   166: aload 5
    //   168: invokevirtual 151	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   171: invokestatic 157	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   174: goto -80 -> 94
    //   177: new 134	java/lang/StringBuilder
    //   180: dup
    //   181: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   184: astore_1
    //   185: aload_1
    //   186: ldc_w 348
    //   189: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: pop
    //   193: aload_1
    //   194: aload 6
    //   196: invokevirtual 351	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   199: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: pop
    //   203: aload_1
    //   204: ldc_w 353
    //   207: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: pop
    //   211: ldc 26
    //   213: aload_1
    //   214: invokevirtual 151	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   217: invokestatic 157	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   220: aload 4
    //   222: invokestatic 210	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   225: return
    //   226: astore_1
    //   227: goto +47 -> 274
    //   230: astore_3
    //   231: aload 4
    //   233: astore_1
    //   234: aload_3
    //   235: astore 4
    //   237: goto +15 -> 252
    //   240: astore_1
    //   241: aload_3
    //   242: astore 4
    //   244: goto +30 -> 274
    //   247: astore 4
    //   249: aload 5
    //   251: astore_1
    //   252: aload_1
    //   253: astore_3
    //   254: ldc 26
    //   256: ldc_w 355
    //   259: invokestatic 157	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   262: aload_1
    //   263: astore_3
    //   264: aload 4
    //   266: invokevirtual 217	java/lang/Throwable:printStackTrace	()V
    //   269: aload_1
    //   270: invokestatic 210	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   273: return
    //   274: aload 4
    //   276: invokestatic 210	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   279: aload_1
    //   280: athrow
    //   281: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	282	0	this	WUPStatClient
    //   0	282	1	parama	a
    //   0	282	2	paramBoolean	boolean
    //   26	131	3	localObject1	Object
    //   230	12	3	localThrowable1	Throwable
    //   253	11	3	locala	a
    //   22	221	4	localObject2	Object
    //   247	28	4	localThrowable2	Throwable
    //   19	231	5	localStringBuilder	StringBuilder
    //   11	184	6	localFile	File
    // Exception table:
    //   from	to	target	type
    //   61	94	226	finally
    //   94	133	226	finally
    //   136	174	226	finally
    //   177	220	226	finally
    //   61	94	230	java/lang/Throwable
    //   94	133	230	java/lang/Throwable
    //   136	174	230	java/lang/Throwable
    //   177	220	230	java/lang/Throwable
    //   27	35	240	finally
    //   38	44	240	finally
    //   47	61	240	finally
    //   254	262	240	finally
    //   264	269	240	finally
    //   27	35	247	java/lang/Throwable
    //   38	44	247	java/lang/Throwable
    //   47	61	247	java/lang/Throwable
  }
  
  private boolean a(int paramInt)
  {
    if (paramInt < 0) {
      return false;
    }
    ??? = new StringBuilder();
    ((StringBuilder)???).append("deletePartialStatReq: deletePartialStatReq called, id=");
    ((StringBuilder)???).append(paramInt);
    FLogger.d("WUPStatClient", ((StringBuilder)???).toString());
    synchronized (this.jdField_b_of_type_ArrayOfByte)
    {
      if (this.jdField_b_of_type_ComTencentCommonWupExtensionA == null) {
        return false;
      }
      if (this.jdField_b_of_type_ComTencentCommonWupExtensionA.a(paramInt))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("deletePartialStatReq: removeDataById returns true, id=");
        localStringBuilder.append(paramInt);
        localStringBuilder.append(", curr size=");
        localStringBuilder.append(this.jdField_b_of_type_ComTencentCommonWupExtensionA.a());
        FLogger.d("WUPStatClient", localStringBuilder.toString());
        b();
        return true;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("deletePartialStatReq: removeDataById returns false, id=");
      localStringBuilder.append(paramInt);
      FLogger.d("WUPStatClient", localStringBuilder.toString());
      return false;
    }
  }
  
  private static File b(boolean paramBoolean)
  {
    Object localObject2 = ThreadUtils.getCurrentProcessName(ContextHolder.getAppContext());
    Object localObject1 = localObject2;
    if (!TextUtils.isEmpty((CharSequence)localObject2)) {
      localObject1 = ((String)localObject2).replace(":", "_");
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("_");
    ((StringBuilder)localObject2).append("wup_stat_cache_file");
    if (paramBoolean) {
      localObject1 = ".non_rt";
    } else {
      localObject1 = ".rt";
    }
    ((StringBuilder)localObject2).append((String)localObject1);
    localObject1 = ((StringBuilder)localObject2).toString();
    return new File(FileUtilsF.getDataDir(ContextHolder.getAppContext()), (String)localObject1);
  }
  
  private void b()
  {
    FLogger.d("WUPStatClient", "savePartialFailList: Begin to schedule saving current data to file");
    this.jdField_a_of_type_ComTencentCommonWupExtensionWUPStatClient$b.a(new Runnable()
    {
      public void run()
      {
        FLogger.d("WUPStatClient", "savePartialFailList: Saving partial data to file in thread");
        for (;;)
        {
          synchronized (WUPStatClient.this.jdField_b_of_type_ArrayOfByte)
          {
            if (WUPStatClient.this.jdField_b_of_type_ComTencentCommonWupExtensionA != null)
            {
              a locala = WUPStatClient.this.jdField_b_of_type_ComTencentCommonWupExtensionA.a();
              if (locala != null) {
                WUPStatClient.a(WUPStatClient.this, locala, false);
              }
              FLogger.d("WUPStatClient", "savePartialFailList: Save complete ");
              return;
            }
          }
          Object localObject2 = null;
        }
      }
    });
  }
  
  private void b(WUPStatRequest paramWUPStatRequest)
  {
    if (paramWUPStatRequest == null) {
      return;
    }
    synchronized (this.jdField_a_of_type_ArrayOfByte)
    {
      StringBuilder localStringBuilder;
      if (this.jdField_a_of_type_ComTencentCommonWupExtensionA == null)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("removeFromCurrentList: current pending list is empty, ignore, request funcName = ");
        localStringBuilder.append(paramWUPStatRequest.getFuncName());
        FLogger.d("WUPStatClient", localStringBuilder.toString());
        return;
      }
      if (this.jdField_a_of_type_ComTencentCommonWupExtensionA.b(paramWUPStatRequest))
      {
        this.jdField_a_of_type_Boolean = true;
        a();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("removeFromCurrentList: remove request from pending list (size = ");
        localStringBuilder.append(this.jdField_a_of_type_ComTencentCommonWupExtensionA.a());
        localStringBuilder.append(") , request funcName = ");
        localStringBuilder.append(paramWUPStatRequest.getFuncName());
        FLogger.d("WUPStatClient", localStringBuilder.toString());
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("removeFromCurrentList: data not existing pending list, ignore, request funcName = ");
        localStringBuilder.append(paramWUPStatRequest.getFuncName());
        FLogger.d("WUPStatClient", localStringBuilder.toString());
      }
      return;
    }
  }
  
  public static WUPStatClient getDefault()
  {
    return a.a();
  }
  
  public boolean cancelNonRealTimeReq(int paramInt)
  {
    return a(paramInt);
  }
  
  public void loadFailList()
  {
    FLogger.d("WUPStatClient", "loadFailList: loadFailList called");
    synchronized (this.jdField_c_of_type_ArrayOfByte)
    {
      if (this.jdField_b_of_type_Boolean)
      {
        FLogger.d("WUPStatClient", "loadFailList: we have load data already, ignore this request");
        return;
      }
      this.jdField_b_of_type_Boolean = true;
      FLogger.d("WUPStatClient", "loadFailList: begin load data from file");
      this.jdField_a_of_type_ComTencentCommonWupExtensionWUPStatClient$b.a(new Runnable()
      {
        public void run()
        {
          FLogger.d("WUPStatClient", "loadFailList: load stat file begins");
          File localFile1 = WUPStatClient.a(false);
          Object localObject2 = WUPStatClient.a(WUPStatClient.this, localFile1);
          if (localObject2 != null) {
            synchronized (WUPStatClient.this.jdField_a_of_type_ArrayOfByte)
            {
              if (WUPStatClient.this.jdField_a_of_type_ComTencentCommonWupExtensionA == null) {
                WUPStatClient.this.jdField_a_of_type_ComTencentCommonWupExtensionA = new a();
              }
              WUPStatClient.this.jdField_a_of_type_Boolean = WUPStatClient.this.jdField_a_of_type_ComTencentCommonWupExtensionA.a((List)localObject2);
              ??? = new StringBuilder();
              ((StringBuilder)???).append("loadFailList: load data from file ");
              ((StringBuilder)???).append(localFile1.getName());
              ((StringBuilder)???).append(" SUCCESS, ");
              ((StringBuilder)???).append(((List)localObject2).size());
              ((StringBuilder)???).append(" requests are loaded");
              FLogger.d("WUPStatClient", ((StringBuilder)???).toString());
              WUPStatClient.this.onLoadDataEnd();
            }
          }
          WUPStatClient.this.c = true;
          FLogger.d("WUPStatClient", "loadFailList: load stat file ends");
          FLogger.d("WUPStatClient", "loadFailList: load partial file begins");
          File localFile2 = WUPStatClient.a(true);
          localObject2 = WUPStatClient.a(WUPStatClient.this, localFile2);
          if ((localObject2 != null) && (!((List)localObject2).isEmpty()))
          {
            ??? = new StringBuilder();
            ((StringBuilder)???).append("loadFailList: load partial data from file ");
            ((StringBuilder)???).append(localFile2.getName());
            ((StringBuilder)???).append(" SUCCESS, ");
            ((StringBuilder)???).append(((List)localObject2).size());
            ((StringBuilder)???).append(" requests are loaded");
            FLogger.d("WUPStatClient", ((StringBuilder)???).toString());
            localObject2 = ((List)localObject2).iterator();
            while (((Iterator)localObject2).hasNext())
            {
              ??? = (WUPStatRequest)((Iterator)localObject2).next();
              WUPStatClient.this.sendRealTime((WUPStatRequest)???);
            }
          }
          FileUtilsF.deleteQuietly(localFile2);
          FLogger.d("WUPStatClient", "loadFailList: load partial file ends");
        }
      });
      return;
    }
  }
  
  protected void onLoadDataEnd()
  {
    FLogger.d("WUPStatClient", "onLoadDataEnd called");
    synchronized (this.jdField_a_of_type_ArrayOfByte)
    {
      a locala;
      if ((this.jdField_a_of_type_ComTencentCommonWupExtensionA != null) && (this.jdField_a_of_type_ComTencentCommonWupExtensionA.a() > 0))
      {
        FLogger.d("WUPStatClient", "onLoadDataEnd: mCurrentRequests not empty, begin getting requests");
        locala = this.jdField_a_of_type_ComTencentCommonWupExtensionA.a();
      }
      else
      {
        FLogger.d("WUPStatClient", "onLoadDataEnd: mPrevFailedRequests empty, ignore");
        locala = null;
      }
      if (locala != null)
      {
        FLogger.d("WUPStatClient", "onLoadDataEnd: send prev requests begins");
        a(locala);
      }
      this.jdField_c_of_type_Boolean = true;
      return;
    }
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Send CURRENT requests FAILED, serventName=");
    localStringBuilder.append(paramWUPRequestBase.getServerName());
    localStringBuilder.append(", funcName=");
    localStringBuilder.append(paramWUPRequestBase.getFuncName());
    FLogger.d("WUPStatClient", localStringBuilder.toString());
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    if ((paramWUPRequestBase instanceof WUPStatRequest))
    {
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("Send CURRENT request send SUCCESS, serventName=");
      paramWUPResponseBase.append(paramWUPRequestBase.getServerName());
      paramWUPResponseBase.append(", funcName=");
      paramWUPResponseBase.append(paramWUPRequestBase.getFuncName());
      FLogger.d("WUPStatClient", paramWUPResponseBase.toString());
      b((WUPStatRequest)paramWUPRequestBase);
    }
  }
  
  public void saveAllAndUpload()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("saveAllAndUpload: saveAllAndUpload called, current data size = ");
    Object localObject = this.jdField_a_of_type_ComTencentCommonWupExtensionA;
    if (localObject == null) {
      localObject = "empty";
    } else {
      localObject = Integer.valueOf(((a)localObject).a());
    }
    localStringBuilder.append(localObject);
    FLogger.d("WUPStatClient", localStringBuilder.toString());
    this.jdField_a_of_type_ComTencentCommonWupExtensionWUPStatClient$b.a(new Runnable()
    {
      public void run()
      {
        FLogger.d("WUPStatClient", "saveAllAndUpload: begin save current data in thread");
        for (;;)
        {
          synchronized (WUPStatClient.this.jdField_a_of_type_ArrayOfByte)
          {
            if (WUPStatClient.this.jdField_a_of_type_ComTencentCommonWupExtensionA != null)
            {
              a locala = WUPStatClient.this.jdField_a_of_type_ComTencentCommonWupExtensionA.a();
              boolean bool = WUPStatClient.this.jdField_a_of_type_Boolean;
              if (locala != null)
              {
                ??? = new StringBuilder();
                ((StringBuilder)???).append("saveAllAndUpload: current pending list not null, save! current size=");
                ((StringBuilder)???).append(locala.a());
                FLogger.d("WUPStatClient", ((StringBuilder)???).toString());
                if (bool) {
                  WUPStatClient.a(WUPStatClient.this, locala, true);
                }
                ??? = WUPStatClient.this;
                ((WUPStatClient)???).jdField_a_of_type_Boolean = false;
                WUPStatClient.a((WUPStatClient)???, locala);
              }
              return;
            }
          }
          Object localObject2 = null;
        }
      }
    });
  }
  
  public void saveCurrentFailList(boolean paramBoolean)
  {
    FLogger.d("WUPStatClient", "saveCurrentFailList: Begin to schedule saving current data to file");
    if (!this.jdField_c_of_type_Boolean)
    {
      FLogger.d("WUPStatClient", "saveCurrentFailList: Begin to schedule saving, but data load is not ready, ignore");
      return;
    }
    Runnable local2 = new Runnable()
    {
      public void run()
      {
        Object localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("saveCurrentFailList: Saving current data to file in thread, current data changed ?");
        ((StringBuilder)localObject1).append(WUPStatClient.this.jdField_a_of_type_Boolean);
        FLogger.d("WUPStatClient", ((StringBuilder)localObject1).toString());
        for (;;)
        {
          synchronized (WUPStatClient.this.jdField_a_of_type_ArrayOfByte)
          {
            if ((WUPStatClient.this.jdField_a_of_type_Boolean) && (WUPStatClient.this.jdField_a_of_type_ComTencentCommonWupExtensionA != null))
            {
              localObject1 = WUPStatClient.this.jdField_a_of_type_ComTencentCommonWupExtensionA.a();
              if (localObject1 != null)
              {
                WUPStatClient.a(WUPStatClient.this, (a)localObject1, true);
                WUPStatClient.this.jdField_a_of_type_Boolean = false;
              }
              FLogger.d("WUPStatClient", "saveCurrentFailList: Save complete ");
              return;
            }
          }
          Object localObject3 = null;
        }
      }
    };
    if (paramBoolean)
    {
      FLogger.d("WUPStatClient", "saveCurrentFailList: begin to save synchronized ");
      local2.run();
      return;
    }
    FLogger.d("WUPStatClient", "saveCurrentFailList: begin to save asynchronized ");
    this.jdField_a_of_type_ComTencentCommonWupExtensionWUPStatClient$b.a(local2);
  }
  
  public int sendNonRealTime(WUPStatRequest paramWUPStatRequest)
  {
    if (paramWUPStatRequest == null) {
      return -1;
    }
    int i = a();
    ??? = new StringBuilder();
    ((StringBuilder)???).append("sendPartialStatReq: sendWUPStatReq called, current reqID=");
    ((StringBuilder)???).append(i);
    FLogger.d("WUPStatClient", ((StringBuilder)???).toString());
    synchronized (this.jdField_b_of_type_ArrayOfByte)
    {
      if (this.jdField_b_of_type_ComTencentCommonWupExtensionA == null) {
        this.jdField_b_of_type_ComTencentCommonWupExtensionA = new a();
      }
      paramWUPStatRequest.setBindObject(Integer.valueOf(i));
      this.jdField_b_of_type_ComTencentCommonWupExtensionA.a(paramWUPStatRequest);
      paramWUPStatRequest = new StringBuilder();
      paramWUPStatRequest.append("sendPartialStatReq: add to mPartialRequests, size=");
      paramWUPStatRequest.append(this.jdField_b_of_type_ComTencentCommonWupExtensionA.a());
      FLogger.d("WUPStatClient", paramWUPStatRequest.toString());
      b();
      return i;
    }
  }
  
  public void sendRealTime(WUPStatRequest paramWUPStatRequest)
  {
    sendRealTime(paramWUPStatRequest, -1);
  }
  
  public void sendRealTime(WUPStatRequest paramWUPStatRequest, int paramInt)
  {
    FLogger.d("WUPStatClient", "sendWUPStatReq: sendWUPStatReq called");
    if (paramWUPStatRequest == null) {
      return;
    }
    FLogger.d("WUPStatClient", "sendWUPStatReq: single WUPRequest got");
    paramWUPStatRequest.setRequestCallBack(this);
    paramWUPStatRequest.setNeedEncrypt(true);
    paramWUPStatRequest.setPriority(Task.Priority.LOW);
    paramWUPStatRequest.setRequestPolicy(RequestPolicy.FAST_MODE_POLICY);
    if (WUPTaskProxy.send(paramWUPStatRequest)) {
      a(paramWUPStatRequest);
    }
    a(paramInt);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("sendWUPStatReq: sendWUPStatReq ends, serventName=");
    localStringBuilder.append(paramWUPStatRequest.getServerName());
    localStringBuilder.append(", funcName=");
    localStringBuilder.append(paramWUPStatRequest.getFuncName());
    FLogger.d("WUPStatClient", localStringBuilder.toString());
  }
  
  public void shutDown()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("shutDown: Shut down called, current data size = ");
    Object localObject = this.jdField_a_of_type_ComTencentCommonWupExtensionA;
    if (localObject == null) {
      localObject = "empty";
    } else {
      localObject = Integer.valueOf(((a)localObject).a());
    }
    localStringBuilder.append(localObject);
    FLogger.d("WUPStatClient", localStringBuilder.toString());
    this.jdField_a_of_type_ComTencentCommonWupExtensionWUPStatClient$b.a(new Runnable()
    {
      public void run()
      {
        ??? = new StringBuilder();
        ((StringBuilder)???).append("shutDown: Saving current data to file in thread, current data changed ?");
        ((StringBuilder)???).append(WUPStatClient.this.jdField_a_of_type_Boolean);
        FLogger.d("WUPStatClient", ((StringBuilder)???).toString());
        for (;;)
        {
          synchronized (WUPStatClient.this.jdField_a_of_type_ArrayOfByte)
          {
            if ((WUPStatClient.this.jdField_a_of_type_Boolean) && (WUPStatClient.this.jdField_a_of_type_ComTencentCommonWupExtensionA != null))
            {
              ??? = WUPStatClient.this.jdField_a_of_type_ComTencentCommonWupExtensionA.a();
              WUPStatClient.this.jdField_a_of_type_ComTencentCommonWupExtensionA = null;
              if (??? != null)
              {
                WUPStatClient.a(WUPStatClient.this, (a)???, true);
                WUPStatClient.this.jdField_a_of_type_Boolean = false;
              }
              ??? = WUPStatClient.this;
              ((WUPStatClient)???).jdField_c_of_type_Boolean = false;
              synchronized (((WUPStatClient)???).jdField_c_of_type_ArrayOfByte)
              {
                WUPStatClient.this.b = false;
                FLogger.d("WUPStatClient", "shutDown: Shutdown complete!!!");
                return;
              }
            }
          }
          Object localObject3 = null;
        }
      }
    });
  }
  
  private static class a
  {
    private static final WUPStatClient a = new WUPStatClient(null);
  }
  
  private static class b
  {
    private Handler jdField_a_of_type_AndroidOsHandler = null;
    private Looper jdField_a_of_type_AndroidOsLooper = null;
    
    public b()
    {
      HandlerThread localHandlerThread = new HandlerThread("wup-stat-client", 11);
      localHandlerThread.start();
      this.jdField_a_of_type_AndroidOsLooper = localHandlerThread.getLooper();
      this.jdField_a_of_type_AndroidOsHandler = new Handler(this.jdField_a_of_type_AndroidOsLooper);
    }
    
    public final boolean a(Runnable paramRunnable)
    {
      Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
      if (localHandler != null) {
        return localHandler.post(paramRunnable);
      }
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\extension\WUPStatClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */