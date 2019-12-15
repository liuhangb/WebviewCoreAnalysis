package org.chromium.mojo.system;

public abstract interface Core
{
  public abstract UntypedHandle acquireNativeHandle(int paramInt);
  
  public abstract Pair<DataPipe.ProducerHandle, DataPipe.ConsumerHandle> createDataPipe(DataPipe.CreateOptions paramCreateOptions);
  
  public abstract RunLoop createDefaultRunLoop();
  
  public abstract Pair<MessagePipeHandle, MessagePipeHandle> createMessagePipe(MessagePipeHandle.CreateOptions paramCreateOptions);
  
  public abstract SharedBufferHandle createSharedBuffer(SharedBufferHandle.CreateOptions paramCreateOptions, long paramLong);
  
  public abstract RunLoop getCurrentRunLoop();
  
  public abstract long getTimeTicksNow();
  
  public abstract Watcher getWatcher();
  
  public static class HandleSignals
    extends Flags<HandleSignals>
  {
    public static final HandleSignals a = (HandleSignals)a().a();
    public static final HandleSignals b = (HandleSignals)a().a(true).a();
    public static final HandleSignals c = (HandleSignals)a().b(true).a();
    
    public HandleSignals(int paramInt)
    {
      super();
    }
    
    public static HandleSignals a()
    {
      return new HandleSignals(0);
    }
    
    public HandleSignals a(boolean paramBoolean)
    {
      return (HandleSignals)a(1, paramBoolean);
    }
    
    public HandleSignals b(boolean paramBoolean)
    {
      return (HandleSignals)a(2, paramBoolean);
    }
  }
  
  public static class HandleSignalsState
  {
    private final Core.HandleSignals a;
    private final Core.HandleSignals b;
    
    public HandleSignalsState(Core.HandleSignals paramHandleSignals1, Core.HandleSignals paramHandleSignals2)
    {
      this.a = paramHandleSignals1;
      this.b = paramHandleSignals2;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\Core.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */