package org.chromium.mojo.system;

import java.nio.ByteBuffer;
import java.util.List;

public abstract interface MessagePipeHandle
  extends Handle
{
  public abstract MessagePipeHandle pass();
  
  public abstract ResultAnd<ReadMessageResult> readMessage(ReadFlags paramReadFlags);
  
  public abstract void writeMessage(ByteBuffer paramByteBuffer, List<? extends Handle> paramList, WriteFlags paramWriteFlags);
  
  public static class CreateFlags
    extends Flags<CreateFlags>
  {
    public static final CreateFlags a = (CreateFlags)a().a();
    
    protected CreateFlags(int paramInt)
    {
      super();
    }
    
    public static CreateFlags a()
    {
      return new CreateFlags(0);
    }
  }
  
  public static class CreateOptions
  {
    private MessagePipeHandle.CreateFlags a = MessagePipeHandle.CreateFlags.a;
    
    public MessagePipeHandle.CreateFlags a()
    {
      return this.a;
    }
  }
  
  public static class ReadFlags
    extends Flags<ReadFlags>
  {
    public static final ReadFlags a = (ReadFlags)a().a();
    
    private ReadFlags(int paramInt)
    {
      super();
    }
    
    public static ReadFlags a()
    {
      return new ReadFlags(0);
    }
  }
  
  public static class ReadMessageResult
  {
    public List<UntypedHandle> a;
    public byte[] a;
    public int[] a;
  }
  
  public static class WriteFlags
    extends Flags<WriteFlags>
  {
    public static final WriteFlags a = (WriteFlags)a().a();
    
    private WriteFlags(int paramInt)
    {
      super();
    }
    
    public static WriteFlags a()
    {
      return new WriteFlags(0);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\MessagePipeHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */