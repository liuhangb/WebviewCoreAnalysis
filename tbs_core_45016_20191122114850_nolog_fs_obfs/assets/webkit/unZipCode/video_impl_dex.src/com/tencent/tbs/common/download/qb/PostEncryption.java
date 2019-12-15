package com.tencent.tbs.common.download.qb;

import android.util.Base64;
import java.security.KeyFactory;
import java.security.Provider;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.Cipher;

public class PostEncryption
{
  private static final char[] HEXARRAY = "0123456789abcdef".toCharArray();
  private static final String PUBLICKEY = "MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRAMRB/Q0hTCD+XtnQhpQJefUCAwEAAQ==";
  private static final String RSA_NO_PADDING = "RSA/ECB/NoPadding";
  private static PostEncryption mInstance;
  private String mDesKeyStr;
  private String mRsaKeyStr;
  private String mRsaKeyStrTemp;
  
  private PostEncryption()
  {
    int i = new Random().nextInt(89999999);
    int j = new Random().nextInt(89999999);
    this.mDesKeyStr = String.valueOf(i + 10000000);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mDesKeyStr);
    localStringBuilder.append(String.valueOf(j + 10000000));
    this.mRsaKeyStrTemp = localStringBuilder.toString();
  }
  
  private String bytesToHex(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = new char[paramArrayOfByte.length * 2];
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      int j = paramArrayOfByte[i] & 0xFF;
      int k = i * 2;
      char[] arrayOfChar2 = HEXARRAY;
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
      if (mInstance == null) {
        mInstance = new PostEncryption();
      }
      PostEncryption localPostEncryption = mInstance;
      return localPostEncryption;
    }
    finally {}
  }
  
  public byte[] DESDecrypt(byte[] paramArrayOfByte)
    throws Exception
  {
    return DesUtils.DesEncrypt(this.mDesKeyStr.getBytes(), paramArrayOfByte, 0);
  }
  
  public byte[] DESEncrypt(byte[] paramArrayOfByte)
    throws Exception
  {
    return DesUtils.DesEncrypt(this.mDesKeyStr.getBytes(), paramArrayOfByte, 1);
  }
  
  public String RSAEncode(String paramString)
    throws Exception
  {
    byte[] arrayOfByte = paramString.getBytes();
    for (;;)
    {
      try
      {
        paramString = Cipher.getInstance("RSA/ECB/NoPadding");
      }
      catch (Exception paramString)
      {
        X509EncodedKeySpec localX509EncodedKeySpec;
        continue;
      }
      try
      {
        addBouncyCastleProvider();
        paramString = Cipher.getInstance("RSA/ECB/NoPadding");
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        paramString = null;
      }
    }
    localX509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode("MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRAMRB/Q0hTCD+XtnQhpQJefUCAwEAAQ==".getBytes(), 0));
    paramString.init(1, KeyFactory.getInstance("RSA").generatePublic(localX509EncodedKeySpec));
    return bytesToHex(paramString.doFinal(arrayOfByte));
  }
  
  public void addBouncyCastleProvider()
    throws Exception
  {
    Security.addProvider((Provider)Class.forName("com.android.org.bouncycastle.jce.provider.BouncyCastleProvider", true, ClassLoader.getSystemClassLoader()).newInstance());
  }
  
  public String initRSAKey()
    throws Exception
  {
    if (this.mRsaKeyStr == null)
    {
      byte[] arrayOfByte = this.mRsaKeyStrTemp.getBytes();
      Object localObject = null;
      for (;;)
      {
        try
        {
          localCipher = Cipher.getInstance("RSA/ECB/NoPadding");
          localObject = localCipher;
        }
        catch (Exception localException2)
        {
          Cipher localCipher;
          X509EncodedKeySpec localX509EncodedKeySpec;
          continue;
        }
        try
        {
          addBouncyCastleProvider();
          localCipher = Cipher.getInstance("RSA/ECB/NoPadding");
          localObject = localCipher;
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
        }
      }
      localX509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode("MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRAMRB/Q0hTCD+XtnQhpQJefUCAwEAAQ==".getBytes(), 0));
      ((Cipher)localObject).init(1, KeyFactory.getInstance("RSA").generatePublic(localX509EncodedKeySpec));
      this.mRsaKeyStr = bytesToHex(((Cipher)localObject).doFinal(arrayOfByte));
    }
    return this.mRsaKeyStr;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\PostEncryption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */