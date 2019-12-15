package com.tencent.common.utils.gzip;

import java.io.IOException;
import java.io.InputStream;

public class GZipInputStream
  extends InputStream
{
  public static final int TYPE_DEFLATE = 0;
  public static final int TYPE_GZIP = 1;
  private byte jdField_a_of_type_Byte;
  int jdField_a_of_type_Int = 0;
  private long jdField_a_of_type_Long = 0L;
  private InputStream jdField_a_of_type_JavaIoInputStream;
  private boolean jdField_a_of_type_Boolean;
  byte[] jdField_a_of_type_ArrayOfByte;
  private int[] jdField_a_of_type_ArrayOfInt = new int['Ā'];
  long[] jdField_a_of_type_ArrayOfLong = new long[2];
  short[] jdField_a_of_type_ArrayOfShort;
  int jdField_b_of_type_Int = 0;
  private boolean jdField_b_of_type_Boolean;
  byte[] jdField_b_of_type_ArrayOfByte = new byte[8];
  short[] jdField_b_of_type_ArrayOfShort;
  int jdField_c_of_type_Int = 0;
  private boolean jdField_c_of_type_Boolean;
  private byte[] jdField_c_of_type_ArrayOfByte = new byte[32768];
  private int jdField_d_of_type_Int;
  private boolean jdField_d_of_type_Boolean;
  private int e;
  private int f;
  private int g = 0;
  private int h;
  private int i;
  
  public GZipInputStream(InputStream paramInputStream, int paramInt1, int paramInt2, boolean paramBoolean)
    throws IOException
  {
    this.jdField_a_of_type_JavaIoInputStream = paramInputStream;
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_Byte = 0;
    this.jdField_b_of_type_Boolean = paramBoolean;
    this.e = paramInt2;
    this.jdField_a_of_type_ArrayOfLong = new long[2];
    this.jdField_a_of_type_ArrayOfShort = new short['Ҁ'];
    this.jdField_b_of_type_ArrayOfShort = new short[''];
    this.h = paramInt1;
    this.jdField_a_of_type_ArrayOfByte = new byte[paramInt1 + 300];
    if (this.e == 1) {
      ZipHelper.skipheader(paramInputStream);
    }
    this.jdField_d_of_type_Int = 0;
  }
  
  public GZipInputStream(InputStream paramInputStream, int paramInt, boolean paramBoolean)
    throws IOException
  {
    this(paramInputStream, 1024, paramInt, paramBoolean);
  }
  
  private int a(long paramLong)
    throws IOException
  {
    if (paramLong == 0L) {
      return 0;
    }
    if (this.jdField_a_of_type_ArrayOfLong[1] < paramLong) {
      c();
    }
    long[] arrayOfLong = this.jdField_a_of_type_ArrayOfLong;
    long l = arrayOfLong[0];
    int j = (int)paramLong;
    int k = (int)(l & (1 << j) - 1);
    arrayOfLong[0] >>>= j;
    arrayOfLong[1] -= paramLong;
    return k;
  }
  
  private void a()
    throws IOException
  {
    byte[] arrayOfByte1 = this.jdField_c_of_type_ArrayOfByte;
    byte[] arrayOfByte2 = this.jdField_a_of_type_ArrayOfByte;
    int k = this.jdField_c_of_type_Int;
    System.arraycopy(arrayOfByte2, k, arrayOfByte2, 0, this.jdField_a_of_type_Int - k);
    this.jdField_a_of_type_Int -= this.jdField_c_of_type_Int;
    this.jdField_c_of_type_Int = 0;
    this.jdField_b_of_type_Int = this.jdField_a_of_type_Int;
    if ((this.i == 0) && (this.jdField_a_of_type_ArrayOfLong[1] < 15L)) {
      c();
    }
    int m;
    long l1;
    long l2;
    while ((arrayOfByte2.length - this.jdField_a_of_type_Int > 300) && ((this.jdField_a_of_type_ArrayOfLong[1] > 0L) || (this.i > 0)))
    {
      k = this.jdField_a_of_type_Byte;
      if (k != 3)
      {
        if (k == 0) {
          b();
        }
        int n;
        int i1;
        int i2;
        if (this.jdField_a_of_type_Byte == 1) {
          if (this.f == 0)
          {
            k = this.i;
            if (k > 0)
            {
              m = arrayOfByte2.length;
              n = this.jdField_a_of_type_Int;
              if (m - n <= k) {
                k = arrayOfByte2.length - n;
              }
              k = this.jdField_a_of_type_JavaIoInputStream.read(arrayOfByte2, this.jdField_a_of_type_Int, k);
              b(this.g, k, arrayOfByte2, this.jdField_a_of_type_Int);
              this.jdField_a_of_type_Int += k;
              this.g = (this.g + k & 0x7FFF);
              this.i -= k;
            }
            else
            {
              if (this.jdField_d_of_type_Boolean) {
                this.jdField_a_of_type_Byte = 2;
              } else {
                this.jdField_a_of_type_Byte = 0;
              }
              if (this.jdField_a_of_type_ArrayOfLong[1] < 15L) {
                c();
              }
            }
          }
          else
          {
            if (this.jdField_a_of_type_ArrayOfLong[1] < 15L) {
              c();
            }
            k = ZipHelper.deHuffNext(this.jdField_a_of_type_ArrayOfLong, this.jdField_a_of_type_ArrayOfShort);
            if (k < 256)
            {
              m = this.g;
              int j = (byte)k;
              arrayOfByte1[m] = j;
              this.g = (m + 1 & 0x7FFF);
              k = this.jdField_a_of_type_Int;
              arrayOfByte2[k] = j;
              this.jdField_a_of_type_Int = (k + 1);
            }
            else if (k != 256)
            {
              if (k <= 285)
              {
                int[] arrayOfInt = ZipHelper.LENGTH_CODE;
                k = k - 257 << 1;
                n = a(arrayOfInt[k]) + ZipHelper.LENGTH_CODE[(k + 1)];
                if (this.jdField_a_of_type_ArrayOfLong[1] < 15L) {
                  c();
                }
                k = ZipHelper.deHuffNext(this.jdField_a_of_type_ArrayOfLong, this.jdField_b_of_type_ArrayOfShort);
                arrayOfInt = ZipHelper.DISTANCE_CODE;
                k <<= 1;
                m = a(arrayOfInt[k]) + ZipHelper.DISTANCE_CODE[(k + 1)];
                i1 = this.g - m;
                if (i1 < 0) {
                  k = arrayOfByte1.length;
                } else {
                  k = 0;
                }
                i1 += k;
                i2 = n / m;
                n -= m * i2;
                k = 0;
                while (k < i2)
                {
                  a(i1, m, arrayOfByte2, this.jdField_a_of_type_Int);
                  b(this.g, m, arrayOfByte2, this.jdField_a_of_type_Int);
                  this.jdField_a_of_type_Int += m;
                  this.g = (this.g + m & 0x7FFF);
                  k += 1;
                }
                a(i1, n, arrayOfByte2, this.jdField_a_of_type_Int);
                b(this.g, n, arrayOfByte2, this.jdField_a_of_type_Int);
                this.jdField_a_of_type_Int += n;
                this.g = (this.g + n & 0x7FFF);
              }
              else
              {
                throw new IOException("1");
              }
            }
            else if (this.jdField_d_of_type_Boolean)
            {
              this.jdField_a_of_type_Byte = 2;
            }
            else
            {
              this.jdField_a_of_type_Byte = 0;
            }
            if (this.jdField_a_of_type_ArrayOfLong[1] < 15L) {
              c();
            }
          }
        }
        if (this.jdField_a_of_type_Byte == 2)
        {
          this.jdField_a_of_type_Byte = 3;
          l1 = this.jdField_a_of_type_Long;
          k = this.jdField_a_of_type_Int;
          l2 = k;
          m = this.jdField_b_of_type_Int;
          this.jdField_a_of_type_Long = (l1 + l2 - m & 0xFFFFFFFF);
          if (this.jdField_b_of_type_Boolean) {
            this.jdField_d_of_type_Int = ZipHelper.crc32(this.jdField_a_of_type_ArrayOfInt, this.jdField_d_of_type_Int, arrayOfByte2, m, k - m);
          }
          a(this.jdField_a_of_type_ArrayOfLong[1] & 0x7);
          k = a(8L);
          m = a(8L);
          n = a(8L);
          i1 = a(8L);
          i2 = a(8L);
          int i3 = a(8L);
          int i4 = a(8L);
          int i5;
          if ((a(8L) << 24 | i2 | i3 << 8 | i4 << 16) == this.jdField_a_of_type_Long) {
            i5 = 1;
          } else {
            i5 = 0;
          }
          this.jdField_c_of_type_Boolean = i5;
          if (this.jdField_b_of_type_Boolean)
          {
            i5 = this.jdField_c_of_type_Boolean;
            if (this.jdField_d_of_type_Int == (k | m << 8 | n << 16 | i1 << 24)) {
              k = 1;
            } else {
              k = 0;
            }
            this.jdField_c_of_type_Boolean = (k & i5);
          }
          if (!this.jdField_c_of_type_Boolean) {
            throw new IOException("2");
          }
        }
      }
    }
    if (this.jdField_a_of_type_Byte != 3)
    {
      l1 = this.jdField_a_of_type_Long;
      k = this.jdField_a_of_type_Int;
      l2 = k;
      m = this.jdField_b_of_type_Int;
      this.jdField_a_of_type_Long = (l1 + l2 - m & 0xFFFFFFFF);
      if (this.jdField_b_of_type_Boolean) {
        this.jdField_d_of_type_Int = ZipHelper.crc32(this.jdField_a_of_type_ArrayOfInt, this.jdField_d_of_type_Int, arrayOfByte2, m, k - m);
      }
    }
  }
  
  private void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
  {
    byte[] arrayOfByte = this.jdField_c_of_type_ArrayOfByte;
    if (paramInt1 + paramInt2 < arrayOfByte.length)
    {
      System.arraycopy(arrayOfByte, paramInt1, paramArrayOfByte, paramInt3 + 0, paramInt2);
      return;
    }
    System.arraycopy(arrayOfByte, paramInt1, paramArrayOfByte, paramInt3 + 0, arrayOfByte.length - paramInt1);
    arrayOfByte = this.jdField_c_of_type_ArrayOfByte;
    System.arraycopy(arrayOfByte, 0, paramArrayOfByte, arrayOfByte.length - paramInt1 + paramInt3, paramInt2 - (arrayOfByte.length - paramInt1));
  }
  
  private void b()
    throws IOException
  {
    int[] arrayOfInt1 = new int[30];
    Object localObject = new int[30];
    byte[] arrayOfByte1 = new byte[30];
    int[] arrayOfInt2 = new int['Ğ'];
    int[] arrayOfInt3 = new int['Ğ'];
    byte[] arrayOfByte2 = new byte['Ğ'];
    boolean bool;
    if (a(1L) == 1) {
      bool = true;
    } else {
      bool = false;
    }
    this.jdField_d_of_type_Boolean = bool;
    this.f = a(2L);
    int k = this.f;
    if (k != 3)
    {
      if (k == 1)
      {
        ZipHelper.genFixedTree(arrayOfInt2, arrayOfByte2, arrayOfInt1, arrayOfByte1);
        k = 0;
        while (k < 286)
        {
          arrayOfInt3[k] = k;
          k += 1;
        }
        k = 0;
        while (k < 30)
        {
          localObject[k] = k;
          k += 1;
        }
        ZipHelper.convertTable2Tree(arrayOfInt2, arrayOfByte2, arrayOfInt3, this.jdField_a_of_type_ArrayOfShort);
        ZipHelper.convertTable2Tree(arrayOfInt1, arrayOfByte1, (int[])localObject, this.jdField_b_of_type_ArrayOfShort);
      }
      else
      {
        int m;
        int j;
        if (k == 2)
        {
          int i1 = a(5L);
          int i2 = a(5L);
          m = a(4L);
          byte[] arrayOfByte3 = new byte[19];
          int[] arrayOfInt4 = new int[19];
          k = 0;
          while (k < m + 4)
          {
            arrayOfByte3[new int[] { 16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15 }[k]] = ((byte)a(3L));
            k += 1;
          }
          ZipHelper.genHuffTree(arrayOfInt4, arrayOfByte3);
          ZipHelper.revHuffTree(arrayOfInt4, arrayOfByte3);
          short[] arrayOfShort = new short[76];
          ZipHelper.convertTable2Tree(arrayOfInt4, arrayOfByte3, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 }, arrayOfShort);
          k = 0;
          while (k < arrayOfByte2.length)
          {
            arrayOfByte2[k] = 0;
            k += 1;
          }
          k = 0;
          while (k < arrayOfByte1.length)
          {
            arrayOfByte1[k] = 0;
            k += 1;
          }
          m = 0;
          j = 0;
          for (;;)
          {
            int i3 = i1 + 257;
            if (m >= i3 + i2 + 1) {
              break;
            }
            if (this.jdField_a_of_type_ArrayOfLong[1] < 15L) {
              c();
            }
            k = ZipHelper.deHuffNext(this.jdField_a_of_type_ArrayOfLong, arrayOfShort);
            if (k < 16)
            {
              j = (byte)k;
              k = 1;
            }
            else if (k == 16)
            {
              k = a(2L) + 3;
            }
            else if (k == 17)
            {
              k = a(3L) + 3;
              j = 0;
            }
            else if (k == 18)
            {
              k = a(7L) + 11;
              j = 0;
            }
            int n = 0;
            while (n < k)
            {
              if (m < i3) {
                arrayOfByte2[m] = j;
              } else {
                arrayOfByte1[(m - i3)] = j;
              }
              n += 1;
              m += 1;
            }
          }
          ZipHelper.genHuffTree(arrayOfInt2, arrayOfByte2);
          k = 0;
          while (k < arrayOfInt3.length)
          {
            arrayOfInt3[k] = k;
            k += 1;
          }
          ZipHelper.revHuffTree(arrayOfInt2, arrayOfByte2);
          ZipHelper.convertTable2Tree(arrayOfInt2, arrayOfByte2, arrayOfInt3, this.jdField_a_of_type_ArrayOfShort);
          k = 0;
          while (k < arrayOfInt1.length)
          {
            localObject[k] = k;
            k += 1;
          }
          ZipHelper.genHuffTree(arrayOfInt1, arrayOfByte1);
          ZipHelper.revHuffTree(arrayOfInt1, arrayOfByte1);
          ZipHelper.convertTable2Tree(arrayOfInt1, arrayOfByte1, (int[])localObject, this.jdField_b_of_type_ArrayOfShort);
        }
        else
        {
          a(this.jdField_a_of_type_ArrayOfLong[1] & 0x7);
          this.i = (a(8L) | a(8L) << 8);
          if (this.jdField_a_of_type_ArrayOfLong[1] < 15L) {
            c();
          }
          if (this.i + (a(8L) | a(8L) << 8) != 65535) {
            break label977;
          }
          while ((this.jdField_a_of_type_ArrayOfLong[1] != 0L) && (this.i > 0))
          {
            k = a(8L);
            localObject = this.jdField_c_of_type_ArrayOfByte;
            m = this.g;
            j = (byte)k;
            localObject[m] = j;
            this.g = (m + 1 & 0x7FFF);
            localObject = this.jdField_a_of_type_ArrayOfByte;
            k = this.jdField_a_of_type_Int;
            localObject[k] = j;
            this.jdField_a_of_type_Int = (k + 1);
            this.i -= 1;
          }
        }
      }
      this.jdField_a_of_type_Byte = 1;
      return;
      label977:
      throw new IOException("3");
    }
    throw new IllegalArgumentException();
  }
  
  private void b(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
  {
    byte[] arrayOfByte = this.jdField_c_of_type_ArrayOfByte;
    if (paramInt2 + paramInt1 < arrayOfByte.length)
    {
      System.arraycopy(paramArrayOfByte, paramInt3, arrayOfByte, paramInt1, paramInt2);
      return;
    }
    System.arraycopy(paramArrayOfByte, paramInt3, arrayOfByte, paramInt1, arrayOfByte.length - paramInt1);
    arrayOfByte = this.jdField_c_of_type_ArrayOfByte;
    System.arraycopy(paramArrayOfByte, paramInt3 + (arrayOfByte.length - paramInt1), arrayOfByte, 0, paramInt2 - (arrayOfByte.length - paramInt1));
  }
  
  private void c()
    throws IOException
  {
    if (!this.jdField_a_of_type_Boolean)
    {
      int j = (int)(8L - this.jdField_a_of_type_ArrayOfLong[1] / 8L - 1L);
      int k = this.jdField_a_of_type_JavaIoInputStream.read(this.jdField_b_of_type_ArrayOfByte, 0, j);
      if (k == -1) {
        this.jdField_a_of_type_Boolean = true;
      }
      j = 0;
      while (j < k)
      {
        long[] arrayOfLong = this.jdField_a_of_type_ArrayOfLong;
        arrayOfLong[0] &= (255L << (int)arrayOfLong[1] ^ 0xFFFFFFFFFFFFFFFF);
        byte[] arrayOfByte = this.jdField_b_of_type_ArrayOfByte;
        if (arrayOfByte[j] < 0) {
          arrayOfLong[0] |= arrayOfByte[j] + 256 << (int)arrayOfLong[1];
        } else {
          arrayOfLong[0] |= arrayOfByte[j] << (int)arrayOfLong[1];
        }
        arrayOfLong = this.jdField_a_of_type_ArrayOfLong;
        arrayOfLong[1] += 8L;
        j += 1;
      }
    }
  }
  
  public int available()
    throws IOException
  {
    if (this.jdField_a_of_type_Int - this.jdField_c_of_type_Int < this.jdField_a_of_type_ArrayOfByte.length - 300) {
      a();
    }
    return this.jdField_a_of_type_Int - this.jdField_c_of_type_Int;
  }
  
  public void close()
    throws IOException
  {
    this.jdField_a_of_type_JavaIoInputStream.close();
    this.jdField_a_of_type_ArrayOfLong = null;
    this.jdField_a_of_type_ArrayOfShort = null;
    this.jdField_b_of_type_ArrayOfShort = null;
  }
  
  public int read()
    throws IOException
  {
    if (this.jdField_a_of_type_Int - this.jdField_c_of_type_Int == 0) {
      a();
    }
    if ((this.jdField_a_of_type_Int - this.jdField_c_of_type_Int == 0) && (this.jdField_a_of_type_Boolean)) {
      return -1;
    }
    byte[] arrayOfByte = this.jdField_a_of_type_ArrayOfByte;
    int j = this.jdField_c_of_type_Int;
    this.jdField_c_of_type_Int = (j + 1);
    return arrayOfByte[j] + 256 & 0xFF;
  }
  
  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.jdField_a_of_type_Int - this.jdField_c_of_type_Int < this.jdField_a_of_type_ArrayOfByte.length - 300) {
      a();
    }
    int k = available();
    int j = paramInt2;
    if (paramInt2 > k) {
      j = k;
    }
    System.arraycopy(this.jdField_a_of_type_ArrayOfByte, this.jdField_c_of_type_Int, paramArrayOfByte, paramInt1, j);
    this.jdField_c_of_type_Int += j;
    if (j != 0) {
      return j;
    }
    return -1;
  }
  
  public long skip(long paramLong)
    throws IOException
  {
    byte[] arrayOfByte = new byte[this.h];
    for (long l = 0L; (l < paramLong) && (this.jdField_a_of_type_Byte != 3); l += read(arrayOfByte)) {}
    return l;
  }
  
  public int validData()
    throws IOException
  {
    a();
    if (this.jdField_a_of_type_Byte != 3) {
      return -1;
    }
    if (this.jdField_c_of_type_Boolean) {
      return 1;
    }
    return 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\gzip\GZipInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */