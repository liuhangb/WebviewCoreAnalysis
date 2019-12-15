package com.tencent.tbs.common.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.LogUtils;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils
{
  private static final String DEFALT_CIPHER_TRANSFORMATION = "AES/ECB/PKCS7Padding";
  private static final String ENCRYPT_ALGORITHM = "AES";
  
  public static byte[] decrypt(byte[] paramArrayOfByte, Key paramKey)
    throws Exception
  {
    return decrypt(paramArrayOfByte, paramKey, "AES/ECB/PKCS7Padding");
  }
  
  public static byte[] decrypt(byte[] paramArrayOfByte, Key paramKey, String paramString)
    throws Exception
  {
    if ((paramArrayOfByte != null) && (paramKey != null) && (!TextUtils.isEmpty(paramString)))
    {
      paramString = Cipher.getInstance(paramString);
      paramString.init(2, paramKey);
      return paramString.doFinal(paramArrayOfByte);
    }
    throw new IllegalArgumentException("AES decrypt meet invalide argument, check it");
  }
  
  public static byte[] encrypt(byte[] paramArrayOfByte, Key paramKey)
    throws Exception
  {
    return encrypt(paramArrayOfByte, paramKey, "AES/ECB/PKCS7Padding");
  }
  
  @SuppressLint({"TrulyRandom"})
  public static byte[] encrypt(byte[] paramArrayOfByte, Key paramKey, String paramString)
    throws Exception
  {
    if ((paramArrayOfByte != null) && (paramKey != null) && (!TextUtils.isEmpty(paramString)))
    {
      paramString = Cipher.getInstance(paramString);
      paramString.init(1, paramKey);
      return paramString.doFinal(paramArrayOfByte);
    }
    throw new IllegalArgumentException("AES encrpt meet invalide argument, check it");
  }
  
  public static Key generateWupKey()
    throws NoSuchAlgorithmException
  {
    long l = System.currentTimeMillis();
    Object localObject1 = new Random(l);
    Object localObject2 = new byte[8];
    byte[] arrayOfByte = new byte[8];
    ((Random)localObject1).nextBytes((byte[])localObject2);
    ((Random)localObject1).nextBytes(arrayOfByte);
    localObject1 = new SecretKeySpec(ByteUtils.mergeByteData((byte[])localObject2, arrayOfByte), "AES");
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("generate aes key time=");
    ((StringBuilder)localObject2).append(System.currentTimeMillis() - l);
    LogUtils.d("DebugGKEY", ((StringBuilder)localObject2).toString());
    return (Key)localObject1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\AESUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */