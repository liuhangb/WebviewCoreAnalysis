package org.chromium.mojo.bindings;

import java.util.concurrent.Executor;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.MessagePipeHandle;

class AutoCloseableRouter
  implements Router
{
  private final Exception jdField_a_of_type_JavaLangException;
  private final Executor jdField_a_of_type_JavaUtilConcurrentExecutor;
  private final Router jdField_a_of_type_OrgChromiumMojoBindingsRouter;
  private boolean jdField_a_of_type_Boolean;
  
  public AutoCloseableRouter(Core paramCore, Router paramRouter)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsRouter = paramRouter;
    this.jdField_a_of_type_JavaUtilConcurrentExecutor = ExecutorFactory.a(paramCore);
    this.jdField_a_of_type_JavaLangException = new Exception("AutocloseableRouter allocated at:");
  }
  
  public MessagePipeHandle a()
  {
    return (MessagePipeHandle)this.jdField_a_of_type_OrgChromiumMojoBindingsRouter.passHandle();
  }
  
  public boolean accept(Message paramMessage)
  {
    return this.jdField_a_of_type_OrgChromiumMojoBindingsRouter.accept(paramMessage);
  }
  
  public boolean acceptWithResponder(Message paramMessage, MessageReceiver paramMessageReceiver)
  {
    return this.jdField_a_of_type_OrgChromiumMojoBindingsRouter.acceptWithResponder(paramMessage, paramMessageReceiver);
  }
  
  public void close()
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsRouter.close();
    this.jdField_a_of_type_Boolean = true;
  }
  
  protected void finalize()
    throws Throwable
  {
    if (this.jdField_a_of_type_Boolean)
    {
      super.finalize();
      return;
    }
    this.jdField_a_of_type_JavaUtilConcurrentExecutor.execute(new Runnable()
    {
      public void run()
      {
        AutoCloseableRouter.this.close();
      }
    });
    throw new IllegalStateException("Warning: Router objects should be explicitly closed when no longer required otherwise you may leak handles.", this.jdField_a_of_type_JavaLangException);
  }
  
  public void setErrorHandler(ConnectionErrorHandler paramConnectionErrorHandler)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsRouter.setErrorHandler(paramConnectionErrorHandler);
  }
  
  public void setIncomingMessageReceiver(MessageReceiverWithResponder paramMessageReceiverWithResponder)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsRouter.setIncomingMessageReceiver(paramMessageReceiverWithResponder);
  }
  
  public void start()
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsRouter.start();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\AutoCloseableRouter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */