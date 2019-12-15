package com.tencent.common.utils;

import com.tencent.basesupport.FLogger;
import java.io.IOException;
import java.io.InputStream;

public class QuotedPrintableInputStream
  extends InputStream
{
  private byte jdField_a_of_type_Byte = 0;
  ByteQueue jdField_a_of_type_ComTencentCommonUtilsByteQueue = new ByteQueue();
  private InputStream jdField_a_of_type_JavaIoInputStream;
  private boolean jdField_a_of_type_Boolean = false;
  ByteQueue b = new ByteQueue();
  
  public QuotedPrintableInputStream(InputStream paramInputStream)
  {
    this.jdField_a_of_type_JavaIoInputStream = paramInputStream;
  }
  
  private byte a(byte paramByte)
  {
    if ((paramByte >= 48) && (paramByte <= 57)) {
      return (byte)(paramByte - 48);
    }
    if ((paramByte >= 65) && (paramByte <= 90)) {
      return (byte)(paramByte - 65 + 10);
    }
    if ((paramByte >= 97) && (paramByte <= 122)) {
      return (byte)(paramByte - 97 + 10);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append((char)paramByte);
    localStringBuilder.append(" is not a hexadecimal digit");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  private void a()
    throws IOException
  {
    if (this.b.count() != 0) {
      return;
    }
    int i;
    for (;;)
    {
      i = this.jdField_a_of_type_JavaIoInputStream.read();
      if (i == -1) {
        break label99;
      }
      if (i == 13) {
        break;
      }
      if (i != 32) {}
      switch (i)
      {
      default: 
        this.b.enqueue((byte)i);
        return;
      case 9: 
        this.b.enqueue((byte)i);
      }
    }
    this.b.clear();
    this.b.enqueue((byte)i);
    return;
    label99:
    this.b.clear();
  }
  
  private void b()
    throws IOException
  {
    byte b1 = 0;
    while (this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.count() == 0)
    {
      if (this.b.count() == 0)
      {
        a();
        if (this.b.count() == 0) {
          return;
        }
      }
      byte b2 = this.b.dequeue();
      StringBuilder localStringBuilder;
      switch (this.jdField_a_of_type_Byte)
      {
      default: 
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Illegal state: ");
        localStringBuilder.append(this.jdField_a_of_type_Byte);
        FLogger.d("QuotedPrintableInputStream", localStringBuilder.toString());
        this.jdField_a_of_type_Byte = 0;
        this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b2);
        break;
      case 3: 
        if (((b2 >= 48) && (b2 <= 57)) || ((b2 >= 65) && (b2 <= 70)) || ((b2 >= 97) && (b2 <= 102)))
        {
          int i = a(b1);
          int j = a(b2);
          this.jdField_a_of_type_Byte = 0;
          this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue((byte)(j | i << 4));
        }
        else
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Malformed MIME; expected [0-9A-Z], got ");
          localStringBuilder.append(b2);
          FLogger.d("QuotedPrintableInputStream", localStringBuilder.toString());
          this.jdField_a_of_type_Byte = 0;
          this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue((byte)61);
          this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b1);
          this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b2);
        }
        break;
      case 2: 
        if (b2 == 10)
        {
          this.jdField_a_of_type_Byte = 0;
        }
        else
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Malformed MIME; expected 10, got ");
          localStringBuilder.append(b2);
          FLogger.d("QuotedPrintableInputStream", localStringBuilder.toString());
          this.jdField_a_of_type_Byte = 0;
          this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue((byte)61);
          this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue((byte)13);
          this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b2);
        }
        break;
      case 1: 
        if (b2 == 13)
        {
          this.jdField_a_of_type_Byte = 2;
        }
        else if (((b2 >= 48) && (b2 <= 57)) || ((b2 >= 65) && (b2 <= 70)) || ((b2 >= 97) && (b2 <= 102)))
        {
          this.jdField_a_of_type_Byte = 3;
          b1 = b2;
        }
        else if (b2 == 61)
        {
          FLogger.d("QuotedPrintableInputStream", "Malformed MIME; got ==");
          this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue((byte)61);
        }
        else
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Malformed MIME; expected \\r or [0-9A-Z], got ");
          localStringBuilder.append(b2);
          FLogger.d("QuotedPrintableInputStream", localStringBuilder.toString());
          this.jdField_a_of_type_Byte = 0;
          this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue((byte)61);
          this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b2);
        }
        break;
      case 0: 
        if (b2 != 61) {
          this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.enqueue(b2);
        } else {
          this.jdField_a_of_type_Byte = 1;
        }
        break;
      }
    }
  }
  
  public void close()
    throws IOException
  {
    this.jdField_a_of_type_Boolean = true;
  }
  
  public int read()
    throws IOException
  {
    if (!this.jdField_a_of_type_Boolean)
    {
      b();
      if (this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.count() == 0) {
        return -1;
      }
      int i = this.jdField_a_of_type_ComTencentCommonUtilsByteQueue.dequeue();
      if (i >= 0) {
        return i;
      }
      return i & 0xFF;
    }
    throw new IOException("QuotedPrintableInputStream has been closed");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\QuotedPrintableInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */