package com.tencent.common.utils;

import com.tencent.basesupport.FLogger;
import java.io.IOException;
import java.io.InputStream;

public class Base64InputStream
  extends InputStream
{
  static final byte[] jdField_a_of_type_ArrayOfByte = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
  private static final int[] jdField_a_of_type_ArrayOfInt = new int['Ā'];
  private int jdField_a_of_type_Int = 0;
  private final ByteQueue jdField_a_of_type_ComTencentCommonUtilsByteQueue = new ByteQueue();
  private final InputStream jdField_a_of_type_JavaIoInputStream;
  private int jdField_b_of_type_Int = 0;
  private boolean jdField_b_of_type_Boolean;
  private final byte[] jdField_b_of_type_ArrayOfByte = new byte[1];
  private boolean jdField_c_of_type_Boolean = false;
  private final byte[] jdField_c_of_type_ArrayOfByte = new byte['؀'];
  private boolean d;
  
  static
  {
    int k = 0;
    int i = 0;
    int j;
    for (;;)
    {
      j = k;
      if (i >= 256) {
        break;
      }
      jdField_a_of_type_ArrayOfInt[i] = -1;
      i += 1;
    }
    for (;;)
    {
      byte[] arrayOfByte = jdField_a_of_type_ArrayOfByte;
      if (j >= arrayOfByte.length) {
        break;
      }
      jdField_a_of_type_ArrayOfInt[(arrayOfByte[j] & 0xFF)] = j;
      j += 1;
    }
  }
  
  public Base64InputStream(InputStream paramInputStream, boolean paramBoolean)
  {
    if (paramInputStream != null)
    {
      this.jdField_a_of_type_JavaIoInputStream = paramInputStream;
      this.jdField_b_of_type_Boolean = paramBoolean;
      return;
    }
    throw new IllegalArgumentException();
  }
  
  private int a(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4)
    throws IOException
  {
    this.d = true;
    byte b1;
    if (paramInt2 == 2)
    {
      b1 = (byte)(paramInt1 >>> 4);
      if (paramInt3 < paramInt4)
      {
        paramArrayOfByte[paramInt3] = b1;
        return paramInt3 + 1;
      }
      this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b1);
      return paramInt3;
    }
    if (paramInt2 == 3)
    {
      b1 = (byte)(paramInt1 >>> 10);
      byte b2 = (byte)(paramInt1 >>> 2 & 0xFF);
      if (paramInt3 < paramInt4 - 1)
      {
        paramInt1 = paramInt3 + 1;
        paramArrayOfByte[paramInt3] = b1;
        paramArrayOfByte[paramInt1] = b2;
        return paramInt1 + 1;
      }
      if (paramInt3 < paramInt4)
      {
        paramArrayOfByte[paramInt3] = b1;
        this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b2);
        return paramInt3 + 1;
      }
      this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b1);
      this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b2);
      return paramInt3;
    }
    if (!this.jdField_b_of_type_Boolean)
    {
      paramArrayOfByte = new StringBuilder();
      paramArrayOfByte.append("unexpected padding character; dropping ");
      paramArrayOfByte.append(paramInt2);
      paramArrayOfByte.append(" sextet(s)");
      FLogger.d("Base64InputStream", paramArrayOfByte.toString());
      return paramInt3;
    }
    throw new IOException("unexpected padding character");
  }
  
  private int a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int j = this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.count();
    int i = paramInt1;
    while ((j > 0) && (i < paramInt2))
    {
      paramArrayOfByte[i] = this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.dequeue();
      i += 1;
      j -= 1;
    }
    if (this.d)
    {
      if (i == paramInt1) {
        return -1;
      }
      return i - paramInt1;
    }
    int k = 0;
    int m;
    for (j = 0; i < paramInt2; j = m)
    {
      Object localObject;
      while (this.jdField_a_of_type_Int == this.jdField_b_of_type_Int)
      {
        localObject = this.jdField_a_of_type_JavaIoInputStream;
        byte[] arrayOfByte = this.jdField_c_of_type_ArrayOfByte;
        m = ((InputStream)localObject).read(arrayOfByte, 0, arrayOfByte.length);
        if (m == -1)
        {
          this.d = true;
          if (k != 0) {
            if (!this.jdField_b_of_type_Boolean)
            {
              paramArrayOfByte = new StringBuilder();
              paramArrayOfByte.append("unexpected end of file; dropping ");
              paramArrayOfByte.append(k);
              paramArrayOfByte.append(" sextet(s)");
              FLogger.d("Base64InputStream", paramArrayOfByte.toString());
            }
            else
            {
              throw new IOException("unexpected end of file");
            }
          }
          if (i == paramInt1) {
            return -1;
          }
          return i - paramInt1;
        }
        if (m > 0)
        {
          this.jdField_a_of_type_Int = 0;
          this.jdField_b_of_type_Int = m;
        }
        else if ((!jdField_a_of_type_Boolean) && (m != 0))
        {
          throw new AssertionError();
        }
      }
      m = j;
      j = k;
      k = m;
      byte b1;
      byte b2;
      byte b3;
      for (;;)
      {
        m = this.jdField_a_of_type_Int;
        if ((m >= this.jdField_b_of_type_Int) || (i >= paramInt2)) {
          break label583;
        }
        localObject = this.jdField_c_of_type_ArrayOfByte;
        this.jdField_a_of_type_Int = (m + 1);
        m = localObject[m] & 0xFF;
        if (m == 61) {
          return a(k, j, paramArrayOfByte, i, paramInt2) - paramInt1;
        }
        m = jdField_a_of_type_ArrayOfInt[m];
        if (m >= 0)
        {
          m = k << 6 | m;
          int n = j + 1;
          k = m;
          j = n;
          if (n == 4)
          {
            b1 = (byte)(m >>> 16);
            b2 = (byte)(m >>> 8);
            b3 = (byte)m;
            if (i >= paramInt2 - 2) {
              break;
            }
            j = i + 1;
            paramArrayOfByte[i] = b1;
            k = j + 1;
            paramArrayOfByte[j] = b2;
            i = k + 1;
            paramArrayOfByte[k] = b3;
            j = 0;
            k = m;
          }
        }
      }
      if (i < paramInt2 - 1)
      {
        j = i + 1;
        paramArrayOfByte[i] = b1;
        i = j + 1;
        paramArrayOfByte[j] = b2;
        this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b3);
      }
      else if (i < paramInt2)
      {
        paramArrayOfByte[i] = b1;
        this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b2);
        this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b3);
        i += 1;
      }
      else
      {
        this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b1);
        this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b2);
        this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b3);
      }
      if ((!jdField_a_of_type_Boolean) && (i != paramInt2)) {
        throw new AssertionError();
      }
      return paramInt2 - paramInt1;
      label583:
      m = k;
      k = j;
    }
    if ((!jdField_a_of_type_Boolean) && (k != 0)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (i != paramInt2)) {
      throw new AssertionError();
    }
    return paramInt2 - paramInt1;
  }
  
  public void close()
    throws IOException
  {
    if (this.jdField_c_of_type_Boolean) {
      return;
    }
    this.jdField_c_of_type_Boolean = true;
  }
  
  public int read()
    throws IOException
  {
    if (!this.jdField_c_of_type_Boolean)
    {
      int i;
      do
      {
        i = a(this.jdField_b_of_type_ArrayOfByte, 0, 1);
        if (i == -1) {
          return -1;
        }
      } while (i != 1);
      return this.jdField_b_of_type_ArrayOfByte[0] & 0xFF;
    }
    throw new IOException("Base64InputStream has been closed");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\Base64InputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */