package org.chromium.mojo.bindings;

public abstract interface MessageReceiverWithResponder
  extends MessageReceiver
{
  public abstract boolean acceptWithResponder(Message paramMessage, MessageReceiver paramMessageReceiver);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\MessageReceiverWithResponder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */