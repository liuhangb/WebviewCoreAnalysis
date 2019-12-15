package com.tencent.turingfd.sdk.tbs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Proxy;
import android.util.SparseArray;
import java.io.Closeable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class l
{
  public static byte a(Context paramContext)
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
      {
        if ((localNetworkInfo.getState() != NetworkInfo.State.CONNECTING) && (localNetworkInfo.getState() != NetworkInfo.State.CONNECTED)) {
          return -1;
        }
        if (localNetworkInfo.getType() == 1) {
          return 0;
        }
        if (localNetworkInfo.getType() == 0)
        {
          if (Proxy.getDefaultHost() == null)
          {
            paramContext = Proxy.getHost(paramContext);
            if (paramContext == null) {
              return 1;
            }
          }
          return 2;
        }
      }
      return -1;
    }
    catch (Exception paramContext) {}
    return -1;
  }
  
  public static int a(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    int i;
    if (paramBoolean) {
      i = 1;
    } else {
      i = 0;
    }
    return paramInt1 | i << paramInt2;
  }
  
  public static int a(SparseArray<Object> paramSparseArray)
  {
    paramSparseArray = (Integer)a(paramSparseArray, 0, Integer.class);
    if (paramSparseArray == null) {
      return -1;
    }
    return paramSparseArray.intValue();
  }
  
  public static SparseArray<Object> a()
  {
    return new SparseArray();
  }
  
  public static <E> E a(SparseArray<Object> paramSparseArray, int paramInt, Class<E> paramClass)
  {
    Object localObject1 = null;
    if (paramSparseArray == null) {
      return null;
    }
    Object localObject2 = paramSparseArray.get(paramInt);
    paramSparseArray = (SparseArray<Object>)localObject1;
    if (paramClass.isInstance(localObject2)) {
      paramSparseArray = paramClass.cast(localObject2);
    }
    return paramSparseArray;
  }
  
  public static String a(Context paramContext, String paramString1, String paramString2)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getPackageInfo(paramString1, 64).signatures;
    }
    catch (Throwable paramContext)
    {
      int k;
      int j;
      int i;
      for (;;) {}
    }
    paramContext = null;
    if (paramContext != null)
    {
      k = paramContext.length;
      j = 0;
      i = 0;
      while (i < k)
      {
        paramString1 = paramContext[i];
        if ("SHA1".equals(paramString2))
        {
          paramContext = paramString1.toByteArray();
          try
          {
            paramString1 = MessageDigest.getInstance("SHA1");
            if (paramString1 == null) {
              return null;
            }
            paramContext = paramString1.digest(paramContext);
            paramString1 = new StringBuilder();
            k = paramContext.length;
            i = j;
            while (i < k)
            {
              paramString1.append(Integer.toHexString(paramContext[i] & 0xFF | 0x100).substring(1, 3));
              i += 1;
            }
            paramContext = paramString1.toString();
            return paramContext;
          }
          catch (NoSuchAlgorithmException paramContext)
          {
            paramContext.printStackTrace();
            return null;
          }
        }
        i += 1;
      }
    }
    return null;
  }
  
  public static String a(byte[] paramArrayOfByte)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance(new String(b("4D4435")));
      localMessageDigest.update(paramArrayOfByte);
      paramArrayOfByte = localMessageDigest.digest();
    }
    catch (NoSuchAlgorithmException paramArrayOfByte)
    {
      for (;;) {}
    }
    paramArrayOfByte = null;
    return b(paramArrayOfByte);
  }
  
  /* Error */
  public static java.util.List<String> a(java.io.File paramFile)
    throws java.io.IOException
  {
    // Byte code:
    //   0: new 174	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 175	java/util/ArrayList:<init>	()V
    //   7: astore_3
    //   8: new 177	java/util/jar/JarFile
    //   11: dup
    //   12: aload_0
    //   13: invokespecial 180	java/util/jar/JarFile:<init>	(Ljava/io/File;)V
    //   16: astore_0
    //   17: aload_0
    //   18: aload_0
    //   19: ldc -74
    //   21: invokevirtual 186	java/util/jar/JarFile:getJarEntry	(Ljava/lang/String;)Ljava/util/jar/JarEntry;
    //   24: sipush 8192
    //   27: newarray <illegal type>
    //   29: invokestatic 189	com/tencent/turingfd/sdk/tbs/l:a	(Ljava/util/jar/JarFile;Ljava/util/jar/JarEntry;[B)[Ljava/security/cert/Certificate;
    //   32: astore 4
    //   34: aload 4
    //   36: ifnull +45 -> 81
    //   39: aload 4
    //   41: arraylength
    //   42: istore_2
    //   43: iconst_0
    //   44: istore_1
    //   45: iload_1
    //   46: iload_2
    //   47: if_icmpge +34 -> 81
    //   50: aload_3
    //   51: aload 4
    //   53: iload_1
    //   54: aaload
    //   55: invokevirtual 194	java/security/cert/Certificate:getEncoded	()[B
    //   58: invokestatic 196	com/tencent/turingfd/sdk/tbs/l:a	([B)Ljava/lang/String;
    //   61: invokeinterface 201 2 0
    //   66: pop
    //   67: iload_1
    //   68: iconst_1
    //   69: iadd
    //   70: istore_1
    //   71: goto -26 -> 45
    //   74: astore_3
    //   75: aload_0
    //   76: invokevirtual 204	java/util/jar/JarFile:close	()V
    //   79: aload_3
    //   80: athrow
    //   81: aload_0
    //   82: invokevirtual 204	java/util/jar/JarFile:close	()V
    //   85: aload_3
    //   86: areturn
    //   87: astore 4
    //   89: goto -8 -> 81
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	92	0	paramFile	java.io.File
    //   44	27	1	i	int
    //   42	6	2	j	int
    //   7	44	3	localArrayList	java.util.ArrayList
    //   74	12	3	localList	java.util.List<String>
    //   32	20	4	arrayOfCertificate	java.security.cert.Certificate[]
    //   87	1	4	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   17	34	74	finally
    //   39	43	74	finally
    //   50	67	74	finally
    //   17	34	87	java/lang/Exception
    //   39	43	87	java/lang/Exception
    //   50	67	87	java/lang/Exception
  }
  
  public static void a(Context paramContext, String paramString, int paramInt)
  {
    try
    {
      paramContext = paramContext.getSharedPreferences("turingfd_protect_105578_28_tbsMini", 0).edit();
      paramContext.putInt(paramString, paramInt);
      paramContext.commit();
      return;
    }
    catch (Throwable paramContext) {}
  }
  
  public static void a(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      paramCloseable.close();
      return;
    }
    catch (Throwable paramCloseable) {}
  }
  
  public static void a(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    int k = paramArrayOfByte.length;
    int i = 0;
    int m;
    for (int j = 0; i < k >> 2; j = m + 1)
    {
      m = j + 1;
      paramArrayOfByte[j] &= 0xFF;
      int n = paramArrayOfInt[i];
      j = m + 1;
      paramArrayOfInt[i] = (n | (paramArrayOfByte[m] & 0xFF) << 8);
      n = paramArrayOfInt[i];
      m = j + 1;
      paramArrayOfInt[i] = (n | (paramArrayOfByte[j] & 0xFF) << 16);
      paramArrayOfInt[i] |= (paramArrayOfByte[m] & 0xFF) << 24;
      i += 1;
    }
    if (j < paramArrayOfByte.length)
    {
      k = j + 1;
      paramArrayOfByte[j] &= 0xFF;
      j = 8;
      while (k < paramArrayOfByte.length)
      {
        paramArrayOfInt[i] |= (paramArrayOfByte[k] & 0xFF) << j;
        k += 1;
        j += 8;
      }
    }
  }
  
  public static void a(int[] paramArrayOfInt, int paramInt, byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length >> 2;
    int j = i;
    if (i > paramInt) {
      j = paramInt;
    }
    i = 0;
    int k = 0;
    int m;
    for (;;)
    {
      m = 8;
      if (i >= j) {
        break;
      }
      m = k + 1;
      paramArrayOfByte[k] = ((byte)(paramArrayOfInt[i] & 0xFF));
      k = m + 1;
      paramArrayOfByte[m] = ((byte)(paramArrayOfInt[i] >>> 8 & 0xFF));
      m = k + 1;
      paramArrayOfByte[k] = ((byte)(paramArrayOfInt[i] >>> 16 & 0xFF));
      k = m + 1;
      paramArrayOfByte[m] = ((byte)(paramArrayOfInt[i] >>> 24 & 0xFF));
      i += 1;
    }
    if ((paramInt > j) && (k < paramArrayOfByte.length))
    {
      paramInt = k + 1;
      paramArrayOfByte[k] = ((byte)(paramArrayOfInt[i] & 0xFF));
      j = m;
      while ((j <= 24) && (paramInt < paramArrayOfByte.length))
      {
        paramArrayOfByte[paramInt] = ((byte)(paramArrayOfInt[i] >>> j & 0xFF));
        j += 8;
        paramInt += 1;
      }
    }
  }
  
  public static byte[] a()
  {
    try
    {
      Object localObject = new StringBuffer();
      int i = 0;
      while (i < 21)
      {
        ((StringBuffer)localObject).append((char)("http://pmir.3g.qq.com".charAt(i) + new int[] { -36, -46, -45, -77, -22, -10, 47, -77, -72, -69, -32, 25, 21, -21, -6, -75, -71, 31, -39, -49, -49 }[i]));
        i += 1;
      }
      localObject = ((StringBuffer)localObject).toString();
      localObject = ((String)localObject).getBytes("UTF-8");
      return (byte[])localObject;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
    return null;
  }
  
  /* Error */
  public static byte[] a(String paramString)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 258	java/io/FileInputStream
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 261	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   10: astore_3
    //   11: new 263	java/io/ByteArrayOutputStream
    //   14: dup
    //   15: aload_3
    //   16: invokevirtual 266	java/io/FileInputStream:available	()I
    //   19: invokespecial 269	java/io/ByteArrayOutputStream:<init>	(I)V
    //   22: astore 4
    //   24: sipush 4096
    //   27: newarray <illegal type>
    //   29: astore_0
    //   30: aload_3
    //   31: aload_0
    //   32: invokevirtual 275	java/io/InputStream:read	([B)I
    //   35: istore_1
    //   36: iconst_m1
    //   37: iload_1
    //   38: if_icmpeq +14 -> 52
    //   41: aload 4
    //   43: aload_0
    //   44: iconst_0
    //   45: iload_1
    //   46: invokevirtual 281	java/io/OutputStream:write	([BII)V
    //   49: goto -19 -> 30
    //   52: aload 4
    //   54: invokevirtual 282	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   57: astore_0
    //   58: aload_0
    //   59: ifnull +6 -> 65
    //   62: goto +10 -> 72
    //   65: ldc_w 284
    //   68: invokevirtual 286	java/lang/String:getBytes	()[B
    //   71: astore_0
    //   72: aload_3
    //   73: invokestatic 288	com/tencent/turingfd/sdk/tbs/l:a	(Ljava/io/Closeable;)V
    //   76: aload 4
    //   78: invokestatic 288	com/tencent/turingfd/sdk/tbs/l:a	(Ljava/io/Closeable;)V
    //   81: aload_0
    //   82: areturn
    //   83: astore_0
    //   84: aload 4
    //   86: astore_2
    //   87: goto +10 -> 97
    //   90: astore_0
    //   91: goto +6 -> 97
    //   94: astore_0
    //   95: aconst_null
    //   96: astore_3
    //   97: aload_3
    //   98: invokestatic 288	com/tencent/turingfd/sdk/tbs/l:a	(Ljava/io/Closeable;)V
    //   101: aload_2
    //   102: invokestatic 288	com/tencent/turingfd/sdk/tbs/l:a	(Ljava/io/Closeable;)V
    //   105: aload_0
    //   106: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	107	0	paramString	String
    //   35	11	1	i	int
    //   1	101	2	localObject	Object
    //   10	88	3	localFileInputStream	java.io.FileInputStream
    //   22	63	4	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    // Exception table:
    //   from	to	target	type
    //   24	30	83	finally
    //   30	36	83	finally
    //   41	49	83	finally
    //   52	58	83	finally
    //   65	72	83	finally
    //   11	24	90	finally
    //   2	11	94	finally
  }
  
  public static byte[] a(byte[] paramArrayOfByte)
  {
    Object localObject = paramArrayOfByte;
    if (paramArrayOfByte != null)
    {
      localObject = paramArrayOfByte;
      if (paramArrayOfByte.length <= 16) {}
    }
    try
    {
      localObject = MessageDigest.getInstance("MD5");
      ((MessageDigest)localObject).update(paramArrayOfByte);
      paramArrayOfByte = ((MessageDigest)localObject).digest();
      return paramArrayOfByte;
    }
    catch (NoSuchAlgorithmException paramArrayOfByte)
    {
      for (;;) {}
    }
    localObject = null;
    return (byte[])localObject;
  }
  
  public static byte[] a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    byte[] arrayOfByte = a(paramArrayOfByte2);
    paramArrayOfByte2 = paramArrayOfByte1;
    if (paramArrayOfByte1 != null)
    {
      paramArrayOfByte2 = paramArrayOfByte1;
      if (arrayOfByte != null)
      {
        if (paramArrayOfByte1.length == 0) {
          return paramArrayOfByte1;
        }
        if (paramArrayOfByte1.length % 4 == 0) {
          i = (paramArrayOfByte1.length >>> 2) + 1;
        } else {
          i = (paramArrayOfByte1.length >>> 2) + 2;
        }
        int[] arrayOfInt = new int[i];
        a(paramArrayOfByte1, arrayOfInt);
        arrayOfInt[(i - 1)] = paramArrayOfByte1.length;
        if (arrayOfByte.length % 4 == 0) {
          i = arrayOfByte.length >>> 2;
        } else {
          i = (arrayOfByte.length >>> 2) + 1;
        }
        int j = i;
        if (i < 4) {
          j = 4;
        }
        paramArrayOfByte1 = new int[j];
        int i = 0;
        while (i < j)
        {
          paramArrayOfByte1[i] = 0;
          i += 1;
        }
        a(arrayOfByte, paramArrayOfByte1);
        int i1 = arrayOfInt.length - 1;
        i = arrayOfInt[i1];
        j = arrayOfInt[0];
        j = 52 / (i1 + 1) + 6;
        int m;
        for (int k = 0; j > 0; k = m)
        {
          m = k - 1640531527;
          int i2 = m >>> 2 & 0x3;
          int n = 0;
          k = i;
          for (i = n; i < i1; i = n)
          {
            n = i + 1;
            i3 = arrayOfInt[n];
            int i4 = arrayOfInt[i];
            k = ((k >>> 5 ^ i3 << 2) + (i3 >>> 3 ^ k << 4) ^ (i3 ^ m) + (k ^ paramArrayOfByte1[(i & 0x3 ^ i2)])) + i4;
            arrayOfInt[i] = k;
          }
          n = arrayOfInt[0];
          int i3 = arrayOfInt[i1];
          i = ((k >>> 5 ^ n << 2) + (n >>> 3 ^ k << 4) ^ (n ^ m) + (paramArrayOfByte1[(i & 0x3 ^ i2)] ^ k)) + i3;
          arrayOfInt[i1] = i;
          j -= 1;
        }
        paramArrayOfByte2 = new byte[arrayOfInt.length << 2];
        a(arrayOfInt, arrayOfInt.length, paramArrayOfByte2);
      }
    }
    return paramArrayOfByte2;
  }
  
  /* Error */
  public static java.security.cert.Certificate[] a(java.util.jar.JarFile paramJarFile, java.util.jar.JarEntry paramJarEntry, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aload_0
    //   3: aload_1
    //   4: invokevirtual 302	java/util/jar/JarFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   7: astore_0
    //   8: aload_0
    //   9: aload_2
    //   10: iconst_0
    //   11: aload_2
    //   12: arraylength
    //   13: invokevirtual 305	java/io/InputStream:read	([BII)I
    //   16: iconst_m1
    //   17: if_icmpeq +6 -> 23
    //   20: goto -12 -> 8
    //   23: aload_3
    //   24: astore_2
    //   25: aload_1
    //   26: ifnull +8 -> 34
    //   29: aload_1
    //   30: invokevirtual 311	java/util/jar/JarEntry:getCertificates	()[Ljava/security/cert/Certificate;
    //   33: astore_2
    //   34: aload_0
    //   35: invokevirtual 312	java/io/InputStream:close	()V
    //   38: aload_2
    //   39: areturn
    //   40: astore_2
    //   41: aload_0
    //   42: astore_1
    //   43: aload_2
    //   44: astore_0
    //   45: goto +6 -> 51
    //   48: astore_0
    //   49: aconst_null
    //   50: astore_1
    //   51: aload_1
    //   52: ifnull +7 -> 59
    //   55: aload_1
    //   56: invokevirtual 312	java/io/InputStream:close	()V
    //   59: aload_0
    //   60: athrow
    //   61: aconst_null
    //   62: astore_0
    //   63: aload_0
    //   64: ifnull +7 -> 71
    //   67: aload_0
    //   68: invokevirtual 312	java/io/InputStream:close	()V
    //   71: aconst_null
    //   72: areturn
    //   73: astore_0
    //   74: goto -13 -> 61
    //   77: astore_1
    //   78: goto -15 -> 63
    //   81: astore_0
    //   82: aload_2
    //   83: areturn
    //   84: astore_1
    //   85: goto -26 -> 59
    //   88: astore_0
    //   89: aconst_null
    //   90: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	91	0	paramJarFile	java.util.jar.JarFile
    //   0	91	1	paramJarEntry	java.util.jar.JarEntry
    //   0	91	2	paramArrayOfByte	byte[]
    //   1	23	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   8	20	40	finally
    //   29	34	40	finally
    //   2	8	48	finally
    //   2	8	73	java/io/IOException
    //   8	20	77	java/io/IOException
    //   29	34	77	java/io/IOException
    //   34	38	81	java/io/IOException
    //   55	59	84	java/io/IOException
    //   67	71	88	java/io/IOException
  }
  
  public static String b(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length);
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      String str = Integer.toHexString(paramArrayOfByte[i] & 0xFF);
      if (str.length() < 2) {
        localStringBuffer.append(0);
      }
      localStringBuffer.append(str.toUpperCase(Locale.getDefault()));
      i += 1;
    }
    return localStringBuffer.toString();
  }
  
  public static byte[] b(String paramString)
  {
    int j = paramString.length() / 2;
    byte[] arrayOfByte = new byte[j];
    int i = 0;
    while (i < j)
    {
      int k = i * 2;
      arrayOfByte[i] = Integer.valueOf(paramString.substring(k, k + 2), 16).byteValue();
      i += 1;
    }
    return arrayOfByte;
  }
  
  public static byte[] b(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    byte[] arrayOfByte = a(paramArrayOfByte2);
    if ((paramArrayOfByte1 != null) && (arrayOfByte != null))
    {
      if (paramArrayOfByte1.length == 0) {
        return paramArrayOfByte1;
      }
      if (paramArrayOfByte1.length % 4 == 0)
      {
        if (paramArrayOfByte1.length < 8) {
          return null;
        }
        paramArrayOfByte2 = new int[paramArrayOfByte1.length >>> 2];
        a(paramArrayOfByte1, paramArrayOfByte2);
        if (arrayOfByte.length % 4 == 0) {
          i = arrayOfByte.length >>> 2;
        } else {
          i = (arrayOfByte.length >>> 2) + 1;
        }
        int j = i;
        if (i < 4) {
          j = 4;
        }
        paramArrayOfByte1 = new int[j];
        int i = 0;
        while (i < j)
        {
          paramArrayOfByte1[i] = 0;
          i += 1;
        }
        a(arrayOfByte, paramArrayOfByte1);
        int m = paramArrayOfByte2.length - 1;
        i = paramArrayOfByte2[m];
        i = paramArrayOfByte2[0];
        j = (52 / (m + 1) + 6) * -1640531527;
        while (j != 0)
        {
          int n = j >>> 2 & 0x3;
          int k = i;
          i = m;
          while (i > 0)
          {
            i1 = paramArrayOfByte2[(i - 1)];
            k = paramArrayOfByte2[i] - ((k ^ j) + (i1 ^ paramArrayOfByte1[(i & 0x3 ^ n)]) ^ (i1 >>> 5 ^ k << 2) + (k >>> 3 ^ i1 << 4));
            paramArrayOfByte2[i] = k;
            i -= 1;
          }
          int i1 = paramArrayOfByte2[m];
          i = paramArrayOfByte2[0] - ((i1 >>> 5 ^ k << 2) + (k >>> 3 ^ i1 << 4) ^ (k ^ j) + (paramArrayOfByte1[(i & 0x3 ^ n)] ^ i1));
          paramArrayOfByte2[0] = i;
          j += 1640531527;
        }
        i = paramArrayOfByte2[(paramArrayOfByte2.length - 1)];
        if (i >= 0)
        {
          if (i > paramArrayOfByte2.length - 1 << 2) {
            return null;
          }
          paramArrayOfByte1 = new byte[i];
          a(paramArrayOfByte2, paramArrayOfByte2.length - 1, paramArrayOfByte1);
          return paramArrayOfByte1;
        }
      }
      return null;
    }
    return paramArrayOfByte1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */