package com.tencent.tbs.core.partner.ad;

import android.os.Build.VERSION;
import android.util.Base64;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class PostEncryption
{
  private static final String ALGORITHM_DES = "DES/ECB/PKCS5Padding";
  private static final String NAD_PUBLICKEY = "MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRANw8wTC4Z66SZMepoSgK0rECAwEAAQ==";
  private static final String Publickey = "MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRAMFIbyQERXfyiD3adAXRitUCAwEAAQ==";
  private static final String RSA_NO_PADDING = "RSA/ECB/NoPadding";
  private static String desStr;
  protected static final char[] hexArray = "0123456789abcdef".toCharArray();
  private static String keyValue = "";
  private static boolean mDisablePostEncryption = false;
  private static PostEncryption mRsaUtils;
  private static byte[] rsaBytes;
  private Cipher mDESEncryptCipher = null;
  private Cipher mRSAEncryptCipher = null;
  
  public PostEncryption()
    throws Exception
  {
    int i = new Random().nextInt(89999999);
    int j = new Random().nextInt(89999999);
    desStr = String.valueOf(i + 10000000);
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(desStr);
    ((StringBuilder)localObject).append(String.valueOf(j + 10000000));
    rsaBytes = ((StringBuilder)localObject).toString().getBytes();
    this.mRSAEncryptCipher = Cipher.getInstance("RSA/ECB/NoPadding");
    localObject = new X509EncodedKeySpec(Base64.decode("MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRAMFIbyQERXfyiD3adAXRitUCAwEAAQ==".getBytes(), 0));
    localObject = KeyFactory.getInstance("RSA").generatePublic((KeySpec)localObject);
    this.mRSAEncryptCipher.init(1, (Key)localObject);
    keyValue = bytesToHex(this.mRSAEncryptCipher.doFinal(rsaBytes));
    localObject = new DESKeySpec(desStr.getBytes());
    localObject = SecretKeyFactory.getInstance("DES").generateSecret((KeySpec)localObject);
    this.mDESEncryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    this.mDESEncryptCipher.init(1, (Key)localObject);
  }
  
  public static byte[] DesDecrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    try
    {
      paramArrayOfByte1 = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(paramArrayOfByte1));
      Cipher localCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
      localCipher.init(2, paramArrayOfByte1);
      paramArrayOfByte1 = localCipher.doFinal(paramArrayOfByte2);
      return paramArrayOfByte1;
    }
    catch (Exception paramArrayOfByte1)
    {
      paramArrayOfByte1.printStackTrace();
    }
    return null;
  }
  
  public static String bytesToHex(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = new char[paramArrayOfByte.length * 2];
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      int j = paramArrayOfByte[i] & 0xFF;
      int k = i * 2;
      char[] arrayOfChar2 = hexArray;
      arrayOfChar1[k] = arrayOfChar2[(j >>> 4)];
      arrayOfChar1[(k + 1)] = arrayOfChar2[(j & 0xF)];
      i += 1;
    }
    return new String(arrayOfChar1);
  }
  
  public static byte[] decryptByPublicKey(byte[] paramArrayOfByte)
    throws Exception
  {
    Object localObject2 = new X509EncodedKeySpec(Base64.decode("MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRANw8wTC4Z66SZMepoSgK0rECAwEAAQ==".getBytes(), 0));
    if (Build.VERSION.SDK_INT >= 27) {
      localObject1 = KeyFactory.getInstance("RSA");
    } else {
      localObject1 = KeyFactory.getInstance("RSA", "BC");
    }
    Object localObject1 = ((KeyFactory)localObject1).generatePublic((KeySpec)localObject2);
    localObject2 = Cipher.getInstance("RSA/ECB/NoPadding");
    ((Cipher)localObject2).init(2, (Key)localObject1);
    return ((Cipher)localObject2).doFinal(paramArrayOfByte);
  }
  
  public static String getDesStr()
  {
    return desStr;
  }
  
  public static PostEncryption getInstance()
  {
    try
    {
      if ((!mDisablePostEncryption) && (mRsaUtils == null)) {
        mRsaUtils = new PostEncryption();
      }
      PostEncryption localPostEncryption = mRsaUtils;
      return localPostEncryption;
    }
    catch (Throwable localThrowable)
    {
      mRsaUtils = null;
      localThrowable.printStackTrace();
      mDisablePostEncryption = true;
    }
    return null;
  }
  
  public static byte[] hexToBytes(String paramString)
  {
    paramString = paramString.toCharArray();
    int m = paramString.length / 2;
    byte[] arrayOfByte = new byte[m];
    int i = 0;
    while (i < m)
    {
      int j = i * 2;
      int k = Character.digit(paramString[j], 16);
      k = Character.digit(paramString[(j + 1)], 16) | k << 4;
      j = k;
      if (k > 127) {
        j = k - 256;
      }
      arrayOfByte[i] = ((byte)j);
      i += 1;
    }
    return arrayOfByte;
  }
  
  public static void setDesStr(String paramString)
  {
    desStr = paramString;
  }
  
  public byte[] DESEncrypt(byte[] paramArrayOfByte)
    throws Exception
  {
    return this.mDESEncryptCipher.doFinal(paramArrayOfByte);
  }
  
  public String RSAEncrypt(byte[] paramArrayOfByte)
    throws Exception
  {
    return bytesToHex(this.mRSAEncryptCipher.doFinal(paramArrayOfByte));
  }
  
  public byte[] getRSABytes()
  {
    return rsaBytes;
  }
  
  public String getRSAKeyValue()
  {
    return keyValue;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\PostEncryption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */