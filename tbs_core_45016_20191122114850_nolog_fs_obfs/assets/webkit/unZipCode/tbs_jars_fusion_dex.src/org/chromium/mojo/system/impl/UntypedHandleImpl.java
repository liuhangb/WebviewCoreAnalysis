package org.chromium.mojo.system.impl;

import org.chromium.mojo.system.DataPipe.ConsumerHandle;
import org.chromium.mojo.system.DataPipe.ProducerHandle;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.mojo.system.SharedBufferHandle;
import org.chromium.mojo.system.UntypedHandle;

class UntypedHandleImpl
  extends HandleBase
  implements UntypedHandle
{
  UntypedHandleImpl(CoreImpl paramCoreImpl, int paramInt)
  {
    super(paramCoreImpl, paramInt);
  }
  
  UntypedHandleImpl(HandleBase paramHandleBase)
  {
    super(paramHandleBase);
  }
  
  public UntypedHandle pass()
  {
    return new UntypedHandleImpl(this);
  }
  
  public DataPipe.ConsumerHandle toDataPipeConsumerHandle()
  {
    return new DataPipeConsumerHandleImpl(this);
  }
  
  public DataPipe.ProducerHandle toDataPipeProducerHandle()
  {
    return new DataPipeProducerHandleImpl(this);
  }
  
  public MessagePipeHandle toMessagePipeHandle()
  {
    return new MessagePipeHandleImpl(this);
  }
  
  public SharedBufferHandle toSharedBufferHandle()
  {
    return new SharedBufferHandleImpl(this);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\impl\UntypedHandleImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */