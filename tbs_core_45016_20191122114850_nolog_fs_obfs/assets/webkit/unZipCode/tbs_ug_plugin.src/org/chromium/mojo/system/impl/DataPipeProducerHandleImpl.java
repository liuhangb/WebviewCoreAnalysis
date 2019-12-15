package org.chromium.mojo.system.impl;

import java.nio.ByteBuffer;
import org.chromium.mojo.system.DataPipe.ProducerHandle;
import org.chromium.mojo.system.DataPipe.WriteFlags;
import org.chromium.mojo.system.ResultAnd;

class DataPipeProducerHandleImpl
  extends HandleBase
  implements DataPipe.ProducerHandle
{
  DataPipeProducerHandleImpl(CoreImpl paramCoreImpl, int paramInt)
  {
    super(paramCoreImpl, paramInt);
  }
  
  DataPipeProducerHandleImpl(HandleBase paramHandleBase)
  {
    super(paramHandleBase);
  }
  
  public ByteBuffer beginWriteData(int paramInt, DataPipe.WriteFlags paramWriteFlags)
  {
    return this.a.a(this, paramInt, paramWriteFlags);
  }
  
  public void endWriteData(int paramInt)
  {
    this.a.a(this, paramInt);
  }
  
  public DataPipe.ProducerHandle pass()
  {
    return new DataPipeProducerHandleImpl(this);
  }
  
  public ResultAnd<Integer> writeData(ByteBuffer paramByteBuffer, DataPipe.WriteFlags paramWriteFlags)
  {
    return this.a.a(this, paramByteBuffer, paramWriteFlags);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\impl\DataPipeProducerHandleImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */