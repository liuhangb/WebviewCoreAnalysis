package com.tencent.common.wup.security;

import android.text.TextUtils;
import com.tencent.common.utils.ByteUtils;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public class WupEncryptHelper
{
  public static final String DEFALT_RSA_CIPHER_TRANSFORMATION = "RSA/ECB/NoPadding";
  public static final String RSA_OAPE_CIPHER_TRANSFORMATION = "RSA/ECB/OAEPWithSHA1AndMGF1Padding";
  private static SecureRandom a = new SecureRandom();
  
  private static byte[] a(byte[] paramArrayOfByte1, Key paramKey, String paramString, byte[] paramArrayOfByte2)
    throws Exception
  {
    if ((paramArrayOfByte1 != null) && (paramKey != null) && (!TextUtils.isEmpty(paramString)))
    {
      paramString = Cipher.getInstance(paramString);
      if (paramArrayOfByte2 != null) {
        paramString.init(1, paramKey, new IvParameterSpec(paramArrayOfByte2));
      } else {
        paramString.init(1, paramKey);
      }
      return paramString.doFinal(paramArrayOfByte1);
    }
    throw new IllegalArgumentException("AES encrpt meet invalide argument, check it");
  }
  
  private static byte[] b(byte[] paramArrayOfByte1, Key paramKey, String paramString, byte[] paramArrayOfByte2)
    throws Exception
  {
    if ((paramArrayOfByte1 != null) && (paramKey != null) && (!TextUtils.isEmpty(paramString)))
    {
      paramString = Cipher.getInstance(paramString);
      if (paramArrayOfByte2 != null) {
        paramString.init(2, paramKey, new IvParameterSpec(paramArrayOfByte2));
      } else {
        paramString.init(2, paramKey);
      }
      return paramString.doFinal(paramArrayOfByte1);
    }
    throw new IllegalArgumentException("AES decrypt meet invalide argument, check it");
  }
  
  public static byte[] decryptWithCBC(byte[] paramArrayOfByte1, Key paramKey, byte[] paramArrayOfByte2)
    throws Exception
  {
    return b(paramArrayOfByte1, paramKey, "AES/CBC/PKCS7Padding", paramArrayOfByte2);
  }
  
  public static byte[] decryptWithECB(byte[] paramArrayOfByte, Key paramKey)
    throws Exception
  {
    return b(paramArrayOfByte, paramKey, "AES/ECB/PKCS7Padding", null);
  }
  
  public static byte[] doRsaEncrypt(byte[] paramArrayOfByte, Key paramKey, String paramString)
    throws Exception
  {
    if ((paramArrayOfByte != null) && (paramKey != null) && (!TextUtils.isEmpty(paramString))) {
      try
      {
        paramString = Cipher.getInstance(paramString);
        paramString.init(1, paramKey);
        paramArrayOfByte = paramString.doFinal(paramArrayOfByte);
        return paramArrayOfByte;
      }
      catch (Error paramArrayOfByte)
      {
        paramArrayOfByte.printStackTrace();
        throw new RuntimeException(paramArrayOfByte.getCause());
      }
    }
    throw new IllegalArgumentException("RSA encrpt meet invalide argument, check it");
  }
  
  public static byte[] encryptWithCBC(byte[] paramArrayOfByte1, Key paramKey, byte[] paramArrayOfByte2)
    throws Exception
  {
    return a(paramArrayOfByte1, paramKey, "AES/CBC/PKCS7Padding", paramArrayOfByte2);
  }
  
  public static byte[] encryptWithECB(byte[] paramArrayOfByte, Key paramKey)
    throws Exception
  {
    return a(paramArrayOfByte, paramKey, "AES/ECB/PKCS7Padding", null);
  }
  
  public static String generateInitVectorStr()
  {
    byte[] arrayOfByte = new byte[8];
    a.nextBytes(arrayOfByte);
    return ByteUtils.byteToHexString(arrayOfByte);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\security\WupEncryptHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */