package com.tencent.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ByteUtils
{
  public static final byte CHARACTER_ENCODING_ASCII = 0;
  public static final byte CHARACTER_ENCODING_GB18030 = 2;
  public static final byte CHARACTER_ENCODING_UTF8 = 1;
  
  public static void DWord2Byte(byte[] paramArrayOfByte, int paramInt, long paramLong)
  {
    paramArrayOfByte[paramInt] = ((byte)(int)(paramLong >> 24));
    paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(paramLong >> 16));
    paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(paramLong >> 8));
    paramArrayOfByte[(paramInt + 3)] = ((byte)(int)paramLong);
  }
  
  public static boolean EqualBytes(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if (paramArrayOfByte1 != null)
    {
      if (paramArrayOfByte2 == null) {
        return false;
      }
      if (paramArrayOfByte1.length != paramArrayOfByte2.length) {
        return false;
      }
      int i = 0;
      while (i < paramArrayOfByte2.length)
      {
        if (paramArrayOfByte1[i] != paramArrayOfByte2[i]) {
          return false;
        }
        i += 1;
      }
      return true;
    }
    return false;
  }
  
  public static void Word2Byte(byte[] paramArrayOfByte, int paramInt, short paramShort)
  {
    paramArrayOfByte[paramInt] = ((byte)(paramShort >> 8));
    paramArrayOfByte[(paramInt + 1)] = ((byte)paramShort);
  }
  
  private static boolean a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = 0;
    while (i < paramInt2)
    {
      int j = paramInt1 + i;
      if (j >= paramArrayOfByte.length) {
        break;
      }
      if ((paramArrayOfByte[j] & 0xC0) != 128) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  public static String byteToHexString(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0))
    {
      StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
      int i = 0;
      while (i < paramArrayOfByte.length)
      {
        if ((paramArrayOfByte[i] & 0xFF) < 16) {
          localStringBuffer.append("0");
        }
        localStringBuffer.append(Long.toString(paramArrayOfByte[i] & 0xFF, 16));
        i += 1;
      }
      return localStringBuffer.toString();
    }
    return null;
  }
  
  public static byte[] comByte(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if (paramArrayOfByte1 != null)
    {
      if (paramArrayOfByte1.length == 0) {
        return paramArrayOfByte2;
      }
      if (paramArrayOfByte2 != null)
      {
        if (paramArrayOfByte2.length == 0) {
          return paramArrayOfByte1;
        }
        byte[] arrayOfByte = new byte[paramArrayOfByte1.length + paramArrayOfByte2.length];
        int k = 0;
        int i = 0;
        int j;
        for (;;)
        {
          j = k;
          if (i >= paramArrayOfByte1.length) {
            break;
          }
          arrayOfByte[i] = paramArrayOfByte1[i];
          i += 1;
        }
        while (j < paramArrayOfByte2.length)
        {
          arrayOfByte[(paramArrayOfByte1.length + j)] = paramArrayOfByte2[j];
          j += 1;
        }
        return arrayOfByte;
      }
      return paramArrayOfByte1;
    }
    return paramArrayOfByte2;
  }
  
  public static int getHashUUID(String paramString)
  {
    return hashRawString(paramString);
  }
  
  public static byte guessCharacterEncoding(byte[] paramArrayOfByte)
  {
    int i = 0;
    int j = 0;
    while (i < paramArrayOfByte.length)
    {
      int k = paramArrayOfByte[i];
      if ((k & 0x80) == 0)
      {
        j = (byte)(j + 1);
        i += 1;
      }
      else if ((k & 0xE0) == 192)
      {
        if (!a(paramArrayOfByte, i + 1, 1)) {
          return 2;
        }
        i += 2;
      }
      else if ((k & 0xF0) == 224)
      {
        if (!a(paramArrayOfByte, i + 1, 2)) {
          return 2;
        }
        i += 3;
      }
      else if ((k & 0xF8) == 240)
      {
        if (!a(paramArrayOfByte, i + 1, 3)) {
          return 2;
        }
        i += 4;
      }
      else if ((k & 0xFC) == 248)
      {
        if (!a(paramArrayOfByte, i + 1, 4)) {
          return 2;
        }
        i += 5;
      }
      else if ((k & 0xC0) == 128)
      {
        i += 1;
      }
      else
      {
        return 2;
      }
    }
    if (j == paramArrayOfByte.length) {
      return 0;
    }
    return 1;
  }
  
  public static int hashRawString(String paramString)
  {
    try
    {
      byte[] arrayOfByte = paramString.getBytes("UTF-8");
      paramString = arrayOfByte;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      int k;
      int i;
      int j;
      for (;;) {}
    }
    paramString = paramString.getBytes();
    k = paramString.length;
    i = 0;
    j = 0;
    while (i < k)
    {
      j += paramString[i];
      j += (j << 10);
      j ^= j >>> 6;
      i += 1;
    }
    i = j + (j << 3);
    i = i >>> 11 ^ i;
    i += (i << 15);
    if (i == 0) {
      return 1;
    }
    return Math.abs(i);
  }
  
  public static byte[] hexStringToByte(String paramString)
  {
    byte[] arrayOfByte;
    int i;
    if ((paramString != null) && (!paramString.equals("")))
    {
      if (paramString.length() % 2 != 0) {
        return null;
      }
      arrayOfByte = new byte[paramString.length() / 2];
      i = 0;
    }
    try
    {
      while (i < paramString.length())
      {
        int k = i / 2;
        int j = i + 2;
        arrayOfByte[k] = ((byte)(Integer.parseInt(paramString.substring(i, j), 16) & 0xFF));
        i = j;
      }
      return arrayOfByte;
    }
    catch (NumberFormatException paramString) {}
    return null;
    return null;
  }
  
  public static boolean isAllZeroBytes(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null)
    {
      if (paramArrayOfByte.length <= 0) {
        return true;
      }
      int j = paramArrayOfByte.length;
      int i = 0;
      while (i < j)
      {
        if (paramArrayOfByte[i] != 0) {
          return false;
        }
        i += 1;
      }
      return true;
    }
    return true;
  }
  
  public static boolean isEqual(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if ((paramArrayOfByte1 == null) && (paramArrayOfByte2 == null)) {
      return true;
    }
    if ((paramArrayOfByte1 == null) && (paramArrayOfByte2 != null)) {
      return false;
    }
    if ((paramArrayOfByte1 != null) && (paramArrayOfByte2 == null)) {
      return false;
    }
    if (paramArrayOfByte1.length != paramArrayOfByte2.length) {
      return false;
    }
    int i = 0;
    while (i < paramArrayOfByte1.length)
    {
      if (paramArrayOfByte1[i] != paramArrayOfByte2[i]) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  public static byte[] mergeByteData(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if (paramArrayOfByte1 != null)
    {
      if (paramArrayOfByte1.length < 0) {
        return paramArrayOfByte2;
      }
      if (paramArrayOfByte2 != null)
      {
        if (paramArrayOfByte2.length < 0) {
          return paramArrayOfByte1;
        }
        byte[] arrayOfByte = new byte[paramArrayOfByte1.length + paramArrayOfByte2.length];
        System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramArrayOfByte1.length);
        System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, paramArrayOfByte1.length, paramArrayOfByte2.length);
        return arrayOfByte;
      }
      return paramArrayOfByte1;
    }
    return paramArrayOfByte2;
  }
  
  public static byte[] mergeListByteData(ArrayList<byte[]> paramArrayList)
  {
    byte[] arrayOfByte1 = null;
    if (paramArrayList == null) {
      return null;
    }
    int i = 0;
    int j = paramArrayList.size();
    while (i < j)
    {
      int k = i + 1;
      byte[] arrayOfByte2;
      if (k == j) {
        arrayOfByte2 = (byte[])paramArrayList.get(i);
      } else {
        arrayOfByte2 = mergeByteData((byte[])paramArrayList.get(i), (byte[])paramArrayList.get(k));
      }
      arrayOfByte1 = mergeByteData(arrayOfByte2, arrayOfByte1);
      i += 2;
    }
    return arrayOfByte1;
  }
  
  public static byte[] subByte(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfByte.length;
    if (paramInt1 >= 0)
    {
      if (paramInt1 + paramInt2 > i) {
        return null;
      }
      i = paramInt2;
      if (paramInt2 < 0) {
        i = paramArrayOfByte.length - paramInt1;
      }
      try
      {
        byte[] arrayOfByte = new byte[i];
        paramInt2 = 0;
        while (paramInt2 < i)
        {
          arrayOfByte[paramInt2] = paramArrayOfByte[(paramInt2 + paramInt1)];
          paramInt2 += 1;
        }
        return arrayOfByte;
      }
      catch (Exception paramArrayOfByte)
      {
        paramArrayOfByte.printStackTrace();
        return null;
      }
    }
    return null;
  }
  
  public static String toHexStr(byte paramByte)
  {
    int i = paramByte;
    if (paramByte < 0) {
      i = paramByte + 256;
    }
    paramByte = i / 16;
    i -= paramByte * 16;
    char c1;
    if (paramByte > 9) {
      c1 = (char)(paramByte - 10 + 65);
    } else {
      c1 = (char)(paramByte + 48);
    }
    char c2;
    if (i > 9) {
      c2 = (char)(i - 10 + 65);
    } else {
      c2 = (char)(i + 48);
    }
    Object localObject1 = new Character(c1);
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("");
    ((StringBuilder)localObject2).append(((Character)localObject1).toString());
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new Character(c2);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append((String)localObject1);
    localStringBuilder.append(((Character)localObject2).toString());
    return localStringBuilder.toString();
  }
  
  public static String toHexStr(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      localStringBuffer.append(toHexStr(paramArrayOfByte[i]));
      i += 1;
    }
    return localStringBuffer.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\ByteUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */