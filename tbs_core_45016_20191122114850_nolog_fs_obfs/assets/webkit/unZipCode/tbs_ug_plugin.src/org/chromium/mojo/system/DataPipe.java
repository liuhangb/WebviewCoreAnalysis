package org.chromium.mojo.system;

import java.nio.ByteBuffer;

public abstract interface DataPipe
{
  public static abstract interface ConsumerHandle
    extends Handle
  {
    public abstract ByteBuffer beginReadData(int paramInt, DataPipe.ReadFlags paramReadFlags);
    
    public abstract int discardData(int paramInt, DataPipe.ReadFlags paramReadFlags);
    
    public abstract void endReadData(int paramInt);
    
    public abstract ConsumerHandle pass();
    
    public abstract ResultAnd<Integer> readData(ByteBuffer paramByteBuffer, DataPipe.ReadFlags paramReadFlags);
  }
  
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
    private int jdField_a_of_type_Int;
    private DataPipe.CreateFlags jdField_a_of_type_OrgChromiumMojoSystemDataPipe$CreateFlags = DataPipe.CreateFlags.a();
    private int b;
    
    public int a()
    {
      return this.jdField_a_of_type_Int;
    }
    
    public DataPipe.CreateFlags a()
    {
      return this.jdField_a_of_type_OrgChromiumMojoSystemDataPipe$CreateFlags;
    }
    
    public int b()
    {
      return this.b;
    }
  }
  
  public static abstract interface ProducerHandle
    extends Handle
  {
    public abstract ByteBuffer beginWriteData(int paramInt, DataPipe.WriteFlags paramWriteFlags);
    
    public abstract void endWriteData(int paramInt);
    
    public abstract ProducerHandle pass();
    
    public abstract ResultAnd<Integer> writeData(ByteBuffer paramByteBuffer, DataPipe.WriteFlags paramWriteFlags);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\DataPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */