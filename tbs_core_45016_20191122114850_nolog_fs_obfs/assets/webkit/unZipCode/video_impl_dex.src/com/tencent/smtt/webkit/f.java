package com.tencent.smtt.webkit;

public class f
{
  private static Boolean jdField_a_of_type_JavaLangBoolean = Boolean.valueOf(false);
  private static String jdField_a_of_type_JavaLangString = "ErrorPageManager";
  private static byte[] jdField_a_of_type_ArrayOfByte = null;
  
  public static void a()
  {
    jdField_a_of_type_ArrayOfByte = null;
    jdField_a_of_type_JavaLangBoolean = Boolean.valueOf(false);
  }
  
  /* Error */
  public static void a(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 32	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifne +292 -> 296
    //   7: aload_0
    //   8: ifnonnull +6 -> 14
    //   11: goto +285 -> 296
    //   14: getstatic 22	com/tencent/smtt/webkit/f:jdField_a_of_type_ArrayOfByte	[B
    //   17: astore_1
    //   18: aload_1
    //   19: ifnull +8 -> 27
    //   22: aload_1
    //   23: arraylength
    //   24: ifne +213 -> 237
    //   27: new 34	java/lang/StringBuilder
    //   30: dup
    //   31: invokespecial 37	java/lang/StringBuilder:<init>	()V
    //   34: astore_1
    //   35: aload_1
    //   36: aload_0
    //   37: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: pop
    //   41: aload_1
    //   42: getstatic 46	java/io/File:separator	Ljava/lang/String;
    //   45: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: pop
    //   49: aload_1
    //   50: ldc 48
    //   52: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: pop
    //   56: aload_1
    //   57: getstatic 46	java/io/File:separator	Ljava/lang/String;
    //   60: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   63: pop
    //   64: aload_1
    //   65: ldc 50
    //   67: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: pop
    //   71: new 43	java/io/File
    //   74: dup
    //   75: aload_1
    //   76: invokevirtual 54	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   79: invokespecial 56	java/io/File:<init>	(Ljava/lang/String;)V
    //   82: astore_0
    //   83: aload_0
    //   84: invokevirtual 60	java/io/File:exists	()Z
    //   87: ifne +12 -> 99
    //   90: getstatic 62	com/tencent/smtt/webkit/f:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   93: ldc 64
    //   95: invokestatic 70	com/tencent/smtt/util/X5Log:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   98: return
    //   99: new 72	java/io/FileInputStream
    //   102: dup
    //   103: aload_0
    //   104: invokespecial 75	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   107: astore_1
    //   108: aload_1
    //   109: astore_0
    //   110: aload_1
    //   111: invokestatic 80	com/tencent/smtt/util/d:a	(Ljava/io/InputStream;)[B
    //   114: putstatic 22	com/tencent/smtt/webkit/f:jdField_a_of_type_ArrayOfByte	[B
    //   117: aload_1
    //   118: astore_0
    //   119: aload_1
    //   120: invokevirtual 85	java/io/InputStream:close	()V
    //   123: goto +114 -> 237
    //   126: astore_2
    //   127: goto +12 -> 139
    //   130: astore_1
    //   131: aconst_null
    //   132: astore_0
    //   133: goto +113 -> 246
    //   136: astore_2
    //   137: aconst_null
    //   138: astore_1
    //   139: aload_1
    //   140: astore_0
    //   141: getstatic 62	com/tencent/smtt/webkit/f:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   144: astore_3
    //   145: aload_1
    //   146: astore_0
    //   147: new 34	java/lang/StringBuilder
    //   150: dup
    //   151: invokespecial 37	java/lang/StringBuilder:<init>	()V
    //   154: astore 4
    //   156: aload_1
    //   157: astore_0
    //   158: aload 4
    //   160: ldc 87
    //   162: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: pop
    //   166: aload_1
    //   167: astore_0
    //   168: aload 4
    //   170: aload_2
    //   171: invokevirtual 90	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   174: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   177: pop
    //   178: aload_1
    //   179: astore_0
    //   180: aload_3
    //   181: aload 4
    //   183: invokevirtual 54	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   186: invokestatic 70	com/tencent/smtt/util/X5Log:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   189: aload_1
    //   190: ifnull +47 -> 237
    //   193: aload_1
    //   194: invokevirtual 85	java/io/InputStream:close	()V
    //   197: goto +40 -> 237
    //   200: astore_0
    //   201: getstatic 62	com/tencent/smtt/webkit/f:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   204: astore_1
    //   205: new 34	java/lang/StringBuilder
    //   208: dup
    //   209: invokespecial 37	java/lang/StringBuilder:<init>	()V
    //   212: astore_2
    //   213: aload_2
    //   214: ldc 92
    //   216: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   219: pop
    //   220: aload_2
    //   221: aload_0
    //   222: invokevirtual 90	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   225: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   228: pop
    //   229: aload_1
    //   230: aload_2
    //   231: invokevirtual 54	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   234: invokestatic 70	com/tencent/smtt/util/X5Log:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   237: iconst_1
    //   238: invokestatic 18	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   241: putstatic 20	com/tencent/smtt/webkit/f:jdField_a_of_type_JavaLangBoolean	Ljava/lang/Boolean;
    //   244: return
    //   245: astore_1
    //   246: aload_0
    //   247: ifnull +47 -> 294
    //   250: aload_0
    //   251: invokevirtual 85	java/io/InputStream:close	()V
    //   254: goto +40 -> 294
    //   257: astore_0
    //   258: getstatic 62	com/tencent/smtt/webkit/f:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   261: astore_2
    //   262: new 34	java/lang/StringBuilder
    //   265: dup
    //   266: invokespecial 37	java/lang/StringBuilder:<init>	()V
    //   269: astore_3
    //   270: aload_3
    //   271: ldc 92
    //   273: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   276: pop
    //   277: aload_3
    //   278: aload_0
    //   279: invokevirtual 90	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   282: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   285: pop
    //   286: aload_2
    //   287: aload_3
    //   288: invokevirtual 54	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   291: invokestatic 70	com/tencent/smtt/util/X5Log:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   294: aload_1
    //   295: athrow
    //   296: getstatic 62	com/tencent/smtt/webkit/f:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   299: ldc 94
    //   301: invokestatic 70	com/tencent/smtt/util/X5Log:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   304: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	305	0	paramString	String
    //   17	103	1	localObject1	Object
    //   130	1	1	localObject2	Object
    //   138	92	1	str	String
    //   245	50	1	localObject3	Object
    //   126	1	2	localException1	Exception
    //   136	35	2	localException2	Exception
    //   212	75	2	localObject4	Object
    //   144	144	3	localObject5	Object
    //   154	28	4	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   110	117	126	java/lang/Exception
    //   119	123	126	java/lang/Exception
    //   99	108	130	finally
    //   99	108	136	java/lang/Exception
    //   193	197	200	java/lang/Exception
    //   110	117	245	finally
    //   119	123	245	finally
    //   141	145	245	finally
    //   147	156	245	finally
    //   158	166	245	finally
    //   168	178	245	finally
    //   180	189	245	finally
    //   250	254	257	java/lang/Exception
  }
  
  public static byte[] a()
  {
    if (jdField_a_of_type_JavaLangBoolean.booleanValue()) {
      return jdField_a_of_type_ArrayOfByte;
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */