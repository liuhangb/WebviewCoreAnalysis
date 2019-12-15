package com.tencent.tbs.common.download.qb;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class AESHelper
{
  private static final String TAG = "AESHelper";
  public static final String VIPARA = "0102030405060708";
  
  public static boolean decryptFile(String paramString1, String paramString2, String paramString3)
  {
    Object localObject = TAG;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("decryptFile:");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", ");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(" to ");
    ((StringBuilder)localObject).append(paramString3);
    ((StringBuilder)localObject).toString();
    return transferFile(paramString1, paramString2, paramString3, false);
  }
  
  public static boolean encryptFile(String paramString1, String paramString2, String paramString3)
  {
    Object localObject = TAG;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("encryptFile:");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", ");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(" to ");
    ((StringBuilder)localObject).append(paramString3);
    ((StringBuilder)localObject).toString();
    return transferFile(paramString1, paramString2, paramString3, true);
  }
  
  private static Cipher initAESCipher(String paramString, int paramInt)
  {
    Object localObject6 = null;
    Object localObject7 = null;
    Object localObject8 = null;
    Object localObject5 = null;
    Object localObject1 = localObject5;
    Object localObject2 = localObject6;
    Object localObject3 = localObject7;
    Object localObject4 = localObject8;
    try
    {
      IvParameterSpec localIvParameterSpec = new IvParameterSpec("0102030405060708".getBytes());
      localObject1 = localObject5;
      localObject2 = localObject6;
      localObject3 = localObject7;
      localObject4 = localObject8;
      SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramString.getBytes(), "AES");
      localObject1 = localObject5;
      localObject2 = localObject6;
      localObject3 = localObject7;
      localObject4 = localObject8;
      paramString = Cipher.getInstance("AES/CBC/PKCS5Padding");
      localObject1 = paramString;
      localObject2 = paramString;
      localObject3 = paramString;
      localObject4 = paramString;
      paramString.init(paramInt, localSecretKeySpec, localIvParameterSpec);
      return paramString;
    }
    catch (InvalidAlgorithmParameterException paramString)
    {
      paramString.printStackTrace();
      return (Cipher)localObject1;
    }
    catch (InvalidKeyException paramString)
    {
      paramString.printStackTrace();
      return (Cipher)localObject2;
    }
    catch (NoSuchPaddingException paramString)
    {
      paramString.printStackTrace();
      return (Cipher)localObject3;
    }
    catch (NoSuchAlgorithmException paramString)
    {
      paramString.printStackTrace();
    }
    return (Cipher)localObject4;
  }
  
  /* Error */
  public static boolean transferFile(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    // Byte code:
    //   0: getstatic 21	com/tencent/tbs/common/download/qb/AESHelper:TAG	Ljava/lang/String;
    //   3: astore 8
    //   5: new 23	java/lang/StringBuilder
    //   8: dup
    //   9: invokespecial 24	java/lang/StringBuilder:<init>	()V
    //   12: astore 8
    //   14: aload 8
    //   16: ldc 26
    //   18: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   21: pop
    //   22: aload 8
    //   24: aload_0
    //   25: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: pop
    //   29: aload 8
    //   31: ldc 32
    //   33: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: pop
    //   37: aload 8
    //   39: aload_1
    //   40: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: pop
    //   44: aload 8
    //   46: ldc 34
    //   48: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: pop
    //   52: aload 8
    //   54: aload_2
    //   55: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: pop
    //   59: aload 8
    //   61: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   64: pop
    //   65: iconst_0
    //   66: istore 6
    //   68: iconst_0
    //   69: istore 7
    //   71: iconst_0
    //   72: istore 5
    //   74: aconst_null
    //   75: astore 9
    //   77: aconst_null
    //   78: astore 10
    //   80: aconst_null
    //   81: astore 8
    //   83: new 99	java/io/File
    //   86: dup
    //   87: aload_1
    //   88: invokespecial 102	java/io/File:<init>	(Ljava/lang/String;)V
    //   91: astore_1
    //   92: getstatic 21	com/tencent/tbs/common/download/qb/AESHelper:TAG	Ljava/lang/String;
    //   95: astore 11
    //   97: new 23	java/lang/StringBuilder
    //   100: dup
    //   101: invokespecial 24	java/lang/StringBuilder:<init>	()V
    //   104: astore 11
    //   106: aload 11
    //   108: ldc 104
    //   110: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: pop
    //   114: aload 11
    //   116: aload_1
    //   117: invokevirtual 108	java/io/File:length	()J
    //   120: invokevirtual 111	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   123: pop
    //   124: aload 11
    //   126: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   129: pop
    //   130: new 99	java/io/File
    //   133: dup
    //   134: aload_2
    //   135: invokespecial 102	java/io/File:<init>	(Ljava/lang/String;)V
    //   138: astore 11
    //   140: aload_1
    //   141: invokevirtual 115	java/io/File:exists	()Z
    //   144: ifeq +160 -> 304
    //   147: aload_1
    //   148: invokevirtual 118	java/io/File:isFile	()Z
    //   151: ifeq +153 -> 304
    //   154: aload 11
    //   156: invokevirtual 122	java/io/File:getParentFile	()Ljava/io/File;
    //   159: invokevirtual 115	java/io/File:exists	()Z
    //   162: ifne +12 -> 174
    //   165: aload 11
    //   167: invokevirtual 122	java/io/File:getParentFile	()Ljava/io/File;
    //   170: invokevirtual 125	java/io/File:mkdirs	()Z
    //   173: pop
    //   174: aload 11
    //   176: invokevirtual 128	java/io/File:createNewFile	()Z
    //   179: pop
    //   180: new 130	java/io/FileInputStream
    //   183: dup
    //   184: aload_1
    //   185: invokespecial 133	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   188: astore_2
    //   189: new 135	java/io/FileOutputStream
    //   192: dup
    //   193: aload 11
    //   195: invokespecial 136	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   198: astore 8
    //   200: iload_3
    //   201: ifeq +9 -> 210
    //   204: iconst_1
    //   205: istore 4
    //   207: goto +6 -> 213
    //   210: iconst_2
    //   211: istore 4
    //   213: new 138	javax/crypto/CipherOutputStream
    //   216: dup
    //   217: aload 8
    //   219: aload_0
    //   220: iload 4
    //   222: invokestatic 140	com/tencent/tbs/common/download/qb/AESHelper:initAESCipher	(Ljava/lang/String;I)Ljavax/crypto/Cipher;
    //   225: invokespecial 143	javax/crypto/CipherOutputStream:<init>	(Ljava/io/OutputStream;Ljavax/crypto/Cipher;)V
    //   228: astore_1
    //   229: sipush 1024
    //   232: newarray <illegal type>
    //   234: astore_0
    //   235: aload_2
    //   236: aload_0
    //   237: invokevirtual 147	java/io/FileInputStream:read	([B)I
    //   240: istore 4
    //   242: iload 4
    //   244: iflt +14 -> 258
    //   247: aload_1
    //   248: aload_0
    //   249: iconst_0
    //   250: iload 4
    //   252: invokevirtual 151	javax/crypto/CipherOutputStream:write	([BII)V
    //   255: goto -20 -> 235
    //   258: aload 8
    //   260: astore_0
    //   261: iconst_1
    //   262: istore_3
    //   263: goto +51 -> 314
    //   266: astore_0
    //   267: goto +25 -> 292
    //   270: astore_0
    //   271: goto +30 -> 301
    //   274: astore_0
    //   275: aconst_null
    //   276: astore_1
    //   277: goto +15 -> 292
    //   280: astore_0
    //   281: aconst_null
    //   282: astore_1
    //   283: goto +18 -> 301
    //   286: astore_0
    //   287: aconst_null
    //   288: astore_1
    //   289: aload_1
    //   290: astore 8
    //   292: goto +179 -> 471
    //   295: astore_0
    //   296: aconst_null
    //   297: astore_1
    //   298: aload_1
    //   299: astore 8
    //   301: goto +83 -> 384
    //   304: aconst_null
    //   305: astore_1
    //   306: aload_1
    //   307: astore_2
    //   308: aload 8
    //   310: astore_0
    //   311: iload 5
    //   313: istore_3
    //   314: aload_2
    //   315: ifnull +15 -> 330
    //   318: aload_2
    //   319: invokevirtual 154	java/io/FileInputStream:close	()V
    //   322: goto +8 -> 330
    //   325: astore_2
    //   326: aload_2
    //   327: invokevirtual 155	java/io/IOException:printStackTrace	()V
    //   330: aload_0
    //   331: ifnull +15 -> 346
    //   334: aload_0
    //   335: invokevirtual 156	java/io/FileOutputStream:close	()V
    //   338: goto +8 -> 346
    //   341: astore_0
    //   342: aload_0
    //   343: invokevirtual 155	java/io/IOException:printStackTrace	()V
    //   346: iload_3
    //   347: istore 5
    //   349: aload_1
    //   350: ifnull +86 -> 436
    //   353: aload_1
    //   354: invokevirtual 157	javax/crypto/CipherOutputStream:close	()V
    //   357: iload_3
    //   358: istore 5
    //   360: goto +76 -> 436
    //   363: astore_0
    //   364: aconst_null
    //   365: astore_1
    //   366: aload_1
    //   367: astore 8
    //   369: aload 10
    //   371: astore_2
    //   372: goto +99 -> 471
    //   375: astore_0
    //   376: aconst_null
    //   377: astore_1
    //   378: aload_1
    //   379: astore 8
    //   381: aload 9
    //   383: astore_2
    //   384: aload_0
    //   385: invokevirtual 158	java/lang/Throwable:printStackTrace	()V
    //   388: aload_2
    //   389: ifnull +15 -> 404
    //   392: aload_2
    //   393: invokevirtual 154	java/io/FileInputStream:close	()V
    //   396: goto +8 -> 404
    //   399: astore_0
    //   400: aload_0
    //   401: invokevirtual 155	java/io/IOException:printStackTrace	()V
    //   404: aload 8
    //   406: ifnull +16 -> 422
    //   409: aload 8
    //   411: invokevirtual 156	java/io/FileOutputStream:close	()V
    //   414: goto +8 -> 422
    //   417: astore_0
    //   418: aload_0
    //   419: invokevirtual 155	java/io/IOException:printStackTrace	()V
    //   422: iload 7
    //   424: istore 5
    //   426: aload_1
    //   427: ifnull +9 -> 436
    //   430: iload 6
    //   432: istore_3
    //   433: goto -80 -> 353
    //   436: getstatic 21	com/tencent/tbs/common/download/qb/AESHelper:TAG	Ljava/lang/String;
    //   439: astore_0
    //   440: new 23	java/lang/StringBuilder
    //   443: dup
    //   444: invokespecial 24	java/lang/StringBuilder:<init>	()V
    //   447: astore_0
    //   448: aload_0
    //   449: ldc -96
    //   451: invokevirtual 30	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   454: pop
    //   455: aload_0
    //   456: iload 5
    //   458: invokevirtual 163	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   461: pop
    //   462: aload_0
    //   463: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   466: pop
    //   467: iload 5
    //   469: ireturn
    //   470: astore_0
    //   471: aload_2
    //   472: ifnull +15 -> 487
    //   475: aload_2
    //   476: invokevirtual 154	java/io/FileInputStream:close	()V
    //   479: goto +8 -> 487
    //   482: astore_2
    //   483: aload_2
    //   484: invokevirtual 155	java/io/IOException:printStackTrace	()V
    //   487: aload 8
    //   489: ifnull +16 -> 505
    //   492: aload 8
    //   494: invokevirtual 156	java/io/FileOutputStream:close	()V
    //   497: goto +8 -> 505
    //   500: astore_2
    //   501: aload_2
    //   502: invokevirtual 155	java/io/IOException:printStackTrace	()V
    //   505: aload_1
    //   506: ifnull +7 -> 513
    //   509: aload_1
    //   510: invokevirtual 157	javax/crypto/CipherOutputStream:close	()V
    //   513: aload_0
    //   514: athrow
    //   515: astore_0
    //   516: iload_3
    //   517: istore 5
    //   519: goto -83 -> 436
    //   522: astore_1
    //   523: goto -10 -> 513
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	526	0	paramString1	String
    //   0	526	1	paramString2	String
    //   0	526	2	paramString3	String
    //   0	526	3	paramBoolean	boolean
    //   205	46	4	i	int
    //   72	446	5	bool1	boolean
    //   66	365	6	bool2	boolean
    //   69	354	7	bool3	boolean
    //   3	490	8	localObject1	Object
    //   75	307	9	localObject2	Object
    //   78	292	10	localObject3	Object
    //   95	99	11	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   229	235	266	finally
    //   235	242	266	finally
    //   247	255	266	finally
    //   229	235	270	java/lang/Throwable
    //   235	242	270	java/lang/Throwable
    //   247	255	270	java/lang/Throwable
    //   213	229	274	finally
    //   213	229	280	java/lang/Throwable
    //   189	200	286	finally
    //   189	200	295	java/lang/Throwable
    //   318	322	325	java/io/IOException
    //   334	338	341	java/io/IOException
    //   83	174	363	finally
    //   174	189	363	finally
    //   83	174	375	java/lang/Throwable
    //   174	189	375	java/lang/Throwable
    //   392	396	399	java/io/IOException
    //   409	414	417	java/io/IOException
    //   384	388	470	finally
    //   475	479	482	java/io/IOException
    //   492	497	500	java/io/IOException
    //   353	357	515	java/lang/Exception
    //   509	513	522	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\AESHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */