package com.tencent.tbs.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class Cryptor
{
  public static final int QUOTIENT = 79764919;
  public static final int SALT_LEN = 2;
  public static final int ZERO_LEN = 7;
  private int contextStart;
  private int crypt;
  private boolean header = true;
  private byte[] key;
  private byte[] out;
  private int padding;
  private byte[] plain;
  private int pos;
  private int preCrypt;
  private byte[] prePlain;
  private Random random = new Random();
  
  public static int CRC32Hash(byte[] paramArrayOfByte)
  {
    int n = paramArrayOfByte.length;
    int j = 0;
    int i = -1;
    while (j < n)
    {
      int k = paramArrayOfByte[j];
      int m = 0;
      while (m < 8)
      {
        if ((k ^ i) >>> 31 == 1) {
          i = i << 1 ^ 0x4C11DB7;
        } else {
          i <<= 1;
        }
        k = (byte)(k << 1);
        m += 1;
      }
      j += 1;
    }
    return i ^ 0xFFFFFFFF;
  }
  
  public static byte[] MD5Hash(byte[] paramArrayOfByte, int paramInt)
  {
    return new byte[2];
  }
  
  public static int _4bytesDecryptAFrame(long paramLong, byte[] paramArrayOfByte)
  {
    short[] arrayOfShort1 = new short[2];
    arrayOfShort1[0] = ((short)(int)(0xFFFF & paramLong));
    arrayOfShort1[1] = ((short)(int)(paramLong >> 16));
    short[] arrayOfShort2 = new short[4];
    arrayOfShort2[0] = ((short)(paramArrayOfByte[1] << 8 | paramArrayOfByte[0] & 0xFF));
    arrayOfShort2[1] = ((short)(paramArrayOfByte[3] << 8 | paramArrayOfByte[2] & 0xFF));
    int k = paramArrayOfByte[5];
    arrayOfShort2[2] = ((short)(paramArrayOfByte[4] & 0xFF | k << 8));
    arrayOfShort2[3] = ((short)(paramArrayOfByte[7] << 8 | paramArrayOfByte[6] & 0xFF));
    int i = arrayOfShort1[0];
    int j = arrayOfShort1[1];
    k = (short)412640;
    int n;
    for (int m = 32;; m = n)
    {
      n = (short)(m - 1);
      if (m <= 0) {
        break;
      }
      j = (short)(j - ((i << 4) + arrayOfShort2[2] ^ i + k ^ (i >> 5) + arrayOfShort2[3]));
      i = (short)(i - ((j << 4) + arrayOfShort2[0] ^ j + k ^ (j >> 5) + arrayOfShort2[1]));
      k = (short)(k - 12895);
    }
    arrayOfShort1[0] = i;
    arrayOfShort1[1] = j;
    return arrayOfShort1[1] << 16 | arrayOfShort1[0] & 0xFFFF;
  }
  
  public static byte[] _4bytesEncryptAFrame(int paramInt, byte[] paramArrayOfByte)
  {
    short[] arrayOfShort1 = new short[2];
    arrayOfShort1[0] = ((short)(0xFFFF & paramInt));
    arrayOfShort1[1] = ((short)(paramInt >>> 16));
    short[] arrayOfShort2 = new short[4];
    arrayOfShort2[0] = ((short)(paramArrayOfByte[1] << 8 | paramArrayOfByte[0] & 0xFF));
    arrayOfShort2[1] = ((short)(paramArrayOfByte[3] << 8 | paramArrayOfByte[2] & 0xFF));
    arrayOfShort2[2] = ((short)(paramArrayOfByte[5] << 8 | paramArrayOfByte[4] & 0xFF));
    paramInt = paramArrayOfByte[7];
    arrayOfShort2[3] = ((short)(paramArrayOfByte[6] & 0xFF | paramInt << 8));
    paramInt = arrayOfShort1[0];
    int i = arrayOfShort1[1];
    int j = 32;
    int k = 0;
    for (;;)
    {
      int m = (short)(j - 1);
      if (j <= 0) {
        break;
      }
      k = (short)(k + 12895);
      paramInt = (short)(paramInt + ((i << 4) + arrayOfShort2[0] ^ i + k ^ (i >> 5) + arrayOfShort2[1]));
      i = (short)(i + ((paramInt << 4) + arrayOfShort2[2] ^ paramInt + k ^ (paramInt >> 5) + arrayOfShort2[3]));
      j = m;
    }
    return new byte[] { (byte)(i >> 8), (byte)(i & 0xFF), (byte)(paramInt >> 8), (byte)(paramInt & 0xFF) };
  }
  
  private byte[] decipher(byte[] paramArrayOfByte)
  {
    return decipher(paramArrayOfByte, 0);
  }
  
  private byte[] decipher(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 16;
    for (;;)
    {
      long l2;
      long l1;
      long l4;
      long l5;
      long l6;
      long l7;
      long l3;
      try
      {
        l2 = getUnsignedInt(paramArrayOfByte, paramInt, 4);
        l1 = getUnsignedInt(paramArrayOfByte, paramInt + 4, 4);
        l4 = getUnsignedInt(this.key, 0, 4);
        l5 = getUnsignedInt(this.key, 4, 4);
        l6 = getUnsignedInt(this.key, 8, 4);
        l7 = getUnsignedInt(this.key, 12, 4);
        l3 = 3816266640L;
        paramInt = i;
      }
      catch (IOException paramArrayOfByte)
      {
        DataOutputStream localDataOutputStream;
        continue;
      }
      paramArrayOfByte = new ByteArrayOutputStream(8);
      localDataOutputStream = new DataOutputStream(paramArrayOfByte);
      localDataOutputStream.writeInt((int)l2);
      localDataOutputStream.writeInt((int)l1);
      localDataOutputStream.close();
      paramArrayOfByte = paramArrayOfByte.toByteArray();
      return paramArrayOfByte;
      return null;
      while (paramInt > 0)
      {
        l1 = l1 - ((l2 << 4) + l6 ^ l2 + l3 ^ (l2 >>> 5) + l7) & 0xFFFFFFFF;
        l2 = l2 - ((l1 << 4) + l4 ^ l1 + l3 ^ (l1 >>> 5) + l5) & 0xFFFFFFFF;
        l3 = l3 - 2654435769L & 0xFFFFFFFF;
        paramInt -= 1;
      }
    }
  }
  
  private boolean decrypt8Bytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i;
    for (this.pos = 0;; this.pos = (i + 1))
    {
      i = this.pos;
      if (i >= 8) {
        break;
      }
      if (this.contextStart + i >= paramInt2) {
        return true;
      }
      byte[] arrayOfByte = this.prePlain;
      arrayOfByte[i] = ((byte)(arrayOfByte[i] ^ paramArrayOfByte[(this.crypt + paramInt1 + i)]));
    }
    this.prePlain = decipher(this.prePlain);
    if (this.prePlain == null) {
      return false;
    }
    this.contextStart += 8;
    this.crypt += 8;
    this.pos = 0;
    return true;
  }
  
  private byte[] encipher(byte[] paramArrayOfByte)
  {
    int i = 16;
    for (;;)
    {
      long l1;
      long l2;
      long l4;
      long l5;
      long l6;
      long l7;
      long l3;
      try
      {
        l1 = getUnsignedInt(paramArrayOfByte, 0, 4);
        l2 = getUnsignedInt(paramArrayOfByte, 4, 4);
        l4 = getUnsignedInt(this.key, 0, 4);
        l5 = getUnsignedInt(this.key, 4, 4);
        l6 = getUnsignedInt(this.key, 8, 4);
        l7 = getUnsignedInt(this.key, 12, 4);
        l3 = 0L;
      }
      catch (IOException paramArrayOfByte)
      {
        DataOutputStream localDataOutputStream;
        continue;
      }
      paramArrayOfByte = new ByteArrayOutputStream(8);
      localDataOutputStream = new DataOutputStream(paramArrayOfByte);
      localDataOutputStream.writeInt((int)l1);
      localDataOutputStream.writeInt((int)l2);
      localDataOutputStream.close();
      paramArrayOfByte = paramArrayOfByte.toByteArray();
      return paramArrayOfByte;
      return null;
      while (i > 0)
      {
        l3 = l3 + 2654435769L & 0xFFFFFFFF;
        l1 = l1 + ((l2 << 4) + l4 ^ l2 + l3 ^ (l2 >>> 5) + l5) & 0xFFFFFFFF;
        l2 = l2 + ((l1 << 4) + l6 ^ l1 + l3 ^ (l1 >>> 5) + l7) & 0xFFFFFFFF;
        i -= 1;
      }
    }
  }
  
  private void encrypt8Bytes()
  {
    byte[] arrayOfByte;
    for (this.pos = 0;; this.pos += 1)
    {
      i = this.pos;
      if (i >= 8) {
        break;
      }
      if (this.header)
      {
        arrayOfByte = this.plain;
        arrayOfByte[i] = ((byte)(arrayOfByte[i] ^ this.prePlain[i]));
      }
      else
      {
        arrayOfByte = this.plain;
        arrayOfByte[i] = ((byte)(arrayOfByte[i] ^ this.out[(this.preCrypt + i)]));
      }
    }
    System.arraycopy(encipher(this.plain), 0, this.out, this.crypt, 8);
    for (this.pos = 0;; this.pos = (i + 1))
    {
      i = this.pos;
      if (i >= 8) {
        break;
      }
      arrayOfByte = this.out;
      int j = this.crypt + i;
      arrayOfByte[j] = ((byte)(arrayOfByte[j] ^ this.prePlain[i]));
    }
    System.arraycopy(this.plain, 0, this.prePlain, 0, 8);
    int i = this.crypt;
    this.preCrypt = i;
    this.crypt = (i + 8);
    this.pos = 0;
    this.header = false;
  }
  
  public static long getUnsignedInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramInt2 > 8) {
      paramInt2 = paramInt1 + 8;
    } else {
      paramInt2 += paramInt1;
    }
    long l = 0L;
    while (paramInt1 < paramInt2)
    {
      l = l << 8 | paramArrayOfByte[paramInt1] & 0xFF;
      paramInt1 += 1;
    }
    return 0xFFFFFFFF & l | l >>> 32;
  }
  
  private int rand()
  {
    return this.random.nextInt();
  }
  
  public byte[] decrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2)
  {
    int k = 0;
    this.preCrypt = 0;
    this.crypt = 0;
    this.key = paramArrayOfByte2;
    paramArrayOfByte2 = new byte[paramInt1 + 8];
    if (paramInt2 % 8 == 0)
    {
      if (paramInt2 < 16) {
        return null;
      }
      this.prePlain = decipher(paramArrayOfByte1, paramInt1);
      byte[] arrayOfByte = this.prePlain;
      if (arrayOfByte != null) {
        this.pos = (arrayOfByte[0] & 0x7);
      }
      int m = paramInt2 - this.pos - 10;
      if (m < 0) {
        return null;
      }
      int i = paramInt1;
      while (i < paramArrayOfByte2.length)
      {
        paramArrayOfByte2[i] = 0;
        i += 1;
      }
      this.out = new byte[m];
      this.preCrypt = 0;
      this.crypt = 8;
      this.contextStart = 8;
      this.pos += 1;
      this.padding = 1;
      int n;
      int j;
      for (;;)
      {
        n = this.padding;
        j = k;
        i = m;
        arrayOfByte = paramArrayOfByte2;
        if (n > 2) {
          break;
        }
        i = this.pos;
        if (i < 8)
        {
          this.pos = (i + 1);
          this.padding = (n + 1);
        }
        if (this.pos == 8)
        {
          if (!decrypt8Bytes(paramArrayOfByte1, paramInt1, paramInt2)) {
            return null;
          }
          paramArrayOfByte2 = paramArrayOfByte1;
        }
      }
      while (i != 0)
      {
        n = this.pos;
        m = j;
        k = i;
        if (n < 8)
        {
          this.out[j] = ((byte)(arrayOfByte[(this.preCrypt + paramInt1 + n)] ^ this.prePlain[n]));
          m = j + 1;
          k = i - 1;
          this.pos = (n + 1);
        }
        j = m;
        i = k;
        if (this.pos == 8)
        {
          this.preCrypt = (this.crypt - 8);
          if (!decrypt8Bytes(paramArrayOfByte1, paramInt1, paramInt2)) {
            return null;
          }
          arrayOfByte = paramArrayOfByte1;
          j = m;
          i = k;
        }
      }
      this.padding = 1;
      while (this.padding < 8)
      {
        i = this.pos;
        if (i < 8)
        {
          if ((arrayOfByte[(this.preCrypt + paramInt1 + i)] ^ this.prePlain[i]) != 0) {
            return null;
          }
          this.pos = (i + 1);
        }
        paramArrayOfByte2 = arrayOfByte;
        if (this.pos == 8)
        {
          this.preCrypt = this.crypt;
          if (!decrypt8Bytes(paramArrayOfByte1, paramInt1, paramInt2)) {
            return null;
          }
          paramArrayOfByte2 = paramArrayOfByte1;
        }
        this.padding += 1;
        arrayOfByte = paramArrayOfByte2;
      }
      return this.out;
    }
    return null;
  }
  
  public byte[] decrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return decrypt(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
  }
  
  public byte[] encrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2)
  {
    this.plain = new byte[8];
    this.prePlain = new byte[8];
    this.pos = 1;
    this.padding = 0;
    this.preCrypt = 0;
    this.crypt = 0;
    this.key = paramArrayOfByte2;
    this.header = true;
    this.pos = ((paramInt2 + 10) % 8);
    int i = this.pos;
    if (i != 0) {
      this.pos = (8 - i);
    }
    this.out = new byte[this.pos + paramInt2 + 10];
    this.plain[0] = ((byte)(rand() & 0xF8 | this.pos));
    i = 1;
    int j;
    for (;;)
    {
      j = this.pos;
      if (i > j) {
        break;
      }
      this.plain[i] = ((byte)(rand() & 0xFF));
      i += 1;
    }
    this.pos = (j + 1);
    i = 0;
    while (i < 8)
    {
      this.prePlain[i] = 0;
      i += 1;
    }
    this.padding = 1;
    for (;;)
    {
      i = paramInt1;
      j = paramInt2;
      if (this.padding > 2) {
        break;
      }
      i = this.pos;
      if (i < 8)
      {
        paramArrayOfByte2 = this.plain;
        this.pos = (i + 1);
        paramArrayOfByte2[i] = ((byte)(rand() & 0xFF));
        this.padding += 1;
      }
      if (this.pos == 8) {
        encrypt8Bytes();
      }
    }
    while (j > 0)
    {
      int k = this.pos;
      paramInt2 = i;
      paramInt1 = j;
      if (k < 8)
      {
        paramArrayOfByte2 = this.plain;
        this.pos = (k + 1);
        paramArrayOfByte2[k] = paramArrayOfByte1[i];
        paramInt1 = j - 1;
        paramInt2 = i + 1;
      }
      i = paramInt2;
      j = paramInt1;
      if (this.pos == 8)
      {
        encrypt8Bytes();
        i = paramInt2;
        j = paramInt1;
      }
    }
    this.padding = 1;
    for (;;)
    {
      paramInt1 = this.padding;
      if (paramInt1 > 7) {
        break;
      }
      paramInt2 = this.pos;
      if (paramInt2 < 8)
      {
        paramArrayOfByte1 = this.plain;
        this.pos = (paramInt2 + 1);
        paramArrayOfByte1[paramInt2] = 0;
        this.padding = (paramInt1 + 1);
      }
      if (this.pos == 8) {
        encrypt8Bytes();
      }
    }
    return this.out;
  }
  
  public byte[] encrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return encrypt(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\Cryptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */