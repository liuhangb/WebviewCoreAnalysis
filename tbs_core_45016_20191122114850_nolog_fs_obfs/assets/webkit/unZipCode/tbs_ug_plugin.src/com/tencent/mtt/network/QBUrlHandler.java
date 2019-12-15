package com.tencent.mtt.network;

import com.tencent.basesupport.ModuleProxy;
import com.tencent.common.manifest.annotation.Service;
import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

@Service
public abstract interface QBUrlHandler
{
  public static final ModuleProxy<QBUrlHandler> PROXY = ModuleProxy.newProxy(QBUrlHandler.class, new DefaultQBUrlHandler());
  
  public abstract String getQueenProxyUrl(String paramString);
  
  public abstract URLConnection openConnection(QBUrl paramQBUrl)
    throws IOException;
  
  public abstract URLConnection openConnection(QBUrl paramQBUrl, Proxy paramProxy)
    throws IOException;
  
  public static class DefaultQBUrlHandler
    implements QBUrlHandler
  {
    public String getQueenProxyUrl(String paramString)
    {
      return paramString;
    }
    
    public URLConnection openConnection(QBUrl paramQBUrl)
      throws IOException
    {
      return paramQBUrl.getUrl().openConnection();
    }
    
    public URLConnection openConnection(QBUrl paramQBUrl, Proxy paramProxy)
      throws IOException
    {
      return paramQBUrl.getUrl().openConnection(paramProxy);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\network\QBUrlHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */