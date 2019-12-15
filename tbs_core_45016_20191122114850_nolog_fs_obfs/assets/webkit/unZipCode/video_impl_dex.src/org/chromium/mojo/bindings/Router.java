package org.chromium.mojo.bindings;

import org.chromium.mojo.system.MessagePipeHandle;

public abstract interface Router
  extends HandleOwner<MessagePipeHandle>, MessageReceiverWithResponder
{
  public abstract void setErrorHandler(ConnectionErrorHandler paramConnectionErrorHandler);
  
  public abstract void setIncomingMessageReceiver(MessageReceiverWithResponder paramMessageReceiverWithResponder);
  
  public abstract void start();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\Router.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */