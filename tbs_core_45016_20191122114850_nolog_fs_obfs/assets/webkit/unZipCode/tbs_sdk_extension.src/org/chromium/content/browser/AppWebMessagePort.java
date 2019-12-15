package org.chromium.content.browser;

import android.os.Handler;
import android.os.Looper;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.blink.mojom.CloneableMessage;
import org.chromium.blink.mojom.SerializedArrayBufferContents;
import org.chromium.blink.mojom.SerializedBlob;
import org.chromium.blink.mojom.TransferableMessage;
import org.chromium.content_public.browser.MessagePort;
import org.chromium.content_public.browser.MessagePort.MessageCallback;
import org.chromium.mojo.bindings.Connector;
import org.chromium.mojo.bindings.DeserializationException;
import org.chromium.mojo.bindings.MessageHeader;
import org.chromium.mojo.bindings.MessageReceiver;
import org.chromium.mojo.bindings.ServiceMessage;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.mojo.system.MessagePipeHandle.CreateOptions;
import org.chromium.mojo.system.Pair;
import org.chromium.mojo.system.impl.CoreImpl;
import org.chromium.skia.mojom.Bitmap;

@JNINamespace("content")
public class AppWebMessagePort
  implements MessagePort
{
  private static final MessageHeader jdField_a_of_type_OrgChromiumMojoBindingsMessageHeader = new MessageHeader(0);
  private Connector jdField_a_of_type_OrgChromiumMojoBindingsConnector;
  private Core jdField_a_of_type_OrgChromiumMojoSystemCore;
  private boolean jdField_a_of_type_Boolean;
  private boolean b;
  private boolean c;
  private boolean d;
  
  private AppWebMessagePort(MessagePipeHandle paramMessagePipeHandle)
  {
    this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramMessagePipeHandle.getCore();
    this.jdField_a_of_type_OrgChromiumMojoBindingsConnector = new Connector(paramMessagePipeHandle);
  }
  
  private MessagePipeHandle a()
  {
    this.b = true;
    MessagePipeHandle localMessagePipeHandle = this.jdField_a_of_type_OrgChromiumMojoBindingsConnector.a();
    this.jdField_a_of_type_OrgChromiumMojoBindingsConnector = null;
    return localMessagePipeHandle;
  }
  
  public static AppWebMessagePort[] createPair()
  {
    Pair localPair = CoreImpl.a().createMessagePipe(new MessagePipeHandle.CreateOptions());
    return new AppWebMessagePort[] { new AppWebMessagePort((MessagePipeHandle)localPair.a), new AppWebMessagePort((MessagePipeHandle)localPair.b) };
  }
  
  private static native String nativeDecodeStringMessage(byte[] paramArrayOfByte);
  
  private static native byte[] nativeEncodeStringMessage(String paramString);
  
  @CalledByNative
  private int releaseNativeHandle()
  {
    return a().releaseNativeHandle();
  }
  
  public void close()
  {
    if (!this.b)
    {
      if (this.jdField_a_of_type_Boolean) {
        return;
      }
      this.jdField_a_of_type_Boolean = true;
      this.jdField_a_of_type_OrgChromiumMojoBindingsConnector.close();
      this.jdField_a_of_type_OrgChromiumMojoBindingsConnector = null;
      return;
    }
    throw new IllegalStateException("Port is already transferred");
  }
  
  public boolean isClosed()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public boolean isStarted()
  {
    return this.c;
  }
  
  public boolean isTransferred()
  {
    return this.b;
  }
  
  public void postMessage(String paramString, MessagePort[] paramArrayOfMessagePort)
    throws IllegalStateException
  {
    if ((!isClosed()) && (!isTransferred()))
    {
      int i;
      if (paramArrayOfMessagePort == null) {
        i = 0;
      } else {
        i = paramArrayOfMessagePort.length;
      }
      MessagePipeHandle[] arrayOfMessagePipeHandle = new MessagePipeHandle[i];
      if (paramArrayOfMessagePort != null)
      {
        int j = paramArrayOfMessagePort.length;
        i = 0;
        while (i < j)
        {
          MessagePort localMessagePort = paramArrayOfMessagePort[i];
          if (!localMessagePort.equals(this))
          {
            if ((!localMessagePort.isClosed()) && (!localMessagePort.isTransferred()))
            {
              if (!localMessagePort.isStarted()) {
                i += 1;
              } else {
                throw new IllegalStateException("Port is already started");
              }
            }
            else {
              throw new IllegalStateException("Port is already closed or transferred");
            }
          }
          else {
            throw new IllegalStateException("Source port cannot be transferred");
          }
        }
        i = 0;
        while (i < paramArrayOfMessagePort.length)
        {
          arrayOfMessagePipeHandle[i] = ((AppWebMessagePort)paramArrayOfMessagePort[i]).a();
          i += 1;
        }
      }
      this.c = true;
      paramArrayOfMessagePort = new TransferableMessage();
      paramArrayOfMessagePort.jdField_a_of_type_OrgChromiumBlinkMojomCloneableMessage = new CloneableMessage();
      paramArrayOfMessagePort.jdField_a_of_type_OrgChromiumBlinkMojomCloneableMessage.jdField_a_of_type_ArrayOfByte = nativeEncodeStringMessage(paramString);
      paramArrayOfMessagePort.jdField_a_of_type_OrgChromiumBlinkMojomCloneableMessage.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedBlob = new SerializedBlob[0];
      paramArrayOfMessagePort.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedArrayBufferContents = new SerializedArrayBufferContents[0];
      paramArrayOfMessagePort.jdField_a_of_type_ArrayOfOrgChromiumSkiaMojomBitmap = new Bitmap[0];
      paramArrayOfMessagePort.jdField_a_of_type_ArrayOfOrgChromiumMojoSystemMessagePipeHandle = arrayOfMessagePipeHandle;
      this.jdField_a_of_type_OrgChromiumMojoBindingsConnector.accept(paramArrayOfMessagePort.a(this.jdField_a_of_type_OrgChromiumMojoSystemCore, jdField_a_of_type_OrgChromiumMojoBindingsMessageHeader));
      return;
    }
    throw new IllegalStateException("Port is already closed or transferred");
  }
  
  public void setMessageCallback(MessagePort.MessageCallback paramMessageCallback, Handler paramHandler)
  {
    if ((!isClosed()) && (!isTransferred()))
    {
      this.c = true;
      if (paramMessageCallback == null)
      {
        this.jdField_a_of_type_OrgChromiumMojoBindingsConnector.a(null);
      }
      else
      {
        Connector localConnector = this.jdField_a_of_type_OrgChromiumMojoBindingsConnector;
        if (paramHandler == null) {
          paramHandler = Looper.getMainLooper();
        } else {
          paramHandler = paramHandler.getLooper();
        }
        localConnector.a(new MessageHandler(paramHandler, paramMessageCallback));
      }
      if (!this.d)
      {
        this.jdField_a_of_type_OrgChromiumMojoBindingsConnector.a();
        this.d = true;
      }
      return;
    }
    throw new IllegalStateException("Port is already closed or transferred");
  }
  
  private static class MessageHandler
    extends Handler
    implements MessageReceiver
  {
    private final MessagePort.MessageCallback a;
    
    public MessageHandler(Looper paramLooper, MessagePort.MessageCallback paramMessageCallback)
    {
      super();
      this.a = paramMessageCallback;
    }
    
    public boolean accept(org.chromium.mojo.bindings.Message paramMessage)
    {
      try
      {
        paramMessage = TransferableMessage.a(paramMessage.a().a());
        AppWebMessagePort[] arrayOfAppWebMessagePort = new AppWebMessagePort[paramMessage.jdField_a_of_type_ArrayOfOrgChromiumMojoSystemMessagePipeHandle.length];
        int i = 0;
        while (i < arrayOfAppWebMessagePort.length)
        {
          arrayOfAppWebMessagePort[i] = new AppWebMessagePort(paramMessage.jdField_a_of_type_ArrayOfOrgChromiumMojoSystemMessagePipeHandle[i], null);
          i += 1;
        }
        MessagePortMessage localMessagePortMessage = new MessagePortMessage(null);
        localMessagePortMessage.encodedMessage = paramMessage.jdField_a_of_type_OrgChromiumBlinkMojomCloneableMessage.a;
        localMessagePortMessage.ports = arrayOfAppWebMessagePort;
        sendMessage(obtainMessage(1, localMessagePortMessage));
        return true;
      }
      catch (DeserializationException paramMessage) {}
      return false;
    }
    
    public void close() {}
    
    public void handleMessage(android.os.Message paramMessage)
    {
      if (paramMessage.what == 1)
      {
        paramMessage = (MessagePortMessage)paramMessage.obj;
        String str = AppWebMessagePort.a(paramMessage.encodedMessage);
        if (str == null) {
          return;
        }
        this.a.onMessage(str, paramMessage.ports);
        return;
      }
      throw new IllegalStateException("undefined message");
    }
    
    private static class MessagePortMessage
    {
      public byte[] encodedMessage;
      public AppWebMessagePort[] ports;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\AppWebMessagePort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */