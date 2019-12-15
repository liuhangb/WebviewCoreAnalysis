package com.tencent.common.utils;

import android.util.Base64;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.http.util.ByteArrayBuffer;

public class RSAUtils
{
  private static RSAPrivateKey a;
  
  static {}
  
  private static void a()
    throws Exception
  {
    try
    {
      Object localObject = new ByteArrayBuffer(633);
      ((ByteArrayBuffer)localObject).append(QBKeyStore.getKeyBytesById(14), 0, 100);
      ((ByteArrayBuffer)localObject).append(QBKeyStore.getKeyBytesById(15), 0, 100);
      ((ByteArrayBuffer)localObject).append(QBKeyStore.getKeyBytesById(16), 0, 100);
      ((ByteArrayBuffer)localObject).append(QBKeyStore.getKeyBytesById(17), 0, 100);
      ((ByteArrayBuffer)localObject).append(QBKeyStore.getKeyBytesById(18), 0, 100);
      ((ByteArrayBuffer)localObject).append(QBKeyStore.getKeyBytesById(19), 0, 100);
      ((ByteArrayBuffer)localObject).append(QBKeyStore.getKeyBytesById(20), 0, 33);
      localObject = new PKCS8EncodedKeySpec(((ByteArrayBuffer)localObject).toByteArray());
      a = (RSAPrivateKey)KeyFactory.getInstance("RSA").generatePrivate((KeySpec)localObject);
      return;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      for (;;) {}
    }
    catch (InvalidKeySpecException localInvalidKeySpecException)
    {
      for (;;) {}
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;) {}
    }
    throw new Exception("error 3");
    throw new Exception("error 2");
    throw new Exception("error 1");
  }
  
  private static byte[] a(RSAPrivateKey paramRSAPrivateKey, byte[] paramArrayOfByte)
    throws Exception
  {
    if (paramRSAPrivateKey != null) {}
    try
    {
      Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      localCipher.init(1, paramRSAPrivateKey);
      paramRSAPrivateKey = localCipher.doFinal(paramArrayOfByte);
      return paramRSAPrivateKey;
    }
    catch (NoSuchPaddingException paramRSAPrivateKey)
    {
      paramRSAPrivateKey.printStackTrace();
      return null;
      throw new Exception("error");
      throw new Exception("null error");
    }
    catch (NoSuchAlgorithmException paramRSAPrivateKey)
    {
      for (;;) {}
    }
    catch (InvalidKeyException paramRSAPrivateKey)
    {
      for (;;) {}
    }
    catch (IllegalBlockSizeException paramRSAPrivateKey)
    {
      for (;;) {}
    }
    catch (BadPaddingException paramRSAPrivateKey)
    {
      for (;;) {}
    }
    throw new Exception("error");
    throw new Exception("error");
    throw new Exception("error");
  }
  
  public static String encrypt(String paramString)
  {
    Object localObject = null;
    try
    {
      byte[] arrayOfByte = a(getPrivateKey(), paramString.getBytes("utf-8"));
      paramString = (String)localObject;
      if (arrayOfByte != null)
      {
        paramString = (String)localObject;
        if (arrayOfByte.length > 0) {
          paramString = new String(Base64.encode(arrayOfByte, 0), "utf-8");
        }
      }
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.getMessage();
    }
    return null;
  }
  
  public static RSAPrivateKey getPrivateKey()
  {
    return a;
  }
  
  public static void loadKey()
  {
    try
    {
      a();
      return;
    }
    catch (Exception localException)
    {
      localException.getMessage();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\RSAUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */