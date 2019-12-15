package com.tencent.common.utils.gzip;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public final class ZipHelper
{
  public static final int[] DISTANCE_CODE = { 0, 1, 0, 2, 0, 3, 0, 4, 1, 5, 1, 7, 2, 9, 2, 13, 3, 17, 3, 25, 4, 33, 4, 49, 5, 65, 5, 97, 6, 129, 6, 193, 7, 257, 7, 385, 8, 513, 8, 769, 9, 1025, 9, 1537, 10, 2049, 10, 3073, 11, 4097, 11, 6145, 12, 8193, 12, 12289, 13, 16385, 13, 24577 };
  public static final int[] LENGTH_CODE = { 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0, 8, 0, 9, 0, 10, 1, 11, 1, 13, 1, 15, 1, 17, 2, 19, 2, 23, 2, 27, 2, 31, 3, 35, 3, 43, 3, 51, 3, 59, 4, 67, 4, 83, 4, 99, 4, 115, 5, 131, 5, 163, 5, 195, 5, 227, 0, 258 };
  
  public static final void convertTable2Tree(int[] paramArrayOfInt1, byte[] paramArrayOfByte, int[] paramArrayOfInt2, short[] paramArrayOfShort)
  {
    int m = 0;
    while (m < paramArrayOfShort.length)
    {
      paramArrayOfShort[m] = 0;
      m += 1;
    }
    m = 0;
    int j;
    for (int i = 1; m < paramArrayOfInt1.length; i = j)
    {
      j = i;
      if (paramArrayOfByte[m] != 0)
      {
        int n = 0;
        int i1 = 0;
        while (n < paramArrayOfByte[m])
        {
          i1 *= 2;
          j = i;
          if (paramArrayOfShort[i1] == 0)
          {
            int k = (short)(i + 1);
            paramArrayOfShort[i1] = i;
            j = (short)(k + 1);
            paramArrayOfShort[(i1 + 1)] = k;
          }
          i1 = paramArrayOfShort[(i1 + (paramArrayOfInt1[m] >>> n & 0x1))];
          n = (short)(n + 1);
          i = j;
        }
        if (i1 < 0) {
          System.out.println("error pointer=-1");
        }
        n = i1 * 2;
        paramArrayOfShort[n] = -1;
        paramArrayOfShort[(n + 1)] = ((short)paramArrayOfInt2[m]);
        j = i;
      }
      m = (short)(m + 1);
    }
  }
  
  public static final int crc32(int[] paramArrayOfInt, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    int i = paramArrayOfInt[2];
    int m = 0;
    if (i == 0)
    {
      i = 0;
      while (i < 256)
      {
        int j = i;
        int k = 0;
        while (k < 8)
        {
          if ((j & 0x1) == 1) {
            j = j >>> 1 ^ 0xEDB88320;
          } else {
            j >>>= 1;
          }
          k += 1;
        }
        paramArrayOfInt[i] = j;
        i += 1;
      }
    }
    i = paramInt1 ^ 0xFFFFFFFF;
    paramInt1 = m;
    while (paramInt1 < paramInt3)
    {
      i = i >>> 8 ^ paramArrayOfInt[((paramArrayOfByte[(paramInt1 + paramInt2)] ^ i) & 0xFF)];
      paramInt1 += 1;
    }
    return i ^ 0xFFFFFFFF;
  }
  
  public static final int deHuffNext(long[] paramArrayOfLong, short[] paramArrayOfShort)
    throws IOException
  {
    if (paramArrayOfLong[1] < 15L) {
      System.out.println("smallCodebuffer is too small");
    }
    int i = 0;
    do
    {
      i *= 2;
      if (paramArrayOfShort[i] == -1) {
        break;
      }
      i = paramArrayOfShort[(i + (int)(paramArrayOfLong[0] & 1L))];
      paramArrayOfLong[0] >>>= 1;
      paramArrayOfLong[1] -= 1L;
    } while (i != 0);
    throw new IOException("5");
    return paramArrayOfShort[(i + 1)];
  }
  
  public static final void genFixedTree(int[] paramArrayOfInt1, byte[] paramArrayOfByte1, int[] paramArrayOfInt2, byte[] paramArrayOfByte2)
  {
    int j = 0;
    int i = 0;
    while (i <= 143)
    {
      paramArrayOfInt1[i] = (i + 48);
      paramArrayOfByte1[i] = 8;
      i += 1;
    }
    i = 144;
    while (i <= 255)
    {
      paramArrayOfInt1[i] = (i + 400 - 144);
      paramArrayOfByte1[i] = 9;
      i += 1;
    }
    i = 256;
    while (i <= 279)
    {
      paramArrayOfInt1[i] = (i - 256);
      paramArrayOfByte1[i] = 7;
      i += 1;
    }
    i = 280;
    while (i < 286)
    {
      paramArrayOfInt1[i] = (i + 192 - 280);
      paramArrayOfByte1[i] = 8;
      i += 1;
    }
    revHuffTree(paramArrayOfInt1, paramArrayOfByte1);
    i = j;
    while (i < paramArrayOfInt2.length)
    {
      paramArrayOfInt2[i] = i;
      paramArrayOfByte2[i] = 5;
      i += 1;
    }
    revHuffTree(paramArrayOfInt2, paramArrayOfByte2);
  }
  
  public static final void genHuffTree(int[] paramArrayOfInt, byte[] paramArrayOfByte)
  {
    int m = 0;
    int i = 0;
    int j = 0;
    int k;
    while (i < paramArrayOfByte.length)
    {
      k = paramArrayOfByte[i];
      if (j <= k) {
        j = k;
      }
      i += 1;
    }
    int n = j + 1;
    short[] arrayOfShort = new short[n];
    i = 0;
    while (i < paramArrayOfByte.length)
    {
      j = paramArrayOfByte[i];
      arrayOfShort[j] = ((short)(arrayOfShort[j] + 1));
      i += 1;
    }
    int[] arrayOfInt = new int[n];
    arrayOfShort[0] = 0;
    i = 1;
    j = 0;
    for (;;)
    {
      k = m;
      if (i >= n) {
        break;
      }
      j = j + arrayOfShort[(i - 1)] << 1;
      arrayOfInt[i] = j;
      i += 1;
    }
    while (k < paramArrayOfInt.length)
    {
      i = paramArrayOfByte[k];
      if (i != 0)
      {
        paramArrayOfInt[k] = arrayOfInt[i];
        arrayOfInt[i] += 1;
      }
      k += 1;
    }
  }
  
  public static final void genTreeLength(int[] paramArrayOfInt, byte[] paramArrayOfByte, int paramInt)
  {
    Object localObject = new int[paramArrayOfInt.length];
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    for (int j = 0; j < paramArrayOfInt.length; j = (short)(j + 1))
    {
      if (paramArrayOfInt[j] != 0) {
        localObject[j] = paramArrayOfInt[j];
      } else {
        localObject[j] = Integer.MAX_VALUE;
      }
      arrayOfInt[j] = j;
    }
    for (;;)
    {
      int k;
      if (localObject[0] < localObject[1])
      {
        k = 0;
        m = 1;
      }
      else
      {
        k = 1;
        m = 0;
      }
      j = 2;
      int i1;
      for (int n = m; j < paramArrayOfInt.length; n = m)
      {
        if (localObject[j] < localObject[k])
        {
          i1 = j;
          m = k;
        }
        else
        {
          i1 = k;
          m = n;
          if (localObject[j] < localObject[n])
          {
            m = j;
            i1 = k;
          }
        }
        j += 1;
        k = i1;
      }
      if (localObject[n] == Integer.MAX_VALUE)
      {
        j = 0;
        for (k = 0; j < paramArrayOfByte.length; k = m)
        {
          m = k;
          if (paramArrayOfByte[j] > paramInt) {
            m = k + 1;
          }
          j += 1;
        }
        if (k != 0)
        {
          paramArrayOfInt = System.out;
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append(" fixing ");
          ((StringBuilder)localObject).append(k);
          ((StringBuilder)localObject).append(" overflows  because of max=");
          ((StringBuilder)localObject).append(paramInt);
          paramArrayOfInt.println(((StringBuilder)localObject).toString());
          paramArrayOfInt = new short[k];
          int i = 0;
          for (m = 0; i < paramArrayOfByte.length; m = j)
          {
            j = m;
            if (paramArrayOfByte[i] > paramInt)
            {
              paramArrayOfInt[m] = i;
              j = m + 1;
            }
            i = (short)(i + 1);
          }
          n = 0;
          for (j = 0;; j = k)
          {
            k = m;
            if (n >= paramArrayOfByte.length) {
              break;
            }
            k = j;
            if (paramArrayOfByte[n] != 0)
            {
              k = j;
              if (paramArrayOfByte[j] > paramArrayOfByte[n]) {
                k = n;
              }
            }
            n += 1;
          }
          while (k != 0)
          {
            n = j;
            m = 0;
            while (m < paramArrayOfByte.length)
            {
              i1 = n;
              if (paramArrayOfByte[m] < paramInt)
              {
                i1 = n;
                if (paramArrayOfByte[n] < paramArrayOfByte[m]) {
                  i1 = m;
                }
              }
              m += 1;
              n = i1;
            }
            i1 = 0;
            int i2 = 0;
            m = 0;
            while (i1 < paramArrayOfInt.length)
            {
              int i3;
              if (paramArrayOfByte[paramArrayOfInt[i1]] > paramArrayOfByte[i2])
              {
                i3 = paramArrayOfInt[i1];
              }
              else
              {
                i3 = i2;
                if (paramArrayOfByte[paramArrayOfInt[i1]] == paramArrayOfByte[i2])
                {
                  m = paramArrayOfInt[i1];
                  i3 = i2;
                }
              }
              i1 += 1;
              i2 = i3;
            }
            paramArrayOfByte[n] = ((byte)(paramArrayOfByte[n] + 1));
            paramArrayOfByte[i2] = paramArrayOfByte[n];
            paramArrayOfByte[m] = ((byte)(paramArrayOfByte[m] - 1));
            n = k - 1;
            k = n;
            if (paramArrayOfByte[m] == paramInt) {
              k = n - 1;
            }
          }
        }
        return;
      }
      localObject[k] += localObject[n];
      int m = arrayOfInt[n];
      localObject[n] = Integer.MAX_VALUE;
      j = 0;
      while (j < paramArrayOfInt.length)
      {
        if (arrayOfInt[j] == m)
        {
          arrayOfInt[j] = arrayOfInt[k];
          paramArrayOfByte[j] = ((byte)(paramArrayOfByte[j] + 1));
        }
        else if (arrayOfInt[j] == arrayOfInt[k])
        {
          paramArrayOfByte[j] = ((byte)(paramArrayOfByte[j] + 1));
        }
        j += 1;
      }
    }
  }
  
  public static final void revHuffTree(int[] paramArrayOfInt, byte[] paramArrayOfByte)
  {
    int i = 0;
    while (i < paramArrayOfInt.length)
    {
      int m = paramArrayOfInt[i];
      int j = 0;
      int k = 0;
      while (j < paramArrayOfByte[i])
      {
        k = (k | m >>> j & 0x1) << 1;
        j += 1;
      }
      paramArrayOfInt[i] = (k >>> 1);
      i += 1;
    }
  }
  
  public static final void skipheader(InputStream paramInputStream)
    throws IOException
  {
    if ((paramInputStream.read() == 31) && (paramInputStream.read() == 139) && (paramInputStream.read() == 8))
    {
      int i = paramInputStream.read();
      paramInputStream.skip(6L);
      if ((i & 0x4) == 4) {
        paramInputStream.skip(paramInputStream.read() | paramInputStream.read() << 8);
      }
      while (((i & 0x8) == 8) && (paramInputStream.read() != 0)) {}
      while (((i & 0x10) == 16) && (paramInputStream.read() != 0)) {}
      if ((i & 0x2) == 2) {
        paramInputStream.skip(2L);
      }
      return;
    }
    throw new IOException("0");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\gzip\ZipHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */