package org.chromium.mojo.bindings;

import org.chromium.mojo.bindings.interfacecontrol.QueryVersionResult;
import org.chromium.mojo.bindings.interfacecontrol.RequireVersion;
import org.chromium.mojo.bindings.interfacecontrol.RunInput;
import org.chromium.mojo.bindings.interfacecontrol.RunMessageParams;
import org.chromium.mojo.bindings.interfacecontrol.RunOrClosePipeInput;
import org.chromium.mojo.bindings.interfacecontrol.RunOrClosePipeMessageParams;
import org.chromium.mojo.bindings.interfacecontrol.RunOutput;
import org.chromium.mojo.bindings.interfacecontrol.RunResponseMessageParams;
import org.chromium.mojo.system.Core;

public class InterfaceControlMessagesHelper
{
  public static void a(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder, RunMessageParams paramRunMessageParams, Callbacks.Callback1<RunResponseMessageParams> paramCallback1)
  {
    paramMessageReceiverWithResponder.acceptWithResponder(paramRunMessageParams.a(paramCore, new MessageHeader(-1, 1, 0L)), new RunResponseForwardToCallback(paramCallback1));
  }
  
  public static void a(Core paramCore, MessageReceiverWithResponder paramMessageReceiverWithResponder, RunOrClosePipeMessageParams paramRunOrClosePipeMessageParams)
  {
    paramMessageReceiverWithResponder.accept(paramRunOrClosePipeMessageParams.a(paramCore, new MessageHeader(-2)));
  }
  
  public static <I extends Interface, P extends Interface.Proxy> boolean a(Interface.Manager<I, P> paramManager, ServiceMessage paramServiceMessage)
  {
    paramServiceMessage = RunOrClosePipeMessageParams.a(paramServiceMessage.a());
    int i = paramServiceMessage.a.a();
    boolean bool = false;
    if (i == 0)
    {
      if (paramServiceMessage.a.a().a <= paramManager.getVersion()) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  public static <I extends Interface, P extends Interface.Proxy> boolean a(Core paramCore, Interface.Manager<I, P> paramManager, ServiceMessage paramServiceMessage, MessageReceiver paramMessageReceiver)
  {
    RunMessageParams localRunMessageParams = RunMessageParams.a(paramServiceMessage.a());
    RunResponseMessageParams localRunResponseMessageParams = new RunResponseMessageParams();
    localRunResponseMessageParams.a = new RunOutput();
    if (localRunMessageParams.a.a() == 0)
    {
      localRunResponseMessageParams.a.a(new QueryVersionResult());
      localRunResponseMessageParams.a.a().a = paramManager.getVersion();
    }
    else
    {
      localRunResponseMessageParams.a = null;
    }
    return paramMessageReceiver.accept(localRunResponseMessageParams.a(paramCore, new MessageHeader(-1, 2, paramServiceMessage.a().a())));
  }
  
  private static class RunResponseForwardToCallback
    extends SideEffectFreeCloseable
    implements MessageReceiver
  {
    private final Callbacks.Callback1<RunResponseMessageParams> a;
    
    RunResponseForwardToCallback(Callbacks.Callback1<RunResponseMessageParams> paramCallback1)
    {
      this.a = paramCallback1;
    }
    
    public boolean accept(Message paramMessage)
    {
      paramMessage = RunResponseMessageParams.a(paramMessage.a().a());
      this.a.call(paramMessage);
      return true;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\InterfaceControlMessagesHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */