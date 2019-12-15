package com.tencent.mtt.base.net.frame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ByteArrayPool
{
  protected static final Comparator<byte[]> BUF_COMPARATOR = new Comparator()
  {
    public int a(byte[] paramAnonymousArrayOfByte1, byte[] paramAnonymousArrayOfByte2)
    {
      return paramAnonymousArrayOfByte1.length - paramAnonymousArrayOfByte2.length;
    }
  };
  private static int jdField_a_of_type_Int = 10240;
  static ByteArrayPool jdField_a_of_type_ComTencentMttBaseNetFrameByteArrayPool;
  private List<byte[]> jdField_a_of_type_JavaUtilList = new LinkedList();
  private int jdField_b_of_type_Int = 0;
  private List<byte[]> jdField_b_of_type_JavaUtilList = new ArrayList(64);
  private final int c;
  
  public ByteArrayPool(int paramInt)
  {
    this.c = paramInt;
  }
  
  private void a()
  {
    try
    {
      while (this.jdField_b_of_type_Int > this.c)
      {
        byte[] arrayOfByte = (byte[])this.jdField_a_of_type_JavaUtilList.remove(0);
        this.jdField_b_of_type_JavaUtilList.remove(arrayOfByte);
        this.jdField_b_of_type_Int -= arrayOfByte.length;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static ByteArrayPool getInstance()
  {
    ByteArrayPool localByteArrayPool = jdField_a_of_type_ComTencentMttBaseNetFrameByteArrayPool;
    if (localByteArrayPool != null) {
      return localByteArrayPool;
    }
    try
    {
      if (jdField_a_of_type_ComTencentMttBaseNetFrameByteArrayPool == null) {
        jdField_a_of_type_ComTencentMttBaseNetFrameByteArrayPool = new ByteArrayPool(jdField_a_of_type_Int);
      }
      return jdField_a_of_type_ComTencentMttBaseNetFrameByteArrayPool;
    }
    finally {}
  }
  
  public byte[] getBuf(int paramInt)
  {
    int i = 0;
    try
    {
      while (i < this.jdField_b_of_type_JavaUtilList.size())
      {
        arrayOfByte = (byte[])this.jdField_b_of_type_JavaUtilList.get(i);
        if (arrayOfByte.length >= paramInt)
        {
          this.jdField_b_of_type_Int -= arrayOfByte.length;
          this.jdField_b_of_type_JavaUtilList.remove(i);
          this.jdField_a_of_type_JavaUtilList.remove(arrayOfByte);
          return arrayOfByte;
        }
        i += 1;
      }
      byte[] arrayOfByte = new byte[paramInt];
      return arrayOfByte;
    }
    finally {}
  }
  
  public void returnBuf(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null) {
      try
      {
        if (paramArrayOfByte.length <= this.c)
        {
          this.jdField_a_of_type_JavaUtilList.add(paramArrayOfByte);
          int j = Collections.binarySearch(this.jdField_b_of_type_JavaUtilList, paramArrayOfByte, BUF_COMPARATOR);
          int i = j;
          if (j < 0) {
            i = -j - 1;
          }
          this.jdField_b_of_type_JavaUtilList.add(i, paramArrayOfByte);
          this.jdField_b_of_type_Int += paramArrayOfByte.length;
          a();
          return;
        }
      }
      finally {}
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\net\frame\ByteArrayPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */