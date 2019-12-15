package org.chromium.mojo.bindings;

import java.nio.ByteBuffer;
import org.chromium.mojo.system.Core.HandleSignals;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.mojo.system.MessagePipeHandle.ReadFlags;
import org.chromium.mojo.system.MessagePipeHandle.ReadMessageResult;
import org.chromium.mojo.system.MessagePipeHandle.WriteFlags;
import org.chromium.mojo.system.MojoException;
import org.chromium.mojo.system.ResultAnd;
import org.chromium.mojo.system.Watcher;
import org.chromium.mojo.system.Watcher.Callback;

public class Connector
  implements HandleOwner<MessagePipeHandle>, MessageReceiver
{
  private ConnectionErrorHandler jdField_a_of_type_OrgChromiumMojoBindingsConnectionErrorHandler;
  private final WatcherCallback jdField_a_of_type_OrgChromiumMojoBindingsConnector$WatcherCallback = new WatcherCallback(null);
  private MessageReceiver jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
  private final MessagePipeHandle jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle;
  private final Watcher jdField_a_of_type_OrgChromiumMojoSystemWatcher;
  
  public Connector(MessagePipeHandle paramMessagePipeHandle)
  {
    this(paramMessagePipeHandle, BindingsHelper.a(paramMessagePipeHandle));
  }
  
  public Connector(MessagePipeHandle paramMessagePipeHandle, Watcher paramWatcher)
  {
    this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle = paramMessagePipeHandle;
    this.jdField_a_of_type_OrgChromiumMojoSystemWatcher = paramWatcher;
  }
  
  static ResultAnd<Boolean> a(MessagePipeHandle paramMessagePipeHandle, MessageReceiver paramMessageReceiver)
  {
    paramMessagePipeHandle = paramMessagePipeHandle.readMessage(MessagePipeHandle.ReadFlags.a);
    if (paramMessagePipeHandle.a() != 0) {
      return new ResultAnd(paramMessagePipeHandle.a(), Boolean.valueOf(false));
    }
    MessagePipeHandle.ReadMessageResult localReadMessageResult = (MessagePipeHandle.ReadMessageResult)paramMessagePipeHandle.a();
    if ((!jdField_a_of_type_Boolean) && (localReadMessageResult == null)) {
      throw new AssertionError();
    }
    if (paramMessageReceiver != null)
    {
      boolean bool = paramMessageReceiver.accept(new Message(ByteBuffer.wrap(localReadMessageResult.jdField_a_of_type_ArrayOfByte), localReadMessageResult.jdField_a_of_type_JavaUtilList));
      return new ResultAnd(paramMessagePipeHandle.a(), Boolean.valueOf(bool));
    }
    return new ResultAnd(paramMessagePipeHandle.a(), Boolean.valueOf(false));
  }
  
  private void a(int paramInt)
  {
    if (paramInt == 0)
    {
      b();
      return;
    }
    a(new MojoException(paramInt));
  }
  
  private void a(MojoException paramMojoException)
  {
    close();
    ConnectionErrorHandler localConnectionErrorHandler = this.jdField_a_of_type_OrgChromiumMojoBindingsConnectionErrorHandler;
    if (localConnectionErrorHandler != null) {
      localConnectionErrorHandler.onConnectionError(paramMojoException);
    }
  }
  
  private void b()
  {
    try
    {
      ResultAnd localResultAnd;
      do
      {
        localResultAnd = a(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle, this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver);
      } while (((Boolean)localResultAnd.a()).booleanValue());
      if (localResultAnd.a() != 17) {
        a(new MojoException(localResultAnd.a()));
      }
      return;
    }
    catch (MojoException localMojoException)
    {
      a(localMojoException);
    }
  }
  
  private void c()
  {
    this.jdField_a_of_type_OrgChromiumMojoSystemWatcher.cancel();
    this.jdField_a_of_type_OrgChromiumMojoSystemWatcher.destroy();
  }
  
  public MessagePipeHandle a()
  {
    c();
    MessagePipeHandle localMessagePipeHandle = this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle.pass();
    MessageReceiver localMessageReceiver = this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
    if (localMessageReceiver != null) {
      localMessageReceiver.close();
    }
    return localMessagePipeHandle;
  }
  
  public void a()
  {
    this.jdField_a_of_type_OrgChromiumMojoSystemWatcher.start(this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle, Core.HandleSignals.b, this.jdField_a_of_type_OrgChromiumMojoBindingsConnector$WatcherCallback);
  }
  
  public void a(ConnectionErrorHandler paramConnectionErrorHandler)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsConnectionErrorHandler = paramConnectionErrorHandler;
  }
  
  public void a(MessageReceiver paramMessageReceiver)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver = paramMessageReceiver;
  }
  
  public boolean accept(Message paramMessage)
  {
    try
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle.writeMessage(paramMessage.a(), paramMessage.a(), MessagePipeHandle.WriteFlags.a);
      return true;
    }
    catch (MojoException paramMessage)
    {
      a(paramMessage);
    }
    return false;
  }
  
  public void close()
  {
    c();
    this.jdField_a_of_type_OrgChromiumMojoSystemMessagePipeHandle.close();
    MessageReceiver localMessageReceiver = this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver;
    if (localMessageReceiver != null)
    {
      this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiver = null;
      localMessageReceiver.close();
    }
  }
  
  private class WatcherCallback
    implements Watcher.Callback
  {
    private WatcherCallback() {}
    
    public void onResult(int paramInt)
    {
      Connector.a(Connector.this, paramInt);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\Connector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */