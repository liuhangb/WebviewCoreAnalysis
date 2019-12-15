package com.tencent.smtt.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class c
{
  private static String jdField_a_of_type_JavaLangString = "QabC-+50";
  private static byte[] jdField_a_of_type_ArrayOfByte = { 1, 2, 3, 4, 5, 6, 7, 8 };
  private Cipher jdField_a_of_type_JavaxCryptoCipher = null;
  private Cipher b = null;
  
  public c()
    throws Exception
  {
    Key localKey = a(jdField_a_of_type_JavaLangString.getBytes("UTF-8"));
    this.jdField_a_of_type_JavaxCryptoCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    this.jdField_a_of_type_JavaxCryptoCipher.init(1, localKey);
    this.b = Cipher.getInstance("DES/ECB/PKCS5Padding");
    this.b.init(2, localKey);
  }
  
  private Key a(byte[] paramArrayOfByte)
    throws Exception
  {
    byte[] arrayOfByte = new byte[8];
    int i = 0;
    while ((i < paramArrayOfByte.length) && (i < arrayOfByte.length))
    {
      arrayOfByte[i] = paramArrayOfByte[i];
      i += 1;
    }
    paramArrayOfByte = new SecretKeySpec(arrayOfByte, "DES");
    i = paramArrayOfByte.getEncoded().length;
    return paramArrayOfByte;
  }
  
  public byte[] a(byte[] paramArrayOfByte)
    throws Exception
  {
    return this.jdField_a_of_type_JavaxCryptoCipher.doFinal(paramArrayOfByte);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */