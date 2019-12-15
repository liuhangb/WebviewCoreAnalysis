package org.chromium.mojo.system.impl;

import java.nio.ByteBuffer;
import org.chromium.mojo.system.DataPipe.ConsumerHandle;
import org.chromium.mojo.system.DataPipe.ReadFlags;
import org.chromium.mojo.system.ResultAnd;

class DataPipeConsumerHandleImpl
  extends HandleBase
  implements DataPipe.ConsumerHandle
{
  DataPipeConsumerHandleImpl(CoreImpl paramCoreImpl, int paramInt)
  {
    super(paramCoreImpl, paramInt);
  }
  
  DataPipeConsumerHandleImpl(HandleBase paramHandleBase)
  {
    super(paramHandleBase);
  }
  
  public ByteBuffer beginReadData(int paramInt, DataPipe.ReadFlags paramReadFlags)
  {
    return this.a.a(this, paramInt, paramReadFlags);
  }
  
  public int discardData(int paramInt, DataPipe.ReadFlags paramReadFlags)
  {
    return this.a.a(this, paramInt, paramReadFlags);
  }
  
  public void endReadData(int paramInt)
  {
    this.a.a(this, paramInt);
  }
  
  public DataPipe.ConsumerHandle pass()
  {
    return new DataPipeConsumerHandleImpl(this);
  }
  
  public ResultAnd<Integer> readData(ByteBuffer paramByteBuffer, DataPipe.ReadFlags paramReadFlags)
  {
    return this.a.a(this, paramByteBuffer, paramReadFlags);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\impl\DataPipeConsumerHandleImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */