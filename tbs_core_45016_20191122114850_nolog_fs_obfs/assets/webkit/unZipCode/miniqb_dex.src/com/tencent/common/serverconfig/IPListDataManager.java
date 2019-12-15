package com.tencent.common.serverconfig;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.mtt.ContextHolder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class IPListDataManager
{
  private static IPListDataManager jdField_a_of_type_ComTencentCommonServerconfigIPListDataManager = null;
  private static Object jdField_b_of_type_JavaLangObject;
  private static HashMap<String, Boolean> c = new HashMap();
  private Context jdField_a_of_type_AndroidContentContext;
  private Handler jdField_a_of_type_AndroidOsHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime());
  Object jdField_a_of_type_JavaLangObject = new Object();
  private String jdField_a_of_type_JavaLangString;
  HashMap<String, ArrayList<String>> jdField_a_of_type_JavaUtilHashMap = null;
  private boolean jdField_a_of_type_Boolean = false;
  HashMap<String, ArrayList<String>> jdField_b_of_type_JavaUtilHashMap = null;
  
  static
  {
    jdField_b_of_type_JavaLangObject = new Object();
  }
  
  private IPListDataManager(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
    this.jdField_a_of_type_JavaLangString = ThreadUtils.getCurrentProcessNameIngoreColon(this.jdField_a_of_type_AndroidContentContext);
  }
  
  /* Error */
  private ArrayList<HashMap<String, ArrayList<String>>> a()
  {
    // Byte code:
    //   0: ldc 82
    //   2: ldc 84
    //   4: invokestatic 90	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   7: new 92	java/util/ArrayList
    //   10: dup
    //   11: invokespecial 93	java/util/ArrayList:<init>	()V
    //   14: astore 7
    //   16: new 24	java/util/HashMap
    //   19: dup
    //   20: invokespecial 27	java/util/HashMap:<init>	()V
    //   23: astore 8
    //   25: new 24	java/util/HashMap
    //   28: dup
    //   29: invokespecial 27	java/util/HashMap:<init>	()V
    //   32: astore 9
    //   34: aload_0
    //   35: getfield 44	com/tencent/common/serverconfig/IPListDataManager:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   38: astore 6
    //   40: aload 6
    //   42: monitorenter
    //   43: aload_0
    //   44: getfield 65	com/tencent/common/serverconfig/IPListDataManager:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   47: invokestatic 99	com/tencent/common/utils/FileUtilsF:getDataDir	(Landroid/content/Context;)Ljava/io/File;
    //   50: astore_3
    //   51: new 101	java/lang/StringBuilder
    //   54: dup
    //   55: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   58: astore 4
    //   60: aload 4
    //   62: aload_0
    //   63: getfield 73	com/tencent/common/serverconfig/IPListDataManager:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   66: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: pop
    //   70: aload 4
    //   72: ldc 108
    //   74: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: pop
    //   78: new 110	java/io/File
    //   81: dup
    //   82: aload_3
    //   83: aload 4
    //   85: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   88: invokespecial 117	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   91: astore_3
    //   92: aload_3
    //   93: invokevirtual 121	java/io/File:exists	()Z
    //   96: ifeq +20 -> 116
    //   99: invokestatic 125	com/tencent/common/threadpool/BrowserExecutorSupplier:forIoTasks	()Ljava/util/concurrent/Executor;
    //   102: new 6	com/tencent/common/serverconfig/IPListDataManager$1
    //   105: dup
    //   106: aload_0
    //   107: aload_3
    //   108: invokespecial 128	com/tencent/common/serverconfig/IPListDataManager$1:<init>	(Lcom/tencent/common/serverconfig/IPListDataManager;Ljava/io/File;)V
    //   111: invokeinterface 134 2 0
    //   116: aload_0
    //   117: getfield 42	com/tencent/common/serverconfig/IPListDataManager:jdField_a_of_type_Boolean	Z
    //   120: iconst_1
    //   121: if_icmpne +29 -> 150
    //   124: aload 7
    //   126: aload_0
    //   127: getfield 38	com/tencent/common/serverconfig/IPListDataManager:jdField_a_of_type_JavaUtilHashMap	Ljava/util/HashMap;
    //   130: invokevirtual 138	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   133: pop
    //   134: aload 7
    //   136: aload_0
    //   137: getfield 40	com/tencent/common/serverconfig/IPListDataManager:jdField_b_of_type_JavaUtilHashMap	Ljava/util/HashMap;
    //   140: invokevirtual 138	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   143: pop
    //   144: aload 6
    //   146: monitorexit
    //   147: aload 7
    //   149: areturn
    //   150: aload_0
    //   151: iconst_1
    //   152: putfield 42	com/tencent/common/serverconfig/IPListDataManager:jdField_a_of_type_Boolean	Z
    //   155: aload_0
    //   156: getfield 65	com/tencent/common/serverconfig/IPListDataManager:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   159: invokestatic 99	com/tencent/common/utils/FileUtilsF:getDataDir	(Landroid/content/Context;)Ljava/io/File;
    //   162: astore_3
    //   163: new 101	java/lang/StringBuilder
    //   166: dup
    //   167: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   170: astore 4
    //   172: aload 4
    //   174: aload_0
    //   175: getfield 73	com/tencent/common/serverconfig/IPListDataManager:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   178: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   181: pop
    //   182: aload 4
    //   184: ldc -116
    //   186: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   189: pop
    //   190: new 110	java/io/File
    //   193: dup
    //   194: aload_3
    //   195: aload 4
    //   197: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   200: invokespecial 117	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   203: astore 4
    //   205: aload 4
    //   207: invokevirtual 121	java/io/File:exists	()Z
    //   210: ifne +25 -> 235
    //   213: aload 7
    //   215: aload 8
    //   217: invokevirtual 138	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   220: pop
    //   221: aload 7
    //   223: aload 9
    //   225: invokevirtual 138	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   228: pop
    //   229: aload 6
    //   231: monitorexit
    //   232: aload 7
    //   234: areturn
    //   235: aconst_null
    //   236: astore 5
    //   238: aconst_null
    //   239: astore_3
    //   240: new 142	java/io/DataInputStream
    //   243: dup
    //   244: new 144	java/io/BufferedInputStream
    //   247: dup
    //   248: aload 4
    //   250: invokestatic 148	com/tencent/common/utils/FileUtilsF:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   253: invokespecial 151	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   256: invokespecial 152	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   259: astore 4
    //   261: aload 4
    //   263: invokevirtual 155	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   266: astore_3
    //   267: aload_3
    //   268: invokestatic 161	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   271: ifne +225 -> 496
    //   274: aload 4
    //   276: invokevirtual 165	java/io/DataInputStream:readInt	()I
    //   279: istore_2
    //   280: new 92	java/util/ArrayList
    //   283: dup
    //   284: invokespecial 93	java/util/ArrayList:<init>	()V
    //   287: astore 5
    //   289: new 92	java/util/ArrayList
    //   292: dup
    //   293: invokespecial 93	java/util/ArrayList:<init>	()V
    //   296: astore 10
    //   298: iconst_0
    //   299: istore_1
    //   300: iload_1
    //   301: iload_2
    //   302: if_icmpge +68 -> 370
    //   305: aload 4
    //   307: invokevirtual 155	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   310: astore 11
    //   312: aload 11
    //   314: invokevirtual 170	java/lang/String:length	()I
    //   317: ifle +298 -> 615
    //   320: aload 11
    //   322: invokestatic 176	com/tencent/common/utils/UrlUtils:isIpv6Url	(Ljava/lang/String;)Z
    //   325: ifeq +24 -> 349
    //   328: aload 10
    //   330: aload 11
    //   332: invokevirtual 179	java/util/ArrayList:contains	(Ljava/lang/Object;)Z
    //   335: ifne +280 -> 615
    //   338: aload 10
    //   340: aload 11
    //   342: invokevirtual 138	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   345: pop
    //   346: goto +269 -> 615
    //   349: aload 5
    //   351: aload 11
    //   353: invokevirtual 179	java/util/ArrayList:contains	(Ljava/lang/Object;)Z
    //   356: ifne +259 -> 615
    //   359: aload 5
    //   361: aload 11
    //   363: invokevirtual 138	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   366: pop
    //   367: goto +248 -> 615
    //   370: aload 8
    //   372: aload_3
    //   373: aload 5
    //   375: invokevirtual 183	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   378: pop
    //   379: aload 9
    //   381: aload_3
    //   382: aload 10
    //   384: invokevirtual 183	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   387: pop
    //   388: aload_3
    //   389: iconst_1
    //   390: invokestatic 187	com/tencent/common/serverconfig/IPListDataManager:setWupServerEnable	(Ljava/lang/String;Z)V
    //   393: new 101	java/lang/StringBuilder
    //   396: dup
    //   397: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   400: astore 10
    //   402: aload 10
    //   404: ldc -67
    //   406: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   409: pop
    //   410: aload 10
    //   412: aload_3
    //   413: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   416: pop
    //   417: aload 10
    //   419: ldc -65
    //   421: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   424: pop
    //   425: aload 10
    //   427: aload 5
    //   429: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   432: pop
    //   433: ldc 82
    //   435: aload 10
    //   437: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   440: invokestatic 90	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   443: new 101	java/lang/StringBuilder
    //   446: dup
    //   447: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   450: astore 10
    //   452: aload 10
    //   454: ldc -60
    //   456: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   459: pop
    //   460: aload 10
    //   462: aload_3
    //   463: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   466: pop
    //   467: aload 10
    //   469: ldc -58
    //   471: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   474: pop
    //   475: aload 10
    //   477: aload 5
    //   479: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   482: pop
    //   483: ldc -56
    //   485: aload 10
    //   487: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   490: invokestatic 90	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   493: goto -232 -> 261
    //   496: aload 4
    //   498: invokevirtual 203	java/io/DataInputStream:close	()V
    //   501: goto +64 -> 565
    //   504: astore_3
    //   505: aload_3
    //   506: invokevirtual 206	java/io/IOException:printStackTrace	()V
    //   509: goto +56 -> 565
    //   512: astore_3
    //   513: goto +74 -> 587
    //   516: astore 5
    //   518: goto +22 -> 540
    //   521: astore 5
    //   523: aload_3
    //   524: astore 4
    //   526: aload 5
    //   528: astore_3
    //   529: goto +58 -> 587
    //   532: astore_3
    //   533: aload 5
    //   535: astore 4
    //   537: aload_3
    //   538: astore 5
    //   540: aload 4
    //   542: astore_3
    //   543: aload 5
    //   545: invokevirtual 207	java/lang/Throwable:printStackTrace	()V
    //   548: aload 4
    //   550: ifnull +15 -> 565
    //   553: aload 4
    //   555: invokevirtual 203	java/io/DataInputStream:close	()V
    //   558: goto +7 -> 565
    //   561: astore_3
    //   562: goto -57 -> 505
    //   565: aload 7
    //   567: aload 8
    //   569: invokevirtual 138	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   572: pop
    //   573: aload 7
    //   575: aload 9
    //   577: invokevirtual 138	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   580: pop
    //   581: aload 6
    //   583: monitorexit
    //   584: aload 7
    //   586: areturn
    //   587: aload 4
    //   589: ifnull +18 -> 607
    //   592: aload 4
    //   594: invokevirtual 203	java/io/DataInputStream:close	()V
    //   597: goto +10 -> 607
    //   600: astore 4
    //   602: aload 4
    //   604: invokevirtual 206	java/io/IOException:printStackTrace	()V
    //   607: aload_3
    //   608: athrow
    //   609: astore_3
    //   610: aload 6
    //   612: monitorexit
    //   613: aload_3
    //   614: athrow
    //   615: iload_1
    //   616: iconst_1
    //   617: iadd
    //   618: istore_1
    //   619: goto -319 -> 300
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	622	0	this	IPListDataManager
    //   299	320	1	i	int
    //   279	24	2	j	int
    //   50	413	3	localObject1	Object
    //   504	2	3	localIOException1	java.io.IOException
    //   512	12	3	localObject2	Object
    //   528	1	3	localObject3	Object
    //   532	6	3	localThrowable1	Throwable
    //   542	1	3	localObject4	Object
    //   561	47	3	localIOException2	java.io.IOException
    //   609	5	3	localObject5	Object
    //   58	535	4	localObject6	Object
    //   600	3	4	localIOException3	java.io.IOException
    //   236	242	5	localArrayList1	ArrayList
    //   516	1	5	localThrowable2	Throwable
    //   521	13	5	localObject7	Object
    //   538	6	5	localThrowable3	Throwable
    //   38	573	6	localObject8	Object
    //   14	571	7	localArrayList2	ArrayList
    //   23	545	8	localHashMap1	HashMap
    //   32	544	9	localHashMap2	HashMap
    //   296	190	10	localObject9	Object
    //   310	52	11	str	String
    // Exception table:
    //   from	to	target	type
    //   496	501	504	java/io/IOException
    //   261	298	512	finally
    //   305	346	512	finally
    //   349	367	512	finally
    //   370	493	512	finally
    //   261	298	516	java/lang/Throwable
    //   305	346	516	java/lang/Throwable
    //   349	367	516	java/lang/Throwable
    //   370	493	516	java/lang/Throwable
    //   240	261	521	finally
    //   543	548	521	finally
    //   240	261	532	java/lang/Throwable
    //   553	558	561	java/io/IOException
    //   592	597	600	java/io/IOException
    //   43	116	609	finally
    //   116	147	609	finally
    //   150	232	609	finally
    //   496	501	609	finally
    //   505	509	609	finally
    //   553	558	609	finally
    //   565	584	609	finally
    //   592	597	609	finally
    //   602	607	609	finally
    //   607	609	609	finally
    //   610	613	609	finally
  }
  
  private void a(boolean paramBoolean)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (((this.jdField_a_of_type_JavaUtilHashMap != null) && ((paramBoolean) || (this.jdField_a_of_type_JavaUtilHashMap.size() > 0))) || ((this.jdField_b_of_type_JavaUtilHashMap != null) && ((paramBoolean) || (this.jdField_b_of_type_JavaUtilHashMap.size() > 0))))
      {
        this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
        {
          /* Error */
          public void run()
          {
            // Byte code:
            //   0: new 27	java/util/HashMap
            //   3: dup
            //   4: invokespecial 28	java/util/HashMap:<init>	()V
            //   7: astore 5
            //   9: aload_0
            //   10: getfield 16	com/tencent/common/serverconfig/IPListDataManager$2:a	Lcom/tencent/common/serverconfig/IPListDataManager;
            //   13: getfield 31	com/tencent/common/serverconfig/IPListDataManager:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
            //   16: astore_2
            //   17: aload_2
            //   18: monitorenter
            //   19: aload_0
            //   20: getfield 16	com/tencent/common/serverconfig/IPListDataManager$2:a	Lcom/tencent/common/serverconfig/IPListDataManager;
            //   23: getfield 34	com/tencent/common/serverconfig/IPListDataManager:jdField_a_of_type_JavaUtilHashMap	Ljava/util/HashMap;
            //   26: invokevirtual 38	java/util/HashMap:entrySet	()Ljava/util/Set;
            //   29: invokeinterface 44 1 0
            //   34: astore_3
            //   35: aload_3
            //   36: invokeinterface 50 1 0
            //   41: ifeq +66 -> 107
            //   44: aload_3
            //   45: invokeinterface 54 1 0
            //   50: checkcast 56	java/util/Map$Entry
            //   53: astore 6
            //   55: aload 6
            //   57: invokeinterface 59 1 0
            //   62: checkcast 61	java/lang/String
            //   65: astore 4
            //   67: aload 6
            //   69: invokeinterface 64 1 0
            //   74: checkcast 66	java/util/ArrayList
            //   77: astore 6
            //   79: aload 6
            //   81: invokevirtual 70	java/util/ArrayList:size	()I
            //   84: ifle -49 -> 35
            //   87: aload 5
            //   89: aload 4
            //   91: new 66	java/util/ArrayList
            //   94: dup
            //   95: aload 6
            //   97: invokespecial 73	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
            //   100: invokevirtual 77	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
            //   103: pop
            //   104: goto -69 -> 35
            //   107: aload_0
            //   108: getfield 16	com/tencent/common/serverconfig/IPListDataManager$2:a	Lcom/tencent/common/serverconfig/IPListDataManager;
            //   111: getfield 80	com/tencent/common/serverconfig/IPListDataManager:b	Ljava/util/HashMap;
            //   114: invokevirtual 83	java/util/HashMap:isEmpty	()Z
            //   117: ifne +161 -> 278
            //   120: aload_0
            //   121: getfield 16	com/tencent/common/serverconfig/IPListDataManager$2:a	Lcom/tencent/common/serverconfig/IPListDataManager;
            //   124: getfield 80	com/tencent/common/serverconfig/IPListDataManager:b	Ljava/util/HashMap;
            //   127: invokevirtual 38	java/util/HashMap:entrySet	()Ljava/util/Set;
            //   130: invokeinterface 44 1 0
            //   135: astore_3
            //   136: aload_3
            //   137: invokeinterface 50 1 0
            //   142: ifeq +136 -> 278
            //   145: aload_3
            //   146: invokeinterface 54 1 0
            //   151: checkcast 56	java/util/Map$Entry
            //   154: astore 6
            //   156: aload 6
            //   158: invokeinterface 59 1 0
            //   163: checkcast 61	java/lang/String
            //   166: astore 4
            //   168: aload 6
            //   170: invokeinterface 64 1 0
            //   175: checkcast 66	java/util/ArrayList
            //   178: astore 6
            //   180: aload 6
            //   182: invokevirtual 70	java/util/ArrayList:size	()I
            //   185: ifle -49 -> 136
            //   188: aload 5
            //   190: aload 4
            //   192: invokevirtual 87	java/util/HashMap:containsKey	(Ljava/lang/Object;)Z
            //   195: ifeq +70 -> 265
            //   198: aload 5
            //   200: aload 4
            //   202: invokevirtual 91	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
            //   205: checkcast 66	java/util/ArrayList
            //   208: astore 4
            //   210: aload 4
            //   212: ifnull -76 -> 136
            //   215: aload 6
            //   217: invokevirtual 92	java/util/ArrayList:iterator	()Ljava/util/Iterator;
            //   220: astore 6
            //   222: aload 6
            //   224: invokeinterface 50 1 0
            //   229: ifeq -93 -> 136
            //   232: aload 6
            //   234: invokeinterface 54 1 0
            //   239: checkcast 61	java/lang/String
            //   242: astore 7
            //   244: aload 4
            //   246: aload 7
            //   248: invokevirtual 95	java/util/ArrayList:contains	(Ljava/lang/Object;)Z
            //   251: ifne -29 -> 222
            //   254: aload 4
            //   256: aload 7
            //   258: invokevirtual 98	java/util/ArrayList:add	(Ljava/lang/Object;)Z
            //   261: pop
            //   262: goto -40 -> 222
            //   265: aload 5
            //   267: aload 4
            //   269: aload 6
            //   271: invokevirtual 77	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
            //   274: pop
            //   275: goto -139 -> 136
            //   278: aload_2
            //   279: monitorexit
            //   280: aload 5
            //   282: invokevirtual 99	java/util/HashMap:size	()I
            //   285: ifgt +4 -> 289
            //   288: return
            //   289: aload_0
            //   290: getfield 16	com/tencent/common/serverconfig/IPListDataManager$2:a	Lcom/tencent/common/serverconfig/IPListDataManager;
            //   293: invokestatic 102	com/tencent/common/serverconfig/IPListDataManager:a	(Lcom/tencent/common/serverconfig/IPListDataManager;)Landroid/content/Context;
            //   296: invokestatic 108	com/tencent/common/utils/FileUtilsF:getDataDir	(Landroid/content/Context;)Ljava/io/File;
            //   299: astore_2
            //   300: new 110	java/lang/StringBuilder
            //   303: dup
            //   304: invokespecial 111	java/lang/StringBuilder:<init>	()V
            //   307: astore_3
            //   308: aload_3
            //   309: aload_0
            //   310: getfield 16	com/tencent/common/serverconfig/IPListDataManager$2:a	Lcom/tencent/common/serverconfig/IPListDataManager;
            //   313: invokestatic 114	com/tencent/common/serverconfig/IPListDataManager:a	(Lcom/tencent/common/serverconfig/IPListDataManager;)Ljava/lang/String;
            //   316: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   319: pop
            //   320: aload_3
            //   321: ldc 120
            //   323: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   326: pop
            //   327: new 122	java/io/File
            //   330: dup
            //   331: aload_2
            //   332: aload_3
            //   333: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   336: invokespecial 129	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
            //   339: astore 6
            //   341: aconst_null
            //   342: astore 4
            //   344: aconst_null
            //   345: astore_3
            //   346: aload_3
            //   347: astore_2
            //   348: aload 6
            //   350: invokevirtual 132	java/io/File:exists	()Z
            //   353: ifne +11 -> 364
            //   356: aload_3
            //   357: astore_2
            //   358: aload 6
            //   360: invokevirtual 135	java/io/File:createNewFile	()Z
            //   363: pop
            //   364: aload_3
            //   365: astore_2
            //   366: new 137	java/io/DataOutputStream
            //   369: dup
            //   370: new 139	java/io/BufferedOutputStream
            //   373: dup
            //   374: aload 6
            //   376: invokestatic 143	com/tencent/common/utils/FileUtilsF:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
            //   379: invokespecial 146	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
            //   382: invokespecial 147	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
            //   385: astore_3
            //   386: aload 5
            //   388: invokevirtual 38	java/util/HashMap:entrySet	()Ljava/util/Set;
            //   391: invokeinterface 44 1 0
            //   396: astore_2
            //   397: aload_2
            //   398: invokeinterface 50 1 0
            //   403: ifeq +200 -> 603
            //   406: aload_2
            //   407: invokeinterface 54 1 0
            //   412: checkcast 56	java/util/Map$Entry
            //   415: astore 5
            //   417: aload 5
            //   419: invokeinterface 59 1 0
            //   424: checkcast 61	java/lang/String
            //   427: astore 4
            //   429: aload 5
            //   431: invokeinterface 64 1 0
            //   436: checkcast 66	java/util/ArrayList
            //   439: astore 5
            //   441: aload 4
            //   443: invokestatic 152	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
            //   446: ifeq +6 -> 452
            //   449: goto -52 -> 397
            //   452: aload_3
            //   453: aload 4
            //   455: invokevirtual 156	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
            //   458: aload_3
            //   459: aload 5
            //   461: invokevirtual 70	java/util/ArrayList:size	()I
            //   464: invokevirtual 160	java/io/DataOutputStream:writeInt	(I)V
            //   467: iconst_0
            //   468: istore_1
            //   469: iload_1
            //   470: aload 5
            //   472: invokevirtual 70	java/util/ArrayList:size	()I
            //   475: if_icmpge +23 -> 498
            //   478: aload_3
            //   479: aload 5
            //   481: iload_1
            //   482: invokevirtual 163	java/util/ArrayList:get	(I)Ljava/lang/Object;
            //   485: checkcast 61	java/lang/String
            //   488: invokevirtual 156	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
            //   491: iload_1
            //   492: iconst_1
            //   493: iadd
            //   494: istore_1
            //   495: goto -26 -> 469
            //   498: new 110	java/lang/StringBuilder
            //   501: dup
            //   502: invokespecial 111	java/lang/StringBuilder:<init>	()V
            //   505: astore 6
            //   507: aload 6
            //   509: ldc -91
            //   511: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   514: pop
            //   515: aload 6
            //   517: aload 4
            //   519: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   522: pop
            //   523: aload 6
            //   525: ldc -89
            //   527: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   530: pop
            //   531: aload 6
            //   533: aload 5
            //   535: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
            //   538: pop
            //   539: ldc -84
            //   541: aload 6
            //   543: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   546: invokestatic 178	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
            //   549: new 110	java/lang/StringBuilder
            //   552: dup
            //   553: invokespecial 111	java/lang/StringBuilder:<init>	()V
            //   556: astore 6
            //   558: aload 6
            //   560: ldc -76
            //   562: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   565: pop
            //   566: aload 6
            //   568: aload 4
            //   570: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   573: pop
            //   574: aload 6
            //   576: ldc -74
            //   578: invokevirtual 118	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   581: pop
            //   582: aload 6
            //   584: aload 5
            //   586: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
            //   589: pop
            //   590: ldc -72
            //   592: aload 6
            //   594: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   597: invokestatic 178	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
            //   600: goto -203 -> 397
            //   603: aload_3
            //   604: invokevirtual 187	java/io/DataOutputStream:close	()V
            //   607: return
            //   608: astore_2
            //   609: goto +47 -> 656
            //   612: astore 4
            //   614: goto +20 -> 634
            //   617: astore 4
            //   619: aload_2
            //   620: astore_3
            //   621: aload 4
            //   623: astore_2
            //   624: goto +32 -> 656
            //   627: astore_2
            //   628: aload 4
            //   630: astore_3
            //   631: aload_2
            //   632: astore 4
            //   634: aload_3
            //   635: astore_2
            //   636: aload 4
            //   638: invokevirtual 190	java/lang/Throwable:printStackTrace	()V
            //   641: aload_3
            //   642: ifnull +13 -> 655
            //   645: aload_3
            //   646: invokevirtual 187	java/io/DataOutputStream:close	()V
            //   649: return
            //   650: astore_2
            //   651: aload_2
            //   652: invokevirtual 191	java/io/IOException:printStackTrace	()V
            //   655: return
            //   656: aload_3
            //   657: ifnull +15 -> 672
            //   660: aload_3
            //   661: invokevirtual 187	java/io/DataOutputStream:close	()V
            //   664: goto +8 -> 672
            //   667: astore_3
            //   668: aload_3
            //   669: invokevirtual 191	java/io/IOException:printStackTrace	()V
            //   672: aload_2
            //   673: athrow
            //   674: astore_3
            //   675: aload_2
            //   676: monitorexit
            //   677: aload_3
            //   678: athrow
            // Local variable table:
            //   start	length	slot	name	signature
            //   0	679	0	this	2
            //   468	27	1	i	int
            //   16	391	2	localObject1	Object
            //   608	12	2	localObject2	Object
            //   623	1	2	localObject3	Object
            //   627	5	2	localThrowable1	Throwable
            //   635	1	2	localObject4	Object
            //   650	26	2	localIOException1	java.io.IOException
            //   34	627	3	localObject5	Object
            //   667	2	3	localIOException2	java.io.IOException
            //   674	4	3	localObject6	Object
            //   65	504	4	localObject7	Object
            //   612	1	4	localThrowable2	Throwable
            //   617	12	4	localObject8	Object
            //   632	5	4	localThrowable3	Throwable
            //   7	578	5	localObject9	Object
            //   53	540	6	localObject10	Object
            //   242	15	7	str	String
            // Exception table:
            //   from	to	target	type
            //   386	397	608	finally
            //   397	449	608	finally
            //   452	467	608	finally
            //   469	491	608	finally
            //   498	600	608	finally
            //   386	397	612	java/lang/Throwable
            //   397	449	612	java/lang/Throwable
            //   452	467	612	java/lang/Throwable
            //   469	491	612	java/lang/Throwable
            //   498	600	612	java/lang/Throwable
            //   348	356	617	finally
            //   358	364	617	finally
            //   366	386	617	finally
            //   636	641	617	finally
            //   348	356	627	java/lang/Throwable
            //   358	364	627	java/lang/Throwable
            //   366	386	627	java/lang/Throwable
            //   603	607	650	java/io/IOException
            //   645	649	650	java/io/IOException
            //   660	664	667	java/io/IOException
            //   19	35	674	finally
            //   35	104	674	finally
            //   107	136	674	finally
            //   136	210	674	finally
            //   215	222	674	finally
            //   222	262	674	finally
            //   265	275	674	finally
            //   278	280	674	finally
            //   675	677	674	finally
          }
        });
        return;
      }
      return;
    }
  }
  
  public static IPListDataManager getInstance()
  {
    return getInstance(ContextHolder.getAppContext());
  }
  
  @Deprecated
  public static IPListDataManager getInstance(Context paramContext)
  {
    if (jdField_a_of_type_ComTencentCommonServerconfigIPListDataManager == null) {
      jdField_a_of_type_ComTencentCommonServerconfigIPListDataManager = new IPListDataManager(paramContext);
    }
    return jdField_a_of_type_ComTencentCommonServerconfigIPListDataManager;
  }
  
  public static boolean isWupserverValidate(String paramString)
  {
    try
    {
      synchronized (jdField_b_of_type_JavaLangObject)
      {
        paramString = (Boolean)c.get(paramString);
        return (paramString != null) && (paramString != Boolean.FALSE);
      }
      return false;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public static void setWupServerEnable(String paramString, boolean paramBoolean)
  {
    try
    {
      synchronized (jdField_b_of_type_JavaLangObject)
      {
        c.put(paramString, Boolean.valueOf(paramBoolean));
        return;
      }
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void clearServerList(String paramString, boolean paramBoolean)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      Object localObject2 = this.jdField_a_of_type_JavaUtilHashMap;
      boolean bool = false;
      if (localObject2 == null)
      {
        localObject2 = a();
        this.jdField_a_of_type_JavaUtilHashMap = ((HashMap)((ArrayList)localObject2).get(0));
        this.jdField_b_of_type_JavaUtilHashMap = ((HashMap)((ArrayList)localObject2).get(1));
      }
      localObject2 = new HashSet();
      ((Set)localObject2).addAll(this.jdField_a_of_type_JavaUtilHashMap.keySet());
      ((Set)localObject2).addAll(this.jdField_b_of_type_JavaUtilHashMap.keySet());
      if (((Set)localObject2).isEmpty()) {
        return;
      }
      localObject2 = ((Set)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        String str = (String)((Iterator)localObject2).next();
        if ((!TextUtils.isEmpty(str)) && (str.endsWith(paramString)))
        {
          if (this.jdField_a_of_type_JavaUtilHashMap.containsKey(str)) {
            this.jdField_a_of_type_JavaUtilHashMap.remove(str);
          } else if (this.jdField_b_of_type_JavaUtilHashMap.containsKey(str)) {
            this.jdField_b_of_type_JavaUtilHashMap.remove(str);
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("clearServerList: type=");
          localStringBuilder.append(str);
          FLogger.d("IPListDataManager", localStringBuilder.toString());
          bool = true;
        }
      }
      paramString = new StringBuilder();
      paramString.append("clearServerList: found=");
      paramString.append(bool);
      FLogger.d("IPListDataManager", paramString.toString());
      if ((bool) && (paramBoolean)) {
        a(true);
      }
      return;
    }
  }
  
  public ArrayList<String> getServerList(String paramString)
  {
    return getServerList(paramString, false);
  }
  
  public ArrayList<String> getServerList(String paramString, boolean paramBoolean)
  {
    ??? = new StringBuilder();
    ((StringBuilder)???).append("getServerList: type=");
    ((StringBuilder)???).append(paramString);
    FLogger.d("IPListDataManager", ((StringBuilder)???).toString());
    if (TextUtils.isEmpty(paramString)) {
      return new ArrayList();
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_JavaUtilHashMap == null)
      {
        localObject2 = a();
        this.jdField_a_of_type_JavaUtilHashMap = ((HashMap)((ArrayList)localObject2).get(0));
        this.jdField_b_of_type_JavaUtilHashMap = ((HashMap)((ArrayList)localObject2).get(1));
      }
      if (this.jdField_a_of_type_JavaUtilHashMap == null)
      {
        paramString = new ArrayList();
        return paramString;
      }
      Object localObject2 = (ArrayList)this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
      Object localObject3 = (ArrayList)this.jdField_b_of_type_JavaUtilHashMap.get(paramString);
      if (!paramBoolean)
      {
        if (localObject2 == null)
        {
          paramString = new ArrayList();
          return paramString;
        }
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("getServerList: type=");
        ((StringBuilder)localObject3).append(paramString);
        ((StringBuilder)localObject3).append(", servers=");
        ((StringBuilder)localObject3).append(localObject2);
        FLogger.d("IPListDataManager", ((StringBuilder)localObject3).toString());
        paramString = new ArrayList((Collection)localObject2);
        return paramString;
      }
      if (localObject3 == null)
      {
        paramString = new ArrayList();
        return paramString;
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("getServerList: type=");
      ((StringBuilder)localObject2).append(paramString);
      ((StringBuilder)localObject2).append(", servers=");
      ((StringBuilder)localObject2).append(localObject3);
      FLogger.d("IPListDataManager", ((StringBuilder)localObject2).toString());
      paramString = new ArrayList((Collection)localObject3);
      return paramString;
    }
  }
  
  public void saveServerList()
  {
    a(false);
  }
  
  public void updateServerList(String paramString, ArrayList<String> paramArrayList)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_JavaUtilHashMap == null)
      {
        localObject2 = a();
        this.jdField_a_of_type_JavaUtilHashMap = ((HashMap)((ArrayList)localObject2).get(0));
        this.jdField_b_of_type_JavaUtilHashMap = ((HashMap)((ArrayList)localObject2).get(1));
      }
      Object localObject2 = (ArrayList)this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
      ArrayList localArrayList1 = (ArrayList)this.jdField_b_of_type_JavaUtilHashMap.get(paramString);
      ArrayList localArrayList2 = new ArrayList();
      ArrayList localArrayList3 = new ArrayList();
      Object localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("add ip server type:");
      ((StringBuilder)localObject3).append(paramString);
      ((StringBuilder)localObject3).toString();
      localObject3 = paramArrayList.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        String str = (String)((Iterator)localObject3).next();
        StringBuilder localStringBuilder;
        if (UrlUtils.isIpUrl(str))
        {
          localArrayList2.add(str);
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("add ipv4 server:");
          localStringBuilder.append(str);
          localStringBuilder.toString();
        }
        else
        {
          localArrayList3.add(str);
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("add ipv6 server:");
          localStringBuilder.append(str);
          localStringBuilder.toString();
        }
      }
      if (localObject2 == null)
      {
        this.jdField_a_of_type_JavaUtilHashMap.put(paramString, new ArrayList(localArrayList2));
      }
      else
      {
        ((ArrayList)localObject2).clear();
        ((ArrayList)localObject2).addAll(localArrayList2);
      }
      if (localArrayList3.size() > 0) {
        if (localArrayList1 == null)
        {
          this.jdField_b_of_type_JavaUtilHashMap.put(paramString, new ArrayList(localArrayList3));
        }
        else
        {
          localArrayList1.clear();
          localArrayList1.addAll(localArrayList3);
        }
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("updateServerList: type=");
      ((StringBuilder)localObject2).append(paramString);
      ((StringBuilder)localObject2).append(", servers=");
      ((StringBuilder)localObject2).append(paramArrayList);
      FLogger.d("IPListDataManager", ((StringBuilder)localObject2).toString());
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\serverconfig\IPListDataManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */