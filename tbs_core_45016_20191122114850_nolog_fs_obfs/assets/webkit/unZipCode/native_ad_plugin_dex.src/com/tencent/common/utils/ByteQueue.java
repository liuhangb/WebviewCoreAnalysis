package com.tencent.common.utils;

import java.util.Iterator;

public class ByteQueue
  implements Iterable<Byte>
{
  private int jdField_a_of_type_Int = -1;
  private UnboundedFifoByteBuffer jdField_a_of_type_ComTencentCommonUtilsUnboundedFifoByteBuffer;
  
  public ByteQueue()
  {
    this.jdField_a_of_type_ComTencentCommonUtilsUnboundedFifoByteBuffer = new UnboundedFifoByteBuffer();
  }
  
  public ByteQueue(int paramInt)
  {
    this.jdField_a_of_type_ComTencentCommonUtilsUnboundedFifoByteBuffer = new UnboundedFifoByteBuffer(paramInt);
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public void clear()
  {
    int i = this.jdField_a_of_type_Int;
    if (i != -1)
    {
      this.jdField_a_of_type_ComTencentCommonUtilsUnboundedFifoByteBuffer = new UnboundedFifoByteBuffer(i);
      return;
    }
    this.jdField_a_of_type_ComTencentCommonUtilsUnboundedFifoByteBuffer = new UnboundedFifoByteBuffer();
  }
  
  public int count()
  {
    return this.jdField_a_of_type_ComTencentCommonUtilsUnboundedFifoByteBuffer.size();
  }
  
  public byte dequeue()
  {
    return this.jdField_a_of_type_ComTencentCommonUtilsUnboundedFifoByteBuffer.remove();
  }
  
  public void enqueue(byte paramByte)
  {
    this.jdField_a_of_type_ComTencentCommonUtilsUnboundedFifoByteBuffer.add(paramByte);
  }
  
  public Iterator<Byte> iterator()
  {
    return this.jdField_a_of_type_ComTencentCommonUtilsUnboundedFifoByteBuffer.iterator();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\ByteQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */