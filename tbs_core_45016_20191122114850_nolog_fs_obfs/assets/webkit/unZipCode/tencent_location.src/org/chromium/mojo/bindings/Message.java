package org.chromium.mojo.bindings;

import java.nio.ByteBuffer;
import java.util.List;
import org.chromium.mojo.system.Handle;

public class Message
{
  private final ByteBuffer jdField_a_of_type_JavaNioByteBuffer;
  private final List<? extends Handle> jdField_a_of_type_JavaUtilList;
  private ServiceMessage jdField_a_of_type_OrgChromiumMojoBindingsServiceMessage;
  
  public Message(ByteBuffer paramByteBuffer, List<? extends Handle> paramList)
  {
    this.jdField_a_of_type_JavaNioByteBuffer = paramByteBuffer;
    this.jdField_a_of_type_JavaUtilList = paramList;
  }
  
  public ByteBuffer a()
  {
    return this.jdField_a_of_type_JavaNioByteBuffer;
  }
  
  public List<? extends Handle> a()
  {
    return this.jdField_a_of_type_JavaUtilList;
  }
  
  public ServiceMessage a()
  {
    if (this.jdField_a_of_type_OrgChromiumMojoBindingsServiceMessage == null) {
      this.jdField_a_of_type_OrgChromiumMojoBindingsServiceMessage = new ServiceMessage(this);
    }
    return this.jdField_a_of_type_OrgChromiumMojoBindingsServiceMessage;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\Message.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */