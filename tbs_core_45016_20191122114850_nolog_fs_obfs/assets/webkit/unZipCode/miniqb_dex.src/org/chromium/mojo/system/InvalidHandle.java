package org.chromium.mojo.system;

import java.nio.ByteBuffer;
import java.util.List;

public class InvalidHandle
  implements DataPipe.ConsumerHandle, DataPipe.ProducerHandle, MessagePipeHandle, SharedBufferHandle, UntypedHandle
{
  public static final InvalidHandle a = new InvalidHandle();
  
  public InvalidHandle a()
  {
    return this;
  }
  
  public ByteBuffer beginReadData(int paramInt, DataPipe.ReadFlags paramReadFlags)
  {
    throw new MojoException(3);
  }
  
  public ByteBuffer beginWriteData(int paramInt, DataPipe.WriteFlags paramWriteFlags)
  {
    throw new MojoException(3);
  }
  
  public void close() {}
  
  public int discardData(int paramInt, DataPipe.ReadFlags paramReadFlags)
  {
    throw new MojoException(3);
  }
  
  public SharedBufferHandle duplicate(SharedBufferHandle.DuplicateOptions paramDuplicateOptions)
  {
    throw new MojoException(3);
  }
  
  public void endReadData(int paramInt)
  {
    throw new MojoException(3);
  }
  
  public void endWriteData(int paramInt)
  {
    throw new MojoException(3);
  }
  
  public Core getCore()
  {
    return null;
  }
  
  public boolean isValid()
  {
    return false;
  }
  
  public ByteBuffer map(long paramLong1, long paramLong2, SharedBufferHandle.MapFlags paramMapFlags)
  {
    throw new MojoException(3);
  }
  
  public Core.HandleSignalsState querySignalsState()
  {
    throw new MojoException(3);
  }
  
  public ResultAnd<Integer> readData(ByteBuffer paramByteBuffer, DataPipe.ReadFlags paramReadFlags)
  {
    throw new MojoException(3);
  }
  
  public ResultAnd<MessagePipeHandle.ReadMessageResult> readMessage(MessagePipeHandle.ReadFlags paramReadFlags)
  {
    throw new MojoException(3);
  }
  
  public int releaseNativeHandle()
  {
    return 0;
  }
  
  public DataPipe.ConsumerHandle toDataPipeConsumerHandle()
  {
    return this;
  }
  
  public DataPipe.ProducerHandle toDataPipeProducerHandle()
  {
    return this;
  }
  
  public MessagePipeHandle toMessagePipeHandle()
  {
    return this;
  }
  
  public SharedBufferHandle toSharedBufferHandle()
  {
    return this;
  }
  
  public UntypedHandle toUntypedHandle()
  {
    return this;
  }
  
  public void unmap(ByteBuffer paramByteBuffer)
  {
    throw new MojoException(3);
  }
  
  public ResultAnd<Integer> writeData(ByteBuffer paramByteBuffer, DataPipe.WriteFlags paramWriteFlags)
  {
    throw new MojoException(3);
  }
  
  public void writeMessage(ByteBuffer paramByteBuffer, List<? extends Handle> paramList, MessagePipeHandle.WriteFlags paramWriteFlags)
  {
    throw new MojoException(3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\InvalidHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */