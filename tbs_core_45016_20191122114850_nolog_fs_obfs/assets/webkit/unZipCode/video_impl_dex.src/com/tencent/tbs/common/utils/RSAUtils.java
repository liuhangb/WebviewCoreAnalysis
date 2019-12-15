package com.tencent.tbs.common.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Base64;
import com.tencent.common.utils.LogUtils;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class RSAUtils
{
  private static final String DEFALT_CIPHER_TRANSFORMATION = "RSA/ECB/NoPadding";
  private static final String ENCRYPT_ALGORITHM = "RSA";
  private static Key mWUPRSAPublicKey;
  
  public static byte[] encrypt(byte[] paramArrayOfByte, Key paramKey)
    throws Exception
  {
    return encrypt(paramArrayOfByte, paramKey, "RSA/ECB/NoPadding");
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
    throw new IllegalArgumentException("RSA encrpt meet invalide argument, check it");
  }
  
  public static byte[] encryptAESKey(byte[] paramArrayOfByte)
    throws Exception
  {
    return encrypt(paramArrayOfByte, getWUPRSAPublicKey());
  }
  
  private static Key getWUPRSAPublicKey()
    throws Exception
  {
    long l = System.currentTimeMillis();
    if (mWUPRSAPublicKey == null)
    {
      localObject = new X509EncodedKeySpec(Base64.decode("MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRALifjottoxSlsET0G46Z2OcCAwEAAQ==".getBytes(), 0));
      mWUPRSAPublicKey = KeyFactory.getInstance("RSA").generatePublic((KeySpec)localObject);
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("generate rsa key time=");
    ((StringBuilder)localObject).append(System.currentTimeMillis() - l);
    LogUtils.d("DebugGKEY", ((StringBuilder)localObject).toString());
    return mWUPRSAPublicKey;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\RSAUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */