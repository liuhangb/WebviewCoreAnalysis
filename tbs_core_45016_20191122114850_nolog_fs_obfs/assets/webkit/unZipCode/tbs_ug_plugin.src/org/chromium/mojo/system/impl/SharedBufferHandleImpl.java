package org.chromium.mojo.system.impl;

import java.nio.ByteBuffer;
import org.chromium.mojo.system.SharedBufferHandle;
import org.chromium.mojo.system.SharedBufferHandle.DuplicateOptions;
import org.chromium.mojo.system.SharedBufferHandle.MapFlags;

class SharedBufferHandleImpl
  extends HandleBase
  implements SharedBufferHandle
{
  SharedBufferHandleImpl(CoreImpl paramCoreImpl, int paramInt)
  {
    super(paramCoreImpl, paramInt);
  }
  
  SharedBufferHandleImpl(HandleBase paramHandleBase)
  {
    super(paramHandleBase);
  }
  
  public SharedBufferHandle duplicate(SharedBufferHandle.DuplicateOptions paramDuplicateOptions)
  {
    return this.a.a(this, paramDuplicateOptions);
  }
  
  public ByteBuffer map(long paramLong1, long paramLong2, SharedBufferHandle.MapFlags paramMapFlags)
  {
    return this.a.a(this, paramLong1, paramLong2, paramMapFlags);
  }
  
  public SharedBufferHandle pass()
  {
    return new SharedBufferHandleImpl(this);
  }
  
  public void unmap(ByteBuffer paramByteBuffer)
  {
    this.a.a(paramByteBuffer);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\impl\SharedBufferHandleImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */