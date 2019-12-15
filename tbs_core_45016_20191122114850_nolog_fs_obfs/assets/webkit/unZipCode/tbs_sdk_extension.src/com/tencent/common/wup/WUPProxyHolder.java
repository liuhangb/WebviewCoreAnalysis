package com.tencent.common.wup;

import com.tencent.common.manifest.AppManifest;
import com.tencent.common.wup.interfaces.IWUPClientProxy;

public class WUPProxyHolder
{
  private static IWUPClientProxy a;
  
  public static IWUPClientProxy getPublicWUPProxy()
  {
    IWUPClientProxy localIWUPClientProxy = a;
    if (localIWUPClientProxy != null) {
      return localIWUPClientProxy;
    }
    try
    {
      localIWUPClientProxy = a;
      if (localIWUPClientProxy == null) {
        try
        {
          a = (IWUPClientProxy)AppManifest.getInstance().queryExtension(IWUPClientProxy.class, null);
        }
        catch (Throwable localThrowable)
        {
          a = null;
          localThrowable.printStackTrace();
        }
      }
      return a;
    }
    finally {}
  }
  
  public static void setPublicWUPProxy(IWUPClientProxy paramIWUPClientProxy)
  {
    a = paramIWUPClientProxy;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\WUPProxyHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */