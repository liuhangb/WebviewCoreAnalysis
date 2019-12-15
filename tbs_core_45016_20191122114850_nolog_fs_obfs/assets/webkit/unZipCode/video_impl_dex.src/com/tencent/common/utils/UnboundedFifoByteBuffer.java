package com.tencent.common.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnboundedFifoByteBuffer
{
  protected byte[] buffer;
  protected int head;
  protected int tail;
  
  public UnboundedFifoByteBuffer()
  {
    this(32);
  }
  
  public UnboundedFifoByteBuffer(int paramInt)
  {
    if (paramInt > 0)
    {
      this.buffer = new byte[paramInt + 1];
      this.head = 0;
      this.tail = 0;
      return;
    }
    throw new IllegalArgumentException("The size must be greater than 0");
  }
  
  int a(int paramInt)
  {
    int i = paramInt + 1;
    paramInt = i;
    if (i >= this.buffer.length) {
      paramInt = 0;
    }
    return paramInt;
  }
  
  public boolean add(byte paramByte)
  {
    int i = size();
    byte[] arrayOfByte1 = this.buffer;
    if (i + 1 >= arrayOfByte1.length)
    {
      arrayOfByte1 = new byte[(arrayOfByte1.length - 1) * 2 + 1];
      i = this.head;
      int j = 0;
      while (i != this.tail)
      {
        byte[] arrayOfByte2 = this.buffer;
        arrayOfByte1[j] = arrayOfByte2[i];
        arrayOfByte2[i] = 0;
        int k = j + 1;
        int m = i + 1;
        i = m;
        j = k;
        if (m == arrayOfByte2.length)
        {
          i = 0;
          j = k;
        }
      }
      this.buffer = arrayOfByte1;
      this.head = 0;
      this.tail = j;
    }
    arrayOfByte1 = this.buffer;
    i = this.tail;
    arrayOfByte1[i] = paramByte;
    this.tail = (i + 1);
    if (this.tail >= arrayOfByte1.length) {
      this.tail = 0;
    }
    return true;
  }
  
  int b(int paramInt)
  {
    int i = paramInt - 1;
    paramInt = i;
    if (i < 0) {
      paramInt = this.buffer.length - 1;
    }
    return paramInt;
  }
  
  public byte get()
  {
    if (!isEmpty()) {
      return this.buffer[this.head];
    }
    throw new IllegalStateException("The buffer is already empty");
  }
  
  public boolean isEmpty()
  {
    return size() == 0;
  }
  
  public Iterator<Byte> iterator()
  {
    new Iterator()
    {
      private int jdField_a_of_type_Int = UnboundedFifoByteBuffer.this.head;
      private int b = -1;
      
      public Byte a()
      {
        if (hasNext())
        {
          int i = this.jdField_a_of_type_Int;
          this.b = i;
          this.jdField_a_of_type_Int = UnboundedFifoByteBuffer.this.a(i);
          return new Byte(UnboundedFifoByteBuffer.this.buffer[this.b]);
        }
        throw new NoSuchElementException();
      }
      
      public boolean hasNext()
      {
        return this.jdField_a_of_type_Int != UnboundedFifoByteBuffer.this.tail;
      }
      
      public void remove()
      {
        int i = this.b;
        if (i != -1)
        {
          if (i == UnboundedFifoByteBuffer.this.head)
          {
            UnboundedFifoByteBuffer.this.remove();
            this.b = -1;
            return;
          }
          i = this.b + 1;
          while (i != UnboundedFifoByteBuffer.this.tail) {
            if (i >= UnboundedFifoByteBuffer.this.buffer.length)
            {
              UnboundedFifoByteBuffer.this.buffer[(i - 1)] = UnboundedFifoByteBuffer.this.buffer[0];
              i = 0;
            }
            else
            {
              UnboundedFifoByteBuffer.this.buffer[(i - 1)] = UnboundedFifoByteBuffer.this.buffer[i];
              i += 1;
            }
          }
          this.b = -1;
          UnboundedFifoByteBuffer localUnboundedFifoByteBuffer = UnboundedFifoByteBuffer.this;
          localUnboundedFifoByteBuffer.tail = localUnboundedFifoByteBuffer.b(localUnboundedFifoByteBuffer.tail);
          UnboundedFifoByteBuffer.this.buffer[UnboundedFifoByteBuffer.this.tail] = 0;
          this.jdField_a_of_type_Int = UnboundedFifoByteBuffer.this.b(this.jdField_a_of_type_Int);
          return;
        }
        throw new IllegalStateException();
      }
    };
  }
  
  public byte remove()
  {
    if (!isEmpty())
    {
      byte[] arrayOfByte = this.buffer;
      int i = this.head;
      byte b = arrayOfByte[i];
      this.head = (i + 1);
      if (this.head >= arrayOfByte.length) {
        this.head = 0;
      }
      return b;
    }
    throw new IllegalStateException("The buffer is already empty");
  }
  
  public int size()
  {
    int i = this.tail;
    int j = this.head;
    if (i < j) {
      return this.buffer.length - j + i;
    }
    return i - j;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\UnboundedFifoByteBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */