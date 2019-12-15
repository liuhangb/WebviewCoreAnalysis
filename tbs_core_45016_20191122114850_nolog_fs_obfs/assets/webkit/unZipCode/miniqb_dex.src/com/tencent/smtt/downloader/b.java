package com.tencent.smtt.downloader;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

class b
{
  private static Context jdField_a_of_type_AndroidContentContext;
  private static b jdField_a_of_type_ComTencentSmttDownloaderB;
  
  static b a(Context paramContext)
  {
    if (jdField_a_of_type_ComTencentSmttDownloaderB == null) {
      try
      {
        if (jdField_a_of_type_ComTencentSmttDownloaderB == null) {
          jdField_a_of_type_ComTencentSmttDownloaderB = new b();
        }
      }
      finally {}
    }
    jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
    return jdField_a_of_type_ComTencentSmttDownloaderB;
  }
  
  /* Error */
  private Properties a()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore 5
    //   5: aconst_null
    //   6: astore_3
    //   7: aload_2
    //   8: astore_1
    //   9: aload_0
    //   10: invokevirtual 33	com/tencent/smtt/downloader/b:a	()Ljava/io/File;
    //   13: astore 6
    //   15: aload_2
    //   16: astore_1
    //   17: new 35	java/util/Properties
    //   20: dup
    //   21: invokespecial 36	java/util/Properties:<init>	()V
    //   24: astore 4
    //   26: aload_3
    //   27: astore_1
    //   28: aload 6
    //   30: ifnull +48 -> 78
    //   33: aload_2
    //   34: astore_1
    //   35: new 38	java/io/BufferedInputStream
    //   38: dup
    //   39: new 40	java/io/FileInputStream
    //   42: dup
    //   43: aload 6
    //   45: invokespecial 43	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   48: invokespecial 46	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   51: astore_2
    //   52: aload 4
    //   54: aload_2
    //   55: invokevirtual 49	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   58: aload_2
    //   59: astore_1
    //   60: goto +18 -> 78
    //   63: astore_1
    //   64: goto +73 -> 137
    //   67: astore_3
    //   68: goto +44 -> 112
    //   71: astore_3
    //   72: aload 5
    //   74: astore_2
    //   75: goto +37 -> 112
    //   78: aload_1
    //   79: ifnull +15 -> 94
    //   82: aload_1
    //   83: invokevirtual 52	java/io/BufferedInputStream:close	()V
    //   86: aload 4
    //   88: areturn
    //   89: astore_1
    //   90: aload_1
    //   91: invokevirtual 55	java/io/IOException:printStackTrace	()V
    //   94: aload 4
    //   96: areturn
    //   97: astore_3
    //   98: aload_1
    //   99: astore_2
    //   100: aload_3
    //   101: astore_1
    //   102: goto +35 -> 137
    //   105: astore_3
    //   106: aconst_null
    //   107: astore 4
    //   109: aload 5
    //   111: astore_2
    //   112: aload_2
    //   113: astore_1
    //   114: aload_3
    //   115: invokevirtual 56	java/lang/Exception:printStackTrace	()V
    //   118: aload_2
    //   119: ifnull +15 -> 134
    //   122: aload_2
    //   123: invokevirtual 52	java/io/BufferedInputStream:close	()V
    //   126: aload 4
    //   128: areturn
    //   129: astore_1
    //   130: aload_1
    //   131: invokevirtual 55	java/io/IOException:printStackTrace	()V
    //   134: aload 4
    //   136: areturn
    //   137: aload_2
    //   138: ifnull +15 -> 153
    //   141: aload_2
    //   142: invokevirtual 52	java/io/BufferedInputStream:close	()V
    //   145: goto +8 -> 153
    //   148: astore_2
    //   149: aload_2
    //   150: invokevirtual 55	java/io/IOException:printStackTrace	()V
    //   153: aload_1
    //   154: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	155	0	this	b
    //   8	52	1	localObject1	Object
    //   63	20	1	localObject2	Object
    //   89	10	1	localIOException1	IOException
    //   101	13	1	localObject3	Object
    //   129	25	1	localIOException2	IOException
    //   1	141	2	localObject4	Object
    //   148	2	2	localIOException3	IOException
    //   6	21	3	localObject5	Object
    //   67	1	3	localException1	Exception
    //   71	1	3	localException2	Exception
    //   97	4	3	localObject6	Object
    //   105	10	3	localException3	Exception
    //   24	111	4	localProperties	Properties
    //   3	107	5	localObject7	Object
    //   13	31	6	localFile	File
    // Exception table:
    //   from	to	target	type
    //   52	58	63	finally
    //   52	58	67	java/lang/Exception
    //   35	52	71	java/lang/Exception
    //   82	86	89	java/io/IOException
    //   9	15	97	finally
    //   17	26	97	finally
    //   35	52	97	finally
    //   114	118	97	finally
    //   9	15	105	java/lang/Exception
    //   17	26	105	java/lang/Exception
    //   122	126	129	java/io/IOException
    //   141	145	148	java/io/IOException
  }
  
  int a()
  {
    return b("install_core_ver");
  }
  
  int a(String paramString)
  {
    Properties localProperties = a();
    if ((localProperties != null) && (localProperties.getProperty(paramString) != null)) {
      return Integer.parseInt(localProperties.getProperty(paramString));
    }
    return -1;
  }
  
  File a()
  {
    e.a();
    File localFile = new File(e.d(jdField_a_of_type_AndroidContentContext), "tbscoreinstall.txt");
    if (!localFile.exists()) {
      try
      {
        localFile.createNewFile();
        return localFile;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        return null;
      }
    }
    return localIOException;
  }
  
  String a(String paramString)
  {
    Properties localProperties = a();
    if ((localProperties != null) && (localProperties.getProperty(paramString) != null)) {
      return localProperties.getProperty(paramString);
    }
    return null;
  }
  
  void a(int paramInt)
  {
    a("dexopt_retry_num", paramInt);
  }
  
  void a(int paramInt1, int paramInt2)
  {
    a("copy_core_ver", paramInt1);
    a("copy_status", paramInt2);
  }
  
  void a(String paramString)
  {
    a("install_apk_path", paramString);
  }
  
  void a(String paramString, int paramInt)
  {
    a(paramString, String.valueOf(paramInt));
  }
  
  /* Error */
  void a(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 122	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   7: astore_3
    //   8: aload_3
    //   9: ldc 125
    //   11: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: pop
    //   15: aload_3
    //   16: aload_1
    //   17: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: pop
    //   21: aload_3
    //   22: ldc -125
    //   24: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: pop
    //   28: aload_3
    //   29: aload_2
    //   30: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: pop
    //   34: ldc -123
    //   36: aload_3
    //   37: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   40: invokestatic 141	com/tencent/smtt/downloader/utils/h:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   43: aconst_null
    //   44: astore 6
    //   46: aconst_null
    //   47: astore 4
    //   49: aconst_null
    //   50: astore 5
    //   52: aload 6
    //   54: astore_3
    //   55: aload_0
    //   56: invokespecial 65	com/tencent/smtt/downloader/b:a	()Ljava/util/Properties;
    //   59: astore 7
    //   61: aload 5
    //   63: astore_3
    //   64: aload 7
    //   66: ifnull +96 -> 162
    //   69: aload 6
    //   71: astore_3
    //   72: aload 7
    //   74: aload_1
    //   75: aload_2
    //   76: invokevirtual 145	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   79: pop
    //   80: aload 6
    //   82: astore_3
    //   83: aload_0
    //   84: invokevirtual 33	com/tencent/smtt/downloader/b:a	()Ljava/io/File;
    //   87: astore_2
    //   88: aload 5
    //   90: astore_3
    //   91: aload_2
    //   92: ifnull +70 -> 162
    //   95: aload 6
    //   97: astore_3
    //   98: new 147	java/io/FileOutputStream
    //   101: dup
    //   102: aload_2
    //   103: invokespecial 148	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   106: astore_2
    //   107: new 122	java/lang/StringBuilder
    //   110: dup
    //   111: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   114: astore_3
    //   115: aload_3
    //   116: ldc -106
    //   118: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: pop
    //   122: aload_3
    //   123: aload_1
    //   124: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: pop
    //   128: aload_3
    //   129: ldc -104
    //   131: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: pop
    //   135: aload 7
    //   137: aload_2
    //   138: aload_3
    //   139: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   142: invokevirtual 156	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   145: aload_2
    //   146: astore_3
    //   147: goto +15 -> 162
    //   150: astore_1
    //   151: goto +51 -> 202
    //   154: astore_3
    //   155: aload_2
    //   156: astore_1
    //   157: aload_3
    //   158: astore_2
    //   159: goto +28 -> 187
    //   162: aload_3
    //   163: ifnull +38 -> 201
    //   166: aload_3
    //   167: invokevirtual 157	java/io/FileOutputStream:close	()V
    //   170: return
    //   171: astore_1
    //   172: aload_1
    //   173: invokevirtual 55	java/io/IOException:printStackTrace	()V
    //   176: return
    //   177: astore_1
    //   178: aload_3
    //   179: astore_2
    //   180: goto +22 -> 202
    //   183: astore_2
    //   184: aload 4
    //   186: astore_1
    //   187: aload_1
    //   188: astore_3
    //   189: aload_2
    //   190: invokevirtual 56	java/lang/Exception:printStackTrace	()V
    //   193: aload_1
    //   194: ifnull +7 -> 201
    //   197: aload_1
    //   198: invokevirtual 157	java/io/FileOutputStream:close	()V
    //   201: return
    //   202: aload_2
    //   203: ifnull +15 -> 218
    //   206: aload_2
    //   207: invokevirtual 157	java/io/FileOutputStream:close	()V
    //   210: goto +8 -> 218
    //   213: astore_2
    //   214: aload_2
    //   215: invokevirtual 55	java/io/IOException:printStackTrace	()V
    //   218: aload_1
    //   219: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	220	0	this	b
    //   0	220	1	paramString1	String
    //   0	220	2	paramString2	String
    //   7	140	3	localObject1	Object
    //   154	25	3	localException	Exception
    //   188	1	3	str	String
    //   47	138	4	localObject2	Object
    //   50	39	5	localObject3	Object
    //   44	52	6	localObject4	Object
    //   59	77	7	localProperties	Properties
    // Exception table:
    //   from	to	target	type
    //   107	145	150	finally
    //   107	145	154	java/lang/Exception
    //   166	170	171	java/io/IOException
    //   197	201	171	java/io/IOException
    //   55	61	177	finally
    //   72	80	177	finally
    //   83	88	177	finally
    //   98	107	177	finally
    //   189	193	177	finally
    //   55	61	183	java/lang/Exception
    //   72	80	183	java/lang/Exception
    //   83	88	183	java/lang/Exception
    //   98	107	183	java/lang/Exception
    //   206	210	213	java/io/IOException
  }
  
  int b()
  {
    return a("install_status");
  }
  
  int b(String paramString)
  {
    Properties localProperties = a();
    if ((localProperties != null) && (localProperties.getProperty(paramString) != null)) {
      return Integer.parseInt(localProperties.getProperty(paramString));
    }
    return 0;
  }
  
  void b(int paramInt)
  {
    a("unzip_retry_num", paramInt);
  }
  
  void b(int paramInt1, int paramInt2)
  {
    a("install_core_ver", paramInt1);
    a("install_status", paramInt2);
  }
  
  int c()
  {
    return a("incrupdate_status");
  }
  
  void c(int paramInt)
  {
    a("incrupdate_status", paramInt);
  }
  
  void d(int paramInt)
  {
    a("unlzma_status", paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */