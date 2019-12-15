package org.chromium.mojo.bindings;

import org.chromium.mojo.system.Core;

public abstract class Struct
{
  private final int a;
  private final int b;
  
  protected Struct(int paramInt1, int paramInt2)
  {
    this.a = paramInt1;
    this.b = paramInt2;
  }
  
  public ServiceMessage a(Core paramCore, MessageHeader paramMessageHeader)
  {
    paramCore = new Encoder(paramCore, this.a + paramMessageHeader.a());
    paramMessageHeader.a(paramCore);
    encode(paramCore);
    return new ServiceMessage(paramCore.a(), paramMessageHeader);
  }
  
  protected abstract void encode(Encoder paramEncoder);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\Struct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */