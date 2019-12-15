package com.tencent.smtt.downloader.utils;

import android.util.Log;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class d
{
  private static OutputStream a;
  
  public static String a()
  {
    return String.valueOf(System.currentTimeMillis());
  }
  
  /* Error */
  public static void a(java.io.File paramFile, String paramString1, byte[] paramArrayOfByte, String paramString2, boolean paramBoolean)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: aload_1
    //   4: aload_3
    //   5: invokestatic 28	com/tencent/smtt/downloader/utils/d:a	(Ljava/lang/String;Ljava/lang/String;)[B
    //   8: astore_1
    //   9: aload_1
    //   10: ifnull +8 -> 18
    //   13: aconst_null
    //   14: astore_3
    //   15: goto +5 -> 20
    //   18: aconst_null
    //   19: astore_1
    //   20: aload_0
    //   21: invokevirtual 34	java/io/File:getParentFile	()Ljava/io/File;
    //   24: invokevirtual 38	java/io/File:mkdirs	()Z
    //   27: pop
    //   28: aload_0
    //   29: invokevirtual 41	java/io/File:isFile	()Z
    //   32: ifeq +31 -> 63
    //   35: aload_0
    //   36: invokevirtual 44	java/io/File:exists	()Z
    //   39: ifeq +24 -> 63
    //   42: aload_0
    //   43: invokevirtual 47	java/io/File:length	()J
    //   46: ldc2_w 48
    //   49: lcmp
    //   50: ifle +13 -> 63
    //   53: aload_0
    //   54: invokevirtual 52	java/io/File:delete	()Z
    //   57: pop
    //   58: aload_0
    //   59: invokevirtual 55	java/io/File:createNewFile	()Z
    //   62: pop
    //   63: getstatic 57	com/tencent/smtt/downloader/utils/d:a	Ljava/io/OutputStream;
    //   66: ifnonnull +23 -> 89
    //   69: new 59	java/io/BufferedOutputStream
    //   72: dup
    //   73: new 61	java/io/FileOutputStream
    //   76: dup
    //   77: aload_0
    //   78: iload 4
    //   80: invokespecial 65	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
    //   83: invokespecial 68	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   86: putstatic 57	com/tencent/smtt/downloader/utils/d:a	Ljava/io/OutputStream;
    //   89: aload_3
    //   90: ifnull +16 -> 106
    //   93: getstatic 57	com/tencent/smtt/downloader/utils/d:a	Ljava/io/OutputStream;
    //   96: aload_3
    //   97: invokevirtual 72	java/lang/String:getBytes	()[B
    //   100: invokevirtual 78	java/io/OutputStream:write	([B)V
    //   103: goto +36 -> 139
    //   106: getstatic 57	com/tencent/smtt/downloader/utils/d:a	Ljava/io/OutputStream;
    //   109: aload_2
    //   110: invokevirtual 78	java/io/OutputStream:write	([B)V
    //   113: getstatic 57	com/tencent/smtt/downloader/utils/d:a	Ljava/io/OutputStream;
    //   116: aload_1
    //   117: invokevirtual 78	java/io/OutputStream:write	([B)V
    //   120: getstatic 57	com/tencent/smtt/downloader/utils/d:a	Ljava/io/OutputStream;
    //   123: iconst_2
    //   124: newarray <illegal type>
    //   126: dup
    //   127: iconst_0
    //   128: bipush 10
    //   130: bastore
    //   131: dup
    //   132: iconst_1
    //   133: bipush 10
    //   135: bastore
    //   136: invokevirtual 78	java/io/OutputStream:write	([B)V
    //   139: getstatic 57	com/tencent/smtt/downloader/utils/d:a	Ljava/io/OutputStream;
    //   142: astore_0
    //   143: aload_0
    //   144: ifnull +180 -> 324
    //   147: getstatic 57	com/tencent/smtt/downloader/utils/d:a	Ljava/io/OutputStream;
    //   150: invokevirtual 81	java/io/OutputStream:flush	()V
    //   153: goto +171 -> 324
    //   156: astore_1
    //   157: new 83	java/lang/StringBuilder
    //   160: dup
    //   161: invokespecial 85	java/lang/StringBuilder:<init>	()V
    //   164: astore_0
    //   165: aload_0
    //   166: ldc 87
    //   168: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   171: pop
    //   172: aload_0
    //   173: aload_1
    //   174: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   177: pop
    //   178: aload_0
    //   179: invokevirtual 97	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   182: pop
    //   183: goto +141 -> 324
    //   186: astore_0
    //   187: goto +141 -> 328
    //   190: astore_0
    //   191: new 83	java/lang/StringBuilder
    //   194: dup
    //   195: invokespecial 85	java/lang/StringBuilder:<init>	()V
    //   198: astore 5
    //   200: aload 5
    //   202: ldc 99
    //   204: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   207: pop
    //   208: aload 5
    //   210: aload_3
    //   211: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: pop
    //   215: aload 5
    //   217: ldc 101
    //   219: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   222: pop
    //   223: aload 5
    //   225: aload_2
    //   226: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   229: pop
    //   230: aload 5
    //   232: ldc 103
    //   234: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   237: pop
    //   238: aload 5
    //   240: aload_1
    //   241: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   244: pop
    //   245: aload 5
    //   247: ldc 105
    //   249: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   252: pop
    //   253: aload 5
    //   255: iload 4
    //   257: invokevirtual 108	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   260: pop
    //   261: aload 5
    //   263: ldc 110
    //   265: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: pop
    //   269: aload 5
    //   271: aload_0
    //   272: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   275: pop
    //   276: aload 5
    //   278: invokevirtual 97	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   281: pop
    //   282: getstatic 57	com/tencent/smtt/downloader/utils/d:a	Ljava/io/OutputStream;
    //   285: astore_0
    //   286: aload_0
    //   287: ifnull +37 -> 324
    //   290: getstatic 57	com/tencent/smtt/downloader/utils/d:a	Ljava/io/OutputStream;
    //   293: invokevirtual 81	java/io/OutputStream:flush	()V
    //   296: goto +28 -> 324
    //   299: astore_1
    //   300: new 83	java/lang/StringBuilder
    //   303: dup
    //   304: invokespecial 85	java/lang/StringBuilder:<init>	()V
    //   307: astore_0
    //   308: aload_0
    //   309: ldc 87
    //   311: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   314: pop
    //   315: aload_0
    //   316: aload_1
    //   317: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   320: pop
    //   321: goto -143 -> 178
    //   324: ldc 2
    //   326: monitorexit
    //   327: return
    //   328: getstatic 57	com/tencent/smtt/downloader/utils/d:a	Ljava/io/OutputStream;
    //   331: astore_1
    //   332: aload_1
    //   333: ifnull +39 -> 372
    //   336: getstatic 57	com/tencent/smtt/downloader/utils/d:a	Ljava/io/OutputStream;
    //   339: invokevirtual 81	java/io/OutputStream:flush	()V
    //   342: goto +30 -> 372
    //   345: astore_1
    //   346: new 83	java/lang/StringBuilder
    //   349: dup
    //   350: invokespecial 85	java/lang/StringBuilder:<init>	()V
    //   353: astore_2
    //   354: aload_2
    //   355: ldc 87
    //   357: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   360: pop
    //   361: aload_2
    //   362: aload_1
    //   363: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   366: pop
    //   367: aload_2
    //   368: invokevirtual 97	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   371: pop
    //   372: aload_0
    //   373: athrow
    //   374: astore_0
    //   375: ldc 2
    //   377: monitorexit
    //   378: aload_0
    //   379: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	380	0	paramFile	java.io.File
    //   0	380	1	paramString1	String
    //   0	380	2	paramArrayOfByte	byte[]
    //   0	380	3	paramString2	String
    //   0	380	4	paramBoolean	boolean
    //   198	79	5	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   147	153	156	java/lang/Throwable
    //   20	63	186	finally
    //   63	89	186	finally
    //   93	103	186	finally
    //   106	139	186	finally
    //   191	282	186	finally
    //   20	63	190	java/lang/Throwable
    //   63	89	190	java/lang/Throwable
    //   93	103	190	java/lang/Throwable
    //   106	139	190	java/lang/Throwable
    //   290	296	299	java/lang/Throwable
    //   336	342	345	java/lang/Throwable
    //   3	9	374	finally
    //   139	143	374	finally
    //   147	153	374	finally
    //   157	178	374	finally
    //   178	183	374	finally
    //   282	286	374	finally
    //   290	296	374	finally
    //   300	321	374	finally
    //   328	332	374	finally
    //   336	342	374	finally
    //   346	372	374	finally
    //   372	374	374	finally
  }
  
  public static byte[] a(String paramString1, String paramString2)
  {
    try
    {
      paramString2 = paramString2.getBytes("UTF-8");
      Cipher localCipher = Cipher.getInstance("RC4");
      localCipher.init(1, new SecretKeySpec(paramString1.getBytes("UTF-8"), "RC4"));
      paramString1 = localCipher.update(paramString2);
      return paramString1;
    }
    catch (Throwable paramString1)
    {
      paramString2 = new StringBuilder();
      paramString2.append("encrypt exception:");
      paramString2.append(paramString1.getMessage());
      Log.e("LOG_FILE", paramString2.toString());
    }
    return null;
  }
  
  public static byte[] b(String paramString1, String paramString2)
  {
    try
    {
      paramString2 = paramString2.getBytes("UTF-8");
      Cipher localCipher = Cipher.getInstance("RC4");
      localCipher.init(1, new SecretKeySpec(paramString1.getBytes("UTF-8"), "RC4"));
      paramString1 = localCipher.update(paramString2);
      return paramString1;
    }
    catch (Throwable paramString1)
    {
      paramString2 = new StringBuilder();
      paramString2.append("encrypt exception:");
      paramString2.append(paramString1.getMessage());
      Log.e("LOG_FILE", paramString2.toString());
    }
    return null;
  }
  
  public static byte[] c(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = b(paramString1, paramString2);
      paramString2 = String.format("%03d", new Object[] { Integer.valueOf(paramString1.length) });
      byte[] arrayOfByte = new byte[paramString1.length + 3];
      arrayOfByte[0] = ((byte)paramString2.charAt(0));
      arrayOfByte[1] = ((byte)paramString2.charAt(1));
      arrayOfByte[2] = ((byte)paramString2.charAt(2));
      System.arraycopy(paramString1, 0, arrayOfByte, 3, paramString1.length);
      return arrayOfByte;
    }
    catch (Exception paramString1)
    {
      for (;;) {}
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\utils\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */