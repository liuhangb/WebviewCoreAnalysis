package org.chromium.mojo.bindings;

import org.chromium.mojo.system.MessagePipeHandle;

public class InterfaceRequest<P extends Interface>
  implements HandleOwner<MessagePipeHandle>
{
  private final MessagePipeHandle a;
  
  InterfaceRequest(MessagePipeHandle paramMessagePipeHandle)
  {
    this.a = paramMessagePipeHandle;
  }
  
  public MessagePipeHandle a()
  {
    return this.a.pass();
  }
  
  public void close()
  {
    this.a.close();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\InterfaceRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */