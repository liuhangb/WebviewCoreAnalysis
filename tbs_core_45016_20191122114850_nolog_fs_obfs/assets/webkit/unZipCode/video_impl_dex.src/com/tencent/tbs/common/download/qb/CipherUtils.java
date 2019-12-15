package com.tencent.tbs.common.download.qb;

import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CipherUtils
{
  private static final int DECRYPT_BLOCK_MAX = 128;
  private static final int ENCRYPT_BLOCK_MAX = 117;
  private static final int KEY_SIZE = 1024;
  public static final String LOGTAG = "CipherUtils";
  
  private static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null) {
      try
      {
        paramCloseable.close();
        return;
      }
      catch (IOException paramCloseable)
      {
        paramCloseable.printStackTrace();
      }
    }
  }
  
  /* Error */
  private static void doFinalWithBatch(String paramString1, String paramString2, Cipher paramCipher, int paramInt)
    throws Throwable
  {
    // Byte code:
    //   0: new 38	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 41	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore 10
    //   10: new 43	java/lang/StringBuilder
    //   13: dup
    //   14: invokespecial 44	java/lang/StringBuilder:<init>	()V
    //   17: astore 5
    //   19: aload 5
    //   21: ldc 46
    //   23: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: pop
    //   27: aload 5
    //   29: aload_0
    //   30: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: pop
    //   34: aload 5
    //   36: ldc 52
    //   38: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: pop
    //   42: aload 5
    //   44: aload 10
    //   46: invokevirtual 56	java/io/File:length	()J
    //   49: invokevirtual 59	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: aload 5
    //   55: invokevirtual 63	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   58: pop
    //   59: new 38	java/io/File
    //   62: dup
    //   63: aload_1
    //   64: invokespecial 41	java/io/File:<init>	(Ljava/lang/String;)V
    //   67: astore 8
    //   69: new 65	java/io/FileOutputStream
    //   72: dup
    //   73: aload_1
    //   74: invokespecial 66	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   77: astore_0
    //   78: new 68	java/io/ByteArrayOutputStream
    //   81: dup
    //   82: invokespecial 69	java/io/ByteArrayOutputStream:<init>	()V
    //   85: astore 6
    //   87: new 68	java/io/ByteArrayOutputStream
    //   90: dup
    //   91: invokespecial 69	java/io/ByteArrayOutputStream:<init>	()V
    //   94: astore 9
    //   96: new 71	java/io/BufferedOutputStream
    //   99: dup
    //   100: aload_0
    //   101: invokespecial 74	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   104: astore 7
    //   106: aconst_null
    //   107: astore 5
    //   109: aconst_null
    //   110: astore_0
    //   111: new 76	java/io/BufferedInputStream
    //   114: dup
    //   115: new 78	java/io/FileInputStream
    //   118: dup
    //   119: aload 10
    //   121: invokespecial 81	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   124: invokespecial 84	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   127: astore_1
    //   128: iload_3
    //   129: newarray <illegal type>
    //   131: astore_0
    //   132: aload_1
    //   133: aload_0
    //   134: iconst_0
    //   135: iload_3
    //   136: invokevirtual 88	java/io/BufferedInputStream:read	([BII)I
    //   139: istore 4
    //   141: iconst_m1
    //   142: iload 4
    //   144: if_icmpeq +87 -> 231
    //   147: aload 6
    //   149: aload_0
    //   150: iconst_0
    //   151: iload 4
    //   153: invokevirtual 92	java/io/ByteArrayOutputStream:write	([BII)V
    //   156: aload_2
    //   157: aload_0
    //   158: iconst_0
    //   159: iload 4
    //   161: invokevirtual 98	javax/crypto/Cipher:doFinal	([BII)[B
    //   164: astore 5
    //   166: new 43	java/lang/StringBuilder
    //   169: dup
    //   170: invokespecial 44	java/lang/StringBuilder:<init>	()V
    //   173: astore 10
    //   175: aload 10
    //   177: ldc 100
    //   179: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   182: pop
    //   183: aload 10
    //   185: iload 4
    //   187: invokevirtual 103	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   190: pop
    //   191: aload 10
    //   193: ldc 105
    //   195: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: pop
    //   199: aload 10
    //   201: aload 5
    //   203: arraylength
    //   204: invokevirtual 103	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   207: pop
    //   208: aload 10
    //   210: invokevirtual 63	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   213: pop
    //   214: aload 7
    //   216: aload 5
    //   218: invokevirtual 108	java/io/BufferedOutputStream:write	([B)V
    //   221: aload 9
    //   223: aload 5
    //   225: invokevirtual 109	java/io/ByteArrayOutputStream:write	([B)V
    //   228: goto -96 -> 132
    //   231: aload 7
    //   233: invokevirtual 112	java/io/BufferedOutputStream:flush	()V
    //   236: aload 6
    //   238: invokevirtual 113	java/io/ByteArrayOutputStream:flush	()V
    //   241: new 115	java/lang/String
    //   244: dup
    //   245: aload 6
    //   247: invokevirtual 119	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   250: invokespecial 121	java/lang/String:<init>	([B)V
    //   253: astore_0
    //   254: new 43	java/lang/StringBuilder
    //   257: dup
    //   258: invokespecial 44	java/lang/StringBuilder:<init>	()V
    //   261: astore_2
    //   262: aload_2
    //   263: ldc 123
    //   265: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: pop
    //   269: aload_2
    //   270: aload_0
    //   271: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   274: pop
    //   275: aload_2
    //   276: invokevirtual 63	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   279: pop
    //   280: aload_1
    //   281: invokestatic 125	com/tencent/tbs/common/download/qb/CipherUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   284: aload 7
    //   286: invokestatic 125	com/tencent/tbs/common/download/qb/CipherUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   289: aload 6
    //   291: invokestatic 125	com/tencent/tbs/common/download/qb/CipherUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   294: new 43	java/lang/StringBuilder
    //   297: dup
    //   298: invokespecial 44	java/lang/StringBuilder:<init>	()V
    //   301: astore_0
    //   302: aload_0
    //   303: ldc 127
    //   305: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   308: pop
    //   309: aload_0
    //   310: aload 8
    //   312: invokevirtual 130	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   315: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: pop
    //   319: aload_0
    //   320: ldc 52
    //   322: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   325: pop
    //   326: aload_0
    //   327: aload 8
    //   329: invokevirtual 56	java/io/File:length	()J
    //   332: invokevirtual 59	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   335: pop
    //   336: aload_0
    //   337: invokevirtual 63	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   340: pop
    //   341: return
    //   342: astore_0
    //   343: goto +29 -> 372
    //   346: astore_2
    //   347: goto +15 -> 362
    //   350: astore_2
    //   351: aload_0
    //   352: astore_1
    //   353: aload_2
    //   354: astore_0
    //   355: goto +17 -> 372
    //   358: astore_2
    //   359: aload 5
    //   361: astore_1
    //   362: aload_1
    //   363: astore_0
    //   364: aload_2
    //   365: invokevirtual 32	java/io/IOException:printStackTrace	()V
    //   368: aload_1
    //   369: astore_0
    //   370: aload_2
    //   371: athrow
    //   372: aload 7
    //   374: invokevirtual 112	java/io/BufferedOutputStream:flush	()V
    //   377: aload 6
    //   379: invokevirtual 113	java/io/ByteArrayOutputStream:flush	()V
    //   382: new 115	java/lang/String
    //   385: dup
    //   386: aload 6
    //   388: invokevirtual 119	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   391: invokespecial 121	java/lang/String:<init>	([B)V
    //   394: astore_2
    //   395: new 43	java/lang/StringBuilder
    //   398: dup
    //   399: invokespecial 44	java/lang/StringBuilder:<init>	()V
    //   402: astore 5
    //   404: aload 5
    //   406: ldc 123
    //   408: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   411: pop
    //   412: aload 5
    //   414: aload_2
    //   415: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   418: pop
    //   419: aload 5
    //   421: invokevirtual 63	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   424: pop
    //   425: aload_1
    //   426: invokestatic 125	com/tencent/tbs/common/download/qb/CipherUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   429: aload 7
    //   431: invokestatic 125	com/tencent/tbs/common/download/qb/CipherUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   434: aload 6
    //   436: invokestatic 125	com/tencent/tbs/common/download/qb/CipherUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   439: aload_0
    //   440: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	441	0	paramString1	String
    //   0	441	1	paramString2	String
    //   0	441	2	paramCipher	Cipher
    //   0	441	3	paramInt	int
    //   139	47	4	i	int
    //   17	403	5	localObject1	Object
    //   85	350	6	localByteArrayOutputStream1	ByteArrayOutputStream
    //   104	326	7	localBufferedOutputStream	java.io.BufferedOutputStream
    //   67	261	8	localFile	java.io.File
    //   94	128	9	localByteArrayOutputStream2	ByteArrayOutputStream
    //   8	201	10	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   128	132	342	finally
    //   132	141	342	finally
    //   147	228	342	finally
    //   128	132	346	java/io/IOException
    //   132	141	346	java/io/IOException
    //   147	228	346	java/io/IOException
    //   111	128	350	finally
    //   364	368	350	finally
    //   370	372	350	finally
    //   111	128	358	java/io/IOException
  }
  
  public static byte[] doFinalWithBatch(byte[] paramArrayOfByte, Cipher paramCipher, int paramInt)
    throws BadPaddingException, IllegalBlockSizeException, IOException
  {
    int j = paramArrayOfByte.length;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("doFinalWithBatch len :");
    ((StringBuilder)localObject).append(j);
    ((StringBuilder)localObject).toString();
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i = 0;
    for (;;)
    {
      int k = j - i;
      if (k <= 0) {
        break;
      }
      StringBuilder localStringBuilder;
      if (k >= paramInt)
      {
        localObject = paramCipher.doFinal(paramArrayOfByte, 0, paramInt);
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("tmpsize:");
        localStringBuilder.append(localObject.length);
        localStringBuilder.toString();
      }
      else
      {
        localObject = paramCipher.doFinal(paramArrayOfByte, i, k);
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("tmpsize2:");
        localStringBuilder.append(localObject.length);
        localStringBuilder.toString();
      }
      localByteArrayOutputStream.write((byte[])localObject);
      i += paramInt;
    }
    localByteArrayOutputStream.close();
    return localByteArrayOutputStream.toByteArray();
  }
  
  public static void fileTransfer(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean)
    throws Throwable
  {
    Cipher localCipher = Cipher.getInstance("RSA");
    paramString5 = getPrivateKey(paramString5);
    localCipher.init(1, getPublicKey(paramString4));
    long l1 = System.currentTimeMillis();
    doFinalWithBatch(paramString1, paramString2, localCipher, 117);
    long l2 = System.currentTimeMillis();
    paramString1 = new StringBuilder();
    paramString1.append("ENCRYPT timeused:");
    paramString1.append(l2 - l1);
    paramString1.toString();
    l1 = System.currentTimeMillis();
    localCipher.init(2, paramString5);
    doFinalWithBatch(paramString2, paramString3, localCipher, 256);
    l2 = System.currentTimeMillis();
    paramString1 = new StringBuilder();
    paramString1.append("DECRYPT timeused:");
    paramString1.append(l2 - l1);
    paramString1.toString();
  }
  
  public static void fileTransfer(String paramString1, String paramString2, byte[] paramArrayOfByte, boolean paramBoolean)
    throws Throwable
  {
    int i;
    if (paramBoolean)
    {
      i = 1;
      paramArrayOfByte = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(paramArrayOfByte));
    }
    else
    {
      paramArrayOfByte = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(paramArrayOfByte));
      i = 2;
    }
    Cipher localCipher = Cipher.getInstance("RSA");
    localCipher.init(i, paramArrayOfByte);
    doFinalWithBatch(paramString1, paramString2, localCipher, 117);
  }
  
  public static PrivateKey getPrivateKey(String paramString)
    throws Exception
  {
    paramString = new PKCS8EncodedKeySpec(Base64.decode(paramString, 0));
    return KeyFactory.getInstance("RSA").generatePrivate(paramString);
  }
  
  public static PublicKey getPublicKey(String paramString)
    throws Exception
  {
    paramString = new X509EncodedKeySpec(Base64.decode(paramString, 0));
    return KeyFactory.getInstance("RSA").generatePublic(paramString);
  }
  
  public static void test(String paramString)
    throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("test:");
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).toString();
    localObject1 = Cipher.getInstance("RSA");
    Object localObject2 = KeyPairGenerator.getInstance("RSA").generateKeyPair();
    Object localObject3 = ((KeyPair)localObject2).getPublic();
    localObject2 = ((KeyPair)localObject2).getPrivate();
    ((Cipher)localObject1).init(1, (Key)localObject3);
    localObject3 = doFinalWithBatch(paramString.getBytes(), (Cipher)localObject1, 100);
    paramString = ((Cipher)localObject1).doFinal(paramString.getBytes());
    Object localObject4 = new String((byte[])localObject3);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("coded:");
    localStringBuilder.append((String)localObject4);
    localStringBuilder.append(",");
    localStringBuilder.append(localObject3.length);
    localStringBuilder.toString();
    localObject4 = new StringBuilder();
    ((StringBuilder)localObject4).append("coded2:");
    ((StringBuilder)localObject4).append(paramString.length);
    ((StringBuilder)localObject4).append(",");
    ((StringBuilder)localObject4).append(new String(paramString));
    ((StringBuilder)localObject4).toString();
    ((Cipher)localObject1).init(2, (Key)localObject2);
    localObject2 = doFinalWithBatch((byte[])localObject3, (Cipher)localObject1, 100);
    paramString = ((Cipher)localObject1).doFinal((byte[])localObject3);
    localObject1 = new String((byte[])localObject2);
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("deResult:");
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).toString();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("deResult2:");
    ((StringBuilder)localObject1).append(paramString.length);
    ((StringBuilder)localObject1).append(",");
    ((StringBuilder)localObject1).append(new String(paramString));
    ((StringBuilder)localObject1).toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\CipherUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */