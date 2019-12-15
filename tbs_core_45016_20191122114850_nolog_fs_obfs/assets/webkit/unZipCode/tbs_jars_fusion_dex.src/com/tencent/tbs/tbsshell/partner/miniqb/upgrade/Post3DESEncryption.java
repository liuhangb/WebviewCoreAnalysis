package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.util.Base64;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

class Post3DESEncryption
{
  private static final String Algorithm = "DESede/ECB/PKCS5Padding";
  private static final String Publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcEQ3TCNWPBqgIiY7WQ/IqTOTTV2w8aZ/GPm68FK0fAJBemZKtYR3Li46VJ+Hwnor7ZpQnblGWPFaLv5JoPqvavgB0GInuhm+T+syPs1mw0uPLWaqwvZsCfoaIvUuxy5xHJgmWARrK4/9pHyDxRlZte0PCIoR1ko5B8lVVH1X1dQIDAQAB";
  private static final String RSA_NO_PADDING = "RSA/ECB/NoPadding";
  private static byte[] desBytes;
  private static String deskeys;
  protected static final char[] hexArray = "0123456789abcdef".toCharArray();
  private static String keyValue = "";
  private static Post3DESEncryption mRsaUtils = null;
  private Cipher mDESEncryptCipher = null;
  private Cipher mRSAEncryptCipher = null;
  
  private Post3DESEncryption()
    throws Exception
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(String.valueOf(new Random().nextInt(89999999) + 10000000));
    ((StringBuilder)localObject).append(String.valueOf(new Random().nextInt(89999999) + 10000000));
    ((StringBuilder)localObject).append(String.valueOf(new Random().nextInt(89999999) + 10000000));
    deskeys = ((StringBuilder)localObject).toString();
    localObject = "00000000";
    int i = 0;
    while (i < 12)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append((String)localObject);
      localStringBuilder.append(String.valueOf(new Random().nextInt(89999999) + 10000000));
      localObject = localStringBuilder.toString();
      i += 1;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(deskeys);
    desBytes = localStringBuilder.toString().getBytes();
    this.mRSAEncryptCipher = Cipher.getInstance("RSA/ECB/NoPadding");
    localObject = new X509EncodedKeySpec(Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcEQ3TCNWPBqgIiY7WQ/IqTOTTV2w8aZ/GPm68FK0fAJBemZKtYR3Li46VJ+Hwnor7ZpQnblGWPFaLv5JoPqvavgB0GInuhm+T+syPs1mw0uPLWaqwvZsCfoaIvUuxy5xHJgmWARrK4/9pHyDxRlZte0PCIoR1ko5B8lVVH1X1dQIDAQAB".getBytes(), 0));
    localObject = KeyFactory.getInstance("RSA").generatePublic((KeySpec)localObject);
    this.mRSAEncryptCipher.init(1, (Key)localObject);
    keyValue = bytesToHex(this.mRSAEncryptCipher.doFinal(desBytes));
    localObject = new DESedeKeySpec(deskeys.getBytes());
    localObject = SecretKeyFactory.getInstance("DESede").generateSecret((KeySpec)localObject);
    this.mDESEncryptCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
    this.mDESEncryptCipher.init(1, (Key)localObject);
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
  
  public static Post3DESEncryption getInstance()
  {
    try
    {
      if (mRsaUtils == null) {
        mRsaUtils = new Post3DESEncryption();
      }
      Post3DESEncryption localPost3DESEncryption = mRsaUtils;
      return localPost3DESEncryption;
    }
    catch (Exception localException)
    {
      mRsaUtils = null;
      localException.printStackTrace();
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
  
  public byte[] DESEncrypt(byte[] paramArrayOfByte)
    throws Exception
  {
    return this.mDESEncryptCipher.doFinal(paramArrayOfByte);
  }
  
  public byte[] DesDecrypt(byte[] paramArrayOfByte)
  {
    Object localObject = deskeys.getBytes();
    try
    {
      localObject = SecretKeyFactory.getInstance("DESede").generateSecret(new DESedeKeySpec((byte[])localObject));
      Cipher localCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
      localCipher.init(2, (Key)localObject);
      paramArrayOfByte = localCipher.doFinal(paramArrayOfByte);
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      paramArrayOfByte.printStackTrace();
    }
    return null;
  }
  
  public String RSAEncrypt(byte[] paramArrayOfByte)
    throws Exception
  {
    return bytesToHex(this.mRSAEncryptCipher.doFinal(paramArrayOfByte));
  }
  
  public byte[] getDESBytes()
  {
    return desBytes;
  }
  
  public String getRSAKeyValue()
  {
    return keyValue;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\Post3DESEncryption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */