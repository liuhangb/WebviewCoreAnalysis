package org.chromium.service_manager.mojom;

import org.chromium.mojo.bindings.DeserializationException;

public final class ConnectResult
{
  public static void a(int paramInt)
  {
    if (a(paramInt)) {
      return;
    }
    throw new DeserializationException("Invalid enum value.");
  }
  
  public static boolean a(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return false;
    }
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\ConnectResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */