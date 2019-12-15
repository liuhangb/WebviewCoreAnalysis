package com.tencent.mtt.base.utils;

import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.DesUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class EncryptUtil
{
  public static final String TAG = "EncryptUtil";
  
  public static byte[] decryption(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    try
    {
      paramArrayOfByte = new Cryptor().decrypt(paramArrayOfByte, paramInt, DesUtils.KEY);
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte) {}
    return null;
  }
  
  public static byte[] encryption(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    try
    {
      paramArrayOfByte = new Cryptor().encrypt(paramArrayOfByte, DesUtils.KEY);
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      paramArrayOfByte.printStackTrace();
    }
    return null;
  }
  
  public static byte[] getEncryptedBytes(String paramString1, String paramString2)
  {
    Object localObject = null;
    try
    {
      paramString2 = MessageDigest.getInstance(paramString2);
      paramString1 = new FileInputStream(paramString1);
      try
      {
        byte[] arrayOfByte = new byte[' '];
        for (;;)
        {
          int i = paramString1.read(arrayOfByte);
          if (i == -1) {
            break;
          }
          paramString2.update(arrayOfByte, 0, i);
        }
        paramString2 = paramString2.digest();
      }
      catch (Exception paramString2) {}
      paramString2.printStackTrace();
    }
    catch (Exception paramString2)
    {
      paramString1 = null;
    }
    paramString2 = (String)localObject;
    if (paramString1 != null) {
      try
      {
        paramString1.close();
        return paramString2;
      }
      catch (IOException paramString1)
      {
        paramString1.printStackTrace();
      }
    }
    return paramString2;
  }
  
  public static String getFileMD5(String paramString)
    throws OutOfMemoryError
  {
    return ByteUtils.byteToHexString(getFileMD5Bytes(paramString));
  }
  
  public static byte[] getFileMD5Bytes(String paramString)
  {
    return getEncryptedBytes(paramString, "MD5");
  }
  
  public static String getFileSHA(String paramString)
    throws OutOfMemoryError
  {
    return ByteUtils.byteToHexString(getFileSHABytes(paramString));
  }
  
  public static byte[] getFileSHABytes(String paramString)
  {
    return getEncryptedBytes(paramString, "SHA1");
  }
  
  public static String getStringMD5(String paramString)
    throws OutOfMemoryError
  {
    return ByteUtils.byteToHexString(getStringMD5Bytes(paramString));
  }
  
  public static byte[] getStringMD5Bytes(String paramString)
  {
    try
    {
      paramString = paramString.getBytes("UTF-8");
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString);
      paramString = localMessageDigest.digest();
      return paramString;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\EncryptUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */