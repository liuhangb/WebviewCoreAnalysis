package com.tencent.smtt.downloader.utils;

import android.util.Base64;
import java.security.KeyFactory;
import java.security.Provider;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.Cipher;

public class f
{
  private static f jdField_a_of_type_ComTencentSmttDownloaderUtilsF;
  private static final char[] jdField_a_of_type_ArrayOfChar = "0123456789abcdef".toCharArray();
  private String jdField_a_of_type_JavaLangString;
  private String b;
  private String c;
  
  private f()
  {
    int i = new Random().nextInt(89999999);
    int j = new Random().nextInt(89999999);
    this.c = String.valueOf(i + 10000000);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.c);
    localStringBuilder.append(String.valueOf(j + 10000000));
    this.jdField_a_of_type_JavaLangString = localStringBuilder.toString();
  }
  
  public static f a()
  {
    try
    {
      if (jdField_a_of_type_ComTencentSmttDownloaderUtilsF == null) {
        jdField_a_of_type_ComTencentSmttDownloaderUtilsF = new f();
      }
      f localf = jdField_a_of_type_ComTencentSmttDownloaderUtilsF;
      return localf;
    }
    finally {}
  }
  
  private String a(byte[] paramArrayOfByte)
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
  
  public String a()
    throws Exception
  {
    if (this.b == null)
    {
      byte[] arrayOfByte = this.jdField_a_of_type_JavaLangString.getBytes();
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
          a();
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
      this.b = a(((Cipher)localObject).doFinal(arrayOfByte));
    }
    return this.b;
  }
  
  public void a()
    throws Exception
  {
    Security.addProvider((Provider)Class.forName("com.android.org.bouncycastle.jce.provider.BouncyCastleProvider", true, ClassLoader.getSystemClassLoader()).newInstance());
  }
  
  public byte[] a(byte[] paramArrayOfByte)
    throws Exception
  {
    return c.a(this.c.getBytes(), paramArrayOfByte, 1);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\utils\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */