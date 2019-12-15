package com.tencent.smtt.downloader.utils;

import android.annotation.TargetApi;
import android.content.Context;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class g
{
  private static g jdField_a_of_type_ComTencentSmttDownloaderUtilsG;
  private Context jdField_a_of_type_AndroidContentContext = null;
  private File jdField_a_of_type_JavaIoFile = null;
  private String jdField_a_of_type_JavaLangString = "http://log.tbs.qq.com/ajax?c=pu&v=2&k=";
  private String b = "http://log.tbs.qq.com/ajax?c=pu&tk=";
  private String c = "http://wup.imtt.qq.com:8080";
  private String d = "http://log.tbs.qq.com/ajax?c=dl&k=";
  private String e = "http://cfg.imtt.qq.com/tbs?v=2&mk=";
  private String f = "http://log.tbs.qq.com/ajax?c=ul&v=2&k=";
  private String g = "http://mqqad.html5.qq.com/adjs";
  private String h = "http://log.tbs.qq.com/ajax?c=ucfu&k=";
  
  @TargetApi(11)
  private g(Context paramContext)
  {
    h.c("TbsCommonConfig", "TbsCommonConfig constructing...");
    this.jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
    a();
  }
  
  public static g a(Context paramContext)
  {
    try
    {
      if (jdField_a_of_type_ComTencentSmttDownloaderUtilsG == null) {
        jdField_a_of_type_ComTencentSmttDownloaderUtilsG = new g(paramContext);
      }
      paramContext = jdField_a_of_type_ComTencentSmttDownloaderUtilsG;
      return paramContext;
    }
    finally {}
  }
  
  private File a()
  {
    StringWriter localStringWriter = null;
    Object localObject;
    try
    {
      if (this.jdField_a_of_type_JavaIoFile == null)
      {
        this.jdField_a_of_type_JavaIoFile = new File(FileUtil.a(this.jdField_a_of_type_AndroidContentContext, 5));
        if (this.jdField_a_of_type_JavaIoFile == null) {
          break label207;
        }
        if (!this.jdField_a_of_type_JavaIoFile.isDirectory()) {
          return null;
        }
      }
      localObject = new File(this.jdField_a_of_type_JavaIoFile, "tbsnet.conf");
      StringBuilder localStringBuilder1;
      if (!((File)localObject).exists())
      {
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("Get file(");
        localStringBuilder1.append(((File)localObject).getCanonicalPath());
        localStringBuilder1.append(") failed!");
        h.b("TbsCommonConfig", localStringBuilder1.toString());
        return null;
      }
      try
      {
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("pathc:");
        localStringBuilder1.append(((File)localObject).getCanonicalPath());
        h.c("TbsCommonConfig", localStringBuilder1.toString());
        return (File)localObject;
      }
      catch (Throwable localThrowable1) {}
      localStringWriter = new StringWriter();
    }
    catch (Throwable localThrowable2)
    {
      localObject = localStringWriter;
    }
    localThrowable2.printStackTrace(new PrintWriter(localStringWriter));
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append("exceptions occurred2:");
    localStringBuilder2.append(localStringWriter.toString());
    h.b("TbsCommonConfig", localStringBuilder2.toString());
    return (File)localObject;
    label207:
    return null;
  }
  
  /* Error */
  private void a()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial 151	com/tencent/smtt/downloader/utils/g:a	()Ljava/io/File;
    //   6: astore_1
    //   7: aload_1
    //   8: ifnonnull +13 -> 21
    //   11: ldc 64
    //   13: ldc -103
    //   15: invokestatic 130	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   18: aload_0
    //   19: monitorexit
    //   20: return
    //   21: new 155	java/io/BufferedInputStream
    //   24: dup
    //   25: new 157	java/io/FileInputStream
    //   28: dup
    //   29: aload_1
    //   30: invokespecial 160	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   33: invokespecial 163	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   36: astore_2
    //   37: aload_2
    //   38: astore_1
    //   39: new 165	java/util/Properties
    //   42: dup
    //   43: invokespecial 166	java/util/Properties:<init>	()V
    //   46: astore_3
    //   47: aload_2
    //   48: astore_1
    //   49: aload_3
    //   50: aload_2
    //   51: invokevirtual 169	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   54: aload_2
    //   55: astore_1
    //   56: aload_3
    //   57: ldc -85
    //   59: ldc -83
    //   61: invokevirtual 177	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   64: astore 4
    //   66: aload_2
    //   67: astore_1
    //   68: ldc -83
    //   70: aload 4
    //   72: invokevirtual 183	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   75: ifne +11 -> 86
    //   78: aload_2
    //   79: astore_1
    //   80: aload_0
    //   81: aload 4
    //   83: putfield 34	com/tencent/smtt/downloader/utils/g:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   86: aload_2
    //   87: astore_1
    //   88: aload_3
    //   89: ldc -71
    //   91: ldc -83
    //   93: invokevirtual 177	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   96: astore 4
    //   98: aload_2
    //   99: astore_1
    //   100: ldc -83
    //   102: aload 4
    //   104: invokevirtual 183	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   107: ifne +11 -> 118
    //   110: aload_2
    //   111: astore_1
    //   112: aload_0
    //   113: aload 4
    //   115: putfield 42	com/tencent/smtt/downloader/utils/g:c	Ljava/lang/String;
    //   118: aload_2
    //   119: astore_1
    //   120: aload_3
    //   121: ldc -69
    //   123: ldc -83
    //   125: invokevirtual 177	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   128: astore 4
    //   130: aload_2
    //   131: astore_1
    //   132: ldc -83
    //   134: aload 4
    //   136: invokevirtual 183	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   139: ifne +11 -> 150
    //   142: aload_2
    //   143: astore_1
    //   144: aload_0
    //   145: aload 4
    //   147: putfield 46	com/tencent/smtt/downloader/utils/g:d	Ljava/lang/String;
    //   150: aload_2
    //   151: astore_1
    //   152: aload_3
    //   153: ldc -67
    //   155: ldc -83
    //   157: invokevirtual 177	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   160: astore 4
    //   162: aload_2
    //   163: astore_1
    //   164: ldc -83
    //   166: aload 4
    //   168: invokevirtual 183	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   171: ifne +11 -> 182
    //   174: aload_2
    //   175: astore_1
    //   176: aload_0
    //   177: aload 4
    //   179: putfield 50	com/tencent/smtt/downloader/utils/g:e	Ljava/lang/String;
    //   182: aload_2
    //   183: astore_1
    //   184: aload_3
    //   185: ldc -65
    //   187: ldc -83
    //   189: invokevirtual 177	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   192: astore 4
    //   194: aload_2
    //   195: astore_1
    //   196: ldc -83
    //   198: aload 4
    //   200: invokevirtual 183	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   203: ifne +11 -> 214
    //   206: aload_2
    //   207: astore_1
    //   208: aload_0
    //   209: aload 4
    //   211: putfield 54	com/tencent/smtt/downloader/utils/g:f	Ljava/lang/String;
    //   214: aload_2
    //   215: astore_1
    //   216: aload_3
    //   217: ldc -63
    //   219: ldc -83
    //   221: invokevirtual 177	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   224: astore 4
    //   226: aload_2
    //   227: astore_1
    //   228: ldc -83
    //   230: aload 4
    //   232: invokevirtual 183	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   235: ifne +11 -> 246
    //   238: aload_2
    //   239: astore_1
    //   240: aload_0
    //   241: aload 4
    //   243: putfield 58	com/tencent/smtt/downloader/utils/g:g	Ljava/lang/String;
    //   246: aload_2
    //   247: astore_1
    //   248: aload_3
    //   249: ldc -61
    //   251: ldc -83
    //   253: invokevirtual 177	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   256: astore 4
    //   258: aload_2
    //   259: astore_1
    //   260: ldc -83
    //   262: aload 4
    //   264: invokevirtual 183	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   267: ifne +11 -> 278
    //   270: aload_2
    //   271: astore_1
    //   272: aload_0
    //   273: aload 4
    //   275: putfield 62	com/tencent/smtt/downloader/utils/g:h	Ljava/lang/String;
    //   278: aload_2
    //   279: astore_1
    //   280: aload_3
    //   281: ldc -59
    //   283: ldc -83
    //   285: invokevirtual 177	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   288: astore_3
    //   289: aload_2
    //   290: astore_1
    //   291: ldc -83
    //   293: aload_3
    //   294: invokevirtual 183	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   297: ifne +10 -> 307
    //   300: aload_2
    //   301: astore_1
    //   302: aload_0
    //   303: aload_3
    //   304: putfield 38	com/tencent/smtt/downloader/utils/g:b	Ljava/lang/String;
    //   307: aload_2
    //   308: invokevirtual 200	java/io/BufferedInputStream:close	()V
    //   311: goto +107 -> 418
    //   314: astore_1
    //   315: aload_1
    //   316: invokevirtual 202	java/io/IOException:printStackTrace	()V
    //   319: goto +99 -> 418
    //   322: astore_3
    //   323: goto +12 -> 335
    //   326: astore_1
    //   327: aconst_null
    //   328: astore_2
    //   329: goto +97 -> 426
    //   332: astore_3
    //   333: aconst_null
    //   334: astore_2
    //   335: aload_2
    //   336: astore_1
    //   337: new 134	java/io/StringWriter
    //   340: dup
    //   341: invokespecial 135	java/io/StringWriter:<init>	()V
    //   344: astore 4
    //   346: aload_2
    //   347: astore_1
    //   348: aload_3
    //   349: new 137	java/io/PrintWriter
    //   352: dup
    //   353: aload 4
    //   355: invokespecial 140	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   358: invokevirtual 144	java/lang/Throwable:printStackTrace	(Ljava/io/PrintWriter;)V
    //   361: aload_2
    //   362: astore_1
    //   363: new 112	java/lang/StringBuilder
    //   366: dup
    //   367: invokespecial 113	java/lang/StringBuilder:<init>	()V
    //   370: astore_3
    //   371: aload_2
    //   372: astore_1
    //   373: aload_3
    //   374: ldc -52
    //   376: invokevirtual 119	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   379: pop
    //   380: aload_2
    //   381: astore_1
    //   382: aload_3
    //   383: aload 4
    //   385: invokevirtual 147	java/io/StringWriter:toString	()Ljava/lang/String;
    //   388: invokevirtual 119	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   391: pop
    //   392: aload_2
    //   393: astore_1
    //   394: ldc 64
    //   396: aload_3
    //   397: invokevirtual 128	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   400: invokestatic 130	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   403: aload_2
    //   404: ifnull +14 -> 418
    //   407: aload_2
    //   408: invokevirtual 200	java/io/BufferedInputStream:close	()V
    //   411: goto +7 -> 418
    //   414: astore_1
    //   415: goto -100 -> 315
    //   418: aload_0
    //   419: monitorexit
    //   420: return
    //   421: astore_3
    //   422: aload_1
    //   423: astore_2
    //   424: aload_3
    //   425: astore_1
    //   426: aload_2
    //   427: ifnull +15 -> 442
    //   430: aload_2
    //   431: invokevirtual 200	java/io/BufferedInputStream:close	()V
    //   434: goto +8 -> 442
    //   437: astore_2
    //   438: aload_2
    //   439: invokevirtual 202	java/io/IOException:printStackTrace	()V
    //   442: aload_1
    //   443: athrow
    //   444: astore_1
    //   445: aload_0
    //   446: monitorexit
    //   447: aload_1
    //   448: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	449	0	this	g
    //   6	296	1	localObject1	Object
    //   314	2	1	localIOException1	java.io.IOException
    //   326	1	1	localObject2	Object
    //   336	58	1	localObject3	Object
    //   414	9	1	localIOException2	java.io.IOException
    //   425	18	1	localObject4	Object
    //   444	4	1	localObject5	Object
    //   36	395	2	localObject6	Object
    //   437	2	2	localIOException3	java.io.IOException
    //   46	258	3	localObject7	Object
    //   322	1	3	localThrowable1	Throwable
    //   332	17	3	localThrowable2	Throwable
    //   370	27	3	localStringBuilder	StringBuilder
    //   421	4	3	localObject8	Object
    //   64	320	4	localObject9	Object
    // Exception table:
    //   from	to	target	type
    //   307	311	314	java/io/IOException
    //   39	47	322	java/lang/Throwable
    //   49	54	322	java/lang/Throwable
    //   56	66	322	java/lang/Throwable
    //   68	78	322	java/lang/Throwable
    //   80	86	322	java/lang/Throwable
    //   88	98	322	java/lang/Throwable
    //   100	110	322	java/lang/Throwable
    //   112	118	322	java/lang/Throwable
    //   120	130	322	java/lang/Throwable
    //   132	142	322	java/lang/Throwable
    //   144	150	322	java/lang/Throwable
    //   152	162	322	java/lang/Throwable
    //   164	174	322	java/lang/Throwable
    //   176	182	322	java/lang/Throwable
    //   184	194	322	java/lang/Throwable
    //   196	206	322	java/lang/Throwable
    //   208	214	322	java/lang/Throwable
    //   216	226	322	java/lang/Throwable
    //   228	238	322	java/lang/Throwable
    //   240	246	322	java/lang/Throwable
    //   248	258	322	java/lang/Throwable
    //   260	270	322	java/lang/Throwable
    //   272	278	322	java/lang/Throwable
    //   280	289	322	java/lang/Throwable
    //   291	300	322	java/lang/Throwable
    //   302	307	322	java/lang/Throwable
    //   2	7	326	finally
    //   11	18	326	finally
    //   21	37	326	finally
    //   2	7	332	java/lang/Throwable
    //   11	18	332	java/lang/Throwable
    //   21	37	332	java/lang/Throwable
    //   407	411	414	java/io/IOException
    //   39	47	421	finally
    //   49	54	421	finally
    //   56	66	421	finally
    //   68	78	421	finally
    //   80	86	421	finally
    //   88	98	421	finally
    //   100	110	421	finally
    //   112	118	421	finally
    //   120	130	421	finally
    //   132	142	421	finally
    //   144	150	421	finally
    //   152	162	421	finally
    //   164	174	421	finally
    //   176	182	421	finally
    //   184	194	421	finally
    //   196	206	421	finally
    //   208	214	421	finally
    //   216	226	421	finally
    //   228	238	421	finally
    //   240	246	421	finally
    //   248	258	421	finally
    //   260	270	421	finally
    //   272	278	421	finally
    //   280	289	421	finally
    //   291	300	421	finally
    //   302	307	421	finally
    //   337	346	421	finally
    //   348	361	421	finally
    //   363	371	421	finally
    //   373	380	421	finally
    //   382	392	421	finally
    //   394	403	421	finally
    //   430	434	437	java/io/IOException
    //   307	311	444	finally
    //   315	319	444	finally
    //   407	411	444	finally
    //   430	434	444	finally
    //   438	442	444	finally
    //   442	444	444	finally
  }
  
  public String a()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getTbsDownloaderPostUrl:");
    localStringBuilder.append(this.e);
    h.d("TbsCommonConfig", localStringBuilder.toString());
    return this.e;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\utils\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */