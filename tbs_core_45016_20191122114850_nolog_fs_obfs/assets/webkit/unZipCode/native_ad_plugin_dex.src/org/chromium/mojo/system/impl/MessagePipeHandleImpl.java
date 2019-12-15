package org.chromium.mojo.system.impl;

import java.nio.ByteBuffer;
import java.util.List;
import org.chromium.mojo.system.Handle;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.mojo.system.MessagePipeHandle.ReadFlags;
import org.chromium.mojo.system.MessagePipeHandle.ReadMessageResult;
import org.chromium.mojo.system.MessagePipeHandle.WriteFlags;
import org.chromium.mojo.system.ResultAnd;

class MessagePipeHandleImpl
  extends HandleBase
  implements MessagePipeHandle
{
  MessagePipeHandleImpl(CoreImpl paramCoreImpl, int paramInt)
  {
    super(paramCoreImpl, paramInt);
  }
  
  MessagePipeHandleImpl(HandleBase paramHandleBase)
  {
    super(paramHandleBase);
  }
  
  public MessagePipeHandle pass()
  {
    return new MessagePipeHandleImpl(this);
  }
  
  public ResultAnd<MessagePipeHandle.ReadMessageResult> readMessage(MessagePipeHandle.ReadFlags paramReadFlags)
  {
    return this.a.a(this, paramReadFlags);
  }
  
  public void writeMessage(ByteBuffer paramByteBuffer, List<? extends Handle> paramList, MessagePipeHandle.WriteFlags paramWriteFlags)
  {
    this.a.a(this, paramByteBuffer, paramList, paramWriteFlags);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\impl\MessagePipeHandleImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */