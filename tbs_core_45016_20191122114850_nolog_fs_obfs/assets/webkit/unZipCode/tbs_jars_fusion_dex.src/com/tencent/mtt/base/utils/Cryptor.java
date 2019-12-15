package com.tencent.mtt.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class Cryptor
{
  public static final int QUOTIENT = 79764919;
  public static final int SALT_LEN = 2;
  public static final int ZERO_LEN = 7;
  private int jdField_a_of_type_Int;
  private Random jdField_a_of_type_JavaUtilRandom = new Random();
  private boolean jdField_a_of_type_Boolean = true;
  private byte[] jdField_a_of_type_ArrayOfByte;
  private int jdField_b_of_type_Int;
  private byte[] jdField_b_of_type_ArrayOfByte;
  private int jdField_c_of_type_Int;
  private byte[] jdField_c_of_type_ArrayOfByte;
  private int jdField_d_of_type_Int;
  private byte[] jdField_d_of_type_ArrayOfByte;
  private int e;
  
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
    arrayOfShort2[0] = ((short)(paramArrayOfByte[1] << 8 | paramArrayOfByte[0]));
    arrayOfShort2[1] = ((short)(paramArrayOfByte[3] << 8 | paramArrayOfByte[2]));
    int k = paramArrayOfByte[5];
    arrayOfShort2[2] = ((short)(paramArrayOfByte[4] | k << 8));
    arrayOfShort2[3] = ((short)(paramArrayOfByte[7] << 8 | paramArrayOfByte[6]));
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
    arrayOfShort2[0] = ((short)(paramArrayOfByte[1] << 8 | paramArrayOfByte[0]));
    arrayOfShort2[1] = ((short)(paramArrayOfByte[3] << 8 | paramArrayOfByte[2]));
    arrayOfShort2[2] = ((short)(paramArrayOfByte[5] << 8 | paramArrayOfByte[4]));
    paramInt = paramArrayOfByte[7];
    arrayOfShort2[3] = ((short)(paramArrayOfByte[6] | paramInt << 8));
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
  
  private int a()
  {
    return this.jdField_a_of_type_JavaUtilRandom.nextInt();
  }
  
  private void a()
  {
    byte[] arrayOfByte;
    for (this.jdField_c_of_type_Int = 0;; this.jdField_c_of_type_Int += 1)
    {
      i = this.jdField_c_of_type_Int;
      if (i >= 8) {
        break;
      }
      if (this.jdField_a_of_type_Boolean)
      {
        arrayOfByte = this.jdField_a_of_type_ArrayOfByte;
        arrayOfByte[i] = ((byte)(arrayOfByte[i] ^ this.jdField_b_of_type_ArrayOfByte[i]));
      }
      else
      {
        arrayOfByte = this.jdField_a_of_type_ArrayOfByte;
        arrayOfByte[i] = ((byte)(arrayOfByte[i] ^ this.jdField_c_of_type_ArrayOfByte[(this.jdField_b_of_type_Int + i)]));
      }
    }
    System.arraycopy(a(this.jdField_a_of_type_ArrayOfByte), 0, this.jdField_c_of_type_ArrayOfByte, this.jdField_a_of_type_Int, 8);
    for (this.jdField_c_of_type_Int = 0;; this.jdField_c_of_type_Int = (i + 1))
    {
      i = this.jdField_c_of_type_Int;
      if (i >= 8) {
        break;
      }
      arrayOfByte = this.jdField_c_of_type_ArrayOfByte;
      int j = this.jdField_a_of_type_Int + i;
      arrayOfByte[j] = ((byte)(arrayOfByte[j] ^ this.jdField_b_of_type_ArrayOfByte[i]));
    }
    System.arraycopy(this.jdField_a_of_type_ArrayOfByte, 0, this.jdField_b_of_type_ArrayOfByte, 0, 8);
    int i = this.jdField_a_of_type_Int;
    this.jdField_b_of_type_Int = i;
    this.jdField_a_of_type_Int = (i + 8);
    this.jdField_c_of_type_Int = 0;
    this.jdField_a_of_type_Boolean = false;
  }
  
  private boolean a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i;
    for (this.jdField_c_of_type_Int = 0;; this.jdField_c_of_type_Int = (i + 1))
    {
      i = this.jdField_c_of_type_Int;
      if (i >= 8) {
        break;
      }
      if (this.e + i >= paramInt2) {
        return true;
      }
      byte[] arrayOfByte = this.jdField_b_of_type_ArrayOfByte;
      arrayOfByte[i] = ((byte)(arrayOfByte[i] ^ paramArrayOfByte[(this.jdField_a_of_type_Int + paramInt1 + i)]));
    }
    this.jdField_b_of_type_ArrayOfByte = b(this.jdField_b_of_type_ArrayOfByte);
    if (this.jdField_b_of_type_ArrayOfByte == null) {
      return false;
    }
    this.e += 8;
    this.jdField_a_of_type_Int += 8;
    this.jdField_c_of_type_Int = 0;
    return true;
  }
  
  private byte[] a(byte[] paramArrayOfByte)
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
        l4 = getUnsignedInt(this.jdField_d_of_type_ArrayOfByte, 0, 4);
        l5 = getUnsignedInt(this.jdField_d_of_type_ArrayOfByte, 4, 4);
        l6 = getUnsignedInt(this.jdField_d_of_type_ArrayOfByte, 8, 4);
        l7 = getUnsignedInt(this.jdField_d_of_type_ArrayOfByte, 12, 4);
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
  
  private byte[] a(byte[] paramArrayOfByte, int paramInt)
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
        l4 = getUnsignedInt(this.jdField_d_of_type_ArrayOfByte, 0, 4);
        l5 = getUnsignedInt(this.jdField_d_of_type_ArrayOfByte, 4, 4);
        l6 = getUnsignedInt(this.jdField_d_of_type_ArrayOfByte, 8, 4);
        l7 = getUnsignedInt(this.jdField_d_of_type_ArrayOfByte, 12, 4);
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
  
  private byte[] b(byte[] paramArrayOfByte)
  {
    return a(paramArrayOfByte, 0);
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
  
  public byte[] decrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2)
  {
    int k = 0;
    this.jdField_b_of_type_Int = 0;
    this.jdField_a_of_type_Int = 0;
    this.jdField_d_of_type_ArrayOfByte = paramArrayOfByte2;
    paramArrayOfByte2 = new byte[paramInt1 + 8];
    if (paramInt2 % 8 == 0)
    {
      if (paramInt2 < 16) {
        return null;
      }
      this.jdField_b_of_type_ArrayOfByte = a(paramArrayOfByte1, paramInt1);
      this.jdField_c_of_type_Int = (this.jdField_b_of_type_ArrayOfByte[0] & 0x7);
      int m = paramInt2 - this.jdField_c_of_type_Int - 10;
      if (m < 0) {
        return null;
      }
      int i = paramInt1;
      while (i < paramArrayOfByte2.length)
      {
        paramArrayOfByte2[i] = 0;
        i += 1;
      }
      this.jdField_c_of_type_ArrayOfByte = new byte[m];
      this.jdField_b_of_type_Int = 0;
      this.jdField_a_of_type_Int = 8;
      this.e = 8;
      this.jdField_c_of_type_Int += 1;
      this.jdField_d_of_type_Int = 1;
      int n;
      int j;
      byte[] arrayOfByte;
      for (;;)
      {
        n = this.jdField_d_of_type_Int;
        j = k;
        i = m;
        arrayOfByte = paramArrayOfByte2;
        if (n > 2) {
          break;
        }
        i = this.jdField_c_of_type_Int;
        if (i < 8)
        {
          this.jdField_c_of_type_Int = (i + 1);
          this.jdField_d_of_type_Int = (n + 1);
        }
        if (this.jdField_c_of_type_Int == 8)
        {
          if (!a(paramArrayOfByte1, paramInt1, paramInt2)) {
            return null;
          }
          paramArrayOfByte2 = paramArrayOfByte1;
        }
      }
      while (i != 0)
      {
        n = this.jdField_c_of_type_Int;
        m = j;
        k = i;
        if (n < 8)
        {
          this.jdField_c_of_type_ArrayOfByte[j] = ((byte)(arrayOfByte[(this.jdField_b_of_type_Int + paramInt1 + n)] ^ this.jdField_b_of_type_ArrayOfByte[n]));
          m = j + 1;
          k = i - 1;
          this.jdField_c_of_type_Int = (n + 1);
        }
        j = m;
        i = k;
        if (this.jdField_c_of_type_Int == 8)
        {
          this.jdField_b_of_type_Int = (this.jdField_a_of_type_Int - 8);
          if (!a(paramArrayOfByte1, paramInt1, paramInt2)) {
            return null;
          }
          arrayOfByte = paramArrayOfByte1;
          j = m;
          i = k;
        }
      }
      this.jdField_d_of_type_Int = 1;
      while (this.jdField_d_of_type_Int < 8)
      {
        i = this.jdField_c_of_type_Int;
        if (i < 8)
        {
          if ((arrayOfByte[(this.jdField_b_of_type_Int + paramInt1 + i)] ^ this.jdField_b_of_type_ArrayOfByte[i]) != 0) {
            return null;
          }
          this.jdField_c_of_type_Int = (i + 1);
        }
        paramArrayOfByte2 = arrayOfByte;
        if (this.jdField_c_of_type_Int == 8)
        {
          this.jdField_b_of_type_Int = this.jdField_a_of_type_Int;
          if (!a(paramArrayOfByte1, paramInt1, paramInt2)) {
            return null;
          }
          paramArrayOfByte2 = paramArrayOfByte1;
        }
        this.jdField_d_of_type_Int += 1;
        arrayOfByte = paramArrayOfByte2;
      }
      return this.jdField_c_of_type_ArrayOfByte;
    }
    return null;
  }
  
  public byte[] decrypt(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2)
  {
    return decrypt(paramArrayOfByte1, 0, paramInt, paramArrayOfByte2);
  }
  
  public byte[] decrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return decrypt(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
  }
  
  public byte[] encrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2)
  {
    this.jdField_a_of_type_ArrayOfByte = new byte[8];
    this.jdField_b_of_type_ArrayOfByte = new byte[8];
    this.jdField_c_of_type_Int = 1;
    this.jdField_d_of_type_Int = 0;
    this.jdField_b_of_type_Int = 0;
    this.jdField_a_of_type_Int = 0;
    this.jdField_d_of_type_ArrayOfByte = paramArrayOfByte2;
    this.jdField_a_of_type_Boolean = true;
    this.jdField_c_of_type_Int = ((paramInt2 + 10) % 8);
    int i = this.jdField_c_of_type_Int;
    if (i != 0) {
      this.jdField_c_of_type_Int = (8 - i);
    }
    this.jdField_c_of_type_ArrayOfByte = new byte[this.jdField_c_of_type_Int + paramInt2 + 10];
    this.jdField_a_of_type_ArrayOfByte[0] = ((byte)(a() & 0xF8 | this.jdField_c_of_type_Int));
    i = 1;
    int j;
    for (;;)
    {
      j = this.jdField_c_of_type_Int;
      if (i > j) {
        break;
      }
      this.jdField_a_of_type_ArrayOfByte[i] = ((byte)(a() & 0xFF));
      i += 1;
    }
    this.jdField_c_of_type_Int = (j + 1);
    i = 0;
    while (i < 8)
    {
      this.jdField_b_of_type_ArrayOfByte[i] = 0;
      i += 1;
    }
    this.jdField_d_of_type_Int = 1;
    for (;;)
    {
      i = paramInt1;
      j = paramInt2;
      if (this.jdField_d_of_type_Int > 2) {
        break;
      }
      i = this.jdField_c_of_type_Int;
      if (i < 8)
      {
        paramArrayOfByte2 = this.jdField_a_of_type_ArrayOfByte;
        this.jdField_c_of_type_Int = (i + 1);
        paramArrayOfByte2[i] = ((byte)(a() & 0xFF));
        this.jdField_d_of_type_Int += 1;
      }
      if (this.jdField_c_of_type_Int == 8) {
        a();
      }
    }
    while (j > 0)
    {
      int k = this.jdField_c_of_type_Int;
      paramInt2 = i;
      paramInt1 = j;
      if (k < 8)
      {
        paramArrayOfByte2 = this.jdField_a_of_type_ArrayOfByte;
        this.jdField_c_of_type_Int = (k + 1);
        paramArrayOfByte2[k] = paramArrayOfByte1[i];
        paramInt1 = j - 1;
        paramInt2 = i + 1;
      }
      i = paramInt2;
      j = paramInt1;
      if (this.jdField_c_of_type_Int == 8)
      {
        a();
        i = paramInt2;
        j = paramInt1;
      }
    }
    this.jdField_d_of_type_Int = 1;
    for (;;)
    {
      paramInt1 = this.jdField_d_of_type_Int;
      if (paramInt1 > 7) {
        break;
      }
      paramInt2 = this.jdField_c_of_type_Int;
      if (paramInt2 < 8)
      {
        paramArrayOfByte1 = this.jdField_a_of_type_ArrayOfByte;
        this.jdField_c_of_type_Int = (paramInt2 + 1);
        paramArrayOfByte1[paramInt2] = 0;
        this.jdField_d_of_type_Int = (paramInt1 + 1);
      }
      if (this.jdField_c_of_type_Int == 8) {
        a();
      }
    }
    return this.jdField_c_of_type_ArrayOfByte;
  }
  
  public byte[] encrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return encrypt(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\Cryptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */