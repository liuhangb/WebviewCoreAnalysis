package org.chromium.mojo.bindings;

import java.nio.ByteBuffer;

public class MessageHeader
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = new DataHeader(24, 0);
  private static final DataHeader jdField_b_of_type_OrgChromiumMojoBindingsDataHeader = new DataHeader(32, 1);
  private final int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private final int jdField_b_of_type_Int;
  private final DataHeader c;
  
  public MessageHeader(int paramInt)
  {
    this.c = jdField_a_of_type_OrgChromiumMojoBindingsDataHeader;
    this.jdField_a_of_type_Int = paramInt;
    this.jdField_b_of_type_Int = 0;
    this.jdField_a_of_type_Long = 0L;
  }
  
  public MessageHeader(int paramInt1, int paramInt2, long paramLong)
  {
    if ((!jdField_a_of_type_Boolean) && (!c(paramInt2))) {
      throw new AssertionError();
    }
    this.c = jdField_b_of_type_OrgChromiumMojoBindingsDataHeader;
    this.jdField_a_of_type_Int = paramInt1;
    this.jdField_b_of_type_Int = paramInt2;
    this.jdField_a_of_type_Long = paramLong;
  }
  
  MessageHeader(Message paramMessage)
  {
    paramMessage = new Decoder(paramMessage);
    this.c = paramMessage.a();
    a(this.c);
    if (paramMessage.a(8) == 0)
    {
      this.jdField_a_of_type_Int = paramMessage.a(12);
      this.jdField_b_of_type_Int = paramMessage.a(16);
      if (c(this.jdField_b_of_type_Int))
      {
        if (this.c.jdField_a_of_type_Int >= 32)
        {
          this.jdField_a_of_type_Long = paramMessage.a(24);
          return;
        }
        paramMessage = new StringBuilder();
        paramMessage.append("Incorrect message size, expecting at least 32 for a message with a request identifier, but got: ");
        paramMessage.append(this.c.jdField_a_of_type_Int);
        throw new DeserializationException(paramMessage.toString());
      }
      this.jdField_a_of_type_Long = 0L;
      return;
    }
    throw new DeserializationException("Non-zero interface ID, expecting zero since associated interfaces are not yet supported.");
  }
  
  private static void a(DataHeader paramDataHeader)
  {
    if (paramDataHeader.jdField_b_of_type_Int >= 0)
    {
      if (paramDataHeader.jdField_a_of_type_Int >= 24)
      {
        if ((paramDataHeader.jdField_b_of_type_Int == 0) && (paramDataHeader.jdField_a_of_type_Int != 24))
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Incorrect message size for a message with 0 fields, expecting 24, but got: ");
          localStringBuilder.append(paramDataHeader.jdField_a_of_type_Int);
          throw new DeserializationException(localStringBuilder.toString());
        }
        if (paramDataHeader.jdField_b_of_type_Int == 1)
        {
          if (paramDataHeader.jdField_a_of_type_Int == 32) {
            return;
          }
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Incorrect message size for a message with 1 fields, expecting 32, but got: ");
          localStringBuilder.append(paramDataHeader.jdField_a_of_type_Int);
          throw new DeserializationException(localStringBuilder.toString());
        }
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Incorrect message size, expecting at least 24, but got: ");
      localStringBuilder.append(paramDataHeader.jdField_a_of_type_Int);
      throw new DeserializationException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Incorrect number of fields, expecting at least 0, but got: ");
    localStringBuilder.append(paramDataHeader.jdField_b_of_type_Int);
    throw new DeserializationException(localStringBuilder.toString());
  }
  
  private static boolean c(int paramInt)
  {
    return (paramInt & 0x3) != 0;
  }
  
  public int a()
  {
    return this.c.jdField_a_of_type_Int;
  }
  
  public long a()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_Long;
  }
  
  void a(ByteBuffer paramByteBuffer, long paramLong)
  {
    if ((!jdField_a_of_type_Boolean) && (!c(paramByteBuffer.getInt(16)))) {
      throw new AssertionError();
    }
    paramByteBuffer.putLong(24, paramLong);
    this.jdField_a_of_type_Long = paramLong;
  }
  
  public void a(Encoder paramEncoder)
  {
    paramEncoder.a(this.c);
    paramEncoder.a(0, 8);
    paramEncoder.a(b(), 12);
    paramEncoder.a(c(), 16);
    if (a()) {
      paramEncoder.a(a(), 24);
    }
  }
  
  public boolean a()
  {
    return c(this.jdField_b_of_type_Int);
  }
  
  public boolean a(int paramInt)
  {
    return (this.jdField_b_of_type_Int & paramInt) == paramInt;
  }
  
  public boolean a(int paramInt1, int paramInt2)
  {
    return (b() == paramInt1) && (b(paramInt2));
  }
  
  public int b()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public boolean b(int paramInt)
  {
    return (c() & 0x3) == paramInt;
  }
  
  public int c()
  {
    return this.jdField_b_of_type_Int;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    if (getClass() != paramObject.getClass()) {
      return false;
    }
    paramObject = (MessageHeader)paramObject;
    return (BindingsHelper.a(this.c, ((MessageHeader)paramObject).c)) && (this.jdField_b_of_type_Int == ((MessageHeader)paramObject).jdField_b_of_type_Int) && (this.jdField_a_of_type_Long == ((MessageHeader)paramObject).jdField_a_of_type_Long) && (this.jdField_a_of_type_Int == ((MessageHeader)paramObject).jdField_a_of_type_Int);
  }
  
  public int hashCode()
  {
    DataHeader localDataHeader = this.c;
    int i;
    if (localDataHeader == null) {
      i = 0;
    } else {
      i = localDataHeader.hashCode();
    }
    int j = this.jdField_b_of_type_Int;
    long l = this.jdField_a_of_type_Long;
    return (((i + 31) * 31 + j) * 31 + (int)(l ^ l >>> 32)) * 31 + this.jdField_a_of_type_Int;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\MessageHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */