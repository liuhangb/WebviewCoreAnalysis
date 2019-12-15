package com.tencent.tbs.common.utils;

public class ApkUtil
{
  /* Error */
  public static boolean isRealApkFile(java.io.File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 4
    //   6: aload 4
    //   8: astore_3
    //   9: iconst_2
    //   10: newarray <illegal type>
    //   12: astore 6
    //   14: aload 4
    //   16: astore_3
    //   17: new 17	java/io/RandomAccessFile
    //   20: dup
    //   21: aload_0
    //   22: ldc 19
    //   24: invokespecial 22	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   27: astore_0
    //   28: aload_0
    //   29: aload 6
    //   31: invokevirtual 26	java/io/RandomAccessFile:read	([B)I
    //   34: istore_1
    //   35: iload_1
    //   36: ifne +16 -> 52
    //   39: aload_0
    //   40: invokevirtual 29	java/io/RandomAccessFile:close	()V
    //   43: iconst_0
    //   44: ireturn
    //   45: astore_0
    //   46: aload_0
    //   47: invokevirtual 32	java/io/IOException:printStackTrace	()V
    //   50: iconst_0
    //   51: ireturn
    //   52: new 34	java/lang/String
    //   55: dup
    //   56: aload 6
    //   58: ldc 36
    //   60: invokespecial 39	java/lang/String:<init>	([BLjava/lang/String;)V
    //   63: ldc 41
    //   65: invokevirtual 45	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   68: istore_2
    //   69: iload_2
    //   70: ifne +16 -> 86
    //   73: aload_0
    //   74: invokevirtual 29	java/io/RandomAccessFile:close	()V
    //   77: iconst_0
    //   78: ireturn
    //   79: astore_0
    //   80: aload_0
    //   81: invokevirtual 32	java/io/IOException:printStackTrace	()V
    //   84: iconst_0
    //   85: ireturn
    //   86: aload_0
    //   87: invokevirtual 29	java/io/RandomAccessFile:close	()V
    //   90: goto +50 -> 140
    //   93: astore_3
    //   94: goto +48 -> 142
    //   97: astore 4
    //   99: goto +18 -> 117
    //   102: astore 4
    //   104: aload_3
    //   105: astore_0
    //   106: aload 4
    //   108: astore_3
    //   109: goto +33 -> 142
    //   112: astore 4
    //   114: aload 5
    //   116: astore_0
    //   117: aload_0
    //   118: astore_3
    //   119: aload 4
    //   121: invokevirtual 46	java/lang/Exception:printStackTrace	()V
    //   124: aload_0
    //   125: ifnull +15 -> 140
    //   128: aload_0
    //   129: invokevirtual 29	java/io/RandomAccessFile:close	()V
    //   132: goto +8 -> 140
    //   135: astore_0
    //   136: aload_0
    //   137: invokevirtual 32	java/io/IOException:printStackTrace	()V
    //   140: iconst_1
    //   141: ireturn
    //   142: aload_0
    //   143: ifnull +15 -> 158
    //   146: aload_0
    //   147: invokevirtual 29	java/io/RandomAccessFile:close	()V
    //   150: goto +8 -> 158
    //   153: astore_0
    //   154: aload_0
    //   155: invokevirtual 32	java/io/IOException:printStackTrace	()V
    //   158: aload_3
    //   159: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	160	0	paramFile	java.io.File
    //   34	2	1	i	int
    //   68	2	2	bool	boolean
    //   8	9	3	localObject1	Object
    //   93	12	3	localObject2	Object
    //   108	51	3	localObject3	Object
    //   4	11	4	localObject4	Object
    //   97	1	4	localException1	Exception
    //   102	5	4	localObject5	Object
    //   112	8	4	localException2	Exception
    //   1	114	5	localObject6	Object
    //   12	45	6	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   39	43	45	java/io/IOException
    //   73	77	79	java/io/IOException
    //   28	35	93	finally
    //   52	69	93	finally
    //   28	35	97	java/lang/Exception
    //   52	69	97	java/lang/Exception
    //   9	14	102	finally
    //   17	28	102	finally
    //   119	124	102	finally
    //   9	14	112	java/lang/Exception
    //   17	28	112	java/lang/Exception
    //   86	90	135	java/io/IOException
    //   128	132	135	java/io/IOException
    //   146	150	153	java/io/IOException
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\ApkUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */