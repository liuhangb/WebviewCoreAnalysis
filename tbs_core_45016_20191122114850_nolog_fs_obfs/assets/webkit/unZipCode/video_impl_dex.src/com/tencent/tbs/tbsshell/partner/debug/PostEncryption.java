package com.tencent.tbs.tbsshell.partner.debug;

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
  private static final String Publickey = "MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRAMFIbyQERXfyiD3adAXRitUCAwEAAQ==";
  private static final String RSA_NO_PADDING = "RSA/ECB/NoPadding";
  protected static final char[] hexArray = "0123456789abcdef".toCharArray();
  private static String keyValue = "";
  private static PostEncryption mRsaUtils;
  private static byte[] rsaBytes;
  private static final String sdkPublickey = "MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRAMRB/Q0hTCD+XtnQhpQJefUCAwEAAQ==";
  private Cipher mDESEncryptCipher = null;
  private Cipher mRSAEncryptCipher = null;
  private boolean mUseSdkPublicKey = false;
  
  public PostEncryption()
    throws Exception
  {
    this(false);
  }
  
  public PostEncryption(boolean paramBoolean)
    throws Exception
  {
    this.mUseSdkPublicKey = paramBoolean;
    int i = new Random().nextInt(89999999);
    int j = new Random().nextInt(89999999);
    Object localObject1 = String.valueOf(i + 10000000);
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(String.valueOf(j + 10000000));
    rsaBytes = ((StringBuilder)localObject2).toString().getBytes();
    this.mRSAEncryptCipher = Cipher.getInstance("RSA/ECB/NoPadding");
    localObject2 = new X509EncodedKeySpec(Base64.decode(getPublickey().getBytes(), 0));
    localObject2 = KeyFactory.getInstance("RSA").generatePublic((KeySpec)localObject2);
    this.mRSAEncryptCipher.init(1, (Key)localObject2);
    keyValue = bytesToHex(this.mRSAEncryptCipher.doFinal(rsaBytes));
    localObject1 = new DESKeySpec(((String)localObject1).getBytes());
    localObject1 = SecretKeyFactory.getInstance("DES").generateSecret((KeySpec)localObject1);
    this.mDESEncryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    this.mDESEncryptCipher.init(1, (Key)localObject1);
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
  
  public static PostEncryption getInstance()
  {
    try
    {
      if (mRsaUtils == null) {
        mRsaUtils = new PostEncryption();
      }
      PostEncryption localPostEncryption = mRsaUtils;
      return localPostEncryption;
    }
    catch (Exception localException)
    {
      mRsaUtils = null;
      localException.printStackTrace();
    }
    return null;
  }
  
  private String getPublickey()
  {
    if (this.mUseSdkPublicKey) {
      return "MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRAMRB/Q0hTCD+XtnQhpQJefUCAwEAAQ==";
    }
    return "MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRAMFIbyQERXfyiD3adAXRitUCAwEAAQ==";
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\debug\PostEncryption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */