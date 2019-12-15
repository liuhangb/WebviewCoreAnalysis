package com.tencent.smtt.net;

import java.nio.ByteBuffer;

public final class i
{
  static void a(ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer.isDirect()) {
      return;
    }
    throw new IllegalArgumentException("byteBuffer must be a direct ByteBuffer.");
  }
  
  static void b(ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer.hasRemaining()) {
      return;
    }
    throw new IllegalArgumentException("ByteBuffer is already full.");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */