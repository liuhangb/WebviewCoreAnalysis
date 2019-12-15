package com.tencent.mtt.base.net.frame;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PoolingByteArrayOutputStream
  extends ByteArrayOutputStream
{
  private final ByteArrayPool a;
  
  public PoolingByteArrayOutputStream(ByteArrayPool paramByteArrayPool)
  {
    this(paramByteArrayPool, 256);
  }
  
  public PoolingByteArrayOutputStream(ByteArrayPool paramByteArrayPool, int paramInt)
  {
    this.a = paramByteArrayPool;
    this.buf = this.a.getBuf(Math.max(paramInt, 256));
  }
  
  private void a(int paramInt)
  {
    if (this.count + paramInt <= this.buf.length) {
      return;
    }
    byte[] arrayOfByte = this.a.getBuf((this.count + paramInt) * 2);
    System.arraycopy(this.buf, 0, arrayOfByte, 0, this.count);
    this.a.returnBuf(this.buf);
    this.buf = arrayOfByte;
  }
  
  public void close()
    throws IOException
  {
    this.a.returnBuf(this.buf);
    this.buf = null;
    super.close();
  }
  
  public void finalize()
  {
    this.a.returnBuf(this.buf);
  }
  
  public void write(int paramInt)
  {
    try
    {
      a(1);
      super.write(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      a(paramInt2);
      super.write(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    finally
    {
      paramArrayOfByte = finally;
      throw paramArrayOfByte;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\net\frame\PoolingByteArrayOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */