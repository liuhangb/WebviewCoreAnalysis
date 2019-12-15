package com.tencent.common.wup.security;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.taf.HexUtil;
import com.tencent.basesupport.FLogger;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.Md5Utils;
import com.tencent.common.utils.StringUtils;
import com.tencent.mtt.ContextHolder;
import java.io.File;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.Executor;

public class MttTokenProvider
{
  public static final String CMD_PARAM_RSA_KEY_DATA = "rsakey";
  public static final String CMD_PARAM_RSA_KEY_ID = "rsaid";
  public static final String CMD_PARAM_RSA_KEY_LENGTH = "rsalen";
  public static final byte RSA_NO_PADDING = 1;
  public static final byte RSA_OAEP_PADDING = 2;
  public static final String URL_PARAM_ENCRYPT_VERSION = "v";
  public static final String URL_PARAM_ENC_TYPE = "encrypt";
  public static final String URL_PARAM_KEY_DATA = "qbkey";
  public static final String URL_PARAM_KEY_ID = "id";
  public static final String URL_PARAM_KEY_IV = "iv";
  public static final String URL_PARAM_KEY_LENGTH = "len";
  public static final String WUP_ENCRYPT_METHOD = "12";
  public static final String WUP_ENCRYPT_METHOD_OAEP_PADDING = "17";
  private static MttTokenProvider jdField_a_of_type_ComTencentCommonWupSecurityMttTokenProvider;
  private static Object jdField_a_of_type_JavaLangObject = new Object();
  private int jdField_a_of_type_Int = -1;
  private Context jdField_a_of_type_AndroidContentContext = ContextHolder.getAppContext();
  private MttRsaPubKey jdField_a_of_type_ComTencentCommonWupSecurityMttRsaPubKey = null;
  private MttWupToken jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken = null;
  private String jdField_a_of_type_JavaLangString = "";
  private boolean jdField_a_of_type_Boolean = false;
  private Object b = new Object();
  private Object c = new Object();
  private Object d = new Object();
  
  private MttRsaPubKey a()
    throws Throwable
  {
    FLogger.d("MttRsaProvider", "loadPublicKeySync: begins");
    try
    {
      MttRsaPubKey localMttRsaPubKey2;
      try
      {
        synchronized (this.c)
        {
          MttRsaPubKey localMttRsaPubKey1 = b();
        }
      }
      catch (Throwable localThrowable1)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("loadPublicKeySync: load key from file Error:");
        localStringBuilder.append(localThrowable1.getMessage());
        FLogger.d("MttRsaProvider", localStringBuilder.toString());
        localMttRsaPubKey2 = null;
        if (localMttRsaPubKey2 != null) {
          return localMttRsaPubKey2;
        }
        localMttRsaPubKey2 = MttRsaPubKey.getDefault();
        FLogger.d("MttRsaProvider", "loadPublicKeySync: load default key succ");
        return localMttRsaPubKey2;
      }
      throw localMttRsaPubKey2;
    }
    catch (Throwable localThrowable2)
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("loadPublicKeySync: load default key Error:");
      ((StringBuilder)???).append(localThrowable2.getMessage());
      FLogger.d("MttRsaProvider", ((StringBuilder)???).toString());
      localThrowable2.printStackTrace();
    }
  }
  
  private MttRsaPubKey a(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    String str = a(paramString2);
    if (TextUtils.isEmpty(str)) {
      return null;
    }
    paramString2 = new MttRsaPubKey();
    paramString2.mKeyData = Base64.decode(str.getBytes(), 0);
    paramString2.mKeyLength = Integer.parseInt(paramString3);
    if ((paramString2.mKeyLength > 0) && (paramString2.mKeyLength % 8 == 0))
    {
      paramString2.mVerificationInfo = HexUtil.hexStr2Bytes(paramString1);
      if (paramString2.mVerificationInfo == null) {
        return null;
      }
      paramString1 = new StringBuilder();
      paramString1.append("parseKeyData: data=");
      paramString1.append(HexUtil.bytes2HexStr(paramString2.mKeyData));
      paramString1.append(", verify=");
      paramString1.append(HexUtil.bytes2HexStr(paramString2.mVerificationInfo));
      FLogger.d("MttRsaProvider", paramString1.toString());
      return paramString2;
    }
    paramString1 = new StringBuilder();
    paramString1.append("parseKeyData: invalid key length, keyLen=");
    paramString1.append(paramString2.mKeyLength);
    FLogger.d("MttRsaProvider", paramString1.toString());
    return null;
  }
  
  private MttWupToken a()
    throws Throwable
  {
    if (!a())
    {
      FLogger.d("MttRsaProvider", "loadTokenFromFile: SAVE TOKEN TO FILE IS DISABLED!! ");
      return null;
    }
    Object localObject1 = a();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("loadTokenFromFile: token from file = ");
    ((StringBuilder)localObject2).append((String)localObject1);
    FLogger.d("MttRsaProvider", ((StringBuilder)localObject2).toString());
    if (!TextUtils.isEmpty((CharSequence)localObject1))
    {
      localObject2 = ((String)localObject1).split(";");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("loadTokenFromFile: after split, size = ");
      if (localObject2 == null) {
        localObject1 = "null";
      } else {
        localObject1 = Integer.valueOf(localObject2.length);
      }
      localStringBuilder.append(localObject1);
      FLogger.d("MttRsaProvider", localStringBuilder.toString());
      if ((localObject2 != null) && (localObject2.length == 3))
      {
        boolean bool = false;
        localObject1 = HexUtil.hexStr2Bytes(localObject2[0]);
        localStringBuilder = localObject2[1];
        long l = Long.parseLong(localObject2[2]);
        if ((localObject1 != null) && (localObject1.length > 0) && (!TextUtils.isEmpty(localStringBuilder)) && (l > 0L) && (System.currentTimeMillis() < l)) {
          return new MttWupToken((byte[])localObject1, localStringBuilder, l);
        }
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("loadTokenFromFile: verification failed, aesKey=");
        if (localObject1 == null) {
          localObject1 = "null";
        } else {
          localObject1 = HexUtil.bytes2HexStr((byte[])localObject1);
        }
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append(", token=");
        ((StringBuilder)localObject2).append(localStringBuilder);
        ((StringBuilder)localObject2).append(", expireDate=");
        ((StringBuilder)localObject2).append(l);
        ((StringBuilder)localObject2).append(", isExpired = ");
        if (System.currentTimeMillis() >= l) {
          bool = true;
        }
        ((StringBuilder)localObject2).append(bool);
        FLogger.d("MttRsaProvider", ((StringBuilder)localObject2).toString());
        return null;
      }
      FLogger.d("MttRsaProvider", "loadTokenFromFile: after splited, token string is null or empty ");
      return null;
    }
    FLogger.d("MttRsaProvider", "loadTokenFromFile: token string is null or empty");
    return null;
  }
  
  /* Error */
  private File a(String paramString1, String paramString2, String paramString3)
  {
    // Byte code:
    //   0: new 268	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: getfield 90	com/tencent/common/wup/security/MttTokenProvider:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   8: invokestatic 274	com/tencent/common/utils/FileUtilsF:getDataDir	(Landroid/content/Context;)Ljava/io/File;
    //   11: ldc_w 276
    //   14: invokespecial 279	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   17: astore 6
    //   19: aload 6
    //   21: invokevirtual 282	java/io/File:exists	()Z
    //   24: ifne +9 -> 33
    //   27: aload 6
    //   29: invokevirtual 285	java/io/File:createNewFile	()Z
    //   32: pop
    //   33: new 287	java/io/DataOutputStream
    //   36: dup
    //   37: aload 6
    //   39: invokestatic 291	com/tencent/common/utils/FileUtilsF:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
    //   42: invokespecial 294	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   45: astore 5
    //   47: aload 5
    //   49: astore 4
    //   51: aload 5
    //   53: sipush 254
    //   56: invokevirtual 298	java/io/DataOutputStream:writeInt	(I)V
    //   59: aload 5
    //   61: astore 4
    //   63: aload 5
    //   65: aload_1
    //   66: invokevirtual 302	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   69: aload 5
    //   71: astore 4
    //   73: aload 5
    //   75: aload_2
    //   76: invokevirtual 302	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   79: aload 5
    //   81: astore 4
    //   83: aload 5
    //   85: aload_3
    //   86: invokevirtual 302	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   89: aload 5
    //   91: astore 4
    //   93: new 113	java/lang/StringBuilder
    //   96: dup
    //   97: invokespecial 114	java/lang/StringBuilder:<init>	()V
    //   100: astore_1
    //   101: aload 5
    //   103: astore 4
    //   105: aload_1
    //   106: ldc_w 304
    //   109: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: pop
    //   113: aload 5
    //   115: astore 4
    //   117: aload_1
    //   118: aload 6
    //   120: invokevirtual 307	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   123: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: pop
    //   127: aload 5
    //   129: astore 4
    //   131: aload_1
    //   132: ldc_w 309
    //   135: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: pop
    //   139: aload 5
    //   141: astore 4
    //   143: ldc 102
    //   145: aload_1
    //   146: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   149: invokestatic 109	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   152: aload 5
    //   154: invokestatic 313	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   157: aload 6
    //   159: areturn
    //   160: astore_2
    //   161: aload 5
    //   163: astore_1
    //   164: goto +13 -> 177
    //   167: astore_1
    //   168: aconst_null
    //   169: astore 4
    //   171: goto +66 -> 237
    //   174: astore_2
    //   175: aconst_null
    //   176: astore_1
    //   177: aload_1
    //   178: astore 4
    //   180: new 113	java/lang/StringBuilder
    //   183: dup
    //   184: invokespecial 114	java/lang/StringBuilder:<init>	()V
    //   187: astore_3
    //   188: aload_1
    //   189: astore 4
    //   191: aload_3
    //   192: ldc_w 315
    //   195: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: pop
    //   199: aload_1
    //   200: astore 4
    //   202: aload_3
    //   203: aload_2
    //   204: invokevirtual 124	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   207: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: pop
    //   211: aload_1
    //   212: astore 4
    //   214: ldc 102
    //   216: aload_3
    //   217: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   220: invokestatic 109	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   223: aload_1
    //   224: astore 4
    //   226: aload_2
    //   227: invokevirtual 139	java/lang/Throwable:printStackTrace	()V
    //   230: aload_1
    //   231: invokestatic 313	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   234: aconst_null
    //   235: areturn
    //   236: astore_1
    //   237: aload 4
    //   239: invokestatic 313	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   242: aload_1
    //   243: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	244	0	this	MttTokenProvider
    //   0	244	1	paramString1	String
    //   0	244	2	paramString2	String
    //   0	244	3	paramString3	String
    //   49	189	4	localObject	Object
    //   45	117	5	localDataOutputStream	java.io.DataOutputStream
    //   17	141	6	localFile	File
    // Exception table:
    //   from	to	target	type
    //   51	59	160	java/lang/Throwable
    //   63	69	160	java/lang/Throwable
    //   73	79	160	java/lang/Throwable
    //   83	89	160	java/lang/Throwable
    //   93	101	160	java/lang/Throwable
    //   105	113	160	java/lang/Throwable
    //   117	127	160	java/lang/Throwable
    //   131	139	160	java/lang/Throwable
    //   143	152	160	java/lang/Throwable
    //   19	33	167	finally
    //   33	47	167	finally
    //   19	33	174	java/lang/Throwable
    //   33	47	174	java/lang/Throwable
    //   51	59	236	finally
    //   63	69	236	finally
    //   73	79	236	finally
    //   83	89	236	finally
    //   93	101	236	finally
    //   105	113	236	finally
    //   117	127	236	finally
    //   131	139	236	finally
    //   143	152	236	finally
    //   180	188	236	finally
    //   191	199	236	finally
    //   202	211	236	finally
    //   214	223	236	finally
    //   226	230	236	finally
  }
  
  private String a()
  {
    Object localObject2 = "";
    Object localObject1 = FileUtilsF.getDataDir(this.jdField_a_of_type_AndroidContentContext);
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append(this.jdField_a_of_type_JavaLangString);
    ((StringBuilder)localObject3).append("_wup_token.dat");
    localObject3 = new File((File)localObject1, ((StringBuilder)localObject3).toString());
    localObject1 = localObject2;
    if (((File)localObject3).exists())
    {
      localObject1 = FileUtilsF.read((File)localObject3);
      byte[] arrayOfByte = new byte[((ByteBuffer)localObject1).position()];
      ((ByteBuffer)localObject1).position(0);
      ((ByteBuffer)localObject1).get(arrayOfByte);
      FileUtilsF.getInstance().releaseByteBuffer((ByteBuffer)localObject1);
      localObject1 = localObject2;
      if (arrayOfByte.length > 0)
      {
        localObject1 = new String(arrayOfByte);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("readTokenStringFromFile: read from file compelet, path = ");
        ((StringBuilder)localObject2).append(((File)localObject3).getAbsolutePath());
        FLogger.d("MttRsaProvider", ((StringBuilder)localObject2).toString());
      }
    }
    return (String)localObject1;
  }
  
  private String a(String paramString)
  {
    if ((!TextUtils.isEmpty(paramString)) && (paramString.contains("-----BEGIN PUBLIC KEY-----")) && (paramString.contains("-----END PUBLIC KEY-----")))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("processPEMKey: before process: ");
      localStringBuilder.append(paramString);
      FLogger.d("MttRsaProvider", localStringBuilder.toString());
      paramString = paramString.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replace("\n", "");
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("processPEMKey: after process: ");
      localStringBuilder.append(paramString);
      FLogger.d("MttRsaProvider", localStringBuilder.toString());
      return paramString;
    }
    FLogger.d("MttRsaProvider", "processPEMKey: key data is null or not formated as PEM");
    return null;
  }
  
  private void a(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      FLogger.d("MttRsaProvider", "writeTokenStringToFile: param is empty, ignore");
      return;
    }
    try
    {
      localObject = FileUtilsF.getDataDir(this.jdField_a_of_type_AndroidContentContext);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append("_wup_token.dat");
      localObject = new File((File)localObject, localStringBuilder.toString());
      if (!((File)localObject).exists()) {
        ((File)localObject).createNewFile();
      }
      FileUtilsF.save((File)localObject, paramString.getBytes());
      paramString = new StringBuilder();
      paramString.append("writeTokenStringToFile: save token to file complete, path = ");
      paramString.append(((File)localObject).getAbsolutePath());
      FLogger.d("MttRsaProvider", paramString.toString());
      return;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("writeTokenStringToFile: error occured in writing token: ");
      ((StringBuilder)localObject).append(paramString.getMessage());
      FLogger.d("MttRsaProvider", ((StringBuilder)localObject).toString());
    }
  }
  
  private boolean a()
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  /* Error */
  private MttRsaPubKey b()
    throws Throwable
  {
    // Byte code:
    //   0: new 268	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: getfield 90	com/tencent/common/wup/security/MttTokenProvider:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   8: invokestatic 274	com/tencent/common/utils/FileUtilsF:getDataDir	(Landroid/content/Context;)Ljava/io/File;
    //   11: ldc_w 387
    //   14: invokespecial 279	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   17: astore 5
    //   19: aload 5
    //   21: invokevirtual 282	java/io/File:exists	()Z
    //   24: istore_1
    //   25: aconst_null
    //   26: astore 4
    //   28: aconst_null
    //   29: astore_3
    //   30: iload_1
    //   31: ifne +78 -> 109
    //   34: new 113	java/lang/StringBuilder
    //   37: dup
    //   38: invokespecial 114	java/lang/StringBuilder:<init>	()V
    //   41: astore_2
    //   42: aload_2
    //   43: ldc_w 389
    //   46: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: pop
    //   50: aload_2
    //   51: aload 5
    //   53: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   56: pop
    //   57: aload_2
    //   58: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   61: ifnonnull +9 -> 70
    //   64: ldc -36
    //   66: astore_2
    //   67: goto +34 -> 101
    //   70: new 113	java/lang/StringBuilder
    //   73: dup
    //   74: invokespecial 114	java/lang/StringBuilder:<init>	()V
    //   77: astore_2
    //   78: aload_2
    //   79: aload 5
    //   81: invokevirtual 392	java/io/File:getName	()Ljava/lang/String;
    //   84: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: pop
    //   88: aload_2
    //   89: ldc_w 394
    //   92: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: aload_2
    //   97: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   100: astore_2
    //   101: ldc 102
    //   103: aload_2
    //   104: invokestatic 109	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   107: aconst_null
    //   108: areturn
    //   109: aload_3
    //   110: astore_2
    //   111: ldc 102
    //   113: ldc_w 396
    //   116: invokestatic 109	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   119: aload_3
    //   120: astore_2
    //   121: new 398	java/io/DataInputStream
    //   124: dup
    //   125: aload 5
    //   127: invokestatic 402	com/tencent/common/utils/FileUtilsF:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   130: invokespecial 405	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   133: astore_3
    //   134: aload_3
    //   135: invokevirtual 408	java/io/DataInputStream:readInt	()I
    //   138: sipush 254
    //   141: if_icmpne +399 -> 540
    //   144: aload_3
    //   145: invokevirtual 411	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   148: astore 4
    //   150: aload_3
    //   151: invokevirtual 411	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   154: astore_2
    //   155: aload_3
    //   156: invokevirtual 411	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   159: astore 5
    //   161: new 113	java/lang/StringBuilder
    //   164: dup
    //   165: invokespecial 114	java/lang/StringBuilder:<init>	()V
    //   168: astore 6
    //   170: aload 6
    //   172: ldc_w 413
    //   175: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   178: pop
    //   179: aload 6
    //   181: aload 4
    //   183: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   186: pop
    //   187: aload 6
    //   189: ldc_w 415
    //   192: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   195: pop
    //   196: aload 6
    //   198: aload_2
    //   199: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: pop
    //   203: aload 6
    //   205: ldc_w 417
    //   208: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   211: pop
    //   212: aload 6
    //   214: aload 5
    //   216: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   219: pop
    //   220: ldc 102
    //   222: aload 6
    //   224: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   227: invokestatic 109	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   230: aload 4
    //   232: invokestatic 152	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   235: ifne +286 -> 521
    //   238: aload_2
    //   239: invokestatic 152	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   242: ifne +279 -> 521
    //   245: aload 5
    //   247: invokestatic 152	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   250: ifne +271 -> 521
    //   253: aload 4
    //   255: invokestatic 422	com/tencent/common/utils/Md5Utils:getMD5	(Ljava/lang/String;)Ljava/lang/String;
    //   258: astore 6
    //   260: new 113	java/lang/StringBuilder
    //   263: dup
    //   264: invokespecial 114	java/lang/StringBuilder:<init>	()V
    //   267: astore 7
    //   269: aload 7
    //   271: ldc_w 424
    //   274: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   277: pop
    //   278: aload 7
    //   280: aload 6
    //   282: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   285: pop
    //   286: aload 7
    //   288: ldc_w 426
    //   291: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   294: pop
    //   295: aload 7
    //   297: aload_2
    //   298: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   301: pop
    //   302: ldc 102
    //   304: aload 7
    //   306: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   309: invokestatic 109	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   312: aload 6
    //   314: invokestatic 152	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   317: ifne +141 -> 458
    //   320: aload 6
    //   322: aload_2
    //   323: invokestatic 432	com/tencent/common/utils/StringUtils:isStringEqualsIgnoreCase	(Ljava/lang/String;Ljava/lang/String;)Z
    //   326: ifeq +132 -> 458
    //   329: ldc 102
    //   331: ldc_w 434
    //   334: invokestatic 109	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   337: aload_0
    //   338: aload_2
    //   339: aload 4
    //   341: aload 5
    //   343: invokespecial 436	com/tencent/common/wup/security/MttTokenProvider:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/tencent/common/wup/security/MttRsaPubKey;
    //   346: astore_2
    //   347: aload_2
    //   348: ifnull +91 -> 439
    //   351: new 113	java/lang/StringBuilder
    //   354: dup
    //   355: invokespecial 114	java/lang/StringBuilder:<init>	()V
    //   358: astore 4
    //   360: aload 4
    //   362: ldc_w 438
    //   365: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   368: pop
    //   369: aload 4
    //   371: aload_2
    //   372: getfield 178	com/tencent/common/wup/security/MttRsaPubKey:mKeyLength	I
    //   375: invokevirtual 200	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   378: pop
    //   379: aload 4
    //   381: ldc_w 440
    //   384: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   387: pop
    //   388: aload 4
    //   390: aload_2
    //   391: getfield 169	com/tencent/common/wup/security/MttRsaPubKey:mKeyData	[B
    //   394: invokestatic 193	com/taf/HexUtil:bytes2HexStr	([B)Ljava/lang/String;
    //   397: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   400: pop
    //   401: aload 4
    //   403: ldc_w 442
    //   406: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   409: pop
    //   410: aload 4
    //   412: aload_2
    //   413: getfield 187	com/tencent/common/wup/security/MttRsaPubKey:mVerificationInfo	[B
    //   416: invokestatic 193	com/taf/HexUtil:bytes2HexStr	([B)Ljava/lang/String;
    //   419: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   422: pop
    //   423: ldc 102
    //   425: aload 4
    //   427: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   430: invokestatic 109	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   433: aload_3
    //   434: invokestatic 313	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   437: aload_2
    //   438: areturn
    //   439: ldc 102
    //   441: ldc_w 444
    //   444: invokestatic 109	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   447: new 379	java/lang/RuntimeException
    //   450: dup
    //   451: ldc_w 444
    //   454: invokespecial 445	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   457: athrow
    //   458: new 113	java/lang/StringBuilder
    //   461: dup
    //   462: invokespecial 114	java/lang/StringBuilder:<init>	()V
    //   465: astore 4
    //   467: aload 4
    //   469: ldc_w 447
    //   472: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   475: pop
    //   476: aload 4
    //   478: aload 6
    //   480: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   483: pop
    //   484: aload 4
    //   486: ldc_w 449
    //   489: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   492: pop
    //   493: aload 4
    //   495: aload_2
    //   496: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   499: pop
    //   500: ldc 102
    //   502: aload 4
    //   504: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   507: invokestatic 109	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   510: new 379	java/lang/RuntimeException
    //   513: dup
    //   514: ldc_w 451
    //   517: invokespecial 445	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   520: athrow
    //   521: ldc 102
    //   523: ldc_w 453
    //   526: invokestatic 109	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   529: new 379	java/lang/RuntimeException
    //   532: dup
    //   533: ldc_w 455
    //   536: invokespecial 445	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   539: athrow
    //   540: new 113	java/lang/StringBuilder
    //   543: dup
    //   544: invokespecial 114	java/lang/StringBuilder:<init>	()V
    //   547: astore_2
    //   548: aload_2
    //   549: ldc_w 457
    //   552: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   555: pop
    //   556: aload_2
    //   557: aload 5
    //   559: invokevirtual 392	java/io/File:getName	()Ljava/lang/String;
    //   562: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   565: pop
    //   566: new 379	java/lang/RuntimeException
    //   569: dup
    //   570: aload_2
    //   571: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   574: invokespecial 445	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   577: athrow
    //   578: astore_2
    //   579: goto +86 -> 665
    //   582: astore 4
    //   584: goto +20 -> 604
    //   587: astore 4
    //   589: aload_2
    //   590: astore_3
    //   591: aload 4
    //   593: astore_2
    //   594: goto +71 -> 665
    //   597: astore_2
    //   598: aload 4
    //   600: astore_3
    //   601: aload_2
    //   602: astore 4
    //   604: aload_3
    //   605: astore_2
    //   606: new 113	java/lang/StringBuilder
    //   609: dup
    //   610: invokespecial 114	java/lang/StringBuilder:<init>	()V
    //   613: astore 5
    //   615: aload_3
    //   616: astore_2
    //   617: aload 5
    //   619: ldc_w 459
    //   622: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   625: pop
    //   626: aload_3
    //   627: astore_2
    //   628: aload 5
    //   630: aload 4
    //   632: invokevirtual 124	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   635: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   638: pop
    //   639: aload_3
    //   640: astore_2
    //   641: ldc 102
    //   643: aload 5
    //   645: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   648: invokestatic 109	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   651: aload_3
    //   652: astore_2
    //   653: aload 4
    //   655: invokevirtual 139	java/lang/Throwable:printStackTrace	()V
    //   658: aload_3
    //   659: invokestatic 313	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   662: aload 4
    //   664: athrow
    //   665: aload_3
    //   666: invokestatic 313	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   669: aload_2
    //   670: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	671	0	this	MttTokenProvider
    //   24	7	1	bool	boolean
    //   41	530	2	localObject1	Object
    //   578	12	2	localObject2	Object
    //   593	1	2	localObject3	Object
    //   597	5	2	localThrowable1	Throwable
    //   605	65	2	localObject4	Object
    //   29	637	3	localObject5	Object
    //   26	477	4	localObject6	Object
    //   582	1	4	localThrowable2	Throwable
    //   587	12	4	localObject7	Object
    //   602	61	4	localObject8	Object
    //   17	627	5	localObject9	Object
    //   168	311	6	localObject10	Object
    //   267	38	7	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   134	347	578	finally
    //   351	433	578	finally
    //   439	458	578	finally
    //   458	521	578	finally
    //   521	540	578	finally
    //   540	578	578	finally
    //   134	347	582	java/lang/Throwable
    //   351	433	582	java/lang/Throwable
    //   439	458	582	java/lang/Throwable
    //   458	521	582	java/lang/Throwable
    //   521	540	582	java/lang/Throwable
    //   540	578	582	java/lang/Throwable
    //   111	119	587	finally
    //   121	134	587	finally
    //   606	615	587	finally
    //   617	626	587	finally
    //   628	639	587	finally
    //   641	651	587	finally
    //   653	658	587	finally
    //   111	119	597	java/lang/Throwable
    //   121	134	597	java/lang/Throwable
  }
  
  private MttWupToken b()
  {
    SecureRandom localSecureRandom = new SecureRandom();
    byte[] arrayOfByte1 = new byte[8];
    byte[] arrayOfByte2 = new byte[8];
    localSecureRandom.nextBytes(arrayOfByte1);
    localSecureRandom.nextBytes(arrayOfByte2);
    return new MttWupToken(ByteUtils.mergeByteData(arrayOfByte1, arrayOfByte2), this);
  }
  
  public static MttTokenProvider getInstance()
  {
    if (jdField_a_of_type_ComTencentCommonWupSecurityMttTokenProvider == null) {
      synchronized (jdField_a_of_type_JavaLangObject)
      {
        if (jdField_a_of_type_ComTencentCommonWupSecurityMttTokenProvider == null) {
          jdField_a_of_type_ComTencentCommonWupSecurityMttTokenProvider = new MttTokenProvider();
        }
      }
    }
    return jdField_a_of_type_ComTencentCommonWupSecurityMttTokenProvider;
  }
  
  protected String buildUrlParams(byte[] paramArrayOfByte, byte paramByte, String paramString)
  {
    Object localObject = this.b;
    if (paramArrayOfByte != null) {}
    try
    {
      if ((this.jdField_a_of_type_ComTencentCommonWupSecurityMttRsaPubKey != null) && (this.jdField_a_of_type_ComTencentCommonWupSecurityMttRsaPubKey.mVerificationInfo != null))
      {
        String str = "12";
        if (paramByte == 2)
        {
          str = "17";
          if ((TextUtils.isEmpty(paramString)) || (paramString.length() != 16)) {
            return "";
          }
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("encrypt");
        localStringBuilder.append("=");
        localStringBuilder.append(str);
        localStringBuilder.append("&");
        localStringBuilder.append("qbkey");
        localStringBuilder.append("=");
        localStringBuilder.append(ByteUtils.byteToHexString(paramArrayOfByte));
        localStringBuilder.append("&");
        localStringBuilder.append("len");
        localStringBuilder.append("=");
        localStringBuilder.append(this.jdField_a_of_type_ComTencentCommonWupSecurityMttRsaPubKey.mKeyLength);
        localStringBuilder.append("&");
        localStringBuilder.append("id");
        localStringBuilder.append("=");
        localStringBuilder.append(ByteUtils.byteToHexString(this.jdField_a_of_type_ComTencentCommonWupSecurityMttRsaPubKey.mVerificationInfo));
        localStringBuilder.append("&");
        localStringBuilder.append("v");
        localStringBuilder.append("=3");
        if (paramByte == 2)
        {
          localStringBuilder.append("&");
          localStringBuilder.append("iv");
          localStringBuilder.append("=");
          localStringBuilder.append(paramString);
        }
        paramArrayOfByte = localStringBuilder.toString();
        return paramArrayOfByte;
      }
      return "";
    }
    finally {}
  }
  
  protected byte[] encryptAESKey(byte[] paramArrayOfByte, byte paramByte)
    throws Throwable
  {
    if (paramArrayOfByte != null)
    {
      FLogger.d("MttRsaProvider", "encryptAESKey begins");
      Key localKey = null;
      int i = -1;
      synchronized (this.b)
      {
        if (this.jdField_a_of_type_ComTencentCommonWupSecurityMttRsaPubKey == null) {
          this.jdField_a_of_type_ComTencentCommonWupSecurityMttRsaPubKey = a();
        }
        if (this.jdField_a_of_type_ComTencentCommonWupSecurityMttRsaPubKey != null)
        {
          localKey = this.jdField_a_of_type_ComTencentCommonWupSecurityMttRsaPubKey.getKeyInstance();
          i = this.jdField_a_of_type_ComTencentCommonWupSecurityMttRsaPubKey.getKeySizeByte();
        }
        ??? = new StringBuilder();
        ((StringBuilder)???).append("encryptAESKey load key compelet, byte size=");
        ((StringBuilder)???).append(i);
        FLogger.d("MttRsaProvider", ((StringBuilder)???).toString());
        boolean bool = true;
        if ((localKey != null) && (i > 0))
        {
          ??? = paramArrayOfByte;
          if (paramByte == 1)
          {
            ??? = paramArrayOfByte;
            if (paramArrayOfByte.length < i)
            {
              ??? = new byte[i];
              System.arraycopy(paramArrayOfByte, 0, ???, ???.length - paramArrayOfByte.length, paramArrayOfByte.length);
            }
          }
          paramArrayOfByte = "RSA/ECB/NoPadding";
          if (paramByte == 2) {
            paramArrayOfByte = "RSA/ECB/OAEPWithSHA1AndMGF1Padding";
          }
          return WupEncryptHelper.doRsaEncrypt((byte[])???, localKey, paramArrayOfByte);
        }
        paramArrayOfByte = new StringBuilder();
        paramArrayOfByte.append("fail to get key inst, key inst is null ?");
        if (localKey != null) {
          bool = false;
        }
        paramArrayOfByte.append(bool);
        paramArrayOfByte.append(", keyLen = ");
        paramArrayOfByte.append(i);
        throw new Exception(paramArrayOfByte.toString());
      }
    }
    throw new IllegalArgumentException("param must not be null");
  }
  
  public MttWupToken getCurrentWupToken()
    throws Throwable
  {
    synchronized (this.d)
    {
      if ((this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken == null) || (this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken.isExpired()))
      {
        MttWupToken localMttWupToken = this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken;
        if (localMttWupToken == null)
        {
          try
          {
            this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken = a();
          }
          catch (Throwable localThrowable)
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("getCurrentWupToken: error occured when loadFromFile, e = ");
            localStringBuilder.append(localThrowable.getMessage());
            FLogger.d("MttRsaProvider", localStringBuilder.toString());
          }
        }
        else
        {
          this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken = null;
          FLogger.d("MttRsaProvider", "getCurrentWupToken: current token is EXPIRED!!!");
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("getCurrentWupToken: loadFromFile complete, values are ");
        if (this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken == null) {
          localObject1 = "null";
        } else {
          localObject1 = this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken;
        }
        localStringBuilder.append(localObject1);
        FLogger.d("MttRsaProvider", localStringBuilder.toString());
        if (this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken == null)
        {
          FLogger.d("MttRsaProvider", "getCurrentWupToken: loadFromFile failed, generate new one");
          this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken = b();
          this.jdField_a_of_type_Boolean = false;
        }
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("generate aes key, value=");
        ((StringBuilder)localObject1).append(this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken.getAESKeyString());
        FLogger.d("MttRsaProvider", ((StringBuilder)localObject1).toString());
      }
      Object localObject1 = this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken;
      return (MttWupToken)localObject1;
    }
  }
  
  public void onEnableFileTokenChanged(boolean paramBoolean)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("onEnableFileTokenChanged: isEnable = ");
    ((StringBuilder)localObject1).append(paramBoolean);
    FLogger.d("MttRsaProvider", ((StringBuilder)localObject1).toString());
    localObject1 = this.d;
    int i;
    if (paramBoolean) {
      i = 1;
    } else {
      i = 0;
    }
    try
    {
      this.jdField_a_of_type_Int = i;
      if (!paramBoolean)
      {
        File localFile = FileUtilsF.getDataDir(this.jdField_a_of_type_AndroidContentContext);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
        localStringBuilder.append("_wup_token.dat");
        localFile = new File(localFile, localStringBuilder.toString());
        if (localFile.exists()) {
          FileUtilsF.deleteQuietly(localFile);
        }
      }
      return;
    }
    finally {}
  }
  
  public boolean onGetWupPublicKey(Map<String, String> paramMap)
  {
    if ((paramMap != null) && (!paramMap.isEmpty()))
    {
      final Object localObject = (String)paramMap.get("rsakey");
      final String str1 = (String)paramMap.get("rsaid");
      final String str2 = (String)paramMap.get("rsalen");
      if ((!TextUtils.isEmpty((CharSequence)localObject)) && (!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onGetWupPublicKey: map = ");
        localStringBuilder.append(paramMap.toString());
        FLogger.d("MttRsaProvider", localStringBuilder.toString());
        paramMap = Md5Utils.getMD5((String)localObject);
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onGetWupPublicKey: calculated MD5=");
        localStringBuilder.append(paramMap);
        FLogger.d("MttRsaProvider", localStringBuilder.toString());
        if ((!TextUtils.isEmpty(paramMap)) && (StringUtils.isStringEqualsIgnoreCase(paramMap, str1)))
        {
          BrowserExecutorSupplier.forIoTasks().execute(new Runnable()
          {
            public void run()
            {
              FLogger.d("MttRsaProvider", "onGetWupPublicKey: begin save key to file");
              Object localObject2 = MttTokenProvider.a(MttTokenProvider.this, localObject, str1, str2);
              if (localObject2 == null)
              {
                FLogger.d("MttRsaProvider", "onGetWupPublicKey: save key to file failed");
                return;
              }
              Object localObject4 = new File(FileUtilsF.getDataDir(MttTokenProvider.a(MttTokenProvider.this)), "wup_key.dat");
              synchronized (MttTokenProvider.a(MttTokenProvider.this))
              {
                boolean bool;
                if (((File)localObject4).exists())
                {
                  bool = FileUtilsF.deleteQuietly((File)localObject4);
                  StringBuilder localStringBuilder = new StringBuilder();
                  localStringBuilder.append("onGetWupPublicKey: delete prev file, result = ");
                  localStringBuilder.append(bool);
                  FLogger.d("MttRsaProvider", localStringBuilder.toString());
                }
                try
                {
                  bool = ((File)localObject2).renameTo((File)localObject4);
                  localObject2 = new StringBuilder();
                  ((StringBuilder)localObject2).append("onGetWupPublicKey:rename to real file, result = ");
                  ((StringBuilder)localObject2).append(bool);
                  FLogger.d("MttRsaProvider", ((StringBuilder)localObject2).toString());
                }
                catch (Exception localException)
                {
                  localObject4 = new StringBuilder();
                  ((StringBuilder)localObject4).append("onGetWupPublicKey:rename to real error, error = ");
                  ((StringBuilder)localObject4).append(localException.getMessage());
                  FLogger.d("MttRsaProvider", ((StringBuilder)localObject4).toString());
                  localException.printStackTrace();
                }
                FLogger.d("MttRsaProvider", "onGetWupPublicKey: save key to file ends");
                return;
              }
            }
          });
          return true;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("onGetWupPublicKey: verify MD5 fail, calculated=");
        ((StringBuilder)localObject).append(paramMap);
        ((StringBuilder)localObject).append(", server given = ");
        ((StringBuilder)localObject).append(str1);
        FLogger.d("MttRsaProvider", ((StringBuilder)localObject).toString());
        return false;
      }
      FLogger.d("MttRsaProvider", "onGetWupPublicKey: cmd content is not complete, ignore");
      return false;
    }
    FLogger.d("MttRsaProvider", "onGetWupPublicKey: CMD IS NULL, Ignore");
    return false;
  }
  
  public void saveCurrentTokenToFile()
  {
    synchronized (this.d)
    {
      if (!a())
      {
        FLogger.d("MttRsaProvider", "saveCurrentTokenToFile: save token to file is disabled, DO NOT SAVE");
        return;
      }
      Object localObject1;
      if ((this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken != null) && (!this.jdField_a_of_type_Boolean))
      {
        localObject1 = this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken.getSerializedString();
      }
      else
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("saveCurrentTokenToFile: curr token is null or mHasSaveCurrToken = ");
        ((StringBuilder)localObject1).append(this.jdField_a_of_type_Boolean);
        FLogger.d("MttRsaProvider", ((StringBuilder)localObject1).toString());
        localObject1 = null;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("saveCurrentTokenToFile: serialized = ");
      localStringBuilder.append((String)localObject1);
      FLogger.d("MttRsaProvider", localStringBuilder.toString());
      if (!TextUtils.isEmpty((CharSequence)localObject1))
      {
        a((String)localObject1);
        FLogger.d("MttRsaProvider", "saveCurrentTokenToFile: save current token success");
        this.jdField_a_of_type_Boolean = true;
      }
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\security\MttTokenProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */