package org.chromium.mojo.system;

import java.nio.ByteBuffer;

public abstract interface SharedBufferHandle
  extends Handle
{
  public abstract SharedBufferHandle duplicate(DuplicateOptions paramDuplicateOptions);
  
  public abstract ByteBuffer map(long paramLong1, long paramLong2, MapFlags paramMapFlags);
  
  public abstract SharedBufferHandle pass();
  
  public abstract void unmap(ByteBuffer paramByteBuffer);
  
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
    private SharedBufferHandle.CreateFlags a = SharedBufferHandle.CreateFlags.a;
    
    public SharedBufferHandle.CreateFlags a()
    {
      return this.a;
    }
  }
  
  public static class DuplicateFlags
    extends Flags<DuplicateFlags>
  {
    public static final DuplicateFlags a = (DuplicateFlags)a().a();
    
    protected DuplicateFlags(int paramInt)
    {
      super();
    }
    
    public static DuplicateFlags a()
    {
      return new DuplicateFlags(0);
    }
  }
  
  public static class DuplicateOptions
  {
    private SharedBufferHandle.DuplicateFlags a = SharedBufferHandle.DuplicateFlags.a;
    
    public SharedBufferHandle.DuplicateFlags a()
    {
      return this.a;
    }
  }
  
  public static class MapFlags
    extends Flags<MapFlags>
  {
    public static final MapFlags a = (MapFlags)a().a();
    
    protected MapFlags(int paramInt)
    {
      super();
    }
    
    public static MapFlags a()
    {
      return new MapFlags(0);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\SharedBufferHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */