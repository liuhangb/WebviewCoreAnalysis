package com.tencent.smtt.downloader;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TbsDownloadUpload
{
  private static TbsDownloadUpload jdField_a_of_type_ComTencentSmttDownloaderTbsDownloadUpload;
  private Context jdField_a_of_type_AndroidContentContext;
  public SharedPreferences a;
  Map<String, Object> jdField_a_of_type_JavaUtilMap = new HashMap();
  
  public TbsDownloadUpload(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentSharedPreferences = paramContext.getSharedPreferences("tbs_download_upload_64", 4);
    this.jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
    if (this.jdField_a_of_type_AndroidContentContext == null) {
      this.jdField_a_of_type_AndroidContentContext = paramContext;
    }
  }
  
  public static TbsDownloadUpload a(Context paramContext)
  {
    try
    {
      if (jdField_a_of_type_ComTencentSmttDownloaderTbsDownloadUpload == null) {
        jdField_a_of_type_ComTencentSmttDownloaderTbsDownloadUpload = new TbsDownloadUpload(paramContext);
      }
      paramContext = jdField_a_of_type_ComTencentSmttDownloaderTbsDownloadUpload;
      return paramContext;
    }
    finally {}
  }
  
  private static File a(Context paramContext, String paramString)
  {
    e.a();
    paramContext = e.d(paramContext);
    if (paramContext == null) {
      return null;
    }
    paramContext = new File(paramContext, paramString);
    if (paramContext.exists()) {
      return paramContext;
    }
    try
    {
      paramContext.createNewFile();
      return paramContext;
    }
    catch (IOException paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public static void a()
  {
    try
    {
      jdField_a_of_type_ComTencentSmttDownloaderTbsDownloadUpload = null;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public void b()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 76
    //   4: ldc 78
    //   6: invokestatic 83	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   9: aconst_null
    //   10: astore_2
    //   11: aconst_null
    //   12: astore 4
    //   14: aload_0
    //   15: getfield 36	com/tencent/smtt/downloader/TbsDownloadUpload:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   18: ldc 85
    //   20: invokestatic 87	com/tencent/smtt/downloader/TbsDownloadUpload:a	(Landroid/content/Context;Ljava/lang/String;)Ljava/io/File;
    //   23: astore_1
    //   24: aload_1
    //   25: ifnonnull +6 -> 31
    //   28: aload_0
    //   29: monitorexit
    //   30: return
    //   31: new 89	java/io/BufferedInputStream
    //   34: dup
    //   35: new 91	java/io/FileInputStream
    //   38: dup
    //   39: aload_1
    //   40: invokespecial 94	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   43: invokespecial 97	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   46: astore_3
    //   47: new 99	java/util/Properties
    //   50: dup
    //   51: invokespecial 100	java/util/Properties:<init>	()V
    //   54: astore 4
    //   56: aload 4
    //   58: aload_3
    //   59: invokevirtual 103	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   62: aload_0
    //   63: getfield 20	com/tencent/smtt/downloader/TbsDownloadUpload:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   66: invokeinterface 109 1 0
    //   71: invokeinterface 115 1 0
    //   76: astore 5
    //   78: aload 5
    //   80: invokeinterface 120 1 0
    //   85: ifeq +120 -> 205
    //   88: aload 5
    //   90: invokeinterface 124 1 0
    //   95: checkcast 126	java/lang/String
    //   98: astore 6
    //   100: aload_0
    //   101: getfield 20	com/tencent/smtt/downloader/TbsDownloadUpload:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   104: aload 6
    //   106: invokeinterface 130 2 0
    //   111: astore 7
    //   113: new 132	java/lang/StringBuilder
    //   116: dup
    //   117: invokespecial 133	java/lang/StringBuilder:<init>	()V
    //   120: astore 8
    //   122: aload 8
    //   124: ldc -121
    //   126: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   129: pop
    //   130: aload 8
    //   132: aload 7
    //   134: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   137: pop
    //   138: aload 4
    //   140: aload 6
    //   142: aload 8
    //   144: invokevirtual 146	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   147: invokevirtual 150	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   150: pop
    //   151: new 132	java/lang/StringBuilder
    //   154: dup
    //   155: invokespecial 133	java/lang/StringBuilder:<init>	()V
    //   158: astore 8
    //   160: aload 8
    //   162: ldc -104
    //   164: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: pop
    //   168: aload 8
    //   170: aload 6
    //   172: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   175: pop
    //   176: aload 8
    //   178: ldc -102
    //   180: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   183: pop
    //   184: aload 8
    //   186: aload 7
    //   188: invokevirtual 142	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   191: pop
    //   192: ldc 76
    //   194: aload 8
    //   196: invokevirtual 146	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   199: invokestatic 83	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   202: goto -124 -> 78
    //   205: aload_0
    //   206: getfield 20	com/tencent/smtt/downloader/TbsDownloadUpload:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   209: invokeinterface 157 1 0
    //   214: new 159	java/io/BufferedOutputStream
    //   217: dup
    //   218: new 161	java/io/FileOutputStream
    //   221: dup
    //   222: aload_1
    //   223: invokespecial 162	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   226: invokespecial 165	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   229: astore_1
    //   230: aload 4
    //   232: aload_1
    //   233: aconst_null
    //   234: invokevirtual 169	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   237: aload_3
    //   238: invokevirtual 172	java/io/BufferedInputStream:close	()V
    //   241: goto +8 -> 249
    //   244: astore_2
    //   245: aload_2
    //   246: invokevirtual 173	java/lang/Exception:printStackTrace	()V
    //   249: aload_1
    //   250: invokevirtual 174	java/io/BufferedOutputStream:close	()V
    //   253: goto +82 -> 335
    //   256: astore_1
    //   257: aload_1
    //   258: invokevirtual 173	java/lang/Exception:printStackTrace	()V
    //   261: goto +74 -> 335
    //   264: astore 4
    //   266: aload_1
    //   267: astore_2
    //   268: aload 4
    //   270: astore_1
    //   271: goto +74 -> 345
    //   274: astore_2
    //   275: goto +10 -> 285
    //   278: astore_1
    //   279: goto +66 -> 345
    //   282: astore_2
    //   283: aconst_null
    //   284: astore_1
    //   285: goto +15 -> 300
    //   288: astore_1
    //   289: aconst_null
    //   290: astore_3
    //   291: goto +54 -> 345
    //   294: astore_2
    //   295: aconst_null
    //   296: astore_1
    //   297: aload 4
    //   299: astore_3
    //   300: aload_2
    //   301: invokevirtual 175	java/lang/Throwable:printStackTrace	()V
    //   304: aload_3
    //   305: ifnull +15 -> 320
    //   308: aload_3
    //   309: invokevirtual 172	java/io/BufferedInputStream:close	()V
    //   312: goto +8 -> 320
    //   315: astore_2
    //   316: aload_2
    //   317: invokevirtual 173	java/lang/Exception:printStackTrace	()V
    //   320: aload_1
    //   321: ifnull +14 -> 335
    //   324: aload_1
    //   325: invokevirtual 174	java/io/BufferedOutputStream:close	()V
    //   328: goto +7 -> 335
    //   331: astore_1
    //   332: goto -75 -> 257
    //   335: aload_0
    //   336: monitorexit
    //   337: return
    //   338: astore 4
    //   340: aload_1
    //   341: astore_2
    //   342: aload 4
    //   344: astore_1
    //   345: aload_3
    //   346: ifnull +15 -> 361
    //   349: aload_3
    //   350: invokevirtual 172	java/io/BufferedInputStream:close	()V
    //   353: goto +8 -> 361
    //   356: astore_3
    //   357: aload_3
    //   358: invokevirtual 173	java/lang/Exception:printStackTrace	()V
    //   361: aload_2
    //   362: ifnull +15 -> 377
    //   365: aload_2
    //   366: invokevirtual 174	java/io/BufferedOutputStream:close	()V
    //   369: goto +8 -> 377
    //   372: astore_2
    //   373: aload_2
    //   374: invokevirtual 173	java/lang/Exception:printStackTrace	()V
    //   377: aload_1
    //   378: athrow
    //   379: astore_1
    //   380: aload_0
    //   381: monitorexit
    //   382: aload_1
    //   383: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	384	0	this	TbsDownloadUpload
    //   23	227	1	localObject1	Object
    //   256	11	1	localException1	Exception
    //   270	1	1	localObject2	Object
    //   278	1	1	localObject3	Object
    //   284	1	1	localObject4	Object
    //   288	1	1	localObject5	Object
    //   296	29	1	localObject6	Object
    //   331	10	1	localException2	Exception
    //   344	34	1	localObject7	Object
    //   379	4	1	localObject8	Object
    //   10	1	2	localObject9	Object
    //   244	2	2	localException3	Exception
    //   267	1	2	localObject10	Object
    //   274	1	2	localThrowable1	Throwable
    //   282	1	2	localThrowable2	Throwable
    //   294	7	2	localThrowable3	Throwable
    //   315	2	2	localException4	Exception
    //   341	25	2	localObject11	Object
    //   372	2	2	localException5	Exception
    //   46	304	3	localObject12	Object
    //   356	2	3	localException6	Exception
    //   12	219	4	localProperties	java.util.Properties
    //   264	34	4	localObject13	Object
    //   338	5	4	localObject14	Object
    //   76	13	5	localIterator	java.util.Iterator
    //   98	73	6	str	String
    //   111	76	7	localObject15	Object
    //   120	75	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   237	241	244	java/lang/Exception
    //   249	253	256	java/lang/Exception
    //   230	237	264	finally
    //   230	237	274	java/lang/Throwable
    //   47	78	278	finally
    //   78	202	278	finally
    //   205	230	278	finally
    //   47	78	282	java/lang/Throwable
    //   78	202	282	java/lang/Throwable
    //   205	230	282	java/lang/Throwable
    //   14	24	288	finally
    //   31	47	288	finally
    //   14	24	294	java/lang/Throwable
    //   31	47	294	java/lang/Throwable
    //   308	312	315	java/lang/Exception
    //   324	328	331	java/lang/Exception
    //   300	304	338	finally
    //   349	353	356	java/lang/Exception
    //   365	369	372	java/lang/Exception
    //   2	9	379	finally
    //   237	241	379	finally
    //   245	249	379	finally
    //   249	253	379	finally
    //   257	261	379	finally
    //   308	312	379	finally
    //   316	320	379	finally
    //   324	328	379	finally
    //   349	353	379	finally
    //   357	361	379	finally
    //   365	369	379	finally
    //   373	377	379	finally
    //   377	379	379	finally
  }
  
  public void c()
  {
    try
    {
      b();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\TbsDownloadUpload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */