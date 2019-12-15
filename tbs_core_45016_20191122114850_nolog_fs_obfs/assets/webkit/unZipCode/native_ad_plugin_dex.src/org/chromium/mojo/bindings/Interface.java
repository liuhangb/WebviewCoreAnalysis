package org.chromium.mojo.bindings;

import java.io.Closeable;
import org.chromium.mojo.bindings.interfacecontrol.QueryVersion;
import org.chromium.mojo.bindings.interfacecontrol.QueryVersionResult;
import org.chromium.mojo.bindings.interfacecontrol.RequireVersion;
import org.chromium.mojo.bindings.interfacecontrol.RunInput;
import org.chromium.mojo.bindings.interfacecontrol.RunMessageParams;
import org.chromium.mojo.bindings.interfacecontrol.RunOrClosePipeInput;
import org.chromium.mojo.bindings.interfacecontrol.RunOrClosePipeMessageParams;
import org.chromium.mojo.bindings.interfacecontrol.RunOutput;
import org.chromium.mojo.bindings.interfacecontrol.RunResponseMessageParams;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.mojo.system.MojoException;
import org.chromium.mojo.system.Pair;

public abstract interface Interface
  extends Closeable, ConnectionErrorHandler
{
  public abstract void close();
  
  public static abstract class AbstractProxy
    implements Interface.Proxy
  {
    private final HandlerImpl a;
    
    protected AbstractProxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
    {
      this.a = new HandlerImpl(paramCore, paramMessageReceiverWithResponder);
    }
    
    public HandlerImpl a()
    {
      return this.a;
    }
    
    public void close()
    {
      this.a.close();
    }
    
    public void onConnectionError(MojoException paramMojoException)
    {
      this.a.onConnectionError(paramMojoException);
    }
    
    protected static class HandlerImpl
      implements ConnectionErrorHandler, Interface.Proxy.Handler
    {
      private int jdField_a_of_type_Int;
      private ConnectionErrorHandler jdField_a_of_type_OrgChromiumMojoBindingsConnectionErrorHandler;
      private final MessageReceiverWithResponder jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiverWithResponder;
      private final Core jdField_a_of_type_OrgChromiumMojoSystemCore;
      
      protected HandlerImpl(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder)
      {
        this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
        this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiverWithResponder = paramMessageReceiverWithResponder;
      }
      
      public MessageReceiverWithResponder a()
      {
        return this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiverWithResponder;
      }
      
      public Core a()
      {
        return this.jdField_a_of_type_OrgChromiumMojoSystemCore;
      }
      
      void a(int paramInt)
      {
        this.jdField_a_of_type_Int = paramInt;
      }
      
      public void close()
      {
        this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiverWithResponder.close();
      }
      
      public int getVersion()
      {
        return this.jdField_a_of_type_Int;
      }
      
      public void onConnectionError(MojoException paramMojoException)
      {
        ConnectionErrorHandler localConnectionErrorHandler = this.jdField_a_of_type_OrgChromiumMojoBindingsConnectionErrorHandler;
        if (localConnectionErrorHandler != null) {
          localConnectionErrorHandler.onConnectionError(paramMojoException);
        }
      }
      
      public MessagePipeHandle passHandle()
      {
        return (MessagePipeHandle)((HandleOwner)this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiverWithResponder).passHandle();
      }
      
      public void queryVersion(final Callbacks.Callback1<Integer> paramCallback1)
      {
        RunMessageParams localRunMessageParams = new RunMessageParams();
        localRunMessageParams.a = new RunInput();
        localRunMessageParams.a.a(new QueryVersion());
        InterfaceControlMessagesHelper.a(a(), this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiverWithResponder, localRunMessageParams, new Callbacks.Callback1()
        {
          public void a(RunResponseMessageParams paramAnonymousRunResponseMessageParams)
          {
            if ((paramAnonymousRunResponseMessageParams.a != null) && (paramAnonymousRunResponseMessageParams.a.a() == 0)) {
              Interface.AbstractProxy.HandlerImpl.a(Interface.AbstractProxy.HandlerImpl.this, paramAnonymousRunResponseMessageParams.a.a().a);
            }
            paramCallback1.call(Integer.valueOf(Interface.AbstractProxy.HandlerImpl.a(Interface.AbstractProxy.HandlerImpl.this)));
          }
        });
      }
      
      public void requireVersion(int paramInt)
      {
        if (this.jdField_a_of_type_Int >= paramInt) {
          return;
        }
        this.jdField_a_of_type_Int = paramInt;
        RunOrClosePipeMessageParams localRunOrClosePipeMessageParams = new RunOrClosePipeMessageParams();
        localRunOrClosePipeMessageParams.a = new RunOrClosePipeInput();
        localRunOrClosePipeMessageParams.a.a(new RequireVersion());
        localRunOrClosePipeMessageParams.a.a().jdField_a_of_type_Int = paramInt;
        InterfaceControlMessagesHelper.a(a(), this.jdField_a_of_type_OrgChromiumMojoBindingsMessageReceiverWithResponder, localRunOrClosePipeMessageParams);
      }
      
      public void setErrorHandler(ConnectionErrorHandler paramConnectionErrorHandler)
      {
        this.jdField_a_of_type_OrgChromiumMojoBindingsConnectionErrorHandler = paramConnectionErrorHandler;
      }
    }
  }
  
  public static abstract class Manager<I extends Interface, P extends Interface.Proxy>
  {
    public void a(I paramI, MessagePipeHandle paramMessagePipeHandle)
    {
      RouterImpl localRouterImpl = new RouterImpl(paramMessagePipeHandle);
      bind(paramMessagePipeHandle.getCore(), paramI, localRouterImpl);
      localRouterImpl.start();
    }
    
    public final InterfaceRequest<I> asInterfaceRequest(MessagePipeHandle paramMessagePipeHandle)
    {
      return new InterfaceRequest(paramMessagePipeHandle);
    }
    
    final P attachProxy(Core paramCore, Router paramRouter)
    {
      return buildProxy(paramCore, new AutoCloseableRouter(paramCore, paramRouter));
    }
    
    public final P attachProxy(MessagePipeHandle paramMessagePipeHandle, int paramInt)
    {
      RouterImpl localRouterImpl = new RouterImpl(paramMessagePipeHandle);
      paramMessagePipeHandle = attachProxy(paramMessagePipeHandle.getCore(), localRouterImpl);
      DelegatingConnectionErrorHandler localDelegatingConnectionErrorHandler = new DelegatingConnectionErrorHandler();
      localDelegatingConnectionErrorHandler.a(paramMessagePipeHandle);
      localRouterImpl.setErrorHandler(localDelegatingConnectionErrorHandler);
      localRouterImpl.start();
      ((Interface.AbstractProxy.HandlerImpl)paramMessagePipeHandle.getProxyHandler()).a(paramInt);
      return paramMessagePipeHandle;
    }
    
    public final void bind(I paramI, InterfaceRequest<I> paramInterfaceRequest)
    {
      a(paramI, paramInterfaceRequest.a());
    }
    
    final void bind(Core paramCore, I paramI, Router paramRouter)
    {
      paramRouter.setErrorHandler(paramI);
      paramRouter.setIncomingMessageReceiver(buildStub(paramCore, paramI));
    }
    
    protected abstract I[] buildArray(int paramInt);
    
    protected abstract P buildProxy(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder);
    
    protected abstract Interface.Stub<I> buildStub(Core paramCore, I paramI);
    
    public final Pair<P, InterfaceRequest<I>> getInterfaceRequest(Core paramCore)
    {
      paramCore = paramCore.createMessagePipe(null);
      return Pair.a(attachProxy((MessagePipeHandle)paramCore.a, 0), new InterfaceRequest((MessagePipeHandle)paramCore.b));
    }
    
    public abstract String getName();
    
    public abstract int getVersion();
  }
  
  public static abstract interface Proxy
    extends Interface
  {
    public abstract Handler getProxyHandler();
    
    public static abstract interface Handler
      extends Closeable
    {
      public abstract int getVersion();
      
      public abstract MessagePipeHandle passHandle();
      
      public abstract void queryVersion(Callbacks.Callback1<Integer> paramCallback1);
      
      public abstract void requireVersion(int paramInt);
      
      public abstract void setErrorHandler(ConnectionErrorHandler paramConnectionErrorHandler);
    }
  }
  
  public static abstract class Stub<I extends Interface>
    implements MessageReceiverWithResponder
  {
    private final I jdField_a_of_type_OrgChromiumMojoBindingsInterface;
    private final Core jdField_a_of_type_OrgChromiumMojoSystemCore;
    
    public Stub(Core paramCore, I paramI)
    {
      this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
      this.jdField_a_of_type_OrgChromiumMojoBindingsInterface = paramI;
    }
    
    protected I a()
    {
      return this.jdField_a_of_type_OrgChromiumMojoBindingsInterface;
    }
    
    protected Core a()
    {
      return this.jdField_a_of_type_OrgChromiumMojoSystemCore;
    }
    
    public void close()
    {
      this.jdField_a_of_type_OrgChromiumMojoBindingsInterface.close();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\Interface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */