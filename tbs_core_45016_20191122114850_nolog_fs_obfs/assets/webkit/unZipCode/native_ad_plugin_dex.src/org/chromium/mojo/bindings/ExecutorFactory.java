package org.chromium.mojo.bindings;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.Core.HandleSignals;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.mojo.system.MessagePipeHandle.CreateOptions;
import org.chromium.mojo.system.MessagePipeHandle.ReadFlags;
import org.chromium.mojo.system.MessagePipeHandle.WriteFlags;
import org.chromium.mojo.system.MojoException;
import org.chromium.mojo.system.Pair;
import org.chromium.mojo.system.ResultAnd;
import org.chromium.mojo.system.Watcher;
import org.chromium.mojo.system.Watcher.Callback;

class ExecutorFactory
{
  private static final ThreadLocal<Executor> jdField_a_of_type_JavaLangThreadLocal = new ThreadLocal();
  private static final ByteBuffer jdField_a_of_type_JavaNioByteBuffer;
  
  public static Executor a(Core paramCore)
  {
    Executor localExecutor = (Executor)jdField_a_of_type_JavaLangThreadLocal.get();
    Object localObject = localExecutor;
    if (localExecutor == null)
    {
      localObject = new PipedExecutor(paramCore);
      jdField_a_of_type_JavaLangThreadLocal.set(localObject);
    }
    return (Executor)localObject;
  }
  
  private static class PipedExecutor
    implements Executor, Watcher.Callback
  {
    private final Object jdField_a_of_type_JavaLangObject;
    private final List<Runnable> jdField_a_of_type_JavaUtilList;
    private final MessagePipeHandle jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle;
    private final Watcher jdField_a_of_type_OrgChromiumMojoSystemWatcher;
    private final MessagePipeHandle b;
    
    public PipedExecutor(Core paramCore)
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemWatcher = paramCore.getWatcher();
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumMojoSystemWatcher == null)) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_JavaLangObject = new Object();
      paramCore = paramCore.createMessagePipe(new MessagePipeHandle.CreateOptions());
      this.b = ((MessagePipeHandle)paramCore.jdField_a_of_type_JavaLangObject);
      this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = ((MessagePipeHandle)paramCore.b);
      this.jdField_a_of_type_JavaUtilList = new ArrayList();
      this.jdField_a_of_type_OrgChromiumMojoSystemWatcher.start(this.b, Core.HandleSignals.b, this);
    }
    
    private void a()
    {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle.close();
        this.jdField_a_of_type_JavaUtilList.clear();
        this.jdField_a_of_type_OrgChromiumMojoSystemWatcher.cancel();
        this.jdField_a_of_type_OrgChromiumMojoSystemWatcher.destroy();
        this.b.close();
        return;
      }
    }
    
    private boolean a()
    {
      try
      {
        int i = this.b.readMessage(MessagePipeHandle.ReadFlags.a).a();
        if (i == 0) {
          return true;
        }
      }
      catch (MojoException localMojoException)
      {
        for (;;) {}
      }
      return false;
    }
    
    private void b()
    {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        Runnable localRunnable = (Runnable)this.jdField_a_of_type_JavaUtilList.remove(0);
        localRunnable.run();
        return;
      }
    }
    
    public void execute(Runnable paramRunnable)
    {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        if (this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle.isValid())
        {
          this.jdField_a_of_type_JavaUtilList.add(paramRunnable);
          this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle.writeMessage(ExecutorFactory.a(), null, MessagePipeHandle.WriteFlags.a);
          return;
        }
        throw new IllegalStateException("Trying to execute an action on a closed executor.");
      }
    }
    
    public void onResult(int paramInt)
    {
      if ((paramInt == 0) && (a()))
      {
        b();
        return;
      }
      a();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\ExecutorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */