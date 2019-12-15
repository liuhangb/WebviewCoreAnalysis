package org.chromium.mojo.bindings;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ServiceMessage
  extends Message
{
  private Message jdField_a_of_type_OrgChromiumMojoBindingsMessage;
  private final MessageHeader jdField_a_of_type_OrgChromiumMojoBindingsMessageHeader;
  
  ServiceMessage(Message paramMessage)
  {
    this(paramMessage, new MessageHeader(paramMessage));
  }
  
  public ServiceMessage(Message paramMessage, MessageHeader paramMessageHeader)
  {
    super(paramMessage.a(), paramMessage.a());
    if ((!jdField_a_of_type_Boolean) && (!paramMessageHeader.equals(new MessageHeader(paramMessage)))) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumMojoBindingsMessageHeader = paramMessageHeader;
  }
  
  public Message a()
  {
    if (this.jdField_a_of_type_OrgChromiumMojoBindingsMessage == null)
    {
      ByteBuffer localByteBuffer = ((ByteBuffer)a().position(a().a())).slice();
      localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessage = new Message(localByteBuffer, a());
    }
    return this.jdField_a_of_type_OrgChromiumMojoBindingsMessage;
  }
  
  public MessageHeader a()
  {
    return this.jdField_a_of_type_OrgChromiumMojoBindingsMessageHeader;
  }
  
  public ServiceMessage a()
  {
    return this;
  }
  
  void a(long paramLong)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsMessageHeader.a(a(), paramLong);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\ServiceMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */