package com.tencent.smtt.downloader.utils;

import android.util.Base64;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class e
{
  private static e jdField_a_of_type_ComTencentSmttDownloaderUtilsE = null;
  private static String jdField_a_of_type_JavaLangString = "";
  private static byte[] jdField_a_of_type_ArrayOfByte;
  protected static final char[] a;
  private static String jdField_b_of_type_JavaLangString;
  private Cipher jdField_a_of_type_JavaxCryptoCipher = null;
  private Cipher jdField_b_of_type_JavaxCryptoCipher = null;
  
  static
  {
    jdField_a_of_type_ArrayOfChar = "0123456789abcdef".toCharArray();
  }
  
  private e()
    throws Exception
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(String.valueOf(new Random().nextInt(89999999) + 10000000));
    ((StringBuilder)localObject).append(String.valueOf(new Random().nextInt(89999999) + 10000000));
    ((StringBuilder)localObject).append(String.valueOf(new Random().nextInt(89999999) + 10000000));
    jdField_b_of_type_JavaLangString = ((StringBuilder)localObject).toString();
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
    localStringBuilder.append(jdField_b_of_type_JavaLangString);
    jdField_a_of_type_ArrayOfByte = localStringBuilder.toString().getBytes();
    this.jdField_a_of_type_JavaxCryptoCipher = Cipher.getInstance("RSA/ECB/NoPadding");
    localObject = new X509EncodedKeySpec(Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcEQ3TCNWPBqgIiY7WQ/IqTOTTV2w8aZ/GPm68FK0fAJBemZKtYR3Li46VJ+Hwnor7ZpQnblGWPFaLv5JoPqvavgB0GInuhm+T+syPs1mw0uPLWaqwvZsCfoaIvUuxy5xHJgmWARrK4/9pHyDxRlZte0PCIoR1ko5B8lVVH1X1dQIDAQAB".getBytes(), 0));
    localObject = KeyFactory.getInstance("RSA").generatePublic((KeySpec)localObject);
    this.jdField_a_of_type_JavaxCryptoCipher.init(1, (Key)localObject);
    jdField_a_of_type_JavaLangString = a(this.jdField_a_of_type_JavaxCryptoCipher.doFinal(jdField_a_of_type_ArrayOfByte));
    localObject = new DESedeKeySpec(jdField_b_of_type_JavaLangString.getBytes());
    localObject = SecretKeyFactory.getInstance("DESede").generateSecret((KeySpec)localObject);
    this.jdField_b_of_type_JavaxCryptoCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
    this.jdField_b_of_type_JavaxCryptoCipher.init(1, (Key)localObject);
  }
  
  public static e a()
  {
    try
    {
      if (jdField_a_of_type_ComTencentSmttDownloaderUtilsE == null) {
        jdField_a_of_type_ComTencentSmttDownloaderUtilsE = new e();
      }
      e locale = jdField_a_of_type_ComTencentSmttDownloaderUtilsE;
      return locale;
    }
    catch (Exception localException)
    {
      jdField_a_of_type_ComTencentSmttDownloaderUtilsE = null;
      localException.printStackTrace();
    }
    return null;
  }
  
  public static String a(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = new char[paramArrayOfByte.length * 2];
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      int j = paramArrayOfByte[i] & 0xFF;
      int k = i * 2;
      char[] arrayOfChar2 = jdField_a_of_type_ArrayOfChar;
      arrayOfChar1[k] = arrayOfChar2[(j >>> 4)];
      arrayOfChar1[(k + 1)] = arrayOfChar2[(j & 0xF)];
      i += 1;
    }
    return new String(arrayOfChar1);
  }
  
  public static String b()
  {
    return jdField_b_of_type_JavaLangString;
  }
  
  public String a()
  {
    return jdField_a_of_type_JavaLangString;
  }
  
  public byte[] a(byte[] paramArrayOfByte)
    throws Exception
  {
    return this.jdField_b_of_type_JavaxCryptoCipher.doFinal(paramArrayOfByte);
  }
  
  public byte[] b(byte[] paramArrayOfByte)
  {
    Object localObject = jdField_b_of_type_JavaLangString.getBytes();
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
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\utils\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */