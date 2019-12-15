package com.tencent.mtt.base.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Base64;
import com.tencent.basesupport.FLogger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class RSAUtils
{
  private static Key a;
  
  private static Key a()
    throws Exception
  {
    long l = System.currentTimeMillis();
    if (a == null)
    {
      localObject = new X509EncodedKeySpec(Base64.decode("MCwwDQYJKoZIhvcNAQEBBQADGwAwGAIRALifjottoxSlsET0G46Z2OcCAwEAAQ==".getBytes(), 0));
      a = KeyFactory.getInstance("RSA").generatePublic((KeySpec)localObject);
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("generate rsa key time=");
    ((StringBuilder)localObject).append(System.currentTimeMillis() - l);
    FLogger.d("DebugGKEY", ((StringBuilder)localObject).toString());
    return a;
  }
  
  public static byte[] encrypt(byte[] paramArrayOfByte, Key paramKey)
    throws Exception
  {
    return encrypt(paramArrayOfByte, paramKey, "RSA/ECB/NoPadding");
  }
  
  @SuppressLint({"TrulyRandom"})
  public static byte[] encrypt(byte[] paramArrayOfByte, Key paramKey, String paramString)
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
  
  public static byte[] encryptAESKey(byte[] paramArrayOfByte)
    throws Exception
  {
    return encrypt(paramArrayOfByte, a());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\RSAUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */