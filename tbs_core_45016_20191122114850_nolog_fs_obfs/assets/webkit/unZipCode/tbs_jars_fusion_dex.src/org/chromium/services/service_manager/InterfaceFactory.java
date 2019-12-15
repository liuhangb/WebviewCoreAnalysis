package org.chromium.services.service_manager;

import org.chromium.mojo.bindings.Interface;

public abstract interface InterfaceFactory<I extends Interface>
{
  public abstract I createImpl();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\services\service_manager\InterfaceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */