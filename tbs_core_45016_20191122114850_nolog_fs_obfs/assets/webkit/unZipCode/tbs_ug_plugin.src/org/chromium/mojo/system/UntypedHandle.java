package org.chromium.mojo.system;

public abstract interface UntypedHandle
  extends Handle
{
  public abstract UntypedHandle pass();
  
  public abstract DataPipe.ConsumerHandle toDataPipeConsumerHandle();
  
  public abstract DataPipe.ProducerHandle toDataPipeProducerHandle();
  
  public abstract MessagePipeHandle toMessagePipeHandle();
  
  public abstract SharedBufferHandle toSharedBufferHandle();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\UntypedHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */