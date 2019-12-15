package org.chromium.mojo.bindings;

import android.annotation.SuppressLint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.mojo.system.Watcher;

@SuppressLint({"UseSparseArrays"})
public class RouterImpl
  implements Router
{
  private long jdField_a_of_type_Long = 1L;
  private Map<Long, MessageReceiver> jdField_a_of_type_JavaUtilMap = new HashMap();
  private final Executor jdField_a_of_type_JavaUtilConcurrentExecutor;
  private final Connector jdField_a_of_type_OrgChromiumMojoBindingsConnector;
  private MessageReceiverWithResponder jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiverWithResponder;
  
  public RouterImpl(MessagePipeHandle paramMessagePipeHandle)
  {
    this(paramMessagePipeHandle, BindingsHelper.a(paramMessagePipeHandle));
  }
  
  public RouterImpl(MessagePipeHandle paramMessagePipeHandle, Watcher paramWatcher)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsConnector = new Connector(paramMessagePipeHandle, paramWatcher);
    this.jdField_a_of_type_OrgChromiumMojoBindingsConnector.a(new HandleIncomingMessageThunk(null));
    paramMessagePipeHandle = paramMessagePipeHandle.getCore();
    if (paramMessagePipeHandle != null)
    {
      this.jdField_a_of_type_JavaUtilConcurrentExecutor = ExecutorFactory.a(paramMessagePipeHandle);
      return;
    }
    this.jdField_a_of_type_JavaUtilConcurrentExecutor = null;
  }
  
  private void a()
  {
    MessageReceiverWithResponder localMessageReceiverWithResponder = this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiverWithResponder;
    if (localMessageReceiverWithResponder != null) {
      localMessageReceiverWithResponder.close();
    }
  }
  
  private boolean a(Message paramMessage)
  {
    Object localObject = paramMessage.a().a();
    if (((MessageHeader)localObject).a(1))
    {
      localObject = this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiverWithResponder;
      if (localObject != null) {
        return ((MessageReceiverWithResponder)localObject).acceptWithResponder(paramMessage, new ResponderThunk());
      }
      close();
      return false;
    }
    if (((MessageHeader)localObject).a(2))
    {
      long l = ((MessageHeader)localObject).a();
      localObject = (MessageReceiver)this.jdField_a_of_type_JavaUtilMap.get(Long.valueOf(l));
      if (localObject == null) {
        return false;
      }
      this.jdField_a_of_type_JavaUtilMap.remove(Long.valueOf(l));
      return ((MessageReceiver)localObject).accept(paramMessage);
    }
    localObject = this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiverWithResponder;
    if (localObject != null) {
      return ((MessageReceiverWithResponder)localObject).accept(paramMessage);
    }
    return false;
  }
  
  private void b()
  {
    Executor localExecutor = this.jdField_a_of_type_JavaUtilConcurrentExecutor;
    if (localExecutor != null) {
      localExecutor.execute(new Runnable()
      {
        public void run()
        {
          RouterImpl.this.close();
        }
      });
    }
  }
  
  public MessagePipeHandle a()
  {
    return this.jdField_a_of_type_OrgChromiumMojoBindingsConnector.a();
  }
  
  public boolean accept(Message paramMessage)
  {
    return this.jdField_a_of_type_OrgChromiumMojoBindingsConnector.accept(paramMessage);
  }
  
  public boolean acceptWithResponder(Message paramMessage, MessageReceiver paramMessageReceiver)
  {
    paramMessage = paramMessage.a();
    if ((!jdField_a_of_type_Boolean) && (!paramMessage.a().a(1))) {
      throw new AssertionError();
    }
    long l2 = this.jdField_a_of_type_Long;
    this.jdField_a_of_type_Long = (l2 + 1L);
    long l1 = l2;
    if (l2 == 0L)
    {
      l1 = this.jdField_a_of_type_Long;
      this.jdField_a_of_type_Long = (1L + l1);
    }
    if (!this.jdField_a_of_type_JavaUtilMap.containsKey(Long.valueOf(l1)))
    {
      paramMessage.a(l1);
      if (!this.jdField_a_of_type_OrgChromiumMojoBindingsConnector.accept(paramMessage)) {
        return false;
      }
      this.jdField_a_of_type_JavaUtilMap.put(Long.valueOf(l1), paramMessageReceiver);
      return true;
    }
    throw new IllegalStateException("Unable to find a new request identifier.");
  }
  
  public void close()
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsConnector.close();
  }
  
  public void setErrorHandler(ConnectionErrorHandler paramConnectionErrorHandler)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsConnector.a(paramConnectionErrorHandler);
  }
  
  public void setIncomingMessageReceiver(MessageReceiverWithResponder paramMessageReceiverWithResponder)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiverWithResponder = paramMessageReceiverWithResponder;
  }
  
  public void start()
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsConnector.a();
  }
  
  private class HandleIncomingMessageThunk
    implements MessageReceiver
  {
    private HandleIncomingMessageThunk() {}
    
    public boolean accept(Message paramMessage)
    {
      return RouterImpl.a(RouterImpl.this, paramMessage);
    }
    
    public void close()
    {
      RouterImpl.a(RouterImpl.this);
    }
  }
  
  class ResponderThunk
    implements MessageReceiver
  {
    private boolean jdField_a_of_type_Boolean;
    
    ResponderThunk() {}
    
    public boolean accept(Message paramMessage)
    {
      this.jdField_a_of_type_Boolean = true;
      return RouterImpl.this.accept(paramMessage);
    }
    
    public void close()
    {
      RouterImpl.this.close();
    }
    
    protected void finalize()
      throws Throwable
    {
      if (!this.jdField_a_of_type_Boolean) {
        RouterImpl.b(RouterImpl.this);
      }
      super.finalize();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\RouterImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */